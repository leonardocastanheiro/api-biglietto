package br.com.laghettohoteis.api_biglietto.main.repositories;

import br.com.laghettohoteis.api_biglietto.main.domain.reservation.PensionTicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PensionTicketTypeRepository extends JpaRepository<PensionTicketType, String> {
    boolean existsByTypeIdAndPensionId(String typeId, String pensionId);

    Optional<PensionTicketType> findByIdAndTypeIdAndPensionId(String id, String typeId, String pensionId);
}
