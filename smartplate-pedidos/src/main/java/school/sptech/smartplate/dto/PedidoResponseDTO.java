package school.sptech.smartplate.dto;

import java.time.LocalDateTime;
import school.sptech.smartplate.enums.StatusPedido;

public class PedidoResponseDTO {

    private Long id;
    private StatusPedido status;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private MesaResumoDTO mesa;
    private GarcomResumoDTO garcom;

    public static class MesaResumoDTO {
        private Long id;
        private Integer numero;

        public MesaResumoDTO() {
        }

        public MesaResumoDTO(Long id, Integer numero) {
            this.id = id;
            this.numero = numero;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getNumero() {
            return numero;
        }

        public void setNumero(Integer numero) {
            this.numero = numero;
        }
    }

    public static class GarcomResumoDTO {
        private Long id;
        private String nome;

        public GarcomResumoDTO() {
        }

        public GarcomResumoDTO(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(Long id, StatusPedido status, LocalDateTime criadoEm, LocalDateTime atualizadoEm,
            MesaResumoDTO mesa, GarcomResumoDTO garcom) {
        this.id = id;
        this.status = status;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.mesa = mesa;
        this.garcom = garcom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public MesaResumoDTO getMesa() {
        return mesa;
    }

    public void setMesa(MesaResumoDTO mesa) {
        this.mesa = mesa;
    }

    public GarcomResumoDTO getGarcom() {
        return garcom;
    }

    public void setGarcom(GarcomResumoDTO garcom) {
        this.garcom = garcom;
    }
}
