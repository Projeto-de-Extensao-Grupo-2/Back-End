package school.sptech.smartplate.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.smartplate.dto.PedidoRequestDTO;
import school.sptech.smartplate.dto.PedidoResponseDTO;
import school.sptech.smartplate.dto.StatusPedidoRequestDTO;
import school.sptech.smartplate.enums.StatusPedido;
import school.sptech.smartplate.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar(
            @RequestParam(required = false) StatusPedido status,
            @RequestParam(required = false) Integer mesa,
            @RequestParam(required = false) Long garcom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        return ResponseEntity.status(200).body(pedidoService.listar(status, mesa, garcom, data));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@Valid @RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.status(201).body(pedidoService.criar(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id,
            @Valid @RequestBody StatusPedidoRequestDTO dto) {

        return ResponseEntity.status(200).body(pedidoService.atualizarStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);

        return ResponseEntity.status(204).build();
    }
}
