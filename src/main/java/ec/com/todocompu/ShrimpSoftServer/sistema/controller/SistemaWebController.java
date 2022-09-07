package ec.com.todocompu.ShrimpSoftServer.sistema.controller;

import ec.com.todocompu.ShrimpSoftServer.caja.service.CajaService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.security.SecurityService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.ArchivosService;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.sistema.service.EmpresaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.GrupoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PcsService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PermisoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SucesoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioDetalleService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisComboPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisConsultaUsuarioGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaSusTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisLoginTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisMenu;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPermisoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisSucesoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ec.com.todocompu.ShrimpSoftServer.sistema.report.ReporteSistemaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.ConfiguracionComprasService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.ConfiguracionConsumosService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.EmpresaParametrosService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.FirmaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.isp.service.MikrotikService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.ScannerConfiguracionService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SisEmpresaNotificacionesService;
import ec.com.todocompu.ShrimpSoftServer.isp.service.SisEmpresaParametrosMikrotikService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SisSucesoClienteService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.ZendeskService;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftUtils.Encriptacion;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoInnecesarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionCompras;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificacionesEventos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametrosMikrotik;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPcs;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisScannerConfiguracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCliente;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.ZendeskConfiguracion;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;

@RestController
@RequestMapping("/todocompuWS/sistemaWebController")
public class SistemaWebController {

    private static Locale locale = new Locale("es", "EC");
    @Autowired
    private EmpresaParametrosService empresaParametrosService;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private PcsService pcsService;
    @Autowired
    private ScannerConfiguracionService scannerConfiguracionService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private PermisoService permisoService;
    @Autowired
    private SucesoService sucesoService;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private CajaService cajaService;
    @Autowired
    private ArchivosService archivosService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private ReporteSistemaService reporteSistemaService;
    @Autowired
    private ZendeskService zendeskService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SisEmpresaNotificacionesService sisEmpresaNotificacionesService;
    @Autowired
    private FirmaElectronicaService firmaElectronicaService;
    @Autowired
    private ConfiguracionConsumosService configuracionConsumosService;
    @Autowired
    private ConfiguracionComprasService configuracionComprasService;
    @Autowired
    private SisSucesoClienteService sisSucesoClienteService;

