package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidosPk;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class AnxComprobantesElectronicosRecibidosDaoImpl extends GenericDaoImpl<AnxComprobantesElectronicosRecibidos, AnxComprobantesElectronicosRecibidosPk> implements AnxComprobantesElectronicosRecibidosDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public AnxComprobantesElectronicosRecibidos obtenerAnxComprobantesElectronicosRecibidos(String empresa, String periodo, String clave) throws Exception {
        AnxComprobantesElectronicosRecibidosPk pk = new AnxComprobantesElectronicosRecibidosPk(empresa, periodo, clave);
        return obtenerPorId(AnxComprobantesElectronicosRecibidos.class, pk);
    }
    
    @Override
    public AnxComprobantesElectronicosRecibidos obtenerAnxComprobantesElectronicosRecibidosPorPK(AnxComprobantesElectronicosRecibidosPk clave) throws Exception {
        return obtenerPorId(AnxComprobantesElectronicosRecibidos.class, clave);
    }

    @Override
    public boolean insertarComprobantesElectronicos(List<AnxComprobantesElectronicosRecibidos> listaComprobantesElectronicos, List<SisSuceso> listadoSisSuceso) throws Exception {
        insertar(listaComprobantesElectronicos);
        sucesoDao.insertar(listadoSisSuceso);
        return true;
    }

    @Override
    public boolean insertarComprobantesElectronico(AnxComprobantesElectronicosRecibidos comprobantesElectronicos, SisSuceso sisSuceso) throws Exception {
        insertar(comprobantesElectronicos);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean actualizarComprobantesElectronico(AnxComprobantesElectronicosRecibidos comprobantesElectronicos, SisSuceso sisSuceso) throws Exception {
        actualizar(comprobantesElectronicos);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String obtenerXMLComprobanteElectronico(String empresa, String periodo, String clave) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        String sql = "SELECT compro_xml FROM anexo.anx_comprobantes_electronicos_recibidos " + "WHERE compro_empresa = " + empresa
                + " and  compro_periodo = '" + periodo + "' and compro_clave_acceso = '" + clave + "'";

        Object respuesta = genericSQLDao.obtenerObjetoPorSql(sql);
        if (respuesta != null) {
            return new String((byte[]) genericSQLDao.obtenerObjetoPorSql(sql), "UTF-8");
        }

        return null;
    }

    @Override
    public List<AnxComprobantesElectronicosRecibidos> listarComprobantesElectronicos(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.anx_comprobantes_electronicos_recibidos WHERE compro_empresa = '" + empresa + "' AND compro_numero_autorizacion NOT IN (select anx_compra.comp_autorizacion from anexo.anx_compra where comp_empresa='" + empresa + "');";
        List<AnxComprobantesElectronicosRecibidos> listadoComprobantes = genericSQLDao.obtenerPorSql(sql, AnxComprobantesElectronicosRecibidos.class);
        return listadoComprobantes;
    }

    @Override
    public List<AnxComprobantesElectronicosRecibidos> listarComprobantesElectronicosParaCompras(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.anx_comprobantes_electronicos_recibidos WHERE compro_empresa = '" + empresa
                + "' AND compro_numero_autorizacion NOT IN (select anx_compra.comp_autorizacion from anexo.anx_compra where comp_empresa='" + empresa + "')"
                + " AND compro_importado is FALSE AND compro_cancelado is FALSE AND compro_comprobante !='Comprobante de Retención';";
        List<AnxComprobantesElectronicosRecibidos> listadoComprobantes = genericSQLDao.obtenerPorSql(sql, AnxComprobantesElectronicosRecibidos.class);
        return listadoComprobantes;
    }

    @Override
    public List<AnxComprobantesElectronicosRecibidos> listarComprobantesElectronicosConsulta(String empresa, String periodo) throws Exception {
        String sql = "SELECT * FROM anexo.anx_comprobantes_electronicos_recibidos WHERE compro_empresa = '" + empresa + "' AND compro_periodo= '" + periodo + "';";
        List<AnxComprobantesElectronicosRecibidos> listadoComprobantes = genericSQLDao.obtenerPorSql(sql, AnxComprobantesElectronicosRecibidos.class);
        return listadoComprobantes;
    }

    @Override
    public boolean cambiarEstadoCancelado(String empresa, String periodo, String clave, boolean estado, SisSuceso sisSuceso) {
        String sql = "UPDATE anexo.anx_comprobantes_electronicos_recibidos SET compro_cancelado = " + estado + " WHERE compro_empresa= '" + empresa + "' and compro_periodo= '" + periodo + "' and compro_clave_acceso= '" + clave + "';";
        genericSQLDao.ejecutarSQL(sql);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String verificarImportados(String empresa, String periodo, SisSuceso sisSuceso) throws Exception {
        String sql = "SELECT * FROM anexo.fun_verificar_compras_electronicas('" + empresa + "', '" + periodo + "')";
        String respuesta = "";
        try {
            String respuestaFuncion = (String) genericSQLDao.obtenerObjetoPorSql(sql);
            if (!respuestaFuncion.equals("T ->> 0 registros afectados!")) {
                sucesoDao.insertar(sisSuceso);
                respuesta = respuestaFuncion;
            } else {
                respuesta = "FNo hubo registros que coincidan con compras";
            }
            //respuesta = "t";
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respuesta = e.getMessage() + ". N: " + sisSuceso.getSusClave();
        }
        return respuesta;
    }
}
