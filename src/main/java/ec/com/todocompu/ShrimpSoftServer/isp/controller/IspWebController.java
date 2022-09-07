/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.controller;

import ec.com.todocompu.ShrimpSoftServer.inventario.service.BodegaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.TipoContratoService;
import ec.com.todocompu.ShrimpSoftServer.isp.service.MikrotikService;
import ec.com.todocompu.ShrimpSoftServer.isp.service.SisEmpresaParametrosMikrotikService;
import ec.com.todocompu.ShrimpSoftServer.isp.service.SisPromesaPagoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoTipo;
import ec.com.todocompu.ShrimpSoftUtils.isp.TO.FirewallContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.isp.entity.IspReporteArcotel;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametrosMikrotik;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPromesaPago;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author VALDIVIEZO
 */
@RestController
@RequestMapping("/todocompuWS/ispWebController")
public class IspWebController {

    private static Locale locale = new Locale("es", "EC");
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private MikrotikService mikrotikService;
    @Autowired
    private SisEmpresaParametrosMikrotikService sisEmpresaParametrosMikrotikService;
    @Autowired
    private SisPromesaPagoService sisPromesaPagoService;
    @Autowired
    private TipoContratoService tipoContratoService;
    @Autowired
    private BodegaService bodegaService;

    @RequestMapping("/listarFirewallRules")
    public RespuestaWebTO listarInterfaces(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<Map<String, String>> respuesta = mikrotikService.listarFirewallRules(sisInfoTO.getEmpresa());
            if (respuesta != null && respuesta.size() > 0) {
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

    @RequestMapping("/actualizarEstadoFirewallRules")
    public RespuestaWebTO actualizarEstadoFirewallRules(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String id = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        boolean habilitar = UtilsJSON.jsonToObjeto(boolean.class, map.get("habilitar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = mikrotikService.habilitarFirewall(sisInfoTO.getEmpresa(), id, habilitar);
            if (respuesta != null && respuesta.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
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

    @RequestMapping("/obtenerSisEmpresaParametrosMikrotik")
    public RespuestaWebTO obtenerSisEmpresaParametrosMikrotik(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisEmpresaParametrosMikrotik parametrosMikrotik = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(sisInfoTO.getEmpresa());
            if (parametrosMikrotik != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(parametrosMikrotik);
            } else {
                resp.setOperacionMensaje("No se encuentra los parámetros de configuración");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosClienteContrato")
    public RespuestaWebTO obtenerDatosClienteContrato(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> resultado = new HashMap<>();
            SisEmpresaParametrosMikrotik parametrosMikrotik = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(sisInfoTO.getEmpresa());
            List<InvListaBodegasTO> listaBodegas = bodegaService.buscarBodegasTO(sisInfoTO.getEmpresa(), false, null);
            List<InvClienteContratoTipo> listaTipos = tipoContratoService.getListarInvTipoContrato(sisInfoTO.getEmpresa());
            resultado.put("listaTipos", listaTipos);
            resultado.put("listaBodegas", listaBodegas);
            resultado.put("parametrosMikrotik", parametrosMikrotik);
            if (resultado != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(resultado);
            } else {
                resp.setOperacionMensaje("No se encuentra los parámetros de configuración");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarSisEmpresaParametrosMikrotik")
    public RespuestaWebTO insertarSisEmpresaParametrosMikrotik(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik = UtilsJSON.jsonToObjeto(SisEmpresaParametrosMikrotik.class, map.get("sisEmpresaParametrosMikrotik"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisEmpresaParametrosMikrotik = sisEmpresaParametrosMikrotikService.insertarSisEmpresaParametrosMikrotik(sisEmpresaParametrosMikrotik, sisInfoTO);
            if (sisEmpresaParametrosMikrotik != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisEmpresaParametrosMikrotik);
                resp.setOperacionMensaje("Los parámetros de mikrotik se ha guardado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al guardar los parámetros de mikrotik");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarSisEmpresaParametrosMikrotik")
    public RespuestaWebTO modificarSisEmpresaParametrosMikrotik(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik = UtilsJSON.jsonToObjeto(SisEmpresaParametrosMikrotik.class, map.get("sisEmpresaParametrosMikrotik"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisEmpresaParametrosMikrotik = sisEmpresaParametrosMikrotikService.modificarSisEmpresaParametrosMikrotik(sisEmpresaParametrosMikrotik, sisInfoTO);
            if (sisEmpresaParametrosMikrotik != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisEmpresaParametrosMikrotik);
                resp.setOperacionMensaje("Los parámetros de mikrotik se ha modificado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar los parámetros de mikrotik");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarSisEmpresaParametrosMikrotik")
    public RespuestaWebTO eliminarSisEmpresaParametrosMikrotik(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = sisEmpresaParametrosMikrotikService.eliminarSisEmpresaParametrosMikrotik(secuencial, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("Los parámetros de mikrotik se ha eliminado correctamente");
            } else {
                resp.setOperacionMensaje("No se puede eliminar los parámetros de mikrotik");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarClientesAddressFirewall")
    public RespuestaWebTO listarClientesAddressFirewall(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<FirewallContratoTO> respuesta = mikrotikService.listarClientesAddressFirewall(sisInfoTO.getEmpresa());
            if (respuesta != null && respuesta.size() > 0) {
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

    @RequestMapping("/actualizarEstadoFirewallAddress")
    public RespuestaWebTO actualizarEstadoFirewallAddress(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String ip = UtilsJSON.jsonToObjeto(String.class, map.get("ip"));
        boolean habilitar = UtilsJSON.jsonToObjeto(boolean.class, map.get("habilitar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = mikrotikService.actualizarEstadoFirewallAddress(sisInfoTO.getEmpresa(), ip, habilitar, sisInfoTO);
            if (respuesta != null && respuesta.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
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

    //PROMESA PAGO
    @RequestMapping("/listarSisPromesaPagoPorcliente")
    public RespuestaWebTO listarSisPromesaPagoPorcliente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SisPromesaPago> respuesta = sisPromesaPagoService.getListSisPromesaPagoPorcliente(sisInfoTO.getEmpresa(), cliente);
            if (respuesta != null && respuesta.size() > 0) {
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

    @RequestMapping("/insertarSisPromesaPago")
    public RespuestaWebTO insertarSisPromesaPago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPromesaPago sisPromesaPago = UtilsJSON.jsonToObjeto(SisPromesaPago.class, map.get("sisPromesaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisPromesaPago = sisPromesaPagoService.insertarSisPromesaPago(sisPromesaPago, sisInfoTO);
            if (sisPromesaPago != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisPromesaPago);
                resp.setOperacionMensaje("La promesa de pago se ha guardado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al guardar la promesa de pago");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarSisPromesaPago")
    public RespuestaWebTO modificarSisPromesaPago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisPromesaPago sisPromesaPago = UtilsJSON.jsonToObjeto(SisPromesaPago.class, map.get("sisPromesaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            sisPromesaPago = sisPromesaPagoService.modificarSisPromesaPago(sisPromesaPago, sisInfoTO);
            if (sisPromesaPago != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(sisPromesaPago);
                resp.setOperacionMensaje("La promesa de pago se ha modificado correctamente");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar la promesa de pago");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarSisPromesaPago")
    public RespuestaWebTO eliminarSisPromesaPago(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respuesta = sisPromesaPagoService.eliminarSisPromesaPago(secuencial, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La promesa de pago se ha eliminado correctamente");
            } else {
                resp.setOperacionMensaje("No se puede eliminar la promesa de pago");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    // FIREWALL ADDRESS
    @RequestMapping("/obtenerDatosFirewallAddress")
    public RespuestaWebTO obtenerDatosFirewallAddress(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usuarioCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = mikrotikService.obtenerDatosFirewallAddress(sisInfoTO.getEmpresa(), usuarioCodigo);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encuentra los parámetros de configuración");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarFirewallAddressList")
    public RespuestaWebTO listarFirewallAddressList(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<Map<String, String>> respuesta = mikrotikService.listarFirewallAddressList(sisInfoTO.getEmpresa());
            if (respuesta != null && respuesta.size() > 0) {
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

    @RequestMapping("/reporteArcotel")
    public RespuestaWebTO reporteArcotel(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<IspReporteArcotel> respuesta = mikrotikService.listarIspReporteArcotel(sisInfoTO.getEmpresa(), busqueda);
            if (respuesta != null && respuesta.size() > 0) {
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

    @RequestMapping("/listarContratos")
    public RespuestaWebTO listarContratos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = sisEmpresaParametrosMikrotikService.listarContratos(sisInfoTO.getEmpresa());
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

    @RequestMapping("/consultarContrato")
    public RespuestaWebTO consultarContrato(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = sisEmpresaParametrosMikrotikService.consultarContrato(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al consultar");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/actualizarEstadoContrato")
    public RespuestaWebTO actualizarEstadoContrato(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = sisEmpresaParametrosMikrotikService.actualizarEstadoContrato(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al consultar");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/actualizarIpContratosListado")
    public RespuestaWebTO actualizarIpContratosListado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = mikrotikService.actualizarIpContratosListado(sisInfoTO);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Error al actualizar IP");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/cortarServicioPorLote")
    public RespuestaWebTO cortarServicioPorLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<CuentasPorCobrarDetalladoTO> listaEnviar = UtilsJSON.jsonToList(CuentasPorCobrarDetalladoTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = mikrotikService.cortarServicioPorLote(sisInfoTO, listaEnviar);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Error al cortar servicio por lote");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/activarServicioEnCobros")
    public RespuestaWebTO activarServicioEnCobros(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarCobrosPK pk = UtilsJSON.jsonToObjeto(CarCobrosPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = mikrotikService.activarServicioEnCobros(sisInfoTO, pk);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Error al cortar servicio por lote");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

}
