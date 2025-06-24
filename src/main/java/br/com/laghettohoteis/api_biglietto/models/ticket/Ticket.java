package br.com.laghettohoteis.api_biglietto.models.ticket;

import br.com.laghettohoteis.api_biglietto.models.guest.Guest;
import br.com.laghettohoteis.api_biglietto.models.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.models.log.Modification;
import br.com.laghettohoteis.api_biglietto.models.reservation.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

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

    @NotNull
    @OneToOne
    private Modification lastModification;
}
