package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarContableTO;
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
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface CobrosService {

    public CarContableTO insertarCarCobros(CarCobrosTO carCobrosTO,
            List<CarCobrosDetalleVentasTO> carCobrosDetalleVentasTOs,
            List<CarCobrosDetalleAnticiposTO> carCobrosDetalleAnticiposTOs,
            List<CarCobrosDetalleFormaTO> carCobrosDetalleFormaTOs, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    public CarListaCobrosClienteTO getCobrosConsultaClienteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    public List<CarListaMayorAuxiliarClienteProveedorTO> getCarListaMayorAuxiliarClienteProveedorTO(String empresa,
            String sector, String proveedor, String desde, String hasta, char accion, boolean anticipos) throws Exception;

    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorCobrarAnticiposTO(String empresa,
            String sector, String hasta) throws Exception;

    public List<CuentasPorCobrarDetalladoTO> getCarListaCuentasPorCobrarDetalladoTO(String empresa, String sector,
            String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception;

    public List<CuentasPorCobrarDetalladoTO> getCarListaCuentasPorCobrarDetalladoTOCortesConexion(String empresa, String sector,
            String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception;

    public List<CarFunCuentasPorCobrarListadoVentasTO> getCarFunCuentasPorCobrarListadoVentasTO(String empresa,
            String sector, String cliente, String desde, String hasta) throws Exception;

    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorCobrarGeneralTO(String empresa, String sector,
            String hasta) throws Exception;

    public List<CarListaCobrosTO> getCobrosConsultaDetalleVentasTO(String empresa, String periodo, String numero)
            throws Exception;

    public List<CarListaPagosCobrosDetalleFormaTO> getCobrosConsultaDetalleFormaTO(String empresa, String periodo,
            String numero, boolean hayPostfechados) throws Exception;

    public List<CarListaPagosCobrosDetalleAnticipoTO> getCobrosConsultaDetalleAnticipo(String empresa, String periodo,
            String numero) throws Exception;

    public List<CarListaCobrosTO> getCarListaCobrosTO(String empresa, String cliente) throws Exception;

    public java.math.BigDecimal getCarDeudaVencida(String empresa, String cliente) throws Exception;

    public List<CarFunCobrosTO> getCarFunCobrosTO(String empresa, String sector, String desde, String hasta,
            String cliente, boolean incluirTodos) throws Exception;

    public List<CarFunCobrosDetalleTO> getCarFunCobrosDetalleTO(String empresa, String sector, String desde,
            String hasta, String cliente, String formaPago) throws Exception;

    public Map<String, Object> obtenerDatosParaCuentasCobrarDetallado(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosParaCobros(Map<String, Object> map) throws Exception;

    @Transactional
    public Map<String, Object> obtenerCobro(Map<String, Object> map) throws Exception;

    @Transactional
    public CarContableTO mayorizarCarCobros(CarCobrosTO carCobrosTO, List<CarCobrosDetalleVentasTO> carCobrosDetalleVentasTOs,
            List<CarCobrosDetalleAnticiposTO> carCobrosDetalleAnticiposTOs, List<CarCobrosDetalleFormaTO> carCobrosDetalleFormaTOs, SisInfoTO sisInfoTO) throws Exception;

    public List<CarFunCobrosDetalleTO> getCarFunCobrosDetalleTOAgrupadoCliente(String empresa, String sector, String desde,
            String hasta, String cliente, String formaPago) throws Exception;
}
