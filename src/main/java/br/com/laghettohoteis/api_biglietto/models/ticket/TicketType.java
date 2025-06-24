package br.com.laghettohoteis.api_biglietto.models.ticket;

import br.com.laghettohoteis.api_biglietto.models.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.models.log.Modification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name = "ticketType")
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

    @OneToOne
    @NotNull
    Modification lastModification;
}
