package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;

@Repository
public class EmpleadoDescuentosFijosDaoImpl extends GenericDaoImpl<RhEmpleadoDescuentosFijos, Integer>
		implements EmpleadoDescuentosFijosDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<RhEmpleadoDescuentosFijos> getRhEmpleadoDescuentosFijos(String empresa, String empleado)
			throws Exception {
		List<RhEmpleadoDescuentosFijos> rhEmpleadoDescuentosFijos = obtenerPorHql(
				"SELECT a FROM RhEmpleadoDescuentosFijos a WHERE "
						+ "a.rhEmpleado.rhEmpleadoPK.empEmpresa=?1 AND a.rhEmpleado.rhEmpleadoPK.empId=?2 ORDER BY a.descSecuencial",
				new Object[] { empresa, empleado });
		return rhEmpleadoDescuentosFijos;
	}

        @Override
	public void eliminarPorSql(Integer detSecuencial) {
		genericSQLDao.ejecutarSQL(
				"DELETE FROM recursoshumanos.rh_empleado_descuentos_fijos WHERE desc_secuencial=" + detSecuencial);
	}
}
