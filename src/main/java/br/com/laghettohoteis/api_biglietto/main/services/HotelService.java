package br.com.laghettohoteis.api_biglietto.main.services;

import br.com.laghettohoteis.api_biglietto.authenticator.service.TokenService;
import br.com.laghettohoteis.api_biglietto.main.dtos.hotel.*;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension.PensionGet;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.type.TypeGet;
import br.com.laghettohoteis.api_biglietto.main.dtos.response.ResponseMessage;
import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.Reservation;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationPension;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationType;
import br.com.laghettohoteis.api_biglietto.main.domain.user.User;
import br.com.laghettohoteis.api_biglietto.main.dtos.guest.GuestGet;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.*;
import br.com.laghettohoteis.api_biglietto.main.dtos.user.UserGetWithoutHotels;
import br.com.laghettohoteis.api_biglietto.main.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void create(HotelCreate data) {
        if (hotelRepository.existsByVhfId(data.vhfId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O VhfId já está cadastrado no banco.");
        }
        if (hotelRepository.existsByDocument(data.document())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O documento já está cadastrado no banco.");
        }
        Hotel hotel = new Hotel(data.vhfId(), data.name(), data.document());
        hotelRepository.save(hotel);
    }

    public List<HotelGet> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(h -> new HotelGet(h.getId(), h.getVhfId(), h.getName(), h.getDocument()))
                .collect(Collectors.toList());
    }

    public HotelGet getById(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel não encontrado com esse id."));
        return new HotelGet(hotel.getId(), hotel.getVhfId(), hotel.getName(), hotel.getDocument());
    }

    public HotelGet edit(String id, HotelEdit data) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel não encontrado com esse id."));
        hotelRepository.findByDocument(data.document())
                .filter(h -> !h.getId().equals(id))
                .ifPresent(h -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe outro hotel cadastrado com esse documento.");
                });
        hotel.setName(data.name());
        hotel.setDocument(data.document());
        hotelRepository.save(hotel);
        return new HotelGet(hotel.getId(), hotel.getVhfId(), hotel.getName(), hotel.getDocument());
    }

    public void delete(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel não encontrado com esse id."));
        if (!hotel.getUsers().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem usuários vinculados a esse hotel.");
        }
        if (!hotel.getTickets().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem tickets vinculados a esse hotel.");
        }
        if (!hotel.getReservations().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem reservas vinculadas a esse hotel.");
        }
        if (!hotel.getReservationTypes().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem tipos de reservas vinculados a esse hotel.");
        }
        if (!hotel.getReservationPensions().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem tipos de pensão de reservas vinculados a esse hotel.");
        }
        hotelRepository.delete(hotel);
    }

    public void linkUser(HotelLinkUser data) {
        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o id " + data.userId()));
        Hotel hotel = hotelRepository.findById(data.hotelId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel não encontrado com o id " + data.hotelId()));
        user.getHotels().add(hotel);
        userRepository.save(user);
    }

    public List<UserGetWithoutHotels> getAllHotelUsers(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel não encontrado com o id " + hotelId));
        return hotel.getUsers().stream()
                .map(u -> new UserGetWithoutHotels(u.getId(), u.getLogin(), u.getName(), u.getLastName(),
                        u.getRoles().stream().map(r -> new br.com.laghettohoteis.api_biglietto.main.dtos.role.RoleGet(r.getName())).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public List<ReservationGet> getAllHotelReservations(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel não encontrado com o id " + hotelId));
        return hotel.getReservations().stream().map(res -> {
            List<GuestGet> guests = res.getGuests().stream()
                    .map(g -> new GuestGet(g.getId(), g.getName(), g.getLastName(), g.getEmail(), g.getDocument(), g.getCard(), g.getAgeRange().toString()))
                    .collect(Collectors.toList());
            return new ReservationGet(res.getId(), res.getNumber(), sdf.format(res.getArrival()), sdf.format(res.getDeparture()), res.getHousingUnit(),
                    new br.com.laghettohoteis.api_biglietto.main.dtos.reservation.ReservationStatusGet(res.getStatus().getId(), res.getStatus().getDescription()),
                    new TypeGet(res.getType().getId(), res.getType().getCode(), res.getType().getDescription()),
                    new PensionGet(res.getPension().getId(), res.getPension().getCode(), res.getPension().getDescription()),
                    guests, hotelId);
        }).collect(Collectors.toList());
    }

    public List<PensionGet> getAllHotelPensions(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel não encontrado com o id " + hotelId));
        return hotel.getReservationPensions().stream()
                .map(p -> new PensionGet(p.getId(), p.getCode(), p.getDescription()))
                .collect(Collectors.toList());
    }

    public String selectHotel(String hotelId, List<String> userHotelsIds, String token) {
        if (!userHotelsIds.contains(hotelId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não possui permissões com esse hotel.");
        }

        return tokenService.refreshToken(token, hotelId);
    }
}