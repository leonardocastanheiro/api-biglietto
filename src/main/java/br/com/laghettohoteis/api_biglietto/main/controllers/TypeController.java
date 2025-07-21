package br.com.laghettohoteis.api_biglietto.main.controllers;

import br.com.laghettohoteis.api_biglietto.authenticator.dto.TokenInfo;
import br.com.laghettohoteis.api_biglietto.authenticator.dto.TokenInfoExtractor;
import br.com.laghettohoteis.api_biglietto.authenticator.service.TokenService;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.type.*;
import br.com.laghettohoteis.api_biglietto.main.dtos.response.ResponseMessage;
import br.com.laghettohoteis.api_biglietto.main.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<TypeGet>> getAllOfHotel(@RequestHeader("Authorization") String authHeader) {
        TokenInfo info = parseToken(authHeader);
        TokenInfoExtractor ext = new TokenInfoExtractor(info);
        String hotelId = ext.getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Nenhum hotel selecionado."));

        var list = typeService.getAllHotelTypes(hotelId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<TypeGet> get(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable String typeId) {
        TokenInfo info = parseToken(authHeader);
        TokenInfoExtractor ext = new TokenInfoExtractor(info);
        String hotelId = ext.getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Nenhum hotel selecionado."));

        var dto = typeService.getHotelType(typeId, hotelId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> create(@RequestHeader("Authorization") String authHeader,
                                                  @RequestBody TypeCreate data) {
        TokenInfo info = parseToken(authHeader);
        TokenInfoExtractor ext = new TokenInfoExtractor(info);
        String hotelId = ext.getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Nenhum hotel selecionado."));

        typeService.create(data, hotelId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage("Tipo de reserva criado com sucesso."));
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<TypeGet> edit(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable String typeId,
                                        @RequestBody TypePut data) {
        TokenInfo info = parseToken(authHeader);
        TokenInfoExtractor ext = new TokenInfoExtractor(info);
        String hotelId = ext.getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Nenhum hotel selecionado."));

        var updated = typeService.edit(typeId, data, hotelId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<ResponseMessage> delete(@RequestHeader("Authorization") String authHeader,
                                                  @PathVariable String typeId) {
        TokenInfo info = parseToken(authHeader);
        TokenInfoExtractor ext = new TokenInfoExtractor(info);
        String hotelId = ext.getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Nenhum hotel selecionado."));

        typeService.delete(typeId, hotelId);
        return ResponseEntity.ok(new ResponseMessage("Tipo de reserva excluído com sucesso."));
    }

    private TokenInfo parseToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido.");
        }
        String token = header.substring(7);
        return tokenService.getTokenInfo(token);
    }
}