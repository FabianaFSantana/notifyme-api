package notifyme.api.model;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(name = "tb_usuarios")
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;
    
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Embedded
    private Endereco endereco;

    @NotBlank(message = "O email é obrigatório.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Pattern(regexp = "^(?=.*[A-Z]).{8,}$", message = "A senha deve conter no mínimo 8 caracteres e uma letra maiúscula.")
    @Column(nullable = false)
    private String senha;

    @NotBlank(message = "O telefone é obrigatório.")
    @Size(min = 10, max = 20, message = "O telefone deve ter 10 a 20 caracteres.")
    @Pattern(regexp = "\\+?[0-9]+", message = "O telefone deve conter apenas números.")
    @Column(nullable = false)
    private String telefone;

    @OneToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<Notificacao> notificacoes;
   
    @Column(nullable = false)
    private Boolean administrador;

    @Column(nullable = false)
    private Boolean usuarioExterno;

    public Usuario() {
        this.administrador = Boolean.FALSE;
        this.usuarioExterno = Boolean.TRUE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       if (administrador) {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
       }

       if (usuarioExterno) {
        return List.of(new SimpleGrantedAuthority("ROLE_EXT_USER"));
       }

       return null;
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
        return true;
    }
    
}
