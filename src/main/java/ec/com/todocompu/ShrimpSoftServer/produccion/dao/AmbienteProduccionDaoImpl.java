package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdAmbienteProduccion;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class AmbienteProduccionDaoImpl extends GenericDaoImpl<PrdAmbienteProduccion, Integer> implements AmbienteProduccionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean insertarAmbienteProduccion(PrdAmbienteProduccion prdAmbienteProduccion, SisSuceso sisSuceso) throws Exception {
        insertar(prdAmbienteProduccion);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<PrdAmbienteProduccion> getListarPrdAmbienteProduccion(String empresa) throws Exception {
        String sql = "SELECT * FROM produccion.prd_ambiente_produccion WHERE usr_empresa = '" + empresa + "'";
        List<PrdAmbienteProduccion> ambientes = genericSQLDao.obtenerPorSql(sql, PrdAmbienteProduccion.class);
        return ambientes;
    }
    
    @Override
    public boolean modificarAmbienteProduccion (PrdAmbienteProduccion prdAmbienteProduccion, SisSuceso sisSuceso) throws Exception {
        actualizar(prdAmbienteProduccion);
        sucesoDao.insertar(sisSuceso);
        return true;
    }
    
    @Override
    public boolean eliminarAmbienteProduccion (PrdAmbienteProduccion prdAmbienteProduccion, SisSuceso sisSuceso) throws Exception {
        eliminar(prdAmbienteProduccion);
        sucesoDao.insertar(sisSuceso);
        return true;
    }
}
