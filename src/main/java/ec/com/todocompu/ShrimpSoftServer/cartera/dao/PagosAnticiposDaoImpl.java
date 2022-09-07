package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosAnticiposPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.RhPreavisoAnticiposPrestamosSueldoMachalaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhOrdenBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoGuayaquilTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoPichinchaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Repository
public class PagosAnticiposDaoImpl extends GenericDaoImpl<CarPagosAnticipos, CarPagosAnticiposPK>
        implements PagosAnticiposDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public CarListaPagosProveedorTO getPagosConsultaProveedorAnticipo(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        String sql = "SELECT inv_proveedor.prov_codigo, " + "inv_proveedor.prov_razon_social, inv_proveedor.prov_id_numero, "
                + "inv_proveedor.prov_direccion, "
                + "con_contable.con_observaciones, con_contable.con_fecha, 0.00 as pag_saldo_anterior, 0.00 as pag_valor,0.00 as pag_saldo_actual , inv_proveedor.prov_descripcion  "
                + "FROM cartera.car_pagos_anticipos INNER JOIN inventario.inv_proveedor "
                + "ON car_pagos_anticipos.prov_empresa = inv_proveedor.prov_empresa AND "
                + "car_pagos_anticipos.prov_codigo = inv_proveedor.prov_codigo "
                + "INNER JOIN contabilidad.con_contable "
                + "ON car_pagos_anticipos.ant_empresa = con_contable.con_empresa AND "
                + "car_pagos_anticipos.ant_periodo = con_contable.con_periodo AND "
                + "car_pagos_anticipos.ant_tipo = con_contable.con_tipo AND "
                + "car_pagos_anticipos.ant_numero = con_contable.con_numero "
                + "WHERE car_pagos_anticipos.ant_empresa = '" + empresa + "' AND "
                + "car_pagos_anticipos.ant_periodo = '" + periodo + "' AND " + "car_pagos_anticipos.ant_tipo = '" + tipo
                + "' AND " + "car_pagos_anticipos.ant_numero = '" + numero + "';";
        return genericSQLDao.obtenerObjetoPorSql(sql, CarListaPagosProveedorTO.class);
    }

    @Override
    public Boolean getCarReversarPagoAnticipos(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT ant_reversado " + "FROM cartera.car_pagos_anticipos "
                + "WHERE car_pagos_anticipos.ant_empresa = '" + empresa + "' "
                + "AND car_pagos_anticipos.ant_periodo = '" + periodo + "' "
                + "AND (car_pagos_anticipos.ant_tipo = 'C-APRO' OR car_pagos_anticipos.ant_tipo = 'C-PAG') "
                + "AND car_pagos_anticipos.ant_numero = '" + numero + "';";
        return (Boolean) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public CarPagosCobrosAnticipoTO getCarPagosCobrosAnticipoTO(String empresa, String periodo, String tipo,
            String numero, char accion) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        periodo = periodo == null ? periodo : "'" + periodo + "'";
        tipo = tipo == null ? tipo : "'" + tipo + "'";
        numero = numero == null ? numero : "'" + numero + "'";
        String sql = "";
        if (accion == 'P') {
            sql = "SELECT ant_valor, fp_secuencial, sec_codigo " + "FROM cartera.car_pagos_anticipos "
                    + "WHERE ant_empresa = " + empresa + " AND " + "ant_periodo = " + periodo + " AND " + "ant_tipo = "
                    + tipo + " AND " + "ant_numero = " + numero + ";";
        } else {
            sql = "SELECT ant_valor, fp_secuencial, sec_codigo " + "FROM cartera.car_cobros_anticipos "
                    + "WHERE ant_empresa = " + empresa + " AND " + "ant_periodo = " + periodo + " AND " + "ant_tipo = "
                    + tipo + " AND " + "ant_numero = " + numero + ";";
        }
        return genericSQLDao.obtenerObjetoPorSql(sql, CarPagosCobrosAnticipoTO.class);
    }

    @Override
    public List<CarFunPagosSaldoAnticipoTO> getCarFunPagosSaldoAnticipoTO(String empresa, String proveedor)
            throws Exception {
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar_saldo_anticipos('" + empresa + "', " + "null, "
                + proveedor + ", null, false);";
        return genericSQLDao.obtenerPorSql(sql, CarFunPagosSaldoAnticipoTO.class);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTO(String empresa,
            String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception {

        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        proveedorCodigo = proveedorCodigo == null ? proveedorCodigo : "'" + proveedorCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar_saldo_anticipos('" + empresa + "', " + sector + ", "
                + proveedorCodigo + ", " + hasta + ", " + incluirTodos + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarSaldoAnticiposTO.class);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoProveedor(String empresa, String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        proveedorCodigo = proveedorCodigo == null ? proveedorCodigo : "'" + proveedorCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar_saldo_anticipos_agrupado_proveedor('" + empresa + "', " + sector + ", "
                + proveedorCodigo + ", " + hasta + ", " + incluirTodos + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarSaldoAnticiposTO.class);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoCC(String empresa, String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        proveedorCodigo = proveedorCodigo == null ? proveedorCodigo : "'" + proveedorCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_pagar_saldo_anticipos_agrupado_cc('" + empresa + "', " + sector + ", "
                + proveedorCodigo + ", " + hasta + ", " + incluirTodos + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarSaldoAnticiposTO.class);

    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> listaOrdenesAnticipoBancoBolivariano(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception {
        sector = sector != null ? sector : "";
        String sql = "SELECT * FROM cartera.fun_preavisos_bolivariano('" + ordenBancaria.getEmpresa() + "', '" + UtilsDate.fechaFormatoString(ordenBancaria.getFecha(), "yyyy-MM-dd") + "','"
                + ordenBancaria.getCuentabancaria() + "', '" + ordenBancaria.getTipoServicio() + "','" + ordenBancaria.getOrden() + "','" + sector + "')";

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoTO.class);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaOrdenesPichinchaInternacional(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception {
        sector = sector != null ? sector : "";
        String sql = "SELECT * FROM cartera.fun_preavisos_pichincha_internacional('" + ordenBancaria.getEmpresa() + "', '" + UtilsDate.fechaFormatoString(ordenBancaria.getFecha(), "yyyy-MM-dd")
                + "','" + ordenBancaria.getCuentabancaria() + "', '" + ordenBancaria.getOrden() + "', '" + ordenBancaria.getBanco() + "','" + sector + "')";

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoPichinchaTO.class);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> listaOrdenesBancoMachala(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception {
        sector = sector != null ? sector : "";
        String sql = "SELECT * FROM cartera.fun_preavisos_machala('" + ordenBancaria.getEmpresa() + "', '" + UtilsDate.fechaFormatoString(ordenBancaria.getFecha(), "yyyy-MM-dd")
                + "','" + ordenBancaria.getCuentabancaria() + "', '" + ordenBancaria.getOrden() + "', '" + ordenBancaria.getBanco() + "','" + sector + "')";

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoMachalaTO.class);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> listaOrdenesBancoGuayaquil(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception {
        sector = sector != null ? sector : "";
        String sql = "SELECT * FROM cartera.fun_preavisos_guayaquil('" + ordenBancaria.getEmpresa() + "', '" + UtilsDate.fechaFormatoString(ordenBancaria.getFecha(), "yyyy-MM-dd")
                + "','" + ordenBancaria.getCuentabancaria() + "', '" + ordenBancaria.getOrden() + "', '" + ordenBancaria.getBanco() + "','" + sector + "')";
        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoGuayaquilTO.class);
    }

    @Override
    public CarPagosAnticipos obtenerCarPagosAnticipo(String empresa, String periodo, String tipo, String numero) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        periodo = periodo == null ? periodo : "'" + periodo + "'";
        tipo = tipo == null ? tipo : "'" + tipo + "'";
        numero = numero == null ? numero : "'" + numero + "'";
        String sql = "";
        sql = "SELECT * FROM cartera.car_pagos_anticipos "
                + "WHERE ant_empresa = " + empresa + " AND " + "ant_periodo = " + periodo + " AND " + "ant_tipo = " + tipo + " AND " + "ant_numero = " + numero + ";";
        return genericSQLDao.obtenerObjetoPorSql(sql, CarPagosAnticipos.class);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistorico(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {

        hasta = hasta == null ? null : "'" + hasta + "'";
        desde = desde == null ? null : "'" + desde + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        proveedorCodigo = proveedorCodigo == null ? proveedorCodigo : "'" + proveedorCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_anticipos_proveedor_historico('" + empresa + "', " + sector + ", "
                + proveedorCodigo + ", " + desde + ", " + hasta + ", " + incluirTodos + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarSaldoAnticiposTO.class);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistoricoAgrupadoProveedor(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        desde = desde == null ? null : "'" + desde + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        proveedorCodigo = proveedorCodigo == null ? proveedorCodigo : "'" + proveedorCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_anticipos_proveedor_historico_proveedor('" + empresa + "', " + sector + ", "
                + proveedorCodigo + ", " + desde + ", " + hasta + ", " + incluirTodos + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarSaldoAnticiposTO.class);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistoricoAgrupadoCC(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        hasta = hasta == null ? null : "'" + hasta + "'";
        desde = desde == null ? null : "'" + desde + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        proveedorCodigo = proveedorCodigo == null ? proveedorCodigo : "'" + proveedorCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_anticipos_proveedor_historico_cc('" + empresa + "', " + sector + ", " + proveedorCodigo + ", " + desde + ", " + hasta + ", " + incluirTodos + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorPagarSaldoAnticiposTO.class);
    }

}
