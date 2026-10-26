package school.sptech.smartplate.mapper;

import java.util.List;
import school.sptech.smartplate.dto.PedidoResponseDTO;
import school.sptech.smartplate.entity.Pedido;

public class PedidoMapper {

    public static PedidoResponseDTO toResponseDto(Pedido pedido) {
        PedidoResponseDTO.MesaResumoDTO mesa = new PedidoResponseDTO.MesaResumoDTO();
        mesa.setId(pedido.getMesa().getId());
        mesa.setNumero(pedido.getMesa().getNumero());

        PedidoResponseDTO.GarcomResumoDTO garcom = null;

        if (pedido.getGarcom() != null) {
            garcom = new PedidoResponseDTO.GarcomResumoDTO();
            garcom.setId(pedido.getGarcom().getId());
            garcom.setNome(pedido.getGarcom().getNome());
        }

        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setStatus(pedido.getStatus());
        dto.setCriadoEm(pedido.getCriadoEm());
        dto.setAtualizadoEm(pedido.getAtualizadoEm());
        dto.setMesa(mesa);
        dto.setGarcom(garcom);
        return dto;
    }

    public static List<PedidoResponseDTO> toResponseDto(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(PedidoMapper::toResponseDto)
                .toList();
    }
}
