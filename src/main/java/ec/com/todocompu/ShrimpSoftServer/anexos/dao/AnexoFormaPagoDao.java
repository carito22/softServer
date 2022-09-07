package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.Date;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormaPago;

public interface AnexoFormaPagoDao extends GenericDao<AnxFormaPago, String> {

	public List<AnxFormaPagoTO> getAnexoFormaPagoTO(Date fechaFactura) throws Exception;

}
