/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.NotificacionService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesSistema;
import ec.com.todocompu.ShrimpSoftServer.util.UtilsMail;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftUtils.enums.TipoNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
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
public class ComprobantesRecibidosService {

    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private AnxComprobantesElectronicosRecibidosService anxComprobantesElectronicosRecibidosService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private NotificacionService notificacionService;

    @Async("asyncExecutor")
    public void insertarComprobantesElectronicosRecibidosLote(List<AnxComprobantesElectronicosRecibidos> listaEnviar, SisInfoTO sisInfoTO) throws Exception {
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();
        try {
            String respuesta = anxComprobantesElectronicosRecibidosService.insertarComprobantesElectronicosRecibidosLote(listaEnviar, sisInfoTO);
            if (respuesta == null || respuesta.equals("")) {
                respuesta = "|";
            }
            if (respuesta.substring(0, 1).equals("|")) {
            } else {
                String[] listaMensajes = respuesta.split("\\|");
                for (int i = 0; i < listaMensajes.length; i++) {
                    if (!listaMensajes[i].equals("")) {
                        listaMensajesEnviar.add(listaMensajes[i].substring(0));
                    }
                }
            }
            cerrarDocumentosRecibidos(listaMensajesEnviar, sisInfoTO);
        } catch (Exception e) {
            cerrarDocumentosRecibidos(listaMensajesEnviar, sisInfoTO);
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
    }

    public void cerrarDocumentosRecibidos(List<String> respuestas, SisInfoTO sisInfoTO) throws Exception {
        SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
        if (parametros != null) {
            parametros.setParDocumentosRecibidosEnProceso(false);
            empresaParametrosDao.actualizar(parametros);
        }
        notificarPorCorreoDocumentosRecibidos(respuestas, sisInfoTO);
    }

    public void notificarPorCorreoDocumentosRecibidos(List<String> listado, SisInfoTO usuario) throws Exception {
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, usuario.getEmpresa());
        SisUsuario sisUuario = usuarioService.obtenerPorId(usuario.getUsuario());
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = ConversionesSistema.completarDatosComprobanteElectronicoTO(sisEmpresaParametros);
        sisEmailComprobanteElectronicoTO.setNombreReceptor(sisUuario.getUsrNombre() + " " + sisUuario.getUsrApellido());
        sisEmailComprobanteElectronicoTO.setMailReceptor(sisUuario.getUsrEmail());
        SisNotificacion sisNotificacion = notificacionService.obtener();
        if (sisNotificacion != null) {
            sisEmailComprobanteElectronicoTO.setMailEmisor(sisNotificacion.getMailEmisor());
        }
        //Datos para AWS
        sisEmailComprobanteElectronicoTO.setTipoComprobante(TipoNotificacion.NOTIFICAR_COMPROBANTES_RECIBIDOS.getNombre());
        if (sisEmailComprobanteElectronicoTO.getMailEmisor() == null) {
            throw new GeneralException("Correo del emisor no registrado.");
        } else if (sisEmailComprobanteElectronicoTO.getMailReceptor() == null || sisEmailComprobanteElectronicoTO.getMailReceptor().compareTo("") == 0) {
            throw new GeneralException("Correo del receptor no registrado.");
        } else {
            String detalle = "<html><head><title></title></head><body>"
                    + "<br>Este correo electrónico es para notificarle que el proceso de comprobantes electrónicos recibidos ha finalizado.";
            String errores = "<br><strong>Si hay algun detalle en la operación, usted podrá visualizarlo a continuación.</strong> "
                    + "<br><br>------------------------------------------------------------------------------------------------------";
            String finalizar = "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                    + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>"
                    + "<br>";
            if (listado != null && !listado.isEmpty()) {
                for (String mensaje : listado) {
                    if (mensaje != null && !mensaje.equals("") && mensaje.charAt(0) == 'T') {
                        errores = errores + "<br>* --> " + mensaje.substring(1);
                    }
                }
                detalle = detalle + errores + finalizar;
            } else {
                detalle = detalle + finalizar;
            }
            UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, sisEmailComprobanteElectronicoTO.getMailReceptor(), "[Atención] Comprobantes electrónicos recibidos.", detalle, "", new ArrayList<>(), sisNotificacion);
        }
    }

}
