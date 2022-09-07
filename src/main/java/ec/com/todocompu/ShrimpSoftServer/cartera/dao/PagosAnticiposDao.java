package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import java.util.List;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
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

public interface PagosAnticiposDao extends GenericDao<CarPagosAnticipos, CarPagosAnticiposPK> {

    public CarListaPagosProveedorTO getPagosConsultaProveedorAnticipo(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    public Boolean getCarReversarPagoAnticipos(String empresa, String periodo, String numero) throws Exception;

    public CarPagosCobrosAnticipoTO getCarPagosCobrosAnticipoTO(String empresa, String periodo, String tipo,
            String numero, char accion) throws Exception;

    public List<CarFunPagosSaldoAnticipoTO> getCarFunPagosSaldoAnticipoTO(String empresa, String proveedor)
            throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTO(String empresa,
            String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoProveedor(String empresa, String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoCC(String empresa, String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoTO> listaOrdenesAnticipoBancoBolivariano(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaOrdenesPichinchaInternacional(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> listaOrdenesBancoMachala(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> listaOrdenesBancoGuayaquil(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception;

    public CarPagosAnticipos obtenerCarPagosAnticipo(String empresa, String periodo, String tipo, String numero) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistorico(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistoricoAgrupadoProveedor(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistoricoAgrupadoCC(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

}
