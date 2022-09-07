package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosCoberturaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVsCostoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaSecuencialesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListadoCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteVentaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.Date;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;

public interface VentasService {

    @Transactional
    public String getConteoNumeroFacturaVenta(String empresaCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception;

    @Transactional
    public Object[] getVenta(String empresa, String perCodigo, String motCodigo, String compNumero) throws Exception;

    @Transactional
    public InvVentaCabeceraTO getInvVentaCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroVenta) throws Exception;

    @Transactional
    public List<InvListaConsultaVentaTO> getFunVentasListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception;

    @Transactional
    public List<InvListaConsultaVentaTO> getListaInvConsultaVenta(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros) throws Exception;

    @Transactional
    public List<InvListaConsultaVentaTO> getListaInvConsultaVentaFiltrado(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, String tipoDocumento) throws Exception;

    @Transactional
    public List<InvListadoCobrosTO> invListadoCobrosTO(String empresa, String periodo, String motivo, String numero)
            throws Exception;

    @Transactional
    public List<InvFunVentasTO> getInvFunVentasTO(String empresa, String desde, String hasta, String motivo,
            String cliente, String documento, String grupo_empresarial) throws Exception;

    @Transactional
    public List<InvFunVentasConsolidandoProductosTO> getInvFunVentasConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega, String cliente) throws Exception;

    @Transactional
    public List<InvFunVentasConsolidandoProductosCoberturaTO> getInvFunVentasConsolidandoProductosCoberturaTO(String empresa,
            String desde, String hasta, String sector, String bodega, String motivo, String cliente) throws Exception;

    @Transactional
    public List<InvFunVentasConsolidandoClientesTO> getInvFunVentasConsolidandoClientesTO(String empresa, String sector,
            String desde, String hasta) throws Exception;

    @Transactional
    public List<InvFunVentasVsCostoTO> getInvFunVentasVsCostoTO(String empresa, String desde, String hasta,
            String bodega, String cliente) throws Exception;

    public MensajeTO insertarInvContableVentasTO(String empresa, String periodo, String motivo, String ventaNumero,
            String codigoUsuario, boolean recontabilizar, String conNumero, String tipCodigo, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public InvVentaRetencionesTO getInvVentaRetencionesTO(String codigoEmpresa, String facturaNumero) throws Exception;

    public InvVentaRetencionesTO getInvVentaRetencionesTO(String codigoEmpresa, String tipoDocumento, String numero) throws Exception;

    public InvVentas obtenerInvVenta(String empresa, String periodo, String motivo, String numero) throws Exception;

    @Transactional
    public MensajeTO insertarInvVentasTO(InvVentasTO invVentasTO, List<InvVentasDetalleTO> listaInvVentasDetalleTO,
            AnxVentaTO anxVentasTO, String tipoDocumentoComplemento, String numeroDocumentoComplemento,
            Boolean isComprobanteElectronica, boolean vtaRecurrente, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarInvVentasTO(InvVentasTO invVentasTO, List<InvVentasDetalleTO> listaInvVentasDetalleTO,
            List<InvVentasDetalleTO> listaInvVentasEliminarDetalleTO, boolean desmayorizar, AnxVentaTO anxVentasTO,
            boolean quitarAnulado, String tipoDocumentoComplemento, String numeroDocumentoComplemento,
            InvVentasMotivoAnulacion invVentasMotivoAnulacion, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public void quitarPendiente(InvVentasTO invVentasTO) throws Exception;

    @Transactional
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero) throws Exception;

    @Transactional
    public List<InvFunVentasTO> listarInvFunVentasTO(String empresa, String desde, String hasta, String motivo, String cliente,
            String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception;

    public List<InvFunVentasTO> listarInvFunVentasTOAgrupadoTipoDocumento(String empresa, String desde, String hasta, String motivo, String cliente,
            String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception;

    public List<InvFunVentasTO> listarInvFunVentasTOAgrupadoTipoContribuyente(String empresa, String desde, String hasta, String motivo, String cliente,
            String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception;

    public String asignarNumeroDocumento(String empresa, String tipoDocumento, SisInfoTO sisInfoTO) throws Exception;

    public boolean validarNumero(String empresa, String tipoDocumento, String numeroDocumento, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaVentas(Map<String, Object> map) throws Exception;

    public int obtenerFacturasDisponiblesPorPuntoEmision(String empresa, String tipoDocumento, String secuencial) throws Exception;

    public Map<String, Object> obtenerDatosListadoVentas(Map<String, Object> map) throws Exception;

    @Transactional(propagation = Propagation.NEVER)
    public Map<String, Object> consultarVenta(Map<String, Object> map) throws Exception;

    @Transactional
    public Map<String, Object> obtenerDatosParaVentasRecurrentes(Map<String, Object> map) throws Exception;

    @Transactional
    public String desmayorizarVenta(InvVentasTO invVentasTO, AnxVentaTO anxVentaTO, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    @Transactional
    public List<ReporteVentaDetalle> obtenerReporteVentaDetalle(InvVentasTO ventasTO, List<InvVentasDetalleTO> listInvVentasDetalleTO, boolean isComprobanteElectronico, SisInfoTO sisInfoTO) throws Exception;

    public List<InvListaConsultaVentaTO> getListaInvConsultaVentaPorTipoDoc(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, String tipoDocumento) throws Exception;

    public InvEntidadTransaccionTO obtenerClienteDeVenta(String empresa, String periodo, String motivo, String numero) throws Exception;

    @Transactional
    public MensajeTO insertarInventarioVentasTO(InvVentasTO invVentasTO, List<InvVentasDetalleTO> listaInvVentasDetalleTO, AnxVentaTO anxVentasTO, String tipoDocumentoComplemento,
            String numeroDocumentoComplemento, String motivoDocumentoComplemento, Boolean isComprobanteElectronica, InvVentaGuiaRemision invVentaGuiaRemision,
            List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTO, AnxVentaExportacionTO anxVentaExportacionTO, List<InvVentasDatosAdjuntos> listadoImagenes, List<AnxVentaReembolsoTO> listAnxVentaReembolsoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarInventarioVentasTO(InvVentasTO invVentasTO, List<InvVentasDetalleTO> listaInvVentasDetalleTO, List<InvVentasDetalleTO> listaInvVentasEliminarDetalleTO,
            boolean desmayorizar, AnxVentaTO anxVentasTO, boolean quitarAnulado, String tipoDocumentoComplemento, String numeroDocumentoComplemento, String motivoDocumentoComplemento,
            InvVentasMotivoAnulacion invVentasMotivoAnulacion, InvVentaGuiaRemision invVentaGuiaRemision,
            List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTO,
            List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTOEliminar,
            AnxVentaExportacionTO anxVentaExportacionTO,
            List<InvVentasDatosAdjuntos> listadoImagenes,
            List<AnxVentaReembolsoTO> listAnxVentaReembolsoTO,
            List<AnxVentaReembolsoTO> listAnxVentaReembolsoEliminarTO,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<ReporteVentaDetalle> obtenerReporteVentaDetalleProducto(String empresa, String numero, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String desmayorizarLoteVenta(InvVentasTO invVentasTO, AnxVentaTO anxVentaTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> suprimirVenta(InvVentasTO invVentasTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO insertarVentasRecurrentes(String empresa, InvCliente cliente, String tipodocumento, InvVentasTO venta, List<InvClientesVentasDetalle> detallesAgrupados, int grupo, Integer contrato, String secuencial, String descripcionProducto, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> listarClientesParaVentarecurrente(Map<String, Object> map) throws Exception;

    @Transactional
    public List<InvVentasDetalleTO> obtenerVentaDetalleTOPorNumero(String vtaEmpresa, String vtaPeriodo, String vtaMotivo, String vtaNumero) throws Exception;

    public void notificarPorCorreoVentasConError(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RespuestaWebTO> listado, String usuario, int guardadas) throws Exception;

    public List<InvFunVentasVendedorTO> listarInvFunVentasVendedorTO(String empresa, String desde, String hasta) throws Exception;

    public List<InvListaSecuencialesTO> listarInvListaSecuencialesVentas(String empresa, String tipoDocumento) throws Exception;

    public String eliminarVentas(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

    public boolean validarNumeracionSegunSector(String empresa, String tipoDocumento, String codigoSector, String numero) throws Exception;

    @Transactional
    public Map<String, Object> validarNumeracionVenta(Map<String, Object> map) throws Exception;

    @Transactional
    public Date cambiarFechaVencimientoVenta(InvVentasPK vtaPK, Date fecha, SisInfoTO sisInfoTO) throws Exception;

    public InvVentasTO getInvVentasTO(String empresa, String tipo, String numeroDocumento) throws Exception;

    public Boolean actualizarClaveExternaVenta(InvVentasPK pk, String clave, SisInfoTO sisInfoTO) throws Exception;
//SALDO POR COBRAR

    public List<InvVentas> obtenerListadoVentasSaldosImportados(String empresa, String motivo, String sector, String fecha) throws Exception;

    public List<String> verificarExistenciaVentas(List<InvVentas> clientes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> insertarClientesRezagadosLote(List<InvCliente> clientes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO insertarInvVentas(InvVentas invVentas, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarInvVentas(InvVentas invVentas, SisInfoTO sisInfoTO) throws Exception;

    public boolean anularContableVentas(InvVentasPK pk, ConContablePK pkContable, SisInfoTO sisInfoTO) throws Exception;

    //Imagenes
    public boolean guardarImagenesVentas(List<InvVentasDatosAdjuntos> imagenes, InvVentasPK pk, SisInfoTO sisInfoTO) throws Exception;

    public List<InvVentasDatosAdjuntos> listarImagenesDeVenta(InvVentasPK pk) throws Exception;

    @Transactional(propagation = Propagation.NEVER)
    public Map<String, Object> consultarVentaSegunContable(Map<String, Object> map) throws Exception;

}
