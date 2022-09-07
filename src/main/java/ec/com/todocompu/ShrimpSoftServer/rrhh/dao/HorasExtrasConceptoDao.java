package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasConcepto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface HorasExtrasConceptoDao extends GenericDao<RhHorasExtrasConcepto, Integer> {

    public RhHorasExtrasConcepto buscarHorasExtrasConcepto(Integer bcSecuencia) throws Exception;

    public boolean insertarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, SisSuceso sisSuceso) throws Exception;

    public boolean modificarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, SisSuceso sisSuceso) throws Exception;

    public List<RhHorasExtrasConcepto> getListaHorasExtrasConcepto(String empresa) throws Exception;

    public List<RhHorasExtrasConcepto> getListaHorasExtrasConceptos(String empresa, boolean inactivo) throws Exception;

}
