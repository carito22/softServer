package ec.com.todocompu.ShrimpSoftServer.banco.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.banco.report.ReporteBancoService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.BancoCajaService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.BancoDebitoService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.BancoService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequeNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequePostfechadoService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequeService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.ConciliacionService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.CuentaService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.PreavisosService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.DetalleService;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequePreavisadoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanDetalleChequesPosfechadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoEntregadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoRevisadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesNoImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanPreavisosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ChequeNoImpresoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ConsultaDatosBancoCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.listaBanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeMotivoReversado;
import ec.com.todocompu.ShrimpSoftUtils.banco.report.ReporteConciliacionCabecera;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaPostfechadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import ec.com.todocompu.ShrimpSoftServer.banco.service.BanChequeMotivoReversadoService;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionPK;
import java.util.ArrayList;

@RestController
@RequestMapping("/todocompuWS/bancoWebController")
public class BancoWebController {

    @Autowired
    private BancoService bancoService;
    @Autowired
    private PreavisosService preavisosService;
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private DetalleService detalleService;
    @Autowired
    private ConciliacionService conciliacionService;
    @Autowired
    private ChequeNumeracionService chequeNumeracionService;
    @Autowired
    private ChequeService chequeService;
    @Autowired
    private BancoCajaService cajaService;
    @Autowired
    private ReporteBancoService reporteBancoService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private ChequePostfechadoService chequePostfechadoService;
    @Autowired
    private BancoDebitoService bancoDebitoService;
    @Autowired
    private BanChequeMotivoReversadoService banChequeMotivoReversadoService;

    @RequestMapping("/getListaBanBancoTO")
    public RespuestaWebTO getListaBanBancoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ListaBanBancoTO> respuesta = bancoService.getListaBanBancoTO(empresa);
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

    //listaBancoDebito
    @RequestMapping("/getListaBancosDebitoTO")
    public RespuestaWebTO getListaBanBancoDebitoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<listaBanBancoDebitoTO> respuesta = bancoDebitoService.getListaBanBancoDebitoTO(empresa);
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

