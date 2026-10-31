package school.sptech.exemplojwt.service;

import org.springframework.stereotype.Service;
import school.sptech.exemplojwt.dto.MesaCriacaoDto;
import school.sptech.exemplojwt.dto.MesaMapper;
import school.sptech.exemplojwt.dto.StatusMesa;
import school.sptech.exemplojwt.entity.Mesa;
import school.sptech.exemplojwt.Exceptions.MesaComPedidosException;
import school.sptech.exemplojwt.Exceptions.MesaJaExisteException;
import school.sptech.exemplojwt.Exceptions.MesaNaoExisteException;
import school.sptech.exemplojwt.repository.MesaRepository;
import school.sptech.exemplojwt.repository.PedidoRepository;

import java.util.List;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;
    private final PedidoRepository pedidoRepository;

    public MesaService(MesaRepository mesaRepository, PedidoRepository pedidoRepository) {
        this.mesaRepository = mesaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<Mesa> listar() {
        return mesaRepository.findAll();
    }

    public Mesa criar(MesaCriacaoDto dto) {
        if (mesaRepository.existsByNumero(dto.getNumero())) {
            throw new MesaJaExisteException("Já existe uma mesa com esse número");
        }
        return mesaRepository.save(MesaMapper.toEntity(dto));
    }

    public Mesa atualizarStatus(Long id, StatusMesa status) {
        Mesa mesa = buscarPorId(id);
        mesa.setStatus(status);
        return mesaRepository.save(mesa);
    }

    public void deletar(Long id) {
        Mesa mesa = buscarPorId(id);
        if (pedidoRepository.existsByMesaId(id)) {
            throw new MesaComPedidosException("Não é possível excluir uma mesa com pedidos vinculados");
        }
        mesaRepository.delete(mesa);
    }

    private Mesa buscarPorId(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new MesaNaoExisteException("Mesa não encontrada"));
    }
}