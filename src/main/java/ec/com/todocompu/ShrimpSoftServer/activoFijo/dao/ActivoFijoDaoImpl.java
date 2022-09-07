package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfListadoActivoFijoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ActivoFijoDaoImpl extends GenericDaoImpl<AfActivos, AfActivosPK> implements ActivoFijoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean eliminarAfActivos(AfActivos afActivos, SisSuceso sisSuceso) throws Exception {
        eliminar(afActivos);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public AfActivos getAfActivos(String empresa, String codigo) throws Exception {
        return obtenerPorId(AfActivos.class, new AfActivosPK(empresa, codigo));
    }

    @Override
    public boolean insertarAfActivos(AfActivos afActivos, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(afActivos);
        return true;
    }

    @Override
    public boolean modificarAfActivos(AfActivos afActivos, SisSuceso sisSuceso) throws Exception {

        sucesoDao.insertar(sisSuceso);
        actualizar(afActivos);
        return true;
    }

    @Override
    public List<AfActivos> getListaActivos(String empresa, String sector, String ubicacion) throws Exception {
        if (!ubicacion.equalsIgnoreCase("")) {
            return obtenerPorHql("select af from AfActivos af inner join af.afUbicaciones ubi "
                    + "where ubi.afUbicacionesPK.ubiEmpresa=?1 and ubi.afUbicacionesPK.ubiCodigo=?2", new Object[]{empresa, ubicacion});
        } else {
            return obtenerPorHql("select c from AfActivos c where c.afActivosPK.afEmpresa=?1", new Object[]{empresa});
        }
    }

     @Override
    public List<AfListadoActivoFijoTO> getFunlistarActivoFijosCompras(String empresa) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        String sql = "SELECT * FROM activosfijos.fun_activo_fijo(" + empresa +  ")";
        List<AfListadoActivoFijoTO> prd = genericSQLDao.obtenerPorSql(sql, AfListadoActivoFijoTO.class);
        return prd;
    }
}
