package ec.com.todocompu.ShrimpSoftServer.publico.dao;

import java.util.List;


import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.publico.DocumentosAnteriores;

public interface DocumentosAnterioresDao extends GenericDao<DocumentosAnteriores, String> {

    public List<String> listarRucs() throws Exception;

    public DocumentosAnteriores obtenerDocumentoAnterior(String rucEmisor, String numeroComprobante, String empresa) throws Exception;

}
