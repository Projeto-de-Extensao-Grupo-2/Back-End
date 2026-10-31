package school.sptech.exemplojwt.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exemplojwt.dto.*;
import school.sptech.exemplojwt.entity.Mesa;
import school.sptech.exemplojwt.service.MesaService;

import java.util.List;

@RestController
@RequestMapping("/mesas")
@CrossOrigin(origins = "*")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public ResponseEntity<List<MesaListarDto>> listar() {
        return ResponseEntity.status(200).body(MesaMapper.toListarDto(mesaService.listar()));
    }

    @PostMapping
    public ResponseEntity<MesaListarDto> criar(@RequestBody @Valid MesaCriacaoDto dto) {
        Mesa mesa = mesaService.criar(dto);
        return ResponseEntity.status(201).body(MesaMapper.toListarDto(mesa));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<MesaListarDto> atualizarStatus(@PathVariable Long id,
                                                         @RequestBody @Valid MesaStatusDto dto) {
        Mesa mesa = mesaService.atualizarStatus(id, dto.getStatus());
        return ResponseEntity.status(200).body(MesaMapper.toListarDto(mesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        mesaService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}