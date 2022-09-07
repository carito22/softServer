package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConNumeracionTO;

@Transactional
public interface NumeracionService {

	public List<ConNumeracionTO> getListaConNumeracionTO(String empresa, String periodo, String tipo) throws Exception;

}
