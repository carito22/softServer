package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface LiquidacionService {

    public List<PrdLiquidacionDatosAdjuntos> listarImagenesDeLiquidacionPesca(PrdLiquidacionPK pk) throws Exception;

    public boolean insertarImagenesLiquidacionPesca(List<PrdLiquidacionDatosAdjuntos> listado, PrdLiquidacionPK pk) throws Exception;

    public MensajeTO insertarModificarPrdLiquidacion(PrdLiquidacion prdLiquidacion, List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle, List<PrdLiquidacionDatosAdjuntos> listadoImagenes, boolean entregaCasa, SisInfoTO sisInfoTO) throws Exception;

    public String insertarPrdLiquidacionTO(PrdLiquidacionTO prdLiquidacionTO, List<PrdLiquidacionDetalleTO> listaPrdLiquidacionDetalleTO, SisInfoTO sisInfoTO) throws Exception;

    public String modificarPrdLiquidacionTO(PrdLiquidacionTO prdLiquidacionTO, List<PrdLiquidacionDetalleTO> listaPrdLiquidacionDetalleTO, List<PrdLiquidacionDetalleTO> listaPrdLiquidacionEliminarDetalleTO, SisInfoTO sisInfoTO) throws Exception;

    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacion(String empresa, String sector, String piscina, String busqueda) throws Exception;

    public PrdEstadoCCCVT getEstadoCCCVT(String empresa, String motivoTipo, String numero) throws Exception;

    public PrdLiquidacionCabeceraTO getPrdLiquidacionCabeceraTO(String empresa, String motivo, String numeroLiquidacion) throws Exception;

    public PrdLiquidacion getPrdLiquidacion(PrdLiquidacionPK liquidacionPK);

    public PrdLiquidacion obtenerLiquidacion(PrdLiquidacionPK liquidacionPK);

    public List<PrdLiquidacion> getListaPrdLiquidacion(String empresa);

    public String desmayorizarPrdLiquidacion(PrdLiquidacionPK liquidacionPK, boolean entregaCasa, SisInfoTO sisInfoTO);

    public String desmayorizarPrdLiquidacionLote(List<ListaLiquidacionTO> listado, String empresa, SisInfoTO sisInfoTO);

    public String anularRestaurarPrdLiquidacion(PrdLiquidacionPK liquidacionPK, boolean anularRestaurar, boolean entregaCasa, SisInfoTO sisInfoTO);

    public PrdLiquidacionTO getBuscaObjLiquidacionPorLote(String liqLote, String empresa) throws Exception;

    public Map<String, Object> obtenerDatosParaLiquidacionPesca(String empresa, String piscina, String sector) throws Exception;

    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacionTO(String empresa, String sector, String piscina, String busqueda, String nRegistros) throws Exception;

    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacionTOSoloActivas(String empresa, String proveedor, String cliente, String motivo, String numero, String nRegistros) throws Exception;

    public PrdLiquidacion getPrdLiquidacionSoloActivas(String empresa, String proveedor, String cliente, String motivo, String numero) throws Exception;

    public List<PrdLiquidacion> getListPrdLiquidacionParaCompras(String empresa, List<InvComprasLiquidacionTO> listado) throws Exception;

    public List<PrdLiquidacion> getListPrdLiquidacionParaVentas(String empresa, List<InvVentasLiquidacionTO> listado) throws Exception;

    public PrdCorrida obtenerCorrida(PrdLiquidacionPK pk) throws Exception;

    @Transactional
    public List<PrdFunLiquidacionConsolidandoTallasTO> getPrdFunLiquidacionConsolidandoTallasTO(String empresa,
            String desde, String hasta, String sector, String cliente, String piscina) throws Exception;

    //liquidacion consolidando clietes
    @Transactional
    public List<PrdLiquidacionConsolidandoClientesTO> getListadoLiquidacionConsolidandoClientesTO(
            String empresa, String sector, String desde, String hasta, String cliente) throws Exception;

    //liquidacion consolidando clietes
    @Transactional
    public List<PrdLiquidacionConsolidandoProveedoresTO> getListadoLiquidacionConsolidandoProveedoresTO(
            String empresa, String sector, String desde, String hasta, String proveedor, String comisionista) throws Exception;

    //lista liquidacion consultas 
    @Transactional
    public List<PrdLiquidacionConsultaTO> getListadoLiquidacionConsultasTO(
            String empresa, String sector, String piscina, String desde, String hasta, boolean incluirAnuladas) throws Exception;

    //lista liquidacion detalle producto 
    @Transactional
    public List<PrdLiquidacionDetalleProductoTO> getListadoLiquidacionDetalleProductoTO(
            String empresa, String sector, String piscina, String desde, String hasta, String cliente, String talla, String tipo) throws Exception;

    //lista liquidacion detalle producto Empacadora(Proveedores)
    @Transactional
    public List<PrdLiquidacionDetalleProductoEmpacadoraTO> getListadoLiquidacionDetalleProductoEmpacadoraTO(
            String empresa, String sector, String piscina, String desde, String hasta, String proveedor, String talla, String tipo, String comisionista) throws Exception;

    //lista liquidacion consultas 
    @Transactional
    public List<PrdLiquidacionConsultaEmpacadoraTO> getListadoLiquidacionConsultasEmpacadoraTO(
            String empresa, String sector, String piscina, String desde, String hasta, String proveedor, boolean incluirAnuladas, String comisionista) throws Exception;

    public List<PrdLiquidacionesDetalleTO> listarLiquidacionesDetalle(String empresa, String desde, String hasta) throws Exception;

    public String obtenerSiguienteLote(String empresa, String motivo) throws Exception;

    public boolean verificarSiDesmayorizarAnularLiquidacion(String empresa, String ePeriodo, String eNumero) throws Exception;

    @Transactional
    public List<PrdFunLiquidacionConsolidandoTallasProveedorTO> getPrdFunLiquidacionConsolidandoTallasProveedor(String empresa,
            String desde, String hasta, String sector, String proveedor, String piscina, String comisionista) throws Exception;

    public String controlOpcionSegunPeriodoLiquidacion(PrdLiquidacion liquidacion) throws Exception;

    public boolean guardarImagenesLiquidacionPesca(PrdLiquidacionPK pk, List<PrdLiquidacionDatosAdjuntos> imagenes, SisInfoTO sisInfoTO) throws Exception;

    public List<String> listadoComisionista(String empresa) throws Exception;

    public Map<String, Object> verificarExistenciasLiquidacion(String empresa, PrdLiquidacion liquidicacion, List<PrdLiquidacionDetalle> detalles) throws Exception;

}
