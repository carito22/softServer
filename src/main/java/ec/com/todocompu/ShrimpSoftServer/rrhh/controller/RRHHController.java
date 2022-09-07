package ec.com.todocompu.ShrimpSoftServer.rrhh.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.report.AuxiliarReporteDetalleRoles;
import ec.com.todocompu.ShrimpSoftServer.rrhh.report.ReporteRrhhService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.AnticipoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.AnticipoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.BonoConceptoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.BonoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.BonoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.CategoriaService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.EmpleadoDescuentoFijoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.EmpleadoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.FormaPagoBeneficioSocialService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.FormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.Formulario107PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.Formulario107Service;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.ParametrosService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.PrestamoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.PrestamoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RelacionTrabajoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RolMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RolService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.SalarioDignoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.UtilidadMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.UtilidadPeriodoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.UtilidadService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.VacacionesService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XiiiSueldoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XiiiSueldoPeriodoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XiiiSueldoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XivSueldoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XivSueldoPeriodoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XivSueldoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.RhPreavisoAnticiposPrestamosSueldoMachalaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnticipoMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnulacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAvisoEntradaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCabeceraReporteRolColectivoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhContableTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaIessTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhDetalleVacionesPagadasGozadasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoDescuentosFijosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107PeriodoFiscalTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107_2013TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunListadoEmpleadosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunPlantillaSueldosLotePreliminarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunPlantillaSueldosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetallePrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleViaticosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaRolSaldoEmpleadoDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoSueldosPorPagarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoIndividualAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoPichinchaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSaldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSueldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhSalarioDignoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBono;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRelacionTrabajo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteAnticipoPrestamoXIIISueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteConsultaAnticiposLote;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteConsultaBonosLote;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReportesRol;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import org.postgresql.util.PSQLException;

@RestController
@RequestMapping("/todocompuWS/RRHHController")
public class RRHHController {

    @Autowired
    private FormaPagoService formaPagoService;
    @Autowired
    private AnticipoMotivoService anticipoMotivoService;
    @Autowired
    private BonoMotivoService bonoMotivoService;
    @Autowired
    private PrestamoMotivoService prestamoMotivoService;
    @Autowired
    private RolMotivoService rolMotivoService;
    @Autowired
    private XiiiSueldoMotivoService xiiiSueldoMotivoService;
    @Autowired
    private XivSueldoMotivoService xivSueldoMotivoService;
    @Autowired
    private UtilidadMotivoService utilidadMotivoService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private EmpleadoDescuentoFijoService empleadoDescuentoFijoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private AnticipoService anticipoService;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private XiiiSueldoService xiiiSueldoService;

    @Autowired
    private XiiiSueldoPeriodoService xiiiSueldoPeriodoService;

    @Autowired
    private XivSueldoService xivSueldoService;

    @Autowired
    private XivSueldoPeriodoService xivSueldoPeriodoService;

    @Autowired
    private BonoService bonoService;

    @Autowired
    private Formulario107PeriodoService formulario107PeriodoService;

    @Autowired
    private BonoConceptoService bonoConceptoService;

    @Autowired
    private Formulario107Service formulario107Service;

    @Autowired
    private VacacionesService vacacionesService;

    @Autowired
    private SalarioDignoService salarioDignoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private RelacionTrabajoService relacionTrabajoService;

    @Autowired
    private UtilidadPeriodoService utilidadPeriodoService;

    @Autowired
    private ParametrosService parametrosService;

    @Autowired
    private FormaPagoBeneficioSocialService formaPagoBeneficioSocialService;

    @Autowired
    private UtilidadService utilidadService;

    @Autowired
    private EnviarCorreoService envioCorreoService;

    @Autowired
    private ReporteRrhhService reporteRrhhService;

    @Autowired
    private ContableService contableService;

