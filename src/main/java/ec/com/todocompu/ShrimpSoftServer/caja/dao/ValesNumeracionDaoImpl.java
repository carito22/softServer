package ec.com.todocompu.ShrimpSoftServer.caja.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesNumeracionPK;

@Repository
public class ValesNumeracionDaoImpl extends GenericDaoImpl<CajValesNumeracion, CajValesNumeracionPK>
		implements ValesNumeracionDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public int buscarConteoUltimaNumeracionCajaVale(String empCodigo, String perCodigo, String motCodigo)
			throws Exception {
		try {
			String sql = "SELECT num_secuencia FROM caja.caj_vales_numeracion WHERE num_empresa = ('" + empCodigo
					+ "') AND num_periodo = ('" + perCodigo + "') AND num_motivo = ('" + motCodigo + "') ";
			return Integer.parseInt((String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql)));
		} catch (Exception e) {
			return 0;
		}
	}

}
