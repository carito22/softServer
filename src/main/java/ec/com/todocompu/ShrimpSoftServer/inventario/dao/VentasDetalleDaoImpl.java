package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalle;

@Repository
public class VentasDetalleDaoImpl extends GenericDaoImpl<InvVentasDetalle, Integer> implements VentasDetalleDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Transactional
        @Override
	public List<InvListaDetalleVentasTO> getListaInvVentasDetalleTO(String empresa, String periodo, String motivo,
			String numeroVentas) throws Exception {
		String sql = "SELECT * FROM inventario.fun_listado_ventas_detalle('" + empresa + "', " + "'" + periodo + "', '"
				+ motivo + "', '" + numeroVentas + "')";

		return genericSQLDao.obtenerPorSql(sql, InvListaDetalleVentasTO.class);

	}
}
