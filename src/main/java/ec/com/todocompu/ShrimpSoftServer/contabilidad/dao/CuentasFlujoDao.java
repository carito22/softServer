package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface CuentasFlujoDao extends GenericDao<ConCuentasFlujo, ConCuentasFlujoPK> {

	public boolean modificarConCuentaFlujo(ConCuentasFlujo conCuentasFlujo, SisSuceso sisSuceso) throws Exception;

	public boolean modificarConCuentaFlujoLlavePrincipal(ConCuentasFlujo conCuentasFlujoEliminar,
			ConCuentasFlujo conCuentasFlujo, SisSuceso sisSuceso) throws Exception;

	public boolean modificarListaConCuentaFlujo(List<ConCuentasFlujo> listaConCuentasFlujo, SisSuceso sisSuceso)
			throws Exception;

	public boolean eliminarConCuentaFlujo(ConCuentasFlujo conCuentasFlujo, SisSuceso sisSuceso) throws Exception;

	public boolean insertarConCuentaFlujo(ConCuentasFlujo conCuentasFlujo, SisSuceso sisSuceso) throws Exception;

	public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(String empresa) throws Exception;

	public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(String empresa, String buscar) throws Exception;

}
