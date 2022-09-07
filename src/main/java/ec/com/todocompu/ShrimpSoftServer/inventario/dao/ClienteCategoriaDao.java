package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaClienteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ClienteCategoriaDao extends GenericDao<InvClienteCategoria, InvClienteCategoriaPK> {

	public Boolean accionInvClienteCategoria(InvClienteCategoria invClienteCategoria, SisSuceso sisSuceso, char accion)
			throws Exception;

	public InvClienteCategoria buscarInvClienteCategoria(String empresa, String codigo) throws Exception;

	public boolean comprobarInvClienteCategoria(String empresa, String codigo) throws Exception;

	public List<InvClienteCategoriaTO> getInvClienteCategoriaTO(String empresa) throws Exception;

	public List<InvCategoriaClienteComboTO> getListaCategoriaClienteComboTO(String empresa) throws Exception;

	public boolean comprobarEliminarInvClienteCategoria(String empresa, String codigo) throws Exception;

}
