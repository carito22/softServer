/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoIngresoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoIngreso;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface PrdReprocesoIngresoDao extends GenericDao<PrdReprocesoIngreso, Integer> {

    public List<PrdReprocesoIngresoTO> getListarPrdReprocesoIngresoTO(String empresa, String repPeriodo, String repMotivo, String repNumero) throws Exception;
}
