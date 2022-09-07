package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasEspeciales;

@Repository
public class CuentasEspecialesDaoImpl extends GenericDaoImpl<ConCuentasEspeciales, String>
		implements CuentasEspecialesDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public Boolean buscarConDetallesActivosBiologicos(String empresa) throws Exception {
		String sql = "SELECT count(cta_activo_biologico) " + "FROM contabilidad.con_cuentas_especiales "
				+ "WHERE cta_empresa = '" + empresa + "'";

		return (((int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql))) == 1);
	}

}
