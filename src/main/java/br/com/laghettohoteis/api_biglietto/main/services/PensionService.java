package br.com.laghettohoteis.api_biglietto.main.services;

import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension.*;
import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationPension;
import br.com.laghettohoteis.api_biglietto.main.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PensionService {

    @Autowired
    private PensionRepository pensionRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public PensionGet getById(String pensionId, String hotelId) {
        hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Nenhum hotel encontrado com esse ID: " + hotelId));

        ReservationPension pension = pensionRepository.findByIdAndHotelId(pensionId, hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Essa pensão não pertence ao hotel selecionado."));

        return new PensionGet(pension.getId(), pension.getCode(), pension.getDescription());
    }

    public List<PensionGet> getAllByHotel(String hotelId) {
        hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Nenhum hotel encontrado com esse ID: " + hotelId));

        List<ReservationPension> pensions = pensionRepository.findAllByHotelId(hotelId);
        if (pensions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Nenhuma pensão encontrada para esse hotel");
        }

        return pensions.stream()
                .map(p -> new PensionGet(p.getId(), p.getCode(), p.getDescription()))
                .collect(Collectors.toList());
    }

    public void create(PensionCreate data, String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Nenhum hotel encontrado com esse ID: " + hotelId));

        if (pensionRepository.existsByCodeAndHotelId(data.code(), hotelId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Código já cadastrado no hotel: " + hotelId);
        }

        ReservationPension pension = new ReservationPension(data.code(), data.description(), hotel);
        pensionRepository.save(pension);
    }

    public PensionGet edit(String pensionId, PensionPut data, String hotelId) {
        hotelRepository.existsById(hotelId);

        ReservationPension pension = pensionRepository.findByIdAndHotelId(pensionId, hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Essa pensão não pertence ao hotel selecionado."));

        if (pensionRepository.existsByCodeAndHotel_IdAndIdNot(data.code(), hotelId, pensionId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Esse código já está vinculado a outra pensão");
        }

        pension.setCode(data.code());
        pension.setDescription(data.description());
        pensionRepository.save(pension);

        return new PensionGet(pension.getId(), pension.getCode(), pension.getDescription());
    }

    public void delete(String pensionId, String hotelId) {
        hotelRepository.existsById(hotelId);

        ReservationPension pension = pensionRepository.findByIdAndHotelId(pensionId, hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Essa pensão não pertence ao hotel selecionado."));

        if (reservationRepository.existsByPension(pension)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Existem reservas vinculadas a essa pensão");
        }

        pensionRepository.delete(pension);
    }

}