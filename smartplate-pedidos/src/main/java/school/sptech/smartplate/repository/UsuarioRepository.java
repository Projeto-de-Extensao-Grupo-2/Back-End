package school.sptech.smartplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.smartplate.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
