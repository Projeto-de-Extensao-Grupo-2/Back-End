package school.sptech.smartplate.dto;

import jakarta.validation.constraints.NotNull;
import school.sptech.smartplate.enums.StatusPedido;

public class StatusPedidoRequestDTO {

    @NotNull
    private StatusPedido status;

    public StatusPedidoRequestDTO() {
    }

    public StatusPedidoRequestDTO(StatusPedido status) {
        this.status = status;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}
