package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PiscinaGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PiscinaDao extends GenericDao<PrdPiscina, PrdPiscinaPK> {

    @Transactional
    public List<PrdCorrida> buscarTodasCorrida(String empresa) throws Exception;

    public boolean insertarPrdPiscina(PrdPiscina prdPiscina, SisSuceso sisSuceso) throws Exception;

    public boolean modificarPrdPiscina(PrdPiscina prdPiscina, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarPrdPiscina(PrdPiscina prdPiscina, SisSuceso sisSuceso) throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscina(String empresa, String sector) throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscina(String empresa) throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscina(String empresa, String sector, boolean activo) throws Exception;

    public List<PrdPiscina> getListaPiscinaPor_Empresa_Sector_Activa(String empresa, String sector, boolean activo)
            throws Exception;

    public List<PiscinaGrameajeTO> getListaPiscinasGrameaje(String empresa, String sector, String fecha)
            throws Exception;

    public List<PrdListaPiscinaComboTO> getListaPiscina(String empresa, boolean activo) throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscinaBusqueda(String empresa, String sector, String fecha)
            throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscinaBusqueda(String empresa, String sector, String fecha, String grupo, String tipo, boolean mostrarInactivos)
            throws Exception;

    public List<PrdComboPiscinaTO> getComboPiscina(String empresa, String sector) throws Exception;

    public boolean eliminarPrdPiscina(String empresa, String sector, String piscina) throws Exception;

    public PrdPiscina obtenerPorEmpresaSectorPiscina(String empresa, String sector, String piscina);

    public List<PrdPiscina> obtenerPorEmpresaSector(String empresa, String sector);

    public boolean cambiarCodigoPiscina(String empresa, String sector, String piscinaIncorrecta, String piscinaCorrecta) throws Exception;
}
