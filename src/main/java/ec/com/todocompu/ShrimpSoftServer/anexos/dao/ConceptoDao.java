package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxConcepto;

public interface ConceptoDao extends GenericDao<AnxConcepto, Integer> {

	public AnxConceptoTO getBuscarAnexoConceptoTO(String fechaRetencion, String conceptoCodigo) throws Exception;
        
        public AnxConcepto obtenerAnexo(String fechaRetencion, String conceptoCodigo) throws Exception;

	public List<AnxConceptoTO> getAnexoConceptoTO() throws Exception;

	public List<AnxConceptoComboTO> getListaAnxConceptoTO() throws Exception;

	public List<AnxConceptoComboTO> getListaAnxConceptoTO(String fechaRetencion) throws Exception;

	public List<AnxConceptoComboTO> getListaAnxConceptoTO(String fechaRetencion, String busqueda) throws Exception;

}
