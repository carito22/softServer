package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosConfiguracionTO;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface PedidosMotivoService {

    public List<InvPedidosMotivoTO> getListaInvPedidosMotivo(String empresa, boolean filtrarInactivos) throws Exception;

    public String eliminarInvPedidosMotivoTO(InvPedidosMotivoTO invPedidosMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public InvPedidosMotivo modificarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, InvPedidosConfiguracionTO invPedidosConfiguracionTO, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public boolean eliminarInvPedidosMotivo(InvPedidosMotivoPK pk, InvPedidosConfiguracionTO conf, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarEstadoInvPedidosMotivo(InvPedidosMotivoPK pk, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public List<InvPedidosMotivo> getListaInvPedidosMotivo(String empresa, boolean incluirInactivos, PrdSectorPK prdSectorPK) throws Exception;

    public InvPedidosMotivo getInvPedidosMotivo(InvPedidosMotivoPK invPedidosMotivoPK) throws Exception;

    public List<InvPedidosMotivoTO> getListaInvPedidosMotivoTO(String empresa, boolean incluirInactivos, PrdSectorPK prdSectorPK, InvProductoCategoriaPK invProductoCategoriaPK);

    public InvPedidosMotivo insertarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, SisInfoTO sisInfoTO) throws GeneralException, Exception;

}
