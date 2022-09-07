package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasNumeracionPK;

public interface ComprasNumeracionDao extends GenericDao<InvComprasNumeracion, InvComprasNumeracionPK> {

	public List<InvNumeracionCompraTO> getListaInvNumeracionCompraTO(String empresa, String periodo, String motivo)
			throws Exception;

}
