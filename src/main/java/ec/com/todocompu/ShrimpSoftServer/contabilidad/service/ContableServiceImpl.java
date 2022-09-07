package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoCuentaDao;
import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequeService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.banco.service.CuentaService;
import ec.com.todocompu.ShrimpSoftServer.cartera.report.ReporteCarteraService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.CobrosService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.PagosService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstructuraDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.helper.BalanceCentroProduccionNombres;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.helper.NumeroColumnaDesconocidadBalanceResultadoMensualizado;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.report.ReporteContabilidadService;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.report.ReporteInventarioService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.CorridaDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PiscinaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.ParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.RolMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.AnticipoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.BonoConceptoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.BonoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.FormaPagoBeneficioSocialService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.FormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.HorasExtrasConceptoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.HorasExtrasService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.PrestamoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.PrestamoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RolService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.UtilidadService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.VacacionesService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XiiiSueldoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XivSueldoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEmpresaNotificacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioDetalleService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxDiferenciasTributacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosAntiguoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosVsInventarioDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDiarioAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceCentroProduccionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceGeneralNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceResultadosNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunIPPComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunIPPTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaBalanceResultadosVsInventarioTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaContableDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosCuentasInconsistentes;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresCompra;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresConsumo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ItemListaContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ItemStatusItemListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.PersonaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConAdjuntosContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContableCierreResultado;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConVerificacionErrores;
import ec.com.todocompu.ShrimpSoftUtils.delegate.BancoDelegate;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductosConErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaCostoVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridasInconsistentesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSaldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TipoRRHH;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBono;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtras;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasConcepto;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldo;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoInnecesarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioEmailTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

@Service
public class ContableServiceImpl implements ContableService {

    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private EstructuraDao estructuraDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private PiscinaService piscinaService;
    @Autowired
    private GenericoDao<SisPeriodo, SisPeriodoPK> sisPeriodoDao;
    @Autowired
    private GenericoDao<ConContable, ConContablePK> conContableDao;
    @Autowired
    private GenericoDao<PrdSector, PrdSectorPK> prdSectorDao;
    @Autowired
    private GenericoDao<PrdPiscina, PrdPiscinaPK> prdPiscinaDao;
    @Autowired
    private GenericoDao<ConCuentas, ConCuentasPK> conCuentaDao;
    @Autowired
    private ParametrosDao parametrosDao;
    @Autowired
    private DetalleService detalleService;
    @Autowired
    private ChequeService chequeService;
    @Autowired
    private CorridaDao corridaDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private UtilidadService utilidadService;
    @Autowired
    private AnticipoService anticipoService;
    @Autowired
    private RolService rolService;
    @Autowired
    private BonoService bonoService;
    @Autowired
    private HorasExtrasService horasExtrasService;
    @Autowired
    private BonoConceptoService bonoConceptoService;
    @Autowired
    private HorasExtrasConceptoService horasExtrasConceptoService;
    @Autowired
    private PrestamoService prestamoService;
    @Autowired
    private VacacionesService vacacionesService;
    @Autowired
    private XiiiSueldoService xiiiSueldoService;
    @Autowired
    private XivSueldoService xivSueldoService;
    @Autowired
    private FormaPagoService formaPagoService;
    @Autowired
    private FormaPagoBeneficioSocialService formaPagoBeneficiosSocialesService;
    @Autowired
    private PrestamoMotivoService prestamoMotivoService;
    @Autowired
    private ProductosEnProcesoErroresService productosEnProcesoErroresService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private RolMotivoDao rolMotivoDao;
    @Autowired
    private ConContableCierreResultadosService conContableCierreResultadosService;
    @Autowired
    private BancoCuentaDao bancoCuentaDao;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private GenericoDao<ConAdjuntosContable, Integer> conAdjuntosContableDao;
    @Autowired
    private SisEmpresaNotificacionesDao sisEmpresaNotificacionesDao;
    @Autowired
    private CobrosService cobrosService;
    @Autowired
    private PagosService pagosService;
    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private ReporteContabilidadService reporteContabilidadService;
    @Autowired
    private ReporteInventarioService reporteInventarioService;
    @Autowired
    private ReporteCarteraService reporteCarteraService;
    @Autowired
    private EnviarCorreoService enviarCorreoService;
    @Autowired
    private VerificacionService verificacionService;
    @Autowired
    private ComprasDao comprasDao;
    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public void actualizar(ConContable conContable) {
        contableDao.saveOrUpdate(conContable);
    }

    @Override
    public boolean insertarTransaccionContableCompra(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, ConNumeracion conNumeracion, InvCompras invCompras, SisInfoTO sisInfoTO)
            throws Exception {
        return contableDao.insertarTransaccionContable(conContable, listaConDetalle, sisSuceso, conNumeracion, null,
                null, null, null, null, null, invCompras, null, null, null, null, null, null, null, null, sisInfoTO);
    }

    @Override
    public boolean insertarTransaccionContableVenta(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, ConNumeracion conNumeracion, List<InvVentas> listInvVentas, SisInfoTO sisInfoTO)
            throws Exception {
        return contableDao.insertarTransaccionContable(conContable, listaConDetalle, sisSuceso, conNumeracion, null,
                null, null, null, null, null, null, null, null, null, null, listInvVentas, null, null, null, sisInfoTO);
    }

    @Override
    public ConContable obtenerPorId(String empresa, String periodo, String tipoCodigo, String conNumero) {
        return contableDao.obtenerPorId(ConContable.class, new ConContablePK(empresa, periodo, tipoCodigo, conNumero));
    }

    @Override
    public List<ConFunContabilizarComprasDetalleTO> getConFunContabilizarComprasDetalle(String empresa, String periodo,
            String motivo, String compraNumero, String validar) throws Exception {
        return contableDao.getConFunContabilizarComprasDetalle(empresa, periodo, motivo, compraNumero, validar);
    }

    @Override
    public List<ConFunContabilizarVentasDetalleTO> getConFunContabilizarVentasDetalle(String empresa, String vtaPeriodo,
            String vtaMotivo, String vtaNumero, String validar) throws Exception {
        return contableDao.getConFunContabilizarVentasDetalle(empresa, vtaPeriodo, vtaMotivo, vtaNumero, validar);
    }

    @Override
    public List<ConContableTO> getListaConContableTO(String empresa, String perCodigo, String tipCodigo,
            String numContable) throws Exception {
        return contableDao.getListaConContableTO(empresa, perCodigo, tipCodigo, numContable);
    }

    @Override
    public List<ConFunBalanceResultadosNecTO> getConEstadoResultadosIntegralTO(String empresa, String sector,
            String fechaDesde, String fechaHasta, Boolean ocultarDetalle, Boolean excluirAsientoCierre) throws Exception {
        return contableDao.getConEstadoResultadosIntegralTO(empresa, sector, fechaDesde, fechaHasta, ocultarDetalle, excluirAsientoCierre);
    }

    @Override
    public List<ConBalanceResultadoComparativoTO> getConBalanceResultadoComparativoTO(String empresa, String sector, String fechaDesde,
            String fechaHasta, String fechaDesde2, String fechaHasta2, Boolean ocultarDetalle, Boolean excluirCierre) throws Exception {
        return contableDao.getConBalanceResultadoComparativoTO(empresa, sector, fechaDesde, fechaHasta, fechaDesde2, fechaHasta2, ocultarDetalle, excluirCierre);
    }

    @Override
    public List<ConBalanceResultadoTO> getConFunBalanceFlujoEfectivo(String empresa, String sector, String fechaDesde,
            String fechaHasta) throws Exception {
        return contableDao.getConFunBalanceFlujoEfectivo(empresa, sector, fechaDesde, fechaHasta);
    }

    @Override
    public List<ConFunBalanceGeneralNecTO> getConEstadoSituacionFinancieraTO(String empresa, String sector, String fecha,
            Boolean ocultar, Boolean ocultarDetalle) throws Exception {
        return contableDao.getConEstadoSituacionFinancieraTO(empresa, sector, fecha, ocultar, ocultarDetalle);
    }

    @Override
    public List<ConFunBalanceGeneralNecTO> getFunCuentasSobregiradasTO(String empresa, String secCodigo, String fecha)
            throws Exception {
        return contableDao.getFunCuentasSobregiradasTO(empresa, secCodigo, fecha);
    }

    @Override
    public List<ConBalanceGeneralComparativoTO> getFunBalanceGeneralComparativoTO(String empresa, String secCodigo,
            String fechaAnterior, String fechaActual, Boolean ocultar) throws Exception {
        return contableDao.getFunBalanceGeneralComparativoTO(empresa, secCodigo, fechaAnterior, fechaActual, ocultar);
    }

    @Override
    public List<ConBalanceComprobacionTO> getListaBalanceComprobacionTO(String empresa, String codigoSector,
            String fechaInicio, String fechaFin, Boolean ocultarDetalle) throws Exception {
        return contableDao.getListaBalanceComprobacionTO(empresa, codigoSector, fechaInicio, fechaFin, ocultarDetalle);
    }

    public List<ConBalanceResultadoTO> getListaBalanceResultadoTO(String empresa, String codigoSector,
            String fechaInicio, String fechaFin, Boolean ocultarDetalle) throws Exception {
        return contableDao.getListaBalanceResultadoTO(empresa, codigoSector, fechaInicio, fechaFin, ocultarDetalle);
    }

    @Override
    public List<ConMayorAuxiliarTO> getListaMayorAuxiliarTO(String empresa, String codigoCuentaDesde,
            String codigoCuentaHasta, String fechaInicio, String fechaFin, String sector, boolean estado) throws Exception {
        return contableDao.getListaMayorAuxiliarTO(empresa, codigoCuentaDesde, codigoCuentaHasta, fechaInicio, fechaFin,
                sector, estado);
    }

    @Override
    public List<ConMayorGeneralTO> getListaMayorGeneralTO(String empresa, String codigoSector, String codigoCuenta,
            String fechaFin) throws Exception {
        return contableDao.getListaMayorGeneralTO(empresa, codigoSector, codigoCuenta, fechaFin);
    }

    @Override
    public List<ConDiarioAuxiliarTO> getListaDiarioAuxiliarTO(String empresa, String codigoTipo, String fechaInicio,
            String fechaFin) throws Exception {
        return contableDao.getListaDiarioAuxiliarTO(empresa, codigoTipo, fechaInicio, fechaFin);
    }

