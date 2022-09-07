package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface BonoMotivoService {

    public RhBonoMotivo getRhBonoMotivo(String empresa, String codigo) throws Exception;

    public MensajeTO insertarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String insertarRhhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoRhBonoMotivo(RhBonoMotivo rhBonoMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public List<RhBonoMotivo> getListaRhBonoMotivo(String empresa) throws Exception;

    public List<RhBonoMotivo> getListaRhBonoMotivos(String empresa, boolean inactivo) throws Exception;

}
