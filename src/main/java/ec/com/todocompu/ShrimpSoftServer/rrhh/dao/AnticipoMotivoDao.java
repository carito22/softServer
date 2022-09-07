package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnticipoMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface AnticipoMotivoDao extends GenericDao<RhAnticipoMotivo, RhAnticipoMotivoPK> {

	public Boolean accionRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisSuceso sisSuceso, char accion)
			throws Exception;

	public RhAnticipoMotivoTO getRhAnticipoMotivoTO(String empresa, String detalle) throws Exception;

	public Boolean buscarRhAnticipoMotivo(String empresa, String detalle) throws Exception;

	public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTablaTO(String empresa) throws Exception;

	public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTOTablaTO(String empresa, String codigo) throws Exception;

	public boolean insertarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean modificarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisSuceso sisSuceso) throws Exception;

	public RhAnticipoMotivo getRhAnticipoMotivo(String empresa, String detalle) throws Exception;

	public List<RhAnticipoMotivo> getListaRhAnticipoMotivo(String empresa) throws Exception;

	public boolean banderaEliminarAnticipoMotivo(String empresa, String detalle) throws Exception;

}
