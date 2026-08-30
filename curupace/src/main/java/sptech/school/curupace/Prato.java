package sptech.school.curupace;

import java.math.BigDecimal;

public class Prato {
    private String nome;
    private String categoria;
    private Double preco;
    private Integer codigo;
    private String porcao;
    private String descricao;
    private String tags;

    public Prato() {
    }

    public Prato(String nome, String categoria, Double preco, Integer codigo, String porcao, String descricao, String tags) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.codigo = codigo;
        this.porcao = porcao;
        this.descricao = descricao;
        this.tags = tags;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getPorcao() {
        return porcao;
    }

    public void setPorcao(String porcao) {
        this.porcao = porcao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
