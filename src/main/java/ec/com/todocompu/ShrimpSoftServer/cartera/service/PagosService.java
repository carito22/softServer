package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarContableTO;
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
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorPagarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.Map;

public interface PagosService {

    @Transactional
    public CarContableTO insertarCarPagos(CarPagosTO carPagosTO,
            List<CarPagosDetalleComprasTO> carPagosDetalleComprasTOs,
            List<CarPagosDetalleAnticiposTO> carPagosDetalleAnticiposTOs,
            List<CarPagosDetalleFormaTO> carPagosDetalleFormaTOs, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public CarListaPagosProveedorTO getPagosConsultaProveedorTO(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    @Transactional
    public List<CarListaPagosTO> getPagosConsultaDetalleComprasTO(String empresa, String periodo, String numero)
            throws Exception;

    @Transactional
    public List<CarListaPagosCobrosDetalleFormaTO> getPagosConsultaDetalleFormaTO(String empresa, String periodo,
            String numero) throws Exception;

    @Transactional
    public List<CarListaPagosTO> getCarListaPagosTO(String empresa, String proveedor) throws Exception;

    @Transactional
    public List<CarFunPagosTO> getCarFunPagosTO(String empresa, String sector, String desde, String hasta,
            String proveedor, boolean incluirTodos) throws Exception;

    @Transactional
    public List<CarFunPagosPruebaTO> getCarFunPagosPruebaTO(String empresa, String desde, String hasta,
            String proveedor) throws Exception;

    @Transactional
    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTO(String empresa, String sector, String desde, String hasta,
            String proveedor, String formaPago) throws Exception;

    @Transactional
    public List<CuentasPorPagarDetalladoTO> getCarListaCuentasPorPagarDetalladoTO(String empresa, String sector,
            String proveedor, String hasta, boolean excluirAprobadas, boolean incluirCheques,String formatoMensual) throws Exception;

    @Transactional
    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorPagarGeneralTO(String empresa, String sector,
            String hasta) throws Exception;

    @Transactional
    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorPagarAnticiposTO(String empresa,
            String sector, String hasta) throws Exception;

    @Transactional
    public List<CarFunCuentasPorPagarListadoComprasTO> getCarFunCuentasPorPagarListadoComprasTO(String empresa,
            String sector, String proveedor, String desde, String hasta) throws Exception;

    @Transactional
    public List<CarListaPagosCobrosDetalleAnticipoTO> getPagosConsultaDetalleAnticipo(String empresa, String periodo,
            String numero) throws Exception;

    @Transactional
    public Map<String, Object> obtenerDatosParaPagos(Map<String, Object> map) throws Exception;

    @Transactional
    public Map<String, Object> obtenerPago(Map<String, Object> map) throws Exception;

    public List<RespuestaWebTO> aprobarPagos(List<CuentasPorPagarDetalladoTO> listado, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<String> validarPagosAprobados(List<CarPagosDetalleComprasTO> carPagosDetalleComprasTOs) throws Exception;

    @Transactional
    public List<CarFunCuentasPagadasListadoTO> listarCuentasPagadas(String empresa, String sector, String proveedor, String desde, String hasta, boolean aprobadas) throws Exception;

    public List<CarCuentasPorPagarDetalladoGranjasMarinasTO> getCarCuentasPorPagarDetalladoGranjasMarinasTO(String fecha) throws Exception;

    @Transactional
    public CarContableTO mayorizarCarPagos(CarPagosTO carPagosTO,
            List<CarPagosDetalleComprasTO> carPagosDetalleComprasTOs,
            List<CarPagosDetalleAnticiposTO> carPagosDetalleAnticiposTOs,
            List<CarPagosDetalleFormaTO> carPagosDetalleFormaTOs, SisInfoTO sisInfoTO) throws Exception;

    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTOAgrupadoCP(String empresa, String sector, String desde, String hasta, String proveedor, String formaPago) throws Exception;

    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTOAgrupadoProveedor(String empresa, String sector, String desde, String hasta, String proveedor, String formaPago) throws Exception;
}
