package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;

public interface EmpleadoDescuentosFijosDao extends GenericDao<RhEmpleadoDescuentosFijos, Integer> {
	public List<RhEmpleadoDescuentosFijos> getRhEmpleadoDescuentosFijos(String empresa, String empleado)
			throws Exception;

	public void eliminarPorSql(Integer detSecuencial);
}
