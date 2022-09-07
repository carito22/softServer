package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisTareasProgramadas;
import java.util.List;

public interface TareasProgramadasDao extends GenericDao<SisTareasProgramadas, Integer> {

    public List<SisTareasProgramadas> listarConfTareasProgramadas(String tipo) throws Exception;
}
