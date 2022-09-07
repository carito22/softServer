package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControlPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class EquipoControlDaoImpl extends GenericDaoImpl<PrdEquipoControl, PrdEquipoControlPK> implements EquipoControlDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public boolean insertarEquipoControl(PrdEquipoControl prdEquipoControl, SisSuceso sisSuceso)
            throws Exception {
        insertar(prdEquipoControl);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<PrdEquipoControl> listarEquiposControl(String empresa) throws Exception {
        String sql = "SELECT * FROM produccion.prd_equipo_control WHERE ec_empresa = '" + empresa + "' ";
        return genericSQLDao.obtenerPorSql(sql, PrdEquipoControl.class);
    }

    @Override
    public boolean actualizarEquipoControl(PrdEquipoControl prdEquipoControl, SisSuceso sisSuceso) throws Exception {
        actualizar(prdEquipoControl);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public PrdEquipoControl obtenerEquipoControl(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM produccion.prd_equipo_control WHERE ec_empresa = '" + empresa + "' AND ec_codigo = '" + codigo + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdEquipoControl.class);
    }

}
