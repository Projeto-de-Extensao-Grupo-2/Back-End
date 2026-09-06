package sptech.school.curupace;


import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pratos")
public class PratoController {

    private final JdbcTemplate jdbcTemplate;

    public PratoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Prato>> listarPrato() {
        String sql = "SELECT * FROM prato";

        List<Prato> pratos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Prato.class));

        return ResponseEntity.status(200).body(pratos);
    }

    @PostMapping
    public ResponseEntity<Prato> criar(@RequestBody Prato pratoNovo) {
        if (pratoNovo.getNome() == null || pratoNovo.getNome().isBlank() || pratoNovo.getCategoria() == null || pratoNovo.getCategoria().isBlank() || pratoNovo.getPreco() == null || pratoNovo.getCodigo() == null || pratoNovo.getPorcao() == null || pratoNovo.getPorcao().isBlank() || pratoNovo.getDescricao() == null || pratoNovo.getDescricao().isBlank() || pratoNovo.getTags() == null || pratoNovo.getTags().isBlank()){
            return ResponseEntity.status(400).build();
        } else if (pratoNovo.getPreco() < 0.0 || pratoNovo.getCodigo() < 0) {
            return ResponseEntity.status(400).build();
        }

        String sql = "INSERT INTO prato (nome, categoria, preco, codigo, porcao, descricao, tags) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, pratoNovo.getNome(), pratoNovo.getCategoria(), pratoNovo.getPreco(), pratoNovo.getCodigo(), pratoNovo.getPorcao(), pratoNovo.getDescricao(), pratoNovo.getTags());

        return ResponseEntity.status(201).body(pratoNovo);
    }
}
