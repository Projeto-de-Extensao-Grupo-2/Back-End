package school.sptech.exemplojwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import school.sptech.exemplojwt.dto.Cargo;
import school.sptech.exemplojwt.entity.Usuario;
import school.sptech.exemplojwt.repository.UsuarioRepository;
import school.sptech.exemplojwt.service.AutenticacaoService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AutenticacaoService service;

    @Test
    @DisplayName("Deve carregar usuario pelo email")
    void deveCarregarUsuarioPeloEmail() {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("maria@email.com");
        usuario.setSenha("$2a$hash");
        usuario.setCargo(Cargo.GESTOR);
        Mockito.when(usuarioRepository.findByEmail("maria@email.com")).thenReturn(Optional.of(usuario));

        UserDetails resultado = service.loadUserByUsername("maria@email.com");

        Assertions.assertEquals("maria@email.com", resultado.getUsername());
        Assertions.assertEquals("$2a$hash", resultado.getPassword());
        Assertions.assertEquals("ROLE_GESTOR", resultado.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    @DisplayName("Deve lancar exception quando o usuario nao for encontrado")
    void deveLancarExceptionQuandoUsuarioNaoEncontrado() {
        Mockito.when(usuarioRepository.findByEmail("ninguem@email.com")).thenReturn(Optional.empty());
        UsernameNotFoundException exception = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("ninguem@email.com"));

        Assertions.assertEquals("usuário: ninguem@email.com não encontrado", exception.getMessage());
    }
}
