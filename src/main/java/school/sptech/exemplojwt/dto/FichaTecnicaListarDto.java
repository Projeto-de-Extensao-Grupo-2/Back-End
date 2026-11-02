package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Linha de ficha técnica: quantidade de um ingrediente usada em um produto")
public class FichaTecnicaListarDto {

    @Schema(description = "Nome do produto", example = "Hambúrguer Artesanal")
    private String produto;
    @Schema(description = "Nome do ingrediente", example = "Queijo cheddar")
    private String ingrediente;
    @Schema(description = "Quantidade do ingrediente usada no produto", example = "30.0")
    private Double quantidadeUsada;
    @Schema(description = "Unidade de medida da quantidade", example = "g")
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