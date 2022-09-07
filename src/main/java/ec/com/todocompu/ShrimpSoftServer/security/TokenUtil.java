package ec.com.todocompu.ShrimpSoftServer.security;

import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.servlet.http.*;
import java.util.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TokenUtil {

    private static final long VALIDITY_TIME_MS = 10 * 24 * 60 * 60 * 1000;// 10 days Validity
    private static final String AUTH_HEADER_NAME = "Authorization";
    @Autowired
    private GenericoDao<SisUsuario, String> usuarioDao;

    private final String secret = "mrin";

    public Optional<Authentication> verifyToken(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null && !token.isEmpty()) {
            final TokenUser user = parseUserFromToken(token.replace("Bearer", "").trim());
            if (user != null) {
                return Optional.of(new UserAuthentication(user));
            }
        }
        return Optional.empty();
    }

    public TokenUser parseUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        SisUsuario user = new SisUsuario();
        user.setUsrNick((String) claims.get("userId"));
        user.setUsrPassword((String) claims.get("sub"));
        return new TokenUser(user);
    }

    public SisUsuario parseTokenFromUser(HttpServletRequest request) {
        String token = request.getHeader(TokenUtil.AUTH_HEADER_NAME);
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            SisUsuario user = new SisUsuario();
            user.setUsrNick((String) claims.get("userId"));
            user.setUsrPassword((String) claims.get("sub"));
            return user;
        } else {
            return null;
        }
    }

    public String createTokenForUser(TokenUser tokenUser) {
        return createTokenForUser(tokenUser.getUsuario());
    }

    public String createTokenForUser(SisUsuario user) {
        Criterio filtro;
        filtro = Criterio.forClass(SisUsuario.class);
        filtro.add(Restrictions.eq("usrNick", user.getUsrNick()));
        SisUsuario userTrue = usuarioDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
                .setSubject(userTrue.getUsrNick())
                .claim("userId", userTrue.getUsrNick())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

}
