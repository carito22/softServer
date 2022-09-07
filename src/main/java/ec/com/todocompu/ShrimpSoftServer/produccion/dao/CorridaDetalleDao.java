package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;

public interface CorridaDetalleDao extends GenericDao<PrdCorridaDetalle, Long> {

	@Transactional
	public List<PrdCorridaDetalle> getCorridaDetalleOrigenPorCorrida(String empresa, String sector, String piscina,
			String corrida);

	@Transactional
	public List<PrdCorridaDetalle> getCorridaDetalleDestinoPorCorrida(String empresa, String sector, String piscina,
			String corrida);

	@Transactional
	public BigDecimal getTotalPorcentajePorCorrida(PrdCorridaPK corridaOrigen, PrdCorridaPK corridaDestino);

	@Transactional
	public void ejecutarSQL(String sql) throws Exception;

}
