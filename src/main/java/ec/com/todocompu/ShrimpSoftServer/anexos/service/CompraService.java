package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario103TO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario104TO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesFuenteIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesRentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxUltimaAutorizacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntosComprasWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaSecuencialesTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

public interface CompraService {

    @Transactional
    public AnxCompraTO getAnexoCompraTO(String empresa, String periodo, String motivo, String numeroCompra)
            throws Exception;

    @Transactional
    public AnxCompra getAnexoCompra(String empresa, String periodo, String motivo, String numeroCompra)
            throws Exception;

    public AnxCompraTO getAnexoCompraTO(String empresa, String numeroDocumento) throws Exception;

    @Transactional
    public List<AnxListaRetencionesTO> getAnexoListaRetencionesTO(String empresa, String fechaDesde, String fechaHasta)
            throws Exception;

    @Transactional
    public List<AnxListaRetencionesRentaTO> getAnexoListaRetencionesRentaTO(String empresa, String fechaDesde,
            String fechaHasta, String pOrden) throws Exception;

    @Transactional
    public List<AnxListaRetencionesFuenteIvaTO> getAnexoListaRetencionesFuenteIvaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    @Transactional
    public List<AnxListaRetencionesTO> getAnexoFunListadoRetencionesPorNumero(String empresa, String fechaDesde,
            String fechaHasta, String parametroEstado) throws Exception;

    @Transactional
    public List<AnxTalonResumenTO> getAnexoTalonResumenTO(String empresa, String fechaDesde, String fechaHasta)
            throws Exception;

    @Transactional
    public List<AnxFunListadoDevolucionIvaTO> getAnxFunListadoDevolucionIvaTOs(String empCodigo, String desde,
            String hasta) throws Exception;

    @Transactional
    public String reestructurarRetencion(AnxCompraTO anxCompraTO, String usuario, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Boolean actualizarClaveExternaRetencion(AnxCompraPK anxCompra, String clave, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<String> getAnexoFunListadoRetencionesHuerfanas(String empresa, String fechaDesde, String fechaHasta)
            throws Exception;

    @Transactional
    public List<String> getAnexoFunListadoComprobantesPendientes(String empresa, String fechaDesde, String fechaHasta)
            throws Exception;

    @Transactional
    public String getCompAutorizacion(String empCodigo, String provCodigo, String codTipoComprobante,
            String numComplemento) throws Exception;

    @Transactional
    public List<AnxListadoCompraElectronicaTO> getListaAnxComprasElectronicaTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteTO(String empresa) throws Exception;

    @Transactional
    public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteAutomaticasTO(String empresa) throws Exception;

    @Transactional
    public AnxUltimaAutorizacionTO getAnxUltimaAutorizacionTO(String empresa, String proveedor, String tipoDocumento,
            String secuencial, String fechaFactura) throws Exception;

    @Transactional
    public String validarSecuenciaPermitida(String empresa, String numeroRetencion, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public List<InvListaSecuencialesTO> listarInvListaSecuencialesRetenciones(String empresa, String fecha) throws Exception;

    @Transactional
    public List<InvAdjuntosComprasWebTO> obtenerListadoImagenesCompraNecesitaSoporte(String empresa, List<AnxFunListadoDevolucionIvaTO> listado) throws Exception;

    @Transactional
    public List<AnxFormulario103TO> listarFormulario103(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public List<AnxRetencionCompraTO> listarAnxRetencionCompraTO(String empresa, String fechaDesde, String fechaHasta, String codigoSustento, String registros) throws Exception;

    @Transactional
    public List<AnxFormulario104TO> listarFormulario104(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public Map<String, Object> listarSecuencialesRetenciones(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public List<AnxRetencionVentaTO> getListaAnxRetencionVentaTO(String empresa, String fechaDesde, String fechaHasta, String registro) throws Exception;

}
