package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface UtilidadMotivoService {

    public RhUtilidadMotivo getRhUtilidadMotivo(String empresa, String codigo) throws Exception;

    public MensajeTO insertarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String insertarRrhhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisInfoTO sisInfoTO) throws Exception;

    public List<RhUtilidadMotivo> getListaRhUtilidadMotivo(String empresa) throws Exception;

    public List<RhUtilidadMotivo> getListaRhUtilidadMotivoSoloActivos(String empresa) throws Exception;

}
