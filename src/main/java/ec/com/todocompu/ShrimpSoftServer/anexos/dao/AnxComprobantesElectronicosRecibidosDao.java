package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidosPk;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface AnxComprobantesElectronicosRecibidosDao extends GenericDao<AnxComprobantesElectronicosRecibidos, AnxComprobantesElectronicosRecibidosPk> {

    public String obtenerXMLComprobanteElectronico(String empresa, String periodo, String clave) throws Exception;
    
    public AnxComprobantesElectronicosRecibidos obtenerAnxComprobantesElectronicosRecibidosPorPK(AnxComprobantesElectronicosRecibidosPk clave) throws Exception;

    public AnxComprobantesElectronicosRecibidos obtenerAnxComprobantesElectronicosRecibidos(String empresa, String periodo, String clave) throws Exception;

    public boolean insertarComprobantesElectronicos(List<AnxComprobantesElectronicosRecibidos> listaComprobantesElectronicos, List<SisSuceso> listadoSisSuceso) throws Exception;

    public boolean insertarComprobantesElectronico(AnxComprobantesElectronicosRecibidos comprobantesElectronicos, SisSuceso sisSuceso) throws Exception;

    public boolean actualizarComprobantesElectronico(AnxComprobantesElectronicosRecibidos comprobantesElectronicos, SisSuceso sisSuceso) throws Exception;

    public List<AnxComprobantesElectronicosRecibidos> listarComprobantesElectronicos(String empresa) throws Exception;

    public List<AnxComprobantesElectronicosRecibidos> listarComprobantesElectronicosParaCompras(String empresa) throws Exception;

    public boolean cambiarEstadoCancelado(String empresa, String periodo, String clave, boolean estado, SisSuceso sisSuceso) throws Exception;

    public String verificarImportados(String empresa, String periodo, SisSuceso sisSuceso) throws Exception;

    public List<AnxComprobantesElectronicosRecibidos> listarComprobantesElectronicosConsulta(String empresa, String periodo) throws Exception;

}
