/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class VentaGuiaRemisionDaoImpl extends GenericDaoImpl<InvVentaGuiaRemision, Integer> implements VentaGuiaRemisionDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public Boolean accionInvVentaGuiaRemision(InvVentaGuiaRemision invVentaGuiaRemision, SisSuceso sisSuceso, char accion) throws Exception {
        if (accion == 'I') {
            insertar(invVentaGuiaRemision);
        }
        if (accion == 'M') {
            actualizar(invVentaGuiaRemision);
        }
        if (accion == 'E') {
            eliminar(invVentaGuiaRemision);
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<InvVentaGuiaRemision> obtenerInvVentaGuiaRemision(String empresa, String periodo, String motivo, String numero) {
        String sql = "SELECT * " + "FROM inventario.inv_venta_guia_remision WHERE vta_empresa = ('" + empresa + "') "
                + "AND vta_periodo = ('" + periodo + "') AND vta_motivo = ('" + motivo + "') AND vta_numero = ('" + numero + "') " + "Order by det_secuencial ASC";

        return genericSQLDao.obtenerPorSql(sql, InvVentaGuiaRemision.class);
    }

    @Override
    public InvVentaGuiaRemision obtenerInvVentaGuiaRemision(int secuencial) {
        return obtenerPorId(InvVentaGuiaRemision.class, secuencial);
    }

}
