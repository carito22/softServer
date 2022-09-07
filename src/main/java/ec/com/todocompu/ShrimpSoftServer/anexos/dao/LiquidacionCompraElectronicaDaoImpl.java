/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronica;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class LiquidacionCompraElectronicaDaoImpl extends GenericDaoImpl<AnxLiquidacionComprasElectronica, Integer> implements LiquidacionCompraElectronicaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxListaLiquidacionComprasElectronicaTO> getListaAnxListaLiquidacionCompraElectronicaTO(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM anexo.fun_liquidacion_compra_electronicas_autorizadas_listado('" + empresa + "','" + fechaDesde + "','" + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxListaLiquidacionComprasElectronicaTO.class);
    }

    @Override
    public AnxLiquidacionComprasElectronica buscarAnxLiquidacionCompraElectronica(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "SELECT e_secuencial  " + "FROM anexo.anx_liquidacion_compras_electronica  " + "WHERE comp_empresa = '" + empresa
                + "' AND comp_periodo = '" + periodo + "' AND   " + "comp_motivo = '" + motivo + "' and comp_numero = '"
                + numero + "';";
        Object obj = genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return obtenerPorId(AnxLiquidacionComprasElectronica.class, (Integer) obj);
        }

        return null;
    }

}
