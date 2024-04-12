package notifyme.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.persistence.EntityNotFoundException;
import notifyme.api.constant.Status;
import notifyme.api.model.Notificacao;
import notifyme.api.model.Usuario;
import notifyme.api.repository.NotificacaoRepository;

@Service
public class TwilioSmsService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;
    
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    private final String THUILIO_PHONE_NUMBER = "+13344497765";

    public void enviarSms(String numeroDestino, String mensagem) {
        Twilio.init(accountSid, authToken);

        try {
            Message message = Message.creator(
                new PhoneNumber(numeroDestino), 
                new PhoneNumber(THUILIO_PHONE_NUMBER), 
                mensagem).create();
        

        } catch (Exception e) {
            e.printStackTrace();
           
        }
       
    }

    public void enviarNotificacaoPorSms(Usuario usuario, Notificacao notificacao) {

        if (!notificacao.getStatus().equals(Status.CRIADA)) {
            throw new IllegalStateException("A notificação esperando para ser enviada.");
        }

        try {
            String mensagemSms = "Olá " + usuario.getNome() + "! Você recebeu uma nova mensagem! \n\n" +
                             "Título: " + notificacao.getTitulo() + "\n" +
                             "Mensagem: " + notificacao.getMensagem() + "\n" +
                             "Data: " + notificacao.getDataCriacao() + "\n";
        
            enviarSms(usuario.getTelefone(), mensagemSms);

            notificacao.setStatus(Status.ENVIADA);
            notificacaoRepository.save(notificacao);
            
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar a notificação por SMS", e);
        }
        
    }
}
