package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface PrestamoMotivoService {

    public RhPrestamoMotivo getRhPrestamoMotivo(String empresa, String codigo) throws Exception;

    public MensajeTO insertarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisInfoTO sisInfoTO) throws Exception;

    public List<RhPrestamoMotivo> getListaRhPrestamoMotivo(String empresa) throws Exception;

    public List<RhPrestamoMotivo> getListaRhPrestamoMotivos(String empresa, boolean inactivo) throws Exception;

}
