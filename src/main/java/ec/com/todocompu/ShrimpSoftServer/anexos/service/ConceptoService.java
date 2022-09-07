package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;

@Transactional
public interface ConceptoService {

	public List<AnxConceptoComboTO> getListaAnxConceptoComboTO() throws Exception;

	public List<AnxConceptoComboTO> getListaAnxConceptoComboTO(String fechaRetencion) throws Exception;

	public List<AnxConceptoComboTO> getListaAnxConceptoTO(String fechaRetencion, String busqueda) throws Exception;

	public AnxConceptoTO getBuscarAnexoConceptoTO(String fechaRetencion, String conceptoCodigo) throws Exception;

	public List<AnxConceptoTO> getAnexoConceptoTO() throws Exception;

}
