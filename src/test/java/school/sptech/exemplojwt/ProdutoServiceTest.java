package school.sptech.exemplojwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.exemplojwt.dto.ProdutoCriacaoDto;
import school.sptech.exemplojwt.entity.Categoria;
import school.sptech.exemplojwt.entity.Produto;
import school.sptech.exemplojwt.repository.CategoriaRepository;
import school.sptech.exemplojwt.repository.FichaTecnicaRepository;
import school.sptech.exemplojwt.repository.ItemPedidoRepository;
import school.sptech.exemplojwt.repository.ProdutoRepository;
import school.sptech.exemplojwt.service.ProdutoService;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private FichaTecnicaRepository fichaTecnicaRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private ProdutoService service;


    private Categoria criarCategoria(Long id, String nome) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome(nome);
        return categoria;
    }

    private Produto criarProduto(Long id, String nome, Double preco, boolean ativo) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setAtivo(ativo);
        return produto;
    }

    private ProdutoCriacaoDto criarDto(Long categoriaId, String nome, Double preco, Boolean ativo) {
        ProdutoCriacaoDto dto = new ProdutoCriacaoDto();
        dto.setCategoriaId(categoriaId);
        dto.setNome(nome);
        dto.setDescricao("Descricao de " + nome);
        dto.setPreco(preco);
        dto.setAtivo(ativo);
        return dto;
    }


    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosOsProdutos() {

        List<Produto> produtos = List.of(
                criarProduto(1L, "Pizza", 40.0, true),
                criarProduto(2L, "Suco", 8.0, true));
        Mockito.when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> resultado = service.listar();

        Assertions.assertEquals(2, resultado.size());
    }



    @Test
    @DisplayName("Deve criar produto ativo por padrao quando o campo ativo nao for informado")
    void deveCriarProdutoAtivoPorPadrao() {

        Categoria categoria = criarCategoria(1L, "Prato principal");
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        Mockito.when(produtoRepository.save(ArgumentMatchers.any(Produto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = service.criar(criarDto(1L, "Pizza", 40.0, null));

        Assertions.assertEquals("Pizza", resultado.getNome());
        Assertions.assertEquals(40.0, resultado.getPreco());
        Assertions.assertSame(categoria, resultado.getCategoria());
        Assertions.assertTrue(resultado.isAtivo());
    }

    @Test
    @DisplayName("Deve criar produto inativo quando informado")
    void deveCriarProdutoInativoQuandoInformado() {

        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(criarCategoria(1L, "Bebida")));
        Mockito.when(produtoRepository.save(ArgumentMatchers.any(Produto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = service.criar(criarDto(1L, "Suco", 8.0, false));

        Assertions.assertFalse(resultado.isAtivo());
    }

    @Test
    @DisplayName("Deve lancar 404 ao criar produto com categoria inexistente")
    void deveLancarNotFoundAoCriarProdutoComCategoriaInexistente() {
        Mockito.when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());
        ProdutoCriacaoDto dto = criarDto(99L, "Pizza", 40.0, null);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> service.criar(dto));

        Assertions.assertEquals(404, exception.getStatusCode().value());
        Assertions.assertEquals("Categoria não encontrada", exception.getReason());
        Mockito.verify(produtoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }



    @Test
    @DisplayName("Deve atualizar produto existente")
    void deveAtualizarProdutoExistente() {

        Produto existente = criarProduto(1L, "Pizza", 40.0, true);
        Categoria novaCategoria = criarCategoria(2L, "Lanche");
        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(categoriaRepository.findById(2L)).thenReturn(Optional.of(novaCategoria));
        Mockito.when(produtoRepository.save(ArgumentMatchers.any(Produto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = service.atualizar(1L, criarDto(2L, "Hamburguer", 25.0, false));

        Assertions.assertEquals(1L, resultado.getId());
        Assertions.assertEquals("Hamburguer", resultado.getNome());
        Assertions.assertEquals(25.0, resultado.getPreco());
        Assertions.assertSame(novaCategoria, resultado.getCategoria());
        Assertions.assertFalse(resultado.isAtivo());
    }

    @Test
    @DisplayName("Deve manter o status ativo ao atualizar sem informar o campo ativo")
    void deveManterStatusAtivoAoAtualizarSemInformarAtivo() {

        Produto existente = criarProduto(1L, "Pizza", 40.0, true);
        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(criarCategoria(1L, "Prato")));
        Mockito.when(produtoRepository.save(ArgumentMatchers.any(Produto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = service.atualizar(1L, criarDto(1L, "Pizza grande", 55.0, null));

        Assertions.assertTrue(resultado.isAtivo());
        Assertions.assertEquals("Pizza grande", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lancar 404 ao atualizar produto inexistente")
    void deveLancarNotFoundAoAtualizarProdutoInexistente() {
        Mockito.when(produtoRepository.findById(99L)).thenReturn(Optional.empty());
        ProdutoCriacaoDto dto = criarDto(1L, "Pizza", 40.0, null);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> service.atualizar(99L, dto));

        Assertions.assertEquals(404, exception.getStatusCode().value());
        Assertions.assertEquals("Produto não encontrado", exception.getReason());
        Mockito.verifyNoInteractions(categoriaRepository);
        Mockito.verify(produtoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar 404 ao atualizar produto com categoria inexistente")
    void deveLancarNotFoundAoAtualizarProdutoComCategoriaInexistente() {
        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(criarProduto(1L, "Pizza", 40.0, true)));
        Mockito.when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());
        ProdutoCriacaoDto dto = criarDto(99L, "Pizza", 40.0, null);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> service.atualizar(1L, dto));

        Assertions.assertEquals(404, exception.getStatusCode().value());
        Assertions.assertEquals("Categoria não encontrada", exception.getReason());
        Mockito.verify(produtoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }


    @Test
    @DisplayName("Deve deletar a ficha tecnica e depois o produto")
    void deveDeletarFichaTecnicaEDepoisOProduto() {

        Produto produto = criarProduto(1L, "Pizza", 40.0, true);
        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        Mockito.when(itemPedidoRepository.existsByProdutoId(1L)).thenReturn(false);

        service.deletar(1L);

        InOrder ordem = Mockito.inOrder(fichaTecnicaRepository, produtoRepository);
        ordem.verify(fichaTecnicaRepository).deleteByProdutoId(1L);
        ordem.verify(produtoRepository).delete(produto);
    }

    @Test
    @DisplayName("Deve lancar 404 ao deletar produto inexistente")
    void deveLancarNotFoundAoDeletarProdutoInexistente() {
        Mockito.when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> service.deletar(99L));

        Assertions.assertEquals(404, exception.getStatusCode().value());
        Mockito.verify(produtoRepository, Mockito.never()).delete(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar 409 ao deletar produto que ja esta em pedidos")
    void deveLancarConflictAoDeletarProdutoEmPedidos() {
        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(criarProduto(1L, "Pizza", 40.0, true)));
        Mockito.when(itemPedidoRepository.existsByProdutoId(1L)).thenReturn(true);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> service.deletar(1L));

        Assertions.assertEquals(409, exception.getStatusCode().value());
        Assertions.assertEquals("Não é possível excluir um produto que já está em pedidos", exception.getReason());
        Mockito.verify(fichaTecnicaRepository, Mockito.never()).deleteByProdutoId(ArgumentMatchers.anyLong());
        Mockito.verify(produtoRepository, Mockito.never()).delete(ArgumentMatchers.any());
    }
}

