package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCuentascontables;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class CuentasContablesDaoImpl extends GenericDaoImpl<AnxCuentascontables, Integer>
		implements CuentasContablesDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sisSucesoDao;

	@Override
	public Boolean actualizarAnxCuentascontables(AnxCuentascontables anxCuentascontables, SisSuceso sisSuceso)
			throws Exception {
		actualizar(anxCuentascontables);
		sisSucesoDao.insertar(sisSuceso);
		return true;
	}

	@Override
	public AnxCuentasContablesTO getCuentasContablesTO(String empresa) throws Exception {
		String sql = "SELECT * FROM anexo.anx_cuentascontables " + "WHERE cta_empresa = ('" + empresa + "');";
		return genericSQLDao.obtenerObjetoPorSql(sql, AnxCuentasContablesTO.class);
	}

	@Override
	public boolean comprobarAnxCuentascontables(String empresa) throws Exception {
		String sql = "SELECT COUNT(*)!=0 " + "FROM anexo.anx_cuentascontables " + "WHERE (cta_empresa = '" + empresa
				+ "');";
		return (boolean) genericSQLDao.obtenerObjetoPorSql(sql);
	}

	@Override
	public AnxCuentasContablesTO getAnxCuentasContablesTO(String empresa, String nombreCuenta) throws Exception {
		String sql = "";
		if (nombreCuenta == null) {
			sql = "SELECT * FROM anexo.anx_cuentascontables " + "WHERE (cta_empresa = '" + empresa + "');";
		} else {
			//sql = "SELECT * FROM anexo.fun_nombres_cuentas_parametrizacion('" + empresa + "');";
                        sql = "SELECT * FROM anexo.anx_cuentascontables " + "WHERE (cta_empresa = '" + empresa + "');";
		}
		return genericSQLDao.obtenerObjetoPorSql(sql, AnxCuentasContablesTO.class);
	}
}
