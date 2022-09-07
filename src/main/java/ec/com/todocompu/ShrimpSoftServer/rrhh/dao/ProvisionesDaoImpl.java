package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhProvisiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KevinQuispe
 */
@Repository
public class ProvisionesDaoImpl extends GenericDaoImpl<RhProvisiones, String> implements ProvisionesDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public RhProvisiones listarRhProvisiones(String empresa) throws Exception {
        String sql = "";
        sql = "select*from recursoshumanos.rh_provisiones where prov_empresa='" + empresa + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, RhProvisiones.class);
    }

    @Override
    public RhProvisiones insertarRhProvisiones(RhProvisiones rhProvisiones, SisSuceso sisSuceso) throws Exception {
        insertar(rhProvisiones);
        sucesoDao.insertar(sisSuceso);
        return rhProvisiones;
    }

    @Override
    public RhProvisiones modificarRhProvisiones(RhProvisiones rhProvisiones, SisSuceso sisSuceso) throws Exception {
        actualizar(rhProvisiones);
        sucesoDao.insertar(sisSuceso);
        return rhProvisiones;
    }
}
