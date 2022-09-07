package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface VentaElectronicaDao extends GenericDao<AnxVentaElectronica, Integer> {

	public AnxVentaElectronica buscarAnxVentaElectronica(String empresa, String periodo, String motivo, String numero)
			throws Exception;

	public Boolean accionAnxVentaElectronica(AnxVentaElectronica anxVentaElectronica, SisSuceso sisSuceso, char accion)
			throws Exception;

	public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eMotivo, String eNumero)
			throws Exception;

	public boolean comprobarAnxVentaElectronica(String empresa, String periodo, String motivo, String numero)
			throws Exception;

	public String comprobarAnxVentaElectronicaAutorizacion(String empresa, String periodo, String motivo,
			String numero) throws Exception;

	public List<AnxListaVentasPendientesTO> getListaVentasPendientes(String empresa) throws Exception;
        
        public List<AnxListaVentasPendientesTO> getListaVentasPendientesAutorizacionAutomatica(String empresa) throws Exception;

}
