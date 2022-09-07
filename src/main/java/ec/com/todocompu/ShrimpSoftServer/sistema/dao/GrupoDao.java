
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface GrupoDao extends GenericDao<SisGrupo, SisGrupoPK> {

	public SisGrupoTO sisGrupoUsuariosTO(String infEmpresa, String infUsuario) throws Exception;

	public List<SisGrupoTO> getListaSisGrupo(String empresa) throws Exception;

	public List<SisGrupoTO> getSisGrupoTOs(String empresa) throws Exception;

	public boolean insertarSisGrupo(SisGrupo sisGrupo, SisSuceso sisSuceso) throws Exception;

	public boolean modificarSisGrupo(SisGrupo sisGrupo, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarSisGrupo(SisGrupo sisGrupo, SisSuceso sisSuceso) throws Exception;

}
