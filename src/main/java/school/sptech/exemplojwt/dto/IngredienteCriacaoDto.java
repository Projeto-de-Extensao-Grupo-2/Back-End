package school.sptech.exemplojwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class IngredienteCriacaoDto {

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        private String nome;

        @NotBlank(message = "A unidade de medida é obrigatória")
        @Size(max = 20, message = "A unidade de medida deve ter no máximo 20 caracteres")
        private String unidadeMedida;

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getUnidadeMedida() { return unidadeMedida; }
        public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
}