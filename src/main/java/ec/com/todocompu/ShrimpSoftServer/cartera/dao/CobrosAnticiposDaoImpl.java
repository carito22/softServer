package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorCobrarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticiposPK;

@Repository
public class CobrosAnticiposDaoImpl extends GenericDaoImpl<CarCobrosAnticipos, CarCobrosAnticiposPK>
        implements CobrosAnticiposDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<CarFunCobrosSaldoAnticipoTO> getCarFunCobrosSaldoAnticipoTO(String empresa, String cliente)
            throws Exception {
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar_saldo_anticipos('" + empresa + "', " + null + ", "
                + cliente + ", " + null + ", false);";
        return genericSQLDao.obtenerPorSql(sql, CarFunCobrosSaldoAnticipoTO.class);
    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTO(String empresa,
            String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception {

        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector.isEmpty() || sector == null ? null : "'" + sector + "'";
        clienteCodigo = clienteCodigo == null ? clienteCodigo : "'" + clienteCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar_saldo_anticipos('" + empresa + "', " + sector + ", "
                + clienteCodigo + ", " + hasta + ", " + incluirTodos + ");";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorCobrarSaldoAnticiposTO.class);

    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCliente(String empresa, String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception {

        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector.isEmpty() || sector == null ? null : "'" + sector + "'";
        clienteCodigo = clienteCodigo == null ? clienteCodigo : "'" + clienteCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar_saldo_anticipos_agrupado_cliente('" + empresa + "', " + sector + ", "
                + clienteCodigo + ", " + hasta + ", " + incluirTodos + ");";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorCobrarSaldoAnticiposTO.class);

    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCP(String empresa, String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception {

        hasta = hasta == null ? null : "'" + hasta + "'";
        sector = sector.isEmpty() || sector == null ? null : "'" + sector + "'";
        clienteCodigo = clienteCodigo == null ? clienteCodigo : "'" + clienteCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_cuentas_por_cobrar_saldo_anticipos_agrupado_cp('" + empresa + "', " + sector + ", "
                + clienteCodigo + ", " + hasta + ", " + incluirTodos + ");";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorCobrarSaldoAnticiposTO.class);

    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistorico(String empresa,
            String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        sector = sector.isEmpty() || sector == null ? null : "'" + sector + "'";
        clienteCodigo = clienteCodigo == null ? clienteCodigo : "'" + clienteCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_anticipos_clientes_historico('" + empresa + "', " + sector + ", "
                + clienteCodigo + ", '" + desde + "', '" + hasta + "', " + incluirTodos + ");";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorCobrarSaldoAnticiposTO.class);

    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistoricoAgrupadoCliente(String empresa, String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        sector = sector.isEmpty() || sector == null ? null : "'" + sector + "'";
        clienteCodigo = clienteCodigo == null ? clienteCodigo : "'" + clienteCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_anticipos_clientes_historico_cliente('" + empresa + "', " + sector + ", "
                + clienteCodigo + ", '" + desde + "', '" + hasta + "', " + incluirTodos + ");";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorCobrarSaldoAnticiposTO.class);
    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistoricoAgrupadoCC(String empresa, String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        sector = sector.isEmpty() || sector == null ? null : "'" + sector + "'";
        clienteCodigo = clienteCodigo == null ? clienteCodigo : "'" + clienteCodigo + "'";
        String sql = "SELECT * FROM cartera.fun_anticipos_clientes_historico_cc('" + empresa + "', " + sector + ", "
                + clienteCodigo + ", '" + desde + "', '" + hasta + "', " + incluirTodos + ");";
        return genericSQLDao.obtenerPorSql(sql, CarCuentasPorCobrarSaldoAnticiposTO.class);
    }

    @Override
    public CarCobrosAnticipos obtenerCarCobrosAnticipo(String empresa, String periodo, String tipo, String numero) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        periodo = periodo == null ? periodo : "'" + periodo + "'";
        tipo = tipo == null ? tipo : "'" + tipo + "'";
        numero = numero == null ? numero : "'" + numero + "'";
        String sql = "";
        sql = "SELECT * FROM cartera.car_cobros_anticipos "
                + "WHERE ant_empresa = " + empresa + " AND " + "ant_periodo = " + periodo + " AND " + "ant_tipo = " + tipo + " AND " + "ant_numero = " + numero + ";";
        return genericSQLDao.obtenerObjetoPorSql(sql, CarCobrosAnticipos.class);
    }

}
