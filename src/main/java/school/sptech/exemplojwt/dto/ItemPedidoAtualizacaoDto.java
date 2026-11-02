package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


@Schema(description = "Dados para atualizar um item do pedido. Informe ao menos um campo")
public class ItemPedidoAtualizacaoDto {

        @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
        @Schema(description = "Nova quantidade (mínimo 1). Se omitida, mantém a atual", example = "3")
        private Integer quantidade;

        @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
        @Schema(description = "Nova observação (máx. 255 caracteres). Se omitida, mantém a atual", example = "Ponto da carne: mal passado")
        private String observacao;

        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

        public String getObservacao() { return observacao; }
        public void setObservacao(String observacao) { this.observacao = observacao; }
}