package ec.com.todocompu.ShrimpSoftServer.contabilidad.controller;

import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequePostfechadoService;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstructuraDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.report.ReporteContabilidadService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.AperturaDeSaldosService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ConContableCierreResultadosService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContablePlanillaAportesService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContablePlanillaFondoReservaService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasEspecialesService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasFlujoDetalleService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasFlujoService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.DetalleService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.EstructuraFlujoService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.EstructuraService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.FlujoCajaService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.NumeracionService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ProductosEnProcesoErroresService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.TipoService;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.EmpleadoDescuentoFijoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.EmpleadoService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RolService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.EmpresaParametrosService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosVsInventarioDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDiarioAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceGeneralNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceResultadosNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunIPPTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaBalanceResultadosVsInventarioTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaContableDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosCuentasInconsistentes;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresCompra;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresConsumo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ItemListaContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ItemStatusItemListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.PersonaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContableEstadoSituacionInicial;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContableCierreResultado;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConEstructura;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.EstadosComparativosConOtrasEmpresasService;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaPostfechadoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticiposPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceCentroProduccionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaAportes;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaFondoReserva;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaCostoVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;

@RestController
@RequestMapping("/todocompuWS/contabilidadWebController")
public class ContabilidadWebController {

    @Autowired
    private EstructuraDao estructuraDao;
    @Autowired
    private NumeracionService numeracionService;
    @Autowired
    private TipoService tipoService;
    @Autowired
    private CuentasService cuentasService;
    @Autowired
    private CuentasEspecialesService cuentasEspecialesService;
    @Autowired
    private CuentasFlujoService cuentasFlujoService;
    @Autowired
    private CuentasFlujoDetalleService cuentasFlujoDetalleService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private DetalleService detalleService;
    @Autowired
    private EstructuraService estructuraService;
    @Autowired
    private EstructuraFlujoService estructuraFlujoService;
    @Autowired
    private ReporteContabilidadService reporteContabilidadService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private GenericoDao<ConCuentas, ConCuentasPK> conCuentaDao;
    @Autowired
    private GenericoDao<ConTipo, ConTipoPK> conTipoDao;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private EmpresaParametrosService empresaParametrosService;
    @Autowired
    private AperturaDeSaldosService aperturaDeSaldosService;
    @Autowired
    private ConContableCierreResultadosService conContableCierreResultadosService;
    @Autowired
    private ProductosEnProcesoErroresService productosEnProcesoErroresService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private EmpleadoDescuentoFijoService empleadoDescuentoFijoService;
    @Autowired
    private RolService rolService;
    @Autowired
    private FlujoCajaService flujoCajaService;
    @Autowired
    private EstadosComparativosConOtrasEmpresasService comparativoOtrasEmpresaService;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private GenericoDao<PrdPiscina, PrdPiscinaPK> prdPiscinaDao;
    @Autowired
    private ChequePostfechadoService chequePostfechadoService;
    @Autowired
    private CobrosAnticiposDao cobrosAnticiposDao;
    @Autowired
    private ContablePlanillaFondoReservaService contablePlanillaFondoReservaService;
    @Autowired
    private ContablePlanillaAportesService contablePlanillaAportesService;

