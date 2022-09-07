/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.ZendeskConfiguracion;

/**
 *
 * @author User
 */
public interface ZendeskDao extends GenericDao<ZendeskConfiguracion, Integer> {

    public ZendeskConfiguracion obtenerConfiguracion() throws Exception;

    public boolean modificarConfiguracion(ZendeskConfiguracion zendesk) throws Exception;
}
