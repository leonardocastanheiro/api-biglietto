package br.com.laghettohoteis.api_biglietto.authenticator.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
public record TokenInfo(
        String subject,
        String issuer,
        Instant expiresAt,
        Map<String, Object> claims
) {
    @Override
    public String toString() {
        String formattedClaims;
        if (claims == null) {
            formattedClaims = "null";
        } else if (claims.isEmpty()) {
            formattedClaims = "{}";
        } else {
            formattedClaims = claims.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining(",\n        ", "{\n        ", "\n    }"));
        }

        return String.format(
                "TokenInfo[\n" +
                        "    subject='%s',\n" +
                        "    issuer='%s',\n" +
                        "    expiresAt=%s,\n" +
                        "    claims=%s\n" +
                        "]",
                subject,
                issuer,
                expiresAt,
                formattedClaims
        );
    }
}
