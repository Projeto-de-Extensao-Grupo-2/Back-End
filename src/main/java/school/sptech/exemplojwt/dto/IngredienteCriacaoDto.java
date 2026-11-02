package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Schema(description = "Dados para cadastro/atualização de um ingrediente")
public class IngredienteCriacaoDto {

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        @Schema(description = "Nome do ingrediente (máx. 100 caracteres)", example = "Queijo cheddar")
        private String nome;

        @NotBlank(message = "A unidade de medida é obrigatória")
        @Size(max = 20, message = "A unidade de medida deve ter no máximo 20 caracteres")
        @Schema(description = "Unidade de medida (máx. 20 caracteres)", example = "g")
        private String unidadeMedida;

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getUnidadeMedida() { return unidadeMedida; }
        public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
}