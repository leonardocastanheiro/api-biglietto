package br.com.laghettohoteis.api_biglietto.main.dtos.reservation;

import br.com.laghettohoteis.api_biglietto.main.dtos.guest.GuestGet;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension.PensionGet;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.type.TypeGet;

import java.util.List;

public record ReservationGet(String id, String number, String arrival, String departure, String housingUnit,
                             ReservationStatusGet status, TypeGet type, PensionGet pension, List<GuestGet> guests, String hotelId) {
}
