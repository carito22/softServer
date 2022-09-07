/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvOrdenCompraMotivoDetalleAprobadoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;

/**
 *
 * @author Developer4
 */
public interface PedidosOrdenCompraMotivoService {

    public InvPedidosOrdenCompraMotivo insertarInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivo invPedidosOrdenCompraMotivo, List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores, SisInfoTO sisInfoTO) throws Exception;

    public InvPedidosOrdenCompraMotivo modificarInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivo invPedidosOrdenCompraMotivo, List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public List<InvPedidosOrdenCompraMotivoTO> getListaInvPedidosOrdenCompraMotivo(String empresa, String sector) throws Exception;

    public InvPedidosOrdenCompraMotivo getInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivoPK invPedidosMotivoPK) throws GeneralException;

    public boolean eliminarInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivoPK invPedidosOrdenCompraMotivoPK, SisInfoTO sisInfoTO) throws Exception, GeneralException;

    public boolean modificarEstadoInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivoPK pk, boolean estado, SisInfoTO sisInfoTO) throws Exception;

}
