/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.cartera.controller;

import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequePostfechadoService;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.report.ReporteCarteraService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.CobrosAnticiposService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.CobrosFormaService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.CobrosService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.PagosAnticiposService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.PagosFormaService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.PagosService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteService;
import ec.com.todocompu.ShrimpSoftServer.util.RespuestaMessageTO;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarContableTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorCobrarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarDetalladoGranjasMarinasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPagadasListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorCobrarListadoVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorPagarListadoComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaMayorAuxiliarClienteProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorPagarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticiposPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhOrdenBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/todocompuWS/carteraWebController")
public class CarteraWebController {

    @Autowired
    private PagosFormaService pagosFormaService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private ReporteCarteraService reporteCarteraService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private CobrosFormaService cobrosFormaService;
    @Autowired
    private PagosAnticiposService pagosAnticiposService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private CobrosService cobrosService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PagosService pagosService;
    @Autowired
    private CobrosAnticiposService cobrosAnticiposService;
    @Autowired
    private ChequePostfechadoService chequePostfechadoService;
    @Autowired
    private CobrosAnticiposDao cobrosAnticiposDao;

    /*Forma de pago*/
    @RequestMapping("/getCarListaPagosCobrosFormaTO")
    public RespuestaWebTO getCarListaPagosCobrosFormaTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//P o C
        boolean inactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarPagosCobrosFormaTO> respues = pagosFormaService.getListaPagosCobrosFormaTO(empresa, accion, inactivos);
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

