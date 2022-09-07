package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxDiferenciasTributacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDividendo;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraReembolso;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDatosBasicoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasPorPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasProgramadasListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasVsVentasTonelajeTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunUltimasComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListadoPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvSecuenciaComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvAdjuntosCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleImb;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;
import java.math.BigDecimal;

public interface ComprasDao extends GenericDao<InvCompras, InvComprasPK> {

    public boolean modificarConContableCompras(ConContable conContable, List<ConDetalle> listaConDetalle,
            InvCompras invCompras, SisSuceso sisSuceso) throws Exception;

    public boolean modificarConContableComprasMayorizar(ConContable conContable, List<ConDetalle> listaConDetalle,
            List<ConDetalle> listaConDetalleEliminar, InvCompras invCompras, SisSuceso sisSuceso) throws Exception;

    public void actualizarPendientePorSql(InvCompras invCompras) throws Exception;

    public InvCompras buscarInvCompras(String empresa, String perCodigo, String motCodigo, String compNumero)
            throws Exception;

    public InvComprasTO getComprasTO(String empresa, String periodo, String motivo, String numeroCompra)
            throws Exception;

    public void insertarInvCompra(InvCompras invCompras, List<InvComprasDetalle> listaInvComprasDetalle,
            SisSuceso sisSuceso, AnxCompra anxCompra, List<AnxCompraDetalle> anxCompraDetalle,
            List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolso,
            List<AnxCompraFormaPago> anxCompraFormaPago) throws Exception;

    public void insertarInvCompraInventario(InvCompras invCompras, List<InvComprasDetalle> listaInvComprasDetalle,
            SisSuceso sisSuceso, AnxCompra anxCompra, List<AnxCompraDetalle> anxCompraDetalle,
            List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolso,
            List<AnxCompraFormaPago> anxCompraFormaPago, List<InvComprasDetalleImb> listaCompraImb,
            List<InvComprasLiquidacion> listInvComprasLiquidacion) throws Exception;

    public boolean insertarTransaccionInvCompra(InvCompras invCompras, List<InvComprasDetalle> listaInvComprasDetalles,
            SisSuceso sisSuceso, AnxCompra anxCompra, List<AnxCompraDetalle> anxCompraDetalle,
            List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolso,
            List<AnxCompraFormaPago> anxCompraFormaPago, List<InvAdjuntosCompras> invAdjuntosCompras) throws Exception;

    public boolean insertarTransaccionInvCompraInventario(InvCompras invCompras, List<InvComprasDetalle> listaInvComprasDetalles,
            SisSuceso sisSuceso, AnxCompra anxCompra, List<AnxCompraDetalle> anxCompraDetalle,
            List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolso,
            List<AnxCompraFormaPago> anxCompraFormaPago, List<InvAdjuntosCompras> invAdjuntosCompras, List<InvComprasDetalleImb> listaCompraImb,
            List<InvComprasLiquidacion> listInvComprasLiquidacion) throws Exception;

    public boolean modificarInvCompras(InvCompras invCompras, List<InvComprasDetalle> listaInvDetalle,
            List<InvComprasDetalle> listaInvDetalleEliminar, AnxCompra anxCompra,
            List<AnxCompraDetalle> anxCompraDetalle, List<AnxCompraDetalle> anxCompraDetalleEliminar,
            List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolsos,
            List<AnxCompraReembolso> anxCompraReembolsoEliminar, List<AnxCompraFormaPago> anxCompraFormaPago,
            List<AnxCompraFormaPago> anxCompraFormaPagoEliminar, SisSuceso sisSuceso, List<SisSuceso> listaSisSuceso,
            ConContable conContable, InvComprasMotivoAnulacion invComprasMotivoAnulacion,
            boolean eliminarMotivoAnulacion, boolean desmayorizar, List<InvAdjuntosCompras> listImagen);

    //web
    public boolean modificarInvComprasInventario(InvCompras invCompras, List<InvComprasDetalle> listaInvDetalle,
            List<InvComprasDetalle> listaInvDetalleEliminar, AnxCompra anxCompra,
            List<AnxCompraDetalle> anxCompraDetalle, List<AnxCompraDetalle> anxCompraDetalleEliminar,
            List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolsos,
            List<AnxCompraReembolso> anxCompraReembolsoEliminar, List<AnxCompraFormaPago> anxCompraFormaPago,
            List<AnxCompraFormaPago> anxCompraFormaPagoEliminar, SisSuceso sisSuceso, List<SisSuceso> listaSisSuceso,
            ConContable conContable, InvComprasMotivoAnulacion invComprasMotivoAnulacion,
            boolean eliminarMotivoAnulacion, boolean desmayorizar, List<InvAdjuntosCompras> listImagen, List<InvComprasDetalleImb> listaCompraImb, List<InvComprasDetalleImb> listaCompraImbEliminar,
            List<InvComprasLiquidacion> listInvComprasLiquidacion, List<InvComprasLiquidacion> listInvComprasLiquidacionEliminar) throws Exception;

    public int buscarConteoUltimaNumeracionCompra(String empCodigo, String perCodigo, String motCodigo)
            throws Exception;

    public Object[] getCompra(String empresa, String periodo, String conTipo, String numero);

    public String getConteoNumeroFacturaCompra(String empresaCodigo, String provCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception;

    public InvCompraCabeceraTO getInvCompraCabeceraTO(String empresa, String periodo, String motivo,
            String numeroCompra) throws Exception;

    public List<InvSecuenciaComprobanteTO> getInvSecuenciaComprobanteTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    public BigDecimal getPrecioProductoUltimaCompra(String empresa, String produCodigo) throws Exception;

    @Transactional
    public List<InvComprasPorPeriodoTO> getComprasPorPeriodo(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, boolean chk) throws Exception;

    public List<InvListaConsultaCompraTO> getFunComprasListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception;

    @Transactional
    public List<AnxDiferenciasTributacionTO> listarDiferenciasTributacion(String empresa, String fechaInicio, String fechaFin) throws Exception;

    public List<InvFunComprasConsolidandoProductosTO> getInvFunComprasConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega, String proveedor) throws Exception;

