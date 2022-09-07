package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTablaTO;

@Transactional
public interface TipoComprobanteService {

	public List<AnxTipoComprobanteTablaTO> getListaAnexoTipoComprobanteTO(String codigo) throws Exception;

	public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTO(String codigoTipoTransaccion)
			throws Exception;

	public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTOCompleto() throws Exception;

	public List<AnxTipoComprobanteTO> getAnexoTipoComprobanteTO() throws Exception;

}
