package ec.com.todocompu.ShrimpSoftServer.security;

import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import org.springframework.security.core.*;
import org.springframework.security.authentication.*;
import ec.com.todocompu.ShrimpSoftUtils.Encriptacion;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SecurityController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping
    public SessionResponse login(@RequestBody String json) throws Exception {
        SessionResponse resp = new SessionResponse();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        try {
            Encriptacion encriptacion = new Encriptacion();
            Map<String, Object> map = UtilsJSON.jsonToMap(json);
            String username = UtilsJSON.jsonToObjeto(String.class, map.get("username"));
            String password = encriptacion.sha256(UtilsJSON.jsonToObjeto(String.class, map.get("password")));
            String hostname = UtilsJSON.jsonToObjeto(String.class, map.get("hostname"));
            String host = UtilsJSON.jsonToObjeto(String.class, map.get("host"));
            securityService.obtenerSessionRespuesta(resp, username, password);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException(e.getMessage());
        } catch (Exception e) {
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
        }
        return resp;
    }

    @RequestMapping("/actualizarTelefonoUsuario")
    public RespuestaWebTO actualizarTelefonoUsuario(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String telefono = UtilsJSON.jsonToObjeto(String.class, map.get("telefono"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = usuarioService.actualizarTelefonoUsuario(usuario, telefono);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Error al actualizar teléfono usuario.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerEmailAdministrador")
    public RespuestaWebTO obtenerEmailAdministrador(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = usuarioService.obtenerEmailAdministrador();
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Error al recuperar los datos del administrador.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

}
