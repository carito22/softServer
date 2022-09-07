/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClientesVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesVentasDetalle;
import java.util.List;

/**
 *
 * @author krlos1206
 */
public interface ClientesVentasDetalleDao extends GenericDao<InvClientesVentasDetalle, Integer> {
    
    public List<InvClientesVentasDetalle> listarInvClientesVentasDetalle(String cliEmpresa, String cliCodigo, int grupo) throws Exception;
    
    public List<InvClientesVentasDetalleTO> listarInvClientesVentasDetalleTO(String cliEmpresa, String cliCodigo, int grupo) throws Exception;

}
