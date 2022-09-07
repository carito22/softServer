/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author krlos1206
 */
@Transactional
public interface ClientesDireccionesService {

    public void insertarClientesDirecciones(List<InvClientesDirecciones> clientesDirecciones, String cliEmpresa, String cliCodigo, SisInfoTO sisInfoTO) throws Exception;
    
    public List<InvClientesDirecciones> listarInvClientesDirecciones(String dirEmpresa, String dirCodigo) throws Exception;

    public InvClientesDirecciones obtenerDireccion(String cliEmpresa, String cliCodigoEstablecimiento, String cliCodigo) throws Exception;

}
