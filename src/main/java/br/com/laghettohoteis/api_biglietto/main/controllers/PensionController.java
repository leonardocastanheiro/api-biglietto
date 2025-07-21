package br.com.laghettohoteis.api_biglietto.main.controllers;

import br.com.laghettohoteis.api_biglietto.authenticator.dto.TokenInfo;
import br.com.laghettohoteis.api_biglietto.authenticator.dto.TokenInfoExtractor;
import br.com.laghettohoteis.api_biglietto.authenticator.service.TokenService;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension.*;
import br.com.laghettohoteis.api_biglietto.main.dtos.response.ResponseMessage;
import br.com.laghettohoteis.api_biglietto.main.services.PensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/pension")
public class PensionController {

    @Autowired
    private PensionService pensionService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<PensionGet>> getAllByHotel(@RequestHeader("Authorization") String authHeader) {
        TokenInfo info = parseToken(authHeader);
        String hotelId = new TokenInfoExtractor(info)
                .getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum hotel selecionado."));

        List<PensionGet> list = pensionService.getAllByHotel(hotelId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{pensionId}")
    public ResponseEntity<PensionGet> getById(@RequestHeader("Authorization") String authHeader,
                                              @PathVariable String pensionId) {
        TokenInfo info = parseToken(authHeader);
        String hotelId = new TokenInfoExtractor(info)
                .getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum hotel selecionado."));

        PensionGet dto = pensionService.getById(pensionId, hotelId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> create(@RequestHeader("Authorization") String authHeader,
                                                  @RequestBody PensionCreate data) {
        TokenInfo info = parseToken(authHeader);
        String hotelId = new TokenInfoExtractor(info)
                .getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum hotel selecionado."));

        pensionService.create(data, hotelId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage("Tipo de Pensão criado com sucesso"));
    }

    @PutMapping("/{pensionId}")
    public ResponseEntity<PensionGet> edit(@RequestHeader("Authorization") String authHeader,
                                           @PathVariable String pensionId,
                                           @RequestBody PensionPut data) {
        TokenInfo info = parseToken(authHeader);
        String hotelId = new TokenInfoExtractor(info)
                .getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum hotel selecionado."));

        PensionGet updated = pensionService.edit(pensionId, data, hotelId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{pensionId}")
    public ResponseEntity<ResponseMessage> delete(@RequestHeader("Authorization") String authHeader,
                                                  @PathVariable String pensionId) {
        TokenInfo info = parseToken(authHeader);
        String hotelId = new TokenInfoExtractor(info)
                .getSelectedHotelId()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum hotel selecionado."));

        pensionService.delete(pensionId, hotelId);
        return ResponseEntity.ok(new ResponseMessage("Pensão excluída com sucesso."));
    }

    private TokenInfo parseToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido.");
        }
        return tokenService.getTokenInfo(header.substring(7));
    }
}