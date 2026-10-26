package school.sptech.smartplate.dto;

import jakarta.validation.constraints.NotNull;

public class PedidoRequestDTO {

    @NotNull
    private Long mesaId;

    private Long garcomId;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(Long mesaId, Long garcomId) {
        this.mesaId = mesaId;
        this.garcomId = garcomId;
    }

    public Long getMesaId() {
        return mesaId;
    }

    public void setMesaId(Long mesaId) {
        this.mesaId = mesaId;
    }

    public Long getGarcomId() {
        return garcomId;
    }

    public void setGarcomId(Long garcomId) {
        this.garcomId = garcomId;
    }
}
