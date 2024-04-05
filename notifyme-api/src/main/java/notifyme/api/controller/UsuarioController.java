package notifyme.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import notifyme.api.model.Usuario;
import notifyme.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(usuarioRepository.save(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> exbirListaDeUsuarios() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(usuarioRepository.findAll());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Optional<Usuario>> buscarUsuarioPeloId(@PathVariable("idUsuario") Long idUsuario) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(usuarioRepository.findById(idUsuario));
    }

    
    
}
