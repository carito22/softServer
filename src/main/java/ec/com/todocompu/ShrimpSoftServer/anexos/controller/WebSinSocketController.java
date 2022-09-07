package ec.com.todocompu.ShrimpSoftServer.anexos.controller;

import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.EnviarComprobantesElectronicosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.EnviarComprobantesWSService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.GuiaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesReducidoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasPendientesReducidoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesPendientesReducidoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesReducidoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoAmbienteEnum;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todocompuWS/comprobantesController")
public class WebSinSocketController {

    @Autowired
    private EnviarComprobantesWSService enviarComprobantesWSService;
    @Autowired
    private VentaElectronicaService ventaElectronicaService;
    @Autowired
    private CompraElectronicaService compraElectronicaService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private GuiaElectronicaService guiaElectronicaService;
    @Autowired
    private EnviarComprobantesElectronicosService enviarComprobantesElectronicosService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @RequestMapping("/enviarAutorizarFacturasElectronicaLote")
    public RespuestaWebTO enviarAutorizarFacturasElectronicaLote(@RequestBody Map<String, Object> map) throws Exception {
        List<AnxListaVentasPendientesReducidoTO> listaEnviar = UtilsJSON.jsonToList(AnxListaVentasPendientesReducidoTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        RespuestaWebTO resp = new RespuestaWebTO();
        try {
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            SisFirmaElectronica cajCajaTO = enviarComprobantesWSService.validarRequisitosParaEnviarAutorizacion(sisInfoTO);

            if (listaEnviar != null && listaEnviar.size() > 500) {
                SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
                if (parametros != null) {
                    if (parametros.isParFacturasEnProceso()) {
                        resp.setExtraInfo(false);
                        resp.setOperacionMensaje("Existe un proceso en curso, espere un mensaje de confirmación al correo: " + sisInfoTO.getEmail() + ", para poder realizar el siguiente envío de comprobantes.");
                        return resp;
                    } else {
                        parametros.setParFacturasEnProceso(true);
                        empresaParametrosDao.actualizar(parametros);
                    }
                }
                enviarComprobantesElectronicosService.enviarFacturasAsincronas(listaEnviar, sisInfoTO, cajCajaTO, usuarioEmpresaReporteTO);
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("Se enviará un mensaje de confirmación al correo: " + sisInfoTO.getEmail() + ", para poder realizar el siguiente envío de comprobantes.");
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                
            } else {
                
                List<RespuestaWebTO> respIndividual = new ArrayList<>();
                List<String> erroresAlAutorizar = new ArrayList<>();
                for (AnxListaVentasPendientesReducidoTO factura : listaEnviar) {
                    RespuestaWebTO individual = new RespuestaWebTO();
                    individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    String mensajeAux;
                    try {
                        mensajeAux = enviarComprobantesWSService.enviarAutorizarFacturaElectronica(sisInfoTO.getEmpresa(), factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero(),
                                factura.getVtaAmbiente().compareToIgnoreCase("PRODUCCION") == 0 ? TipoAmbienteEnum.PRODUCCION.getCode() : TipoAmbienteEnum.PRUEBAS.getCode(), 'I', cajCajaTO, sisInfoTO);
                    } catch (GeneralException e) {
                        mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                    } catch (Exception e) {
                        e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                        mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                    }
                    mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico " + factura.getVtaDocumentoTipo() + ":" + factura.getVtaDocumentoNumero()) : mensajeAux;
                    if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                        if (mensajeAux.contains("AUTORIZADO") && !mensajeAux.contains("NO AUTORIZADO") && !mensajeAux.contains("erro")) {
                            individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                            String nombreReporte = "";
                            if (factura.getVtaDocumentoTipo().compareTo("18") == 0 || factura.getVtaDocumentoTipo().compareTo("41") == 0) {
                                nombreReporte = "reportComprobanteFacturaRide";
                            } else if (factura.getVtaDocumentoTipo().compareTo("05") == 0) {
                                nombreReporte = "reportComprobanteNotaDebitoRide";
                            } else if (factura.getVtaDocumentoTipo().compareTo("04") == 0) {
                                nombreReporte = "reportComprobanteNotaCreditoRide";
                            }
                            String xml = ventaElectronicaService.getXmlComprobanteElectronico(sisInfoTO.getEmpresa(), factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero());
                            String claveAcceso = xml.substring(xml.lastIndexOf("<claveAcceso>") + 13, xml.lastIndexOf("</claveAcceso>")).trim();
                            System.out.println("==========> INICIO ENVIO DE CORREO: " + factura.getVtaDocumentoNumero());
                            mensajeAux += ", " + compraElectronicaService.enviarEmailComprobantesElectronicos(sisInfoTO.getEmpresa(), factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero(), claveAcceso, nombreReporte, xml, sisInfoTO).substring(1);
                            System.out.println("==========> FIN ENVIO DE CORREO: " + factura.getVtaDocumentoNumero());
                        } else {
                            erroresAlAutorizar.add(factura.getVtaDocumentoNumero() + " --> " + mensajeAux.substring(1));
                        }
                    } else {
                        erroresAlAutorizar.add(factura.getVtaDocumentoNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(1) : "Error desconocido."));
                    }
                    individual.setOperacionMensaje(factura.getVtaDocumentoNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(1) : "Error desconocido."));
                    individual.setExtraInfo(factura.getId());
                    respIndividual.add(individual);
                }
                resp.setExtraInfo(respIndividual);
                resp.setOperacionMensaje("El proceso ha finalizado de manera correcta.");
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                envioCorreoService.notificarPorCorreoDocumentosNoAutorizadas(usuarioEmpresaReporteTO, erroresAlAutorizar, sisInfoTO, "[Atención] Ventas pendientes de autorización.");
            }
            
        } catch (Exception e) {
            SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
            if (parametros != null) {
                parametros.setParFacturasEnProceso(false);
                empresaParametrosDao.actualizar(parametros);
            }
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje("Ocurrió un error al realizar algunas autorizaciones: " + e.getMessage());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarAutorizarRetencionElectronicaLote")
    public RespuestaWebTO enviarAutorizarRetencionElectronicaLote(@RequestBody Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaRetencionesPendientesReducidoTO> listaEnviar = UtilsJSON.jsonToList(AnxListaRetencionesPendientesReducidoTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        RespuestaWebTO resp = new RespuestaWebTO();
        List<RespuestaWebTO> respIndividual = new ArrayList<>();
        List<String> erroresAlAutorizar = new ArrayList<>();
        try {
            SisFirmaElectronica cajCajaTO = enviarComprobantesWSService.validarRequisitosParaEnviarAutorizacion(sisInfoTO);
            for (AnxListaRetencionesPendientesReducidoTO compra : listaEnviar) {
                RespuestaWebTO individual = new RespuestaWebTO();
                individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                List<String> comprobantes = UtilsValidacion.separarComprobante(compra.getRetLlaveCompra());
                String periodo = comprobantes.get(0);
                String motivo = comprobantes.get(1);
                String numero = comprobantes.get(2);
                String mensajeAux;
                try {
                    mensajeAux = enviarComprobantesWSService.enviarAutorizarRetencionElectronica(empresa, periodo, motivo, numero,
                            compra.getRetAmbiente().compareToIgnoreCase("PRODUCCION") == 0
                            ? TipoAmbienteEnum.PRODUCCION.getCode()
                            : TipoAmbienteEnum.PRUEBAS.getCode(),
                            'I', cajCajaTO, sisInfoTO);
                } catch (GeneralException e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                } catch (Exception e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                }
                mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico " + compra.getRetProveedorTipo() + ":" + compra.getRetRetencionNumero()) : mensajeAux;
                if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                    if (mensajeAux.contains("AUTORIZADO") && !mensajeAux.contains("erro")) {
                        individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        String xml = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
                        String claveAcceso = xml.substring(xml.lastIndexOf("<claveAcceso>") + 13, xml.lastIndexOf("</claveAcceso>")).trim();
                        mensajeAux += ", " + compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, periodo, motivo, numero, claveAcceso, "reportComprobanteRetencionRide", xml, sisInfoTO).substring(1) + ")|";
                    } else {
                        erroresAlAutorizar.add(compra.getRetRetencionNumero() + " --> " + mensajeAux.substring(1));
                    }
                } else {
                    erroresAlAutorizar.add(compra.getRetRetencionNumero() + " --> " + mensajeAux.substring(1));
                }
                individual.setOperacionMensaje(compra.getRetRetencionNumero() + " --> " + mensajeAux.substring(1));
                individual.setExtraInfo(compra.getId());
                respIndividual.add(individual);
            }
            resp.setExtraInfo(respIndividual);
            resp.setOperacionMensaje("El proceso ha finalizado de manera correcta.");
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            envioCorreoService.notificarPorCorreoDocumentosNoAutorizadas(usuarioEmpresaReporteTO, erroresAlAutorizar, sisInfoTO, "[Atención] Retenciones pendientes de autorización.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje("Ocurrió un error al realizar algunas autorizaciones: " + e.getMessage());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarAutorizarLiquidacionCompraLote")
    public RespuestaWebTO enviarAutorizarLiquidacionCompraLote(@RequestBody Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaLiquidacionComprasPendientesReducidoTO> listaEnviar = UtilsJSON.jsonToList(AnxListaLiquidacionComprasPendientesReducidoTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        RespuestaWebTO resp = new RespuestaWebTO();
        List<RespuestaWebTO> respIndividual = new ArrayList<>();
        List<String> erroresAlAutorizar = new ArrayList<>();
        try {
            SisFirmaElectronica cajCajaTO = enviarComprobantesWSService.validarRequisitosParaEnviarAutorizacion(sisInfoTO);
            for (AnxListaLiquidacionComprasPendientesReducidoTO compra : listaEnviar) {
                RespuestaWebTO individual = new RespuestaWebTO();
                individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                String mensajeAux;
                try {
                    mensajeAux = enviarComprobantesWSService.enviarAutorizarLiquidacionCompras(empresa, compra.getCompPeriodo(), compra.getCompMotivo(), compra.getCompNumero(),
                            compra.getCompAmbiente().compareToIgnoreCase("PRODUCCION") == 0 ? TipoAmbienteEnum.PRODUCCION.getCode() : TipoAmbienteEnum.PRUEBAS.getCode(), 'I', cajCajaTO, sisInfoTO);
                } catch (Exception e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;;
                }
                mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico " + compra.getCompDocumentoTipo() + ":" + compra.getCompDocumentoNumero()) : mensajeAux;
                if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                    if (mensajeAux.contains("AUTORIZADO") && !mensajeAux.contains("erro")) {
                        individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        String xml = compraElectronicaService.getXmlLiquidacionCompras(empresa, compra.getCompPeriodo(), compra.getCompMotivo(), compra.getCompNumero());
                        String claveAcceso = xml.substring(xml.lastIndexOf("<claveAcceso>") + 13, xml.lastIndexOf("</claveAcceso>")).trim();
                        mensajeAux += ", " + compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, compra.getCompPeriodo(), compra.getCompMotivo(), compra.getCompNumero(), claveAcceso, "reportComprobanteLiquidacionCompraRide", xml, sisInfoTO).substring(1) + ")|";
                    } else {
                        erroresAlAutorizar.add(compra.getCompDocumentoNumero() + " --> " + mensajeAux.substring(1));
                    }
                } else {
                    erroresAlAutorizar.add(compra.getCompDocumentoNumero() + " --> " + mensajeAux.substring(1));
                }
                individual.setOperacionMensaje(compra.getCompDocumentoNumero() + " --> " + mensajeAux.substring(1));
                individual.setExtraInfo(compra.getId());
                respIndividual.add(individual);
            }
            resp.setExtraInfo(respIndividual);
            resp.setOperacionMensaje("El proceso ha finalizado de manera correcta.");
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            envioCorreoService.notificarPorCorreoDocumentosNoAutorizadas(usuarioEmpresaReporteTO, erroresAlAutorizar, sisInfoTO, "[Atención] Liquidación compras pendientes de autorización.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje("Ocurrió un error al realizar algunas autorizaciones: " + e.getMessage());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarAutorizarGuiaRemisionLote")
    public RespuestaWebTO enviarAutorizarGuiaRemisionLote(@RequestBody Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaGuiaRemisionPendientesReducidoTO> listaEnviar = UtilsJSON.jsonToList(AnxListaGuiaRemisionPendientesReducidoTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        RespuestaWebTO resp = new RespuestaWebTO();
        List<RespuestaWebTO> respIndividual = new ArrayList<>();
        List<String> erroresAlAutorizar = new ArrayList<>();
        try {
            SisFirmaElectronica cajCajaTO = enviarComprobantesWSService.validarRequisitosParaEnviarAutorizacion(sisInfoTO);
            for (AnxListaGuiaRemisionPendientesReducidoTO guia : listaEnviar) {
                RespuestaWebTO individual = new RespuestaWebTO();
                individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                String mensajeAux;
                try {
                    mensajeAux = enviarComprobantesWSService.enviarAutorizarGuiaRemision(empresa, guia.getGuiaPeriodo(), guia.getGuiaNumero(),
                            guia.getGuiaAmbiente().compareToIgnoreCase("PRODUCCION") == 0 ? TipoAmbienteEnum.PRODUCCION.getCode() : TipoAmbienteEnum.PRUEBAS.getCode(), 'I', cajCajaTO, sisInfoTO);
                } catch (Exception e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;;
                }
                mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico " + "06" + ":" + guia.getGuiaDocumentoNumero()) : mensajeAux;
                if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                    if (mensajeAux.contains("AUTORIZADO") && !mensajeAux.contains("erro")) {
                        individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        String xml = guiaElectronicaService.getXmlGuiaRemision(empresa, guia.getGuiaPeriodo(), guia.getGuiaNumero());
                        String claveAcceso = xml.substring(xml.lastIndexOf("<claveAcceso>") + 13, xml.lastIndexOf("</claveAcceso>")).trim();
                        mensajeAux += ", " + guiaElectronicaService.enviarEmailComprobantesElectronicos(empresa, guia.getGuiaPeriodo(), guia.getGuiaNumero(), claveAcceso, "reportComprobanteGuiaRemisionRide", xml).substring(1) + ")|";
                    } else {
                        erroresAlAutorizar.add(guia.getGuiaDocumentoNumero() + " --> " + mensajeAux.substring(1));
                    }
                } else {
                    erroresAlAutorizar.add(guia.getGuiaDocumentoNumero() + " --> " + mensajeAux.substring(1));
                }
                individual.setOperacionMensaje(guia.getGuiaDocumentoNumero() + " --> " + mensajeAux.substring(1));
                individual.setExtraInfo(guia.getId());
                respIndividual.add(individual);
            }
            resp.setExtraInfo(respIndividual);
            resp.setOperacionMensaje("El proceso ha finalizado de manera correcta.");
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            envioCorreoService.notificarPorCorreoDocumentosNoAutorizadas(usuarioEmpresaReporteTO, erroresAlAutorizar, sisInfoTO, "[Atención] Guía de remisión pendientes de autorización.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje("Ocurrió un error al realizar algunas autorizaciones: " + e.getMessage());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    public void errorPorEnvioDeCorreo(RespuestaWebTO resp, String mensaje, String urlRespuesta) {
        resp.setOperacionMensaje("No se pudo enviar notificaciones debido a: " + mensaje);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
    }

    @RequestMapping("/enviarCorreoFacturasElectronicaLote")
    public RespuestaWebTO enviarCorreoFacturasElectronicaLote(@RequestBody Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaVentaElectronicaTO> listaEnviar = UtilsJSON.jsonToList(AnxListaVentaElectronicaTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RespuestaWebTO resp = new RespuestaWebTO();
        List<RespuestaWebTO> respIndividual = new ArrayList<>();
        try {
            for (AnxListaVentaElectronicaTO factura : listaEnviar) {
                String nombreReporte = "";
                if (factura.getVtaDocumento_tipo().equalsIgnoreCase("18")) {
                    nombreReporte = "reportComprobanteFacturaRide";
                } else if (factura.getVtaDocumento_tipo().equalsIgnoreCase("05")) {
                    nombreReporte = "reportComprobanteNotaDebitoRide";
                } else if (factura.getVtaDocumento_tipo().equalsIgnoreCase("04")) {
                    nombreReporte = "reportComprobanteNotaCreditoRide";
                }
                RespuestaWebTO individual = new RespuestaWebTO();
                individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                String mensajeAux;
                try {
                    String xmlAutorizacion = ventaElectronicaService.getXmlComprobanteElectronico(empresa, factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero());
                    String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                    mensajeAux = compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero(), claveAcceso, nombreReporte, xmlAutorizacion, sisInfoTO);

                } catch (GeneralException e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                } catch (Exception e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                }
                mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico " + factura.getVtaDocumento_tipo() + ":" + factura.getVtaDocumentoNumero()) : mensajeAux;
                if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                    individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                }
                individual.setOperacionMensaje(factura.getVtaDocumentoNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(1) : "Error desconocido."));
                individual.setExtraInfo(factura.getId());
                respIndividual.add(individual);
            }
            resp.setExtraInfo(respIndividual);
            resp.setOperacionMensaje("El proceso ha finalizado de manera correcta.");
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje("Ocurrió un error al realizar algunas autorizaciones: " + e.getMessage());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoGuiasElectronicaLote")
    public RespuestaWebTO enviarCorreoGuiasElectronicaLote(@RequestBody Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaGuiaRemisionElectronicaTO> listaEnviar = UtilsJSON.jsonToList(AnxListaGuiaRemisionElectronicaTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RespuestaWebTO resp = new RespuestaWebTO();
        List<RespuestaWebTO> respIndividual = new ArrayList<>();
        try {
            for (AnxListaGuiaRemisionElectronicaTO item : listaEnviar) {
                String nombreReporte = "reportComprobanteGuiaRemisionRide";
                RespuestaWebTO individual = new RespuestaWebTO();
                individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                String mensajeAux;
                try {
                    String xmlAutorizacion = guiaElectronicaService.getXmlGuiaRemision(empresa, item.getGuiaPeriodo(), item.getGuiaNumero());
                    String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                    mensajeAux = guiaElectronicaService.enviarEmailComprobantesElectronicos(empresa, item.getGuiaPeriodo(), item.getGuiaNumero(), claveAcceso, nombreReporte, xmlAutorizacion);
                } catch (GeneralException e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                } catch (Exception e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                }
                mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico " + item.getGuiaDocumentoNumero()) : mensajeAux;
                if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                    individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                }
                individual.setOperacionMensaje(item.getGuiaDocumentoNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(1) : "Error desconocido."));
                individual.setExtraInfo(item.getId());
                respIndividual.add(individual);
            }
            resp.setExtraInfo(respIndividual);
            resp.setOperacionMensaje("El proceso ha finalizado de manera correcta.");
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje("Ocurrió un error al realizar algunas autorizaciones: " + e.getMessage());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoRetencionesElectronicaLote")
    public RespuestaWebTO enviarCorreoRetencionesElectronicaLote(@RequestBody Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListadoCompraElectronicaTO> listaEnviar = UtilsJSON.jsonToList(AnxListadoCompraElectronicaTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RespuestaWebTO resp = new RespuestaWebTO();
        List<RespuestaWebTO> respIndividual = new ArrayList<>();
        try {
            for (AnxListadoCompraElectronicaTO item : listaEnviar) {
                RespuestaWebTO individual = new RespuestaWebTO();
                individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                String mensajeAux;
                try {
                    String periodo = item.getCompPeriodo();
                    String motivo = item.getCompMotivo();
                    String numero = item.getCompNumero();
                    String xmlAutorizacion = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
                    String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                    mensajeAux = compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, periodo, motivo, numero, claveAcceso, "reportComprobanteRetencionRide", xmlAutorizacion, sisInfoTO);
                } catch (GeneralException e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                } catch (Exception e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                }
                mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico " + item.getCompNumero()) : mensajeAux;
                if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                    individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                }
                individual.setOperacionMensaje(item.getCompNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(1) : "Error desconocido."));
                individual.setExtraInfo(item.getId());
                respIndividual.add(individual);
            }
            resp.setExtraInfo(respIndividual);
            resp.setOperacionMensaje("El proceso ha finalizado de manera correcta.");
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje("Ocurrió un error al realizar algunas autorizaciones: " + e.getMessage());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoLiquidacionesElectronicaLote")
    public RespuestaWebTO enviarCorreoLiquidacionesElectronicaLote(@RequestBody Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaLiquidacionComprasElectronicaTO> listaEnviar = UtilsJSON.jsonToList(AnxListaLiquidacionComprasElectronicaTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        RespuestaWebTO resp = new RespuestaWebTO();
        List<RespuestaWebTO> respIndividual = new ArrayList<>();
        try {
            for (AnxListaLiquidacionComprasElectronicaTO item : listaEnviar) {
                RespuestaWebTO individual = new RespuestaWebTO();
                individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                String mensajeAux;
                try {
                    String nombreReporte = "";
                    nombreReporte = "reportComprobanteLiquidacionCompraRide";
                    String xmlAutorizacion = compraElectronicaService.getXmlLiquidacionCompras(empresa, item.getCompPeriodo(), item.getCompMotivo(), item.getCompNumero());
                    String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                    mensajeAux = compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, item.getCompPeriodo(), item.getCompMotivo(), item.getCompNumero(), claveAcceso, nombreReporte, xmlAutorizacion, sisInfoTO);

                } catch (GeneralException e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                } catch (Exception e) {
                    mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
                }
                mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico " + item.getCompNumero()) : mensajeAux;
                if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                    individual.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                }
                individual.setOperacionMensaje(item.getCompNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(1) : "Error desconocido."));
                individual.setExtraInfo(item.getId());
                respIndividual.add(individual);
            }
            resp.setExtraInfo(respIndividual);
            resp.setOperacionMensaje("El proceso ha finalizado de manera correcta.");
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje("Ocurrió un error al realizar algunas autorizaciones: " + e.getMessage());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

}
