/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasProgramadas;
import ec.com.todocompu.ShrimpSoftUtils.quartz.TO.InvComprasProgramadasTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author krlos1206
 */
public interface ComprasProgramadasService {

    public List<InvComprasProgramadasTO> invListadoComprasProgramadasTO(String fecha) throws Exception;

    public List<InvComprasProgramadasTO> listarInvComprasProgramadasTO(String empresa) throws Exception;

    public boolean generarInvComprasProgramadas(InvComprasProgramadasTO invComprasProgramadas, String fecha);

    @Transactional
    public InvComprasProgramadas configurarCompraProgramada(String empresa, String numeroCompra,
            InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public InvComprasProgramadas editarConfiguracionCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public InvComprasProgramadas eliminarConfiguracionCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception;

}
