package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdVehiculos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdVehiculosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class VehiculosDaoImpl extends GenericDaoImpl<PrdVehiculos, PrdVehiculosPK> implements VehiculosDao {

	@Autowired
	private SucesoDao sisSucesoDao;
	@Autowired
	private PiscinaDao piscinaDao;

        @Override
	public Boolean accionPrdVehiculos(PrdVehiculos prdVehiculos, PrdPiscina prdPiscina, SisSuceso sisSuceso,
			char accion) throws Exception {
		if (accion == 'I') {
			piscinaDao.insertar(prdPiscina);
			insertar(prdVehiculos);
		}
		if (accion == 'M') {
			actualizar(prdVehiculos);
		}
		if (accion == 'E') {
			piscinaDao.eliminar(prdPiscina);
			eliminar(prdVehiculos);
		}
		sisSucesoDao.insertar(sisSuceso);

		return true;
	}

}
