/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class GuiaRemisionDetalleDaoImpl extends GenericDaoImpl<InvGuiaRemisionDetalle, Integer> implements GuiaRemisionDetalleDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public void eliminarPorSql(Integer detSecuencial) {
        genericSQLDao.ejecutarSQL("DELETE FROM inventario.inv_guia_remision_detalle WHERE det_secuencial=" + detSecuencial);
    }
}
