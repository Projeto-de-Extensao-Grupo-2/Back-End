package school.sptech.exemplojwt.dto;

import school.sptech.exemplojwt.entity.Mesa;
import school.sptech.exemplojwt.entity.Pedido;
import school.sptech.exemplojwt.entity.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoMapper {

    private PedidoMapper() {
    }

    public static Pedido toEntity(Mesa mesa, Usuario garcom) {
        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setGarcom(garcom);
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setCriado_em(LocalDateTime.now());
        pedido.setAtualizado_em(LocalDateTime.now());
        return pedido;
    }

    public static PedidoListarDto toListarDto(Pedido pedido) {
        return new PedidoListarDto(
                pedido.getId(),
                pedido.getMesa().getNumero(),
                pedido.getGarcom() == null ? null : pedido.getGarcom().getNome(),
                pedido.getStatus(),
                pedido.getCriado_em(),
                pedido.getAtualizado_em()
        );
    }

    public static List<PedidoListarDto> toListarDto(List<Pedido> pedidos) {
        List<PedidoListarDto> dtos = new ArrayList<>();

        for (Pedido p : pedidos) {
            dtos.add(toListarDto(p));
        }

        return dtos;
    }
}