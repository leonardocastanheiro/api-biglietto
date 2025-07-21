package br.com.laghettohoteis.api_biglietto.authenticator.dto;

import java.util.List;
import java.util.Optional;

public class TokenInfoExtractor {
    private final TokenInfo tokenInfo;

    public TokenInfoExtractor(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public Optional<String> getUserId() {
        Object val = tokenInfo.claims().get("userId");
        return Optional.ofNullable(val)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(s -> !s.isBlank());
    }

    public Optional<List<String>> getRoles() {
        Object val = tokenInfo.claims().get("roles");
        if (val instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) val;
            return Optional.of(roles).filter(l -> !l.isEmpty());
        }
        return Optional.empty();
    }

    public Optional<List<String>> getHotelsId() {
        Object val = tokenInfo.claims().get("hotelsId");
        if (val instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<String> hotels = (List<String>) val;
            return Optional.of(hotels).filter(l -> !l.isEmpty());
        }
        return Optional.empty();
    }

    public Optional<String> getSelectedHotelId() {
        Object val = tokenInfo.claims().get("selectedHotelId");
        return Optional.ofNullable(val)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(s -> !s.isBlank());
    }
}

