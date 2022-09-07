/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoFormaPagoCompra;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class SucesoFormaPagoCompraDaoImpl extends GenericDaoImpl<SisSucesoFormaPagoCompra, Integer> implements SucesoFormaPagoCompraDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisSucesoFormaPagoCompra> getListaSisSucesoFormaPagoCompra(Integer suceso) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_suceso_forma_pago_compra WHERE sus_suceso=" + suceso + " ORDER BY sus_secuencial";
        return genericSQLDao.obtenerPorSql(sql, SisSucesoFormaPagoCompra.class);
    }
}
