package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisTareasProgramadas;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface TareasProgramadasService {

    @Transactional
    public List<SisTareasProgramadas> listarConfTareasProgramadas(String tipo) throws Exception;
}