    @Override
    public RetornoTO getConBalanceBalanceCentroProduccionTO(String empresa, String fechaDesde, String fechaHasta, Boolean ocultarDetalle, Boolean excluirAsientoCierre) throws Exception {
        RetornoTO retornoTO = new RetornoTO();
        List<ConFunBalanceCentroProduccionTO> conBalanceResultadosMensualizadosTOs = contableDao.getConBalanceBalanceCentroProduccionTO(empresa, fechaDesde, fechaHasta, ocultarDetalle, excluirAsientoCierre);
        String mensaje = "T";
        BalanceCentroProduccionNombres obj = new BalanceCentroProduccionNombres();
        obj.agruparCabeceraColumnas(conBalanceResultadosMensualizadosTOs);
        obj.agruparCuentas(conBalanceResultadosMensualizadosTOs);
        obj.llenarObjetoParaTabla(conBalanceResultadosMensualizadosTOs);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public MensajeTO insertarConContable(ConContableTO conContableTO, List<ConDetalleTO> listaConDetalleTO,
            SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensaje = new ArrayList<String>(1);
        String mensajeAux = "";
        boolean periodoCerrado = false;
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(conContableTO.getEmpCodigo());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(conContableTO.getConFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(conContableTO.getConFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                conContableTO.setPerCodigo(sisListaPeriodoTO.getPerCodigo());
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (comprobar) {
            if (!periodoCerrado) {
                if (tipoDao.buscarTipoContable(conContableTO.getEmpCodigo(), conContableTO.getTipCodigo())) {
                    ///// CREANDO UN SUCESO
                    susClave = conContableTO.getPerCodigo() + " " + conContableTO.getTipCodigo() + " "
                            + conContableTO.getConNumero();
                    susSuceso = "INSERT";
                    susTabla = "con_contable";
                    susDetalle = "Se inserto contable del periodo " + conContableTO.getPerCodigo()
                            + ", del tipo contable " + conContableTO.getTipCodigo() + ", de la numeracion "
                            + conContableTO.getConNumero();
                    sisInfoTO.setEmpresa(conContableTO.getEmpCodigo());
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);
                    conContableTO.setUsrFechaInsertaContable(
                            UtilsValidacion.fecha(sisSuceso.getSusFecha(), "yyyy-MM-dd HH:mm:ss"));
                    ////// CREANDO NUMERACION
                    ConNumeracion conNumeracion = new ConNumeracion(
                            new ConNumeracionPK(conContableTO.getEmpCodigo(), conContableTO.getPerCodigo(),
                                    conContableTO.getTipCodigo()));
                    ////// CREANDO CONTABLE
                    ConContable conContable = ConversionesContabilidad
                            .convertirConContableTO_ConContable(conContableTO);
                    ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                    List<ConDetalle> listConDetalle = new ArrayList<ConDetalle>(0);
                    ConDetalle conDetalle = null;
                    for (ConDetalleTO conDetalleTO : listaConDetalleTO) {
                        conDetalle = new ConDetalle();
                        conDetalleTO.setPerCodigo(conContableTO.getPerCodigo());
                        // conDetalleTO.setPerCodigo(conContableTO.getPerCodigo());
                        conDetalle = ConversionesContabilidad.convertirConDetalleTO_ConDetalle(conDetalleTO);
                        conDetalle.setConContable(conContable);
                        ConCuentas conCuentas = cuentasDao.obtenerPorId(ConCuentas.class,
                                new ConCuentasPK(conDetalleTO.getEmpCodigo(), conDetalleTO.getConCtaCodigo()));
                        if (conCuentas != null) {
                            conDetalle.setConCuentas(conCuentas);
                            listConDetalle.add(conDetalle);
                        } else {
                            mensajeAux = "F<html>No se encontraron las cuentas contables en la(s) línea(s):</html>";
                            mensaje.add(conDetalleTO.getDetOrden().toString() + " - "
                                    + conDetalleTO.getConCtaCodigo().toString());
                        }
                    }
                    if (mensaje.isEmpty()) {
                        comprobar = contableDao.insertarTransaccionContable(conContable, listConDetalle, sisSuceso,
                                conNumeracion, null, null, null, null, null, null, null, null, null, null, null,
                                null, null, null, null, sisInfoTO);
                        if (comprobar) {
                            SisPeriodo sisPeriodo = periodoService.buscarPeriodo(conContableTO.getEmpCodigo(),
                                    conContable.getConContablePK().getConPeriodo());

                            ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(
                                    conContableTO.getEmpCodigo(), conContable.getConContablePK().getConTipo()));

                            mensajeAux = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                                    + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                    + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                    + "</font>.<br> Número: <font size = 5>"
                                    + conContable.getConContablePK().getConNumero() + "</font>.</html>, "
                                    + conContable.getConContablePK().getConPeriodo() + ", "
                                    + conContable.getConContablePK().getConNumero();
                            mensajeTO.setFechaCreacion(
                                    UtilsValidacion.fecha(conContable.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                        } else {
                            mensajeAux = "F<html>Ocurrió un error al guardar el contable, inténtelo de nuevo...</html>";
                        }
                    } else {
                        mensajeTO.setMensaje(mensajeAux);
                        mensajeTO.setListaErrores1(mensaje);
                    }
                } else {
                    mensajeAux = "F<html>No se encuentra tipo de contable...</html>";
                }
            } else {
                mensajeAux = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
            }
        } else {
            mensajeAux = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
        }
        mensajeTO.setMensaje(mensajeAux);
        return mensajeTO;
    }

    /// metodo pendiente para validar contables no esta terminado
    public String validarDetalleContable(String empresa, List<ConDetalleTO> listaConDetalleTO, SisInfoTO sisInfoTO) throws Exception {
        List<String> paraCompararRepetidos = new ArrayList<String>();
        String retornoMensaje = "";
        List<String> cuentasContables = cuentaService.getBanCuentasContablesBancos(empresa);

        List<PrdListaSectorTO> listBuscarSectorTO = sectorService.getListaSectorTO(empresa, false);
        List<PrdListaPiscinaTO> listBuscarPiscinaTO = null;
        int cont = 0;
        int cont_sec = 0;
        for (ConDetalleTO conDetalleTO : listaConDetalleTO) {

            boolean terminarCiclo = false;

            if (conDetalleTO.getConCtaCodigo().trim().isEmpty()) {
                retornoMensaje = "FNo ha ingresado CUENTA CONTABLE_servidor";
                terminarCiclo = true;
            } else if (conDetalleTO.getSecCodigo().trim().isEmpty()) {
                retornoMensaje = "FNo ha ingresado CP_servidor";
                terminarCiclo = true;
            } else if (!terminarCiclo) {

                boolean buscarSector = false;
                for (PrdListaSectorTO prdListaSectorTO : listBuscarSectorTO) {
                    if (prdListaSectorTO.getSecCodigo().compareToIgnoreCase(conDetalleTO.getSecCodigo()) == 0) {
                        buscarSector = true;
                        break;
                    }
                }
                if (!buscarSector) {
                    retornoMensaje = "FNo se encuentra CP_servidor";
                    terminarCiclo = true;
                    break;
                } else {

                    if (listBuscarPiscinaTO == null || listBuscarPiscinaTO.isEmpty()
                            || (listBuscarPiscinaTO.get(0).getPisSector() != null && listBuscarPiscinaTO.get(0)
                            .getPisSector().compareTo(conDetalleTO.getSecCodigo()) != 0)) {

                        cont_sec = 0;
                        listBuscarPiscinaTO = piscinaService.getListaPiscinaTO(empresa.trim(),
                                conDetalleTO.getSecCodigo());
                    }
                    cont++;
                    cont_sec++;

                    boolean buscarPiscina = false;

                    if (listBuscarPiscinaTO != null) {

                    }
                    for (PrdListaPiscinaTO prdListaPiscinaTO : listBuscarPiscinaTO) {
                        if (prdListaPiscinaTO.getPisNumero() != null && prdListaPiscinaTO.getPisNumero()
                                .compareToIgnoreCase(conDetalleTO.getPisNumero()) == 0) {
                            buscarPiscina = true;
                            break;
                        }

                    }

                    if (!buscarPiscina) {
                        retornoMensaje = "FNo se encuentra CC (" + conDetalleTO.getPisNumero() + ") en el CP ("
                                + conDetalleTO.getSecCodigo() + ") _servidor";
                        terminarCiclo = true;
                        break;
                    }

                }

            }

            for (int j = 0; j < cuentasContables.size(); j++) {
                if (conDetalleTO.getConCtaCodigo().equals(cuentasContables.get(j))
                        && !conDetalleTO.getDetDocumento().trim().isEmpty()) {
                    paraCompararRepetidos.add(conDetalleTO.getDetDocumento());
                    if (BancoDelegate.getInstance().isExisteChequeAimprimir(empresa, conDetalleTO.getConCtaCodigo(),
                            conDetalleTO.getDetDocumento(), null, sisInfoTO)) {
                        retornoMensaje = "El cheque número: " + conDetalleTO.getDetDocumento() + " ya existe.";
                        terminarCiclo = true;
                        break;
                    }
                }
            }

            if (terminarCiclo) {
                break;
            }

        }
        /// verificar Cheques repetidos en el Detalle
        int contadorRepetidos = 0;
        Collections.sort(paraCompararRepetidos);
        for (int i = 0; i < paraCompararRepetidos.size(); i++) {
            for (int j = (i + 1); j < paraCompararRepetidos.size(); j++) {
                if (paraCompararRepetidos.get(i).equals(paraCompararRepetidos.get(j))) {
                    contadorRepetidos++;
                }
            }
        }
        if (contadorRepetidos > 0) {
            retornoMensaje = "FExisten CHEQUES REPETIDOS, en los CHEQUES que ha ingresado";
        }

        return retornoMensaje;
    }

    @Override
    public String desbloquearConContable(String empresa, String periodo, String tipo, String numero, String usuario,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "F";
        comprobar = false;
        ConContable conContable = contableDao.obtenerPorId(ConContable.class, new ConContablePK(empresa, periodo, tipo, numero));
        if (conContable != null) {
            if (!conContable.getConBloqueado()) {
                retorno = "FEl contable:\n " + periodo + " | " + tipo + " | " + numero + "\nYa fué desbloqueado";
            } else if (conContable.getConAnulado()) {
                retorno = "FEl contable:\n " + periodo + " | " + tipo + " | " + numero + "\nEstá anulado";
            } else if (conContable.getConPendiente()) {
                retorno = "FEl contable:\n " + periodo + " | " + tipo + " | " + numero + "\nEstá pendiente";
            } else {
                conContable.setConBloqueado(false);
                /////////////// NUEVO SUCESO PARA CONTABLE
                susClave = periodo + "|" + tipo + "|" + numero;
                susDetalle = "Se desbloqueó el contable del periodo " + periodo + ", del tipo " + tipo + ", de la numeración " + numero;
                susSuceso = "DELETE";
                susTabla = "con_contable";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                comprobar = contableDao.anularConContable(conContable.getConContablePK(), sisSuceso, false);
                if (comprobar) {
                    retorno = "TEl contable:\n " + periodo + " | " + tipo + " | " + numero + "\nSe desbloqueó";
                } else {
                    retorno = "FOcurrio un error al desbloquear el contable:\n " + periodo + " | " + tipo + " | " + numero;
                }
            }
        } else {
            retorno = "FNo se encontro el contable..";
        }
        return retorno;
    }

    @Override
    public String anularConContable(String empresa, String periodo, String tipo, String numero, String usuario,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "F";
        comprobar = false;
        ConContable conContable = contableDao.obtenerPorId(ConContable.class, new ConContablePK(empresa, periodo, tipo, numero));
        if (conContable != null) {
            if (conContable.getConBloqueado()) {
                retorno = "FEl contable:\n " + periodo + " | " + tipo + " | " + numero + "\n bloqueado";
            } else if (conContable.getConAnulado()) {
                retorno = "FEl contable:\n " + periodo + " | " + tipo + " | " + numero + "\nya eEstá anulado";
            } else {
                conContable.setConAnulado(true);
                /////////////// NUEVO SUCESO PARA CONTABLE
                susClave = periodo + "|" + tipo + "|" + numero;
                susDetalle = "Se anuló el contable del periodo " + periodo + ", del tipo " + tipo + ", de la numeración " + numero;
                susSuceso = "UPDATE";
                susTabla = "con_contable";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                comprobar = contableDao.anularConContable(conContable.getConContablePK(), sisSuceso, false);
                if (comprobar) {
                    retorno = "TEl contable:\n " + periodo + " | " + tipo + " | " + numero + "\nSe anuló";
                } else {
                    retorno = "FOcurrió un error al anular el contable:\n " + periodo + " | " + tipo + " | " + numero;
                }
            }
        } else {
            retorno = "FNo se encontró el contable..";
        }
        return retorno;
    }

    @Override
    public List<ConFunContablesVerificacionesTO> getFunContablesVerificacionesTO(String empresa) throws Exception {
        return contableDao.getFunContablesVerificacionesTO(empresa);
    }

    @Override
    public String eliminarConContable(ConContablePK conContablePK, SisInfoTO sisInfoTO) throws Exception {
        crearSuceso(conContablePK, "D", sisInfoTO);
        return contableDao.eliminarConContable(conContablePK);
    }

    @Override
    public List<ConFunIPPTO> getFunListaIPP(String empresa, String fechaInicio, String fechaFin, String parametro)
            throws Exception {
        return contableDao.getFunListaIPP(empresa, fechaInicio, fechaFin, parametro);
    }

    @Override
    public MensajeTO getListaInvConsultaConsumosPendientes(String empCodigo, String fechaDesde, String fechaHasta)
            throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        List<String> listaMensajes = new ArrayList<String>();
        String mensajeAux = "";
        try {
            List<ConListaConsultaConsumosTO> listaConsultaConsumosTOs = contableDao
                    .getListaInvConsultaConsumosPendientes(empCodigo, fechaDesde, fechaHasta);
            if (!listaConsultaConsumosTOs.isEmpty()) {
                listaMensajes.add("No se puede generar los contables, existen los siguientes consumos pendientes :");
                for (ConListaConsultaConsumosTO invListaConsultaConsumosTO : listaConsultaConsumosTOs) {
                    listaMensajes.add("Fecha    : " + invListaConsultaConsumosTO.getConsFecha());
                    listaMensajes.add("Motivo   : " + invListaConsultaConsumosTO.getConsMotivo());
                    listaMensajes.add("Número   : " + invListaConsultaConsumosTO.getConsNumero());
                    listaMensajes.add("Periodo  : " + invListaConsultaConsumosTO.getConsPeriodo());
                    listaMensajes.add("");
                }
                mensajeAux = "F<html>Existen consumos pendientes.</html>";
                mensajeTO.setListaMensajes(listaMensajes);
            } else {
                mensajeAux = "T<html>No existen consumos pendientes.</html>";
            }

        } catch (Exception e) {
            e.printStackTrace();
            mensajeAux = "FOcurrió un error al obtener los datos de la Base de Datos. \nContacte con el administrador...";
        }
        mensajeTO.setMensaje(mensajeAux);
        return mensajeTO;
    }

    @Override
    public MensajeTO insertarModificarIPP(String empresa, String fechaDesde, String fechaHasta, String tipo,
            Date fechaHoraBusqueda, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        String retorno = "";
        String retornoValidacion = "";
        List<ConFunIPPComplementoTO> listContablesIPP;

        if ((retorno = periodoService.validarPeriodo(fechaDesde, fechaHasta, empresa)) != null) {
        } else if ((retornoValidacion = contableDao.getFunListaIPPComplementoValidar(empresa, fechaDesde, fechaHasta,
                tipo)) != null) {
            retorno = "FError de validacion: ";
            List<String> list = new ArrayList<String>();
            list.add(retornoValidacion);
            mensajeTO.setListaMensajes(list);
        } else {
            sisInfoTO.setEmpresa(empresa);
            listContablesIPP = contableDao.getFunListaIPPComplemento(empresa, fechaDesde, fechaHasta, tipo);
            List<ConContable> listContable = new ArrayList<ConContable>();
            List<ConContablePK> listContableModificar = new ArrayList<ConContablePK>();
            List<ConContablePK> listContableAnular = new ArrayList<ConContablePK>();
            List<SisSuceso> sisSucesos = new ArrayList<SisSuceso>();
            List<String> listaMensajes = new ArrayList<String>();
            String sql = "";
            for (ConFunIPPComplementoTO ippTO : listContablesIPP) {
                if (ippTO.isAnulado()) {
                    susSuceso = "DELETE";
                    susTabla = "contabilidad.con_contable";
                    susDetalle = "Se Anuló el contable del periodo " + ippTO.getPeriodo() + ", del tipo contable "
                            + ippTO.getTipo() + ", de la numeracion " + ippTO.getNumero();
                    susClave = ippTO.getPeriodo() + " " + ippTO.getTipo() + " " + ippTO.getNumero();
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sisSucesos.add(sisSuceso);
                    listContableAnular.add(new ConContablePK(ippTO.getEmpresa(), ippTO.getPeriodo(), ippTO.getTipo(),
                            ippTO.getNumero()));

                    sql += "UPDATE contabilidad.con_contable SET con_anulado=true WHERE con_empresa='"
                            + ippTO.getEmpresa() + "' and con_periodo='" + ippTO.getPeriodo() + "' and con_tipo='"
                            + ippTO.getTipo() + "' and con_numero='" + ippTO.getNumero() + "';\n";
                } else if (ippTO.isPendiente()) {
                    if (ippTO.getNumero() == null || ippTO.getNumero().compareToIgnoreCase("") == 0) {
                        susSuceso = "INSERT";
                        susTabla = "contabilidad.con_contable";
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                sisInfoTO);
                        sisSucesos.add(sisSuceso);
                        ConContable conContable = new ConContable(ippTO.getEmpresa(), ippTO.getPeriodo(),
                                ippTO.getTipo(), "");
                        conContable.setConFecha(ippTO.getFecha());
                        conContable.setConConcepto("INVENTARIO DE PRODUCTOS EN PROCESO " + tipo.toUpperCase());
                        conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                        conContable.setConObservaciones("CONTABLE DE IPP " + tipo.toUpperCase());
                        conContable.setConBloqueado(false);
                        conContable.setConReversado(false);
                        conContable.setConAnulado(false);
                        conContable.setConGenerado(true);
                        if (tipo.compareToIgnoreCase("DIRECTO") == 0) {
                            conContable.setConReferencia("IPP " + tipo.toUpperCase() + "*");
                        } else {
                            conContable.setConReferencia("IPP " + tipo.toUpperCase());
                        }
                        conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                        conContable.setUsrCodigo(sisInfoTO.getUsuario());
                        conContable.setUsrFechaInserta(UtilsDate.timestamp());
                        listContable.add(conContable);
                    } else {
                        susSuceso = "UPDATE";
                        susTabla = "contabilidad.con_contable";
                        susDetalle = "Se Actualizó el contable del periodo " + ippTO.getPeriodo()
                                + ", del tipo contable " + ippTO.getTipo() + ", de la numeracion " + ippTO.getNumero();
                        susClave = ippTO.getPeriodo() + " " + ippTO.getTipo() + " " + ippTO.getNumero();
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                sisInfoTO);
                        sisSucesos.add(sisSuceso);
                        listContableModificar.add(new ConContablePK(ippTO.getEmpresa(), ippTO.getPeriodo(),
                                ippTO.getTipo(), ippTO.getNumero()));

                        sql += "UPDATE contabilidad.con_contable SET con_pendiente=true, con_referencia=con_referencia||"
                                + (tipo.compareToIgnoreCase("DIRECTO") == 0 ? "'*'" : "''") + " WHERE con_empresa='"
                                + ippTO.getEmpresa() + "' and con_periodo='" + ippTO.getPeriodo() + "' and con_tipo='"
                                + ippTO.getTipo() + "' and con_numero='" + ippTO.getNumero() + "';\n";
                    }
                }
            }

            comprobar = contableDao.insertarModificarIPP(listContable, sql, sisSucesos);
            if (!comprobar) {
                retorno = "F<html>Ocurrió un error al generar los contables, inténtelo de nuevo o contacte con el administrador...</html>";
            } else {
                List<ConContablePK> listContableIngresar = new ArrayList<>();
                for (ConContable conContable : listContable) {
                    listContableIngresar.add(conContable.getConContablePK());
                }
                if (!listContableIngresar.isEmpty()) {
                    listaMensajes.addAll(mensajesConContableAgrupado(listContableIngresar, "INSERT", tipo));
                }
                if (!listContableModificar.isEmpty()) {
                    listaMensajes.addAll(mensajesConContableAgrupado(listContableModificar, "UPDATE", tipo));
                }
                if (!listContableAnular.isEmpty()) {
                    listaMensajes.addAll(mensajesConContableAgrupado(listContableAnular, "DELETE", tipo));
                }

                listContableIngresar.addAll(listContableModificar);
                mensajeTO.getMap().put("conContables", listContableIngresar);

                mayorizarSql((List<ConContablePK>) mensajeTO.getMap().get("conContables"));

                mensajeTO.setListaMensajes(listaMensajes);
                retorno = "T<html>Se realizó la contabilización de los IPP " + tipo + " de la fechas: " + fechaDesde
                        + " - " + fechaHasta + " .</html>";
                mensajeTO.setFechaCreacion(fechaHoraBusqueda.toString());
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public MensajeTO insertarModificarContablesIPPTodo(String empresa, String fechaDesde, String fechaHasta,
            Date fechaHoraBusqueda, List<PrdContabilizarCorridaTO> listaContabilizarCorrida, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        MensajeTO mensajeTODirecto = new MensajeTO();
        MensajeTO mensajeTOIndirecto = new MensajeTO();
        MensajeTO mensajeTOCorridas = new MensajeTO();
        String retorno = "";
        String erroresContabilizarMaterialDirecto = contableDao.getFunListaIPPComplementoValidar(empresa, fechaDesde, fechaHasta, "DIRECTO");
        String erroresContabilizarMaterialIndirecto = contableDao.getFunListaIPPComplementoValidar(empresa, fechaDesde, fechaHasta, "INDIRECTO");
        List<String> listaMensajes = new ArrayList<String>();
        if (erroresContabilizarMaterialDirecto != null || erroresContabilizarMaterialIndirecto != null) {
            retorno = "FError de validacion (CUENTAS CONTABLES)";
            listaMensajes.add(erroresContabilizarMaterialDirecto);
            listaMensajes.add(erroresContabilizarMaterialIndirecto);
            mensajeTO.setListaMensajes(listaMensajes);
        } else if ((retorno = periodoService.validarPeriodo(fechaDesde, fechaHasta, empresa)) != null) {
            listaMensajes.add(retorno.substring(1));
            mensajeTO.setListaMensajes(listaMensajes);
            retorno = "FError de validación (PERIODOS)";
        } else {
            mensajeTODirecto = insertarModificarIPP(empresa, fechaDesde, fechaHasta, "DIRECTO", fechaHoraBusqueda, sisInfoTO);
            mensajeTOIndirecto = insertarModificarIPP(empresa, fechaDesde, fechaHasta, "INDIRECTO", fechaHoraBusqueda, sisInfoTO);
            mensajeTOCorridas = insertarModificarContabilizarCorrida(empresa, fechaDesde, fechaHasta, listaContabilizarCorrida, sisInfoTO);

            if (mensajeTODirecto.getMensaje().charAt(0) == 'T'
                    && (mensajeTOIndirecto.getMensaje().charAt(0) == 'T')
                    && (mensajeTOCorridas.getMensaje().charAt(0) == 'T')) {
                listaMensajes.addAll(mensajeTODirecto.getListaMensajes());
                listaMensajes.addAll(mensajeTOIndirecto.getListaMensajes());
                listaMensajes.addAll(mensajeTOCorridas.getListaMensajes());
                mensajeTO.setListaMensajes(listaMensajes);
                retorno = "TContabilización exitosa!";
            } else {
                retorno = "FError de validación";
            }
            listaMensajes.add((mensajeTODirecto.getMensaje().charAt(0) == 'T' ? "" : "COSTO DIRECTO: \n" + mensajeTODirecto.getMensaje().substring(1) + "\n")
                    + (mensajeTOIndirecto.getMensaje().charAt(0) == 'T' ? "" : "COSTO INDIRECTO: \n" + mensajeTOIndirecto.getMensaje().substring(1) + "\n")
                    + (mensajeTOCorridas.getMensaje().charAt(0) == 'T' ? "" : "CIERRE DE CORRIDA:\n" + mensajeTOCorridas.getMensaje().substring(1)));
            mensajeTO.setListaMensajes(listaMensajes);

        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public MensajeTO insertarModificarContabilizarCorrida(String empresa, String fechaDesde, String fechaHasta,
            List<PrdContabilizarCorridaTO> listaContabilizarCorrida, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        if ((retorno = periodoService.validarPeriodo(fechaDesde, fechaHasta, empresa)) != null) {
        } else if (listaContabilizarCorrida == null || listaContabilizarCorrida.isEmpty()) {
            retorno = "T<html>No existen CORRIDAS en las fechas indicadas</html>";
        } else {
            sisInfoTO.setEmpresa(empresa);
            List<ConContable> listContable = new ArrayList<ConContable>();
            List<ConContablePK> listContableModificar = new ArrayList<ConContablePK>();
            List<SisSuceso> sisSucesos = new ArrayList<SisSuceso>();
            List<String> listaMensajes = new ArrayList<String>();
            String sql = "";
            listaContabilizarCorrida = listaContabilizarCorrida.subList(0, listaContabilizarCorrida.size() - 1);
            for (PrdContabilizarCorridaTO ccTO : listaContabilizarCorrida) {
                if ((ccTO.getRcContablePeriodo() == null || ccTO.getRcContablePeriodo().compareToIgnoreCase("") == 0)
                        && (ccTO.getRcContableTipo() == null || ccTO.getRcContableTipo().compareToIgnoreCase("") == 0)
                        && (ccTO.getRcContableNumero() == null
                        || ccTO.getRcContableNumero().compareToIgnoreCase("") == 0)) {
                    susSuceso = "INSERT";
                    susTabla = "contabilidad.con_contable";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sisSucesos.add(sisSuceso);
                    Date fechaContable = UtilsDate.fechaFormatoDate(ccTO.getRcFechaHasta(), "yyyy-MM-dd");
                    SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaContable, ccTO.getEmpCodigo());
                    ConContable conContable = new ConContable(ccTO.getEmpCodigo(),
                            sisPeriodo.getSisPeriodoPK().getPerCodigo(), "C-IPP", "");
                    conContable.setConFecha(fechaContable);
                    conContable.setConConcepto("CIERRE DE CORRIDA");
                    conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                    conContable.setConObservaciones("CIERRE DE CORRIDA SECTOR:" + ccTO.getSecCodigo() + " PISCINA: "
                            + ccTO.getPisNumero() + " CORRIDA: " + ccTO.getRcCorridaNumero());
                    conContable.setConBloqueado(false);
                    conContable.setConReversado(false);
                    conContable.setConAnulado(false);
                    conContable.setConGenerado(true);
                    conContable.setConReferencia("produccion.prd_corrida");
                    conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                    conContable.setUsrCodigo(sisInfoTO.getUsuario());
                    conContable.setUsrFechaInserta(UtilsDate.timestamp());
                    List<PrdCorrida> corList = new ArrayList<PrdCorrida>();
                    corList.add(new PrdCorrida(ccTO.getEmpCodigo(), ccTO.getSecCodigo(), ccTO.getPisNumero(),
                            ccTO.getRcCorridaNumero()));
                    conContable.setPrdCorridaList(corList);
                    listContable.add(conContable);
                } else {
                    susSuceso = "UPDATE";
                    susTabla = "contabilidad.con_contable";
                    susDetalle = "Se Actualizó el contable del periodo " + ccTO.getRcContablePeriodo()
                            + ", del tipo contable " + ccTO.getRcContableTipo() + ", de la numeracion "
                            + ccTO.getRcContableNumero();
                    susClave = ccTO.getRcContablePeriodo() + " " + ccTO.getRcContableTipo() + " "
                            + ccTO.getRcContableNumero();
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sisSucesos.add(sisSuceso);
                    listContableModificar.add(new ConContablePK(ccTO.getEmpCodigo(), ccTO.getRcContablePeriodo(),
                            ccTO.getRcContableTipo(), ccTO.getRcContableNumero()));

                    sql += "UPDATE contabilidad.con_contable SET con_pendiente=true WHERE con_empresa='"
                            + ccTO.getEmpCodigo() + "' and con_periodo='" + ccTO.getRcContablePeriodo()
                            + "' and con_tipo='" + ccTO.getRcContableTipo() + "' and con_numero='"
                            + ccTO.getRcContableNumero() + "';\n";
                }
            }

            listContable.sort(new Comparator<ConContable>() {
                @Override
                public int compare(ConContable con1, ConContable con2) {
                    return con1.getConContablePK().getConPeriodo()
                            .compareToIgnoreCase(con2.getConContablePK().getConPeriodo());
                }
            });
            comprobar = contableDao.insertarModificarContabilizarCorrida(listContable, sql, false, sisSucesos);
            if (!comprobar) {
                retorno = "F<html>Ocurrió un error al generar los contables, inténtelo de nuevo o contacte con el administrador...</html>";
            } else {
                List<ConContablePK> listContableIngresar = new ArrayList<>();
                for (ConContable conContable : listContable) {
                    listContableIngresar.add(conContable.getConContablePK());
                }
                if (!listContableIngresar.isEmpty()) {
                    listaMensajes.addAll(mensajesConContableAgrupado(listContableIngresar, "INSERT", "CIERRE DE CORRIDA"));
                }
                if (!listContableModificar.isEmpty()) {
                    listaMensajes.addAll(mensajesConContableAgrupado(listContableModificar, "UPDATE", "CIERRE DE CORRIDA"));
                }

                listContableIngresar.addAll(listContableModificar);
                mensajeTO.getMap().put("conContables", listContableIngresar);

                mayorizarSql((List<ConContablePK>) mensajeTO.getMap().get("conContables"));

                mensajeTO.setListaMensajes(listaMensajes);
                retorno = "T<html>Se realizó la contabilización de los CIERRE DE CORRIDA de la fechas: " + fechaDesde
                        + " - " + fechaHasta + " .</html>";
                mensajeTO.setFechaCreacion(new Date().toString());
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public MensajeTO insertarModificarContabilizarCorridaCostoVenta(String empresa, String fechaDesde, String fechaHasta, List<PrdContabilizarCorridaCostoVentaTO> listaContabilizarCorrida, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        if ((retorno = periodoService.validarPeriodo(fechaDesde, fechaHasta, empresa)) != null) {
        } else if (listaContabilizarCorrida == null || listaContabilizarCorrida.isEmpty()) {
            retorno = "T<html>No existen CORRIDAS en las fechas indicadas</html>";
        } else {
            sisInfoTO.setEmpresa(empresa);
            List<ConContable> listContable = new ArrayList<ConContable>();
            List<ConContablePK> listContableModificar = new ArrayList<ConContablePK>();
            List<SisSuceso> sisSucesos = new ArrayList<SisSuceso>();
            List<String> listaMensajes = new ArrayList<String>();
            String sql = "";
            listaContabilizarCorrida = listaContabilizarCorrida.subList(0, listaContabilizarCorrida.size() - 1);
            for (PrdContabilizarCorridaCostoVentaTO ccTO : listaContabilizarCorrida) {
                if ((ccTO.getRcContablePeriodo() == null || ccTO.getRcContablePeriodo().compareToIgnoreCase("") == 0)
                        && (ccTO.getRcContableTipo() == null || ccTO.getRcContableTipo().compareToIgnoreCase("") == 0)
                        && (ccTO.getRcContableNumero() == null
                        || ccTO.getRcContableNumero().compareToIgnoreCase("") == 0)) {
                    susSuceso = "INSERT";
                    susTabla = "contabilidad.con_contable";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sisSucesos.add(sisSuceso);
                    Date fechaContable = UtilsDate.fechaFormatoDate(ccTO.getRcFechaVenta(), "yyyy-MM-dd");
                    SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaContable, ccTO.getEmpCodigo());
                    ConContable conContable = new ConContable(ccTO.getEmpCodigo(), sisPeriodo.getSisPeriodoPK().getPerCodigo(), "C-IPP", "");
                    conContable.setConFecha(fechaContable);
                    conContable.setConConcepto("CIERRE DE COSTO DE VENTA");
                    conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                    conContable.setConObservaciones("CIERRE DE COSTO DE VENTA:" + ccTO.getSecCodigo() + " PISCINA: " + ccTO.getPisNumero() + " CORRIDA: " + ccTO.getRcCorridaNumero());
                    conContable.setConBloqueado(false);
                    conContable.setConReversado(false);
                    conContable.setConAnulado(false);
                    conContable.setConGenerado(true);
                    conContable.setConReferencia("produccion.prd_corrida (COSTO DE VENTA)");
                    conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                    conContable.setUsrCodigo(sisInfoTO.getUsuario());
                    conContable.setUsrFechaInserta(UtilsDate.timestamp());
                    List<PrdCorrida> corList = new ArrayList<PrdCorrida>();
                    corList.add(new PrdCorrida(ccTO.getEmpCodigo(), ccTO.getSecCodigo(), ccTO.getPisNumero(), ccTO.getRcCorridaNumero()));
                    conContable.setPrdCorridaList(corList);
                    listContable.add(conContable);
                } else {
                    susSuceso = "UPDATE";
                    susTabla = "contabilidad.con_contable";
                    susDetalle = "Se Actualizó el contable del periodo " + ccTO.getRcContablePeriodo()
                            + ", del tipo contable " + ccTO.getRcContableTipo() + ", de la numeracion "
                            + ccTO.getRcContableNumero();
                    susClave = ccTO.getRcContablePeriodo() + " " + ccTO.getRcContableTipo() + " "
                            + ccTO.getRcContableNumero();
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sisSucesos.add(sisSuceso);
                    listContableModificar.add(new ConContablePK(ccTO.getEmpCodigo(), ccTO.getRcContablePeriodo(),
                            ccTO.getRcContableTipo(), ccTO.getRcContableNumero()));

                    sql += "UPDATE contabilidad.con_contable SET con_pendiente=true WHERE con_empresa='"
                            + ccTO.getEmpCodigo() + "' and con_periodo='" + ccTO.getRcContablePeriodo()
                            + "' and con_tipo='" + ccTO.getRcContableTipo() + "' and con_numero='"
                            + ccTO.getRcContableNumero() + "';\n";
                }
            }

            listContable.sort(new Comparator<ConContable>() {
                @Override
                public int compare(ConContable con1, ConContable con2) {
                    return con1.getConContablePK().getConPeriodo()
                            .compareToIgnoreCase(con2.getConContablePK().getConPeriodo());
                }
            });
            comprobar = contableDao.insertarModificarContabilizarCorrida(listContable, sql, true, sisSucesos);
            if (!comprobar) {
                retorno = "F<html>Ocurrió un error al generar los contables, inténtelo de nuevo o contacte con el administrador...</html>";
            } else {
                List<ConContablePK> listContableIngresar = new ArrayList<>();
                for (ConContable conContable : listContable) {
                    listContableIngresar.add(conContable.getConContablePK());
                }
                if (!listContableIngresar.isEmpty()) {
                    listaMensajes.addAll(mensajesConContableAgrupado(listContableIngresar, "INSERT", "CIERRE DE CORRIDA"));
                }
                if (!listContableModificar.isEmpty()) {
                    listaMensajes.addAll(mensajesConContableAgrupado(listContableModificar, "UPDATE", "CIERRE DE CORRIDA"));
                }

                listContableIngresar.addAll(listContableModificar);
                mensajeTO.getMap().put("conContables", listContableIngresar);

                mayorizarSql((List<ConContablePK>) mensajeTO.getMap().get("conContables"));

                mensajeTO.setListaMensajes(listaMensajes);
                retorno = "T<html>Se realizó la contabilización de los CIERRE DE CORRIDA COSTO DE VENTA de la fechas: " + fechaDesde
                        + " - " + fechaHasta + " .</html>";
                mensajeTO.setFechaCreacion(new Date().toString());
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    public List<String> mensajesConContableAgrupado(List<ConContablePK> conContablesPK, String operacion, String tipo) throws Exception {
        List<String> listaMensajes = new ArrayList<String>();
        for (ConContablePK conContablePK : conContablesPK) {
            listaMensajes.add(operacion + "("
                    + conContablePK.getConPeriodo() + ", "
                    + conContablePK.getConTipo() + ", "
                    + conContablePK.getConNumero() + ","
                    + tipo + ")");
        }
        return listaMensajes;
    }

    @Override
    public MensajeTO insertarConContableCierreCuentas(ConContableTO conContableTO, String centroProduccion,
            String conNumero_Contable, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        ConContableCierreResultado conCierreResultados = new ConContableCierreResultado();
        conCierreResultados.setCierreFechaHasta(UtilsDate.DeStringADate(conContableTO.getConFecha()));
        conCierreResultados.setConContable(ConversionesContabilidad.convertirConContableTO_ConContable(conContableTO));
        conCierreResultados.setSecEmpresa(sisInfoTO.getEmpresa());
        conCierreResultados.setSecCodigo(centroProduccion);
        conCierreResultados.setCtaCodigo(conNumero_Contable);
        conCierreResultados.setCtaEmpresa(sisInfoTO.getEmpresa());
        conCierreResultados.setUsrEmpresa(sisInfoTO.getEmpresa());
        if (conContableCierreResultadosService.obtenerConContableCierreResultados(conCierreResultados) == null) {
            mensajeTO = conContableCierreResultadosService.insertarConContableCierreResultados(conCierreResultados, sisInfoTO);
        } else {
            mensajeTO = conContableCierreResultadosService.modificarConContableCierreResultados(conCierreResultados, sisInfoTO);
        }
        return mensajeTO;
    }

    @Override
    public List<ConFunContablesVerificacionesComprasTO> getConFunContablesVerificacionesComprasTOs(String empresa,
            String fechaInicio, String fechaFin) throws Exception {
        return contableDao.getConFunContablesVerificacionesComprasTOs(empresa, fechaInicio, fechaFin);
    }

    @Override
    public List<PersonaTO> getFunPersonaTOs(String empresa, String busquedad) throws Exception {
        return contableDao.getFunPersonaTOs(empresa, busquedad);
    }

    @Override
    public List<ConListaBalanceResultadosVsInventarioTO> getConListaBalanceResultadosVsInventarioTO(String empresa,
            String desde, String hasta, String sector) throws Exception {
        return contableDao.getConListaBalanceResultadosVsInventarioTO(empresa, desde, hasta, sector);
    }

    @Override
    public List<ConBalanceResultadosVsInventarioDetalladoTO> getConListaBalanceResultadosVsInventarioDetalladoTO(String empresa, String desde, String hasta, String sector, boolean estado) throws Exception {
        return contableDao.getConListaBalanceResultadosVsInventarioDetalladoTO(empresa, desde, hasta, sector, estado);
    }

    @Override
    public List<ConBalanceResultadosMensualizadosTO> getBalanceResultadoMensualizado(String empresa, String codigoSector, String fechaInicio, String fechaFin, String usuario, SisInfoTO sisInfoTO) throws Exception {
        return contableDao.getBalanceResultadoMensualizado(empresa, codigoSector, fechaInicio, fechaFin);
    }

    @Override
    public RetornoTO getBalanceResultadoMensualizadoAntiguo(String empresa, String codigoSector, String fechaInicio, String fechaFin, String usuario, SisInfoTO sisInfoTO) throws Exception {
        RetornoTO retornoTO = new RetornoTO();
        List<ConBalanceResultadosMensualizadosAntiguoTO> conBalanceResultadosMensualizadosTOs = contableDao.getBalanceResultadoMensualizadoAntiguo(empresa, codigoSector, fechaInicio, fechaFin);
        String mensaje = "T";
        NumeroColumnaDesconocidadBalanceResultadoMensualizado obj = new NumeroColumnaDesconocidadBalanceResultadoMensualizado();
        obj.agruparCabeceraColumnas(conBalanceResultadosMensualizadosTOs);
        obj.agruparCuentas(conBalanceResultadosMensualizadosTOs);
        obj.llenarObjetoParaTabla(conBalanceResultadosMensualizadosTOs);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    public List<ConMayorAuxiliarTO> getListaMayorAuxiliarMultipleTO(String empresa, String codigoCuentaDesde,
            String codigoCuentaHasta, String fechaInicio, String fechaFin, String sector, String usuario,
            SisInfoTO sisInfoTO) throws Exception {
        List<ConMayorAuxiliarTO> conMayorAuxiliarMultipleTOs = new ArrayList<>(1);
        List<ConMayorAuxiliarTO> conMayorAuxiliarMultipleAuxTOs = null;
        List<ConEstructuraTO> conEstructuraTOs = estructuraDao.getListaConEstructuraTO(empresa);
        int largoCuenta = conEstructuraTOs.get(0).getEstGrupo1() + conEstructuraTOs.get(0).getEstGrupo2()
                + conEstructuraTOs.get(0).getEstGrupo3() + conEstructuraTOs.get(0).getEstGrupo4()
                + conEstructuraTOs.get(0).getEstGrupo5() + conEstructuraTOs.get(0).getEstGrupo6();
        List<ConCuentasTO> conCuentasTOs = cuentasDao.getRangoCuentasTO(empresa, codigoCuentaDesde,
                codigoCuentaHasta, largoCuenta);
        for (ConCuentasTO conCuentasTO : conCuentasTOs) {
            conMayorAuxiliarMultipleAuxTOs = getListaMayorAuxiliarTO(empresa, conCuentasTO.getCuentaCodigo(),
                    conCuentasTO.getCuentaCodigo(), fechaInicio, fechaFin, sector, false);
            for (ConMayorAuxiliarTO conMayorAuxiliarMultipleTO : conMayorAuxiliarMultipleAuxTOs) {
                conMayorAuxiliarMultipleTOs.add(conMayorAuxiliarMultipleTO);
            }
        }
        return conMayorAuxiliarMultipleTOs;
    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return contableDao.getEstadoCCCVT(empresa, periodo, motivo, numero);
    }

    @Override
    public List<ListaConContableTO> getListConContableTO(String empresa, String periodo, String tipo, String busqueda,
            String referencia, String nRegistros) throws Exception {
        return contableDao.getListConContableTO(empresa, periodo, tipo, busqueda, referencia, nRegistros);
    }

    @Override
    public List<ItemStatusItemListaConContableTO> getListConContableTOConStatus(String empresa, String periodo, String tipo, String busqueda, String referencia, String nRegistros) throws Exception {
        List<ListaConContableTO> listadoConContableTO = contableDao.getListConContableTO(empresa, periodo, tipo, busqueda, referencia, nRegistros);
        List<ItemStatusItemListaConContableTO> listadoConStatus = new ArrayList<ItemStatusItemListaConContableTO>();
        for (ListaConContableTO listaConContableTO : listadoConContableTO) {
            ItemStatusItemListaConContableTO itemLista = new ItemStatusItemListaConContableTO();
            itemLista.setEmpCodigo(empresa);
            itemLista.setConReferencia(listaConContableTO.getConReferencia());
            itemLista.setPerCodigo(listaConContableTO.getPerCodigo());
            itemLista.setTipCodigo(listaConContableTO.getTipCodigo());
            itemLista.setConNumero(listaConContableTO.getConNumero());
            itemLista.setConFecha(listaConContableTO.getConFecha());
            itemLista.setConConcepto(listaConContableTO.getConConcepto());
            itemLista.setConDetalle(listaConContableTO.getConDetalle());
            itemLista.setConObservaciones(listaConContableTO.getConObservaciones());
            if (listaConContableTO.getConAnulado().equalsIgnoreCase("ANULADO")) {
                itemLista.setConStatus(listaConContableTO.getConAnulado());
                itemLista.setConAnulado(listaConContableTO.getConAnulado());
            }
            if (listaConContableTO.getConPendiente().equalsIgnoreCase("PENDIENTE")) {
                itemLista.setConStatus(listaConContableTO.getConPendiente());
                itemLista.setConPendiente(listaConContableTO.getConPendiente());
            }
            if (listaConContableTO.getConReversado().equalsIgnoreCase("REVERSADO")) {
                itemLista.setConStatus(listaConContableTO.getConReversado());
                itemLista.setConReversado(listaConContableTO.getConReversado());
            }
            if (listaConContableTO.getConBloqueado().equalsIgnoreCase("BLOQUEADO")) {
                itemLista.setConStatus(listaConContableTO.getConBloqueado());
                itemLista.setConBloqueado(listaConContableTO.getConBloqueado());
                if (listaConContableTO.getConPendiente().equalsIgnoreCase("PENDIENTE")) {
                    itemLista.setConStatus(listaConContableTO.getConBloqueado() + "-" + listaConContableTO.getConPendiente());
                }
            }
            listadoConStatus.add(itemLista);
        }

        return listadoConStatus;
    }

    @Override
    public MensajeTO insertarModificarContable(ConContable conContable, List<ConDetalle> listaConDetalle, List<ConAdjuntosContableWebTO> listadoImagenes,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        retorno = periodoService.validarPeriodo(conContable.getConFecha(),
                conContable.getConContablePK().getConEmpresa());

        if (retorno != null) {
            retorno = "F<html>" + retorno + "</html>";
        } else if (!tipoDao.buscarTipoContable(conContable.getConContablePK().getConEmpresa(),
                conContable.getConContablePK().getConTipo())) {
            retorno = "F<html>No se encuentra el tipo de contable.</html>";
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(),
                    conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(false);
                conContable.setConReversado(false);
                // conContable.setConReferencia("recursoshumanos.rh_anticipo");
                conContable.setConReferencia(null);
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }

            susSuceso = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT"
                    : "UPDATE";
            susTabla = "contabilidad.con_contable";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingreso"
                    : "mayorizo";
            comprobar = contableDao.insertarModificarContable(conContable, listaConDetalle, sisSuceso);
            actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
            if (!comprobar) {
                retorno = "F<html>Ocurrió un error al guardar el contable.\nIntente de nuevo o contacte con el administrador</html>";
            } else {
                retorno = "T<html>Se " + mensaje + " el contable con la siguiente información:<br><br>"
                        + "Periodo: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Tipo: <font size = 5>"
                        + conContable.getConContablePK().getConTipo() + "</font>.<br> Numero: <font size = 5>"
                        + conContable.getConContablePK().getConNumero() + "</font>.</html>"
                        + conContable.getConContablePK().getConNumero() + ", "
                        + conContable.getConContablePK().getConPeriodo();

                mensajeTO.setFechaCreacion(
                        UtilsValidacion.fecha(conContable.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                mensajeTO.getMap().put("contable", conContable);
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public ConContable getConContable(ConContablePK conContablePK) {
        return contableDao.getConContable(conContablePK);
    }

    @Override
    public void mayorizarSql(List<ConContablePK> listaContable) {
        String sql = "";
        for (ConContablePK conPK : listaContable) {
            sql += "UPDATE contabilidad.con_contable SET con_pendiente=false WHERE con_empresa='"
                    + conPK.getConEmpresa() + "' and  con_periodo='" + conPK.getConPeriodo() + "' and con_tipo='"
                    + conPK.getConTipo() + "' and con_numero='" + conPK.getConNumero() + "';\n";
        }
        contableDao.ejecutarSQL(sql);
    }

    @Override
    public String mayorizarDesmayorizarSql(ConContablePK conContablePK, boolean pendiente, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        susClave = conContablePK.getConPeriodo() + " " + conContablePK.getConTipo() + " " + conContablePK.getConNumero();
        susDetalle = "";
        susSuceso = "UPDATE";
        susTabla = "contabilidad.con_contable";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                sisInfoTO);
        ConContable conContable = getConContable(conContablePK);
        if ((mensaje = periodoService.validarPeriodo(conContable.getConFecha(),
                conContable.getConContablePK().getConEmpresa())) != null) {
        } else if (pendiente) { // TRUE, DESMAYORIZAR
            if (conContable.getConAnulado()) {
                mensaje = "FNo se puede Desmayorizar el contable " + conContablePK.mensaje()
                        + " porque esta Anulado";
            } else if (conContable.getConPendiente()) {
                mensaje = "FNo se puede Desmayorizar el contable " + conContablePK.mensaje()
                        + " porque esta Desmayorizado";
            } else if (conContable.getConReversado()) {
                mensaje = "FNo se puede Desmayorizar el contable " + conContablePK.mensaje()
                        + " porque esta Reversado";
            } else if (conContable.getConBloqueado()) {
                mensaje = "FNo se puede Desmayorizar el contable " + conContablePK.mensaje()
                        + " porque esta Bloqueado";
            } else {
                contableDao.mayorizarDesmayorizarSql(conContablePK, pendiente, sisSuceso);
                mensaje = "TSe Desmayorizo correctamente el contable " + conContablePK.mensaje();
            }//CONDICIONES CUANDO SE INTENTA MAYORIZAR
        } else if (conContable.getConAnulado()) {
            mensaje = "FNo se puede mayorizar el contable " + conContablePK.mensaje() + " porque esta Anulado";
        } else if (!conContable.getConPendiente()) {
            mensaje = "FNo se puede mayorizar el contable " + conContablePK.mensaje()
                    + " porque no esta Desmayorizado";
        } else if (conContable.getConReversado()) {
            mensaje = "FNo se puede mayorizar el contable " + conContablePK.mensaje()
                    + " porque esta Reversado";
        } else if (conContable.getConBloqueado()) {
            mensaje = "FNo se puede mayorizar el contable " + conContablePK.mensaje()
                    + " porque esta Bloqueado";
        } else {
            contableDao.mayorizarDesmayorizarSql(conContablePK, pendiente, sisSuceso);
            mensaje = "TSe mayorizó correctamente el contable " + conContablePK.mensaje();
        }
        return mensaje;
    }

    @Override
    public String anularReversarSql(ConContablePK conContablePK, boolean anularReversar, SisInfoTO sisInfoTO) {
        String mensaje = "";
        ConContable conContable = getConContable(conContablePK);
        String accion = anularReversar ? "anular" : "reversar";
        boolean eliminarReferencia = false;
        if (conContable.getConReferencia() != null && !conContable.getConReferencia().equals("") && conContable.getConReferencia().equals("inventario.inv_compras")
                && anularReversar) {
            eliminarReferencia = true;
        }

        if ((mensaje = periodoService.validarPeriodo(conContable.getConFecha(),
                conContable.getConContablePK().getConEmpresa())) != null) {
        } else if (conContable.getConAnulado()) {
            mensaje = "FNo se puede " + accion + " el contable " + conContablePK.mensaje() + " porque esta Anulado";
        } else if (conContable.getConReversado()) {
            mensaje = "FNo se puede " + accion + " el contable " + conContablePK.mensaje() + " porque esta Reversado";
        } else if (conContable.getConBloqueado()) {
            mensaje = "FNo se puede " + accion + " el contable " + conContablePK.mensaje() + " porque esta Bloqueado";
        } else {

            mensaje = "TSe " + (anularReversar ? "anulo" : "reverso") + " correctamente el contable "
                    + conContablePK.mensaje();
            susClave = conContablePK.mensaje();
            susDetalle = mensaje;
            susSuceso = "UPDATE";
            susTabla = "contabilidad.con_contable";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sucesoDao.insertar(sisSuceso);
            contableDao.anularReversarSql(conContablePK, anularReversar, eliminarReferencia);

        }

        return mensaje;
    }

    @Override
    public String restaurarSql(ConContablePK conContablePK) throws Exception {
        String mensaje = "";
        boolean puedeRestaurar = true;
        ConContable conContable = getConContable(conContablePK);

        if (conContable.getConDetalleList() != null && conContable.getConDetalleList().size() > 0) {
            for (ConDetalle item : conContable.getConDetalleList()) {

                if (item.getDetDocumento() != null
                        && !item.getDetDocumento().equals("")
                        && item.getDetDebitoCredito() == 'C') {
                    boolean isChequeRepetido = chequeService.isExisteChequeAimprimir(
                            conContable.getConContablePK().getConEmpresa(),
                            item.getConCuentas().getConCuentasPK().getCtaCodigo(),
                            item.getDetDocumento(), item.getDetSecuencia());
                    if (isChequeRepetido) {
                        puedeRestaurar = false;
                        mensaje = "FNo se puede restaurar el contable porque el cheque: "
                                + item.getDetDocumento()
                                + " está siendo utilizando en otro contable";
                        break;
                    }
                }
            }
        }

        if (puedeRestaurar) {
            if ((mensaje = periodoService.validarPeriodo(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa())) != null) {
            } else if (conContable.getConPendiente()) {
                mensaje = "FNo se puede restaurar el contable " + conContablePK.mensaje() + " porque esta Desmayorizado";
            } else if (conContable.getConBloqueado()) {
                mensaje = "FNo se puede restaurar el contable " + conContablePK.mensaje() + " porque esta Bloqueado";
            } else if (!conContable.getConReversado() && !conContable.getConAnulado()) {
                mensaje = "FNo se puede restaurar el contable " + conContablePK.mensaje() + " porque no esta Anulado o Reversado";
            } else if (conContable.getConAnulado() && conContable.getConReversado()) {
                mensaje = "FNo se puede restaurar el contable " + conContablePK.mensaje() + " porque esta Anulado y Reversado";
            } else {
                contableDao.restaurarSql(conContablePK);
                mensaje = "TSe restauro correctamente el contable " + conContablePK.mensaje();
            }
        }

        return mensaje;
    }

    @Override
    public List<String> validarChequesConciliados(String empresa, String periodo, String conTipo, String conNumero)
            throws Exception {

        return contableDao.validarChequesConciliados(empresa, periodo, conTipo, conNumero);
    }

    @Override
    public MensajeTO validarContables(String empresa, String periodo, String conTipo, String conNumero,
            String accionUsuario, String bandera) throws Exception {
        List<String> mensaje = new ArrayList<String>(1);
        String retorno = "T";
        MensajeTO mensajeTO = new MensajeTO();
        mensaje = contableDao.validarChequesConciliados(empresa, periodo, conTipo, conNumero);
        if (mensaje != null && !mensaje.isEmpty()) {
            retorno = "FNo se puede " + accionUsuario + " el contable por que tiene cheques CONCILIADOS.\n";
        } else if (bandera.compareTo("") != 0) {
            mensaje = contableDao.validarPagoCobroRefernciaAnticipo(empresa, periodo, conTipo, conNumero, bandera);
            if (mensaje != null && !mensaje.isEmpty()) {
                retorno = "FNo se puede " + accionUsuario
                        + " el ANTICIPO se esta haciendo referencia a los siguientes CONTABLES.\n";
            }

        }
        mensajeTO.setMensaje(retorno);
        mensajeTO.setListaErrores1(mensaje);
        return mensajeTO;
    }

    @Override
    public ItemListaContableTO obtenerContableConDetalle(ConContablePK conContablePK, boolean mostrarSoloActivos) throws Exception {
        ItemListaContableTO respuesta = null;
        ConContable conContable = conContableDao.obtener(ConContable.class, conContablePK);
        if (conContable != null) {
            respuesta = new ItemListaContableTO();
            Timestamp fechaActual = sistemaWebServicio.getFechaActual();
            SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, conContable.getConContablePK().getConEmpresa());
            List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(conContable.getConContablePK().getConEmpresa());

            SisPeriodo periodo = sisPeriodoDao.obtener(SisPeriodo.class, new SisPeriodoPK(conContable.getConContablePK().getConEmpresa(), conContable.getConContablePK().getConPeriodo()));
            ConTipoTO tipo = tipoDao.getConTipoTO(conContable.getConContablePK().getConEmpresa(), conContable.getConContablePK().getConTipo());
            List<ConTipoTO> tipos = tipoDao.getListaConTipoTO(conContable.getConContablePK().getConEmpresa());
            List<ListaBanCuentaTO> cuentaBanco = cuentaService.getListaBanCuentaTO(conContable.getConContablePK().getConEmpresa());
            respuesta.setFechaActual(fechaActual);
            respuesta.setIsPeriodoAbierto((sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
            respuesta.setListaperiodoSeleccionado(listaPeriodos);
            respuesta.setListaCuentaBanco(cuentaBanco);
            respuesta.setListaTipoContableTO(tipos);
            respuesta.setTipoSeleccionado(tipo);
            respuesta.setGenerado(conContable.getConGenerado());
            respuesta.setPeriodoSeleccionado(periodo);
            respuesta.setConContable(conContable);
            List<PrdListaSectorTO> listBuscarSectorTO = sectorService.getListaSectorTO(conContable.getConContablePK().getConEmpresa(), (mostrarSoloActivos ? false : true));
            respuesta.setListasectorSeleccionado(listBuscarSectorTO);
            //DETALLE
            List<ConDetalleTablaTO> detalleAuxiliar = detalleService.getListaConDetalleTO(conContable.getConContablePK().getConEmpresa(), conContable.getConContablePK().getConPeriodo(), conContable.getConContablePK().getConTipo(), conContable.getConContablePK().getConNumero());
            List<ConListaContableDetalleTO> detalle = new ArrayList<ConListaContableDetalleTO>();
            int i = 0;
            for (ConDetalleTablaTO conDetalleTablaTO : detalleAuxiliar) {
                ConListaContableDetalleTO item = new ConListaContableDetalleTO();
                ConCuentas conCuenta = conCuentaDao.obtener(ConCuentas.class, new ConCuentasPK(conContable.getConContablePK().getConEmpresa(), conDetalleTablaTO.getCtaCodigo()));
                item.setId(i);
                item.setConCuentas(conCuenta);
                item.setCtaCodigo(conDetalleTablaTO.getCtaCodigo());
                item.setCtaDetalle(conDetalleTablaTO.getCtaDetalle());
                item.setDetGenerado(conDetalleTablaTO.getDetGenerado());
                item.setDetObservaciones(conDetalleTablaTO.getDetObservaciones());
                item.setConFilaEstado(true);
                item.setConEstado(true);
                item.setConCuentaVacia(false);
                item.setConChequeImprimir(false);
                item.setConChequeRepetido(false);
                item.setConEstadoDebitoCreditoValido(true);
                if (conDetalleTablaTO.getDetCreditos().compareTo(BigDecimal.ZERO) == 0) {
                    item.setDebidoCredito("D");
                    item.setSaldo(conDetalleTablaTO.getDetDebitos());
                }
                if (conDetalleTablaTO.getDetDebitos().compareTo(BigDecimal.ZERO) == 0) {
                    item.setDebidoCredito("C");
                    item.setSaldo(conDetalleTablaTO.getDetCreditos());
                }
                item.setDetCreditos(conDetalleTablaTO.getDetCreditos());
                item.setDetDebitos(conDetalleTablaTO.getDetDebitos());
                item.setDetDocumento(conDetalleTablaTO.getDetDocumento());
                item.setPisNumero(conDetalleTablaTO.getPisNumero());
                if (conDetalleTablaTO.getSecCodigo() != null) {
                    PrdSector sector = prdSectorDao.obtener(PrdSector.class, new PrdSectorPK(conContable.getConContablePK().getConEmpresa(), conDetalleTablaTO.getSecCodigo()));
                    PrdListaSectorTO prdSectorTO = new PrdListaSectorTO(sector.getPrdSectorPK().getSecCodigo(), sector.getSecNombre(), sector.getSecLatitud(), sector.getSecLongitud(), sector.getSecActivo());
                    item.setSectorSeleccionado(prdSectorTO);
                    item.setListapiscinaSeleccionada(piscinaService.getListaPiscinaTO(conContable.getConContablePK().getConEmpresa().trim(), sector.getPrdSectorPK().getSecCodigo().trim()));
                }
                if (conDetalleTablaTO.getPisNumero() != null) {
                    PrdPiscina piscina = prdPiscinaDao.obtener(PrdPiscina.class, new PrdPiscinaPK(conContable.getConContablePK().getConEmpresa(), conDetalleTablaTO.getSecCodigo(), conDetalleTablaTO.getPisNumero()));
                    PrdListaPiscinaTO prdPiscinaTO = new PrdListaPiscinaTO(piscina.getPrdPiscinaPK().getPisNumero(), piscina.getPisNombre(), piscina.getPisHectareaje(), piscina.getPisActiva(), piscina.getPrdPiscinaPK().getPisSector());
                    item.setPiscinaSeleccionada(prdPiscinaTO);
                }
                item.setDetSecuencia(conDetalleTablaTO.getDetSecuencia());
                detalle.add(item);
                i++;
            }
            respuesta.setDetalle(detalle);
        }
        return respuesta;
    }

    @Override
    public List<ConContablePK> obtenerListadoContablePK(List<ItemStatusItemListaConContableTO> listaConContableTOSeleccion) {
        List<ConContablePK> listContable = new ArrayList<ConContablePK>();
        for (ItemStatusItemListaConContableTO con : listaConContableTOSeleccion) {
            if (con.getConStatus() == null || con.getConStatus().isEmpty() || "BLOQUEADO".equals(con.getConStatus()) || "ANULADO".equals(con.getConStatus()) || "REVERSADO".equals(con.getConStatus())) {
                listContable.add(new ConContablePK(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero()));
            }
        }
        if (listContable.size() != listaConContableTOSeleccion.size()) {
            listContable.clear();
        }
        return listContable;
    }

    @Override
    public ConContable obtenerConContableConDetalle(ConContable conContable, List<ConListaContableDetalleTO> detalle) throws Exception {
        List<ConDetalle> listaConDetalle = new ArrayList<>();
        Integer x = 0;
        for (ConListaContableDetalleTO conListaContableDetalleTO : detalle) {
            ConDetalle conDetalle = new ConDetalle();
            conDetalle.setDetDocumento(conListaContableDetalleTO.getDetDocumento());
            conDetalle.setDetDebitoCredito(conListaContableDetalleTO.getDebidoCredito().charAt(0));
            conDetalle.setDetValor(conListaContableDetalleTO.getSaldo());
            conDetalle.setDetSaldo(BigDecimal.ZERO);
            conDetalle.setDetGenerado(conListaContableDetalleTO.getDetGenerado());
            conDetalle.setDetOrden(x);
            conDetalle.setConContable(conContable);
            conDetalle.setConCuentas(conListaContableDetalleTO.getConCuentas());
            PrdSector sector = new PrdSector(new PrdSectorPK(conContable.getConContablePK().getConEmpresa(), conListaContableDetalleTO.getSectorSeleccionado().getSecCodigo()));
            if (conListaContableDetalleTO.getPiscinaSeleccionada() != null) {
                conDetalle.setPrdPiscina(piscinaService.obtenerPorEmpresaSectorPiscina(
                        conContable.getConContablePK().getConEmpresa(),
                        conListaContableDetalleTO.getSectorSeleccionado().getSecCodigo(),
                        conListaContableDetalleTO.getPiscinaSeleccionada().getPisNumero()));
            } else {
                conDetalle.setPrdPiscina(null);
            }
            conDetalle.setPrdSector(sector);
            conDetalle.setDetReferencia("");
            conDetalle.setDetObservaciones(conListaContableDetalleTO.getDetObservaciones());
            conDetalle.setDetSecuencia((conListaContableDetalleTO.getDetSecuencia() == null) ? null : new Long(conListaContableDetalleTO.getDetSecuencia()));
            x++;
            listaConDetalle.add(conDetalle);
        }
        conContable.setConDetalleList(listaConDetalle);
        return conContable;
    }

    @Override
    public boolean validarCuentaBancos(ConListaContableDetalleTO detalleContableEvaluar, String empresa) throws Exception {
        List<ListaBanCuentaTO> listaBancos = cuentaService.getListaBanCuentaTO(empresa);

        /*PARA VERIFICAR SI EL CHEQUE ESTA POR IMPRIMIR*/
        for (ListaBanCuentaTO listaBanCuentaTO : listaBancos) {
            if (listaBanCuentaTO != null && detalleContableEvaluar != null && detalleContableEvaluar.getConCuentas() != null
                    && listaBanCuentaTO.getCtaCuentaContable().equalsIgnoreCase(
                            detalleContableEvaluar.getConCuentas().getConCuentasPK().getCtaCodigo())) {
                boolean isChequeImprimir = chequeService.isExisteChequeAimprimir(empresa, detalleContableEvaluar.getConCuentas().getConCuentasPK().getCtaCodigo(), detalleContableEvaluar.getDetDocumento(), null);
                if (isChequeImprimir) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean verificarDocumentoBanco(String documento, String cuenta, String empresa) throws Exception {
        List<ListaBanCuentaTO> listaBancos = cuentaService.getListaBanCuentaTO(empresa);
        /*PARA VERIFICAR SI EL CHEQUE ESTA POR IMPRIMIR*/
        for (ListaBanCuentaTO listaBanCuentaTO : listaBancos) {
            if (listaBanCuentaTO.getCtaCuentaContable().equalsIgnoreCase(cuenta)) {
                boolean isChequeImprimir = chequeService.isExisteChequeAimprimir(empresa, cuenta, documento, null);
                if (isChequeImprimir) {
                    return false;
                }
            }
        }
        return true;
    }

    public void crearSuceso(ConContablePK conContablePK, String tipo, SisInfoTO sisInfoTO) {
        String detalle = "";
        switch (tipo) {
            case "U":
                susSuceso = "UPDATE";
                detalle = "Se modificó contable del periodo " + conContablePK.getConPeriodo() + ", del tipo contable " + conContablePK.getConTipo() + ", de la numeracion " + conContablePK.getConNumero();
                break;
            case "I":
                susSuceso = "INSERT";
                detalle = "Se insertó contable del periodo " + conContablePK.getConPeriodo() + ", del tipo contable " + conContablePK.getConTipo() + ", de la numeracion " + conContablePK.getConNumero();
                break;
            case "D":
                susSuceso = "DELETE";
                detalle = "Se eliminó contable del periodo " + conContablePK.getConPeriodo() + ", del tipo contable " + conContablePK.getConTipo() + ", de la numeracion " + conContablePK.getConNumero();
                break;
        }
        susTabla = "contabilidad.con_contable";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisSuceso.setSusClave(conContablePK.getConPeriodo() + " " + conContablePK.getConTipo() + " " + conContablePK.getConNumero());
        sisSuceso.setSusDetalle(detalle);
        sucesoDao.insertar(sisSuceso);
    }

    @Override
    public List<ConFunBalanceGeneralNecTO> convertirConBalanceGeneralTO_ConFunBalanceGeneralNecTO(List<ConBalanceGeneralTO> listaConBalanceGeneralTO) {
        List<ConFunBalanceGeneralNecTO> listConFunBalanceGeneralNecTO = new ArrayList<>();
        listaConBalanceGeneralTO.stream().map((conBalanceGeneralTO) -> {
            ConFunBalanceGeneralNecTO conFunBalanceGeneralNecTO = new ConFunBalanceGeneralNecTO();
            conFunBalanceGeneralNecTO.setBgCuenta(conBalanceGeneralTO.getBgCuenta());
            conFunBalanceGeneralNecTO.setBgDetalle(conBalanceGeneralTO.getBgDetalle());
            conFunBalanceGeneralNecTO.setBgSaldo1(conBalanceGeneralTO.getBgSaldo());
            return conFunBalanceGeneralNecTO;
        }).forEachOrdered((conFunBalanceGeneralNecTO) -> {
            listConFunBalanceGeneralNecTO.add(conFunBalanceGeneralNecTO);
        });
        return listConFunBalanceGeneralNecTO;
    }

    @Override
    public List<ConFunBalanceResultadosNecTO> convertirConBalanceResultadoTO_ConFunBalanceGeneralNecTO(List<ConBalanceResultadoTO> listado) {
        List<ConFunBalanceResultadosNecTO> listaonFunBalanceResultadosNecTO = new ArrayList<ConFunBalanceResultadosNecTO>();
        for (ConBalanceResultadoTO conBalanceResultadoTO : listado) {
            ConFunBalanceResultadosNecTO conFunBalanceResultadoNecTO = new ConFunBalanceResultadosNecTO();
            conFunBalanceResultadoNecTO.setBrCuenta(conBalanceResultadoTO.getBgCuenta());
            conFunBalanceResultadoNecTO.setBrDetalle(conBalanceResultadoTO.getBgDetalle());
            conFunBalanceResultadoNecTO.setBrSaldo1(conBalanceResultadoTO.getBgSaldo());
            listaonFunBalanceResultadosNecTO.add(conFunBalanceResultadoNecTO);
        }
        return listaonFunBalanceResultadosNecTO;
    }

    @Override
    public Map<String, Object> obtenerListasParaContabilizarTodoProceso(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));

        List<ConListaConsultaConsumosTO> listaConsultaConsumosTOs = contableDao.getListaInvConsultaConsumosPendientes(empresa, fechaDesde, fechaHasta);
        if (listaConsultaConsumosTOs != null && !listaConsultaConsumosTOs.isEmpty()) {
            campos.put("mensajeDirecto", "Existen consumos pendientes.");
            campos.put("mensajeIndirecto", "Existen consumos pendientes.");
        } else {
            List<ConFunIPPTO> listadoIndirecto = contableDao.getFunListaIPP(empresa, fechaDesde, fechaHasta, "INDIRECTO");
            List<ConFunIPPTO> listadoDirecto = contableDao.getFunListaIPP(empresa, fechaDesde, fechaHasta, "DIRECTO");
            if (listadoIndirecto == null || listadoIndirecto.isEmpty() || listadoIndirecto.size() == 1) {
                campos.put("mensajeIndirecto", "No se encontraron datos para IPP indirecto");
            } else {
                campos.put("listadoIndirecto", listadoIndirecto);
            }
            if (listadoDirecto == null || listadoDirecto.isEmpty() || listadoDirecto.size() == 1) {
                campos.put("mensajeDirecto", "No se encontraron datos para IPP directo");
            } else {
                campos.put("listadoDirecto", listadoDirecto);
            }
        }
        List<PrdContabilizarCorridaTO> listadoCorrida = corridaDao.getListaContabilizarCorridaTO(empresa, "'" + fechaDesde + "'", "'" + fechaHasta + "'");
        if (listadoCorrida == null || listadoCorrida.isEmpty() || listadoCorrida.size() == 1) {
            campos.put("mensajeCorrida", "No se encontraron datos para corridas");
        }
        campos.put("listadoCorrida", listadoCorrida);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaMayorizarContableRRHH(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        ConContablePK pk = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        boolean mayorizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("mayorizar"));
        String vista = "";
        List<RhUtilidades> utilidades = new ArrayList<>();
        List<RhAnticipo> anticipos = new ArrayList<>();
        List<RhRol> roles = new ArrayList<>();
        List<RhBono> bonos = new ArrayList<>();
        List<RhHorasExtras> horasextras = new ArrayList<>();
        List<RhPrestamo> prestamos = new ArrayList<>();
        List<RhVacaciones> vacaciones;
        List<RhXiiiSueldo> xiiiSueldos = new ArrayList<>();
        List<RhXivSueldo> xivSueldos = new ArrayList<>();
        List<SisEmpresaNotificaciones> listadoConfNoticaciones = sisEmpresaNotificacionesDao.listarSisEmpresaNotificaciones(pk.getConEmpresa());
        SisPeriodo periodo = sisPeriodoDao.obtener(SisPeriodo.class, new SisPeriodoPK(pk.getConEmpresa(), pk.getConPeriodo()));
        ConTipoTO tipo = tipoDao.getConTipoTO(pk.getConEmpresa(), pk.getConTipo());
        ConContable contable = conContableDao.obtener(ConContable.class, pk);
        if (null != TipoRRHH.getTipoRRHH(contable.getConReferencia())) {
            switch (TipoRRHH.getTipoRRHH(contable.getConReferencia())) {
                case ANTICIPO:
                    List<RhComboFormaPagoTO> formasDePago = formaPagoService.getComboFormaPagoTO(pk.getConEmpresa());
                    anticipos = anticipoService.getListRhAnticipo(pk);
                    if (anticipos != null && !anticipos.isEmpty()) {
                        for (RhAnticipo anticipo : anticipos) {
                            RhRolSaldoEmpleadoTO saldoEmpleado = rolService.getRhRolSaldoEmpleado(
                                    anticipo.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                                    anticipo.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                                    UtilsDate.fechaFormatoString(UtilsDate.getPrimerDiaDelMes(contable.getConFecha()), "yyyy-MM-dd"),
                                    UtilsDate.fechaFormatoString(UtilsDate.getUltimoDiaDelMes(contable.getConFecha()), "yyyy-MM-dd"), null);
                            if (saldoEmpleado != null) {
                                anticipo.getRhEmpleado().setEmpSaldoAnticipos(saldoEmpleado.getSaldoAnticipo());
                                anticipo.getRhEmpleado().setEmpSaldoHorasExtras50(saldoEmpleado.getSaldoSaldoHorasExtras50());
                                anticipo.getRhEmpleado().setEmpSaldoHorasExtras100(saldoEmpleado.getSaldoSaldoHorasExtras100());
                                anticipo.getRhEmpleado().setEmpSaldoHorasExtrasExtraordinarias100(saldoEmpleado.getSaldoSaldoHorasExtrasExtraordinarias100());
                                anticipo.getRhEmpleado().setEmpSaldoBonos(saldoEmpleado.getSaldoBono());
                                anticipo.getRhEmpleado().setEmpSaldoBonosNd(saldoEmpleado.getSaldoBonond());
                            }
                        }
                    }
                    campos.put("formasDePago", formasDePago);
                    vista = "ANTICIPO";
                    break;
                case BONO:
                    bonos = bonoService.getListRhBono(pk);
                    if (bonos != null && bonos.get(0) != null) {
                        List<RhListaBonoConceptoTO> conceptos = bonoConceptoService.getListaBonoConceptosTO(pk.getConEmpresa(), false);
                        List<PrdComboPiscinaTO> piscinas = piscinaService.getComboPiscinaTO(pk.getConEmpresa(), bonos.get(0).getPrdSector().getPrdSectorPK().getSecCodigo());
                        campos.put("conceptos", conceptos);
                        campos.put("piscinas", piscinas);
                    }
                    vista = "BONO";
                    break;
                case HORAS_EXTRAS:
                    horasextras = horasExtrasService.getListRhHorasExtras(pk);
                    if (horasextras != null && horasextras.size() > 0 && horasextras.get(0) != null) {
                        for (RhHorasExtras horasextra : horasextras) {
                            RhRolSaldoEmpleadoTO saldoEmpleado = rolService.getRhRolSaldoEmpleado(
                                    horasextra.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                                    horasextra.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                                    UtilsDate.fechaFormatoString(UtilsDate.getPrimerDiaDelMes(contable.getConFecha()), "yyyy-MM-dd"),
                                    UtilsDate.fechaFormatoString(contable.getConFecha(), "yyyy-MM-dd"), null);
                            if (saldoEmpleado != null) {
                                horasextra.getRhEmpleado().setEmpSaldoHorasExtras50(saldoEmpleado.getSaldoSaldoHorasExtras50());
                                horasextra.getRhEmpleado().setEmpSaldoHorasExtras100(saldoEmpleado.getSaldoSaldoHorasExtras100());
                                horasextra.getRhEmpleado().setEmpSaldoHorasExtrasExtraordinarias100(saldoEmpleado.getSaldoSaldoHorasExtrasExtraordinarias100());
                            }
                        }

                        List<RhHorasExtrasConcepto> conceptos = horasExtrasConceptoService.getListaHorasExtrasConceptos(pk.getConEmpresa(), false);
                        List<PrdComboPiscinaTO> piscinas = piscinaService.getComboPiscinaTO(pk.getConEmpresa(), horasextras.get(0).getPrdSector().getPrdSectorPK().getSecCodigo());
                        campos.put("conceptos", conceptos);
                        campos.put("piscinas", piscinas);
                    }
                    vista = "HORAS_EXTRAS";
                    break;
                case PRESTAMO:
                    List<RhComboFormaPagoTO> formasDePagoP = formaPagoService.getComboFormaPagoTO(pk.getConEmpresa());
                    campos.put("formasDePago", formasDePagoP);
                    List<RhPrestamoMotivo> motivos = prestamoMotivoService.getListaRhPrestamoMotivos(pk.getConEmpresa(), false);
                    campos.put("motivos", motivos);
                    prestamos = prestamoService.getListRhPrestamo(pk);
                    vista = "PRESTAMO";
                    break;
                case ROL:
                    List<RhComboFormaPagoTO> formasDePagoR = formaPagoService.getComboFormaPagoTO(pk.getConEmpresa());
                    campos.put("formasDePago", formasDePagoR);
                    if (mayorizar) {
                        RhRolMotivo motivoSeleccionado = rolMotivoDao.obtenerMotivoPorTipoDecomprobante(pk.getConEmpresa(), pk.getConTipo());
                        campos.put("motivoSeleccionado", motivoSeleccionado);
                    }
                    roles = rolService.getListRhRol(pk);
                    if (roles != null && !roles.isEmpty()) {
                        for (RhRol rol : roles) {
                            RhRolSaldoEmpleadoTO saldoEmpleado = rolService.getRhRolSaldoEmpleado(
                                    rol.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                                    rol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                                    UtilsDate.fechaFormatoString(rol.getRolDesde(), "yyyy-MM-dd"),
                                    UtilsDate.fechaFormatoString(rol.getRolHasta(), "yyyy-MM-dd"), null);
                            if (saldoEmpleado != null) {
                                rol.getRhEmpleado().setEmpSaldoPrestamos(saldoEmpleado.getSaldoPrestamo());
                            }
                        }
                    }
                    vista = "ROL";
                    break;
                case UTILIDAD:
                    List<RhComboFormaPagoBeneficioSocialTO> formasDePagoBS = formaPagoBeneficiosSocialesService.getComboFormaPagoBeneficioSocialTO(pk.getConEmpresa());
                    utilidades = utilidadService.getListRhUtilidades(pk);
                    vista = "UTILIDAD";
                    campos.put("formasDePagoBS", formasDePagoBS);
                    break;
                case XIIISUELDO:
                    List<RhComboFormaPagoBeneficioSocialTO> formasDePagoXIII = formaPagoBeneficiosSocialesService.getComboFormaPagoBeneficioSocialTO(pk.getConEmpresa());
                    campos.put("formasDePagoBS", formasDePagoXIII);
                    xiiiSueldos = xiiiSueldoService.getListRhXiiiSueldo(pk);
                    vista = "XIII";
                    break;
                case XIVSUELDO:
                    List<RhComboFormaPagoBeneficioSocialTO> formasDePagoXIV = formaPagoBeneficiosSocialesService.getComboFormaPagoBeneficioSocialTO(pk.getConEmpresa());
                    campos.put("formasDePagoBS", formasDePagoXIV);
                    xivSueldos = xivSueldoService.getListRhXivSueldo(pk);
                    vista = "XIV";
                    BigDecimal salarioMinimo = parametrosDao.getSalarioMinimoVital(contable.getConFecha());
                    campos.put("salarioMinimo", salarioMinimo);
                    break;
                case LIQUIDACION:
                    List<RhComboFormaPagoTO> formasDePagoL = formaPagoService.getComboFormaPagoTO(pk.getConEmpresa());
                    roles = rolService.getListRhRol(pk);
                    if (roles != null && !roles.isEmpty()) {
                        for (RhRol rol : roles) {
                            RhRolSaldoEmpleadoTO saldoEmpleado = rolService.getRhRolSaldoEmpleado(
                                    rol.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                                    rol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                                    UtilsDate.fechaFormatoString(rol.getRolDesde(), "yyyy-MM-dd"),
                                    UtilsDate.fechaFormatoString(rol.getRolHasta(), "yyyy-MM-dd"), null);
                            if (saldoEmpleado != null) {
                                rol.getRhEmpleado().setEmpSaldoAnticipos(saldoEmpleado.getSaldoAnticipo());
                                rol.getRhEmpleado().setEmpSaldoPrestamos(saldoEmpleado.getSaldoPrestamo());
                                rol.getRhEmpleado().setEmpSaldoAnterior(saldoEmpleado.getSaldoAnterior());
                                rol.getRhEmpleado().setEmpSaldoBonos(saldoEmpleado.getSaldoBono());
                            }
                        }
                    }
                    vista = "LIQUIDACION";
                    campos.put("formasDePago", formasDePagoL);
                    break;
                case VACACIONES:
                    List<RhComboFormaPagoTO> formasDePagoV = formaPagoService.getComboFormaPagoTO(pk.getConEmpresa());
                    campos.put("formasDePago", formasDePagoV);
                    vacaciones = vacacionesService.getListRhVacaciones(pk);
                    if (vacaciones != null && !vacaciones.isEmpty()) {
                        RhVacacionesTO vacacionesTO = ConversionesRRHH.convertirRhVacaciones_RhVacacionesTO(vacaciones.get(0));
                        List<ConDetalle> detalles = detalleService.getListConDetalle(pk);
                        detalles.stream().filter((detalle) -> (detalle.getDetDebitoCredito() == 'C')).forEachOrdered((detalle) -> {
                            vacacionesTO.setConDetDocumento(detalle.getDetDocumento());
                        });
                        campos.put("vacaciones", vacacionesTO);
                    }
                    vista = "VACACIONES";
                    break;
                default:
                    break;
            }
        }
        campos.put("listadoConfNoticaciones", listadoConfNoticaciones);
        campos.put("utilidades", utilidades);
        campos.put("anticipos", anticipos);
        campos.put("roles", roles);
        campos.put("bonos", bonos);
        campos.put("horasextras", horasextras);
        campos.put("prestamos", prestamos);
        campos.put("xiiiSueldos", xiiiSueldos);
        campos.put("xivSueldos", xivSueldos);
        campos.put("contable", contable);
        campos.put("vista", vista);
        campos.put("periodo", periodo);
        campos.put("tipo", tipo);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudContable(String empresa, boolean mostrarTodos) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        List<ListaBanCuentaTO> cuentaBanco = cuentaService.getListaBanCuentaTO(empresa);

        List<ConTipoTO> tipos = tipoDao.getListaConTipoTO(empresa);
        List<PrdListaPiscinaTO> listaPiscinas = new ArrayList<>();
        List<PrdListaSectorTO> listBuscarSectorTO = sectorService.getListaSectorTO(empresa.trim(), mostrarTodos);
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa.trim());
        if (listBuscarSectorTO != null && listBuscarSectorTO.size() > 0) {
            listaPiscinas = piscinaService.getListaPiscinaTO(empresa.trim(), listBuscarSectorTO.get(0).getSecCodigo(), mostrarTodos);
        }

        campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("cuentaBanco", cuentaBanco);
        campos.put("tipos", tipos);
        campos.put("fechaActual", fechaActual);
        campos.put("listaPiscinas", listaPiscinas);
        campos.put("listaPeriodos", listaPeriodos);
        campos.put("listaSectores", listBuscarSectorTO);

        return campos;
    }

    @Override
    public List<ConFunContablesVerificacionesTO> getFunContablesVerificaciones(String empresa, String fechaInicio, String fechaFin) throws Exception {
        return contableDao.getFunContablesVerificacion(empresa, fechaInicio, fechaFin);
    }

    @Override
    public List<String> enviarPorCorreoErroresDeContabilidad(Map<String, Object> map, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<File> listAdjunto = new ArrayList<>();
        List<String> respuestas = new ArrayList<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<SisUsuarioEmailTO> destinatarios = usuarioDetalleService.obtenerCorreosADM(empresa);
        SisEmpresaParametros e = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);

        SisUsuarioEmailTO usuarioEmpresa = new SisUsuarioEmailTO(empresa, empresa, usuarioEmpresaReporteTO.getEmpRazonSocial(), "", e.getEmpCodigo().getEmpEmail());
        if (destinatarios != null && !destinatarios.isEmpty()) {
            destinatarios.add(usuarioEmpresa);
        } else {
            destinatarios = new ArrayList<>();
            destinatarios.add(usuarioEmpresa);
        }

        List<ConFunContablesVerificacionesTO> listaContableErrores = UtilsJSON.jsonToList(ConFunContablesVerificacionesTO.class, map.get("listaContableErrores"));
        if (listaContableErrores != null && !listaContableErrores.isEmpty()) {
            File filePDF = reporteContabilidadService.generarReporteContablesVerificacionesErroresFile(usuarioEmpresaReporteTO, listaContableErrores);
            if (filePDF != null) {
                File file = genericReporteService.respondeServidorCorreo(filePDF, "Contables_Errores");
                if (file != null) {
                    listAdjunto.add(file);
                } else {
                    respuestas.add("Ocurrio un error al generar el documento de contables con errores.");
                }
            } else {
                respuestas.add("Ocurrio un error al generar el documento de contables con errores.");
            }
        }
        List<InventarioProductosEnProcesoErroresCompra> listaCompraErrores = UtilsJSON.jsonToList(InventarioProductosEnProcesoErroresCompra.class, map.get("listaCompraErrores"));
        if (listaCompraErrores != null && !listaCompraErrores.isEmpty()) {
            File filePDF = reporteContabilidadService.generarReporteComprasVerificacionesErroresFile(usuarioEmpresaReporteTO, listaCompraErrores);
            if (filePDF != null) {
                File file = genericReporteService.respondeServidorCorreo(filePDF, "Compras_Errores");
                if (file != null) {
                    listAdjunto.add(file);
                } else {
                    respuestas.add("Ocurrio un error al generar el documento de compras con errores.");
                }
            } else {
                respuestas.add("Ocurrio un error al generar el documento de compras con errores.");
            }
        }
        List<InventarioProductosEnProcesoErroresConsumo> listaConsumosErrores = UtilsJSON.jsonToList(InventarioProductosEnProcesoErroresConsumo.class, map.get("listaConsumosErrores"));
        if (listaConsumosErrores != null && !listaConsumosErrores.isEmpty()) {
            File filePDF = reporteContabilidadService.generarReporteConsumosVerificacionesErroresFile(usuarioEmpresaReporteTO, listaConsumosErrores);
            if (filePDF != null) {
                File file = genericReporteService.respondeServidorCorreo(filePDF, "Consumos_Errores");
                if (file == null) {
                    respuestas.add("Ocurrio un error al generar el documento de consumos con errores.");
                } else {
                    listAdjunto.add(file);
                }
            } else {
                respuestas.add("Ocurrio un error al generar el documento de consumos con errores.");
            }
        }
        List<InventarioProductosCuentasInconsistentes> listaProductoErrores = UtilsJSON.jsonToList(InventarioProductosCuentasInconsistentes.class, map.get("listaProductoErrores"));
        if (listaProductoErrores != null && !listaProductoErrores.isEmpty()) {
            File filePDF = genericReporteService.generarFile("contabilidad", "ReporteProductosInconsistencias.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listaProductoErrores);
            if (filePDF != null) {
                File file = genericReporteService.respondeServidorCorreo(filePDF, "Productos_Errores");
                if (file == null) {
                    respuestas.add("Ocurrio un error al generar el documento de productos con errores.");
                } else {
                    listAdjunto.add(file);
                }
            } else {
                respuestas.add("Ocurrio un error al generar el documento de productos con errores.");
            }
        }
        List<InvProductosConErrorTO> listaSaldos = UtilsJSON.jsonToList(InvProductosConErrorTO.class, map.get("listaSaldos"));
        if (listaSaldos != null && !listaSaldos.isEmpty()) {
            File filePDF = reporteInventarioService.generarReporteInvProductosConErrorFile(usuarioEmpresaReporteTO, listaSaldos);
            if (filePDF != null) {
                File file = genericReporteService.respondeServidorCorreo(filePDF, "Saldos_Errores");
                if (file == null) {
                    respuestas.add("Ocurrio un error al generar el documento de saldos con errores.");
                } else {
                    listAdjunto.add(file);
                }
            } else {
                respuestas.add("Ocurrio un error al generar el documento de saldos con errores.");
            }
        }
        List<PrdCorridasInconsistentesTO> corridasInconsistencias = UtilsJSON.jsonToList(PrdCorridasInconsistentesTO.class, map.get("corridasInconsistencias"));
        if (corridasInconsistencias != null && !corridasInconsistencias.isEmpty()) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("desde", desde);
            parametros.put("hasta", hasta);
            File filePDF = genericReporteService.generarFile("produccion", "reportCorridasInconsistentes.jrxml", usuarioEmpresaReporteTO, parametros, corridasInconsistencias);
            if (filePDF != null) {
                File file = genericReporteService.respondeServidorCorreo(filePDF, "Corridas_Errores");
                if (file == null) {
                    respuestas.add("Ocurrio un error al generar el documento de corridas con errores.");
                } else {
                    listAdjunto.add(file);
                }
            } else {
                respuestas.add("Ocurrio un error al generar el documento de corridas con errores.");
            }
        }
        List<SisPeriodoInnecesarioTO> listaPeriodoInnecesarios = UtilsJSON.jsonToList(SisPeriodoInnecesarioTO.class, map.get("listaPeriodoInnecesarios"));
        if (listaPeriodoInnecesarios != null && !listaPeriodoInnecesarios.isEmpty()) {
            File filePDF = genericReporteService.generarFile("sistema", "reportPeriodosErrores.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listaPeriodoInnecesarios);
            if (filePDF != null) {
                File file = genericReporteService.respondeServidorCorreo(filePDF, "Periodos_Errores");
                if (file == null) {
                    respuestas.add("Ocurrio un error al generar el documento de periodos con errores.");
                } else {
                    listAdjunto.add(file);
                }
            } else {
                respuestas.add("Ocurrio un error al generar el documento de periodos con errores.");
            }
        }
        List<ConBalanceResultadosVsInventarioDetalladoTO> balanceResultados = UtilsJSON.jsonToList(ConBalanceResultadosVsInventarioDetalladoTO.class, map.get("balanceResultados"));
        if (balanceResultados != null && !balanceResultados.isEmpty()) {
            File filePDF = reporteContabilidadService.generarReporteConBalanceResultadosVsInventarioDetalladoTOFile(usuarioEmpresaReporteTO, desde, hasta, balanceResultados);
            if (filePDF != null) {
                File file = genericReporteService.respondeServidorCorreo(filePDF, "Balance_Resultados");
                if (file == null) {
                    respuestas.add("Ocurrio un error al generar el documento de Balance de resultados.");
                } else {
                    listAdjunto.add(file);
                }
            } else {
                respuestas.add("Ocurrio un error al generar el documento de Balance de resultados.");
            }
        }
        List<ConFunContablesVerificacionesComprasTO> erroresCompras = UtilsJSON.jsonToList(ConFunContablesVerificacionesComprasTO.class, map.get("erroresCompras"));
        if (erroresCompras != null && !erroresCompras.isEmpty()) {
            File filePDF = reporteContabilidadService.generarReporteContablesVerificacionesComprasFile(usuarioEmpresaReporteTO, desde, hasta, erroresCompras);
            if (filePDF != null) {
                File file = genericReporteService.respondeServidorCorreo(filePDF, "Contable_compras_errores");
                if (file == null) {
                    respuestas.add("Ocurrio un error al generar el documento de Contable de compras con errores.");
                } else {
                    listAdjunto.add(file);
                }
            } else {
                respuestas.add("Ocurrio un error al generar el documento de Contable de compras con errores.");
            }
        }
        List<CarCuentasPorPagarCobrarAnticiposTO> listaCobros = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarAnticiposTO.class, map.get("listaCobros"));
        if (listaCobros != null && !listaCobros.isEmpty()) {
            CarCuentasPorPagarCobrarAnticiposTO ultimaFila = listaCobros.get(listaCobros.size() - 1);
            listaCobros = (ultimaFila.getCxpgSaldo() == null && ultimaFila.getCxpgAnticipos() == null) || (ultimaFila.getCxpgSaldo().compareTo(BigDecimal.ZERO) == 0 && ultimaFila.getCxpgAnticipos().compareTo(BigDecimal.ZERO) == 0) ? new ArrayList<>() : listaCobros;
            if (!listaCobros.isEmpty()) {
                File filePDF = reporteCarteraService.generarReporteCuentasPorCobrarAnticiposFile(usuarioEmpresaReporteTO, listaCobros, hasta);
                if (filePDF != null) {
                    File file = genericReporteService.respondeServidorCorreo(filePDF, "Cuentas_Por_Cobrar");
                    if (file == null) {
                        respuestas.add("Ocurrio un error al generar el documento de Cuentas por cobrar.");
                    } else {
                        listAdjunto.add(file);
                    }
                } else {
                    respuestas.add("Ocurrio un error al generar el documento de Cuentas por cobrar.");
                }
            }
        }
        List<CarCuentasPorPagarCobrarAnticiposTO> listaPagos = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarAnticiposTO.class, map.get("listaPagos"));
        if (listaPagos != null && !listaPagos.isEmpty()) {
            CarCuentasPorPagarCobrarAnticiposTO ultimaFila = listaPagos.get(listaPagos.size() - 1);
            listaPagos = (ultimaFila.getCxpgSaldo() == null && ultimaFila.getCxpgAnticipos() == null) || (ultimaFila.getCxpgSaldo().compareTo(BigDecimal.ZERO) == 0 && ultimaFila.getCxpgAnticipos().compareTo(BigDecimal.ZERO) == 0) ? new ArrayList<>() : listaPagos;
            if (!listaPagos.isEmpty()) {
                File filePDF = reporteCarteraService.generarReporteCarCuentasPorPagarCobrarAnticiposFile(usuarioEmpresaReporteTO, listaPagos, hasta);
                if (filePDF != null) {
                    File file = genericReporteService.respondeServidorCorreo(filePDF, "Cuentas_Por_Cobrar");
                    if (file == null) {
                        respuestas.add("Ocurrio un error al generar el documento de Cuentas por cobrar.");
                    } else {
                        listAdjunto.add(file);
                    }
                } else {
                    respuestas.add("Ocurrio un error al generar el documento de Cuentas por cobrar.");
                }
            }
        }
        if (listAdjunto.isEmpty()) {
            respuestas.add(0, "No hay archivos para adjuntar en el correo.");
        } else {
            ConVerificacionErrores ve = new ConVerificacionErrores();
            String bucket = e.getParRutaImagen();
            Bucket b = AmazonS3Crud.crearBucket(bucket);
            if (b != null) {

                PDFMergerUtility ut = new PDFMergerUtility();
                File temp = File.createTempFile("temp", ".pdf");
                for (File file : listAdjunto) {
                    ut.addSource(file);
                    ut.setDestinationFileName(temp.getAbsolutePath());
                    ut.mergeDocuments();
                }
                String nombre = UtilsString.generarNombreAmazonS3() + ".pdf";
                String carpeta = "contables-errores/" + empresa + "/";
                AmazonS3Crud.subirFile(bucket, carpeta + nombre, temp, "application/pdf");

                ve.setUsrCodigo(usuarioEmpresaReporteTO.getUsrNick());
                ve.setVeSecuencial(0);
                ve.setVeEmpresa(empresa);
                ve.setUsrFechaInserta(new Date());
                ve.setVeUrlEvidencias("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                verificacionService.insertar(ve, sisInfoTO);
            } else {
                throw new GeneralException("Error al crear contenedor de imágenes.");
            }

            String respuesta = enviarCorreoService.notificarPorCorreoErroresContabilidad(usuarioEmpresaReporteTO, listAdjunto, destinatarios, ve.getVeSecuencial());
            respuestas.add(0, respuesta);
        }
        return respuestas;
    }

    @Override
    public Map<String, Object> obtenerListasVerificacionConErrores(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));

        List<ConFunContablesVerificacionesTO> listaContableErrores = contableDao.getFunContablesVerificacion(empresa, fechaInicio, fechaFin);
        List<InventarioProductosEnProcesoErroresCompra> listaCompraErrores = productosEnProcesoErroresService.listarPppErroresCompra(empresa, fechaInicio, fechaFin);
        List<InventarioProductosEnProcesoErroresConsumo> listaConsumosErrores = productosEnProcesoErroresService.listarPppErroresConsumo(empresa, fechaInicio, fechaFin);
        List<InventarioProductosCuentasInconsistentes> listaProductoErrores = productosEnProcesoErroresService.productosCuentasInconsitentes(empresa);
        List<InvProductosConErrorTO> listaSaldos = productoService.getListadoProductosConError(empresa);
        List<PrdCorridasInconsistentesTO> corridasInconsistencias = productosEnProcesoErroresService.listarCorridasInconsistentes(empresa, fechaInicio, fechaFin);
        List<SisPeriodoInnecesarioTO> listaPeriodoInnecesarios = periodoService.getSisPeriodoInnecesarioTO(empresa);
        List<ConBalanceResultadosVsInventarioDetalladoTO> balanceResultados = contableDao.getConListaBalanceResultadosVsInventarioDetalladoTO(empresa, fechaInicio, fechaFin, null, true);
        List<ConFunContablesVerificacionesComprasTO> erroresCompras = contableDao.getConFunContablesVerificacionesComprasTOs(empresa, fechaInicio, fechaFin);
        List<CarCuentasPorPagarCobrarAnticiposTO> listaCobros = cobrosService.getCarListaCuentasPorCobrarAnticiposTO(empresa, null, fechaFin);
        List<CarCuentasPorPagarCobrarAnticiposTO> listaPagos = pagosService.getCarListaCuentasPorPagarAnticiposTO(empresa, null, fechaFin);
        List<AnxDiferenciasTributacionTO> listaDiferenciasTributacion = comprasDao.listarDiferenciasTributacion(empresa, fechaInicio, fechaFin);

        campos.put("listaContableErrores", listaContableErrores);
        campos.put("listaCompraErrores", listaCompraErrores);
        campos.put("listaConsumosErrores", listaConsumosErrores);
        campos.put("listaProductoErrores", listaProductoErrores);
        campos.put("listaSaldos", listaSaldos);
        campos.put("corridasInconsistentes", corridasInconsistencias);
        campos.put("listaPeriodoInnecesarios", listaPeriodoInnecesarios);
        campos.put("balanceResultados", balanceResultados);
        campos.put("erroresCompras", erroresCompras);
        campos.put("listaCobros", listaCobros);
        campos.put("listaPagos", listaPagos);
        campos.put("empresa", empresa);
        campos.put("listaDiferenciasTributacion", listaDiferenciasTributacion);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosMayorAuxiliar(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<ConEstructuraTO> estructura = estructuraDao.getListaConEstructuraTO(empresa);
        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);
        List<BanComboBancoTO> listaCuentasBanco = bancoCuentaDao.getBanComboBancoTO(empresa);
        Date fechaInicio = sistemaWebServicio.getFechaInicioMes();

        campos.put("estructura", estructura);
        campos.put("listaSectores", listaSectores);
        campos.put("listaSisPeriodoTO", listaSisPeriodoTO);
        campos.put("fechaInicio", fechaInicio);
        campos.put("listaCuentasBanco", listaCuentasBanco);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosContable(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String referencia = UtilsJSON.jsonToObjeto(String.class, map.get("referencia"));

        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa);
        List<ConEstructuraTO> estructura = estructuraDao.getListaConEstructuraTO(empresa);
        List<ConTipoTO> listaTipos = null;

        if (referencia != null) {
            List<ConTipo> respuesta = tipoDao.getListaConTipo(empresa, referencia);
            List<ConTipoTO> respuesTO = new ArrayList<>();
            if (respuesta != null && !respuesta.isEmpty()) {
                for (int i = 0; i < respuesta.size(); i++) {
                    ConTipoTO tipoTO = ConversionesContabilidad.convertirConTipo_ConTipoTO(respuesta.get(i));
                    respuesTO.add(tipoTO);
                }
                listaTipos = respuesTO;
            }
        } else {
            listaTipos = tipoDao.getListaConTipoTO(empresa);
        }

        campos.put("listaSectores", listaSectores);
        campos.put("listaPeriodos", listaPeriodos);
        campos.put("listaTipos", listaTipos);
        campos.put("estructura", estructura);

        return campos;
    }

    @Override
    public ItemListaContableTO consultaContableVirtual(ConContablePK conContablePK, String tipoContableVirtual) throws Exception {
        ItemListaContableTO respuesta = new ItemListaContableTO();

        List<PrdListaSectorTO> listBuscarSectorTO = sectorService.getListaSectorTO(conContablePK.getConEmpresa(), true);
        respuesta.setListasectorSeleccionado(listBuscarSectorTO);

        List<ConDetalleTablaTO> detalleAuxiliar = null;
        List<ConListaContableDetalleTO> detalle = new ArrayList<ConListaContableDetalleTO>();

        if (tipoContableVirtual.equals("inventario.inv_consumos")) {
            detalleAuxiliar = contableDao.obtenerContableVirtualConsumo(conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(), conContablePK.getConNumero());
        } else {
            detalleAuxiliar = contableDao.obtenerContableVirtualVenta(conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(), conContablePK.getConNumero());
        }

        for (ConDetalleTablaTO conDetalleTablaTO : detalleAuxiliar) {
            ConListaContableDetalleTO item = new ConListaContableDetalleTO();
            ConCuentas conCuenta = conCuentaDao.obtener(ConCuentas.class, new ConCuentasPK(conContablePK.getConEmpresa(), conDetalleTablaTO.getCtaCodigo()));
            item.setConCuentas(conCuenta);
            item.setCtaCodigo(conDetalleTablaTO.getCtaCodigo());
            item.setCtaDetalle(conDetalleTablaTO.getCtaDetalle());
            item.setDetGenerado(conDetalleTablaTO.getDetGenerado());
            item.setConFilaEstado(true);
            item.setConEstado(true);
            item.setConCuentaVacia(false);
            item.setConChequeImprimir(false);
            item.setConChequeRepetido(false);
            item.setConEstadoDebitoCreditoValido(true);
            if (conDetalleTablaTO.getDetCreditos().compareTo(BigDecimal.ZERO) == 0) {
                item.setDebidoCredito("D");
                item.setSaldo(conDetalleTablaTO.getDetDebitos());
            }
            if (conDetalleTablaTO.getDetDebitos().compareTo(BigDecimal.ZERO) == 0) {
                item.setDebidoCredito("C");
                item.setSaldo(conDetalleTablaTO.getDetCreditos());
            }
            item.setDetCreditos(conDetalleTablaTO.getDetCreditos());
            item.setDetDebitos(conDetalleTablaTO.getDetDebitos());
            item.setDetDocumento(conDetalleTablaTO.getDetDocumento());
            item.setPisNumero(conDetalleTablaTO.getPisNumero());
            if (conDetalleTablaTO.getSecCodigo() != null) {
                PrdSector sector = prdSectorDao.obtener(PrdSector.class, new PrdSectorPK(conContablePK.getConEmpresa(), conDetalleTablaTO.getSecCodigo()));
                PrdListaSectorTO prdSectorTO = new PrdListaSectorTO(sector.getPrdSectorPK().getSecCodigo(), sector.getSecNombre(), sector.getSecLatitud(), sector.getSecLongitud(), sector.getSecActivo());
                item.setSectorSeleccionado(prdSectorTO);
                item.setListapiscinaSeleccionada(piscinaService.getListaPiscinaTO(conContablePK.getConEmpresa().trim(), sector.getPrdSectorPK().getSecCodigo().trim()));
            }
            if (conDetalleTablaTO.getPisNumero() != null) {
                PrdPiscina piscina = prdPiscinaDao.obtener(PrdPiscina.class, new PrdPiscinaPK(conContablePK.getConEmpresa(), conDetalleTablaTO.getSecCodigo(), conDetalleTablaTO.getPisNumero()));
                PrdListaPiscinaTO prdPiscinaTO = new PrdListaPiscinaTO(piscina.getPrdPiscinaPK().getPisNumero(), piscina.getPisNombre(), piscina.getPisHectareaje(), piscina.getPisActiva(), piscina.getPrdPiscinaPK().getPisSector());
                item.setPiscinaSeleccionada(prdPiscinaTO);
            }
            item.setDetSecuencia(conDetalleTablaTO.getDetSecuencia());
            detalle.add(item);
        }
        respuesta.setDetalle(detalle);
        return respuesta;
    }

    @Override
    public Map<String, Object> obtenerDatosDiarioAuxiliar(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<ConTipoTO> listaTipos = tipoDao.getListaConTipoTO(empresa);
        Date fechaInicioMes = sistemaWebServicio.getFechaInicioMes();
        Date fechaFinMes = sistemaWebServicio.getFechaFinMes();

        campos.put("listaTipos", listaTipos);
        campos.put("fechaInicioMes", fechaInicioMes);
        campos.put("fechaFinMes", fechaFinMes);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaContabilizarDatos(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<ConTipoTO> listaTipos = tipoDao.getListaConTipoTO(empresa);
        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);

        List<ConEstructuraTO> tamanioEstructura = estructuraDao.getListaConEstructuraTO(empresa);
        Date fechaInicioMes = sistemaWebServicio.getFechaInicioMes();
        Date fechaFinMes = sistemaWebServicio.getFechaFinMes();
        Integer columnaEstadoFinanciero = empresaParametrosDao.getColumnasEstadosFinancieros(empresa);

        campos.put("listaTipos", listaTipos);
        campos.put("listaSectores", listaSectores);
        campos.put("tamanioEstructura", tamanioEstructura);
        campos.put("fechaInicioMes", fechaInicioMes);
        campos.put("fechaFinMes", fechaFinMes);
        campos.put("columnaEstadoFinanciero", columnaEstadoFinanciero);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosEstadoResultadoIntegralCompar(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<ConEstructuraTO> tamanioEstructura = estructuraDao.getListaConEstructuraTO(empresa);
        Date fechaInicioMes = sistemaWebServicio.getFechaInicioMes();
        Date fechaFinMes = sistemaWebServicio.getFechaFinMes();
        Date fechaActual = sistemaWebServicio.getFechaActual();

        campos.put("listaSectores", listaSectores);
        campos.put("tamanioEstructura", tamanioEstructura);
        campos.put("fechaInicioMes", fechaInicioMes);
        campos.put("fechaFinMes", fechaFinMes);
        campos.put("fechaActual", fechaActual);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosConsultaContabilidad(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<ConEstructuraTO> tamanioEstructura = estructuraDao.getListaConEstructuraTO(empresa);

        List<SisPeriodo> listaPeriodos = null;
        SisPeriodo periodoSeleccionado = new SisPeriodo();

        listaPeriodos = periodoService.getListaPeriodo(empresa);
        if (listaPeriodos != null && !listaPeriodos.isEmpty()) {
            for (int i = 0; i < listaPeriodos.size(); i++) {
                if (!listaPeriodos.get(i).getPerCerrado() && periodoSeleccionado.getPerDesde() == null) {
                    periodoSeleccionado = listaPeriodos.get(i);
                }
            }
        }
        campos.put("listaSectores", listaSectores);
        campos.put("periodoSeleccionado", periodoSeleccionado);
        campos.put("tamanioEstructura", tamanioEstructura);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosConsultaComprobanteContable(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<ConTipoTO> listaTipos = tipoDao.getListaConTipoTO(empresa);
        List<ConEstructuraTO> tamanioEstructura = estructuraDao.getListaConEstructuraTO(empresa);
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa);

        campos.put("listaTipos", listaTipos);
        campos.put("tamanioEstructura", tamanioEstructura);
        campos.put("listaPeriodos", listaPeriodos);

        return campos;
    }

    /*IMAGENES*/
    @Override
    public List<ConAdjuntosContableWebTO> convertirStringUTF8(ConContablePK conContablePK
    ) {
        List<ConAdjuntosContable> listadoAdjuntos = null;
        List<ConAdjuntosContableWebTO> listaRespuesta = new ArrayList<>();
        try {
            listadoAdjuntos = contableDao.getAdjuntosContable(conContablePK);
            for (ConAdjuntosContable invAdjunto : listadoAdjuntos) {
                ConAdjuntosContableWebTO conAdjuntosContableWebTO = new ConAdjuntosContableWebTO();
                conAdjuntosContableWebTO.setAdjTipo(invAdjunto.getAdjTipo());
                conAdjuntosContableWebTO.setAdjSecuencial(invAdjunto.getAdjSecuencial());
                conAdjuntosContableWebTO.setConContable(invAdjunto.getConContable());
                conAdjuntosContableWebTO.setAdjArchivo(invAdjunto.getAdjArchivo());
                conAdjuntosContableWebTO.setAdjBucket(invAdjunto.getAdjBucket());
                conAdjuntosContableWebTO.setAdjClaveArchivo(invAdjunto.getAdjClaveArchivo());
                conAdjuntosContableWebTO.setAdjUrlArchivo(invAdjunto.getAdjUrlArchivo());
                conAdjuntosContableWebTO.setAdjVerificado(invAdjunto.isAdjVerificado());
//                conAdjuntosContableWebTO.setImagenString(new String(invAdjunto.getAdjArchivo(), "UTF-8"));
                listaRespuesta.add(conAdjuntosContableWebTO);
            }
        } catch (Exception ex) {
            Logger.getLogger(ContableServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaRespuesta;
    }

    public boolean insertarImagenesContables(List<ConAdjuntosContableWebTO> listado, ConContablePK conContablePK) throws Exception {
        boolean respuesta = false;
        if (listado.isEmpty()) {
            respuesta = true;
        }
        String bucket = sistemaWebServicio.obtenerRutaImagen(conContablePK.getConEmpresa());
        Bucket b = AmazonS3Crud.crearBucket(bucket);
        if (b != null) {
            for (ConAdjuntosContableWebTO conAdjuntosContableWebTO : listado) {
                if (conAdjuntosContableWebTO.getAdjSecuencial() == null) {
                    ComboGenericoTO combo = UtilsString.completarDatosAmazon(conAdjuntosContableWebTO.getAdjClaveArchivo(), conAdjuntosContableWebTO.getImagenString());
                    String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
                    String carpeta = "contables/" + conContablePK.getConPeriodo() + "/" + conContablePK.getConTipo() + "/" + conContablePK.getConNumero() + "/";
                    ConAdjuntosContable invAdjunto = new ConAdjuntosContable();
                    invAdjunto.setAdjTipo(conAdjuntosContableWebTO.getAdjTipo());
                    invAdjunto.setConContable(new ConContable(conContablePK));
                    invAdjunto.setAdjBucket(bucket);
                    invAdjunto.setAdjClaveArchivo(carpeta + nombre);
                    invAdjunto.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                    conAdjuntosContableDao.insertar(invAdjunto);
                    AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, conAdjuntosContableWebTO.getImagenString(), combo.getValor());
                    respuesta = true;
                } else {
                    respuesta = true;
                }
            }
        } else {
            throw new GeneralException("Error al crear contenedor de imágenes.");
        }
        return respuesta;
    }

    @Override
    public boolean guardarImagenesContable(List<ConAdjuntosContableWebTO> imagenes, ConContablePK pk, SisInfoTO sisInfoTO) throws Exception {
        List<ConAdjuntosContableWebTO> listaImagenes = new ArrayList<>();
        if (imagenes != null && imagenes.size() > 0) {
            for (ConAdjuntosContableWebTO item : imagenes) {
                /// PREPARANDO OBJETO SISSUCESO
                if (item.getAdjSecuencial() == null) {
                    susClave = pk.getConEmpresa() + "|" + pk.getConPeriodo() + "|" + pk.getConTipo() + "|" + pk.getConNumero();
                    susDetalle = "Se agregó la imágen para el contable: " + susClave;
                    susSuceso = "INSERT";
                    susTabla = "contabilidad.con_contable_datos_adjuntos";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sucesoDao.insertar(sisSuceso);
                    ConAdjuntosContableWebTO imagen = new ConAdjuntosContableWebTO();
                    imagen.setConContable(new ConContable(pk));
                    imagen.setAdjSecuencial(item.getAdjSecuencial());
                    imagen.setAdjTipo(item.getAdjTipo());
                    imagen.setAdjArchivo(item.getImagenString().getBytes("UTF-8"));
                    imagen.setImagenString(item.getImagenString());
                    listaImagenes.add(imagen);
                } else {
                    ConAdjuntosContableWebTO imagen = new ConAdjuntosContableWebTO();
                    imagen.setConContable(new ConContable(pk));
                    imagen.setAdjSecuencial(item.getAdjSecuencial());
                    imagen.setAdjTipo(item.getAdjTipo());
                    imagen.setImagenString(item.getImagenString());
                    listaImagenes.add(imagen);
                }
            }
        }
        actualizarImagenesContables(listaImagenes, pk);
        return true;
    }

    @Override
    public boolean actualizarImagenesContables(List<ConAdjuntosContableWebTO> listado, ConContablePK conContablePK) throws Exception {
        List<ConAdjuntosContable> listAdjuntosEnLaBase = contableDao.getAdjuntosContable(conContablePK);
        if (listado != null && !listado.isEmpty()) {
            if (listAdjuntosEnLaBase.size() > 0) {
                listado.forEach((item) -> {//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                    listAdjuntosEnLaBase.removeIf(n -> (n.getAdjSecuencial().equals(item.getAdjSecuencial())));
                });
                if (listAdjuntosEnLaBase.size() > 0) {
                    listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                        conAdjuntosContableDao.eliminar(itemEliminar);
                        AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                    });
                }
            }
            insertarImagenesContables(listado, conContablePK);
        } else {
            if (listAdjuntosEnLaBase.size() > 0) {
                listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                    conAdjuntosContableDao.eliminar(itemEliminar);
                    AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                });
            }
        }
        return true;
    }

    @Override
    public boolean activarWispro(SisInfoTO sisSuceso, List<CarCobrosDetalleVentasTO> carCobrosDetalleVentas) throws Exception {
        return contableDao.activarWispro(sisSuceso, carCobrosDetalleVentas);
    }
}
