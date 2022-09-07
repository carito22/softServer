package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraElectronica;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaComprasFacturasPorNumeroTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

@Repository
public class CompraElectronicaDaoImpl extends GenericDaoImpl<AnxCompraElectronica, Integer>
        implements CompraElectronicaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private CompraDao compraDao;

    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public AnxCompraElectronica buscarAnxCompraElectronica(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        String sql = "SELECT e_secuencial  " + "FROM anexo.anx_compra_electronica  " + "WHERE comp_empresa = '"
                + empresa + "' AND comp_periodo = '" + periodo + "' AND   " + "comp_motivo = '" + motivo
                + "' and comp_numero = '" + numero + "';";
        //String obj = (String) genericSQLDao.obtenerObjetoPorSql(sql);
        return obtenerPorId(AnxCompraElectronica.class, (Integer) genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public Boolean accionAnxCompraElectronica(AnxCompraElectronica anxCompraElectronica, AnxCompra anxCompra,
            SisSuceso sisSuceso, char accion) throws Exception {
        if (accion == 'I') {
            insertar(anxCompraElectronica);
        }
        if (accion == 'M') {
            actualizar(anxCompraElectronica);
        }
        if (accion == 'E') {
            eliminar(anxCompraElectronica);
        }
        if (anxCompra != null) {
            compraDao.actualizar(anxCompra);
        }

        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String getXmlComprobanteRetencion(String empresa, String ePeriodo, String eMotivo, String eNumero)
            throws Exception {

        empresa = empresa == null ? empresa : "'" + empresa + "'";
        ePeriodo = ePeriodo == null ? ePeriodo : "'" + ePeriodo + "'";
        eMotivo = eMotivo == null ? eMotivo : "'" + eMotivo + "'";
        eNumero = eNumero == null ? eNumero : "'" + eNumero + "'";
        String sql = "SELECT e_xml FROM anexo.anx_compra_electronica " + "WHERE comp_empresa = " + empresa
                + " and  comp_periodo = " + ePeriodo + " and comp_motivo = " + eMotivo + " and comp_numero = " + eNumero
                + ";";
        return new String((byte[]) genericSQLDao.obtenerObjetoPorSql(sql), "UTF-8");
    }

    @Override
    public boolean comprobarAnxCompraElectronica(String empresa, String periodo, String motivo, String numero)
            throws Exception {

        String sql = "SELECT COUNT(*)!=0 FROM anexo.anx_compra_electronica " + "WHERE (comp_empresa = '" + empresa
                + "' AND comp_periodo = '" + periodo + "' AND comp_motivo = '" + motivo + "' AND comp_numero = '"
                + numero + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public boolean comprobarRetencionAutorizadaProcesamiento(String empresa, String periodo, String motivo,
            String numero) throws Exception {
        String sql = "SELECT COUNT(*)!=0 FROM anexo.anx_compra_electronica "
                + "WHERE (comp_empresa = '" + empresa + "' AND comp_periodo = '" + periodo + "' AND comp_motivo = '" + motivo + "' AND comp_numero = '" + numero + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public InvConsultaComprasFacturasPorNumeroTO getConsultaPKRetencion(String codEmpresa, String numClaveAcceso,
            String numAutorizacion) throws Exception {
        String sql = "SELECT comp_empresa, comp_periodo, comp_motivo, comp_numero "
                + "FROM anexo.anx_compra_electronica " + "WHERE (comp_empresa = '" + codEmpresa
                + "' AND e_clave_acceso like '%" + numClaveAcceso + "%' AND e_autorizacion_numero = '" + numAutorizacion
                + "');";

        return genericSQLDao.obtenerObjetoPorSql(sql, InvConsultaComprasFacturasPorNumeroTO.class);
    }

    @Override
    public List<AnxListaLiquidacionComprasPendientesTO> getListaLiquidacionCompraPendientes(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.fun_liquidacion_compras_electronicas_listado('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxListaLiquidacionComprasPendientesTO.class);
    }

    @Override
    public String getXmlLiquidacionCompras(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        ePeriodo = ePeriodo == null ? ePeriodo : "'" + ePeriodo + "'";
        eMotivo = eMotivo == null ? eMotivo : "'" + eMotivo + "'";
        eNumero = eNumero == null ? eNumero : "'" + eNumero + "'";
        String sql = "SELECT e_xml FROM anexo.anx_liquidacion_compras_electronica " + "WHERE comp_empresa = " + empresa
                + " and  comp_periodo = " + ePeriodo + " and comp_motivo = " + eMotivo + " and comp_numero = " + eNumero
                + ";";
        return new String((byte[]) genericSQLDao.obtenerObjetoPorSql(sql), "UTF-8");
    }

}
