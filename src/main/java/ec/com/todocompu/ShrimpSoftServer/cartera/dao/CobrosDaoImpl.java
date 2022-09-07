package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorCobrarListadoVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaMayorAuxiliarClienteProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobros;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosPK;

@Repository
public class CobrosDaoImpl extends GenericDaoImpl<CarCobros, CarCobrosPK> implements CobrosDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    /// ******
    @Override
    public CarListaCobrosClienteTO getCobrosConsultaCliente(String empresa, String periodo, String tipo, String numero)
            throws Exception {
        String sql = "SELECT inv_cliente.cli_codigo, inv_cliente.cli_razon_social, "
                + "inv_cliente.cli_id_numero, inv_cliente.cli_direccion, "
                + "con_contable.con_observaciones, con_contable.con_fecha, (con_pendiente or con_reversado or con_anulado) as con_anulado, car_cobros.cob_saldo_anterior, car_cobros.cob_valor, car_cobros.cob_saldo_actual "
                + "FROM cartera.car_cobros INNER JOIN inventario.inv_cliente "
                + "ON car_cobros.cli_empresa = inv_cliente.cli_empresa "
                + "AND car_cobros.cli_codigo = inv_cliente.cli_codigo " + "INNER JOIN contabilidad.con_contable "
                + "ON car_cobros.cob_empresa = con_contable.con_empresa "
                + "AND car_cobros.cob_periodo = con_contable.con_periodo "
                + "AND car_cobros.cob_tipo = con_contable.con_tipo "
                + "AND car_cobros.cob_numero = con_contable.con_numero " + "WHERE car_cobros.cob_empresa = '" + empresa
                + "' " + "AND car_cobros.cob_periodo = '" + periodo + "' " + "AND car_cobros.cob_tipo = '" + tipo + "' "
                + "AND car_cobros.cob_numero = '" + numero + "';";
        return genericSQLDao.obtenerObjetoPorSql(sql, CarListaCobrosClienteTO.class);
    }

    @Override
    public List<CarListaPagosCobrosDetalleFormaTO> getCobrosConsultaDetalleForma(String empresa, String periodo,
            String numero, boolean hayPostfechados) throws Exception {
        String sql = "SELECT car_cobros_forma.fp_detalle as forma, ban_nombre , "
                + "car_cobros_detalle_forma.det_cuenta as cuenta, car_cobros_detalle_forma.det_fecha_vencimiento "
                + "as fecha, car_cobros_detalle_forma.det_referencia as referencia, car_cobros_detalle_forma.det_valor "
                + "as valor, car_cobros_detalle_forma.det_observaciones as observaciones, "
                + "car_cobros_detalle_forma.det_secuencial id, car_cobros_forma.fp_secuencial fp_secuencial "
                + "FROM cartera.car_cobros INNER JOIN "
                + "cartera.car_cobros_detalle_forma INNER JOIN cartera.car_cobros_forma ON "
                + "car_cobros_detalle_forma.fp_secuencial = car_cobros_forma.fp_secuencial ON car_cobros.cob_empresa "
                + "= car_cobros_detalle_forma.cob_empresa AND car_cobros.cob_periodo = "
                + "car_cobros_detalle_forma.cob_periodo AND car_cobros.cob_tipo = car_cobros_detalle_forma.cob_tipo "
                + "AND car_cobros.cob_numero = car_cobros_detalle_forma.cob_numero LEFT JOIN banco.ban_banco ON "
                + "car_cobros_detalle_forma.ban_empresa = ban_banco.ban_empresa AND car_cobros_detalle_forma.ban_codigo "
                + "= ban_banco.ban_codigo WHERE car_cobros.cob_empresa = '" + empresa
                + "' AND  car_cobros.cob_periodo = '" + periodo + "' " + "AND  car_cobros.cob_tipo = 'C-COB' AND"
                + " car_cobros.cob_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarListaPagosCobrosDetalleFormaTO.class);
    }

    @Override
    public List<CarListaPagosCobrosDetalleAnticipoTO> getCobrosConsultaDetalleAnticipo(String empresa, String periodo,
            String numero) throws Exception {
        String sql = "SELECT ant_periodo, ant_tipo, ant_numero, con_contable.con_fecha as ant_fecha,"
                + "car_cobros_detalle_anticipos.det_valor as valor, row_number() over (partition by '' order by ant_periodo) id  "
                + "FROM  cartera.car_cobros " + "INNER JOIN cartera.car_cobros_detalle_anticipos "
                + "INNER JOIN contabilidad.con_contable "
                + "ON car_cobros_detalle_anticipos.ant_empresa = con_contable.con_empresa AND "
                + "car_cobros_detalle_anticipos.ant_periodo = con_contable.con_periodo AND "
                + "car_cobros_detalle_anticipos.ant_tipo = con_contable.con_tipo AND "
                + "car_cobros_detalle_anticipos.ant_numero = con_contable.con_numero "
                + "ON car_cobros.cob_empresa = car_cobros_detalle_anticipos.cob_empresa AND "
                + "car_cobros.cob_periodo = car_cobros_detalle_anticipos.cob_periodo AND "
                + "car_cobros.cob_tipo = car_cobros_detalle_anticipos.cob_tipo AND "
                + "car_cobros.cob_numero = car_cobros_detalle_anticipos.cob_numero "
                + "WHERE car_cobros.cob_empresa = '" + empresa + "' " + "AND car_cobros.cob_periodo = '" + periodo
                + "' " + "AND car_cobros.cob_tipo = 'C-COB' " + "AND car_cobros.cob_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarListaPagosCobrosDetalleAnticipoTO.class);
    }

    @Override
    public List<CarFunCobrosTO> getCarFunCobrosTO(String empresa, String sector, String desde, String hasta,
            String cliente, boolean incluirTodos) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM cartera.fun_cobros('" + empresa + "', " + desde + ", " + hasta + ", " + cliente
                + ", " + incluirTodos + ");";
        return genericSQLDao.obtenerPorSql(sql, CarFunCobrosTO.class);
    }

    @Override
    public List<CarFunCobrosDetalleTO> getCarFunCobrosDetalleTO(String empresa, String sector, String desde,
            String hasta, String cliente, String formaPago) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        formaPago = formaPago == null ? formaPago : "'" + formaPago + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM cartera.fun_cobros_detalle('" + empresa + "', " + desde + ", " + hasta + ", "
                + cliente + ", " + formaPago + ");";
        return genericSQLDao.obtenerPorSql(sql, CarFunCobrosDetalleTO.class);
    }

    @Override
    public List<CarFunCobrosDetalleTO> getCarFunCobrosDetalleTOAgrupadoCliente(String empresa, String sector, String desde, String hasta, String cliente, String formaPago) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        formaPago = formaPago == null ? formaPago : "'" + formaPago + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM cartera.fun_cobros_detalle_agrupado_cliente('" + empresa + "', " + desde + ", " + hasta + ", "
                + cliente + ", " + formaPago + ");";
        return genericSQLDao.obtenerPorSql(sql, CarFunCobrosDetalleTO.class);
    }

    /// ---------------------------------------
    @Override
    public List<CarListaCobrosTO> getCobrosConsultaDetalleVentas(String empresa, String periodo, String numero)
            throws Exception {
        String sql = "SELECT car_cobros_detalle_ventas.vta_periodo as cxcc_periodo, "
                + "car_cobros_detalle_ventas.vta_motivo as cxcc_motivo, "
                + "car_cobros_detalle_ventas.vta_numero as cxcc_numero, "
                + "inv_ventas.vta_documento_numero as cxcc_documento_numero, "
                + "'' as cxcc_sector, inv_ventas.vta_fecha as cxcc_fecha_emision, "
                + "inv_ventas.vta_fecha_vencimiento as cxcc_fecha_vencimiento, "
                + "inv_ventas.vta_total as cxcc_total, det_valor as cxcc_abonos, NULL as cxcc_saldo, '' cxcc_documento_complemento,"
                + "row_number() over (partition by '' order by car_cobros_detalle_ventas.vta_periodo, car_cobros_detalle_ventas.vta_motivo, car_cobros_detalle_ventas.vta_numero) as id "
                + "FROM cartera.car_cobros " + "INNER JOIN cartera.car_cobros_detalle_ventas "
                + "INNER JOIN inventario.inv_ventas "
                + "ON car_cobros_detalle_ventas.vta_empresa = inv_ventas.vta_empresa "
                + "AND car_cobros_detalle_ventas.vta_periodo = inv_ventas.vta_periodo "
                + "AND car_cobros_detalle_ventas.vta_motivo = inv_ventas.vta_motivo "
                + "AND car_cobros_detalle_ventas.vta_numero = inv_ventas.vta_numero "
                + "ON car_cobros.cob_empresa = car_cobros_detalle_ventas.cob_empresa "
                + "AND car_cobros.cob_periodo = car_cobros_detalle_ventas.cob_periodo "
                + "AND car_cobros.cob_tipo = car_cobros_detalle_ventas.cob_tipo "
                + "AND car_cobros.cob_numero = car_cobros_detalle_ventas.cob_numero "
                + "WHERE car_cobros.cob_empresa = '" + empresa + "' " + "AND car_cobros.cob_periodo = '" + periodo
                + "' " + "AND car_cobros.cob_tipo = 'C-COB' " + "AND car_cobros.cob_numero = '" + numero + "' "
                + "ORDER BY cxcc_periodo, cxcc_motivo, cxcc_numero;";
        return genericSQLDao.obtenerPorSql(sql, CarListaCobrosTO.class);
    }

    @Override
    public List<CarListaCobrosTO> getCarListaCobrosTO(String empresa, String cliente) throws Exception {
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar('" + empresa + "', '" + cliente + "');";
        return genericSQLDao.obtenerPorSql(sql, CarListaCobrosTO.class);
    }

    @Override
    public java.math.BigDecimal getCarDeudaVencida(String empresa, String cliente) throws Exception {
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar_vencidas('" + empresa + "', '" + cliente + "');";
        java.math.BigDecimal deudaVencida = (java.math.BigDecimal) genericSQLDao.obtenerObjetoPorSql(sql);
        return deudaVencida;
    }

    @Override
    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorCobrarAnticiposTO(String empresa,
            String sector, String hasta) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT cxcva.cxpg_codigo, cxcva.cxpg_nombre,"
                + " cxcva.cxpp_saldo_ventas as cxpp_saldo_compras,"
                + " cxcva.cxpp_saldo_anticipos, "
                + " cxcva.cxpp_total, cxcva.id, cxpp_saldo_sin_vencer, cxpp_saldo_vencido_30, cxpp_saldo_vencido_60, cxpp_saldo_vencido_90,cxpp_saldo_vencido_120,cxpp_saldo_vencido_mayor_120 "
                + " FROM cartera.fun_cuentas_por_cobrar_vs_anticipos('"
                + empresa + "', " + sector + ", " + hasta + ") cxcva";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarCobrarAnticiposTO.class);
    }

    @Override
    public List<CuentasPorCobrarDetalladoTO> getCarListaCuentasPorCobrarDetalladoTO(String empresa, String sector,
            String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception {
        sector = sector == null ? sector : "'" + sector + "'";
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        grupo = grupo == null ? grupo : "'" + grupo + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar_detallado(" + "'" + empresa + "', " + sector + ", "
                + cliente + ", " + desde + ", " + hasta + ", " + grupo + "," + ichfa + ")";
        return genericSQLDao.obtenerPorSql(sql, CuentasPorCobrarDetalladoTO.class);
    }

    @Override
    public List<CuentasPorCobrarDetalladoTO> getCarListaCuentasPorCobrarDetalladoTOCorteconexion(String empresa, String sector, String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception {
        sector = sector == null ? sector : "'" + sector + "'";
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        grupo = grupo == null ? grupo : "'" + grupo + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar_detallado_corte_conexion(" + "'" + empresa + "', " + sector + ", "
                + cliente + ", " + desde + ", " + hasta + ", " + grupo + "," + ichfa + ")";
        return genericSQLDao.obtenerPorSql(sql, CuentasPorCobrarDetalladoTO.class);
    }

    @Override
    public List<CarFunCuentasPorCobrarListadoVentasTO> getCarFunCuentasPorCobrarListadoVentasTO(String empresa,
            String sector, String cliente, String desde, String hasta) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        desde = desde == null ? null : "'" + desde + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar_listado_ventas('" + empresa + "', " + "" + sector
                + ", " + cliente + ", " + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, CarFunCuentasPorCobrarListadoVentasTO.class);
    }

    @Override
    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorCobrarGeneralTO(String empresa, String sector,
            String hasta) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT ccg.cxcg_codigo as cxpg_codigo, ccg.cxcg_nombre as cxpg_nombre, ccg.cxcp_saldo as cxpp_saldo, id FROM cartera.fun_cuentas_por_cobrar_general('"
                + empresa + "', " + "" + sector + ", " + hasta + ") ccg";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarCobrarGeneralTO.class);
    }

    @Override
    public List<CarListaCobrosTO> getCarListaCobrosVentasTO(String empresa, String cliente) throws Exception {
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar('" + empresa + "', '" + cliente + "');";
        return genericSQLDao.obtenerPorSql(sql, CarListaCobrosTO.class);
    }

    @Override
    public Boolean buscarCarCobrosForma(String ctaContable, String empresa) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM cartera.car_cobros_forma " + "WHERE (cta_codigo = '" + ctaContable
                + "') AND (cta_empresa = '" + empresa + "');";
        BigInteger objeto = (BigInteger) genericSQLDao.obtenerObjetoPorSql(sql);
        return objeto.equals(new BigInteger("0")) ? false : true;
    }

    @Override
    public Boolean buscarCarCobrosForma(String ctaContable, String empresa, String sector) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM cartera.car_cobros_forma " + "WHERE (cta_codigo = '" + ctaContable
                + "') AND (cta_empresa = '" + empresa + "') AND (sec_codigo = '" + sector + "');";
        BigInteger objeto = (BigInteger) genericSQLDao.obtenerObjetoPorSql(sql);
        return objeto.equals(new BigInteger("0")) ? false : true;
    }

    @Override
    public List<CarListaMayorAuxiliarClienteProveedorTO> getCarListaMayorAuxiliarClienteProveedorTO(String empresa,
            String sector, String codigo, String desde, String hasta, char accion, boolean anticipos) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        codigo = codigo == null || codigo.isEmpty() ? null : "'" + codigo + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "";
        if (accion == 'P') {
            sql = "SELECT * FROM cartera." + "fun_mayor_auxiliar_proveedor('" + empresa + "', " + codigo + ", "
                    + desde + ", " + hasta + ", " + anticipos + ");";
        } else {
            sql = "SELECT mac.mac_contable as map_contable, mac.mac_fecha as map_fecha, mac.mac_clave_principal as map_clave_principal, mac.mac_cp as map_cp, "
                    + "mac.mac_documento as map_documento, mac.mac_individuo as map_individuo, mac.mac_debe as map_debe, mac.mac_haber as map_haber, mac.mac_saldo as map_saldo, mac.mac_observaciones as map_observaciones,mac.mac_grupo_empresarial as map_grupo_empresarial, mac.mac_orden as map_orden, mac.id FROM cartera."
                    + "fun_mayor_auxiliar_cliente('" + empresa + "', "
                    // + sector + ", '" //Cuando ya este lista
                    // la función, descomentar esta linea
                    + codigo + ", " + desde + ", " + hasta + ", " + anticipos + ") mac;";
        }
        return genericSQLDao.obtenerPorSql(sql, CarListaMayorAuxiliarClienteProveedorTO.class);
    }

}
