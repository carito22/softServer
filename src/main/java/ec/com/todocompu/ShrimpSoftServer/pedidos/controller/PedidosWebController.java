package ec.com.todocompu.ShrimpSoftServer.pedidos.controller;

import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.OrdenCompraConfiguracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosConfiguracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosOrdenCompraMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosOrdenCompraService;
import ec.com.todocompu.ShrimpSoftServer.pedidos.report.ReportePedidosService;
import ec.com.todocompu.ShrimpSoftServer.pedidos.service.InvPedidosService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SisEmpresaNotificacionesService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ComprasVSOrdenesCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvOrdenCompraMotivoDetalleAprobadoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosConfiguracionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosReporteComprasImbTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosReporteEntregaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraSaldo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraVsCompraDolaresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.pedidos.TO.InvPedidoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/todocompuWS/pedidosWebController")
public class PedidosWebController {

    @Autowired
    private InvPedidosService invPedidosService;
    @Autowired
    private PedidosDetalleService pedidosDetalleService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private PedidosMotivoService pedidosMotivoService;
    @Autowired
    private PedidosConfiguracionService pedidosConfiguracionService;
    @Autowired
    private ReportePedidosService reportePedidosService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private PedidosOrdenCompraMotivoService pedidosOrdenCompraMotivoService;
    @Autowired
    private PedidosOrdenCompraService pedidosOrdenCompraService;
    @Autowired
    private OrdenCompraConfiguracionService compraConfiguracionService;
    @Autowired
    private GenericoDao<InvPedidosOrdenCompraDetalle, Integer> pedidosOrdenCompraDetalleDao;
    @Autowired
    private SisEmpresaNotificacionesService sisEmpresaNotificacionesService;

