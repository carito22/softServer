package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaChequesPostfechadosProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorCobrarClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorPagarProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaValorizacionActivoBiologicoTO;

@Repository
public class FlujoCajaDaoImpl implements FlujoCajaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<ConFlujoCajaTO> listarFlujoDeCaja(String empresa, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_flujo_caja('" + empresa + "', null, '" + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, ConFlujoCajaTO.class);
    }

    @Override
    public List<ConFlujoCajaCuentasPorCobrarClientesTO> listarCuentasPorCobrarClientes(String empresa, String fechaHasta) throws Exception {
        String sql = "SELECT cxcd_cliente_id, cxcd_cliente_razon_social, sum(cxcd_saldo) cxcd_saldo "
                + "FROM cartera.fun_cuentas_por_cobrar_detallado('" + empresa + "', null, null, '2000-01-01', '" + fechaHasta + "', null, true) "
                + "WHERE cxcd_numero IS NOT NULL "
                + "GROUP BY cxcd_cliente_id, cxcd_cliente_razon_social "
                + "ORDER BY cxcd_cliente_razon_social;";
        return genericSQLDao.obtenerPorSql(sql, ConFlujoCajaCuentasPorCobrarClientesTO.class);
    }

    @Override
    public List<ConFlujoCajaCuentasPorPagarProveedoresTO> listarCuentasPorPagarProveedores(String empresa, String fechaHasta) throws Exception {
        String sql = "SELECT cxpd_proveedor_id, cxpd_proveedor_razon_social, sum(cxpd_saldo) cxpd_saldo "
                + "from cartera.fun_cuentas_por_pagar_detallado('" + empresa + "', null, null, '" + fechaHasta + "', false, false, false) "
                + "where cxpd_numero IS NOT NULL AND cxpd_usuario_aprueba_pago IS NOT NULL "
                + "GROUP BY cxpd_proveedor_id, cxpd_proveedor_razon_social "
                + "ORDER BY cxpd_proveedor_razon_social;";
        return genericSQLDao.obtenerPorSql(sql, ConFlujoCajaCuentasPorPagarProveedoresTO.class);
    }

    @Override
    public List<ConFlujoCajaChequesPostfechadosProveedoresTO> listarChequesPosfechados(String empresa, String fechaHasta) throws Exception {
        String sql = "SELECT ROW_NUMBER() OVER() AS id, COALESCE(NULLIF(chq_numero,'')) chq_numero, chq_beneficiario, "
                + "COALESCE(chq_fecha_vencimiento, chq_fecha_emision) chq_fecha, chq_valor "
                + "FROM banco.fun_cheques('" + empresa + "', null, '2000-01-01', '" + fechaHasta + "', 'VENCIMIENTO') "
                + "WHERE chq_cuenta_codigo IS NOT NULL AND conc_codigo IS NULL";
        return genericSQLDao.obtenerPorSql(sql, ConFlujoCajaChequesPostfechadosProveedoresTO.class);
    }

    @Override
    public List<ConFlujoCajaValorizacionActivoBiologicoTO> getListaBuscarConCuentasFlujoDetalleTO(String empresa, String fecha) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_flujo_caja_valorizacion_activo_biologico('" + empresa + "', null, '" + fecha + "');";
        return genericSQLDao.obtenerPorSql(sql, ConFlujoCajaValorizacionActivoBiologicoTO.class);
    }

}
