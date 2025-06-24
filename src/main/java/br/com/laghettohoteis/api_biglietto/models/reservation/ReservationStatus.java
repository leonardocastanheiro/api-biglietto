package br.com.laghettohoteis.api_biglietto.models.reservation;

import br.com.laghettohoteis.api_biglietto.models.log.Modification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "reservationStatus")
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

    @NotNull
    @OneToOne
    private Modification lastModification;
}
