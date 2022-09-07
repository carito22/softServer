package ec.com.todocompu.ShrimpSoftServer.security;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SecurityService {

    public SessionResponse obtenerSessionRespuesta(SessionResponse resp, String username, String password) throws Exception;

    public boolean logout(SisInfoTO sisInfoTO) throws Exception;

}
