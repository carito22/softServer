package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisMenu;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPermisoTO;

public interface PermisoService {

	@Transactional
	public List<SisMenu> getListaPermisoTO(String empCodigo, String grupoCodigo, String perModulo) throws Exception;

	@Transactional
	public boolean modificarSisPermiso(List<SisPermisoTO> listaSisPermisoTO, String usuario, String codModifGrupo,
			SisInfoTO sisInfoTO) throws Exception;

	@Transactional
	public List<SisPermisoTO> getListaPermisoTO(String grupoCodigo, String empCodigo) throws Exception;

	@Transactional
	public List<String> getListaPermisoModulo(String empCodigo, String grupoCodigo) throws Exception;

	@Transactional
	public List<SisMenu> getMenuWeb(String usuario, boolean produccion);
}