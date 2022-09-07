package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorConHectareajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface SectorService {

    public boolean insertarPrdSector(PrdSectorTO prdSectorTO, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarPrdSector(PrdSectorTO prdSectorTO, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarEstadoPrdSector(PrdSectorTO prdSectorTO, boolean inactivos, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarPrdSector(PrdSectorTO prdSectorTO, SisInfoTO sisInfoTO) throws Exception;

    public String obtenerFechaGrameajeSuperior(String empresa, String sector, String numPiscina) throws Exception;

    public List<PrdListaSectorTO> getListaSectorTO(String empresa, boolean activo) throws Exception;

    public List<PrdListaSectorTO> obtenerTodosLosSectores(String empresa) throws Exception;

    public List<PrdListaSectorConHectareajeTO> getListaSectorConHectareaje(String empresa, String fecha)
            throws Exception;

    public String insertarPrdSector(PrdSector prdSector, SisInfoTO sisInfoTO) throws Exception;

    public String modificarPrdSector(PrdSector prdSector, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarPrdSector(PrdSector prdSector, SisInfoTO sisInfoTO) throws Exception;

    public PrdSector obtenerPorEmpresaSector(String empresa, String sector);

    public List<PrdSector> getListaSectorPorEmpresa(String empresa, boolean activo) throws Exception;

}
