package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadesPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class UtilidadesPeriodoDaoImpl extends GenericDaoImpl<RhUtilidadesPeriodo, String>
		implements UtilidadesPeriodoDao {

	@Autowired
	private SucesoDao sucesoDao;

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public Boolean accionRhUtilidadesPeriodo(RhUtilidadesPeriodo rhUtilidadesPeriodo, SisSuceso sisSuceso, char accion)
			throws Exception {
		if (accion == 'I')
			insertar(rhUtilidadesPeriodo);
		if (accion == 'M')
			actualizar(rhUtilidadesPeriodo);
		if (accion == 'E')
			eliminar(rhUtilidadesPeriodo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean comprobarRhUtilidadesPeriodo(String utiEmpresa, String utiDescripcion) throws Exception {
		String sql = "SELECT COUNT(*)!=0 FROM recursoshumanos.rh_utilidades_periodo " + "WHERE (uti_empresa = '"
				+ utiEmpresa + "' and uti_descripcion = '" + utiDescripcion + "')";

		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

        @Override
	public List<RhUtilidadesPeriodoTO> getRhComboUtilidadesPeriodoTO(String empresa) throws Exception {
		String sql = "SELECT uti_descripcion, uti_desde, uti_hasta, uti_fecha_maxima_pago, uti_total_dias, uti_total_cargas, uti_total_pagar, uti_empresa "
				+ "FROM recursoshumanos.rh_utilidades_periodo where (uti_empresa = '" + empresa + "') ORDER BY uti_hasta DESC";

		return genericSQLDao.obtenerPorSql(sql, RhUtilidadesPeriodoTO.class);
	}

}
