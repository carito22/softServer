package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface VentaElectronicaService {

	public String accionAnxVentaElectronica(AnxVentaElectronicaTO anxVentaElectronicaTO, char accion,
			SisInfoTO sisInfoTO) throws Exception;

	public List<AnxListaVentasPendientesTO> getListaVentasPendientes(String empresa)
			throws Exception;

	public List<AnxListaVentasPendientesTO> getListaVentasPendientesAutorizacionAutomatica(String empresa)
			throws Exception;

	public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eMotivo, String eNumero)
			throws Exception;

	public boolean comprobarAnxVentaElectronica(String empresa, String periodo, String motivo, String numero)
			throws Exception;

	public String comprobarAnxVentaElectronicaAutorizacion(String empresa, String periodo, String motivo, String numero)
			throws Exception;

        public List<AnxVentaElectronicaNotificaciones> listarNotificacionesVentasElectronicas(String empresa, String motivo, String periodo, String numero) throws Exception;

}
