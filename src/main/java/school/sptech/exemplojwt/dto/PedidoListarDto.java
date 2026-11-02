package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Dados de um pedido")
public class PedidoListarDto {

    @Schema(description = "ID do pedido", example = "10")
    private Long id;
    @Schema(description = "Número da mesa do pedido", example = "5")
    private Integer mesaNumero;
    @Schema(description = "Nome do garçom responsável (`null` se não houver)", example = "Maria Souza")
    private String garcomNome;
    @Schema(description = "Status atual do pedido", example = "RECEBIDO")
    private StatusPedido status;
    @Schema(description = "Data/hora de criação do pedido", example = "2026-11-01T12:30:00")
    private LocalDateTime criadoEm;
    @Schema(description = "Data/hora da última atualização do pedido", example = "2026-11-01T12:45:00")
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