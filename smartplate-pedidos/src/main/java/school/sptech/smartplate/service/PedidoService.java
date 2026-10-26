package school.sptech.smartplate.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.smartplate.dto.PedidoRequestDTO;
import school.sptech.smartplate.dto.PedidoResponseDTO;
import school.sptech.smartplate.dto.StatusPedidoRequestDTO;
import school.sptech.smartplate.entity.Mesa;
import school.sptech.smartplate.entity.Pedido;
import school.sptech.smartplate.entity.Usuario;
import school.sptech.smartplate.enums.StatusPedido;
import school.sptech.smartplate.exception.GarcomInativoException;
import school.sptech.smartplate.exception.MesaNaoEncontradaException;
import school.sptech.smartplate.exception.PedidoJaEntregueException;
import school.sptech.smartplate.exception.PedidoNaoEncontradoException;
import school.sptech.smartplate.exception.UsuarioNaoEncontradoException;
import school.sptech.smartplate.mapper.PedidoMapper;
import school.sptech.smartplate.repository.MesaRepository;
import school.sptech.smartplate.repository.PedidoRepository;
import school.sptech.smartplate.repository.UsuarioRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository, MesaRepository mesaRepository,
            UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Serve para "listar pedidos" (sem filtros) e "filtrar pedido" (com filtros opcionais)
    public List<PedidoResponseDTO> listar(StatusPedido status, Integer mesa, Long garcom, LocalDate data) {
        LocalDateTime inicio = data == null ? null : data.atStartOfDay();
        LocalDateTime fim = data == null ? null : data.plusDays(1).atStartOfDay();

        return PedidoMapper.toResponseDto(pedidoRepository.filtrar(status, mesa, garcom, inicio, fim));
    }

    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Mesa mesa = buscarMesaPorId(dto.getMesaId());

        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);

        if (dto.getGarcomId() != null) {
            Usuario garcom = buscarUsuarioPorId(dto.getGarcomId());

            if (!Boolean.TRUE.equals(garcom.getAtivo())) {
                throw new GarcomInativoException();
            }

            pedido.setGarcom(garcom);
        }

        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setCriadoEm(LocalDateTime.now());

        return PedidoMapper.toResponseDto(pedidoRepository.save(pedido));
    }

    public PedidoResponseDTO atualizarStatus(Long id, StatusPedidoRequestDTO dto) {
        Pedido pedido = buscarEntidadePorId(id);

        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new PedidoJaEntregueException();
        }

        pedido.setStatus(dto.getStatus());
        pedido.setAtualizadoEm(LocalDateTime.now());

        return PedidoMapper.toResponseDto(pedidoRepository.save(pedido));
    }

    @Transactional
    public void deletar(Long id) {
        buscarEntidadePorId(id);

        pedidoRepository.deleteItensByPedidoId(id);
        pedidoRepository.deleteById(id);
    }

    private Pedido buscarEntidadePorId(Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }

        return pedidoOptional.get();
    }

    private Mesa buscarMesaPorId(Long id) {
        Optional<Mesa> mesaOptional = mesaRepository.findById(id);

        if (mesaOptional.isEmpty()) {
            throw new MesaNaoEncontradaException();
        }

        return mesaOptional.get();
    }

    private Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()) {
            throw new UsuarioNaoEncontradoException();
        }

        return usuarioOptional.get();
    }
}
