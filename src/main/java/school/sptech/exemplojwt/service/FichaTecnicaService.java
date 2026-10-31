package school.sptech.exemplojwt.service;

import org.springframework.stereotype.Service;
import school.sptech.exemplojwt.dto.FichaTecnicaListarDto;
import school.sptech.exemplojwt.dto.FichaTecnicaMapper;
import school.sptech.exemplojwt.repository.FichaTecnicaRepository;

import java.util.List;

@Service
public class FichaTecnicaService {

    private final FichaTecnicaRepository fichaTecnicaRepository;

    public FichaTecnicaService(FichaTecnicaRepository fichaTecnicaRepository) {
        this.fichaTecnicaRepository = fichaTecnicaRepository;
    }

    public List<FichaTecnicaListarDto> listar() {
        return FichaTecnicaMapper.toListarDto(
                fichaTecnicaRepository.findAllByOrderByProdutoNomeAscIngredienteNomeAsc());
    }
}