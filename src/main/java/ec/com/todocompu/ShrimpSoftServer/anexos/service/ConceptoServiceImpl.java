package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.ConceptoDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;

@Service
public class ConceptoServiceImpl implements ConceptoService {

    @Autowired
    private ConceptoDao conceptoDao;

    @Override
    public List<AnxConceptoComboTO> getListaAnxConceptoComboTO() throws Exception {
        return conceptoDao.getListaAnxConceptoTO();
    }

    @Override
    public List<AnxConceptoComboTO> getListaAnxConceptoComboTO(String fechaRetencion) throws Exception {
        return conceptoDao.getListaAnxConceptoTO(fechaRetencion);
    }

    @Override
    public List<AnxConceptoComboTO> getListaAnxConceptoTO(String fechaRetencion, String busqueda) throws Exception {
        return conceptoDao.getListaAnxConceptoTO(fechaRetencion, busqueda);
    }

    @Override
    public AnxConceptoTO getBuscarAnexoConceptoTO(String fechaRetencion, String conceptoCodigo) throws Exception {
        return conceptoDao.getBuscarAnexoConceptoTO(fechaRetencion, conceptoCodigo);
    }

    @Override
    public List<AnxConceptoTO> getAnexoConceptoTO() throws Exception {
        return conceptoDao.getAnexoConceptoTO();
    }

}
