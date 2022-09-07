package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;

@Transactional
public interface PaisService {

	public List<AnxPaisTO> getComboAnxPaisTO() throws Exception;

}
