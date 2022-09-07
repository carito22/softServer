package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class GrupoDaoImpl extends GenericDaoImpl<SisGrupo, SisGrupoPK> implements GrupoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sucesoDao;

        @Override
	public SisGrupoTO sisGrupoUsuariosTO(String infEmpresa, String infUsuario) throws Exception {
		String sql = "SELECT sis_grupo.* FROM sistemaweb.sis_usuario_detalle INNER JOIN sistemaweb.sis_grupo "
				+ "ON sis_usuario_detalle.gru_empresa = sis_grupo.gru_empresa AND "
				+ "sis_usuario_detalle.gru_codigo = sis_grupo.gru_codigo " + "WHERE sis_usuario_detalle.det_empresa='"
				+ infEmpresa + "' " + "AND sis_usuario_detalle.det_usuario='" + infUsuario + "'";
		return genericSQLDao.obtenerObjetoPorSql(sql, SisGrupoTO.class);
	}

        @Override
	public List<SisGrupoTO> getListaSisGrupo(String empresa) throws Exception {
		String var = "('" + empresa.trim() + "')";
		String sql = "select * FROM sistemaweb.sis_grupo "
				+ "where gru_empresa = " + var + " ORDER BY gru_nombre";

		return genericSQLDao.obtenerPorSql(sql, SisGrupoTO.class);
	}

        @Override
	public List<SisGrupoTO> getSisGrupoTOs(String empresa) throws Exception {
		String var = "('" + empresa.trim() + "')";
		String sql = "SELECT * FROM sistemaweb.sis_grupo " + "WHERE (gru_empresa = " + var + ") order by gru_nombre";
		return genericSQLDao.obtenerPorSql(sql, SisGrupoTO.class);
	}

        @Override
	public boolean insertarSisGrupo(SisGrupo sisGrupo, SisSuceso sisSuceso) throws Exception {
		insertar(sisGrupo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarSisGrupo(SisGrupo sisGrupo, SisSuceso sisSuceso) throws Exception {
		actualizar(sisGrupo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarSisGrupo(SisGrupo sisGrupo, SisSuceso sisSuceso) throws Exception {
		eliminar(sisGrupo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}
}
