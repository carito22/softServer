package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorConHectareajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface SectorDao extends GenericDao<PrdSector, PrdSectorPK> {

    public boolean insertarPrdSector(PrdSector prdSector, SisSuceso sisSuceso) throws Exception;

    public boolean modificarPrdSector(PrdSector prdSectorModifica, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarPrdSector(PrdSector prdSectorEliminar, SisSuceso sisSuceso) throws Exception;

    public List<PrdListaSectorTO> getListaSector(String empresa, boolean activo) throws Exception;

    public List<PrdListaSectorTO> obtenerTodosLosSectores(String empresa) throws Exception;

    public List<PrdSector> getListaSectorPorEmpresa(String empresa, boolean activo) throws Exception;

    public List<PrdListaSectorConHectareajeTO> getListaSectorConHectareaje(String empresa, String fecha) throws Exception;

    public boolean eliminarPrdSector(String empresa, String codigo) throws Exception;
}
