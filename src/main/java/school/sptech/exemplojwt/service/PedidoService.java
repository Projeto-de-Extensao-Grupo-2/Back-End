package school.sptech.exemplojwt.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.exemplojwt.dto.Cargo;
import school.sptech.exemplojwt.dto.PedidoCriacaoDto;
import school.sptech.exemplojwt.dto.PedidoMapper;
import school.sptech.exemplojwt.dto.StatusPedido;
import school.sptech.exemplojwt.entity.Mesa;
import school.sptech.exemplojwt.entity.Pedido;
import school.sptech.exemplojwt.entity.Usuario;
import school.sptech.exemplojwt.Exceptions.GarcomInativoException;
import school.sptech.exemplojwt.Exceptions.GarcomNaoExisteException;
import school.sptech.exemplojwt.Exceptions.MesaNaoExisteException;
import school.sptech.exemplojwt.Exceptions.PedidoNaoExisteException;
import school.sptech.exemplojwt.Exceptions.UsuarioNaoGarcomException;
import school.sptech.exemplojwt.repository.ItemPedidoRepository;
import school.sptech.exemplojwt.repository.MesaRepository;
import school.sptech.exemplojwt.repository.PedidoRepository;
import school.sptech.exemplojwt.repository.UsuarioRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         MesaRepository mesaRepository,
                         UsuarioRepository usuarioRepository,
                         ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.usuarioRepository = usuarioRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<Pedido> filtrar(StatusPedido status, Integer mesa, Long garcom, LocalDate data) {

        List<Pedido> todos = pedidoRepository.findAll(Sort.by("criado_em"));
        List<Pedido> filtrados = new ArrayList<>();

        for (Pedido p : todos) {
            if (status != null && p.getStatus() != status) continue;
            if (mesa != null && !mesa.equals(p.getMesa().getNumero())) continue;
            if (garcom != null && (p.getGarcom() == null || !garcom.equals(p.getGarcom().getId()))) continue;
            if (data != null && !p.getCriado_em().toLocalDate().equals(data)) continue;

            filtrados.add(p);
        }

        return filtrados;
    }

    public Pedido criar(PedidoCriacaoDto dto) {
        Mesa mesa = mesaRepository.findById(dto.getMesaId())
                .orElseThrow(() -> new MesaNaoExisteException("Mesa não encontrada"));

        Usuario garcom = null;
        if (dto.getGarcomId() != null) {
            garcom = usuarioRepository.findById(dto.getGarcomId())
                    .orElseThrow(() -> new GarcomNaoExisteException("Garçom não encontrado"));

            if (garcom.getCargo() != Cargo.GARCOM) {
                throw new UsuarioNaoGarcomException("O usuário informado não é garçom");
            }
            if (!garcom.isAtivo()) {
                throw new GarcomInativoException("O garçom informado está inativo");
            }
        }

        return pedidoRepository.save(PedidoMapper.toEntity(mesa, garcom));
    }

    public Pedido atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(status);
        pedido.setAtualizado_em(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void deletar(Long id) {
        Pedido pedido = buscarPorId(id);
        itemPedidoRepository.deleteByPedidoId(id);
        pedidoRepository.delete(pedido);
    }

    private Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoExisteException("Pedido não encontrado"));
    }
}
