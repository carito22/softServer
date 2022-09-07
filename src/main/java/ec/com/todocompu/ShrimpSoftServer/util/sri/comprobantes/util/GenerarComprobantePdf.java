package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.InfoTributaria;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.Factura;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.Factura.InfoFactura;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.facturareembolso.FacturaReembolso;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.guiaremision.GuiaRemision;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.liquidacioncompra.LiquidacionCompra;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadebito.ImpuestoNotaDebito;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadebito.NotaDebito;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadebito.NotaDebito.InfoNotaDebito;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadecredito.NotaCredito;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadecredito.NotaCredito.InfoNotaCredito;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadecredito.TotalConImpuestos;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.rentencion.ComprobanteRetencion.InfoAdicional;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.rentencion.ComprobanteRetencion.InfoCompRetencion;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.ComprobanteRetencionReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.DetallesAdicionalesReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.FacturaReembolsoReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.FacturaReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.GuiaRemisionReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.InformacionAdicional;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.LiquidacionCompraReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.NotaCreditoReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.NotaDebitoReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.TotalComprobante;
import ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoAmbienteEnum;
import java.io.IOException;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class GenerarComprobantePdf {

    public static byte[] generarReporteComprobanteElectronicoPDF(String empresa, String claveAcceso, String XmlString, String nombreReporteJasper, String rutaPersonalizada, String rutaLogo, FacturaReembolsoReporte reporte, JRDataSource dataSourceReembolso) throws Exception {
        Node contenidoComprobanteXm;
        Document doc;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(new ByteArrayInputStream(XmlString.getBytes("UTF-8")));
        contenidoComprobanteXm = doc.getElementsByTagName("comprobante").item(0);

        DocumentoReporteRIDE documentoRide = ArchivoUtils.documentoRIDE(doc);
        String numeroAutorizacion = documentoRide.getNumeroAutorizacion();
        String fechaAutorizacion = documentoRide.getFechaAutorizacion();

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
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteNotaCreditoRideGenerico" : nombreReporteJasper;
        } else if (tipoComprobante.compareTo("05") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteNotaDebitoRideGenerico" : nombreReporteJasper;//
        } else if (tipoComprobante.compareTo("06") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteGuiaRemisionRideGenerico" : nombreReporteJasper;
        } else if (tipoComprobante.compareTo("07") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteRetencionRideGenerico" : nombreReporteJasper;
        }

        if (tipoComprobante.compareTo("01") == 0) {
            if (dataSourceReembolso != null && reporte != null) {
                return llenarYcompilarReportesElectronicoFacturaReembolso(empresa, reporte, dataSourceReembolso, numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
            } else {
                return llenarYcompilarReportesElectronicoFactura(empresa, new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
            }
        } else if (tipoComprobante.compareTo("03") == 0) {
            return llenarYcompilarReportesElectronicoLiquidacionCompra(empresa, new LiquidacionCompraReporte((LiquidacionCompra) ArchivoUtils.unmarshal(LiquidacionCompra.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        } else if (tipoComprobante.compareTo("04") == 0) {
            return llenarYcompilarReportesElectronicoNotaCredito(empresa, new NotaCreditoReporte((NotaCredito) ArchivoUtils.unmarshal(NotaCredito.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        } else if (tipoComprobante.compareTo("05") == 0) {
            return llenarYcompilarReportesElectronicoNotaDebito(empresa, new NotaDebitoReporte((NotaDebito) ArchivoUtils.unmarshal(NotaDebito.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        } else if (tipoComprobante.compareTo("06") == 0) {
            return llenarYcompilarReportesElectronicoComprobanteGuiaRemision(empresa, new GuiaRemisionReporte((GuiaRemision) ArchivoUtils.unmarshal(GuiaRemision.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        } else if (tipoComprobante.compareTo("07") == 0) {
            return llenarYcompilarReportesElectronicoComprobanteRetencion(empresa, new ComprobanteRetencionReporte((ComprobanteRetencion) ArchivoUtils.unmarshal(ComprobanteRetencion.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        }
        return null;
    }

    public static byte[] llenarYcompilarReportesElectronicoFacturaReembolso(String empresa, FacturaReembolsoReporte facturaReporte, JRDataSource dataSourceReembolso, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            String[] identificadorDireccion = {"Direccion", "Dirección", "dirCliente"};
            String respuestaDireccionCli = null;
            JRDataSource dataSource = dataSourceReembolso;
            File file = File.createTempFile(nombreReporte.replaceAll(".jasper", ""), ".jrprint");
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", facturaReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", facturaReporte.getInfoAdicional());

            for (int i = 0; i < identificadorDireccion.length; i++) {
                respuestaDireccionCli = BuscarTituloInfAdicional(identificadorDireccion[i], facturaReporte.getInfoAdicional());
                if (respuestaDireccionCli != null) {
                    break;
                }
            }

            JasperFillManager.fillReportToFile(rutaReportesNombre, file.getPath(),
                    obtenerMapaParametrosReportes(
                            obtenerParametrosInfoTriobutaria(empresa, facturaReporte.getFacturaReembolso().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, null),
                            obtenerInfoFacturaReembolso(facturaReporte.getFacturaReembolso().getInfoFactura(), facturaReporte)),
                    dataSource);

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] llenarYcompilarReportesElectronicoFactura(String empresa, FacturaReporte facturaReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            Factura.InfoSustitutivaGuiaRemision guia = facturaReporte.getFactura().getInfoSustitutivaGuiaRemision();
            String[] identificadorDireccion = {"Direccion", "Dirección", "dirCliente"};
            String respuestaDireccionCli = null;
            if (guia != null) {
                rutaReportesNombre = rutaReportes + nombreReporte + "ConGuia" + ".jasper";// añadir a los nuevos reportes ConGuia al final para no afectar los nombres de los archivos
            }
            //verificar si es reporte exportador factura
            BigDecimal totalExportacion = null;
            if (facturaReporte.getFactura().getInfoFactura() != null && facturaReporte.getFactura().getInfoFactura().getComercioExterior() != null) {
                totalExportacion = facturaReporte.getFactura().getInfoFactura().getFleteInternacional()
                        .add(facturaReporte.getFactura().getInfoFactura().getSeguroInternacional())
                        .add(facturaReporte.getFactura().getInfoFactura().getGastosAduaneros())
                        .add(facturaReporte.getFactura().getInfoFactura().getGastosTransporteOtros());
                rutaReportesNombre = rutaReportes + "reportComprobanteFacturaExportacionRide" + ".jasper";
            }

            JRDataSource dataSource = new JRBeanCollectionDataSource(facturaReporte.getDetallesAdiciones());
            File file = File.createTempFile(nombreReporte.replaceAll(".jasper", ""), ".jrprint");
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", facturaReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", facturaReporte.getInfoAdicional());

            for (int i = 0; i < identificadorDireccion.length; i++) {
                respuestaDireccionCli = BuscarTituloInfAdicional(identificadorDireccion[i], facturaReporte.getInfoAdicional());
                if (respuestaDireccionCli != null) {
                    break;
                }
            }

            JasperFillManager.fillReportToFile(rutaReportesNombre, file.getPath(),
                    obtenerMapaParametrosReportes(
                            obtenerParametrosInfoTriobutaria(empresa, facturaReporte.getFactura().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, totalExportacion),
                            obtenerInfoFactura(facturaReporte.getFactura().getInfoFactura(), facturaReporte)),
                    dataSource);

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] llenarYcompilarReportesElectronicoNotaDebito(String empresa, NotaDebitoReporte notaDebitoReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            // String rutaReportes = UtilsArchivos.getRutaReportes() + "anexos/"
            // + nombreReporte + ".jasper";
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            JRDataSource dataSource = new JRBeanCollectionDataSource(notaDebitoReporte.getDetallesAdiciones());
            String[] identificadorDireccion = {"Direccion", "Dirección", "dirCliente"};
            String respuestaDireccionCli = null;
            File file = File.createTempFile(nombreReporte.replaceAll(".jasper", ""), ".jrprint");
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", notaDebitoReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", notaDebitoReporte.getInfoAdicional());
            for (int i = 0; i < identificadorDireccion.length; i++) {
                respuestaDireccionCli = BuscarTituloInfAdicional(identificadorDireccion[i], notaDebitoReporte.getInfoAdicional());
                if (respuestaDireccionCli != null) {
                    break;
                }
            }
            JasperFillManager.fillReportToFile(rutaReportesNombre, file.getPath(), obtenerMapaParametrosReportes(
                    obtenerParametrosInfoTriobutaria(empresa, notaDebitoReporte.getNotaDebito().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, null),
                    obtenerInfoND(notaDebitoReporte.getNotaDebito().getInfoNotaDebito())), dataSource);

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] llenarYcompilarReportesElectronicoLiquidacionCompra(String empresa, LiquidacionCompraReporte liquidacionCompraReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            net.sf.jasperreports.engine.JRDataSource dataSource = new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(liquidacionCompraReporte.getDetallesAdiciones());
            File file = File.createTempFile(nombreReporte.replaceAll(".jasper", ""), ".jrprint");
            String[] identificadorDireccion = {"Direccion", "Dirección", "dirCliente"};
            String respuestaDireccionCli = null;
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", liquidacionCompraReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", liquidacionCompraReporte.getInfoAdicional());
            for (int i = 0; i < identificadorDireccion.length; i++) {
                respuestaDireccionCli = BuscarTituloInfAdicional(identificadorDireccion[i], liquidacionCompraReporte.getInfoAdicional());
                if (respuestaDireccionCli != null) {
                    break;
                }
            }
            JasperFillManager.fillReportToFile(rutaReportesNombre, file.getPath(), obtenerMapaParametrosReportes(
                    obtenerParametrosInfoTriobutaria(empresa, liquidacionCompraReporte.getLiquidacionCompra().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, null),
                    obtenerInfoLiquidacionCompra(liquidacionCompraReporte.getLiquidacionCompra().getInfoLiquidacionCompra(), liquidacionCompraReporte)),
                    dataSource);

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] llenarYcompilarReportesElectronicoNotaCredito(String empresa, NotaCreditoReporte notaCreditoReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            // String rutaReportes = UtilsArchivos.getRutaReportes() + "anexos/"
            // + nombreReporte + ".jasper";
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            net.sf.jasperreports.engine.JRDataSource dataSource = new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
                    notaCreditoReporte.getDetallesAdiciones());
            String[] identificadorDireccion = {"Direccion", "Dirección", "dirCliente"};
            String respuestaDireccionCli = null;
            File file = File.createTempFile(nombreReporte.replaceAll(".jasper", ""), ".jrprint");
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", notaCreditoReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", notaCreditoReporte.getInfoAdicional());
            for (int i = 0; i < identificadorDireccion.length; i++) {
                respuestaDireccionCli = BuscarTituloInfAdicional(identificadorDireccion[i], notaCreditoReporte.getInfoAdicional());
                if (respuestaDireccionCli != null) {
                    break;
                }
            }
            JasperFillManager.fillReportToFile(rutaReportesNombre, file.getPath(), obtenerMapaParametrosReportes(
                    obtenerParametrosInfoTriobutaria(empresa, notaCreditoReporte.getNotaCredito().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, null),
                    obtenerInfoNC(notaCreditoReporte.getNotaCredito().getInfoNotaCredito(), notaCreditoReporte)),
                    dataSource);

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] llenarYcompilarReportesElectronicoComprobanteRetencion(String empresa, ComprobanteRetencionReporte comprobanteRetencionReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            JRDataSource dataSource = new JRBeanCollectionDataSource(comprobanteRetencionReporte.getDetallesAdiciones());
            File file = File.createTempFile(nombreReporte.replaceAll(".jasper", ""), ".jrprint");
            String[] identificadorDireccion = {"Direccion", "Dirección", "dirCliente"};
            String respuestaDireccionCli = null;
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", comprobanteRetencionReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", comprobanteRetencionReporte.getInfoAdicional());
            for (int i = 0; i < identificadorDireccion.length; i++) {
                respuestaDireccionCli = BuscarTituloInfAdicional(identificadorDireccion[i], comprobanteRetencionReporte.getInfoAdicional());
                if (respuestaDireccionCli != null) {
                    break;
                }
            }
            JasperFillManager.fillReportToFile(rutaReportesNombre, file.getPath(),
                    obtenerMapaParametrosReportes(
                            obtenerParametrosInfoTriobutaria(empresa, comprobanteRetencionReporte.getComprobanteRetencion().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, null),
                            obtenerInfoCompRetencion(
                                    comprobanteRetencionReporte.getComprobanteRetencion().getInfoCompRetencion(), comprobanteRetencionReporte.getComprobanteRetencion().getInfoAdicional())),
                    dataSource);

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] llenarYcompilarReportesElectronicoComprobanteGuiaRemision(String empresa, GuiaRemisionReporte guiaRemisionReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            JRDataSource dataSource = new JRBeanCollectionDataSource(guiaRemisionReporte.getDetallesAdiciones());
            String[] identificadorDireccion = {"Direccion", "Dirección", "dirCliente"};
            String respuestaDireccionCli = null;
            File file = File.createTempFile(nombreReporte.replaceAll(".jasper", ""), ".jrprint");
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", guiaRemisionReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", guiaRemisionReporte.getInfoAdicional());
            for (int i = 0; i < identificadorDireccion.length; i++) {
                respuestaDireccionCli = BuscarTituloInfAdicional(identificadorDireccion[i], guiaRemisionReporte.getInfoAdicional());
                if (respuestaDireccionCli != null) {
                    break;
                }
            }
            JasperFillManager.fillReportToFile(rutaReportesNombre, file.getPath(),
                    obtenerMapaParametrosReportes(
                            obtenerParametrosInfoTriobutaria(empresa, guiaRemisionReporte.getGuiaRemision().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, null),
                            obtenerInfoCompGuiaRemision(guiaRemisionReporte.getGuiaRemision())
                    ), dataSource);

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File generarReporteComprobanteElectronicoPDFEmail(String empresa, String claveAcceso, String XmlString, String nombreReporteJasper, String rutaPersonalizada, String rutaLogo) throws Exception {
        Node contenidoComprobanteXm;
        Document doc;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(new ByteArrayInputStream(XmlString.getBytes("UTF-8")));
        contenidoComprobanteXm = doc.getElementsByTagName("comprobante").item(0);

        DocumentoReporteRIDE documentoRide = ArchivoUtils.documentoRIDE(doc);
        String numeroAutorizacion = documentoRide.getNumeroAutorizacion();
        String fechaAutorizacion = documentoRide.getFechaAutorizacion();

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
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteLiquidacionCompraRideGenerico" : nombreReporteJasper;//liquidacion compras
        } else if (tipoComprobante.compareTo("04") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteNotaCreditoRideGenerico" : nombreReporteJasper;//
        } else if (tipoComprobante.compareTo("05") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteNotaDebitoRideGenerico" : nombreReporteJasper;
        } else if (tipoComprobante.compareTo("06") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteGuiaRemisionRideGenerico" : nombreReporteJasper;
        } else if (tipoComprobante.compareTo("07") == 0) {
            nombreReporteJasper = nombreReporteJasper.compareTo("GENERICO") == 0 ? "reportComprobanteRetencionRideGenerico" : nombreReporteJasper;
        }

        if (tipoComprobante.compareTo("01") == 0) {
            return llenarYcompilarReportesElectronicoFacturaPDF(empresa, new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        } else if (tipoComprobante.compareTo("03") == 0) {
            return llenarYcompilarReportesElectronicoLiquidacionCompraPDF(empresa, new LiquidacionCompraReporte((LiquidacionCompra) ArchivoUtils.unmarshal(LiquidacionCompra.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        } else if (tipoComprobante.compareTo("04") == 0) {
            return llenarYcompilarReportesElectronicoNotaCreditoPDF(empresa, new NotaCreditoReporte((NotaCredito) ArchivoUtils.unmarshal(NotaCredito.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        } else if (tipoComprobante.compareTo("05") == 0) {
            return llenarYcompilarReportesElectronicoNotaDebitoPDF(empresa, new NotaDebitoReporte((NotaDebito) ArchivoUtils.unmarshal(NotaDebito.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        } else if (tipoComprobante.compareTo("06") == 0) {
            GuiaRemisionReporte reporte = new GuiaRemisionReporte((GuiaRemision) ArchivoUtils.unmarshal(GuiaRemision.class, contenidoComprobanteXm));
            return llenarYcompilarReportesElectronicoGuiaRemisionPDF(empresa, reporte, numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        } else if (tipoComprobante.compareTo("07") == 0) {
            return llenarYcompilarReportesElectronicoComprobanteRetencionPDF(empresa, new ComprobanteRetencionReporte((ComprobanteRetencion) ArchivoUtils.unmarshal(ComprobanteRetencion.class, contenidoComprobanteXm)), numeroAutorizacion, fechaAutorizacion, nombreReporteJasper, rutaReportes, rutaLogo);
        }
        return null;
    }

    public static File llenarYcompilarReportesElectronicoFacturaPDF(String empresa, FacturaReporte facturaReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            JRDataSource dataSource = new JRBeanCollectionDataSource(facturaReporte.getDetallesAdiciones());
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", facturaReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", facturaReporte.getInfoAdicional());
            String respuestaDireccionCli = BuscarTituloInfAdicional("Dirección", facturaReporte.getInfoAdicional());

            //verificar si es reporte exportador factura
            BigDecimal totalExportacion = null;
            if (facturaReporte.getFactura().getInfoFactura() != null && facturaReporte.getFactura().getInfoFactura().getComercioExterior() != null) {
                totalExportacion = facturaReporte.getFactura().getInfoFactura().getFleteInternacional()
                        .add(facturaReporte.getFactura().getInfoFactura().getSeguroInternacional())
                        .add(facturaReporte.getFactura().getInfoFactura().getGastosAduaneros())
                        .add(facturaReporte.getFactura().getInfoFactura().getGastosTransporteOtros());
                rutaReportesNombre = rutaReportes + "reportComprobanteFacturaExportacionRide" + ".jasper";
            }

            JasperPrint jp = JasperFillManager.fillReport(rutaReportesNombre,
                    obtenerMapaParametrosReportes(
                            obtenerParametrosInfoTriobutaria(empresa, facturaReporte.getFactura().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, totalExportacion),
                            obtenerInfoFactura(facturaReporte.getFactura().getInfoFactura(), facturaReporte)),
                    dataSource);

            File file = File.createTempFile(nombreReporte, ".pdf");

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jp));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            return file;
        } catch (IOException | JRException e) {
        }
        return null;
    }

    public static File llenarYcompilarReportesElectronicoNotaDebitoPDF(String empresa, NotaDebitoReporte notaDebitoReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            JRDataSource dataSource = new JRBeanCollectionDataSource(notaDebitoReporte.getDetallesAdiciones());
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", notaDebitoReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", notaDebitoReporte.getInfoAdicional());
            String respuestaDireccionCli = BuscarTituloInfAdicional("Dirección", notaDebitoReporte.getInfoAdicional());

            JasperPrint jp = JasperFillManager.fillReport(rutaReportesNombre, obtenerMapaParametrosReportes(
                    obtenerParametrosInfoTriobutaria(empresa, notaDebitoReporte.getNotaDebito().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, null),
                    obtenerInfoND(notaDebitoReporte.getNotaDebito().getInfoNotaDebito())), dataSource);

            File file = File.createTempFile(nombreReporte, ".pdf");

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jp));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            return file;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File llenarYcompilarReportesElectronicoNotaCreditoPDF(String empresa, NotaCreditoReporte notaCreditoReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            net.sf.jasperreports.engine.JRDataSource dataSource = new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
                    notaCreditoReporte.getDetallesAdiciones());

            String agenteInfAdicional = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", notaCreditoReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", notaCreditoReporte.getInfoAdicional());
            String respuestaDireccionCli = BuscarTituloInfAdicional("Dirección", notaCreditoReporte.getInfoAdicional());

            JasperPrint jp = JasperFillManager.fillReport(rutaReportesNombre, obtenerMapaParametrosReportes(
                    obtenerParametrosInfoTriobutaria(empresa, notaCreditoReporte.getNotaCredito().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, agenteInfAdicional, respuestaRegimen, respuestaDireccionCli, null),
                    obtenerInfoNC(notaCreditoReporte.getNotaCredito().getInfoNotaCredito(), notaCreditoReporte)),
                    dataSource);

            File file = File.createTempFile(nombreReporte, ".pdf");

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jp));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            return file;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File llenarYcompilarReportesElectronicoComprobanteRetencionPDF(String empresa, ComprobanteRetencionReporte comprobanteRetencionReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            JRDataSource dataSource = new JRBeanCollectionDataSource(
                    comprobanteRetencionReporte.getDetallesAdiciones());

            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", comprobanteRetencionReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", comprobanteRetencionReporte.getInfoAdicional());
            String respuestaDireccionCli = BuscarTituloInfAdicional("Dirección", comprobanteRetencionReporte.getInfoAdicional());

            JasperPrint jp = JasperFillManager.fillReport(rutaReportesNombre,
                    obtenerMapaParametrosReportes(
                            obtenerParametrosInfoTriobutaria(empresa, comprobanteRetencionReporte.getComprobanteRetencion().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, null),
                            obtenerInfoCompRetencion(
                                    comprobanteRetencionReporte.getComprobanteRetencion().getInfoCompRetencion(), comprobanteRetencionReporte.getComprobanteRetencion().getInfoAdicional())),
                    dataSource);

            File file = File.createTempFile(nombreReporte, ".pdf");

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jp));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            return file;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File llenarYcompilarReportesElectronicoLiquidacionCompraPDF(String empresa, LiquidacionCompraReporte liquidacionCompraReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            JRDataSource dataSource = new JRBeanCollectionDataSource(liquidacionCompraReporte.getDetallesAdiciones());
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", liquidacionCompraReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", liquidacionCompraReporte.getInfoAdicional());
            String respuestaDireccionCli = BuscarTituloInfAdicional("Dirección", liquidacionCompraReporte.getInfoAdicional());

            JasperPrint jp = JasperFillManager.fillReport(
                    rutaReportesNombre,
                    obtenerMapaParametrosReportes(
                            obtenerParametrosInfoTriobutaria(empresa, liquidacionCompraReporte.getLiquidacionCompra().getInfoTributaria(), numeroAutorizacion, fechaAutorizacion, rutaReportes, rutaLogo, respuestaAgente, respuestaRegimen, respuestaDireccionCli, null),
                            obtenerInfoLiquidacionCompra(liquidacionCompraReporte.getLiquidacionCompra().getInfoLiquidacionCompra(), liquidacionCompraReporte)
                    ), dataSource);

            File file = File.createTempFile(nombreReporte, ".pdf");

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jp));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            return file;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File llenarYcompilarReportesElectronicoGuiaRemisionPDF(String empresa, GuiaRemisionReporte guiaRemisionReporte, String numeroAutorizacion, String fechaAutorizacion, String nombreReporte, String rutaReportes, String rutaLogo) {
        try {
            String rutaReportesNombre = rutaReportes + nombreReporte + ".jasper";
            JRDataSource dataSource = new JRBeanCollectionDataSource(guiaRemisionReporte.getDetallesAdiciones());
            String respuestaAgente = BuscarTituloInfAdicional("AGENTE DE RETENCIÓN SEGÚN RESOLUCIÓN", guiaRemisionReporte.getInfoAdicional());
            String respuestaRegimen = BuscarTituloInfAdicional("Régimen", guiaRemisionReporte.getInfoAdicional());
            String respuestaDireccionCli = BuscarTituloInfAdicional("Dirección", guiaRemisionReporte.getInfoAdicional());

            JasperPrint jp = JasperFillManager.fillReport(rutaReportesNombre,
                    obtenerMapaParametrosReportes(
                            obtenerParametrosInfoTriobutaria(empresa,
                                    guiaRemisionReporte.getGuiaRemision().getInfoTributaria(),
                                    numeroAutorizacion,
                                    fechaAutorizacion,
                                    rutaReportes,
                                    rutaLogo,
                                    respuestaAgente,
                                    respuestaRegimen, respuestaDireccionCli, null),
                            obtenerInfoCompGuiaRemision(guiaRemisionReporte.getGuiaRemision())
                    ), dataSource);

            File file = File.createTempFile(nombreReporte, ".pdf");
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jp));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            return file;
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> obtenerMapaParametrosReportes(Map<String, Object> mapa1, Map<String, Object> mapa2) {
        mapa1.putAll(mapa2);
        return mapa1;
    }

    public static Map<String, Object> obtenerParametrosInfoTriobutaria(String empresa, InfoTributaria infoTributaria, String numAut, String fechaAut, String subReporte, String rutaLogo, String agenteRetencionInfAdicional, String respuestaRegimen, String respuestaDireccionCli, BigDecimal totalExportacion) {
        Map<String, Object> param = new HashMap<>();
        String rutaImg = UtilsArchivos.getRutaImagen() + rutaLogo + ".jpg";// empresa
        param.put("LOGO", rutaImg);
        param.put("RUC", infoTributaria.getRuc());
        param.put("CLAVE_ACC", infoTributaria.getClaveAcceso());
        param.put("RAZON_SOCIAL", infoTributaria.getRazonSocial());
        param.put("DIR_MATRIZ", infoTributaria.getDirMatriz());
        param.put("SUBREPORT_DIR", subReporte);
        param.put("TIPO_EMISION", obtenerTipoEmision(infoTributaria));
        param.put("NUM_AUT", numAut);
        param.put("FECHA_AUT", fechaAut);
        param.put("NUM_FACT", infoTributaria.getEstab() + "-" + infoTributaria.getPtoEmi() + "-" + infoTributaria.getSecuencial());
        param.put("AMBIENTE", obtenerAmbiente(infoTributaria));
        param.put("NOM_COMERCIAL", infoTributaria.getNombreComercial());
        param.put("CONTRIBUYENTE_RIMPE", infoTributaria.getContribuyenteRimpe());
        param.put("TOTAL_EXPORTACION", totalExportacion);
//        param.put("CONTRIBUYENTE_RIMPE", "");

        param.put("AGENTE_RETENCION", infoTributaria.getAgenteRetencion() != null
                && !infoTributaria.getAgenteRetencion().equals("")
                ? infoTributaria.getAgenteRetencion()
                : (agenteRetencionInfAdicional != null
                && !agenteRetencionInfAdicional.equals("") ? agenteRetencionInfAdicional : null));

        String regimen = infoTributaria.getRegimenMicroempresas() != null && !infoTributaria.getRegimenMicroempresas().equals("")
                ? infoTributaria.getRegimenMicroempresas()
                : (respuestaRegimen != null && !respuestaRegimen.equals("") ? respuestaRegimen : null);

        if (regimen != null) {
            boolean tieneMicro = regimen.toLowerCase().contains("micro");
            if (!tieneMicro) {
                regimen = null;
            }
        }

        param.put("REGIMEN_MICROEMPRESA", regimen);

        param.put("DIR_CLIENTE", (respuestaDireccionCli != null
                && !respuestaDireccionCli.equals("") ? respuestaDireccionCli : null));

        return param;
    }

    public static Map<String, Object> obtenerInfoCompRetencion(InfoCompRetencion infoComp, InfoAdicional infoAdicional) {
        Map<String, Object> param = new HashMap<>();
        param.put("DIR_SUCURSAL", infoComp.getDirEstablecimiento());
        param.put("RS_COMPRADOR", infoComp.getRazonSocialSujetoRetenido());
        param.put("RUC_COMPRADOR", infoComp.getIdentificacionSujetoRetenido());
        param.put("FECHA_EMISION", infoComp.getFechaEmision());
        param.put("CONT_ESPECIAL", infoComp.getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", infoComp.getObligadoContabilidad());
        param.put("EJERCICIO_FISCAL", infoComp.getPeriodoFiscal());
        return param;
    }

    public static Map<String, Object> obtenerInfoCompGuiaRemision(GuiaRemision infoComp) {
        Map<String, Object> param = new HashMap<>();
        param.put("DIR_SUCURSAL", infoComp.getInfoGuiaRemision().getDirEstablecimiento());
        param.put("RS_TRANSPORTISTA", infoComp.getInfoGuiaRemision().getRazonSocialTransportista());
        param.put("RUC_TRANSPORTISTA", infoComp.getInfoGuiaRemision().getRucTransportista());
        param.put("FECHA_INICIO", infoComp.getInfoGuiaRemision().getFechaIniTransporte());
        param.put("FECHA_FINAL", infoComp.getInfoGuiaRemision().getFechaFinTransporte());
        param.put("PUNTO_PARTIDA", infoComp.getInfoGuiaRemision().getDirPartida());
        param.put("PUNTO_LLEGADA", infoComp.getDestinatarios().getDestinario().get(0).getDirDestinatario());
        param.put("PLACA", infoComp.getInfoGuiaRemision().getPlaca());

        param.put("COMPROBANTE", infoComp.getDestinatarios().getDestinario().get(0).getNumDocSustento());
        param.put("FECHA_EMISION", infoComp.getDestinatarios().getDestinario().get(0).getFechaEmisionDocSustento());
        param.put("NUMERO_AUTORIZACION", infoComp.getDestinatarios().getDestinario().get(0).getNumAutDocSustento());
        param.put("MOTIVO_TRASLADO", infoComp.getDestinatarios().getDestinario().get(0).getMotivoTraslado());
        param.put("RUC_DESTINATARIO", infoComp.getDestinatarios().getDestinario().get(0).getIdentificacionDestinatario());
        param.put("RS_DESTINATARIO", infoComp.getDestinatarios().getDestinario().get(0).getRazonSocialDestinatario());
        param.put("DOC_ADUANERO", infoComp.getDestinatarios().getDestinario().get(0).getDocAduaneroUnico());
        param.put("COD_ESTABLECIMIENTO", infoComp.getDestinatarios().getDestinario().get(0).getCodEstabDestino());
        param.put("RUTA", infoComp.getDestinatarios().getDestinario().get(0).getRuta());

        param.put("CONT_ESPECIAL", infoComp.getInfoGuiaRemision().getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", infoComp.getInfoGuiaRemision().getObligadoContabilidad());
        return param;
    }

    public static String BuscarTituloInfAdicional(String titulo, List<InformacionAdicional> listaInf) {
        String resp = null;
        if (listaInf != null) {
            for (int i = 0; i < listaInf.size(); i++) {
                if (listaInf.get(i) != null && listaInf.get(i).getNombre() != null) {
                    if (listaInf.get(i).getNombre().contains(titulo)) {
                        resp = listaInf.get(i).getValor();
                    }
                }
            }
        }
        return resp;
    }

    public static Map<String, Object> obtenerInfoFacturaReembolso(FacturaReembolso.InfoFactura infoFactura, FacturaReembolsoReporte fact) {
        Map<String, Object> param = new HashMap<>();
        param.put("DIR_SUCURSAL", infoFactura.getDirEstablecimiento());
        param.put("CONT_ESPECIAL", infoFactura.getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", infoFactura.getObligadoContabilidad());
        param.put("RS_COMPRADOR", infoFactura.getRazonSocialComprador());
        param.put("RUC_COMPRADOR", infoFactura.getIdentificacionComprador());
        param.put("FECHA_EMISION", infoFactura.getFechaEmision());
        param.put("GUIA", infoFactura.getGuiaRemision());
        TotalComprobante tc = getTotalesReembolso(infoFactura);
        param.put("VALOR_TOTAL", infoFactura.getImporteTotal());// b
        param.put("DESCUENTO", infoFactura.getTotalDescuento());// b
        param.put("IVA", new BigDecimal(tc.getIva12()));
        param.put("IVA_0", new BigDecimal(tc.getSubtotal0()));
        param.put("IVA_12", new BigDecimal(tc.getSubtotal12()));
        param.put("ICE", new BigDecimal(tc.getTotalIce()));
        param.put("NO_OBJETO_IVA", new BigDecimal(tc.getSubtotalNoSujetoIva()));
        param.put("EXENTO_IVA", new BigDecimal("0.00"));
        param.put("IRBPNR", new BigDecimal("0.00"));
        param.put("SUBTOTAL", infoFactura.getTotalSinImpuestos());// b
        param.put("IVA_VIGENTE", tc.getIvaVigente());
        param.put("REEMBOLSOS", fact.getDetallesReembolso());
        if (infoFactura.getPropina() != null) {
            param.put("PROPINA", infoFactura.getPropina());
        }
        param.put("TOTAL_DESCUENTO", new BigDecimal(calcularDescuentoReembolso(fact)));

        return param;
    }

    public static Map<String, Object> obtenerInfoFactura(InfoFactura infoFactura, FacturaReporte fact) {
        Map<String, Object> param = new HashMap<>();
        param.put("DIR_SUCURSAL", infoFactura.getDirEstablecimiento());
        param.put("CONT_ESPECIAL", infoFactura.getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", infoFactura.getObligadoContabilidad());
        param.put("RS_COMPRADOR", infoFactura.getRazonSocialComprador());
        param.put("RUC_COMPRADOR", infoFactura.getIdentificacionComprador());
        param.put("FECHA_EMISION", infoFactura.getFechaEmision());
        param.put("GUIA", infoFactura.getGuiaRemision());
        TotalComprobante tc = getTotales(infoFactura);
        param.put("VALOR_TOTAL", infoFactura.getImporteTotal());// b
        param.put("DESCUENTO", infoFactura.getTotalDescuento());// b
        param.put("IVA", new BigDecimal(tc.getIva12()));
        param.put("IVA_0", new BigDecimal(tc.getSubtotal0()));
        param.put("IVA_12", new BigDecimal(tc.getSubtotal12()));
        param.put("ICE", new BigDecimal(tc.getTotalIce()));
        param.put("NO_OBJETO_IVA", new BigDecimal(tc.getSubtotalNoSujetoIva()));
        param.put("EXENTO_IVA", new BigDecimal("0.00"));
        param.put("IRBPNR", new BigDecimal("0.00"));
        param.put("SUBTOTAL", infoFactura.getTotalSinImpuestos());// b
        param.put("IVA_VIGENTE", tc.getIvaVigente());

        if (infoFactura.getPropina() != null) {
            param.put("PROPINA", infoFactura.getPropina());
        }
        param.put("TOTAL_DESCUENTO", new BigDecimal(calcularDescuento(fact)));
        /*GUIA*/
        Factura.InfoSustitutivaGuiaRemision guia = fact.getFactura().getInfoSustitutivaGuiaRemision();
        if (guia != null) {
            param.put("DIR_DESTINATARIO", guia.getDirDestinatario());
            param.put("DIR_PARTIDA", guia.getDirPartida());
            param.put("FECHA_INICIO_TRANSPORTE", guia.getFechaIniTransporte());
            param.put("FECHA_FIN_TRANSPORTE", guia.getFechaFinTransporte());
            param.put("PLACA", guia.getPlaca());
            param.put("RAZON_SOCIAL_TRANSPORTISTA", guia.getRazonSocialTransportista());
            param.put("RUC_TRANSPORTISTA", guia.getRucTransportista());
            param.put("TIPO_IDENTIFICACION_TRANSPORTISTA", guia.getTipoIdentificacionTransportista());
            param.put("COD_ESTABLECIMIENTO_DESTINO", guia.getDestinos().getDestino().get(0).getCodEstabDestino());
            param.put("DOC_ADUANERO", guia.getDestinos().getDestino().get(0).getDocAduaneroUnico());
            param.put("MOTIVO_TRASLADO", guia.getDestinos().getDestino().get(0).getMotivoTraslado());
            param.put("RUTA", guia.getDestinos().getDestino().get(0).getRuta());
        }
        return param;
    }

    public static Map<String, Object> obtenerInfoND(InfoNotaDebito notaDebito) {
        Map<String, Object> param = new HashMap<>();
        param.put("DIR_SUCURSAL", notaDebito.getDirEstablecimiento());
        param.put("CONT_ESPECIAL", notaDebito.getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", notaDebito.getObligadoContabilidad());
        param.put("RS_COMPRADOR", notaDebito.getRazonSocialComprador());
        param.put("RUC_COMPRADOR", notaDebito.getIdentificacionComprador());
        param.put("FECHA_EMISION", notaDebito.getFechaEmision());
        TotalComprobante tc = getTotalesND(notaDebito);
        param.put("IVA_0", new BigDecimal(tc.getSubtotal0()));
        param.put("IVA_12", new BigDecimal(tc.getSubtotal12()));
        param.put("ICE", new BigDecimal(tc.getTotalIce()));
        param.put("TOTAL", notaDebito.getValorTotal());
        param.put("IVA", new BigDecimal(tc.getIva12()));
        param.put("NO_OBJETO_IVA", new BigDecimal(tc.getSubtotalNoSujetoIva()));
        param.put("EXENTO_IVA", new BigDecimal("0.00"));
        param.put("NUM_DOC_MODIFICADO", notaDebito.getNumDocModificado());
        param.put("FECHA_EMISION_DOC_SUSTENTO", notaDebito.getFechaEmisionDocSustento());
        param.put("DOC_MODIFICADO", ArchivoUtils.obtenerDocumentoModificado(notaDebito.getCodDocModificado()));
        param.put("TOTAL_SIN_IMP", notaDebito.getTotalSinImpuestos());
        param.put("IVA_VIGENTE", tc.getIvaVigente());
        return param;
    }

    public static Map<String, Object> obtenerInfoNC(InfoNotaCredito infoNC, NotaCreditoReporte nc) {
        Map<String, Object> param = new HashMap<>();
        param.put("DIR_SUCURSAL", infoNC.getDirEstablecimiento());
        param.put("CONT_ESPECIAL", infoNC.getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", infoNC.getObligadoContabilidad());
        param.put("RS_COMPRADOR", infoNC.getRazonSocialComprador());
        param.put("RUC_COMPRADOR", infoNC.getIdentificacionComprador());
        param.put("FECHA_EMISION", infoNC.getFechaEmision());
        TotalComprobante tc = getTotalesNC(infoNC);
        param.put("IVA_0", new BigDecimal(tc.getSubtotal0()));//
        param.put("IVA_12", new BigDecimal(tc.getSubtotal12()));//
        param.put("ICE", new BigDecimal(tc.getTotalIce()));
        param.put("VALOR_TOTAL", infoNC.getValorModificacion());
        param.put("IVA", new BigDecimal(tc.getIva12()));
        param.put("SUBTOTAL", infoNC.getTotalSinImpuestos());
        param.put("NO_OBJETO_IVA", new BigDecimal(tc.getSubtotalNoSujetoIva()));//
        param.put("EXENTO_IVA", new BigDecimal("0.00"));

        param.put("NUM_DOC_MODIFICADO", infoNC.getNumDocModificado());
        param.put("FECHA_EMISION_DOC_SUSTENTO", infoNC.getFechaEmisionDocSustento());
        param.put("DOC_MODIFICADO", ArchivoUtils.obtenerDocumentoModificado(infoNC.getCodDocModificado()));
        param.put("TOTAL_DESCUENTO", new BigDecimal(obtenerTotalDescuento(nc)));
        param.put("RAZON_MODIF", infoNC.getMotivo());
        param.put("IVA_VIGENTE", tc.getIvaVigente());
        param.put("IRBPNR", new BigDecimal("0.00"));
        return param;
    }

    public static Map<String, Object> obtenerInfoLiquidacionCompra(LiquidacionCompra.InfoLiquidacionCompra infoLiq, LiquidacionCompraReporte liq) {
        Map<String, Object> param = new HashMap<>();
        param.put("DIR_SUCURSAL", infoLiq.getDirEstablecimiento());
        param.put("CONT_ESPECIAL", infoLiq.getContribuyenteEspecial());
        param.put("LLEVA_CONTABILIDAD", infoLiq.getObligadoContabilidad());
        param.put("RS_COMPRADOR", infoLiq.getRazonSocialProveedor());
        param.put("RUC_COMPRADOR", infoLiq.getIdentificacionProveedor());
        param.put("FECHA_EMISION", infoLiq.getFechaEmision());
        TotalComprobante tc = getTotalesLiq(infoLiq);
        param.put("VALOR_TOTAL", infoLiq.getImporteTotal());// b
        param.put("DESCUENTO", infoLiq.getTotalDescuento());// b
        param.put("IVA", new BigDecimal(tc.getIva12()));
        param.put("IVA_0", new BigDecimal(tc.getSubtotal0()));
        param.put("IVA_12", new BigDecimal(tc.getSubtotal12()));
        param.put("ICE", new BigDecimal(tc.getTotalIce()));
        param.put("NO_OBJETO_IVA", new BigDecimal(tc.getSubtotalNoSujetoIva()));
        param.put("EXENTO_IVA", new BigDecimal("0.00"));
        param.put("IRBPNR", new BigDecimal("0.00"));
        param.put("SUBTOTAL", infoLiq.getTotalSinImpuestos());// b
        param.put("IVA_VIGENTE", tc.getIvaVigente());
        param.put("TOTAL_DESCUENTO", new BigDecimal(calcularDescuentoLiquidacionCompra(liq)));
        return param;
    }

    public static String obtenerTotalDescuento(NotaCreditoReporte nc) {
        BigDecimal descuento = new BigDecimal(0);
        for (DetallesAdicionalesReporte detalle : nc.getDetallesAdiciones()) {
            descuento = descuento.add(new BigDecimal(detalle.getDescuento()));
        }
        return descuento.toString();
    }

    public static String calcularDescuentoReembolso(FacturaReembolsoReporte fact) {
        BigDecimal descuento = new BigDecimal(0);
        for (DetallesAdicionalesReporte detalle : fact.getDetallesAdiciones()) {
            descuento = descuento.add(new BigDecimal(detalle.getDescuento()));
        }
        return descuento.toString();
    }

    public static String calcularDescuento(FacturaReporte fact) {
        BigDecimal descuento = new BigDecimal(0);
        for (DetallesAdicionalesReporte detalle : fact.getDetallesAdiciones()) {
            descuento = descuento.add(new BigDecimal(detalle.getDescuento()));
        }
        return descuento.toString();
    }

    public static String calcularDescuentoLiquidacionCompra(LiquidacionCompraReporte liq) {
        BigDecimal descuento = new BigDecimal(0);
        for (DetallesAdicionalesReporte detalle : liq.getDetallesAdiciones()) {
            descuento = descuento.add(new BigDecimal(detalle.getDescuento()));
        }
        return descuento.toString();
    }

    public static String obtenerTipoEmision(InfoTributaria infoTributaria) {
        if (infoTributaria.getTipoEmision().equals("2")) {
            return TipoEmisionEnum.CONTINGENCIA.getCode();
        }
        if (infoTributaria.getTipoEmision().equals("1")) {
            return TipoEmisionEnum.NORMAL.getCode();
        }
        return null;
    }

    public static String obtenerAmbiente(InfoTributaria infoTributaria) {
        if (infoTributaria.getAmbiente().equals("2")) {
            return TipoAmbienteEnum.PRODUCCION.toString();
        }
        return TipoAmbienteEnum.PRUEBAS.toString();
    }

    public static TotalComprobante getTotalesNC(InfoNotaCredito infoNc) {
        BigDecimal totalIva12 = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);
        BigDecimal iva12 = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
        TotalComprobante tc = new TotalComprobante();
        String codigoPorcentajeIva = "";
        for (TotalConImpuestos.TotalImpuesto ti : infoNc.getTotalConImpuestos().getTotalImpuesto()) {
            Integer cod = new Integer(ti.getCodigo());
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_12.getCode().equals(ti.getCodigoPorcentaje())
                    || TipoImpuestoIvaEnum.IVA_VENTA_14.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva12 = totalIva12.add(ti.getBaseImponible());
                iva12 = iva12.add(ti.getValor());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_0.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva0 = totalIva0.add(ti.getBaseImponible());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_EXCENTO.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
            }
            if (TipoImpuestoEnum.ICE.getCode() == cod) {
                totalICE = totalICE.add(ti.getValor());
            }
        }
        tc.setIva12(iva12.toString());
        tc.setSubtotal0(totalIva0.toString());
        tc.setSubtotal12(totalIva12.toString());
        tc.setTotalIce(totalICE.toString());
        tc.setSubtotal(totalIva0.add(totalIva12));
        tc.setSubtotalNoSujetoIva(totalSinImpuesto.toString());
        tc.setIvaVigente(
                codigoPorcentajeIva.compareTo(TipoImpuestoIvaEnum.IVA_VENTA_14.getCode()) == 0 ? "14%" : "12%");
        return tc;
    }

    public static TotalComprobante getTotalesND(InfoNotaDebito infoNotaDebito) {
        BigDecimal totalIva12 = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal iva12 = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
        TotalComprobante tc = new TotalComprobante();
        String codigoPorcentajeIva = "";
        for (ImpuestoNotaDebito ti : infoNotaDebito.getImpuestos().getImpuesto()) {
            Integer cod = new Integer(ti.getCodigo());
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_12.getCode().equals(ti.getCodigoPorcentaje())
                    || TipoImpuestoIvaEnum.IVA_VENTA_14.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva12 = totalIva12.add(ti.getBaseImponible());
                iva12 = iva12.add(ti.getValor());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_0.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva0 = totalIva0.add(ti.getBaseImponible());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_EXCENTO.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
            }
            if (TipoImpuestoEnum.ICE.getCode() == cod) {
                totalICE = totalICE.add(ti.getValor());
            }
        }
        tc.setSubtotal0(totalIva0.toString());
        tc.setSubtotal12(totalIva12.toString());
        tc.setTotalIce(totalICE.toString());
        tc.setIva12(iva12.toString());
        tc.setSubtotalNoSujetoIva(totalSinImpuesto.toPlainString());
        tc.setIvaVigente(
                codigoPorcentajeIva.compareTo(TipoImpuestoIvaEnum.IVA_VENTA_14.getCode()) == 0 ? "14%" : "12%");
        return tc;
    }

    public static TotalComprobante getTotalesReembolso(FacturaReembolso.InfoFactura infoFactura) {
        BigDecimal totalIva12 = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);
        BigDecimal iva12 = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
        String codigoPorcentajeIva = "";
        TotalComprobante tc = new TotalComprobante();
        for (FacturaReembolso.InfoFactura.TotalConImpuesto.TotalImpuesto ti : infoFactura.getTotalConImpuestos().getTotalImpuesto()) {
            Integer cod = new Integer(ti.getCodigo());
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_12.getCode().equals(ti.getCodigoPorcentaje()))
                    || TipoImpuestoIvaEnum.IVA_VENTA_14.getCode().equals(ti.getCodigoPorcentaje())) {
                totalIva12 = totalIva12.add(ti.getBaseImponible());
                iva12 = iva12.add(ti.getValor());
                codigoPorcentajeIva = ti.getCodigoPorcentaje();
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_0.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva0 = totalIva0.add(ti.getBaseImponible());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_EXCENTO.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
            }
            if (TipoImpuestoEnum.ICE.getCode() == cod) {
                totalICE = totalICE.add(ti.getValor());
            }
        }
        tc.setIva12(iva12.toString());
        tc.setSubtotal0(totalIva0.toString());
        tc.setSubtotal12(totalIva12.toString());
        tc.setTotalIce(totalICE.toString());
        tc.setSubtotal(totalIva0.add(totalIva12));
        tc.setSubtotalNoSujetoIva(totalSinImpuesto.toString());
        tc.setIvaVigente(
                codigoPorcentajeIva.compareTo(TipoImpuestoIvaEnum.IVA_VENTA_14.getCode()) == 0 ? "14%" : "12%");
        return tc;
    }

    public static TotalComprobante getTotales(InfoFactura infoFactura) {
        BigDecimal totalIva12 = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);
        BigDecimal iva12 = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
        String codigoPorcentajeIva = "";
        TotalComprobante tc = new TotalComprobante();
        for (Factura.InfoFactura.TotalConImpuestos.TotalImpuesto ti : infoFactura.getTotalConImpuestos()
                .getTotalImpuesto()) {
            Integer cod = new Integer(ti.getCodigo());
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_12.getCode().equals(ti.getCodigoPorcentaje()))
                    || TipoImpuestoIvaEnum.IVA_VENTA_14.getCode().equals(ti.getCodigoPorcentaje())) {
                totalIva12 = totalIva12.add(ti.getBaseImponible());
                iva12 = iva12.add(ti.getValor());
                codigoPorcentajeIva = ti.getCodigoPorcentaje();
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_0.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva0 = totalIva0.add(ti.getBaseImponible());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod)
                    && (TipoImpuestoIvaEnum.IVA_VENTA_EXCENTO.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
            }
            if (TipoImpuestoEnum.ICE.getCode() == cod) {
                totalICE = totalICE.add(ti.getValor());
            }
        }
        tc.setIva12(iva12.toString());
        tc.setSubtotal0(totalIva0.toString());
        tc.setSubtotal12(totalIva12.toString());
        tc.setTotalIce(totalICE.toString());
        tc.setSubtotal(totalIva0.add(totalIva12));
        tc.setSubtotalNoSujetoIva(totalSinImpuesto.toString());
        tc.setIvaVigente(
                codigoPorcentajeIva.compareTo(TipoImpuestoIvaEnum.IVA_VENTA_14.getCode()) == 0 ? "14%" : "12%");
        return tc;
    }

    public static TotalComprobante getTotalesLiq(LiquidacionCompra.InfoLiquidacionCompra infoLiq) {
        BigDecimal totalIva12 = new BigDecimal(0.0D);
        BigDecimal totalIva0 = new BigDecimal(0.0D);
        BigDecimal iva12 = new BigDecimal(0.0D);
        BigDecimal totalICE = new BigDecimal(0.0D);
        BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
        String codigoPorcentajeIva = "";
        TotalComprobante tc = new TotalComprobante();
        for (LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto ti : infoLiq.getTotalConImpuestos().getTotalImpuesto()) {
            Integer cod = new Integer(ti.getCodigo());
            if ((TipoImpuestoEnum.IVA.getCode() == cod) && (TipoImpuestoIvaEnum.IVA_VENTA_12.getCode().equals(ti.getCodigoPorcentaje())) || TipoImpuestoIvaEnum.IVA_VENTA_14.getCode().equals(ti.getCodigoPorcentaje())) {
                totalIva12 = totalIva12.add(ti.getBaseImponible());
                iva12 = iva12.add(ti.getValor());
                codigoPorcentajeIva = ti.getCodigoPorcentaje();
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod) && (TipoImpuestoIvaEnum.IVA_VENTA_0.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalIva0 = totalIva0.add(ti.getBaseImponible());
            }
            if ((TipoImpuestoEnum.IVA.getCode() == cod) && (TipoImpuestoIvaEnum.IVA_VENTA_EXCENTO.getCode().equals(ti.getCodigoPorcentaje()))) {
                totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
            }
            if (TipoImpuestoEnum.ICE.getCode() == cod) {
                totalICE = totalICE.add(ti.getValor());
            }
        }
        tc.setIva12(iva12.toString());
        tc.setSubtotal0(totalIva0.toString());
        tc.setSubtotal12(totalIva12.toString());
        tc.setTotalIce(totalICE.toString());
        tc.setSubtotal(totalIva0.add(totalIva12));
        tc.setSubtotalNoSujetoIva(totalSinImpuesto.toString());
        tc.setIvaVigente(codigoPorcentajeIva.compareTo(TipoImpuestoIvaEnum.IVA_VENTA_14.getCode()) == 0 ? "14%" : "12%");
        return tc;
    }
}
