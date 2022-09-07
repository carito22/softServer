package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;

@Transactional
public interface EmpleadoDescuentoFijoService {

	public List<RhEmpleadoDescuentosFijos> getEmpleadoDescuentosFijos(String empresa, String empleado)
			throws Exception;

}
