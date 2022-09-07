package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleEjecutores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PedidosMotivoDetalleEjecutoresDao extends GenericDao<InvPedidosMotivoDetalleEjecutores, Integer> {

    public boolean eliminarInvPedidosMotivoDetalleEjecutores(InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores, SisSuceso sisSuceso) throws Exception;

    public InvPedidosMotivoDetalleEjecutores getInvPedidosMotivoDetalleEjecutores(Integer codigo) throws Exception;

    public boolean insertarInvPedidosMotivoDetalleEjecutores(InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvPedidosMotivoDetalleEjecutores(InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores, SisSuceso sisSuceso) throws Exception;

    public List<InvPedidosMotivoDetalleEjecutores> getListaInvPedidosMotivoDetalleEjecutores(InvPedidosMotivoPK invPedidosMotivoPK) throws Exception;
}
