package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhOrdenBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.Map;

@Transactional
public interface PagosAnticiposService {

    public CarListaPagosProveedorTO getPagosConsultaProveedorAnticipoTO(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    public List<CarFunPagosSaldoAnticipoTO> getCarFunPagosSaldoAnticipoTO(String empresa, String proveedor)
            throws Exception;

    public CarPagosCobrosAnticipoTO getCarPagosCobrosAnticipoTO(String empresa, String periodo, String tipo,
            String numero, char accion) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTO(String empresa,
            String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoProveedor(String empresa, String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoCC(String empresa, String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception;

    public MensajeTO insertarAnticiposPago(CarPagosAnticipoTO carPagosAnticipoTO, String observaciones,
            String nombreProveedor, String fecha, String sectorProveedor, String documento, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO)
            throws Exception;

    public Map<String, Object> obtenerDatosParaCrudAnticipos(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerAnticipoPago(Map<String, Object> map) throws Exception;

    public RespuestaWebTO generarOrdenBancariaCartera(RhOrdenBancariaTO ordenBancariaTO, String sector, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO mayorizarAnticiposPago(CarPagosAnticipoTO carPagosAnticipoTO, String observaciones, String nombreProveedor, String fecha,
            String sectorProveedor, String documento, SisInfoTO sisInfoTO) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistorico(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistoricoAgrupadoProveedor(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistoricoAgrupadoCC(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception;

}
