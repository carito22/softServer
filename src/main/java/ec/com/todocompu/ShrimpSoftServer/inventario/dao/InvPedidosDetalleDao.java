/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author Developer4
 */
public interface InvPedidosDetalleDao extends GenericDao<InvPedidosDetalle, Integer>{
    
    public boolean modificarListaInvPedidosDetalle(List<InvPedidosDetalle> listaInvPedidosDetalle, List<SisSuceso>  listaSisSuceso) throws Exception;
    
}
