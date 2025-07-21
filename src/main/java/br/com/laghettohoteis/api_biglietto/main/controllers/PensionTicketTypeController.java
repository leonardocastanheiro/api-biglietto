package br.com.laghettohoteis.api_biglietto.main.controllers;

import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension.PensionTicketTypeCreate;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension.PensionTicketTypeGet;
import br.com.laghettohoteis.api_biglietto.main.services.PensionTicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pension-ticket-types")
public class PensionTicketTypeController {

    @Autowired
    private PensionTicketTypeService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PensionTicketTypeCreate dto) {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<PensionTicketTypeGet> edit(@RequestBody PensionTicketTypeGet dto) {
        PensionTicketTypeGet updated = service.edit(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
