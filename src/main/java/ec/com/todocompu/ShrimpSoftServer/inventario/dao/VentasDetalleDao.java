package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalle;

public interface VentasDetalleDao extends GenericDao<InvVentasDetalle, Integer> {

	public List<InvListaDetalleVentasTO> getListaInvVentasDetalleTO(String empresa, String periodo, String motivo,
			String numeroVentas) throws Exception;

}
