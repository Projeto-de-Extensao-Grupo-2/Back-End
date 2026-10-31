package school.sptech.exemplojwt.dto;

public class ItemPedidoListarDto {

    private Long id;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private Double precoUnitario;
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