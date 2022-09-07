package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface BonoConceptoService {

    public boolean modificarRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarEstadoRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO, SisInfoTO sisInfoTO) throws Exception;

    public boolean insertarRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO, SisInfoTO sisInfoTO) throws Exception;

    public List<RhListaBonoConceptoTO> getListaBonoConceptoTO(String empresa) throws Exception;

    public List<RhListaBonoConceptoTO> getListaBonoConceptosTO(String empresa, boolean inactivo) throws Exception;

    public List<RhComboBonoConceptoTO> getComboBonoConceptoTO(String empresa) throws Exception;

}
