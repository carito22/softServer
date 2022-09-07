package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleAprobadores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PedidosMotivoDetalleAprobadoresDao extends GenericDao<InvPedidosMotivoDetalleAprobadores, Integer> {

    public boolean eliminarInvPedidosMotivoDetalleAprobadores(InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores, SisSuceso sisSuceso) throws Exception;

    public InvPedidosMotivoDetalleAprobadores getInvPedidosMotivoDetalleAprobadores(Integer codigo) throws Exception;

    public boolean insertarInvPedidosMotivoDetalleAprobadores(InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvPedidosMotivoDetalleAprobadores(InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores, SisSuceso sisSuceso) throws Exception;

    public List<InvPedidosMotivoDetalleAprobadores> getListaInvPedidosMotivoDetalleAprobadores(InvPedidosMotivoPK invPedidosMotivoPK) throws Exception;
}
