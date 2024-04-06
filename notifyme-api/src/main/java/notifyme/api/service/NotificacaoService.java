package notifyme.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
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

        //Para que o título não seja nulo no processo de disserialização
        String titulo = (String) requestBody.get("titulo");

        Notificacao novaNotificacao = new Notificacao();
        novaNotificacao.setTitulo(titulo);
        novaNotificacao.setMensagem((String) requestBody.get("mensagem"));
        novaNotificacao.setDataCriacao(dataCriacao);
        novaNotificacao.setStatus(Status.CRIADA);
        notificacaoRepository.save(novaNotificacao);

    }

    public void atualizarNotificacao(Long id, Map<String, Object> requestBody) {
        Optional<Notificacao> notifOptional = notificacaoRepository.findById(id);

        if (notifOptional.isPresent()) {
            Notificacao notifEncontr = notifOptional.get();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataCriacaoString = (String) requestBody.get("dataCriacao");
            LocalDateTime dataCriacao = LocalDateTime.parse(dataCriacaoString, formatter);

            String titulo = (String) requestBody.get("titulo");
            String mensagem = (String) requestBody.get("mensagem");

            notifEncontr.setTitulo(titulo);
            notifEncontr.setMensagem(mensagem);
            notifEncontr.setDataCriacao(dataCriacao);

            notificacaoRepository.save(notifEncontr);
        } else {
            throw new EntityNotFoundException("Notificação não foi encontrada.");
        }
        
       
    }
    
}