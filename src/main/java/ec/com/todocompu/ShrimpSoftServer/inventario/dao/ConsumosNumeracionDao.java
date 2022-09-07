package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosNumeracionPK;

public interface ConsumosNumeracionDao extends GenericDao<InvConsumosNumeracion, InvConsumosNumeracionPK> {

	public List<InvNumeracionConsumoTO> getListaInvNumeracionConsumoTO(String empresa, String periodo, String motivo)
			throws Exception;

}
