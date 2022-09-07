package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.ConceptoDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnexoNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxComprobantesElectronicosRecibidosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxHomologacionProductoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraFormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraReembolsoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ConceptoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.FormaPagoAnexoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.LiquidacionCompraElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.PorcentajeIvaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.SustentoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoComprobanteService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoTransaccionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.UrlWebServicesService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaService;
import ec.com.todocompu.ShrimpSoftServer.caja.dao.CajaDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.DetalleDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.BodegaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDetalleSeriesDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasLiquidacionDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasMotivoAnuladoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasRecepcionDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoSaldosDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProveedorDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.EquipoControlDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PiscinaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.ScannerConfiguracionDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.ConfiguracionComprasService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioDetalleService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.NumeroColumnaDesconocidadComprasPorPeriodo;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.Factura;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.ImpuestoFactura;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.FacturaReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.ArchivoUtils;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.DocumentoReporteRIDE;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleRetencionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxUltimaAutorizacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDividendo;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraReembolso;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidosPk;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxConcepto;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronica;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntosComprasWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraTotalesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDatosBasicoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleImbTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleSeriesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasPorPeriodoTO;
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
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCompraSustentoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListadoPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorDatosXMLTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvSecuenciaComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ItemComprobanteElectronico;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ItemResultadoBusquedaElectronico;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraSaldo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvAdjuntosCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleImb;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasRecepcion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionCompras;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisScannerConfiguracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoAmbienteEnum;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoComprobanteEnum;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.Autorizacion;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

@Service
public class ComprasServiceImpl implements ComprasService {

    @Autowired
    private BodegaDao bodegaDao;
    @Autowired
    private ComprasDao comprasDao;
    @Autowired
    private ComprasDetalleDao comprasDetalleDao;
    @Autowired
    private ComprasMotivoAnuladoDao comprasMotivoAnuladoDao;
    @Autowired
    private ComprasMotivoDao comprasMotivoDao;
    @Autowired
    private ComprasRecepcionDao comprasRecepcionDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private ProductoSaldosDao productoSaldosDao;
    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private DetalleDao detalleDao;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private CompraDao compraDao;
    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private FormaPagoAnexoService formaPagoAnexoService;
    @Autowired
    private ConceptoDao conceptoDao;
    @Autowired
    private ComprasDetalleService comprasDetalleService;
    @Autowired
    private ComprasDetalleSeriesDao comprasDetalleSeriesDao;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private TipoTransaccionService tipoTransaccionService;
    @Autowired
    private ComprasFormaPagoService comprasFormaPagoService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private TipoComprobanteService tipoComprobanteService;
    @Autowired
    private ConceptoService conceptoService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CompraService compraService;
    @Autowired
    private CajaDao cajaDao;
    @Autowired
    private AnexoNumeracionService numeracionService;
    @Autowired
    private ConfiguracionComprasService configuracionComprasService;
    @Autowired
    private SustentoService sustentoService;
    @Autowired
    private CompraElectronicaService compraElectronicaService;
    @Autowired
    private ComprasMotivoService comprasMotivoService;
    @Autowired
    private CompraDetalleDao compraDetalleDao;
    @Autowired
    private CompraFormaPagoService compraFormaPagoService;
    @Autowired
    private CompraReembolsoService compraReembolsoService;
    @Autowired
    private UrlWebServicesService urlWebServicesService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private PedidosOrdenCompraMotivoService pedidosOrdenCompraMotivoService;
    @Autowired
    private PorcentajeIvaService porcentajeIvaService;
    @Autowired
    private PedidosOrdenCompraService pedidosOrdenCompraService;
    @Autowired
    private InvSeriesService invSeriesService;
    @Autowired
    private ScannerConfiguracionDao scannerConfiguracionDao;
    @Autowired
    private ComprasDetalleImbService comprasDetalleImbService;
    @Autowired
    private LiquidacionMotivoService liquidacionMotivoService;
    @Autowired
    private ComprasLiquidacionDao comprasLiquidacionDao;
    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private PiscinaService piscinaService;
    @Autowired
    private ProductoCategoriaService productoCategoriaService;
    @Autowired
    private InvProveedorTransportistaService invProveedorTransportistaService;
    @Autowired
    private AnxHomologacionProductoService anxHomologacionProductoService;
    @Autowired
    private AnxComprobantesElectronicosRecibidosService anxComprobantesElectronicosRecibidosService;
    @Autowired
    private ProveedorCategoriaService proveedorCategoriaService;
    @Autowired
    private LiquidacionCompraElectronicaService liquidacionCompraElectronicaService;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private EquipoControlDao equipoControlDao;
    private boolean comprobar = false;
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String getConteoNumeroFacturaCompra(String empresaCodigo, String provCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception {
        return comprasDao.getConteoNumeroFacturaCompra(empresaCodigo, provCodigo, compDocumentoTipo,
                compDocumentoNumero);
    }

    @Override
    public InvCompraCabeceraTO getInvCompraCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroCompra) throws Exception {
        return comprasDao.getInvCompraCabeceraTO(empresa, codigoPeriodo, motivo, numeroCompra);
    }

    @Override
    public BigDecimal getPrecioProductoUltimaCompra(String empresa, String produCodigo) throws Exception {
        return comprasDao.getPrecioProductoUltimaCompra(empresa, produCodigo);
    }

    @Override
    public List<InvListadoPagosTO> invListadoPagosTO(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return comprasDao.invListadoPagosTO(empresa, periodo, motivo, numero);
    }

    @Override
    public List<InvFunComprasTO> getInvFunComprasTO(String empresa, String desde, String hasta, String motivo,
            String proveedor, String documento, String formaPago) throws Exception {
        return comprasDao.getInvFunComprasTO(empresa, desde, hasta, motivo, proveedor, documento, formaPago);
    }

    @Override
    public List<InvFunComprasTO> getInvFunComprasTOAgrupadoTipoDocumento(String empresa, String desde, String hasta, String motivo, String proveedor, String documento, String formaPago) throws Exception {
        return comprasDao.getInvFunComprasTOAgrupadoTipoDocumento(empresa, desde, hasta, motivo, proveedor, documento, formaPago);
    }

    @Override
    public List<InvFunComprasTO> getInvFunComprasTOAgrupadoTipoContribuyente(String empresa, String desde, String hasta, String motivo, String proveedor, String documento, String formaPago) throws Exception {
        return comprasDao.getInvFunComprasTOAgrupadoTipoContribuyente(empresa, desde, hasta, motivo, proveedor, documento, formaPago);
    }

    @Override
    public List<InvFunComprasConsolidandoProductosTO> getInvFunComprasConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String motivo, String proveedor) throws Exception {
        return comprasDao.getInvFunComprasConsolidandoProductosTO(empresa, desde, hasta, sector, motivo, proveedor);
    }

    @Override
    public List<InvFunComprasVsVentasTonelajeTO> listarComprasVsVentasTonelaje(String empresa, String desde, String hasta, String sector, String bodega, String proveedor) throws Exception {
        return comprasDao.listarComprasVsVentasTonelaje(empresa, desde, hasta, sector, bodega, proveedor);
    }

    @Override
    public List<InvSecuenciaComprobanteTO> getInvSecuenciaComprobanteTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        return comprasDao.getInvSecuenciaComprobanteTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<InvFunUltimasComprasTO> getInvFunUltimasComprasTOs(String empresa, String producto) throws Exception {
        return comprasDao.getInvFunUltimasComprasTOs(empresa, producto);
    }

    @Override
    public InvComprasTO getComprasTO(String empresa, String periodo, String motivo, String numeroCompra)
            throws Exception {
        return comprasDao.getComprasTO(empresa, periodo, motivo, numeroCompra);
    }

    @Override
    public InvCompraTotalesTO getCompraTotalesTO(String empresa, String comPeriodo, String comMotivo, String comNumero)
            throws Exception {
        List<InvListaDetalleComprasTO> listaDetalleTO = comprasDetalleDao.getListaInvCompraDetalleTO(empresa,
                comPeriodo, comMotivo, comNumero);
        BigDecimal baseImponible = new BigDecimal("0.00");
        BigDecimal base0 = new BigDecimal("0.00");
        BigDecimal ice = new BigDecimal("0.00");
        for (InvListaDetalleComprasTO detalleComprasTO : listaDetalleTO) {
            ice = ice.add(detalleComprasTO.getDetIce());
            if (detalleComprasTO.getGravaIva().compareTo("GRAVA") == 0) {
                baseImponible = baseImponible.add(detalleComprasTO.getDetalleSubtotal());
            } else {
                base0 = base0.add(detalleComprasTO.getDetalleSubtotal());
            }

        }
        InvCompraTotalesTO compraTotalesTO = new InvCompraTotalesTO();
        compraTotalesTO.setCompBase0(base0);
        compraTotalesTO.setCompBaseImponible(baseImponible);
        compraTotalesTO.setCompIce(ice);
        return compraTotalesTO;
    }

    @Override
    public Object[] getCompra(String empresa, String perCodigo, String motCodigo, String compNumero) throws Exception {
        return comprasDao.getCompra(empresa, perCodigo, motCodigo, compNumero);
    }

    @Override
    public List<InvListaConsultaCompraTO> getFunComprasListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception {
        return comprasDao.getFunComprasListado(empresa, fechaDesde, fechaHasta, status);
    }

    @Override
    public List<InvListaConsultaCompraTO> getListaInvConsultaCompra(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, Boolean listarImb) throws Exception {
        return comprasDao.getListaInvConsultaCompra(empresa, periodo, motivo, busqueda, nRegistros, listarImb);
    }

    @Override
    public List<InvListaConsultaCompraTO> obtenerListadoIMBPendientes(String empresa, String periodo, String motivo, String proveedor, String producto, String fechaInicio, String fechaFin) throws Exception {
        return comprasDao.obtenerListadoIMBPendientes(empresa, periodo, motivo, proveedor, producto, fechaInicio, fechaFin);
    }

    @Override
    public List<InvListaConsultaCompraTO> listarComprasPorOrdenCompra(String empresa, String sector, String motivo, String numero) throws Exception {
        return comprasDao.listarComprasPorOrdenCompra(empresa, sector, motivo, numero);
    }

    @Override
    public List<InvListaConsultaCompraTO> listarComprasPorOrdenCompraYProducto(String empresa, String sector, String motivo, String numero, String producto, int ocSecuencial) throws Exception {
        return comprasDao.listarComprasPorOrdenCompraYProducto(empresa, sector, motivo, numero, producto, ocSecuencial);
    }

    @Override
    public List<InvFunComprasProgramadasListadoTO> listarComprasProgramadas(String empresa, String periodo, String motivo, String desde, String hasta, String nRegistros) throws Exception {
        return comprasDao.listarComprasProgramadas(empresa, periodo, motivo, desde, hasta, nRegistros);
    }

    @Override
    public String eliminarComprasProgramadas(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        /// PREPARANDO OBJETO SISSUCESO
        susClave = empresa + "|" + periodo + "|" + motivo + "|" + numero;
        susDetalle = "Se eliminó la compra programada: " + susClave;
        susSuceso = "DELETE";
        susTabla = "inventario.inv_compras";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return comprasDao.eliminarComprasProgramadas(empresa, periodo, motivo, numero, sisSuceso);
    }

    @Override
    public String eliminarCompras(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        /// PREPARANDO OBJETO SISSUCESO
        susClave = empresa + "|" + periodo + "|" + motivo + "|" + numero;
        susDetalle = "Se eliminó la compra: " + susClave;
        susSuceso = "DELETE";
        susTabla = "inventario.inv_compras";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return comprasDao.eliminarCompras(empresa, periodo, motivo, numero, sisSuceso);
    }

    @Override
    public String duplicarComprasProgramada(String empresa, String numeroCompra, Date date, SisInfoTO sisInfoTO) throws Exception {
        List<String> pks = UtilsValidacion.separar(numeroCompra, "|");
        return comprasDao.duplicarComprasProgramada(empresa, pks.get(0), pks.get(1), pks.get(2), date, sisInfoTO);
    }

    @Override
    public RetornoTO getComprasPorPeriodo(String empresa, String codigoSector, String fechaInicio, String fechaFin,
            boolean chk) throws Exception {
        String mensaje;
        RetornoTO retornoTO = new RetornoTO();
        try {
            List<InvComprasPorPeriodoTO> prdComprasPorPeriodoTOs = comprasDao.getComprasPorPeriodo(empresa, codigoSector, fechaInicio, fechaFin, chk);
            mensaje = "T";
            NumeroColumnaDesconocidadComprasPorPeriodo obj = new NumeroColumnaDesconocidadComprasPorPeriodo();
            obj.agruparCabeceraColumnas(prdComprasPorPeriodoTOs);
            obj.agruparProductos(prdComprasPorPeriodoTOs);
            obj.llenarObjetoParaTabla(prdComprasPorPeriodoTOs);
            retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
            retornoTO.setColumnas(obj.getColumnas());
            retornoTO.setDatos(obj.getDatos());
        } catch (Exception e) {
            comprobar = false;
            mensaje = "FOcurrió un error al obtener los datos de la Base de Datos. \nContacte con el administrador...";
        }
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public String insertarModificarComprasRecepcionTO(InvComprasRecepcionTO invComprasRecepcionTO, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        boolean periodoCerrado = false;
        // try {
        if (!UtilsValidacion.isFechaSuperior(invComprasRecepcionTO.getRecepFecha(), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invComprasRecepcionTO.getCompEmpresa());
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(invComprasRecepcionTO.getRecepFecha(), "yyyy-MM-dd")
                        .getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(invComprasRecepcionTO.getRecepFecha(), "yyyy-MM-dd")
                                .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                                .getTime()) {
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (!periodoCerrado) {
                InvCompras invCompras = comprasDao.buscarInvCompras(invComprasRecepcionTO.getCompEmpresa(),
                        invComprasRecepcionTO.getCompPeriodo(), invComprasRecepcionTO.getCompMotivo(),
                        invComprasRecepcionTO.getCompNumero());

                int anioCompra = Integer.parseInt(invCompras.getCompFecha().toString().split("-")[0]);
                int mesCompra = Integer.parseInt(invCompras.getCompFecha().toString().split("-")[1]);

                int anioRecepcion = Integer.parseInt(invComprasRecepcionTO.getRecepFecha().split("-")[0]);
                int mesRecepcion = Integer.parseInt(invComprasRecepcionTO.getRecepFecha().split("-")[1]);

                if ((mesRecepcion <= mesCompra || anioRecepcion < anioCompra) && anioRecepcion <= anioCompra) {
                    if (invCompras != null && !invCompras.getCompAnulado() && !invCompras.getCompPendiente()) {
                        String mensajeAux1 = "";
                        String mensajeAux2 = "";
                        InvComprasRecepcionTO invComprasRecepcionTOAux = comprasRecepcionDao.getInvComprasRecepcionTO(
                                invComprasRecepcionTO.getCompEmpresa(), invComprasRecepcionTO.getCompPeriodo(),
                                invComprasRecepcionTO.getCompMotivo(), invComprasRecepcionTO.getCompNumero());
                        if (invComprasRecepcionTOAux != null) {
                            mensajeAux1 = "modificó";
                            mensajeAux2 = "UPDATE";
                            invComprasRecepcionTO.setRecep_secuencial(invComprasRecepcionTOAux.getRecep_secuencial());
                        } else {
                            mensajeAux1 = "ingresó";
                            mensajeAux2 = "INSERT";
                            invComprasRecepcionTO.setRecep_secuencial(0L);
                        }
                        /// PREPARANDO OBJETO SISSUCESO
                        susClave = invComprasRecepcionTO.getRecepFecha();
                        susDetalle = "Se " + mensajeAux1 + " la compra recepcion: "
                                + invComprasRecepcionTO.getCompEmpresa() + " | " + invComprasRecepcionTO.getCompMotivo()
                                + " | " + invComprasRecepcionTO.getCompNumero();
                        susSuceso = mensajeAux2;
                        susTabla = "inventar.inv_compras_recepcion";
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                sisInfoTO);

                        InvComprasRecepcion invComprasRecepcion = ConversionesInventario.convertirInvComprasRecepcion_InvComprasRecepcionTO(invComprasRecepcionTO);
                        invComprasRecepcion.setInvCompras(invCompras);
                        if (comprasRecepcionDao.insertarModificarinvComprasRecepcion(invComprasRecepcion, sisSuceso)) {
                            retorno = "TEl dato ha sido guardado correctamente...";
                        } else {
                            retorno = "FHubo un error al guardar la información...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else {
                        retorno = "FNo se pudo obtener la relación con la Compra, puede que este:\n - Anulada.\n - Pendiente.\n - No existe.\n\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "F<html>La fecha de recepción debe ser menor o igual  al mes de la fecha de compra "
                            + invCompras.getCompFecha().toString() + "</html>";
                }
            } else {
                retorno = "F<html>No se puede cambiar la fecha de recepción de la compra debido a que el período se encuentra cerrado...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        return retorno;
    }

    @Override
    public String validacionesCompras(InvComprasTO invComprasTO, AnxCompraTO anxCompraTO,
            List<AnxCompraDetalleTO> anxCompraDetalleTO) throws Exception {
        String verificar = "";
        if (UtilsValidacion.isFechaSuperior(invComprasTO.getCompFecha(), "yyyy-MM-dd")) {
            verificar = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        } else {
            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invComprasTO.getEmpCodigo());
            boolean periodoCerrado = false;
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(invComprasTO.getCompFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(invComprasTO.getCompFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    comprobar = true;
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }
            if (!comprobar) {
                verificar = "F <html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            } else if (periodoCerrado) {
                verificar = "F <html>No se puede MAYORIZAR, DESMAYORIZAR o ANULAR debido a que el periodo se encuentra cerrado</html>";
            } else if (!comprasMotivoDao.comprobarInvComprasMotivo(invComprasTO.getEmpCodigo(), invComprasTO.getCompMotivo())) {
                verificar = "F <html>No se encuentra el Motivo...</html>";
            } else if (proveedorDao.buscarInvProveedor(invComprasTO.getEmpCodigo(), invComprasTO.getProvCodigo()) == null) {
                verificar = "F<html>El Proveedor que escogió ya no está disponible...\nIntente de nuevo, escoja otro Proveedor o contacte con el administrador</html>";
            } else if (anxCompraTO != null && ventaDao
                    .getNumeroAutorizacion(invComprasTO.getEmpCodigo(), anxCompraTO.getCompRetencionNumero(), "07",
                            ("'" + anxCompraTO.getCompRetencionFechaEmision() + "'"))
                    .getNumeroAutorizacion().trim().equals("ANULADO")) {
                verificar = "F<html>El NÚMERO DE RETENCIÓN que ingresó se encuentra ANULADO</html>";
            } else {
                boolean exixste = true;
                String nombreConcepto = "";
                for (int i = 0; i < anxCompraDetalleTO.size(); i++) {
                    if (conceptoDao.obtenerAnexo(anxCompraTO.getCompRetencionFechaEmision(), anxCompraDetalleTO.get(i).getDetConcepto()) == null) {
                        exixste = false;
                        nombreConcepto = anxCompraDetalleTO.get(i).getDetConcepto();
                        i = anxCompraDetalleTO.size();
                    }
                }
                if (!exixste) {
                    verificar = "F<html>El Concepto de Retención " + nombreConcepto
                            + " que ha escogido no se encuentra disponible.<br>Contacte con el administrador.</html>";
                } else {
                    String codigoFactura = comprasDao.getConteoNumeroFacturaCompra(invComprasTO.getEmpCodigo(),
                            invComprasTO.getProvCodigo(), invComprasTO.getCompDocumentoTipo(),
                            invComprasTO.getCompDocumentoNumero());
                    String codigoFacturaAux = invComprasTO.getEmpCodigo().trim()
                            .concat(invComprasTO.getCompPeriodo().trim().concat(
                                    invComprasTO.getCompMotivo().trim().concat(invComprasTO.getCompNumero().trim())));
                    if (!(codigoFactura.trim().equals(codigoFacturaAux))
                            || !(invComprasTO.getCompDocumentoNumero().equals("999-999-999999999")
                            || invComprasTO.getCompDocumentoTipo().equals("00")
                            && invComprasTO.getCompDocumentoTipo().equals("99"))) {
                        verificar = "F<html>El Número de comprobante que ingresó ya existe..<br>Intente de nuevo o contacte con el administrador</html>";
                    }
                }
            }
        }
        return verificar;
    }

    @Override
    public MensajeTO insertarInvContableComprasTO(String empresa, String periodo, String motivo, String compraNumero,
            String codigoUsuario, boolean recontabilizar, String conNumero, boolean recontabilizarSinPendiente,
            String tipCodigo, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        ConContable conContable = null;
        String retorno;
        comprobar = false;
        boolean contablePendiente = false;
        String tipoCodigo;
        if (!recontabilizar) {
            tipoCodigo = tipoDao.buscarTipoContablePorMotivoCompra(empresa, motivo);
        } else {
            tipoCodigo = tipCodigo;
        }
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);

        if (tipoDao.buscarTipoContable(empresa, tipoCodigo)) {
            InvCompras invCompras = comprasDao.buscarInvCompras(empresa, periodo, motivo, compraNumero);
            if ((invCompras.getConNumero() != null && !recontabilizar)) {
                retorno = "F<html>La compra ya ha sido recontabilizada.</html>";
            } else {
                List<InvListaDetalleComprasTO> listaDetalleTO = comprasDetalleDao.getListaInvCompraDetalleTO(empresa, periodo, motivo, compraNumero);
                List<InvListaDetalleComprasTO> listaDetalleFinal = new ArrayList<InvListaDetalleComprasTO>();
                listaDetalleFinal.add(listaDetalleTO.get(0));
                // METODO PARA VERIFICAR CUENTAS EN LOS PRODUCTOS ANTES DE CONTABILIZAR COMPRA
                AnxCompraTO anxCompraTO = null;
                if (!invCompras.getCompDocumentoTipo().equals("00")) {
                    anxCompraTO = compraDao.getAnexoCompraTO(empresa, periodo, motivo, compraNumero);
                    if (anxCompraTO != null) {
                        // metodo para que el contable escoja la fecha de la retencion Ruc_de_icapar
                        if (sisEmpresaParametros.getEmpCodigo().getEmpRuc().compareTo("0190329313001") == 0 || sisEmpresaParametros.getEmpCodigo().getEmpRuc().compareTo("0791726867001") == 0) {
                            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);
                            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                                if (UtilsValidacion.fecha(anxCompraTO.getCompRetencionFechaEmision(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime() && UtilsValidacion.fecha(anxCompraTO.getCompRetencionFechaEmision(), "yyyy-MM-dd")
                                        .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                                    periodo = sisListaPeriodoTO.getPerCodigo();
                                }
                            }
                        }
                    } else {
                        periodo = invCompras.getInvComprasPK().getCompPeriodo();
                    }
                }
                ////// CREANDO CONTABLE TO
                ConContableTO conContableTO = new ConContableTO();
                conContableTO.setEmpCodigo(empresa);
                conContableTO.setPerCodigo(periodo);
                conContableTO.setTipCodigo(tipoCodigo);
                // ruc de ICapar
                if (sisEmpresaParametros.getEmpCodigo().getEmpRuc().compareTo("0190329313001") == 0 || sisEmpresaParametros.getEmpCodigo().getEmpRuc().compareTo("0791726867001") == 0) {
                    conContableTO.setConFecha(anxCompraTO != null ? anxCompraTO.getCompRetencionFechaEmision() : UtilsValidacion.fecha(invCompras.getCompFecha(), "yyyy-MM-dd"));
                } else {
                    conContableTO.setConFecha(UtilsValidacion.fecha(invCompras.getCompFecha(), "yyyy-MM-dd"));// "yyyy-MM-dd"
                }

                conContableTO.setConPendiente(false);
                conContableTO.setConBloqueado(false);
                conContableTO.setConAnulado(false);
                conContableTO.setConGenerado(true);
                conContableTO.setConConcepto(invCompras.getInvProveedor().getProvRazonSocial().trim());
                conContableTO.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                conContableTO.setConObservaciones(invCompras.getCompObservaciones());
                conContableTO.setConReferencia("inventario.inv_compras");
                conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                conContableTO.setUsrInsertaContable(invCompras.getUsrCodigo());
                ////// CREANDO SUCESO
                susSuceso = "INSERT";
                susTabla = "contabilidad.con_contable";
                conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                ////// CREANDO NUMERACION
                ConNumeracion conNumeracion = new ConNumeracion(new ConNumeracionPK(conContableTO.getEmpCodigo(), conContableTO.getPerCodigo(), conContableTO.getTipCodigo()));
                conNumeracion.setNumSecuencia(new Integer(invCompras.getInvComprasPK().getCompNumero()));
                ////// CREANDO CONTABLE
                if (!recontabilizar) {
                    conContable = ConversionesContabilidad.convertirConContableTO_ConContable(conContableTO);
                    conContable.setConReversado(false);
                } else {
                    conContable = contableService.obtenerPorId(empresa, periodo, tipoCodigo, conNumero);
                    conContable.setConConcepto(invCompras.getInvProveedor().getProvRazonSocial().trim());
                    conContable.setConObservaciones(invCompras.getCompObservaciones());
                    // ruc de ICapar
                    if (sisEmpresaParametros.getEmpCodigo().getEmpRuc().compareTo("0190329313001") == 0 || sisEmpresaParametros.getEmpCodigo().getEmpRuc().compareTo("0791726867001") == 0) {
                        conContable.setConFecha(anxCompraTO != null ? UtilsValidacion.fecha(anxCompraTO.getCompRetencionFechaEmision(), "yyyy-MM-dd") : invCompras.getCompFecha());
                    } else {
                        conContable.setConFecha(invCompras.getCompFecha());/// ----------
                    }
                }
                ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                List<ConDetalle> listConDetalle = new ArrayList<>();
                List<ConDetalle> listConDetalleEliminar = new ArrayList<>();
                List<ConFunContabilizarComprasDetalleTO> listaConDetalleTO = contableService.getConFunContabilizarComprasDetalle(empresa, periodo, motivo, compraNumero, null);
                for (ConFunContabilizarComprasDetalleTO conDetalleTO : listaConDetalleTO) {
                    ConDetalle conDetalle = ConversionesContabilidad.convertirConFunContabilizarCompraDetalleTO_ConDetalle(conDetalleTO);
                    conDetalle.setDetSecuencia(null);
                    listConDetalle.add(conDetalle);
                }

                susClave = conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conNumero;

                if (!recontabilizar) {
                    susDetalle = "Se Ingreso contable del periodo " + conContable.getConContablePK().getConPeriodo()
                            + ", del tipo contable " + conContable.getConContablePK().getConTipo()
                            + ", de la numeracion " + conNumero;
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    comprobar = contableService.insertarTransaccionContableCompra(conContable, listConDetalle, sisSuceso, conNumeracion, invCompras, sisInfoTO);
                } else {
                    susDetalle = "Se recontabilizó  el contable del periodo "
                            + conContable.getConContablePK().getConPeriodo() + ", del tipo contable "
                            + conContable.getConContablePK().getConTipo() + ", de la numeracion " + conNumero;
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    InvCompras invComprasAux = ConversionesInventario.convertirInvCompras_InvCompras(invCompras);
                    if (!recontabilizarSinPendiente) {
                        comprobar = comprasDao.modificarConContableCompras(conContable, listConDetalle, invComprasAux, sisSuceso);
                    } else {
                        List<ConDetalleTO> listConDetalleTOEliminar = detalleDao.getConDetalleTO(empresa, periodo, tipoCodigo, conNumero);
                        listConDetalleTOEliminar.forEach(conDetalleTO -> {
                            detalleDao.eliminarPorSql(conDetalleTO.getDetSecuencia().longValue());
                        });
                        comprobar = comprasDao.modificarConContableComprasMayorizar(conContable, listConDetalle, listConDetalleEliminar, invComprasAux, sisSuceso);
                    }
                }
                if (comprobar) {
                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(conContableTO.getEmpCodigo(), conContable.getConContablePK().getConPeriodo());
                    ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(conContableTO.getEmpCodigo(), conContable.getConContablePK().getConTipo()));
                    if (!recontabilizar) {
                        retorno = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                                + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                + "</font>.<br> Número: <font size = 5>"
                                + conContable.getConContablePK().getConNumero()
                                + (contablePendiente == true
                                        ? "</font>.<font size = 5 color= " + "red"
                                        + " ><br><b><small>Observación: PENDIENTE POR DIFERENCIA</small></b></font>"
                                        : "")
                                + ".</html>" + conTipo.getConTipoPK().getTipCodigo() + ", "
                                + conContable.getConContablePK().getConNumero() + "*  "
                                + conContable.getConContablePK().getConPeriodo();
                    } else {
                        retorno = "T<html>Se recontabilizó el contable de esta compra con la siguiente información:<br><br>"
                                + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                + "</font>.<br> Número: <font size = 5>"
                                + conContable.getConContablePK().getConNumero()
                                + (contablePendiente == true
                                        ? "</font>.<font size = 5 color= " + "red"
                                        + " ><br><b><small>Observación: PENDIENTE POR DIFERENCIA</small></b></font>"
                                        : "")
                                + ".</html>" + conTipo.getConTipoPK().getTipCodigo() + ", "
                                + conContable.getConContablePK().getConNumero() + "*  "
                                + conContable.getConContablePK().getConPeriodo();
                    }
                } else {
                    retorno = "F<html>Ocurrió un error al recontabilizar, inténtelo de nuevo...</html>";
                }
            }
        } else {
            retorno = "F<html>No se encuentra tipo de contable...</html>";
        }
        mensajeTO.setContable(conContable != null && conContable.getConContablePK() != null ? conContable.getConContablePK().getConNumero() : null);
        mensajeTO.setMensaje(retorno);
        return mensajeTO;

    }

    @Override
    public List<String> verificarStockCompras(InvCompras invCompras, List<InvComprasDetalle> listInvComprasDetalle)
            throws Exception {
        List<String> verificar = new ArrayList<>();
        List<InvComprasDetalle> listadoDetalleCompras = agrupraProductosBodegaCompra(listInvComprasDetalle);
        for (int i = 0; i < listadoDetalleCompras.size(); i++) {
            if (!listadoDetalleCompras.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("COSTO O GASTO")
                    && !listadoDetalleCompras.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("SERVICIOS")
                    && !listadoDetalleCompras.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("MERCADERIA EN TRANSITO")
                    && !listadoDetalleCompras.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("ACTIVO FIJO")
                    && !listadoDetalleCompras.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("CONSTRUCCION EN CURSO")
                    && !listadoDetalleCompras.get(i).getDetPendiente()) {

                InvProductoSaldos invProductoSaldosConsulta = productoSaldosDao.getInvProductoSaldo(
                        listadoDetalleCompras.get(i).getInvBodega().getInvBodegaPK().getBodEmpresa(),
                        listadoDetalleCompras.get(i).getInvBodega().getInvBodegaPK().getBodCodigo(),
                        listadoDetalleCompras.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal());
                if (invProductoSaldosConsulta != null) {
                    BigDecimal saldoFinal;
                    if (invCompras.getCompDocumentoTipo().compareTo("04") == 0) {
                        saldoFinal = invProductoSaldosConsulta.getStkSaldoFinal()
                                .subtract(listadoDetalleCompras.get(i).getDetCantidad());
                    } else {
                        saldoFinal = invProductoSaldosConsulta.getStkSaldoFinal()
                                .add(listadoDetalleCompras.get(i).getDetCantidad());
                    }

                    if (saldoFinal.compareTo(cero) < 0) {
                        verificar.add(listadoDetalleCompras.get(i).getInvBodega().getInvBodegaPK().getBodCodigo() + "|"
                                + listadoDetalleCompras.get(i).getInvBodega().getBodNombre() + " \t"
                                + listadoDetalleCompras.get(i).getInvProducto().getInvProductoPK()
                                        .getProCodigoPrincipal()
                                + " \t" + listadoDetalleCompras.get(i).getInvProducto().getProNombre() + " \t Cant: "
                                + UtilsValidacion.redondeoDecimalBigDecimal(
                                        listadoDetalleCompras.get(i).getDetCantidad(), 2,
                                        java.math.RoundingMode.HALF_UP)
                                + " \t Saldo: "
                                + UtilsValidacion.redondeoDecimalBigDecimal(
                                        saldoFinal.add(listadoDetalleCompras.get(i).getDetCantidad()), 2,
                                        java.math.RoundingMode.HALF_UP));
                    }
                } else {

                    verificar.add(listadoDetalleCompras.get(i).getInvBodega().getInvBodegaPK().getBodCodigo() + "|"
                            + listadoDetalleCompras.get(i).getInvBodega().getBodNombre() + " \t"
                            + listadoDetalleCompras.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                            + " \t" + listadoDetalleCompras.get(i).getInvProducto().getProNombre() + " \t Cant: "
                            + listadoDetalleCompras.get(i).getDetCantidad() + "\t Saldo: 0.00");
                }
            }

        }
        return verificar;
    }

    private List<InvComprasDetalle> agrupraProductosBodegaCompra(List<InvComprasDetalle> comprasDetalle) throws Exception {

        List<InvComprasDetalle> listaDetalleFinal = new ArrayList<>();
        listaDetalleFinal.add(ConversionesInventario.convertirInvCompraDetalle_InvCompraDetalle(comprasDetalle.get(0)));

        int contador;
        boolean encontro = false;
        for (int i = 1; i < comprasDetalle.size(); i++) {
            contador = 0;
            for (InvComprasDetalle invComprasDetalleAux : listaDetalleFinal) {
                if (comprasDetalle.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                        .equals(invComprasDetalleAux.getInvProducto().getInvProductoPK().getProCodigoPrincipal())
                        && comprasDetalle.get(i).getInvBodega().getInvBodegaPK().getBodCodigo()
                                .equals(invComprasDetalleAux.getInvBodega().getInvBodegaPK().getBodCodigo())) {
                    encontro = true;
                    break;
                } else {
                    encontro = false;
                    contador++;
                }
            }
            if (encontro) {
                listaDetalleFinal.get(contador).setDetCantidad(
                        listaDetalleFinal.get(contador).getDetCantidad().add(comprasDetalle.get(i).getDetCantidad()));
                listaDetalleFinal.get(contador).setDetPrecio(comprasDetalle.get(i).getDetPrecio());
            } else {
                listaDetalleFinal
                        .add(ConversionesInventario.convertirInvCompraDetalle_InvCompraDetalle(comprasDetalle.get(i)));
            }
        }
        return listaDetalleFinal;
    }

    @Override
    public MensajeTO insertarInvComprasTO(
            InvComprasTO invComprasTO,
            List<InvComprasDetalleTO> listaInvComprasDetalleTO,
            AnxCompraTO anxCompraTO,
            List<AnxCompraDetalleTO> anxCompraDetalleTO,
            List<AnxCompraReembolsoTO> anxCompraReembolsoTO,
            List<AnxCompraFormaPagoTO> anxCompraFormaPagoTO,
            List<InvAdjuntosCompras> listImagen,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno;
        boolean periodoCerrado = false;
        List<String> mensajeClase = new ArrayList<>();
        MensajeTO mensajeTO = new MensajeTO();
        // ojo -
        if (!UtilsValidacion.isFechaSuperior(invComprasTO.getCompFecha(), "yyyy-MM-dd")) {
            comprobar = false;

            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invComprasTO.getEmpCodigo());
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(invComprasTO.getCompFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(invComprasTO.getCompFecha(), "yyyy-MM-dd")
                                .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                                .getTime()) {
                    comprobar = true;
                    invComprasTO.setCompPeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (comprobar) {
                if (!periodoCerrado) {
                    boolean continuar = false;
                    if (anxCompraTO != null) {
                        if (anxCompraTO.getCompRetencionNumero() != null) {
                            AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(
                                    invComprasTO.getEmpCodigo(), anxCompraTO.getCompRetencionNumero(), "07",
                                    ("'" + anxCompraTO.getCompRetencionFechaEmision() + "'"));
                            if (!anxNumeracionLineaTO.getNumeroAutorizacion().trim().equals("ANULADO")) {
                                continuar = true;
                            }

                        } else {
                            continuar = true;
                        }
                    } else {
                        continuar = true;
                    }
                    if (continuar) {
                        if (comprasMotivoDao.comprobarInvComprasMotivo(invComprasTO.getEmpCodigo(),
                                invComprasTO.getCompMotivo())) {
                            /// PREPARANDO OBJETO SISSUCESO (susClave y susDetalle se llenan en DAOTransaccion)
                            susSuceso = "INSERT";
                            susTabla = "inventario.inv_compra";
                            invComprasTO.setUsrFechaInsertaCompra(UtilsValidacion.fechaSistema());
                            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                    sisInfoTO);
                            ///// BUSCANDO PROVEEDOR
                            InvProveedor invProveedor = proveedorDao.buscarInvProveedor(invComprasTO.getEmpCodigo(),
                                    invComprasTO.getProvCodigo());
                            if (invProveedor != null) {
                                ////// CREANDO COMPRAS
                                invComprasTO.setCompImportacion(false);
                                InvCompras invCompras = ConversionesInventario.convertirInvComprasTO_InvCompras(invComprasTO);
                                if (invCompras.getConNumero() == null || invCompras.getConPeriodo() == null || invCompras.getConNumero() == null) {
                                    invCompras.setConEmpresa(null);
                                    invCompras.setConNumero(null);
                                    invCompras.setConPeriodo(null);
                                    invCompras.setConTipo(null);
                                }
                                invCompras.setInvProveedor(proveedorDao.buscarInvProveedor(invComprasTO.getEmpCodigo(), invComprasTO.getProvCodigo()));
                                ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                                List<InvComprasDetalle> listInvComprasDetalle = new ArrayList<InvComprasDetalle>();
                                InvComprasDetalle invComprasDetalle = null;
                                int estadoDetalle = 0;
                                String estadoDetalleComplemento = "";
                                for (InvComprasDetalleTO invComprasDetalleTO : listaInvComprasDetalleTO) {
                                    invComprasDetalleTO.setDetPendiente(invCompras.getCompPendiente());
                                    invComprasDetalleTO.setDetConfirmado(true);

                                    invComprasDetalle = ConversionesInventario.convertirInvComprasDetalleTO_InvComprasDetalle(invComprasDetalleTO);
                                    ///// BUSCANDO EL PRODUCTO EN DETALLE
                                    InvProducto invProducto = productoDao.buscarInvProducto(invComprasDetalleTO.getProEmpresa(), invComprasDetalleTO.getProCodigoPrincipal());
                                    if (invProducto != null) {
                                        invComprasDetalle.setInvProducto(invProducto);
                                        invComprasDetalle.setProCreditoTributario(invProducto.getProCreditoTributario());
                                        ////// BUSCANDO LA BODEGA EN EL DETALLE
                                        InvBodega invBodega = bodegaDao.buscarInvBodega(invComprasDetalleTO.getProEmpresa(), invComprasDetalleTO.getBodCodigo());
                                        if (invBodega != null) {
                                            invComprasDetalle.setInvBodega(invBodega);
                                            listInvComprasDetalle.add(invComprasDetalle);
                                        } else {
                                            estadoDetalle = 2;
                                        }
                                    } else {
                                        estadoDetalle = 1;
                                    }
                                    if (invComprasDetalle.getInvBodega().getBodProductoPermitido() != null
                                            && !invComprasDetalle.getInvBodega().getBodProductoPermitido().equalsIgnoreCase(
                                                    invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                        estadoDetalle = 3;
                                        estadoDetalleComplemento = "F<html>El producto '" + invComprasDetalle.getInvProducto().getProNombre().trim() + "' no puede ingresar a la bodega '"
                                                + invComprasDetalle.getInvBodega().getInvBodegaPK().getBodCodigo()
                                                + "'; esta bodega NO permite productos del tipo '" + invComprasDetalle.getInvProducto().getInvProductoTipo().getTipDetalle() + "'</html>";
                                    }
                                    if (invComprasDetalle.getInvBodega().getBodProductoNoPermitido() != null
                                            && invComprasDetalle.getInvBodega().getBodProductoNoPermitido().equalsIgnoreCase(
                                                    invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                        estadoDetalle = 3;
                                        estadoDetalleComplemento = "F<html>El producto '" + invComprasDetalle.getInvProducto().getProNombre().trim() + "' no puede ingresar a la bodega '"
                                                + invComprasDetalle.getInvBodega().getInvBodegaPK().getBodCodigo()
                                                + "'; esta bodega NO permite productos del tipo '" + invComprasDetalle.getInvProducto().getInvProductoTipo().getTipDetalle() + "'</html>";
                                    }
                                    if (estadoDetalle == 1 || estadoDetalle == 2 || estadoDetalle == 3) {
                                        break;
                                    }
                                }

                                boolean existeConcepto = false;
                                if (estadoDetalle == 0) {
                                    AnxCompra anxCompra = null;
                                    AnxCompraDetalle anxCompraDetalle = null;
                                    AnxCompraDividendo anxCompraDividendo = null;
                                    AnxCompraFormaPago anxCompraFormaPago = null;
                                    AnxCompraReembolso anxCompraReembolso = null;
                                    List<AnxCompraDetalle> anxCompraDetalles = new ArrayList<AnxCompraDetalle>();
                                    List<AnxCompraDividendo> anxCompraDividendos = new ArrayList<AnxCompraDividendo>();
                                    List<AnxCompraReembolso> anxCompraReembolsos = new ArrayList<AnxCompraReembolso>();
                                    List<AnxCompraFormaPago> anxCompraFormaPagos = new ArrayList<AnxCompraFormaPago>();

                                    boolean esAgente = false;
                                    SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorIdEvict(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
                                    if (parametros != null && parametros.getParAgenteRetencion() != null && !parametros.getParAgenteRetencion().isEmpty()) {
                                        esAgente = true;
                                    }

                                    if (invComprasTO.getCompDocumentoTipo().equals("00") || (invComprasTO.getCompDocumentoTipo().equals("04") && anxCompraTO == null) || invComprasTO.getCompDocumentoTipo().equals("99")) {
                                        existeConcepto = true;
                                    } else {
                                        for (int i = 0; i < anxCompraFormaPagoTO.size(); i++) {
                                            AnxFormaPago anxFormaPago = formaPagoAnexoService.getAnexoFormaPago(anxCompraFormaPagoTO.get(i).getFpCodigo());
                                            if (anxFormaPago != null) {
                                                anxCompraFormaPago = ConversionesAnexos.convertirAnxCompraFormaPagoTO_AnxCompraFormaPago(anxCompraFormaPagoTO.get(i));
                                                anxCompraFormaPago.setAnxCompra(anxCompra);
                                                anxCompraFormaPago.setFpCodigo(anxFormaPago);
                                                existeConcepto = true;
                                            } else {
                                                existeConcepto = false;
                                            }
                                            if (!existeConcepto) {
                                                i = anxCompraFormaPagoTO.size();
                                            } else {
                                                anxCompraFormaPagos.add(anxCompraFormaPago);
                                            }
                                        }
                                        if (!esAgente && anxCompraDetalleTO.isEmpty()) {
                                            existeConcepto = true;
                                        }
                                        if (existeConcepto) {
                                            anxCompra = ConversionesAnexos.convertirAnxCompraTO_AnxCompra(anxCompraTO);
                                            if (!invComprasTO.getCompDocumentoTipo().equals("04") && !invComprasTO.getCompDocumentoTipo().equals("05")) {
                                                anxCompra.setCompModificadoDocumentoEmpresa(null);
                                                anxCompra.setCompModificadoDocumentoTipo(null);
                                                anxCompra.setCompModificadoDocumentoNumero(null);
                                                anxCompra.setCompModificadoAutorizacion(null);
                                            }
                                            ///
                                            for (int i = 0; i < anxCompraDetalleTO.size(); i++) {
                                                AnxConcepto anxConcepto = conceptoDao.obtenerAnexo(anxCompraTO.getCompRetencionFechaEmision(), anxCompraDetalleTO.get(i).getDetConcepto());
                                                if (anxConcepto != null) {
                                                    anxCompraDividendo = null;
                                                    anxCompraDetalle = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDetalle(anxCompraDetalleTO.get(i));
                                                    anxCompraDetalle.setAnxCompra(anxCompra);
                                                    anxCompraDetalle.setDetConcepto(anxConcepto.getConCodigo());
                                                    if (anxCompraDetalleTO.get(i).getDivIrAsociado() != null && anxCompraDetalleTO.get(i).getDivIrAsociado().compareTo(cero) > 0) {
                                                        anxCompraDividendo = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDividendo(anxCompraDetalleTO.get(i));
                                                        anxCompraDividendo.setAnxCompra(anxCompra);
                                                        anxCompraDividendo.setConCodigo(anxConcepto.getConCodigo());
                                                    }
                                                    existeConcepto = true;
                                                } else {
                                                    existeConcepto = false;
                                                }
                                                if (!existeConcepto) {
                                                    i = anxCompraDetalleTO.size();
                                                } else {
                                                    anxCompraDetalles.add(anxCompraDetalle);
                                                    if (anxCompraDividendo != null) {
                                                        anxCompraDividendos.add(anxCompraDividendo);
                                                    }
                                                }
                                            }
                                            if (invComprasTO.getCompDocumentoTipo().equals("41")) {
                                                for (AnxCompraReembolsoTO o : anxCompraReembolsoTO) {
                                                    anxCompraReembolso = ConversionesAnexos.convertirAnxCompraReembolsoTO_AnxCompraReembolso(o);
                                                    anxCompraReembolsos.add(anxCompraReembolso);
                                                }
                                            }

                                        } else {
                                            existeConcepto = false;
                                        }
                                    }
                                    if (existeConcepto) {
                                        //////////// COMPROBAR SI NO EXISTE NUMERO DE FACTURA
                                        String codigoFactura = comprasDao.getConteoNumeroFacturaCompra(
                                                invComprasTO.getEmpCodigo(), invComprasTO.getProvCodigo(),
                                                invComprasTO.getCompDocumentoTipo(),
                                                invComprasTO.getCompDocumentoNumero());

                                        if (codigoFactura.trim().isEmpty()
                                                || invComprasTO.getCompDocumentoNumero().equals("999-999-999999999")
                                                || invComprasTO.getCompDocumentoTipo().equals("00")
                                                && invComprasTO.getCompDocumentoTipo().equals("99")) {
                                            boolean noExiste = false;
                                            //////////// COMPROBAR SI EL TIPO DE DOCUMENTO ES 00
                                            boolean continua = true;
                                            if (!invComprasTO.getCompDocumentoTipo().equals("00")
                                                    && !invComprasTO.getCompDocumentoTipo().equals("99")) {
                                                if (invComprasTO.getCompDocumentoTipo().equals("04")) {
                                                    if (anxCompraTO == null) {
                                                        continua = false;
                                                        noExiste = true;
                                                    }
                                                }
                                                if (continua) {
                                                    //////////// COMPROBAR SI NO EXISTE NUMERO DE RETENCION
                                                    String codigoRetencion = null;
                                                    anxCompraTO.setCompRetencionNumero(
                                                            anxCompraTO.getCompRetencionNumero() == null ? ""
                                                            : anxCompraTO.getCompRetencionNumero());
                                                    if (!anxCompraTO.getCompRetencionNumero().isEmpty()) {
                                                        codigoRetencion = compraDao.getConteoNumeroRetencion(
                                                                anxCompraTO.getEmpCodigo(),
                                                                anxCompraTO.getCompRetencionNumero());
                                                    } else {
                                                        codigoRetencion = null;
                                                    }
                                                    if (codigoRetencion == null) {
                                                        noExiste = true;
                                                    }
                                                }
                                            } else {
                                                noExiste = true;
                                            }
                                            if (noExiste) {

                                                if (!invCompras.getCompAnulado() && !invCompras.getCompPendiente() && invCompras.getCompDocumentoTipo().compareTo("04") == 0) {
                                                    for (int i = 0; i < listInvComprasDetalle.size(); i++) {
                                                        listInvComprasDetalle.get(i).setDetPendiente(invCompras.getCompPendiente());
                                                    }
                                                    mensajeClase = verificarStockCompras(invCompras, listInvComprasDetalle);
                                                }

                                                if (mensajeClase.isEmpty()) {
                                                    if (invCompras.getCompDocumentoTipo().equals("00") || invCompras.getCompDocumentoTipo().equals("19")) {
                                                        if (invCompras.getCompDocumentoNumero() != null && invCompras.getCompDocumentoNumero().equals("999-999-999999999")) {
                                                            invCompras.setCompDocumentoNumero(null);
                                                        }

                                                    }
                                                    comprobar = comprasDao.insertarTransaccionInvCompra(invCompras,
                                                            listInvComprasDetalle, sisSuceso, anxCompra,
                                                            anxCompraDetalles, anxCompraDividendos,
                                                            anxCompraReembolsos, anxCompraFormaPagos, listImagen);
                                                    if (comprobar) {
                                                        SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                                                invComprasTO.getEmpCodigo(),
                                                                invCompras.getInvComprasPK().getCompPeriodo());
                                                        retorno = "T<html>La compra se guardo correctamente con la siguiente información:<br><br>"
                                                                + "Periodo: <font size = 5>"
                                                                + sisPeriodo.getPerDetalle()
                                                                + "</font>.<br> Motivo: <font size = 5>"
                                                                + invCompras.getInvComprasPK().getCompMotivo()
                                                                + "</font>.<br> Número: <font size = 5>"
                                                                + invCompras.getInvComprasPK().getCompNumero()
                                                                + "</font>.</html>"
                                                                + invCompras.getInvComprasPK().getCompNumero() + ","
                                                                + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                                    } else {
                                                        retorno = "F<html>Hubo un error al guardar la Compra...\nIntente de nuevo o contacte con el administrador</html>";
                                                    }
                                                } else {
                                                    retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                                                    mensajeTO.setListaErrores1(mensajeClase);
                                                }
                                            } else {
                                                retorno = "F<html>El Número de Retención que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                            }
                                        } else {
                                            retorno = "F<html>El Número de Documento que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                        }
                                    } else {
                                        retorno = "F<html>El concepto de retención que ha escogido no se encuentra disponible.<br>Contacte con el administrador.</html>";
                                    }
                                } else if (estadoDetalle == 1) {
                                    retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                } else if (estadoDetalle == 2) {
                                    retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                } else {
                                    retorno = estadoDetalleComplemento;
                                }
                            } else {
                                retorno = "F<html>El proveedor que escogió ya no está disponible...\nIntente de nuevo, escoja otro Proveedor o contacte con el administrador</html>";
                            }
                        } else {
                            retorno = "F<html>No se encuentra el motivo...</html>";
                        }
                    } else {
                        retorno = "F<html>El NÚMERO DE RETENCIÓN que ingresó se encuentra ANULADO</html>";
                    }
                } else {
                    retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                }
            } else {
                retorno = "F <html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public void quitarPendiente(InvComprasTO invComprasTO) throws Exception {
        InvCompras invCompras = ConversionesInventario.convertirInvComprasTO_InvCompras(invComprasTO);
        comprasDao.actualizarPendientePorSql(invCompras);
    }

    @Override
    public MensajeTO modificarInvComprasTO(InvComprasTO invComprasTO,
            List<InvComprasDetalleTO> listaInvComprasDetalleTO,
            List<InvComprasDetalleTO> listaInvComprasEliminarDetalleTO,
            AnxCompraTO anxCompraTO,
            List<AnxCompraDetalleTO> anxCompraDetalleTO,
            List<AnxCompraDetalleTO> anxCompraDetalleEliminarTO,
            List<AnxCompraReembolsoTO> anxCompraReembolsoTO,
            List<AnxCompraReembolsoTO> anxCompraReembolsoEliminarTO,
            List<AnxCompraFormaPagoTO> anxCompraFormaPagoTO,
            List<AnxCompraFormaPagoTO> anxCompraFormaPagoEliminarTO,
            boolean desmayorizar,
            boolean quitarMotivoAnulacion,
            InvComprasMotivoAnulacion invComprasMotivoAnulacion,
            List<InvAdjuntosCompras> listImagen,
            SisInfoTO sisInfoTO) throws Exception {

        boolean eliminarMotivoAnulacion = false, periodoCerrado = false;
        String retorno;

        if (invComprasTO.getFpSecuencial() != null && invComprasTO.getFpSecuencial() == 0) {
            invComprasTO.setFpSecuencial(null);
        }
        MensajeTO mensajeTO = new MensajeTO();
        if (!UtilsValidacion.isFechaSuperior(invComprasTO.getCompFecha(), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invComprasTO.getEmpCodigo());

            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(invComprasTO.getCompFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(invComprasTO.getCompFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    comprobar = true;
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }
            if (comprobar) {
                if (!periodoCerrado) {
                    List<String> mensajeClase = new ArrayList<>();
                    boolean continuar = false;
                    if (anxCompraTO != null && (!invComprasTO.getCompAnulado() || !invComprasTO.getCompPendiente())) {
                        if (anxCompraTO.getCompRetencionNumero() != null) {
                            if (anxCompraTO.getRetElectronica()) {
                                continuar = true;
                            } else {
                                AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(
                                        invComprasTO.getEmpCodigo(),
                                        anxCompraTO.getCompRetencionNumero() == null ? ""
                                        : anxCompraTO.getCompRetencionNumero(),
                                        "07", ("'" + anxCompraTO.getCompRetencionFechaEmision() + "'"));
                                if (anxNumeracionLineaTO != null && !anxNumeracionLineaTO.getNumeroAutorizacion().trim().equals("ANULADO")) {
                                    continuar = true;
                                }
                            }
                        } else {
                            continuar = true;
                        }
                    } else {
                        continuar = true;
                    }

                    if (continuar) {
                        if (comprasMotivoDao.comprobarInvComprasMotivo(invComprasTO.getEmpCodigo(), invComprasTO.getCompMotivo())) {
                            InvCompras invComprasCreadas = comprasDao.buscarInvCompras(invComprasTO.getEmpCodigo(), invComprasTO.getCompPeriodo(), invComprasTO.getCompMotivo(), invComprasTO.getCompNumero());
                            boolean validacionModificar = (invComprasCreadas != null && !quitarMotivoAnulacion);
                            boolean validacionRestaurar = (invComprasCreadas != null && invComprasCreadas.getCompAnulado() && quitarMotivoAnulacion);
                            if (validacionModificar || validacionRestaurar) {

                                List<String> lisCheques = contableService.validarChequesConciliados(sisInfoTO.getEmpresa(), invComprasTO.getContPeriodo(), invComprasTO.getContTipo(), invComprasTO.getContNumero());
                                if (lisCheques != null && !lisCheques.isEmpty()) {
                                    mensajeTO.setMensaje("FNo se puede Modificar un contable que contiene un cheque conciliado:");
                                    mensajeTO.setListaErrores1(lisCheques);
                                    return mensajeTO;
                                }

                                //// CREANDO SUCESO
                                susClave = invComprasTO.getCompPeriodo() + " " + invComprasTO.getCompMotivo() + " " + invComprasTO.getCompNumero();
                                susTabla = "inventario.inv_compra";
                                String estado;
                                if (invComprasTO.getCompAnulado()) {
                                    estado = "anuló";
                                } else {
                                    estado = "modificó";
                                }

                                if (quitarMotivoAnulacion) {
                                    eliminarMotivoAnulacion = true;
                                    InvComprasMotivoAnulacion invComprasMotivoAnulacionTmp = comprasMotivoAnuladoDao.buscarCompraMotivo(invComprasTO.getEmpCodigo(), invComprasTO.getCompPeriodo(), invComprasTO.getCompMotivo(), invComprasTO.getCompNumero());
                                    invComprasMotivoAnulacion = new InvComprasMotivoAnulacion();
                                    invComprasMotivoAnulacion.setAnuComentario(invComprasMotivoAnulacionTmp.getAnuComentario());
                                    invComprasMotivoAnulacion.setInvCompras(invComprasMotivoAnulacionTmp.getInvCompras());
                                    invComprasMotivoAnulacion.setAnuSecuencial(invComprasMotivoAnulacionTmp.getAnuSecuencial());

                                    estado = "restauró";
                                    susDetalle = "Se " + estado + " la compra en el periodo " + invComprasTO.getCompPeriodo() + ", del motivo " + invComprasTO.getCompMotivo() + ", de la numeración " + invComprasTO.getCompNumero();
                                    susSuceso = "RESTORE";
                                    invComprasTO.setCompDocumentoFormaPago(invComprasTO.getCompDocumentoFormaPago() == null || invComprasTO.getCompDocumentoFormaPago().equals("") ? invComprasCreadas.getCompDocumentoFormaPago() : invComprasTO.getCompDocumentoFormaPago());
                                    invComprasTO.setUsrInsertaCompra(invComprasCreadas.getUsrCodigo());
                                    invComprasTO.setUsrFechaInsertaCompra(UtilsValidacion.fecha(invComprasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                } else {
                                    susDetalle = "";
                                    if (estado.trim().equals("anuló")) {
                                        susDetalle = "Compra número " + invComprasTO.getCompNumero() + "se anuló por " + invComprasMotivoAnulacion.getAnuComentario();
                                    } else {
                                        susDetalle = "Se " + estado + " la compra en el periodo " + invComprasTO.getCompPeriodo() + ", del motivo " + invComprasTO.getCompMotivo() + ", de la numeración " + invComprasTO.getCompNumero();
                                    }
                                    susSuceso = "UPDATE";
                                    invComprasTO.setCompDocumentoFormaPago(invComprasTO.getCompDocumentoFormaPago() == null || invComprasTO.getCompDocumentoFormaPago().equals("") ? invComprasCreadas.getCompDocumentoFormaPago() : invComprasTO.getCompDocumentoFormaPago());
                                    invComprasTO.setUsrInsertaCompra(invComprasCreadas.getUsrCodigo());
                                    invComprasTO.setUsrFechaInsertaCompra(UtilsValidacion.fecha(invComprasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                }
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                // CREANDO COMPRAS
                                InvCompras invCompras = ConversionesInventario.convertirInvComprasTO_InvCompras(invComprasTO);
                                if (invCompras.getConNumero() == null || invCompras.getConPeriodo() == null || invCompras.getConNumero() == null) {
                                    invCompras.setConEmpresa(null);
                                    invCompras.setConNumero(null);
                                    invCompras.setConPeriodo(null);
                                    invCompras.setConTipo(null);
                                }
                                // BUSCANDO PROVEEDOR
                                InvProveedor invProveedor = proveedorDao.buscarInvProveedor(invComprasTO.getEmpCodigo(), invComprasTO.getProvCodigo());
                                invCompras.setInvProveedor(invProveedor);

                                String detalleError = "";
                                if (invProveedor != null) {

                                    if ((!desmayorizar || quitarMotivoAnulacion) && listaInvComprasDetalleTO == null) {
                                        List<InvListaDetalleComprasTO> invListaDetalleTO = comprasDetalleDao.getListaInvCompraDetalleTO(invComprasTO.getEmpCodigo(), invComprasTO.getCompPeriodo(), invComprasTO.getCompMotivo(),
                                                invComprasTO.getCompNumero());
                                        if (invListaDetalleTO != null) {
                                            listaInvComprasDetalleTO = new ArrayList<>();
                                            for (int i = 0; i < invListaDetalleTO.size(); i++) {
                                                InvComprasDetalleTO invComprasDetalleTO = new InvComprasDetalleTO();
                                                invComprasDetalleTO.setDetSecuencia(invListaDetalleTO.get(i).getSecuencial());
                                                //invComprasDetalleTO.setDetSecuencialOrdenCompra(invListaDetalleTO.get(i).getCdSecuencialOrdenCompra());
                                                invComprasDetalleTO.setDetOrden(i + 1);
                                                invComprasDetalleTO.setDetPendiente(invListaDetalleTO.get(i).getPendiente());
                                                invComprasDetalleTO.setDetCantidad(invListaDetalleTO.get(i).getCantidadProducto());
                                                invComprasDetalleTO.setDetPrecio(invListaDetalleTO.get(i).getPrecioProducto());
                                                invComprasDetalleTO.setDetPorcentajeDescuento(invListaDetalleTO.get(i).getPorcentajeDescuento());
                                                invComprasDetalleTO.setDetConfirmado(true);
                                                invComprasDetalleTO.setBodEmpresa(invComprasTO.getEmpCodigo());
                                                invComprasDetalleTO.setBodCodigo(invListaDetalleTO.get(i).getCodigoBodega());
                                                invComprasDetalleTO.setProEmpresa(invComprasTO.getEmpCodigo());
                                                invComprasDetalleTO.setProCodigoPrincipal(invListaDetalleTO.get(i).getCodigoProducto());
                                                invComprasDetalleTO.setSecEmpresa(invComprasTO.getEmpCodigo());
                                                invComprasDetalleTO.setSecCodigo(invListaDetalleTO.get(i).getCodigoCP());
                                                invComprasDetalleTO.setPisEmpresa(invComprasTO.getEmpCodigo());
                                                invComprasDetalleTO.setPisSector(invListaDetalleTO.get(i).getCodigoCP());
                                                invComprasDetalleTO.setPisNumero(invListaDetalleTO.get(i).getCodigoCC());
                                                invComprasDetalleTO.setComEmpresa(invComprasTO.getEmpCodigo());
                                                invComprasDetalleTO.setComPeriodo(invComprasTO.getCompPeriodo());
                                                invComprasDetalleTO.setComMotivo(invComprasTO.getCompMotivo());
                                                invComprasDetalleTO.setComNumero(invComprasTO.getCompNumero());
                                                listaInvComprasDetalleTO.add(invComprasDetalleTO);
                                            }
                                        } else {
                                            detalleError = "Hubo en error al recuperar el detalle de la compra.\nContacte con el administrador...";
                                        }
                                    }
                                    if (detalleError.trim().isEmpty()) {
                                        // CONVIRTIENDO A ENTIDAD EL DETALLE
                                        List<InvComprasDetalle> listInvComprasDetalle = new ArrayList<>();
                                        int estadoDetalle = 0;
                                        String estadoDetalleComplemento = "";
                                        if (!desmayorizar && !invCompras.getCompAnulado()) {
                                            for (InvComprasDetalleTO invComprasDetalleTO : listaInvComprasDetalleTO) {
                                                invComprasDetalleTO.setDetConfirmado(true);
                                                InvComprasDetalle invComprasDetalle = ConversionesInventario.convertirInvComprasDetalleTO_InvComprasDetalle(invComprasDetalleTO);
                                                invComprasDetalle.setInvCompras(invCompras);
                                                // BUSCANDO EL PRODUCTO
                                                InvProducto invProducto = productoDao.buscarInvProducto(invComprasDetalleTO.getComEmpresa(), invComprasDetalleTO.getProCodigoPrincipal());
                                                if (invProducto != null) {
                                                    invComprasDetalle.setInvProducto(invProducto);
                                                    invComprasDetalle.setProCreditoTributario(invProducto.getProCreditoTributario());
                                                    // BUSCANDO LA BODEGA
                                                    InvBodega invBodega = bodegaDao.buscarInvBodega(invComprasDetalleTO.getComEmpresa(), invComprasDetalleTO.getBodCodigo());
                                                    if (invBodega != null) {
                                                        invComprasDetalle.setInvBodega(invBodega);
                                                        listInvComprasDetalle.add(invComprasDetalle);
                                                    } else {
                                                        estadoDetalle = 2;
                                                    }
                                                } else {
                                                    estadoDetalle = 1;
                                                }
                                                if (invComprasDetalle.getInvBodega().getBodProductoPermitido() != null && !invComprasDetalle.getInvBodega().getBodProductoPermitido().equalsIgnoreCase(
                                                        invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                                    estadoDetalle = 3;
                                                    estadoDetalleComplemento = "F<html>El producto '" + invComprasDetalle.getInvProducto().getProNombre().trim() + "' no puede ingresar a la bodega '"
                                                            + invComprasDetalle.getInvBodega().getInvBodegaPK().getBodCodigo()
                                                            + "'; esta bodega NO permite productos del tipo '" + invComprasDetalle.getInvProducto().getInvProductoTipo().getTipDetalle() + "'</html>";
                                                };
                                                if (invComprasDetalle.getInvBodega().getBodProductoNoPermitido() != null && invComprasDetalle.getInvBodega().getBodProductoNoPermitido().equalsIgnoreCase(
                                                        invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                                    estadoDetalle = 3;
                                                    estadoDetalleComplemento = "F<html>El producto '" + invComprasDetalle.getInvProducto().getProNombre().trim() + "' no puede ingresar a la bodega '"
                                                            + invComprasDetalle.getInvBodega().getInvBodegaPK().getBodCodigo()
                                                            + "'; esta bodega NO permite productos del tipo '" + invComprasDetalle.getInvProducto().getInvProductoTipo().getTipDetalle() + "'</html>";
                                                };
                                                if (estadoDetalle == 1 || estadoDetalle == 2 || estadoDetalle == 3) {
                                                    break;
                                                }
                                            }
                                        }
                                        if (estadoDetalle == 0) {
                                            // CONVIRTIENDO A ENTIDAD EL DETALLE A ELIMINAR
                                            List<InvComprasDetalle> listInvComprasDetalleEliminar = new ArrayList<>();
                                            int estadoDetalleEliminar = 0;
                                            if (listaInvComprasEliminarDetalleTO != null) {
                                                for (InvComprasDetalleTO invComprasDetalleTO : listaInvComprasEliminarDetalleTO) {
                                                    invComprasDetalleTO.setDetConfirmado(true);
                                                    InvComprasDetalle invComprasDetalleEliminar = ConversionesInventario.convertirInvComprasDetalleTO_InvComprasDetalle(invComprasDetalleTO);
                                                    invComprasDetalleEliminar.setInvCompras(invCompras);
                                                    // BUSCANDO EL PRODUCTO
                                                    InvProducto invProducto = productoDao.buscarInvProducto(invComprasDetalleTO.getComEmpresa(), invComprasDetalleTO.getProCodigoPrincipal());
                                                    if (invProducto != null) {
                                                        invComprasDetalleEliminar.setInvProducto(invProducto);
                                                        // BUSCANDO LA BODEGA
                                                        InvBodega invBodega = bodegaDao.buscarInvBodega(invComprasDetalleTO.getComEmpresa(), invComprasDetalleTO.getBodCodigo());
                                                        if (invBodega != null) {
                                                            invComprasDetalleEliminar.setInvBodega(invBodega);
                                                            listInvComprasDetalleEliminar.add(invComprasDetalleEliminar);
                                                        } else {
                                                            estadoDetalleEliminar = 2;
                                                        }
                                                    } else {
                                                        estadoDetalleEliminar = 1;
                                                    }
                                                    if (estadoDetalleEliminar == 1 || estadoDetalleEliminar == 2) {
                                                        break;
                                                    }
                                                }
                                            }

                                            if (estadoDetalleEliminar == 0) {
                                                boolean existeConcepto = false;
                                                String nombreConcepto = "";
                                                AnxCompra anxCompra = null;
                                                AnxCompraDetalle anxCompraDetalle = null;
                                                AnxCompraDetalle anxCompraDetalleEliminar = null;
                                                AnxCompraDividendo anxCompraDividendo = null;
                                                AnxCompraReembolso anxCompraReembolso;
                                                AnxCompraReembolso anxCompraReembolsoEliminar;
                                                AnxCompraFormaPago anxCompraFormaPago = null;
                                                AnxCompraFormaPago anxCompraFormaPagoEliminar = null;
                                                List<AnxCompraDetalle> anxCompraDetalles = new ArrayList<>();
                                                List<AnxCompraDetalle> anxCompraDetallesEliminar = new ArrayList<>();
                                                List<AnxCompraDividendo> anxCompraDividendos = new ArrayList<>();

                                                List<AnxCompraReembolso> anxCompraReembolsos = new ArrayList<>();
                                                List<AnxCompraReembolso> anxCompraReembolsosEliminar = new ArrayList<>();

                                                List<AnxCompraFormaPago> anxCompraFormaPagos = new ArrayList<>();
                                                List<AnxCompraFormaPago> anxCompraFormaPagosEliminar = new ArrayList<>();
                                                int estadoError = 0;

                                                if (!invComprasTO.getCompDocumentoTipo().equals("00") && !invComprasTO.getCompDocumentoTipo().equals("99")) {
                                                    if (anxCompraTO != null) {
                                                        anxCompra = ConversionesAnexos.convertirAnxCompraTO_AnxCompra(anxCompraTO);
                                                        if (!invComprasTO.getCompDocumentoTipo().equals("04") && !invComprasTO.getCompDocumentoTipo().equals("05")) {
                                                            anxCompra.setCompModificadoDocumentoEmpresa(null);
                                                            anxCompra.setCompModificadoDocumentoTipo(null);
                                                            anxCompra.setCompModificadoDocumentoNumero(null);
                                                            anxCompra.setCompModificadoAutorizacion(null);
                                                        }
                                                        // anexo_Forma_Pago
                                                        for (int i = 0; i < anxCompraFormaPagoTO.size(); i++) {
                                                            AnxFormaPago anxFormaPago = formaPagoAnexoService.getAnexoFormaPago(anxCompraFormaPagoTO.get(i).getFpCodigo());
                                                            if (anxFormaPago != null) {
                                                                anxCompraFormaPago = ConversionesAnexos.convertirAnxCompraFormaPagoTO_AnxCompraFormaPago(anxCompraFormaPagoTO.get(i));
                                                                anxCompraFormaPago.setAnxCompra(anxCompra);
                                                                anxCompraFormaPago.setFpCodigo(anxFormaPago);
                                                                existeConcepto = true;
                                                            } else {
                                                                existeConcepto = false;
                                                                estadoError = 2;
                                                            }
                                                            if (!existeConcepto) {
                                                                i = anxCompraFormaPagoTO.size();
                                                            } else {
                                                                anxCompraFormaPagos.add(anxCompraFormaPago);
                                                            }
                                                        }
                                                        if (anxCompraFormaPagoEliminarTO != null && !anxCompraFormaPagoEliminarTO.isEmpty()) {
                                                            for (int i = 0; i < anxCompraFormaPagoEliminarTO.size(); i++) {
                                                                AnxFormaPago anxFormaPago = formaPagoAnexoService.getAnexoFormaPago(anxCompraFormaPagoEliminarTO.get(i).getFpCodigo());
                                                                if (anxFormaPago != null) {
                                                                    anxCompraFormaPagoEliminar = ConversionesAnexos.convertirAnxCompraFormaPagoTO_AnxCompraFormaPago(anxCompraFormaPagoEliminarTO.get(i));
                                                                    anxCompraFormaPagoEliminar.setAnxCompra(anxCompra);
                                                                    anxCompraFormaPagoEliminar.setFpCodigo(anxFormaPago);
                                                                    existeConcepto = true;
                                                                } else {
                                                                    existeConcepto = false;
                                                                    estadoError = 2;
                                                                }
                                                                if (!existeConcepto) {
                                                                    i = anxCompraFormaPagoEliminarTO.size();
                                                                } else {
                                                                    anxCompraFormaPagosEliminar.add(anxCompraFormaPagoEliminar);
                                                                }
                                                            }
                                                        }
                                                        // Anexo_detalle_concepto
                                                        if (anxCompraDetalleTO != null) {
                                                            for (int i = 0; i < anxCompraDetalleTO.size(); i++) {
                                                                AnxConcepto anxConcepto = conceptoDao.obtenerAnexo(anxCompraTO.getCompRetencionFechaEmision(), anxCompraDetalleTO.get(i).getDetConcepto());
                                                                if (anxConcepto != null) {
                                                                    anxCompraDividendo = null;
                                                                    anxCompraDetalle = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDetalle(anxCompraDetalleTO.get(i));
                                                                    anxCompraDetalle.setAnxCompra(anxCompra);
                                                                    anxCompraDetalle.setDetConcepto(anxConcepto.getConCodigo());
                                                                    if ((anxCompraDetalleTO.get(i).getDivIrAsociado() != null) && (anxCompraDetalleTO.get(i).getDivIrAsociado().compareTo(cero) > 0)) {
                                                                        anxCompraDividendo = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDividendo(anxCompraDetalleTO.get(i));
                                                                        anxCompraDividendo.setAnxCompra(anxCompra);
                                                                        anxCompraDividendo.setConCodigo(anxConcepto.getConCodigo());
                                                                    }
                                                                    existeConcepto = true;
                                                                } else {
                                                                    existeConcepto = false;
                                                                    estadoError = 1;
                                                                }
                                                                if (!existeConcepto) {
                                                                    nombreConcepto = anxCompraDetalleTO.get(i).getDetConcepto();
                                                                    i = anxCompraDetalleTO.size();
                                                                } else {
                                                                    anxCompraDetalles.add(anxCompraDetalle);
                                                                    if (anxCompraDividendo != null) {
                                                                        anxCompraDividendos.add(anxCompraDividendo);
                                                                    }
                                                                }
                                                            }
                                                            if (anxCompraDetalleEliminarTO != null) {
                                                                for (int i = 0; i < anxCompraDetalleEliminarTO.size(); i++) {
                                                                    AnxConcepto anxConcepto = conceptoDao.obtenerAnexo(anxCompraTO.getCompRetencionFechaEmision(), anxCompraDetalleTO.get(i).getDetConcepto());
                                                                    if (anxConcepto != null) {
                                                                        anxCompraDetalleEliminar = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDetalle(anxCompraDetalleEliminarTO.get(i));
                                                                        anxCompraDetalleEliminar.setAnxCompra(anxCompra);
                                                                        anxCompraDetalleEliminar.setDetConcepto(anxConcepto.getConCodigo());
                                                                        existeConcepto = true;
                                                                    } else {
                                                                        existeConcepto = false;
                                                                        estadoError = 1;
                                                                    }
                                                                    if (!existeConcepto) {
                                                                        nombreConcepto = anxCompraDetalleTO.get(i).getDetConcepto();
                                                                        i = anxCompraDetalleEliminarTO.size();
                                                                    } else {
                                                                        anxCompraDetallesEliminar.add(anxCompraDetalleEliminar);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            existeConcepto = true;
                                                        }
                                                        // se implemento 2016
                                                        if (invComprasTO.getCompDocumentoTipo().equals("41")) {
                                                            if (anxCompraReembolsoTO != null) {
                                                                for (AnxCompraReembolsoTO o : anxCompraReembolsoTO) {
                                                                    anxCompraReembolso = ConversionesAnexos.convertirAnxCompraReembolsoTO_AnxCompraReembolso(o);
                                                                    anxCompraReembolsos.add(anxCompraReembolso);
                                                                }
                                                            }
                                                            if (anxCompraReembolsoEliminarTO != null) {
                                                                for (AnxCompraReembolsoTO o : anxCompraReembolsoEliminarTO) {
                                                                    anxCompraReembolsoEliminar = ConversionesAnexos.convertirAnxCompraReembolsoTO_AnxCompraReembolso(o);
                                                                    anxCompraReembolsosEliminar.add(anxCompraReembolsoEliminar);
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        existeConcepto = true;
                                                    }
                                                    //
                                                } else {
                                                    existeConcepto = true;
                                                }
                                                if (existeConcepto) {
                                                    // COMPROBAR SI NO EXISTE NUMERO DE FACTURA
                                                    String codigoFactura = comprasDao.getConteoNumeroFacturaCompra(invComprasTO.getEmpCodigo(), invComprasTO.getProvCodigo(), invComprasTO.getCompDocumentoTipo(), invComprasTO.getCompDocumentoNumero());
                                                    String codigoFacturaAux = invComprasCreadas.getInvComprasPK().getCompEmpresa().trim().concat(
                                                            invComprasCreadas.getInvComprasPK().getCompPeriodo().trim().concat(
                                                                    invComprasCreadas.getInvComprasPK().getCompMotivo().trim().concat(invComprasCreadas.getInvComprasPK().getCompNumero().trim())));

                                                    if ((codigoFactura.trim().isEmpty() || codigoFactura.trim().equals(codigoFacturaAux))
                                                            || (invComprasTO.getCompDocumentoNumero().equals("999-999-999999999") || invComprasTO.getCompDocumentoTipo().equals("00") && invComprasTO.getCompDocumentoTipo().equals("99"))) {
                                                        boolean existe = false;
                                                        // COMPROBAR SI EL TIPO DE DOCUMENTO ES 00
                                                        boolean continua = true;
                                                        if (!invComprasTO.getCompDocumentoTipo().equals("00") && !invComprasTO.getCompDocumentoTipo().equals("99")) {
                                                            if (invComprasTO.getCompDocumentoTipo().equals("04")) {
                                                                if (anxCompraTO == null) {
                                                                    continua = false;
                                                                    existe = true;
                                                                }
                                                            }
                                                            if (continua) {
                                                                // COMPROBAR SI NO EXISTE NUMERO DE RETENCION
                                                                anxCompraTO.setCompRetencionNumero(anxCompraTO.getCompRetencionNumero() == null ? "" : anxCompraTO.getCompRetencionNumero());
                                                                if (!anxCompraTO.getCompRetencionNumero().isEmpty()) {
                                                                    String codigoRetencion = compraDao.getConteoNumeroRetencion(anxCompraTO.getEmpCodigo(), anxCompraTO.getCompRetencionNumero());
                                                                    String codigoRetencionAux = invComprasCreadas.getInvComprasPK().getCompEmpresa().trim()
                                                                            .concat(invComprasCreadas.getInvComprasPK().getCompPeriodo().trim().concat(invComprasCreadas
                                                                                    .getInvComprasPK().getCompMotivo().trim().concat(invComprasCreadas.getInvComprasPK().getCompNumero().trim())));
                                                                    if (codigoRetencion != null) {
                                                                        if (codigoRetencion.trim().equals(codigoRetencionAux)) {
                                                                            existe = true;
                                                                        } else {
                                                                            retorno = "F<html>El Número de Retención que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                                                            existe = false;
                                                                        }
                                                                    } else {
                                                                        existe = true;
                                                                    }
                                                                } else {
                                                                    existe = true;
                                                                }
                                                            }
                                                        } else {
                                                            existe = true;
                                                        }
                                                        if (existe) {

                                                            if (!desmayorizar && !invCompras.getCompAnulado() && !invCompras.getCompPendiente() && invCompras.getCompDocumentoTipo().compareTo("04") == 0) {
                                                                mensajeClase = verificarStockCompras(invCompras, listInvComprasDetalle);
                                                            }
                                                            ConContable conContable = contableService.obtenerPorId(invCompras.getConEmpresa(), invCompras.getConPeriodo(), invCompras.getConTipo(), invCompras.getConNumero());
                                                            List<SisSuceso> listaSisSuceso = new ArrayList<>();
                                                            if (mensajeClase.isEmpty()) {
                                                                if (invCompras.getCompAnulado()) {
                                                                    if (conContable != null) {
                                                                        conContable.setConAnulado(true);
                                                                        susSuceso = "DELETE";
                                                                        sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                                                        listaSisSuceso.add(sisSuceso);
                                                                        /////////////// NUEVO SUCESO PARA CONTABLE
                                                                        SisSuceso sisSucesoContable = Suceso
                                                                                .crearSisSuceso("contabilidad.con_contable", conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero(),
                                                                                        "DELETE", "Se anuló el contable del periodo " + conContable.getConContablePK().getConPeriodo() + ", del tipo "
                                                                                        + conContable.getConContablePK().getConTipo() + ", de la numeración " + conContable.getConContablePK().getConNumero(), sisInfoTO);
                                                                        listaSisSuceso.add(sisSucesoContable);
                                                                        sisSuceso = null;
                                                                    }
                                                                } else if (desmayorizar) {
                                                                    if (conContable != null) {
                                                                        conContable.setConPendiente(true);
                                                                    }
                                                                } else {
                                                                    if (conContable != null) {
                                                                        conContable.setConPendiente(false);
                                                                    }
                                                                }
                                                                if (quitarMotivoAnulacion) {
                                                                    if (conContable != null) {
                                                                        conContable.setConAnulado(false);
                                                                        susSuceso = "RESTORE";
                                                                        sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                                                        listaSisSuceso.add(sisSuceso);
                                                                        /////////////// NUEVO SUCESO PARA CONTABLE
                                                                        SisSuceso sisSucesoContable = Suceso.crearSisSuceso("contabilidad.con_contable", conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo()
                                                                                + " " + conContable.getConContablePK().getConNumero(), "RESTORE", "Se restauró el contable del periodo " + conContable.getConContablePK()
                                                                                .getConPeriodo() + ", del tipo " + conContable.getConContablePK().getConTipo() + ", de la numeración " + conContable.getConContablePK()
                                                                                .getConNumero(), sisInfoTO);
                                                                        listaSisSuceso.add(sisSucesoContable);
                                                                        sisSuceso = null;
                                                                    }
                                                                }

                                                                if (invCompras.getCompAnulado()) {
                                                                    invComprasMotivoAnulacion.setInvCompras(invCompras);
                                                                }
                                                                if (invCompras.getCompDocumentoTipo().equals("00") || invCompras.getCompDocumentoTipo().equals("19")) {
                                                                    if (invCompras.getCompDocumentoNumero() != null && invCompras.getCompDocumentoNumero().equals("999-999-999999999")) {
                                                                        invCompras.setCompDocumentoNumero(null);
                                                                    }
                                                                }

                                                                comprobar = comprasDao.modificarInvCompras(invCompras,
                                                                        (desmayorizar ? null : listInvComprasDetalle),
                                                                        listInvComprasDetalleEliminar, anxCompra,
                                                                        anxCompraDetalles, anxCompraDetallesEliminar,
                                                                        anxCompraDividendos, anxCompraReembolsos,
                                                                        anxCompraReembolsosEliminar,
                                                                        anxCompraFormaPagos,
                                                                        anxCompraFormaPagosEliminar, sisSuceso,
                                                                        listaSisSuceso, conContable,
                                                                        invComprasMotivoAnulacion,
                                                                        eliminarMotivoAnulacion, desmayorizar, listImagen);

                                                                if (comprobar) {
                                                                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(invComprasTO.getEmpCodigo(), invCompras.getInvComprasPK().getCompPeriodo());
                                                                    retorno = "T<html>Se " + estado
                                                                            + " la compra con la siguiente información:<br><br>"
                                                                            + "Periodo: <font size = 5>"
                                                                            + sisPeriodo.getPerDetalle()
                                                                            + "</font>.<br> Motivo: <font size = 5>"
                                                                            + invCompras.getInvComprasPK().getCompMotivo()
                                                                            + "</font>.<br> Número: <font size = 5>"
                                                                            + invCompras.getInvComprasPK().getCompNumero()
                                                                            + "</font>.</html>"
                                                                            + invCompras.getInvComprasPK().getCompNumero()
                                                                            + "," + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                                                } else {
                                                                    retorno = "F<html>Hubo un error al modificar la Compra...\nIntente de nuevo o contacte con el administrador</html>";
                                                                }
                                                            } else {
                                                                retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                                                                mensajeTO.setListaErrores1(mensajeClase);
                                                            }
                                                        } else {
                                                            retorno = "F<html>El Número de Retención que ingresó ya existe.<br>Intente de nuevo o contacte con el administrador</html>";
                                                        }
                                                    } else {
                                                        retorno = "F<html>El Número de comprobante que ingresó ya existe...<br>Intente de nuevo o contacte con el administrador</html>";
                                                    }
                                                } else if (estadoError == 1) {
                                                    retorno = "F<html>El Concepto de Retención " + nombreConcepto + " que ha escogido no se encuentra disponible.<br>Contacte con el administrador.</html>";
                                                } else {
                                                    retorno = "F<html>El Concepto de Retención " + nombreConcepto + " que ha escogido no se encuentra disponible.<br>Contacte con el administrador.</html>";
                                                }
                                            } else if (estadoDetalleEliminar == 1) {
                                                retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                            } else {
                                                retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                            }
                                        } else if (estadoDetalle == 1) {
                                            retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                        } else if (estadoDetalle == 2) {
                                            retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                        } else {
                                            retorno = estadoDetalleComplemento;
                                        }
                                    } else {
                                        retorno = "F<html>" + detalleError + "</html>";
                                    }
                                } else {
                                    retorno = "F<html>El Proveedor que escogió ya no está disponible...\nIntente de nuevo, escoja otro Proveedor o contacte con el administrador</html>";
                                }
                            } else if (validacionModificar) {
                                retorno = "F<html>La compra que quiere modificar ya no se encuentra disponible.</html>";
                            } else {
                                retorno = "F<html>La compra que desea restaurar NO existe o NO está anulada</html>";
                            }
                        } else {
                            retorno = "F<html>No se encuentra el Motivo...</html>";
                        }
                    } else {
                        retorno = "F<html>El NÚMERO DE RETENCIÓN que ingresó se encuentra ANULADO o no está REGISTRADA la secuencia</html>";
                    }
                } else {
                    retorno = "F<html>No se puede MAYORIZAR, DESMAYORIZAR o ANULAR debido a que el periodo se encuentra cerrado</html>";
                }
            } else {
                retorno = "F <html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public MensajeTO validarInvContableComprasDetalleTO(String empresa, String periodo, String motivo,
            String compraNumero, SisInfoTO sisInfoTO) throws Exception {
        List<String> mensajeClase = new ArrayList<>();
        MensajeTO mensajeTO = new MensajeTO();
        String retorno;
        List<ConFunContabilizarComprasDetalleTO> validarConDetalle = contableService
                .getConFunContabilizarComprasDetalle(empresa, periodo, motivo, compraNumero, "VALIDAR");
        boolean banderaValidar = true;
        for (ConFunContabilizarComprasDetalleTO conFunContabilizarComprasDetalleTO : validarConDetalle) {
            if (conFunContabilizarComprasDetalleTO.getDetObservaciones() != null) {
                mensajeClase.add(conFunContabilizarComprasDetalleTO.getDetObservaciones());
                banderaValidar = false;
            }
        }
        if (banderaValidar) {
            retorno = "T<html>Correctamente</html>";
        } else {
            retorno = "F<html>Existen los Siguientes Errores...</html>";
            mensajeTO.setListaErrores1(mensajeClase);
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero) throws Exception {
        return comprasDao.getEstadoCCCVT(empresa, periodo, motivo, numero);
    }

    @Override
    public String mayorizarDesmayorizarComprasSql(InvComprasPK invComprasPK, boolean pendiente, boolean revisado, SisInfoTO sisInfoTO) throws Exception {
        boolean continuar = true;
        String respuesta = "";
        if (pendiente) {
            InvCompraCabeceraTO invCompraCabeceraTO = getInvCompraCabeceraTO(invComprasPK.getCompEmpresa(), invComprasPK.getCompPeriodo(), invComprasPK.getCompMotivo(), invComprasPK.getCompNumero());
            if (invCompraCabeceraTO.getConPeriodo() != null && invCompraCabeceraTO.getConTipo() != null && invCompraCabeceraTO.getConNumero() != null) {
                List<String> lisCheques = contableService.validarChequesConciliados(sisInfoTO.getEmpresa(), invCompraCabeceraTO.getConPeriodo(), invCompraCabeceraTO.getConTipo(), invCompraCabeceraTO.getConNumero());
                if (lisCheques != null && !lisCheques.isEmpty()) {
                    continuar = false;
                    respuesta = "FNo se puede modificar un contable que contiene un cheque conciliado";
                }
            }
        }

        if (continuar) {
            String estado = pendiente ? "desmayorizo" : "mayorizo";
            susClave = invComprasPK.getCompPeriodo() + " " + invComprasPK.getCompMotivo() + " " + invComprasPK.getCompNumero();
            susDetalle = "Se " + estado + " el registro con el codigo " + invComprasPK.getCompPeriodo() + " " + invComprasPK.getCompMotivo() + " " + invComprasPK.getCompNumero();
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_compra";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprasDao.mayorizarDesmayorizarComprasSql(invComprasPK, pendiente, revisado, sisSuceso);
            respuesta = "TLa compra " + invComprasPK.getCompPeriodo() + "|" + invComprasPK.getCompMotivo() + "|" + invComprasPK.getCompNumero() + " se ha desmayorizado correctamente";
        }

        return respuesta;
    }

    @Override
    public String anularRestaurarComprasSql(InvComprasPK invComprasPK, boolean anulado, boolean actualizarFechaUltimaValidacionSri, SisInfoTO sisInfoTO) throws Exception {
        String estado = anulado ? "anulado" : "restaurado";
        susClave = invComprasPK.getCompPeriodo() + " " + invComprasPK.getCompMotivo() + " " + invComprasPK.getCompNumero();
        susDetalle = "El registro " + invComprasPK.getCompPeriodo() + " " + invComprasPK.getCompMotivo() + " " + invComprasPK.getCompNumero() + " se ha " + estado + " correctamente";
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_compra";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        comprasDao.anularRestaurarComprasSql(invComprasPK, anulado, actualizarFechaUltimaValidacionSri, sisSuceso);
        if (anulado) {
            comprasDao.eliminarLiquidacionCompras(invComprasPK);
        }
        return "TLa compra " + invComprasPK.getCompPeriodo() + "|" + invComprasPK.getCompMotivo() + "|" + invComprasPK.getCompNumero() + (anulado ? " se ha anulado correctamente." : " se ha restaurado correctamente.");
    }

    @Override
    public String guardarImagenesCompra(InvComprasPK invComprasPK, List<InvAdjuntosCompras> listImagenboolean) throws Exception {
        comprasDao.guardarImagenesCompra(invComprasPK, listImagenboolean);
        return "TLas imagenes de la compra " + invComprasPK.getCompPeriodo() + "|" + invComprasPK.getCompMotivo() + "|" + invComprasPK.getCompNumero() + " se ha guardado correctamente.";
    }

    @Override
    public InvComprasDatosBasicoTO getDatosBasicosCompra(ConContablePK conContablePK) throws Exception {
        return comprasDao.getDatosBasicosCompra(conContablePK);
    }

    @Override
    public List<InvAdjuntosCompras> getAdjuntosCompra(InvComprasPK invComprasPK) throws Exception {
        return comprasDao.getAdjuntosCompra(invComprasPK);
    }

    @Override
    public List<InvAdjuntosCompras> convertirStringUTF8(InvComprasPK invComprasPK) {
        try {
            return comprasDao.getAdjuntosCompra(invComprasPK);
        } catch (Exception ex) {
            Logger.getLogger(ComprasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public InvAdjuntosComprasWebTO completarArchivo(InvAdjuntosCompras invAdjuntoComprasTO) {
        InvAdjuntosComprasWebTO adjunto = new InvAdjuntosComprasWebTO();
        adjunto.setAdjBucket(invAdjuntoComprasTO.getAdjBucket());
        adjunto.setAdjSecuencial(invAdjuntoComprasTO.getAdjSecuencial());
        adjunto.setAdjClaveArchivo(invAdjuntoComprasTO.getAdjClaveArchivo());
        adjunto.setAdjTipo(invAdjuntoComprasTO.getAdjTipo());
        adjunto.setAdjUrlArchivo(invAdjuntoComprasTO.getAdjUrlArchivo());
        return adjunto;
    }

    @Override
    public List<InvAdjuntosComprasWebTO> convertirStringUTF8(InvComprasPK invComprasPK, String tipo) {
        List<InvAdjuntosCompras> listaInvAdjuntoCompraTO = null;
        List<InvAdjuntosComprasWebTO> listaRespuesta = new ArrayList<>();
        try {
            listaInvAdjuntoCompraTO = comprasDao.getAdjuntosCompra(invComprasPK);
            switch (tipo) {
                case "FACTURA":
                    for (InvAdjuntosCompras invAdjuntoComprasTO : listaInvAdjuntoCompraTO) {
                        if (invAdjuntoComprasTO.getAdjTipo().equals("FACTURA")) {
                            listaRespuesta.add(completarArchivo(invAdjuntoComprasTO));
                        }
                    }
                    break;
                case "OTROS":
                    for (InvAdjuntosCompras invAdjuntoComprasTO : listaInvAdjuntoCompraTO) {
                        if (invAdjuntoComprasTO.getAdjTipo().equals("OTROS")) {
                            listaRespuesta.add(completarArchivo(invAdjuntoComprasTO));
                        }
                    }
                    break;
                case "GUIA REMISION":
                    for (InvAdjuntosCompras invAdjuntoComprasTO : listaInvAdjuntoCompraTO) {
                        if (invAdjuntoComprasTO.getAdjTipo().equals("GUIA REMISION")) {
                            listaRespuesta.add(completarArchivo(invAdjuntoComprasTO));
                        }
                    }
                    break;
                default:
                    for (InvAdjuntosCompras invAdjuntoComprasTO : listaInvAdjuntoCompraTO) {
                        listaRespuesta.add(completarArchivo(invAdjuntoComprasTO));
                    }
                    break;
            }

        } catch (Exception ex) {
            Logger.getLogger(ComprasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaRespuesta;
    }

    //Operaciones
    @Override
    public Map<String, Object> consultarCompra(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        InvCompraCabeceraTO invCompraCabeceraTO = getInvCompraCabeceraTO(empresa, periodo, motivo, numero);
        AnxLiquidacionComprasElectronica liquidacionCompra = liquidacionCompraElectronicaService.buscarAnxLiquidacionCompraElectronica(empresa, periodo, motivo, numero);
        boolean estaAutorizado = false;
        if (liquidacionCompra != null) {
            estaAutorizado = liquidacionCompra.geteEstado().equals("AUTORIZADO") ? true : false;
        }

        if (invCompraCabeceraTO != null) {
            InvComprasTO invComprasTO = new InvComprasTO();
            invComprasTO.setBodEmpresa(empresa);
            invComprasTO.setBodCodigo(invCompraCabeceraTO.getBodCodigo());
            invComprasTO.setCompActivoFijo(invCompraCabeceraTO.getCompActivoFijo());
            invComprasTO.setCompAnulado(invCompraCabeceraTO.getCompAnulado());
            invComprasTO.setCompBase0(invCompraCabeceraTO.getCompBase0());
            invComprasTO.setCompBaseExenta(invCompraCabeceraTO.getCompBaseExenta());
            invComprasTO.setCompBaseImponible(invCompraCabeceraTO.getCompBaseimponible());
            invComprasTO.setCompBaseNoObjeto(invCompraCabeceraTO.getCompBaseNoObjeto());
            invComprasTO.setCompDocumentoFormaPago(invCompraCabeceraTO.getDocumentoFormaPago());
            invComprasTO.setCompDocumentoNumero(invCompraCabeceraTO.getCompDocumentoNumero());
            invComprasTO.setCompDocumentoTipo(invCompraCabeceraTO.getCompDocumentoTipo());
            invComprasTO.setCompElectronica(invCompraCabeceraTO.getCompElectronica());
            invComprasTO.setCompFecha(invCompraCabeceraTO.getCompfecha());
            invComprasTO.setCompFechaVencimiento(invCompraCabeceraTO.getCompFechaVencimiento());
            invComprasTO.setCompFormaPago(invCompraCabeceraTO.getCompFormaPago());
            invComprasTO.setCompIce(invCompraCabeceraTO.getCompIce());
            invComprasTO.setCompImportacion(invCompraCabeceraTO.getCompImportacion());
            invComprasTO.setCompIvaVigente(invCompraCabeceraTO.getCompIvaVigente());
            invComprasTO.setCompMontoIva(invCompraCabeceraTO.getCompMontoiva());
            invComprasTO.setCompMotivo(motivo);
            invComprasTO.setCompNumero(numero);
            invComprasTO.setCompObservaciones(invCompraCabeceraTO.getCompObservaciones());
            invComprasTO.setCompOtrosImpuestos(invCompraCabeceraTO.getCompOtrosImpuestos());
            invComprasTO.setCompPendiente(invCompraCabeceraTO.getCompPendiente());
            invComprasTO.setCompPeriodo(periodo);
            invComprasTO.setCompRetencionAsumida(invCompraCabeceraTO.isRetencionAsumida());
            invComprasTO.setCompPropina(invCompraCabeceraTO.getCompPropina());
            invComprasTO.setCompRevisado(invCompraCabeceraTO.getCompRevisado());
            invComprasTO.setCompSaldo(invCompraCabeceraTO.getCompSaldo());
            invComprasTO.setCompTotal(invCompraCabeceraTO.getCompTotal());
            invComprasTO.setCompValorRetenido(invCompraCabeceraTO.getCompValorretenido());
            invComprasTO.setContNumero(invCompraCabeceraTO.getConNumero());
            invComprasTO.setContPeriodo(invCompraCabeceraTO.getConPeriodo());
            invComprasTO.setContTipo(invCompraCabeceraTO.getConTipo());
            invComprasTO.setEmpCodigo(empresa);
            invComprasTO.setProvCodigo(invCompraCabeceraTO.getProvCodigo());
            invComprasTO.setProvEmpresa(empresa);
            invComprasTO.setSecCodigo(invCompraCabeceraTO.getSecCodigo());
            invComprasTO.setSecEmpresa(empresa);
            invComprasTO.setUsrFechaInsertaCompra(invCompraCabeceraTO.getFechaUsuarioInserto());
            invComprasTO.setUsrInsertaCompra(invCompraCabeceraTO.getUsuarioInserto());
            //orden de compra
            invComprasTO.setOcEmpresa(invCompraCabeceraTO.getOcEmpresa());
            invComprasTO.setOcMotivo(invCompraCabeceraTO.getOcMotivo());
            invComprasTO.setOcSector(invCompraCabeceraTO.getOcSector());
            invComprasTO.setOcNumero(invCompraCabeceraTO.getOcNumero());
            invComprasTO.setOcOrdenPedido(invCompraCabeceraTO.getOcOrdenPedido());
            invComprasTO.setCompSaldoImportado(invCompraCabeceraTO.isCompSaldoImportado());
            invComprasTO.setCompProgramada(invCompraCabeceraTO.isCompProgramada());
            invComprasTO.setCompUsuarioApruebaPago(invCompraCabeceraTO.getCompUsuarioApruebaPago());
            invComprasTO.setCompFechaApruebaPago(invCompraCabeceraTO.getCompFechaApruebaPago());
            invComprasTO.setCompImbFacturado(invCompraCabeceraTO.isCompImbFacturado());
            invComprasTO.setCompTransportistaRuc(invCompraCabeceraTO.getCompTransportistaRuc());
            invComprasTO.setFechaUltimaValidacionSri(invCompraCabeceraTO.getFechaUltimaValidacionSri());
            invComprasTO.setFpSecuencial(invCompraCabeceraTO.getFpSecuencial());
            invComprasTO.setCtaCodigo(invCompraCabeceraTO.getCtaCodigo());
            invComprasTO.setCompGuiaCompra(invCompraCabeceraTO.getCompGuiaCompra());
            invComprasTO.setCompEsImb(invCompraCabeceraTO.getCompEsImb());
            List<InvListadoPagosTO> listaInvListadoPagosTO = new ArrayList<>();
            ItemResultadoBusquedaElectronico itemResultadoBusquedaElectronico = null;

            /*OC*/
            List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
            List<InvPedidosOrdenCompraMotivoTO> listaMotivosOC = new ArrayList<>();
            if (invComprasTO.getOcSector() != null) {
                listaMotivosOC = pedidosOrdenCompraMotivoService.getListaInvPedidosOrdenCompraMotivo(empresa, invComprasTO.getOcSector());
            }
            /*Listado*/
            List<InvComprasDetalle> listaInvCompraDetalle = comprasDetalleService.obtenerCompraDetallePorNumero(empresa, periodo, motivo, numero);
            List<InvListaDetalleComprasTO> listaInvListaDetalleComprasTO = comprasDetalleService.getListaInvCompraDetalleTO(empresa, periodo, motivo, numero);

            boolean tieneSeriesOcupadas = false;
            for (InvComprasDetalle detalle : listaInvCompraDetalle) {
                for (InvListaDetalleComprasTO detalleTO : listaInvListaDetalleComprasTO) {
                    if (detalleTO.getSecuencial().equals(detalle.getDetSecuencial())) {
                        detalleTO.setDetCreditoTributario(detalle.getProCreditoTributario());
                        detalleTO.setFactorConversion(detalle.getInvProducto().getProFactorCajaSacoBulto());
                        //Series
                        boolean algunaSerieOcupada = false;
                        List<InvComprasDetalleSeriesTO> listaSerieTO = new ArrayList<>();
                        List<InvComprasDetalleSeries> listaSerie = comprasDetalleSeriesDao.listarSeriesPorSecuencialDetalle(detalle.getDetSecuencial());
                        if (listaSerie != null && listaSerie.size() > 0) {
                            for (InvComprasDetalleSeries serie : listaSerie) {
                                InvComprasDetalleSeriesTO serieTO = ConversionesInventario.convertirInvComprasDetalleSeries_InvComprasDetalleSeriesTO(serie);
                                serieTO.setSerieOcupada(invSeriesService.serieCompraOcupada(empresa, serieTO.getDetNumeroSerie(), detalleTO.getCodigoProducto()));
                                if (serieTO.isSerieOcupada()) {
                                    tieneSeriesOcupadas = true;
                                    algunaSerieOcupada = true;
                                }
                                listaSerieTO.add(serieTO);
                            }
                        }
                        if (algunaSerieOcupada) {
                            detalleTO.setSerieOCupada(algunaSerieOcupada);
                        }
                        detalleTO.setInvComprasDetalleSeriesListTO(listaSerieTO);
                        break;
                    }
                }

            }
            /*Anexo si tiene RETENCION O NO*/
            AnxCompraTO anxCompraTO = compraDao.getAnexoCompraTO(empresa, periodo, motivo, numero);
            if (anxCompraTO != null) {
                Boolean existeDetalle = compraDao.existeDetalleRetención(new AnxCompraPK(empresa, anxCompraTO.getPerCodigo(), anxCompraTO.getMotCodigo(), anxCompraTO.getCompNumero()));
                campos.put("existeDetalle", existeDetalle);
                List<AnxSustentoComboTO> sustentos = sustentoService.getListaAnxSustentoComboTOByTipoCredito(invCompraCabeceraTO.getCompDocumentoTipo(), null);
                List<AnxFormaPagoTO> formasDePagoAnx = formaPagoAnexoService.getAnexoFormaPagoTO(UtilsDate.DeStringADate(invComprasTO.getCompFecha()));
                List<AnxCompraFormaPagoTO> formaPagoRetencion = compraFormaPagoService.getAnexoCompraFormaPagoTO(empresa, periodo, motivo, numero);
                List<AnxCompraReembolsoTO> listaAnxCompraReembolsoTO = compraReembolsoService.getAnexoCompraReembolsoTOs(empresa, periodo, motivo, numero);
                campos.put("listAnxCompraReembolsoTO", listaAnxCompraReembolsoTO);
                campos.put("sustentos", sustentos);
                campos.put("formasDePagoAnx", formasDePagoAnx);
                campos.put("formaPagoRetencion", formaPagoRetencion);
            }

            /*Proveedor*/
            InvProveedorTO proveedor = proveedorService.getProveedorTO(empresa, invComprasTO.getProvCodigo()).get(0);
            String codigoTipoTransaccion = tipoTransaccionService.getCodigoAnxTipoTransaccionTO(proveedor.getProvTipoId(), "COMPRA");

            /*Forma pago*/
            List<InvComboFormaPagoTO> listaFormaPago = comprasFormaPagoService.getComboFormaPagoCompra(empresa);
            List<InvListaBodegasTO> listaInvListaBodegasTO = bodegaService.buscarBodegasTO(empresa, false, null);
            List<PrdListaPiscinaTO> piscinas = piscinaService.getListaPiscinaTO(empresa.trim(), invComprasTO.getSecCodigo(), false);
            /*Documentos*/
            List<AnxTipoComprobanteComboTO> listaDocumentos = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
            /*Transportistas*/
            List<InvProveedorTransportistaTO> transportistas = invProveedorTransportistaService.listarTransportistasTO(empresa, proveedor.getProvCodigo());
            /*Lista motivos*/
            List<InvComprasMotivoTO> listaInvComprasMotivoTO = comprasMotivoService.getListaInvComprasMotivoTO(empresa, true);
            SisConfiguracionCompras sisConfiguracionCompras = configuracionComprasService.getSisConfiguracionCompras(new SisConfiguracionComprasPK(empresa, sisInfoTO.getUsuario()));

            /*comprobante electronico*/
            boolean puedeAnular = true;
            boolean puedeAnularCompra = true;
            boolean reintentarSRI = false;
            if (anxCompraTO != null && invComprasTO.getCompElectronica() && accion != null && !accion.equalsIgnoreCase("Consultar")) {
                boolean estadoSRI = urlWebServicesService.verifcarDisponibilidadSRI();
                if (estadoSRI) {
                    try {
                        if (accion != null && accion.equalsIgnoreCase("Anular")) {
                            //esto es para ver si puede anular la retencion
                            RespuestaComprobante respuestaComprobanteRetencion = urlWebServicesService.getAutorizadocionComprobantes(anxCompraTO.getCompRetencionAutorizacion(), TipoAmbienteEnum.PRODUCCION.getCode(), sisInfoTO);
                            if (respuestaComprobanteRetencion != null) {
                                if (!respuestaComprobanteRetencion.getAutorizaciones().getAutorizacion().isEmpty()) {
                                    puedeAnular = false;
                                }
                            }
                        }
                        //esto es para maximos y minimos de la compra
                        RespuestaComprobante respuestaComprobanteCompra = urlWebServicesService.getAutorizadocionComprobantes(anxCompraTO.getCompAutorizacion(), TipoAmbienteEnum.PRODUCCION.getCode(), sisInfoTO);
                        if (respuestaComprobanteCompra != null) {
                            Autorizacion comprobanteAutorizado = obtenerComprobanteAutorizado(respuestaComprobanteCompra);
                            if (comprobanteAutorizado != null) {
                                itemResultadoBusquedaElectronico = ItemResultadoBusquedaElectronico.convertirAutorizacionEnItemResultadoBusquedaElectronico(comprobanteAutorizado);
                                puedeAnularCompra = false;
                            }
                        }
                    } catch (Exception e) {
                        puedeAnular = false;
                        puedeAnularCompra = false;
                        reintentarSRI = false;
                    }
                } else {
                    puedeAnular = false;
                    puedeAnularCompra = false;
                    reintentarSRI = true;//primer intento
                }
            }
            SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(UtilsDate.DeStringADate(invComprasTO.getCompFecha()), empresa);

            Timestamp fechaActual = sistemaWebServicio.getFechaActual();
            boolean comprobarRetencionAutorizadaProcesamiento = compraElectronicaService.comprobarRetencionAutorizadaProcesamiento(empresa, periodo, motivo, numero);
            BigDecimal valorPorcentaje = porcentajeIvaService.getValorAnxPorcentajeIvaTO(invComprasTO.getCompFecha());

            //Orden compra
            InvPedidosOrdenCompra invPedidosOrdenCompra = null;
            List<OrdenCompraSaldo> listaOrdenCompraSaldo = new ArrayList<>();
            if (invComprasTO.getOcEmpresa() != null && invComprasTO.getOcMotivo() != null && invComprasTO.getOcNumero() != null) {
                InvPedidosOrdenCompraPK pk = new InvPedidosOrdenCompraPK(invComprasTO.getOcEmpresa(), invComprasTO.getOcSector(), invComprasTO.getOcMotivo(), invComprasTO.getOcNumero());
                invPedidosOrdenCompra = pedidosOrdenCompraService.getInvPedidosOrdenCompra(pk);
                listaOrdenCompraSaldo = pedidosOrdenCompraService.getInvPedidosOrdenCompraSaldo(pk);
            }
            //licencia scanner
            SisScannerConfiguracion scanner = scannerConfiguracionDao.obtenerPorId(SisScannerConfiguracion.class, empresa);
            if (scanner != null) {
                campos.put("licencia", scanner.getScLicencia());
            }
            List<SisPeriodo> listadoPeriodos = periodoService.getListaPeriodo(empresa);
            //listado IMB
            List<InvComprasDetalleImbTO> listaInvComprasDetalleImbTO = new ArrayList<>();
            List<InvComprasDetalleImb> listaInvComprasDetalleImb = comprasDetalleImbService.getListInvComprasDetalleImb(empresa, periodo, motivo, numero);

            if (listaInvComprasDetalleImb != null && listaInvComprasDetalleImb.size() > 0) {
                for (int i = 0; i < listaInvComprasDetalleImb.size(); i++) {
                    InvComprasDetalleImbTO item = new InvComprasDetalleImbTO();
                    item.setDetSecuencia(listaInvComprasDetalleImb.get(i).getDetSecuencial());
                    item.setComEmpresa(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompEmpresa());
                    item.setComPeriodo(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompPeriodo());
                    item.setComMotivo(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompMotivo());
                    item.setComNumero(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompNumero());
                    item.setComImbEmpresa(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompEmpresa());
                    item.setComImbPeriodo(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompPeriodo());
                    item.setComImbMotivo(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompMotivo());
                    item.setComImbNumero(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompNumero());
                    item.setComImbTotal(listaInvComprasDetalleImb.get(i).getCompImbTotal());
                    item.setProvImbCodigo(listaInvComprasDetalleImb.get(i).getInvProveedorImb().getInvProveedorPK().getProvCodigo());
                    item.setProvImbEmpresa(listaInvComprasDetalleImb.get(i).getInvProveedorImb().getInvProveedorPK().getProvEmpresa());
                    //fecha
                    InvCompraCabeceraTO invCompraCabecera = getInvCompraCabeceraTO(empresa, item.getComImbPeriodo(), item.getComImbMotivo(), item.getComImbNumero());
                    item.setComImbFecha(invCompraCabecera.getCompfecha());
                    listaInvComprasDetalleImbTO.add(item);
                }
            }
            //listado LIQUIDACIONES
            List<PrdLiquidacionMotivo> listaMotivoLiquidacion = liquidacionMotivoService.getListaPrdLiquidacionMotivoTO(empresa, true);
            List<InvComprasLiquidacionTO> listaInvComprasLiquidacionTO = new ArrayList<>();
            List<InvComprasLiquidacion> listaInvComprasLiquidacion = comprasLiquidacionDao.getListInvComprasLiquidacion(empresa, periodo, motivo, numero);

            if (listaInvComprasLiquidacion != null && listaInvComprasLiquidacion.size() > 0) {
                for (int i = 0; i < listaInvComprasLiquidacion.size(); i++) {
                    InvComprasLiquidacionTO item = new InvComprasLiquidacionTO();
                    item.setDetSecuencia(listaInvComprasLiquidacion.get(i).getDetSecuencial());
                    item.setComEmpresa(listaInvComprasLiquidacion.get(i).getInvCompras().getInvComprasPK().getCompEmpresa());
                    item.setComPeriodo(listaInvComprasLiquidacion.get(i).getInvCompras().getInvComprasPK().getCompPeriodo());
                    item.setComMotivo(listaInvComprasLiquidacion.get(i).getInvCompras().getInvComprasPK().getCompMotivo());
                    item.setComNumero(listaInvComprasLiquidacion.get(i).getInvCompras().getInvComprasPK().getCompNumero());
                    item.setLiqEmpresa(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqEmpresa());
                    item.setLiqMotivo(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqMotivo());
                    item.setLiqNumero(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqNumero());
                    item.setLiqTotal(listaInvComprasLiquidacion.get(i).getLiqTotal());
                    item.setLiqFecha(UtilsValidacion.fecha(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getLiqFecha(), "yyyy-MM-dd"));
                    item.setLiqLibras(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getLiqLibrasEntero().add(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getLiqLibrasColaProcesadas()));
                    listaInvComprasLiquidacionTO.add(item);
                }
            }
            List<SisEmpresa> listaEmpresa = usuarioDetalleService.getEmpresasPorUsuarioItem(sisInfoTO.getUsuario(), "pagosCartera", empresa);
            List<PrdEquipoControl> listadoEquiposControl = equipoControlDao.listarEquiposControl(empresa);
            campos.put("listaEmpresa", listaEmpresa);
            campos.put("listaInvComprasLiquidacionTO", listaInvComprasLiquidacionTO);
            campos.put("listaMotivoLiquidacion", listaMotivoLiquidacion);
            campos.put("listadoPeriodos", listadoPeriodos);
            campos.put("listaInvComprasDetalleImbTO", listaInvComprasDetalleImbTO);
            campos.put("invPedidosOrdenCompra", invPedidosOrdenCompra);
            campos.put("listaOrdenCompraSaldo", listaOrdenCompraSaldo);
            // campos.put("listaImagenes", listaImagenes);
            campos.put("listaMotivosOC", listaMotivosOC);
            campos.put("listaSectores", listaSectores);
            campos.put("piscinas", piscinas);
            campos.put("listaInvComprasMotivoTO", listaInvComprasMotivoTO);
            campos.put("listaFormaPago", listaFormaPago);
            campos.put("listaDocumentos", listaDocumentos);
            campos.put("invComprasTO", invComprasTO);
            campos.put("fechaActual", fechaActual);
            campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
            campos.put("codigoTipoTransaccion", codigoTipoTransaccion);
            campos.put("proveedor", proveedor);
            campos.put("listaEquiposControl", listadoEquiposControl);
            campos.put("listaInvListaDetalleComprasTO", listaInvListaDetalleComprasTO);
            campos.put("anxCompraTO", anxCompraTO);
            campos.put("tieneSeriesOcupadas", tieneSeriesOcupadas);
            campos.put("valorPorcentaje", valorPorcentaje);
            campos.put("listaInvListadoPagosTO", listaInvListadoPagosTO);
            campos.put("puedeAnular", puedeAnular);
            campos.put("puedeAnularCompra", puedeAnularCompra);
            campos.put("transportistas", transportistas);
            campos.put("reintentarSRI", reintentarSRI);
            campos.put("itemResultadoBusquedaElectronico", itemResultadoBusquedaElectronico);
            campos.put("comprobarRetencionAutorizadaProcesamiento", comprobarRetencionAutorizadaProcesamiento);
            campos.put("sisConfiguracionCompras", sisConfiguracionCompras);
            campos.put("listaInvListaBodegasTO", listaInvListaBodegasTO);
            campos.put("estaAutorizadoLiquidacion", estaAutorizado);

        } else {
            return null;
        }
        return campos;
    }

    @Override
    public Map<String, Object> consultarCompraActivo(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        InvCompraCabeceraTO invCompraCabeceraTO = getInvCompraCabeceraTO(empresa, periodo, motivo, numero);
        List<InvComprasDetalle> listaInvCompraDetalle = new ArrayList<>();

        if (invCompraCabeceraTO != null) {
            if (!invCompraCabeceraTO.getCompAnulado() && !invCompraCabeceraTO.getCompPendiente()) {
                listaInvCompraDetalle = comprasDetalleService.obtenerCompraDetallePorNumero(empresa, periodo, motivo, numero);
                if (listaInvCompraDetalle != null && listaInvCompraDetalle.size() > 0) {
                    listaInvCompraDetalle.forEach((item) -> {
                        item.setInvComprasDetalleSeriesList(null);
                        item.setInvPedidosOrdenCompraDetalle(null);
                        item.setInvCompras(null);
                    });
                }
                campos.put("mensaje", "T");
            } else {
                if (invCompraCabeceraTO.getCompAnulado()) {
                    campos.put("mensaje", "FLa compra se encuentra ANULADA");
                } else {
                    campos.put("mensaje", "FLa compra se encuentra PENDIENTE");
                }
            }
        } else {
            campos.put("mensaje", "FLa compra no existe");
        }

        campos.put("listaInvCompraDetalle", listaInvCompraDetalle);
        return campos;
    }

    @Override
    public InvEntidadTransaccionTO obtenerProveedorDeCompra(String empresa, String periodo, String motivo, String numero) throws Exception {
        InvCompras compra = comprasDao.buscarInvCompras(empresa, periodo, motivo, numero);
        if (compra != null) {
            InvEntidadTransaccionTO entidadTransaccion = new InvEntidadTransaccionTO();
            entidadTransaccion.setDocumento(compra.getCompDocumentoNumero());
            entidadTransaccion.setTipo("Retención");
            entidadTransaccion.setRazonSocial(compra.getInvProveedor().getProvRazonSocial());
            entidadTransaccion.setIdentificacion(compra.getInvProveedor().getProvIdNumero());
            return entidadTransaccion;
        }
        return null;
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosCompraNueva(String empresa, String fechaString, String provTipoId, String proveedor, String usuario) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        Date fecha = UtilsDate.DeStringADate(fechaString);
        List<AnxTipoComprobanteComboTO> listaDocumentos;
        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<InvComboFormaPagoTO> listaFormaPago = comprasFormaPagoService.getComboFormaPagoCompra(empresa);

        //String documentoSeleccionado = null;
        /*Lista motivos*/
        List<InvComprasMotivoTO> listaInvComprasMotivoTO = comprasMotivoService.getListaInvComprasMotivoTO(empresa, true);
        SisConfiguracionCompras sisConfiguracionCompras = configuracionComprasService.getSisConfiguracionCompras(new SisConfiguracionComprasPK(empresa, usuario));
        List<InvListaBodegasTO> listaInvListaBodegasTO = bodegaService.buscarBodegasTO(empresa, false, null);
        SisPeriodo periodo = periodoService.getPeriodoPorFecha(fecha, empresa);
        if (provTipoId != null) {
            /*Documentos*/
            String codigoTipoTransaccion = tipoTransaccionService.getCodigoAnxTipoTransaccionTO(provTipoId, "COMPRA");
            listaDocumentos = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
            if (sisConfiguracionCompras != null && sisConfiguracionCompras.getConfDocumentoPermitido() != null && !sisConfiguracionCompras.getConfDocumentoPermitido().equals("")) {
                listaDocumentos = filtrarDocumento(listaDocumentos, sisConfiguracionCompras.getConfDocumentoPermitido());
            }
        } else {
            //Si tiene documento permitido en configuración
            listaDocumentos = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(null);
            if (sisConfiguracionCompras != null && sisConfiguracionCompras.getConfDocumentoPermitido() != null && !sisConfiguracionCompras.getConfDocumentoPermitido().equals("")) {
                listaDocumentos = filtrarDocumento(listaDocumentos, sisConfiguracionCompras.getConfDocumentoPermitido());
            }
        }

        if (proveedor != null && !proveedor.equals("")) {
            InvProveedor prov = proveedorService.buscarInvProveedor(empresa, proveedor);
            if (prov != null) {
                campos.put("diasCredito", prov.getProvDiasCredito());
            }
        }

        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        BigDecimal valorPorcentaje = porcentajeIvaService.getValorAnxPorcentajeIvaTO(fechaString);

        //licencia scanner
        SisScannerConfiguracion scanner = scannerConfiguracionDao.obtenerPorId(SisScannerConfiguracion.class, empresa);
        if (scanner != null) {
            campos.put("licencia", scanner.getScLicencia());
        }
        //LIQUIDACION
        List<PrdLiquidacionMotivo> listaMotivoLiquidacion = liquidacionMotivoService.getListaPrdLiquidacionMotivoTO(empresa, true);
        List<PrdEquipoControl> listadoEquiposControl = equipoControlDao.listarEquiposControl(empresa);
        List<SisEmpresa> listaEmpresa = usuarioDetalleService.getEmpresasPorUsuarioItem(usuario, "pagosCartera", empresa);
        campos.put("listaEmpresa", listaEmpresa);
        campos.put("listaMotivoLiquidacion", listaMotivoLiquidacion);
        campos.put("listaSectores", listaSectores);
        campos.put("listaDocumentos", listaDocumentos);
        campos.put("listaInvComprasMotivoTO", listaInvComprasMotivoTO);
        campos.put("listaFormaPago", listaFormaPago);
        campos.put("periodo", periodo);
        campos.put("fechaActual", fechaActual);
        campos.put("valorPorcentaje", valorPorcentaje);
        campos.put("listaInvListaBodegasTO", listaInvListaBodegasTO);
        campos.put("listaEquiposControl", listadoEquiposControl);
        campos.put("sisConfiguracionCompras", sisConfiguracionCompras);
        campos.put("periodoAbierto", (periodo != null ? (periodo.getPerCerrado() ? false : true) : false));

        return campos;
    }

    public List<AnxTipoComprobanteComboTO> filtrarDocumento(List<AnxTipoComprobanteComboTO> listaDocumentos, String documentoSeleccionado) throws Exception {
        List<AnxTipoComprobanteComboTO> listaDocumentosFiltrado = new ArrayList<>();
        if (documentoSeleccionado != null) {
            for (int i = 0; i <= listaDocumentos.size(); i++) {
                if (documentoSeleccionado.equals(listaDocumentos.get(i).getTcCodigo())) {
                    listaDocumentosFiltrado.add(listaDocumentos.get(i));
                    break;
                }
            }
            listaDocumentos = listaDocumentosFiltrado;
        }
        return listaDocumentos;
    }

    @Override
    public Map<String, Object> validarRetencionCompra(String empresa, AnxCompraTO anxCompraTO, InvComprasTO invCompraTO, String usuario) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("mensaje", "T");

        anxCompraTO.setCompRetencionAutorizacion(anxCompraTO.getCompRetencionAutorizacion() == null ? "" : anxCompraTO.getCompRetencionAutorizacion());
        if (invCompraTO.getCompDocumentoTipo().equalsIgnoreCase("02") && anxCompraTO.getValorRetencion().compareTo(BigDecimal.ZERO) != 0) {
            campos.put("mensaje", "FEl comprobante NOTA DE VENTA no aplica un valor en la RETENCION");
            return campos;
        }

        if (invCompraTO.getCompDocumentoTipo().equalsIgnoreCase("04") && invCompraTO.getCompValorRetenido().compareTo(BigDecimal.ZERO) != 0) {
            campos.put("mensaje", "FEl comprobante NOTA DE CREDITO no aplica un valor en la RETENCIÓN");
            return campos;
        }

        if (invCompraTO.getCompDocumentoTipo().equalsIgnoreCase("04") && (anxCompraTO.getCompRetencionNumero() != null || !anxCompraTO.getCompRetencionAutorizacion().isEmpty())) {
            campos.put("mensaje", "FEl comprobante es NOTA DE CREDITO, el número de retención y el número de autorización NO deben ser ingresados");
            return campos;
        }

        if (!invCompraTO.getCompDocumentoTipo().equalsIgnoreCase("04")
                && invCompraTO.getCompValorRetenido().compareTo(BigDecimal.ZERO) != 0
                && (anxCompraTO.getCompRetencionNumero() == null || anxCompraTO.getCompRetencionAutorizacion().isEmpty())) {
            campos.put("mensaje", "FEl valor retenido es diferente de cero, el número de la retención y el número de autorización deben ser ingresados, ");
            return campos;
        }

        List<SisListaEmpresaTO> listaSisListaEmpresaTO = usuarioDetalleService.getListaLoginEmpresaTO(usuario);
        SisListaEmpresaTO sisListaEmpresaTO = null;
        for (SisListaEmpresaTO item : listaSisListaEmpresaTO) {
            if (item.getEmpCodigo().equalsIgnoreCase(anxCompraTO.getEmpCodigo())) {
                sisListaEmpresaTO = item;
                break;
            }
        }
        if (sisListaEmpresaTO != null) {
            if (!invCompraTO.getCompDocumentoTipo().equalsIgnoreCase("04")
                    && anxCompraTO.getValorRetencion().compareTo(BigDecimal.ZERO) == 0
                    && sisListaEmpresaTO.isParObligadoEmitirDocumentosElectronicos()
                    && (anxCompraTO.getCompRetencionNumero() == null || anxCompraTO.getCompAutorizacion().isEmpty())) {
                campos.put("mensaje", "FEl valor retenido es cero, pero el contribuyente esta obligado a emitir documentos electrónicos, el número de la retención y el número de autorización deben ser ingresados");
                return campos;
            }

            if (!invCompraTO.getCompDocumentoTipo().equalsIgnoreCase("04") && invCompraTO.getCompValorRetenido().compareTo(BigDecimal.ZERO) == 0
                    && !sisListaEmpresaTO.isParObligadoEmitirDocumentosElectronicos()
                    && (anxCompraTO.getCompRetencionNumero() != null || !anxCompraTO.getCompRetencionAutorizacion().isEmpty())) {
                campos.put("mensaje", "FEl valor retenido es cero, el número de la retención y el número de autorización NO deben ser ingresados");
                return campos;
            }
        }
        return campos;
    }

    @Override
    public Map<String, Object> validarRetencionDesdeCompra(String empresa, AnxCompraTO anxCompraTO, InvComprasTO invCompraTO) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("mensaje", "");
        String mensajeEnviar = "";
        String estadoNumeroRetencion;
        if (anxCompraTO == null) {
            mensajeEnviar = "F-No ha hecho ninguna Retención.\n";
        } else {
            if ((invCompraTO.getCompDocumentoTipo().equalsIgnoreCase("04")
                    || invCompraTO.getCompDocumentoTipo().equalsIgnoreCase("05"))
                    && (anxCompraTO.getCompModificadoAutorizacion() == null
                    || anxCompraTO.getCompModificadoDocumentoNumero() == null
                    || anxCompraTO.getCompModificadoDocumentoTipo() == null)) {
                mensajeEnviar = "F-Falta el Complemento de N/C o N/D en la Retencion.\n";
            }
            AnxNumeracionLineaTO anxNumeracionLineaTO = ventaService.getNumeroAutorizacion(
                    invCompraTO.getEmpCodigo(),
                    anxCompraTO.getCompRetencionNumero(),
                    "07",
                    ("'" + anxCompraTO.getCompRetencionFechaEmision() + "'")
            );

            if (anxNumeracionLineaTO != null && anxCompraTO.getCompRetencionNumero() != null) {
                estadoNumeroRetencion = anxNumeracionLineaTO.getNumeroAutorizacion().trim();
                if (estadoNumeroRetencion.equals("CADUCADO")) {
                    mensajeEnviar = (mensajeEnviar.length() == 0 ? "F" : mensajeEnviar) + "-El Número de la Retención que ingreso esta CADUCADA. Corrija este error cambiando la FECHA DE RETENCIÓN o ingrese otro NÚMERO DE RETENCIÓN.\n";
                    estadoNumeroRetencion = "";
                }
                if (estadoNumeroRetencion.equals("ANULADO")) {
                    mensajeEnviar = (mensajeEnviar.length() == 0 ? "F" : mensajeEnviar) + "-El Número de la Retención que ingreso esta ANULADA.\n";
                    estadoNumeroRetencion = "";
                }
            } else {
                anxCompraTO.setCompRetencionNumero(anxCompraTO.getCompRetencionNumero() == null
                        || anxCompraTO.getCompRetencionNumero().isEmpty() ? null
                        : anxCompraTO.getCompRetencionNumero());

                if (anxNumeracionLineaTO == null && anxCompraTO.getCompRetencionNumero() != null) {
                    mensajeEnviar = (mensajeEnviar.length() == 0 ? "F" : mensajeEnviar) + "-" + anxCompraTO.getCompRetencionNumero() + " | 07 | '" + anxCompraTO.getCompRetencionFechaEmision() + "' devolvio NULL al comprobar la disponibilidad del Número de Retención" + ".\n";
                    estadoNumeroRetencion = "";
                }

            }

            BigDecimal sumaRetncionesFuente = anxCompraTO.getCompBaseivabienes()
                    .add(anxCompraTO.getCompBaseivaservicios())
                    .add(anxCompraTO.getCompBaseivaserviciosprofesionales());
            BigDecimal ice = BigDecimal.ZERO;
            if (invCompraTO.getCompIce().compareTo(BigDecimal.ZERO) != 0) {
                ice = invCompraTO.getCompIce();
            }
            int resultadoComparacionBase0 = anxCompraTO.getCompBase0().setScale(2, RoundingMode.HALF_UP).compareTo(invCompraTO.getCompBase0().setScale(2, RoundingMode.HALF_UP));
            int resultadoComparacionBaseImponible = anxCompraTO.getCompBaseimponible().setScale(2, RoundingMode.HALF_DOWN).compareTo(invCompraTO.getCompBaseImponible().add(ice).setScale(2, RoundingMode.HALF_UP));
            int resultadoComparacionRetencionesFuenteYCero = sumaRetncionesFuente.setScale(2, RoundingMode.HALF_DOWN).compareTo(BigDecimal.ZERO);
            int resultadoComparacionRetencionesFuenteYMontoIva = sumaRetncionesFuente.setScale(2, RoundingMode.HALF_DOWN).compareTo(invCompraTO.getCompMontoIva().setScale(2, RoundingMode.HALF_UP));

            if (resultadoComparacionBase0 != 0 || resultadoComparacionBaseImponible != 0 || (resultadoComparacionRetencionesFuenteYCero != 0 && resultadoComparacionRetencionesFuenteYMontoIva != 0)) {
                String detalleError = "";
                if (resultadoComparacionBase0 == 1) {
                    detalleError = "*El valor de la base 0 de la retencion es mayor a la base 0 de la compra ("
                            + anxCompraTO.getCompBase0().setScale(2, RoundingMode.HALF_UP)
                            + " > "
                            + invCompraTO.getCompBase0().setScale(2, RoundingMode.HALF_UP) + ")";
                } else if (resultadoComparacionBase0 == -1) {
                    detalleError = "*El valor de la base 0 de la retencion es menor a la base 0 de la compra ("
                            + anxCompraTO.getCompBase0().setScale(2, RoundingMode.HALF_UP)
                            + " < "
                            + invCompraTO.getCompBase0().setScale(2, RoundingMode.HALF_UP) + ")";
                } else if (resultadoComparacionBaseImponible == 1) {
                    detalleError = "*El valor de la base imponible de la retencion es mayor a la base imponible de la compra ("
                            + anxCompraTO.getCompBaseimponible().setScale(2, RoundingMode.HALF_DOWN)
                            + " > "
                            + invCompraTO.getCompBaseImponible().setScale(2, RoundingMode.HALF_UP) + ")";
                } else if (resultadoComparacionBaseImponible == -1) {
                    detalleError = "*El valor de la base imponible de la retencion es menor a la base imponible de la compra ("
                            + anxCompraTO.getCompBaseimponible().setScale(2, RoundingMode.HALF_DOWN)
                            + " < "
                            + invCompraTO.getCompBaseImponible().setScale(2, RoundingMode.HALF_UP) + ")";
                } else if (resultadoComparacionRetencionesFuenteYCero != 0 && resultadoComparacionRetencionesFuenteYMontoIva != 0) {
                    if (resultadoComparacionRetencionesFuenteYCero == 1) {
                        detalleError = "*El valor de la suma de reteciones fuente es mayor a 0 ("
                                + sumaRetncionesFuente.setScale(2, RoundingMode.HALF_DOWN) + ")";
                    } else if (resultadoComparacionRetencionesFuenteYCero == -1) {
                        detalleError = "*El valor de la suma de reteciones fuente es menor a 0 ("
                                + sumaRetncionesFuente.setScale(2, RoundingMode.HALF_DOWN) + ")";
                    } else if (resultadoComparacionRetencionesFuenteYMontoIva == 1) {
                        detalleError = "*El valor de la suma de reteciones fuente es mayor al valor del IVA ("
                                + sumaRetncionesFuente.setScale(2, RoundingMode.HALF_DOWN)
                                + " > "
                                + invCompraTO.getCompMontoIva().setScale(2, RoundingMode.HALF_UP) + ")";
                    } else if (resultadoComparacionRetencionesFuenteYMontoIva == -1) {
                        detalleError = "*El valor de la suma de reteciones fuente es menor al valor del IVA ("
                                + sumaRetncionesFuente.setScale(2, RoundingMode.HALF_DOWN)
                                + " < "
                                + invCompraTO.getCompMontoIva().setScale(2, RoundingMode.HALF_UP) + ")";
                    }
                }
                mensajeEnviar = (mensajeEnviar.length() == 0 ? "F" : mensajeEnviar) + "-Los valores en la compra son diferentes a la de la retención.\n" + detalleError;
            }
        }
        mensajeEnviar = (mensajeEnviar.length() == 0) ? "T" : mensajeEnviar;
        campos.put("mensaje", mensajeEnviar);
        return campos;
    }

    @Override
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
            SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<AnxCompraFormaPagoTO> listaFp = new ArrayList<>();
        List<InvAdjuntosCompras> listaImagenes = new ArrayList<>();

        campos.put("mensaje", "F");
        String mensaje;
        String mensajeAux;
        String mensajeAutorizacion = "";
        MensajeTO mensajeTO;

        invCompraTO.setUsrInsertaCompra(usuario);
        invCompraTO.setEmpCodigo(empresa);
        invCompraTO.setCompPeriodo(invCompraTO.getCompFecha().substring(0, 7));
        if (formaPago.getFpDetalle().equalsIgnoreCase("POR PAGAR")) {
            invCompraTO.setCompSaldo(invCompraTO.getCompTotal().subtract(invCompraTO.getCompValorRetenido()).add(BigDecimal.ZERO));
        } else {
            invCompraTO.setCompSaldo(new BigDecimal("0.00"));
        }

        if (fp != null) {
            AnxCompraFormaPagoTO formaPagoNueva = new AnxCompraFormaPagoTO();
            formaPagoNueva.setDetSecuencial(null);
            formaPagoNueva.setFpCodigo(fp.getFpCodigo());
            listaFp.add(formaPagoNueva);
        }

        if (listAnxCompraReembolsoTO != null && listAnxCompraReembolsoTO.size() >= 0) {
            for (AnxCompraReembolsoTO item : listAnxCompraReembolsoTO) {
                item.setCompMotivo(invCompraTO.getCompMotivo());
                item.setCompNumero(invCompraTO.getCompNumero());
                item.setCompPeriodo(invCompraTO.getCompPeriodo());
            }
        }
        if (anxCompraTO != null) {
            anxCompraTO.setPerCodigo(invCompraTO.getCompPeriodo());
        }

        if (listImagen != null && listImagen.size() >= 0) {
            for (InvAdjuntosComprasWebTO item : listImagen) {
                InvAdjuntosCompras imagen = new InvAdjuntosCompras();
                InvCompras invCompra = new InvCompras();
                invCompra.setInvComprasPK(new InvComprasPK());
                invCompra.getInvComprasPK().setCompEmpresa(empresa);
                invCompra.getInvComprasPK().setCompMotivo(invCompraTO.getCompMotivo());
                invCompra.getInvComprasPK().setCompPeriodo(invCompraTO.getCompPeriodo());
                invCompra.getInvComprasPK().setCompNumero(null);
                imagen.setInvCompras(invCompra);
                imagen.setAdjSecuencial(item.getAdjSecuencial());
                imagen.setAdjTipo(item.getAdjTipo());
                imagen.setAdjArchivo(item.getImagenString().getBytes("UTF-8"));
                listaImagenes.add(imagen);
            }
        }

        mensajeTO = insertarInvComprasTO(
                invCompraTO,
                listInvComprasDetalleTO,
                anxCompraTO,
                listAnxCompraDetalleTO,
                listAnxCompraReembolsoTO,
                listaFp,
                listaImagenes,
                sisInfoTO);

        mensajeAux = mensajeTO.getMensaje();
        mensaje = mensajeAux.substring(0, mensajeAux.lastIndexOf("</html>") + 7);

        if (mensaje.charAt(0) == 'T') {
            invCompraTO.setCompNumero(mensajeAux.substring(mensajeAux.lastIndexOf("</html>") + 7, mensajeAux.lastIndexOf("</html>") + 14));
            invCompraTO.setCompPeriodo(mensajeAux.substring(mensajeAux.lastIndexOf("</html>") + 15).trim());
            if (invCompraTO.getCompPendiente()) {
                campos.put("mensaje", mensaje.substring(0));
            } else {
                ///// inicio de imprimir
                if (!invCompraTO.getCompPendiente()) {
                    if (anxCompraTO != null && anxCompraTO.getRetElectronica()) {
                        mensajeAutorizacion = "<html>  Respuesta WS SRI: <font size = 5> " + "PENDIENTE"
                                + "</font>.<br>" + "Enviado al Correo : <font size = 5> "
                                + (!proveedor.getProvEmail().isEmpty() ? proveedor.getProvEmail() : "CORREO NO REGISTRADO") + "</font>.<br>"
                                + "        Ambiente  : <font size = 5> "
                                + "</font>.<br></html>";
                    }
                    campos.put("mensaje", mensajeAutorizacion.isEmpty() ? mensaje : mensajeAutorizacion);
                    //manda a imprimir compra
                } else {
                    campos.put("mensaje", "No se puede imprimir una Compra pendiente y/o anulado...");
                }
            }
        } else if (mensajeTO.getListaErrores1().isEmpty()) {
            campos.put("mensaje", mensaje);
        } else {
            String mensajeListaErrores = "";
            for (int i = 0; i < mensajeTO.getListaErrores1().size(); i++) {
                mensajeListaErrores = mensajeListaErrores + "<br>" + mensajeTO.getListaErrores1().get(i);
            }
            campos.put("mensaje", mensaje + mensajeListaErrores);
        }

        campos.put("invCompraTO", invCompraTO);

        return campos;
    }

    public Map<String, Object> evaluarErroresComprobante(AnxComprobantesElectronicosRecibidos comprobante, InvComprasTO invCompraTO) throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException, Exception {
        String retorno = "";
        List<String> listaDetalleErrores = new ArrayList<>();
        Map<String, Object> campos = new HashMap<>();
        if (comprobante.getComproComprobante().contains("Factura")
                || comprobante.getComproComprobante().contains("Crédito")
                || comprobante.getComproComprobante().contains("Débito")) {
            //evaluar número documento
            if (!comprobante.getComproSerieComprobante().equals(invCompraTO.getCompDocumentoNumero())) {
                retorno = "F<html>El número de documento debe ser " + comprobante.getComproSerieComprobante() + ".</html>";
            } else {
                //evaluar tipo documento
                if (comprobante.getComproComprobante().contains("Factura")) {
                    if (!invCompraTO.getCompDocumentoTipo().equals("01")) {
                        retorno = "F<html>El tipo de documento seleccionado debe ser FACTURA.</html>";
                    }
                } else {
                    if (comprobante.getComproComprobante().contains("Crédito")) {
                        if (!invCompraTO.getCompDocumentoTipo().equals("04")) {
                            retorno = "F<html>El tipo de documento seleccionado debe ser NOTA DE CRÉDITO.</html>";
                        }
                    } else {
                        if (comprobante.getComproComprobante().contains("Débito")) {
                            if (!invCompraTO.getCompDocumentoTipo().equals("05")) {
                                retorno = "F<html>El tipo de documento seleccionado debe ser NOTA DE DÉDITO.</html>";
                            }
                        }
                    }
                }
                //evaluar total  total>maximo || total<minimo (error) 
                if (retorno.equals("")) {
                    BigDecimal minimo = comprobante.getComproImporteTotal().subtract(comprobante.getComproImporteTotal().multiply(new BigDecimal("0.10")));
                    BigDecimal maximo = comprobante.getComproImporteTotal().add(comprobante.getComproImporteTotal().multiply(new BigDecimal("0.10")));
                    if (invCompraTO.getCompTotal().compareTo(minimo) < 0 || invCompraTO.getCompTotal().compareTo(maximo) > 0) {
                        retorno = "F<html>El importe total de la compra debe ser mayor o igual a " + minimo + " y menor o igual a " + maximo + ".</html>";
                        if (comprobante.getComproXml() != null && comprobante.getComproXml().length != 0) {
                            String comprobanteXMLSriString = new String((byte[]) comprobante.getComproXml(), "UTF-8");
                            if (comprobanteXMLSriString != null && !comprobanteXMLSriString.equals("")) {
                                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                factory.setNamespaceAware(true);
                                DocumentBuilder builder = factory.newDocumentBuilder();
                                Document doc = builder.parse(new ByteArrayInputStream(comprobanteXMLSriString.getBytes("UTF-8")));
                                Node contenidoComprobanteXmlNode = doc.getElementsByTagName("comprobante").item(0);
                                if (contenidoComprobanteXmlNode != null) {
                                    if (invCompraTO.getCompDocumentoTipo().equals("01")) {
                                        FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXmlNode));
                                        Factura factura = facturaReporte != null ? facturaReporte.getFactura() : null;
                                        if (factura != null) {
                                            for (Factura.Detalles.Detalle det : factura.getDetalles().getDetalle()) {
                                                String detalle = det.getCodigoPrincipal() + "-" + det.getDescripcion() + "-->";
                                                BigDecimal total = det.getPrecioTotalSinImpuesto();
                                                if (det.getImpuestos().getImpuesto() != null) {
                                                    for (ImpuestoFactura impuesto : det.getImpuestos().getImpuesto()) {
                                                        if (impuesto.getCodigo().equals("2")) {
                                                            total = impuesto.getBaseImponible().add(impuesto.getValor());
                                                        }
                                                    }
                                                }
                                                detalle += total;
                                                listaDetalleErrores.add(detalle);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        campos.put("mensaje", retorno);
        campos.put("listaDetalleErrores", listaDetalleErrores);
        return campos;
    }

    @Override
    public Map<String, Object> guardarCompraImb(String empresa, String usuario, List<InvComprasDetalleTO> listInvComprasDetalleTO, List<AnxCompraDetalleTO> listAnxCompraDetalleTO, List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO, AnxCompraTO anxCompraTO, InvComprasTO invCompraTO, InvProveedorTO proveedor, InvComboFormaPagoTO formaPago, AnxFormaPagoTO fp, List<InvAdjuntosComprasWebTO> listImagen, List<InvComprasDetalleImbTO> listaCompraImb, List<InvComprasLiquidacionTO> listaCompraLiquidacionTO, SisInfoTO sisInfoTO) throws Exception {
        //Inicializar variables
        Map<String, Object> campos = new HashMap<>();
        List<AnxCompraFormaPagoTO> listaFp = new ArrayList<>();
        List<InvAdjuntosCompras> listaImagenes = new ArrayList<>();
        List<String> mensajeClase = new ArrayList<>();
        campos.put("mensaje", "F");
        String mensaje;
        String mensajeAux;
        String mensajeAutorizacion = "";
        List<String> listaErrores1 = new ArrayList<>();
        String retorno = "";
        boolean periodoCerrado = false;
        invCompraTO.setUsrInsertaCompra(usuario);
        invCompraTO.setEmpCodigo(empresa);

        //*************VERIFICAR DATOS XML*****************
        if (anxCompraTO != null && anxCompraTO.getCompAutorizacion() != null && invCompraTO.getCompElectronica()) {
            String clave = anxCompraTO.getCompAutorizacion();
            String periodo = clave.substring(0, 8);
            if (periodo != null) {
                String anio = periodo.substring(4, 8);
                String mes = periodo.substring(2, 4);
                periodo = anio.concat("-").concat(mes);
            }
            if (clave.length() > 10) {
                AnxComprobantesElectronicosRecibidos comprobante = anxComprobantesElectronicosRecibidosService.obtenerAnxComprobantesElectronicosRecibidos(empresa, periodo, clave);
                if (comprobante != null) {
                    retorno = UtilsJSON.jsonToObjeto(String.class, evaluarErroresComprobante(comprobante, invCompraTO).get("mensaje"));
                    listaErrores1 = UtilsJSON.jsonToList(String.class, evaluarErroresComprobante(comprobante, invCompraTO).get("listaDetalleErrores"));
                } else {
                    RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(clave, TipoAmbienteEnum.PRODUCCION.getCode(), sisInfoTO);
                    if (respuestaComprobante != null && existeComprobanteAutorizado(respuestaComprobante)) {
                        Autorizacion comprobanteAutorizado = obtenerComprobanteAutorizado(respuestaComprobante);
                        if (comprobanteAutorizado != null) {
                            //setear valor a comprobante electronico recibido
                            comprobante = convertirXMLAAnxComprobantesElectronicosRecibidos(respuestaComprobante, empresa, clave);
                            if (comprobante != null) {
                                retorno = UtilsJSON.jsonToObjeto(String.class, evaluarErroresComprobante(comprobante, invCompraTO).get("mensaje"));
                                listaErrores1 = UtilsJSON.jsonToList(String.class, evaluarErroresComprobante(comprobante, invCompraTO).get("listaDetalleErrores"));
                            }
                        }
                    }
                }
            }
        }

        //*************************************************
        if (retorno.equals("")) {
            if (!UtilsValidacion.isFechaSuperior(invCompraTO.getCompFecha(), "yyyy-MM-dd")) {
                comprobar = false;
                //VERIFICAR PERIODO
                List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invCompraTO.getEmpCodigo());
                for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                    if (UtilsValidacion.fecha(invCompraTO.getCompFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime() && UtilsValidacion.fecha(invCompraTO.getCompFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                        comprobar = true;
                        invCompraTO.setCompPeriodo(sisListaPeriodoTO.getPerCodigo());
                        periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                    }
                }

                if (comprobar) {
                    if (!periodoCerrado) {
                        invCompraTO.setCtaEmpresa(empresa);
                        invCompraTO.setCtaCodigo(formaPago.getCtaCodigo());
                        invCompraTO.setFpSecuencial(formaPago.getFpSecuencial());
                        //ASIGNAR VALORES
                        if (formaPago.getFpTipoPrincipal() != null && formaPago.getFpTipoPrincipal().equalsIgnoreCase("CUENTAS POR PAGAR")) {
                            invCompraTO.setCompFormaPago("POR PAGAR");
                            invCompraTO.setCompSaldo(invCompraTO.getCompTotal().subtract(invCompraTO.getCompValorRetenido()).add(BigDecimal.ZERO));
                        } else {
                            invCompraTO.setCompSaldo(new BigDecimal("0.00"));
                        }
                        if (fp != null) {
                            AnxCompraFormaPagoTO formaPagoNueva = new AnxCompraFormaPagoTO();
                            formaPagoNueva.setDetSecuencial(null);
                            formaPagoNueva.setFpCodigo(fp.getFpCodigo());
                            listaFp.add(formaPagoNueva);
                        }
                        if (listAnxCompraReembolsoTO != null && listAnxCompraReembolsoTO.size() >= 0) {
                            for (AnxCompraReembolsoTO item : listAnxCompraReembolsoTO) {
                                item.setCompMotivo(invCompraTO.getCompMotivo());
                                item.setCompNumero(invCompraTO.getCompNumero());
                                item.setCompPeriodo(invCompraTO.getCompPeriodo());
                            }
                        }
                        if (anxCompraTO != null) {
                            anxCompraTO.setPerCodigo(invCompraTO.getCompPeriodo());
                        }
                        if (listImagen != null && listImagen.size() >= 0) {
                            for (InvAdjuntosComprasWebTO item : listImagen) {
                                InvAdjuntosCompras imagen = new InvAdjuntosCompras();
                                InvCompras invCompra = new InvCompras();
                                invCompra.setInvComprasPK(new InvComprasPK());
                                invCompra.getInvComprasPK().setCompEmpresa(empresa);
                                invCompra.getInvComprasPK().setCompMotivo(invCompraTO.getCompMotivo());
                                invCompra.getInvComprasPK().setCompPeriodo(invCompraTO.getCompPeriodo());
                                invCompra.getInvComprasPK().setCompNumero(null);
                                imagen.setInvCompras(invCompra);
                                imagen.setAdjSecuencial(item.getAdjSecuencial());
                                imagen.setAdjTipo(item.getAdjTipo());
                                imagen.setAdjArchivo(item.getImagenString() != null ? item.getImagenString().getBytes("UTF-8") : null);
                                listaImagenes.add(imagen);
                            }
                        }

                        boolean continuar = true;
                        //VERIFICAR SI NUMERO AUTORIZACION ESTA ANULADO
                        if (anxCompraTO != null) {
                            if (anxCompraTO.getCompRetencionNumero() != null) {
                                AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(invCompraTO.getEmpCodigo(), anxCompraTO.getCompRetencionNumero(), "07", ("'" + anxCompraTO.getCompRetencionFechaEmision() + "'"));
                                if (anxNumeracionLineaTO.getNumeroAutorizacion().trim().equals("ANULADO")) {
                                    continuar = false;
                                }
                            }
                        }

                        if (continuar) {
                            if (comprasMotivoDao.comprobarInvComprasMotivo(invCompraTO.getEmpCodigo(), invCompraTO.getCompMotivo())) {
                                /// PREPARANDO OBJETO SISSUCESO
                                susSuceso = "INSERT";
                                susTabla = "inventario.inv_compra";
                                invCompraTO.setUsrFechaInsertaCompra(UtilsValidacion.fechaSistema());
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                //VERIFICAR SI PROVEEDOR EXISTE
                                InvProveedor invProveedor = proveedorDao.buscarInvProveedor(invCompraTO.getEmpCodigo(), invCompraTO.getProvCodigo());
                                if (invProveedor != null) {
                                    invCompraTO.setCompImportacion(false);
                                    //CREA CABECERA DE COMPRA
                                    InvCompras invCompras = ConversionesInventario.convertirInvComprasTO_InvCompras(invCompraTO);
                                    if (invCompras.getConNumero() == null || invCompras.getConPeriodo() == null || invCompras.getConNumero() == null) {
                                        invCompras.setConEmpresa(null);
                                        invCompras.setConNumero(null);
                                        invCompras.setConPeriodo(null);
                                        invCompras.setConTipo(null);
                                    }
                                    invCompras.setInvProveedor(invProveedor);
                                    //CREANDO DETALLE DE COMPRA
                                    List<InvComprasDetalle> listInvComprasDetalle = new ArrayList<>();
                                    int estadoDetalle = 0;
                                    String estadoDetalleComplemento = "";

                                    for (InvComprasDetalleTO invComprasDetalleTO : listInvComprasDetalleTO) {
                                        invComprasDetalleTO.setDetPendiente(invCompras.getCompPendiente());
                                        invComprasDetalleTO.setDetConfirmado(true);
                                        InvComprasDetalle invComprasDetalle = ConversionesInventario.convertirInvComprasDetalleTO_InvComprasDetalle(invComprasDetalleTO);
                                        //VERIFICAR PRODUCTO, SI NO EXISTE estadoDetalle = 1
                                        InvProducto invProducto = productoDao.buscarInvProducto(invComprasDetalleTO.getProEmpresa(), invComprasDetalleTO.getProCodigoPrincipal());
                                        if (invProducto != null) {
                                            invComprasDetalle.setInvProducto(invProducto);
                                            invComprasDetalle.setProCreditoTributario(invProducto.getProCreditoTributario());
                                            //VERIFICAR BODEGA, SI NO EXISTE estadoDetalle = 2
                                            InvBodega invBodega = bodegaDao.buscarInvBodega(invComprasDetalleTO.getProEmpresa(), invComprasDetalleTO.getBodCodigo());
                                            if (invBodega != null) {
                                                if (invComprasDetalle.getSecCodigo() == null || invComprasDetalle.getSecCodigo().equals("")) {
                                                    invComprasDetalle.setSecCodigo(invBodega.getSecCodigo());
                                                    invComprasDetalle.setSecEmpresa(empresa);
                                                }
                                                invComprasDetalle.setInvBodega(invBodega);
                                                listInvComprasDetalle.add(invComprasDetalle);
                                            } else {
                                                estadoDetalle = 2;
                                            }
                                        } else {
                                            estadoDetalle = 1;
                                        }

                                        if (invComprasDetalle.getInvBodega().getBodProductoPermitido() != null && !invComprasDetalle.getInvBodega().getBodProductoPermitido().equalsIgnoreCase(invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                            estadoDetalle = 3;
                                        }
                                        if (invComprasDetalle.getInvBodega().getBodProductoNoPermitido() != null && invComprasDetalle.getInvBodega().getBodProductoNoPermitido().equalsIgnoreCase(invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                            estadoDetalle = 3;
                                        }

                                        if (estadoDetalle == 3) {
                                            estadoDetalleComplemento = "F<html>El producto '" + invComprasDetalle.getInvProducto().getProNombre().trim() + "' no puede ingresar a la bodega '" + invComprasDetalle.getInvBodega().getInvBodegaPK().getBodCodigo() + "'; esta bodega NO permite productos del tipo '" + invComprasDetalle.getInvProducto().getInvProductoTipo().getTipDetalle() + "'</html>";
                                        }

                                        if (estadoDetalle == 1 || estadoDetalle == 2 || estadoDetalle == 3) {
                                            break;
                                        } else {
                                            invProducto = null;
                                        }
                                    }
                                    //VERIFICAR RETENCION DE COMPRA
                                    boolean existeConcepto = false;
                                    if (estadoDetalle == 0) {
                                        AnxCompra anxCompra = null;
                                        AnxCompraDetalle anxCompraDetalle = null;
                                        AnxCompraDividendo anxCompraDividendo = null;
                                        AnxCompraFormaPago anxCompraFormaPago = null;
                                        AnxCompraReembolso anxCompraReembolso = null;
                                        List<AnxCompraDetalle> anxCompraDetalles = new ArrayList<>();
                                        List<AnxCompraDividendo> anxCompraDividendos = new ArrayList<>();
                                        List<AnxCompraReembolso> anxCompraReembolsos = new ArrayList<>();
                                        List<AnxCompraFormaPago> anxCompraFormaPagos = new ArrayList<>();

                                        boolean esAgente = false;
                                        SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorIdEvict(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
                                        if (parametros != null && parametros.getParAgenteRetencion() != null && !parametros.getParAgenteRetencion().isEmpty()) {
                                            esAgente = true;
                                        }

                                        if (invCompraTO.getCompDocumentoTipo().equals("00") || (invCompraTO.getCompDocumentoTipo().equals("04") && anxCompraTO == null) || invCompraTO.getCompDocumentoTipo().equals("99")) {
                                            existeConcepto = true;
                                        } else {
                                            for (int i = 0; i < listaFp.size(); i++) {
                                                AnxFormaPago anxFormaPago = formaPagoAnexoService.getAnexoFormaPago(listaFp.get(i).getFpCodigo());
                                                if (anxFormaPago != null) {
                                                    anxCompraFormaPago = ConversionesAnexos.convertirAnxCompraFormaPagoTO_AnxCompraFormaPago(listaFp.get(i));
                                                    anxCompraFormaPago.setAnxCompra(anxCompra);
                                                    anxCompraFormaPago.setFpCodigo(anxFormaPago);
                                                    existeConcepto = true;
                                                } else {
                                                    existeConcepto = false;
                                                }
                                                if (!existeConcepto) {
                                                    i = listaFp.size();
                                                } else {
                                                    anxCompraFormaPagos.add(anxCompraFormaPago);
                                                }
                                            }
                                            if (!esAgente && listAnxCompraDetalleTO.isEmpty()) {
                                                existeConcepto = true;
                                            }
                                            if (existeConcepto) {
                                                anxCompra = ConversionesAnexos.convertirAnxCompraTO_AnxCompra(anxCompraTO);
                                                if (!invCompraTO.getCompDocumentoTipo().equals("04") && !invCompraTO.getCompDocumentoTipo().equals("05")) {
                                                    anxCompra.setCompModificadoDocumentoEmpresa(null);
                                                    anxCompra.setCompModificadoDocumentoTipo(null);
                                                    anxCompra.setCompModificadoDocumentoNumero(null);
                                                    anxCompra.setCompModificadoAutorizacion(null);
                                                }
                                                //CREAR DETALLE DE RETENCION 
                                                for (int i = 0; i < listAnxCompraDetalleTO.size(); i++) {
                                                    AnxConcepto anxConcepto = conceptoDao.obtenerAnexo(anxCompraTO.getCompRetencionFechaEmision(), listAnxCompraDetalleTO.get(i).getDetConcepto());
                                                    if (anxConcepto != null) {
                                                        anxCompraDividendo = null;
                                                        anxCompraDetalle = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDetalle(listAnxCompraDetalleTO.get(i));
                                                        anxCompraDetalle.setAnxCompra(anxCompra);
                                                        anxCompraDetalle.setDetConcepto(anxConcepto.getConCodigo());
                                                        if (listAnxCompraDetalleTO.get(i).getDivIrAsociado().compareTo(cero) > 0) {
                                                            anxCompraDividendo = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDividendo(listAnxCompraDetalleTO.get(i));
                                                            anxCompraDividendo.setAnxCompra(anxCompra);
                                                            anxCompraDividendo.setConCodigo(anxConcepto.getConCodigo());
                                                        }
                                                        existeConcepto = true;
                                                    } else {
                                                        existeConcepto = false;
                                                    }
                                                    if (!existeConcepto) {
                                                        i = listAnxCompraDetalleTO.size();
                                                    } else {
                                                        anxCompraDetalles.add(anxCompraDetalle);
                                                        if (anxCompraDividendo != null) {
                                                            anxCompraDividendos.add(anxCompraDividendo);
                                                        }
                                                    }
                                                }
                                                if (invCompraTO.getCompDocumentoTipo().equals("41")) {
                                                    for (AnxCompraReembolsoTO o : listAnxCompraReembolsoTO) {
                                                        anxCompraReembolso = ConversionesAnexos.convertirAnxCompraReembolsoTO_AnxCompraReembolso(o);
                                                        anxCompraReembolsos.add(anxCompraReembolso);
                                                    }
                                                }
                                            } else {
                                                existeConcepto = false;
                                            }
                                        }
                                        if (existeConcepto) {
                                            //COMPROBAR SI NO EXISTE NUMERO DE FACTURA
                                            String codigoFactura = comprasDao.getConteoNumeroFacturaCompra(invCompraTO.getEmpCodigo(), invCompraTO.getProvCodigo(), invCompraTO.getCompDocumentoTipo(), invCompraTO.getCompDocumentoNumero());
                                            if (codigoFactura.trim().isEmpty() || invCompraTO.getCompDocumentoNumero().equals("999-999-999999999") || invCompraTO.getCompDocumentoTipo().equals("00") && invCompraTO.getCompDocumentoTipo().equals("99")) {
                                                boolean noExiste = false;
                                                //COMPROBAR SI EL TIPO DE DOCUMENTO ES 00
                                                boolean continua = true;
                                                if (!invCompraTO.getCompDocumentoTipo().equals("00") && !invCompraTO.getCompDocumentoTipo().equals("99")) {
                                                    if (invCompraTO.getCompDocumentoTipo().equals("04")) {
                                                        if (anxCompraTO == null) {
                                                            continua = false;
                                                            noExiste = true;
                                                        }
                                                    }
                                                    if (continua) {
                                                        //COMPROBAR SI NO EXISTE NUMERO DE RETENCION
                                                        String codigoRetencion = null;
                                                        anxCompraTO.setCompRetencionNumero(anxCompraTO.getCompRetencionNumero() == null ? "" : anxCompraTO.getCompRetencionNumero());
                                                        if (!anxCompraTO.getCompRetencionNumero().isEmpty()) {
                                                            codigoRetencion = compraDao.getConteoNumeroRetencion(anxCompraTO.getEmpCodigo(), anxCompraTO.getCompRetencionNumero());
                                                        } else {
                                                            codigoRetencion = null;
                                                        }
                                                        if (codigoRetencion == null) {
                                                            noExiste = true;
                                                        }
                                                    }
                                                } else {
                                                    noExiste = true;
                                                }
                                                if (noExiste) {
                                                    if (!invCompras.getCompAnulado() && !invCompras.getCompPendiente() && invCompras.getCompDocumentoTipo().compareTo("04") == 0) {
                                                        for (int i = 0; i < listInvComprasDetalle.size(); i++) {
                                                            listInvComprasDetalle.get(i).setDetPendiente(invCompras.getCompPendiente());
                                                        }
                                                        mensajeClase = verificarStockCompras(invCompras, listInvComprasDetalle);
                                                    }

                                                    if (mensajeClase.isEmpty()) {
                                                        if (invCompras.getCompDocumentoTipo().equals("00") || invCompras.getCompDocumentoTipo().equals("19")) {
                                                            if (invCompras.getCompDocumentoNumero() != null && invCompras.getCompDocumentoNumero().equals("999-999-999999999")) {
                                                                invCompras.setCompDocumentoNumero(null);
                                                            }
                                                        }
                                                        //CREANDO IBM DE COMPRA
                                                        List<InvComprasDetalleImb> listInvComprasDetalleImb = new ArrayList<>();
                                                        for (int i = 0; i < listaCompraImb.size(); i++) {
                                                            InvComprasDetalleImb imb = ConversionesInventario.convertirInvComprasDetalleImbTO_InvComprasDetalleImb(listaCompraImb.get(i));
                                                            imb.setInvProveedorImb(invProveedor);
                                                            listInvComprasDetalleImb.add(imb);
                                                        }

                                                        //CREANDO LIQUIDACION COMPRAS
                                                        List<InvComprasLiquidacion> listInvComprasLiquidacion = new ArrayList<>();
                                                        for (int i = 0; i < listaCompraLiquidacionTO.size(); i++) {
                                                            InvComprasLiquidacion liq = ConversionesInventario.convertirInvComprasLiquidacionTO_InvComprasLiquidacion(listaCompraLiquidacionTO.get(i));
                                                            listInvComprasLiquidacion.add(liq);
                                                        }

                                                        if (invCompras.getSecCodigo() == null || invCompras.getSecCodigo().equals("")) {
                                                            invCompras.setSecCodigo(listInvComprasDetalle.get(0).getInvBodega().getSecCodigo());
                                                            invCompras.setSecEmpresa(empresa);
                                                        }

                                                        comprobar = comprasDao.insertarTransaccionInvCompraInventario(invCompras,
                                                                listInvComprasDetalle, sisSuceso, anxCompra,
                                                                anxCompraDetalles, anxCompraDividendos,
                                                                anxCompraReembolsos, anxCompraFormaPagos, listaImagenes, listInvComprasDetalleImb, listInvComprasLiquidacion);
                                                        if (comprobar) {
                                                            SisPeriodo sisPeriodo = periodoService.buscarPeriodo(invCompras.getEmpCodigo(), invCompras.getInvComprasPK().getCompPeriodo());
                                                            retorno = "T<html>La compra se guardo correctamente con la siguiente información:<br><br>"
                                                                    + "Periodo: <font size = 5>"
                                                                    + sisPeriodo.getPerDetalle()
                                                                    + "</font>.<br> Motivo: <font size = 5>"
                                                                    + invCompras.getInvComprasPK().getCompMotivo()
                                                                    + "</font>.<br> Número: <font size = 5>"
                                                                    + invCompras.getInvComprasPK().getCompNumero()
                                                                    + "</font>.</html>"
                                                                    + invCompras.getInvComprasPK().getCompNumero() + ","
                                                                    + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                                        } else {
                                                            retorno = "F<html>Hubo un error al guardar la Compra...\nIntente de nuevo o contacte con el administrador</html>";
                                                        }
                                                    } else {
                                                        retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                                                        listaErrores1 = mensajeClase;
                                                    }
                                                } else {
                                                    retorno = "F<html>El Número de Retención que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                                }
                                            } else {
                                                retorno = "F<html>El Número de Documento que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                            }
                                        } else {
                                            retorno = "F<html>El concepto de retención que ha escogido no se encuentra disponible.<br>Contacte con el administrador.</html>";
                                        }
                                    } else if (estadoDetalle == 1) {
                                        retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                    } else if (estadoDetalle == 2) {
                                        retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                    } else {
                                        retorno = estadoDetalleComplemento;
                                    }
                                } else {
                                    retorno = "F<html>El proveedor que escogió ya no está disponible...\nIntente de nuevo, escoja otro Proveedor o contacte con el administrador</html>";
                                }
                            } else {
                                retorno = "F<html>No se encuentra el motivo...</html>";
                            }
                        } else {
                            retorno = "F<html>El NÚMERO DE RETENCIÓN que ingresó se encuentra ANULADO</html>";
                        }
                    } else {
                        retorno = "F<html>El período que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                    }
                } else {
                    retorno = "F<html>No se encuentra ningún período para la fecha que ingresó...</html>";
                }

            } else {
                retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
            }
        }

        mensaje = retorno.substring(0, retorno.lastIndexOf("</html>") + 7);
        mensajeAux = retorno;

        if (mensaje.charAt(0) == 'T') {
            invCompraTO.setCompNumero(mensajeAux.substring(mensajeAux.lastIndexOf("</html>") + 7, mensajeAux.lastIndexOf("</html>") + 14));
            invCompraTO.setCompPeriodo(mensajeAux.substring(mensajeAux.lastIndexOf("</html>") + 15).trim());
            if (invCompraTO.getCompPendiente()) {
                campos.put("mensaje", mensaje.substring(0));
            } else {
                ///// inicio de imprimir
                if (!invCompraTO.getCompPendiente()) {
                    if (anxCompraTO != null && anxCompraTO.getRetElectronica()) {
                        mensajeAutorizacion = "<html>  Respuesta WS SRI: <font size = 5> " + "PENDIENTE"
                                + "</font>.<br>" + "Enviado al Correo : <font size = 5> "
                                + (!proveedor.getProvEmail().isEmpty() ? proveedor.getProvEmail() : "CORREO NO REGISTRADO") + "</font>.<br>"
                                + "        Ambiente  : <font size = 5> "
                                + "</font>.<br></html>";
                    }
                    campos.put("mensaje", mensajeAutorizacion.isEmpty() ? mensaje : mensajeAutorizacion);
                    //manda a imprimir compra
                } else {
                    campos.put("mensaje", "No se puede imprimir una Compra pendiente y/o anulado...");
                }
            }
        } else if (listaErrores1.isEmpty()) {
            campos.put("mensaje", mensaje);
        } else {
            String mensajeListaErrores = "";
            for (int i = 0; i < listaErrores1.size(); i++) {
                mensajeListaErrores = mensajeListaErrores + "<br>" + listaErrores1.get(i);
            }
            campos.put("mensaje", mensaje + mensajeListaErrores);
        }
        campos.put("invCompraTO", invCompraTO);

        return campos;
    }

    @Override
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
            SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("mensaje", "F");

        List<AnxCompraFormaPagoTO> listaFp = new ArrayList<>();
        List<AnxCompraFormaPagoTO> listaFpEliminar = new ArrayList<>();
        List<InvAdjuntosCompras> listaImagenes = new ArrayList<>();

        if (listImagen != null && listImagen.size() >= 0) {
            for (InvAdjuntosComprasWebTO item : listImagen) {
                InvAdjuntosCompras imagen = new InvAdjuntosCompras();
                InvCompras invCompra = new InvCompras();
                invCompra.setInvComprasPK(new InvComprasPK());
                invCompra.getInvComprasPK().setCompEmpresa(empresa);
                invCompra.getInvComprasPK().setCompMotivo(invCompraTO.getCompMotivo());
                invCompra.getInvComprasPK().setCompPeriodo(invCompraTO.getCompPeriodo());
                invCompra.getInvComprasPK().setCompNumero(null);
                imagen.setInvCompras(invCompra);
                imagen.setAdjSecuencial(item.getAdjSecuencial());
                imagen.setAdjTipo(item.getAdjTipo());
                imagen.setAdjArchivo(item.getImagenString() != null ? item.getImagenString().getBytes("UTF-8") : null);
                listaImagenes.add(imagen);
            }
        }

        if (fp != null) {
            List<AnxCompraFormaPagoTO> listAnxCompraFormaPago = compraFormaPagoService.getAnexoCompraFormaPagoTO(empresa, invCompraTO.getCompPeriodo(), invCompraTO.getCompMotivo(), invCompraTO.getCompNumero());
            if (fpEliminar != null) {//Eliminar ya existe , se ingresara un nuevo
                if (listAnxCompraFormaPago.size() > 0) {
                    listaFpEliminar.add(listAnxCompraFormaPago.get(0));
                }
                AnxCompraFormaPagoTO formaPagoNueva = new AnxCompraFormaPagoTO();
                formaPagoNueva.setDetSecuencial(null);
                formaPagoNueva.setFpCodigo(fp.getFpCodigo());
                listaFp.add(formaPagoNueva);
            } else {
                if (listAnxCompraFormaPago.size() > 0) {
                    listaFp.add(listAnxCompraFormaPago.get(0));
                } else {
                    AnxCompraFormaPagoTO formaPagoNueva = new AnxCompraFormaPagoTO();
                    formaPagoNueva.setDetSecuencial(null);
                    formaPagoNueva.setFpCodigo(fp.getFpCodigo());
                    listaFp.add(formaPagoNueva);
                }
            }
        }

        /*--Saldo--El valor que viene desde frontend se cambia aquí, el usuario no es quien interactua con ese campo*/
        if (formaPago.getFpDetalle().equalsIgnoreCase("POR PAGAR")) {
            invCompraTO.setCompSaldo(invCompraTO.getCompTotal().subtract(invCompraTO.getCompValorRetenido()).add(BigDecimal.ZERO));
        } else {
            invCompraTO.setCompSaldo(new BigDecimal("0.00"));
        }
        //Fin Saldo--*/
        /*--Periodos--*/
        if (listInvComprasDetalleTO != null) {
            for (int i = 0; i < listInvComprasDetalleTO.size(); i++) {
                listInvComprasDetalleTO.get(i).setComMotivo(invCompraTO.getCompMotivo());
                listInvComprasDetalleTO.get(i).setComPeriodo(invCompraTO.getCompPeriodo());
                listInvComprasDetalleTO.get(i).setComNumero(invCompraTO.getCompNumero());
            }
        }
        if (anxCompraTO != null) {
            anxCompraTO.setMotCodigo(invCompraTO.getCompMotivo());
            anxCompraTO.setPerCodigo(invCompraTO.getCompPeriodo());
            anxCompraTO.setCompNumero(invCompraTO.getCompNumero());
            anxCompraTO.setEmpCodigo(empresa);
        }
        if (listAnxCompraDetalleTO != null) {
            for (int i = 0; i < listAnxCompraDetalleTO.size(); i++) {
                listAnxCompraDetalleTO.get(i).setCompMotivo(invCompraTO.getCompMotivo());
                listAnxCompraDetalleTO.get(i).setCompPeriodo(invCompraTO.getCompPeriodo());
                listAnxCompraDetalleTO.get(i).setCompNumero(invCompraTO.getCompNumero());
                listAnxCompraDetalleTO.get(i).setCompEmpresa(empresa);
            }
        }
        if (listAnxCompraReembolsoTO != null) {
            for (int i = 0; i < listAnxCompraReembolsoTO.size(); i++) {
                listAnxCompraReembolsoTO.get(i).setCompMotivo(invCompraTO.getCompMotivo());
                listAnxCompraReembolsoTO.get(i).setCompPeriodo(invCompraTO.getCompPeriodo());
                listAnxCompraReembolsoTO.get(i).setCompNumero(invCompraTO.getCompNumero());
                listAnxCompraReembolsoTO.get(i).setCompEmpresa(empresa);
            }
        }
        //Fin Periodos--*/
        if (invCompraTO.getCtaCodigo() != null && !invCompraTO.getCtaCodigo().equals("")) {
            invCompraTO.setCtaEmpresa(empresa);
        }

        MensajeTO mensajeTO = modificarInvComprasTO(
                invCompraTO,
                listInvComprasDetalleTO,
                listInvComprasDetalleTOEliminar,
                anxCompraTO,
                listAnxCompraDetalleTO,
                listAnxCompraDetalleTOEliminar,//anxCompraDetalleEliminarTO
                listAnxCompraReembolsoTO,
                listAnxCompraReembolsoTOEliminar,
                listaFp,
                listaFpEliminar,//anxCompraFormaPagoEliminarTO
                desmayorizar,
                false,
                null,
                listaImagenes,
                sisInfoTO);
        campos.put("mensaje", mensajeTO.getMensaje());
        campos.put("invCompraTO", invCompraTO);

        return campos;

    }

    @Override
    public Map<String, Object> modificarCompraInventario(String empresa, List<InvComprasDetalleTO> listInvComprasDetalleTO, List<InvComprasDetalleTO> listInvComprasDetalleTOEliminar,
            List<AnxCompraDetalleTO> listAnxCompraDetalleTO, List<AnxCompraDetalleTO> listAnxCompraDetalleTOEliminar, List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO,
            List<AnxCompraReembolsoTO> listAnxCompraReembolsoTOEliminar, AnxCompraTO anxCompraTO, InvComprasTO invCompraTO, InvComboFormaPagoTO formaPago, AnxFormaPagoTO fp,
            AnxFormaPagoTO fpEliminar, boolean desmayorizar, List<InvAdjuntosComprasWebTO> listImagen, List<InvComprasDetalleImbTO> listaCompraImb, List<InvComprasDetalleImbTO> listaCompraImbEliminar,
            List<InvComprasLiquidacionTO> listaCompraLiquidacionTO,
            List<InvComprasLiquidacionTO> listInvComprasLiquidacionTOEliminar, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("mensaje", "F");

        List<AnxCompraFormaPagoTO> listaFp = new ArrayList<>();
        List<AnxCompraFormaPagoTO> listaFpEliminar = new ArrayList<>();
        List<AnxCompraFormaPagoTO> listAnxCompraFormaPago;
        List<InvAdjuntosCompras> listaImagenes = new ArrayList<>();

        if (listImagen != null && listImagen.size() >= 0) {
            for (InvAdjuntosComprasWebTO item : listImagen) {
                InvAdjuntosCompras imagen = new InvAdjuntosCompras();
                InvCompras invCompra = new InvCompras();
                invCompra.setInvComprasPK(new InvComprasPK());
                invCompra.getInvComprasPK().setCompEmpresa(empresa);
                invCompra.getInvComprasPK().setCompMotivo(invCompraTO.getCompMotivo());
                invCompra.getInvComprasPK().setCompPeriodo(invCompraTO.getCompPeriodo());
                invCompra.getInvComprasPK().setCompNumero(invCompraTO.getCompNumero());
                imagen.setInvCompras(invCompra);
                imagen.setAdjSecuencial(item.getAdjSecuencial());
                imagen.setAdjTipo(item.getAdjTipo());
                imagen.setAdjArchivo(item.getImagenString() != null ? item.getImagenString().getBytes("UTF-8") : null);
                listaImagenes.add(imagen);
            }
        }

        if (fp != null) {
            if (fpEliminar != null) {//Eliminar ya existe , se ingresara un nuevo
                boolean sonIguales = false;
                listAnxCompraFormaPago = compraFormaPagoService.getAnexoCompraFormaPagoTO(empresa, invCompraTO.getCompPeriodo(), invCompraTO.getCompMotivo(), invCompraTO.getCompNumero());
                if (listAnxCompraFormaPago.size() > 0) {
                    if (!listAnxCompraFormaPago.get(0).getFpCodigo().equals(fpEliminar.getFpCodigo()) || !fp.getFpCodigo().equals(fpEliminar.getFpCodigo())) {
                        listaFpEliminar.add(listAnxCompraFormaPago.get(0));
                    } else {
                        sonIguales = true;
                    }
                }
                if (sonIguales) {
                    listaFp.add(listAnxCompraFormaPago.get(0));
                } else {
                    AnxCompraFormaPagoTO formaPagoNueva = new AnxCompraFormaPagoTO();
                    formaPagoNueva.setDetSecuencial(null);
                    formaPagoNueva.setFpCodigo(fp.getFpCodigo());
                    listaFp.add(formaPagoNueva);
                }
            } else {
                listAnxCompraFormaPago = compraFormaPagoService.getAnexoCompraFormaPagoTO(empresa, invCompraTO.getCompPeriodo(), invCompraTO.getCompMotivo(), invCompraTO.getCompNumero());
                if (listAnxCompraFormaPago.size() > 0) {
                    listaFp.add(listAnxCompraFormaPago.get(0));
                } else {
                    AnxCompraFormaPagoTO formaPagoNueva = new AnxCompraFormaPagoTO();
                    formaPagoNueva.setDetSecuencial(null);
                    formaPagoNueva.setFpCodigo(fp.getFpCodigo());
                    listaFp.add(formaPagoNueva);
                }
            }
        }

        /*--Saldo--El valor que viene desde frontend se cambia aquí, el usuario no es quien interactua con ese campo*/
        invCompraTO.setCtaEmpresa(empresa);
        invCompraTO.setCtaCodigo(formaPago.getCtaCodigo());
        invCompraTO.setFpSecuencial(formaPago.getFpSecuencial());
        if (formaPago.getFpTipoPrincipal() != null && formaPago.getFpTipoPrincipal().equalsIgnoreCase("CUENTAS POR PAGAR")) {
            invCompraTO.setCompFormaPago("POR PAGAR");
            invCompraTO.setCompSaldo(invCompraTO.getCompTotal().subtract(invCompraTO.getCompValorRetenido()).add(BigDecimal.ZERO));
        } else {
            invCompraTO.setCompSaldo(new BigDecimal("0.00"));
        }
        //Fin Saldo--*/
        if (listInvComprasDetalleTO != null) {
            for (int i = 0; i < listInvComprasDetalleTO.size(); i++) {
                listInvComprasDetalleTO.get(i).setComMotivo(invCompraTO.getCompMotivo());
                listInvComprasDetalleTO.get(i).setComPeriodo(invCompraTO.getCompPeriodo());
                listInvComprasDetalleTO.get(i).setComNumero(invCompraTO.getCompNumero());
                //desvincular todo oc
                if ((invCompraTO.getOcMotivo() == null || invCompraTO.getOcMotivo().equals("")) && (invCompraTO.getOcNumero() == null || invCompraTO.getOcNumero().equals("")) && (invCompraTO.getOcSector() == null || invCompraTO.getOcSector().equals(""))) {
                    listInvComprasDetalleTO.get(i).setDetSecuencialOrdenCompra(null);
                    if (listInvComprasDetalleTO.get(i).getDetSecuencia() != null) {
                        comprasDetalleDao.actualizarPorSql(listInvComprasDetalleTO.get(i).getDetSecuencia().longValue());
                    }
                }
            }
        }
        if (anxCompraTO != null) {
            anxCompraTO.setMotCodigo(invCompraTO.getCompMotivo());
            anxCompraTO.setPerCodigo(invCompraTO.getCompPeriodo());
            anxCompraTO.setCompNumero(invCompraTO.getCompNumero());
            anxCompraTO.setEmpCodigo(empresa);
        }
        if (listAnxCompraDetalleTO != null) {
            for (int i = 0; i < listAnxCompraDetalleTO.size(); i++) {
                listAnxCompraDetalleTO.get(i).setCompMotivo(invCompraTO.getCompMotivo());
                listAnxCompraDetalleTO.get(i).setCompPeriodo(invCompraTO.getCompPeriodo());
                listAnxCompraDetalleTO.get(i).setCompNumero(invCompraTO.getCompNumero());
                listAnxCompraDetalleTO.get(i).setCompEmpresa(empresa);
            }
        }
        if (listAnxCompraReembolsoTO != null) {
            for (int i = 0; i < listAnxCompraReembolsoTO.size(); i++) {
                listAnxCompraReembolsoTO.get(i).setCompMotivo(invCompraTO.getCompMotivo());
                listAnxCompraReembolsoTO.get(i).setCompPeriodo(invCompraTO.getCompPeriodo());
                listAnxCompraReembolsoTO.get(i).setCompNumero(invCompraTO.getCompNumero());
                listAnxCompraReembolsoTO.get(i).setCompEmpresa(empresa);
            }
        }

        boolean eliminarMotivoAnulacion = false, periodoCerrado = false, quitarMotivoAnulacion = false;
        InvComprasMotivoAnulacion invComprasMotivoAnulacion = null;
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();

        if (!UtilsValidacion.isFechaSuperior(invCompraTO.getCompFecha(), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invCompraTO.getEmpCodigo());

            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(invCompraTO.getCompFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime() && UtilsValidacion.fecha(invCompraTO.getCompFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    comprobar = true;
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }
            if (comprobar) {
                if (!periodoCerrado) {
                    List<String> mensajeClase = new ArrayList<>();
                    boolean continuar = false;
                    if (anxCompraTO != null && (!invCompraTO.getCompAnulado() || !invCompraTO.getCompPendiente())) {
                        if (anxCompraTO.getCompRetencionNumero() != null) {
                            if (anxCompraTO.getRetElectronica()) {
                                continuar = true;
                            } else {
                                AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(invCompraTO.getEmpCodigo(), anxCompraTO.getCompRetencionNumero() == null ? "" : anxCompraTO.getCompRetencionNumero(), "07", ("'" + anxCompraTO.getCompRetencionFechaEmision() + "'"));
                                if (anxNumeracionLineaTO != null && !anxNumeracionLineaTO.getNumeroAutorizacion().trim().equals("ANULADO")) {
                                    continuar = true;
                                }
                            }
                        } else {
                            continuar = true;
                        }
                    } else {
                        continuar = true;
                    }

                    if (continuar) {
                        if (comprasMotivoDao.comprobarInvComprasMotivo(invCompraTO.getEmpCodigo(), invCompraTO.getCompMotivo())) {
                            InvCompras invComprasCreadas = comprasDao.buscarInvCompras(invCompraTO.getEmpCodigo(), invCompraTO.getCompPeriodo(), invCompraTO.getCompMotivo(), invCompraTO.getCompNumero());
                            boolean validacionModificar = (invComprasCreadas != null && !quitarMotivoAnulacion);
                            boolean validacionRestaurar = (invComprasCreadas != null && invComprasCreadas.getCompAnulado() && quitarMotivoAnulacion);
                            if (validacionModificar || validacionRestaurar) {
                                List<String> lisCheques = contableService.validarChequesConciliados(sisInfoTO.getEmpresa(), invCompraTO.getContPeriodo(), invCompraTO.getContTipo(), invCompraTO.getContNumero());
                                if (lisCheques != null && !lisCheques.isEmpty()) {
                                    mensajeTO.setMensaje("FNo se puede modificar un contable que contiene un cheque conciliado:");
                                    mensajeTO.setListaErrores1(lisCheques);
                                    campos.put("listaErrores", mensajeTO.getListaErrores1());
                                    campos.put("mensaje", mensajeTO.getMensaje());
                                    campos.put("invCompraTO", null);
                                    return campos;
                                }
                                //CREANDO SUCESO
                                susClave = invCompraTO.getCompPeriodo() + " " + invCompraTO.getCompMotivo() + " " + invCompraTO.getCompNumero();
                                susTabla = "inventario.inv_compra";
                                String estado;
                                if (invCompraTO.getCompAnulado()) {
                                    estado = "anuló";
                                } else {
                                    estado = "mayorizo";
                                }
                                if (quitarMotivoAnulacion) {
                                    eliminarMotivoAnulacion = true;
                                    InvComprasMotivoAnulacion invComprasMotivoAnulacionTmp = comprasMotivoAnuladoDao.buscarCompraMotivo(invCompraTO.getEmpCodigo(), invCompraTO.getCompPeriodo(), invCompraTO.getCompMotivo(), invCompraTO.getCompNumero());
                                    invComprasMotivoAnulacion = new InvComprasMotivoAnulacion();
                                    invComprasMotivoAnulacion.setAnuComentario(invComprasMotivoAnulacionTmp.getAnuComentario());
                                    invComprasMotivoAnulacion.setInvCompras(invComprasMotivoAnulacionTmp.getInvCompras());
                                    invComprasMotivoAnulacion.setAnuSecuencial(invComprasMotivoAnulacionTmp.getAnuSecuencial());
                                    estado = "restauró";
                                    susDetalle = "Se " + estado + " la compra en el periodo "
                                            + invCompraTO.getCompPeriodo() + ", del motivo "
                                            + invCompraTO.getCompMotivo() + ", de la numeración "
                                            + invCompraTO.getCompNumero();
                                    susSuceso = "RESTORE";
                                    /*Evita que si forma de pago se deja en blanco despues de tener un registro, este agrega automaticamente el mismo registro de la compra y no lo deja poner en vacio 
                                    invCompraTO.setCompDocumentoFormaPago(invCompraTO.getCompDocumentoFormaPago() == null
                                            ? invComprasCreadas.getCompDocumentoFormaPago()
                                            : invCompraTO.getCompDocumentoFormaPago().trim().isEmpty()
                                            ? invComprasCreadas.getCompDocumentoFormaPago()
                                            : invCompraTO.getCompDocumentoFormaPago());*/
                                    invCompraTO.setCompDocumentoFormaPago(invCompraTO.getCompDocumentoFormaPago().trim());
                                    invCompraTO.setUsrInsertaCompra(invComprasCreadas.getUsrCodigo());
                                    invCompraTO.setUsrFechaInsertaCompra(UtilsValidacion.fecha(invComprasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                } else {
                                    susDetalle = "";
                                    if (estado.trim().equals("anuló")) {
                                        susDetalle = "Compra número " + invCompraTO.getCompNumero() + "se anuló por " + invComprasMotivoAnulacion.getAnuComentario();
                                    } else {
                                        susDetalle = "Se " + estado + " la compra en el periodo " + invCompraTO.getCompPeriodo() + ", del motivo " + invCompraTO.getCompMotivo() + ", de la numeración " + invCompraTO.getCompNumero();
                                    }
                                    susSuceso = "UPDATE";
                                    /* Evita que si forma de pago se deja en blanco despues de tener un registro, este agrega automaticamente el mismo registro de la compra y no lo deja poner en vacio
                                    invCompraTO.setCompDocumentoFormaPago(invCompraTO.getCompDocumentoFormaPago() == null
                                            ? invComprasCreadas.getCompDocumentoFormaPago()
                                            : invCompraTO.getCompDocumentoFormaPago().trim().isEmpty()
                                            ? invComprasCreadas.getCompDocumentoFormaPago()
                                            : invCompraTO.getCompDocumentoFormaPago());*/
                                    invCompraTO.setCompDocumentoFormaPago(invCompraTO.getCompDocumentoFormaPago().trim());
                                    invCompraTO.setUsrInsertaCompra(invComprasCreadas.getUsrCodigo());
                                    invCompraTO.setUsrFechaInsertaCompra(UtilsValidacion.fecha(invComprasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                }
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                // CREANDO COMPRAS
                                InvCompras invCompras = ConversionesInventario.convertirInvComprasTO_InvCompras(invCompraTO);
                                if (invCompras.getConNumero() == null || invCompras.getConPeriodo() == null || invCompras.getConNumero() == null) {
                                    invCompras.setConEmpresa(null);
                                    invCompras.setConNumero(null);
                                    invCompras.setConPeriodo(null);
                                    invCompras.setConTipo(null);
                                }
                                // BUSCANDO PROVEEDOR
                                InvProveedor invProveedor = proveedorDao.buscarInvProveedor(invCompraTO.getEmpCodigo(), invCompraTO.getProvCodigo());
                                invCompras.setInvProveedor(invProveedor);
                                String detalleError = "";
                                if (invProveedor != null) {
                                    if ((!desmayorizar || quitarMotivoAnulacion) && listInvComprasDetalleTO == null) {
                                        List<InvListaDetalleComprasTO> invListaDetalleTO = comprasDetalleDao.getListaInvCompraDetalleTO(invCompraTO.getEmpCodigo(), invCompraTO.getCompPeriodo(), invCompraTO.getCompMotivo(), invCompraTO.getCompNumero());
                                        if (invListaDetalleTO != null) {
                                            listInvComprasDetalleTO = new ArrayList<InvComprasDetalleTO>();
                                            for (int i = 0; i < invListaDetalleTO.size(); i++) {
                                                InvComprasDetalleTO invComprasDetalleTO = new InvComprasDetalleTO();
                                                invComprasDetalleTO.setDetSecuencia(invListaDetalleTO.get(i).getSecuencial());
                                                invComprasDetalleTO.setDetOrden(i + 1);
                                                invComprasDetalleTO.setDetPendiente(invListaDetalleTO.get(i).getPendiente());
                                                invComprasDetalleTO.setDetCantidad(invListaDetalleTO.get(i).getCantidadProducto());
                                                invComprasDetalleTO.setDetPrecio(invListaDetalleTO.get(i).getPrecioProducto());
                                                invComprasDetalleTO.setDetPorcentajeDescuento(invListaDetalleTO.get(i).getPorcentajeDescuento());
                                                invComprasDetalleTO.setDetConfirmado(true);
                                                invComprasDetalleTO.setBodEmpresa(invCompraTO.getEmpCodigo());
                                                invComprasDetalleTO.setBodCodigo(invListaDetalleTO.get(i).getCodigoBodega());
                                                invComprasDetalleTO.setProEmpresa(invCompraTO.getEmpCodigo());
                                                invComprasDetalleTO.setProCodigoPrincipal(invListaDetalleTO.get(i).getCodigoProducto());
                                                invComprasDetalleTO.setSecEmpresa(invCompraTO.getEmpCodigo());
                                                invComprasDetalleTO.setSecCodigo(invListaDetalleTO.get(i).getCodigoCP());
                                                invComprasDetalleTO.setPisEmpresa(invCompraTO.getEmpCodigo());
                                                invComprasDetalleTO.setPisSector(invListaDetalleTO.get(i).getCodigoCP());
                                                invComprasDetalleTO.setPisNumero(invListaDetalleTO.get(i).getCodigoCC());
                                                invComprasDetalleTO.setComEmpresa(invCompraTO.getEmpCodigo());
                                                invComprasDetalleTO.setComPeriodo(invCompraTO.getCompPeriodo());
                                                invComprasDetalleTO.setComMotivo(invCompraTO.getCompMotivo());
                                                invComprasDetalleTO.setComNumero(invCompraTO.getCompNumero());
                                                listInvComprasDetalleTO.add(invComprasDetalleTO);
                                            }
                                        } else {
                                            detalleError = "Hubo en error al recuperar el detalle de la compra.\nContacte con el administrador...";
                                        }
                                    }
                                    if (detalleError.trim().isEmpty()) {
                                        // CONVIRTIENDO A ENTIDAD EL DETALLE
                                        List<InvComprasDetalle> listInvComprasDetalle = new ArrayList<InvComprasDetalle>();
                                        InvComprasDetalle invComprasDetalle = null;
                                        int estadoDetalle = 0;
                                        String estadoDetalleComplemento = "";
                                        if (!desmayorizar && !invCompras.getCompAnulado()) {
                                            for (InvComprasDetalleTO invComprasDetalleTO : listInvComprasDetalleTO) {
                                                invComprasDetalleTO.setDetConfirmado(true);
                                                invComprasDetalle = ConversionesInventario.convertirInvComprasDetalleTO_InvComprasDetalle(invComprasDetalleTO);
                                                invComprasDetalle.setInvCompras(invCompras);
                                                // BUSCANDO EL PRODUCTO
                                                InvProducto invProducto = productoDao.buscarInvProducto(invComprasDetalleTO.getComEmpresa(), invComprasDetalleTO.getProCodigoPrincipal());
                                                if (invProducto != null) {
                                                    invComprasDetalle.setInvProducto(invProducto);
                                                    invComprasDetalle.setProCreditoTributario(invProducto.getProCreditoTributario());
                                                    // BUSCANDO LA BODEGA
                                                    InvBodega invBodega = bodegaDao.buscarInvBodega(invComprasDetalleTO.getComEmpresa(), invComprasDetalleTO.getBodCodigo());
                                                    if (invBodega != null) {
                                                        if (invComprasDetalle.getSecCodigo() == null || invComprasDetalle.getSecCodigo().equals("")) {
                                                            invComprasDetalle.setSecCodigo(invBodega.getSecCodigo());
                                                            invComprasDetalle.setSecEmpresa(empresa);
                                                        }
                                                        invComprasDetalle.setInvBodega(invBodega);
                                                        listInvComprasDetalle.add(invComprasDetalle);
                                                    } else {
                                                        estadoDetalle = 2;
                                                    }
                                                } else {
                                                    estadoDetalle = 1;
                                                }
                                                if (invComprasDetalle.getInvBodega().getBodProductoPermitido() != null && !invComprasDetalle.getInvBodega().getBodProductoPermitido().equalsIgnoreCase(invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                                    estadoDetalle = 3;
                                                    estadoDetalleComplemento = "F<html>El producto '" + invComprasDetalle.getInvProducto().getProNombre().trim() + "' no puede ingresar a la bodega '" + invComprasDetalle.getInvBodega().getInvBodegaPK().getBodCodigo() + "'; esta bodega NO permite productos del tipo '" + invComprasDetalle.getInvProducto().getInvProductoTipo().getTipDetalle() + "'</html>";
                                                }
                                                if (invComprasDetalle.getInvBodega().getBodProductoNoPermitido() != null && invComprasDetalle.getInvBodega().getBodProductoNoPermitido().equalsIgnoreCase(invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                                    estadoDetalle = 3;
                                                    estadoDetalleComplemento = "F<html>El producto '" + invComprasDetalle.getInvProducto().getProNombre().trim() + "' no puede ingresar a la bodega '" + invComprasDetalle.getInvBodega().getInvBodegaPK().getBodCodigo() + "'; esta bodega NO permite productos del tipo '" + invComprasDetalle.getInvProducto().getInvProductoTipo().getTipDetalle() + "'</html>";
                                                }
                                                if (estadoDetalle == 1 || estadoDetalle == 2 || estadoDetalle == 3) {
                                                    break;
                                                } else {
                                                    invProducto = null;
                                                }
                                            }
                                        }
                                        if (estadoDetalle == 0) {
                                            // CONVIRTIENDO A ENTIDAD EL DETALLE A ELIMINAR
                                            List<InvComprasDetalle> listInvComprasDetalleEliminar = new ArrayList<InvComprasDetalle>();
                                            InvComprasDetalle invComprasDetalleEliminar;
                                            int estadoDetalleEliminar = 0;
                                            if (listInvComprasDetalleTOEliminar != null) {
                                                for (InvComprasDetalleTO invComprasDetalleTO : listInvComprasDetalleTOEliminar) {
                                                    invComprasDetalleTO.setDetConfirmado(true);
                                                    invComprasDetalleEliminar = ConversionesInventario.convertirInvComprasDetalleTO_InvComprasDetalle(invComprasDetalleTO);
                                                    invComprasDetalleEliminar.setInvCompras(invCompras);
                                                    //BUSCANDO PRODUCTO
                                                    InvProducto invProducto;
                                                    if (invComprasDetalleTO.getProCodigoPrincipal() == null || invComprasDetalleTO.getProCodigoPrincipal().equals("")) {
                                                        listInvComprasDetalleEliminar.add(invComprasDetalleEliminar);
                                                    } else {
                                                        invProducto = productoDao.buscarInvProducto(invComprasDetalleTO.getComEmpresa(), invComprasDetalleTO.getProCodigoPrincipal());
                                                        if (invProducto != null) {
                                                            invComprasDetalleEliminar.setInvProducto(invProducto);
                                                            //BUSCANDO BODEGA
                                                            InvBodega invBodega = bodegaDao.buscarInvBodega(invComprasDetalleTO.getComEmpresa(), invComprasDetalleTO.getBodCodigo());
                                                            if (invBodega != null) {
                                                                invComprasDetalleEliminar.setInvBodega(invBodega);
                                                                listInvComprasDetalleEliminar.add(invComprasDetalleEliminar);
                                                            } else {
                                                                estadoDetalleEliminar = 2;
                                                            }
                                                        } else {
                                                            estadoDetalleEliminar = 1;
                                                        }
                                                    }
                                                    if (estadoDetalleEliminar == 1 || estadoDetalleEliminar == 2) {
                                                        break;
                                                    }
                                                }
                                            }

                                            if (estadoDetalleEliminar == 0) {
                                                boolean existeConcepto = false;
                                                String nombreConcepto = "";
                                                AnxCompra anxCompra = null;
                                                AnxCompraDetalle anxCompraDetalle = null;
                                                AnxCompraDetalle anxCompraDetalleEliminar = null;
                                                AnxCompraDividendo anxCompraDividendo = null;
                                                AnxCompraReembolso anxCompraReembolso;
                                                AnxCompraReembolso anxCompraReembolsoEliminar;
                                                AnxCompraFormaPago anxCompraFormaPago = null;
                                                AnxCompraFormaPago anxCompraFormaPagoEliminar = null;
                                                List<AnxCompraDetalle> anxCompraDetalles = new ArrayList<>();
                                                List<AnxCompraDetalle> anxCompraDetallesEliminar = new ArrayList<>();
                                                List<AnxCompraDividendo> anxCompraDividendos = new ArrayList<>();
                                                List<AnxCompraReembolso> anxCompraReembolsos = new ArrayList<>();
                                                List<AnxCompraReembolso> anxCompraReembolsosEliminar = new ArrayList<>();
                                                List<AnxCompraFormaPago> anxCompraFormaPagos = new ArrayList<>();
                                                List<AnxCompraFormaPago> anxCompraFormaPagosEliminar = new ArrayList<>();
                                                int estadoError = 0;

                                                if (!invCompraTO.getCompDocumentoTipo().equals("00") && !invCompraTO.getCompDocumentoTipo().equals("99")) {
                                                    if (anxCompraTO != null) {
                                                        anxCompra = ConversionesAnexos.convertirAnxCompraTO_AnxCompra(anxCompraTO);
                                                        if (!invCompraTO.getCompDocumentoTipo().equals("04") && !invCompraTO.getCompDocumentoTipo().equals("05")) {
                                                            anxCompra.setCompModificadoDocumentoEmpresa(null);
                                                            anxCompra.setCompModificadoDocumentoTipo(null);
                                                            anxCompra.setCompModificadoDocumentoNumero(null);
                                                            anxCompra.setCompModificadoAutorizacion(null);
                                                        }
                                                        // anexo_Forma_Pago
                                                        for (int i = 0; i < listaFp.size(); i++) {
                                                            AnxFormaPago anxFormaPago = formaPagoAnexoService.getAnexoFormaPago(listaFp.get(i).getFpCodigo());
                                                            if (anxFormaPago != null) {
                                                                anxCompraFormaPago = ConversionesAnexos.convertirAnxCompraFormaPagoTO_AnxCompraFormaPago(listaFp.get(i));
                                                                anxCompraFormaPago.setAnxCompra(anxCompra);
                                                                anxCompraFormaPago.setFpCodigo(anxFormaPago);
                                                                existeConcepto = true;
                                                            } else {
                                                                existeConcepto = false;
                                                                estadoError = 2;
                                                            }
                                                            if (!existeConcepto) {
                                                                i = listaFp.size();
                                                            } else {
                                                                anxCompraFormaPagos.add(anxCompraFormaPago);
                                                            }
                                                        }
                                                        if (listaFpEliminar != null && !listaFpEliminar.isEmpty()) {
                                                            for (int i = 0; i < listaFpEliminar.size(); i++) {
                                                                AnxFormaPago anxFormaPago = formaPagoAnexoService.getAnexoFormaPago(listaFpEliminar.get(i).getFpCodigo());
                                                                if (anxFormaPago != null) {
                                                                    anxCompraFormaPagoEliminar = ConversionesAnexos.convertirAnxCompraFormaPagoTO_AnxCompraFormaPago(listaFpEliminar.get(i));
                                                                    anxCompraFormaPagoEliminar.setAnxCompra(anxCompra);
                                                                    anxCompraFormaPagoEliminar.setFpCodigo(anxFormaPago);
                                                                    existeConcepto = true;
                                                                } else {
                                                                    existeConcepto = false;
                                                                    estadoError = 2;
                                                                }
                                                                if (!existeConcepto) {
                                                                    i = listaFpEliminar.size();
                                                                } else {
                                                                    anxCompraFormaPagosEliminar.add(anxCompraFormaPagoEliminar);
                                                                }
                                                            }
                                                        }
                                                        // Anexo_detalle_concepto
                                                        if (listAnxCompraDetalleTO != null) {
                                                            for (int i = 0; i < listAnxCompraDetalleTO.size(); i++) {
                                                                AnxConcepto anxConcepto = conceptoDao.obtenerAnexo(anxCompraTO.getCompRetencionFechaEmision(), listAnxCompraDetalleTO.get(i).getDetConcepto());
                                                                if (anxConcepto != null) {
                                                                    anxCompraDividendo = null;
                                                                    anxCompraDetalle = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDetalle(listAnxCompraDetalleTO.get(i));
                                                                    anxCompraDetalle.setAnxCompra(anxCompra);
                                                                    anxCompraDetalle.setDetConcepto(anxConcepto.getConCodigo());
                                                                    if ((listAnxCompraDetalleTO.get(i).getDivIrAsociado() != null) && (listAnxCompraDetalleTO.get(i).getDivIrAsociado().compareTo(cero) > 0)) {
                                                                        anxCompraDividendo = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDividendo(listAnxCompraDetalleTO.get(i));
                                                                        anxCompraDividendo.setAnxCompra(anxCompra);
                                                                        anxCompraDividendo.setConCodigo(anxConcepto.getConCodigo());
                                                                    }
                                                                    existeConcepto = true;
                                                                } else {
                                                                    existeConcepto = false;
                                                                    estadoError = 1;
                                                                }
                                                                if (!existeConcepto) {
                                                                    nombreConcepto = listAnxCompraDetalleTO.get(i).getDetConcepto();
                                                                    i = listAnxCompraDetalleTO.size();
                                                                } else {
                                                                    anxCompraDetalles.add(anxCompraDetalle);
                                                                    if (anxCompraDividendo != null) {
                                                                        anxCompraDividendos.add(anxCompraDividendo);
                                                                    }

                                                                }
                                                            }

                                                            if (listAnxCompraDetalleTOEliminar != null) {
                                                                for (int i = 0; i < listAnxCompraDetalleTOEliminar.size(); i++) {
                                                                    AnxConcepto anxConcepto = conceptoDao.obtenerAnexo(anxCompraTO.getCompRetencionFechaEmision(), listAnxCompraDetalleTOEliminar.get(i).getDetConcepto());
                                                                    if (anxConcepto != null) {
                                                                        anxCompraDetalleEliminar = ConversionesAnexos.convertirAnxCompraDetalleTO_AnxCompraDetalle(listAnxCompraDetalleTOEliminar.get(i));
                                                                        anxCompraDetalleEliminar.setAnxCompra(anxCompra);
                                                                        anxCompraDetalleEliminar.setDetConcepto(anxConcepto.getConCodigo());
                                                                        existeConcepto = true;
                                                                    } else {
                                                                        existeConcepto = false;
                                                                        estadoError = 1;
                                                                    }
                                                                    if (!existeConcepto) {
                                                                        nombreConcepto = listAnxCompraDetalleTOEliminar.get(i).getDetConcepto();
                                                                        i = listAnxCompraDetalleTOEliminar.size();
                                                                    } else {
                                                                        anxCompraDetallesEliminar.add(anxCompraDetalleEliminar);
                                                                    }
                                                                }
                                                            }

                                                        } else {
                                                            existeConcepto = true;
                                                        }
                                                        // se implemento
                                                        // 2016
                                                        if (invCompraTO.getCompDocumentoTipo().equals("41")) {
                                                            if (listAnxCompraReembolsoTO != null) {
                                                                for (AnxCompraReembolsoTO o : listAnxCompraReembolsoTO) {
                                                                    anxCompraReembolso = ConversionesAnexos.convertirAnxCompraReembolsoTO_AnxCompraReembolso(o);
                                                                    anxCompraReembolsos.add(anxCompraReembolso);
                                                                }

                                                            }
                                                            if (listAnxCompraReembolsoTOEliminar != null) {
                                                                for (AnxCompraReembolsoTO o : listAnxCompraReembolsoTOEliminar) {
                                                                    anxCompraReembolsoEliminar = ConversionesAnexos.convertirAnxCompraReembolsoTO_AnxCompraReembolso(o);
                                                                    anxCompraReembolsosEliminar.add(anxCompraReembolsoEliminar);
                                                                }
                                                            }
                                                        }

                                                    } else {
                                                        existeConcepto = true;
                                                    }
                                                    //
                                                } else {
                                                    existeConcepto = true;
                                                }
                                                if (existeConcepto) {
                                                    // COMPROBAR SI NO EXISTE NUMERO DE FACTURA
                                                    String codigoFactura = comprasDao.getConteoNumeroFacturaCompra(invCompraTO.getEmpCodigo(), invCompraTO.getProvCodigo(), invCompraTO.getCompDocumentoTipo(), invCompraTO.getCompDocumentoNumero());
                                                    String codigoFacturaAux = invComprasCreadas.getInvComprasPK().getCompEmpresa().trim().concat(invComprasCreadas.getInvComprasPK().getCompPeriodo().trim().concat(invComprasCreadas.getInvComprasPK()
                                                            .getCompMotivo().trim().concat(invComprasCreadas.getInvComprasPK().getCompNumero().trim())));

                                                    if ((codigoFactura.trim().isEmpty() || codigoFactura.trim().equals(codigoFacturaAux)) || (invCompraTO.getCompDocumentoNumero().equals("999-999-999999999") || invCompraTO.getCompDocumentoTipo().equals("00") && invCompraTO.getCompDocumentoTipo().equals("99"))) {
                                                        boolean existe = false;
                                                        // COMPROBAR SI EL TIPO DE DOCUMENTO ES 00
                                                        boolean continua = true;
                                                        if (!invCompraTO.getCompDocumentoTipo().equals("00") && !invCompraTO.getCompDocumentoTipo().equals("99")) {
                                                            if (invCompraTO.getCompDocumentoTipo().equals("04")) {
                                                                if (anxCompraTO == null) {
                                                                    continua = false;
                                                                    existe = true;
                                                                }
                                                            }
                                                            if (continua) {
                                                                // COMPROBAR SI NO EXISTE NUMERO DE RETENCION
                                                                String codigoRetencion = null;
                                                                anxCompraTO.setCompRetencionNumero(anxCompraTO.getCompRetencionNumero() == null ? "" : anxCompraTO.getCompRetencionNumero());
                                                                if (!anxCompraTO.getCompRetencionNumero().isEmpty()) {
                                                                    codigoRetencion = compraDao.getConteoNumeroRetencion(anxCompraTO.getEmpCodigo(), anxCompraTO.getCompRetencionNumero());
                                                                    String codigoRetencionAux = invComprasCreadas
                                                                            .getInvComprasPK().getCompEmpresa().trim()
                                                                            .concat(invComprasCreadas.getInvComprasPK().getCompPeriodo().trim().concat(invComprasCreadas.getInvComprasPK().getCompMotivo().trim().concat(invComprasCreadas
                                                                                    .getInvComprasPK()
                                                                                    .getCompNumero()
                                                                                    .trim())));
                                                                    if (codigoRetencion != null) {
                                                                        if (codigoRetencion.trim().equals(codigoRetencionAux)) {
                                                                            existe = true;
                                                                        } else {
                                                                            retorno = "F<html>El Número de Retención que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                                                            existe = false;
                                                                        }

                                                                    } else {
                                                                        existe = true;
                                                                    }
                                                                } else {
                                                                    existe = true;
                                                                }
                                                            }
                                                        } else {
                                                            existe = true;
                                                        }
                                                        if (existe) {
                                                            if (!desmayorizar && !invCompras.getCompAnulado() && !invCompras.getCompPendiente() && invCompras.getCompDocumentoTipo().compareTo("04") == 0) {
                                                                mensajeClase = verificarStockCompras(invCompras, listInvComprasDetalle);
                                                            }

                                                            List<SisSuceso> listaSisSuceso = new ArrayList<>();

                                                            if (mensajeClase.isEmpty()) {
                                                                ConContable conContable = contableService.obtenerPorId(invCompras.getConEmpresa(), invCompras.getConPeriodo(), invCompras.getConTipo(), invCompras.getConNumero());
                                                                if (invCompras.getCompAnulado()) {
                                                                    if (conContable != null) {
                                                                        conContable.setConAnulado(true);
                                                                        susSuceso = "DELETE";
                                                                        sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                                                        listaSisSuceso.add(sisSuceso);
                                                                        //NUEVO SUCESO PARA CONTABLE
                                                                        SisSuceso sisSucesoContable = Suceso.crearSisSuceso(
                                                                                "contabilidad.con_contable",
                                                                                conContable.getConContablePK().getConPeriodo()
                                                                                + " " + conContable.getConContablePK().getConTipo()
                                                                                + " " + conContable.getConContablePK().getConNumero(),
                                                                                "DELETE",
                                                                                "Se anuló el contable del periodo "
                                                                                + conContable.getConContablePK().getConPeriodo()
                                                                                + ", del tipo "
                                                                                + conContable.getConContablePK().getConTipo()
                                                                                + ", de la numeración "
                                                                                + conContable.getConContablePK().getConNumero(),
                                                                                sisInfoTO);
                                                                        listaSisSuceso.add(sisSucesoContable);
                                                                        sisSuceso = null;
                                                                    }
                                                                } else if (desmayorizar) {
                                                                    if (conContable != null) {
                                                                        conContable.setConPendiente(true);
                                                                    }
                                                                } else {
                                                                    if (conContable != null) {
                                                                        conContable.setConPendiente(false);
                                                                    }
                                                                }

                                                                if (quitarMotivoAnulacion) {
                                                                    if (conContable != null) {
                                                                        conContable.setConAnulado(false);
                                                                        susSuceso = "RESTORE";
                                                                        sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                                                        listaSisSuceso.add(sisSuceso);
                                                                        //NUEVO SUCESO PARA CONTABLE
                                                                        SisSuceso sisSucesoContable = Suceso
                                                                                .crearSisSuceso(
                                                                                        "contabilidad.con_contable",
                                                                                        conContable.getConContablePK().getConPeriodo()
                                                                                        + " " + conContable.getConContablePK().getConTipo()
                                                                                        + " " + conContable.getConContablePK().getConNumero(),
                                                                                        "RESTORE",
                                                                                        "Se restauró el contable del periodo "
                                                                                        + conContable.getConContablePK().getConPeriodo()
                                                                                        + ", del tipo "
                                                                                        + conContable.getConContablePK().getConTipo()
                                                                                        + ", de la numeración "
                                                                                        + conContable.getConContablePK().getConNumero(),
                                                                                        sisInfoTO);
                                                                        listaSisSuceso.add(sisSucesoContable);
                                                                        sisSuceso = null;
                                                                    }
                                                                }

                                                                if (invCompras.getCompAnulado()) {
                                                                    invComprasMotivoAnulacion.setInvCompras(invCompras);
                                                                }

                                                                if (invCompras.getCompDocumentoTipo().equals("00") || invCompras.getCompDocumentoTipo().equals("19")) {
                                                                    if (invCompras.getCompDocumentoNumero() != null && invCompras.getCompDocumentoNumero().equals("999-999-999999999")) {
                                                                        invCompras.setCompDocumentoNumero(null);
                                                                    }
                                                                }

                                                                //DETALLE IMB COMPRAS
                                                                List<InvComprasDetalleImb> listInvComprasDetalleImb = new ArrayList<>();
                                                                for (int i = 0; i < listaCompraImb.size(); i++) {
                                                                    InvComprasDetalleImb imb = ConversionesInventario.convertirInvComprasDetalleImbTO_InvComprasDetalleImb(listaCompraImb.get(i));
                                                                    imb.setInvProveedorImb(invProveedor);
                                                                    imb.setInvCompras(invCompras);
                                                                    listInvComprasDetalleImb.add(imb);
                                                                }

                                                                //DETALLE IMB COMPRAS A ELIMINAR
                                                                List<InvComprasDetalleImb> listaImbEliminar = new ArrayList<>();
                                                                if (listaCompraImbEliminar != null) {
                                                                    for (InvComprasDetalleImbTO invComprasDetalleImbTO : listaCompraImbEliminar) {
                                                                        InvComprasDetalleImb imb = ConversionesInventario.convertirInvComprasDetalleImbTO_InvComprasDetalleImb(invComprasDetalleImbTO);
                                                                        imb.setInvCompras(invCompras);
                                                                        imb.setInvProveedorImb(invProveedor);
                                                                        listaImbEliminar.add(imb);
                                                                    }
                                                                }

                                                                //LIQUIDACION COMPRAS
                                                                List<InvComprasLiquidacion> listInvComprasLiquidacion = new ArrayList<>();
                                                                for (int i = 0; i < listaCompraLiquidacionTO.size(); i++) {
                                                                    InvComprasLiquidacion liqComp = ConversionesInventario.convertirInvComprasLiquidacionTO_InvComprasLiquidacion(listaCompraLiquidacionTO.get(i));
                                                                    liqComp.setInvCompras(invCompras);
                                                                    listInvComprasLiquidacion.add(liqComp);
                                                                }

                                                                List<InvComprasLiquidacion> listInvComprasLiquidacionEliminar = new ArrayList<>();
                                                                if (listInvComprasLiquidacionTOEliminar != null) {
                                                                    for (InvComprasLiquidacionTO item : listInvComprasLiquidacionTOEliminar) {
                                                                        InvComprasLiquidacion liqCompElim = ConversionesInventario.convertirInvComprasLiquidacionTO_InvComprasLiquidacion(item);
                                                                        liqCompElim.setInvCompras(invCompras);
                                                                        listInvComprasLiquidacionEliminar.add(liqCompElim);
                                                                    }
                                                                }

                                                                if (invCompras.getSecCodigo() == null || invCompras.getSecCodigo().equals("")) {
                                                                    invCompras.setSecCodigo(listInvComprasDetalle.get(0).getInvBodega().getSecCodigo());
                                                                    invCompras.setSecEmpresa(empresa);
                                                                }

                                                                comprobar = comprasDao.modificarInvComprasInventario(invCompras,
                                                                        (desmayorizar ? null : listInvComprasDetalle),
                                                                        listInvComprasDetalleEliminar, anxCompra,
                                                                        anxCompraDetalles, anxCompraDetallesEliminar,
                                                                        anxCompraDividendos, anxCompraReembolsos,
                                                                        anxCompraReembolsosEliminar,
                                                                        anxCompraFormaPagos,
                                                                        anxCompraFormaPagosEliminar, sisSuceso,
                                                                        listaSisSuceso, conContable,
                                                                        invComprasMotivoAnulacion,
                                                                        eliminarMotivoAnulacion, desmayorizar, listaImagenes, listInvComprasDetalleImb, listaImbEliminar,
                                                                        listInvComprasLiquidacion, listInvComprasLiquidacionEliminar);

                                                                if (comprobar) {

                                                                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(invCompras.getEmpCodigo(), invCompras.getInvComprasPK().getCompPeriodo());
                                                                    retorno = "T<html>Se " + estado
                                                                            + " la compra con la siguiente información:<br><br>"
                                                                            + "Periodo: <font size = 5>"
                                                                            + sisPeriodo.getPerDetalle()
                                                                            + "</font>.<br> Motivo: <font size = 5>"
                                                                            + invCompras.getInvComprasPK().getCompMotivo()
                                                                            + "</font>.<br> Número: <font size = 5>"
                                                                            + invCompras.getInvComprasPK().getCompNumero()
                                                                            + "</font>.</html>"
                                                                            + invCompras.getInvComprasPK().getCompNumero()
                                                                            + "," + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                                                } else {
                                                                    retorno = "F<html>Hubo un error al modificar la Compra...\nIntente de nuevo o contacte con el administrador</html>";
                                                                }
                                                            } else {
                                                                retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                                                                mensajeTO.setListaErrores1(mensajeClase);
                                                            }

                                                        } else {
                                                            retorno = "F<html>El Número de Retención que ingresó ya existe.<br>Intente de nuevo o contacte con el administrador</html>";
                                                        }
                                                    } else {
                                                        retorno = "F<html>El Número de comprobante que ingresó ya existe...<br>Intente de nuevo o contacte con el administrador</html>";
                                                    }

                                                } else if (estadoError == 1) {
                                                    retorno = "F<html>El Concepto de Retención " + nombreConcepto + " que ha escogido no se encuentra disponible.<br>Contacte con el administrador.</html>";
                                                } else {
                                                    retorno = "F<html>El Concepto de Retención " + nombreConcepto + " que ha escogido no se encuentra disponible.<br>Contacte con el administrador.</html>";
                                                }
                                            } else if (estadoDetalleEliminar == 1) {
                                                retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                            } else {
                                                retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                            }
                                        } else if (estadoDetalle == 1) {
                                            retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                        } else if (estadoDetalle == 2) {
                                            retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                        } else {
                                            retorno = estadoDetalleComplemento;
                                        }
                                    } else {
                                        retorno = "F<html>" + detalleError + "</html>";
                                    }
                                } else {
                                    retorno = "F<html>El Proveedor que escogió ya no está disponible...\nIntente de nuevo, escoja otro Proveedor o contacte con el administrador</html>";
                                }
                            } else if (validacionModificar) {
                                retorno = "F<html>La compra que quiere modificar ya no se encuentra disponible.</html>";
                            } else {
                                retorno = "F<html>La compra que desea restaurar NO existe o NO está anulada</html>";
                            }
                        } else {
                            retorno = "F<html>No se encuentra el Motivo...</html>";
                        }
                    } else {
                        retorno = "F<html>El NÚMERO DE RETENCIÓN que ingresó se encuentra ANULADO o no está REGISTRADA la secuencia</html>";
                    }
                } else {
                    retorno = "F<html>No se puede MAYORIZAR, DESMAYORIZAR o ANULAR debido a que el periodo se encuentra cerrado</html>";
                }
            } else {
                retorno = "F <html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }

        mensajeTO.setMensaje(retorno);

        campos.put("mensaje", mensajeTO.getMensaje());
        campos.put("invCompraTO", invCompraTO);

        return campos;

    }

    @Override
    public boolean isFechaDentroDeDiasHabiles(String fechaRetencion, String fechaCompra, String empresa) {
        boolean isFechaValida = true;
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class,
                empresa);
        List<String> rucs = Arrays.asList("0992408855001", "0993036366001", "0791765196001", "0702613423001", "0791728495001", "0993197475001");
        boolean quitarValidacion = rucs.contains(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
        if (!quitarValidacion) {
            Date fechaDeRetencion = UtilsDate.DeStringADate(fechaRetencion);
            Date fechaMinimaRetencion = UtilsDate.DeStringADate(fechaCompra);
            Date fechaMaximaRetencion = UtilsDate.fechaFormatoDate(UtilsValidacion.fechaSumarDias(UtilsDate.fechaFormatoDate(fechaCompra, "yyyy-MM-dd"), 5), "yyyy-MM-dd");
            if ((fechaMinimaRetencion.equals(fechaDeRetencion) || fechaMaximaRetencion.equals(fechaDeRetencion)) || (fechaDeRetencion.after(fechaMinimaRetencion) && fechaDeRetencion.before(fechaMaximaRetencion))) {
                isFechaValida = true;
            } else {
                isFechaValida = false;
            }
        }

        return isFechaValida;
    }

    @Override
    public List<AnxCompraDetalleRetencionTO> obtenerDetalleRetencion(String empresa, String fecha, List<InvComprasDetalleTO> listaInvComprasDetalleTO, boolean esNueva) throws Exception {
        List<InvListaProductosCompraSustentoConceptoTO> anxInvProductoSustentoConceptoTO = new ArrayList();
        BigDecimal sumaDeIce = BigDecimal.ZERO;
        if (listaInvComprasDetalleTO != null && !listaInvComprasDetalleTO.isEmpty()) {
            for (InvComprasDetalleTO item : listaInvComprasDetalleTO) {
                InvListaProductosCompraSustentoConceptoTO invListaProductosCompraTO = new InvListaProductosCompraSustentoConceptoTO();
                invListaProductosCompraTO.setProCodigoPrincipal(item.getProCodigoPrincipal());
                invListaProductosCompraTO.setSubtotal(item.getDetPrecio().multiply(item.getDetCantidad()));//.subtract(item.getDetIce())
                if (item.getDetIce() != null) {
                    sumaDeIce = sumaDeIce.add(item.getDetIce());
                }
                anxInvProductoSustentoConceptoTO.add(invListaProductosCompraTO);
            }
        }
        List<InvListaProductosCompraSustentoConceptoTO> listaProductosCompraSustentoConceptoTO = productoService.getInvProductoSustentoConcepto(empresa, fecha, anxInvProductoSustentoConceptoTO);
        //Ver si hay un sustento 332 sino crear
        InvListaProductosCompraSustentoConceptoTO sustento332 = new InvListaProductosCompraSustentoConceptoTO();
        if (esNueva && listaProductosCompraSustentoConceptoTO != null && sumaDeIce != null && sumaDeIce.compareTo(cero) == 1) {
            Optional<InvListaProductosCompraSustentoConceptoTO> existente332 = listaProductosCompraSustentoConceptoTO.stream()
                    .filter(s -> s.getConCodigo() != null && s.getConCodigo().equals("332"))
                    .findFirst();
            if (existente332 != null && existente332.isPresent()) {
                sustento332 = existente332.get();
                sustento332.setBaseImponible(sustento332.getBaseImponible().add(sumaDeIce));
            } else {
                sustento332.setConCodigo("332");
                sustento332.setBaseImponible(sumaDeIce);
                listaProductosCompraSustentoConceptoTO.add(sustento332);
            }
        }

        List<AnxCompraDetalleRetencionTO> listaAnxCompraDetalleRetencionTO = new ArrayList<>();
        List<AnxConceptoComboTO> listaConceptos = conceptoService.getListaAnxConceptoComboTO(fecha);
        for (InvListaProductosCompraSustentoConceptoTO item : listaProductosCompraSustentoConceptoTO) {
            AnxCompraDetalleRetencionTO anxCompraDetalleRetencionTO = new AnxCompraDetalleRetencionTO();
            anxCompraDetalleRetencionTO.setCompEmpresa(empresa);
            anxCompraDetalleRetencionTO.setDetBase0(item.getBase0() == null ? BigDecimal.ZERO : item.getBase0());
            anxCompraDetalleRetencionTO.setDetConcepto(item.getConCodigo());
            anxCompraDetalleRetencionTO.setConCodigoCopia(item.getConCodigo());
            if (anxCompraDetalleRetencionTO.getDetConcepto() != null) {
                for (AnxConceptoComboTO itemConceptos : listaConceptos) {
                    if (itemConceptos.getConCodigo().equalsIgnoreCase(anxCompraDetalleRetencionTO.getDetConcepto())) {
                        anxCompraDetalleRetencionTO.setNombreConcepto(itemConceptos.getConConcepto());
                    }
                }
            }
            anxCompraDetalleRetencionTO.setDetBaseImponible(item.getBaseImponible() == null ? BigDecimal.ZERO : item.getBaseImponible());
            anxCompraDetalleRetencionTO.setDetBaseNoObjetoIva(item.getDetBaseNoObjetoIva() == null ? BigDecimal.ZERO : item.getDetBaseNoObjetoIva());
            anxCompraDetalleRetencionTO.setDetPorcentaje(item.getDetPorcentaje() == null ? BigDecimal.ZERO : item.getDetPorcentaje());
            anxCompraDetalleRetencionTO.setDetValorRetenido(item.getDetValorRetenido() == null ? BigDecimal.ZERO : item.getDetValorRetenido());
            anxCompraDetalleRetencionTO.setDivIrAsociado(BigDecimal.ZERO);
            item.getSusCodigo();
            listaAnxCompraDetalleRetencionTO.add(anxCompraDetalleRetencionTO);
        }
        listaAnxCompraDetalleRetencionTO.remove(0);
        return listaAnxCompraDetalleRetencionTO;
    }

    public List<AnxCompraDetalleRetencionTO> convertirListaAnxCompraDetalleTOAListaAnxCompraDetalleRetencionTO(List<AnxCompraDetalleTO> listaAnxCompra, AnxCompraTO anxCompraTO, String comFecha) throws Exception {
        List<AnxCompraDetalleRetencionTO> listaAnxCompraDetalleRetencionTO = new ArrayList<>();
        for (AnxCompraDetalleTO item : listaAnxCompra) {
            AnxCompraDetalleRetencionTO anxCompraDetalleRetencionTO = new AnxCompraDetalleRetencionTO();
            anxCompraDetalleRetencionTO.setCompEmpresa(item.getCompEmpresa());
            anxCompraDetalleRetencionTO.setCompMotivo(item.getCompMotivo());
            anxCompraDetalleRetencionTO.setCompNumero(item.getCompNumero());
            anxCompraDetalleRetencionTO.setCompPeriodo(item.getCompPeriodo());
            anxCompraDetalleRetencionTO.setDetBase0(item.getDetBase0());
            anxCompraDetalleRetencionTO.setDetBaseImponible(item.getDetBaseImponible());
            anxCompraDetalleRetencionTO.setDetBaseNoObjetoIva(item.getDetBaseNoObjetoIva());
            anxCompraDetalleRetencionTO.setDetConcepto(item.getDetConcepto());
            anxCompraDetalleRetencionTO.setConCodigoCopia(item.getDetConcepto());
            anxCompraDetalleRetencionTO.setDetOrden(item.getDetOrden());
            anxCompraDetalleRetencionTO.setDetPorcentaje(item.getDetPorcentaje());
            anxCompraDetalleRetencionTO.setDetSecuencial(item.getDetSecuencial());
            anxCompraDetalleRetencionTO.setDetValorRetenido(item.getDetValorRetenido());
            anxCompraDetalleRetencionTO.setDivAnioUtilidades(item.getDivAnioUtilidades());
            anxCompraDetalleRetencionTO.setDivFechaPago(item.getDivFechaPago() == null ? "" : item.getDivFechaPago().isEmpty() ? "" : item.getDivFechaPago());
            anxCompraDetalleRetencionTO.setDivIrAsociado(item.getDivIrAsociado());
            anxCompraDetalleRetencionTO.setDivSecuencial(item.getDetSecuencial());
            for (AnxConceptoComboTO itemConcepto : conceptoService.getListaAnxConceptoTO(comFecha, item.getDetConcepto())) {
                if (itemConcepto.getConCodigo().equalsIgnoreCase(item.getDetConcepto())) {
                    anxCompraDetalleRetencionTO.setNombreConcepto(itemConcepto.getConConcepto());
                    anxCompraDetalleRetencionTO.setConIngresaPorcentaje(itemConcepto.getConIngresaPorcentaje().equals('S') ? true : false);
                    break;
                }
            }

            listaAnxCompraDetalleRetencionTO.add(anxCompraDetalleRetencionTO);
        }
        return listaAnxCompraDetalleRetencionTO;
    }

    @Override
    public String obtenerCodigoSustento(String empresa, String fecha, List<InvComprasDetalleTO> listaInvComprasDetalleTO) throws Exception {
        List<InvListaProductosCompraSustentoConceptoTO> anxInvProductoSustentoConceptoTO = new ArrayList();
        for (InvComprasDetalleTO item : listaInvComprasDetalleTO) {
            InvListaProductosCompraSustentoConceptoTO invListaProductosCompraTO = new InvListaProductosCompraSustentoConceptoTO();
            invListaProductosCompraTO.setProCodigoPrincipal(item.getProCodigoPrincipal());
            invListaProductosCompraTO.setSubtotal(item.getDetPrecio().multiply(item.getDetCantidad()).subtract(item.getDetIce()));
            anxInvProductoSustentoConceptoTO.add(invListaProductosCompraTO);
        }
        List<InvListaProductosCompraSustentoConceptoTO> listaProductosCompraSustentoConceptoTO
                = productoService.getInvProductoSustentoConcepto(empresa, fecha, anxInvProductoSustentoConceptoTO);
        for (InvListaProductosCompraSustentoConceptoTO item : listaProductosCompraSustentoConceptoTO) {
            return item.getSusCodigo();
        }
        return "";
    }

    public InvListaProductosCompraSustentoConceptoTO obtenerSustento(String empresa, String fecha, List<InvComprasDetalleTO> listaInvComprasDetalleTO) throws Exception {
        List<InvListaProductosCompraSustentoConceptoTO> anxInvProductoSustentoConceptoTO = new ArrayList();
        for (InvComprasDetalleTO item : listaInvComprasDetalleTO) {
            InvListaProductosCompraSustentoConceptoTO invListaProductosCompraTO = new InvListaProductosCompraSustentoConceptoTO();
            invListaProductosCompraTO.setProCodigoPrincipal(item.getProCodigoPrincipal());
            invListaProductosCompraTO.setSubtotal(item.getDetPrecio().multiply(item.getDetCantidad()).subtract(item.getDetIce()));
            anxInvProductoSustentoConceptoTO.add(invListaProductosCompraTO);
        }
        List<InvListaProductosCompraSustentoConceptoTO> listaProductosCompraSustentoConceptoTO
                = productoService.getInvProductoSustentoConcepto(empresa, fecha, anxInvProductoSustentoConceptoTO);
        for (InvListaProductosCompraSustentoConceptoTO item : listaProductosCompraSustentoConceptoTO) {
            return item;
        }
        return null;
    }

    @Override
    public Map<String, Object> contabilizarComprasTrans(String empresa, InvComprasTO invCompraTO, MensajeTO mensajeTO1, SisInfoTO usuario) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("mensaje", "T");
        String mensajeAux = mensajeTO1.getMensaje();
        String mensaje = mensajeAux.substring(0, mensajeAux.lastIndexOf("</html>") + 7);
        if (mensaje.charAt(0) == 'T') {
            mensajeTO1 = insertarInvContableComprasTO(invCompraTO.getEmpCodigo(), invCompraTO.getCompPeriodo(), invCompraTO.getCompMotivo(), invCompraTO.getCompNumero(), usuario.getUsuario(), false, "", false, "", usuario);
            mensajeAux = mensajeTO1.getMensaje();
            mensaje = mensajeAux.substring(0, mensajeAux.lastIndexOf("</html>") + 7);
            if (mensaje.charAt(0) == 'T') {
                campos.put("mensaje", mensaje.substring(0));
            } else if (mensajeTO1.getListaErrores() == null || mensajeTO1.getListaErrores().isEmpty()) {
                campos.put("mensaje", mensaje.substring(0));
            } else {
                String errores = "";
                for (String item : mensajeTO1.getListaErrores1()) {
                    errores = errores + "- " + item + "\n";
                }
                campos.put("mensaje", "F" + errores);
            }
        } else {
            String errores = "";
            for (String item : mensajeTO1.getListaErrores1()) {
                errores = errores + "- " + item + "\n";
            }
            campos.put("mensaje", "F" + errores);
        }
        campos.put("numeroContable", mensajeTO1.getContable());
        return campos;
    }

    @Override
    public Map<String, Object> reContabilizarComprasTrans(String empresa, InvComprasTO invCompraTO, MensajeTO mensajeTO1, SisInfoTO usuario) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("mensaje", "F");
        String mensajeAux = mensajeTO1.getMensaje();

        String mensaje = mensajeAux.substring(0, mensajeAux.lastIndexOf("</html>") + 7);
        if (mensaje.charAt(0) == 'T') {
            mensajeTO1 = insertarInvContableComprasTO(
                    invCompraTO.getEmpCodigo(),
                    invCompraTO.getCompPeriodo(),
                    invCompraTO.getCompMotivo(),
                    invCompraTO.getCompNumero(),
                    usuario.getUsuario(),
                    true,
                    invCompraTO.getContNumero(),
                    true,
                    invCompraTO.getContTipo(),
                    usuario);
            mensajeAux = mensajeTO1.getMensaje();
            mensaje = mensajeAux.substring(0, mensajeAux.lastIndexOf("</html>") + 7);
            if (mensaje.charAt(0) == 'T') {
                campos.put("mensaje", mensaje.substring(0));

            } else if (mensajeTO1.getListaErrores().isEmpty()) {
                campos.put("mensaje", mensaje.substring(0));
            } else {
                String errores = "";
                for (String item : mensajeTO1.getListaErrores1()) {
                    errores = errores + "- " + item + "\n";
                }
                campos.put("mensaje", "F" + errores);
            }

        } else {
            String errores = "";
            for (String item : mensajeTO1.getListaErrores1()) {
                errores = errores + "- " + item + "\n";
            }
            campos.put("mensaje", "F" + errores);
        }

        campos.put("numeroContable", mensajeTO1.getContable());
        return campos;
    }

    @Override
    public Map<String, Object> consultarRetencionCompra(Map<String, Object> datos) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                datos.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class,
                datos.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                datos.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class,
                datos.get("numero"));
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class,
                datos.get("usuarioCodigo"));
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class,
                datos.get("listInvComprasDetalleTO"));
        InvCompraCabeceraTO invCompraCabeceraTO = getInvCompraCabeceraTO(empresa, periodo, motivo, numero);
        InvComprasTO invComprasTO = convertirInvComprasTO(invCompraCabeceraTO);
        InvProveedor proveedor = proveedorDao.buscarInvProveedor(empresa, invComprasTO.getProvCodigo());
        invComprasTO.setCompNumero(numero);

        List<AnxFormaPagoTO> listAnxFormaPagoTO = new ArrayList<>();
        List<AnxSustentoComboTO> listAnxSustentoComboTO = new ArrayList<>();
        List<AnxCompraDetalleRetencionTO> listAnxCompraDetalleRetencionTO = new ArrayList<>();
        List<AnxCompraDetalleTO> listaAnxCompra = new ArrayList<>();
        List<AnxCompraFormaPagoTO> formaPagoRetencion = new ArrayList<>();
        List<AnxTipoComprobanteComboTO> listaDocumentos = new ArrayList<>();
        List<AnxTipoComprobanteComboTO> listaDocumentosReembolso = new ArrayList<>();
        List<AnxCompraReembolsoTO> listaAnxCompraReembolsoTO = new ArrayList<>();

        AnxCompraTO anxCompraTO = compraDao.getAnexoCompraTO(empresa, periodo, motivo, numero);
        CajCajaTO caja = null;

        /*comprobante electronico*/
        boolean comprobarRetencionAutorizadaProcesamiento = compraElectronicaService.comprobarRetencionAutorizadaProcesamiento(empresa, periodo, motivo, numero);

        if (anxCompraTO != null) {
            caja = cajaDao.getCajCajaTO(empresa, usuarioCodigo);
            listAnxFormaPagoTO = formaPagoAnexoService.getAnexoFormaPagoTO(UtilsDate.fechaFormatoDate(invCompraCabeceraTO.getCompfecha(), "yyyy-MM-dd"));
            listAnxSustentoComboTO = sustentoService.getListaAnxSustentoComboTO(invCompraCabeceraTO.getCompDocumentoTipo());

            formaPagoRetencion = compraFormaPagoService.getAnexoCompraFormaPagoTO(empresa, periodo, motivo, numero);
            listaAnxCompra = compraDetalleDao.getAnexoCompraDetalleTO(empresa, periodo, motivo, numero);
            listAnxCompraDetalleRetencionTO = convertirListaAnxCompraDetalleTOAListaAnxCompraDetalleRetencionTO(listaAnxCompra, anxCompraTO, invCompraCabeceraTO.getCompfecha());

            if (listAnxCompraDetalleRetencionTO != null && listAnxCompraDetalleRetencionTO.size() == 0) {
                listAnxCompraDetalleRetencionTO = obtenerDetalleRetencion(empresa, invComprasTO.getCompFecha(), listaInvComprasDetalleTO, false);
                if (invCompraCabeceraTO.getCompDocumentoTipo().equals("04")) {
                    listAnxCompraDetalleRetencionTO = new ArrayList<>();
                    AnxCompraDetalleRetencionTO detalleConcepto = new AnxCompraDetalleRetencionTO();
                    AnxConceptoTO concepto = conceptoService.getBuscarAnexoConceptoTO(anxCompraTO.getCompRetencionFechaEmision(), "332");
                    detalleConcepto.setConCodigoCopia(concepto.getConCodigo());
                    detalleConcepto.setDetConcepto(concepto.getConCodigo());
                    List<AnxConceptoComboTO> listaConceptos = conceptoService.getListaAnxConceptoComboTO(invComprasTO.getCompFecha());
                    for (AnxConceptoComboTO itemConceptos : listaConceptos) {
                        if (itemConceptos.getConCodigo().equalsIgnoreCase(detalleConcepto.getDetConcepto())) {
                            detalleConcepto.setNombreConcepto(itemConceptos.getConConcepto());
                        }
                    }
                    detalleConcepto.setDetBase0(cero);
                    detalleConcepto.setDetBaseImponible(cero);
                    detalleConcepto.setDetBaseNoObjetoIva(cero);
                    detalleConcepto.setDetPorcentaje(cero);
                    detalleConcepto.setDetValorRetenido(cero);
                    detalleConcepto.setDivIrAsociado(cero);
                    listAnxCompraDetalleRetencionTO.add(detalleConcepto);
                }
            }

            /*NOTA DE CREDITO O DEBITO*/
            if (invCompraCabeceraTO.getCompDocumentoTipo() != null && (invCompraCabeceraTO.getCompDocumentoTipo().equals("04") || invCompraCabeceraTO.getCompDocumentoTipo().equals("05"))) {
                /*Proveedor*/
                InvProveedorTO proveedorTO = proveedorService.getProveedorTO(empresa, invCompraCabeceraTO.getProvCodigo()).get(0);
                String codigoTipoTransaccion = tipoTransaccionService.getCodigoAnxTipoTransaccionTO(proveedorTO.getProvTipoId(), "COMPRA");
                /*Documentos*/
                listaDocumentos = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
            }
            /*REEMBOLSO*/
            if (invCompraCabeceraTO.getCompDocumentoTipo() != null && invCompraCabeceraTO.getCompDocumentoTipo().equals("41")) {
                /*Documentos*/
                listaDocumentosReembolso = tipoComprobanteService.getListaAnxTipoComprobanteComboTOCompleto();

            }
        }
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();

        campos.put("caja", caja);
        campos.put("fechaActual", fechaActual);
        campos.put("invComprasTO", invComprasTO);
        campos.put("anxCompraTO", anxCompraTO);
        campos.put("formaPagoRetencion", formaPagoRetencion);
        campos.put("listAnxFormaPagoTO", listAnxFormaPagoTO);
        campos.put("listAnxSustentoComboTO", listAnxSustentoComboTO);
        campos.put("listaDocumentos", listaDocumentos);
        campos.put("listaDocumentosReembolso", listaDocumentosReembolso);
        campos.put("listaAnxCompra", listaAnxCompra);
        campos.put("comprobarRetencionAutorizadaProcesamiento", comprobarRetencionAutorizadaProcesamiento);
        campos.put("listAnxCompraDetalleRetencionTO", listAnxCompraDetalleRetencionTO);
        campos.put("listAnxCompraReembolsoTO", listaAnxCompraReembolsoTO);
        campos.put("proveedor", proveedor);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaAnularRetencion(Map<String, Object> datos) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                datos.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class,
                datos.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                datos.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class,
                datos.get("numero"));
        AnxCompraTO anxCompraTO = compraDao.getAnexoCompraTO(empresa, periodo, motivo, numero);

        List<AnxTipoComprobanteComboTO> listaTipoComprobantes = tipoComprobanteService.getListaAnxTipoComprobanteComboTOCompleto();
        List<AnxTipoComprobanteComboTO> listaFinal = new ArrayList<>();
        for (AnxTipoComprobanteComboTO item : listaTipoComprobantes) {
            if (!item.getTcCodigo().trim().equalsIgnoreCase("00")) {
                listaFinal.add(item);
            }
        }
        campos.put("listaComprobante", listaFinal);
        campos.put("anxCompraTO", anxCompraTO);
        return campos;
    }

    @Override
    public Map<String, Object> validarFechaRetencion(Map<String, Object> datos) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String fechaCompraInv = UtilsJSON.jsonToObjeto(String.class,
                datos.get("fechaCompra"));
        String fechaRetencion = UtilsJSON.jsonToObjeto(String.class,
                datos.get("fechaRetencion"));
        String fechaEmision = UtilsJSON.jsonToObjeto(String.class,
                datos.get("fechaEmision"));
        String fechaCaduca = UtilsJSON.jsonToObjeto(String.class,
                datos.get("fechaCaduca"));
        String compAutorizacion = UtilsJSON.jsonToObjeto(String.class,
                datos.get("compAutorizacion"));

        boolean isCompRetencionAsumida = UtilsJSON.jsonToObjeto(boolean.class,
                datos.get("isCompRetencionAsumida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                datos.get("sisInfoTO"));

        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class,
                sisInfoTO.getEmpresa());
        List<String> rucs = Arrays.asList("0992408855001", "0993036366001", "0791765196001", "0702613423001", "0791728495001", "0993197475001");
        boolean quitarValidacion = rucs.contains(sisEmpresaParametros.getEmpCodigo().getEmpRuc());

        String mensaje = "";
        String fechaFormularioCompra = fechaCompraInv;
        Date fechaDeRetencion = UtilsValidacion.fecha(fechaRetencion, "yyyy-MM-dd");
        Date fechaMinimaRetencion = UtilsValidacion.fecha(fechaFormularioCompra, "yyyy-MM-dd");
        Date fechaMaximaRetencion;

        if (isCompRetencionAsumida) {
            fechaMaximaRetencion = UtilsValidacion.fecha(UtilsValidacion.fechaSistema("yyyy-MM-dd"), "yyyy-MM-dd");
        } else if (getDiaDeLaSemana(UtilsValidacion.fechaString_Date(fechaFormularioCompra, "yyyy-MM-dd")) == 2) {
            fechaMaximaRetencion = UtilsValidacion.fecha(UtilsValidacion.fechaSumarDias(java.sql.Date.valueOf(fechaFormularioCompra), 4), "yyyy-MM-dd");
        } else {
            fechaMaximaRetencion = UtilsValidacion.fecha(UtilsValidacion.fechaSumarDias(java.sql.Date.valueOf(fechaFormularioCompra), 6), "yyyy-MM-dd");
        }

        if (((fechaDeRetencion.getTime() < fechaMinimaRetencion.getTime()) || (fechaDeRetencion.getTime() > fechaMaximaRetencion.getTime())) && !quitarValidacion) {
            mensaje = "FLa fecha de la retención no puede ser menor a " + UtilsValidacion.fecha(fechaMinimaRetencion, "dd-MM-yyyy") + " ni mayor a " + UtilsValidacion.fecha(fechaMaximaRetencion, "dd-MM-yyyy");
        }
        // Validar la fecha de la compra, debe estar dentro del rango de emision y caducidad de la autorizacion de la factura
        Date fechaCompra = UtilsValidacion.fecha(fechaFormularioCompra, "yyyy-MM-dd");
        Date fechaEmisionAutorizacion = UtilsValidacion.fecha(fechaEmision, "yyyy-MM-dd");
        Date fechaVencimientoAutorizacion = UtilsValidacion.fecha(fechaCaduca, "yyy-MM-dd");

        boolean laFechaCompraFisicaEsIncorrecta = ((compAutorizacion.length() == 10) && (fechaCompra.getTime() < fechaEmisionAutorizacion.getTime() || fechaCompra.getTime() > fechaVencimientoAutorizacion.getTime()));
        boolean laFechaCompraElectronicaEsIncorrecta = ((compAutorizacion.length() == 37 || compAutorizacion.length() == 49)
                && (fechaEmisionAutorizacion.getTime() != fechaVencimientoAutorizacion.getTime() || fechaEmisionAutorizacion.getTime() < fechaCompra.getTime()));
        if (laFechaCompraFisicaEsIncorrecta || laFechaCompraElectronicaEsIncorrecta) {
            mensaje = "FLa fecha de emisión de la COMPRA :" + fechaFormularioCompra + "no está dentro del rango permitido.";
        }

        mensaje = mensaje.length() == 0 ? "T" : mensaje;

        campos.put("mensaje", mensaje);
        return campos;
    }

    public static int getDiaDeLaSemana(Date d) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public InvComprasTO convertirInvComprasTO(InvCompraCabeceraTO invCompraCabeceraTO) {
        InvComprasTO invComprasTO = new InvComprasTO();
        invComprasTO.setCompActivoFijo(invCompraCabeceraTO.getCompActivoFijo());
        invComprasTO.setCompAnulado(invCompraCabeceraTO.getCompAnulado());
        invComprasTO.setCompBase0(invCompraCabeceraTO.getCompBase0());
        invComprasTO.setCompBaseExenta(invCompraCabeceraTO.getCompBaseExenta());
        invComprasTO.setCompBaseImponible(invCompraCabeceraTO.getCompBaseimponible());
        invComprasTO.setCompBaseNoObjeto(invCompraCabeceraTO.getCompBaseNoObjeto());
        invComprasTO.setCompDocumentoFormaPago(invCompraCabeceraTO.getDocumentoFormaPago());
        invComprasTO.setCompDocumentoNumero(invCompraCabeceraTO.getCompDocumentoNumero());
        invComprasTO.setCompDocumentoTipo(invCompraCabeceraTO.getCompDocumentoTipo());
        invComprasTO.setCompElectronica(invCompraCabeceraTO.getCompElectronica());
        invComprasTO.setCompFecha(invCompraCabeceraTO.getCompfecha());
        invComprasTO.setCompFechaVencimiento(invCompraCabeceraTO.getCompFechaVencimiento());
        invComprasTO.setCompFormaPago(invCompraCabeceraTO.getCompFormaPago());
        invComprasTO.setCompIce(invCompraCabeceraTO.getCompIce());
        invComprasTO.setCompImportacion(invCompraCabeceraTO.getCompImportacion());
        invComprasTO.setCompIvaVigente(invCompraCabeceraTO.getCompIvaVigente());
        invComprasTO.setCompMontoIva(invCompraCabeceraTO.getCompMontoiva());
        invComprasTO.setCompObservaciones(invCompraCabeceraTO.getCompObservaciones());
        invComprasTO.setCompOtrosImpuestos(invCompraCabeceraTO.getCompOtrosImpuestos());
        invComprasTO.setCompPendiente(invCompraCabeceraTO.getCompPendiente());
        invComprasTO.setCompRetencionAsumida(invCompraCabeceraTO.isRetencionAsumida());
        invComprasTO.setCompPropina(invCompraCabeceraTO.getCompPropina());
        invComprasTO.setCompRevisado(invCompraCabeceraTO.getCompRevisado());
        invComprasTO.setCompSaldo(invCompraCabeceraTO.getCompSaldo());
        invComprasTO.setCompTotal(invCompraCabeceraTO.getCompTotal());
        invComprasTO.setCompValorRetenido(invCompraCabeceraTO.getCompValorretenido());
        invComprasTO.setContNumero(invCompraCabeceraTO.getConNumero());
        invComprasTO.setContPeriodo(invCompraCabeceraTO.getConPeriodo());
        invComprasTO.setContTipo(invCompraCabeceraTO.getConTipo());
        invComprasTO.setProvCodigo(invCompraCabeceraTO.getProvCodigo());
        invComprasTO.setSecCodigo(invCompraCabeceraTO.getSecCodigo());
        invComprasTO.setUsrFechaInsertaCompra(invCompraCabeceraTO.getFechaUsuarioInserto());
        invComprasTO.setUsrInsertaCompra(invCompraCabeceraTO.getUsuarioInserto());
        invComprasTO.setCompImbFacturado(invCompraCabeceraTO.isCompImbFacturado());

        return invComprasTO;
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosRetencionNueva(Map<String, Object> datos) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String compDocumentoNumero = UtilsJSON.jsonToObjeto(String.class, datos.get("compDocumentoNumero"));
        String compDocumentoTipo = UtilsJSON.jsonToObjeto(String.class, datos.get("compDocumentoTipo"));
        String compFecha = UtilsJSON.jsonToObjeto(String.class, datos.get("compFecha"));
        String fechaRetencion = UtilsJSON.jsonToObjeto(String.class, datos.get("fechaRetencion"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, datos.get("provCodigo"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, datos.get("empresa"));
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class, datos.get("usuarioCodigo"));
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class, datos.get("listaInvComprasDetalleTO"));
        List<AnxTipoComprobanteComboTO> listaDocumentos = new ArrayList<>();
        List<AnxTipoComprobanteComboTO> listaDocumentosReembolso = new ArrayList<>();

        CajCajaTO caja = cajaDao.getCajCajaTO(empresa, usuarioCodigo);
        AnxUltimaAutorizacionTO anxUltimaAutorizacionTO = null;
        AnxNumeracionLineaTO anxNumeracionLineaTO = null;
        String ultimaNumeracionComprobante = null;
        String codigoTipoDocumento = null;
        String parteEstatica = null;
        int secuenciaNumeroFactura = 0;
        boolean existeUltimoSecuencial = false;
        boolean isFechaDentroDeDiasHabiles = isFechaDentroDeDiasHabiles(fechaRetencion, compFecha, empresa);
        List<AnxCompraDetalleRetencionTO> listAnxCompraDetalleRetencionTO = obtenerDetalleRetencion(empresa, compFecha, listaInvComprasDetalleTO, true);
        InvListaProductosCompraSustentoConceptoTO sustento = obtenerSustento(empresa, compFecha, listaInvComprasDetalleTO);
        String codigoSustento = sustento != null ? sustento.getSusCodigo() : null;
        String creditoTributario = sustento != null ? sustento.getProCreditoTributario() : null;
        List<AnxFormaPagoTO> listAnxFormaPagoTO = formaPagoAnexoService.getAnexoFormaPagoTO(UtilsDate.fechaFormatoDate(compFecha, "yyyy-MM-dd"));
        List<AnxSustentoComboTO> listAnxSustentoComboTO = sustentoService.getListaAnxSustentoComboTOByTipoCredito(compDocumentoTipo, null);
        if (!compDocumentoTipo.equals("19")) {
            anxUltimaAutorizacionTO = compraService.getAnxUltimaAutorizacionTO(empresa, provCodigo, compDocumentoTipo, compDocumentoNumero.substring(0, 7), compFecha);
        }
        if (caja.getPermisoSecuencialRetenciones() != null) {
            ultimaNumeracionComprobante = ventaService.getUltimaNumeracionComprobante(empresa, TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode(), caja.getPermisoSecuencialRetenciones());

            if (ultimaNumeracionComprobante != null) {
                existeUltimoSecuencial = true;
            }
            if (!existeUltimoSecuencial) {
                List<AnxNumeracionTablaTO> listaAnxNumeracionTablaTO = numeracionService.getListaAnexoNumeracionTO(empresa);
                for (AnxNumeracionTablaTO item : listaAnxNumeracionTablaTO) {
                    if (item.getNumeroComprobante().equalsIgnoreCase("07")) {
                        if (item.getNumeroDesde().substring(0, 7).equalsIgnoreCase(caja.getPermisoSecuencialRetenciones())) {
                            int numeroUltimosecuencial = Integer.parseInt(item.getNumeroDesde().substring(8));
                            String textoUltimoSecuencial = "" + numeroUltimosecuencial;
                            int numeroDigitos = (textoUltimoSecuencial).length();
                            for (int i = 0; i < 9 - numeroDigitos; i++) {
                                textoUltimoSecuencial = "0" + textoUltimoSecuencial;
                            }
                            ultimaNumeracionComprobante = caja.getPermisoSecuencialRetenciones() + "-" + textoUltimoSecuencial;
                        }
                    }
                }
            }

            if (ultimaNumeracionComprobante == null || ultimaNumeracionComprobante.isEmpty()) {
                ultimaNumeracionComprobante = caja.getPermisoSecuencialRetenciones() + "-000000000";
            }
            if (!"".equals(ultimaNumeracionComprobante)) {
                String separado = ultimaNumeracionComprobante.substring(0, ultimaNumeracionComprobante.lastIndexOf('-') + 1);
                int numeroUltimosecuencial;
                if (existeUltimoSecuencial) {
                    numeroUltimosecuencial = Integer.parseInt(ultimaNumeracionComprobante.substring(ultimaNumeracionComprobante.lastIndexOf('-') + 2));
                } else {
                    numeroUltimosecuencial = Integer.parseInt(ultimaNumeracionComprobante.substring(8)) - 1;
                }
                String textoUltimoSecuencial = "" + numeroUltimosecuencial;
                int numeroDigitos = (textoUltimoSecuencial).length();
                for (int i = 0; i < 9 - numeroDigitos; i++) {
                    textoUltimoSecuencial = "0" + textoUltimoSecuencial;
                }
                codigoTipoDocumento = TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode();
                parteEstatica = separado;
                secuenciaNumeroFactura = numeroUltimosecuencial;
                anxNumeracionLineaTO = ventaService.getNumeroAutorizacion(empresa, ultimaNumeracionComprobante, TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode(), compFecha);
            }
        }

        /*NOTA DE CREDITO*/
        if (compDocumentoTipo.equals("04")) {
            listAnxCompraDetalleRetencionTO = new ArrayList<>();
            AnxCompraDetalleRetencionTO detalleConcepto = new AnxCompraDetalleRetencionTO();
            AnxConceptoTO concepto = conceptoService.getBuscarAnexoConceptoTO(fechaRetencion, "332");
            detalleConcepto.setConCodigoCopia(concepto.getConCodigo());
            detalleConcepto.setDetConcepto(concepto.getConCodigo());
            List<AnxConceptoComboTO> listaConceptos = conceptoService.getListaAnxConceptoComboTO(compFecha);
            for (AnxConceptoComboTO itemConceptos : listaConceptos) {
                if (itemConceptos.getConCodigo().equalsIgnoreCase(detalleConcepto.getDetConcepto())) {
                    detalleConcepto.setNombreConcepto(itemConceptos.getConConcepto());
                }
            }
            detalleConcepto.setDetBase0(cero);
            detalleConcepto.setDetBaseImponible(cero);
            detalleConcepto.setDetBaseNoObjetoIva(cero);
            detalleConcepto.setDetPorcentaje(cero);
            detalleConcepto.setDetValorRetenido(cero);
            detalleConcepto.setDivIrAsociado(cero);
            listAnxCompraDetalleRetencionTO.add(detalleConcepto);
        }

        /*NOTA DE CREDITO O DEBITO*/
        if (compDocumentoTipo.equals("04") || compDocumentoTipo.equals("05")) {
            /*Proveedor*/
            InvProveedorTO proveedor = proveedorService.getProveedorTO(empresa, provCodigo).get(0);
            String codigoTipoTransaccion = tipoTransaccionService.getCodigoAnxTipoTransaccionTO(proveedor.getProvTipoId(), "COMPRA");
            /*Documentos*/
            listaDocumentos = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
        }
        /*Reembolso*/
        if (compDocumentoTipo.equals("41")) {
            /*Documentos*/
            listaDocumentosReembolso = tipoComprobanteService.getListaAnxTipoComprobanteComboTOCompleto();
        }
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        campos.put("fechaActual", fechaActual);
        campos.put("listaDocumentosReembolso", listaDocumentosReembolso);
        campos.put("listaDocumentos", listaDocumentos);
        campos.put("retencionAutorizacion", anxNumeracionLineaTO != null ? anxNumeracionLineaTO.isNumeroDocumentoElectronico() : null);
        campos.put("isRetencionElectronica", anxNumeracionLineaTO != null ? anxNumeracionLineaTO.isNumeroDocumentoElectronico() : false);
        campos.put("isNumeroAmbienteProduccion", anxNumeracionLineaTO != null ? anxNumeracionLineaTO.isNumeroAmbienteProduccion() : false);
        campos.put("isFechaDentroDeDiasHabiles", isFechaDentroDeDiasHabiles);
        campos.put("codigoSustento", codigoSustento);
        campos.put("codigoTipoDocumento", codigoTipoDocumento);
        campos.put("parteEstatica", parteEstatica);
        campos.put("secuenciaNumeroFactura", secuenciaNumeroFactura);
        campos.put("anxNumeracionLineaTO", anxNumeracionLineaTO);
        campos.put("anxUltimaAutorizacionTO", anxUltimaAutorizacionTO);
        campos.put("ultimaNumeracionComprobante", ultimaNumeracionComprobante);
        campos.put("cajCajaTO", caja);
        campos.put("listAnxCompraDetalleRetencionTO", listAnxCompraDetalleRetencionTO);
        campos.put("listAnxFormaPagoTO", listAnxFormaPagoTO);
        campos.put("listAnxSustentoComboTO", listAnxSustentoComboTO);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosRetencionCreada(Map<String, Object> datos) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        InvComprasTO invComprasTO = UtilsJSON.jsonToObjeto(InvComprasTO.class,
                datos.get("invComprasTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                datos.get("empresa"));

        List<AnxFormaPagoTO> listAnxFormaPagoTO = formaPagoAnexoService.getAnexoFormaPagoTO(UtilsDate.fechaFormatoDate(invComprasTO.getCompFecha(), "yyyy-MM-dd"));
        List<AnxSustentoComboTO> listAnxSustentoComboTO = sustentoService.getListaAnxSustentoComboTO(invComprasTO.getCompDocumentoTipo());
        List<AnxTipoComprobanteComboTO> listaDocumentos = new ArrayList<>();
        List<AnxTipoComprobanteComboTO> listaDocumentosReembolso = new ArrayList<>();

        /*NOTA DE CREDITO O DEBITO*/
        String provCodigo = invComprasTO.getProvCodigo();
        String compDocumentoTipo = invComprasTO.getCompDocumentoTipo();
        if (compDocumentoTipo.equals("04") || compDocumentoTipo.equals("05")) {
            /*Proveedor*/
            InvProveedorTO proveedor = proveedorService.getProveedorTO(empresa, provCodigo).get(0);
            String codigoTipoTransaccion = tipoTransaccionService.getCodigoAnxTipoTransaccionTO(proveedor.getProvTipoId(), "COMPRA");
            /*Documentos*/
            listaDocumentos = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
        }

        /*Reembolso*/
        if (compDocumentoTipo.equals("41")) {
            /*Documentos*/
            listaDocumentosReembolso = tipoComprobanteService.getListaAnxTipoComprobanteComboTOCompleto();
        }
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        campos.put("fechaActual", fechaActual);
        campos.put("listaDocumentosReembolso", listaDocumentosReembolso);
        campos.put("listaDocumentos", listaDocumentos);
        campos.put("listAnxFormaPagoTO", listAnxFormaPagoTO);
        campos.put("listAnxSustentoComboTO", listAnxSustentoComboTO);
        return campos;
    }

    @Override
    public boolean validarFechasDeRetencionYComprasMismoMes(String fechaRetencion, String fechaCompra, SisInfoTO sisInfoTO) throws Exception {
        String mesDesde = fechaCompra.split("-")[1];
        String mesHasta = fechaRetencion.split("-")[1];
        String rucEmpresa = sisInfoTO.getEmpresaRuc();

        if (!mesDesde.equalsIgnoreCase(mesHasta)) {
            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
            rucEmpresa = sisEmpresaParametros.getEmpCodigo().getEmpRuc();
            if (rucEmpresa.equalsIgnoreCase("0190329313001") || rucEmpresa.equalsIgnoreCase("0791726867001")) {// xxx || ruc ICapar
                return false;
            }
        }

        return true;
    }

    @Override
    public Map<String, Object> buscarComprobanteElectronico(String empresa, String clave, RespuestaComprobante respuestaComprobante, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("itemComprobanteElectronico", null);
        String mensaje = "T";
        String conteo = "";
        String tipoDocumento = clave.substring(8, 10);
        String periodo = clave.substring(0, 8);

        if (periodo != null) {
            String anio = periodo.substring(4, 8);
            String mes = periodo.substring(2, 4);
            periodo = anio.concat("-").concat(mes);
        }

        if (clave.length() != 49) {
            mensaje = "FLa longitud de la clave de acceso no es la correcta";
        }
        if (tipoDocumento.equals("06") || tipoDocumento.equals("07")) {
            mensaje = "FLa clave de acceso es de una retención o guía de remisión...";
        } else {
            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            String comDocumentoNumero = clave.substring(24, 27) + "-" + clave.substring(27, 30) + "-" + clave.substring(30, 39);
            InvProveedorTO invProveedorTO = proveedorService.getBuscaCedulaProveedorTO(empresa, clave.substring(10, 23));
            if (invProveedorTO != null) {
                conteo = getConteoNumeroFacturaCompra(empresa, invProveedorTO.getProvCodigo(), tipoDocumento, comDocumentoNumero);
            }
            if (invProveedorTO != null && conteo != null && !conteo.equals("")) {
                mensaje = "FEl Nº " + comDocumentoNumero + " ya fue ocupado para otra compra\n con el proveedor: " + invProveedorTO.getProvRazonSocial() + "\n " + "con RUC : " + invProveedorTO.getProvId() + ".";
            } else {
                //*************************buscar si existe xml en BD comprobante electronico recibido**********************
                String comprobanteXMLSriString = null;
                Autorizacion comprobanteAutorizado = null;
                List<AnxComprobantesElectronicosRecibidos> listaEvaluar = new ArrayList<>();
                boolean buscarXMLenSRI = true;
                AnxComprobantesElectronicosRecibidos comprobante = anxComprobantesElectronicosRecibidosService.obtenerAnxComprobantesElectronicosRecibidos(empresa, periodo, clave);
                if (comprobante != null) {
                    if (comprobante.getComproXml() != null && comprobante.getComproXml().length != 0) {
                        comprobanteXMLSriString = new String((byte[]) comprobante.getComproXml(), "UTF-8");
                        listaEvaluar.add(comprobante);
                        buscarXMLenSRI = false;
                    }
                }

                if (buscarXMLenSRI) {
                    if (respuestaComprobante == null || !existeComprobanteAutorizado(respuestaComprobante)) {
                        mensaje = "FComprobante electrónico no encontrado...";
                    } else {
                        comprobanteAutorizado = obtenerComprobanteAutorizado(respuestaComprobante);
                        if (comprobanteAutorizado != null) {
                            //setear valor a comprobante electronico recibido que se va a guardar en BD
                            comprobante = convertirXMLAAnxComprobantesElectronicosRecibidos(respuestaComprobante, empresa, clave);
                            listaEvaluar.add(comprobante);
                        }
                    }
                }

                //Actualizar o Insertar comprobantes con homologacion y/o retención
                if (listaEvaluar.size() > 0
                        && ((comprobante != null && comprobante.getComproXml() != null && comprobante.getComproXml().length == 0)
                        || (respuestaComprobante != null))) {

                    String respuestaActualizarInsertarComprobante = anxComprobantesElectronicosRecibidosService.insertarComprobantesElectronicosRecibidosLote(listaEvaluar, sisInfoTO);
                    if (respuestaActualizarInsertarComprobante == null || respuestaActualizarInsertarComprobante.equals("") || (respuestaActualizarInsertarComprobante.substring(0, 1) != null && respuestaActualizarInsertarComprobante.substring(0, 1).equals("F"))) {
                        mensaje = "FError al insertar o actualizar comprobante electrónico recibido, homologación y/o retención de venta";
                        if (respuestaActualizarInsertarComprobante != null) {
                            mensaje = respuestaActualizarInsertarComprobante;
                        }
                    }
                }

                //volver a buscar XML que se guardó en la BD
                if (comprobanteXMLSriString == null || mensaje.substring(0, 1).equals("T")) {
                    //buscar xml
                    comprobanteXMLSriString = anxComprobantesElectronicosRecibidosService.obtenerXMLComprobanteElectronico(empresa, periodo, clave);
                }

                if (comprobanteXMLSriString != null && !comprobanteXMLSriString.equals("")) {
                    ItemComprobanteElectronico itemComprobanteElectronico = new ItemComprobanteElectronico();
                    ItemResultadoBusquedaElectronico itemResultadoBusquedaElectronico = new ItemResultadoBusquedaElectronico();

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setNamespaceAware(true);
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(new ByteArrayInputStream(comprobanteXMLSriString.getBytes("UTF-8")));
                    Node contenidoComprobanteXmlNode = doc.getElementsByTagName("comprobante").item(0);

                    if (comprobanteAutorizado == null) {
                        comprobanteAutorizado = new Autorizacion();
                        comprobanteAutorizado.setComprobante(comprobanteXMLSriString);
                        comprobanteAutorizado.setAmbiente("PRODUCCIÓN");
                        //fecha
                        DocumentoReporteRIDE documentoRide = ArchivoUtils.documentoRIDE(doc);
                        String fechaAutorizacion = documentoRide.getFechaAutorizacion();
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(UtilsDate.fechaFormatoDate(fechaAutorizacion, "dd/MM/yyyy hh:mm:ss"));
                        comprobanteAutorizado.setEstado(documentoRide.getEstado());
                        comprobanteAutorizado.setNumeroAutorizacion(documentoRide.getNumeroAutorizacion());
                        comprobanteAutorizado.setFechaAutorizacion(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
                    }

                    itemResultadoBusquedaElectronico = ItemResultadoBusquedaElectronico.convertirAutorizacionEnItemResultadoBusquedaElectronico(comprobanteAutorizado);
                    //provedor xml
                    if (invProveedorTO != null) {
                        itemResultadoBusquedaElectronico.setIdProveedor(invProveedorTO.getProvCodigo());
                        itemResultadoBusquedaElectronico.setProvTipoId(invProveedorTO.getProvTipoId());
                    }
                    itemComprobanteElectronico.setItemBusqueda(itemResultadoBusquedaElectronico);
                    if (itemResultadoBusquedaElectronico != null && !itemResultadoBusquedaElectronico.getRucComprador().equalsIgnoreCase(sisEmpresaParametros.getEmpCodigo().getEmpRuc())) {
                        mensaje = "FRuc: " + itemResultadoBusquedaElectronico.getRucComprador() + " no pertenece a la empresa " + empresa + " , con RUC:" + sisEmpresaParametros.getEmpCodigo().getEmpRuc();
                        itemComprobanteElectronico = null;
                    } else {
                        InvProveedorDatosXMLTO invProveedorDatosXMLTO = new InvProveedorDatosXMLTO();
                        invProveedorDatosXMLTO.setRazonSocial(itemResultadoBusquedaElectronico.getNombre());
                        invProveedorDatosXMLTO.setRuc(itemResultadoBusquedaElectronico.getRucProveedor());
                        invProveedorDatosXMLTO.setDirMatriz(comprobanteAutorizado.getComprobante().substring(comprobanteAutorizado.getComprobante().lastIndexOf("<dirMatriz>") + 11, comprobanteAutorizado.getComprobante().lastIndexOf("</dirMatriz>")).trim());
                        invProveedorDatosXMLTO.setNumeroDocumento(itemResultadoBusquedaElectronico.getNumeroDocumento());
                        invProveedorDatosXMLTO.setFechaEmision(itemResultadoBusquedaElectronico.getFechaEmision());
                        invProveedorDatosXMLTO.setNumeroAutorizacion(itemResultadoBusquedaElectronico.getNumeroAutorizacion());
                        invProveedorDatosXMLTO.setFechaAutorizacion(itemResultadoBusquedaElectronico.getFechaAutorizacion());
                        invProveedorDatosXMLTO.setCodDoc(comprobanteAutorizado.getComprobante().substring(comprobanteAutorizado.getComprobante().lastIndexOf("<codDoc>") + 8, comprobanteAutorizado.getComprobante().lastIndexOf("</codDoc>")).trim());
                        invProveedorDatosXMLTO.setTotalComprobante(itemResultadoBusquedaElectronico.getTotalComprobante());
                        itemComprobanteElectronico.setInvProveedorDatosXMLTO(invProveedorDatosXMLTO);
                        mensaje = "T";
                    }
                    campos.put("itemComprobanteElectronico", itemComprobanteElectronico);

                    if (tipoDocumento.compareTo("01") == 0 && mensaje.substring(0, 1).equals("T") && contenidoComprobanteXmlNode != null) {

                        FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXmlNode));
                        List<InvComprasDetalleTO> listaDetalleCompras = obtenerListaDetalleCompra(facturaReporte, empresa);
                        if (listaDetalleCompras != null && listaDetalleCompras.size() > 0) {
                            campos.put("listaDetalleCompras", listaDetalleCompras);
                        }
                    }
                }
                //**********************************************************************************************
            }
        }

        campos.put("mensaje", mensaje);

        return campos;
    }

    public boolean existeComprobanteAutorizado(RespuestaComprobante respuestaComprobante) {
        for (Autorizacion autorizacion : respuestaComprobante.getAutorizaciones().getAutorizacion()) {
            if (autorizacion.getEstado().equalsIgnoreCase("AUTORIZADO")) {
                return true;
            }
        }
        return false;
    }

    public Autorizacion obtenerComprobanteAutorizado(RespuestaComprobante respuestaComprobante) {
        if (respuestaComprobante != null) {
            for (Autorizacion autorizacion : respuestaComprobante.getAutorizaciones().getAutorizacion()) {
                if (autorizacion.getEstado().equalsIgnoreCase("AUTORIZADO")) {
                    return autorizacion;
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> compararCombrobanteElectronico(String empresa, String clave, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String mensaje = "";
        campos.put("estado", false);
        ItemResultadoBusquedaElectronico itemResultadoBusquedaElectronico;
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(clave, TipoAmbienteEnum.PRODUCCION.getCode(), sisInfoTO);
        Autorizacion comprobanteAutorizado = obtenerComprobanteAutorizado(respuestaComprobante);
        if (comprobanteAutorizado != null) {
            itemResultadoBusquedaElectronico = ItemResultadoBusquedaElectronico.convertirAutorizacionEnItemResultadoBusquedaElectronico(comprobanteAutorizado);
            if (itemResultadoBusquedaElectronico.getRucComprador().length() != sisEmpresaParametros.getEmpCodigo().getEmpRuc().length()) {
                mensaje = "TRuc: " + itemResultadoBusquedaElectronico.getRucComprador() + " La compra fue emitida con cedula ";
                campos.put("estado", true);
            } else {
                mensaje = "T";
            }
        }
        campos.put("mensaje", mensaje);
        campos.put("respuestaComprobante", respuestaComprobante);
        return campos;
    }

    @Override
    public String desmayorizarLoteCompra(List<InvListaConsultaCompraTO> listado, SisInfoTO sisInfoTO) throws Exception {
        String respuesta;
        StringBuilder mensaje = new StringBuilder();
        for (InvListaConsultaCompraTO compra : listado) {
            if (compra.getCompStatus().equalsIgnoreCase("ANULADO") || compra.getCompStatus().equalsIgnoreCase("PENDIENTE")) {
                mensaje.append("\n").append("No se puede Desmayorizar la compra ").append(compra.getCompNumero()).append(" ya ha sido ANULADA o está PENDIENTE");
            } else {
                String[] numero = compra.getCompNumero().split("\\|");
                InvComprasPK pk = new InvComprasPK(sisInfoTO.getEmpresa(), numero[0], numero[1], numero[2]);
                String cadena = mayorizarDesmayorizarComprasSql(pk, true, false, sisInfoTO) + "./";
                mensaje.append(cadena.substring(0, cadena.length()));
                if (cadena.charAt(0) == 'T') {
                    compra.setCompStatus("PENDIENTE");
                }
            }
        }

        respuesta = mensaje.toString();

        return respuesta;
    }

    @Override
    public Map<String, Object> suprimirCompra(InvComprasTO invComprasTO, SisInfoTO sisInfoTO) throws Exception {
        boolean estaDesmayorizado = false;
        String mensajeRespuesta = "T";
        Map<String, Object> campos = new HashMap<>();
        List<InvComprasDetalleTO> listaDetalle = new ArrayList<>();
        List<InvAdjuntosCompras> listaImagenes = new ArrayList<>();

        if (invComprasTO.getCompAnulado()) {
            mensajeRespuesta = "FLa compra esta ANULADA, se debe restaurar";
        } else {

            if (invComprasTO.getCompPendiente()) {
                InvComprasPK invComprasPK = new InvComprasPK(invComprasTO.getEmpCodigo(), invComprasTO.getCompPeriodo(), invComprasTO.getCompMotivo(), invComprasTO.getCompNumero());
                //Detalle
                List<InvListaDetalleComprasTO> listaInvCompraDetalleTO = comprasDetalleService.getListaInvCompraDetalleTO(invComprasPK.getCompEmpresa(), invComprasPK.getCompPeriodo(), invComprasPK.getCompMotivo(), invComprasPK.getCompNumero());
                listaDetalle = ConversionesInventario.convertirInvListaDetalleComprasTO_InvComprasDetalleTO(listaInvCompraDetalleTO, invComprasPK);
                //Imagenes
                listaImagenes = comprasDao.getAdjuntosCompra(invComprasPK);
                estaDesmayorizado = true;
            } else {// se debe desnayorizar
                mensajeRespuesta = "FLa compra debe estar PENDIENTE";
            }

            if (estaDesmayorizado) {//Solo cambiamos a 00 y anulamos
                invComprasTO.setCompDocumentoTipo("00");
                invComprasTO.setCompElectronica(false);

                MensajeTO mensajeTO = modificarInvComprasTO(
                        invComprasTO,
                        listaDetalle,
                        new ArrayList<>(),
                        null,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        false,
                        false,
                        null,
                        listaImagenes,
                        sisInfoTO);
                mensajeRespuesta = mensajeTO.getMensaje();
            }

        }
        campos.put("invComprasTO", invComprasTO);
        campos.put("mensaje", mensajeRespuesta);
        return campos;
    }

    @Override
    public List<InvConsultaCompraTO> listarComprasPorProveedor(String empresa, String periodo, String motivo, String numero, String provCodigo) throws Exception {
        return comprasDao.listarComprasPorProveedor(empresa, periodo, motivo, numero, provCodigo);
    }

    @Override
    public List<InvConsultaCompraTO> listarComprasPorProveedorSoloNotaEntrega(String empresa, String periodo, String motivo, String numero, String provCodigo) throws Exception {
        return comprasDao.listarComprasPorProveedorSoloNotaEntrega(empresa, periodo, motivo, numero, provCodigo);
    }

    @Override
    public List<InvConsultaCompraTO> listarComprasImb(String empresa, String periodo, String motivo, String numero) throws Exception {
        return comprasDao.listarComprasImb(empresa, periodo, motivo, numero);
    }

    @Override
    public String guardarClaveAcceso(String codigoEmpresa, String motivo, String numero, String periodo, String claveAcceso, SisInfoTO sisInfoTO) throws Exception {
        String retorno;
        comprobar = false;
        susClave = motivo + " | " + numero + " | " + periodo;
        susDetalle = "registro de clave de acceso perteneciente al documento " + motivo + " | " + numero + " | " + periodo;
        susSuceso = "INSERT";
        susTabla = "inventario.inv_compra";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sucesoDao.insertar(sisSuceso);
        comprobar = comprasDao.guardarClaveAcceso(codigoEmpresa, motivo, numero, periodo, claveAcceso, sisInfoTO);
        if (comprobar) {
            retorno = "TEl registro de clave de acceso se efectuo correctamente.";
        } else {
            retorno = "FError al guardar clave de acceso";
        }
        return retorno;
    }

    @Override
    public boolean guardarImagenesCompra(InvComprasPK pk, List<InvAdjuntosComprasWebTO> imagenes, SisInfoTO sisInfoTO) throws Exception {
        List<InvAdjuntosCompras> listaImagenes = new ArrayList<>();
        if (imagenes != null && imagenes.size() > 0) {
            for (InvAdjuntosComprasWebTO item : imagenes) {
                /// PREPARANDO OBJETO SISSUCESO
                if (item.getAdjSecuencial() == null) {
                    susClave = pk.getCompEmpresa() + "|" + pk.getCompPeriodo() + "|" + pk.getCompMotivo() + "|" + pk.getCompNumero();
                    susDetalle = "Se agregó la imagen para la compra: " + susClave;
                    susSuceso = "INSERT";
                    susTabla = "inventario.inv_compras_datos_adjuntos";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sucesoDao.insertar(sisSuceso);
                    InvAdjuntosCompras imagen = new InvAdjuntosCompras();
                    imagen.setInvCompras(new InvCompras(pk));
                    imagen.setAdjSecuencial(item.getAdjSecuencial());
                    imagen.setAdjTipo(item.getAdjTipo());
                    imagen.setAdjArchivo(item.getImagenString().getBytes("UTF-8"));
                    listaImagenes.add(imagen);
                } else {
                    InvAdjuntosCompras imagen = new InvAdjuntosCompras();
                    imagen.setInvCompras(new InvCompras(pk));
                    imagen.setAdjSecuencial(item.getAdjSecuencial());
                    imagen.setAdjTipo(item.getAdjTipo());
                    listaImagenes.add(imagen);
                }
            }
        } else {
            listaImagenes.add(new InvAdjuntosComprasWebTO());//esto para que elimine
        }
        comprasDao.actualizarImagenesCompra(listaImagenes, pk);
        return true;
    }

    @Override
    public Map<String, Object> obtenerDatosAjusteInventario(String empresa, String accion, String bodega, String hasta, String categoria, String usuario) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        /*Lista bodega*/
        List<InvListaBodegasTO> listaInvListaBodegasTO = bodegaService.buscarBodegasTO(empresa, false, null);
        /*Lista motivos*/
        List<InvComprasMotivoTO> listaInvComprasMotivoTO = comprasMotivoService.listarInvComprasMotivoTOAjusteInv(empresa, true);
        List<InvProductoCategoriaTO> listaCategorias = productoCategoriaService.getInvProductoCategoriaTO(empresa);
        SisPeriodo periodo = periodoService.getPeriodoPorFecha(UtilsDate.DeStringADate(hasta), empresa);

        if (accion != null) {
            List<InvComboFormaPagoTO> listaFormaPago = comprasFormaPagoService.getComboFormaPagoCompra(empresa);
            InvComboFormaPagoTO fp = new InvComboFormaPagoTO();
            fp.setCtaCodigo("000000000000");
            fp.setFpDetalle("POR PAGAR");
            fp.setValidar(false);
            listaFormaPago.add(fp);
            SisConfiguracionCompras sisConfiguracionCompras = configuracionComprasService.getSisConfiguracionCompras(new SisConfiguracionComprasPK(empresa, usuario));
            CajCajaTO cajCajaTO = cajaDao.getCajCajaTO(empresa, usuario);

            List<SaldoBodegaTO> listaSaldoBodega = bodegaService.getListaSaldoBodegaTO(empresa, bodega, hasta, categoria);

            campos.put("listaFormaPago", listaFormaPago);
            campos.put("listaSaldoBodega", listaSaldoBodega);
            campos.put("sisConfiguracionCompras", sisConfiguracionCompras);
            campos.put("cajCajaTO", cajCajaTO);
        }
        campos.put("periodo", periodo);
        campos.put("listaInvComprasMotivoTO", listaInvComprasMotivoTO);
        campos.put("listaBodegas", listaInvListaBodegasTO);
        campos.put("listaCategorias", listaCategorias);
        return campos;
    }

    @Override
    public Map<String, Object> crearAjusteInventario(String empresa, String usuario, List<InvComprasDetalleTO> listaInvAjusteInventarioDetalleTO, InvComprasTO invCompraTO, InvComboFormaPagoTO formaPago, SisInfoTO sisInfoTO) throws Exception {
        //Inicializar variables

        //  stock real -stock actual = (+) es nota entrega
        //  stock real -stock actual = (-) es nota credito interna documento numero 999-999-999999999
        Map<String, Object> campos = new HashMap<>();
        List<String> mensajeClase = new ArrayList<String>();
        campos.put("mensaje", "F");
        String mensaje = "";
        List<String> listaErrores1 = new ArrayList<>();
        String retorno = "";
        boolean periodoCerrado = false;
        invCompraTO.setUsrInsertaCompra(usuario);
        invCompraTO.setEmpCodigo(empresa);
        invCompraTO.setCtaEmpresa(empresa);
        invCompraTO.setCtaCodigo(formaPago.getCtaCodigo());
        invCompraTO.setFpSecuencial(formaPago.getFpSecuencial());
        SisListaPeriodoTO periodo = null;

        //VERIFICAR SI PROVEEDOR EXISTE
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, invCompraTO.getEmpCodigo());
        InvProveedor invProveedor = proveedorDao.obtenerInvProveedorPorCedulaRuc(invCompraTO.getEmpCodigo(), sisEmpresaParametros.getEmpCodigo().getEmpRuc());

        if (invProveedor != null) {
            if (!UtilsValidacion.isFechaSuperior(invCompraTO.getCompFecha(), "yyyy-MM-dd")) {
                comprobar = false;
                //VERIFICAR PERIODO
                List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invCompraTO.getEmpCodigo());
                for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                    if (UtilsValidacion.fecha(invCompraTO.getCompFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime() && UtilsValidacion.fecha(invCompraTO.getCompFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                        comprobar = true;
                        periodo = sisListaPeriodoTO;
                        invCompraTO.setCompPeriodo(sisListaPeriodoTO.getPerCodigo());
                        periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                    }
                }
                if (comprobar) {
                    if (!periodoCerrado) {
                        //ASIGNAR VALORES
                        if (formaPago.getFpDetalle().equalsIgnoreCase("POR PAGAR")) {
                            invCompraTO.setCompSaldo(invCompraTO.getCompTotal().subtract(invCompraTO.getCompValorRetenido()).add(BigDecimal.ZERO));
                        } else {
                            invCompraTO.setCompSaldo(new BigDecimal("0.00"));
                        }
                        if (comprasMotivoDao.comprobarInvComprasMotivo(invCompraTO.getEmpCodigo(), invCompraTO.getCompMotivo())) {
                            /// PREPARANDO OBJETO SISSUCESO
                            susSuceso = "INSERT";
                            susTabla = "inventario.inv_compra";
                            invCompraTO.setUsrFechaInsertaCompra(UtilsValidacion.fechaSistema());
                            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

                            invCompraTO.setCompImportacion(false);
                            //CREA CABECERA DE COMPRA
                            BigDecimal valorPorcentaje = porcentajeIvaService.getValorAnxPorcentajeIvaTO(invCompraTO.getCompFecha());
                            BigDecimal iva_porcentaje = valorPorcentaje.divide(new BigDecimal("100.00"), 2, RoundingMode.HALF_UP);

                            InvCompras invComprasNE = ConversionesInventario.convertirInvComprasTO_InvCompras(invCompraTO);
                            InvCompras invComprasNC = null;
                            invComprasNE.setCompIvaVigente(valorPorcentaje);
                            invComprasNE.setCompDocumentoTipo("00");
                            invComprasNE.setCompIvaVigente(valorPorcentaje);
                            invComprasNE.setCompBaseImponible(cero);
                            invComprasNE.setCompBase0(cero);
                            invComprasNE.setCompIce(cero);
                            invComprasNE.setCompMontoIva(cero);
                            invComprasNE.setCompTotal(cero);
                            invComprasNE.setInvProveedor(invProveedor);

                            invComprasNC = ConversionesInventario.convertirInvCompras_InvCompras(invComprasNE);
                            invComprasNC.setCompDocumentoTipo("04");
                            invComprasNC.setCompDocumentoNumero("999-999-999999999");

                            //CREANDO DETALLE DE COMPRA
                            List<InvComprasDetalle> listInvComprasDetalleNE = new ArrayList<InvComprasDetalle>();
                            List<InvComprasDetalle> listInvComprasDetalleNC = new ArrayList<InvComprasDetalle>();

                            int estadoDetalle = 0;
                            String estadoDetalleComplemento = "";

                            for (InvComprasDetalleTO invComprasDetalleTO : listaInvAjusteInventarioDetalleTO) {
                                InvComprasDetalle invComprasDetalle = new InvComprasDetalle();
                                invComprasDetalleTO.setDetPendiente(false);
                                invComprasDetalleTO.setDetConfirmado(true);
                                invComprasDetalle = ConversionesInventario.convertirInvComprasDetalleTO_InvComprasDetalle(invComprasDetalleTO);
                                //VERIFICAR PRODUCTO, SI NO EXISTE estadoDetalle = 1
                                InvProducto invProducto = productoDao.buscarInvProducto(invComprasDetalleTO.getProEmpresa(), invComprasDetalleTO.getProCodigoPrincipal());
                                if (invProducto != null) {
                                    invComprasDetalle.setInvProducto(invProducto);
                                    invComprasDetalle.setProCreditoTributario(invProducto.getProCreditoTributario());
                                    //VERIFICAR BODEGA, SI NO EXISTE estadoDetalle = 2
                                    InvBodega invBodega = bodegaDao.buscarInvBodega(invComprasDetalleTO.getProEmpresa(), invComprasDetalleTO.getBodCodigo());
                                    if (invBodega != null) {
                                        invComprasDetalle.setInvBodega(invBodega);
                                        invComprasDetalle.setSecCodigo(invBodega.getSecCodigo());
                                        invComprasDetalle.setSecEmpresa(invBodega.getSecEmpresa());
                                        //Calculo
                                        BigDecimal diferencia = invComprasDetalleTO.getDetCantidad().subtract(invComprasDetalleTO.getSaldoActual());// saldo real - saldo actual
                                        BigDecimal cantidad = diferencia.compareTo(cero) == 1 ? diferencia : diferencia.multiply(new BigDecimal(-1));
                                        BigDecimal parcial = invComprasDetalleTO.getDetPrecio().multiply(cantidad);
                                        BigDecimal sumTotal = parcial;
                                        BigDecimal compIva = (invProducto.getProIva().equals("GRAVA") ? sumTotal.multiply(iva_porcentaje) : cero);
                                        invComprasDetalle.setDetIce(cero);
                                        invComprasDetalle.setDetOtrosImpuestos(cero);
                                        invComprasDetalle.setDetCantidad(cantidad);
                                        invComprasDetalle.setDetPrecio(invComprasDetalleTO.getDetPrecio());

                                        if (diferencia.compareTo(BigDecimal.ZERO) == 1) { //(+)
                                            if (invProducto.getProIva().equals("GRAVA")) {
                                                invComprasNE.setCompBaseImponible(invComprasNE.getCompBaseImponible().add(parcial));
                                            } else {
                                                invComprasNE.setCompBase0(invComprasNE.getCompBase0().add(parcial));
                                            }
                                            invComprasNE.setCompMontoIva(invComprasNE.getCompMontoIva().add(compIva));
                                            listInvComprasDetalleNE.add(invComprasDetalle);
                                        } else {
                                            if (invProducto.getProIva().equals("GRAVA")) {
                                                invComprasNC.setCompBaseImponible(invComprasNC.getCompBaseImponible().add(parcial));
                                            } else {
                                                invComprasNC.setCompBase0(invComprasNC.getCompBase0().add(parcial));
                                            }
                                            invComprasNC.setCompMontoIva(invComprasNC.getCompMontoIva().add(compIva));
                                            listInvComprasDetalleNC.add(invComprasDetalle);
                                        }
                                    } else {
                                        estadoDetalle = 2;
                                    }
                                } else {
                                    estadoDetalle = 1;
                                }

                                if (invComprasDetalle.getInvBodega().getBodProductoPermitido() != null && !invComprasDetalle.getInvBodega().getBodProductoPermitido().equalsIgnoreCase(invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                    estadoDetalle = 3;
                                }
                                if (invComprasDetalle.getInvBodega().getBodProductoNoPermitido() != null && invComprasDetalle.getInvBodega().getBodProductoNoPermitido().equalsIgnoreCase(invComprasDetalle.getInvProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo())) {
                                    estadoDetalle = 3;
                                }

                                if (estadoDetalle == 3) {
                                    estadoDetalleComplemento = "F<html>El producto '" + invComprasDetalle.getInvProducto().getProNombre().trim() + "' no puede ingresar a la bodega '" + invComprasDetalle.getInvBodega().getInvBodegaPK().getBodCodigo() + "'; esta bodega NO permite productos del tipo '" + invComprasDetalle.getInvProducto().getInvProductoTipo().getTipDetalle() + "'</html>";
                                }

                                if (estadoDetalle == 1 || estadoDetalle == 2 || estadoDetalle == 3) {
                                    break;
                                } else {
                                    invProducto = null;
                                }
                            }
                            if (listInvComprasDetalleNE.size() > 0) {
                                invComprasNE.setSecCodigo(listInvComprasDetalleNE.get(0).getSecCodigo());
                                invComprasNE.setSecEmpresa(listInvComprasDetalleNE.get(0).getSecEmpresa());
                            }

                            if (listInvComprasDetalleNC.size() > 0) {
                                invComprasNC.setSecCodigo(listInvComprasDetalleNC.get(0).getSecCodigo());
                                invComprasNC.setSecEmpresa(listInvComprasDetalleNC.get(0).getSecEmpresa());
                            }

                            invComprasNE.setCompTotal(invComprasNE.getCompMontoIva().add(
                                    invComprasNE.getCompBaseImponible()).add(
                                    invComprasNE.getCompBase0()));

                            invComprasNC.setCompTotal(invComprasNC.getCompMontoIva().add(
                                    invComprasNC.getCompBaseImponible()).add(
                                    invComprasNC.getCompBase0()));

                            if (estadoDetalle == 0 && (listInvComprasDetalleNE.size() > 0 || listInvComprasDetalleNC.size() > 0)) {
                                invComprasNE.setCompDocumentoNumero(null);
                                if (listInvComprasDetalleNC.size() > 0) {
                                    for (int i = 0; i < listInvComprasDetalleNC.size(); i++) {
                                        listInvComprasDetalleNC.get(i).setDetPendiente(invComprasNC.getCompPendiente());
                                    }
                                    mensajeClase = verificarStockCompras(invComprasNC, listInvComprasDetalleNC);
                                }

                                if (mensajeClase.isEmpty()) {
                                    //CREANDO LIQUIDACION COMPRAS
                                    comprobar = comprasDao.crearAjusteInventario(
                                            invComprasNE,
                                            invComprasNC,
                                            listInvComprasDetalleNE,
                                            listInvComprasDetalleNC,
                                            sisSuceso);
                                    if (comprobar) {
                                        invComprasNE = invComprasNE.getInvComprasPK().getCompNumero() != null ? invComprasNE : null;
                                        invComprasNC = invComprasNC.getInvComprasPK().getCompNumero() != null ? invComprasNC : null;
                                        if (invComprasNE != null && invComprasNC != null) {
                                            retorno = "T<html>Las siguientes compras se guardaron correctamente con la siguiente información:<br><br>"
                                                    + "Nota de entrega: <font size = 5>"
                                                    + invComprasNE.getInvComprasPK().getCompMotivo() + " | "
                                                    + periodo.getPerDetalle() + " | "
                                                    + invComprasNE.getInvComprasPK().getCompNumero()
                                                    + "</font>.<br> Nota de crédito: <font size = 5>"
                                                    + invComprasNC.getInvComprasPK().getCompMotivo() + " | "
                                                    + periodo.getPerDetalle() + " | "
                                                    + invComprasNC.getInvComprasPK().getCompNumero()
                                                    + "</font>.</html>";
                                            campos.put("invComprasNE", invComprasNE);
                                            campos.put("invComprasNC", invComprasNC);

                                        } else if (invComprasNE == null && invComprasNC != null) {
                                            retorno = "T<html>La compra se guardó correctamente con la siguiente información:<br><br>"
                                                    + "Periodo: <font size = 5>"
                                                    + periodo.getPerDetalle()
                                                    + "</font>.<br> Motivo: <font size = 5>"
                                                    + invComprasNC.getInvComprasPK().getCompMotivo()
                                                    + "</font>.<br> Número: <font size = 5>"
                                                    + invComprasNC.getInvComprasPK().getCompNumero()
                                                    + "</font>.</html>";
                                            campos.put("invComprasNC", invComprasNC);
                                        } else {
                                            retorno = "T<html>La compra se guardó correctamente con la siguiente información:<br><br>"
                                                    + "Periodo: <font size = 5>"
                                                    + periodo.getPerDetalle()
                                                    + "</font>.<br> Motivo: <font size = 5>"
                                                    + invComprasNE.getInvComprasPK().getCompMotivo()
                                                    + "</font>.<br> Número: <font size = 5>"
                                                    + invComprasNE.getInvComprasPK().getCompNumero()
                                                    + "</font>.</html>";
                                            campos.put("invComprasNE", invComprasNE);
                                        }
                                    } else {
                                        retorno = "F<html>Hubo un error al guardar la Compra...\nIntente de nuevo o contacte con el administrador</html>";
                                    }
                                } else {
                                    retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                                    listaErrores1 = mensajeClase;
                                }
                            } else if (estadoDetalle == 1) {
                                retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                            } else if (estadoDetalle == 2) {
                                retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                            } else if (estadoDetalle == 3) {
                                retorno = estadoDetalleComplemento;
                            } else {
                                retorno = "F<html>No existe detalle de compra</html>";
                            }
                        } else {
                            retorno = "F<html>No se encuentra el motivo...</html>";
                        }
                    } else {
                        retorno = "F<html>El período que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                    }
                } else {
                    retorno = "F<html>No se encuentra ningún período para la fecha que ingresó...</html>";
                }
            } else {
                retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
            }
        } else {
            retorno = "F<html>Debe existir un proveedor con el RUC de la empresa...\nIntente de nuevo, escoja otro Proveedor o contacte con el administrador</html>";
        }

        mensaje = retorno.substring(0, retorno.lastIndexOf("</html>") + 7);
        //mensajeAux = retorno;

        if (mensaje.charAt(0) == 'T') {
            campos.put("mensaje", mensaje.substring(0));
        } else if (listaErrores1.isEmpty()) {
            campos.put("mensaje", mensaje);
        } else {
            String mensajeListaErrores = "";
            for (int i = 0; i < listaErrores1.size(); i++) {
                mensajeListaErrores = mensajeListaErrores + "<br>" + listaErrores1.get(i);
            }
            campos.put("mensaje", mensaje + mensajeListaErrores);
        }

        return campos;
    }

    @Override
    public InvComprasTO getComprasTO(String empresa, String tipo, String numeroDocumento) throws Exception {
        return comprasDao.getComprasTO(empresa, tipo, numeroDocumento);
    }

    @Override
    public Boolean actualizarClaveExterna(InvComprasPK pk, String clave, SisInfoTO sisInfoTO) throws Exception {
        // / PREPARANDO OBJETO SISSUCESO
        susClave = pk.getCompPeriodo() + " | " + pk.getCompMotivo() + " | " + pk.getCompNumero();
        susDetalle = "Se actualizó la clave externa de la liquidación de compra con código: " + pk.getCompPeriodo() + " | " + pk.getCompMotivo() + " | " + pk.getCompNumero();
        susTabla = "inventario.inv_compras";
        susSuceso = "UPDATE";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return comprasDao.actualizarClaveExterna(pk, clave, sisSuceso);
    }

    @Override
    public Map<String, Object> convertirXMLResultado(String empresa, String clave, String XmlString, boolean validarCompra) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        Autorizacion comprobanteAutorizado = new Autorizacion();
        String mensaje = "T";
        String conteo;
        String tipoDocumento = clave.substring(8, 10);
        //xml viene en base 64
        if (XmlString != null) {
            try {
                byte[] result = Base64.getDecoder().decode(XmlString);
                String xmlUTF8 = new String((byte[]) result, "UTF-8");
                XmlString = xmlUTF8;
            } catch (Exception e) {
            }
        }

        comprobanteAutorizado.setComprobante(XmlString);
        comprobanteAutorizado.setAmbiente("PRODUCCIÓN");
        //proveedor
        String comDocumentoNumero = clave.substring(24, 27) + "-" + clave.substring(27, 30) + "-" + clave.substring(30, 39);
        InvProveedorTO invProveedorTO = proveedorService.getBuscaCedulaProveedorTO(empresa, clave.substring(10, 23));

        //fecha
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(XmlString.getBytes("UTF-8")));
        Node contenidoComprobanteXmlNode = doc.getElementsByTagName("comprobante").item(0);

        DocumentoReporteRIDE documentoRide = ArchivoUtils.documentoRIDE(doc);
        String fechaAutorizacion = documentoRide.getFechaAutorizacion();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(UtilsDate.fechaFormatoDate(fechaAutorizacion, "dd/MM/yyyy hh:mm:ss"));

        comprobanteAutorizado.setEstado(documentoRide.getEstado());
        comprobanteAutorizado.setNumeroAutorizacion(documentoRide.getNumeroAutorizacion());
        comprobanteAutorizado.setFechaAutorizacion(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        ItemResultadoBusquedaElectronico itemResultadoBusquedaElectronico = ItemResultadoBusquedaElectronico.convertirAutorizacionEnItemResultadoBusquedaElectronico(comprobanteAutorizado);

        //provedor xml
        InvProveedorDatosXMLTO invProveedorDatosXMLTO = new InvProveedorDatosXMLTO();
        invProveedorDatosXMLTO.setRazonSocial(itemResultadoBusquedaElectronico.getNombre());
        invProveedorDatosXMLTO.setRuc(itemResultadoBusquedaElectronico.getRucProveedor());
        invProveedorDatosXMLTO.setDirMatriz(comprobanteAutorizado.getComprobante().substring(comprobanteAutorizado.getComprobante().lastIndexOf("<dirMatriz>") + 11, comprobanteAutorizado.getComprobante().lastIndexOf("</dirMatriz>")).trim());
        invProveedorDatosXMLTO.setNumeroDocumento(itemResultadoBusquedaElectronico.getNumeroDocumento());
        invProveedorDatosXMLTO.setFechaEmision(itemResultadoBusquedaElectronico.getFechaEmision());
        invProveedorDatosXMLTO.setNumeroAutorizacion(itemResultadoBusquedaElectronico.getNumeroAutorizacion());
        invProveedorDatosXMLTO.setFechaAutorizacion(itemResultadoBusquedaElectronico.getFechaAutorizacion());
        invProveedorDatosXMLTO.setCodDoc(comprobanteAutorizado.getComprobante().substring(comprobanteAutorizado.getComprobante().lastIndexOf("<codDoc>") + 8, comprobanteAutorizado.getComprobante().lastIndexOf("</codDoc>")).trim());
        invProveedorDatosXMLTO.setTotalComprobante(itemResultadoBusquedaElectronico.getTotalComprobante());

        if (invProveedorTO != null) {
            invProveedorDatosXMLTO.setRazonSocial(invProveedorTO.getProvRazonSocial());
            boolean continuar = false;
            if (validarCompra) {
                conteo = getConteoNumeroFacturaCompra(empresa, invProveedorTO.getProvCodigo(), tipoDocumento, comDocumentoNumero);
                if (conteo != null && !conteo.equals("")) {
                    mensaje = "FEl Nº " + comDocumentoNumero + " ya fue ocupado para otra compra\n con el proveedor: " + invProveedorTO.getProvRazonSocial() + "\n " + "con RUC : " + invProveedorTO.getProvId() + ".";
                } else {
                    continuar = true;
                }
            } else {
                continuar = true;
            }
            if (continuar) {
                itemResultadoBusquedaElectronico.setIdProveedor(invProveedorTO.getProvCodigo());
                itemResultadoBusquedaElectronico.setProvTipoId(invProveedorTO.getProvTipoId());
            }
        }

        if (validarCompra) {
            //obtener detalle de compra
            if (tipoDocumento.compareTo("01") == 0) {
                FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXmlNode));
                List<InvComprasDetalleTO> listaDetalleCompras = obtenerListaDetalleCompra(facturaReporte, empresa);
                if (listaDetalleCompras != null && listaDetalleCompras.size() > 0) {
                    campos.put("listaDetalleCompras", listaDetalleCompras);
                }
            }
        }

        if (mensaje.substring(0).equals("T")) {
            //resultado
            ItemComprobanteElectronico itemComprobanteElectronico = new ItemComprobanteElectronico();
            itemComprobanteElectronico.setInvProveedorDatosXMLTO(invProveedorDatosXMLTO);
            itemComprobanteElectronico.setItemBusqueda(itemResultadoBusquedaElectronico);
            campos.put("itemComprobanteElectronico", itemComprobanteElectronico);
        }

        campos.put("mensaje", mensaje);

        return campos;
    }

    public List<InvComprasDetalleTO> obtenerListaDetalleCompra(FacturaReporte facturaReporte, String empresa) {
        Factura factura = facturaReporte != null ? facturaReporte.getFactura() : null;
        List<InvComprasDetalleTO> listaDetalleCompras = new ArrayList();
        if (factura != null) {
            for (Factura.Detalles.Detalle det : factura.getDetalles().getDetalle()) {
                InvComprasDetalleTO detalle = new InvComprasDetalleTO();
                detalle.setProCodigoPrincipal("");
                detalle.setProCodigoPrincipalCopia("");
                AnxHomologacionProducto homl = anxHomologacionProductoService.obtenerHomologacionProducto(empresa, det.getCodigoPrincipal(), factura.getInfoTributaria().getRuc());
                if (homl != null) {
                    if (homl.getInvProducto() != null) {
                        InvProducto producto = homl.getInvProducto();
                        detalle.setProEmpresa(empresa);
                        detalle.setProCodigoPrincipal(producto.getInvProductoPK().getProCodigoPrincipal());
                        detalle.setProCodigoPrincipalCopia(detalle.getProCodigoPrincipal());
                        detalle.setNombreProducto(producto.getProNombre());
                        detalle.setProEstadoIva(producto.getProIva());
                        detalle.setMedidaDetalle(producto.getInvProductoMedida().getMedDetalle());
                        detalle.setDetCreditoTributario(producto.getProCreditoTributario());
                        detalle.setExigirSerie(producto.isProExigirSerie());
                    }
                }
                //
                detalle.setParcialProducto(det.getPrecioTotalSinImpuesto());
                detalle.setParcialInicial(detalle.getParcialProducto());

                //*************IMPUESTOS*************
                detalle.setProEstadoIva("NO GRAVA");
                if (det.getImpuestos().getImpuesto() != null) {
                    for (ImpuestoFactura impuesto : det.getImpuestos().getImpuesto()) {
                        if (impuesto.getTarifa().compareTo(cero) != 0) {
                            detalle.setProEstadoIva("GRAVA");
                        }

                        if (impuesto.getCodigo().equals("3")) {
                            detalle.setParcialProducto(detalle.getParcialProducto().add(impuesto.getValor()));
                            detalle.setParcialInicial(detalle.getParcialProducto());
                        }
                    }
                }
                //***************************************
                if (listaDetalleCompras.isEmpty()) {
                    detalle.setDetCantidad(BigDecimal.ONE);
                    detalle.setDetPrecio(detalle.getParcialProducto());
                    listaDetalleCompras.add(detalle);
                } else {
                    boolean encontrado = false;
                    for (InvComprasDetalleTO item : listaDetalleCompras) {
                        if (item.getProCodigoPrincipal().equals(detalle.getProCodigoPrincipal())
                                && item.getProEstadoIva().equals(detalle.getProEstadoIva())) {
                            encontrado = true;
                            BigDecimal sumaParcial = item.getParcialProducto().add(detalle.getParcialProducto());
                            item.setParcialInicial(sumaParcial);
                            item.setParcialProducto(sumaParcial);
                            item.setDetCantidad(BigDecimal.ONE);
                            item.setDetPrecio(item.getParcialProducto());
                        }
                    }
                    if (!encontrado) {
                        detalle.setDetCantidad(BigDecimal.ONE);
                        detalle.setDetPrecio(detalle.getParcialProducto());
                        listaDetalleCompras.add(detalle);
                    }
                }
            }
        }

        return listaDetalleCompras;
    }

    public AnxComprobantesElectronicosRecibidos convertirXMLAAnxComprobantesElectronicosRecibidos(RespuestaComprobante respuestaComprobante, String empresa, String clave) throws IOException {
        AnxComprobantesElectronicosRecibidos comprobante = null;
        String tipoDocumento = clave.substring(8, 10);
        String periodo = clave.substring(0, 8);
        if (periodo != null) {
            String anio = periodo.substring(4, 8);
            String mes = periodo.substring(2, 4);
            periodo = anio.concat("-").concat(mes);
        }
        Autorizacion comprobanteAutorizado = obtenerComprobanteAutorizado(respuestaComprobante);
        if (comprobanteAutorizado != null) {
            String total = "";
            String comprobanteDescripcion = "";
            String complemento = "";
            String xmlSriString = comprobanteAutorizado.getComprobante();
            comprobante = new AnxComprobantesElectronicosRecibidos();

            comprobante.setAnxComprobantesElectronicosRecibidosPk(new AnxComprobantesElectronicosRecibidosPk(empresa, periodo, clave));
            String comprobanteSerie = xmlSriString.substring(xmlSriString.lastIndexOf("<estab>") + 7, xmlSriString.lastIndexOf("</estab>")).trim()
                    + "-" + xmlSriString.substring(xmlSriString.lastIndexOf("<ptoEmi>") + 8, xmlSriString.lastIndexOf("</ptoEmi>")).trim()
                    + "-" + xmlSriString.substring(xmlSriString.lastIndexOf("<secuencial>") + 12, xmlSriString.lastIndexOf("</secuencial>")).trim();

            if (tipoDocumento.equals(TipoComprobanteEnum.FACTURA.getCode())) {
                comprobanteDescripcion = "Factura";
                total = xmlSriString.substring(xmlSriString.lastIndexOf("<importeTotal>") + 14, xmlSriString.lastIndexOf("</importeTotal>")).trim();
            } else if (tipoDocumento.equals(TipoComprobanteEnum.NOTA_DE_CREDITO.getCode())) {
                comprobanteDescripcion = "Notas de Crédito";
                complemento = xmlSriString.substring(xmlSriString.lastIndexOf("<numDocModificado>") + 18, xmlSriString.lastIndexOf("</numDocModificado>")).trim();
                total = xmlSriString.substring(xmlSriString.lastIndexOf("<valorModificacion>") + 19, xmlSriString.lastIndexOf("</valorModificacion>")).trim();
            } else if (tipoDocumento.equals(TipoComprobanteEnum.NOTA_DE_DEBITO.getCode())) {
                comprobanteDescripcion = "Notas de Débito";
                complemento = xmlSriString.substring(xmlSriString.lastIndexOf("<numDocModificado>") + 18, xmlSriString.lastIndexOf("</numDocModificado>")).trim();
                total = xmlSriString.substring(xmlSriString.lastIndexOf("<valorTotal>") + 12,
                        xmlSriString.lastIndexOf("</valorTotal>")).trim();
            } else if (tipoDocumento.equals(TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode())) {
                comprobanteDescripcion = "Comprobante de Retención";
            }

            comprobante.setComproSerieComprobante(comprobanteSerie);
            comprobante.setComproFechaAutorizacion(UtilsDate.fechaFormatoDate(UtilsValidacion.fecha(
                    comprobanteAutorizado.getFechaAutorizacion().toGregorianCalendar().getTime(), "yyyy-MM-dd hh:mm:ss"), "yyyy-MM-dd"));

            comprobante.setComproFechaEmision(UtilsDate.fechaFormatoDate(
                    UtilsValidacion.fecha(xmlSriString.substring(xmlSriString.lastIndexOf("<fechaEmision>") + 14, xmlSriString.lastIndexOf("</fechaEmision>")).trim(), "dd/MM/yyyy"),
                    "yyyy-MM-dd"));
            comprobante.setComproNumeroAutorizacion(clave);
            comprobante.setComproTipoEmision("NORMAL");
            //emisor
            String rucEmisor = xmlSriString.substring(xmlSriString.lastIndexOf("<ruc>") + 5, xmlSriString.lastIndexOf("</ruc>")).trim();
            comprobante.setComproRucEmisor(rucEmisor);
            comprobante.setComproRazonSocialEmisor(xmlSriString.substring(xmlSriString.lastIndexOf("<razonSocial>") + 13, xmlSriString.lastIndexOf("</razonSocial>")).trim());
            //receptor
            String identificacionRec = xmlSriString.substring(xmlSriString.lastIndexOf("<identificacionComprador>") + 25, xmlSriString.lastIndexOf("</identificacionComprador>")).trim();
            comprobante.setComproIdentificacionReceptor(identificacionRec);
            comprobante.setComproComprobante(comprobanteDescripcion);
            comprobante.setComproImporteTotal(new BigDecimal(total));
            comprobante.setComproComplemento(complemento);
            //Setear xml para no volver a consultar al sri
            for (Autorizacion autorizacion : respuestaComprobante.getAutorizaciones().getAutorizacion()) {
                if (autorizacion.getEstado().equalsIgnoreCase("AUTORIZADO")) {
                    if (!autorizacion.getComprobante().substring(0, 9).trim().equals("<![CDATA[")) {
                        autorizacion.setComprobante("<![CDATA[" + autorizacion.getComprobante() + "]]>");
                    }
                    comprobante.setComproXml(ArchivoUtils.convertirXMLAutorizacionABytes(autorizacion));
                }
            }
        }

        return comprobante;
    }

    @Override
    public List<InvCompras> obtenerListadoComprasSaldosImportados(String empresa, String motivo, String sector, String fecha) throws Exception {
        return comprasDao.obtenerListadoComprasSaldosImportados(empresa, motivo, sector, fecha);
    }

    @Override
    public Map<String, Object> insertarProveedoresRezagadosLote(List<InvProveedor> proveedores, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        Map<String, Object> campos = null;
        String empresa = sisInfoTO.getEmpresa();
        try {
            campos = new HashMap<>();
            if (proveedores != null && !proveedores.isEmpty()) {
                List<InvProveedorCategoriaTO> categorias = proveedorCategoriaService.getInvProveedorCategoriaTO(empresa);
                List<InvProveedorTO> listaProveedoresInsertado = new ArrayList<>();//contiene los que ya estan en BD y los nuevos en BD
                for (int i = 0; i < proveedores.size(); i++) {
                    //proveedor
                    boolean proveedorRepetido = false;
                    String razonSocial = proveedores.get(i).getProvRazonSocial() != null
                            && !proveedores.get(i).getProvRazonSocial().equals("")
                            ? proveedores.get(i).getProvRazonSocial().toUpperCase()
                            : "";

                    String identificacion = proveedores.get(i).getProvIdNumero() != null
                            && !proveedores.get(i).getProvIdNumero().equals("")
                            ? proveedores.get(i).getProvIdNumero().trim() : null;
                    String mensajeAux = "T";

                    if (identificacion != null) {
                        char tipo = proveedores.get(i).getProvIdNumero().length() == 13 ? 'R' : proveedores.get(i).getProvIdNumero().length() == 10 ? 'C' : 'N';
                        InvProveedorTO proveedorEnBD = proveedorService.getBuscaCedulaProveedorTO(empresa, identificacion, tipo);
                        List<InvProveedorTO> fueInsertado = listaProveedoresInsertado.stream()
                                .filter(item -> item.getProvId().equals(identificacion)
                                || item.getProvRazonSocial().equals(razonSocial))
                                .collect(Collectors.toList());
                        proveedorRepetido = (fueInsertado == null || fueInsertado.isEmpty()) ? false : true;

                        if (proveedorEnBD == null) {//no existe en BD
                            if (!proveedorRepetido) {
                                if (razonSocial != null) {
                                    //Crear proveedor
                                    InvProveedorTO proveedor = new InvProveedorTO();
                                    proveedor.setProvCodigo("");
                                    proveedor.setProvEmpresa(empresa);
                                    proveedor.setEmpCodigo(empresa);
                                    proveedor.setProvId(identificacion);
                                    proveedor.setProvRazonSocial(razonSocial);
                                    proveedor.setProvRelacionado(false);
                                    proveedor.setProvInactivo(false);
                                    proveedor.setProvTipoId(tipo + "");
                                    proveedor.setProvEmail("");
                                    proveedor.setUsrInsertaProveedor(sisInfoTO.getUsuario());
                                    if (categorias != null && categorias.size() > 0) {
                                        proveedor.setProvCategoria(categorias.get(0).getPcCodigo());
                                    } else {
                                        mensajeAux = "FNo existe categorías de proveedor.";
                                    }
                                    String respues = proveedorService.insertarInvProveedorTO(proveedor, sisInfoTO, null);
                                    if (respues != null && respues.substring(0, 1).equals("T")) {
                                        listaProveedoresInsertado.add(proveedor);
                                    }
                                    mensajeAux = respues;
                                } else {
                                    mensajeAux = "FEl proveedor: <strong class='pl-2'>" + identificacion + " </strong> no existe.Se debe ingresar razon social";
                                }
                            }
                        } else {
                            InvProveedorTO proveedor = proveedorEnBD;
                            mensajeAux = "FEl proveedor <strong>" + razonSocial + "</strong> con identificación:<strong>" + identificacion + "</strong>, ya existe";
                            if (!proveedorRepetido) {
                                listaProveedoresInsertado.add(proveedor);
                            }
                            if (!razonSocial.equals(proveedor.getProvRazonSocial()) || !identificacion.equals(proveedor.getProvId())) {
                                mensajeAux = "FEl proveedor <strong>" + razonSocial + "</strong> con identificación:<strong>" + identificacion + "</strong>, ya se encuentra registrado en la base de datos con la siguiente información: </br>"
                                        + "Identificación: <strong class='pl-2'>" + proveedor.getProvId() + " </strong></br>"
                                        + "Razón social: <strong class='pl-2'>" + proveedor.getProvRazonSocial() + " </strong></br>";
                            }
                        }
                    } else {
                        mensajeAux = "FDebe ingresar identificación</br>";
                    }

                    if (!proveedorRepetido) {
                        mensaje += mensajeAux + "|";
                    }

                }

                campos.put("mensaje", mensaje);
                campos.put("listaProveedoresInsertado", listaProveedoresInsertado);
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            throw new GeneralException(e.getMessage());
        }

        return campos;
    }

    @Override
    public List<String> validarExistenciasCompras(List<InvCompras> compras, SisInfoTO sisInfoTO) throws Exception {
        String empresa = sisInfoTO.getEmpresa();
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();
        try {
            if (compras != null && !compras.isEmpty()) {
                //insertar  
                for (int i = 0; i < compras.size(); i++) {
                    //proveedor
                    String identificacion = compras.get(i).getInvProveedor().getProvIdNumero();
                    char tipo = compras.get(i).getInvProveedor().getProvIdNumero().length() == 13 ? 'R' : compras.get(i).getInvProveedor().getProvIdNumero().length() == 10 ? 'C' : 'N';
                    InvProveedorTO proveedorEnBD = proveedorService.getBuscaCedulaProveedorTO(empresa, identificacion, tipo);
                    if (proveedorEnBD == null) {
                        listaMensajesEnviar.add("FEl proveedor: <strong class='pl-2'>" + identificacion + " </strong> no existe.");
                    } else {
                        //motivo 
                        boolean existeMotivo = comprasMotivoDao.comprobarInvComprasMotivo(empresa, compras.get(i).getInvComprasPK().getCompMotivo());
                        if (!existeMotivo) {
                            listaMensajesEnviar.add("FEl Motivo: <strong class='pl-2'>"
                                    + compras.get(i).getInvComprasPK().getCompMotivo()
                                    + " </strong> no existe.<br>");
                        }
                    }

                }
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            throw new GeneralException(e.getMessage());
        }

        return listaMensajesEnviar;
    }

    @Override
    public String obtenerLicenciaScanner(String empresa) throws Exception {
        String licencia = null;
        SisScannerConfiguracion scanner = scannerConfiguracionDao.obtenerPorId(SisScannerConfiguracion.class, empresa);
        if (scanner != null) {
            licencia = scanner.getScLicencia();
        }
        return licencia;
    }

    @Override
    public MensajeTO insertarInvComprasTO(InvCompras invCompras, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        boolean periodoCerrado = false;
        MensajeTO mensajeTO = new MensajeTO();
        Integer id = invCompras.getId();
        // ojo -
        if (!UtilsValidacion.isFechaSuperior(UtilsValidacion.fecha(invCompras.getCompFecha(), "yyyy-MM-dd"), "yyyy-MM-dd")) {
            comprobar = false;
            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invCompras.getEmpCodigo());
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (invCompras.getCompFecha().getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && invCompras.getCompFecha().getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    comprobar = true;
                    invCompras.getInvComprasPK().setCompPeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (comprobar) {
                if (!periodoCerrado) {
                    if (comprasMotivoDao.comprobarInvComprasMotivo(invCompras.getEmpCodigo(), invCompras.getInvComprasPK().getCompMotivo())) {
                        /// PREPARANDO OBJETO SISSUCESO (susClave y susDetalle se llenan en DAOTransaccion)
                        susSuceso = "INSERT";
                        susTabla = "inventario.inv_compra";
                        invCompras.setUsrFechaInserta(UtilsDate.timestamp());
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                        ///// BUSCANDO PROVEEDOR
                        String identificacion = invCompras.getInvProveedor().getProvIdNumero();
                        char tipo = identificacion.length() == 13 ? 'R' : identificacion.length() == 10 ? 'C' : 'N';
                        InvProveedorTO proveedorEnBD = proveedorService.getBuscaCedulaProveedorTO(invCompras.getEmpCodigo(), identificacion, tipo);
                        if (proveedorEnBD != null) {
                            InvProveedor invProveedor = new InvProveedor();
                            invProveedor.setInvProveedorPK(new InvProveedorPK(invCompras.getEmpCodigo(), proveedorEnBD.getProvCodigo()));
                            if (invProveedor != null) {
                                ////// CREANDO COMPRAS
                                invCompras.setSecEmpresa(invCompras.getEmpCodigo());
                                invCompras.setCompSaldoImportado(true);
                                invCompras.setCompImportacion(false);
                                invCompras.setCompFechaVencimiento(
                                        invCompras.getCompFechaVencimiento() != null
                                        ? invCompras.getCompFechaVencimiento()
                                        : invCompras.getCompFecha());
                                if (invCompras.getConNumero() == null || invCompras.getConPeriodo() == null || invCompras.getConNumero() == null) {
                                    invCompras.setConEmpresa(null);
                                    invCompras.setConNumero(null);
                                    invCompras.setConPeriodo(null);
                                    invCompras.setConTipo(null);
                                }
                                invCompras.setInvProveedor(invProveedor);
                                //////////// COMPROBAR SI NO EXISTE NUMERO DE FACTURA
                                comprobar = comprasDao.insertarInvComprasTO(invCompras, sisSuceso);
                                if (comprobar) {
                                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                            invCompras.getEmpCodigo(),
                                            invCompras.getInvComprasPK().getCompPeriodo());
                                    retorno = "T<html>La compra se guardó correctamente con la siguiente información:<br><br>"
                                            + "Periodo: <font size = 5>"
                                            + sisPeriodo.getPerDetalle()
                                            + "</font>.<br> Motivo: <font size = 5>"
                                            + invCompras.getInvComprasPK().getCompMotivo()
                                            + "</font>.<br> Número: <font size = 5>"
                                            + invCompras.getInvComprasPK().getCompNumero()
                                            + "</font>.</html>"
                                            + invCompras.getInvComprasPK().getCompNumero() + ","
                                            + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("pk", invCompras.getInvComprasPK());
                                    map.put("id", id);
                                    mensajeTO.setMap(map);
                                } else {
                                    retorno = "F<html>Hubo un error al guardar la Compra...\nIntente de nuevo o contacte con el administrador</html>";
                                }

                            } else {
                                retorno = "F<html>El proveedor no existe...\nIntente de nuevo o contacte con el administrador</html>";
                            }
                        } else {
                            retorno = "F<html>El proveedor no existe...\nIntente de nuevo o contacte con el administrador</html>";
                        }
                    } else {
                        retorno = "F<html>No se encuentra el motivo...</html>";
                    }
                } else {
                    retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                }
            } else {
                retorno = "F <html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }

        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public MensajeTO modificarInvCompras(InvCompras invCompras, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        boolean periodoCerrado = false;
        MensajeTO mensajeTO = new MensajeTO();
        // ojo -
        if (!UtilsValidacion.isFechaSuperior(UtilsValidacion.fecha(invCompras.getCompFecha(), "yyyy-MM-dd"), "yyyy-MM-dd")) {
            comprobar = false;
            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invCompras.getEmpCodigo());
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (invCompras.getCompFecha().getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && invCompras.getCompFecha().getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    comprobar = true;
                    invCompras.getInvComprasPK().setCompPeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (comprobar) {
                if (!periodoCerrado) {
                    if (comprasMotivoDao.comprobarInvComprasMotivo(invCompras.getEmpCodigo(), invCompras.getInvComprasPK().getCompMotivo())) {
                        /// PREPARANDO OBJETO SISSUCESO (susClave y susDetalle se llenan en DAOTransaccion)
                        susSuceso = "UPDATE";
                        susTabla = "inventario.inv_compra";
                        invCompras.setUsrFechaInserta(UtilsDate.timestamp());
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                        ///// BUSCANDO PROVEEDOR
                        List<InvProveedorTO> provedorList = proveedorService.getProveedorTO(invCompras.getEmpCodigo(), invCompras.getInvProveedor().getInvProveedorPK().getProvCodigo());
                        InvProveedorTO proveedorEnBD = provedorList != null && provedorList.size() > 0
                                ? provedorList.get(0) : null;
                        if (proveedorEnBD != null) {
                            InvProveedor invProveedor = new InvProveedor();
                            invProveedor.setInvProveedorPK(new InvProveedorPK(invCompras.getEmpCodigo(), proveedorEnBD.getProvCodigo()));
                            invProveedor.setProvIdNumero(proveedorEnBD.getProvId());
                            invProveedor.setProvRazonSocial(proveedorEnBD.getProvRazonSocial());
                            if (invProveedor != null) {
                                ////// CREANDO COMPRAS
                                invCompras.setSecEmpresa(invCompras.getEmpCodigo());
                                invCompras.setCompSaldoImportado(true);
                                invCompras.setCompImportacion(false);
                                invCompras.setCompFechaVencimiento(invCompras.getCompFecha());
                                if (invCompras.getConNumero() == null || invCompras.getConPeriodo() == null || invCompras.getConNumero() == null) {
                                    invCompras.setConEmpresa(null);
                                    invCompras.setConNumero(null);
                                    invCompras.setConPeriodo(null);
                                    invCompras.setConTipo(null);
                                }
                                invCompras.setInvProveedor(invProveedor);
                                //////////// COMPROBAR SI NO EXISTE NUMERO DE FACTURA
                                comprobar = comprasDao.modificarInvCompras(invCompras, sisSuceso);
                                if (comprobar) {
                                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                            invCompras.getEmpCodigo(),
                                            invCompras.getInvComprasPK().getCompPeriodo());
                                    retorno = "T<html>La compra se modificó correctamente con la siguiente información:<br><br>"
                                            + "Periodo: <font size = 5>"
                                            + sisPeriodo.getPerDetalle()
                                            + "</font>.<br> Motivo: <font size = 5>"
                                            + invCompras.getInvComprasPK().getCompMotivo()
                                            + "</font>.<br> Número: <font size = 5>"
                                            + invCompras.getInvComprasPK().getCompNumero()
                                            + "</font>.</html>"
                                            + invCompras.getInvComprasPK().getCompNumero() + ","
                                            + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("compra", invCompras);
                                    mensajeTO.setMap(map);
                                } else {
                                    retorno = "F<html>Hubo un error al guardar la Compra...\nIntente de nuevo o contacte con el administrador</html>";
                                }

                            } else {
                                retorno = "F<html>El proveedor no existe...\nIntente de nuevo o contacte con el administrador</html>";
                            }
                        } else {
                            retorno = "F<html>El proveedor no existe...\nIntente de nuevo o contacte con el administrador</html>";
                        }
                    } else {
                        retorno = "F<html>No se encuentra el motivo...</html>";
                    }
                } else {
                    retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                }
            } else {
                retorno = "F <html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }

        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public Map<String, Object> obtenerCompras(String empresa, String tipo, String numeroDocumento, String provCodigo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        InvCompras invCompras = comprasDao.obtenerCompras(empresa, tipo, numeroDocumento, provCodigo);
        if (invCompras != null) {
            map.put("compra", invCompras);

            AnxCompraTO retencion = compraService.getAnexoCompraTO(empresa,
                    invCompras.getInvComprasPK().getCompPeriodo(),
                    invCompras.getInvComprasPK().getCompMotivo(),
                    invCompras.getInvComprasPK().getCompNumero());
            if (retencion != null) {
                map.put("retencion", retencion);
            }
        }

        return map;
    }

    @Override
    public Map<String, Object> consultarCompraPorContablePk(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String conPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("conPeriodo"));
        String conTipo = UtilsJSON.jsonToObjeto(String.class, map.get("conTipo"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        String empresa = sisInfoTO.getEmpresa();
        InvCompraCabeceraTO invCompraCabeceraTO = comprasDao.consultarCompraPorContablePk(sisInfoTO.getEmpresa(), conPeriodo, conTipo, conNumero);

        if (invCompraCabeceraTO != null) {
            InvComprasTO invComprasTO = new InvComprasTO();
            invComprasTO.setBodEmpresa(empresa);
            invComprasTO.setBodCodigo(invCompraCabeceraTO.getBodCodigo());
            invComprasTO.setCompActivoFijo(invCompraCabeceraTO.getCompActivoFijo());
            invComprasTO.setCompAnulado(invCompraCabeceraTO.getCompAnulado());
            invComprasTO.setCompBase0(invCompraCabeceraTO.getCompBase0());
            invComprasTO.setCompBaseExenta(invCompraCabeceraTO.getCompBaseExenta());
            invComprasTO.setCompBaseImponible(invCompraCabeceraTO.getCompBaseimponible());
            invComprasTO.setCompBaseNoObjeto(invCompraCabeceraTO.getCompBaseNoObjeto());
            invComprasTO.setCompDocumentoFormaPago(invCompraCabeceraTO.getDocumentoFormaPago());
            invComprasTO.setCompDocumentoNumero(invCompraCabeceraTO.getCompDocumentoNumero());
            invComprasTO.setCompDocumentoTipo(invCompraCabeceraTO.getCompDocumentoTipo());
            invComprasTO.setCompElectronica(invCompraCabeceraTO.getCompElectronica());
            invComprasTO.setCompFecha(invCompraCabeceraTO.getCompfecha());
            invComprasTO.setCompFechaVencimiento(invCompraCabeceraTO.getCompFechaVencimiento());
            invComprasTO.setCompFormaPago(invCompraCabeceraTO.getCompFormaPago());
            invComprasTO.setCompIce(invCompraCabeceraTO.getCompIce());
            invComprasTO.setCompImportacion(invCompraCabeceraTO.getCompImportacion());
            invComprasTO.setCompIvaVigente(invCompraCabeceraTO.getCompIvaVigente());
            invComprasTO.setCompMontoIva(invCompraCabeceraTO.getCompMontoiva());
            invComprasTO.setCompMotivo(invCompraCabeceraTO.getCompMotivo());
            invComprasTO.setCompNumero(invCompraCabeceraTO.getCompNumero());
            invComprasTO.setCompObservaciones(invCompraCabeceraTO.getCompObservaciones());
            invComprasTO.setCompOtrosImpuestos(invCompraCabeceraTO.getCompOtrosImpuestos());
            invComprasTO.setCompPendiente(invCompraCabeceraTO.getCompPendiente());
            invComprasTO.setCompPeriodo(invCompraCabeceraTO.getCompPeriodo());
            invComprasTO.setCompRetencionAsumida(invCompraCabeceraTO.isRetencionAsumida());
            invComprasTO.setCompPropina(invCompraCabeceraTO.getCompPropina());
            invComprasTO.setCompRevisado(invCompraCabeceraTO.getCompRevisado());
            invComprasTO.setCompSaldo(invCompraCabeceraTO.getCompSaldo());
            invComprasTO.setCompTotal(invCompraCabeceraTO.getCompTotal());
            invComprasTO.setCompValorRetenido(invCompraCabeceraTO.getCompValorretenido());
            invComprasTO.setContNumero(invCompraCabeceraTO.getConNumero());
            invComprasTO.setContPeriodo(invCompraCabeceraTO.getConPeriodo());
            invComprasTO.setContTipo(invCompraCabeceraTO.getConTipo());
            invComprasTO.setEmpCodigo(empresa);
            invComprasTO.setProvCodigo(invCompraCabeceraTO.getProvCodigo());
            invComprasTO.setProvEmpresa(empresa);
            invComprasTO.setSecCodigo(invCompraCabeceraTO.getSecCodigo());
            invComprasTO.setSecEmpresa(empresa);
            invComprasTO.setUsrFechaInsertaCompra(invCompraCabeceraTO.getFechaUsuarioInserto());
            invComprasTO.setUsrInsertaCompra(invCompraCabeceraTO.getUsuarioInserto());
            //orden de compra
            invComprasTO.setOcEmpresa(invCompraCabeceraTO.getOcEmpresa());
            invComprasTO.setOcMotivo(invCompraCabeceraTO.getOcMotivo());
            invComprasTO.setOcSector(invCompraCabeceraTO.getOcSector());
            invComprasTO.setOcNumero(invCompraCabeceraTO.getOcNumero());
            invComprasTO.setOcOrdenPedido(invCompraCabeceraTO.getOcOrdenPedido());
            invComprasTO.setCompSaldoImportado(invCompraCabeceraTO.isCompSaldoImportado());
            invComprasTO.setCompProgramada(invCompraCabeceraTO.isCompProgramada());
            invComprasTO.setCompUsuarioApruebaPago(invCompraCabeceraTO.getCompUsuarioApruebaPago());
            invComprasTO.setCompFechaApruebaPago(invCompraCabeceraTO.getCompFechaApruebaPago());
            invComprasTO.setCompImbFacturado(invCompraCabeceraTO.isCompImbFacturado());
            invComprasTO.setCompTransportistaRuc(invCompraCabeceraTO.getCompTransportistaRuc());
            invComprasTO.setFechaUltimaValidacionSri(invCompraCabeceraTO.getFechaUltimaValidacionSri());
            invComprasTO.setFpSecuencial(invCompraCabeceraTO.getFpSecuencial());
            invComprasTO.setCtaCodigo(invCompraCabeceraTO.getCtaCodigo());
            invComprasTO.setCompGuiaCompra(invCompraCabeceraTO.getCompGuiaCompra());
            invComprasTO.setCompEsImb(invCompraCabeceraTO.getCompEsImb());
            String periodo = invComprasTO.getCompPeriodo();
            String motivo = invComprasTO.getCompMotivo();
            String numero = invComprasTO.getCompNumero();

            AnxLiquidacionComprasElectronica liquidacionCompra = liquidacionCompraElectronicaService.buscarAnxLiquidacionCompraElectronica(empresa, periodo, motivo, numero);
            boolean estaAutorizado = false;
            if (liquidacionCompra != null) {
                estaAutorizado = liquidacionCompra.geteEstado().equals("AUTORIZADO");
            }

            List<InvListadoPagosTO> listaInvListadoPagosTO = new ArrayList<>();
            ItemResultadoBusquedaElectronico itemResultadoBusquedaElectronico = null;

            /*OC*/
            List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
            List<InvPedidosOrdenCompraMotivoTO> listaMotivosOC = new ArrayList<>();
            if (invComprasTO.getOcSector() != null) {
                listaMotivosOC = pedidosOrdenCompraMotivoService.getListaInvPedidosOrdenCompraMotivo(empresa, invComprasTO.getOcSector());
            }
            /*Listado*/
            List<InvComprasDetalle> listaInvCompraDetalle = comprasDetalleService.obtenerCompraDetallePorNumero(empresa, periodo, motivo, numero);
            List<InvListaDetalleComprasTO> listaInvListaDetalleComprasTO = comprasDetalleService.getListaInvCompraDetalleTO(empresa, periodo, motivo, numero);

            boolean tieneSeriesOcupadas = false;
            for (InvComprasDetalle detalle : listaInvCompraDetalle) {
                for (InvListaDetalleComprasTO detalleTO : listaInvListaDetalleComprasTO) {
                    detalleTO.setDetCreditoTributario(detalle.getProCreditoTributario());
                    if (detalleTO.getSecuencial().equals(detalle.getDetSecuencial())) {
                        //Series
                        boolean algunaSerieOcupada = false;
                        List<InvComprasDetalleSeriesTO> listaSerieTO = new ArrayList<>();
                        List<InvComprasDetalleSeries> listaSerie = comprasDetalleSeriesDao.listarSeriesPorSecuencialDetalle(detalle.getDetSecuencial());
                        if (listaSerie != null && listaSerie.size() > 0) {
                            for (InvComprasDetalleSeries serie : listaSerie) {
                                InvComprasDetalleSeriesTO serieTO = ConversionesInventario.convertirInvComprasDetalleSeries_InvComprasDetalleSeriesTO(serie);
                                serieTO.setSerieOcupada(invSeriesService.serieCompraOcupada(empresa, serieTO.getDetNumeroSerie(), detalleTO.getCodigoProducto()));
                                if (serieTO.isSerieOcupada()) {
                                    tieneSeriesOcupadas = true;
                                    algunaSerieOcupada = true;
                                }
                                listaSerieTO.add(serieTO);
                            }
                        }
                        if (algunaSerieOcupada) {
                            detalleTO.setSerieOCupada(algunaSerieOcupada);
                        }
                        detalleTO.setInvComprasDetalleSeriesListTO(listaSerieTO);
                        break;
                    }
                }

            }
            /*Anexo si tiene RETENCION O NO*/
            AnxCompraTO anxCompraTO = compraDao.getAnexoCompraTO(empresa, periodo, motivo, numero);

            /*Proveedor*/
            InvProveedorTO proveedor = proveedorService.getProveedorTO(empresa, invComprasTO.getProvCodigo()).get(0);
            String codigoTipoTransaccion = tipoTransaccionService.getCodigoAnxTipoTransaccionTO(proveedor.getProvTipoId(), "COMPRA");

            /*Forma pago*/
            List<InvComboFormaPagoTO> listaFormaPago = comprasFormaPagoService.getComboFormaPagoCompra(empresa);
            List<InvListaBodegasTO> listaInvListaBodegasTO = bodegaService.buscarBodegasTO(empresa, false, null);
            List<PrdListaPiscinaTO> piscinas = piscinaService.getListaPiscinaTO(empresa.trim(), invComprasTO.getSecCodigo(), false);
            /*Documentos*/
            List<AnxTipoComprobanteComboTO> listaDocumentos = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
            /*Transportistas*/
            List<InvProveedorTransportistaTO> transportistas = invProveedorTransportistaService.listarTransportistasTO(empresa, proveedor.getProvCodigo());
            /*Lista motivos*/
            List<InvComprasMotivoTO> listaInvComprasMotivoTO = comprasMotivoService.getListaInvComprasMotivoTO(empresa, true);
            SisConfiguracionCompras sisConfiguracionCompras = configuracionComprasService.getSisConfiguracionCompras(new SisConfiguracionComprasPK(empresa, sisInfoTO.getUsuario()));

            /*comprobante electronico*/
            boolean puedeAnular = true;
            boolean puedeAnularCompra = true;
            boolean reintentarSRI = false;
            if (anxCompraTO != null && invComprasTO.getCompElectronica() && accion != null && !accion.equalsIgnoreCase("Consultar")) {
                boolean estadoSRI = urlWebServicesService.verifcarDisponibilidadSRI();
                if (estadoSRI) {
                    try {
                        if (accion != null && accion.equalsIgnoreCase("Anular")) {
                            //esto es para ver si puede anular la retencion
                            RespuestaComprobante respuestaComprobanteRetencion = urlWebServicesService.getAutorizadocionComprobantes(anxCompraTO.getCompRetencionAutorizacion(), TipoAmbienteEnum.PRODUCCION.getCode(), sisInfoTO);
                            if (respuestaComprobanteRetencion != null) {
                                if (!respuestaComprobanteRetencion.getAutorizaciones().getAutorizacion().isEmpty()) {
                                    puedeAnular = false;
                                }
                            }
                        }
                        //esto es para maximos y minimos de la compra
                        RespuestaComprobante respuestaComprobanteCompra = urlWebServicesService.getAutorizadocionComprobantes(anxCompraTO.getCompAutorizacion(), TipoAmbienteEnum.PRODUCCION.getCode(), sisInfoTO);
                        if (respuestaComprobanteCompra != null) {
                            Autorizacion comprobanteAutorizado = obtenerComprobanteAutorizado(respuestaComprobanteCompra);
                            if (comprobanteAutorizado != null) {
                                itemResultadoBusquedaElectronico = ItemResultadoBusquedaElectronico.convertirAutorizacionEnItemResultadoBusquedaElectronico(comprobanteAutorizado);
                                puedeAnularCompra = false;
                            }
                        }
                    } catch (Exception e) {
                        puedeAnular = false;
                        puedeAnularCompra = false;
                        reintentarSRI = false;
                    }
                } else {
                    puedeAnular = false;
                    puedeAnularCompra = false;
                    reintentarSRI = true;//primer intento
                }
            }
            SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(UtilsDate.DeStringADate(invComprasTO.getCompFecha()), empresa);

            Timestamp fechaActual = sistemaWebServicio.getFechaActual();
            boolean comprobarRetencionAutorizadaProcesamiento = compraElectronicaService.comprobarRetencionAutorizadaProcesamiento(empresa, periodo, motivo, numero);
            BigDecimal valorPorcentaje = porcentajeIvaService.getValorAnxPorcentajeIvaTO(invComprasTO.getCompFecha());

            //Orden compra
            InvPedidosOrdenCompra invPedidosOrdenCompra = null;
            List<OrdenCompraSaldo> listaOrdenCompraSaldo = new ArrayList<>();
            if (invComprasTO.getOcEmpresa() != null && invComprasTO.getOcMotivo() != null && invComprasTO.getOcNumero() != null) {
                InvPedidosOrdenCompraPK pk = new InvPedidosOrdenCompraPK(invComprasTO.getOcEmpresa(), invComprasTO.getOcSector(), invComprasTO.getOcMotivo(), invComprasTO.getOcNumero());
                invPedidosOrdenCompra = pedidosOrdenCompraService.getInvPedidosOrdenCompra(pk);
                listaOrdenCompraSaldo = pedidosOrdenCompraService.getInvPedidosOrdenCompraSaldo(pk);
            }
            //licencia scanner
            SisScannerConfiguracion scanner = scannerConfiguracionDao.obtenerPorId(SisScannerConfiguracion.class, empresa);
            if (scanner != null) {
                campos.put("licencia", scanner.getScLicencia());
            }
            List<SisPeriodo> listadoPeriodos = periodoService.getListaPeriodo(empresa);
            //listado IMB
            List<InvComprasDetalleImbTO> listaInvComprasDetalleImbTO = new ArrayList<>();
            List<InvComprasDetalleImb> listaInvComprasDetalleImb = comprasDetalleImbService.getListInvComprasDetalleImb(empresa, periodo, motivo, numero);

            if (listaInvComprasDetalleImb != null && listaInvComprasDetalleImb.size() > 0) {
                for (int i = 0; i < listaInvComprasDetalleImb.size(); i++) {
                    InvComprasDetalleImbTO item = new InvComprasDetalleImbTO();
                    item.setDetSecuencia(listaInvComprasDetalleImb.get(i).getDetSecuencial());
                    item.setComEmpresa(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompEmpresa());
                    item.setComPeriodo(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompPeriodo());
                    item.setComMotivo(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompMotivo());
                    item.setComNumero(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompNumero());
                    item.setComImbEmpresa(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompEmpresa());
                    item.setComImbPeriodo(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompPeriodo());
                    item.setComImbMotivo(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompMotivo());
                    item.setComImbNumero(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompNumero());
                    item.setComImbTotal(listaInvComprasDetalleImb.get(i).getCompImbTotal());
                    item.setProvImbCodigo(listaInvComprasDetalleImb.get(i).getInvProveedorImb().getInvProveedorPK().getProvCodigo());
                    item.setProvImbEmpresa(listaInvComprasDetalleImb.get(i).getInvProveedorImb().getInvProveedorPK().getProvEmpresa());
                    //fecha
                    InvCompraCabeceraTO invCompraCabecera = getInvCompraCabeceraTO(empresa, item.getComImbPeriodo(), item.getComImbMotivo(), item.getComImbNumero());
                    item.setComImbFecha(invCompraCabecera.getCompfecha());
                    listaInvComprasDetalleImbTO.add(item);
                }
            }
            //listado LIQUIDACIONES
            List<PrdLiquidacionMotivo> listaMotivoLiquidacion = liquidacionMotivoService.getListaPrdLiquidacionMotivoTO(empresa, true);
            List<InvComprasLiquidacionTO> listaInvComprasLiquidacionTO = new ArrayList<>();
            List<InvComprasLiquidacion> listaInvComprasLiquidacion = comprasLiquidacionDao.getListInvComprasLiquidacion(empresa, periodo, motivo, numero);

            if (listaInvComprasLiquidacion != null && listaInvComprasLiquidacion.size() > 0) {
                for (int i = 0; i < listaInvComprasLiquidacion.size(); i++) {
                    InvComprasLiquidacionTO item = new InvComprasLiquidacionTO();
                    item.setDetSecuencia(listaInvComprasLiquidacion.get(i).getDetSecuencial());
                    item.setComEmpresa(listaInvComprasLiquidacion.get(i).getInvCompras().getInvComprasPK().getCompEmpresa());
                    item.setComPeriodo(listaInvComprasLiquidacion.get(i).getInvCompras().getInvComprasPK().getCompPeriodo());
                    item.setComMotivo(listaInvComprasLiquidacion.get(i).getInvCompras().getInvComprasPK().getCompMotivo());
                    item.setComNumero(listaInvComprasLiquidacion.get(i).getInvCompras().getInvComprasPK().getCompNumero());
                    item.setLiqEmpresa(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqEmpresa());
                    item.setLiqMotivo(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqMotivo());
                    item.setLiqNumero(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqNumero());
                    item.setLiqTotal(listaInvComprasLiquidacion.get(i).getLiqTotal());
                    item.setLiqFecha(UtilsValidacion.fecha(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getLiqFecha(), "yyyy-MM-dd"));
                    item.setLiqLibras(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getLiqLibrasEntero().add(listaInvComprasLiquidacion.get(i).getPrdLiquidacion().getLiqLibrasColaProcesadas()));
                    listaInvComprasLiquidacionTO.add(item);
                }
            }
            List<SisEmpresa> listaEmpresa = usuarioDetalleService.getEmpresasPorUsuarioItem(sisInfoTO.getUsuario(), "pagosCartera", empresa);
            List<PrdEquipoControl> listadoEquiposControl = equipoControlDao.listarEquiposControl(empresa);
            campos.put("listaEmpresa", listaEmpresa);
            campos.put("listaInvComprasLiquidacionTO", listaInvComprasLiquidacionTO);
            campos.put("listaMotivoLiquidacion", listaMotivoLiquidacion);
            campos.put("listadoPeriodos", listadoPeriodos);
            campos.put("listaInvComprasDetalleImbTO", listaInvComprasDetalleImbTO);
            campos.put("invPedidosOrdenCompra", invPedidosOrdenCompra);
            campos.put("listaOrdenCompraSaldo", listaOrdenCompraSaldo);
            // campos.put("listaImagenes", listaImagenes);
            campos.put("listaMotivosOC", listaMotivosOC);
            campos.put("listaSectores", listaSectores);
            campos.put("piscinas", piscinas);
            campos.put("listaInvComprasMotivoTO", listaInvComprasMotivoTO);
            campos.put("listaFormaPago", listaFormaPago);
            campos.put("listaDocumentos", listaDocumentos);
            campos.put("invComprasTO", invComprasTO);
            campos.put("fechaActual", fechaActual);
            campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
            campos.put("codigoTipoTransaccion", codigoTipoTransaccion);
            campos.put("proveedor", proveedor);
            campos.put("listaEquiposControl", listadoEquiposControl);
            campos.put("listaInvListaDetalleComprasTO", listaInvListaDetalleComprasTO);
            campos.put("anxCompraTO", anxCompraTO);
            campos.put("tieneSeriesOcupadas", tieneSeriesOcupadas);
            campos.put("valorPorcentaje", valorPorcentaje);
            campos.put("listaInvListadoPagosTO", listaInvListadoPagosTO);
            campos.put("puedeAnular", puedeAnular);
            campos.put("puedeAnularCompra", puedeAnularCompra);
            campos.put("transportistas", transportistas);
            campos.put("reintentarSRI", reintentarSRI);
            campos.put("itemResultadoBusquedaElectronico", itemResultadoBusquedaElectronico);
            campos.put("comprobarRetencionAutorizadaProcesamiento", comprobarRetencionAutorizadaProcesamiento);
            campos.put("sisConfiguracionCompras", sisConfiguracionCompras);
            campos.put("listaInvListaBodegasTO", listaInvListaBodegasTO);
            campos.put("estaAutorizadoLiquidacion", estaAutorizado);
            return campos;
        } else {
            return null;
        }
    }

}
