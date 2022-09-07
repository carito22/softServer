package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import com.thoughtworks.xstream.XStream;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxComprobantesElectronicosRecibidosDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;

import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.Factura;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.Factura.Detalles.Detalle;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.liquidacioncompra.LiquidacionCompra;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadecredito.NotaCredito;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.rentencion.ImpuestoRetencion;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.ComprobanteRetencionReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.FacturaReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.LiquidacionCompraReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.NotaCreditoReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.ArchivoUtils;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteImportarTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidosPk;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxRetencionesVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoComprobanteEnum;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.XStreamUtil;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.Autorizacion;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Service
public class AnxComprobantesElectronicosRecibidosServiceImpl implements AnxComprobantesElectronicosRecibidosService {

    @Autowired
    private AnxComprobantesElectronicosRecibidosDao anxComprobantesElectronicosRecibidosDao;
    private Boolean comprobar = false;
    private String modulo = "anexos";
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private UrlWebServicesService urlWebServicesService;
    @Autowired
    private AnxHomologacionProductoService anxHomologacionProductoService;
    @Autowired
    private AnxRetencionesVentaService anxRetencionesVentaService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private EmpresaDao empresaDao;

    @Override
    public AnxComprobantesElectronicosRecibidos obtenerAnxComprobantesElectronicosRecibidos(String empresa, String periodo, String clave) throws Exception {
        return anxComprobantesElectronicosRecibidosDao.obtenerAnxComprobantesElectronicosRecibidos(empresa, periodo, clave);
    }

    @Override
    public String obtenerXMLComprobanteElectronico(String empresa, String periodo, String clave) throws Exception {
        return anxComprobantesElectronicosRecibidosDao.obtenerXMLComprobanteElectronico(empresa, periodo, clave);
    }

    @Override
    public Boolean insertarComprobantesElectronicos(List<AnxComprobantesElectronicosRecibidos> listaComprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception {
        Boolean ingresoComprobantes = false;
        List<SisSuceso> listadoSisSuceso = new java.util.ArrayList();
        SisSuceso sisSuceso;
        for (AnxComprobantesElectronicosRecibidos anxComprobantesElectronicosRecibidos : listaComprobantesElectronicos) {
            susClave = anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso();
            susDetalle = "Se insertó registro " + anxComprobantesElectronicosRecibidos.getComproSerieComprobante() + "del perido " + anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk().getComproPeriodo();
            susSuceso = "INSERT";
            susTabla = "anexo.anx_comprobantes_electronicos_recibidos";
            sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //XML
            String claveAcceso = anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso();

            RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(claveAcceso, "2", sisInfoTO);
            if (respuestaComprobante != null) {
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
                        String xmlAutorizacion = outputStream.toString("UTF-8");
                        anxComprobantesElectronicosRecibidos.setComproXml(xmlAutorizacion.getBytes("UTF-8"));
                    }
                }

                anxComprobantesElectronicosRecibidos.setUsrCodigo(sisInfoTO.getUsuario());
                anxComprobantesElectronicosRecibidos.setUsrEmpresa(sisInfoTO.getEmpresa());
                anxComprobantesElectronicosRecibidos.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                listadoSisSuceso.add(sisSuceso);
            }
        }

        if (anxComprobantesElectronicosRecibidosDao.insertarComprobantesElectronicos(listaComprobantesElectronicos, listadoSisSuceso)) {
            ingresoComprobantes = true;
        }

