package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDetalle;

public interface CompraDetalleDao extends GenericDao<AnxCompraDetalle, Integer> {

	public List<AnxCompraDetalleTO> getAnexoCompraDetalleTO(String empresa, String periodo, String motivo,
			String numeroCompra) throws Exception;

}
