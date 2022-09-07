package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;

@Repository
public class NumeracionDaoImpl extends GenericDaoImpl<ConNumeracion, ConNumeracionPK> implements NumeracionDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<ConNumeracionTO> getListaConNumeracionTO(String empresa, String periodo, String tipo) throws Exception {
		String sql = "SELECT * FROM contabilidad.con_numeracion WHERE (num_empresa = '" + empresa + "') "
				+ "AND CASE WHEN ('" + periodo + "') = '' THEN TRUE ELSE (num_periodo = '" + periodo + "') END "
				+ "AND CASE WHEN ('" + tipo + "') = '' THEN TRUE ELSE num_tipo = ('" + tipo + "') END";

		return genericSQLDao.obtenerPorSql(sql, ConNumeracionTO.class);
	}

}
