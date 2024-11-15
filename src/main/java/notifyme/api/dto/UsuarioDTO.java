package notifyme.api.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notifyme.api.model.Endereco;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    
    private String nome;
    private LocalDate dataNascimento;
    private Endereco endereco;
    private String email;
    private Boolean administrador;
    private Boolean usuarioExterno;
    private String token;
}
