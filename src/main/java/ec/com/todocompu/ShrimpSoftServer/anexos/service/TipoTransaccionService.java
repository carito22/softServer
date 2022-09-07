package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoListaTransaccionTO;

@Transactional
public interface TipoTransaccionService {

	public String getCodigoAnxTipoTransaccionTO(String tipoIdentificacion, String tipoTransaccion) throws Exception;

	public List<AnxTipoListaTransaccionTO> getAnexoTipoListaTransaccionTO() throws Exception;

}
