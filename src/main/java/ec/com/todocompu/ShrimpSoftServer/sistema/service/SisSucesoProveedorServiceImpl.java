/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoProveedorDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoProveedor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trabajo
 */
@Service
public class SisSucesoProveedorServiceImpl implements SisSucesoProveedorService {

    @Autowired
    private SucesoProveedorDao sucesoProveedorDao;

    @Override
    public List<SisSucesoProveedor> getListaSisSucesoProveedor(Integer suceso) throws Exception {
        List<SisSucesoProveedor> sisSucesos = sucesoProveedorDao.getListaSisSucesoProveedor(suceso);
        return sisSucesos;
    }

}
