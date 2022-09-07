package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhProvisiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProvisionesDao extends GenericDao<RhProvisiones, String> {

    public RhProvisiones listarRhProvisiones(String empresa) throws Exception;

    public RhProvisiones insertarRhProvisiones(RhProvisiones rhProvisiones, SisSuceso sisSuceso) throws Exception;

    public RhProvisiones modificarRhProvisiones(RhProvisiones rhProvisiones, SisSuceso sisSuceso) throws Exception;

}
