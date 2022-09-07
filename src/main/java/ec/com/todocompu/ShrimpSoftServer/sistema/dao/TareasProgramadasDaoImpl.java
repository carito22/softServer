package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisTareasProgramadas;
import java.util.List;

@Repository
public class TareasProgramadasDaoImpl extends GenericDaoImpl<SisTareasProgramadas, Integer> implements TareasProgramadasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisTareasProgramadas> listarConfTareasProgramadas(String tipo) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_tareas_programadas where tar_tipo='" + tipo + "'";
        return genericSQLDao.obtenerPorSql(sql, SisTareasProgramadas.class);
    }

}
