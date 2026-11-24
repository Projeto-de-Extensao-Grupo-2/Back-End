package school.sptech.exemplojwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.exemplojwt.Exceptions.ItemPedidoNaoExisteException;
import school.sptech.exemplojwt.Exceptions.ItemPedidoSemAlteracaoException;
import school.sptech.exemplojwt.Exceptions.PedidoNaoExisteException;
import school.sptech.exemplojwt.Exceptions.ProdutoInativoException;
import school.sptech.exemplojwt.Exceptions.ProdutoNaoExisteException;
import school.sptech.exemplojwt.dto.ItemPedidoAtualizacaoDto;
import school.sptech.exemplojwt.dto.ItemPedidoCriacaoDto;
import school.sptech.exemplojwt.entity.ItemPedido;
import school.sptech.exemplojwt.entity.Pedido;
import school.sptech.exemplojwt.entity.Produto;
import school.sptech.exemplojwt.repository.ItemPedidoRepository;
import school.sptech.exemplojwt.repository.PedidoRepository;
import school.sptech.exemplojwt.repository.ProdutoRepository;
import school.sptech.exemplojwt.service.ItemPedidoService;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ItemPedidoServiceTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ItemPedidoService service;

    private Pedido criarPedido(Long id) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        return pedido;
    }

    private Produto criarProduto(Long id, Double preco, boolean ativo) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("Produto " + id);
        produto.setPreco(preco);
        produto.setAtivo(ativo);
        return produto;
    }

    private ItemPedido criarItem(Long id, Integer quantidade, String observacao) {
        ItemPedido item = new ItemPedido();
        item.setId(id);
        item.setQuantidade(quantidade);
        item.setObservacao(observacao);
        return item;
    }

    private ItemPedidoCriacaoDto criarCriacaoDto(Long pedidoId, Long produtoId, Integer quantidade, String obs) {
        ItemPedidoCriacaoDto dto = new ItemPedidoCriacaoDto();
        dto.setPedidoId(pedidoId);
        dto.setProdutoId(produtoId);
        dto.setQuantidade(quantidade);
        dto.setObservacao(obs);
        return dto;
    }

    private ItemPedidoAtualizacaoDto criarAtualizacaoDto(Integer quantidade, String observacao) {
        ItemPedidoAtualizacaoDto dto = new ItemPedidoAtualizacaoDto();
        dto.setQuantidade(quantidade);
        dto.setObservacao(observacao);
        return dto;
    }


    @Test
    @DisplayName("Deve criar item copiando o preco atual do produto")
    void deveCriarItemCopiandoPrecoDoProduto() {
        // Arrange
        Pedido pedido = criarPedido(1L);
        Produto produto = criarProduto(2L, 35.5, true);
        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        Mockito.when(produtoRepository.findById(2L)).thenReturn(Optional.of(produto));
        Mockito.when(itemPedidoRepository.save(ArgumentMatchers.any(ItemPedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ItemPedido resultado = service.criar(criarCriacaoDto(1L, 2L, 3, "Sem cebola"));

        // Assert
        Assertions.assertSame(pedido, resultado.getPedido());
        Assertions.assertSame(produto, resultado.getProduto());
        Assertions.assertEquals(3, resultado.getQuantidade());
        Assertions.assertEquals("Sem cebola", resultado.getObservacao());
        Assertions.assertEquals(35.5, resultado.getPreco_unitario());
    }

    @Test
    @DisplayName("Deve lancar exception ao criar item para pedido inexistente")
    void deveLancarExceptionAoCriarItemParaPedidoInexistente() {

        Mockito.when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
        ItemPedidoCriacaoDto dto = criarCriacaoDto(99L, 2L, 1, null);


        PedidoNaoExisteException exception = Assertions.assertThrows(
                PedidoNaoExisteException.class,
                () -> service.criar(dto));

        Assertions.assertEquals("Pedido não encontrado", exception.getMessage());
        Mockito.verify(itemPedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar exception ao criar item com produto inexistente")
    void deveLancarExceptionAoCriarItemComProdutoInexistente() {

        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(criarPedido(1L)));
        Mockito.when(produtoRepository.findById(99L)).thenReturn(Optional.empty());
        ItemPedidoCriacaoDto dto = criarCriacaoDto(1L, 99L, 1, null);


        ProdutoNaoExisteException exception = Assertions.assertThrows(
                ProdutoNaoExisteException.class,
                () -> service.criar(dto));

        Assertions.assertEquals("Produto não encontrado", exception.getMessage());
        Mockito.verify(itemPedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar exception ao criar item com produto inativo")
    void deveLancarExceptionAoCriarItemComProdutoInativo() {

        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(criarPedido(1L)));
        Mockito.when(produtoRepository.findById(2L)).thenReturn(Optional.of(criarProduto(2L, 10.0, false)));
        ItemPedidoCriacaoDto dto = criarCriacaoDto(1L, 2L, 1, null);

        ProdutoInativoException exception = Assertions.assertThrows(
                ProdutoInativoException.class,
                () -> service.criar(dto));

        Assertions.assertEquals("Produto inativo não pode ser adicionado ao pedido", exception.getMessage());
        Mockito.verify(itemPedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    // ---------- atualizar ----------

    @Test
    @DisplayName("Deve atualizar apenas a quantidade")
    void deveAtualizarApenasQuantidade() {

        ItemPedido item = criarItem(1L, 1, "Sem sal");
        Mockito.when(itemPedidoRepository.findById(1L)).thenReturn(Optional.of(item));
        Mockito.when(itemPedidoRepository.save(ArgumentMatchers.any(ItemPedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        ItemPedido resultado = service.atualizar(1L, criarAtualizacaoDto(4, null));


        Assertions.assertEquals(4, resultado.getQuantidade());
        Assertions.assertEquals("Sem sal", resultado.getObservacao());
    }

    @Test
    @DisplayName("Deve atualizar apenas a observacao")
    void deveAtualizarApenasObservacao() {

        ItemPedido item = criarItem(1L, 2, "Sem sal");
        Mockito.when(itemPedidoRepository.findById(1L)).thenReturn(Optional.of(item));
        Mockito.when(itemPedidoRepository.save(ArgumentMatchers.any(ItemPedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        ItemPedido resultado = service.atualizar(1L, criarAtualizacaoDto(null, "Bem passado"));


        Assertions.assertEquals(2, resultado.getQuantidade());
        Assertions.assertEquals("Bem passado", resultado.getObservacao());
    }

    @Test
    @DisplayName("Deve atualizar quantidade e observacao juntas")
    void deveAtualizarQuantidadeEObservacao() {

        ItemPedido item = criarItem(1L, 2, "Sem sal");
        Mockito.when(itemPedidoRepository.findById(1L)).thenReturn(Optional.of(item));
        Mockito.when(itemPedidoRepository.save(ArgumentMatchers.any(ItemPedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        ItemPedido resultado = service.atualizar(1L, criarAtualizacaoDto(5, "Com gelo"));


        Assertions.assertEquals(5, resultado.getQuantidade());
        Assertions.assertEquals("Com gelo", resultado.getObservacao());
    }

    @Test
    @DisplayName("Deve lancar exception quando nenhuma alteracao for informada")
    void deveLancarExceptionQuandoNenhumaAlteracaoForInformada() {

        Mockito.when(itemPedidoRepository.findById(1L)).thenReturn(Optional.of(criarItem(1L, 2, "Sem sal")));
        ItemPedidoAtualizacaoDto dto = criarAtualizacaoDto(null, null);


        ItemPedidoSemAlteracaoException exception = Assertions.assertThrows(
                ItemPedidoSemAlteracaoException.class,
                () -> service.atualizar(1L, dto));

        Assertions.assertEquals("Informe a quantidade e/ou a observação", exception.getMessage());
        Mockito.verify(itemPedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar exception ao atualizar item inexistente")
    void deveLancarExceptionAoAtualizarItemInexistente() {

        Mockito.when(itemPedidoRepository.findById(99L)).thenReturn(Optional.empty());
        ItemPedidoAtualizacaoDto dto = criarAtualizacaoDto(1, null);

        ItemPedidoNaoExisteException exception = Assertions.assertThrows(
                ItemPedidoNaoExisteException.class,
                () -> service.atualizar(99L, dto));

        Assertions.assertEquals("Item do pedido não encontrado", exception.getMessage());
        Mockito.verify(itemPedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }



    @Test
    @DisplayName("Deve deletar item existente")
    void deveDeletarItemExistente() {

        ItemPedido item = criarItem(1L, 1, null);
        Mockito.when(itemPedidoRepository.findById(1L)).thenReturn(Optional.of(item));


        service.deletar(1L);


        Mockito.verify(itemPedidoRepository, Mockito.times(1)).delete(item);
    }

    @Test
    @DisplayName("Deve lancar exception ao deletar item inexistente")
    void deveLancarExceptionAoDeletarItemInexistente() {

        Mockito.when(itemPedidoRepository.findById(99L)).thenReturn(Optional.empty());


        Assertions.assertThrows(ItemPedidoNaoExisteException.class, () -> service.deletar(99L));
        Mockito.verify(itemPedidoRepository, Mockito.never()).delete(ArgumentMatchers.any());
    }



    @Test
    @DisplayName("Deve listar itens de um pedido existente")
    void deveListarItensDeUmPedidoExistente() {

        List<ItemPedido> itens = List.of(criarItem(1L, 1, null), criarItem(2L, 2, null));
        Mockito.when(pedidoRepository.existsById(1L)).thenReturn(true);
        Mockito.when(itemPedidoRepository.findByPedidoId(1L)).thenReturn(itens);


        List<ItemPedido> resultado = service.listarPorPedido(1L);


        Assertions.assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Deve lancar exception ao listar itens de pedido inexistente")
    void deveLancarExceptionAoListarItensDePedidoInexistente() {

        Mockito.when(pedidoRepository.existsById(99L)).thenReturn(false);


        PedidoNaoExisteException exception = Assertions.assertThrows(
                PedidoNaoExisteException.class,
                () -> service.listarPorPedido(99L));

        Assertions.assertEquals("Pedido não encontrado", exception.getMessage());
        Mockito.verify(itemPedidoRepository, Mockito.never()).findByPedidoId(ArgumentMatchers.anyLong());
    }
}

