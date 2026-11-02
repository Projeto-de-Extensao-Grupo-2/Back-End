package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de um item de pedido")
public class ItemPedidoListarDto {

    @Schema(description = "ID do item do pedido", example = "25")
    private Long id;
    @Schema(description = "ID do produto", example = "4")
    private Long produtoId;
    @Schema(description = "Nome do produto", example = "Hambúrguer Artesanal")
    private String produtoNome;
    @Schema(description = "Quantidade pedida", example = "2")
    private Integer quantidade;
    @Schema(description = "Preço unitário do produto no momento do pedido (R$)", example = "34.9")
    private Double precoUnitario;
    @Schema(description = "Observação do cliente", example = "Sem cebola")
    private String observacao;

    public ItemPedidoListarDto(Long id, Long produtoId, String produtoNome, Integer quantidade,
                               Double precoUnitario, String observacao) {
        this.id = id;
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.observacao = observacao;
    }

    public Long getId() { return id; }
    public Long getProdutoId() { return produtoId; }
    public String getProdutoNome() { return produtoNome; }
    public Integer getQuantidade() { return quantidade; }
    public Double getPrecoUnitario() { return precoUnitario; }
    public String getObservacao() { return observacao; }
}