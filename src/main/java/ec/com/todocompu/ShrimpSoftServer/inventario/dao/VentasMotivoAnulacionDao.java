package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoAnulacion;

public interface VentasMotivoAnulacionDao extends GenericDao<InvVentasMotivoAnulacion, Integer> {

	public InvVentasMotivoAnulacion buscarVentaMotivo(String empresa, String periodo, String motivo, String numero)
			throws Exception;

}
