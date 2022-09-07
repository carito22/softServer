package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleRegistradores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PedidosMotivoDetalleRegistradoresDao extends GenericDao<InvPedidosMotivoDetalleRegistradores, Integer> {

    public boolean eliminarInvPedidosMotivoDetalleRegistradores(InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores, SisSuceso sisSuceso) throws Exception;

    public InvPedidosMotivoDetalleRegistradores getInvPedidosMotivoDetalleRegistradores(Integer codigo) throws Exception;

    public boolean insertarInvPedidosMotivoDetalleRegistradores(InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvPedidosMotivoDetalleRegistradores(InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores, SisSuceso sisSuceso) throws Exception;

    public List<InvPedidosMotivoDetalleRegistradores> getListaInvPedidosMotivoDetalleRegistradores(InvPedidosMotivoPK invPedidosMotivoPK) throws Exception;
}
