package br.com.laghettohoteis.api_biglietto.models.reservation;

import br.com.laghettohoteis.api_biglietto.models.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.models.log.Modification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "reservationPension")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ReservationPension {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String code;

    @NotNull
    private String description;

    @NotNull
    private Hotel hotel;

    @NotNull
    @OneToOne
    private Modification lastModification;
}
