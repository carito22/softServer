/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoContrato;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface SisSucesoContratoService {

    public List<SisSucesoContrato> getListaSisSucesoContrato(Integer suceso) throws Exception;
}
