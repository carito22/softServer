package ec.com.todocompu.ShrimpSoftServer.anexos.report;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnuladosDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxVentaExportacionDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraFormaPagoDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraReembolsoDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.SustentoDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.GuiaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoComprobanteService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProveedorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnexosBBListaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxDiferenciasTributacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario103TO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario104TO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunRegistroDatosCrediticiosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaComprobanteAnuladoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaConsolidadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaEstablecimientoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesFuenteIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesRentaComprasTipoDocumentoTO;
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
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxRetencionesVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteANexoListaDevolucionIVAventas;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteAnxFunListadoDevolucionIva;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteAnxListaRetenciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteAnxRetencionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteConsolidadoRetencionesVentas;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteListadoRetencionesVentas;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteObjetoAnexo;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteTalonResumenRetenciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.web.ArchivoTO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang.ArrayUtils;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

@Service
public class ReporteAnexosServiceImpl implements ReporteAnexosService {

    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private CompraDao compraDao;
    @Autowired
    private AnuladosDao anuladosDao;
    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private CompraFormaPagoDao compraFormaPagoDao;
    @Autowired
    private CompraDetalleDao compraDetalleDao;
    @Autowired
    private CompraReembolsoDao compraReembolsoDao;
    @Autowired
    private SustentoDao sustentoDao;
    @Autowired
    private CompraService compraService;
    @Autowired
    private TipoComprobanteService tipoComprobanteService;
    private String modulo = "anexos";
    @Autowired
    private VentaElectronicaService ventaElectronicaService;
    @Autowired
    private ReporteComprobanteElectronicoService reporteComprobanteElectronicoService;
    @Autowired
    private CompraElectronicaService compraElectronicaService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private GuiaElectronicaService guiaElectronicaService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private AnxVentaExportacionDao anxVentaExportacionDao;

