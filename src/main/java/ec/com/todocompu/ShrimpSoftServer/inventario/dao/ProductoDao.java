package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListaProductosImpresionPlacasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSincronizarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductosConErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoFormula;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoRelacionados;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProductoDao extends GenericDao<InvProducto, InvProductoPK> {

    @Transactional
    public InvProducto buscarInvProducto(String empresa, String codigoProducto) throws Exception;

    public boolean eliminarInvProducto(InvProducto invProducto, SisSuceso sisSuceso) throws Exception;

    public boolean insertarInvProducto(InvProducto invProducto, SisSuceso sisSuceso, InvNumeracionVarios invNumeracionVarios) throws Exception;

    public boolean insertarInvProductoImportacion(InvProducto invProducto, AfActivos afActivo, SisSuceso sisSuceso, SisSuceso sisSucesoAF, InvNumeracionVarios invNumeracionVarios) throws Exception;

    public boolean insertarInvProducto(InvProducto invProducto, AfActivos afActivo, List<InvProductoDatosAdjuntos> listadoImagenes, List<InvProductoRelacionados> listaInvProductoRelacionados, List<InvProductoFormula> listaInvProductoFormula, SisSuceso sisSuceso, InvNumeracionVarios invNumeracionVarios) throws Exception;

    public boolean modificarInvProductoImportacion(InvProducto invProducto, AfActivos afActivo, AfActivos afActivoEliminar, SisSuceso sisSuceso, SisSuceso sisSucesoAF) throws Exception;

    public Boolean invCambiarPrecioProductos(List<InvProducto> invProductos, List<SisSuceso> sisSucesos) throws Exception;

    public Boolean invSincronizarProductosCategorias(List<InvProducto> invProductos, List<InvProductoCategoria> invProductoCategorias, List<SisSuceso> sisSucesos) throws Exception;

    public boolean modificarInvProducto(InvProducto invProducto, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvProducto(InvProducto invProducto, AfActivos afActivo, AfActivos afActivoEliminar, List<InvProductoDatosAdjuntos> listadoImagenes, List<InvProductoDatosAdjuntos> listadoImagenesEliminar, List<InvProductoRelacionados> listaInvProductoRelacionados, List<InvProductoRelacionados> listaInvProductoRelacionadosEliminar, List<InvProductoFormula> listaInvProductoFormula, List<InvProductoFormula> listaInvProductoFormulaEliminar, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvProductoLlavePrincipal(InvProducto invProductoEliminar, InvProducto invProducto, SisSuceso sisSuceso) throws Exception;

    public String getInvProximaNumeracionProducto(String empresa, InvProductoTO invProductoTO) throws Exception;

    public Boolean buscarExisteNombreProducto(String empresa, String nombreProducto) throws Exception;

    public BigDecimal getCantidad3(String empresa, String codProducto) throws Exception;

    public List<InvProductoTO> getProductoTO(String empresa, String codigo) throws Exception;

    public boolean getProductoRepetido(String empresa, String codigo, String alterno, String barras, String barras2, String barras3, String barras4, String barras5, String nombre) throws Exception;

    public Boolean getPuedeEliminarProducto(String empresa, String producto) throws Exception;

    public List<InvProductoSincronizarTO> invProductoSincronizar(String empresaOrigen, String empresaDestino) throws Exception;

    public List<InvFunListaProductosImpresionPlacasTO> getInvFunListaProductosImpresionPlacasTO(String empresa, String producto, boolean estado) throws Exception;

    public List<InvProductosConErrorTO> getListadoProductosConError(String empresa) throws Exception;

    public BigDecimal getPrecioProductoPorCantidad(String empresa, String cliente, String codProducto, BigDecimal cantidad) throws Exception;

    public List<InvFunListadoProductosTO> getInvFunListadoProductosTO(String empresa, String categoria, String busqueda) throws Exception;

    public InvProductoTO obtenerProductoTO(String empresa, String codigo) throws Exception;

    public String obtenerCodigoPorNombre(String empresa, String nombre) throws Exception;

    public InvFunListadoProductosTO obtenerInvFunListadoProductosTO(String empresa, String codigo) throws Exception;

    /*Imagenes*/
    public List<InvProductoDatosAdjuntos> getAdjuntosProducto(InvProductoPK invProductoPK) throws Exception;

    public boolean insertarImagen(InvProductoDatosAdjuntos invProductoDatosAdjuntos) throws Exception;

    public boolean actualizarImagen(InvProductoDatosAdjuntos invProductoDatosAdjuntos) throws Exception;

    public boolean eliminarImagen(InvProductoDatosAdjuntos invProductoDatosAdjuntos) throws Exception;

    public boolean insertarInvProductoRelacionados(InvProductoRelacionados productoRelacionado) throws Exception;

    public boolean actualizarInvProductoRelacionados(InvProductoRelacionados productoRelacionado) throws Exception;

    public boolean eliminarInvProductoRelacionados(InvProductoRelacionados productoRelacionado) throws Exception;

    public boolean insertarInvProductoFormula(InvProductoFormula productoFormula) throws Exception;

    public boolean actualizarInvProductoFormula(InvProductoFormula productoFormula) throws Exception;

    public boolean eliminarInvProductoFormula(InvProductoFormula productoFormula) throws Exception;

    public List<InvVentasDetalleProductoTO> obtenerListadoVentasDetalleProducto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception;

    public List<InvComprasDetalleProductoTO> obtenerListadoComprasDetalleProducto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception;

    public List<InvProductoSinMovimientoTO> obtenerProductosSinMovimientos(String empresa) throws Exception;

    public List<Integer> listaImagenesNoMigradas(String empresa) throws Exception;

    public List<Integer> listaImagenesMigradas(String empresa) throws Exception;

    public List<InvComprasDetalleProductoTO> obtenerListadoComprasDetalleProductoAgrupadoProveedor(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception;

    public List<InvComprasDetalleProductoTO> obtenerListadoComprasDetalleProductoAgrupadoCentroCosto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception;

    public List<InvVentasDetalleProductoTO> obtenerListadoVentasDetalleProductoAgrupadoCliente(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception;

    public List<InvVentasDetalleProductoTO> obtenerListadoVentasDetalleProductoAgrupadoCC(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception;

    public List<InvComprasDetalleProductoTO> obtenerListadoComprasDetalleProductoAgrupadoEquipoControl(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception;
}
