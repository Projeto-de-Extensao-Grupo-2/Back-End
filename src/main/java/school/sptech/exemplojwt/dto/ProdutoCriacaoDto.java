package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro/atualização de um produto")
public class ProdutoCriacaoDto {

        @NotNull(message = "A categoria é obrigatória")
        @Schema(description = "ID da categoria do produto", example = "1")
        private Long categoriaId;

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        @Schema(description = "Nome do produto (máx. 100 caracteres)", example = "Hambúrguer Artesanal")
        private String nome;

        @Schema(description = "Descrição do produto", example = "Pão brioche, blend 180g, queijo cheddar e molho da casa")
        private String descricao;

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        @Schema(description = "Preço em reais (maior que zero)", example = "34.9")
        private Double preco;

        @Schema(description = "Indica se o produto está disponível. Na criação, se omitido assume `true`; na atualização, se omitido mantém o valor atual", example = "true")
        private Boolean ativo;

        public Long getCategoriaId() { return categoriaId; }
        public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }

        public Double getPreco() { return preco; }
        public void setPreco(Double preco) { this.preco = preco; }

        public Boolean getAtivo() { return ativo; }
        public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}