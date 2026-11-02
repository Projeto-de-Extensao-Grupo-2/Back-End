package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para adicionar um item a um pedido")
public class ItemPedidoCriacaoDto {

        @NotNull(message = "O pedido é obrigatório")
        @Schema(description = "ID do pedido que receberá o item", example = "10")
        private Long pedidoId;

        @NotNull(message = "O produto é obrigatório")
        @Schema(description = "ID do produto (deve estar ativo)", example = "4")
        private Long produtoId;

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
        @Schema(description = "Quantidade do produto (mínimo 1)", example = "2")
        private Integer quantidade;

        @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
        @Schema(description = "Observação do cliente (máx. 255 caracteres)", example = "Sem cebola")
        private String observacao;

        public Long getPedidoId() { return pedidoId; }
        public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

        public Long getProdutoId() { return produtoId; }
        public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }

        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

        public String getObservacao() { return observacao; }
        public void setObservacao(String observacao) { this.observacao = observacao; }
}