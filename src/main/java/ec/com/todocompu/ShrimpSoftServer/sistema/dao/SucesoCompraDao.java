/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCompra;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface SucesoCompraDao extends GenericDao<SisSucesoCompra, Integer> {

    public List<SisSucesoCompra> getListaSisSucesoCompra(Integer suceso) throws Exception;
}