        return ingresoComprobantes;
    }

    @Override
    public Boolean insertarComprobantesElectronico(AnxComprobantesElectronicosRecibidos comprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception {
        Boolean ingresoComprobante = false;
        SisSuceso sisSuceso;

        susClave = comprobantesElectronicos.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso();
        susDetalle = "Se insertó registro " + comprobantesElectronicos.getComproSerieComprobante() + " del perido " + comprobantesElectronicos.getAnxComprobantesElectronicosRecibidosPk().getComproPeriodo();
        susSuceso = "INSERT";
        susTabla = "anexo.anx_comprobantes_electronicos_recibidos";
        sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        String claveAcceso = comprobantesElectronicos.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso();
        if (comprobantesElectronicos.getComproXml() != null && comprobantesElectronicos.getComproXml().length == 0) {
            RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(claveAcceso, "2", sisInfoTO);
            if (respuestaComprobante != null) {
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
                        String xmlAutorizacion = outputStream.toString("UTF-8");
                        comprobantesElectronicos.setComproXml(xmlAutorizacion.getBytes("UTF-8"));
                    }
                }
            }
        }
        comprobantesElectronicos.setUsrCodigo(sisInfoTO.getUsuario());
        comprobantesElectronicos.setUsrEmpresa(sisInfoTO.getEmpresa());
        comprobantesElectronicos.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));

        ingresoComprobante = anxComprobantesElectronicosRecibidosDao.insertarComprobantesElectronico(comprobantesElectronicos, sisSuceso);

        return ingresoComprobante;
    }

    @Override
    public String insertarComprobantesElectronicosRecibidosLote(List<AnxComprobantesElectronicosRecibidos> listaEnviar, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        String empresa = sisInfoTO.getEmpresa();

        try {
            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            for (AnxComprobantesElectronicosRecibidos anxComprobantesElectronicosRecibidos : listaEnviar) {
                AnxComprobantesElectronicosRecibidos enBaseDeDatos = anxComprobantesElectronicosRecibidosDao.obtenerAnxComprobantesElectronicosRecibidosPorPK(anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk());
                String mensajeAux = "";
                if (enBaseDeDatos == null || enBaseDeDatos.getComproXml() == null || enBaseDeDatos.getComproXml().length <= 0) {

                    //**HOMOLOGACION PRODUCTO
                    List<AnxHomologacionProducto> listaHomol = new ArrayList<>();
                    AnxRetencionesVenta anxRetencionesVenta = null;
                    //**************
                    SisSuceso sisSuceso;
                    susClave = anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso();
                    susDetalle = "Se insertó registro " + anxComprobantesElectronicosRecibidos.getComproSerieComprobante() + " del período " + anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk().getComproPeriodo();
                    susSuceso = "INSERT";
                    susTabla = "anexo.anx_comprobantes_electronicos_recibidos";
                    sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    String claveAcceso = anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso();
                    String tipoComprobante = claveAcceso.substring(8, 10);

                    String periodo = claveAcceso.substring(0, 8);

                    if (periodo != null) {
                        String anio = periodo.substring(4, 8);
                        String mes = periodo.substring(2, 4);
                        periodo = anio.concat("-").concat(mes);
                    }
                    try {
                        //xml
                        String mensajeRUC = "";
                        String xmlAutorizacion = null;
                        if (anxComprobantesElectronicosRecibidos.getComproXml() != null && anxComprobantesElectronicosRecibidos.getComproXml().length == 0) {
                            //busca en sri
                            RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(claveAcceso, "2", sisInfoTO);
                            if (respuestaComprobante != null) {
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
                                        anxComprobantesElectronicosRecibidos.setComproXml(xmlAutorizacion.getBytes("UTF-8"));
                                    }
                                }
                            } else {
                                //obtener de la bd si existiese
                                xmlAutorizacion = obtenerXMLComprobanteElectronico(empresa, periodo, claveAcceso);
                                if (xmlAutorizacion != null && !xmlAutorizacion.equals("")) {
                                    anxComprobantesElectronicosRecibidos.setComproXml(xmlAutorizacion.getBytes("UTF-8"));
                                } else {
                                    anxComprobantesElectronicosRecibidos.setComproXml(null);
                                }
                            }
                        }
                        mensajeAux = "";
                        if (anxComprobantesElectronicosRecibidos.getComproXml() == null || anxComprobantesElectronicosRecibidos.getComproXml().equals("")) {
                            mensajeAux
                                    = "F" + anxComprobantesElectronicosRecibidos.getComproComprobante() + " --> "
                                    + "(" + anxComprobantesElectronicosRecibidos.getComproSerieComprobante() + ") "
                                    + "El comprobante electrónico con clave de acceso: " + claveAcceso + ", no se encontró XML del comprobante.";
                        } else {
                            //validar ruc
                            String rucComprador = anxComprobantesElectronicosRecibidos.getComproIdentificacionReceptor();
                            if (rucComprador != null && !rucComprador.equalsIgnoreCase(sisEmpresaParametros.getEmpCodigo().getEmpRuc())) {
                                rucComprador = rucComprador.trim();
                                mensajeRUC = "FRuc: " + rucComprador + " no pertenece a la empresa " + empresa + " ,con RUC:" + sisEmpresaParametros.getEmpCodigo().getEmpRuc();
                                anxComprobantesElectronicosRecibidos.setComproXml(null);
                            }

                            //***************HOMOLGAR PRODUCTO
                            if (anxComprobantesElectronicosRecibidos.getComproXml() != null && anxComprobantesElectronicosRecibidos.getComproXml().length != 0 && mensajeRUC != null && mensajeRUC.equals("")) {
                                AnxHomologacionProducto homl = null;
                                if (xmlAutorizacion == null) {
                                    xmlAutorizacion = new String((byte[]) anxComprobantesElectronicosRecibidos.getComproXml(), "UTF-8");
                                }
                                Node contenidoComprobanteXm = null;
                                Document doc = null;
                                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                factory.setNamespaceAware(true);
                                DocumentBuilder builder = factory.newDocumentBuilder();
                                doc = builder.parse(new ByteArrayInputStream(xmlAutorizacion.getBytes("UTF-8")));
                                contenidoComprobanteXm = doc.getElementsByTagName("comprobante").item(0);

                                String identificacionReceptor = null;
                                String identificacionEmisor = null;
                                String razonSocialEmisor = null;

                                if (tipoComprobante.compareTo("01") == 0 && contenidoComprobanteXm != null) {
                                    FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXm));
                                    Factura factura = facturaReporte.getFactura();
                                    if (factura != null) {
                                        identificacionEmisor = factura.getInfoTributaria().getRuc();
                                        razonSocialEmisor = factura.getInfoTributaria().getRazonSocial();
                                        identificacionReceptor = factura.getInfoFactura().getIdentificacionComprador();
                                        String fecha = (UtilsValidacion.fecha(factura.getInfoFactura().getFechaEmision(), "dd/MM/yyyy", "yyyy-MM-dd"));

                                        anxComprobantesElectronicosRecibidos.setComproRucEmisor(identificacionEmisor);
                                        anxComprobantesElectronicosRecibidos.setComproRazonSocialEmisor(razonSocialEmisor);
                                        anxComprobantesElectronicosRecibidos.setComproIdentificacionReceptor(identificacionReceptor);
                                        anxComprobantesElectronicosRecibidos.setComproFechaEmision(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                        for (Detalle det : factura.getDetalles().getDetalle()) {
                                            homl = new AnxHomologacionProducto();
                                            homl.setHomEmpresa(empresa);
                                            //producto
                                            homl.setHomCodigoProductoProveedor(det.getCodigoPrincipal());
                                            homl.setHomDescripcionProductoProveedor(det.getDescripcion());
                                            //Proveedor
                                            homl.setProvIdentificacion(factura.getInfoTributaria().getRuc());
                                            homl.setProvRazonSocial(factura.getInfoTributaria().getRazonSocial());
                                            //auditoria
                                            homl.setUsrCodigo(sisInfoTO.getUsuario());
                                            homl.setUsrEmpresa(sisInfoTO.getEmpresa());
                                            homl.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                            listaHomol.add(homl);
                                        }
                                    }
                                } else if (tipoComprobante.compareTo("03") == 0) {
                                    LiquidacionCompraReporte liq = new LiquidacionCompraReporte((LiquidacionCompra) ArchivoUtils.unmarshal(LiquidacionCompra.class, contenidoComprobanteXm));
                                    LiquidacionCompra liquidacion = liq.getLiquidacionCompra() != null ? liq.getLiquidacionCompra() : null;
                                    if (liquidacion != null) {
                                        identificacionEmisor = liquidacion.getInfoTributaria().getRuc();
                                        razonSocialEmisor = liquidacion.getInfoTributaria().getRazonSocial();
                                        identificacionReceptor = liquidacion.getInfoLiquidacionCompra().getIdentificacionProveedor();
                                        String fecha = (UtilsValidacion.fecha(liquidacion.getInfoLiquidacionCompra().getFechaEmision(), "dd/MM/yyyy", "yyyy-MM-dd"));

                                        anxComprobantesElectronicosRecibidos.setComproRucEmisor(identificacionEmisor);
                                        anxComprobantesElectronicosRecibidos.setComproRazonSocialEmisor(razonSocialEmisor);
                                        anxComprobantesElectronicosRecibidos.setComproIdentificacionReceptor(identificacionReceptor);
                                        anxComprobantesElectronicosRecibidos.setComproFechaEmision(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));

                                        for (LiquidacionCompra.Detalles.Detalle det : liquidacion.getDetalles().getDetalle()) {
                                            homl = new AnxHomologacionProducto();
                                            homl.setHomEmpresa(empresa);
                                            //producto
                                            homl.setHomCodigoProductoProveedor(det.getCodigoPrincipal());
                                            homl.setHomDescripcionProductoProveedor(det.getDescripcion());
                                            //Proveedor
                                            homl.setProvIdentificacion(liquidacion.getInfoTributaria().getRuc());
                                            homl.setProvRazonSocial(liquidacion.getInfoTributaria().getRazonSocial());
                                            //auditoria
                                            homl.setUsrCodigo(sisInfoTO.getUsuario());
                                            homl.setUsrEmpresa(sisInfoTO.getEmpresa());
                                            homl.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                            listaHomol.add(homl);
                                        }
                                    }
                                } else if (tipoComprobante.compareTo("04") == 0) {
                                    NotaCreditoReporte nc = new NotaCreditoReporte((NotaCredito) ArchivoUtils.unmarshal(NotaCredito.class, contenidoComprobanteXm));
                                    NotaCredito notaCredito = nc.getNotaCredito() != null ? nc.getNotaCredito() : null;

                                    if (notaCredito != null) {
                                        identificacionEmisor = notaCredito.getInfoTributaria().getRuc();
                                        razonSocialEmisor = notaCredito.getInfoTributaria().getRazonSocial();
                                        identificacionReceptor = notaCredito.getInfoNotaCredito().getIdentificacionComprador();
                                        String fecha = (UtilsValidacion.fecha(notaCredito.getInfoNotaCredito().getFechaEmision(), "dd/MM/yyyy", "yyyy-MM-dd"));

                                        anxComprobantesElectronicosRecibidos.setComproRucEmisor(identificacionEmisor);
                                        anxComprobantesElectronicosRecibidos.setComproRazonSocialEmisor(razonSocialEmisor);
                                        anxComprobantesElectronicosRecibidos.setComproIdentificacionReceptor(identificacionReceptor);
                                        anxComprobantesElectronicosRecibidos.setComproFechaEmision(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));

                                        for (NotaCredito.Detalles.Detalle det : notaCredito.getDetalles().getDetalle()) {
                                            homl = new AnxHomologacionProducto();
                                            homl.setHomEmpresa(empresa);
                                            //producto
                                            homl.setHomCodigoProductoProveedor(det.getCodigoInterno());
                                            homl.setHomDescripcionProductoProveedor(det.getDescripcion());
                                            //Proveedor
                                            homl.setProvIdentificacion(notaCredito.getInfoTributaria().getRuc());
                                            homl.setProvRazonSocial(notaCredito.getInfoTributaria().getRazonSocial());
                                            //auditoria
                                            homl.setUsrCodigo(sisInfoTO.getUsuario());
                                            homl.setUsrEmpresa(sisInfoTO.getEmpresa());
                                            homl.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                            listaHomol.add(homl);
                                        }
                                    }
                                } else if (tipoComprobante.compareTo("07") == 0) {
                                    //retenciones
                                    ComprobanteRetencionReporte reten = new ComprobanteRetencionReporte((ComprobanteRetencion) ArchivoUtils.unmarshal(ComprobanteRetencion.class, contenidoComprobanteXm));
                                    ComprobanteRetencion retencion = reten.getComprobanteRetencion() != null ? reten.getComprobanteRetencion() : null;
                                    if (retencion != null) {
                                        identificacionEmisor = retencion.getInfoTributaria().getRuc();
                                        razonSocialEmisor = retencion.getInfoTributaria().getRazonSocial();
                                        identificacionReceptor = retencion.getInfoCompRetencion().getIdentificacionSujetoRetenido();
                                        String fecha = (UtilsValidacion.fecha(retencion.getInfoCompRetencion().getFechaEmision(), "dd/MM/yyyy", "yyyy-MM-dd"));

                                        anxComprobantesElectronicosRecibidos.setComproRucEmisor(identificacionEmisor);
                                        anxComprobantesElectronicosRecibidos.setComproRazonSocialEmisor(razonSocialEmisor);
                                        anxComprobantesElectronicosRecibidos.setComproIdentificacionReceptor(identificacionReceptor);
                                        anxComprobantesElectronicosRecibidos.setComproFechaEmision(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));

                                        anxRetencionesVenta = new AnxRetencionesVenta();
                                        anxRetencionesVenta.setRetEmpresa(empresa);
                                        //cliente
                                        anxRetencionesVenta.setCliIdentificacion(retencion.getInfoTributaria().getRuc());
                                        anxRetencionesVenta.setCliRazonSocial(retencion.getInfoTributaria().getRazonSocial());
                                        //clave acceso
                                        anxRetencionesVenta.setRetClaveAcceso(retencion.getInfoTributaria().getClaveAcceso());
                                        anxRetencionesVenta.setRetAnulado(false);
                                        //numero documento
                                        String numeroDoc = retencion.getInfoTributaria().getEstab() + "-"
                                                + retencion.getInfoTributaria().getPtoEmi() + "-"
                                                + retencion.getInfoTributaria().getSecuencial();
                                        anxRetencionesVenta.setRetNumeroDocumento(numeroDoc);
                                        //impuesto
                                        List<ImpuestoRetencion> listaFiltrada = new ArrayList<>();
                                        if (retencion.getImpuestos() != null && retencion.getImpuestos().getImpuesto() != null) {
                                            for (ImpuestoRetencion det : retencion.getImpuestos().getImpuesto()) {
                                                if (det.getNumDocSustento() != null) {
                                                    String numeroDocumento = det.getNumDocSustento().substring(0, 3) + "-"
                                                            + det.getNumDocSustento().substring(3, 6) + "-"
                                                            + det.getNumDocSustento().substring(6, 15);
                                                    anxRetencionesVenta.setRetNumDocSustento(numeroDocumento);
                                                }
                                                anxRetencionesVenta.setRetDocSustento(det.getCodDocSustento());
                                                if (listaFiltrada.size() == 0) {
                                                    listaFiltrada.add(det);
                                                } else {
                                                    boolean seEncontro = false;
                                                    for (ImpuestoRetencion itemFiltrado : listaFiltrada) {
                                                        if (det.getCodigo().equals(itemFiltrado.getCodigo())) {
                                                            BigDecimal valor = itemFiltrado.getValorRetenido().add(det.getValorRetenido());
                                                            itemFiltrado.setValorRetenido(valor);
                                                            seEncontro = true;
                                                        }
                                                    }
                                                    if (!seEncontro) {
                                                        listaFiltrada.add(det);
                                                    }
                                                }
                                            }
                                            //lista de 2 ítems: con codigo 1(valor_ir) y 2(valor_iva)
                                            for (ImpuestoRetencion det : listaFiltrada) {
                                                if (det.getCodigo().equals("1")) {
                                                    anxRetencionesVenta.setRetValorRetencionIr(det.getValorRetenido());
                                                } else {
                                                    anxRetencionesVenta.setRetValorRetencionIva(det.getValorRetenido());
                                                }
                                            }
                                        } else {
                                            anxRetencionesVenta.setRetValorRetencionIr(BigDecimal.ZERO);
                                            anxRetencionesVenta.setRetValorRetencionIva(BigDecimal.ZERO);
                                        }

                                        //auditoria
                                        anxRetencionesVenta.setUsrCodigo(sisInfoTO.getUsuario());
                                        anxRetencionesVenta.setUsrEmpresa(sisInfoTO.getEmpresa());
                                        anxRetencionesVenta.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                    }
                                }
                            }
                            //*************
                            anxComprobantesElectronicosRecibidos.setUsrCodigo(sisInfoTO.getUsuario());
                            anxComprobantesElectronicosRecibidos.setUsrEmpresa(sisInfoTO.getEmpresa());
                            anxComprobantesElectronicosRecibidos.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));

                            if (xmlAutorizacion != null) {
                                //existe comprobante, entonces actualiza datos
                                anxComprobantesElectronicosRecibidos = actualizarComprobanteXML(xmlAutorizacion, tipoComprobante, anxComprobantesElectronicosRecibidos);
                            }

                            AnxComprobantesElectronicosRecibidos comprobante = anxComprobantesElectronicosRecibidosDao.obtenerPorId(AnxComprobantesElectronicosRecibidos.class, anxComprobantesElectronicosRecibidos.getAnxComprobantesElectronicosRecibidosPk());
                            //insertar
                            if (comprobante == null) {
                                if (anxComprobantesElectronicosRecibidosDao.insertarComprobantesElectronico(anxComprobantesElectronicosRecibidos, sisSuceso)) {
                                    if (xmlAutorizacion != null) {
                                        mensajeAux = "T" + anxComprobantesElectronicosRecibidos.getComproComprobante() + " --> "
                                                + "(" + anxComprobantesElectronicosRecibidos.getComproSerieComprobante() + ") "
                                                + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", ha sido guardado correctamente.";
                                    } else {
                                        mensajeAux = "F" + anxComprobantesElectronicosRecibidos.getComproComprobante() + " --> "
                                                + "(" + anxComprobantesElectronicosRecibidos.getComproSerieComprobante() + ") "
                                                + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", puede que se encuentre anulado, por favor consultelo en el SRI.";
                                    }
                                } else {
                                    mensajeAux = "F" + anxComprobantesElectronicosRecibidos.getComproComprobante() + " --> "
                                            + "(" + anxComprobantesElectronicosRecibidos.getComproSerieComprobante() + ") "
                                            + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", no se pudo guardar";
                                }
                            } else {
                                anxComprobantesElectronicosRecibidosDao.actualizar(anxComprobantesElectronicosRecibidos);
                                mensajeAux = "F" + anxComprobantesElectronicosRecibidos.getComproComprobante() + " --> "
                                        + "(" + anxComprobantesElectronicosRecibidos.getComproSerieComprobante() + ") "
                                        + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", ya se registró anteriormente.";
                            }
                            if (mensajeRUC != null && mensajeRUC.equals("")) {
                                if (mensajeAux != null && !mensajeAux.equals("")) {
                                    if (mensajeAux.substring(0, 1).equals("T")) {
                                        for (AnxHomologacionProducto homol : listaHomol) {
                                            anxHomologacionProductoService.insertarAnxHomologacionProducto(homol, sisInfoTO);
                                        }
                                    }
                                }
                                if (anxRetencionesVenta != null && mensajeAux != null && !mensajeAux.equals("")) {
                                    if (mensajeAux.substring(0, 1).equals("T")) {
                                        anxRetencionesVentaService.insertarAnxRetencionesVenta(anxRetencionesVenta, sisInfoTO);
                                    }
                                }
                            } else {
                                mensajeAux = mensajeRUC;
                            }
                        }

                        mensaje += mensajeAux + "|";
                    } catch (GeneralException e) {
                        mensajeAux = "F" + (e != null ? e.getMessage() : "Error desconocido en comprobante: " + anxComprobantesElectronicosRecibidos.getComproComprobante());
                        mensaje += mensajeAux + "|";
                    } catch (Exception e) {
                        e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                        mensajeAux = "F" + (e != null ? e.getMessage() : "Error desconocido en comprobante: " + anxComprobantesElectronicosRecibidos.getComproComprobante());
                        mensaje += mensajeAux + "|";
                    }
                } else {
                    mensajeAux = "F" + anxComprobantesElectronicosRecibidos.getComproComprobante() + " --> "
                            + "(" + anxComprobantesElectronicosRecibidos.getComproSerieComprobante() + ") "
                            + "El comprobante electrónico con clave de acceso :" + enBaseDeDatos.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso() + ", ya se registró anteriormente.";
                    mensaje += mensajeAux + "|";
                }
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            throw new GeneralException(e.getMessage());
        }
        return mensaje;
    }

    public AnxComprobantesElectronicosRecibidos actualizarComprobanteXML(String xmlAutorizacion, String tipoComprobante, AnxComprobantesElectronicosRecibidos anxComprobantesElectronicosRecibidos) throws Exception {
        //total,complemento y comprobante nombre
        String complemento = null;
        String total = null;
        String comprobanteNombre = null;
        String idReceptorFactura = null;
        String idReceptorRetencion = null;
        String comprobanteSerie = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<estab>") + 7, xmlAutorizacion.lastIndexOf("</estab>")).trim()
                + "-" + xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<ptoEmi>") + 8, xmlAutorizacion.lastIndexOf("</ptoEmi>")).trim()
                + "-" + xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<secuencial>") + 12, xmlAutorizacion.lastIndexOf("</secuencial>")).trim();

        if (tipoComprobante.equals(TipoComprobanteEnum.FACTURA.getCode())) {
            comprobanteNombre = "Factura";
            total = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<importeTotal>") + 14, xmlAutorizacion.lastIndexOf("</importeTotal>")).trim();
            idReceptorFactura = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<identificacionComprador>") + 25, xmlAutorizacion.lastIndexOf("</identificacionComprador>")).trim();
        } else if (tipoComprobante.equals(TipoComprobanteEnum.NOTA_DE_CREDITO.getCode())) {
            comprobanteNombre = "Notas de Crédito";
            complemento = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<numDocModificado>") + 18, xmlAutorizacion.lastIndexOf("</numDocModificado>")).trim();
            total = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<valorModificacion>") + 19, xmlAutorizacion.lastIndexOf("</valorModificacion>")).trim();
            idReceptorFactura = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<identificacionComprador>") + 25, xmlAutorizacion.lastIndexOf("</identificacionComprador>")).trim();
        } else if (tipoComprobante.equals(TipoComprobanteEnum.NOTA_DE_DEBITO.getCode())) {
            comprobanteNombre = "Notas de Débito";
            complemento = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<numDocModificado>") + 18, xmlAutorizacion.lastIndexOf("</numDocModificado>")).trim();
            total = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<valorTotal>") + 12,
                    xmlAutorizacion.lastIndexOf("</valorTotal>")).trim();
            idReceptorFactura = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<identificacionComprador>") + 25, xmlAutorizacion.lastIndexOf("</identificacionComprador>")).trim();

        } else if (tipoComprobante.equals(TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode())) {
            comprobanteNombre = "Comprobante de Retención";
            idReceptorRetencion = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<identificacionSujetoRetenido>") + 30, xmlAutorizacion.lastIndexOf("</identificacionSujetoRetenido>")).trim();
            if (idReceptorRetencion != null) {
                if (!idReceptorRetencion.equals(anxComprobantesElectronicosRecibidos.getComproIdentificacionReceptor())) {
                    anxComprobantesElectronicosRecibidos.setComproIdentificacionReceptor(idReceptorRetencion);
                }
            }
        }
        if (idReceptorFactura != null) {
            if (!idReceptorFactura.equals(anxComprobantesElectronicosRecibidos.getComproIdentificacionReceptor())) {
                anxComprobantesElectronicosRecibidos.setComproIdentificacionReceptor(idReceptorFactura);
            }
        }

        //fecha autorizacion
        if (xmlAutorizacion.contains("fechaAutorizacion")) {
            String fechaAutorizacion = xmlAutorizacion.contains("<fechaAutorizacion>")
                    ? xmlAutorizacion.substring(
                            xmlAutorizacion.lastIndexOf("<fechaAutorizacion>") + 19,
                            xmlAutorizacion.lastIndexOf("</fechaAutorizacion>"))
                    : xmlAutorizacion.substring(
                            xmlAutorizacion.lastIndexOf("<fechaAutorizacion ") + 45,
                            xmlAutorizacion.lastIndexOf("</fechaAutorizacion>"));
            String fechaAutFormato = "";
            if (fechaAutorizacion.contains(("T"))) {
                fechaAutFormato = UtilsValidacion.fecha(fechaAutorizacion, "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
            } else {
                fechaAutFormato = UtilsValidacion.fecha(fechaAutorizacion, "dd/MM/yyyy HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
            }

            Date fechaAut = UtilsValidacion.fecha(fechaAutFormato, "yyyy-MM-dd HH:mm:ss");
            if (fechaAut != null) {
                anxComprobantesElectronicosRecibidos.setComproFechaAutorizacion(fechaAut);
            } else {
                anxComprobantesElectronicosRecibidos.setComproFechaAutorizacion(null);
            }
        }

        anxComprobantesElectronicosRecibidos.setComproSerieComprobante(comprobanteSerie);
        anxComprobantesElectronicosRecibidos.setComproComplemento(complemento);
        anxComprobantesElectronicosRecibidos.setComproComprobante(comprobanteNombre);
        anxComprobantesElectronicosRecibidos.setComproImporteTotal(new BigDecimal(total != null ? total : "0.00"));

        return anxComprobantesElectronicosRecibidos;
    }

    @Override
    public List<AnxComprobantesElectronicosRecibidos> listarComprobantesElectronicos(String empresa, String periodo, String tipoVista) throws Exception {
        List<AnxComprobantesElectronicosRecibidos> lista = new ArrayList<>();
        if (tipoVista == null) {
            if (periodo != null) {
                lista = anxComprobantesElectronicosRecibidosDao.listarComprobantesElectronicosConsulta(empresa, periodo);
            } else {
                lista = anxComprobantesElectronicosRecibidosDao.listarComprobantesElectronicos(empresa);
            }
        } else {
            if (tipoVista.equals("COMPRA")) {
                lista = anxComprobantesElectronicosRecibidosDao.listarComprobantesElectronicosParaCompras(empresa);
            }
        }

        return lista;
    }

    @Override
    public Map<String, Object> cambiarEstadoCancelado(String empresa, String periodo, String clave, boolean estado, String tipo, List<AnxComprobantesElectronicosRecibidos> listadoComprobantes, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> resultado = new HashMap<String, Object>();
        ArrayList<String> listaclavesCorrectas = new ArrayList<>();
        ArrayList<String> listaMensajesRespuesta = new ArrayList<>();
        String[] listaMensajes = new String[]{};
        String mensaje = "";

        if (tipo.equals("individual")) {
            String periodoClave = clave.substring(0, 8);
            if (periodoClave != null) {
                String anio = periodoClave.substring(4, 8);
                String mes = periodoClave.substring(2, 4);
                periodoClave = anio.concat("-").concat(mes);
            }
            String resp = cambiarEstadoComprobanteRecibidos(estado, clave, periodoClave, empresa, sisInfoTO);
            mensaje = resp + "|";
            if (resp != null && resp.substring(0, 1).equals("T")) {
                listaclavesCorrectas.add(clave);
            }
        } else {
            String confirmacion = "";
            for (AnxComprobantesElectronicosRecibidos comprobantes : listadoComprobantes) {
                String claveAcceso = comprobantes.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso();
                String periodoClave = claveAcceso.substring(0, 8);
                if (periodoClave != null) {
                    String anio = periodoClave.substring(4, 8);
                    String mes = periodoClave.substring(2, 4);
                    periodoClave = anio.concat("-").concat(mes);
                }
                String resp = cambiarEstadoComprobanteRecibidos(comprobantes.getComproCancelado(), comprobantes.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso(), periodoClave, empresa, sisInfoTO);
                if (resp != null && resp.substring(0, 1).equals("T")) {
                    listaclavesCorrectas.add(claveAcceso);
                }
                confirmacion = confirmacion + resp + '|';
            }
            mensaje = confirmacion;
        }
        //mensajes listado
        if (mensaje != null && !mensaje.equals("")) {
            listaMensajes = mensaje.split("\\|");
            for (int i = 0; i < listaMensajes.length; i++) {
                //obtener solo mensajes 
                if (listaMensajes[i] != null) {
                    listaMensajesRespuesta.add(listaMensajes[i].substring(0));
                }
            }
        }

        resultado.put("listaclavesCorrectas", listaclavesCorrectas);
        resultado.put("listaMensajesRespuesta", listaMensajesRespuesta);

        return resultado;
    }

    public String cambiarEstadoComprobanteRecibidos(boolean estado, String clave, String periodo, String empresa, SisInfoTO sisInfoTO) throws Exception {
        susClave = clave;
        susSuceso = "UPDATE";
        susTabla = "anexo.anx_comprobantes_electronicos_recibidos";
        susDetalle = "Cambio de estado cancelado a " + estado + " del registro clave acceso = " + clave + "  periodo = " + periodo + " empresa = " + empresa;
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        comprobar = anxComprobantesElectronicosRecibidosDao.cambiarEstadoCancelado(empresa, periodo, clave, estado, sisSuceso);
        if (comprobar) {
            mensaje = "TSe cambió correctamente el estado del registo con clave de acceso: " + clave;
        } else {
            mensaje = "FOcurrio un error al modificar el estado del registro con clave de acceso: " + clave;
        }
        return mensaje;
    }

    @Override
    public String verificarImportados(String empresa, String periodo, SisInfoTO sisInfoTO) throws Exception {
        susClave = empresa + "|" + periodo;
        susDetalle = "Se verificaron los registos electronicos de la empresa " + empresa + " del periodo " + periodo;
        susSuceso = "UPDATE";
        susTabla = "anexo.anx_comprobantes_electronicos_recibidos";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return anxComprobantesElectronicosRecibidosDao.verificarImportados(empresa, periodo, sisSuceso);
    }

    @Override
    public byte[] generarReporteComprobantesRecibidos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AnxComprobantesElectronicosRecibidos> listadoComprobantesRecibidos, SisInfoTO sisInfoTO) throws Exception {
        List<ReporteComprobantesElectronicosRecibidos> listadoComprobante = new ArrayList();
        String nombreReporte = "ReporteListadoComprobantesRecibidos.jrxml";
        for (AnxComprobantesElectronicosRecibidos listado : listadoComprobantesRecibidos) {
            ReporteComprobantesElectronicosRecibidos reporteComprobante = new ReporteComprobantesElectronicosRecibidos();
            reporteComprobante.setComproEstado(listado.getComproCancelado());
            reporteComprobante.setComproComprobante(listado.getComproComprobante());
            reporteComprobante.setComproSerieComprobante(listado.getComproSerieComprobante());
            reporteComprobante.setComproRucEmisor(listado.getComproRucEmisor());
            reporteComprobante.setComproRazonSocialEmisor(listado.getComproRazonSocialEmisor());
            reporteComprobante.setComproFechaEmision(UtilsDate.fechaFormatoString(listado.getComproFechaEmision(), "yyyy-MM-dd"));
            reporteComprobante.setComproFechaAutorizacion(UtilsDate.fechaFormatoString(listado.getComproFechaAutorizacion(), "yyyy-MM-dd HH:mm:ss"));
            reporteComprobante.setComproClaveAcceso(listado.getAnxComprobantesElectronicosRecibidosPk().getComproClaveAcceso());
            reporteComprobante.setComproNumeroAutorizacion(listado.getComproNumeroAutorizacion());
            reporteComprobante.setComproImporteTotal(listado.getComproImporteTotal());
            listadoComprobante.add(reporteComprobante);
        }

        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoComprobante);
    }

    @Override
    public String insertarComprobantesRecibidosLote(List<ComprobanteImportarTO> listaComprobantesElectronicos, SisInfoTO sisInfoTOAux) throws Exception {
        String mensaje = "";
        SisEmpresaTO sisEmpresa = null;
        SisInfoTO sisInfoTO = null;
        if (sisInfoTOAux != null) {
            //SI ES DESDE ACOSUX
            sisInfoTO = sisInfoTOAux;
        } else {
            //SI ES DESDE API PUBLICA
            sisInfoTO = new SisInfoTO();
            sisInfoTO.setEmpresa("SIN EMPRESA");//buscar en xml el ruc para obtener la empresa
            sisInfoTO.setUsuario("SOPORTE");
            sisInfoTO.setMac("");
        }

        try {
            if (listaComprobantesElectronicos != null && listaComprobantesElectronicos.size() > 0) {
                for (ComprobanteImportarTO comprobanteInsertar : listaComprobantesElectronicos) {
                    List<AnxHomologacionProducto> listaHomol = new ArrayList<>();
                    AnxRetencionesVenta anxRetencionesVenta = null;
                    String claveAcceso = comprobanteInsertar.getClaveAcceso();
                    String mensajeAux = "T";
                    String codigoEmpresa = null;
                    if (comprobanteInsertar.getXml() != null && claveAcceso != null) {
                        byte[] result = Base64.getDecoder().decode(comprobanteInsertar.getXml());
                        String xmlAutorizacion = new String((byte[]) result, "UTF-8");
                        String tipoComprobante = claveAcceso.substring(8, 10);
                        String identificacionReceptor = null;
                        String identificacionEmisor = null;
                        String razonSocialEmisor = null;
                        String complemento = null;
                        String total = null;
                        String comprobanteNombre = null;
                        String periodo = claveAcceso.substring(0, 8);
                        String claveAccesoXML = null;
                        if (periodo != null) {
                            String anio = periodo.substring(4, 8);
                            String mes = periodo.substring(2, 4);
                            periodo = anio.concat("-").concat(mes);
                        }
                        String comprobanteSerie = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<estab>") + 7, xmlAutorizacion.lastIndexOf("</estab>")).trim()
                                + "-" + xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<ptoEmi>") + 8, xmlAutorizacion.lastIndexOf("</ptoEmi>")).trim()
                                + "-" + xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<secuencial>") + 12, xmlAutorizacion.lastIndexOf("</secuencial>")).trim();

                        if (comprobanteSerie != null) {
                            String fechaAutorizacion = xmlAutorizacion.substring(
                                    xmlAutorizacion.lastIndexOf("<fechaAutorizacion ") + 45,
                                    xmlAutorizacion.lastIndexOf("</fechaAutorizacion>"));
                            Date fechaAut = UtilsValidacion.fecha((UtilsValidacion.fecha(fechaAutorizacion, "dd/MM/yyyy HH:mm:ss", "yyyy-MM-dd HH:mm:ss")),
                                    "yyyy-MM-dd HH:mm:ss");

                            if (tipoComprobante.equals(TipoComprobanteEnum.FACTURA.getCode())) {
                                comprobanteNombre = "Factura";
                                total = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<importeTotal>") + 14, xmlAutorizacion.lastIndexOf("</importeTotal>")).trim();
                            } else if (tipoComprobante.equals(TipoComprobanteEnum.NOTA_DE_CREDITO.getCode())) {
                                comprobanteNombre = "Notas de Crédito";
                                complemento = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<numDocModificado>") + 18, xmlAutorizacion.lastIndexOf("</numDocModificado>")).trim();
                                total = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<valorModificacion>") + 19, xmlAutorizacion.lastIndexOf("</valorModificacion>")).trim();
                            } else if (tipoComprobante.equals(TipoComprobanteEnum.NOTA_DE_DEBITO.getCode())) {
                                comprobanteNombre = "Notas de Débito";
                                complemento = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<numDocModificado>") + 18, xmlAutorizacion.lastIndexOf("</numDocModificado>")).trim();
                                total = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<valorTotal>") + 12,
                                        xmlAutorizacion.lastIndexOf("</valorTotal>")).trim();
                            } else if (tipoComprobante.equals(TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode())) {
                                comprobanteNombre = "Comprobante de Retención";
                            }

                            AnxComprobantesElectronicosRecibidos recibido = new AnxComprobantesElectronicosRecibidos();
                            recibido.setAnxComprobantesElectronicosRecibidosPk(new AnxComprobantesElectronicosRecibidosPk(null, periodo, claveAcceso));
                            recibido.setComproComprobante(comprobanteNombre);
                            recibido.setComproSerieComprobante(comprobanteSerie);
                            recibido.setComproFechaAutorizacion(fechaAut);
                            recibido.setComproTipoEmision("NORMAL");
                            recibido.setComproNumeroAutorizacion(claveAcceso);
                            recibido.setComproImporteTotal(new BigDecimal(total != null ? total : "0.00"));
                            recibido.setComproXml(xmlAutorizacion.getBytes("UTF-8"));
                            recibido.setComproComplemento(complemento);
                            recibido.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                            //*************OBTENER DATOS DE XML******************
                            Node contenidoComprobanteXm = obtenerNodeComprobante(xmlAutorizacion);
                            if (contenidoComprobanteXm != null) {
                                AnxHomologacionProducto homl = null;
                                if (tipoComprobante.compareTo("01") == 0) {//Factura
                                    FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXm));
                                    Factura factura = facturaReporte != null ? facturaReporte.getFactura() : null;
                                    if (factura != null) {
                                        identificacionEmisor = factura.getInfoTributaria().getRuc();
                                        razonSocialEmisor = factura.getInfoTributaria().getRazonSocial();
                                        identificacionReceptor = factura.getInfoFactura().getIdentificacionComprador();
                                        String fecha = (UtilsValidacion.fecha(factura.getInfoFactura().getFechaEmision(), "dd/MM/yyyy", "yyyy-MM-dd"));

                                        recibido.setComproRucEmisor(identificacionEmisor);
                                        recibido.setComproRazonSocialEmisor(razonSocialEmisor);
                                        recibido.setComproIdentificacionReceptor(identificacionReceptor);
                                        recibido.setComproFechaEmision(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                        claveAccesoXML = factura.getInfoTributaria().getClaveAcceso();
                                        //***************************************HOMOLGAR PRODUCTO**************************************
                                        for (Detalle det : factura.getDetalles().getDetalle()) {
                                            homl = new AnxHomologacionProducto();
                                            //producto
                                            homl.setHomCodigoProductoProveedor(det.getCodigoPrincipal());
                                            homl.setHomDescripcionProductoProveedor(det.getDescripcion());
                                            //Proveedor
                                            homl.setProvIdentificacion(factura.getInfoTributaria().getRuc());
                                            homl.setProvRazonSocial(factura.getInfoTributaria().getRazonSocial());
                                            //auditoria
                                            homl.setUsrCodigo(sisInfoTO.getUsuario());
                                            homl.setUsrEmpresa(sisInfoTO.getEmpresa());
                                            homl.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                            listaHomol.add(homl);
                                        }
                                        //******************************************************************************
                                    }
                                } else if (tipoComprobante.compareTo("04") == 0) {//nota credito 
                                    NotaCreditoReporte nc = new NotaCreditoReporte((NotaCredito) ArchivoUtils.unmarshal(NotaCredito.class, contenidoComprobanteXm));
                                    NotaCredito notaCredito = nc.getNotaCredito() != null ? nc.getNotaCredito() : null;
                                    if (notaCredito != null) {
                                        for (NotaCredito.Detalles.Detalle det : notaCredito.getDetalles().getDetalle()) {
                                            homl = new AnxHomologacionProducto();
                                            //producto
                                            homl.setHomCodigoProductoProveedor(det.getCodigoInterno());
                                            homl.setHomDescripcionProductoProveedor(det.getDescripcion());
                                            //Proveedor
                                            homl.setProvIdentificacion(notaCredito.getInfoTributaria().getRuc());
                                            homl.setProvRazonSocial(notaCredito.getInfoTributaria().getRazonSocial());
                                            //auditoria
                                            homl.setUsrCodigo(sisInfoTO.getUsuario());
                                            homl.setUsrEmpresa(sisInfoTO.getEmpresa());
                                            homl.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                            listaHomol.add(homl);
                                        }
                                    }
                                } else if (tipoComprobante.compareTo("03") == 0) {//liquidacion 
                                    LiquidacionCompraReporte liq = new LiquidacionCompraReporte((LiquidacionCompra) ArchivoUtils.unmarshal(LiquidacionCompra.class, contenidoComprobanteXm));
                                    LiquidacionCompra liquidacion = liq.getLiquidacionCompra() != null ? liq.getLiquidacionCompra() : null;
                                    if (liquidacion != null) {
                                        for (LiquidacionCompra.Detalles.Detalle det : liquidacion.getDetalles().getDetalle()) {
                                            homl = new AnxHomologacionProducto();
                                            //producto
                                            homl.setHomCodigoProductoProveedor(det.getCodigoPrincipal());
                                            homl.setHomDescripcionProductoProveedor(det.getDescripcion());
                                            //Proveedor
                                            homl.setProvIdentificacion(liquidacion.getInfoTributaria().getRuc());
                                            homl.setProvRazonSocial(liquidacion.getInfoTributaria().getRazonSocial());
                                            //auditoria
                                            homl.setUsrCodigo(sisInfoTO.getUsuario());
                                            homl.setUsrEmpresa(sisInfoTO.getEmpresa());
                                            homl.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                            listaHomol.add(homl);
                                        }
                                    }
                                } else if (tipoComprobante.compareTo("07") == 0) {
                                    //retenciones
                                    ComprobanteRetencionReporte reten = new ComprobanteRetencionReporte((ComprobanteRetencion) ArchivoUtils.unmarshal(ComprobanteRetencion.class, contenidoComprobanteXm));
                                    ComprobanteRetencion retencion = reten.getComprobanteRetencion() != null ? reten.getComprobanteRetencion() : null;
                                    if (retencion != null) {
                                        anxRetencionesVenta = new AnxRetencionesVenta();
                                        //cliente
                                        anxRetencionesVenta.setCliIdentificacion(retencion.getInfoTributaria().getRuc());
                                        anxRetencionesVenta.setCliRazonSocial(retencion.getInfoTributaria().getRazonSocial());
                                        //clave acceso
                                        anxRetencionesVenta.setRetClaveAcceso(retencion.getInfoTributaria().getClaveAcceso());
                                        anxRetencionesVenta.setRetAnulado(false);
                                        //numero documento
                                        String numeroDoc = retencion.getInfoTributaria().getEstab() + "-"
                                                + retencion.getInfoTributaria().getPtoEmi() + "-"
                                                + retencion.getInfoTributaria().getSecuencial();
                                        anxRetencionesVenta.setRetNumeroDocumento(numeroDoc);
                                        //impuesto
                                        List<ImpuestoRetencion> listaFiltrada = new ArrayList<>();
                                        if (retencion.getImpuestos() != null && retencion.getImpuestos().getImpuesto() != null) {
                                            for (ImpuestoRetencion det : retencion.getImpuestos().getImpuesto()) {
                                                if (det.getNumDocSustento() != null) {
                                                    String numeroDocumento = det.getNumDocSustento().substring(0, 3) + "-"
                                                            + det.getNumDocSustento().substring(3, 6) + "-"
                                                            + det.getNumDocSustento().substring(6, 15);
                                                    anxRetencionesVenta.setRetNumDocSustento(numeroDocumento);
                                                }
                                                anxRetencionesVenta.setRetDocSustento(det.getCodDocSustento());
                                                if (listaFiltrada.size() == 0) {
                                                    listaFiltrada.add(det);
                                                } else {
                                                    boolean seEncontro = false;
                                                    for (ImpuestoRetencion itemFiltrado : listaFiltrada) {
                                                        if (det.getCodigo().equals(itemFiltrado.getCodigo())) {
                                                            BigDecimal valor = itemFiltrado.getValorRetenido().add(det.getValorRetenido());
                                                            itemFiltrado.setValorRetenido(valor);
                                                            seEncontro = true;
                                                        }
                                                    }
                                                    if (!seEncontro) {
                                                        listaFiltrada.add(det);
                                                    }
                                                }
                                            }
                                            //lista de 2 ítems: con codigo 1(valor_ir) y 2(valor_iva)
                                            for (ImpuestoRetencion det : listaFiltrada) {
                                                if (det.getCodigo().equals("1")) {
                                                    anxRetencionesVenta.setRetValorRetencionIr(det.getValorRetenido());
                                                } else {
                                                    anxRetencionesVenta.setRetValorRetencionIva(det.getValorRetenido());
                                                }
                                            }
                                        } else {
                                            anxRetencionesVenta.setRetValorRetencionIr(BigDecimal.ZERO);
                                            anxRetencionesVenta.setRetValorRetencionIva(BigDecimal.ZERO);
                                        }

                                    }
                                }

                                if (identificacionReceptor != null) {
                                    //se ingresa desde acosux

                                    if (sisInfoTOAux != null) {
                                        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTOAux.getEmpresa());
                                        if (sisEmpresaParametros != null) {
                                            if (!identificacionReceptor.equalsIgnoreCase(sisEmpresaParametros.getEmpCodigo().getEmpRuc())) {
                                                mensajeAux = "FRuc de receptor: " + identificacionReceptor + " no pertenece a la empresa "
                                                        + sisInfoTOAux.getEmpresa() + " , con RUC:" + sisEmpresaParametros.getEmpCodigo().getEmpRuc();
                                            } else {
                                                codigoEmpresa = sisInfoTOAux.getEmpresa();
                                            }
                                        } else {
                                            mensajeAux = "FNo existe parámetros de la empresa: " + sisInfoTOAux.getEmpresa();
                                        }
                                    } else {
                                        //se ingresa desde microservicio
                                        codigoEmpresa = "RMG";
                                    }

                                    if (codigoEmpresa != null) {
                                        sisInfoTO.setEmpresa(codigoEmpresa);
                                        recibido.getAnxComprobantesElectronicosRecibidosPk().setComproEmpresa(codigoEmpresa);
                                        recibido.setUsrCodigo(sisInfoTO.getUsuario());
                                        recibido.setUsrEmpresa(codigoEmpresa);
                                        if (claveAccesoXML != null && claveAccesoXML.equals(claveAcceso)) {
                                            //INSERCIÓN O ACTUALIZACIÓN DE COMPROBANTE EMITIDO
                                            AnxComprobantesElectronicosRecibidos comprobante = anxComprobantesElectronicosRecibidosDao.obtenerPorId(
                                                    AnxComprobantesElectronicosRecibidos.class,
                                                    recibido.getAnxComprobantesElectronicosRecibidosPk());

                                            //preparando suceso
                                            susClave = claveAcceso;
                                            susDetalle = "Se insertó comprobante: " + comprobanteSerie + "del período " + periodo;
                                            susSuceso = "INSERT";
                                            susTabla = "anexo.anx_comprobantes_electronicos_recibidos";
                                            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                            //actualizar
                                            if (comprobante != null) {
                                                sisSuceso.setSusSuceso("UPDATE");
                                                sisSuceso.setSusDetalle("Se actualizó comprobante: " + comprobanteSerie + "del período " + periodo);
                                                if (anxComprobantesElectronicosRecibidosDao.actualizarComprobantesElectronico(recibido, sisSuceso)) {
                                                    mensajeAux
                                                            = "T" + recibido.getComproComprobante() + " --> "
                                                            + "(" + comprobanteSerie + ") "
                                                            + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", se ha actualizado correctamente.";
                                                } else {
                                                    mensajeAux = "F" + recibido.getComproComprobante() + " --> "
                                                            + "(" + comprobanteSerie + ") "
                                                            + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", no se pudo actualizar";
                                                }
                                            } else {
                                                //insertar
                                                if (anxComprobantesElectronicosRecibidosDao.insertarComprobantesElectronico(recibido, sisSuceso)) {
                                                    mensajeAux = "T" + recibido.getComproComprobante() + " --> "
                                                            + "(" + recibido.getComproSerieComprobante() + ") "
                                                            + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", se ha guardado correctamente.";
                                                } else {
                                                    mensajeAux = "F" + recibido.getComproComprobante() + " --> "
                                                            + "(" + comprobanteSerie + ") "
                                                            + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", no se pudo guardar";
                                                }
                                            }
                                        } else {
                                            mensajeAux = "FLa clave de acceso:" + claveAcceso + " no pertenece al xml";
                                        }
                                    } else {
                                        mensajeAux = "FNo se logró encontrar la empresa en el sistema con el siguiente RUC: " + identificacionEmisor;
                                    }
                                } else {
                                    mensajeAux = "FNo se encontró RUC de emisor en el archivo XML";
                                    if (tipoComprobante.compareTo("05") == 0) {
                                        mensajeAux += " ,falta implementar para NOTA DE DÉBITO";
                                    }
                                }
                            }
                        } else {
                            mensajeAux = "FEl XML es incorrecto";
                        }
                    } else {
                        mensajeAux = "FDebe ingresar clave de acceso y xml";
                    }

                    if (codigoEmpresa != null) {
                        if (listaHomol != null && mensajeAux != null && mensajeAux.substring(0, 1).equals("T")) {
                            for (AnxHomologacionProducto homol : listaHomol) {
                                homol.setHomEmpresa(codigoEmpresa);
                                homol.setUsrEmpresa(codigoEmpresa);
                                anxHomologacionProductoService.insertarAnxHomologacionProducto(homol, sisInfoTO);
                            }
                        }
                        if (anxRetencionesVenta != null && mensajeAux != null && mensajeAux.substring(0, 1).equals("T")) {
                            anxRetencionesVenta.setRetEmpresa(codigoEmpresa);
                            anxRetencionesVenta.setUsrCodigo(sisInfoTO.getUsuario());
                            anxRetencionesVenta.setUsrEmpresa(codigoEmpresa);
                            anxRetencionesVenta.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                            anxRetencionesVentaService.insertarAnxRetencionesVenta(anxRetencionesVenta, sisInfoTO);
                        }
                    }

                    mensaje += mensajeAux + "|";
                }
            } else {
                mensaje += "FNo existe listado por importar" + "|";
            }
        } catch (GeneralException e) {
            UtilsExcepciones.enviarError(e, "AnxComprobantesElectronicosEmitidosService-insertarComprobantesEmitidosLote", sisInfoTO);
            throw new GeneralException(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            UtilsExcepciones.enviarError(e, "AnxComprobantesElectronicosEmitidosService-insertarComprobantesEmitidosLote", sisInfoTO);
            throw new GeneralException(e.getMessage());
        }
        return mensaje;
    }

    public Node obtenerNodeComprobante(String xmlAutorizacion) throws Exception {
        Node contenidoComprobanteXm = null;
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(new ByteArrayInputStream(xmlAutorizacion.getBytes("UTF-8")));
        contenidoComprobanteXm = doc.getElementsByTagName("comprobante").item(0);

        return contenidoComprobanteXm;
    }
}
