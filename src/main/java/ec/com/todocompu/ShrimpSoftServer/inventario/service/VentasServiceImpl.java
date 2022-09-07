package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.PorcentajeIvaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.TipoComprobanteDao;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaElectronicaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnexoNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxVentaReembolsoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ExportacionesService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoComprobanteService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.UrlWebServicesService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaService;
import ec.com.todocompu.ShrimpSoftServer.caja.dao.CajaDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.DetalleDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.BodegaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteContratoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClientesDireccionesDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VendedorDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasComplementoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDetalleSeriesDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasFormaCobroDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasLiquidacionDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasMotivoAnulacionDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.NotificacionService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioDetalleService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesSistema;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.UtilsMail;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.ClaveDeAcceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaReembolso;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.enums.TipoNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteGrupoEmpresarialTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteRecurrenteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaVentasFacturasPorNumeroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosCoberturaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVsCostoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaSecuencialesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListadoCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoFormula;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplemento;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteVentaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisLoginTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoAmbienteEnum;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.Autorizacion;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VentasServiceImpl implements VentasService {

    @Autowired
    private BodegaDao bodegaDao;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private VentaElectronicaDao ventaElectronicaDao;
    @Autowired
    private VentasDao ventasDao;
    @Autowired
    private VentasDetalleDao ventasDetalleDao;
    @Autowired
    private VentasDetalleSeriesDao ventasDetalleSeriesDao;
    @Autowired
    private VentasComplementoDao ventasComplementoDao;
    @Autowired
    private VentasMotivoAnulacionDao ventasMotivoAnulacionDao;
    @Autowired
    private VentasMotivoDao ventasMotivoDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private EmpresaDao empresaDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private DetalleDao detalleDao;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private ProductoSaldosService productoSaldosService;
    @Autowired
    private CobrosDao cobrosDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private AnexoNumeracionService numeracionService;
    @Autowired
    private CajaDao cajaDao;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private PiscinaDao piscinaDao;
    @Autowired
    private VentasFormaCobroDao ventaFormaCobroDao;
    @Autowired
    private PorcentajeIvaDao porcentajeIvaDao;
    @Autowired
    private TipoComprobanteDao tipoComprobanteDao;
    @Autowired
    private ClienteCategoriaService clienteCategoriaService;
    @Autowired
    private ProformasMotivoService proformasMotivoService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private VendedorDao vendedorDao;
    @Autowired
    private UrlWebServicesService urlWebServicesService;
    @Autowired
    private InvClienteGrupoEmpresarialService invClienteGrupoEmpresarialService;
    @Autowired
    private ClientesDireccionesDao clientesDireccionesDao;
    @Autowired
    private LiquidacionMotivoService liquidacionMotivoService;
    @Autowired
    private VentasLiquidacionDao ventasLiquidacionDao;
    @Autowired
    private ExportacionesService exportacionesService;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;
    @Autowired
    private ProductoFormulaService productoFormulaService;
    @Autowired
    private VentasFormaCobroService ventaFormaCobroService;
    @Autowired
    private ClienteContratoDao clienteContratoDao;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private TipoComprobanteService tipoComprobanteService;
    private boolean comprobar = false;
    private BigDecimal cero = new BigDecimal("0.00");
    private BigDecimal cien = new BigDecimal("100.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private AnxVentaReembolsoService anxVentaReembolsoService;

    @Override
    public String getConteoNumeroFacturaVenta(String empresaCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception {
        return ventasDao.getConteoNumeroFacturaVenta(empresaCodigo, compDocumentoTipo, compDocumentoNumero);
    }

    @Override
    public Object[] getVenta(String empresa, String perCodigo, String motCodigo, String compNumero) throws Exception {
        return ventasDao.getVenta(empresa, perCodigo, motCodigo, compNumero);
    }

    @Override
    public InvVentaCabeceraTO getInvVentaCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroVenta) throws Exception {
        return ventasDao.getInvVentaCabeceraTO(empresa, codigoPeriodo, motivo, numeroVenta);
    }

    @Override
    public List<InvListaConsultaVentaTO> getFunVentasListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception {
        return ventasDao.getFunVentasListado(empresa, fechaDesde, fechaHasta, status);
    }

    @Override
    public List<InvListaConsultaVentaTO> getListaInvConsultaVenta(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros) throws Exception {
        return ventasDao.getListaInvConsultaVenta(empresa, periodo, motivo, busqueda, nRegistros);
    }

    @Override
    public List<InvListaConsultaVentaTO> getListaInvConsultaVentaPorTipoDoc(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, String tipoDocumento) throws Exception {
        return ventasDao.getListaInvConsultaVentaPorTipoDoc(empresa, periodo, motivo, busqueda, nRegistros, tipoDocumento);
    }

    @Override
    public List<InvListaConsultaVentaTO> getListaInvConsultaVentaFiltrado(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, String tipoDocumento) throws Exception {
        return ventasDao.getListaInvConsultaVentaFiltrado(empresa, periodo, motivo, busqueda, nRegistros, tipoDocumento);
    }

    @Override
    public List<InvListadoCobrosTO> invListadoCobrosTO(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return ventasDao.invListadoCobrosTO(empresa, periodo, motivo, numero);
    }

    @Override
    public List<InvFunVentasTO> getInvFunVentasTO(String empresa, String desde, String hasta, String motivo,
            String cliente, String documento, String grupo_empresarial) throws Exception {
        return ventasDao.getInvFunVentasTO(empresa, desde, hasta, motivo, cliente, documento, grupo_empresarial);
    }

    @Override
    public List<InvFunVentasTO> listarInvFunVentasTO(String empresa, String desde, String hasta, String motivo,
            String cliente, String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception {
        return ventasDao.listarInvFunVentasTO(empresa, desde, hasta, motivo, cliente, documento, sector, estado, grupo_empresarial, formaCobro, incluirTodos);
    }

    @Override
    public List<InvFunVentasTO> listarInvFunVentasTOAgrupadoTipoDocumento(String empresa, String desde, String hasta, String motivo, String cliente, String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception {
        return ventasDao.listarInvFunVentasTOAgrupadoTipoDocumento(empresa, desde, hasta, motivo, cliente, documento, sector, estado, grupo_empresarial, formaCobro, incluirTodos);
    }

    @Override
    public List<InvFunVentasTO> listarInvFunVentasTOAgrupadoTipoContribuyente(String empresa, String desde, String hasta, String motivo, String cliente, String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception {
        return ventasDao.listarInvFunVentasTOAgrupadoTipoContribuyente(empresa, desde, hasta, motivo, cliente, documento, sector, estado, grupo_empresarial, formaCobro, incluirTodos);
    }

    @Override
    public List<InvFunVentasVendedorTO> listarInvFunVentasVendedorTO(String empresa, String desde, String hasta) throws Exception {
        return ventasDao.listarInvFunVentasVendedorTO(empresa, desde, hasta);
    }

    @Override
    public List<InvFunVentasConsolidandoProductosTO> getInvFunVentasConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega, String cliente) throws Exception {
        return ventasDao.getInvFunVentasConsolidandoProductosTO(empresa, desde, hasta, sector, bodega, cliente);
    }

    @Override
    public List<InvFunVentasConsolidandoProductosCoberturaTO> getInvFunVentasConsolidandoProductosCoberturaTO(String empresa,
            String desde, String hasta, String sector, String bodega, String motivo, String cliente) throws Exception {
        return ventasDao.getInvFunVentasConsolidandoProductosCoberturaTO(empresa, desde, hasta, sector, bodega, motivo, cliente);
    }

    @Override
    public List<InvFunVentasConsolidandoClientesTO> getInvFunVentasConsolidandoClientesTO(String empresa, String sector,
            String desde, String hasta) throws Exception {
        return ventasDao.getInvFunVentasConsolidandoClientesTO(empresa, sector, desde, hasta);
    }

    @Override
    public List<InvFunVentasVsCostoTO> getInvFunVentasVsCostoTO(String empresa, String desde, String hasta,
            String bodega, String cliente) throws Exception {
        return ventasDao.getInvFunVentasVsCostoTO(empresa, desde, hasta, bodega, cliente);
    }

    @Override
    public MensajeTO insertarInvContableVentasTO(String empresa, String periodo, String motivo, String ventaNumero,
            String codigoUsuario, boolean recontabilizar, String conNumero, String tipCodigo, SisInfoTO sisInfoTO)
            throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        String retorno = "";
        String tipoCodigo = "";
        boolean contablePendiente = false;
        List<ConFunContabilizarVentasDetalleTO> listaConDetalleTO;
        List<String> mensajeClase = new ArrayList<String>();

        if (!recontabilizar) {
            tipoCodigo = tipoDao.buscarTipoContablePorMotivoVenta(empresa, motivo);
        } else {
            tipoCodigo = tipCodigo;
        }
        if (tipoDao.buscarTipoContable(empresa, tipoCodigo)) {
            InvVentas invVentas = ventasDao.buscarInvVentas(empresa, periodo, motivo, ventaNumero);
            if (invVentas == null) {
                retorno = "F<html>No se encontró venta para contabilizar.</html>";
            } else if (invVentas.getConNumero() != null && !recontabilizar) {
                retorno = "F<html>La venta ya ha sido recontabilizada.</html>";
            } else {
                listaConDetalleTO = contableService.getConFunContabilizarVentasDetalle(empresa, periodo, motivo, ventaNumero, "VALIDAR");
                for (ConFunContabilizarVentasDetalleTO conFunContabilizarComprasDetalleTO : listaConDetalleTO) {
                    if (conFunContabilizarComprasDetalleTO.getDetObservaciones() != null) {
                        mensajeClase.add(conFunContabilizarComprasDetalleTO.getDetObservaciones());
                    }
                }
                if (mensajeClase.isEmpty()) {
                    ////// CREANDO CONTABLE TO
                    ConContableTO conContableTO = new ConContableTO();
                    conContableTO.setEmpCodigo(empresa);
                    conContableTO.setPerCodigo(periodo);
                    conContableTO.setTipCodigo(tipoCodigo);
                    conContableTO.setConFecha(UtilsValidacion.fecha(invVentas.getVtaFecha(), "yyyy-MM-dd"));
                    conContableTO.setConPendiente(false);
                    conContableTO.setConBloqueado(false);
                    conContableTO.setConAnulado(false);
                    conContableTO.setConGenerado(true);
                    conContableTO.setConConcepto(invVentas.getInvCliente().getCliRazonSocial().trim());
                    conContableTO.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                    conContableTO.setConObservaciones(invVentas.getVtaObservaciones());
                    conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                    conContableTO.setUsrInsertaContable(codigoUsuario);
                    ///// CREANDO UN SUCESO
                    susSuceso = "INSERT";
                    susTabla = "contabilidad.con_contable";
                    conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                    ////// CREANDO NUMERACION
                    ConNumeracion conNumeracion = new ConNumeracion(new ConNumeracionPK(conContableTO.getEmpCodigo(), conContableTO.getPerCodigo(), conContableTO.getTipCodigo()));
                    conNumeracion.setNumSecuencia(new Integer(invVentas.getInvVentasPK().getVtaNumero()));
                    ////// CREANDO CONTABLE
                    ConContable conContable;
                    if (!recontabilizar) {
                        conContable = ConversionesContabilidad.convertirConContableTO_ConContable(conContableTO);
                        conContable.setConReversado(false);
                    } else {
                        susSuceso = "UPDATE";
                        susTabla = "contabilidad.con_contable";
                        conContable = contableService.obtenerPorId(empresa, periodo, tipoCodigo, conNumero);
                        conContable.setConConcepto(invVentas.getInvCliente().getCliRazonSocial().trim());
                        conContable.setConObservaciones(invVentas.getVtaObservaciones());
                        conContable.setConFecha(invVentas.getVtaFecha());
                    }
                    ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                    List<ConDetalle> listConDetalle = new ArrayList<ConDetalle>();
                    List<ConDetalle> listConDetalleEliminar = new ArrayList<ConDetalle>();
                    ConDetalle conDetalle = null;
                    listaConDetalleTO = contableService.getConFunContabilizarVentasDetalle(empresa, periodo, motivo, ventaNumero, null);

                    for (ConFunContabilizarVentasDetalleTO conDetalleTO : listaConDetalleTO) {
                        conDetalle = new ConDetalle();
                        conDetalle = ConversionesContabilidad.convertirConFunContabilizarVentasDetalleTO_ConDetalle(conDetalleTO);
                        listConDetalle.add(conDetalle);
                    }
                    List<InvVentas> invVentases = new ArrayList<InvVentas>();
                    invVentases.add(invVentas);

                    if (!recontabilizar) {
                        susDetalle = "Se ingresó contable del periodo "
                                + conContable.getConContablePK().getConPeriodo() + ", del tipo contable "
                                + conContable.getConContablePK().getConTipo() + ", de la numeracion " + conNumero;
                        susClave = conContable.getConContablePK().getConPeriodo() + " "
                                + conContable.getConContablePK().getConTipo() + " " + conNumero;
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                        comprobar = contableService.insertarTransaccionContableVenta(conContable, listConDetalle, sisSuceso, conNumeracion, invVentases, sisInfoTO);
                    } else {
                        susDetalle = "Se recontabilizó contable del periodo "
                                + conContable.getConContablePK().getConPeriodo() + ", del tipo contable "
                                + conContable.getConContablePK().getConTipo() + ", de la numeracion " + conNumero;
                        susClave = conContable.getConContablePK().getConPeriodo() + " "
                                + conContable.getConContablePK().getConTipo() + " " + conNumero;
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                        InvVentas invVentasAux = ConversionesInventario.convertirInvVentas_InvVentas(invVentas);
                        invVentasAux.setVtaRevisado(false);

                        List<ConDetalleTO> listaConDetalleEliminar = detalleDao.getConDetalleTO(empresa, periodo, tipoCodigo, conNumero);
                        for (ConDetalleTO conDetalleTO : listaConDetalleEliminar) {
                            listConDetalleEliminar.add(detalleDao.obtenerPorId(ConDetalle.class, conDetalleTO.getDetSecuencia().longValue()));
                        }
                        comprobar = contableDao.modificarConContableVentasMayorizar(conContable, listConDetalle, listConDetalleEliminar, invVentasAux, sisSuceso);
                    }

                    if (comprobar) {
                        SisPeriodo sisPeriodo = periodoService.buscarPeriodo(conContableTO.getEmpCodigo(), conContable.getConContablePK().getConPeriodo());
                        ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(conContableTO.getEmpCodigo(), conContable.getConContablePK().getConTipo()));
                        if (!recontabilizar) {
                            retorno = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                                    + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                    + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                    + "</font>.<br> Número: <font size = 5>"
                                    + conContable.getConContablePK().getConNumero() + "</font>."
                                    + (contablePendiente == true
                                            ? "</font>.<font size = 5 color= " + "red"
                                            + " ><br><b><small>Observación: PENDIENTE POR DIFERENCIA</small></b></font>"
                                            : "")
                                    + "</html>" + conTipo.getConTipoPK().getTipCodigo() + ", "
                                    + conContable.getConContablePK().getConNumero();
                            mensajeTO.getMap().put("conContable", conContable);
                        } else {
                            retorno = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                                    + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                    + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                    + "</font>.<br> Número: <font size = 5>"
                                    + conContable.getConContablePK().getConNumero() + "</font>."
                                    + (contablePendiente == true
                                            ? "</font>.<font size = 5 color= " + "red"
                                            + " ><br><b><small>Observación: PENDIENTE POR DIFERENCIA</small></b></font>"
                                            : "")
                                    + "</html>" + conTipo.getConTipoPK().getTipCodigo() + ", "
                                    + conContable.getConContablePK().getConNumero();
                            mensajeTO.getMap().put("conContable", conContable);
                        }
                    } else {
                        retorno = "F<html>Ocurrió un error al guardar el contable, inténtelo de nuevo...</html>";
                    }
                } else {
                    retorno = "F<html>Existen los Siguientes Errores...</html>";
                    mensajeTO.setListaErrores1(mensajeClase);
                }
            }
        } else {
            retorno = "F<html>No se encuentra tipo de contable...</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public List<ReporteVentaDetalle> obtenerReporteVentaDetalleProducto(String empresa, String numero1, SisInfoTO sisInfoTO) throws Exception {
        List<ReporteVentaDetalle> listadoDetalle = new ArrayList<>();
        List<String> documento = UtilsValidacion.separar(numero1, "|");
        if (documento != null && !documento.isEmpty()) {
            InvVentas venta = ventasDao.buscarInvVentas(empresa, documento.get(0), documento.get(1), documento.get(2));
            if (venta != null) {
                InvVentasTO ventasTO = ConversionesInventario.convertirInvVentas_InvVentasTO(venta);
                List<InvVentasDetalle> detalles = ventasDao.obtenerVentaDetallePorNumero(empresa, venta.getInvVentasPK().getVtaPeriodo(), venta.getInvVentasPK().getVtaMotivo(), venta.getInvVentasPK().getVtaNumero());
                List<InvVentasDetalleTO> detallesTO = new ArrayList<>();
                for (InvVentasDetalle detalle : detalles) {
                    detallesTO.add(ConversionesInventario.convertirInvVentasDetalle_InvVentasDetalleTO(detalle));
                }
                listadoDetalle.addAll(obtenerReporteVentaDetalle(ventasTO, detallesTO, false, sisInfoTO));
            }

        }
        return listadoDetalle;
    }

    @Override
    public List<ReporteVentaDetalle> obtenerReporteVentaDetalle(InvVentasTO ventasTO, List<InvVentasDetalleTO> listInvVentasDetalleTO, boolean isComprobanteElectronico, SisInfoTO sisInfoTO) throws Exception {
        InvCliente cliente = clienteDao.buscarInvCliente(ventasTO.getCliEmpresa(), ventasTO.getCliCodigo());
        InvVendedor vendedor = vendedorDao.buscarInvVendedor(ventasTO.getVtaEmpresa(), ventasTO.getVtaVendedor());
        boolean isNumeroAmbienteProduccion = false;
        InvVentasComplemento invVentasComplemento = null;
        //Si es NOTA CREDITO O NOTA DEBITO
        if (ventasTO.getVtaDocumentoTipo().equals("04") || ventasTO.getVtaDocumentoTipo().equals("05")) {
            invVentasComplemento = ventasComplementoDao.buscarVentasComplemento(ventasTO.getVtaEmpresa(), ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
        }

        AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(sisInfoTO.getEmpresa(), ventasTO.getVtaDocumentoNumero(), ventasTO.getVtaDocumentoTipo(), ventasTO.getVtaFecha());
        if (anxNumeracionLineaTO != null) {
            isNumeroAmbienteProduccion = anxNumeracionLineaTO.isNumeroAmbienteProduccion();
            isComprobanteElectronico = anxNumeracionLineaTO.isNumeroDocumentoElectronico();
        }
        if (ventasTO.getVtaDocumentoTipo().equals("00")) {
            isComprobanteElectronico = false;
        }
        String claveDeAcceso = null;
        if (isComprobanteElectronico) {
            claveDeAcceso = new ClaveDeAcceso().generaClave(
                    UtilsValidacion.fecha(ventasTO.getVtaFecha(), "yyyy-MM-dd"),
                    ventasTO.getVtaDocumentoTipo().equals("18") ? "01" : ventasTO.getVtaDocumentoTipo(),
                    sisInfoTO.getEmpresaRuc(),
                    isNumeroAmbienteProduccion ? "2" : "1",
                    ventasTO.getVtaDocumentoNumero() != null ? ventasTO.getVtaDocumentoNumero().substring(0, 7).replaceAll("-", "") : "",
                    ventasTO.getVtaDocumentoNumero() != null ? ventasTO.getVtaDocumentoNumero().substring(8, 17) : "",
                    "26031982", "1");
        }
        List<ReporteVentaDetalle> lista = new ArrayList();
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
        if (empresaParametros != null && (empresaParametros.getParActividad().equals("CAMARONERA") || empresaParametros.getParActividad().equals("MINERA"))) {
            for (InvVentasDetalleTO item : listInvVentasDetalleTO) {
                if (item.getProComplementario() == null || item.getProComplementario().equals("")) {
                    ReporteVentaDetalle reporteVentaDetalle = ConversionesInventario.obtenerItemReporte(claveDeAcceso, ventasTO, invVentasComplemento, item, cliente, vendedor);
                    lista.add(reporteVentaDetalle);
                }

            }
        } else {
            for (int y = 1; y <= 2; y++) {
                for (InvVentasDetalleTO item : listInvVentasDetalleTO) {
                    if (item.getProComplementario() == null || item.getProComplementario().equals("")) {
                        BigDecimal cantidad = item.getDetCantidad();
                        BigDecimal cantidadCaja = BigDecimal.ZERO; //Ver
                        boolean esUnidad = cantidadCaja.compareTo(new BigDecimal("0")) == 0 || cantidad.remainder(cantidadCaja).compareTo(new BigDecimal("0")) > 0;
                        if ((esUnidad && y == 1) || (!esUnidad && y == 2)) {
                            ReporteVentaDetalle reporteVentaDetalle = ConversionesInventario.obtenerItemReporte(claveDeAcceso, ventasTO, invVentasComplemento, item, cliente, vendedor);
                            lista.add(reporteVentaDetalle);
                        }
                    }
                }
            }
        }
        return lista;
    }

    @Override
    public InvVentaRetencionesTO getInvVentaRetencionesTO(String codigoEmpresa, String facturaNumero) throws Exception {
        InvVentas invVentasCreadas = null;
        InvVentaRetencionesTO invVentaRetencionesTO = null;
        InvConsultaVentasFacturasPorNumeroTO consultaVentasFacturasPorNumeroTO = new InvConsultaVentasFacturasPorNumeroTO();
        consultaVentasFacturasPorNumeroTO = ventasDao.getConsultaVentasFacturasPorNumeroTO(codigoEmpresa,
                facturaNumero);
        if (consultaVentasFacturasPorNumeroTO != null) {
            invVentasCreadas = new InvVentas();
            invVentasCreadas = ventasDao.buscarInvVentas(consultaVentasFacturasPorNumeroTO.getVtaEmpresa(),
                    consultaVentasFacturasPorNumeroTO.getVtaPeriodo(), consultaVentasFacturasPorNumeroTO.getVtaMotivo(),
                    consultaVentasFacturasPorNumeroTO.getVtaNumero());
            if (invVentasCreadas != null) {
                invVentasCreadas = ConversionesInventario.convertirInvVentas_InvVentas(invVentasCreadas);
                invVentaRetencionesTO = ConversionesInventario
                        .convertirInvVentaRetenciones_InvVentaRetencionesTO(invVentasCreadas);
            }
        } else {
            return null;
        }
        return invVentaRetencionesTO;
    }

    @Override
    public InvVentaRetencionesTO getInvVentaRetencionesTO(String codigoEmpresa, String tipoDocumento, String numero) throws Exception {
        InvVentas invVentasCreadas = null;
        InvVentaRetencionesTO invVentaRetencionesTO = null;
        InvConsultaVentasFacturasPorNumeroTO consultaVentasFacturasPorNumeroTO = new InvConsultaVentasFacturasPorNumeroTO();
        consultaVentasFacturasPorNumeroTO = ventasDao.getConsultaVentasFacturasPorTipoYNumeroTO(codigoEmpresa, tipoDocumento, numero);
        if (consultaVentasFacturasPorNumeroTO != null) {
            invVentasCreadas = new InvVentas();
            invVentasCreadas = ventasDao.buscarInvVentas(consultaVentasFacturasPorNumeroTO.getVtaEmpresa(),
                    consultaVentasFacturasPorNumeroTO.getVtaPeriodo(), consultaVentasFacturasPorNumeroTO.getVtaMotivo(),
                    consultaVentasFacturasPorNumeroTO.getVtaNumero());
            if (invVentasCreadas != null) {
                invVentasCreadas = ConversionesInventario.convertirInvVentas_InvVentas(invVentasCreadas);
                invVentaRetencionesTO = ConversionesInventario.convertirInvVentaRetenciones_InvVentaRetencionesTO(invVentasCreadas);
            }
        } else {
            return null;
        }
        return invVentaRetencionesTO;
    }

    @Override
    public MensajeTO insertarVentasRecurrentes(String empresa, InvCliente cliente, String tipodocumento, InvVentasTO venta, List<InvClientesVentasDetalle> detallesAgrupados,
            int grupo, Integer contrato, String secuencial, String descripcionProducto, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO respuesta = new MensajeTO();
        InvVentasMotivo motivo = cliente.getInvVentasMotivo();
        //validar que haya secuencial para numeracion de documentos
        if (tipodocumento != null && !tipodocumento.equals("00")) {
            if (secuencial == null || secuencial.equals("")) {
                respuesta.setMensaje("F" + "No hay un secuencial de numeración para documentos.");
                return respuesta;
            }
        }
        if (motivo != null) {
            venta.setVtaMotivo(motivo.getInvVentasMotivoPK().getVmCodigo());
            if (detallesAgrupados != null && !detallesAgrupados.isEmpty()) {
                String productosValidos = validarQueSeanServicios(detallesAgrupados);
                if (productosValidos != null && !productosValidos.equals("")) {
                    respuesta.setMensaje("F" + productosValidos);
                    return respuesta;
                } else {
                    String cliCodigoEstablecimiento = cliente.getCliCodigoEstablecimiento();
                    //el grupo debe ser el mismo del numero de contrato en caso tenga contrato, se supone que todos los detalles tienen el mismo grupo o contrato
                    Integer diasCredito = 0;
                    if (contrato != null && contrato > 0) {
                        InvClienteContrato contratoCliente = clienteContratoDao.obtenerPorId(InvClienteContrato.class, contrato);
                        if (contratoCliente != null) {
                            ///*********Dias credito**************************************************
                            InvClienteContratoTipo tipoContrato = contratoCliente.getInvClienteContratoTipo();
                            diasCredito = tipoContrato.getCliDiasCredito() != null && tipoContrato.getCliDiasCredito() != 0
                                    ? tipoContrato.getCliDiasCredito()
                                    : (cliente.getCliDiasCredito() != null && cliente.getCliDiasCredito() != 0 ? cliente.getCliDiasCredito() : 0);
                            //**************************************************************************
                            for (InvClientesVentasDetalle detalle : detallesAgrupados) {
                                if (contratoCliente.getCliEstablecimiento() != null && !contratoCliente.getCliEstablecimiento().equals("")) {
                                    cliCodigoEstablecimiento = contratoCliente.getCliEstablecimiento();
                                    detalle.setBodCodigo(contratoCliente.getInvBodega().getInvBodegaPK().getBodCodigo());
                                }
                            }
                        } else {
                            respuesta.setMensaje("F" + "No existe un contrato con identificador: " + contrato);
                            return respuesta;
                        }
                    } else {
                        for (InvClientesVentasDetalle detalle : detallesAgrupados) {
                            if (detalle.getCliCodigoEstablecimiento() != null && !detalle.getCliCodigoEstablecimiento().equals("")) {
                                cliCodigoEstablecimiento = detalle.getCliCodigoEstablecimiento();
                            }
                        }
                    }
                    List<InvVentasDetalleTO> detalle = ConversionesInventario.convertirListInvClientesVentasDetalle_InvVentasDetalleTO(detallesAgrupados, venta, descripcionProducto);
                    InvVentasTO ventaResultado = completarDatosDeLaVenta(empresa, cliente, tipodocumento, venta, detalle, cliCodigoEstablecimiento, grupo, secuencial);
                    try {
                        if (ventaResultado != null && ventaResultado.getVtaDocumentoTipo().equals("18") && (ventaResultado.getVtaDocumentoNumero() == null || ventaResultado.getVtaDocumentoNumero().equals(""))) {
                            respuesta.setMensaje("F" + cliente.getCliRazonSocial() + " => " + "No existe documento válido para la venta.");
                        } else {
                            //setear dias credito (fecha vencimiento = fecha venta + dias credito)
                            Date fechaDate = UtilsValidacion.fecha(ventaResultado.getVtaFecha(), "yyyy-MM-dd");
                            fechaDate.setDate(fechaDate.getDate() + diasCredito);
                            ventaResultado.setVtaFechaVencimiento(UtilsValidacion.fecha(fechaDate, "yyyy-MM-dd"));
                            //********************************************************************
                            respuesta = insertarInvVentasTO(ventaResultado, detalle, null, null, null, false, true, sisInfoTO);
                        }
                        return respuesta;
                    } catch (Exception e) {
                        respuesta = new MensajeTO();
                        e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                        respuesta.setMensaje("F" + cliente.getCliRazonSocial() + " => " + e.getMessage());
                        return respuesta;
                    }
                }
            } else {
                respuesta.setMensaje("F" + "No existen datos suficientes para realizar la venta recurrente.");
                return respuesta;
            }
        } else {
            respuesta.setMensaje("F" + "Debe configurar un motivo de venta recurrente. Archivo -> Cliente -> Modificar");
            return respuesta;
        }
    }

    public String validarQueSeanServicios(List<InvClientesVentasDetalle> listAPersistir) {
        String mensaje = "";
        for (InvClientesVentasDetalle detalle : listAPersistir) {
            InvProducto p = detalle.getInvProducto();
            if (!p.getInvProductoTipo().getTipTipo().equals("SERVICIOS")) {
                mensaje = mensaje + "\n<br>" + "Servicio: " + p.getInvProductoPK().getProCodigoPrincipal() + " \t\t" + p.getProNombre() + " - Tipo : "
                        + p.getInvProductoTipo().getTipTipo();
            }
        }
        return mensaje;
    }

    private InvVentasTO completarDatosDeLaVenta(String empresa, InvCliente cliente, String tipodocumento, InvVentasTO venta, List<InvVentasDetalleTO> detalles, String cliCodigoEstablecimiento, int grupo, String secuencial) throws Exception {
        InvVentasTO ventaResultado = venta;
        ventaResultado.setVtaDocumentoTipo(tipodocumento);
        Integer dias = new Date().getDate() + (cliente.getCliDiasCredito() > 0 ? cliente.getCliDiasCredito() - 1 : 0);
        Date fechaDate = UtilsValidacion.fecha(ventaResultado.getVtaFecha(), "yyyy-MM-dd");
        fechaDate.setDate(dias);
        ventaResultado.setVtaFechaVencimiento(UtilsValidacion.fecha(fechaDate, "yyyy-MM-dd"));
        ventaResultado.setCliCodigo(cliente.getInvClientePK().getCliCodigo());
        ventaResultado.setCliEmpresa(empresa);
        ventaResultado.setVtaDocumentoNumero(asignarNumeroDocumentoVentaRecurrente(empresa, tipodocumento, secuencial));
        venta.setVtaBaseImponible(cero);
        venta.setVtaRecargoBaseImponible(cero);
        venta.setVtaDescuentoBaseImponible(cero);
        venta.setVtaSubtotalBaseImponible(cero);
        venta.setVtaBase0(cero);
        venta.setVtaRecargoBase0(cero);
        venta.setVtaDescuentoBase0(cero);
        venta.setVtaSubtotalBase0(cero);
        venta.setVtaMontoIva(cero);
        venta.setVtaMontoIce(cero);
        venta.setVtaTotal(cero);
        venta.setVtaRecurrente(grupo);
        venta.setCliCodigoEstablecimiento(cliCodigoEstablecimiento);
        for (InvVentasDetalleTO detalle : detalles) {
            if (detalle.getProComplementario() == null || detalle.getProComplementario().equals("")) {
                BigDecimal iva_porcentaje = venta.getVtaIvaVigente().divide(cien, 2, RoundingMode.HALF_UP);
                BigDecimal parcial = detalle.getDetPrecio().multiply(detalle.getDetCantidad());
                BigDecimal recargo = parcial.multiply(detalle.getDetPorcentajeRecargo().divide(cien, 2, RoundingMode.HALF_UP));
                BigDecimal descuento = parcial.add(recargo).multiply(detalle.getDetPorcentajeDescuento().divide(cien, 2, RoundingMode.HALF_UP));
                BigDecimal sumTotal = parcial.add(recargo).subtract(descuento);
                BigDecimal vtIva = (detalle.getProEstadoIva().equals("GRAVA") ? sumTotal.multiply(iva_porcentaje) : cero);
                //ICE
                detalle.setDetMontoIce(cero);
                detalle.setIcePorcentaje(cero);
                detalle.setIceTarifaFija(cero);
                if ("GRAVA".equals(detalle.getProEstadoIva())) {
                    venta.setVtaBaseImponible(venta.getVtaBaseImponible().add(parcial));
                    venta.setVtaRecargoBaseImponible(venta.getVtaRecargoBaseImponible().add(recargo));
                    venta.setVtaDescuentoBaseImponible(venta.getVtaDescuentoBaseImponible().add(descuento));
                } else {
                    venta.setVtaBase0(venta.getVtaBase0().add(parcial));
                    venta.setVtaRecargoBase0(venta.getVtaRecargoBase0().add(recargo));
                    venta.setVtaDescuentoBase0(venta.getVtaDescuentoBase0().add(descuento));
                }
                venta.setVtaMontoIva(venta.getVtaMontoIva().add(vtIva));
            }
        }
        //establecer el sector de la bodega
        InvBodega invBodega = bodegaDao.buscarInvBodega(venta.getVtaEmpresa(), venta.getBodCodigo());
        if (invBodega != null) {
            venta.setSecCodigo(invBodega.getSecCodigo());
            venta.setSecEmpresa(invBodega.getSecEmpresa());
        }
        venta.setVtaSubtotalBaseImponible(venta.getVtaBaseImponible().add(venta.getVtaRecargoBaseImponible()).subtract(venta.getVtaDescuentoBaseImponible()));
        venta.setVtaSubtotalBase0(venta.getVtaBase0().add(venta.getVtaRecargoBase0()).subtract(venta.getVtaDescuentoBase0()));
        venta.setVtaTotal(venta.getVtaMontoIva().add(venta.getVtaSubtotalBaseImponible()).add(venta.getVtaSubtotalBase0()));
        venta.setVtaPagadoOtro(venta.getVtaTotal());
        return venta;
    }

    @Override
    public MensajeTO insertarInvVentasTO(InvVentasTO invVentasTO, List<InvVentasDetalleTO> listaInvVentasDetalleTO, AnxVentaTO anxVentasTO, String tipoDocumentoComplemento, String numeroDocumentoComplemento,
            Boolean isComprobanteElectronica, boolean vtaRecurrente, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>();
        boolean periodoCerrado = false;
        boolean continuar = false;

        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, invVentasTO.getVtaEmpresa());
        if (!sisEmpresaParametros.getParActividad().trim().equals("COMISARIATO")) {
            if (!invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaDocumentoNumero(), invVentasTO.getVtaDocumentoTipo(), ("'" + invVentasTO.getVtaFecha() + "'"));
                if (anxNumeracionLineaTO != null) {
                    if (!anxNumeracionLineaTO.getNumeroAutorizacion().trim().equals("ANULADO")) {
                        continuar = true;
                    }
                } else {
                    continuar = true;
                }
            } else {
                continuar = true;
            }
        } else {
            continuar = true;
        }

        if (continuar) {
            List<CarListaCobrosTO> carListaPagosCobrosTOs = new ArrayList<CarListaCobrosTO>();
            List<CarListaCobrosTO> carListaPagosCobrosVencidosTOs = new ArrayList<CarListaCobrosTO>();
            if (invVentasTO.getVtaFormaPago().equals("POR PAGAR") && !invVentasTO.getVtaDocumentoTipo().equals("04")) {
                carListaPagosCobrosTOs = cobrosDao.getCarListaCobrosVentasTO(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo());
                if (!carListaPagosCobrosTOs.isEmpty()) {
                    for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                        if (UtilsValidacion.fecha(carListaPagosCobrosTOs.get(i).getCxccFechaVencimiento(), "yyyy-MM-dd").getTime() <= new Date().getTime()) {
                            carListaPagosCobrosVencidosTOs.add(carListaPagosCobrosTOs.get(i));
                        }
                    }
                }
            }

            if (carListaPagosCobrosVencidosTOs.isEmpty() || !invVentasTO.getVtaEmpresa().trim().equals("AA")) {
//                if (!UtilsValidacion.isFechaSuperior(invVentasTO.getVtaFecha(), "yyyy-MM-dd")) {
                ///// BUSCANDO CLIENTE
                InvCliente invCliente = clienteDao.buscarInvCliente(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo());
                if (invCliente != null) {
                    boolean puedeContinuar = true;
                    if (invVentasTO.getVtaFormaPago().equals("POR PAGAR")) {
                        if (UtilsValidacion.numeroDias("yyyy-MM-dd", invVentasTO.getVtaFecha(), invVentasTO.getVtaFechaVencimiento()) <= invCliente.getCliDiasCredito()) {
                            puedeContinuar = true;
                        } else {
                            puedeContinuar = false;
                        }
                    }
                    if (vtaRecurrente) {
                        puedeContinuar = true;
                    }
                    if (puedeContinuar) {
                        puedeContinuar = true;
                        BigDecimal saldoTotalVentaCobros = cero;
                        if (invVentasTO.getVtaFormaPago().equals("POR PAGAR")) {
                            BigDecimal saldoTotalCobros = cero;
                            for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                                saldoTotalCobros = saldoTotalCobros.add(carListaPagosCobrosTOs.get(i).getCxccSaldo());
                            }
                            saldoTotalVentaCobros = saldoTotalCobros.add(invVentasTO.getVtaTotal());
                            if (sisEmpresaParametros.getParActividad().equals("COMERCIAL") || sisEmpresaParametros.getParActividad().trim().equals("COMISARIATO")) {
                                if (!invVentasTO.getVtaDocumentoTipo().equals("04")) {
                                    if (saldoTotalVentaCobros.compareTo(invCliente.getCliCupoCredito()) <= 0) {
                                        puedeContinuar = true;
                                    } else {
                                        puedeContinuar = false;
                                    }
                                } else {
                                    puedeContinuar = true;
                                }
                            } else {
                                puedeContinuar = true;
                            }
                        }

                        if (vtaRecurrente) {
                            puedeContinuar = true;
                        }
                        if (puedeContinuar) {//
                            comprobar = false;
                            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
                            listaSisPeriodoTO = periodoService.getListaPeriodoTO(invVentasTO.getVtaEmpresa());
                            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                                if (UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                                        && UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                                    comprobar = true;
                                    invVentasTO.setVtaPeriodo(sisListaPeriodoTO.getPerCodigo());
                                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                                }
                            }

                            if (comprobar) {
                                if (!periodoCerrado) {
                                    if (ventasMotivoDao.comprobarInvVentasMotivo(invVentasTO.getVtaEmpresa(),
                                            invVentasTO.getVtaMotivo())) {
                                        /// PREPARANDO OBJETO SISSUCESO (susClave y susDetalle se llenan en DAOTransaccion)
                                        susSuceso = "INSERT";
                                        susTabla = "inventario.inv_ventas";
                                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                        invVentasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                                        ////// CREANDO VENTAS
                                        InvVentas invVentas = ConversionesInventario.convertirInvVentasTO_InvVentas(invVentasTO);
                                        invVentas.setInvCliente(clienteDao.buscarInvCliente(invVentasTO.getCliEmpresa(), invVentasTO.getCliCodigo()));
                                        if (invVentas.getConNumero() == null || invVentas.getConPeriodo() == null || invVentas.getConTipo() == null) {
                                            invVentas.setConEmpresa(null);
                                            invVentas.setConNumero(null);
                                            invVentas.setConPeriodo(null);
                                            invVentas.setConTipo(null);
                                        }

                                        ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                                        List<InvVentasDetalle> listInvVentasDetalle = new ArrayList<InvVentasDetalle>();
                                        InvVentasDetalle invVentasDetalle = null;

                                        int estadoDetalle = 0;
                                        for (InvVentasDetalleTO invVentasDetalleTO : listaInvVentasDetalleTO) {
                                            invVentasDetalle = new InvVentasDetalle();
                                            invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
                                            invVentasDetalleTO.setDetPendiente(invVentasTO.getVtaPendiente());
                                            invVentasDetalle = ConversionesInventario.convertirInvVentasDetalleTO_InvVentasDetalle(invVentasDetalleTO);
                                            ///// BUSCANDO EL PRODUCTO EN DETALLE
                                            InvProducto invProducto = productoDao.buscarInvProducto(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getProCodigoPrincipal());
                                            if (invProducto != null) {
                                                invVentasDetalle.setInvProducto(invProducto);
                                                invVentasDetalle.setProCreditoTributario(invProducto.getProCreditoTributario());
                                                ////// BUSCANDO LA BODEGA EN EL DETALLE
                                                InvBodega invBodega = bodegaDao.buscarInvBodega(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getBodCodigo());
                                                //
                                                if (invBodega != null) {
                                                    invVentasDetalle.setInvBodega(invBodega);
                                                    invVentasDetalle.setSecCodigo(invBodega.getSecCodigo());
                                                    invVentasDetalle.setSecEmpresa(invBodega.getSecEmpresa());
                                                    listInvVentasDetalle.add(invVentasDetalle);
                                                } else {
                                                    estadoDetalle = 2;
                                                }
                                            } else {
                                                estadoDetalle = 1;
                                            }

                                            if (estadoDetalle == 1 || estadoDetalle == 2) {
                                                break;
                                            } else {
                                                invProducto = null;
                                            }
                                        }
                                        if (estadoDetalle == 0) {
                                            AnxVenta anxVenta = null;
                                            if (anxVentasTO != null) {
                                                anxVentasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                                                anxVenta = ConversionesAnexos.convertirAnxVentaTO_AnxVenta(anxVentasTO);
                                            }
                                            //////////// COMPROBAR SI NO EXISTE NUMERO DE FACTURA Y SI
                                            //////////// ES EMPRESA COMERCIAL YA NO SE PREGUNTA ESO
                                            String codigoFactura = ventasDao.getConteoNumeroFacturaVenta(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaDocumentoTipo(), invVentasTO.getVtaDocumentoNumero());

                                            if (codigoFactura.isEmpty() || (invVentasTO.getVtaDocumentoNumero() != null && invVentasTO.getVtaDocumentoNumero().equals("999-999-999999999")) || invVentasTO.getVtaDocumentoTipo().equals("00")) {
                                                boolean noExiste = false;
                                                //////////// COMPROBAR SI EL TIPO DE DOCUMENTO ES 00
                                                if (anxVentasTO != null) {
                                                    //////////// COMPROBAR SI NO EXISTE
                                                    //////////// NUMERO DE RETENCION
                                                    String codigoRetencion = "";
                                                    anxVentasTO.setVenRetencionNumero(anxVentasTO.getVenRetencionNumero() == null ? "" : anxVentasTO.getVenRetencionNumero());
                                                    if (!anxVentasTO.getVenRetencionNumero().isEmpty()) {
                                                        codigoRetencion = ventaDao.getConteoNumeroRetencionVenta(anxVentasTO.getVenEmpresa(), anxVentasTO.getVenRetencionNumero(), invCliente.getInvClientePK().getCliCodigo());
                                                    } else {
                                                        codigoRetencion = "";
                                                    }
                                                    if (codigoRetencion.trim().isEmpty()) {
                                                        noExiste = true;
                                                    }
                                                } else {
                                                    noExiste = true;
                                                }

                                                if (noExiste) {
                                                    if (!invVentas.getVtaPendiente() && !invVentasTO.getVtaDocumentoTipo().equals("04")) {
                                                        mensajeClase = productoSaldosService.verificarStockVentas(listInvVentasDetalle);
                                                    }
                                                    if (mensajeClase.isEmpty()) {
                                                        InvVentasComplemento invVentasComplemento = null;
                                                        puedeContinuar = true;//false
                                                        if ((invVentas.getVtaDocumentoTipo().equals("04") || invVentas.getVtaDocumentoTipo().equals("05")) && (numeroDocumentoComplemento != null
                                                                && tipoDocumentoComplemento != null && !numeroDocumentoComplemento.trim().isEmpty() && !tipoDocumentoComplemento.trim().isEmpty())) {
                                                            invVentasComplemento = new InvVentasComplemento();
                                                            invVentasComplemento.setComDocumentoNumero(numeroDocumentoComplemento);
                                                            invVentasComplemento.setComDocumentoTipo(tipoDocumentoComplemento);
                                                            codigoFactura = ventasDao.getConteoNotaCreditoVenta(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo(), tipoDocumentoComplemento, numeroDocumentoComplemento);
                                                            if (!codigoFactura.trim().isEmpty() || (invVentasTO.getVtaDocumentoNumero() != null && invVentasTO.getVtaDocumentoNumero().equals("999-999-999999999"))) {
                                                                puedeContinuar = true;
                                                            }
                                                        } else {
                                                            puedeContinuar = true;
                                                        }
                                                        if (puedeContinuar) {
                                                            AnxVentaElectronicaTO anxVentaElectronicaTO = new AnxVentaElectronicaTO();
                                                            AnxVentaElectronica anxVentaElectronica = null;
                                                            if (isComprobanteElectronica != null && isComprobanteElectronica) {
                                                                File file = new File("auxxml.xml");
                                                                byte[] buffer = new byte[(int) file.length()];
                                                                // ************************
                                                                // clave primaria
                                                                // ************************
                                                                anxVentaElectronicaTO.seteSecuencial(0);
                                                                anxVentaElectronicaTO.seteTipoAmbiente("");
                                                                anxVentaElectronicaTO.seteClaveAcceso("");
                                                                anxVentaElectronicaTO.seteEstado("PENDIENTE");
                                                                anxVentaElectronicaTO.seteAutorizacionNumero("");
                                                                anxVentaElectronicaTO.seteAutorizacionFecha(UtilsValidacion.fechaSistema());
                                                                anxVentaElectronicaTO.seteXml(buffer);
                                                                anxVentaElectronicaTO.seteEnviadoPorCorreo(false);
                                                                anxVentaElectronicaTO.setVtaEmpresa("");
                                                                anxVentaElectronicaTO.setVtaPeriodo("");
                                                                anxVentaElectronicaTO.setVtaMotivo("");
                                                                anxVentaElectronicaTO.setVtaNumero("");
                                                                anxVentaElectronicaTO.setUsrEmpresa(sisInfoTO.getEmpresa());
                                                                anxVentaElectronicaTO.setUsrCodigo(invVentasTO.getUsrCodigo());
                                                                anxVentaElectronicaTO.setUsrFechaInserta(UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "dd-MM-yyyy", "yyyy-MM-dd"));
                                                                anxVentaElectronicaTO.setVtaFecha(UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "dd-MM-yyyy", "yyyy-MM-dd"));
                                                                anxVentaElectronica = ConversionesAnexos.convertirAnxVentaElectronicaTO_AnxVentaElectronica(anxVentaElectronicaTO);
                                                            }
                                                            if (invVentas.getVtaDocumentoTipo().equals("00") || (invVentasTO.getVtaDocumentoNumero() != null && invVentas.getVtaDocumentoNumero().equals("999-999-999999999") && invVentas.getVtaDocumentoTipo().equals("04"))
                                                                    || (invVentasTO.getVtaDocumentoNumero() != null && invVentas.getVtaDocumentoNumero().equals("999-999-999999999") && invVentas.getVtaDocumentoTipo().equals("05"))) {
                                                                invVentas.setVtaDocumentoNumero(null);
                                                            }
                                                            comprobar = ventasDao.insertarTransaccionInvVenta(invVentas, listInvVentasDetalle, sisSuceso, anxVenta, invVentasComplemento, anxVentaElectronica);
                                                            if (comprobar) {
                                                                SisPeriodo sisPeriodo = periodoService.buscarPeriodo(invVentasTO.getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo());
                                                                retorno = "T<html>Se ingresó la venta con la siguiente información:<br><br>"
                                                                        + "<center>"
                                                                        + "Periodo: <font size = 5>"
                                                                        + sisPeriodo.getPerDetalle()
                                                                        + "</font>.<br> Motivo: <font size = 5>"
                                                                        + invVentas.getInvVentasPK().getVtaMotivo()
                                                                        + "</font>.<br> Número: <font size = 5>"
                                                                        + invVentas.getInvVentasPK().getVtaNumero()
                                                                        + "</font>."
                                                                        + "</center>"
                                                                        + "</html>"
                                                                        + invVentas.getInvVentasPK().getVtaNumero()
                                                                        + "," + sisPeriodo.getSisPeriodoPK().getPerCodigo();

                                                                invVentasTO.setVtaNumero(invVentas.getInvVentasPK().getVtaNumero());
                                                                Map<String, Object> campos = new HashMap<>();
                                                                campos.put("invVentasTO", invVentasTO);
                                                                mensajeTO.setMap(campos);
                                                                if (!carListaPagosCobrosVencidosTOs.isEmpty()) {
                                                                    mensajeClase.add("<html>Cliente tiene deudas vencidas! Avise al cliente que\nse acerque a cancelar lo más pronto...</html>");
                                                                    mensajeClase.add("Número de Sistema\tValor\tFecha de Vencimiento");
                                                                    for (int i = 0; i < carListaPagosCobrosVencidosTOs.size(); i++) {
                                                                        mensajeClase.add(carListaPagosCobrosVencidosTOs.get(i).getCxccPeriodo()
                                                                                + " | "
                                                                                + carListaPagosCobrosVencidosTOs.get(i).getCxccMotivo()
                                                                                + " | "
                                                                                + carListaPagosCobrosVencidosTOs.get(i).getCxccNumero()
                                                                                + "\t"
                                                                                + carListaPagosCobrosVencidosTOs.get(i).getCxccSaldo()
                                                                                + "\t"
                                                                                + carListaPagosCobrosVencidosTOs.get(i).getCxccFechaVencimiento());
                                                                    }
                                                                    mensajeTO.setListaErrores1(mensajeClase);
                                                                }
                                                            } else {
                                                                retorno = "F<html>Hubo un error al guardar la Venta...\nIntente de nuevo o contacte con el administrador</html>";
                                                            }
                                                        } else {
                                                            retorno = "F<html>El Número de Documento del Complemento que ingresó no existe...\nIntente de nuevo o contacte con el administrador</html>";
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
                                        } else if (estadoDetalle == 1) {
                                            retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                        } else {
                                            retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                        }
                                    } else {
                                        retorno = "F<html>No se encuentra el motivo...</html>";
                                    }
                                } else {
                                    retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                                }
                            } else {
                                retorno = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
                            }
                        } else {
                            retorno = "F<html>Se superó el limite del monto del crédito...</html>";
                        }
                    } else {
                        retorno = "F<html>Los días de crédito del Cliente es superior al que se le permite...</html>";
                    }
                } else {
                    retorno = "F<html>El Cliente que escogió ya no está disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
                }
//                } else {
//                    retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
//                }
            } else {
                retorno = "F<html>Cliente tiene deudas vencidas! Avise al cliente que\nse acerque a cancelar lo más pronto...</html>";
                mensajeTO.setMensaje(retorno);
                mensajeClase.add("Número de Sistema\tValor\tFecha de Vencimiento");
                for (int i = 0; i < carListaPagosCobrosVencidosTOs.size(); i++) {
                    mensajeClase.add(carListaPagosCobrosVencidosTOs.get(i).getCxccPeriodo() + " | "
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccMotivo() + " | "
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccNumero() + "\t"
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccSaldo() + "\t"
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccFechaVencimiento());
                }
                mensajeTO.setListaErrores1(mensajeClase);
            }
        } else {
            retorno = "F<html>El NÚMERO DE DOCUMENTO que ingresó se encuentra ANULADO</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public void quitarPendiente(InvVentasTO invVentasTO) throws Exception {
        InvVentas invVentas = ConversionesInventario.convertirInvVentasTO_InvVentas(invVentasTO);
        ventasDao.actualizarPendientePorSql(invVentas);
    }

    @Override
    public MensajeTO modificarInvVentasTO(InvVentasTO invVentasTO, List<InvVentasDetalleTO> listaInvVentasDetalleTO,
            List<InvVentasDetalleTO> listaInvVentasEliminarDetalleTO, boolean desmayorizar, AnxVentaTO anxVentasTO,
            boolean quitarAnulado, String tipoDocumentoComplemento, String numeroDocumentoComplemento,
            InvVentasMotivoAnulacion invVentasMotivoAnulacion, SisInfoTO sisInfoTO) throws Exception {

        boolean eliminarMotivoAnulacion = false;
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>();
        SisSuceso sisSuceso;
        String retorno = "";
        boolean periodoCerrado = false;
        boolean continuar = false;
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, invVentasTO.getVtaEmpresa());
        if (!sisEmpresaParametros.getParActividad().trim().equals("COMISARIATO")) {
            if (!invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaDocumentoNumero(), invVentasTO.getVtaDocumentoTipo(), ("'" + invVentasTO.getVtaFecha() + "'"));
                if (anxNumeracionLineaTO != null) {
                    if (!anxNumeracionLineaTO.getNumeroAutorizacion().trim().equals("ANULADO")) {
                        continuar = true;
                    }
                } else {
                    continuar = true;
                }
            } else {
                continuar = true;
            }
        } else {
            continuar = true;
        }
        if (continuar) {
            List<CarListaCobrosTO> carListaPagosCobrosTOs = new ArrayList<CarListaCobrosTO>();
            List<CarListaCobrosTO> carListaPagosCobrosVencidosTOs = new ArrayList<CarListaCobrosTO>();
            if (invVentasTO.getVtaFormaPago().equals("POR PAGAR") && !desmayorizar && !invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                carListaPagosCobrosTOs = cobrosDao.getCarListaCobrosVentasTO(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo());
                if (!carListaPagosCobrosTOs.isEmpty()) {
                    for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                        if (UtilsValidacion.fecha(carListaPagosCobrosTOs.get(i).getCxccFechaVencimiento(), "yyyy-MM-dd").getTime() <= new Date().getTime()) {
                            carListaPagosCobrosVencidosTOs.add(carListaPagosCobrosTOs.get(i));
                        }
                    }
                }
            }

            if (carListaPagosCobrosVencidosTOs.isEmpty() || !invVentasTO.getVtaEmpresa().trim().equals("AA")) {
                ///// BUSCANDO CLIENTE
                InvCliente invCliente = clienteDao.buscarInvCliente(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo());
                if (invCliente != null) {
                    String detalleError = "";
                    if (desmayorizar && listaInvVentasDetalleTO == null) {
                        List<InvListaDetalleVentasTO> invListaDetalleTO = ventasDetalleDao.getListaInvVentasDetalleTO(invVentasTO.getVtaEmpresa(),
                                invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
                        if (invListaDetalleTO != null) {
                            listaInvVentasDetalleTO = new ArrayList<InvVentasDetalleTO>();
                            for (int i = 0; i < invListaDetalleTO.size(); i++) {
                                InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();
                                invVentasDetalleTO.setDetSecuencia(invListaDetalleTO.get(i).getSecuencial());
                                invVentasDetalleTO.setDetOrden(i + 1);
                                invVentasDetalleTO.setDetPendiente(invListaDetalleTO.get(i).getPendiente());
                                invVentasDetalleTO.setDetCantidad(invListaDetalleTO.get(i).getCantidadProducto());
                                invVentasDetalleTO.setDetPrecio(invListaDetalleTO.get(i).getPrecioProducto());
                                invVentasDetalleTO.setDetPorcentajeRecargo(invListaDetalleTO.get(i).getPorcentajeRecargo());
                                invVentasDetalleTO.setDetPorcentajeDescuento(invListaDetalleTO.get(i).getPorcentajeDescuento());
                                invVentasDetalleTO.setBodEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setBodCodigo(invListaDetalleTO.get(i).getCodigoBodega());
                                invVentasDetalleTO.setProEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setProCodigoPrincipal(invListaDetalleTO.get(i).getCodigoProducto());
                                invVentasDetalleTO.setProNombre(invListaDetalleTO.get(i).getNombreProducto());
                                invVentasDetalleTO.setSecEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setSecCodigo(invListaDetalleTO.get(i).getCodigoCP());
                                invVentasDetalleTO.setPisEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setPisSector(invListaDetalleTO.get(i).getCodigoCP());
                                invVentasDetalleTO.setPisNumero(invListaDetalleTO.get(i).getCodigoCC());
                                invVentasDetalleTO.setVtaEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
                                invVentasDetalleTO.setVtaMotivo(invVentasTO.getVtaMotivo());
                                invVentasDetalleTO.setVtaNumero(invVentasTO.getVtaNumero());
                                invVentasDetalleTO.setProComplementario(invListaDetalleTO.get(i).getProComplementario());
                                invVentasDetalleTO.setDetObservaciones(invListaDetalleTO.get(i).getDetObservaciones());
                                invVentasDetalleTO.setDetEmpaque(invListaDetalleTO.get(i).getDetEmpaqueExportadora());
                                invVentasDetalleTO.setDetEmpaqueCantidad(invListaDetalleTO.get(i).getDetEmpaqueCantidad());
                                invVentasDetalleTO.setDetConversionPesoNeto(invListaDetalleTO.get(i).getDetConversionPesoNeto());
                                listaInvVentasDetalleTO.add(invVentasDetalleTO);
                            }
                        } else {
                            detalleError = "Hubo en error al recuperar el detalle de la VENTA.\nContacte con el administrador...";
                        }
                    }

                    if (quitarAnulado && listaInvVentasDetalleTO == null) {
                        List<InvListaDetalleVentasTO> invListaDetalleTO = ventasDetalleDao.getListaInvVentasDetalleTO(invVentasTO.getVtaEmpresa(),
                                invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
                        if (invListaDetalleTO != null) {
                            listaInvVentasDetalleTO = new ArrayList<InvVentasDetalleTO>();
                            for (int i = 0; i < invListaDetalleTO.size(); i++) {
                                InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();
                                invVentasDetalleTO.setDetSecuencia(invListaDetalleTO.get(i).getSecuencial());
                                invVentasDetalleTO.setDetOrden(i + 1);
                                invVentasDetalleTO.setDetPendiente(invListaDetalleTO.get(i).getPendiente());
                                invVentasDetalleTO.setDetCantidad(invListaDetalleTO.get(i).getCantidadProducto());
                                invVentasDetalleTO.setDetPrecio(invListaDetalleTO.get(i).getPrecioProducto());
                                invVentasDetalleTO.setDetPorcentajeDescuento(invListaDetalleTO.get(i).getPorcentajeDescuento());
                                invVentasDetalleTO.setDetPorcentajeRecargo(invListaDetalleTO.get(i).getPorcentajeRecargo());
                                invVentasDetalleTO.setBodEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setBodCodigo(invListaDetalleTO.get(i).getCodigoBodega());
                                invVentasDetalleTO.setProEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setProCodigoPrincipal(invListaDetalleTO.get(i).getCodigoProducto());
                                invVentasDetalleTO.setProNombre(invListaDetalleTO.get(i).getNombreProducto());
                                invVentasDetalleTO.setSecEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setSecCodigo(invListaDetalleTO.get(i).getCodigoCP());
                                invVentasDetalleTO.setPisEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setPisSector(invListaDetalleTO.get(i).getCodigoCP());
                                invVentasDetalleTO.setPisNumero(invListaDetalleTO.get(i).getCodigoCC());
                                invVentasDetalleTO.setVtaEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
                                invVentasDetalleTO.setVtaMotivo(invVentasTO.getVtaMotivo());
                                invVentasDetalleTO.setVtaNumero(invVentasTO.getVtaNumero());
                                invVentasDetalleTO.setProComplementario(invListaDetalleTO.get(i).getProComplementario());
                                invVentasDetalleTO.setDetObservaciones(invListaDetalleTO.get(i).getDetObservaciones());
                                invVentasDetalleTO.setDetEmpaque(invListaDetalleTO.get(i).getDetEmpaqueExportadora());
                                invVentasDetalleTO.setDetEmpaqueCantidad(invListaDetalleTO.get(i).getDetEmpaqueCantidad());
                                invVentasDetalleTO.setDetConversionPesoNeto(invListaDetalleTO.get(i).getDetConversionPesoNeto());

                                listaInvVentasDetalleTO.add(invVentasDetalleTO);
                            }
                        } else {
                            detalleError = "Hubo en error al recuperar el detalle de la VENTA.\nContacte con el administrador...";
                        }
                    }

                    if (detalleError.trim().isEmpty()) {
                        boolean puedeContinuar = true;
                        if (invVentasTO.getVtaFormaPago().equals("POR PAGAR") && !desmayorizar && !invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                            if (UtilsValidacion.numeroDias("yyyy-MM-dd", invVentasTO.getVtaFecha(), invVentasTO.getVtaFechaVencimiento()) <= invCliente.getCliDiasCredito()) {
                                puedeContinuar = true;
                            } else {
                                puedeContinuar = false;
                            }
                        }

                        if (puedeContinuar) {
                            puedeContinuar = true;
                            if (invVentasTO.getVtaFormaPago().equals("POR PAGAR") && !desmayorizar && !invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                                BigDecimal saldoTotalVentaCobros = cero;
                                BigDecimal saldoTotalCobros = cero;
                                for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                                    saldoTotalCobros = saldoTotalCobros.add(carListaPagosCobrosTOs.get(i).getCxccSaldo());
                                }
                                saldoTotalVentaCobros = saldoTotalCobros.add(invVentasTO.getVtaTotal());
                                if (invCliente.getCliCupoCredito() != null) {
                                    if (saldoTotalVentaCobros.compareTo(invCliente.getCliCupoCredito()) <= 0) {
                                        puedeContinuar = true;
                                    } else {
                                        puedeContinuar = false;
                                    }
                                } else {
                                    puedeContinuar = true;
                                }

                            }
                            if (puedeContinuar) {
                                comprobar = false;
                                List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
                                listaSisPeriodoTO = periodoService.getListaPeriodoTO(invVentasTO.getVtaEmpresa());

                                for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                                    if (UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                                            && UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                                        comprobar = true;
                                        invVentasTO.setVtaPeriodo(sisListaPeriodoTO.getPerCodigo());
                                        periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                                    }
                                }

                                if (comprobar) {
                                    if (!periodoCerrado) {
                                        List<String> lisCheques = contableService.validarChequesConciliados(sisInfoTO.getEmpresa(), invVentasTO.getConPeriodo(), invVentasTO.getConTipo(), invVentasTO.getConNumero());
                                        if (lisCheques != null && !lisCheques.isEmpty()) {
                                            mensajeTO.setMensaje("FNo se puede Modificar un contable que contiene un cheque conciliado:");
                                            mensajeTO.setListaErrores1(lisCheques);
                                            return mensajeTO;
                                        }

                                        if (ventasMotivoDao.comprobarInvVentasMotivo(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaMotivo())) {

                                            InvVentas invVentasCreadas = ventasDao.buscarInvVentas(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
                                            String estado = "";
                                            if (invVentasTO.getVtaAnulado()) {
                                                estado = "anulá";
                                            } else {
                                                estado = "desmayorizo";
                                            }
                                            boolean validacionModificar = invVentasCreadas != null && !quitarAnulado;
                                            boolean validacionRestaurar = invVentasCreadas != null && invVentasCreadas.getVtaAnulado() && quitarAnulado;
                                            if (validacionModificar || validacionRestaurar) {
                                                if (quitarAnulado) {
                                                    eliminarMotivoAnulacion = true;
                                                    InvVentasMotivoAnulacion invVentasMotivoAnulacionTmp = ventasMotivoAnulacionDao.buscarVentaMotivo(invVentasTO.getVtaEmpresa(),
                                                            invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
                                                    invVentasMotivoAnulacion = new InvVentasMotivoAnulacion();
                                                    invVentasMotivoAnulacion.setAnuComentario(invVentasMotivoAnulacionTmp.getAnuComentario());
                                                    invVentasMotivoAnulacion.setInvVentas(invVentasMotivoAnulacionTmp.getInvVentas());
                                                    invVentasMotivoAnulacion.setAnuSecuencial(invVentasMotivoAnulacionTmp.getAnuSecuencial());

                                                    estado = "restauró";
                                                    //// CREANDO SUCESO
                                                    susClave = invVentasTO.getVtaPeriodo() + " " + invVentasTO.getVtaMotivo() + " " + invVentasTO.getVtaNumero();
                                                    susDetalle = "Se " + estado + " la venta en el periodo " + invVentasTO.getVtaPeriodo() + ", del motivo "
                                                            + invVentasTO.getVtaMotivo() + ", de la numeración " + invVentasTO.getVtaNumero();
                                                    susSuceso = "UPDATE";
                                                    susTabla = "inventario.inv_ventas";
                                                    sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                                    invVentasTO.setVtaAnulado(false);
                                                    invVentasTO.setUsrCodigo(invVentasCreadas.getUsrCodigo());
                                                    invVentasTO.setUsrFechaInserta(UtilsValidacion.fecha(invVentasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                                } else {
                                                    susDetalle = "";
                                                    if (estado.trim().equals("anuló")) {
                                                        susDetalle = "Venta número " + invVentasTO.getVtaNumero() + "se anuló por "
                                                                + invVentasMotivoAnulacion.getAnuComentario();
                                                    } else {
                                                        susDetalle = "Se " + estado + " la ventas en el periodo " + invVentasTO.getVtaPeriodo() + ", del motivo "
                                                                + invVentasTO.getVtaMotivo() + ", de la numeración " + invVentasTO.getVtaNumero();
                                                    }
                                                    //// CREANDO SUCESO
                                                    susClave = invVentasTO.getVtaPeriodo() + " " + invVentasTO.getVtaMotivo() + " " + invVentasTO.getVtaNumero();
                                                    susSuceso = "UPDATE";
                                                    susTabla = "inventario.inv_ventas";
                                                    sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                                    invVentasTO.setUsrCodigo(invVentasCreadas.getUsrCodigo());
                                                    invVentasTO.setUsrFechaInserta(UtilsValidacion.fecha(invVentasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                                }
                                                ////// CREANDO NUMERACION DE
                                                ////// VENTA CREANDO VENTAS
                                                InvVentas invVentas = ConversionesInventario.convertirInvVentasTO_InvVentas(invVentasTO);
                                                if (invVentasCreadas != null) {
                                                    invVentas.setVtaSaldoImportado(invVentasCreadas.isVtaSaldoImportado());
                                                }
                                                invVentas.setInvCliente(clienteDao.buscarInvCliente(invVentasTO.getCliEmpresa(), invVentasTO.getCliCodigo()));
                                                ////// CONVIRTIENDO A
                                                ////// ENTIDAD EL DETALLE
                                                List<InvVentasDetalle> listInvVentasDetalle = new ArrayList<InvVentasDetalle>();
                                                InvVentasDetalle invVentasDetalle = null;
                                                int estadoDetalle = 0;
                                                for (InvVentasDetalleTO invVentasDetalleTO : listaInvVentasDetalleTO) {
                                                    invVentasDetalle = new InvVentasDetalle();
                                                    invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
                                                    invVentasDetalle = ConversionesInventario.convertirInvVentasDetalleTO_InvVentasDetalle(invVentasDetalleTO);
                                                    ///// BUSCANDO EL
                                                    ///// PRODUCTO EN DETALLE
                                                    InvProducto invProducto = productoDao.buscarInvProducto(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getProCodigoPrincipal());
                                                    if (invProducto != null) {
                                                        invVentasDetalle.setInvProducto(invProducto);
                                                        invVentasDetalle.setProCreditoTributario(invProducto.getProCreditoTributario());
                                                        ////// BUSCANDO LA BODEGA
                                                        ////// EN EL DETALLE
                                                        InvBodega invBodega = bodegaDao.buscarInvBodega(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getBodCodigo());
                                                        if (invBodega != null) {
                                                            invVentasDetalle.setInvBodega(invBodega);
                                                            listInvVentasDetalle.add(invVentasDetalle);
                                                        } else {
                                                            estadoDetalle = 2;
                                                        }
                                                    } else {
                                                        estadoDetalle = 1;
                                                    }

                                                    if (estadoDetalle == 1 || estadoDetalle == 2) {
                                                        break;
                                                    } else {
                                                        invProducto = null;
                                                    }
                                                }

                                                if (estadoDetalle == 0) {
                                                    ////// CONVIRTIENDO A ENTIDAD EL
                                                    ////// DETALLE A ELIMINAR
                                                    List<InvVentasDetalle> listInvVentasDetalleEliminar = new ArrayList<InvVentasDetalle>();
                                                    InvVentasDetalle invVentasDetalleEliminar = null;

                                                    int estadoDetalleEliminar = 0;
                                                    if (listaInvVentasEliminarDetalleTO != null) {
                                                        for (InvVentasDetalleTO invVentasDetalleTO : listaInvVentasEliminarDetalleTO) {
                                                            invVentasDetalleEliminar = new InvVentasDetalle();
                                                            invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
                                                            invVentasDetalleEliminar = ConversionesInventario.convertirInvVentasDetalleTO_InvVentasDetalle(invVentasDetalleTO);
                                                            invVentasDetalleEliminar.setInvVentas(invVentas);
                                                            ///// BUSCANDO EL PRODUCTO
                                                            ///// EN DETALLE
                                                            InvProducto invProducto = productoDao.buscarInvProducto(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getProCodigoPrincipal());
                                                            if (invProducto != null) {
                                                                invVentasDetalleEliminar.setInvProducto(invProducto);
                                                                ////// BUSCANDO LA BODEGA
                                                                ////// EN EL DETALLE
                                                                InvBodega invBodega = bodegaDao.buscarInvBodega(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getBodCodigo());
                                                                if (invBodega != null) {
                                                                    invVentasDetalleEliminar.setInvBodega(invBodega);
                                                                    listInvVentasDetalleEliminar.add(invVentasDetalleEliminar);
                                                                } else {
                                                                    estadoDetalleEliminar = 2;
                                                                }
                                                            } else {
                                                                estadoDetalleEliminar = 1;
                                                            }

                                                            if (estadoDetalleEliminar == 1 || estadoDetalleEliminar == 2) {
                                                                break;
                                                            } else {
                                                                invProducto = null;
                                                            }
                                                        }
                                                    }
                                                    if (estadoDetalleEliminar == 0) {
                                                        AnxVenta anxVenta = null;
                                                        if (anxVentasTO != null) {
                                                            anxVentasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                                                            anxVenta = ConversionesAnexos.convertirAnxVentaTO_AnxVenta(anxVentasTO);
                                                        }
                                                        //////////// COMPROBAR SI NO EXISTE
                                                        //////////// NUMERO DE FACTURA
                                                        String codigoFactura;
                                                        if (!sisEmpresaParametros.getParActividad().equals("COMISARIATO")) {
                                                            codigoFactura = ventasDao.getConteoNumeroFacturaVenta(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaDocumentoTipo(), invVentasTO.getVtaDocumentoNumero());
                                                        } else {
                                                            codigoFactura = "";
                                                        }
                                                        if ((codigoFactura.trim().isEmpty() || codigoFactura.trim().equals(invVentasCreadas.getInvVentasPK()
                                                                .getVtaEmpresa().trim().concat(invVentasCreadas.getInvVentasPK().getVtaPeriodo().trim()
                                                                        .concat(invVentasCreadas.getInvVentasPK().getVtaMotivo().trim()
                                                                                .concat(invVentasCreadas.getInvVentasPK().getVtaNumero().trim())))))
                                                                || ((invVentasTO.getVtaDocumentoNumero() != null && invVentasTO.getVtaDocumentoNumero().equals("999-999-999999999"))
                                                                || invVentasTO.getVtaDocumentoTipo().equals("00"))) {
                                                            boolean noExiste = false;
                                                            //////////// COMPROBAR SI EL TIPO
                                                            //////////// DE DOCUMENTO ES 00
                                                            if (anxVentasTO != null) {
                                                                //////////// COMPROBAR SI NO EXISTE
                                                                //////////// NUMERO DE RETENCION
                                                                String codigoRetencion = "";
                                                                anxVentasTO.setVenRetencionNumero(anxVentasTO
                                                                        .getVenRetencionNumero() == null ? ""
                                                                                : anxVentasTO.getVenRetencionNumero());
                                                                if (!anxVentasTO.getVenRetencionNumero().isEmpty()) {
                                                                    codigoRetencion = ventaDao.getConteoNumeroRetencionVenta(
                                                                            anxVentasTO.getVenEmpresa(),
                                                                            anxVentasTO.getVenRetencionNumero(),
                                                                            invCliente.getInvClientePK().getCliCodigo());
                                                                } else {
                                                                    codigoRetencion = "";
                                                                }
                                                                if (codigoRetencion.trim().isEmpty()
                                                                        || codigoRetencion.trim().equals(invVentasCreadas.getInvVentasPK().getVtaEmpresa().trim()
                                                                                .concat(invVentasCreadas.getInvVentasPK().getVtaPeriodo().trim()
                                                                                        .concat(invVentasCreadas.getInvVentasPK().getVtaMotivo().trim()
                                                                                                .concat(invVentasCreadas.getInvVentasPK().getVtaNumero().trim()))))) {
                                                                    noExiste = true;
                                                                }
                                                            } else {
                                                                noExiste = true;
                                                            }
                                                            if (noExiste) {

                                                                if (!invVentas.getVtaDocumentoTipo().equals("04") && !desmayorizar
                                                                        && (listaInvVentasDetalleTO != null && listaInvVentasDetalleTO.size() > 0)) {
                                                                    if (!invVentas.getVtaAnulado()) {
                                                                        mensajeClase = productoSaldosService.verificarStockVentas(listInvVentasDetalle);
                                                                    }
                                                                } else {
                                                                    ///////////
                                                                }
                                                                if (mensajeClase.isEmpty()) {
                                                                    ConContable conContable = null;
                                                                    List<SisSuceso> listaSisSuceso = new ArrayList<SisSuceso>();

                                                                    if (invVentas.getVtaAnulado()) {
                                                                        conContable = contableDao.obtenerPorId(
                                                                                ConContable.class, new ConContablePK(
                                                                                        invVentas.getConEmpresa(),
                                                                                        invVentas.getConPeriodo(),
                                                                                        invVentas.getConTipo(),
                                                                                        invVentas.getConNumero()));
                                                                        if (conContable != null) {
                                                                            conContable.setConPendiente(false);
                                                                            conContable.setConAnulado(true);
                                                                            susSuceso = "DELETE";
                                                                            sisSuceso = Suceso.crearSisSuceso(
                                                                                    susTabla, susClave,
                                                                                    susSuceso, susDetalle,
                                                                                    sisInfoTO);
                                                                            listaSisSuceso.add(sisSuceso);
                                                                            /////////////// NUEVO
                                                                            /////////////// SUCESO
                                                                            /////////////// PARA
                                                                            /////////////// CONTABLE
                                                                            SisSuceso sisSucesoContable = Suceso
                                                                                    .crearSisSuceso(
                                                                                            "contabilidad.con_contable",
                                                                                            conContable.getConContablePK().getConPeriodo()
                                                                                            + " "
                                                                                            + conContable.getConContablePK().getConTipo()
                                                                                            + " "
                                                                                            + conContable.getConContablePK().getConNumero(),
                                                                                            "DELETE",
                                                                                            "Se anuló el contable del periodo "
                                                                                            + conContable.getConContablePK().getConPeriodo()
                                                                                            + ", del tipo "
                                                                                            + conContable.getConContablePK().getConTipo()
                                                                                            + ", de la numeración "
                                                                                            + conContable.getConContablePK().getConNumero(),
                                                                                            sisInfoTO);
                                                                            listaSisSuceso
                                                                                    .add(sisSucesoContable);
                                                                            sisSuceso = null;
                                                                        }
                                                                    }

                                                                    if (quitarAnulado) {
                                                                        conContable = contableDao.obtenerPorId(
                                                                                ConContable.class,
                                                                                new ConContablePK(
                                                                                        invVentas.getConEmpresa(),
                                                                                        invVentas.getConPeriodo(),
                                                                                        invVentas.getConTipo(),
                                                                                        invVentas.getConNumero()));
                                                                        if (conContable != null) {
                                                                            conContable.setConAnulado(false);
                                                                            susSuceso = "RESTORE";
                                                                            sisSuceso = Suceso.crearSisSuceso(
                                                                                    susTabla, susClave,
                                                                                    susSuceso, susDetalle,
                                                                                    sisInfoTO);
                                                                            listaSisSuceso.add(sisSuceso);
                                                                            /////////////// NUEVO
                                                                            /////////////// SUCESO
                                                                            /////////////// PARA
                                                                            /////////////// CONTABLE
                                                                            SisSuceso sisSucesoContable = Suceso
                                                                                    .crearSisSuceso(
                                                                                            "contabilidad.con_contable",
                                                                                            conContable.getConContablePK().getConPeriodo()
                                                                                            + " "
                                                                                            + conContable.getConContablePK().getConTipo()
                                                                                            + " "
                                                                                            + conContable
                                                                                                    .getConContablePK()
                                                                                                    .getConNumero(),
                                                                                            "RESTORE",
                                                                                            "Se restauró el contable del periodo "
                                                                                            + conContable
                                                                                                    .getConContablePK()
                                                                                                    .getConPeriodo()
                                                                                            + ", del tipo "
                                                                                            + conContable
                                                                                                    .getConContablePK()
                                                                                                    .getConTipo()
                                                                                            + ", de la numeración "
                                                                                            + conContable
                                                                                                    .getConContablePK()
                                                                                                    .getConNumero(),
                                                                                            sisInfoTO);
                                                                            listaSisSuceso
                                                                                    .add(sisSucesoContable);
                                                                            sisSuceso = null;
                                                                        }
                                                                    }

                                                                    InvVentasComplemento invVentasComplemento = null;
                                                                    InvVentasComplemento invVentasComplementoAux = null;
                                                                    puedeContinuar = true;//false
                                                                    String complemento = "";
                                                                    if (!invVentas.getVtaAnulado()) {
                                                                        invVentasComplementoAux = ventasComplementoDao
                                                                                .buscarVentasComplemento(
                                                                                        invVentas
                                                                                                .getInvVentasPK()
                                                                                                .getVtaEmpresa(),
                                                                                        invVentas
                                                                                                .getInvVentasPK()
                                                                                                .getVtaPeriodo(),
                                                                                        invVentas
                                                                                                .getInvVentasPK()
                                                                                                .getVtaMotivo(),
                                                                                        invVentas
                                                                                                .getInvVentasPK()
                                                                                                .getVtaNumero());

                                                                        if (invVentasComplementoAux != null
                                                                                && (!invVentas
                                                                                        .getVtaDocumentoTipo()
                                                                                        .equals("04")
                                                                                && !invVentas
                                                                                        .getVtaDocumentoTipo()
                                                                                        .equals("05"))) {
                                                                            puedeContinuar = true;
                                                                            complemento = "ELIMINAR";
                                                                            invVentasComplemento = new InvVentasComplemento();
                                                                            invVentasComplemento
                                                                                    .setComDocumentoNumero(
                                                                                            invVentasComplementoAux
                                                                                                    .getComDocumentoNumero());
                                                                            invVentasComplemento
                                                                                    .setComDocumentoTipo(
                                                                                            invVentasComplementoAux
                                                                                                    .getComDocumentoTipo());
                                                                        } else if (invVentasComplementoAux == null
                                                                                && (invVentas
                                                                                        .getVtaDocumentoTipo()
                                                                                        .equals("04")
                                                                                || invVentas
                                                                                        .getVtaDocumentoTipo()
                                                                                        .equals("05"))
                                                                                && (!numeroDocumentoComplemento
                                                                                        .trim().isEmpty()
                                                                                && !tipoDocumentoComplemento
                                                                                        .trim()
                                                                                        .isEmpty())) {
                                                                            complemento = "CREAR";
                                                                            invVentasComplemento = new InvVentasComplemento();
                                                                            invVentasComplemento
                                                                                    .setComDocumentoNumero(
                                                                                            numeroDocumentoComplemento);
                                                                            invVentasComplemento
                                                                                    .setComDocumentoTipo(
                                                                                            tipoDocumentoComplemento);
                                                                            codigoFactura = ventasDao
                                                                                    .getConteoNotaCreditoVenta(
                                                                                            invVentasTO
                                                                                                    .getVtaEmpresa(),
                                                                                            invVentasTO
                                                                                                    .getCliCodigo(),
                                                                                            tipoDocumentoComplemento,
                                                                                            numeroDocumentoComplemento);
                                                                            if (!codigoFactura.trim()
                                                                                    .isEmpty()
                                                                                    || invVentasTO
                                                                                            .getVtaDocumentoNumero()
                                                                                            .equals("999-999-999999999")) {
                                                                                puedeContinuar = true;
                                                                            }
                                                                        } else if (invVentasComplementoAux != null
                                                                                && (invVentas
                                                                                        .getVtaDocumentoTipo()
                                                                                        .equals("04")
                                                                                || invVentas
                                                                                        .getVtaDocumentoTipo()
                                                                                        .equals("05"))
                                                                                && (!numeroDocumentoComplemento
                                                                                        .trim()
                                                                                        .isEmpty()
                                                                                && !tipoDocumentoComplemento
                                                                                        .trim()
                                                                                        .isEmpty())) {
                                                                            complemento = "MODIFICAR";
                                                                            invVentasComplemento = new InvVentasComplemento();
                                                                            invVentasComplemento
                                                                                    .setComDocumentoNumero(
                                                                                            numeroDocumentoComplemento);
                                                                            invVentasComplemento
                                                                                    .setComDocumentoTipo(
                                                                                            tipoDocumentoComplemento);
                                                                            codigoFactura = ventasDao
                                                                                    .getConteoNotaCreditoVenta(
                                                                                            invVentasTO
                                                                                                    .getVtaEmpresa(),
                                                                                            invVentasTO
                                                                                                    .getCliCodigo(),
                                                                                            tipoDocumentoComplemento,
                                                                                            numeroDocumentoComplemento);
                                                                            if (!codigoFactura.trim()
                                                                                    .isEmpty()
                                                                                    || invVentasTO
                                                                                            .getVtaDocumentoNumero()
                                                                                            .equals("999-999-999999999")) {
                                                                                puedeContinuar = true;
                                                                            }
                                                                        } else {
                                                                            puedeContinuar = true;
                                                                        }
                                                                    } else {
                                                                        puedeContinuar = true;
                                                                    }

                                                                    if (puedeContinuar) {
                                                                        invVentas.setVtaBaseExenta(cero);
                                                                        invVentas.setVtaBaseNoObjeto(cero);
                                                                        invVentas
                                                                                .setVtaSubtotalBaseExenta(cero);
                                                                        invVentas.setVtaSubtotalBaseNoObjeto(
                                                                                cero);
                                                                        invVentas.setVtaDescuentoBaseExenta(
                                                                                cero);
                                                                        invVentas.setVtaDescuentoBaseNoObjeto(
                                                                                cero);

                                                                        if (invVentas.getVtaAnulado()) {
                                                                            invVentasMotivoAnulacion
                                                                                    .setInvVentas(invVentas);
                                                                        }

                                                                        if (invVentas.getVtaDocumentoTipo()
                                                                                .equals("00")) {
                                                                            invVentas.setVtaDocumentoNumero(
                                                                                    null);
                                                                        }
                                                                        if (invVentas.getConNumero() == null
                                                                                || invVentas
                                                                                        .getConPeriodo() == null
                                                                                || invVentas
                                                                                        .getConTipo() == null) {
                                                                            invVentas.setConEmpresa(null);
                                                                            invVentas.setConNumero(null);
                                                                            invVentas.setConPeriodo(null);
                                                                            invVentas.setConTipo(null);
                                                                        }

                                                                        comprobar = ventasDao
                                                                                .modificarInvVentas(invVentas,
                                                                                        listInvVentasDetalle,
                                                                                        listInvVentasDetalleEliminar,
                                                                                        sisSuceso,
                                                                                        listaSisSuceso,
                                                                                        conContable, anxVenta,
                                                                                        invVentasComplemento,
                                                                                        complemento,
                                                                                        invVentasMotivoAnulacion,
                                                                                        eliminarMotivoAnulacion,
                                                                                        desmayorizar, true);
                                                                        if (comprobar) {
                                                                            // quitarPendiente(invVentasTO);

                                                                            SisPeriodo sisPeriodo = periodoService
                                                                                    .buscarPeriodo(
                                                                                            invVentasTO
                                                                                                    .getVtaEmpresa(),
                                                                                            invVentas
                                                                                                    .getInvVentasPK()
                                                                                                    .getVtaPeriodo());
                                                                            String accion = "";
                                                                            if (invVentasTO.getVtaAnulado()) {
                                                                                accion = "anuló";
                                                                            } else if (quitarAnulado) {
                                                                                accion = "restauró";
                                                                            } else {
                                                                                accion = "modificó";
                                                                            }
                                                                            retorno = "T<html>Se  " + accion
                                                                                    + "  la Venta con la siguiente información:<br><br>"
                                                                                    + "Periodo: <font size = 5>"
                                                                                    + sisPeriodo.getPerDetalle()
                                                                                    + "</font>.<br> Motivo: <font size = 5>"
                                                                                    + invVentas.getInvVentasPK()
                                                                                            .getVtaMotivo()
                                                                                    + "</font>.<br> Número: <font size = 5>"
                                                                                    + invVentas.getInvVentasPK()
                                                                                            .getVtaNumero()
                                                                                    + "</font>.</html>"
                                                                                    + invVentas.getInvVentasPK()
                                                                                            .getVtaNumero()
                                                                                    + ","
                                                                                    + sisPeriodo
                                                                                            .getSisPeriodoPK()
                                                                                            .getPerCodigo();
                                                                            mensajeTO.setFechaCreacion(invVentas
                                                                                    .getUsrFechaInserta()
                                                                                    .toString());

                                                                            if (!carListaPagosCobrosVencidosTOs
                                                                                    .isEmpty()) {
                                                                                mensajeClase.add(
                                                                                        "<html>Cliente tiene deudas vencidas! Avise al cliente que\nse acerque a cancelar lo más pronto...</html>");
                                                                                mensajeClase.add(
                                                                                        "Número de Sistema\tValor\tFecha de Vencimiento");
                                                                                for (int i = 0; i < carListaPagosCobrosVencidosTOs
                                                                                        .size(); i++) {
                                                                                    mensajeClase
                                                                                            .add(carListaPagosCobrosVencidosTOs
                                                                                                    .get(i)
                                                                                                    .getCxccPeriodo()
                                                                                                    + " | "
                                                                                                    + carListaPagosCobrosVencidosTOs
                                                                                                            .get(i)
                                                                                                            .getCxccMotivo()
                                                                                                    + " | "
                                                                                                    + carListaPagosCobrosVencidosTOs
                                                                                                            .get(i)
                                                                                                            .getCxccNumero()
                                                                                                    + "\t"
                                                                                                    + carListaPagosCobrosVencidosTOs
                                                                                                            .get(i)
                                                                                                            .getCxccSaldo()
                                                                                                    + "\t"
                                                                                                    + carListaPagosCobrosVencidosTOs
                                                                                                            .get(i)
                                                                                                            .getCxccFechaVencimiento());
                                                                                }
                                                                                mensajeTO.setListaErrores1(
                                                                                        mensajeClase);
                                                                            }

                                                                        } else {
                                                                            retorno = "FHubo un error al modificar la Venta...\nIntente de nuevo o contacte con el administrador";
                                                                        }

                                                                    } else {
                                                                        retorno = "F<html>El Número de Documento del Complemento que ingresó no existe...\nIntente de nuevo o contacte con el administrador</html>";
                                                                    }
                                                                } else {
                                                                    retorno = "F<html>No hay stock suficiente en los siguientes productos...........</html>";
                                                                    mensajeTO.setListaErrores1(mensajeClase);
                                                                }
                                                            } else {
                                                                retorno = "F<html>El Número de Retención que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                                            }
                                                        } else {
                                                            retorno = "F<html>No se puede realizar la acción solicitada sobre la venta.</html>";
                                                        }
                                                    } else if (estadoDetalleEliminar == 1) {
                                                        retorno = "F<html>Uno de los Productos que escogió ya no esta¡ disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                                    } else {
                                                        retorno = "F<html>Una de las Bodega que escogió ya no está¡ disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                                    }
                                                } else if (estadoDetalle == 1) {
                                                    retorno = "F<html>Uno de los Productos que escogió ya no esta¡ disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                                } else {
                                                    retorno = "F<html>Una de las Bodega que escogió ya no esta¡ disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                                }
                                            } else if (validacionModificar) {
                                                retorno = "F<html>La venta que quiere modificar ya no se encuentra disponible.</html>";
                                            } else {
                                                retorno = "F<html>La venta que desea restaurar NO existe o NO esta¡ anulada</html>";
                                            }
                                        } else {
                                            retorno = "F<html>No se encuentra el motivo...</html>";
                                        }
                                    } else {
                                        retorno = "F<html>No se puede MAYORIZAR, DESMAYORIZAR o ANULAR debido a que el periodo se encuentra cerrado...</html>";
                                    }
                                } else {
                                    retorno = "F<html>No se encuentra ningun periodo para la fecha que ingresó...</html>";
                                }
                            } else {
                                retorno = "F<html>Se superó el limite del monto del credito...</html>";
                            }
                        } else {
                            retorno = "F<html>Los días de crédito del Cliente es superior al que se le permite...</html>";
                        }
                    } else {
                        retorno = "F<html>" + detalleError + "</html>";
                    }
                } else {
                    retorno = "F<html>El Cliente que escogió ya no esta¡ disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
                }
            } else {
                retorno = "F<html>Cliente tiene deudas vencidas! Avise al cliente que\nse acerque a cancelar lo más pronto...</html>";
                mensajeTO.setMensaje(retorno);
                mensajeClase.add("Número de Sistema\tValor\tFecha de Vencimiento");
                for (int i = 0; i < carListaPagosCobrosVencidosTOs.size(); i++) {
                    mensajeClase.add(carListaPagosCobrosVencidosTOs.get(i).getCxccPeriodo() + " | "
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccMotivo() + " | "
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccNumero() + "\t"
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccSaldo() + "\t"
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccFechaVencimiento());
                }
                mensajeTO.setListaErrores1(mensajeClase);
            }
        } else {
            retorno = "F<html>El NUMERO DE DOCUMENTO que ingresó se encuentra ANULADO</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;

    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return ventasDao.getEstadoCCCVT(empresa, periodo, motivo, numero);
    }

    public String asignarNumeroDocumentoVentaRecurrente(String empresa, String tipoDocumento, String secuencial) throws Exception {
        String numeroDocumento = "";
        switch (tipoDocumento) {
            case "04": //nota de credito 
            case "05": //nota de debito
            case "18": //factura ventas
                if (secuencial != null) {
                    String ultimoSecuencial = ventaService.getUltimaNumeracionComprobante(empresa, tipoDocumento, secuencial);
                    if (ultimoSecuencial == null || "".equals(ultimoSecuencial)) {
                        numeroDocumento = secuencial + "-000000001";
                    }
                    if (ultimoSecuencial != null && !"".equals(ultimoSecuencial)) {
                        String separado = ultimoSecuencial.substring(0, ultimoSecuencial.lastIndexOf("-") + 1);
                        int numero = Integer.parseInt(ultimoSecuencial.substring(ultimoSecuencial.lastIndexOf("-") + 2));
                        int numeroParaDocumento = numero + 1;
                        int digitosNumero = ("" + numeroParaDocumento).length();
                        for (int i = 0; i < (9 - digitosNumero); i++) {
                            numeroDocumento = numeroDocumento + "0";
                        }
                        numeroDocumento = separado + numeroDocumento + numeroParaDocumento;
                    }
                    if (!isNumeroDocumentoValido(empresa, tipoDocumento, numeroDocumento)) {
                        return "";
                    }
                }
                break;
            default:
                if (tipoDocumento.equalsIgnoreCase("00")) {
                    return "999-999-999999999";
                }
                break;
        }
        return numeroDocumento;
    }

    @Override
    public String asignarNumeroDocumento(String empresa, String tipoDocumento, SisInfoTO sisInfoTO) throws Exception {
        String numeroDocumento = "";
        SisLoginTO sisLoginTO = usuarioService.getPermisoInventarioTO(sisInfoTO.getEmpresa(), sisInfoTO.getUsuario());
        switch (tipoDocumento) {
            case "04": //nota de credito 
                if (sisLoginTO != null && sisLoginTO.getPerSecuencialPermitidoNotaCredito() != null) {
                    String ultimoSecuencialCredito = ventaService.getUltimaNumeracionComprobante(empresa, tipoDocumento, sisLoginTO.getPerSecuencialPermitidoNotaCredito());
                    if (ultimoSecuencialCredito == null || "".equals(ultimoSecuencialCredito)) {
                        numeroDocumento = sisLoginTO.getPerSecuencialPermitidoNotaCredito() + "-000000001";
                    }
                    if (ultimoSecuencialCredito != null && !"".equals(ultimoSecuencialCredito)) {
                        String separado = ultimoSecuencialCredito.substring(0, ultimoSecuencialCredito.lastIndexOf("-") + 1);
                        int numero = Integer.parseInt(ultimoSecuencialCredito.substring(ultimoSecuencialCredito.lastIndexOf("-") + 2));
                        int numeroParaDocumento = numero + 1;
                        int digitosNumero = ("" + numeroParaDocumento).length();
                        for (int i = 0; i < (9 - digitosNumero); i++) {
                            numeroDocumento = numeroDocumento + "0";
                        }
                        numeroDocumento = separado + numeroDocumento + numeroParaDocumento;
                    }
                    if (!isNumeroDocumentoValido(empresa, tipoDocumento, numeroDocumento)) {
                        return "";
                    }
                    break;
                }

            case "05": //nota de debito
                if (sisLoginTO != null && sisLoginTO.getPerSecuencialPermitidoNotaDebito() != null) {
                    String ultimoSecuencialDebito = ventaService.getUltimaNumeracionComprobante(empresa, tipoDocumento, sisLoginTO.getPerSecuencialPermitidoNotaDebito());
                    if (ultimoSecuencialDebito == null || "".equals(ultimoSecuencialDebito)) {
                        numeroDocumento = sisLoginTO.getPerSecuencialPermitidoNotaDebito() + "-000000001";
                    }
                    if (ultimoSecuencialDebito != null && !"".equals(ultimoSecuencialDebito)) {
                        String separado = ultimoSecuencialDebito.substring(0, ultimoSecuencialDebito.lastIndexOf("-") + 1);
                        int numero = Integer.parseInt(ultimoSecuencialDebito.substring(ultimoSecuencialDebito.lastIndexOf("-") + 2));
                        int numeroParaDocumento = numero + 1;
                        int digitosNumero = ("" + numeroParaDocumento).length();
                        for (int i = 0; i < (9 - digitosNumero); i++) {
                            numeroDocumento = numeroDocumento + "0";
                        }
                        numeroDocumento = separado + numeroDocumento + numeroParaDocumento;
                    }
                    if (!isNumeroDocumentoValido(empresa, tipoDocumento, numeroDocumento)) {
                        return "";
                    }
                    break;
                }
            case "18": //factura ventas
                if (sisLoginTO != null && sisLoginTO.getPerSecuencialPermitidoVentas() != null) {
                    String ultimoSecuencial = ventaService.getUltimaNumeracionComprobante(empresa, tipoDocumento, sisLoginTO.getPerSecuencialPermitidoVentas());
                    if (ultimoSecuencial == null || "".equals(ultimoSecuencial)) {
                        numeroDocumento = sisLoginTO.getPerSecuencialPermitidoVentas() + "-000000001";
                    }
                    if (ultimoSecuencial != null && !"".equals(ultimoSecuencial)) {
                        String separado = ultimoSecuencial.substring(0, ultimoSecuencial.lastIndexOf("-") + 1);
                        int numero = Integer.parseInt(ultimoSecuencial.substring(ultimoSecuencial.lastIndexOf("-") + 2));
                        int numeroParaDocumento = numero + 1;
                        int digitosNumero = ("" + numeroParaDocumento).length();
                        for (int i = 0; i < (9 - digitosNumero); i++) {
                            numeroDocumento = numeroDocumento + "0";
                        }
                        numeroDocumento = separado + numeroDocumento + numeroParaDocumento;
                    }
                    if (!isNumeroDocumentoValido(empresa, tipoDocumento, numeroDocumento)) {
                        return "";
                    }
                    break;
                }
            default:
                if (tipoDocumento.equalsIgnoreCase("00")) {
                    return "999-999-999999999";
                }
                break;

        }
        return numeroDocumento;
    }

    @Override
    public boolean validarNumero(String empresa, String tipoDocumento, String numeroDocumento, SisInfoTO sisInfoTO) throws Exception {
        Boolean isValido = isNumeroDocumentoValido(empresa, tipoDocumento, numeroDocumento);
        return isValido;
    }

    public boolean isNumeroDocumentoValido(String empresa, String tipoDocumento, String numeroDocumento) throws Exception {
        List<AnxNumeracionTablaTO> listaAnxNumeracionTablaTO = numeracionService.getListaAnexoNumeracionTO(empresa);
        String secuencialPermitido = numeroDocumento.substring(0, 7);
        String ultimosDigitos = numeroDocumento.substring(8);
        for (AnxNumeracionTablaTO item : listaAnxNumeracionTablaTO) {
            if (item.getNumeroComprobante().equalsIgnoreCase(tipoDocumento)
                    && item.getEstablecimientoPtoEmisionDesde().equalsIgnoreCase(secuencialPermitido)
                    && item.getEstablecimientoPtoEmisionHasta().equalsIgnoreCase(secuencialPermitido)) {
                if (Integer.parseInt(ultimosDigitos) >= Integer.parseInt(item.getNumeroDesde().substring(8))
                        && Integer.parseInt(ultimosDigitos) <= Integer.parseInt(item.getNumeroHasta().substring(8))) {
                    return true;
                }
            }
        }
        return false;
    }

    public int obtenerFacturasDisponibles(String empresa, String tipoDocumento, String usuarioCodigo) throws Exception {
        int numero = 1;
        SisLoginTO sisLoginTO = usuarioService.getPermisoInventarioTO(empresa, usuarioCodigo);
        if (sisLoginTO != null && sisLoginTO.getPerSecuencialPermitidoVentas() != null) {
            String ultimoSecuencial = ventaService.getUltimaNumeracionComprobante(empresa, tipoDocumento, sisLoginTO.getPerSecuencialPermitidoVentas());
            if (ultimoSecuencial != null && !"".equals(ultimoSecuencial)) {
                numero = Integer.parseInt(ultimoSecuencial.substring(ultimoSecuencial.lastIndexOf("-") + 2));
            }
            List<AnxNumeracionTablaTO> numeraciones = numeracionService.getListaAnexoNumeracionTO(empresa, tipoDocumento);
            if (numeraciones != null && !numeraciones.isEmpty()) {
                for (AnxNumeracionTablaTO item : numeraciones) {
                    if (item.getEstablecimientoPtoEmisionDesde().equalsIgnoreCase(sisLoginTO.getPerSecuencialPermitidoVentas())
                            && item.getEstablecimientoPtoEmisionHasta().equalsIgnoreCase(sisLoginTO.getPerSecuencialPermitidoVentas())) {
                        if ((numero + 1) >= Integer.parseInt(item.getNumeroDesde().substring(8))
                                && (numero + 1) <= Integer.parseInt(item.getNumeroHasta().substring(8))) {
                            return Integer.parseInt(item.getNumeroHasta().substring(8)) - numero;//restar numero - numeracion-desde
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int obtenerFacturasDisponiblesPorPuntoEmision(String empresa, String tipoDocumento, String secuencial) throws Exception {
        int numero = 1;
        if (secuencial != null) {
            String ultimoSecuencial = ventaService.getUltimaNumeracionComprobante(empresa, tipoDocumento, secuencial);
            if (ultimoSecuencial != null && !"".equals(ultimoSecuencial)) {
                numero = Integer.parseInt(ultimoSecuencial.substring(ultimoSecuencial.lastIndexOf("-") + 2));
            }
            List<AnxNumeracionTablaTO> numeraciones = numeracionService.getListaAnexoNumeracionTO(empresa, tipoDocumento);
            if (numeraciones != null && !numeraciones.isEmpty()) {
                for (AnxNumeracionTablaTO item : numeraciones) {
                    if (item.getEstablecimientoPtoEmisionDesde().equalsIgnoreCase(secuencial)
                            && item.getEstablecimientoPtoEmisionHasta().equalsIgnoreCase(secuencial)) {
                        if ((numero + 1) >= Integer.parseInt(item.getNumeroDesde().substring(8))
                                && (numero + 1) <= Integer.parseInt(item.getNumeroHasta().substring(8))) {
                            return Integer.parseInt(item.getNumeroHasta().substring(8)) - numero;//restar numero - numeracion-desde
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public Map<String, Object> obtenerDatosParaVentas(Map<String, Object> map) throws Exception {
        List<AnxTipoComprobanteComboTO> listaDocumentosReembolso = new ArrayList<>();
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usuarioCodigo"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        InvVentasTO venta = new InvVentasTO();
        CajCajaTO caja = cajaDao.getCajCajaTO(empresa, usuarioCodigo);
        listaDocumentosReembolso = tipoComprobanteService.getListaAnxTipoComprobanteComboTOCompleto();
        List<SisPeriodo> listadoPeriodos = periodoService.getListaPeriodo(empresa);
        List<InvVentaMotivoTO> listadoMotivos = ventasMotivoDao.getListaInvVentasMotivoTO(empresa, true, tipoDocumento);
        String numeroDocumento = asignarNumeroDocumento(empresa, tipoDocumento, sisInfoTO);
        InvProductoEtiquetas etiquetas = productoService.traerEtiquetas(empresa);
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        BigDecimal iva = porcentajeIvaDao.getValorAnxPorcentajeIvaTO(UtilsDate.fechaFormatoString(fechaActual, "yyyy-MM-dd"));
        //poner en cero el iva dependiento de quimiplast y nota entrega
        if (tipoDocumento != null && tipoDocumento.equals("00")) {
            List<String> rucsSinIva = Arrays.asList("0909476103001");
            SisEmpresa e = empresaDao.obtenerPorId(SisEmpresa.class, empresa);
            if (e != null && rucsSinIva.contains(e.getEmpRuc())) {
                iva = cero;
            }
        }

        BigDecimal montoMaximoConsumidorFinal = porcentajeIvaDao.getValorAnxMontoMaximoConsumidorFinalTO(UtilsDate.fechaFormatoString(fechaActual, "yyyy-MM-dd"));
        List<InvVentasFormaCobroTO> listadoFormaCobro = ventaFormaCobroDao.getListaInvVentasFormaCobroTO(empresa, false);
        List<AnxTipoComprobanteComboTO> listadoTipoComprobante = tipoComprobanteDao.getListaAnxTipoComprobanteComboTO(null);
        List<InvListaBodegasTO> listadoBodegas = bodegaDao.buscarBodegasTO(empresa, false, null);
        List<InvVendedorComboListadoTO> listaVendedores = vendedorService.getComboinvListaVendedorTOs(empresa, null);

        /*Lista motivos*/
        List<InvProformaMotivoTO> listaInvProformaMotivoTO = proformasMotivoService.getListaInvProformaMotivoTO(empresa, false, null);
        campos.put("listaInvProformaMotivoTO", listaInvProformaMotivoTO);
        /*Buscar bodega y listado de piscinas segun permiso de caja*/
        if (caja != null && caja.getPermisoBodegaPermitida() != null) {
            List<InvBodegaTO> bodegas = bodegaDao.getBodegaTO(empresa, caja.getPermisoBodegaPermitida());
            if (bodegas != null && !bodegas.isEmpty()) {
                InvBodegaTO invBodega = bodegas.get(0);
                if (invBodega != null) {
                    venta.setBodCodigo(invBodega.getBodCodigo());
                    List<PrdListaPiscinaTO> listadoPiscinas = piscinaDao.getListaPiscina(empresa, invBodega.getSecCodigo(), false);
                    campos.put("listadoPiscinas", listadoPiscinas);
                }
            }
        }
        /*forma de cobro*/
        if (caja != null && caja.getPermisoFormaPagoPermitida() != null && !caja.getPermisoFormaPagoPermitida().equals("")) {
            listadoFormaCobro.stream().filter((invComboFormaPagoTO) -> (invComboFormaPagoTO.getFcDetalle().contains(caja.getPermisoFormaPagoPermitida()))).forEachOrdered((invComboFormaPagoTO) -> {
                campos.put("formaCobroSeleccionada", invComboFormaPagoTO);
            });
        }
        //Asignacion a venta
        venta.setVtaIvaVigente(iva);
        venta.setVtaMotivo(caja != null ? caja.getPermisoMotivoPermitido() : null);
        venta.setVtaDocumentoNumero(numeroDocumento);
        venta.setVtaFecha(UtilsDate.fechaFormatoString(fechaActual, "yyyy-MM-dd"));
        boolean numeroValido = numeroDocumento != null && !numeroDocumento.equals("") ? validarNumero(empresa, tipoDocumento, numeroDocumento, sisInfoTO) : false;
        AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(empresa, numeroDocumento, tipoDocumento, UtilsDate.fechaFormatoString(fechaActual, "yyyy-MM-dd"));
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        //LIQUIDACION
        List<PrdLiquidacionMotivo> listaMotivoLiquidacion = liquidacionMotivoService.getListaPrdLiquidacionMotivoTO(empresa, true);
        List<PrdListaPiscinaTO> listadoPiscinas = piscinaDao.getListaPiscina(empresa);
        List<SisEmpresa> listaEmpresa = usuarioDetalleService.getEmpresasPorUsuarioItem(usuarioCodigo, "cobrosCartera", empresa);
        campos.put("listaEmpresa", listaEmpresa);
        campos.put("caja", caja);
        campos.put("numeroValido", numeroValido);
        campos.put("anxNumeracionLineaTO", anxNumeracionLineaTO);
        campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("listadoPeriodos", listadoPeriodos);
        campos.put("listadoMotivos", listadoMotivos);
        campos.put("etiquetas", etiquetas);
        campos.put("fechaActual", fechaActual);
        campos.put("listadoFormaCobro", listadoFormaCobro);
        campos.put("listadoTipoComprobante", listadoTipoComprobante);
        campos.put("listadoBodegas", listadoBodegas);
        campos.put("venta", venta);
        campos.put("montoMaximoConsumidorFinal", montoMaximoConsumidorFinal);
        campos.put("listaVendedores", listaVendedores);
        campos.put("listaMotivoLiquidacion", listaMotivoLiquidacion);
        campos.put("listadoPiscinas", listadoPiscinas);
        campos.put("listaDocumentosReembolso", listaDocumentosReembolso);
        return campos;

    }

    @Override
    public Map<String, Object> obtenerDatosListadoVentas(Map<String, Object> map) throws Exception {

        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usuarioCodigo"));

        CajCajaTO caja = cajaDao.getCajCajaTO(empresa, usuarioCodigo);
        List<AnxTipoComprobanteComboTO> listaTipoComprobante = tipoComprobanteDao.getListaAnxTipoComprobanteComboTO(null);
        List<PrdListaSectorTO> listadoSectores = sectorService.obtenerTodosLosSectores(empresa);
        List<InvVentaMotivoTO> listadoMotivos = ventasMotivoDao.getListaInvVentasMotivoTO(empresa, true, null);
        List<InvClienteGrupoEmpresarialTO> listaGrupoEmpresarial = invClienteGrupoEmpresarialService.getInvClienteGrupoEmpresarialTO(empresa, null);
        List<InvVentasFormaCobroTO> listaInvVentasFormaCobroTO = ventaFormaCobroService.getListaInvVentasFormaCobroTO(empresa, false);

        campos.put("caja", caja);
        campos.put("listaTipoComprobante", listaTipoComprobante);
        campos.put("listadoSectores", listadoSectores);
        campos.put("listadoMotivos", listadoMotivos);
        campos.put("listaGrupoEmpresarial", listaGrupoEmpresarial);
        campos.put("listaInvVentasFormaCobroTO", listaInvVentasFormaCobroTO);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaVentasRecurrentes(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("usuarioCodigo"));
        InvVentasTO venta = new InvVentasTO();
        CajCajaTO caja = cajaDao.getCajCajaTO(empresa, usuarioCodigo);
        List<SisPeriodo> listadoPeriodos = periodoService.getListaPeriodo(empresa);
        List<InvVentaMotivoTO> listadoMotivos = ventasMotivoDao.getListaInvVentasMotivoTO(empresa, true, null);
        InvProductoEtiquetas etiquetas = productoService.traerEtiquetas(empresa);
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        BigDecimal iva = porcentajeIvaDao.getValorAnxPorcentajeIvaTO(UtilsDate.fechaFormatoString(fechaActual, "yyyy-MM-dd"));
        List<InvVentasFormaCobroTO> listadoFormaCobro = ventaFormaCobroDao.getListaInvVentasFormaCobroTO(empresa, false);
        List<InvListaBodegasTO> listadoBodegas = bodegaDao.buscarBodegasTO(empresa, false, null);
        List<InvClienteCategoriaTO> listadoCategorias = clienteCategoriaService.getInvClienteCategoriaTO(empresa);
        List<AnxTipoComprobanteComboTO> listadoTipoComprobante = tipoComprobanteDao.getListaAnxTipoComprobanteComboParaVentaRecurrente();

        if (caja != null && caja.getPermisoBodegaPermitida() != null) {
            List<InvBodegaTO> bodegas = bodegaDao.getBodegaTO(empresa, caja.getPermisoBodegaPermitida());
            if (bodegas != null && !bodegas.isEmpty()) {
                InvBodegaTO invBodega = bodegas.get(0);
                if (invBodega != null) {
                    venta.setBodCodigo(invBodega.getBodCodigo());
                }
            }
        }
        /*forma de cobro*/
        if (caja != null && caja.getPermisoFormaPagoPermitida() != null && !caja.getPermisoFormaPagoPermitida().equals("")) {
            listadoFormaCobro.stream().filter((invComboFormaPagoTO) -> (invComboFormaPagoTO.getFcDetalle().contains(caja.getPermisoFormaPagoPermitida()))).forEachOrdered((invComboFormaPagoTO) -> {
                campos.put("formaCobroSeleccionada", invComboFormaPagoTO);
            });
        }
        //Asignacion a venta
        venta.setVtaIvaVigente(iva);
        venta.setVtaMotivo(caja != null ? caja.getPermisoMotivoPermitido() : null);
        venta.setVtaFecha(UtilsDate.fechaFormatoString(fechaActual, "yyyy-MM-dd"));

        campos.put("caja", caja);
        campos.put("listadoPeriodos", listadoPeriodos);
        campos.put("listadoMotivos", listadoMotivos);
        campos.put("etiquetas", etiquetas);
        campos.put("listadoFormaCobro", listadoFormaCobro);
        campos.put("listadoBodegas", listadoBodegas);
        campos.put("listadoCategorias", listadoCategorias);
        campos.put("venta", venta);
        campos.put("listadoTipoComprobante", listadoTipoComprobante);
        return campos;
    }

    @Override
    public Map<String, Object> listarClientesParaVentarecurrente(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usuarioCodigo"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        if (tipoDocumento != null && !tipoDocumento.equals("00")) {
            CajCajaTO caja = cajaDao.getCajCajaTO(empresa, usuarioCodigo);
            if (caja != null) {

                int totalComprobantes = 0;
                List<ComboGenericoTO> secuencialesDisponibles = new ArrayList<>();
                List<InvListaSecuencialesTO> secuenciales = listarInvListaSecuencialesVentas(empresa, tipoDocumento);
                if (secuenciales != null) {
                    for (InvListaSecuencialesTO secuencial : secuenciales) {
                        String sec = secuencial.getSecuencial().substring(0, 7);
                        int cantidad = obtenerFacturasDisponiblesPorPuntoEmision(empresa, tipoDocumento, sec);
                        totalComprobantes = totalComprobantes + cantidad;
                        ComboGenericoTO combo = new ComboGenericoTO(sec, cantidad + "");
                        secuencialesDisponibles.add(combo);
                    }
                }

                if (totalComprobantes > 0) {
                    if (caja.getPermisoSecuencialFacturas() != null && !caja.getPermisoSecuencialFacturas().equals("")) {
                        int totalFacturas = obtenerFacturasDisponibles(empresa, tipoDocumento, usuarioCodigo);
                        campos.put("totalFacturas", totalFacturas);
                        campos.put("secuencialFacturas", caja.getPermisoSecuencialFacturas());
                        secuencialesDisponibles.removeIf(l -> l.getClave().equals(caja.getPermisoSecuencialFacturas()));
                    }
                    campos.put("secuencialesDisponibles", secuencialesDisponibles);
                } else {
                    throw new GeneralException("No hay número de comprobantes disponibles.");
                }
            } else {
                throw new GeneralException("Usted no cuenta con el permiso de caja para realizar facturación.");
            }
        }
        List<InvClienteRecurrenteTO> clientesRecurrentes = clienteDao.listarClientesParaVentarecurrente(empresa, fecha);
        if (clientesRecurrentes != null && !clientesRecurrentes.isEmpty()) {
            campos.put("clientesRecurrentes", clientesRecurrentes);
        } else {
            throw new GeneralException("No hay información sobre clientes recurrentes.");
        }
        return campos;
    }

    @Override
    public List<InvVentasDetalleTO> obtenerVentaDetalleTOPorNumero(String vtaEmpresa, String vtaPeriodo, String vtaMotivo, String vtaNumero) throws Exception {
        List<InvVentasDetalle> listaDetalle = ventasDao.obtenerVentaDetallePorNumero(vtaEmpresa, vtaPeriodo, vtaMotivo, vtaNumero);
        SisEmpresaTO sisEmpresaTO = empresaDao.obtenerSisEmpresaTO(vtaEmpresa);
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = new ArrayList<>();
        for (InvVentasDetalle detalle : listaDetalle) {
            InvVentasDetalleTO detalleTO = ConversionesInventario.convertirInvVentasDetalle_InvVentasDetalleTO(detalle);
            detalleTO.setDetValorPromedio(detalleTO.getDetCantidad().multiply(detalleTO.getDetPrecio()));
            detalleTO.setParAgenteRetencion(sisEmpresaTO.getParAgenteRetencion());
            detalleTO.setParContribuyenteRegimenMicroempresa(sisEmpresaTO.isParContribuyenteRegimenMicroempresa());
            detalleTO.setParObligadoLlevarContabilidad(sisEmpresaTO.getEmObligadoLlevarContabilidad());
            detalleTO.setParResolucionContribuyenteEspecial(sisEmpresaTO.getEmResolucionContribuyenteEspecial());
            detalleTO.setMedConversionKilos(detalle.getInvProducto().getInvProductoMedida().getMedConversionKilos());
            detalleTO.setMedConversionLibras(detalle.getInvProducto().getInvProductoMedida().getMedConversionLibras());
            listaInvVentasDetalleTO.add(detalleTO);
        }
        return listaInvVentasDetalleTO;
    }

    @Override
    public Map<String, Object> consultarVenta(Map<String, Object> map) throws Exception {
        InvVentas venta = null;
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String numeroDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("numeroDocumento"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String vtaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("vtaNumero"));
        boolean quiereAnular = UtilsJSON.jsonToObjeto(boolean.class, map.get("quiereAnular"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String sectorSeleccionado = "";
        if (vtaNumero != null && !vtaNumero.equals("")) {
            List<String> documento = UtilsValidacion.separar(vtaNumero, "|");
            if (documento != null && !documento.isEmpty()) {
                venta = ventasDao.buscarInvVentas(empresa, documento.get(0), documento.get(1), documento.get(2));
            } else {
                venta = ventasDao.obtenerVentaPorNumero(empresa, tipoDocumento, numeroDocumento);
            }
        } else {
            venta = ventasDao.obtenerVentaPorNumero(empresa, tipoDocumento, numeroDocumento);
        }
        if (venta != null) {
            InvVentasTO ventasTO = ConversionesInventario.convertirInvVentas_InvVentasTO(venta);
            InvVentaCabeceraTO invVentaCabeceraTO = getInvVentaCabeceraTO(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
            long diferenciaMiliSegundos = UtilsDate.fechaFormatoDate(venta.getVtaFechaVencimiento(), "yyyy-MM-dd").getTime() - UtilsDate.fechaFormatoDate(ventasTO.getVtaFecha(), "yyyy-MM-dd").getTime();
            long diasCredito = diferenciaMiliSegundos / (1000 * 60 * 60 * 24);
            InvClienteTO clienteTO = clienteDao.obtenerClienteTO(empresa, ventasTO.getCliCodigo());
            InvFunListadoClientesTO cliente = new InvFunListadoClientesTO();
            cliente.setCliId(clienteTO.getCliId());
            cliente.setCliCodigo(clienteTO.getCliCodigo());
            cliente.setCliRazonSocial(clienteTO.getCliRazonSocial());
            cliente.setCliPrecio(clienteTO.getCliPrecio() != null ? clienteTO.getCliPrecio().intValue() : 0);
            cliente.setCliDireccion(clienteTO.getCliDireccion());
            cliente.setCliCiudad(clienteTO.getCliCiudad());
            if (ventasTO.getCliCodigoEstablecimiento() != null && !ventasTO.getCliCodigoEstablecimiento().equals("") && !clienteTO.getCliCodigoEstablecimiento().equals(ventasTO.getCliCodigoEstablecimiento())) {
                InvClientesDirecciones direccion = clientesDireccionesDao.obtenerDireccion(empresa, ventasTO.getCliCodigoEstablecimiento(), ventasTO.getCliCodigo());
                if (direccion != null) {
                    cliente.setCliDireccion(direccion.getDirDetalle());
                }
            }
            if (clienteTO.getCliDiasCredito() != null) {
                int diasCreditoEntero = Integer.parseInt(clienteTO.getCliDiasCredito() + "");
                cliente.setCliDiasCredito(diasCreditoEntero);
            }
            //liquidacion ventas
            List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTO = new ArrayList<>();
            List<InvVentasLiquidacion> listaInvVentasLiquidacion = ventasLiquidacionDao.getListInvVentasLiquidacion(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
            if (listaInvVentasLiquidacion != null && listaInvVentasLiquidacion.size() > 0) {
                for (int i = 0; i < listaInvVentasLiquidacion.size(); i++) {
                    InvVentasLiquidacionTO item = new InvVentasLiquidacionTO();
                    item.setDetSecuencia(listaInvVentasLiquidacion.get(i).getDetSecuencial());
                    item.setVtaEmpresa(listaInvVentasLiquidacion.get(i).getInvVentas().getInvVentasPK().getVtaEmpresa());
                    item.setVtaPeriodo(listaInvVentasLiquidacion.get(i).getInvVentas().getInvVentasPK().getVtaPeriodo());
                    item.setVtaMotivo(listaInvVentasLiquidacion.get(i).getInvVentas().getInvVentasPK().getVtaMotivo());
                    item.setVtaNumero(listaInvVentasLiquidacion.get(i).getInvVentas().getInvVentasPK().getVtaNumero());
                    item.setLiqEmpresa(listaInvVentasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqEmpresa());
                    item.setLiqMotivo(listaInvVentasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqMotivo());
                    item.setLiqNumero(listaInvVentasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqNumero());
                    item.setLiqTotal(listaInvVentasLiquidacion.get(i).getLiqTotal());
                    item.setLiqFecha(UtilsValidacion.fecha(listaInvVentasLiquidacion.get(i).getPrdLiquidacion().getLiqFecha(), "yyyy-MM-dd"));
                    listaInvVentasLiquidacionTO.add(item);
                }
            }
            List<InvVentasDetalle> listaDetalle = ventasDao.obtenerVentaDetallePorNumero(venta.getInvVentasPK().getVtaEmpresa(), venta.getInvVentasPK().getVtaPeriodo(), venta.getInvVentasPK().getVtaMotivo(), venta.getInvVentasPK().getVtaNumero());
            List<InvVentasDetalleTO> listaInvVentasDetalleTO = new ArrayList<>();
            for (InvVentasDetalle detalle : listaDetalle) {
                List<InvVentasDetalleSeries> listaSerie = ventasDetalleSeriesDao.listarSeriesPorSecuencialDetalle(detalle.getDetSecuencial());
                if (listaSerie != null && !listaDetalle.isEmpty()) {
                    detalle.setInvVentasDetalleSeriesList(listaSerie);
                } else {
                    detalle.setInvVentasDetalleSeriesList(new ArrayList<>());
                }
                InvVentasDetalleTO detalleTO = ConversionesInventario.convertirInvVentasDetalle_InvVentasDetalleTO(detalle);
                sectorSeleccionado = detalleTO.getSecCodigo();
                if (listaInvVentasLiquidacionTO.size() > 0) {
                    detalleTO.setSectorLiq(detalleTO.getPisSector());
                    detalleTO.setPisNumeroLiq(detalleTO.getPisNumero());
                }
                detalleTO.setInvVentasDetalleComplementarioList(new ArrayList<>());
                //BUSCAR FORMULA PRODUCTO
                for (InvVentasDetalle prod : listaDetalle) {
                    if (!Objects.equals(prod.getDetSecuencial(), detalle.getDetSecuencial())) {
                        if (Objects.equals(detalle.getDetSecuencial(), prod.getProComplementario())) {
                            prod.setInvVentasDetalleSeriesList(detalle.getInvVentasDetalleSeriesList());
                            InvVentasDetalleTO detalleComplTO = ConversionesInventario.convertirInvVentasDetalle_InvVentasDetalleTO(prod);
                            BigDecimal cantidadInicial = detalleComplTO.getDetCantidad().divide(detalleTO.getDetCantidad(), 6, RoundingMode.HALF_UP);
                            detalleComplTO.setDetCantidad(cantidadInicial);
                            detalleTO.getInvVentasDetalleComplementarioList().add(detalleComplTO);
                        }
                    }

                }
                //*****************************
                if (detalleTO.getProComplementario() == null) {
                    listaInvVentasDetalleTO.add(detalleTO);
                }
            }
            String estadoComprobanteAutorizacionElectronica = ventaElectronicaDao.comprobarAnxVentaElectronicaAutorizacion(empresa, venta.getInvVentasPK().getVtaPeriodo(), venta.getInvVentasPK().getVtaMotivo(), venta.getInvVentasPK().getVtaNumero());
            boolean estaAutorizada = estadoComprobanteAutorizacionElectronica != null && estadoComprobanteAutorizacionElectronica.equals("AUTORIZADO");
            AnxVentaTO anxVentaTO = ventaDao.getAnexoVentaTO(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
            AnxVentaElectronica anxVentaElectronica = ventaDao.getAnexoVentaElectronicaTO(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());

            /*comprobante electronico*/
            boolean puedeAnular = true;
            if (anxVentaElectronica != null && quiereAnular) {
                if (anxVentaElectronica.getEAutorizacionNumero() != null && anxVentaElectronica.getEAutorizacionNumero().length() == 49) {
                    try {
                        RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(anxVentaElectronica.getEAutorizacionNumero(), TipoAmbienteEnum.PRODUCCION.getCode(), sisInfoTO);
                        if (respuestaComprobante != null && respuestaComprobante.getAutorizaciones() != null
                                && respuestaComprobante.getAutorizaciones().getAutorizacion() != null && !respuestaComprobante.getAutorizaciones().getAutorizacion().isEmpty()) {
                            puedeAnular = false;
                        }
                    } catch (Exception e) {
                        campos.put("mensajeAnulacion", "Ocurrió un error al consultar el estado del documento en el SRI. Puede ser que este documento no se encuentre anulado en el SRI");
                        puedeAnular = false;
                    }
                }
            }

            InvVentasComplementoTO invVentasComplemento = ventasComplementoDao.buscarVentasComplementoTO(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
            if (sectorSeleccionado != null) {
                List<PrdListaPiscinaTO> listadoPiscinas = piscinaDao.getListaPiscina(empresa, sectorSeleccionado);
                campos.put("listadoPiscinas", listadoPiscinas);
            }
            SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(UtilsDate.fechaFormatoDate(venta.getVtaFecha()), empresa);
            AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(empresa, numeroDocumento, tipoDocumento, ventasTO.getVtaFecha());
            //Retencion
            InvVentaRetencionesTO invVentaRetencionesTO = getInvVentaRetencionesTO(empresa, numeroDocumento);
            if (invVentaRetencionesTO != null && invVentaRetencionesTO.getVenMotivo().equals(venta.getInvVentasPK().getVtaMotivo())
                    && invVentaRetencionesTO.getVenPeriodo().equals(venta.getInvVentasPK().getVtaPeriodo())
                    && invVentaRetencionesTO.getVenNumero().equals(venta.getInvVentasPK().getVtaNumero())) {
            } else {
                invVentaRetencionesTO = null;
            }

            //Exportacion
            AnxVentaExportacion exportacion = exportacionesService.obtenerAnxVentaExportacion(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
            AnxVentaExportacionTO exportacionTO = null;
            if (exportacion != null) {
                exportacionTO = new AnxVentaExportacionTO();
                String fecha = UtilsValidacion.fecha(exportacion.getExpFechaExportacion(), "yyyy-MM-dd");
                exportacionTO.setExpFechaExportacion(fecha);
                exportacionTO.setExpImpuestoPagadoExterior(exportacion.getExpImpuestoPagadoExterior());
                exportacionTO.setExpObservaciones(exportacion.getExpObservaciones());
                exportacionTO.setExpPaisEfectuaExportacion(exportacion.getExpPaisEfectuaExportacion());
                exportacionTO.setExpParaisoFiscal(exportacion.getExpParaisoFiscal());
                exportacionTO.setExpRefrendoAnio(exportacion.getExpRefrendoAnio());
                exportacionTO.setExpRefrendoCorrelativo(exportacion.getExpRefrendoCorrelativo());
                exportacionTO.setExpRefrendoDistrito(exportacion.getExpRefrendoDistrito());
                exportacionTO.setExpRefrendoDocumentoTransporte(exportacion.getExpRefrendoDocumentoTransporte());
                exportacionTO.setExpRefrendoRegimen(exportacion.getExpRefrendoRegimen());
                exportacionTO.setExpRegimenFiscalPreferente(exportacion.getExpRegimenFiscalPreferente());
                exportacionTO.setExpRegimenGeneral(exportacion.getExpRegimenGeneral());
                exportacionTO.setExpSecuencial(exportacion.getExpSecuencial());
                exportacionTO.setExpTipoExportacion(exportacion.getExpTipoExportacion());
                exportacionTO.setExpTipoIngresoExterior(exportacion.getExpTipoIngresoExterior());
                exportacionTO.setExpTipoRegimenFiscal(exportacion.getExpTipoRegimenFiscal());
                exportacionTO.setExpValorFobExterior(exportacion.getExpValorFobExterior());
                exportacionTO.setExpValorFobLocal(exportacion.getExpValorFobLocal());
                exportacionTO.setVtaEmpresa(exportacion.getVtaEmpresa());
                exportacionTO.setVtaPeriodo(exportacion.getVtaPeriodo());
                exportacionTO.setVtaMotivo(exportacion.getVtaMotivo());
                exportacionTO.setVtaNumero(exportacion.getVtaNumero());
                exportacionTO.setExpNumeroFue(exportacion.getExpNumeroFue());
                exportacionTO.setExpVerificador(exportacion.getExpVerificador());
                exportacionTO.setExpFactura(exportacion.getExpFactura());
                exportacionTO.setExpLugar(exportacion.getExpLugar());
                exportacionTO.setExpPuertoEmbarque(exportacion.getExpPuertoEmbarque());
                exportacionTO.setExpPuertoDestino(exportacion.getExpPuertoDestino());
                exportacionTO.setExpPaisDestino(exportacion.getExpPaisDestino());
                exportacionTO.setExpFlete(exportacion.getExpFlete());
                exportacionTO.setExpSeguro(exportacion.getExpSeguro());
                exportacionTO.setExpGastosAduaneros(exportacion.getExpGastosAduaneros());
                exportacionTO.setExpTransporteOtros(exportacion.getExpTransporteOtros());
                boolean fueGravado = false;
                if (exportacion.getExpImpuestoPagadoExterior() != null && exportacion.getExpImpuestoPagadoExterior().compareTo(BigDecimal.ZERO) != 0) {
                    fueGravado = true;
                }
                exportacionTO.setExpIngresoExteriorFueGravado(fueGravado);
            }
            //reembolso
            if (venta != null && venta.getInvVentasPK() != null) {
                List<AnxVentaReembolsoTO> listaAnxVentaReembolsoTO = anxVentaReembolsoService.getListaAnxVentaReembolsoTO(
                        venta.getInvVentasPK().getVtaEmpresa(),
                        venta.getInvVentasPK().getVtaPeriodo(),
                        venta.getInvVentasPK().getVtaMotivo(),
                        venta.getInvVentasPK().getVtaNumero());
                if (listaAnxVentaReembolsoTO != null && listaAnxVentaReembolsoTO.size() > 0) {
                    for (AnxVentaReembolsoTO item : listaAnxVentaReembolsoTO) {
                        item.setProvCodigoCopia(item.getProvCodigo());
                        item.setReembFechaemision(UtilsValidacion.fecha(item.getReembFechaemision(), "yyyy-MM-dd", "dd-MM-yyyy"));
                    }
                }
                campos.put("listaAnxVentaReembolsoTO", listaAnxVentaReembolsoTO);
            }

            campos.put("exportacionTO", exportacionTO);
            campos.put("ventasTO", ventasTO);
            campos.put("puedeAnular", puedeAnular);
            campos.put("invVentaCabeceraTO", invVentaCabeceraTO);
            campos.put("invVentaRetencionesTO", invVentaRetencionesTO);
            campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
            campos.put("diasCredito", diasCredito);
            campos.put("cliente", cliente);
            campos.put("listaInvVentasDetalleTO", listaInvVentasDetalleTO);
            campos.put("anxVentaTO", anxVentaTO);
            campos.put("invVentasComplemento", invVentasComplemento);
            campos.put("anxNumeracionLineaTO", anxNumeracionLineaTO);
            campos.put("estaAutorizada", estaAutorizada);
            campos.put("listaInvVentasLiquidacionTO", listaInvVentasLiquidacionTO);
            return campos;
        } else {
            throw new GeneralException("No se encontraron resultados.");
        }
    }

    public Autorizacion obtenerComprobanteAutorizado(RespuestaComprobante respuestaComprobante) {
        for (Autorizacion autorizacion : respuestaComprobante.getAutorizaciones().getAutorizacion()) {
            if (autorizacion.getEstado().equalsIgnoreCase("AUTORIZADO")) {
                return autorizacion;
            }
        }
        return null;
    }

    @Override
    public String desmayorizarVenta(InvVentasTO invVentasTO, AnxVentaTO anxVentaTO,
            SisInfoTO sisInfoTO) throws GeneralException, Exception {
        List<InvListadoCobrosTO> listadoCobrosTOs = new ArrayList();
        String errores = "";
        if (invVentasTO != null && invVentasTO.getVtaPendiente()) {
            listadoCobrosTOs = ventasDao.invListadoCobrosTO(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
        }
        if (listadoCobrosTOs != null && listadoCobrosTOs.isEmpty()) {
            if (anxVentaTO != null) {
                ////// MANDA A GUARDAR
                MensajeTO mensajeTO = modificarInvVentasTO(invVentasTO, null, null, true, anxVentaTO, false, "", "", null, sisInfoTO);
                String mensaje = mensajeTO.getMensaje();
                if (mensaje.charAt(0) == 'T') {
                    return mensajeTO.getMensaje().substring(1);
                } else {
                    for (String item : mensajeTO.getListaErrores1()) {
                        errores = errores + "-" + item + "\n";
                    }
                    throw new GeneralException("La venta no fue desmayorizada.\n" + errores);
                }
            } else {
                ////// MANDA A GUARDAR
                MensajeTO mensajeTO = modificarInvVentasTO(invVentasTO, null, null, true, anxVentaTO, false, "", "", null, sisInfoTO);
                String mensaje = mensajeTO.getMensaje();
                if (mensaje.charAt(0) == 'T') {
                    return mensajeTO.getMensaje().substring(1);
                } else {
                    for (String item : mensajeTO.getListaErrores1()) {
                        errores = errores + "-" + item + "\n";
                    }
                    throw new GeneralException(mensajeTO.getMensaje().substring(1) + "\n" + errores);
                }
            }
        } else {
            List<String> lista = new ArrayList();
            for (int i = 0; i < listadoCobrosTOs.size(); i++) {
                lista.add(listadoCobrosTOs.get(i).toString());
            }
            if (anxVentaTO != null) {
                if ((anxVentaTO.getVenBase0()
                        .compareTo(UtilsValidacion.redondeoDecimalBigDecimal(invVentasTO.getVtaBase0(), 2, RoundingMode.HALF_UP)) == 0)
                        && (anxVentaTO.getVenBaseImponible().compareTo(UtilsValidacion.redondeoDecimalBigDecimal(invVentasTO.getVtaSubtotalBaseImponible(), 2, RoundingMode.HALF_UP)) == 0)) {
                    ////// MANDA A GUARDAR
                    MensajeTO mensajeTO = modificarInvVentasTO(invVentasTO, null, null, true, anxVentaTO, false, "", "", null, sisInfoTO);
                    String mensaje = mensajeTO.getMensaje();
                    if (mensaje.charAt(0) == 'T') {
                        return mensajeTO.getMensaje().substring(1);
                    } else {
                        for (String item : mensajeTO.getListaErrores1()) {
                            errores = errores + "-" + item + "\n";
                        }
                        throw new GeneralException("La venta no fue desmayorizada.\n" + mensajeTO.getMensaje().substring(1) + "\n" + errores);
                    }
                } else {
                    throw new GeneralException("La venta no fue desmayorizada debido a que los valores de retención han cambiado\n\nIntentelo de nuevo o contacte al administrador");
                }
            } else {
                ////// MANDA A GUARDAR
                MensajeTO mensajeTO = modificarInvVentasTO(invVentasTO, null, null, true, anxVentaTO, false, "", "", null, sisInfoTO);
                String mensaje = mensajeTO.getMensaje();
                if (mensaje.charAt(0) == 'T') {
                    return mensajeTO.getMensaje().substring(1);
                } else {
                    for (String item : mensajeTO.getListaErrores1()) {
                        errores = errores + "-" + item + "\n";
                    }
                    throw new GeneralException("La venta no fue desmayorizada. Intentelo de nuevo o contacte al administrador\n" + mensajeTO.getMensaje().substring(1) + "\n" + errores);
                }
            }
        }
    }

    @Override
    public InvEntidadTransaccionTO obtenerClienteDeVenta(String empresa, String periodo, String motivo, String numero) throws Exception {
        InvVentas venta = ventasDao.buscarInvVentas(empresa, periodo, motivo, numero);
        if (venta != null) {
            InvEntidadTransaccionTO entidadTransaccion = new InvEntidadTransaccionTO();
            entidadTransaccion.setDocumento(venta.getVtaDocumentoNumero());
            entidadTransaccion.setTipo("Factura");
            entidadTransaccion.setRazonSocial(venta.getInvCliente().getCliRazonSocial());
            entidadTransaccion.setIdentificacion(venta.getInvCliente().getCliIdNumero());
            return entidadTransaccion;
        }
        return null;
    }

    /*Insertar Venta con complementos*/
    @Override
    public MensajeTO insertarInventarioVentasTO(InvVentasTO invVentasTO, List<InvVentasDetalleTO> listaInvVentasDetalleTO,
            AnxVentaTO anxVentasTO,
            String tipoDocumentoComplemento,
            String numeroDocumentoComplemento, String motivoDocumentoComplemento,
            Boolean isComprobanteElectronica,
            InvVentaGuiaRemision invVentaGuiaRemision,
            List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTO,
            AnxVentaExportacionTO anxVentaExportacionTO,
            List<InvVentasDatosAdjuntos> listadoImagenes,
            List<AnxVentaReembolsoTO> listAnxVentaReembolsoTO,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>();
        boolean periodoCerrado = false;
        boolean continuar = false;
        boolean isVentaConsignacion = invVentasTO.isVtaConsignacion();
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, invVentasTO.getVtaEmpresa());
        if (!sisEmpresaParametros.getParActividad().trim().equals("COMISARIATO")) {
            if (!invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaDocumentoNumero(), invVentasTO.getVtaDocumentoTipo(), ("'" + invVentasTO.getVtaFecha() + "'"));
                if (anxNumeracionLineaTO != null) {
                    if (!anxNumeracionLineaTO.getNumeroAutorizacion().trim().equals("ANULADO")) {
                        continuar = true;
                    }
                } else {
                    continuar = true;
                }
            } else {
                continuar = true;
            }
        } else {
            continuar = true;
        }
        //solo puede ser tipo == 00 && isVentaConsignacion, los demas son !isVentaConsignacion
        if (continuar) {
            List<CarListaCobrosTO> carListaPagosCobrosTOs = new ArrayList<CarListaCobrosTO>();
            List<CarListaCobrosTO> carListaPagosCobrosVencidosTOs = new ArrayList<CarListaCobrosTO>();
            if (invVentasTO.getVtaFormaPago().equals("POR PAGAR") && !invVentasTO.getVtaDocumentoTipo().equals("04") && !isVentaConsignacion) {
                carListaPagosCobrosTOs = cobrosDao.getCarListaCobrosVentasTO(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo());
                if (!carListaPagosCobrosTOs.isEmpty()) {
                    for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                        if (UtilsValidacion.fecha(carListaPagosCobrosTOs.get(i).getCxccFechaVencimiento(), "yyyy-MM-dd").getTime() <= new Date().getTime()) {
                            carListaPagosCobrosVencidosTOs.add(carListaPagosCobrosTOs.get(i));
                        }
                    }
                }
            }

            if (carListaPagosCobrosVencidosTOs.isEmpty() || !invVentasTO.getVtaEmpresa().trim().equals("AA")) {
                ///// BUSCANDO CLIENTE
                InvCliente invCliente = clienteDao.buscarInvCliente(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo());
                if (invCliente != null) {
                    boolean puedeContinuar = true;
                    if (invVentasTO.getVtaFormaPago().equals("POR PAGAR")) {
                        if (UtilsValidacion.numeroDias("yyyy-MM-dd", invVentasTO.getVtaFecha(), invVentasTO.getVtaFechaVencimiento()) <= invCliente.getCliDiasCredito()) {
                            puedeContinuar = true;
                        } else {
                            puedeContinuar = false;
                        }
                    }
                    if (puedeContinuar) {
                        puedeContinuar = true;
                        BigDecimal saldoTotalVentaCobros = cero;
                        if (invVentasTO.getVtaFormaPago().equals("POR PAGAR")) {
                            BigDecimal saldoTotalCobros = cero;
                            for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                                saldoTotalCobros = saldoTotalCobros.add(carListaPagosCobrosTOs.get(i).getCxccSaldo());
                            }
                            saldoTotalVentaCobros = saldoTotalCobros.add(invVentasTO.getVtaTotal());
                            if (sisEmpresaParametros.getParActividad().equals("COMERCIAL") || sisEmpresaParametros.getParActividad().trim().equals("COMISARIATO")) {
                                if (!invVentasTO.getVtaDocumentoTipo().equals("04")) {
                                    if (saldoTotalVentaCobros.compareTo(invCliente.getCliCupoCredito()) <= 0) {
                                        puedeContinuar = true;
                                    } else {
                                        puedeContinuar = false;
                                    }
                                } else {
                                    puedeContinuar = true;
                                }
                            } else {
                                puedeContinuar = true;
                            }
                        }
                        if (puedeContinuar) {//
                            comprobar = false;
                            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
                            listaSisPeriodoTO = periodoService.getListaPeriodoTO(invVentasTO.getVtaEmpresa());
                            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                                if (UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                                        && UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                                    comprobar = true;
                                    invVentasTO.setVtaPeriodo(sisListaPeriodoTO.getPerCodigo());
                                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                                }
                            }

                            if (comprobar) {
                                if (!periodoCerrado) {
                                    if (ventasMotivoDao.comprobarInvVentasMotivo(invVentasTO.getVtaEmpresa(),
                                            invVentasTO.getVtaMotivo())) {
                                        /// PREPARANDO OBJETO SISSUCESO (susClave y susDetalle se llenan en DAOTransaccion)
                                        susSuceso = "INSERT";
                                        susTabla = "inventario.inv_ventas";
                                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                        invVentasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                                        ////// CREANDO VENTAS
                                        InvVentas invVentas = ConversionesInventario.convertirInvVentasTO_InvVentas(invVentasTO);
                                        invVentas.setInvCliente(clienteDao.buscarInvCliente(invVentasTO.getCliEmpresa(), invVentasTO.getCliCodigo()));
                                        if (invVentas.getConNumero() == null || invVentas.getConPeriodo() == null || invVentas.getConTipo() == null) {
                                            invVentas.setConEmpresa(null);
                                            invVentas.setConNumero(null);
                                            invVentas.setConPeriodo(null);
                                            invVentas.setConTipo(null);
                                        }

                                        ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                                        List<InvVentasDetalle> listInvVentasDetalle = new ArrayList<InvVentasDetalle>();
                                        InvVentasDetalle invVentasDetalle = null;

                                        int estadoDetalle = 0;
                                        for (InvVentasDetalleTO invVentasDetalleTO : listaInvVentasDetalleTO) {
                                            invVentasDetalle = new InvVentasDetalle();
                                            invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
                                            invVentasDetalleTO.setDetPendiente(invVentasTO.getVtaPendiente());
                                            invVentasDetalle = ConversionesInventario.convertirInvVentasDetalleTO_InvVentasDetalle(invVentasDetalleTO);
                                            ///// BUSCANDO EL PRODUCTO EN DETALLE
                                            InvProducto invProducto = productoDao.buscarInvProducto(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getProCodigoPrincipal());
                                            if (invProducto != null) {
                                                invVentasDetalle.setInvProducto(invProducto);
                                                invVentasDetalle.setProCreditoTributario(invProducto.getProCreditoTributario());
                                                ////// BUSCANDO LA BODEGA EN EL DETALLE
                                                InvBodega invBodega = bodegaDao.buscarInvBodega(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getBodCodigo());
                                                if (invBodega != null) {
                                                    invVentasDetalle.setInvBodega(invBodega);
                                                    listInvVentasDetalle.add(invVentasDetalle);
                                                } else {
                                                    estadoDetalle = 2;
                                                }
                                            } else {
                                                estadoDetalle = 1;
                                            }

                                            if (estadoDetalle == 1 || estadoDetalle == 2) {
                                                break;
                                            } else {
                                                invProducto = null;
                                            }
                                        }
                                        if (estadoDetalle == 0) {
                                            AnxVenta anxVenta = null;
                                            if (anxVentasTO != null) {
                                                anxVentasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                                                anxVenta = ConversionesAnexos.convertirAnxVentaTO_AnxVenta(anxVentasTO);
                                            }
                                            //////////// COMPROBAR SI NO EXISTE NUMERO DE FACTURA Y SI
                                            //////////// ES EMPRESA COMERCIAL YA NO SE PREGUNTA ESO
                                            String codigoFactura = ventasDao.getConteoNumeroFacturaVenta(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaDocumentoTipo(), invVentasTO.getVtaDocumentoNumero());

                                            if (codigoFactura.isEmpty() || (invVentasTO.getVtaDocumentoNumero() != null && invVentasTO.getVtaDocumentoNumero().equals("999-999-999999999")) || invVentasTO.getVtaDocumentoTipo().equals("00")) {
                                                boolean noExiste = false;
                                                //////////// COMPROBAR SI EL TIPO DE DOCUMENTO ES 00
                                                if (anxVentasTO != null) {
                                                    //////////// COMPROBAR SI NO EXISTE
                                                    //////////// NUMERO DE RETENCION
                                                    String codigoRetencion = "";
                                                    anxVentasTO.setVenRetencionNumero(anxVentasTO.getVenRetencionNumero() == null ? "" : anxVentasTO.getVenRetencionNumero());
                                                    if (!anxVentasTO.getVenRetencionNumero().isEmpty()) {
                                                        codigoRetencion = ventaDao.getConteoNumeroRetencionVenta(anxVentasTO.getVenEmpresa(), anxVentasTO.getVenRetencionNumero(), invCliente.getInvClientePK().getCliCodigo());
                                                    } else {
                                                        codigoRetencion = "";
                                                    }
                                                    if (codigoRetencion.trim().isEmpty()) {
                                                        noExiste = true;
                                                    }
                                                } else {
                                                    noExiste = true;
                                                }

                                                if (noExiste) {
                                                    if (!invVentas.getVtaPendiente() && !invVentasTO.getVtaDocumentoTipo().equals("04")) {
                                                        mensajeClase = productoSaldosService.verificarStockVentas(listInvVentasDetalle);
                                                    }
                                                    if (mensajeClase.isEmpty()) {
                                                        InvVentasComplemento invVentasComplemento = null;
                                                        puedeContinuar = true;//false
                                                        if ((invVentas.getVtaDocumentoTipo().equals("04") || invVentas.getVtaDocumentoTipo().equals("05")) && (numeroDocumentoComplemento != null
                                                                && tipoDocumentoComplemento != null && !numeroDocumentoComplemento.trim().isEmpty() && !tipoDocumentoComplemento.trim().isEmpty())) {
                                                            invVentasComplemento = new InvVentasComplemento();
                                                            invVentasComplemento.setComDocumentoNumero(numeroDocumentoComplemento);
                                                            invVentasComplemento.setComDocumentoTipo(tipoDocumentoComplemento);
                                                            invVentasComplemento.setComDocumentoMotivo(motivoDocumentoComplemento);
                                                            List<String> rucsComNet = Arrays.asList("0791755093001", "0791702070001", "0791702054001", "0791723396001");
                                                            boolean esComnet = rucsComNet.contains(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
                                                            codigoFactura = ventasDao.getConteoNotaCreditoVenta(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo(), tipoDocumentoComplemento, numeroDocumentoComplemento);
                                                            if (!codigoFactura.trim().isEmpty() || (invVentasTO.getVtaDocumentoNumero() != null && invVentasTO.getVtaDocumentoNumero().equals("999-999-999999999"))) {
                                                                puedeContinuar = true;
                                                            }
                                                            if (esComnet) {
                                                                puedeContinuar = true;
                                                            }
                                                        } else {
                                                            puedeContinuar = true;
                                                        }
                                                        if (puedeContinuar) {
                                                            AnxVentaElectronicaTO anxVentaElectronicaTO = new AnxVentaElectronicaTO();
                                                            AnxVentaElectronica anxVentaElectronica = null;
                                                            if (isComprobanteElectronica != null && isComprobanteElectronica) {
                                                                File file = new File("auxxml.xml");
                                                                byte[] buffer = new byte[(int) file.length()];
                                                                // ************************
                                                                // clave primaria
                                                                // ************************
                                                                anxVentaElectronicaTO.seteSecuencial(0);
                                                                anxVentaElectronicaTO.seteTipoAmbiente("");
                                                                anxVentaElectronicaTO.seteClaveAcceso("");
                                                                anxVentaElectronicaTO.seteEstado("PENDIENTE");
                                                                anxVentaElectronicaTO.seteAutorizacionNumero("");
                                                                anxVentaElectronicaTO.seteAutorizacionFecha(UtilsValidacion.fechaSistema());
                                                                anxVentaElectronicaTO.seteXml(buffer);
                                                                anxVentaElectronicaTO.seteEnviadoPorCorreo(false);
                                                                anxVentaElectronicaTO.setVtaEmpresa("");
                                                                anxVentaElectronicaTO.setVtaPeriodo("");
                                                                anxVentaElectronicaTO.setVtaMotivo("");
                                                                anxVentaElectronicaTO.setVtaNumero("");
                                                                anxVentaElectronicaTO.setUsrEmpresa(sisInfoTO.getEmpresa());
                                                                anxVentaElectronicaTO.setUsrCodigo(invVentasTO.getUsrCodigo());
                                                                anxVentaElectronicaTO.setUsrFechaInserta(UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "dd-MM-yyyy", "yyyy-MM-dd"));
                                                                anxVentaElectronicaTO.setVtaFecha(UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "dd-MM-yyyy", "yyyy-MM-dd"));
                                                                anxVentaElectronica = ConversionesAnexos.convertirAnxVentaElectronicaTO_AnxVentaElectronica(anxVentaElectronicaTO);
                                                            }
                                                            if (invVentas.getVtaDocumentoTipo().equals("00") || (invVentasTO.getVtaDocumentoNumero() != null && invVentas.getVtaDocumentoNumero().equals("999-999-999999999") && invVentas.getVtaDocumentoTipo().equals("04"))
                                                                    || (invVentasTO.getVtaDocumentoNumero() != null && invVentas.getVtaDocumentoNumero().equals("999-999-999999999") && invVentas.getVtaDocumentoTipo().equals("05"))) {
                                                                invVentas.setVtaDocumentoNumero(null);
                                                            }
                                                            //GUIA REMISION
                                                            if (invVentaGuiaRemision != null) {
                                                                invVentaGuiaRemision.setUsrCodigo(invVentasTO.getUsrCodigo());
                                                                invVentaGuiaRemision.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                                                            }

                                                            //LIQUIDACION
                                                            List<InvVentasLiquidacion> listInvVentasLiquidacion = new ArrayList<>();
                                                            if (listaInvVentasLiquidacionTO != null) {
                                                                for (int i = 0; i < listaInvVentasLiquidacionTO.size(); i++) {
                                                                    InvVentasLiquidacion liq = ConversionesInventario.convertirInvVentasLiquidacionTO_InvVentasLiquidacion(listaInvVentasLiquidacionTO.get(i));
                                                                    listInvVentasLiquidacion.add(liq);
                                                                }
                                                            }
                                                            //REEMBOLSOS
                                                            List<AnxVentaReembolso> listAnxVentaReembolso = new ArrayList<>();
                                                            if (listAnxVentaReembolsoTO != null) {
                                                                for (int i = 0; i < listAnxVentaReembolsoTO.size(); i++) {
                                                                    AnxVentaReembolso reem = new AnxVentaReembolso();
                                                                    InvCompras invCompra = new InvCompras();
                                                                    invCompra.setInvComprasPK(new InvComprasPK(
                                                                            listAnxVentaReembolsoTO.get(i).getCompEmpresa(),
                                                                            listAnxVentaReembolsoTO.get(i).getCompPeriodo(),
                                                                            listAnxVentaReembolsoTO.get(i).getCompMotivo(),
                                                                            listAnxVentaReembolsoTO.get(i).getCompNumero()));
                                                                    reem.setInvCompra(invCompra);
                                                                    reem.setReembSecuencial(listAnxVentaReembolsoTO.get(i).getReembSecuencial());
                                                                    listAnxVentaReembolso.add(reem);
                                                                }
                                                            }

                                                            //VENTA EXPORTACION
                                                            AnxVentaExportacion anexo = null;
                                                            if (anxVentaExportacionTO != null) {
                                                                anexo = ConversionesAnexos.convertirAnxVentaExportacionTO_AnxVentaExportacion(anxVentaExportacionTO);
                                                            }

                                                            comprobar = ventasDao.insertarTransaccionInvVenta(invVentas, listInvVentasDetalle, sisSuceso, anxVenta, invVentasComplemento, anxVentaElectronica,
                                                                    invVentaGuiaRemision,
                                                                    listInvVentasLiquidacion,
                                                                    anexo,
                                                                    listAnxVentaReembolso);
                                                            if (comprobar) {
                                                                SisPeriodo sisPeriodo = periodoService.buscarPeriodo(invVentasTO.getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo());
                                                                retorno = "T<html>Se ingresó la venta con la siguiente información:<br><br>"
                                                                        + "Periodo: <font size = 5>"
                                                                        + sisPeriodo.getPerDetalle()
                                                                        + "</font>.<br> Motivo: <font size = 5>"
                                                                        + invVentas.getInvVentasPK().getVtaMotivo()
                                                                        + "</font>.<br> Número: <font size = 5>"
                                                                        + invVentas.getInvVentasPK().getVtaNumero()
                                                                        + "</font>.</html>"
                                                                        + invVentas.getInvVentasPK().getVtaNumero()
                                                                        + "," + sisPeriodo.getSisPeriodoPK().getPerCodigo();

                                                                invVentasTO.setVtaNumero(invVentas.getInvVentasPK().getVtaNumero());
                                                                Map<String, Object> campos = new HashMap<>();
                                                                campos.put("invVentasTO", invVentasTO);
                                                                mensajeTO.setMap(campos);
                                                                if (!carListaPagosCobrosVencidosTOs.isEmpty()) {
                                                                    mensajeClase.add("<html>Cliente tiene deudas vencidas! Avise al cliente que\nse acerque a cancelar lo más pronto...</html>");
                                                                    mensajeClase.add("Número de Sistema\tValor\tFecha de Vencimiento");
                                                                    for (int i = 0; i < carListaPagosCobrosVencidosTOs.size(); i++) {
                                                                        mensajeClase.add(carListaPagosCobrosVencidosTOs.get(i).getCxccPeriodo()
                                                                                + " | "
                                                                                + carListaPagosCobrosVencidosTOs.get(i).getCxccMotivo()
                                                                                + " | "
                                                                                + carListaPagosCobrosVencidosTOs.get(i).getCxccNumero()
                                                                                + "\t"
                                                                                + carListaPagosCobrosVencidosTOs.get(i).getCxccSaldo()
                                                                                + "\t"
                                                                                + carListaPagosCobrosVencidosTOs.get(i).getCxccFechaVencimiento());
                                                                    }
                                                                    mensajeTO.setListaErrores1(mensajeClase);
                                                                }
                                                                //Imagenes
                                                                ventasDao.actualizarImagenesVenta(listadoImagenes, invVentas.getInvVentasPK(), new ArrayList<>());
                                                            } else {
                                                                retorno = "F<html>Hubo un error al guardar la Venta...\nIntente de nuevo o contacte con el administrador</html>";
                                                            }
                                                        } else {
                                                            retorno = "F<html>El Número de Documento del Complemento que ingresó no existe...\nIntente de nuevo o contacte con el administrador</html>";
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
                                        } else if (estadoDetalle == 1) {
                                            retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                        } else {
                                            retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                        }
                                    } else {
                                        retorno = "F<html>No se encuentra el motivo...</html>";
                                    }
                                } else {
                                    retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                                }
                            } else {
                                retorno = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
                            }
                        } else {
                            retorno = "F<html>Se superó el limite del monto del crédito...</html>";
                        }
                    } else {
                        retorno = "F<html>Los días de crédito del Cliente es superior al que se le permite...</html>";
                    }
                } else {
                    retorno = "F<html>El Cliente que escogió ya no está disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
                }
            } else {
                retorno = "F<html>Cliente tiene deudas vencidas! Avise al cliente que\nse acerque a cancelar lo más pronto...</html>";
                mensajeTO.setMensaje(retorno);
                mensajeClase.add("Número de Sistema\tValor\tFecha de Vencimiento");
                for (int i = 0; i < carListaPagosCobrosVencidosTOs.size(); i++) {
                    mensajeClase.add(carListaPagosCobrosVencidosTOs.get(i).getCxccPeriodo() + " | "
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccMotivo() + " | "
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccNumero() + "\t"
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccSaldo() + "\t"
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccFechaVencimiento());
                }
                mensajeTO.setListaErrores1(mensajeClase);
            }
        } else {
            retorno = "F<html>El NÚMERO DE DOCUMENTO que ingresó se encuentra ANULADO</html>";
        }

        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public MensajeTO modificarInventarioVentasTO(InvVentasTO invVentasTO, List<InvVentasDetalleTO> listaInvVentasDetalleTO,
            List<InvVentasDetalleTO> listaInvVentasEliminarDetalleTO, boolean desmayorizar, AnxVentaTO anxVentasTO,
            boolean quitarAnulado, String tipoDocumentoComplemento,
            String numeroDocumentoComplemento, String motivoDocumentoComplemento,
            InvVentasMotivoAnulacion invVentasMotivoAnulacion,
            InvVentaGuiaRemision invVentaGuiaRemision,
            List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTO,
            List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTOEliminar,
            AnxVentaExportacionTO anxVentaExportacionTO,
            List<InvVentasDatosAdjuntos> listadoImagenes,
            List<AnxVentaReembolsoTO> listAnxVentaReembolsoTO,
            List<AnxVentaReembolsoTO> listAnxVentaReembolsoEliminarTO,
            SisInfoTO sisInfoTO) throws Exception {

        boolean eliminarMotivoAnulacion = false;
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>();
        SisSuceso sisSuceso;
        String retorno = "";
        boolean periodoCerrado = false;
        boolean continuar = false;
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, invVentasTO.getVtaEmpresa());

        if (!sisEmpresaParametros.getParActividad().trim().equals("COMISARIATO")) {
            if (!invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaDocumentoNumero(), invVentasTO.getVtaDocumentoTipo(), ("'" + invVentasTO.getVtaFecha() + "'"));
                if (anxNumeracionLineaTO != null) {
                    if (!anxNumeracionLineaTO.getNumeroAutorizacion().trim().equals("ANULADO")) {
                        continuar = true;
                    }
                } else {
                    continuar = true;
                }
            } else {
                continuar = true;
            }
        } else {
            continuar = true;
        }
        if (continuar) {
            List<CarListaCobrosTO> carListaPagosCobrosTOs = new ArrayList<CarListaCobrosTO>();
            List<CarListaCobrosTO> carListaPagosCobrosVencidosTOs = new ArrayList<CarListaCobrosTO>();
            if (invVentasTO.getVtaFormaPago().equals("POR PAGAR") && !desmayorizar && !invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                carListaPagosCobrosTOs = cobrosDao.getCarListaCobrosVentasTO(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo());
                if (!carListaPagosCobrosTOs.isEmpty()) {
                    for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                        if (UtilsValidacion.fecha(carListaPagosCobrosTOs.get(i).getCxccFechaVencimiento(), "yyyy-MM-dd").getTime() <= new Date().getTime()) {
                            carListaPagosCobrosVencidosTOs.add(carListaPagosCobrosTOs.get(i));
                        }
                    }
                }
            }

            if (carListaPagosCobrosVencidosTOs.isEmpty() || !invVentasTO.getVtaEmpresa().trim().equals("AA")) {
                ///// BUSCANDO CLIENTE
                InvCliente invCliente = clienteDao.buscarInvCliente(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo());
                if (invCliente != null) {
                    String detalleError = "";
                    if (desmayorizar && listaInvVentasDetalleTO == null) {
                        List<InvListaDetalleVentasTO> invListaDetalleTO = ventasDetalleDao.getListaInvVentasDetalleTO(invVentasTO.getVtaEmpresa(),
                                invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
                        if (invListaDetalleTO != null) {
                            listaInvVentasDetalleTO = new ArrayList<InvVentasDetalleTO>();
                            for (int i = 0; i < invListaDetalleTO.size(); i++) {
                                InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();
                                invVentasDetalleTO.setDetSecuencia(invListaDetalleTO.get(i).getSecuencial());
                                invVentasDetalleTO.setDetOrden(i + 1);
                                invVentasDetalleTO.setDetPendiente(invListaDetalleTO.get(i).getPendiente());
                                invVentasDetalleTO.setDetCantidad(invListaDetalleTO.get(i).getCantidadProducto());
                                invVentasDetalleTO.setDetPrecio(invListaDetalleTO.get(i).getPrecioProducto());
                                invVentasDetalleTO.setDetPorcentajeRecargo(invListaDetalleTO.get(i).getPorcentajeRecargo());
                                invVentasDetalleTO.setDetPorcentajeDescuento(invListaDetalleTO.get(i).getPorcentajeDescuento());
                                invVentasDetalleTO.setBodEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setBodCodigo(invListaDetalleTO.get(i).getCodigoBodega());
                                invVentasDetalleTO.setProEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setProCodigoPrincipal(invListaDetalleTO.get(i).getCodigoProducto());
                                invVentasDetalleTO.setProNombre(invListaDetalleTO.get(i).getNombreProducto());
                                invVentasDetalleTO.setSecEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setSecCodigo(invListaDetalleTO.get(i).getCodigoCP());
                                invVentasDetalleTO.setPisEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setPisSector(invListaDetalleTO.get(i).getCodigoCP());
                                invVentasDetalleTO.setPisNumero(invListaDetalleTO.get(i).getCodigoCC());
                                invVentasDetalleTO.setVtaEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
                                invVentasDetalleTO.setVtaMotivo(invVentasTO.getVtaMotivo());
                                invVentasDetalleTO.setVtaNumero(invVentasTO.getVtaNumero());
                                invVentasDetalleTO.setProComplementario(invListaDetalleTO.get(i).getProComplementario());
                                invVentasDetalleTO.setDetObservaciones(invListaDetalleTO.get(i).getDetObservaciones());
                                invVentasDetalleTO.setDetEmpaque(invListaDetalleTO.get(i).getDetEmpaqueExportadora());
                                invVentasDetalleTO.setDetEmpaqueCantidad(invListaDetalleTO.get(i).getDetEmpaqueCantidad());
                                invVentasDetalleTO.setDetConversionPesoNeto(invListaDetalleTO.get(i).getDetConversionPesoNeto());
                                listaInvVentasDetalleTO.add(invVentasDetalleTO);
                            }
                        } else {
                            detalleError = "Hubo en error al recuperar el detalle de la VENTA.\nContacte con el administrador...";
                        }
                    }

                    if (quitarAnulado && listaInvVentasDetalleTO == null) {
                        List<InvListaDetalleVentasTO> invListaDetalleTO = ventasDetalleDao.getListaInvVentasDetalleTO(invVentasTO.getVtaEmpresa(),
                                invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
                        if (invListaDetalleTO != null) {
                            listaInvVentasDetalleTO = new ArrayList<>();
                            for (int i = 0; i < invListaDetalleTO.size(); i++) {
                                InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();
                                invVentasDetalleTO.setDetSecuencia(invListaDetalleTO.get(i).getSecuencial());
                                invVentasDetalleTO.setDetOrden(i + 1);
                                invVentasDetalleTO.setDetPendiente(invListaDetalleTO.get(i).getPendiente());
                                invVentasDetalleTO.setDetCantidad(invListaDetalleTO.get(i).getCantidadProducto());
                                invVentasDetalleTO.setDetPrecio(invListaDetalleTO.get(i).getPrecioProducto());
                                invVentasDetalleTO.setDetPorcentajeDescuento(invListaDetalleTO.get(i).getPorcentajeDescuento());
                                invVentasDetalleTO.setDetPorcentajeRecargo(invListaDetalleTO.get(i).getPorcentajeRecargo());
                                invVentasDetalleTO.setBodEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setBodCodigo(invListaDetalleTO.get(i).getCodigoBodega());
                                invVentasDetalleTO.setProEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setProCodigoPrincipal(invListaDetalleTO.get(i).getCodigoProducto());
                                invVentasDetalleTO.setProNombre(invListaDetalleTO.get(i).getNombreProducto());
                                invVentasDetalleTO.setSecEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setSecCodigo(invListaDetalleTO.get(i).getCodigoCP());
                                invVentasDetalleTO.setPisEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setPisSector(invListaDetalleTO.get(i).getCodigoCP());
                                invVentasDetalleTO.setPisNumero(invListaDetalleTO.get(i).getCodigoCC());
                                invVentasDetalleTO.setVtaEmpresa(invVentasTO.getVtaEmpresa());
                                invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
                                invVentasDetalleTO.setVtaMotivo(invVentasTO.getVtaMotivo());
                                invVentasDetalleTO.setVtaNumero(invVentasTO.getVtaNumero());
                                invVentasDetalleTO.setProComplementario(invListaDetalleTO.get(i).getProComplementario());
                                invVentasDetalleTO.setDetObservaciones(invListaDetalleTO.get(i).getDetObservaciones());
                                invVentasDetalleTO.setDetEmpaque(invListaDetalleTO.get(i).getDetEmpaqueExportadora());
                                invVentasDetalleTO.setDetEmpaqueCantidad(invListaDetalleTO.get(i).getDetEmpaqueCantidad());
                                invVentasDetalleTO.setDetConversionPesoNeto(invListaDetalleTO.get(i).getDetConversionPesoNeto());
                                listaInvVentasDetalleTO.add(invVentasDetalleTO);
                            }
                        } else {
                            detalleError = "Hubo en error al recuperar el detalle de la VENTA.\nContacte con el administrador...";
                        }
                    }

                    if (detalleError.trim().isEmpty()) {
                        boolean puedeContinuar = true;
                        if (invVentasTO.getVtaFormaPago().equals("POR PAGAR") && !desmayorizar && !invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                            if (UtilsValidacion.numeroDias("yyyy-MM-dd", invVentasTO.getVtaFecha(), invVentasTO.getVtaFechaVencimiento()) <= invCliente.getCliDiasCredito()) {
                                puedeContinuar = true;
                            } else {
                                puedeContinuar = false;
                            }
                        }
                        if (puedeContinuar) {
                            puedeContinuar = true;
                            if (invVentasTO.getVtaFormaPago().equals("POR PAGAR") && !desmayorizar && !invVentasTO.getVtaAnulado() && !invVentasTO.getVtaPendiente()) {
                                BigDecimal saldoTotalVentaCobros = cero;
                                BigDecimal saldoTotalCobros = cero;
                                for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                                    saldoTotalCobros = saldoTotalCobros.add(carListaPagosCobrosTOs.get(i).getCxccSaldo());
                                }
                                saldoTotalVentaCobros = saldoTotalCobros.add(invVentasTO.getVtaTotal());
                                if (invCliente.getCliCupoCredito() != null) {
                                    if (saldoTotalVentaCobros.compareTo(invCliente.getCliCupoCredito()) <= 0) {
                                        puedeContinuar = true;
                                    } else {
                                        puedeContinuar = false;
                                    }
                                } else {
                                    puedeContinuar = true;
                                }

                            }
                            if (puedeContinuar) {
                                comprobar = false;
                                List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(invVentasTO.getVtaEmpresa());
                                for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                                    if (UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                                            && UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                                        comprobar = true;
                                        invVentasTO.setVtaPeriodo(sisListaPeriodoTO.getPerCodigo());
                                        periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                                    }
                                }

                                if (comprobar) {
                                    if (!periodoCerrado) {
                                        List<String> lisCheques = contableService.validarChequesConciliados(sisInfoTO.getEmpresa(), invVentasTO.getConPeriodo(), invVentasTO.getConTipo(), invVentasTO.getConNumero());
                                        if (lisCheques != null && !lisCheques.isEmpty()) {
                                            mensajeTO.setMensaje("FNo se puede Modificar un contable que contiene un cheque conciliado:");
                                            mensajeTO.setListaErrores1(lisCheques);
                                            return mensajeTO;
                                        }

                                        if (ventasMotivoDao.comprobarInvVentasMotivo(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaMotivo())) {
                                            InvVentas invVentasCreadas = ventasDao.buscarInvVentas(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
                                            String estado = "";
                                            if (invVentasTO.getVtaAnulado()) {
                                                invVentasTO.setVtaPendiente(false);
                                                estado = "anulá";
                                            } else {
                                                estado = "mayorizo";
                                            }
                                            boolean validacionModificar = invVentasCreadas != null && !quitarAnulado;
                                            boolean validacionRestaurar = invVentasCreadas != null && invVentasCreadas.getVtaAnulado() && quitarAnulado;
                                            if (validacionModificar || validacionRestaurar) {
                                                if (quitarAnulado) {
                                                    eliminarMotivoAnulacion = true;
                                                    InvVentasMotivoAnulacion invVentasMotivoAnulacionTmp = ventasMotivoAnulacionDao.buscarVentaMotivo(invVentasTO.getVtaEmpresa(),
                                                            invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
                                                    invVentasMotivoAnulacion = new InvVentasMotivoAnulacion();
                                                    invVentasMotivoAnulacion.setAnuComentario(invVentasMotivoAnulacionTmp.getAnuComentario());
                                                    invVentasMotivoAnulacion.setInvVentas(invVentasMotivoAnulacionTmp.getInvVentas());
                                                    invVentasMotivoAnulacion.setAnuSecuencial(invVentasMotivoAnulacionTmp.getAnuSecuencial());

                                                    estado = "restauró";
                                                    //// CREANDO SUCESO
                                                    susClave = invVentasTO.getVtaPeriodo() + " " + invVentasTO.getVtaMotivo() + " " + invVentasTO.getVtaNumero();
                                                    susDetalle = "Se " + estado + " la venta en el periodo " + invVentasTO.getVtaPeriodo() + ", del motivo "
                                                            + invVentasTO.getVtaMotivo() + ", de la numeración " + invVentasTO.getVtaNumero();
                                                    susSuceso = "UPDATE";
                                                    susTabla = "inventario.inv_ventas";
                                                    sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                                    invVentasTO.setVtaAnulado(false);
                                                    invVentasTO.setUsrCodigo(invVentasCreadas.getUsrCodigo());
                                                    invVentasTO.setUsrFechaInserta(UtilsValidacion.fecha(invVentasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                                } else {
                                                    susDetalle = "";
                                                    if (estado.trim().equals("anuló")) {
                                                        susDetalle = "Venta número " + invVentasTO.getVtaNumero() + "se anuló por "
                                                                + invVentasMotivoAnulacion.getAnuComentario();
                                                    } else {
                                                        susDetalle = "Se " + estado + " la ventas en el periodo " + invVentasTO.getVtaPeriodo() + ", del motivo "
                                                                + invVentasTO.getVtaMotivo() + ", de la numeración " + invVentasTO.getVtaNumero();
                                                    }
                                                    //// CREANDO SUCESO
                                                    susClave = invVentasTO.getVtaPeriodo() + " " + invVentasTO.getVtaMotivo() + " " + invVentasTO.getVtaNumero();
                                                    susSuceso = "UPDATE";
                                                    susTabla = "inventario.inv_ventas";
                                                    sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                                    invVentasTO.setUsrCodigo(invVentasCreadas.getUsrCodigo());
                                                    invVentasTO.setUsrFechaInserta(UtilsValidacion.fecha(invVentasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                                }
                                                ////// CREANDO NUMERACION DE
                                                ////// VENTA CREANDO VENTAS
                                                InvVentas invVentas = ConversionesInventario.convertirInvVentasTO_InvVentas(invVentasTO);
                                                invVentas.setInvCliente(clienteDao.buscarInvCliente(invVentasTO.getCliEmpresa(), invVentasTO.getCliCodigo()));
                                                ////// CONVIRTIENDO A
                                                ////// ENTIDAD EL DETALLE
                                                List<InvVentasDetalle> listInvVentasDetalle = new ArrayList<InvVentasDetalle>();
                                                InvVentasDetalle invVentasDetalle = null;
                                                int estadoDetalle = 0;
                                                for (InvVentasDetalleTO invVentasDetalleTO : listaInvVentasDetalleTO) {
                                                    invVentasDetalle = new InvVentasDetalle();
                                                    invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
                                                    invVentasDetalle = ConversionesInventario.convertirInvVentasDetalleTO_InvVentasDetalle(invVentasDetalleTO);
                                                    ///// BUSCANDO EL
                                                    ///// PRODUCTO EN DETALLE
                                                    InvProducto invProducto = productoDao.buscarInvProducto(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getProCodigoPrincipal());
                                                    if (invProducto != null) {
                                                        invVentasDetalle.setInvProducto(invProducto);
                                                        invVentasDetalle.setProCreditoTributario(invProducto.getProCreditoTributario());
                                                        ////// BUSCANDO LA BODEGA
                                                        ////// EN EL DETALLE
                                                        InvBodega invBodega = bodegaDao.buscarInvBodega(invVentasDetalleTO.getVtaEmpresa(), invVentasDetalleTO.getBodCodigo());
                                                        if (invBodega != null) {
                                                            invVentasDetalle.setInvBodega(invBodega);
                                                            //PRODUCTO FORMULA
                                                            /*ES UN DETALLE GUARDADO EN BD */
                                                            if (invVentasDetalleTO.getDetSecuencia() != null && invVentasDetalleTO.getDetSecuencia() != 0) {
                                                                if (invVentasDetalleTO.getInvVentasDetalleComplementarioList() != null && invVentasDetalleTO.getInvVentasDetalleComplementarioList().size() > 0) {
                                                                    //ACTUALIZAR
                                                                    for (InvVentasDetalleTO item : invVentasDetalleTO.getInvVentasDetalleComplementarioList()) {
                                                                        InvVentasDetalle invVentasDetalleFormulaActu = ConversionesInventario.convertirInvVentasDetalleTO_InvVentasDetalle(item);
                                                                        InvProducto invProductoFormula = productoDao.buscarInvProducto(invVentasDetalleTO.getVtaEmpresa(), item.getProCodigoPrincipal());
                                                                        invVentasDetalleFormulaActu.setInvProducto(invProductoFormula);
                                                                        invVentasDetalleFormulaActu.setInvBodega(invBodega);
                                                                        invVentasDetalleFormulaActu.setDetCantidad(item.getDetCantidad().multiply(invVentasDetalle.getDetCantidad()));
                                                                        listInvVentasDetalle.add(invVentasDetalleFormulaActu);
                                                                    }
                                                                } else {
                                                                    List<InvProductoFormula> listaProductosFormula = productoFormulaService.getListInvProductoFormula(
                                                                            invVentas.getInvVentasPK().getVtaEmpresa(),
                                                                            invVentasDetalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                                                                    );
                                                                    if (listaProductosFormula != null && listaProductosFormula.size() > 0) {
                                                                        for (InvProductoFormula item : listaProductosFormula) {
                                                                            InvVentasDetalle invVentasDetalleFormula = new InvVentasDetalle();
                                                                            invVentasDetalleFormula.setDetSecuencial(estadoDetalle);
                                                                            invVentasDetalleFormula.setInvProducto(item.getInvProductoFormula());
                                                                            invVentasDetalleFormula.setProNombre(invVentasDetalleFormula.getInvProducto().getProNombre());
                                                                            invVentasDetalleFormula.setSecEmpresa(invVentasDetalle.getSecEmpresa());
                                                                            invVentasDetalleFormula.setSecCodigo(invVentasDetalle.getSecCodigo());
                                                                            invVentasDetalleFormula.setDetCantidad(item.getPrCantidad().multiply(invVentasDetalle.getDetCantidad()));
                                                                            invVentasDetalleFormula.setDetPrecio(BigDecimal.ZERO);
                                                                            invVentasDetalleFormula.setDetParcial(BigDecimal.ZERO);
                                                                            invVentasDetalleFormula.setDetMontoIce(BigDecimal.ZERO);
                                                                            invVentasDetalleFormula.setDetPorcentajeRecargo(BigDecimal.ZERO);
                                                                            invVentasDetalleFormula.setDetPorcentajeDescuento(BigDecimal.ZERO);
                                                                            invVentasDetalleFormula.setDetValorPromedio(BigDecimal.ZERO);
                                                                            invVentasDetalleFormula.setIcePorcentaje(BigDecimal.ZERO);
                                                                            invVentasDetalleFormula.setIceTarifaFija(BigDecimal.ZERO);
                                                                            invVentasDetalleFormula.setProCreditoTributario(invVentasDetalle.getProCreditoTributario());
                                                                            invVentasDetalleFormula.setProComplementario(invVentasDetalle.getDetSecuencial());
                                                                            invVentasDetalleFormula.setDetPendiente(invVentas.getVtaPendiente());
                                                                            invVentasDetalleFormula.setInvBodega(invVentasDetalle.getInvBodega());
                                                                            listInvVentasDetalle.add(invVentasDetalleFormula);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            //******
                                                            listInvVentasDetalle.add(invVentasDetalle);
                                                        } else {
                                                            estadoDetalle = 2;
                                                        }
                                                    } else {
                                                        estadoDetalle = 1;
                                                    }

                                                    if (estadoDetalle == 1 || estadoDetalle == 2) {
                                                        break;
                                                    } else {
                                                        invProducto = null;
                                                    }
                                                }

                                                if (estadoDetalle == 0) {
                                                    ////// CONVIRTIENDO A ENTIDAD EL
                                                    ////// DETALLE A ELIMINAR
                                                    List<InvVentasDetalle> listInvVentasDetalleEliminar = new ArrayList<InvVentasDetalle>();
                                                    InvVentasDetalle invVentasDetalleEliminar = null;

                                                    int estadoDetalleEliminar = 0;
                                                    if (listaInvVentasEliminarDetalleTO != null) {
                                                        for (InvVentasDetalleTO invVentasDetalleTO : listaInvVentasEliminarDetalleTO) {
                                                            invVentasDetalleEliminar = new InvVentasDetalle(invVentasDetalleTO.getDetSecuencia());
                                                            listInvVentasDetalleEliminar.add(invVentasDetalleEliminar);
                                                        }
                                                    }
                                                    if (estadoDetalleEliminar == 0) {
                                                        AnxVenta anxVenta = null;
                                                        if (anxVentasTO != null) {
                                                            anxVentasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                                                            anxVenta = ConversionesAnexos.convertirAnxVentaTO_AnxVenta(anxVentasTO);
                                                        }
                                                        //////////// COMPROBAR SI NO EXISTE NUMERO DE FACTURA
                                                        String codigoFactura;
                                                        if (!sisEmpresaParametros.getParActividad().equals("COMISARIATO")) {
                                                            codigoFactura = ventasDao.getConteoNumeroFacturaVenta(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaDocumentoTipo(), invVentasTO.getVtaDocumentoNumero());
                                                        } else {
                                                            codigoFactura = "";
                                                        }
                                                        if ((codigoFactura.trim().isEmpty()
                                                                || codigoFactura.trim().equals(invVentasCreadas.getInvVentasPK().getVtaEmpresa().trim().concat(invVentasCreadas.getInvVentasPK().getVtaPeriodo().trim().concat(invVentasCreadas.getInvVentasPK().getVtaMotivo().trim().concat(invVentasCreadas.getInvVentasPK().getVtaNumero().trim())))))
                                                                || ((invVentasTO.getVtaDocumentoNumero() != null && invVentasTO.getVtaDocumentoNumero().equals("999-999-999999999"))
                                                                || invVentasTO.getVtaDocumentoTipo().equals("00"))) {
                                                            boolean noExiste = false;
                                                            //////////// COMPROBAR SI EL TIPO DE DOCUMENTO ES 00
                                                            if (anxVentasTO != null) {
                                                                //////////// COMPROBAR SI NO EXISTE NUMERO DE RETENCION
                                                                String codigoRetencion = "";
                                                                anxVentasTO.setVenRetencionNumero(anxVentasTO
                                                                        .getVenRetencionNumero() == null ? ""
                                                                                : anxVentasTO.getVenRetencionNumero());
                                                                if (!anxVentasTO.getVenRetencionNumero().isEmpty()) {
                                                                    codigoRetencion = ventaDao.getConteoNumeroRetencionVenta(
                                                                            anxVentasTO.getVenEmpresa(),
                                                                            anxVentasTO.getVenRetencionNumero(),
                                                                            invCliente.getInvClientePK().getCliCodigo());
                                                                } else {
                                                                    codigoRetencion = "";
                                                                }
                                                                if (codigoRetencion.trim().isEmpty()
                                                                        || codigoRetencion.trim().equals(invVentasCreadas.getInvVentasPK().getVtaEmpresa().trim()
                                                                                .concat(invVentasCreadas.getInvVentasPK().getVtaPeriodo().trim()
                                                                                        .concat(invVentasCreadas.getInvVentasPK().getVtaMotivo().trim()
                                                                                                .concat(invVentasCreadas.getInvVentasPK().getVtaNumero().trim()))))) {
                                                                    noExiste = true;
                                                                }
                                                            } else {
                                                                noExiste = true;
                                                            }
                                                            if (noExiste) {

                                                                if (!invVentas.getVtaDocumentoTipo().equals("04") && !desmayorizar && (listaInvVentasDetalleTO != null && listaInvVentasDetalleTO.size() > 0)) {
                                                                    if (!invVentas.getVtaAnulado() && !invVentas.getVtaPendiente()) {
                                                                        mensajeClase = productoSaldosService.verificarStockVentas(listInvVentasDetalle);
                                                                    }
                                                                } else {
                                                                    ///////////
                                                                }
                                                                if (mensajeClase.isEmpty()) {
                                                                    ConContable conContable = null;
                                                                    List<SisSuceso> listaSisSuceso = new ArrayList<SisSuceso>();

                                                                    if (invVentas.getVtaAnulado()) {
                                                                        conContable = contableDao.obtenerPorId(
                                                                                ConContable.class,
                                                                                new ConContablePK(
                                                                                        invVentas.getConEmpresa(),
                                                                                        invVentas.getConPeriodo(),
                                                                                        invVentas.getConTipo(),
                                                                                        invVentas.getConNumero()));
                                                                        if (conContable != null) {
                                                                            conContable.setConPendiente(false);
                                                                            conContable.setConAnulado(true);
                                                                            susSuceso = "DELETE";
                                                                            sisSuceso = Suceso.crearSisSuceso(
                                                                                    susTabla, susClave,
                                                                                    susSuceso, susDetalle,
                                                                                    sisInfoTO);
                                                                            listaSisSuceso.add(sisSuceso);
                                                                            /////////////// NUEVO SUCESO PARA CONTABLE
                                                                            SisSuceso sisSucesoContable = Suceso
                                                                                    .crearSisSuceso(
                                                                                            "contabilidad.con_contable",
                                                                                            conContable.getConContablePK().getConPeriodo()
                                                                                            + " "
                                                                                            + conContable.getConContablePK().getConTipo()
                                                                                            + " "
                                                                                            + conContable.getConContablePK().getConNumero(),
                                                                                            "DELETE",
                                                                                            "Se anuló el contable del periodo "
                                                                                            + conContable.getConContablePK().getConPeriodo()
                                                                                            + ", del tipo "
                                                                                            + conContable.getConContablePK().getConTipo()
                                                                                            + ", de la numeración "
                                                                                            + conContable.getConContablePK().getConNumero(),
                                                                                            sisInfoTO);
                                                                            listaSisSuceso
                                                                                    .add(sisSucesoContable);
                                                                            sisSuceso = null;

                                                                        }
                                                                    }

                                                                    if (quitarAnulado) {
                                                                        conContable = contableDao.obtenerPorId(
                                                                                ConContable.class,
                                                                                new ConContablePK(
                                                                                        invVentas.getConEmpresa(),
                                                                                        invVentas.getConPeriodo(),
                                                                                        invVentas.getConTipo(),
                                                                                        invVentas.getConNumero()));
                                                                        if (conContable != null) {
                                                                            conContable.setConAnulado(false);
                                                                            susSuceso = "RESTORE";
                                                                            sisSuceso = Suceso.crearSisSuceso(
                                                                                    susTabla, susClave,
                                                                                    susSuceso, susDetalle,
                                                                                    sisInfoTO);
                                                                            listaSisSuceso.add(sisSuceso);
                                                                            /////////////// NUEVO SUCESO PARA CONTABLE
                                                                            SisSuceso sisSucesoContable = Suceso
                                                                                    .crearSisSuceso(
                                                                                            "contabilidad.con_contable",
                                                                                            conContable.getConContablePK().getConPeriodo()
                                                                                            + " "
                                                                                            + conContable.getConContablePK().getConTipo()
                                                                                            + " "
                                                                                            + conContable
                                                                                                    .getConContablePK()
                                                                                                    .getConNumero(),
                                                                                            "RESTORE",
                                                                                            "Se restauró el contable del periodo "
                                                                                            + conContable
                                                                                                    .getConContablePK()
                                                                                                    .getConPeriodo()
                                                                                            + ", del tipo "
                                                                                            + conContable
                                                                                                    .getConContablePK()
                                                                                                    .getConTipo()
                                                                                            + ", de la numeración "
                                                                                            + conContable
                                                                                                    .getConContablePK()
                                                                                                    .getConNumero(),
                                                                                            sisInfoTO);
                                                                            listaSisSuceso
                                                                                    .add(sisSucesoContable);
                                                                            sisSuceso = null;
                                                                        }
                                                                    }

                                                                    InvVentasComplemento invVentasComplemento = null;
                                                                    InvVentasComplemento invVentasComplementoAux = null;
                                                                    puedeContinuar = true;//false
                                                                    String complemento = "";
                                                                    if (!invVentas.getVtaAnulado()) {
                                                                        invVentasComplementoAux = ventasComplementoDao.buscarVentasComplemento(
                                                                                invVentas.getInvVentasPK().getVtaEmpresa(),
                                                                                invVentas.getInvVentasPK().getVtaPeriodo(),
                                                                                invVentas.getInvVentasPK().getVtaMotivo(),
                                                                                invVentas.getInvVentasPK().getVtaNumero());

                                                                        if (invVentasComplementoAux != null && (!invVentas.getVtaDocumentoTipo().equals("04") && !invVentas.getVtaDocumentoTipo().equals("05"))) {
                                                                            puedeContinuar = true;
                                                                            complemento = "ELIMINAR";
                                                                            invVentasComplemento = new InvVentasComplemento();
                                                                            invVentasComplemento.setComDocumentoNumero(invVentasComplementoAux.getComDocumentoNumero());
                                                                            invVentasComplemento.setComDocumentoTipo(invVentasComplementoAux.getComDocumentoTipo());
                                                                        } else if (invVentasComplementoAux == null
                                                                                && (invVentas.getVtaDocumentoTipo().equals("04") || invVentas.getVtaDocumentoTipo().equals("05"))
                                                                                && (numeroDocumentoComplemento != null && tipoDocumentoComplemento != null && !numeroDocumentoComplemento.trim().isEmpty() && !tipoDocumentoComplemento.trim().isEmpty())) {
                                                                            complemento = "CREAR";
                                                                            invVentasComplemento = new InvVentasComplemento();
                                                                            invVentasComplemento.setComDocumentoNumero(numeroDocumentoComplemento);
                                                                            invVentasComplemento.setComDocumentoTipo(tipoDocumentoComplemento);
                                                                            invVentasComplemento.setComDocumentoMotivo(motivoDocumentoComplemento);
                                                                            codigoFactura = ventasDao.getConteoNotaCreditoVenta(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo(), tipoDocumentoComplemento, numeroDocumentoComplemento);
                                                                            if (!codigoFactura.trim().isEmpty() || invVentasTO.getVtaDocumentoNumero().equals("999-999-999999999")) {
                                                                                puedeContinuar = true;
                                                                            }
                                                                        } else if (invVentasComplementoAux != null
                                                                                && (invVentas.getVtaDocumentoTipo().equals("04") || invVentas.getVtaDocumentoTipo().equals("05"))
                                                                                && (numeroDocumentoComplemento != null && tipoDocumentoComplemento != null && !numeroDocumentoComplemento.trim().isEmpty() && !tipoDocumentoComplemento.trim().isEmpty())) {
                                                                            complemento = "MODIFICAR";
                                                                            invVentasComplemento = new InvVentasComplemento();
                                                                            invVentasComplemento.setComDocumentoNumero(numeroDocumentoComplemento);
                                                                            invVentasComplemento.setComDocumentoTipo(tipoDocumentoComplemento);
                                                                            invVentasComplemento.setComDocumentoMotivo(motivoDocumentoComplemento);
                                                                            codigoFactura = ventasDao.getConteoNotaCreditoVenta(invVentasTO.getVtaEmpresa(), invVentasTO.getCliCodigo(), tipoDocumentoComplemento, numeroDocumentoComplemento);
                                                                            if (!codigoFactura.trim().isEmpty() || invVentasTO.getVtaDocumentoNumero().equals("999-999-999999999")) {
                                                                                puedeContinuar = true;
                                                                            }
                                                                        } else {
                                                                            if (invVentasComplementoAux != null && numeroDocumentoComplemento == null && motivoDocumentoComplemento.equals("NOTA DE CREDITO INTERNA")) {
                                                                                complemento = "ELIMINAR";
                                                                                invVentasComplemento = new InvVentasComplemento();
                                                                                invVentasComplemento.setComDocumentoNumero(invVentasComplementoAux.getComDocumentoNumero());
                                                                                invVentasComplemento.setComDocumentoTipo(invVentasComplementoAux.getComDocumentoTipo());
                                                                            }
                                                                            puedeContinuar = true;
                                                                        }
                                                                    } else {
                                                                        puedeContinuar = true;
                                                                    }
                                                                    if (puedeContinuar) {
                                                                        invVentas.setVtaBaseExenta(cero);
                                                                        invVentas.setVtaBaseNoObjeto(cero);
                                                                        invVentas
                                                                                .setVtaSubtotalBaseExenta(cero);
                                                                        invVentas.setVtaSubtotalBaseNoObjeto(
                                                                                cero);
                                                                        invVentas.setVtaDescuentoBaseExenta(
                                                                                cero);
                                                                        invVentas.setVtaDescuentoBaseNoObjeto(
                                                                                cero);

                                                                        if (invVentas.getVtaAnulado()) {
                                                                            invVentasMotivoAnulacion
                                                                                    .setInvVentas(invVentas);
                                                                        }

                                                                        if (invVentas.getVtaDocumentoTipo()
                                                                                .equals("00")) {
                                                                            invVentas.setVtaDocumentoNumero(
                                                                                    null);
                                                                        }
                                                                        if (invVentas.getConNumero() == null
                                                                                || invVentas
                                                                                        .getConPeriodo() == null
                                                                                || invVentas
                                                                                        .getConTipo() == null) {
                                                                            invVentas.setConEmpresa(null);
                                                                            invVentas.setConNumero(null);
                                                                            invVentas.setConPeriodo(null);
                                                                            invVentas.setConTipo(null);
                                                                        }
                                                                        //LIQUIDACION
                                                                        List<InvVentasLiquidacion> listInvVentasLiquidacion = new ArrayList<>();
                                                                        if (listaInvVentasLiquidacionTO != null) {
                                                                            for (int i = 0; i < listaInvVentasLiquidacionTO.size(); i++) {
                                                                                InvVentasLiquidacion liqVent = ConversionesInventario.convertirInvVentasLiquidacionTO_InvVentasLiquidacion(listaInvVentasLiquidacionTO.get(i));
                                                                                liqVent.setInvVentas(invVentas);
                                                                                listInvVentasLiquidacion.add(liqVent);
                                                                            }
                                                                        }

                                                                        List<InvVentasLiquidacion> listInvVentasLiquidacionEliminar = new ArrayList<>();
                                                                        if (listaInvVentasLiquidacionTOEliminar != null) {
                                                                            for (InvVentasLiquidacionTO item : listaInvVentasLiquidacionTOEliminar) {
                                                                                InvVentasLiquidacion liqVentElim = ConversionesInventario.convertirInvVentasLiquidacionTO_InvVentasLiquidacion(item);
                                                                                liqVentElim.setInvVentas(invVentas);
                                                                                listInvVentasLiquidacionEliminar.add(liqVentElim);
                                                                            }
                                                                        }

                                                                        //VENTA EXPORTACION
                                                                        AnxVentaExportacion anexo = null;
                                                                        if (anxVentaExportacionTO != null) {
                                                                            anexo = ConversionesAnexos.convertirAnxVentaExportacionTO_AnxVentaExportacion(anxVentaExportacionTO);
                                                                        }
                                                                        //REEMBOLSOS
                                                                        List<AnxVentaReembolso> listAnxVentaReembolso = new ArrayList<>();
                                                                        List<AnxVentaReembolso> listAnxVentaReembolsoEliminar = new ArrayList<>();
                                                                        if (listAnxVentaReembolsoTO != null) {
                                                                            for (AnxVentaReembolsoTO item : listAnxVentaReembolsoTO) {
                                                                                AnxVentaReembolso reembolso = ConversionesInventario.convertirAnxVentaReembolsoTO_AnxVentaReembolso(item);
                                                                                listAnxVentaReembolso.add(reembolso);
                                                                            }
                                                                        }

                                                                        if (listAnxVentaReembolsoEliminarTO != null) {
                                                                            for (AnxVentaReembolsoTO item : listAnxVentaReembolsoEliminarTO) {
                                                                                AnxVentaReembolso reembolso = ConversionesInventario.convertirAnxVentaReembolsoTO_AnxVentaReembolso(item);
                                                                                listAnxVentaReembolsoEliminar.add(reembolso);
                                                                            }
                                                                        }

                                                                        //PRODUCTO FORMULA
                                                                        comprobar = ventasDao
                                                                                .modificarInvVentas(invVentas,
                                                                                        listInvVentasDetalle,
                                                                                        listInvVentasDetalleEliminar,
                                                                                        sisSuceso,
                                                                                        listaSisSuceso,
                                                                                        conContable, anxVenta,
                                                                                        invVentasComplemento,
                                                                                        complemento,
                                                                                        invVentasMotivoAnulacion,
                                                                                        eliminarMotivoAnulacion,
                                                                                        desmayorizar, false,
                                                                                        invVentaGuiaRemision,
                                                                                        listInvVentasLiquidacion,
                                                                                        listInvVentasLiquidacionEliminar,
                                                                                        listAnxVentaReembolso,
                                                                                        listAnxVentaReembolsoEliminar,
                                                                                        anexo);
                                                                        if (comprobar) {
                                                                            // quitarPendiente(invVentasTO);
                                                                            SisPeriodo sisPeriodo = periodoService
                                                                                    .buscarPeriodo(
                                                                                            invVentasTO
                                                                                                    .getVtaEmpresa(),
                                                                                            invVentas
                                                                                                    .getInvVentasPK()
                                                                                                    .getVtaPeriodo());
                                                                            String accion = "";
                                                                            if (invVentasTO.getVtaAnulado()) {
                                                                                accion = "anuló";
                                                                            } else if (quitarAnulado) {
                                                                                accion = "restauró";
                                                                            } else {
                                                                                accion = "modificó";
                                                                            }
                                                                            retorno = "T<html>Se  " + accion
                                                                                    + "  la Venta con la siguiente información:<br><br>"
                                                                                    + "Periodo: <font size = 5>"
                                                                                    + sisPeriodo.getPerDetalle()
                                                                                    + "</font>.<br> Motivo: <font size = 5>"
                                                                                    + invVentas.getInvVentasPK()
                                                                                            .getVtaMotivo()
                                                                                    + "</font>.<br> Número: <font size = 5>"
                                                                                    + invVentas.getInvVentasPK()
                                                                                            .getVtaNumero()
                                                                                    + "</font>.</html>"
                                                                                    + invVentas.getInvVentasPK()
                                                                                            .getVtaNumero()
                                                                                    + ","
                                                                                    + sisPeriodo
                                                                                            .getSisPeriodoPK()
                                                                                            .getPerCodigo();
                                                                            mensajeTO.setFechaCreacion(invVentas
                                                                                    .getUsrFechaInserta()
                                                                                    .toString());

                                                                            Map<String, Object> campos = new HashMap<>();
                                                                            campos.put("invVentasTO", invVentasTO);
                                                                            mensajeTO.setMap(campos);

                                                                            if (!carListaPagosCobrosVencidosTOs
                                                                                    .isEmpty()) {
                                                                                mensajeClase.add(
                                                                                        "<html>Cliente tiene deudas vencidas! Avise al cliente que\nse acerque a cancelar lo más pronto...</html>");
                                                                                mensajeClase.add(
                                                                                        "Número de Sistema\tValor\tFecha de Vencimiento");
                                                                                for (int i = 0; i < carListaPagosCobrosVencidosTOs
                                                                                        .size(); i++) {
                                                                                    mensajeClase
                                                                                            .add(carListaPagosCobrosVencidosTOs
                                                                                                    .get(i)
                                                                                                    .getCxccPeriodo()
                                                                                                    + " | "
                                                                                                    + carListaPagosCobrosVencidosTOs
                                                                                                            .get(i)
                                                                                                            .getCxccMotivo()
                                                                                                    + " | "
                                                                                                    + carListaPagosCobrosVencidosTOs
                                                                                                            .get(i)
                                                                                                            .getCxccNumero()
                                                                                                    + "\t"
                                                                                                    + carListaPagosCobrosVencidosTOs
                                                                                                            .get(i)
                                                                                                            .getCxccSaldo()
                                                                                                    + "\t"
                                                                                                    + carListaPagosCobrosVencidosTOs
                                                                                                            .get(i)
                                                                                                            .getCxccFechaVencimiento());
                                                                                }
                                                                                mensajeTO.setListaErrores1(
                                                                                        mensajeClase);
                                                                            }
                                                                            //Imagenes
                                                                            ventasDao.actualizarImagenesVenta(listadoImagenes, invVentas.getInvVentasPK(), new ArrayList<>());
                                                                        } else {
                                                                            retorno = "FHubo un error al modificar la Venta...\nIntente de nuevo o contacte con el administrador";
                                                                        }

                                                                    } else {
                                                                        retorno = "F<html>El Número de Documento del Complemento que ingresó no existe...\nIntente de nuevo o contacte con el administrador</html>";
                                                                    }
                                                                } else {
                                                                    retorno = "F<html>No hay stock suficiente en los siguientes productos...........</html>";
                                                                    mensajeTO.setListaErrores1(mensajeClase);
                                                                }
                                                            } else {
                                                                retorno = "F<html>El Número de Retención que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                                            }
                                                        } else {
                                                            String codigoFacturaCreada = invVentasCreadas.getInvVentasPK().getVtaEmpresa().trim().concat(invVentasCreadas.getInvVentasPK().getVtaPeriodo().trim().concat(invVentasCreadas.getInvVentasPK().getVtaMotivo().trim().concat(invVentasCreadas.getInvVentasPK().getVtaNumero().trim())));
                                                            if (!codigoFactura.trim().equals(codigoFacturaCreada)) {
                                                                retorno = "F<html>El Número de Documento que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                                            } else {
                                                                retorno = "F<html>No se puede realizar la acción solicitada sobre la venta.</html>";
                                                            }
                                                        }
                                                    } else if (estadoDetalleEliminar == 1) {
                                                        retorno = "F<html>Uno de los Productos que escogió ya no esta¡ disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                                    } else {
                                                        retorno = "F<html>Una de las Bodega que escogió ya no está¡ disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                                    }
                                                } else if (estadoDetalle == 1) {
                                                    retorno = "F<html>Uno de los Productos que escogió ya no esta¡ disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                                } else {
                                                    retorno = "F<html>Una de las Bodega que escogió ya no esta¡ disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                                }
                                            } else if (validacionModificar) {
                                                retorno = "F<html>La venta que quiere modificar ya no se encuentra disponible.</html>";
                                            } else {
                                                retorno = "F<html>La venta que desea restaurar NO existe o NO esta¡ anulada</html>";
                                            }
                                        } else {
                                            retorno = "F<html>No se encuentra el motivo...</html>";
                                        }
                                    } else {
                                        retorno = "F<html>No se puede MAYORIZAR, DESMAYORIZAR o ANULAR debido a que el periodo se encuentra cerrado...</html>";
                                    }
                                } else {
                                    retorno = "F<html>No se encuentra ningun periodo para la fecha que ingresó...</html>";
                                }
                            } else {
                                retorno = "F<html>Se superó el limite del monto del credito...</html>";
                            }
                        } else {
                            retorno = "F<html>Los días de crédito del Cliente es superior al que se le permite...</html>";
                        }
                    } else {
                        retorno = "F<html>" + detalleError + "</html>";
                    }
                } else {
                    retorno = "F<html>El Cliente que escogió ya no esta¡ disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
                }
            } else {
                retorno = "F<html>Cliente tiene deudas vencidas! Avise al cliente que\nse acerque a cancelar lo más pronto...</html>";
                mensajeTO.setMensaje(retorno);
                mensajeClase.add("Número de Sistema\tValor\tFecha de Vencimiento");
                for (int i = 0; i < carListaPagosCobrosVencidosTOs.size(); i++) {
                    mensajeClase.add(carListaPagosCobrosVencidosTOs.get(i).getCxccPeriodo() + " | "
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccMotivo() + " | "
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccNumero() + "\t"
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccSaldo() + "\t"
                            + carListaPagosCobrosVencidosTOs.get(i).getCxccFechaVencimiento());
                }
                mensajeTO.setListaErrores1(mensajeClase);
            }
        } else {
            retorno = "F<html>El NUMERO DE DOCUMENTO que ingresó se encuentra ANULADO</html>";
        }

        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public InvVentas obtenerInvVenta(String empresa, String periodo, String motivo, String numero) throws Exception {
        return ventasDao.buscarInvVentas(empresa, periodo, motivo, numero);
    }

    @Override
    public String desmayorizarLoteVenta(InvVentasTO invVentasTO, AnxVentaTO anxVentaTO, SisInfoTO sisInfoTO) throws Exception {
        List<InvListadoCobrosTO> listadoCobrosTOs = new ArrayList();
        String errores = "";
        if (invVentasTO != null && invVentasTO.getVtaPendiente()) {
            listadoCobrosTOs = ventasDao.invListadoCobrosTO(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());
        }
        if (listadoCobrosTOs != null && listadoCobrosTOs.isEmpty()) {
            if (anxVentaTO != null) {
                ////// MANDA A GUARDAR
                MensajeTO mensajeTO = modificarInvVentasTO(invVentasTO, null, null, true, anxVentaTO, false, "", "", null, sisInfoTO);
                String mensaje = mensajeTO.getMensaje();
                if (mensaje.charAt(0) == 'T') {
                    return "TLa venta " + invVentasTO.getVtaPeriodo() + "|" + invVentasTO.getVtaMotivo() + "|" + invVentasTO.getVtaNumero() + ", se ha desmayorizado correctamente.";
                } else {
                    for (String item : mensajeTO.getListaErrores1()) {
                        errores = errores + "-" + item + "\n";
                    }
                    return "FLa venta no fue desmayorizada.\n" + errores;
                }
            } else {
                ////// MANDA A GUARDAR
                MensajeTO mensajeTO = modificarInvVentasTO(invVentasTO, null, null, true, anxVentaTO, false, "", "", null, sisInfoTO);
                String mensaje = mensajeTO.getMensaje();
                if (mensaje.charAt(0) == 'T') {
                    return "TLa venta " + invVentasTO.getVtaPeriodo() + "|" + invVentasTO.getVtaMotivo() + "|" + invVentasTO.getVtaNumero() + ", se ha desmayorizado correctamente.";
                } else {
                    for (String item : mensajeTO.getListaErrores1()) {
                        errores = errores + "-" + item + "\n";
                    }
                    return "F" + mensajeTO.getMensaje().substring(1) + "\n" + errores;
                }
            }
        } else {
            List<String> lista = new ArrayList();
            for (int i = 0; i < listadoCobrosTOs.size(); i++) {
                lista.add(listadoCobrosTOs.get(i).toString());
            }
            if (anxVentaTO != null) {
                if ((anxVentaTO.getVenBase0()
                        .compareTo(UtilsValidacion.redondeoDecimalBigDecimal(invVentasTO.getVtaBase0(), 2, RoundingMode.HALF_UP)) == 0)
                        && (anxVentaTO.getVenBaseImponible().compareTo(UtilsValidacion.redondeoDecimalBigDecimal(invVentasTO.getVtaSubtotalBaseImponible(), 2, RoundingMode.HALF_UP)) == 0)) {
                    ////// MANDA A GUARDAR
                    MensajeTO mensajeTO = modificarInvVentasTO(invVentasTO, null, null, true, anxVentaTO, false, "", "", null, sisInfoTO);
                    String mensaje = mensajeTO.getMensaje();
                    if (mensaje.charAt(0) == 'T') {
                        return "TLa venta " + invVentasTO.getVtaPeriodo() + "|" + invVentasTO.getVtaMotivo() + "|" + invVentasTO.getVtaNumero() + ", se ha desmayorizado correctamente.";
                    } else {
                        for (String item : mensajeTO.getListaErrores1()) {
                            errores = errores + "-" + item + "\n";
                        }
                        return "FLa venta no fue desmayorizada.\n" + mensajeTO.getMensaje().substring(1) + "\n" + errores;
                    }
                } else {
                    return "FLa venta no fue desmayorizada debido a que los valores de retención han cambiado\n\nIntentelo de nuevo o contacte al administrador";
                }
            } else {
                ////// MANDA A GUARDAR
                MensajeTO mensajeTO = modificarInvVentasTO(invVentasTO, null, null, true, anxVentaTO, false, "", "", null, sisInfoTO);
                String mensaje = mensajeTO.getMensaje();
                if (mensaje.charAt(0) == 'T') {
                    return "TLa venta " + invVentasTO.getVtaPeriodo() + "|" + invVentasTO.getVtaMotivo() + "|" + invVentasTO.getVtaNumero() + ", se ha desmayorizado correctamente.";
                } else {
                    for (String item : mensajeTO.getListaErrores1()) {
                        errores = errores + "-" + item + "\n";
                    }
                    return "FLa venta no fue desmayorizada. Intentelo de nuevo o contacte al administrador\n" + mensajeTO.getMensaje().substring(1) + "\n" + errores;
                }
            }
        }
    }

    @Override
    public Map<String, Object> suprimirVenta(InvVentasTO invVentasTO, SisInfoTO sisInfoTO) throws Exception {
        boolean estaDesmayorizado = false;
        String mensajeRespuesta = "T";
        Map<String, Object> campos = new HashMap<>();

        List<InvVentasDetalle> listaDetalle = new ArrayList<>();
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = new ArrayList<>();

        if (invVentasTO.getVtaAnulado()) {
            mensajeRespuesta = "FLa venta esta ANULADA, se debe restaurar";
        } else {
            if (invVentasTO.getVtaPendiente()) {
                listaDetalle = ventasDao.obtenerVentaDetallePorNumero(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaPeriodo(), invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero());

                for (InvVentasDetalle detalle : listaDetalle) {
                    InvVentasDetalleTO detalleTO = ConversionesInventario.convertirInvVentasDetalle_InvVentasDetalleTO(detalle);
                    listaInvVentasDetalleTO.add(detalleTO);
                }

                estaDesmayorizado = true;
            } else {// se debe desnayorizar
                mensajeRespuesta = "FLa venta debe estar PENDIENTE";
            }

            //buscar reembolso
            List<AnxVentaReembolsoTO> listAnxVentaReembolsoEliminarTO
                    = anxVentaReembolsoService.getListaAnxVentaReembolsoTO(
                            invVentasTO.getVtaEmpresa(),
                            invVentasTO.getVtaPeriodo(),
                            invVentasTO.getVtaMotivo(),
                            invVentasTO.getVtaNumero());

            if (estaDesmayorizado) {//Solo cambiamos a 00 y anulamos
                invVentasTO.setVtaDocumentoTipo("00");
                invVentasTO.setVtaDocumentoNumero("999-999-999999999");
                MensajeTO mensajeTO = modificarInventarioVentasTO(
                        invVentasTO,
                        listaInvVentasDetalleTO,
                        new ArrayList<>(),
                        false,
                        null,
                        false,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        null,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        listAnxVentaReembolsoEliminarTO,
                        sisInfoTO);
                mensajeRespuesta = mensajeTO.getMensaje();
            }

        }
        campos.put("listaInvVentasDetalleTO", listaInvVentasDetalleTO);
        campos.put("invVentasTO", invVentasTO);
        campos.put("mensaje", mensajeRespuesta);
        return campos;
    }

    @Override
    public void notificarPorCorreoVentasConError(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RespuestaWebTO> listado, String usuario, int guardadas) throws Exception {
        List<InvClientesVentasDetalle> detalleReporte = new ArrayList<>();
        for (RespuestaWebTO mensaje : listado) {
            List<InvClientesVentasDetalle> detalle = (List<InvClientesVentasDetalle>) mensaje.getExtraInfo();
            if (detalle != null && !detalle.isEmpty()) {
                for (int i = 0; i < detalle.size(); i++) {
                    ObjectMapper mapper = new ObjectMapper();
                    InvClientesVentasDetalle ventaDetalle = mapper.convertValue(detalle.get(i), InvClientesVentasDetalle.class
                    );
                    if (ventaDetalle != null) {
                        ventaDetalle.getInvCliente().setCliCiudad(mensaje.getOperacionMensaje());
                    }
                    detalleReporte.add(ventaDetalle);
                }
            }
        }
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, usuarioEmpresaReporteTO.getEmpCodigo());
        SisUsuario sisUuario = usuarioService.obtenerPorId(usuario);
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = ConversionesSistema.completarDatosComprobanteElectronicoTO(sisEmpresaParametros);
        sisEmailComprobanteElectronicoTO.setNombreReceptor(sisUuario.getUsrNombre() + " " + sisUuario.getUsrApellido());
        sisEmailComprobanteElectronicoTO.setMailReceptor(sisUuario.getUsrEmail());
        SisNotificacion sisNotificacion = notificacionService.obtener();
        if (sisNotificacion != null) {
            sisEmailComprobanteElectronicoTO.setMailEmisor(sisNotificacion.getMailEmisor());
        }
        //Datos para AWS
        sisEmailComprobanteElectronicoTO.setTipoComprobante(TipoNotificacion.NOTIFICAR_VENTA_RECURRENTE_ERROR.getNombre());
        if (sisEmailComprobanteElectronicoTO.getMailEmisor() == null) {
            throw new GeneralException("Correo del emisor no registrado.");
        } else if (sisEmailComprobanteElectronicoTO.getMailReceptor() == null || sisEmailComprobanteElectronicoTO.getMailReceptor().compareTo("") == 0) {
            throw new GeneralException("Correo del receptor no registrado.");
        } else {
            String detalle = "<html><head><title></title></head><body>"
                    + "<br>Este correo electrónico es para notificarle que el proceso de generacion de ventas recurrentes ha finalizado. Y se han guardado exitosamente " + guardadas + " ventas. ";
            String errores = "<br><strong>Las siguientes ventas recurrentes no fueron guardadas como se esperaba.</strong> "
                    + "<br>Considere resolver los inconvenientes e intente nuevamente. "
                    + "<br>Si el problema persiste; considere ingresarlas manualmente. "
                    + "<br><br>------------------------------------------------------------------------------------------------------";
            String finalizar = "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                    + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>"
                    + "<br>";
            List<File> listAdjunto = new ArrayList<>();
            if (listado != null && !listado.isEmpty()) {
                detalle = detalle + errores + finalizar;
                File filePDF = genericReporteService.generarFile("inventario", "ventasRecurrentesFallidas.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), detalleReporte);
                if (filePDF != null) {
                    File file = genericReporteService.respondeServidorCorreo(filePDF, "ventasRecurrentesFallidas");
                    if (file != null) {
                        listAdjunto.add(file);
                    }
                }
            } else {
                detalle = detalle + finalizar;
            }
            UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, sisEmailComprobanteElectronicoTO.getMailReceptor(), "[Atención] Ventas recurrentes.", detalle, "", listAdjunto, sisNotificacion);
        }
    }

    @Override
    public List<InvListaSecuencialesTO> listarInvListaSecuencialesVentas(String empresa, String tipoDocumento) throws Exception {
        List<InvListaSecuencialesTO> lista = new ArrayList<>();
        Timestamp fechaActualTime = sistemaWebServicio.getFechaActual();
        String fechaActual = UtilsDate.fechaFormatoString(fechaActualTime, "yyyy-MM-dd");
        List<AnxNumeracionTablaTO> numeraciones = numeracionService.getListaAnexoNumeracionTO(empresa, tipoDocumento, fechaActual);

        if (numeraciones != null && !numeraciones.isEmpty()) {
            for (AnxNumeracionTablaTO item : numeraciones) {
                InvListaSecuencialesTO secuencialVenta = new InvListaSecuencialesTO();
                String numeroDocumento = "";

                String[] partsSecuencial = item.getNumeroDesde().split("-");
                String secuencialPermitido = partsSecuencial[0] + "-" + partsSecuencial[1];//001-001 ejemplo
                String ultimoSecuencialVenta = ventaService.getUltimaNumeracionComprobante(empresa, tipoDocumento, secuencialPermitido);

                if (ultimoSecuencialVenta != null && !"".equals(ultimoSecuencialVenta)) {
                    String separado = ultimoSecuencialVenta.substring(0, ultimoSecuencialVenta.lastIndexOf("-") + 1);
                    int numero = Integer.parseInt(ultimoSecuencialVenta.substring(ultimoSecuencialVenta.lastIndexOf("-") + 2));
                    int numeroParaDocumento = numero + 1;
                    int digitosNumero = ("" + numeroParaDocumento).length();
                    for (int i = 0; i < (9 - digitosNumero); i++) {
                        numeroDocumento = numeroDocumento + "0";
                    }
                    numeroDocumento = separado + numeroDocumento + numeroParaDocumento;
                    if (numero >= item.getSecuenciaDesde() && numero <= item.getSecuenciaHasta()) {
                        secuencialVenta.setNumAutorizacion(item.getNumeroAutorizacion());
                        secuencialVenta.setNumObservacion(item.getNumObservacion() != null ? (item.getNumObservacion()).toUpperCase() : "");
                        secuencialVenta.setSecuencial(numeroDocumento);
                        lista.add(secuencialVenta);
                    }
                } else {
                    numeroDocumento = secuencialPermitido + "-000000001";
                    secuencialVenta.setNumAutorizacion(item.getNumeroAutorizacion());
                    secuencialVenta.setNumObservacion(item.getNumObservacion() != null ? (item.getNumObservacion()).toUpperCase() : "");
                    secuencialVenta.setSecuencial(numeroDocumento);
                    lista.add(secuencialVenta);
                }

            }
        }

        return lista;
    }

    @Override
    public String eliminarVentas(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        susClave = empresa + "|" + periodo + "|" + motivo + "|" + numero;
        susDetalle = "Se eliminó la venta: " + susClave;
        susSuceso = "DELETE";
        susTabla = "inventario.inv_ventas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return ventasDao.eliminarVentas(empresa, periodo, motivo, numero, sisSuceso);
    }

    @Override
    public boolean validarNumeracionSegunSector(String empresa, String tipoDocumento, String codigoSector, String numero) throws Exception {
        /*AnxNumeracion anxNumeracion = ventaDao.obtenerNumeracionPorTipoNumeroDocumentoSector(empresa, tipoDocumento, codigoSector, numero);
        if (anxNumeracion == null) {
            return false;
        }*/
        return true;
    }

    @Override
    public Map<String, Object> validarNumeracionVenta(Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String numeroDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("numeroDocumento"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaVencimiento = UtilsJSON.jsonToObjeto(String.class, map.get("fechaVencimiento"));
        Map<String, Object> campos = new HashMap<>();

        boolean isValido = false;
        boolean isValidoSegunSector = false;
        AnxNumeracionLineaTO numeracion = null;

        if (empresa != null && tipoDocumento != null && numeroDocumento != null) {
            isValido = isNumeroDocumentoValido(empresa, tipoDocumento, numeroDocumento);
            if (codigoSector != null) {
                isValidoSegunSector = validarNumeracionSegunSector(empresa, tipoDocumento, codigoSector, numeroDocumento);
            }

            if (fechaVencimiento != null) {
                numeracion = ventaDao.getNumeroAutorizacion(empresa, numeroDocumento, tipoDocumento, fechaVencimiento);
            }
        }

        campos.put("documentoValido", isValido);
        campos.put("isValidoSegunSector", true);
        campos.put("numeracion", numeracion);
        return campos;
    }

    @Override
    public Date cambiarFechaVencimientoVenta(InvVentasPK vtaPK, Date fecha, SisInfoTO sisInfoTO) throws Exception {
        InvVentas venta = ventasDao.obtenerPorId(InvVentas.class, vtaPK);
        if (venta != null) {
            venta.setVtaFechaVencimiento(fecha);
            susClave = vtaPK.getVtaEmpresa() + "|" + vtaPK.getVtaPeriodo() + "|" + vtaPK.getVtaMotivo() + "|" + vtaPK.getVtaNumero();
            susDetalle = "Se cambió la fecha de vencimiento de la venta: " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_ventas";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ventasDao.actualizar(venta, sisSuceso);
            return venta.getVtaFechaVencimiento();
        }
        return null;
    }

    @Override
    public InvVentasTO getInvVentasTO(String empresa, String tipo, String numeroDocumento) throws Exception {
        return ventasDao.getInvVentasTO(empresa, tipo, numeroDocumento);
    }

    @Override
    public Boolean actualizarClaveExternaVenta(InvVentasPK pk, String clave, SisInfoTO sisInfoTO) throws Exception {
        // / PREPARANDO OBJETO SISSUCESO
        susClave = pk.getVtaPeriodo() + " " + pk.getVtaMotivo() + " "
                + pk.getVtaNumero();
        susDetalle = "Se actualizó la clave externa de la venta con código: " + pk.getVtaPeriodo() + " "
                + pk.getVtaMotivo() + " " + pk.getVtaNumero();
        susTabla = "inventario.inv_ventas";
        susSuceso = "UPDATE";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return ventasDao.actualizarClaveExternaVenta(pk, clave, sisSuceso);
    }
//SALDO POR COBRAR

    @Override
    public List<InvVentas> obtenerListadoVentasSaldosImportados(String empresa, String motivo, String sector, String fecha) throws Exception {
        return ventasDao.obtenerListadoVentasSaldosImportados(empresa, motivo, sector, fecha);
    }

    @Override
    public List<String> verificarExistenciaVentas(List<InvVentas> ventas, SisInfoTO sisInfoTO) throws Exception {
        String empresa = sisInfoTO.getEmpresa();
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();
        try {
            if (ventas != null && !ventas.isEmpty()) {
                //insertar  
                for (int i = 0; i < ventas.size(); i++) {
                    //cliente
                    String identificacion = ventas.get(i).getInvCliente().getCliIdNumero();
                    char tipo = ventas.get(i).getInvCliente().getCliIdNumero().length() == 13 ? 'R' : ventas.get(i).getInvCliente().getCliIdNumero().length() == 10 ? 'C' : 'N';
                    InvCliente clienteEnBD = clienteService.getBuscaCedulaCliente(empresa, identificacion, tipo);
                    if (clienteEnBD == null) {
                        listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + identificacion + " </strong> no existe.");
                    } else {
                        //motivo 
                        boolean existeMotivo = ventasMotivoDao.comprobarInvVentasMotivo(empresa, ventas.get(i).getInvVentasPK().getVtaMotivo());
                        if (!existeMotivo) {
                            listaMensajesEnviar.add("FEl Motivo: <strong class='pl-2'>"
                                    + ventas.get(i).getInvVentasPK().getVtaMotivo()
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
    public Map<String, Object> insertarClientesRezagadosLote(List<InvCliente> clientes, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        Map<String, Object> campos = null;
        String empresa = sisInfoTO.getEmpresa();
        try {
            campos = new HashMap<>();
            if (clientes != null && !clientes.isEmpty()) {
                List<InvClienteCategoriaTO> categorias = clienteCategoriaService.getInvClienteCategoriaTO(empresa);
                List<InvVendedorComboListadoTO> listaVendedores = vendedorService.getComboinvListaVendedorTOs(sisInfoTO.getEmpresa(), null);

                List<InvCliente> listaClientesInsertado = new ArrayList<>();//contiene los que ya estan en BD y los nuevos en BD
                for (int i = 0; i < clientes.size(); i++) {
                    //proveedor
                    boolean clienteRepetido = false;

                    String razonSocial = clientes.get(i).getCliRazonSocial() != null
                            && !clientes.get(i).getCliRazonSocial().equals("")
                            ? clientes.get(i).getCliRazonSocial().toUpperCase()
                            : "";

                    String identificacion = clientes.get(i).getCliIdNumero() != null
                            && !clientes.get(i).getCliIdNumero().equals("")
                            ? clientes.get(i).getCliIdNumero().trim() : null;

                    String mensajeAux = "T";

                    if (identificacion != null) {

                        char tipo = clientes.get(i).getCliIdNumero().length() == 13 ? 'R' : clientes.get(i).getCliIdNumero().length() == 10 ? 'C' : 'N';
                        InvCliente clienteEnBD = clienteService.getBuscaCedulaCliente(empresa, identificacion, tipo);
                        List<InvCliente> fueInsertado = listaClientesInsertado.stream()
                                .filter(item -> item.getCliIdNumero().equals(identificacion)
                                || item.getCliRazonSocial().equals(razonSocial))
                                .collect(Collectors.toList());
                        clienteRepetido = (fueInsertado == null || fueInsertado.isEmpty()) ? false : true;

                        if (clienteEnBD == null) {//no existe en BD
                            if (!clienteRepetido) {
                                if (razonSocial != null) {
                                    //Crear proveedor
                                    InvClienteTO clienteTO = new InvClienteTO();
                                    clienteTO.setCliCodigo("");
                                    clienteTO.setEmpCodigo(empresa);
                                    clienteTO.setCliEstadoCivil("S");
                                    clienteTO.setCliId(identificacion);
                                    clienteTO.setCliRazonSocial(razonSocial);
                                    clienteTO.setCliRelacionado(false);
                                    clienteTO.setCliInactivo(false);
                                    clienteTO.setCliTipoId(tipo);
                                    clienteTO.setCliEmail("");
                                    clienteTO.setCliPrecio((short) 0);
                                    clienteTO.setCliDiasCredito((short) 0);
                                    clienteTO.setCliCupoCredito(new BigDecimal("0.00"));
                                    clienteTO.setUsrFechaInsertaCliente(sisInfoTO.getUsuario());

                                    //establecimiento
                                    //clienteTO.setCliCodigoEstablecimiento("001");
                                    //clienteTO.setCliDireccion(dirEstablecimiento);
                                    if (listaVendedores != null && listaVendedores.size() > 0) {
                                        clienteTO.setVendEmpresa(sisInfoTO.getEmpresa());
                                        clienteTO.setVendCodigo(listaVendedores.get(0).getVendCodigo());
                                        //categoria
                                        List<InvClienteCategoriaTO> listaCategorias = clienteCategoriaService.getInvClienteCategoriaTO(sisInfoTO.getEmpresa());
                                        if (listaCategorias != null && listaCategorias.size() > 0) {
                                            clienteTO.setCliCategoria(listaCategorias.get(0).getCcCodigo());
                                            if (categorias != null && categorias.size() > 0) {
                                                clienteTO.setCliCategoria(categorias.get(0).getCcCodigo());
                                            } else {
                                                mensajeAux = "FNo existe categorías de cliente.";
                                            }
                                        } else {
                                            mensajeAux = "FNo se puede guardar cliente con identificación:" + identificacion
                                                    + ", debido a que no existe categorías de cliente";
                                        }
                                    } else {
                                        mensajeAux = "FNo se puede guardar cliente con identificación:" + identificacion
                                                + ", debido a que no existe vendedores de cliente";
                                    }

                                    String respues = clienteService.insertarInvClienteTO(clienteTO, null, sisInfoTO);;
                                    if (respues != null && respues.substring(0, 1).equals("T")) {
                                        InvCliente cliente = new InvCliente();
                                        cliente.setCliIdNumero(identificacion);
                                        cliente.setInvClientePK(new InvClientePK(empresa, clienteTO.getCliCodigo()));
                                        cliente.setCliRazonSocial(clienteTO.getCliRazonSocial());
                                        listaClientesInsertado.add(cliente);
                                    }
                                    mensajeAux = respues;
                                } else {
                                    mensajeAux = "FEl cliente: <strong class='pl-2'>" + identificacion + " </strong> no existe.Se debe ingresar razon social";
                                }
                            }
                        } else {
                            InvCliente cliente = clienteEnBD;
                            mensajeAux = "FEl cliente <strong>" + razonSocial + "</strong> con identificación:<strong>" + identificacion + "</strong>, ya existe";
                            if (!clienteRepetido) {
                                listaClientesInsertado.add(cliente);
                            }
                            if (!razonSocial.equals(cliente.getCliRazonSocial()) || !identificacion.equals(cliente.getCliIdNumero())) {
                                mensajeAux = "FEl cliente <strong>" + razonSocial + "</strong> con identificación:<strong>" + identificacion + "</strong>, ya se encuentra registrado en la base de datos con la siguiente información: </br>"
                                        + "Identificación: <strong class='pl-2'>" + cliente.getCliIdNumero() + " </strong></br>"
                                        + "Razón social: <strong class='pl-2'>" + cliente.getCliRazonSocial() + " </strong></br>";
                            }
                        }
                    } else {
                        mensajeAux = "FDebe ingresar identificación</br>";
                    }

                    if (!clienteRepetido) {
                        mensaje += mensajeAux + "|";
                    }
                }

                campos.put("mensaje", mensaje);
                campos.put("listaClientesInsertado", listaClientesInsertado);
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
    public MensajeTO insertarInvVentas(InvVentas invVentas, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        boolean periodoCerrado = false;
        MensajeTO mensajeTO = new MensajeTO();
        Integer id = invVentas.getId();
        String empresa = sisInfoTO.getEmpresa();

        //***********
        if (!UtilsValidacion.isFechaSuperior(UtilsValidacion.fecha(invVentas.getVtaFecha(), "yyyy-MM-dd"), "yyyy-MM-dd")) {
            ///// BUSCANDO CLIENTE
            String identificacion = invVentas.getInvCliente().getCliIdNumero();
            char tipo = identificacion.length() == 13 ? 'R' : identificacion.length() == 10 ? 'C' : 'N';
            InvCliente invCliente = clienteService.getBuscaCedulaCliente(empresa, identificacion, tipo);

            if (invCliente != null) {
                comprobar = false;
                List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
                listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);
                for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                    if (invVentas.getVtaFecha().getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                            && invVentas.getVtaFecha().getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                        comprobar = true;
                        invVentas.getInvVentasPK().setVtaPeriodo(sisListaPeriodoTO.getPerCodigo());
                        periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                    }
                }
                if (comprobar) {
                    if (!periodoCerrado) {
                        if (ventasMotivoDao.comprobarInvVentasMotivo(empresa, invVentas.getInvVentasPK().getVtaMotivo())) {
                            /// PREPARANDO OBJETO SISSUCESO (susClave y susDetalle se llenan en DAOTransaccion)
                            susSuceso = "INSERT";
                            susTabla = "inventario.inv_ventas";
                            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                            invVentas.setUsrFechaInserta(UtilsDate.timestamp());
                            invVentas.setInvCliente(invCliente);
                            invVentas.setConEmpresa(null);
                            invVentas.setConNumero(null);
                            invVentas.setConPeriodo(null);
                            invVentas.setConTipo(null);
                            invVentas.setSecEmpresa(empresa);
                            invVentas.setUsrEmpresa(empresa);
                            invVentas.setVtaSaldoImportado(true);
                            invVentas.setVtaFechaVencimiento(
                                    invVentas.getVtaFechaVencimiento() != null ? invVentas.getVtaFechaVencimiento()
                                    : invVentas.getVtaFecha());
                            comprobar = ventasDao.insertarInvVentas(invVentas, sisSuceso);
                            if (comprobar) {
                                SisPeriodo sisPeriodo = periodoService.buscarPeriodo(empresa, invVentas.getInvVentasPK().getVtaPeriodo());
                                retorno = "T<html>Se ingresó la venta con la siguiente información:<br><br>"
                                        + "<center>"
                                        + "Periodo: <font size = 5>"
                                        + sisPeriodo.getPerDetalle()
                                        + "</font>.<br> Motivo: <font size = 5>"
                                        + invVentas.getInvVentasPK().getVtaMotivo()
                                        + "</font>.<br> Número: <font size = 5>"
                                        + invVentas.getInvVentasPK().getVtaNumero()
                                        + "</font>."
                                        + "</center>"
                                        + "</html>"
                                        + invVentas.getInvVentasPK().getVtaNumero()
                                        + "," + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("pk", invVentas.getInvVentasPK());
                                map.put("id", id);
                                mensajeTO.setMap(map);
                            } else {
                                retorno = "F<html>Hubo un error al guardar la Venta...\nIntente de nuevo o contacte con el administrador</html>";
                            }
                        } else {
                            retorno = "F<html>No se encuentra el motivo...</html>";
                        }
                    } else {
                        retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                    }
                } else {
                    retorno = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
                }

            } else {
                retorno = "F<html>El Cliente que escogió ya no está disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }

        mensajeTO.setMensaje(retorno);
        return mensajeTO;

    }

    @Override
    public MensajeTO modificarInvVentas(InvVentas invVentas, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        boolean periodoCerrado = false;
        MensajeTO mensajeTO = new MensajeTO();
        String empresa = sisInfoTO.getEmpresa();

        //***********
        if (!UtilsValidacion.isFechaSuperior(UtilsValidacion.fecha(invVentas.getVtaFecha(), "yyyy-MM-dd"), "yyyy-MM-dd")) {
            ///// BUSCANDO CLIENTE
            String codigo = invVentas.getInvCliente().getInvClientePK().getCliCodigo();
            InvClienteTO invCliente = clienteService.obtenerClienteTO(empresa, codigo);

            if (invCliente != null) {
                comprobar = false;
                List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
                listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);
                for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                    if (invVentas.getVtaFecha().getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                            && invVentas.getVtaFecha().getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                        comprobar = true;
                        invVentas.getInvVentasPK().setVtaPeriodo(sisListaPeriodoTO.getPerCodigo());
                        periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                    }
                }
                if (comprobar) {
                    if (!periodoCerrado) {
                        if (ventasMotivoDao.comprobarInvVentasMotivo(empresa, invVentas.getInvVentasPK().getVtaMotivo())) {
                            /// PREPARANDO OBJETO SISSUCESO (susClave y susDetalle se llenan en DAOTransaccion)
                            susSuceso = "UPDATE";
                            susTabla = "inventario.inv_ventas";
                            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                            invVentas.setUsrFechaInserta(UtilsDate.timestamp());
                            InvCliente cliente = invVentas.getInvCliente();
                            invVentas.setInvCliente(cliente);
                            invVentas.setVtaDocumentoNumero(null);
                            invVentas.setSecEmpresa(empresa);
                            invVentas.setUsrEmpresa(empresa);
                            invVentas.setVtaSaldoImportado(true);
                            invVentas.setVtaFechaVencimiento(invVentas.getVtaFecha());
                            comprobar = ventasDao.modificarInvVentas(invVentas, sisSuceso);
                            if (comprobar) {
                                SisPeriodo sisPeriodo = periodoService.buscarPeriodo(empresa, invVentas.getInvVentasPK().getVtaPeriodo());
                                retorno = "T<html>Se modificar la venta con la siguiente información:<br><br>"
                                        + "<center>"
                                        + "Periodo: <font size = 5>"
                                        + sisPeriodo.getPerDetalle()
                                        + "</font>.<br> Motivo: <font size = 5>"
                                        + invVentas.getInvVentasPK().getVtaMotivo()
                                        + "</font>.<br> Número: <font size = 5>"
                                        + invVentas.getInvVentasPK().getVtaNumero()
                                        + "</font>."
                                        + "</center>"
                                        + "</html>"
                                        + invVentas.getInvVentasPK().getVtaNumero()
                                        + "," + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("venta", invVentas);
                                mensajeTO.setMap(map);
                            } else {
                                retorno = "F<html>Hubo un error al guardar la Venta...\nIntente de nuevo o contacte con el administrador</html>";
                            }
                        } else {
                            retorno = "F<html>No se encuentra el motivo...</html>";
                        }
                    } else {
                        retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                    }
                } else {
                    retorno = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
                }

            } else {
                retorno = "F<html>El Cliente que escogió ya no está disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }

        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public boolean anularContableVentas(InvVentasPK pk, ConContablePK pkContable, SisInfoTO sisInfoTO) throws Exception {
        susDetalle = "Se anuló contable del periodo " + pkContable.getConPeriodo() + ", del tipo contable "
                + pkContable.getConTipo() + ", de la numeracion " + pkContable.getConNumero() + " para la venta: " + pk.getVtaPeriodo() + "|" + pk.getVtaMotivo() + "|" + pk.getVtaNumero();
        susClave = pkContable.getConPeriodo() + " " + pkContable.getConTipo() + " " + pkContable.getConNumero();
        SisSuceso sisSuceso = Suceso.crearSisSuceso("contabilidad.con_contable", susClave, "UPDATE", susDetalle, sisInfoTO);
        boolean anulado = contableDao.anularConContable(pkContable, sisSuceso);
        if (anulado) {
            sisSuceso.setSusClave(pk.getVtaPeriodo() + "|" + pk.getVtaMotivo() + "|" + pk.getVtaNumero());
            sisSuceso.setSusTabla("inventario.inv_ventas");
            return ventaDao.anularConContableVentas(pk, sisSuceso);
        } else {
            return false;
        }
    }

    @Override
    public boolean guardarImagenesVentas(List<InvVentasDatosAdjuntos> imagenes, InvVentasPK pk, SisInfoTO sisInfoTO) throws Exception {
        List<InvVentasDatosAdjuntos> listaImagenes = new ArrayList<>();
        List<SisSuceso> listaSucesos = new ArrayList<>();
        if (imagenes != null && imagenes.size() > 0) {
            for (InvVentasDatosAdjuntos item : imagenes) {
                /// PREPARANDO OBJETO SISSUCESO
                item.setInvVentas(new InvVentas(pk));
                susClave = pk.getVtaEmpresa() + "|" + pk.getVtaPeriodo() + "|" + pk.getVtaMotivo() + "|" + pk.getVtaNumero();
                if (item.getAdjSecuencial() == null) {
                    item.setAdjSecuencial(null);
                    susDetalle = "Se insertó la imágen para el contable: " + susClave;
                    susSuceso = "INSERT";
                } else {
                    item.setAdjSecuencial(item.getAdjSecuencial());
                    susDetalle = "Se modificó la imágen para el contable: " + susClave;
                    susSuceso = "UPDATE";
                }
                listaImagenes.add(item);
                //suceso
                susTabla = "contabilidad.con_contable_datos_adjuntos";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                listaSucesos.add(sisSuceso);
            }
        }
        ventasDao.actualizarImagenesVenta(listaImagenes, pk, listaSucesos);
        return true;
    }

    @Override
    public List<InvVentasDatosAdjuntos> listarImagenesDeVenta(InvVentasPK pk) throws Exception {
        return ventasDao.listarImagenesDeVenta(pk);
    }

    @Override
    public Map<String, Object> consultarVentaSegunContable(Map<String, Object> map) throws Exception {
        InvVentas venta = null;
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String conPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("conPeriodo"));
        String conTipo = UtilsJSON.jsonToObjeto(String.class, map.get("conTipo"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        String sectorSeleccionado = "";
        venta = ventasDao.obtenerVentaPorContable(empresa, conPeriodo, conTipo, conNumero);
        if (venta != null) {
            InvVentasTO ventasTO = ConversionesInventario.convertirInvVentas_InvVentasTO(venta);

            InvVentaCabeceraTO invVentaCabeceraTO = getInvVentaCabeceraTO(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());

            List<SisPeriodo> listadoPeriodos = periodoService.getListaPeriodo(empresa);
            List<InvVentaMotivoTO> listadoMotivos = ventasMotivoDao.getListaInvVentasMotivoTO(empresa, true, ventasTO.getVtaDocumentoTipo());
            List<InvVentasFormaCobroTO> listadoFormaCobro = ventaFormaCobroDao.getListaInvVentasFormaCobroTO(empresa, false);
            List<AnxTipoComprobanteComboTO> listadoTipoComprobante = tipoComprobanteDao.getListaAnxTipoComprobanteComboTO(null);
            List<InvListaBodegasTO> listadoBodegas = bodegaDao.buscarBodegasTO(empresa, false, null);
            List<InvVendedorComboListadoTO> listaVendedores = vendedorService.getComboinvListaVendedorTOs(empresa, null);

            campos.put("listadoPeriodos", listadoPeriodos);
            campos.put("listadoMotivos", listadoMotivos);
            campos.put("listadoFormaCobro", listadoFormaCobro);
            campos.put("listadoTipoComprobante", listadoTipoComprobante);
            campos.put("listadoBodegas", listadoBodegas);
            campos.put("listadoVendedores", listaVendedores);

            long diferenciaMiliSegundos = UtilsDate.fechaFormatoDate(venta.getVtaFechaVencimiento(), "yyyy-MM-dd").getTime() - UtilsDate.fechaFormatoDate(ventasTO.getVtaFecha(), "yyyy-MM-dd").getTime();
            long diasCredito = diferenciaMiliSegundos / (1000 * 60 * 60 * 24);
            InvClienteTO clienteTO = clienteDao.obtenerClienteTO(empresa, ventasTO.getCliCodigo());
            InvFunListadoClientesTO cliente = new InvFunListadoClientesTO();
            cliente.setCliId(clienteTO.getCliId());
            cliente.setCliCodigo(clienteTO.getCliCodigo());
            cliente.setCliRazonSocial(clienteTO.getCliRazonSocial());
            cliente.setCliPrecio(clienteTO.getCliPrecio() != null ? clienteTO.getCliPrecio().intValue() : 0);
            cliente.setCliDireccion(clienteTO.getCliDireccion());
            cliente.setCliCiudad(clienteTO.getCliCiudad());
            if (ventasTO.getCliCodigoEstablecimiento() != null && !ventasTO.getCliCodigoEstablecimiento().equals("") && !clienteTO.getCliCodigoEstablecimiento().equals(ventasTO.getCliCodigoEstablecimiento())) {
                InvClientesDirecciones direccion = clientesDireccionesDao.obtenerDireccion(empresa, ventasTO.getCliCodigoEstablecimiento(), ventasTO.getCliCodigo());
                if (direccion != null) {
                    cliente.setCliDireccion(direccion.getDirDetalle());
                }
            }
            if (clienteTO.getCliDiasCredito() != null) {
                int diasCreditoEntero = Integer.parseInt(clienteTO.getCliDiasCredito() + "");
                cliente.setCliDiasCredito(diasCreditoEntero);
            }
            //liquidacion ventas
            List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTO = new ArrayList<>();
            List<InvVentasLiquidacion> listaInvVentasLiquidacion = ventasLiquidacionDao.getListInvVentasLiquidacion(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
            if (listaInvVentasLiquidacion != null && listaInvVentasLiquidacion.size() > 0) {
                for (int i = 0; i < listaInvVentasLiquidacion.size(); i++) {
                    InvVentasLiquidacionTO item = new InvVentasLiquidacionTO();
                    item.setDetSecuencia(listaInvVentasLiquidacion.get(i).getDetSecuencial());
                    item.setVtaEmpresa(listaInvVentasLiquidacion.get(i).getInvVentas().getInvVentasPK().getVtaEmpresa());
                    item.setVtaPeriodo(listaInvVentasLiquidacion.get(i).getInvVentas().getInvVentasPK().getVtaPeriodo());
                    item.setVtaMotivo(listaInvVentasLiquidacion.get(i).getInvVentas().getInvVentasPK().getVtaMotivo());
                    item.setVtaNumero(listaInvVentasLiquidacion.get(i).getInvVentas().getInvVentasPK().getVtaNumero());
                    item.setLiqEmpresa(listaInvVentasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqEmpresa());
                    item.setLiqMotivo(listaInvVentasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqMotivo());
                    item.setLiqNumero(listaInvVentasLiquidacion.get(i).getPrdLiquidacion().getPrdLiquidacionPK().getLiqNumero());
                    item.setLiqTotal(listaInvVentasLiquidacion.get(i).getLiqTotal());
                    item.setLiqFecha(UtilsValidacion.fecha(listaInvVentasLiquidacion.get(i).getPrdLiquidacion().getLiqFecha(), "yyyy-MM-dd"));
                    listaInvVentasLiquidacionTO.add(item);
                }
            }
            List<InvVentasDetalle> listaDetalle = ventasDao.obtenerVentaDetallePorNumero(venta.getInvVentasPK().getVtaEmpresa(), venta.getInvVentasPK().getVtaPeriodo(), venta.getInvVentasPK().getVtaMotivo(), venta.getInvVentasPK().getVtaNumero());
            List<InvVentasDetalleTO> listaInvVentasDetalleTO = new ArrayList<>();
            for (InvVentasDetalle detalle : listaDetalle) {
                List<InvVentasDetalleSeries> listaSerie = ventasDetalleSeriesDao.listarSeriesPorSecuencialDetalle(detalle.getDetSecuencial());
                if (listaSerie != null && !listaDetalle.isEmpty()) {
                    detalle.setInvVentasDetalleSeriesList(listaSerie);
                } else {
                    detalle.setInvVentasDetalleSeriesList(new ArrayList<>());
                }
                InvVentasDetalleTO detalleTO = ConversionesInventario.convertirInvVentasDetalle_InvVentasDetalleTO(detalle);
                sectorSeleccionado = detalleTO.getSecCodigo();
                if (listaInvVentasLiquidacionTO.size() > 0) {
                    detalleTO.setSectorLiq(detalleTO.getPisSector());
                    detalleTO.setPisNumeroLiq(detalleTO.getPisNumero());
                }
                detalleTO.setInvVentasDetalleComplementarioList(new ArrayList<>());
                //BUSCAR FORMULA PRODUCTO
                for (InvVentasDetalle prod : listaDetalle) {
                    if (!Objects.equals(prod.getDetSecuencial(), detalle.getDetSecuencial())) {
                        if (Objects.equals(detalle.getDetSecuencial(), prod.getProComplementario())) {
                            prod.setInvVentasDetalleSeriesList(detalle.getInvVentasDetalleSeriesList());
                            InvVentasDetalleTO detalleComplTO = ConversionesInventario.convertirInvVentasDetalle_InvVentasDetalleTO(prod);
                            BigDecimal cantidadInicial = detalleComplTO.getDetCantidad().divide(detalleTO.getDetCantidad(), 6, RoundingMode.HALF_UP);
                            detalleComplTO.setDetCantidad(cantidadInicial);
                            detalleTO.getInvVentasDetalleComplementarioList().add(detalleComplTO);
                        }
                    }

                }
                //*****************************
                if (detalleTO.getProComplementario() == null) {
                    listaInvVentasDetalleTO.add(detalleTO);
                }
            }
            String estadoComprobanteAutorizacionElectronica = ventaElectronicaDao.comprobarAnxVentaElectronicaAutorizacion(empresa, venta.getInvVentasPK().getVtaPeriodo(), venta.getInvVentasPK().getVtaMotivo(), venta.getInvVentasPK().getVtaNumero());
            boolean estaAutorizada = estadoComprobanteAutorizacionElectronica != null && estadoComprobanteAutorizacionElectronica.equals("AUTORIZADO");
            AnxVentaTO anxVentaTO = ventaDao.getAnexoVentaTO(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
            AnxVentaElectronica anxVentaElectronica = ventaDao.getAnexoVentaElectronicaTO(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());

            /*comprobante electronico*/
            boolean puedeAnular = true;
            if (anxVentaElectronica != null) {// && quiereanular
                if (anxVentaElectronica.getEAutorizacionNumero() != null && anxVentaElectronica.getEAutorizacionNumero().length() == 49) {
                    try {
                        RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(anxVentaElectronica.getEAutorizacionNumero(), TipoAmbienteEnum.PRODUCCION.getCode(), sisInfoTO);
                        if (respuestaComprobante != null && respuestaComprobante.getAutorizaciones() != null
                                && respuestaComprobante.getAutorizaciones().getAutorizacion() != null && !respuestaComprobante.getAutorizaciones().getAutorizacion().isEmpty()) {
                            puedeAnular = false;
                        }
                    } catch (Exception e) {
                        campos.put("mensajeAnulacion", "Ocurrió un error al consultar el estado del documento en el SRI. Puede ser que este documento no se encuentre anulado en el SRI");
                        puedeAnular = false;
                    }
                }
            }

            InvVentasComplementoTO invVentasComplemento = ventasComplementoDao.buscarVentasComplementoTO(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
            if (sectorSeleccionado != null) {
                List<PrdListaPiscinaTO> listadoPiscinas = piscinaDao.getListaPiscina(empresa, sectorSeleccionado);
                campos.put("listadoPiscinas", listadoPiscinas);
            }
            SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(UtilsDate.fechaFormatoDate(venta.getVtaFecha()), empresa);
            AnxNumeracionLineaTO anxNumeracionLineaTO = ventaDao.getNumeroAutorizacion(empresa, venta.getVtaDocumentoNumero(), venta.getVtaDocumentoTipo(), ventasTO.getVtaFecha());
            //Retencion
            InvVentaRetencionesTO invVentaRetencionesTO = getInvVentaRetencionesTO(empresa, venta.getVtaDocumentoNumero());
            if (invVentaRetencionesTO != null && invVentaRetencionesTO.getVenMotivo().equals(venta.getInvVentasPK().getVtaMotivo())
                    && invVentaRetencionesTO.getVenPeriodo().equals(venta.getInvVentasPK().getVtaPeriodo())
                    && invVentaRetencionesTO.getVenNumero().equals(venta.getInvVentasPK().getVtaNumero())) {
            } else {
                invVentaRetencionesTO = null;
            }

            //Exportacion
            AnxVentaExportacion exportacion = exportacionesService.obtenerAnxVentaExportacion(empresa, ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero());
            AnxVentaExportacionTO exportacionTO = null;
            if (exportacion != null) {
                exportacionTO = new AnxVentaExportacionTO();
                String fecha = UtilsValidacion.fecha(exportacion.getExpFechaExportacion(), "yyyy-MM-dd");
                exportacionTO.setExpFechaExportacion(fecha);
                exportacionTO.setExpImpuestoPagadoExterior(exportacion.getExpImpuestoPagadoExterior());
                exportacionTO.setExpObservaciones(exportacion.getExpObservaciones());
                exportacionTO.setExpPaisEfectuaExportacion(exportacion.getExpPaisEfectuaExportacion());
                exportacionTO.setExpParaisoFiscal(exportacion.getExpParaisoFiscal());
                exportacionTO.setExpRefrendoAnio(exportacion.getExpRefrendoAnio());
                exportacionTO.setExpRefrendoCorrelativo(exportacion.getExpRefrendoCorrelativo());
                exportacionTO.setExpRefrendoDistrito(exportacion.getExpRefrendoDistrito());
                exportacionTO.setExpRefrendoDocumentoTransporte(exportacion.getExpRefrendoDocumentoTransporte());
                exportacionTO.setExpRefrendoRegimen(exportacion.getExpRefrendoRegimen());
                exportacionTO.setExpRegimenFiscalPreferente(exportacion.getExpRegimenFiscalPreferente());
                exportacionTO.setExpRegimenGeneral(exportacion.getExpRegimenGeneral());
                exportacionTO.setExpSecuencial(exportacion.getExpSecuencial());
                exportacionTO.setExpTipoExportacion(exportacion.getExpTipoExportacion());
                exportacionTO.setExpTipoIngresoExterior(exportacion.getExpTipoIngresoExterior());
                exportacionTO.setExpTipoRegimenFiscal(exportacion.getExpTipoRegimenFiscal());
                exportacionTO.setExpValorFobExterior(exportacion.getExpValorFobExterior());
                exportacionTO.setExpValorFobLocal(exportacion.getExpValorFobLocal());
                exportacionTO.setVtaEmpresa(exportacion.getVtaEmpresa());
                exportacionTO.setVtaPeriodo(exportacion.getVtaPeriodo());
                exportacionTO.setVtaMotivo(exportacion.getVtaMotivo());
                exportacionTO.setVtaNumero(exportacion.getVtaNumero());
                boolean fueGravado = false;
                if (exportacion.getExpImpuestoPagadoExterior() != null && exportacion.getExpImpuestoPagadoExterior().compareTo(BigDecimal.ZERO) != 0) {
                    fueGravado = true;
                }
                exportacionTO.setExpIngresoExteriorFueGravado(fueGravado);
            }
            //reembolso
            if (venta != null && venta.getInvVentasPK() != null) {
                List<AnxVentaReembolsoTO> listaAnxVentaReembolsoTO = anxVentaReembolsoService.getListaAnxVentaReembolsoTO(
                        venta.getInvVentasPK().getVtaEmpresa(),
                        venta.getInvVentasPK().getVtaPeriodo(),
                        venta.getInvVentasPK().getVtaMotivo(),
                        venta.getInvVentasPK().getVtaNumero());
                if (listaAnxVentaReembolsoTO != null && listaAnxVentaReembolsoTO.size() > 0) {
                    for (AnxVentaReembolsoTO item : listaAnxVentaReembolsoTO) {
                        item.setProvCodigoCopia(item.getProvCodigo());
                        item.setReembFechaemision(UtilsValidacion.fecha(item.getReembFechaemision(), "yyyy-MM-dd", "dd-MM-yyyy"));
                    }
                }
                campos.put("listaAnxVentaReembolsoTO", listaAnxVentaReembolsoTO);
            }

            campos.put("exportacionTO", exportacionTO);
            campos.put("ventasTO", ventasTO);
            campos.put("puedeAnular", puedeAnular);
            campos.put("invVentaCabeceraTO", invVentaCabeceraTO);
            campos.put("invVentaRetencionesTO", invVentaRetencionesTO);
            campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
            campos.put("diasCredito", diasCredito);
            campos.put("cliente", cliente);
            campos.put("listaInvVentasDetalleTO", listaInvVentasDetalleTO);
            campos.put("anxVentaTO", anxVentaTO);
            campos.put("invVentasComplemento", invVentasComplemento);
            campos.put("anxNumeracionLineaTO", anxNumeracionLineaTO);
            campos.put("estaAutorizada", estaAutorizada);
            campos.put("listaInvVentasLiquidacionTO", listaInvVentasLiquidacionTO);
            return campos;
        } else {
            throw new GeneralException("No se encontraron resultados.");
        }
    }

}
