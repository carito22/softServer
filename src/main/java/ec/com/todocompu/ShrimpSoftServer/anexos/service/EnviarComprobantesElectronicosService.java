/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesReducidoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoAmbienteEnum;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author mario
 */
@Service
public class EnviarComprobantesElectronicosService {

    @Autowired
    private VentaElectronicaService ventaElectronicaService;
    @Autowired
    private CompraElectronicaService compraElectronicaService;
    @Autowired
    private EnviarComprobantesWSService enviarComprobantesWSService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @Async("asyncExecutor")
    public void enviarFacturasAsincronas(List<AnxListaVentasPendientesReducidoTO> listaEnviar, SisInfoTO sisInfoTO, SisFirmaElectronica cajCajaTO, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
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
                try {
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
                } catch (Exception e) {
                    erroresAlAutorizar.add(factura.getVtaDocumentoNumero() + " --> " + e.getMessage());
                }
            } else {
                erroresAlAutorizar.add(factura.getVtaDocumentoNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(1) : "Error desconocido."));
            }
            individual.setOperacionMensaje(factura.getVtaDocumentoNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(1) : "Error desconocido."));
            individual.setExtraInfo(factura.getId());
        }
        SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
        if (parametros != null) {
            parametros.setParFacturasEnProceso(false);
            empresaParametrosDao.actualizar(parametros);
        }
        envioCorreoService.notificarPorCorreoDocumentosNoAutorizadas(usuarioEmpresaReporteTO, erroresAlAutorizar, sisInfoTO, "[Atención] Ventas pendientes de autorización.");

    }
}
