package br.com.laghettohoteis.api_biglietto.main.repositories;

import br.com.laghettohoteis.api_biglietto.main.domain.ticket.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketTypeRepository extends JpaRepository<TicketType, String> {
    Optional<TicketType> findByIdAndHotelId(String id, String hotelId);
}
