package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteImportarTO;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.Map;

@Transactional
public interface AnxComprobantesElectronicosRecibidosService {

    public String obtenerXMLComprobanteElectronico(String empresa, String periodo, String clave) throws Exception;

    public AnxComprobantesElectronicosRecibidos obtenerAnxComprobantesElectronicosRecibidos(String empresa, String periodo, String clave) throws Exception;

    public Boolean insertarComprobantesElectronicos(List<AnxComprobantesElectronicosRecibidos> listaComprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception;

    public Boolean insertarComprobantesElectronico(AnxComprobantesElectronicosRecibidos comprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception;

    public String insertarComprobantesElectronicosRecibidosLote(List<AnxComprobantesElectronicosRecibidos> listaComprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception;

    public List<AnxComprobantesElectronicosRecibidos> listarComprobantesElectronicos(String empresa, String periodo, String tipoVista) throws Exception;

    public Map<String, Object> cambiarEstadoCancelado(String empresa, String periodo, String clave, boolean estado, String tipo, List<AnxComprobantesElectronicosRecibidos> listadoComprobantes, SisInfoTO sisInfoTO) throws Exception;

    public String verificarImportados(String empresa, String periodo, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteComprobantesRecibidos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxComprobantesElectronicosRecibidos> listadoComprobantesRecibidos, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String insertarComprobantesRecibidosLote(List<ComprobanteImportarTO> listaComprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception;

}
