package notifyme.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import notifyme.api.dto.EnderecoDTO;
import notifyme.api.dto.UsuarioDTO;
import notifyme.api.model.Endereco;
import notifyme.api.model.Usuario;
import notifyme.api.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    public UsuarioDTO salvarUsuario(Usuario usuario) {
        String senha = usuario.getSenha();

        BCryptPasswordEncoder encoder = authenticationService.getPasswordEncoder();

        String senhaCriptografada = encoder.encode(senha);

        usuario.setSenha(senhaCriptografada);

        Endereco endereco = usuario.getEndereco();
        EnderecoDTO enderecoDTO = viaCepEnderecoService.buscarEnderecoPeloCep(usuario.getEndereco().getCep());

        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setCidade(enderecoDTO.getLocalidade());
        endereco.setUf(enderecoDTO.getUf());

        return usuarioRepository.save(usuario).converterUsuarioDTO();
    }

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired 
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ViaCepEnderecoService viaCepEnderecoService;
}
