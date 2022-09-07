package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

public interface PedidosMotivoDao extends GenericDao<InvPedidosMotivo, InvPedidosMotivoPK> {

    public boolean eliminarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, SisSuceso sisSuceso) throws Exception;

    public InvPedidosMotivo getInvPedidosMotivo(String empresa, String sector, String codigo) throws Exception;

    public InvPedidosMotivo getInvPedidosMotivoPorAtributo(String empresa, String atributo, String valorAtributo) throws Exception;

    public boolean insertarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, SisSuceso sisSuceso) throws Exception;

    public List<InvPedidosMotivo> getListaInvPedidosMotivo(String empresa, boolean filtrarInactivos) throws Exception;
}
