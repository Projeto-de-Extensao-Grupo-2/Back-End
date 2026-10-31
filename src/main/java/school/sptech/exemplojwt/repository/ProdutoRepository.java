package school.sptech.exemplojwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.exemplojwt.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}