package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Perfil do usuário. `GARCOM` atende mesas; `GESTOR` administra o restaurante")
public enum Cargo {
    GARCOM,
    GESTOR
}
