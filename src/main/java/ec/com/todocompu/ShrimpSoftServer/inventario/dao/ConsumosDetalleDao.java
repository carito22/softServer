package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;

public interface ConsumosDetalleDao extends GenericDao<InvConsumosDetalle, Integer> {

	public List<InvListaDetalleConsumoTO> getListaInvConsumoDetalleTO(String empresa, String periodo, String motivo,
			String numeroConsumo) throws Exception;

	public void eliminarPorSql(Integer detSecuencial);

}
