/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoFormaCobroVenta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class SucesoFormaCobroVentaDaoImpl extends GenericDaoImpl<SisSucesoFormaCobroVenta, Integer> implements SucesoFormaCobroVentaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisSucesoFormaCobroVenta> getListaSisSisSucesoFormaCobroVenta(Integer suceso) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_suceso_forma_cobro_venta WHERE sus_suceso=" + suceso + " ORDER BY sus_secuencial";
        return genericSQLDao.obtenerPorSql(sql, SisSucesoFormaCobroVenta.class);
    }
}
