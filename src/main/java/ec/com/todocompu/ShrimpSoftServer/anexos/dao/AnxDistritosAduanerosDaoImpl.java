/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxDistritosAduaneros;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class AnxDistritosAduanerosDaoImpl extends GenericDaoImpl<AnxDistritosAduaneros, Integer> implements AnxDistritosAduanerosDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxDistritosAduaneros> getListaAnxDistritosAduaneros() throws Exception {
        String sql = "SELECT * FROM anexo.anx_distritos_aduaneros";
        return genericSQLDao.obtenerPorSql(sql, AnxDistritosAduaneros.class);
    }

}
