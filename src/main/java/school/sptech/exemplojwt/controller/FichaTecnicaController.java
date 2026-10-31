package school.sptech.exemplojwt.controller;

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
public class FichaTecnicaController {

    private final FichaTecnicaService fichaTecnicaService;

    public FichaTecnicaController(FichaTecnicaService fichaTecnicaService) {
        this.fichaTecnicaService = fichaTecnicaService;
    }

    @GetMapping
    public ResponseEntity<List<FichaTecnicaListarDto>> listar() {
        return ResponseEntity.status(200).body(fichaTecnicaService.listar());
    }
}