package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvMedidaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMedidaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedida;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedidaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProductoMedidaDao extends GenericDao<InvProductoMedida, InvProductoMedidaPK> {

	public Boolean accionInvProductoMedida(InvProductoMedida invProductoMedida, SisSuceso sisSuceso, char accion)
			throws Exception;

	public InvProductoMedida buscarProductoMedida(String empresa, String codigoProducto) throws Exception;

	public boolean comprobarInvProductoMedida(String empresa, String codigo) throws Exception;

	public List<InvProductoMedidaTO> getInvProductoMedidaTO(String empresa) throws Exception;

	public List<InvMedidaComboTO> getListaInvMedidaTablaTO(String empresa) throws Exception;

	public boolean comprobarEliminarInvProductoMedida(String empresa, String codigo) throws Exception;

	public boolean productoMedidaRepetido(String empresa, String codigo, String detalle) throws Exception;

}
