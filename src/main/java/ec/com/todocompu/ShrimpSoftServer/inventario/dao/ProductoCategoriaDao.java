package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaComboProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaSincronizarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProductoCategoriaDao extends GenericDao<InvProductoCategoria, InvProductoCategoriaPK> {

	public Boolean accionInvProductoCategoria(InvProductoCategoria invProductoCategoria, SisSuceso sisSuceso,
			char accion) throws Exception;

	public InvProductoCategoria buscarInvProductoCategoria(String empresa, String codigo) throws Exception;

	public boolean comprobarInvProductoCategoria(String empresa, String codigo) throws Exception;

	public List<InvProductoCategoriaTO> getInvProductoCategoriaTO(String empresa) throws Exception;

	public List<InvCategoriaComboProductoTO> getListaCategoriaComboTO(String empresa) throws Exception;

	public Boolean getPrecioFijoCategoriaProducto(String empresa, String codigoCategoria) throws Exception;

	public boolean comprobarEliminarInvProductoCategoria(String empresa, String codigo) throws Exception;

	public List<InvCategoriaSincronizarTO> invCategoriasSincronizar(String empresaOrigen, String empresaDestino)
			throws Exception;

}
