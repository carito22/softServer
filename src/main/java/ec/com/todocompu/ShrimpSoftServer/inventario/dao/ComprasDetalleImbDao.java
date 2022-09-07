/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleImb;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface ComprasDetalleImbDao extends GenericDao<InvComprasDetalleImb, Integer> {

    public List<InvComprasDetalleImb> getListInvComprasDetalleImb(String empresa, String periodo, String motivo, String numero) throws Exception;
}
