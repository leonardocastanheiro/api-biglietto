package br.com.laghettohoteis.api_biglietto.main.repositories;

import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationPension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PensionRepository extends JpaRepository<ReservationPension, String> {

    boolean existsByCodeAndHotelId(String code, String hotelId);

    boolean existsByCodeAndHotel_IdAndIdNot(String code, String hotelId, String idToSkip);

    Optional<ReservationPension> findByIdAndHotelId(String id, String hotelId);

    List<ReservationPension> findAllByHotelId(String hotelId);
}
