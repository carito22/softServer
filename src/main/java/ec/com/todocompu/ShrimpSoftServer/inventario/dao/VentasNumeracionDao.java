package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasNumeracionPK;

public interface VentasNumeracionDao extends GenericDao<InvVentasNumeracion, InvVentasNumeracionPK> {

	public List<InvNumeracionVentaTO> getListaInvNumeracionVentaTO(String empresa, String periodo, String motivo)
			throws Exception;

}
