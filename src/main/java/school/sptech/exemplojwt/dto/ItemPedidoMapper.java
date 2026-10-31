package school.sptech.exemplojwt.dto;

import school.sptech.exemplojwt.entity.ItemPedido;
import school.sptech.exemplojwt.entity.Pedido;
import school.sptech.exemplojwt.entity.Produto;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoMapper {

    private ItemPedidoMapper() {
    }

    public static ItemPedido toEntity(ItemPedidoCriacaoDto dto, Pedido pedido, Produto produto) {
        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(dto.getQuantidade());
        item.setObservacao(dto.getObservacao());
        item.setPreco_unitario(produto.getPreco());
        return item;
    }

    public static ItemPedidoListarDto toListarDto(ItemPedido item) {
        return new ItemPedidoListarDto(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPreco_unitario(),
                item.getObservacao()
        );
    }

    public static List<ItemPedidoListarDto> toListarDto(List<ItemPedido> itens) {
        List<ItemPedidoListarDto> dtos = new ArrayList<>();

        for (ItemPedido i : itens) {
            dtos.add(toListarDto(i));
        }

        return dtos;
    }
}