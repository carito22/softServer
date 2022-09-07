/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasProgramadas;
import ec.com.todocompu.ShrimpSoftUtils.quartz.TO.InvComprasProgramadasTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;

/**
 *
 * @author krlos1206
 */
public interface ComprasProgramadasDao extends GenericDao<InvComprasProgramadas, Integer> {

    public List<InvComprasProgramadasTO> invListadoComprasProgramadasTO(String fecha) throws Exception;

    public String generarInvComprasProgramadas(InvComprasProgramadasTO invComprasProgramadas, String fecha, String usuario) throws Exception;

    public InvComprasProgramadas configurarCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception;

    public InvComprasProgramadas editarConfiguracionCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception;

    public InvComprasProgramadas eliminarConfiguracionCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception;

    public List<InvComprasProgramadasTO> listarInvComprasProgramadasTO(String empresa) throws Exception;

}