    @Override
    public byte[] generarReporteRetencionesEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListadoCompraElectronicaTO> listado, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteConsolidadoRetencionesVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde,
            String hasta, List<AnxListaConsolidadoRetencionesVentasTO> listaAnxConsolidadoRetencionesVentasTO) throws Exception {
        List<ReporteConsolidadoRetencionesVentas> lista = new ArrayList<ReporteConsolidadoRetencionesVentas>();
        for (AnxListaConsolidadoRetencionesVentasTO plgTO : listaAnxConsolidadoRetencionesVentasTO) {
            ReporteConsolidadoRetencionesVentas reporteConsolidadoRetencionesVTO = new ReporteConsolidadoRetencionesVentas();
            reporteConsolidadoRetencionesVTO.setDesde(desde);
            reporteConsolidadoRetencionesVTO.setHasta(hasta);
            reporteConsolidadoRetencionesVTO.setRvcTransaccion((plgTO.getRvcTransaccion() == null ? "" : plgTO.getRvcTransaccion()));
            reporteConsolidadoRetencionesVTO.setRvcIdentificacion((plgTO.getRvcIdentificacion() == null ? "" : plgTO.getRvcIdentificacion()));
            reporteConsolidadoRetencionesVTO.setRvcComprobanteTipo((plgTO.getRvcComprobanteTipo() == null ? "" : plgTO.getRvcComprobanteTipo()));
            reporteConsolidadoRetencionesVTO.setRvcNumeroRetenciones((plgTO.getRvcNumeroRetenciones() == null ? 0 : plgTO.getRvcNumeroRetenciones()));
            reporteConsolidadoRetencionesVTO.setRvcBaseNoObjetoIva((plgTO.getRvcBaseNoObjetoIva() == null ? null : plgTO.getRvcBaseNoObjetoIva()));
            reporteConsolidadoRetencionesVTO.setRvcBase0((plgTO.getRvcBase0() == null ? null : plgTO.getRvcBase0()));
            reporteConsolidadoRetencionesVTO.setVenBaseImponible((plgTO.getVenBaseImponible() == null ? null : plgTO.getVenBaseImponible()));
            reporteConsolidadoRetencionesVTO.setVenMontoIce(plgTO.getVenMontoIce() == null ? null : plgTO.getVenMontoIce());
            reporteConsolidadoRetencionesVTO.setVenMontoIva((plgTO.getVenMontoIva() == null ? null : plgTO.getVenMontoIva()));
            reporteConsolidadoRetencionesVTO.setVenValorRetenidoIva((plgTO.getVenValorRetenidoIva() == null ? null : plgTO.getVenValorRetenidoIva()));
            reporteConsolidadoRetencionesVTO.setVenValorRetenidoRenta((plgTO.getVenValorRetenidoRenta() == null ? null : plgTO.getVenValorRetenidoRenta()));
            lista.add(reporteConsolidadoRetencionesVTO);
        }
        return genericReporteService.generarReporte(modulo, "reportConsolidadoRetencionesVentas.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteFormulario103(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, List<AnxFormulario103TO> listado) throws Exception {
        List<ReporteTalonResumenRetenciones> lista = new ArrayList<>();
        listado.stream().map((plgTO) -> {
            ReporteTalonResumenRetenciones reporteTalonResumenRetencion = new ReporteTalonResumenRetenciones();
            reporteTalonResumenRetencion.setDesde(desde);
            reporteTalonResumenRetencion.setHasta(hasta);
            reporteTalonResumenRetencion.setRetConcepto((plgTO.getRetConcepto() == null ? "" : plgTO.getRetConcepto()));
            reporteTalonResumenRetencion.setRetCodigoRetencion((plgTO.getRetCodigoRetencion() == null ? "" : plgTO.getRetCodigoRetencion()));
            reporteTalonResumenRetencion
                    .setRetBaseImponible((plgTO.getRetBaseImponible() == null ? null : plgTO.getRetBaseImponible()));
            reporteTalonResumenRetencion.setRetCodigoFormulario(plgTO.getRetCodigoFormulario103() == null ? "" : plgTO.getRetCodigoFormulario103());
            reporteTalonResumenRetencion
                    .setRetValorRetenido((plgTO.getRetValorRetenido() == null ? null : plgTO.getRetValorRetenido()));
            return reporteTalonResumenRetencion;
        }).forEachOrdered((reporteTalonResumenRetencion) -> {
            lista.add(reporteTalonResumenRetencion);
        });
        return genericReporteService.generarReporte(modulo, "reportFormulario103.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteFormulario104(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, List<AnxFormulario104TO> listado) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("desde", desde);
        parametros.put("hasta", hasta);
        return genericReporteService.generarReporte(modulo, "reportFormulario104.jrxml",
                usuarioEmpresaReporteTO, parametros, listado);
    }

    @Override
    public byte[] generarReporteDiferenciasTributacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxDiferenciasTributacionTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "ReporteDiferenciasTributacion.jrxml",
                usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteTalonResumen(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde,
            String hasta, List<AnxTalonResumenTO> listaAnxTalonResumenTO) throws Exception {
        List<ReporteTalonResumenRetenciones> lista = new ArrayList<ReporteTalonResumenRetenciones>();
        for (AnxTalonResumenTO plgTO : listaAnxTalonResumenTO) {
            ReporteTalonResumenRetenciones reporteTalonResumenRetencion = new ReporteTalonResumenRetenciones();
            reporteTalonResumenRetencion.setDesde(desde);
            reporteTalonResumenRetencion.setHasta(hasta);
            reporteTalonResumenRetencion.setRetConcepto((plgTO.getRetConcepto() == null ? "" : plgTO.getRetConcepto()));
            reporteTalonResumenRetencion.setRetCantidad((plgTO.getRetCantidad() == null ? 0 : plgTO.getRetCantidad()));
            reporteTalonResumenRetencion
                    .setRetBaseImponible((plgTO.getRetBaseImponible() == null ? null : plgTO.getRetBaseImponible()));
            reporteTalonResumenRetencion
                    .setRetPorcentaje((plgTO.getRetPorcentaje() == null ? null : plgTO.getRetPorcentaje()));
            reporteTalonResumenRetencion
                    .setRetValorRetenido((plgTO.getRetValorRetenido() == null ? null : plgTO.getRetValorRetenido()));

            lista.add(reporteTalonResumenRetencion);
        }
        return genericReporteService.generarReporte(modulo, "reportTalonResumenRetenciones.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteTalonResumenVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde,
            String hasta, List<AnxTalonResumenVentaTO> listaAnxTalonResumenVentaTO) throws Exception {
        List<ReporteTalonResumenVentas> lista = new ArrayList<ReporteTalonResumenVentas>();
        for (AnxTalonResumenVentaTO plgTO : listaAnxTalonResumenVentaTO) {
            ReporteTalonResumenVentas reporteTalonResumenVentas = new ReporteTalonResumenVentas();
            reporteTalonResumenVentas.setDesde(desde);
            reporteTalonResumenVentas.setHasta(hasta);
            reporteTalonResumenVentas.setVenCantidadComprobantes((plgTO.getVenCantidadComprobantes() == null ? 0 : plgTO.getVenCantidadComprobantes()));
            reporteTalonResumenVentas.setVenComprobante((plgTO.getVenComprobante() == null ? "" : plgTO.getVenComprobante()));
            reporteTalonResumenVentas.setVenEstablecimiento((plgTO.getVenEstablecimiento() == null ? "" : plgTO.getVenEstablecimiento()));
            reporteTalonResumenVentas.setVenPuntoEmision((plgTO.getVenPuntoEmision() == null ? "" : plgTO.getVenPuntoEmision()));
            reporteTalonResumenVentas.setVenBaseNoObjetoIva((plgTO.getVenBaseNoObjetoIva() == null ? new BigDecimal("0.00") : plgTO.getVenBaseNoObjetoIva()));
            reporteTalonResumenVentas.setVenBase0((plgTO.getVenBase0() == null ? new BigDecimal("0.00") : plgTO.getVenBase0()));
            reporteTalonResumenVentas.setVenBaseImponible((plgTO.getVenBaseNoObjetoIva() == null ? new BigDecimal("0.00") : plgTO.getVenBaseImponible()));
            reporteTalonResumenVentas.setVenMontoIce((plgTO.getVenMontoIce() == null ? new BigDecimal("0.00") : plgTO.getVenMontoIce()));
            reporteTalonResumenVentas.setVenMotoIva((plgTO.getVenMotoIva() == null ? new BigDecimal("0.00") : plgTO.getVenMotoIva()));
            reporteTalonResumenVentas.setVenValorRetenidoIva((plgTO.getVenValorRetenidoIva() == null ? new BigDecimal("0.00") : plgTO.getVenValorRetenidoIva()));
            reporteTalonResumenVentas.setVenValorRetenidoRenta((plgTO.getVenValorRetenidoRenta() == null
                    ? new BigDecimal("0.00") : plgTO.getVenValorRetenidoRenta()));
            lista.add(reporteTalonResumenVentas);
        }
        return genericReporteService.generarReporte(modulo, "reportTalonResumenVentas.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteListadoRetencionesPorNumero(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AnxListaRetencionesTO> listaAnxListaRetencionesTO) throws Exception {
        List<ReporteAnxListaRetenciones> lista = new ArrayList<ReporteAnxListaRetenciones>();
        for (AnxListaRetencionesTO plgTO : listaAnxListaRetencionesTO) {
            ReporteAnxListaRetenciones reporteAnxListaRetencion = new ReporteAnxListaRetenciones();

            reporteAnxListaRetencion
                    .setRetProveedorId((plgTO.getRetProveedorId() == null ? "" : plgTO.getRetProveedorId()));
            reporteAnxListaRetencion.setRetProveedorNombre(
                    (plgTO.getRetProveedorNombre() == null ? "" : plgTO.getRetProveedorNombre()));
            reporteAnxListaRetencion.setRetDocumentoNombre(
                    (plgTO.getRetDocumentoNombre() == null ? "" : plgTO.getRetDocumentoNombre()));
            reporteAnxListaRetencion.setRetDocumentoNumero(
                    (plgTO.getRetDocumentoNumero() == null ? "" : plgTO.getRetDocumentoNumero()));
            reporteAnxListaRetencion
                    .setRetCompraFecha((plgTO.getRetCompraFecha() == null ? "" : plgTO.getRetCompraFecha()));
            reporteAnxListaRetencion
                    .setRetCompraBase0((plgTO.getRetCompraBase0() == null ? null : plgTO.getRetCompraBase0()));
            reporteAnxListaRetencion.setRetCompraBaseImponible(
                    (plgTO.getRetCompraBaseImponible() == null ? null : plgTO.getRetCompraBaseImponible()));
            reporteAnxListaRetencion
                    .setRetCompraMontoIva((plgTO.getRetCompraMontoIva() == null ? null : plgTO.getRetCompraMontoIva()));
            reporteAnxListaRetencion.setRetRetencionAutorizacion(
                    (plgTO.getRetRetencionAutorizacion() == null ? "" : plgTO.getRetRetencionAutorizacion()));
            reporteAnxListaRetencion.setRetRetencionNumero(
                    (plgTO.getRetRetencionNumero() == null ? "" : plgTO.getRetRetencionNumero()));
            reporteAnxListaRetencion
                    .setRetRetencionFecha((plgTO.getRetRetencionFecha() == null ? "" : plgTO.getRetRetencionFecha()));
            reporteAnxListaRetencion.setRetValorRetenidoIr(
                    (plgTO.getRetValorRetenidoIr() == null ? null : plgTO.getRetValorRetenidoIr()));
            reporteAnxListaRetencion.setRetValorRetenidoIva(
                    (plgTO.getRetValorRetenidoIva() == null ? null : plgTO.getRetValorRetenidoIva()));
            reporteAnxListaRetencion
                    .setRetTotalRetenido((plgTO.getRetTotalRetenido() == null ? null : plgTO.getRetTotalRetenido()));

            lista.add(reporteAnxListaRetencion);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoRetencionesComprasPorNumero.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteListadoRetencionesVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta, List<AnxListadoRetencionesVentasTO> anxListadoRetencionesVentasTOs)
            throws Exception {
        List<ReporteListadoRetencionesVentas> lista = new ArrayList<ReporteListadoRetencionesVentas>();
        for (AnxListadoRetencionesVentasTO plgTO : anxListadoRetencionesVentasTOs) {
            ReporteListadoRetencionesVentas reporteListadoRetencionesVentas = new ReporteListadoRetencionesVentas();

            reporteListadoRetencionesVentas.setDesde(fechaDesde);
            reporteListadoRetencionesVentas.setHasta(fechaHasta);
            reporteListadoRetencionesVentas.setVenFecha((plgTO.getVenFecha() == null ? "" : plgTO.getVenFecha()));
            reporteListadoRetencionesVentas
                    .setVenIdentificacion((plgTO.getVenIdentificacion() == null ? "" : plgTO.getVenIdentificacion()));
            reporteListadoRetencionesVentas.setVenNombre((plgTO.getVenNombre() == null ? "" : plgTO.getVenNombre()));
            reporteListadoRetencionesVentas.setVenComprobanteTipo(
                    (plgTO.getVenComprobanteTipo() == null ? "" : plgTO.getVenComprobanteTipo()));
            reporteListadoRetencionesVentas.setVenComprobanteNumero(
                    (plgTO.getVenComprobanteNumero() == null ? "" : plgTO.getVenComprobanteNumero()));
            reporteListadoRetencionesVentas.setVenBaseNoObjetoIva(
                    (plgTO.getVenBaseNoObjetoIva() == null ? null : plgTO.getVenBaseNoObjetoIva()));
            reporteListadoRetencionesVentas.setVenBase0((plgTO.getVenBase0() == null ? null : plgTO.getVenBase0()));
            reporteListadoRetencionesVentas
                    .setVenBaseImponible((plgTO.getVenBaseImponible() == null ? new BigDecimal("0.00") : plgTO.getVenBaseImponible()));
            reporteListadoRetencionesVentas.
                    setVenMontoIce((plgTO.getVenMontoIce() == null ? new BigDecimal("0.00") : plgTO.getVenMontoIce()));
            reporteListadoRetencionesVentas
                    .setVenMontoIva((plgTO.getVenMontoIva() == null ? new BigDecimal("0.00") : plgTO.getVenMontoIva()));
            reporteListadoRetencionesVentas.setVenValorRetenidoIva(
                    (plgTO.getVenValorRetenidoIva() == null ? new BigDecimal("0.00") : plgTO.getVenValorRetenidoIva()));
            reporteListadoRetencionesVentas.setVenValorRetenidoRenta(
                    (plgTO.getVenValorRetenidoRenta() == null ? new BigDecimal("0.00") : plgTO.getVenValorRetenidoRenta()));

            lista.add(reporteListadoRetencionesVentas);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoRetencionesVentas.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteListadoDevolucionIva(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<AnxFunListadoDevolucionIvaTO> anxFunListadoDevolucionIvaTOs) throws Exception {
        List<ReporteAnxFunListadoDevolucionIva> lista = new ArrayList<ReporteAnxFunListadoDevolucionIva>();
        for (AnxFunListadoDevolucionIvaTO plgTO : anxFunListadoDevolucionIvaTOs) {
            ReporteAnxFunListadoDevolucionIva reporteAnxFunListadoDevolucionIva = new ReporteAnxFunListadoDevolucionIva();

            reporteAnxFunListadoDevolucionIva.setFechaDesde(fechaDesde);
            reporteAnxFunListadoDevolucionIva.setFechaHasta(fechaHasta);

            reporteAnxFunListadoDevolucionIva.setActSustentoTributario(
                    (plgTO.getActSustentoTributario() == null ? "" : plgTO.getActSustentoTributario()));
            reporteAnxFunListadoDevolucionIva.setActFecha((plgTO.getActFecha() == null ? "" : plgTO.getActFecha()));
            reporteAnxFunListadoDevolucionIva.setActProveedorNombre(
                    (plgTO.getActProveedorNombre() == null ? "" : plgTO.getActProveedorNombre()));
            reporteAnxFunListadoDevolucionIva.setActProveedorIdTipo(
                    (plgTO.getActProveedorIdTipo() == null ? null : plgTO.getActProveedorIdTipo()));
            reporteAnxFunListadoDevolucionIva.setActProveedorIdNumero(
                    (plgTO.getActProveedorIdNumero() == null ? "" : plgTO.getActProveedorIdNumero()));
            reporteAnxFunListadoDevolucionIva
                    .setActDocumentoTipo((plgTO.getActDocumentoTipo() == null ? "" : plgTO.getActDocumentoTipo()));
            reporteAnxFunListadoDevolucionIva.setActDocumentoNumero(
                    (plgTO.getActDocumentoNumero() == null ? "" : plgTO.getActDocumentoNumero()));
            reporteAnxFunListadoDevolucionIva
                    .setActAutorizacion((plgTO.getActAutorizacion() == null ? "" : plgTO.getActAutorizacion()));
            reporteAnxFunListadoDevolucionIva.setActBase0((plgTO.getActBase0() == null ? null : plgTO.getActBase0()));
            reporteAnxFunListadoDevolucionIva
                    .setActBaseImponible((plgTO.getActBaseImponible() == null ? null : plgTO.getActBaseImponible()));
            reporteAnxFunListadoDevolucionIva.setActIva((plgTO.getActIva() == null ? null : plgTO.getActIva()));
            reporteAnxFunListadoDevolucionIva.setActTotal((plgTO.getActTotal() == null ? null : plgTO.getActTotal()));
            reporteAnxFunListadoDevolucionIva.setActClaveAcceso(plgTO.getActClaveAccesoRetencion() == null ? null : plgTO.getActClaveAccesoRetencion());
            reporteAnxFunListadoDevolucionIva.setActNecesitaSoporte(plgTO.getActNecesitaSoporte() == null ? null : plgTO.getActNecesitaSoporte());
            lista.add(reporteAnxFunListadoDevolucionIva);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoDevolucionIva.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteListadoAnxListaVentaExportacionTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<AnxListaVentaExportacionTO> anxListaVentaExportacionTO) throws Exception {
        List<ReporteAnxListaVentaExportacionTO> lista = new ArrayList<ReporteAnxListaVentaExportacionTO>();
        for (AnxListaVentaExportacionTO plgTOventa : anxListaVentaExportacionTO) {
            ReporteAnxListaVentaExportacionTO itemReporte = new ReporteAnxListaVentaExportacionTO();
            itemReporte.setFechaDesde(fechaDesde);
            itemReporte.setFechaHasta(fechaHasta);
            itemReporte.setExpClienteIdNumero(plgTOventa.getExpClienteIdNumero() == null ? "" : plgTOventa.getExpClienteIdNumero());
            itemReporte.setExpClienteIdTipo(plgTOventa.getExpClienteIdTipo() == null ? "" : plgTOventa.getExpClienteIdTipo());
            itemReporte.setExpClienteRazonSocial(plgTOventa.getExpClienteRazonSocial() == null ? "" : plgTOventa.getExpClienteRazonSocial());
            itemReporte.setExpClienteRelacionado(plgTOventa.getExpClienteRelacionado() == null ? "" : plgTOventa.getExpClienteRelacionado());
            itemReporte.setExpFechaExportacion(plgTOventa.getExpFechaExportacion() == null ? "" : plgTOventa.getExpFechaExportacion());
            itemReporte.setExpObservaciones(plgTOventa.getExpObservaciones() == null ? "" : plgTOventa.getExpObservaciones());
            itemReporte.setExpPaisEfectuaExportacion(plgTOventa.getExpPaisEfectuaExportacion() == null ? "" : plgTOventa.getExpPaisEfectuaExportacion());
            itemReporte.setExpParaisoFiscal(plgTOventa.getExpParaisoFiscal() == null ? "" : plgTOventa.getExpParaisoFiscal());
            itemReporte.setExpRefrendoAnio(plgTOventa.getExpRefrendoAnio() == null ? "" : plgTOventa.getExpRefrendoAnio());
            itemReporte.setExpRefrendoCorrelativo(plgTOventa.getExpRefrendoCorrelativo() == null ? "" : plgTOventa.getExpRefrendoCorrelativo());
            itemReporte.setExpRefrendoDistrito(plgTOventa.getExpRefrendoDistrito() == null ? "" : plgTOventa.getExpRefrendoDistrito());
            itemReporte.setExpRefrendoDocumentoTransporte(plgTOventa.getExpRefrendoDocumentoTransporte() == null ? "" : plgTOventa.getExpRefrendoDocumentoTransporte());
            itemReporte.setExpRefrendoRegimen(plgTOventa.getExpRefrendoRegimen() == null ? "" : plgTOventa.getExpRefrendoRegimen());
            itemReporte.setExpRegimenFiscalPreferente(plgTOventa.getExpRegimenFiscalPreferente() == null ? "" : plgTOventa.getExpRegimenFiscalPreferente());
            itemReporte.setExpRegimenGeneral(plgTOventa.getExpRegimenGeneral() == null ? "" : plgTOventa.getExpRegimenGeneral());
            itemReporte.setExpTipoExportacion(plgTOventa.getExpTipoExportacion() == null ? "" : plgTOventa.getExpTipoExportacion());
            itemReporte.setExpTipoIngresoExterior(plgTOventa.getExpTipoIngresoExterior() == null ? "" : plgTOventa.getExpTipoIngresoExterior());
            itemReporte.setExpTipoRegimenFiscal(plgTOventa.getExpTipoRegimenFiscal() == null ? "" : plgTOventa.getExpTipoRegimenFiscal());
            itemReporte.setExpValorFobLocal(plgTOventa.getExpValorFobLocal() == null ? BigDecimal.ZERO : plgTOventa.getExpValorFobLocal());
            itemReporte.setExpValorFobExterior(plgTOventa.getExpValorFobExterior() == null ? BigDecimal.ZERO : plgTOventa.getExpValorFobExterior());
            itemReporte.setExpImpuestoPagadoExterior(plgTOventa.getExpImpuestoPagadoExterior() == null ? BigDecimal.ZERO : plgTOventa.getExpImpuestoPagadoExterior());
            itemReporte.setVtaDocumentoAutorizacion(plgTOventa.getVtaDocumentoAutorizacion() == null ? "" : plgTOventa.getVtaDocumentoAutorizacion());
            itemReporte.setVtaDocumentoNumero(plgTOventa.getVtaDocumentoNumero() == null ? "" : plgTOventa.getVtaDocumentoNumero());
            lista.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoAnxListaVentaExportacionTO.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);

    }

    @Override
    public byte[] generarReporteListadoDevolucionIvaVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<AnxFunListadoDevolucionIvaVentasTO> anxFunListadoDevolucionIvaVentasTO) throws Exception {
        List<ReporteANexoListaDevolucionIVAventas> lista = new ArrayList<ReporteANexoListaDevolucionIVAventas>();
        for (AnxFunListadoDevolucionIvaVentasTO plgTOventa : anxFunListadoDevolucionIvaVentasTO) {
            ReporteANexoListaDevolucionIVAventas itemReporte = new ReporteANexoListaDevolucionIVAventas();
            itemReporte.setFechaDesde(fechaDesde);
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setVenAutorizacionElectronica(plgTOventa.getVenAutorizacionElectronica() == null ? "" : plgTOventa.getVenAutorizacionElectronica());
            itemReporte.setVenAutorizacionFisica(plgTOventa.getVenAutorizacionFisica() == null ? "" : plgTOventa.getVenAutorizacionFisica());
            itemReporte.setVenComprobanteSecuencia(plgTOventa.getVenComprobanteSecuencia() == null ? "" : plgTOventa.getVenComprobanteSecuencia());
            itemReporte.setVenComprobanteSerie(plgTOventa.getVenComprobanteSerie() == null ? "" : plgTOventa.getVenComprobanteSerie());
            itemReporte.setVenFecha(plgTOventa.getVenFecha() == null ? "" : plgTOventa.getVenFecha());
            itemReporte.setVenComprobanteTipo(plgTOventa.getVenComprobanteTipo() == null ? "" : plgTOventa.getVenComprobanteTipo());
            itemReporte.setVenIdentificacion(plgTOventa.getVenIdentificacion() == null ? "" : plgTOventa.getVenIdentificacion());
            itemReporte.setVenNombre(plgTOventa.getVenNombre() == null ? "" : plgTOventa.getVenNombre());
            itemReporte.setVenTotal(plgTOventa.getVenTotal() == null ? BigDecimal.ZERO : plgTOventa.getVenTotal());
            lista.add(itemReporte);

        }
        return genericReporteService.generarReporte(modulo, "reportListadoDevolucionIvaVentas.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);

    }

    @Override
    public byte[] generarReporteRegistroDatosCrediticios(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, List<AnxFunRegistroDatosCrediticiosTO> listaAnxFunRegistroDatosCrediticiosTO) throws Exception {
        List<AnxFunRegistroDatosCrediticiosTO> lista = new ArrayList<>();
        lista.add(new AnxFunRegistroDatosCrediticiosTO());
        return genericReporteService.generarReporte(modulo, "reportAnxRegistroDatosCrediticios.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), listaAnxFunRegistroDatosCrediticiosTO);
    }
    //cambios en ell reporte excel

    @Override
    public Map<String, Object> generarReporteListadoDevolucionIva(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxFunListadoDevolucionIvaTO> anxFunListadoDevolucionIvaTOs, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado devolución IVA compras");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SSustento Tributario" + "¬" + "SNumero ID" + "¬" + "SNombre" + "¬" + "SFecha" + "¬" + "SSerie" + "¬"
                    + "SSecuencia" + "¬" + "SAutorización Física" + "¬" + "SAutorización Electrónica" + "¬" + "SBase 0" + "¬" + "SBase Imp" + "¬" + "SICE" + "¬" + "SIVA" + "¬" + "STotal" + "¬" + "SClave acceso ret." + "¬" + "SNec. sop.");
            for (AnxFunListadoDevolucionIvaTO anxDevolucionIvaTOs : anxFunListadoDevolucionIvaTOs) {
                listaCuerpo.add((anxDevolucionIvaTOs.getActSustentoTributario() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActSustentoTributario())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActProveedorIdNumero() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActProveedorIdNumero())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActProveedorNombre() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActProveedorNombre())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActFecha() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActFecha())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActDocumentoNumero() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActDocumentoNumero().substring(0, 7))
                        + "¬"
                        + (anxDevolucionIvaTOs.getActDocumentoNumero() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActDocumentoNumero().substring(8))
                        + "¬"
                        + (anxDevolucionIvaTOs.getActAutorizacion() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActAutorizacion())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActAutorizacionElectronica() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActAutorizacionElectronica())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActBase0() == null ? "B"
                        : "D" + anxDevolucionIvaTOs.getActBase0())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActBaseImponible() == null ? "B"
                        : "D" + anxDevolucionIvaTOs.getActBaseImponible())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActIce() == null ? "B"
                        : "D" + anxDevolucionIvaTOs.getActIce())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActIva() == null ? "B"
                        : "D" + anxDevolucionIvaTOs.getActIva())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActTotal() == null ? "B"
                        : "D" + anxDevolucionIvaTOs.getActTotal())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActClaveAccesoRetencion() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActClaveAccesoRetencion())
                        + "¬"
                        + (anxDevolucionIvaTOs.getActNecesitaSoporte() == null ? "B"
                        : "S" + anxDevolucionIvaTOs.getActNecesitaSoporte())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteListadoDevolucionIvaVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxFunListadoDevolucionIvaVentasTO> anxFunListadoDevolucionIvaVentasTO, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado devolución IVA ventas");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SNº Identificación" + "¬" + "SNombre" + "¬" + "SComprobante" + "¬" + "SSerie" + "¬" + "SFecha" + "¬" + "SSecuencia" + "¬"
                    + "SAut. física" + "¬" + "SAut. electrónica" + "¬" + "STotal");
            for (AnxFunListadoDevolucionIvaVentasTO anxDevolucionIvaVentasTOs : anxFunListadoDevolucionIvaVentasTO) {
                listaCuerpo.add((anxDevolucionIvaVentasTOs.getVenIdentificacion() == null ? "B"
                        : "S" + anxDevolucionIvaVentasTOs.getVenIdentificacion())
                        + "¬"
                        + (anxDevolucionIvaVentasTOs.getVenNombre() == null ? "B"
                        : "S" + anxDevolucionIvaVentasTOs.getVenNombre())
                        + "¬"
                        + (anxDevolucionIvaVentasTOs.getVenComprobanteTipo() == null ? "B"
                        : "S" + anxDevolucionIvaVentasTOs.getVenComprobanteTipo())
                        + "¬"
                        + (anxDevolucionIvaVentasTOs.getVenComprobanteSerie() == null ? "B"
                        : "S" + anxDevolucionIvaVentasTOs.getVenComprobanteSerie())
                        + "¬"
                        + (anxDevolucionIvaVentasTOs.getVenFecha() == null ? "B"
                        : "S" + anxDevolucionIvaVentasTOs.getVenFecha())
                        + "¬"
                        + (anxDevolucionIvaVentasTOs.getVenComprobanteSecuencia() == null ? "B"
                        : "S" + anxDevolucionIvaVentasTOs.getVenComprobanteSecuencia())
                        + "¬"
                        + (anxDevolucionIvaVentasTOs.getVenAutorizacionFisica() == null ? "B"
                        : "S" + anxDevolucionIvaVentasTOs.getVenAutorizacionFisica())
                        + "¬"
                        + (anxDevolucionIvaVentasTOs.getVenAutorizacionElectronica() == null ? "B"
                        : "S" + anxDevolucionIvaVentasTOs.getVenAutorizacionElectronica())
                        + "¬"
                        + (anxDevolucionIvaVentasTOs.getVenTotal() == null ? "B"
                        : "D" + anxDevolucionIvaVentasTOs.getVenTotal()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteTalonResumen(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxTalonResumenTO> listaAnxTalonResumenTO, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado talón resumen compras");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SConcepto" + "¬" + "SCantidad" + "¬" + "SB. imp." + "¬" + "SPorcentaje" + "¬" + "SV. ret.");
            for (AnxTalonResumenTO anxTalonResumenTOs : listaAnxTalonResumenTO) {
                listaCuerpo.add((anxTalonResumenTOs.getRetConcepto() == null ? "B"
                        : "S" + anxTalonResumenTOs.getRetConcepto())
                        + "¬"
                        + (anxTalonResumenTOs.getRetCantidad() == null ? "B"
                        : "D" + anxTalonResumenTOs.getRetCantidad())
                        + "¬"
                        + (anxTalonResumenTOs.getRetBaseImponible() == null ? "B"
                        : "D" + anxTalonResumenTOs.getRetBaseImponible())
                        + "¬"
                        + (anxTalonResumenTOs.getRetPorcentaje() == null ? "B"
                        : "D" + anxTalonResumenTOs.getRetPorcentaje())
                        + "¬"
                        + (anxTalonResumenTOs.getRetValorRetenido() == null ? "B"
                        : "D" + anxTalonResumenTOs.getRetValorRetenido()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteFormulario103(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxFormulario103TO> lista, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SFormulario 103");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SConcepto" + "¬" + "SCodigo Retención" + "¬" + "SB. imp." + "¬" + "SCodigo Formulario 103" + "¬" + "SV. ret.");
            for (AnxFormulario103TO anxTalonResumenTOs : lista) {
                listaCuerpo.add((anxTalonResumenTOs.getRetConcepto() == null ? "B"
                        : "S" + anxTalonResumenTOs.getRetConcepto())
                        + "¬"
                        + (anxTalonResumenTOs.getRetCodigoRetencion() == null ? "B"
                        : "S" + anxTalonResumenTOs.getRetCodigoRetencion())
                        + "¬"
                        + (anxTalonResumenTOs.getRetBaseImponible() == null ? "B"
                        : "D" + anxTalonResumenTOs.getRetBaseImponible())
                        + "¬"
                        + (anxTalonResumenTOs.getRetCodigoFormulario103() == null ? "B"
                        : "S" + anxTalonResumenTOs.getRetCodigoFormulario103())
                        + "¬"
                        + (anxTalonResumenTOs.getRetValorRetenido() == null ? "B"
                        : "D" + anxTalonResumenTOs.getRetValorRetenido()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteFormulario104(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxFormulario104TO> lista, String desde, String hasta) throws Exception {
        try {
            Map<String, List<AnxFormulario104TO>> agrupado = lista.stream().collect(Collectors.groupingBy(AnxFormulario104TO::getF104Seccion));
            List<AnxFormulario104TO> listaVentas = agrupado.get("VENTAS");
            List<AnxFormulario104TO> listaCompras = agrupado.get("COMPRAS");
            List<AnxFormulario104TO> listaRetenciones = agrupado.get("RETENCIONES");
            List<AnxFormulario104TO> listaResumenImpositivo = agrupado.get("RESUMEN IMPOSITIVO");
            List<AnxFormulario104TO> listaTotales = agrupado.get("TOTALES");
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SFormulario 104");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            if (listaVentas != null && !listaVentas.isEmpty()) {
                listaCuerpo.add("SVENTAS¬");
                listaCuerpo.add("SDescripcion" + "¬" + "SCodigo Valor Bruto" + "¬" + "SValor bruto" + "¬" + "SCodigo Valor Neto" + "¬" + "SValor Neto" + "¬" + "SCodigo impuesto generado" + "¬" + "SValor impuesto generado");
                for (AnxFormulario104TO anxTalonResumenTOs : listaVentas) {
                    listaCuerpo.add((anxTalonResumenTOs.getF104Descripcion() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104Descripcion())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ValorBrutoCodigo() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104ValorBrutoCodigo())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ValorBrutoValor() == null ? "B"
                            : "D" + anxTalonResumenTOs.getF104ValorBrutoValor())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ValorNetoCodigo() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104ValorNetoCodigo())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ValorNetoValor() == null ? "B"
                            : "D" + anxTalonResumenTOs.getF104ValorNetoValor())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoValor() == null ? "B"
                            : "D" + anxTalonResumenTOs.getF104ImpuestoGeneradoValor()));
                }
                listaCuerpo.add("S¬");
                listaCuerpo.add("S¬");
            }
            if (listaCompras != null && !listaCompras.isEmpty()) {
                listaCuerpo.add("SCOMPRAS¬");
                listaCuerpo.add("SDescripcion" + "¬" + "SCodigo Valor Bruto" + "¬" + "SValor bruto" + "¬" + "SCodigo Valor Neto" + "¬" + "SValor Neto" + "¬" + "SCodigo impuesto generado" + "¬" + "SValor impuesto generado");
                for (AnxFormulario104TO anxTalonResumenTOs : listaCompras) {
                    listaCuerpo.add((anxTalonResumenTOs.getF104Descripcion() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104Descripcion())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ValorBrutoCodigo() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104ValorBrutoCodigo())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ValorBrutoValor() == null ? "B"
                            : "D" + anxTalonResumenTOs.getF104ValorBrutoValor())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ValorNetoCodigo() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104ValorNetoCodigo())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ValorNetoValor() == null ? "B"
                            : "D" + anxTalonResumenTOs.getF104ValorNetoValor())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoValor() == null ? "B"
                            : "D" + anxTalonResumenTOs.getF104ImpuestoGeneradoValor()));
                }
                listaCuerpo.add("S¬");
                listaCuerpo.add("S¬");
            }
            if (listaRetenciones != null && !listaRetenciones.isEmpty()) {
                listaCuerpo.add("SRESUMEN IMPOSITIVO");
                listaCuerpo.add("SDescripcion" + "¬" + "SCodigo impuesto generado" + "¬" + "SValor impuesto generado");
                for (AnxFormulario104TO anxTalonResumenTOs : listaResumenImpositivo) {
                    listaCuerpo.add((anxTalonResumenTOs.getF104Descripcion() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104Descripcion())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoValor() == null ? "B"
                            : "D" + anxTalonResumenTOs.getF104ImpuestoGeneradoValor()));
                }
                listaCuerpo.add("S¬");
                listaCuerpo.add("S¬");
            }
            if (listaRetenciones != null && !listaRetenciones.isEmpty()) {
                listaCuerpo.add("SRETENCIONES¬");
                listaCuerpo.add("SDescripcion" + "¬" + "SCodigo impuesto generado" + "¬" + "SValor impuesto generado");
                for (AnxFormulario104TO anxTalonResumenTOs : listaRetenciones) {
                    listaCuerpo.add((anxTalonResumenTOs.getF104Descripcion() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104Descripcion())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoValor() == null ? "B"
                            : "D" + anxTalonResumenTOs.getF104ImpuestoGeneradoValor()));
                }
                listaCuerpo.add("S¬");
                listaCuerpo.add("S¬");
            }
            if (listaRetenciones != null && !listaRetenciones.isEmpty()) {
                listaCuerpo.add("STOTALES");
                listaCuerpo.add("SDescripcion" + "¬" + "SCodigo impuesto generado" + "¬" + "SValor impuesto generado");
                for (AnxFormulario104TO anxTalonResumenTOs : listaTotales) {
                    listaCuerpo.add((anxTalonResumenTOs.getF104Descripcion() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104Descripcion())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo() == null ? "B"
                            : "S" + anxTalonResumenTOs.getF104ImpuestoGeneradoCodigo())
                            + "¬"
                            + (anxTalonResumenTOs.getF104ImpuestoGeneradoValor() == null ? "B"
                            : "D" + anxTalonResumenTOs.getF104ImpuestoGeneradoValor()));
                }
                listaCuerpo.add("S¬");
                listaCuerpo.add("S¬");
            }

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteTalonResumenVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxTalonResumenVentaTO> listaAnxTalonResumenVentaTO, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado talón resumen ventas");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SComprobante" + "¬" + "SEstablecimiento" + "¬" + "SP. emisión" + "¬" + "SCantidad de comprobantes" + "¬" + "SB. no objeta iva" + "¬" + "SB. 0"
                    + "¬" + "SB. imponible" + "¬" + "SMonto ICE" + "¬" + "SMonto IVA" + "¬" + "SV. ret. iva" + "¬" + "SV. ret. renta");
            for (AnxTalonResumenVentaTO anxTalonResumenVentasTOs : listaAnxTalonResumenVentaTO) {
                listaCuerpo.add((anxTalonResumenVentasTOs.getVenComprobante() == null ? "B"
                        : "S" + anxTalonResumenVentasTOs.getVenComprobante())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenEstablecimiento() == null ? "B"
                        : "S" + anxTalonResumenVentasTOs.getVenEstablecimiento())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenPuntoEmision() == null ? "B"
                        : "S" + anxTalonResumenVentasTOs.getVenPuntoEmision())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenCantidadComprobantes() == null ? "B"
                        : "I" + anxTalonResumenVentasTOs.getVenCantidadComprobantes())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenBaseNoObjetoIva() == null ? "B"
                        : "D" + anxTalonResumenVentasTOs.getVenBaseNoObjetoIva())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenBase0() == null ? "B"
                        : "D" + anxTalonResumenVentasTOs.getVenBase0())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenBaseImponible() == null ? "B"
                        : "D" + anxTalonResumenVentasTOs.getVenBaseImponible())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenMontoIce() == null ? "B"
                        : "D" + anxTalonResumenVentasTOs.getVenMontoIce())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenMotoIva() == null ? "B"
                        : "D" + anxTalonResumenVentasTOs.getVenMotoIva())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenValorRetenidoIva() == null ? "B"
                        : "D" + anxTalonResumenVentasTOs.getVenValorRetenidoIva())
                        + "¬"
                        + (anxTalonResumenVentasTOs.getVenValorRetenidoRenta() == null ? "B"
                        : "D" + anxTalonResumenVentasTOs.getVenValorRetenidoRenta())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarAnxRespuestasErroresPorNotasDeDebitoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRespuestasErroresPorNotasDeDebitoTO> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SEl anexo transaccional se generará sin la información correspondiente a las retenciones en notas de débito. Por favor adicionar de acuerdo al siguiente listado:");
            listaCabecera.add("S");
            listaCuerpo.add("SN. Identificación" + "¬" + "SProveedor" + "¬" + "SDocumento" + "¬" + "SRetención" + "¬" + "SFecha emisión" + "¬" + "SSustento tributario" + "¬" + "SDocumento modificado");
            for (AnxRespuestasErroresPorNotasDeDebitoTO nota : lista) {
                listaCuerpo.add((nota.getProvIdentificacion() == null ? "B"
                        : "S" + nota.getProvIdentificacion())
                        + "¬"
                        + (nota.getProvNombre() == null ? "B"
                        : "S" + nota.getProvNombre())
                        + "¬"
                        + (nota.getDocumento() == null ? "B"
                        : "S" + nota.getDocumento())
                        + "¬"
                        + (nota.getRetencion() == null ? "B"
                        : "S" + nota.getRetencion())
                        + "¬"
                        + (nota.getFechaEmision() == null ? "B"
                        : "S" + nota.getFechaEmision())
                        + "¬"
                        + (nota.getSustentoTributario() == null ? "B"
                        : "S" + nota.getSustentoTributario())
                        + "¬"
                        + (nota.getDocumentoModificado() == null ? "B"
                        : "S" + nota.getDocumentoModificado())
                );
            }
            listaCuerpo.add("S");
            listaCuerpo.add("SEste proceso se debe realizar en la plataforma del DIMM Anexos.");
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteConsolidadoRetencionesVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaConsolidadoRetencionesVentasTO> listaAnxConsolidadoRetencionesVentasTO, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SRetenciones en ventas (consolidado por clientes)");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("STransacción" + "¬" + "SIdentificación" + "¬" + "SComprobante tipo" + "¬" + "SNúmero retenciones" + "¬" + "SBase no objeto iva"
                    + "¬" + "SBase 0" + "¬" + "SBase Imponible" + "¬" + "SMonto ice" + "¬" + "SMonto iva" + "¬" + "SValor retenido iva" + "¬" + "SValor retenido renta");
            for (AnxListaConsolidadoRetencionesVentasTO anxListaConsolidadoRetencionesVentasTO : listaAnxConsolidadoRetencionesVentasTO) {

                listaCuerpo.add((anxListaConsolidadoRetencionesVentasTO.getRvcTransaccion() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getRvcTransaccion())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getRvcIdentificacion() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getRvcIdentificacion())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getRvcComprobanteTipo() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getRvcComprobanteTipo())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getRvcNumeroRetenciones() == null ? "B"
                        : "I" + anxListaConsolidadoRetencionesVentasTO.getRvcNumeroRetenciones().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getRvcBaseNoObjetoIva() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getRvcBaseNoObjetoIva().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getRvcBase0() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getRvcBase0().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenBaseImponible() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenBaseImponible().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenMontoIce() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenMontoIce().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenMontoIva() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenMontoIva().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoIva() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoIva().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoRenta() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoRenta().toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteListadoRetencionesVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListadoRetencionesVentasTO> anxListadoRetencionesVentasTOs, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado Retenciones en ventas (listado simple)");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SFecha" + "¬" + "SIdentificación" + "¬" + "SNombre" + "¬" + "SComprobante Numero" + "¬"
                    + "SComprobante Tipo" + "¬" + "SAutorización" + "¬" + "SBase No Objeto Iva" + "¬" + "SBase 0" + "¬" + "SBase Imponible" + "¬" + "SMonto ICE"
                    + "¬" + "SMonto Iva" + "¬" + "SValor Retenido Iva" + "¬" + "SValor Retenido Renta" + "¬" + "SNúmero retención" + "¬" + "SNúmero Autorización"
                    + "¬" + "SFecha Emisión");
            for (AnxListadoRetencionesVentasTO anxListaConsolidadoRetencionesVentasTO : anxListadoRetencionesVentasTOs) {

                listaCuerpo.add((anxListaConsolidadoRetencionesVentasTO.getVenFecha() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getVenFecha())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenIdentificacion() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getVenIdentificacion())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenNombre() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getVenNombre())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenComprobanteNumero() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getVenComprobanteNumero())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenComprobanteTipo() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getVenComprobanteTipo())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenAutorizacion() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getVenAutorizacion())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenBaseNoObjetoIva() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenBaseNoObjetoIva().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenBase0() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenBase0().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenBaseImponible() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenBaseImponible().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenMontoIce() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenMontoIce().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenMontoIva() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenMontoIva().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoIva() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoIva().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoRenta() == null ? "B"
                        : "D" + anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoRenta().toString())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenRetencion() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getVenRetencion())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenRetencionAutorizacion() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getVenRetencionAutorizacion())
                        + "¬"
                        + (anxListaConsolidadoRetencionesVentasTO.getVenRetencionFechaEmision() == null ? "B"
                        : "S" + anxListaConsolidadoRetencionesVentasTO.getVenRetencionFechaEmision())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarRetencionesEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListadoCompraElectronicaTO> listado, String desde, String hasta) throws Exception {

        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SRetenciones Electrónicas Emitidas");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "SMotivo" + "¬" + "SNumero" + "¬" + "SRetencion Numero" + "¬" + "SRetencion Fecha" + "¬" + "SProveedor" + "¬" + "SAutorización" + "¬" + "SFecha y Hora de Autorización"
                    + "SEmail" + "SEnviado" + "SEntregado" + "SLeido" + "SRebotado");

            for (AnxListadoCompraElectronicaTO anxListadoCompraElectronicaTO : listado) {
                listaCuerpo.add((anxListadoCompraElectronicaTO.getCompPeriodo() == null ? "B"
                        : "S" + anxListadoCompraElectronicaTO.getCompPeriodo())
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getCompMotivo() == null ? "B"
                        : "S" + anxListadoCompraElectronicaTO.getCompMotivo())
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getCompNumero() == null ? "B"
                        : "S" + anxListadoCompraElectronicaTO.getCompNumero())
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getCompRetencionNumero() == null ? "B"
                        : "S" + anxListadoCompraElectronicaTO.getCompRetencionNumero())
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getCompRetencionFechaEmision() == null ? "B"
                        : "S" + anxListadoCompraElectronicaTO.getCompRetencionFechaEmision())
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getCompProveedorRazonSocial() == null ? "B"
                        : "S" + anxListadoCompraElectronicaTO.getCompProveedorRazonSocial())
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getCompAutorizacionNumero() == null ? "B"
                        : "S" + anxListadoCompraElectronicaTO.getCompAutorizacionNumero())
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getCompAutorizacionFecha() == null ? "B"
                        : "S" + anxListadoCompraElectronicaTO.getCompAutorizacionFecha())
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getEmail() == null ? "B" : "SOK")
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getEmailEnviado() == null ? "B" : "SOK")
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getEmailEntregado() == null ? "B" : "SOK")
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getEmailLeido() == null ? "B" : "SOK")
                        + "¬"
                        + (anxListadoCompraElectronicaTO.getEmailRebotado() == null ? "B" : "SOK")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteRetencionesRentaComprasTD(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaRetencionesRentaTO> listaAnxConsolidadoRetencionesRentaTO, String fechaDesde, String fechaHasta, String orden) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            String nombreReporteSustento = "Retenciones renta compras (sustento tributario)";
            String nombreReporteProveedor = "Retenciones renta compras (tipo proveedor)";
            String nombreReporteTipoDocumento = "Retenciones renta compra (tipo documento)";
            String nombreReporteTipoCocepto = "Retenciones renta compra (concepto)";
            listaCabecera.add("S" + nombreEmpresa);
            switch (orden) {
                case "SUSTENTO":
                    listaCabecera.add("S" + nombreReporteSustento);
                    break;
                case "TIPO DOCUMENTO":
                    listaCabecera.add("S" + nombreReporteTipoDocumento);
                    break;
                case "TIPO PROVEEDOR":
                    listaCabecera.add("S" + nombreReporteProveedor);
                    break;
                case "CONCEPTO":
                    listaCabecera.add("S" + nombreReporteTipoCocepto);
                    break;
                default:
            }

            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SSusntento tributario" + "¬" + "SConcepto" + "¬" + "SIdentificación" + "¬" + "SProveedor" + "¬"
                    + "SDoc. nombre" + "¬" + "SDoc. número" + "¬" + "SDoc. autorización" + "¬" + "SFecha compra" + "¬" + "SBase 0"
                    + "¬" + "SBase imponible" + "¬" + "SRetención autorización" + "¬" + "SRetención número" + "¬" + "SRetención fecha" + "¬" + "SV. R. IR"
                    + "¬" + "SContable");
            for (AnxListaRetencionesRentaTO anxListaRetencionesRentaTO : listaAnxConsolidadoRetencionesRentaTO) {

                listaCuerpo.add((anxListaRetencionesRentaTO.getRetSustentotributario() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetSustentotributario())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetConcepto() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetConcepto())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetProveedorId() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetProveedorId())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetProveedorNombre() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetProveedorNombre())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetDocumentoNombre() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetDocumentoNombre())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetDocumentoNumero() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetDocumentoNumero())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetDocumentoAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetDocumentoAutorizacion())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetCompraFecha() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetCompraFecha())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetCompraBase0() == null ? "B"
                        : "D" + anxListaRetencionesRentaTO.getRetCompraBase0())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetComprabaseImponible() == null ? "B"
                        : "D" + anxListaRetencionesRentaTO.getRetComprabaseImponible())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetRetencionAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetRetencionAutorizacion())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetRetencionNumero() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetRetencionNumero())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetRetencionFecha() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetRetencionFecha())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetValorRetenidoIr() == null ? "B"
                        : "D" + anxListaRetencionesRentaTO.getRetValorRetenidoIr())
                        + "¬"
                        + (anxListaRetencionesRentaTO.getRetLlavecontable() == null ? "B"
                        : "S" + anxListaRetencionesRentaTO.getRetLlavecontable()));

            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteRetencionesRentaComprasTD(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta,
            List<AnxListaRetencionesRentaTO> listaAnxConsolidadoRetencionesRentaTO) throws Exception {

        List<AnxListaRetencionesRentaComprasTipoDocumentoTO> lista = new ArrayList<AnxListaRetencionesRentaComprasTipoDocumentoTO>();
        for (AnxListaRetencionesRentaTO plgTO : listaAnxConsolidadoRetencionesRentaTO) {
            AnxListaRetencionesRentaComprasTipoDocumentoTO reporteAnxRetencionesRenta = new AnxListaRetencionesRentaComprasTipoDocumentoTO();

            reporteAnxRetencionesRenta.setFechaDesde(fechaDesde);
            reporteAnxRetencionesRenta.setFechaHasta(fechaHasta);

            reporteAnxRetencionesRenta.setRetSustentotributario((plgTO.getRetSustentotributario() == null ? "" : plgTO.getRetSustentotributario()));
            reporteAnxRetencionesRenta.setRetConcepto((plgTO.getRetConcepto() == null ? "" : plgTO.getRetConcepto()));
            reporteAnxRetencionesRenta.setRetProveedorId((plgTO.getRetProveedorId() == null ? "" : plgTO.getRetProveedorId()));
            reporteAnxRetencionesRenta.setRetProveedorNombre((plgTO.getRetProveedorNombre() == null ? "" : plgTO.getRetProveedorNombre()));
            reporteAnxRetencionesRenta.setRetDocumentoNombre((plgTO.getRetDocumentoNombre() == null ? "" : plgTO.getRetDocumentoNombre()));
            reporteAnxRetencionesRenta.setRetDocumentoNumero((plgTO.getRetDocumentoNumero() == null ? "" : plgTO.getRetDocumentoNumero()));
            reporteAnxRetencionesRenta.setRetCompraFecha((plgTO.getRetCompraFecha() == null ? "" : plgTO.getRetCompraFecha()));
            reporteAnxRetencionesRenta.setRetCompraBase0((plgTO.getRetCompraBase0() == null ? null : plgTO.getRetCompraBase0()));
            reporteAnxRetencionesRenta.setRetComprabaseImponible((plgTO.getRetComprabaseImponible() == null ? null : plgTO.getRetComprabaseImponible()));
            reporteAnxRetencionesRenta.setRetRetencionAutorizacion((plgTO.getRetRetencionAutorizacion() == null ? "" : plgTO.getRetRetencionAutorizacion()));
            reporteAnxRetencionesRenta.setRetRetencionNumero((plgTO.getRetRetencionNumero() == null ? "" : plgTO.getRetRetencionNumero()));
            reporteAnxRetencionesRenta.setRetRetencionFecha((plgTO.getRetRetencionFecha() == null ? "" : plgTO.getRetRetencionFecha()));
            reporteAnxRetencionesRenta.setRetValorRetenidoIr((plgTO.getRetValorRetenidoIr() == null ? "" : plgTO.getRetValorRetenidoIr()));
            reporteAnxRetencionesRenta.setRetLlaveCompra((plgTO.getRetLlaveCompra() == null ? "" : plgTO.getRetLlaveCompra()));
            reporteAnxRetencionesRenta.setRetLlavecontable((plgTO.getRetLlavecontable() == null ? "" : plgTO.getRetLlavecontable()));
            lista.add(reporteAnxRetencionesRenta);
        }
        return genericReporteService.generarReporte(modulo, "reportRetencionesRentaComprasTipoDocumento.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteRetencionVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRetencionVentaReporteTO> listaRetencionVenta, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaRetencionVenta);
    }

    @Override
    public Map<String, Object> generarReporteAnexoListadoRetencionesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaRetencionesTO> listaRetencionesTO, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SRetenciones renta en compras (listado simple)");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SSust. Trib." + "¬" + "SId Proveedor" + "¬" + "SProveedor" + "¬" + "SDcto. Nombre" + "¬"
                    + "SNúmero Dcto." + "¬" + "SDcto. Autorización" + "¬" + "SFecha Comp." + "¬" + "SCompra Base 0" + "¬"
                    + "SCompra Base Imp." + "¬" + "SCompra Monto ICE" + "¬" + "SCompra Monto IVA" + "¬" + "S# Ret. Autori." + "¬" + "S# Ret." + "¬"
                    + "SFecha Ret." + "¬" + "SValor Retenido IR" + "¬" + "SValor Retenido IVA" + "¬"
                    + "STotal Retenido");
            for (AnxListaRetencionesTO anxListaRetencionesTO : listaRetencionesTO) {

                listaCuerpo.add((anxListaRetencionesTO.getRetSustentoTributario() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetSustentoTributario())
                        + "¬"
                        + (anxListaRetencionesTO.getRetProveedorId() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetProveedorId())
                        + "¬"
                        + (anxListaRetencionesTO.getRetProveedorNombre() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetProveedorNombre())
                        + "¬"
                        + (anxListaRetencionesTO.getRetDocumentoNombre() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetDocumentoNombre())
                        + "¬"
                        + (anxListaRetencionesTO.getRetDocumentoNumero() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetDocumentoNumero())
                        + "¬"
                        + (anxListaRetencionesTO.getRetDocumentoAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetDocumentoAutorizacion())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraFecha() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetCompraFecha())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraBase0() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetCompraBase0())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraBaseImponible() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetCompraBaseImponible())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraMontoIce() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetCompraMontoIce())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraMontoIva() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetCompraMontoIva())
                        + "¬"
                        + (anxListaRetencionesTO.getRetRetencionAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetRetencionAutorizacion())
                        + "¬"
                        + (anxListaRetencionesTO.getRetRetencionNumero() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetRetencionNumero())
                        + "¬"
                        + (anxListaRetencionesTO.getRetRetencionFecha() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetRetencionFecha())
                        + "¬"
                        + (anxListaRetencionesTO.getRetValorRetenidoIr() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetValorRetenidoIr())
                        + "¬"
                        + (anxListaRetencionesTO.getRetValorRetenidoIva() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetValorRetenidoIva())
                        + "¬" + (anxListaRetencionesTO.getRetTotalRetenido() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetTotalRetenido()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteAnexoListadoRetencionesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<AnxListaRetencionesTO> listaRetencionesTO) throws Exception {

        List<AnxListaRetencionesTO> lista = new ArrayList<AnxListaRetencionesTO>();
        for (AnxListaRetencionesTO plgTO : listaRetencionesTO) {
            AnxListaRetencionesTO reporteAnxListadoRetenciones = new AnxListaRetencionesTO();

            reporteAnxListadoRetenciones.setRetSustentoTributario((plgTO.getRetSustentoTributario() == null ? "" : plgTO.getRetSustentoTributario()));
            reporteAnxListadoRetenciones.setRetProveedorId((plgTO.getRetProveedorId() == null ? "" : plgTO.getRetProveedorId()));
            reporteAnxListadoRetenciones.setRetProveedorNombre((plgTO.getRetProveedorNombre() == null ? "" : plgTO.getRetProveedorNombre()));
            reporteAnxListadoRetenciones.setRetDocumentoNombre((plgTO.getRetDocumentoNombre() == null ? "" : plgTO.getRetDocumentoNombre()));
            reporteAnxListadoRetenciones.setRetDocumentoNumero((plgTO.getRetDocumentoNumero() == null ? "" : plgTO.getRetDocumentoNumero()));
            reporteAnxListadoRetenciones.setRetDocumentoAutorizacion((plgTO.getRetDocumentoAutorizacion() == null ? "" : plgTO.getRetDocumentoAutorizacion()));
            reporteAnxListadoRetenciones.setRetCompraFecha((plgTO.getRetCompraFecha() == null ? "" : plgTO.getRetCompraFecha()));
            reporteAnxListadoRetenciones.setRetCompraBase0((plgTO.getRetCompraBase0() == null ? null : plgTO.getRetCompraBase0()));
            reporteAnxListadoRetenciones.setRetCompraBaseImponible((plgTO.getRetCompraBaseImponible() == null ? new BigDecimal("0.00") : plgTO.getRetCompraBaseImponible()));
            reporteAnxListadoRetenciones.setRetRetencionAutorizacion((plgTO.getRetRetencionAutorizacion() == null ? "" : plgTO.getRetRetencionAutorizacion()));
            reporteAnxListadoRetenciones.setRetCompraMontoIva((plgTO.getRetCompraMontoIva() == null ? null : plgTO.getRetCompraMontoIva()));
            reporteAnxListadoRetenciones.setRetRetencionNumero((plgTO.getRetRetencionNumero() == null ? "" : plgTO.getRetRetencionNumero()));
            reporteAnxListadoRetenciones.setRetRetencionFecha((plgTO.getRetRetencionFecha() == null ? "" : plgTO.getRetRetencionFecha()));
            reporteAnxListadoRetenciones.setRetValorRetenidoIr((plgTO.getRetValorRetenidoIr() == null ? null : plgTO.getRetValorRetenidoIr()));
            reporteAnxListadoRetenciones.setRetTotalRetenido((plgTO.getRetTotalRetenido() == null ? null : plgTO.getRetTotalRetenido()));;
            lista.add(reporteAnxListadoRetenciones);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoRetencionesCompras.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    //cambiios se agrego parametros fechas
    @Override
    public Map<String, Object> generarReporteListadoRetencionesPorNumero(List<AnxListaRetencionesTO> listaAnxListaRetencionesTO,
            UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SRetenciones en compras (secuencia)");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SSust. Trib." + "¬" + "SProveedor" + "¬" + "SDcto. Nombre" + "¬" + "S# Dcto." + "¬"
                    + "SDcto. Autorización" + "¬" + "SFecha Comp." + "¬" + "SCompra Base 0" + "¬" + "SCompra Base Imp."
                    + "¬" + "SCompra Monto ICE" + "¬" + "SCompra Monto IVA" + "¬" + "S# Ret. Autori." + "¬" + "S# Ret." + "¬" + "SFecha Ret." + "¬"
                    + "SValor Retenido IR" + "¬" + "SValor Retenido IVA" + "¬" + "STotal Retenido");

            for (AnxListaRetencionesTO anxListaRetencionesTO : listaAnxListaRetencionesTO) {

                listaCuerpo.add((anxListaRetencionesTO.getRetSustentoTributario() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetSustentoTributario())
                        + "¬"
                        + (anxListaRetencionesTO.getRetProveedorNombre() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetProveedorNombre())
                        + "¬"
                        + (anxListaRetencionesTO.getRetDocumentoNombre() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetDocumentoNombre())
                        + "¬"
                        + (anxListaRetencionesTO.getRetDocumentoNumero() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetDocumentoNumero())
                        + "¬"
                        + (anxListaRetencionesTO.getRetDocumentoAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetDocumentoAutorizacion())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraFecha() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetCompraFecha())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraBase0() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetCompraBase0())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraBaseImponible() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetCompraBaseImponible())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraMontoIce() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetCompraMontoIce())
                        + "¬"
                        + (anxListaRetencionesTO.getRetCompraMontoIva() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetCompraMontoIva())
                        + "¬"
                        + (anxListaRetencionesTO.getRetRetencionAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetRetencionAutorizacion())
                        + "¬"
                        + (anxListaRetencionesTO.getRetRetencionNumero() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetRetencionNumero())
                        + "¬"
                        + (anxListaRetencionesTO.getRetRetencionFecha() == null ? "B"
                        : "S" + anxListaRetencionesTO.getRetRetencionFecha())
                        + "¬"
                        + (anxListaRetencionesTO.getRetValorRetenidoIr() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetValorRetenidoIr())
                        + "¬"
                        + (anxListaRetencionesTO.getRetValorRetenidoIva() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetValorRetenidoIva())
                        + "¬" + (anxListaRetencionesTO.getRetTotalRetenido() == null ? "B"
                        : "D" + anxListaRetencionesTO.getRetTotalRetenido()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarVentasElectronicasEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaVentaElectronicaTO> listado, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SVentas Electrónicas Emitidas");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "SMotivo" + "¬" + "SNumero" + "¬" + "SNúmero Dcto." + "¬"
                    + "SVenta Fecha" + "¬" + "SCliente" + "¬" + "SFecha y Hora de Autorización" + "¬"
                    + "SEmail" + "¬" + "SEnviado" + "¬" + "SEntregado" + "¬" + "SRebotado");
            for (AnxListaVentaElectronicaTO anxListaVentaElectronicaTO : listado) {
                listaCuerpo.add((anxListaVentaElectronicaTO.getVtaPeriodo() == null ? "B"
                        : "S" + anxListaVentaElectronicaTO.getVtaPeriodo())
                        + "¬"
                        + (anxListaVentaElectronicaTO.getVtaMotivo() == null ? "B"
                        : "S" + anxListaVentaElectronicaTO.getVtaMotivo())
                        + "¬"
                        + (anxListaVentaElectronicaTO.getVtaNumero() == null ? "B"
                        : "S" + anxListaVentaElectronicaTO.getVtaNumero())
                        + "¬"
                        + (anxListaVentaElectronicaTO.getVtaDocumentoNumero() == null ? "B"
                        : "S" + anxListaVentaElectronicaTO.getVtaDocumentoNumero())
                        + "¬"
                        + (anxListaVentaElectronicaTO.getVtaFecha() == null ? "B"
                        : "S" + anxListaVentaElectronicaTO.getVtaFecha())
                        + "¬"
                        + (anxListaVentaElectronicaTO.getVtaClienteRazonSocial() == null ? "B"
                        : "S" + anxListaVentaElectronicaTO.getVtaClienteRazonSocial())
                        + "¬"
                        + (anxListaVentaElectronicaTO.getVtaAutorizacionFecha() == null ? "B"
                        : "S" + anxListaVentaElectronicaTO.getVtaAutorizacionFecha())
                        + "¬"
                        + (anxListaVentaElectronicaTO.getEmail() == null ? "B" : "SOK")
                        + "¬"
                        + (anxListaVentaElectronicaTO.getEmailEnviado() == null ? "B" : "SOK")
                        + "¬"
                        + (anxListaVentaElectronicaTO.getEmailEntregado() == null ? "B" : "SOK")
                        + "¬"
                        + (anxListaVentaElectronicaTO.getEmailLeido() == null ? "B" : "SOK")
                        + "¬"
                        + (anxListaVentaElectronicaTO.getEmailRebotado() == null ? "B" : "SOK"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteVentasElectronicasEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaVentaElectronicaTO> listado, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public Map<String, Object> generarReporteRetencionesIvaCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaRetencionesFuenteIvaTO> listaAnxRetencionesFuentesIva, String fechaDesde, String fechaHasta) throws Exception {

        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SRetenciones IVA Compras ");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SSust. Trib.¬" + "SPorcentaje¬" + "SID¬" + "SProveedor¬" + "SDcto. Nombre¬" + "SNúmero Dcto.¬"
                    + "SDcto. Autorización¬" + "SFecha Comp.¬" + "SCompra Base 0¬" + "SCompra Base Imp.¬"
                    + "SNúmero Ret. Autori.¬" + "SNúmero Ret.¬" + "SFecha Ret.¬" + "SValor Retenido IR¬" + "SContable");
            for (AnxListaRetencionesFuenteIvaTO anxListaRetencionesFuenteIvaTO : listaAnxRetencionesFuentesIva) {
                listaCuerpo.add((anxListaRetencionesFuenteIvaTO.getRetSustentotributario() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetSustentotributario())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetPorcentaje() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetPorcentaje())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetProveedorId() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetProveedorId())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetProveedorNombre() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetProveedorNombre())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetDocumentoNombre() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetDocumentoNombre())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetDocumentoNumero() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetDocumentoNumero())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetDocumentoAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetDocumentoAutorizacion())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompraFecha() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompraFecha())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompraBase0() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetCompraBase0())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompraBaseImponible() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetCompraBaseImponible())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetRetencionAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetRetencionAutorizacion())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetRetencionNumero() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetRetencionNumero())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetRetencionFecha() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetRetencionFecha())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIr() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIr())
                        + "¬" + (anxListaRetencionesFuenteIvaTO.getRetLlaveContable() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetLlaveContable()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteRetencionesIvaCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String fechaDesde, String fechaHasta,
            List<AnxListaRetencionesFuenteIvaTO> listaAnxRetencionesFuentesIva) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listaAnxRetencionesFuentesIva);
    }

    @Override
    public Map<String, Object> generarReporteRegistroDatosCrediticios(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxFunRegistroDatosCrediticiosTO> listaAnxFunRegistroDatosCrediticiosTO, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SAnexo registro datos crediticios");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCodigo" + "¬" + "SFecha de datos" + "¬" + "STipo Cliente" + "¬" + "SID" + "¬" + "SNombre"
                    + "¬" + "SClase Sujeto" + "¬" + "SProvincia" + "¬" + "SCiudad" + "¬" + "SParroquia" + "¬" + "SSexo"
                    + "¬" + "SEstado Civil" + "¬" + "SOrigen ingreso" + "¬" + "SNúmero de operación" + "¬"
                    + "SValor de operación" + "¬" + "SSaldo de operación" + "¬" + "SFecha de concesión" + "¬"
                    + "SFecha de vencimiento" + "¬" + "SFecha exigible" + "¬" + "SPlazo operación" + "¬"
                    + "SPeriodicidad de pago" + "¬" + "SDías de morosidad" + "¬" + "SMonto de morosidad" + "¬"
                    + "SMonto interés en mora" + "¬" + "SValor por vencer de 1 a 30 días" + "¬"
                    + "SValor por vencer de 31 a 90 días" + "¬" + "SValor por vencer de 91 a 180 días" + "¬"
                    + "SValor por vencer de 181 a 360 días" + "¬" + "SValor por vencer más de 360 días" + "¬"
                    + "SValor vencido de 1 de 30 días" + "¬" + "SValor vencido de 31 de 90 días" + "¬"
                    + "SValor vencido de 91 de 180 días" + "¬" + "SValor vencido de 181 de 360 dias" + "¬"
                    + "SValor vencido más de 360 dias" + "¬" + "SDemanda judicial" + "¬" + "SCartera castigada" + "¬"
                    + "SCuota crédito" + "¬" + "SFecha de cancelación" + "¬" + "SForma de cancelación");
            for (AnxFunRegistroDatosCrediticiosTO anxFunRegistroDatosCrediticiosTO : listaAnxFunRegistroDatosCrediticiosTO) {
                listaCuerpo.add((anxFunRegistroDatosCrediticiosTO.getCliCodigoDinardap() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliCodigoDinardap())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliFechaCorte() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliFechaCorte().replace("-", "/"))
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliTipoId() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliTipoId().toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliID() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliID())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliRazonSocial() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliRazonSocial())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliClaseSujeto() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliClaseSujeto())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliProvincia() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliProvincia())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliCiudad() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliCiudad())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliParroquia() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliParroquia())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliSexo() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliSexo())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliEstadoCivil() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliEstadoCivil())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getCliOrigenIngreso() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getCliOrigenIngreso())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaDocumentoNumero() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getVtaDocumentoNumero())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaTotal() == null ? "F"
                        : "F" + anxFunRegistroDatosCrediticiosTO.getVtaTotal().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaSaldo() == null ? "F"
                        : "F" + anxFunRegistroDatosCrediticiosTO.getVtaSaldo().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaFechaConcecion() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getVtaFechaConcecion().replace("-",
                                "/"))
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaFechaVencimiento() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getVtaFechaVencimiento().replace("-",
                                "/"))
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaFechaExigible() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getVtaFechaExigible().replace("-", "/"))
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaPlazoOperacion() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getVtaPlazoOperacion().toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaPeriodicidadPago() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getVtaPeriodicidadPago().toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaDiasMorosidad() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getVtaDiasMorosidad().toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaMontoMorosidad() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaMontoMorosidad().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaMontoInteresMora() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaMontoInteresMora().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer0130() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer0130().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer3190() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer3190().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer91180() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer91180().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorporVencer181360() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorporVencer181360().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencerMas360() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencerMas360().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencido0130() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorVencido0130().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencido3190() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorVencido3190().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencido91180() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorVencido91180().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencido181360() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorVencido181360().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorDemandaJudicial() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorDemandaJudicial().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaCarteraCastigada() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaCarteraCastigada().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaCuotaCredito() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaCuotaCredito().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencido91180() == null ? "B"
                        : "D" + anxFunRegistroDatosCrediticiosTO.getVtaValorVencido91180().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaFechaCancelacion() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getVtaFechaCancelacion().replace("-",
                                "/"))
                        + "¬" + (anxFunRegistroDatosCrediticiosTO.getVtaFormaCancelacion() == null ? "B"
                        : "S" + anxFunRegistroDatosCrediticiosTO.getVtaFormaCancelacion().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<InputStream> generarReporteRide(String empresa, List<AnxListaVentaElectronicaTO> listaAnxListaVentaElectronicaTO) throws Exception {
        List<InputStream> list = new ArrayList<>();
        String ruta;
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaVentaElectronicaTO item : listaAnxListaVentaElectronicaTO) {
            String nombreReporte = "";
            if (item.getVtaDocumento_tipo().equalsIgnoreCase("18")) {
                nombreReporte = "reportComprobanteFacturaRide";
            } else if (item.getVtaDocumento_tipo().equalsIgnoreCase("05")) {
                nombreReporte = "reportComprobanteNotaDebitoRide";
            } else if (item.getVtaDocumento_tipo().equalsIgnoreCase("04")) {
                nombreReporte = "reportComprobanteNotaCreditoRide";
            }
            String xmlAutorizacion = ventaElectronicaService.getXmlComprobanteElectronico(empresa, item.getVtaPeriodo(), item.getVtaMotivo(), item.getVtaNumero());
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, nombreReporte, empresaParametros.getParRutaReportes());

            if (xmlAutorizacion != null && xmlAutorizacion.contains("reembolsos")) {
                nombreReporte = "reportComprobanteReembolsoRide";
            }

            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, nombreReporte, empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.charAt(0) == 'T') {
                ruta = archivoService.obtenerRutaDeReporteTemporal(rpta, claveAcceso + ".jrprint");
                list.add(new FileInputStream(archivoService.respondeServidorPDF(ruta)));
            }
        }
        return list;
    }

    @Override
    public byte[] generarReporteRideMatricial(String empresa, List<AnxListaVentaElectronicaTO> listaAnxListaVentaElectronicaTO) throws Exception {
        byte[] resultado = null;
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaVentaElectronicaTO item : listaAnxListaVentaElectronicaTO) {
            String nombreReporte = "";
            if (item.getVtaDocumento_tipo().equalsIgnoreCase("18")) {
                nombreReporte = "reportComprobanteFacturaRide";
            } else if (item.getVtaDocumento_tipo().equalsIgnoreCase("05")) {
                nombreReporte = "reportComprobanteNotaDebitoRide";
            } else if (item.getVtaDocumento_tipo().equalsIgnoreCase("04")) {
                nombreReporte = "reportComprobanteNotaCreditoRide";
            }
            String xmlAutorizacion = ventaElectronicaService.getXmlComprobanteElectronico(empresa, item.getVtaPeriodo(), item.getVtaMotivo(), item.getVtaNumero());
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, nombreReporte, empresaParametros.getParRutaReportes());
            
            if (xmlAutorizacion != null && xmlAutorizacion.contains("reembolsos")) {
                nombreReporte = "reportComprobanteReembolsoRide";
            }
            
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, nombreReporte, empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.charAt(0) == 'T') {
                resultado = ArrayUtils.addAll(resultado, rpta);
            }
        }

        return resultado;
    }

    @Override
    public List<InputStream> generarReporteRideRetencionesEmitidas(String empresa, List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO) throws Exception {
        List<InputStream> list = new ArrayList<>();
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListadoCompraElectronicaTO itemRetencion : listaAnxListaCompraElectronicaTO) {
            String periodo = itemRetencion.getCompPeriodo();
            String motivo = itemRetencion.getCompMotivo();
            String numero = itemRetencion.getCompNumero();
            String xmlAutorizacion = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, "reportComprobanteRetencionRide", empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, "reportComprobanteRetencionRide", empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.substring(0, 1).equalsIgnoreCase("T")) {
                String ruta = archivoService.obtenerRutaDeReporteTemporal(rpta, claveAcceso + ".jrprint");
                list.add(new FileInputStream(archivoService.respondeServidorPDF(ruta)));
            }
        }
        return list;
    }

    @Override
    public byte[] generarReporteRideRetencionesEmitidasMatricial(String empresa, List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO) throws Exception {
        byte[] resultado = new byte[0];
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListadoCompraElectronicaTO itemRetencion : listaAnxListaCompraElectronicaTO) {
            String periodo = itemRetencion.getCompPeriodo();
            String motivo = itemRetencion.getCompMotivo();
            String numero = itemRetencion.getCompNumero();
            String xmlAutorizacion = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, "reportComprobanteRetencionRide", empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, "reportComprobanteRetencionRide", empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.substring(0, 1).equalsIgnoreCase("T")) {
                resultado = ArrayUtils.addAll(resultado, rpta);
            }
        }
        return resultado;
    }

    @Override
    public List<ArchivoTO> generarXmlRetencionesElectronicasEmitidas(String empresa, List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO) throws Exception {

        List<ArchivoTO> list = new ArrayList<>();
        for (AnxListadoCompraElectronicaTO itemRetencion : listaAnxListaCompraElectronicaTO) {
            String periodo = itemRetencion.getCompPeriodo();
            String motivo = itemRetencion.getCompMotivo();
            String numero = itemRetencion.getCompNumero();
            String xmlAutorizacion = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
            String nombre = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            File contenido = genericReporteService.respondeServidorXML(xmlAutorizacion);
            list.add(new ArchivoTO(contenido, nombre));
        }
        return list;
    }

    @Override
    public List<InputStream> imprimirRideVentasPendientesElectronicas(String empresa, List<AnxListaVentasPendientesTO> listaEnviar) throws Exception {
        List<InputStream> list = new ArrayList<>();
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaVentasPendientesTO itemVenta : listaEnviar) {
            String nombreReporte = "";
            switch (itemVenta.getVtaDocumentoTipo()) {
                case "18":
                    nombreReporte = "reportComprobanteFacturaRide";
                    break;
                case "05":
                    nombreReporte = "reportComprobanteNotaDebitoRide";
                    break;
                case "04":
                    nombreReporte = "reportComprobanteNotaCreditoRide";
                    break;
            }
            String xmlAutorizacion = ventaElectronicaService.getXmlComprobanteElectronico(empresa, itemVenta.getVtaPeriodo(), itemVenta.getVtaMotivo(), itemVenta.getVtaNumero());
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, nombreReporte, empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, nombreReporte, empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.substring(0, 1).equalsIgnoreCase("T")) {
                String ruta = archivoService.obtenerRutaDeReporteTemporal(rpta, claveAcceso + ".jrprint");
                list.add(new FileInputStream(archivoService.respondeServidorPDF(ruta)));
            }
        }
        return list;
    }

    @Override
    public void generarTXT(List<AnxFunRegistroDatosCrediticiosTO> listado, HttpServletResponse response) throws Exception {
        try {
            File archivoCreado = File.createTempFile("xxx", ".txt");
            FileOutputStream fos = new FileOutputStream(archivoCreado);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            BigDecimal cero = new java.math.BigDecimal("0.00");
            for (AnxFunRegistroDatosCrediticiosTO anxFunRegistroDatosCrediticiosTO : listado) {
                out.write(anxFunRegistroDatosCrediticiosTO.getCliCodigoDinardap() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliCodigoDinardap()
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliFechaCorte() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliFechaCorte().replace("-", "/"))
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliTipoId() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliTipoId().toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliID() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliID())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliRazonSocial() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliRazonSocial())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliClaseSujeto() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliClaseSujeto())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliProvincia() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliProvincia())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliCiudad() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliCiudad())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliParroquia() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliParroquia())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliSexo() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliSexo())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliEstadoCivil() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliEstadoCivil())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getCliOrigenIngreso() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getCliOrigenIngreso())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaDocumentoNumero() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaDocumentoNumero())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaTotal() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaTotal().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaSaldo() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaSaldo().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaFechaConcecion() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaFechaConcecion().replace("-", "/"))
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaFechaVencimiento() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaFechaVencimiento().replace("-", "/"))
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaFechaExigible() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaFechaExigible().replace("-", "/"))
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaPlazoOperacion() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaPlazoOperacion().toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaPeriodicidadPago() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaPeriodicidadPago().toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaDiasMorosidad() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaDiasMorosidad().toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaMontoMorosidad() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaMontoMorosidad().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaMontoInteresMora() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaMontoInteresMora().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer0130() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer0130().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer3190() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer3190().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer91180() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencer91180().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorporVencer181360() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorporVencer181360().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencerMas360() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorPorVencerMas360().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencido0130() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorVencido0130().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencido3190() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorVencido3190().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencido91180() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorVencido91180().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencido181360() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorVencido181360().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorVencidomas360() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorVencidomas360().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaValorDemandaJudicial() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaValorDemandaJudicial().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaCarteraCastigada() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaCarteraCastigada().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaCuotaCredito() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaCuotaCredito().add(cero).toString())
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaFechaCancelacion() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaFechaCancelacion().replace("-", "/"))
                        + "|"
                        + (anxFunRegistroDatosCrediticiosTO.getVtaFormaCancelacion() == null ? ""
                        : anxFunRegistroDatosCrediticiosTO.getVtaFormaCancelacion().toString())
                );
                out.newLine();
            }
            out.close();
            fos.close();

            ServletOutputStream servletStream = response.getOutputStream();
            InputStream in = new FileInputStream(archivoCreado);
            int bit;
            while ((bit = in.read()) != -1) {
                servletStream.write(bit);
            }
            servletStream.flush();
            servletStream.close();
            in.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArchivoTO generarXmlAnexoTransaccional(String empresa, String fechaDesde, String fechaHasta, String ruc, String nombreEmpresa, Boolean agente) throws Exception {
        AnexosBBListaRetencionesTO anxListaRetenciones = new AnexosBBListaRetencionesTO();
        String[] itemFechaHasta = fechaHasta.split("-");
        String nombreXml = empresa + "-" + itemFechaHasta[0] + "-" + itemFechaHasta[1];
        llenarListasXML(empresa, fechaDesde, fechaHasta, anxListaRetenciones);
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(armarArchivoXML(empresa, fechaDesde, fechaHasta, anxListaRetenciones, ruc, nombreEmpresa, agente)), new StreamResult(sw));
        File contenido = genericReporteService.respondeServidorXML(sw.toString());
        return new ArchivoTO(contenido, nombreXml);
    }

    public void llenarListasXML(String empresa, String fechaDesde, String fechaHasta, AnexosBBListaRetencionesTO anxListaRetenciones) throws Exception {
        anxListaRetenciones.setAnxListaRetencionesTO(compraDao.getAnexoListaRetencionesTO(empresa, fechaDesde, fechaHasta));
        anxListaRetenciones.setAnxListaComprobanteAnuladoTOs(anuladosDao.getAnxListaComprobanteAnuladoTO(empresa, fechaDesde, fechaHasta));
        anxListaRetenciones.setAnxListaConsolidadoRetencionesVentasTOs(ventaDao.getAnxListaConsolidadoRetencionesVentasTO(empresa, fechaDesde, fechaHasta));
        anxListaRetenciones.setAnxListaEstablecimientoRetencionesVentasTOs(ventaDao.getAnxListaEstablecimientoRetencionesVentasTO(empresa, fechaDesde, fechaHasta));
        anxListaRetenciones.setAnxListaExportaciones(anxVentaExportacionDao.getAnxListaVentaExportacionTO(empresa, fechaDesde, fechaHasta));
    }

    public Document armarArchivoXML(String empresa, String fechaDesde, String fechaHasta, AnexosBBListaRetencionesTO anxListaRetenciones, String ruc, String nombreEmpresa, Boolean agente) throws Exception {
        List<String> comprobante = new ArrayList<>();
        List<String> compra = new ArrayList<>();
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();

        Document document = implementation.createDocument(null, "iva", null);
        document.setXmlVersion("1.0");

        Element raiz = document.getDocumentElement();
        Element compras = document.createElement("compras");
        Element ventas = document.createElement("ventas");
        Element ventasEstablecimiento = document.createElement("ventasEstablecimiento");
        Element exportaciones = document.createElement("exportaciones");
        Element anulados = document.createElement("anulados");

        Element TipoIDInformante = document.createElement("TipoIDInformante");
        Text textTipoIDInformante = document.createTextNode("R");
        TipoIDInformante.appendChild(textTipoIDInformante);
        raiz.appendChild(TipoIDInformante);

        Element IdInformante = document.createElement("IdInformante");
        Text textIdInformante = document.createTextNode(ruc);
        IdInformante.appendChild(textIdInformante);
        raiz.appendChild(IdInformante);

        Element razonSocial = document.createElement("razonSocial");
        Text textRazonSocial = document.createTextNode(nombreEmpresa);
        razonSocial.appendChild(textRazonSocial);
        raiz.appendChild(razonSocial);

        String[] itemFechaHasta = fechaHasta.split("-");
        String mesf = itemFechaHasta[1];

        Element anio = document.createElement("Anio");
        Text textAnio = document.createTextNode(itemFechaHasta[0]);// ((new
        anio.appendChild(textAnio);
        raiz.appendChild(anio);

        Element mes = document.createElement("Mes");
        int mesNro = Integer.parseInt(mesf);
        Text textMes = document.createTextNode((mesNro > 9 ? "" : "0") + mesNro);
        mes.appendChild(textMes);
        raiz.appendChild(mes);

        //microempresa
        if (agente) {
            Element regimenMicroempresa = document.createElement("regimenMicroempresa");
            Text textRegimen = document.createTextNode("SI");
            regimenMicroempresa.appendChild(textRegimen);
            raiz.appendChild(regimenMicroempresa);
        }

        BigDecimal totaVentas = BigDecimal.ZERO;

        for (AnxListaConsolidadoRetencionesVentasTO anxListaConsolidadoRetencionesVentasTO : anxListaRetenciones.getAnxListaConsolidadoRetencionesVentasTOs()) {
            if (anxListaConsolidadoRetencionesVentasTO.getRvcTransaccion() == null) {
                anxListaConsolidadoRetencionesVentasTO.setRvcBaseNoObjetoIva(anxListaConsolidadoRetencionesVentasTO.getRvcBaseNoObjetoIva() == null ? BigDecimal.ZERO : anxListaConsolidadoRetencionesVentasTO.getRvcBaseNoObjetoIva());
                anxListaConsolidadoRetencionesVentasTO.setRvcBase0(anxListaConsolidadoRetencionesVentasTO.getRvcBase0() == null ? BigDecimal.ZERO : anxListaConsolidadoRetencionesVentasTO.getRvcBase0());
                anxListaConsolidadoRetencionesVentasTO.setVenBaseImponible(anxListaConsolidadoRetencionesVentasTO.getVenBaseImponible() == null ? BigDecimal.ZERO : anxListaConsolidadoRetencionesVentasTO.getVenBaseImponible());
                totaVentas = anxListaConsolidadoRetencionesVentasTO.getRvcBase0()
                        .add(anxListaConsolidadoRetencionesVentasTO.getRvcBaseNoObjetoIva())
                        .add(anxListaConsolidadoRetencionesVentasTO.getVenBaseImponible());
                break;
            }
        }

        int numeroEstablecimientos = anxListaRetenciones.getAnxListaEstablecimientoRetencionesVentasTOs().size();

        if (numeroEstablecimientos != 0) {
            Element numEstabRuc = document.createElement("numEstabRuc");
            Text textnumEstabRuc = document.createTextNode("00" + numeroEstablecimientos);
            if (numeroEstablecimientos > 9) {
                textnumEstabRuc = document.createTextNode("0" + numeroEstablecimientos);
            }
            if (numeroEstablecimientos > 99) {
                textnumEstabRuc = document.createTextNode("" + numeroEstablecimientos);
            }
            numEstabRuc.appendChild(textnumEstabRuc);
            raiz.appendChild(numEstabRuc);
        }

        Element totalVentas = document.createElement("totalVentas");
        Text textTotalVentas = document.createTextNode(totaVentas.toString());
        totalVentas.appendChild(textTotalVentas);
        raiz.appendChild(totalVentas);

        Element codigoOperativo = document.createElement("codigoOperativo");
        Text textCodigoOperativo = document.createTextNode("IVA");
        codigoOperativo.appendChild(textCodigoOperativo);
        raiz.appendChild(codigoOperativo);

        Element detalleCompras;
        Element detalleVentas;
        Element ventaEst;
        Element detalleAnulados;
        Element detalleExportaciones;

        List<AnxCompraDetalleTO> anxCompraDetalleTOs = new ArrayList<>();
        List<AnxCompraFormaPagoTO> anxCompraFormaPagoTOs = new ArrayList<>();
        List<AnxCompraReembolsoTO> anxCompraReembolsoTO = new ArrayList<>();

        for (AnxListaRetencionesTO anxListaRetencionesTO : anxListaRetenciones.getAnxListaRetencionesTO()) {
            if (anxListaRetencionesTO.getRetSustentoTributario() != null && !anxListaRetencionesTO.getRetSustentoTributario().isEmpty()) {

                detalleCompras = document.createElement("detalleCompras");

                Element codSustento = document.createElement("codSustento");
                Text textCodSustento = document.createTextNode(anxListaRetencionesTO.getRetSustentoTributario());
                codSustento.appendChild(textCodSustento);
                detalleCompras.appendChild(codSustento);

                Element tpIdProv = document.createElement("tpIdProv");
                Text textTpIdProv = document.createTextNode(anxListaRetencionesTO.getRetProveedorTipo());
                tpIdProv.appendChild(textTpIdProv);
                detalleCompras.appendChild(tpIdProv);

                Element idProv = document.createElement("idProv");
                Text textIdProv = document.createTextNode(anxListaRetencionesTO.getRetProveedorId());
                idProv.appendChild(textIdProv);
                detalleCompras.appendChild(idProv);

                Element tipoComprobante = document.createElement("tipoComprobante");
                Text textTipoComprobante = document.createTextNode(anxListaRetencionesTO.getRetDocumentoTipo());
                tipoComprobante.appendChild(textTipoComprobante);
                detalleCompras.appendChild(tipoComprobante);

                if (anxListaRetencionesTO.getRetProveedorTipo() != null && anxListaRetencionesTO.getRetProveedorTipo().equals("03")) {//Si es pasaporte
                    Element tipoProv = document.createElement("tipoProv");
                    Text textTipoProv = document.createTextNode(
                            anxListaRetencionesTO.getRetProveedorExtranjeroTipo() != null && !anxListaRetencionesTO.getRetProveedorExtranjeroTipo().equals("")
                            ? anxListaRetencionesTO.getRetProveedorExtranjeroTipo() : "01"
                    );
                    tipoProv.appendChild(textTipoProv);
                    detalleCompras.appendChild(tipoProv);

                    String exteriorTipo = "PERSONA NATURAL";
                    if (anxListaRetencionesTO.getRetProveedorExtranjeroTipo() != null && anxListaRetencionesTO.getRetProveedorExtranjeroTipo().equals("02")) {
                        exteriorTipo = "SOCIEDAD";
                    }

                    Element denoProv = document.createElement("denoProv");
                    Text textDenoProv = document.createTextNode(exteriorTipo);
                    denoProv.appendChild(textDenoProv);
                    detalleCompras.appendChild(denoProv);
                }

                Element parteRel = document.createElement("parteRel");
                Text textParteRel = document.createTextNode(anxListaRetencionesTO.getRetProveedorRelacionado().trim());
                parteRel.appendChild(textParteRel);
                detalleCompras.appendChild(parteRel);

                Element fechaRegistro = document.createElement("fechaRegistro");
                Text textFechaRegistro = document.createTextNode(UtilsValidacion.convertirAaaaMmDdTODdMmAaaa(anxListaRetencionesTO.getRetCompraFecha()).replace('-', '/'));
                fechaRegistro.appendChild(textFechaRegistro);
                detalleCompras.appendChild(fechaRegistro);

                Element establecimiento = document.createElement("establecimiento");
                Text textEstablecimiento;

                Element puntoEmision = document.createElement("puntoEmision");
                Text textPuntoEmision;

                Element secuencial = document.createElement("secuencial");
                Text textSecuencial;

                comprobante = UtilsValidacion.separarComprobante(anxListaRetencionesTO.getRetDocumentoNumero().replace('-', '|'));
                textEstablecimiento = document.createTextNode(comprobante.get(0));
                textPuntoEmision = document.createTextNode(comprobante.get(1));
                textSecuencial = document.createTextNode(comprobante.get(2));

                establecimiento.appendChild(textEstablecimiento);
                detalleCompras.appendChild(establecimiento);
                puntoEmision.appendChild(textPuntoEmision);
                detalleCompras.appendChild(puntoEmision);
                secuencial.appendChild(textSecuencial);
                detalleCompras.appendChild(secuencial);

                Element fechaEmision = document.createElement("fechaEmision");
                Text textFechaEmision = document.createTextNode(UtilsValidacion.convertirAaaaMmDdTODdMmAaaa(anxListaRetencionesTO.getRetCompraFecha()).replace('-', '/'));
                fechaEmision.appendChild(textFechaEmision);
                detalleCompras.appendChild(fechaEmision);

                Element autorizacion = document.createElement("autorizacion");
                Text textAutorizacion = document.createTextNode(anxListaRetencionesTO.getRetDocumentoAutorizacion().trim());
                autorizacion.appendChild(textAutorizacion);
                detalleCompras.appendChild(autorizacion);

                Element baseNoGraIva = document.createElement("baseNoGraIva");
                Text textBaseNoGraIva = document.createTextNode("0.00");
                baseNoGraIva.appendChild(textBaseNoGraIva);
                detalleCompras.appendChild(baseNoGraIva);

                Element baseImponible = document.createElement("baseImponible");
                Text textBaseImponible = document.createTextNode(anxListaRetencionesTO.getRetCompraBase0() + "");
                baseImponible.appendChild(textBaseImponible);
                detalleCompras.appendChild(baseImponible);

                Element baseImpGrav = document.createElement("baseImpGrav");
                Text textBaseImpGrav = document.createTextNode(anxListaRetencionesTO.getRetCompraBaseImponible() + "");
                baseImpGrav.appendChild(textBaseImpGrav);
                detalleCompras.appendChild(baseImpGrav);

                Element baseImpExe = document.createElement("baseImpExe");
                Text textbaseImpExe = document.createTextNode(anxListaRetencionesTO.getRetCompraBaseExenta() + "");
                baseImpExe.appendChild(textbaseImpExe);
                detalleCompras.appendChild(baseImpExe);

                Element montoIce = document.createElement("montoIce");
                Text textMontoIce = document.createTextNode(anxListaRetencionesTO.getRetCompraMontoIce() + "");
                montoIce.appendChild(textMontoIce);
                detalleCompras.appendChild(montoIce);

                Element montoIva = document.createElement("montoIva");
                Text textMontoIva = document.createTextNode(anxListaRetencionesTO.getRetCompraMontoIva() + "");
                montoIva.appendChild(textMontoIva);
                detalleCompras.appendChild(montoIva);

                Element valRetBienes10 = document.createElement("valRetBien10");
                Text textValRetBienes10 = document.createTextNode(anxListaRetencionesTO.getRetValorBienes10() + "");
                valRetBienes10.appendChild(textValRetBienes10);
                detalleCompras.appendChild(valRetBienes10);

                Element valRetBienes20 = document.createElement("valRetServ20");
                Text textValRetBienes20 = document.createTextNode(anxListaRetencionesTO.getRetValorBienes20() + "");
                valRetBienes20.appendChild(textValRetBienes20);
                detalleCompras.appendChild(valRetBienes20);

                Element valorRetBienes = document.createElement("valorRetBienes");
                Text textValorRetBienes = document.createTextNode(anxListaRetencionesTO.getRetValorBienes30() + "");
                valorRetBienes.appendChild(textValorRetBienes);
                detalleCompras.appendChild(valorRetBienes);

                Element valRetServ50 = document.createElement("valRetServ50");
                Text textvalRetServ50 = document.createTextNode(anxListaRetencionesTO.getRetValorServicios50() + "");/// Revisar
                valRetServ50.appendChild(textvalRetServ50);
                detalleCompras.appendChild(valRetServ50);

                Element valorRetServicios = document.createElement("valorRetServicios");
                Text textValorRetServicios = document.createTextNode(anxListaRetencionesTO.getRetValorServicios70() + "");
                valorRetServicios.appendChild(textValorRetServicios);
                detalleCompras.appendChild(valorRetServicios);

                Element valRetServ100 = document.createElement("valRetServ100");
                Text textValRetServ100 = document.createTextNode(anxListaRetencionesTO.getRetValorServiciosProfesionales() + "");
                valRetServ100.appendChild(textValRetServ100);
                detalleCompras.appendChild(valRetServ100);

                if (anxListaRetencionesTO.getRetDocumentoTipo().equals("19")) {
                    BigDecimal totValorRetencionNc = new BigDecimal("0.00");
                    Element valorRetencionNc = document.createElement("valorRetencionNc");
                    Text textValorRetencionNc = document.createTextNode(totValorRetencionNc + "");
                    valorRetencionNc.appendChild(textValorRetencionNc);
                    detalleCompras.appendChild(valorRetencionNc);
                }

                BigDecimal totBaseImpReemb = new BigDecimal("0.00");

                if (anxListaRetencionesTO.getRetDocumentoTipo().equals("41")) {
                    totBaseImpReemb = anxListaRetencionesTO.getRetCompraBaseImponible().add(anxListaRetencionesTO.getRetCompraBase0());
                }

                Element totbasesImpReemb = document.createElement("totbasesImpReemb");
                Text texttotbasesImpReemb = document.createTextNode(totBaseImpReemb + "");
                totbasesImpReemb.appendChild(texttotbasesImpReemb);
                detalleCompras.appendChild(totbasesImpReemb);

                Element pagoExterior = document.createElement("pagoExterior");
                Element pagoLocExt = document.createElement("pagoLocExt");
                Text textPagoLocExt = document.createTextNode("01");
                pagoLocExt.appendChild(textPagoLocExt);
                pagoExterior.appendChild(pagoLocExt);

                Element paisEfecPago = document.createElement("paisEfecPago");
                Text textPaisEfecPago = document.createTextNode("NA");
                paisEfecPago.appendChild(textPaisEfecPago);
                pagoExterior.appendChild(paisEfecPago);

                Element aplicConvDobTrib = document.createElement("aplicConvDobTrib");
                Text textAplicConvDobTrib = document.createTextNode("NA");
                aplicConvDobTrib.appendChild(textAplicConvDobTrib);
                pagoExterior.appendChild(aplicConvDobTrib);

                Element pagExtSujRetNorLeg = document.createElement("pagExtSujRetNorLeg");
                Text textPagExtSujRetNorLeg = document.createTextNode("NA");
                pagExtSujRetNorLeg.appendChild(textPagExtSujRetNorLeg);
                pagoExterior.appendChild(pagExtSujRetNorLeg);

                detalleCompras.appendChild(pagoExterior);

                if (anxListaRetencionesTO.getRetCompraBase0().add(anxListaRetencionesTO.getRetCompraBaseImponible())
                        .add(anxListaRetencionesTO.getRetCompraMontoIva().add(anxListaRetencionesTO.getRetCompraMontoIce())).compareTo(new BigDecimal("1000.0")) >= 0) {
                    Element formasDePago = document.createElement("formasDePago");

                    compra = UtilsValidacion.separarComprobante(anxListaRetencionesTO.getRetLlaveCompra());
                    anxCompraFormaPagoTOs = compraFormaPagoDao.getAnexoCompraFormaPagoTO(empresa, compra.get(0), compra.get(1), compra.get(2));
                    if (!anxListaRetencionesTO.getRetDocumentoTipo().equals("04") || !anxListaRetencionesTO.getRetDocumentoTipo().equals("05")) {
                        if (anxCompraFormaPagoTOs != null && !anxCompraFormaPagoTOs.isEmpty()) {
                            for (AnxCompraFormaPagoTO o : anxCompraFormaPagoTOs) {
                                Element formaPago = document.createElement("formaPago");
                                Text textFormaPago = document.createTextNode(o.getFpCodigo());
                                formaPago.appendChild(textFormaPago);
                                formasDePago.appendChild(formaPago);
                                detalleCompras.appendChild(formasDePago);
                            }
                        } else {
                            Element formaPago = document.createElement("formaPago");
                            Text textFormaPago = document.createTextNode("02");
                            formaPago.appendChild(textFormaPago);
                            formasDePago.appendChild(formaPago);
                            detalleCompras.appendChild(formasDePago);
                        }

                    }
                }

                Element estabRetencion1 = document.createElement("estabRetencion1");
                Element ptoEmiRetencion1 = document.createElement("ptoEmiRetencion1");
                Element secRetencion1 = document.createElement("secRetencion1");
                Element autRetencion1 = document.createElement("autRetencion1");

                Text textEstabRetencion1;
                Text textPtoEmiRetencion1;
                Text textSecRetencion1;
                Text textAutRetencion1;

                if (anxListaRetencionesTO.getRetModificadoDocumentoTipo() != null && !anxListaRetencionesTO.getRetModificadoDocumentoTipo().isEmpty()) {

                    comprobante = UtilsValidacion.separarComprobante(anxListaRetencionesTO.getRetModificadoDocumentonumero().replace('-', '|'));

                    Element docModificado = document.createElement("docModificado");
                    Text textdocModificado = document.createTextNode(anxListaRetencionesTO.getRetModificadoDocumentoTipo());
                    docModificado.appendChild(textdocModificado);
                    detalleCompras.appendChild(docModificado);

                    Element estabModificado = document.createElement("estabModificado");
                    Text textestabModificado = document.createTextNode(comprobante.get(0));
                    estabModificado.appendChild(textestabModificado);
                    detalleCompras.appendChild(estabModificado);

                    Element ptoEmiModificado = document.createElement("ptoEmiModificado");
                    Text textptoEmiModificado = document.createTextNode(comprobante.get(1));
                    ptoEmiModificado.appendChild(textptoEmiModificado);
                    detalleCompras.appendChild(ptoEmiModificado);

                    Element secModificado = document.createElement("secModificado");
                    Text textsecModificado = document.createTextNode(comprobante.get(2));
                    secModificado.appendChild(textsecModificado);
                    detalleCompras.appendChild(secModificado);

                    Element autModificado = document.createElement("autModificado");
                    Text textautModificado = document.createTextNode(anxListaRetencionesTO.getRetModificadoAutorizacion());
                    autModificado.appendChild(textautModificado);
                    detalleCompras.appendChild(autModificado);
                }

                if (!anxListaRetencionesTO.getRetDocumentoTipo().equals("04")
                        && !anxListaRetencionesTO.getRetDocumentoTipo().equals("05")
                        && !anxListaRetencionesTO.getRetDocumentoTipo().equals("41")) {

                    Element air = document.createElement("air");
                    Element detalleAir;
                    assert anxListaRetencionesTO.getRetLlaveCompra() != null : "El numero de compra es NULO";
                    compra = UtilsValidacion.separarComprobante(anxListaRetencionesTO.getRetLlaveCompra());
                    anxCompraDetalleTOs = compraDetalleDao.getAnexoCompraDetalleTO(empresa, compra.get(0), compra.get(1), compra.get(2));

                    for (int i = 0; i < anxCompraDetalleTOs.size(); i++) {

                        detalleAir = document.createElement("detalleAir");

                        Element codRetAir = document.createElement("codRetAir");
                        Text textCodRetAir = document.createTextNode(anxCompraDetalleTOs.get(i).getDetConcepto());
                        codRetAir.appendChild(textCodRetAir);
                        detalleAir.appendChild(codRetAir);

                        BigDecimal base = anxCompraDetalleTOs.get(i).getDetBase0()
                                .add(anxCompraDetalleTOs.get(i).getDetBaseImponible())
                                .add(anxCompraDetalleTOs.get(i).getDetBaseNoObjetoIva());
                        Element baseImpAir = document.createElement("baseImpAir");
                        Text textBaseImpAir = document.createTextNode(base.toString());
                        baseImpAir.appendChild(textBaseImpAir);
                        detalleAir.appendChild(baseImpAir);

                        Element porcentajeAir = document.createElement("porcentajeAir");
                        Text textPorcentajeAir = document.createTextNode(UtilsValidacion.redondeoDecimalBigDecimal(anxCompraDetalleTOs.get(i).getDetPorcentaje(), "#.00") + "");
                        porcentajeAir.appendChild(textPorcentajeAir);
                        detalleAir.appendChild(porcentajeAir);

                        Element valRetAir = document.createElement("valRetAir");
                        Text textValRetAir = document.createTextNode(anxCompraDetalleTOs.get(i).getDetValorRetenido() + "");
                        valRetAir.appendChild(textValRetAir);
                        detalleAir.appendChild(valRetAir);
                        air.appendChild(detalleAir);

                        if (anxCompraDetalleTOs.get(i).getDetConcepto().equals("325A")) {

                            Element valFechaPagoDiv = document.createElement("fechaPagoDiv");
                            Text textFechaPagoDiv = document
                                    .createTextNode(
                                            anxCompraDetalleTOs.get(i)
                                                    .getDivFechaPago() == null
                                                    ? ""
                                                    : UtilsValidacion
                                                            .fecha(anxCompraDetalleTOs.get(i).getDivFechaPago(),
                                                                    "yyyy-MM-dd", "dd-MM-yyyy")
                                                            .replace('-', '/') + "");
                            valFechaPagoDiv.appendChild(textFechaPagoDiv);
                            detalleAir.appendChild(valFechaPagoDiv);
                            air.appendChild(detalleAir);

                            Element valImRentaSoc = document.createElement("imRentaSoc");
                            Text textImRentaSoc = document.createTextNode(anxCompraDetalleTOs.get(i).getDivIrAsociado() == null ? "" : anxCompraDetalleTOs.get(i).getDivIrAsociado() + "");
                            valImRentaSoc.appendChild(textImRentaSoc);
                            detalleAir.appendChild(valImRentaSoc);
                            air.appendChild(detalleAir);

                            Element valAnioUtDiv = document.createElement("anioUtDiv");
                            Text textAnioUtDiv = document.createTextNode(anxCompraDetalleTOs.get(i).getDivAnioUtilidades() == null ? "" : anxCompraDetalleTOs.get(i).getDivAnioUtilidades() + "");
                            valAnioUtDiv.appendChild(textAnioUtDiv);
                            detalleAir.appendChild(valAnioUtDiv);
                            air.appendChild(detalleAir);

                        }
                    }

                    detalleCompras.appendChild(air);

                    if (anxListaRetencionesTO.getRetRetencionNumero() != null) {
                        comprobante = UtilsValidacion.separarComprobante(anxListaRetencionesTO.getRetRetencionNumero().replace('-', '|'));

                        textEstabRetencion1 = document.createTextNode(comprobante.get(0));
                        textPtoEmiRetencion1 = document.createTextNode(comprobante.get(1));
                        textSecRetencion1 = document.createTextNode(comprobante.get(2));
                        textAutRetencion1 = document.createTextNode(anxListaRetencionesTO.getRetRetencionAutorizacion());

                        estabRetencion1.appendChild(textEstabRetencion1);
                        detalleCompras.appendChild(estabRetencion1);

                        ptoEmiRetencion1.appendChild(textPtoEmiRetencion1);
                        detalleCompras.appendChild(ptoEmiRetencion1);

                        secRetencion1.appendChild(textSecRetencion1);
                        detalleCompras.appendChild(secRetencion1);

                        autRetencion1.appendChild(textAutRetencion1);
                        detalleCompras.appendChild(autRetencion1);

                        Element fechaEmiRet1 = document.createElement("fechaEmiRet1");
                        Text textFechaEmiRet1 = document.createTextNode(UtilsValidacion
                                .convertirAaaaMmDdTODdMmAaaa(anxListaRetencionesTO.getRetRetencionFecha())
                                .replace('-', '/'));
                        fechaEmiRet1.appendChild(textFechaEmiRet1);
                        detalleCompras.appendChild(fechaEmiRet1);
                    }
                }

                if (anxListaRetencionesTO.getRetDocumentoTipo().equals("41")) {
                    compra = UtilsValidacion.separarComprobante(anxListaRetencionesTO.getRetLlaveCompra());
                    anxCompraReembolsoTO = compraReembolsoDao.getAnexoCompraReembolsoTOs(empresa, compra.get(0), compra.get(1), compra.get(2));
                    Element reembolsos = document.createElement("reembolsos");
                    Element reembolso;

                    int c = 0;
                    for (AnxCompraReembolsoTO o : anxCompraReembolsoTO) {
                        reembolso = document.createElement("reembolso");

                        Element tipoComprobanteReemb = document.createElement("tipoComprobanteReemb");
                        Text textTipoComprobanteReemb = document.createTextNode(o.getReembDocumentoTipo());
                        tipoComprobanteReemb.appendChild(textTipoComprobanteReemb);
                        reembolso.appendChild(tipoComprobanteReemb);

                        Element tpIdProvReemb = document.createElement("tpIdProvReemb");
                        Text textTpIdProvReemb = document.createTextNode("01");
                        tpIdProvReemb.appendChild(textTpIdProvReemb);
                        reembolso.appendChild(tpIdProvReemb);

                        Element idProvReemb = document.createElement("idProvReemb");
                        Text textIdProvReemb = document.createTextNode(o.getAuxProvRUC());
                        idProvReemb.appendChild(textIdProvReemb);
                        reembolso.appendChild(idProvReemb);

                        Element establecimientoReemb = document.createElement("establecimientoReemb");
                        Text textEstablecimientoReemb = document.createTextNode(o.getReembDocumentoNumero().substring(0, 3));
                        establecimientoReemb.appendChild(textEstablecimientoReemb);
                        reembolso.appendChild(establecimientoReemb);

                        Element puntoEmisionReemb = document.createElement("puntoEmisionReemb");
                        Text textPuntoEmisionReemb = document.createTextNode(o.getReembDocumentoNumero().substring(4, 7));
                        puntoEmisionReemb.appendChild(textPuntoEmisionReemb);
                        reembolso.appendChild(puntoEmisionReemb);

                        Element secuencialReemb = document.createElement("secuencialReemb");
                        Text textSecuencialReemb = document.createTextNode(o.getReembDocumentoNumero().substring(8, o.getReembDocumentoNumero().length()));
                        secuencialReemb.appendChild(textSecuencialReemb);
                        reembolso.appendChild(secuencialReemb);

                        Element fechaEmisionReemb = document.createElement("fechaEmisionReemb");
                        String fechaFormateada = UtilsValidacion.fecha(o.getReembFechaemision(), "yyyy-MM-dd", "dd-MM-yyyy");
                        Text textFechaEmisionReemb = document.createTextNode(fechaFormateada.replace('-', '/'));
                        fechaEmisionReemb.appendChild(textFechaEmisionReemb);
                        reembolso.appendChild(fechaEmisionReemb);

                        Element autorizacionReemb = document.createElement("autorizacionReemb");
                        Text textAutorizacionReemb = document.createTextNode(o.getReembAutorizacion());
                        autorizacionReemb.appendChild(textAutorizacionReemb);
                        reembolso.appendChild(autorizacionReemb);

                        Element baseImponibleReemb = document.createElement("baseImponibleReemb");
                        Text textBaseImponibleReemb = document.createTextNode(o.getReembBaseimponible() + "");
                        baseImponibleReemb.appendChild(textBaseImponibleReemb);
                        reembolso.appendChild(baseImponibleReemb);

                        Element baseImpGravReemb = document.createElement("baseImpGravReemb");
                        Text textBaseImpGravReemb = document.createTextNode(o.getReembBaseimpgrav() + "");
                        baseImpGravReemb.appendChild(textBaseImpGravReemb);
                        reembolso.appendChild(baseImpGravReemb);

                        Element baseNoGraIvaReemb = document.createElement("baseNoGraIvaReemb");
                        Text textBaseNoGraIvaReemb = document.createTextNode(o.getReembBasenograiva() + "");
                        baseNoGraIvaReemb.appendChild(textBaseNoGraIvaReemb);
                        reembolso.appendChild(baseNoGraIvaReemb);

                        Element baseImpExeReemb = document.createElement("baseImpExeReemb");
                        Text textbaseImpExeReemb = document.createTextNode("0.00");
                        baseImpExeReemb.appendChild(textbaseImpExeReemb);
                        reembolso.appendChild(baseImpExeReemb);

                        Element montoIceRemb = document.createElement("montoIceRemb");
                        Text textMontoIceRemb = document.createTextNode(o.getReembMontoice() + "");
                        montoIceRemb.appendChild(textMontoIceRemb);
                        reembolso.appendChild(montoIceRemb);

                        Element montoIvaRemb = document.createElement("montoIvaRemb");
                        Text textMontoIvaRemb = document.createTextNode(o.getReembMontoiva() + "");
                        montoIvaRemb.appendChild(textMontoIvaRemb);
                        reembolso.appendChild(montoIvaRemb);
                        reembolsos.appendChild(reembolso);
                        c++;
                    }
                    if (!anxCompraReembolsoTO.isEmpty()) {
                        detalleCompras.appendChild(reembolsos);
                    }
                }
                compras.appendChild(detalleCompras);
            }
            raiz.appendChild(compras);
        }

        int c = 0;
        for (AnxListaConsolidadoRetencionesVentasTO anxListaConsolidadoRetencionesVentasTO : anxListaRetenciones.getAnxListaConsolidadoRetencionesVentasTOs()) {

            if (anxListaConsolidadoRetencionesVentasTO.getRvcTransaccion() != null) {

                detalleVentas = document.createElement("detalleVentas");
                Element tpIdCliente = document.createElement("tpIdCliente");
                Text texttpIdCliente = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getRvcTransaccion());
                tpIdCliente.appendChild(texttpIdCliente);
                detalleVentas.appendChild(tpIdCliente);

                Element idCliente = document.createElement("idCliente");
                Text textidCliente = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getRvcIdentificacion());
                idCliente.appendChild(textidCliente);
                detalleVentas.appendChild(idCliente);

                if (!anxListaConsolidadoRetencionesVentasTO.getRvcTransaccion().equals("07")) {
                    Element parteRelVtas = document.createElement("parteRelVtas");
                    Text textparteRelVtas = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getRvcRelacionado().trim());
                    parteRelVtas.appendChild(textparteRelVtas);
                    detalleVentas.appendChild(parteRelVtas);
                }

                if (anxListaConsolidadoRetencionesVentasTO.getRvcTransaccion().compareTo("06") == 0) {
                    Element tipoCliente = document.createElement("tipoCliente");
                    Text texttipoCliente = document.createTextNode(validarNulo(anxListaConsolidadoRetencionesVentasTO.getRvcExtranjeroTipo(), "00"));
                    tipoCliente.appendChild(texttipoCliente);
                    detalleVentas.appendChild(tipoCliente);

                    Element denoCli = document.createElement("denoCli");
                    Text textdenoCli = document.createTextNode(validarNulo(anxListaConsolidadoRetencionesVentasTO.getRvcCliente(), ""));
                    denoCli.appendChild(textdenoCli);
                    detalleVentas.appendChild(denoCli);
                }

                Element tipoComprobante = document.createElement("tipoComprobante");
                Text texttipoComprobante = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getRvcComprobanteTipo());
                tipoComprobante.appendChild(texttipoComprobante);
                detalleVentas.appendChild(tipoComprobante);

                Element tipoEmision = document.createElement("tipoEmision");
                Text texttipoEmision = document.createTextNode(validarNulo(anxListaConsolidadoRetencionesVentasTO.getVenTipoEmision(), "F"));
                tipoEmision.appendChild(texttipoEmision);
                detalleVentas.appendChild(tipoEmision);

                Element numeroComprobantes = document.createElement("numeroComprobantes");
                Text textnumeroComprobantes = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getRvcNumeroRetenciones().toString());
                numeroComprobantes.appendChild(textnumeroComprobantes);
                detalleVentas.appendChild(numeroComprobantes);

                Element baseNoGraIva = document.createElement("baseNoGraIva");
                Text textbaseNoGraIva = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getRvcBaseNoObjetoIva().toString());
                baseNoGraIva.appendChild(textbaseNoGraIva);
                detalleVentas.appendChild(baseNoGraIva);

                Element baseImponible = document.createElement("baseImponible");
                Text texbaseImponible = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getRvcBase0().toString());
                baseImponible.appendChild(texbaseImponible);
                detalleVentas.appendChild(baseImponible);

                Element baseImpGrav = document.createElement("baseImpGrav");
                Text texbaseImpGrav = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getVenBaseImponible().toString());
                baseImpGrav.appendChild(texbaseImpGrav);
                detalleVentas.appendChild(baseImpGrav);

                Element montoIva = document.createElement("montoIva");
                Text texmontoIva = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getVenMontoIva().toString());
                montoIva.appendChild(texmontoIva);
                detalleVentas.appendChild(montoIva);

                Element montoIce = document.createElement("montoIce");
                Text texmontoIce = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getVenMontoIce().toString());
                montoIce.appendChild(texmontoIce);
                detalleVentas.appendChild(montoIce);

                Element valorRetIva = document.createElement("valorRetIva");
                Text texvalorRetIva = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoIva().toString());
                valorRetIva.appendChild(texvalorRetIva);
                detalleVentas.appendChild(valorRetIva);

                Element valorRetRenta = document.createElement("valorRetRenta");
                Text texvalorRetRenta = document.createTextNode(anxListaConsolidadoRetencionesVentasTO.getVenValorRetenidoRenta().toString());
                valorRetRenta.appendChild(texvalorRetRenta);
                detalleVentas.appendChild(valorRetRenta);

                if (anxListaConsolidadoRetencionesVentasTO.getRvcComprobanteTipo().compareTo("04") != 0) {
                    BigDecimal totalPagado = new java.math.BigDecimal("0.00");
                    BigDecimal pagadoEfectivo = new java.math.BigDecimal("0.00");
                    BigDecimal pagadoDineroElectronico = new java.math.BigDecimal("0.00");
                    BigDecimal pagadoTarjetaCredito = new java.math.BigDecimal("0.00");
                    BigDecimal pagadoOtros = new java.math.BigDecimal("0.00");

                    pagadoEfectivo = anxListaConsolidadoRetencionesVentasTO.getVenPagadoEfectivo() == null
                            ? new java.math.BigDecimal("0.00")
                            : anxListaConsolidadoRetencionesVentasTO.getVenPagadoEfectivo();
                    pagadoDineroElectronico = anxListaConsolidadoRetencionesVentasTO
                            .getVenPagadoDineroElectronico() == null ? new java.math.BigDecimal("0.00")
                                    : anxListaConsolidadoRetencionesVentasTO.getVenPagadoDineroElectronico();
                    pagadoTarjetaCredito = anxListaConsolidadoRetencionesVentasTO.getVenPagadoTarjetaCredito() == null
                            ? new java.math.BigDecimal("0.00")
                            : anxListaConsolidadoRetencionesVentasTO.getVenPagadoTarjetaCredito();
                    pagadoOtros = anxListaConsolidadoRetencionesVentasTO.getVenPagadoOtro() == null
                            ? new java.math.BigDecimal("0.00")
                            : anxListaConsolidadoRetencionesVentasTO.getVenPagadoOtro();
                    totalPagado = pagadoEfectivo
                            .add(pagadoDineroElectronico.add(pagadoTarjetaCredito.add(pagadoOtros)));

                    // 01/06/2016
                    Boolean boleano = null;
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

                    if (formatoFecha.parse(fechaDesde).getTime() > formatoFecha.parse("2016-05-31").getTime()) {
                        boleano = true;
                    } else {
                        boleano = false;
                    }

                    if (BigDecimal.ZERO.compareTo(totalPagado) != 0 && boleano) {
                        Element formasDePago = document.createElement("formasDePago");
                        Element formaPago;
                        Text texformaPago;

                        if (pagadoEfectivo.compareTo(BigDecimal.ZERO) != 0) {
                            formaPago = document.createElement("formaPago");
                            texformaPago = document.createTextNode("01");
                            formaPago.appendChild(texformaPago);
                            formasDePago.appendChild(formaPago);
                        }
                        if (pagadoDineroElectronico.compareTo(BigDecimal.ZERO) != 0) {
                            formaPago = document.createElement("formaPago");
                            texformaPago = document.createTextNode("17");
                            formaPago.appendChild(texformaPago);
                            formasDePago.appendChild(formaPago);
                        }
                        if (pagadoTarjetaCredito.compareTo(BigDecimal.ZERO) != 0) {
                            formaPago = document.createElement("formaPago");
                            texformaPago = document.createTextNode("19");
                            formaPago.appendChild(texformaPago);
                            formasDePago.appendChild(formaPago);
                        }
                        if (pagadoOtros.compareTo(BigDecimal.ZERO) != 0) {
                            formaPago = document.createElement("formaPago");
                            texformaPago = document.createTextNode("20");
                            formaPago.appendChild(texformaPago);
                            formasDePago.appendChild(formaPago);
                        }
                        detalleVentas.appendChild(formasDePago);
                    }
                }
                ventas.appendChild(detalleVentas);
                raiz.appendChild(ventas);
            }
            c++;
        }
        //VENTAS ESTABLECIMIENTO
        c = 0;
        for (AnxListaEstablecimientoRetencionesVentasTO anxListaEstablecimientoRetencionesVentasTO : anxListaRetenciones.getAnxListaEstablecimientoRetencionesVentasTOs()) {
            if (anxListaEstablecimientoRetencionesVentasTO.getEstablecimiento() != null) {
                ventaEst = document.createElement("ventaEst");
                Element codEstab = document.createElement("codEstab");
                Text textcodEstab = document.createTextNode(anxListaEstablecimientoRetencionesVentasTO.getEstablecimiento());
                codEstab.appendChild(textcodEstab);
                ventaEst.appendChild(codEstab);
                Element ventasEstab = document.createElement("ventasEstab");
                Text textventasEstab = document.createTextNode(anxListaEstablecimientoRetencionesVentasTO.getTotalVentas().toString());
                ventasEstab.appendChild(textventasEstab);
                ventaEst.appendChild(ventasEstab);
                //IVA COMP
                ventasEstablecimiento.appendChild(ventaEst);
                raiz.appendChild(ventasEstablecimiento);
            }
            c++;
        }
        //exportaciones
        c = 0;
        for (AnxListaVentaExportacionTO anxListaVentaExportacionTO : anxListaRetenciones.getAnxListaExportaciones()) {
            //
            detalleExportaciones = document.createElement("detalleExportaciones");

            if (anxListaVentaExportacionTO.getExpClienteIdTipo() != null) {
                Element elementTpIdClienteEx = document.createElement("tpIdClienteEx");
                Text textTpIdClienteEx = document.createTextNode(anxListaVentaExportacionTO.getExpClienteIdTipo());
                elementTpIdClienteEx.appendChild(textTpIdClienteEx);
                detalleExportaciones.appendChild(elementTpIdClienteEx);
            }
            //
            if (anxListaVentaExportacionTO.getExpClienteIdNumero() != null) {
                Element elementIdClienteEx = document.createElement("idClienteEx");
                Text textIdClienteEx = document.createTextNode(anxListaVentaExportacionTO.getExpClienteIdNumero());
                elementIdClienteEx.appendChild(textIdClienteEx);
                detalleExportaciones.appendChild(elementIdClienteEx);
            }
            //
            if (anxListaVentaExportacionTO.getExpClienteRelacionado() != null) {
                Element elementParteRel = document.createElement("parteRelExp");
                Text textParteRel = document.createTextNode(anxListaVentaExportacionTO.getExpClienteRelacionado());
                elementParteRel.appendChild(textParteRel);
                detalleExportaciones.appendChild(elementParteRel);
            }
            //
            if (anxListaVentaExportacionTO.getExpClienteTipo() != null) {
                Element elementTipoCli = document.createElement("tipoCli");
                Text textTipoCli = document.createTextNode(anxListaVentaExportacionTO.getExpClienteTipo() != null ? anxListaVentaExportacionTO.getExpClienteTipo() : "");
                elementTipoCli.appendChild(textTipoCli);
                detalleExportaciones.appendChild(elementTipoCli);
            }
            //
            if (anxListaVentaExportacionTO.getExpClienteRazonSocial() != null) {
                Element elementDenoExpCli = document.createElement("denoExpCli");
                Text textDenoExpCli = document.createTextNode(anxListaVentaExportacionTO.getExpClienteRazonSocial());
                elementDenoExpCli.appendChild(textDenoExpCli);
                detalleExportaciones.appendChild(elementDenoExpCli);
            }
            //
            if (anxListaVentaExportacionTO.getExpTipoRegimenFiscal() != null) {
                Element elementTipoRegi = document.createElement("tipoRegi");
                Text textTipoRegi = document.createTextNode(anxListaVentaExportacionTO.getExpTipoRegimenFiscal());
                elementTipoRegi.appendChild(textTipoRegi);
                detalleExportaciones.appendChild(elementTipoRegi);
            }
            //
            if (anxListaVentaExportacionTO.getExpRegimenGeneral() != null) {
                Element elementPaisEfecPagoGen = document.createElement("paisEfecPagoGen");
                Text textPaisEfecPagoGen = document.createTextNode(anxListaVentaExportacionTO.getExpRegimenGeneral());
                elementPaisEfecPagoGen.appendChild(textPaisEfecPagoGen);
                detalleExportaciones.appendChild(elementPaisEfecPagoGen);
            }
            //
            if (anxListaVentaExportacionTO.getExpPaisEfectuaExportacion() != null) {
                Element elementPaisEfecExp = document.createElement("paisEfecExp");
                Text textPaisEfecExp = document.createTextNode(anxListaVentaExportacionTO.getExpPaisEfectuaExportacion());
                elementPaisEfecExp.appendChild(textPaisEfecExp);
                detalleExportaciones.appendChild(elementPaisEfecExp);
            }
            //
            if (anxListaVentaExportacionTO.getExpTipoExportacion() != null) {
                Element elementExportacionDe = document.createElement("exportacionDe");
                Text textExportacionDe = document.createTextNode(anxListaVentaExportacionTO.getExpTipoExportacion());
                elementExportacionDe.appendChild(textExportacionDe);
                detalleExportaciones.appendChild(elementExportacionDe);
            }
            //
            if (anxListaVentaExportacionTO.getVtaDocumentoTipo() != null) {
                Element elementtipoComprobante = document.createElement("tipoComprobante");
                Text texttipoComprobante = document.createTextNode(anxListaVentaExportacionTO.getVtaDocumentoTipo());
                elementtipoComprobante.appendChild(texttipoComprobante);
                detalleExportaciones.appendChild(elementtipoComprobante);
            }
            //
            if (anxListaVentaExportacionTO.getExpRefrendoDistrito() != null) {
                Element elementdistAduanero = document.createElement("distAduanero");
                Text textdistAduanero = document.createTextNode(anxListaVentaExportacionTO.getExpRefrendoDistrito());
                elementdistAduanero.appendChild(textdistAduanero);
                detalleExportaciones.appendChild(elementdistAduanero);
            }
            //
            if (anxListaVentaExportacionTO.getExpRefrendoAnio() != null) {
                Element elementAnio = document.createElement("anio");
                Text textAniio = document.createTextNode(anxListaVentaExportacionTO.getExpRefrendoAnio());
                elementAnio.appendChild(textAniio);
                detalleExportaciones.appendChild(elementAnio);
            }
            //
            if (anxListaVentaExportacionTO.getExpRefrendoRegimen() != null) {
                Element elementregimen = document.createElement("regimen");
                Text textregimen = document.createTextNode(anxListaVentaExportacionTO.getExpRefrendoRegimen());
                elementregimen.appendChild(textregimen);
                detalleExportaciones.appendChild(elementregimen);
            }
            //
            if (anxListaVentaExportacionTO.getExpRefrendoCorrelativo() != null) {
                Element elementcorrelativo = document.createElement("correlativo");
                Text textcorrelativo = document.createTextNode(anxListaVentaExportacionTO.getExpRefrendoCorrelativo());
                elementcorrelativo.appendChild(textcorrelativo);
                detalleExportaciones.appendChild(elementcorrelativo);
            }
            //
            if (anxListaVentaExportacionTO.getExpRefrendoDocumentoTransporte() != null) {
                Element elementDocTransp = document.createElement("docTransp");
                Text textDocTransp = document.createTextNode(anxListaVentaExportacionTO.getExpRefrendoDocumentoTransporte());
                elementDocTransp.appendChild(textDocTransp);
                detalleExportaciones.appendChild(elementDocTransp);
            }
            //
            if (anxListaVentaExportacionTO.getExpFechaExportacion() != null) {
                Element elementfechaEmbarque = document.createElement("fechaEmbarque");
                Text textfechaEmbarque = document.createTextNode(UtilsValidacion.fecha(anxListaVentaExportacionTO.getExpFechaExportacion(), "yyyy-MM-dd", "dd/MM/yyyy"));
                elementfechaEmbarque.appendChild(textfechaEmbarque);
                detalleExportaciones.appendChild(elementfechaEmbarque);
            }
            //
            if (anxListaVentaExportacionTO.getExpValorFobExterior() != null) {
                Element elementValorFOB = document.createElement("valorFOB");
                Text textValorFOB = document.createTextNode(UtilsValidacion.redondeoDecimalBigDecimal(anxListaVentaExportacionTO.getExpValorFobExterior(), 2, java.math.RoundingMode.HALF_UP) + "");
                elementValorFOB.appendChild(textValorFOB);
                detalleExportaciones.appendChild(elementValorFOB);
            }
            //
            if (anxListaVentaExportacionTO.getExpValorFobLocal() != null) {
                Element elementValorFOBComprobante = document.createElement("valorFOBComprobante");
                Text textValorFOBComprobante = document.createTextNode(UtilsValidacion.redondeoDecimalBigDecimal(anxListaVentaExportacionTO.getExpValorFobLocal(), 2, java.math.RoundingMode.HALF_UP) + "");
                elementValorFOBComprobante.appendChild(textValorFOBComprobante);
                detalleExportaciones.appendChild(elementValorFOBComprobante);
            }
            //
            if (anxListaVentaExportacionTO.getVtaDocumentoNumero() != null) {
                Element elementEstablecimiento = document.createElement("establecimiento");
                Text textEstablecimiento = document.createTextNode(anxListaVentaExportacionTO.getVtaDocumentoNumero().substring(0, 3));
                elementEstablecimiento.appendChild(textEstablecimiento);
                detalleExportaciones.appendChild(elementEstablecimiento);
            }
            //
            Element elementPuntoEmision = document.createElement("puntoEmision");
            Text textPuntoEmision = document.createTextNode(anxListaVentaExportacionTO.getVtaDocumentoNumero().substring(4, 7));
            elementPuntoEmision.appendChild(textPuntoEmision);
            detalleExportaciones.appendChild(elementPuntoEmision);
            //
            Element elementSecuencial = document.createElement("secuencial");
            Text textSecuencial = document.createTextNode(anxListaVentaExportacionTO.getVtaDocumentoNumero().substring(8, anxListaVentaExportacionTO.getVtaDocumentoNumero().length()));
            elementSecuencial.appendChild(textSecuencial);
            detalleExportaciones.appendChild(elementSecuencial);
            //

            Element elementAutorizacion = document.createElement("autorizacion");
            Text textAutorizacion = document.createTextNode(
                    anxListaVentaExportacionTO.getVtaDocumentoAutorizacion() != null && !anxListaVentaExportacionTO.getVtaDocumentoAutorizacion().equals("")
                    ? anxListaVentaExportacionTO.getVtaDocumentoAutorizacion()
                    : "");
            elementAutorizacion.appendChild(textAutorizacion);
            detalleExportaciones.appendChild(elementAutorizacion);

            //
            if (anxListaVentaExportacionTO.getVtaFechaEmision() != null) {
                Element elementFechaEmision = document.createElement("fechaEmision");
                Text textFechaEmision = document.createTextNode(UtilsValidacion.fecha(anxListaVentaExportacionTO.getVtaFechaEmision(), "dd/MM/yyyy"));
                elementFechaEmision.appendChild(textFechaEmision);
                detalleExportaciones.appendChild(elementFechaEmision);
            }
            //
            /*
            Element elementIngextgravotropaís = document.createElement("ingextgravotropaís");
            Text textIngextgravotropaís = document.createTextNode(anxListaVentaExportacionTO.getExpImpuestoPagadoExterior() != null && anxListaVentaExportacionTO.getExpImpuestoPagadoExterior().compareTo(BigDecimal.ZERO) > 0 ? "SI" : "NO");
            elementIngextgravotropaís.appendChild(textIngextgravotropaís);
            detalleExportaciones.appendChild(elementIngextgravotropaís);
             */
            //
            if (anxListaVentaExportacionTO.getExpTipoIngresoExterior() != null) {
                Element elementTipIngExt = document.createElement("tipIngExt");
                Text textTipIngExt = document.createTextNode(anxListaVentaExportacionTO.getExpTipoIngresoExterior());
                elementTipIngExt.appendChild(textTipIngExt);
                detalleExportaciones.appendChild(elementTipIngExt);
            }

            //
            if (anxListaVentaExportacionTO.getExpParaisoFiscal() != null) {
                Element elementPaisEfecPagoParFis = document.createElement("paisEfecPagoParFis");
                Text textPaisEfecPagoParFis = document.createTextNode(anxListaVentaExportacionTO.getExpParaisoFiscal());
                elementPaisEfecPagoParFis.appendChild(textPaisEfecPagoParFis);
                detalleExportaciones.appendChild(elementPaisEfecPagoParFis);
            }
            //
            if (anxListaVentaExportacionTO.getExpRegimenFiscalPreferente() != null) {
                Element elementDenopagoRegFis = document.createElement("denopagoRegFis");
                Text textDenopagoRegFis = document.createTextNode(anxListaVentaExportacionTO.getExpRegimenFiscalPreferente());
                elementDenopagoRegFis.appendChild(textDenopagoRegFis);
                detalleExportaciones.appendChild(elementDenopagoRegFis);
            }

            //
            /*
            Element elementPagoRegFis = document.createElement("pagoRegFis");
            Text textPagoRegFis = document.createTextNode("NO");
            elementPagoRegFis.appendChild(textPagoRegFis);
            detalleExportaciones.appendChild(elementPagoRegFis);*/
            //
            /*
            if (anxListaVentaExportacionTO.getExpImpuestoPagadoExterior() != null) {
                Element elementImpuestootropaís = document.createElement("impuestootropaís");
                Text textImpuestootropaís = document.createTextNode(UtilsValidacion.redondeoDecimalBigDecimal(anxListaVentaExportacionTO.getExpImpuestoPagadoExterior(), 2, java.math.RoundingMode.HALF_UP) + "");
                elementImpuestootropaís.appendChild(textImpuestootropaís);
                detalleExportaciones.appendChild(elementImpuestootropaís);
            }*/
            exportaciones.appendChild(detalleExportaciones);
            raiz.appendChild(exportaciones);

            c++;
        }
        //anulados
        c = 0;
        for (AnxListaComprobanteAnuladoTO anxListaComprobanteAnuladoTO : anxListaRetenciones.getAnxListaComprobanteAnuladoTOs()) {
            if (anxListaComprobanteAnuladoTO.getAnuComprobanteTipo() != null) {
                detalleAnulados = document.createElement("detalleAnulados");
                Element tipoComprobante = document.createElement("tipoComprobante");
                Text texttipoComprobante = document.createTextNode(anxListaComprobanteAnuladoTO.getAnuComprobanteTipo());
                tipoComprobante.appendChild(texttipoComprobante);
                detalleAnulados.appendChild(tipoComprobante);

                Element establecimiento = document.createElement("establecimiento");
                Text textestablecimiento = document.createTextNode(anxListaComprobanteAnuladoTO.getAnuComprobanteEstablecimiento());
                establecimiento.appendChild(textestablecimiento);
                detalleAnulados.appendChild(establecimiento);

                Element puntoEmision = document.createElement("puntoEmision");
                Text textpuntoEmision = document.createTextNode(anxListaComprobanteAnuladoTO.getAnuComprobantePuntoEmision());
                puntoEmision.appendChild(textpuntoEmision);
                detalleAnulados.appendChild(puntoEmision);

                Element secuencialInicio = document.createElement("secuencialInicio");
                Text textsecuencialInicio = document.createTextNode(anxListaComprobanteAnuladoTO.getAnuSecuencialInicio());
                secuencialInicio.appendChild(textsecuencialInicio);
                detalleAnulados.appendChild(secuencialInicio);

                Element secuencialFin = document.createElement("secuencialFin");
                Text textsecuencialFin = document.createTextNode(anxListaComprobanteAnuladoTO.getAnuSecuencialFin());
                secuencialFin.appendChild(textsecuencialFin);
                detalleAnulados.appendChild(secuencialFin);

                Element autorizacion = document.createElement("autorizacion");
                Text textautorizacion = document.createTextNode(anxListaComprobanteAnuladoTO.getAnuAutorizacion());
                autorizacion.appendChild(textautorizacion);
                detalleAnulados.appendChild(autorizacion);

                anulados.appendChild(detalleAnulados);
                raiz.appendChild(anulados);

            }
            c++;
        }

        return document;
    }

    @Override
    public List<AnxRespuestasErroresPorNotasDeDebitoTO> validacionPorNotasDebito(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        List<AnxRespuestasErroresPorNotasDeDebitoTO> mensajes = new ArrayList<>();
        List<AnxListaRetencionesTO> retenciones = compraDao.getAnexoListaRetencionesTO(empresa, fechaDesde, fechaHasta);
        if (retenciones != null && !retenciones.isEmpty()) {
            for (AnxListaRetencionesTO anxListaRetencionesTO : retenciones) {
                if (anxListaRetencionesTO.getRetSustentoTributario() != null && !anxListaRetencionesTO.getRetSustentoTributario().isEmpty()) {
                    if (anxListaRetencionesTO.getRetDocumentoTipo().equals("05")) {
                        AnxRespuestasErroresPorNotasDeDebitoTO mensaje = new AnxRespuestasErroresPorNotasDeDebitoTO();
                        mensaje.setProvIdentificacion(anxListaRetencionesTO.getRetProveedorId());
                        mensaje.setProvNombre(anxListaRetencionesTO.getRetProveedorNombre());
                        mensaje.setDocumento(anxListaRetencionesTO.getRetDocumentoNumero());
                        mensaje.setRetencion(anxListaRetencionesTO.getRetRetencionNumero());
                        mensaje.setFechaEmision(anxListaRetencionesTO.getRetRetencionFecha());
                        mensaje.setSustentoTributario(anxListaRetencionesTO.getRetSustentoTributario());
                        mensaje.setDocumentoModificado(anxListaRetencionesTO.getRetModificadoDocumentonumero());
                        mensajes.add(mensaje);
                    }
                }
            }
        }
        return mensajes;
    }

    private String validarNulo(String textoOriginal, String textoPorDefecto) {
        return (textoOriginal == null ? textoPorDefecto : textoOriginal);
    }

    @Override
    public List<InputStream> generarRideRetencionesPendientes(String empresa, List<AnxListaRetencionesPendientesTO> listaRetenciones) throws Exception {
        List<InputStream> list = new ArrayList<>();
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaRetencionesPendientesTO itemRetencion : listaRetenciones) {
            List<String> comprobantes = UtilsValidacion.separarComprobante(itemRetencion.getRetLlaveCompra());
            String periodo = comprobantes.get(0);
            String motivo = comprobantes.get(1);
            String numero = comprobantes.get(2);
            String xmlAutorizacion = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, "reportComprobanteRetencionRide", empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, "reportComprobanteRetencionRide", empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.charAt(0) == 'T') {
                String ruta = archivoService.obtenerRutaDeReporteTemporal(rpta, claveAcceso + ".jrprint");
                list.add(new FileInputStream(archivoService.respondeServidorPDF(ruta)));
            }
        }
        return list;
    }

    @Override
    public List<InputStream> generarRideLiquidacionCompraPendientes(String empresa, List<AnxListaLiquidacionComprasPendientesTO> listado) throws Exception {
        List<InputStream> list = new ArrayList<>();
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaLiquidacionComprasPendientesTO item : listado) {
            String periodo = item.getCompPeriodo();
            String motivo = item.getCompMotivo();
            String numero = item.getCompNumero();
            String xmlAutorizacion = compraElectronicaService.getXmlLiquidacionCompras(empresa, periodo, motivo, numero);
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, "reportComprobanteLiquidacionCompraRide", empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, "reportComprobanteLiquidacionCompraRide", empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.charAt(0) == 'T') {
                String ruta = archivoService.obtenerRutaDeReporteTemporal(rpta, claveAcceso + ".jrprint");
                list.add(new FileInputStream(archivoService.respondeServidorPDF(ruta)));
            }
        }
        return list;
    }

    @Override
    public byte[] generarReporteComprobanteRetencion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa,
            List<AnxListaRetencionesTO> anxListaRetencionesTO, boolean sinDescripcionDocumentoSinProveedor, String reportComprobanteRetencion) throws Exception {

        List<ReporteObjetoAnexo> lista = new ArrayList();
        List<AnxTipoComprobanteComboTO> listaDocumentos = new ArrayList();
        InvProveedorTO proveedor = null;
        if (sinDescripcionDocumentoSinProveedor) {
            listaDocumentos = tipoComprobanteService.getListaAnxTipoComprobanteComboTOCompleto();
        }
        for (AnxListaRetencionesTO itemRetencion : anxListaRetencionesTO) {
            List<String> comprobantes = UtilsValidacion.separarComprobante(itemRetencion.getRetLlaveCompra());
            String periodo = comprobantes.get(0);
            String motivo = comprobantes.get(1);
            String numero = comprobantes.get(2);

            //Si no tiene descripcion de documento, por ejemplo en invcompra se guarda solo 01 y en consultas de retencion 01 - factura compra-->se quiere obtener (01 - factura compra , el detalle del documento)
            if (sinDescripcionDocumentoSinProveedor) {
                proveedor = proveedorService.getProveedorTO(empresa, itemRetencion.getRetProveedorId()).get(0);
                if (listaDocumentos != null && listaDocumentos.size() > 0) {
                    for (int i = 0; i < listaDocumentos.size(); i++) {
                        if (listaDocumentos.get(i).getTcCodigo().equals(itemRetencion.getRetDocumentoTipo())) {
                            itemRetencion.setRetDocumentoNombre(listaDocumentos.get(i).getTcDescripcion());
                            break;
                        }
                    }
                }
            }

            List<AnxCompraDetalleTO> anxCompraDetalleTOs = compraDetalleDao.getAnexoCompraDetalleTO(empresa, periodo, motivo, numero);
            AnxCompraTO anxCompraTO = compraService.getAnexoCompraTO(empresa, periodo, motivo, numero);

            anxCompraTO.setCompSustentotributario(llenarAnxSustentoTributario(anxCompraTO.getCompSustentotributario()));

            ReporteObjetoAnexo reporteObjetoAnexo;

            List<AnxTipoComprobanteComboTO> anexosBBTipoComprobante = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(null);
            String detalleTipoComprobanteEscogido = "";

            for (int i = 0; i < anexosBBTipoComprobante.size(); i++) {
                if (anexosBBTipoComprobante.get(i).getTcCodigo().equals(anxCompraTO.getCompModificadoDocumentoTipo())) {
                    detalleTipoComprobanteEscogido = anexosBBTipoComprobante.get(i).getTcDescripcion();
                    i = anexosBBTipoComprobante.size();
                }
            }
            anxCompraTO.setCompModificadoDocumentoTipo(detalleTipoComprobanteEscogido);

            for (int i = 0; i < anxCompraDetalleTOs.size(); i++) {
                reporteObjetoAnexo = new ReporteObjetoAnexo();
                reporteObjetoAnexo.setComAutorizacion(anxCompraTO.getCompAutorizacion());
                reporteObjetoAnexo.setComBase0(anxCompraTO.getCompBase0());
                reporteObjetoAnexo.setComBaseimponible(anxCompraTO.getCompBaseimponible());
                reporteObjetoAnexo.setComBasenoobjetoiva(anxCompraTO.getCompBasenoobjetoiva());
                reporteObjetoAnexo.setComEmisionAutorizacion(anxCompraTO.getCompEmision());
                reporteObjetoAnexo.setComCaduca(anxCompraTO.getCompCaduca());
                //////// CONCEPTO E INCLUSION DE RETENCIONES DE IVA
                reporteObjetoAnexo.setComConcepto(anxCompraDetalleTOs.get(i).getDetConcepto());
                reporteObjetoAnexo.setComConceptoNombre("RENTA");
                reporteObjetoAnexo.setComConceptoBase0(anxCompraDetalleTOs.get(i).getDetBase0());
                reporteObjetoAnexo.setComConceptoBaseImponible(anxCompraDetalleTOs.get(i).getDetBaseImponible());
                reporteObjetoAnexo.setComConceptoBaseNoObjetoIva(anxCompraDetalleTOs.get(i).getDetBaseNoObjetoIva());
                reporteObjetoAnexo.setComConceptoPorcentaje(anxCompraDetalleTOs.get(i).getDetPorcentaje());
                reporteObjetoAnexo.setComConceptoValorRetenido(anxCompraDetalleTOs.get(i).getDetValorRetenido());
                /////////////////////////////////////////
                reporteObjetoAnexo.setComModificadoautorizacion(anxCompraTO.getCompModificadoAutorizacion());
                reporteObjetoAnexo.setComModificadodocumento(anxCompraTO.getCompModificadoDocumentoNumero());
                reporteObjetoAnexo.setComModificadotipodocumento(anxCompraTO.getCompModificadoDocumentoTipo());
                reporteObjetoAnexo.setComMontoice(anxCompraTO.getCompMontoice());
                reporteObjetoAnexo.setComMontoiva(anxCompraTO.getCompMontoiva());
                reporteObjetoAnexo.setComRetencionautorizacion(anxCompraTO.getCompRetencionAutorizacion());
                reporteObjetoAnexo.setComRetencionfechaemision(anxCompraTO.getCompRetencionFechaEmision());
                reporteObjetoAnexo.setComRetencionnumero(anxCompraTO.getCompRetencionNumero());
                reporteObjetoAnexo.setComSustentotributario(anxCompraTO.getCompSustentotributario());
                reporteObjetoAnexo.setCompNumero(anxCompraTO.getCompNumero());

                reporteObjetoAnexo.setNombreProveedor(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvRazonSocial() : itemRetencion.getRetProveedorNombre());
                reporteObjetoAnexo.setDireccionProveedor(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvDireccion() : itemRetencion.getRetProveedorDireccion());
                reporteObjetoAnexo.setCiudad(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvCiudad() : itemRetencion.getRetProveedorCiudad());

                reporteObjetoAnexo.setRucCompra(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvId() : itemRetencion.getRetProveedorId());
                reporteObjetoAnexo.setNombreTipoDocumento(itemRetencion.getRetDocumentoNombre());
                reporteObjetoAnexo.setNumeroDocumentoCompra(itemRetencion.getRetDocumentoNumero());
                reporteObjetoAnexo.setFechaCompra(itemRetencion.getRetCompraFecha());

                lista.add(reporteObjetoAnexo);
            }
            if (anxCompraTO.getCompBaseivabienes().compareTo(BigDecimal.ZERO) > 0) {
                reporteObjetoAnexo = new ReporteObjetoAnexo();
                reporteObjetoAnexo.setComAutorizacion(anxCompraTO.getCompAutorizacion());
                reporteObjetoAnexo.setComBase0(anxCompraTO.getCompBase0());
                reporteObjetoAnexo.setComBaseimponible(anxCompraTO.getCompBaseimponible());
                reporteObjetoAnexo.setComBasenoobjetoiva(anxCompraTO.getCompBasenoobjetoiva());
                reporteObjetoAnexo.setComEmisionAutorizacion(anxCompraTO.getCompEmision());
                reporteObjetoAnexo.setComCaduca(anxCompraTO.getCompCaduca());
                //////// CONCEPTO E INCLUSION DE RETENCIONES DE IVA
                reporteObjetoAnexo.setComConcepto("");
                reporteObjetoAnexo.setComConceptoNombre("IVA");
                reporteObjetoAnexo.setComConceptoBase0(anxCompraTO.getCompBaseivabienes());
                reporteObjetoAnexo.setComConceptoBaseImponible(BigDecimal.ZERO);
                reporteObjetoAnexo.setComConceptoBaseNoObjetoIva(BigDecimal.ZERO);
                reporteObjetoAnexo.setComConceptoPorcentaje(anxCompraTO.getCompPorcentajebienes());
                reporteObjetoAnexo.setComConceptoValorRetenido(anxCompraTO.getCompValorbienes());
                /////////////////////////////////////////
                reporteObjetoAnexo.setComModificadoautorizacion(anxCompraTO.getCompModificadoAutorizacion());
                reporteObjetoAnexo.setComModificadodocumento(anxCompraTO.getCompModificadoDocumentoNumero());
                reporteObjetoAnexo.setComModificadotipodocumento(anxCompraTO.getCompModificadoDocumentoTipo());
                reporteObjetoAnexo.setComMontoice(anxCompraTO.getCompMontoice());
                reporteObjetoAnexo.setComMontoiva(anxCompraTO.getCompMontoiva());
                reporteObjetoAnexo.setComRetencionautorizacion(anxCompraTO.getCompRetencionAutorizacion());
                reporteObjetoAnexo.setComRetencionfechaemision(anxCompraTO.getCompRetencionFechaEmision());
                reporteObjetoAnexo.setComRetencionnumero(anxCompraTO.getCompRetencionNumero());
                reporteObjetoAnexo.setComSustentotributario(anxCompraTO.getCompSustentotributario());
                reporteObjetoAnexo.setCompNumero(anxCompraTO.getCompNumero());
                reporteObjetoAnexo.setNombreProveedor(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvRazonSocial() : itemRetencion.getRetProveedorNombre());
                reporteObjetoAnexo.setDireccionProveedor(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvDireccion() : itemRetencion.getRetProveedorDireccion());
                reporteObjetoAnexo.setCiudad(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvCiudad() : itemRetencion.getRetProveedorCiudad());
                reporteObjetoAnexo.setRucCompra(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvId() : itemRetencion.getRetProveedorId());
                reporteObjetoAnexo.setNombreTipoDocumento(itemRetencion.getRetDocumentoNombre());
                reporteObjetoAnexo.setNumeroDocumentoCompra(itemRetencion.getRetDocumentoNumero());
                reporteObjetoAnexo.setFechaCompra(itemRetencion.getRetCompraFecha());

                lista.add(reporteObjetoAnexo);
            }

            if (anxCompraTO.getCompBaseivaservicios().compareTo(BigDecimal.ZERO) > 0) {
                reporteObjetoAnexo = new ReporteObjetoAnexo();
                reporteObjetoAnexo.setComAutorizacion(anxCompraTO.getCompAutorizacion());
                reporteObjetoAnexo.setComBase0(anxCompraTO.getCompBase0());
                reporteObjetoAnexo.setComBaseimponible(anxCompraTO.getCompBaseimponible());
                reporteObjetoAnexo.setComBasenoobjetoiva(anxCompraTO.getCompBasenoobjetoiva());
                reporteObjetoAnexo.setComEmisionAutorizacion(anxCompraTO.getCompEmision());
                reporteObjetoAnexo.setComCaduca(anxCompraTO.getCompCaduca());
                //////// CONCEPTO E INCLUSION DE RETENCIONES DE IVA
                reporteObjetoAnexo.setComConcepto("");
                reporteObjetoAnexo.setComConceptoNombre("IVA");
                reporteObjetoAnexo.setComConceptoBase0(BigDecimal.ZERO);
                reporteObjetoAnexo.setComConceptoBaseImponible(anxCompraTO.getCompBaseivaservicios());
                reporteObjetoAnexo.setComConceptoBaseNoObjetoIva(BigDecimal.ZERO);
                reporteObjetoAnexo.setComConceptoPorcentaje(anxCompraTO.getCompPorcentajeservicios());
                reporteObjetoAnexo.setComConceptoValorRetenido(anxCompraTO.getCompValorservicios());
                /////////////////////////////////////////
                reporteObjetoAnexo.setComModificadoautorizacion(anxCompraTO.getCompModificadoAutorizacion());
                reporteObjetoAnexo.setComModificadodocumento(anxCompraTO.getCompModificadoDocumentoNumero());
                reporteObjetoAnexo.setComModificadotipodocumento(anxCompraTO.getCompModificadoDocumentoTipo());
                reporteObjetoAnexo.setComMontoice(anxCompraTO.getCompMontoice());
                reporteObjetoAnexo.setComMontoiva(anxCompraTO.getCompMontoiva());
                reporteObjetoAnexo.setComRetencionautorizacion(anxCompraTO.getCompRetencionAutorizacion());
                reporteObjetoAnexo.setComRetencionfechaemision(anxCompraTO.getCompRetencionFechaEmision());
                reporteObjetoAnexo.setComRetencionnumero(anxCompraTO.getCompRetencionNumero());
                reporteObjetoAnexo.setComSustentotributario(anxCompraTO.getCompSustentotributario());
                reporteObjetoAnexo.setCompNumero(anxCompraTO.getCompNumero());
                reporteObjetoAnexo.setNombreProveedor(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvRazonSocial() : itemRetencion.getRetProveedorNombre());
                reporteObjetoAnexo.setDireccionProveedor(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvDireccion() : itemRetencion.getRetProveedorDireccion());
                reporteObjetoAnexo.setCiudad(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvCiudad() : itemRetencion.getRetProveedorCiudad());
                reporteObjetoAnexo.setRucCompra(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvId() : itemRetencion.getRetProveedorId());
                reporteObjetoAnexo.setNombreTipoDocumento(itemRetencion.getRetDocumentoNombre());
                reporteObjetoAnexo.setNumeroDocumentoCompra(itemRetencion.getRetDocumentoNumero());
                reporteObjetoAnexo.setFechaCompra(itemRetencion.getRetCompraFecha());

                lista.add(reporteObjetoAnexo);
            }

            if (anxCompraTO.getCompBaseivaserviciosprofesionales().compareTo(BigDecimal.ZERO) > 0) {
                reporteObjetoAnexo = new ReporteObjetoAnexo();
                reporteObjetoAnexo.setComAutorizacion(anxCompraTO.getCompAutorizacion());
                reporteObjetoAnexo.setComBase0(anxCompraTO.getCompBase0());
                reporteObjetoAnexo.setComBaseimponible(anxCompraTO.getCompBaseimponible());
                reporteObjetoAnexo.setComBasenoobjetoiva(anxCompraTO.getCompBasenoobjetoiva());
                reporteObjetoAnexo.setComEmisionAutorizacion(anxCompraTO.getCompEmision());
                reporteObjetoAnexo.setComCaduca(anxCompraTO.getCompCaduca());
                //////// CONCEPTO E INCLUSION DE RETENCIONES DE IVA
                reporteObjetoAnexo.setComConcepto("");
                reporteObjetoAnexo.setComConceptoNombre("IVA");
                reporteObjetoAnexo.setComConceptoBase0(BigDecimal.ZERO);
                reporteObjetoAnexo.setComConceptoBaseImponible(BigDecimal.ZERO);
                reporteObjetoAnexo.setComConceptoBaseNoObjetoIva(anxCompraTO.getCompBaseivaserviciosprofesionales());
                reporteObjetoAnexo.setComConceptoPorcentaje(anxCompraTO.getCompPorcentajeserviciosprofesionales());
                reporteObjetoAnexo.setComConceptoValorRetenido(anxCompraTO.getCompValorserviciosprofesionales());
                /////////////////////////////////////////
                reporteObjetoAnexo.setComModificadoautorizacion(anxCompraTO.getCompModificadoAutorizacion());
                reporteObjetoAnexo.setComModificadodocumento(anxCompraTO.getCompModificadoDocumentoNumero());
                reporteObjetoAnexo.setComModificadotipodocumento(anxCompraTO.getCompModificadoDocumentoTipo());
                reporteObjetoAnexo.setComMontoice(anxCompraTO.getCompMontoice());
                reporteObjetoAnexo.setComMontoiva(anxCompraTO.getCompMontoiva());
                reporteObjetoAnexo.setComRetencionautorizacion(anxCompraTO.getCompRetencionAutorizacion());
                reporteObjetoAnexo.setComRetencionfechaemision(anxCompraTO.getCompRetencionFechaEmision());
                reporteObjetoAnexo.setComRetencionnumero(anxCompraTO.getCompRetencionNumero());
                reporteObjetoAnexo.setComSustentotributario(anxCompraTO.getCompSustentotributario());
                reporteObjetoAnexo.setCompNumero(anxCompraTO.getCompNumero());
                reporteObjetoAnexo.setNombreProveedor(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvRazonSocial() : itemRetencion.getRetProveedorNombre());
                reporteObjetoAnexo.setDireccionProveedor(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvDireccion() : itemRetencion.getRetProveedorDireccion());
                reporteObjetoAnexo.setCiudad(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvCiudad() : itemRetencion.getRetProveedorCiudad());
                reporteObjetoAnexo.setRucCompra(sinDescripcionDocumentoSinProveedor && proveedor != null ? proveedor.getProvId() : itemRetencion.getRetProveedorId());

                reporteObjetoAnexo.setNombreTipoDocumento(itemRetencion.getRetDocumentoNombre());
                reporteObjetoAnexo.setNumeroDocumentoCompra(itemRetencion.getRetDocumentoNumero());
                reporteObjetoAnexo.setFechaCompra(itemRetencion.getRetCompraFecha());

                lista.add(reporteObjetoAnexo);
            }
        }
        String nombreReporte = reportComprobanteRetencion != null && !reportComprobanteRetencion.equals("") ? reportComprobanteRetencion : "reportComprobanteRetencionPreliminar.jrxml";
        if (anxListaRetencionesTO != null && anxListaRetencionesTO.size() == 1) {
            if (anxListaRetencionesTO.get(0).getRetDocumentoTipo().equals("04") && anxListaRetencionesTO.get(0).getRetModificadoDocumentonumero() != null && !anxListaRetencionesTO.get(0).getRetModificadoDocumentonumero().equals("")) {
                nombreReporte = reportComprobanteRetencion != null && !reportComprobanteRetencion.equals("") ? reportComprobanteRetencion : "reportComprobanteRetencionPreliminarModificado.jrxml";
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    @Override
    public List<InputStream> imprimirRideGuiaRemisionPendientesElectronicas(String empresa, List<AnxListaGuiaRemisionPendientesTO> listaEnviar) throws Exception {
        List<InputStream> list = new ArrayList<>();
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaGuiaRemisionPendientesTO item : listaEnviar) {
            String periodo = item.getGuiaPeriodo();
            String numero = item.getGuiaNumero();
            String xmlAutorizacion = guiaElectronicaService.getXmlGuiaRemision(empresa, periodo, numero);
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, "reportComprobanteGuiaRemisionRide", empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, "reportComprobanteGuiaRemisionRide", empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.charAt(0) == 'T') {
                String ruta = archivoService.obtenerRutaDeReporteTemporal(rpta, claveAcceso + ".jrprint");
                list.add(new FileInputStream(archivoService.respondeServidorPDF(ruta)));
            }
        }
        return list;
    }

    public String llenarAnxSustentoTributario(String compSustentoTributario) throws Exception {

        List<AnxSustentoComboTO> anxSustentoComboTOs = sustentoDao.getListaAnxSustentoComboTO(null);
        for (int i = 0; i < anxSustentoComboTOs.size(); i++) {
            if (anxSustentoComboTOs.get(i).getSusCodigo().equals(compSustentoTributario)) {
                compSustentoTributario = anxSustentoComboTOs.get(i).toString();
                i = anxSustentoComboTOs.size();
            }
        }

        return compSustentoTributario;
    }

    //guias
    @Override
    public List<InputStream> generarReporteRideGuiaRemision(String empresa, List<AnxListaGuiaRemisionElectronicaTO> listaAnxListaGuiaRemisionElectronicaTO) throws Exception {
        List<InputStream> list = new ArrayList<>();
        String ruta;
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaGuiaRemisionElectronicaTO item : listaAnxListaGuiaRemisionElectronicaTO) {
            String nombreReporte = "reportComprobanteGuiaRemisionRide";
            String xmlAutorizacion = guiaElectronicaService.getXmlGuiaRemision(empresa, item.getGuiaPeriodo(), item.getGuiaNumero());
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, nombreReporte, empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, nombreReporte, empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.charAt(0) == 'T') {
                ruta = archivoService.obtenerRutaDeReporteTemporal(rpta, claveAcceso + ".jrprint");
                list.add(new FileInputStream(archivoService.respondeServidorPDF(ruta)));
            }
        }
        return list;
    }

    @Override
    public byte[] generarReporteRideGuiaRemisionMatricial(String empresa, List<AnxListaGuiaRemisionElectronicaTO> listaAnxListaGuiaRemisionElectronicaTO) throws Exception {
        byte[] resultado = null;
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaGuiaRemisionElectronicaTO item : listaAnxListaGuiaRemisionElectronicaTO) {
            String nombreReporte = "reportComprobanteGuiaRemisionRide";
            String xmlAutorizacion = guiaElectronicaService.getXmlGuiaRemision(empresa, item.getGuiaPeriodo(), item.getGuiaNumero());
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, nombreReporte, empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, nombreReporte, empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.charAt(0) == 'T') {
                resultado = ArrayUtils.addAll(resultado, rpta);
            }
        }
        return resultado;
    }

    //liquidacion compras
    @Override
    public List<InputStream> generarReporteRideLiquidacionCompra(String empresa, List<AnxListaLiquidacionComprasElectronicaTO> listado) throws Exception {
        List<InputStream> list = new ArrayList<>();
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaLiquidacionComprasElectronicaTO item : listado) {
            String periodo = item.getCompPeriodo();
            String motivo = item.getCompMotivo();
            String numero = item.getCompNumero();
            String xmlAutorizacion = compraElectronicaService.getXmlLiquidacionCompras(empresa, periodo, motivo, numero);
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, "reportComprobanteLiquidacionCompraRide", empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, "reportComprobanteLiquidacionCompraRide", empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.charAt(0) == 'T') {
                String ruta = archivoService.obtenerRutaDeReporteTemporal(rpta, claveAcceso + ".jrprint");
                list.add(new FileInputStream(archivoService.respondeServidorPDF(ruta)));
            }
        }
        return list;
    }

    @Override
    public byte[] generarRideLiquidacionCompraElectronicasEmitidasMatricial(String empresa, List<AnxListaLiquidacionComprasElectronicaTO> listado) throws Exception {
        byte[] resultado = null;
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        for (AnxListaLiquidacionComprasElectronicaTO item : listado) {
            String nombreReporte = "reportComprobanteLiquidacionCompraRide";
            String periodo = item.getCompPeriodo();
            String motivo = item.getCompMotivo();
            String numero = item.getCompNumero();
            String xmlAutorizacion = compraElectronicaService.getXmlLiquidacionCompras(empresa, periodo, motivo, numero);
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensaje = reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, nombreReporte, empresaParametros.getParRutaReportes());
            byte[] rpta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, nombreReporte, empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
            if (mensaje.charAt(0) == 'T') {
                resultado = ArrayUtils.addAll(resultado, rpta);
            }
        }
        return resultado;
    }

    @Override
    public byte[] generarReporteLiquidacionComprasEmitidas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaLiquidacionComprasElectronicaTO> listado, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listado);

    }

    @Override
    public Map<String, Object> exportarReporteAnxListaVentaExportacionTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxListaVentaExportacionTO> listaAnxListaVentaExportacionTO, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de exportaciones");
            listaCabecera.add("S");
            listaCuerpo.add("SN. Identificación" + "¬" + "SCliente" + "¬" + "SDocumento" + "¬" + "SAutorización" + "¬" + "SFecha emisión" + "¬" + "SValor FOB" + "¬" + "SValor FOB local"
                    + "¬" + "SImpuesto"
                    + "¬" + "SPaís efectuado exportación"
                    + "¬" + "SParaiso fiscal"
                    + "¬" + "SReferendo anio"
                    + "¬" + "SReferendo correlativo"
                    + "¬" + "SReferendo distrito"
                    + "¬" + "SRefrendo documento transporte"
                    + "¬" + "SReferendo regimen"
                    + "¬" + "SRegimen fiscal preferente"
                    + "¬" + "SRegimen general"
                    + "¬" + "STipo exportación"
                    + "¬" + "STipo ingreso exterior"
                    + "¬" + "STipo regimen fiscal");
            for (AnxListaVentaExportacionTO nota : listaAnxListaVentaExportacionTO) {
                listaCuerpo.add((nota.getExpClienteIdNumero() == null ? "B"
                        : "S" + nota.getExpClienteIdNumero())
                        + "¬"
                        + (nota.getExpClienteRazonSocial() == null ? "B"
                        : "S" + nota.getExpClienteRazonSocial())
                        + "¬"
                        + (nota.getVtaDocumentoNumero() == null ? "B"
                        : "S" + nota.getVtaDocumentoNumero())
                        + "¬"
                        + (nota.getVtaDocumentoAutorizacion() == null ? "B"
                        : "S" + nota.getVtaDocumentoAutorizacion())
                        + "¬"
                        + (nota.getVtaFechaEmision() == null ? "B"
                        : "S" + UtilsValidacion.fecha(nota.getVtaFechaEmision(), "dd/MM/yyyy"))
                        + "¬"
                        + (nota.getExpValorFobExterior() == null ? "B"
                        : "D" + nota.getExpValorFobExterior())
                        + "¬"
                        + (nota.getExpValorFobLocal() == null ? "B"
                        : "D" + nota.getExpValorFobLocal())
                        + "¬"
                        + (nota.getExpImpuestoPagadoExterior() == null ? "B"
                        : "D" + nota.getExpImpuestoPagadoExterior())
                        + "¬"
                        + (nota.getExpPaisEfectuaExportacion() == null ? "B"
                        : "S" + nota.getExpPaisEfectuaExportacion())
                        + "¬"
                        + (nota.getExpParaisoFiscal() == null ? "B"
                        : "S" + nota.getExpParaisoFiscal())
                        + "¬"
                        + (nota.getExpRefrendoAnio() == null ? "B"
                        : "S" + nota.getExpRefrendoAnio())
                        + "¬"
                        + (nota.getExpRefrendoCorrelativo() == null ? "B"
                        : "S" + nota.getExpRefrendoCorrelativo())
                        + "¬"
                        + (nota.getExpRefrendoDistrito() == null ? "B"
                        : "S" + nota.getExpRefrendoDistrito())
                        + "¬"
                        + (nota.getExpRefrendoDocumentoTransporte() == null ? "B"
                        : "S" + nota.getExpRefrendoDocumentoTransporte())
                        + "¬"
                        + (nota.getExpRefrendoRegimen() == null ? "B"
                        : "S" + nota.getExpRefrendoRegimen())
                        + "¬"
                        + (nota.getExpRegimenFiscalPreferente() == null ? "B"
                        : "S" + nota.getExpRegimenFiscalPreferente())
                        + "¬"
                        + (nota.getExpRegimenGeneral() == null ? "B"
                        : "S" + nota.getExpRegimenGeneral())
                        + "¬"
                        + (nota.getExpTipoExportacion() == null ? "B"
                        : "S" + nota.getExpTipoExportacion())
                        + "¬"
                        + (nota.getExpTipoIngresoExterior() == null ? "B"
                        : "S" + nota.getExpTipoIngresoExterior())
                        + "¬"
                        + (nota.getExpTipoRegimenFiscal() == null ? "B"
                        : "S" + nota.getExpTipoRegimenFiscal())
                );
            }
            listaCuerpo.add("S");
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteListadoAnxRetencionCompraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<AnxRetencionCompraTO> listaAnxRetencionCompraTO) throws Exception {

        List<ReporteAnxRetencionCompraTO> lista = new ArrayList<ReporteAnxRetencionCompraTO>();
        for (AnxRetencionCompraTO item : listaAnxRetencionCompraTO) {
            ReporteAnxRetencionCompraTO itemReporte = new ReporteAnxRetencionCompraTO();
            itemReporte.setFechaDesde(fechaDesde);
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setRetCompAutorizacion(item.getRetCompAutorizacion() == null ? "" : item.getRetCompAutorizacion());
            itemReporte.setRetCompBase0(item.getRetCompBase0());
            itemReporte.setRetCompBaseImponible(item.getRetCompBaseImponible());

            itemReporte.setRetCompDocumento(item.getRetCompDocumento());
            itemReporte.setRetCompDocumentoNumero(item.getRetCompDocumentoNumero());
            itemReporte.setRetCompFecha(item.getRetCompFecha());
            itemReporte.setRetCompFormaPago(item.getRetCompFormaPago());
            itemReporte.setRetCompMontoIva(item.getRetCompMontoIva());
            itemReporte.setRetCompObservaciones(item.getRetCompObservaciones());
            itemReporte.setRetCompRetencionAutorizacion(item.getRetCompRetencionAutorizacion());
            itemReporte.setRetCompRetencionFechaEmision(item.getRetCompRetencionFechaEmision());
            itemReporte.setRetCompRetencionNumero(item.getRetCompRetencionNumero());
            itemReporte.setRetCompTotal(item.getRetCompTotal());
            itemReporte.setRetCompra(item.getRetCompra());
            itemReporte.setRetProvIdNumero(item.getRetProvIdNumero());
            itemReporte.setRetProvRazonSocial(item.getRetProvRazonSocial());
            itemReporte.setRetSustentoTributario(item.getRetSustentoTributario());
            itemReporte.setRetTcDescripcion(item.getRetTcDescripcion());
            itemReporte.setRetTotalRetenido(item.getRetTotalRetenido());
            itemReporte.setRetValorRetenido(item.getRetValorRetenido());
            itemReporte.setRetValorRetenidoIr(item.getRetValorRetenidoIr());
            itemReporte.setRetValorRetenidoIva(item.getRetValorRetenidoIva());
            itemReporte.setRetValorSoportar(item.getRetValorSoportar());
            itemReporte.setRetValorSoportarChequeDeposito(item.getRetValorSoportarChequeDeposito());
            lista.add(itemReporte);

        }
        return genericReporteService.generarReporte(modulo, "reportListadoAnxRetencionCompraTO.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteListadoAnxRetencionVentaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<AnxRetencionVentaTO> listado) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("fechaDesde", fechaDesde);
        parametros.put("fechaHasta", fechaHasta);
        return genericReporteService.generarReporte(modulo, "reportListadoAnxRetencionVentaTO.jrxml", usuarioEmpresaReporteTO, parametros, listado);
    }

    @Override
    public Map<String, Object> exportarReporteListadoAnxRetencionVentaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRetencionVentaTO> lista, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SRetenciones Compras ");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SEstado¬" + "SID¬" + "SCliente¬" + "SDocumento Nombre¬" + "SNúmero Dcto.¬" + "SForma De Pago¬" + "SObservacion¬"
                    + "SDcto. Autorización¬" + "SFecha Venta¬" + "SVenta Base 0¬" + "SVenta Base Imp.¬" + "SVenta ICE¬" + "SMonto IVA¬" + "STotal¬"
                    + "SNúmero Ret. Autori.¬" + "SNúmero Ret.¬" + "SFecha Ret.¬" + "SValor Retenido IR¬" + "SValor Retenido IVA¬" + "STotal Retenido");
            for (AnxRetencionVentaTO anxListaRetencionesFuenteIvaTO : lista) {
                listaCuerpo.add(
                        (anxListaRetencionesFuenteIvaTO.getRetVtaEstado() == null ? "B"
                        : anxListaRetencionesFuenteIvaTO.getRetVtaEstado() == false ? "B" : "SPendiente")
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCliIdNumero() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCliIdNumero())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCliRazonSocial() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCliRazonSocial())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetTcDescripcion() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetTcDescripcion())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaDocumentoNumero() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetVtaDocumentoNumero())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaFormaPago() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetVtaFormaPago())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaObservaciones() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetVtaObservaciones())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetVtaAutorizacion())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaFecha() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetVtaFecha())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaBase0() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetVtaBase0())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaBaseImponible() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetVtaBaseImponible())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetIce() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetIce())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaMontoIva() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetVtaMontoIva())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaTotal() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetVtaTotal())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaRetencionAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetVtaRetencionAutorizacion())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaRetencionNumero() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetVtaRetencionNumero())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetVtaRetencionFechaEmision() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetVtaRetencionFechaEmision())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIr() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIr())
                        + "¬" + (anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIva() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIva())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetTotalRetenido() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetTotalRetenido())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteListadoAnxRetencionCompraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRetencionCompraTO> lista, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SRetenciones Compras ");
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");
            listaCuerpo.add("SEstado¬" + "SSust. Trib.¬" + "SID¬" + "SProveedor¬" + "SDcto. Nombre¬" + "SNúmero Dcto.¬" + "SForma Pago¬" + "SDocumento¬" + "SObservaciones¬"
                    + "SFecha Autorización Compra¬" + "SDcto. Autorización¬" + "SFecha Comp.¬" + "SFecha caduca Comp.¬" + "SCompra Base 0¬" + "SCompra Base Imp.¬" + "SCompra ICE¬" + "SCompra Monto IVA¬" + "STotal¬"
                    + "SNúmero Ret. Autori.¬" + "SNúmero Ret.¬" + "SFecha Ret.¬" + "SValor Retenido IR¬" + "SValor Retenido IVA¬" + "ST Retenido");
            for (AnxRetencionCompraTO anxListaRetencionesFuenteIvaTO : lista) {
                listaCuerpo.add(
                        (anxListaRetencionesFuenteIvaTO.getRetCompEstado() == null ? "B"
                        : anxListaRetencionesFuenteIvaTO.getRetCompEstado() == false ? "B" : "SPendiente")
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetSustentoTributario() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetSustentoTributario())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetProvIdNumero() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetProvIdNumero())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetProvRazonSocial() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetProvRazonSocial())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetTcDescripcion() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetTcDescripcion())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompDocumentoNumero() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompDocumentoNumero())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompFormaPago() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompFormaPago())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompDocumento() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompDocumento())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompObservaciones() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompObservaciones())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompFechaAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompFechaAutorizacion())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompAutorizacion())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompFecha() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompFecha())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompFechaCaduca() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompFechaCaduca())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompBase0() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetCompBase0())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompBaseImponible() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetCompBaseImponible())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetIce() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetIce())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompMontoIva() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetCompMontoIva())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompTotal() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetCompTotal())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompRetencionAutorizacion() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompRetencionAutorizacion())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompRetencionNumero() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompRetencionNumero())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetCompRetencionFechaEmision() == null ? "B"
                        : "S" + anxListaRetencionesFuenteIvaTO.getRetCompRetencionFechaEmision())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIr() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIr())
                        + "¬" + (anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIva() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetValorRetenidoIva())
                        + "¬"
                        + (anxListaRetencionesFuenteIvaTO.getRetTotalRetenido() == null ? "B"
                        : "D" + anxListaRetencionesFuenteIvaTO.getRetTotalRetenido()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteDiferenciasTributacion(Map<String, Object> map) throws Exception {
        try {
            UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
            List<AnxDiferenciasTributacionTO> listado = UtilsJSON.jsonToList(AnxDiferenciasTributacionTO.class, map.get("listado"));
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SDiferencias en Tributacion ");
            listaCabecera.add("S");
            listaCuerpo.add("SCompra¬" + "SFecha Compra¬" + "SNúmero Retención¬" + "SFecha Retención¬" + "SProveedor¬");
            for (AnxDiferenciasTributacionTO item : listado) {
                listaCuerpo.add((item.getRetCompNumero() == null ? "B"
                        : "S" + (item.getRetCompPeriodo() + " | " + item.getRetCompMotivo() + " | " + item.getRetCompNumero()))
                        + "¬"
                        + (item.getRetCompFecha() == null ? "B"
                        : "S" + item.getRetCompFecha())
                        + "¬"
                        + (item.getRetRetencionNumero() == null ? "B"
                        : "S" + item.getRetRetencionNumero())
                        + "¬"
                        + (item.getRetFechaEmision() == null ? "B"
                        : "S" + item.getRetFechaEmision())
                        + "¬"
                        + (item.getRetProvCodigo() == null ? "B"
                        : "S" + item.getRetProvCodigo())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarRetencionesComprobantesVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxRetencionesVenta> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SRetenciones Electrónicas Emitidas");
            listaCabecera.add("S");
            listaCuerpo.add("SId" + "¬" + "SRazon Social" + "¬" + "SNumero documento" + "¬" + "SRetencion Numero" + "¬" + "SClave acceso" + "¬" + "SV. R. IR" + "¬" + "SV. Retención IVA");
            for (AnxRetencionesVenta anxRetencionesVenta : listado) {
                listaCuerpo.add((anxRetencionesVenta.getCliIdentificacion() == null ? "B" : "S" + anxRetencionesVenta.getCliIdentificacion())
                        + "¬"
                        + (anxRetencionesVenta.getCliRazonSocial() == null ? "B" : "S" + anxRetencionesVenta.getCliRazonSocial())
                        + "¬"
                        + (anxRetencionesVenta.getRetNumDocSustento() == null ? "B" : "S" + anxRetencionesVenta.getRetNumDocSustento())
                        + "¬"
                        + (anxRetencionesVenta.getRetNumeroDocumento() == null ? "B" : "S" + anxRetencionesVenta.getRetNumeroDocumento())
                        + "¬"
                        + (anxRetencionesVenta.getRetClaveAcceso() == null ? "B" : "S" + anxRetencionesVenta.getRetClaveAcceso())
                        + "¬"
                        + (anxRetencionesVenta.getRetValorRetencionIr() == null ? "B" : "S" + anxRetencionesVenta.getRetValorRetencionIr())
                        + "¬"
                        + (anxRetencionesVenta.getRetValorRetencionIva() == null ? "B" : "S" + anxRetencionesVenta.getRetValorRetencionIva())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarComprobantesRecibidos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxComprobantesElectronicosRecibidos> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SComprobantes Electronicos Recibidos");
            listaCabecera.add("S");
            listaCuerpo.add("SDocumento" + "¬" + "SSerie Comprobante" + "¬" + "SRUC emisor" + "¬" + "SRazon social emisor" + "¬" + "SFecha emisión" + "¬" + "SFecha autorización" + "¬" + "SClave acceso" + "¬" + "SImporte total");
            for (AnxComprobantesElectronicosRecibidos anxComprobantesElectronicosRecibidos : listado) {
                listaCuerpo.add((anxComprobantesElectronicosRecibidos.getComproComprobante() == null ? "B" : "S" + anxComprobantesElectronicosRecibidos.getComproComprobante())
                        + "¬"
                        + (anxComprobantesElectronicosRecibidos.getComproSerieComprobante() == null ? "B" : "S" + anxComprobantesElectronicosRecibidos.getComproSerieComprobante())
                        + "¬"
                        + (anxComprobantesElectronicosRecibidos.getComproRucEmisor() == null ? "B" : "S" + anxComprobantesElectronicosRecibidos.getComproRucEmisor())
                        + "¬"
                        + (anxComprobantesElectronicosRecibidos.getComproRazonSocialEmisor() == null ? "B" : "S" + anxComprobantesElectronicosRecibidos.getComproRazonSocialEmisor())
                        + "¬"
                        + (UtilsDate.fechaFormatoString(anxComprobantesElectronicosRecibidos.getComproFechaEmision()) == null ? "B" : "S" + UtilsDate.fechaFormatoString(anxComprobantesElectronicosRecibidos.getComproFechaEmision()))
                        + "¬"
                        + (UtilsDate.fechaFormatoString(anxComprobantesElectronicosRecibidos.getComproFechaAutorizacion()) == null ? "B" : "S" + UtilsDate.fechaFormatoString(anxComprobantesElectronicosRecibidos.getComproFechaAutorizacion()))
                        + "¬"
                        + (anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso() == null ? "B" : "S" + anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso())
                        + "¬"
                        + (anxComprobantesElectronicosRecibidos.getComproImporteTotal() == null ? "B" : "D" + anxComprobantesElectronicosRecibidos.getComproImporteTotal())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

}
