package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class XiiiSueldoPeriodoDaoImpl extends GenericDaoImpl<RhXiiiSueldoPeriodo, Integer>
		implements XiiiSueldoPeriodoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public RhXiiiSueldoPeriodo buscarRhXiiiSueldoPeriodo(Integer secucenciaPeriodo) throws Exception {
		return obtenerPorId(RhXiiiSueldoPeriodo.class, secucenciaPeriodo);
	}

        @Override
	public Boolean accionRhXiiiSueldoPeriodo(RhXiiiSueldoPeriodo xiiiSueldoPeriodo, SisSuceso sisSuceso, char accion)
			throws Exception {
		if (accion == 'I')
			insertar(xiiiSueldoPeriodo);
		if (accion == 'M') {
			actualizar(xiiiSueldoPeriodo);
		}
		if (accion == 'E')
			eliminar(xiiiSueldoPeriodo);
		//sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public List<RhXiiiSueldoPeriodoTO> getRhComboXiiiSueldoPeriodoTO() throws Exception {
		String sql = "SELECT xiii_descripcion, xiii_desde, xiii_hasta, xiii_fecha_maxima_pago, xiii_secuencial "
				+ "FROM recursoshumanos.rh_xiii_sueldo_periodo ORDER BY xiii_secuencial DESC";

		return genericSQLDao.obtenerPorSql(sql, RhXiiiSueldoPeriodoTO.class);
	}

        @Override
	public RhXiiiSueldoPeriodoTO buscarRhXiiiSueldoPeriodoTO(String descripcion) throws Exception {
		descripcion = descripcion == null ? descripcion : "'" + descripcion + "'";
		String sql = "SELECT * FROM recursoshumanos.rh_xiii_sueldo_periodo WHERE (xiii_descripcion = " + descripcion
				+ ")";

		return genericSQLDao.obtenerObjetoPorSql(sql, RhXiiiSueldoPeriodoTO.class);
	}

        @Override
	public boolean comprobarRhXiiiSueldoPeriodoTO(String descripcion) throws Exception {
		String sql = "SELECT COUNT(*)!=0 FROM recursoshumanos.rh_xiii_sueldo_periodo " + "WHERE (xiii_descripcion = '"
				+ descripcion + "')";

		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

}
