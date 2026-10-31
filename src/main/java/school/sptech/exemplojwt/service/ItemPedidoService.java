package school.sptech.exemplojwt.service;

import org.springframework.stereotype.Service;
import school.sptech.exemplojwt.dto.ItemPedidoAtualizacaoDto;
import school.sptech.exemplojwt.dto.ItemPedidoCriacaoDto;
import school.sptech.exemplojwt.dto.ItemPedidoMapper;
import school.sptech.exemplojwt.entity.ItemPedido;
import school.sptech.exemplojwt.entity.Pedido;
import school.sptech.exemplojwt.entity.Produto;
import school.sptech.exemplojwt.Exceptions.ItemPedidoNaoExisteException;
import school.sptech.exemplojwt.Exceptions.ItemPedidoSemAlteracaoException;
import school.sptech.exemplojwt.Exceptions.PedidoNaoExisteException;
import school.sptech.exemplojwt.Exceptions.ProdutoInativoException;
import school.sptech.exemplojwt.Exceptions.ProdutoNaoExisteException;
import school.sptech.exemplojwt.repository.ItemPedidoRepository;
import school.sptech.exemplojwt.repository.PedidoRepository;
import school.sptech.exemplojwt.repository.ProdutoRepository;

import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository,
                             PedidoRepository pedidoRepository,
                             ProdutoRepository produtoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public ItemPedido criar(ItemPedidoCriacaoDto dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new PedidoNaoExisteException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ProdutoNaoExisteException("Produto não encontrado"));

        if (!produto.isAtivo()) {
            throw new ProdutoInativoException("Produto inativo não pode ser adicionado ao pedido");
        }

        return itemPedidoRepository.save(ItemPedidoMapper.toEntity(dto, pedido, produto));
    }

    public ItemPedido atualizar(Long id, ItemPedidoAtualizacaoDto dto) {
        ItemPedido item = buscarPorId(id);

        if (dto.getQuantidade() == null && dto.getObservacao() == null) {
            throw new ItemPedidoSemAlteracaoException("Informe a quantidade e/ou a observação");
        }
        if (dto.getQuantidade() != null) {
            item.setQuantidade(dto.getQuantidade());
        }
        if (dto.getObservacao() != null) {
            item.setObservacao(dto.getObservacao());
        }

        return itemPedidoRepository.save(item);
    }

    public void deletar(Long id) {
        ItemPedido item = buscarPorId(id);
        itemPedidoRepository.delete(item);
    }

    public List<ItemPedido> listarPorPedido(Long pedidoId) {
        if (!pedidoRepository.existsById(pedidoId)) {
            throw new PedidoNaoExisteException("Pedido não encontrado");
        }
        return itemPedidoRepository.findByPedidoId(pedidoId);
    }

    private ItemPedido buscarPorId(Long id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNaoExisteException("Item do pedido não encontrado"));
    }
}