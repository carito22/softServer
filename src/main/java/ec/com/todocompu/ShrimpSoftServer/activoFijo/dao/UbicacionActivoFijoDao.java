package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfUbicaciones;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfUbicacionesPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface UbicacionActivoFijoDao extends GenericDao<AfUbicaciones, AfUbicacionesPK> {

    public boolean eliminarAfUbicaciones(AfUbicaciones afUbicaciones, SisSuceso sisSuceso) throws Exception;

    public AfUbicaciones getAfUbicaciones(String empresa, String sector, String codigo) throws Exception;

    public AfUbicaciones getAfUbicacionesPorDescripcion(String empresa, String descripcion) throws Exception;

    public boolean insertarAfUbicaciones(AfUbicaciones afUbicaciones, SisSuceso sisSuceso) throws Exception;

    public boolean modificarAfUbicaciones(AfUbicaciones afUbicaciones, SisSuceso sisSuceso) throws Exception;

    public List<AfUbicaciones> getListaUbicaciones(String empresa) throws Exception;
}
