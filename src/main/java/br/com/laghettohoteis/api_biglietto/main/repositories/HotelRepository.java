package br.com.laghettohoteis.api_biglietto.main.repositories;

import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, String> {
    Optional<Hotel> findByVhfId(Long vhfId);

    boolean existsByVhfId(Long vhfId);

    boolean existsByDocument(String document);

    Optional<Hotel> findByDocument(String document);
}
