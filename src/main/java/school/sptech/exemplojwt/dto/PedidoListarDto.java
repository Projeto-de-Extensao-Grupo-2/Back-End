package school.sptech.exemplojwt.dto;

import java.time.LocalDateTime;

public class PedidoListarDto {

    private Long id;
    private Integer mesaNumero;
    private String garcomNome;
    private StatusPedido status;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public PedidoListarDto(Long id, Integer mesaNumero, String garcomNome, StatusPedido status,
                           LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.mesaNumero = mesaNumero;
        this.garcomNome = garcomNome;
        this.status = status;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public Long getId() { return id; }
    public Integer getMesaNumero() { return mesaNumero; }
    public String getGarcomNome() { return garcomNome; }
    public StatusPedido getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
}