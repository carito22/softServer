/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosEmitidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosEmitidosPk;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class AnxComprobantesElectronicosEmitidosDaoImpl extends GenericDaoImpl<AnxComprobantesElectronicosEmitidos, AnxComprobantesElectronicosEmitidosPk> implements AnxComprobantesElectronicosEmitidosDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public String obtenerXMLComprobanteElectronicoEmitido(String empresa, String periodo, String clave) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        String sql = "SELECT compro_xml FROM anexo.anx_comprobantes_electronicos_emitidos " + "WHERE compro_empresa = " + empresa
                + " and  compro_periodo = '" + periodo + "' and compro_clave_acceso = '" + clave + "'";
        Object resp = genericSQLDao.obtenerObjetoPorSql(sql);
        if (resp != null) {
            return new String((byte[]) genericSQLDao.obtenerObjetoPorSql(sql), "UTF-8") ;
        }
        return "";
    }

    @Override
    public AnxComprobantesElectronicosEmitidos obtenerAnxComprobantesElectronicosEmitidos(String empresa, String periodo, String clave) throws Exception {
        AnxComprobantesElectronicosEmitidosPk pk = new AnxComprobantesElectronicosEmitidosPk(empresa, periodo, clave);
        return obtenerPorId(AnxComprobantesElectronicosEmitidos.class, pk);
    }

    @Override
    public boolean insertarComprobantesElectronico(AnxComprobantesElectronicosEmitidos comprobantesElectronicos, SisSuceso sisSuceso) throws Exception {
        insertar(comprobantesElectronicos);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean actualizarComprobantesElectronico(AnxComprobantesElectronicosEmitidos comprobantesElectronicos, SisSuceso sisSuceso) throws Exception {
        actualizar(comprobantesElectronicos);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean cambiarEstadoCancelado(String empresa, String periodo, String clave, boolean estado, SisSuceso sisSuceso) throws Exception {
        String sql = "UPDATE anexo.anx_comprobantes_electronicos_emitidos SET compro_cancelado = " + estado + " WHERE compro_empresa= '" + empresa + "' and compro_periodo= '" + periodo + "' and compro_clave_acceso= '" + clave + "';";
        genericSQLDao.ejecutarSQL(sql);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicos(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.anx_comprobantes_electronicos_emitidos WHERE compro_empresa = '" + empresa + "' AND compro_numero_autorizacion NOT IN (select anx_compra.comp_autorizacion from anexo.anx_compra where comp_empresa='" + empresa + "');";
        List<AnxComprobantesElectronicosEmitidos> listadoComprobantes = genericSQLDao.obtenerPorSql(sql, AnxComprobantesElectronicosEmitidos.class);
        return listadoComprobantes;
    }

    @Override
    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicosConsulta(String empresa, String periodo) throws Exception {
        String sql = "SELECT * FROM anexo.anx_comprobantes_electronicos_emitidos WHERE compro_empresa = '" + empresa + "' AND compro_periodo= '" + periodo + "';";
        List<AnxComprobantesElectronicosEmitidos> listadoComprobantes = genericSQLDao.obtenerPorSql(sql, AnxComprobantesElectronicosEmitidos.class);
        return listadoComprobantes;
    }

    @Override
    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicosEmitidos(String empresa, String periodo) throws Exception {

        String sqlPeriodo = periodo != null && !periodo.equals("") ? (" AND emitidos.compro_periodo= '" + periodo + "'") : "";
        String sql = "SELECT * from anexo.anx_comprobantes_electronicos_emitidos emitidos "
                + "LEFT JOIN anexo.anx_venta_electronica ret "
                + "ON emitidos.compro_empresa=ret.vta_empresa "
                + "AND emitidos.compro_clave_acceso= ret.e_clave_acceso "
                + "WHERE ret.e_clave_acceso is null "
                + "AND ret.vta_empresa is null "
                + "AND emitidos.compro_comprobante != 'Comprobante de Retención' "
                + "AND emitidos.compro_empresa = '" + empresa + "'" + sqlPeriodo + ";";
        List<AnxComprobantesElectronicosEmitidos> listadoComprobantes = genericSQLDao.obtenerPorSql(sql, AnxComprobantesElectronicosEmitidos.class);
        return listadoComprobantes;
    }

}
