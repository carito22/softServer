package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
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

public interface CobrosDao extends GenericDao<CarCobros, CarCobrosPK> {

    public CarListaCobrosClienteTO getCobrosConsultaCliente(String empresa, String periodo, String tipo, String numero)
            throws Exception;

    public List<CarListaPagosCobrosDetalleFormaTO> getCobrosConsultaDetalleForma(String empresa, String periodo,
            String numero, boolean hayPostfechados) throws Exception;

    public List<CarListaPagosCobrosDetalleAnticipoTO> getCobrosConsultaDetalleAnticipo(String empresa, String periodo,
            String numero) throws Exception;

    public List<CarFunCobrosTO> getCarFunCobrosTO(String empresa, String sector, String desde, String hasta,
            String cliente, boolean incluirTodos) throws Exception;

    public List<CarFunCobrosDetalleTO> getCarFunCobrosDetalleTO(String empresa, String sector, String desde,
            String hasta, String cliente, String formaPago) throws Exception;

    public List<CarListaCobrosTO> getCobrosConsultaDetalleVentas(String empresa, String periodo, String numero)
            throws Exception;

    public List<CarListaCobrosTO> getCarListaCobrosTO(String empresa, String cliente) throws Exception;

    public java.math.BigDecimal getCarDeudaVencida(String empresa, String cliente) throws Exception;

    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorCobrarAnticiposTO(String empresa,
            String sector, String hasta) throws Exception;

    public List<CuentasPorCobrarDetalladoTO> getCarListaCuentasPorCobrarDetalladoTO(String empresa, String sector,
            String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception;

    public List<CuentasPorCobrarDetalladoTO> getCarListaCuentasPorCobrarDetalladoTOCorteconexion(String empresa, String sector,
            String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception;

    public List<CarFunCuentasPorCobrarListadoVentasTO> getCarFunCuentasPorCobrarListadoVentasTO(String empresa,
            String sector, String cliente, String desde, String hasta) throws Exception;

    public List<CarListaCobrosTO> getCarListaCobrosVentasTO(String empresa, String cliente) throws Exception;

    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorCobrarGeneralTO(String empresa, String sector,
            String hasta) throws Exception;

    public Boolean buscarCarCobrosForma(String ctaContable, String empresa) throws Exception;
    
    public Boolean buscarCarCobrosForma(String ctaContable, String empresa, String sector) throws Exception;

    public List<CarListaMayorAuxiliarClienteProveedorTO> getCarListaMayorAuxiliarClienteProveedorTO(String empresa,
            String sector, String codigo, String desde, String hasta, char accion, boolean anticipos) throws Exception;

    public List<CarFunCobrosDetalleTO> getCarFunCobrosDetalleTOAgrupadoCliente(String empresa, String sector, String desde, String hasta, String cliente, String formaPago) throws Exception;
}
