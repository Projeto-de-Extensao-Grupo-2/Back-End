package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de um ingrediente")
public class IngredienteListarDto {

    @Schema(description = "ID do ingrediente", example = "3")
    private Long id;
    @Schema(description = "Nome do ingrediente", example = "Queijo cheddar")
    private String nome;
    @Schema(description = "Unidade de medida", example = "g")
    private String unidadeMedida;

    public IngredienteListarDto(Long id, String nome, String unidadeMedida) {
        this.id = id;
        this.nome = nome;
        this.unidadeMedida = unidadeMedida;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getUnidadeMedida() { return unidadeMedida; }
}