package school.sptech.exemplojwt.dto;

import school.sptech.exemplojwt.entity.Ingrediente;

import java.util.ArrayList;
import java.util.List;

public class IngredienteMapper {

    private IngredienteMapper() {
    }

    public static Ingrediente toEntity(IngredienteCriacaoDto dto) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(dto.getNome());
        ingrediente.setUnidade_medida(dto.getUnidadeMedida());
        return ingrediente;
    }

    public static void atualizar(Ingrediente ingrediente, IngredienteCriacaoDto dto) {
        ingrediente.setNome(dto.getNome());
        ingrediente.setUnidade_medida(dto.getUnidadeMedida());
    }

    public static IngredienteListarDto toListarDto(Ingrediente ingrediente) {
        return new IngredienteListarDto(
                ingrediente.getId(),
                ingrediente.getNome(),
                ingrediente.getUnidade_medida()
        );
    }

    public static List<IngredienteListarDto> toListarDto(List<Ingrediente> ingredientes) {
        List<IngredienteListarDto> dtos = new ArrayList<>();

        for (Ingrediente i : ingredientes) {
            dtos.add(toListarDto(i));
        }

        return dtos;
    }
}