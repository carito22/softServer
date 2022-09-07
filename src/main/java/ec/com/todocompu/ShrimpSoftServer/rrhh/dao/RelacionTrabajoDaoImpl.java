package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRelacionTrabajo;
import java.util.List;

@Repository
public class RelacionTrabajoDaoImpl extends GenericDaoImpl<RhRelacionTrabajo, String> implements RelacionTrabajoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<RhRelacionTrabajo> listaRelacionTrabajo() throws Exception {
        String sql = "SELECT * FROM recursoshumanos.rh_relacion_trabajo ORDER BY rt_descripcion;";
        return genericSQLDao.obtenerPorSql(sql, RhRelacionTrabajo.class);
    }

}
