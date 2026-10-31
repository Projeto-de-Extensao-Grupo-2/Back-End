package school.sptech.exemplojwt.controller;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exemplojwt.dto.*;
import school.sptech.exemplojwt.entity.Pedido;
import school.sptech.exemplojwt.service.ItemPedidoService;
import school.sptech.exemplojwt.service.PedidoService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;

    public PedidoController(PedidoService pedidoService, ItemPedidoService itemPedidoService) {
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoListarDto>> listar(
            @RequestParam(required = false) StatusPedido status,
            @RequestParam(required = false) Integer mesa,
            @RequestParam(required = false) Long garcom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<Pedido> pedidos = pedidoService.filtrar(status, mesa, garcom, data);
        return ResponseEntity.status(200).body(PedidoMapper.toListarDto(pedidos));
    }

    @PostMapping
    public ResponseEntity<PedidoListarDto> criar(@RequestBody @Valid PedidoCriacaoDto dto) {
        Pedido pedido = pedidoService.criar(dto);
        return ResponseEntity.status(201).body(PedidoMapper.toListarDto(pedido));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoListarDto> atualizarStatus(@PathVariable Long id,
                                                           @RequestBody @Valid PedidoStatusDto dto) {
        Pedido pedido = pedidoService.atualizarStatus(id, dto.getStatus());
        return ResponseEntity.status(200).body(PedidoMapper.toListarDto(pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemPedidoListarDto>> listarItens(@PathVariable Long id) {
        return ResponseEntity.status(200).body(ItemPedidoMapper.toListarDto(itemPedidoService.listarPorPedido(id)));
    }
}