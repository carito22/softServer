/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author VALDIVIEZO
 */
public interface ClienteContratoDao extends GenericDao<InvClienteContrato, Integer> {

    public List<InvClienteContratoTO> getListaInvClienteContratoTO(String empresa, String cliente, String busqueda, String nRegistros) throws Exception;

    public InvClienteContrato obtenerInvClienteContrato(String empresa, String numeroContrato) throws Exception;

    public InvClienteContrato obtenerInvClienteContrato(String empresa, String cliCodigo, String ip) throws Exception;

    public InvClienteContrato obtenerInvClienteContrato(Integer secuencial) throws Exception;

    public boolean insertarInvClienteContrato(InvClienteContrato invClienteContrato, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvClienteContrato(InvClienteContrato invClienteContrato, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarInvClienteContrato(InvClienteContrato invClienteContrato, SisSuceso sisSuceso) throws Exception;

    public List<InvClienteContratoDatosAdjuntos> listarImagenesDeClienteContrato(Integer secuencial) throws Exception;
}
