package ec.com.todocompu.ShrimpSoftServer.anexos.report;

import com.thoughtworks.xstream.XStream;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.UrlWebServicesService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProveedorService;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.facturareembolso.FacturaReembolso;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.DetallesAdicionalesReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.DetallesReembolsoReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.FacturaReembolsoReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.ArchivoUtils;
import java.io.File;

import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.GenerarComprobantePdf;
import ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.XStreamUtil;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.Autorizacion;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Service
public class ReporteComprobanteElectronicoServiceImpl implements ReporteComprobanteElectronicoService {

    @Autowired
    private UrlWebServicesService urlWebServicesService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private ProveedorService proveedorService;

    @Override
    public List<InputStream> generarReporteComprobanteElectronicoLote(List<ComboGenericoTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<InputStream> list = new ArrayList<>();
        String ruta;
        //clave: clave de acceso  y valor: xml decodificado
        for (ComboGenericoTO item : listado) {
            String claveAcceso = item.getClave();
            String xmlAutorizacion = null;
            if (item.getValor() == null || item.getValor().equals("")) {
                RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(claveAcceso, "2", sisInfoTO);
                for (Autorizacion autorizacion : respuestaComprobante.getAutorizaciones().getAutorizacion()) {
                    if (autorizacion.getEstado().equalsIgnoreCase("AUTORIZADO")) {
                        if (!autorizacion.getComprobante().substring(0, 9).trim().equals("<![CDATA[")) {
                            autorizacion.setComprobante("<![CDATA[" + autorizacion.getComprobante() + "]]>");
                        }
                        XStream xstream = XStreamUtil.getRespuestaXStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
                        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                        xstream.toXML(autorizacion, writer);
                        xmlAutorizacion = outputStream.toString("UTF-8");
                    }
                }
            } else {
                if (item.getValor() != null) {
                    xmlAutorizacion = item.getValor();
                }
            }
            if (xmlAutorizacion != null) {
                byte[] rpta = generarReporteComprobanteElectronico(sisInfoTO.getEmpresa(), claveAcceso, xmlAutorizacion, "GENERICO", "", usuarioEmpresaReporteTO.getEmpRutaLogo());
                ruta = archivoService.obtenerRutaDeReporteTemporal(rpta, claveAcceso + ".jrprint");
                list.add(new FileInputStream(archivoService.respondeServidorPDF(ruta)));
            }
        }
        return list;
    }

