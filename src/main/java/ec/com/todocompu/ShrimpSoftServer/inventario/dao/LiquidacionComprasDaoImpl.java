/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class LiquidacionComprasDaoImpl extends GenericDaoImpl<AnxLiquidacionComprasElectronica, Integer> implements LiquidacionComprasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public AnxLiquidacionComprasElectronica buscarAnxLiquidacionComprasElectronica(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "SELECT e_secuencial  " + "FROM anexo.anx_liquidacion_compras_electronica  " + "WHERE comp_empresa = '" + empresa
                + "' AND comp_periodo = '" + periodo + "' AND   " + "comp_motivo = '" + motivo + "' and comp_numero = '"
                + numero + "';";
        Object obj = genericSQLDao.obtenerObjetoPorSql(sql);
        return obtenerPorId(AnxLiquidacionComprasElectronica.class, (Integer) obj);
    }

    @Override
    public Boolean accionAnxLiquidacionComprasElectronica(AnxLiquidacionComprasElectronica anxLiquidacionComprasElectronica, SisSuceso sisSuceso, char accion) throws Exception {
        if (accion == 'I') {
            insertar(anxLiquidacionComprasElectronica);
        }
        if (accion == 'M') {
            actualizar(anxLiquidacionComprasElectronica);
        }
        if (accion == 'E') {
            eliminar(anxLiquidacionComprasElectronica);
        }
        if (sisSuceso != null) {
            sisSucesoDao.insertar(sisSuceso);
        }
        return true;
    }

    @Override
    public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        ePeriodo = ePeriodo == null ? ePeriodo : "'" + ePeriodo + "'";
        eMotivo = eMotivo == null ? eMotivo : "'" + eMotivo + "'";
        eNumero = eNumero == null ? eNumero : "'" + eNumero + "'";
        String sql = "SELECT e_xml FROM anexo.anx_liquidacion_compras_electronica WHERE comp_empresa = " + empresa
                + " and comp_periodo = " + ePeriodo + " and comp_motivo = " + eMotivo + " and comp_numero = " + eNumero;
        return new String((byte[]) genericSQLDao.obtenerObjetoPorSql(sql), "UTF-8");
    }

    @Override
    public boolean comprobarAnxLiquidacionComprasElectronica(String empresa, String periodo, String motivo, String numero) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        periodo = periodo == null ? periodo : "'" + periodo + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        numero = numero == null ? numero : "'" + numero + "'";
        String sql = "SELECT COUNT(*)!=0 FROM anexo.anx_liquidacion_compras_electronica " + "WHERE (comp_empresa = " + empresa
                + " AND comp_periodo = " + periodo + " AND comp_motivo = " + motivo + " AND comp_numero = " + numero + ")";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String comprobarAnxLiquidacionComprasElectronicaAutorizacion(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "SELECT e_estado FROM anexo.anx_liquidacion_compras_electronica " + "WHERE (comp_empresa = '" + empresa
                + "' AND comp_periodo = '" + periodo + "' AND comp_motivo = '" + motivo + "' AND comp_numero = '" + numero + "')";
        return (String) genericSQLDao.obtenerObjetoPorSql(sql);
    }

}
