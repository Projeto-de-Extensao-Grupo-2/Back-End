package school.sptech.exemplojwt.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exemplojwt.dto.ItemPedidoAtualizacaoDto;
import school.sptech.exemplojwt.dto.ItemPedidoCriacaoDto;
import school.sptech.exemplojwt.dto.ItemPedidoListarDto;
import school.sptech.exemplojwt.dto.ItemPedidoMapper;
import school.sptech.exemplojwt.entity.ItemPedido;
import school.sptech.exemplojwt.service.ItemPedidoService;

@RestController
@RequestMapping("/itens-pedido")
@CrossOrigin(origins = "*")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping
    public ResponseEntity<ItemPedidoListarDto> criar(@RequestBody @Valid ItemPedidoCriacaoDto dto) {
        ItemPedido item = itemPedidoService.criar(dto);
        return ResponseEntity.status(201).body(ItemPedidoMapper.toListarDto(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoListarDto> atualizar(@PathVariable Long id,
                                                         @RequestBody @Valid ItemPedidoAtualizacaoDto dto) {
        ItemPedido item = itemPedidoService.atualizar(id, dto);
        return ResponseEntity.status(200).body(ItemPedidoMapper.toListarDto(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemPedidoService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}