package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoFormaPagoCompraDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoFormaPagoCompra;

@Repository
public class ComprasFormaPagoDaoImpl extends GenericDaoImpl<InvComprasFormaPago, Integer> implements ComprasFormaPagoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoFormaPagoCompraDao sucesoFormaPagoCompraDao;

    @Override
    public Boolean accionInvComprasFormaPago(InvComprasFormaPago invComprasFormaPago, SisSuceso sisSuceso, char accion)
            throws Exception {
        if (accion == 'I') {
            insertar(invComprasFormaPago);
        }
        if (accion == 'M') {

            actualizar(invComprasFormaPago);
        }
        if (accion == 'E') {

            eliminar(invComprasFormaPago);
        }
        sucesoDao.insertar(sisSuceso);

        if (accion == 'M' || accion == 'I') {
            SisSucesoFormaPagoCompra sucesoFp = new SisSucesoFormaPagoCompra();
            String json = UtilsJSON.objetoToJson(invComprasFormaPago);
            sucesoFp.setSusJson(json);
            sucesoFp.setSisSuceso(sisSuceso);
            sucesoFp.setInvComprasFormaPago(invComprasFormaPago);
            sucesoFormaPagoCompraDao.insertar(sucesoFp);
        }
        return true;
    }

    @Override
    public InvComprasFormaPago buscarComprasFormaPago(Integer secuencial) throws Exception {
        return obtenerPorId(InvComprasFormaPago.class, secuencial);
    }

    @Override
    public InvComprasFormaPagoTO getInvComprasFormaPago(String empresa, String ctaContable) throws Exception {
        String sql = "SELECT * FROM inventario.inv_compras_forma_pago WHERE "
                + "(cta_empresa = '" + empresa + "') AND "
                + "(cta_codigo = '" + ctaContable + "') ";
        return genericSQLDao.obtenerPorSql(sql, InvComprasFormaPagoTO.class).get(0);
    }

    @Override
    public Boolean buscarInvComprasFormaPago(String ctaContable, String empresa, Integer secuencial) throws Exception {
        String sql = "SELECT COUNT(*) FROM inventario.inv_compras_forma_pago WHERE "
                + "(cta_empresa = '" + empresa + "') AND "
                + "(cta_codigo = '" + ctaContable + "') "
                + (secuencial != null ? " AND fp_secuencial !=" + secuencial : "");//MODIFICAR

        return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0))) == 1 ? true : false;
    }

    @Override
    public List<InvComboFormaPagoTO> getComboFormaPagoCompra(String empresa) throws Exception {
        String sql = "SELECT  UPPER(fp_detalle) fp_detalle, inv_compras_forma_pago.cta_codigo, ban_cuenta.cta_empresa IS NOT NULL validar, fp_tipo_principal, fp_secuencial "
                + "FROM inventario.inv_compras_forma_pago INNER JOIN contabilidad.con_cuentas LEFT JOIN banco.ban_cuenta "
                + " ON con_cuentas.cta_empresa = ban_cuenta.cta_empresa AND "
                + " con_cuentas.cta_codigo = ban_cuenta.cta_cuenta_contable "
                + "ON inv_compras_forma_pago.cta_empresa = con_cuentas.cta_empresa AND "
                + "inv_compras_forma_pago.cta_codigo = con_cuentas.cta_codigo "
                + "WHERE inv_compras_forma_pago.cta_empresa = '" + empresa
                + "' AND NOT fp_inactivo ORDER BY fp_tipo_principal='CUENTAS POR PAGAR', 1";

        return genericSQLDao.obtenerPorSql(sql, InvComboFormaPagoTO.class);
    }

    @Override
    public String getCuentaSectorFormaPagoCompra(String empresa, String detalleFormaPago) throws Exception {
        String retorno = "";
        String sql = "SELECT sec_codigo, cta_codigo " + "FROM inventario.inv_compras_forma_pago "
                + "WHERE sec_empresa = ('" + empresa + "') AND fp_detalle = ('" + detalleFormaPago + "')";
        Object[] array = (Object[]) genericSQLDao.obtenerPorSql(sql).get(0);
        if (array != null) {
            retorno = array[0].toString().trim() + "|" + array[1].toString().trim();
        } else {
            retorno = "";
        }
        return retorno;
    }

    @Override
    public List<InvListaComprasFormaPagoTO> getInvListaComprasFormaPagoTO(String empresa) throws Exception {
        String sql = "SELECT fp_secuencial, upper(cta_detalle) fp_detalle, fp_inactivo, sec_codigo, inv_compras_forma_pago.cta_codigo,fp_tipo_principal "
                + "FROM inventario.inv_compras_forma_pago  INNER JOIN contabilidad.con_cuentas "
                + "ON inv_compras_forma_pago.cta_empresa = con_cuentas.cta_empresa AND "
                + "inv_compras_forma_pago.cta_codigo = con_cuentas.cta_codigo "
                + "WHERE (inv_compras_forma_pago.usr_empresa = '" + empresa
                + "') ORDER BY 2";

        return genericSQLDao.obtenerPorSql(sql, InvListaComprasFormaPagoTO.class);
    }

    @Override
    public List<InvComprasFormaPagoTO> getInvListaInvComprasFormaPagoTO(String empresa, boolean incluirInactivos) throws Exception {
        String sqlInactivos = "";
        if (!incluirInactivos) {
            sqlInactivos = " AND fp.fp_inactivo = FALSE ";
        }

        String sql = "SELECT * FROM inventario.inv_compras_forma_pago as fp WHERE (fp.usr_empresa = '" + empresa + "'" + sqlInactivos + ") ORDER BY fp_tipo_principal='CUENTAS POR PAGAR', 2";
        return genericSQLDao.obtenerPorSql(sql, InvComprasFormaPagoTO.class);

    }

}
