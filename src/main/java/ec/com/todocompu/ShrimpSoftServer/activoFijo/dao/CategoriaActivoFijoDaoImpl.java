package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfCategorias;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfCategoriasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class CategoriaActivoFijoDaoImpl extends GenericDaoImpl<AfCategorias, AfCategoriasPK> implements CategoriaActivoFijoDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean eliminarAfCategorias(AfCategorias afCategorias, SisSuceso sisSuceso) throws Exception {
        eliminar(afCategorias);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public AfCategorias getAfCategorias(String empresa, String codigo) throws Exception {
        return obtenerPorId(AfCategorias.class, new AfCategoriasPK(empresa, codigo));
    }

    @Override
    public AfCategorias getAfCategoriasPorDescripcion(String empresa, String descripcion) throws Exception {
        List<AfCategorias> lista = obtenerPorHql("select c from AfCategorias c where c.afCategoriasPK.catEmpresa=?1 and c.catDescripcion=?2", new Object[]{empresa, descripcion});
        if (lista != null && lista.size() > 0) {
            return lista.get(0);
        }

        return null;
    }

    @Override
    public boolean insertarAfCategorias(AfCategorias afCategorias, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(afCategorias);
        return true;
    }

    @Override
    public boolean modificarAfCategorias(AfCategorias afCategorias, SisSuceso sisSuceso) throws Exception {

        sucesoDao.insertar(sisSuceso);
        actualizar(afCategorias);
        return true;
    }

    @Override
    public List<AfCategorias> getListaCategorias(String empresa, boolean filtrarInactivos) throws Exception {
        if (filtrarInactivos) {
            return obtenerPorHql("select c from AfCategorias c where c.afCategoriasPK.catEmpresa=?1 and c.catInactivo=?2", new Object[]{empresa, false});
        } else {
            return obtenerPorHql("select c from AfCategorias c where c.afCategoriasPK.catEmpresa=?1", new Object[]{empresa});
        }
    }

}
