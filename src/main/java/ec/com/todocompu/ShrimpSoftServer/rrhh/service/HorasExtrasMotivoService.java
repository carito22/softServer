package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface HorasExtrasMotivoService {

    public RhHorasExtrasMotivo getRhHorasExtrasMotivo(String empresa, String codigo) throws Exception;

    public MensajeTO insertarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String insertarRhhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisInfoTO sisInfoTO) throws Exception;

    public List<RhHorasExtrasMotivo> getListaRhHorasExtrasMotivo(String empresa) throws Exception;

    public List<RhHorasExtrasMotivo> getListaRhHorasExtrasMotivos(String empresa, boolean inactivo) throws Exception;

}
