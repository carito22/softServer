/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxVentaReembolsoDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trabajo
 */
@Service
public class AnxVentaReembolsoServiceImpl implements AnxVentaReembolsoService {

    @Autowired
    private AnxVentaReembolsoDao anxVentaReembolsoDao;

    @Override
    public List<AnxVentaReembolsoTO> getListaAnxVentaReembolsoTO(String vtaEmpresa, String vtaPeriodo, String vtaMotivo, String vtaNumero) throws Exception {
        return anxVentaReembolsoDao.getListaAnxVentaReembolsoTO(vtaEmpresa, vtaPeriodo, vtaMotivo, vtaNumero);
    }

}
