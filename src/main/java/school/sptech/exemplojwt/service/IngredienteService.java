package school.sptech.exemplojwt.service;

import org.springframework.stereotype.Service;
import school.sptech.exemplojwt.dto.IngredienteCriacaoDto;
import school.sptech.exemplojwt.dto.IngredienteMapper;
import school.sptech.exemplojwt.entity.Ingrediente;
import school.sptech.exemplojwt.Exceptions.IngredienteEmFichaTecnicaException;
import school.sptech.exemplojwt.Exceptions.IngredienteNaoExisteException;
import school.sptech.exemplojwt.repository.FichaTecnicaRepository;
import school.sptech.exemplojwt.repository.IngredienteRepository;

import java.util.List;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository,
                              FichaTecnicaRepository fichaTecnicaRepository) {
        this.ingredienteRepository = ingredienteRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
    }

    public List<Ingrediente> listar() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente criar(IngredienteCriacaoDto dto) {
        return ingredienteRepository.save(IngredienteMapper.toEntity(dto));
    }

    public Ingrediente atualizar(Long id, IngredienteCriacaoDto dto) {
        Ingrediente ingrediente = buscarPorId(id);
        IngredienteMapper.atualizar(ingrediente, dto);
        return ingredienteRepository.save(ingrediente);
    }

    public void deletar(Long id) {
        Ingrediente ingrediente = buscarPorId(id);

        if (fichaTecnicaRepository.existsByIngredienteId(id)) {
            throw new IngredienteEmFichaTecnicaException("Não é possível excluir um ingrediente que está em uma ficha técnica");
        }

        ingredienteRepository.delete(ingrediente);
    }

    private Ingrediente buscarPorId(Long id) {
        return ingredienteRepository.findById(id)
                .orElseThrow(() -> new IngredienteNaoExisteException("Ingrediente não encontrado"));
    }
}