package br.com.laghettohoteis.api_biglietto.authenticator.controller;

import br.com.laghettohoteis.api_biglietto.main.dtos.response.ResponseMessage;
import br.com.laghettohoteis.api_biglietto.authenticator.dto.AuthResponse;
import br.com.laghettohoteis.api_biglietto.authenticator.dto.LoginRequest;
import br.com.laghettohoteis.api_biglietto.authenticator.dto.UserCreate;
import br.com.laghettohoteis.api_biglietto.authenticator.service.TokenService;
import br.com.laghettohoteis.api_biglietto.main.domain.modification.Modification;
import br.com.laghettohoteis.api_biglietto.main.domain.user.Role;
import br.com.laghettohoteis.api_biglietto.main.domain.user.User;
import br.com.laghettohoteis.api_biglietto.main.repositories.ModificationRepository;
import br.com.laghettohoteis.api_biglietto.main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticatorController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> create(
            @RequestBody UserCreate data,
            @RequestHeader("Authorization") String authorizationHeader,
            Authentication authentication) {

        if (authentication == null || authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN_MASTER"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseMessage("Acesso negado. Apenas ADMIN_MASTER."));
        }

        if (repository.findByLogin(data.login()) != null) {
            return new ResponseEntity<>(new ResponseMessage("Usuário já cadastrado."), HttpStatus.BAD_REQUEST);
        }

        String token = authorizationHeader.replace("Bearer ", "");
        String userId = tokenService.getTokenInfo(token).claims().get("userId").toString();

        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Erro ao encontrar o usuário logado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User userLogged = userOptional.get();

        List<Role> roles;
        if (data.roles() != null) {
            roles = Role.fromStringList(data.roles());
        } else {
            roles = List.of(Role.DEFAULT);
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data, encryptedPassword, roles);

        repository.save(newUser);

        return new ResponseEntity<>(new ResponseMessage("Usuário criado com sucesso."), HttpStatus.CREATED);
    }

    @PostMapping("/init")
    public ResponseEntity<?> init() {
        UserCreate data = new UserCreate("admin","admin","Administrador","Geral", List.of("ADMIN_MASTER","ADMIN_HOTEL"));
        List<Role> roles;
        if (data.roles() != null) {
            roles = Role.fromStringList(data.roles());
        } else {
            roles = List.of(Role.DEFAULT);
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data, encryptedPassword, roles);

        repository.save(newUser);

        return new ResponseEntity<>(new ResponseMessage("Usuário criado com sucesso."), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest data) throws Exception {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        String token;
        try {
            token = tokenService.generateToken((User) auth.getPrincipal());
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
