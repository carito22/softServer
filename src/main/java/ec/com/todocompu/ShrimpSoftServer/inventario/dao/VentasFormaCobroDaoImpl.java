package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoFormaCobroVentaDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasFormaCobro;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoFormaCobroVenta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VentasFormaCobroDaoImpl extends GenericDaoImpl<InvVentasFormaCobro, Integer> implements VentasFormaCobroDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoFormaCobroVentaDao sucesoFormaCobroVentaDao;

    @Override
    public List<InvVentasFormaCobroTO> getListaInvVentasFormaCobroTO(String empresa, boolean incluirInactivos) throws Exception {
        String sqlInactivos = "";
        if (!incluirInactivos) {
            sqlInactivos = " AND fc.fc_inactivo = FALSE ";
        }
        String sql = "SELECT * FROM inventario.inv_ventas_forma_cobro as fc WHERE (fc.usr_empresa = '" + empresa + "'" + sqlInactivos + ") ORDER BY fc_tipo_principal='CUENTAS POR COBRAR', 2";
        return genericSQLDao.obtenerPorSql(sql, InvVentasFormaCobroTO.class);
    }

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
    public InvVentasFormaCobro buscarVentasFormaCobro(Integer secuencial) throws Exception {
        return obtenerPorId(InvVentasFormaCobro.class, secuencial);
    }

    @Override
    public InvVentasFormaCobroTO buscarInvVentaFormaCobroTO(String empresa, String ctaCodigo) throws Exception {
        String sql = "SELECT * FROM inventario.inv_ventas_forma_cobro WHERE (cta_codigo = '" + ctaCodigo + "') AND (cta_empresa = '" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, InvVentasFormaCobroTO.class).get(0);
    }

    @Override
    public Boolean buscarInvVentasFormaCobro(String ctaContable, String empresa, Integer secuencial) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM inventario.inv_ventas_forma_cobro " + "WHERE (cta_codigo = '" + ctaContable + "') AND (cta_empresa = '" + empresa + "')"
                + (secuencial != null ? " AND fc_secuencial !=" + secuencial : "");//secuencial para modificar
        return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0)) == 0) ? false : true;
    }

}
