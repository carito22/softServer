package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaDetalle;

@Repository
public class PresupuestoPescaDetalleDaoImpl extends GenericDaoImpl<PrdPresupuestoPescaDetalle, Integer>
		implements PresupuestoPescaDetalleDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public void eliminarPorSql(Integer detSecuencial) {
		genericSQLDao.ejecutarSQL(
				"DELETE FROM produccion.prd_presupuesto_pesca_detalle_detalle WHERE det_secuencial=" + detSecuencial);
	}

}
