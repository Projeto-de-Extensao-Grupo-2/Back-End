package school.sptech.exemplojwt.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exemplojwt.dto.ProdutoCriacaoDto;
import school.sptech.exemplojwt.dto.ProdutoListarDto;
import school.sptech.exemplojwt.dto.ProdutoMapper;
import school.sptech.exemplojwt.entity.Produto;
import school.sptech.exemplojwt.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoListarDto>> listar() {
        return ResponseEntity.status(200).body(ProdutoMapper.toListarDto(produtoService.listar()));
    }

    @PostMapping
    public ResponseEntity<ProdutoListarDto> criar(@RequestBody @Valid ProdutoCriacaoDto dto) {
        Produto produto = produtoService.criar(dto);
        return ResponseEntity.status(201).body(ProdutoMapper.toListarDto(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoListarDto> atualizar(@PathVariable Long id,
                                                      @RequestBody @Valid ProdutoCriacaoDto dto) {
        Produto produto = produtoService.atualizar(id, dto);
        return ResponseEntity.status(200).body(ProdutoMapper.toListarDto(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}