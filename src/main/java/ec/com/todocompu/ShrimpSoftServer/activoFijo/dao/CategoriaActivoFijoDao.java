package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfCategorias;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfCategoriasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface CategoriaActivoFijoDao extends GenericDao<AfCategorias, AfCategoriasPK> {

    public boolean eliminarAfCategorias(AfCategorias afCategorias, SisSuceso sisSuceso) throws Exception;

    public AfCategorias getAfCategorias(String empresa, String codigo) throws Exception;

    public AfCategorias getAfCategoriasPorDescripcion(String empresa, String descripcion) throws Exception;

    public boolean insertarAfCategorias(AfCategorias afCategorias, SisSuceso sisSuceso) throws Exception;

    public boolean modificarAfCategorias(AfCategorias afCategorias, SisSuceso sisSuceso) throws Exception;

    public List<AfCategorias> getListaCategorias(String empresa, boolean filtrarInactivos) throws Exception;
}