    //insertarBancoDebito
    @RequestMapping("/insertarBancoDebitoTO")
    public RespuestaWebTO insertarBancoDebitoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanBancoDebitoTO banBancoTO = UtilsJSON.jsonToObjeto(BanBancoDebitoTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (bancoDebitoService.insertarBancoDebitoTO(banBancoTO, sisInfoTO)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("El banco: Código " + banBancoTO.getBanCodigo() + ", se ha guardado correctamente.");

            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setExtraInfo(false);
                resp.setOperacionMensaje("El código del banco que va a ingresar ya existe...\nIntente con otro...");

            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //modificarBancoDebito
    @RequestMapping("/modificarBancoDebitoTO")
    public RespuestaWebTO modificarBancoDebitoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        BanBancoDebitoTO banBancoTO = UtilsJSON.jsonToObjeto(BanBancoDebitoTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (bancoDebitoService.modificarBancoDebitoTO(banBancoTO, sisInfoTO)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El banco: Código " + banBancoTO.getBanCodigo() + ", se ha modificado correctamente.");
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

    //eliminarBancoDebito
    @RequestMapping("/eliminarBancoDebitoTO")
    public RespuestaWebTO eliminarBancoDebitoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanBancoDebitoTO banBancoTO = UtilsJSON.jsonToObjeto(BanBancoDebitoTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (bancoDebitoService.eliminarBancoDebitoTO(banBancoTO, sisInfoTO)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El banco: Código " + banBancoTO.getBanCodigo() + ", se ha eliminado correctamente.");
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

    @RequestMapping("/bancosPorDefecto")
    public RespuestaWebTO bancosPorDefecto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<ListaBanBancoTO> respuesta = bancoService.getListaBanBancoTODefecto(empresa);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se han encontrado resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarChequesPostfechados")
    public RespuestaWebTO listarChequesPostfechados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCobrosDetalleFormaPostfechadoTO> respues = chequePostfechadoService.getListaChequesPostfechados(empresa, desde, hasta);
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

    @RequestMapping("/actualizarFechaVencimiento")
    public RespuestaWebTO actualizarFechaVencimiento(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        Date fechaVencimiento = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaVencimiento"));
        String banco = UtilsJSON.jsonToObjeto(String.class, map.get("banco"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = chequePostfechadoService.actualizarFechaVencimiento(secuencial, fechaVencimiento, banco, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La fecha de vencimiento se modificó correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se pudo actualizar fecha de vencimiento.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaChequesPostfechados")
    public RespuestaWebTO obtenerDatosParaChequesPostfechados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = chequePostfechadoService.obtenerDatosParaChequesPostfechados(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener datos para cheques postfechados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarChequesPostfechados")
    public @ResponseBody
    String exportarChequesPostfechados(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<CarCobrosDetalleFormaPostfechadoTO> listado = UtilsJSON.jsonToList(CarCobrosDetalleFormaPostfechadoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteBancoService.exportarChequesPostfechados(usuarioEmpresaReporteTO, listado, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarChequesPostfechados")
    public @ResponseBody
    String generarChequesPostfechados(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "reportChequesPosfechados.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<CarCobrosDetalleFormaPostfechadoTO> listado = UtilsJSON.jsonToList(CarCobrosDetalleFormaPostfechadoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarChequesPostfechados(usuarioEmpresaReporteTO, nombreReporte, listado, desde, hasta);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarContableDeposito")
    public RespuestaWebTO insertarContableDeposito(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String observacionesContable = UtilsJSON.jsonToObjeto(String.class, map.get("observacionesContable"));
        List<CarCobrosDetalleFormaPostfechadoTO> listaPosfechados = UtilsJSON.jsonToList(CarCobrosDetalleFormaPostfechadoTO.class, map.get("listaPosfechados"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = chequePostfechadoService.insertarContableDeposito(observacionesContable, listaPosfechados, fecha, cuenta, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable contable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado contable de deposito.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteBancos")
    public @ResponseBody
    String generarReporteBancos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportBanco.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ListaBanBancoTO> listado = UtilsJSON.jsonToList(ListaBanBancoTO.class, parametros.get("ListadoBancos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteBanco(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //insertar motivo de reversado cheque
    @RequestMapping("/insertarMotivoBancoReversadoCheque")
    public RespuestaWebTO insertarMotivoBancoReversadoCheque(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequeMotivoReversado banChequeMotivo = UtilsJSON.jsonToObjeto(BanChequeMotivoReversado.class, map.get("banChequeMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = banChequeMotivoReversadoService.insertarMotivoBancoReversoCheque(banChequeMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "El motivo de anulación no se a guardado");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setEstadoOperacion(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaMotivoReversadoCheque")
    public RespuestaWebTO getListaMotivoReversadoCheque(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanChequeMotivoReversado> respues = banChequeMotivoReversadoService.getListaMotivoReversadoCheque(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontro resultado");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarMotivoReversado")
    public RespuestaWebTO modificarMotivoReversado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequeMotivoReversado banChequeMotivo = UtilsJSON.jsonToObjeto(BanChequeMotivoReversado.class, map.get("banChequeMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = banChequeMotivoReversadoService.modificarMotivoReversado(banChequeMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se a podido modificar el registro");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarMotivoReversado")
    public RespuestaWebTO eliminarMotivoReversado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequeMotivoReversado banChequeMotivo = UtilsJSON.jsonToObjeto(BanChequeMotivoReversado.class, map.get("banChequeMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = banChequeMotivoReversadoService.eliminarMotivoReversado(banChequeMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se a podido eliminar el motivo de anulación");
            }

        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoMotivoReversado")
    public RespuestaWebTO modificarEstadoMotivoReversado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        BanChequeMotivoReversado banChequeMotivo = UtilsJSON.jsonToObjeto(BanChequeMotivoReversado.class, map.get("banChequeMotivo"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String respuesta = banChequeMotivoReversadoService.modificarEstadoMotivoReversado(banChequeMotivo, estado, sisInfoTO);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha podido modificar el estado del motivo");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteBancos")
    public @ResponseBody
    String exportarReporteBodegas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ListaBanBancoTO> listado = UtilsJSON.jsonToList(ListaBanBancoTO.class, map.get("ListadoBancos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteBancos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBancoTO")
    public RespuestaWebTO getBancoTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        try {
            BanBancoTO respues = bancoService.getBancoTO(empresa, codigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados de la búsqueda de banco.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaChequesNumeracionTO")
    public List<ListaBanChequesNumeracionTO> getListaChequesNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getListaChequesNumeracionTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBanCuentaTO")
    public RespuestaWebTO getListaBanCuentaTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ListaBanCuentaTO> respuesta = cuentaService.getListaBanCuentaTO(empresa);
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

    @RequestMapping("/getBancoCuentaTO")
    public RespuestaWebTO getBancoCuentaTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String ctaCuentaContable = UtilsJSON.jsonToObjeto(String.class, map.get("ctaCuentaContable"));
        try {
            BanCuentaTO respues = cuentaService.getBancoCuentaTO(empresa, ctaCuentaContable);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados de la búsqueda de la cuenta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //BANCOS
    @RequestMapping("/getListaChequesNoImpresosTO")
    public RespuestaWebTO getListaChequesNoImpresosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuentaBancaria = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaBancaria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanListaChequesNoImpresosTO> respues = chequeService.getListaChequesNoImpresosTO(empresa, cuentaBancaria);
            if (respues.size() > 0) {
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

    @RequestMapping("/visualizarChequeNoImpreso")
    public RespuestaWebTO visualizarChequeNoImpreso(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        BanListaChequesNoImpresosTO banListaChequesNoImpresosTO = UtilsJSON.jsonToObjeto(BanListaChequesNoImpresosTO.class, map.get("banListaChequesNoImpresosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            ChequeNoImpresoTO respues = chequeService.visualizarChequeNoImpreso(empresa, banListaChequesNoImpresosTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró cheque.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaChequesNoImpresosWebTO")
    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosWebTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String cuentaBancaria = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaBancaria"));
        String modulo = UtilsJSON.jsonToObjeto(String.class, map.get("modulo"));

        try {
            return chequeService.getListaChequesNoImpresosWebTO(empresa, cuentaBancaria, modulo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getConsultaDatosBancoCuentaTO")
    public ConsultaDatosBancoCuentaTO getConsultaDatosBancoCuentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bancoService.getConsultaDatosBancoCuentaTO(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/obtenerDatosParaCrudCuentas")
    public RespuestaWebTO obtenerDatosParaCrudCuentas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = cuentaService.obtenerDatosParaCrudCuentas(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para generar cuenta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/isExisteChequeAimprimir")
    public RespuestaWebTO isExisteChequeAimprimir(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContable"));
        String numeroCheque = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCheque"));
        Long detSecuencia = UtilsJSON.jsonToObjeto(Long.class, map.get("detSecuencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (chequeService.isExisteChequeAimprimir(empresa, cuentaContable, numeroCheque, detSecuencia)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("El cheque ya existe, ingrese otro número de cheque....");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
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

    @RequestMapping("/insertarBanBancoTO")
    public RespuestaWebTO insertarBanBancoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanBancoTO banBancoTO = UtilsJSON.jsonToObjeto(BanBancoTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (bancoService.insertarBanBancoTO(banBancoTO, sisInfoTO)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El banco: Código " + banBancoTO.getBanCodigo() + ", se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("El código del banco que va a ingresar ya existe...\nIntente con otro...");
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

    @RequestMapping("/insertarBanChequeNumeracionTO")
    public boolean insertarBanChequeNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequesNumeracionTO banBancoTO = UtilsJSON.jsonToObjeto(BanChequesNumeracionTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeNumeracionService.insertarBanChequeNumeracionTO(banBancoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/insertarBanCuentaTO")
    public RespuestaWebTO insertarBanCuentaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCuentaTO banCuentaTO = UtilsJSON.jsonToObjeto(BanCuentaTO.class, map.get("banCuentaTO"));
        String codigoBanco = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBanco"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (cuentaService.insertarBanCuentaTO(banCuentaTO, codigoBanco, sisInfoTO)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La cuenta: Nùmero " + banCuentaTO.getCtaNumero() + ", se ha guardado correctamente.");
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

    @RequestMapping("/imprimirReporteCuentas")
    public @ResponseBody
    String generarReporteCuentas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCuenta.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ListaBanCuentaTO> listado = UtilsJSON.jsonToList(ListaBanCuentaTO.class, parametros.get("ListadoCuentas"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteCuentas(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCuentas")
    public @ResponseBody
    String exportarReporteCuentas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ListaBanCuentaTO> listado = UtilsJSON.jsonToList(ListaBanCuentaTO.class, map.get("ListadoCuentas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteCuentas(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarBanChequeTO")
    public RespuestaWebTO insertarBanChequeTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequeTO banChequeTO = UtilsJSON.jsonToObjeto(BanChequeTO.class, map.get("banChequeTO"));
        String usrInserta = UtilsJSON.jsonToObjeto(String.class, map.get("usrInserta"));
        String numeroCheque = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCheque"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = chequeService.insertarBanChequeTO(banChequeTO, usrInserta, numeroCheque, empresa, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
            return resp;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarFechaBanChequeTO")
    public RespuestaWebTO modificarFechaBanChequeTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String usuario = sisInfoTO.getUsuario();
        try {
            String msje = chequeService.modificarFechaBanChequeTO(empresa, cuenta, numero, fecha, usuario, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
            return resp;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarNumeroBanChequeTO")
    public RespuestaWebTO modificarNumeroBanChequeTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String numeroNuevo = UtilsJSON.jsonToObjeto(String.class, map.get("numeroNuevo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String usuario = sisInfoTO.getUsuario();
        try {
            String msje = chequeService.modificarNumeroBanChequeTO(empresa, cuenta, numero, numeroNuevo, usuario, sisInfoTO);
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
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarBanBancoTO")
    public RespuestaWebTO modificarBanBancoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        BanBancoTO banBancoTO = UtilsJSON.jsonToObjeto(BanBancoTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (bancoService.modificarBanBancoTO(banBancoTO, sisInfoTO)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El banco: Código " + banBancoTO.getBanCodigo() + ", se ha modificado correctamente.");
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

    @RequestMapping("/modificarBanChequeNumeracionTO")
    public boolean modificarBanChequeNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequesNumeracionTO banBancoTO = UtilsJSON.jsonToObjeto(BanChequesNumeracionTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeNumeracionService.modificarBanChequeNumeracionTO(banBancoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarBanCuentaTO")
    public RespuestaWebTO modificarBanCuentaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCuentaTO banCuentaTO = UtilsJSON.jsonToObjeto(BanCuentaTO.class, map.get("banCuentaTO"));
        String codigoBanco = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBanco"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (cuentaService.modificarBanCuentaTO(banCuentaTO, codigoBanco, sisInfoTO)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La cuenta: Número " + banCuentaTO.getCtaNumero() + ", se ha modificado correctamente.");
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

    @RequestMapping("/eliminarBanBancoTO")
    public RespuestaWebTO eliminarBanBancoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanBancoTO banBancoTO = UtilsJSON.jsonToObjeto(BanBancoTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (bancoService.eliminarBanBancoTO(banBancoTO, sisInfoTO)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El banco: Código " + banBancoTO.getBanCodigo() + ", se ha eliminado correctamente.");
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

    @RequestMapping("/eliminarBanChequeNumeracionTO")
    public boolean eliminarBanChequeNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequesNumeracionTO banBancoTO = UtilsJSON.jsonToObjeto(BanChequesNumeracionTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeNumeracionService.eliminarBanChequeNumeracionTO(banBancoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarBanCuentaTO")
    public RespuestaWebTO eliminarBanCuentaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCuentaTO banCuentaTO = UtilsJSON.jsonToObjeto(BanCuentaTO.class, map.get("banCuentaTO"));
        String codigoBanco = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBanco"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (cuentaService.eliminarBanCuentaTO(banCuentaTO, codigoBanco, sisInfoTO)) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La cuenta: Número " + banCuentaTO.getCtaNumero() + ", se ha eliminado correctamente.");
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

    @RequestMapping("/getBanConciliacionFechaHasta")
    public String getBanConciliacionFechaHasta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.getBanConciliacionFechaHasta(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanComboBancoTO")
    public RespuestaWebTO getBanComboBancoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanComboBancoTO> respuesta = cuentaService.getBanComboBancoTO(empresa);
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

    @RequestMapping("/getBanListaConciliacionBancariaDebitoTO")
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaDebitoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.getBanListaConciliacionBancariaDebitoTO(empresa, cuenta, codigo, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanListaConciliacionBancariaCreditoTO")
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaCreditoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.getBanListaConciliacionBancariaCreditoTO(empresa, cuenta, codigo, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanValorSaldoSistema")
    public BigDecimal getBanValorSaldoSistema(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.getBanValorSaldoSistema(empresa, cuenta, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanListaChequeTO")
    public RespuestaWebTO getBanListaChequeTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanListaChequeTO> respues = chequeService.getBanListaChequeTO(empresa, cuenta, desde, hasta, tipo);
            if (respues.size() > 1) {
                respues.remove(0);
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

    @RequestMapping("/listarChequesImpresos")
    public RespuestaWebTO listarChequesImpresos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanListaChequesImpresosTO> respues = chequeService.getBanListaChequesImpresosTO(empresa, cuenta, desde, hasta);
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

    @RequestMapping("/getBanListaChequePostfechado")
    public RespuestaWebTO getBanListaChequePostfechado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String grupo = UtilsJSON.jsonToObjeto(String.class, map.get("grupo"));
        boolean ichfa = UtilsJSON.jsonToObjeto(boolean.class, map.get("ichfa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanDetalleChequesPosfechadosTO> respues = chequeService.getBanListaChequePostfechado(empresa, sector, cliente, desde, hasta, grupo, ichfa);
            List<BanDetalleChequesPosfechadosTO> chequesAnt = chequeService.getBanListaChequePostfechadoAnticipos(empresa, sector, cliente, desde, hasta, grupo, ichfa);
            if (respues == null || respues.size() == 1) {
                respues = new ArrayList<>();
            }
            if (chequesAnt != null && chequesAnt.size() > 0) {
                int i = 1;
                for (BanDetalleChequesPosfechadosTO entry : chequesAnt) {
//                entry.setChqTipo("ANT");//anticipo
                    entry.setId(respues.size() + i);
                    respues.add(entry);
                    i++;
                }
            }
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

    @RequestMapping("/imprimirReporteChequesCobrar")
    public @ResponseBody
    String imprimirReporteChequesCobrar(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportChequeCobrar.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequeTO> listadoChequesCobrar = UtilsJSON.jsonToList(BanListaChequeTO.class, parametros.get("listadoChequesCobrar"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesListado(usuarioEmpresaReporteTO, listadoChequesCobrar);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCheques")
    public @ResponseBody
    String exportarReporteCheques(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanListaChequeTO> listadoChequesCobrar = UtilsJSON.jsonToList(BanListaChequeTO.class, map.get("listadoCheques"));
        String tipoCheque = UtilsJSON.jsonToObjeto(String.class, map.get("tipoCheque"));
        String banco = UtilsJSON.jsonToObjeto(String.class, map.get("banco"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteCheque(usuarioEmpresaReporteTO, listadoChequesCobrar, tipoCheque, banco, cuenta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteChequesImpresos")
    public @ResponseBody
    String exportarReporteChequesImpresos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanListaChequesImpresosTO> listadoChequesCobrar = UtilsJSON.jsonToList(BanListaChequesImpresosTO.class, map.get("listadoCheques"));
        String tipoCheque = UtilsJSON.jsonToObjeto(String.class, map.get("tipoCheque"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteChequesImpresos(usuarioEmpresaReporteTO, listadoChequesCobrar, tipoCheque);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteChequesPostfechados")
    public @ResponseBody
    String exportarReporteChequesPostfechados(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanDetalleChequesPosfechadosTO> banDetalleChequesPosfechados = UtilsJSON.jsonToList(BanDetalleChequesPosfechadosTO.class, map.get("listadoChequesPostfechado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteChequesPostfechados(usuarioEmpresaReporteTO, banDetalleChequesPosfechados);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirReporteChequesEmision")
    public @ResponseBody
    String imprimirReporteChequesEmision(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportChequeEmision.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequeTO> listadoChequesEmision = UtilsJSON.jsonToList(BanListaChequeTO.class, parametros.get("listadoChequesEmision"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesEmision(usuarioEmpresaReporteTO, listadoChequesEmision);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirReporteChequesImpresos")
    public @ResponseBody
    String imprimirReporteChequesImpresos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportChequeImpresos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequesImpresosTO> listadoCheques = UtilsJSON.jsonToList(BanListaChequesImpresosTO.class, parametros.get("listadoChequesImpresion"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesImpresos(usuarioEmpresaReporteTO, listadoCheques);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirReporteChequesNumero")
    public @ResponseBody
    String imprimirReporteChequesNumero(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportChequeNumero.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequeTO> listadoChequesEmision = UtilsJSON.jsonToList(BanListaChequeTO.class, parametros.get("listadoChequesNumero"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesNumero(usuarioEmpresaReporteTO, listadoChequesEmision);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirReporteChequesVencimiento")
    public @ResponseBody
    String imprimirReporteChequesVencimiento(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportChequeVencimiento.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequeTO> listadoChequesEmision = UtilsJSON.jsonToList(BanListaChequeTO.class, parametros.get("listadoChequesVencimiento"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesVencimiento(usuarioEmpresaReporteTO, listadoChequesEmision);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanComboCuentaTO")
    public List<BanComboCuentaTO> getBanComboCuentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return cuentaService.getBanComboCuentaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanFunChequesNoEntregadosTO")
    public RespuestaWebTO getBanFunChequesNoEntregadosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanFunChequesNoEntregadosTO> respues = chequeService.getBanFunChequesNoEntregadosTO(empresa, cuenta);
            if (respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteChequesNoEntregados")
    public @ResponseBody
    String imprimirReporteChequesNoEntregados(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportChequeNoEntregados.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanFunChequesNoEntregadosTO> listadoChequesNoEntregados = UtilsJSON.jsonToList(BanFunChequesNoEntregadosTO.class, parametros.get("listadoChequeNoEntregadosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesNoEntregados(usuarioEmpresaReporteTO, listadoChequesNoEntregados);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteChequesNoEntregados")
    public @ResponseBody
    String exportarReporteChequesNoEntregados(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanFunChequesNoEntregadosTO> listadoChequesNoEntregados = UtilsJSON.jsonToList(BanFunChequesNoEntregadosTO.class, map.get("listadoChequeNoEntregadosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteChequesNoEntregados(usuarioEmpresaReporteTO, listadoChequesNoEntregados);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarBanFunChequesNoEntregados")
    public RespuestaWebTO insertarBanFunChequesNoEntregados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        List<BanFunChequesNoEntregadosTO> banFunChequesNoEntregadosTOs = UtilsJSON
                .jsonToList(BanFunChequesNoEntregadosTO.class, map.get("banFunChequesNoEntregadosTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String usuario = sisInfoTO.getUsuario();
        try {
            List<String> respues = chequeService.insertarBanFunChequesNoEntregados(empresa, cuenta, banFunChequesNoEntregadosTOs,
                    usuario, sisInfoTO);
            if (respues != null && respues.size() > 0) {
                if (respues.get(0).charAt(0) == 'T') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(respues.get(0).substring(1));
                    resp.setExtraInfo(respues);
                } else {
                    resp.setOperacionMensaje(respues.get(0).substring(1) + ". " + respues.get(1).substring(1));
                }
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

    @RequestMapping("/getBanFunChequesNoRevisadosTO")
    public RespuestaWebTO getBanFunChequesNoRevisadosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanFunChequesNoRevisadosTO> respues = chequeService.getBanFunChequesNoRevisadosTO(empresa, cuenta);
            if (respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteChequesNoRevisados")
    public @ResponseBody
    String imprimirReporteChequesNoRevisados(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportChequeNoRevisados.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanFunChequesNoRevisadosTO> listadoChequesNoRevisados = UtilsJSON.jsonToList(BanFunChequesNoRevisadosTO.class, parametros.get("listadoChequeNoRevisadosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesNoRevisados(usuarioEmpresaReporteTO, listadoChequesNoRevisados);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteChequesNoRevisados")
    public @ResponseBody
    String exportarReporteChequesNoRevisados(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanFunChequesNoRevisadosTO> BanFunChequesNoRevisadosTO = UtilsJSON.jsonToList(BanFunChequesNoRevisadosTO.class, map.get("listadoChequeNoRevisadosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteChequesNoRevisados(usuarioEmpresaReporteTO, BanFunChequesNoRevisadosTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarBanFunChequesNoRevisados")
    public RespuestaWebTO insertarBanFunChequesNoRevisados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        List<BanFunChequesNoRevisadosTO> banFunChequesNoRevisadosTOs = UtilsJSON.jsonToList(BanFunChequesNoRevisadosTO.class, map.get("banFunChequesNoRevisadosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String usuario = sisInfoTO.getUsuario();
        try {
            List<String> respues = chequeService.insertarBanFunChequesNoRevisados(empresa, cuenta, banFunChequesNoRevisadosTOs, usuario,
                    sisInfoTO);
            if (respues != null && respues.size() > 0) {
                if (respues.get(0).charAt(0) == 'T') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(respues.get(0).substring(1));
                    resp.setExtraInfo(respues);
                } else {
                    resp.setOperacionMensaje(respues.get(0).substring(1));
                }
            } else {
                resp.setOperacionMensaje("No se insertaron cheques revisados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/getBanCuentasContablesBancos")
    public List<String> getBanCuentasContablesBancos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.getBanCuentasContablesBancos(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanFunChequesPreavisados")
    public List<BanChequePreavisadoTO> getBanFunChequesPreavisados(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getBanFunChequesPreavisados(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConteoChequesPreavisados")
    public int getConteoChequesPreavisados(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getConteoChequesPreavisados(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return 0;
    }

    @RequestMapping("/insertarPreaviso")
    public String insertarPreaviso(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanPreavisosTO banPreavisosTO = UtilsJSON.jsonToObjeto(BanPreavisosTO.class, map.get("banPreavisosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preavisosService.insertarPreaviso(banPreavisosTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarBanPreaviso")
    public String eliminarBanPreaviso(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preavisosService.eliminarBanPreaviso(empresa, usuario, cuenta, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/cambioDeCheque")
    public RespuestaWebTO cambioDeCheque(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String cuentaActual = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaActual"));
        String chequeAnterior = UtilsJSON.jsonToObjeto(String.class, map.get("chequeAnterior"));
        String chequeNuevo = UtilsJSON.jsonToObjeto(String.class, map.get("chequeNuevo"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String con_tipo_cod = UtilsJSON.jsonToObjeto(String.class, map.get("con_tipo_cod"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String usuario = sisInfoTO.getUsuario();
        try {
            MensajeTO mensajeTO = chequeService.cambioDeChequeWeb(cuenta, cuentaActual, chequeAnterior, chequeNuevo,
                    empresa, usuario, observaciones, fecha, con_tipo_cod, sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1, mensajeTO.getMensaje().length()));
                resp.setExtraInfo(conContable.getConContablePK());
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1, mensajeTO.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoChequeEmisionVencimientoCobrarNumero")
    public byte[] generarReporteListadoChequeEmisionVencimientoCobrarNumero(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        List<BanListaChequeTO> listBanListaChequeTO = UtilsJSON.jsonToList(BanListaChequeTO.class,
                map.get("listBanListaChequeTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteBancoService.generarReporteListadoChequeEmisionVencimientoCobrarNumero(
                    usuarioEmpresaReporteTO, cuenta, desde, hasta, tipo, listBanListaChequeTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirReporteChequesNoImpresos")
    public @ResponseBody
    String imprimirReporteChequesNoImpresos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportChequeNoImpresos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequesNoImpresosTO> listadoChequesNoImpresos = UtilsJSON.jsonToList(BanListaChequesNoImpresosTO.class, parametros.get("listadoChequeNoImpresosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesNoImpresos(usuarioEmpresaReporteTO, listadoChequesNoImpresos);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteChequesNoImpresos")
    public @ResponseBody
    String exportarReporteChequesNoImpresos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanListaChequesNoImpresosTO> listadoChequesNoImpresos = UtilsJSON.jsonToList(BanListaChequesNoImpresosTO.class, map.get("listadoChequeNoImpresosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteChequesNoImpresos(usuarioEmpresaReporteTO, listadoChequesNoImpresos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCheque")
    public String generarReporteCheque(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportChequeInternacionalAngarshrimp.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        BanChequeTO banChequeTO = UtilsJSON.jsonToObjeto(BanChequeTO.class, parametros.get("banChequeTO"));
        String valorLetra1 = UtilsJSON.jsonToObjeto(String.class, parametros.get("valorLetra1"));
        String valorLetra2 = UtilsJSON.jsonToObjeto(String.class, parametros.get("valorLetra2"));
        String cheqDiferente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cheqDiferente"));
        String nombreReporteCheque = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporteCheque"));
        String secuencia = UtilsJSON.jsonToObjeto(String.class, parametros.get("secuencia"));
        String cuentaCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("cuentaCodigo"));
        String fechaActual = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaActual"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            ConDetalle conDetalle = detalleService.buscarDetalleContable(Long.parseLong(secuencia));
            if (cheqDiferente.equals("S")) {
                return "El cheque ya existe, ingrese otro número de cheque...";
            } else {
                // El cheque no existe
                if (conDetalle != null) {
                    banChequeTO.setChqFecha(fechaActual);
                    respuesta = reporteBancoService.generarReporteCheque(usuarioEmpresaReporteTO, banChequeTO, valorLetra1,
                            valorLetra2, nombreReporteCheque, conDetalle, cuentaCodigo);
                    return archivoService.generarReportePDF(respuesta, nombreReporteCheque, response);
                } else {
                    return "No se pudo establecer la relación CON_DETALLE-BAN_CHEQUE.\nContacte con el administrador...";
                }
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteChequeMatricial")
    public RespuestaWebTO generarReporteChequeMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        RespuestaWebTO resp = new RespuestaWebTO();
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        BanChequeTO banChequeTO = UtilsJSON.jsonToObjeto(BanChequeTO.class, parametros.get("banChequeTO"));
        String valorLetra1 = UtilsJSON.jsonToObjeto(String.class, parametros.get("valorLetra1"));
        String valorLetra2 = UtilsJSON.jsonToObjeto(String.class, parametros.get("valorLetra2"));
        String cheqDiferente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cheqDiferente"));
        String nombreReporteCheque = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporteCheque"));
        String secuencia = UtilsJSON.jsonToObjeto(String.class, parametros.get("secuencia"));
        String cuentaCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("cuentaCodigo"));
        String fechaActual = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaActual"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            ConDetalle conDetalle = detalleService.buscarDetalleContable(Long.parseLong(secuencia));
            if (cheqDiferente.equals("S")) {
                resp.setOperacionMensaje("El cheque ya existe, ingrese otro número de cheque...");
            } else {
                // El cheque no existe
                if (conDetalle != null) {
                    banChequeTO.setChqFecha(fechaActual);
                    respuesta = reporteBancoService.generarReporteCheque(usuarioEmpresaReporteTO, banChequeTO, valorLetra1,
                            valorLetra2, nombreReporteCheque, conDetalle, cuentaCodigo);
                    resp.setExtraInfo(respuesta);
                } else {
                    resp.setOperacionMensaje("No se pudo establecer la relación CON_DETALLE-BAN_CHEQUE.\nContacte con el administrador...");
                }
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarNoChequeChequesNoImpresos")
    public RespuestaWebTO generarNoChequeChequesNoImpresos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequeTO banChequeTO = UtilsJSON.jsonToObjeto(BanChequeTO.class, map.get("banChequeTO"));
        String cheqDiferente = UtilsJSON.jsonToObjeto(String.class, map.get("cheqDiferente"));
        String secuencia = UtilsJSON.jsonToObjeto(String.class, map.get("secuencia"));
        String fechaActual = UtilsJSON.jsonToObjeto(String.class, map.get("fechaActual"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String numeroCheque = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCheque"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String usrInserta = sisInfoTO.getUsuario();
        try {
            String msje;
            ConDetalle conDetalle = detalleService.buscarDetalleContable(Long.parseLong(secuencia));
            if (cheqDiferente.equals("S")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                msje = "El cheque ya existe, ingrese otro número de cheque...";
                resp.setOperacionMensaje(msje);
            } else {
                // El cheque no existe
                if (conDetalle != null) {
                    banChequeTO.setChqFecha(fechaActual);
                    msje = "TSolo mucho exito";
                    msje = chequeService.insertarBanChequeTO(banChequeTO, usrInserta, numeroCheque, empresa, sisInfoTO);
                    if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        resp.setOperacionMensaje("El cheque: Nª " + numeroCheque + ", se ha guardado como no cheque.");
                        resp.setExtraInfo(true);
                    } else {
                        resp.setOperacionMensaje(msje.substring(1, msje.length()));
                    }
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    msje = "\"No se pudo establecer la relación CON_DETALLE-BAN_CHEQUE.\\nContacte con el administrador...\"";
                    resp.setOperacionMensaje(msje);
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getBanChequeSecuencial")
    public Object getBanChequeSecuencial(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getBanChequeSecuencial(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getValidarCuentaContableConBancoExiste")
    public List<String> getValidarCuentaContableConBancoExiste(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String banEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("banEmpresa"));
        String banCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("banCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.getValidarCuentaContableConBancoExiste(banEmpresa, banCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //CONCILIACION BANCARIA
    @RequestMapping("/conciliacionPendiente")
    public RespuestaWebTO conciliacionPendiente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean conciliacionPendiente = conciliacionService.conciliacionPendiente(empresa, cuentaContable);
            if (conciliacionPendiente) {
                resp.setOperacionMensaje("Existe conciliación pendiente..");
            }
            resp.setExtraInfo(conciliacionPendiente);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/conciliacionHasta")
    public RespuestaWebTO conciliacionHasta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean conciliacionHasta = conciliacionService.conciliacionHasta(empresa, hasta, cuenta);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(conciliacionHasta);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getBanListaConciliacionTO")
    public RespuestaWebTO getBanListaConciliacionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanListaConciliacionTO> respuesta = conciliacionService.getBanListaConciliacionTO(empresa, buscar);
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

    @RequestMapping("/obtenerDatosConciliacionNueva")
    public RespuestaWebTO obtenerDatosConciliacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String codigoConciliacion = UtilsJSON.jsonToObjeto(String.class, map.get("codigoConciliacion"));
        String cuentaBanco = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            map.put("listaConciliacionBancariaDebito", conciliacionService.getBanListaConciliacionBancariaDebitoTO(empresa, cuentaBanco, codigoConciliacion, fechaHasta));
            Map<String, Object> respues = conciliacionService.obtenerDatosConciliacion(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener conciliación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionBanConciliacionTO")
    public RespuestaWebTO accionBanConciliacionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanConciliacionTO banConciliacionTO = UtilsJSON.jsonToObjeto(BanConciliacionTO.class, map.get("banConciliacionTO"));
        List<BanChequeTO> banChequeTOs = UtilsJSON.jsonToList(BanChequeTO.class, map.get("listaBanChequeTOs"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        List<BanConciliacionDatosAdjuntos> listadoImagenes = UtilsJSON.jsonToList(BanConciliacionDatosAdjuntos.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = conciliacionService.accionBanConciliacionTO(banConciliacionTO, banChequeTOs, listadoImagenes, accion, sisInfoTO);
            if (respues != null && respues.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/guardarBanConciliacionTO")
    public RespuestaWebTO guardarBanConciliacionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanConciliacionTO banConciliacionTO = UtilsJSON.jsonToObjeto(BanConciliacionTO.class, map.get("banConciliacionTO"));
        List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listaConciliacionBancariaDebito"));
        List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listaConciliacionBancariaCredito"));
        List<BanConciliacionDatosAdjuntos> listadoImagenes = UtilsJSON.jsonToList(BanConciliacionDatosAdjuntos.class, map.get("listaImagenes"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = conciliacionService.guardarBanConciliacionTO(banConciliacionTO, listaConciliacionBancariaDebito, listaConciliacionBancariaCredito, listadoImagenes, sisInfoTO);
            if (respues != null && respues.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarBanConciliacionTO")
    public RespuestaWebTO modificarBanConciliacionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanConciliacionTO banConciliacionTO = UtilsJSON.jsonToObjeto(BanConciliacionTO.class, map.get("banConciliacionTO"));
        List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listaConciliacionBancariaDebito"));
        List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listaConciliacionBancariaCredito"));
        List<BanConciliacionDatosAdjuntos> listadoImagenes = UtilsJSON.jsonToList(BanConciliacionDatosAdjuntos.class, map.get("listaImagenes"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = conciliacionService.modificarBanConciliacionTO(banConciliacionTO, listaConciliacionBancariaDebito, listaConciliacionBancariaCredito, listadoImagenes, sisInfoTO);
            if (respues != null && respues.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarBanConciliacionTO")
    public RespuestaWebTO eliminarBanConciliacionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanConciliacionTO banConciliacionTO = UtilsJSON.jsonToObjeto(BanConciliacionTO.class, map.get("banConciliacionTO"));
        List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listaConciliacionBancariaDebito"));
        List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listaConciliacionBancariaCredito"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = conciliacionService.eliminarBanConciliacionTO(banConciliacionTO, listaConciliacionBancariaDebito, listaConciliacionBancariaCredito, sisInfoTO);
            if (respues != null && respues.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConciliacion")
    public @ResponseBody
    String generarReporteConciliacion(HttpServletResponse response, @RequestBody Map<String, Object> map) {
        String nombreReporte = "reportConciliacion.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        int estado = UtilsJSON.jsonToObjeto(int.class, map.get("estado"));
        ReporteConciliacionCabecera conciliacionCabecera = UtilsJSON.jsonToObjeto(ReporteConciliacionCabecera.class, map.get("conciliacionCabecera"));
        List<BanListaConciliacionBancariaTO> listConciliacionCredito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listConciliacionCredito"));
        List<BanListaConciliacionBancariaTO> listConciliacionDebito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listConciliacionDebito"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.generarReporteConciliacion(usuarioEmpresaReporteTO, estado, conciliacionCabecera, listConciliacionCredito, listConciliacionDebito);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoConciliacion")
    public @ResponseBody
    String generarReporteListadoConciliacion(HttpServletResponse response, @RequestBody Map<String, Object> map) {
        String nombreReporte = "reportListadoConciliacion.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanListaConciliacionTO> listado = UtilsJSON.jsonToList(BanListaConciliacionTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.generarReporteListadoConciliacion(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //matricial
    @RequestMapping("/generarReporteListadoConciliacionMatricial")
    public RespuestaWebTO generarReporteListadoConciliacionMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaConciliacionTO> listado = UtilsJSON.jsonToList(BanListaConciliacionTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteListadoConciliacion(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteMotivoReversado")
    public @ResponseBody
    String exportarReporteMotivoReversado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanChequeMotivoReversado> banChequeMotivo = UtilsJSON.jsonToList(BanChequeMotivoReversado.class, map.get("banChequeMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteMotivoReverso(usuarioEmpresaReporteTO, banChequeMotivo);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteMotivoAnulacionMatricial")
    public RespuestaWebTO generarReporteMotivoAnulacionMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanChequeMotivoReversado> banChequeMotivo = UtilsJSON.jsonToList(BanChequeMotivoReversado.class, parametros.get("banChequeMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.generarReporteMotivoAnulacion(usuarioEmpresaReporteTO, banChequeMotivo);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteMotivoAnulacion")
    public @ResponseBody
    String generarReporteLiquidacionMotivo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionMotivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanChequeMotivoReversado> banChequeMotivo = UtilsJSON.jsonToList(BanChequeMotivoReversado.class, parametros.get("banChequeMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.generarReporteMotivoAnulacion(usuarioEmpresaReporteTO, banChequeMotivo);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConciliacion")
    public @ResponseBody
    String exportarReporteConciliacion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String estado = UtilsJSON.jsonToObjeto(String.class, map.get("estado"));
        String pendiente = UtilsJSON.jsonToObjeto(String.class, map.get("pendiente"));
        ReporteConciliacionCabecera conciliacionCabecera = UtilsJSON.jsonToObjeto(ReporteConciliacionCabecera.class, map.get("conciliacionCabecera"));
        List<BanListaConciliacionBancariaTO> listConciliacionCredito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listConciliacionCredito"));
        List<BanListaConciliacionBancariaTO> listConciliacionDebito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listConciliacionDebito"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarChequesConciliacion(usuarioEmpresaReporteTO, estado, conciliacionCabecera, pendiente, listConciliacionCredito, listConciliacionDebito);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoConciliacion")
    public @ResponseBody
    String exportarReporteListadoConciliacion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanListaConciliacionTO> listado = UtilsJSON.jsonToList(BanListaConciliacionTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteListadoConciliacion(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //Caja
    @RequestMapping("/getListaBanCajaTO")
    public RespuestaWebTO getListaBanCajaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanCajaTO> respues = cajaService.getListBanCajaTO(empresa);
            if (respues.size() > 0) {
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

    @RequestMapping("/insertarBanCajaTO")
    public RespuestaWebTO insertarBanCajaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCajaTO banCajaTO = UtilsJSON.jsonToObjeto(BanCajaTO.class, map.get("banCajaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = cajaService.insertarBanCajaTO(banCajaTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La caja: código " + banCajaTO.getCajaCodigo() + ", se ha guardado correctamente.");
            } else {
                resp.setOperacionMensaje("No se pudo insertar la caja.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarBanCajaTO")
    public RespuestaWebTO modificarBanCajaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCajaTO banCajaTO = UtilsJSON.jsonToObjeto(BanCajaTO.class, map.get("banCajaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = cajaService.modificarBanCajaTO(banCajaTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La caja: código " + banCajaTO.getCajaCodigo() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje("No se pudo modificar la caja.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarBanCajaTO")
    public RespuestaWebTO eliminarBanCajaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCajaTO banCajaTO = UtilsJSON.jsonToObjeto(BanCajaTO.class, map.get("banCajaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = cajaService.eliminarBanCajaTO(banCajaTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La caja: código " + banCajaTO.getCajaCodigo() + ", se ha eliminado correctamente.");
            } else {
                resp.setOperacionMensaje("No se pudo eliminar la caja.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoBanCajaTO")
    public @ResponseBody
    String generarReporteListadoBanCajaTO(HttpServletResponse response, @RequestBody Map<String, Object> map) {
        String nombreReporte = "reportListadoCaja.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanCajaTO> listado = UtilsJSON.jsonToList(BanCajaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.generarReporteListadoBanCajaTO(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteBanCajaTO")
    public @ResponseBody
    String exportarReporteBanCajaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanCajaTO> listado = UtilsJSON.jsonToList(BanCajaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteBanCajaTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerDatosParaConciliacion")
    public RespuestaWebTO obtenerDatosParaConciliacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = conciliacionService.obtenerDatosParaConciliacion(map);
            if (respues != null) {
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
    @RequestMapping("/generarReporteimprimirBancosMatricial")
    public RespuestaWebTO generarReporteimprimirBancosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ListaBanBancoTO> listado = UtilsJSON.jsonToList(ListaBanBancoTO.class, parametros.get("ListadoBancos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteBanco(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoBanCajaTOMatricial")
    public RespuestaWebTO generarReporteListadoBanCajaTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportListadoCaja.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<BanCajaTO> listado = UtilsJSON.jsonToList(BanCajaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.generarReporteListadoBanCajaTO(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirCuentasMatricial")
    public RespuestaWebTO generarReporteImprimirCuentasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ListaBanCuentaTO> listado = UtilsJSON.jsonToList(ListaBanCuentaTO.class, parametros.get("ListadoCuentas"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteCuentas(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConciliacionMatricial")
    public RespuestaWebTO generarReporteConciliacionMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        int estado = UtilsJSON.jsonToObjeto(int.class, map.get("estado"));
        ReporteConciliacionCabecera conciliacionCabecera = UtilsJSON.jsonToObjeto(ReporteConciliacionCabecera.class, map.get("conciliacionCabecera"));
        List<BanListaConciliacionBancariaTO> listConciliacionCredito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listConciliacionCredito"));
        List<BanListaConciliacionBancariaTO> listConciliacionDebito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listConciliacionDebito"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.generarReporteConciliacion(usuarioEmpresaReporteTO, estado, conciliacionCabecera, listConciliacionCredito, listConciliacionDebito);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesMatricial")
    public RespuestaWebTO generarReporteImprimirChequesMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequeTO> listadoChequesCobrar = UtilsJSON.jsonToList(BanListaChequeTO.class, parametros.get("listadoChequesCobrar"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesListado(usuarioEmpresaReporteTO, listadoChequesCobrar);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesEmisionMatricial")
    public RespuestaWebTO generarReporteImprimirChequesEmisionMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequeTO> listadoChequesEmision = UtilsJSON.jsonToList(BanListaChequeTO.class, parametros.get("listadoChequesEmision"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesEmision(usuarioEmpresaReporteTO, listadoChequesEmision);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesImpresosMatricial")
    public RespuestaWebTO generarReporteImprimirChequesImpresosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequesImpresosTO> listadoCheques = UtilsJSON.jsonToList(BanListaChequesImpresosTO.class, parametros.get("listadoChequesImpresos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesImpresos(usuarioEmpresaReporteTO, listadoCheques);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesVencimientoMatricial")
    public RespuestaWebTO generarReporteImprimirChequesVencimientoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequeTO> listadoChequesEmision = UtilsJSON.jsonToList(BanListaChequeTO.class, parametros.get("listadoChequesVencimiento"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesVencimiento(usuarioEmpresaReporteTO, listadoChequesEmision);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesNoEntregadosMatricial")
    public RespuestaWebTO generarReporteImprimirChequesNoEntregadosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanFunChequesNoEntregadosTO> listadoChequesNoEntregados = UtilsJSON.jsonToList(BanFunChequesNoEntregadosTO.class, parametros.get("listadoChequeNoEntregadosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesNoEntregados(usuarioEmpresaReporteTO, listadoChequesNoEntregados);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesPostfechados")
    public RespuestaWebTO generarReporteImprimirChequesPostfechados(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanDetalleChequesPosfechadosTO> banDetalleChequesPosfechados = UtilsJSON.jsonToList(BanDetalleChequesPosfechadosTO.class, parametros.get("listadoChequesPostfechado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteImprimirChequesPostfechados(usuarioEmpresaReporteTO, banDetalleChequesPosfechados);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirListadoChequesPostfechadosMatricial")
    public RespuestaWebTO generarReporteImprimirListadoChequesPostfechadosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanDetalleChequesPosfechadosTO> banDetalleChequesPosfechados = UtilsJSON.jsonToList(BanDetalleChequesPosfechadosTO.class, parametros.get("listadoChequesPostfechado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteImprimirChequesPostfechados(usuarioEmpresaReporteTO, banDetalleChequesPosfechados);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesNoImpresosMatricial")
    public RespuestaWebTO generarReporteImprimirChequesNoImpresosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequesNoImpresosTO> listadoChequesNoImpresos = UtilsJSON.jsonToList(BanListaChequesNoImpresosTO.class, parametros.get("listadoChequeNoImpresosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesNoImpresos(usuarioEmpresaReporteTO, listadoChequesNoImpresos);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesNoRevisadosMatricial")
    public RespuestaWebTO generarReporteImprimirChequesNoRevisadosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanFunChequesNoRevisadosTO> listadoChequesNoRevisados = UtilsJSON.jsonToList(BanFunChequesNoRevisadosTO.class, parametros.get("listadoChequeNoRevisadosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesNoRevisados(usuarioEmpresaReporteTO, listadoChequesNoRevisados);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesPostfechadosMatricial")
    public RespuestaWebTO generarReporteImprimirChequesPostfechadosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportChequesPosfechados.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<CarCobrosDetalleFormaPostfechadoTO> listado = UtilsJSON.jsonToList(CarCobrosDetalleFormaPostfechadoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarChequesPostfechados(usuarioEmpresaReporteTO, nombreReporte, listado, desde, hasta);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirChequesNumeroMatricial")
    public RespuestaWebTO generarReporteImprimirChequesNumeroMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<BanListaChequeTO> listadoChequesEmision = UtilsJSON.jsonToList(BanListaChequeTO.class, parametros.get("listadoChequesNumero"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteChequesNumero(usuarioEmpresaReporteTO, listadoChequesEmision);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarChequesReversados")
    public RespuestaWebTO listarChequesReversados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ConDetalle> respues = chequeService.listarChequesReversados(empresa, desde, hasta);
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

    @RequestMapping("/generarReporteListadoChequesAnuladosMatricial")
    public RespuestaWebTO generarReporteListadoChequesAnuladosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConDetalle> listadoChequesAnulados = UtilsJSON.jsonToList(ConDetalle.class, parametros.get("listadoChequesAnulados"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteListadoChequesAnulados(usuarioEmpresaReporteTO, listadoChequesAnulados);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoChequesAnulados")
    public RespuestaWebTO generarReporteListadoChequesAnulados(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ConDetalle> listadoChequesAnulados = UtilsJSON.jsonToList(ConDetalle.class, parametros.get("listadoChequesAnulados"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteBancoService.generarReporteListadoChequesAnulados(usuarioEmpresaReporteTO, listadoChequesAnulados);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteChequesReversados")
    public @ResponseBody
    String exportarReporteChequesReversados(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ConDetalle> listadoChequesAnulados = UtilsJSON.jsonToList(ConDetalle.class, map.get("listadoChequesAnulados"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteBancoService.exportarReporteChequesReversados(usuarioEmpresaReporteTO, listadoChequesAnulados);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/reversarCheque")
    public RespuestaWebTO reversarCheque(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cp = UtilsJSON.jsonToObjeto(String.class, map.get("cp"));
        String cheque = UtilsJSON.jsonToObjeto(String.class, map.get("cheque"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = chequeService.reversarChequeWeb(empresa, motivo, cp, cuenta, cheque, fecha, observaciones, sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1, mensajeTO.getMensaje().length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1, mensajeTO.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaChequesReversar")
    public RespuestaWebTO obtenerDatosParaChequesReversar(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = chequePostfechadoService.obtenerDatosParaChequesReversar(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener datos para cheques postfechados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/reutilizarCheque")
    public RespuestaWebTO reutilizarCheque(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Long secuencial = UtilsJSON.jsonToObjeto(Long.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String rpta = chequeService.reutilizarCheque(secuencial, sisInfoTO);
            if (rpta != null && rpta.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(rpta.substring(1));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se logró actualizar el estado");
            }
            return resp;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarImagenesBanConciliacion")
    public RespuestaWebTO listarImagenesBanConciliacion(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanConciliacionPK pk = UtilsJSON.jsonToObjeto(BanConciliacionPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<BanConciliacionDatosAdjuntos> respues = conciliacionService.listarImagenesDeBanConciliacion(pk);
            if (respues != null) {
                respues.forEach((detalle) -> {
                    detalle.setBanConciliacion(new BanConciliacion(detalle.getBanConciliacion().getBanConciliacionPK()));
                });
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron imágenes de liquidación de pesca.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

}
