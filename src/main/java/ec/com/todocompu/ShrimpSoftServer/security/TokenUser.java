package ec.com.todocompu.ShrimpSoftServer.security;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import org.springframework.security.core.authority.AuthorityUtils;

public class TokenUser extends org.springframework.security.core.userdetails.User {

    public SisUsuario user;

    public TokenUser(SisUsuario user) {
        super(user.getUsrNick(), user.getUsrPassword(), AuthorityUtils.createAuthorityList("ADMIN"));
        this.user = user;
    }

    public SisUsuario getUsuario() {
        return user;
    }

}
