/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxTiposRegimenFiscalExteriorDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposRegimenFiscalExterior;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class AnxTiposRegimenFiscalExteriorServiceImpl implements AnxTiposRegimenFiscalExteriorService {

    @Autowired
    private AnxTiposRegimenFiscalExteriorDao anxTiposRegimenFiscalExteriorDao;

    @Override
    public List<AnxTiposRegimenFiscalExterior> getListaAnxTiposRegimenFiscalExterior() throws Exception {
        return anxTiposRegimenFiscalExteriorDao.getListaAnxTiposRegimenFiscalExterior();
    }

}
