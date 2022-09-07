package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarDetalladoGranjasMarinasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPagadasListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorPagarListadoComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosPruebaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorPagarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;

@Repository
public class PagosDaoImpl extends GenericDaoImpl<CarPagos, CarPagosPK> implements PagosDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private ComprasDao comprasDao;

    @Override
    public CarListaPagosProveedorTO getPagosConsultaProveedor(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        String sql = "SELECT inv_proveedor.prov_codigo, inv_proveedor.prov_razon_social, inv_proveedor.prov_id_numero, "
                + "inv_proveedor.prov_direccion, "
                + "con_contable.con_observaciones, con_contable.con_fecha, car_pagos.pag_saldo_anterior, car_pagos.pag_valor, car_pagos.pag_saldo_actual, inv_proveedor.prov_descripcion "
                + "FROM cartera.car_pagos INNER JOIN inventario.inv_proveedor "
                + "ON car_pagos.prov_empresa = inv_proveedor.prov_empresa AND "
                + "car_pagos.prov_codigo = inv_proveedor.prov_codigo " + "INNER JOIN contabilidad.con_contable "
                + "ON car_pagos.pag_empresa = con_contable.con_empresa AND "
                + "car_pagos.pag_periodo = con_contable.con_periodo AND "
                + "car_pagos.pag_tipo = con_contable.con_tipo AND " + "car_pagos.pag_numero = con_contable.con_numero "
                + "WHERE car_pagos.pag_empresa = '" + empresa + "' AND " + "car_pagos.pag_periodo = '" + periodo
                + "' AND " + "car_pagos.pag_tipo = '" + tipo + "' AND " + "car_pagos.pag_numero = '" + numero + "';";
        return genericSQLDao.obtenerObjetoPorSql(sql, CarListaPagosProveedorTO.class);
    }

    @Override
    public Boolean getCarReversarPago(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT pag_reversado " + "FROM cartera.car_pagos " + "WHERE car_pagos.pag_empresa = '" + empresa
                + "' " + "AND car_pagos.pag_periodo = '" + periodo + "' " + "AND car_pagos.pag_tipo = 'C-PAG' "
                + "AND car_pagos.pag_numero = '" + numero + "';";
        return (Boolean) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public List<CarListaPagosTO> getPagosConsultaDetalleCompras(String empresa, String periodo, String numero)
            throws Exception {
        String sql = "SELECT car_pagos_detalle_compras.comp_periodo as cxpp_periodo, "
                + "car_pagos_detalle_compras.comp_motivo as cxpp_motivo, "
                + "car_pagos_detalle_compras.comp_numero as cxpp_numero, "
                + "inv_compras.comp_documento_numero as cxpp_documento_numero, "
                + "'' as cxpp_sector, inv_compras.comp_fecha as cxpp_fecha_emision, "
                + "inv_compras.comp_fecha_vencimiento as cxpp_fecha_vencimiento, "
                + "inv_compras.comp_total - inv_compras.comp_valor_retenido as cxpp_total, "
                + "det_valor as cxpp_abonos, NULL as cxpp_saldo, inv_compras.comp_usuario_aprueba_pago cxpp_usuario_aprueba_pago,'' as cxpp_documento_tipo_complemento,'' as cxpp_documento_numero_complemento, "
                + "row_number() over (partition by '' order by car_pagos_detalle_compras.comp_periodo,  car_pagos_detalle_compras.comp_motivo, car_pagos_detalle_compras.comp_numero ) as id "
                + "FROM cartera.car_pagos INNER JOIN cartera.car_pagos_detalle_compras INNER JOIN inventario.inv_compras "
                + "ON car_pagos_detalle_compras.comp_empresa = inv_compras.comp_empresa AND "
                + "car_pagos_detalle_compras.comp_periodo = inv_compras.comp_periodo AND "
                + "car_pagos_detalle_compras.comp_motivo = inv_compras.comp_motivo AND "
                + "car_pagos_detalle_compras.comp_numero = inv_compras.comp_numero "
                + "ON car_pagos.pag_empresa = car_pagos_detalle_compras.pag_empresa AND "
                + "car_pagos.pag_periodo = car_pagos_detalle_compras.pag_periodo AND "
                + "car_pagos.pag_tipo = car_pagos_detalle_compras.pag_tipo AND "
                + "car_pagos.pag_numero = car_pagos_detalle_compras.pag_numero " + "WHERE car_pagos.pag_empresa = '"
                + empresa + "' AND " + "car_pagos.pag_periodo = '" + periodo + "' AND "
                + "car_pagos.pag_tipo = 'C-PAG' AND " + "car_pagos.pag_numero = '" + numero + "' "
                + "ORDER BY cxpp_periodo, cxpp_motivo, cxpp_numero;";
        return genericSQLDao.obtenerPorSql(sql, CarListaPagosTO.class);
    }

    @Override
    public List<CarListaPagosCobrosDetalleFormaTO> getPagosConsultaDetalleForma(String empresa, String periodo,
            String numero) throws Exception {
        String sql = "SELECT car_pagos_forma.fp_detalle as forma, '' as ban_nombre, '' as cuenta, '' as fecha,"
                + "car_pagos_detalle_forma.det_referencia as referencia, "
                + "car_pagos_detalle_forma.det_valor as valor, "
                + "car_pagos_detalle_forma.det_observaciones as observaciones, "
                + "car_pagos_detalle_forma.det_secuencial id, car_pagos_forma.fp_secuencial fp_secuencial "
                + "FROM cartera.car_pagos INNER JOIN cartera.car_pagos_detalle_forma INNER JOIN cartera.car_pagos_forma "
                + "ON car_pagos_detalle_forma.fp_secuencial = car_pagos_forma.fp_secuencial "
                + "ON car_pagos.pag_empresa = car_pagos_detalle_forma.pag_empresa AND "
                + "car_pagos.pag_periodo = car_pagos_detalle_forma.pag_periodo AND "
                + "car_pagos.pag_tipo = car_pagos_detalle_forma.pag_tipo AND "
                + "car_pagos.pag_numero = car_pagos_detalle_forma.pag_numero " + "WHERE car_pagos.pag_empresa = '"
                + empresa + "' AND " + "car_pagos.pag_periodo = '" + periodo + "' AND "
                + "car_pagos.pag_tipo = 'C-PAG' AND " + "car_pagos.pag_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarListaPagosCobrosDetalleFormaTO.class);
    }

    @Override
    public List<CarListaPagosCobrosDetalleAnticipoTO> getPagosConsultaDetalleAnticipo(String empresa, String periodo,
            String numero) throws Exception {
        String sql = "SELECT  ant_periodo, ant_tipo, ant_numero, con_contable.con_fecha as ant_fecha, "
                + "car_pagos_detalle_anticipos.det_valor as valor, row_number() over (partition by '' order by ant_periodo) id "
                + "FROM  cartera.car_pagos INNER JOIN cartera.car_pagos_detalle_anticipos INNER JOIN contabilidad.con_contable "
                + "ON car_pagos_detalle_anticipos.ant_empresa = con_contable.con_empresa AND "
                + "car_pagos_detalle_anticipos.ant_periodo = con_contable.con_periodo AND "
                + "car_pagos_detalle_anticipos.ant_tipo = con_contable.con_tipo AND "
                + "car_pagos_detalle_anticipos.ant_numero = con_contable.con_numero "
                + "ON car_pagos.pag_empresa = car_pagos_detalle_anticipos.pag_empresa AND "
                + "car_pagos.pag_periodo = car_pagos_detalle_anticipos.pag_periodo AND "
                + "car_pagos.pag_tipo = car_pagos_detalle_anticipos.pag_tipo AND "
                + "car_pagos.pag_numero = car_pagos_detalle_anticipos.pag_numero " + "WHERE car_pagos.pag_empresa = '"
                + empresa + "' " + "AND car_pagos.pag_periodo = '" + periodo + "' "
                + "AND car_pagos.pag_tipo = 'C-PAG' " + "AND car_pagos.pag_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarListaPagosCobrosDetalleAnticipoTO.class);
    }

    @Override
    public List<CarFunPagosTO> getCarFunPagosTO(String empresa, String sector, String desde, String hasta,
            String proveedor, boolean incluirTodos) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        sector = sector == null ? sector : "'" + sector + "'";

        String sql = "SELECT * FROM cartera.fun_Pagos('" + empresa + "', "
                // + sector + "', " //Cuando ya este lista la función,
                // descomentar esta linea
                + desde + ", " + hasta + ", " + proveedor + ", " + incluirTodos + ")";
        return genericSQLDao.obtenerPorSql(sql, CarFunPagosTO.class);
    }

    @Override
    public List<CarFunPagosPruebaTO> getCarFunPagosPruebaTO(String empresa, String desde, String hasta,
            String proveedor) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";

        String sql = "SELECT * FROM cartera.fun_Pagos('" + empresa + "', " + desde + ", " + hasta + ", " + proveedor
                + ", true);";
        return genericSQLDao.obtenerPorSql(sql, CarFunPagosPruebaTO.class);
    }

    @Override
    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTO(String empresa, String sector, String desde, String hasta,
            String proveedor, String formaPago) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        formaPago = formaPago == null ? formaPago : "'" + formaPago + "'";
        String sql = "SELECT * FROM cartera.fun_pagos_detalle('" + empresa + "', "
                // + sector + ", " //Cuando ya este lista la función,
                // descomentar esta linea
                + desde + ", " + hasta + ", " + proveedor + ", " + formaPago + ");";
        return genericSQLDao.obtenerPorSql(sql, CarFunPagosDetalleTO.class);
    }

    @Override
    public List<CarListaPagosTO> getCarListaPagosTO(String empresa, String proveedor) throws Exception {
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar('" + empresa + "', '" + proveedor + "');";
        return genericSQLDao.obtenerPorSql(sql, CarListaPagosTO.class);
    }

    @Override
    public List<CuentasPorPagarDetalladoTO> getCarListaCuentasPorPagarDetalladoTO(String empresa, String sector,
            String proveedor, String hasta, boolean excluirAprobadas, boolean incluirCheques, String formatoMensual) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        formatoMensual = formatoMensual == null ? formatoMensual : "'" + formatoMensual + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar_detallado('" + empresa + "', " + sector + ", "
                + proveedor + ", " + hasta + ", " + excluirAprobadas + ", " + incluirCheques + "," + formatoMensual + ")";
        return genericSQLDao.obtenerPorSql(sql, CuentasPorPagarDetalladoTO.class);
    }

    @Override
    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorPagarGeneralTO(String empresa, String sector,
            String hasta) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar_general('" + empresa + "', " + sector + ", " + hasta
                + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarCobrarGeneralTO.class);
    }

    @Override
    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorPagarAnticiposTO(String empresa,
            String sector, String hasta) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector == null ? null : "'" + sector + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar_vs_anticipos('" + empresa + "', " + sector + ", "
                + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarCobrarAnticiposTO.class);
    }

    @Override
    public List<CarFunCuentasPorPagarListadoComprasTO> getCarFunCuentasPorPagarListadoComprasTO(String empresa,
            String sector, String proveedor, String desde, String hasta) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        desde = desde == null ? null : "'" + desde + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar_listado_compras('" + empresa + "', " + "" + sector
                + ", " + proveedor + ", " + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, CarFunCuentasPorPagarListadoComprasTO.class);
    }

    @Override
    public List<CarFunCuentasPagadasListadoTO> listarCuentasPagadas(String empresa, String sector, String proveedor, String desde, String hasta, boolean aprobadas) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        desde = desde == null ? null : "'" + desde + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_pagadas_listado('" + empresa + "', " + "" + sector + ", " + proveedor + ", " + desde + ", " + hasta + ", " + aprobadas + ")";
        return genericSQLDao.obtenerPorSql(sql, CarFunCuentasPagadasListadoTO.class);
    }

    @Override
    public String aprobarPago(CuentasPorPagarDetalladoTO cuentaPorPagar, SisInfoTO sisInfoTO) throws Exception {
        InvCompras compra = comprasDao.obtenerPorId(InvCompras.class, new InvComprasPK(sisInfoTO.getEmpresa(), cuentaPorPagar.getCxpdPeriodo(), cuentaPorPagar.getCxpdMotivo(), cuentaPorPagar.getCxpdNumero()));
        String susClave = sisInfoTO.getEmpresa() + " | " + cuentaPorPagar.getCxpdPeriodo() + " | " + cuentaPorPagar.getCxpdMotivo() + " | " + cuentaPorPagar.getCxpdNumero();
        if (compra != null) {
            compra.setCompUsuarioApruebaPago(sisInfoTO.getUsuario());
            compra.setCompFechaApruebaPago(new Date());
            comprasDao.actualizar(compra);
            String susSuceso = "UPDATE";
            String susTabla = "inventario.inv_compras";
            String susDetalle = "Se aprobó el pago para la compra: " + susClave;
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sucesoDao.insertar(sisSuceso);
            return "T" + susDetalle;
        } else {
            return "FNo existe compra: " + susClave;
        }
    }

    @Override
    public List<CarCuentasPorPagarDetalladoGranjasMarinasTO> getCarCuentasPorPagarDetalladoGranjasMarinasTO(String fecha) throws Exception {
        fecha = fecha == null ? null : "'" + fecha + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar_detallado_granjas_marinas(" + fecha + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarDetalladoGranjasMarinasTO.class);
    }

    @Override
    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTOAgrupadoProveedor(String empresa, String sector, String desde, String hasta, String proveedor, String formaPago) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        formaPago = formaPago == null ? formaPago : "'" + formaPago + "'";
        String sql = "SELECT * FROM cartera.fun_pagos_detalle_agrupado_proveedor('" + empresa + "', "
                // + sector + ", " //Cuando ya este lista la función,
                // descomentar esta linea
                + desde + ", " + hasta + ", " + proveedor + ", " + formaPago + ");";
        return genericSQLDao.obtenerPorSql(sql, CarFunPagosDetalleTO.class);
    }

    @Override
    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTOAgrupadoCP(String empresa, String sector, String desde, String hasta, String proveedor, String formaPago) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        formaPago = formaPago == null ? formaPago : "'" + formaPago + "'";
        String sql = "SELECT * FROM cartera.fun_pagos_detalle_agrupado_centro_produccion('" + empresa + "', "
                // + sector + ", " //Cuando ya este lista la función,
                // descomentar esta linea
                + desde + ", " + hasta + ", " + proveedor + ", " + formaPago + ");";
        return genericSQLDao.obtenerPorSql(sql, CarFunPagosDetalleTO.class);
    }
}
