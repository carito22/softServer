package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
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
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface CompraDao extends GenericDao<AnxCompra, AnxCompraPK> {

    public AnxCompraTO getAnexoCompraTO(String empresa, String periodo, String motivo, String numeroCompra)
            throws Exception;

    public AnxCompraTO getAnexoCompraTO(String empresa, String numeroDocumento) throws Exception;

    public Boolean reestructurarRetencion(AnxCompra anxCompra, SisSuceso sisSuceso) throws Exception;

    public Boolean actualizarClaveExternaRetencion(AnxCompraPK pk, String clave, SisSuceso sisSuceso) throws Exception;

    public Boolean existeDetalleRetención(AnxCompraPK pk) throws Exception;

    public String getConteoNumeroRetencion(String empresaCodigo, String numeroRetencion) throws Exception;

    public String getCompAutorizacion(String empCodigo, String provCodigo, String codTipoComprobante,
            String numComplemento) throws Exception;

    public List<AnxListadoCompraElectronicaTO> getListaAnxComprasElectronicaTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception;

    public AnxUltimaAutorizacionTO getAnxUltimaAutorizacionTO(String empresa, String proveedor, String tipoDocumento,
            String secuencial, String fechaFactura) throws Exception;

    public List<AnxListaRetencionesTO> getAnexoListaRetencionesTO(String empresa, String fechaDesde, String fechaHasta)
            throws Exception;

    public List<AnxListaRetencionesRentaTO> getAnexoListaRetencionesRentaTO(String empresa, String fechaDesde,
            String fechaHasta, String pOrden) throws Exception;

    public List<AnxListaRetencionesFuenteIvaTO> getAnexoListaRetencionesFuenteIvaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    public List<AnxListaRetencionesTO> getAnexoFunListadoRetencionesPorNumero(String empresa, String fechaDesde,
            String fechaHasta, String parametroEstado) throws Exception;

    public List<String> getAnexoFunListadoRetencionesHuerfanas(String empresa, String fechaDesde, String fechaHasta)
            throws Exception;

    public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteTO(String empresa) throws Exception;

    public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteAutomaticasTO(String empresa) throws Exception;

    public List<AnxFunListadoDevolucionIvaTO> getAnxFunListadoDevolucionIvaTOs(String empCodigo, String desde,
            String hasta) throws Exception;

    public List<AnxTalonResumenTO> getAnexoTalonResumenTO(String empresa, String fechaDesde, String fechaHasta)
            throws Exception;

    public Boolean getComprobanteVerificarAutorizacion(String empresa, String comprobanteTipo, String establecimiento,
            String puntoEmision, String secuencialInicio, String secuencialFin) throws Exception;

    public List<String> getAnexoFunListadoComprobantesPendientes(String empresa, String fechaDesde, String fechaHasta)
            throws Exception;

    public List<AnxFormulario103TO> listarFormulario103(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxRetencionCompraTO> listarAnxRetencionCompraTO(String empresa, String fechaDesde, String fechaHasta, String codigoSustento, String registros) throws Exception;

    public List<AnxFormulario104TO> listarFormulario104(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxRetencionVentaTO> getListaAnxRetencionVentaTO(String empresa, String fechaDesde, String fechaHasta, String registro) throws Exception;
}
