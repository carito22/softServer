package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraFlujoTO;

@Transactional
public interface EstructuraFlujoService {

	public List<ConEstructuraFlujoTO> getListaConEstructuraFlujoTO(String empresa) throws Exception;

}
