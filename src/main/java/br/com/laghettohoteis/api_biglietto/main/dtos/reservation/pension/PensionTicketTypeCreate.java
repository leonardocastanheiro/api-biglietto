package br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension;

public record PensionTicketTypeCreate(
        String hotelId,
        String pensionId,
        String ticketTypeId,
        Long arrivalDay,
        Long commonDay,
        Long departureDay
) {}