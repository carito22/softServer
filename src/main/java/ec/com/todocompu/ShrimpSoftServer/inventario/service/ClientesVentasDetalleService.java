/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClientesVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author krlos1206
 */
@Transactional
public interface ClientesVentasDetalleService {

    public void insertarClientesVentasDetalles(List<InvClientesVentasDetalleTO> invClientesVentasDetalleTO, String cliEmpresa, String cliCodigo, SisInfoTO sisInfoTO) throws Exception;

}
