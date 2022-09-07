/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoContratoDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoContrato;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trabajo
 */
@Service
public class SisSucesoContratoServiceImpl implements SisSucesoContratoService {

    @Autowired
    private SucesoContratoDao sucesoContratoDao;

    @Override
    public List<SisSucesoContrato> getListaSisSucesoContrato(Integer suceso) throws Exception {
        return sucesoContratoDao.getListaSisSucesoContrato(suceso);
    }

}
