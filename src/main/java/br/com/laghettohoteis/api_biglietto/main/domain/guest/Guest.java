package br.com.laghettohoteis.api_biglietto.main.domain.guest;

import br.com.laghettohoteis.api_biglietto.main.domain.modification.Modification;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.Reservation;
import br.com.laghettohoteis.api_biglietto.main.domain.ticket.Ticket;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guest")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    private Long vhfId;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false, unique = true)
    private String document;

    @Column(unique = true)
    private String card;

    @ManyToMany(mappedBy = "guests")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "guest")
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "guest")
    private List<Modification> modifications;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgeRange ageRange;
}
