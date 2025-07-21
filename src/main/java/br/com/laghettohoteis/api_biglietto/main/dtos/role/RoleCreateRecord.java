package br.com.laghettohoteis.api_biglietto.main.dtos.role;

import br.com.laghettohoteis.api_biglietto.main.dtos.modification.ModificationCreateRecord;

public record RoleCreateRecord(String name, ModificationCreateRecord modification) {
}
