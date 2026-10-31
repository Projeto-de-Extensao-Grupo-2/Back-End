package school.sptech.exemplojwt.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exemplojwt.dto.IngredienteCriacaoDto;
import school.sptech.exemplojwt.dto.IngredienteListarDto;
import school.sptech.exemplojwt.dto.IngredienteMapper;
import school.sptech.exemplojwt.entity.Ingrediente;
import school.sptech.exemplojwt.service.IngredienteService;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
@CrossOrigin(origins = "*")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @GetMapping
    public ResponseEntity<List<IngredienteListarDto>> listar() {
        return ResponseEntity.ok(IngredienteMapper.toListarDto(ingredienteService.listar()));
    }

    @PostMapping
    public ResponseEntity<IngredienteListarDto> criar(@RequestBody @Valid IngredienteCriacaoDto dto) {
        Ingrediente ingrediente = ingredienteService.criar(dto);
        return ResponseEntity.status(201).body(IngredienteMapper.toListarDto(ingrediente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteListarDto> atualizar(@PathVariable Long id,
                                                          @RequestBody @Valid IngredienteCriacaoDto dto) {
        Ingrediente ingrediente = ingredienteService.atualizar(id, dto);
        return ResponseEntity.status(200).body(IngredienteMapper.toListarDto(ingrediente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ingredienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}