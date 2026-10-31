package school.sptech.exemplojwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.exemplojwt.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}