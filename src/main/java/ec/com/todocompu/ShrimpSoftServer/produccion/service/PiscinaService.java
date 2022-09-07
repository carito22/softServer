package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PiscinaGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoRelacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface PiscinaService {

    public boolean insertarPrdPiscina(PrdPiscinaTO prdPiscinaTO, List<PrdPiscinaGrupoRelacionTO> listado, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarPrdPiscina(PrdPiscinaTO prdPiscinaTO, List<PrdPiscinaGrupoRelacionTO> listado, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarPrdPiscina(PrdPiscinaTO prdPiscinaTO, SisInfoTO sisInfoTO) throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscinaTO(String empresa) throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscinaTO(String empresa, String sector) throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscinaTO(String empresa, String sector, boolean mostrarInactivo) throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscinaGeneralTO(String empresa) throws Exception;

    public List<PrdPiscina> getListaPiscinaPor_Empresa_Sector_Activa(String empresa, String sector, boolean activo)
            throws Exception;

    public List<PiscinaGrameajeTO> getListaPiscinasGrameaje(String empresa, String sector, String fecha)
            throws Exception;

    public List<PrdListaPiscinaComboTO> getListaPiscinaTO(String empresa, boolean activo) throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscinaTOBusqueda(String empresa, String sector, String fecha)
            throws Exception;

    public List<PrdListaPiscinaTO> getListaPiscinaTOBusqueda(String empresa, String sector, String fecha, String grupo, String tipo, boolean mostrarInactivos)
            throws Exception;

    public List<PrdComboPiscinaTO> getComboPiscinaTO(String empresa, String sector) throws Exception;

    public PrdPiscina obtenerPorEmpresaSectorPiscina(String empresa, String sector, String piscina) throws Exception;

    public List<PrdPiscina> getListaSectorPorEmpresa(String empresa, String sector) throws Exception;

    public String insertarPrdPiscina(PrdPiscina prdPiscina, SisInfoTO sisInfoTO) throws Exception;

    public String modificarPrdPiscina(PrdPiscina prdPiscina, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarEstadoPrdPiscina(PrdPiscinaTO prdPiscinaTO, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarPrdPiscina(PrdPiscina prdPiscina, SisInfoTO sisInfoTO) throws Exception;

    public String cambiarCodigoPiscina(String empresa, String sector, String piscinaIncorrecta, String piscinaCorrecta) throws Exception;

    public Map<String, Object> obtenerDetalleCuenta(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudPiscina(Map<String, Object> map) throws Exception;

    public Map<String, Object> verificarExistenciaPiscina(String empresa, List<PrdPiscinaTO> importarPiscinas) throws Exception;

    public Map<String, Object> insertarPiscinaImportacion(PrdPiscinaTO piscina, SisInfoTO sisInfoTO) throws Exception;

}
