/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasLiquidacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class ComprasLiquidacionDaoImpl extends GenericDaoImpl<InvComprasLiquidacion, Integer> implements ComprasLiquidacionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvComprasLiquidacion> getListInvComprasLiquidacion(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "SELECT * FROM inventario.inv_compras_liquidacion WHERE comp_empresa = ('" + empresa + "') AND comp_periodo = ('" + periodo + "') "
                + "AND comp_motivo = ('" + motivo + "') AND comp_numero = ('" + numero + "') " + "Order by det_secuencial ASC";
        return genericSQLDao.obtenerPorSql(sql, InvComprasLiquidacion.class);
    }

}
