package school.sptech.exemplojwt.dto;

import school.sptech.exemplojwt.entity.Mesa;

import java.util.ArrayList;
import java.util.List;

public class MesaMapper {

    private MesaMapper() {
    }

    public static Mesa toEntity(MesaCriacaoDto dto) {
        Mesa mesa = new Mesa();
        mesa.setNumero(dto.getNumero());
        mesa.setQr_code_hash(dto.getQrCodeHash());
        mesa.setStatus(dto.getStatus() == null ? StatusMesa.LIVRE : dto.getStatus());
        return mesa;
    }

    public static MesaListarDto toListarDto(Mesa mesa) {
        return new MesaListarDto(
                mesa.getId(),
                mesa.getNumero(),
                mesa.getQr_code_hash(),
                mesa.getStatus()
        );
    }

    public static List<MesaListarDto> toListarDto(List<Mesa> mesas) {
        List<MesaListarDto> dtos = new ArrayList<>();

        for (Mesa m : mesas) {
            dtos.add(toListarDto(m));
        }

        return dtos;
    }
}