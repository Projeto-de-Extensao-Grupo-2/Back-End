package school.sptech.exemplojwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exemplojwt.config.swagger.ErroResponse;
import school.sptech.exemplojwt.dto.ItemPedidoAtualizacaoDto;
import school.sptech.exemplojwt.dto.ItemPedidoCriacaoDto;
import school.sptech.exemplojwt.dto.ItemPedidoListarDto;
import school.sptech.exemplojwt.dto.ItemPedidoMapper;
import school.sptech.exemplojwt.entity.ItemPedido;
import school.sptech.exemplojwt.service.ItemPedidoService;

@RestController
@RequestMapping("/itens-pedido")
@CrossOrigin(origins = "*")
@Tag(name = "Itens de Pedido", description = "Itens (produtos) que compõem um pedido")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping
    @Operation(summary = "Adicionar item ao pedido",
            description = "Inclui um produto em um pedido existente. O produto deve estar ativo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item adicionado",
                    content = @Content(schema = @Schema(implementation = ItemPedidoListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou produto inativo",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pedido ou produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<ItemPedidoListarDto> criar(@RequestBody @Valid ItemPedidoCriacaoDto dto) {
        ItemPedido item = itemPedidoService.criar(dto);
        return ResponseEntity.status(201).body(ItemPedidoMapper.toListarDto(item));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item do pedido",
            description = "Altera a quantidade e/ou a observação do item. " +
                    "Ao menos um dos dois campos deve ser informado; campos omitidos permanecem inalterados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado",
                    content = @Content(schema = @Schema(implementation = ItemPedidoListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Nenhum campo informado ou dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<ItemPedidoListarDto> atualizar(
            @Parameter(description = "ID do item do pedido", example = "25") @PathVariable Long id,
            @RequestBody @Valid ItemPedidoAtualizacaoDto dto) {
        ItemPedido item = itemPedidoService.atualizar(id, dto);
        return ResponseEntity.status(200).body(ItemPedidoMapper.toListarDto(item));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover item do pedido", description = "Remove um item de um pedido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item removido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do item do pedido", example = "25") @PathVariable Long id) {
        itemPedidoService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
