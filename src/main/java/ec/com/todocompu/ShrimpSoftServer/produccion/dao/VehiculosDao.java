package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdVehiculos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdVehiculosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface VehiculosDao extends GenericDao<PrdVehiculos, PrdVehiculosPK> {

	public Boolean accionPrdVehiculos(PrdVehiculos prdVehiculos, PrdPiscina prdPiscina, SisSuceso sisSuceso,
			char accion) throws Exception;

}
