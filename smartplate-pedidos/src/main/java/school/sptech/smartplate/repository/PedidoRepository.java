package school.sptech.smartplate.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.smartplate.entity.Pedido;
import school.sptech.smartplate.enums.StatusPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("""
            select p from Pedido p
            join fetch p.mesa m
            left join fetch p.garcom g
            where (:status is null or p.status = :status)
              and (:numeroMesa is null or m.numero = :numeroMesa)
              and (:garcomId is null or g.id = :garcomId)
              and (:inicio is null or p.criadoEm >= :inicio)
              and (:fim is null or p.criadoEm < :fim)
            order by p.criadoEm desc
            """)
    List<Pedido> filtrar(
            @Param("status") StatusPedido status,
            @Param("numeroMesa") Integer numeroMesa,
            @Param("garcomId") Long garcomId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);

    @Modifying
    @Transactional
    @Query(value = "delete from itens_pedido where id_pedido = :pedidoId", nativeQuery = true)
    void deleteItensByPedidoId(@Param("pedidoId") Long pedidoId);
}
