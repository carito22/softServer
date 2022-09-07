package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;

@Transactional
public interface CompraElectronicaService {

    public String accionAnxCompraElectronica(AnxCompraElectronicaTO anxCompraElectronicaTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public String getXmlComprobanteRetencion(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception;

    public boolean comprobarAnxCompraElectronica(String empresa, String periodo, String motivo, String numero) throws Exception;

    public boolean comprobarRetencionAutorizadaProcesamiento(String empresa, String periodo, String motivo, String numero) throws Exception;

    public String enviarEmailComprobantesElectronicos(String empresa, String ePeriodo, String eMotivo, String eNumero, String claveAcceso, String nombreReporteJasper, String XmlString, SisInfoTO sisInfoTO) throws Exception;

    public List<AnxListaLiquidacionComprasPendientesTO> getListaLiquidacionCompraPendientes(String empresa) throws Exception;

    public String getXmlLiquidacionCompras(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception;

    public List<AnxCompraElectronicaNotificaciones> listarNotificacionesRetencionesElectronicas(String empresa, String motivo, String periodo, String numero) throws Exception;

}
