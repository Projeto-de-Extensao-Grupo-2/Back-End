package school.sptech.exemplojwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.exemplojwt.entity.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
}