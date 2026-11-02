package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

@Schema(description = "Novo status do pedido")
public class PedidoStatusDto {

        @NotNull(message = "O status é obrigatório")
        @Schema(description = "Novo status do pedido", example = "PREPARANDO")
        private StatusPedido status;

        public StatusPedido getStatus() { return status; }
        public void setStatus(StatusPedido status) { this.status = status; }
}