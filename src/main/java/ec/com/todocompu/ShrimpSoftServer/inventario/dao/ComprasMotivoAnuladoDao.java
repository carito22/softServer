package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoAnulacion;

public interface ComprasMotivoAnuladoDao extends GenericDao<InvComprasMotivoAnulacion, Integer> {

	public InvComprasMotivoAnulacion buscarCompraMotivo(String empresa, String periodo, String motivo, String numero)
			throws Exception;

}
