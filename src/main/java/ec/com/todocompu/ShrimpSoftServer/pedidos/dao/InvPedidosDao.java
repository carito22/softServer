package ec.com.todocompu.ShrimpSoftServer.pedidos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;

public interface InvPedidosDao extends GenericDao<InvPedidos, InvPedidosPK> {

    public boolean insertarInvPedido(InvPedidos invPedidos, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvPedido(InvPedidos invPedidos, SisSuceso sisSuceso) throws Exception;
            
    public InvPedidos obtenerInvPedidos(String pedEmpresa, String sector, String pedMotivo, String pedNumero) throws Exception;

    public List<InvPedidosTO> getInvPedidosTO(String empresa, String busqueda, String motivo, String tipo) throws Exception;

    public boolean eliminarInvPedidos(InvPedidos invPedidos, SisSuceso sisSuceso) throws Exception, ConstraintViolationException;
    
    public String getProximaNumeracionInvPedidos(String empresa, InvPedidos invPedidos) throws Exception; 
    
}
