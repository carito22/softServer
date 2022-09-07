package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConEstructura;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class EstructuraDaoImpl extends GenericDaoImpl<ConEstructura, String> implements EstructuraDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<ConEstructuraTO> getListaConEstructuraTO(String empresa) throws Exception {
        String sql = "SELECT * FROM contabilidad.con_estructura WHERE " + "est_empresa = ('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, ConEstructuraTO.class);
    }

    @Override
    public boolean insertarConEstructura(ConEstructura conEstructura, SisSuceso sisSuceso) throws Exception {
        insertar(conEstructura);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarConEstructura(ConEstructura conEstructura, SisSuceso sisSuceso) throws Exception {
        actualizar(conEstructura);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarConEstructura(ConEstructura conEstructura, SisSuceso sisSuceso) throws Exception {
        eliminar(conEstructura);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
