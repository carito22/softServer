/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumosPK;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface ConfiguracionConsumosDao extends GenericDao<SisConfiguracionConsumos, SisConfiguracionConsumosPK> {

    public List<SisConfiguracionConsumos> listarSisConfiguracionConsumos(String empresa) throws Exception;
}
