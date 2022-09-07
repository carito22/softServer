/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TipoContratoService {

    public InvClienteContratoTipo obtenerInvTipoContrato(String empresa, String codigo) throws Exception;

    public InvClienteContratoTipo insertarInvTipoContrato(InvClienteContratoTipo invTipoContrato, SisInfoTO sisInfoTO) throws Exception;

    public List<InvClienteContratoTipo> getListarInvTipoContrato(String empresa) throws Exception;

    public InvClienteContratoTipo modificarInvTipoContrato(InvClienteContratoTipo invTipoContrato, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarInvTipoContrato(InvClienteContratoTipoPK pk, SisInfoTO sisInfoTO) throws Exception;

}
