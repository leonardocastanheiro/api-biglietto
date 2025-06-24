package br.com.laghettohoteis.api_biglietto.models.guest;

import br.com.laghettohoteis.api_biglietto.models.log.Modification;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @OneToOne
    @NotNull
    private Modification modification;
}
