package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosNumeracionPK;

@Repository
public class ConsumosNumeracionDaoImpl extends GenericDaoImpl<InvConsumosNumeracion, InvConsumosNumeracionPK>
		implements ConsumosNumeracionDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<InvNumeracionConsumoTO> getListaInvNumeracionConsumoTO(String empresa, String periodo, String motivo)
			throws Exception {
		String sql = "SELECT *, row_number() over (partition by num_empresa order by num_empresa, num_periodo, num_motivo) num_id FROM inventario.inv_consumos_numeracion "
				+ "WHERE (num_empresa = '" + empresa + "') AND CASE WHEN ('" + periodo
				+ "') = '' THEN TRUE ELSE (num_periodo = '" + periodo + "') END AND CASE WHEN ('" + motivo
				+ "') = '' THEN TRUE ELSE num_motivo = ('" + motivo + "') END";

		return genericSQLDao.obtenerPorSql(sql, InvNumeracionConsumoTO.class);
	}

}