    /*INV_PEDIDOS*/
    @RequestMapping("/obtenerDatosParaGenerarOP")
    public RespuestaWebTO obtenerDatosParaGenerarOP(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        InvPedidosMotivoPK invPedidosMotivoPK = UtilsJSON.jsonToObjeto(InvPedidosMotivoPK.class, parametros.get("invPedidosMotivoPK"));
        try {
            Map<String, Object> respues = invPedidosService.obtenerDatosParaGenerarOP(invPedidosMotivoPK, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró datos para generar orden de pedido.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaPantallaOP")
    public RespuestaWebTO obtenerDatosParaPantallaOP(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        try {
            Map<String, Object> respues = invPedidosService.obtenerDatosParaPantallaOP(empresa, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró datos para generar orden de pedido.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvPedidos")
    public RespuestaWebTO listarInvPedidos(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String busqueda = SistemaWebUtil.obtenerFiltroComoString(parametros, "busqueda");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String tipo = SistemaWebUtil.obtenerFiltroComoString(parametros, "tipo");//GENERAROP O APROBAR
        Integer nroRegistros = SistemaWebUtil.obtenerFiltroComoInteger(parametros, "nroRegistros");
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        try {
            List<InvPedidoTO> respues = invPedidosService.getInvPedidos(empresa, busqueda, motivo, nroRegistros, tipo, fechaInicio, fechaFin);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de la búsqueda.");
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

    @RequestMapping("/getListaInvPedidosOrdenTO")
    public RespuestaWebTO listarInvPedidosOrdenTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));//Sector
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        Boolean soloRegistrados = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("soloRegistrados"));
        Boolean soloAprobados = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("soloAprobados"));
        Boolean soloEjecutados = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("soloEjecutados"));
        try {
            String usuario = sisInfoTO != null ? sisInfoTO.getUsuario() : null;
            List<InvPedidoTO> respues = invPedidosService.getListaInvPedidosOrdenTO(empresa, motivo, busqueda, fechaInicio, fechaFin, soloRegistrados, soloAprobados, soloEjecutados, usuario);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de órdenes de pedidos");
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

    @RequestMapping("/obtenerInvPedidos")
    public RespuestaWebTO obtenerInvPedidos(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String sector = SistemaWebUtil.obtenerFiltroComoString(parametros, "sector");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String numero = SistemaWebUtil.obtenerFiltroComoString(parametros, "numero");
        try {
            InvPedidos respues = invPedidosService.obtenerInvPedidos(new InvPedidosPK(empresa, sector, motivo, numero));
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró pedido.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvPedidos")
    public RespuestaWebTO insertarInvPedidos(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidos invPedidos = UtilsJSON.jsonToObjeto(InvPedidos.class, map.get("invPedidos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidos respues = invPedidosService.insertarInvPedidos(invPedidos, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La orden de pedido N. " + invPedidos.getInvPedidosPK().getPedNumero() + " se ha guardado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se ha guardado la orden de pedido debido a que ya existe");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvPedidos")
    public RespuestaWebTO modificarInvPedidos(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidos invPedidos = UtilsJSON.jsonToObjeto(InvPedidos.class, map.get("invPedidos"));
        String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidos respues = invPedidosService.modificarInvPedidos(invPedidos, accion, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La orden de pedido N. " + invPedidos.getInvPedidosPK().getPedNumero() + " se ha modificado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha modificado la orden de pedido.");
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

    @RequestMapping("/desaprobarInvPedidos")
    public RespuestaWebTO desaprobarInvPedidos(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidos invPedidos = UtilsJSON.jsonToObjeto(InvPedidos.class, map.get("invPedidos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidos respues = invPedidosService.desaprobarInvPedidos(invPedidos, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La orden de pedido N. " + invPedidos.getInvPedidosPK().getPedNumero() + " se ha modificado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha modificado la orden de pedido.");
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

    @RequestMapping("/desmayorizarInvPedidos")
    public RespuestaWebTO desmayorizarInvPedidos(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = invPedidosService.desmayorizarInvPedidos(invPedidosPK, sisInfoTO);
            if (respues.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
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

    @RequestMapping("/anularInvPedidos")
    public RespuestaWebTO anularInvPedidos(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        String tipo = SistemaWebUtil.obtenerFiltroComoString(map, "tipo");//GENERAROP O APROBAR
        String motivoAnulacion = SistemaWebUtil.obtenerFiltroComoString(map, "motivoAnulacion");
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = invPedidosService.anularInvPedidos(invPedidosPK, tipo, motivoAnulacion, sisInfoTO);
            if (respues.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
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

    @RequestMapping("/restaturarInvPedidos")
    public RespuestaWebTO restaturarInvPedidos(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = invPedidosService.restaturarInvPedidos(invPedidosPK, sisInfoTO);
            if (respues.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
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

    @RequestMapping("/eliminarInvPedidosDetalle")
    public RespuestaWebTO eliminarInvPedidosDetalle(@RequestBody String json) throws Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer pk = UtilsJSON.jsonToObjeto(Integer.class, map.get("detSecuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = pedidosDetalleService.eliminarInvPedidosDetalle(pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El detalle de pedido se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado el detalle de pedido.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El detalle de pedido tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvPedidos")
    public RespuestaWebTO eliminarInvPedidosTO(@RequestBody String json) throws Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosPK pk = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = invPedidosService.eliminarInvPedidos(pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La orden de pedido N. " + pk.getPedNumero() + " se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado la orden de pedido");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. " + "La orden de pedido N. " + pk.getPedNumero() + " tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*REPORTES DE ORDEN PEDIDO*/
    @RequestMapping("/generarReporteInvPedidosGeneral")
    public @ResponseBody
    String generarReporteInvPedidosGeneral(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        try {
            InvPedidoTO invPedidosTO = invPedidosService.obtenerInvPedidosTO(invPedidosPK);
            InvPedidos invPedidos = invPedidosService.obtenerInvPedidos(invPedidosPK);
            if (invPedidosTO != null && invPedidos != null) {
                respuesta = reportePedidosService.generarReporteInvPedidosGeneral(usuarioEmpresaReporteTO, invPedidos, invPedidosTO, nombreReporte);
                return archivoService.generarReportePDF(respuesta, nombreReporte, response);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvPedidosLote")
    public @ResponseBody
    String generarReporteInvPedidosLote(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosPK> listaInvPedidosPK = UtilsJSON.jsonToList(InvPedidosPK.class, map.get("listaInvPedidosPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (listaInvPedidosPK.size() > 0) {
                respuesta = reportePedidosService.generarReporteInvPedidosPorLote(usuarioEmpresaReporteTO, listaInvPedidosPK, nombreReporte);
                return archivoService.generarReportePDF(respuesta, nombreReporte, response);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvPedidosTO")
    public @ResponseBody
    String generarReporteInvPedidosTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidoTO> listaInvPedidoTO = UtilsJSON.jsonToList(InvPedidoTO.class, map.get("listadoInvPedidosTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteInvPedidosTO(usuarioEmpresaReporteTO, listaInvPedidoTO, codigoSector, codigoMotivo, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvPedidosTO")
    public @ResponseBody
    String exportarReporteInvPedidosTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidoTO> listaInvPedidoTO = UtilsJSON.jsonToList(InvPedidoTO.class, map.get("listadoInvPedidosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.exportarReporteInvPedidoTO(usuarioEmpresaReporteTO, listaInvPedidoTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvOrdenPedido")
    public @ResponseBody
    String exportarReporteInvOrdenPedido(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidoTO> listaInvPedidoTO = UtilsJSON.jsonToList(InvPedidoTO.class, map.get("listadoInvPedidosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.exportarReporteInvOrdenPedido(usuarioEmpresaReporteTO, listaInvPedidoTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvPedidos")
    public @ResponseBody
    String exportarReporteInvPedidos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        try {
            InvPedidoTO invPedidosTO = invPedidosService.obtenerInvPedidosTO(invPedidosPK);
            InvPedidos invPedidos = invPedidosService.obtenerInvPedidos(invPedidosPK);
            if (invPedidosTO != null && invPedidos != null) {
                respuesta = reportePedidosService.exportarReporteInvPedidos(usuarioEmpresaReporteTO, invPedidos, invPedidosTO);
                List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
                List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
                archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /* Ver configuracion y motivo*/
    @RequestMapping("/getListaInvPedidosMotivo")
    public ResponseEntity getListaInvPedidosMotivo(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        PrdSectorPK prdSectorPK = UtilsJSON.jsonToObjeto(PrdSectorPK.class, map.get("prdSectorPK"));
        boolean incluirInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirInactivos"));
        try {
            List<InvPedidosMotivo> respues = pedidosMotivoService.getListaInvPedidosMotivo(empresa, incluirInactivos, prdSectorPK);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de la búsqueda motivo de pedido.");
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
    }

    @RequestMapping("/getListaInvPedidosMotivoTO")
    public RespuestaWebTO getListaInvPedidosMotivoTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        PrdSectorPK prdSectorPK = UtilsJSON.jsonToObjeto(PrdSectorPK.class, map.get("prdSectorPK"));
        InvProductoCategoriaPK invProductoCategoriaPK = UtilsJSON.jsonToObjeto(InvProductoCategoriaPK.class, map.get("invProductoCategoriaPK"));
        boolean incluirInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirInactivos"));
        try {
            List<InvPedidosMotivoTO> respues = pedidosMotivoService.getListaInvPedidosMotivoTO(empresa, incluirInactivos, prdSectorPK, invProductoCategoriaPK);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
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

    @RequestMapping("/getInvPedidosMotivo")
    public RespuestaWebTO getInvPedidosMotivo(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosMotivoPK invPedidosMotivoPK = UtilsJSON.jsonToObjeto(InvPedidosMotivoPK.class, map.get("invPedidosMotivoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosConfiguracionTO conf = pedidosConfiguracionService.getListaInvPedidosConfiguracionTO(invPedidosMotivoPK, sisInfoTO);
            InvPedidosMotivo mot = pedidosMotivoService.getInvPedidosMotivo(invPedidosMotivoPK);
            Map<String, Object> campos = new HashMap<>();
            if (mot != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                campos.put("motivo", mot);
                campos.put("configuracion", conf);
                resp.setExtraInfo(campos);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró motivo de pedido.");
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

    @RequestMapping("/generarReporteInvPedidosMotivo")
    public @ResponseBody
    String generarReporteInvPedidosMotivo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportPedidosMotivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosMotivoTO> listaInvPedidosMotivo = UtilsJSON.jsonToList(InvPedidosMotivoTO.class, map.get("listadoInvPedidosMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteInvPedidosMotivo(usuarioEmpresaReporteTO, listaInvPedidosMotivo, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvPedidosMotivo")
    public @ResponseBody
    String exportarReporteInvPedidosMotivo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosMotivoTO> listaInvPedidosMotivo = UtilsJSON.jsonToList(InvPedidosMotivoTO.class, map.get("listadoInvPedidosMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        Map<String, Object> respuesta;
        try {
            respuesta = reportePedidosService.exportarReporteInvPedidosMotivo(usuarioEmpresaReporteTO, listaInvPedidosMotivo);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvPedidosConfiguracion")
    public ResponseEntity insertarInvPedidosConfiguracion(@RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        InvPedidosConfiguracionTO invPedidosConfiguracionTO = UtilsJSON.jsonToObjeto(InvPedidosConfiguracionTO.class, map.get("invPedidosConfiguracionTO"));
        InvPedidosMotivo invPedidosMotivo = UtilsJSON.jsonToObjeto(InvPedidosMotivo.class, map.get("invPedidosMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosMotivo mot = pedidosMotivoService.insertarInvPedidosMotivo(invPedidosMotivo, sisInfoTO);
            if (mot != null) {
                InvPedidosMotivo respues = pedidosConfiguracionService.insertarMotivoPedidoConfiguracionTO(invPedidosConfiguracionTO, invPedidosMotivo, false, sisInfoTO);
                if (respues != null) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje("El motivo de orden de pedido: Código " + invPedidosMotivo.getInvPedidosMotivoPK().getPmCodigo() + ", se ha guardado correctamente.");
                    resp.setExtraInfo(respues);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje("Ocurrió un error al guardar la configuración de orden de pedido.");
                }
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
    }

    @RequestMapping("/modificarInvPedidosMotivo")
    public RespuestaWebTO modificarInvPedidosMotivo(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosMotivo invPedidosMotivo = UtilsJSON.jsonToObjeto(InvPedidosMotivo.class, map.get("invPedidosMotivo"));
        InvPedidosConfiguracionTO invPedidosConfiguracionTO = UtilsJSON.jsonToObjeto(InvPedidosConfiguracionTO.class, map.get("invPedidosConfiguracionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosMotivo motivo = pedidosMotivoService.modificarInvPedidosMotivo(invPedidosMotivo, invPedidosConfiguracionTO, sisInfoTO);
            if (motivo != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de orden de pedido: Código " + invPedidosMotivo.getInvPedidosMotivoPK().getPmCodigo() + ", se ha modificado correctamente.");
                resp.setExtraInfo(motivo);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se ha modificado motivo de pedido debido a que ya existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvPedidosMotivo")
    public ResponseEntity modificarEstadoInvPedidosMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosMotivoPK pk = UtilsJSON.jsonToObjeto(InvPedidosMotivoPK.class, map.get("invPedidosMotivoPK"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            boolean respues = pedidosMotivoService.modificarEstadoInvPedidosMotivo(pk, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El estado de motivo de orden de pedido: Código " + pk.getPmCodigo() + ", se ha modificado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se ha modificado el estado del motivo de pedido");
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
    }

    @RequestMapping("/eliminarInvPedidosMotivo")
    public RespuestaWebTO eliminarInvPedidosMotivo(@RequestBody String json) throws GeneralException, Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosMotivoPK pk = UtilsJSON.jsonToObjeto(InvPedidosMotivoPK.class, map.get("invPedidosMotivoPK"));
        InvPedidosConfiguracionTO conf = pedidosConfiguracionService.getListaInvPedidosConfiguracionTO(pk, sisInfoTO);
        try {
            boolean respues = pedidosMotivoService.eliminarInvPedidosMotivo(pk, conf, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de orden de pedido: Código " + pk.getPmCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado motivo de pedido.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El motivo de pedido tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaInvPedidosConfiguracion")
    public RespuestaWebTO getListaInvPedidosConfiguracion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosMotivoPK invPedidosMotivoPK = UtilsJSON.jsonToObjeto(InvPedidosMotivoPK.class, map.get("invPedidosMotivoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosConfiguracionTO respues = pedidosConfiguracionService.getListaInvPedidosConfiguracionTO(invPedidosMotivoPK, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se obtuvo la configuración de orden de pedido.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /* INV_PEDIDOS_ORDEN_COMPRA_MOTIVO */
    @RequestMapping("/insertarConfiguracionOrdenCompra")
    public RespuestaWebTO insertarInvPedidosOrdenCompraMotivoTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraMotivo invPedidosOrdenCompraMotivo = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraMotivo.class, map.get("invPedidosOrdenCompraMotivo"));
        List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores = UtilsJSON.jsonToList(InvOrdenCompraMotivoDetalleAprobadoresTO.class, map.get("aprobadores"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosOrdenCompraMotivo respues = pedidosOrdenCompraMotivoService.insertarInvPedidosOrdenCompraMotivo(invPedidosOrdenCompraMotivo, invOrdenCompraMotivoDetalleAprobadores, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de orden de compra: Código " + respues.getInvPedidosOrdenCompraMotivoPK().getOcmCodigo() + ", se ha guardado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se ha guardado motivo de orden de compra debido a que ya existe");
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

    @RequestMapping("/modificarConfiguracionOrdenCompra")
    public RespuestaWebTO modificarInvPedidosOrdenCompraMotivo(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraMotivo invPedidosOrdenCompraMotivo = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraMotivo.class, map.get("invPedidosOrdenCompraMotivo"));
        List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores = UtilsJSON.jsonToList(InvOrdenCompraMotivoDetalleAprobadoresTO.class, map.get("aprobadores"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosOrdenCompraMotivo respues = pedidosOrdenCompraMotivoService.modificarInvPedidosOrdenCompraMotivo(invPedidosOrdenCompraMotivo, invOrdenCompraMotivoDetalleAprobadores, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de orden de compra: Código " + respues.getInvPedidosOrdenCompraMotivoPK().getOcmCodigo() + ", se ha modificado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se ha modificado motivo de orden de compra debido a que ya existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvPedidosOrdenCompraMotivo")
    public ResponseEntity modificarEstadoInvPedidosOrdenCompraMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosOrdenCompraMotivoPK pk = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraMotivoPK.class, map.get("invPedidosOrdenCompraMotivoPK"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            boolean respues = pedidosOrdenCompraMotivoService.modificarEstadoInvPedidosOrdenCompraMotivo(pk, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de orden de compra: Código " + pk.getOcmCodigo() + (estado ? ", se ha inactivado correctamente." : ", se ha activado correctamente."));
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se ha modificado el estado del motivo de compra");
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
    }

    @RequestMapping("/getListaInvPedidosOrdenCompraMotivoTO")
    public RespuestaWebTO getListaInvPedidosOrdenCompraMotivoTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        try {
            List<InvPedidosOrdenCompraMotivoTO> respues = pedidosOrdenCompraMotivoService.getListaInvPedidosOrdenCompraMotivo(empresa, sector);
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

    @RequestMapping("/getInvPedidosOrdenCompraMotivo")
    public RespuestaWebTO getInvPedidosOrdenCompraMotivo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraMotivoPK invPedidosOrdenCompraMotivoPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraMotivoPK.class, map.get("invPedidosOrdenCompraMotivoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        Map<String, Object> campos = new HashMap<>();
        try {
            InvPedidosOrdenCompraMotivo respues = pedidosOrdenCompraMotivoService.getInvPedidosOrdenCompraMotivo(invPedidosOrdenCompraMotivoPK);
            List<SisEmpresaNotificaciones> listadoConfNoticaciones = sisEmpresaNotificacionesService.listarSisEmpresaNotificaciones(invPedidosOrdenCompraMotivoPK.getOcmEmpresa());
            List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores = compraConfiguracionService.getListaInvOrdenCompraMotivoDetalleAprobadoresTO(invPedidosOrdenCompraMotivoPK, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                campos.put("motivo", respues);
                campos.put("aprobadores", invOrdenCompraMotivoDetalleAprobadores);
                campos.put("listadoConfNoticaciones", listadoConfNoticaciones);
                resp.setExtraInfo(campos);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró el motivo de orden de compra.");
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

    @RequestMapping("/eliminarInvPedidosOrdenCompraMotivo")
    public RespuestaWebTO eliminarInvPedidosOrdenCompraMotivo(@RequestBody String json) throws GeneralException, Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosOrdenCompraMotivoPK invPedidosOrdenCompraMotivoPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraMotivoPK.class, map.get("invPedidosOrdenCompraMotivoPK"));
        try {
            boolean respues = pedidosOrdenCompraMotivoService.eliminarInvPedidosOrdenCompraMotivo(invPedidosOrdenCompraMotivoPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El motivo de orden de compra: Código " + invPedidosOrdenCompraMotivoPK.getOcmCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado motivo de orden de compra");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El motivo de orden de compra tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvPedidosOrdenCompraMotivo")
    public @ResponseBody
    String generarReporteInvPedidosOrdenCompraMotivo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportPedidosOrdenCompraMotivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosOrdenCompraMotivoTO> listaInvPedidosOrdenCompraMotivoTO = UtilsJSON.jsonToList(InvPedidosOrdenCompraMotivoTO.class, map.get("listaInvPedidosOrdenCompraMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteInvPedidosOrdenCompraMotivo(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraMotivoTO, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvPedidosOrdenCompraMotivo")
    public @ResponseBody
    String exportarReporteInvPedidosOrdenCompraMotivo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosOrdenCompraMotivoTO> listaInvPedidosOrdenCompraMotivoTO = UtilsJSON.jsonToList(InvPedidosOrdenCompraMotivoTO.class, map.get("listaInvPedidosOrdenCompraMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.exportarReporteInvPedidosOrdenCompraMotivo(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraMotivoTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*INV_PEDIDOS_ORDEN_COMPRA*/
    @RequestMapping("/validarSiTieneOrdenesComprasPendientes")
    public RespuestaWebTO validarSiTieneOrdenesComprasPendientes(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosPK pk = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = pedidosOrdenCompraService.validarSiTieneOrdenesComprasPendientes(pk);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getDatosGenerarInvPedidosOrdenCompra")
    public RespuestaWebTO getDatosGenerarInvPedidosOrdenCompra(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        try {
            Map<String, Object> respues = pedidosOrdenCompraService.getDatosGenerarInvPedidosOrdenCompra(invPedidosPK, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para generar la orden de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaGenerarOrdenCompra")
    public RespuestaWebTO obtenerDatosParaGenerarOrdenCompra(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosMotivoPK invPedidosMotivoPK = UtilsJSON.jsonToObjeto(InvPedidosMotivoPK.class, map.get("invPedidosMotivoPK"));
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        try {
            Map<String, Object> respues = pedidosOrdenCompraService.obtenerDatosParaGenerarOrdenCompra(invPedidosPK, invPedidosMotivoPK, provCodigo, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para generar la orden de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerOrdenCompra")
    public RespuestaWebTO obtenerOrdenCompra(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK pk = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosOrdenCompra respues = pedidosOrdenCompraService.getInvPedidosOrdenCompra(pk);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No existe la orden de compra");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvPedidosOrdenCompraPorProveedorConSaldo")
    public RespuestaWebTO getInvPedidosOrdenCompraPorProveedorConSaldo(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        String ocSector = UtilsJSON.jsonToObjeto(String.class, map.get("ocSector"));
        String ocMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("ocMotivo"));
        String ocNumero = UtilsJSON.jsonToObjeto(String.class, map.get("ocNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<OrdenCompraProveedor> lista = pedidosOrdenCompraService.getInvPedidosOrdenCompraPorProveedorConSaldo(empresa, provCodigo, ocSector, ocMotivo, ocNumero);
            if (lista != null && lista.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(lista);
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

    @RequestMapping("/getInvPedidosOrdenCompraSaldo")
    public RespuestaWebTO getInvPedidosOrdenCompraSaldo(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK pk = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<OrdenCompraSaldo> lista = pedidosOrdenCompraService.getInvPedidosOrdenCompraSaldo(pk);
            if (lista != null && lista.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(lista);
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

    @RequestMapping("/getInvPedidosOrdenCompraVsOrdenesCompras")
    public RespuestaWebTO getInvPedidosOrdenCompraVsOrdenesCompras(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ComprasVSOrdenesCompras> lista = pedidosOrdenCompraService.getInvPedidosOrdenCompraVsOrdenesCompras(empresa, provCodigo, sector, motivo, fechaDesde, fechaHasta);
            if (lista != null && lista.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(lista);
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

    @RequestMapping("/exportarReporteOrdenCompraVsOrdenesCompras")
    public @ResponseBody
    String exportarReporteOrdenCompraVsOrdenesCompras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ComprasVSOrdenesCompras> comprasVSOrdenesCompras = UtilsJSON.jsonToList(ComprasVSOrdenesCompras.class, map.get("comprasVSOrdenesCompras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.exportarReporteOrdenCompraVsOrdenesCompras(usuarioEmpresaReporteTO, comprasVSOrdenesCompras);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/listarOrdenCompraVsCompraDolares")
    public RespuestaWebTO listarOrdenCompraVsCompraDolares(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<OrdenCompraVsCompraDolaresTO> lista = pedidosOrdenCompraService.listarOrdenCompraVsCompraDolares(empresa, sector, motivo, fechaDesde, fechaHasta);
            if (lista != null && lista.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(lista);
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

    @RequestMapping("/exportarOrdenCompraVsCompraDolares")
    public @ResponseBody
    String exportarOrdenCompraVsCompraDolares(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<OrdenCompraVsCompraDolaresTO> comprasVSOrdenesCompras = UtilsJSON.jsonToList(OrdenCompraVsCompraDolaresTO.class, map.get("comprasVSOrdenesCompras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.exportarOrdenCompraVsCompraDolares(usuarioEmpresaReporteTO, comprasVSOrdenesCompras);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerOrdenCompraSoloAprobadas")
    public RespuestaWebTO obtenerOrdenCompraSoloAprobadas(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK pk = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("pk"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        boolean buscarIndividual = UtilsJSON.jsonToObjeto(boolean.class, map.get("buscarIndividual"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean puedeBuscarOrdenCompra = true;
            Map<String, Object> respues = null;
            if (buscarIndividual) {
                List<OrdenCompraProveedor> lista = pedidosOrdenCompraService.getInvPedidosOrdenCompraPorProveedorConSaldo(pk.getOcEmpresa(), provCodigo, pk.getOcSector(), pk.getOcMotivo(), pk.getOcNumero());
                puedeBuscarOrdenCompra = lista != null && lista.size() > 0;
            }
            if (puedeBuscarOrdenCompra) {
                InvPedidosOrdenCompra ipoc = pedidosOrdenCompraService.getInvPedidosOrdenCompra(pk);
                respues = pedidosOrdenCompraService.obtenerOrdenCompraSoloAprobadas(ipoc, sisInfoTO);
            }

            if (respues != null) {
                InvPedidosOrdenCompra oc = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompra.class, respues.get("ordenDeCompra"));
                if (oc != null) {
                    if (oc.getOcAnulado()) {
                        resp.setOperacionMensaje("La orden de compra está ANULADA");
                    } else {
                        if (oc.getOcAprobada() && !oc.isOcCerrada()) {
                            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                            resp.setExtraInfo(respues);
                        } else {
                            //CERRAR ORDEN COMPRA
                            if (oc.isOcCerrada()) {
                                pedidosOrdenCompraService.cerrarInvPedidosOrdenCompra(pk, sisInfoTO);
                            }
                            resp.setOperacionMensaje("La orden de compra no está APROBADA o CERRADA");
                        }
                    }
                } else {
                    resp.setOperacionMensaje("La orden de compra no existe");
                }
            } else {
                resp.setOperacionMensaje("La orden de compra no existe o ya se encuentra importada en otra compra");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvPedidosOrdenCompra")
    public RespuestaWebTO insertarInvPedidosOrdenCompra(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompra invPedidosOrdenCompra = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompra.class, map.get("invPedidosOrdenCompra"));
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        List<InvPedidosDetalle> listaInvPedidosDetalle = UtilsJSON.jsonToList(InvPedidosDetalle.class, map.get("listaInvPedidosDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = pedidosOrdenCompraService.insertarInvPedidosOrdenCompra(invPedidosOrdenCompra, invPedidosPK, listaInvPedidosDetalle, sisInfoTO);
            if (respues != null) {
                List<InvPedidosDetalle> listaInvPedidosDetalleRetorno = invPedidosService.listarDetallesParaOrdenDecompra(invPedidosPK);
                InvPedidoTO invPedidosTO = invPedidosService.obtenerInvPedidosTO(invPedidosPK);
                respues.put("listaInvPedidosDetalle", listaInvPedidosDetalleRetorno);
                respues.put("invPedidosTO", invPedidosTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se ha generado correctamente la orden de compra.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se generó orden de compra debido a que ya existe");
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

    @RequestMapping("/mayorizarInvPedidosOrdenCompra")
    public RespuestaWebTO mayorizarInvPedidosOrdenCompra(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompra invPedidosOrdenCompra = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompra.class, map.get("invPedidosOrdenCompra"));
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        List<InvPedidosDetalle> listaInvPedidosDetalle = UtilsJSON.jsonToList(InvPedidosDetalle.class, map.get("listaInvPedidosDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosOrdenCompra respues = pedidosOrdenCompraService.mayorizarInvPedidosOrdenCompra(invPedidosOrdenCompra, invPedidosPK, listaInvPedidosDetalle, sisInfoTO);
            if (respues != null) {
                List<InvPedidosOrdenCompraDetalle> detalleLocal = pedidosOrdenCompraService.getInvPedidosOrdenCompraDetalle(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK());
                if (detalleLocal != null && !detalleLocal.isEmpty()) {
                    detalleLocal.removeAll(respues.getInvPedidosOrdenCompraDetalleList());
                    detalleLocal.forEach((detalle) -> {
                        pedidosOrdenCompraDetalleDao.eliminar(detalle);
                    });
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La orden de compra se ha mayorizado correctamente.");
                resp.setExtraInfo(respues.getInvPedidosOrdenCompraPK());
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Ocurrió un error al mayorizar orden de compra");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaInvPedidosOrdenCompraTO")
    public RespuestaWebTO listarInvPedidosOrdenCompraTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        Integer nroRegistros = UtilsJSON.jsonToObjeto(Integer.class, parametros.get("nroRegistros"));
        Boolean incluirAnulados = UtilsJSON.jsonToObjeto(Boolean.class, parametros.get("incluirAnulados"));
        try {
            String usuario = sisInfoTO != null ? sisInfoTO.getUsuario() : null;
            List<InvPedidosOrdenCompraTO> respues = pedidosOrdenCompraService.getListaInvPedidosOrdenCompraTO(empresa, motivo, fechaInicio, fechaFin, incluirAnulados, busqueda, nroRegistros, usuario);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de órdenes de compra");
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

    @RequestMapping("/obtenerDatosParaReporteEntrega")
    public RespuestaWebTO obtenerDatosParaReporteEntrega(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            Map<String, Object> respues = pedidosOrdenCompraService.obtenerDatosParaReporteEntrega(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron parámetros.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarReporteEntrega")
    public RespuestaWebTO listarReporteEntrega(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String grupo = UtilsJSON.jsonToObjeto(String.class, parametros.get("grupo"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String producto = UtilsJSON.jsonToObjeto(String.class, parametros.get("producto"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        try {
            List<InvPedidosReporteEntregaTO> respues = pedidosOrdenCompraService.listarReporteEntrega(empresa, motivo, sector, producto, cliente, proveedor, grupo, fechaInicio, fechaFin);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
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

    @RequestMapping("/exportarReporteEntrega")
    public @ResponseBody
    String exportarReporteEntrega(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosReporteEntregaTO> datos = UtilsJSON.jsonToList(InvPedidosReporteEntregaTO.class, map.get("datos"));
        String titulo = "Reporte de entrega";
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.exportarReporteEntrega(usuarioEmpresaReporteTO, titulo, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/listarOrdenCompraParaNotificaciones")
    public RespuestaWebTO listarOrdenCompraParaNotificaciones(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        Integer nroRegistros = UtilsJSON.jsonToObjeto(Integer.class, parametros.get("nroRegistros"));
        try {
            List<InvPedidosOrdenCompraTO> respues = pedidosOrdenCompraService.listarOrdenCompraParaNotificaciones(empresa, motivo, fechaInicio, fechaFin, busqueda, nroRegistros);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de órdenes de compra");
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

    @RequestMapping("/listarNotificacionesPorOrdenDeCompra")
    public RespuestaWebTO listarNotificacionesPorOrdenDeCompra(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        try {
            List<InvPedidosOrdenCompraNotificaciones> respues = pedidosOrdenCompraService.listarNotificacionesPorOrdenDeCompra(empresa, motivo, sector, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron notificaciones de órdenes de compra");
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

    @RequestMapping("/obtenerInvPedidosOrdenCompra")
    public RespuestaWebTO obtenerInvPedidosOrdenCompra(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, parametros.get("invPedidosOrdenCompraPK"));
        boolean esParaMayorizar = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("esParaMayorizar"));
        boolean aprobar = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("aprobar"));
        try {
            Map<String, Object> respues = pedidosOrdenCompraService.obtenerInvPedidosOrdenCompra(invPedidosOrdenCompraPK, esParaMayorizar, aprobar, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró la orden de compra");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarInvPedidosOrdenCompra")
    public RespuestaWebTO listarInvPedidosOrdenCompra(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        Integer detSecuencial = UtilsJSON.jsonToObjeto(Integer.class, parametros.get("detSecuencial"));
        try {
            Map<String, Object> respues = pedidosOrdenCompraService.listarInvPedidosOrdenCompra(detSecuencial, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró la orden de compra");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularInvPedidosOrdenCompra")
    public RespuestaWebTO anularInvPedidosOrdenCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        String motivoAnulacion = SistemaWebUtil.obtenerFiltroComoString(map, "motivoAnulacion");
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = pedidosOrdenCompraService.anularInvPedidosOrdenCompra(invPedidosOrdenCompraPK, motivoAnulacion, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La orden de compra N. " + invPedidosOrdenCompraPK.getOcNumero() + ", se ha anulado correctamente.");
            } else {
                resp.setOperacionMensaje("No se anuló la orden de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/aprobarInvPedidosOrdenCompra")
    public RespuestaWebTO aprobarInvPedidosOrdenCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = pedidosOrdenCompraService.aprobarInvPedidosOrdenCompra(invPedidosOrdenCompraPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La orden de compra N. " + invPedidosOrdenCompraPK.getOcNumero() + ", se ha aprobado correctamente.");
            } else {
                resp.setOperacionMensaje("No se aprobó la orden de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desaprobarInvPedidosOrdenCompra")
    public RespuestaWebTO desaprobarInvPedidosOrdenCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = pedidosOrdenCompraService.desaprobarInvPedidosOrdenCompra(invPedidosOrdenCompraPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La orden de compra N. " + invPedidosOrdenCompraPK.getOcNumero() + ", se ha desaprobado correctamente.");
            } else {
                resp.setOperacionMensaje("No se desaprobó la orden de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/cerrarInvPedidosOrdenCompra")
    public RespuestaWebTO cerrarInvPedidosOrdenCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = pedidosOrdenCompraService.cerrarInvPedidosOrdenCompra(invPedidosOrdenCompraPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La orden de compra N. " + invPedidosOrdenCompraPK.getOcNumero() + ", se ha cerrado correctamente.");
            } else {
                resp.setOperacionMensaje("No se cerró la orden de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarInvPedidosOrdenCompra")
    public RespuestaWebTO desmayorizarInvPedidosOrdenCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        boolean validarEjecutores = UtilsJSON.jsonToObjeto(boolean.class, map.get("validarEjecutores"));
        String motivoDesmayorizar = SistemaWebUtil.obtenerFiltroComoString(map, "motivo");

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = pedidosOrdenCompraService.desmayorizarInvPedidosOrdenCompra(invPedidosOrdenCompraPK, usuario, validarEjecutores, motivoDesmayorizar, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("La orden de compra N. " + invPedidosOrdenCompraPK.getOcNumero() + ", se ha demayorizado correctamente.");
            } else {
                resp.setOperacionMensaje("No se demayorizó la orden de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerParametrosParaMayorizar")
    public RespuestaWebTO obtenerParametrosParaMayorizar(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = pedidosOrdenCompraService.obtenerParametrosParaMayorizar(invPedidosOrdenCompraPK, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron parámetros.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Reporte de ordenes de compra*/
    @RequestMapping("/generarReporteInvPedidosOrdenCompraTO")
    public @ResponseBody
    String generarReporteInvPedidosOrdenCompraTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosOrdenCompraTO> listaInvPedidosOrdenCompraTO = UtilsJSON.jsonToList(InvPedidosOrdenCompraTO.class, map.get("listaInvPedidosOrdenCompraTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteInvPedidosOrdenCompraTO(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraTO, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvPedidosOrdenCompraLote")
    public @ResponseBody
    String generarReporteInvPedidosOrdenCompraLote(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));

        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosOrdenCompraPK> listaInvPedidosOrdenCompraPK = UtilsJSON.jsonToList(InvPedidosOrdenCompraPK.class, map.get("listaInvPedidosOrdenCompraPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvPedidosOrdenCompraDetalleTO> listaInvPedidosOrdenCompraDetalleTO = new ArrayList<>();
            for (InvPedidosOrdenCompraPK invPedidosOrdenCompraPK : listaInvPedidosOrdenCompraPK) {
                InvPedidosOrdenCompra invPedidosOrdenCompra = pedidosOrdenCompraService.getInvPedidosOrdenCompra(invPedidosOrdenCompraPK);
                InvPedidosOrdenCompraDetalleTO invPedidosOrdenCompraDetalleTO = new InvPedidosOrdenCompraDetalleTO();
                invPedidosOrdenCompraDetalleTO.setInvPedidosOrdenCompra(invPedidosOrdenCompra);
                listaInvPedidosOrdenCompraDetalleTO.add(invPedidosOrdenCompraDetalleTO);
            }
            if (listaInvPedidosOrdenCompraDetalleTO.size() > 0) {
                respuesta = reportePedidosService.generarReporteInvPedidosOrdenCompraPorLote(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraDetalleTO, nombreReporte);
                return archivoService.generarReportePDF(respuesta, nombreReporte, response);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvPedidosOrdenCompra")
    public @ResponseBody
    String generarReporteInvPedidosOrdenCompra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosOrdenCompra invPedidosOrdenCompra = pedidosOrdenCompraService.getInvPedidosOrdenCompra(invPedidosOrdenCompraPK);
            if (invPedidosOrdenCompra != null && invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList() != null) {
                respuesta = reportePedidosService.generarReporteInvPedidosOrdenCompra(usuarioEmpresaReporteTO, invPedidosOrdenCompra, nombreReporte);
                return archivoService.generarReportePDF(respuesta, nombreReporte, response);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvPedidosOrdenCompraTO")
    public @ResponseBody
    String exportarReporteInvPedidosOrdenCompraTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosOrdenCompraTO> listaInvPedidosOrdenCompraTO = UtilsJSON.jsonToList(InvPedidosOrdenCompraTO.class, map.get("listaInvPedidosOrdenCompraTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.exportarReporteInvPedidosOrdenCompraTO(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvPedidosOrdenCompra")
    public @ResponseBody
    String exportarReporteInvPedidosOrdenCompra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosOrdenCompra invPedidosOrdenCompra = pedidosOrdenCompraService.getInvPedidosOrdenCompra(invPedidosOrdenCompraPK);
            if (invPedidosOrdenCompra != null && invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList() != null) {
                respuesta = reportePedidosService.exportarReporteInvPedidosOrdenCompra(usuarioEmpresaReporteTO, invPedidosOrdenCompra);
                List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
                List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
                archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarPDFOrdenCompra")
    public RespuestaWebTO enviarPDFOrdenCompra(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportOrdenCompraAprobada.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        Integer idNotificacionEntero = UtilsJSON.jsonToObjeto(Integer.class, map.get("idNotificacionEntero"));
        String emailProveedor = UtilsJSON.jsonToObjeto(String.class, map.get("emailProveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String mensaje = pedidosOrdenCompraService.enviarPdfOrdenCompra(sisInfoTO.getEmpresa(), nombreReporte, invPedidosOrdenCompraPK, usuarioEmpresaReporteTO, emailProveedor, idNotificacionEntero, response);
            if (mensaje.equals("Email sent!")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La notificación al proveedor se ha enviado correctamente.");
                resp.setExtraInfo("Documento enviado exitosamente.");
            } else {
                resp.setOperacionMensaje(mensaje);
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

    @RequestMapping("/obtenerDatosParaConfiguracionDePedidos")
    public RespuestaWebTO obtenerDatosParaConfiguracionDePedidos(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        try {
            Map<String, Object> respues = pedidosConfiguracionService.obtenerDatosParaConfiguracionDePedidos(empresa, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró datos para configurar pedidos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaConfiguracionDeOrdenCompra")
    public RespuestaWebTO obtenerDatosParaConfiguracionDeOrdenCompra(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        try {
            Map<String, Object> respues = compraConfiguracionService.obtenerDatosParaConfiguracionDeOrdenCompra(empresa, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró datos para configurar orden de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getOrdenComprasImportadasEnCompras")
    public RespuestaWebTO getOrdenComprasImportadasEnCompras(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK pk = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvCompras> lista = pedidosOrdenCompraService.getOrdenComprasImportadasEnCompras(pk);
            if (lista != null && lista.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(lista);
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

    @RequestMapping("/generarReporteNotificacionesOrdenCompra")
    public @ResponseBody
    String generarReporteNotificacionesOrdenCompra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteNotificacionesOrdenCompra(usuarioEmpresaReporteTO, map);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //matricial
    @RequestMapping("/generarReporteInvPedidosOrdenCompraMotivoMatricial")
    public RespuestaWebTO generarReporteInvPedidosOrdenCompraMotivoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportPedidosOrdenCompraMotivo.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvPedidosOrdenCompraMotivoTO> listaInvPedidosOrdenCompraMotivoTO = UtilsJSON.jsonToList(InvPedidosOrdenCompraMotivoTO.class, parametros.get("listaInvPedidosOrdenCompraMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteInvPedidosOrdenCompraMotivo(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraMotivoTO, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReportePedidosMotivoMatricial")
    public RespuestaWebTO generarReportePedidosMotivoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportPedidosMotivo.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvPedidosMotivoTO> listaInvPedidosMotivo = UtilsJSON.jsonToList(InvPedidosMotivoTO.class, parametros.get("listadoInvPedidosMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteInvPedidosMotivo(usuarioEmpresaReporteTO, listaInvPedidosMotivo, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteInvPedidosGeneralMatricial")
    public RespuestaWebTO generarReporteInvPedidosGeneralMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosPK invPedidosPK = UtilsJSON.jsonToObjeto(InvPedidosPK.class, map.get("invPedidosPK"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        try {
            InvPedidoTO invPedidosTO = invPedidosService.obtenerInvPedidosTO(invPedidosPK);
            InvPedidos invPedidos = invPedidosService.obtenerInvPedidos(invPedidosPK);
            if (invPedidosTO != null && invPedidos != null) {
                respuesta = reportePedidosService.generarReporteInvPedidosGeneral(usuarioEmpresaReporteTO, invPedidos, invPedidosTO, nombreReporte);
                resp.setExtraInfo(respuesta);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteInvPedidosTOMatricial")
    public RespuestaWebTO generarReporteInvPedidosTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidoTO> listaInvPedidoTO = UtilsJSON.jsonToList(InvPedidoTO.class, map.get("listadoInvPedidosTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteInvPedidosTO(usuarioEmpresaReporteTO, listaInvPedidoTO, codigoSector, codigoMotivo, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteInvPedidosPorLoteMatricial")
    public RespuestaWebTO generarReporteInvPedidosPorLoteMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosPK> listaInvPedidosPK = UtilsJSON.jsonToList(InvPedidosPK.class, map.get("listaInvPedidosPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteInvPedidosPorLote(usuarioEmpresaReporteTO, listaInvPedidosPK, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteInvPedidosOrdenCompraPorLoteMatricial")
    public RespuestaWebTO generarReporteInvPedidosOrdenCompraPorLoteMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();

        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosOrdenCompraPK> listaInvPedidosOrdenCompraPK = UtilsJSON.jsonToList(InvPedidosOrdenCompraPK.class, map.get("listaInvPedidosOrdenCompraPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvPedidosOrdenCompraDetalleTO> listaInvPedidosOrdenCompraDetalleTO = new ArrayList<>();
            for (InvPedidosOrdenCompraPK invPedidosOrdenCompraPK : listaInvPedidosOrdenCompraPK) {
                InvPedidosOrdenCompra invPedidosOrdenCompra = pedidosOrdenCompraService.getInvPedidosOrdenCompra(invPedidosOrdenCompraPK);
                InvPedidosOrdenCompraDetalleTO invPedidosOrdenCompraDetalleTO = new InvPedidosOrdenCompraDetalleTO();
                invPedidosOrdenCompraDetalleTO.setInvPedidosOrdenCompra(invPedidosOrdenCompra);
                listaInvPedidosOrdenCompraDetalleTO.add(invPedidosOrdenCompraDetalleTO);
            }
            if (listaInvPedidosOrdenCompraDetalleTO.size() > 0) {
                respuesta = reportePedidosService.generarReporteInvPedidosOrdenCompraPorLote(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraDetalleTO, nombreReporte);
                resp.setExtraInfo(respuesta);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteInvPedidosOrdenCompraTOMatricial")
    public RespuestaWebTO generarReporteInvPedidosOrdenCompraTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvPedidosOrdenCompraTO> listaInvPedidosOrdenCompraTO = UtilsJSON.jsonToList(InvPedidosOrdenCompraTO.class, map.get("listaInvPedidosOrdenCompraTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteInvPedidosOrdenCompraTO(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraTO, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteInvPedidosOrdenCompraMatricial")
    public RespuestaWebTO generarReporteInvPedidosOrdenCompraMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosOrdenCompra invPedidosOrdenCompra = pedidosOrdenCompraService.getInvPedidosOrdenCompra(invPedidosOrdenCompraPK);
            if (invPedidosOrdenCompra != null && invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList() != null) {
                respuesta = reportePedidosService.generarReporteInvPedidosOrdenCompra(usuarioEmpresaReporteTO, invPedidosOrdenCompra, nombreReporte);
                resp.setExtraInfo(respuesta);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteNotificacionesOrdenCompraMatricial")
    public RespuestaWebTO generarReporteNotificacionesOrdenCompraMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reportePedidosService.generarReporteNotificacionesOrdenCompra(usuarioEmpresaReporteTO, map);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaReporteComprasImb")
    public RespuestaWebTO obtenerDatosParaReporteComprasImb(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        try {
            Map<String, Object> respues = pedidosOrdenCompraService.obtenerDatosParaReporteComprasImb(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron parámetros.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarReporteComprasImb")
    public RespuestaWebTO listarReporteComprasImb(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String periodo = SistemaWebUtil.obtenerFiltroComoString(parametros, "periodo");
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        String producto = UtilsJSON.jsonToObjeto(String.class, parametros.get("producto"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        try {
            List<InvPedidosReporteComprasImbTO> respues = pedidosOrdenCompraService.listarReporteComprasImb(empresa, periodo, motivo, producto, proveedor, fechaInicio, fechaFin);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
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

    @RequestMapping("/cambiarDatosProductoOC")
    public RespuestaWebTO cambiarDatosProductoOC(HttpServletResponse response, @RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosOrdenCompraPK invPedidosOrdenCompraPK = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraPK.class, map.get("invPedidosOrdenCompraPK"));
        Integer detSecuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("detSecuencial"));
        InvProductoPK productoNuevoPk = UtilsJSON.jsonToObjeto(InvProductoPK.class, map.get("productoNuevoPk"));
        BigDecimal productoNuevoPrecio = UtilsJSON.jsonToObjeto(BigDecimal.class, map.get("productoNuevoPrecio"));
        BigDecimal productoNuevoCantidad = UtilsJSON.jsonToObjeto(BigDecimal.class, map.get("productoNuevoCantidad"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvPedidosOrdenCompra respuesta = pedidosOrdenCompraService.cambiarDatosProductoOC(invPedidosOrdenCompraPK, detSecuencial,
                    productoNuevoPk, productoNuevoPrecio, productoNuevoCantidad, sisInfoTO);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al cambiar de valores al ítem de Orden de compra");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

}
