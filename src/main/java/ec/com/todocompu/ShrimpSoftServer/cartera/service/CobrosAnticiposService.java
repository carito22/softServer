package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorCobrarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

public interface CobrosAnticiposService {

    @Transactional
    public List<CarFunCobrosSaldoAnticipoTO> getCarFunCobrosSaldoAnticipoTO(String empresa, String cliente)
            throws Exception;

    @Transactional
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTO(String empresa,
            String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception;

    @Transactional
    public MensajeTO insertarAnticiposCobro(CarCobrosAnticipoTO carCobrosAnticipoTO, String observaciones,
            String nombreCliente, String fecha, String sectorCliente, String documento, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public Map<String, Object> obtenerAnticipoCobro(Map<String, Object> map) throws Exception;

    @Transactional
    public MensajeTO mayorizarAnticiposCobro(CarCobrosAnticipoTO carCobrosAnticipoTO, String observaciones,
            String nombreCliente, String fecha, String sectorCliente, String documento, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistorico(String empresa,
            String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCP(String empresa, String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCliente(String empresa, String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistoricoAgrupadoCliente(String empresa, String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistoricoAgrupadoCC(String empresa, String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

}
