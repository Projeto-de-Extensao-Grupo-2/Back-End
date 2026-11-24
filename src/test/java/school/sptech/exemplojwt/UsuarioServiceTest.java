package school.sptech.exemplojwt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.exemplojwt.Exceptions.UsuarioNaoExisteException;
import school.sptech.exemplojwt.config.GerenciadorTokenJwt;
import school.sptech.exemplojwt.dto.Cargo;
import school.sptech.exemplojwt.dto.UsuarioListarDto;
import school.sptech.exemplojwt.dto.UsuarioTokenDto;
import school.sptech.exemplojwt.entity.Usuario;
import school.sptech.exemplojwt.repository.UsuarioRepository;
import school.sptech.exemplojwt.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UsuarioService service;

    @AfterEach
    void limparContextoDeSeguranca() {
        SecurityContextHolder.clearContext();
    }

    private Usuario criarUsuario(Long id, String nome, String email, String senha) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setCargo(Cargo.GARCOM);
        return usuario;
    }


    @Test
    @DisplayName("Deve criptografar a senha antes de salvar o usuario")
    void deveCriptografarSenhaAntesDeSalvar() {

        Usuario novo = criarUsuario(null, "Maria", "maria@email.com", "senha123");
        Mockito.when(passwordEncoder.encode("senha123")).thenReturn("$2a$hash");


        service.criar(novo);


        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(captor.capture());
        Assertions.assertEquals("$2a$hash", captor.getValue().getSenha());
        Assertions.assertEquals("maria@email.com", captor.getValue().getEmail());
    }



    @Test
    @DisplayName("Deve autenticar usuario e retornar o token")
    void deveAutenticarUsuarioERetornarToken() {

        Usuario credenciais = criarUsuario(null, null, "maria@email.com", "senha123");
        Usuario noBanco = criarUsuario(1L, "Maria", "maria@email.com", "$2a$hash");
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("maria@email.com", null, List.of());

        Mockito.when(authenticationManager.authenticate(ArgumentMatchers.any(Authentication.class)))
                .thenReturn(authentication);
        Mockito.when(usuarioRepository.findByEmail("maria@email.com")).thenReturn(Optional.of(noBanco));
        Mockito.when(gerenciadorTokenJwt.generateToken(authentication)).thenReturn("token-jwt");


        UsuarioTokenDto resultado = service.autenticar(credenciais);


        Assertions.assertEquals(1L, resultado.getUserId());
        Assertions.assertEquals("Maria", resultado.getNome());
        Assertions.assertEquals("maria@email.com", resultado.getEmail());
        Assertions.assertEquals("token-jwt", resultado.getToken());
        Assertions.assertSame(authentication, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Deve lancar exception quando o email nao estiver cadastrado")
    void deveLancarExceptionQuandoEmailNaoCadastrado() {
        Usuario credenciais = criarUsuario(null, null, "ninguem@email.com", "senha123");
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("ninguem@email.com", null, List.of());
        Mockito.when(authenticationManager.authenticate(ArgumentMatchers.any(Authentication.class)))
                .thenReturn(authentication);
        Mockito.when(usuarioRepository.findByEmail("ninguem@email.com")).thenReturn(Optional.empty());


        UsuarioNaoExisteException exception = Assertions.assertThrows(
                UsuarioNaoExisteException.class,
                () -> service.autenticar(credenciais));

        Assertions.assertEquals("Email do usuário não cadastrado", exception.getMessage());
        Mockito.verify(gerenciadorTokenJwt, Mockito.never()).generateToken(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve propagar exception quando as credenciais forem invalidas")
    void devePropagarExceptionQuandoCredenciaisInvalidas() {
        Usuario credenciais = criarUsuario(null, null, "maria@email.com", "errada");
        Mockito.when(authenticationManager.authenticate(ArgumentMatchers.any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Usuário ou Senha inválidos"));


        Assertions.assertThrows(BadCredentialsException.class, () -> service.autenticar(credenciais));
        Mockito.verifyNoInteractions(usuarioRepository);
        Mockito.verifyNoInteractions(gerenciadorTokenJwt);
    }



    @Test
    @DisplayName("Deve listar todos os usuarios convertidos para DTO")
    void deveListarTodosOsUsuarios() {

        List<Usuario> usuarios = List.of(
                criarUsuario(1L, "Maria", "maria@email.com", "x"),
                criarUsuario(2L, "Joao", "joao@email.com", "y"));
        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);


        List<UsuarioListarDto> resultado = service.listarTodos();


        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("Maria", resultado.get(0).getNome());
        Assertions.assertEquals("joao@email.com", resultado.get(1).getEmail());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nao houver usuarios")
    void deveRetornarListaVaziaQuandoNaoHouverUsuarios() {

        Mockito.when(usuarioRepository.findAll()).thenReturn(List.of());


        List<UsuarioListarDto> resultado = service.listarTodos();


        Assertions.assertTrue(resultado.isEmpty());
    }



    @Test
    @DisplayName("Deve inativar usuario")
    void deveInativarUsuario() {

        Usuario usuario = criarUsuario(1L, "Maria", "maria@email.com", "x");
        usuario.setAtivo(true);
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));


        service.atualizarAtivo(1L, false);


        Assertions.assertFalse(usuario.isAtivo());
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario);
    }

    @Test
    @DisplayName("Deve reativar usuario")
    void deveReativarUsuario() {

        Usuario usuario = criarUsuario(1L, "Maria", "maria@email.com", "x");
        usuario.setAtivo(false);
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));


        service.atualizarAtivo(1L, true);


        Assertions.assertTrue(usuario.isAtivo());
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario);
    }

    @Test
    @DisplayName("Deve lancar exception ao atualizar usuario inexistente")
    void deveLancarExceptionAoAtualizarUsuarioInexistente() {
        Mockito.when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());


        UsuarioNaoExisteException exception = Assertions.assertThrows(
                UsuarioNaoExisteException.class,
                () -> service.atualizarAtivo(99L, true));

        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());
        Mockito.verify(usuarioRepository, Mockito.never()).save(ArgumentMatchers.any());
    }
}

