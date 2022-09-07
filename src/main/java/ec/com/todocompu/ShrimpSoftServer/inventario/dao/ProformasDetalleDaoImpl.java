package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleProformasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasDetalle;

@Repository
public class ProformasDetalleDaoImpl extends GenericDaoImpl<InvProformasDetalle, Integer>
		implements ProformasDetalleDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<InvListaDetalleProformasTO> getListaInvProformasDetalleTO(String empresa, String periodo, String motivo,
			String numeroProformas) throws Exception {
		String sql = "SELECT * FROM inventario.fun_listado_proformas_detalle('" + empresa + "', " + "'" + periodo
				+ "', '" + motivo + "', '" + numeroProformas + "')";

		return genericSQLDao.obtenerPorSql(sql, InvListaDetalleProformasTO.class);

	}

}