    @RequestMapping("/accionCarPagosForma")
    public RespuestaWebTO accionCarPagosForma(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosCobrosFormaTO carPagosCobrosFormaTO = UtilsJSON.jsonToObjeto(CarPagosCobrosFormaTO.class, map.get("carPagosCobrosFormaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//I,M o E
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = pagosFormaService.accionCarPagosForma(carPagosCobrosFormaTO, accion, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(accion == 'I' ? pagosFormaService.getCarPagosCobrosFormaTO(carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getCtaCodigo(), carPagosCobrosFormaTO.getSecCodigo()) : true);
                resp.setOperacionMensaje(respues.substring(1));
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

    @RequestMapping("/modificarEstadoCarPagosForma")
    public RespuestaWebTO modificarEstadoCarPagosForma(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        CarPagosCobrosFormaTO carPagosCobrosFormaTO = UtilsJSON.jsonToObjeto(CarPagosCobrosFormaTO.class, map.get("carPagosCobrosFormaTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = pagosFormaService.modificarEstadoCarPagosForma(carPagosCobrosFormaTO, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarReporteFormaCobroPago")
    public @ResponseBody
    String generarReporteCarReporteFormaPago(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte;
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<CarPagosCobrosFormaTO> listado = UtilsJSON.jsonToList(CarPagosCobrosFormaTO.class, parametros.get("listado"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));//P o C
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        nombreReporte = tipo.equalsIgnoreCase("P") ? "reportCarFormaPago.jrxml" : "reportCarFormaCobro.jrxml";
        try {
            respuesta = reporteCarteraService.generarReporteCarReporteFormaPago(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarReporteFormaCobroPago")
    public @ResponseBody
    String exportarReporteCarReporteFormaPago(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<CarPagosCobrosFormaTO> listado = UtilsJSON.jsonToList(CarPagosCobrosFormaTO.class, map.get("listadoCarPagosForma"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.exportarReporteCarReporteFormaPago(usuarioEmpresaReporteTO, tipo, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //Forma cobro
    @RequestMapping("/accionCarCobrosForma")
    public RespuestaWebTO accionCarCobrosForma(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosCobrosFormaTO carPagosCobrosFormaTO = UtilsJSON.jsonToObjeto(CarPagosCobrosFormaTO.class, map.get("carPagosCobrosFormaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//I,M o E
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = cobrosFormaService.accionCarCobrosForma(carPagosCobrosFormaTO, accion, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(accion == 'I' ? cobrosFormaService.getCarPagosCobrosFormaTO(carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getCtaCodigo(), carPagosCobrosFormaTO.getSecCodigo()) : true);
                resp.setOperacionMensaje(respues.substring(1));
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

    @RequestMapping("/modificarEstadoCarCobroForma")
    public RespuestaWebTO modificarEstadoCarCobroForma(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        CarPagosCobrosFormaTO carPagosCobrosFormaTO = UtilsJSON.jsonToObjeto(CarPagosCobrosFormaTO.class, map.get("carPagosCobrosFormaTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = cobrosFormaService.modificarEstadoCarCobroForma(carPagosCobrosFormaTO, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*ANTICIPOS PROVEEDOR*/
    @RequestMapping("/insertarAnticiposPago")
    public RespuestaWebTO insertarAnticiposPago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosAnticipoTO carPagosAnticipoTO = UtilsJSON.jsonToObjeto(CarPagosAnticipoTO.class, map.get("carPagosAnticipoTO"));
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String nombreProveedor = UtilsJSON.jsonToObjeto(String.class, map.get("nombreProveedor"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String sectorProveedor = UtilsJSON.jsonToObjeto(String.class, map.get("sectorProveedor"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = pagosAnticiposService.insertarAnticiposPago(carPagosAnticipoTO, observaciones, nombreProveedor, fecha, sectorProveedor, documento, listadoImagenes, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                resp.setExtraInfo(conContable);
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null
                        ? mensajeTO.getMensaje().substring(1) : "No se ha insertado anticipo a proveedor.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/mayorizarAnticiposPago")
    public RespuestaWebTO mayorizarAnticiposPago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosAnticipoTO carPagosAnticipoTO = UtilsJSON.jsonToObjeto(CarPagosAnticipoTO.class, map.get("carPagosAnticipoTO"));
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String nombreProveedor = UtilsJSON.jsonToObjeto(String.class, map.get("nombreProveedor"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String sectorProveedor = UtilsJSON.jsonToObjeto(String.class, map.get("sectorProveedor"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = pagosAnticiposService.mayorizarAnticiposPago(carPagosAnticipoTO, observaciones, nombreProveedor, fecha, sectorProveedor, documento, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                try {
                    if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
                        contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                    }
                } catch (Exception e) {
                }
                resp.setExtraInfo(conContable);
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null
                        ? mensajeTO.getMensaje().substring(1) : "No se ha mayorizado anticipo a proveedor.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*ANTICIPOS CLIENTE*/
    @RequestMapping("/insertarAnticiposCobro")
    public RespuestaWebTO insertarAnticiposCobro(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarCobrosAnticipoTO carCobrosAnticipoTO = UtilsJSON.jsonToObjeto(CarCobrosAnticipoTO.class, map.get("carCobrosAnticipoTO"));
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String nombreCliente = UtilsJSON.jsonToObjeto(String.class, map.get("nombreCliente"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String sectorCliente = UtilsJSON.jsonToObjeto(String.class, map.get("sectorCliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = cobrosAnticiposService.insertarAnticiposCobro(carCobrosAnticipoTO, observaciones, nombreCliente, fecha, sectorCliente, documento, listadoImagenes, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                resp.setExtraInfo(conContable);
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null
                        ? mensajeTO.getMensaje().substring(1) : "No se ha insertado anticipo a cliente.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/mayorizarAnticiposCobro")
    public RespuestaWebTO mayorizarAnticiposCobro(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarCobrosAnticipoTO carCobrosAnticipoTO = UtilsJSON.jsonToObjeto(CarCobrosAnticipoTO.class, map.get("carCobrosAnticipoTO"));
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String nombreCliente = UtilsJSON.jsonToObjeto(String.class, map.get("nombreCliente"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String sectorCliente = UtilsJSON.jsonToObjeto(String.class, map.get("sectorCliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = cobrosAnticiposService.mayorizarAnticiposCobro(carCobrosAnticipoTO, observaciones, nombreCliente, fecha, sectorCliente, documento, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                try {
                    if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
                        contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                    }
                } catch (Exception e) {
                }
                resp.setExtraInfo(conContable);
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null
                        ? mensajeTO.getMensaje().substring(1) : "No se ha mayorizado anticipo a cliente.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //CONSULTAS
    @RequestMapping("/getCarFunPagosTO")
    public RespuestaWebTO getCarFunPagosTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunPagosTO> respuesta = pagosService.getCarFunPagosTO(empresa, sector, desde, hasta, proveedor, incluirTodos);
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

    @RequestMapping("/getCarFunPagosDetalleTO")
    public RespuestaWebTO getCarFunPagosDetalleTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunPagosDetalleTO> respuesta = pagosService.getCarFunPagosDetalleTO(empresa, sector, desde, hasta, proveedor, formaPago);
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

    @RequestMapping("/getCarFunCuentasPorPagarListadoComprasTO")
    public RespuestaWebTO getCarFunCuentasPorPagarListadoComprasTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunCuentasPorPagarListadoComprasTO> respuesta = pagosService.getCarFunCuentasPorPagarListadoComprasTO(empresa, sector, proveedor, desde, hasta);
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

    @RequestMapping("/listarCuentasPagadas")
    public RespuestaWebTO listarCuentasPagadas(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean aprobadas = UtilsJSON.jsonToObjeto(boolean.class, map.get("aprobadas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunCuentasPagadasListadoTO> respuesta = pagosService.listarCuentasPagadas(empresa, sector, proveedor, desde, hasta, aprobadas);
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

    @RequestMapping("/getCarListaMayorAuxiliarClienteProveedorTO")
    public RespuestaWebTO getCarListaMayorAuxiliarClienteProveedorTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        boolean anticipos = UtilsJSON.jsonToObjeto(boolean.class, map.get("anticipos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarListaMayorAuxiliarClienteProveedorTO> respuesta = cobrosService.getCarListaMayorAuxiliarClienteProveedorTO(empresa, sector, proveedor, desde, hasta, accion, anticipos);
            if (respuesta != null && !respuesta.isEmpty() && respuesta.size() > 2) {
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

    @RequestMapping("/getCarListaCuentasPorPagarDetalladoTO")
    public RespuestaWebTO getCarListaCuentasPorPagarDetalladoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean excluirAprobadas = UtilsJSON.jsonToObjeto(boolean.class, map.get("excluirAprobadas"));
        boolean incluirCheques = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirCheques"));
        String formatoMensual = UtilsJSON.jsonToObjeto(String.class, map.get("formatoMensual"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CuentasPorPagarDetalladoTO> respuesta = pagosService.getCarListaCuentasPorPagarDetalladoTO(empresa, sector, proveedor, hasta, excluirAprobadas, incluirCheques, formatoMensual);
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

    @RequestMapping("/getCarListaCuentasPorPagarGeneralTO")
    public RespuestaWebTO getCarListaCuentasPorPagarGeneralTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarCobrarGeneralTO> respuesta = pagosService.getCarListaCuentasPorPagarGeneralTO(empresa, sector, hasta);
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

    @RequestMapping("/getCarListaCuentasPorPagarSaldoAnticiposTO")
    public RespuestaWebTO getCarListaCuentasPorPagarSaldoAnticiposTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedorCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("proveedorCodigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarSaldoAnticiposTO> respuesta = pagosAnticiposService.getCarListaCuentasPorPagarSaldoAnticiposTO(empresa, sector, proveedorCodigo,
                    hasta, incluirTodos);
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

    @RequestMapping("/getCarFunCobrosTO")
    public RespuestaWebTO getCarFunCobrosTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunCobrosTO> respuesta = cobrosService.getCarFunCobrosTO(empresa, sector, desde, hasta, cliente, incluirTodos);
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

    @RequestMapping("/getCarFunCobrosDetalleTO")
    public RespuestaWebTO getCarFunCobrosDetalleTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunCobrosDetalleTO> respuesta = cobrosService.getCarFunCobrosDetalleTO(empresa, sector, desde, hasta, cliente, formaPago);
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

    @RequestMapping("/getCarListaCuentasPorCobrarSaldoAnticiposTO")
    public RespuestaWebTO getCarListaCuentasPorCobrarSaldoAnticiposTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String clienteCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("clienteCodigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorCobrarSaldoAnticiposTO> respuesta = cobrosAnticiposService.getCarListaCuentasPorCobrarSaldoAnticiposTO(empresa, sector, clienteCodigo, hasta, incluirTodos);
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

    @RequestMapping("/getCarFunCuentasPorCobrarListadoVentasTO")
    public RespuestaWebTO getCarFunCuentasPorCobrarListadoVentasTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunCuentasPorCobrarListadoVentasTO> respuesta = cobrosService.getCarFunCuentasPorCobrarListadoVentasTO(empresa, sector, cliente, desde, hasta);
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

    @RequestMapping("/getCarListaCuentasPorCobrarDetalladoTO")
    public RespuestaWebTO getCarListaCuentasPorCobrarDetalladoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
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
            List<CuentasPorCobrarDetalladoTO> respuesta = cobrosService.getCarListaCuentasPorCobrarDetalladoTO(empresa, sector, cliente, desde, hasta, grupo, ichfa);
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

    @RequestMapping("/getCarListaCuentasPorCobrarGeneralTO")
    public RespuestaWebTO getCarListaCuentasPorCobrarGeneralTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarCobrarGeneralTO> respuesta = cobrosService.getCarListaCuentasPorCobrarGeneralTO(empresa, sector, hasta);
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

    @RequestMapping("/getCarListaCuentasPorCobrarAnticiposTO")
    public RespuestaWebTO getCarListaCuentasPorCobrarAnticiposTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarCobrarAnticiposTO> respuesta = cobrosService.getCarListaCuentasPorCobrarAnticiposTO(empresa, sector, hasta);
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

    @RequestMapping("/getCarListaCuentasPorPagarAnticiposTO")
    public RespuestaWebTO getCarListaCuentasPorPagarAnticiposTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarCobrarAnticiposTO> respuesta = pagosService.getCarListaCuentasPorPagarAnticiposTO(empresa, sector, hasta);
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

    @RequestMapping("/generarReporteCarFunCuentasPorCobrarListadoVentas")
    public @ResponseBody
    String generarReporteCarFunCuentasPorCobrarListadoVentas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCarFunCuentasPorCobrarListadoVentas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarFunCuentasPorCobrarListadoVentasTO> listado = UtilsJSON.jsonToList(CarFunCuentasPorCobrarListadoVentasTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarFunCuentasPorCobrarListadoVentas(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaDesde, fechaHasta);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerDatosParaCrudAnticipos")
    public RespuestaWebTO obtenerDatosParaCrudAnticipos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = pagosAnticiposService.obtenerDatosParaCrudAnticipos(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener datos para anticipos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerAnticipoPago")
    public RespuestaWebTO obtenerAnticipoPago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = pagosAnticiposService.obtenerAnticipoPago(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener anticipo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerAnticipoCobro")
    public RespuestaWebTO obtenerAnticipoCobro(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = cobrosAnticiposService.obtenerAnticipoCobro(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener anticipo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarFunCobrosDetalle")
    public @ResponseBody
    String generarReporteCarFunCobrosDetalle(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoCobro.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarFunCobrosDetalleTO> listado = UtilsJSON.jsonToList(CarFunCobrosDetalleTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarFunCobrosDetalle(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaDesde, fechaHasta);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarListaMayorAuxiliarClienteProveedor")
    public @ResponseBody
    String generarReporteCarListaMayorAuxiliarClienteProveedor(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMayorAuxiliarProveedorCliente.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        List<CarListaMayorAuxiliarClienteProveedorTO> listado = UtilsJSON.jsonToList(CarListaMayorAuxiliarClienteProveedorTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarListaMayorAuxiliarClienteProveedor(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaDesde, fechaHasta, tipo);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorCobrarDetallado")
    public @ResponseBody
    String generarReporteCarListaCuentasPorCobrarDetallado(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCuentasPorCobrarDetallado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CuentasPorCobrarDetalladoTO> listado = UtilsJSON.jsonToList(CuentasPorCobrarDetalladoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarListaCuentasPorCobrarDetallado(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorCobrarGeneral")
    public @ResponseBody
    String generarReporteCarListaCuentasPorCobrarGeneral(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCuentasPorCobrarGeneral.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarCuentasPorPagarCobrarGeneralTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarGeneralTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarListaCuentasPorCobrarGeneral(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPorPagarAnticipos")
    public @ResponseBody
    String generarReporteCuentasPorPagarAnticipos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCuentasPorPagarAnticipos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarCuentasPorCobrarSaldoAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorCobrarSaldoAnticiposTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCuentasPorPagarAnticipos(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaHasta);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPorCobrarAnticipos")
    public @ResponseBody
    String generarReporteCuentasPorCobrarAnticipos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCuentasPorCobrarAnticipos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        boolean verDetalle = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("verDetalle"));
        List<CarCuentasPorPagarCobrarAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarAnticiposTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCuentasPorCobrarAnticipos(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta, verDetalle);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarFunPagos")
    public @ResponseBody
    String generarReporteCarFunPagos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoPago.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarFunPagosTO> listado = UtilsJSON.jsonToList(CarFunPagosTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarFunPagos(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarFunPagosDetalle")
    public @ResponseBody
    String generarReporteCarFunPagosDetalle(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoPago.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        boolean esForma = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("esForma"));
        String formaPagoSeleccionada = UtilsJSON.jsonToObjeto(String.class, parametros.get("formaPagoSeleccionada"));
        List<CarFunPagosDetalleTO> listado = UtilsJSON.jsonToList(CarFunPagosDetalleTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            if (esForma) {
                nombreReporte = "reportListadoPagoForma.jrxml";
            }
            respuesta = reporteCarteraService.generarReporteCarFunPagosDetalle(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor, formaPagoSeleccionada);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarFunCuentasPorPagarListadoCompras")
    public @ResponseBody
    String generarReporteCarFunCuentasPorPagarListadoCompras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCarFunCuentasPorPagarListadoCompras.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarFunCuentasPorPagarListadoComprasTO> listado = UtilsJSON.jsonToList(CarFunCuentasPorPagarListadoComprasTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarFunCuentasPorPagarListadoCompras(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPagadas")
    public @ResponseBody
    String generarReporteCuentasPagadas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCarCuentasPagadasCompras.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarFunCuentasPagadasListadoTO> listado = UtilsJSON.jsonToList(CarFunCuentasPagadasListadoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCuentasPagadas(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorPagarDetallado")
    public @ResponseBody
    String generarReporteCarListaCuentasPorPagarDetallado(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCuentasPorPagarDetallado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CuentasPorPagarDetalladoTO> listado = UtilsJSON.jsonToList(CuentasPorPagarDetalladoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarListaCuentasPorPagarDetallado(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta, proveedor);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorPagarGeneral")
    public @ResponseBody
    String generarReporteCarListaCuentasPorPagarGeneral(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCuentasPorPagarGeneral.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarCuentasPorPagarCobrarGeneralTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarGeneralTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarListaCuentasPorPagarGeneral(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta, proveedor);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorPagarSaldoAnticipos")
    public @ResponseBody
    String generarReporteCarListaCuentasPorPagarSaldoAnticipos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCuentasPorPagarSaldoAnticipos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarCuentasPorPagarSaldoAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarSaldoAnticiposTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarListaCuentasPorPagarSaldoAnticipos(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarCuentasPorPagarCobrarAnticipos")
    public @ResponseBody
    String generarReporteCarCuentasPorPagarCobrarAnticipos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCuentasPorPagarAnticipos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarCuentasPorPagarCobrarAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarAnticiposTO.class, parametros.get("listado"));
        boolean verDetalle = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("verDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarCuentasPorPagarCobrarAnticipos(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta, verDetalle);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePorLoteCobros")
    public @ResponseBody
    String generarReportePorLoteCobros(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "reportCobrosPorLote.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        nombreReporte = nombre != null && !nombre.equals("") ? nombre : nombreReporte;
        List<String> numeroSistema = UtilsJSON.jsonToList(String.class, map.get("numeroSistema"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReportePorLoteCobros(usuarioEmpresaReporteTO, nombreReporte, numeroSistema, empresa);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePorLotePagos")
    public @ResponseBody
    String generarReportePorLotePagos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "reportPagosPorLote.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        nombreReporte = nombre != null && !nombre.equals("") ? nombre : nombreReporte;
        List<String> numeroSistema = UtilsJSON.jsonToList(String.class, map.get("numeroSistema"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReportePorPagos(usuarioEmpresaReporteTO, nombreReporte, numeroSistema, empresa);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarFunCobrosDetalle")
    public @ResponseBody
    String exportarReporteCarFunCobrosDetalle(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<CarFunCobrosDetalleTO> listado = UtilsJSON.jsonToList(CarFunCobrosDetalleTO.class, map.get("listado"));
        boolean esForma = UtilsJSON.jsonToObjeto(boolean.class, map.get("esForma"));
        String formaPagoSeleccionada = UtilsJSON.jsonToObjeto(String.class, map.get("formaPagoSeleccionada"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.exportarReporteCarFunCobrosDetalleForma(usuarioEmpresaReporteTO, cliente, listado, sector, fechaDesde, fechaHasta, formaPagoSeleccionada);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarFunCobros")
    public @ResponseBody
    String exportarReporteCarFunCobros(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<CarFunCobrosTO> listado = UtilsJSON.jsonToList(CarFunCobrosTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.exportarReporteCarFunCobros(usuarioEmpresaReporteTO, cliente, listado, sector, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarFunCuentasPorCobrarListadoVentas")
    public @ResponseBody
    String exportarReporteCarFunCuentasPorCobrarListadoVentas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<CarFunCuentasPorCobrarListadoVentasTO> listado = UtilsJSON.jsonToList(CarFunCuentasPorCobrarListadoVentasTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.exportarReporteCarFunCuentasPorCobrarListadoVentas(usuarioEmpresaReporteTO, cliente, listado, sector, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarListaMayorAuxiliarClienteProveedor")
    public @ResponseBody
    String exportarReporteCarListaMayorAuxiliarClienteProveedor(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        List<CarListaMayorAuxiliarClienteProveedorTO> listado = UtilsJSON.jsonToList(CarListaMayorAuxiliarClienteProveedorTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.exportarReporteCarListaMayorAuxiliarClienteProveedor(usuarioEmpresaReporteTO, cliente, listado, sector, fechaDesde, fechaHasta, tipo);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarListaCuentasPorCobrarDetallado")
    public @ResponseBody
    String exportarReporteCarListaCuentasPorCobrarDetallado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<CuentasPorCobrarDetalladoTO> listado = UtilsJSON.jsonToList(CuentasPorCobrarDetalladoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.exportarReporteCarListaCuentasPorCobrarDetallado(usuarioEmpresaReporteTO, listado, sector, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarListaCuentasPorCobrarGeneral")
    public @ResponseBody
    String exportarReporteCarListaCuentasPorCobrarGeneral(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<CarCuentasPorPagarCobrarGeneralTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarGeneralTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.exportarReporteCarListaCuentasPorCobrarGeneral(usuarioEmpresaReporteTO, listado, sector, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCuentasPorPagarAnticipos")
    public @ResponseBody
    String exportarReporteCuentasPorPagarAnticipos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<CarCuentasPorCobrarSaldoAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorCobrarSaldoAnticiposTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.exportarReporteCuentasPorPagarAnticipos(usuarioEmpresaReporteTO, listado, sector, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCuentasPorCobrarAnticipos")
    public @ResponseBody
    String exportarReporteCuentasPorCobrarAnticipos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        boolean verDetalle = UtilsJSON.jsonToObjeto(boolean.class, map.get("verDetalle"));
        List<CarCuentasPorPagarCobrarAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarAnticiposTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.exportarReporteCuentasPorCobrarAnticipos(usuarioEmpresaReporteTO, listado, sector, fechaHasta, verDetalle);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarFunPagos")
    public @ResponseBody
    String exportarReporteCarFunPagos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        List<CarFunPagosTO> listado = UtilsJSON.jsonToList(CarFunPagosTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.exportarReporteCarFunPagos(usuarioEmpresaReporteTO, listado, sector, fechaDesde, fechaHasta, proveedor);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarFunPagosDetalle")
    public @ResponseBody
    String exportarReporteCarFunPagosDetalle(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        List<CarFunPagosDetalleTO> listado = UtilsJSON.jsonToList(CarFunPagosDetalleTO.class, map.get("listado"));
        boolean esForma = UtilsJSON.jsonToObjeto(boolean.class, map.get("esForma"));
        String formaPagoSeleccionada = UtilsJSON.jsonToObjeto(String.class, map.get("formaPagoSeleccionada"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (esForma) {
                respuesta = reporteCarteraService.exportarReporteCarFunPagosDetalleForma(usuarioEmpresaReporteTO, listado, sector, fechaDesde, fechaHasta, proveedor, formaPagoSeleccionada);
            } else {
                respuesta = reporteCarteraService.exportarReporteCarFunPagosDetalle(usuarioEmpresaReporteTO, listado, sector, fechaDesde, fechaHasta, proveedor);
            }
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCuentasPorPagarListadoCompras")
    public @ResponseBody
    String exportarReporteCuentasPorPagarListadoCompras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        List<CarFunCuentasPorPagarListadoComprasTO> listado = UtilsJSON.jsonToList(CarFunCuentasPorPagarListadoComprasTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.exportarReporteCuentasPorPagarListadoCompras(usuarioEmpresaReporteTO, listado, sector, fechaDesde, fechaHasta, proveedor);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCuentasPagadas")
    public @ResponseBody
    String exportarCuentasPagadas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        List<CarFunCuentasPagadasListadoTO> listado = UtilsJSON.jsonToList(CarFunCuentasPagadasListadoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.exportarCuentasPagadas(usuarioEmpresaReporteTO, listado, sector, fechaDesde, fechaHasta, proveedor);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarCarListaCuentasPorPagarDetallado")
    public @ResponseBody
    String exportarCarListaCuentasPorPagarDetallado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        List<CuentasPorPagarDetalladoTO> listado = UtilsJSON.jsonToList(CuentasPorPagarDetalladoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.exportarReporteCarListaCuentasPorPagarDetallado(usuarioEmpresaReporteTO, listado, sector, fechaHasta, proveedor);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarListaCuentasPorPagarGeneral")
    public @ResponseBody
    String exportarReporteCarListaCuentasPorPagarGeneral(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<CarCuentasPorPagarCobrarGeneralTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarGeneralTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.exportarReporteCarListaCuentasPorPagarGeneral(usuarioEmpresaReporteTO, listado, sector, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCarListaCuentasPorPagarSaldoAnticipos")
    public @ResponseBody
    String exportarReporteCarListaCuentasPorPagarSaldoAnticipos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<CarCuentasPorPagarSaldoAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarSaldoAnticiposTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.exportarReporteCarListaCuentasPorPagarSaldoAnticipos(usuarioEmpresaReporteTO, listado, sector, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCuentasPorPagarCobrarAnticipos")
    public @ResponseBody
    String exportarReporteCuentasPorPagarCobrarAnticipos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<DetalleExportarFiltrado> listadoFiltrado = UtilsJSON.jsonToList(DetalleExportarFiltrado.class, map.get("listadoFiltrado"));
        List<CarCuentasPorPagarCobrarAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarAnticiposTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.exportarReporteCuentasPorPagarCobrarAnticipos(usuarioEmpresaReporteTO, listado, sector, fechaHasta, listadoFiltrado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*COBROS*/
    @RequestMapping("/insertarCarCobros")
    public RespuestaWebTO insertarCarCobros(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarCobrosTO carCobrosTO = UtilsJSON.jsonToObjeto(CarCobrosTO.class, map.get("carCobrosTO"));
        List<CarCobrosDetalleVentasTO> carCobrosDetalleVentasTOs = UtilsJSON.jsonToList(CarCobrosDetalleVentasTO.class, map.get("carCobrosDetalleVentasTOs"));
        List<CarCobrosDetalleAnticiposTO> carCobrosDetalleAnticiposTOs = UtilsJSON.jsonToList(CarCobrosDetalleAnticiposTO.class, map.get("carCobrosDetalleAnticiposTOs"));
        List<CarCobrosDetalleFormaTO> carCobrosDetalleFormaTOs = UtilsJSON.jsonToList(CarCobrosDetalleFormaTO.class, map.get("carCobrosDetalleFormaTOs"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            CarContableTO carContableTO = cobrosService.insertarCarCobros(carCobrosTO, carCobrosDetalleVentasTOs, carCobrosDetalleAnticiposTOs, carCobrosDetalleFormaTOs, listadoImagenes, sisInfoTO);
            if (carContableTO != null && carContableTO.getMensaje() != null && carContableTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(carContableTO.getMensaje().substring(1));
                ConContable contable = new ConContable(sisInfoTO.getEmpresa(), carContableTO.getContPeriodo(), carContableTO.getContTipo(), carContableTO.getContNumero());
                resp.setExtraInfo(contable);
            } else {
                String mensaje = "";
                if (carContableTO != null && carContableTO.getListaFacturaTO() != null) {
                    for (int i = 0; i < carContableTO.getListaFacturaTO().size(); i++) {
                        mensaje = mensaje + '\n' + carContableTO.getListaFacturaTO().get(i);
                    }
                }
                resp.setOperacionMensaje(carContableTO != null && carContableTO.getMensaje() != null ? carContableTO.getMensaje().substring(1) + mensaje : "Ocurrió un error al insertar cobro");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/activarWispro")
    public RespuestaWebTO activarWispro(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<CarCobrosDetalleVentasTO> carCobrosDetalleVentasTOs = UtilsJSON.jsonToList(CarCobrosDetalleVentasTO.class, map.get("carCobrosDetalleVentasTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respta = contableService.activarWispro(sisInfoTO, carCobrosDetalleVentasTOs);
            resp.setExtraInfo(respta);
            resp.setOperacionMensaje("Se ha realizado el proceso de activación correctamente.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/mayorizarCarCobros")
    public RespuestaWebTO mayorizarCarCobros(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarCobrosTO carCobrosTO = UtilsJSON.jsonToObjeto(CarCobrosTO.class, map.get("carCobrosTO"));
        List<CarCobrosDetalleVentasTO> carCobrosDetalleVentasTOs = UtilsJSON.jsonToList(CarCobrosDetalleVentasTO.class, map.get("carCobrosDetalleVentasTOs"));
        List<CarCobrosDetalleAnticiposTO> carCobrosDetalleAnticiposTOs = UtilsJSON.jsonToList(CarCobrosDetalleAnticiposTO.class, map.get("carCobrosDetalleAnticiposTOs"));
        List<CarCobrosDetalleFormaTO> carCobrosDetalleFormaTOs = UtilsJSON.jsonToList(CarCobrosDetalleFormaTO.class, map.get("carCobrosDetalleFormaTOs"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            CarContableTO carContableTO = cobrosService.mayorizarCarCobros(carCobrosTO, carCobrosDetalleVentasTOs, carCobrosDetalleAnticiposTOs, carCobrosDetalleFormaTOs, sisInfoTO);
            if (carContableTO != null && carContableTO.getMensaje() != null && carContableTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(carContableTO.getMensaje().substring(1));
                ConContable contable = new ConContable(sisInfoTO.getEmpresa(), carContableTO.getContPeriodo(), carContableTO.getContTipo(), carContableTO.getContNumero());
                try {
                    if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
                        contableService.actualizarImagenesContables(listadoImagenes, contable.getConContablePK());
                    }
                } catch (Exception e) {
                }
                resp.setExtraInfo(contable);
            } else {
                String mensaje = "";
                if (carContableTO != null && carContableTO.getListaFacturaTO() != null) {
                    for (int i = 0; i < carContableTO.getListaFacturaTO().size(); i++) {
                        mensaje = mensaje + '\n' + carContableTO.getListaFacturaTO().get(i);
                    }
                }
                resp.setOperacionMensaje(carContableTO != null && carContableTO.getMensaje() != null ? carContableTO.getMensaje().substring(1) + mensaje : "Ocurrió un error al insertar cobro");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaCobros")
    public RespuestaWebTO obtenerDatosParaCobros(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = cobrosService.obtenerDatosParaCobros(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener datos para cobros.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerCobro")
    public RespuestaWebTO obtenerCobro(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = cobrosService.obtenerCobro(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener cobro.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerCobroAPartirDeChequePostfechado")
    public RespuestaWebTO obtenerCobroAPartirDeChequePostfechado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        int secuencial = UtilsJSON.jsonToObjeto(int.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            CarFunCobrosTO respues = chequePostfechadoService.obtenerCobroAPartirDeChequePostfechado(secuencial);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener cobro.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*PAGOS*/
    @RequestMapping("/obtenerDatosParaPagos")
    public RespuestaWebTO obtenerDatosParaPagos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = pagosService.obtenerDatosParaPagos(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener datos para pagos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarCarPagos")
    public RespuestaWebTO insertarCarPagos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosTO carPagosTO = UtilsJSON.jsonToObjeto(CarPagosTO.class, map.get("carPagosTO"));
        List<CarPagosDetalleComprasTO> carPagosDetalleComprasTOs = UtilsJSON.jsonToList(CarPagosDetalleComprasTO.class, map.get("carPagosDetalleComprasTOs"));
        List<CarPagosDetalleAnticiposTO> carPagosDetalleAnticiposTOs = UtilsJSON.jsonToList(CarPagosDetalleAnticiposTO.class, map.get("carPagosDetalleAnticiposTOs"));
        List<CarPagosDetalleFormaTO> carPagosDetalleFormaTOs = UtilsJSON.jsonToList(CarPagosDetalleFormaTO.class, map.get("carPagosDetalleFormaTOs"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            CarContableTO carContableTO = pagosService.insertarCarPagos(carPagosTO, carPagosDetalleComprasTOs, carPagosDetalleAnticiposTOs, carPagosDetalleFormaTOs, listadoImagenes, sisInfoTO);
            if (carContableTO != null && carContableTO.getMensaje() != null && carContableTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(carContableTO.getMensaje().substring(1));
                ConContable contable = new ConContable(sisInfoTO.getEmpresa(), carContableTO.getContPeriodo(), carContableTO.getContTipo(), carContableTO.getContNumero());
                resp.setExtraInfo(contable);
            } else {
                String mensaje = "";
                if (carContableTO != null && carContableTO.getListaFacturaTO() != null) {
                    for (int i = 0; i < carContableTO.getListaFacturaTO().size(); i++) {
                        mensaje = mensaje + "\n" + carContableTO.getListaFacturaTO().get(i);
                    }
                }
                resp.setOperacionMensaje(carContableTO != null && carContableTO.getMensaje() != null ? carContableTO.getMensaje().substring(1) + mensaje : "Ocurrió un error al insertar pago");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/mayorizarCarPagos")
    public RespuestaWebTO mayorizarCarPagos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosTO carPagosTO = UtilsJSON.jsonToObjeto(CarPagosTO.class, map.get("carPagosTO"));
        List<CarPagosDetalleComprasTO> carPagosDetalleComprasTOs = UtilsJSON.jsonToList(CarPagosDetalleComprasTO.class, map.get("carPagosDetalleComprasTOs"));
        List<CarPagosDetalleAnticiposTO> carPagosDetalleAnticiposTOs = UtilsJSON.jsonToList(CarPagosDetalleAnticiposTO.class, map.get("carPagosDetalleAnticiposTOs"));
        List<CarPagosDetalleFormaTO> carPagosDetalleFormaTOs = UtilsJSON.jsonToList(CarPagosDetalleFormaTO.class, map.get("carPagosDetalleFormaTOs"));
        List<ConAdjuntosContableWebTO> listadoImagenes = UtilsJSON.jsonToList(ConAdjuntosContableWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            CarContableTO carContableTO = pagosService.mayorizarCarPagos(carPagosTO, carPagosDetalleComprasTOs, carPagosDetalleAnticiposTOs, carPagosDetalleFormaTOs, sisInfoTO);
            if (carContableTO != null && carContableTO.getMensaje() != null && carContableTO.getMensaje().charAt(0) == 'T') {
                ConContable contable = new ConContable(sisInfoTO.getEmpresa(), carContableTO.getContPeriodo(), carContableTO.getContTipo(), carContableTO.getContNumero());
                try {
                    if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
                        contableService.actualizarImagenesContables(listadoImagenes, contable.getConContablePK());
                    }
                } catch (Exception e) {
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(carContableTO.getMensaje().substring(1));

                resp.setExtraInfo(contable);
            } else {
                String mensaje = "";
                if (carContableTO != null && carContableTO.getListaFacturaTO() != null) {
                    for (int i = 0; i < carContableTO.getListaFacturaTO().size(); i++) {
                        mensaje = mensaje + "\n" + carContableTO.getListaFacturaTO().get(i);
                    }
                }
                resp.setOperacionMensaje(carContableTO != null && carContableTO.getMensaje() != null ? carContableTO.getMensaje().substring(1) + mensaje : "Ocurrió un error al mayorizar pago");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerPago")
    public RespuestaWebTO obtenerPago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = pagosService.obtenerPago(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //matricial
    @RequestMapping("/generarReporteFormaCobroPagoMatricial")
    public RespuestaWebTO generarReporteFormaCobroPagoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<CarPagosCobrosFormaTO> listado = UtilsJSON.jsonToList(CarPagosCobrosFormaTO.class, parametros.get("listado"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));//P o C
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String nombreReporte = tipo.equalsIgnoreCase("P") ? "reportCarFormaPago.jrxml" : "reportCarFormaCobro.jrxml";
        try {
            respuesta = reporteCarteraService.generarReporteCarReporteFormaPago(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePorLoteCobrosMatricial")
    public RespuestaWebTO generarReportePorLoteCobrosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCobrosPorLote.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        nombreReporte = nombre != null && !nombre.equals("") ? nombre : nombreReporte;
        List<String> numeroSistema = UtilsJSON.jsonToList(String.class, map.get("numeroSistema"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReportePorLoteCobros(usuarioEmpresaReporteTO, nombreReporte, numeroSistema, empresa);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePorLotePagosMatricial")
    public RespuestaWebTO generarReportePorLotePagosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportPagosPorLote.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        nombreReporte = nombre != null && !nombre.equals("") ? nombre : nombreReporte;
        List<String> numeroSistema = UtilsJSON.jsonToList(String.class, map.get("numeroSistema"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReportePorPagos(usuarioEmpresaReporteTO, nombreReporte, numeroSistema, empresa);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCuentasPorCobrarAnticiposMatricial")
    public RespuestaWebTO generarReporteCuentasPorCobrarAnticiposMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCuentasPorCobrarAnticipos.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        boolean verDetalle = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("verDetalle"));
        List<CarCuentasPorPagarCobrarAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarAnticiposTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCuentasPorCobrarAnticipos(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta, verDetalle);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarCuentasPorPagarCobrarAnticiposMatricial")
    public RespuestaWebTO generarReporteCarCuentasPorPagarCobrarAnticiposMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCuentasPorPagarAnticipos.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarCuentasPorPagarCobrarAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarAnticiposTO.class, parametros.get("listado"));
        boolean verDetalle = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("verDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarCuentasPorPagarCobrarAnticipos(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta, verDetalle);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarFunCuentasPorCobrarListadoVentasMatricial")
    public RespuestaWebTO generarReporteCarFunCuentasPorCobrarListadoVentasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCarFunCuentasPorCobrarListadoVentas.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarFunCuentasPorCobrarListadoVentasTO> listado = UtilsJSON.jsonToList(CarFunCuentasPorCobrarListadoVentasTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarFunCuentasPorCobrarListadoVentas(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaDesde, fechaHasta);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarListaMayorAuxiliarClienteProveedorMatricial")
    public RespuestaWebTO generarReporteCarListaMayorAuxiliarClienteProveedorMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportMayorAuxiliarProveedorCliente.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        List<CarListaMayorAuxiliarClienteProveedorTO> listado = UtilsJSON.jsonToList(CarListaMayorAuxiliarClienteProveedorTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarListaMayorAuxiliarClienteProveedor(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaDesde, fechaHasta, tipo);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorCobrarDetalladoMatricial")
    public RespuestaWebTO generarReporteCarListaCuentasPorCobrarDetalladoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCuentasPorCobrarDetallado.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CuentasPorCobrarDetalladoTO> listado = UtilsJSON.jsonToList(CuentasPorCobrarDetalladoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarListaCuentasPorCobrarDetallado(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorCobrarGeneralMatricial")
    public RespuestaWebTO generarReporteCarListaCuentasPorCobrarGeneralMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCuentasPorCobrarGeneral.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarCuentasPorPagarCobrarGeneralTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarGeneralTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarListaCuentasPorCobrarGeneral(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorPagarDetalladoMatricial")
    public RespuestaWebTO generarReporteCarListaCuentasPorPagarDetalladoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCuentasPorPagarDetallado.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CuentasPorPagarDetalladoTO> listado = UtilsJSON.jsonToList(CuentasPorPagarDetalladoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarListaCuentasPorPagarDetallado(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta, proveedor);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorPagarGeneralMatricial")
    public RespuestaWebTO generarReporteCarListaCuentasPorPagarGeneralMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCuentasPorPagarGeneral.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarCuentasPorPagarCobrarGeneralTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarCobrarGeneralTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarListaCuentasPorPagarGeneral(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaHasta, proveedor);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarFunCobrosDetalleMatricial")
    public RespuestaWebTO generarReporteCarFunCobrosDetalleMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportListadoCobro.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarFunCobrosDetalleTO> listado = UtilsJSON.jsonToList(CarFunCobrosDetalleTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarFunCobrosDetalle(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaDesde, fechaHasta);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarFunPagosDetalleMatricial")
    public RespuestaWebTO generarReporteCarFunPagosDetalleMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportListadoPago.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        boolean esForma = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("esForma"));
        String formaPagoSeleccionada = UtilsJSON.jsonToObjeto(String.class, parametros.get("formaPagoSeleccionada"));
        List<CarFunPagosDetalleTO> listado = UtilsJSON.jsonToList(CarFunPagosDetalleTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            if (esForma) {
                nombreReporte = "reportListadoPagoForma.jrxml";
            }
            respuesta = reporteCarteraService.generarReporteCarFunPagosDetalle(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor, formaPagoSeleccionada);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarFunCuentasPorPagarListadoComprasMatricial")
    public RespuestaWebTO generarReporteCarFunCuentasPorPagarListadoComprasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCarFunCuentasPorPagarListadoCompras.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarFunCuentasPorPagarListadoComprasTO> listado = UtilsJSON.jsonToList(CarFunCuentasPorPagarListadoComprasTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCarFunCuentasPorPagarListadoCompras(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCuentasPagadasMatricial")
    public RespuestaWebTO generarReporteCuentasPagadasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCarCuentasPagadas.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarFunCuentasPagadasListadoTO> listado = UtilsJSON.jsonToList(CarFunCuentasPagadasListadoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteCarteraService.generarReporteCuentasPagadas(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarFunPagosMatricial")
    public RespuestaWebTO generarReporteCarFunPagosMatricial(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportListadoPago.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarFunPagosTO> listado = UtilsJSON.jsonToList(CarFunPagosTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            byte[] respuesta = reporteCarteraService.generarReporteCarFunPagos(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarListaCuentasPorPagarSaldoAnticiposMatricial")
    public RespuestaWebTO generarReporteCarListaCuentasPorPagarSaldoAnticiposMatricial(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportCuentasPorPagarSaldoAnticipos.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<CarCuentasPorPagarSaldoAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorPagarSaldoAnticiposTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            byte[] respuesta = reporteCarteraService.generarReporteCarListaCuentasPorPagarSaldoAnticipos(usuarioEmpresaReporteTO, nombreReporte, listado, sector, fechaDesde, fechaHasta, proveedor);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCuentasPorPagarAnticiposMatricial")
    public RespuestaWebTO generarReporteCuentasPorPagarAnticiposMatricial(@RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportCuentasPorPagarAnticipos.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarCuentasPorCobrarSaldoAnticiposTO> listado = UtilsJSON.jsonToList(CarCuentasPorCobrarSaldoAnticiposTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            byte[] respuesta = reporteCarteraService.generarReporteCuentasPorPagarAnticipos(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaHasta);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //generarTxtDebitosBancarios
    @RequestMapping("/exportarTxtDebitoBancario")
    public @ResponseBody
    void exportarTxtDebitoBancario(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        List<CuentasPorCobrarDetalladoTO> cuentasPorCobrarDetallado = UtilsJSON.jsonToList(CuentasPorCobrarDetalladoTO.class, parametros.get("listado"));
        String reporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("reporte"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            reporteCarteraService.exportarTxtDebitoBancario(cuentasPorCobrarDetallado, reporte, response, sisInfoTO);
        } catch (GeneralException e) {
            response.addHeader("error", e.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
    }

    @RequestMapping("/enviarCorreosCuentaPorCobrarLote")
    public RespuestaWebTO enviarCorreosCuentaPorCobrarLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String asunto = UtilsJSON.jsonToObjeto(String.class, map.get("asunto"));
        SisEmpresaNotificaciones notificacion = UtilsJSON.jsonToObjeto(SisEmpresaNotificaciones.class, map.get("notificacion"));
        List<CuentasPorCobrarDetalladoTO> listaEnviar = UtilsJSON.jsonToList(CuentasPorCobrarDetalladoTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> mensajeAux = envioCorreoService.enviarCorreoParaCuentasPorCobrar(listaEnviar, empresa, asunto, notificacion);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(mensajeAux);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/aprobarPagos")
    public RespuestaWebTO aprobarPagos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<CuentasPorPagarDetalladoTO> listado = UtilsJSON.jsonToList(CuentasPorPagarDetalladoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> mensajeAux = pagosService.aprobarPagos(listado, sisInfoTO);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(mensajeAux);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarPagosAprobados")
    public RespuestaWebTO validarPagosAprobados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<CarPagosDetalleComprasTO> listado = UtilsJSON.jsonToList(CarPagosDetalleComprasTO.class, map.get("compras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> respues = pagosService.validarPagosAprobados(listado);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage() != null ? e.getMessage() : "Error al validar pagos aprobados.");
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getCarListaCobrosTO")
    public RespuestaWebTO getCarListaCobrosTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarListaCobrosTO> respuesta = cobrosService.getCarListaCobrosTO(empresa, cliente);
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

    @RequestMapping("/getCarCuentasPorPagarDetalladoGranjasMarinasTO")
    public RespuestaWebTO getCarCuentasPorPagarDetalladoGranjasMarinasTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarDetalladoGranjasMarinasTO> respuesta = pagosService.getCarCuentasPorPagarDetalladoGranjasMarinasTO(fecha);
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

    //listaOrdenesBancarias
    @RequestMapping("/getRhListaOrdenBancariaTO")
    public RespuestaWebTO generarOrdenBancariaCartera(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        RhOrdenBancariaTO ordenBancariaTO = UtilsJSON.jsonToObjeto(RhOrdenBancariaTO.class, map.get("ordenBancariaTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            resp = pagosAnticiposService.generarOrdenBancariaCartera(ordenBancariaTO, sector, sisInfoTO);
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

    @RequestMapping("/generarReporteCarFunCobrosMatricial")
    public RespuestaWebTO generarReporteCarFunCobrosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportListadoCobro.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarFunCobrosTO> listado = UtilsJSON.jsonToList(CarFunCobrosTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarFunCobros(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaDesde, fechaHasta);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCarFunCobros")
    public @ResponseBody
    String generarReporteCarFunCobros(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoCobro.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<CarFunCobrosTO> listado = UtilsJSON.jsonToList(CarFunCobrosTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteCarteraService.generarReporteCarFunCobros(usuarioEmpresaReporteTO, nombreReporte, cliente, listado, sector, fechaDesde, fechaHasta);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/validarAnticipoTienenContableAsociado")
    public ResponseEntity validarAnticipoTienenContableAsociado(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        CarCobrosAnticiposPK pk = UtilsJSON.jsonToObjeto(CarCobrosAnticiposPK.class, parametros.get("pk"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            CarCobrosAnticipos carCobrosAnticipos = cobrosAnticiposDao.obtenerPorId(CarCobrosAnticipos.class, pk);
            if (carCobrosAnticipos != null) {
                ConContablePK contablePk = carCobrosAnticipos.getConContableDeposito() != null
                        ? carCobrosAnticipos.getConContableDeposito().getConContablePK() : null;
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(contablePk);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró anticipo");
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

    @RequestMapping("/getCarListarAnticiposClientesHistorico")
    public RespuestaWebTO getCarListarAnticiposClientesHistorico(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String clienteCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("clienteCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorCobrarSaldoAnticiposTO> respuesta = cobrosAnticiposService.getCarListarAnticiposClientesHistorico(empresa, sector, clienteCodigo, desde, hasta, incluirTodos);
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

    @RequestMapping("/getCarListadoAnticipoProveedorHistorico")
    public RespuestaWebTO getCarListadoAnticipoProveedorHistorico(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedorCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("proveedorCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarSaldoAnticiposTO> respuesta = pagosAnticiposService.getCarListadoAnticipoProveedorHistorico(empresa, sector, proveedorCodigo, desde, hasta, incluirTodos);
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

    @RequestMapping("/getCarFunPagosDetalleTOAgrupadoProveedor")
    public RespuestaWebTO getCarFunPagosDetalleTOAgrupadoProveedor(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunPagosDetalleTO> respuesta = pagosService.getCarFunPagosDetalleTOAgrupadoProveedor(empresa, sector, desde, hasta, proveedor, formaPago);
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

    @RequestMapping("/getCarFunPagosDetalleTOAgrupadoCP")
    public RespuestaWebTO getCarFunPagosDetalleTOAgrupadoCP(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunPagosDetalleTO> respuesta = pagosService.getCarFunPagosDetalleTOAgrupadoCP(empresa, sector, desde, hasta, proveedor, formaPago);
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

    @RequestMapping("/getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCP")
    public RespuestaWebTO getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCP(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String clienteCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("clienteCodigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorCobrarSaldoAnticiposTO> respuesta = cobrosAnticiposService.getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCP(empresa, sector, clienteCodigo, hasta, incluirTodos);
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

    @RequestMapping("/getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCliente")
    public RespuestaWebTO getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCliente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String clienteCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("clienteCodigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorCobrarSaldoAnticiposTO> respuesta = cobrosAnticiposService.getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCliente(empresa, sector, clienteCodigo, hasta, incluirTodos);
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

    @RequestMapping("/getCarFunCobrosDetalleTOAgrupadoCliente")
    public RespuestaWebTO getCarFunCobrosDetalleTOAgrupadoCliente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarFunCobrosDetalleTO> respuesta = cobrosService.getCarFunCobrosDetalleTOAgrupadoCliente(empresa, sector, desde, hasta, cliente, formaPago);
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

    @RequestMapping("/getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoProveedor")
    public RespuestaWebTO getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoProveedor(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedorCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("proveedorCodigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarSaldoAnticiposTO> respuesta = pagosAnticiposService.getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoProveedor(empresa, sector, proveedorCodigo, hasta, incluirTodos);
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

    @RequestMapping("/getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoCC")
    public RespuestaWebTO getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoCC(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedorCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("proveedorCodigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarSaldoAnticiposTO> respuesta = pagosAnticiposService.getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoCC(empresa, sector, proveedorCodigo, hasta, incluirTodos);
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

    @RequestMapping("/getCarListarAnticiposClientesHistoricoAgrupadoCliente")
    public RespuestaWebTO getCarListarAnticiposClientesHistoricoAgrupadoCliente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String clienteCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("clienteCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorCobrarSaldoAnticiposTO> respuesta = cobrosAnticiposService.getCarListarAnticiposClientesHistoricoAgrupadoCliente(empresa, sector, clienteCodigo, desde, hasta, incluirTodos);
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

    @RequestMapping("/getCarListarAnticiposClientesHistoricoAgrupadoCC")
    public RespuestaWebTO getCarListarAnticiposClientesHistoricoAgrupadoCC(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String clienteCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("clienteCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorCobrarSaldoAnticiposTO> respuesta = cobrosAnticiposService.getCarListarAnticiposClientesHistoricoAgrupadoCC(empresa, sector, clienteCodigo, desde, hasta, incluirTodos);
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

    @RequestMapping("/listarCuentasCobrarCorteConexion")
    public RespuestaWebTO listarCuentasCobrarCorteConexion(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
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
            List<CuentasPorCobrarDetalladoTO> respuesta = cobrosService.getCarListaCuentasPorCobrarDetalladoTOCortesConexion(empresa, sector, cliente, desde, hasta, grupo, ichfa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            try {
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            } catch (Exception ex) {
            }
        }
        return resp;
    }

    @RequestMapping("/listarCuentasCobrarCorteConexionMS")
    public RespuestaMessageTO listarCuentasCobrarCorteConexionMS(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaMessageTO resp = new RespuestaMessageTO();
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
            if (cliente != null && !cliente.equals("")) {
                InvCliente c = clienteService.obtenerInvClientePorCedulaRuc(empresa, cliente);
                if (c != null) {
                    List<CuentasPorCobrarDetalladoTO> respuesta = cobrosService.getCarListaCuentasPorCobrarDetalladoTOCortesConexion(empresa, sector, c.getInvClientePK().getCliCodigo(), desde, hasta, grupo, ichfa);
                    if (respuesta != null && !respuesta.isEmpty()) {

                        List<CuentasPorCobrarDetalladoTO> collect = respuesta.stream()
                                .filter(usuario -> usuario.getCxcdClienteId() != null && !usuario.getCxcdClienteId().equals(""))
                                .collect(Collectors.toList());
                        String cadena = "";
                        if (collect != null) {
                            for (CuentasPorCobrarDetalladoTO deuda : collect) {
                                String id = "ID: " + deuda.getCxcdClienteId() + "\n";
                                String nombres = "Nombres: " + deuda.getCxcdClienteRazonSocial() + "\n";
                                String deudas = "Deudas: " + deuda.getCxcdSaldo() + "\n";
                                String fechaFac = "Fecha Factura: " + deuda.getCxcdFechaEmision() + "\n";
                                String numeroFac = "Número Factura: " + deuda.getCxcdCliente() + "\n";
                                cadena = cadena + id + nombres + deudas + fechaFac + numeroFac + "\n";
                            }
                        }
                        if (cadena != null && !cadena.isEmpty()) {
                            resp.setMessage(cadena);
                        } else {
                            resp.setMessage("El cliente no posee deudas.");
                        }
                    } else {
                        resp.setMessage("El cliente no posee deudas.");
                    }
                } else {
                    resp.setMessage("El cliente ingresado no es un cliente de la empresa.");
                }
            } else {
                resp.setMessage("No hay datos de cliente válido.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setMessage(e.getMessage());
            try {
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            } catch (Exception ex) {
            }
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaCuentasCobrarDetallado")
    public RespuestaWebTO obtenerDatosParaCuentasCobrarDetallado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = cobrosService.obtenerDatosParaCuentasCobrarDetallado(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener datos para cobros.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getCarListadoAnticipoProveedorHistoricoAgrupadoProveedor")
    public RespuestaWebTO getCarListadoAnticipoProveedorHistoricoAgrupadoProveedor(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedorCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("proveedorCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarSaldoAnticiposTO> respuesta = pagosAnticiposService.getCarListadoAnticipoProveedorHistoricoAgrupadoProveedor(empresa, sector, proveedorCodigo, desde, hasta, incluirTodos);
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

    @RequestMapping("/getCarListadoAnticipoProveedorHistoricoAgrupadoCC")
    public RespuestaWebTO getCarListadoAnticipoProveedorHistoricoAgrupadoCC(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedorCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("proveedorCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarSaldoAnticiposTO> respuesta = pagosAnticiposService.getCarListadoAnticipoProveedorHistoricoAgrupadoCC(empresa, sector, proveedorCodigo, desde, hasta, incluirTodos);
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
}
