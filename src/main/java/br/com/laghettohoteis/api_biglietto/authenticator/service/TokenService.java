package br.com.laghettohoteis.api_biglietto.authenticator.service;

import br.com.laghettohoteis.api_biglietto.authenticator.dto.TokenInfo;
import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.user.Role;
import br.com.laghettohoteis.api_biglietto.main.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) throws Exception {
        if (user == null) {
            throw new Exception("Usuário nulo enviado na geração de Token");
        }

        List<String> hotelsId = new ArrayList<>();

        for(Hotel hotel : user.getHotels()) {
            hotelsId.add(hotel.getId());
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            List<String> roleNames = user.getRoles().stream()
                    .map(Role::name)
                    .collect(Collectors.toList());

            return JWT.create()
                    .withIssuer("biglietto-api-auth")
                    .withSubject(user.getLogin())
                    .withExpiresAt(this.genExpirationDate())
                    .withClaim("roles", roleNames)
                    .withClaim("userId", user.getId())
                    .withClaim("hotelsId", hotelsId)
                    .withClaim("selectedHotelId", "")
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("biglietto-api-auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    public TokenInfo getTokenInfo(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("biglietto-api-auth")
                    .build()
                    .verify(token);

            List<String> roleNames = jwt.getClaim("roles").asList(String.class);
            if (roleNames == null) {
                roleNames = List.of();
            }
            List<Role> roles = Role.fromStringList(roleNames);

            Map<String, Object> claims = new HashMap<>();
            claims.put("issuer", jwt.getIssuer());
            claims.put("expiresAt", jwt.getExpiresAt());
            claims.put("roles", roleNames);
            claims.put("userId", jwt.getClaim("userId").asString());
            claims.put("hotelsId",jwt.getClaim("hotelsId").asList(String.class));
            claims.put("selectedHotelId",jwt.getClaim("selectedHotelId").asString());

            return new TokenInfo(
                    jwt.getSubject(),
                    jwt.getIssuer(),
                    jwt.getExpiresAt().toInstant(),
                    claims
            );
        } catch (JWTVerificationException ex) {
            throw new RuntimeException("Token inválido ou expirado", ex);
        }
    }

    public String refreshToken(String oldToken, String newSelectedHotelId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decoded = JWT.require(algorithm)
                    .withIssuer("biglietto-api-auth")
                    .build()
                    .verify(oldToken);

            String subject       = decoded.getSubject();
            List<String> roles   = decoded.getClaim("roles").asList(String.class);
            String userId        = decoded.getClaim("userId").asString();
            List<String> hotels  = decoded.getClaim("hotelsId").asList(String.class);
            String oldSelected   = decoded.getClaim("selectedHotelId").asString();

            String selectedHotelId = (newSelectedHotelId != null && !newSelectedHotelId.isBlank())
                    ? newSelectedHotelId
                    : oldSelected;

            Instant expiresAt = genExpirationDate();

            return JWT.create()
                    .withIssuer("biglietto-api-auth")
                    .withSubject(subject)
                    .withExpiresAt(expiresAt)
                    .withClaim("roles", roles)
                    .withClaim("userId", userId)
                    .withClaim("hotelsId", hotels)
                    .withClaim("selectedHotelId", selectedHotelId)
                    .sign(algorithm);

        } catch (JWTVerificationException | JWTCreationException e) {
            throw new RuntimeException("Erro ao renovar o token JWT", e);
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
