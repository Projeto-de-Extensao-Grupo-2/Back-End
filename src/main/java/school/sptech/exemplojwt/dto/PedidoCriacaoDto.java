package school.sptech.exemplojwt.dto;

import jakarta.validation.constraints.NotNull;

public class PedidoCriacaoDto {

        @NotNull(message = "A mesa é obrigatória")
        private Long mesaId;

        private Long garcomId;

        public Long getMesaId() { return mesaId; }
        public void setMesaId(Long mesaId) { this.mesaId = mesaId; }

        public Long getGarcomId() { return garcomId; }
        public void setGarcomId(Long garcomId) { this.garcomId = garcomId; }
}