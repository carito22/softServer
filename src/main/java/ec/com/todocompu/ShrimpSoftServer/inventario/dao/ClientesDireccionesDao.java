/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import java.util.List;

/**
 *
 * @author krlos1206
 */
public interface ClientesDireccionesDao extends GenericDao<InvClientesDirecciones, Integer> {
    
    public List<InvClientesDirecciones> listarInvClientesDirecciones(String empresa, String codigo) throws Exception;
    
    public InvClientesDirecciones obtenerDireccion(String dirEmpresa, String dirCodigo, String cliente) throws Exception;

}
