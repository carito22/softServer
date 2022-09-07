/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface AnxVentaReembolsoService {

    public List<AnxVentaReembolsoTO> getListaAnxVentaReembolsoTO(String vtaEmpresa, String vtaPeriodo, String vtaMotivo, String vtaNumero) throws Exception;
}
