package br.com.laghettohoteis.api_biglietto.main.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Role {
    ADMIN_MASTER("admin_master"),
    ADMIN_HOTEL("admin_hotel"),
    RESERVATION("reservation"),
    OPERATION("operation"),
    RECEPTION("reception"),
    DEFAULT("default");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @JsonCreator
    public static Role fromString(String role) {
        return valueOf(role.toUpperCase());
    }

    public static List<Role> fromStringList(List<String> strings) {
        List<Role> roles = new ArrayList<>();

        for(String string : strings) {
            roles.add(Role.fromString(string));
        }

        return roles;
    }
}