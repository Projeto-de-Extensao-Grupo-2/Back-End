package school.sptech.exemplojwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.exemplojwt.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByMesaId(Long mesaId);
}