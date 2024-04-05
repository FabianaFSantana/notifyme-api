package notifyme.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notifyme.api.constant.Status;
import notifyme.api.model.Notificacao;
import notifyme.api.repository.NotificacaoRepository;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    //Para disserializar o formato da data e criar a notificação
    public void criarNotificacao(Map<String, Object> requestBody) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataCriacaoString = (String) requestBody.get("dataCriacao");
        LocalDateTime dataCriacao = LocalDateTime.parse(dataCriacaoString, formatter);

        String titulo = (String) requestBody.get("titulo");

        Notificacao novaNotificacao = new Notificacao();
        novaNotificacao.setTitulo(titulo);
        novaNotificacao.setMensagem((String) requestBody.get("mensagem"));
        novaNotificacao.setDataCriacao(dataCriacao);
        novaNotificacao.setStatus(Status.CRIADA);
        notificacaoRepository.save(novaNotificacao);

    }
    
}
