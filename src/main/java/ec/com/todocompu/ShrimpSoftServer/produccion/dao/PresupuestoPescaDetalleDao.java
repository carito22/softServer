package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaDetalle;

public interface PresupuestoPescaDetalleDao extends GenericDao<PrdPresupuestoPescaDetalle, Integer> {

	public void eliminarPorSql(Integer detSecuencial);

}
