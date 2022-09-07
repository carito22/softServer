package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfDepreciacionesMotivos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface MotivoDepreciacionActivoFijoDao extends GenericDao<AfDepreciacionesMotivos, Integer> {

    public boolean eliminarAfDepreciacionesMotivos(AfDepreciacionesMotivos afUbicaciones, SisSuceso sisSuceso) throws Exception;

    public AfDepreciacionesMotivos getAfDepreciacionesMotivos(Integer codigo) throws Exception;

    public AfDepreciacionesMotivos getAfDepreciacionesMotivosUbicacion(String empresa, String codigo) throws Exception;

    public AfDepreciacionesMotivos getAfDepreciacionesMotivosCategoria(String empresa, String codigo) throws Exception;

    public boolean insertarAfDepreciacionesMotivos(AfDepreciacionesMotivos afUbicaciones, SisSuceso sisSuceso) throws Exception;

    public boolean modificarAfDepreciacionesMotivos(AfDepreciacionesMotivos afUbicaciones, SisSuceso sisSuceso) throws Exception;

    public List<AfDepreciacionesMotivos> getListaAfDepreciacionesMotivos(String empresa) throws Exception;
}
