package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdVehiculosTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface VehiculosService {

	public String accionPrdVehiculos(PrdVehiculosTO prdVehiculosTO, char accion, SisInfoTO sisInfoTO) throws Exception;

}
