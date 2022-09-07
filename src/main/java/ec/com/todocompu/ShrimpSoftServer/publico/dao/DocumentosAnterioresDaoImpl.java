package ec.com.todocompu.ShrimpSoftServer.publico.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.publico.DocumentosAnteriores;

@Repository
public class DocumentosAnterioresDaoImpl extends GenericDaoImpl<DocumentosAnteriores, String> implements DocumentosAnterioresDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<String> listarRucs() throws Exception {
        String sql = "select ruc_emisor from public.documentosanteriores group by ruc_emisor;";
        List<Object> rucs = genericSQLDao.obtenerPorSql(sql, Object.class);
        return genericSQLDao.obtenerPorSql(sql, String.class);
    }

    @Override
    public DocumentosAnteriores obtenerDocumentoAnterior(String rucEmisor, String numeroComprobante, String empresa) throws Exception {
        String sql = "select * from public.documentosanteriores where ruc_emisor = '" + rucEmisor + "' and"
                + " documento_numero = '" + numeroComprobante + "' and empresa = '" + empresa + "';";
        return genericSQLDao.obtenerObjetoPorSql(sql, DocumentosAnteriores.class);
    }

}
