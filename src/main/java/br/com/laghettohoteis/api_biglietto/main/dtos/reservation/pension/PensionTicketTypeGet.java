package br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension;

public record PensionTicketTypeGet(
        String id,
        String pensionId,
        String ticketTypeId,
        Long arrivalDay,
        Long commonDay,
        Long departureDay
) {}