/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCliente;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface SisSucesoClienteService {

    public List<SisSucesoCliente> getListaSisSucesoCliente(String empresa, String cliCodigo) throws Exception;

    public List<SisSucesoCliente> getListaSisSucesoCliente(Integer suceso) throws Exception;
}
