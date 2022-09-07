/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxDistritosAduanerosDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxDistritosAduaneros;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class AnxDistritosAduanerosServiceImpl implements AnxDistritosAduanerosService {

    @Autowired
    private AnxDistritosAduanerosDao anxDistritosAduanerosDao;

    @Override
    public List<AnxDistritosAduaneros> getListaAnxDistritosAduaneros() throws Exception {
        return anxDistritosAduanerosDao.getListaAnxDistritosAduaneros();
    }

}
