/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface ClienteContratoService {

    public Map<String, Object> obtenerDatosParaContrato(Map<String, Object> map) throws Exception;

    public List<InvClienteContratoTO> getListarInvClienteContratoTO(String empresa, String cliCodigo, String busqueda, String nroRegistro) throws Exception;

    public List<InvClienteContratoDatosAdjuntos> listarImagenesDeClienteContrato(Integer secuencial) throws Exception;

    @Transactional
    public String insertarInvClienteContrato(InvClienteContrato invClienteContrato, List<InvClienteContratoDatosAdjuntos> listaImagenes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String modificarInvClienteContrato(InvClienteContrato invClienteContrato, List<InvClienteContratoDatosAdjuntos> listaImagenes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String modificarIPInvClienteContrato(Integer secuencial, String ip, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String eliminarInvClienteContrato(Integer secuencial, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> validarExistenciasDatos(List<InvClienteContrato> contratos, String empresa) throws Exception;

    public InvClienteContrato obtenerInvClienteContrato(Integer secuencial) throws Exception;

    public InvClienteContrato obtenerInvClienteContrato(String empresa, String numeroContrato) throws Exception;

    public InvClienteContrato obtenerInvClienteContrato(String empresa, String cliCodigo, String ip) throws Exception;
}
