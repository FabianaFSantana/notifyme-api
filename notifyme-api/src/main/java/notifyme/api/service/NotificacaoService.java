package notifyme.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import notifyme.api.model.Notificacao;
import notifyme.api.model.Usuario;
import notifyme.api.repository.NotificacaoRepository;
import notifyme.api.repository.UsuarioRepository;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TwilioSmsService twilioSmsService;

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

    public void adicionarNotificacaoNaLista(Long idUsuario, Long id) {
        
        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);
        if (usuarOptional.isPresent()) {
            Usuario usuarioEncont = usuarOptional.get();

            Optional<Notificacao> notifOptional = notificacaoRepository.findById(id);
            if (notifOptional.isPresent()) {
                Notificacao notifEncont = notifOptional.get();

                List<Notificacao> notificacoes = usuarioEncont.getNotificacoes();
                notificacoes.add(notifEncont);
                usuarioRepository.save(usuarioEncont);
                
            } else {
                throw new EntityNotFoundException("Notificação não econtrada.");
            }
            
        } else {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
    }

    public List<Notificacao> exibirListaDeNotificacoesDoUsuario(Long idUsuario) {
        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);
        
        if (usuarOptional.isPresent()) {
            Usuario usuarioEncont = usuarOptional.get();
            return usuarioEncont.getNotificacoes();
        } else {
            return Collections.emptyList();
        }
    }

    public String removerNotificacaoDaListaDoUsuario(Long idUsuario, Long id) {
        
        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);
        if (usuarOptional.isPresent()) {
            Usuario usuarioEncont = usuarOptional.get();

            Optional<Notificacao> notifOptional = notificacaoRepository.findById(id);
            if (notifOptional.isPresent()) {
                Notificacao notifEncont = notifOptional.get();

                List<Notificacao> notificacoes = usuarioEncont.getNotificacoes();
                notificacoes.remove(notifEncont);
                usuarioRepository.save(usuarioEncont);
                return "Notificação removida com sucesso!";
                
            } else {
                return "Notificação não encontrada.";
            }
            
        } else {
            return "Usuario não encontrado.";
        }

    }

    public void enviarNotificacaoPorEmail(Long idUsuario, Long id) {

        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);
        Optional<Notificacao> notifOptional = notificacaoRepository.findById(id);

        if (usuarOptional.isPresent() && notifOptional.isPresent()) {
            Usuario usuario = usuarOptional.get();
            Notificacao notificacao = notifOptional.get();

            emailService.enviarNotificacaoPorEmail(usuario, notificacao);
            
        } else {
            throw new EntityNotFoundException("Usuario ou notificação não encontrados.");
        }

    }

    public void enviarNotificacaoPorSms(Long idUsuario, Long id) {

        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);
        Optional<Notificacao> notifOptional = notificacaoRepository.findById(id);

        if (usuarOptional.isPresent() && notifOptional.isPresent()) {
            Usuario usuario = usuarOptional.get();
            Notificacao notificacao = notifOptional.get();

            twilioSmsService.enviarNotificacaoPorSms(usuario, notificacao);

        } else {
            throw new EntityNotFoundException("Usuário ou notificação não encontrados.");
        }
    }
    
}
