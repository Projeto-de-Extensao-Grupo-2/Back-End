package school.sptech.exemplojwt.dto;

public class FichaTecnicaListarDto {

    private String produto;
    private String ingrediente;
    private Double quantidadeUsada;
    private String unidadeMedida;

    public FichaTecnicaListarDto(String produto, String ingrediente,
                                 Double quantidadeUsada, String unidadeMedida) {
        this.produto = produto;
        this.ingrediente = ingrediente;
        this.quantidadeUsada = quantidadeUsada;
        this.unidadeMedida = unidadeMedida;
    }

    public String getProduto() { return produto; }
    public String getIngrediente() { return ingrediente; }
    public Double getQuantidadeUsada() { return quantidadeUsada; }
    public String getUnidadeMedida() { return unidadeMedida; }
}