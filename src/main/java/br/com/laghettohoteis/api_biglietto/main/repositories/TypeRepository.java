package br.com.laghettohoteis.api_biglietto.main.repositories;

import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<ReservationType, String> {

    boolean existsByCodeAndHotelId(String code, String hotelId);

    Optional<ReservationType> findByIdAndHotelId(String id, String hotelId);

    boolean existsByCodeAndHotel_IdAndIdNot(String code, String hotelId, String idToSkip);
}
