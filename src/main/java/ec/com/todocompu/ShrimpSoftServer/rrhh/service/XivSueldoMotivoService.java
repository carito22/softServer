package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface XivSueldoMotivoService {

    public RhXivSueldoMotivo getRhXivSueldoMotivo(String empresa, String codigo) throws Exception;

    public MensajeTO insertarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String insertarRhXivSueldMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivo(String empresa) throws Exception;

    public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivoSoloActivos(String empresa) throws Exception;

}
