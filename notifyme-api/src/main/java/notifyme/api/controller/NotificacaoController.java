package notifyme.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
import notifyme.api.repository.NotificacaoRepository;
import notifyme.api.service.NotificacaoService;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @PostMapping
    public ResponseEntity<?> criarNotificacao(@RequestBody Map<String, Object> requestBody) {
        notificacaoService.criarNotificacao(requestBody);
        return ResponseEntity.ok("Notificação criada com sucesso.");
    }

    @PostMapping("/{idUsuario}/adicionarNotificacaoListaUsuario/{id}")
    public ResponseEntity<String> adicionarNotificacaoListaUsuario(@PathVariable("idUsuario") Long idUsuario,
    @PathVariable("id") Long id) {
        notificacaoService.adicionarNotificacaoNaLista(idUsuario, id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Notificação adicionada na lista de notificações do Usuário.");
    }

    @GetMapping
    public ResponseEntity<List<Notificacao>> exibirListaDeNotificacoes() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(notificacaoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Notificacao>> buscarNotificacaoPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(notificacaoRepository.findById(id));
    }

    @GetMapping("/dataCriacao/{dataCriacao}")
    public ResponseEntity<Notificacao> buscarNotificacaoPelaDataCriacao(@PathVariable("dataCriacao") LocalDateTime dataCriacao) {

        Optional<Notificacao> notifOptional = notificacaoRepository.findByDataCriacao(dataCriacao);

        if (notifOptional.isPresent()) {
            Notificacao notifEncont = notifOptional.get();
            return ResponseEntity.status(HttpStatus.OK)
            .body(notifEncont);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dataCriacao/data/{data}")
    public ResponseEntity<List<Notificacao>> buscarTodasNotificacoesDaData(@PathVariable("data") LocalDate data) {
        List<Notificacao> notificacoes = notificacaoRepository.findByListDataCriacao(data);

        if (!notificacoes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
            .body(notificacoes);
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idUsuario}/enviarNotificacaoPorEmail/{id}")
    public ResponseEntity<String> enviarNotificacaoPorEmail(@PathVariable("idUsuario") Long idUsuario,
    @PathVariable("id") Long id) {
        notificacaoService.enviarNotificacaoPorEmail(idUsuario, id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Notificação enviada para o email do usuário.");
    }

    @GetMapping("/{idUsuario}/enviarNotificacaoPorSms/{id}")
    public ResponseEntity<String> enviarNotificacaoPorSms(@PathVariable("idUsuario") Long idUsuario,
    @PathVariable("id") Long id) {
        notificacaoService.enviarNotificacaoPorSms(idUsuario, id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Notificação enviada por SMS para o usuário.");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarNotificacao(@PathVariable("id") Long id,
    @RequestBody Map<String, Object> requestBody) {
        try {
            notificacaoService.atualizarNotificacao(id, requestBody);
            return ResponseEntity.status(HttpStatus.OK)
            .body("Notificação atualizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
  
    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirNotificacao(@PathVariable("id") Long id) {
        notificacaoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Notificação excluída com sucesso!");
    }
    
}
