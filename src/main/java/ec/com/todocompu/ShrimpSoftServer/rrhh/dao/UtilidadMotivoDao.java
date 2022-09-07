package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface UtilidadMotivoDao extends GenericDao<RhUtilidadMotivo, RhUtilidadMotivoPK> {

    public boolean insertarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisSuceso sisSuceso) throws Exception;

    public boolean modificarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisSuceso sisSuceso) throws Exception;

    public RhUtilidadMotivo getRhUtilidadMotivo(String empresa, String detalle) throws Exception;

    public List<RhUtilidadMotivo> getListaRhUtilidadMotivo(String empresa) throws Exception;

    public List<RhUtilidadMotivo> getListaRhUtilidadMotivoSoloActivos(String empresa) throws Exception;

    public boolean banderaEliminarUtilidadMotivo(String empresa, String detalle) throws Exception;

}
