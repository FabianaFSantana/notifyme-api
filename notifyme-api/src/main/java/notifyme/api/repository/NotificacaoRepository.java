package notifyme.api.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import notifyme.api.model.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    
    @Query("SELECT n FROM notificacao n WHERE n.dataCriacao = :dataCriacao")
    Optional<Notificacao> findByDataCriacao(@Param("dataCriacao") LocalDateTime dataCriacao);

    @Query("SELECT n FROM notificacao n WHERE DATE(n.dataCriacao) = :data")
    List<Notificacao> findByListDataCriacao(@Param("data") LocalDate data);
}
