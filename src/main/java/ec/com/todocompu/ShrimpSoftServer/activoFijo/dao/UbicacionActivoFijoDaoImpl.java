package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfUbicaciones;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfUbicacionesPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class UbicacionActivoFijoDaoImpl extends GenericDaoImpl<AfUbicaciones, AfUbicacionesPK> implements UbicacionActivoFijoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean eliminarAfUbicaciones(AfUbicaciones afUbicaciones, SisSuceso sisSuceso) throws Exception {
        eliminar(afUbicaciones);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public AfUbicaciones getAfUbicaciones(String empresa, String sector, String codigo) throws Exception {
        return obtenerPorId(AfUbicaciones.class, new AfUbicacionesPK(empresa, sector, codigo));
    }

    @Override
    public AfUbicaciones getAfUbicacionesPorDescripcion(String empresa, String descripcion) throws Exception {
        List<AfUbicaciones> listado = obtenerPorHql("select c from AfUbicaciones c where c.afUbicacionesPK.ubiEmpresa=?1 AND c.ubiDescripcion=?2", new Object[]{empresa, descripcion});
        if (listado != null && listado.size() > 0) {
            return listado.get(0);
        }

        return null;
    }

    @Override
    public boolean insertarAfUbicaciones(AfUbicaciones afUbicaciones, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(afUbicaciones);
        return true;
    }

    @Override
    public boolean modificarAfUbicaciones(AfUbicaciones afUbicaciones, SisSuceso sisSuceso) throws Exception {

        sucesoDao.insertar(sisSuceso);
        actualizar(afUbicaciones);
        return true;
    }

    @Override
    public List<AfUbicaciones> getListaUbicaciones(String empresa) throws Exception {
        return obtenerPorHql("select c from AfUbicaciones c where c.afUbicacionesPK.ubiEmpresa=?1", new Object[]{empresa});
    }

}
