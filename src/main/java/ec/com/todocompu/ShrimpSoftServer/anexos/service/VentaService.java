package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxEstablecimientoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaConsolidadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaEstablecimientoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPuntoEmisionComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface VentaService {

    public List<AnxEstablecimientoComboTO> getEstablecimientos(String empresa) throws Exception;

    public List<AnxPuntoEmisionComboTO> getPuntosEmision(String empresa, String establecimiento) throws Exception;

    public AnxNumeracionLineaTO getNumeroAutorizacion(String empresa, String numeroRetencion, String numeroComprobante,
            String fechaVencimiento) throws Exception;

    public AnxVentaTO getAnexoVentaTO(String empresa, String periodo, String motivo, String numeroVenta)
            throws Exception;

    public List<AnxListaRetencionVentaTO> getAnxListaRetencionVentaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    public List<AnxListaConsolidadoRetencionesVentasTO> getAnxListaConsolidadoRetencionesVentasTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxListaEstablecimientoRetencionesVentasTO> getAnxListaEstablecimientoRetencionesVentasTO(
            String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxListadoRetencionesVentasTO> getAnxListadoRetencionesVentasTO(String empresa, String tipoDocumento,
            String establecimiento, String puntoEmision, String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxFunListadoDevolucionIvaVentasTO> getAnxFunListadoDevolucionIvaVentasTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxTalonResumenVentaTO> getAnexoTalonResumenVentaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    public String eliminarAnxVentas(AnxVentaTO anxVentaTO, SisInfoTO sisInfoTO) throws Exception;

    public String accionAnxVenta(AnxVentaTO anxVentaTO, String numeroFactura, String periodoFactura, String cliCodigo,
            char accion, SisInfoTO sisInfoTO) throws Exception;

    public List<AnxListaVentaElectronicaTO> getListaAnxVentaElectronicaTO(String empresa,
            String fechaDesde, String fechaHasta, String tipoDocumento) throws Exception;

    public String getUltimaNumeracionComprobante(String empresa, String comprobante, String secuencial)
            throws Exception;

    public Map<String, Object> obtenerAnexoVentaTO(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosConsultaRetencionesVentasListadoSimple(Map<String, Object> map) throws Exception;
}