    @RequestMapping("/getRhConsolidadoBonosTO")
    public List<RhListaConsolidadoBonosTO> getRhConsolidadoBonosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoService.getRhConsolidadoBonosTO(empCodigo, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhSaldoConsolidadoBonosTO")
    public List<RhListaSaldoConsolidadoBonosTO> getRhSaldoConsolidadoBonosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoService.getRhSaldoConsolidadoBonosTO(empCodigo, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhAnticipoMotivo")
    public MensajeTO insertarRhAnticipoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhAnticipoMotivo rhAnticipoMotivo = UtilsJSON.jsonToObjeto(RhAnticipoMotivo.class, map.get("rhAnticipoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoMotivoService.insertarRhAnticipoMotivo(rhAnticipoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarRhAnticipoMotivo")
    public String modificarRhAnticipoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhAnticipoMotivo rhAnticipoMotivo = UtilsJSON.jsonToObjeto(RhAnticipoMotivo.class, map.get("rhAnticipoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoMotivoService.modificarRhAnticipoMotivo(rhAnticipoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarRhAnticipoMotivo")
    public String eliminarRhAnticipoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhAnticipoMotivo rhAnticipoMotivo = UtilsJSON.jsonToObjeto(RhAnticipoMotivo.class, map.get("rhAnticipoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoMotivoService.eliminarRhAnticipoMotivo(rhAnticipoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhAnticipoMotivo")
    public List<RhAnticipoMotivo> getListaRhAnticipoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoMotivoService.getListaRhAnticipoMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhBonoMotivo")
    public MensajeTO insertarRhBonoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoMotivo rhBonoMotivo = UtilsJSON.jsonToObjeto(RhBonoMotivo.class, map.get("rhBonoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoMotivoService.insertarRhBonoMotivo(rhBonoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarRhBonoMotivo")
    public String modificarRhBonoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoMotivo rhBonoMotivo = UtilsJSON.jsonToObjeto(RhBonoMotivo.class, map.get("rhBonoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoMotivoService.modificarRhBonoMotivo(rhBonoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarRhBonoMotivo")
    public String eliminarRhBonoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoMotivo rhBonoMotivo = UtilsJSON.jsonToObjeto(RhBonoMotivo.class, map.get("rhBonoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoMotivoService.eliminarRhBonoMotivo(rhBonoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhBonoMotivo")
    public List<RhBonoMotivo> getListaRhBonoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoMotivoService.getListaRhBonoMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhXiiiSueldoMotivo")
    public MensajeTO insertarRhXiiiSueldoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXiiiSueldoMotivo rhXiiiSueldoMotivo = UtilsJSON.jsonToObjeto(RhXiiiSueldoMotivo.class,
                map.get("rhXiiiSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoMotivoService.insertarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarRhXiiiSueldoMotivo")
    public String modificarRhXiiiSueldoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXiiiSueldoMotivo rhXiiiSueldoMotivo = UtilsJSON.jsonToObjeto(RhXiiiSueldoMotivo.class,
                map.get("rhXiiiSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoMotivoService.modificarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarRhXiiiSueldoMotivo")
    public String eliminarRhXiiiSueldoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXiiiSueldoMotivo rhXiiiSueldoMotivo = UtilsJSON.jsonToObjeto(RhXiiiSueldoMotivo.class,
                map.get("rhXiiiSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoMotivoService.eliminarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhXiiiSueldoMotivo")
    public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoMotivoService.getListaRhXiiiSueldoMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhXivSueldoMotivo")
    public MensajeTO insertarRhXivSueldoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXivSueldoMotivo rhXivSueldoMotivo = UtilsJSON.jsonToObjeto(RhXivSueldoMotivo.class,
                map.get("rhXivSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoMotivoService.insertarRhXivSueldoMotivo(rhXivSueldoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarRhXivSueldoMotivo")
    public String modificarRhXivSueldoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXivSueldoMotivo rhXivSueldoMotivo = UtilsJSON.jsonToObjeto(RhXivSueldoMotivo.class,
                map.get("rhXivSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoMotivoService.modificarRhXivSueldoMotivo(rhXivSueldoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarRhXivSueldoMotivo")
    public String eliminarRhXivSueldoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXivSueldoMotivo rhXivSueldoMotivo = UtilsJSON.jsonToObjeto(RhXivSueldoMotivo.class,
                map.get("rhXivSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoMotivoService.eliminarRhXivSueldoMotivo(rhXivSueldoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhXivSueldoMotivo")
    public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoMotivoService.getListaRhXivSueldoMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhUtilidadMotivo")
    public MensajeTO insertarRhUtilidadMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhUtilidadMotivo rhUtilidadMotivo = UtilsJSON.jsonToObjeto(RhUtilidadMotivo.class, map.get("rhUtilidadMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadMotivoService.insertarRhUtilidadMotivo(rhUtilidadMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarRhUtilidadMotivo")
    public String modificarRhUtilidadMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhUtilidadMotivo rhUtilidadMotivo = UtilsJSON.jsonToObjeto(RhUtilidadMotivo.class, map.get("rhUtilidadMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadMotivoService.modificarRhUtilidadMotivo(rhUtilidadMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarRhUtilidadMotivo")
    public String eliminarRhUtilidadMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhUtilidadMotivo rhUtilidadMotivo = UtilsJSON.jsonToObjeto(RhUtilidadMotivo.class, map.get("rhUtilidadMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadMotivoService.eliminarRhUtilidadMotivo(rhUtilidadMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhUtilidadMotivo")
    public List<RhUtilidadMotivo> getListaRhUtilidadMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadMotivoService.getListaRhUtilidadMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhPrestamoMotivo")
    public MensajeTO insertarRhPrestamoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhPrestamoMotivo rhPrestamoMotivo = UtilsJSON.jsonToObjeto(RhPrestamoMotivo.class, map.get("rhPrestamoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoMotivoService.insertarRhPrestamoMotivo(rhPrestamoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarRhPrestamoMotivo")
    public String modificarRhPrestamoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhPrestamoMotivo rhPrestamoMotivo = UtilsJSON.jsonToObjeto(RhPrestamoMotivo.class, map.get("rhPrestamoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoMotivoService.modificarRhPrestamoMotivo(rhPrestamoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarRhPrestamoMotivo")
    public String eliminarRhPrestamoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhPrestamoMotivo rhPrestamoMotivo = UtilsJSON.jsonToObjeto(RhPrestamoMotivo.class, map.get("rhPrestamoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoMotivoService.eliminarRhPrestamoMotivo(rhPrestamoMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhPrestamoMotivo")
    public List<RhPrestamoMotivo> getListaRhPrestamoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoMotivoService.getListaRhPrestamoMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhRolMotivo")
    public String insertarRhRolMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRolMotivo rhRolMotivo = UtilsJSON.jsonToObjeto(RhRolMotivo.class, map.get("rhRolMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolMotivoService.insertarRhRolMotivo(rhRolMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarRhRolMotivo")
    public String modificarRhRolMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRolMotivo rhRolMotivo = UtilsJSON.jsonToObjeto(RhRolMotivo.class, map.get("rhRolMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolMotivoService.modificarRhRolMotivo(rhRolMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarRhRolMotivo")
    public String eliminarRhRolMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRolMotivo rhRolMotivo = UtilsJSON.jsonToObjeto(RhRolMotivo.class, map.get("rhRolMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolMotivoService.eliminarRhRolMotivo(rhRolMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhRolMotivo")
    public List<RhRolMotivo> getListaRhRolMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolMotivoService.getListaRhRolMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarModificarRhProvisiones")
    public MensajeTO insertarModificarRhProvisiones(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhRol> listaRhRol = UtilsJSON.jsonToList(RhRol.class, map.get("listaRhRol"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = rolService.insertarModificarRhProvisiones(conContable, listaRhRol, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @RequestMapping("/getPermisoAccionesRol")
    public boolean getPermisoAccionesRol(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String fechaContable = UtilsJSON.jsonToObjeto(String.class, map.get("fechaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getPermisoAcciones(conContablePK, fechaContable);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarModificarRhRol")
    public MensajeTO insertarModificarRhRol(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhRol> listaRhRol = UtilsJSON.jsonToList(RhRol.class, map.get("listaRhRol"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = rolService.insertarModificarRhRolEscritorio(conContable, listaRhRol, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            } else {
                envioCorreoService.enviarError("SERVER",
                        UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return mensajeTO;
    }

    @RequestMapping("/getListRhRol")
    public List<RhRol> getListRhRol(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getListRhRol(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPermisoAccionesAnticipo")
    public boolean getPermisoAccionesAnticipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String fechaContable = UtilsJSON.jsonToObjeto(String.class, map.get("fechaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoService.getPermisoAcciones(conContablePK, fechaContable);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarModificarRhAnticipo")
    public MensajeTO insertarModificarRhAnticipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhAnticipo> listaRhAnticipo = UtilsJSON.jsonToList(RhAnticipo.class, map.get("listaRhAnticipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = anticipoService.insertarRhAnticipoEscritorio(conContable, listaRhAnticipo, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            } else {
                envioCorreoService.enviarError("SERVER",
                        UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return mensajeTO;
    }

    @RequestMapping("/getListRhAnticipo")
    public List<RhAnticipo> getListRhAnticipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoService.getListRhAnticipo(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPermisoAccionesXiiiSueldo")
    public boolean getPermisoAccionesXiiiSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String fechaContable = UtilsJSON.jsonToObjeto(String.class, map.get("fechaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoService.getPermisoAcciones(conContablePK, fechaContable);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarModificarRhXiiiSueldo")
    public MensajeTO insertarModificarRhXiiiSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhXiiiSueldo> listaRhXiiiSueldo = UtilsJSON.jsonToList(RhXiiiSueldo.class, map.get("listaRhXiiiSueldo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            boolean pendiente = conContable.getConPendiente();
            mensajeTO = xiiiSueldoService.insertarModificarRhXiiiSueldo(conContable, listaRhXiiiSueldo, sisInfoTO);
            if (!pendiente) {
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            } else {
                envioCorreoService.enviarError("SERVER",
                        UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return mensajeTO;
    }

    @RequestMapping("/getListRhXiiiSueldo")
    public List<RhXiiiSueldo> getListRhXiiiSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoService.getListRhXiiiSueldo(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPermisoAccionesUtilidad")
    public boolean getPermisoAccionesUtilidad(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String fechaContable = UtilsJSON.jsonToObjeto(String.class, map.get("fechaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadService.getPermisoAcciones(conContablePK, fechaContable);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarModificarRhUtilidades")
    public MensajeTO insertarModificarRhUtilidades(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhUtilidades> listaRhUtilidades = UtilsJSON.jsonToList(RhUtilidades.class, map.get("listaRhUtilidades"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            boolean pendiente = conContable.getConPendiente();
            mensajeTO = utilidadService.insertarModificarRhUtilidades(conContable, listaRhUtilidades, sisInfoTO);
            if (!pendiente) {
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            } else {
                envioCorreoService.enviarError("SERVER",
                        UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return mensajeTO;
    }

    @RequestMapping("/getListRhUtilidades")
    public List<RhUtilidades> getListRhUtilidades(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadService.getListRhUtilidades(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPermisoAccionesXivSueldo")
    public boolean getPermisoAccionesXivSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String fechaContable = UtilsJSON.jsonToObjeto(String.class, map.get("fechaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoService.getPermisoAcciones(conContablePK, fechaContable);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarModificarRhXivSueldo")
    public MensajeTO insertarModificarRhXivSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhXivSueldo> listaRhXivSueldo = UtilsJSON.jsonToList(RhXivSueldo.class, map.get("listaRhXivSueldo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            boolean pendiente = conContable.getConPendiente();
            mensajeTO = xivSueldoService.insertarModificarRhXivSueldo(conContable, listaRhXivSueldo, sisInfoTO);
            if (!pendiente) {
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            } else {
                envioCorreoService.enviarError("SERVER",
                        UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return mensajeTO;
    }

    @RequestMapping("/getListRhXivSueldo")
    public List<RhXivSueldo> getListRhXivSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoService.getListRhXivSueldo(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPermisoAccionesPrestamo")
    public boolean getPermisoAccionesPrestamo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String fechaContable = UtilsJSON.jsonToObjeto(String.class, map.get("fechaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoService.getPermisoAcciones(conContablePK, fechaContable);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarModificarRhPrestamo")
    public MensajeTO insertarModificarRhPrestamo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        RhPrestamo rhPrestamo = UtilsJSON.jsonToObjeto(RhPrestamo.class, map.get("rhPrestamo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = prestamoService.insertarModificarRhPrestamoWeb(conContable, rhPrestamo, null, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            } else {
                envioCorreoService.enviarError("SERVER",
                        UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return mensajeTO;
    }

    @RequestMapping("/getListRhPrestamo")
    public List<RhPrestamo> getListRhPrestamo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoService.getListRhPrestamo(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPermisoAccionesBono")
    public boolean getPermisoAccionesBono(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String fechaContable = UtilsJSON.jsonToObjeto(String.class, map.get("fechaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoService.getPermisoAcciones(conContablePK, fechaContable);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarModificarRhBono")
    public MensajeTO insertarModificarRhBono(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhBono> listaRhBono = UtilsJSON.jsonToList(RhBono.class, map.get("listaRhBono"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = bonoService.insertarRhBonoEscritorio(conContable, listaRhBono, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            } else {
                envioCorreoService.enviarError("SERVER",
                        UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return mensajeTO;
    }

    @RequestMapping("/getListRhBono")
    public List<RhBono> getListRhBono(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoService.getListRhBono(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaEmpleado")
    public List<RhEmpleado> getListaEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getListaEmpleado(empresa, buscar, estado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getEmpleado")
    public RhEmpleado getEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String id = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getEmpleado(empresa, id);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getEmpleadoDescuentosFijos")
    public List<RhEmpleadoDescuentosFijos> getEmpleadoDescuentosFijos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, map.get("empleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoDescuentoFijoService.getEmpleadoDescuentosFijos(empresa, empleado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarModificarRhEmpleado")
    public MensajeTO insertarModificarRhEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhEmpleado rhEmpleado = UtilsJSON.jsonToObjeto(RhEmpleado.class, map.get("rhEmpleado"));
        List<RhEmpleadoDescuentosFijos> listEmpleadoDescuentosFijos = UtilsJSON
                .jsonToList(RhEmpleadoDescuentosFijos.class, map.get("listEmpleadoDescuentosFijos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.insertarModificarRhEmpleado(rhEmpleado, listEmpleadoDescuentosFijos, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarRhEmpleado")
    public String eliminarRhEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhEmpleadoPK rhEmpleadoPK = UtilsJSON.jsonToObjeto(RhEmpleadoPK.class, map.get("rhEmpleadoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.eliminarRhEmpleado(rhEmpleadoPK, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionRhCategoria")
    public String accionRhCategoria(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhCategoriaTO rhCategoriaTO = UtilsJSON.jsonToObjeto(RhCategoriaTO.class, map.get("rhCategoriaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return categoriaService.accionRhCategoria(rhCategoriaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhCategoriaTO")
    public RhCategoriaTO getRhCategoriaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String catNombre = UtilsJSON.jsonToObjeto(String.class, map.get("catNombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return categoriaService.getRhCategoriaTO(empCodigo, catNombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhCategoriaTO")
    public List<RhCategoriaTO> getListaRhCategoriaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return categoriaService.getListaRhCategoriaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/listarRhRelacionTrabajo")
    public List<RhRelacionTrabajo> listarRhRelacionTrabajo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return relacionTrabajoService.listarRhRelacionTrabajo();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhCategoriaCuentasTO")
    public List<RhCategoriaTO> getListaRhCategoriaCuentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return categoriaService.getListaRhCategoriaCuentasTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboRhCategoriaTO")
    public List<RhComboCategoriaTO> getComboRhCategoriaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return categoriaService.getComboRhCategoriaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhEmpleado")
    public String insertarRhEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhEmpleadoTO rhEmpleadoTO = UtilsJSON.jsonToObjeto(RhEmpleadoTO.class, map.get("rhEmpleadoTO"));
        List<RhEmpleadoDescuentosFijosTO> ListarhEmpleadoDescuentosFijosTO = UtilsJSON
                .jsonToList(RhEmpleadoDescuentosFijosTO.class, map.get("ListarhEmpleadoDescuentosFijosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.insertarRhEmpleado(rhEmpleadoTO, ListarhEmpleadoDescuentosFijosTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/guardarImagenEmpleado")
    public String guardarImagenEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] imagen = UtilsJSON.jsonToObjeto(byte[].class, map.get("imagen"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.guardarImagenEmpleado(imagen, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarImagenEmpleado")
    public String eliminarImagenEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.eliminarImagenEmpleado(nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerImagenEmpleado")
    public byte[] obtenerImagenEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.obtenerImagenEmpleado(nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerRutaImagenEmpleado")
    public String obtenerRutaImagenEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.obtenerRutaImagenEmpleado(nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarRhEmpleado")
    public String modificarRhEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhEmpleadoTO rhEmpleadoTO = UtilsJSON.jsonToObjeto(RhEmpleadoTO.class, map.get("rhEmpleadoTO"));
        List<RhEmpleadoDescuentosFijosTO> listaModificar = UtilsJSON.jsonToList(RhEmpleadoDescuentosFijosTO.class,
                map.get("listaModificar"));
        List<RhEmpleadoDescuentosFijosTO> listaEliminar = UtilsJSON.jsonToList(RhEmpleadoDescuentosFijosTO.class,
                map.get("listaEliminar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.modificarRhEmpleado(rhEmpleadoTO, listaModificar, listaEliminar, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarRhEmpleadoTO")
    public String eliminarRhEmpleadoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhEmpleadoTO rhEmpleadoTO = UtilsJSON.jsonToObjeto(RhEmpleadoTO.class, map.get("rhEmpleadoTO"));
        List<RhEmpleadoDescuentosFijosTO> listaEliminar = UtilsJSON.jsonToList(RhEmpleadoDescuentosFijosTO.class,
                map.get("listaEliminar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.eliminarRhEmpleado(rhEmpleadoTO, listaEliminar, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhEmpleadoTO")
    public List<RhEmpleadoTO> getListaRhEmpleadoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getListaRhEmpleadoTO(empresa, buscar, estado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunListadoEmpleadosTO")
    public List<RhFunListadoEmpleadosTO> getRhFunListadoEmpleadosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String provincia = UtilsJSON.jsonToObjeto(String.class, map.get("provincia"));
        String canton = UtilsJSON.jsonToObjeto(String.class, map.get("canton"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getRhFunListadoEmpleadosTO(empresa, provincia, canton, sector, categoria, estado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConsultaEmpleadoTO")
    public List<RhListaEmpleadoTO> getListaConsultaEmpleadoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        boolean interno = UtilsJSON.jsonToObjeto(boolean.class, map.get("interno"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getListaConsultaEmpleadoTO(empresa, buscar, interno, estado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaEmpleadoLote")
    public List<RhListaEmpleadoLoteTO> getListaEmpleadoLote(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        boolean rol = UtilsJSON.jsonToObjeto(boolean.class, map.get("rol"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getListaEmpleadoLote(empresa, categoria, sector, fechaHasta, motivo, rol);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunPlantillaSueldosLote")
    public List<RhFunPlantillaSueldosLoteTO> getFunPlantillaSueldosLote(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getFunPlantillaSueldosLote(empCodigo, fechaDesde, fechaHasta, categoria, sector);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunPlantillaSueldosLotePreliminar")
    public List<RhFunPlantillaSueldosLotePreliminarTO> getFunPlantillaSueldosLotePreliminar(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getFunPlantillaSueldosLotePreliminar(empCodigo, fechaDesde, fechaHasta, categoria,
                    sector);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionRhAvisosEntrada")
    public String accionRhAvisosEntrada(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhAvisoEntradaTO rhAvisoEntradaTO = UtilsJSON.jsonToObjeto(RhAvisoEntradaTO.class, map.get("rhAvisoEntradaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.accionRhAvisosEntrada(rhAvisoEntradaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionRhFormaPago")
    public String accionRhFormaPago(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhFormaPagoTO rhFormaPagoTO = UtilsJSON.jsonToObjeto(RhFormaPagoTO.class, map.get("rhFormaPagoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formaPagoService.accionRhFormaPago(rhFormaPagoTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhFormaPagoTO")
    public List<RhListaFormaPagoTO> getListaRhFormaPagoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formaPagoService.getListaFormaPagoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboRhFormaPagoTO")
    public List<RhComboFormaPagoTO> getComboRhFormaPagoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formaPagoService.getComboFormaPagoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionRhFormaPagoBeneficioSocial")
    public String accionRhFormaPagoBeneficioSocial(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhFormaPagoBeneficioSocialTO rhFormaPagoBeneficioSocialTO = UtilsJSON
                .jsonToObjeto(RhFormaPagoBeneficioSocialTO.class, map.get("rhFormaPagoBeneficioSocialTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formaPagoBeneficioSocialService.accionRhFormaPagoBeneficioSocial(rhFormaPagoBeneficioSocialTO,
                    accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaFormaPagoBeneficioSocialTO")
    public List<RhListaFormaPagoBeneficioSocialTO> getListaRhFormaPagoBeneficioSocialTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formaPagoBeneficioSocialService.getListaFormaPagoBeneficioSocialTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboFormaPagoBeneficioSocial")
    public List<RhComboFormaPagoBeneficioSocialTO> getComboFormaPagoBeneficioSocial(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formaPagoBeneficioSocialService.getComboFormaPagoBeneficioSocialTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhVacacionesConContable")
    public RhContableTO insertarRhVacacionesConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhVacacionesTO rhVacacionesTO = UtilsJSON.jsonToObjeto(RhVacacionesTO.class, map.get("rhVacacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RhContableTO rhContableTO = null;
        try {
            rhContableTO = vacacionesService.insertarRhVacacionesConContable(rhVacacionesTO, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                rhContableTO.setMensaje("M" + e.getMessage());
            } else {
                envioCorreoService.enviarError("SERVER",
                        UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return rhContableTO;
    }

    @RequestMapping("/insertarRhSalarioDignoContable")
    public RhContableTO insertarRhSalarioDignoContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhSalarioDignoTO rhSalarioDignoTO = UtilsJSON.jsonToObjeto(RhSalarioDignoTO.class, map.get("rhSalarioDignoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return salarioDignoService.insertarRhSalarioDignoContable(rhSalarioDignoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhBonoConcepto")
    public boolean insertarRhBonoConcepto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoConceptoTO rhBonoConceptoTO = UtilsJSON.jsonToObjeto(RhBonoConceptoTO.class, map.get("rhBonoConceptoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoConceptoService.insertarRhBonoConcepto(rhBonoConceptoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarRhBonoConcepto")
    public boolean modificarRhBonoConcepto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoConceptoTO rhBonoConceptoTO = UtilsJSON.jsonToObjeto(RhBonoConceptoTO.class, map.get("rhBonoConceptoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoConceptoService.modificarRhBonoConcepto(rhBonoConceptoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarRhBonoConcepto")
    public boolean eliminarRhBonoConcepto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoConceptoTO rhBonoConceptoTO = UtilsJSON.jsonToObjeto(RhBonoConceptoTO.class, map.get("rhBonoConceptoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoConceptoService.eliminarRhBonoConcepto(rhBonoConceptoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getListaRhBonoConceptoTO")
    public List<RhListaBonoConceptoTO> getListaRhBonoConceptoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoConceptoService.getListaBonoConceptoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboRhBonoConceptoTO")
    public List<RhComboBonoConceptoTO> getComboRhBonoConceptoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoConceptoService.getComboBonoConceptoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhParametros")
    public RhParametros getRhParametros(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return parametrosService.getRhParametros(fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerRhParametrosPorCodigoRelacionTrabajo")
    public RhParametros obtenerRhParametrosPorCodigoRelacionTrabajo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return parametrosService.obtenerRhParametrosPorCodigoRelacionTrabajo(fechaHasta, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerRhRolSaldoEmpleadoTO")
    public RhRolSaldoEmpleadoTO obtenerRhRolSaldoEmpleadoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, map.get("empleado"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getRhRolSaldoEmpleado(empresa, empleado, fechaDesde, fechaHasta, null);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhEmpleadoRetencion")
    public boolean getRhEmpleadoRetencion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getRhEmpleadoRetencion(empCodigo, empId);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/buscarCtaRhIess")
    public RhCtaIessTO buscarCtaRhIess(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.buscarCtaRhIess(empCodigo, empId);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhValorImpuestoRenta")
    public java.math.BigDecimal getRhValorImpuestoRenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        Integer diasLaborados = UtilsJSON.jsonToObjeto(Integer.class, map.get("diasLaborados"));
        BigDecimal rolIngresos = UtilsJSON.jsonToObjeto(BigDecimal.class, map.get("rolIngresos"));
        BigDecimal rolExtras = UtilsJSON.jsonToObjeto(BigDecimal.class, map.get("rolExtras"));
        BigDecimal rolIngresosExento = UtilsJSON.jsonToObjeto(BigDecimal.class, map.get("ingresoExento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getRhValorImpuestoRenta(empCodigo, empId, fechaHasta, diasLaborados, rolIngresos, rolExtras, rolIngresosExento);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhRolSaldoEmpleadoDetallado")
    public List<RhListaRolSaldoEmpleadoDetalladoTO> getRhRolSaldoEmpleadoDetallado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getRhRolSaldoEmpleadoDetalladoTO(empCodigo, empId, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhRolSueldoEmpleadoTO")
    public RhRolSueldoEmpleadoTO getRhRolSueldoEmpleadoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getRhRolSueldoEmpleadoTO(empCodigo, empId);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhRolSaldoPrestamo")
    public java.math.BigDecimal getRhRolSaldoPrestamo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoService.getRhRolSaldoPrestamo(empCodigo, empId, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhAnulacionesTO")
    public List<RhAnulacionesTO> getRhAnulacionesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getRhAnulacionesTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhDetalleAnticiposTO")
    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoService.getRhDetalleAnticiposTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId,
                    formaPago);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhDetalleAnticiposFiltradoTO")
    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposFiltradoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        String parametro = UtilsJSON.jsonToObjeto(String.class, map.get("parametro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoService.getRhDetalleAnticiposFiltradoTO(empCodigo, fechaDesde, fechaHasta, empCategoria,
                    empId, formaPago, parametro);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhDetalleAnticiposLoteTO")
    public List<RhListaDetalleAnticiposLoteTO> getRhDetalleAnticiposLoteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        List<RhListaDetalleAnticiposLoteTO> lista;
        try {
            lista = anticipoService.getRhDetalleAnticiposLoteTO(empresa, periodo, tipo, numero);
            return lista;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhDetalleBonosLoteTO")
    public List<RhListaDetalleBonosLoteTO> getRhDetalleBonosLoteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoService.getRhDetalleBonosLoteTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhDetalleAnticiposPrestamosTO")
    public List<RhListaDetalleAnticiposPrestamosTO> getRhDetalleAnticiposPrestamosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String parametro = UtilsJSON.jsonToObjeto(String.class, map.get("parametro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoService.getRhDetalleAnticiposPrestamosTO(empCodigo, fechaDesde, fechaHasta, empCategoria,
                    empId, parametro);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhDetallePrestamosTO")
    public List<RhListaDetallePrestamosTO> getRhDetallePrestamosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoService.getRhDetallePrestamosTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhDetalleBonosTO")
    public List<RhListaDetalleBonosTO> getRhDetalleBonosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String estadoDeducible = UtilsJSON.jsonToObjeto(String.class, map.get("estadoDeducible"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoService.getRhDetalleBonosTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId,
                    estadoDeducible);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhListaDetalleBonosFiltradoTO")
    public List<RhListaDetalleBonosTO> getRhListaDetalleBonosFiltradoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String estadoDeducible = UtilsJSON.jsonToObjeto(String.class, map.get("estadoDeducible"));
        String parametro = UtilsJSON.jsonToObjeto(String.class, map.get("parametro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bonoService.getRhListaDetalleBonosFiltradoTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId,
                    estadoDeducible, parametro);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhDetalleRolesTO") // getRhDetalleRolesTO
    public List<RhListaDetalleRolesTO> getRhDetalleRolesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String filtro = UtilsJSON.jsonToObjeto(String.class, map.get("filtro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getRhDetalleRolesTO(empCodigo, fechaDesde, fechaHasta, sector, empCategoria, empId, filtro);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhDetalleVacacionesPagadasGozadasTO")
    public List<RhDetalleVacionesPagadasGozadasTO> getRhDetalleVacacionesPagadasGozadasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return vacacionesService.getRhDetalleVacacionesPagadasGozadasTO(empCodigo, empId, sector, fechaDesde,
                    fechaHasta, tipo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhConsolidadoAnticiposPrestamosTO")
    public List<RhListaConsolidadoAnticiposPrestamosTO> getRhConsolidadoAnticiposPrestamosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoService.getRhConsolidadoAnticiposPrestamosTO(empCodigo, fechaDesde, fechaHasta, empCategoria,
                    empId);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhConsolidadoRolesTO")
    public List<RhListaConsolidadoRolesTO> getRhConsolidadoRolesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getRhConsolidadoRolesTO(empCodigo, fechaDesde, fechaHasta, sector, empCategoria, empId, false);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhSaldoConsolidadoAnticiposPrestamosTO")
    public List<RhListaSaldoConsolidadoAnticiposPrestamosTO> getRhSaldoConsolidadoAnticiposPrestamosTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoService.getRhSaldoConsolidadoAnticiposPrestamosTO(empCodigo, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhSaldoConsolidadoSueldosPorPagarTO")
    public List<RhListaSaldoConsolidadoSueldosPorPagarTO> getRhSaldoConsolidadoSueldosPorPagarTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getRhSaldoConsolidadoSueldosPorPagarTO(empCodigo, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhSaldoIndividualAnticiposPrestamosTO")
    public List<RhListaSaldoIndividualAnticiposPrestamosTO> getRhSaldoIndividualAnticiposPrestamosTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoService.getRhSaldoIndividualAnticiposPrestamosTO(empCodigo, fechaDesde, fechaHasta, empId,
                    tipo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhListaProvisionesConContable")
    public RhContableTO insertarRhListaProvisionesConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String status = UtilsJSON.jsonToObjeto(String.class, map.get("status"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RhContableTO rhContableTO = null;
        try {
            rhContableTO = rolService.insertarRhListaProvisionesConContable(empresa, periodo, sector, status,
                    usuario, sisInfoTO);
            if (rhContableTO != null && rhContableTO.getMensaje() != null && rhContableTO.getMensaje().charAt(0) == 'T') {
                contableService.mayorizarDesmayorizarSql(new ConContablePK(empresa, periodo, rhContableTO.getTipCodigo(), rhContableTO.getConNumero()), false, sisInfoTO);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                rhContableTO = new RhContableTO();
                rhContableTO.setMensaje("M" + e.getMessage());
            } else {
                envioCorreoService.enviarError("SERVER",
                        UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return rhContableTO;
    }

    @RequestMapping("/getRhListaProvisionesTO")
    public List<RhListaProvisionesTO> getRhListaProvisionesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String status = UtilsJSON.jsonToObjeto(String.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getRhListaProvisionesTO(empresa, periodo, sector, status);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhListaProvisionesComprobanteContableTO")
    public List<RhListaProvisionesTO> getRhListaProvisionesComprobanteContableTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getRhListaProvisionesComprobanteContableTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhComboXiiiSueldoPeriodoTO")
    public List<RhXiiiSueldoPeriodoTO> getRhComboXiiiSueldoPeriodoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoPeriodoService.getRhComboXiiiSueldoPeriodoTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunCalcularXiiiSueldo")
    public List<RhFunXiiiSueldoCalcularTO> getRhFunCalcularXiiiSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoService.getRhFunCalcularXiiiSueldo(empresa, sector, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunConsultarXiiiSueldo")
    public List<RhFunXiiiSueldoConsultarTO> getRhFunConsultarXiiiSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoService.getRhFunConsultarXiiiSueldo(empresa, sector, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhComboXivSueldoPeriodoTO")
    public List<RhXivSueldoPeriodoTO> getRhComboXivSueldoPeriodoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoPeriodoService.getRhComboXivSueldoPeriodoTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhComboUtilidadesPeriodoTO")
    public List<RhUtilidadesPeriodoTO> getRhComboUtilidadesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadPeriodoService.getRhComboUtilidadesPeriodoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunCalcularXivSueldo")
    public List<RhFunXivSueldoCalcularTO> getRhFunCalcularXivSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoService.getRhFunCalcularXivSueldo(empresa, sector, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunCalcularUtilidades")
    public List<RhFunUtilidadesCalcularTO> getRhFunCalcularUtilidades(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        Integer totalDias = UtilsJSON.jsonToObjeto(Integer.class, map.get("totalDias"));
        Integer totalCargas = UtilsJSON.jsonToObjeto(Integer.class, map.get("totalCargas"));
        BigDecimal totalPagar = UtilsJSON.jsonToObjeto(BigDecimal.class, map.get("totalPagar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadService.getRhFunCalcularUtilidades(empresa, sector, desde, hasta, totalDias, totalCargas,
                    totalPagar);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunConsultarUtilidades")
    public List<RhFunUtilidadesConsultarTO> getRhFunConsultarUtilidades(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadService.getRhFunConsultarUtilidades(empresa, sector, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunConsultarXivSueldo")
    public List<RhFunXivSueldoConsultarTO> getRhFunConsultarXivSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoService.getRhFunConsultarXivSueldo(empresa, sector, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFormulario107ConsultaTO")
    public RhFormulario107TO getRhFormulario107ConsultaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String empleadoID = UtilsJSON.jsonToObjeto(String.class, map.get("empleadoID"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formulario107Service.getRhFormulario107ConsultaTO(empresa, desde, hasta, empleadoID);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFormulario107TO")
    public RhFormulario107TO getRhFormulario107TO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String anio = UtilsJSON.jsonToObjeto(String.class, map.get("anio"));
        String empleadoID = UtilsJSON.jsonToObjeto(String.class, map.get("empleadoID"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formulario107Service.getRhFormulario107TO(empresa, anio, empleadoID);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarRhFormulario107TO")
    public String insertarRhFormulario107TO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhFormulario107TO rhFormulario107TO = UtilsJSON.jsonToObjeto(RhFormulario107TO.class,
                map.get("rhFormulario107TO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formulario107Service.insertarRhFormulario107TO(rhFormulario107TO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFormulario107PeriodoFiscalComboTO")
    public List<RhFormulario107PeriodoFiscalTO> getRhFormulario107PeriodoFiscalComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formulario107PeriodoService.getRhFormulario107PeriodoFiscalComboTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getValorRecalculadoIR")
    public java.math.BigDecimal getValorRecalculadoIR(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BigDecimal valor = UtilsJSON.jsonToObjeto(BigDecimal.class, map.get("valor"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getValorRecalculadoIR(valor, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunPreavisoXiiis")
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXiiis(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoService.getRhFunPreavisoXiiis(empresa, fecha, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunPreavisoXivs")
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXivs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoService.getRhFunPreavisoXivs(empresa, fecha, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunPreavisoAnticiposBolivariano") // getRhFunPreavisoAnticiposBolivariano
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoAnticiposBolivariano(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String tipoPreAviso = UtilsJSON.jsonToObjeto(String.class, map.get("tipoPreAviso"));
        String servicio = UtilsJSON.jsonToObjeto(String.class, map.get("servicio"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoService.getRhFunPreavisoAnticiposBolivariano(empresa, fecha, cuenta, tipoPreAviso, servicio, null, false);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunPreavisoAnticiposPichincha")
    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> getRhFunPreavisoAnticiposPichincha(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String banco = UtilsJSON.jsonToObjeto(String.class, map.get("banco"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoService.getRhFunPreavisoAnticiposPichincha(empresa, fecha, cuenta, tipo, banco, null);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //listaAnticiposMachala
    @RequestMapping("/getRhFunPreavisoAnticiposMachala")
    public List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> getRhFunPreavisoAnticiposMachala(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String banco = UtilsJSON.jsonToObjeto(String.class, map.get("banco"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoService.getRhFunPreavisoAnticiposMachala(empresa, fecha, cuenta, tipo, banco, null);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunPreavisoPrestamos")
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoPrestamos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prestamoService.getRhFunPreavisoPrestamos(empresa, fecha, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunPreavisoSueldos")
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoSueldos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getRhFunPreavisoSueldos(empresa, fecha, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunFormulario107")
    public List<RhFunFormulario107TO> getRhFunFormulario107(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        Character status = UtilsJSON.jsonToObjeto(Character.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formulario107Service.getRhFunFormulario107(empresa, desde, hasta, status);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhFunFormulario107_2013")
    public List<RhFunFormulario107_2013TO> getRhFunFormulario107_2013(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        Character status = UtilsJSON.jsonToObjeto(Character.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formulario107Service.getRhFunFormulario107(empresa, desde, hasta, status, null);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getSwInactivaEmpleado")
    public boolean getSwInactivaEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, map.get("empleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getSwInactivaEmpleado(empresa, empleado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getRhFunPreavisoVacaciones")
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoVacaciones(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return vacacionesService.getRhFunPreavisoVacaciones(empresa, fecha, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConsolidadoIngresosTabulado")
    public RetornoTO getConsolidadoIngresosTabulado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getConsolidadoIngresosTabulado(empresa, codigoSector, fechaInicio, fechaFin,
                    usuario);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhEmpleadoDescuentosFijosTO")
    public List<RhEmpleadoDescuentosFijosTO> getRhEmpleadoDescuentosFijosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empresaID = UtilsJSON.jsonToObjeto(String.class, map.get("empresaID"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getRhEmpleadoDescuentosFijosTO(empresa, empresaID);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getProvisionPorFechas")
    public RetornoTO getProvisionPorFechas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String agrupacion = UtilsJSON.jsonToObjeto(String.class, map.get("agrupacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empleadoService.getProvisionPorFechas(empresa, codigoSector, fechaInicio, fechaFin, agrupacion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionRhAnticipoMotivo")
    public String accionRhAnticipoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhAnticipoMotivoTO rhAnticipoMotivoTO = UtilsJSON.jsonToObjeto(RhAnticipoMotivoTO.class,
                map.get("rhAnticipoMotivoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoMotivoService.accionRhAnticipoMotivo(rhAnticipoMotivoTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhAnticipoMotivoTablaTO")
    public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoMotivoService.getListaRhAnticipoMotivoTablaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaRhAnticipoMotivoTOTablaTO")
    public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTOTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoMotivoService.getListaRhAnticipoMotivoTOTablaTO(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhAnticipoMotivoTO")
    public RhAnticipoMotivoTO getRhAnticipoMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String detalle = UtilsJSON.jsonToObjeto(String.class, map.get("detalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoMotivoService.getRhAnticipoMotivoTO(empresa, detalle);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhAnticipoMotivo")
    public RhAnticipoMotivo getRhAnticipoMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anticipoMotivoService.getRhAnticipoMotivo(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionRhXiiiSueldoPeriodo")
    public String accionRhXiiiSueldoPeriodo(@RequestBody String json) {

        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXiiiSueldoPeriodoTO rhXiiiSueldoPeriodoTO = UtilsJSON.jsonToObjeto(RhXiiiSueldoPeriodoTO.class,
                map.get("rhXiiiSueldoPeriodoTO"));
        String codigoEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codigoEmpresa"));
        String codigoUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUsuario"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xiiiSueldoPeriodoService.accionRhXiiiSueldoPeriodo(rhXiiiSueldoPeriodoTO, codigoEmpresa,
                    codigoUsuario, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionRhXivSueldoPeriodo")
    public String accionRhXivSueldoPeriodo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXivSueldoPeriodoTO rhXivSueldoPeriodoTO = UtilsJSON.jsonToObjeto(RhXivSueldoPeriodoTO.class,
                map.get("rhXivSueldoPeriodoTO"));
        String codigoEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codigoEmpresa"));
        String codigoUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUsuario"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return xivSueldoPeriodoService.accionRhXivSueldoPeriodo(rhXivSueldoPeriodoTO, codigoEmpresa, codigoUsuario,
                    accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionRhUtilidadesPeriodo")
    public String accionRhUtilidadesPeriodo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhUtilidadesPeriodoTO rhUtilidadesPeriodoTO = UtilsJSON.jsonToObjeto(RhUtilidadesPeriodoTO.class,
                map.get("rhUtilidadesPeriodoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return utilidadPeriodoService.accionRhUtilidadesPeriodo(rhUtilidadesPeriodoTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoAnticiposPrestamos")
    public byte[] generarReporteConsolidadoAnticiposPrestamos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaConsolidadoAnticiposPrestamosTO> listaConsolidadoAnticiposPrestamosTO = UtilsJSON.jsonToList(
                RhListaConsolidadoAnticiposPrestamosTO.class, map.get("listaConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteConsolidadoAnticiposPrestamos(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, listaConsolidadoAnticiposPrestamosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListaDetalleVacaionesPagadas")
    public byte[] generarReporteListaDetalleVacaionesPagadas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String empSector = UtilsJSON.jsonToObjeto(String.class, map.get("empSector"));
        List<RhDetalleVacionesPagadasGozadasTO> listaDetalleVacacionesPagadasGozadasTO = UtilsJSON
                .jsonToList(RhDetalleVacionesPagadasGozadasTO.class, map.get("listaDetalleVacacionesPagadasGozadasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteListaDetalleVacaionesPagadas(usuarioEmpresaReporteTO, desde, hasta,
                    empSector, listaDetalleVacacionesPagadasGozadasTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleAnticipos")
    public byte[] generarReporteDetalleAnticipos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposTO.class,
                map.get("listaDetalleAnticiposTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteDetalleAnticipos(usuarioEmpresaReporteTO, empId, empCodigo,
                    empCategoria, fechaDesde, fechaHasta, listaDetalleAnticiposTO, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleAnticiposPrestamos")
    public byte[] generarReporteDetalleAnticiposPrestamos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        List<RhListaDetalleAnticiposPrestamosTO> listaDetalleAnticiposPrestamosTO = UtilsJSON
                .jsonToList(RhListaDetalleAnticiposPrestamosTO.class, map.get("listaDetalleAnticiposPrestamosTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteDetalleAnticiposPrestamos(usuarioEmpresaReporteTO, empCodigo,
                    fechaDesde, fechaHasta, empCategoria, empId, listaDetalleAnticiposPrestamosTO, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetallePrestamos")
    public byte[] generarReporteDetallePrestamos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        List<RhListaDetallePrestamosTO> listaDetallePrestamosTO = UtilsJSON.jsonToList(RhListaDetallePrestamosTO.class,
                map.get("listaDetallePrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteDetallePrestamos(usuarioEmpresaReporteTO, empCodigo, fechaDesde,
                    fechaHasta, empCategoria, empId, listaDetallePrestamosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleBonos")
    public byte[] generarReporteDetalleBonos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("nombreEmpleado"));
        List<RhListaDetalleBonosTO> listaDetalleBonosTO = UtilsJSON.jsonToList(RhListaDetalleBonosTO.class,
                map.get("listaDetalleBonosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteDetalleBonos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta,
                    empCategoria, nombreEmpleado, listaDetalleBonosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleBonosLote")
    public byte[] generarReporteDetalleBonosLote(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        List<RhListaDetalleBonosLoteTO> listaDetalleBonosLoteTO = UtilsJSON.jsonToList(RhListaDetalleBonosLoteTO.class,
                map.get("listaDetalleBonosLoteTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteDetalleBonosLote(usuarioEmpresaReporteTO, periodo, tipo, numero,
                    listaDetalleBonosLoteTO, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleBonosLoteWeb")
    public byte[] generarReporteDetalleBonosLoteWeb(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteConsultaBonosLote> reporteConsultaBonosLotes = UtilsJSON.jsonToList(ReporteConsultaBonosLote.class,
                map.get("reporteConsultaBonosLotes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteDetalleBonosLote(usuarioEmpresaReporteTO,
                    reporteConsultaBonosLotes);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleBonosLoteColectivo")
    public byte[] generarReporteDetalleBonosLoteColectivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteConsultaBonosLote> reporteConsultaBonosLotes = UtilsJSON.jsonToList(ReporteConsultaBonosLote.class,
                map.get("reporteConsultaBonosLotes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteDetalleBonosLoteColectivo(usuarioEmpresaReporteTO,
                    reporteConsultaBonosLotes);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleViaticos")
    public byte[] generarReporteDetalleViaticos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("nombreEmpleado"));
        List<RhListaDetalleViaticosTO> listaDetalleViaticosTO = UtilsJSON.jsonToList(RhListaDetalleViaticosTO.class,
                map.get("listaDetalleViaticosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteDetalleViaticos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta,
                    empCategoria, nombreEmpleado, listaDetalleViaticosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoBonosViatico")
    public byte[] generarReporteConsolidadoBonosViatico(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaConsolidadoBonosTO> listaConsolidadoBonosViaticosTO = UtilsJSON
                .jsonToList(RhListaConsolidadoBonosTO.class, map.get("listaConsolidadoBonosViaticosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteConsolidadoBonosViatico(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, listaConsolidadoBonosViaticosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoRolesPago")
    public byte[] generarReporteListadoRolesPago(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("nombreEmpleado"));
        List<RhListaDetalleRolesTO> listaDetalleRolesTO = UtilsJSON.jsonToList(RhListaDetalleRolesTO.class,
                map.get("listaDetalleRolesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteListadoRolesPago(usuarioEmpresaReporteTO, fechaDesde, fechaHasta,
                    empCategoria, nombreEmpleado, listaDetalleRolesTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoRoles")
    public byte[] generarReporteConsolidadoRoles(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("nombreEmpleado"));
        boolean excluirLiquidacion = UtilsJSON.jsonToObjeto(boolean.class, map.get("excluirLiquidacion"));
        List<RhListaConsolidadoRolesTO> listaConsolidadoRolesTO = UtilsJSON.jsonToList(RhListaConsolidadoRolesTO.class,
                map.get("listaConsolidadoRolesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteConsolidadoRoles(usuarioEmpresaReporteTO, fechaDesde, fechaHasta,
                    empCategoria, nombreEmpleado, listaConsolidadoRolesTO, excluirLiquidacion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSaldoIndividualAnticiposPrestamos")
    public byte[] generarReporteSaldoIndividualAnticiposPrestamos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        List<RhListaSaldoIndividualAnticiposPrestamosTO> listaSaldoIndividualAnticiposPrestamosTO = UtilsJSON
                .jsonToList(RhListaSaldoIndividualAnticiposPrestamosTO.class,
                        map.get("listaSaldoIndividualAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteSaldoIndividualAnticiposPrestamos(usuarioEmpresaReporteTO,
                    fechaDesde, fechaHasta, empId, listaSaldoIndividualAnticiposPrestamosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoAnticiposPrestamos")
    public byte[] generarReporteSaldoConsolidadoAnticiposPrestamos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listaSaldoConsolidadoAnticiposPrestamosTO = UtilsJSON
                .jsonToList(RhListaSaldoConsolidadoAnticiposPrestamosTO.class,
                        map.get("listaSaldoConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteSaldoConsolidadoAnticiposPrestamos(usuarioEmpresaReporteTO,
                    fechaHasta, listaSaldoConsolidadoAnticiposPrestamosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoBonosViaticos")
    public byte[] generarReporteSaldoConsolidadoBonosViaticos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaSaldoConsolidadoBonosTO> listaSaldoConsolidadoBonosViaticosTO = UtilsJSON
                .jsonToList(RhListaSaldoConsolidadoBonosTO.class, map.get("listaSaldoConsolidadoBonosViaticosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteSaldoConsolidadoBonosViaticos(usuarioEmpresaReporteTO, fechaHasta,
                    listaSaldoConsolidadoBonosViaticosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoSueldosPorPagar")
    public byte[] generarReporteSaldoConsolidadoSueldosPorPagar(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaSaldoConsolidadoSueldosPorPagarTO> listaSaldoConsolidadoSueldosPorPagarTO = UtilsJSON.jsonToList(
                RhListaSaldoConsolidadoSueldosPorPagarTO.class, map.get("listaSaldoConsolidadoSueldosPorPagarTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteSaldoConsolidadoSueldosPorPagar(usuarioEmpresaReporteTO, fechaHasta,
                    listaSaldoConsolidadoSueldosPorPagarTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteXiiiSueldoConsulta")
    public byte[] generarReporteXiiiSueldoConsulta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, map.get("fechaMaxima"));
        List<RhFunXiiiSueldoConsultarTO> rhFunXiiiSueldoConsultarTO = UtilsJSON
                .jsonToList(RhFunXiiiSueldoConsultarTO.class, map.get("rhFunXiiiSueldoConsultarTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteXiiiSueldoConsulta(usuarioEmpresaReporteTO, sector, periodo,
                    fechaDesde, fechaHasta, fechaMaxima, rhFunXiiiSueldoConsultarTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteXivSueldoConsulta")
    public byte[] generarReporteXivSueldoConsulta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, map.get("fechaMaxima"));
        List<RhFunXivSueldoConsultarTO> rhFunXivSueldoConsultarTO = UtilsJSON
                .jsonToList(RhFunXivSueldoConsultarTO.class, map.get("rhFunXivSueldoConsultarTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteXivSueldoConsulta(usuarioEmpresaReporteTO, sector, periodo,
                    fechaDesde, fechaHasta, fechaMaxima, rhFunXivSueldoConsultarTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteUtilidadesPreCalculo")
    public byte[] generarReporteUtilidadesPreCalculo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, map.get("fechaMaxima"));
        List<RhFunUtilidadesCalcularTO> rhFunUtilidadesCalcularTOs = UtilsJSON
                .jsonToList(RhFunUtilidadesCalcularTO.class, map.get("rhFunUtilidadesCalcularTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteUtilidadesPreCalculo(usuarioEmpresaReporteTO, sector, periodo,
                    fechaDesde, fechaHasta, fechaMaxima, rhFunUtilidadesCalcularTOs);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteProvisionesComprobanteContable")
    public byte[] generarReporteProvisionesComprobanteContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        List<RhListaProvisionesTO> listaProvisionesTO = UtilsJSON.jsonToList(RhListaProvisionesTO.class,
                map.get("listaProvisionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteProvisionesComprobanteContable(usuarioEmpresaReporteTO, periodo,
                    tipo, numero, listaProvisionesTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteFormulario107")
    public byte[] generarReporteFormulario107(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaEntrega = UtilsJSON.jsonToObjeto(String.class, map.get("fechaEntrega"));
        String rucEmpleador = UtilsJSON.jsonToObjeto(String.class, map.get("rucEmpleador"));
        String razonSocial = UtilsJSON.jsonToObjeto(String.class, map.get("razonSocial"));
        String rucContador = UtilsJSON.jsonToObjeto(String.class, map.get("rucContador"));
        RhFormulario107TO rhFormulario107TOGuardar = UtilsJSON.jsonToObjeto(RhFormulario107TO.class,
                map.get("rhFormulario107TOGuardar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteFormulario107(usuarioEmpresaReporteTO, fechaEntrega, rucEmpleador,
                    razonSocial, rucContador, rhFormulario107TOGuardar);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteXIIISueldo")
    public byte[] generarReporteXIIISueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldo = UtilsJSON
                .jsonToList(ReporteAnticipoPrestamoXIIISueldo.class, map.get("rhReporteXIIISueldo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteXIIISueldo(usuarioEmpresaReporteTO, rhReporteXIIISueldo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteXIVSueldo")
    public byte[] generarReporteXIVSueldo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIVSueldo = UtilsJSON
                .jsonToList(ReporteAnticipoPrestamoXIIISueldo.class, map.get("rhReporteXIVSueldo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteXIVSueldo(usuarioEmpresaReporteTO, rhReporteXIVSueldo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteUtilidades")
    public byte[] generarReporteUtilidades(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteUtilidades = UtilsJSON
                .jsonToList(ReporteAnticipoPrestamoXIIISueldo.class, map.get("rhReporteUtilidades"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteUtilidades(usuarioEmpresaReporteTO, rhReporteUtilidades);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteAnticipo")
    public byte[] generarReporteAnticipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteAnticipoOprestamos = UtilsJSON
                .jsonToList(ReporteAnticipoPrestamoXIIISueldo.class, map.get("rhReporteAnticipoOprestamos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteAnticipo(usuarioEmpresaReporteTO, rhReporteAnticipoOprestamos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsultaAnticiposLote")
    public byte[] generarReporteConsultaAnticiposLote(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteConsultaAnticiposLote> lista = UtilsJSON.jsonToList(ReporteConsultaAnticiposLote.class,
                map.get("lista"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteConsultaAnticiposLote(usuarioEmpresaReporteTO, lista, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsultaAnticiposLoteColectivo")
    public byte[] generarReporteConsultaAnticiposLoteColectivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteConsultaAnticiposLote> lista = UtilsJSON.jsonToList(ReporteConsultaAnticiposLote.class,
                map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteConsultaAnticiposLoteColectivo(usuarioEmpresaReporteTO, lista);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrestamoVista")
    public byte[] generarReportePrestamoVista(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteAnticipoPrestamoXIIISueldo> lista = UtilsJSON.jsonToList(ReporteAnticipoPrestamoXIIISueldo.class,
                map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReportePrestamoVista(usuarioEmpresaReporteTO, lista);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRol")
    public byte[] generarReporteRol(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReportesRol> lista = UtilsJSON.jsonToList(ReportesRol.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteRol(usuarioEmpresaReporteTO, lista);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteLiquidacion")
    public byte[] generarReporteLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReportesRol> lista = UtilsJSON.jsonToList(ReportesRol.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteLiquidacion(usuarioEmpresaReporteTO, lista);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRolLote")
    public byte[] generarReporteRolLote(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ReportesRol> lista = UtilsJSON.jsonToList(ReportesRol.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteRolLote(usuarioEmpresaReporteTO, lista);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRolLoteSoporte")
    public byte[] generarReporteRolLoteSoporte(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ReportesRol> lista = UtilsJSON.jsonToList(ReportesRol.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteRolLoteSoporte(usuarioEmpresaReporteTO, lista);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteEmpleado")
    public byte[] generarReporteEmpleado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteEmpleado> listReporteEmpleado = UtilsJSON.jsonToList(ReporteEmpleado.class,
                map.get("listReporteEmpleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteEmpleado(usuarioEmpresaReporteTO, listReporteEmpleado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRol2")
    public byte[] generarReporteRol2(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<AuxiliarReporteDetalleRoles> lista = UtilsJSON.jsonToList(AuxiliarReporteDetalleRoles.class,
                map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteRrhhService.generarReporteRol2(usuarioEmpresaReporteTO, lista);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhSoporteContableRolesTO")
    public List<RhListaDetalleRolesTO> getRhSoporteContableRolesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return rolService.getRhSoporteContableRolesTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }
}
