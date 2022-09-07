/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface GuiaElectronicaService {

    public AnxGuiaRemisionElectronica buscarAnxGuiaRemisionElectronica(String empresa, String periodo, String numero) throws Exception;

    public List<AnxListaGuiaRemisionPendientesTO> getListaGuiaRemisionPendientesTO(String empresa) throws Exception;

    public List<AnxListaGuiaRemisionElectronicaTO> getListaAnxListaGuiaRemisionElectronicaTO(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxGuiaRemisionElectronicaNotificaciones> listarNotificacionesGuiasElectronicas(String empresa, String periodo, String numero) throws Exception;

    public String accionAnxGuiaRemisionTOElectronica(AnxGuiaRemisionElectronicaTO anxGuiaRemisionElectronicaTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public boolean comprobarAnxGuiaElectronica(String empresa, String periodo, String numero) throws Exception;

    public String enviarEmailComprobantesElectronicos(String empresa, String ePeriodo, String eNumero, String claveAcceso, String nombreReporteJasper, String XmlString) throws Exception;

    public String getXmlGuiaRemision(String empresa, String ePeriodo, String eNumero) throws Exception;

    public InvEntidadTransaccionTO obtenerClienteDeGuia(String empresa, String periodo, String numero) throws Exception;

}
