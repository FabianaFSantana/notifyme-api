package notifyme.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import notifyme.api.model.Notificacao;
import notifyme.api.model.Usuario;

@Service
public class TwilioSmsService {
    
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    private final String THUILIO_PHONE_NUMBER = "+13344497765";

    public void enviarSms(String numeroDestino, String mensagem) {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
            new PhoneNumber(numeroDestino), 
            new PhoneNumber(THUILIO_PHONE_NUMBER), 
            mensagem).create();
    }

    public void enviarNotificacaoPorSms(Usuario usuario, Notificacao notificacao) {

        String mensagemSms = "Olá " + usuario.getNome() + "! Você recebeu uma nova mensagem! \n\n" +
                             "Título: " + notificacao.getTitulo() + "\n" +
                             "Mensagem: " + notificacao.getMensagem() + "\n" +
                             "Data: " + notificacao.getDataCriacao() + "\n";
        
        enviarSms(usuario.getTelefone(), mensagemSms);
    }
}
