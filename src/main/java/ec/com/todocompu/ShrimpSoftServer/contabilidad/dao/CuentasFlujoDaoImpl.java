package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class CuentasFlujoDaoImpl extends GenericDaoImpl<ConCuentasFlujo, ConCuentasFlujoPK> implements CuentasFlujoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sucesoDao;

        @Override
	public boolean modificarConCuentaFlujo(ConCuentasFlujo conCuentasFlujo, SisSuceso sisSuceso) throws Exception {
		actualizar(conCuentasFlujo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarConCuentaFlujoLlavePrincipal(ConCuentasFlujo conCuentasFlujoEliminar,
			ConCuentasFlujo conCuentasFlujo, SisSuceso sisSuceso) throws Exception {
		eliminar(conCuentasFlujoEliminar);
		actualizar(conCuentasFlujo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarListaConCuentaFlujo(List<ConCuentasFlujo> listaConCuentasFlujo, SisSuceso sisSuceso)
			throws Exception {
		for (int i = 0; i < listaConCuentasFlujo.size(); i++)
			actualizar(listaConCuentasFlujo.get(i));

		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarConCuentaFlujo(ConCuentasFlujo conCuentasFlujo, SisSuceso sisSuceso) throws Exception {
		eliminar(conCuentasFlujo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean insertarConCuentaFlujo(ConCuentasFlujo conCuentasFlujo, SisSuceso sisSuceso) throws Exception {
		insertar(conCuentasFlujo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(String empresa) throws Exception {
		String sql = "SELECT * FROM contabilidad.con_cuentas_flujo WHERE (cta_empresa = '" + empresa + "')";

		return genericSQLDao.obtenerPorSql(sql, ConCuentasFlujoTO.class);
	}

        @Override
	public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(String empresa, String buscar) throws Exception {
		String sql = "SELECT flu_empresa, flu_codigo, REPEAT(' ', "
				+ "CHAR_LENGTH(TRIM(BOTH ' ' FROM flu_codigo))) || flu_detalle "
				+ "flu_detalle, flu_activo, null usr_codigo, null usr_fecha_inserta FROM contabilidad.con_cuentas_flujo WHERE flu_empresa = ('"
				+ empresa + "') AND (flu_codigo || UPPER(flu_detalle) LIKE TRANSLATE(' ' || CASE WHEN ('" + buscar
				+ "') = '' THEN '~' ELSE ('" + buscar + "') END || ' ', ' ', '%')) ORDER BY flu_codigo";

		return genericSQLDao.obtenerPorSql(sql, ConCuentasFlujoTO.class);
	}

}
