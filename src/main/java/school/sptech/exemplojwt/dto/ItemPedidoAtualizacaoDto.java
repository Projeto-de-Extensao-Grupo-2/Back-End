package school.sptech.exemplojwt.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


public class ItemPedidoAtualizacaoDto {

        @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
        private Integer quantidade;

        @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
        private String observacao;

        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

        public String getObservacao() { return observacao; }
        public void setObservacao(String observacao) { this.observacao = observacao; }
}