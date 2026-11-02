package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta do login: dados do usuário e token JWT")
public class UsuarioTokenDto {

  @Schema(description = "ID do usuário autenticado", example = "1")
  private Long userId;
  @Schema(description = "Nome do usuário", example = "John Doe")
  private String nome;
  @Schema(description = "E-mail do usuário", example = "john@doe.com")
  private String email;
  @Schema(description = "Token JWT para uso no header `Authorization: Bearer <token>`", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQGRvZS5jb20ifQ.assinatura")
  private String token;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
