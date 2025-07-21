package br.com.laghettohoteis.api_biglietto.main.controllers;

import br.com.laghettohoteis.api_biglietto.authenticator.dto.AuthResponse;
import br.com.laghettohoteis.api_biglietto.authenticator.dto.TokenInfo;
import br.com.laghettohoteis.api_biglietto.authenticator.dto.TokenInfoExtractor;
import br.com.laghettohoteis.api_biglietto.authenticator.service.TokenService;
import br.com.laghettohoteis.api_biglietto.main.dtos.hotel.*;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.ReservationGet;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension.PensionGet;
import br.com.laghettohoteis.api_biglietto.main.dtos.response.ResponseMessage;
import br.com.laghettohoteis.api_biglietto.main.dtos.user.UserGetWithoutHotels;
import br.com.laghettohoteis.api_biglietto.main.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<ResponseMessage> create(@RequestBody HotelCreate data, Authentication auth) {
        checkMaster(auth);
        hotelService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage("Hotel criado com sucesso."));
    }

    @GetMapping
    public ResponseEntity<List<HotelGet>> getAll(Authentication auth) {
        checkMaster(auth);
        List<HotelGet> list = hotelService.getAllHotels();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id, Authentication auth, @RequestHeader("Authorization") String authHeader) {
        TokenInfo info = parseToken(authHeader);

        TokenInfoExtractor infoExtractor = new TokenInfoExtractor(info);

        if(infoExtractor.getHotelsId().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        checkAccess(auth, infoExtractor.getHotelsId().get(), id);
        HotelGet dto = hotelService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelGet> edit(@PathVariable String id, @RequestBody HotelEdit data, Authentication auth, @RequestHeader("Authorization") String authHeader) {
        checkMaster(auth);
        HotelGet updated = hotelService.edit(id, data);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable String id, Authentication auth) {
        checkMaster(auth);
        hotelService.delete(id);
        return ResponseEntity.ok(new ResponseMessage("Hotel excluído com sucesso."));
    }

    @PostMapping("/link")
    public ResponseEntity<ResponseMessage> linkUser(@RequestBody HotelLinkUser data, Authentication auth) {
        checkMaster(auth);
        hotelService.linkUser(data);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage("Vínculo criado com sucesso."));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserGetWithoutHotels>> getUsers(@PathVariable String id, Authentication auth) {
        checkMaster(auth);
        List<UserGetWithoutHotels> users = hotelService.getAllHotelUsers(id);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<ReservationGet>> getReservations(@PathVariable String id, Authentication auth) {
        checkMaster(auth);
        List<ReservationGet> res = hotelService.getAllHotelReservations(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}/pensions")
    public ResponseEntity<List<PensionGet>> getPensions(@PathVariable String id, Authentication auth) {
        checkMaster(auth);
        List<PensionGet> pensions = hotelService.getAllHotelPensions(id);
        return ResponseEntity.ok(pensions);
    }

    @PostMapping("/{id}/select")
    public ResponseEntity<?> selectHotel(@PathVariable String id, @RequestHeader("Authorization") String authHeader, Authentication auth) {
        TokenInfo info = parseToken(authHeader);
        TokenInfoExtractor infoExtractor = new TokenInfoExtractor(info);

        if(infoExtractor.getHotelsId().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String newToken = hotelService.selectHotel(id, infoExtractor.getHotelsId().get(), extractToken(authHeader));
        return ResponseEntity.ok(new AuthResponse(newToken));
    }

    private void checkMaster(Authentication auth) {
        if (auth == null || auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN_MASTER"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado. Apenas administradores totais.");
        }
    }

    private void checkAccess(Authentication auth, List<String> allowed, String hotelId) {
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN_MASTER")) && !allowed.contains(hotelId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este hotel.");
        }
    }

    private TokenInfo parseToken(String header) {
        String token = extractToken(header);
        return tokenService.getTokenInfo(token);
    }

    private String extractToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido.");
        }
        return header.substring(7);
    }
}