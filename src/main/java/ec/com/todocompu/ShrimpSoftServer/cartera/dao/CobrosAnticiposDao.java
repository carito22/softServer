package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorCobrarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticiposPK;

public interface CobrosAnticiposDao extends GenericDao<CarCobrosAnticipos, CarCobrosAnticiposPK> {

    public List<CarFunCobrosSaldoAnticipoTO> getCarFunCobrosSaldoAnticipoTO(String empresa, String cliente)
            throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTO(String empresa,
            String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistorico(String empresa,
            String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

    public CarCobrosAnticipos obtenerCarCobrosAnticipo(String empresa, String periodo, String tipo, String numero) throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCliente(String empresa, String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCP(String empresa, String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistoricoAgrupadoCliente(String empresa, String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistoricoAgrupadoCC(String empresa, String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

}
