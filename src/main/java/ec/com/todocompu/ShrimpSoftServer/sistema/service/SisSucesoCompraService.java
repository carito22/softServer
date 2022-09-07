/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCompra;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface SisSucesoCompraService {

    public List<SisSucesoCompra> getListaSisSucesoCompra(Integer suceso) throws Exception;
}
