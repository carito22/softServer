package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanPreavisos;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanPreavisosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PreavisosDaoImpl extends GenericDaoImpl<BanPreavisos, BanPreavisosPK> implements PreavisosDao {

	@Autowired
	private SucesoDao sucesoDao;

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public BanPreavisos getBanPreavisos(String empresa, String cuentaContable) throws Exception {
		String sql = "SELECT pre_empresa, pre_cuenta_contable, "
				+ "pre_nombre_archivo_generado, pre_fecha_revision_ultimo_cheque, "
				+ "usr_empresa, usr_codigo, usr_fecha_inserta " + "FROM banco.ban_preavisos " + "WHERE pre_empresa = '"
				+ empresa + "' " + "AND pre_cuenta_contable = '" + cuentaContable + "' "
				+ "ORDER BY usr_fecha_inserta DESC LIMIT 1;";
		return genericSQLDao.obtenerObjetoPorSql(sql, BanPreavisos.class);
	}

        @Override
	public Boolean insertarPreaviso(BanPreavisos banPreavisos, SisSuceso sisSuceso) throws Exception {
		sucesoDao.insertar(sisSuceso);
		insertar(banPreavisos);
		return true;
	}

        @Override
	public boolean eliminarBanPreaviso(BanPreavisos banPreavisos, SisSuceso sisSuceso) throws Exception {
		sucesoDao.insertar(sisSuceso);
		eliminar(banPreavisos);
		return true;
	}
}
