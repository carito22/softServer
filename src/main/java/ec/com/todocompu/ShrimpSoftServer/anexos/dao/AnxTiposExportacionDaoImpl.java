/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposExportacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class AnxTiposExportacionDaoImpl extends GenericDaoImpl<AnxTiposExportacion, Integer> implements AnxTiposExportacionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxTiposExportacion> getListaAnxTiposExportacion() throws Exception {
        String sql = "SELECT * FROM anexo.anx_tipos_exportacion";
        return genericSQLDao.obtenerPorSql(sql, AnxTiposExportacion.class);
    }

}
