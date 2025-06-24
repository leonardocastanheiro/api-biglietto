package br.com.laghettohoteis.api_biglietto.models.user;

import br.com.laghettohoteis.api_biglietto.models.log.Modification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne
    @NotNull
    private Modification lastModification;
}
