package ec.com.todocompu.ShrimpSoftServer.sistema.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEmpresaNotificacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.GrupoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.CedulaRuc;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.web.ArchivoSoporteTO;
import ec.com.todocompu.ShrimpSoftUtils.web.PermisosEmpresaMenuTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author dev-out-03
 */
@RestController
@RequestMapping("/todocompuWS/appWebController")
public class AppWebController {

    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private SisEmpresaNotificacionesDao sisEmpresaNotificacionesDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private GrupoService grupoService;

    @RequestMapping("/validarPeriodo")
    public ResponseEntity validarPeriodo(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, parametros.get("fecha"));
        try {
            SisPeriodo respues = periodoService.getPeriodoPorFecha(fecha, sisInfoTO.getEmpresa());
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("No se encuentra ningun periodo para la fecha que ingreso");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/validarPermisos")
    public ResponseEntity validarPermisos(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String item = SistemaWebUtil.obtenerFiltroComoString(parametros, "item");
        try {
            List<PermisosEmpresaMenuTO> respues = sistemaWebServicio.getEmpresasPorUsuarioItemAngular(sisInfoTO, item);
            if (respues != null) {
                for (PermisosEmpresaMenuTO respue : respues) {
                    SisGrupoTO sgTO = grupoService.sisGrupoUsuariosTO(respue.getEmpCodigo(), sisInfoTO.getUsuario());
                    respue.setListaSisPermisoTO(sgTO);
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("No se encontraron empresas para el usuario");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getFechaActual")
    public RespuestaWebTO getFechaActual(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            Timestamp respues = sistemaWebServicio.getFechaActual();
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getFechaInicioFinMes")
    public RespuestaWebTO getFechaInicioFinMes(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        List<Date> respues = new ArrayList<>();
        try {
            Date fechaInicio = sistemaWebServicio.getFechaInicioMes();
            Date fechaFin = sistemaWebServicio.getFechaFinMes();
            respues.add(fechaInicio);
            respues.add(fechaFin);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getFechaInicioActualMes")
    public RespuestaWebTO getFechaInicioActualMes(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        List<Date> respues = new ArrayList<>();
        try {
            Date fechaInicio = sistemaWebServicio.getFechaInicioMes();
            Date fechaActual = sistemaWebServicio.getFechaActual();
            respues.add(fechaInicio);
            respues.add(fechaActual);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarCedula")
    public RespuestaWebTO validarCedula(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String cedula = UtilsJSON.jsonToObjeto(String.class, map.get("cedula"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = CedulaRuc.comprobacion(cedula);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo("F".equals(respues.substring(0, 1)) ? respues.substring(1) : respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se realizó validación de cédula.");
            }
        } catch (Exception e) {
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarNotificacionCorreo")
    public RespuestaWebTO enviarNotificacionCorreoPedidos(@RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String correoDestinatario = UtilsJSON.jsonToObjeto(String.class, map.get("usuarioCorreo"));
        String notificacion = UtilsJSON.jsonToObjeto(String.class, map.get("notificacion"));
        SisEmpresaNotificaciones idNotificacion = UtilsJSON.jsonToObjeto(SisEmpresaNotificaciones.class, map.get("idNotificacion"));
        int idNotificacionEntero = UtilsJSON.jsonToObjeto(int.class, map.get("idNotificacionEntero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (idNotificacionEntero > 0) {
                idNotificacion = sisEmpresaNotificacionesDao.obtenerPorId(SisEmpresaNotificaciones.class, idNotificacionEntero);
            }
            String envioCorreo = envioCorreoService.enviarInformacionPedido(correoDestinatario, notificacion, idNotificacion, sisInfoTO.getEmpresa());
            if (envioCorreo.equals("Email sent!")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se han enviado las notificaciones a los usuarios");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al enviar notificaciones: " + envioCorreo);
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            resp.setOperacionMensaje("Ha ocurrido un error durante el envío de notificaciones a los usuarios (" + e.getMessage() + ")");
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoParaTicket")
    public RespuestaWebTO enviarCorreoParaTicket(@RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String nombreUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("nombreUsuario"));
        String usuarioCorreo = UtilsJSON.jsonToObjeto(String.class, map.get("usuarioCorreo"));
        String asunto = UtilsJSON.jsonToObjeto(String.class, map.get("asunto"));
        String descipcion = UtilsJSON.jsonToObjeto(String.class, map.get("descripcion"));
        List<ArchivoSoporteTO> listaArchivoSoporteTO = UtilsJSON.jsonToList(ArchivoSoporteTO.class, map.get("listaArchivoSoporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String mensaje = envioCorreoService.enviarCorreoParaTicket(nombreUsuario, usuarioCorreo, asunto, descipcion, sisInfoTO.getEmpresa(), listaArchivoSoporteTO);
            if (mensaje != null && mensaje.equals("Email sent!")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se ha generado el ticket.");
            } else {
                resp.setOperacionMensaje(mensaje != null ? mensaje : "Ocurrió un error al generar ticket.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            resp.setOperacionMensaje("Ha ocurrido un error durante el envío de notificaciones a los usuarios (" + e.getMessage() + ")");
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
}
