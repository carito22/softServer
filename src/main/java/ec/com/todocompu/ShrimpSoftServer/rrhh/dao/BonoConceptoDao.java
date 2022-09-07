package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoConcepto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface BonoConceptoDao extends GenericDao<RhBonoConcepto, Integer> {

    public RhBonoConcepto buscarBonoConcepto(Integer bcSecuencia) throws Exception;

    public boolean insertarRhBonoConcepto(RhBonoConcepto rhBonoConcepto, SisSuceso sisSuceso) throws Exception;

    public boolean modificarRhBonoConcepto(RhBonoConcepto rhBonoConcepto, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarRhBonoConcepto(RhBonoConcepto rhBonoConcepto, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO) throws Exception;

    public List<RhListaBonoConceptoTO> getListaBonoConcepto(String empresa) throws Exception;

    public List<RhListaBonoConceptoTO> getListaBonoConceptos(String empresa, boolean inactivo) throws Exception;

    public List<RhComboBonoConceptoTO> getComboBonoConcepto(String empresa) throws Exception;

}
