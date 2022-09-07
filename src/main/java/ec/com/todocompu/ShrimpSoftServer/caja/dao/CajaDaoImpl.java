package ec.com.todocompu.ShrimpSoftServer.caja.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajCaja;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajCajaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class CajaDaoImpl extends GenericDaoImpl<CajCaja, CajCajaPK> implements CajaDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sisSucesoDao;

        @Override
	public List<CajCajaTO> getListadoCajCajaTO(String empresa) throws Exception {
                String sql = "SELECT caj_caja.*, usr_apellido || ' ' || usr_nombre caja_usuario_nombre FROM caja.caj_caja INNER JOIN sistemaweb.sis_usuario "
                        + "ON caj_caja.caja_usuario_responsable = sis_usuario.usr_codigo "
                        + "WHERE caja_empresa = ('" + empresa + "') "
                        + "ORDER BY caja_usuario_responsable";
		return genericSQLDao.obtenerPorSql(sql, CajCajaTO.class);
	}

        @Override
	public List<CajCajaComboTO> getCajCajaComboTO(String empresa) throws Exception {
		String sql = "SELECT caja_codigo, ''::text caja_nombre FROM caja.caj_caja WHERE " + "caja_empresa = ('" + empresa
				+ "') ORDER BY caja_usuario_nombre";
		return genericSQLDao.obtenerPorSql(sql, CajCajaComboTO.class);
	}

        @Override
	public CajCajaTO getCajCajaTO(String empresa, String usuarioCodigo) throws Exception {
		String sql = "SELECT caj_caja.*, usr_apellido || ' ' || usr_nombre caja_usuario_nombre FROM caja.caj_caja INNER JOIN sistemaweb.sis_usuario "
                        + "ON caj_caja.caja_usuario_responsable = sis_usuario.usr_codigo "
                        + "WHERE caja_empresa= '" + empresa + "' "
                        + "AND caja_usuario_responsable = '" + usuarioCodigo + "';";

                return genericSQLDao.obtenerObjetoPorSql(sql, CajCajaTO.class);
	}

        @Override
	public void accionCajCaja(CajCaja cajCajaAux, CajCaja cajCaja, SisSuceso sisSuceso, String accion)
			throws Exception {
		if (accion.equals("I")) {
			sisSucesoDao.insertar(sisSuceso);
			insertar(cajCaja);
		}
		if (accion.equals("U")) {
			sisSucesoDao.insertar(sisSuceso);
			actualizar(cajCaja);
		}
		if (accion.equals("D")) {
			sisSucesoDao.insertar(sisSuceso);
			eliminar(cajCaja);
		}
	}

}
