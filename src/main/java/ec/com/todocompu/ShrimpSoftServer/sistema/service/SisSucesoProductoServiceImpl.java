/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoProductoDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoProducto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trabajo
 */
@Service
public class SisSucesoProductoServiceImpl implements SisSucesoProductoService {

    @Autowired
    private SucesoProductoDao sucesoProductoDao;

    @Override
    public List<SisSucesoProducto> getListaSisSucesoProducto(Integer suceso) throws Exception {
        List<SisSucesoProducto> sisSucesos = sucesoProductoDao.getListaSisSucesoProducto(suceso);
        return sisSucesos;
    }

}