    public List<InvListaConsultaCompraTO> getListaInvConsultaCompra(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, Boolean listarImb) throws Exception;

    public List<InvListaConsultaCompraTO> listarComprasPorOrdenCompra(String empresa, String sector, String motivo, String numero) throws Exception;

    public List<InvListaConsultaCompraTO> listarComprasPorOrdenCompraYProducto(String empresa, String sector, String motivo, String numero, String codigoProducto, int ocSecuencial) throws Exception;

    public List<InvFunComprasTO> getInvFunComprasTO(String empresa, String desde, String hasta, String motivo,
            String proveedor, String documento, String formaPago) throws Exception;

    public List<InvFunComprasTO> getInvFunComprasTOAgrupadoTipoDocumento(String empresa, String desde, String hasta, String motivo,
            String proveedor, String documento, String formaPago) throws Exception;

    public List<InvFunComprasTO> getInvFunComprasTOAgrupadoTipoContribuyente(String empresa, String desde, String hasta, String motivo,
            String proveedor, String documento, String formaPago) throws Exception;

    public List<InvFunUltimasComprasTO> getInvFunUltimasComprasTOs(String empresa, String producto) throws Exception;

    public List<InvListadoPagosTO> invListadoPagosTO(String empresa, String periodo, String motivo, String numero)
            throws Exception;

    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception;

    public void mayorizarDesmayorizarComprasSql(InvComprasPK invComprasPK, boolean pendiente, boolean revisado, SisSuceso sisSuceso) throws Exception;

    public void anularRestaurarComprasSql(InvComprasPK invComprasPK, boolean anulado, boolean actualizarFechaUltimaValidacionSri, SisSuceso sisSuceso) throws Exception;

    public void guardarImagenesCompra(InvComprasPK invComprasPK, List<InvAdjuntosCompras> listInvAdjuntosCompras) throws Exception;

    public InvComprasDatosBasicoTO getDatosBasicosCompra(ConContablePK conContablePK) throws Exception;

    public List<InvAdjuntosCompras> getAdjuntosCompra(InvComprasPK invComprasPK) throws Exception;

    public List<InvAdjuntosCompras> listarImagenesNoMigradas(String empresa) throws Exception;

    public List<Integer> listaImagenesNoMigradas(String empresa) throws Exception;

    public List<Integer> listaImagenesMigradas(String empresa) throws Exception;

    public List<InvFunComprasProgramadasListadoTO> listarComprasProgramadas(String empresa, String periodo, String motivo, String desde, String hasta, String nRegistros) throws Exception;

    public String eliminarComprasProgramadas(String empresa, String periodo, String motivo, String numero, SisSuceso sisSuceso) throws Exception;

    public String eliminarCompras(String empresa, String periodo, String motivo, String numero, SisSuceso sisSuceso) throws Exception;

    public String duplicarComprasProgramada(String empresa, String periodo, String motivo, String numero, Date date, SisInfoTO sisInfoTO) throws Exception;

    public List<InvFunComprasVsVentasTonelajeTO> listarComprasVsVentasTonelaje(String empresa, String desde, String hasta, String sector, String bodega, String proveedor) throws Exception;

    public List<InvConsultaCompraTO> listarComprasPorProveedor(String empresa, String periodo, String motivo, String numero, String provCodigo) throws Exception;

    public List<InvConsultaCompraTO> listarComprasPorProveedorSoloNotaEntrega(String empresa, String periodo, String motivo, String numero, String provCodigo) throws Exception;

    public List<InvConsultaCompraTO> listarComprasImb(String empresa, String periodo, String motivo, String numero) throws Exception;

    public boolean ordenCompraEstaImportada(InvPedidosOrdenCompraPK pk);

    public boolean guardarClaveAcceso(String codigoEmpresa, String motivo, String numero, String periodo, String claveAcceso, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean actualizarImagenesCompra(List<InvAdjuntosCompras> listado, InvComprasPK pk) throws Exception;

    @Transactional
    public boolean crearAjusteInventario(InvCompras invCompras,
            InvCompras invComprasNotaCreditoInt,
            List<InvComprasDetalle> listaInvComprasDetalles,
            List<InvComprasDetalle> listaInvComprasDetallesNotaCreditoInt,
            SisSuceso sisSuceso) throws Exception;

    public InvComprasTO getComprasTO(String empresa, String tipo, String numeroDocumento) throws Exception;

    public Boolean actualizarClaveExterna(InvComprasPK pk, String clave, SisSuceso sisSuceso) throws Exception;

    public List<InvListaConsultaCompraTO> obtenerListadoIMBPendientes(String empresa, String periodo, String motivo, String proveedor, String producto, String fechaInicio, String fechaFin) throws Exception;

    public List<InvCompras> obtenerListadoComprasSaldosImportados(String empresa, String motivo, String sector, String fecha) throws Exception;

    public boolean insertarInvComprasTO(InvCompras invCompras, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvCompras(InvCompras invCompras, SisSuceso sisSuceso) throws Exception;

    public InvCompras obtenerCompras(String empresa, String tipo, String numeroDocumento, String provCodigo) throws Exception;

    public InvCompraCabeceraTO consultarCompraPorContablePk(String empresa, String periodo, String conTipo, String numero);

    public boolean eliminarLiquidacionCompras(InvComprasPK invComprasPK) throws Exception;
}
