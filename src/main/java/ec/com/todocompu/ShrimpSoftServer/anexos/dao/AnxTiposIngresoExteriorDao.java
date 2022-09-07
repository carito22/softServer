/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposIngresoExterior;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface AnxTiposIngresoExteriorDao extends GenericDao<AnxTiposIngresoExterior, Integer> {

    public List<AnxTiposIngresoExterior> getListaAnxTiposIngresoExterior() throws Exception;
}
