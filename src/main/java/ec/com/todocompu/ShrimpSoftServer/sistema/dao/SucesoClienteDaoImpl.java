/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class SucesoClienteDaoImpl extends GenericDaoImpl<SisSucesoCliente, Integer> implements SucesoClienteDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisSucesoCliente> getListaSisSucesoCliente(Integer suceso) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_suceso_cliente WHERE sus_suceso=" + suceso + " ORDER BY sus_secuencial";
        return genericSQLDao.obtenerPorSql(sql, SisSucesoCliente.class);
    }

    @Override
    public List<SisSucesoCliente> getListaSisSucesoCliente(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_suceso_cliente WHERE cli_empresa='" + empresa + "' AND cli_codigo='" + codigo + "' ORDER BY sus_secuencial";
        return genericSQLDao.obtenerPorSql(sql, SisSucesoCliente.class);
    }
}
