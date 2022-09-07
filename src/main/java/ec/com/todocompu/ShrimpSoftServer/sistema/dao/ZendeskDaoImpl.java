/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.ZendeskConfiguracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public class ZendeskDaoImpl extends GenericDaoImpl<ZendeskConfiguracion, Integer> implements ZendeskDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public ZendeskConfiguracion obtenerConfiguracion() throws Exception {
        String sql = "SELECT * FROM sistemaweb.zendeskconfiguracion";
        return genericSQLDao.obtenerPorSql(sql, ZendeskConfiguracion.class).get(0);
    }

    @Override
    public boolean modificarConfiguracion(ZendeskConfiguracion zendesk) throws Exception {
        actualizar(zendesk);
        return true;
    }

}
