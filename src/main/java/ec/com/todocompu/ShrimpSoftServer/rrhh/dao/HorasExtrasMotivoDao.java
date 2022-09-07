package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface HorasExtrasMotivoDao extends GenericDao<RhHorasExtrasMotivo, RhHorasExtrasMotivoPK> {

	public boolean insertarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean modificarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisSuceso sisSuceso) throws Exception;

	public RhHorasExtrasMotivo getRhHorasExtrasMotivo(String empresa, String detalle) throws Exception;

	public List<RhHorasExtrasMotivo> getListaRhHorasExtrasMotivo(String empresa) throws Exception;
        
        public List<RhHorasExtrasMotivo> getListaRhHorasExtrasMotivos(String empresa, boolean inactivo) throws Exception;

	public boolean banderaEliminarHorasExtrasMotivo(String empresa, String detalle) throws Exception;

}
