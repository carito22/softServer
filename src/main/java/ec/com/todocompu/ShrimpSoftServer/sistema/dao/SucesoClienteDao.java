package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCliente;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trabajo
 */
public interface SucesoClienteDao extends GenericDao<SisSucesoCliente, Integer> {

    public List<SisSucesoCliente> getListaSisSucesoCliente(Integer suceso) throws Exception;

    public List<SisSucesoCliente> getListaSisSucesoCliente(String empresa, String codigo) throws Exception;
}
