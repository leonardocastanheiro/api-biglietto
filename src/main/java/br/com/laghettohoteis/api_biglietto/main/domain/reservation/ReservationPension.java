package br.com.laghettohoteis.api_biglietto.main.domain.reservation;

import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.modification.Modification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "reservation_pension")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ReservationPension {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private String code;

    @NotNull
    private String description;

    @NotNull
    @ManyToOne
    private Hotel hotel;

    @OneToMany(mappedBy = "pension")
    private List<Modification> modifications;

    public ReservationPension(String code, String description, Hotel hotel) {
        this.code = code;
        this.description = description;
        this.hotel = hotel;
    }
}
