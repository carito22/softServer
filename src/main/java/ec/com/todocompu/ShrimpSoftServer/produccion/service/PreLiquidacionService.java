package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunPreLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface PreLiquidacionService {

    public MensajeTO insertarModificarPrdPreLiquidacion(PrdPreLiquidacion prdPreLiquidacion, List<PrdPreLiquidacionDetalle> listaPrdPreLiquidacionDetalle, SisInfoTO sisInfoTO) throws Exception;

    public PrdPreLiquidacion getPrdPreLiquidacion(PrdPreLiquidacionPK liquidacionPK);

    public PrdPreLiquidacion obtenerPrdPreLiquidacion(PrdPreLiquidacionPK liquidacionPK);

    public List<PrdPreLiquidacion> getListaPrdPreLiquidacion(String empresa);

    public String desmayorizarPrdPreLiquidacion(PrdPreLiquidacionPK liquidacionPK);

    public String desmayorizarPrdPreLiquidacionLote(List<ListaPreLiquidacionTO> listado, String empresa);

    public String anularRestaurarPrdPreLiquidacion(PrdPreLiquidacionPK liquidacionPK, boolean anularRestaurar);

    public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacion(String empresa, String sector, String piscina, String busqueda) throws Exception;

    public PrdPreLiquidacionTO getBuscaObjPreLiquidacionPorLote(String plLote) throws Exception;

    public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacionTO(String empresa, String sector, String piscina, String busqueda, String nRegistros) throws Exception;

    public Map<String, Object> obtenerDatosParaPreLiquidacionPesca(String empresa, String piscina, String sector) throws Exception;

    public PrdCorrida obtenerCorrida(PrdPreLiquidacionPK pk, SisInfoTO sisInfoTO) throws Exception;

    //lista preliquidacion detalle producto
    public List<PrdPreLiquidacionDetalleProductoTO> getListadoPreLiquidacionDetalleProductoTO(
            String empresa, String sector, String piscina, String desde, String hasta, String cliente, String talla, String tipo) throws Exception;

    //Lista preliquidacion consolidando tallas
    @Transactional
    public List<PrdFunPreLiquidacionConsolidandoTallasTO> getPrdFunPreLiquidacionConsolidandoTallasTO(String empresa,
            String desde, String hasta, String sector, String cliente, String piscina) throws Exception;

    //Lista PreLiquidacion consolidando cliente
    @Transactional
    public List<PrdPreLiquidacionConsolidandoClientesTO> getListadoPreLiquidacionConsolidandoClientesTO(
            String empresa, String sector, String desde, String hasta, String cliente) throws Exception;

    //lista liquidacion consultas 
    @Transactional
    public List<PrdPreLiquidacionConsultaTO> getListadoPreLiquidacionConsultasTO(
            String empresa, String sector, String piscina, String desde, String hasta, boolean incluirAnuladas) throws Exception;

    public List<PrdPreLiquidacionesDetalleTO> listarPreLiquidacionesDetalle(String empresa, String desde, String hasta) throws Exception;

}
