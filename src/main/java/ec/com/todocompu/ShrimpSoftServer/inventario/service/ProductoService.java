package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfActivoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ImportarProductos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntosProductosWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListaProductosImpresionPlacasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioCantidadTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCompraSustentoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoDAOTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoFormulaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoRelacionadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSincronizarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductosConErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.DatoFunListaProductosImpresionPlaca;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface ProductoService {

    public Map<String, Object> insertarInvProducto(InvProducto invProducto, AfActivos afActivo, SisInfoTO sisInfoTO) throws Exception;

    public InvProductoDAOTO buscarInvProductoDAOTO(String empresa, String codigoProducto) throws Exception;

    public String evaluaAntesDeinsertarInvProductoTO(InvProductoTO invProductoTO, SisInfoTO sisInfoTO) throws Exception;

    public String insertarInvProductoTO(InvProductoTO invProductoTO, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> insertarInvProductoTO(InvProductoTO invProductoTO, AfActivoTO afActivoTO, List<InvAdjuntosProductosWebTO> listadoImagenes, List<InvProductoRelacionadoTO> listaInvProductoRelacionados, List<InvProductoFormulaTO> listaInvProductoFormulaTO, SisInfoTO sisInfoTO) throws Exception;

    public boolean productoRepetidoCodigoBarra(String empresa, String barras) throws Exception;

    public String modificarInvProductoTO(InvProductoTO invProductoTO, String codigoCambiarLlave, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> modificarInvProductoTO(InvProductoTO invProductoTO, String codigoCambiarLlave, AfActivoTO afActivoTO, List<InvAdjuntosProductosWebTO> listadoImagenes, List<InvAdjuntosProductosWebTO> listadoImagenesEliminar, List<InvProductoRelacionadoTO> listaInvProductoRelacionados, List<InvProductoRelacionadoTO> listaInvProductoRelacionadosEliminar, List<InvProductoFormulaTO> listaInvProductoFormulaTO, List<InvProductoFormulaTO> listaInvProductoFormulaTOEliminar, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarListaInvProducto(List<InvProducto> listaInvProducto, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public String eliminarInvProductoTO(InvProductoTO invProductoTO, SisInfoTO sisInfoTO) throws Exception;

    public List<InvProductoTO> getProductoTO(String empresa, String codigo) throws Exception;

    public InvProductoTO obtenerProductoTO(String empresa, String codigo) throws Exception;

    public String obtenerCodigoPorNombre(String empresa, String nombre) throws Exception;

    public InvProducto obtenerPorId(String empresa, String codigo) throws Exception;

    public List<InvFunListadoProductosTO> getInvFunListadoProductosTO(String empresa, String categoria, String busqueda) throws Exception;

    public InvFunListadoProductosTO obtenerInvFunListadoProductosTO(String empresa, String codigo) throws Exception;

    public Boolean getPuedeEliminarProducto(String empresa, String producto) throws Exception;

    public BigDecimal getPrecioProductoPorCantidad(String empresa, String cliente, String codProducto, BigDecimal cantidad) throws Exception;

    public BigDecimal getCantidad3(String empresa, String codProducto) throws Exception;

    public List<InvProductosConErrorTO> getListadoProductosConError(String empresa) throws Exception;

    public List<InvFunListaProductosImpresionPlacasTO> getInvFunListaProductosImpresionPlacasTO(String empresa, String producto, boolean estado) throws Exception;

    public List<DatoFunListaProductosImpresionPlaca> convertirInvFunListaProductosImpresionPlacasTO_DatoFunListaProductosImpresionPlaca(List<InvFunListaProductosImpresionPlacasTO> listado) throws Exception;

    public MensajeTO invCambiarPrecioProducto(String empresa, String usuario, List<InvListaProductosCambiarPrecioTO> invListaProductosCambiarPrecioTOs, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO invCambiarPrecioCantidadProducto(String empresa, String usuario, List<InvListaProductosCambiarPrecioCantidadTO> invListaProductosCambiarPrecioCantidadTOs, SisInfoTO sisInfoTO) throws Exception;

    public List<InvProductoSincronizarTO> invProductoSincronizar(String empresaOrigen, String empresaDestino, String usuario, SisInfoTO sisInfoTO) throws Exception;

    public List<InvListaProductosCompraSustentoConceptoTO> getInvProductoSustentoConcepto(String empresa, String fechaCompra, List<InvListaProductosCompraSustentoConceptoTO> invListaProductosCompraTOs) throws Exception;

    public boolean eliminarInvProducto(InvProductoPK pk, SisInfoTO sisInfoTO) throws Exception, GeneralException;

    public boolean modificarEstadoInvProducto(InvProductoPK pk, boolean estado, SisInfoTO sisInfoTO) throws Exception, GeneralException;

    public boolean activarEcommerceInvProducto(InvProductoPK pk, SisInfoTO sisInfoTO) throws Exception, GeneralException;

    public List<InvProductoSimpleTO> listarProductos(String empresa, String busqueda, String categoria, boolean incluirInactivos, boolean limite) throws Exception;

    public InvProductoEtiquetas traerEtiquetas(String empresa) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudProductos(Map<String, Object> map) throws Exception;

    public List<InvComprasDetalleProductoTO> getListadoComprasDetalleProductoAgrupadoProveedor(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception;

    public List<InvComprasDetalleProductoTO> getListadoComprasDetalleProductoAgrupadoCentroCosto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception;

    public List<InvComprasDetalleProductoTO> getListadoComprasDetalleProductoAgrupadoEquipoControl(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception;

    /*Imagenes*/
    public List<InvAdjuntosProductosWebTO> convertirStringUTF8(InvProductoPK invProductoPK);

    public List<InvVentasDetalleProductoTO> getListadoVentasDetalleProducto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception;

    public List<InvComprasDetalleProductoTO> getListadoComprasDetalleProducto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception;

    public Map<String, Object> obtenerDatosParaProductosPrecio(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosParaModificacionPrecios(String empresa) throws Exception;

    public Map<String, Object> obtenerInventarioReporte(String empresa) throws Exception;

    public List<InvProductoSinMovimientoTO> obtenerProductosSinMovimientos(String empresa) throws Exception;

    public AnxHomologacionProducto buscarInvProductoHomologado(String empresa, String codigoProducto, String codigoProveedor) throws Exception;

    public Map<String, Object> verificarExistenciaEnProductos(String empresa, List<ImportarProductos> productosExcel) throws Exception;

    public List<InvVentasDetalleProductoTO> getListadoVentasDetalleProductoAgrupadoCliente(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception;

    public List<InvVentasDetalleProductoTO> getListadoVentasDetalleProductoAgrupadoCC(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception;

}
