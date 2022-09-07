package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleProformasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasDetalle;

public interface ProformasDetalleDao extends GenericDao<InvProformasDetalle, Integer> {

	public List<InvListaDetalleProformasTO> getListaInvProformasDetalleTO(String empresa, String periodo, String motivo,
			String numeroProformas) throws Exception;

}
