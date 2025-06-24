package br.com.laghettohoteis.api_biglietto.models.user;
import br.com.laghettohoteis.api_biglietto.models.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.models.log.Modification;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @ManyToMany(mappedBy = "users")
    private List<Role> roles;

    @ManyToMany(mappedBy = "users")
    private List<Hotel> hotels;

    @NotNull
    @OneToOne
    private Modification lastModification;
}
