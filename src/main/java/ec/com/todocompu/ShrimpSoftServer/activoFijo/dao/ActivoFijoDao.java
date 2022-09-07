package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfListadoActivoFijoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivosPK;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ActivoFijoDao extends GenericDao<AfActivos, AfActivosPK> {

    public boolean eliminarAfActivos(AfActivos afUbicaciones, SisSuceso sisSuceso) throws Exception;

    public AfActivos getAfActivos(String empresa, String codigo) throws Exception;

    public boolean insertarAfActivos(AfActivos afUbicaciones, SisSuceso sisSuceso) throws Exception;

    public boolean modificarAfActivos(AfActivos afUbicaciones, SisSuceso sisSuceso) throws Exception;

    public List<AfActivos> getListaActivos(String empresa, String sector, String ubicacion) throws Exception;

    public List<AfListadoActivoFijoTO> getFunlistarActivoFijosCompras(String empresa) throws Exception;

}
