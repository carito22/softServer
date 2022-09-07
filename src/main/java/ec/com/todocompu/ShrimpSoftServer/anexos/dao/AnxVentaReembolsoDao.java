/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaReembolso;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface AnxVentaReembolsoDao extends GenericDao<AnxVentaReembolso, Integer> {

    public List<AnxVentaReembolsoTO> getListaAnxVentaReembolsoTO(String vtaEmpresa, String vtaPeriodo, String vtaMotivo, String vtaNumero) throws Exception;
}
