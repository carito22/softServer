/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Developer4
 */
@Repository
public class InvPedidosDetalleDaoImpl extends GenericDaoImpl<InvPedidosDetalle, Integer> implements InvPedidosDetalleDao{
    
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoDao sucesoDao;
    
    @Override
    public boolean modificarListaInvPedidosDetalle(List<InvPedidosDetalle> listaInvPedidosDetalle, List<SisSuceso> listadoSisSuceso) throws Exception {
        actualizar(listaInvPedidosDetalle);
        sucesoDao.insertar(listadoSisSuceso);
        return true;
    }

}
