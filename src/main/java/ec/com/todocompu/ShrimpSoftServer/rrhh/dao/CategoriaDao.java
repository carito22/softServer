/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoria;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface CategoriaDao extends GenericDao<RhCategoria, RhCategoriaPK> {

	public RhCategoria buscarCategoria(String empCodigo, String catNombre) throws Exception;

	public Boolean accionRhCategoria(RhCategoria rhCategoria, SisSuceso sisSuceso, char accion) throws Exception;

	RhCategoriaTO getCategoria(String empCodigo, String catNombre) throws Exception;

	List<RhComboCategoriaTO> getComboCategoria(String empresa) throws Exception;

	List<RhCategoriaTO> getListaRhCategoriaTO(String empresa) throws Exception;
	
	List<RhCategoriaTO> getListaRhCategoriaCuentasTO(String empresa) throws Exception;

	String buscarCtaRhXiiiCategoria(String empresa, String categoria) throws Exception;

	String buscarCtaRhXivCategoria(String empresa, String categoria) throws Exception;

	RhCategoriaProvisionesTO getRhCategoriaProvisionesTO(String empresa, String categoria) throws Exception;

}
