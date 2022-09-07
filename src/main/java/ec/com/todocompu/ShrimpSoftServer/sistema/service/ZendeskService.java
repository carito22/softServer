/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.ZendeskConfiguracion;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
public interface ZendeskService {

    public ZendeskConfiguracion obtenerConfiguracion();

    @Transactional
    public String modificarConfiguracion(ZendeskConfiguracion zendesk) throws Exception;

}
