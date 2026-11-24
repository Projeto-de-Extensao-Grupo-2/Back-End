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
import school.sptech.exemplojwt.Exceptions.MesaComPedidosException;
import school.sptech.exemplojwt.Exceptions.MesaJaExisteException;
import school.sptech.exemplojwt.Exceptions.MesaNaoExisteException;
import school.sptech.exemplojwt.dto.MesaCriacaoDto;
import school.sptech.exemplojwt.dto.StatusMesa;
import school.sptech.exemplojwt.entity.Mesa;
import school.sptech.exemplojwt.repository.MesaRepository;
import school.sptech.exemplojwt.repository.PedidoRepository;
import school.sptech.exemplojwt.service.MesaService;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MesaServiceTest {

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private MesaService service;

    private Mesa criarMesa(Long id, Integer numero, StatusMesa status) {
        Mesa mesa = new Mesa();
        mesa.setId(id);
        mesa.setNumero(numero);
        mesa.setStatus(status);
        return mesa;
    }

    private MesaCriacaoDto criarDto(Integer numero, String hash, StatusMesa status) {
        MesaCriacaoDto dto = new MesaCriacaoDto();
        dto.setNumero(numero);
        dto.setQrCodeHash(hash);
        dto.setStatus(status);
        return dto;
    }

    @Test
    @DisplayName("Deve listar todas as mesas")
    void deveListarTodasAsMesas() {

        List<Mesa> mesas = List.of(criarMesa(1L, 1, StatusMesa.LIVRE), criarMesa(2L, 2, StatusMesa.OCUPADA));
        Mockito.when(mesaRepository.findAll()).thenReturn(mesas);


        List<Mesa> resultado = service.listar();


        Assertions.assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Deve criar mesa quando o numero ainda nao existe")
    void deveCriarMesaQuandoNumeroNaoExiste() {

        MesaCriacaoDto dto = criarDto(5, "hash-5", null);
        Mockito.when(mesaRepository.existsByNumero(5)).thenReturn(false);
        Mockito.when(mesaRepository.save(ArgumentMatchers.any(Mesa.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        Mesa resultado = service.criar(dto);


        Assertions.assertEquals(5, resultado.getNumero());
        Assertions.assertEquals("hash-5", resultado.getQr_code_hash());
        Assertions.assertEquals(StatusMesa.LIVRE, resultado.getStatus());
    }

    @Test
    @DisplayName("Deve lancar exception ao criar mesa com numero repetido")
    void deveLancarExceptionAoCriarMesaComNumeroRepetido() {

        MesaCriacaoDto dto = criarDto(5, "hash-5", null);
        Mockito.when(mesaRepository.existsByNumero(5)).thenReturn(true);


        MesaJaExisteException exception = Assertions.assertThrows(
                MesaJaExisteException.class,
                () -> service.criar(dto));

        Assertions.assertEquals("Já existe uma mesa com esse número", exception.getMessage());
        Mockito.verify(mesaRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve atualizar o status da mesa")
    void deveAtualizarStatusDaMesa() {

        Mesa mesa = criarMesa(1L, 1, StatusMesa.LIVRE);
        Mockito.when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        Mockito.when(mesaRepository.save(ArgumentMatchers.any(Mesa.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        Mesa resultado = service.atualizarStatus(1L, StatusMesa.OCUPADA);


        Assertions.assertEquals(StatusMesa.OCUPADA, resultado.getStatus());
    }

    @Test
    @DisplayName("Deve lancar exception ao atualizar status de mesa inexistente")
    void deveLancarExceptionAoAtualizarStatusDeMesaInexistente() {

        Mockito.when(mesaRepository.findById(99L)).thenReturn(Optional.empty());


        MesaNaoExisteException exception = Assertions.assertThrows(
                MesaNaoExisteException.class,
                () -> service.atualizarStatus(99L, StatusMesa.OCUPADA));

        Assertions.assertEquals("Mesa não encontrada", exception.getMessage());
        Mockito.verify(mesaRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve deletar mesa sem pedidos vinculados")
    void deveDeletarMesaSemPedidos() {

        Mesa mesa = criarMesa(1L, 1, StatusMesa.LIVRE);
        Mockito.when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        Mockito.when(pedidoRepository.existsByMesaId(1L)).thenReturn(false);


        service.deletar(1L);


        Mockito.verify(mesaRepository, Mockito.times(1)).delete(mesa);
    }

    @Test
    @DisplayName("Deve lancar exception ao deletar mesa inexistente")
    void deveLancarExceptionAoDeletarMesaInexistente() {

        Mockito.when(mesaRepository.findById(99L)).thenReturn(Optional.empty());


        Assertions.assertThrows(MesaNaoExisteException.class, () -> service.deletar(99L));
        Mockito.verify(mesaRepository, Mockito.never()).delete(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar exception ao deletar mesa com pedidos vinculados")
    void deveLancarExceptionAoDeletarMesaComPedidos() {

        Mesa mesa = criarMesa(1L, 1, StatusMesa.OCUPADA);
        Mockito.when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        Mockito.when(pedidoRepository.existsByMesaId(1L)).thenReturn(true);


        MesaComPedidosException exception = Assertions.assertThrows(
                MesaComPedidosException.class,
                () -> service.deletar(1L));

        Assertions.assertEquals("Não é possível excluir uma mesa com pedidos vinculados", exception.getMessage());
        Mockito.verify(mesaRepository, Mockito.never()).delete(ArgumentMatchers.any());
    }
}

