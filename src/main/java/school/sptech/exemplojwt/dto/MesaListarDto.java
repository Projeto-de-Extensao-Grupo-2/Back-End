package school.sptech.exemplojwt.dto;

public class MesaListarDto {

    private Long id;
    private Integer numero;
    private String qrCodeHash;
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