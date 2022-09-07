package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasNumeracionPK;

@Repository
public class VentasNumeracionDaoImpl extends GenericDaoImpl<InvVentasNumeracion, InvVentasNumeracionPK>
		implements VentasNumeracionDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<InvNumeracionVentaTO> getListaInvNumeracionVentaTO(String empresa, String periodo, String motivo)
			throws Exception {
		String sql = "SELECT *, row_number() over (partition by num_empresa order by num_empresa, num_periodo, num_motivo) num_id FROM inventario.inv_ventas_numeracion "
				+ "WHERE (num_empresa = '" + empresa + "') AND CASE WHEN ('" + periodo
				+ "') = '' THEN TRUE ELSE (num_periodo = '" + periodo + "') END AND CASE WHEN ('" + motivo
				+ "') = '' THEN TRUE ELSE num_motivo = ('" + motivo + "') END ";

		return genericSQLDao.obtenerPorSql(sql, InvNumeracionVentaTO.class);
	}

}
