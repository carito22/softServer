package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface XivSueldoMotivoDao extends GenericDao<RhXivSueldoMotivo, RhXivSueldoMotivoPK> {

    public boolean insertarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisSuceso sisSuceso) throws Exception;

    public boolean modificarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisSuceso sisSuceso)
            throws Exception;

    public boolean eliminarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisSuceso sisSuceso) throws Exception;

    public RhXivSueldoMotivo getRhXivSueldoMotivo(String empresa, String detalle) throws Exception;

    public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivo(String empresa) throws Exception;

    public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivoSoloActivos(String empresa) throws Exception;

    public boolean banderaEliminarXivMotivo(String empresa, String detalle) throws Exception;

}
