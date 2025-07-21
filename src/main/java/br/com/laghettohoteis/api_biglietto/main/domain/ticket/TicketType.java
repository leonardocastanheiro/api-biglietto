package br.com.laghettohoteis.api_biglietto.main.domain.ticket;

import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.modification.Modification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "ticket_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @NotNull
    String description;

    @ManyToOne
    Hotel hotel;

    @OneToMany(mappedBy = "type")
    List<Ticket> tickets;

    @OneToMany(mappedBy = "ticketType")
    private List<Modification> modifications;
}
