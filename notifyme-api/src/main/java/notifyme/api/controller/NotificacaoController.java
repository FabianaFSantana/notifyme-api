package notifyme.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
}
