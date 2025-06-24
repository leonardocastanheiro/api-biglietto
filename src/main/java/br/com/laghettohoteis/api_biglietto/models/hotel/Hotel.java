package br.com.laghettohoteis.api_biglietto.models.hotel;

import br.com.laghettohoteis.api_biglietto.models.log.Modification;
import br.com.laghettohoteis.api_biglietto.models.reservation.Reservation;
import br.com.laghettohoteis.api_biglietto.models.reservation.ReservationPension;
import br.com.laghettohoteis.api_biglietto.models.reservation.ReservationType;
import br.com.laghettohoteis.api_biglietto.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hotel")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @NotNull
    private Long vhfId;

    @NotNull
    private String name;

    @NotNull
    private String document;

    @ManyToMany(mappedBy = "hotels")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<ReservationType> reservationTypes = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<ReservationPension> reservationPensions = new ArrayList<>();

    @OneToOne
    @NotNull
    private Modification lastModification;
}
