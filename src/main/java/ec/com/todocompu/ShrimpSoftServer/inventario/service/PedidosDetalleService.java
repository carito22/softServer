/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Developer4
 */
@Transactional
public interface PedidosDetalleService {

    public boolean eliminarInvPedidosDetalle(Integer secuencial, SisInfoTO sisInfoTO) throws GeneralException;

    public List<InvPedidosDetalle> getListaInvPedidosDetallePorOrdenPedido(InvPedidosPK invPedidosPK, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarListaInvPedidosDetalle(List<InvPedidosDetalle> listaInvPedidosDetalle, SisInfoTO sisInfoTO) throws GeneralException, Exception;

}
