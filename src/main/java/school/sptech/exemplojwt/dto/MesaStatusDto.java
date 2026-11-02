package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

@Schema(description = "Novo status da mesa")
public class MesaStatusDto {

        @NotNull(message = "O status é obrigatório")
        @Schema(description = "Novo status da mesa", example = "OCUPADA")
        private StatusMesa status;

        public StatusMesa getStatus() { return status; }
        public void setStatus(StatusMesa status) { this.status = status; }
}