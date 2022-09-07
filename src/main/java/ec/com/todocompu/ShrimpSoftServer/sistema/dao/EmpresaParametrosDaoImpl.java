package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;

@Repository
public class EmpresaParametrosDaoImpl extends GenericDaoImpl<SisEmpresaParametros, String>
		implements EmpresaParametrosDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public Integer getColumnasEstadosFinancieros(String empCodigo) throws Exception {
		String sql = "SELECT par_columnas_estados_financieros FROM sistemaweb.sis_empresa_parametros "
				+ "WHERE (emp_codigo = '" + empCodigo + "');";
		return (Integer) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

}
