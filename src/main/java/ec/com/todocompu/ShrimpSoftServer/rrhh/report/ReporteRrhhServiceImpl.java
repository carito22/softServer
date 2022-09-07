package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.PaisDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.ProvinciasDao;
import ec.com.todocompu.ShrimpSoftServer.banco.service.BancoService;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.report.ReporteContabilidadService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.DetalleService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.EstructuraService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.TipoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.AnticipoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.BonoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.EmpleadoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.HorasExtrasService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.PrestamoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RolService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.UtilidadService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XiiiSueldoPeriodoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XiiiSueldoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XivSueldoPeriodoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XivSueldoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ItemStatusItemListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.RhPreavisoAnticiposPrestamosSueldoMachalaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.ItemListaRolTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhDetalleVacionesPagadasGozadasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107_2013TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunListadoEmpleadosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetallePrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleUtilidadesLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleViaticosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaRolSaldoEmpleadoDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoSueldosPorPagarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoIndividualAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhOrdenBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoGuayaquilTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoPichinchaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolEmpTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoXiiiSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoXivSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TipoRRHH;
import static ec.com.todocompu.ShrimpSoftUtils.rrhh.UtilsEmpleado.convertirValoresItemDetalleVaciosACero;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasConcepto;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolPagoNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadas;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteAnticipoPrestamoXIIISueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteConsultaAnticiposLote;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteConsultaBonosLote;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteConsultaHorasExtrasLote;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteXivSueldoConsulta;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReportesRol;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.InformacionAdicional;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service
public class ReporteRrhhServiceImpl implements ReporteRrhhService {

    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private RolService rolService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private DetalleService detalleService;
    @Autowired
    private TipoService tipoService;
    private String modulo = "RRHH";
    @Autowired
    private EstructuraService estructuraService;
    @Autowired
    private CuentasService cuentasService;
    @Autowired
    private ReporteContabilidadService reporteContabilidadService;
    @Autowired
    private AnticipoService anticipoService;
    @Autowired
    private PagosAnticiposDao pagoanticipoService;
    @Autowired
    private PaisDao paisDao;
    @Autowired
    private ProvinciasDao provinciasDao;
    @Autowired
    private PrestamoService prestamoService;
    @Autowired
    private UtilidadService utilidadService;
    @Autowired
    private XiiiSueldoService xiiiSueldoService;
    @Autowired
    private XivSueldoService xivSueldoService;
    @Autowired
    private XiiiSueldoPeriodoService xiiiSueldoPeriodoService;
    @Autowired
    private XivSueldoPeriodoService xivSueldoPeriodoService;
    @Autowired
    private EmpresaDao empresaDao;
    @Autowired
    private HorasExtrasService horasExtrasService;
    @Autowired
    private BonoService bonoService;
    @Autowired
    private BancoService bancoService;
    boolean esBio = false;

    @Override
    public byte[] generarReporteListaDetalleVacaionesPagadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String desde, String hasta, String empSector,
            List<RhDetalleVacionesPagadasGozadasTO> listaDetalleVacacionesPagadasGozadasTO) throws Exception {

        List<ReporteListaDetalleVacaionesPagadas> reporteListaDetalleVacaionesPagadas = new ArrayList<ReporteListaDetalleVacaionesPagadas>();

        for (RhDetalleVacionesPagadasGozadasTO rldvpg : listaDetalleVacacionesPagadasGozadasTO) {
            ReporteListaDetalleVacaionesPagadas reporteListaDetalleVacaionesPagada = new ReporteListaDetalleVacaionesPagadas(
                    desde, hasta, empSector, rldvpg.getVacApellidosNombres(), rldvpg.getVacId(),
                    rldvpg.getVacApellidosNombres(), rldvpg.getVacValor(), rldvpg.getVacDesde(), rldvpg.getVacHasta(),
                    rldvpg.getVacGozadasDesde(), rldvpg.getVacGozadasHasta(), rldvpg.getVacObservaciones(),
                    rldvpg.getVacContables());
            reporteListaDetalleVacaionesPagadas.add(reporteListaDetalleVacaionesPagada);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoDetalleVacacionesPagadas.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaDetalleVacaionesPagadas);
    }

    @Override
    public byte[] generarReporteListaDetalleVacaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String desde, String hasta, String empSector,
            List<RhDetalleVacionesPagadasGozadasTO> listaDetalleVacacionesPagadasGozadasTO, String nombreReporte) throws Exception {

        List<ReporteListaDetalleVacaionesPagadas> reporteListaDetalleVacaionesPagadas = new ArrayList<>();

        for (RhDetalleVacionesPagadasGozadasTO rldvpg : listaDetalleVacacionesPagadasGozadasTO) {
            ReporteListaDetalleVacaionesPagadas reporteListaDetalleVacaionesPagada = new ReporteListaDetalleVacaionesPagadas(
                    desde, hasta, empSector, rldvpg.getVacApellidosNombres(), rldvpg.getVacId(),
                    rldvpg.getVacApellidosNombres(), rldvpg.getVacValor(), rldvpg.getVacDesde(), rldvpg.getVacHasta(),
                    rldvpg.getVacGozadasDesde(), rldvpg.getVacGozadasHasta(), rldvpg.getVacObservaciones(),
                    rldvpg.getVacContables());
            reporteListaDetalleVacaionesPagadas.add(reporteListaDetalleVacaionesPagada);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte,
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaDetalleVacaionesPagadas);
    }

    @Override
    public byte[] generarReporteListaVacacionesGozadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, String empSector, List<RhVacacionesGozadas> lista, String nombre) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("p_sector", empSector);
        parametros.put("p_desde", desde);
        parametros.put("p_hasta", hasta);
        return genericReporteService.generarReporte(modulo, nombre, usuarioEmpresaReporteTO, parametros, lista);
    }

    @Override
    public byte[] generarReporteListaVacacionesGozadasIndividual(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhVacacionesGozadas> lista, String nombre) throws Exception {
        List<ReporteSolicitudVacaciones> listadoReportes = new ArrayList<ReporteSolicitudVacaciones>();
        for (RhVacacionesGozadas item : lista) {
            ReporteSolicitudVacaciones report = new ReporteSolicitudVacaciones();
            report.setVacNombresEmpleado(item.getRhEmpleado().getEmpApellidos() + " " + item.getRhEmpleado().getEmpNombres());
            report.setVacCedulaEmpleado(item.getRhEmpleado().getRhEmpleadoPK().getEmpId());
            report.setVacCargo(item.getRhEmpleado().getEmpCargo());
            report.setVacNumero(item.getRhVacacionesGozadasPK().getVacNumero());
            report.setVacFechaSolicitud(UtilsDate.fechaFormatoString(item.getVacFecha(), "dd-MM-yyyy"));
            report.setVacPeriodoDesde(UtilsDate.fechaFormatoString(item.getVacGozadasDesde(), "dd-MM-yyyy"));
            report.setVacPeriodoHasta(UtilsDate.fechaFormatoString(item.getVacGozadasHasta(), "dd-MM-yyyy"));
            report.setVacObservaciones(item.getVacObservaciones());
            listadoReportes.add(report);
        }
        return genericReporteService.generarReporte(modulo, nombre, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoReportes);
    }

    @Override
    public byte[] generarReporteConsolidadoAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta,
            List<RhListaConsolidadoAnticiposPrestamosTO> listaConsolidadoAnticiposPrestamosTO) throws Exception {

        List<RhConsolidadoAnticiposPrestamos> rhConsolidadoAnticiposPrestamoses = new ArrayList<RhConsolidadoAnticiposPrestamos>();
        RhConsolidadoAnticiposPrestamos rhConsolidadoAnticiposPrestamos = null;
        for (int i = 0; i < listaConsolidadoAnticiposPrestamosTO.size(); i++) {
            rhConsolidadoAnticiposPrestamos = new RhConsolidadoAnticiposPrestamos();
            rhConsolidadoAnticiposPrestamos.setFechaDesde(fechaDesde);
            rhConsolidadoAnticiposPrestamos.setFechaHasta(fechaHasta);
            rhConsolidadoAnticiposPrestamos.setCapCategoria(listaConsolidadoAnticiposPrestamosTO.get(i).getCapCategoria());
            rhConsolidadoAnticiposPrestamos.setCapId(listaConsolidadoAnticiposPrestamosTO.get(i).getCapId());
            rhConsolidadoAnticiposPrestamos.setCapNombres(listaConsolidadoAnticiposPrestamosTO.get(i).getCapNombres());
            rhConsolidadoAnticiposPrestamos.setCapAnticipos(listaConsolidadoAnticiposPrestamosTO.get(i).getCapAnticipos());
            rhConsolidadoAnticiposPrestamos.setCapPrestamos(listaConsolidadoAnticiposPrestamosTO.get(i).getCapPrestamos());
            rhConsolidadoAnticiposPrestamos.setCapTotal(listaConsolidadoAnticiposPrestamosTO.get(i).getCapTotal());
            rhConsolidadoAnticiposPrestamos.setCapOrden(listaConsolidadoAnticiposPrestamosTO.get(i).getCapOrden());
            rhConsolidadoAnticiposPrestamoses.add(rhConsolidadoAnticiposPrestamos);
        }

        return genericReporteService.generarReporte(modulo + "/RC16", "reportRRHHConsolidadoAnticiposPrestamos.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), rhConsolidadoAnticiposPrestamoses);

    }

    @Override
    public byte[] generarReporteDetalleAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empId,
            String empCodigo, String empCategoria, String fechaDesde, String fechaHasta,
            List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO, String nombre) throws Exception {

        List<ReporteDetalleAnticipos> reporteDetalleAnticipos = new ArrayList<ReporteDetalleAnticipos>();
        for (RhListaDetalleAnticiposTO rldvpg : listaDetalleAnticiposTO) {
            ReporteDetalleAnticipos reporteDetalleAnticipo = new ReporteDetalleAnticipos(empCodigo, fechaDesde,
                    fechaHasta, empCategoria, empId,
                    // plgTO.getGraFecha() == null ? "" :
                    // plgTO.getGraFecha(),
                    // plgTO.getGratGrande() == null ? null :
                    // plgTO.getGratGrande(),
                    rldvpg.getDaCategoria() == null ? "" : rldvpg.getDaCategoria(),
                    rldvpg.getDaFecha() == null ? "" : rldvpg.getDaFecha(),
                    rldvpg.getDaId() == null ? "" : rldvpg.getDaId(),
                    rldvpg.getDaNombres() == null ? "" : rldvpg.getDaNombres(),
                    rldvpg.getDaValor() == null ? null : rldvpg.getDaValor(),
                    rldvpg.getDaFormaPago() == null ? "" : rldvpg.getDaFormaPago(),
                    rldvpg.getDaDocumento() == null ? "" : rldvpg.getDaDocumento(),
                    rldvpg.getDaContable() == null ? "" : rldvpg.getDaContable(),
                    rldvpg.getDaAnulado() == null ? false : rldvpg.getDaAnulado());
            reporteDetalleAnticipo.setDaObservacion(rldvpg.getDaObservaciones() == null ? "" : rldvpg.getDaObservaciones());
            reporteDetalleAnticipos.add(reporteDetalleAnticipo);
        }
        return genericReporteService.generarReporte(modulo, nombre + ".jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), reporteDetalleAnticipos);
        //
    }

    @Override
    public byte[] generarReporteDetalleAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String empCodigo, String fechaDesde, String fechaHasta, String empCategoria, String empId,
            List<RhListaDetalleAnticiposPrestamosTO> listaDetalleAnticiposPrestamosTO, String nombre) throws Exception {

        List<ReporteDetalleAnticiposPrestamos> reporteDetalleAnticiposPrestamos = new ArrayList<ReporteDetalleAnticiposPrestamos>();
        for (RhListaDetalleAnticiposPrestamosTO rldvpg : listaDetalleAnticiposPrestamosTO) {
            ReporteDetalleAnticiposPrestamos reporteDetalleAnticiposPrestamo = new ReporteDetalleAnticiposPrestamos();
            reporteDetalleAnticiposPrestamo.setEmpCodigo(empCodigo);
            reporteDetalleAnticiposPrestamo.setFechaDesde(fechaDesde);
            reporteDetalleAnticiposPrestamo.setFechaHasta(fechaHasta);
            reporteDetalleAnticiposPrestamo.setEmpCategoria(empCategoria);
            reporteDetalleAnticiposPrestamo.setEmpId(empId);

            reporteDetalleAnticiposPrestamo.setDapTipo((rldvpg.getDapTipo() == null ? "" : rldvpg.getDapTipo()));
            reporteDetalleAnticiposPrestamo.setDapCategoria(rldvpg.getDapCategoria() == null ? "" : rldvpg.getDapCategoria());
            reporteDetalleAnticiposPrestamo.setDapFecha(rldvpg.getDapFecha() == null ? "" : rldvpg.getDapFecha());
            reporteDetalleAnticiposPrestamo.setDapId(rldvpg.getDapId() == null ? "" : rldvpg.getDapId());
            reporteDetalleAnticiposPrestamo.setDapNombres(rldvpg.getDapNombres() == null ? "" : rldvpg.getDapNombres());
            reporteDetalleAnticiposPrestamo.setDapValor((rldvpg.getDapValor() == null ? null : rldvpg.getDapValor()));
            reporteDetalleAnticiposPrestamo.setDapFormaPago(rldvpg.getDapFormaPago() == null ? "" : rldvpg.getDapFormaPago());
            reporteDetalleAnticiposPrestamo.setDapDocumento(rldvpg.getDapDocumento() == null ? "" : rldvpg.getDapDocumento());
            reporteDetalleAnticiposPrestamo.setDapContable(rldvpg.getDapContable() == null ? "" : rldvpg.getDapContable());

            reporteDetalleAnticiposPrestamos.add(reporteDetalleAnticiposPrestamo);
        }
        return genericReporteService.generarReporte(modulo, nombre + ".jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), reporteDetalleAnticiposPrestamos);
    }

    @Override
    public byte[] generarReporteDetallePrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empCodigo,
            String fechaDesde, String fechaHasta, String empCategoria, String empId,
            List<RhListaDetallePrestamosTO> listaDetallePrestamosTO) throws Exception {

        List<ReporteDetallePrestamos> reporteDetallePrestamos = new ArrayList<ReporteDetallePrestamos>();
        for (RhListaDetallePrestamosTO rldvpg : listaDetallePrestamosTO) {
            ReporteDetallePrestamos reporteDetallePrestamo = new ReporteDetallePrestamos();
            reporteDetallePrestamo.setEmpCodigo(empCodigo);
            reporteDetallePrestamo.setFechaDesde(fechaDesde);
            reporteDetallePrestamo.setFechaHasta(fechaHasta);
            reporteDetallePrestamo.setEmpCategoria(empCategoria);
            reporteDetallePrestamo.setEmpId(empId);
            //
            // reporteDetallePrestamo.setDapTipo((rldvpg.getDapTipo() ==
            // null ? "" : rldvpg.getDapTipo()));
            reporteDetallePrestamo.setDpCategoria(rldvpg.getDpCategoria() == null ? "" : rldvpg.getDpCategoria());
            reporteDetallePrestamo.setDpFecha(rldvpg.getDpFecha() == null ? "" : rldvpg.getDpFecha());
            reporteDetallePrestamo.setDpId(rldvpg.getDpId() == null ? "" : rldvpg.getDpId());
            reporteDetallePrestamo.setDpNombres(rldvpg.getDpNombres() == null ? "" : rldvpg.getDpNombres());
            reporteDetallePrestamo.setDpValor((rldvpg.getDpValor() == null ? null : rldvpg.getDpValor()));
            reporteDetallePrestamo.setDpFormaPago(rldvpg.getDpFormaPago() == null ? "" : rldvpg.getDpFormaPago());
            reporteDetallePrestamo.setDpDocumento(rldvpg.getDpDocumento() == null ? "" : rldvpg.getDpDocumento());
            reporteDetallePrestamo.setDpContable(rldvpg.getDpContable() == null ? "" : rldvpg.getDpContable());

            reporteDetallePrestamos.add(reporteDetallePrestamo);
        }
        return genericReporteService.generarReporte(modulo, "reportRRHHDetallePrestamos.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), reporteDetallePrestamos);
    }

    @Override
    public byte[] generarReporteDetalleBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String empCategoria, String nombreEmpleado,
            List<RhListaDetalleBonosTO> listaDetalleBonosTO) throws Exception {

        List<ReporteDetalleBonos> reporteDetalleBonos = new ArrayList<ReporteDetalleBonos>();
        for (RhListaDetalleBonosTO rldvpg : listaDetalleBonosTO) {
            ReporteDetalleBonos reporteDetalleBono = new ReporteDetalleBonos();

            reporteDetalleBono.setDesde(fechaDesde);
            reporteDetalleBono.setHasta(fechaHasta);
            reporteDetalleBono.setCategoriaCabecera(empCategoria);
            reporteDetalleBono.setNombresCabecera(nombreEmpleado);

            reporteDetalleBono.setDbCategoria((rldvpg.getDbCategoria() == null ? "" : rldvpg.getDbCategoria()));
            reporteDetalleBono.setDbFecha(rldvpg.getDbFecha() == null ? "" : rldvpg.getDbFecha());
            reporteDetalleBono.setDbId(rldvpg.getDbId() == null ? "" : rldvpg.getDbId());
            reporteDetalleBono.setDbNombres(rldvpg.getDbNombres() == null ? "" : rldvpg.getDbNombres());
            reporteDetalleBono.setDbConcepto(rldvpg.getDbConcepto() == null ? "" : rldvpg.getDbConcepto());
            reporteDetalleBono.setDbSector(rldvpg.getDbSector() == null ? "" : rldvpg.getDbSector());

            reporteDetalleBono.setDbPiscina(rldvpg.getDbPiscina() == null ? "" : rldvpg.getDbPiscina());
            reporteDetalleBono.setDbValor(rldvpg.getDbValor() == null ? null : rldvpg.getDbValor());
            reporteDetalleBono.setDbReverso(rldvpg.getDbReversado() == null ? false : rldvpg.getDbReversado());
            reporteDetalleBono.setDbContable(rldvpg.getDbContable() == null ? "" : rldvpg.getDbContable());
            reporteDetalleBono.setDbAnulado(rldvpg.getDbAnulado() == null ? false : rldvpg.getDbAnulado());
            reporteDetalleBono.setDbDeducible(rldvpg.getDbDeducible() == null ? false : rldvpg.getDbDeducible());
            reporteDetalleBono
                    .setDbObservaciones(rldvpg.getDbObservaciones() == null ? "" : rldvpg.getDbObservaciones());

            reporteDetalleBonos.add(reporteDetalleBono);
        }
        return genericReporteService.generarReporte(modulo, "reportDetalleBonos.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), reporteDetalleBonos);
    }

    @Override
    public byte[] generarReporteDetalleHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String empCategoria, String nombreEmpleado, List<RhListaDetalleHorasExtrasTO> listaDetalleHorasExtrasTO) throws Exception {
        List<ReporteDetalleHorasExtras> reporteDetalle = new ArrayList<>();
        for (RhListaDetalleHorasExtrasTO rldvpg : listaDetalleHorasExtrasTO) {
            ReporteDetalleHorasExtras reporteDetalleHE = new ReporteDetalleHorasExtras();

            reporteDetalleHE.setDesde(fechaDesde);
            reporteDetalleHE.setHasta(fechaHasta);
            reporteDetalleHE.setCategoriaCabecera(empCategoria);
            reporteDetalleHE.setNombresCabecera(nombreEmpleado);

            reporteDetalleHE.setDbCategoria((rldvpg.getDbCategoria() == null ? "" : rldvpg.getDbCategoria()));
            reporteDetalleHE.setDbFecha(rldvpg.getDbFecha() == null ? "" : rldvpg.getDbFecha());
            reporteDetalleHE.setDbId(rldvpg.getDbId() == null ? "" : rldvpg.getDbId());
            reporteDetalleHE.setDbNombres(rldvpg.getDbNombres() == null ? "" : rldvpg.getDbNombres());
            reporteDetalleHE.setDbConcepto(rldvpg.getDbConcepto() == null ? "" : rldvpg.getDbConcepto());
            reporteDetalleHE.setDbSector(rldvpg.getDbSector() == null ? "" : rldvpg.getDbSector());

            reporteDetalleHE.setDbPiscina(rldvpg.getDbPiscina() == null ? "" : rldvpg.getDbPiscina());
            reporteDetalleHE.setDbValor50(rldvpg.getDbValor50() == null ? null : rldvpg.getDbValor50());
            reporteDetalleHE.setDbValor100(rldvpg.getDbValor100() == null ? null : rldvpg.getDbValor100());
            reporteDetalleHE.setDbValorExtraordinaria100(rldvpg.getDbValorExtraordinarias100() == null ? null : rldvpg.getDbValorExtraordinarias100());
            reporteDetalleHE.setDbReverso(rldvpg.getDbReversado() == null ? false : rldvpg.getDbReversado());
            reporteDetalleHE.setDbContable(rldvpg.getDbContable() == null ? "" : rldvpg.getDbContable());
            reporteDetalleHE.setDbAnulado(rldvpg.getDbAnulado() == null ? false : rldvpg.getDbAnulado());
            reporteDetalleHE.setDbObservaciones(rldvpg.getDbObservaciones() == null ? "" : rldvpg.getDbObservaciones());

            reporteDetalle.add(reporteDetalleHE);
        }
        return genericReporteService.generarReporte(modulo, "reportDetalleHorasExtras.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), reporteDetalle);
    }

    @Override
    public byte[] generarReporteDetalleBonosLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String periodo,
            String tipo, String numero, List<RhListaDetalleBonosLoteTO> listaDetalleBonosLoteTO, String nombre)
            throws Exception {

        List<ReporteConsultaBonosLote> reporteConsultaBonosLotes = new ArrayList<ReporteConsultaBonosLote>();
        int i = 1;
        for (RhListaDetalleBonosLoteTO rldvpg : listaDetalleBonosLoteTO) {
            ReporteConsultaBonosLote reporteConsultaBonosLote = new ReporteConsultaBonosLote();

            reporteConsultaBonosLote.setPeriodo(periodo);
            reporteConsultaBonosLote.setTipo(tipo);
            reporteConsultaBonosLote.setNumero(numero);

            reporteConsultaBonosLote.setDblFecha(rldvpg.getDblFecha() == null ? "" : rldvpg.getDblFecha());
            reporteConsultaBonosLote.setDblId(rldvpg.getDblId() == null ? "" : rldvpg.getDblId());
            reporteConsultaBonosLote.setDblNombres(rldvpg.getDblNombres() == null ? "" : rldvpg.getDblNombres());
            reporteConsultaBonosLote.setDblValor(rldvpg.getDblValor() == null ? null : rldvpg.getDblValor());

            if (i == listaDetalleBonosLoteTO.size()) {
                break;
            }
            reporteConsultaBonosLote
                    .setFormaPago(rldvpg.getDblFormaPagoDetalle() == null ? null : rldvpg.getDblFormaPagoDetalle());
            reporteConsultaBonosLote.setDocumento(rldvpg.getDblDocumento() == null ? null : rldvpg.getDblDocumento());
            reporteConsultaBonosLote
                    .setObservaciones(rldvpg.getDblObservaciones() == null ? "¬¬¬¬" : rldvpg.getDblObservaciones());

            reporteConsultaBonosLotes.add(reporteConsultaBonosLote);
            i++;
        }
        return genericReporteService.generarReporte(modulo, nombre + ".jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), reporteConsultaBonosLotes);
        //
    }

    @Override
    public byte[] generarReporteDetalleHorasExtrasLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String periodo, String tipo, String numero, List<RhListaDetalleHorasExtrasLoteTO> listaDetalleHorasExtrasLoteTO, String nombre) throws Exception {
        List<ReporteConsultaHorasExtrasLote> reporteConsultaHorasExtrasLotes = new ArrayList<>();
        int i = 1;
        for (RhListaDetalleHorasExtrasLoteTO rldvpg : listaDetalleHorasExtrasLoteTO) {
            ReporteConsultaHorasExtrasLote reporteConsultaHELote = new ReporteConsultaHorasExtrasLote();
            reporteConsultaHELote.setPeriodo(periodo);
            reporteConsultaHELote.setTipo(tipo);
            reporteConsultaHELote.setNumero(numero);
            reporteConsultaHELote.setDblFecha(rldvpg.getDblFecha() == null ? "" : rldvpg.getDblFecha());
            reporteConsultaHELote.setDblId(rldvpg.getDblId() == null ? "" : rldvpg.getDblId());
            reporteConsultaHELote.setDblNombres(rldvpg.getDblNombres() == null ? "" : rldvpg.getDblNombres());
            reporteConsultaHELote.setDblValor50(rldvpg.getDblValor50() == null ? null : rldvpg.getDblValor50());
            reporteConsultaHELote.setDblValor100(rldvpg.getDblValor100() == null ? null : rldvpg.getDblValor100());
            reporteConsultaHELote.setDblValorExtraordinarias100(rldvpg.getDblValorExtraordinarias100() == null ? null : rldvpg.getDblValorExtraordinarias100());
            if (i == listaDetalleHorasExtrasLoteTO.size()) {
                break;
            }
            reporteConsultaHELote.setFormaPago(rldvpg.getDblFormaPagoDetalle() == null ? null : rldvpg.getDblFormaPagoDetalle());
            reporteConsultaHELote.setDocumento(rldvpg.getDblDocumento() == null ? null : rldvpg.getDblDocumento());
            reporteConsultaHELote.setObservaciones(rldvpg.getDblObservaciones() == null ? "¬¬¬¬" : rldvpg.getDblObservaciones());
            reporteConsultaHorasExtrasLotes.add(reporteConsultaHELote);
            i++;
        }
        return genericReporteService.generarReporte(modulo, nombre + ".jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteConsultaHorasExtrasLotes);
    }

    @Override
    public byte[] generarReporteDetalleBonosLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsultaBonosLote> reporteConsultaBonosLotes) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportComprobanteBonosPorLote.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteConsultaBonosLotes);
    }

    @Override
    public byte[] generarReporteDetalleBonosLoteColectivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsultaBonosLote> lista) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportComprobanteBonosPorLoteColectivo.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteDetalleViaticos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String empCategoria, String nombreEmpleado,
            List<RhListaDetalleViaticosTO> listaDetalleViaticosTO) throws Exception {

        List<ReporteDetalleViatico> reporteDetalleViaticos = new ArrayList<ReporteDetalleViatico>();
        for (RhListaDetalleViaticosTO rldvpg : listaDetalleViaticosTO) {
            ReporteDetalleViatico reporteDetalleViatico = new ReporteDetalleViatico();
            reporteDetalleViatico.setDesde(fechaDesde);
            reporteDetalleViatico.setHasta(fechaHasta);
            reporteDetalleViatico.setCategoriaCabecera(empCategoria);
            reporteDetalleViatico.setNombresCabecera(nombreEmpleado);

            reporteDetalleViatico.setCategoria((rldvpg.getDvCategoria() == null ? "" : rldvpg.getDvCategoria()));
            reporteDetalleViatico.setFecha((rldvpg.getDvFecha() == null ? "" : rldvpg.getDvFecha()));
            reporteDetalleViatico.setId((rldvpg.getDvId() == null ? "" : rldvpg.getDvId()));
            reporteDetalleViatico.setNombres((rldvpg.getDvNombres() == null ? "" : rldvpg.getDvNombres()));
            reporteDetalleViatico.setValor(rldvpg.getDvValor() == null ? null : rldvpg.getDvValor());
            reporteDetalleViatico.setContable(rldvpg.getDvContable() == null ? "" : rldvpg.getDvContable());
            reporteDetalleViatico.setAnulado(rldvpg.getDvReverso() == null ? false : rldvpg.getDvReverso());
            reporteDetalleViaticos.add(reporteDetalleViatico);
        }
        return genericReporteService.generarReporte(modulo, "reportDetalleViatico.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), reporteDetalleViaticos);
        //
    }

    @Override
    public byte[] generarReporteConsolidadoBonosViatico(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta, List<RhListaConsolidadoBonosTO> listaConsolidadoBonosViaticosTO)
            throws Exception {

        List<ReporteConsolidadoBonosViaticos> reporteConsolidadoBonosViaticos = new ArrayList<ReporteConsolidadoBonosViaticos>();
        for (RhListaConsolidadoBonosTO rldvpg : listaConsolidadoBonosViaticosTO) {
            ReporteConsolidadoBonosViaticos reporteConsolidadoBonosViatico = new ReporteConsolidadoBonosViaticos();

            reporteConsolidadoBonosViatico.setDesde(fechaDesde);
            reporteConsolidadoBonosViatico.setHasta(fechaHasta);
            reporteConsolidadoBonosViatico
                    .setCbvCategoria((rldvpg.getCbvCategoria() == null ? "" : rldvpg.getCbvCategoria()));
            reporteConsolidadoBonosViatico.setCbvId((rldvpg.getCbvId() == null ? "" : rldvpg.getCbvId()));
            reporteConsolidadoBonosViatico
                    .setCbvNombres((rldvpg.getCbvNombres() == null ? "" : rldvpg.getCbvNombres()));
            reporteConsolidadoBonosViatico.setCbvBonos((rldvpg.getCbvBonos() == null ? null : rldvpg.getCbvBonos()));
            reporteConsolidadoBonosViatico
                    .setCbvBonosND((rldvpg.getCbvBonosND() == null ? null : rldvpg.getCbvBonosND()));
            reporteConsolidadoBonosViatico
                    .setCbvBonoFijo((rldvpg.getCbvBonoFijo() == null ? null : rldvpg.getCbvBonoFijo()));
            reporteConsolidadoBonosViatico
                    .setCbvBonoFijoND((rldvpg.getCbvBonoFijoND() == null ? null : rldvpg.getCbvBonoFijoND()));
            reporteConsolidadoBonosViatico.setCbvViaticos(null);
            reporteConsolidadoBonosViatico.setCbvTotal((rldvpg.getCbvTotal() == null ? null : rldvpg.getCbvTotal()));
            reporteConsolidadoBonosViatico.setCbvOrden("");

            reporteConsolidadoBonosViaticos.add(reporteConsolidadoBonosViatico);
        }
        return genericReporteService.generarReporte(modulo, "reportConsolidadoBonosViaticos.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteConsolidadoBonosViaticos);
        //
    }

    @Override
    public byte[] generarReporteConsolidadoBonosHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhListaConsolidadoBonosHorasExtrasTO> listaConsolidadoBonosViaticosTO) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("fechaDesde", fechaDesde);
        parametros.put("fechaHasta", fechaHasta);
        return genericReporteService.generarReporte(modulo, "reportConsolidadoBonosHorasExtras.jrxml", usuarioEmpresaReporteTO, parametros, listaConsolidadoBonosViaticosTO);
    }

    @Override
    public byte[] generarReporteListadoRolesPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String empCategoria, String nombreEmpleado,
            List<RhListaDetalleRolesTO> listaDetalleRolesTO) throws Exception {

        List<ReporteListadoRoles> reporteListadoRoles = new ArrayList<>();
        for (RhListaDetalleRolesTO rldvpg : listaDetalleRolesTO) {
            ReporteListadoRoles reporteListadoRol = new ReporteListadoRoles();

            reporteListadoRol.setDesde(fechaDesde);
            reporteListadoRol.setHasta(fechaHasta);
            reporteListadoRol.setCategoria(empCategoria);
            reporteListadoRol.setEmpleado(nombreEmpleado);
            reporteListadoRol.setLrpId((rldvpg.getLrpId() == null ? "" : rldvpg.getLrpId()));
            reporteListadoRol.setLrpNombres((rldvpg.getLrpNombres() == null ? "" : rldvpg.getLrpNombres()));
            reporteListadoRol.setLrpCargo((rldvpg.getLrpCargo() == null ? "" : rldvpg.getLrpCargo()));
            reporteListadoRol.setLrpDesde((rldvpg.getLrpDesde() == null ? "" : rldvpg.getLrpDesde()));
            reporteListadoRol.setLrpHasta((rldvpg.getLrpHasta() == null ? "" : rldvpg.getLrpHasta()));
            reporteListadoRol.setLrpHE50(rldvpg.getLrpHorasExtras() == null ? BigDecimal.ZERO : rldvpg.getLrpHorasExtras());
            reporteListadoRol.setLrpHE100(rldvpg.getLrpHorasExtras100() == null ? BigDecimal.ZERO : rldvpg.getLrpHorasExtras100());
            reporteListadoRol.setLrpHEExtraordinarias100(rldvpg.getLrpHorasExtrasExtraordinarias100() == null ? BigDecimal.ZERO : rldvpg.getLrpHorasExtrasExtraordinarias100());
            reporteListadoRol.setLrpSaldoHE50(rldvpg.getLrpSaldoHorasExtras50() == null ? BigDecimal.ZERO : rldvpg.getLrpSaldoHorasExtras50());
            reporteListadoRol.setLrpSaldoHE100(rldvpg.getLrpSaldoHorasExtras100() == null ? BigDecimal.ZERO : rldvpg.getLrpSaldoHorasExtras100());
            reporteListadoRol.setLrpSaldoHEExtraordinarias100(rldvpg.getLrpSaldoHorasExtrasExtraordinarias100() == null ? BigDecimal.ZERO : rldvpg.getLrpSaldoHorasExtrasExtraordinarias100());
            // reporteListadoRol.setLrpSueldo((rldvpg.getLrpSueldo() == null
            // ? null: rldvpg.getLrpSueldo()));
            reporteListadoRol.setLrpXiiiSueldo(rldvpg.getLrpXiiiSueldo() == null ? BigDecimal.ZERO : rldvpg.getLrpXiiiSueldo());
            reporteListadoRol.setLrpXivSueldo(rldvpg.getLrpXivSueldo() == null ? BigDecimal.ZERO : rldvpg.getLrpXivSueldo());
            reporteListadoRol.setLrpDl((rldvpg.getLrpDl() == null ? null : rldvpg.getLrpDl()));
            reporteListadoRol.setLrpDf((rldvpg.getLrpDf() == null ? null : rldvpg.getLrpDf()));
            reporteListadoRol.setLrpDe((rldvpg.getLrpDe() == null ? null : rldvpg.getLrpDe()));
            reporteListadoRol.setLrpDd((rldvpg.getLrpDd() == null ? null : rldvpg.getLrpDd()));

            reporteListadoRol.setLrpDp((rldvpg.getLrpDp() == null ? null : rldvpg.getLrpDp()));
            reporteListadoRol.setLrpSaldo(rldvpg.getLrpSaldo() == null ? null : rldvpg.getLrpSaldo());
            reporteListadoRol.setLrpIngresos((rldvpg.getLrpIngresos() == null ? null : rldvpg.getLrpIngresos()));
            reporteListadoRol.setLrpBonos((rldvpg.getLrpBonos() == null ? null : rldvpg.getLrpBonos()));
            reporteListadoRol.setLrpBonosnd((rldvpg.getLrpBonosnd() == null ? null : rldvpg.getLrpBonosnd()));
            reporteListadoRol.setLrpBonoFijo((rldvpg.getLrpBonosFijo() == null ? null : rldvpg.getLrpBonosFijo()));
            reporteListadoRol.setLrpBonoFijoNd((rldvpg.getLrpBonosFijoNd() == null ? null : rldvpg.getLrpBonosFijoNd()));
            reporteListadoRol.setLrpViaticos(null);
            reporteListadoRol.setLrpLiquidacion((rldvpg.getLrpLiquidacion() == null ? null : rldvpg.getLrpLiquidacion()));
            reporteListadoRol.setLrpFondoReserva((rldvpg.getLrpFondoReserva() == null ? null : rldvpg.getLrpFondoReserva()));
            reporteListadoRol.setLrpTotalIngresos((rldvpg.getLrpTotalIngresos() == null ? null : rldvpg.getLrpTotalIngresos()));
            reporteListadoRol.setLrpAnticipos((rldvpg.getLrpAnticipos() == null ? null : rldvpg.getLrpAnticipos()));
            reporteListadoRol.setLrpPrestamos((rldvpg.getLrpPrestamos() == null ? null : rldvpg.getLrpPrestamos()));
            reporteListadoRol.setLrpIess((rldvpg.getLrpIess() == null ? null : rldvpg.getLrpIess()));
            reporteListadoRol.setLrpRetencion((rldvpg.getLrpRetencion() == null ? null : rldvpg.getLrpRetencion()));
            reporteListadoRol.setLrpDescuentos((rldvpg.getLrpDescuentos() == null ? null : rldvpg.getLrpDescuentos()));
            reporteListadoRol.setLrpTotal((rldvpg.getLrpTotal() == null ? null : rldvpg.getLrpTotal()));
            reporteListadoRol.setLrpFormaPago((rldvpg.getLrpFormaPago() == null ? "" : rldvpg.getLrpFormaPago()));
            reporteListadoRol.setLrpDocumento((rldvpg.getLrpDocumento() == null ? "" : rldvpg.getLrpDocumento()));
            reporteListadoRol.setLrpContable((rldvpg.getLrpContable() == null ? "" : rldvpg.getLrpContable()));
            // 
            BigDecimal totatBonos = (rldvpg.getLrpBonos() == null ? null : rldvpg.getLrpBonos().add(rldvpg.getLrpBonosFijo() == null ? BigDecimal.ZERO : rldvpg.getLrpBonosFijo()));
            BigDecimal totalHE = reporteListadoRol.getLrpHE50()
                    .add(reporteListadoRol.getLrpHE100())
                    .add(reporteListadoRol.getLrpHEExtraordinarias100())
                    .add(reporteListadoRol.getLrpSaldoHE50())
                    .add(reporteListadoRol.getLrpSaldoHE100())
                    .add(reporteListadoRol.getLrpSaldoHEExtraordinarias100());
            BigDecimal totalBonosNg = (rldvpg.getLrpBonosnd() == null ? null : rldvpg.getLrpBonosnd().add(rldvpg.getLrpBonosFijoNd() == null ? BigDecimal.ZERO : rldvpg.getLrpBonosFijoNd()));
            reporteListadoRol.setLrpTotalBonos(totatBonos == null ? null : totatBonos);
            reporteListadoRol.setLrpTotalBonosNg(totalBonosNg == null ? null : totalBonosNg);
            reporteListadoRol.setLrpTotalHE(totalHE.compareTo(BigDecimal.ZERO) == 0 ? null : totalHE);
            reporteListadoRol.setLrpPrestamoQuirografario(rldvpg.getLrpPrestamoQuirografario() == null ? BigDecimal.ZERO : rldvpg.getLrpPrestamoQuirografario());
            reporteListadoRol.setLrpPrestamoHipotecario(rldvpg.getLrpPrestamoHipotecario() == null ? BigDecimal.ZERO : rldvpg.getLrpPrestamoHipotecario());
            reporteListadoRoles.add(reporteListadoRol);
        }
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("CATEGORIA", empCategoria != null && !empCategoria.equals("") ? " DE " + empCategoria : "");
        return genericReporteService.generarReporte(modulo, "reportDetalladoRolesPago.jrxml", usuarioEmpresaReporteTO, parametros, reporteListadoRoles);
        //
    }

    @Override
    public byte[] generarReporteConsolidadoRoles(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String empCategoria, String nombreEmpleado,
            List<RhListaConsolidadoRolesTO> listaConsolidadoRolesTO, boolean excluirLiquidacion) throws Exception {

        List<ReporteConsolidadoRol> reporteConsolidadoRoles = new ArrayList<ReporteConsolidadoRol>();
        for (RhListaConsolidadoRolesTO rldvpg : listaConsolidadoRolesTO) {
            ReporteConsolidadoRol reporteConsolidadoRol = new ReporteConsolidadoRol();
            reporteConsolidadoRol.setDesde(fechaDesde);
            reporteConsolidadoRol.setHasta(fechaHasta);
            reporteConsolidadoRol.setCategoria(empCategoria);
            reporteConsolidadoRol.setEmpleado(nombreEmpleado);

            reporteConsolidadoRol.setCrpCategoria((rldvpg.getCrpCategoria() == null ? "" : rldvpg.getCrpCategoria()));
            reporteConsolidadoRol.setCrpId((rldvpg.getCrpId() == null ? "" : rldvpg.getCrpId()));
            reporteConsolidadoRol.setCrpNombres((rldvpg.getCrpNombres() == null ? "" : rldvpg.getCrpNombres()));
            reporteConsolidadoRol.setCrpDl((rldvpg.getCrpDl() == null ? null : rldvpg.getCrpDl()));
            reporteConsolidadoRol.setCrpDf((rldvpg.getCrpDf() == null ? null : rldvpg.getCrpDf()));
            reporteConsolidadoRol.setCrpDe((rldvpg.getCrpDe() == null ? null : rldvpg.getCrpDe()));
            reporteConsolidadoRol.setCrpDd((rldvpg.getCrpDd() == null ? null : rldvpg.getCrpDd()));
            reporteConsolidadoRol.setCrpDp((rldvpg.getCrpDp() == null ? null : rldvpg.getCrpDp()));
            reporteConsolidadoRol.setCrpSueldo((rldvpg.getCrpSueldo() == null ? null : rldvpg.getCrpSueldo()));
            reporteConsolidadoRol.setCrpBonos((rldvpg.getCrpBonos() == null ? null : rldvpg.getCrpBonos()));
            reporteConsolidadoRol.setCrpBonosnd((rldvpg.getCrpBonosnd() == null ? null : rldvpg.getCrpBonosnd()));
            reporteConsolidadoRol.setCrpBonoFijo((rldvpg.getCrpBonoFijo() == null ? null : rldvpg.getCrpBonoFijo()));
            reporteConsolidadoRol.setCrpBonoFijoNd((rldvpg.getCrpBonoFijoNd() == null ? null : rldvpg.getCrpBonoFijoNd()));
            reporteConsolidadoRol.setCrpOtrosIngresos((rldvpg.getCrpOtrosIngresos() == null ? null : rldvpg.getCrpOtrosIngresos()));
            reporteConsolidadoRol.setCrpOtrosIngresosNd((rldvpg.getCrpOtrosIngresos() == null ? null : rldvpg.getCrpOtrosIngresosNd()));
            reporteConsolidadoRol.setCrpSubtotalBonos((rldvpg.getCrpSubtotalBonos() == null ? null : rldvpg.getCrpSubtotalBonos()));
            reporteConsolidadoRol.setCrpSubtotalIngresos((rldvpg.getCrpSubtotalIngresos() == null ? null : rldvpg.getCrpSubtotalIngresos()));
            reporteConsolidadoRol.setCrpViaticos(null);
            reporteConsolidadoRol.setCrpPermisoMedico((rldvpg.getCrpPermisoMedico() == null ? null : rldvpg.getCrpSubtotalIngresos()));
            reporteConsolidadoRol.setCrpFondoReserva((rldvpg.getCrpFondoReserva() == null ? null : rldvpg.getCrpFondoReserva()));
            reporteConsolidadoRol.setCrpLiquidacion((rldvpg.getCrpLiquidacion() == null ? null : rldvpg.getCrpLiquidacion()));
            reporteConsolidadoRol.setCrpIngresos((rldvpg.getCrpIngresos() == null ? null : rldvpg.getCrpIngresos()));
            reporteConsolidadoRol.setCrpAnticipos((rldvpg.getCrpAnticipos() == null ? null : rldvpg.getCrpAnticipos()));
            reporteConsolidadoRol.setCrpPrestamos((rldvpg.getCrpPrestamos() == null ? null : rldvpg.getCrpPrestamos()));
            reporteConsolidadoRol.setCrpIess((rldvpg.getCrpIess() == null ? null : rldvpg.getCrpIess()));
            reporteConsolidadoRol.setCrpRetencion((rldvpg.getCrpRetencion() == null ? null : rldvpg.getCrpRetencion()));
            reporteConsolidadoRol.setCrpDescuentosFondoReserva((rldvpg.getCrpDescuentosFondoReserva() == null ? null : rldvpg.getCrpDescuentosFondoReserva()));
            reporteConsolidadoRol.setCrpDescuentos((rldvpg.getCrpDescuentos() == null ? null : rldvpg.getCrpDescuentos()));
            reporteConsolidadoRol.setCrpTotal((rldvpg.getCrpTotal() == null ? null : rldvpg.getCrpTotal()));
            reporteConsolidadoRol.setCrpHE50(rldvpg.getCrpHorasExtras() == null ? BigDecimal.ZERO : rldvpg.getCrpHorasExtras());
            reporteConsolidadoRol.setCrpHE100(rldvpg.getCrpHorasExtras100() == null ? BigDecimal.ZERO : rldvpg.getCrpHorasExtras100());
            reporteConsolidadoRol.setCrpHEExtraordinarias100(rldvpg.getCrpHorasExtrasExtraordinarias100() == null ? BigDecimal.ZERO : rldvpg.getCrpHorasExtrasExtraordinarias100());
            reporteConsolidadoRol.setCrpSaldoHE50(rldvpg.getCrpSaldoHorasExtras50() == null ? BigDecimal.ZERO : rldvpg.getCrpSaldoHorasExtras50());
            reporteConsolidadoRol.setCrpSaldoHE100(rldvpg.getCrpSaldoHorasExtras100() == null ? BigDecimal.ZERO : rldvpg.getCrpSaldoHorasExtras100());
            reporteConsolidadoRol.setCrpSaldoHEExtraordinarias100(rldvpg.getCrpSaldoHorasExtrasExtraordinarias100() == null ? BigDecimal.ZERO : rldvpg.getCrpSaldoHorasExtrasExtraordinarias100());
            reporteConsolidadoRol.setCrpPrestamoQuirografario(rldvpg.getCrpPrestamoQuirografario() == null ? BigDecimal.ZERO : rldvpg.getCrpPrestamoQuirografario());
            reporteConsolidadoRol.setCrpIessExtension(rldvpg.getCrpIessExtension() == null ? BigDecimal.ZERO : rldvpg.getCrpIessExtension());
            reporteConsolidadoRol.setExcluirLiquidacion(excluirLiquidacion);

            reporteConsolidadoRoles.add(reporteConsolidadoRol);
        }
        return genericReporteService.generarReporte(modulo, "reportConsolidadoRolesPago.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), reporteConsolidadoRoles);
        //
    }

    @Override
    public byte[] generarReporteSaldoIndividualAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta, String empId,
            List<RhListaSaldoIndividualAnticiposPrestamosTO> listaSaldoIndividualAnticiposPrestamosTO)
            throws Exception {

        List<ReporteSaldoIndividualAnticiposPrestamos> lista = new ArrayList<ReporteSaldoIndividualAnticiposPrestamos>();
        for (RhListaSaldoIndividualAnticiposPrestamosTO rldvpg : listaSaldoIndividualAnticiposPrestamosTO) {
            ReporteSaldoIndividualAnticiposPrestamos reporteSaldoIndividualAnticiposPrestamo = new ReporteSaldoIndividualAnticiposPrestamos();

            reporteSaldoIndividualAnticiposPrestamo.setDesde(fechaDesde);
            reporteSaldoIndividualAnticiposPrestamo.setHasta(fechaHasta);
            reporteSaldoIndividualAnticiposPrestamo.setId(empId);

            reporteSaldoIndividualAnticiposPrestamo
                    .setSiapTipo((rldvpg.getSiapTipo() == null ? "" : rldvpg.getSiapTipo()));
            reporteSaldoIndividualAnticiposPrestamo
                    .setSiapFecha((rldvpg.getSiapFecha() == null ? "" : rldvpg.getSiapFecha()));
            reporteSaldoIndividualAnticiposPrestamo
                    .setSiapObservaciones((rldvpg.getSiapObservaciones() == null ? "" : rldvpg.getSiapObservaciones()));
            reporteSaldoIndividualAnticiposPrestamo
                    .setSiapDebitos((rldvpg.getSiapDebitos() == null ? null : rldvpg.getSiapDebitos()));
            reporteSaldoIndividualAnticiposPrestamo
                    .setSiapCreditos((rldvpg.getSiapCreditos() == null ? null : rldvpg.getSiapCreditos()));
            reporteSaldoIndividualAnticiposPrestamo
                    .setSiapSaldo((rldvpg.getSiapSaldo() == null ? null : rldvpg.getSiapSaldo()));
            reporteSaldoIndividualAnticiposPrestamo
                    .setSiapContable((rldvpg.getSiapContable() == null ? "" : rldvpg.getSiapContable()));

            lista.add(reporteSaldoIndividualAnticiposPrestamo);
        }
        return genericReporteService.generarReporte(modulo, "reportSaldoIndividualAnticiposPrestamos.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
        //
    }

    @Override
    public byte[] generarReporteSaldoConsolidadoAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaHasta,
            List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listaSaldoConsolidadoAnticiposPrestamosTO)
            throws Exception {
        List<ReporteSaldoConsolidadoAnticipoPrestamo> lista = new ArrayList<ReporteSaldoConsolidadoAnticipoPrestamo>();
        for (RhListaSaldoConsolidadoAnticiposPrestamosTO rldvpg : listaSaldoConsolidadoAnticiposPrestamosTO) {
            ReporteSaldoConsolidadoAnticipoPrestamo reporteSaldoConsolidadoAnticipoPrestamo = new ReporteSaldoConsolidadoAnticipoPrestamo();

            reporteSaldoConsolidadoAnticipoPrestamo.setFechaHasta(fechaHasta);
            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapCategoria((rldvpg.getScapCategoria() == null ? "" : rldvpg.getScapCategoria()));
            reporteSaldoConsolidadoAnticipoPrestamo.setScapId((rldvpg.getScapId() == null ? "" : rldvpg.getScapId()));
            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapNombres((rldvpg.getScapNombres() == null ? "" : rldvpg.getScapNombres()));

            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapAnticipos((rldvpg.getScapAnticipos() == null ? null : rldvpg.getScapAnticipos()));
            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapPrestamos((rldvpg.getScapPrestamos() == null ? null : rldvpg.getScapPrestamos()));
            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapTotal((rldvpg.getScapTotal() == null ? null : rldvpg.getScapTotal()));

            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapOrden((rldvpg.getScapOrden() == null ? "" : rldvpg.getScapOrden()));

            lista.add(reporteSaldoConsolidadoAnticipoPrestamo);
        }
        return genericReporteService.generarReporte(modulo, "reportSaldoConsolidadoAnticiposPrestamos.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteSaldoConsolidadoSeparandoAnticiposYPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaHasta,
            List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listaSaldoConsolidadoAnticiposPrestamosTO, String documento)
            throws Exception {
        String nombreReporte = null;

        if (documento != null) {
            if (documento.equals("ANTICIPO")) {
                nombreReporte = "reportSaldoConsolidadoAnticipos.jrxml";
            }
            if (documento.equals("PRESTAMO")) {
                nombreReporte = "reportSaldoConsolidadoPrestamos.jrxml";
            }
        }

        List<ReporteSaldoConsolidadoAnticipoPrestamo> lista = new ArrayList<ReporteSaldoConsolidadoAnticipoPrestamo>();
        for (RhListaSaldoConsolidadoAnticiposPrestamosTO rldvpg : listaSaldoConsolidadoAnticiposPrestamosTO) {
            ReporteSaldoConsolidadoAnticipoPrestamo reporteSaldoConsolidadoAnticipoPrestamo = new ReporteSaldoConsolidadoAnticipoPrestamo();

            reporteSaldoConsolidadoAnticipoPrestamo.setFechaHasta(fechaHasta);
            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapCategoria((rldvpg.getScapCategoria() == null ? "" : rldvpg.getScapCategoria()));
            reporteSaldoConsolidadoAnticipoPrestamo.setScapId((rldvpg.getScapId() == null ? "" : rldvpg.getScapId()));
            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapNombres((rldvpg.getScapNombres() == null ? "" : rldvpg.getScapNombres()));

            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapAnticipos((rldvpg.getScapAnticipos() == null ? null : rldvpg.getScapAnticipos()));
            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapPrestamos((rldvpg.getScapPrestamos() == null ? null : rldvpg.getScapPrestamos()));
            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapTotal((rldvpg.getScapTotal() == null ? null : rldvpg.getScapTotal()));

            reporteSaldoConsolidadoAnticipoPrestamo
                    .setScapOrden((rldvpg.getScapOrden() == null ? "" : rldvpg.getScapOrden()));

            lista.add(reporteSaldoConsolidadoAnticipoPrestamo);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte,
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteSaldoConsolidadoBonosViaticos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaHasta, List<RhListaSaldoConsolidadoBonosTO> listaSaldoConsolidadoBonosViaticosTO)
            throws Exception {

        List<ReporteSaldoConsolidadoBonosViaticos> lista = new ArrayList<ReporteSaldoConsolidadoBonosViaticos>();
        for (RhListaSaldoConsolidadoBonosTO rldvpg : listaSaldoConsolidadoBonosViaticosTO) {
            ReporteSaldoConsolidadoBonosViaticos reporteSaldoConsolidadoBonoViatico = new ReporteSaldoConsolidadoBonosViaticos();

            reporteSaldoConsolidadoBonoViatico.setFechaHasta(fechaHasta);
            reporteSaldoConsolidadoBonoViatico
                    .setScbvCategoria((rldvpg.getScbvCategoria() == null ? "" : rldvpg.getScbvCategoria()));
            reporteSaldoConsolidadoBonoViatico.setScbvId((rldvpg.getScbvId() == null ? "" : rldvpg.getScbvId()));
            reporteSaldoConsolidadoBonoViatico
                    .setScbvNombres((rldvpg.getScbvNombres() == null ? "" : rldvpg.getScbvNombres()));
            reporteSaldoConsolidadoBonoViatico
                    .setScbvBonos((rldvpg.getScbvBonos() == null ? null : rldvpg.getScbvBonos()));
            reporteSaldoConsolidadoBonoViatico
                    .setScbvViaticos((rldvpg.getScbvViaticos() == null ? null : rldvpg.getScbvViaticos()));
            reporteSaldoConsolidadoBonoViatico
                    .setScbvTotal((rldvpg.getScbvTotal() == null ? null : rldvpg.getScbvTotal()));
            reporteSaldoConsolidadoBonoViatico
                    .setScbvOrden((rldvpg.getScbvOrden() == null ? "" : rldvpg.getScbvOrden()));

            lista.add(reporteSaldoConsolidadoBonoViatico);
        }
        return genericReporteService.generarReporte(modulo, "reportSaldoConsolidadoBonosViaticos.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
        //
    }

    @Override
    public byte[] generarReporteSaldoConsolidadoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta, List<RhListaSaldoConsolidadoHorasExtrasTO> listaSaldoConsolidadoHorasExtras) throws Exception {
        List<ReporteSaldoConsolidadoHorasExtras> lista = new ArrayList<>();
        for (RhListaSaldoConsolidadoHorasExtrasTO rldvpg : listaSaldoConsolidadoHorasExtras) {
            ReporteSaldoConsolidadoHorasExtras reporteSaldoConsolidadoHE = new ReporteSaldoConsolidadoHorasExtras();
            reporteSaldoConsolidadoHE.setFechaHasta(fechaHasta);
            reporteSaldoConsolidadoHE.setScbvCategoria((rldvpg.getScbvCategoria() == null ? "" : rldvpg.getScbvCategoria()));
            reporteSaldoConsolidadoHE.setScbvId((rldvpg.getScbvId() == null ? "" : rldvpg.getScbvId()));
            reporteSaldoConsolidadoHE.setScbvNombres((rldvpg.getScbvNombres() == null ? "" : rldvpg.getScbvNombres()));
            reporteSaldoConsolidadoHE.setScbvValor50((rldvpg.getScbvValor50() == null ? null : rldvpg.getScbvValor50()));
            reporteSaldoConsolidadoHE.setScbvValor100((rldvpg.getScbvValor100() == null ? null : rldvpg.getScbvValor100()));
            reporteSaldoConsolidadoHE.setScbvValorExtraordinarias100((rldvpg.getScbvValorExtraordinarias100() == null ? null : rldvpg.getScbvValorExtraordinarias100()));
            reporteSaldoConsolidadoHE.setScbvTotal((rldvpg.getScbvTotal() == null ? null : rldvpg.getScbvTotal()));
            reporteSaldoConsolidadoHE.setScbvOrden((rldvpg.getScbvOrden() == null ? "" : rldvpg.getScbvOrden()));
            lista.add(reporteSaldoConsolidadoHE);
        }
        return genericReporteService.generarReporte(modulo, "reportSaldoConsolidadoHorasExtras.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    @Override
    public byte[] generarReporteSaldoConsolidadoSueldosPorPagar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaHasta, List<RhListaSaldoConsolidadoSueldosPorPagarTO> listaSaldoConsolidadoSueldosPorPagarTO)
            throws Exception {

        List<ReporteSaldoConsolidadoSueldoPorPagar> lista = new ArrayList<ReporteSaldoConsolidadoSueldoPorPagar>();
        for (RhListaSaldoConsolidadoSueldosPorPagarTO rldvpg : listaSaldoConsolidadoSueldosPorPagarTO) {
            ReporteSaldoConsolidadoSueldoPorPagar reporteSaldoConsolidadoSueldoPorPagar = new ReporteSaldoConsolidadoSueldoPorPagar();

            reporteSaldoConsolidadoSueldoPorPagar.setFechaHasta(fechaHasta);
            reporteSaldoConsolidadoSueldoPorPagar
                    .setScspCategoria((rldvpg.getScspCategoria() == null ? "" : rldvpg.getScspCategoria()));
            reporteSaldoConsolidadoSueldoPorPagar.setScspId((rldvpg.getScspId() == null ? "" : rldvpg.getScspId()));
            reporteSaldoConsolidadoSueldoPorPagar
                    .setScspNombres((rldvpg.getScspNombres() == null ? "" : rldvpg.getScspNombres()));
            reporteSaldoConsolidadoSueldoPorPagar
                    .setScspValor((rldvpg.getScspValor() == null ? null : rldvpg.getScspValor()));
            reporteSaldoConsolidadoSueldoPorPagar
                    .setScspOrden((rldvpg.getScspOrden() == null ? "" : rldvpg.getScspOrden()));

            lista.add(reporteSaldoConsolidadoSueldoPorPagar);
        }
        return genericReporteService.generarReporte(modulo, "reportSaldoConsolidadoSueldosPorPagar.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
        //
    }

    @Override
    public byte[] generarReporteXiiiSueldoConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector,
            String periodo, String fechaDesde, String fechaHasta, String fechaMaxima,
            List<RhFunXiiiSueldoConsultarTO> rhFunXiiiSueldoConsultarTO) throws Exception {

        List<ReporteXiiiSueldoConsulta> lista = new ArrayList<ReporteXiiiSueldoConsulta>();
        for (RhFunXiiiSueldoConsultarTO rldvpg : rhFunXiiiSueldoConsultarTO) {
            ReporteXiiiSueldoConsulta reporteXiiiSueldoConsulta = new ReporteXiiiSueldoConsulta();

            reporteXiiiSueldoConsulta.setSector(sector);
            reporteXiiiSueldoConsulta.setPeriodo(periodo);
            reporteXiiiSueldoConsulta.setFechaDesde(fechaDesde);
            reporteXiiiSueldoConsulta.setFechaHasta(fechaHasta);
            reporteXiiiSueldoConsulta.setFechaMaxima(fechaMaxima);

            reporteXiiiSueldoConsulta
                    .setXiiiCategoria((rldvpg.getXiiiCategoria() == null ? "" : rldvpg.getXiiiCategoria()));
            reporteXiiiSueldoConsulta.setXiiiSector((rldvpg.getXiiiSector() == null ? "" : rldvpg.getXiiiSector()));
            reporteXiiiSueldoConsulta.setXiiiId((rldvpg.getXiiiId() == null ? "" : rldvpg.getXiiiId()));
            reporteXiiiSueldoConsulta
                    .setXiiiNombres((rldvpg.getXiiiNombres() == null ? "" : rldvpg.getXiiiNombres().trim()));
            reporteXiiiSueldoConsulta
                    .setXiiiApellidos((rldvpg.getXiiiApellidos() == null ? "" : rldvpg.getXiiiApellidos().trim()));
            reporteXiiiSueldoConsulta.setXiiiGenero((rldvpg.getXiiiGenero() == null ? null : rldvpg.getXiiiGenero()));
            reporteXiiiSueldoConsulta
                    .setXiiiFechaIngreso((rldvpg.getXiiiFechaIngreso() == null ? "" : rldvpg.getXiiiFechaIngreso()));
            reporteXiiiSueldoConsulta.setXiiiCargo((rldvpg.getXiiiCargo() == null ? "" : rldvpg.getXiiiCargo()));
            reporteXiiiSueldoConsulta.setXiiiTotalIngresos(
                    (rldvpg.getXiiiTotalIngresos() == null ? null : rldvpg.getXiiiTotalIngresos()));
            reporteXiiiSueldoConsulta.setXiiiDiasLaborados(
                    (rldvpg.getXiiiDiasLaborados() == null ? null : rldvpg.getXiiiDiasLaborados()));
            reporteXiiiSueldoConsulta.setXiiiValorXiiiSueldo(
                    (rldvpg.getXiiiValorXiiiSueldo() == null ? null : rldvpg.getXiiiValorXiiiSueldo()));
            reporteXiiiSueldoConsulta.setXiiiCodigoMinisterial((rldvpg.getXiiiCodigoMinisterial() == null
                    || rldvpg.getXiiiCodigoMinisterial().compareToIgnoreCase("") == 0 ? null
                    : rldvpg.getXiiiCodigoMinisterial().charAt(0)));
            // Contalbe
            reporteXiiiSueldoConsulta.setXiiiNumero((rldvpg.getXiiiNumero() == null ? "" : rldvpg.getXiiiNumero()));//

            lista.add(reporteXiiiSueldoConsulta);
        }
        return genericReporteService.generarReporte(modulo, "reportXiiiSueldoConsulta.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
        //
    }

    @Override
    public byte[] generarReporteXivSueldoConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector,
            String periodo, String fechaDesde, String fechaHasta, String fechaMaxima,
            List<RhFunXivSueldoConsultarTO> rhFunXivSueldoConsultarTO) throws Exception {

        List<ReporteXivSueldoConsulta> lista = new ArrayList<ReporteXivSueldoConsulta>();
        for (RhFunXivSueldoConsultarTO rldvpg : rhFunXivSueldoConsultarTO) {
            ReporteXivSueldoConsulta reporteXivSueldoConsulta = new ReporteXivSueldoConsulta();

            reporteXivSueldoConsulta.setSector(sector);
            reporteXivSueldoConsulta.setPeriodo(periodo);
            reporteXivSueldoConsulta.setFechaDesde(fechaDesde);
            reporteXivSueldoConsulta.setFechaHasta(fechaHasta);
            reporteXivSueldoConsulta.setFechaMaxima(fechaMaxima);

            reporteXivSueldoConsulta.setXivCategoria((rldvpg.getXivCategoria() == null ? "" : rldvpg.getXivCategoria()));
            reporteXivSueldoConsulta.setXivSector((rldvpg.getXivSector() == null ? "" : rldvpg.getXivSector()));
            reporteXivSueldoConsulta.setXivId((rldvpg.getXivId() == null ? "" : rldvpg.getXivId()));
            reporteXivSueldoConsulta.setXivNombres((rldvpg.getXivNombres() == null ? "" : rldvpg.getXivNombres().trim()));
            reporteXivSueldoConsulta.setXivApellidos((rldvpg.getXivApellidos() == null ? "" : rldvpg.getXivApellidos().trim()));
            reporteXivSueldoConsulta.setXivGenero((rldvpg.getXivGenero() == null ? null : rldvpg.getXivGenero()));
            reporteXivSueldoConsulta.setXivFechaIngreso((rldvpg.getXivFechaIngreso() == null ? "" : rldvpg.getXivFechaIngreso()));
            reporteXivSueldoConsulta.setXivCargo((rldvpg.getXivCargo() == null ? "" : rldvpg.getXivCargo()));
            reporteXivSueldoConsulta.setXivTotalIngresos((rldvpg.getXivTotalIngresos() == null ? null : rldvpg.getXivTotalIngresos()));
            reporteXivSueldoConsulta.setXivDiasLaborados((rldvpg.getXivDiasLaborados() == null ? null : rldvpg.getXivDiasLaborados()));
            reporteXivSueldoConsulta.setXivValorXivSueldo((rldvpg.getXivValorXivSueldo() == null ? null : rldvpg.getXivValorXivSueldo()));
            reporteXivSueldoConsulta.setXivCodigoMinisterial((rldvpg.getXivCodigoMinisterial() == null ? null : rldvpg.getXivCodigoMinisterial()));
            // Contalbe
            reporteXivSueldoConsulta.setXivNumero((rldvpg.getXivNumero() == null ? "" : rldvpg.getXivNumero()));//

            lista.add(reporteXivSueldoConsulta);
        }
        return genericReporteService.generarReporte(modulo, "reportXivSueldoConsulta.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
        //
    }

    @Override
    public byte[] generarReporteUtilidadesPreCalculo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector,
            String periodo, String fechaDesde, String fechaHasta, String fechaMaxima,
            List<RhFunUtilidadesCalcularTO> rhFunUtilidadesCalcularTOs) throws Exception {

        List<ReporteFunUtilidadesCalcular> lista = new ArrayList<ReporteFunUtilidadesCalcular>();
        for (RhFunUtilidadesCalcularTO rldvpg : rhFunUtilidadesCalcularTOs) {
            ReporteFunUtilidadesCalcular reporteFunUtilidadCalcular = new ReporteFunUtilidadesCalcular();

            reporteFunUtilidadCalcular.setSector(sector);
            reporteFunUtilidadCalcular.setPeriodo(periodo);
            reporteFunUtilidadCalcular.setFechaDesde(fechaDesde);
            reporteFunUtilidadCalcular.setFechaHasta(fechaHasta);
            reporteFunUtilidadCalcular.setFechaMaxima(fechaMaxima);

            reporteFunUtilidadCalcular.setXivId((rldvpg.getUtiId() == null ? "" : rldvpg.getUtiId())); //////////////

            reporteFunUtilidadCalcular
                    .setXivNombres((rldvpg.getUtiNombres() == null ? "" : rldvpg.getUtiNombres().trim())); //////////////
            reporteFunUtilidadCalcular
                    .setXivApellidos((rldvpg.getUtiApellidos() == null ? "" : rldvpg.getUtiApellidos().trim()));

            reporteFunUtilidadCalcular.setXivGenero((rldvpg.getUtiGenero() == null ? null : rldvpg.getUtiGenero())); //////////////
            reporteFunUtilidadCalcular
                    .setXivFechaIngreso((rldvpg.getUtiFechaIngreso() == null ? "" : rldvpg.getUtiFechaIngreso())); //////////////
            reporteFunUtilidadCalcular
                    .setXivFechaSalida((rldvpg.getUtiFechaSalida() == null ? "" : rldvpg.getUtiFechaSalida())); //////////////
            reporteFunUtilidadCalcular.setXivCargo((rldvpg.getUtiCargo() == null ? "" : rldvpg.getUtiCargo())); //////////////
            reporteFunUtilidadCalcular.setXivCargasFamiliares(
                    (rldvpg.getUtiCargasFamiliares() == null ? 0 : rldvpg.getUtiCargasFamiliares())); //////////////
            reporteFunUtilidadCalcular
                    .setXivDiasLaborados((rldvpg.getUtiDiasLaborados() == null ? 0 : rldvpg.getUtiDiasLaborados())); //////////////

            reporteFunUtilidadCalcular
                    .setXivValorPersonal((rldvpg.getUtiValorPersonal() == null ? null : rldvpg.getUtiValorPersonal()));
            reporteFunUtilidadCalcular
                    .setXivValorCargas((rldvpg.getUtiValorCargas() == null ? null : rldvpg.getUtiValorCargas()));

            reporteFunUtilidadCalcular.setXivValorUtilidades(
                    (rldvpg.getUtiValorUtilidades() == null ? null : rldvpg.getUtiValorUtilidades())); //////////////
            reporteFunUtilidadCalcular
                    .setXivValorSueldos((rldvpg.getUtiValorSueldos() == null ? null : rldvpg.getUtiValorSueldos())); //////////////
            reporteFunUtilidadCalcular
                    .setXivValorBonos((rldvpg.getUtiValorBonos() == null ? null : rldvpg.getUtiValorBonos())); //////////////
            reporteFunUtilidadCalcular
                    .setXivValorXiii((rldvpg.getUtiValorXiii() == null ? null : rldvpg.getUtiValorXiii())); //////////////
            reporteFunUtilidadCalcular
                    .setXivValorXiv((rldvpg.getUtiValorXiv() == null ? null : rldvpg.getUtiValorXiv())); //////////////
            reporteFunUtilidadCalcular.setXivValorFondoReserva(
                    (rldvpg.getUtiValorFondoReserva() == null ? null : rldvpg.getUtiValorFondoReserva())); //////////////
            reporteFunUtilidadCalcular.setXivValorSalarioDigno(
                    (rldvpg.getUtiValorSalarioDigno() == null ? null : rldvpg.getUtiValorSalarioDigno())); //////////////
            reporteFunUtilidadCalcular.setXivValorUtilidadAnterior(
                    (rldvpg.getUtiValorUtilidadAnterior() == null ? null : rldvpg.getUtiValorUtilidadAnterior())); //////////////
            reporteFunUtilidadCalcular
                    .setXivValorImpuesto((rldvpg.getUtiValorImpuesto() == null ? null : rldvpg.getUtiValorImpuesto())); //////////////

            reporteFunUtilidadCalcular
                    .setXivCategoria((rldvpg.getUtiCategoria() == null ? "" : rldvpg.getUtiCategoria()));
            reporteFunUtilidadCalcular.setXivCuenta((rldvpg.getUtiCuenta() == null ? "" : rldvpg.getUtiCuenta()));
            reporteFunUtilidadCalcular.setXivSector((rldvpg.getUtiSector() == null ? "" : rldvpg.getUtiSector()));
            reporteFunUtilidadCalcular.setEstado((rldvpg.isEstado() == false ? false : rldvpg.isEstado()));

            lista.add(reporteFunUtilidadCalcular);

        }
        return genericReporteService.generarReporte(modulo, "reportUtilidadesPreCalculo.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);

    }

    @Override
    public byte[] generarReporteProvisionesComprobanteContable(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String periodo, String tipo, String numero, List<RhListaProvisionesTO> listaProvisionesTO)
            throws Exception {

        List<ReporteProvisionesComprobanteContable> lista = new ArrayList<ReporteProvisionesComprobanteContable>();
        for (RhListaProvisionesTO rldvpg : listaProvisionesTO) {
            ReporteProvisionesComprobanteContable reporteProvisionesComprobanteContable = new ReporteProvisionesComprobanteContable();

            reporteProvisionesComprobanteContable.setPeriodo(periodo);
            reporteProvisionesComprobanteContable.setTipo(tipo);
            reporteProvisionesComprobanteContable.setNumero(numero);

            reporteProvisionesComprobanteContable.setProvId((rldvpg.getProvId() == null ? "" : rldvpg.getProvId()));
            reporteProvisionesComprobanteContable
                    .setProvNombres((rldvpg.getProvNombres() == null ? "" : rldvpg.getProvNombres()));
            reporteProvisionesComprobanteContable
                    .setProvSueldo((rldvpg.getProvSueldo() == null ? null : rldvpg.getProvSueldo()));
            reporteProvisionesComprobanteContable
                    .setProvDiasPagados((rldvpg.getProvDiasPagados() == null ? null : rldvpg.getProvDiasPagados()));
            reporteProvisionesComprobanteContable.setProvAporteIndividual(
                    (rldvpg.getProvAporteIndividual() == null ? null : rldvpg.getProvAporteIndividual()));
            reporteProvisionesComprobanteContable.setProvAportePatronal(
                    (rldvpg.getProvAportePatronal() == null ? null : rldvpg.getProvAportePatronal()));
            reporteProvisionesComprobanteContable
                    .setProvIece((rldvpg.getProvIece() == null ? null : rldvpg.getProvIece()));
            reporteProvisionesComprobanteContable
                    .setProvSecap((rldvpg.getProvSecap() == null ? null : rldvpg.getProvSecap()));
            reporteProvisionesComprobanteContable
                    .setProvXiii((rldvpg.getProvXiii() == null ? null : rldvpg.getProvXiii()));
            reporteProvisionesComprobanteContable
                    .setProvXiv((rldvpg.getProvXiv() == null ? null : rldvpg.getProvXiv()));
            reporteProvisionesComprobanteContable
                    .setProvFondoReserva((rldvpg.getProvFondoReserva() == null ? null : rldvpg.getProvFondoReserva()));
            reporteProvisionesComprobanteContable
                    .setProvVacaciones((rldvpg.getProvVacaciones() == null ? null : rldvpg.getProvVacaciones()));
            reporteProvisionesComprobanteContable
                    .setProvDesahucio((rldvpg.getProvDesahucio() == null ? null : rldvpg.getProvDesahucio()));
            reporteProvisionesComprobanteContable
                    .setProvContableRol((rldvpg.getProvContableRol() == null ? "" : rldvpg.getProvContableRol()));
            reporteProvisionesComprobanteContable.setProvContableProvision(
                    (rldvpg.getProvContableProvision() == null ? "" : rldvpg.getProvContableProvision()));

            lista.add(reporteProvisionesComprobanteContable);

        }
        return genericReporteService.generarReporte(modulo, "reportComprobanteProvisiones.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteFormulario107(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaEntrega,
            String rucEmpleador, String razonSocial, String rucContador, RhFormulario107TO rhFormulario107TOGuardar)
            throws Exception {

        List<ReporteFormulario107> lista = new ArrayList<ReporteFormulario107>();

        ReporteFormulario107 rhFormulario107 = new ReporteFormulario107();

        rhFormulario107.setF107Anio(rhFormulario107TOGuardar.getF107Anio());
        rhFormulario107.setF107Tipo(rhFormulario107TOGuardar.getF107Tipo());
        rhFormulario107.setF107Id(rhFormulario107TOGuardar.getF107Id());
        rhFormulario107.setF107Nombres(rhFormulario107TOGuardar.getF107Nombres());
        rhFormulario107.setF107Direccion(rhFormulario107TOGuardar.getF107Direccion());
        rhFormulario107.setF107Numero(rhFormulario107TOGuardar.getF107Direccion());
        rhFormulario107.setF107Canton(rhFormulario107TOGuardar.getF107Canton());
        rhFormulario107.setF107Provincia(rhFormulario107TOGuardar.getF107Provincia());
        rhFormulario107.setF107Telefono(rhFormulario107TOGuardar.getF107Telefono());
        rhFormulario107.setF107SalarioNeto(rhFormulario107TOGuardar.getF107SalarioNeto());
        rhFormulario107.setF107Sueldo(rhFormulario107TOGuardar.getF107Sueldo());
        rhFormulario107.setF107Bonos(rhFormulario107TOGuardar.getF107Bonos());
        rhFormulario107.setF107XiiiSueldo(rhFormulario107TOGuardar.getF107XiiiSueldo());
        rhFormulario107.setF107XivSueldo(rhFormulario107TOGuardar.getF107XivSueldo());
        rhFormulario107.setF107FondoReserva(rhFormulario107TOGuardar.getF107FondoReserva());
        rhFormulario107.setF107SalarioDigno(rhFormulario107TOGuardar.getF107SalarioDigno());
        rhFormulario107.setF107Utilidades(rhFormulario107TOGuardar.getF107Utilidades());
        rhFormulario107.setF107Desahucio(rhFormulario107TOGuardar.getF107Desahucio());
        rhFormulario107.setF107Iess(rhFormulario107TOGuardar.getF107Iess());
        rhFormulario107.setF107Vivienda(rhFormulario107TOGuardar.getF107Vivienda());
        rhFormulario107.setF107Salud(rhFormulario107TOGuardar.getF107Salud());
        rhFormulario107.setF107Educacion(rhFormulario107TOGuardar.getF107Educacion());
        rhFormulario107.setF107Alimentacion(rhFormulario107TOGuardar.getF107Alimentacion());
        rhFormulario107.setF107Vestuario(rhFormulario107TOGuardar.getF107Vestuario());
        rhFormulario107.setF107RebajaDiscapacitado(rhFormulario107TOGuardar.getF107RebajaDiscapacitado());
        rhFormulario107.setF107RebajaTerceraEdad(rhFormulario107TOGuardar.getF107RebajaTerceraEdad());
        rhFormulario107.setF107ImpuestoAsumido(rhFormulario107TOGuardar.getF107ImpuestoAsumido());
        rhFormulario107.setF107Subtotal(rhFormulario107TOGuardar.getF107Subtotal());
        rhFormulario107.setF107MesesTrabajados(rhFormulario107TOGuardar.getF107MesesTrabajados());
        rhFormulario107.setF107OtrosIngresos(rhFormulario107TOGuardar.getF107OtrosIngresos());
        rhFormulario107.setF107OtrasDeducciones(rhFormulario107TOGuardar.getF107OtrasDeducciones());
        rhFormulario107.setF107OtrasRebajas(rhFormulario107TOGuardar.getF107OtrasRebajas());
        rhFormulario107.setF107BaseImponible(rhFormulario107TOGuardar.getF107BaseImponible());
        rhFormulario107.setF107ImpuestoCausado(rhFormulario107TOGuardar.getF107ImpuestoCausado());
        rhFormulario107.setF107ValorRetenido(rhFormulario107TOGuardar.getF107ValorRetenido());
        rhFormulario107.setF107NumeroRetenciones(rhFormulario107TOGuardar.getF107NumeroRetenciones());
        rhFormulario107.setF107Impuesto(rhFormulario107TOGuardar.getF107Impuesto());
        rhFormulario107.setUsrCodigo(rhFormulario107TOGuardar.getUsrCodigo());
        rhFormulario107.setUsrFechaInserta(rhFormulario107TOGuardar.getUsrFechaInserta());

        rhFormulario107.setF107FechaEntrega(fechaEntrega);
        rhFormulario107.setF107RucEmpleador(rucEmpleador);
        rhFormulario107.setF107NombreEmpleador(razonSocial);
        rhFormulario107.setF107RucContador(rucContador);

        lista.add(rhFormulario107);

        return genericReporteService.generarReporte(modulo, "reportFormulario107.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteXIIISueldo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldo) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportComprobanteXIIISueldo.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), rhReporteXIIISueldo);
    }

    @Override
    public byte[] generarReporteXIVSueldo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIVSueldo) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportComprobanteXIVSueldo.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), rhReporteXIVSueldo);
    }

    @Override
    public byte[] generarReporteUtilidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> rhReporteUtilidades) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportComprobanteUtilidades.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), rhReporteUtilidades);
    }

    @Override
    public byte[] generarReporteAnticipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> rhReporteAnticipoOprestamos) throws Exception {

        return genericReporteService.generarReporte(modulo, "reportComprobanteAnticipo.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), rhReporteAnticipoOprestamos);
    }

    @Override
    public byte[] generarReporteListadoLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhRolEmpTO> listado) throws Exception {
        List<ReportesRol> list = new ArrayList<>();
        for (RhRolEmpTO liquidacion : listado) {
            list.add(generarListLiquidacion(liquidacion));
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), list);
    }

    public ReportesRol generarListLiquidacion(RhRolEmpTO liquidacion) throws Exception {
        ReportesRol liqui = new ReportesRol();
        liqui.setCedula(liquidacion.getRhEmpleado().getRhEmpleadoPK().getEmpId());
        liqui.setNombres(liquidacion.getRhEmpleado().getEmpApellidos() + " " + liquidacion.getRhEmpleado().getEmpNombres());
        liqui.setRolCargo(liquidacion.getRhEmpleado().getEmpCargo());
        liqui.setRolDesde(UtilsDate.fechaFormatoString(liquidacion.getRolDesde(), "dd-MM-yyyy"));
        liqui.setRolHasta(liquidacion.getRhEmpleado().getEmpFechaUltimaSalida() == null ? UtilsDate.fechaFormatoString(liquidacion.getRhEmpleado().getEmpFechaPrimeraSalida(), "dd-MM-yyyy") : UtilsDate.fechaFormatoString(liquidacion.getRhEmpleado().getEmpFechaUltimaSalida(), "dd-MM-yyyy"));
        liqui.setRolMotivoSalida(liquidacion.getRhEmpleado().getEmpMotivoSalida());
        liqui.setRolSaldoAnterior(liquidacion.getRolSaldoAnterior());
        liqui.setRolLiqXiii(liquidacion.getRolLiqXiii());
        liqui.setRolLiqXiv(liquidacion.getRolLiqXiv());
        liqui.setRolLiqVacaciones(liquidacion.getRolLiqVacaciones());
        liqui.setRolAnticipos(liquidacion.getRolAnticipos());
        liqui.setRolPrestamos(liquidacion.getRolPrestamos());
        liqui.setRolLiqSalarioDigno(liquidacion.getRolLiqSalarioDigno());
        liqui.setRolLiqDesahucio(liquidacion.getRolLiqDesahucio());
        liqui.setRolLiqDesahucioIntempestivo(liquidacion.getRolLiqDesahucioIntempestivo());
        liqui.setRolLiqBonificacion(liquidacion.getRolLiqBonificacion());
        liqui.setValor(liquidacion.getRolTotal());
        liqui.setFormaPago(liquidacion.getRolFormaPago());
        liqui.setReferencia(liquidacion.getRolDocumento());
        liqui.setComprobante(liquidacion.getConPeriodo() + "|" + liquidacion.getConTipo() + "|" + liquidacion.getConNumero());
        return liqui;
    }

    @Override
    public byte[] generarReporteAnticipoDetalleAnticipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhListaDetalleAnticiposTO> listaAnticipoDetalleSeleccion, SisInfoTO usuario) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> listado = this.convertirAPrestamoXIIISueldo(usuarioEmpresaReporteTO, listaAnticipoDetalleSeleccion, usuario);
        return genericReporteService.generarReporte(modulo, "reportComprobanteAnticipo.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), listado);
    }

    public List<ReporteAnticipoPrestamoXIIISueldo> convertirAPrestamoXIIISueldo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhListaDetalleAnticiposTO> listaAnticipoDetalleSeleccion, SisInfoTO usuario) {

        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteAnticipoOprestamos = new ArrayList<ReporteAnticipoPrestamoXIIISueldo>();
        try {
            for (RhListaDetalleAnticiposTO listaDetalleAnticiposTO : listaAnticipoDetalleSeleccion) {
                BigDecimal sueldo = empleadoService.getListaRhEmpleadoTO(usuarioEmpresaReporteTO.getEmpCodigo(), listaDetalleAnticiposTO.getDaId(), true).get(0).getEmpSueldoIess();
                ReporteAnticipoPrestamoXIIISueldo rhReporteAnticipoOprestamo = new ReporteAnticipoPrestamoXIIISueldo();
                rhReporteAnticipoOprestamo.setComprobante(listaDetalleAnticiposTO.getDaContable());
                rhReporteAnticipoOprestamo.setFecha(listaDetalleAnticiposTO.getDaFecha());
                rhReporteAnticipoOprestamo.setCedula(listaDetalleAnticiposTO.getDaId());
                rhReporteAnticipoOprestamo.setNombres(listaDetalleAnticiposTO.getDaNombres());
                rhReporteAnticipoOprestamo.setCargo("");
                rhReporteAnticipoOprestamo.setNombreSector("");
                rhReporteAnticipoOprestamo.setValor(listaDetalleAnticiposTO.getDaValor());
                rhReporteAnticipoOprestamo.setFormaPago(listaDetalleAnticiposTO.getDaFormaPago());
                rhReporteAnticipoOprestamo.setNombreCuenta(listaDetalleAnticiposTO.getDaFormaPago());
                rhReporteAnticipoOprestamo.setReferencia(listaDetalleAnticiposTO.getDaDocumento());
                rhReporteAnticipoOprestamo.setObservaciones(listaDetalleAnticiposTO.getDaObservaciones());
                rhReporteAnticipoOprestamo.setSueldo(sueldo);
                rhReporteAnticipoOprestamos.add(rhReporteAnticipoOprestamo);
            }

        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", usuario));
        }

        return rhReporteAnticipoOprestamos;
    }

    @Override
    public byte[] generarReporteAnticipoContable(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhListaDetalleAnticiposTO> listaAnticipoDetalleSeleccion, SisInfoTO usuario) throws Exception {

        List<ConContableReporteTO> listado = this.convertirAContable(listaAnticipoDetalleSeleccion, usuarioEmpresaReporteTO, usuario);
        return reporteContabilidadService.generarReporteContableDetalle(usuarioEmpresaReporteTO,
                listado);
    }

    public List<ConContableReporteTO> convertirAContable(List<RhListaDetalleAnticiposTO> listaAnticipoDetalleSeleccion, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            SisInfoTO usuario) {
        List<ConContablePK> listContable = new ArrayList<ConContablePK>();
        List<ConContableReporteTO> list = new ArrayList<ConContableReporteTO>();
        try {
            Short estGrupo1;
            Short estGrupo2;
            Short estGrupo3;
            Short estGrupo4;
            Short estGrupo5;
            Short estGrupo6;

            for (RhListaDetalleAnticiposTO conAnt : listaAnticipoDetalleSeleccion) {
                if (conAnt.getDaContable() != null) {
                    List<String> comprobantes = UtilsValidacion.separarComprobante(conAnt.getDaContable());
                    String perCodigo = comprobantes.get(0);
                    String tipCodigo = comprobantes.get(1);
                    String conNumero = comprobantes.get(2);
                    if (perCodigo != null && tipCodigo != null && conNumero != null) {
                        listContable.add(new ConContablePK(usuarioEmpresaReporteTO.getEmpCodigo(), perCodigo, tipCodigo, conNumero));
                    }
                }
            }

            ConEstructuraTO estructura2 = estructuraService.getListaConEstructuraTO(usuarioEmpresaReporteTO.getEmpCodigo()).get(0);
            estGrupo1 = estructura2.getEstGrupo1();
            estGrupo2 = estructura2.getEstGrupo2();
            estGrupo3 = estructura2.getEstGrupo3();
            estGrupo4 = estructura2.getEstGrupo4();
            estGrupo5 = estructura2.getEstGrupo5();
            estGrupo6 = estructura2.getEstGrupo6();

            for (ConContablePK conPK : listContable) {
                ConContableTO contable = contableService.getListaConContableTO(conPK.getConEmpresa(), conPK.getConPeriodo(), conPK.getConTipo(), conPK.getConNumero()).get(0);

                List<ConDetalleTablaTO> detalleContable = detalleService.getListaConDetalleTO(conPK.getConEmpresa(), conPK.getConPeriodo(), conPK.getConTipo(), conPK.getConNumero());

                String tipoDetalle = tipoService.getConTipoTO(conPK.getConEmpresa(), contable.getTipCodigo()).getTipDetalle();
                List<String> cuentaPadre = new ArrayList<String>();
                for (ConDetalleTablaTO dc : detalleContable) {
                    String cuenta = dc.getCtaCodigo().trim().substring(0,
                            ((estGrupo2 == 0 ? 0 : estGrupo1) + (estGrupo3 == 0 ? 0 : estGrupo2)
                            + (estGrupo4 == 0 ? 0 : estGrupo3) + (estGrupo5 == 0 ? 0 : estGrupo4)
                            + (estGrupo6 == 0 ? 0 : estGrupo5)));
                    String nombre = this.buscarDetalleContablePadre(cuenta, conPK.getConEmpresa(), usuario);
                    cuentaPadre.add(cuenta + " | " + nombre);
                }
                list.add(new ConContableReporteTO(tipoDetalle, cuentaPadre, contable, detalleContable));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", usuario));
        }
        return list;
    }

    public String buscarDetalleContablePadre(String codigo, String empresa, SisInfoTO sisInfoTO) {
        List<ConCuentasTO> listaConCuentasTO = new ArrayList<ConCuentasTO>();
        String encontro = "";
        try {
            listaConCuentasTO = cuentasService.getListaBuscarConCuentasTO(empresa, codigo, null);
        } catch (Exception e) {
            UtilsExcepciones.enviarError(e, "UtilsContable", sisInfoTO);
        }

        for (ConCuentasTO conCuentasTO : listaConCuentasTO) {
            if (conCuentasTO.getCuentaCodigo().equals(codigo)) {
                encontro = conCuentasTO.getCuentaDetalle().trim();
                break;
            } else {
                encontro = "";
            }
        }
        return encontro;
    }

    @Override
    public byte[] generarReporteConsultaAnticiposLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsultaAnticiposLote> lista, String nombre) throws Exception {

        return genericReporteService.generarReporte(modulo, nombre + ".jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteConsultaAnticiposLoteWeb(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhListaDetalleAnticiposLoteTO> listDetalleAnticipoLoteSeleccion, String nombre, String periodo, String tipoContable, String numeroContable) throws Exception {
        List<ReporteConsultaAnticiposLote> listaReporte = new ArrayList<ReporteConsultaAnticiposLote>();
        String formaPagoBono = listDetalleAnticipoLoteSeleccion.get(0).getDalFormaPagoDetalle();
        String documentoBono = listDetalleAnticipoLoteSeleccion.get(0).getDalDocumento();
        String observacionBono = listDetalleAnticipoLoteSeleccion.get(0).getDalObservaciones();

        for (int i = 0; i < listDetalleAnticipoLoteSeleccion.size(); i++) {
            ReporteConsultaAnticiposLote rhReporteConsultaAnticiposLote = new ReporteConsultaAnticiposLote(periodo,
                    tipoContable, numeroContable,
                    listDetalleAnticipoLoteSeleccion.get(i).getDalFecha() == null ? ""
                    : listDetalleAnticipoLoteSeleccion.get(i).getDalFecha(),
                    listDetalleAnticipoLoteSeleccion.get(i).getDalId() == null ? ""
                    : listDetalleAnticipoLoteSeleccion.get(i).getDalId(),
                    listDetalleAnticipoLoteSeleccion.get(i).getDalNombres() == null ? ""
                    : listDetalleAnticipoLoteSeleccion.get(i).getDalNombres(),
                    listDetalleAnticipoLoteSeleccion.get(i).getDalValor() == null ? null
                    : listDetalleAnticipoLoteSeleccion.get(i).getDalValor(),
                    formaPagoBono, documentoBono, observacionBono);
            listaReporte.add(rhReporteConsultaAnticiposLote);
        }
        return genericReporteService.generarReporte(modulo, nombre + ".jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), listaReporte);
    }

    @Override
    public byte[] generarReporteConsultaAnticiposLoteColectivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsultaAnticiposLote> lista) throws Exception {

        return genericReporteService.generarReporte(modulo, "reportComprobanteAnticipoPorLoteColectivo.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReportePrestamoVista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> lista) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportComprobantePrestamo.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteRol(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportesRol> lista)
            throws Exception {

        return genericReporteService.generarReporte(modulo, "reportComprobanteRol.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportesRol> lista)
            throws Exception {

        return genericReporteService.generarReporte(modulo, "reportComprobanteLiquidacion.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteRolLoteSoporte(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportesRol> lista)
            throws Exception {

        return genericReporteService.generarReporte(modulo, "reportSoporteRolesEnLote.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteRolLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportesRol> lista)
            throws Exception {

        return genericReporteService.generarReporte(modulo, "reportComprobanteRolesEnLote.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteEmpleado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteEmpleado> listReporteEmpleado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportEmpleado.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), listReporteEmpleado);
    }

    @Override
    public byte[] generarReporteRol2(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AuxiliarReporteDetalleRoles> lista) {
        return genericReporteService.generarReporte(modulo, "reportComprobanteRol.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteRhUtilidadMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhUtilidadMotivo> lista) {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteRhXivSueldoMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhXivSueldoMotivo> lista) {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteConceptoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhListaBonoConceptoTO> lista) {
        return genericReporteService.generarReporte(modulo, "reportConceptoBonos.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteConceptoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhHorasExtrasConcepto> lista) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportConceptoHorasExtras.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    @Override
    public byte[] generarReporteMotivoPrestamo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhPrestamoMotivo> lista) {
        return genericReporteService.generarReporte(modulo, "reportMotivoPrestamo.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteMotivoRolPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhRolMotivo> lista) {
        return genericReporteService.generarReporte(modulo, "reportMotivoRolPago.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteMotivoXiiiSueldo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhXiiiSueldoMotivo> lista) {
        return genericReporteService.generarReporte(modulo, "reportMotivoXiiiSueldo.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteMotivoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhBonoMotivo> lista) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportMotivoBonos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteMotivoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhHorasExtrasMotivo> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportMotivoHorasExtras.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteMotivoHorasExtrass(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhHorasExtrasMotivo> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo horas extras");
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "STipo Contable" + "¬" + "SInactivo");
            for (RhHorasExtrasMotivo motivo : listado) {
                listaCuerpo.add((motivo.getRhHorasExtrasMotivoPK().getMotDetalle() == null ? "B"
                        : "S" + motivo.getRhHorasExtrasMotivoPK().getMotDetalle())
                        + "¬"
                        + (motivo.getConTipo().getConTipoPK().getTipCodigo() == null ? "B"
                        : "S" + motivo.getConTipo().getConTipoPK().getTipCodigo())
                        + "¬" + (motivo.getMotInactivo() ? "SInactivo"
                        : "SActivo"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReportePeriodosUtilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhUtilidadesPeriodoTO> lista) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteXiiiSueldoPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhXiiiSueldoPeriodoTO> lista) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteXivSueldoPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhXivSueldoPeriodoTO> lista) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteRhFormaPagoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhListaFormaPagoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteRhFormaPagoBeneficiosTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhListaFormaPagoBeneficioSocialTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteRhAnticipoMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhAnticipoMotivo> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteRhCategoriaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhCategoriaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteRhEmpleado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhEmpleado> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteRhEmpleadoConsuta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhFunListadoEmpleadosTO> listadoC) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoC);
    }

    @Override
    public byte[] generarReporteRhXiiiSueldoXiiiSueldoCalcular(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContable contable, List<RhXiiiSueldoXiiiSueldoCalcular> listado) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        for (RhXiiiSueldoXiiiSueldoCalcular rhXiiiSueldo : listado) {
            String obs = rhXiiiSueldo.getRhXiiiSueldo().getXiiiObservaciones() != null && !rhXiiiSueldo.getRhXiiiSueldo().getXiiiObservaciones().equals("") ? rhXiiiSueldo.getRhXiiiSueldo().getXiiiObservaciones() : contable.getConObservaciones();
            ReporteAnticipoPrestamoXIIISueldo objetoReporte = new ReporteAnticipoPrestamoXIIISueldo(
                    contable.getConContablePK().getConEmpresa() + " | " + contable.getConContablePK().getConPeriodo() + " | " + contable.getConContablePK().getConTipo() + " | " + contable.getConContablePK().getConNumero(),
                    UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                    rhXiiiSueldo.getRhXiiiSueldo().getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    rhXiiiSueldo.getRhXiiiSueldo().getRhEmpleado().getEmpApellidos() + " " + rhXiiiSueldo.getRhXiiiSueldo().getRhEmpleado().getEmpNombres(),
                    "",
                    "",
                    rhXiiiSueldo.getRhXiiiSueldo().getXiiiValor().add(BigDecimal.ZERO),
                    rhXiiiSueldo.getRhXiiiSueldo().getConCuentas().getConCuentasPK().getCtaCodigo(),
                    rhXiiiSueldo.getRhXiiiSueldo().getXiiiDocumento(),
                    obs);
            objetoReporte.setNombreCuenta(rhXiiiSueldo.getRhXiiiSueldo().getConCuentas().getCtaDetalle() != null
                    ? rhXiiiSueldo.getRhXiiiSueldo().getConCuentas().getCtaDetalle() : rhXiiiSueldo.getRhXiiiSueldo().getConCuentas().getConCuentasPK().getCtaCodigo());
            lista.add(objetoReporte);
        }
        return generarReporteXIIISueldo(usuarioEmpresaReporteTO, lista);
    }

    @Override
    public byte[] generarReporteRhXiiiSueldoXiiiSueldoCalcularDeVariosContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<ConContablePK> pks) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        for (int i = 0; i < pks.size(); i++) {
            ConContable contable = contableService.getConContable(pks.get(i));
            List<RhXiiiSueldo> listado = xiiiSueldoService.getListRhXiiiSueldo(pks.get(i));
            if (listado != null && !listado.isEmpty()) {
                for (RhXiiiSueldo rhXiiiSueldo : listado) {
                    String obs = rhXiiiSueldo.getXiiiObservaciones() != null && !rhXiiiSueldo.getXiiiObservaciones().equals("") ? rhXiiiSueldo.getXiiiObservaciones() : contable.getConObservaciones();
                    ReporteAnticipoPrestamoXIIISueldo objetoReporte = new ReporteAnticipoPrestamoXIIISueldo(
                            contable.getConContablePK().getConEmpresa() + " | " + contable.getConContablePK().getConPeriodo() + " | " + contable.getConContablePK().getConTipo() + " | " + contable.getConContablePK().getConNumero(),
                            UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                            rhXiiiSueldo.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                            rhXiiiSueldo.getRhEmpleado().getEmpApellidos() + " " + rhXiiiSueldo.getRhEmpleado().getEmpNombres(),
                            "",
                            "",
                            rhXiiiSueldo.getXiiiValor().add(BigDecimal.ZERO),
                            rhXiiiSueldo.getConCuentas().getConCuentasPK().getCtaCodigo(),
                            rhXiiiSueldo.getXiiiDocumento(),
                            obs);
                    objetoReporte.setNombreCuenta(rhXiiiSueldo.getConCuentas().getCtaDetalle() != null
                            ? rhXiiiSueldo.getConCuentas().getCtaDetalle() : rhXiiiSueldo.getConCuentas().getConCuentasPK().getCtaCodigo());
                    lista.add(objetoReporte);
                }
            }
        }
        return generarReporteXIIISueldo(usuarioEmpresaReporteTO, lista);
    }

    @Override
    public byte[] generarReporteRhXivSueldoXivSueldoCalcular(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContable contable, List<RhXivSueldoXivSueldoCalcular> listaXivSueldoImprimir) throws Exception {

        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        for (RhXivSueldoXivSueldoCalcular rhXivSueldo : listaXivSueldoImprimir) {
            String obs = rhXivSueldo.getRhXivSueldo().getXivObservaciones() != null && !rhXivSueldo.getRhXivSueldo().getXivObservaciones().equals("") ? rhXivSueldo.getRhXivSueldo().getXivObservaciones() : contable.getConObservaciones();
            ReporteAnticipoPrestamoXIIISueldo objetoReporte = new ReporteAnticipoPrestamoXIIISueldo(
                    contable.getConContablePK().getConEmpresa() + " | " + contable.getConContablePK().getConPeriodo() + " | " + contable.getConContablePK().getConTipo() + " | " + contable.getConContablePK().getConNumero(),
                    UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                    rhXivSueldo.getRhXivSueldo().getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    rhXivSueldo.getRhXivSueldo().getRhEmpleado().getEmpNombres(),
                    "",
                    "",
                    rhXivSueldo.getRhXivSueldo().getXivValor().add(BigDecimal.ZERO),
                    rhXivSueldo.getRhXivSueldo().getConCuentas().getConCuentasPK().getCtaCodigo(),
                    rhXivSueldo.getRhXivSueldo().getXivDocumento(),
                    obs);
            objetoReporte.setDiasCalculados(rhXivSueldo.getRhXivSueldo().getXivDiasCalculados());
            objetoReporte.setNombreCuenta(rhXivSueldo.getRhXivSueldo().getConCuentas().getCtaDetalle() != null ? rhXivSueldo.getRhXivSueldo().getConCuentas().getCtaDetalle() : rhXivSueldo.getRhXivSueldo().getConCuentas().getConCuentasPK().getCtaCodigo());
            lista.add(objetoReporte);
        }
        return generarReporteXIVSueldo(usuarioEmpresaReporteTO, lista);
    }

    @Override
    public byte[] generarReporteRhXivSueldoXivSueldoCalcularDeVariosContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<ConContablePK> pks) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        for (int i = 0; i < pks.size(); i++) {
            ConContable contable = contableService.getConContable(pks.get(i));
            List<RhXivSueldo> listado = xivSueldoService.getListRhXivSueldo(pks.get(i));
            if (listado != null && !listado.isEmpty()) {
                for (RhXivSueldo rhXiiiSueldo : listado) {
                    String obs = rhXiiiSueldo.getXivObservaciones() != null && !rhXiiiSueldo.getXivObservaciones().equals("") ? rhXiiiSueldo.getXivObservaciones() : contable.getConObservaciones();
                    ReporteAnticipoPrestamoXIIISueldo objetoReporte = new ReporteAnticipoPrestamoXIIISueldo(
                            contable.getConContablePK().getConEmpresa() + " | " + contable.getConContablePK().getConPeriodo() + " | " + contable.getConContablePK().getConTipo() + " | " + contable.getConContablePK().getConNumero(),
                            UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                            rhXiiiSueldo.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                            rhXiiiSueldo.getRhEmpleado().getEmpApellidos() + " " + rhXiiiSueldo.getRhEmpleado().getEmpNombres(),
                            "",
                            "",
                            rhXiiiSueldo.getXivValor().add(BigDecimal.ZERO),
                            rhXiiiSueldo.getConCuentas().getConCuentasPK().getCtaCodigo(),
                            rhXiiiSueldo.getXivDocumento(),
                            obs);
                    objetoReporte.setNombreCuenta(rhXiiiSueldo.getConCuentas().getCtaDetalle() != null
                            ? rhXiiiSueldo.getConCuentas().getCtaDetalle() : rhXiiiSueldo.getConCuentas().getConCuentasPK().getCtaCodigo());
                    objetoReporte.setDiasCalculados(rhXiiiSueldo.getXivDiasCalculados());
                    lista.add(objetoReporte);
                }
            }
        }
        return generarReporteXIVSueldo(usuarioEmpresaReporteTO, lista);
    }

    @Override
    public byte[] generarReporteComprobanteAnticipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContable contable, List<RhAnticipo> listaRhAnticipo, String nombreReporte) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        for (RhAnticipo rhAnticipo : listaRhAnticipo) {
            String obs = rhAnticipo.getAntObservaciones() != null && !rhAnticipo.getAntObservaciones().equals("") ? rhAnticipo.getAntObservaciones() : contable.getConObservaciones();
            ReporteAnticipoPrestamoXIIISueldo objetoReporte = new ReporteAnticipoPrestamoXIIISueldo(
                    contable.getConContablePK().getConEmpresa() + " | " + contable.getConContablePK().getConPeriodo() + " | " + contable.getConContablePK().getConTipo() + " | " + contable.getConContablePK().getConNumero(),
                    UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                    rhAnticipo.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    rhAnticipo.getRhEmpleado().getEmpNombres(),
                    "",
                    "",
                    rhAnticipo.getAntValor().add(BigDecimal.ZERO),
                    rhAnticipo.getConCuentas().getConCuentasPK().getCtaCodigo(),
                    rhAnticipo.getAntDocumento(),
                    obs);
            objetoReporte.setNombreCuenta(rhAnticipo.getConCuentas().getCtaDetalle() != null ? rhAnticipo.getConCuentas().getCtaDetalle() : rhAnticipo.getConCuentas().getConCuentasPK().getCtaCodigo());
            lista.add(objetoReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteComprobanteAnticipoDeVariosContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<ConContablePK> pks) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        for (int i = 0; i < pks.size(); i++) {
            ConContable contable = contableService.getConContable(pks.get(i));
            List<RhAnticipo> listaRhAnticipo = anticipoService.getListRhAnticipo(pks.get(i));
            if (listaRhAnticipo != null && !listaRhAnticipo.isEmpty()) {
                for (RhAnticipo rhAnticipo : listaRhAnticipo) {
                    String obs = rhAnticipo.getAntObservaciones() != null && !rhAnticipo.getAntObservaciones().equals("") ? rhAnticipo.getAntObservaciones() : contable.getConObservaciones();
                    ReporteAnticipoPrestamoXIIISueldo objetoReporte = new ReporteAnticipoPrestamoXIIISueldo(
                            contable.getConContablePK().getConEmpresa() + " | " + contable.getConContablePK().getConPeriodo() + " | " + contable.getConContablePK().getConTipo() + " | " + contable.getConContablePK().getConNumero(),
                            UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                            rhAnticipo.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                            rhAnticipo.getRhEmpleado().getEmpNombres(),
                            "",
                            "",
                            rhAnticipo.getAntValor().add(BigDecimal.ZERO),
                            rhAnticipo.getConCuentas().getConCuentasPK().getCtaCodigo(),
                            rhAnticipo.getAntDocumento(),
                            obs);
                    objetoReporte.setNombreCuenta(rhAnticipo.getConCuentas().getCtaDetalle() != null ? rhAnticipo.getConCuentas().getCtaDetalle() : rhAnticipo.getConCuentas().getConCuentasPK().getCtaCodigo());
                    objetoReporte.setNombres(rhAnticipo.getRhEmpleado().getEmpNombres() != null ? rhAnticipo.getRhEmpleado().getEmpApellidos() + " " + rhAnticipo.getRhEmpleado().getEmpNombres() : null);
                    lista.add(objetoReporte);
                }
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteComprobanteUtilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContable contable, List<RhUtilidades> listaRhUtilidades, String nombreReporte) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        for (RhUtilidades rhUtilidades : listaRhUtilidades) {
            ConCuentas cuenta = cuentasService.obtenerConCuenta(rhUtilidades.getConCuentas().getConCuentasPK().getCtaEmpresa(), rhUtilidades.getConCuentas().getConCuentasPK().getCtaCodigo());
            String obs = rhUtilidades.getUtiObservaciones() != null && !rhUtilidades.getUtiObservaciones().equals("") ? rhUtilidades.getUtiObservaciones() : contable.getConObservaciones();
            ReporteAnticipoPrestamoXIIISueldo objetoReporte = new ReporteAnticipoPrestamoXIIISueldo(
                    contable.getConContablePK().getConEmpresa() + " | " + contable.getConContablePK().getConPeriodo() + " | " + contable.getConContablePK().getConTipo() + " | " + contable.getConContablePK().getConNumero(),
                    UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                    rhUtilidades.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    rhUtilidades.getRhEmpleado().getEmpNombres(),
                    "",
                    "",
                    rhUtilidades.getUtiValorTotal().add(BigDecimal.ZERO),
                    rhUtilidades.getConCuentas().getConCuentasPK().getCtaCodigo(),
                    rhUtilidades.getUtiDocumento(),
                    obs);
            objetoReporte.setNombreCuenta(cuenta != null && cuenta.getCtaDetalle() != null
                    ? cuenta.getCtaDetalle() : rhUtilidades.getConCuentas().getConCuentasPK().getCtaCodigo());
            objetoReporte.setFormaPago(rhUtilidades.getConCuentas().getConCuentasPK().getCtaCodigo() + "-" + cuenta.getCtaDetalle());
            objetoReporte.setNombres(rhUtilidades.getRhEmpleado().getEmpApellidos() + " " + rhUtilidades.getRhEmpleado().getEmpNombres());
            objetoReporte.setCargo(rhUtilidades.getRhEmpleado().getEmpCargo());

            objetoReporte.setValor(objetoReporte.getValor() == null ? BigDecimal.ZERO : objetoReporte.getValor());
            objetoReporte.setImpuestoRenta(rhUtilidades.getUtiImpuestoRenta());
            objetoReporte.setImpuestoRenta(objetoReporte.getImpuestoRenta() == null ? BigDecimal.ZERO : objetoReporte.getImpuestoRenta());
            BigDecimal valorARecibir = objetoReporte.getValor().subtract(objetoReporte.getImpuestoRenta());
            objetoReporte.setValorARecibir(valorARecibir);

            lista.add(objetoReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    @Override
    public byte[] generarReporteComprobanteUtilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhFunUtilidadesConsultarTO> listaRhUtilidades, String nombreReporte) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        listaRhUtilidades.forEach((rhUtilidades) -> {
            ConContable contable = contableService.getConContable(new ConContablePK(usuarioEmpresaReporteTO.getEmpCodigo(), rhUtilidades.getUtiPeriodo(), rhUtilidades.getUtiTipo(), rhUtilidades.getUtiNumero()));
            String obs = rhUtilidades.getUtiObservaciones() != null && !rhUtilidades.getUtiObservaciones().equals("") ? rhUtilidades.getUtiObservaciones() : contable.getConObservaciones();
            ReporteAnticipoPrestamoXIIISueldo objetoReporte = new ReporteAnticipoPrestamoXIIISueldo(
                    usuarioEmpresaReporteTO.getEmpCodigo() + " | " + rhUtilidades.getUtiPeriodo() + " | " + rhUtilidades.getUtiTipo() + " | " + rhUtilidades.getUtiNumero(),
                    UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                    rhUtilidades.getUtiId(),
                    rhUtilidades.getUtiNombres() + " " + rhUtilidades.getUtiApellidos(),
                    "",
                    "",
                    rhUtilidades.getUtiValorUtilidades().add(BigDecimal.ZERO),
                    rhUtilidades.getUtiFormaPago(),
                    rhUtilidades.getUtiDocumento(),
                    obs);

            objetoReporte.setValor(objetoReporte.getValor() == null ? BigDecimal.ZERO : objetoReporte.getValor());
            objetoReporte.setImpuestoRenta(rhUtilidades.getUtiImpuestoRenta());
            objetoReporte.setImpuestoRenta(objetoReporte.getImpuestoRenta() == null ? BigDecimal.ZERO : objetoReporte.getImpuestoRenta());
            BigDecimal valorARecibir = objetoReporte.getValor().subtract(objetoReporte.getImpuestoRenta());
            objetoReporte.setValorARecibir(valorARecibir);
            lista.add(objetoReporte);
        });
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    @Override
    public byte[] generarReporteComprobanteRol(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, ConContablePK contablePK) throws Exception {
        ConContable contable = contableService.getConContable(contablePK);
        List<RhRol> listRol = rolService.getListRhRol(contablePK);
        List<ReportesRol> listaRol = new ArrayList<>();
        if (listRol != null && !listRol.isEmpty()) {
            List<RhListaDetalleRolesTO> listaDetalleRolesTO = new ArrayList<>();
            for (RhRol itemRol : listRol) {
                listaDetalleRolesTO.addAll(eliminarDetallesRolesNulos(
                        rolService.getRhDetalleRolesTO(
                                contablePK.getConEmpresa(),
                                UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                                UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                                itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(),
                                itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                                itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(), ""), false, null));
            }
            for (RhRol itemRol : listRol) {
                List<RhListaRolSaldoEmpleadoDetalladoTO> detalleList = rolService.getRhRolSaldoEmpleadoDetalladoTO(contablePK.getConEmpresa(), itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                        UtilsDate.fechaFormatoString(itemRol.getRolDesde(), "dd-MM-yyyy"), UtilsDate.fechaFormatoString(itemRol.getRolHasta(), "dd-MM-yyyy"));
                for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                    if (itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId().equalsIgnoreCase(itemDetalle.getLrpId())) {
                        convertirValoresItemDetalleVaciosACero(itemDetalle);
                        if (detalleList == null || detalleList.isEmpty()) {
                            listaRol.add(new ReportesRol(itemDetalle, "", "", "", "", "", null, "", itemRol.getPrdSector().getPrdSectorPK().getSecCodigo()));
                        } else {
                            for (RhListaRolSaldoEmpleadoDetalladoTO detalle : detalleList) {
                                listaRol.add(new ReportesRol(itemDetalle, detalle.getSedConcepto(), detalle.getSedDetalle(), detalle.getSedCp(), detalle.getSedCc(), detalle.getSedFecha(), detalle.getSedValor(),
                                        detalle.getSedObservaciones(), itemRol.getRhEmpleado().getPrdSector().getPrdSectorPK().getSecCodigo()));
                            }
                        }
                    }
                }
            }
        }
        return generarReporteRol(usuarioEmpresaReporteTO, listaRol);
    }

    @Override
    public byte[] generarReporteComprobanteRolDeVarioContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<ConContablePK> pks) throws Exception {
        List<ReportesRol> listaRol = new ArrayList<>();
        for (int i = 0; i < pks.size(); i++) {
            ConContable contable = contableService.getConContable(pks.get(i));
            List<RhRol> listRol = rolService.getListRhRol(pks.get(i));
            if (listRol != null && !listRol.isEmpty()) {
                List<RhListaDetalleRolesTO> listaDetalleRolesTO = new ArrayList<>();
                for (RhRol itemRol : listRol) {
                    listaDetalleRolesTO.addAll(eliminarDetallesRolesNulos(
                            rolService.getRhDetalleRolesTO(
                                    pks.get(i).getConEmpresa(),
                                    UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                                    UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                                    itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(),
                                    itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                                    itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(), ""), false, null));
                }
                for (RhRol itemRol : listRol) {
                    List<RhListaRolSaldoEmpleadoDetalladoTO> detalleList = rolService.getRhRolSaldoEmpleadoDetalladoTO(pks.get(i).getConEmpresa(), itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                            UtilsDate.fechaFormatoString(itemRol.getRolDesde(), "dd-MM-yyyy"), UtilsDate.fechaFormatoString(itemRol.getRolHasta(), "dd-MM-yyyy"));
                    for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                        if (itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId().equalsIgnoreCase(itemDetalle.getLrpId())) {
                            convertirValoresItemDetalleVaciosACero(itemDetalle);
                            if (detalleList == null || detalleList.isEmpty()) {
                                listaRol.add(new ReportesRol(itemDetalle, "", "", "", "", "", null, "", itemRol.getPrdSector().getPrdSectorPK().getSecCodigo()));
                            } else {
                                for (RhListaRolSaldoEmpleadoDetalladoTO detalle : detalleList) {
                                    listaRol.add(new ReportesRol(itemDetalle, detalle.getSedConcepto(), detalle.getSedDetalle(), detalle.getSedCp(), detalle.getSedCc(), detalle.getSedFecha(), detalle.getSedValor(),
                                            detalle.getSedObservaciones(), itemRol.getRhEmpleado().getPrdSector().getPrdSectorPK().getSecCodigo()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return generarReporteRol(usuarioEmpresaReporteTO, listaRol);
    }

    @Override
    public byte[] generarReporteRhEmpleadoDatosPersonales(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhEmpleado> listadoC) throws Exception {
        List<ReporteEmpleado> list = new ArrayList<>();
        for (RhEmpleado empleado : listadoC) {
            list.add(generarReporte(empleado));
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), list);
    }

    public ReporteEmpleado generarReporte(RhEmpleado empleado) throws Exception {
        ReporteEmpleado repEmp = new ReporteEmpleado();
        String nombreCuenta = "";
        BanBancoTO banco = new BanBancoTO();
        List<InformacionAdicional> listaInf = new ArrayList<>();
        repEmp.setSecCodigo(empleado.getPrdSector().getPrdSectorPK().getSecCodigo());
        repEmp.setCatNombre(empleado.getRhCategoria().getRhCategoriaPK().getCatNombre());
        AnxPaisTO pais = paisDao.getAnxPaisTO(empleado.getEmpResidenciaPais());
        repEmp.setEmpNacionalidad(pais != null ? pais.getPaisNombre() : "");
        repEmp.setEmpTipoId(empleado.getRhEmpleadoPK().getEmpId());
        repEmp.setEmpId(empleado.getRhEmpleadoPK().getEmpId());
        repEmp.setEmpNombres(empleado.getEmpNombres());
        repEmp.setEmpApellidos(empleado.getEmpApellidos());
        repEmp.setEmpGenero("M".compareToIgnoreCase(String.valueOf(empleado.getEmpGenero())) == 0 ? "MASCULINO" : "FEMENINO");
        repEmp.setEmpFechaNacimiento(UtilsDate.fechaFormatoString(empleado.getEmpFechaNacimiento(), "yyyy-MM-dd"));
        repEmp.setEmpProvincia(empleado.getEmpProvincia());
        repEmp.setEmpCanton(empleado.getEmpCanton());
        AnxProvinciaCantonTO provincia = provinciasDao.obtenerProvincia(empleado.getEmpProvincia());
        AnxProvinciaCantonTO canton = provinciasDao.obtenerCanton(empleado.getEmpProvincia(), empleado.getEmpCanton());
        repEmp.setEmpProvincia(provincia == null ? "" : provincia.getNombre());
        repEmp.setEmpCanton(canton == null ? "" : canton.getNombre());
        repEmp.setEmpCiudad(empleado.getEmpLugarNacimiento());
        repEmp.setEmpDomicilio(empleado.getEmpDomicilio());
        repEmp.setEmpNumero(empleado.getEmpNumero());
        repEmp.setEmpTelefono(empleado.getEmpTelefono());
        repEmp.setEmpEstadoCivil(empleado.getEmpEstadoCivil());
        repEmp.setEmpCargasFamiliares(String.valueOf(empleado.getEmpCargasFamiliares()));
        repEmp.setEmpObservaciones(empleado.getEmpObservaciones());
        repEmp.setEmpFechaPrimerIngreso(UtilsDate.fechaFormatoString(empleado.getEmpFechaPrimerIngreso(), "yyyy-MM-dd"));
        repEmp.setEmpFechaUltimoIngreso(UtilsDate.fechaFormatoString(empleado.getEmpFechaUltimoIngreso(), "yyyy-MM-dd"));
        repEmp.setEmpFechaPrimeraSalida(UtilsDate.fechaFormatoString(empleado.getEmpFechaPrimeraSalida(), "yyyy-MM-dd"));
        // repEmp.setEmpFechaPrimeraSalida(UtilsDate.fechaFormatoString(empleado.getEmpFechaPrimeraSalida(), "yyyy-MM-dd"));
        repEmp.setEmpFechaUltimaSalida(UtilsDate.fechaFormatoString(empleado.getEmpFechaUltimaSalida(), "yyyy-MM-dd"));
        repEmp.setEmpFechaAfiliacionIess(UtilsDate.fechaFormatoString(empleado.getEmpFechaAfiliacionIess(), "yyyy-MM-dd"));
        repEmp.setEmpCodigoCargoIess(empleado.getEmpCodigoCargo());
        repEmp.setEmpCodigoAfiliacionIess(empleado.getEmpCodigoAfiliacionIess());
        repEmp.setEmpCargo(empleado.getEmpCargo());
        repEmp.setEmpFormaPago(empleado.getEmpFormaPago());
        repEmp.setEmpDiasTrabajados(String.valueOf(empleado.getEmpDiasTrabajados()));
        repEmp.setEmpDiasDescanso(String.valueOf(empleado.getEmpDiasDescanso()));
        repEmp.setEmpBonoFijo(empleado.getEmpBonoFijo().toString());
        repEmp.setEmpBonoFijoND(empleado.getEmpBonoFijoNd().toString());
        repEmp.setEmpOtrosIngresos(empleado.getEmpOtrosIngresos().toString());
        repEmp.setEmpOtrosIngresosNd(empleado.getEmpOtrosIngresosNd().toString());
        repEmp.setEmpSueldoIess(empleado.getEmpSueldoIess());
        repEmp.setEmpCorreo(empleado.getEmpCorreoElectronico());
        //Certificado de Trabajo
        repEmp.setEmpInactivo(empleado.getEmpInactivo());
        SisEmpresa empresa = empresaDao.obtenerPorId(SisEmpresa.class, empleado.getRhEmpleadoPK().getEmpEmpresa());
        repEmp.setEmpresaNombre(empresa.getEmpNombre());
        repEmp.setEmpresaRUC(empresa.getEmpRuc());
        repEmp.setEmpresaCiudad(empresa.getEmpCiudad());
        // SRI
        repEmp.setEmpEducacion(empleado.getEmpEducacion());
        repEmp.setEmpAlimentacion(empleado.getEmpAlimentacion());
        repEmp.setEmpSalud(empleado.getEmpSalud());
        repEmp.setEmpVivienda(empleado.getEmpVivienda());
        repEmp.setEmpVestuario(empleado.getEmpVestuario());
        repEmp.setEmpUtilidades(empleado.getEmpUtilidades());
        repEmp.setEmpRebajaDiscapacidad(empleado.getEmpRebajaDiscapacidad());
        repEmp.setEmpRebajaTerceraEdad(empleado.getEmpRebajaTerceraEdad());
        repEmp.setEmpSueldoOtraCompania(empleado.getEmpSueldoOtraCompania());
        repEmp.setEmpOtrasDeducciones(empleado.getEmpOtrasDeducciones());
        repEmp.setEmpOtrasRebajas(empleado.getEmpOtrasRebajas());
        // Financiero
        if (empleado.getEmpBanco() != null && !empleado.getEmpBanco().equals("")) {
            banco = bancoService.getBancoTO(empleado.getRhEmpleadoPK().getEmpEmpresa(), empleado.getEmpBanco());
        }
        repEmp.setEmpBanco(empleado.getEmpBanco() == null ? "" : empleado.getEmpBanco() + " - " + banco.getBanNombre());
        List<ComboGenericoTO> cuentasBancarias = new ArrayList<>();
        cuentasBancarias.add(new ComboGenericoTO("03", "CTA CORRIENTE"));
        cuentasBancarias.add(new ComboGenericoTO("04", "CTA AHORRO"));
        cuentasBancarias.add(new ComboGenericoTO("05", "CTA VIRTUAL"));

        if (empleado.getEmpCuentaTipo() != null) {
            for (ComboGenericoTO cuenta : cuentasBancarias) {
                if (cuenta.getClave().equals(empleado.getEmpCuentaTipo())) {
                    nombreCuenta = cuenta.getValor();
                }
            }
        }
        repEmp.setEmpCuentaTipo(empleado.getEmpBanco() == null ? "" : empleado.getEmpCuentaTipo() + " - " + nombreCuenta);
        repEmp.setEmpCuentaNumero(empleado.getEmpBanco() == null ? "" : empleado.getEmpCuentaNumero());
        //INFORMACION ADICIONAL
        if (empleado.getEmpDescripcion() != null && !empleado.getEmpDescripcion().equals("")) {
            java.util.List<String> informacionAdicional = UtilsValidacion.separar(empleado.getEmpDescripcion(), "|");
            for (int i = 0; i < informacionAdicional.size(); i++) {
                if (!informacionAdicional.get(i).equals("|")
                        && informacionAdicional.get(i).compareTo("") > 0
                        && informacionAdicional.get(i).lastIndexOf("=") >= 0) {
                    InformacionAdicional inf = new InformacionAdicional();
                    inf.setNombre(informacionAdicional.get(i).substring(0, informacionAdicional.get(i).lastIndexOf("=")));
                    inf.setValor(informacionAdicional.get(i).substring(informacionAdicional.get(i).lastIndexOf("=") + 1));
                    listaInf.add(inf);
                }
            }
            repEmp.setInfoAdicional(listaInf);
        }
        return repEmp;
    }

    //Exportar
    @Override
    public Map<String, Object> exportarReporteRhUtilidadMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhUtilidadMotivo> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo de utilidad");
            listaCabecera.add("S");
            listaCuerpo.add("STipo comprobante" + "¬" + "SDetalle" + "¬" + "SInactivo");
            for (RhUtilidadMotivo rhUtilidadMotivo : lista) {
                listaCuerpo.add(
                        (rhUtilidadMotivo.getConTipo() == null ? "B" : "S" + rhUtilidadMotivo.getConTipo().getTipDetalle()) + "¬"
                        + (rhUtilidadMotivo.getRhUtilidadMotivoPK() == null ? "B" : "S" + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle()) + "¬"
                        + (rhUtilidadMotivo.getMotInactivo() == false ? "SACTIVO" : "SINACTIVO"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteRhXivSueldoMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXivSueldoMotivo> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo de 14º sueldo");
            listaCabecera.add("S");
            listaCuerpo.add("STipo comprobante" + "¬" + "SDetalle" + "¬" + "SInactivo");
            for (RhXivSueldoMotivo rhXivSueldoMotivo : lista) {
                listaCuerpo.add(
                        (rhXivSueldoMotivo.getConTipo() == null ? "B" : "S" + rhXivSueldoMotivo.getConTipo().getTipDetalle()) + "¬"
                        + (rhXivSueldoMotivo.getRhXivSueldoMotivoPK() == null ? "B" : "S" + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle()) + "¬"
                        + (rhXivSueldoMotivo.getMotInactivo() == false ? "SACTIVO" : "SINACTIVO"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhBonoMotivo> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo bonos");
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "STipo Contable" + "¬" + "SInactivo");
            for (RhBonoMotivo motivo : listado) {
                listaCuerpo.add((motivo.getRhBonoMotivoPK().getMotDetalle() == null ? "B"
                        : "S" + motivo.getRhBonoMotivoPK().getMotDetalle())
                        + "¬"
                        + (motivo.getConTipo().getConTipoPK().getTipCodigo() == null ? "B"
                        : "S" + motivo.getConTipo().getConTipoPK().getTipCodigo())
                        + "¬" + (motivo.getMotInactivo() ? "SInactivo"
                        : "SActivo"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConceptoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaBonoConceptoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de concepto bonos");
            listaCabecera.add("S");
            listaCuerpo.add("SNombre" + "¬" + "SInactivo");
            for (RhListaBonoConceptoTO motivo : listado) {
                listaCuerpo.add((motivo.getBcDetalle() == null ? "B"
                        : "S" + motivo.getBcDetalle())
                        + "¬" + (motivo.getBcInactivo() ? "SInactivo"
                        : "SActivo"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConceptoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhHorasExtrasConcepto> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de concepto horas extras");
            listaCabecera.add("S");
            listaCuerpo.add("SNombre" + "¬" + "SInactivo");
            for (RhHorasExtrasConcepto concepto : listado) {
                listaCuerpo.add((concepto.getHecDetalle() == null ? "B" : "S" + concepto.getHecDetalle())
                        + "¬" + (concepto.getHecInactivo() ? "SInactivo" : "SActivo"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteProvisionDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String titulo, List<RhProvisionDetalladoTO> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCuadro de provisiones (Detallado por trabajador)");
            listaCabecera.add("S");
            listaCuerpo.add("SCedula" + "¬" + "SNombres" + "¬" + "SProvision vacaciones" + "¬" + "SProvisión Xiii Sueldo" + "¬" + "SProvisión Xiv Sueldo");
            for (RhProvisionDetalladoTO concepto : datos) {
                listaCuerpo.add(
                        (concepto.getProvId() == null ? "B" : "S" + concepto.getProvId())
                        + "¬" + (concepto.getProvNombres() == null ? "B" : "S" + concepto.getProvNombres())
                        + "¬" + (concepto.getProvVacaciones() == null ? "B" : "D" + concepto.getProvVacaciones())
                        + "¬" + (concepto.getProvXiii() == null ? "B" : "D" + concepto.getProvXiii())
                        + "¬" + (concepto.getProvXiv() == null ? "B" : "D" + concepto.getProvXiv())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsolidadoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaConsolidadoBonosTO> listado,
            String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();

            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Consolidado de Bonos y Viáticos");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCat." + "¬" + "SId" + "¬" + "SNombres" + "¬" + "SHoras Extras" + "¬" + "SHoras Extras 100" + "¬" + "SBonos" + "¬" + "SBonos ND" + "¬"
                    + "SBono Fijo" + "¬" + "SBono Fijo ND" + "¬" + "STotal");
            for (RhListaConsolidadoBonosTO rhListaConsolidadoAnticiposPrestamosTO : listado) {
                listaCuerpo.add((rhListaConsolidadoAnticiposPrestamosTO.getCbvCategoria() == null ? "B"
                        : "S" + rhListaConsolidadoAnticiposPrestamosTO.getCbvCategoria().toString())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCbvId() == null ? "B"
                        : "S" + rhListaConsolidadoAnticiposPrestamosTO.getCbvId().toString())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCbvNombres() == null ? "B"
                        : "S" + rhListaConsolidadoAnticiposPrestamosTO.getCbvNombres().toString())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCbvHorasExtras() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCbvHorasExtras().toString())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCbvHorasExtras100() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCbvHorasExtras100().toString())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCbvBonos() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCbvBonos().toString())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCbvBonosND() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCbvBonosND().toString())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCbvBonoFijo() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCbvBonoFijo().toString())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCbvBonoFijoND() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCbvBonoFijoND().toString())
                        + "¬" + (rhListaConsolidadoAnticiposPrestamosTO.getCbvTotal() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCbvTotal().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsolidadoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaConsolidadoHorasExtrasTO> listado,
            String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();

            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Consolidado Horas Extras");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCategoria." + "¬" + "SId" + "¬" + "SNombres" + "¬" + "SRol Horas Extras 50" + "¬" + "SRol Horas Extras 100" + "¬" + "SRol Horas Extraordinarias 100" + "¬" + "SHoras Extras 50" + "¬"
                    + "SHoras Extras 100" + "¬" + "SHoras Extraordinarias 100" + "¬" + "STotal");
            for (RhListaConsolidadoHorasExtrasTO rhListaConsolidadoHorasExtrasTO : listado) {
                listaCuerpo.add((rhListaConsolidadoHorasExtrasTO.getCheCategoria() == null ? "B"
                        : "S" + rhListaConsolidadoHorasExtrasTO.getCheCategoria().toString())
                        + "¬"
                        + (rhListaConsolidadoHorasExtrasTO.getCheId() == null ? "B"
                        : "S" + rhListaConsolidadoHorasExtrasTO.getCheId().toString())
                        + "¬"
                        + (rhListaConsolidadoHorasExtrasTO.getCheNombres() == null ? "B"
                        : "S" + rhListaConsolidadoHorasExtrasTO.getCheNombres().toString())
                        + "¬"
                        + (rhListaConsolidadoHorasExtrasTO.getCheRolValor50() == null ? "B"
                        : "D" + rhListaConsolidadoHorasExtrasTO.getCheRolValor50().toString())
                        + "¬"
                        + (rhListaConsolidadoHorasExtrasTO.getCheRolValor100() == null ? "B"
                        : "D" + rhListaConsolidadoHorasExtrasTO.getCheRolValor100().toString())
                        + "¬"
                        + (rhListaConsolidadoHorasExtrasTO.getCheRolValorExtraordinario() == null ? "B"
                        : "D" + rhListaConsolidadoHorasExtrasTO.getCheRolValorExtraordinario().toString())
                        + "¬"
                        + (rhListaConsolidadoHorasExtrasTO.getCheValor50() == null ? "B"
                        : "D" + rhListaConsolidadoHorasExtrasTO.getCheValor50().toString())
                        + "¬"
                        + (rhListaConsolidadoHorasExtrasTO.getCheValor100() == null ? "B"
                        : "D" + rhListaConsolidadoHorasExtrasTO.getCheValor100().toString())
                        + "¬"
                        + (rhListaConsolidadoHorasExtrasTO.getCheValorExtraordinaria() == null ? "B"
                        : "D" + rhListaConsolidadoHorasExtrasTO.getCheValorExtraordinaria().toString())
                        + "¬" + (rhListaConsolidadoHorasExtrasTO.getCheTotal() == null ? "B"
                        : "D" + rhListaConsolidadoHorasExtrasTO.getCheTotal().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsolidadoBonosHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaConsolidadoBonosHorasExtrasTO> listado, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();

            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Consolidado de Bonos y Horas Extras");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCat." + "¬" + "SId" + "¬" + "SNombres" + "¬" + "SRol Horas Extras" + "¬" + "SRol Horas Extras 100" + "¬" + "SRol Horas Extras Extraordinaria 100" + "¬" + "SBonos" + "¬" + "SBonos ND" + "¬"
                    + "SBono Fijo" + "¬" + "SBono Fijo ND" + "¬" + "SHoras Extras" + "¬" + "SHoras Extras 100" + "¬" + "SHoras Extras Extraordinaria 100" + "¬" + "STotal");
            listado.forEach((objeto) -> {
                listaCuerpo.add(
                        (objeto.getCbheCategoria() == null ? "B" : "S" + objeto.getCbheCategoria()) + "¬"
                        + (objeto.getCbheId() == null ? "B" : "S" + objeto.getCbheId()) + "¬"
                        + (objeto.getCbheNombres() == null ? "B" : "S" + objeto.getCbheNombres()) + "¬"
                        + (objeto.getCbheRolHorasExtras() == null ? "B" : "D" + objeto.getCbheRolHorasExtras().toString()) + "¬"
                        + (objeto.getCbheRolHorasExtras100() == null ? "B" : "D" + objeto.getCbheRolHorasExtras100().toString()) + "¬"
                        + (objeto.getCbheRolHorasExtrasExtraordinarias100() == null ? "B" : "D" + objeto.getCbheRolHorasExtrasExtraordinarias100().toString()) + "¬"
                        + (objeto.getCbheBonos() == null ? "B" : "D" + objeto.getCbheBonos().toString()) + "¬"
                        + (objeto.getCbheBonosND() == null ? "B" : "D" + objeto.getCbheBonosND().toString()) + "¬"
                        + (objeto.getCbheBonoFijo() == null ? "B" : "D" + objeto.getCbheBonoFijo().toString()) + "¬"
                        + (objeto.getCbheBonoFijoND() == null ? "B" : "D" + objeto.getCbheBonoFijoND().toString()) + "¬"
                        + (objeto.getCbheHorasExtras() == null ? "B" : "D" + objeto.getCbheHorasExtras().toString()) + "¬"
                        + (objeto.getCbheHorasExtras100() == null ? "B" : "D" + objeto.getCbheHorasExtras100().toString()) + "¬"
                        + (objeto.getCbheHorasExtrasExtraordinarias100() == null ? "B" : "D" + objeto.getCbheHorasExtrasExtraordinarias100().toString()) + "¬"
                        + (objeto.getCbheTotal() == null ? "B" : "D" + objeto.getCbheTotal().toString()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoPrestamo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhPrestamoMotivo> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo prestamo");
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "STipo Contable" + "¬" + "SInactivo");
            for (RhPrestamoMotivo motivo : listado) {
                listaCuerpo.add((motivo.getRhPrestamoMotivoPK().getMotDetalle() == null ? "B"
                        : "S" + motivo.getRhPrestamoMotivoPK().getMotDetalle())
                        + "¬"
                        + (motivo.getConTipo().getConTipoPK().getTipCodigo() == null ? "B"
                        : "S" + motivo.getConTipo().getConTipoPK().getTipCodigo())
                        + "¬" + (motivo.getMotInactivo() ? "SInactivo"
                        : "SActivo"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoRolPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhRolMotivo> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo rol pago");
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "STipo Contable" + "¬" + "SInactivo" + "¬" + "SEmpleado horas extras");
            for (RhRolMotivo motivo : listado) {
                listaCuerpo.add((motivo.getRhRolMotivoPK().getMotDetalle() == null ? "B"
                        : "S" + motivo.getRhRolMotivoPK().getMotDetalle())
                        + "¬"
                        + (motivo.getConTipo().getConTipoPK().getTipCodigo() == null ? "B"
                        : "S" + motivo.getConTipo().getConTipoPK().getTipCodigo())
                        + "¬" + (motivo.getMotInactivo() ? "SInactivo"
                        : "SActivo")
                        + "¬" + (motivo.isMotRegistrarHoraExtra() ? "SSI"
                        : "SNO")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoXiiiSueldo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXiiiSueldoMotivo> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo decimoo xiii sueldo");
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "STipo Contable" + "¬" + "SInactivo");
            for (RhXiiiSueldoMotivo motivo : listado) {
                listaCuerpo.add((motivo.getRhXiiiSueldoMotivoPK().getMotDetalle() == null ? "B"
                        : "S" + motivo.getRhXiiiSueldoMotivoPK().getMotDetalle())
                        + "¬"
                        + (motivo.getConTipo().getConTipoPK().getTipCodigo() == null ? "B"
                        : "S" + motivo.getConTipo().getConTipoPK().getTipCodigo())
                        + "¬" + (motivo.getMotInactivo() ? "SInactivo"
                        : "SActivo"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePeriodosUtilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhUtilidadesPeriodoTO> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de periodo de utilidad");
            listaCabecera.add("S");
            listaCuerpo.add("SDescripción" + "¬" + "SFecha desde" + "¬" + "Sfecha hasta" + "¬" + "SFecha maximo pago");
            for (RhUtilidadesPeriodoTO rhUtilidadesPeriodoTO : lista) {
                listaCuerpo.add(
                        (rhUtilidadesPeriodoTO.getUtiDescripcion() == null ? "B" : "S" + rhUtilidadesPeriodoTO.getUtiDescripcion()) + "¬"
                        + (rhUtilidadesPeriodoTO.getUtiDesde() == null ? "B" : "S" + rhUtilidadesPeriodoTO.getUtiDesde()) + "¬"
                        + (rhUtilidadesPeriodoTO.getUtiHasta() == null ? "B" : "S" + rhUtilidadesPeriodoTO.getUtiHasta()) + "¬"
                        + (rhUtilidadesPeriodoTO.getUtiFechaMaximaPago() == null ? "B" : "S" + rhUtilidadesPeriodoTO.getUtiFechaMaximaPago())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteXiiiSueldoPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXiiiSueldoPeriodoTO> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de periodo de 13º sueldo");
            listaCabecera.add("S");
            listaCuerpo.add("SDescripción" + "¬" + "SFecha desde" + "¬" + "Sfecha hasta" + "¬" + "SFecha maximo pago");
            for (RhXiiiSueldoPeriodoTO rhXiiiSueldoPeriodoTO : lista) {
                listaCuerpo.add(
                        (rhXiiiSueldoPeriodoTO.getXiiiDescripcion() == null ? "B" : "S" + rhXiiiSueldoPeriodoTO.getXiiiDescripcion()) + "¬"
                        + (rhXiiiSueldoPeriodoTO.getXiiiDesde() == null ? "B" : "S" + rhXiiiSueldoPeriodoTO.getXiiiDesde()) + "¬"
                        + (rhXiiiSueldoPeriodoTO.getXiiiHasta() == null ? "B" : "S" + rhXiiiSueldoPeriodoTO.getXiiiHasta()) + "¬"
                        + (rhXiiiSueldoPeriodoTO.getXiiiFechaMaximaPago() == null ? "B" : "S" + rhXiiiSueldoPeriodoTO.getXiiiFechaMaximaPago())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteXivSueldoPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXivSueldoPeriodoTO> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de periodo de 14º sueldo");
            listaCabecera.add("S");
            listaCuerpo.add("SDescripción" + "¬" + "SFecha desde" + "¬" + "Sfecha hasta" + "¬" + "SFecha maximo pago");
            for (RhXivSueldoPeriodoTO rhXivSueldoPeriodoTO : lista) {
                listaCuerpo.add(
                        (rhXivSueldoPeriodoTO.getXivDescripcion() == null ? "B" : "S" + rhXivSueldoPeriodoTO.getXivDescripcion()) + "¬"
                        + (rhXivSueldoPeriodoTO.getXivDesde() == null ? "B" : "S" + rhXivSueldoPeriodoTO.getXivDesde()) + "¬"
                        + (rhXivSueldoPeriodoTO.getXivHasta() == null ? "B" : "S" + rhXivSueldoPeriodoTO.getXivHasta()) + "¬"
                        + (rhXivSueldoPeriodoTO.getXivFechaMaximaPago() == null ? "B" : "S" + rhXivSueldoPeriodoTO.getXivFechaMaximaPago())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteXiiiSueldoConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhFunXiiiSueldoConsultarTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte 13º sueldo listado");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCategoría" + "¬" + "SSector" + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SApellidos" + "¬"
                    + "SGénero" + "¬" + "SFecha Ingreso" + "¬" + "SCódigo de cargo IESS" + "¬" + "SCargo" + "¬" + "STotal Ingreso" + "¬"
                    + "SDías Laborados" + "¬" + "SValor XIII Sueldo" + "¬" + "SForma Pago" + "¬" + "SCódigo Ministerial" + "¬" + "SPeriodo"
                    + "¬" + "STipo" + "¬" + "SNúmero");

            for (RhFunXiiiSueldoConsultarTO rhFunXiiiSueldoConsultarTO : listado) {
                listaCuerpo.add((rhFunXiiiSueldoConsultarTO.getXiiiCategoria() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiCategoria())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiSector() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiSector())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiId() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiId())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiNombres() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiNombres())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiApellidos() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiApellidos())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiGenero() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiGenero())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiFechaIngreso() == null ? "B"
                        : "T" + rhFunXiiiSueldoConsultarTO.getXiiiFechaIngreso())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiCargoIess() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiCargoIess())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiCargo() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiCargo())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiTotalIngresos() == null ? "F"
                        : "F" + rhFunXiiiSueldoConsultarTO.getXiiiTotalIngresos())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiDiasLaborados() == null ? "B"
                        : "I" + rhFunXiiiSueldoConsultarTO.getXiiiDiasLaborados())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiValorXiiiSueldo() == null ? "F"
                        : "F" + rhFunXiiiSueldoConsultarTO.getXiiiValorXiiiSueldo())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiFormaPago() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiFormaPago().toString())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiCodigoMinisterial() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiCodigoMinisterial())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiPeriodo() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiPeriodo())
                        + "¬"
                        + (rhFunXiiiSueldoConsultarTO.getXiiiTipo() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiTipo())
                        + "¬" + (rhFunXiiiSueldoConsultarTO.getXiiiNumero() == null ? "B"
                        : "S" + rhFunXiiiSueldoConsultarTO.getXiiiNumero()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSaldoConsolidadoSueldosPorPagar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta, List<RhListaSaldoConsolidadoSueldosPorPagarTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte saldos consolidados de sueldos por pagar");
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCat." + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SValor" + "¬" + "SOrden");
            for (RhListaSaldoConsolidadoSueldosPorPagarTO rhListaSaldoConsolidadoSueldosPorPagarTO : listado) {
                listaCuerpo.add((rhListaSaldoConsolidadoSueldosPorPagarTO.getScspCategoria() == null ? "B"
                        : "S" + rhListaSaldoConsolidadoSueldosPorPagarTO.getScspCategoria())
                        + "¬"
                        + (rhListaSaldoConsolidadoSueldosPorPagarTO.getScspId() == null ? "B"
                        : "S" + rhListaSaldoConsolidadoSueldosPorPagarTO.getScspId())
                        + "¬"
                        + (rhListaSaldoConsolidadoSueldosPorPagarTO.getScspNombres() == null ? "B"
                        : "S" + rhListaSaldoConsolidadoSueldosPorPagarTO.getScspNombres())
                        + "¬"
                        + (rhListaSaldoConsolidadoSueldosPorPagarTO.getScspValor() == null ? "B"
                        : "D" + rhListaSaldoConsolidadoSueldosPorPagarTO.getScspValor())
                        + "¬" + (rhListaSaldoConsolidadoSueldosPorPagarTO.getScspOrden() == null ? "B"
                        : "S" + rhListaSaldoConsolidadoSueldosPorPagarTO.getScspOrden()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteListadoRolesPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String empCategoria, String empSector, List<RhListaDetalleRolesTO> listado, String filtro) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Consolidado de Roles");
            boolean hayFiltro = filtro != null && filtro.equals("LIQ");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            if (empCategoria != "") {
                listaCabecera.add("SCategoria: " + empCategoria);
            }
            if (empSector != "") {
                listaCabecera.add("SSector: " + empSector);
            }
            listaCabecera.add("S");
            listaCuerpo.add("SN. Identificación" + "¬" + "SNombres" + "¬" + "SCargo" + "¬" + "SDesde" + "¬" + "SHasta" + "¬" + "SDl"
                    + "¬" + "SDf" + "¬" + "SDe" + "¬" + "SDd" + "¬" + "SDpm" + "¬" + "SDvac" + "¬" + "SDp" + "¬" + "SSaldo" + "¬" + "SIngresos" + "¬"
                    + "SHoras Extras 50%" + "¬" + "SHoras Extras 100%" + "¬" + "SHoras Extras Extraordinarias 100%" + "¬" + "SBonos" + "¬" + "SBonos ND" + "¬" + "SBonos Fijo" + "¬"
                    + "SBonos Fijo ND"
                    + (hayFiltro ? ("¬" + "SLiquidación Bonificacion") : "")
                    + (hayFiltro ? ("¬" + "SLiquidación Desahucio") : "")
                    + (hayFiltro ? ("¬" + "SLiquidación Desahucio Intempestivo") : "")
                    + (hayFiltro ? ("¬" + "SLiquidación Salario Digno") : "")
                    + (hayFiltro ? ("¬" + "SLiquidación Vacaciones") : "")
                    + (hayFiltro ? ("¬" + "SLiquidación Xiii") : "")
                    + (hayFiltro ? ("¬" + "SLiquidación Xiv") : "")
                    + (hayFiltro ? ("¬" + "SLiquidación") : "")
                    + "¬" + "SOtros Ingresos" + "¬" + "SOtros Ingresos ND" + "¬" + "SXIII Sueldo" + "¬" + "SXIV Sueldo" + "¬" + "SFondo Reserva" + "¬" + "STotal Ingresos" + "¬"
                    + "SAnticipos" + "¬" + "SPréstamos" + "¬" + "SIess" + "¬" + "SIess extensión" + "¬" + "SFondo de reserva" + "¬" + "SPréstamo Quirografario" + "¬"
                    + "SPréstamo Hipotecario" + "¬" + "SRetención" + "¬" + "SDcto permiso médico" + "¬" + "SDcto vacaciones" + "¬" + "SDescuentos" + "¬"
                    + "STotal" + "¬" + "SForma Pago" + "¬" + "SDocumento" + "¬" + "SContable");
            for (RhListaDetalleRolesTO rhListaDetalleRolesTO : listado) {
                BigDecimal horas50 = rhListaDetalleRolesTO.getLrpHorasExtras() != null
                        ? rhListaDetalleRolesTO.getLrpHorasExtras().add(
                                rhListaDetalleRolesTO.getLrpSaldoHorasExtras50() != null ? rhListaDetalleRolesTO.getLrpSaldoHorasExtras50() : BigDecimal.ZERO
                        ) : rhListaDetalleRolesTO.getLrpSaldoHorasExtras50();
                BigDecimal horas100 = rhListaDetalleRolesTO.getLrpHorasExtras100() != null
                        ? rhListaDetalleRolesTO.getLrpHorasExtras100().add(
                                rhListaDetalleRolesTO.getLrpSaldoHorasExtras100() != null ? rhListaDetalleRolesTO.getLrpSaldoHorasExtras100() : BigDecimal.ZERO
                        ) : rhListaDetalleRolesTO.getLrpSaldoHorasExtras100();
                BigDecimal horasExtraordinarias100 = rhListaDetalleRolesTO.getLrpHorasExtrasExtraordinarias100() != null
                        ? rhListaDetalleRolesTO.getLrpHorasExtrasExtraordinarias100().add(
                                rhListaDetalleRolesTO.getLrpSaldoHorasExtrasExtraordinarias100() != null ? rhListaDetalleRolesTO.getLrpSaldoHorasExtrasExtraordinarias100() : BigDecimal.ZERO
                        ) : rhListaDetalleRolesTO.getLrpSaldoHorasExtrasExtraordinarias100();
                listaCuerpo.add((rhListaDetalleRolesTO.getLrpId() == null ? "B"
                        : "S" + rhListaDetalleRolesTO.getLrpId())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpNombres() == null ? "B"
                        : "S" + rhListaDetalleRolesTO.getLrpNombres())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpCargo() == null ? "B"
                        : "S" + rhListaDetalleRolesTO.getLrpCargo())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDesde() == null ? "B"
                        : "T" + rhListaDetalleRolesTO.getLrpDesde())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpHasta() == null ? "B"
                        : "T" + rhListaDetalleRolesTO.getLrpHasta())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDl() == null ? "B"
                        : "I" + rhListaDetalleRolesTO.getLrpDl().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDf() == null ? "B"
                        : "I" + rhListaDetalleRolesTO.getLrpDf().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDe() == null ? "B"
                        : "I" + rhListaDetalleRolesTO.getLrpDe().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDd() == null ? "B"
                        : "I" + rhListaDetalleRolesTO.getLrpDd().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDpm() == null ? "B"
                        : "I" + rhListaDetalleRolesTO.getLrpDpm().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDvac() == null ? "B"
                        : "I" + rhListaDetalleRolesTO.getLrpDvac().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDp() == null ? "B"
                        : "I" + rhListaDetalleRolesTO.getLrpDp().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpSaldo() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpSaldo().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpIngresos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpIngresos().toString())
                        + "¬"
                        + (horas50 == null ? "B" : "D" + horas50.toString())
                        + "¬"
                        + (horas100 == null ? "B" : "D" + horas100.toString())
                        + "¬"
                        + (horasExtraordinarias100 == null ? "B" : "D" + horasExtraordinarias100.toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpBonos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpBonos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpBonosnd() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpBonosnd().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpBonosFijo() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpBonosFijo().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpBonosFijoNd() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpBonosFijoNd().toString())
                        + (hayFiltro ? ((rhListaDetalleRolesTO.getLrpLiquidacionBonificacion() == null ? "B" : "D" + rhListaDetalleRolesTO.getLrpLiquidacionBonificacion().toString()) + "¬") : "")
                        + (hayFiltro ? ((rhListaDetalleRolesTO.getLrpLiquidacionDesahucio() == null ? "B" : "D" + rhListaDetalleRolesTO.getLrpLiquidacionDesahucio().toString()) + "¬") : "")
                        + (hayFiltro ? ((rhListaDetalleRolesTO.getLrpLiquidacionDesahucioIntempestivo() == null ? "B" : "D" + rhListaDetalleRolesTO.getLrpLiquidacionDesahucioIntempestivo().toString()) + "¬") : "")
                        + (hayFiltro ? ((rhListaDetalleRolesTO.getLrpLiquidacionSalarioDigno() == null ? "B" : "D" + rhListaDetalleRolesTO.getLrpLiquidacionSalarioDigno().toString()) + "¬") : "")
                        + (hayFiltro ? ((rhListaDetalleRolesTO.getLrpLiquidacionVacaciones() == null ? "B" : "D" + rhListaDetalleRolesTO.getLrpLiquidacionVacaciones().toString()) + "¬") : "")
                        + (hayFiltro ? ((rhListaDetalleRolesTO.getLrpLiquidacionXiii() == null ? "B" : "D" + rhListaDetalleRolesTO.getLrpLiquidacionXiii().toString()) + "¬") : "")
                        + (hayFiltro ? ((rhListaDetalleRolesTO.getLrpLiquidacionXiv() == null ? "B" : "D" + rhListaDetalleRolesTO.getLrpLiquidacionXiv().toString()) + "¬") : "")
                        + (hayFiltro ? ((rhListaDetalleRolesTO.getLrpLiquidacion() == null ? "B" : "D" + rhListaDetalleRolesTO.getLrpLiquidacion().toString()) + "¬") : "")
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpOtrosIngresos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpOtrosIngresos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpOtrosIngresosNd() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpOtrosIngresosNd().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpXiiiSueldo() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpXiiiSueldo().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpXivSueldo() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpXivSueldo().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpFondoReserva() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpFondoReserva().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpTotalIngresos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpTotalIngresos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpAnticipos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpAnticipos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpPrestamos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpPrestamos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpIess() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpIess().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpIessExtension() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpIessExtension().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpAcumulaFondoReserva() ? "B"
                        : (rhListaDetalleRolesTO.getLrpFondoReserva() == null || rhListaDetalleRolesTO.getLrpFondoReserva().compareTo(BigDecimal.ZERO) == 0) ? "B" : "D" + rhListaDetalleRolesTO.getLrpFondoReserva().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpPrestamoQuirografario() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpPrestamoQuirografario().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpPrestamoHipotecario() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpPrestamoHipotecario().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpRetencion() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpRetencion().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDescuentoPermisoMedico() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpDescuentoPermisoMedico().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDescuentoVacaciones() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpDescuentoVacaciones().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDescuentos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpDescuentos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpTotal() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getLrpTotal().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpFormaPago() == null ? "B"
                        : "S" + rhListaDetalleRolesTO.getLrpFormaPago())
                        + "¬"
                        + (rhListaDetalleRolesTO.getLrpDocumento() == null ? "B"
                        : "S" + rhListaDetalleRolesTO.getLrpDocumento())
                        + "¬" + (rhListaDetalleRolesTO.getLrpContable() == null ? "B"
                        : "S" + rhListaDetalleRolesTO.getLrpContable()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteProvisionesComprobanteContable(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String periodo, String numero, String tipo, List<RhListaProvisionesTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SSoporte de comprobante contable de provisiones");
            listaCabecera.add("SPeriodo: " + periodo + " Numero: " + numero + " Tipo: " + tipo);
            listaCabecera.add("S");
            listaCuerpo.add("SCategoría" + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SSueldo" + "¬" + "SDías Pagados"
                    + "¬" + "SAporte Individual" + "¬" + "SAporte Patronal" + "¬" + "SIece" + "¬" + "SSecap" + "¬"
                    + "SXiii" + "¬" + "SXiv" + "¬" + "SFR" + "¬" + "SVacaciones" + "¬" + "SContable Rol" + "¬"
                    + "SContable Provisión");
            for (RhListaProvisionesTO rhListaProvisionesTO : listado) {
                listaCuerpo.add((rhListaProvisionesTO.getProvCategoria() == null ? "B"
                        : "S" + rhListaProvisionesTO.getProvCategoria())
                        + "¬"
                        + (rhListaProvisionesTO.getProvId() == null ? "B"
                        : "S" + rhListaProvisionesTO.getProvId())
                        + "¬"
                        + (rhListaProvisionesTO.getProvNombres() == null ? "B"
                        : "S" + rhListaProvisionesTO.getProvNombres())
                        + "¬"
                        + (rhListaProvisionesTO.getProvSueldo() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvSueldo().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvDiasPagados() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvDiasPagados().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvAporteIndividual() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvAporteIndividual().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvAportePatronal() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvAportePatronal().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvIece() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvIece().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvSecap() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvSecap().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvXiii() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvXiii().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvXiv() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvXiv().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvFondoReserva() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvFondoReserva().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvVacaciones() == null ? "B"
                        : "D" + rhListaProvisionesTO.getProvVacaciones().toString())
                        + "¬"
                        + (rhListaProvisionesTO.getProvContableRol() == null ? "B"
                        : "S" + rhListaProvisionesTO.getProvContableRol())
                        + "¬" + (rhListaProvisionesTO.getProvContableProvision() == null ? "B"
                        : "S" + rhListaProvisionesTO.getProvContableProvision()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsolidadoIngresosTabulado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, String sector, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SConsolidado de Ingresos Tabulados");
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("SDesde: " + desde + " Hasta: " + hasta);
            listaCabecera.add("S");
            listaCabecera.add("S");
            String nombresColumnas = "";
            for (int i = 0; i < columnas.size(); i++) {
                nombresColumnas = nombresColumnas + "S" + columnas.get(i) + "¬";
            }
            listaCuerpo.add(nombresColumnas);
            String dato = "";
            for (int i = 0; i < datos.length; i++) {
                dato = "S" + datos[i][0] + "¬" + "S" + datos[i][1] + "¬";
                for (int j = 2; j < columnas.size(); j++) {
                    if (j <= 3) {
                        dato += comprobarNumericoString(datos[i][j] != null ? datos[i][j].toString() : "");
                    } else {
                        if (datos[i][j] != null) {
                            dato += "D" + datos[i][j] + "¬";
                        } else {
                            dato += " " + "¬";
                        }
                    }
                }
                listaCuerpo.add(dato);
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsolidadoRoles(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, String categoria, String empleado, List<RhListaConsolidadoRolesTO> listado, boolean excluirLiquidacion) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte consolidado de roles");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("SEmpleado: " + empleado);
            listaCabecera.add("SCategoria: " + categoria);
            listaCabecera.add("S");
            listaCuerpo.add("SCat." + "¬" + "SId" + "¬" + "SNombres" + "¬" + "SDl" + "¬" + "SDf" + "¬" + "SDe" + "¬" + "SDpm" + "¬" + "SDvac" + "¬" + "SDd"
                    + "¬" + "SDp" + "¬" + "SSueldo" + "¬" + "SHoras Extras" + "¬" + "SHoras Extras 100" + "¬" + "SHoras Extras Extraordinarias 100" + "¬" + "SBonos" + "¬" + "SBonos ND" + "¬" + "SBono Fijo" + "¬"
                    + "SBono Fijo ND" + "¬" + "SOtros Ingresos" + "¬" + "SOtros Ingresos ND" + "¬" + "SSubtotal Bonos" + "¬"
                    + "SSubtotal Ingresos" + "¬" + "SFondo Reserva" + (excluirLiquidacion ? "" : "¬" + "SLiquidación") + "¬" + "SIngresos" + "¬"
                    + "SAnticipos" + "¬" + "SPrestamos" + "¬" + "SIess" + "¬" + "SIess Ext." + "¬" + "SP. Quirografario" + "¬" + "SP. Hipotecario" + "¬" + "SRetención" + "¬"
                    + "SPermiso Medico" + "¬" + "SDcto vacaciones" + "¬" + "SDesc. Fondo Reserva"
                    + "¬" + "SDesc." + "¬" + "STotal");
            for (RhListaConsolidadoRolesTO rhListaConsolidadoRolesTO : listado) {
                BigDecimal horas50 = rhListaConsolidadoRolesTO.getCrpHorasExtras() != null
                        ? rhListaConsolidadoRolesTO.getCrpHorasExtras().add(
                                rhListaConsolidadoRolesTO.getCrpSaldoHorasExtras50() != null ? rhListaConsolidadoRolesTO.getCrpSaldoHorasExtras50() : BigDecimal.ZERO
                        ) : rhListaConsolidadoRolesTO.getCrpSaldoHorasExtras50();
                BigDecimal horas100 = rhListaConsolidadoRolesTO.getCrpHorasExtras100() != null
                        ? rhListaConsolidadoRolesTO.getCrpHorasExtras100().add(
                                rhListaConsolidadoRolesTO.getCrpSaldoHorasExtras100() != null ? rhListaConsolidadoRolesTO.getCrpSaldoHorasExtras100() : BigDecimal.ZERO
                        ) : rhListaConsolidadoRolesTO.getCrpSaldoHorasExtras100();
                BigDecimal horasExtraordinarias100 = rhListaConsolidadoRolesTO.getCrpHorasExtrasExtraordinarias100() != null
                        ? rhListaConsolidadoRolesTO.getCrpHorasExtrasExtraordinarias100().add(
                                rhListaConsolidadoRolesTO.getCrpSaldoHorasExtrasExtraordinarias100() != null ? rhListaConsolidadoRolesTO.getCrpSaldoHorasExtrasExtraordinarias100() : BigDecimal.ZERO
                        ) : rhListaConsolidadoRolesTO.getCrpSaldoHorasExtrasExtraordinarias100();
                listaCuerpo.add((rhListaConsolidadoRolesTO.getCrpCategoria() == null ? "B"
                        : "S" + rhListaConsolidadoRolesTO.getCrpCategoria())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpId() == null ? "B"
                        : "S" + rhListaConsolidadoRolesTO.getCrpId())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpNombres() == null ? "B"
                        : "S" + rhListaConsolidadoRolesTO.getCrpNombres())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpDl() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpDl().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpDf() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpDf().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpDe() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpDe().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpDpm() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpDpm().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpDvac() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpDvac().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpDd() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpDd().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpDp() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpDp().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpSueldo() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpSueldo().toString())
                        + "¬"
                        + (horas50 == null ? "B" : "D" + horas50.toString())
                        + "¬"
                        + (horas100 == null ? "B" : "D" + horas100.toString())
                        + "¬"
                        + (horasExtraordinarias100 == null ? "B" : "D" + horasExtraordinarias100.toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpBonos() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpBonos().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpBonosnd() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpBonosnd().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpBonoFijo() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpBonoFijo().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpBonoFijoNd() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpBonoFijoNd().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpOtrosIngresos() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpOtrosIngresos().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpOtrosIngresosNd() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpOtrosIngresosNd().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpSubtotalBonos() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpSubtotalBonos().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpSubtotalIngresos() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpSubtotalIngresos().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpFondoReserva() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpFondoReserva().toString())
                        + (excluirLiquidacion ? "" : "¬"
                                + (rhListaConsolidadoRolesTO.getCrpLiquidacion() == null ? "B"
                                : "D" + rhListaConsolidadoRolesTO.getCrpLiquidacion().toString()))
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpIngresos() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpIngresos().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpAnticipos() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpAnticipos().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpPrestamos() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpPrestamos().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpIess() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpIess().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpIessExtension() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpIessExtension().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpPrestamoQuirografario() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpPrestamoQuirografario().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpPrestamoHipotecario() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpPrestamoHipotecario().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpRetencion() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpRetencion().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpPermisoMedico() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpPermisoMedico().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpVacaciones() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpVacaciones().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpDescuentosFondoReserva() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpDescuentosFondoReserva().toString())
                        + "¬"
                        + (rhListaConsolidadoRolesTO.getCrpDescuentos() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpDescuentos().toString())
                        + "¬" + (rhListaConsolidadoRolesTO.getCrpTotal() == null ? "B"
                        : "D" + rhListaConsolidadoRolesTO.getCrpTotal().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Map<String, Object> exportarReporteSaldoConsolidadoAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String hasta, List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte saldos consolidados de anticipos y préstamos");
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCat." + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SAnticipos" + "¬" + "SPréstamos" + "¬"
                    + "STotal" + "¬" + "SOrden");
            for (RhListaSaldoConsolidadoAnticiposPrestamosTO rhListaSaldoConsolidadoAnticiposPrestamosTO : listado) {
                listaCuerpo
                        .add((rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapCategoria() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapCategoria())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapId() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapId())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapNombres() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapNombres())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapAnticipos() == null ? "B"
                                : "D" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapAnticipos()
                                        .toString())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapPrestamos() == null ? "B"
                                : "D" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapPrestamos()
                                        .toString())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapTotal() == null ? "B"
                                : "D" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapTotal().toString())
                                + "¬" + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapOrden() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapOrden()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSaldoConsolidadoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String hasta, List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte saldos consolidados de anticipos");
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCat." + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SAnticipos");
            for (RhListaSaldoConsolidadoAnticiposPrestamosTO rhListaSaldoConsolidadoAnticiposPrestamosTO : listado) {
                listaCuerpo
                        .add((rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapCategoria() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapCategoria())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapId() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapId())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapNombres() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapNombres())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapAnticipos() == null ? "B"
                                : "D" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapAnticipos()
                                        .toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSaldoConsolidadoPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String hasta, List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte saldos consolidados de préstamos");
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCat." + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SPréstamos");
            for (RhListaSaldoConsolidadoAnticiposPrestamosTO rhListaSaldoConsolidadoAnticiposPrestamosTO : listado) {
                listaCuerpo
                        .add((rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapCategoria() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapCategoria())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapId() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapId())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapNombres() == null ? "B"
                                : "S" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapNombres())
                                + "¬"
                                + (rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapPrestamos() == null ? "B"
                                : "D" + rhListaSaldoConsolidadoAnticiposPrestamosTO.getScapPrestamos()
                                        .toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSaldosIndividualAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, String tituloReporte, List<RhListaSaldoIndividualAnticiposPrestamosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("S" + tituloReporte);
            listaCabecera.add("SDesde: " + desde + "," + " Hasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("STipo" + "¬" + "SFecha" + "¬" + "SObservaciones" + "¬" + "SDébitos" + "¬" + "SCréditos" + "¬" + "SSaldo" + "¬" + "SContable");
            for (RhListaSaldoIndividualAnticiposPrestamosTO rhListaSaldoIndividualAnticiposPrestamosTO : listado) {
                listaCuerpo.add((rhListaSaldoIndividualAnticiposPrestamosTO.getSiapTipo() == null ? "B"
                        : "S" + rhListaSaldoIndividualAnticiposPrestamosTO.getSiapTipo())
                        + "¬"
                        + (rhListaSaldoIndividualAnticiposPrestamosTO.getSiapFecha() == null ? "B"
                        : "T" + rhListaSaldoIndividualAnticiposPrestamosTO.getSiapFecha())
                        + "¬"
                        + (rhListaSaldoIndividualAnticiposPrestamosTO.getSiapObservaciones() == null ? "B"
                        : "S" + rhListaSaldoIndividualAnticiposPrestamosTO.getSiapObservaciones())
                        + "¬"
                        + (rhListaSaldoIndividualAnticiposPrestamosTO.getSiapDebitos() == null ? "B"
                        : "D" + rhListaSaldoIndividualAnticiposPrestamosTO.getSiapDebitos())
                        + "¬"
                        + (rhListaSaldoIndividualAnticiposPrestamosTO.getSiapCreditos() == null ? "B"
                        : "D" + rhListaSaldoIndividualAnticiposPrestamosTO.getSiapCreditos())
                        + "¬" + (rhListaSaldoIndividualAnticiposPrestamosTO.getSiapSaldo() == null ? "B"
                        : "D" + rhListaSaldoIndividualAnticiposPrestamosTO.getSiapSaldo().toString())
                        + "¬" + (rhListaSaldoIndividualAnticiposPrestamosTO.getSiapContable() == null ? "B"
                        : "S" + rhListaSaldoIndividualAnticiposPrestamosTO.getSiapContable()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarExcelDetalleVacacionesPagadasGozadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String tipo, String fechaDesde, String fechaHasta, List<RhDetalleVacionesPagadasGozadasTO> listadoDetalleVacionesPagadasGozadas) throws Exception {
        try {
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList<>();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Detalle de Vacaciones " + tipo);
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SN. Identificación" + "¬" + "SNombres" + "¬" + "SValor" + "¬" + "SDesde" + "¬" + "SHasta" + "¬"
                    + "SObservaciones" + "¬" + "SContable");
            for (RhDetalleVacionesPagadasGozadasTO ListaDetalleVacionesPagadasGozadasTO : listadoDetalleVacionesPagadasGozadas) {
                listaCuerpo
                        .add((ListaDetalleVacionesPagadasGozadasTO.getVacId() == null ? "B"
                                : "S" + ListaDetalleVacionesPagadasGozadasTO.getVacId())
                                + "¬"
                                + (ListaDetalleVacionesPagadasGozadasTO.getVacApellidosNombres() == null ? "B"
                                : "S" + ListaDetalleVacionesPagadasGozadasTO.getVacApellidosNombres())
                                + "¬"
                                + (ListaDetalleVacionesPagadasGozadasTO.getVacValor() == null ? "B"
                                : "D" + ListaDetalleVacionesPagadasGozadasTO.getVacValor().toString())
                                + "¬"
                                + (ListaDetalleVacionesPagadasGozadasTO.getVacDesde() == null ? "B"
                                : "T" + ListaDetalleVacionesPagadasGozadasTO.getVacDesde())
                                + "¬"
                                + (ListaDetalleVacionesPagadasGozadasTO.getVacHasta() == null ? "B"
                                : "T" + ListaDetalleVacionesPagadasGozadasTO.getVacHasta())
                                + "¬"
                                + (ListaDetalleVacionesPagadasGozadasTO.getVacObservaciones() == null ? "B"
                                : "S" + ListaDetalleVacionesPagadasGozadasTO.getVacObservaciones())
                                + "¬" + (ListaDetalleVacionesPagadasGozadasTO.getVacContables() == null ? "B"
                                : "S" + ListaDetalleVacionesPagadasGozadasTO.getVacContables()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteProvisionPorFechas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String titulo, List<String> columnas, List<String[]> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            if (sector != null && !sector.equals("")) {
                listaCabecera.add("SSector: " + sector);
            }
            listaCabecera.add("S" + titulo);
            listaCabecera.add("S");
            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        cuerpo += comprobarNumericoString(datos.get(i)[j] != null ? datos.get(i)[j] : "");
                    } else {
                        cuerpo += comprobarNumericoString(datos.get(i)[j] != null ? datos.get(i)[j] : "");
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    private String generarColumnas(List<String> lista) {
        String columnas = "";
        for (int i = 0; i < lista.size(); i++) {
            if (i < lista.size() - 1) {
                columnas += "S" + lista.get(i) + "¬";
            } else {
                columnas += "S" + lista.get(i);
            }
        }
        return columnas;
    }

    private boolean buscarPuntoEnString(String dato) {
        boolean encontro = false;
        for (int i = 0; i < dato.length(); i++) {
            if (dato.charAt(i) == '.') {
                encontro = true;
                i = dato.length();
            }
        }
        return encontro;
    }

    private String comprobarNumericoString(String dato) {
        BigDecimal valor;
        try {
            if (buscarPuntoEnString(dato)) {
                valor = new BigDecimal(dato);
                if (valor != null) {
                    return "D" + dato + "¬";
                } else {
                    return "S" + dato + "¬";
                }
            } else {
                return "S" + dato + "¬";
            }
        } catch (Exception e) {
            return "S" + dato + "¬";
        }
    }

    @Override
    public byte[] generarReporteConsolidadoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhListaConsolidadoHorasExtrasTO> listaConsolidadoBonosViaticosTO) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("fechaDesde", fechaDesde);
        parametros.put("fechaHasta", fechaHasta);
        return genericReporteService.generarReporte(modulo, "reportConsolidadoHorasExtras.jrxml", usuarioEmpresaReporteTO, parametros, listaConsolidadoBonosViaticosTO);
    }

    @Override
    public Map<String, Object> exportarRhAnticipoMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhAnticipoMotivo> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo de anticipo");
            listaCabecera.add("S");
            listaCuerpo.add("SDescripción" + "¬" + "STipo contable" + "¬" + "SEstado");
            for (RhAnticipoMotivo motivoAnticipo : listado) {
                listaCuerpo.add(
                        (motivoAnticipo.getRhAnticipoMotivoPK().getMotDetalle() == null ? "B" : "S" + motivoAnticipo.getRhAnticipoMotivoPK().getMotDetalle()) + "¬"
                        + (motivoAnticipo.getConTipo().getTipDetalle() == null ? "B" : "S" + motivoAnticipo.getConTipo().getTipDetalle()) + "¬"
                        + (motivoAnticipo.getMotInactivo() ? "SINACTIVO" : "S" + "ACTIVO") + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteXivSueldoConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhFunXivSueldoConsultarTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte 14º sueldo listado");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCategoría" + "¬" + "SSector" + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SApellidos" + "¬"
                    + "SGénero" + "¬" + "SFecha Ingreso" + "¬" + "SCargo" + "¬" + "STotal Ingreso" + "¬"
                    + "SDías Laborados" + "¬" + "SValor XIV Sueldo" + "¬" + "SCódigo Ministerial" + "¬" + "SPeriodo"
                    + "¬" + "STipo" + "¬" + "SNúmero");
            for (RhFunXivSueldoConsultarTO rhFunXivSueldoConsultarTO : listado) {
                listaCuerpo.add((rhFunXivSueldoConsultarTO.getXivCategoria() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivCategoria())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivSector() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivSector())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivId() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivId())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivNombres() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivNombres())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivApellidos() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivApellidos())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivGenero() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivGenero())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivFechaIngreso() == null ? "B"
                        : "T" + rhFunXivSueldoConsultarTO.getXivFechaIngreso())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivCargo() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivCargo())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivTotalIngresos() == null ? "F"
                        : "F" + rhFunXivSueldoConsultarTO.getXivTotalIngresos())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivDiasLaborados() == null ? "B"
                        : "I" + rhFunXivSueldoConsultarTO.getXivDiasLaborados())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivValorXivSueldo() == null ? "F"
                        : "F" + rhFunXivSueldoConsultarTO.getXivValorXivSueldo())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivCodigoMinisterial() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivCodigoMinisterial())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivPeriodo() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivPeriodo())
                        + "¬"
                        + (rhFunXivSueldoConsultarTO.getXivTipo() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivTipo())
                        + "¬" + (rhFunXivSueldoConsultarTO.getXivNumero() == null ? "B"
                        : "S" + rhFunXivSueldoConsultarTO.getXivNumero()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCalcularUtilidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhFunUtilidadesCalcularTO> listado) throws Exception {
        try {
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            List<String> listaCabecera = new ArrayList<String>();
            List<String> listaCuerpo = new ArrayList<String>();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Utilidades PreCálculo");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SN. Identificación" + "¬" + "SNombres" + "¬" + "SApellidos" + "¬" + "SGénero" + "¬" + "SFecha Ingreso"
                    + "¬" + "SFecha Salida" + "¬" + "SCargo" + "¬" + "SCargas Familiares" + "¬" + "SDías Laborados"
                    + "¬" + "SValor Utilidades" + "¬" + "SValor Sueldos" + "¬" + "SValor Bonos" + "¬" + "SValor Xiii"
                    + "¬" + "SValor Xiv" + "¬" + "SValor Fondo Reserva" + "¬" + "SValor Salario Digno" + "¬"
                    + "SValor Utilidad Anterior" + "¬" + "SValor Impuesto");
            for (RhFunUtilidadesCalcularTO rhFunUtilidadesCalcularTO : listado) {
                listaCuerpo.add((rhFunUtilidadesCalcularTO.getUtiId() == null ? "B"
                        : "S" + rhFunUtilidadesCalcularTO.getUtiId())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiNombres() == null ? "B"
                        : "S" + rhFunUtilidadesCalcularTO.getUtiNombres())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiApellidos() == null ? "B"
                        : "S" + rhFunUtilidadesCalcularTO.getUtiApellidos())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiGenero() == null ? "B"
                        : "S" + rhFunUtilidadesCalcularTO.getUtiGenero().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiFechaIngreso() == null ? "B"
                        : "T" + rhFunUtilidadesCalcularTO.getUtiFechaIngreso())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiFechaSalida() == null ? "B"
                        : "T" + rhFunUtilidadesCalcularTO.getUtiFechaSalida())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiCargo() == null ? "B"
                        : "S" + rhFunUtilidadesCalcularTO.getUtiCargo())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiCargasFamiliares() == null ? "B"
                        : "S" + rhFunUtilidadesCalcularTO.getUtiCargasFamiliares().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiDiasLaborados() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiDiasLaborados().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiValorUtilidades() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiValorUtilidades().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiValorSueldos() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiValorSueldos().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiValorBonos() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiValorBonos().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiValorXiii() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiValorXiii().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiValorXiv() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiValorXiv().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiValorFondoReserva() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiValorFondoReserva().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiValorSalarioDigno() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiValorSalarioDigno().toString())
                        + "¬"
                        + (rhFunUtilidadesCalcularTO.getUtiValorUtilidadAnterior() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiValorUtilidadAnterior().toString())
                        + "¬" + (rhFunUtilidadesCalcularTO.getUtiValorImpuesto() == null ? "B"
                        : "D" + rhFunUtilidadesCalcularTO.getUtiValorImpuesto().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarRhListaFormaPagoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaFormaPagoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de formas de pago");
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSector" + "¬" + "SEstado");
            for (RhListaFormaPagoTO o : listado) {
                listaCuerpo.add(
                        (o.getCtaCodigo() == null ? "B" : "S" + o.getCtaCodigo()) + "¬"
                        + (o.getFpDetalle() == null ? "B" : "S" + o.getFpDetalle()) + "¬"
                        + (o.getSecCodigo() == null ? "B" : "S" + o.getSecCodigo()) + "¬"
                        + (o.getFpInactivo() ? "SINACTIVO" : "S" + "ACTIVO") + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarRhListaFormaPagoBeneficioSocialTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaFormaPagoBeneficioSocialTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de formas de pago beneficios sociales");
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SCodigo magisterial" + "¬" + "SSector" + "¬" + "SEstado");
            for (RhListaFormaPagoBeneficioSocialTO o : listado) {
                listaCuerpo.add(
                        (o.getCtaCodigo() == null ? "B" : "S" + o.getCtaCodigo()) + "¬"
                        + (o.getFpDetalle() == null ? "B" : "S" + o.getFpDetalle()) + "¬"
                        + (o.getFpCodigoMinisterial() == null ? "B" : "S" + o.getFpCodigoMinisterial()) + "¬"
                        + (o.getSecCodigo() == null ? "B" : "S" + o.getSecCodigo()) + "¬"
                        + (o.getFpInactivo() ? "SINACTIVO" : "S" + "ACTIVO") + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //ARCHIVO->EMPLEADO
    @Override
    public Map<String, Object> exportarRhEmpleado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhEmpleado> listado, List<DetalleExportarFiltrado> listadoFiltrado, boolean filtrarPorGrupo) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            BanBancoTO banco = new BanBancoTO();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de empleados");
            listaCabecera.add("S");

            String fila = "";
            String filaCabecera = "";
            for (DetalleExportarFiltrado cabecera : listadoFiltrado) {
                filaCabecera += cabecera.isCheck() ? "S" + cabecera.getHeaderName() + "¬" : "";
            }
            listaCuerpo.add(filaCabecera);
            for (RhEmpleado rhFunListadoEmpleadosTO : listado) {//recorre las filas 
                fila = "";
                for (int i = 0; i < listadoFiltrado.size(); i++) {//recorre las columnas
                    if (listadoFiltrado.get(i).isCheck()) { // esta con check
                        switch (listadoFiltrado.get(i).getField()) { //toma el campo 
                            case "empTipoId":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpTipoId() + "" == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpTipoId()) + "¬";
                                }
                                break;
                            case "empId":
                                fila += (rhFunListadoEmpleadosTO.getRhEmpleadoPK().getEmpId() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getRhEmpleadoPK().getEmpId()) + "¬";
                                break;
                            case "empNacionalidad":
                                if (!filtrarPorGrupo) {
                                    AnxPaisTO pais = null;
                                    String local = "";
                                    if (rhFunListadoEmpleadosTO.getEmpResidenciaPais() != null) {
                                        pais = paisDao.getAnxPaisTO(rhFunListadoEmpleadosTO.getEmpResidenciaPais());
                                        local = String.valueOf(pais);
                                    }
                                    if (pais != null) {
                                        if (local.equals("ECUADOR")) {
                                            pais.setPaisNombre("LOCAL");
                                        } else {
                                            pais.setPaisNombre("EXTRANJERO");
                                        }
                                    }
                                    fila += (pais == null ? "B" : "S" + pais) + "¬";
                                }
                                break;
                            case "empApellidos":
                                fila += (rhFunListadoEmpleadosTO.getEmpApellidos() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpApellidos()) + "¬";
                                break;
                            case "empNombres":
                                fila += (rhFunListadoEmpleadosTO.getEmpNombres() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpNombres()) + "¬";
                                break;
                            case "empGenero":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpGenero() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpGenero()) + "¬";
                                }
                                break;
                            case "empFechaNacimiento":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaNacimiento() == null ? "B" : "S" + UtilsDate.fechaFormatoString(rhFunListadoEmpleadosTO.getEmpFechaNacimiento(), "yyyy-MM-dd")) + "¬";
                                }
                                break;
                            case "empEstadoCivil":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpEstadoCivil() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpEstadoCivil()) + "¬";
                                }
                                break;
                            case "empCargasFamiliares":
                                if (!filtrarPorGrupo) {
                                    fila += ("S" + rhFunListadoEmpleadosTO.getEmpCargasFamiliares()) + "¬";
                                }
                                break;
                            case "empProvincia":
                                if (!filtrarPorGrupo) {
                                    AnxProvinciaCantonTO provincia = provinciasDao.obtenerProvincia(rhFunListadoEmpleadosTO.getEmpProvincia());
                                    fila += (provincia == null ? "B" : "S" + provincia.getNombre()) + "¬";
                                }
                                break;
                            case "empCanton":
                                if (!filtrarPorGrupo) {
                                    AnxProvinciaCantonTO canton = provinciasDao.obtenerCanton(rhFunListadoEmpleadosTO.getEmpProvincia(), rhFunListadoEmpleadosTO.getEmpCanton());
                                    fila += (canton == null ? "B" : "S" + canton.getNombre()) + "¬";
                                }
                                break;
                            case "empLugarNacimiento":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpLugarNacimiento() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpLugarNacimiento()) + "¬";
                                }
                                break;
                            case "empDomicilio":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpDomicilio() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpDomicilio()) + "¬";
                                }
                                break;
                            case "empNumero":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpNumero() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpNumero()) + "¬";
                                }
                                break;
                            case "empTelefono":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpTelefono() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpTelefono()) + "¬";
                                }
                                break;
                            case "secCodigo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getPrdSector().getPrdSectorPK().getSecCodigo() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getPrdSector().getPrdSectorPK().getSecCodigo()) + "¬";
                                }
                                break;
                            case "catNombre":
                                fila += (rhFunListadoEmpleadosTO.getRhCategoria() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getRhCategoria().getRhCategoriaPK().getCatNombre()) + "¬";
                                break;
                            case "empFechaPrimerIngreso":
                                fila += (rhFunListadoEmpleadosTO.getEmpFechaPrimerIngreso() == null ? "B" : "S" + UtilsDate.fechaFormatoString(rhFunListadoEmpleadosTO.getEmpFechaPrimerIngreso(), "yyyy-MM-dd")) + "¬";
                                break;
                            case "empFechaPrimeraSalida":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaPrimeraSalida() == null ? "B" : "S" + UtilsDate.fechaFormatoString(rhFunListadoEmpleadosTO.getEmpFechaPrimeraSalida(), "yyyy-MM-dd")) + "¬";
                                }
                                break;
                            case "empFechaUltimoIngreso":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaUltimoIngreso() == null ? "B" : "S" + UtilsDate.fechaFormatoString(rhFunListadoEmpleadosTO.getEmpFechaUltimoIngreso(), "yyyy-MM-dd")) + "¬";
                                }
                                break;
                            case "empFechaUltimaSalida":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaUltimaSalida() == null ? "B" : "S" + UtilsDate.fechaFormatoString(rhFunListadoEmpleadosTO.getEmpFechaUltimaSalida(), "yyyy-MM-dd")) + "¬";
                                }
                                break;
                            case "empFechaAfiliacionIess":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaAfiliacionIess() == null ? "B" : "S" + UtilsDate.fechaFormatoString(rhFunListadoEmpleadosTO.getEmpFechaAfiliacionIess(), "yyyy-MM-dd")) + "¬";
                                }
                                break;
                            case "empCodigoAfiliacionIess":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCodigoAfiliacionIess() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCodigoAfiliacionIess()) + "¬";
                                }
                                break;
                            case "empCodigoCargo":
                                fila += (rhFunListadoEmpleadosTO.getEmpCodigoCargo() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCodigoCargo()) + "¬";
                                break;
                            case "empCargo":
                                fila += (rhFunListadoEmpleadosTO.getEmpCargo() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCargo()) + "¬";
                                break;
                            case "empSueldoIess":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpSueldoIess() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpSueldoIess()) + "¬";
                                }
                                break;
                            case "empFormaPago":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFormaPago() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpFormaPago()) + "¬";
                                }
                                break;
                            case "empDiasTrabajados":
                                if (!filtrarPorGrupo) {
                                    fila += ("I" + rhFunListadoEmpleadosTO.getEmpDiasTrabajados()) + "¬";
                                }
                                break;
                            case "empDiasDescanso":
                                if (!filtrarPorGrupo) {
                                    fila += ("I" + rhFunListadoEmpleadosTO.getEmpDiasDescanso()) + "¬";
                                }
                                break;
                            case "empSueldoReal":
                                if (!filtrarPorGrupo) {
                                    fila += ("D" + rhFunListadoEmpleadosTO.getEmpSueldoIess()) + "¬";
                                }
                                break;
                            case "empBonoFijo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpBonoFijo() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpBonoFijo()) + "¬";
                                }
                                break;
                            case "empBonoFijoNd":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpBonoFijoNd() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpBonoFijoNd()) + "¬";
                                }
                                break;
                            case "empOtrosIngresos":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpOtrosIngresos() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpOtrosIngresos()) + "¬";
                                }
                                break;
                            case "empOtrosIngresosNd":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpOtrosIngresosNd() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpOtrosIngresosNd()) + "¬";
                                }
                                break;
                            case "empAcumulaFondoReserva":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCancelarFondoReservaMensualmente() == false ? "SSI" : "SNO") + "¬";
                                }
                                break;
                            case "empRetencion":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpRetencion() == false ? "BNO" : "SSI") + "¬";
                                }
                                break;
                            case "empEducacion":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpEducacion() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpOtrosIngresosNd()) + "¬";
                                }
                                break;
                            case "empAlimentacion":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpAlimentacion() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpAlimentacion()) + "¬";
                                }
                                break;
                            case "empSalud":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpSalud() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpOtrosIngresosNd()) + "¬";
                                }
                                break;
                            case "empVivienda":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpVivienda() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpVivienda()) + "¬";
                                }
                                break;
                            case "empVestuario":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpVestuario() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpVestuario()) + "¬";
                                }
                                break;
                            case "empSueldoOtraCompania":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpSueldoOtraCompania() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpSueldoOtraCompania()) + "¬";
                                }
                                break;
                            case "empUtilidades":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpUtilidades() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpUtilidades()) + "¬";
                                }
                                break;
                            case "empBanco":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpBanco() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpBanco()) + "¬";
                                }
                                break;
                            case "empBancoDescripcion":
                                if (!filtrarPorGrupo) {
                                    if (rhFunListadoEmpleadosTO.getEmpBanco() != null && !rhFunListadoEmpleadosTO.getEmpBanco().equals("")) {
                                        banco = bancoService.getBancoTO(rhFunListadoEmpleadosTO.getRhEmpleadoPK().getEmpEmpresa(), rhFunListadoEmpleadosTO.getEmpBanco());
                                    }
                                    fila += (banco == null ? "B" : "S" + banco.getBanNombre()) + "¬";
                                    banco = null;
                                }
                                break;
                            case "empCuentaNumero":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCuentaNumero() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCuentaNumero()) + "¬";
                                }
                                break;
                            case "empObservaciones":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpObservaciones() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpObservaciones()) + "¬";
                                }
                                break;
                            case "empInactivo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpInactivo() == false ? "SNO" : "SSI") + "¬";
                                }
                                break;
                            case "empAcumulaXiiiSueldo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCancelarXiiiSueldoMensualmente() == false ? "SSI" : "SNO") + "¬";
                                }
                                break;
                            case "empAcumulaXivSueldo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCancelarXivSueldoMensualmente() == false ? "SSI" : "SNO") + "¬";
                                }
                                break;
                            case "empCorreoElectronico":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCorreoElectronico() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCorreoElectronico()) + "¬";
                                }
                                break;
                            case "empTurismo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpTurismo() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpTurismo()) + "¬";
                                }
                                break;
                            case "empSalarioNeto":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpSalarioNeto() ? "SSI" : "SNO") + "¬";
                                }
                                break;
                            case "prdSector.prdSectorPK.secCodigo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getPrdSector().getPrdSectorPK().getSecCodigo() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getPrdSector().getPrdSectorPK().getSecCodigo()) + "¬";
                                }
                                break;
                            case "esTerceraEdad":
                                if (!filtrarPorGrupo) {
                                    if (rhFunListadoEmpleadosTO.getEmpInactivo() == false) {
                                        if (rhFunListadoEmpleadosTO.getEmpFechaNacimiento() != null) {
                                            String[] fecha = UtilsValidacion.fecha(rhFunListadoEmpleadosTO.getEmpFechaNacimiento(), "yyyy-MM-dd").split("-");
                                            Calendar fechaNacimiento = new GregorianCalendar(new Integer(fecha[0]), new Integer(fecha[1]), new Integer(fecha[2]));
                                            Calendar ahora = Calendar.getInstance();
                                            long edadEnDias = (ahora.getTimeInMillis() - fechaNacimiento.getTimeInMillis()) / 1000 / 60 / 60 / 24;
                                            int anos = Double.valueOf(edadEnDias / 365.25d).intValue();
                                            fila += (anos <= 65 ? "SNO" : "SSI") + "¬";
                                        } else {
                                            fila += "¬";
                                        }
                                    } else {
                                        fila += "¬";
                                    }
                                }
                                break;
                            case "empMaternidad":
                                if (!filtrarPorGrupo) {
                                    fila += (!rhFunListadoEmpleadosTO.isEmpMaternidad() ? "SNO" : "SSI") + "¬";
                                }
                                break;
                            case "empLactancia":
                                if (!filtrarPorGrupo) {
                                    fila += (!rhFunListadoEmpleadosTO.isEmpLactancia() ? "SNO" : "SSI") + "¬";
                                }
                                break;
                            default:
                                fila += "";
                                break;
                        }
                    }

                }
                listaCuerpo.add(fila);
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //CONSULTA->EMPLEADOS
    @Override
    public Map<String, Object> exportarRhFunListadoEmpleadosTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhFunListadoEmpleadosTO> listado, List<DetalleExportarFiltrado> listadoFiltrado, boolean filtrarPorGrupo) throws Exception {
        try {
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            List<String> listaCabecera = new ArrayList<String>();
            List<String> listaCuerpo = new ArrayList<String>();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Listado Empleados");
            listaCabecera.add("S");
            //variables para el filtro de exportar
            String fila = "";
            String filaCabecera = "";
            String[] bancoDatos = null;
            for (DetalleExportarFiltrado cabecera : listadoFiltrado) {
                filaCabecera += cabecera.isCheck() ? "S" + cabecera.getHeaderName() + "¬" : "";
            }
            listaCuerpo.add(filaCabecera);
            for (RhFunListadoEmpleadosTO rhFunListadoEmpleadosTO : listado) {//recorre las filas 
                fila = "";
                for (int i = 0; i < listadoFiltrado.size(); i++) {//recorre las columnas
                    if (listadoFiltrado.get(i).isCheck()) { // esta con check
                        switch (listadoFiltrado.get(i).getField()) { //toma el campo 
                            case "empTipoId":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpTipoId() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpTipoId()) + "¬";
                                }
                                break;
                            case "empId":
                                fila += (rhFunListadoEmpleadosTO.getEmpId() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpId()) + "¬";
                                break;
                            case "empNacionalidad":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpNacionalidad() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpNacionalidad()) + "¬";
                                }
                                break;
                            case "empApellidos":
                                fila += (rhFunListadoEmpleadosTO.getEmpApellidos() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpApellidos()) + "¬";
                                break;
                            case "empNombres":
                                fila += (rhFunListadoEmpleadosTO.getEmpNombres() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpNombres()) + "¬";
                                break;
                            case "empGenero":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpGenero() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpGenero()) + "¬";
                                }
                                break;
                            case "empFechaNacimiento":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaNacimiento() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpFechaNacimiento()) + "¬";
                                }
                                break;
                            case "empEstadoCivil":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpEstadoCivil() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpEstadoCivil()) + "¬";
                                }
                                break;
                            case "empCargasFamiliares":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCargasFamiliares() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCargasFamiliares()) + "¬";
                                }
                                break;
                            case "empProvincia":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpProvincia() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpProvincia()) + "¬";
                                }
                                break;
                            case "empCanton":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCanton() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCanton()) + "¬";
                                }
                                break;
                            case "empLugarNacimiento":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpLugarNacimiento() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpLugarNacimiento()) + "¬";
                                }

                                break;
                            case "empDomicilio":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpDomicilio() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpDomicilio()) + "¬";
                                }
                                break;
                            case "empNumero":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpNumero() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpNumero()) + "¬";
                                }
                                break;
                            case "empTelefono":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpTelefono() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpTelefono()) + "¬";
                                }
                                break;
                            case "secCodigo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getSecCodigo() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getSecCodigo()) + "¬";
                                }
                                break;
                            case "catNombre":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getCatNombre() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getCatNombre()) + "¬";
                                }
                                break;
                            case "empFechaPrimerIngreso":
                                fila += (rhFunListadoEmpleadosTO.getEmpFechaPrimerIngreso() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpFechaPrimerIngreso()) + "¬";
                                break;
                            case "empFechaPrimeraSalida":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaPrimeraSalida() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpFechaPrimeraSalida()) + "¬";
                                }
                                break;
                            case "empFechaUltimoIngreso":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaUltimoIngreso() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpFechaUltimoIngreso()) + "¬";
                                }
                                break;
                            case "empFechaUltimaSalida":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaUltimaSalida() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpFechaUltimaSalida()) + "¬";
                                }
                                break;
                            case "empFechaAfiliacionIess":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFechaAfiliacionIess() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpFechaAfiliacionIess()) + "¬";
                                }
                                break;
                            case "empSalarioNeto":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.isEmpSalarioNeto() ? "SSI" : "SNO") + "¬";
                                }
                                break;
                            case "empCodigoAfiliacionIess":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCodigoAfiliacionIess() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCodigoAfiliacionIess()) + "¬";
                                }
                                break;
                            case "empCodigoCargo":
                                fila += (rhFunListadoEmpleadosTO.getEmpCodigoCargo() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCodigoCargo()) + "¬";
                                break;
                            case "empCargo":
                                fila += (rhFunListadoEmpleadosTO.getEmpCargo() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCargo()) + "¬";
                                break;
                            case "empSueldoIess":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpSueldoIess() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpSueldoIess()) + "¬";
                                }
                                break;
                            case "empFormaPago":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpFormaPago() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpFormaPago()) + "¬";
                                }
                                break;
                            case "empDiasTrabajados":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpDiasTrabajados() == null ? "B" : "I" + rhFunListadoEmpleadosTO.getEmpDiasTrabajados()) + "¬";
                                }
                                break;
                            case "empDiasDescanso":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpDiasDescanso() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpDiasDescanso()) + "¬";
                                }
                                break;
                            case "empSueldoReal":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpSueldoReal() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpSueldoReal()) + "¬";
                                }
                                break;
                            case "empBonoFijo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpBonoFijo() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpBonoFijo()) + "¬";
                                }
                                break;
                            case "empBonoFijoNd":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpBonoFijoNd() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpBonoFijoNd()) + "¬";
                                }
                                break;
                            case "empOtrosIngresos":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpOtrosIngresos() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpOtrosIngresos()) + "¬";
                                }
                                break;
                            case "empOtrosIngresosNd":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpOtrosIngresosNd() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpOtrosIngresosNd()) + "¬";
                                }
                                break;
                            case "empAcumulaFondoReserva":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpAcumulaFondoReserva() == false ? "SSI" : "SNO") + "¬";
                                }
                                break;
                            case "empRetencion":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpRetencion() == false ? "BNO" : "SSI") + "¬";
                                }
                                break;
                            case "empEducacion":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpEducacion() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpOtrosIngresosNd()) + "¬";
                                }
                                break;
                            case "empAlimentacion":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpAlimentacion() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpAlimentacion()) + "¬";
                                }
                                break;
                            case "empSalud":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpSalud() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpOtrosIngresosNd()) + "¬";
                                }
                                break;
                            case "empVivienda":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpVivienda() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpVivienda()) + "¬";
                                }
                                break;
                            case "empVestuario":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpVestuario() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpVestuario()) + "¬";
                                }
                                break;
                            case "empSueldoOtraCompania":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpSueldoOtraCompania() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpSueldoOtraCompania()) + "¬";
                                }
                                break;
                            case "empUtilidades":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpUtilidades() == null ? "B" : "D" + rhFunListadoEmpleadosTO.getEmpUtilidades()) + "¬";
                                }
                                break;
                            case "empBanco":
                                if (!filtrarPorGrupo) {
                                    bancoDatos = rhFunListadoEmpleadosTO.getEmpBanco() != null ? rhFunListadoEmpleadosTO.getEmpBanco().split("\\|") : null;
                                    fila += (bancoDatos == null ? "B" : "S" + bancoDatos[0]) + "¬";
                                    bancoDatos = null;
                                }
                                break;
                            case "empBancoDescripcion":
                                if (!filtrarPorGrupo) {
                                    bancoDatos = rhFunListadoEmpleadosTO.getEmpBanco() != null ? rhFunListadoEmpleadosTO.getEmpBanco().split("\\|") : null;
                                    fila += (bancoDatos == null ? "B" : "S" + bancoDatos[1]) + "¬";
                                    bancoDatos = null;
                                }
                                break;
                            case "empCuentaNumero":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCuentaNumero() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCuentaNumero()) + "¬";
                                }
                                break;
                            case "empObservaciones":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpObservaciones() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpObservaciones()) + "¬";
                                }
                                break;
                            case "empInactivo":
                                if (!filtrarPorGrupo) {
                                    fila += (!rhFunListadoEmpleadosTO.getEmpInactivo() ? "SNO" : "SSI") + "¬";
                                }
                                break;
                            case "empAcumulaXiiiSueldo":
                                if (!filtrarPorGrupo) {
                                    fila += (!rhFunListadoEmpleadosTO.getEmpAcumulaXiiiSueldo() ? "SSI" : "SNO") + "¬";
                                }
                                break;
                            case "empAcumulaXivSueldo":
                                if (!filtrarPorGrupo) {
                                    fila += (!rhFunListadoEmpleadosTO.getEmpAcumulaXivSueldo() ? "SSI" : "SNO") + "¬";
                                }
                                break;
                            case "empCorreoElectronico":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpCorreoElectronico() == null ? "B" : "S" + rhFunListadoEmpleadosTO.getEmpCorreoElectronico()) + "¬";
                                }
                                break;
                            case "esTerceraEdad":
                                if (!filtrarPorGrupo) {
                                    if (rhFunListadoEmpleadosTO.getEmpInactivo() == false) {
                                        if (rhFunListadoEmpleadosTO.getEmpFechaNacimiento() != null) {
                                            String[] fecha = rhFunListadoEmpleadosTO.getEmpFechaNacimiento().split("-");
                                            Calendar fechaNacimiento = new GregorianCalendar(new Integer(fecha[0]), new Integer(fecha[1]), new Integer(fecha[2]));
                                            Calendar ahora = Calendar.getInstance();
                                            long edadEnDias = (ahora.getTimeInMillis() - fechaNacimiento.getTimeInMillis()) / 1000 / 60 / 60 / 24;
                                            int anos = Double.valueOf(edadEnDias / 365.25d).intValue();
                                            fila += (anos <= 65 ? "SNO" : "SSI") + "¬";
                                        } else {
                                            fila += "¬";
                                        }
                                    } else {
                                        fila += "¬";
                                    }
                                }
                                break;
                            case "empMaternidad":
                                if (!filtrarPorGrupo) {
                                    fila += (!rhFunListadoEmpleadosTO.getEmpMaternidad() ? "SNO" : "SSI") + "¬";
                                }
                                break;
                            case "empLactancia":
                                if (!filtrarPorGrupo) {
                                    fila += (!rhFunListadoEmpleadosTO.getEmpLactancia() ? "SNO" : "SSI") + "¬";
                                }
                                break;
                            case "empDiscapacidadTipo":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpDiscapacidadTipo() != null ? "S" + rhFunListadoEmpleadosTO.getEmpDiscapacidadTipo()
                                            : "B") + "¬";
                                }
                                break;
                            case "empDiscapacidadPorcentaje":
                                if (!filtrarPorGrupo) {
                                    fila += (rhFunListadoEmpleadosTO.getEmpDiscapacidadPorcentaje() != null ? "S" + rhFunListadoEmpleadosTO.getEmpDiscapacidadPorcentaje()
                                            : "B") + "¬";
                                }
                                break;
                            default:
                                fila += "";
                                break;
                        }
                    }

                }
                listaCuerpo.add(fila);
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarRhCategoriaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhCategoriaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de categorías");
            listaCabecera.add("S");
            listaCuerpo.add("SNombre");
            for (RhCategoriaTO o : listado) {
                listaCuerpo.add(
                        (o.getCatNombre() == null ? "B" : "S" + o.getCatNombre()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteAnticipoDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleAnticiposTO> listaDetalleAnticipos,
            String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Detalle de Anticipos");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCat." + "¬" + "SFecha" + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SValor" + "¬"
                    + "SForma de Pago" + "¬" + "SDocumento" + "¬" + "SContable" + "¬" + "SObservacion" + "¬" + "SPendiente" + "¬"
                    + "SReversado" + "¬" + "SAnulado");

            for (RhListaDetalleAnticiposTO rhListaDetalleAnticiposTO : listaDetalleAnticipos) {
                listaCuerpo.add((rhListaDetalleAnticiposTO.getDaCategoria() == null ? "B"
                        : "S" + rhListaDetalleAnticiposTO.getDaCategoria())
                        + "¬"
                        + (rhListaDetalleAnticiposTO.getDaFecha() == null ? "B"
                        : "T" + rhListaDetalleAnticiposTO.getDaFecha())
                        + "¬"
                        + (rhListaDetalleAnticiposTO.getDaId() == null ? "B"
                        : "S" + rhListaDetalleAnticiposTO.getDaId())
                        + "¬"
                        + (rhListaDetalleAnticiposTO.getDaNombres() == null ? "B"
                        : "S" + rhListaDetalleAnticiposTO.getDaNombres())
                        + "¬"
                        + (rhListaDetalleAnticiposTO.getDaValor() == null ? "B"
                        : "D" + rhListaDetalleAnticiposTO.getDaValor())
                        + "¬"
                        + (rhListaDetalleAnticiposTO.getDaFormaPago() == null ? "B"
                        : "S" + rhListaDetalleAnticiposTO.getDaFormaPago())
                        + "¬"
                        + (rhListaDetalleAnticiposTO.getDaDocumento() == null ? "B"
                        : "S" + rhListaDetalleAnticiposTO.getDaDocumento())
                        + "¬"
                        + (rhListaDetalleAnticiposTO.getDaContable() == null ? "B"
                        : "S" + rhListaDetalleAnticiposTO.getDaContable())
                        + "¬"
                        + (rhListaDetalleAnticiposTO.getDaObservaciones() == null ? "B" : "S" + rhListaDetalleAnticiposTO.getDaObservaciones())
                        + "¬"
                        + (rhListaDetalleAnticiposTO.getDaId() == null ? "B" : (rhListaDetalleAnticiposTO.getDaPendiente() ? "SSI" : "SNO")) + "¬"
                        + (rhListaDetalleAnticiposTO.getDaId() == null ? "B" : (rhListaDetalleAnticiposTO.getDaReverso() ? "SSI" : "SNO")) + "¬"
                        + (rhListaDetalleAnticiposTO.getDaId() == null ? "B" : (rhListaDetalleAnticiposTO.getDaAnulado() ? "SSI" : "SNO")));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteRhXiiiSueldoXiiiSueldoCalcular(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXiiiSueldoXiiiSueldoCalcular> lista) throws Exception {
        try {

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SPre-Calculo Xiii Sueldo");
            listaCabecera.add("S");
            listaCuerpo.add("SN. Identificación" + "¬" + "SApellidos y nombres" + "¬" + "SIngresos Calculados" + "¬" + "SIngresos Reales"
                    + "¬" + "SValor" + "¬" + "SForma de pago" + "¬" + "SNúmero de Documento" + "¬" + "SObservación");
            for (RhXiiiSueldoXiiiSueldoCalcular item : lista) {
                listaCuerpo.add("S" + item.getRhXiiiSueldo().getRhEmpleado().getRhEmpleadoPK().getEmpId()
                        + "¬"
                        + "S" + item.getRhXiiiSueldo().getRhEmpleado().getEmpApellidos() + " " + item.getRhXiiiSueldo().getRhEmpleado().getEmpNombres()
                        + "¬"
                        + (item.getIngresosCalculados() == null ? "B" : "D" + item.getIngresosCalculados().toString())
                        + "¬"
                        + (item.getRhXiiiSueldo().getXiiiBaseImponible() == null ? "B" : "D" + item.getRhXiiiSueldo().getXiiiBaseImponible())
                        + "¬"
                        + (item.getRhXiiiSueldo().getXiiiValor() == null ? "B" : "D" + item.getRhXiiiSueldo().getXiiiValor())
                        + "¬"
                        + (item.getFormaPago() == null ? "B" : "S" + item.getFormaPago().getFpDetalle())
                        + "¬"
                        + (item.getRhXiiiSueldo().getXiiiDocumento() == null ? "B" : "S" + item.getRhXiiiSueldo().getXiiiDocumento())
                        + "¬"
                        + (item.getRhXiiiSueldo().getXiiiObservaciones() == null ? "B" : "S" + item.getRhXiiiSueldo().getXiiiObservaciones()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteAnticipoDetalleLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleAnticiposLoteTO> listadoAnticipoDetalles,
            String tipo, String numero, String periodo) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            BigDecimal cero = new java.math.BigDecimal("0.00");
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SSoporte Contable de Anticipos a Empleados");
            listaCabecera.add("SContable: " + periodo + " | " + "Hasta: " + tipo + " | " + numero);
            listaCabecera.add("S");

            listaCuerpo.add("SFecha" + "¬" + "SN. Identificación" + "¬" + "SNombre" + "¬" + "SValor" + "¬" + "SForma Pago" + "¬"
                    + "SDocumento" + "¬" + "SObservación");
            for (RhListaDetalleAnticiposLoteTO rhListaDetalleBonosLoteTO : listadoAnticipoDetalles) {
                listaCuerpo.add((rhListaDetalleBonosLoteTO.getDalFecha() == null ? "B"
                        : "T" + rhListaDetalleBonosLoteTO.getDalFecha())
                        + "¬"
                        + (rhListaDetalleBonosLoteTO.getDalId() == null ? "B"
                        : "S" + rhListaDetalleBonosLoteTO.getDalId())
                        + "¬"
                        + (rhListaDetalleBonosLoteTO.getDalNombres() == null ? "B"
                        : "S" + rhListaDetalleBonosLoteTO.getDalNombres())
                        + "¬"
                        + (rhListaDetalleBonosLoteTO.getDalValor() == null ? "B" + cero
                        : "D" + rhListaDetalleBonosLoteTO.getDalValor())
                        + "¬"
                        + (rhListaDetalleBonosLoteTO.getDalFormaPagoDetalle() == null ? "B"
                        : "S" + rhListaDetalleBonosLoteTO.getDalFormaPagoDetalle())
                        + "¬"
                        + (rhListaDetalleBonosLoteTO.getDalDocumento() == null ? "B"
                        : "S" + rhListaDetalleBonosLoteTO.getDalDocumento())
                        + "¬" + (rhListaDetalleBonosLoteTO.getDalObservaciones() == null ? "B"
                        : "S" + rhListaDetalleBonosLoteTO.getDalObservaciones())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReportePrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetallePrestamosTO> listadoDetallePrestamos,
            String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Detalle de Préstamos");
            listaCabecera.add("SDesde: " + fechaDesde + "Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCat." + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SValor" + "¬" + "SForma Pago" + "¬"
                    + "SDocumento" + "¬" + "SContable" + "¬" + "SAnulado");
            for (RhListaDetallePrestamosTO rhListaDetalleAnticiposPrestamosTO : listadoDetallePrestamos) {
                listaCuerpo.add((rhListaDetalleAnticiposPrestamosTO.getDpCategoria() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDpCategoria())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDpId() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDpId())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDpNombres() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDpNombres())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDpValor() == null ? "B"
                        : "D" + rhListaDetalleAnticiposPrestamosTO.getDpValor().toString())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDpFormaPago() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDpFormaPago())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDpDocumento() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDpDocumento())
                        + "¬" + (rhListaDetalleAnticiposPrestamosTO.getDpContable() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDpContable()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteRhXivSueldoXivSueldoCalcular(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXivSueldoXivSueldoCalcular> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SPre-Calculo Xiv Sueldo");
            listaCabecera.add("S");
            listaCuerpo.add("SN. Identificación" + "¬" + "SApellidos y nombres" + "¬" + "SDías Calculados" + "¬" + "SDías Reales"
                    + "¬" + "SValor" + "¬" + "SForma de pago" + "¬" + "SNúmero de Documento" + "¬" + "SObservación");
            for (RhXivSueldoXivSueldoCalcular item : lista) {
                listaCuerpo.add(
                        "S" + item.getRhXivSueldo().getRhEmpleado().getRhEmpleadoPK().getEmpId() + "¬"
                        + "S" + item.getRhXivSueldo().getRhEmpleado().getEmpApellidos() + " " + item.getRhXivSueldo().getRhEmpleado().getEmpNombres() + "¬"
                        + (item.getRhXivSueldo().getXivDiasLaboradosEmpleado() == null ? "B" : "S" + item.getRhXivSueldo().getXivDiasLaboradosEmpleado().toString()) + "¬"
                        + (item.getDiasLaborados() == null ? "B" : "S" + item.getDiasLaborados()) + "¬"
                        + (item.getRhXivSueldo().getXivValor() == null ? "B" : "D" + item.getRhXivSueldo().getXivValor()) + "¬"
                        + (item.getFormaPago() == null ? "B" : "S" + item.getFormaPago().getFpDetalle()) + "¬"
                        + (item.getRhXivSueldo().getXivDocumento() == null ? "B" : "S" + item.getRhXivSueldo().getXivDocumento()) + "¬"
                        + (item.getRhXivSueldo().getXivObservaciones() == null ? "B" : "S" + item.getRhXivSueldo().getXivObservaciones()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteDetalleDeAnticipoPrestamo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleAnticiposPrestamosTO> listadoDetalleAnticiposPrestamos,
            String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Detalle Anticipo de Préstamos");
            listaCabecera.add("SDesde: " + fechaDesde + "Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCat." + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SValor" + "¬" + "SForma Pago" + "¬"
                    + "SDocumento" + "¬" + "SContable" + "¬" + "SAnulado");
            for (RhListaDetalleAnticiposPrestamosTO rhListaDetalleAnticiposPrestamosTO : listadoDetalleAnticiposPrestamos) {
                listaCuerpo.add((rhListaDetalleAnticiposPrestamosTO.getDapCategoria() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDapCategoria())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDapId() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDapId())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDapNombres() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDapNombres())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDapValor() == null ? "B"
                        : "D" + rhListaDetalleAnticiposPrestamosTO.getDapValor())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDapFormaPago() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDapFormaPago())
                        + "¬"
                        + (rhListaDetalleAnticiposPrestamosTO.getDapDocumento() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDapDocumento())
                        + "¬" + (rhListaDetalleAnticiposPrestamosTO.getDapContable() == null ? "B"
                        : "S" + rhListaDetalleAnticiposPrestamosTO.getDapContable()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteDetalleDeBono(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleBonosTO> listadoDetalleBonos,
            String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            BigDecimal cero = new java.math.BigDecimal("0.00");
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de detalle de bono");
            listaCabecera.add("SDesde: " + fechaDesde + "Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCategoría" + "¬" + "SFecha" + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SConcepto" + "¬"
                    + "SSector" + "¬" + "SPiscina" + "¬" + "SValor" + "¬" + "SContable" + "¬" + "SDeducible" + "¬"
                    + "SObservaciones");
            for (RhListaDetalleBonosTO rhListaDetalleBonosTO : listadoDetalleBonos) {
                boolean reverso = rhListaDetalleBonosTO.getDbReversado() == null ? false
                        : rhListaDetalleBonosTO.getDbReversado();
                boolean anulado = rhListaDetalleBonosTO.getDbAnulado() == null ? false
                        : rhListaDetalleBonosTO.getDbAnulado();
                listaCuerpo
                        .add((rhListaDetalleBonosTO.getDbCategoria() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbCategoria())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbFecha() == null ? "B"
                                : "T" + rhListaDetalleBonosTO.getDbFecha())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbId() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbId())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbNombres() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbNombres())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbConcepto() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbConcepto())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbSector() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbSector())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbPiscina() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbPiscina())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbValor() == null ? "B"
                                : "D" + (rhListaDetalleBonosTO.getDbValor()
                                        .multiply(reverso ? java.math.BigDecimal.ONE.negate()
                                                : java.math.BigDecimal.ONE)
                                        .multiply(anulado ? cero : java.math.BigDecimal.ONE)).toString())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbContable() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbContable())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbDeducible() == null ? "B"
                                : "S" + (rhListaDetalleBonosTO.getDbDeducible() ? "SI" : "NO"))
                                + "¬" + (rhListaDetalleBonosTO.getDbObservaciones() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbObservaciones())
                                + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteDetalleDeHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleHorasExtrasTO> listaDetalleHorasExtrasTO, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            BigDecimal cero = new java.math.BigDecimal("0.00");
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de detalle de horas extras");
            listaCabecera.add("SDesde: " + fechaDesde + "Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCategoría" + "¬" + "SFecha" + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SConcepto" + "¬"
                    + "SSector" + "¬" + "SPiscina" + "¬" + "SHoras 50%" + "¬" + "SHoras 100%" + "¬" + "SHoras Extraordinarias 100%" + "¬" + "SContable" + "¬"
                    + "SObservaciones");
            for (RhListaDetalleHorasExtrasTO rhListaDetalleBonosTO : listaDetalleHorasExtrasTO) {
                boolean reverso = rhListaDetalleBonosTO.getDbReversado() == null ? false
                        : rhListaDetalleBonosTO.getDbReversado();
                boolean anulado = rhListaDetalleBonosTO.getDbAnulado() == null ? false
                        : rhListaDetalleBonosTO.getDbAnulado();
                listaCuerpo
                        .add((rhListaDetalleBonosTO.getDbCategoria() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbCategoria())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbFecha() == null ? "B"
                                : "T" + rhListaDetalleBonosTO.getDbFecha())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbId() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbId())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbNombres() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbNombres())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbConcepto() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbConcepto())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbSector() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbSector())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbPiscina() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbPiscina())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbValor50() == null ? "B"
                                : "D" + (rhListaDetalleBonosTO.getDbValor50()
                                        .multiply(reverso ? java.math.BigDecimal.ONE.negate()
                                                : java.math.BigDecimal.ONE)
                                        .multiply(anulado ? cero : java.math.BigDecimal.ONE)).toString())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbValor100() == null ? "B"
                                : "D" + (rhListaDetalleBonosTO.getDbValor100()
                                        .multiply(reverso ? java.math.BigDecimal.ONE.negate()
                                                : java.math.BigDecimal.ONE)
                                        .multiply(anulado ? cero : java.math.BigDecimal.ONE)).toString())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbValorExtraordinarias100() == null ? "B"
                                : "D" + (rhListaDetalleBonosTO.getDbValorExtraordinarias100()
                                        .multiply(reverso ? java.math.BigDecimal.ONE.negate()
                                                : java.math.BigDecimal.ONE)
                                        .multiply(anulado ? cero : java.math.BigDecimal.ONE)).toString())
                                + "¬"
                                + (rhListaDetalleBonosTO.getDbContable() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbContable())
                                + "¬" + (rhListaDetalleBonosTO.getDbObservaciones() == null ? "B"
                                : "S" + rhListaDetalleBonosTO.getDbObservaciones())
                                + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteConsolidadosDeIngresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhFunFormulario107_2013TO> listadoConsolidadoIngresos,
            String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            BigDecimal cero = new java.math.BigDecimal("0.00");
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Consolidado de Ingresos (Preliminar de Formulario 107-2013)");
            listaCabecera.add("SDesde: " + fechaDesde + "Hasta: " + fechaHasta);
            listaCabecera.add("S");

            int anioHasta = UtilsDate.getNumeroAnio(fechaHasta, "dd-MM-yyyy");
            boolean ocultarCampoTurismo = false;
            if (anioHasta <= 2020) {
                ocultarCampoTurismo = true;
            }

            listaCuerpo.add("SbenGalpg" + "¬" + "SEnferm. Catastrófica" + "¬" + "STipo Id" + "¬" + "SN. Identificación" + "¬" + "SApellidos" + "¬" + "SNombres" + "¬"
                    + "SEstablecimiento" + "¬" + "SResidencia" + "¬" + "SPaís de Residencia" + "¬" + "SConvenio" + "¬"
                    + "STipo de Discapacidad" + "¬" + "SPorcentaje Discapacidad" + "¬" + "SDiscapacidad Tipo Id" + "¬"
                    + "SDiscapacidad Número Id" + "¬" + "SSueldo" + "¬" + "SBonos" + "¬" + "SUtilidades" + "¬"
                    + "SSueldo otros Empleadores" + "¬" + "SImpuesto Asumido" + "¬" + "SXiii Sueldo" + "¬"
                    + "SXiv Sueldo" + "¬" + "SFondo Reserva" + "¬" + "SSalario Digno" + "¬" + "SDesahucio" + "¬"
                    + "SSubtotal" + "¬" + "SSalario Neto" + "¬" + "SIess" + "¬" + "SIess otros Empleadores" + "¬"
                    + "SVivienda" + "¬" + "SSalud" + "¬" + "SEducación" + "¬" + "SAlimentación" + "¬" + "SVestuario" + (!ocultarCampoTurismo ? ("¬" + "STurismo") : "")
                    + "¬" + "SRebaja Discapacitado" + "¬" + "SRebaja Tercera Edad" + "¬" + "SBase Imponible" + "¬"
                    + "SImpuesto Causado" + "¬" + "SImpuesto Asumido otros Empleadores" + "¬"
                    + "SImpuesto Asumido este Empleador" + "¬" + "SValor Retenido" + "¬" + "SInactivo");
            for (RhFunFormulario107_2013TO rhFunFormulario107_2013TO : listadoConsolidadoIngresos) {
                boolean f107EmpleadoInactivo = rhFunFormulario107_2013TO.getF107EmpleadoInactivo() == null ? false
                        : rhFunFormulario107_2013TO.getF107EmpleadoInactivo();
                listaCuerpo
                        .add("S" + "NO" + "¬" + "S" + "NO" + "¬"
                                + (rhFunFormulario107_2013TO.getF107Tipo() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107Tipo())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Id() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107Id())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Apellidos() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107Apellidos())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Nombres() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107Nombres())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Establecimiento() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107Establecimiento())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107ResidenciaTipo() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107ResidenciaTipo())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107ResidenciaPais() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107ResidenciaPais())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Convenio() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107Convenio())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107DiscapacidadTipo() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107DiscapacidadTipo())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107DiscapacidadPorcentaje() == null ? "B"
                                : "I" + rhFunFormulario107_2013TO.getF107DiscapacidadPorcentaje().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107DiscapacidadIdTipo() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107DiscapacidadIdTipo())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107DiscapacidadIdNumero() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107DiscapacidadIdNumero())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Sueldo() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Sueldo().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Bonos() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Bonos().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Utilidades() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Utilidades().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107SueldoOtrosEmpleadores() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107SueldoOtrosEmpleadores().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107ImpuestoAsumido() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107ImpuestoAsumido().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107XiiiSueldo() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107XiiiSueldo().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107XivSueldo() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107XivSueldo().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107FondoReserva() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107FondoReserva().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107SalarioDigno() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107SalarioDigno().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Desahucio() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Desahucio().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Subtotal() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Subtotal().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107SalarioNeto() == null ? "B"
                                : "S" + rhFunFormulario107_2013TO.getF107SalarioNeto())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Iess() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Iess().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107IessOtrosEmpleadores() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107IessOtrosEmpleadores().add(cero)
                                        .toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Vivienda() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Vivienda().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Salud() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Salud().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Educacion() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Educacion().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Alimentacion() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Alimentacion().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107Vestuario() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107Vestuario().add(cero).toString())
                                + (ocultarCampoTurismo ? "" : ("¬" + (rhFunFormulario107_2013TO.getF107Turismo() == null ? "F" : "F" + rhFunFormulario107_2013TO.getF107Turismo().add(cero).toString())))
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107RebajaDiscapacitado() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107RebajaDiscapacitado().add(cero)
                                        .toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107RebajaTerceraEdad() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107RebajaTerceraEdad().add(cero)
                                        .toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107BaseImponible() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107BaseImponible().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107ImpuestoCausado() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107ImpuestoCausado().add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107ImpuestoAsumidoOtrosEmpleadores() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107ImpuestoAsumidoOtrosEmpleadores()
                                        .add(cero).toString())
                                + "¬"
                                + (rhFunFormulario107_2013TO.getF107ImpuestoAsumidoEsteEmpleador() == null ? "F"
                                : "F" + rhFunFormulario107_2013TO.getF107ImpuestoAsumidoEsteEmpleador().add(cero).toString())
                                + "¬" + (rhFunFormulario107_2013TO.getF107ValorRetenido() == null ? "F" : "F" + rhFunFormulario107_2013TO.getF107ValorRetenido().add(cero).toString())
                                + "¬" + (f107EmpleadoInactivo == false ? "SNO" : "SSI"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void exportarReporteTXTConsolidadosDeIngresos(List<RhFunFormulario107_2013TO> listaConsolidadoIngresos, HttpServletResponse response, String fechaHasta) throws Exception {
        File archivoCreado = File.createTempFile("xxx", ".txt");
        FileOutputStream fos = new FileOutputStream(archivoCreado);
        OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
        BigDecimal cero = new java.math.BigDecimal("0.00");
        int anioHasta = UtilsDate.getNumeroAnio(fechaHasta, "dd-MM-yyyy");
        boolean ocultarCampoTurismo = false;
        if (anioHasta <= 2020) {
            ocultarCampoTurismo = true;
        }
        int porcentaje = 0;
        for (RhFunFormulario107_2013TO rhFunFormulario107_2013TO : listaConsolidadoIngresos) {
            if (rhFunFormulario107_2013TO.getF107DiscapacidadPorcentaje() != null) {
                porcentaje = rhFunFormulario107_2013TO.getF107DiscapacidadPorcentaje().add(cero).intValue();
            }
            out.write("NO" + "\t" + "NO" + "\t"
                    + (rhFunFormulario107_2013TO.getF107Tipo() == null ? "" : rhFunFormulario107_2013TO.getF107Tipo())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Id() == null ? "" : rhFunFormulario107_2013TO.getF107Id())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Apellidos() == null ? "" : rhFunFormulario107_2013TO.getF107Apellidos())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Nombres() == null ? "" : rhFunFormulario107_2013TO.getF107Nombres())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Establecimiento() == null ? "" : rhFunFormulario107_2013TO.getF107Establecimiento())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107ResidenciaTipo() == null ? "" : rhFunFormulario107_2013TO.getF107ResidenciaTipo())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107ResidenciaPais() == null ? "" : rhFunFormulario107_2013TO.getF107ResidenciaPais())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Convenio() == null ? "" : rhFunFormulario107_2013TO.getF107Convenio())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107DiscapacidadTipo() == null ? "" : rhFunFormulario107_2013TO.getF107DiscapacidadTipo())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107DiscapacidadPorcentaje() == null ? "" : porcentaje + "")
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107DiscapacidadIdTipo() == null ? "" : rhFunFormulario107_2013TO.getF107DiscapacidadIdTipo())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107DiscapacidadIdNumero() == null ? "" : rhFunFormulario107_2013TO.getF107DiscapacidadIdNumero())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Sueldo() == null ? "" : rhFunFormulario107_2013TO.getF107Sueldo().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Bonos() == null ? "" : rhFunFormulario107_2013TO.getF107Bonos().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Utilidades() == null ? "" : rhFunFormulario107_2013TO.getF107Utilidades().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107SueldoOtrosEmpleadores() == null ? "" : rhFunFormulario107_2013TO.getF107SueldoOtrosEmpleadores().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107ImpuestoAsumido() == null ? "" : rhFunFormulario107_2013TO.getF107ImpuestoAsumido().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107XiiiSueldo() == null ? "" : rhFunFormulario107_2013TO.getF107XiiiSueldo().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107XivSueldo() == null ? "" : rhFunFormulario107_2013TO.getF107XivSueldo().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107FondoReserva() == null ? "" : rhFunFormulario107_2013TO.getF107FondoReserva().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107SalarioDigno() == null ? "" : rhFunFormulario107_2013TO.getF107SalarioDigno().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Desahucio() == null ? "" : rhFunFormulario107_2013TO.getF107Desahucio().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Subtotal() == null ? "" : rhFunFormulario107_2013TO.getF107Subtotal().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107SalarioNeto() == null ? "" : rhFunFormulario107_2013TO.getF107SalarioNeto())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Iess() == null ? "" : rhFunFormulario107_2013TO.getF107Iess().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107IessOtrosEmpleadores() == null ? "" : rhFunFormulario107_2013TO.getF107IessOtrosEmpleadores().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Vivienda() == null ? "" : rhFunFormulario107_2013TO.getF107Vivienda().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Salud() == null ? "" : rhFunFormulario107_2013TO.getF107Salud().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Educacion() == null ? "" : rhFunFormulario107_2013TO.getF107Educacion().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Alimentacion() == null ? "" : rhFunFormulario107_2013TO.getF107Alimentacion().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Vestuario() == null ? "" : rhFunFormulario107_2013TO.getF107Vestuario().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107Turismo() == null || ocultarCampoTurismo ? "" : rhFunFormulario107_2013TO.getF107Turismo().add(cero).toString())
                    + (ocultarCampoTurismo ? "" : "\t")
                    + (rhFunFormulario107_2013TO.getF107RebajaDiscapacitado() == null ? "" : rhFunFormulario107_2013TO.getF107RebajaDiscapacitado().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107RebajaTerceraEdad() == null ? "" : rhFunFormulario107_2013TO.getF107RebajaTerceraEdad().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107BaseImponible() == null ? "" : rhFunFormulario107_2013TO.getF107BaseImponible().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107ImpuestoCausado() == null ? "" : rhFunFormulario107_2013TO.getF107ImpuestoCausado().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107ImpuestoAsumidoOtrosEmpleadores() == null ? "" : rhFunFormulario107_2013TO.getF107ImpuestoAsumidoOtrosEmpleadores().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107ImpuestoAsumidoEsteEmpleador() == null ? "" : rhFunFormulario107_2013TO.getF107ImpuestoAsumidoEsteEmpleador().add(cero).toString())
                    + "\t"
                    + (rhFunFormulario107_2013TO.getF107ValorRetenido() == null ? "" : rhFunFormulario107_2013TO.getF107ValorRetenido().add(cero).toString())
                    + "\r\n");
        }
        out.close();
        fos.close();

        ServletOutputStream servletStream = response.getOutputStream();
        InputStream in = new FileInputStream(archivoCreado);
        int bit;
        while ((bit = in.read()) != -1) {
            servletStream.write(bit);
        }
        servletStream.flush();
        servletStream.close();
        in.close();
    }

    @Override
    public Map<String, Object> generarReporteConsolidadosAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaConsolidadoAnticiposPrestamosTO> listadoConsolidadoAnticiposPrestamos,
            String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            BigDecimal cero = new java.math.BigDecimal("0.00");
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Consolidado de Anticipos y Préstamos");
            listaCabecera.add("SDesde: " + fechaDesde + "Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCat." + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SAnticipos" + "¬" + "SPréstamos" + "¬"
                    + "STotal" + "¬" + "SOrden");
            for (RhListaConsolidadoAnticiposPrestamosTO rhListaConsolidadoAnticiposPrestamosTO : listadoConsolidadoAnticiposPrestamos) {
                listaCuerpo.add((rhListaConsolidadoAnticiposPrestamosTO.getCapCategoria() == null ? "B"
                        : "S" + rhListaConsolidadoAnticiposPrestamosTO.getCapCategoria())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCapId() == null ? "B"
                        : "S" + rhListaConsolidadoAnticiposPrestamosTO.getCapId())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCapNombres() == null ? "B"
                        : "S" + rhListaConsolidadoAnticiposPrestamosTO.getCapNombres())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCapAnticipos() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCapAnticipos().toString())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCapPrestamos() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCapPrestamos())
                        + "¬"
                        + (rhListaConsolidadoAnticiposPrestamosTO.getCapTotal() == null ? "B"
                        : "D" + rhListaConsolidadoAnticiposPrestamosTO.getCapTotal().toString())
                        + "¬" + (rhListaConsolidadoAnticiposPrestamosTO.getCapOrden() == null ? "B"
                        : "S" + rhListaConsolidadoAnticiposPrestamosTO.getCapOrden()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteSaldosConsolidadosDeBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaSaldoConsolidadoBonosTO> listadoSaldoConsolidadoBonos,
            String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Saldos Consolidados De Bonos y Viáticos");
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SCat." + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SBonos" + "¬" + "SViáticos" + "¬"
                    + "STotal" + "¬" + "SOrden");
            for (RhListaSaldoConsolidadoBonosTO rhListaSaldoConsolidadoBonosViaticosTO : listadoSaldoConsolidadoBonos) {
                listaCuerpo.add((rhListaSaldoConsolidadoBonosViaticosTO.getScbvCategoria() == null ? "B"
                        : "S" + rhListaSaldoConsolidadoBonosViaticosTO.getScbvCategoria())
                        + "¬"
                        + (rhListaSaldoConsolidadoBonosViaticosTO.getScbvId() == null ? "B"
                        : "S" + rhListaSaldoConsolidadoBonosViaticosTO.getScbvId())
                        + "¬"
                        + (rhListaSaldoConsolidadoBonosViaticosTO.getScbvNombres() == null ? "B"
                        : "S" + rhListaSaldoConsolidadoBonosViaticosTO.getScbvNombres())
                        + "¬"
                        + (rhListaSaldoConsolidadoBonosViaticosTO.getScbvBonos() == null ? "B"
                        : "D" + rhListaSaldoConsolidadoBonosViaticosTO.getScbvBonos().toString())
                        + "¬"
                        + (rhListaSaldoConsolidadoBonosViaticosTO.getScbvViaticos() == null ? "B"
                        : "D" + rhListaSaldoConsolidadoBonosViaticosTO.getScbvViaticos().toString())
                        + "¬"
                        + (rhListaSaldoConsolidadoBonosViaticosTO.getScbvTotal() == null ? "B"
                        : "D" + rhListaSaldoConsolidadoBonosViaticosTO.getScbvTotal().toString())
                        + "¬" + (rhListaSaldoConsolidadoBonosViaticosTO.getScbvOrden() == null ? "B"
                        : "S" + rhListaSaldoConsolidadoBonosViaticosTO.getScbvOrden()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSaldoConsolidadoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaSaldoConsolidadoHorasExtrasTO> listaSaldoConsolidadoHorasExtras, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Saldos Consolidados de Horas extras");
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCat." + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SHoras 50%" + "¬" + "SHoras 100%" + "¬" + "SExtraordinarias 100%" + "¬"
                    + "STotal" + "¬" + "SOrden");
            for (RhListaSaldoConsolidadoHorasExtrasTO rhListaSaldoConsolidadoHE : listaSaldoConsolidadoHorasExtras) {
                listaCuerpo.add((rhListaSaldoConsolidadoHE.getScbvCategoria() == null ? "B" : "S" + rhListaSaldoConsolidadoHE.getScbvCategoria())
                        + "¬" + (rhListaSaldoConsolidadoHE.getScbvId() == null ? "B" : "S" + rhListaSaldoConsolidadoHE.getScbvId())
                        + "¬" + (rhListaSaldoConsolidadoHE.getScbvNombres() == null ? "B" : "S" + rhListaSaldoConsolidadoHE.getScbvNombres())
                        + "¬" + (rhListaSaldoConsolidadoHE.getScbvValor50() == null ? "B" : "D" + rhListaSaldoConsolidadoHE.getScbvValor50().toString())
                        + "¬" + (rhListaSaldoConsolidadoHE.getScbvValor100() == null ? "B" : "D" + rhListaSaldoConsolidadoHE.getScbvValor100().toString())
                        + "¬" + (rhListaSaldoConsolidadoHE.getScbvValorExtraordinarias100() == null ? "B" : "D" + rhListaSaldoConsolidadoHE.getScbvValorExtraordinarias100().toString())
                        + "¬" + (rhListaSaldoConsolidadoHE.getScbvTotal() == null ? "B" : "D" + rhListaSaldoConsolidadoHE.getScbvTotal().toString())
                        + "¬" + (rhListaSaldoConsolidadoHE.getScbvOrden() == null ? "B" : "S" + rhListaSaldoConsolidadoHE.getScbvOrden()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteBonoDetalleLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleBonosLoteTO> listadoDetalleBonosLote) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            String fechaBono = "";
            String formaPagoBono = "";
            String documentoBono = "";
            String observacionBono = "";

            BigDecimal cero = new java.math.BigDecimal("0.00");
            if (listadoDetalleBonosLote.size() > 1) {
                fechaBono = listadoDetalleBonosLote.get(0).getDblFecha();
                formaPagoBono = listadoDetalleBonosLote.get(0).getDblFormaPagoDetalle();
                documentoBono = listadoDetalleBonosLote.get(0).getDblDocumento();
                observacionBono = listadoDetalleBonosLote.get(0).getDblObservaciones();
            }
            listaCabecera.add("SDetalle de Bono por Lote");
            listaCabecera.add("SFecha: " + fechaBono);
            listaCabecera.add("SForma de Pago: " + formaPagoBono);
            listaCabecera.add("SDocumento: " + documentoBono);
            listaCabecera.add("SObservación: " + observacionBono);
            listaCabecera.add("S");

            listaCuerpo.add("SN. Identificación" + "¬" + "SNombre" + "¬" + "SValor");
            for (RhListaDetalleBonosLoteTO rhListaDetalleBonosLoteTO : listadoDetalleBonosLote) {
                listaCuerpo.add((rhListaDetalleBonosLoteTO.getDblId() == null ? "B"
                        : "S" + rhListaDetalleBonosLoteTO.getDblId())
                        + "¬"
                        + (rhListaDetalleBonosLoteTO.getDblNombres() == null ? "B"
                        : "S" + rhListaDetalleBonosLoteTO.getDblNombres())
                        + "¬" + (rhListaDetalleBonosLoteTO.getDblValor() == null ? "B" + cero
                        : "D" + rhListaDetalleBonosLoteTO.getDblValor())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteHorasExtrasDetalleLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleHorasExtrasLoteTO> listaSoporteContableHorasExtrasTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            String fechaHorasExtras = "";
            String formaPagoHorasExtras = "";
            String documentoHorasExtras = "";
            String observacionHorasExtras = "";

            BigDecimal cero = new java.math.BigDecimal("0.00");
            if (listaSoporteContableHorasExtrasTO.size() > 1) {
                fechaHorasExtras = listaSoporteContableHorasExtrasTO.get(0).getDblFecha();
                formaPagoHorasExtras = listaSoporteContableHorasExtrasTO.get(0).getDblFormaPagoDetalle();
                documentoHorasExtras = listaSoporteContableHorasExtrasTO.get(0).getDblDocumento();
                observacionHorasExtras = listaSoporteContableHorasExtrasTO.get(0).getDblObservaciones();
            }
            listaCabecera.add("SDetalle de Horas extras por Lote");
            listaCabecera.add("SFecha: " + fechaHorasExtras);
            listaCabecera.add("SForma de Pago: " + formaPagoHorasExtras);
            listaCabecera.add("SDocumento: " + documentoHorasExtras);
            listaCabecera.add("SObservación: " + observacionHorasExtras);
            listaCabecera.add("S");

            listaCuerpo.add("SN. Identificación" + "¬" + "SNombre" + "¬" + "SHoras 50%" + "¬" + "SHoras 100%" + "¬" + "SHoras Extraordinarias 100%");
            for (RhListaDetalleHorasExtrasLoteTO rhListaDetalleHorasExtrasLoteTO : listaSoporteContableHorasExtrasTO) {
                listaCuerpo.add((rhListaDetalleHorasExtrasLoteTO.getDblId() == null ? "B"
                        : "S" + rhListaDetalleHorasExtrasLoteTO.getDblId())
                        + "¬"
                        + (rhListaDetalleHorasExtrasLoteTO.getDblNombres() == null ? "B"
                        : "S" + rhListaDetalleHorasExtrasLoteTO.getDblNombres())
                        + "¬" + (rhListaDetalleHorasExtrasLoteTO.getDblValor50() == null ? "B" + cero : "D" + rhListaDetalleHorasExtrasLoteTO.getDblValor50())
                        + "¬" + (rhListaDetalleHorasExtrasLoteTO.getDblValor100() == null ? "B" + cero : "D" + rhListaDetalleHorasExtrasLoteTO.getDblValor100())
                        + "¬" + (rhListaDetalleHorasExtrasLoteTO.getDblValorExtraordinarias100() == null ? "B" + cero : "D" + rhListaDetalleHorasExtrasLoteTO.getDblValorExtraordinarias100())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<RhListaDetalleRolesTO> eliminarDetallesRolesNulos(List<RhListaDetalleRolesTO> listaRhListaDetalleRolesTO, boolean evaluarContable, ConContablePK pk) {
        List<RhListaDetalleRolesTO> listaFinal = new ArrayList<>();
        for (RhListaDetalleRolesTO item : listaRhListaDetalleRolesTO) {
            if (item.getLrpContable() != null) {
                if (evaluarContable) {
                    String[] pkcontable = item.getLrpContable().split("\\|");
                    String periodo = pkcontable[0] != null ? pkcontable[0].trim() : null;
                    String tipo = pkcontable[1] != null ? pkcontable[1].trim() : null;
                    String numero = pkcontable[2] != null ? pkcontable[2].trim() : null;

                    if (pk != null && pk.getConPeriodo().equals(periodo) && pk.getConTipo().equals(tipo) && pk.getConNumero().equals(numero)) {
                        listaFinal.add(item);
                    }
                } else {
                    listaFinal.add(item);
                }
            }
        }
        return listaFinal;
    }

    @Override
    public ReporteAnticipoPrestamoXIIISueldo convertirRhFunXiiiSueldoConsultarTOAReporteAnticipoPrestamoXIIISueldo(RhFunXiiiSueldoConsultarTO item, String fechaMaxima) {
        ReporteAnticipoPrestamoXIIISueldo rhReporteXIVSueldo = new ReporteAnticipoPrestamoXIIISueldo();
        rhReporteXIVSueldo.setComprobante(item.getXiiiPeriodo());
        rhReporteXIVSueldo.setComprobante(item.getXiiiTipo());
        rhReporteXIVSueldo.setComprobante(item.getXiiiNumero());
        rhReporteXIVSueldo.setFecha(fechaMaxima);
        rhReporteXIVSueldo.setCedula(item.getXiiiId());
        rhReporteXIVSueldo.setNombres(item.getXiiiApellidos() + " " + item.getXiiiNombres());
        rhReporteXIVSueldo.setCargo(item.getXiiiCargo());
        rhReporteXIVSueldo.setNombreSector(item.getXiiiSector());
        rhReporteXIVSueldo.setValor(item.getXiiiValorXiiiSueldo());
        rhReporteXIVSueldo.setFormaPago(item.getXiiiFormaPago());
        rhReporteXIVSueldo.setReferencia(item.getXiiiDocumento());
        rhReporteXIVSueldo.setObservaciones(item.getXiiiObservaciones());
        return rhReporteXIVSueldo;
    }

    @Override
    public ReporteAnticipoPrestamoXIIISueldo convertirRhFunXivSueldoConsultarTOAReporteAnticipoPrestamoXIIISueldo(RhFunXivSueldoConsultarTO item, String fechaMaxima) {
        ReporteAnticipoPrestamoXIIISueldo rhReporteXIVSueldo = new ReporteAnticipoPrestamoXIIISueldo();
        rhReporteXIVSueldo.setComprobante(item.getXivPeriodo());
        rhReporteXIVSueldo.setComprobante(item.getXivTipo());
        rhReporteXIVSueldo.setComprobante(item.getXivNumero());
        rhReporteXIVSueldo.setFecha(fechaMaxima);
        rhReporteXIVSueldo.setCedula(item.getXivId());
        rhReporteXIVSueldo.setNombres(item.getXivApellidos() + " " + item.getXivNombres());
        rhReporteXIVSueldo.setCargo(item.getXivCargo());
        rhReporteXIVSueldo.setNombreSector(item.getXivSector());
        rhReporteXIVSueldo.setValor(item.getXivValorXivSueldo());
        rhReporteXIVSueldo.setFormaPago(item.getXivFormaPago());
        rhReporteXIVSueldo.setReferencia(item.getXivDocumento());
        rhReporteXIVSueldo.setObservaciones(item.getXivObservaciones());
        return rhReporteXIVSueldo;
    }

    @Override
    public Map<String, Object> exportarRhUtilidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhUtilidades> listaRhUtilidades) throws Exception {
        try {
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList<>();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SPre-Calculo Utilidades");
            listaCabecera.add("S");

            listaCuerpo.add("SN. Identificación" + "¬" + "SApellidos y nombres" + "¬" + "SValor" + "¬" + "SImpuesto a la Renta" + "¬" + "SValor a Recibir" + "¬" + "SForma de pago" + "¬" + "SNúmero de Documento" + "¬" + "SObservación");
            for (RhUtilidades item : listaRhUtilidades) {

                item.setUtiValorTotal(item.getUtiValorTotal() == null ? BigDecimal.ZERO : item.getUtiValorTotal());
                item.setUtiImpuestoRenta(item.getUtiImpuestoRenta() == null ? BigDecimal.ZERO : item.getUtiImpuestoRenta());
                BigDecimal valorARecibir = item.getUtiValorTotal().subtract(item.getUtiImpuestoRenta());

                listaCuerpo.add("S" + item.getRhEmpleado().getRhEmpleadoPK().getEmpId()
                        + "¬"
                        + "S" + item.getRhEmpleado().getEmpApellidos() + " " + item.getRhEmpleado().getEmpNombres()
                        + "¬"
                        + (item.getUtiValorTotal() == null ? "B" : "D" + item.getUtiValorTotal())
                        + "¬"
                        + (item.getUtiImpuestoRenta() == null ? "B" : "D" + item.getUtiImpuestoRenta())
                        + "¬"
                        + (valorARecibir == null ? "B" : "D" + valorARecibir)
                        + "¬"
                        + (item.getRhEmpleado() == null ? "B" : "S" + item.getRhEmpleado().getEmpFormaPago())
                        + "¬"
                        + (item.getUtiDocumento() == null ? "B" : "S" + item.getUtiDocumento())
                        + "¬"
                        + (item.getUtiObservaciones() == null ? "B" : "S" + item.getUtiObservaciones()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarValoresCalculadosRol(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ItemListaRolTO> lista) throws Exception {
        try {
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();

            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList<>();
            String otrosIngresos = "SOtros Ingresos";

            if (usuarioEmpresaReporteTO.getEmpCodigo().equals("BIO1") || usuarioEmpresaReporteTO.getEmpCodigo().equals("BIO2") || usuarioEmpresaReporteTO.getEmpCodigo().equals("BIO3")) {
                esBio = true;
                otrosIngresos = "SHoras extras Fijas";
            }

            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SPre-Calculo de Roles");
            listaCabecera.add("S");

            listaCuerpo.add("SN. Identificación" + "¬" + "SNombres" + "¬" + "SCargo" + "¬" + "SDl"
                    + "¬" + "SSueldo" + "¬" + "SHoras Extras 50%" + "¬" + "SHoras Extras 100%" + "¬" + "SHoras Extras Extraordinarias 100%" + "¬"
                    + "SSaldo Horas Extras 50%" + "¬" + "SSaldo Horas Extras 100%" + "¬" + "SSaldo Horas Extras Extraordinarias 100%" + "¬"
                    + "SBonos%" + "¬" + "SBonos ND%" + "¬" + "SBono fijo%" + "¬" + "SBono fijo ND" + "¬"
                    + "SFondo Reserva" + "¬" + "S13er Sueldo" + "¬" + "S14to Sueldo" + "¬" + otrosIngresos + "¬" + "STotal Ingresos" + "¬"
                    + "SAnticipos" + "¬" + "SPréstamos" + "¬" + "SPréstamos Quirografarios" + "¬" + "SPréstamos Hipotecarios" + "¬" + "SIess" + "¬"
                    + "SIess Extensión Cónyuge" + "¬" + "SRetención" + "¬" + "SPermiso Mèdico" + "¬" + "SIngreso Vacaciones" + "¬" + "SDcto. Vacaciones" + "¬" + "STotal Descuentos" + "¬"
                    + "STotal a recibir" + "¬" + "SForma Pago" + "¬" + "SDocumento");
            lista.stream().forEach((rhListaDetalleRolesTO) -> {

                if (esBio && !rhListaDetalleRolesTO.getRhRol().getRhEmpleado().getEmpCancelarFondoReservaMensualmente()) {
                    if (rhListaDetalleRolesTO.getRolDescFondoReserva() != null) {
                        rhListaDetalleRolesTO.setTotalIngresos(rhListaDetalleRolesTO.getTotalIngresos().subtract(rhListaDetalleRolesTO.getRolDescFondoReserva()));
                        rhListaDetalleRolesTO.setTotalIngresos(rhListaDetalleRolesTO.getTotalDescuentos().add(rhListaDetalleRolesTO.getRolDescFondoReserva()));
                    }
                }

                listaCuerpo.add("S" + rhListaDetalleRolesTO.getRhRol().getRhEmpleado().getRhEmpleadoPK().getEmpId()
                        + "¬"
                        + "S" + rhListaDetalleRolesTO.getRhRol().getRhEmpleado().getEmpApellidos() + " " + rhListaDetalleRolesTO.getRhRol().getRhEmpleado().getEmpNombres()
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRhEmpleado().getEmpCargo() == null ? "B"
                        : "S" + rhListaDetalleRolesTO.getRhRol().getRhEmpleado().getEmpCargo())
                        + "¬"
                        + "I" + rhListaDetalleRolesTO.getRhRol().getRolDiasLaboradosReales()
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getEmpSueldo() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getEmpSueldo().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolHorasExtras() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolHorasExtras().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolHorasExtras100() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolHorasExtras100().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolHorasExtrasExtraordinarias100() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolHorasExtrasExtraordinarias100().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolSaldoHorasExtras50() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolSaldoHorasExtras50().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolSaldoHorasExtras100() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolSaldoHorasExtras100().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolSaldoHorasExtrasExtraordinarias100() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolSaldoHorasExtrasExtraordinarias100().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolBonos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolBonos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolBonosnd() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolBonosnd().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolBonoFijo() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolBonoFijo().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolBonoFijoNd() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolBonoFijoNd().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolFondoReserva() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolFondoReserva().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolXiii() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolXiii().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolXiv() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolXiv().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getEmpOtrosIngresos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getEmpOtrosIngresos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getTotalIngresos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getTotalIngresos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolAnticipos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolAnticipos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRolValorPrestamos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRolValorPrestamos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolPrestamoQuirografario() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolPrestamoQuirografario().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolPrestamoHipotecario() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolPrestamoHipotecario().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolIess() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolIess().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolIessExtension() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolIessExtension().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolRetencionFuente() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolRetencionFuente().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolDescuentoPermisoMedico() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolDescuentoPermisoMedico().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolIngresoVacaciones() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolIngresoVacaciones().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolDescuentoVacaciones() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getRhRol().getRolDescuentoVacaciones().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getTotalDescuentos() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getTotalDescuentos().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getTotalPagar() == null ? "B"
                        : "D" + rhListaDetalleRolesTO.getTotalPagar().toString())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolFormaPago() == null ? "B"
                        : "S" + rhListaDetalleRolesTO.getRhRol().getRolFormaPago())
                        + "¬"
                        + (rhListaDetalleRolesTO.getRhRol().getRolDocumento() == null ? "B"
                        : "S" + rhListaDetalleRolesTO.getRhRol().getRolDocumento()));
            });

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarPlantilla(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhRol> lista) throws Exception {
        Map<String, Object> respuesta = new HashMap<>();
        List<String> listaCabecera = new ArrayList<>();
        List<String> listaCuerpo = new ArrayList<>();
        listaCuerpo.add("SN. Identificación" + "¬" + "SNombres" + "¬" + "SCategoría" + "¬"
                + "SHoras Extras 50%" + "¬" + "SHoras Extras 100%" + "¬" + "SHoras Extras Extraordinarias 100%" + "¬" + "SDías falta" + "¬"
                + "SPermiso Médico" + "¬" + "SDías Vacaciones" + "¬" + "SDcto Vacaciones" + "¬" + "SPréstamos" + "¬" + "SPréstamos Quirografarios" + "¬"
                + "SPréstamos Hipotecarios" + "¬" + "SForma pago" + "¬" + "SDocumento" + "¬" + "SObservación");
        lista.stream().forEach((rhListaDetalleRolesTO) -> {
            listaCuerpo.add(
                    "S" + rhListaDetalleRolesTO.getRhEmpleado().getRhEmpleadoPK().getEmpId()
                    + "¬" + "S" + rhListaDetalleRolesTO.getRhEmpleado().getEmpApellidos() + " " + rhListaDetalleRolesTO.getRhEmpleado().getEmpNombres()
                    + "¬" + (rhListaDetalleRolesTO.getCatNombre() == null ? "B" : "S" + rhListaDetalleRolesTO.getCatNombre())
                    + "¬" + (rhListaDetalleRolesTO.getRolHorasExtras() == null ? "B" : "D" + rhListaDetalleRolesTO.getRolHorasExtras().toString())
                    + "¬" + (rhListaDetalleRolesTO.getRolHorasExtras100() == null ? "B" : "D" + rhListaDetalleRolesTO.getRolHorasExtras100().toString())
                    + "¬" + (rhListaDetalleRolesTO.getRolHorasExtrasExtraordinarias100() == null ? "B" : "D" + rhListaDetalleRolesTO.getRolHorasExtrasExtraordinarias100().toString())
                    + "¬" + "I" + rhListaDetalleRolesTO.getRolDiasFaltasReales()
                    + "¬" + (rhListaDetalleRolesTO.getRolDescuentoPermisoMedico() == null ? "D" + 0 : "D" + rhListaDetalleRolesTO.getRolDescuentoPermisoMedico().toString())
                    + "¬" + (rhListaDetalleRolesTO.getRolDiasVacaciones() == null ? "I" + 0 : "I" + rhListaDetalleRolesTO.getRolDiasVacaciones())
                    + "¬" + (rhListaDetalleRolesTO.getRolDescuentoVacaciones() == null ? "D" + 0 : "D" + rhListaDetalleRolesTO.getRolDescuentoVacaciones().toString())
                    + "¬" + (rhListaDetalleRolesTO.getRolPrestamos() != null ? "D" + rhListaDetalleRolesTO.getRolPrestamos() : "D" + BigDecimal.ZERO)
                    + "¬" + (rhListaDetalleRolesTO.getRolPrestamoQuirografario() != null ? "D" + rhListaDetalleRolesTO.getRolPrestamoQuirografario() : "D" + BigDecimal.ZERO)
                    + "¬" + (rhListaDetalleRolesTO.getRolPrestamoHipotecario() != null ? "D" + rhListaDetalleRolesTO.getRolPrestamoHipotecario() : "D" + BigDecimal.ZERO)
                    + "¬" + (rhListaDetalleRolesTO.getRolFormaPago() != null ? "S" + rhListaDetalleRolesTO.getRolFormaPago() : "B")
                    + "¬" + (rhListaDetalleRolesTO.getRolDocumento() != null ? "S" + rhListaDetalleRolesTO.getRolDocumento() : "B")
                    + "¬" + (rhListaDetalleRolesTO.getRolObservaciones() != null ? "S" + rhListaDetalleRolesTO.getRolObservaciones() : "B"));
        });
        respuesta.put("listaCabecera", listaCabecera);
        respuesta.put("listaCuerpo", listaCuerpo);
        return respuesta;
    }

    @Override
    public void generarArchivoOrdenBanco(RhOrdenBancariaTO ordenBancaria, String sector, HttpServletResponse response, SisInfoTO usuario, boolean esCartera, Map<String, Object> parametros, boolean sinCuenta) throws Exception { // HttpServletResponse response
        try {
            response.setContentType("text/plain");
            response.addHeader("Content-disposition", "attachment; filename=" + "mificherotemporal");
            File tempFile = File.createTempFile("mificherotemporal", ".txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
            List<String> lista = new ArrayList<>();
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("BOLIVARIANO")) {
                ordenBancaria.setBanco("BOLIVARIANO");
                List<RhPreavisoAnticiposPrestamosSueldoTO> listaBolivariano;
                if (esCartera) {
                    listaBolivariano = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoTO.class, parametros.get("listado"));
                } else {
                    listaBolivariano = anticipoService.listaOrdenesAnticipoBancoBolivariano(ordenBancaria, sector, usuario, sinCuenta);
                }
                lista = generarArchivoBolivariano(listaBolivariano, usuario);
            }
            //Banco Internacional
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("INTERNACIONAL")) {
                ordenBancaria.setBanco("INTERNACIONAL");
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichinchaInternacional;
                if (esCartera) {
                    listaPichinchaInternacional = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoPichinchaTO.class, parametros.get("listado"));
                } else {
                    listaPichinchaInternacional = anticipoService.listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
                }
                lista = generarArchivoPichinchaInternacional(listaPichinchaInternacional, ordenBancaria, usuario);
            }
            //Banco Pichincha 
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("PICHINCHA")) {
                ordenBancaria.setBanco("PICHINCHA");
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichincha;
                if (esCartera) {
                    listaPichincha = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoPichinchaTO.class, parametros.get("listado"));
                } else {
                    listaPichincha = anticipoService.listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
                }
                lista = generarArchivoPichinchaInternacional(listaPichincha, ordenBancaria, usuario);
            }
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("PRODUCCION")) {
                ordenBancaria.setBanco("PRODUCCION"); //Temporal - NUEVO METODO
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaProduccion;
                if (esCartera) {
                    listaProduccion = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoPichinchaTO.class, parametros.get("listado"));
                } else {
                    listaProduccion = anticipoService.listaOrdenesProduccion(ordenBancaria, sector, usuario);
                }
                lista = generarArchivoProduccion(listaProduccion, ordenBancaria, usuario);
            }
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("AUSTRO")) {
                ordenBancaria.setBanco("AUSTRO"); //Temporal - NUEVO METODO
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaProduccion;
                if (esCartera) {
                    listaProduccion = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoPichinchaTO.class, parametros.get("listado"));
                } else {
                    listaProduccion = anticipoService.listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
                }
                lista = generarArchivoAustro(listaProduccion, ordenBancaria, usuario);
            }
            //Banco Machala
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("MACHALA")) {
                ordenBancaria.setBanco("MACHALA");
                List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> listaMachala;
                if (esCartera) {
                    listaMachala = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoMachalaTO.class, parametros.get("listado"));
                } else {
                    listaMachala = anticipoService.listaOrdenesBancoMachala(ordenBancaria, sector, usuario);
                }
                lista = generarArchivoBancoMachala(listaMachala, ordenBancaria, usuario);
            }
            //Banco guayaquil
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("GUAYAQUIL")) {
                ordenBancaria.setBanco("GUAYAQUIL");
                List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> listaGuayaquil;
                if (esCartera) {
                    listaGuayaquil = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoGuayaquilTO.class, parametros.get("listado"));
                } else {
                    listaGuayaquil = anticipoService.listaOrdenesBancoGuayaquil(ordenBancaria, sector, usuario);
                }
                lista = generarArchivoBancoGuayaquil(listaGuayaquil, ordenBancaria, usuario);
            }
            for (String item : lista) {
                out.write(item);
            }
            out.flush();
            out.close();
            ServletOutputStream servletStream = response.getOutputStream();
            InputStream in = new FileInputStream(tempFile);
            int bit;
            while ((bit = in.read()) != -1) {
                servletStream.write(bit);
            }
            servletStream.flush();
            servletStream.close();
            in.close();
        } catch (Exception e) {
            UtilsExcepciones.enviarError(e, "OrdenesBancariasService  - generarArchivoProduccion", null);
        }
    }

    @Override
    public Map<String, Object> generarReporteOrdenBanco(RhOrdenBancariaTO ordenBancaria, String sector, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO usuario, boolean esCartera, Map<String, Object> parametros, boolean sinCuenta) throws Exception {
        try {
            Map<String, Object> respuesta = new HashMap<>();
            // GenericReporte genericReporte = new GenericReporte();
            List<String> listaCabecera = new java.util.ArrayList();
            List<String> listaCuerpo = new java.util.ArrayList();
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("BOLIVARIANO")) {
                listaCabecera.add("S" + usuarioEmpresaReporteTO.getEmpRazonSocial());
                listaCabecera.add("SCuenta: " + ordenBancaria.getNombreCuenta());
                listaCabecera.add("SFecha y hora de generación: " + UtilsValidacion.fechaSistema());
                listaCabecera.add("S");
                listaCuerpo.add("STipo registro" + "¬" + "SSecuencial" + "¬" + "SCódigo beneficiario" + "¬"
                        + "STipo beneficiario" + "¬" + "SBeneficiario" + "¬" + "SNombre beneficiario" + "¬"
                        + "SForma pago" + "¬" + "SCódigo país" + "¬" + "SCódigo banco" + "¬" + "STipo cuenta" + "¬"
                        + "SNúmero cuenta" + "¬" + "SCódigo moneda" + "¬" + "STotal pagado" + "¬" + "SConcepto" + "¬"
                        + "SCódigo bancario" + "¬" + "SCódigo servicio" + "¬" + "SMotivo pago");
                List<RhPreavisoAnticiposPrestamosSueldoTO> listaBolivariano;
                if (esCartera) {
                    listaBolivariano = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoTO.class, parametros.get("listado"));
                } else {
                    listaBolivariano = anticipoService.listaOrdenesAnticipoBancoBolivariano(ordenBancaria, sector, usuario, sinCuenta);
                }
                for (RhPreavisoAnticiposPrestamosSueldoTO rhPreavisoAntPrestSueldoTO : listaBolivariano) {
                    if (rhPreavisoAntPrestSueldoTO != null) {
                        listaCuerpo.add((rhPreavisoAntPrestSueldoTO.getPreTipoRegistro() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreTipoRegistro())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreSecuencial() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreSecuencial())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreBeneficiarioCodigo() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreBeneficiarioCodigo())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreBeneficiarioTipoId() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreBeneficiarioTipoId())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreBeneficiarioNumeroId() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreBeneficiarioNumeroId())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreBeneficiarioNombre() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreBeneficiarioNombre())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreFormaPago() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreFormaPago())
                                + "¬" + (rhPreavisoAntPrestSueldoTO.getPreCodigoPais() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreCodigoPais())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreCodigoBanco() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreCodigoBanco())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreCuentaTipo() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreCuentaTipo())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreCuentaNumero() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreCuentaNumero())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreCodigoMoneda() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreCodigoMoneda())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreTotalPagado() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreTotalPagado())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreConcepto() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreConcepto())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreCodigoBancario() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreCodigoBancario())
                                + "¬"
                                + (rhPreavisoAntPrestSueldoTO.getPreCodigoServicio() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreCodigoServicio())
                                + "¬" + (rhPreavisoAntPrestSueldoTO.getPreMotivoPago() == null ? "B"
                                : "S" + rhPreavisoAntPrestSueldoTO.getPreMotivoPago()));
                    }
                }
            }
            //Banco Internacional - Pichincha
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("INTERNACIONAL") || ordenBancaria.getNombreCuenta().toUpperCase().contains("PICHINCHA")
                    || ordenBancaria.getNombreCuenta().toUpperCase().contains("AUSTRO")) {
                listaCabecera.add("S" + usuarioEmpresaReporteTO.getEmpRazonSocial());
                listaCabecera.add("SCuenta: " + ordenBancaria.getNombreCuenta());
                listaCabecera.add("SFecha y hora de generación: " + UtilsValidacion.fechaSistema());
                listaCabecera.add("S");
                listaCuerpo.add("STipo Registro" + "¬" + "SSecuencial" + "¬" + "SMoneda" + "¬" + "STotal pagado" + "¬"
                        + "SCuenta" + "¬" + "SCuenta tipo" + "¬" + "SCuenta número" + "¬" + "SReferencia" + "¬"
                        + "STipo beneficiario" + "¬" + "SNúmero de beneficiario" + "¬" + "SNombre beneficiario" + "¬"
                        + "SCódigo banco");
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichinchaInternacional;
                if (esCartera) {
                    listaPichinchaInternacional = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoPichinchaTO.class, parametros.get("listado"));
                } else {
                    listaPichinchaInternacional = anticipoService.listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
                }
                for (RhPreavisoAnticiposPrestamosSueldoPichinchaTO rhpreavisoAnticipoPichincha : listaPichinchaInternacional) {
                    if (rhpreavisoAnticipoPichincha != null) {
                        listaCuerpo.add((rhpreavisoAnticipoPichincha.getPreTipoRegistro() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreTipoRegistro())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreSecuencial() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreSecuencial())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreMoneda() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreMoneda())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreTotalPagado() == null ? "B"
                                : "D" + Double.parseDouble(rhpreavisoAnticipoPichincha.getPreTotalPagado()) / 100)
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreCuenta() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreCuenta())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreCuentaTipo() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreCuentaTipo())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreCuentaNumero() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreCuentaNumero())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreReferencia() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreReferencia())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreBeneficiarioTipoId() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreBeneficiarioTipoId())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreBeneficiarioNumeroId() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreBeneficiarioNumeroId())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreBeneficiarioNombre() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreBeneficiarioNombre())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreCodigoBanco() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreCodigoBanco()));
                    }
                }
            }
            //Produbanco   
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("PRODUCCION")) {
                listaCabecera.add("S" + usuarioEmpresaReporteTO.getEmpRazonSocial());
                listaCabecera.add("SCuenta: " + ordenBancaria.getNombreCuenta());
                listaCabecera.add("SFecha y hora de generación: " + UtilsValidacion.fechaSistema());
                listaCabecera.add("S");
                listaCuerpo.add("STipo registro" + "¬" + "SSecuencial" + "¬" + "SMoneda" + "¬" + "STotal pagado" + "¬" + "SCuenta" + "¬"
                        + "SCuenta tipo" + "¬" + "SNúmero cuenta" + "¬" + "SReferencia" + "¬" + "SBeneficiario" + "¬" + "SNúmero beneficiario" + "¬"
                        + "SNombre beneficiario" + "¬" + "SCódigo banco");
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaProduccion2;
                if (esCartera) {
                    listaProduccion2 = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoPichinchaTO.class, parametros.get("listado"));
                } else {
                    listaProduccion2 = anticipoService.listaOrdenesProduccion(ordenBancaria, sector, usuario);
                }
                for (RhPreavisoAnticiposPrestamosSueldoPichinchaTO rhpreavisoAnticipoPichincha : listaProduccion2) {
                    if (rhpreavisoAnticipoPichincha != null) {
                        listaCuerpo.add((rhpreavisoAnticipoPichincha.getPreTipoRegistro() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreTipoRegistro())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreSecuencial() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreSecuencial())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreMoneda() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreMoneda())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreTotalPagado() == null ? "B"
                                : "S" + rellenarCeros(rhpreavisoAnticipoPichincha.getPreTotalPagado(), 13))
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreCuenta() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreCuenta())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreCuentaTipo() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreCuentaTipo())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreCuentaNumero() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreCuentaNumero())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreReferencia() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreReferencia())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreBeneficiarioTipoId() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreBeneficiarioTipoId())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreBeneficiarioNumeroId() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreBeneficiarioNumeroId())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreBeneficiarioNombre() == null ? "B"
                                : "S" + rhpreavisoAnticipoPichincha.getPreBeneficiarioNombre())
                                + "¬"
                                + (rhpreavisoAnticipoPichincha.getPreCodigoBanco() == null ? "B"
                                : "S" + rellenarCeros(rhpreavisoAnticipoPichincha.getPreCodigoBanco().trim(), 4)));
                    }
                }
            }
            //BancoMachala
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("MACHALA")) {
                listaCabecera.add("S" + usuarioEmpresaReporteTO.getEmpRazonSocial());
                listaCabecera.add("SCuenta: " + ordenBancaria.getNombreCuenta());
                listaCabecera.add("SFecha y hora de generación: " + UtilsValidacion.fechaSistema());
                listaCabecera.add("S");
                listaCuerpo.add("STipo registro" + "¬" + "SNúmero identificación" + "¬" + "SNombre beneficiario"
                        + "¬" + "SConcepto" + "¬" + "SForma Pago" + "¬" + "SCuenta tipo" + "¬" + "SNúmero cuenta"
                        + "¬" + "SMoneda" + "¬" + "SCódigo banco" + "¬" + "STotal pagado");

                List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> listamachala;
                if (esCartera) {
                    listamachala = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoMachalaTO.class, parametros.get("listado"));
                } else {
                    listamachala = anticipoService.listaOrdenesBancoMachala(ordenBancaria, sector, usuario);
                }
                for (RhPreavisoAnticiposPrestamosSueldoMachalaTO rhpreavisoAnticipoMachala : listamachala) {
                    if (rhpreavisoAnticipoMachala != null) {
                        listaCuerpo.add((rhpreavisoAnticipoMachala.getPreTipoRegistro() == null ? "B" : "S" + rhpreavisoAnticipoMachala.getPreTipoRegistro())
                                + "¬" + (rhpreavisoAnticipoMachala.getPreBeneficiarioNumeroId() == null ? "B" : "S" + rhpreavisoAnticipoMachala.getPreBeneficiarioNumeroId())
                                + "¬" + (rhpreavisoAnticipoMachala.getPreBeneficiarioNombre() == null ? "B" : "S" + rhpreavisoAnticipoMachala.getPreBeneficiarioNombre())
                                + "¬" + (rhpreavisoAnticipoMachala.getPreReferencia() == null ? "B" : "S" + rhpreavisoAnticipoMachala.getPreReferencia())
                                + "¬" + (rhpreavisoAnticipoMachala.getPreCuenta() == null ? "B" : "S" + rhpreavisoAnticipoMachala.getPreCuenta())
                                + "¬" + (rhpreavisoAnticipoMachala.getPreCuentaTipo() == null ? "B" : "S" + rhpreavisoAnticipoMachala.getPreCuentaTipo())
                                + "¬" + (rhpreavisoAnticipoMachala.getPreCuentaNumero() == null ? "B" : "S" + rhpreavisoAnticipoMachala.getPreCuentaNumero())
                                + "¬" + (rhpreavisoAnticipoMachala.getPreMoneda() == null ? "B" : "S" + rhpreavisoAnticipoMachala.getPreMoneda())
                                + "¬" + (rhpreavisoAnticipoMachala.getPreCodigoBanco() == null ? "B" : "S" + rellenarCeros(rhpreavisoAnticipoMachala.getPreCodigoBanco().trim(), 4))
                                + "¬" + (rhpreavisoAnticipoMachala.getPreTotalPagado() == null ? "B" : "S" + rellenarCeros(rhpreavisoAnticipoMachala.getPreTotalPagado(), 13))
                        );

                    }
                }
            }
            //guayaquil
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("GUAYAQUIL")) {
                listaCabecera.add("S" + usuarioEmpresaReporteTO.getEmpRazonSocial());
                listaCabecera.add("SCuenta: " + ordenBancaria.getNombreCuenta());
                listaCabecera.add("SFecha y hora de generación: " + UtilsValidacion.fechaSistema());
                listaCabecera.add("S");
                listaCuerpo.add("STipo registro" + "¬" + "SNúmero identificación" + "¬" + "SNombre beneficiario"
                        + "¬" + "SCuenta tipo" + "¬" + "SNúmero cuenta"
                        + "¬" + "SCódigo banco" + "¬" + "STotal pagado");

                List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> listado;
                if (esCartera) {
                    listado = UtilsJSON.jsonToList(RhPreavisoAnticiposPrestamosSueldoGuayaquilTO.class, parametros.get("listado"));
                } else {
                    listado = anticipoService.listaOrdenesBancoGuayaquil(ordenBancaria, sector, usuario);
                }
                for (RhPreavisoAnticiposPrestamosSueldoGuayaquilTO item : listado) {
                    if (item != null) {
                        listaCuerpo.add((item.getPreCuentaTipo() == null ? "B" : "S" + item.getPreCuentaTipo())
                                + "¬" + (item.getPreBeneficiarioNumeroId() == null ? "B" : "S" + item.getPreBeneficiarioNumeroId())
                                + "¬" + (item.getPreCuentaTitularNombre() == null ? "B" : "S" + item.getPreCuentaTitularNombre())
                                + "¬" + (item.getPreCuentaTipo() == null ? "B" : "S" + (item.getPreCuentaTipo().equals("A") ? "CTA AHORRO" : "CTA CREDITO"))
                                + "¬" + (item.getPreCuentaNumero() == null ? "B" : "S" + item.getPreCuentaNumero())
                                + "¬" + (item.getPreBancoCodigo() == null ? "B" : "S" + item.getPreBancoCodigo())
                                + "¬" + (item.getPreValor() == null ? "B" : "S" + rellenarCeros(item.getPreValor(), 15))
                        );

                    }
                }
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            UtilsExcepciones.enviarError(e, "OrdenesBancariasService  - generarArchivoProduccion", null);
            throw e;
        }
    }

    private String rellenarValores(String dato, int valorCompleto) {
        String relleno = "";
        dato = dato != null ? dato : "";
        int numeroRelleno = valorCompleto - dato.trim().length();
        for (int i = 0; i < numeroRelleno; i++) {
            relleno += " ";
        }
        return dato.trim() + relleno;
    }

    private String rellenarCeros(String dato, int valorCompleto) {
        String relleno = "";
        int numeroRelleno = valorCompleto - dato.trim().length();
        for (int i = 0; i < numeroRelleno; i++) {
            relleno += "0";
        }
        return relleno + dato.trim();
    }

    // metodos para generar archivo de orden bancario
    public List<String> generarArchivoBolivariano(List<RhPreavisoAnticiposPrestamosSueldoTO> listaBolivariano, SisInfoTO usuario) throws Exception {
        List<String> lista = new java.util.ArrayList();
        String espacio6 = "", cero15 = "", cero20 = "", espacio10 = "", espacio50 = "", espacio20 = "";
        for (int i = 0; i < 6; i++) {
            espacio6 += " ";
        }
        for (int i = 0; i < 15; i++) {
            cero15 += "0";
        }
        for (int i = 0; i < 20; i++) {
            cero20 += "0";
        }
        for (int i = 0; i < 10; i++) {
            espacio10 += " ";
        }
        for (int i = 0; i < 50; i++) {
            espacio50 += " ";
        }
        for (int i = 0; i < 20; i++) {
            espacio20 += " ";
        }
        for (RhPreavisoAnticiposPrestamosSueldoTO rhPreavisoAnticiposTO : listaBolivariano) {
            lista.add(new String((rhPreavisoAnticiposTO.getPreTipoRegistro()
                    + rellenarValores(rhPreavisoAnticiposTO.getPreSecuencial().trim(), 6)
                    + rellenarValores(rhPreavisoAnticiposTO.getPreBeneficiarioCodigo().trim(), 18)
                    + rhPreavisoAnticiposTO.getPreBeneficiarioTipoId()
                    + rellenarValores(rhPreavisoAnticiposTO.getPreBeneficiarioNumeroId().trim(), 14)
                    + rellenarValores(rhPreavisoAnticiposTO.getPreBeneficiarioNombre().trim(), 60)
                    + rhPreavisoAnticiposTO.getPreFormaPago() + rhPreavisoAnticiposTO.getPreCodigoPais()
                    + rellenarValores(rhPreavisoAnticiposTO.getPreCodigoBanco(), 2)
                    + rellenarValores(rhPreavisoAnticiposTO.getPreCuentaTipo(), 2)
                    + rellenarValores(rhPreavisoAnticiposTO.getPreCuentaNumero(), 20)
                    + rhPreavisoAnticiposTO.getPreCodigoMoneda() + rhPreavisoAnticiposTO.getPreTotalPagado()
                    + rellenarValores(rhPreavisoAnticiposTO.getPreConcepto().trim(), 60) + cero15 + cero15 + cero15
                    + cero20 + espacio10 + espacio50 + espacio50 + espacio20
                    + rhPreavisoAnticiposTO.getPreCodigoServicio() + espacio10 + espacio10 + espacio10 + " "
                    + rellenarValores(rhPreavisoAnticiposTO.getPreCodigoBancario().trim(), 5) + espacio6
                    + rhPreavisoAnticiposTO.getPreMotivoPago() + "\r\n").getBytes("ASCII"), "ISO-8859-1"));
        }
        return lista;
    }

    //bancoPichincha
    public List<String> generarArchivoPichinchaInternacional(List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichinchaInternacional, RhOrdenBancariaTO ordenBancaria, SisInfoTO usuario) throws Exception {
        List<String> lista = new java.util.ArrayList();
        for (RhPreavisoAnticiposPrestamosSueldoPichinchaTO rhPreavisoAnticiposPichinchaTO : listaPichinchaInternacional) {
            if (rhPreavisoAnticiposPichinchaTO != null && rhPreavisoAnticiposPichinchaTO.getPreTipoRegistro() != null) {
                lista.add(new String(
                        (rhPreavisoAnticiposPichinchaTO.getPreTipoRegistro() + "\t"
                                + (ordenBancaria.getBanco().equals("Pichincha") ? rhPreavisoAnticiposPichinchaTO.getPreSecuencial().trim()
                                : rhPreavisoAnticiposPichinchaTO.getPreCodigo().trim())
                                + "\t" + rhPreavisoAnticiposPichinchaTO.getPreMoneda().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreTotalPagado().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreCuenta().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreCuentaTipo().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreCuentaNumero().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreReferencia() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioTipoId() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioNumeroId().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioNombre().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreCodigoBanco().trim() + "\r\n").getBytes("ASCII"),
                        "ISO-8859-1"));
            }
        }
        return lista;
    }

    //bancoMachala
    public List<String> generarArchivoBancoMachala(List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> listaBancomachala, RhOrdenBancariaTO ordenBancaria, SisInfoTO usuario) throws Exception {
        //variables para concatenar al archivo txt
        String prefBeneficiario = "DET00000";
        String prefEc = "EC";
        String prefNombre = "0000";
        String prefTipoCuenta = "10"; //
        String prefMontoDetalle = "000000000000000000000000000000000000000";
        String codigoFinal = "06148"; //codigofijo
        Integer numCorrelativo = 0;
        List<String> lista = new java.util.ArrayList();
        String espacio30 = "", espacio10 = "", espacio50 = "";
        for (int i = 0; i < 30; i++) {
            espacio30 += " ";
        }
        for (int i = 0; i < 10; i++) {
            espacio10 += " ";
        }
        for (int i = 0; i < 50; i++) {
            espacio50 += " ";
        }
        for (RhPreavisoAnticiposPrestamosSueldoMachalaTO rhPreavisoAnticipoMachalaTO : listaBancomachala) {
            if (rhPreavisoAnticipoMachalaTO != null && rhPreavisoAnticipoMachalaTO.getPreTipoRegistro() != null) {
                lista.add(new String((prefBeneficiario + numCorrelativo + rhPreavisoAnticipoMachalaTO.getPreBeneficiarioNumeroId() //DET000IDENTIFICACION  
                        + "\t" + rhPreavisoAnticipoMachalaTO.getPreCuenta().trim().concat(prefEc).trim() + " " + rhPreavisoAnticipoMachalaTO.getPreCodigoBanco().trim() + " " + prefTipoCuenta + rhPreavisoAnticipoMachalaTO.getPreCuentaNumero().trim() //CUENTA COD BANCO
                        + espacio10 + prefNombre + rhPreavisoAnticipoMachalaTO.getPreBeneficiarioNombre()//000nombre
                        + espacio10 + rhPreavisoAnticipoMachalaTO.getPreBeneficiarioNumeroId()//identificacion
                        + espacio30 + rhPreavisoAnticipoMachalaTO.getPreBeneficiarioTipoId().concat(prefMontoDetalle).concat(rhPreavisoAnticipoMachalaTO.getPreTotalPagado().trim().concat(rhPreavisoAnticipoMachalaTO.getPreMoneda()).concat(rhPreavisoAnticipoMachalaTO.getPreBeneficiarioNombre()))
                        + "\t" + espacio10 + rhPreavisoAnticipoMachalaTO.getPreBeneficiarioTipoId().concat(rhPreavisoAnticipoMachalaTO.getPreBeneficiarioNumeroId()).trim() //c+identificacion
                        + "\t" + espacio50 + espacio50 + codigoFinal
                        + "\r\n").getBytes("ASCII"), "ISO-8859-1"));
            }
        }
        return lista;
    }

    public List<String> generarArchivoBancoGuayaquil(List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> listaBancomachala, RhOrdenBancariaTO ordenBancaria, SisInfoTO usuario) throws Exception {
        //variables para concatenar al archivo txt
        List<String> lista = new java.util.ArrayList();
        String espacioEmailCorreo = "";
        String espacio = "";
        for (int i = 0; i < 40; i++) {
            espacioEmailCorreo += " ";
        }
        for (RhPreavisoAnticiposPrestamosSueldoGuayaquilTO item : listaBancomachala) {
            if (item != null && item.getPreCuentaTipo() != null) {
                String numeroCuenta = item.getPreCuentaNumero().trim();
                String valorSinDecimales = item.getPreValor().trim();
                String nombreTitular = item.getPreCuentaTitularNombre();
                String cadenaCeros = "";
                if (item.getPreBeneficiarioCuentaNombre().toUpperCase().contains("GUAYAQUIL")) {
                    for (int i = 0; i < (10 - numeroCuenta.length()); i++) {
                        cadenaCeros = cadenaCeros.concat("0");
                    }
                } else {
                    for (int i = 0; i < (18 - numeroCuenta.length()); i++) {
                        cadenaCeros = cadenaCeros.concat("0");
                    }
                }
                numeroCuenta = cadenaCeros.concat(numeroCuenta);
                cadenaCeros = "";
                for (int i = 0; i < (15 - valorSinDecimales.length()); i++) {
                    cadenaCeros = cadenaCeros.concat("0");
                }
                valorSinDecimales = cadenaCeros.concat(valorSinDecimales);
                espacio = "";
                if (nombreTitular.length() > 18) {
                    nombreTitular = nombreTitular.substring(0, 18);
                } else {
                    for (int i = 0; i < (18 - nombreTitular.length()); i++) {
                        espacio = espacio.concat(" ");
                    }
                    nombreTitular = nombreTitular.concat(espacio);
                }
                lista.add(new String(
                        (item.getPreCuentaTipo() // char 1
                                + (numeroCuenta.length() == 10 ? numeroCuenta : "0000000000")// si es 10 es porque es guayaquil // char 10
                                + valorSinDecimales // char 15
                                + item.getPreMotivo()// char 2
                                + item.getPreTipoNota() // char 1
                                + item.getPreAgencia() // char 2
                                + (numeroCuenta.length() == 10 ? "  " : (item.getPreCodigoPagoInterbancario() != null && item.getPreCodigoPagoInterbancario().length() == 2 ? item.getPreCodigoPagoInterbancario() : "XX")) // char 2
                                + (numeroCuenta.length() == 10 ? "                  " : numeroCuenta) // char 18
                                + nombreTitular //char 18
                                + item.getPreMotivoNuevo() //char 3
                                + espacioEmailCorreo //char 40
                                + (numeroCuenta.length() == 10 ? "" : item.getPreBancoCodigo()) //char 3
                                + (numeroCuenta.length() == 10 ? "" : item.getPreBeneficiarioTipoId().concat(item.getPreBeneficiarioNumeroId()))//char 14
                                + "\r\n").getBytes("ASCII"),
                        "ISO-8859-1"));
//                lista.add(
//                        new String(
//                                (item.getPreCuentaTipo() // char 1
//                                        + convertirAEspacios(numeroCuenta, 10, true, "0")// si es 10 es porque es guayaquil // char 10
//                                        + valorSinDecimales // char 15
//                                        + item.getPreMotivo()// char 2
//                                        + item.getPreTipoNota() // char 1
//                                        + item.getPreAgencia() // char 2
//                                        + (item.getPreCodigoPagoInterbancario() != null && item.getPreCodigoPagoInterbancario().length() == 2 ? item.getPreCodigoPagoInterbancario() : "XX") // char 2
//                                        + convertirAEspacios(numeroCuenta, 18, true, "0") // char 18
//                                        + nombreTitular + "AEA" //char 18 + AEA
//                                        + convertirAEspacios("", 30, true, " ") //char 30
//                                        + convertirAEspacios(item.getPreBeneficiarioTelefono(), 10, true, "0") //char 10
//                                        + convertirAEspacios(item.getPreBancoCodigo(), 3, true, "0") //char 3
//                                        + convertirAEspacios(item.getPreBeneficiarioTipoId().concat(item.getPreBeneficiarioNumeroId()), 14, false, " ")//char 14
//                                        + "\r\n").getBytes("ASCII"),
//                                "ISO-8859-1")
//                );

            }
        }
        return lista;
    }

    public String convertirAEspacios(String campo, int longitudTotalDeEspacios, boolean ponerEspaciosALaIzquierda, String valorParaCompletar) {
        String valorConEspacios = "";
        campo = campo != null ? campo.trim() : "";
        int longitudDelCampo = campo.length();
        int espaciosQueDeboAgregar = longitudTotalDeEspacios - longitudDelCampo;
        if (!ponerEspaciosALaIzquierda) {
            for (int i = 0; i < espaciosQueDeboAgregar; i++) {
                valorConEspacios += valorParaCompletar;
            }
            valorConEspacios = campo + valorConEspacios;
        } else {
            for (int i = 0; i < espaciosQueDeboAgregar; i++) {
                valorConEspacios += valorParaCompletar;
            }
            valorConEspacios = valorConEspacios + campo;
        }
        if (valorConEspacios.length() > longitudTotalDeEspacios) {
            valorConEspacios = valorConEspacios.substring(0, longitudTotalDeEspacios);
        }
        return valorConEspacios;
    }

    public List<String> generarArchivoProduccion(List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaProduccion, RhOrdenBancariaTO ordenBancaria, SisInfoTO usuario) throws Exception {
        List<String> lista = new java.util.ArrayList();
        boolean esExcamecor = ordenBancaria.getEmpresa().equals("SA17") ? true : false;
        for (RhPreavisoAnticiposPrestamosSueldoPichinchaTO rhPreavisoAnticiposPichinchaTO : listaProduccion) {
            if (rhPreavisoAnticiposPichinchaTO != null && rhPreavisoAnticiposPichinchaTO.getPreTipoRegistro() != null) {
                int longitud = 10;
                if (rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioTipoId().equals("R") || rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioTipoId().equals("P")) {
                    longitud = 13;
                }
                lista.add(new String(
                        (convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreTipoRegistro().trim(), 2, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreCuentaEmpresa().trim(), 11, false, "0") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreSecuencial().trim(), 7, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreContable().trim(), 20, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioNumeroId().trim(), 20, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreMoneda().trim(), 3, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreTotalPagado().trim(), 13, true, "0") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreCuenta().trim(), 3, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreCodigoBanco().trim(), 15, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreCuentaTipo().trim(), 3, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreCuentaNumero().trim(), 30, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioTipoId().trim(), 1, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioNumeroId().trim(), longitud, false, " ") + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioNombre().trim(), 40, false, " ") + "\t"
                                + "\t"
                                + "\t"
                                + "\t"
                                + "\t"
                                + convertirAEspacios(rhPreavisoAnticiposPichinchaTO.getPreReferencia(), (esExcamecor ? 15 : 100), false, " ")
                                + "\t"
                                + "\r\n").getBytes("ASCII"),
                        "ISO-8859-1"));
            }
        }
        return lista;
    }

    public List<String> generarArchivoAustro(List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaAustro, RhOrdenBancariaTO ordenBancaria, SisInfoTO usuario) throws Exception {
        List<String> lista = new java.util.ArrayList();
        for (RhPreavisoAnticiposPrestamosSueldoPichinchaTO rhPreavisoAnticiposPichinchaTO : listaAustro) {
            if (rhPreavisoAnticiposPichinchaTO != null && rhPreavisoAnticiposPichinchaTO.getPreTipoRegistro() != null) {
                lista.add(new String(
                        ("PA" + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioTipoId().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioNumeroId().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioNumeroId().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreBeneficiarioNombre().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreCuentaTipo().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreCuentaNumero().trim() + "\t"
                                + rhPreavisoAnticiposPichinchaTO.getPreMoneda().trim() + "\t"
                                + rellenarCeros(rhPreavisoAnticiposPichinchaTO.getPreTotalPagado().trim(), 13) + "\t"
                                + rellenarCeros(rhPreavisoAnticiposPichinchaTO.getPreCodigoBanco().trim(), 4) + "\t"
                                + "\r\n").getBytes("ASCII"),
                        "ISO-8859-1"));
            }
        }
        return lista;
    }

    @Override
    public byte[] generarReporteComprobanteRolLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String empresa, List<RhListaDetalleRolesTO> listaDetalleRolesTO) throws Exception {
        List<ReportesRol> listaRol = new ArrayList<>();
        for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
            List<RhListaRolSaldoEmpleadoDetalladoTO> detalleList = rolService.getRhRolSaldoEmpleadoDetalladoTO(empresa, itemDetalle.getLrpId(), itemDetalle.getLrpDesde(), itemDetalle.getLrpHasta());
            convertirValoresItemDetalleVaciosACero(itemDetalle);
            if (detalleList == null || detalleList.isEmpty()) {
                listaRol.add(new ReportesRol(itemDetalle, "", "", "", "", "", null, "", ""));
            } else {
                for (RhListaRolSaldoEmpleadoDetalladoTO detalle : detalleList) {
                    listaRol.add(new ReportesRol(itemDetalle, detalle.getSedConcepto(), detalle.getSedDetalle(), detalle.getSedCp(), detalle.getSedCc(), detalle.getSedFecha(), detalle.getSedValor(),
                            detalle.getSedObservaciones(), ""));
                }
            }
        }
        return generarReporteRol(usuarioEmpresaReporteTO, listaRol);
    }

    @Override
    public byte[] generarReporteComprobanteColectivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ItemStatusItemListaConContableTO> listado) throws Exception {
        byte[] respuesta = null;
        ItemStatusItemListaConContableTO con;
        if (listado != null && !listado.isEmpty()) {
            con = listado.get(0);
        } else {
            return respuesta;
        }
        switch (TipoRRHH.getTipoRRHH(con.getConReferencia())) {
            case ANTICIPO:
                List<ReporteConsultaAnticiposLote> list = new ArrayList<>();
                List<RhListaDetalleAnticiposLoteTO> listAnticipo = anticipoService.getRhDetalleAnticiposLoteTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
                for (RhListaDetalleAnticiposLoteTO ant : listAnticipo) {
                    list.add(new ReporteConsultaAnticiposLote(con.getPerCodigo(), con.getTipCodigo(),
                            con.getConNumero(), ant.getDalFecha() == null ? "" : ant.getDalFecha(),
                            ant.getDalId() == null ? "" : ant.getDalId(),
                            ant.getDalNombres() == null ? "" : ant.getDalNombres(), ant.getDalValor(),
                            listAnticipo.get(0).getDalFormaPagoDetalle(), listAnticipo.get(0).getDalDocumento(),
                            listAnticipo.get(0).getDalObservaciones()));
                }
                respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteAnticipoPorLoteColectivo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), list);
                break;
            case PRESTAMO:
                List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
                RhPrestamo rhPrestamo = prestamoService.getListRhPrestamo(con.toConContablePK()).get(0);
                lista.add(new ReporteAnticipoPrestamoXIIISueldo(
                        con.getEmpCodigo() + " | " + con.getPerCodigo() + " | " + con.getTipCodigo() + " | " + con.getConNumero(),
                        con.getConFecha(),
                        rhPrestamo.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                        rhPrestamo.getRhEmpleado().getEmpApellidos() + " " + rhPrestamo.getRhEmpleado().getEmpNombres(),
                        rhPrestamo.getRhEmpleado().getEmpCargo(),
                        rhPrestamo.getRhEmpleado().getPrdSector().getSecNombre(),
                        rhPrestamo.getPreValor(),
                        rhPrestamo.getConCuentas().getCtaDetalle(),
                        rhPrestamo.getPreDocumento(),
                        rhPrestamo.getPreObservaciones() == null ? "" : rhPrestamo.getPreObservaciones().toUpperCase()));
                respuesta = genericReporteService.generarReporte(modulo, "reportComprobantePrestamo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
                break;
            case ROL:
                String fechaDesde = "";
                String fechaHasta = "";
                List<RhListaDetalleRolesTO> listaDetalleRolesTO = rolService.getRhSoporteContableRolesTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
                List<ReportesRol> lis = new ArrayList<>();
                for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                    convertirValoresItemDetalleVaciosACero(itemDetalle);
                    itemDetalle.setLrpCargo(itemDetalle.getLrpCargo() == null ? "" : itemDetalle.getLrpCargo());
                    itemDetalle.setLrpDocumento(itemDetalle.getLrpDocumento() == null ? "" : itemDetalle.getLrpDocumento());
                    itemDetalle.setLrpContable(itemDetalle.getLrpContable() == null ? "" : itemDetalle.getLrpContable());
                    if (itemDetalle.getLrpDesde() != null && !itemDetalle.getLrpDesde().isEmpty()) {
                        fechaDesde = itemDetalle.getLrpDesde();
                    }
                    if (itemDetalle.getLrpHasta() != null && !itemDetalle.getLrpHasta().isEmpty()) {
                        fechaHasta = itemDetalle.getLrpHasta();
                    }
                    ReportesRol reportesRol = new ReportesRol(itemDetalle, "", "", "", "", "", null, "", "");
                    lis.add(reportesRol);
                }
                for (ReportesRol item : lis) {
                    if (item.getRolDesde() == null || item.getRolDesde().isEmpty()) {
                        item.setRolDesde(fechaDesde);
                    }
                    if (item.getRolHasta() == null || item.getRolHasta().isEmpty()) {
                        item.setRolHasta(fechaHasta);
                    }
                }
                respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteRolesEnLote.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lis);
                break;
            case LIQUIDACION:
                List<RhRol> listLiquidacion = rolService.getListRhRol(con.toConContablePK());
                List<ReportesRol> listaLiquidacion = new ArrayList<>();
                if (listLiquidacion != null && !listLiquidacion.isEmpty()) {
                    List<RhListaDetalleRolesTO> listaDetalleRoles = new ArrayList<>();
                    for (RhRol itemRol : listLiquidacion) {
                        listaDetalleRoles.addAll(eliminarDetallesRolesNulos(rolService.getRhDetalleRolesTO(
                                con.getEmpCodigo(),
                                con.getConFecha(),
                                con.getConFecha(),
                                itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(),
                                itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                                itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                                "LIQUIDACIONES"), false, null));
                    }
                    for (RhRol itemRol : listLiquidacion) {
                        for (RhListaDetalleRolesTO itemDetalle : listaDetalleRoles) {
                            convertirValoresItemDetalleVaciosACero(itemDetalle);
                            listaLiquidacion.add(new ReportesRol(itemDetalle, "", "", "", "", "", null, "",
                                    itemRol.getPrdSector().getPrdSectorPK().getSecCodigo()));
                        }
                    }
                    respuesta = genericReporteService.generarReporte(modulo, "reportListadoLiquidacion.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listaLiquidacion);
                }
                break;
            case UTILIDAD:
                List<ReporteConsultaAnticiposLote> listU = new ArrayList<>();
                List<RhListaDetalleUtilidadesLoteTO> listaUtilidades = utilidadService.getRhDetalleUtilidadesLoteTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
                for (RhListaDetalleUtilidadesLoteTO ant : listaUtilidades) {
                    ReporteConsultaAnticiposLote obj = new ReporteConsultaAnticiposLote(con.getPerCodigo(), con.getTipCodigo(),
                            con.getConNumero(), ant.getUtilFecha() == null ? "" : ant.getUtilFecha(),
                            ant.getUtilId() == null ? "" : ant.getUtilId(),
                            ant.getUtilNombres() == null ? "" : ant.getUtilNombres(), ant.getUtilValor(),
                            listaUtilidades.get(0).getUtilFormaPagoDetalle(), listaUtilidades.get(0).getUtilDocumento(),
                            listaUtilidades.get(0).getUtilObservaciones());

                    obj.setDalValor(obj.getDalValor() == null ? BigDecimal.ZERO : obj.getDalValor());
                    obj.setDalImpuestoRenta(ant.getUtilImpuestoRenta());
                    obj.setDalImpuestoRenta(obj.getDalImpuestoRenta() == null ? BigDecimal.ZERO : obj.getDalImpuestoRenta());
                    BigDecimal valorARecibir = obj.getDalValor().subtract(obj.getDalImpuestoRenta());
                    obj.setDalValorARecibir(valorARecibir);
                    listU.add(obj);
                }
                respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteUtilidadesLotes.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listU);
                break;
        }
        return respuesta;
    }

    @Override
    public Map<String, Object> listarSoporteBeneficios(String empresa, String periodo, String tipo, String numero) throws Exception {
        Map<String, Object> resultado = new HashMap<>();
        ConContable contable = contableService.obtenerPorId(empresa, periodo, tipo, numero);
        if (contable != null) {
            switch (TipoRRHH.getTipoRRHH(contable.getConReferencia())) {
                case UTILIDAD:
                    List<RhUtilidades> utilidades = utilidadService.getListRhUtilidades(contable.getConContablePK());
                    resultado.put("utilidades", utilidades);
                    break;
                case XIIISUELDO:
                    List<RhXiiiSueldo> xii = xiiiSueldoService.getListRhXiiiSueldo(contable.getConContablePK());
                    resultado.put("xii", xii);
                    break;
                case XIVSUELDO:
                    List<RhXivSueldo> xiv = xivSueldoService.getListRhXivSueldo(contable.getConContablePK());
                    resultado.put("xiv", xiv);
                    break;
            }
            resultado.put("tipo", contable.getConReferencia());
        }
        return resultado;
    }

    //reportesoporte
    @Override
    public byte[] generarReporteSoporte(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ItemStatusItemListaConContableTO> listado) throws Exception {
        byte[] respuesta = null;
        String tipoReferencia = null;
        ItemStatusItemListaConContableTO con;
        if (listado != null && !listado.isEmpty()) {
            tipoReferencia = listado.get(0).getConReferencia();
            List<ReporteConsultaAnticiposLote> list = new ArrayList<>();
            List<ReporteConsultaHorasExtrasLote> reporteConsultaHorasExtrasLotes = new ArrayList<>();
            List<ReporteConsultaBonosLote> reporteConsultaBonosLotes = new ArrayList<>();
            List<ReportesRol> lis = new ArrayList<>();
            List<ReporteConsultaAnticiposLote> listaImprimir = new ArrayList<>();
            List<ReporteXiiiSueldoConsulta> lista = new ArrayList<>();
            List<ReporteXivSueldoConsulta> lista2 = new ArrayList<>();
            List<ReporteProvisionesComprobanteContable> listaProv = new ArrayList<>();
            //--

            for (ItemStatusItemListaConContableTO cont : listado) {
                con = cont;
                switch (TipoRRHH.getTipoRRHH(con.getConReferencia())) {
                    case ANTICIPO:
                        List<RhListaDetalleAnticiposLoteTO> listAnticipo = anticipoService.getRhDetalleAnticiposLoteTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
                        for (RhListaDetalleAnticiposLoteTO ant : listAnticipo) {
                            list.add(new ReporteConsultaAnticiposLote(con.getPerCodigo(), con.getTipCodigo(),
                                    con.getConNumero(), ant.getDalFecha() == null ? "" : ant.getDalFecha(),
                                    ant.getDalId() == null ? "" : ant.getDalId(),
                                    ant.getDalNombres() == null ? "" : ant.getDalNombres(), ant.getDalValor(),
                                    listAnticipo.get(0).getDalFormaPagoDetalle(), listAnticipo.get(0).getDalDocumento(),
                                    listAnticipo.get(0).getDalObservaciones()));
                        }
                        break;
                    case HORAS_EXTRAS:
                        List<RhListaDetalleHorasExtrasLoteTO> listaHE2 = horasExtrasService.getRhDetalleHorasExtrasLoteTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
                        for (RhListaDetalleHorasExtrasLoteTO rldvpg : listaHE2) {
                            int i = 1;
                            ReporteConsultaHorasExtrasLote reporteConsultaHELote = new ReporteConsultaHorasExtrasLote();
                            reporteConsultaHELote.setPeriodo(con.getPerCodigo());
                            reporteConsultaHELote.setTipo(con.getTipCodigo());
                            reporteConsultaHELote.setNumero(con.getConNumero());
                            reporteConsultaHELote.setDblFecha(rldvpg.getDblFecha() == null ? "" : rldvpg.getDblFecha());
                            reporteConsultaHELote.setDblId(rldvpg.getDblId() == null ? "" : rldvpg.getDblId());
                            reporteConsultaHELote.setDblNombres(rldvpg.getDblNombres() == null ? "" : rldvpg.getDblNombres());
                            reporteConsultaHELote.setDblValor50(rldvpg.getDblValor50() == null ? null : rldvpg.getDblValor50());
                            reporteConsultaHELote.setDblValor100(rldvpg.getDblValor100() == null ? null : rldvpg.getDblValor100());
                            reporteConsultaHELote.setDblValorExtraordinarias100(rldvpg.getDblValorExtraordinarias100() == null ? null : rldvpg.getDblValorExtraordinarias100());
                            reporteConsultaHELote.setFormaPago(rldvpg.getDblFormaPagoDetalle() == null ? null : rldvpg.getDblFormaPagoDetalle());
                            reporteConsultaHELote.setDocumento(rldvpg.getDblDocumento() == null ? null : rldvpg.getDblDocumento());
                            reporteConsultaHELote.setObservaciones(rldvpg.getDblObservaciones() == null ? "¬¬¬¬" : rldvpg.getDblObservaciones());
                            reporteConsultaHorasExtrasLotes.add(reporteConsultaHELote);
                            if (i == listaHE2.size()) {
                                break;
                            }
                            i++;
                        }
                        break;
                    case BONO:
                        List<RhListaDetalleBonosLoteTO> listaBonos2 = bonoService.getRhDetalleBonosLoteTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
                        int i = 1;
                        for (RhListaDetalleBonosLoteTO rldvpg : listaBonos2) {

                            ReporteConsultaBonosLote reporteConsultaBonosLote = new ReporteConsultaBonosLote();
                            reporteConsultaBonosLote.setPeriodo(con.getPerCodigo());
                            reporteConsultaBonosLote.setTipo(con.getTipCodigo());
                            reporteConsultaBonosLote.setNumero(con.getConNumero());
                            reporteConsultaBonosLote.setDblFecha(rldvpg.getDblFecha() == null ? "" : rldvpg.getDblFecha());
                            reporteConsultaBonosLote.setDblId(rldvpg.getDblId() == null ? "" : rldvpg.getDblId());
                            reporteConsultaBonosLote.setDblNombres(rldvpg.getDblNombres() == null ? "" : rldvpg.getDblNombres());
                            reporteConsultaBonosLote.setDblValor(rldvpg.getDblValor() == null ? null : rldvpg.getDblValor());
                            reporteConsultaBonosLote.setFormaPago(rldvpg.getDblFormaPagoDetalle() == null ? null : rldvpg.getDblFormaPagoDetalle());
                            reporteConsultaBonosLote.setDocumento(rldvpg.getDblDocumento() == null ? null : rldvpg.getDblDocumento());
                            reporteConsultaBonosLote.setObservaciones(rldvpg.getDblObservaciones() == null ? "¬¬¬¬" : rldvpg.getDblObservaciones());
                            reporteConsultaBonosLotes.add(reporteConsultaBonosLote);
                            if (i == listaBonos2.size()) {
                                break;
                            }
                            i++;
                        }
                        break;
                    case ROL:
                        List<RhListaDetalleRolesTO> listaDetalleRolesTO = rolService.getRhSoporteContableRolesTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
                        for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                            convertirValoresItemDetalleVaciosACero(itemDetalle);
                            ReportesRol reportesRol = new ReportesRol(itemDetalle, "", "", "", "", "", null, "", "");
                            lis.add(reportesRol);
                        }
                        break;
                    case LIQUIDACION:
                        List<RhRol> listLiquidacion = rolService.getListRhRol(con.toConContablePK());
                        List<RhListaDetalleRolesTO> listaDetalleRoles = new ArrayList<>();
                        for (RhRol itemRol : listLiquidacion) {
                            listaDetalleRoles.addAll(eliminarDetallesRolesNulos(rolService.getRhDetalleRolesTO(
                                    con.getEmpCodigo(),
                                    con.getConFecha(),
                                    con.getConFecha(),
                                    itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(),
                                    itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                                    itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                                    "LIQUIDACIONES"), false, null));
                        }
                        for (RhRol itemRol : listLiquidacion) {
                            for (RhListaDetalleRolesTO itemDetalle : listaDetalleRoles) {
                                convertirValoresItemDetalleVaciosACero(itemDetalle);
                                lis.add(new ReportesRol(itemDetalle, "", "", "", "", "", null, "",
                                        itemRol.getPrdSector().getPrdSectorPK().getSecCodigo()));
                            }
                        }
                        respuesta = genericReporteService.generarReporte(modulo, "reportSoporteRolesLiquidacionEnLote.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lis);

                        break;
                    case UTILIDAD:
                        List<RhListaDetalleUtilidadesLoteTO> listaUtilidades = utilidadService.getRhDetalleUtilidadesLoteTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
                        for (RhListaDetalleUtilidadesLoteTO ant : listaUtilidades) {
                            ReporteConsultaAnticiposLote obj = new ReporteConsultaAnticiposLote(con.getPerCodigo(), con.getTipCodigo(),
                                    con.getConNumero(), ant.getUtilFecha() == null ? "" : ant.getUtilFecha(),
                                    ant.getUtilId() == null ? "" : ant.getUtilId(),
                                    ant.getUtilNombres() == null ? "" : ant.getUtilNombres(), ant.getUtilValor(),
                                    listaUtilidades.get(0).getUtilFormaPagoDetalle(), listaUtilidades.get(0).getUtilDocumento(),
                                    listaUtilidades.get(0).getUtilObservaciones());

                            obj.setDalValor(obj.getDalValor() == null ? BigDecimal.ZERO : obj.getDalValor());
                            obj.setDalImpuestoRenta(ant.getUtilImpuestoRenta());
                            obj.setDalImpuestoRenta(obj.getDalImpuestoRenta() == null ? BigDecimal.ZERO : obj.getDalImpuestoRenta());
                            BigDecimal valorARecibir = obj.getDalValor().subtract(obj.getDalImpuestoRenta());
                            obj.setDalValorARecibir(valorARecibir);
                            listaImprimir.add(obj);
                        }
                        break;
                    case XIIISUELDO:
                        List<RhXiiiSueldo> listXiiiSueldo = xiiiSueldoService.getListRhXiiiSueldo(con.toConContablePK());
                        for (RhXiiiSueldo xiiiSueldo : listXiiiSueldo) {
                            xiiiSueldo.setXiiiAuxiliar(true);
                            xiiiSueldo.getRhEmpleado().setEmpDiasDescanso(xiiiSueldo.getXiiiDiasLaborados());
                            xiiiSueldo.getRhEmpleado().setEmpBonoFijo(xiiiSueldo.getXiiiBaseImponible());
                            xiiiSueldo.getRhEmpleado().setEmpBonoFijoNd(xiiiSueldo.getXiiiValor());
                        }
                        String sector = listXiiiSueldo.size() > 0 && listXiiiSueldo.get(0).getPrdSector() != null ? listXiiiSueldo.get(0).getPrdSector().getPrdSectorPK().getSecCodigo() : "";
                        RhXiiiSueldoPeriodoTO rhXiiiSueldoPeriodoTO = obtenerRhXiiiSueldoPeriodoTO(con.getEmpCodigo(), UtilsDate.fechaFormatoDate(con.getConFecha(), "yyyy-MM-dd"));
                        listXiiiSueldo.stream().map((rldvpg) -> {
                            ReporteXiiiSueldoConsulta reporteXiiiSueldoConsulta = new ReporteXiiiSueldoConsulta();
                            reporteXiiiSueldoConsulta.setSector(sector);
                            reporteXiiiSueldoConsulta.setPeriodo(rldvpg.getConContable() == null ? "" : rldvpg.getConContable().getConContablePK().getConPeriodo());
                            reporteXiiiSueldoConsulta.setFechaDesde(rhXiiiSueldoPeriodoTO.getXiiiDesde());
                            reporteXiiiSueldoConsulta.setFechaHasta(rhXiiiSueldoPeriodoTO.getXiiiHasta());
                            reporteXiiiSueldoConsulta.setFechaMaxima(rhXiiiSueldoPeriodoTO.getXiiiFechaMaximaPago());
                            reporteXiiiSueldoConsulta.setXiiiCategoria((rldvpg.getRhEmpleado().getRhCategoria() == null ? "" : rldvpg.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre()));
                            reporteXiiiSueldoConsulta.setXiiiSector((rldvpg.getPrdSector() == null ? "" : rldvpg.getPrdSector().getSecNombre()));
                            reporteXiiiSueldoConsulta.setXiiiId((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getRhEmpleadoPK().getEmpId()));
                            reporteXiiiSueldoConsulta.setXiiiNombres((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpNombres().trim()));
                            reporteXiiiSueldoConsulta.setXiiiApellidos((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpApellidos().trim()));
                            reporteXiiiSueldoConsulta.setXiiiGenero((rldvpg.getRhEmpleado() == null ? null : rldvpg.getRhEmpleado().getEmpGenero()));
                            reporteXiiiSueldoConsulta.setXiiiFechaIngreso((rldvpg.getEmpFechaIngreso() == null ? "" : UtilsDate.fechaFormatoString(rldvpg.getEmpFechaIngreso(), "yyyy-MM-dd")));
                            reporteXiiiSueldoConsulta.setXiiiCargo((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpCargo()));
                            reporteXiiiSueldoConsulta.setXiiiTotalIngresos((rldvpg.getXiiiBaseImponible() == null ? null : rldvpg.getXiiiBaseImponible()));
                            reporteXiiiSueldoConsulta.setXiiiDiasLaborados((rldvpg.getXiiiDiasLaborados() == null ? null : rldvpg.getXiiiDiasLaborados().shortValue()));
                            reporteXiiiSueldoConsulta.setXiiiValorXiiiSueldo((rldvpg.getXiiiValor() == null ? null : rldvpg.getXiiiValor()));
                            reporteXiiiSueldoConsulta.setXiiiCodigoMinisterial((rldvpg.getXiiiCodigoMinisterial() == null
                                    || rldvpg.getXiiiCodigoMinisterial().compareToIgnoreCase("") == 0 ? null
                                    : rldvpg.getXiiiCodigoMinisterial().charAt(0)));
                            reporteXiiiSueldoConsulta.setXiiiNumero((rldvpg.getConContable() == null ? "" : rldvpg.getConContable().getConContablePK().getConNumero()));//
                            return reporteXiiiSueldoConsulta;
                        }).forEachOrdered((reporteXiiiSueldoConsulta) -> {
                            lista.add(reporteXiiiSueldoConsulta);
                        });
                        break;
                    case XIVSUELDO:
                        List<RhXivSueldo> listXivSueldo = xivSueldoService.getListRhXivSueldo(con.toConContablePK());
                        for (RhXivSueldo xivSueldo : listXivSueldo) {
                            xivSueldo.setXivAuxiliar(true);
                            xivSueldo.getRhEmpleado().setEmpDiasDescanso(xivSueldo.getXivDiasLaboradosEmpleado());
                            xivSueldo.getRhEmpleado().setEmpBonoFijo(xivSueldo.getXivBaseImponible());
                            xivSueldo.getRhEmpleado().setEmpBonoFijoNd(xivSueldo.getXivValor());
                        }
                        String prdsector = listXivSueldo.size() > 0 && listXivSueldo.get(0).getPrdSector() != null ? listXivSueldo.get(0).getPrdSector().getPrdSectorPK().getSecCodigo() : "";
                        RhXivSueldoPeriodoTO rhXivSueldoPeriodoTO = obtenerRhXivSueldoPeriodoTO(con.getEmpCodigo(), UtilsDate.fechaFormatoDate(con.getConFecha(), "yyyy-MM-dd"));
                        for (RhXivSueldo rldvpg : listXivSueldo) {
                            ReporteXivSueldoConsulta reporteXivSueldoConsulta = new ReporteXivSueldoConsulta();
                            reporteXivSueldoConsulta.setSector(prdsector);
                            reporteXivSueldoConsulta.setPeriodo(con.getPerCodigo());
                            reporteXivSueldoConsulta.setFechaDesde(rhXivSueldoPeriodoTO.getXivDesde());
                            reporteXivSueldoConsulta.setFechaHasta(rhXivSueldoPeriodoTO.getXivHasta());
                            reporteXivSueldoConsulta.setFechaMaxima(rhXivSueldoPeriodoTO.getXivFechaMaximaPago());
                            reporteXivSueldoConsulta.setXivCategoria((rldvpg.getRhEmpleado().getRhCategoria() == null ? "" : rldvpg.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre()));
                            reporteXivSueldoConsulta.setXivSector((rldvpg.getPrdSector() == null ? "" : rldvpg.getPrdSector().getSecNombre()));
                            reporteXivSueldoConsulta.setXivId((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getRhEmpleadoPK().getEmpId()));
                            reporteXivSueldoConsulta.setXivNombres((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpNombres().trim()));
                            reporteXivSueldoConsulta.setXivApellidos((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpApellidos().trim()));
                            reporteXivSueldoConsulta.setXivGenero((rldvpg.getRhEmpleado() == null ? null : rldvpg.getRhEmpleado().getEmpGenero()));
                            reporteXivSueldoConsulta.setXivFechaIngreso((rldvpg.getEmpFechaIngreso() == null ? "" : UtilsDate.fechaFormatoString(rldvpg.getEmpFechaIngreso(), "yyyy-MM-dd")));
                            reporteXivSueldoConsulta.setXivCargo((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpCargo()));
                            reporteXivSueldoConsulta.setXivTotalIngresos((rldvpg.getXivBaseImponible() == null ? null : rldvpg.getXivBaseImponible()));
                            reporteXivSueldoConsulta.setXivDiasLaborados((rldvpg.getXivDiasLaboradosEmpleado() == null ? null : rldvpg.getXivDiasLaboradosEmpleado().shortValue()));
                            reporteXivSueldoConsulta.setXivValorXivSueldo((rldvpg.getXivValor() == null ? null : rldvpg.getXivValor()));
                            reporteXivSueldoConsulta.setXivCodigoMinisterial((rldvpg.getXivCodigoMinisterial() == null ? null : rldvpg.getXivCodigoMinisterial().charAt(0)));
                            reporteXivSueldoConsulta.setXivNumero((rldvpg.getConContable() == null ? "" : rldvpg.getConContable().getConContablePK().getConNumero()));//
                            reporteXivSueldoConsulta.setXivDiasCalculados(rldvpg.getXivDiasCalculados() != null ? rldvpg.getXivDiasCalculados() : 0);//
                            reporteXivSueldoConsulta.setDiferenciaDias(Math.abs(rldvpg.getXivDiasLaboradosEmpleado() - rldvpg.getXivDiasCalculados()));//
                            lista2.add(reporteXivSueldoConsulta);
                        }
                        break;
                    case PROVISIONES:
                        List<RhListaProvisionesTO> listProvisiones = empleadoService.getRhListaProvisionesComprobanteContableTO(con.toConContablePK().getConEmpresa(), con.toConContablePK().getConPeriodo(), con.toConContablePK().getConTipo(), con.toConContablePK().getConNumero());
                        for (RhListaProvisionesTO rldvpg : listProvisiones) {
                            ReporteProvisionesComprobanteContable reporteProvisionesComprobanteContable = new ReporteProvisionesComprobanteContable();
                            reporteProvisionesComprobanteContable.setPeriodo(con.getPerCodigo());
                            reporteProvisionesComprobanteContable.setTipo(con.getTipCodigo());
                            reporteProvisionesComprobanteContable.setNumero(con.getConNumero());
                            reporteProvisionesComprobanteContable.setProvId((rldvpg.getProvId() == null ? "" : rldvpg.getProvId()));
                            reporteProvisionesComprobanteContable
                                    .setProvNombres((rldvpg.getProvNombres() == null ? "" : rldvpg.getProvNombres()));
                            reporteProvisionesComprobanteContable
                                    .setProvSueldo((rldvpg.getProvSueldo() == null ? null : rldvpg.getProvSueldo()));
                            reporteProvisionesComprobanteContable
                                    .setProvDiasPagados((rldvpg.getProvDiasPagados() == null ? null : rldvpg.getProvDiasPagados()));
                            reporteProvisionesComprobanteContable.setProvAporteIndividual(
                                    (rldvpg.getProvAporteIndividual() == null ? null : rldvpg.getProvAporteIndividual()));
                            reporteProvisionesComprobanteContable.setProvAportePatronal(
                                    (rldvpg.getProvAportePatronal() == null ? null : rldvpg.getProvAportePatronal()));
                            reporteProvisionesComprobanteContable
                                    .setProvIece((rldvpg.getProvIece() == null ? null : rldvpg.getProvIece()));
                            reporteProvisionesComprobanteContable
                                    .setProvSecap((rldvpg.getProvSecap() == null ? null : rldvpg.getProvSecap()));
                            reporteProvisionesComprobanteContable
                                    .setProvXiii((rldvpg.getProvXiii() == null ? null : rldvpg.getProvXiii()));
                            reporteProvisionesComprobanteContable
                                    .setProvXiv((rldvpg.getProvXiv() == null ? null : rldvpg.getProvXiv()));
                            reporteProvisionesComprobanteContable
                                    .setProvFondoReserva((rldvpg.getProvFondoReserva() == null ? null : rldvpg.getProvFondoReserva()));
                            reporteProvisionesComprobanteContable
                                    .setProvVacaciones((rldvpg.getProvVacaciones() == null ? null : rldvpg.getProvVacaciones()));
                            reporteProvisionesComprobanteContable
                                    .setProvDesahucio((rldvpg.getProvDesahucio() == null ? null : rldvpg.getProvDesahucio()));
                            reporteProvisionesComprobanteContable
                                    .setProvContableRol((rldvpg.getProvContableRol() == null ? "" : rldvpg.getProvContableRol()));
                            reporteProvisionesComprobanteContable.setProvContableProvision(
                                    (rldvpg.getProvContableProvision() == null ? "" : rldvpg.getProvContableProvision()));
                            listaProv.add(reporteProvisionesComprobanteContable);
                        }
                        break;
                }
            }
            if (tipoReferencia != null) {
                switch (TipoRRHH.getTipoRRHH(tipoReferencia)) {
                    case ANTICIPO:
                        respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteAnticipoPorLote.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), list);
                        break;
                    case HORAS_EXTRAS:
                        respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteHorasExtrasPorLote.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), reporteConsultaHorasExtrasLotes);
                        break;
                    case BONO:
                        respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteBonosPorLote.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), reporteConsultaBonosLotes);
                        break;
                    case ROL:
                        respuesta = generarReporteRolLoteSoporte(usuarioEmpresaReporteTO, lis);
                        break;
                    case UTILIDAD:
                        respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteUtilidadesLotes.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listaImprimir);
                        break;
                    case XIIISUELDO:
                        respuesta = genericReporteService.generarReporte(modulo, "reportXiiiSueldoConsulta.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
                        break;
                    case XIVSUELDO:
                        respuesta = genericReporteService.generarReporte(modulo, "reportXivSueldoConsulta.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista2);
                        break;
                    case PROVISIONES:
                        respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteProvisiones.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaProv);
                        break;
                }
            }

        } else {
            return respuesta;
        }

        return respuesta;
    }

    public byte[] generarReporteXiii(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String periodo, String fechaDesde, String fechaHasta, String fechaMaxima, List<RhXiiiSueldo> rhFunXiiiSueldoConsultarTO) throws Exception {
        List<ReporteXiiiSueldoConsulta> lista = new ArrayList<>();
        rhFunXiiiSueldoConsultarTO.stream().map((rldvpg) -> {
            ReporteXiiiSueldoConsulta reporteXiiiSueldoConsulta = new ReporteXiiiSueldoConsulta();
            reporteXiiiSueldoConsulta.setSector(sector);
            reporteXiiiSueldoConsulta.setPeriodo(periodo);
            reporteXiiiSueldoConsulta.setFechaDesde(fechaDesde);
            reporteXiiiSueldoConsulta.setFechaHasta(fechaHasta);
            reporteXiiiSueldoConsulta.setFechaMaxima(fechaMaxima);
            reporteXiiiSueldoConsulta.setXiiiCategoria((rldvpg.getRhEmpleado().getRhCategoria() == null ? "" : rldvpg.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre()));
            reporteXiiiSueldoConsulta.setXiiiSector((rldvpg.getPrdSector() == null ? "" : rldvpg.getPrdSector().getSecNombre()));
            reporteXiiiSueldoConsulta.setXiiiId((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getRhEmpleadoPK().getEmpId()));
            reporteXiiiSueldoConsulta.setXiiiNombres((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpNombres().trim()));
            reporteXiiiSueldoConsulta.setXiiiApellidos((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpApellidos().trim()));
            reporteXiiiSueldoConsulta.setXiiiGenero((rldvpg.getRhEmpleado() == null ? null : rldvpg.getRhEmpleado().getEmpGenero()));
            reporteXiiiSueldoConsulta.setXiiiFechaIngreso((rldvpg.getEmpFechaIngreso() == null ? "" : UtilsDate.fechaFormatoString(rldvpg.getEmpFechaIngreso(), "yyyy-MM-dd")));
            reporteXiiiSueldoConsulta.setXiiiCargo((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpCargo()));
            reporteXiiiSueldoConsulta.setXiiiTotalIngresos((rldvpg.getXiiiBaseImponible() == null ? null : rldvpg.getXiiiBaseImponible()));
            reporteXiiiSueldoConsulta.setXiiiDiasLaborados((rldvpg.getXiiiDiasLaborados() == null ? null : rldvpg.getXiiiDiasLaborados().shortValue()));
            reporteXiiiSueldoConsulta.setXiiiValorXiiiSueldo((rldvpg.getXiiiValor() == null ? null : rldvpg.getXiiiValor()));
            reporteXiiiSueldoConsulta.setXiiiCodigoMinisterial((rldvpg.getXiiiCodigoMinisterial() == null
                    || rldvpg.getXiiiCodigoMinisterial().compareToIgnoreCase("") == 0 ? null
                    : rldvpg.getXiiiCodigoMinisterial().charAt(0)));
            reporteXiiiSueldoConsulta.setXiiiNumero((rldvpg.getConContable() == null ? "" : rldvpg.getConContable().getConContablePK().getConNumero()));//
            return reporteXiiiSueldoConsulta;
        }).forEachOrdered((reporteXiiiSueldoConsulta) -> {
            lista.add(reporteXiiiSueldoConsulta);
        });
        return genericReporteService.generarReporte(modulo, "reportXiiiSueldoConsulta.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    public RhXiiiSueldoPeriodoTO obtenerRhXiiiSueldoPeriodoTO(String empresa, Date fecha) throws Exception {
        List<RhXiiiSueldoPeriodoTO> listaRhXiiiSueldoPeriodoTO = xiiiSueldoPeriodoService.getRhComboXiiiSueldoPeriodoTO();
        RhXiiiSueldoPeriodoTO rhXiiiSueldoPeriodoTO = new RhXiiiSueldoPeriodoTO();
        for (RhXiiiSueldoPeriodoTO item : listaRhXiiiSueldoPeriodoTO) {
            Date fechaXiiiDesde = UtilsDate.fechaFormatoDate(item.getXiiiDesde(), "yyyy-MM-dd");
            Date fechaXiiiHasta = UtilsDate.fechaFormatoDate(item.getXiiiHasta(), "yyyy-MM-dd");
            Date fechaXiiiMaxima = UtilsDate.fechaFormatoDate(item.getXiiiFechaMaximaPago(), "yyyy-MM-dd");
            if (((fechaXiiiDesde.equals(fecha) || fechaXiiiDesde.before(fecha) && (fechaXiiiHasta.equals(fecha) || fechaXiiiHasta.after(fecha)))
                    || ((fechaXiiiDesde.equals(fecha) || fechaXiiiDesde.before(fecha)) && (fechaXiiiMaxima.equals(fecha) || fechaXiiiMaxima.after(fecha))))) {
                rhXiiiSueldoPeriodoTO = item;
                break;
            }
        }
        return rhXiiiSueldoPeriodoTO;
    }

    public byte[] generarReporteXiv(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector,
            String periodo, String fechaDesde, String fechaHasta, String fechaMaxima, List<RhXivSueldo> rhFunXivSueldoConsultarTO) throws Exception {
        List<ReporteXivSueldoConsulta> lista = new ArrayList<>();
        for (RhXivSueldo rldvpg : rhFunXivSueldoConsultarTO) {
            ReporteXivSueldoConsulta reporteXivSueldoConsulta = new ReporteXivSueldoConsulta();
            reporteXivSueldoConsulta.setSector(sector);
            reporteXivSueldoConsulta.setPeriodo(periodo);
            reporteXivSueldoConsulta.setFechaDesde(fechaDesde);
            reporteXivSueldoConsulta.setFechaHasta(fechaHasta);
            reporteXivSueldoConsulta.setFechaMaxima(fechaMaxima);
            reporteXivSueldoConsulta.setXivCategoria((rldvpg.getRhEmpleado().getRhCategoria() == null ? "" : rldvpg.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre()));
            reporteXivSueldoConsulta.setXivSector((rldvpg.getPrdSector() == null ? "" : rldvpg.getPrdSector().getSecNombre()));
            reporteXivSueldoConsulta.setXivId((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getRhEmpleadoPK().getEmpId()));
            reporteXivSueldoConsulta.setXivNombres((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpNombres().trim()));
            reporteXivSueldoConsulta.setXivApellidos((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpApellidos().trim()));
            reporteXivSueldoConsulta.setXivGenero((rldvpg.getRhEmpleado() == null ? null : rldvpg.getRhEmpleado().getEmpGenero()));
            reporteXivSueldoConsulta.setXivFechaIngreso((rldvpg.getEmpFechaIngreso() == null ? "" : UtilsDate.fechaFormatoString(rldvpg.getEmpFechaIngreso(), "yyyy-MM-dd")));
            reporteXivSueldoConsulta.setXivCargo((rldvpg.getRhEmpleado() == null ? "" : rldvpg.getRhEmpleado().getEmpCargo()));
            reporteXivSueldoConsulta.setXivTotalIngresos((rldvpg.getXivBaseImponible() == null ? null : rldvpg.getXivBaseImponible()));
            reporteXivSueldoConsulta.setXivDiasLaborados((rldvpg.getXivDiasLaboradosEmpleado() == null ? null : rldvpg.getXivDiasLaboradosEmpleado().shortValue()));
            reporteXivSueldoConsulta.setXivValorXivSueldo((rldvpg.getXivValor() == null ? null : rldvpg.getXivValor()));
            reporteXivSueldoConsulta.setXivCodigoMinisterial((rldvpg.getXivCodigoMinisterial() == null ? null : rldvpg.getXivCodigoMinisterial().charAt(0)));
            reporteXivSueldoConsulta.setXivNumero((rldvpg.getConContable() == null ? "" : rldvpg.getConContable().getConContablePK().getConNumero()));//
            reporteXivSueldoConsulta.setXivDiasCalculados(rldvpg.getXivDiasCalculados() != null ? rldvpg.getXivDiasCalculados() : 0);//
            reporteXivSueldoConsulta.setDiferenciaDias(Math.abs(rldvpg.getXivDiasLaboradosEmpleado() - rldvpg.getXivDiasCalculados()));//
            lista.add(reporteXivSueldoConsulta);
        }
        return genericReporteService.generarReporte(modulo, "reportXivSueldoConsulta.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    public RhXivSueldoPeriodoTO obtenerRhXivSueldoPeriodoTO(String empresa, Date fecha) throws Exception {
        List<RhXivSueldoPeriodoTO> listaRhXivSueldoPeriodoTO = xivSueldoPeriodoService.getRhComboXivSueldoPeriodoTO();
        RhXivSueldoPeriodoTO rhXivSueldoPeriodoTO = new RhXivSueldoPeriodoTO();
        for (RhXivSueldoPeriodoTO item : listaRhXivSueldoPeriodoTO) {
            Date fechaXivDesde = UtilsDate.fechaFormatoDate(item.getXivDesde(), "yyyy-MM-dd");
            Date fechaXivHasta = UtilsDate.fechaFormatoDate(item.getXivHasta(), "yyyy-MM-dd");
            Date fechaXivMaxima = UtilsDate.fechaFormatoDate(item.getXivFechaMaximaPago(), "yyyy-MM-dd");
            if (((fechaXivDesde.equals(fecha) || fechaXivDesde.before(fecha) && (fechaXivHasta.equals(fecha) || fechaXivHasta.after(fecha)))
                    || ((fechaXivDesde.equals(fecha) || fechaXivDesde.before(fecha)) && (fechaXivMaxima.equals(fecha) || fechaXivMaxima.after(fecha))))) {
                rhXivSueldoPeriodoTO = item;
                break;
            }
        }
        return rhXivSueldoPeriodoTO;
    }

    @Override
    public byte[] generarListaConContableReporteRRHH(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        byte[] respuesta = null;
        ItemStatusItemListaConContableTO con;
        if (listado != null && !listado.isEmpty()) {
            con = listado.get(0);
        } else {
            return respuesta;
        }
        switch (TipoRRHH.getTipoRRHH(con.getConReferencia())) {
            case ANTICIPO:
                List<ReporteAnticipoPrestamoXIIISueldo> list = new ArrayList<>();
                List<RhListaDetalleAnticiposLoteTO> listAnticipo = anticipoService.getRhDetalleAnticiposLoteTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
                listAnticipo.remove(listAnticipo.size() - 1);//Se elimina el último elemento, contiene el total
                for (RhListaDetalleAnticiposLoteTO listaDetalleAnticiposTO : listAnticipo) {
                    ReporteAnticipoPrestamoXIIISueldo rhReporteAnticipoOprestamo = new ReporteAnticipoPrestamoXIIISueldo();
                    rhReporteAnticipoOprestamo.setComprobante(
                            con.getPerCodigo() + " | " + con.getTipCodigo() + " | " + con.getConNumero());
                    rhReporteAnticipoOprestamo.setFecha(listaDetalleAnticiposTO.getDalFecha());
                    rhReporteAnticipoOprestamo.setCedula(listaDetalleAnticiposTO.getDalId());
                    rhReporteAnticipoOprestamo.setNombres(listaDetalleAnticiposTO.getDalNombres());
                    rhReporteAnticipoOprestamo.setCargo("");
                    rhReporteAnticipoOprestamo.setNombreSector("");
                    rhReporteAnticipoOprestamo.setValor(listaDetalleAnticiposTO.getDalValor());
                    rhReporteAnticipoOprestamo.setFormaPago(listaDetalleAnticiposTO.getDalFormaPagoDetalle());
                    rhReporteAnticipoOprestamo.setNombreCuenta(listaDetalleAnticiposTO.getDalFormaPagoDetalle());
                    rhReporteAnticipoOprestamo.setReferencia(listaDetalleAnticiposTO.getDalDocumento());
                    rhReporteAnticipoOprestamo.setObservaciones(listaDetalleAnticiposTO.getDalObservaciones());
                    list.add(rhReporteAnticipoOprestamo);
                }
                respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteAnticipo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), list);
                break;
            case PRESTAMO:
                List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
                List<RhPrestamo> rhPrestamos = prestamoService.getListRhPrestamo(con.toConContablePK());
                for (RhPrestamo rhPrestamo : rhPrestamos) {
                    lista.add(new ReporteAnticipoPrestamoXIIISueldo(
                            con.getEmpCodigo() + " | " + con.getPerCodigo() + " | " + con.getTipCodigo() + " | " + con.getConNumero(),
                            con.getConFecha(),
                            rhPrestamo.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                            rhPrestamo.getRhEmpleado().getEmpApellidos() + " " + rhPrestamo.getRhEmpleado().getEmpNombres(),
                            rhPrestamo.getRhEmpleado().getEmpCargo(),
                            rhPrestamo.getRhEmpleado().getPrdSector().getSecNombre(),
                            rhPrestamo.getPreValor(),
                            rhPrestamo.getConCuentas().getCtaDetalle(),
                            rhPrestamo.getPreDocumento(),
                            rhPrestamo.getPreObservaciones() == null ? "" : rhPrestamo.getPreObservaciones().toUpperCase()));
                }
                respuesta = genericReporteService.generarReporte(modulo, "reportComprobantePrestamo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
                break;
            case ROL:
                List<RhRol> listRol = rolService.getListRhRol(con.toConContablePK());
                List<ReportesRol> listaRol = new ArrayList<>();
                if (listRol != null && !listRol.isEmpty()) {
                    List<RhListaDetalleRolesTO> listaDetalleRolesTO = new ArrayList<>();
                    for (RhRol itemRol : listRol) {
                        listaDetalleRolesTO.addAll(eliminarDetallesRolesNulos(rolService.getRhDetalleRolesTO(con.getEmpCodigo(), con.getConFecha(), con.getConFecha(),
                                itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(), itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                                itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(), ""), false, null));
                    }
                    for (RhRol itemRol : listRol) {
                        List<RhListaRolSaldoEmpleadoDetalladoTO> detalleList = rolService.getRhRolSaldoEmpleadoDetalladoTO(con.getEmpCodigo(),
                                itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                                UtilsDate.fechaFormatoString(itemRol.getRolDesde(), "dd-MM-yyyy"),
                                UtilsDate.fechaFormatoString(itemRol.getRolHasta(), "dd-MM-yyyy"));
                        for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                            System.out.println(itemDetalle.getLrpFormaPago() + "FORMA PAGO");
                            if (itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId().equals(itemDetalle.getLrpId())) {
                                if (itemRol.getRolObservaciones() != null) {
                                    itemDetalle.setLrpObservaciones(itemRol.getRolObservaciones());
                                }
                            }
                            if (itemDetalle.getLrpObservaciones() == null || itemDetalle.getLrpObservaciones().equals("")) {
                                itemDetalle.setLrpObservaciones("OBSERVACION GENERAL");
                            }
                            if (itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId().equalsIgnoreCase(itemDetalle.getLrpId())) {
                                convertirValoresItemDetalleVaciosACero(itemDetalle);
                                if (detalleList == null || detalleList.isEmpty()) {
                                    listaRol.add(new ReportesRol(itemDetalle, "", "", "", "", "", null, "", itemRol.getPrdSector().getPrdSectorPK().getSecCodigo()));
                                } else {
                                    for (RhListaRolSaldoEmpleadoDetalladoTO detalle : detalleList) {
                                        listaRol.add(new ReportesRol(
                                                itemDetalle,
                                                detalle.getSedConcepto(),
                                                detalle.getSedDetalle(),
                                                detalle.getSedCp(),
                                                detalle.getSedCc(),
                                                detalle.getSedFecha(),
                                                detalle.getSedValor(),
                                                detalle.getSedObservaciones(),
                                                itemRol.getRhEmpleado().getPrdSector().getPrdSectorPK().getSecCodigo()));
                                    }
                                }
                            }
                        }
                    }
                    respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteRol.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listaRol);
                }
                break;

            case LIQUIDACION:
                List<RhRol> listLiquidacion = rolService.getListRhRol(con.toConContablePK());
                List<ReportesRol> listaLiquidacion = new ArrayList<>();
                if (listLiquidacion != null && !listLiquidacion.isEmpty()) {
                    List<RhListaDetalleRolesTO> listaDetalleRolesTO = new ArrayList<>();
                    for (RhRol itemRol : listLiquidacion) {
                        listaDetalleRolesTO.addAll(eliminarDetallesRolesNulos(rolService.getRhDetalleRolesTO(
                                con.getEmpCodigo(),
                                con.getConFecha(),
                                con.getConFecha(),
                                itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(),
                                itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                                itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                                "LIQUIDACIONES"), false, null));
                    }
                    for (RhRol itemRol : listLiquidacion) {
                        for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                            convertirValoresItemDetalleVaciosACero(itemDetalle);
                            listaLiquidacion.add(new ReportesRol(itemDetalle, "", "", "", "", "", null, "",
                                    itemRol.getPrdSector().getPrdSectorPK().getSecCodigo()));
                        }
                    }
                    respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteLiquidacion.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listaLiquidacion);
                }
                break;
            case UTILIDAD:
                List<ReporteAnticipoPrestamoXIIISueldo> rhReporteUtilidadesList = new ArrayList<>();
                List<RhUtilidades> listaRhUtilidades = utilidadService.getListRhUtilidades(con.toConContablePK());
                for (RhUtilidades item : listaRhUtilidades) {
                    ReporteAnticipoPrestamoXIIISueldo rhReporteUtilidades = new ReporteAnticipoPrestamoXIIISueldo();
                    rhReporteUtilidades.setComprobante(con.getPerCodigo() + "|" + con.getTipCodigo() + "|" + con.getConNumero());
                    rhReporteUtilidades.setFecha(con.getConFecha());
                    rhReporteUtilidades.setCedula(item.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                    rhReporteUtilidades.setNombres(item.getRhEmpleado().getEmpApellidos() + " " + item.getRhEmpleado().getEmpNombres());
                    rhReporteUtilidades.setCargo(item.getRhEmpleado().getEmpCargo());
                    rhReporteUtilidades.setNombreSector(item.getPrdSector().getPrdSectorPK().getSecCodigo());
                    rhReporteUtilidades.setValor(item.getUtiValorTotal());
                    rhReporteUtilidades.setFormaPago(item.getConCuentas().getConCuentasPK().getCtaCodigo() + "-" + item.getConCuentas().getCtaDetalle());
                    rhReporteUtilidades.setReferencia(item.getUtiDocumento());
                    rhReporteUtilidades.setObservaciones(item.getUtiObservaciones() == null || item.getUtiObservaciones().isEmpty() ? item.getConContable().getConObservaciones() : item.getUtiObservaciones());

                    rhReporteUtilidades.setValor(rhReporteUtilidades.getValor() == null ? BigDecimal.ZERO : rhReporteUtilidades.getValor());
                    rhReporteUtilidades.setImpuestoRenta(item.getUtiImpuestoRenta());
                    rhReporteUtilidades.setImpuestoRenta(rhReporteUtilidades.getImpuestoRenta() == null ? BigDecimal.ZERO : rhReporteUtilidades.getImpuestoRenta());
                    BigDecimal valorARecibir = rhReporteUtilidades.getValor().subtract(rhReporteUtilidades.getImpuestoRenta());
                    rhReporteUtilidades.setValorARecibir(valorARecibir);

                    rhReporteUtilidadesList.add(rhReporteUtilidades);
                }
                respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteUtilidades.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), rhReporteUtilidadesList);
                break;
            case XIIISUELDO:
                List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldoList = new ArrayList<>();
                List<RhXiiiSueldo> listaRhXiiiSueldo = xiiiSueldoService.getListRhXiiiSueldo(con.toConContablePK());
                for (RhXiiiSueldo item : listaRhXiiiSueldo) {
                    ReporteAnticipoPrestamoXIIISueldo rhReporteXIIISueldo = new ReporteAnticipoPrestamoXIIISueldo();
                    rhReporteXIIISueldo.setComprobante(con.getPerCodigo() + "|" + con.getTipCodigo() + "|" + con.getConNumero());
                    rhReporteXIIISueldo.setFecha(con.getConFecha());
                    rhReporteXIIISueldo.setCedula(item.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                    rhReporteXIIISueldo.setNombres(item.getRhEmpleado().getEmpApellidos() + " " + item.getRhEmpleado().getEmpNombres());
                    rhReporteXIIISueldo.setCargo(item.getRhEmpleado().getEmpCargo());
                    rhReporteXIIISueldo.setNombreSector(item.getPrdSector().getPrdSectorPK().getSecCodigo());
                    rhReporteXIIISueldo.setValor(item.getXiiiValor());
                    rhReporteXIIISueldo.setFormaPago(item.getConCuentas().getCtaDetalle());
                    rhReporteXIIISueldo.setReferencia(item.getXiiiDocumento());
                    rhReporteXIIISueldo.setObservaciones(item.getXiiiObservaciones() == null || item.getXiiiObservaciones().isEmpty() ? item.getConContable().getConObservaciones() : item.getXiiiObservaciones());
                    rhReporteXIIISueldoList.add(rhReporteXIIISueldo);
                }
                respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteXIIISueldo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), rhReporteXIIISueldoList);
                break;
            case XIVSUELDO:
                List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIVSueldoList = new ArrayList<>();
                List<RhXivSueldo> listaRhXivSueldo = xivSueldoService.getListRhXivSueldo(con.toConContablePK());
                for (RhXivSueldo item : listaRhXivSueldo) {
                    ReporteAnticipoPrestamoXIIISueldo rhReporteXIVSueldo = new ReporteAnticipoPrestamoXIIISueldo();
                    rhReporteXIVSueldo.setComprobante(con.getPerCodigo() + "|" + con.getTipCodigo() + "|" + con.getConNumero());
                    rhReporteXIVSueldo.setFecha(con.getConFecha());
                    rhReporteXIVSueldo.setCedula(item.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                    rhReporteXIVSueldo.setNombres(item.getRhEmpleado().getEmpApellidos() + " " + item.getRhEmpleado().getEmpNombres());
                    rhReporteXIVSueldo.setCargo(item.getRhEmpleado().getEmpCargo());
                    rhReporteXIVSueldo.setNombreSector(item.getPrdSector().getPrdSectorPK().getSecCodigo());
                    rhReporteXIVSueldo.setValor(item.getXivValor());
                    rhReporteXIVSueldo.setFormaPago(item.getConCuentas().getCtaDetalle());
                    rhReporteXIVSueldo.setReferencia(item.getXivDocumento());
                    rhReporteXIVSueldo.setObservaciones(item.getXivObservaciones() == null || item.getXivObservaciones().isEmpty() ? item.getConContable().getConObservaciones() : item.getXivObservaciones());
                    rhReporteXIVSueldoList.add(rhReporteXIVSueldo);
                }
                respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteXIVSueldo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), rhReporteXIVSueldoList);
                break;
        }
        return respuesta;
    }

    @Override
    public byte[] generarReporteRRHHIndividualLiquidacion(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        byte[] respuesta = null;
        ItemStatusItemListaConContableTO con;
        ConContable contable = null;
        if (listado != null && !listado.isEmpty()) {
            con = listado.get(0);
            ConContablePK conContablePK = new ConContablePK();
            conContablePK.setConEmpresa(con.getEmpCodigo());
            conContablePK.setConPeriodo(con.getPerCodigo());
            conContablePK.setConTipo(con.getTipCodigo());
            conContablePK.setConNumero(con.getConNumero());
            contable = contableService.getConContable(conContablePK);
        } else {
            return respuesta;
        }

        List<RhRol> listLiquidacion = rolService.getListRhRol(contable.getConContablePK());
        List<ReportesRol> listaLiquidacion = new ArrayList<>();

        if (listLiquidacion != null && !listLiquidacion.isEmpty()) {
            List<RhListaDetalleRolesTO> listaDetalleRolesTO = new ArrayList<>();
            for (RhRol itemRol : listLiquidacion) {
                List<RhListaDetalleRolesTO> detalle = rolService.getRhDetalleRolesTO(
                        con.getEmpCodigo(),
                        UtilsDate.fechaFormatoString(contable.getConFecha()),
                        UtilsDate.fechaFormatoString(contable.getConFecha()),
                        itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(),
                        itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                        itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                        "");
                listaDetalleRolesTO.addAll(eliminarDetallesRolesNulos(detalle, true, contable.getConContablePK()));
            }
            for (RhRol itemRol : listLiquidacion) {
                List<RhListaRolSaldoEmpleadoDetalladoTO> detalleList = rolService.getRhRolSaldoEmpleadoDetalladoTO(con.getEmpCodigo(),
                        itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                        UtilsDate.fechaFormatoString(itemRol.getRolDesde(), "dd-MM-yyyy"),
                        UtilsDate.fechaFormatoString(itemRol.getRolHasta(), "dd-MM-yyyy"));
                listaDetalleRolesTO.stream().filter((itemDetalle) -> (itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId().equalsIgnoreCase(itemDetalle.getLrpId()))).map((itemDetalle) -> {
                    convertirValoresItemDetalleVaciosACero(itemDetalle);
                    return itemDetalle;
                }).map((itemDetalle) -> {
                    itemDetalle.setLrpBonos(itemDetalle.getLrpBonos().add(itemDetalle.getLrpHorasExtras()));
                    return itemDetalle;
                }).forEach((itemDetalle) -> {
                    if (detalleList == null || detalleList.isEmpty()) {
                        ReportesRol rep = new ReportesRol(itemDetalle, "", "", "", "", "", null, "",
                                itemRol.getPrdSector().getPrdSectorPK().getSecCodigo());
                        rep.setRolMotivoSalida(itemRol.getRhEmpleado().getEmpMotivoSalida());
                        if (itemRol.getRhEmpleado() != null) {
                            if (itemRol.getRhEmpleado().getEmpFechaUltimaSalida() != null) {
                                rep.setFechaFinLabores(UtilsDate.fechaFormatoString(itemRol.getRhEmpleado().getEmpFechaUltimaSalida(), "yyyy-MM-dd"));
                            } else {
                                if (itemRol.getRhEmpleado().getEmpFechaPrimeraSalida() != null) {
                                    rep.setFechaFinLabores(UtilsDate.fechaFormatoString(itemRol.getRhEmpleado().getEmpFechaPrimeraSalida(), "yyyy-MM-dd"));
                                }
                            }
                        }
                        listaLiquidacion.add(rep);
                    } else {
                        detalleList.stream().forEach((detalle) -> {
                            ReportesRol rep = new ReportesRol(
                                    itemDetalle,
                                    detalle.getSedConcepto(),
                                    detalle.getSedDetalle(),
                                    detalle.getSedCp(),
                                    detalle.getSedCc(),
                                    detalle.getSedFecha(),
                                    detalle.getSedValor(),
                                    detalle.getSedObservaciones(),
                                    itemRol.getRhEmpleado().getPrdSector().getPrdSectorPK().getSecCodigo());
                            rep.setRolMotivoSalida(itemRol.getRhEmpleado().getEmpMotivoSalida());
                            if (itemRol.getRhEmpleado() != null) {
                                if (itemRol.getRhEmpleado().getEmpFechaUltimaSalida() != null) {
                                    rep.setFechaFinLabores(UtilsDate.fechaFormatoString(itemRol.getRhEmpleado().getEmpFechaUltimaSalida(), "yyyy-MM-dd"));
                                } else {
                                    if (itemRol.getRhEmpleado().getEmpFechaPrimeraSalida() != null) {
                                        rep.setFechaFinLabores(UtilsDate.fechaFormatoString(itemRol.getRhEmpleado().getEmpFechaPrimeraSalida(), "yyyy-MM-dd"));
                                    }
                                }

                            }
                            listaLiquidacion.add(rep);
                        });
                    }
                });
            }
            respuesta = genericReporteService.generarReporte(modulo, "reportComprobanteLiquidacionIndividual.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listaLiquidacion);

        }
        return respuesta;
    }

    @Override
    public byte[] generarReporteRRHHMatricialXIV(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIVSueldoList = new ArrayList<>();
        int index = 0;
        for (ItemStatusItemListaConContableTO con : listado) {
            con = listado.get(index);
            index++;
            List<RhXivSueldo> listaRhXivSueldo = xivSueldoService.getListRhXivSueldo(con.toConContablePK());
            for (RhXivSueldo item : listaRhXivSueldo) {
                ReporteAnticipoPrestamoXIIISueldo rhReporteXIVSueldo = new ReporteAnticipoPrestamoXIIISueldo();
                rhReporteXIVSueldo.setComprobante(con.getPerCodigo() + "|" + con.getTipCodigo() + "|" + con.getConNumero());
                rhReporteXIVSueldo.setFecha(con.getConFecha());
                rhReporteXIVSueldo.setCedula(item.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                rhReporteXIVSueldo.setNombres(item.getRhEmpleado().getEmpApellidos() + " " + item.getRhEmpleado().getEmpNombres());
                rhReporteXIVSueldo.setCargo(item.getRhEmpleado().getEmpCargo());
                rhReporteXIVSueldo.setNombreSector(item.getPrdSector().getPrdSectorPK().getSecCodigo());
                rhReporteXIVSueldo.setValor(item.getXivValor());
                rhReporteXIVSueldo.setFormaPago(item.getConCuentas().getCtaDetalle());
                rhReporteXIVSueldo.setReferencia(item.getXivDocumento());
                rhReporteXIVSueldo.setObservaciones(item.getXivObservaciones() == null || item.getXivObservaciones().isEmpty() ? item.getConContable().getConObservaciones() : item.getXivObservaciones());
                rhReporteXIVSueldoList.add(rhReporteXIVSueldo);
            }
        }
        return genericReporteService.generarReporte(modulo, "reportComprobanteXIVSueldo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), rhReporteXIVSueldoList);
    }

    @Override
    public byte[] generarReporteRRHHMatricialXIII(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldoList = new ArrayList<>();
        int index = 0;
        for (ItemStatusItemListaConContableTO con : listado) {
            con = listado.get(index);
            index++;
            List<RhXiiiSueldo> listaRhXiiiSueldo = xiiiSueldoService.getListRhXiiiSueldo(con.toConContablePK());
            for (RhXiiiSueldo item : listaRhXiiiSueldo) {
                ReporteAnticipoPrestamoXIIISueldo rhReporteXIIISueldo = new ReporteAnticipoPrestamoXIIISueldo();
                rhReporteXIIISueldo.setComprobante(con.getPerCodigo() + "|" + con.getTipCodigo() + "|" + con.getConNumero());
                rhReporteXIIISueldo.setFecha(con.getConFecha());
                rhReporteXIIISueldo.setCedula(item.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                rhReporteXIIISueldo.setNombres(item.getRhEmpleado().getEmpApellidos() + " " + item.getRhEmpleado().getEmpNombres());
                rhReporteXIIISueldo.setCargo(item.getRhEmpleado().getEmpCargo());
                rhReporteXIIISueldo.setNombreSector(item.getPrdSector().getPrdSectorPK().getSecCodigo());
                rhReporteXIIISueldo.setValor(item.getXiiiValor());
                rhReporteXIIISueldo.setFormaPago(item.getConCuentas().getCtaDetalle());
                rhReporteXIIISueldo.setReferencia(item.getXiiiDocumento());
                rhReporteXIIISueldo.setObservaciones(item.getXiiiObservaciones() == null || item.getXiiiObservaciones().isEmpty() ? item.getConContable().getConObservaciones() : item.getXiiiObservaciones());
                rhReporteXIIISueldoList.add(rhReporteXIIISueldo);
            }
        }
        return genericReporteService.generarReporte(modulo, "reportComprobanteXIIISueldo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), rhReporteXIIISueldoList);
    }

    @Override
    public byte[] generarReporteRRHHMatricialAnticipo(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> list = new ArrayList<>();
        int index = 0;
        for (ItemStatusItemListaConContableTO con : listado) {
            con = listado.get(index);
            index++;
            List<RhListaDetalleAnticiposLoteTO> listAnticipo = anticipoService.getRhDetalleAnticiposLoteTO(con.getEmpCodigo(), con.getPerCodigo(), con.getTipCodigo(), con.getConNumero());
            listAnticipo.remove(listAnticipo.size() - 1);//Se elimina el último elemento, contiene el total
            for (RhListaDetalleAnticiposLoteTO listaDetalleAnticiposTO : listAnticipo) {
                ReporteAnticipoPrestamoXIIISueldo rhReporteAnticipoOprestamo = new ReporteAnticipoPrestamoXIIISueldo();
                rhReporteAnticipoOprestamo.setComprobante(
                        con.getPerCodigo() + " | " + con.getTipCodigo() + " | " + con.getConNumero());
                rhReporteAnticipoOprestamo.setFecha(listaDetalleAnticiposTO.getDalFecha());
                rhReporteAnticipoOprestamo.setCedula(listaDetalleAnticiposTO.getDalId());
                rhReporteAnticipoOprestamo.setNombres(listaDetalleAnticiposTO.getDalNombres());
                rhReporteAnticipoOprestamo.setCargo("");
                rhReporteAnticipoOprestamo.setNombreSector("");
                rhReporteAnticipoOprestamo.setValor(listaDetalleAnticiposTO.getDalValor());
                rhReporteAnticipoOprestamo.setFormaPago(listaDetalleAnticiposTO.getDalFormaPagoDetalle());
                rhReporteAnticipoOprestamo.setNombreCuenta(listaDetalleAnticiposTO.getDalFormaPagoDetalle());
                rhReporteAnticipoOprestamo.setReferencia(listaDetalleAnticiposTO.getDalDocumento());
                rhReporteAnticipoOprestamo.setObservaciones(listaDetalleAnticiposTO.getDalObservaciones());
                list.add(rhReporteAnticipoOprestamo);
            }
        }
        return genericReporteService.generarReporte(modulo, "reportComprobanteAnticipo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), list);
    }

    @Override
    public byte[] generarReporteRRHHMatricialPrestamo(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        int index = 0;
        for (ItemStatusItemListaConContableTO con : listado) {
            con = listado.get(index);
            index++;
            List<RhPrestamo> rhPrestamos = prestamoService.getListRhPrestamo(con.toConContablePK());
            for (RhPrestamo rhPrestamo : rhPrestamos) {
                lista.add(new ReporteAnticipoPrestamoXIIISueldo(
                        con.getEmpCodigo() + " | " + con.getPerCodigo() + " | " + con.getTipCodigo() + " | " + con.getConNumero(),
                        con.getConFecha(),
                        rhPrestamo.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                        rhPrestamo.getRhEmpleado().getEmpApellidos() + " " + rhPrestamo.getRhEmpleado().getEmpNombres(),
                        rhPrestamo.getRhEmpleado().getEmpCargo(),
                        rhPrestamo.getRhEmpleado().getPrdSector().getSecNombre(),
                        rhPrestamo.getPreValor(),
                        rhPrestamo.getConCuentas().getCtaDetalle(),
                        rhPrestamo.getPreDocumento(),
                        rhPrestamo.getPreObservaciones() == null ? "" : rhPrestamo.getPreObservaciones().toUpperCase()));
            }
        }
        return genericReporteService.generarReporte(modulo, "reportComprobantePrestamo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    @Override
    public byte[] generarReporteRRHHMatricialRolDePagos(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<ReportesRol> listaRol = new ArrayList<>();
        int index = 0;
        for (ItemStatusItemListaConContableTO con : listado) {
            con = listado.get(index);
            index++;
            List<RhRol> listRol = rolService.getListRhRol(con.toConContablePK());
            if (listRol != null && !listRol.isEmpty()) {
                List<RhListaDetalleRolesTO> listaDetalleRolesTO = new ArrayList<>();
                for (RhRol itemRol : listRol) {
                    listaDetalleRolesTO.addAll(eliminarDetallesRolesNulos(rolService.getRhDetalleRolesTO(con.getEmpCodigo(), con.getConFecha(), con.getConFecha(),
                            itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(), itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                            itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(), ""), false, null));
                }
                for (RhRol itemRol : listRol) {
                    List<RhListaRolSaldoEmpleadoDetalladoTO> detalleList = rolService.getRhRolSaldoEmpleadoDetalladoTO(con.getEmpCodigo(),
                            itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                            UtilsDate.fechaFormatoString(itemRol.getRolDesde(), "dd-MM-yyyy"),
                            UtilsDate.fechaFormatoString(itemRol.getRolHasta(), "dd-MM-yyyy"));
                    for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                        System.out.println(itemDetalle.getLrpFormaPago() + "FORMA PAGO");
                        if (itemDetalle.getLrpObservaciones() == null || itemDetalle.getLrpObservaciones().equals("")) {
                            itemDetalle.setLrpObservaciones("OBSERVACION GENERAL");
                        }
                        if (itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId().equalsIgnoreCase(itemDetalle.getLrpId())) {
                            convertirValoresItemDetalleVaciosACero(itemDetalle);
                            if (detalleList == null || detalleList.isEmpty()) {
                                listaRol.add(new ReportesRol(itemDetalle, "", "", "", "", "", null, "", itemRol.getPrdSector().getPrdSectorPK().getSecCodigo()));
                            } else {
                                for (RhListaRolSaldoEmpleadoDetalladoTO detalle : detalleList) {
                                    listaRol.add(new ReportesRol(
                                            itemDetalle,
                                            detalle.getSedConcepto(),
                                            detalle.getSedDetalle(),
                                            detalle.getSedCp(),
                                            detalle.getSedCc(),
                                            detalle.getSedFecha(),
                                            detalle.getSedValor(),
                                            detalle.getSedObservaciones(),
                                            itemRol.getRhEmpleado().getPrdSector().getPrdSectorPK().getSecCodigo()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return genericReporteService.generarReporte(modulo, "reportComprobanteRol.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listaRol);
    }

    @Override
    public byte[] generarReporteRRHHMatricialUtilidades(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteUtilidadesList = new ArrayList<>();
        int index = 0;
        for (ItemStatusItemListaConContableTO con : listado) {
            con = listado.get(index);
            index++;
            List<RhUtilidades> listaRhUtilidades = utilidadService.getListRhUtilidades(con.toConContablePK());
            for (RhUtilidades item : listaRhUtilidades) {
                ReporteAnticipoPrestamoXIIISueldo rhReporteUtilidades = new ReporteAnticipoPrestamoXIIISueldo();
                rhReporteUtilidades.setComprobante(con.getPerCodigo() + "|" + con.getTipCodigo() + "|" + con.getConNumero());
                rhReporteUtilidades.setFecha(con.getConFecha());
                rhReporteUtilidades.setCedula(item.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                rhReporteUtilidades.setNombres(item.getRhEmpleado().getEmpApellidos() + " " + item.getRhEmpleado().getEmpNombres());
                rhReporteUtilidades.setCargo(item.getRhEmpleado().getEmpCargo());
                rhReporteUtilidades.setNombreSector(item.getPrdSector().getPrdSectorPK().getSecCodigo());
                rhReporteUtilidades.setValor(item.getUtiValorTotal());
                rhReporteUtilidades.setFormaPago(item.getConCuentas().getConCuentasPK().getCtaCodigo() + "-" + item.getConCuentas().getCtaDetalle());
                rhReporteUtilidades.setReferencia(item.getUtiDocumento());
                rhReporteUtilidades.setObservaciones(item.getUtiObservaciones() == null || item.getUtiObservaciones().isEmpty() ? item.getConContable().getConObservaciones() : item.getUtiObservaciones());

                rhReporteUtilidades.setValor(rhReporteUtilidades.getValor() == null ? BigDecimal.ZERO : rhReporteUtilidades.getValor());
                rhReporteUtilidades.setImpuestoRenta(item.getUtiImpuestoRenta());
                rhReporteUtilidades.setImpuestoRenta(rhReporteUtilidades.getImpuestoRenta() == null ? BigDecimal.ZERO : rhReporteUtilidades.getImpuestoRenta());
                BigDecimal valorARecibir = rhReporteUtilidades.getValor().subtract(rhReporteUtilidades.getImpuestoRenta());
                rhReporteUtilidades.setValorARecibir(valorARecibir);

                rhReporteUtilidadesList.add(rhReporteUtilidades);
            }
        }
        return genericReporteService.generarReporte(modulo, "reportComprobanteUtilidades.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), rhReporteUtilidadesList);
    }

    @Override
    public byte[] generarReporteRRHHMatricialLiquidacion(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<ReportesRol> listaLiquidacion = new ArrayList<>();
        int index = 0;
        for (ItemStatusItemListaConContableTO con : listado) {
            con = listado.get(index);
            index++;
            ConContable contable = null;
            List<RhRol> listLiquidacion = rolService.getListRhRol(con.toConContablePK());
            contable = contableService.getConContable(con.toConContablePK());
            if (listLiquidacion != null && !listLiquidacion.isEmpty()) {
                List<RhListaDetalleRolesTO> listaDetalleRolesTO = new ArrayList<>();
                for (RhRol itemRol : listLiquidacion) {
                    listaDetalleRolesTO.addAll(eliminarDetallesRolesNulos(rolService.getRhDetalleRolesTO(
                            con.getEmpCodigo(),
                            UtilsDate.fechaFormatoString(contable.getConFecha()),
                            UtilsDate.fechaFormatoString(contable.getConFecha()),
                            itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(),
                            itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                            itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                            "LIQUIDACIONES"), false, null));
                }
                for (RhRol itemRol : listLiquidacion) {
                    for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                        convertirValoresItemDetalleVaciosACero(itemDetalle);
                        listaLiquidacion.add(new ReportesRol(itemDetalle, "", "", "", "", "", null, "",
                                itemRol.getPrdSector().getPrdSectorPK().getSecCodigo()));
                    }
                }
            }
        }
        return genericReporteService.generarReporte(modulo, "reportComprobanteLiquidacion.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listaLiquidacion);
    }

    @Override
    public byte[] generarReporteComprobanteVacaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String vacacionesDias, String vacacionesDesde, String vacacionesHasta, String empleadoNombre, String empleadoApellido, String idEmpleado, String gerente) throws Exception {
        List<ReporteEmpleado> list = new ArrayList<>();
        ReporteEmpleado repEmp = new ReporteEmpleado();
        repEmp.setEmpDiasVacaciones(vacacionesDias);
        repEmp.setEmpVacacionesDesde(vacacionesDesde);
        repEmp.setEmpVacacionesHasta(vacacionesHasta);
        repEmp.setEmpNombres(empleadoNombre);
        repEmp.setEmpApellidos(empleadoApellido);
        repEmp.setEmpId(idEmpleado);
        repEmp.setEmpresaGerente(gerente);
        list.add(repEmp);
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), list);
    }

    @Override
    public byte[] generarCertificadoTrabajo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhEmpleado> listado, String gerente) throws Exception {
        List<ReporteEmpleado> list = new ArrayList<>();
        for (RhEmpleado empleado : listado) {
            list.add(generarReporteCertificado(empleado, gerente));
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), list);
    }

    public ReporteEmpleado generarReporteCertificado(RhEmpleado empleado, String gerente) throws Exception {
        ReporteEmpleado repEmp = new ReporteEmpleado();
        repEmp.setSecCodigo(empleado.getPrdSector().getPrdSectorPK().getSecCodigo());
        repEmp.setEmpNombres(empleado.getEmpNombres());
        repEmp.setEmpApellidos(empleado.getEmpApellidos());
        repEmp.setEmpId(empleado.getRhEmpleadoPK().getEmpId());
        repEmp.setEmpFechaPrimerIngreso(UtilsDate.fechaFormatoString(empleado.getEmpFechaPrimerIngreso(), "d 'de' MMMM 'del' yyyy"));
        repEmp.setEmpFechaUltimoIngreso(UtilsDate.fechaFormatoString(empleado.getEmpFechaUltimoIngreso(), "d 'de' MMMM 'del' yyyy"));
        repEmp.setEmpFechaPrimeraSalida(UtilsDate.fechaFormatoString(empleado.getEmpFechaPrimeraSalida(), "d 'de' MMMM 'del' yyyy"));
        repEmp.setEmpFechaUltimaSalida(UtilsDate.fechaFormatoString(empleado.getEmpFechaUltimoIngreso(), "d 'de' MMMM 'del' yyyy"));
        repEmp.setEmpFechaAfiliacionIess(UtilsDate.fechaFormatoString(empleado.getEmpFechaAfiliacionIess(), "d 'de' MMMM 'del' yyyy"));

        repEmp.setEmpCargo(empleado.getEmpCargo());
        repEmp.setEmpSueldoIess(empleado.getEmpSueldoIess());
        //Certificado de Trabajo
        repEmp.setEmpInactivo(empleado.getEmpInactivo());
        SisEmpresa empresa = empresaDao.obtenerPorId(SisEmpresa.class,
                empleado.getRhEmpleadoPK().getEmpEmpresa());
        repEmp.setEmpresaNombre(empresa.getEmpNombre());
        repEmp.setEmpresaRUC(empresa.getEmpRuc());
        repEmp.setEmpresaCiudad(empresa.getEmpCiudad());
        repEmp.setEmpresaGerente(gerente);
        // SRI
        repEmp.setEmpSueldoOtraCompania(empleado.getEmpSueldoOtraCompania());
        return repEmp;
    }

    //exportar parametros
    @Override
    public Map<String, Object> exportarReporteParametros(List<RhParametros> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) {
        try {
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            listaCabecera.add("SReporte de Parametros");
            listaCabecera.add("SLista de parametros de configuración");
            listaCabecera.add("S");
            listaCuerpo.add("SDesde" + "¬" + "SHasta" + "¬" + "SPorcentaje Individual" + "¬" + "SPorcentaje Aporte Extendido" + "¬"
                    + "SPorcentaje Aporte Patronal" + "¬" + "¬" + "SPorcentaje Aporte ICE" + "¬" + "SPorcentaje Aporte CECAP" + "¬" + "SSalario minimo vital");
            for (RhParametros parametro : listado) {
                listaCuerpo.add(
                        (parametro.getParDesde() == null ? "B" : "S" + UtilsDate.fechaFormatoString(parametro.getParHasta(), "dd-MM-yyyy"))
                        + "¬" + (parametro.getParHasta() == null ? "B" : "S" + UtilsDate.fechaFormatoString(parametro.getParHasta(), "dd-MM-yyyy"))
                        + "¬" + (parametro.getParIessPorcentajeAporteIndividual() == null ? "B" : "S" + parametro.getParIessPorcentajeAporteIndividual())
                        + "¬" + (parametro.getParIessPorcentajeAporteExtendido() == null ? "B" : "S" + parametro.getParIessPorcentajeAporteExtendido())
                        + "¬" + (parametro.getParIessPorcentajeAportePatronal() == null ? "B" : "S" + parametro.getParIessPorcentajeAportePatronal())
                        + "¬" + (parametro.getParIessPorcentajeIece() == null ? "B" : "S" + parametro.getParIessPorcentajeIece())
                        + "¬" + (parametro.getParIessPorcentajeSecap() == null ? "B" : "S" + parametro.getParIessPorcentajeSecap())
                        + "¬" + (parametro.getParSalarioMinimoVital() == null ? "B" : "D" + parametro.getParSalarioMinimoVital())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteUtilidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhFunUtilidadesConsultarTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            listaCabecera.add("SReporte Utilidades");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCategoría" + "¬" + "SSector" + "¬" + "SId" + "¬" + "SNombres" + "¬" + "SApellidos" + "¬"
                    + "SGenero" + "¬" + "SFecha Ingreso" + "¬" + "SCargo" + "¬" + "SDías Laborados"
                    + "¬" + "SValor Utilidades" + "¬" + "SImpuesto a la Renta" + "¬" + "SValor a Recibir" + "¬" + "SForma de Pago" + "¬" + "SPeriodo" + "¬" + "STipo" + "¬"
                    + "SNumero");
            for (RhFunUtilidadesConsultarTO rhFunUtilidadesConsultarTO : listado) {

                rhFunUtilidadesConsultarTO.setUtiValorUtilidades(rhFunUtilidadesConsultarTO.getUtiValorUtilidades() == null ? BigDecimal.ZERO : rhFunUtilidadesConsultarTO.getUtiValorUtilidades());
                rhFunUtilidadesConsultarTO.setUtiImpuestoRenta(rhFunUtilidadesConsultarTO.getUtiImpuestoRenta() == null ? BigDecimal.ZERO : rhFunUtilidadesConsultarTO.getUtiImpuestoRenta());
                BigDecimal valorARecibir = rhFunUtilidadesConsultarTO.getUtiValorUtilidades().subtract(rhFunUtilidadesConsultarTO.getUtiImpuestoRenta());

                listaCuerpo.add((rhFunUtilidadesConsultarTO.getUtiCategoria() == null ? "B"
                        : "S" + rhFunUtilidadesConsultarTO.getUtiCategoria())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiSector() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiSector())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiId() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiId())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiNombres() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiNombres())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiApellidos() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiApellidos())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiGenero() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiGenero())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiFechaIngreso() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiFechaIngreso())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiCargo() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiCargo())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiDiasLaborados() == null ? "B" : "I" + rhFunUtilidadesConsultarTO.getUtiDiasLaborados().toString())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiValorUtilidades() == null ? "F" : "F" + rhFunUtilidadesConsultarTO.getUtiValorUtilidades().toString())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiImpuestoRenta() == null ? "F" : "F" + rhFunUtilidadesConsultarTO.getUtiImpuestoRenta().toString())
                        + "¬" + (valorARecibir == null ? "F" : "F" + valorARecibir.toString())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiFormaPago() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiFormaPago())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiPeriodo() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiPeriodo())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiTipo() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiTipo())
                        + "¬" + (rhFunUtilidadesConsultarTO.getUtiNumero() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getUtiNumero()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarVacacionesGozadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhVacacionesGozadas> listado) throws Exception {
        try {
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList<>();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SNúmero" + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SPeriodo Desde" + "¬" + "SPeriodo Hasta" + "¬" + "SDesde" + "¬" + "SHasta" + "¬"
                    + "SObservaciones");
            for (RhVacacionesGozadas objeto : listado) {
                listaCuerpo
                        .add((objeto.getRhVacacionesGozadasPK().getVacNumero() == null ? "B" : "S" + objeto.getRhVacacionesGozadasPK().getVacNumero())
                                + "¬"
                                + (objeto.getRhEmpleado().getRhEmpleadoPK().getEmpId() == null ? "B" : "S" + objeto.getRhEmpleado().getRhEmpleadoPK().getEmpId())
                                + "¬"
                                + (objeto.getRhEmpleado().getEmpNombres() == null ? "B" : "S" + objeto.getRhEmpleado().getEmpNombres() + " " + objeto.getRhEmpleado().getEmpApellidos())
                                + "¬"
                                + (objeto.getVacDesde() == null ? "B" : "T" + UtilsDate.fechaFormatoString(objeto.getVacDesde(), "dd-MM-yyyy"))
                                + "¬"
                                + (objeto.getVacHasta() == null ? "B" : "T" + UtilsDate.fechaFormatoString(objeto.getVacHasta(), "dd-MM-yyyy"))
                                + "¬"
                                + (objeto.getVacGozadasDesde() == null ? "B" : "T" + UtilsDate.fechaFormatoString(objeto.getVacGozadasDesde(), "dd-MM-yyyy"))
                                + "¬"
                                + (objeto.getVacGozadasHasta() == null ? "B" : "T" + UtilsDate.fechaFormatoString(objeto.getVacGozadasHasta(), "dd-MM-yyyy"))
                                + "¬"
                                + (objeto.getVacObservaciones() == null ? "B" : "S" + objeto.getVacObservaciones()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarVacaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhVacaciones> listado) throws Exception {
        try {
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList<>();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");

            listaCuerpo.add("SNúmero" + "¬" + "SN. Identificación" + "¬" + "SNombres" + "¬" + "SPeriodo Desde" + "¬" + "SPeriodo Hasta" + "¬" + "SDesde" + "¬" + "SHasta" + "¬"
                    + "SForma de Pago");
            for (RhVacaciones objeto : listado) {
                listaCuerpo
                        .add((objeto.getVacSecuencial() == null ? "B" : "S" + objeto.getVacSecuencial())
                                + "¬"
                                + (objeto.getRhEmpleado().getRhEmpleadoPK().getEmpId() == null ? "B" : "S" + objeto.getRhEmpleado().getRhEmpleadoPK().getEmpId())
                                + "¬"
                                + (objeto.getRhEmpleado().getEmpNombres() == null ? "B" : "S" + objeto.getRhEmpleado().getEmpNombres() + " " + objeto.getRhEmpleado().getEmpApellidos())
                                + "¬"
                                + (objeto.getVacDesde() == null ? "B" : "T" + UtilsDate.fechaFormatoString(objeto.getVacDesde(), "dd-MM-yyyy"))
                                + "¬"
                                + (objeto.getVacHasta() == null ? "B" : "T" + UtilsDate.fechaFormatoString(objeto.getVacHasta(), "dd-MM-yyyy"))
                                + "¬"
                                + (objeto.getVacGozadasDesde() == null ? "B" : "T" + UtilsDate.fechaFormatoString(objeto.getVacGozadasDesde(), "dd-MM-yyyy"))
                                + "¬"
                                + (objeto.getVacGozadasHasta() == null ? "B" : "T" + UtilsDate.fechaFormatoString(objeto.getVacGozadasHasta(), "dd-MM-yyyy"))
                                + "¬"
                                + (objeto.getVacFormaPago() == null ? "B" : "S" + objeto.getVacFormaPago()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhRolEmpTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Liquidacion");
            listaCabecera.add("S");
            listaCuerpo.add("SIdentificacion" + "¬" + "SApellidos y Nombres" + "¬" + "SCargo" + "¬" + "SFecha registro" + "¬" + "SFecha fin de labores" + "¬" + "SMotivo salida" + "¬" + "SSaldo anterior" + "¬" + "SDécimo tercer sueldo" + "¬"
                    + "SDécimo cuarto sueldo" + "¬" + "SVacaciones" + "¬" + "SAnticipos" + "¬" + "SPréstamos" + "¬" + "SSalario digno" + "¬" + "SBonificación deshaucio" + "¬" + "SDespido intempestivo" + "¬" + "SBonificacion voluntaria" + "¬"
                    + "STotal" + "¬" + "SForma de pago" + "¬" + "SDocumento" + "¬" + "SContable");
            for (RhRolEmpTO listaLiquidacion : listado) {
                listaCuerpo.add((listaLiquidacion.getRhEmpleado().getRhEmpleadoPK().getEmpId() == null ? "B" : "S" + listaLiquidacion.getRhEmpleado().getRhEmpleadoPK().getEmpId()) + "¬"
                        + (listaLiquidacion.getRhEmpleado().getEmpApellidos() == null ? "B" : "S" + listaLiquidacion.getRhEmpleado().getEmpApellidos() + " " + listaLiquidacion.getRhEmpleado().getEmpNombres()) + "¬"
                        + (listaLiquidacion.getRhEmpleado().getEmpCargo() == null ? "B" : "S" + listaLiquidacion.getRhEmpleado().getEmpCargo()) + "¬"
                        + (listaLiquidacion.getRolDesde() == null ? "B" : "T" + (UtilsDate.fechaFormatoString(listaLiquidacion.getRolDesde(), "dd-MM-yyyy"))) + "¬"
                        + (listaLiquidacion.getRhEmpleado().getEmpFechaUltimaSalida() == null ? "T" + (UtilsDate.fechaFormatoString(listaLiquidacion.getRhEmpleado().getEmpFechaPrimeraSalida(), "dd-MM-yyyy")) : "T" + (UtilsDate.fechaFormatoString(listaLiquidacion.getRhEmpleado().getEmpFechaUltimaSalida(), "dd-MM-yyyy"))) + "¬"
                        + (listaLiquidacion.getRhEmpleado().getEmpMotivoSalida() == null ? "B" : "S" + listaLiquidacion.getRhEmpleado().getEmpMotivoSalida()) + "¬"
                        + (listaLiquidacion.getRolSaldoAnterior() == null ? "B" : "D" + listaLiquidacion.getRolSaldoAnterior()) + "¬"
                        + (listaLiquidacion.getRolLiqXiii() == null ? "B" : "D" + listaLiquidacion.getRolLiqXiii()) + "¬"
                        + (listaLiquidacion.getRolLiqXiv() == null ? "B" : "D" + listaLiquidacion.getRolLiqXiv()) + "¬"
                        + (listaLiquidacion.getRolLiqVacaciones() == null ? "B" : "D" + listaLiquidacion.getRolLiqVacaciones()) + "¬"
                        + (listaLiquidacion.getRolAnticipos() == null ? "B" : "D" + listaLiquidacion.getRolAnticipos()) + "¬"
                        + (listaLiquidacion.getRolPrestamos() == null ? "B" : "D" + listaLiquidacion.getRolPrestamos()) + "¬"
                        + (listaLiquidacion.getRolLiqSalarioDigno() == null ? "B" : "D" + listaLiquidacion.getRolLiqSalarioDigno()) + "¬"
                        + (listaLiquidacion.getRolLiqDesahucio() == null ? "B" : "D" + listaLiquidacion.getRolLiqDesahucio()) + "¬"
                        + (listaLiquidacion.getRolLiqDesahucioIntempestivo() == null ? "B" : "D" + listaLiquidacion.getRolLiqDesahucioIntempestivo()) + "¬"
                        + (listaLiquidacion.getRolLiqBonificacion() == null ? "B" : "D" + listaLiquidacion.getRolLiqBonificacion()) + "¬"
                        + (listaLiquidacion.getRolTotal() == null ? "B" : "D" + listaLiquidacion.getRolTotal()) + "¬"
                        + (listaLiquidacion.getRolFormaPago() == null ? "B" : "S" + listaLiquidacion.getRolFormaPago()) + "¬"
                        + (listaLiquidacion.getRolDocumento() == null ? "B" : "S" + listaLiquidacion.getRolDocumento()) + "¬"
                        + (listaLiquidacion.getConNumero() == null ? "B" : "S" + listaLiquidacion.getConPeriodo() + "|" + listaLiquidacion.getConTipo() + "|" + listaLiquidacion.getConNumero()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarRolNotificaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhRolPagoNotificaciones> lista, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            listaCabecera.add("SReporte notificaciones enviadas por roles de pago");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SContable" + "SFecha");
            for (RhRolPagoNotificaciones rhFunUtilidadesConsultarTO : lista) {
                listaCuerpo.add((rhFunUtilidadesConsultarTO.getRpnContable() == null ? "B" : "S" + rhFunUtilidadesConsultarTO.getRpnContable())
                        + "¬" + (rhFunUtilidadesConsultarTO.getRpnFecha() == null ? "B" : "S" + UtilsDate.DeDateAString(rhFunUtilidadesConsultarTO.getRpnFecha())));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

}
