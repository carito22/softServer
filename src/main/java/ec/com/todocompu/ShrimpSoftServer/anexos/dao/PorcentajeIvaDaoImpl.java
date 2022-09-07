package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPorcentajeiva;

@Repository
public class PorcentajeIvaDaoImpl extends GenericDaoImpl<AnxPorcentajeiva, String> implements PorcentajeIvaDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Override
	public BigDecimal getValorAnxPorcentajeIvaTO(String fechaFactura) throws Exception {
		fechaFactura = fechaFactura == null ? null : "'" + fechaFactura + "'";
		String sql = "SELECT pi_porcentaje FROM anexo.anx_porcentajeiva "
				+ "WHERE CASE WHEN pi_fecha_fin IS NULL THEN ((" + fechaFactura + ") >= pi_fecha_inicio) " + "ELSE (("
				+ fechaFactura + ") >= pi_fecha_inicio AND (" + fechaFactura + ") <= pi_fecha_fin) END";
		Object ivaVigente = genericSQLDao.obtenerObjetoPorSql(sql);
		return new BigDecimal(String.valueOf(ivaVigente == null ? "0.0" : ivaVigente.toString()));

	}

	@Override
	public BigDecimal getValorAnxMontoMaximoConsumidorFinalTO(String fechaFactura) throws Exception {
		fechaFactura = fechaFactura == null ? null : "'" + fechaFactura + "'";
		String sql = "SELECT pi_monto_maximo_consumidor_final FROM anexo.anx_porcentajeiva "
				+ "WHERE CASE WHEN pi_fecha_fin IS NULL THEN ((" + fechaFactura + ") >= pi_fecha_inicio) " + "ELSE (("
				+ fechaFactura + ") >= pi_fecha_inicio AND (" + fechaFactura + ") <= pi_fecha_fin) END";
		Object montoMaximo = genericSQLDao.obtenerObjetoPorSql(sql);
		return new BigDecimal(String.valueOf(montoMaximo == null ? "0.0" : montoMaximo.toString()));
	}

}
