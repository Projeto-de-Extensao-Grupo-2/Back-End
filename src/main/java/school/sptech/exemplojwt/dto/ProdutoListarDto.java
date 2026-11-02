package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de um produto do cardápio")
public class ProdutoListarDto {

    @Schema(description = "ID do produto", example = "4")
    private Long id;
    @Schema(description = "Nome do produto", example = "Hambúrguer Artesanal")
    private String nome;
    @Schema(description = "Descrição do produto", example = "Pão brioche, blend 180g, queijo cheddar e molho da casa")
    private String descricao;
    @Schema(description = "Preço em reais", example = "34.9")
    private Double preco;
    @Schema(description = "ID da categoria", example = "1")
    private Long categoriaId;
    @Schema(description = "Nome da categoria", example = "Lanches")
    private String categoriaNome;
    @Schema(description = "Indica se o produto está disponível", example = "true")
    private boolean ativo;

    public ProdutoListarDto(Long id, String nome, String descricao, Double preco,
                            Long categoriaId, String categoriaNome, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Double getPreco() { return preco; }
    public Long getCategoriaId() { return categoriaId; }
    public String getCategoriaNome() { return categoriaNome; }
    public boolean isAtivo() { return ativo; }
}