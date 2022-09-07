/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoCompraDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCompra;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trabajo
 */
@Service
public class SisSucesoCompraServiceImpl implements SisSucesoCompraService {

    @Autowired
    private SucesoCompraDao sucesoCompraDao;

    @Override
    public List<SisSucesoCompra> getListaSisSucesoCompra(Integer suceso) throws Exception {
        List<SisSucesoCompra> sisSucesos = sucesoCompraDao.getListaSisSucesoCompra(suceso);
        return sisSucesos;
    }

}
