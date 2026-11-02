package school.sptech.exemplojwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exemplojwt.config.swagger.ErroResponse;
import school.sptech.exemplojwt.dto.*;
import school.sptech.exemplojwt.entity.Usuario;
import school.sptech.exemplojwt.service.UsuarioService;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
@Tag(name = "Usuários", description = "Cadastro, login/logout e ativação de usuários")
public class UsuarioController {

    public static final String COOKIE_NOME = "authToken";

    @Value("${jwt.validity}")
    private long jwtValidity;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Cadastrar usuário",
            description = "Cria um novo usuário (garçom ou gestor). A senha é armazenada criptografada (BCrypt)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (nome, e-mail, senha ou cargo)",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content)
    })
    public ResponseEntity<Void> criar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto) {
        final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);
        this.usuarioService.criar(novoUsuario);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    @Operation(
            summary = "Autenticar usuário (login)",
            description = """
                    Valida e-mail e senha e devolve um **JWT** no corpo da resposta. \
                    Além disso, grava o cookie HttpOnly `authToken` (SameSite=Strict, path `/`) \
                    com a mesma validade do token (propriedade `jwt.validity`, padrão 3600 s)."""
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    headers = @Header(name = "Set-Cookie", description = "Cookie `authToken` contendo o JWT",
                            schema = @Schema(type = "string")),
                    content = @Content(schema = @Schema(implementation = UsuarioTokenDto.class))),
            @ApiResponse(responseCode = "400", description = "Corpo inválido (e-mail mal formado ou campo em branco)",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha inválidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário inativo ou e-mail não cadastrado", content = @Content)
    })
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto,
                                                 HttpServletResponse response) {
        final Usuario usuario = UsuarioMapper.of(usuarioLoginDto);
        UsuarioTokenDto autenticado = this.usuarioService.autenticar(usuario);

        ResponseCookie cookie = ResponseCookie.from(COOKIE_NOME, autenticado.getToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofSeconds(jwtValidity))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.status(200).body(autenticado);
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Encerrar sessão (logout)",
            description = "Expira o cookie `authToken` no navegador. Não invalida o JWT já emitido: " +
                    "ele continua válido até expirar caso tenha sido copiado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout realizado; cookie removido",
                    headers = @Header(name = "Set-Cookie", description = "Cookie `authToken` expirado (Max-Age=0)",
                            schema = @Schema(type = "string")),
                    content = @Content)
    })
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NOME, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.status(204).build();
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar usuários", description = "Retorna todos os usuários cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioListarDto.class)))),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário cadastrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content)
    })
    public ResponseEntity<List<UsuarioListarDto>> listarTodos() {
        List<UsuarioListarDto> usuariosEncontrados = this.usuarioService.listarTodos();

        if (usuariosEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuariosEncontrados);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Ativar ou inativar usuário",
            description = "Atualiza o campo `ativo` do usuário. Usuários inativos não conseguem fazer login " +
                    "e não podem ser atribuídos como garçom de um pedido."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Status atualizado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Campo `ativo` ausente",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResponseEntity<Void> atualizarAtivo(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long id,
            @RequestBody @Valid UsuarioAtivoDto dto) {
        usuarioService.atualizarAtivo(id, dto.getAtivo());
        return ResponseEntity.status(204).build();
    }
}
