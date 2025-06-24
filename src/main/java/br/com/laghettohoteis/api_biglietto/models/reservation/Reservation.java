package br.com.laghettohoteis.api_biglietto.models.reservation;

import br.com.laghettohoteis.api_biglietto.models.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.models.log.Modification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String number;

    @NotNull
    private Date arrival;

    @NotNull
    private Date departure;

    @NotNull
    private String housingUnit;

    @ManyToOne
    @NotNull
    private ReservationStatus status;

    @ManyToOne
    @NotNull
    private ReservationType type;

    @ManyToOne
    @NotNull
    private ReservationPension pension;

    @ManyToOne
    @NotNull
    private Hotel hotel;

    @OneToOne
    @NotNull
    private Modification lastModification;
}
