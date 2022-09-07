/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ComprasVSOrdenesCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosReporteComprasImbTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosReporteEntregaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraSaldo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraVsCompraDolaresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Developer4
 */
@Transactional
public interface PedidosOrdenCompraService {

    public Map<String, Object> getDatosGenerarInvPedidosOrdenCompra(InvPedidosPK invPedidosPK, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public Map<String, Object> obtenerDatosParaGenerarOrdenCompra(InvPedidosPK invPedidosPK, InvPedidosMotivoPK invPedidosMotivoPK, String provCodigo, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public Map<String, Object> insertarInvPedidosOrdenCompra(InvPedidosOrdenCompra invPedidosOrdenCompra, InvPedidosPK invPedidosPK, List<InvPedidosDetalle> listaInvPedidosDetalle, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public List<InvPedidosOrdenCompraTO> getListaInvPedidosOrdenCompraTO(String empresa, String motivo, String fechaInicio, String fechaFin, Boolean incluirAnulados, String busqueda, Integer nroRegistros, String usuario) throws GeneralException, Exception;

    public List<InvPedidosOrdenCompraTO> listarOrdenCompraParaNotificaciones(String empresa, String motivo, String fechaInicio, String fechaFin, String busqueda, Integer nroRegistros) throws Exception;

    public InvPedidosOrdenCompra getInvPedidosOrdenCompra(InvPedidosOrdenCompraPK pk) throws GeneralException, Exception;

    public Map<String, Object> obtenerOrdenCompraSoloAprobadas(InvPedidosOrdenCompra ipoc, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public List<OrdenCompraProveedor> getInvPedidosOrdenCompraPorProveedorConSaldo(String empresa, String codigoProveedor, String oc_sector, String oc_motivo, String oc_numero) throws GeneralException, Exception;

    public List<OrdenCompraSaldo> getInvPedidosOrdenCompraSaldo(InvPedidosOrdenCompraPK pk) throws GeneralException, Exception;

    public List<ComprasVSOrdenesCompras> getInvPedidosOrdenCompraVsOrdenesCompras(String empresa, String codigoProveedor, String sector, String motivo, String fechaDesde, String fechaHasta) throws GeneralException, Exception;

    public String enviarPdfOrdenCompra(String empresa, String nombreReporte, InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String emailProveedor, Integer idNotificacionEntero, HttpServletResponse response) throws Exception, GeneralException;

    public boolean anularInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, String motivoAnulacion, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public boolean aprobarInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public boolean desaprobarInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public Map<String, Object> obtenerInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, boolean esParaMayorizar, boolean aprobar, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public Map<String, Object> listarInvPedidosOrdenCompra(Integer detSecuencial, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public boolean validarSiTieneOrdenesComprasPendientes(InvPedidosPK pk);

    public InvEntidadTransaccionTO obtenerProveedorDeOrdenDeCompra(String empresa, String sector, String motivo, String numero) throws Exception;

    public boolean cerrarInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> desmayorizarInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, String usuario, boolean validarEjecutores, String motivoDesmayorizar, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerParametrosParaMayorizar(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, SisInfoTO sisInfoTO) throws Exception;

    public InvPedidosOrdenCompra mayorizarInvPedidosOrdenCompra(InvPedidosOrdenCompra invPedidosOrdenCompra, InvPedidosPK invPedidosPK, List<InvPedidosDetalle> listaInvPedidosDetalle, SisInfoTO sisInfoTO) throws Exception;

    public List<InvPedidosOrdenCompraDetalle> getInvPedidosOrdenCompraDetalle(InvPedidosOrdenCompraPK pk) throws GeneralException, Exception;

    public List<InvPedidosOrdenCompraNotificaciones> listarNotificacionesPorOrdenDeCompra(String empresa, String motivo, String sector, String numero) throws Exception;

    public List<InvPedidosReporteEntregaTO> listarReporteEntrega(String empresa, String motivo, String sector, String producto, String cliente, String proveedor, String grupo, String fechaInicio, String fechaFin) throws Exception;

    public List<InvPedidosReporteComprasImbTO> listarReporteComprasImb(String empresa, String periodo, String motivo, String producto, String proveedor, String fechaInicio, String fechaFin) throws Exception;

    public List<OrdenCompraVsCompraDolaresTO> listarOrdenCompraVsCompraDolares(String empresa, String sector, String motivo, String fechaDesde, String fechaHasta) throws Exception;

    public List<InvCompras> getOrdenComprasImportadasEnCompras(InvPedidosOrdenCompraPK pk) throws GeneralException, Exception;

    public Map<String, Object> obtenerDatosParaReporteEntrega(String empresa) throws Exception;

    public Map<String, Object> obtenerDatosParaReporteComprasImb(String empresa) throws Exception;

    public InvPedidosOrdenCompra cambiarDatosProductoOC(
            InvPedidosOrdenCompraPK invPedidosOrdenCompraPK,
            Integer detSecuencial,
            InvProductoPK productoNuevoPk,
            BigDecimal productoNuevoPrecio,
            BigDecimal productoNuevoCantidad,
            SisInfoTO sisInfoTO) throws Exception;
}
