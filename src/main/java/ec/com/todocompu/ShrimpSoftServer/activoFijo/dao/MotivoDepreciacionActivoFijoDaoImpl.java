package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfDepreciacionesMotivos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class MotivoDepreciacionActivoFijoDaoImpl extends GenericDaoImpl<AfDepreciacionesMotivos, Integer> implements MotivoDepreciacionActivoFijoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean eliminarAfDepreciacionesMotivos(AfDepreciacionesMotivos afDepreciacionesMotivos, SisSuceso sisSuceso) throws Exception {
        eliminar(afDepreciacionesMotivos);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public AfDepreciacionesMotivos getAfDepreciacionesMotivos(Integer codigo) throws Exception {
        return obtenerPorId(AfDepreciacionesMotivos.class, codigo);
    }

    @Override
    public AfDepreciacionesMotivos getAfDepreciacionesMotivosUbicacion(String empresa, String codigo) throws Exception {
        List<AfDepreciacionesMotivos> afDepreciacionesMotivosList = null;
        AfDepreciacionesMotivos afDepreciacionesMotivos = null;
        afDepreciacionesMotivosList = obtenerPorHql("select c from AfDepreciacionesMotivos c inner join c.afUbicaciones ti where ti.afUbicacionesPK.ubiEmpresa=?1 and ti.afUbicacionesPK.ubiCodigo=?2", new Object[]{empresa, codigo});
        if (afDepreciacionesMotivosList.size() > 0) {
            afDepreciacionesMotivos = afDepreciacionesMotivosList.get(0);
        }

        return afDepreciacionesMotivos;
    }

    @Override
    public AfDepreciacionesMotivos getAfDepreciacionesMotivosCategoria(String empresa, String codigo) throws Exception {
        List<AfDepreciacionesMotivos> afDepreciacionesMotivosList = null;
        AfDepreciacionesMotivos afDepreciacionesMotivos = null;
        afDepreciacionesMotivosList = obtenerPorHql("select c from AfDepreciacionesMotivos c inner join c.conTipo ti where ti.conTipoPK.tipEmpresa=?1 and ti.conTipoPK.tipCodigo=?2", new Object[]{empresa, codigo});
        if (afDepreciacionesMotivosList.size() > 0) {
            afDepreciacionesMotivos = afDepreciacionesMotivosList.get(0);
        }
        return afDepreciacionesMotivos;
    }

    @Override
    public boolean insertarAfDepreciacionesMotivos(AfDepreciacionesMotivos afDepreciacionesMotivos, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(afDepreciacionesMotivos);
        return true;
    }

    @Override
    public boolean modificarAfDepreciacionesMotivos(AfDepreciacionesMotivos afDepreciacionesMotivos, SisSuceso sisSuceso) throws Exception {

        sucesoDao.insertar(sisSuceso);
        actualizar(afDepreciacionesMotivos);
        return true;
    }

    @Override
    public List<AfDepreciacionesMotivos> getListaAfDepreciacionesMotivos(String empresa) throws Exception {
        return obtenerPorHql("select c from AfDepreciacionesMotivos c inner join c.conTipo ti where ti.conTipoPK.tipEmpresa=?1", new Object[]{empresa});
    }

}
