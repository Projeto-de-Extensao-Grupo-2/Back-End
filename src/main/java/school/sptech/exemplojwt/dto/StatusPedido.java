package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status do pedido: `RECEBIDO` → `PREPARANDO` → `PRONTO` → `ENTREGUE`")
public enum StatusPedido {
    RECEBIDO,
    PREPARANDO,
    PRONTO,
    ENTREGUE
}
