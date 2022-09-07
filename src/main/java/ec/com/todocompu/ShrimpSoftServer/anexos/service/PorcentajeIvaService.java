package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PorcentajeIvaService {

	public BigDecimal getValorAnxPorcentajeIvaTO(String fechaFactura) throws Exception;

	public BigDecimal getValorAnxMontoMaximoConsumidorFinalTO(String fechaFactura) throws Exception;

}
