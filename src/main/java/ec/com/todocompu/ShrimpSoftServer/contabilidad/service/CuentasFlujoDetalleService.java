package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface CuentasFlujoDetalleService {

	public boolean insertarConCuentaFlujoDetalle(ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO, SisInfoTO sisInfoTO)
			throws Exception;

	public boolean modificarConCuentaFlujoDetalle(ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO,
			String codigoCambiarLlave, char formaPagoAnterior, SisInfoTO sisInfoTO) throws Exception;

	public List<ConCuentasFlujoDetalleTO> getListaConCuentasFlujoDetalleTO(String empresa) throws Exception;

}
