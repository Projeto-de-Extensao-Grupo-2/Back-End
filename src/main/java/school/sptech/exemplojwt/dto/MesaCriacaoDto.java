package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro de uma mesa")
public class MesaCriacaoDto {

        @NotNull(message = "O número da mesa é obrigatório")
        @Positive(message = "O número da mesa deve ser maior que zero")
        @Schema(description = "Número da mesa (único, maior que zero)", example = "5")
        private Integer numero;

        @NotBlank(message = "O qrCodeHash é obrigatório")
        @Size(max = 255, message = "O qrCodeHash deve ter no máximo 255 caracteres")
        @Schema(description = "Hash/identificador do QR Code da mesa (máx. 255 caracteres)", example = "a1b2c3d4e5f6")
        private String qrCodeHash;

        @Schema(description = "Status inicial da mesa. Se omitido, assume `LIVRE`", example = "LIVRE")
        private StatusMesa status;

        public Integer getNumero() { return numero; }
        public void setNumero(Integer numero) { this.numero = numero; }

        public String getQrCodeHash() { return qrCodeHash; }
        public void setQrCodeHash(String qrCodeHash) { this.qrCodeHash = qrCodeHash; }

        public StatusMesa getStatus() { return status; }
        public void setStatus(StatusMesa status) { this.status = status; }
}