    @Override
    public byte[] generarReporteComprobanteElectronico(String empresa, String claveAcceso, String XmlString, String nombreReporteJasper, String rutaPersonalizada, String rutaLogo) throws Exception {

        if (XmlString != null && XmlString.contains("reembolsos")) {
            Node contenidoComprobanteXm;
            Document doc;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new ByteArrayInputStream(XmlString.getBytes("UTF-8")));
            contenidoComprobanteXm = doc.getElementsByTagName("comprobante").item(0);
            FacturaReembolsoReporte reporte = new FacturaReembolsoReporte((FacturaReembolso) ArchivoUtils.unmarshal(FacturaReembolso.class, contenidoComprobanteXm));
            JRDataSource dataSource = null;
            if (reporte != null) {
                List<DetallesAdicionalesReporte> detallesAdiciones = new ArrayList();
                for (FacturaReembolso.Detalles.Detalle det : reporte.getFacturaReembolso().getDetalles().getDetalle()) {
                    DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
                    detAd.setCodigoPrincipal(det.getCodigoPrincipal());
                    detAd.setCodigoAuxiliar(det.getCodigoAuxiliar());
                    detAd.setDescripcion(det.getDescripcion());
                    detAd.setCantidad(det.getCantidad().toPlainString());
                    detAd.setPrecioTotalSinImpuesto(det.getPrecioTotalSinImpuesto().toString());
                    detAd.setPrecioUnitario(det.getPrecioUnitario().toString());
                    if (det.getDescuento() != null) {
                        detAd.setDescuento(det.getDescuento().toString());
                    }
                    int i = 0;
                    if ((det.getDetallesAdicionales() != null) && (det.getDetallesAdicionales().getDetAdicional() != null)
                            && (!det.getDetallesAdicionales().getDetAdicional().isEmpty())) {
                        for (FacturaReembolso.Detalles.Detalle.DetallesAdicionales.DetAdicional detAdicional : det
                                .getDetallesAdicionales().getDetAdicional()) {
                            if (i == 0) {
                                detAd.setDetalle1(detAdicional.getValor());
                            }
                            if (i == 1) {
                                detAd.setDetalle2(detAdicional.getValor());
                            }
                            if (i == 2) {
                                detAd.setDetalle3(detAdicional.getValor());
                            }
                            i++;
                        }
                    }
                    detAd.setInfoAdicional(reporte.getInfoAdicional());
                    detAd.setFormasPago(reporte.getFormasPago());
                    detallesAdiciones.add(detAd);
                }
                reporte.setDetallesAdiciones(detallesAdiciones);
                //DETALLE REEMBOLSO
                List<DetallesReembolsoReporte> detallesReembolso = new ArrayList();
                for (FacturaReembolso.Reembolsos.ReembolsoDetalle det : reporte.getFacturaReembolso().getReembolsos().getReembolsoDetalle()) {
                    DetallesReembolsoReporte detAd = new DetallesReembolsoReporte();
                    detAd.setDocumento(det.getEstabDocReembolso() + '-' + det.getPtoEmiDocReembolso() + '-' + det.getSecuencialDocReembolso());
                    detAd.setFechaEmision(det.getFechaEmisionDocReembolso());
                    detAd.setIdentificacionProveedor(det.getIdentificacionProveedorReembolso());
                    // llenar datos de proveedor en reembolsos
                    InvProveedorTO invProveedorTO = proveedorService.getBuscaCedulaProveedorTO(empresa, detAd.getIdentificacionProveedor());
                    if (invProveedorTO != null) {
                        detAd.setNombreProveedor(invProveedorTO.getProvRazonSocial());
                    }

                    if (det.getDetalleImpuestos() != null) {
                        for (FacturaReembolso.Reembolsos.ReembolsoDetalle.DetalleImpuestos.DetalleImpuesto det1 : det.getDetalleImpuestos().getDetalleImpuesto()) {
                            if (det1.getCodigo().compareTo("2") == 0) {
                                if (det1.getTarifa().compareTo(BigDecimal.ZERO) == 0) {
                                    detAd.setSub0(det1.getBaseImponibleReembolso());
                                } else {
                                    detAd.setSubIva(det1.getBaseImponibleReembolso());
                                    detAd.setIva(det1.getImpuestoReembolso());
                                }
                            }
                        }
                    }

                    detAd.setTotal(detAd.getIva().add(detAd.getSub0().add(detAd.getSubIva())));
                    detallesReembolso.add(detAd);
                }
                reporte.setDetallesReembolso(detallesReembolso);

                dataSource = new JRBeanCollectionDataSource(reporte.getDetallesAdiciones());//aqui se llenan datos segun xml
            }
            return GenerarComprobantePdf.generarReporteComprobanteElectronicoPDF(empresa, claveAcceso, XmlString, nombreReporteJasper, rutaPersonalizada, rutaLogo, reporte, dataSource);
        } else {
            return GenerarComprobantePdf.generarReporteComprobanteElectronicoPDF(empresa, claveAcceso, XmlString, nombreReporteJasper, rutaPersonalizada, rutaLogo, null, null);
        }

    }

    @Override
    public String validarExistenciaReportesElectronicos(String empresa, String claveAcceso, String nombreReporteJasper, String rutaPersonalizada) throws Exception {
        String tipoComprobante = claveAcceso.substring(8, 10);
        String rutaReportes;
        if (nombreReporteJasper.compareTo("GENERICO") == 0) {
            rutaReportes = UtilsArchivos.getRutaReportesRide("generico");
        } else {
            rutaReportes = UtilsArchivos.getRutaReportesRide(rutaPersonalizada);
        }

        if (tipoComprobante.compareTo("01") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteFacturaRideGenerico" : nombreReporteJasper;
        } else if (tipoComprobante.compareTo("03") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteLiquidacionCompraRideGenerico" : nombreReporteJasper;
        } else if (tipoComprobante.compareTo("04") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteNotaDebitoRideGenerico" : nombreReporteJasper;
        } else if (tipoComprobante.compareTo("05") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteNotaCreditoRideGenerico" : nombreReporteJasper;
        } else if (tipoComprobante.compareTo("06") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteGuiaRemisionRideGenerico" : nombreReporteJasper;
        } else if (tipoComprobante.compareTo("07") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteRetencionRideGenerico" : nombreReporteJasper;
        }

        String rutaReportesNombre = rutaReportes + nombreReporteJasper + ".jasper";
        File archivo = new File(rutaReportesNombre);
        if (archivo.exists()) {
            return "TExisteReporte";
        } else {
            return "FNo se puede Generar el Ride: \n Falta el archivo -> " + rutaReportesNombre + "\n" + " Contacte con el administrador...";
        }
    }

    @Override
    public String obtenerNombreReporte(String claveAcceso) throws Exception {
        String tipoComprobante = claveAcceso.substring(8, 10);
        if (tipoComprobante.compareTo("01") == 0) {
            return "reportComprobanteFacturaRide";
        } else if (tipoComprobante.compareTo("03") == 0) {
            return "reportComprobanteLiquidacionCompraRide";
        } else if (tipoComprobante.compareTo("04") == 0) {
            return "reportComprobanteNotaDebitoRide";
        } else if (tipoComprobante.compareTo("05") == 0) {
            return "reportComprobanteNotaCreditoRide";
        } else if (tipoComprobante.compareTo("06") == 0) {
            return "reportComprobanteGuiaRemisionRide";
        } else if (tipoComprobante.compareTo("07") == 0) {
            return "reportComprobanteRetencionRide";
        }
        return "";
    }
}
