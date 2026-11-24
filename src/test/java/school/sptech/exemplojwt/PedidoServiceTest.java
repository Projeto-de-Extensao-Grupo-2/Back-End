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
import org.springframework.data.domain.Sort;
import school.sptech.exemplojwt.Exceptions.GarcomInativoException;
import school.sptech.exemplojwt.Exceptions.GarcomNaoExisteException;
import school.sptech.exemplojwt.Exceptions.MesaNaoExisteException;
import school.sptech.exemplojwt.Exceptions.PedidoNaoExisteException;
import school.sptech.exemplojwt.Exceptions.UsuarioNaoGarcomException;
import school.sptech.exemplojwt.dto.Cargo;
import school.sptech.exemplojwt.dto.PedidoCriacaoDto;
import school.sptech.exemplojwt.dto.StatusPedido;
import school.sptech.exemplojwt.entity.Mesa;
import school.sptech.exemplojwt.entity.Pedido;
import school.sptech.exemplojwt.entity.Usuario;
import school.sptech.exemplojwt.repository.ItemPedidoRepository;
import school.sptech.exemplojwt.repository.MesaRepository;
import school.sptech.exemplojwt.repository.PedidoRepository;
import school.sptech.exemplojwt.repository.UsuarioRepository;
import school.sptech.exemplojwt.service.PedidoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private PedidoService service;



    private Mesa criarMesa(Long id, Integer numero) {
        Mesa mesa = new Mesa();
        mesa.setId(id);
        mesa.setNumero(numero);
        return mesa;
    }

    private Usuario criarUsuario(Long id, Cargo cargo, boolean ativo) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome("Usuario " + id);
        usuario.setCargo(cargo);
        usuario.setAtivo(ativo);
        return usuario;
    }

    private Pedido criarPedido(Long id, StatusPedido status, Mesa mesa, Usuario garcom, LocalDateTime criadoEm) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatus(status);
        pedido.setMesa(mesa);
        pedido.setGarcom(garcom);
        pedido.setCriado_em(criadoEm);
        return pedido;
    }

    private PedidoCriacaoDto criarDto(Long mesaId, Long garcomId) {
        PedidoCriacaoDto dto = new PedidoCriacaoDto();
        dto.setMesaId(mesaId);
        dto.setGarcomId(garcomId);
        return dto;
    }
    private List<Pedido> criarPedidosParaFiltro() {
        Mesa mesa1 = criarMesa(1L, 1);
        Mesa mesa2 = criarMesa(2L, 2);
        Usuario garcom10 = criarUsuario(10L, Cargo.GARCOM, true);
        Usuario garcom20 = criarUsuario(20L, Cargo.GARCOM, true);

        return List.of(
                criarPedido(1L, StatusPedido.RECEBIDO, mesa1, garcom10, LocalDateTime.of(2026, 11, 1, 12, 0)),
                criarPedido(2L, StatusPedido.PREPARANDO, mesa2, garcom20, LocalDateTime.of(2026, 11, 2, 13, 0)),
                criarPedido(3L, StatusPedido.PRONTO, mesa1, null, LocalDateTime.of(2026, 11, 2, 14, 0)));
    }


    @Test
    @DisplayName("Deve retornar todos os pedidos quando nenhum filtro for informado")
    void deveRetornarTodosOsPedidosSemFiltro() {
        Mockito.when(pedidoRepository.findAll(Sort.by("criado_em"))).thenReturn(criarPedidosParaFiltro());

        List<Pedido> resultado = service.filtrar(null, null, null, null);
        Assertions.assertEquals(3, resultado.size());
    }

    @Test
    @DisplayName("Deve filtrar pedidos por status")
    void deveFiltrarPedidosPorStatus() {
        Mockito.when(pedidoRepository.findAll(Sort.by("criado_em"))).thenReturn(criarPedidosParaFiltro());

        List<Pedido> resultado = service.filtrar(StatusPedido.PREPARANDO, null, null, null);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals(2L, resultado.get(0).getId());
    }

    @Test
    @DisplayName("Deve filtrar pedidos pelo numero da mesa")
    void deveFiltrarPedidosPorNumeroDaMesa() {
        Mockito.when(pedidoRepository.findAll(Sort.by("criado_em"))).thenReturn(criarPedidosParaFiltro());

        List<Pedido> resultado = service.filtrar(null, 1, null, null);
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(1L, resultado.get(0).getId());
        Assertions.assertEquals(3L, resultado.get(1).getId());
    }

    @Test
    @DisplayName("Deve filtrar pedidos por garcom e ignorar pedidos sem garcom")
    void deveFiltrarPedidosPorGarcomIgnorandoPedidosSemGarcom() {
        Mockito.when(pedidoRepository.findAll(Sort.by("criado_em"))).thenReturn(criarPedidosParaFiltro());

        List<Pedido> resultado = service.filtrar(null, null, 10L, null);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals(1L, resultado.get(0).getId());
    }

    @Test
    @DisplayName("Deve filtrar pedidos pela data de criacao")
    void deveFiltrarPedidosPorData() {
        Mockito.when(pedidoRepository.findAll(Sort.by("criado_em"))).thenReturn(criarPedidosParaFiltro());

        List<Pedido> resultado = service.filtrar(null, null, null, LocalDate.of(2026, 11, 2));
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(2L, resultado.get(0).getId());
        Assertions.assertEquals(3L, resultado.get(1).getId());
    }

    @Test
    @DisplayName("Deve combinar filtros com condicao E")
    void deveCombinarFiltros() {
        Mockito.when(pedidoRepository.findAll(Sort.by("criado_em"))).thenReturn(criarPedidosParaFiltro());

        List<Pedido> resultado = service.filtrar(StatusPedido.PRONTO, 1, null, LocalDate.of(2026, 11, 2));
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals(3L, resultado.get(0).getId());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nenhum pedido atender aos filtros")
    void deveRetornarListaVaziaQuandoNenhumPedidoAtenderAosFiltros() {
        Mockito.when(pedidoRepository.findAll(Sort.by("criado_em"))).thenReturn(criarPedidosParaFiltro());

        List<Pedido> resultado = service.filtrar(StatusPedido.ENTREGUE, null, null, null);
        Assertions.assertTrue(resultado.isEmpty());
    }



    @Test
    @DisplayName("Deve criar pedido sem garcom")
    void deveCriarPedidoSemGarcom() {
        Mesa mesa = criarMesa(1L, 5);
        Mockito.when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        Mockito.when(pedidoRepository.save(ArgumentMatchers.any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pedido resultado = service.criar(criarDto(1L, null));
        Assertions.assertSame(mesa, resultado.getMesa());
        Assertions.assertNull(resultado.getGarcom());
        Assertions.assertEquals(StatusPedido.RECEBIDO, resultado.getStatus());
        Mockito.verifyNoInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Deve criar pedido com garcom ativo")
    void deveCriarPedidoComGarcomAtivo() {
        Mesa mesa = criarMesa(1L, 5);
        Usuario garcom = criarUsuario(10L, Cargo.GARCOM, true);
        Mockito.when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        Mockito.when(usuarioRepository.findById(10L)).thenReturn(Optional.of(garcom));
        Mockito.when(pedidoRepository.save(ArgumentMatchers.any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pedido resultado = service.criar(criarDto(1L, 10L));
        Assertions.assertSame(garcom, resultado.getGarcom());
        Assertions.assertSame(mesa, resultado.getMesa());
        Assertions.assertEquals(StatusPedido.RECEBIDO, resultado.getStatus());
    }

    @Test
    @DisplayName("Deve lancar exception ao criar pedido para mesa inexistente")
    void deveLancarExceptionAoCriarPedidoParaMesaInexistente() {

        Mockito.when(mesaRepository.findById(99L)).thenReturn(Optional.empty());

        MesaNaoExisteException exception = Assertions.assertThrows(
                MesaNaoExisteException.class,
                () -> service.criar(criarDto(99L, null)));

        Assertions.assertEquals("Mesa não encontrada", exception.getMessage());
        Mockito.verify(pedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar exception ao criar pedido com garcom inexistente")
    void deveLancarExceptionAoCriarPedidoComGarcomInexistente() {

        Mockito.when(mesaRepository.findById(1L)).thenReturn(Optional.of(criarMesa(1L, 5)));
        Mockito.when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        GarcomNaoExisteException exception = Assertions.assertThrows(
                GarcomNaoExisteException.class,
                () -> service.criar(criarDto(1L, 99L)));

        Assertions.assertEquals("Garçom não encontrado", exception.getMessage());
        Mockito.verify(pedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar exception quando o usuario informado nao e garcom")
    void deveLancarExceptionQuandoUsuarioNaoForGarcom() {

        Usuario gestor = criarUsuario(10L, Cargo.GESTOR, true);
        Mockito.when(mesaRepository.findById(1L)).thenReturn(Optional.of(criarMesa(1L, 5)));
        Mockito.when(usuarioRepository.findById(10L)).thenReturn(Optional.of(gestor));

        UsuarioNaoGarcomException exception = Assertions.assertThrows(
                UsuarioNaoGarcomException.class,
                () -> service.criar(criarDto(1L, 10L)));

        Assertions.assertEquals("O usuário informado não é garçom", exception.getMessage());
        Mockito.verify(pedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar exception quando o garcom estiver inativo")
    void deveLancarExceptionQuandoGarcomEstiverInativo() {

        Usuario garcomInativo = criarUsuario(10L, Cargo.GARCOM, false);
        Mockito.when(mesaRepository.findById(1L)).thenReturn(Optional.of(criarMesa(1L, 5)));
        Mockito.when(usuarioRepository.findById(10L)).thenReturn(Optional.of(garcomInativo));

        GarcomInativoException exception = Assertions.assertThrows(
                GarcomInativoException.class,
                () -> service.criar(criarDto(1L, 10L)));

        Assertions.assertEquals("O garçom informado está inativo", exception.getMessage());
        Mockito.verify(pedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve atualizar status e a data de atualizacao do pedido")
    void deveAtualizarStatusEDataDeAtualizacao() {
        LocalDateTime dataAntiga = LocalDateTime.of(2020, 1, 1, 10, 0);
        Pedido pedido = criarPedido(1L, StatusPedido.RECEBIDO, criarMesa(1L, 1), null, dataAntiga);
        pedido.setAtualizado_em(dataAntiga);
        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        Mockito.when(pedidoRepository.save(ArgumentMatchers.any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pedido resultado = service.atualizarStatus(1L, StatusPedido.PRONTO);
        Assertions.assertEquals(StatusPedido.PRONTO, resultado.getStatus());
        Assertions.assertTrue(resultado.getAtualizado_em().isAfter(dataAntiga));
    }

    @Test
    @DisplayName("Deve lancar exception ao atualizar status de pedido inexistente")
    void deveLancarExceptionAoAtualizarStatusDePedidoInexistente() {

        Mockito.when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        PedidoNaoExisteException exception = Assertions.assertThrows(
                PedidoNaoExisteException.class,
                () -> service.atualizarStatus(99L, StatusPedido.PRONTO));

        Assertions.assertEquals("Pedido não encontrado", exception.getMessage());
        Mockito.verify(pedidoRepository, Mockito.never()).save(ArgumentMatchers.any());
    }


    @Test
    @DisplayName("Deve deletar os itens e depois o pedido")
    void deveDeletarItensEDepoisOPedido() {
        Pedido pedido = criarPedido(1L, StatusPedido.RECEBIDO, criarMesa(1L, 1), null, LocalDateTime.now());
        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        service.deletar(1L);
        InOrder ordem = Mockito.inOrder(itemPedidoRepository, pedidoRepository);
        ordem.verify(itemPedidoRepository).deleteByPedidoId(1L);
        ordem.verify(pedidoRepository).delete(pedido);
    }

    @Test
    @DisplayName("Deve lancar exception ao deletar pedido inexistente")
    void deveLancarExceptionAoDeletarPedidoInexistente() {

        Mockito.when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(PedidoNaoExisteException.class, () -> service.deletar(99L));
        Mockito.verify(itemPedidoRepository, Mockito.never()).deleteByPedidoId(ArgumentMatchers.anyLong());
        Mockito.verify(pedidoRepository, Mockito.never()).delete(ArgumentMatchers.any());
    }
}

