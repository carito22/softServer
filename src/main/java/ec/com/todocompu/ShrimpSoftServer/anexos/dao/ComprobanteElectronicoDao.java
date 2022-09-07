package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteElectronico;

public interface ComprobanteElectronicoDao extends GenericDao<ComprobanteElectronico, String> {

	public List<ComprobanteElectronico> obtenerDocumentosPorCedulaRucMesAnio(String cedulaRuc, String mes, String anio);

}
