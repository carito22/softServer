package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;

@Repository
public class PreLiquidacionDetalleDaoImpl extends GenericDaoImpl<PrdPreLiquidacionDetalle, Integer>
		implements PreLiquidacionDetalleDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public void eliminarPorSql(Integer detSecuencial) {
		genericSQLDao
				.ejecutarSQL("DELETE FROM produccion.prd_preliquidacion_detalle WHERE det_secuencial=" + detSecuencial);
	}

}
