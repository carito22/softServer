package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoFormaCobroVentaDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaVentasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasFormaCobro;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoFormaCobroVenta;

@Repository
public class VentasFormaPagoDaoImpl extends GenericDaoImpl<InvVentasFormaCobro, Integer> implements VentasFormaPagoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoFormaCobroVentaDao sucesoFormaCobroVentaDao;

    @Override
    public Boolean accionInvVentasFormaCobro(InvVentasFormaCobro invVentasFormaPago, SisSuceso sisSuceso, char accion)
            throws Exception {
        if (accion == 'I') {
            insertar(invVentasFormaPago);
        }
        if (accion == 'M') {

            actualizar(invVentasFormaPago);
        }
        if (accion == 'E') {

            eliminar(invVentasFormaPago);
        }
        sucesoDao.insertar(sisSuceso);
        if (accion == 'M' || accion == 'I') {
            SisSucesoFormaCobroVenta sucesoFp = new SisSucesoFormaCobroVenta();
            String json = UtilsJSON.objetoToJson(invVentasFormaPago);
            sucesoFp.setSusJson(json);
            sucesoFp.setSisSuceso(sisSuceso);
            sucesoFp.setInvVentasFormaCobro(invVentasFormaPago);
            sucesoFormaCobroVentaDao.insertar(sucesoFp);
        }
        return true;
    }

    @Override
    public InvVentasFormaCobro buscarVentasFormaPago(Integer secuencial) throws Exception {
        return obtenerPorId(InvVentasFormaCobro.class, secuencial);
    }

    @Override
    public InvVentasFormaPagoTO getInvVentasFormaCobro(String empresa, String ctaContable) throws Exception {
        String sql = "SELECT * FROM inventario.inv_ventas_forma_cobro WHERE (cta_codigo = '" + ctaContable + "') AND (cta_empresa = '" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, InvVentasFormaPagoTO.class).get(0);
    }

    @Override
    public Boolean buscarInvVentasFormaCobro(String ctaContable, String empresa) throws Exception {
        String sql = "SELECT COUNT(*) FROM inventario.inv_ventas_forma_cobro WHERE (cta_codigo = '" + ctaContable + "') AND (cta_empresa = '" + empresa + "')";

        return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0)) == 0) ? false : true;
    }

    @Override
    public List<InvComboFormaPagoTO> getComboFormaPagoVenta(String empresa) throws Exception {
        String sql = "SELECT  fc_detalle fp_detalle, cta_codigo as cta_codigo, fc_inactivo IS NOT NULL validar,fc_tipo_principal as fp_tipo_principal, fc_secuencial as fp_secuencial FROM inventario.inv_ventas_forma_cobro "
                + "WHERE (cta_empresa = '" + empresa + "') AND (NOT fc_inactivo) ORDER BY fc_detalle";
        return genericSQLDao.obtenerPorSql(sql, InvComboFormaPagoTO.class);
    }

    @Override
    public String getCuentaSectorFormaPagoVenta(String empresa, String detalleFormaPago) throws Exception {
        String retorno = "";
        String sql = "SELECT sec_codigo, cta_codigo FROM inventario.inv_ventas_forma_cobro "
                + "WHERE sec_empresa = ('" + empresa + "') AND fc_detalle = ('" + detalleFormaPago + "')";
        Object[] array = (Object[]) genericSQLDao.obtenerPorSql(sql).get(0);
        if (array != null) {
            retorno = array[0].toString().trim() + "|" + array[1].toString().trim();
        } else {
            retorno = "";
        }
        return retorno;
    }

    @Override
    public List<InvListaVentasFormaPagoTO> getInvListaVentasFormaPagoTO(String empresa) throws Exception {
        String sql = "SELECT fc_secuencial, upper(fc_detalle) fc_detalle, fc_tipo_principal, "
                + "fc_inactivo, sec_codigo, inv_ventas_forma_cobro.cta_codigo "
                + "FROM inventario.inv_ventas_forma_cobro INNER JOIN contabilidad.con_cuentas "
                + "ON inv_ventas_forma_cobro.cta_empresa = con_cuentas.cta_empresa AND "
                + "inv_ventas_forma_cobro.cta_codigo = con_cuentas.cta_codigo "
                + "WHERE (inv_ventas_forma_cobro.usr_empresa = '" + empresa + "') ORDER BY fc_tipo_principal='CUENTAS POR COBRAR', 2";

        return genericSQLDao.obtenerPorSql(sql, InvListaVentasFormaPagoTO.class);
    }

    @Override
    public List<InvVentasFormaPagoTO> getListaInvVentasFormaPagoTO(String empresa, boolean incluirInactivos) throws Exception {
        String sqlInactivos = "";
        if (!incluirInactivos) {
            sqlInactivos = " AND fc.fc_inactivo = FALSE ";
        }

        String sql = "SELECT * FROM inventario.inv_ventas_forma_cobro as fc WHERE (fc.usr_empresa = '" + empresa + "'" + sqlInactivos + ") ORDER BY fc_tipo_principal='CUENTAS POR COBRAR', 2";
        return genericSQLDao.obtenerPorSql(sql, InvVentasFormaPagoTO.class);
    }

}
