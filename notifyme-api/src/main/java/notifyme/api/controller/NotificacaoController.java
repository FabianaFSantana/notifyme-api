package notifyme.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
