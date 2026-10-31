package school.sptech.exemplojwt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fichas_tecnicas")
public class FichaTecnica {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    private Double quantidade_usada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Double getQuantidade_usada() {
        return quantidade_usada;
    }

    public void setQuantidade_usada(Double quantidade_usada) {
        this.quantidade_usada = quantidade_usada;
    }
}