/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasLiquidacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class VentasLiquidacionDaoImpl extends GenericDaoImpl<InvVentasLiquidacion, Integer> implements VentasLiquidacionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvVentasLiquidacion> getListInvVentasLiquidacion(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "SELECT * FROM inventario.inv_ventas_liquidacion WHERE vta_empresa = ('" + empresa + "') AND vta_periodo = ('" + periodo + "') "
                + "AND vta_motivo = ('" + motivo + "') AND vta_numero = ('" + numero + "') " + "Order by det_secuencial ASC";
        return genericSQLDao.obtenerPorSql(sql, InvVentasLiquidacion.class);
    }

}