    /* ESTRUCTURA*/
    @RequestMapping("/getListaConEstructura")
    public RespuestaWebTO getListaConEstructura(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConEstructuraTO> respues = estructuraService.getListaConEstructuraTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de estructura");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarConEstructura")
    public RespuestaWebTO insertarConEstructura(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConEstructura conEstructura = UtilsJSON.jsonToObjeto(ConEstructura.class, map.get("conEstructura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (estructuraDao.obtenerPorId(ConEstructura.class, conEstructura.getEmpCodigo()) == null) {
                MensajeTO msje = estructuraService.insertarConEstructura(conEstructura, sisInfoTO);
                if (msje.getMensaje().equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje("Se guardó la estructura de manera exitosa");
                    resp.setExtraInfo(true);
                } else {
                    resp.setOperacionMensaje("No se logró guardar la estructura");
                }
            } else {
                resp.setOperacionMensaje("La empresa ya cuenta con una estructura");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarConEstructura")
    public ResponseEntity modificarConEstructura(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConEstructura conEstructura = UtilsJSON.jsonToObjeto(ConEstructura.class, map.get("conEstructura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO estructura = estructuraService.modificarConEstructura(conEstructura, sisInfoTO);
            if (estructura.getMensaje().equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se modificó la estructura de manera exitosa");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("No se logró modificar la estructura");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/eliminarConEstructura")
    public ResponseEntity eliminarConEstructura(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConEstructura conEstructura = UtilsJSON.jsonToObjeto(ConEstructura.class, map.get("conEstructura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO tipo = estructuraService.eliminarConEstructura(conEstructura, sisInfoTO);
            if (tipo.getMensaje().equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se eliminó la estructura de manera exitosa");
                resp.setExtraInfo(tipo);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se logró eliminar la estructura");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    /*PLAN DE CUENTAS*/
    @RequestMapping("/getConCuentas")
    public RespuestaWebTO getConCuentas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            List<ConCuentasTO> respues = cuentasService.getListaConCuentasTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de plan de cuentas");
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

    @RequestMapping("/obtenerConCuentasParaSinFormatear")
    public RespuestaWebTO obtenerConCuentasParaSinFormatear(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            List<ConCuentasTO> respues = cuentasService.obtenerConCuentasParaSinFormatear(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados de plan de cuentas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Obtener datos necesarios para crear contables*/
    @RequestMapping("/obtenerDatosParaCrudContable")
    public RespuestaWebTO obtenerDatosParaCrudContable(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean mostrarTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("mostrarTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerDatosParaCrudContable(empresa, mostrarTodos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados para trabajar contables");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*PLAN DE CUENTAS*/
    @RequestMapping("/tamanioPlanCuentas")
    public RespuestaWebTO tamanioPlanCuentas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            List<ConCuentasTO> respues = cuentasService.getListaConCuentasTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.size());
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de plan de cuentas");
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

    /*Obtener datos necesarios para crear contables*/
    @RequestMapping("/obtenerDatosParaPlanContable")
    public RespuestaWebTO obtenerDatosParaPlanContable(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = cuentasService.obtenerDatosParaPlanContable(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados para trabajar plan contables");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerConCuenta")
    public ResponseEntity obtenerConCuenta(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String codigo = SistemaWebUtil.obtenerFiltroComoString(parametros, "codigo");
        try {
            ConCuentas respues = cuentasService.obtenerConCuenta(empresa, codigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro plan de cuenta");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/obtenerConCuentaProductoCategoria")
    public ResponseEntity obtenerConCuentaProductoCategoria(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String codigo = SistemaWebUtil.obtenerFiltroComoString(parametros, "codigoCategoria");
        try {
            Map<String, Object> respues = cuentasService.obtenerConCuentaProductoCategoria(empresa, codigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro plan de cuenta");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/insertarConCuenta")
    public ResponseEntity insertarConCuenta(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String isOk;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasTO conCuentasTO = UtilsJSON.jsonToObjeto(ConCuentasTO.class, map.get("conCuentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (!cuentasService.validarCuentaGrupoDetalle(conCuentasTO, sisInfoTO)) {
                resp.setOperacionMensaje("No se puede Ingresar una cuenta detalle sin una cuenta de grupo.");
            } else {
                if (cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo())) == null) {
                    isOk = cuentasService.insertarConCuenta(conCuentasTO, sisInfoTO);
                    if (isOk.substring(0, 1).equalsIgnoreCase("T")) {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        resp.setOperacionMensaje("Se guardó plan de cuenta de manera exitosa");
                        resp.setExtraInfo(cuentasService.obtenerConCuentaTO(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()));
                    } else {
                        resp.setOperacionMensaje("No se logró guardar plan de cuenta");
                    }

                } else {
                    resp.setOperacionMensaje("El plan de cuentas que desea ingresar ya existe");
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/insertarListadoConCuentaTO")
    public RespuestaWebTO insertarListaConCuentaTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ConCuentasTO> listadoConCuentasTO = UtilsJSON.jsonToList(ConCuentasTO.class, map.get("listadoConCuentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Boolean respuesta;
        try {
            respuesta = cuentasService.insertarListaConCuentaTO(listadoConCuentasTO, empresa, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
                resp.setOperacionMensaje("Se guardó plan de cuenta de manera exitosa");
            } else {
                resp.setOperacionMensaje("No se logró guardar plan de cuenta");
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

    @RequestMapping("/getConteoConCuentasTO")
    public RespuestaWebTO getConteoConCuentasTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            int respues = cuentasService.getConteoCuentasTO(empresa);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarConCuenta")
    public ResponseEntity modificarConCuenta(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String isOk;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasTO conCuentasTO = UtilsJSON.jsonToObjeto(ConCuentasTO.class, map.get("conCuentasTO"));
        String codigoCambiarLlave = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCambiarLlave"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (conCuentaDao.existe(ConCuentas.class, new ConCuentasPK(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()))) {
                isOk = cuentasService.modificarConCuenta(conCuentasTO, codigoCambiarLlave, sisInfoTO);
                if (isOk.substring(0, 1).equalsIgnoreCase("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje("Se modificó plan de cuenta de manera exitosa");
                    resp.setExtraInfo(cuentasService.obtenerConCuentaTO(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()));
                } else {
                    resp.setOperacionMensaje("No se logró modificar plan de cuenta");
                }
            } else {
                resp.setOperacionMensaje("El plan de cuentas que desea modificar ya no existe");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/modificarEstadoConCuenta")
    public ResponseEntity modificarEstadoConCuenta(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        ConCuentasTO conCuentasTO = UtilsJSON.jsonToObjeto(ConCuentasTO.class, map.get("conCuentasTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            MensajeTO respues = cuentasService.modificarEstadoConCuenta(conCuentasTO, estado, sisInfoTO);
            if (respues.getMensaje().equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se modificó el estado del plan de cuenta de manera exitosa");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se pudo modificar el estado del plan de cuenta");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/modificarEstadoBloqueoConCuenta")
    public ResponseEntity modificarEstadoBloqueoConCuenta(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        ConCuentasTO conCuentasTO = UtilsJSON.jsonToObjeto(ConCuentasTO.class, map.get("conCuentasTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            MensajeTO respues = cuentasService.modificarEstadoBloqueoConCuenta(conCuentasTO, estado, sisInfoTO);
            if (respues.getMensaje().equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se modificó el estado del plan de cuenta de manera exitosa");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se pudo modificar el estado del plan de cuenta");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/eliminarConCuenta")
    public RespuestaWebTO eliminarConCuenta(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasTO conCuentasTO = UtilsJSON.jsonToObjeto(ConCuentasTO.class, map.get("conCuentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String mensaje = cuentasService.eliminarConCuenta(conCuentasTO, sisInfoTO);
            if (mensaje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se eliminó plan de cuenta de manera exitosa");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarTodoConCuenta")
    public RespuestaWebTO eliminarTodoConCuenta(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            String respues = cuentasService.eliminarTodoConCuentas(empresa);
            if (respues != null && respues.length() > 0 && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(true);
            } else if (respues != null && respues.length() > 0 && respues.charAt(0) == 'F') {
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
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

    @RequestMapping("/buscarConteoDetalleEliminarCuenta")
    public int buscarConteoDetalleEliminarCuenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String cuentaCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.buscarConteoDetalleEliminarCuenta(empCodigo, cuentaCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return 0;
    }

    @RequestMapping("/exportarReporteConCuentasTO")
    public @ResponseBody
    String exportarReporteConCuentasTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConCuentasTO> listado = UtilsJSON.jsonToList(ConCuentasTO.class, map.get("ConCuentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteConCuenta(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarPlantillaConCuentasTO")
    public @ResponseBody
    String exportarPlantillaConCuentasTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConCuentasTO> listado = UtilsJSON.jsonToList(ConCuentasTO.class, map.get("ConCuentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.exportarPlantillaConCuentasTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteTXTConCuentasTO")
    public @ResponseBody
    void exportarReporteTXTConCuentasTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ConCuentasTO> listado = UtilsJSON.jsonToList(ConCuentasTO.class, map.get("ConCuentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            reporteContabilidadService.generarTXT(listado, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
    }

    /*TIPO CONTABLE*/
    @RequestMapping("/getConTipo")
    public ResponseEntity getConTipo(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<ConTipoTO> respues = tipoService.getListaConTipoTO(sisInfoTO.getEmpresa());
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron plan de cuentas");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getListaConTipoCodigo")
    public ResponseEntity getConTipoFiltro(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String codigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigo"));
        try {
            List<ConTipoTO> respues = tipoService.getListaConTipoCodigo(sisInfoTO.getEmpresa(), codigo);
            if (respues != null && !respues.isEmpty()) {
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

    @RequestMapping("/insertarConTipo")
    public ResponseEntity insertarConTipo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConTipoTO conTipoTO = UtilsJSON.jsonToObjeto(ConTipoTO.class, map.get("conTipoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(conTipoTO.getEmpCodigo(), conTipoTO.getTipCodigo())) == null) {
                MensajeTO tipo = tipoService.insertarConTipo(conTipoTO, sisInfoTO);
                if (tipo.getMensaje().equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje("El tipo contable: Código " + conTipoTO.getTipCodigo() + ", se ha guardado correctamente.");
                    resp.setExtraInfo(true);
                } else {
                    resp.setOperacionMensaje("No se logró guardar el tipo contable");
                }
            } else {
                resp.setOperacionMensaje("El tipo contable que desea ingresar ya existe");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/modificarConTipo")
    public ResponseEntity modificarConTipo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConTipoTO conTipoTO = UtilsJSON.jsonToObjeto(ConTipoTO.class, map.get("conTipoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (conTipoDao.existe(ConTipo.class, new ConTipoPK(conTipoTO.getEmpCodigo(), conTipoTO.getTipCodigo()))) {
                MensajeTO tipo = tipoService.modificarConTipo(conTipoTO, sisInfoTO);
                if (tipo.getMensaje().equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje("El tipo contable: Código " + conTipoTO.getTipCodigo() + ", se ha modificado correctamente.");
                    resp.setExtraInfo(true);
                } else {
                    resp.setOperacionMensaje("No se logró modificar el tipo contable");
                }
            } else {
                resp.setOperacionMensaje("El tipo contable que desea modificar ya no existe");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/modificarEstadoConTipo")
    public ResponseEntity modificarEstadoInvPedidosMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        ConTipoTO conTipoTO = UtilsJSON.jsonToObjeto(ConTipoTO.class, map.get("conTipoTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            MensajeTO respues = tipoService.modificarEstadoConTipo(conTipoTO, estado, sisInfoTO);
            if (respues.getMensaje().equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se modificó el estado del tipo contable de manera exitosa");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se pudo modificar el estado del tipo contable");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/eliminarConTipo")
    public ResponseEntity eliminarConTipo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConTipoTO conTipoTO = UtilsJSON.jsonToObjeto(ConTipoTO.class, map.get("conTipoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO tipo = tipoService.eliminarConTipo(conTipoTO, sisInfoTO);
            if (tipo.getMensaje().equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El tipo contable: Código " + conTipoTO.getTipCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(tipo);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(tipo.getMensaje().substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/exportarReporteConTipoTO")
    public @ResponseBody
    String exportarReporteTipoContable(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConTipoTO> listado = UtilsJSON.jsonToList(ConTipoTO.class, map.get("ConTipoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteConTipoTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirReporteConTipoTO")
    public @ResponseBody
    String generarReporteProveedor(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportTipoContable.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConTipoTO> listado = UtilsJSON.jsonToList(ConTipoTO.class, parametros.get("listadoTipos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteTipoContable(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*NUMERACIONES*/
    @RequestMapping("/getListaConNumeracionTO")
    public ResponseEntity getListaConNumeracionTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConNumeracionTO> respues = numeracionService.getListaConNumeracionTO(empresa, periodo, tipo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron numeraciones");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    /*Listar ppp Errores compras*/
    @RequestMapping("/listarPppErroresCompra")
    public RespuestaWebTO listarPppErroresCompra(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<InventarioProductosEnProcesoErroresCompra> respues = productosEnProcesoErroresService.listarPppErroresCompra(empresa, desde, hasta);
            if (respues != null && !respues.isEmpty()) {
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

    /*Listar ppp Errores consumos*/
    @RequestMapping("/listarPppErroresConsumos")
    public RespuestaWebTO listarPppErroresConsumos(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<InventarioProductosEnProcesoErroresConsumo> respues = productosEnProcesoErroresService.listarPppErroresConsumo(empresa, desde, hasta);
            if (respues != null && !respues.isEmpty()) {
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

    /*MAYOR AUXILIAR*/
    @RequestMapping("/extraerDatosMayorAuxiliar")
    public RespuestaWebTO extraerDatosMayorAuxiliar(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = contableService.obtenerDatosMayorAuxiliar(map);
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

    @RequestMapping("/getListaMayorAuxiliarTO")
    public ResponseEntity getListaMayorAuxiliarTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoCuentaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoCuentaDesde"));
        String codigoCuentaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoCuentaHasta"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConMayorAuxiliarTO> respues = contableService.getListaMayorAuxiliarTO(empresa, codigoCuentaDesde, codigoCuentaHasta, fechaInicio, fechaFin, sector, estado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron mayor auxiliares");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/exportarReporteConMayorAuxiliarTO")
    public @ResponseBody
    String exportarReporteMayorAuxiliar(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConMayorAuxiliarTO> listado = UtilsJSON.jsonToList(ConMayorAuxiliarTO.class, map.get("ConMayorAuxiliarTO"));
        String codigoCuenta = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuenta"));
        String codigoCuentaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuentaHasta"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteMayorAuxiliar(usuarioEmpresaReporteTO, listado, codigoCuenta, codigoCuentaHasta, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirReporteMayorAuxiliar")
    public @ResponseBody
    String generarReporteMayorAuxiliar(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContabilidadMayorAuxiliar.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConMayorAuxiliarTO> listado = UtilsJSON.jsonToList(ConMayorAuxiliarTO.class, parametros.get("ListaConMayorAuxiliarTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String ctaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("ctaDesde"));
        String ctaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("ctaHasta"));
        String detalleDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("detalleDesde"));
        String detalleHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("detalleHasta"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        sisInfoTO.setEmpresa(empresa);
        try {
            Map<String, Object> nombres = cuentasService.buscarNombresDesdeHastaPadre(empresa, ctaDesde, ctaHasta, sisInfoTO);
            String nombrePadresDesde = UtilsJSON.jsonToObjeto(String.class, nombres.get("nombrePadresDesde"));
            String nombrePadreHasta = UtilsJSON.jsonToObjeto(String.class, nombres.get("nombrePadreHasta"));
            ctaDesde = ctaDesde + '|' + detalleDesde;
            ctaHasta = ctaHasta != null ? ctaHasta + '|' + detalleHasta : null;
            respuesta = reporteContabilidadService.generarReporteMayorAuxiliar(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, ctaDesde, nombrePadresDesde, ctaHasta, nombrePadreHasta, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*MAYOR GENERAL*/
    @RequestMapping("/getListaMayorGeneralTO")
    public ResponseEntity getListaMayorGeneralTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String codigoCuenta = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoCuenta"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConMayorGeneralTO> respues = contableService.getListaMayorGeneralTO(empresa, codigoSector, codigoCuenta, fechaFin);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron mayor general");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteMayorGeneral")
    public @ResponseBody
    String generarReporteMayorGeneral(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContabilidadMayorGeneral.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, parametros.get("cuentaContable"));
        List<ConMayorGeneralTO> listConMayorGeneralTO = UtilsJSON.jsonToList(ConMayorGeneralTO.class, parametros.get("listConMayorGeneralTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteMayorGeneral(usuarioEmpresaReporteTO, fechaHasta, cuentaContable, listConMayorGeneralTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMayorGeneral")
    public @ResponseBody
    String exportarReporteMayorGeneral(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConMayorGeneralTO> listado = UtilsJSON.jsonToList(ConMayorGeneralTO.class, map.get("ConMayorGeneralTO"));
        String codigoCuenta = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuenta"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteMayorGeneral(usuarioEmpresaReporteTO, listado, codigoCuenta, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*VERIFICACION FECHAS COMPRAS*/
    @RequestMapping("/getConFunContablesVerificacionesComprasTOs")
    public ResponseEntity getConFunContablesVerificacionesComprasTOs(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConFunContablesVerificacionesComprasTO> respues = contableService.getConFunContablesVerificacionesComprasTOs(empresa, fechaInicio, fechaFin);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron registros");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteContablesVerificacionesCompras")
    public @ResponseBody
    String generarReporteContablesVerificacionesCompras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reporteFunContablesVerificacionesCompras.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<ConFunContablesVerificacionesComprasTO> listVerificacionesComprasTO = UtilsJSON.jsonToList(ConFunContablesVerificacionesComprasTO.class, parametros.get("listConFunContablesVerificacionesComprasTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteContablesVerificacionesCompras(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listVerificacionesComprasTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteContablesVerificacionesCompras")
    public @ResponseBody
    String exportarReporteContablesVerificacionesCompras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConFunContablesVerificacionesComprasTO> listado = UtilsJSON.jsonToList(ConFunContablesVerificacionesComprasTO.class, map.get("ConFunContablesVerificacionesComprasTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteContablesVerificacionesCompras(usuarioEmpresaReporteTO, listado, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*CONTABLE*/
    @RequestMapping("/getListaConContableTO")
    public ResponseEntity getListaConContableTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("perCodigo"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipCodigo"));
        String numContable = UtilsJSON.jsonToObjeto(String.class, parametros.get("numContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConContableTO> listadoConContableTO = contableService.getListaConContableTO(empresa, perCodigo, tipCodigo, numContable);
            if (listadoConContableTO.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(listadoConContableTO);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro contables");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getListConContableTO")
    public ResponseEntity getListConContableTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));
        String referencia = UtilsJSON.jsonToObjeto(String.class, parametros.get("referencia"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, parametros.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ItemStatusItemListaConContableTO> listadoContablesTO = contableService.getListConContableTOConStatus(empresa, periodo, tipo, busqueda, referencia, nRegistros);
            if (listadoContablesTO.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(listadoContablesTO);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro contables");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getConContable")
    public ResponseEntity getConContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("conContablePK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            ConContable respues = contableService.getConContable(conContablePK);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro el contable");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/validarChequeContable")
    public ResponseEntity validarChequeContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        ConListaContableDetalleTO detalleEvaluar = UtilsJSON.jsonToObjeto(ConListaContableDetalleTO.class, parametros.get("ConListaContableDetalleTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        boolean isCorrecto = false;
        try {
            isCorrecto = contableService.validarCuentaBancos(detalleEvaluar, empresa);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje("");
            resp.setExtraInfo(isCorrecto);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/verificarDocumentoBanco")
    public RespuestaWebTO verificarDocumentoBanco(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String documento = UtilsJSON.jsonToObjeto(String.class, parametros.get("documento"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, parametros.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        boolean isCorrecto = false;
        try {
            isCorrecto = contableService.verificarDocumentoBanco(documento, cuenta, empresa);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(isCorrecto);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarContable")
    public ResponseEntity insertarContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        ConContable contable = UtilsJSON.jsonToObjeto(ConContable.class, parametros.get("conContable"));
        List<ConListaContableDetalleTO> detalle = UtilsJSON.jsonToList(ConListaContableDetalleTO.class, parametros.get("detalle"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, parametros.get("listaImagenes"));
        ConContable conContable = contableService.obtenerConContableConDetalle(contable, detalle);
        List<ConDetalle> listaConDetalle = conContable.getConDetalleList();
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            boolean pendiente = conContable.getConPendiente();
            boolean reversado = conContable.getConReversado();
            mensajeTO = contableService.insertarModificarContable(conContable, listaConDetalle, listadoImagenes, sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!pendiente && !reversado) {
                    contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                    conContable.setConPendiente(false);
                }
//                contableService.insertarImagenesContables(listadoImagenes, conContable.getConContablePK());
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1, mensajeTO.getMensaje().length()));
                resp.setExtraInfo(conContable);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1, mensajeTO.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("F" + e.getMessage());
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/modificarContable")
    public ResponseEntity modificarContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        ConContable contable = UtilsJSON.jsonToObjeto(ConContable.class, parametros.get("conContable"));
        List<ConListaContableDetalleTO> detalle = UtilsJSON.jsonToList(ConListaContableDetalleTO.class, parametros.get("detalle"));
        ConContable conContable = contableService.obtenerConContableConDetalle(contable, detalle);
        List<ConDetalle> listaConDetalle = conContable.getConDetalleList();
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, parametros.get("listadoImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            boolean pendiente = conContable.getConPendiente();
            boolean reversado = conContable.getConReversado();
            mensajeTO = contableService.insertarModificarContable(conContable, listaConDetalle, listadoImagenes, sisInfoTO);
            //ES PARA MAYORIZAR
            if (mensajeTO.getMensaje().charAt(0) == 'T' && !pendiente && !reversado) {
                mensajeTO.setMensaje(contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO));
            }
            if (mensajeTO.getMensaje().charAt(0) == 'T') {
                contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1, mensajeTO.getMensaje().length()));
                resp.setExtraInfo(contableService.getConContable(conContable.getConContablePK()));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1, mensajeTO.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("F" + e.getMessage());
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/desmayorizarConContable")
    public RespuestaWebTO desmayorizarConContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("conContablePK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String respues = "";
        try {
            respues = contableService.mayorizarDesmayorizarSql(conContablePK, true, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                ConContable contable = contableService.getConContable(conContablePK);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(contable);
                contableService.crearSuceso(conContablePK, "U", sisInfoTO);

                if (contable.getConReferencia() != null && contable.getConReferencia().equals("recursoshumanos.rh_liquidacion")) {
                    List<RhRol> roles = rolService.getListRhRol(conContablePK);
                    RhEmpleado empleado = roles.get(0).getRhEmpleado();
                    String codigoEmpId = empleado.getRhEmpleadoPK().getEmpId();
                    empleado.setEmpInactivo(false);

                    if (empleado.getEmpFechaUltimaSalida() == null && empleado.getEmpFechaPrimeraSalida() != null) {
                        empleado.setEmpFechaPrimeraSalida(null);
                    }
                    empleado.setEmpFechaUltimaSalida(null);
                    empleado.setEmpMotivoSalida(null);
                    List<RhEmpleadoDescuentosFijos> descuentos = empleadoDescuentoFijoService.getEmpleadoDescuentosFijos(conContablePK.getConEmpresa(), codigoEmpId);
                    MensajeTO mensajeTO = empleadoService.insertarModificarRhEmpleado(empleado, descuentos, sisInfoTO);
                    if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        resp.setExtraInfo(contable);
                        resp.setOperacionMensaje(respues.substring(1, respues.length()));
                    } else {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                        resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha actualizado datos del trabajador.");
                    }
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarListaConContable")
    public RespuestaWebTO desmayorizarListaConContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        List<ConContablePK> listadoConContablePK = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoConContablePK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String respues;
        List<RespuestaWebTO> listaRes = new ArrayList<>();
        try {
            for (ConContablePK conContablePK : listadoConContablePK) {
                respues = contableService.mayorizarDesmayorizarSql(conContablePK, true, sisInfoTO);
                RespuestaWebTO respuestaWT = new RespuestaWebTO();
                if (respues.charAt(0) == 'T') {
                    respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    respuestaWT.setOperacionMensaje(respues.substring(1));
                    respuestaWT.setExtraInfo(contableService.getConContable(conContablePK));
                    contableService.crearSuceso(conContablePK, "U", sisInfoTO);
                } else {
                    respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    respuestaWT.setOperacionMensaje(respues.substring(1));
                }
                listaRes.add(respuestaWT);
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje("La operación de desmayorizar finalizó correctamente.");
            resp.setExtraInfo(listaRes);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desbloquearListaConContable")
    public RespuestaWebTO desbloquearListaConContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        List<ConContablePK> listadoConContablePK = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoConContablePK"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, parametros.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        List<RespuestaWebTO> listaRes = new ArrayList<>();
        String respues;
        try {
            for (ConContablePK conContablePK : listadoConContablePK) {
                RespuestaWebTO respuestaWT = new RespuestaWebTO();
                respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                ConContable conContable = contableService.getConContable(conContablePK);
                respues = periodoService.validarPeriodo(conContable.getConFecha(), conContablePK.getConEmpresa());
                if (respues == null) {// SI ES NULL, PERIODO ESTA ABIERTO
                    respues = contableService.desbloquearConContable(conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(), conContablePK.getConNumero(), usuario, sisInfoTO);
                    if (respues.charAt(0) == 'T') {
                        respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        respuestaWT.setOperacionMensaje(respues.substring(1));
                        respuestaWT.setExtraInfo(conContable);
                        contableService.crearSuceso(conContablePK, "U", sisInfoTO);
                    } else {
                        respuestaWT.setOperacionMensaje(respues.substring(1));
                    }
                } else {
                    respuestaWT.setOperacionMensaje(respues.substring(1));
                }
                listaRes.add(respuestaWT);
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje("La operación de delbloqueo finalizó correctamente.");
            resp.setExtraInfo(listaRes);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularListaConContable")
    public RespuestaWebTO anularListaConContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        List<ConContablePK> listadoConContablePK = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoConContablePK"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, parametros.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        List<RespuestaWebTO> listaRes = new ArrayList<>();
        String respues;
        try {
            for (ConContablePK conContablePK : listadoConContablePK) {
                RespuestaWebTO respuestaWT = new RespuestaWebTO();
                try {
                    MensajeTO mensajeTO = contableService.validarContables(conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(), conContablePK.getConNumero(), "anulae", "");
                    if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                        respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                        ConContable conContable = contableService.getConContable(conContablePK);
                        respues = periodoService.validarPeriodo(conContable.getConFecha(), conContablePK.getConEmpresa());
                        if (respues == null) {// SI ES NULL, PERIODO ESTA ABIERTO
                            respues = contableService.anularConContable(conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(), conContablePK.getConNumero(), usuario, sisInfoTO);
                            if (respues.charAt(0) == 'T') {
                                respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                                respuestaWT.setOperacionMensaje(respues.substring(1));
                                respuestaWT.setExtraInfo(conContable);
                                contableService.crearSuceso(conContablePK, "U", sisInfoTO);
                            } else {
                                respuestaWT.setOperacionMensaje(respues.substring(1));
                            }
                        } else {
                            respuestaWT.setOperacionMensaje(respues.substring(1));
                        }
                    } else {
                        respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                        respuestaWT.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "Ocurrió un error al validar contables.");
                    }
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    respuestaWT.setOperacionMensaje(e != null && e.getMessage() != null
                            ? e.getMessage() : "Ocurrió un error al validar contables.");
                }

                listaRes.add(respuestaWT);
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje("La operación de anulación finalizó correctamente.");
            resp.setExtraInfo(listaRes);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarListaConContable")
    public RespuestaWebTO eliminarListaConContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        List<ConContablePK> listadoConContablePK = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoConContablePK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String respues;
        List<RespuestaWebTO> listaRes = new ArrayList<>();
        try {
            for (ConContablePK conContablePK : listadoConContablePK) {
                respues = contableService.eliminarConContable(conContablePK, sisInfoTO);
                RespuestaWebTO respuestaWT = new RespuestaWebTO();
                if (respues.charAt(0) == 'T') {
                    respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    respuestaWT.setOperacionMensaje(respues.substring(1));
                    respuestaWT.setExtraInfo(conContablePK);
                } else {
                    respuestaWT.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    respuestaWT.setOperacionMensaje(respues.substring(1));
                }
                listaRes.add(respuestaWT);
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje("La eliminación finalizó correctamente.");
            resp.setExtraInfo(listaRes);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularReversarConContable")
    public ResponseEntity anularReversarConContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        StringBuilder mensaje = new StringBuilder();
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("conContablePK"));
        boolean anularReversar = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("anularReversar"));//true->anular,false->reversar
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String retorno = "";
        try {
            retorno = contableService.anularReversarSql(conContablePK, anularReversar, sisInfoTO);
            mensaje.append("\n").append(retorno.substring(1, retorno.length()));
            if (retorno.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(retorno.substring(1, retorno.length()));
                resp.setExtraInfo(contableService.getConContable(conContablePK));
                contableService.crearSuceso(conContablePK, "U", sisInfoTO);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(retorno.substring(1, retorno.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/validarContablesParaAnularReversar")
    public RespuestaWebTO validarContablesParaAnularReversar(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        boolean anularReversar = UtilsJSON.jsonToObjeto(boolean.class, map.get("anularReversar"));//true->anular,false->reversar
        String accionUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("accionUsuario"));
        String bandera = UtilsJSON.jsonToObjeto(String.class, map.get("bandera"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contableService.validarContables(conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(), conContablePK.getConNumero(), accionUsuario, bandera);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                String retorno = contableService.anularReversarSql(conContablePK, anularReversar, sisInfoTO);

                if (conContablePK.getConTipo() != null && conContablePK.getConTipo().equals("C-ACLI") && anularReversar) {
                    CarCobrosAnticiposPK pk = new CarCobrosAnticiposPK(
                            conContablePK.getConEmpresa(),
                            conContablePK.getConPeriodo(),
                            conContablePK.getConTipo(),
                            conContablePK.getConNumero());
                    CarCobrosAnticipos carCobrosAnticipos = cobrosAnticiposDao.obtenerPorId(CarCobrosAnticipos.class, pk);
                    if (carCobrosAnticipos != null && carCobrosAnticipos.getConContableDeposito() != null) {
                        List<CarCobrosDetalleFormaPostfechadoTO> listaAnt = new ArrayList<>();
                        CarCobrosDetalleFormaPostfechadoTO ant = new CarCobrosDetalleFormaPostfechadoTO();
                        String pkAnticipo = pk.getAntEmpresa() + "|" + pk.getAntPeriodo() + "|" + pk.getAntTipo() + "|" + pk.getAntNumero();
                        ant.setPk(pkAnticipo);//empresa|periodo|tipo|numero
                        listaAnt.add(ant);
                        chequePostfechadoService.actualizarContableAnticipos(listaAnt, null, false, null, sisInfoTO);//eliminar contable de anticipo asociado y cuenta
                    }
                }

                if (retorno != null && retorno.charAt(0) == 'T') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(retorno.substring(1, retorno.length()));
                    resp.setExtraInfo(contableService.getConContable(conContablePK));
                    contableService.crearSuceso(conContablePK, "U", sisInfoTO);
                } else {
                    resp.setOperacionMensaje(retorno != null ? retorno.substring(1, retorno.length()) : "Ocurrió un error al anular/reservar contables");
                }
            } else {
                String mensaje = "";
                if (mensajeTO != null && mensajeTO.getListaErrores1() != null) {
                    for (int i = 0; i < mensajeTO.getListaErrores1().size(); i++) {
                        mensaje = mensaje + "<br>" + mensajeTO.getListaErrores1().get(i);
                    }
                }
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null
                        ? mensajeTO.getMensaje().substring(1) + mensaje : "Ocurrió un error al validar contables.");
            }

        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/restaurarConContable")
    public ResponseEntity restaurarConContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        StringBuilder mensaje = new StringBuilder();
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("conContablePK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            String retorno = contableService.restaurarSql(conContablePK);
            mensaje.append("\n").append(retorno.substring(1, retorno.length()));
            if (retorno.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(retorno.substring(1, retorno.length()));
                resp.setExtraInfo(contableService.getConContable(conContablePK));
                contableService.crearSuceso(conContablePK, "U", sisInfoTO);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(retorno.substring(1, retorno.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/desbloquearConContable")
    public RespuestaWebTO desbloquearConContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("conContablePK"));
        ConContable conContable = contableService.getConContable(conContablePK);
        String usuario = UtilsJSON.jsonToObjeto(String.class, parametros.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        String respuestaString = "";
        try {
            respuestaString = periodoService.validarPeriodo(conContable.getConFecha(), conContablePK.getConEmpresa());
            if (respuestaString == null) {// SI ES NULL, PERIODO ESTA ABIERTO
                respuestaString = contableService.desbloquearConContable(conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(), conContablePK.getConNumero(), usuario, sisInfoTO);
                if ("T".equals(respuestaString.substring(0, 1))) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(contableService.getConContable(conContablePK));
                }
            }
            resp.setOperacionMensaje(respuestaString.substring(1, respuestaString.length()));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaConDetalleTO")
    public List<ConDetalleTablaTO> getListaConDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.getListaConDetalleTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/consultaContable")
    public ResponseEntity consultaContable(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("conContablePK"));
        boolean mostrarSoloActivos = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("mostrarSoloActivos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            ItemListaContableTO respuesta = contableService.obtenerContableConDetalle(conContablePK, mostrarSoloActivos);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro contable");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteContableDetalle")
    public @ResponseBody
    String generarReporteContableDetalle(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteContable.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<ConContablePK> listadoContablePK = contableService.obtenerListadoContablePK(listado);
            if (!listadoContablePK.isEmpty()) {
                List<ConContableReporteTO> listadoEnviar = reporteContabilidadService.generarListaConContableReporteTO(listadoContablePK, sisInfoTO);
                respuesta = reporteContabilidadService.generarReporteContableDetalle(usuarioEmpresaReporteTO, listadoEnviar);
                return archivoService.generarReportePDF(respuesta, nombreReporte, response);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteContableIndividual")
    public @ResponseBody
    String generarReporteContableIndividual(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteContable.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> listadoContablePK = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoPK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            if (!listadoContablePK.isEmpty()) {
                List<ConContableReporteTO> listadoEnviar = reporteContabilidadService.generarListaConContableReporteTO(listadoContablePK, sisInfoTO);
                respuesta = reporteContabilidadService.generarReporteContableDetalle(usuarioEmpresaReporteTO, listadoEnviar);
                return archivoService.generarReportePDF(respuesta, nombreReporte, response);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteContableIndividualMatricial")
    public RespuestaWebTO generarReporteContableIndividualMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConContablePK> listadoContablePK = UtilsJSON.jsonToList(ConContablePK.class, parametros.get("listadoPK"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            if (!listadoContablePK.isEmpty()) {
                List<ConContableReporteTO> listadoEnviar = reporteContabilidadService.generarListaConContableReporteTO(listadoContablePK, sisInfoTO);
                respuesta = reporteContabilidadService.generarReporteContableDetalle(usuarioEmpresaReporteTO, listadoEnviar);
                resp.setExtraInfo(respuesta);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerImagenesContable")
    public RespuestaWebTO obtenerImagenesContable(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConAdjuntosContableWebTO> respues = contableService.convertirStringUTF8(conContablePK);
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

    /*DIARIO AUXILIAR CUENTA*/
    @RequestMapping("/getListaDiarioAuxiliarTO")
    public ResponseEntity getListaDiarioAuxiliarTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoTipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoTipo"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConDiarioAuxiliarTO> respues = contableService.getListaDiarioAuxiliarTO(empresa, codigoTipo, fechaInicio, fechaFin);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron diarios de auxiliar");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteDiarioAuxiliar")
    public @ResponseBody
    String generarReporteDiarioAuxiliar(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContabilidadDiarioGeneral.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String codigoTipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoTipo"));
        List<ConDiarioAuxiliarTO> listado = UtilsJSON.jsonToList(ConDiarioAuxiliarTO.class, parametros.get("listConDiarioAuxiliarTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteDiarioAuxiliar(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, codigoTipo, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteDiarioAuxiliar")
    public @ResponseBody
    String exportarReporteDiarioAuxiliar(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConDiarioAuxiliarTO> listado = UtilsJSON.jsonToList(ConDiarioAuxiliarTO.class, map.get("ConFunContablesVerificacionesComprasTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteDiarioAuxiliar(usuarioEmpresaReporteTO, listado, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*VERIFICACION DE CONTABLES*/
    @RequestMapping("/getFunContablesVerificacionesTO")
    public ResponseEntity getFunContablesVerificacionesTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerListasVerificacionConErrores(map);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron verificaciones de contables");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/enviarPorCorreoErroresDeContabilidad")
    public ResponseEntity enviarPorCorreoErroresDeContabilidad(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> respues = contableService.enviarPorCorreoErroresDeContabilidad(map, usuarioEmpresaReporteTO, sisInfoTO);
            if (respues != null && respues.get(0) != null && respues.get(0).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Envio de correo exitoso.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues != null ? respues.get(0) : "No se enviaron los correos electrónicos.");
                resp.setExtraInfo(respues);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteContablesVerificacionesErrores")
    public @ResponseBody
    String generarReporteContablesVerificacionesErrores(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "ReporteContablesVerificacionesErrores.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO = UtilsJSON.jsonToList(ConFunContablesVerificacionesTO.class, parametros.get("listConFunContablesVerificacionesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteContablesVerificacionesErrores(usuarioEmpresaReporteTO, listConFunContablesVerificacionesTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteProductosInconsistencias")
    public @ResponseBody
    String generarReporteProductosInconsistencias(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "ReporteProductosInconsistencias.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InventarioProductosCuentasInconsistentes> listado = UtilsJSON.jsonToList(InventarioProductosCuentasInconsistentes.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteProductosInconsistencias(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteProductosInconsistenciasMatricial")
    public RespuestaWebTO generarReporteProductosInconsistenciasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InventarioProductosCuentasInconsistentes> listado = UtilsJSON.jsonToList(InventarioProductosCuentasInconsistentes.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteProductosInconsistencias(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteProductosInconsistencias")
    public @ResponseBody
    String exportarReporteProductosInconsistencias(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InventarioProductosCuentasInconsistentes> listado = UtilsJSON.jsonToList(InventarioProductosCuentasInconsistentes.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.exportarReporteProductosInconsistencias(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsumoErrores")
    public @ResponseBody
    String generarReporteConsumoErrores(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "ReporteConsumoVerificacionesErrores.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InventarioProductosEnProcesoErroresConsumo> listConFunConsumoVerificacionesTO = UtilsJSON.jsonToList(InventarioProductosEnProcesoErroresConsumo.class, parametros.get("listConFunConsumoVerificacionesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteConsumosVerificacionesErrores(usuarioEmpresaReporteTO, listConFunConsumoVerificacionesTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsumoErroresMatricial")
    public RespuestaWebTO generarReporteConsumoErroresMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InventarioProductosEnProcesoErroresConsumo> listConFunConsumoVerificacionesTO = UtilsJSON.jsonToList(InventarioProductosEnProcesoErroresConsumo.class, parametros.get("listConFunConsumoVerificacionesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteConsumosVerificacionesErrores(usuarioEmpresaReporteTO, listConFunConsumoVerificacionesTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteContablesVerificacionesErrores")
    public @ResponseBody
    String exportarReporteContablesVerificacionesErrores(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConFunContablesVerificacionesTO> listado = UtilsJSON.jsonToList(ConFunContablesVerificacionesTO.class, map.get("listConFunContablesVerificacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteContablesVerificacionesErroresExcel(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConsumoErrores")
    public @ResponseBody
    String exportarReporteConsumoErrores(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InventarioProductosEnProcesoErroresConsumo> listado = UtilsJSON.jsonToList(InventarioProductosEnProcesoErroresConsumo.class, map.get("listConFunConsumoVerificacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.exportarReporteConsumoErrores(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*VERIFICACION DE CUENTAS SOBREGIRADAS*/
    @RequestMapping("/getFunCuentasSobregiradasTO")
    public ResponseEntity getFunCuentasSobregiradasTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("secCodigo"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConFunBalanceGeneralNecTO> respues = contableService.getFunCuentasSobregiradasTO(empresa, secCodigo, fecha);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron cuentas sobregiradas");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    //balanceGeneral
    @RequestMapping("/generarReporteBalanceGeneral")
    public @ResponseBody
    String generarReporteBalanceGeneral(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContabilidadBalanceGeneral.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        List<ConBalanceGeneralTO> listConBalanceGeneralTO = UtilsJSON.jsonToList(ConBalanceGeneralTO.class, parametros.get("listConBalanceGeneralTO"));
        List<ConFunBalanceGeneralNecTO> listConFunBalanceGeneralNecTO = contableService.convertirConBalanceGeneralTO_ConFunBalanceGeneralNecTO(listConBalanceGeneralTO);
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceGeneral(usuarioEmpresaReporteTO, fechaHasta, codigoSector, listConFunBalanceGeneralNecTO, listConBalanceGeneralTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteBalanceGeneralTO")
    public @ResponseBody
    String generarReporteBalanceGeneralTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContabilidadBalanceGeneral.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        List<ConFunBalanceGeneralNecTO> listConBalanceGeneralNecTO = UtilsJSON.jsonToList(ConFunBalanceGeneralNecTO.class, parametros.get("listConBalanceGeneralNecTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceGeneralNecTO(usuarioEmpresaReporteTO, fechaHasta, codigoSector, listConBalanceGeneralNecTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteBalanceGeneral")
    public @ResponseBody
    String exportarReporteBalanceGeneral(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConFunBalanceGeneralNecTO> listado = UtilsJSON.jsonToList(ConFunBalanceGeneralNecTO.class, map.get("listConBalanceGeneralTO"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.exportarReporteConCuentasSobregiradasTO(usuarioEmpresaReporteTO, listado, fecha, codigoSector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*ESTADO DE RESULTADOS INTEGRAL MENSUALIZADO*/
    @RequestMapping("/getBalanceResultadoMensualizado")
    public ResponseEntity getBalanceResultadoMensualizado(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, parametros.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConBalanceResultadosMensualizadosTO> respues = contableService.getBalanceResultadoMensualizado(empresa, codigoSector, fechaInicio, fechaFin, usuario, sisInfoTO);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estados de resultado integral mensualizado");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getBalanceResultadoMensualizadoAntiguo")
    public ResponseEntity getBalanceResultadoMensualizadoAntiguo(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, parametros.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            RetornoTO respues = contableService.getBalanceResultadoMensualizadoAntiguo(empresa, codigoSector, fechaInicio, fechaFin, usuario, sisInfoTO);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estados de resultado integral mensualizado");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteMayorGeneralDatos")
    public @ResponseBody
    String generarReporteMayorGeneralDatos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContabilidadMayorGeneral.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, parametros.get("cuentaContable"));
        Object[][] datos = UtilsJSON.jsonToObjeto(Object[][].class, parametros.get("datos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteMayorGeneral(usuarioEmpresaReporteTO, fechaHasta, cuentaContable, datos);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteBalanceResultadoMensualizado")
    public @ResponseBody
    String exportarReporteBalanceResultadoMensualizado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConBalanceResultadosMensualizadosTO> lista = UtilsJSON.jsonToList(ConBalanceResultadosMensualizadosTO.class, map.get("lista"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceResultadoMensualizado(usuarioEmpresaReporteTO, lista, fechaInicio, fechaFin, codigoSector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteBalanceResultadoMensualizadoAntiguo")
    public @ResponseBody
    String exportarReporteBalanceResultadoMensualizadoAntiguo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        Object[][] datos = UtilsJSON.jsonToObjeto(Object[][].class, map.get("datos"));
        List<String> columnas = UtilsJSON.jsonToObjeto(List.class, map.get("columnas"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceResultadoMensualizadoAntiguo(usuarioEmpresaReporteTO, datos, columnas, codigoSector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*ESTADO DE COMPROBACIÓN*/
    @RequestMapping("/getListaBalanceComprobacionTO")
    public ResponseEntity getListaBalanceComprobacionTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("ocultarDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConBalanceComprobacionTO> respues = contableService.getListaBalanceComprobacionTO(empresa, codigoSector, fechaInicio, fechaFin, ocultarDetalle);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estados de comprobación");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteBalanceComprobacion")
    public @ResponseBody
    String generarReporteBalanceComprobacion(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContabilidadBalanceComprobacion.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<ConBalanceComprobacionTO> listConBalanceComprobacionTO = UtilsJSON.jsonToList(ConBalanceComprobacionTO.class, parametros.get("listConBalanceComprobacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceComprobacion(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listConBalanceComprobacionTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteEstadosComprobacion")
    public @ResponseBody
    String exportarReporteEstadosComprobacion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<ConBalanceComprobacionTO> listConBalanceComprobacionTO = UtilsJSON.jsonToList(ConBalanceComprobacionTO.class, map.get("listConBalanceComprobacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteEstadosComprobacion(usuarioEmpresaReporteTO, listConBalanceComprobacionTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*ESTADO DE SITUACION INTEGRAL COMPARATIVO*/
    @RequestMapping("/obtenerDatosEstadoResultadoIntegralCompar")
    public RespuestaWebTO obtenerDatosEstadoResultadoIntegralCompar(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerDatosEstadoResultadoIntegralCompar(map);
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
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getConBalanceResultadoComparativoTO")
    public ResponseEntity getConBalanceResultadoComparativoTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String fechaDesde2 = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde2"));
        String fechaHasta2 = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta2"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("ocultarDetalle"));
        Boolean excluirCierre = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("excluirCierre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConBalanceResultadoComparativoTO> respues = contableService.getConBalanceResultadoComparativoTO(empresa, sector, fechaDesde, fechaHasta, fechaDesde2, fechaHasta2, ocultarDetalle, excluirCierre);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estado de situación integral comparativo");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteBalanceResultadoComparativo")
    public @ResponseBody
    String generarReporteBalanceResultadoComparativo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContabilidadBalanceResultados.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String fechaDesde2 = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde2"));
        String fechaHasta2 = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta2"));
        List<ConBalanceResultadoComparativoTO> conBalanceResultadoComparativoTO = UtilsJSON.jsonToList(ConBalanceResultadoComparativoTO.class, parametros.get("conBalanceResultadoComparativoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceResultadoComparativo(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, fechaDesde2, fechaHasta2, codigoSector, conBalanceResultadoComparativoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteEstadoResultadoIntegralComparativo")
    public @ResponseBody
    String exportarReporteEstadoResultadoIntegralComparativo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        List<ConBalanceResultadoComparativoTO> conBalanceResultadoComparativoTO = UtilsJSON.jsonToList(ConBalanceResultadoComparativoTO.class, map.get("conBalanceResultadoComparativoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteEstadoResultadoIntegralComparativo(usuarioEmpresaReporteTO, conBalanceResultadoComparativoTO, fechaDesde, fechaHasta, codigoSector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*ESTADO DE RESULTADO VS INTENTARIO*/
    @RequestMapping("/getConListaBalanceResultadosVsInventarioTO")
    public ResponseEntity getConListaBalanceResultadosVsInventarioTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConListaBalanceResultadosVsInventarioTO> respues = contableService.getConListaBalanceResultadosVsInventarioTO(empresa, desde, hasta, sector);
            if (!respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estado de resultado vs inventario");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getConListaBalanceResultadosVsInventarioDetalladoTO")
    public ResponseEntity getConListaBalanceResultadosVsInventarioDetalladoTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConBalanceResultadosVsInventarioDetalladoTO> respues = contableService.getConListaBalanceResultadosVsInventarioDetalladoTO(empresa, desde, hasta, sector, estado);
            if (!respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estado de resultado vs inventario");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteConListaBalanceResultadosVsInventario")
    public @ResponseBody
    String generarReporteConListaBalanceResultadosVsInventario(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportBalanceResultadoVSInventario.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<ConListaBalanceResultadosVsInventarioTO> listConListaBalanceResultadosVsInventarioTO = UtilsJSON.jsonToList(ConListaBalanceResultadosVsInventarioTO.class, parametros.get("listConListaBalanceResultadosVsInventarioTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteConListaBalanceResultadosVsInventario(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listConListaBalanceResultadosVsInventarioTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConBalanceResultadosVsInventarioDetalladoTO")
    public @ResponseBody
    String generarReporteConBalanceResultadosVsInventarioDetalladoTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportBalanceResultadoVSInventario.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<ConBalanceResultadosVsInventarioDetalladoTO> listConListaBalanceResultadosVsInventarioTO = UtilsJSON.jsonToList(ConBalanceResultadosVsInventarioDetalladoTO.class, parametros.get("listConListaBalanceResultadosVsInventarioTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteConBalanceResultadosVsInventarioDetalladoTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listConListaBalanceResultadosVsInventarioTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConBalanceResultadosVsInventarioDetalladoTO")
    public @ResponseBody
    String exportarReporteConBalanceResultadosVsInventarioDetalladoTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<ConBalanceResultadosVsInventarioDetalladoTO> listConListaBalanceResultadosVsInventarioTO = UtilsJSON.jsonToList(ConBalanceResultadosVsInventarioDetalladoTO.class, map.get("listConListaBalanceResultadosVsInventarioTO"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteConBalanceResultadosVsInventarioDetalladoTO(usuarioEmpresaReporteTO, listConListaBalanceResultadosVsInventarioTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteEstadoResultadosVsInventario")
    public @ResponseBody
    String exportarReporteEstadoResultadosVsInventario(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<ConListaBalanceResultadosVsInventarioTO> listConListaBalanceResultadosVsInventarioTO = UtilsJSON.jsonToList(ConListaBalanceResultadosVsInventarioTO.class, map.get("listConListaBalanceResultadosVsInventarioTO"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteEstadoResultadosVsInventario(usuarioEmpresaReporteTO, listConListaBalanceResultadosVsInventarioTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConBalanceBalanceCentroProduccionTO")
    public ResponseEntity getConBalanceBalanceCentroProduccionTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("ocultarDetalle"));
        Boolean excluirAsientoCierre = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("excluirAsientoCierre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            RetornoTO respues = contableService.getConBalanceBalanceCentroProduccionTO(empresa, fechaDesde, fechaHasta, ocultarDetalle, excluirAsientoCierre);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estado de resultado integral");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    /*ESTADO DE RESULTADO INTEGRAL*/
    @RequestMapping("/getConBalanceResultadoTO")
    public ResponseEntity getConBalanceResultadoTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("ocultarDetalle"));
        Boolean excluirAsientoCierre = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("excluirAsientoCierre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConFunBalanceResultadosNecTO> respues = contableService.getConEstadoResultadosIntegralTO(empresa, sector, fechaDesde, fechaHasta, ocultarDetalle, excluirAsientoCierre);
            if (!respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estado de resultado integral");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteBalanceResultado")
    public @ResponseBody
    String generarReporteBalanceResultado(HttpServletResponse response, @RequestBody Map<String, Object> parametros) throws Exception {
        String nombreReporte = "reportContabilidadBalanceResultados.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        Integer columnasEstadosFinancieros = empresaParametrosService.getColumnasEstadosFinancieros(usuarioEmpresaReporteTO.getEmpCodigo());
        List<ConFunBalanceResultadosNecTO> listConFunBalanceResultadosNecTO = UtilsJSON.jsonToList(ConFunBalanceResultadosNecTO.class, parametros.get("ConBalanceResultadoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceResultadoTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, codigoSector, columnasEstadosFinancieros, listConFunBalanceResultadosNecTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteEstadoResultadoIntegral")
    public @ResponseBody
    String generarReporteEstadoResultadoIntegral(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        List<ConFunBalanceResultadosNecTO> listConListaConBalanceResultadoTO = UtilsJSON.jsonToList(ConFunBalanceResultadosNecTO.class, map.get("ConBalanceResultadoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.exportarReporteEstadoResultadoIntegralTO(usuarioEmpresaReporteTO, listConListaConBalanceResultadoTO, fechaDesde, fechaHasta, sector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*ESTADO DE SITUACIÓN FINANCIERA*/
    @RequestMapping("/getFunBalanceGeneralTO")
    public ResponseEntity getFunBalanceGeneralTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        Boolean ocultar = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("ocultar"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("ocultarDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConFunBalanceGeneralNecTO> respues = contableService.getConEstadoSituacionFinancieraTO(empresa, sector, fecha, ocultar, ocultarDetalle);
            if (!respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estado de situación financiera comparativo");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/exportarReporteEstadoSituacionFinanciera")
    public @ResponseBody
    String exportarReporteEstadoSituacionFinanciera(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        List<ConFunBalanceGeneralNecTO> listado = UtilsJSON.jsonToList(ConFunBalanceGeneralNecTO.class, map.get("listConBalanceGeneralTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteEstadoSituacionFinancieraTO(usuarioEmpresaReporteTO, listado, fechaHasta, sector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*ESTADO DE SITUACION FINANCIERA COMPARATIVA*/
    @RequestMapping("/getFunBalanceGeneralComparativoTO")
    public RespuestaWebTO getFunBalanceGeneralComparativoTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("secCodigo"));
        String fechaAnterior = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaAnterior"));
        String fechaActual = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaActual"));
        Boolean ocultar = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("ocultar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ConBalanceGeneralComparativoTO> respues = contableService.getFunBalanceGeneralComparativoTO(empresa, secCodigo, fechaAnterior, fechaActual, ocultar);
            if (!respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron estado de situación financiera comparativo");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteBalanceGeneralComparativo")
    public @ResponseBody
    String generarReporteBalanceGeneralComparativo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContabilidadBalanceGeneralComparativo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaAnterior = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaAnterior"));
        String fechaActual = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaActual"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        List<ConBalanceGeneralComparativoTO> listConBalanceGeneralComparativoTO = UtilsJSON.jsonToList(ConBalanceGeneralComparativoTO.class, parametros.get("listConBalanceGeneralComparativoTO"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceGeneralComparativo(usuarioEmpresaReporteTO, fechaAnterior, fechaActual, codigoSector, listConBalanceGeneralComparativoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteEstadoSituacionFinancieraComparativo")
    public @ResponseBody
    String generarReporteEstadoSituacionFinancieraComparativo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaAnterior"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaActual"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        List<ConBalanceGeneralComparativoTO> listConListaEstadoSituacionFinancieraComparativo = UtilsJSON.jsonToList(ConBalanceGeneralComparativoTO.class, map.get("listConBalanceGeneralComparativoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteEstadoSituacionFinancieraComparativo(usuarioEmpresaReporteTO, listConListaEstadoSituacionFinancieraComparativo, fechaDesde, fechaHasta, sector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConTipoReferencia")
    public RespuestaWebTO getListaConTipoReferencia(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String referencia = UtilsJSON.jsonToObjeto(String.class, map.get("referencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConTipo> respues = tipoService.getListaConTipo(empresa, referencia);
            List<ConTipoTO> respuesTO = new ArrayList<>();
            if (respues != null && !respues.isEmpty()) {
                for (int i = 0; i < respues.size(); i++) {
                    ConTipoTO tipoTO = ConversionesContabilidad.convertirConTipo_ConTipoTO(respues.get(i));
                    respuesTO.add(tipoTO);
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesTO);
            } else {
                resp.setOperacionMensaje("No se encontraron tipos contables");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }

        return resp;
    }

    @RequestMapping("/getListaConTipo")
    public RespuestaWebTO getListaConTipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConTipo> respues = tipoService.getListaConTipo(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron tipos contables");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaConTipoTOFiltrado")
    public List<ConTipoTO> getListaConTipoTOFiltrado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String filtro = UtilsJSON.jsonToObjeto(String.class, map.get("filtro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoTOFiltrado(empresa, filtro);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }

        return null;
    }

    @RequestMapping("/eliminarConContable")
    public RespuestaWebTO eliminarConContable(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = contableService.eliminarConContable(conContablePK, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El contable: " + conContablePK.mensaje() + " se eliminó correctamente.");
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

    @RequestMapping("/getListaConTipoTO")
    public List<ConTipoTO> getListaConTipoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConTipoTO")
    public ConTipoTO getConTipoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tipCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getConTipoTO(empresa, tipCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConTipoRrhhTO")
    public List<ConTipoTO> getListaConTipoRrhhTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoRrhhTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConTipoCarteraTO")
    public List<ConTipoTO> getListaConTipoCarteraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoCarteraTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConTipoCarteraAnticiposTO")
    public List<ConTipoTO> getListaConTipoCarteraAnticiposTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoCarteraAnticiposTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // revisar
    @RequestMapping("/getRangoCuentasTO")
    public List<ConCuentasTO> getRangoCuentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoCuentaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuentaDesde"));
        String codigoCuentaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuentaHasta"));
        int largoCuenta = UtilsJSON.jsonToObjeto(int.class, map.get("largoCuenta"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasService.getRangoCuentasTO(empresa, codigoCuentaDesde, codigoCuentaHasta, largoCuenta, usuario,
                    sisInfoTO);
        } catch (Exception e) {
            e.printStackTrace();
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConCuentasFlujoTO")
    public List<ConCuentasFlujoTO> getListaConCuentasFlujoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasService.getListaConCuentasFlujoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConCuentasFlujoDetalleTO")
    public List<ConCuentasFlujoDetalleTO> getListaConCuentasFlujoDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoDetalleService.getListaConCuentasFlujoDetalleTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBuscarConCuentasTO")
    public RespuestaWebTO getListaBuscarConCuentasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        String ctaGrupo = UtilsJSON.jsonToObjeto(String.class, map.get("ctaGrupo"));
        boolean cuentaActivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("cuentaActivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConCuentasTO> respues = cuentasService.getListaBuscarConCuentasTO(empresa, buscar, ctaGrupo, cuentaActivo);
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

    @RequestMapping("/getListaBuscarConCuentasFlujoTOEmpresa")
    public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTOEmpresa(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.getListaBuscarConCuentasFlujoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBuscarConCuentasFlujoTO")
    public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.getListaBuscarConCuentasFlujoTO(empresa, buscar);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConEstructuraFlujoTO")
    public List<ConEstructuraFlujoTO> getListaConEstructuraFlujoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return estructuraFlujoService.getListaConEstructuraFlujoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/buscarConDetallesActivosBiologicos")
    public Boolean buscarConDetallesActivosBiologicos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasEspecialesService.buscarConDetallesActivosBiologicos(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /* CONTABILIDAD CONTABILIDAD CUENTAS DE RESULTADOS */
    @RequestMapping("/getConFunBalanceResultadosNecTO")
    public RespuestaWebTO getConFunBalanceResultadosNecTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, map.get("ocultarDetalle"));
        Boolean excluirAsientoCierre = UtilsJSON.jsonToObjeto(Boolean.class, map.get("excluirAsientoCierre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConFunBalanceResultadosNecTO> respues = contableService.getConEstadoResultadosIntegralTO(empresa, sector, fechaDesde, fechaHasta, ocultarDetalle, excluirAsientoCierre);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de cuentas de resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getConFunBalanceFlujoEfectivo")
    public List<ConBalanceResultadoTO> getConFunBalanceFlujoEfectivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getConFunBalanceFlujoEfectivo(empresa, sector, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConFunBalanceGeneralNecTO")
    public List<ConFunBalanceGeneralNecTO> getConFunBalanceGeneralNecTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        Boolean ocultar = UtilsJSON.jsonToObjeto(Boolean.class, map.get("ocultar"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, map.get("ocultarDetalle"));
        Integer columnasEstadosFinancieros = UtilsJSON.jsonToObjeto(Integer.class,
                map.get("columnasEstadosFinancieros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getConEstadoSituacionFinancieraTO(empresa, sector, fecha, ocultar, ocultarDetalle);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarConCuentaFlujo")
    public boolean insertarConCuentaFlujo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoTO conCuentasFlujoTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoTO.class,
                map.get("conCuentasFlujoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.insertarConCuentaFlujo(conCuentasFlujoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarConCuentaFlujoDetalle")
    public boolean insertarConCuentaFlujoDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoDetalleTO.class,
                map.get("conCuentasFlujoDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoDetalleService.insertarConCuentaFlujoDetalle(conCuentasFlujoDetalleTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarConCuentaFlujo")
    public boolean modificarConCuentaFlujo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoTO conCuentasFlujoTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoTO.class,
                map.get("conCuentasFlujoTO"));
        String codigoCambiarLlave = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCambiarLlave"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.modificarConCuentaFlujo(conCuentasFlujoTO, codigoCambiarLlave, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarConCuentaFlujoDetalle")
    public boolean modificarConCuentaFlujoDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoDetalleTO.class,
                map.get("conCuentasFlujoDetalleTO"));
        String codigoCambiarLlave = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCambiarLlave"));
        char formaPagoAnterior = UtilsJSON.jsonToObjeto(char.class, map.get("formaPagoAnterior"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoDetalleService.modificarConCuentaFlujoDetalle(conCuentasFlujoDetalleTO,
                    codigoCambiarLlave, formaPagoAnterior, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarConCuentaFlujoDetalle")
    public String eliminarConCuentaFlujoDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoDetalleTO.class,
                map.get("conCuentasFlujoDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.eliminarConCuentaFlujoDetalle(conCuentasFlujoDetalleTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return "";
    }

    @RequestMapping("/eliminarConCuentaFlujo")
    public String eliminarConCuentaFlujo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoTO conCuentasFlujoTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoTO.class,
                map.get("conCuentasFlujoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.eliminarConCuentaFlujo(conCuentasFlujoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return "";
    }

    @RequestMapping("/insertarConContable")
    public MensajeTO insertarConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContableTO conContableTO = UtilsJSON.jsonToObjeto(ConContableTO.class, map.get("conContableTO"));
        List<ConDetalleTO> listaConDetalleTO = UtilsJSON.jsonToList(ConDetalleTO.class, map.get("listaConDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.insertarConContable(conContableTO, listaConDetalleTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/buscarDetalleContable")
    public ConDetalle buscarDetalleContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Long codDetalle = UtilsJSON.jsonToObjeto(Long.class, map.get("codDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.buscarDetalleContable(codDetalle);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListConDetalle")
    public List<ConDetalle> getListConDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.getListConDetalle(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/buscarConContable")
    public List<ConDetalleTO> buscarConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codEmpresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("perCodigo"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tipCodigo"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.buscarConContable(codEmpresa, perCodigo, tipCodigo, conNumero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // revisar
    @RequestMapping("/getFunListaIPP")
    public RespuestaWebTO getFunListaIPP(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String parametro = UtilsJSON.jsonToObjeto(String.class, map.get("parametro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConFunIPPTO> respues = contableService.getFunListaIPP(empresa, fechaInicio, fechaFin, parametro);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);//No hay pendientes
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

    @RequestMapping("/listarTodoProceso")
    public RespuestaWebTO listarTodoProceso(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerListasParaContabilizarTodoProceso(map);
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
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*  Consulta consumos pendientes */
    @RequestMapping("/getListaInvConsultaConsumosPendientes")
    public RespuestaWebTO getListaInvConsultaConsumosPendientes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respues = contableService.getListaInvConsultaConsumosPendientes(empCodigo, fechaDesde, fechaHasta);
            if (respues.getMensaje().length() > 0 && respues.getMensaje().substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1).replace("<html>", "").replace("</html>", ""));
                resp.setExtraInfo("");//No hay pendientes
            } else if (respues.getMensaje().length() > 0 && respues.getMensaje().substring(0, 1).equalsIgnoreCase("F")) {
                resp.setOperacionMensaje(respues.getMensaje().substring(1).replace("<html>", "").replace("</html>", ""));
                resp.setExtraInfo(respues.getListaMensajes());//Si hay pendientes
            } else {
                resp.setOperacionMensaje("No se encontraron resultados de consumos pendientes");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarIPP")
    public RespuestaWebTO insertarModificarIPP(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));//DIRECTO, INDIRECTO, CORRIDAS
        Date fechaHoraBusqueda = UtilsDate.timestamp();
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respues = contableService.insertarModificarIPP(empresa, fechaDesde, fechaHasta, tipo, fechaHoraBusqueda, sisInfoTO);
            if (respues.getMensaje().length() > 0 && respues.getMensaje().substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1).replace("<html>", "").replace("</html>", ""));
                resp.setExtraInfo("");//No hay pendientes
            } else if (respues.getMensaje().length() > 0 && respues.getMensaje().substring(0, 1).equalsIgnoreCase("F")) {
                resp.setOperacionMensaje(respues.getMensaje().substring(1).replace("<html>", "").replace("</html>", ""));
                resp.setExtraInfo(respues.getListaMensajes());//Si hay pendientes
            } else {
                resp.setOperacionMensaje("No se completo la operación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarContablesIPPTodo")
    public RespuestaWebTO insertarModificarContablesIPPTodo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        Date fechaHoraBusqueda = new Date();
        List<PrdContabilizarCorridaTO> listaContabilizarCorrida = UtilsJSON.jsonToList(PrdContabilizarCorridaTO.class, map.get("listaContabilizarCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contableService.insertarModificarContablesIPPTodo(empresa, fechaDesde, fechaHasta, fechaHoraBusqueda, listaContabilizarCorrida, sisInfoTO);
            if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                resp.setExtraInfo(true);
            } else if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'F') {
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarContabilizarCorrida")
    public RespuestaWebTO insertarModificarContabilizarCorrida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdContabilizarCorridaTO> listaContabilizarCorrida = UtilsJSON.jsonToList(PrdContabilizarCorridaTO.class, map.get("listaContabilizarCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contableService.insertarModificarContabilizarCorrida(empresa, desde, hasta, listaContabilizarCorrida, sisInfoTO);
            if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                resp.setExtraInfo(true);
            } else if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'F') {
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getFunPersonaTOs")
    public List<PersonaTO> getFunPersonaTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busquedad = UtilsJSON.jsonToObjeto(String.class, map.get("busquedad"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getFunPersonaTOs(empresa, busquedad);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/validarContables")
    public MensajeTO validarContables(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String conTipo = UtilsJSON.jsonToObjeto(String.class, map.get("conTipo"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        String accionUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("accionUsuario"));
        String bandera = UtilsJSON.jsonToObjeto(String.class, map.get("bandera"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.validarContables(sisInfoTO.getEmpresa(), periodo, conTipo, conNumero, accionUsuario,
                    bandera);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/validarChequesConciliados")
    public List<String> validarChequesConciliados(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String conTipo = UtilsJSON.jsonToObjeto(String.class, map.get("conTipo"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.validarChequesConciliados(sisInfoTO.getEmpresa(), periodo, conTipo, conNumero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCompraContableDetalle")
    public @ResponseBody
    String generarReporteCompraContableDetalle(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ReporteCompraDetalle> reporteCompraDetalles = UtilsJSON.jsonToList(ReporteCompraDetalle.class, map.get("reporteCompraDetalles"));
        List<ConContableReporteTO> list = UtilsJSON.jsonToList(ConContableReporteTO.class, map.get("list"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteCompraContableDetalle(usuarioEmpresaReporteTO, reporteCompraDetalles, list);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //ContableApreturaDeSaldosIniciales
    @RequestMapping("/insertarConContableAperturaSaldoInicial")
    public RespuestaWebTO insertarConContableAperturaSaldoInicial(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContableEstadoSituacionInicial conApertura = UtilsJSON.jsonToObjeto(ConContableEstadoSituacionInicial.class, map.get("conApertura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (aperturaDeSaldosService.obtenerConAperturaSaldoInicial(conApertura) == null) {
                MensajeTO mensajeTO = aperturaDeSaldosService.insertarConContableAperturaSaldoInicial(conApertura, sisInfoTO);
                if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(mensajeTO.getMap());
                    resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                } else {
                    resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado contable de situacion inicial.");
                }
            } else {
                resp.setExtraInfo("modificar");
                resp.setOperacionMensaje("Ya existe un contable de estado de situación inicial, ¿Desea modificarlo?");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarConContableAperturaSaldoInicial")
    public RespuestaWebTO modificarConContableAperturaSaldoInicial(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContableEstadoSituacionInicial conApertura = UtilsJSON.jsonToObjeto(ConContableEstadoSituacionInicial.class, map.get("conApertura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = aperturaDeSaldosService.modificarConContableAperturaSaldoInicial(conApertura, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable contable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado contable de situacion inicial.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //ConContableCierreResultados
    @RequestMapping("/insertarConContableCierreResultados")
    public RespuestaWebTO insertarConContableCierreResultados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContableCierreResultado conCierreResultados = UtilsJSON.jsonToObjeto(ConContableCierreResultado.class, map.get("contable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (conContableCierreResultadosService.obtenerConContableCierreResultados(conCierreResultados) == null) {
                MensajeTO mensajeTO = conContableCierreResultadosService.insertarConContableCierreResultados(conCierreResultados, sisInfoTO);
                if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(mensajeTO.getMap());
                    resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                } else {
                    resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado contable de cierre de resultados.");
                }
            } else {
                resp.setExtraInfo("modificar");
                resp.setOperacionMensaje("Ya existe un contable de cierre de resultados, ¿Desea modificarlo?");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarConContableCierreResultados")
    public RespuestaWebTO modificarConContableCierreResultados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContableCierreResultado conCierreResultados = UtilsJSON.jsonToObjeto(ConContableCierreResultado.class, map.get("contable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = conContableCierreResultadosService.modificarConContableCierreResultados(conCierreResultados, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable contable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado contable de cierre de resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosContable")
    public RespuestaWebTO obtenerDatosContable(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerDatosContable(map);
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
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/consultaContableVirtual")
    public ResponseEntity consultaContableVirtual(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, parametros.get("conContablePK"));
        String tipoContableVirtual = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipoContableVirtual"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            ItemListaContableTO respuesta = contableService.consultaContableVirtual(conContablePK, tipoContableVirtual);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro contable");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/obtenerDatosDiarioAuxiliar")
    public RespuestaWebTO obtenerDatosDiarioAuxiliar(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerDatosDiarioAuxiliar(map);
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
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaContabilizarDatos")
    public RespuestaWebTO obtenerDatosParaContabilizarDatos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerDatosParaContabilizarDatos(map);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron datos para continuar.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosConsultaContabilidad")
    public RespuestaWebTO obtenerDatosConsultaContabilidad(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerDatosConsultaContabilidad(map);
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
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosConsultaComprobanteContable")
    public RespuestaWebTO obtenerDatosConsultaComprobanteContable(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contableService.obtenerDatosConsultaComprobanteContable(map);
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
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //matricial
    @RequestMapping("/generarReporteBalanceComprobacionMatricial")
    public RespuestaWebTO generarReporteTrasferenciasListadoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<ConBalanceComprobacionTO> listConBalanceComprobacionTO = UtilsJSON.jsonToList(ConBalanceComprobacionTO.class, parametros.get("listConBalanceComprobacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceComprobacion(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listConBalanceComprobacionTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteBalanceResultadosMatricial")
    public RespuestaWebTO generarReporteBalanceResultadosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        Integer columnasEstadosFinancieros = empresaParametrosService.getColumnasEstadosFinancieros(usuarioEmpresaReporteTO.getEmpCodigo());
        List<ConFunBalanceResultadosNecTO> listConFunBalanceResultadosNecTO = UtilsJSON.jsonToList(ConFunBalanceResultadosNecTO.class, parametros.get("ConBalanceResultadoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceResultadoTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, codigoSector, columnasEstadosFinancieros, listConFunBalanceResultadosNecTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteBalanceResultadoComparativoMatricial")
    public RespuestaWebTO generarReporteBalanceResultadoComparativoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String fechaDesde2 = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde2"));
        String fechaHasta2 = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta2"));
        List<ConBalanceResultadoComparativoTO> conBalanceResultadoComparativoTO = UtilsJSON.jsonToList(ConBalanceResultadoComparativoTO.class, parametros.get("conBalanceResultadoComparativoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceResultadoComparativo(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, fechaDesde2, fechaHasta2, codigoSector, conBalanceResultadoComparativoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteMayorGeneralDatosMatricial")
    public RespuestaWebTO generarReporteMayorGeneralDatosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, parametros.get("cuentaContable"));
        Object[][] datos = UtilsJSON.jsonToObjeto(Object[][].class, parametros.get("datos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteMayorGeneral(usuarioEmpresaReporteTO, fechaHasta, cuentaContable, datos);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteBalanceResultadosVsInventarioDetalladoTOMatricial")
    public RespuestaWebTO generarReporteBalanceResultadosVsInventarioDetalladoTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<ConBalanceResultadosVsInventarioDetalladoTO> listConListaBalanceResultadosVsInventarioTO = UtilsJSON.jsonToList(ConBalanceResultadosVsInventarioDetalladoTO.class, parametros.get("listConListaBalanceResultadosVsInventarioTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteConBalanceResultadosVsInventarioDetalladoTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listConListaBalanceResultadosVsInventarioTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteBalanceGeneralTOMatricial")
    public RespuestaWebTO generarReporteBalanceGeneralTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        List<ConFunBalanceGeneralNecTO> listConBalanceGeneralNecTO = UtilsJSON.jsonToList(ConFunBalanceGeneralNecTO.class, parametros.get("listConBalanceGeneralNecTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceGeneralNecTO(usuarioEmpresaReporteTO, fechaHasta, codigoSector, listConBalanceGeneralNecTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteBalanceGeneralComparativoMatricial")
    public RespuestaWebTO generarReporteBalanceGeneralComparativoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaAnterior = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaAnterior"));
        String fechaActual = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaActual"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        List<ConBalanceGeneralComparativoTO> listConBalanceGeneralComparativoTO = UtilsJSON.jsonToList(ConBalanceGeneralComparativoTO.class, parametros.get("listConBalanceGeneralComparativoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceGeneralComparativo(usuarioEmpresaReporteTO, fechaAnterior, fechaActual, codigoSector, listConBalanceGeneralComparativoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirMayorAuxiliarMatricial")
    public RespuestaWebTO generarReporteImprimirMayorAuxiliarMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConMayorAuxiliarTO> listado = UtilsJSON.jsonToList(ConMayorAuxiliarTO.class, parametros.get("ListaConMayorAuxiliarTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String ctaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("ctaDesde"));
        String ctaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("ctaHasta"));
        String detalleDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("detalleDesde"));
        String detalleHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("detalleHasta"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        sisInfoTO.setEmpresa(empresa);
        try {
            Map<String, Object> nombres = cuentasService.buscarNombresDesdeHastaPadre(empresa, ctaDesde, ctaHasta, sisInfoTO);
            String nombrePadresDesde = UtilsJSON.jsonToObjeto(String.class, nombres.get("nombrePadresDesde"));
            String nombrePadreHasta = UtilsJSON.jsonToObjeto(String.class, nombres.get("nombrePadreHasta"));
            ctaDesde = ctaDesde + '|' + detalleDesde;
            ctaHasta = ctaHasta != null ? ctaHasta + '|' + detalleHasta : null;
            respuesta = reporteContabilidadService.generarReporteMayorAuxiliar(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, ctaDesde, nombrePadresDesde, ctaHasta, nombrePadreHasta, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteMayorGeneralMatricial")
    public RespuestaWebTO generarReporteMayorGeneralMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, parametros.get("cuentaContable"));
        List<ConMayorGeneralTO> listConMayorGeneralTO = UtilsJSON.jsonToList(ConMayorGeneralTO.class, parametros.get("listConMayorGeneralTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteMayorGeneral(usuarioEmpresaReporteTO, fechaHasta, cuentaContable, listConMayorGeneralTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteContablesVerificacionesErroresMatricial")
    public RespuestaWebTO generarReporteContablesVerificacionesErroresMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO = UtilsJSON.jsonToList(ConFunContablesVerificacionesTO.class, parametros.get("listConFunContablesVerificacionesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteContablesVerificacionesErrores(usuarioEmpresaReporteTO, listConFunContablesVerificacionesTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteContablesVerificacionesComprasMatricial")
    public RespuestaWebTO generarReporteContablesVerificacionesComprasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<ConFunContablesVerificacionesComprasTO> listVerificacionesComprasTO = UtilsJSON.jsonToList(ConFunContablesVerificacionesComprasTO.class, parametros.get("listConFunContablesVerificacionesComprasTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteContablesVerificacionesCompras(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listVerificacionesComprasTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //cambios
    @RequestMapping("/generarReporteContableDetalleMatricial")
    public RespuestaWebTO generarReporteContableDetalleMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<ConContablePK> listadoContablePK = contableService.obtenerListadoContablePK(listado);
            if (!listadoContablePK.isEmpty()) {
                List<ConContableReporteTO> listadoEnviar = reporteContabilidadService.generarListaConContableReporteTO(listadoContablePK, sisInfoTO);
                respuesta = reporteContabilidadService.generarReporteContableDetalle(usuarioEmpresaReporteTO, listadoEnviar);
                resp.setExtraInfo(respuesta);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirReporteMayorAuxiliarMatricial")
    public RespuestaWebTO generarReporteimprimirReporteMayorAuxiliarMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ItemStatusItemListaConContableTO> listado = UtilsJSON.jsonToList(ItemStatusItemListaConContableTO.class, parametros.get("ListaConContableTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<ConContablePK> listadoContablePK = contableService.obtenerListadoContablePK(listado);
            if (!listadoContablePK.isEmpty()) {
                List<ConContableReporteTO> listadoEnviar = reporteContabilidadService.generarListaConContableReporteTO(listadoContablePK, sisInfoTO);
                respuesta = reporteContabilidadService.generarReporteContableDetalle(usuarioEmpresaReporteTO, listadoEnviar);
                resp.setExtraInfo(respuesta);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDiarioAuxiliarMatricial")
    public RespuestaWebTO generarReporteDiarioAuxiliarMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String codigoTipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoTipo"));
        List<ConDiarioAuxiliarTO> listado = UtilsJSON.jsonToList(ConDiarioAuxiliarTO.class, parametros.get("listConDiarioAuxiliarTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteDiarioAuxiliar(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, codigoTipo, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteTipoContableMatricial")
    public RespuestaWebTO generarReporteTipoContableMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConTipoTO> listado = UtilsJSON.jsonToList(ConTipoTO.class, parametros.get("listadoTipos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.generarReporteTipoContable(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerProyectoFlujoCaja")
    public RespuestaWebTO obtenerProyectoFlujoCaja(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            Map<String, Object> respuesta = flujoCajaService.obtenerProyectoFlujoCaja(parametros);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
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

    @RequestMapping("/imprimirReporteProyectoFlujoCaja")
    public @ResponseBody
    String imprimirReporteProyectoFlujoCaja(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportFlujoCaja.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        Map<String, Object> resultado = UtilsJSON.jsonToObjeto(HashMap.class, parametros.get("resultado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.imprimirReporteProyectoFlujoCaja(usuarioEmpresaReporteTO, resultado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirReporteProyectoFlujoCajaMatricial")
    public RespuestaWebTO imprimirReporteProyectoFlujoCajaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        Map<String, Object> resultado = UtilsJSON.jsonToObjeto(HashMap.class, parametros.get("resultado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteContabilidadService.imprimirReporteProyectoFlujoCaja(usuarioEmpresaReporteTO, resultado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteFlujoDeCaja")
    public @ResponseBody
    String exportarReporteFlujoDeCaja(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        Map<String, Object> resultado = UtilsJSON.jsonToObjeto(HashMap.class, map.get("resultado"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.exportarReporteFlujoDeCaja(usuarioEmpresaReporteTO, resultado, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerCuadroComparativoEntreEmpresas")
    public RespuestaWebTO obtenerCuadroComparativoEntreEmpresas(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        List<String> empresas = UtilsJSON.jsonToList(String.class, parametros.get("empresas"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        try {
            String temporal = empresaParametrosService.actualizarTemporalDeEmpresas(empresas);
            if (temporal != null) {
                Map<String, Object> respuesta = comparativoOtrasEmpresaService.obtenerCuadroComparativoEntreEmpresas(temporal, fecha);
                if (respuesta != null) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respuesta);
                } else {
                    resp.setOperacionMensaje("No se encontraron resultados");
                }
            } else {
                resp.setOperacionMensaje("Error al generar campo temporal");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerResultadoComparativoEntreEmpresas")
    public RespuestaWebTO obtenerResultadoComparativoEntreEmpresas(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        List<String> empresas = UtilsJSON.jsonToList(String.class, parametros.get("empresas"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        try {
            String temporal = empresaParametrosService.actualizarTemporalDeEmpresas(empresas);
            if (temporal != null) {
                Map<String, Object> respuesta = comparativoOtrasEmpresaService.obtenerResultadoComparativoEntreEmpresas(temporal, desde, hasta);
                if (respuesta != null) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respuesta);
                } else {
                    resp.setOperacionMensaje("No se encontraron resultados");
                }
            } else {
                resp.setOperacionMensaje("Error al generar campo temporal");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarPlantillaContables")
    public @ResponseBody
    String exportarPlantillaContables(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.exportarPlantillaContables(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/verificarExistenciasContables")
    public RespuestaWebTO verificarExistenciasContables(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ConListaContableDetalleTO> cuentas = UtilsJSON.jsonToList(ConListaContableDetalleTO.class, map.get("cuentas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (cuentas != null && !cuentas.isEmpty()) {
                List<ConListaContableDetalleTO> cuentasEncontrados = new ArrayList<>();
                String mensaje = "";
                for (int i = 0; i < cuentas.size(); i++) {

                    ConCuentas cuenta = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(sisInfoTO.getEmpresa(), cuentas.get(i).getCtaCodigo()));
                    if (cuenta == null) {
                        mensaje = mensaje + "La cuenta con código: <strong class='pl-2'>" + cuentas.get(i).getCtaCodigo() + " </strong> no existe.<br>";
                    } else {
                        if (cuentas.get(i).getSecCodigo() != null) {
                            PrdSector sector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(sisInfoTO.getEmpresa(), cuentas.get(i).getSecCodigo()));
                            if (sector != null) {
                                ConListaContableDetalleTO item = new ConListaContableDetalleTO();
                                PrdListaSectorTO sectorTO = new PrdListaSectorTO();
                                sectorTO.setSecCodigo(sector.getPrdSectorPK().getSecCodigo());
                                sectorTO.setNomSector(sector.getSecNombre());
                                sectorTO.setSecNombre(sector.getSecNombre());

                                item.setConCuentas(cuenta);
                                item.setSectorSeleccionado(sectorTO);

                                if (cuentas.get(i).getPisNumero() != null) {
                                    PrdPiscina piscina = prdPiscinaDao.obtener(PrdPiscina.class, new PrdPiscinaPK(sisInfoTO.getEmpresa(), cuentas.get(i).getSecCodigo(), cuentas.get(i).getPisNumero()));
                                    if (piscina != null) {
                                        PrdListaPiscinaTO piscinaTO = new PrdListaPiscinaTO();
                                        piscinaTO.setPisSector(sector.getPrdSectorPK().getSecCodigo());
                                        piscinaTO.setPisNumero(piscina.getPrdPiscinaPK().getPisNumero());
                                        item.setPiscinaSeleccionada(piscinaTO);
                                        cuentasEncontrados.add(item);
                                    } else {
                                        mensaje = mensaje + "EL CC con código: <strong class='pl-2'>" + cuentas.get(i).getPisNumero() + " </strong> no existe.<br>";
                                    }
                                } else {
                                    cuentasEncontrados.add(item);
                                }
                            } else {
                                mensaje = mensaje + "El CP con código: <strong class='pl-2'>" + cuentas.get(i).getSecCodigo() + " </strong> no existe.<br>";
                            }
                        }
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(cuentasEncontrados);
                if (mensaje != null && !mensaje.isEmpty()) {
                    resp.setOperacionMensaje("<strong>Las siguientes cuentas no existen en la base de datos actual: <br></strong>" + mensaje);
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //buscar sector null 
    @RequestMapping("/guardarImagenesContable")
    public RespuestaWebTO guardarImagenesContable(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK pk = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("pk"));
        List<ConAdjuntosContableWebTO> imagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listImagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = contableService.guardarImagenesContable(imagenes, pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (imagenes == null || imagenes.isEmpty()) {
                    resp.setOperacionMensaje("Las imágenes para contable se han eliminado correctamente.");
                } else {
                    resp.setOperacionMensaje("Las imágenes para contable se han guardado correctamente.");
                }
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar imagenes de contable.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarContabilizarCostoVenta")
    public RespuestaWebTO insertarModificarContabilizarCostoVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdContabilizarCorridaCostoVentaTO> listaContabilizarCorrida = UtilsJSON.jsonToList(PrdContabilizarCorridaCostoVentaTO.class, map.get("listaContabilizarCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contableService.insertarModificarContabilizarCorridaCostoVenta(empresa, desde, hasta, listaContabilizarCorrida, sisInfoTO);
            if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                resp.setExtraInfo(true);
            } else if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'F') {
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaConContablePlanillaFondoReserva")
    public RespuestaWebTO getListaConContablePlanillaFondoReserva(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConContablePlanillaFondoReserva> respues = contablePlanillaFondoReservaService.getListaConContablePlanillaFondoReserva(empresa, sector, periodo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);//No hay pendientes
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

    @RequestMapping("/obtenerDatosParaPlanillaFondoReserva")
    public RespuestaWebTO obtenerDatosParaPlanillaFondoReserva(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean mostrarTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("mostrarTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contablePlanillaFondoReservaService.obtenerDatosParaPlanillaFondoReserva(empresa, mostrarTodos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados para trabajar contables");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaPlanillaFondoReservaFormulario")
    public RespuestaWebTO obtenerDatosParaPlanillaFondoReservaFormulario(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean mostrarTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("mostrarTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contablePlanillaFondoReservaService.obtenerDatosParaPlanillaFondoReservaFormulario(empresa, mostrarTodos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados para trabajar contables");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarConContablePlanillaFondoReserva")
    public RespuestaWebTO insertarConContablePlanillaFondoReserva(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePlanillaFondoReserva conContablePlanillaFondoReserva = UtilsJSON.jsonToObjeto(ConContablePlanillaFondoReserva.class, map.get("conContablePlanillaFondoReserva"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contablePlanillaFondoReservaService.insertarConContablePlanillaFondoReserva(conContablePlanillaFondoReserva, sisInfoTO);
            if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                resp.setExtraInfo(mensajeTO.getMap());
            } else if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'F') {
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarConContablePlanillaFondoReserva")
    public RespuestaWebTO modificarConContablePlanillaFondoReserva(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePlanillaFondoReserva conContablePlanillaFondoReserva = UtilsJSON.jsonToObjeto(ConContablePlanillaFondoReserva.class, map.get("conContablePlanillaFondoReserva"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contablePlanillaFondoReservaService.modificarConContablePlanillaFondoReserva(conContablePlanillaFondoReserva, sisInfoTO);
            if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                resp.setExtraInfo(mensajeTO.getMap());
            } else if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'F') {
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarConContablePlanillaFondoReserva")
    public RespuestaWebTO eliminarConContablePlanillaFondoReserva(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contablePlanillaFondoReservaService.eliminarConContablePlanillaFondoReserva(secuencial, sisInfoTO);
            if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                resp.setExtraInfo(true);
            } else if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'F') {
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //PLANILLA APORTE
    @RequestMapping("/getListaConContablePlanillaAportes")
    public RespuestaWebTO getListaConContablePlanillaAportes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConContablePlanillaAportes> respues = contablePlanillaAportesService.getListaConContablePlanillaAportes(empresa, sector, periodo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);//No hay pendientes
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

    @RequestMapping("/obtenerDatosParaPlanillaAportes")
    public RespuestaWebTO obtenerDatosParaPlanillaAportes(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean mostrarTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("mostrarTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contablePlanillaAportesService.obtenerDatosParaPlanillaAportes(empresa, mostrarTodos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados para trabajar contables");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaPlanillaAportesFormulario")
    public RespuestaWebTO obtenerDatosParaPlanillaAportesFormulario(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean mostrarTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("mostrarTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = contablePlanillaAportesService.obtenerDatosParaPlanillaAportesFormulario(empresa, mostrarTodos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados para trabajar contables");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarConContablePlanillaAportes")
    public RespuestaWebTO insertarConContablePlanillaAportes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePlanillaAportes conContablePlanilla = UtilsJSON.jsonToObjeto(ConContablePlanillaAportes.class, map.get("conContablePlanillaAporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contablePlanillaAportesService.insertarConContablePlanillaAportes(conContablePlanilla, sisInfoTO);
            if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                resp.setExtraInfo(mensajeTO.getMap());
            } else if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'F') {
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarConContablePlanillaAportes")
    public RespuestaWebTO modificarConContablePlanillaAportes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePlanillaAportes conContablePlanilla = UtilsJSON.jsonToObjeto(ConContablePlanillaAportes.class, map.get("conContablePlanillaAporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contablePlanillaAportesService.modificarConContablePlanillaAportes(conContablePlanilla, sisInfoTO);
            if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                resp.setExtraInfo(mensajeTO.getMap());
            } else if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'F') {
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarConContablePlanillaAportes")
    public RespuestaWebTO eliminarConContablePlanillaAportes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = contablePlanillaAportesService.eliminarConContablePlanillaAportes(secuencial, sisInfoTO);
            if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                resp.setExtraInfo(true);
            } else if (mensajeTO.getMensaje().length() > 0 && mensajeTO.getMensaje().charAt(0) == 'F') {
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje("No se logró realizar la operación solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteEstadoResultadoCentroProduccion")
    public @ResponseBody
    String generarReporteEstadoResultadoCentroProduccion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<ConFunBalanceCentroProduccionTO> listEstadoResultadoCentroProduccion = UtilsJSON.jsonToList(ConFunBalanceCentroProduccionTO.class, map.get("ConBalanceResultadoCentroProduccionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.exportarReporteEstadoResultadoCentroProduccion(usuarioEmpresaReporteTO, listEstadoResultadoCentroProduccion, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteBalanceResultadosCentroProduccionMatricial")
    public RespuestaWebTO generarReporteBalanceResultadosCentroProduccionMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<ConFunBalanceCentroProduccionTO> listEstadoResultadoCentroProduccion = UtilsJSON.jsonToList(ConFunBalanceCentroProduccionTO.class, parametros.get("ConBalanceResultadoCentroProduccionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceResultadoCentroProduccion(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listEstadoResultadoCentroProduccion);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteBalanceResultadoCentroProduccion")
    public @ResponseBody
    String generarReporteBalanceResultadoCentroProduccion(HttpServletResponse response, @RequestBody Map<String, Object> parametros) throws Exception {
        String nombreReporte = "reportContabilidadBalanceResultadosCentroProduccion.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<ConFunBalanceCentroProduccionTO> listEstadoResultadoCentroProduccion = UtilsJSON.jsonToList(ConFunBalanceCentroProduccionTO.class, parametros.get("ConBalanceResultadoCentroProduccionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteContabilidadService.generarReporteBalanceResultadoCentroProduccion(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listEstadoResultadoCentroProduccion);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

}
