package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface XiiiSueldoMotivoDao extends GenericDao<RhXiiiSueldoMotivo, RhXiiiSueldoMotivoPK> {

    public boolean insertarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisSuceso sisSuceso)
            throws Exception;

    public boolean modificarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisSuceso sisSuceso)
            throws Exception;

    public boolean eliminarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisSuceso sisSuceso)
            throws Exception;

    public RhXiiiSueldoMotivo getRhXiiiSueldoMotivo(String empresa, String detalle) throws Exception;

    public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivo(String empresa) throws Exception;

    public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivos(String empresa, boolean inactivo) throws Exception;

    public boolean banderaEliminarXiiiMotivo(String empresa, String detalle) throws Exception;

}
