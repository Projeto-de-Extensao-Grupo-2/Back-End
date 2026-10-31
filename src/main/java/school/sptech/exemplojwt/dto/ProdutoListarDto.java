package school.sptech.exemplojwt.dto;

public class ProdutoListarDto {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Long categoriaId;
    private String categoriaNome;
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