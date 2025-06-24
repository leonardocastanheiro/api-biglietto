package br.com.laghettohoteis.api_biglietto.models.log;

import br.com.laghettohoteis.api_biglietto.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "modification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Modification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Timestamp dateLastModification;

    @NotNull
    private String descriptionLastModification;

    @ManyToOne
    @NotNull
    private User userModification;
}
