package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasConcepto;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface HorasExtrasConceptoService {

    public boolean modificarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarEstadoRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, SisInfoTO sisInfoTO) throws Exception;

    public boolean insertarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, SisInfoTO sisInfoTO) throws Exception;

    public List<RhHorasExtrasConcepto> getListaHorasExtrasConcepto(String empresa) throws Exception;

    public List<RhHorasExtrasConcepto> getListaHorasExtrasConceptos(String empresa, boolean inactivo) throws Exception;

}
