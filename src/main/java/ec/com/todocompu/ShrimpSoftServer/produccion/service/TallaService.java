package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.LiquidacionLotesValorizadaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface TallaService {

    public boolean insertarPrdLiquidacionLotesValorizada(LiquidacionLotesValorizadaTO liquidacionLotesValorizadaTO,
            SisInfoTO sisInfoTO) throws Exception;

    public PrdLiquidacionTallaTO getPrdLiquidacionTallaTO(String empresa,
            PrdLiquidacionTallaTablaTO prdLiquidacionTallaTablaTO) throws Exception;

    public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(String empresa) throws Exception;

    public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(String empresa, String codigo)
            throws Exception;

    public String insertarPrdLiquidacionTallaTO(PrdLiquidacionTallaTO prdLiquidacionTallaTO, SisInfoTO sisInfoTO)
            throws Exception;

    public String modificarPrdLiquidacionTallaTO(PrdLiquidacionTallaTO prdLiquidacionTallaTO, SisInfoTO sisInfoTO)
            throws Exception;

    public String eliminarPrdLiquidacionTallaTO(PrdLiquidacionTallaTO prdLiquidacionTallaTO, SisInfoTO sisInfoTO)
            throws Exception;

    public String insertarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisInfoTO sisInfoTO)
            throws Exception;

    public String modificarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisInfoTO sisInfoTO)
            throws Exception;

    public String modificarEstadoPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, boolean estado, SisInfoTO sisInfoTO)
            throws Exception;

    public String eliminarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisInfoTO sisInfoTO)
            throws Exception;

    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, boolean presupuestoPesca)
            throws Exception;

    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, boolean presupuestoPesca, boolean inactivos)
            throws Exception;
    //listaDetalleTalla
    public List<PrdTalla> getListaPrdLiquidacionTallaDetalle(String empresa)
            throws Exception;

    public Map<String, Object> verificarTallaExcel(List<PrdTalla> prdLiquidacionTalla, SisInfoTO sisInfoTO) throws Exception;
    
    public Map<String, Object> insertarTallasImportadas(PrdTalla talla, SisInfoTO sisInfoTO)throws Exception;
}
