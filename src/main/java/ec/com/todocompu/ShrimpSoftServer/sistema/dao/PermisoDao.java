
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisMenu;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPermisoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPermiso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPermisoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PermisoDao extends GenericDao<SisPermiso, SisPermisoPK> {

	public List<SisPermisoTO> getListaPermisoTO(String grupoCodigo, String empCodigo) throws Exception;

	public List<String> getListaPermisoModulo(String empCodigo, String grupoCodigo) throws Exception;

	public List<SisMenu> getListaPermisoTO(String empCodigo, String grupoCodigo, String perModulo) throws Exception;

	public List<SisMenu> getMenuWeb(String usuario, boolean produccion);

	public boolean modificarSisPermiso(List<SisPermiso> sisPermisos, SisSuceso sisSuceso) throws Exception;

}
