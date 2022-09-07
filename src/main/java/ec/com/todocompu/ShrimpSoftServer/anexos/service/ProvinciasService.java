package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;

@Transactional
public interface ProvinciasService {

	public List<AnxProvinciaCantonTO> getComboAnxProvinciaTO() throws Exception;

	public List<AnxProvinciaCantonTO> getComboAnxCantonTO(String provincia) throws Exception;

}
