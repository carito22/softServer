package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;

public interface CompraFormaPagoDao extends GenericDao<AnxCompraFormaPago, Integer> {

	public List<AnxCompraFormaPagoTO> getAnexoCompraFormaPagoTO(String empresa, String periodo, String motivo,
			String numero) throws Exception;

	public List<AnxCompraFormaPago> getAnexoCompraFormaPago(String empresa, String periodo, String motivo,
			String numeroCompra) throws Exception;

}
