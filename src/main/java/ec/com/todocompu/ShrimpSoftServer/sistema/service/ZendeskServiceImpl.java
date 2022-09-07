/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.ZendeskDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.ZendeskConfiguracion;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class ZendeskServiceImpl implements ZendeskService {

    @Autowired
    private ZendeskDao zendeskDao;

    @Override
    public ZendeskConfiguracion obtenerConfiguracion() {
        try {
            return zendeskDao.obtenerConfiguracion();
        } catch (Exception ex) {
            Logger.getLogger(ZendeskServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String modificarConfiguracion(ZendeskConfiguracion zendesk) throws Exception {
        if (zendeskDao.modificarConfiguracion(zendesk)) {
            return "TLa configuración de zendesk se ha modificado correctamente.";
        } else {
            return "FHubo un error al actualizar la configuración de zendesk...\nIntente de nuevo o contacte con el administrador.";
        }
    }

}
