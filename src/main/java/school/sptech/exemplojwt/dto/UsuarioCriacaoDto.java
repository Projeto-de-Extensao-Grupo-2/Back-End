package school.sptech.exemplojwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro de um usuário")
public class UsuarioCriacaoDto {

  @Size(min = 3, max = 100)
  @Schema(description = "Nome completo do usuário (3 a 100 caracteres)", example = "John Doe")
  private String nome;

  @Email
  @Schema(description = "Email do usuário", example = "john@doe.com")
  private String email;

  @Size(min = 6, max = 20)
  @Schema(description = "Senha do usuário", example = "123456")
  private String senha;

  @NotNull
  @Schema(description = "Cargo do usuário", example = "GARCOM")
  private Cargo cargo;

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

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Cargo getCargo() { return cargo; }

  public void setCargo(Cargo cargo) { this.cargo = cargo; }
}
