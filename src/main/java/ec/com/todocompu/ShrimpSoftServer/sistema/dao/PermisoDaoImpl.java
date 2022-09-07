
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisMenu;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPermisoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPermiso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPermisoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PermisoDaoImpl extends GenericDaoImpl<SisPermiso, SisPermisoPK> implements PermisoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sucesoDao;

        @Override
	public List<SisPermisoTO> getListaPermisoTO(String grupoCodigo, String empCodigo) throws Exception {
		if (grupoCodigo.equals("ADM")) {
			grupoCodigo = grupoCodigo + "~";
		} else {
			grupoCodigo = "~" + grupoCodigo + "~";
		}

		String sql = "SELECT per_empresa,per_modulo, per_menu, per_item, "
				+ "per_item_etiqueta, per_secuencia, per_usuarios,emp_codigo, CASE " + "WHEN POSITION(('" + grupoCodigo
				+ "') IN per_usuarios)>0 " + "THEN TRUE ELSE FALSE END AS per_activo "
				+ "FROM sistemaweb.sis_permiso WHERE emp_codigo = ('" + empCodigo + "') "
				+ "ORDER BY per_modulo, per_menu, per_secuencia";
		return genericSQLDao.obtenerPorSql(sql, SisPermisoTO.class);
	}

        @Override
	public List<String> getListaPermisoModulo(String empCodigo, String grupoCodigo) throws Exception {
		if (grupoCodigo.equals("ADM")) {
			grupoCodigo = grupoCodigo + "~";
		} else {
			grupoCodigo = "~" + grupoCodigo + "~";
		}

		String sql = "SELECT DISTINCT ON (per_modulo) per_modulo FROM sistemaweb.fun_usuario_permiso('" + empCodigo
				+ "','" + grupoCodigo + "') WHERE POSITION(('" + grupoCodigo
				+ "') IN per_usuarios)>0 ORDER BY per_modulo;";
		return (List<String>) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql));
	}

        @Override
	public List<SisMenu> getListaPermisoTO(String empCodigo, String grupoCodigo, String perModulo) throws Exception {
		if (grupoCodigo.equals("ADM")) {
			grupoCodigo = grupoCodigo + "~";
		} else {
			grupoCodigo = "~" + grupoCodigo + "~";
		}

		String sql = "SELECT per_modulo, per_menu, per_item, per_item_etiqueta, POSITION(('" + grupoCodigo
				+ "') IN per_usuarios)>0 per_estado, '' per_url, '' per_icono FROM sistemaweb.fun_usuario_permiso('"
				+ empCodigo + "','" + grupoCodigo + "') WHERE per_modulo=('" + perModulo + "')";
		return genericSQLDao.obtenerPorSql(sql, SisMenu.class);
	}

        @Override
	public List<SisMenu> getMenuWeb(String usuario, boolean produccion) {
		String sql = "SELECT DISTINCT ON (per_modulo, per_menu, per_item, per_secuencia) per_modulo, per_menu, per_item, per_item_etiqueta, per_url, per_icono, false per_estado "
				+ "FROM sistemaweb.sis_permiso INNER JOIN sistemaweb.sis_usuario_detalle "
				+ "ON sis_permiso.emp_codigo = sis_usuario_detalle.gru_empresa AND "
				+ "POSITION(sis_usuario_detalle.gru_codigo||'~' IN sis_permiso.per_usuarios)!=0 AND "
				+ "sis_usuario_detalle.usr_codigo='" + usuario + "' WHERE "
				+ (produccion ? "per_modulo='WEB PRODUCCION' " : "POSITION('WEB' IN per_modulo)!=0 ")
				+ "and per_url notnull order by 1, 2, per_secuencia, 3;";
		return genericSQLDao.obtenerPorSql(sql, SisMenu.class);
	}

        @Override
	public boolean modificarSisPermiso(List<SisPermiso> sisPermisos, SisSuceso sisSuceso) throws Exception {
		for (SisPermiso sisPermiso : sisPermisos) {
			actualizar(sisPermiso);
		}
		sucesoDao.insertar(sisSuceso);
		return true;
	}

}
