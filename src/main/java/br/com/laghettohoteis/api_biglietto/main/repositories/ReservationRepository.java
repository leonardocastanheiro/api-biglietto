package br.com.laghettohoteis.api_biglietto.main.repositories;

import br.com.laghettohoteis.api_biglietto.main.domain.reservation.Reservation;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationPension;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    boolean existsByType(ReservationType type);

    boolean existsByPension(ReservationPension pension);
}
