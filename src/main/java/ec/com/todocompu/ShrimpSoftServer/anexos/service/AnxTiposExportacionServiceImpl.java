/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxTiposExportacionDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposExportacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class AnxTiposExportacionServiceImpl implements AnxTiposExportacionService {

    @Autowired
    private AnxTiposExportacionDao anxTiposExportacionDao;

    @Override
    public List<AnxTiposExportacion> getListaAnxTiposExportacion() throws Exception {
        return anxTiposExportacionDao.getListaAnxTiposExportacion();
    }

}
