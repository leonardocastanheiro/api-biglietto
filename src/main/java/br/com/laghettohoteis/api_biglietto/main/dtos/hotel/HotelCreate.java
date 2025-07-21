package br.com.laghettohoteis.api_biglietto.main.dtos.hotel;

import jakarta.validation.constraints.NotNull;

public record HotelCreate(Long vhfId, String name, String document) {
}
