/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteImportarTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosEmitidos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface AnxComprobantesElectronicosEmitidosService {

    public String obtenerXMLComprobanteElectronicoEmitido(String empresa, String periodo, String clave) throws Exception;

    public AnxComprobantesElectronicosEmitidos obtenerAnxComprobantesElectronicosEmitidos(String empresa, String periodo, String clave) throws Exception;

    @Transactional
    public String insertarComprobantesElectronicosLote(List<AnxComprobantesElectronicosEmitidos> listaComprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> insertarClientesRezagadosLote(List<AnxComprobantesElectronicosEmitidos> listado, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Boolean insertarComprobantesElectronico(AnxComprobantesElectronicosEmitidos comprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception;

    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicos(String empresa, String periodo) throws Exception;

    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicosEmitidos(String empresa, String periodo) throws Exception;

    public String cambiarEstadoCancelado(String empresa, String periodo, String clave, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosProductosDeXML(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosParaComprobantesElectronicosEmitidos(Map<String, Object> map) throws Exception;

    public Map<String, Object> generarComprobantesEmitidos(List<AnxComprobantesElectronicosEmitidos> listaComprobantesElectronicos, String bodega, String motivo, String precioVenta, Integer formaCobro, String codigoProducto, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String insertarComprobantesEmitidosLote(List<ComprobanteImportarTO> listaComprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception;

}
