/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.controller;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.report.ReporteContabilidadService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.AnticipoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.report.ReporteRrhhService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.AnticipoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.BonoConceptoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.BonoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.BonoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.PrestamoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RolMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XiiiSueldoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.UtilidadMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.UtilidadPeriodoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XiiiSueldoPeriodoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.CategoriaService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.EmpleadoDescuentoFijoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.EmpleadoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.FormaPagoBeneficioSocialService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.FormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.Formulario107Service;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.HorasExtrasConceptoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.HorasExtrasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.HorasExtrasService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.ParametrosService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.PrestamoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RolService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.UtilidadService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.VacacionesService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XiiiSueldoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XivSueldoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XivSueldoPeriodoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.XivSueldoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ItemStatusItemListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.ItemListaRolTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhDetalleVacionesPagadasGozadasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107_2013TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunListadoEmpleadosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoConsultarTO;
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
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaRolSaldoEmpleadoDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoSueldosPorPagarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoIndividualAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhOrdenBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionesListadoTransTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoXiiiSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoXivSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhImpuestoRenta;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteAnticipoPrestamoXIIISueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReportesRol;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.ImpuestoRentaService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.ProvisionesService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RolPagoNotificacionesService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.SalarioDignoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.VacacionesGozadasService;
import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhContableTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolEmpTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSaldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhSalarioDignoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesGozadasDatosAdjuntosWebTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtras;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasConcepto;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhProvisiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolPagoNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadas;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.olap4j.impl.ArrayMap;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/todocompuWS/rrhhWebController")
public class RRHHWebController {

    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private XivSueldoMotivoService xivSueldoMotivoService;
    @Autowired
    private UtilidadMotivoService utilidadMotivoService;
    @Autowired
    private UtilidadPeriodoService utilidadPeriodoService;
    @Autowired
    private XivSueldoPeriodoService xivSueldoPeriodoService;
    @Autowired
    private XiiiSueldoPeriodoService xiiiSueldoPeriodoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private FormaPagoService formaPagoService;
    @Autowired
    private FormaPagoBeneficioSocialService formaPagoBeneficioSocialService;
    @Autowired
    private ReporteRrhhService reporteRrhhService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private AnticipoMotivoService anticipoMotivoService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private HorasExtrasMotivoService horasExtrasMotivoService;
    @Autowired
    private BonoMotivoService bonoMotivoService;
    @Autowired
    private BonoConceptoService bonoConceptoService;
    @Autowired
    private HorasExtrasConceptoService horasExtrasConceptoService;
    @Autowired
    private PrestamoMotivoService prestamoMotivoService;
    @Autowired
    private RolMotivoService rolMotivoService;
    @Autowired
    private XiiiSueldoMotivoService xiiiSueldoMotivoService;
    @Autowired
    private XiiiSueldoService xiiiSueldoService;
    @Autowired
    private RolService rolService;
    @Autowired
    private PrestamoService prestamoService;
    @Autowired
    private VacacionesService vacacionesService;
    @Autowired
    private XivSueldoService xivSueldoService;
    @Autowired
    private UtilidadService utilidadService;
    @Autowired
    private Formulario107Service formulario107Service;
    @Autowired
    private BonoService bonoService;
    @Autowired
    private HorasExtrasService horasExtrasService;
    @Autowired
    private AnticipoService anticipoService;
    @Autowired
    private ReporteContabilidadService reporteContabilidadService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private ParametrosService parametrosService;
    @Autowired
    private EmpleadoDescuentoFijoService empleadoDescuentoFijoService;
    @Autowired
    private ImpuestoRentaService impuestoRentaService;
    @Autowired
    private ProvisionesService provisionesService;
    @Autowired
    private SalarioDignoService salarioDignoService;
    @Autowired
    private VacacionesGozadasService vacacionesGozadasService;
    @Autowired
    private RolPagoNotificacionesService rolPagoNotificacionesService;

