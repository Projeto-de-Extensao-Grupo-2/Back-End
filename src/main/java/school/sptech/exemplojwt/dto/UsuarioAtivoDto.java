package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para ativar/inativar um usuário")
public class UsuarioAtivoDto {

        @NotNull(message = "O campo ativo é obrigatório")
        @Schema(description = "`true` para ativar, `false` para inativar o usuário", example = "true")
        private Boolean ativo;

        public Boolean getAtivo() { return ativo; }
        public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}