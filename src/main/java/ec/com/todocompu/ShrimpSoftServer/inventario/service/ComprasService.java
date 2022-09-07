package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleRetencionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntosComprasWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraTotalesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDatosBasicoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleImbTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasRecepcionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasProgramadasListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasVsVentasTonelajeTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunUltimasComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListadoPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvSecuenciaComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvAdjuntosCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import java.util.Date;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import java.math.BigDecimal;

public interface ComprasService {

    @Transactional
    public String getConteoNumeroFacturaCompra(String empresaCodigo, String provCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception;

    @Transactional
    public InvCompraCabeceraTO getInvCompraCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroCompra) throws Exception;

    @Transactional
    public BigDecimal getPrecioProductoUltimaCompra(String empresa, String produCodigo) throws Exception;

    @Transactional
    public List<InvListadoPagosTO> invListadoPagosTO(String empresa, String periodo, String motivo, String numero)
            throws Exception;

    @Transactional
    public List<InvFunComprasTO> getInvFunComprasTO(String empresa, String desde, String hasta, String motivo,
            String proveedor, String documento, String formaPago) throws Exception;

    public List<InvFunComprasTO> getInvFunComprasTOAgrupadoTipoDocumento(String empresa, String desde, String hasta, String motivo,
            String proveedor, String documento, String formaPago) throws Exception;

    public List<InvFunComprasTO> getInvFunComprasTOAgrupadoTipoContribuyente(String empresa, String desde, String hasta, String motivo,
            String proveedor, String documento, String formaPago) throws Exception;

