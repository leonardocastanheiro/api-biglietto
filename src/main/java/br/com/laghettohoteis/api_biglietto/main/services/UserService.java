package br.com.laghettohoteis.api_biglietto.main.services;


import br.com.laghettohoteis.api_biglietto.main.domain.user.User;
import br.com.laghettohoteis.api_biglietto.main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public Optional<User> findById(String id) {
        return repository.findById(id);
    }


}
