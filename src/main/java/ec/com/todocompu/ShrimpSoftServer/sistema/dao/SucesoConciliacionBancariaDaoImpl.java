/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoConciliacionBancaria;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class SucesoConciliacionBancariaDaoImpl extends GenericDaoImpl<SisSucesoConciliacionBancaria, Integer> implements SucesoConciliacionBancariaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisSucesoConciliacionBancaria> getListaSisSucesoConciliacionBancaria(Integer suceso) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_suceso_conciliacion_bancaria WHERE sus_suceso=" + suceso + " ORDER BY sus_secuencial";
        return genericSQLDao.obtenerPorSql(sql, SisSucesoConciliacionBancaria.class);
    }

}
