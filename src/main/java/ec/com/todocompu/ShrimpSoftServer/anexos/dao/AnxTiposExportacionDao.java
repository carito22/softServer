/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposExportacion;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface AnxTiposExportacionDao extends GenericDao<AnxTiposExportacion, Integer> {

    public List<AnxTiposExportacion> getListaAnxTiposExportacion() throws Exception;
}
