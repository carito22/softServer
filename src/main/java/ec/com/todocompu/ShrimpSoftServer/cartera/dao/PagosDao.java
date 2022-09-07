package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
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
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

public interface PagosDao extends GenericDao<CarPagos, CarPagosPK> {

    public CarListaPagosProveedorTO getPagosConsultaProveedor(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    public Boolean getCarReversarPago(String empresa, String periodo, String numero) throws Exception;

    public List<CarListaPagosTO> getPagosConsultaDetalleCompras(String empresa, String periodo, String numero)
            throws Exception;

    public List<CarListaPagosCobrosDetalleFormaTO> getPagosConsultaDetalleForma(String empresa, String periodo,
            String numero) throws Exception;

    public List<CarListaPagosCobrosDetalleAnticipoTO> getPagosConsultaDetalleAnticipo(String empresa, String periodo,
            String numero) throws Exception;

    public List<CarFunPagosTO> getCarFunPagosTO(String empresa, String sector, String desde, String hasta,
            String proveedor, boolean incluirTodos) throws Exception;

    public List<CarFunPagosPruebaTO> getCarFunPagosPruebaTO(String empresa, String desde, String hasta,
            String proveedor) throws Exception;

    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTO(String empresa, String sector, String desde, String hasta,
            String proveedor, String formaPago) throws Exception;

    public List<CarListaPagosTO> getCarListaPagosTO(String empresa, String proveedor) throws Exception;

    public List<CuentasPorPagarDetalladoTO> getCarListaCuentasPorPagarDetalladoTO(String empresa, String sector,
            String proveedor, String hasta, boolean excluirAprobadas, boolean incluirCheques, String formatoMensual) throws Exception;

    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorPagarGeneralTO(String empresa, String sector,
            String hasta) throws Exception;

    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorPagarAnticiposTO(String empresa,
            String sector, String hasta) throws Exception;

    public List<CarFunCuentasPorPagarListadoComprasTO> getCarFunCuentasPorPagarListadoComprasTO(String empresa,
            String sector, String proveedor, String desde, String hasta) throws Exception;

    public List<CarFunCuentasPagadasListadoTO> listarCuentasPagadas(String empresa, String sector, String proveedor, String desde, String hasta, boolean aprobadas) throws Exception;

    public String aprobarPago(CuentasPorPagarDetalladoTO cuentaPorPagar, SisInfoTO sisInfoTO) throws Exception;

    public List<CarCuentasPorPagarDetalladoGranjasMarinasTO> getCarCuentasPorPagarDetalladoGranjasMarinasTO(String fecha) throws Exception;

    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTOAgrupadoCP(String empresa, String sector, String desde, String hasta, String proveedor, String formaPago) throws Exception;

    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTOAgrupadoProveedor(String empresa, String sector, String desde, String hasta, String proveedor, String formaPago) throws Exception;
}
