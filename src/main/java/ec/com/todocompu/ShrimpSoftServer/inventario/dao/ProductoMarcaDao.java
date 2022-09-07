package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarcaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProductoMarcaDao extends GenericDao<InvProductoMarca, InvProductoMarcaPK> {

	public Boolean accionInvProductoMarca(InvProductoMarca invProductoMarca, SisSuceso sisSuceso, char accion)
			throws Exception;

	public InvProductoMarca buscarMarcaProducto(String empresa, String codigoMarca) throws Exception;

	public boolean comprobarInvProductoMarca(String empresa, String codigo, String detalle, String accion)
			throws Exception;

	public List<InvProductoMarcaComboListadoTO> getInvMarcaComboListadoTO(String empresa) throws Exception;

	public boolean comprobarEliminarInvProductoMarca(String empresa, String codigo) throws Exception;

}
