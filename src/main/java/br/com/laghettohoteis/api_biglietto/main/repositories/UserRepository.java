package br.com.laghettohoteis.api_biglietto.main.repositories;

import br.com.laghettohoteis.api_biglietto.main.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}
