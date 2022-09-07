package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;

public interface PreLiquidacionDetalleDao extends GenericDao<PrdPreLiquidacionDetalle, Integer> {

	public void eliminarPorSql(Integer detSecuencial);
}
