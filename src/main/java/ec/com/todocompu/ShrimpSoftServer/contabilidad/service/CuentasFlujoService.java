package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface CuentasFlujoService {

	public boolean insertarConCuentaFlujo(ConCuentasFlujoTO conCuentasFlujoTO, SisInfoTO sisInfoTO) throws Exception;

	public boolean modificarConCuentaFlujo(ConCuentasFlujoTO conCuentasFlujoTO, String codigoCambiarLlave,
			SisInfoTO sisInfoTO) throws Exception;

	public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(String empresa, String buscar) throws Exception;

	public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(String empresa) throws Exception;

	public String eliminarConCuentaFlujo(ConCuentasFlujoTO conCuentasFlujoTO, SisInfoTO sisInfoTO) throws Exception;

	public String eliminarConCuentaFlujoDetalle(ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO, SisInfoTO sisInfoTO)
			throws Exception;

}
