package br.com.laghettohoteis.api_biglietto.main.domain.reservation;

import br.com.laghettohoteis.api_biglietto.main.domain.guest.Guest;
import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.modification.Modification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "reservation_guest",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private List<Guest> guests = new ArrayList<>();

    @OneToMany(mappedBy = "reservation")
    private List<Modification> modifications;
}
