package school.sptech.exemplojwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.exemplojwt.dto.FichaTecnicaListarDto;
import school.sptech.exemplojwt.service.FichaTecnicaService;

import java.util.List;

@RestController
@RequestMapping("/fichas-tecnicas")
@CrossOrigin(origins = "*")
@Tag(name = "Fichas Técnicas", description = "Relação de ingredientes e quantidades por produto")
public class FichaTecnicaController {

    private final FichaTecnicaService fichaTecnicaService;

    public FichaTecnicaController(FichaTecnicaService fichaTecnicaService) {
        this.fichaTecnicaService = fichaTecnicaService;
    }

    @GetMapping
    @Operation(summary = "Listar fichas técnicas",
            description = "Retorna, para cada produto, os ingredientes utilizados, a quantidade e a unidade de medida.")
    @ApiResponse(responseCode = "200", description = "Lista de fichas técnicas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FichaTecnicaListarDto.class))))
    public ResponseEntity<List<FichaTecnicaListarDto>> listar() {
        return ResponseEntity.status(200).body(fichaTecnicaService.listar());
    }
}
