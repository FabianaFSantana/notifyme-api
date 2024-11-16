package notifyme.api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import notifyme.api.model.Usuario;

@Service
public class TokenService {
    
    public String gerarToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                  .withIssuer(TOKEN_ISSUER)
                  .withSubject(usuario.getEmail())
                  .withExpiresAt(_gerarDataExpiracao())
                  .withClaim("nome", usuario.getNome())
                  .sign(algorithm);
    }

    public String obterEmailUsuario(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.require(algorithm)
                  .withIssuer(TOKEN_ISSUER)
                  .build()
                  .verify(token)
                  .getSubject();
    }

    public Instant _gerarDataExpiracao() {
        return LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
    }

    @Value("${spring.security.secret-key}")
    private String SECRET_KEY;

    @Value("${spring.security.token-issuer}")
    private String TOKEN_ISSUER;

}
