/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionDetalle;

/**
 *
 * @author CarolValdiviezo
 */
public interface GuiaRemisionDetalleDao extends GenericDao<InvGuiaRemisionDetalle, Integer> {

    public void eliminarPorSql(Integer detSecuencial);
}
