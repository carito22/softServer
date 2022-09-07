/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumosPK;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class ConfiguracionConsumosDaoImpl extends GenericDaoImpl<SisConfiguracionConsumos, SisConfiguracionConsumosPK> implements ConfiguracionConsumosDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisConfiguracionConsumos> listarSisConfiguracionConsumos(String empresa) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_configuracion_consumos where conf_empresa='" + empresa + "'";
        return genericSQLDao.obtenerPorSql(sql, SisConfiguracionConsumos.class);
    }

}
