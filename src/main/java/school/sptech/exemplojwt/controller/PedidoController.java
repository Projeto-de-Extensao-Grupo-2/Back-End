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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exemplojwt.config.swagger.ErroResponse;
import school.sptech.exemplojwt.dto.*;
import school.sptech.exemplojwt.entity.Pedido;
import school.sptech.exemplojwt.service.ItemPedidoService;
import school.sptech.exemplojwt.service.PedidoService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "Abertura, acompanhamento e exclusão de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;

    public PedidoController(PedidoService pedidoService, ItemPedidoService itemPedidoService) {
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    @Operation(summary = "Listar pedidos",
            description = "Lista os pedidos ordenados por data de criação. Todos os filtros são opcionais e " +
                    "podem ser combinados (condição E).")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos (vazia se nenhum atender aos filtros)",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PedidoListarDto.class))))
    public ResponseEntity<List<PedidoListarDto>> listar(
            @Parameter(description = "Filtra pelo status do pedido", example = "PREPARANDO")
            @RequestParam(required = false) StatusPedido status,
            @Parameter(description = "Filtra pelo **número** da mesa (e não pelo ID)", example = "5")
            @RequestParam(required = false) Integer mesa,
            @Parameter(description = "Filtra pelo ID do garçom responsável", example = "2")
            @RequestParam(required = false) Long garcom,
            @Parameter(description = "Filtra pela data de criação (formato ISO `yyyy-MM-dd`)", example = "2026-11-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<Pedido> pedidos = pedidoService.filtrar(status, mesa, garcom, data);
        return ResponseEntity.status(200).body(PedidoMapper.toListarDto(pedidos));
    }

    @PostMapping
    @Operation(summary = "Abrir pedido",
            description = "Cria um pedido para uma mesa, com status inicial `RECEBIDO`. " +
                    "O garçom é opcional; se informado, deve ser um usuário com cargo `GARCOM` e ativo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado",
                    content = @Content(schema = @Schema(implementation = PedidoListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário informado não é garçom",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Mesa ou garçom não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "409", description = "Garçom informado está inativo",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<PedidoListarDto> criar(@RequestBody @Valid PedidoCriacaoDto dto) {
        Pedido pedido = pedidoService.criar(dto);
        return ResponseEntity.status(201).body(PedidoMapper.toListarDto(pedido));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido",
            description = "Altera o status do pedido (`RECEBIDO`, `PREPARANDO`, `PRONTO`, `ENTREGUE`) " +
                    "e atualiza o campo `atualizadoEm`. A API não impõe uma ordem de transição.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado",
                    content = @Content(schema = @Schema(implementation = PedidoListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Status ausente ou inválido",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<PedidoListarDto> atualizarStatus(
            @Parameter(description = "ID do pedido", example = "10") @PathVariable Long id,
            @RequestBody @Valid PedidoStatusDto dto) {
        Pedido pedido = pedidoService.atualizarStatus(id, dto.getStatus());
        return ResponseEntity.status(200).body(PedidoMapper.toListarDto(pedido));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pedido", description = "Remove o pedido e todos os seus itens.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido excluído", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do pedido", example = "10") @PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/{id}/itens")
    @Operation(summary = "Listar itens do pedido", description = "Retorna os itens que compõem o pedido informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Itens do pedido",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemPedidoListarDto.class)))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<List<ItemPedidoListarDto>> listarItens(
            @Parameter(description = "ID do pedido", example = "10") @PathVariable Long id) {
        return ResponseEntity.status(200).body(ItemPedidoMapper.toListarDto(itemPedidoService.listarPorPedido(id)));
    }
}
