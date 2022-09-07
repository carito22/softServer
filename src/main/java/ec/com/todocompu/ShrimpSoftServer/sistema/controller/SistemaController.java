package ec.com.todocompu.ShrimpSoftServer.sistema.controller;

import ec.com.todocompu.ShrimpSoftServer.caja.service.CajaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.report.ReporteSistemaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.ArchivosService;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.sistema.service.EmpresaParametrosService;
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
import ec.com.todocompu.ShrimpSoftUtils.sistema.report.ReporteSuceso;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/todocompuWS/sistemaController")
public class SistemaController {

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
    private ArchivosService archivoService;

    @Autowired
    private ReporteSistemaService reporteSistemaService;

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

    @RequestMapping("/comprobarEmails")
    public boolean comprobarEmails(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String destinatarios = UtilsJSON.jsonToObjeto(String.class, map.get("destinatarios"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return envioCorreoService.comprobarEmails(destinatarios);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getListaLoginEmpresaTO")
    public List<SisListaEmpresaTO> getListaLoginEmpresaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usrCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usrCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioDetalleService.getListaLoginEmpresaTO(usrCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
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
    public List<SisPermisoTO> getListaPermisoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String grupoCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("grupoCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return permisoService.getListaPermisoTO(grupoCodigo, empCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

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
    public String insertarSisUsuarioTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisUsuarioTO sisUsuarioTO = UtilsJSON.jsonToObjeto(SisUsuarioTO.class, map.get("sisUsuarioTO"));
        boolean insertaDetalle = UtilsJSON.jsonToObjeto(boolean.class, map.get("insertaDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.insertarSisUsuarioTO(sisUsuarioTO, sisInfoTO, insertaDetalle);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/modificarPasswordSisUsuarioTO")
    public String modificarPasswordSisUsuarioTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisUsuarioTO sisUsuarioTO = UtilsJSON.jsonToObjeto(SisUsuarioTO.class, map.get("sisUsuarioTO"));
        String pass = UtilsJSON.jsonToObjeto(String.class, map.get("pass"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.modificarPasswordSisUsuarioTO(sisUsuarioTO, pass);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/modificarSisUsuarioWebTO")
    public String modificarSisUsuarioWebTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisUsuarioTO sisUsuarioTO = UtilsJSON.jsonToObjeto(SisUsuarioTO.class, map.get("sisUsuarioTO"));
        String pass = UtilsJSON.jsonToObjeto(String.class, map.get("pass"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.modificarSisUsuarioWebTO(sisUsuarioTO, pass);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/modificarSisUsuarioTO")
    public String modificarSisUsuarioTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisUsuarioTO sisUsuarioTO = UtilsJSON.jsonToObjeto(SisUsuarioTO.class, map.get("sisUsuarioTO"));
        String pass = UtilsJSON.jsonToObjeto(String.class, map.get("pass"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.modificarSisUsuarioTO(sisUsuarioTO, pass, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/eliminarSisUsuarioTO")
    public boolean eliminarSisUsuarioTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisUsuarioTO sisUsuarioTO = UtilsJSON.jsonToObjeto(SisUsuarioTO.class, map.get("sisUsuarioTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.eliminarSisUsuarioTO(sisUsuarioTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/getListaSisGrupo")
    public List<SisGrupoTO> getListaSisGrupo(@RequestBody String json) {
        // @RequestParam(value = "empresa") String empresa) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grupoService.getListaSisGrupo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

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
    public List<SisConsultaUsuarioGrupoTO> getListaUsuario(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String parametro = UtilsJSON.jsonToObjeto(String.class, map.get("parametro"));
        String infEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("infEmpresa"));
        // String infUsuario = UtilsJSON.jsonToObjeto(String.class,
        // map.get("infUsuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {

            return usuarioService.getListaUsuario(infEmpresa, parametro);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

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
    public SisUsuario buscarUsuarioPorNick(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usrNick = UtilsJSON.jsonToObjeto(String.class, map.get("usrNick"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioService.obtenerPorNick(usrNick);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/sisGrupoUsuariosTO")
    public SisGrupoTO sisGrupoUsuariosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String infEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("infEmpresa"));
        String infUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("infUsuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grupoService.sisGrupoUsuariosTO(infEmpresa, infUsuario);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/accionSisGrupoTO")
    public boolean accionSisGrupoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisGrupoTO sisGrupoTO = UtilsJSON.jsonToObjeto(SisGrupoTO.class, map.get("sisGrupoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grupoService.accionSisGrupoTO(sisGrupoTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/estadoPeriodo")
    public String estadoPeriodo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.estadoPeriodo(empresa, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaSisPeriodosTO")
    public List<SisListaPeriodoTO> getListaSisPeriodosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.getListaPeriodoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaSisPeriodos")
    public List<SisPeriodo> getListaSisPeriodos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.getListaPeriodo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
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

    @RequestMapping("/insertarSisPeriodoTO")
    public String insertarSisPeriodoTO(@RequestBody String json) {
        String respuesta = null;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPeriodoTO sisPeriodoTO = UtilsJSON.jsonToObjeto(SisPeriodoTO.class, map.get("sisPeriodoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.insertarSisPeriodoTO(sisPeriodoTO, sisInfoTO) ? "T" : "F";
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respuesta = e.getMessage();
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), e.getMessage(), sisInfoTO));
        }
        return respuesta;
    }

    @RequestMapping("/modificarSisPeriodoTO")
    public String modificarSisPeriodoTO(@RequestBody String json) {
        String respuesta = null;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPeriodoTO sisPeriodoTO = UtilsJSON.jsonToObjeto(SisPeriodoTO.class, map.get("sisPeriodoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.modificarSisPeriodoTO(sisPeriodoTO, sisInfoTO) ? "T" : "F";
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respuesta = e.getMessage();
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return respuesta;
    }

    @RequestMapping("/eliminarSisPeriodoTO")
    public boolean eliminarSisPeriodoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPeriodoTO sisPeriodoTO = UtilsJSON.jsonToObjeto(SisPeriodoTO.class, map.get("sisPeriodoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.eliminarSisPeriodoTO(sisPeriodoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/getPeriodoPorFecha")
    public SisPeriodo getPeriodoPorFecha(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, map.get("fecha"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return periodoService.getPeriodoPorFecha(fecha, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
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

    @RequestMapping("/getListaSisSusTablaTOs")
    public List<SisListaSusTablaTO> getListaSisSusTablaTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sucesoService.getListaSisSusTablaTOs(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaSisUsuario")
    public List<SisListaUsuarioTO> getListaSisUsuario(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return usuarioDetalleService.getListaSisUsuarios(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // revisar
    @RequestMapping("/getListaSisSucesoTO")
    public List<SisSucesoTO> getListaSisSucesoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String suceso = UtilsJSON.jsonToObjeto(String.class, map.get("suceso"));
        String cadenaGeneral = UtilsJSON.jsonToObjeto(String.class, map.get("cadenaGeneral"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sucesoService.getListaSisSucesoTO(desde, hasta, usuario, suceso, cadenaGeneral, empresa);
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
            boolean rpta = empresaService.insertarSisEmpresa(sisEmpresaTO, sisInfoTO);
            if (rpta) {
                empresaService.insertarRegistrosComplementarios(sisEmpresaTO.getEmpCodigo());
            }
            return rpta;
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
    public boolean modificarSisEmpresa(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisEmpresaTO sisEmpresaTO = UtilsJSON.jsonToObjeto(SisEmpresaTO.class, map.get("sisEmpresaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empresaService.modificarSisEmpresa(sisEmpresaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    // revisar
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

    @RequestMapping("/guardarArchivoFirmaElectronica")
    public String guardarArchivoFirmaElectronica(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] archivo = UtilsJSON.jsonToObjeto(byte[].class, map.get("archivo"));
        String nombreArchivo = UtilsJSON.jsonToObjeto(String.class, map.get("nombreArchivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cajaService.guardarArchivoFirmaElectronica(archivo, nombreArchivo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

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
    public List<SisEmpresaTO> getListaSisEmpresaTO(@RequestBody String json) {
        // @RequestParam(value = "usrCodigo") String usrCodigo,
        // @RequestParam(value = "empresa") String empresa) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usrCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usrCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empresaService.getListaSisEmpresaTO(usrCodigo, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaSisEmpresaTOWeb")
    public List<SisEmpresaTO> getListaSisEmpresaTOWeb(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usrCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usrCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empresaService.getListaSisEmpresaTOWeb(usrCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/modificarSisPermiso")
    public boolean modificarSisPermiso(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<SisPermisoTO> listaSisPermisoTO = UtilsJSON.jsonToList(SisPermisoTO.class, map.get("listaSisPermisoTO"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String codModifGrupo = UtilsJSON.jsonToObjeto(String.class, map.get("codModifGrupo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return permisoService.modificarSisPermiso(listaSisPermisoTO, usuario, codModifGrupo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/getColumnasEstadosFinancieros")
    public Integer getColumnasEstadosFinancieros(@RequestBody String json) {
        // @RequestParam(value = "empCodigo") String empCodigo) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return empresaParametrosService.getColumnasEstadosFinancieros(empCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

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

    @RequestMapping("/guardarImagenTexto")
    public String guardarImagenTexto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);

        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        byte[] imagen = UtilsJSON.jsonToObjeto(byte[].class, map.get("imagen"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombreArchivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return archivoService.guardarArchivo(tipo, imagen, empresa, nombre);
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
            return archivoService.eliminarArchivo(tipo, empresa, nombre);
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
            return archivoService.obtenerArchivo(tipo, empresa, nombre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSuceso")
    public byte[] generarReporteSuceso(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ReporteSuceso> lista = UtilsJSON.jsonToList(ReporteSuceso.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteSistemaService.generarReporteSuceso(lista, usuarioEmpresaReporteTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }
}
