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
import school.sptech.exemplojwt.Exceptions.IngredienteEmFichaTecnicaException;
import school.sptech.exemplojwt.Exceptions.IngredienteNaoExisteException;
import school.sptech.exemplojwt.dto.IngredienteCriacaoDto;
import school.sptech.exemplojwt.entity.Ingrediente;
import school.sptech.exemplojwt.repository.FichaTecnicaRepository;
import school.sptech.exemplojwt.repository.IngredienteRepository;
import school.sptech.exemplojwt.service.IngredienteService;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class IngredienteServiceTest {

    @Mock
    private IngredienteRepository ingredienteRepository;

    @Mock
    private FichaTecnicaRepository fichaTecnicaRepository;

    @InjectMocks
    private IngredienteService service;

    private Ingrediente criarIngrediente(Long id, String nome, String unidade) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(id);
        ingrediente.setNome(nome);
        ingrediente.setUnidade_medida(unidade);
        return ingrediente;
    }

    private IngredienteCriacaoDto criarDto(String nome, String unidade) {
        IngredienteCriacaoDto dto = new IngredienteCriacaoDto();
        dto.setNome(nome);
        dto.setUnidadeMedida(unidade);
        return dto;
    }

    @Test
    @DisplayName("Deve listar todos os ingredientes")
    void deveListarTodosOsIngredientes() {

        List<Ingrediente> ingredientes = List.of(
                criarIngrediente(1L, "Farinha", "kg"),
                criarIngrediente(2L, "Queijo", "g"));
        Mockito.when(ingredienteRepository.findAll()).thenReturn(ingredientes);


        List<Ingrediente> resultado = service.listar();


        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("Farinha", resultado.get(0).getNome());
        Assertions.assertEquals("Queijo", resultado.get(1).getNome());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nao houver ingredientes")
    void deveRetornarListaVaziaQuandoNaoHouverIngredientes() {

        Mockito.when(ingredienteRepository.findAll()).thenReturn(List.of());


        List<Ingrediente> resultado = service.listar();


        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve criar ingrediente com os dados do DTO")
    void deveCriarIngredienteCorretamente() {

        IngredienteCriacaoDto dto = criarDto("Tomate", "kg");
        Mockito.when(ingredienteRepository.save(ArgumentMatchers.any(Ingrediente.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        Ingrediente resultado = service.criar(dto);


        Assertions.assertEquals("Tomate", resultado.getNome());
        Assertions.assertEquals("kg", resultado.getUnidade_medida());
        Mockito.verify(ingredienteRepository, Mockito.times(1)).save(resultado);
    }

    @Test
    @DisplayName("Deve atualizar ingrediente existente")
    void deveAtualizarIngredienteExistente() {

        Ingrediente existente = criarIngrediente(1L, "Farinha", "kg");
        IngredienteCriacaoDto dto = criarDto("Farinha integral", "g");
        Mockito.when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(ingredienteRepository.save(ArgumentMatchers.any(Ingrediente.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        Ingrediente resultado = service.atualizar(1L, dto);


        Assertions.assertEquals(1L, resultado.getId());
        Assertions.assertEquals("Farinha integral", resultado.getNome());
        Assertions.assertEquals("g", resultado.getUnidade_medida());
    }

    @Test
    @DisplayName("Deve lancar exception ao atualizar ingrediente inexistente")
    void deveLancarExceptionAoAtualizarIngredienteInexistente() {

        Mockito.when(ingredienteRepository.findById(99L)).thenReturn(Optional.empty());
        IngredienteCriacaoDto dto = criarDto("Sal", "g");


        IngredienteNaoExisteException exception = Assertions.assertThrows(
                IngredienteNaoExisteException.class,
                () -> service.atualizar(99L, dto));

        Assertions.assertEquals("Ingrediente não encontrado", exception.getMessage());
        Mockito.verify(ingredienteRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve deletar ingrediente que nao esta em ficha tecnica")
    void deveDeletarIngredienteSemFichaTecnica() {
        // Arrange
        Ingrediente existente = criarIngrediente(1L, "Farinha", "kg");
        Mockito.when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(fichaTecnicaRepository.existsByIngredienteId(1L)).thenReturn(false);

        service.deletar(1L);


        Mockito.verify(ingredienteRepository, Mockito.times(1)).delete(existente);
    }

    @Test
    @DisplayName("Deve lancar exception ao deletar ingrediente inexistente")
    void deveLancarExceptionAoDeletarIngredienteInexistente() {

        Mockito.when(ingredienteRepository.findById(99L)).thenReturn(Optional.empty());


        IngredienteNaoExisteException exception = Assertions.assertThrows(
                IngredienteNaoExisteException.class,
                () -> service.deletar(99L));

        Assertions.assertEquals("Ingrediente não encontrado", exception.getMessage());
        Mockito.verify(ingredienteRepository, Mockito.never()).delete(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve lancar exception ao deletar ingrediente usado em ficha tecnica")
    void deveLancarExceptionAoDeletarIngredienteEmFichaTecnica() {

        Ingrediente existente = criarIngrediente(1L, "Farinha", "kg");
        Mockito.when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(fichaTecnicaRepository.existsByIngredienteId(1L)).thenReturn(true);

        IngredienteEmFichaTecnicaException exception = Assertions.assertThrows(
                IngredienteEmFichaTecnicaException.class,
                () -> service.deletar(1L));

        Assertions.assertEquals("Não é possível excluir um ingrediente que está em uma ficha técnica",
                exception.getMessage());
        Mockito.verify(ingredienteRepository, Mockito.never()).delete(ArgumentMatchers.any());
    }
}

