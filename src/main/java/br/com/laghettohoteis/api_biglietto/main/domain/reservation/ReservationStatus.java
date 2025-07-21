package br.com.laghettohoteis.api_biglietto.main.domain.reservation;

import br.com.laghettohoteis.api_biglietto.main.domain.modification.Modification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "reservation_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ReservationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "status")
    private List<Modification> modifications;
}
