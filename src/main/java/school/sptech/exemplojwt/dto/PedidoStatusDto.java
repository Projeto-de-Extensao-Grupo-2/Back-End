package school.sptech.exemplojwt.dto;

import jakarta.validation.constraints.NotNull;

public class PedidoStatusDto {

        @NotNull(message = "O status é obrigatório")
        private StatusPedido status;

        public StatusPedido getStatus() { return status; }
        public void setStatus(StatusPedido status) { this.status = status; }
}