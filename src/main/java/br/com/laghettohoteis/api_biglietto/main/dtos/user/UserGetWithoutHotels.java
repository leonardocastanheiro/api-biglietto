package br.com.laghettohoteis.api_biglietto.main.dtos.user;

import br.com.laghettohoteis.api_biglietto.main.dtos.hotel.HotelGet;
import br.com.laghettohoteis.api_biglietto.main.dtos.role.RoleGet;

import java.util.List;

public record UserGetWithoutHotels(String id, String login, String name, String lastName, List<RoleGet> roles) {}
