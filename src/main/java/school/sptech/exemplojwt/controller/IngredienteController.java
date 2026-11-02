package school.sptech.exemplojwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exemplojwt.config.swagger.ErroResponse;
import school.sptech.exemplojwt.dto.IngredienteCriacaoDto;
import school.sptech.exemplojwt.dto.IngredienteListarDto;
import school.sptech.exemplojwt.dto.IngredienteMapper;
import school.sptech.exemplojwt.entity.Ingrediente;
import school.sptech.exemplojwt.service.IngredienteService;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
@CrossOrigin(origins = "*")
@Tag(name = "Ingredientes", description = "Ingredientes usados na produção dos produtos")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @GetMapping
    @Operation(summary = "Listar ingredientes", description = "Retorna todos os ingredientes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de ingredientes",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = IngredienteListarDto.class))))
    public ResponseEntity<List<IngredienteListarDto>> listar() {
        return ResponseEntity.ok(IngredienteMapper.toListarDto(ingredienteService.listar()));
    }

    @PostMapping
    @Operation(summary = "Cadastrar ingrediente", description = "Cria um novo ingrediente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ingrediente criado",
                    content = @Content(schema = @Schema(implementation = IngredienteListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<IngredienteListarDto> criar(@RequestBody @Valid IngredienteCriacaoDto dto) {
        Ingrediente ingrediente = ingredienteService.criar(dto);
        return ResponseEntity.status(201).body(IngredienteMapper.toListarDto(ingrediente));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ingrediente", description = "Substitui os dados do ingrediente informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ingrediente atualizado",
                    content = @Content(schema = @Schema(implementation = IngredienteListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<IngredienteListarDto> atualizar(
            @Parameter(description = "ID do ingrediente", example = "3") @PathVariable Long id,
            @RequestBody @Valid IngredienteCriacaoDto dto) {
        Ingrediente ingrediente = ingredienteService.atualizar(id, dto);
        return ResponseEntity.status(200).body(IngredienteMapper.toListarDto(ingrediente));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ingrediente",
            description = "Remove o ingrediente. Não é permitido excluir ingredientes presentes em alguma ficha técnica.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ingrediente excluído", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "409", description = "Ingrediente utilizado em ficha técnica",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do ingrediente", example = "3") @PathVariable Long id) {
        ingredienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
