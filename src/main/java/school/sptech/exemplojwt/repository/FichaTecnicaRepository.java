package school.sptech.exemplojwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.exemplojwt.entity.FichaTecnica;

import java.util.List;

public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {

    boolean existsByIngredienteId(Long ingredienteId);

    void deleteByProdutoId(Long produtoId);

    List<FichaTecnica> findAllByOrderByProdutoNomeAscIngredienteNomeAsc();
}