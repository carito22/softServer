package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.TareasProgramadasDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisTareasProgramadas;
import java.util.List;

@Service
public class TareasProgramadasServiceImpl implements TareasProgramadasService {

    @Autowired
    private TareasProgramadasDao tareasProgramadasDao;

    @Override
    public List<SisTareasProgramadas> listarConfTareasProgramadas(String tipo) throws Exception {
        return tareasProgramadasDao.listarConfTareasProgramadas(tipo);
    }

}
