package school.sptech.exemplojwt.entity;

import jakarta.persistence.*;
import school.sptech.exemplojwt.dto.StatusMesa;

@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numero;
    private String qr_code_hash;
    @Enumerated(EnumType.STRING)
    private StatusMesa status = StatusMesa.LIVRE;

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

    public String getQr_code_hash() {
        return qr_code_hash;
    }

    public void setQr_code_hash(String qr_code_hash) {
        this.qr_code_hash = qr_code_hash;
    }

    public StatusMesa getStatus() {
        return status;
    }

    public void setStatus(StatusMesa status) {
        this.status = status;
    }
}
