package notifyme.api.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import notifyme.api.model.Notificacao;
import notifyme.api.model.Usuario;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int porta;

    @Value("${spring.mail.username}")
    private String usuario;

    @Value("${spring.mail.password}")
    private String senha;


    private void enviarEmail(Usuario usuario, Notificacao notificacao) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(usuario.getEmail());
        mailMessage.setSubject(notificacao.getTitulo());
        mailMessage.setText(notificacao.getMensagem());

        javaMailSender.send(mailMessage);
    }


    public void enviarNotificacaoPorEmail(Usuario usuario, Notificacao notificacao) throws MailException {

        String destinatario = usuario.getEmail();
        String assunto = "Nova notificação: " + notificacao.getTitulo();
        String corpo = "Olá, " + usuario.getNome() + "\n\n Você recebeu uma nova notificação.\n\n" +
                       "Título: " + notificacao.getTitulo() + "\n" +
                       "Mensagem: " + notificacao.getMensagem() + "\n" +
                       "Data: " + notificacao.getDataCriacao() + "\n";
        
        try {
            enviarEmail(usuario, notificacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
