package school.sptech.exemplojwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.exemplojwt.entity.Mesa;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

    boolean existsByNumero(Integer numero);
}