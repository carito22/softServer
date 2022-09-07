package ec.com.todocompu.ShrimpSoftServer.activoFijo.controller;

import ec.com.todocompu.ShrimpSoftServer.activoFijo.report.ReporteActivoFijoSerice;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.service.ActivoFijoService;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.service.CategoriaActivoFijoService;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.service.DepreciacionActivoFijoService;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.service.MotivoDepreciacionActivoFijoService;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.service.UbicacionActivoFijoService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfActivoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfCategoriasTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfListadoActivoFijoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfUbicacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author DEVELOPER3
 */
@RestController
@RequestMapping("/todocompuWS/activosFijosWebController")
public class ActivosFijosWebController {

    @Autowired
    private CategoriaActivoFijoService categoriaService;
    @Autowired
    private UbicacionActivoFijoService ubicacionService;
    @Autowired
    private MotivoDepreciacionActivoFijoService motivoService;
    @Autowired
    private ActivoFijoService activoService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private DepreciacionActivoFijoService depreciacionActivoFijoService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private ReporteActivoFijoSerice reporteActivoFijoSerice;

    /*MOTIVO DEPRECIACIÓN*/
    @RequestMapping("/getListaAfDepreciacionMotivoTO")
    public RespuestaWebTO getListaAfDepreciacionMotivoTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<AfDepreciacionMotivoTO> respues = motivoService.getListaAfDepreciacionesMotivos(empresa);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron motivos de depreciación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarAfDepreciacionMotivoTO")
    public RespuestaWebTO insertarAfDepreciacionMotivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfDepreciacionMotivoTO afDepreciacionMotivoTO = UtilsJSON.jsonToObjeto(AfDepreciacionMotivoTO.class, map.get("afDepreciacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = motivoService.insertarAfDepreciacionMotivoTO(afDepreciacionMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                int secuencial = motivoService.getAfDepreciacionesMotivos(afDepreciacionMotivoTO.getAfUbicaciones().getUbiEmpresa(), afDepreciacionMotivoTO.getAfUbicaciones().getUbiCodigo(), "UBICACION").getMotSecuencial();
                resp.setExtraInfo(secuencial);
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

    @RequestMapping("/modificarAfDepreciacionMotivoTO")
    public RespuestaWebTO modificarAfDepreciacionMotivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfDepreciacionMotivoTO afDepreciacionMotivoTO = UtilsJSON.jsonToObjeto(AfDepreciacionMotivoTO.class, map.get("afDepreciacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = motivoService.modificarAfDepreciacionMotivoTO(afDepreciacionMotivoTO, sisInfoTO);
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

    @RequestMapping("/eliminarAfDepreciacionMotivoTO")
    public RespuestaWebTO eliminarAfDepreciacionMotivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfDepreciacionMotivoTO afDepreciacionMotivoTO = UtilsJSON.jsonToObjeto(AfDepreciacionMotivoTO.class, map.get("afDepreciacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = motivoService.eliminarAfDepreciacionMotivoTO(afDepreciacionMotivoTO, sisInfoTO);
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

    @RequestMapping("/generarReporteAfDepreciacionMotivoTO")
    public @ResponseBody
    String generarReporteAfDepreciacionMotivoTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportAfDepreciacionMotivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfDepreciacionMotivoTO> listado = UtilsJSON.jsonToList(AfDepreciacionMotivoTO.class, map.get("listadoAfDepreciacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = motivoService.generarReporteAfDepreciacionMotivoTO(usuarioEmpresaReporteTO, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteAfDepreciacionMotivoTO")
    public @ResponseBody
    String exportarReporteAfDepreciacionMotivoTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfDepreciacionMotivoTO> listado = UtilsJSON.jsonToList(AfDepreciacionMotivoTO.class, map.get("listadoAfDepreciacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = motivoService.exportarReporteAfDepreciacionMotivoTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*UBICACIONES*/
    @RequestMapping("/getListaAfUbicacionesTO")
    public RespuestaWebTO getListaAfUbicacionesTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<AfUbicacionesTO> respues = ubicacionService.getListaAfUbicaciones(empresa);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron ubicaciones.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarAfUbicacionesTO")
    public RespuestaWebTO insertarAfUbicacionesTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfUbicacionesTO afUbicacionesTO = UtilsJSON.jsonToObjeto(AfUbicacionesTO.class, map.get("afUbicacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ubicacionService.insertarAfUbicacionesTO(afUbicacionesTO, sisInfoTO);
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

    @RequestMapping("/modificarAfUbicacionesTO")
    public RespuestaWebTO modificarAfUbicacionesTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfUbicacionesTO afUbicacionesTO = UtilsJSON.jsonToObjeto(AfUbicacionesTO.class, map.get("afUbicacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ubicacionService.modificarAfUbicacionesTO(afUbicacionesTO, sisInfoTO);
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

    @RequestMapping("/eliminarAfUbicacionesTO")
    public RespuestaWebTO eliminarAfUbicacionesTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfUbicacionesTO afUbicacionesTO = UtilsJSON.jsonToObjeto(AfUbicacionesTO.class, map.get("afUbicacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ubicacionService.eliminarAfUbicacionesTO(afUbicacionesTO, sisInfoTO);
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

    @RequestMapping("/generarReporteAfUbicacionesTO")
    public @ResponseBody
    String generarReporteAfUbicacionesTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportAfUbicaciones.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfUbicacionesTO> listado = UtilsJSON.jsonToList(AfUbicacionesTO.class, map.get("listadoAfUbicacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = ubicacionService.generarReporteAfUbicacionesTO(usuarioEmpresaReporteTO, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteAfUbicacionesTO")
    public @ResponseBody
    String exportarReporteAfUbicacionesTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfUbicacionesTO> listado = UtilsJSON.jsonToList(AfUbicacionesTO.class, map.get("listadoAfUbicacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = ubicacionService.exportarReporteAfUbicacionesTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*GRUPO Y CLASIFICACION*/
    @RequestMapping("/getListaAfCategoriasTO")
    public RespuestaWebTO getListaAfCategoriasTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<AfCategoriasTO> respues = categoriaService.getListaAfCategorias(empresa, filtrarInactivos);
            if (respues != null && respues.size() > 0) {
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

    @RequestMapping("/consultarAfCategoriasTO")
    public RespuestaWebTO consultarAfCategoriasTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AfCategoriasTO respuesta = categoriaService.consultarAfCategoriasTO(empresa, codigo);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No existe la categoria");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarAfCategoriasTO")
    public RespuestaWebTO insertarAfCategoriasTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfCategoriasTO afCategoriasTO = UtilsJSON.jsonToObjeto(AfCategoriasTO.class, map.get("afCategoriasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = categoriaService.insertarAfCategoriasTO(afCategoriasTO, sisInfoTO);
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

    @RequestMapping("/modificarAfCategoriasTO")
    public RespuestaWebTO modificarAfCategoriasTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfCategoriasTO afCategoriasTO = UtilsJSON.jsonToObjeto(AfCategoriasTO.class, map.get("afCategoriasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = categoriaService.modificarAfCategoriasTO(afCategoriasTO, sisInfoTO);
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

    @RequestMapping("/eliminarAfCategoriasTO")
    public RespuestaWebTO eliminarAfCategoriasTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfCategoriasTO afCategoriasTO = UtilsJSON.jsonToObjeto(AfCategoriasTO.class, map.get("afCategoriasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = categoriaService.eliminarAfCategoriasTO(afCategoriasTO, sisInfoTO);
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

    @RequestMapping("/generarReporteAfCategoriasTO")
    public @ResponseBody
    String generarReporteAfCategoriasTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportAfCategorias.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfCategoriasTO> listado = UtilsJSON.jsonToList(AfCategoriasTO.class, map.get("listadoAfCategoriasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = categoriaService.generarReporteAfCategoriasTO(usuarioEmpresaReporteTO, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteAfCategoriasTO")
    public @ResponseBody
    String exportarReporteAfCategoriasTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfCategoriasTO> listado = UtilsJSON.jsonToList(AfCategoriasTO.class, map.get("listadoAfCategoriasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = categoriaService.exportarReporteAfCategoriasTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*ACTIVOS FIJOS*/
    @RequestMapping("/getListaAfActivoTO")
    public RespuestaWebTO getListaAfActivoTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String ubicacion = UtilsJSON.jsonToObjeto(String.class, map.get("ubicacion"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<AfActivoTO> respues = activoService.getListaAfActivos(empresa, sector, ubicacion);
            if (respues != null && respues.size() > 0) {
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

    @RequestMapping("/insertarAfActivoTO")
    public RespuestaWebTO insertarAfActivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfActivoTO afActivoTO = UtilsJSON.jsonToObjeto(AfActivoTO.class, map.get("afActivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = activoService.insertarAfActivoTO(afActivoTO, sisInfoTO);
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

    @RequestMapping("/modificarAfActivoTO")
    public RespuestaWebTO modificarAfActivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AfActivoTO afActivoTO = UtilsJSON.jsonToObjeto(AfActivoTO.class, map.get("afActivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = activoService.modificarAfActivoTO(afActivoTO, sisInfoTO);
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

    @RequestMapping("/eliminarAfActivoTO")
    public RespuestaWebTO eliminarAfActivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoProducto = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = activoService.eliminarAfActivoTO(empresa, codigoProducto, sisInfoTO);
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

    @RequestMapping("/generarReporteAfActivoTO")
    public @ResponseBody
    String generarReporteAfActivoTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportAfActivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfActivoTO> listado = UtilsJSON.jsonToList(AfActivoTO.class, map.get("listadoAfActivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = activoService.generarReporteAfActivoTO(usuarioEmpresaReporteTO, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteAfActivoTO")
    public @ResponseBody
    String exportarReporteAfActivoTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfActivoTO> listado = UtilsJSON.jsonToList(AfActivoTO.class, map.get("listadoAfActivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = activoService.exportarReporteAfActivoTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoActivoFijoCompra")
    public @ResponseBody
    String exportarReporteListadoActivoFijoCompra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfListadoActivoFijoTO> listado = UtilsJSON.jsonToList(AfListadoActivoFijoTO.class, map.get("listadoResultados"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = activoService.exportarReporteListadoActivoFijoCompra(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoActivoCompraMatricial")
    public RespuestaWebTO generarReporteListadoActivoCompraMatricial(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportListadoActivoFijo.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfListadoActivoFijoTO> listado = UtilsJSON.jsonToList(AfListadoActivoFijoTO.class, map.get("listadoResultados"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] respuesta = activoService.generarReporteListadoActivoCompra(usuarioEmpresaReporteTO, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoActivoCompra")
    public @ResponseBody
    String generarReporteListadoActivoCompra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportListadoActivoFijo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfListadoActivoFijoTO> listado = UtilsJSON.jsonToList(AfListadoActivoFijoTO.class, map.get("listadoAfCategoriasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = activoService.generarReporteListadoActivoCompra(usuarioEmpresaReporteTO, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListarActivoFijosCompras")
    public RespuestaWebTO getListarActivoFijosCompras(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<AfListadoActivoFijoTO> respues = activoService.listarActivoFijosCompras(empresa);
            if (respues != null && respues.size() > 0) {
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

    /*DEPRECIACION*/
    @RequestMapping("/getListaAfDepreciaciones")
    public RespuestaWebTO getListaAfDepreciaciones(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<AfDepreciacionesTO> respues = depreciacionActivoFijoService.listarDepreciaciones(empresa, fecha);
            if (respues != null && respues.size() > 0) {
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

    @RequestMapping("/exportarListadoDepreciacion")
    public @ResponseBody
    String exportarListadoDepreciacion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfDepreciacionesTO> listado = UtilsJSON.jsonToList(AfDepreciacionesTO.class, map.get("ListaAfDepreciacionesTO"));
        String fechaIngreso = UtilsJSON.jsonToObjeto(String.class, map.get("fechaIngreso"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = depreciacionActivoFijoService.exportarListadoDepreciacion(usuarioEmpresaReporteTO, fechaIngreso, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarAfDepreciaciones")
    public RespuestaWebTO insertarAfDepreciaciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        List<AfDepreciacionesTO> listado = UtilsJSON.jsonToList(AfDepreciacionesTO.class, map.get("listaAfDepreciacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = depreciacionActivoFijoService.insertarModificarAfDepreciaciones(empresa, fecha, listado, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable contable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(contable.getConContablePK()); // respuesta (mensaje. contable y la lista)
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado la depreciación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //matricial
    @RequestMapping("/generarReporteAfActivoTOMatricial")
    public RespuestaWebTO generarReporteAfActivoTOMatricial(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportAfActivo.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfActivoTO> listado = UtilsJSON.jsonToList(AfActivoTO.class, map.get("listadoAfActivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] respuesta = activoService.generarReporteAfActivoTO(usuarioEmpresaReporteTO, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteAfCategoriasTOMatricial")
    public RespuestaWebTO generarReporteAfCategoriasTOMatricial(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportAfCategorias.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfCategoriasTO> listado = UtilsJSON.jsonToList(AfCategoriasTO.class, map.get("listadoAfCategoriasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] respuesta = categoriaService.generarReporteAfCategoriasTO(usuarioEmpresaReporteTO, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteAfDepreciacionMotivoTOMatricial")
    public RespuestaWebTO generarReporteAfDepreciacionMotivoTOMatricial(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportAfDepreciacionMotivo.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfDepreciacionMotivoTO> listado = UtilsJSON.jsonToList(AfDepreciacionMotivoTO.class, map.get("listadoAfDepreciacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] respuesta = motivoService.generarReporteAfDepreciacionMotivoTO(usuarioEmpresaReporteTO, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteAfUbicacionesTOMatricial")
    public RespuestaWebTO generarReporteAfUbicacionesTOMatricial(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportAfUbicaciones.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AfUbicacionesTO> listado = UtilsJSON.jsonToList(AfUbicacionesTO.class, map.get("listadoAfUbicacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            byte[] respuesta = ubicacionService.generarReporteAfUbicacionesTO(usuarioEmpresaReporteTO, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAfDepreciacionesConsulta")
    public RespuestaWebTO getListaAfDepreciacionesConsulta(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            List<AfDepreciacionesTO> respues = depreciacionActivoFijoService.listarDepreciacionesConsulta(empresa, fechaDesde, fechaHasta);
            if (respues != null && respues.size() > 0) {
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

    @RequestMapping("/generarReporteDepreciacionConsulta")
    public @ResponseBody
    String imprimirReporteChequesCobrar(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportAfDepreciacionesDetalle.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AfDepreciacionesTO> listado = UtilsJSON.jsonToList(AfDepreciacionesTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteActivoFijoSerice.generarReporteDepreciacionConsulta(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDepreciacionConsultaMatricial")
    public RespuestaWebTO generarReporteDepreciacionConsultaMatricial(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AfDepreciacionesTO> listado = UtilsJSON.jsonToList(AfDepreciacionesTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteActivoFijoSerice.generarReporteDepreciacionConsulta(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

}
