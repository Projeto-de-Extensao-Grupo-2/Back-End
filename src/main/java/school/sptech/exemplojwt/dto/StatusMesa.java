package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status da mesa: `LIVRE` ou `OCUPADA`")
public enum StatusMesa {
    LIVRE,
    OCUPADA
}
