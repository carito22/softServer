package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControlPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface EquipoControlDao extends GenericDao<PrdEquipoControl, PrdEquipoControlPK> {

    public boolean insertarEquipoControl(PrdEquipoControl prdEquipoControl, SisSuceso sisSuceso) throws Exception;

    public List<PrdEquipoControl> listarEquiposControl(String empresa) throws Exception;

    public boolean actualizarEquipoControl(PrdEquipoControl prdEquipoControl, SisSuceso sisSuceso) throws Exception;
    
    public PrdEquipoControl obtenerEquipoControl(String empresa, String codigo) throws Exception;
}
