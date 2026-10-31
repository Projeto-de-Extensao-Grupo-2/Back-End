package school.sptech.exemplojwt.dto;
import school.sptech.exemplojwt.entity.FichaTecnica;

import java.util.ArrayList;
import java.util.List;

public class FichaTecnicaMapper {

    public static FichaTecnicaListarDto toListarDto(FichaTecnica f) {
        return new FichaTecnicaListarDto(
                f.getProduto().getNome(),
                f.getIngrediente().getNome(),
                f.getQuantidade_usada(),
                f.getIngrediente().getUnidade_medida());
    }

    public static List<FichaTecnicaListarDto> toListarDto(List<FichaTecnica> fichas) {
        List<FichaTecnicaListarDto> dtos = new ArrayList<>();

        for (FichaTecnica f : fichas) {
            dtos.add(toListarDto(f));
        }

        return dtos;
    }
}