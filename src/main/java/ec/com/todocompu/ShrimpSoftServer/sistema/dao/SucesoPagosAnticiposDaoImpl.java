/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoPagosAnticipos;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class SucesoPagosAnticiposDaoImpl extends GenericDaoImpl<SisSucesoPagosAnticipos, Integer> implements SucesoPagosAnticiposDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisSucesoPagosAnticipos> getListaSisSucesoPagosAnticipos(Integer suceso) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_suceso_pagos_anticipos WHERE sus_suceso=" + suceso + " ORDER BY sus_secuencial";
        return genericSQLDao.obtenerPorSql(sql, SisSucesoPagosAnticipos.class);
    }

}
