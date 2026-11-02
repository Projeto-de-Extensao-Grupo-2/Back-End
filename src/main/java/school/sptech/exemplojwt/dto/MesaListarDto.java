package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de uma mesa")
public class MesaListarDto {

    @Schema(description = "ID da mesa", example = "1")
    private Long id;
    @Schema(description = "Número da mesa", example = "5")
    private Integer numero;
    @Schema(description = "Hash/identificador do QR Code da mesa", example = "a1b2c3d4e5f6")
    private String qrCodeHash;
    @Schema(description = "Status atual da mesa", example = "LIVRE")
    private StatusMesa status;

    public MesaListarDto(Long id, Integer numero, String qrCodeHash, StatusMesa status) {
        this.id = id;
        this.numero = numero;
        this.qrCodeHash = qrCodeHash;
        this.status = status;
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public String getQrCodeHash() { return qrCodeHash; }
    public StatusMesa getStatus() { return status; }
}