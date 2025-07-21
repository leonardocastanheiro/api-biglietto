package br.com.laghettohoteis.api_biglietto.main.domain.hotel;

import br.com.laghettohoteis.api_biglietto.main.domain.modification.Modification;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.Reservation;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationPension;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationType;
import br.com.laghettohoteis.api_biglietto.main.domain.ticket.Ticket;
import br.com.laghettohoteis.api_biglietto.main.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Hotel implements Comparable<Hotel> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private Long vhfId;

    @NotNull
    private String name;

    @NotNull
    private String document;

    @OneToMany(mappedBy = "hotel")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<ReservationType> reservationTypes = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<ReservationPension> reservationPensions = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToMany(mappedBy = "hotels")
    private List<User> users;

    @OneToMany(mappedBy = "hotel")
    private List<Modification> modifications;

    public Hotel(Long vhfId, String name, String document) {
        this.vhfId = vhfId;
        this.name = name;
        this.document = document;
    }

    public Hotel(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(Hotel o) {
        return this.getId().compareTo(o.getId());
    }
}
