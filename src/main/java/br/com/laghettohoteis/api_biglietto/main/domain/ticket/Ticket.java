package br.com.laghettohoteis.api_biglietto.main.domain.ticket;

import br.com.laghettohoteis.api_biglietto.main.domain.guest.Guest;
import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.modification.Modification;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private Boolean usable = true;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private TicketType type;

    @NotNull
    private Date beginningOfValidity;

    @NotNull
    private Date endOfValidity;

    @ManyToOne
    private Guest guest;

    @ManyToOne
    private Hotel hotel;

    @OneToMany(mappedBy = "ticket")
    private List<Modification> modifications;
}