    @Transactional
    public List<InvFunComprasConsolidandoProductosTO> getInvFunComprasConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String motivo, String proveedor) throws Exception;

    @Transactional
    public List<InvSecuenciaComprobanteTO> getInvSecuenciaComprobanteTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    @Transactional
    public List<InvFunUltimasComprasTO> getInvFunUltimasComprasTOs(String empresa, String producto) throws Exception;

    @Transactional
    public InvComprasTO getComprasTO(String empresa, String periodo, String motivo, String numeroCompra)
            throws Exception;

    @Transactional
    public InvCompraTotalesTO getCompraTotalesTO(String empresa, String comPeriodo, String comMotivo, String comNumero)
            throws Exception;

    @Transactional
    public Object[] getCompra(String empresa, String perCodigo, String motCodigo, String compNumero) throws Exception;

    @Transactional
    public List<InvListaConsultaCompraTO> getFunComprasListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception;

    @Transactional
    public List<InvListaConsultaCompraTO> getListaInvConsultaCompra(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, Boolean listarImb) throws Exception;

    @Transactional
    public List<InvListaConsultaCompraTO> listarComprasPorOrdenCompra(String empresa, String sector, String motivo, String numero) throws Exception;

    public List<InvListaConsultaCompraTO> listarComprasPorOrdenCompraYProducto(String empresa, String sector, String motivo, String numero, String producto, int ocSecuencial) throws Exception;

    public RetornoTO getComprasPorPeriodo(String empresa, String codigoSector, String fechaInicio, String fechaFin,
            boolean chk) throws Exception;

    @Transactional
    public String insertarModificarComprasRecepcionTO(InvComprasRecepcionTO invComprasRecepcionTO, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public String guardarClaveAcceso(String codigoEmpresa, String motivo, String numero, String periodo, String claveAcceso, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String validacionesCompras(InvComprasTO invComprasTO, AnxCompraTO anxCompraTO,
            List<AnxCompraDetalleTO> anxCompraDetalleTO) throws Exception;

    @Transactional
    public MensajeTO insertarInvContableComprasTO(String empresa, String periodo, String motivo, String compraNumero,
            String codigoUsuario, boolean recontabilizar, String conNumero, boolean recontabilizarSinPendiente,
            String tipCodigo, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<String> verificarStockCompras(InvCompras invCompras, List<InvComprasDetalle> listInvComprasDetalle)
            throws Exception;

    @Transactional
    public MensajeTO insertarInvComprasTO(InvComprasTO invComprasTO, List<InvComprasDetalleTO> listaInvComprasDetalleTO,
            AnxCompraTO anxCompraTO, List<AnxCompraDetalleTO> anxCompraDetalleTO,
            List<AnxCompraReembolsoTO> anxCompraReembolsoTO, List<AnxCompraFormaPagoTO> anxCompraFormaPagoTO, List<InvAdjuntosCompras> listImagen,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarInvComprasTO(InvComprasTO invComprasTO,
            List<InvComprasDetalleTO> listaInvComprasDetalleTO,
            List<InvComprasDetalleTO> listaInvComprasEliminarDetalleTO, AnxCompraTO anxCompraTO,
            List<AnxCompraDetalleTO> anxCompraDetalleTO, List<AnxCompraDetalleTO> anxCompraDetalleEliminarTO,
            List<AnxCompraReembolsoTO> anxCompraReembolsoTO, List<AnxCompraReembolsoTO> anxCompraReembolsoEliminarTO,
            List<AnxCompraFormaPagoTO> anxCompraFormaPagoTO, List<AnxCompraFormaPagoTO> anxCompraFormaPagoEliminarTO,
            boolean desmayorizar, boolean quitarMotivoAnulacion, InvComprasMotivoAnulacion invComprasMotivoAnulacion,
            List<InvAdjuntosCompras> listImagen,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public void quitarPendiente(InvComprasTO invComprasTO) throws Exception;

    @Transactional
    public MensajeTO validarInvContableComprasDetalleTO(String empresa, String periodo, String motivo,
            String compraNumero, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero) throws Exception;

    @Transactional
    public String mayorizarDesmayorizarComprasSql(InvComprasPK invComprasPK, boolean pendiente, boolean revisado, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String desmayorizarLoteCompra(List< InvListaConsultaCompraTO> listado, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String anularRestaurarComprasSql(InvComprasPK invComprasPK, boolean anulado, boolean actualizarFechaUltimaValidacionSri, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String guardarImagenesCompra(InvComprasPK invComprasPK, List<InvAdjuntosCompras> listInvAdjuntosCompras) throws Exception;

    @Transactional
    public InvComprasDatosBasicoTO getDatosBasicosCompra(ConContablePK conContablePK) throws Exception;

    @Transactional
    public List<InvAdjuntosCompras> getAdjuntosCompra(InvComprasPK invComprasPK) throws Exception;

    public List<InvAdjuntosCompras> convertirStringUTF8(InvComprasPK invComprasPK);

    public List<InvAdjuntosComprasWebTO> convertirStringUTF8(InvComprasPK invComprasPK, String tipo);

    //operaciones
    @Transactional(propagation = Propagation.NEVER)
    public Map<String, Object> consultarCompra(Map<String, Object> map) throws Exception;

    public Map<String, Object> consultarCompraActivo(Map<String, Object> map) throws Exception;

    public InvEntidadTransaccionTO obtenerProveedorDeCompra(String empresa, String periodo, String motivo, String numero) throws Exception;

    @Transactional
    public Map<String, Object> obtenerDatosBasicosCompraNueva(String empresa, String fecha, String provTipoId, String proveedor, String usuario) throws Exception;

    @Transactional
    public Map<String, Object> validarRetencionCompra(String empresa, AnxCompraTO anxCompraTO, InvComprasTO invCompraTO, String usuario) throws Exception;

    @Transactional
    public Map<String, Object> validarRetencionDesdeCompra(String empresa, AnxCompraTO anxCompraTO, InvComprasTO invCompraTO) throws Exception;

    @Transactional
    public Map<String, Object> guardarCompra(
            String empresa,
            String usuario,
            List<InvComprasDetalleTO> listInvComprasDetalleTO,
            List<AnxCompraDetalleTO> listAnxCompraDetalleTO,
            List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO,
            AnxCompraTO anxCompraTO,
            InvComprasTO invCompraTO,
            InvProveedorTO proveedor,
            InvComboFormaPagoTO formaPago,
            AnxFormaPagoTO fp,
            List<InvAdjuntosComprasWebTO> listImagen,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> guardarCompraImb(
            String empresa,
            String usuario,
            List<InvComprasDetalleTO> listInvComprasDetalleTO,
            List<AnxCompraDetalleTO> listAnxCompraDetalleTO,
            List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO,
            AnxCompraTO anxCompraTO,
            InvComprasTO invCompraTO,
            InvProveedorTO proveedor,
            InvComboFormaPagoTO formaPago,
            AnxFormaPagoTO fp,
            List<InvAdjuntosComprasWebTO> listImagen,
            List<InvComprasDetalleImbTO> listaCompraImb,
            List<InvComprasLiquidacionTO> listaCompraLiquidacionTO,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> modificarCompra(String empresa,
            List<InvComprasDetalleTO> listInvComprasDetalleTO,
            List<InvComprasDetalleTO> listInvComprasDetalleTOEliminar,
            List<AnxCompraDetalleTO> listAnxCompraDetalleTO,
            List<AnxCompraDetalleTO> listAnxCompraDetalleTOEliminar,
            List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO,
            List<AnxCompraReembolsoTO> listAnxCompraReembolsoTOEliminar,
            AnxCompraTO anxCompraTO,
            InvComprasTO invCompraTO,
            InvComboFormaPagoTO formaPago,
            AnxFormaPagoTO fp,
            AnxFormaPagoTO fpEliminar,
            boolean desmayorizar,
            List<InvAdjuntosComprasWebTO> listImagen,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> modificarCompraInventario(String empresa,
            List<InvComprasDetalleTO> listInvComprasDetalleTO,
            List<InvComprasDetalleTO> listInvComprasDetalleTOEliminar,
            List<AnxCompraDetalleTO> listAnxCompraDetalleTO,
            List<AnxCompraDetalleTO> listAnxCompraDetalleTOEliminar,
            List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO,
            List<AnxCompraReembolsoTO> listAnxCompraReembolsoTOEliminar,
            AnxCompraTO anxCompraTO,
            InvComprasTO invCompraTO,
            InvComboFormaPagoTO formaPago,
            AnxFormaPagoTO fp,
            AnxFormaPagoTO fpEliminar,
            boolean desmayorizar,
            List<InvAdjuntosComprasWebTO> listImagen,
            List<InvComprasDetalleImbTO> listaCompraImb,
            List<InvComprasDetalleImbTO> listaCompraImbEliminar,
            List<InvComprasLiquidacionTO> listaCompraLiquidacionTO,
            List<InvComprasLiquidacionTO> listInvComprasLiquidacionTOEliminar,
            SisInfoTO sisInfoTO) throws Exception;

    /*Retenciones*/
    public boolean isFechaDentroDeDiasHabiles(String fechaRetencion, String fechaCompra, String empresa);

    public List<AnxCompraDetalleRetencionTO> obtenerDetalleRetencion(String empresa, String fecha, List<InvComprasDetalleTO> listaItemInvComprasDetalleTO, boolean esNueva) throws Exception;

    public String obtenerCodigoSustento(String empresa, String fecha, List<InvComprasDetalleTO> listaInvComprasDetalleTO) throws Exception;

    @Transactional
    public Map<String, Object> contabilizarComprasTrans(String empresa, InvComprasTO invCompraTO, MensajeTO mensajeTO1, SisInfoTO usuario) throws Exception;

    @Transactional
    public Map<String, Object> reContabilizarComprasTrans(String empresa, InvComprasTO invCompraTO, MensajeTO mensajeTO1, SisInfoTO usuario) throws Exception;

    @Transactional
    public Map<String, Object> obtenerDatosBasicosRetencionNueva(Map<String, Object> datos) throws Exception;

    @Transactional
    public Map<String, Object> obtenerDatosBasicosRetencionCreada(Map<String, Object> datos) throws Exception;

    @Transactional
    public Map<String, Object> consultarRetencionCompra(Map<String, Object> datos) throws Exception;

    @Transactional
    public Map<String, Object> validarFechaRetencion(Map<String, Object> datos) throws Exception;

    @Transactional
    public boolean validarFechasDeRetencionYComprasMismoMes(String fechaRetencion, String fechaCompra, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> buscarComprobanteElectronico(String empresa, String clave, RespuestaComprobante respuestaComprobante, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> compararCombrobanteElectronico(String empresa, String clave, SisInfoTO sisInfoTO) throws Exception;

    public List<InvFunComprasProgramadasListadoTO> listarComprasProgramadas(String empresa, String periodo, String motivo, String desde, String hasta, String nRegistros) throws Exception;

    public String eliminarComprasProgramadas(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarCompras(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

    public String duplicarComprasProgramada(String empresa, String numeroCompra, Date date, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> suprimirCompra(InvComprasTO invComprasTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> obtenerDatosParaAnularRetencion(Map<String, Object> datos) throws Exception;

    public List<InvFunComprasVsVentasTonelajeTO> listarComprasVsVentasTonelaje(String empresa, String desde, String hasta, String sector, String bodega, String proveedor) throws Exception;

    public List<InvConsultaCompraTO> listarComprasPorProveedor(String empresa, String periodo, String motivo, String numero, String provCodigo) throws Exception;

    public List<InvConsultaCompraTO> listarComprasPorProveedorSoloNotaEntrega(String empresa, String periodo, String motivo, String numero, String provCodigo) throws Exception;

    public List<InvConsultaCompraTO> listarComprasImb(String empresa, String periodo, String motivo, String numero) throws Exception;

    public boolean guardarImagenesCompra(InvComprasPK pk, List<InvAdjuntosComprasWebTO> imagenes, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosAjusteInventario(String empresa, String accion, String bodega, String hasta, String categoria, String usuario) throws Exception;

    @Transactional
    public Map<String, Object> crearAjusteInventario(
            String empresa,
            String usuario,
            List<InvComprasDetalleTO> listInvAjusteInventarioDetalleTO,
            InvComprasTO invCompraTO,
            InvComboFormaPagoTO formaPago,
            SisInfoTO sisInfoTO) throws Exception;

    public InvComprasTO getComprasTO(String empresa, String tipo, String numeroDocumento) throws Exception;

    public Boolean actualizarClaveExterna(InvComprasPK pk, String clave, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> convertirXMLResultado(String empresa, String clave, String xml, boolean validarCompra) throws Exception;

    public List<InvListaConsultaCompraTO> obtenerListadoIMBPendientes(String empresa, String periodo, String motivo, String proveedor, String producto, String fechaInicio, String fechaFin) throws Exception;

    public List<InvCompras> obtenerListadoComprasSaldosImportados(String empresa, String motivo, String sector, String fecha) throws Exception;

    public List<String> validarExistenciasCompras(List<InvCompras> compras, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> insertarProveedoresRezagadosLote(List<InvProveedor> proveedores, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO insertarInvComprasTO(InvCompras invCompras, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarInvCompras(InvCompras invCompras, SisInfoTO sisInfoTO) throws Exception;

    public String obtenerLicenciaScanner(String empresa) throws Exception;

    public Map<String, Object> obtenerCompras(String empresa, String tipo, String numeroDocumento, String provCodigo) throws Exception;

    @Transactional(propagation = Propagation.NEVER)
    public Map<String, Object> consultarCompraPorContablePk(Map<String, Object> map) throws Exception;

}
