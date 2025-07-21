package br.com.laghettohoteis.api_biglietto.main.dtos.reservation.reservation;

import java.util.List;

public record ReservationCreate(String number, String arrival, String departure, String housingUnit, Long statusId,
                                String typeId, String pensionId, List<String> guests, String hotelId) {
}
