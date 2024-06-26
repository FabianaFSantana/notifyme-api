package notifyme.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import notifyme.api.model.Notificacao;
import notifyme.api.model.Usuario;
import notifyme.api.repository.UsuarioRepository;
import notifyme.api.service.NotificacaoService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacaoService notificacaoService;
    
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

    @GetMapping("/exibirListaDeNotificacoes/{idUsuario}")
    public ResponseEntity<List<Notificacao>> exibirListaNotificacoes(@PathVariable("idUsuario") Long idUsuario) {
        List<Notificacao> notificacoes = notificacaoService.exibirListaDeNotificacoesDoUsuario(idUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(notificacoes);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> atualizarDadosUsuario(@PathVariable("idUsuario") Long idUsuario,
    @RequestBody Usuario usuario) {
        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);

        if (usuarOptional.isPresent()) {
            Usuario usuarioEncont = usuarOptional.get();

            usuarioEncont.setNome(usuario.getNome());
            usuarioEncont.setEmail(usuario.getEmail());
            usuarioEncont.setTelefone(usuario.getTelefone());

            return ResponseEntity.status(HttpStatus.OK)
            .body(usuarioRepository.save(usuarioEncont));
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<String> excluirUsuario(@PathVariable("idUsuario") Long idUsuario) {
        
        usuarioRepository.deleteById(idUsuario);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Usuário excluído com sucesso.");

    }

    @DeleteMapping("/{idUsuario}/removerNotificacao/{id}")
    public ResponseEntity<String> removerNotificacaoDaLista(@PathVariable("idUsuario") Long idUsuario,
    @PathVariable("id") Long id) {
        notificacaoService.removerNotificacaoDaListaDoUsuario(idUsuario, id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Usuario Removido com sucesso.");
    }


    
}
