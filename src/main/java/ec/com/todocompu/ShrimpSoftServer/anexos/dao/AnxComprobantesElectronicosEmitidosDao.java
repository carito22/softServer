/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosEmitidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosEmitidosPk;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author VALDIVIEZO
 */
public interface AnxComprobantesElectronicosEmitidosDao extends GenericDao<AnxComprobantesElectronicosEmitidos, AnxComprobantesElectronicosEmitidosPk> {

    public AnxComprobantesElectronicosEmitidos obtenerAnxComprobantesElectronicosEmitidos(String empresa, String periodo, String clave) throws Exception;

    public String obtenerXMLComprobanteElectronicoEmitido(String empresa, String periodo, String clave) throws Exception;

    public boolean insertarComprobantesElectronico(AnxComprobantesElectronicosEmitidos comprobantesElectronicos, SisSuceso sisSuceso) throws Exception;

    public boolean actualizarComprobantesElectronico(AnxComprobantesElectronicosEmitidos comprobantesElectronicos, SisSuceso sisSuceso) throws Exception;

    public boolean cambiarEstadoCancelado(String empresa, String periodo, String clave, boolean estado, SisSuceso sisSuceso) throws Exception;

    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicos(String empresa) throws Exception;

    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicosConsulta(String empresa, String periodo) throws Exception;

    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicosEmitidos(String empresa, String periodo) throws Exception;

}
