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
import school.sptech.exemplojwt.dto.ProdutoCriacaoDto;
import school.sptech.exemplojwt.dto.ProdutoListarDto;
import school.sptech.exemplojwt.dto.ProdutoMapper;
import school.sptech.exemplojwt.entity.Produto;
import school.sptech.exemplojwt.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
@Tag(name = "Produtos", description = "Cardápio: produtos e preços")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos do cardápio, ativos e inativos.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoListarDto.class))))
    public ResponseEntity<List<ProdutoListarDto>> listar() {
        return ResponseEntity.status(200).body(ProdutoMapper.toListarDto(produtoService.listar()));
    }

    @PostMapping
    @Operation(summary = "Cadastrar produto", description = "Cria um novo produto vinculado a uma categoria existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado",
                    content = @Content(schema = @Schema(implementation = ProdutoListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<ProdutoListarDto> criar(@RequestBody @Valid ProdutoCriacaoDto dto) {
        Produto produto = produtoService.criar(dto);
        return ResponseEntity.status(201).body(ProdutoMapper.toListarDto(produto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Substitui os dados do produto informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado",
                    content = @Content(schema = @Schema(implementation = ProdutoListarDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produto ou categoria não encontrado(a)",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<ProdutoListarDto> atualizar(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long id,
            @RequestBody @Valid ProdutoCriacaoDto dto) {
        Produto produto = produtoService.atualizar(id, dto);
        return ResponseEntity.status(200).body(ProdutoMapper.toListarDto(produto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto",
            description = "Remove o produto e a respectiva ficha técnica. " +
                    "Não é permitido excluir produtos que já constam em algum pedido; " +
                    "nesse caso, prefira inativá-lo (`ativo = false`) via `PUT`.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto excluído", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "409", description = "Produto já está em pedidos",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
