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
import school.sptech.exemplojwt.dto.*;
import school.sptech.exemplojwt.entity.Mesa;
import school.sptech.exemplojwt.service.MesaService;

import java.util.List;

@RestController
@RequestMapping("/mesas")
@CrossOrigin(origins = "*")
@Tag(name = "Mesas", description = "Gerenciamento das mesas do restaurante")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    @Operation(summary = "Listar mesas", description = "Retorna todas as mesas cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de mesas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MesaListarDto.class))))
    public ResponseEntity<List<MesaListarDto>> listar() {
        return ResponseEntity.status(200).body(MesaMapper.toListarDto(mesaService.listar()));
    }

    @PostMapping
    @Operation(summary = "Cadastrar mesa",
            description = "Cria uma nova mesa. O número da mesa deve ser único.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mesa criada",
                    content = @Content(schema = @Schema(implementation = MesaListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "409", description = "Já existe uma mesa com esse número",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<MesaListarDto> criar(@RequestBody @Valid MesaCriacaoDto dto) {
        Mesa mesa = mesaService.criar(dto);
        return ResponseEntity.status(201).body(MesaMapper.toListarDto(mesa));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da mesa",
            description = "Altera o status da mesa entre `LIVRE` e `OCUPADA`.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado",
                    content = @Content(schema = @Schema(implementation = MesaListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Status ausente ou inválido",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Mesa não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<MesaListarDto> atualizarStatus(
            @Parameter(description = "ID da mesa", example = "1") @PathVariable Long id,
            @RequestBody @Valid MesaStatusDto dto) {
        Mesa mesa = mesaService.atualizarStatus(id, dto.getStatus());
        return ResponseEntity.status(200).body(MesaMapper.toListarDto(mesa));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir mesa",
            description = "Remove a mesa. Não é permitido excluir mesas que possuam pedidos vinculados.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mesa excluída", content = @Content),
            @ApiResponse(responseCode = "404", description = "Mesa não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "409", description = "Mesa possui pedidos vinculados",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da mesa", example = "1") @PathVariable Long id) {
        mesaService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
