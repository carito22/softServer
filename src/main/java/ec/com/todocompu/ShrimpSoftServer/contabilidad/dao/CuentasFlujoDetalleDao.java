package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoDetallePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface CuentasFlujoDetalleDao extends GenericDao<ConCuentasFlujoDetalle, ConCuentasFlujoDetallePK> {

	public boolean insertarConCuentaFlujoDetalle(ConCuentasFlujoDetalle conCuentasFlujoDetalle, SisSuceso sisSuceso)
			throws Exception;

	public boolean modificarConCuentaFlujoDetalle(ConCuentasFlujoDetalle conCuentasFlujoDetalle, SisSuceso sisSuceso)
			throws Exception;

	public boolean modificarConCuentaLlavePrincipalFlujoDetalle(ConCuentasFlujoDetalle conCuentasFlujoEliminarDetalle,
			ConCuentasFlujoDetalle conFlujoCuentasDetalle, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarConCuentaFlujoDetalle(ConCuentasFlujoDetalle conCuentasFlujoDetalle, SisSuceso sisSuceso)
			throws Exception;

	public List<ConCuentasFlujoDetalleTO> getListaBuscarConCuentasFlujoDetalleTO(String empresa, String buscar)
			throws Exception;

	public List<ConCuentasFlujoDetalleTO> getListaConCuentasFlujoDetalleTO(String empresa) throws Exception;
}