    /*UTILIDADES MOTIVO*/
    @RequestMapping("/getListaRhUtilidadMotivo")
    public RespuestaWebTO getListaRhUtilidadMotivo(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean soloActivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("soloActivos"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<RhUtilidadMotivo> respues = soloActivos ? utilidadMotivoService.getListaRhUtilidadMotivoSoloActivos(empresa) : utilidadMotivoService.getListaRhUtilidadMotivo(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhUtilidadMotivo")
    public RespuestaWebTO insertarRhUtilidadMotivo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhUtilidadMotivo rhUtilidadMotivo = UtilsJSON.jsonToObjeto(RhUtilidadMotivo.class, map.get("rhUtilidadMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = utilidadMotivoService.insertarRrhhUtilidadMotivo(rhUtilidadMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de utilidad: Código " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + ", se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhUtilidadMotivo")
    public RespuestaWebTO modificarRhUtilidadMotivo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhUtilidadMotivo rhUtilidadMotivo = UtilsJSON.jsonToObjeto(RhUtilidadMotivo.class, map.get("rhUtilidadMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = utilidadMotivoService.modificarRhUtilidadMotivo(rhUtilidadMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de utilidad: Código " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + ", se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoRhUtilidadMotivo")
    public RespuestaWebTO modificarEstadoRhUtilidadMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RhUtilidadMotivo rhUtilidadMotivo = UtilsJSON.jsonToObjeto(RhUtilidadMotivo.class, map.get("rhUtilidadMotivo"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = utilidadMotivoService.modificarEstadoRhUtilidadMotivo(rhUtilidadMotivo, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhUtilidadMotivo")
    public RespuestaWebTO eliminarRhUtilidadMotivo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhUtilidadMotivo rhUtilidadMotivo = UtilsJSON.jsonToObjeto(RhUtilidadMotivo.class, map.get("rhUtilidadMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = utilidadMotivoService.eliminarRhUtilidadMotivo(rhUtilidadMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhUtilidadMotivo")
    public @ResponseBody
    String generarReporteRhUtilidadMotivo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRhUtilidadMotivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhUtilidadMotivo> listado = UtilsJSON.jsonToList(RhUtilidadMotivo.class, parametros.get("ListadoRhUtilidadMotivo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhUtilidadMotivo(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteRhUtilidadMotivo")
    public @ResponseBody
    String exportarReporteRhUtilidadMotivo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhUtilidadMotivo> listado = UtilsJSON.jsonToList(RhUtilidadMotivo.class, map.get("ListadoRhUtilidadMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteRhUtilidadMotivo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*HORAS EXTRAS MOTIVO*/
    @RequestMapping("/getListaRhHorasExtrasMotivo")
    public RespuestaWebTO getListaRhHorasExtrasMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhHorasExtrasMotivo> respues = horasExtrasMotivoService.getListaRhHorasExtrasMotivos(empresa, inactivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhHorasExtrasMotivo")
    public RespuestaWebTO insertarRhHorasExtrasMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhHorasExtrasMotivo rhHorasExtrasMotivo = UtilsJSON.jsonToObjeto(RhHorasExtrasMotivo.class, map.get("rhHorasExtrasMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = horasExtrasMotivoService.insertarRhhHorasExtrasMotivo(rhHorasExtrasMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El bono motivo: " + rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle() + ", se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhHorasExtrasMotivo")
    public RespuestaWebTO modificarRhHorasExtrasMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhHorasExtrasMotivo bonoMotivo = UtilsJSON.jsonToObjeto(RhHorasExtrasMotivo.class, map.get("rhHorasExtrasMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = horasExtrasMotivoService.modificarRhHorasExtrasMotivo(bonoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de horas extras: " + bonoMotivo.getRhHorasExtrasMotivoPK().getMotDetalle() + ", se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoRhHorasExtrasMotivo")
    public RespuestaWebTO modificarEstadoRhHorasExtrasMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhHorasExtrasMotivo bonoMotivo = UtilsJSON.jsonToObjeto(RhHorasExtrasMotivo.class, map.get("rhHorasExtrasMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = horasExtrasMotivoService.modificarEstadoRhHorasExtrasMotivo(bonoMotivo, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de horas extras: " + bonoMotivo.getRhHorasExtrasMotivoPK().getMotDetalle() + ", se ha " + (estado ? "inactivado" : "activado") + " correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhHorasExtrasMotivo")
    public RespuestaWebTO eliminarRhHorasExtrasMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhHorasExtrasMotivo rhHorasExtrasMotivo = UtilsJSON.jsonToObjeto(RhHorasExtrasMotivo.class, map.get("rhHorasExtrasMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = horasExtrasMotivoService.eliminarRhHorasExtrasMotivo(rhHorasExtrasMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de horas extras: " + rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoHorasExtras")
    public @ResponseBody
    String generarReporteMotivoHorasExtras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoPrestamo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhHorasExtrasMotivo> listado = UtilsJSON.jsonToList(RhHorasExtrasMotivo.class, parametros.get("ListadoMotivoHorasExtras"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoHorasExtras(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoHorasExtras")
    public @ResponseBody
    String exportarReporteMotivoHorasExtras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhHorasExtrasMotivo> listado = UtilsJSON.jsonToList(RhHorasExtrasMotivo.class, map.get("ListadoMotivoHorasExtras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteMotivoHorasExtrass(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhMotivoHorasExtrasMatricial")
    public RespuestaWebTO generarReporteRhMotivoHorasExtrasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhHorasExtrasMotivo> listado = UtilsJSON.jsonToList(RhHorasExtrasMotivo.class, parametros.get("ListadoMotivoHorasExtras"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoHorasExtras(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*BONO MOTIVO*/
    @RequestMapping("/getListaRhBonoMotivo")
    public RespuestaWebTO getListaRhBonoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhBonoMotivo> respues = bonoMotivoService.getListaRhBonoMotivos(empresa, inactivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhBonoMotivo")
    public RespuestaWebTO insertarRhBonoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoMotivo rhBonoMotivo = UtilsJSON.jsonToObjeto(RhBonoMotivo.class, map.get("rhBonoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = bonoMotivoService.insertarRhhBonoMotivo(rhBonoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El bono motivo: " + rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle() + ", se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhBonoMotivo")
    public RespuestaWebTO modificarRhBonoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoMotivo bonoMotivo = UtilsJSON.jsonToObjeto(RhBonoMotivo.class, map.get("rhBonoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = bonoMotivoService.modificarRhBonoMotivo(bonoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El bono motivo: " + bonoMotivo.getRhBonoMotivoPK().getMotDetalle() + ", se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoRhBonoMotivo")
    public RespuestaWebTO modificarEstadoRhBonoMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoMotivo bonoMotivo = UtilsJSON.jsonToObjeto(RhBonoMotivo.class, map.get("rhBonoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = bonoMotivoService.modificarEstadoRhBonoMotivo(bonoMotivo, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El bono motivo: " + bonoMotivo.getRhBonoMotivoPK().getMotDetalle() + ", se ha " + (estado ? "inactivado" : "activado") + " correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhBonoMotivo")
    public RespuestaWebTO eliminarRhBonoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoMotivo rhBonoMotivo = UtilsJSON.jsonToObjeto(RhBonoMotivo.class, map.get("rhBonoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = bonoMotivoService.eliminarRhBonoMotivo(rhBonoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El bono motivo: " + rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoBonos")
    public @ResponseBody
    String generarReporteMotivoBonos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoPrestamo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhBonoMotivo> listado = UtilsJSON.jsonToList(RhBonoMotivo.class, parametros.get("ListadoMotivoBonos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoBonos(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoBonos")
    public @ResponseBody
    String exportarReporteMotivoBonos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhBonoMotivo> listado = UtilsJSON.jsonToList(RhBonoMotivo.class, map.get("ListadoMotivoBonos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteMotivoBonos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Concepto de horas extras*/
    @RequestMapping("/getListaRhHorasExtrasConceptoTO")
    public RespuestaWebTO getListaRhHorasExtrasConceptoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhHorasExtrasConcepto> respues = horasExtrasConceptoService.getListaHorasExtrasConceptos(empresa, inactivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhHorasExtrasConcepto")
    public RespuestaWebTO insertarRhHorasExtrasConcepto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhHorasExtrasConcepto horasExtrasConcepto = UtilsJSON.jsonToObjeto(RhHorasExtrasConcepto.class, map.get("rhHorasExtrasConcepto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = horasExtrasConceptoService.insertarRhHorasExtrasConcepto(horasExtrasConcepto, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El concepto de horas extras: " + horasExtrasConcepto.getHecDetalle() + ", se ha ingressado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("Error al ingresar concepto de horas extras");
                resp.setExtraInfo(true);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhHorasExtrasConcepto")
    public RespuestaWebTO modificarRhHorasExtrasConcepto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhHorasExtrasConcepto horasExtrasConcepto = UtilsJSON.jsonToObjeto(RhHorasExtrasConcepto.class, map.get("rhHorasExtrasConcepto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = horasExtrasConceptoService.modificarRhHorasExtrasConcepto(horasExtrasConcepto, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El concepto horas extras: " + horasExtrasConcepto.getHecDetalle() + ", se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("Error al ingresar concepto de horas extras");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoRhHorasExtrasConcepto")
    public RespuestaWebTO modificarEstadoRhHorasExtrasConcepto(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhHorasExtrasConcepto horasExtrasConcepto = UtilsJSON.jsonToObjeto(RhHorasExtrasConcepto.class, map.get("rhHorasExtrasConcepto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            boolean respues = horasExtrasConceptoService.modificarEstadoRhHorasExtrasConcepto(horasExtrasConcepto, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El concepto horas extras: " + horasExtrasConcepto.getHecDetalle() + ", se ha " + (estado ? "inactivado" : "activado") + " correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("Error al ingresar concepto de horas extras");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhHorasExtrasConcepto")
    public RespuestaWebTO eliminarRhHorasExtrasConcepto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhHorasExtrasConcepto horasExtrasConcepto = UtilsJSON.jsonToObjeto(RhHorasExtrasConcepto.class, map.get("rhHorasExtrasConcepto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = horasExtrasConceptoService.eliminarRhHorasExtrasConcepto(horasExtrasConcepto, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El concepto horas extras: " + horasExtrasConcepto.getHecDetalle() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("Error al ingresar concepto de horas extras");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteConceptoHorasExtras")
    public @ResponseBody
    String generarReporteConceptoHorasExtras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConceptoHorasExtras.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhHorasExtrasConcepto> listado = UtilsJSON.jsonToList(RhHorasExtrasConcepto.class, parametros.get("ListadoConceptoHorasExtras"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteConceptoHorasExtras(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConceptoHorasExtras")
    public @ResponseBody
    String exportarReporteConceptoHorasExtras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhHorasExtrasConcepto> listado = UtilsJSON.jsonToList(RhHorasExtrasConcepto.class, map.get("ListadoConceptoHorasExtras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteConceptoHorasExtras(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConceptoHorasExtrasMatricial")
    public RespuestaWebTO generarReporteConceptoHorasExtrasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhHorasExtrasConcepto> listado = UtilsJSON.jsonToList(RhHorasExtrasConcepto.class, parametros.get("ListadoConceptoHorasExtras"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteConceptoHorasExtras(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Concepto de bonos*/
    @RequestMapping("/getListaRhBonoConceptoTO")
    public RespuestaWebTO getListaRhBonoConceptoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaBonoConceptoTO> respues = bonoConceptoService.getListaBonoConceptosTO(empresa, inactivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhBonoConcepto")
    public RespuestaWebTO insertarRhBonoConcepto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoConceptoTO rhBonoConceptoTO = UtilsJSON.jsonToObjeto(RhBonoConceptoTO.class, map.get("rhBonoConceptoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = bonoConceptoService.insertarRhBonoConcepto(rhBonoConceptoTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El concepto bono: " + rhBonoConceptoTO.getBcDetalle() + ", se ha ingressado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("Error al ingresar concepto de bono");
                resp.setExtraInfo(true);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhBonoConcepto")
    public RespuestaWebTO modificarRhBonoConcepto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoConceptoTO rhBonoConceptoTO = UtilsJSON.jsonToObjeto(RhBonoConceptoTO.class, map.get("rhBonoConceptoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = bonoConceptoService.modificarRhBonoConcepto(rhBonoConceptoTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El concepto bono: " + rhBonoConceptoTO.getBcDetalle() + ", se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("Error al ingresar concepto de bono");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoRhBonoConcepto")
    public RespuestaWebTO modificarEstadoRhBonoConcepto(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoConceptoTO rhBonoConceptoTO = UtilsJSON.jsonToObjeto(RhBonoConceptoTO.class, map.get("rhBonoConceptoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            boolean respues = bonoConceptoService.modificarEstadoRhBonoConcepto(rhBonoConceptoTO, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El concepto bono: " + rhBonoConceptoTO.getBcDetalle() + ", se ha " + (estado ? "inactivado" : "activado") + " correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("Error al ingresar concepto de bono");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhBonoConcepto")
    public RespuestaWebTO eliminarRhBonoConcepto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhBonoConceptoTO rhBonoConceptoTO = UtilsJSON.jsonToObjeto(RhBonoConceptoTO.class, map.get("rhBonoConceptoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = bonoConceptoService.eliminarRhBonoConcepto(rhBonoConceptoTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El concepto bono: " + rhBonoConceptoTO.getBcDetalle() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("Error al ingresar concepto de bono");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteConceptoBonos")
    public @ResponseBody
    String generarReporteConceptoBonos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConceptoBonos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaBonoConceptoTO> listado = UtilsJSON.jsonToList(RhListaBonoConceptoTO.class, parametros.get("ListadoConceptoBonos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteConceptoBonos(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConceptoBonos")
    public @ResponseBody
    String exportarReporteConceptoBonos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhListaBonoConceptoTO> listado = UtilsJSON.jsonToList(RhListaBonoConceptoTO.class, map.get("ListadoConceptoBonos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteConceptoBonos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Motivo de prestamo*/
    @RequestMapping("/getListaRhPrestamoMotivo")
    public RespuestaWebTO getListaRhPrestamoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhPrestamoMotivo> respues = prestamoMotivoService.getListaRhPrestamoMotivos(empresa, inactivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhPrestamoMotivo")
    public RespuestaWebTO insertarRhPrestamoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhPrestamoMotivo rhPrestamoMotivo = UtilsJSON.jsonToObjeto(RhPrestamoMotivo.class, map.get("rhPrestamoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respues = prestamoMotivoService.insertarRhPrestamoMotivo(rhPrestamoMotivo, sisInfoTO);
            if (respues.getMensaje().substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhPrestamoMotivo")
    public RespuestaWebTO modificarRhPrestamoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhPrestamoMotivo rhPrestamoMotivo = UtilsJSON.jsonToObjeto(RhPrestamoMotivo.class, map.get("rhPrestamoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = prestamoMotivoService.modificarRhPrestamoMotivo(rhPrestamoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoRhPrestamoMotivo")
    public RespuestaWebTO modificarEstadoRhPrestamoMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhPrestamoMotivo rhPrestamoMotivo = UtilsJSON.jsonToObjeto(RhPrestamoMotivo.class, map.get("rhPrestamoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = prestamoMotivoService.modificarEstadoRhPrestamoMotivo(rhPrestamoMotivo, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhPrestamoMotivo")
    public RespuestaWebTO eliminarRhPrestamoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhPrestamoMotivo rhPrestamoMotivo = UtilsJSON.jsonToObjeto(RhPrestamoMotivo.class, map.get("rhPrestamoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = prestamoMotivoService.eliminarRhPrestamoMotivo(rhPrestamoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoPrestamo")
    public @ResponseBody
    String generarReporteMotivoPrestamo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoPrestamo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhPrestamoMotivo> listado = UtilsJSON.jsonToList(RhPrestamoMotivo.class, parametros.get("ListadoMotivoPrestamo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoPrestamo(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoPrestamo")
    public @ResponseBody
    String exportarReporteMotivoPrestamo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhPrestamoMotivo> listado = UtilsJSON.jsonToList(RhPrestamoMotivo.class, map.get("ListadoMotivoPrestamo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteMotivoPrestamo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*ROL MOTIVO*/
    @RequestMapping("/getListaRhRolMotivo")
    public RespuestaWebTO getListaRhRolMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhRolMotivo> respues = rolMotivoService.getListaRhRolMotivos(empresa, inactivo);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhRolMotivo")
    public RespuestaWebTO insertarRhRolMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRolMotivo rhRolMotivo = UtilsJSON.jsonToObjeto(RhRolMotivo.class, map.get("rhRolMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = rolMotivoService.insertarRhRolMotivo(rhRolMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhRolMotivo")
    public RespuestaWebTO modificarRhRolMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRolMotivo rhRolMotivo = UtilsJSON.jsonToObjeto(RhRolMotivo.class, map.get("rhRolMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = rolMotivoService.modificarRhRolMotivo(rhRolMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoRhRolMotivo")
    public RespuestaWebTO modificarEstadoRhRolMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRolMotivo rhRolMotivo = UtilsJSON.jsonToObjeto(RhRolMotivo.class, map.get("rhRolMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = rolMotivoService.modificarEstadoRhRolMotivo(rhRolMotivo, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhRolMotivo")
    public RespuestaWebTO eliminarRhRolMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRolMotivo rhRolMotivo = UtilsJSON.jsonToObjeto(RhRolMotivo.class, map.get("rhRolMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = rolMotivoService.eliminarRhRolMotivo(rhRolMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoRolPago")
    public @ResponseBody
    String generarReporteMotivoRolPago(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoRolPago.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhRolMotivo> listado = UtilsJSON.jsonToList(RhRolMotivo.class, parametros.get("ListadoRolPago"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoRolPago(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoRolPago")
    public @ResponseBody
    String exportarReporteMotivoRolPago(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhRolMotivo> listado = UtilsJSON.jsonToList(RhRolMotivo.class, map.get("ListadoRolPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteMotivoRolPago(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*MOTIVO DECIMO TERCER SUELDO*/
    @RequestMapping("/getListaRhXiiiSueldoMotivo")
    public RespuestaWebTO getListaRhXiiiSueldoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhXiiiSueldoMotivo> respues = xiiiSueldoMotivoService.getListaRhXiiiSueldoMotivos(empresa, inactivo);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhXiiiSueldoMotivo")
    public RespuestaWebTO insertarRhXiiiSueldoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXiiiSueldoMotivo rhXiiiSueldoMotivo = UtilsJSON.jsonToObjeto(RhXiiiSueldoMotivo.class,
                map.get("rhXiiiSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO msje = xiiiSueldoMotivoService.insertarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisInfoTO);
            if (msje.getMensaje().substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.getMensaje().substring(1, msje.getMensaje().length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.getMensaje().substring(1, msje.getMensaje().length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhXiiiSueldoMotivo")
    public RespuestaWebTO modificarRhXiiiSueldoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXiiiSueldoMotivo rhXiiiSueldoMotivo = UtilsJSON.jsonToObjeto(RhXiiiSueldoMotivo.class,
                map.get("rhXiiiSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = xiiiSueldoMotivoService.modificarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoRhXiiiSueldoMotivo")
    public RespuestaWebTO modificarEstadoRhXiiiSueldoMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXiiiSueldoMotivo rhXiiiSueldoMotivo = UtilsJSON.jsonToObjeto(RhXiiiSueldoMotivo.class,
                map.get("rhXiiiSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = xiiiSueldoMotivoService.modificarEstadoRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhXiiiSueldoMotivo")
    public RespuestaWebTO eliminarRhXiiiSueldoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXiiiSueldoMotivo rhXiiiSueldoMotivo = UtilsJSON.jsonToObjeto(RhXiiiSueldoMotivo.class,
                map.get("rhXiiiSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = xiiiSueldoMotivoService.eliminarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoXiiiSueldo")
    public @ResponseBody
    String generarReporteMotivoXiiiSueldo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoXiiiSueldo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhXiiiSueldoMotivo> listado = UtilsJSON.jsonToList(RhXiiiSueldoMotivo.class, parametros.get("ListadoXiiiSueldo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoXiiiSueldo(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoXiiiSueldo")
    public @ResponseBody
    String exportarReporteMotivoXiiiSueldo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhXiiiSueldoMotivo> listado = UtilsJSON.jsonToList(RhXiiiSueldoMotivo.class, map.get("ListadoXiiiSueldo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteMotivoXiiiSueldo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*XIV SUELDO MOTIVO*/
    @RequestMapping("/getListaRhXivSueldoMotivo")
    public RespuestaWebTO getListaRhXivSueldoMotivo(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean soloActivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("soloActivos"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<RhXivSueldoMotivo> respues = soloActivos ? xivSueldoMotivoService.getListaRhXivSueldoMotivoSoloActivos(empresa) : xivSueldoMotivoService.getListaRhXivSueldoMotivo(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhXivSueldoMotivo")
    public RespuestaWebTO insertarRhXivSueldoMotivo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXivSueldoMotivo rhXivSueldoMotivo = UtilsJSON.jsonToObjeto(RhXivSueldoMotivo.class, map.get("rhXivSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = xivSueldoMotivoService.insertarRhXivSueldMotivo(rhXivSueldoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhXivSueldoMotivo")
    public RespuestaWebTO modificarRhXivSueldoMotivo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXivSueldoMotivo rhXivSueldoMotivo = UtilsJSON.jsonToObjeto(RhXivSueldoMotivo.class, map.get("rhXivSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = xivSueldoMotivoService.modificarRhXivSueldoMotivo(rhXivSueldoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoRhXivSueldoMotivo")
    public RespuestaWebTO modificarEstadoRhXivSueldoMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RhXivSueldoMotivo rhXivSueldoMotivo = UtilsJSON.jsonToObjeto(RhXivSueldoMotivo.class, map.get("rhXivSueldoMotivo"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = xivSueldoMotivoService.modificarEstadoRhXivSueldoMotivo(rhXivSueldoMotivo, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhXivSueldoMotivo")
    public RespuestaWebTO eliminarRhXivSueldoMotivo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXivSueldoMotivo rhXivSueldoMotivo = UtilsJSON.jsonToObjeto(RhXivSueldoMotivo.class, map.get("rhXivSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = xivSueldoMotivoService.eliminarRhXivSueldoMotivo(rhXivSueldoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhXivSueldoMotivo")
    public @ResponseBody
    String generarReporteRhXivSueldoMotivo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRhXivSueldoMotivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhXivSueldoMotivo> listado = UtilsJSON.jsonToList(RhXivSueldoMotivo.class, parametros.get("ListadoRhXivSueldoMotivo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhXivSueldoMotivo(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteRhXivSueldoMotivo")
    public @ResponseBody
    String exportarReporteRhXivSueldoMotivo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhXivSueldoMotivo> listado = UtilsJSON.jsonToList(RhXivSueldoMotivo.class, map.get("ListadoRhXivSueldoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteRhXivSueldoMotivo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*PERIODO UTILIDAD*/
    @RequestMapping("/getRhComboUtilidadesPeriodoTO")
    public RespuestaWebTO getRhComboUtilidadesTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<RhUtilidadesPeriodoTO> respues = utilidadPeriodoService.getRhComboUtilidadesPeriodoTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionRhUtilidadesPeriodo")
    public RespuestaWebTO accionRhUtilidadesPeriodo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhUtilidadesPeriodoTO rhUtilidadesPeriodoTO = UtilsJSON.jsonToObjeto(RhUtilidadesPeriodoTO.class, map.get("rhUtilidadesPeriodoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = utilidadPeriodoService.accionRhUtilidadesPeriodo(rhUtilidadesPeriodoTO, accion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePeriodosUtilidad")
    public @ResponseBody
    String generarReportePeriodosUtilidad(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPeriodosUtilidad.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhUtilidadesPeriodoTO> listado = UtilsJSON.jsonToList(RhUtilidadesPeriodoTO.class, parametros.get("ListadoPeriodosUtilidad"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReportePeriodosUtilidad(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePeriodosUtilidad")
    public @ResponseBody
    String exportarReportePeriodosUtilidad(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhUtilidadesPeriodoTO> listado = UtilsJSON.jsonToList(RhUtilidadesPeriodoTO.class, map.get("ListadoPeriodosUtilidad"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReportePeriodosUtilidad(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*PERIODO XIII SUELDO*/
    @RequestMapping("/getRhComboXiiiSueldoPeriodoTO")
    public RespuestaWebTO getRhComboXiiiSueldoPeriodoTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhXiiiSueldoPeriodoTO> respues = xiiiSueldoPeriodoService.getRhComboXiiiSueldoPeriodoTO();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionRhXiiiSueldoPeriodo")
    public RespuestaWebTO accionRhXiiiSueldoPeriodo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXiiiSueldoPeriodoTO rhXiiiSueldoPeriodoTO = UtilsJSON.jsonToObjeto(RhXiiiSueldoPeriodoTO.class, map.get("rhXiiiSueldoPeriodoTO"));
        String codigoEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codigoEmpresa"));
        String codigoUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUsuario"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = xiiiSueldoPeriodoService.accionRhXiiiSueldoPeriodo(rhXiiiSueldoPeriodoTO, codigoEmpresa, codigoUsuario, accion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                Integer secuencial = 0;
                if ('I' == accion) {
                    secuencial = xiiiSueldoPeriodoService.getRhXiiiSueldoPeriodoPorAtributo("xiiiDescripcion", rhXiiiSueldoPeriodoTO.getXiiiDescripcion()).getXiiiSecuencial();
                }
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo('I' == accion ? secuencial : true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteXiiiSueldoPeriodo")
    public @ResponseBody
    String generarReporteXiiiSueldoPeriodo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPeriodosXiiiSueldo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhXiiiSueldoPeriodoTO> listado = UtilsJSON.jsonToList(RhXiiiSueldoPeriodoTO.class, parametros.get("ListadoXiiiSueldoPeriodo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXiiiSueldoPeriodo(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteXiiiSueldoPeriodo")
    public @ResponseBody
    String exportarReporteXiiiSueldoPeriodo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhXiiiSueldoPeriodoTO> listado = UtilsJSON.jsonToList(RhXiiiSueldoPeriodoTO.class, map.get("ListadoXiiiSueldoPeriodo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteXiiiSueldoPeriodo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*PERIODO XIV SUELDO*/
    @RequestMapping("/getRhComboXivSueldoPeriodoTO")
    public RespuestaWebTO getRhComboXivSueldoPeriodoTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhXivSueldoPeriodoTO> respues = xivSueldoPeriodoService.getRhComboXivSueldoPeriodoTO();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionRhXivSueldoPeriodo")
    public RespuestaWebTO accionRhXivSueldoPeriodo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhXivSueldoPeriodoTO rhXivSueldoPeriodoTO = UtilsJSON.jsonToObjeto(RhXivSueldoPeriodoTO.class, map.get("rhXivSueldoPeriodoTO"));
        String codigoEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codigoEmpresa"));
        String codigoUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUsuario"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = xivSueldoPeriodoService.accionRhXivSueldoPeriodo(rhXivSueldoPeriodoTO, codigoEmpresa, codigoUsuario, accion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                Integer secuencial = 0;
                if ('I' == accion) {
                    secuencial = xivSueldoPeriodoService.getRhXivSueldoPeriodoPorAtributo("xivDescripcion", rhXivSueldoPeriodoTO.getXivDescripcion()).getXivSecuencial();
                }
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo('I' == accion ? secuencial : true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteXivSueldoPeriodo")
    public @ResponseBody
    String generarReporteXivSueldoPeriodo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPeriodosXivSueldo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhXivSueldoPeriodoTO> listado = UtilsJSON.jsonToList(RhXivSueldoPeriodoTO.class, parametros.get("ListadoPeriodoXivSueldo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXivSueldoPeriodo(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteXivSueldoPeriodo")
    public @ResponseBody
    String exportarReporteXivSueldoPeriodo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhXivSueldoPeriodoTO> listado = UtilsJSON.jsonToList(RhXivSueldoPeriodoTO.class, map.get("ListadoPeriodoXivSueldo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteXivSueldoPeriodo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Categorias*/
    @RequestMapping("/getComboRhCategoriaTO")
    public RespuestaWebTO getComboRhCategoriaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhComboCategoriaTO> respues = categoriaService.getComboRhCategoriaTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron categorías.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaRhCategoriaCuentasTO")
    public RespuestaWebTO getListaRhCategoriaCuentasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhCategoriaTO> respues = categoriaService.getListaRhCategoriaCuentasTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron categorías.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionRhCategoria")
    public RespuestaWebTO accionRhCategoria(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhCategoriaTO rhCategoriaTO = UtilsJSON.jsonToObjeto(RhCategoriaTO.class, map.get("rhCategoriaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = categoriaService.accionRhCategoria(rhCategoriaTO, accion, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "No se ha realizado acción.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarRhCategoriaTO")
    public @ResponseBody
    String exportarRhCategoriaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhCategoriaTO> listado = UtilsJSON.jsonToList(RhCategoriaTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarRhCategoriaTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhCategoriaTO")
    public @ResponseBody
    String generarReporteRhCategoriaTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRhCategoria.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhCategoriaTO> listado = UtilsJSON.jsonToList(RhCategoriaTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhCategoriaTO(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Forma pago*/
    @RequestMapping("/accionRhFormaPago")
    public RespuestaWebTO accionRhFormaPago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhFormaPagoTO rhFormaPagoTO = UtilsJSON.jsonToObjeto(RhFormaPagoTO.class, map.get("rhFormaPagoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        String accionRpta = UtilsJSON.jsonToObjeto(String.class, map.get("accionRpta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = formaPagoService.accionRhFormaPago(rhFormaPagoTO, accion, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                String mensaje = respues.substring(1);
                if ("I".equals(accionRpta)) {
                    mensaje = "La Forma de Pago " + rhFormaPagoTO.getCtaCodigo() + " - "
                            + rhFormaPagoTO.getFpDetalle() + " se ha inactivado correctamente.";
                }
                if ("A".equals(accionRpta)) {
                    mensaje = "La Forma de Pago " + rhFormaPagoTO.getCtaCodigo() + " - "
                            + rhFormaPagoTO.getFpDetalle() + " se ha activado correctamente.";
                }
                resp.setExtraInfo(mensaje);
                resp.setOperacionMensaje(mensaje);
            } else {
                String mensaje = " modificado ";
                if ("I".equals(accionRpta)) {
                    mensaje = " inactivado ";
                }
                if ("A".equals(accionRpta)) {
                    mensaje = " activado ";
                }
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "No se ha" + mensaje + "forma de pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhFormaPago")
    public RespuestaWebTO insertarRhFormaPago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhFormaPagoTO rhFormaPagoTO = UtilsJSON.jsonToObjeto(RhFormaPagoTO.class, map.get("rhFormaPagoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RhFormaPagoTO respues = formaPagoService.insertarRhFormaPago(rhFormaPagoTO, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La forma de pago " + respues.getFpDetalle() + " se ha guardado correctamente.");
            } else {
                resp.setOperacionMensaje("No se ha guardado la forma de pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaRhFormaPagoTO")
    public RespuestaWebTO getListaRhFormaPagoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaFormaPagoTO> respues = formaPagoService.getListaFormaPagoTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (!estado) {
                    List<RhListaFormaPagoTO> listaResultante = new ArrayList<>();
                    for (RhListaFormaPagoTO o : respues) {
                        if (!o.getFpInactivo()) {
                            listaResultante.add(o);
                        }
                    }
                    if (!listaResultante.isEmpty()) {
                        resp.setExtraInfo(listaResultante);
                    } else {
                        resp.setOperacionMensaje("No se encontraron motivos de anticipos.");
                    }
                } else {
                    resp.setExtraInfo(respues);
                }
            } else {
                resp.setOperacionMensaje("No se encontraron formas de pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarRhFormaPagoTO")
    public @ResponseBody
    String exportarRhFormaPagoTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhListaFormaPagoTO> listado = UtilsJSON.jsonToList(RhListaFormaPagoTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarRhListaFormaPagoTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhFormaPagoTO")
    public @ResponseBody
    String generarReporteRhFormaPagoTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportFormaPago.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaFormaPagoTO> listado = UtilsJSON.jsonToList(RhListaFormaPagoTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhFormaPagoTO(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*FP Beneficios*/
    @RequestMapping("/getListaFormaPagoBeneficioSocialTO")
    public RespuestaWebTO getListaRhFormaPagoBeneficioSocialTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaFormaPagoBeneficioSocialTO> respues = formaPagoBeneficioSocialService.getListaFormaPagoBeneficioSocialTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (!estado) {
                    List<RhListaFormaPagoBeneficioSocialTO> listaResultante = new ArrayList<>();
                    for (RhListaFormaPagoBeneficioSocialTO o : respues) {
                        if (!o.getFpInactivo()) {
                            listaResultante.add(o);
                        }
                    }
                    if (!listaResultante.isEmpty()) {
                        resp.setExtraInfo(listaResultante);
                    } else {
                        resp.setOperacionMensaje("No se encontraron formas de pago beneficios sociales.");
                    }
                } else {
                    resp.setExtraInfo(respues);
                }
            } else {
                resp.setOperacionMensaje("No se encontraron formas de pago beneficios sociales.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionRhFormaPagoBeneficioSocial")
    public RespuestaWebTO accionRhFormaPagoBeneficioSocial(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhFormaPagoBeneficioSocialTO rhFormaPagoBeneficioSocialTO = UtilsJSON
                .jsonToObjeto(RhFormaPagoBeneficioSocialTO.class, map.get("rhFormaPagoBeneficioSocialTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        String accionRpta = UtilsJSON.jsonToObjeto(String.class, map.get("accionRpta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = formaPagoBeneficioSocialService.accionRhFormaPagoBeneficioSocial(rhFormaPagoBeneficioSocialTO, accion, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                String mensaje = respues.substring(1);
                if ("I".equals(accionRpta)) {
                    mensaje = "La Forma de Pago beneficios sociales " + rhFormaPagoBeneficioSocialTO.getCtaCodigo() + " - "
                            + rhFormaPagoBeneficioSocialTO.getFpDetalle() + " se ha inactivado correctamente.";
                }
                if ("A".equals(accionRpta)) {
                    mensaje = "La Forma de Pago beneficios sociales " + rhFormaPagoBeneficioSocialTO.getCtaCodigo() + " - "
                            + rhFormaPagoBeneficioSocialTO.getFpDetalle() + " se ha activado correctamente.";
                }
                resp.setExtraInfo(mensaje);
                resp.setOperacionMensaje(mensaje);
            } else {
                String mensaje = " modificado ";
                if ("I".equals(accionRpta)) {
                    mensaje = " inactivado ";
                }
                if ("A".equals(accionRpta)) {
                    mensaje = " activado ";
                }
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "No se ha" + mensaje + "forma de pago beneficios sociales.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhFormaPagoBeneficioSocial")
    public RespuestaWebTO insertarRhFormaPagoBeneficioSocial(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhFormaPagoBeneficioSocialTO rhFormaPagoBeneficioSocialTO = UtilsJSON.jsonToObjeto(RhFormaPagoBeneficioSocialTO.class, map.get("rhFormaPagoBeneficioSocialTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RhFormaPagoBeneficioSocialTO respues = formaPagoBeneficioSocialService.insertarRhFormaPagoBeneficioSocial(rhFormaPagoBeneficioSocialTO, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La forma de pago beneficios sociales " + respues.getFpDetalle() + " se ha guardado correctamente.");
            } else {
                resp.setOperacionMensaje("No se ha guardado la forma de pago beneficios sociales.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarRhFormaPagoBeneficioSocial")
    public @ResponseBody
    String exportarRhFormaPagoBeneficioSocial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhListaFormaPagoBeneficioSocialTO> listado = UtilsJSON.jsonToList(RhListaFormaPagoBeneficioSocialTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarRhListaFormaPagoBeneficioSocialTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhFormaPagoBeneficiosTO")
    public @ResponseBody
    String generarReporteRhFormaPagoBeneficiosTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportFormaPagoBeneficios.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaFormaPagoBeneficioSocialTO> listado = UtilsJSON.jsonToList(RhListaFormaPagoBeneficioSocialTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhFormaPagoBeneficiosTO(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Empleados*/
    @RequestMapping("/getListaEmpleado")
    public RespuestaWebTO getListaEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhEmpleado> respues = empleadoService.getListaEmpleado(empresa, buscar, estado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron trabajadores.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaBusquedaEmpleados")
    public RespuestaWebTO obtenerDatosParaBusquedaEmpleados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = empleadoService.obtenerDatosParaBusquedaEmpleados(map);
            if (respues != null && !respues.isEmpty()) {
                List<RhAnticipoMotivo> motivosAnticipos = anticipoMotivoService.getListaRhAnticipoMotivo(sisInfoTO.getEmpresa());
                respues.put("motivosAnticipos", motivosAnticipos);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para buscar trabajadores.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaCrudEmpleados")
    public RespuestaWebTO obtenerDatosParaCrudEmpleados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = empleadoService.obtenerDatosParaCrudEmpleados(map);
            if (respues != null && !respues.isEmpty()) {
                List<RhAnticipoMotivo> motivosAnticipos = anticipoMotivoService.getListaRhAnticipoMotivo(sisInfoTO.getEmpresa());
                respues.put("motivosAnticipos", motivosAnticipos);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para desarrollar trabajadores.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarRhEmpleado")
    public RespuestaWebTO insertarModificarRhEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhEmpleado rhEmpleado = UtilsJSON.jsonToObjeto(RhEmpleado.class, map.get("rhEmpleado"));
        List<RhEmpleadoDescuentosFijos> listEmpleadoDescuentosFijos = UtilsJSON.jsonToList(RhEmpleadoDescuentosFijos.class, map.get("listEmpleadoDescuentosFijos"));
        String imagen = UtilsJSON.jsonToObjeto(String.class, map.get("imagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] decodedString = imagen != null ? Base64.getDecoder().decode(imagen.getBytes("UTF-8")) : null;
            MensajeTO mensajeTO = empleadoService.insertarModificarRhEmpleado(rhEmpleado, listEmpleadoDescuentosFijos, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                try {
                    empleadoService.guardarImagenEmpleado(decodedString, rhEmpleado);
                } catch (Exception e) {
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado trabajador.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/cambiarEstadoRhEmpleado")
    public RespuestaWebTO cambiarEstadoRhEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhEmpleadoPK pk = UtilsJSON.jsonToObjeto(RhEmpleadoPK.class, map.get("pk"));
        boolean activar = UtilsJSON.jsonToObjeto(boolean.class, map.get("activar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = empleadoService.cambiarEstadoRhEmpleado(pk, activar, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMensaje().substring(1));
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha modificado trabajador.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhEmpleado")
    public RespuestaWebTO eliminarRhEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhEmpleadoPK rhEmpleadoPK = UtilsJSON.jsonToObjeto(RhEmpleadoPK.class, map.get("rhEmpleadoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String mensajeTO = empleadoService.eliminarRhEmpleado(rhEmpleadoPK, sisInfoTO);
            if (mensajeTO != null && mensajeTO.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.substring(1));
                resp.setExtraInfo(mensajeTO.substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null ? mensajeTO.substring(1) : "No se ha eliminado trabajador.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/cambiarCedulaEmpleado")
    public RespuestaWebTO cambiarCedulaEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cedulaInconrrecta = UtilsJSON.jsonToObjeto(String.class, map.get("cedulaInconrrecta"));
        String cedulaCorrecta = UtilsJSON.jsonToObjeto(String.class, map.get("cedulaCorrecta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = empleadoService.cambiarCedulaEmpleado(empresa, cedulaInconrrecta, cedulaCorrecta);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo("El trabajador con cédula " + cedulaInconrrecta + " se ha modificado correctamente.");
                resp.setOperacionMensaje("El trabajador con cédula " + cedulaInconrrecta + " se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getEmpleadoDescuentosFijos")
    public RespuestaWebTO getEmpleadoDescuentosFijos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, map.get("empleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhEmpleadoDescuentosFijos> respues = empleadoDescuentoFijoService.getEmpleadoDescuentosFijos(empresa, empleado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron descuentos fijos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerComplementosEmpleado")
    public RespuestaWebTO obtenerComplementosEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, map.get("empleado"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = empleadoService.obtenerComplementosEmpleado(map);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos adicionales para el trabajador.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getRhFunListadoEmpleadosTO")
    public RespuestaWebTO getRhFunListadoEmpleadosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String provincia = UtilsJSON.jsonToObjeto(String.class, map.get("provincia"));
        String canton = UtilsJSON.jsonToObjeto(String.class, map.get("canton"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhFunListadoEmpleadosTO> respues = empleadoService.getRhFunListadoEmpleadosTO(empresa, provincia, canton, sector, categoria, estado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron trabajadores.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarEmpleado")
    public @ResponseBody
    String exportarEmpleado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhEmpleado> listado = UtilsJSON.jsonToList(RhEmpleado.class, map.get("listaEmpleado"));
        List<RhFunListadoEmpleadosTO> listadoC = UtilsJSON.jsonToList(RhFunListadoEmpleadosTO.class, map.get("listaConsultaEmpleado"));
        String tipoVista = UtilsJSON.jsonToObjeto(String.class, map.get("tipoVista"));
        List<DetalleExportarFiltrado> listadoFiltrado = UtilsJSON.jsonToList(DetalleExportarFiltrado.class, map.get("listaFiltradoTrabajadores"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean filtrarPorGrupo = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarPorGrupo"));

        try {
            if (tipoVista.equals("A")) {
                respuesta = reporteRrhhService.exportarRhEmpleado(usuarioEmpresaReporteTO, listado, listadoFiltrado, filtrarPorGrupo);
            } else {
                respuesta = reporteRrhhService.exportarRhFunListadoEmpleadosTO(usuarioEmpresaReporteTO, listadoC, listadoFiltrado, filtrarPorGrupo);
            }
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhEmpleado")
    public @ResponseBody
    String generarReporteRhEmpleado(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte;
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhEmpleado> listado = UtilsJSON.jsonToList(RhEmpleado.class, parametros.get("lista"));
        List<RhFunListadoEmpleadosTO> listadoC = UtilsJSON.jsonToList(RhFunListadoEmpleadosTO.class, parametros.get("lista"));
        String tipoVista = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipoVista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            switch (tipoVista) {
                case "A"://archivo
                    nombreReporte = "reportEmpleadosArchivo.jrxml";
                    respuesta = reporteRrhhService.generarReporteRhEmpleado(usuarioEmpresaReporteTO, nombreReporte, listado);
                    break;
                case "I"://individual
                    nombreReporte = "reportEmpleado.jrxml";
                    respuesta = reporteRrhhService.generarReporteRhEmpleadoDatosPersonales(usuarioEmpresaReporteTO, nombreReporte, listado);
                    break;
                default:
                    nombreReporte = "reportEmpleadosBusqueda.jrxml";
                    respuesta = reporteRrhhService.generarReporteRhEmpleadoConsuta(usuarioEmpresaReporteTO, nombreReporte, listadoC);
                    break;
            }
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarCertificadoTrabajo")
    public @ResponseBody
    String generarCertificadoTrabajo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte;
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhEmpleado> listado = UtilsJSON.jsonToList(RhEmpleado.class, parametros.get("lista"));
        String gerente = UtilsJSON.jsonToObjeto(String.class, parametros.get("gerente"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            nombreReporte = "reportCertificadoTrabajo.jrxml";
            respuesta = reporteRrhhService.generarCertificadoTrabajo(usuarioEmpresaReporteTO, nombreReporte, listado, gerente);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteVacaciones")
    public @ResponseBody
    String generarReporteComprobanteVacaciones(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte;
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String vacacionesDias = UtilsJSON.jsonToObjeto(String.class, parametros.get("vacacionesDias"));
        String vacacionesDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("vacacionesDesde"));
        String vacacionHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("vacacionHasta"));
        String empleadoNombre = UtilsJSON.jsonToObjeto(String.class, parametros.get("empleadoNombre"));
        String empleadoApellido = UtilsJSON.jsonToObjeto(String.class, parametros.get("empleadoApellido"));
        String idEmpleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("idEmpleado"));
        String gerente = UtilsJSON.jsonToObjeto(String.class, parametros.get("gerente"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            nombreReporte = "reportCertificadoVacaciones.jrxml";
            respuesta = reporteRrhhService.generarReporteComprobanteVacaciones(usuarioEmpresaReporteTO, nombreReporte, vacacionesDias, vacacionesDesde, vacacionHasta, empleadoNombre, empleadoApellido, idEmpleado, gerente);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Motivo anticipos*/
    @RequestMapping("/getListaRhAnticipoMotivo")
    public RespuestaWebTO getListaRhAnticipoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhAnticipoMotivo> respues = anticipoMotivoService.getListaRhAnticipoMotivo(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (!estado) {
                    List<RhAnticipoMotivo> listaResultante = new ArrayList<>();
                    for (RhAnticipoMotivo o : respues) {
                        if (!o.getMotInactivo()) {
                            listaResultante.add(o);
                        }
                    }
                    if (!listaResultante.isEmpty()) {
                        resp.setExtraInfo(listaResultante);
                    } else {
                        resp.setOperacionMensaje("No se encontraron motivos de anticipos.");
                    }
                } else {
                    resp.setExtraInfo(respues);
                }
            } else {
                resp.setOperacionMensaje("No se encontraron motivos de anticipos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhAnticipoMotivo")
    public RespuestaWebTO insertarRhAnticipoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhAnticipoMotivo rhAnticipoMotivo = UtilsJSON.jsonToObjeto(RhAnticipoMotivo.class, map.get("rhAnticipoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = anticipoMotivoService.insertarRhAnticipoMotivo(rhAnticipoMotivo, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado motivo de anticipo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhAnticipoMotivo")
    public RespuestaWebTO modificarRhAnticipoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhAnticipoMotivo rhAnticipoMotivo = UtilsJSON.jsonToObjeto(RhAnticipoMotivo.class, map.get("rhAnticipoMotivo"));
        String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String mensajeTO = anticipoMotivoService.modificarRhAnticipoMotivo(rhAnticipoMotivo, sisInfoTO);
            if (mensajeTO != null && mensajeTO.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                String mensaje = mensajeTO.substring(1);
                if ("I".equals(accion)) {
                    mensaje = "El motivo del anticipo " + rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle() + " se ha inactivado correctamente.";
                }
                if ("A".equals(accion)) {
                    mensaje = "El motivo del anticipo " + rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle() + " se ha activado correctamente.";
                }
                resp.setExtraInfo(mensaje);
                resp.setOperacionMensaje(mensaje);
            } else {
                String mensaje = " modificado ";
                if ("I".equals(accion)) {
                    mensaje = " inactivado ";
                }
                if ("A".equals(accion)) {
                    mensaje = " activado ";
                }
                resp.setOperacionMensaje(mensajeTO != null ? mensajeTO : "No se ha" + mensaje + "motivo de anticipo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhAnticipoMotivo")
    public RespuestaWebTO eliminarRhAnticipoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhAnticipoMotivo rhAnticipoMotivo = UtilsJSON.jsonToObjeto(RhAnticipoMotivo.class, map.get("rhAnticipoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String mensajeTO = anticipoMotivoService.eliminarRhAnticipoMotivo(rhAnticipoMotivo, sisInfoTO);
            if (mensajeTO != null && mensajeTO.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.substring(1));
                resp.setOperacionMensaje(mensajeTO.substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null ? mensajeTO.substring(1) : "No se ha eliminado motivo de anticipo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarRhAnticipoMotivo")
    public @ResponseBody
    String exportarRhAnticipoMotivo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhAnticipoMotivo> listado = UtilsJSON.jsonToList(RhAnticipoMotivo.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarRhAnticipoMotivo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhAnticipoMotivo")
    public @ResponseBody
    String generarReporteRhAnticipoMotivo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoAnticipo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhAnticipoMotivo> listado = UtilsJSON.jsonToList(RhAnticipoMotivo.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhAnticipoMotivo(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*CONSULTAS*/
 /*Consolidado ingresos (no tiene reporte)*/
    @RequestMapping("/getRhFunFormulario107_2013")
    public RespuestaWebTO getRhFunFormulario107_2013(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        Character status = UtilsJSON.jsonToObjeto(Character.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhFunFormulario107_2013TO> respues = formulario107Service.getRhFunFormulario107_2013(empresa, desde, hasta, status);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteConsolidadosDeIngresos")
    public @ResponseBody
    String exportarReporteConsolidadosDeIngresos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhFunFormulario107_2013TO> listaConsolidadoIngresos = UtilsJSON.jsonToList(RhFunFormulario107_2013TO.class,
                map.get("listaConsolidadoIngresos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadosDeIngresos(usuarioEmpresaReporteTO, listaConsolidadoIngresos, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteTXTConsolidadosDeIngresos")
    public @ResponseBody
    void exportarReporteTXTConsolidadosDeIngresos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhFunFormulario107_2013TO> listaConsolidadoIngresos = UtilsJSON.jsonToList(RhFunFormulario107_2013TO.class, map.get("listaConsolidadoIngresos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            reporteRrhhService.exportarReporteTXTConsolidadosDeIngresos(listaConsolidadoIngresos, response, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
    }

    // DETALLE DE ANTICIPOS
    @RequestMapping("/getComboRhFormaPagoTO")
    public RespuestaWebTO getComboRhFormaPagoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhComboFormaPagoTO> respues = formaPagoService.getComboFormaPagoTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron formas de pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getRhDetalleAnticiposFiltradoTO")
    public RespuestaWebTO getRhDetalleAnticiposFiltradoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
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
            List<RhListaDetalleAnticiposTO> respues = anticipoService.getRhDetalleAnticiposFiltradoTO(empCodigo, fechaDesde, fechaHasta, empCategoria,
                    empId, formaPago, parametro);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetalleAnticipos")
    public @ResponseBody
    String generarReporteDetalleAnticipos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRRHHDetalleAnticipos";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empId = UtilsJSON.jsonToObjeto(String.class, parametros.get("empId"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCodigo"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposTO.class,
                parametros.get("listaDetalleAnticiposTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleAnticipos(usuarioEmpresaReporteTO, empId, empCodigo,
                    empCategoria, fechaDesde, fechaHasta, listaDetalleAnticiposTO, nombre);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteAnticipo")
    public @ResponseBody
    String exportarReporteAnticipo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposTO.class,
                map.get("listaDetalleAnticiposTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteAnticipoDetalle(usuarioEmpresaReporteTO, listaDetalleAnticiposTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteAnticipo")
    public @ResponseBody
    String generarReporteAnticipo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposTO.class,
                parametros.get("listaDetalleAnticiposTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteAnticipoDetalleAnticipo(usuarioEmpresaReporteTO, listaDetalleAnticiposTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteAnticipoContable")
    public @ResponseBody
    String generarReporteAnticipoContable(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposTO.class,
                parametros.get("listaDetalleAnticiposTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteAnticipoContable(usuarioEmpresaReporteTO, listaDetalleAnticiposTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // soporte contable de anticipo (no hay reporte)
    @RequestMapping("/getRhDetalleAnticiposLoteTO")
    public RespuestaWebTO getRhDetalleAnticiposLoteTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaDetalleAnticiposLoteTO> respues = anticipoService.getRhDetalleAnticiposLoteTO(empresa, periodo, tipo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsultaAnticiposLote")
    public @ResponseBody
    String generarReporteConsultaAnticiposLote(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleAnticiposLoteTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposLoteTO.class,
                parametros.get("listaDetalleAnticiposLoteTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsultaAnticiposLoteWeb(usuarioEmpresaReporteTO, listaDetalleAnticiposTO, nombre, periodo, tipo, numero);
            return archivoService.generarReportePDF(respuesta, nombre, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteAnticipoLoteIndividual")
    public @ResponseBody
    String generarReporteAnticipoLoteIndividual(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteAnticipoOprestamos = UtilsJSON.jsonToList(ReporteAnticipoPrestamoXIIISueldo.class,
                parametros.get("listaDetalleAnticiposLoteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteAnticipo(usuarioEmpresaReporteTO, rhReporteAnticipoOprestamos);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteContable")
    public @ResponseBody
    String generarReporteContable(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConContableReporteTO> listConContableReporteTO = anticipoService.generarConContableReporte(usuarioEmpresaReporteTO, periodo, tipo, numero, sisInfoTO);
            respuesta = reporteContabilidadService.generarReporteContableDetalle(usuarioEmpresaReporteTO,
                    listConContableReporteTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarDetalleAnticiposLoteTO")
    public @ResponseBody
    String exportarDetalleAnticiposLoteTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        List<RhListaDetalleAnticiposLoteTO> listaDetalleAnticiposLoteTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposLoteTO.class,
                map.get("listaDetalleAnticiposLoteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteAnticipoDetalleLote(usuarioEmpresaReporteTO, listaDetalleAnticiposLoteTO, tipo, numero, periodo);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // detalle de prestamos
    @RequestMapping("/getRhDetallePrestamosTO")
    public RespuestaWebTO getRhDetallePrestamosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaDetallePrestamosTO> respues = prestamoService.getRhDetallePrestamosTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetallePrestamos")
    public @ResponseBody
    String generarReporteDetallePrestamos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRRHHDetallePrestamos";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, parametros.get("empId"));
        List<RhListaDetallePrestamosTO> listaDetallePrestamosTO = UtilsJSON.jsonToList(RhListaDetallePrestamosTO.class,
                parametros.get("listaDetallePrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetallePrestamos(usuarioEmpresaReporteTO, empCodigo, fechaDesde,
                    fechaHasta, empCategoria, empId, listaDetallePrestamosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteDetallePrestamos")
    public @ResponseBody
    String exportarReporteDetallePrestamos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaDetallePrestamosTO> listaDetallePrestamosTO = UtilsJSON.jsonToList(RhListaDetallePrestamosTO.class,
                map.get("listaDetallePrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReportePrestamos(usuarioEmpresaReporteTO, listaDetallePrestamosTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // detalle de anticipos y prestanmos
    @RequestMapping("/getRhDetalleAnticiposPrestamosTO")
    public RespuestaWebTO getRhDetalleAnticiposPrestamosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String parametro = UtilsJSON.jsonToObjeto(String.class, map.get("parametro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaDetalleAnticiposPrestamosTO> respues = prestamoService.getRhDetalleAnticiposPrestamosTO(empCodigo, fechaDesde, fechaHasta, empCategoria,
                    empId, parametro);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetalleAnticiposPrestamos")
    public @ResponseBody
    String generarReporteDetalleAnticiposPrestamos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRRHHDetalleAnticiposPrestamos";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, parametros.get("empId"));
        List<RhListaDetalleAnticiposPrestamosTO> listaDetalleAnticiposPrestamosTO = UtilsJSON
                .jsonToList(RhListaDetalleAnticiposPrestamosTO.class, parametros.get("listaDetalleAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleAnticiposPrestamos(usuarioEmpresaReporteTO, empCodigo,
                    fechaDesde, fechaHasta, empCategoria, empId, listaDetalleAnticiposPrestamosTO, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteDetalleAnticiposPrestamos")
    public @ResponseBody
    String exportarReporteDetalleAnticiposPrestamos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaDetalleAnticiposPrestamosTO> listaDetalleAnticiposPrestamosTO = UtilsJSON
                .jsonToList(RhListaDetalleAnticiposPrestamosTO.class, map.get("listaDetalleAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleDeAnticipoPrestamo(usuarioEmpresaReporteTO, listaDetalleAnticiposPrestamosTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //detalle de bono
    @RequestMapping("/getRhListaDetalleBonosFiltradoTO")
    public RespuestaWebTO getRhListaDetalleBonosFiltradoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
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
            List<RhListaDetalleBonosTO> respues = bonoService.getRhListaDetalleBonosFiltradoTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId,
                    estadoDeducible, parametro);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetalleBonos")
    public @ResponseBody
    String generarReporteDetalleBonos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportDetalleBonos";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreEmpleado"));
        List<RhListaDetalleBonosTO> listaDetalleBonosTO = UtilsJSON.jsonToList(RhListaDetalleBonosTO.class,
                parametros.get("listaDetalleBonosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleBonos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta,
                    empCategoria, nombreEmpleado, listaDetalleBonosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleBonosMatricial")
    public RespuestaWebTO generarReporteDetalleBonosMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreEmpleado"));
        List<RhListaDetalleBonosTO> listaDetalleHorasExtrasTO = UtilsJSON.jsonToList(RhListaDetalleBonosTO.class, parametros.get("listaDetalleBonosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            byte[] respuesta = reporteRrhhService.generarReporteDetalleBonos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta,
                    empCategoria, nombreEmpleado, listaDetalleHorasExtrasTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteDetalleDeBono")
    public @ResponseBody
    String exportarReporteDetalleDeBono(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaDetalleBonosTO> listaDetalleBonosTO = UtilsJSON.jsonToList(RhListaDetalleBonosTO.class,
                map.get("listaDetalleBonosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleDeBono(usuarioEmpresaReporteTO, listaDetalleBonosTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //detalle de horas extras
    @RequestMapping("/getRhListaDetalleHorasExtrasFiltradoTO")
    public RespuestaWebTO getRhListaDetalleHorasExtrasFiltradoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String parametro = UtilsJSON.jsonToObjeto(String.class, map.get("parametro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaDetalleHorasExtrasTO> respues = horasExtrasService.getRhListaDetalleHorasExtrasFiltradoTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId, parametro);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Listado de consolidado Horas Extras
    @RequestMapping("/getRhListadoConsolidadoHorasExtrasTO")
    public RespuestaWebTO getRhListadoConsolidadoHorasExtrasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaConsolidadoHorasExtrasTO> respues = horasExtrasService.getRhListadoConsolidadoHorasExtrasTO(empCodigo, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetalleHorasExtras")
    public @ResponseBody
    String generarReporteDetalleHorasExtras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportDetalleHorasExtras";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreEmpleado"));
        List<RhListaDetalleHorasExtrasTO> listaDetalleHorasExtrasTO = UtilsJSON.jsonToList(RhListaDetalleHorasExtrasTO.class, parametros.get("listaDetalleHorasExtrasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleHorasExtras(usuarioEmpresaReporteTO, fechaDesde, fechaHasta,
                    empCategoria, nombreEmpleado, listaDetalleHorasExtrasTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleHorasExtrasMatricial")
    public RespuestaWebTO generarReporteDetalleHorasExtrasMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreEmpleado"));
        List<RhListaDetalleHorasExtrasTO> listaDetalleHorasExtrasTO = UtilsJSON.jsonToList(RhListaDetalleHorasExtrasTO.class, parametros.get("listaDetalleHorasExtrasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            byte[] respuesta = reporteRrhhService.generarReporteDetalleHorasExtras(usuarioEmpresaReporteTO, fechaDesde, fechaHasta,
                    empCategoria, nombreEmpleado, listaDetalleHorasExtrasTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteDetalleDeHorasExtras")
    public @ResponseBody
    String exportarReporteDetalleDeHorasExtras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaDetalleHorasExtrasTO> listaDetalleHorasExtrasTO = UtilsJSON.jsonToList(RhListaDetalleHorasExtrasTO.class, map.get("listaDetalleHorasExtrasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteDetalleDeHorasExtras(usuarioEmpresaReporteTO, listaDetalleHorasExtrasTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // soporte contable de bonos
    @RequestMapping("/getRhDetalleBonosLoteTO")
    public RespuestaWebTO getRhDetalleBonosLoteTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaDetalleBonosLoteTO> respues = bonoService.getRhDetalleBonosLoteTO(empresa, periodo, tipo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetalleBonosLote")
    public String generarReporteDetalleBonosLote(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        List<RhListaDetalleBonosLoteTO> listaDetalleBonosLoteTO = UtilsJSON.jsonToList(RhListaDetalleBonosLoteTO.class,
                parametros.get("listaDetalleBonosLoteTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleBonosLote(usuarioEmpresaReporteTO, periodo, tipo, numero,
                    listaDetalleBonosLoteTO, nombre);
            return archivoService.generarReportePDF(respuesta, nombre, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSoporteContableDeBono")
    public @ResponseBody
    String exportarReporteSoporteContableDeBono(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleBonosLoteTO> listaSoporteContableBonoTO = UtilsJSON.jsonToList(RhListaDetalleBonosLoteTO.class,
                map.get("listaSoporteContableBonoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteBonoDetalleLote(usuarioEmpresaReporteTO, listaSoporteContableBonoTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // soporte contable de horas extras
    @RequestMapping("/getRhDetalleHorasExtrasLoteTO")
    public RespuestaWebTO getRhDetalleHorasExtrasLoteTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaDetalleHorasExtrasLoteTO> respues = horasExtrasService.getRhDetalleHorasExtrasLoteTO(empresa, periodo, tipo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetalleHorasExtrasLote")
    public String generarReporteDetalleHorasExtrasLote(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        List<RhListaDetalleHorasExtrasLoteTO> listaDetalleHorasExtrasLoteTO = UtilsJSON.jsonToList(RhListaDetalleHorasExtrasLoteTO.class, parametros.get("RhListaDetalleHorasExtrasLoteTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleHorasExtrasLote(usuarioEmpresaReporteTO, periodo, tipo, numero, listaDetalleHorasExtrasLoteTO, nombre);
            return archivoService.generarReportePDF(respuesta, nombre, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDetalleHorasExtrasLoteMatricial")
    public RespuestaWebTO generarReporteDetalleHorasExtrasLoteMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        List<RhListaDetalleHorasExtrasLoteTO> listaDetalleHorasExtrasLoteTO = UtilsJSON.jsonToList(RhListaDetalleHorasExtrasLoteTO.class, parametros.get("RhListaDetalleHorasExtrasLoteTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            byte[] respuesta = reporteRrhhService.generarReporteDetalleHorasExtrasLote(usuarioEmpresaReporteTO, periodo, tipo, numero, listaDetalleHorasExtrasLoteTO, nombre);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteSoporteContableDeHorasExtras")
    public @ResponseBody
    String exportarReporteSoporteContableDeHorasExtras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleHorasExtrasLoteTO> listaSoporteContableHorasExtrasTO = UtilsJSON.jsonToList(RhListaDetalleHorasExtrasLoteTO.class, map.get("listaSoporteContableHorasExtrasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteHorasExtrasDetalleLote(usuarioEmpresaReporteTO, listaSoporteContableHorasExtrasTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // consolidado de bonos
    @RequestMapping("/getRhConsolidadoBonosTO")
    public RespuestaWebTO getRhConsolidadoBonosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaConsolidadoBonosTO> respues = bonoService.getRhConsolidadoBonosTO(empCodigo, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoBonosViatico")
    public String generarReporteConsolidadoBonosViatico(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsolidadoBonosViaticos";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaConsolidadoBonosTO> listaConsolidadoBonosViaticosTO = UtilsJSON
                .jsonToList(RhListaConsolidadoBonosTO.class, parametros.get("listaConsolidadoBonosViaticosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoBonosViatico(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, listaConsolidadoBonosViaticosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConsolidadoBonos")
    public @ResponseBody
    String exportarReporteConsolidadoBonos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        List<RhListaConsolidadoBonosTO> listaConsolidadoBonosTO = UtilsJSON.jsonToList(RhListaConsolidadoBonosTO.class,
                map.get("listaConsolidadoBonosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteConsolidadoBonos(usuarioEmpresaReporteTO, listaConsolidadoBonosTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //Exportat consolidado horas extras
    @RequestMapping("/exportarReporteConsolidadoHorasExtras")
    public @ResponseBody
    String exportarReporteConsolidadoHorasExtras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        List<RhListaConsolidadoHorasExtrasTO> listaConsolidadoHorasExtrasTO = UtilsJSON.jsonToList(RhListaConsolidadoHorasExtrasTO.class, map.get("listaConsolidadoBonosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteConsolidadoHorasExtras(usuarioEmpresaReporteTO, listaConsolidadoHorasExtrasTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // saldo consolidado de bonos
    @RequestMapping("/getRhSaldoConsolidadoBonosTO")
    public RespuestaWebTO getRhSaldoConsolidadoBonosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaSaldoConsolidadoBonosTO> respues = bonoService.getRhSaldoConsolidadoBonosTO(empCodigo, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoBonosViaticos")
    public @ResponseBody
    String generarReporteSaldoConsolidadoBonosViaticos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSaldoConsolidadoBonosViaticos";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaSaldoConsolidadoBonosTO> listaSaldoConsolidadoBonosViaticosTO = UtilsJSON
                .jsonToList(RhListaSaldoConsolidadoBonosTO.class, parametros.get("listaSaldoConsolidadoBonosViaticosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteSaldoConsolidadoBonosViaticos(usuarioEmpresaReporteTO, fechaHasta,
                    listaSaldoConsolidadoBonosViaticosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSaldoConsolidadoBonosViaticos")
    public @ResponseBody
    String exportarReporteSaldoConsolidadoBonosViaticos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaSaldoConsolidadoBonosTO> listaSaldoConsolidadoBonosViaticosTO = UtilsJSON.jsonToList(RhListaSaldoConsolidadoBonosTO.class,
                map.get("listaSaldoConsolidadoBonosViaticosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteSaldosConsolidadosDeBonos(usuarioEmpresaReporteTO, listaSaldoConsolidadoBonosViaticosTO, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // saldo consolidado de horas extras
    @RequestMapping("/getRhSaldoConsolidadoHorasExtrasTO")
    public RespuestaWebTO getRhSaldoConsolidadoHorasExtrasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaSaldoConsolidadoHorasExtrasTO> respues = horasExtrasService.getRhSaldoConsolidadoHorasExtrasTO(empCodigo, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoHorasExtras")
    public @ResponseBody
    String generarReporteSaldoConsolidadoHorasExtras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSaldoConsolidadoHorasExtras";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaSaldoConsolidadoHorasExtrasTO> listaSaldoConsolidadoHorasExtras = UtilsJSON.jsonToList(RhListaSaldoConsolidadoHorasExtrasTO.class, parametros.get("listaSaldoConsolidadoHorasExtras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteSaldoConsolidadoHorasExtras(usuarioEmpresaReporteTO, fechaHasta, listaSaldoConsolidadoHorasExtras);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoHorasExtrasMatricial")
    public RespuestaWebTO generarReporteSaldoConsolidadoHorasExtrasMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaSaldoConsolidadoHorasExtrasTO> listaSaldoConsolidadoHorasExtras = UtilsJSON.jsonToList(RhListaSaldoConsolidadoHorasExtrasTO.class, parametros.get("listaSaldoConsolidadoHorasExtras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteSaldoConsolidadoHorasExtras(usuarioEmpresaReporteTO, fechaHasta, listaSaldoConsolidadoHorasExtras);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteSaldoConsolidadoHorasExtras")
    public @ResponseBody
    String exportarReporteSaldoConsolidadoHorasExtras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaSaldoConsolidadoHorasExtrasTO> listaSaldoConsolidadoHorasExtras = UtilsJSON.jsonToList(RhListaSaldoConsolidadoHorasExtrasTO.class,
                map.get("listaSaldoConsolidadoHorasExtras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteSaldoConsolidadoHorasExtras(usuarioEmpresaReporteTO, listaSaldoConsolidadoHorasExtras, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // consolidado de anticipos prestamos
    @RequestMapping("/getRhConsolidadoAnticiposPrestamosTO")
    public RespuestaWebTO getRhConsolidadoAnticiposPrestamosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaConsolidadoAnticiposPrestamosTO> respues = prestamoService.getRhConsolidadoAnticiposPrestamosTO(empCodigo, fechaDesde, fechaHasta, empCategoria,
                    empId);
            if (!respues.isEmpty() && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoAnticiposPrestamos")
    public @ResponseBody
    String generarReporteConsolidadoAnticiposPrestamos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSaldoConsolidadoAnticiposPrestamos";
        byte[] respuesta;

        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaConsolidadoAnticiposPrestamosTO> listaConsolidadoAnticiposPrestamosTO = UtilsJSON.jsonToList(
                RhListaConsolidadoAnticiposPrestamosTO.class, parametros.get("listaConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoAnticiposPrestamos(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, listaConsolidadoAnticiposPrestamosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConsolidadosAnticiposPrestamos")
    public @ResponseBody
    String exportarReporteConsolidadosAnticiposPrestamos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaConsolidadoAnticiposPrestamosTO> listaConsolidadoAnticiposPrestamosTO = UtilsJSON.jsonToList(RhListaConsolidadoAnticiposPrestamosTO.class,
                map.get("listaConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadosAnticiposPrestamos(usuarioEmpresaReporteTO, listaConsolidadoAnticiposPrestamosTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*XIII SUELDO*/
    @RequestMapping("/getRhFunConsultarXiiiSueldo")
    public RespuestaWebTO getRhFunConsultarXiiiSueldo(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhFunXiiiSueldoConsultarTO> respues = xiiiSueldoService.getRhFunConsultarXiiiSueldo(empresa, sector, desde, hasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteXiiiSueldoConsulta")
    public @ResponseBody
    String generarReporteXiiiSueldoConsulta(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportXiiiSueldoConsulta.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        List<RhFunXiiiSueldoConsultarTO> rhFunXiiiSueldoConsultarTO = UtilsJSON.jsonToList(RhFunXiiiSueldoConsultarTO.class, parametros.get("rhFunXiiiSueldoConsultarTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXiiiSueldoConsulta(usuarioEmpresaReporteTO, sector, periodo, fechaDesde, fechaHasta, fechaMaxima, rhFunXiiiSueldoConsultarTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteXIIISueldo")
    public @ResponseBody
    String generarReporteComprobanteXIIISueldo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteXIIISueldo.jrxml";
        byte[] respuesta;
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldo = new ArrayList<>();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhFunXiiiSueldoConsultarTO> lista = UtilsJSON.jsonToList(RhFunXiiiSueldoConsultarTO.class, parametros.get("listaRhFunXiiiSueldoConsultarTO"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        for (int i = 0; i < lista.size(); i++) {
            ReporteAnticipoPrestamoXIIISueldo reporte = reporteRrhhService.convertirRhFunXiiiSueldoConsultarTOAReporteAnticipoPrestamoXIIISueldo(lista.get(i), fechaMaxima);
            rhReporteXIIISueldo.add(reporte);
        }
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXIIISueldo(usuarioEmpresaReporteTO, rhReporteXIIISueldo);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteXIIISueldoMatricial")
    public RespuestaWebTO generarReporteComprobanteXIIISueldoMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldo = new ArrayList<>();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhFunXiiiSueldoConsultarTO> lista = UtilsJSON.jsonToList(RhFunXiiiSueldoConsultarTO.class, parametros.get("listaRhFunXiiiSueldoConsultarTO"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        for (int i = 0; i < lista.size(); i++) {
            ReporteAnticipoPrestamoXIIISueldo reporte = reporteRrhhService.convertirRhFunXiiiSueldoConsultarTOAReporteAnticipoPrestamoXIIISueldo(lista.get(i), fechaMaxima);
            rhReporteXIIISueldo.add(reporte);
        }
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXIIISueldo(usuarioEmpresaReporteTO, rhReporteXIIISueldo);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteXiiiSueldoConsulta")
    public @ResponseBody
    String exportarReporteXiiiSueldoConsulta(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhFunXiiiSueldoConsultarTO> listado = UtilsJSON.jsonToList(RhFunXiiiSueldoConsultarTO.class, map.get("rhFunXiiiSueldoConsultarTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteXiiiSueldoConsulta(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*XIV SUELDO*/
    @RequestMapping("/getRhFunConsultarXivSueldo")
    public RespuestaWebTO getRhFunConsultarXivSueldo(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhFunXivSueldoConsultarTO> respues = xivSueldoService.getRhFunConsultarXivSueldo(empresa, sector, desde, hasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteXivSueldoConsulta")
    public @ResponseBody
    String generarReporteXivSueldoConsulta(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportXivSueldoConsulta.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        List<RhFunXivSueldoConsultarTO> rhFunXivSueldoConsultarTO = UtilsJSON.jsonToList(RhFunXivSueldoConsultarTO.class, parametros.get("rhFunXivSueldoConsultarTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXivSueldoConsulta(usuarioEmpresaReporteTO, sector, periodo, fechaDesde, fechaHasta, fechaMaxima, rhFunXivSueldoConsultarTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteXIVSueldo")
    public @ResponseBody
    String generarReporteComprobanteXIVSueldo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteXIVSueldo.jrxml";
        byte[] respuesta;
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldo = new ArrayList<>();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        RhFunXivSueldoConsultarTO item = UtilsJSON.jsonToObjeto(RhFunXivSueldoConsultarTO.class, parametros.get("RhFunXivSueldoConsultarTO"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        ReporteAnticipoPrestamoXIIISueldo reporte = reporteRrhhService.convertirRhFunXivSueldoConsultarTOAReporteAnticipoPrestamoXIIISueldo(item, fechaMaxima);
        rhReporteXIIISueldo.add(reporte);
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXIVSueldo(usuarioEmpresaReporteTO, rhReporteXIIISueldo);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteXIVSueldoMatricial")
    public RespuestaWebTO generarReporteComprobanteXIVSueldoMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldo = new ArrayList<>();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        RhFunXivSueldoConsultarTO item = UtilsJSON.jsonToObjeto(RhFunXivSueldoConsultarTO.class, parametros.get("RhFunXivSueldoConsultarTO"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        ReporteAnticipoPrestamoXIIISueldo reporte = reporteRrhhService.convertirRhFunXivSueldoConsultarTOAReporteAnticipoPrestamoXIIISueldo(item, fechaMaxima);
        rhReporteXIIISueldo.add(reporte);
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXIVSueldo(usuarioEmpresaReporteTO, rhReporteXIIISueldo);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteXivSueldoConsulta")
    public @ResponseBody
    String exportarReporteXivSueldoConsulta(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhFunXivSueldoConsultarTO> listado = UtilsJSON.jsonToList(RhFunXivSueldoConsultarTO.class, map.get("rhFunXivSueldoConsultarTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteXivSueldoConsulta(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*UTILIDADES PRECALCULADAS*/
    @RequestMapping("/getRhFunCalcularUtilidades")
    public RespuestaWebTO getRhFunCalcularUtilidades(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
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
            List<RhFunUtilidadesCalcularTO> respues = utilidadService.getRhFunCalcularUtilidades(empresa, sector, desde, hasta, totalDias, totalCargas, totalPagar);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCalcularUtilidades")
    public @ResponseBody
    String generarReporteCalcularUtilidades(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportUtilidadesPreCalculo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        List<RhFunUtilidadesCalcularTO> rhFunUtilidadesCalcularTOs = UtilsJSON.jsonToList(RhFunUtilidadesCalcularTO.class, parametros.get("rhFunUtilidadesCalcularTOs"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteUtilidadesPreCalculo(usuarioEmpresaReporteTO, sector, periodo, fechaDesde, fechaHasta, fechaMaxima, rhFunUtilidadesCalcularTOs);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCalcularUtilidades")
    public @ResponseBody
    String exportarReporteCalcularUtilidades(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhFunUtilidadesCalcularTO> listado = UtilsJSON.jsonToList(RhFunUtilidadesCalcularTO.class, map.get("rhFunUtilidadesCalcularTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteCalcularUtilidades(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*SALDO CONSOLIDADO SUELDO POR PAGAR*/
    @RequestMapping("/getRhSaldoConsolidadoSueldosPorPagarTO")
    public RespuestaWebTO getRhSaldoConsolidadoSueldosPorPagarTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaSaldoConsolidadoSueldosPorPagarTO> respues = rolService.getRhSaldoConsolidadoSueldosPorPagarTO(empresa, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoSueldosPorPagar")
    public @ResponseBody
    String generarReporteSaldoConsolidadoSueldosPorPagar(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSaldoConsolidadoSueldosPorPagar.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaSaldoConsolidadoSueldosPorPagarTO> listado = UtilsJSON.jsonToList(RhListaSaldoConsolidadoSueldosPorPagarTO.class, parametros.get("listaSaldoConsolidadoSueldosPorPagarTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteSaldoConsolidadoSueldosPorPagar(usuarioEmpresaReporteTO, fechaHasta, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSaldoConsolidadoSueldosPorPagar")
    public @ResponseBody
    String exportarReporteSaldoConsolidadoSueldosPorPagar(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaSaldoConsolidadoSueldosPorPagarTO> listado = UtilsJSON.jsonToList(RhListaSaldoConsolidadoSueldosPorPagarTO.class, map.get("listaSaldoConsolidadoSueldosPorPagarTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteSaldoConsolidadoSueldosPorPagar(usuarioEmpresaReporteTO, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*ROL DE PAGOS*/
    @RequestMapping("/getRhDetalleRolesTO")
    public RespuestaWebTO getRhDetalleRolesTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String filtro = UtilsJSON.jsonToObjeto(String.class, map.get("filtro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaDetalleRolesTO> respues = rolService.getRhDetalleRolesTO(empresa, fechaDesde, fechaHasta, sector, empCategoria, empId, filtro);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoRolesPago")
    public @ResponseBody
    String generarReporteListadoRolesPago(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoRolesPago.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreEmpleado"));
        List<RhListaDetalleRolesTO> listaDetalleRolesTO = UtilsJSON.jsonToList(RhListaDetalleRolesTO.class, parametros.get("listaDetalleRolesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteListadoRolesPago(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, empCategoria, nombreEmpleado, listaDetalleRolesTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRolLoteSoporte")
    public @ResponseBody
    String generarReporteRolLoteSoporte(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRolLote.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ReportesRol> lista = UtilsJSON.jsonToList(ReportesRol.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRolLoteSoporte(usuarioEmpresaReporteTO, lista);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoRolesPago")
    public @ResponseBody
    String exportarReporteListadoRolesPago(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empSector = UtilsJSON.jsonToObjeto(String.class, map.get("empSector"));
        String filtro = UtilsJSON.jsonToObjeto(String.class, map.get("filtro"));
        List<RhListaDetalleRolesTO> listado = UtilsJSON.jsonToList(RhListaDetalleRolesTO.class, map.get("listaDetalleRolesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteListadoRolesPago(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, empCategoria, empSector, listado, filtro);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListRhRol")
    public RespuestaWebTO getListRhRol(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhRol> respues = rolService.getListRhRol(conContablePK);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerRhRolSaldoEmpleadoTO")
    public RespuestaWebTO obtenerRhRolSaldoEmpleadoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, map.get("empleado"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RhRolSaldoEmpleadoTO respues = rolService.getRhRolSaldoEmpleado(empresa, empleado, fechaDesde, fechaHasta, tipo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerRol")
    public RespuestaWebTO obtenerRol(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String idEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("idEmpleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            ItemListaRolTO respues = rolService.obtenerItemListaRolTO(conContablePK, empresa, idEmpleado, sisInfoTO);
            if (respues != null) {
                List<RhListaRolSaldoEmpleadoDetalladoTO> detalle = rolService.getRhRolSaldoEmpleadoDetalladoTO(
                        empresa,
                        respues.getRhRol().getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                        respues.getRolDesdeTexto(),
                        respues.getRolHastaTexto());
                respues.setDetalle(detalle);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontro rol");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerVistaPreliminarRol")
    public RespuestaWebTO obtenerVistaPreliminarRol(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRol rol = UtilsJSON.jsonToObjeto(RhRol.class, map.get("rol"));
        String idEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("idEmpleado"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            ItemListaRolTO respues = rolService.obtenerVistaPreliminarRol(rol, idEmpleado, empresa, sisInfoTO);
            if (respues != null) {
                List<RhListaRolSaldoEmpleadoDetalladoTO> detalle = rolService.getRhRolSaldoEmpleadoDetalladoTO(
                        empresa,
                        respues.getRhRol().getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                        respues.getRolDesdeTexto(),
                        respues.getRolHastaTexto());
                respues.setDetalle(detalle);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró rol de pago");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/actualizarDatosDelRolDePago")
    public RespuestaWebTO actualizarDatosDelRolDePago(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRol rol = UtilsJSON.jsonToObjeto(RhRol.class, map.get("rol"));
        String idEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("idEmpleado"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RhRolSaldoEmpleadoTO saldoEmpleado = rolService.actualizarSaldosEmpleado(empresa, idEmpleado, fechaDesde, fechaHasta);
            if (saldoEmpleado != null) {
                rol.setConContable(new ConContable(conContablePK));
                ItemListaRolTO respues = rolService.obtenerVistaPreliminarRol(rol, idEmpleado, empresa, sisInfoTO);
                if (respues != null) {
                    respues.getRhRol().getRhEmpleado().setEmpSaldoPrestamos(saldoEmpleado.getSaldoPrestamo());
                    List<RhListaRolSaldoEmpleadoDetalladoTO> detalle = rolService.getRhRolSaldoEmpleadoDetalladoTO(
                            empresa,
                            respues.getRhRol().getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                            respues.getRolDesdeTexto(),
                            respues.getRolHastaTexto());
                    respues.setDetalle(detalle);
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respues);
                } else {
                    resp.setOperacionMensaje("No se encontró rol de pago");
                }
            } else {
                resp.setOperacionMensaje("Error al obtener saldo de empleado: fun_saldo_empleado");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/completarDatosDelRolDePago")
    public RespuestaWebTO completarDatosDelRolDePago(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        RhRol rol = UtilsJSON.jsonToObjeto(RhRol.class, map.get("rol"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            rol.setConContable(new ConContable(conContablePK));
            rol = rolService.completarDatosDelRolDePago(rol, sisInfoTO);
            if (rol != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(rol);
            } else {
                resp.setOperacionMensaje("No se encontró rol de pago");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/calcularValoresRolesPago")
    public RespuestaWebTO calcularValoresRolesPago(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<RhRol> roles = UtilsJSON.jsonToList(RhRol.class, map.get("listado"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ItemListaRolTO> respues = rolService.calcularValoresRolesPago(roles, empresa, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontro rol");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/calcularPermisoMedico")
    public RespuestaWebTO calcularPermisoMedico(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empleadoId = UtilsJSON.jsonToObjeto(String.class, map.get("empleadoId"));
        Integer diasPermiso = UtilsJSON.jsonToObjeto(Integer.class, map.get("diasPermiso"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            BigDecimal respues = rolService.calcularPermisoMedico(empleadoId, diasPermiso, fechaDesde, empresa, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarValoresCalculadosRol")
    public @ResponseBody
    String exportarValoresCalculadosRol(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ItemListaRolTO> lista = UtilsJSON.jsonToList(ItemListaRolTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarValoresCalculadosRol(usuarioEmpresaReporteTO, lista);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarPlantilla")
    public @ResponseBody
    String exportarPlantilla(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhRol> lista = UtilsJSON.jsonToList(RhRol.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarPlantilla(usuarioEmpresaReporteTO, lista);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*SOPORTE COMPROBANTE PROVISIONES*/
    @RequestMapping("/getRhListaProvisionesComprobanteContableTO")
    public RespuestaWebTO getRhListaProvisionesComprobanteContableTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaProvisionesTO> respues = empleadoService.getRhListaProvisionesComprobanteContableTO(empresa, periodo, tipo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProvisionesComprobanteContable")
    public @ResponseBody
    String generarReporteProvisionesComprobanteContable(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportProvisionesComprobanteContable.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        List<RhListaProvisionesTO> lista = UtilsJSON.jsonToList(RhListaProvisionesTO.class, parametros.get("listaProvisionesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteProvisionesComprobanteContable(usuarioEmpresaReporteTO, periodo, tipo, numero, lista);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteProvisionesComprobanteContable")
    public @ResponseBody
    String exportarReporteProvisionesComprobanteContable(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        List<RhListaProvisionesTO> lista = UtilsJSON.jsonToList(RhListaProvisionesTO.class, map.get("listaProvisionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteProvisionesComprobanteContable(usuarioEmpresaReporteTO, periodo, numero, tipo, lista);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*CONSOLIDADO INGRESOS TABULADOS*/
    @RequestMapping("/getConsolidadoIngresosTabulado")
    public ResponseEntity getConsolidadoIngresosTabulado(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, parametros.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            RetornoTO respues = empleadoService.getConsolidadoIngresosTabulado(empresa, codigoSector, fechaInicio, fechaFin, usuario);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/exportarReporteConsolidadoIngresosTabulado")
    public @ResponseBody
    String exportarReporteConsolidadoIngresosTabulado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        Object[][] datos = UtilsJSON.jsonToObjeto(Object[][].class, map.get("datos"));
        List<String> columnas = UtilsJSON.jsonToObjeto(List.class, map.get("columnas"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteConsolidadoIngresosTabulado(usuarioEmpresaReporteTO, datos, columnas, sector, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*CONSOLIDADO ROL DE PAGOS*/
    @RequestMapping("/getRhConsolidadoRolesTO")
    public RespuestaWebTO getRhConsolidadoRolesTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        boolean excluirLiquidacion = UtilsJSON.jsonToObjeto(boolean.class, map.get("excluirLiquidacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaConsolidadoRolesTO> respues = rolService.getRhConsolidadoRolesTO(empresa, fechaDesde, fechaHasta, sector, empCategoria, empId, excluirLiquidacion);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoRoles")
    public @ResponseBody
    String generarReporteConsolidadoRoles(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsolidadoRoles.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreEmpleado"));
        boolean excluirLiquidacion = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("excluirLiquidacion"));
        List<RhListaConsolidadoRolesTO> listado = UtilsJSON.jsonToList(RhListaConsolidadoRolesTO.class, parametros.get("listaConsolidadoRolesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoRoles(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, empCategoria, nombreEmpleado, listado, excluirLiquidacion);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConsolidadoRoles")
    public @ResponseBody
    String exportarReporteConsolidadoRoles(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("nombreEmpleado"));
        boolean excluirLiquidacion = UtilsJSON.jsonToObjeto(boolean.class, map.get("excluirLiquidacion"));
        List<RhListaConsolidadoRolesTO> listado = UtilsJSON.jsonToList(RhListaConsolidadoRolesTO.class, map.get("listaConsolidadoRolesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteConsolidadoRoles(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, empCategoria, nombreEmpleado, listado, excluirLiquidacion);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*SALDO CONSOLIDADO ANTICIPO Y PRESTAMO*/
    @RequestMapping("/getRhSaldoConsolidadoAnticiposPrestamosTO")
    public RespuestaWebTO getRhSaldoConsolidadoAnticiposPrestamosTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaSaldoConsolidadoAnticiposPrestamosTO> respues = prestamoService.getRhSaldoConsolidadoAnticiposPrestamosTO(empresa, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoAnticiposPrestamos")
    public @ResponseBody
    String generarReporteSaldoConsolidadoAnticiposPrestamos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSaldoConsolidadoAnticiposPrestamos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado = UtilsJSON.jsonToList(RhListaSaldoConsolidadoAnticiposPrestamosTO.class, parametros.get("listaSaldoConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteSaldoConsolidadoAnticiposPrestamos(usuarioEmpresaReporteTO, fechaHasta, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSaldoConsolidadoAnticiposPrestamos")
    public @ResponseBody
    String exportarReporteSaldoConsolidadoAnticiposPrestamos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado = UtilsJSON.jsonToList(RhListaSaldoConsolidadoAnticiposPrestamosTO.class, map.get("listaSaldoConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteSaldoConsolidadoAnticiposPrestamos(usuarioEmpresaReporteTO, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSaldoConsolidadoAnticipos")
    public @ResponseBody
    String exportarReporteSaldoConsolidadoAnticipos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado = UtilsJSON.jsonToList(RhListaSaldoConsolidadoAnticiposPrestamosTO.class, map.get("listaSaldoConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteSaldoConsolidadoAnticipos(usuarioEmpresaReporteTO, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSaldoConsolidadoPrestamos")
    public @ResponseBody
    String exportarReporteSaldoConsolidadoPrestamos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado = UtilsJSON.jsonToList(RhListaSaldoConsolidadoAnticiposPrestamosTO.class, map.get("listaSaldoConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteSaldoConsolidadoPrestamos(usuarioEmpresaReporteTO, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*SALDO INDIVIDUAL DE ANTICIPOS,SALDO INDIVIDUAL DE PRESTAMOS Y SALDO INDIVIDUAL DE ANTICIPOS Y PRESTAMOS */
    @RequestMapping("/getRhSaldoIndividualAnticiposPrestamosTO")
    public RespuestaWebTO getRhSaldoIndividualAnticiposPrestamosTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaSaldoIndividualAnticiposPrestamosTO> respues = prestamoService.getRhSaldoIndividualAnticiposPrestamosTO(empresa, fechaDesde, fechaHasta, empId, tipo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteSaldoIndividualAnticiposPrestamosTO")
    public @ResponseBody
    String exportarReporteSaldoIndividualAnticiposPrestamosTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        List<RhListaSaldoIndividualAnticiposPrestamosTO> listado = UtilsJSON.jsonToList(RhListaSaldoIndividualAnticiposPrestamosTO.class, map.get("listaRhListaSaldoIndividualAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteSaldosIndividualAnticiposPrestamos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, tituloReporte, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSaldoIndividualAnticiposPrestamos")
    public @ResponseBody
    String generarReporteSaldoIndividualAnticiposPrestamos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSaldoIndividualAnticiposPrestamos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empId = UtilsJSON.jsonToObjeto(String.class, parametros.get("empId"));
        List<RhListaSaldoIndividualAnticiposPrestamosTO> listado = UtilsJSON.jsonToList(RhListaSaldoIndividualAnticiposPrestamosTO.class, parametros.get("listaSaldoIndividualAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteSaldoIndividualAnticiposPrestamos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, empId, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //consultas vacaciones
    @RequestMapping("/getRhDetalleVacacionesPagadasGozadasTO")
    public RespuestaWebTO getRhDetalleVacacionesPagadasGozadasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhDetalleVacionesPagadasGozadasTO> respues = vacacionesService.getRhDetalleVacacionesPagadasGozadasTO(empresa, empId, sector, fechaDesde, fechaHasta, tipo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteRhDetalleVacacionesPagadasGozadas")
    public @ResponseBody
    String exportarReporteRhDetalleVacacionesPagadasGozadas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        List<RhDetalleVacionesPagadasGozadasTO> listado = UtilsJSON.jsonToList(RhDetalleVacionesPagadasGozadasTO.class, map.get("rhDetalleVacionesPagadasGozadasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarExcelDetalleVacacionesPagadasGozadas(usuarioEmpresaReporteTO, tipo, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListaDetalleVacaciones")
    public @ResponseBody
    String generarReporteListaDetalleVacaciones(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte;
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String empSector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        List<RhDetalleVacionesPagadasGozadasTO> listaDetalleVacacionesPagadasGozadasTO = UtilsJSON.jsonToList(RhDetalleVacionesPagadasGozadasTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (tipo.equals("P")) {
                nombreReporte = "reportListaDetalleVacacionesPagadas.jrxml";
            } else {
                nombreReporte = "reportListaDetalleVacacionesGozadas.jrxml";
            }
            respuesta = reporteRrhhService.generarReporteListaDetalleVacaciones(usuarioEmpresaReporteTO, desde, hasta,
                    empSector, listaDetalleVacacionesPagadasGozadasTO, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //cuadro de provisiones por empleado
    @RequestMapping("/listarProvisionesPorEmpleado")
    public RespuestaWebTO listarProvisionesPorEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        boolean mostrarSaldos = UtilsJSON.jsonToObjeto(boolean.class, map.get("mostrarSaldos"));
        String trabajador = UtilsJSON.jsonToObjeto(String.class, map.get("trabajador"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhProvisionDetalladoTO> respues = empleadoService.listarProvisionesPorEmpleado(empresa, codigoSector, fechaInicio, fechaFin, mostrarSaldos, trabajador);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteProvisionDetallado")
    public @ResponseBody
    String exportarReporteProvisionDetallado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhProvisionDetalladoTO> datos = UtilsJSON.jsonToList(RhProvisionDetalladoTO.class, map.get("datos"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String titulo = UtilsJSON.jsonToObjeto(String.class, map.get("titulo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteProvisionDetallado(usuarioEmpresaReporteTO, sector, titulo, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //provicion por fechas
    @RequestMapping("/getProvisionPorFechas")
    public RespuestaWebTO getProvisionPorFechas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String agrupacion = UtilsJSON.jsonToObjeto(String.class, map.get("agrupacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RetornoTO respues = empleadoService.getProvisionPorFechas(empresa, codigoSector, fechaInicio, fechaFin, agrupacion);
            if (respues != null && respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteProvisionPorFechas")
    public @ResponseBody
    String exportarReporteProvisionPorFechas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String titulo = UtilsJSON.jsonToObjeto(String.class, map.get("titulo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteProvisionPorFechas(usuarioEmpresaReporteTO, sector, titulo, columnas, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Transacciones*/
    // prestamos
    @RequestMapping("/extraerDatosPaPrestamos")
    public RespuestaWebTO extraerDatosPaPrestamos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = prestamoService.obtenerDatosParaCrudPrestamos(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para prestamos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarRhPrestamo")
    public ResponseEntity insertarModificarRhPrestamo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        RhPrestamo rhPrestamo = UtilsJSON.jsonToObjeto(RhPrestamo.class, map.get("rhPrestamo"));
        RhComboFormaPagoTO rhComboFormaPagoTO = UtilsJSON.jsonToObjeto(RhComboFormaPagoTO.class, map.get("rhComboFormaPagoTO"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        Date fechaHasta = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHasta"));
        String codigoUnico = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUnico"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO respuesta;
        try {
            respuesta = prestamoService.postIngresarPrestamo(rhPrestamo, rhComboFormaPagoTO, fechaHasta, listadoImagenes, codigoUnico, sisInfoTO);
            if (respuesta != null) {
                if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                    respuesta.setMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(respuesta.getMensaje());
                    resp.setExtraInfo(respuesta.getMap());
                } else {
                    resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/modificarRhPrestamo")
    public RespuestaWebTO modificarRhPrestamo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        RhPrestamo rhPrestamo = UtilsJSON.jsonToObjeto(RhPrestamo.class, map.get("rhPrestamo"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean pendiente = conContable.getConPendiente();
            MensajeTO mensajeTO = prestamoService.insertarModificarRhPrestamo(conContable, rhPrestamo, listadoImagenes, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!pendiente) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado anticipo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprobantePrestamo")
    public @ResponseBody
    String generarReporteComprobantePrestamo(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportComprobantePrestamo.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        RhPrestamo rhPrestamo = UtilsJSON.jsonToObjeto(RhPrestamo.class, map.get("rhPrestamo"));
        RhComboFormaPagoTO rhComboFormaPagoTO = UtilsJSON.jsonToObjeto(RhComboFormaPagoTO.class, map.get("rhComboFormaPagoTO"));
        Date fechaHasta = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHasta"));
        ConContable con = UtilsJSON.jsonToObjeto(ConContable.class, map.get("contable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        List<ReporteAnticipoPrestamoXIIISueldo> lista;
        try {
            lista = prestamoService.postGenerarReporteComprobantePrestamo(con, usuarioEmpresaReporteTO, rhPrestamo, rhComboFormaPagoTO, fechaHasta);
            respuesta = reporteRrhhService.generarReportePrestamoVista(usuarioEmpresaReporteTO, lista);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobantePrestamoMatricial")
    public RespuestaWebTO generarReporteComprobantePrestamoMatricial(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        RhPrestamo rhPrestamo = UtilsJSON.jsonToObjeto(RhPrestamo.class, map.get("rhPrestamo"));
        RhComboFormaPagoTO rhComboFormaPagoTO = UtilsJSON.jsonToObjeto(RhComboFormaPagoTO.class, map.get("rhComboFormaPagoTO"));
        Date fechaHasta = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHasta"));
        ConContable con = UtilsJSON.jsonToObjeto(ConContable.class, map.get("contable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        List<ReporteAnticipoPrestamoXIIISueldo> lista;
        try {
            lista = prestamoService.postGenerarReporteComprobantePrestamo(con, usuarioEmpresaReporteTO, rhPrestamo, rhComboFormaPagoTO, fechaHasta);
            respuesta = reporteRrhhService.generarReportePrestamoVista(usuarioEmpresaReporteTO, lista);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Horas extras*/
    @RequestMapping("/obtenerDatosParaCrudHorasExtras")
    public RespuestaWebTO obtenerDatosParaCrudHorasExtras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = horasExtrasService.obtenerDatosParaCrudHorasExtras(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos horas extras.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaRhHorasExtras")
    public RespuestaWebTO getListaRhHorasExtras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        boolean rol = UtilsJSON.jsonToObjeto(boolean.class, map.get("rol"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaEmpleadoLoteTO> respues = empleadoService.getListaEmpleadoLote(empresa, categoria, sector, fechaHasta, motivo, rol);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhHorasExtras")
    public RespuestaWebTO insertarRhHorasExtras(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Date fechaHasta = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHasta"));
        List<RhHorasExtras> lista = UtilsJSON.jsonToList(RhHorasExtras.class, map.get("horasExtras"));
        String codigoUnico = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUnico"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respuesta = horasExtrasService.insertarRhHorasExtras(fechaHasta, empresa, lista, observaciones, codigoUnico, sisInfoTO);
            if (respuesta != null && respuesta.getMensaje() != null) {
                if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                    respuesta.setMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                }
                resp.setOperacionMensaje(respuesta.getMensaje());
                resp.setExtraInfo(respuesta.getMap());
            } else {
                resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhHEIndividual")
    public RespuestaWebTO insertarRhHEIndividual(@RequestBody String json) {
        RespuestaWebTO respFinal = new RespuestaWebTO();
        respFinal.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observacionesContable = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        List<RhHorasExtras> horasExtras = UtilsJSON.jsonToList(RhHorasExtras.class, map.get("horasExtras"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<String> codigos = UtilsJSON.jsonToList(String.class, map.get("codigos"));
        Date fechaHasta = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> listadoRespuestas = new ArrayList<>();
            int contador = 0;
            for (RhHorasExtras he : horasExtras) {
                RespuestaWebTO resp = new RespuestaWebTO();
                List<RhHorasExtras> listaDeUno = new ArrayList<>();
                listaDeUno.add(he);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                try {
                    String observacionTemporal = observacionesContable;
                    if (observacionesContable == null || observacionesContable.equals("")) {
                        observacionTemporal = he.getHeObservacion();
                    }
                    MensajeTO mensajeTO = horasExtrasService.insertarRhHorasExtras(fechaHasta, empresa, listaDeUno, observacionTemporal, codigos.get(contador), sisInfoTO);
                    contador++;
                    if (mensajeTO != null && mensajeTO.getMensaje() != null) {
                        if (mensajeTO.getMensaje().charAt(0) == 'T') {
                            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                            mensajeTO.getMap().put("empleado", he.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                        }
                        resp.setExtraInfo(mensajeTO.getMap());
                        resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                    } else {
                        resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado el registro de horas extras.");
                    }
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    resp.setOperacionMensaje(e.getMessage());
                }
                listadoRespuestas.add(resp);
            }
            respFinal.setExtraInfo(listadoRespuestas);
        } catch (GeneralException e) {
            respFinal.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respFinal.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return respFinal;
    }

    @RequestMapping("/modificarRhHorasExtras")
    public RespuestaWebTO modificarRhHorasExtras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhHorasExtras> listaRhBono = UtilsJSON.jsonToList(RhHorasExtras.class, map.get("horasExtras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean pendiente = conContable.getConPendiente();
            MensajeTO respuesta = horasExtrasService.insertarModificarRhHorasExtras(conContable, listaRhBono, sisInfoTO);
            if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                if (!pendiente) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                }
                respuesta.setMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.getMensaje());
                resp.setExtraInfo(respuesta.getMap());
            } else {
                resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Bonos*/
    @RequestMapping("/getListaRhBonos")
    public RespuestaWebTO getListaRhBonos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        List<PrdListaPiscinaTO> listadoPiscinas = UtilsJSON.jsonToList(PrdListaPiscinaTO.class, map.get("listadoPiscinas"));
        boolean rol = UtilsJSON.jsonToObjeto(boolean.class, map.get("rol"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaBonosLoteTO> respues = empleadoService.getListaRhBonos(empresa, categoria, sector, fechaHasta, motivo, rol, listadoPiscinas);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhBono")
    public RespuestaWebTO insertarRhBono(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Date fechaHasta = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHasta"));
        List<RhListaBonosLoteTO> lista = UtilsJSON.jsonToList(RhListaBonosLoteTO.class, map.get("listaRhListaBonosLoteTO"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        String codigoUnico = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUnico"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respuesta = bonoService.insertarRhBono(fechaHasta, empresa, lista, observaciones, listadoImagenes, codigoUnico, sisInfoTO);
            if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                respuesta.setMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.getMensaje());
                resp.setExtraInfo(respuesta.getMap());
            } else {
                resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhBonoIndividual")
    public RespuestaWebTO insertarRhBonoIndividual(@RequestBody String json) {
        RespuestaWebTO respFinal = new RespuestaWebTO();
        respFinal.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observacionesContable = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        List<RhListaBonosLoteTO> listaRhBono = UtilsJSON.jsonToList(RhListaBonosLoteTO.class, map.get("listaRhListaBonosLoteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Date fechaHasta = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHasta"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        List<String> codigos = UtilsJSON.jsonToList(String.class, map.get("codigos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> listadoRespuestas = new ArrayList<>();
            int contador = 0;
            for (RhListaBonosLoteTO bono : listaRhBono) {
                RespuestaWebTO resp = new RespuestaWebTO();
                List<RhListaBonosLoteTO> listaDeUno = new ArrayList<>();
                listaDeUno.add(bono);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                try {
                    String observacionTemporal = observacionesContable;
                    if (observacionesContable == null || observacionesContable.equals("")) {
                        observacionTemporal = bono.getObservacion();
                    }
                    MensajeTO mensajeTO = bonoService.insertarRhBono(fechaHasta, empresa, listaDeUno, observacionTemporal, listadoImagenes, codigos.get(contador), sisInfoTO);
                    contador++;
                    if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        mensajeTO.getMap().put("empleado", bono.getRhListaEmpleadoLoteTO().getPrId());
                        resp.setExtraInfo(mensajeTO.getMap());
                        resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                    } else {
                        resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado bono.");
                    }
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    resp.setOperacionMensaje(e.getMessage());
                }
                listadoRespuestas.add(resp);
            }
            respFinal.setExtraInfo(listadoRespuestas);
        } catch (GeneralException e) {
            respFinal.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respFinal.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return respFinal;
    }

    @RequestMapping("/modificarRhBono")
    public RespuestaWebTO modificarRhBono(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhListaBonosLoteTO> listaRhBono = UtilsJSON.jsonToList(RhListaBonosLoteTO.class, map.get("listaRhListaBonosLoteTO"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean pendiente = conContable.getConPendiente();
            MensajeTO respuesta = bonoService.modificarRhBono(conContable, listaRhBono, listadoImagenes, sisInfoTO);
            if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                if (!pendiente) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                }
                respuesta.setMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.getMensaje());
                resp.setExtraInfo(respuesta.getMap());
            } else {
                resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*xiii sueldo*/
    @RequestMapping("/getListaRhXiiiSueldoXiiiSueldoCalcular")
    public RespuestaWebTO getListaRhXiiiSueldoXiiiSueldoCalcular(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean formaPagoSeleccionado = UtilsJSON.jsonToObjeto(boolean.class, map.get("formaPagoSeleccionado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhXiiiSueldoXiiiSueldoCalcular> respues = xiiiSueldoService.getListaRhXiiiSueldoXiiiSueldoCalcular(empresa, sector, desde, hasta, formaPagoSeleccionado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhXiiiSueldoXiiiSueldoCalcular")
    public RespuestaWebTO insertarRhXiiiSueldoXiiiSueldoCalcular(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Boolean aceptado = UtilsJSON.jsonToObjeto(boolean.class, map.get("aceptado"));
        Date fechaMaximo = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaMaximo"));
        RhXiiiSueldoPeriodoTO periodoSelccionado = UtilsJSON.jsonToObjeto(RhXiiiSueldoPeriodoTO.class, map.get("RhXiiiSueldoPeriodoTO"));
        List<RhXiiiSueldoXiiiSueldoCalcular> lista = UtilsJSON.jsonToList(RhXiiiSueldoXiiiSueldoCalcular.class, map.get("listaRhXiiiSueldoXiiiSueldoCalcular"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respuesta = xiiiSueldoService.insertarRhXiiiSueldoXiiiSueldoCalcular(lista, observaciones, aceptado, empresa, fechaMaximo, periodoSelccionado, listadoImagenes, sisInfoTO);
            if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                respuesta.setMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.getMensaje());
                resp.setExtraInfo(respuesta.getMap());
            } else {
                resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhXiiiSueldoXiiiSueldoCalcularIndividual")
    public RespuestaWebTO insertarRhXiiiSueldoXiiiSueldoCalcularIndividual(@RequestBody String json) {
        RespuestaWebTO respFinal = new RespuestaWebTO();
        respFinal.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        RhXiiiSueldoPeriodoTO periodoSelccionado = UtilsJSON.jsonToObjeto(RhXiiiSueldoPeriodoTO.class, map.get("RhXiiiSueldoPeriodoTO"));
        List<RhXiiiSueldoXiiiSueldoCalcular> lista = UtilsJSON.jsonToList(RhXiiiSueldoXiiiSueldoCalcular.class, map.get("listaRhXiiiSueldoXiiiSueldoCalcular"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Boolean aceptado = UtilsJSON.jsonToObjeto(boolean.class, map.get("aceptado"));
        Date fechaMaximo = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaMaximo"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> listadoRespuestas = new ArrayList<>();
            for (RhXiiiSueldoXiiiSueldoCalcular xiii : lista) {
                RespuestaWebTO resp = new RespuestaWebTO();
                List<RhXiiiSueldoXiiiSueldoCalcular> listaDeUno = new ArrayList<>();
                listaDeUno.add(xiii);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                try {
                    MensajeTO mensajeTO = xiiiSueldoService.insertarRhXiiiSueldoXiiiSueldoCalcular(listaDeUno, observaciones, aceptado, empresa, fechaMaximo, periodoSelccionado, listadoImagenes, sisInfoTO);
                    if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        mensajeTO.getMap().put("empleado", xiii.getRhXiiiSueldo().getRhEmpleado().getRhEmpleadoPK().getEmpId());
                        resp.setExtraInfo(mensajeTO.getMap());
                        resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                    } else {
                        resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado xiii sueldo.");
                    }
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    resp.setOperacionMensaje(e.getMessage());
                }
                listadoRespuestas.add(resp);
            }
            respFinal.setExtraInfo(listadoRespuestas);
        } catch (GeneralException e) {
            respFinal.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respFinal.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return respFinal;
    }

    @RequestMapping("/modificarRhXiiiSueldo")
    public RespuestaWebTO modificarRhXiiiSueldo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhXiiiSueldoXiiiSueldoCalcular> lista = UtilsJSON.jsonToList(RhXiiiSueldoXiiiSueldoCalcular.class, map.get("listaRhXiiiSueldoXiiiSueldoCalcular"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Boolean aceptado = UtilsJSON.jsonToObjeto(boolean.class, map.get("aceptado"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean pendiente = conContable.getConPendiente();
            MensajeTO respuesta = xiiiSueldoService.modificarRhXiiiSueldoXiiiSueldoCalcular(conContable, lista, aceptado, empresa, sisInfoTO);
            if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                if (!pendiente) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                }
                contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                respuesta.setMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.getMensaje());
                resp.setExtraInfo(respuesta.getMap());
            } else {
                resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/xiiiSueldoVerificarMayor")
    public RespuestaWebTO xiiiSueldoVerificarMayor(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<RhXiiiSueldoXiiiSueldoCalcular> lista = UtilsJSON.jsonToList(RhXiiiSueldoXiiiSueldoCalcular.class, map.get("listaRhXiiiSueldoXiiiSueldoCalcular"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = xiiiSueldoService.xiiiSueldoVerificarMayor(lista);
            resp.setOperacionMensaje("");
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhXiiiSueldoXiiiSueldoCalcular")
    public @ResponseBody
    String generarReporteRhXiiiSueldoXiiiSueldoCalcular(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportComprobanteXIIISueldo.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhXiiiSueldoXiiiSueldoCalcular> lista = UtilsJSON.jsonToList(RhXiiiSueldoXiiiSueldoCalcular.class, map.get("listaRhXiiiSueldoXiiiSueldoCalcular"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteRhXiiiSueldoXiiiSueldoCalcular(usuarioEmpresaReporteTO, conContable, lista);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhXiiiSueldoXiiiSueldoCalcularMatricial")
    public RespuestaWebTO generarReporteRhXiiiSueldoXiiiSueldoCalcularMatricial(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhXiiiSueldoXiiiSueldoCalcular> lista = UtilsJSON.jsonToList(RhXiiiSueldoXiiiSueldoCalcular.class, map.get("listaRhXiiiSueldoXiiiSueldoCalcular"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteRhXiiiSueldoXiiiSueldoCalcular(usuarioEmpresaReporteTO, conContable, lista);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhXiiiSueldoXiiiSueldoCalcularIndividual")
    public @ResponseBody
    String generarReporteRhXiiiSueldoXiiiSueldoCalcularIndividual(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportComprobanteXIIISueldo.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> pks = UtilsJSON.jsonToList(ConContablePK.class, map.get("listadoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteRhXiiiSueldoXiiiSueldoCalcularDeVariosContables(usuarioEmpresaReporteTO, nombreReporte, pks);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhXiiiSueldoXiiiSueldoCalcularIndividualMatricial")
    public RespuestaWebTO generarReporteRhXiiiSueldoXiiiSueldoCalcularIndividualMatricial(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> pks = UtilsJSON.jsonToList(ConContablePK.class, map.get("listadoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteRhXiiiSueldoXiiiSueldoCalcularDeVariosContables(usuarioEmpresaReporteTO, "", pks);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteRhXiiiSueldoXiiiSueldoCalcular")
    public @ResponseBody
    String exportarReporteRhXiiiSueldoXiiiSueldoCalcular(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhXiiiSueldoXiiiSueldoCalcular> lista = UtilsJSON.jsonToList(RhXiiiSueldoXiiiSueldoCalcular.class, map.get("listaRhXiiiSueldoXiiiSueldoCalcular"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteRhXiiiSueldoXiiiSueldoCalcular(usuarioEmpresaReporteTO, lista);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Xiv Sueldo*/
    @RequestMapping("/getListaRhXivSueldoXivSueldoCalcular")
    public RespuestaWebTO getListaRhXivSueldoXivSueldoCalcular(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean formaPagoSeleccionado = UtilsJSON.jsonToObjeto(boolean.class, map.get("formaPagoSeleccionado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhXivSueldoXivSueldoCalcular> respues = xivSueldoService.getListaRhXivSueldoXivSueldoCalcular(empresa, sector, desde, hasta, formaPagoSeleccionado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhXivSueldoXivSueldoCalcular")
    public RespuestaWebTO insertarRhXivSueldoXivSueldoCalcular(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Boolean aceptado = UtilsJSON.jsonToObjeto(boolean.class, map.get("aceptado"));
        Date fechaMaximo = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaMaximo"));
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        RhXivSueldoPeriodoTO periodoSelccionado = UtilsJSON.jsonToObjeto(RhXivSueldoPeriodoTO.class, map.get("RhXivSueldoPeriodoTO"));
        List<RhXivSueldoXivSueldoCalcular> lista = UtilsJSON.jsonToList(RhXivSueldoXivSueldoCalcular.class, map.get("listaRhXivSueldoXivSueldoCalcular"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respuesta = xivSueldoService.insertarRhXivSueldoXivSueldoCalcular(conContable, lista, observaciones, aceptado, empresa, fechaMaximo, periodoSelccionado, listadoImagenes, sisInfoTO);
            if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                respuesta.setMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.getMensaje());
                resp.setExtraInfo(respuesta.getMap());
            } else {
                resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhXivSueldoXivSueldoCalcularIndividual")
    public RespuestaWebTO insertarRhXivSueldoXivSueldoCalcularIndividual(@RequestBody String json) {
        RespuestaWebTO respFinal = new RespuestaWebTO();
        respFinal.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        RhXivSueldoPeriodoTO periodoSelccionado = UtilsJSON.jsonToObjeto(RhXivSueldoPeriodoTO.class, map.get("RhXivSueldoPeriodoTO"));
        List<RhXivSueldoXivSueldoCalcular> lista = UtilsJSON.jsonToList(RhXivSueldoXivSueldoCalcular.class, map.get("listaRhXivSueldoXivSueldoCalcular"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Boolean aceptado = UtilsJSON.jsonToObjeto(boolean.class, map.get("aceptado"));
        Date fechaMaximo = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaMaximo"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> listadoRespuestas = new ArrayList<>();
            for (RhXivSueldoXivSueldoCalcular xiv : lista) {
                RespuestaWebTO resp = new RespuestaWebTO();
                List<RhXivSueldoXivSueldoCalcular> listaDeUno = new ArrayList<>();
                listaDeUno.add(xiv);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                try {
                    MensajeTO mensajeTO = xivSueldoService.insertarRhXivSueldoXivSueldoCalcular(conContable, listaDeUno, observaciones, aceptado, empresa, fechaMaximo, periodoSelccionado, listadoImagenes, sisInfoTO);
                    if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        mensajeTO.getMap().put("empleado", xiv.getRhXivSueldo().getRhEmpleado().getRhEmpleadoPK().getEmpId());
                        resp.setExtraInfo(mensajeTO.getMap());
                        resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                    } else {
                        resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado xiv sueldo.");
                    }
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    resp.setOperacionMensaje(e.getMessage());
                }
                listadoRespuestas.add(resp);
            }
            respFinal.setExtraInfo(listadoRespuestas);
        } catch (GeneralException e) {
            respFinal.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respFinal.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return respFinal;
    }

    @RequestMapping("/modificarRhXivSueldo")
    public RespuestaWebTO modificarRhXivSueldo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhXivSueldoXivSueldoCalcular> lista = UtilsJSON.jsonToList(RhXivSueldoXivSueldoCalcular.class, map.get("listaRhXivSueldoXivSueldoCalcular"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Boolean aceptado = UtilsJSON.jsonToObjeto(boolean.class, map.get("aceptado"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean pendiente = conContable.getConPendiente();
            MensajeTO respuesta = xivSueldoService.modificarRhXivSueldoXivSueldoCalcular(conContable, lista, aceptado, empresa, sisInfoTO);
            if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                if (!pendiente) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                }
                contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                respuesta.setMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.getMensaje());
                resp.setExtraInfo(respuesta.getMap());
            } else {
                resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhXivSueldoXivSueldoCalcular")
    public @ResponseBody
    String generarReporteRhXivSueldoXivSueldoCalcular(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportComprobanteXIVSueldo.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhXivSueldoXivSueldoCalcular> lista = UtilsJSON.jsonToList(RhXivSueldoXivSueldoCalcular.class, map.get("listaRhXivSueldoXivSueldoCalcular"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteRhXivSueldoXivSueldoCalcular(usuarioEmpresaReporteTO, conContable, lista);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhXivSueldoXivSueldoCalcularMatricial")
    public RespuestaWebTO generarReporteRhXivSueldoXivSueldoCalcularMatricial(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhXivSueldoXivSueldoCalcular> lista = UtilsJSON.jsonToList(RhXivSueldoXivSueldoCalcular.class, map.get("listaRhXivSueldoXivSueldoCalcular"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteRhXivSueldoXivSueldoCalcular(usuarioEmpresaReporteTO, conContable, lista);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhXivSueldoXivSueldoCalcularIndividual")
    public @ResponseBody
    String generarReporteRhXivSueldoXivSueldoCalcularIndividual(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportComprobanteXIVSueldo.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> pks = UtilsJSON.jsonToList(ConContablePK.class, map.get("listadoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteRhXivSueldoXivSueldoCalcularDeVariosContables(usuarioEmpresaReporteTO, nombreReporte, pks);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRhXivSueldoXivSueldoCalcularIndividualMatricial")
    public RespuestaWebTO generarReporteRhXivSueldoXivSueldoCalcularIndividualMatricial(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> pks = UtilsJSON.jsonToList(ConContablePK.class, map.get("listadoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteRhXivSueldoXivSueldoCalcularDeVariosContables(usuarioEmpresaReporteTO, "", pks);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteRhXivSueldoXivSueldoCalcular")
    public @ResponseBody
    String exportarReporteRhXivSueldoXivSueldoCalcular(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhXivSueldoXivSueldoCalcular> lista = UtilsJSON.jsonToList(RhXivSueldoXivSueldoCalcular.class, map.get("listaRhXivSueldoXivSueldoCalcular"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteRhXivSueldoXivSueldoCalcular(usuarioEmpresaReporteTO, lista);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Anticipos*/
    @RequestMapping("/obtenerDatosParaCrudAnticipos")
    public RespuestaWebTO obtenerDatosParaCrudAnticipos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = anticipoService.obtenerDatosParaCrudAnticipos(map);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para trabajar anticipos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhAnticipo")
    public RespuestaWebTO insertarRhAnticipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observacionesContable = UtilsJSON.jsonToObjeto(String.class, map.get("observacionesContable"));
        List<RhAnticipo> listaRhAnticipo = UtilsJSON.jsonToList(RhAnticipo.class, map.get("listaRhAnticipo"));
        String codigoUnico = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUnico"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = anticipoService.insertarRhAnticipo(observacionesContable, listaRhAnticipo, fechaHasta, listadoImagenes, codigoUnico, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado anticipo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhAnticipoIndividual")
    public RespuestaWebTO insertarRhAnticipoIndividual(@RequestBody String json) {
        RespuestaWebTO respFinal = new RespuestaWebTO();
        respFinal.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observacionesContable = UtilsJSON.jsonToObjeto(String.class, map.get("observacionesContable"));
        List<RhAnticipo> listaRhAnticipo = UtilsJSON.jsonToList(RhAnticipo.class, map.get("listaRhAnticipo"));
        List<String> codigos = UtilsJSON.jsonToList(String.class, map.get("codigos"));
        String perHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> listadoRespuestas = new ArrayList<>();
            int contador = 0;
            for (RhAnticipo anticipo : listaRhAnticipo) {
                RespuestaWebTO resp = new RespuestaWebTO();
                List<RhAnticipo> listaDeUno = new ArrayList<>();
                listaDeUno.add(anticipo);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                try {
                    String observacionTemporal = observacionesContable;
                    if (observacionesContable == null || observacionesContable.equals("")) {
                        observacionTemporal = anticipo.getAntObservaciones();
                    }
                    MensajeTO mensajeTO = anticipoService.insertarRhAnticipo(observacionTemporal, listaDeUno, perHasta, listadoImagenes, codigos.get(contador), sisInfoTO);
                    contador++;
                    if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        mensajeTO.getMap().put("empleado", anticipo.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                        resp.setExtraInfo(mensajeTO.getMap());
                        resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                    } else {
                        resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado anticipoa a empleado.");
                    }
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    resp.setOperacionMensaje(e.getMessage());
                }
                listadoRespuestas.add(resp);
            }
            respFinal.setExtraInfo(listadoRespuestas);
        } catch (GeneralException e) {
            respFinal.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respFinal.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return respFinal;
    }

    @RequestMapping("/modificarRhAnticipo")
    public RespuestaWebTO modificarRhAnticipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhAnticipo> listaRhAnticipo = UtilsJSON.jsonToList(RhAnticipo.class, map.get("listaRhAnticipo"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO;
        try {
            boolean pendiente = conContable.getConPendiente();
            mensajeTO = anticipoService.insertarModificarRhAnticipo(conContable, listaRhAnticipo, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!pendiente) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                }
                contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha modificado anticipo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprobanteAnticipo")
    public @ResponseBody
    String generarReporteComprobanteAnticipo(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportComprobanteAnticipo.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        ConContable contable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("contable"));
        List<RhAnticipo> listaRhAnticipo = UtilsJSON.jsonToList(RhAnticipo.class, map.get("listaRhAnticipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteAnticipo(usuarioEmpresaReporteTO, contable, listaRhAnticipo, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteAnticipoMatricial")
    public RespuestaWebTO generarReporteComprobanteAnticipoMatricial(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportComprobanteAnticipo.jrxml";
        byte[] respuesta;
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        ConContable contable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("contable"));
        List<RhAnticipo> listaRhAnticipo = UtilsJSON.jsonToList(RhAnticipo.class, map.get("listaRhAnticipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteAnticipo(usuarioEmpresaReporteTO, contable, listaRhAnticipo, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprobanteAnticipoDeVariosContables")
    public @ResponseBody
    String generarReporteComprobanteAnticipoDeVariosContables(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteAnticipo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> pks = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoPK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteAnticipoDeVariosContables(usuarioEmpresaReporteTO, nombreReporte, pks);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteAnticipoDeVariosContablesMatricial")
    public RespuestaWebTO generarReporteComprobanteAnticipoDeVariosContablesMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteAnticipo.jrxml";
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> pks = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoPK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteAnticipoDeVariosContables(usuarioEmpresaReporteTO, nombreReporte, pks);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaEmpleadoLote")
    public RespuestaWebTO getListaEmpleadoLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        boolean rol = UtilsJSON.jsonToObjeto(boolean.class, map.get("rol"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaEmpleadoLoteTO> respues = empleadoService.getListaEmpleadoLote(empresa, categoria, sector, fechaHasta, motivo, rol);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Utilidades*/
    @RequestMapping("/obtenerDatosParaCrudUtilidades")
    public RespuestaWebTO obtenerDatosParaCrudUtilidades(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = utilidadService.obtenerDatosParaCrudUtilidades(map);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para trabajar utilidades.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhUtilidades")
    public RespuestaWebTO insertarRhUtilidades(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observacionesContable = UtilsJSON.jsonToObjeto(String.class, map.get("observacionesContable"));
        List<RhUtilidades> listaRhUtilidades = UtilsJSON.jsonToList(RhUtilidades.class, map.get("listaRhUtilidades"));
        String fechaMaximaPago = UtilsJSON.jsonToObjeto(String.class, map.get("fechaMaximaPago"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = utilidadService.insertarRhUtilidades(observacionesContable, listaRhUtilidades, fechaMaximaPago, listadoImagenes, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado utilidades.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhUtilidadesIndividual")
    public RespuestaWebTO insertarRhUtilidadesIndividual(@RequestBody String json) {
        RespuestaWebTO respFinal = new RespuestaWebTO();
        respFinal.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observacionesContable = UtilsJSON.jsonToObjeto(String.class, map.get("observacionesContable"));
        List<RhUtilidades> listaRhUtilidades = UtilsJSON.jsonToList(RhUtilidades.class, map.get("listaRhUtilidades"));
        String fechaMaximaPago = UtilsJSON.jsonToObjeto(String.class, map.get("fechaMaximaPago"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> listadoRespuestas = new ArrayList<>();
            for (RhUtilidades utilidad : listaRhUtilidades) {
                RespuestaWebTO resp = new RespuestaWebTO();
                List<RhUtilidades> listaDeUno = new ArrayList<>();
                listaDeUno.add(utilidad);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                try {
                    String observacionTemporal = observacionesContable;
                    if (observacionesContable == null || observacionesContable.equals("")) {
                        observacionTemporal = utilidad.getUtiObservaciones();
                    }
                    MensajeTO mensajeTO = utilidadService.insertarRhUtilidades(observacionTemporal, listaDeUno, fechaMaximaPago, listadoImagenes, sisInfoTO);
                    if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        mensajeTO.getMap().put("empleado", utilidad.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                        resp.setExtraInfo(mensajeTO.getMap());
                        resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                    } else {
                        resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado utilidades.");
                    }
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    resp.setOperacionMensaje(e.getMessage());
                }
                listadoRespuestas.add(resp);
            }
            respFinal.setExtraInfo(listadoRespuestas);
        } catch (GeneralException e) {
            respFinal.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respFinal.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return respFinal;
    }

    @RequestMapping("/modificarRhUtilidades")
    public RespuestaWebTO modificarRhUtilidades(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhUtilidades> listaRhUtilidades = UtilsJSON.jsonToList(RhUtilidades.class, map.get("listaRhUtilidades"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean pendiente = conContable.getConPendiente();
            MensajeTO mensajeTO = utilidadService.insertarModificarRhUtilidades(conContable, listaRhUtilidades, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!pendiente) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                }
                contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha modificado utilidades.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarRhUtilidades")
    public @ResponseBody
    String exportarRhUtilidades(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhUtilidades> listaRhUtilidades = UtilsJSON.jsonToList(RhUtilidades.class, map.get("listaRhUtilidades"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarRhUtilidades(usuarioEmpresaReporteTO, listaRhUtilidades);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteUtilidades")
    public @ResponseBody
    String generarReporteComprobanteUtilidades(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportComprobanteUtilidades.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        ConContable contable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("contable"));
        List<RhUtilidades> listaRhUtilidades = UtilsJSON.jsonToList(RhUtilidades.class, map.get("listaRhUtilidades"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteUtilidad(usuarioEmpresaReporteTO, contable, listaRhUtilidades, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteUtilidadesMatricial")
    public RespuestaWebTO generarReporteComprobanteUtilidadesMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteUtilidades.jrxml";
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        ConContable contable = UtilsJSON.jsonToObjeto(ConContable.class, parametros.get("contable"));
        List<RhUtilidades> listaRhUtilidades = UtilsJSON.jsonToList(RhUtilidades.class, parametros.get("listaRhUtilidades"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteUtilidad(usuarioEmpresaReporteTO, contable, listaRhUtilidades, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Roles de pago*/
    @RequestMapping("/obtenerDatosParaRolesDePago")
    public RespuestaWebTO obtenerDatosParaRolesDePago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = rolService.obtenerDatosParaRolesDePago(map);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para trabajar roles de pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhRol")
    public RespuestaWebTO insertarRhRol(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observacionesContable = UtilsJSON.jsonToObjeto(String.class, map.get("observacionesContable"));
        List<RhRol> listaRhRol = UtilsJSON.jsonToList(RhRol.class, map.get("listaRhRol"));
        List<RhVacacionesGozadas> gozadas = UtilsJSON.jsonToList(RhVacacionesGozadas.class, map.get("gozadas"));
        String perHasta = UtilsJSON.jsonToObjeto(String.class, map.get("perHasta"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = rolService.insertarRhRol(observacionesContable, listaRhRol, perHasta, listadoImagenes, gozadas, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado rol de pago.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhRolIndividual")
    public RespuestaWebTO insertarRhRolIndividual(@RequestBody String json) {
        RespuestaWebTO respFinal = new RespuestaWebTO();
        respFinal.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observacionesContable = UtilsJSON.jsonToObjeto(String.class, map.get("observacionesContable"));
        List<RhRol> listaRhRol = UtilsJSON.jsonToList(RhRol.class, map.get("listaRhRol"));
        List<RhVacacionesGozadas> gozadas = UtilsJSON.jsonToList(RhVacacionesGozadas.class, map.get("gozadas"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> listadoRespuestas = new ArrayList<>();
            for (RhRol rol : listaRhRol) {
                RespuestaWebTO resp = new RespuestaWebTO();
                List<RhRol> listaDeUno = new ArrayList<>();
                listaDeUno.add(rol);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                try {
                    String observacionTemporal = observacionesContable;
                    if (observacionesContable == null || observacionesContable.equals("")) {
                        observacionTemporal = rol.getRolObservaciones();
                    }
                    List<RhVacacionesGozadas> gozadasPorRol = new ArrayList<>();
                    if (gozadas != null && !gozadas.isEmpty()) {
                        gozadasPorRol = gozadas
                                .stream()
                                .filter(x -> x.getRhEmpleado().getRhEmpleadoPK().getEmpId().equals(rol.getRhEmpleado().getRhEmpleadoPK().getEmpId()))
                                .collect(Collectors.toList());
                    }
                    MensajeTO mensajeTO = rolService.insertarRhRol(observacionTemporal, listaDeUno, UtilsDate.fechaFormatoString(rol.getRolHasta(), "dd-MM-yyyy"), listadoImagenes, gozadasPorRol, sisInfoTO);
                    if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        mensajeTO.getMap().put("empleado", rol.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                        resp.setExtraInfo(mensajeTO.getMap());
                        resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                    } else {
                        resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado rol de pago.");
                    }
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    resp.setOperacionMensaje(e.getMessage());
                }
                listadoRespuestas.add(resp);
            }
            respFinal.setExtraInfo(listadoRespuestas);
        } catch (GeneralException e) {
            respFinal.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respFinal.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return respFinal;
    }

    @RequestMapping("/modificarRhRol")
    public RespuestaWebTO modificarRhRol(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<RhRol> listaRhRol = UtilsJSON.jsonToList(RhRol.class, map.get("listaRhRol"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean pendiente = conContable.getConPendiente();
            MensajeTO mensajeTO = rolService.modificarRhRol(listaRhRol, conContable, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!pendiente) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                }
                contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha modificado rol de pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprobanteRol")
    public @ResponseBody
    String generarReporteComprobanteRol(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteRol.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        ConContablePK contablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("contablePK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteRol(usuarioEmpresaReporteTO, nombreReporte, contablePK);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteRolMatricial")
    public RespuestaWebTO generarReporteComprobanteRolMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteRol.jrxml";
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        ConContablePK contablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("contablePK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteRol(usuarioEmpresaReporteTO, nombreReporte, contablePK);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprobanteRolDeVariosContables")
    public @ResponseBody
    String generarReporteComprobanteRolDeVariosContables(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteRol.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> pks = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoPK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteRolDeVarioContables(usuarioEmpresaReporteTO, nombreReporte, pks);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteRolDeVariosContablesMatricial")
    public RespuestaWebTO generarReporteComprobanteRolDeVariosContablesMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteRol.jrxml";
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> pks = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoPK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteRolDeVarioContables(usuarioEmpresaReporteTO, nombreReporte, pks);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprobanteRolLote")
    public @ResponseBody
    String generarReporteComprobanteRolLote(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteRol.jrxml";
        byte[] respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleRolesTO> listaRhListaDetalleRolesTO = UtilsJSON.jsonToList(RhListaDetalleRolesTO.class, parametros.get("listaRhListaDetalleRolesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteRolLote(usuarioEmpresaReporteTO, nombreReporte, empresa, listaRhListaDetalleRolesTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //contable RRHH
    @RequestMapping("/obtenerDatosParaMayorizarContableRRHH")
    public RespuestaWebTO obtenerDatosParaMayorizarContableRRHH(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerDatosParaMayorizarContableRRHH(map);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para trabajar contables de RRHH.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getComboFormaPagoBeneficioSocial")
    public RespuestaWebTO getComboFormaPagoBeneficioSocial(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhComboFormaPagoBeneficioSocialTO> respues = formaPagoBeneficioSocialService.getComboFormaPagoBeneficioSocialTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Liquidacion
    @RequestMapping("/insertarRhLiquidacion")
    public RespuestaWebTO insertarRhLiquidacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhRol rhRol = UtilsJSON.jsonToObjeto(RhRol.class, map.get("rhRol"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = rolService.insertarRhLiquidacion(rhRol, listadoImagenes, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                mensajeTO.setMensaje(mensajeTO.getMensaje().substring(1));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje());
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado la liquidación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    // transacciones provisiones
    @RequestMapping("/extraerDatosConsultaProvisiones")
    public RespuestaWebTO extraerDatosConsultaProvisiones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = rolService.obtenerDatosParaConsultaProvisiones(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getRhListaProvisionesTO")
    public RespuestaWebTO getRhListaProvisionesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        RhProvisionesListadoTransTO provisionesListadoTransTO = UtilsJSON.jsonToObjeto(RhProvisionesListadoTransTO.class, map.get("provisionesListadoTransTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaProvisionesTO> respues = empleadoService.getRhListaProvisionesTO(provisionesListadoTransTO.getEmpresa(), provisionesListadoTransTO.getPeriodo(),
                    provisionesListadoTransTO.getSector(), provisionesListadoTransTO.getEstado());
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarRhProvisiones")
    public RespuestaWebTO insertarModificarRhProvisiones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<RhListaProvisionesTO> listaProvisionesTO = UtilsJSON.jsonToList(RhListaProvisionesTO.class, map.get("listaProvisionesTO"));
        RhProvisionesListadoTransTO provisionesListadoTransTO = UtilsJSON.jsonToObjeto(RhProvisionesListadoTransTO.class, map.get("provisionesListadoTransTO"));
        String contableProvision = UtilsJSON.jsonToObjeto(String.class, map.get("contableProvision"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = rolService.insertarModificarRhProvisionesWeb(provisionesListadoTransTO, contableProvision, listaProvisionesTO, sisInfoTO);
            MensajeTO mensajeTO = (MensajeTO) respuesta.get("mensaje");
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                contableService.mayorizarDesmayorizarSql((ConContablePK) respuesta.get("contablePk"), false, sisInfoTO);
//                List<RhListaProvisionesTO> lista = rolService.obtenerListaImprimirProvision(listaProvisionesTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                respuesta.put("listaProvisionesImprimir", listaProvisionesTO);
                resp.setExtraInfo(respuesta); // respuesta (mensaje. contable y la lista)
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado rol de pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProvisionesComprobanteContableProvision")
    public @ResponseBody
    String generarReporteProvisionesComprobanteContableProvision(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteProvisiones.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaProvisionesTO> listaProvisionesTO = UtilsJSON.jsonToList(RhListaProvisionesTO.class, parametros.get("listaProvisionesTO"));
        ConContablePK contablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("contablePK"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            // itemImprimirProvisiones siempre en angular es null (eso lo pone como parametro)
            respuesta = reporteRrhhService.generarReporteProvisionesComprobanteContable(usuarioEmpresaReporteTO, periodo,
                    "C-PRO", contablePK.getConNumero(), listaProvisionesTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteProvisionesComprobanteContableProvisionMatricial")
    public RespuestaWebTO generarReporteProvisionesComprobanteContableProvisionMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaProvisionesTO> listaProvisionesTO = UtilsJSON.jsonToList(RhListaProvisionesTO.class, parametros.get("listaProvisionesTO"));
        ConContablePK contablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("contablePK"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            // itemImprimirProvisiones siempre en angular es null (eso lo pone como parametro)
            respuesta = reporteRrhhService.generarReporteProvisionesComprobanteContable(usuarioEmpresaReporteTO, periodo, "C-PRO", contablePK.getConNumero(), listaProvisionesTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaLiquidacion")
    public RespuestaWebTO obtenerDatosParaLiquidacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = rolService.obtenerDatosParaLiquidacion(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhLiquidacion")
    public RespuestaWebTO modificarRhLiquidacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        RhRol rhRol = UtilsJSON.jsonToObjeto(RhRol.class, map.get("rhRol"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean pendiente = conContable.getConPendiente();
            rhRol.setEmpActualizar(true);
            MensajeTO mensajeTO = rolService.modificarRhiquidacion(rhRol, conContable, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!pendiente) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                }
                contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                mensajeTO.setMensaje(mensajeTO.getMensaje().substring(1));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje());
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha modificado la liquidación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    // ordenes bancarias
    @RequestMapping("/obtenerDatosParaCrudOrdenesBancarias")
    public RespuestaWebTO obtenerDatosParaCrudOrdenesBancarias(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean esCartera = UtilsJSON.jsonToObjeto(boolean.class, map.get("esCartera"));
        try {
            Map<String, Object> respues = anticipoService.obtenerDatosParaCrudOrdenesBancarias(esCartera);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para desarrollar trabajadores.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //listaOrdenesBancarias
    @RequestMapping("/getRhListaOrdenBancariaTO")
    public RespuestaWebTO generarOrdenBancaria(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        RhOrdenBancariaTO ordenBancariaTO = UtilsJSON.jsonToObjeto(RhOrdenBancariaTO.class, map.get("ordenBancariaTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        boolean sinCuenta = UtilsJSON.jsonToObjeto(boolean.class, map.get("sinCuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            resp = anticipoService.generarListaOrden(ordenBancariaTO, sector, sisInfoTO, sinCuenta);
            if (resp.getExtraInfo() == null) {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //generarTxt
    @RequestMapping("/exportarReporteTxtOrdenBancaria")
    public @ResponseBody
    void exportarReporteTxtOrdenBancaria(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        RhOrdenBancariaTO ordenBancariaTO = UtilsJSON.jsonToObjeto(RhOrdenBancariaTO.class, parametros.get("ordenBancariaTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        boolean esCartera = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("esCartera"));
        boolean sinCuenta = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("sinCuenta"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            reporteRrhhService.generarArchivoOrdenBanco(ordenBancariaTO, sector, response, sisInfoTO, esCartera, parametros, sinCuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
    }

    @RequestMapping("/exportarReporteOrdenBancaria")
    public @ResponseBody
    String exportarReporteOrdenBancaria(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        RhOrdenBancariaTO ordenBancariaTO = UtilsJSON.jsonToObjeto(RhOrdenBancariaTO.class, map.get("ordenBancariaTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        boolean esCartera = UtilsJSON.jsonToObjeto(boolean.class, map.get("esCartera"));
        boolean sinCuenta = UtilsJSON.jsonToObjeto(boolean.class, map.get("sinCuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteOrdenBanco(ordenBancariaTO, sector, usuarioEmpresaReporteTO, sisInfoTO, esCartera, map, sinCuenta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getRhParametros")
    public RespuestaWebTO getRhParametros(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RhParametros respues = parametrosService.getRhParametros(fechaHasta);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron parámetros para la fecha indicada.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprobanteColectivo")
    public @ResponseBody
    String generarReporteComprobanteColectivo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteAnticipoPorLoteFirmaColectiva.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteColectivo(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSoporte")
    public @ResponseBody
    String generarReporteSoporte(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSoporte.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteSoporte(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRRHHIndividual")
    public @ResponseBody
    String generarReporteRRHHIndividual(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarListaConContableReporteRRHH(listado, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRRHHIndividualLiquidacion")
    public @ResponseBody
    String generarReporteRRHHIndividualLiquidacion(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRRHHIndividualLiquidacion(listado, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRRHHIndividualLiquidacionMatricial")
    public RespuestaWebTO generarReporteRRHHIndividualLiquidacionMatricial(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarReporteRRHHIndividualLiquidacion(listado, usuarioEmpresaReporteTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Impuesto a la renta*/
    @RequestMapping("/getImpuestoRenta")
    public RespuestaWebTO getImpuestoRenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String año = UtilsJSON.jsonToObjeto(String.class, map.get("año"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhImpuestoRenta> respues = impuestoRentaService.getImpuestoRenta(empresa, año);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerRolConsulta")
    public RespuestaWebTO obtenerRolConsulta(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String idEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("idEmpleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            ItemListaRolTO respues = rolService.obtenerRolTO(conContablePK, empresa, idEmpleado, sisInfoTO);
            if (respues != null) {
                List<RhListaRolSaldoEmpleadoDetalladoTO> detalle = rolService.getRhRolSaldoEmpleadoDetalladoTO(
                        empresa,
                        respues.getRhRol().getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                        respues.getRolDesdeTexto(),
                        respues.getRolHastaTexto());
                respues.setDetalle(detalle);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontro rol");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosCategoria")
    public RespuestaWebTO obtenerDatosCategoria(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = categoriaService.obtenerDatosCategoria(map);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //SALARIO DIGNO
    @RequestMapping("/insertarRhSalarioDignoContable")
    public RespuestaWebTO insertarRhSalarioDignoContable(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhSalarioDignoTO rhSalarioDignoTO = UtilsJSON.jsonToObjeto(RhSalarioDignoTO.class, map.get("rhSalarioDignoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        ConContablePK pkContable = new ConContablePK();
        try {
            RhContableTO contableTo = salarioDignoService.insertarRhSalarioDignoContable(rhSalarioDignoTO, sisInfoTO);
            if (contableTo != null && contableTo.getMensaje().charAt(0) == 'T') {
                pkContable.setConEmpresa(rhSalarioDignoTO.getEmpEmpresa());
                pkContable.setConNumero(contableTo.getConNumero());
                pkContable.setConPeriodo(contableTo.getPerCodigo());
                pkContable.setConTipo(contableTo.getTipCodigo());
                contableService.mayorizarDesmayorizarSql(pkContable, false, sisInfoTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(contableTo);
                resp.setOperacionMensaje(contableTo.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(contableTo.getMensaje().substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosSalarioDigno")
    public RespuestaWebTO obtenerDatosSalarioDigno(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = salarioDignoService.obtenerDatosSalarioDigno(map);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //matricial
    @RequestMapping("/generarReporteRRHHIndividualMatricial")
    public RespuestaWebTO generarReporteRRHHIndividualMatricial(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarListaConContableReporteRRHH(listado, usuarioEmpresaReporteTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRRHHMatricialXIV")
    public RespuestaWebTO generarReporteRRHHMatricialXIV(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarReporteRRHHMatricialXIV(listado, usuarioEmpresaReporteTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRRHHXIV")
    public @ResponseBody
    String generarReporteRRHHXIV(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRRHHMatricialXIV(listado, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRRHHMatricialXIII")
    public RespuestaWebTO generarReporteRRHHMatricialXIII(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarReporteRRHHMatricialXIII(listado, usuarioEmpresaReporteTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRRHHXIII")
    public @ResponseBody
    String generarReporteRRHHXIII(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRRHHMatricialXIII(listado, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRRHHMatricialAnticipo")
    public RespuestaWebTO generarReporteRRHHMatricialAnticipo(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarReporteRRHHMatricialAnticipo(listado, usuarioEmpresaReporteTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRRHHAnticipo")
    public @ResponseBody
    String generarReporteRRHHAnticipo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRRHHMatricialAnticipo(listado, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRRHHMatricialPrestamo")
    public RespuestaWebTO generarReporteRRHHMatricialPrestamo(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarReporteRRHHMatricialPrestamo(listado, usuarioEmpresaReporteTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRRHHPrestamo")
    public @ResponseBody
    String generarReporteRRHHPrestamo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRRHHMatricialPrestamo(listado, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRRHHMatricialRolDePagos")
    public RespuestaWebTO generarReporteRRHHMatricialRolDePagos(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarReporteRRHHMatricialRolDePagos(listado, usuarioEmpresaReporteTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRRHHRolDePagos")
    public @ResponseBody
    String generarReporteRRHHRolDePagos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRRHHMatricialRolDePagos(listado, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRRHHMatricialUtilidades")
    public RespuestaWebTO generarReporteRRHHMatricialUtilidades(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarReporteRRHHMatricialUtilidades(listado, usuarioEmpresaReporteTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRRHHUtilidades")
    public @ResponseBody
    String generarReporteRRHHUtilidades(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRRHHMatricialUtilidades(listado, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRRHHMatricialLiquidacion")
    public RespuestaWebTO generarReporteRRHHMatricialLiquidacion(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarReporteRRHHMatricialLiquidacion(listado, usuarioEmpresaReporteTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRRHHLiquidacion")
    public @ResponseBody
    String generarReporteRRHHLiquidacion(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRRHHMatricialLiquidacion(listado, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteColectivoMatricial")
    public RespuestaWebTO generarReporteComprobanteColectivoMatricial(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteRrhhService.generarReporteComprobanteColectivo(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //matricial       generarReporteComprobanteRolLoteMatricial
    @RequestMapping("/generarReporteComprobanteRolLoteMatricial")
    public RespuestaWebTO generarReporteComprobanteRolLoteMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportComprobanteRol.jrxml";
        byte[] respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleRolesTO> listaRhListaDetalleRolesTO = UtilsJSON.jsonToList(RhListaDetalleRolesTO.class, parametros.get("listaRhListaDetalleRolesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteRolLote(usuarioEmpresaReporteTO, nombreReporte, empresa, listaRhListaDetalleRolesTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhCategoriaTOMatricial")
    public RespuestaWebTO generarReporteRhCategoriaTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportRhCategoria.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhCategoriaTO> listado = UtilsJSON.jsonToList(RhCategoriaTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhCategoriaTO(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConceptoBonosMatricial")
    public RespuestaWebTO generarReporteConceptoBonosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaBonoConceptoTO> listado = UtilsJSON.jsonToList(RhListaBonoConceptoTO.class, parametros.get("ListadoConceptoBonos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteConceptoBonos(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhFormaPagoTOMatricial")
    public RespuestaWebTO generarReporteRhFormaPagoTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportFormaPago.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaFormaPagoTO> listado = UtilsJSON.jsonToList(RhListaFormaPagoTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhFormaPagoTO(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhFormaPagoBeneficiosTOMatricial")
    public RespuestaWebTO generarReporteRhFormaPagoBeneficiosTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportFormaPagoBeneficios.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaFormaPagoBeneficioSocialTO> listado = UtilsJSON.jsonToList(RhListaFormaPagoBeneficioSocialTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhFormaPagoBeneficiosTO(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhAnticipoMotivoMatricial")
    public RespuestaWebTO generarReporteRhAnticipoMotivoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportMotivoAnticipo.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhAnticipoMotivo> listado = UtilsJSON.jsonToList(RhAnticipoMotivo.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhAnticipoMotivo(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhMotivoBonosMatricial")
    public RespuestaWebTO generarReporteRhMotivoBonosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhBonoMotivo> listado = UtilsJSON.jsonToList(RhBonoMotivo.class, parametros.get("ListadoMotivoBonos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoBonos(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhMotivoPrestamoMatricial")
    public RespuestaWebTO generarReporteRhMotivoPrestamoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhPrestamoMotivo> listado = UtilsJSON.jsonToList(RhPrestamoMotivo.class, parametros.get("ListadoMotivoPrestamo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoPrestamo(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhMotivoRolPagoMatricial")
    public RespuestaWebTO generarReporteRhMotivoRolPagoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhRolMotivo> listado = UtilsJSON.jsonToList(RhRolMotivo.class, parametros.get("ListadoRolPago"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoRolPago(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhUtilidadMotivoMatricial")
    public RespuestaWebTO generarReporteRhUtilidadMotivoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportRhUtilidadMotivo.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhUtilidadMotivo> listado = UtilsJSON.jsonToList(RhUtilidadMotivo.class, parametros.get("ListadoRhUtilidadMotivo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhUtilidadMotivo(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteMotivoXiiiSueldoMatricial")
    public RespuestaWebTO generarReporteMotivoXiiiSueldoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportMotivoXiiiSueldo.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhXiiiSueldoMotivo> listado = UtilsJSON.jsonToList(RhXiiiSueldoMotivo.class, parametros.get("ListadoXiiiSueldo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteMotivoXiiiSueldo(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhXivSueldoMotivoMatricial")
    public RespuestaWebTO generarReporteRhXivSueldoMotivoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportRhXivSueldoMotivo.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhXivSueldoMotivo> listado = UtilsJSON.jsonToList(RhXivSueldoMotivo.class, parametros.get("ListadoRhXivSueldoMotivo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteRhXivSueldoMotivo(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePeriodosUtilidadMatricial")
    public RespuestaWebTO generarReportePeriodosUtilidadMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportPeriodosUtilidad.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhUtilidadesPeriodoTO> listado = UtilsJSON.jsonToList(RhUtilidadesPeriodoTO.class, parametros.get("ListadoPeriodosUtilidad"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReportePeriodosUtilidad(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteXiiiSueldoPeriodoMatricial")
    public RespuestaWebTO generarReporteXiiiSueldoPeriodoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportPeriodosXiiiSueldo.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhXiiiSueldoPeriodoTO> listado = UtilsJSON.jsonToList(RhXiiiSueldoPeriodoTO.class, parametros.get("ListadoXiiiSueldoPeriodo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXiiiSueldoPeriodo(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteXivSueldoPeriodoMatricial")
    public RespuestaWebTO generarReporteXivSueldoPeriodoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportPeriodosXivSueldo.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhXivSueldoPeriodoTO> listado = UtilsJSON.jsonToList(RhXivSueldoPeriodoTO.class, parametros.get("ListadoPeriodoXivSueldo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXivSueldoPeriodo(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRhEmpleadoMatricial")
    public RespuestaWebTO generarReporteRhEmpleadoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhEmpleado> listado = UtilsJSON.jsonToList(RhEmpleado.class, parametros.get("lista"));
        List<RhFunListadoEmpleadosTO> listadoC = UtilsJSON.jsonToList(RhFunListadoEmpleadosTO.class, parametros.get("lista"));
        String tipoVista = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipoVista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            switch (tipoVista) {
                case "A"://archivo
                    nombreReporte = "reportEmpleadosArchivo.jrxml";
                    respuesta = reporteRrhhService.generarReporteRhEmpleado(usuarioEmpresaReporteTO, nombreReporte, listado);
                    break;
                case "I"://individual
                    nombreReporte = "reportEmpleado.jrxml";
                    respuesta = reporteRrhhService.generarReporteRhEmpleadoDatosPersonales(usuarioEmpresaReporteTO, nombreReporte, listado);
                    break;
                default:
                    nombreReporte = "reportEmpleadosBusqueda.jrxml";
                    respuesta = reporteRrhhService.generarReporteRhEmpleadoConsuta(usuarioEmpresaReporteTO, nombreReporte, listadoC);
                    break;
            }
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoBonosViaticoMatricial")
    public RespuestaWebTO generarReporteConsolidadoBonosViaticoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaConsolidadoBonosTO> listaConsolidadoBonosViaticosTO = UtilsJSON.jsonToList(RhListaConsolidadoBonosTO.class, parametros.get("listaConsolidadoBonosViaticosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoBonosViatico(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaConsolidadoBonosViaticosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoHorasExtrasMatricial")
    public RespuestaWebTO generarReporteConsolidadoHorasExtrasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaConsolidadoHorasExtrasTO> listaConsolidadoBonosViaticosTO = UtilsJSON.jsonToList(RhListaConsolidadoHorasExtrasTO.class, parametros.get("listaConsolidadoHorasExtrasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoHorasExtras(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaConsolidadoBonosViaticosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoHorasExtras")
    public String generarReporteConsolidadoHorasExtras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsolidadoHorasExtras";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaConsolidadoHorasExtrasTO> listaConsolidadoBonosViaticosTO = UtilsJSON
                .jsonToList(RhListaConsolidadoHorasExtrasTO.class, parametros.get("listaConsolidadoHorasExtrasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoHorasExtras(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, listaConsolidadoBonosViaticosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoRolesMatricial")
    public RespuestaWebTO generarReporteConsolidadoRolesMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreEmpleado"));
        boolean excluirLiquidacion = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("excluirLiquidacion"));
        List<RhListaConsolidadoRolesTO> listado = UtilsJSON.jsonToList(RhListaConsolidadoRolesTO.class, parametros.get("listaConsolidadoRolesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoRoles(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, empCategoria, nombreEmpleado, listado, excluirLiquidacion);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //reportSoporte
    @RequestMapping("/generarReporteSoporteMatricial")
    public RespuestaWebTO generarReporteSoporteMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteSoporte(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoRolesPagoMatricial")
    public RespuestaWebTO generarReporteListadoRolesPagoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String nombreEmpleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreEmpleado"));
        List<RhListaDetalleRolesTO> listaDetalleRolesTO = UtilsJSON.jsonToList(RhListaDetalleRolesTO.class, parametros.get("listaDetalleRolesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteListadoRolesPago(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, empCategoria, nombreEmpleado, listaDetalleRolesTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoAnticiposPrestamosMatricial")
    public RespuestaWebTO generarReporteSaldoConsolidadoAnticiposPrestamosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado = UtilsJSON.jsonToList(RhListaSaldoConsolidadoAnticiposPrestamosTO.class, parametros.get("listaSaldoConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteSaldoConsolidadoAnticiposPrestamos(usuarioEmpresaReporteTO, fechaHasta, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoSeparandoAnticiposYPrestamosMatricial")
    public RespuestaWebTO generarReporteSaldoConsolidadoSeparandoAnticiposYPrestamosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado = UtilsJSON.jsonToList(RhListaSaldoConsolidadoAnticiposPrestamosTO.class, parametros.get("listaSaldoConsolidadoAnticiposPrestamosTO"));
        String documento = UtilsJSON.jsonToObjeto(String.class, parametros.get("documento"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteSaldoConsolidadoSeparandoAnticiposYPrestamos(usuarioEmpresaReporteTO, fechaHasta, listado, documento);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoConsolidadoSueldosPorPagarMatricial")
    public RespuestaWebTO generarReporteSaldoConsolidadoSueldosPorPagarMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaSaldoConsolidadoSueldosPorPagarTO> listado = UtilsJSON.jsonToList(RhListaSaldoConsolidadoSueldosPorPagarTO.class, parametros.get("listaSaldoConsolidadoSueldosPorPagarTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteSaldoConsolidadoSueldosPorPagar(usuarioEmpresaReporteTO, fechaHasta, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoIndividualAnticiposPrestamosMatricial")
    public RespuestaWebTO generarReporteSaldoIndividualAnticiposPrestamosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empId = UtilsJSON.jsonToObjeto(String.class, parametros.get("empId"));
        List<RhListaSaldoIndividualAnticiposPrestamosTO> listado = UtilsJSON.jsonToList(RhListaSaldoIndividualAnticiposPrestamosTO.class, parametros.get("listaSaldoIndividualAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteSaldoIndividualAnticiposPrestamos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, empId, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsultaAnticiposLoteWebMatricial")
    public RespuestaWebTO generarReporteConsultaAnticiposLoteWebMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleAnticiposLoteTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposLoteTO.class, parametros.get("listaDetalleAnticiposLoteTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsultaAnticiposLoteWeb(usuarioEmpresaReporteTO, listaDetalleAnticiposTO, nombre, periodo, tipo, numero);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteAnticipoMatricial")
    public RespuestaWebTO generarReporteAnticipoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ReporteAnticipoPrestamoXIIISueldo> rhReporteAnticipoOprestamos = UtilsJSON.jsonToList(ReporteAnticipoPrestamoXIIISueldo.class, parametros.get("listaDetalleAnticiposLoteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteAnticipo(usuarioEmpresaReporteTO, rhReporteAnticipoOprestamos);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteContableDetalleMatricial")
    public RespuestaWebTO generarReporteContableDetalleMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConContableReporteTO> listConContableReporteTO = anticipoService.generarConContableReporte(usuarioEmpresaReporteTO, periodo, tipo, numero, sisInfoTO);
            respuesta = reporteContabilidadService.generarReporteContableDetalle(usuarioEmpresaReporteTO, listConContableReporteTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetalleBonosLoteMatricial")
    public RespuestaWebTO generarReporteDetalleBonosLoteMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        List<RhListaDetalleBonosLoteTO> listaDetalleBonosLoteTO = UtilsJSON.jsonToList(RhListaDetalleBonosLoteTO.class, parametros.get("listaDetalleBonosLoteTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleBonosLote(usuarioEmpresaReporteTO, periodo, tipo, numero, listaDetalleBonosLoteTO, nombre);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProvisionesComprobanteContableMatricial")
    public RespuestaWebTO generarReporteProvisionesComprobanteContableMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        List<RhListaProvisionesTO> lista = UtilsJSON.jsonToList(RhListaProvisionesTO.class, parametros.get("listaProvisionesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteProvisionesComprobanteContable(usuarioEmpresaReporteTO, periodo, tipo, numero, lista);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteUtilidadesPreCalculoMatricial")
    public RespuestaWebTO generarReporteUtilidadesPreCalculoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        List<RhFunUtilidadesCalcularTO> rhFunUtilidadesCalcularTOs = UtilsJSON.jsonToList(RhFunUtilidadesCalcularTO.class, parametros.get("rhFunUtilidadesCalcularTOs"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteUtilidadesPreCalculo(usuarioEmpresaReporteTO, sector, periodo, fechaDesde, fechaHasta, fechaMaxima, rhFunUtilidadesCalcularTOs);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListaDetalleVacacionesMatricial")
    public RespuestaWebTO generarReporteListaDetalleVacacionesMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String empSector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        List<RhDetalleVacionesPagadasGozadasTO> listaDetalleVacacionesPagadasGozadasTO = UtilsJSON.jsonToList(RhDetalleVacionesPagadasGozadasTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (tipo.equals("P")) {
                nombreReporte = "reportListaDetalleVacacionesPagadas.jrxml";
            } else {
                nombreReporte = "reportListaDetalleVacacionesGozadas.jrxml";
            }
            respuesta = reporteRrhhService.generarReporteListaDetalleVacaciones(usuarioEmpresaReporteTO, desde, hasta,
                    empSector, listaDetalleVacacionesPagadasGozadasTO, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteXiiiSueldoConsultaMatricial")
    public RespuestaWebTO generarReporteXiiiSueldoConsultaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        List<RhFunXiiiSueldoConsultarTO> rhFunXiiiSueldoConsultarTO = UtilsJSON.jsonToList(RhFunXiiiSueldoConsultarTO.class, parametros.get("rhFunXiiiSueldoConsultarTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXiiiSueldoConsulta(usuarioEmpresaReporteTO, sector, periodo, fechaDesde, fechaHasta, fechaMaxima, rhFunXiiiSueldoConsultarTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteXivSueldoConsultaMatricial")
    public RespuestaWebTO generarReporteXivSueldoConsultaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String fechaMaxima = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaMaxima"));
        List<RhFunXivSueldoConsultarTO> rhFunXivSueldoConsultarTO = UtilsJSON.jsonToList(RhFunXivSueldoConsultarTO.class, parametros.get("rhFunXivSueldoConsultarTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteXivSueldoConsulta(usuarioEmpresaReporteTO, sector, periodo, fechaDesde, fechaHasta, fechaMaxima, rhFunXivSueldoConsultarTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetalleAnticiposMatricial")
    public RespuestaWebTO generarReporteDetalleAnticiposMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empId = UtilsJSON.jsonToObjeto(String.class, parametros.get("empId"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCodigo"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposTO.class, parametros.get("listaDetalleAnticiposTO"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleAnticipos(usuarioEmpresaReporteTO, empId, empCodigo, empCategoria, fechaDesde, fechaHasta, listaDetalleAnticiposTO, nombre);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteAnticipoDetalleAnticipoMatricial")
    public RespuestaWebTO generarReporteAnticipoDetalleAnticipoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposTO.class, parametros.get("listaDetalleAnticiposTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteAnticipoDetalleAnticipo(usuarioEmpresaReporteTO, listaDetalleAnticiposTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteAnticipoContableMatricial")
    public RespuestaWebTO generarReporteAnticipoContableMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO = UtilsJSON.jsonToList(RhListaDetalleAnticiposTO.class, parametros.get("listaDetalleAnticiposTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteAnticipoContable(usuarioEmpresaReporteTO, listaDetalleAnticiposTO, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetallePrestamosMatricial")
    public RespuestaWebTO generarReporteDetallePrestamosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, parametros.get("empId"));
        List<RhListaDetallePrestamosTO> listaDetallePrestamosTO = UtilsJSON.jsonToList(RhListaDetallePrestamosTO.class, parametros.get("listaDetallePrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetallePrestamos(usuarioEmpresaReporteTO, empCodigo, fechaDesde,
                    fechaHasta, empCategoria, empId, listaDetallePrestamosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDetalleAnticiposPrestamosMatricial")
    public RespuestaWebTO generarReporteDetalleAnticiposPrestamosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportRRHHDetalleAnticiposPrestamos";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, parametros.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, parametros.get("empId"));
        List<RhListaDetalleAnticiposPrestamosTO> listaDetalleAnticiposPrestamosTO = UtilsJSON
                .jsonToList(RhListaDetalleAnticiposPrestamosTO.class, parametros.get("listaDetalleAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteDetalleAnticiposPrestamos(usuarioEmpresaReporteTO, empCodigo,
                    fechaDesde, fechaHasta, empCategoria, empId, listaDetalleAnticiposPrestamosTO, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoAnticiposPrestamosMatricial")
    public RespuestaWebTO generarReporteConsolidadoAnticiposPrestamosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaConsolidadoAnticiposPrestamosTO> listaConsolidadoAnticiposPrestamosTO = UtilsJSON.jsonToList(RhListaConsolidadoAnticiposPrestamosTO.class, parametros.get("listaConsolidadoAnticiposPrestamosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoAnticiposPrestamos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaConsolidadoAnticiposPrestamosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getEstadoReconstruccionSaldosEmpleados")
    public RespuestaWebTO getEstadoReconstruccionSaldosEmpleados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = empleadoService.getReconstruccionSaldosEmpleados(empresa);
            if (respues == true) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La consulta Fue realizada Exitosamente en reconstrucción de saldos empleados");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("La consulta No fue relizada Exitosamente en reconstrucción de saldos empleados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerUltimoParametroConfiguracion")
    public RespuestaWebTO obtenerUltimoParametroConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RhParametros listado = parametrosService.obtenerUltimoParametro();
            if (listado != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listado);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarParametroConfiguracion")
    public RespuestaWebTO insertarParametroConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhParametros rhParametros = UtilsJSON.jsonToObjeto(RhParametros.class, map.get("rhParametros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            rhParametros = parametrosService.insertarRhParametro(rhParametros, sisInfoTO);
            if (rhParametros != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(rhParametros);
                resp.setOperacionMensaje("El parámetro de configuración se ha guardado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al guardar el parámetro de configuración.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarParametroConfiguracion")
    public RespuestaWebTO modificarParametroConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhParametros rhParametros = UtilsJSON.jsonToObjeto(RhParametros.class, map.get("rhParametros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            rhParametros = parametrosService.modificarRhParametro(rhParametros, sisInfoTO);
            if (rhParametros != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(rhParametros);
                resp.setOperacionMensaje("El parámetro de configuración se ha modificado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar el parámetro de configuración.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarParametroConfiguracion")
    public RespuestaWebTO eliminarParametroConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        int rhParametros = UtilsJSON.jsonToObjeto(int.class, map.get("rhParametros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = parametrosService.eliminarRhParametro(rhParametros, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("El parámetro de configuración se ha aliminado correctamente");
            } else {
                resp.setOperacionMensaje("No se puede eliminar el parámetro");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteParametros")
    public @ResponseBody
    String exportarReporteParametros(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhParametros> listado = UtilsJSON.jsonToList(RhParametros.class, map.get("listadoRhParametros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteParametros(listado, usuarioEmpresaReporteTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerDatosParaCrudConfiguracion")
    public RespuestaWebTO obtenerDatosParaCrudConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = parametrosService.obtenerDatosParaCrudConfiguracion();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para configurar parámetros.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //configuracion de provisiones
    @RequestMapping("/listarConfiguracionDeProvisiones")
    public RespuestaWebTO listarConfiguracionDeProvisiones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RhProvisiones respues = provisionesService.listarRhProvisiones(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("La empresa con código " + empresa + " no tiene datos de configuración de provisiones registrados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarConfiguracionDeProvisiones")
    public RespuestaWebTO insertarConfiguracionDeProvisiones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhProvisiones rhProvisiones = UtilsJSON.jsonToObjeto(RhProvisiones.class, map.get("rhProvisiones"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            rhProvisiones = provisionesService.insertarRhProvisiones(rhProvisiones, sisInfoTO);
            if (rhProvisiones != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(rhProvisiones);
                resp.setOperacionMensaje("Los valores de configuración de provisiones se han guardado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al guardar los valores de configuración la provisiones");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarConfiguracionDeProvisiones")
    public RespuestaWebTO modificarConfiguracionDeProvisiones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhProvisiones rhProvisiones = UtilsJSON.jsonToObjeto(RhProvisiones.class, map.get("rhProvisiones"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            rhProvisiones = provisionesService.modificarRhProvisiones(rhProvisiones, sisInfoTO);
            if (rhProvisiones != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(rhProvisiones);
                resp.setOperacionMensaje("Los valores de configuración de provisiones se han modificado correctamente");
            } else {
                resp.setOperacionMensaje("Los valores de configuración de provisiones No se han podido modificar");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarUtilidades")
    public RespuestaWebTO listarUtilidades(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhFunUtilidadesConsultarTO> respues = utilidadService.getRhFunConsultarUtilidades(empresa, sector, desde, hasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteUtilidades")
    public @ResponseBody
    String exportarReporteUtilidades(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<RhFunUtilidadesConsultarTO> listado = UtilsJSON.jsonToList(RhFunUtilidadesConsultarTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteUtilidades(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteUtilidadesConsulta")
    public @ResponseBody
    String generarReporteComprobanteUtilidadesConsulta(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportComprobanteUtilidades.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhFunUtilidadesConsultarTO> listaRhUtilidades = UtilsJSON.jsonToList(RhFunUtilidadesConsultarTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteUtilidad(usuarioEmpresaReporteTO, listaRhUtilidades, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteUtilidadesConsultaMatricial")
    public RespuestaWebTO generarReporteComprobanteUtilidadesConsultaMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteUtilidades.jrxml";
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhFunUtilidadesConsultarTO> listaRhUtilidades = UtilsJSON.jsonToList(RhFunUtilidadesConsultarTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteComprobanteUtilidad(usuarioEmpresaReporteTO, listaRhUtilidades, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    // vacaciones
    @RequestMapping("/extraerDatosPaVacaciones")
    public RespuestaWebTO extraerDatosPaVacaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = vacacionesService.obtenerDatosParaCrudVacaciones(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron datos para vacaciones.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhVacacionesConContable")
    public RespuestaWebTO insertarRhVacacionesConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        RhVacacionesTO rhVacacionesTO = UtilsJSON.jsonToObjeto(RhVacacionesTO.class, map.get("vacaciones"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RhContableTO respuesta;
        try {
            rhVacacionesTO.setUsrCodigo(sisInfoTO.getUsuario());
            respuesta = vacacionesService.insertarRhVacacionesConContable(rhVacacionesTO, sisInfoTO);
            if (respuesta != null && respuesta.getMensaje() != null) {
                if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                    ConContable contable = new ConContable(sisInfoTO.getEmpresa(), respuesta.getPerCodigo(), respuesta.getTipCodigo(), respuesta.getConNumero());
                    resp.setExtraInfo(contable);
                } else {
                    resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                }
            } else {
                resp.setOperacionMensaje("Ocurrió un error al insertar vacaciones");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhVacaciones")
    public RespuestaWebTO modificarRhVacaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        RhVacacionesTO rhVacacionesTO = UtilsJSON.jsonToObjeto(RhVacacionesTO.class, map.get("vacaciones"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RhContableTO respuesta;
        try {
            boolean pendiente = conContable.getConPendiente();
            respuesta = vacacionesService.modificarRhVacacionesConContable(rhVacacionesTO, conContable, sisInfoTO);
            if (respuesta != null && respuesta.getMensaje() != null) {
                if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                    if (!pendiente) {
                        contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                    }
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                    resp.setExtraInfo(conContable);
                } else {
                    resp.setOperacionMensaje(respuesta.getMensaje().substring(1, respuesta.getMensaje().length()));
                }
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar vacaciones");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    // vacaciones gozadas
    @RequestMapping("/listarRhVacacionesGozadas")
    public RespuestaWebTO listarRhVacacionesGozadas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhVacacionesGozadas> respuesta = vacacionesGozadasService.listarRhVacacionesGozadas(empresa, empId, sector, fechaDesde, fechaHasta);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarRhVacacionesGozadas")
    public RespuestaWebTO insertarRhVacacionesGozadas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        RhVacacionesGozadas respuesta = UtilsJSON.jsonToObjeto(RhVacacionesGozadas.class, map.get("vacaciones"));
        List<RhVacacionesGozadasDatosAdjuntosWebTO> listadoImagenes = UtilsJSON.jsonToList(RhVacacionesGozadasDatosAdjuntosWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = vacacionesGozadasService.insertarRhVacacionesGozadas(respuesta, listadoImagenes, sisInfoTO);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se realizó el registro de vacaciones N°. " + respuesta.getRhVacacionesGozadasPK().getVacNumero());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al insertar vacaciones");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarRhVacacionesGozadas")
    public RespuestaWebTO modificarRhVacacionesGozadas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhVacacionesGozadas respuesta = UtilsJSON.jsonToObjeto(RhVacacionesGozadas.class, map.get("vacaciones"));
        List<RhVacacionesGozadasDatosAdjuntosWebTO> listadoImagenes = UtilsJSON.jsonToList(RhVacacionesGozadasDatosAdjuntosWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = vacacionesGozadasService.modificarRhVacacionesGozadas(respuesta, listadoImagenes, sisInfoTO);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se modificó el registro de vacaciones N°. " + respuesta.getRhVacacionesGozadasPK().getVacNumero());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar vacaciones");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarRhVacacionesGozadas")
    public RespuestaWebTO desmayorizarRhVacacionesGozadas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = vacacionesGozadasService.desmayorizarRhVacacionesGozadas(numero, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se desmayorizó el registro de vacaciones N°. " + numero);
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al desmayorizar vacaciones");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarRhVacacionesGozadas")
    public RespuestaWebTO eliminarRhVacacionesGozadas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = vacacionesGozadasService.eliminarRhVacacionesGozadas(numero, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se eliminó el registro de vacaciones N°. " + numero);
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al eliminar vacaciones");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarVacacionesGozadas")
    public @ResponseBody
    String exportarVacacionesGozadas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        List<RhVacacionesGozadas> listado = UtilsJSON.jsonToList(RhVacacionesGozadas.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarVacacionesGozadas(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListaVacacionesGozadasMatricial")
    public RespuestaWebTO generarReporteListaVacacionesGozadasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String empSector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        List<RhVacacionesGozadas> lista = UtilsJSON.jsonToList(RhVacacionesGozadas.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteListaVacacionesGozadas(usuarioEmpresaReporteTO, desde, hasta,
                    empSector, lista, "reportListaVacacionesGozadas.jrxml");
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListaVacacionesGozadas")
    public @ResponseBody
    String generarReporteListaVacacionesGozadas(HttpServletResponse response, @RequestBody String json) {
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String empSector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        List<RhVacacionesGozadas> lista = UtilsJSON.jsonToList(RhVacacionesGozadas.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteListaVacacionesGozadas(usuarioEmpresaReporteTO, desde, hasta,
                    empSector, lista, "reportListaDetalleVacacionesGozadas.jrxml");
            return archivoService.generarReportePDF(respuesta, "reportListaDetalleVacacionesGozadas.jrxml", response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarUltimaVacacionesGozadas")
    public RespuestaWebTO eliminarUltimaVacacionesGozadas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String resultado = vacacionesGozadasService.eliminarUltimaVacacionesGozadas(empresa, numero, empId, sisInfoTO);
            if (resultado.equalsIgnoreCase("t")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La solicitud N. " + numero + " se ha eliminado exitosamente.");
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(resultado);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerImagenesRhVacacionesGozadas")
    public RespuestaWebTO obtenerImagenesRhVacacionesGozadas(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RhVacacionesGozadasPK pk = UtilsJSON.jsonToObjeto(RhVacacionesGozadasPK.class, map.get("rhVacacionesGozadasPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhVacacionesGozadasDatosAdjuntosWebTO> respues = vacacionesGozadasService.convertirStringUTF8(pk);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron imágenes de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularRestaurarRhVacacionesGozadas")
    public ResponseEntity anularRestaurarRhVacacionesGozadas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        RhVacacionesGozadasPK pk = UtilsJSON.jsonToObjeto(RhVacacionesGozadasPK.class, parametros.get("rhVacacionesGozadasPK"));
        boolean anularRestaurar = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("anularRestaurar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String respues = vacacionesGozadasService.anularRestaurarRhVacacionesGozadas(pk, anularRestaurar, sisInfoTO);
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteListaVacacionesGozadasIndividualMatricial")
    public RespuestaWebTO generarReporteListaVacacionesGozadasIndividualMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhVacacionesGozadas> lista = UtilsJSON.jsonToList(RhVacacionesGozadas.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteListaVacacionesGozadasIndividual(usuarioEmpresaReporteTO, lista, "reportComprobanteSolicitudVacaciones.jrxml");
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListaVacacionesGozadasIndividual")
    public @ResponseBody
    String generarReporteListaVacacionesGozadasIndividual(HttpServletResponse response, @RequestBody String json) {
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhVacacionesGozadas> lista = UtilsJSON.jsonToList(RhVacacionesGozadas.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteListaVacacionesGozadasIndividual(usuarioEmpresaReporteTO, lista, "reportComprobanteSolicitudVacaciones.jrxml");
            return archivoService.generarReportePDF(respuesta, "reportComprobanteSolicitudVacaciones.jrxml", response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/buscarFechaHastaRhVacaciones")
    public RespuestaWebTO buscarFechaHastaRhVacaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = vacacionesService.buscarFechaHastaRhVacaciones(empCodigo, empId);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respuesta);
            resp.setOperacionMensaje("");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerValorProvisionadoRoles")
    public RespuestaWebTO obtenerValorProvisionadoRoles(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            BigDecimal respuesta = vacacionesService.obtenerValorProvisionadoRoles(fechaDesde, fechaHasta, empCodigo, empId);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respuesta);
            resp.setOperacionMensaje("");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListRhLiquidaciones")
    public RespuestaWebTO getListRhLiquidaciones(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String empCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("empCategoria"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String nroRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nroRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhRolEmpTO> respues = rolService.getListRhLiquidaciones(empresa, fechaDesde, fechaHasta, sector, empCategoria, empId, nroRegistros);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteLiquidacion")
    public @ResponseBody
    String exportarReporteBodegas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RhRolEmpTO> listado = UtilsJSON.jsonToList(RhRolEmpTO.class, map.get("ListadoLiquidacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarLiquidacion(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //listar soporte de benficios
    @RequestMapping("/listarSoporteBeneficios")
    public RespuestaWebTO listarSoporteBeneficios(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = reporteRrhhService.listarSoporteBeneficios(empresa, periodo, tipo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoRolPorLote")
    public RespuestaWebTO enviarCorreoRolPorLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisEmpresaNotificaciones notificacion = UtilsJSON.jsonToObjeto(SisEmpresaNotificaciones.class, map.get("notificacion"));
        ConContablePK contablepk = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> mensajeAux = envioCorreoService.enviarCorreoRolPorLote(contablepk, usuarioEmpresaReporteTO, notificacion);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(mensajeAux);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    // consolidado de bonos horas extras
    @RequestMapping("/listarConsolidadoBonosHorasExtras")
    public RespuestaWebTO listarConsolidadoBonosHorasExtras(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhListaConsolidadoBonosHorasExtrasTO> respues = bonoService.listarConsolidadoBonosHorasExtras(empCodigo, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoBonosHorasExtras")
    public String generarReporteConsolidadoBonosHorasExtras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsolidadoBonosHorasExtras";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaConsolidadoBonosHorasExtrasTO> listaConsolidadoBonosViaticosTO = UtilsJSON
                .jsonToList(RhListaConsolidadoBonosHorasExtrasTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoBonosHorasExtras(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, listaConsolidadoBonosViaticosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConsolidadoBonosHorasExtras")
    public @ResponseBody
    String exportarReporteConsolidadoBonosHorasExtras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        List<RhListaConsolidadoBonosHorasExtrasTO> listaConsolidadoBonosTO = UtilsJSON.jsonToList(RhListaConsolidadoBonosHorasExtrasTO.class,
                map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarReporteConsolidadoBonosHorasExtras(usuarioEmpresaReporteTO, listaConsolidadoBonosTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoBonosHorasExtrasMatricial")
    public RespuestaWebTO generarReporteConsolidadoBonosHorasExtrasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<RhListaConsolidadoBonosHorasExtrasTO> listaConsolidadoBonosViaticosTO = UtilsJSON.jsonToList(RhListaConsolidadoBonosHorasExtrasTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.generarReporteConsolidadoBonosHorasExtras(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaConsolidadoBonosViaticosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getRhFunFormularioSector107")
    public RespuestaWebTO getRhFunFormularioSector107(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        Character status = UtilsJSON.jsonToObjeto(Character.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhFunFormulario107_2013TO> respues = formulario107Service.getRhFunFormulario107(empresa, desde, hasta, status, sector);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoRol")
    public RespuestaWebTO enviarCorreoRol(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisEmpresaNotificaciones notificacion = UtilsJSON.jsonToObjeto(SisEmpresaNotificaciones.class, map.get("notificacion"));
        Integer rolSecuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("rolSecuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> mensajeAux = envioCorreoService.enviarCorreoRol(rolSecuencial, usuarioEmpresaReporteTO, notificacion);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(mensajeAux);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosRol")
    public RespuestaWebTO obtenerDatosRol(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = rolService.obtenerRol(map);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosRolConsulta")
    public RespuestaWebTO obtenerDatosRolConsulta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = rolService.obtenerRolConsulta(map);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosRolVistaPreliminarRol")
    public RespuestaWebTO obtenerDatosRolVistaPreliminarRol(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = rolService.obtenerVistaPreliminarRol(map);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoLiquidacionMatricial")
    public RespuestaWebTO generarReporteListadoLiquidacionMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportListadoLiquidacion.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhRolEmpTO> listado = UtilsJSON.jsonToList(RhRolEmpTO.class, parametros.get("ListadoLiquidacion"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteListadoLiquidacion(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoLiquidacion")
    public @ResponseBody
    String generarReporteListadoLiquidacion(HttpServletResponse response, @RequestBody String json) throws Exception {
        byte[] respuesta;
        String nombreReporte = "reportListadoLiquidacion.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<RhRolEmpTO> listado = UtilsJSON.jsonToList(RhRolEmpTO.class, parametros.get("ListadoLiquidacion"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteRrhhService.generarReporteListadoLiquidacion(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getSwInactivaEmpleado")
    public RespuestaWebTO getSwInactivaEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, map.get("empleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = empleadoService.getSwInactivaEmpleado(empresa, empleado);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje("");
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInactivaEmpleado")
    public RespuestaWebTO getInactivaEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, map.get("empleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = empleadoService.getInactivaEmpleado(empresa, empleado);
            if (respues != null && respues.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarRolPagoNotificaciones")
    public RespuestaWebTO listarRolPagoNotificaciones(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String contable = SistemaWebUtil.obtenerFiltroComoString(parametros, "contable");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        try {
            List<RhRolPagoNotificaciones> respues = rolPagoNotificacionesService.listarRolPagoNotificaciones(contable, fechaInicio, fechaFin, empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de notificaciones");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarRolNotificaciones")
    public @ResponseBody
    String exportarRolNotificaciones(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        List<RhRolPagoNotificaciones> lista = UtilsJSON.jsonToList(RhRolPagoNotificaciones.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarRolNotificaciones(usuarioEmpresaReporteTO, lista, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/verificarExistenciaEmpleados")
    public RespuestaWebTO verificarExistenciaEmpleados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = empleadoService.verificarExistenciaEmpleados(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarRhEmpleadoLote")
    public RespuestaWebTO insertarModificarRhEmpleadoLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = empleadoService.insertarModificarRhEmpleadoLote(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarRolesConVacaciones")
    public RespuestaWebTO validarRolesConVacaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<RhRol> listaRhRol = UtilsJSON.jsonToList(RhRol.class, map.get("listaRhRol"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> respuesta = new ArrayList<>();
            if (listaRhRol != null && !listaRhRol.isEmpty()) {
                for (int i = 0; i < listaRhRol.size(); i++) {
                    if (listaRhRol.get(i).getRolDescuentoVacaciones() != null && listaRhRol.get(i).getRolDescuentoVacaciones().signum() == 0) {
                        List<RhVacaciones> vacaciones = vacacionesService.listarVacacionesEntreUnRol(listaRhRol.get(i));
                        if (vacaciones != null && !vacaciones.isEmpty()) {
                            String texto = "El empleado: " + listaRhRol.get(i).getRhEmpleado().getRhEmpleadoPK().getEmpId() + ", tiene un pago de vacaciones que no ha sido considerado: ";
                            for (int j = 0; j < vacaciones.size(); j++) {
                                texto = texto + "Valor a tomar en cuenta: " + vacaciones.get(j).getVacValor() + "$ <br>";

                            }
                            respuesta.add(texto);
                        }
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    // vacaciones gozadas
    @RequestMapping("/listarRhVacaciones")
    public RespuestaWebTO listarRhVacaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RhVacaciones> respuesta = vacacionesService.listarRhVacacionesGozadas(empresa, empId, sector, fechaDesde, fechaHasta);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarVacaciones")
    public @ResponseBody
    String exportarVacaciones(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        List<RhVacaciones> listado = UtilsJSON.jsonToList(RhVacaciones.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteRrhhService.exportarVacaciones(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerFechaAfiliacionEmpleado")
    public RespuestaWebTO obtenerFechaAfiliacionEmpleado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        try {
            RhEmpleado respuesta = empleadoService.getEmpleado(empresa, empId);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.getEmpFechaAfiliacionIess());
            } else {
                resp.setExtraInfo(null);
            }
        } catch (Exception e) {
            resp.setExtraInfo(null);
        }
        return resp;
    }

    @RequestMapping("/obtenerIngresoDeVacacionesPorPeriodo")
    public RespuestaWebTO obtenerIngresoDeVacacionesPorPeriodo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        BigDecimal ingresoVacaciones = BigDecimal.ZERO;
        BigDecimal descuentoVacaciones = BigDecimal.ZERO;
        int diasDeVacaciones = 0;
        Map<String, Object> respuesta = new HashMap<>();
        try {
            List<RhRol> roles = rolService.obtenerRolesPorPeriodoDeVacaciones(empresa, empId, desde, hasta);
            RhVacaciones vacaciones = vacacionesService.buscarRhVacacionesPorPeriodoTrabajo(empresa, empId, desde, hasta);
            List<RhVacacionesGozadas> gozadas = vacacionesGozadasService.listarRhVacacionesGozadasSinContable(empresa, empId, null, desde, hasta);
            if (roles != null) {
                for (RhRol rol : roles) {
                    if (rol.getRolVacaciones() != null) {
                        ingresoVacaciones = ingresoVacaciones.add(rol.getRolVacaciones());
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            }
            if (vacaciones != null) {
                if (vacaciones.getVacGozadasDesde() != null && vacaciones.getVacGozadasHasta() != null) {
                    diasDeVacaciones = UtilsValidacion.numeroDias(vacaciones.getVacGozadasDesde(), vacaciones.getVacGozadasHasta());
                }
                if (vacaciones.getVacValor() != null) {
                    descuentoVacaciones = vacaciones.getVacValor();
                }
            }
            respuesta.put("ingresoVacaciones", ingresoVacaciones);
            respuesta.put("gozadas", gozadas);
            respuesta.put("descuentoVacaciones", descuentoVacaciones);
            respuesta.put("diasDeVacaciones", diasDeVacaciones);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setExtraInfo(ingresoVacaciones);
            respuesta.put("ingresoVacaciones", ingresoVacaciones);
            respuesta.put("descuentoVacaciones", descuentoVacaciones);
            respuesta.put("diasDeVacaciones", diasDeVacaciones);
            resp.setExtraInfo(respuesta);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
}
