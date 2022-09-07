package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.math.BigDecimal;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPorcentajeiva;

public interface PorcentajeIvaDao extends GenericDao<AnxPorcentajeiva, String> {

	public BigDecimal getValorAnxPorcentajeIvaTO(String fechaFactura) throws Exception;

	public BigDecimal getValorAnxMontoMaximoConsumidorFinalTO(String fechaFactura) throws Exception;

}
