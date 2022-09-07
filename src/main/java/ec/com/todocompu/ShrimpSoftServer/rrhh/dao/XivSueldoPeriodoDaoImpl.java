package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class XivSueldoPeriodoDaoImpl extends GenericDaoImpl<RhXivSueldoPeriodo, Integer>
		implements XivSueldoPeriodoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public RhXivSueldoPeriodo buscarRhXivSueldoPeriodo(Integer secucenciaPeriodo) throws Exception {
		return obtenerPorId(RhXivSueldoPeriodo.class, secucenciaPeriodo);
	}

        @Override
	public Boolean accionRhXivSueldoPeriodo(RhXivSueldoPeriodo xivSueldoPeriodo, SisSuceso sisSuceso, char accion)
			throws Exception {
		if (accion == 'I')
			insertar(xivSueldoPeriodo);
		if (accion == 'M')
			actualizar(xivSueldoPeriodo);
		if (accion == 'E')
			eliminar(xivSueldoPeriodo);
		return true;
	}

        @Override
	public List<RhXivSueldoPeriodoTO> getRhComboXivSueldoPeriodoTO() throws Exception {
		String sql = "SELECT xiv_secuencial, xiv_descripcion, xiv_desde, xiv_hasta, xiv_fecha_maxima_pago, "
                                + "0.00 par_salario_minimo_vital "
				+ "FROM recursoshumanos.rh_xiv_sueldo_periodo ORDER BY xiv_secuenciaL DESC";

		return genericSQLDao.obtenerPorSql(sql, RhXivSueldoPeriodoTO.class);
	}

        @Override
	public RhXivSueldoPeriodoTO buscarRhXivSueldoPeriodoTO(String descripcion) throws Exception {
		descripcion = descripcion == null ? descripcion : "'" + descripcion + "'";
		String sql = "SELECT * FROM recursoshumanos.rh_xiv_sueldo_periodo WHERE (xiv_descripcion = " + descripcion + ");";
		return genericSQLDao.obtenerObjetoPorSql(sql, RhXivSueldoPeriodoTO.class);

	}

        @Override
	public boolean comprobarRhXivSueldoPeriodoTO(String descripcion) throws Exception {
		String sql = "SELECT COUNT(*)!=0 FROM recursoshumanos.rh_xiv_sueldo_periodo " + "WHERE (xiv_descripcion = '" + descripcion + "')";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

}
