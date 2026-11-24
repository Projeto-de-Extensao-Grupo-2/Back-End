package school.sptech.exemplojwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.exemplojwt.dto.FichaTecnicaListarDto;
import school.sptech.exemplojwt.entity.FichaTecnica;
import school.sptech.exemplojwt.entity.Ingrediente;
import school.sptech.exemplojwt.entity.Produto;
import school.sptech.exemplojwt.repository.FichaTecnicaRepository;
import school.sptech.exemplojwt.service.FichaTecnicaService;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class FichaTecnicaServiceTest {

    @Mock
    private FichaTecnicaRepository fichaTecnicaRepository;

    @InjectMocks
    private FichaTecnicaService service;

    @Test
    @DisplayName("Deve listar fichas tecnicas convertidas para DTO")
    void deveListarFichasTecnicasConvertidasParaDto() {
        // Arrange
        Produto produto = new Produto();
        produto.setNome("Pizza");
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome("Queijo");
        ingrediente.setUnidade_medida("g");
        FichaTecnica ficha = new FichaTecnica();
        ficha.setProduto(produto);
        ficha.setIngrediente(ingrediente);
        ficha.setQuantidade_usada(200.0);
        Mockito.when(fichaTecnicaRepository.findAllByOrderByProdutoNomeAscIngredienteNomeAsc())
                .thenReturn(List.of(ficha));
        List<FichaTecnicaListarDto> resultado = service.listar();

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Pizza", resultado.get(0).getProduto());
        Assertions.assertEquals("Queijo", resultado.get(0).getIngrediente());
        Assertions.assertEquals(200.0, resultado.get(0).getQuantidadeUsada());
        Assertions.assertEquals("g", resultado.get(0).getUnidadeMedida());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nao houver fichas tecnicas")
    void deveRetornarListaVaziaQuandoNaoHouverFichasTecnicas() {

        Mockito.when(fichaTecnicaRepository.findAllByOrderByProdutoNomeAscIngredienteNomeAsc())
                .thenReturn(List.of());

        List<FichaTecnicaListarDto> resultado = service.listar();

        Assertions.assertTrue(resultado.isEmpty());
    }
}

