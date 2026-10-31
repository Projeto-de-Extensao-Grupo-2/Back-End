package school.sptech.exemplojwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class MesaCriacaoDto {

        @NotNull(message = "O número da mesa é obrigatório")
        @Positive(message = "O número da mesa deve ser maior que zero")
        private Integer numero;

        @NotBlank(message = "O qrCodeHash é obrigatório")
        @Size(max = 255, message = "O qrCodeHash deve ter no máximo 255 caracteres")
        private String qrCodeHash;

        private StatusMesa status;

        public Integer getNumero() { return numero; }
        public void setNumero(Integer numero) { this.numero = numero; }

        public String getQrCodeHash() { return qrCodeHash; }
        public void setQrCodeHash(String qrCodeHash) { this.qrCodeHash = qrCodeHash; }

        public StatusMesa getStatus() { return status; }
        public void setStatus(StatusMesa status) { this.status = status; }
}