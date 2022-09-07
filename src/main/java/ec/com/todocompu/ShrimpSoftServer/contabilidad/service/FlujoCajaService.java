package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FlujoCajaService {

    public Map<String, Object> obtenerProyectoFlujoCaja(Map<String, Object> map) throws Exception;

}
