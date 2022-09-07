package ec.com.todocompu.ShrimpSoftServer.anexos.report;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxDiferenciasTributacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario103TO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario104TO;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunRegistroDatosCrediticiosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaConsolidadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesFuenteIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesRentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRespuestasErroresPorNotasDeDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionVentaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxRetencionesVenta;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.web.ArchivoTO;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface ReporteAnexosService {

    public byte[] generarReporteTalonResumen(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde,
            String hasta, List<AnxTalonResumenTO> listaAnxTalonResumenTO) throws Exception;

    public byte[] generarReporteTalonResumenVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde,
            String hasta, List<AnxTalonResumenVentaTO> listaAnxTalonResumenVentaTO) throws Exception;

    public byte[] generarReporteListadoRetencionesPorNumero(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AnxListaRetencionesTO> listaAnxListaRetencionesTO) throws Exception;

    public byte[] generarReporteListadoRetencionesVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta, List<AnxListadoRetencionesVentasTO> anxListadoRetencionesVentasTOs)
            throws Exception;

    public byte[] generarReporteListadoDevolucionIva(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<AnxFunListadoDevolucionIvaTO> anxFunListadoDevolucionIvaTOs) throws Exception;

    public byte[] generarReporteListadoDevolucionIvaVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<AnxFunListadoDevolucionIvaVentasTO> anxFunListadoDevolucionIvaVentasTO) throws Exception;

    public byte[] generarReporteRegistroDatosCrediticios(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde,
            String hasta, List<AnxFunRegistroDatosCrediticiosTO> listaAnxFunRegistroDatosCrediticiosTO) throws Exception;

    public Map<String, Object> generarReporteListadoDevolucionIva(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AnxFunListadoDevolucionIvaTO> anxFunListadoDevolucionIvaTOs, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteListadoDevolucionIvaVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AnxFunListadoDevolucionIvaVentasTO> anxFunListadoDevolucionIvaVentasTO, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteTalonResumen(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AnxTalonResumenTO> listaAnxTalonResumenTO, String desde, String hasta) throws Exception;

    public Map<String, Object> generarReporteTalonResumenVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxTalonResumenVentaTO> listaAnxTalonResumenVentaTO, String desde, String hasta) throws Exception;

    public Map<String, Object> generarReporteConsolidadoRetencionesVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AnxListaConsolidadoRetencionesVentasTO> listaAnxConsolidadoRetencionesVentasTO, String desde, String hasta) throws Exception;

    public byte[] generarReporteConsolidadoRetencionesVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta,
            List<AnxListaConsolidadoRetencionesVentasTO> listaAnxConsolidadoRetencionesVentasTO) throws Exception;

    public Map<String, Object> generarReporteListadoRetencionesVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AnxListadoRetencionesVentasTO> anxListadoRetencionesVentasTOs, String desde, String hasta) throws Exception;

    public byte[] generarReporteListadoAnxListaVentaExportacionTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<AnxListaVentaExportacionTO> anxListaVentaExportacionTO) throws Exception;

    public byte[] generarReporteListadoAnxRetencionCompraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<AnxRetencionCompraTO> listaAnxRetencionCompraTO) throws Exception;

    //Pdf
    public byte[] generarReporteRetencionesEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListadoCompraElectronicaTO> listado, String nombreReporte) throws Exception;

    //Excel
    public Map<String, Object> exportarRetencionesEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListadoCompraElectronicaTO> listado, String desde, String hasta) throws Exception;

    public Map<String, Object> generarReporteRetencionesRentaComprasTD(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AnxListaRetencionesRentaTO> listaAnxConsolidadoRetencionesRentaTO, String fechaDesde, String fechaHasta, String orden) throws Exception;

    public byte[] generarReporteRetencionesRentaComprasTD(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<AnxListaRetencionesRentaTO> listaAnxConsolidadoRetencionesRentaTO) throws Exception;

    public Map<String, Object> generarReporteAnexoListadoRetencionesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaRetencionesTO> listaRetencionesTO, String fechaDesde, String fechaHasta) throws Exception;

    public byte[] generarReporteAnexoListadoRetencionesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<AnxListaRetencionesTO> listaRetencionesTO) throws Exception;

    public Map<String, Object> generarReporteListadoRetencionesPorNumero(List<AnxListaRetencionesTO> listaAnxListaRetencionesTO,
            UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> exportarVentasElectronicasEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaVentaElectronicaTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public byte[] generarReporteVentasElectronicasEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaVentaElectronicaTO> listado, String nombreReporte) throws Exception;

    public Map<String, Object> generarReporteRetencionesIvaCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaRetencionesFuenteIvaTO> listaAnxRetencionesFuentesIva, String fechaDesde, String fechaHasta) throws Exception;

    public byte[] generarReporteRetencionesIvaCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String fechaDesde, String fechaHasta,
            List<AnxListaRetencionesFuenteIvaTO> listaAnxRetencionesFuentesIva) throws Exception;

    public Map<String, Object> generarReporteRegistroDatosCrediticios(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AnxFunRegistroDatosCrediticiosTO> listaAnxFunRegistroDatosCrediticiosTO, String desde, String hasta) throws Exception;

    public List<InputStream> generarReporteRide(String empresa, List<AnxListaVentaElectronicaTO> listaAnxListaVentaElectronicaTO) throws Exception;

    public byte[] generarReporteRideMatricial(String empresa, List<AnxListaVentaElectronicaTO> listaAnxListaVentaElectronicaTO) throws Exception;

    public List<InputStream> generarReporteRideRetencionesEmitidas(String empresa, List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO) throws Exception;

    public byte[] generarReporteRideRetencionesEmitidasMatricial(String empresa, List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO) throws Exception;

    public List<ArchivoTO> generarXmlRetencionesElectronicasEmitidas(String empresa, List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO) throws Exception;

    public List<InputStream> imprimirRideVentasPendientesElectronicas(String empresa, List<AnxListaVentasPendientesTO> listaEnviar) throws Exception;

    public void generarTXT(List<AnxFunRegistroDatosCrediticiosTO> listado, HttpServletResponse response) throws Exception;

    public ArchivoTO generarXmlAnexoTransaccional(String empresa, String fechaDesde, String fechaHasta, String ruc, String nombreEmpresa, Boolean agente) throws Exception;

    public List<InputStream> generarRideRetencionesPendientes(String empresa, List<AnxListaRetencionesPendientesTO> listaRetenciones) throws Exception;

    public List<AnxRespuestasErroresPorNotasDeDebitoTO> validacionPorNotasDebito(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public byte[] generarReporteComprobanteRetencion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa, List<AnxListaRetencionesTO> anxListaRetencionesTO, boolean sinDescripcionDocumento, String reportComprobanteRetencion) throws Exception;

    public Map<String, Object> exportarAnxRespuestasErroresPorNotasDeDebitoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRespuestasErroresPorNotasDeDebitoTO> lista) throws Exception;

    public List<InputStream> generarRideLiquidacionCompraPendientes(String empresa, List<AnxListaLiquidacionComprasPendientesTO> listado) throws Exception;

    //guia
    public List<InputStream> imprimirRideGuiaRemisionPendientesElectronicas(String empresa, List<AnxListaGuiaRemisionPendientesTO> listaEnviar) throws Exception;

    public byte[] generarReporteRideGuiaRemisionMatricial(String empresa, List<AnxListaGuiaRemisionElectronicaTO> listaAnxListaGuiaRemisionElectronicaTO) throws Exception;

    public List<InputStream> generarReporteRideGuiaRemision(String empresa, List<AnxListaGuiaRemisionElectronicaTO> listaAnxListaGuiaRemisionElectronicaTO) throws Exception;

    //liquidacion
    public List<InputStream> generarReporteRideLiquidacionCompra(String empresa, List<AnxListaLiquidacionComprasElectronicaTO> listado) throws Exception;

    public byte[] generarRideLiquidacionCompraElectronicasEmitidasMatricial(String empresa, List<AnxListaLiquidacionComprasElectronicaTO> listado) throws Exception;

    public byte[] generarReporteLiquidacionComprasEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaLiquidacionComprasElectronicaTO> listado, String nombreReporte) throws Exception;

    //exportaciones
    public Map<String, Object> exportarReporteAnxListaVentaExportacionTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaVentaExportacionTO> listaAnxListaVentaExportacionTO, String fechaDesde, String fechaHasta) throws Exception;

    public byte[] generarReporteFormulario103(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, List<AnxFormulario103TO> lista) throws Exception;

    public Map<String, Object> exportarReporteFormulario103(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxFormulario103TO> lista, String desde, String hasta) throws Exception;

    public Map<String, Object> exportarReporteListadoAnxRetencionCompraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRetencionCompraTO> lista, String desde, String hasta) throws Exception;

    public Map<String, Object> exportarReporteFormulario104(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxFormulario104TO> lista, String desde, String hasta) throws Exception;

    public byte[] generarReporteFormulario104(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, List<AnxFormulario104TO> lista) throws Exception;

    public byte[] generarReporteDiferenciasTributacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxDiferenciasTributacionTO> listado) throws Exception;

    public Map<String, Object> exportarReporteDiferenciasTributacion(Map<String, Object> map) throws Exception;

    public byte[] generarReporteListadoAnxRetencionVentaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<AnxRetencionVentaTO> listado) throws Exception;

    public Map<String, Object> exportarReporteListadoAnxRetencionVentaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRetencionVentaTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public byte[] generarReporteRetencionVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRetencionVentaReporteTO> listaRetencionVenta, String nombreReporte) throws Exception;
        
    public Map<String, Object> exportarRetencionesComprobantesVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRetencionesVenta> listado) throws Exception;

    public Map<String, Object> exportarComprobantesRecibidos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxComprobantesElectronicosRecibidos> listado) throws Exception;
}
