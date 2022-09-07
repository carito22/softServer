/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.banco.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

/**
 *
 * @author CarolValdiviezo
 */
public interface ChequeNumeracionService {

    @Transactional
    public boolean insertarBanChequeNumeracionTO(BanChequesNumeracionTO banBancoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean modificarBanChequeNumeracionTO(BanChequesNumeracionTO banBancoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarBanChequeNumeracionTO(BanChequesNumeracionTO banBancoTO, SisInfoTO sisInfoTO) throws Exception;
}