    @RequestMapping("/logout")
    public RespuestaWebTO logout(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            boolean respues = securityService.logout(sisInfoTO);
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

    @RequestMapping("/getMenuWeb")
    public List<SisMenu> getMenuWeb(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        boolean produccion = UtilsJSON.jsonToObjeto(boolean.class, map.get("produccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return permisoService.getMenuWeb(usuario, produccion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarError")
    public void enviarError(@RequestBody SisErrorTO sisErrorTO) {
        envioCorreoService.enviarError("CLIENTE", sisErrorTO);
    }

    @RequestMapping("/comprobarSisPcs")
    public boolean comprobarSisPcs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String mac = UtilsJSON.jsonToObjeto(String.class, map.get("mac"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pcsService.comprobarSisPcs(mac);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getEmpresasPorUsuarioItem")
    public List<SisEmpresa> getEmpresasPorUsuarioItem(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String item = UtilsJSON.jsonToObjeto(String.class, map.get("item"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioDetalleService.getEmpresasPorUsuarioItem(usuario, item);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getSisAccesoTO")
    public SisLoginTO getSisAccesoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nick = UtilsJSON.jsonToObjeto(String.class, map.get("nick"));
        String password = UtilsJSON.jsonToObjeto(String.class, map.get("password"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.getSisAccesoTO(nick, password, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getPermisoInventarioTO")
    public SisLoginTO getPermisoInventarioTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String infEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("infEmpresa"));
        String infUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("infUsuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.getPermisoInventarioTO(infEmpresa, infUsuario);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPermisoModulo")
    public List<String> getListaPermisoModulo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String grupoCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("grupoCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return permisoService.getListaPermisoModulo(empCodigo, grupoCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaLoginPermisoTO")
    public List<SisMenu> getListaLoginPermisoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String grupoCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("grupoCodigo"));
        String perModulo = UtilsJSON.jsonToObjeto(String.class, map.get("perModulo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return permisoService.getListaPermisoTO(empCodigo, grupoCodigo, perModulo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaPermisoTO")
    public RespuestaWebTO getListaPermisoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String grupoCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("grupo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisPermisoTO> respuesta = permisoService.getListaPermisoTO(grupoCodigo, empCodigo);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontro registros");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/sisRegistrarEventosUsuario")
    public void sisRegistrarEventosUsuario(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String suceso = UtilsJSON.jsonToObjeto(String.class, map.get("suceso"));
        String mensaje = UtilsJSON.jsonToObjeto(String.class, map.get("mensaje"));
        String tabla = UtilsJSON.jsonToObjeto(String.class, map.get("tabla"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sucesoService.sisRegistrarEventosUsuario(suceso, mensaje, tabla, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
    }

    @RequestMapping("/insertarSisUsuarioTO")
    public RespuestaWebTO insertarSisUsuarioTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisUsuarioTO sisUsuarioTO = UtilsJSON.jsonToObjeto(SisUsuarioTO.class, map.get("sisUsuarioTO"));
        boolean insertaDetalle = UtilsJSON.jsonToObjeto(boolean.class, map.get("insertaDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Encriptacion encriptacion = new Encriptacion();
            String clavePass = encriptacion.sha256(sisUsuarioTO.getUsrPassword());
            if (sisUsuarioTO.getUsrEmail() != null && sisUsuarioTO.getUsrEmail().trim().length() > 0) {
                if (sisUsuarioTO.getUsrEmail() != null) {
                    String claveEmail = usuarioService.encriptarClaveEmail(sisUsuarioTO.getUsrPasswordEmail());
                    sisUsuarioTO.setUsrPasswordEmail(claveEmail);
                }
            } else {
                sisUsuarioTO.setUsrPasswordEmail(null);
            }
            sisUsuarioTO.setUsrPassword(clavePass);
            String respuesta = usuarioService.insertarSistemUsuarioTO(sisUsuarioTO, sisInfoTO, insertaDetalle);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
                SisUsuario usuarioEncriptado = usuarioService.obtenerPorId(sisUsuarioTO.getUsrCodigo());
                resp.setExtraInfo(usuarioEncriptado);
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se insertó.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPasswordSisUsuario")
    public RespuestaWebTO modificarPasswordSisUsuarioTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoUser = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUser"));
        String clave = UtilsJSON.jsonToObjeto(String.class, map.get("clave"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = usuarioService.modificarPasswordSisUsuario(codigoUser, clave);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se cambio la clave del usuario.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarSisUsuarioWebTO")
    public RespuestaWebTO modificarSisUsuarioWebTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisUsuario sisUsuario = UtilsJSON.jsonToObjeto(SisUsuario.class, map.get("sisUsuario"));
        String imagen = UtilsJSON.jsonToObjeto(String.class, map.get("imagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] decodedString = imagen != null ? Base64.getDecoder().decode(imagen.getBytes("UTF-8")) : null;
            String respues = usuarioService.modificarSisUsuario(sisUsuario, decodedString, sisInfoTO);
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

    @RequestMapping("/claveActualValida")
    public RespuestaWebTO claveActualValida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        String clave = UtilsJSON.jsonToObjeto(String.class, map.get("clave"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = usuarioService.claveActualValida(clave, codigo);
            if (respues) {
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

    @RequestMapping("/modificarSisUsuarioTO")
    public RespuestaWebTO modificarSisUsuarioTO(@RequestBody String json) {
        Encriptacion encriptacion = new Encriptacion();
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisUsuarioTO sisUsuarioTO = UtilsJSON.jsonToObjeto(SisUsuarioTO.class, map.get("sisUsuarioTO"));
        String pass = UtilsJSON.jsonToObjeto(String.class, map.get("pass"));
        String passwordEmail = UtilsJSON.jsonToObjeto(String.class, map.get("passwordEmail"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (pass != null && !pass.equals(sisUsuarioTO.getUsrPassword())) {
                String clavePass = encriptacion.sha256(sisUsuarioTO.getUsrPassword());
                sisUsuarioTO.setUsrPassword(clavePass);
            }
            if (passwordEmail != null && !passwordEmail.equals(sisUsuarioTO.getUsrPasswordEmail())) {
                String claveEmail = usuarioService.encriptarClaveEmail(passwordEmail);
                sisUsuarioTO.setUsrPasswordEmail(claveEmail);
            }
            String respuesta = usuarioService.modificarSistemUsuarioTO(sisUsuarioTO, pass, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
                SisUsuario usuarioEncriptado = usuarioService.obtenerPorId(sisUsuarioTO.getUsrCodigo());
                resp.setExtraInfo(usuarioEncriptado);
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se modificó.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarSisUsuarioTO")
    public RespuestaWebTO eliminarSisUsuarioTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisUsuarioTO sisUsuarioTO = UtilsJSON.jsonToObjeto(SisUsuarioTO.class, map.get("sisUsuarioTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = usuarioService.eliminarSisUsuarioTO(sisUsuarioTO, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
                resp.setOperacionMensaje("Se elminó de manera exitosa el usuario.");
            } else {
                resp.setOperacionMensaje("No se logró eliminar el usuario.");
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

    @RequestMapping("/getListaSisGrupo")
    public RespuestaWebTO getListaSisGrupo(@RequestBody String json) {
        // @RequestParam(value = "empresa") String empresa) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisGrupoTO> respuesta = grupoService.getListaSisGrupo(empresa);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontró usuario.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getSisGrupoTOs")
    public List<SisGrupoTO> getSisGrupoTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grupoService.getSisGrupoTOs(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaUsuario")
    public RespuestaWebTO getListaUsuario(HttpServletRequest request, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String parametro = UtilsJSON.jsonToObjeto(String.class, map.get("parametro"));
        String infEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("infEmpresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisConsultaUsuarioGrupoTO> respuesta = usuarioService.getListaUsuario(infEmpresa, parametro);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontró usuario.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/buscarUsuario")
    public SisUsuario buscarUsuario(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usrCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usrCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.obtenerPorId(usrCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/buscarUsuarioPorNick")
    public RespuestaWebTO buscarUsuarioPorNick(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usrNick = UtilsJSON.jsonToObjeto(String.class, map.get("usrNick"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = usuarioService.obtenerPerfilDeUsuario(usrNick);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró usuario.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionSisGrupoTO")
    public RespuestaWebTO accionSisGrupoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisGrupoTO sisGrupoTO = UtilsJSON.jsonToObjeto(SisGrupoTO.class, map.get("sisGrupoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = grupoService.accionSisGrupoTO(sisGrupoTO, accion, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                String mensaje = "";
                if (accion == 'I') {
                    mensaje = "El grupo: " + sisGrupoTO.getGruCodigo() + ", se ha guardado correctamente";
                } else {
                    if (accion == 'M') {
                        mensaje = "El grupo: " + sisGrupoTO.getGruCodigo() + ", se ha modificado correctamente";
                    } else {
                        mensaje = "El grupo: " + sisGrupoTO.getGruCodigo() + ", se ha eliminado correctamente";
                    }
                }
                resp.setOperacionMensaje(mensaje);
            } else {
                resp.setOperacionMensaje("No se encuentró período.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/estadoPeriodo")
    public RespuestaWebTO estadoPeriodo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String estado = periodoService.estadoPeriodo(empresa, fecha);
            if (estado == null || estado.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(estado);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaSisPeriodos")
    public RespuestaWebTO getListaSisPeriodos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa);
            if (listaPeriodos != null && !listaPeriodos.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaPeriodos);
            } else {
                resp.setOperacionMensaje("No se encuentraron períodos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerSisPeriodo")
    public RespuestaWebTO obtenerSisPeriodo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisPeriodo respues = periodoService.buscarPeriodo(empresa, periodo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encuentró período.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getSisPeriodo")
    public String getSisPeriodo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.getSisPeriodo(empresa, periodo, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getSisComboPeriodosTO")
    public List<SisComboPeriodoTO> getSisComboPeriodosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.getSisComboPeriodoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getPeriodoPorFecha")
    public RespuestaWebTO getPeriodoPorFecha(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaString = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        Date fecha = UtilsDate.DeStringADate(fechaString);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisPeriodo respues = periodoService.getPeriodoPorFecha(fecha, empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró período válido.");
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

    @RequestMapping("/validarPeriodo")
    public String validarPeriodo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, map.get("fecha"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.validarPeriodo(fecha, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaSisSusTablaTO")
    public RespuestaWebTO getListaSisSusTablaTO(HttpServletRequest request, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisListaSusTablaTO> respues = sucesoService.getListaSisSusTablaTOs(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados de producto presentacion caja.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaSisUsuario")
    public RespuestaWebTO getListaSisUsuario(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisListaUsuarioTO> respues = usuarioDetalleService.getListaSisUsuarios(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró usuario.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaSisSucesoTO")
    public RespuestaWebTO getListaSisSucesoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String suceso = UtilsJSON.jsonToObjeto(String.class, map.get("suceso"));
        String cadenaGeneral = UtilsJSON.jsonToObjeto(String.class, map.get("cadenaGeneral"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisSucesoTO> respues = sucesoService.getListaSisSucesoTO(desde, hasta, usuario, suceso, cadenaGeneral, empresa);
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

    @RequestMapping("/getListaSisSusTablaTOs")
    public RespuestaWebTO getListaSisSusTablaTOs(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisListaSusTablaTO> respues = sucesoService.getListaSisSusTablaTOs(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados de producto presentacion caja.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteSucesos")
    public @ResponseBody
    String imprimirReporteSucesos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reporteSucesos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SisSucesoTO> listado = UtilsJSON.jsonToList(SisSucesoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            respuesta = reporteSistemaService.generarReporteSistemaSucesos(usuarioEmpresaReporteTO, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSucesos")
    public @ResponseBody
    String exportarReporteSucesos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SisSucesoTO> listado = UtilsJSON.jsonToList(SisSucesoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteSistemaService.exportarReporteSistemaSucesos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteUsuarios")
    public @ResponseBody
    String exportarReporteUsuarios(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SisConsultaUsuarioGrupoTO> listado = UtilsJSON.jsonToList(SisConsultaUsuarioGrupoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteSistemaService.exportarUsuario(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteUsuario")
    public byte[] generarReporteUsuario(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<SisConsultaUsuarioGrupoTO> listSisConsultaUsuarioGrupoTO = UtilsJSON.jsonToList(SisConsultaUsuarioGrupoTO.class, map.get("listSisConsultaUsuarioGrupoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.generarReporteUsuario(usuarioEmpresaReporteTO, listSisConsultaUsuarioGrupoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarSisEmpresa")
    public boolean insertarSisEmpresa(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisEmpresaTO sisEmpresaTO = UtilsJSON.jsonToObjeto(SisEmpresaTO.class, map.get("sisEmpresaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empresaService.insertarSisEmpresa(sisEmpresaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/estadoLlevarContabilidad")
    public boolean estadoLlevarContabilidad(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empresaService.estadoLlevarContabilidad(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarSisEmpresa")
    public RespuestaWebTO modificarSisEmpresa(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisEmpresa sisEmpresa = UtilsJSON.jsonToObjeto(SisEmpresa.class, map.get("sisEmpresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisEmpresa respues = empresaService.modificarSisEmpresa(sisEmpresa, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Empresa modificada correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se logró modificar empresa.");
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

    @RequestMapping("/modificarSisEmpresaTO")
    public RespuestaWebTO modificarSisEmpresaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisEmpresaTO sisEmpresaTO = UtilsJSON.jsonToObjeto(SisEmpresaTO.class, map.get("sisEmpresaTO"));
        String imagen = UtilsJSON.jsonToObjeto(String.class, map.get("imagen"));
        boolean validarRucRepetido = UtilsJSON.jsonToObjeto(boolean.class, map.get("validarRucRepetido"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] decodedString = imagen != null ? Base64.getDecoder().decode(imagen.getBytes("UTF-8")) : null;
            SisEmpresaTO respues = empresaService.modificarSisEmpresaTO(sisEmpresaTO, decodedString, sisInfoTO, validarRucRepetido);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La empresa: " + sisEmpresaTO.getEmpCodigo() + " se ha modificado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se logró modificar empresa.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
            if (e.getCause() != null) {
                resp.setExtraInfo("PREGUNTA");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarSisEmpresaTO")
    public RespuestaWebTO insertarSisEmpresaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisEmpresaTO sisEmpresaTO = UtilsJSON.jsonToObjeto(SisEmpresaTO.class, map.get("sisEmpresaTO"));
        String imagen = UtilsJSON.jsonToObjeto(String.class, map.get("imagen"));
        boolean validarRucRepetido = UtilsJSON.jsonToObjeto(boolean.class, map.get("validarRucRepetido"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] decodedString = imagen != null ? Base64.getDecoder().decode(imagen.getBytes("UTF-8")) : null;
            SisEmpresaTO respues = empresaService.insertarSisEmpresaTO(sisEmpresaTO, decodedString, sisInfoTO, validarRucRepetido);
            if (respues != null) {
                empresaService.insertarRegistrosComplementarios(respues.getEmpCodigo());
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La empresa: " + sisEmpresaTO.getEmpCodigo() + " se ha guardado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se logró guardar empresa.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
            if (e.getCause() != null) {
                resp.setExtraInfo("PREGUNTA");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerEmpresa")
    public RespuestaWebTO obtenerEmpresa(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisEmpresa respues = empresaService.obtenerEmpresa(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados de empresa.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/guardarImagenEmpresa")
    public RespuestaWebTO guardarImagenEmpresa(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] imagen = UtilsJSON.jsonToObjeto(byte[].class, map.get("imagen"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = empresaService.guardarImagenEmpresa(imagen, nombre);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró imagen.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*
    @RequestMapping("/guardarImagenEmpresa")
    public String guardarImagenEmpresa(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] imagen = UtilsJSON.jsonToObjeto(byte[].class, map.get("imagen"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empresaService.guardarImagenEmpresa(imagen, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }
     */
    @RequestMapping("/guardarArchivoFirmaElectronica")
    public RespuestaWebTO guardarArchivoFirmaElectronica(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String archivoBase64 = UtilsJSON.jsonToObjeto(String.class, map.get("archivoBase64"));
        String nombreArchivo = UtilsJSON.jsonToObjeto(String.class, map.get("nombreArchivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] archivoByte = archivoBase64 != null ? Base64.getDecoder().decode(archivoBase64.getBytes("UTF-8")) : null;
            String respues = cajaService.guardarArchivoFirmaElectronica(archivoByte, nombreArchivo);
            if (respues.length() > 0 && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarImagenEmpresa")
    public String eliminarImagenEmpresa(@RequestBody String json) {
        // @RequestParam(value = "nombre") String nombre) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empresaService.eliminarImagenEmpresa(nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerImagenEmpresa")
    public byte[] obtenerImagenEmpresa(@RequestBody String json) {
        // @RequestParam(value = "nombre") String nombre) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empresaService.obtenerImagenEmpresa(nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerImagenUsuario")
    public byte[] obtenerImagenUsuario(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.obtenerImagenUsuario(nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/guardarImagenUsuario")
    public String guardarImagenUsuario(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] imagen = UtilsJSON.jsonToObjeto(byte[].class, map.get("imagen"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.guardarImagenUsuario(imagen, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaSisEmpresaTO")
    public RespuestaWebTO getListaSisEmpresaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usrCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usrCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisEmpresaTO> respues = empresaService.getListaSisEmpresaTO(usrCodigo, empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setExtraInfo(respues);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            } else {
                resp.setOperacionMensaje("No se encontraron empresas.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaSisEmpresaTOByUser")
    public RespuestaWebTO getListaSisEmpresaTOByUser(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usrCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usrCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisEmpresaTO> respues = empresaService.getListaSisEmpresaTOWeb(usrCodigo);
            if (respues != null && !respues.isEmpty()) {
                resp.setExtraInfo(respues);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            } else {
                resp.setOperacionMensaje("No se encontraron empresas.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaEmpresa")
    public RespuestaWebTO obtenerDatosParaEmpresa(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = empresaService.obtenerDatosParaEmpresa(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setExtraInfo(respues);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
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

    @RequestMapping("/modificarSisPermiso")
    public RespuestaWebTO modificarSisPermiso(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<SisPermisoTO> listaSisPermisoTO = UtilsJSON.jsonToList(SisPermisoTO.class, map.get("listaSisPermisoTO"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String codModifGrupo = UtilsJSON.jsonToObjeto(String.class, map.get("codModifGrupo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = permisoService.modificarSisPermiso(listaSisPermisoTO, usuario, codModifGrupo, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Permisos modificados correctamente");
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

    @RequestMapping("/getColumnasEstadosFinancieros")
    public RespuestaWebTO getColumnasEstadosFinancieros(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Integer respues = empresaParametrosService.getColumnasEstadosFinancieros(empCodigo);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje("");
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerEmpresaParametros")
    public RespuestaWebTO obtenerEmpresaParametros(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisEmpresaParametros respues = empresaParametrosService.obtenerEmpresaParametros(empCodigo);
            if (respues != null) {
                resp.setExtraInfo(respues);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
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

    @RequestMapping("/getFechaServidor")
    public String getFechaServidor(@RequestParam("mascara") String mascara) {
        try {
            Date fecha = new Date();
            SimpleDateFormat formato = new SimpleDateFormat(mascara, locale);
            return formato.format(fecha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/guardarImagenTexto")
    public String guardarImagenTexto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);

        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        byte[] imagen = UtilsJSON.jsonToObjeto(byte[].class, map.get("imagen"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombreArchivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return archivosService.guardarArchivo(tipo, imagen, empresa, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarImagenTexto")
    public String eliminarImagenTexto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);

        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombreArchivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return archivosService.eliminarArchivo(tipo, empresa, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerImagenesTexto")
    public List<String> obtenerImagenesTexto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombreArchivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return archivosService.obtenerArchivo(tipo, empresa, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getIsPeriodoAbierto")
    public RespuestaWebTO getIsPeriodoAbierto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        boolean isPeriodoAbierto = false;
        SisPeriodo sisPeriodo = null;
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa);
            if (listaPeriodos != null) {
                for (SisPeriodo itemSisPeriodo : listaPeriodos) {
                    //Si la fecha está entre el 'desde' y 'hasta' del periodo, ese periodo es el que corresponde a nuestra fecha pasada como parámetro 
                    if (fecha.getTime() >= itemSisPeriodo.getPerDesde().getTime() && fecha.getTime() <= UtilsDate.dateCompleto(itemSisPeriodo.getPerHasta()).getTime()) {
                        sisPeriodo = itemSisPeriodo;
                    }
                }
                if (sisPeriodo == null || sisPeriodo.getPerCerrado()) {
                    isPeriodoAbierto = false;
                } else {
                    isPeriodoAbierto = true;
                }
                resp.setOperacionMensaje(!isPeriodoAbierto ? "El periodo que corresponde a la fecha que ingresó se encuentra cerrado o no existe" : "");
                resp.setExtraInfo(isPeriodoAbierto);
            } else {
                resp.setOperacionMensaje("No se encuentra períodos");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //PERIODO
    @RequestMapping("/getListaSisPeriodosTO")
    public RespuestaWebTO getListaSisPeriodosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisListaPeriodoTO> listado = periodoService.getListaPeriodoTO(empresa);
            if (listado != null && listado.size() > 0) {
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

    @RequestMapping("/insertarSisPeriodoTO")
    public RespuestaWebTO insertarSisPeriodoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPeriodoTO sisPeriodoTO = UtilsJSON.jsonToObjeto(SisPeriodoTO.class, map.get("sisPeriodoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = periodoService.insertarSisPeriodoTO(sisPeriodoTO, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("El período: " + sisPeriodoTO.getPerCodigo() + ", se ha guardado correctamente");
            } else {
                resp.setOperacionMensaje("No se pudo insertar el período");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarSisPeriodoTO")
    public RespuestaWebTO modificarSisPeriodoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPeriodoTO sisPeriodoTO = UtilsJSON.jsonToObjeto(SisPeriodoTO.class, map.get("sisPeriodoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = periodoService.modificarSisPeriodoTO(sisPeriodoTO, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("El período: " + sisPeriodoTO.getPerCodigo() + ", se ha modificado correctamente");
            } else {
                resp.setOperacionMensaje("El período que va a ingresar ya existe");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoSisPeriodoTO")
    public RespuestaWebTO modificarEstadoSisPeriodoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        boolean perCerrado = UtilsJSON.jsonToObjeto(boolean.class, map.get("perCerrado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = periodoService.modificarEstadoSisPeriodoTO(empresa, codigo, perCerrado, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("El período: " + codigo + (perCerrado ? ", se ha cerrado correctamente" : "se ha abierto correctamente"));
            } else {
                resp.setOperacionMensaje("El período que va a modificar ya no existe");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarSisPeriodoTO")
    public RespuestaWebTO eliminarSisPeriodoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPeriodoTO sisPeriodoTO = UtilsJSON.jsonToObjeto(SisPeriodoTO.class, map.get("sisPeriodoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = periodoService.eliminarSisPeriodoTO(sisPeriodoTO, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("El período: " + sisPeriodoTO.getPerCodigo() + ", se ha eliminado correctamente");
            } else {
                resp.setOperacionMensaje("No se puede eliminar un período que tiene movimientos");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/sisGrupoUsuariosTO")
    public RespuestaWebTO sisGrupoUsuariosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String infEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("infEmpresa"));
        String infUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("infUsuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisGrupoTO respuesta = grupoService.sisGrupoUsuariosTO(infEmpresa, infUsuario);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No tiene los permisos suficientes para la opciones de CONFIGURACIÓN DEL SISTEMA");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReportePeriodos")
    public @ResponseBody
    String generarReportePeriodos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPeriodos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<SisListaPeriodoTO> listado = UtilsJSON.jsonToList(SisListaPeriodoTO.class, parametros.get("listadoSisListaPeriodoTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteSistemaService.generarReportePeriodos(listado, usuarioEmpresaReporteTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePeriodos")
    public @ResponseBody
    String exportarReportePeriodos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SisListaPeriodoTO> listado = UtilsJSON.jsonToList(SisListaPeriodoTO.class, map.get("listadoSisListaPeriodoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteSistemaService.exportarReportePeriodos(listado, usuarioEmpresaReporteTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePeriodosErrores")
    public @ResponseBody
    String exportarReportePeriodosErrores(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SisPeriodoInnecesarioTO> listado = UtilsJSON.jsonToList(SisPeriodoInnecesarioTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteSistemaService.exportarReportePeriodosErrores(listado, usuarioEmpresaReporteTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaLoginEmpresaTO")
    public RespuestaWebTO getListaLoginEmpresaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usrCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usrCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisListaEmpresaTO> listado = usuarioDetalleService.getListaLoginEmpresaTO(usrCodigo);
            if (listado != null && listado.size() > 0) {
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

    //Zendesk
    @RequestMapping("/obtenerConfiguracionZendesk")
    public RespuestaWebTO obtenerConfiguracionZendesk(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            ZendeskConfiguracion zendesk = zendeskService.obtenerConfiguracion();
            if (zendesk != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(zendesk);
            } else {
                resp.setOperacionMensaje("No se encontró la configuración de zendesk.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarConfiguracionZendesk")
    public RespuestaWebTO modificarConfiguracionZendesk(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ZendeskConfiguracion zendeskConfiguracion = UtilsJSON.jsonToObjeto(ZendeskConfiguracion.class, map.get("zendeskConfiguracion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = zendeskService.modificarConfiguracion(zendeskConfiguracion);
            if (respuesta.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //matricial
    @RequestMapping("/generarReportePeriodosMatricial")
    public RespuestaWebTO generarReportePeriodosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<SisListaPeriodoTO> listado = UtilsJSON.jsonToList(SisListaPeriodoTO.class, parametros.get("listadoSisListaPeriodoTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteSistemaService.generarReportePeriodos(listado, usuarioEmpresaReporteTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReportePeriodosErrores")
    public @ResponseBody
    String generarReportePeriodosErrores(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPeriodosErrores.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<SisPeriodoInnecesarioTO> listado = UtilsJSON.jsonToList(SisPeriodoInnecesarioTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteSistemaService.generarReportePeriodosErrores(listado, usuarioEmpresaReporteTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePeriodosErroresMatricial")
    public RespuestaWebTO generarReportePeriodosErroresMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<SisPeriodoInnecesarioTO> listado = UtilsJSON.jsonToList(SisPeriodoInnecesarioTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteSistemaService.generarReportePeriodosErrores(listado, usuarioEmpresaReporteTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSistemaSucesosMatricial")
    public RespuestaWebTO generarReporteSistemaSucesosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reporteSucesos.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SisSucesoTO> listado = UtilsJSON.jsonToList(SisSucesoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteSistemaService.generarReporteSistemaSucesos(usuarioEmpresaReporteTO, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Notificaciones
    @RequestMapping("/listarSisEmpresaNotificaciones")
    public RespuestaWebTO listarSisEmpresaNotificaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<SisEmpresaNotificaciones> respues = sisEmpresaNotificacionesService.listarSisEmpresaNotificaciones(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
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

    @RequestMapping("/listarSisEventoNotificacion")
    public RespuestaWebTO listarSisEventoNotificacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisEmpresaNotificacionesEventos> respues = sisEmpresaNotificacionesService.listarSisEventoNotificacion();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se han encontrado resultados.");
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

    @RequestMapping("/verificarSisEmpresaNotificaciones")
    public RespuestaWebTO verificarSisEmpresaNotificaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean verificarEmail = UtilsJSON.jsonToObjeto(boolean.class, map.get("verificarEmail"));
        List<SisEmpresaNotificaciones> sisEmpresaNotificaciones = UtilsJSON.jsonToList(SisEmpresaNotificaciones.class, map.get("sisEmpresaNotificaciones"));
        try {
            boolean respues = sisEmpresaNotificacionesService.verificarSisEmpresaNotificaciones(sisEmpresaNotificaciones, verificarEmail, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La configuración de notificaciónes se ha verificado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha verificado la configuración de notificación.");
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

    @RequestMapping("/insertarSisEmpresaNotificaciones")
    public RespuestaWebTO insertarSisEmpresaNotificaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        SisEmpresaNotificaciones sisEmpresaNotificaciones = UtilsJSON.jsonToObjeto(SisEmpresaNotificaciones.class, map.get("sisEmpresaNotificaciones"));
        try {
            SisEmpresaNotificaciones respues = sisEmpresaNotificacionesService.insertarSisEmpresaNotificaciones(sisEmpresaNotificaciones, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La configuración de notificación: " + respues.getId() + " se ha insertado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha insertado la configuración de notificación.");
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

    @RequestMapping("/modificarSisEmpresaNotificaciones")
    public RespuestaWebTO modificarSisEmpresaNotificaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        SisEmpresaNotificaciones sisEmpresaNotificaciones = UtilsJSON.jsonToObjeto(SisEmpresaNotificaciones.class, map.get("sisEmpresaNotificaciones"));
        try {
            SisEmpresaNotificaciones respues = sisEmpresaNotificacionesService.modificarSisEmpresaNotificaciones(sisEmpresaNotificaciones, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La configuración de notificación: " + respues.getId() + " se ha modificado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha modificado la configuración de notificación.");
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

    @RequestMapping("/eliminarSisEmpresaNotificaciones")
    public RespuestaWebTO eliminarSisEmpresaNotificaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        Integer idNotificacion = UtilsJSON.jsonToObjeto(Integer.class, map.get("idNotificacion"));
        try {
            boolean respues = sisEmpresaNotificacionesService.eliminarSisEmpresaNotificaciones(idNotificacion, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La configuración de notificación: " + idNotificacion + " se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado la configuración de notificación.");
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

    @RequestMapping("/exportarSisEmpresaNotificaciones")
    public @ResponseBody
    String exportarSisEmpresaNotificaciones(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        List<SisEmpresaNotificaciones> listado = UtilsJSON.jsonToList(SisEmpresaNotificaciones.class, map.get("lista"));
        try {
            respuesta = reporteSistemaService.exportarSisEmpresaNotificaciones(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/borrarCorreoListaNegra")
    public RespuestaWebTO borrarCorreoListaNegra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String correo = UtilsJSON.jsonToObjeto(String.class, map.get("correo"));
        try {
            String respues = sisEmpresaNotificacionesService.borrarCorreoListaNegra(correo, sisInfoTO);
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El correo: " + correo + " se ha eliminado de la lista negra de correos.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(respues.substring(1));
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

    @RequestMapping("/listarSisPcs")
    public RespuestaWebTO listarSisPcs(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        boolean activos = UtilsJSON.jsonToObjeto(boolean.class, map.get("activos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisPcs> respuesta = pcsService.listarSisPcs(busqueda, activos);
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

    @RequestMapping("/insertarSisPcs")
    public RespuestaWebTO insertarSisPcs(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPcs sisPcs = UtilsJSON.jsonToObjeto(SisPcs.class, map.get("sisPcs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisPcs = pcsService.insertarSisPc(sisPcs, sisInfoTO);
            if (sisPcs != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisPcs);
                resp.setOperacionMensaje("La configuración de mac se ha guardado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al guardar la configuración de mac.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarSisPcs")
    public RespuestaWebTO modificarSisPcs(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPcs sisPcs = UtilsJSON.jsonToObjeto(SisPcs.class, map.get("sisPcs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisPcs = pcsService.modificarSisPc(sisPcs, sisInfoTO);
            if (sisPcs != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisPcs);
                resp.setOperacionMensaje("La configuración de mac se ha modificado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar la configuración de mac.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarSisPcs")
    public RespuestaWebTO eliminarSisPcs(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String sisPcs = UtilsJSON.jsonToObjeto(String.class, map.get("sisPcs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = pcsService.eliminarSisPc(sisPcs, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La configuración de mac se ha aliminado correctamente");
            } else {
                resp.setOperacionMensaje("No se puede eliminar la configuración de mac.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteSisPcs")
    public @ResponseBody
    String exportarReporteSisPcs(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SisPcs> listado = UtilsJSON.jsonToList(SisPcs.class, map.get("listadoSisPcs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteSistemaService.exportarReporteSisPcs(listado, usuarioEmpresaReporteTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/listarSisScannerConfiguracion")
    public RespuestaWebTO listarSisScannerConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisScannerConfiguracion> respuesta = scannerConfiguracionService.listarSisScannerConfiguracion(empresa);
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

    @RequestMapping("/insertarSisScannerConfiguracion")
    public RespuestaWebTO insertarSisScannerConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisScannerConfiguracion sisSccaner = UtilsJSON.jsonToObjeto(SisScannerConfiguracion.class, map.get("scanner"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisSccaner = scannerConfiguracionService.insertarSisScannerConfiguracion(sisSccaner, sisInfoTO);
            if (sisSccaner != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisSccaner);
                resp.setOperacionMensaje("La configuración de scanner se ha guardado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al guardar la configuración de scanner.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarSisScannerConfiguracion")
    public RespuestaWebTO modificarSisScannerConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisScannerConfiguracion sisSccaner = UtilsJSON.jsonToObjeto(SisScannerConfiguracion.class, map.get("scanner"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisSccaner = scannerConfiguracionService.modificarSisScannerConfiguracion(sisSccaner, sisInfoTO);
            if (sisSccaner != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisSccaner);
                resp.setOperacionMensaje("La configuración de scanner se ha modificado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar la configuración de scanner.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoSisScannerConfiguracion")
    public RespuestaWebTO modificarEstadoSisScannerConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisScannerConfiguracion sisSccaner = UtilsJSON.jsonToObjeto(SisScannerConfiguracion.class, map.get("scanner"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisSccaner = scannerConfiguracionService.modificarEstadoSisScannerConfiguracion(sisSccaner, estado, sisInfoTO);
            if (sisSccaner != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisSccaner);
                resp.setOperacionMensaje("Se " + (estado ? "activó" : "desactivó") + " la configuración de scanner.");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al cambiar estado de la configuración de scanner.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarSisScannerConfiguracion")
    public RespuestaWebTO eliminarSisScannerConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String sisSccaner = UtilsJSON.jsonToObjeto(String.class, map.get("scanner"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = scannerConfiguracionService.eliminarSisScannerConfiguracion(sisSccaner, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La configuración de scanner se ha aliminado correctamente");
            } else {
                resp.setOperacionMensaje("No se puede eliminar la configuración de scanner.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarSisScannerConfiguracion")
    public @ResponseBody
    String exportarReporteSisScannerConfiguracion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SisScannerConfiguracion> listado = UtilsJSON.jsonToList(SisScannerConfiguracion.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteSistemaService.exportarReporteSisScannerConfiguracion(listado, usuarioEmpresaReporteTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //Firma electrónica
    @RequestMapping("/listarSisFirmaElectronica")
    public RespuestaWebTO listarSisFirmaElectronica(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisFirmaElectronica> respuesta = firmaElectronicaService.listarSisFirmaElectronica(empresa);
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

    @RequestMapping("/insertarSisFirmaElectronica")
    public RespuestaWebTO insertarSisFirmaElectronica(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisFirmaElectronica firmaElectronica = UtilsJSON.jsonToObjeto(SisFirmaElectronica.class, map.get("firma"));
        String clave = UtilsJSON.jsonToObjeto(String.class, map.get("clave"));
        String ruc = UtilsJSON.jsonToObjeto(String.class, map.get("ruc"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisInfoTO.setEmpresaRuc(ruc);
            if (clave != null && !clave.equals(firmaElectronica.getFirmaClave())) {
                Encriptacion encriptacion1 = new Encriptacion();
                java.math.BigInteger[] textoPass = encriptacion1.encripta(clave);
                String clavePass = "";
                for (BigInteger textoPas : textoPass) {
                    clavePass = clavePass + textoPas.toString(16).toUpperCase() + "G";
                }
                String n = encriptacion1.damen().toString(16);
                String d = encriptacion1.damed().toString(16);
                clavePass = n.length() + encriptacion1.damen().toString(16).toUpperCase() + clavePass + encriptacion1.damed().toString(16).toUpperCase() + d.length();
                firmaElectronica.setFirmaClave(clavePass);
            }

            firmaElectronica = firmaElectronicaService.insertarSisFirmaElectronica(firmaElectronica, sisInfoTO);
            if (firmaElectronica != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(firmaElectronica);
                resp.setOperacionMensaje("La firma electrónica se ha guardado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al guardar la firma electrónica.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarSisFirmaElectronica")
    public RespuestaWebTO modificarSisFirmaElectronica(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String clave = UtilsJSON.jsonToObjeto(String.class, map.get("clave"));
        String ruc = UtilsJSON.jsonToObjeto(String.class, map.get("ruc"));
        SisFirmaElectronica firmaElectronica = UtilsJSON.jsonToObjeto(SisFirmaElectronica.class, map.get("firma"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisInfoTO.setEmpresaRuc(ruc);
            if (clave != null && !clave.equals(firmaElectronica.getFirmaClave())) {
                Encriptacion encriptacion1 = new Encriptacion();
                java.math.BigInteger[] textoPass = encriptacion1.encripta(clave);
                String clavePass = "";
                for (BigInteger textoPas : textoPass) {
                    clavePass = clavePass + textoPas.toString(16).toUpperCase() + "G";
                }
                String n = encriptacion1.damen().toString(16);
                String d = encriptacion1.damed().toString(16);
                clavePass = n.length() + encriptacion1.damen().toString(16).toUpperCase() + clavePass + encriptacion1.damed().toString(16).toUpperCase() + d.length();
                firmaElectronica.setFirmaClave(clavePass);
            }
            firmaElectronica = firmaElectronicaService.modificarSisFirmaElectronica(firmaElectronica, sisInfoTO);
            if (firmaElectronica != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(firmaElectronica);
                resp.setOperacionMensaje("La firma electrónica se ha modificado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar la firma electrónica.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarSisFirmaElectronica")
    public RespuestaWebTO eliminarSisFirmaElectronica(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer firmaElectronica = UtilsJSON.jsonToObjeto(Integer.class, map.get("firma"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = firmaElectronicaService.eliminarSisFirmaElectronica(firmaElectronica, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La firma electrónica se ha aliminado correctamente");
            } else {
                resp.setOperacionMensaje("No se puede eliminar la firma electrónica.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarSisFirmaElectronica")
    public @ResponseBody
    String exportarReporteSisFirmaElectronica(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SisFirmaElectronica> listado = UtilsJSON.jsonToList(SisFirmaElectronica.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteSistemaService.exportarReporteSisFirmaElectronica(listado, usuarioEmpresaReporteTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getSisConfiguracionConsumos")
    public RespuestaWebTO getSisConfiguracionConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoUser = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUser"));//para consultar
        SisConfiguracionConsumosPK pk = new SisConfiguracionConsumosPK();
        pk.setConfEmpresa(empresa);
        pk.setConfUsuarioResponsable(codigoUser);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisConfiguracionConsumos respuesta = configuracionConsumosService.getSisConfiguracionConsumos(pk);
            if (respuesta != null) {
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

    @RequestMapping("/getSisConfiguracionCompras")
    public RespuestaWebTO getSisConfiguracionCompras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoUser = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUser"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisConfiguracionCompras respuesta = configuracionComprasService.getSisConfiguracionCompras(new SisConfiguracionComprasPK(empresa, codigoUser));
            if (respuesta != null) {
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

    @RequestMapping("/listarSisConfiguracionConsumos")
    public RespuestaWebTO listarSisConfiguracionConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisConfiguracionConsumos> respuesta = configuracionConsumosService.listarSisConfiguracionConsumos(empresa);
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

    @RequestMapping("/obtenerDatosParaSisConfiguracionConsumos")
    public RespuestaWebTO obtenerDatosParaSisConfiguracionConsumos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = configuracionConsumosService.obtenerDatosParaSisConfiguracionConsumos(empresa, sector, sisInfoTO);
            if (respues != null && !respues.isEmpty()) {
                resp.setExtraInfo(respues);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
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

    @RequestMapping("/insertarSisConfiguracionConsumos")
    public RespuestaWebTO insertarSisConfiguracionConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisConfiguracionConsumos sisConfiguracionConsumos = UtilsJSON.jsonToObjeto(SisConfiguracionConsumos.class, map.get("sisConfiguracionConsumos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisConfiguracionConsumos = configuracionConsumosService.insertarSisConfiguracionConsumos(sisConfiguracionConsumos, sisInfoTO);
            if (sisConfiguracionConsumos != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisConfiguracionConsumos);
                resp.setOperacionMensaje("La configuración de consumo se ha guardado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al guardar la configuración de consumo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarSisConfiguracionConsumos")
    public RespuestaWebTO modificarSisConfiguracionConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisConfiguracionConsumos sisConfiguracionConsumos = UtilsJSON.jsonToObjeto(SisConfiguracionConsumos.class, map.get("sisConfiguracionConsumos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisConfiguracionConsumos = configuracionConsumosService.modificarSisConfiguracionConsumos(sisConfiguracionConsumos, sisInfoTO);
            if (sisConfiguracionConsumos != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisConfiguracionConsumos);
                resp.setOperacionMensaje("La configuración de consumo se ha modificado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar la configuración de consumo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarSisConfiguracionConsumos")
    public RespuestaWebTO eliminarSisConfiguracionConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisConfiguracionConsumosPK pk = UtilsJSON.jsonToObjeto(SisConfiguracionConsumosPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = configuracionConsumosService.eliminarSisConfiguracionConsumos(pk, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La configuración de consumo se ha eliminado correctamente");
            } else {
                resp.setOperacionMensaje("No se puede eliminar la configuración de consumo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarCorreo")
    public RespuestaWebTO verificarCorreo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String email = UtilsJSON.jsonToObjeto(String.class, map.get("email"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = sisEmpresaNotificacionesService.verificarCorreo(email, sisInfoTO);
            if (respuesta != null && respuesta.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarSisConfiguracionCompras")
    public RespuestaWebTO listarSisConfiguracionCompras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisConfiguracionCompras> respuesta = configuracionComprasService.listarSisConfiguracionCompras(empresa);
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

    @RequestMapping("/obtenerDatosParaSisConfiguracionCompras")
    public RespuestaWebTO obtenerDatosParaSisConfiguracionCompras(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        boolean isNuevo = UtilsJSON.jsonToObjeto(boolean.class, map.get("isNuevo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = configuracionComprasService.obtenerDatosParaSisConfiguracionCompras(empresa, sector, isNuevo, sisInfoTO);
            if (respues != null && !respues.isEmpty()) {
                resp.setExtraInfo(respues);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
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

    @RequestMapping("/insertarSisConfiguracionCompras")
    public RespuestaWebTO insertarSisConfiguracionCompras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisConfiguracionCompras sisConfiguracionCompras = UtilsJSON.jsonToObjeto(SisConfiguracionCompras.class, map.get("sisConfiguracionCompras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisConfiguracionCompras = configuracionComprasService.insertarSisConfiguracionCompras(sisConfiguracionCompras, sisInfoTO);
            if (sisConfiguracionCompras != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisConfiguracionCompras);
                resp.setOperacionMensaje("La configuración de compras se ha guardado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al guardar la configuración de compras.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarSisConfiguracionCompras")
    public RespuestaWebTO modificarSisConfiguracionCompras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisConfiguracionCompras sisConfiguracionCompras = UtilsJSON.jsonToObjeto(SisConfiguracionCompras.class, map.get("sisConfiguracionCompras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisConfiguracionCompras = configuracionComprasService.modificarSisConfiguracionCompras(sisConfiguracionCompras, sisInfoTO);
            if (sisConfiguracionCompras != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisConfiguracionCompras);
                resp.setOperacionMensaje("La configuración de consumo se ha modificado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar la configuración de compras.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarSisConfiguracionCompras")
    public RespuestaWebTO eliminarSisConfiguracionCompras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisConfiguracionComprasPK pk = UtilsJSON.jsonToObjeto(SisConfiguracionComprasPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = configuracionComprasService.eliminarSisConfiguracionCompras(pk, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La configuración de compras se ha eliminado correctamente");
            } else {
                resp.setOperacionMensaje("No se puede eliminar la configuración de compras.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/cerrarPeriodosAutomaticamente")
    public RespuestaWebTO cerrarPeriodosAutomaticamente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = periodoService.cerrarPeriodosAutomaticamente(sisInfoTO.getEmpresa());
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("Se cerraron los períodos automáticamente");
            } else {
                resp.setOperacionMensaje("No se puede cerrar los períodos");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getDatosFirmaElectronica")
    public RespuestaWebTO getDatosFirmaElectronica(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = firmaElectronicaService.getDatosFirmaElectronica(secuencial, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para realizar la acción solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/migracionCajaAFirmaElectronica")
    public RespuestaWebTO migracionCajaAFirmaElectronica(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String ruc = UtilsJSON.jsonToObjeto(String.class, map.get("ruc"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisInfoTO.setEmpresaRuc(ruc);
            boolean respues = firmaElectronicaService.migracionCajaAFirmaElectronica(sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se migró correctamente");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para realizar la acción solicitada");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validacionCertificado")
    public RespuestaWebTO validacionCertificado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] certificado = UtilsJSON.jsonToObjeto(byte[].class, map.get("certificado"));
        String contrasenia = UtilsJSON.jsonToObjeto(String.class, map.get("contrasenia"));
        boolean contraseniaEncriptada = UtilsJSON.jsonToObjeto(boolean.class, map.get("contraseniaEncriptada"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = firmaElectronicaService.validacionCertificado(certificado, contrasenia, contraseniaEncriptada);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se puede eliminar la firma electrónica.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getIsPeriodoAbiertoVariasFechas")
    public RespuestaWebTO getIsPeriodoAbiertoVariasFechas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        boolean isPeriodoAbierto = false;
        SisPeriodo sisPeriodo = null;
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<Date> fechas = UtilsJSON.jsonToList(Date.class, map.get("fechas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            List<String> resultados2 = new ArrayList();
            List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa);
            if (listaPeriodos != null) {

                for (Date fecha : fechas) {
                    for (SisPeriodo itemSisPeriodo : listaPeriodos) {
                        if (fecha.getTime() >= itemSisPeriodo.getPerDesde().getTime() && fecha.getTime() <= UtilsDate.dateCompleto(itemSisPeriodo.getPerHasta()).getTime()) {
                            sisPeriodo = itemSisPeriodo;
                        }
                    }
                    if (sisPeriodo == null || sisPeriodo.getPerCerrado()) {
                        isPeriodoAbierto = false;
                    } else {
                        isPeriodoAbierto = true;
                    }
                    resultados2.add(!isPeriodoAbierto
                            ? "FEl periodo que corresponde a la fecha que ingresó se encuentra cerrado o no existe" : "T");
                }
                resp.setOperacionMensaje("");
                resp.setExtraInfo(resultados2);
            } else {
                resp.setOperacionMensaje("No se encuentra períodos");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosSucesoDetalle")
    public RespuestaWebTO obtenerDatosSucesoDetalle(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = sucesoService.obtenerDatosSucesoDetalle(secuencial, sisInfoTO.getEmpresa());
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

    @RequestMapping("/getListaSisSucesoDetalle")
    public RespuestaWebTO getListaSisSucesoDetalle(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {

            List<SisSucesoDetalle> respues = sucesoService.getListaSisSucesoDetalle(secuencial);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
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

    @RequestMapping("/getListaSisSucesoCliente")
    public RespuestaWebTO getListaSisSucesoCliente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClientePK pk = UtilsJSON.jsonToObjeto(InvClientePK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {

            List<SisSucesoCliente> respues = sisSucesoClienteService.getListaSisSucesoCliente(pk.getCliEmpresa(), pk.getCliCodigo());
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
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
}
