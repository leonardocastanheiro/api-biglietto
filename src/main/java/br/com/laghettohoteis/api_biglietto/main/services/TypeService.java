package br.com.laghettohoteis.api_biglietto.main.services;

import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.type.*;
import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationType;
import br.com.laghettohoteis.api_biglietto.main.repositories.HotelRepository;
import br.com.laghettohoteis.api_biglietto.main.repositories.ReservationRepository;
import br.com.laghettohoteis.api_biglietto.main.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<TypeGet> getAllHotelTypes(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Hotel não encontrado com o ID: " + hotelId));

        return hotel.getReservationTypes().stream()
                .map(t -> new TypeGet(t.getId(), t.getCode(), t.getDescription()))
                .collect(Collectors.toList());
    }

    public TypeGet getHotelType(String typeId, String hotelId) {
        ReservationType type = typeRepository.findByIdAndHotelId(typeId, hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tipo de reserva não encontrado para o hotel com ID: " + hotelId));

        return new TypeGet(type.getId(), type.getCode(), type.getDescription());
    }

    public void create(TypeCreate data, String hotelId) {
        hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Nenhum hotel encontrado com esse ID: " + hotelId));

        if (typeRepository.existsByCodeAndHotelId(data.code(), hotelId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Código já cadastrado: " + data.code());
        }

        var type = new ReservationType(data.code(), data.description(), new Hotel(hotelId));
        typeRepository.save(type);
    }

    public TypeGet edit(String typeId, TypePut data, String hotelId) {
        ReservationType type = typeRepository.findByIdAndHotelId(typeId, hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Esse tipo de reserva não pertence ao hotel selecionado."));

        if (typeRepository.existsByCodeAndHotel_IdAndIdNot(data.code(), hotelId, typeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Esse código já está vinculado a outro tipo de reserva");
        }

        type.setCode(data.code());
        type.setDescription(data.description());
        typeRepository.save(type);

        return new TypeGet(type.getId(), type.getCode(), type.getDescription());
    }

    public void delete(String typeId, String hotelId) {
        ReservationType type = typeRepository.findByIdAndHotelId(typeId, hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Esse tipo de reserva não pertence ao hotel selecionado."));

        if (reservationRepository.existsByType(type)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Existem reservas vinculadas a esse tipo.");
        }

        typeRepository.delete(type);
    }
}