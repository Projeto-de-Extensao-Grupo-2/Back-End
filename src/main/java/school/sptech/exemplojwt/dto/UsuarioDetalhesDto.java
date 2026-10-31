package school.sptech.exemplojwt.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.exemplojwt.entity.Usuario;

import java.util.Collection;
import java.util.List;

public class UsuarioDetalhesDto implements UserDetails {

  private final String nome;

  private final String email;

  private final String senha;

  private final Cargo cargo;
  private final boolean ativo;

  public UsuarioDetalhesDto(Usuario usuario) {
    this.nome = usuario.getNome();
    this.email = usuario.getEmail();
    this.senha = usuario.getSenha();
    this.cargo = usuario.getCargo();
    this.ativo = usuario.isAtivo();
  }

  public String getNome() {
    return nome;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + cargo.name()));
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return ativo;
  }
}
