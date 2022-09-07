/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposRegimenFiscalExterior;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface AnxTiposRegimenFiscalExteriorDao extends GenericDao<AnxTiposRegimenFiscalExterior, Integer> {

    public List<AnxTiposRegimenFiscalExterior> getListaAnxTiposRegimenFiscalExterior() throws Exception;
}
