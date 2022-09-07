package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface XiiiSueldoMotivoService {

    public RhXiiiSueldoMotivo getRhXiiiSueldoMotivo(String empresa, String codigo) throws Exception;

    public MensajeTO insertarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisInfoTO sisInfoTO)
            throws Exception;

    public String modificarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisInfoTO sisInfoTO)
            throws Exception;

    public String modificarEstadoRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, boolean estado, SisInfoTO sisInfoTO)
            throws Exception;

    public String eliminarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisInfoTO sisInfoTO)
            throws Exception;

    public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivo(String empresa) throws Exception;

    public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivos(String empresa, boolean inactivo) throws Exception;

}
