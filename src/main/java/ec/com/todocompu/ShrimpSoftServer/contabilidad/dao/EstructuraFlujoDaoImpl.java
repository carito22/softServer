package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConEstructuraFlujo;

@Repository
public class EstructuraFlujoDaoImpl extends GenericDaoImpl<ConEstructuraFlujo, String> implements EstructuraFlujoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<ConEstructuraFlujoTO> getListaConEstructuraFlujoTO(String empresa) throws Exception {
		String sql = "SELECT * FROM contabilidad.con_estructura_flujo WHERE " + "est_empresa = ('" + empresa + "')";

		return genericSQLDao.obtenerPorSql(sql, ConEstructuraFlujoTO.class);
	}

}
