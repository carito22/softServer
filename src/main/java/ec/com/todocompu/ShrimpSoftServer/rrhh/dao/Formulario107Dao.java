package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107_2013TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormulario107;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormulario107PK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface Formulario107Dao extends GenericDao<RhFormulario107, RhFormulario107PK> {

    public RhFormulario107 getRhFormulario107(String empresa, String anio, String empleadoID) throws Exception;

    public RhFormulario107TO getRhFormulario107TO(String empresa, String anio, String empleadoID) throws Exception;

    public boolean insertarRhFormulario107(RhFormulario107 rhFormulario107, SisSuceso sisSuceso) throws Exception;

    public List<RhFunFormulario107TO> getRhFunFormulario107(String empresa, String desde, String hasta,
            Character status) throws Exception;

    public List<RhFunFormulario107_2013TO> getRhFunFormulario107_2013(String empresa, String desde, String hasta,
            Character status) throws Exception;

    public List<RhFunFormulario107_2013TO> getRhFunFormulario107(String empresa, String desde, String hasta,
            Character status, String sector) throws Exception;

    public RhFormulario107TO getRhFormulario107ConsultaTO(String empresa, String desde, String hasta, String empleadoID)
            throws Exception;

}
