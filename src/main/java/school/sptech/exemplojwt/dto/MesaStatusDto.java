package school.sptech.exemplojwt.dto;

import jakarta.validation.constraints.NotNull;

public class MesaStatusDto {

        @NotNull(message = "O status é obrigatório")
        private StatusMesa status;

        public StatusMesa getStatus() { return status; }
        public void setStatus(StatusMesa status) { this.status = status; }
}