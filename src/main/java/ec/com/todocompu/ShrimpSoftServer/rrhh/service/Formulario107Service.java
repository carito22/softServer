package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107_2013TO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface Formulario107Service {

    public RhFormulario107TO getRhFormulario107ConsultaTO(String empresa, String desde, String hasta, String empleadoID)
            throws Exception;

    public RhFormulario107TO getRhFormulario107TO(String empresa, String anio, String empleadoID) throws Exception;

    public String insertarRhFormulario107TO(RhFormulario107TO rhFormulario107TO, SisInfoTO sisInfoTO) throws Exception;

    public List<RhFunFormulario107TO> getRhFunFormulario107(String empresa, String desde, String hasta,
            Character status) throws Exception;

    public List<RhFunFormulario107_2013TO> getRhFunFormulario107_2013(String empresa, String desde, String hasta,
            Character status) throws Exception;

    public List<RhFunFormulario107_2013TO> getRhFunFormulario107(String empresa, String desde, String hasta,
            Character status, String sector) throws Exception;

}
