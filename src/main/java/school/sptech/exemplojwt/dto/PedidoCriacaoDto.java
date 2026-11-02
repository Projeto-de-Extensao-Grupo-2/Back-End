package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para abertura de um pedido")
public class PedidoCriacaoDto {

        @NotNull(message = "A mesa é obrigatória")
        @Schema(description = "ID da mesa (não confundir com o número da mesa)", example = "1")
        private Long mesaId;

        @Schema(description = "ID do garçom responsável (opcional). Deve ser um usuário ativo com cargo `GARCOM`", example = "2")
        private Long garcomId;

        public Long getMesaId() { return mesaId; }
        public void setMesaId(Long mesaId) { this.mesaId = mesaId; }

        public Long getGarcomId() { return garcomId; }
        public void setGarcomId(Long garcomId) { this.garcomId = garcomId; }
}