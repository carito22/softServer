package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.UrlWebServicesDao;
import ec.com.todocompu.ShrimpSoftServer.caja.dao.CajaDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasComplementoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClientesDireccionesService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.GuiaRemisionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.LiquidacionComprasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProveedorService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentaGuiaRemisionService;
import ec.com.todocompu.ShrimpSoftServer.publico.dao.DocumentosAnterioresDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.FirmaElectronicaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.EmpresaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.FirmaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.ClaveContingencia;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.Emisor;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.FormGenerales;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.UtilsWebService;
import ec.com.todocompu.ShrimpSoftUtils.Desencriptar;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.anxUrlWebServicesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.delegate.CarteraVerificarDeudasNelsonBenavidesDelegate;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportista;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplemento;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoAmbienteEnum;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoComprobanteEnum;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class EnviarComprobantesWSServiceImpl implements EnviarComprobantesWSService {

    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private CompraElectronicaService compraElectronicaService;
    @Autowired
    private CompraDao compraDao;
    @Autowired
    private VentasDetalleDao ventasDetalleDao;
    @Autowired
    private CompraDetalleDao compraDetalleDao;
    @Autowired
    private CajaDao cajaDao;
    @Autowired
    private UrlWebServicesDao urlWebServicesDao;
    @Autowired
    private UrlWebServicesService urlWebServicesService;
    @Autowired
    private ComprasService comprasService;
    @Autowired
    private CompraService compraService;
    @Autowired
    private VentasDao ventasDao;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private VentasComplementoDao ventasComplementoDao;
    @Autowired
    private TipoTransaccionService tipoTransaccionService;
    @Autowired
    private VentaElectronicaService ventaElectronicaService;
    @Autowired
    private ComprasDetalleDao comprasDetalleDao;
    @Autowired
    private LiquidacionComprasService liquidacionComprasService;
    @Autowired
    private GuiaElectronicaService guiaElectronicaService;
    @Autowired
    private GuiaRemisionService guiaRemisionService;
    @Autowired
    private VentaGuiaRemisionService ventaGuiaRemisionService;
    @Autowired
    private EnviarCorreoService enviarCorreoService;
    @Autowired
    private DocumentosAnterioresDao documentosAnterioresDao;
    @Autowired
    private ClientesDireccionesService clientesDireccionesService;
    @Autowired
    private ExportacionesService exportacionesService;
    @Autowired
    private FirmaElectronicaDao firmaElectronicaDao;
    @Autowired
    private FirmaElectronicaService firmaElectronicaService;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private AnxVentaReembolsoService anxVentaReembolsoService;

    @Override
    public void enviarEmailComprobantesPendientes(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception {
        List<AnxListaRetencionesPendientesTO> anxListaPendientesTOs = compraService.getAnexoListaRetencionesPendienteAutomaticasTO(sisEmpresaTO.getEmpCodigo());
        List<AnxListaVentasPendientesTO> anxListaVentasPendientesTOs = ventaElectronicaService.getListaVentasPendientesAutorizacionAutomatica(sisEmpresaTO.getEmpCodigo());
        String html = "Los siguientes documentos electronicos no fueron autorizados aún: <br>";
        int hayAlgoPorEnviar = 0;
        if (anxListaVentasPendientesTOs != null && !anxListaVentasPendientesTOs.isEmpty()) {
            hayAlgoPorEnviar++;
            html = html + "<br>VENTAS: <br>";
            for (AnxListaVentasPendientesTO anxListaVentasPendientesTO : anxListaVentasPendientesTOs) {
                html = html + "<li>" + anxListaVentasPendientesTO.getVtaDocumentoNumero() + "</li>";
            }
        }
        if (anxListaPendientesTOs != null && !anxListaPendientesTOs.isEmpty()) {
            hayAlgoPorEnviar++;
            html = html + "<br>RETENCIONES: <br>";
            for (AnxListaRetencionesPendientesTO anxListaPendientesTO : anxListaPendientesTOs) {
                html = html + "<li>" + anxListaPendientesTO.getRetRetencionNumero() + "</li>";
            }
        }
        if (hayAlgoPorEnviar > 0) {
            enviarCorreoService.enviarErrorPersonalizado("DOCUMENTOS ELECTRONICOS PENDIENTES DE AUTORIZACION", html, sisInfoTO.getEmpresa());
        }
    }

    public void enviarEmailComprobantesConErroresAutomaticos(List<String> listaErroresComprobantes, String tipoComprobante, SisInfoTO sisInfoTO) throws Exception {
        String html = "Los siguientes documentos electronicos no fueron autorizados aún: <br>";
        int hayAlgoPorEnviar = 0;
        if (listaErroresComprobantes != null && !listaErroresComprobantes.isEmpty()) {
            hayAlgoPorEnviar++;
            html = html + "<br>" + tipoComprobante + ": <br>";
            for (String mensaje : listaErroresComprobantes) {
                html = html + "<li>" + mensaje + "</li>";
            }
        }
        if (hayAlgoPorEnviar > 0) {
            enviarCorreoService.enviarErrorPersonalizado("DOCUMENTOS ELECTRONICOS AUTOMATICOS CON ERRORES EN AUTORIZACION", html, sisInfoTO.getEmpresa());
        }
    }

    @Override
    public void enviarAutorizarRetencionesElectronicasLoteAutomatico(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception {
        List<AnxListaRetencionesPendientesTO> anxListaPendientesTOs = compraService.getAnexoListaRetencionesPendienteAutomaticasTO(sisEmpresaTO.getEmpCodigo());
        String empresa = sisInfoTO.getEmpresa();
        if (anxListaPendientesTOs != null && !anxListaPendientesTOs.isEmpty()) {
            List<String> listaErroresComprobantes = new ArrayList<>();
            int contadorComprobantesAutorizados = 0;
            int contadorComprobantesNoAutorizados = 0;
            System.out.println("\nResultado Envio Retenciones Automáticas ->");
            SisFirmaElectronica cajCajaTO = validarRequisitosParaEnviarAutorizacion(sisInfoTO);
            for (AnxListaRetencionesPendientesTO compra : anxListaPendientesTOs) {
                List<String> comprobantes = UtilsValidacion.separarComprobante(compra.getRetLlaveCompra());
                String periodo = comprobantes.get(0);
                String motivo = comprobantes.get(1);
                String numero = comprobantes.get(2);
                String mensajeAux = autorizarRetencion(empresa, compra, periodo, motivo, numero, cajCajaTO, sisInfoTO);
                if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                    contadorComprobantesAutorizados++;
                    if (mensajeAux.contains("AUTORIZADO") && !mensajeAux.contains("NO AUTORIZADO") && !mensajeAux.contains("erro")) {
                        String xml = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
                        String claveAcceso = xml.substring(xml.lastIndexOf("<claveAcceso>") + 13, xml.lastIndexOf("</claveAcceso>")).trim();
                        compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, periodo, motivo, numero, claveAcceso, "reportComprobanteRetencionRide", xml, sisInfoTO);
                    }
                } else {
                    listaErroresComprobantes.add(mensajeAux != null ? mensajeAux.substring(1) : "Error desconocido.");
                    contadorComprobantesNoAutorizados++;
                }
            }
            if (contadorComprobantesAutorizados == 0) {
                System.out.println("\tSe enviaron, pero no se pudo autorizar ningún comprobante de retención!");
            }
            if (contadorComprobantesNoAutorizados == 0) {
                if (contadorComprobantesAutorizados == 1) {
                    System.out.println("\tSe envió y autorizó su comprobante  de retención!");
                } else {
                    System.out.println("\tSe enviaron y autorizaron todos los comprobantes  de retención!");
                }
            } else {
                System.out.println("\tSe enviaron los comprobantes de retención pero algunos no pudieron ser autorizados");
            }
            try {
                enviarEmailComprobantesConErroresAutomaticos(listaErroresComprobantes, "RETENCIONES", sisInfoTO);
            } catch (Exception e) {
            } finally {
                System.out.println("Fin del proceso!\n");
            }
        }
    }

    public String autorizarRetencion(String empresa, AnxListaRetencionesPendientesTO factura, String periodo, String motivo, String numero, SisFirmaElectronica cajCajaTO, SisInfoTO sisInfoTO) throws Exception {
        String mensajeAux = "";
        try {
            mensajeAux = enviarAutorizarRetencionElectronica(empresa, periodo, motivo, numero,
                    factura.getRetAmbiente().compareToIgnoreCase("PRODUCCION") == 0 ? TipoAmbienteEnum.PRODUCCION.getCode() : TipoAmbienteEnum.PRUEBAS.getCode(),
                    'I', cajCajaTO, sisInfoTO);
            if (mensajeAux == null || mensajeAux.equals("")) {
                return "FError inesperado en el comprobante electrónico : " + factura.getRetRetencionNumero();
            }
        } catch (Exception e) {
            mensajeAux += e.getMessage() != null ? "F" + e.getMessage() + ":" + factura.getRetRetencionNumero() : "FError inesperado en el comprobante electrónico " + ":" + factura.getRetRetencionNumero();
        }
        return mensajeAux;
    }

    @Override
    public String enviarAutorizarRetencionElectronicaLote(String empresa, List<AnxListaRetencionesPendientesTO> listaEnviar, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        SisFirmaElectronica cajCajaTO = validarRequisitosParaEnviarAutorizacion(sisInfoTO);
        for (AnxListaRetencionesPendientesTO compra : listaEnviar) {
            List<String> comprobantes = UtilsValidacion.separarComprobante(compra.getRetLlaveCompra());
            String periodo = comprobantes.get(0);
            String motivo = comprobantes.get(1);
            String numero = comprobantes.get(2);
            String mensajeAux = enviarAutorizarRetencionElectronica(empresa, periodo, motivo, numero,
                    compra.getRetAmbiente().compareToIgnoreCase("PRODUCCION") == 0
                    ? TipoAmbienteEnum.PRODUCCION.getCode()
                    : TipoAmbienteEnum.PRUEBAS.getCode(),
                    'I', cajCajaTO, sisInfoTO);
            if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                mensaje += mensajeAux;
                if (mensajeAux.contains("AUTORIZADO") && !mensajeAux.contains("NO AUTORIZADO") && !mensajeAux.contains("erro")) {
                    String xml = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
                    String claveAcceso = xml.substring(xml.lastIndexOf("<claveAcceso>") + 13, xml.lastIndexOf("</claveAcceso>")).trim();
                    mensaje += ", " + compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, periodo, motivo, numero, claveAcceso, "reportComprobanteRetencionRide", xml, sisInfoTO).substring(1) + ")|";
                }
            } else {
                mensaje += mensajeAux;
            }
        }
        System.out.println("mensaje final " + mensaje);
        return mensaje;
    }

    @Override
    public String enviarAutorizarRetencionElectronica(
            String empresa,
            String perCodigo,
            String motCodigo,
            String compNumero,
            String tipoAmbiente,
            char accion,
            SisFirmaElectronica cajCajaTO,
            SisInfoTO sisInfoTO) throws Exception {

        String mensaje;
        String claveAcceso = null;
        Date fechaEmision;
        Desencriptar desencriptar1 = new Desencriptar();
        String passSignature = desencriptar1.desencriptarMod(cajCajaTO.getFirmaClave());

        if (passSignature != null) {
            if (cajCajaTO.getFirmaElectronicaNombre() != null && cajCajaTO.getFirmaElectronicaNombre().contains("*")) {
                mensaje = "CONECTION REFUSED: Service unavailable";
            } else {
                InvComprasTO invComprasTO = comprasService.getComprasTO(empresa, perCodigo, motCodigo, compNumero);
                if (invComprasTO == null) {
                    mensaje = "FProblema al Verificar La compra...";
                } else {
                    AnxCompraTO anxCompraTO = compraDao.getAnexoCompraTO(empresa, perCodigo, motCodigo, compNumero);
                    if (anxCompraTO == null) {
                        mensaje = "FLa compra no tiene retención ...";
                    } else {
                        InvProveedor invProveedor = proveedorService.buscarInvProveedor(empresa, invComprasTO.getProvCodigo());
                        if (invProveedor != null && invProveedor.getProvIdTipo() != null) {
                            String mensajeProv = "FLa identificación del proveedor: " + invProveedor.getProvIdNumero() + ", no coincide con la longitud permitida.";
                            if (invProveedor.getProvIdTipo().equals('C')) {
                                if (invProveedor.getProvIdNumero() == null || invProveedor.getProvIdNumero().length() != 10) {
                                    return mensajeProv;
                                }
                            }
                            if (invProveedor.getProvIdTipo().equals('R')) {
                                if (invProveedor.getProvIdNumero() == null || invProveedor.getProvIdNumero().length() != 13) {
                                    return mensajeProv;
                                }
                            }
                        }
                        List<AnxCompraDetalleTO> listAnxCompraDetalleTO = compraDetalleDao.getAnexoCompraDetalleTO(empresa, perCodigo, motCodigo, compNumero);
                        Emisor emisor = llenarDatosEmisor(empresa, anxCompraTO.getCompRetencionNumero(), tipoAmbiente);
                        fechaEmision = UtilsValidacion.fecha(UtilsValidacion.fecha(anxCompraTO.getCompRetencionFechaEmision(), "yyyy-MM-dd", "dd/MM/yyyy"), "dd/MM/yyyy");

                        if (anxCompraTO.getCompClaveAccesoExterna() != null && !anxCompraTO.getCompClaveAccesoExterna().equals("")) {
                            claveAcceso = anxCompraTO.getCompClaveAccesoExterna();
                        } else {
                            ClaveContingencia claveContingencia = new FormGenerales().obtieneClaveDeAcceso(
                                    anxCompraTO.getCompRetencionNumero().substring(8), emisor, claveAcceso, fechaEmision,
                                    TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode());
                            if ((claveContingencia.getCodigoComprobante() != null) && (!claveContingencia.getCodigoComprobante().isEmpty())) {
                                claveAcceso = claveContingencia.getCodigoComprobante();
                            }
                        }

                        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, anxCompraTO.getEmpCodigo());
                        boolean agregarRetencion = sisEmpresaParametros.getParAgenteRetencion() != null && !sisEmpresaParametros.getParAgenteRetencion().equals("");
                        String agenteRetencion = agregarRetencion ? sisEmpresaParametros.getParAgenteRetencion() : null;

                        RespuestaWebTO mensajeWS = urlWebServicesService.enviarAutorizarRetencion(claveAcceso, agenteRetencion, passSignature, invProveedor, emisor, invComprasTO, anxCompraTO, listAnxCompraDetalleTO, cajCajaTO.getFirmaElectronicaArchivo(), sisEmpresaParametros);
                        String estadoRpta = "F";
                        if (mensajeWS != null) {
                            mensaje = mensajeWS.getOperacionMensaje();
                            if (mensaje != null && mensaje.equals("AUTORIZADO")) {
                                AnxCompraElectronicaTO anxCompraElectronicaTO = UtilsJSON.jsonToObjeto(AnxCompraElectronicaTO.class, mensajeWS.getExtraInfo());
                                estadoRpta = "T";
                                compraElectronicaService.accionAnxCompraElectronica(anxCompraElectronicaTO, accion, sisInfoTO);
                            }
                        } else {
                            mensaje = "FEl microservicio no devolvió ninguna respuesta.";
                        }
                        mensaje = estadoRpta + "(" + (emisor.getTipoAmbiente().equals("1") ? "PRUEBA" : "PRODUCCIÓN") + ", "
                                + anxCompraTO.getCompRetencionNumero() + ", "
                                + invComprasTO.getCompFecha() + ", "
                                + mensaje + ", "
                                + (invProveedor != null ? invProveedor.getProvRazonSocial() : "Sin proveedor.");
                    }
                }
            }
        } else {
            mensaje = "FNo existe contraseña";
        }
        return mensaje;
    }

    @Override
    public String descargarAutorizarRetencionElectronica(String empresa, String perCodigo, String motCodigo,
            String compNumero, String tipoAmbiente, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        Emisor emisor;
        String claveAcceso = null;
        ClaveContingencia claveContingencia;
        Date fechaEmision;

        anxUrlWebServicesTO urlWebServicesTO = urlWebServicesDao.getAnxUrlWebServicesTO();
        if (urlWebServicesTO == null || urlWebServicesTO.getUrlAmbienteProduccion() == null || urlWebServicesTO.getUrlAmbientePruebas() == null) {
            mensaje = "FFalta configurar la URL del web service...";
        } else {
            InvComprasTO invComprasTO = comprasService.getComprasTO(empresa, perCodigo, motCodigo, compNumero);
            if (invComprasTO == null) {
                mensaje = "FProblema al Verificar La compra...";
            } else {
                AnxCompraTO anxCompraTO = compraDao.getAnexoCompraTO(empresa, perCodigo, motCodigo, compNumero);
                if (anxCompraTO == null) {
                    mensaje = "FLa compra No tiene Retencion ...";
                } else {
                    InvProveedor invProveedor = proveedorService.buscarInvProveedor(empresa, invComprasTO.getProvCodigo());
                    emisor = llenarDatosEmisor(empresa, anxCompraTO.getCompRetencionNumero(), tipoAmbiente);
                    fechaEmision = UtilsValidacion.fecha(UtilsValidacion.fecha(anxCompraTO.getCompRetencionFechaEmision(), "yyyy-MM-dd", "dd/MM/yyyy"), "dd/MM/yyyy");

                    if (anxCompraTO.getCompClaveAccesoExterna() != null && !anxCompraTO.getCompClaveAccesoExterna().equals("")) {
                        claveAcceso = anxCompraTO.getCompClaveAccesoExterna();
                    } else {
                        claveContingencia = new FormGenerales().obtieneClaveDeAcceso(
                                anxCompraTO.getCompRetencionNumero().substring(8), emisor, claveAcceso, fechaEmision,
                                TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode());
                        if ((claveContingencia.getCodigoComprobante() != null) && (!claveContingencia.getCodigoComprobante().isEmpty())) {
                            claveAcceso = claveContingencia.getCodigoComprobante();
                        }
                    }
                    String respuestaAutoriz = urlWebServicesService.generarXmlComprobantes(claveAcceso, tipoAmbiente);
                    if (respuestaAutoriz == null) {
                        mensaje = "F<ERROR>" + "El comprobante esta en proceso de espera";
                    } else if (respuestaAutoriz.equals("AUTORIZADO")) {
//                        AnxCompraElectronicaTO anxCompraElectronicaTO = LlenarObjetoAnx.llenarObjetoAnxCompraElectronica(respuestaAutoriz, anxCompraTO, tipoAmbiente, claveAcceso, sisInfoTO);
//                        mensaje = compraElectronicaService.accionAnxCompraElectronica(anxCompraElectronicaTO, 'M', sisInfoTO);
                        if (mensaje != null && mensaje.charAt(0) == 'T') {
                            mensaje = "T<html>  Respuesta WS SRI: <font size = 5> " + respuestaAutoriz + "</font>.<br>"
                                    + "        Proveedor : <font size = 5> " + invProveedor.getProvRazonSocial()
                                    + "</font>.<br>" + "        Ambiente  : <font size = 5> "
                                    + (emisor.getTipoAmbiente().equals("1") ? "PRUEBA" : "PRODUCCIÓN")
                                    + "</font>.<br></html>";
                        } else {
                            mensaje = "F<html><font size = 5>" + mensaje + "</font>.<br></html>";
                        }
                    } else if (respuestaAutoriz.isEmpty()) {
                        mensaje = "FNo Existe la Clave de acceso Contacte al administrador";
                    } else {
                        String estado = respuestaAutoriz.substring(0, respuestaAutoriz.lastIndexOf("|"));
                        String resultado = respuestaAutoriz.substring(respuestaAutoriz.lastIndexOf("|") + 1, respuestaAutoriz.length());
                        mensaje = "FEl comprobante fue guardado, firmado y enviado exitósamente, pero no fue Autorizado\n"
                                + "<" + estado + ">" + "\n"
                                + FormGenerales.insertarCaracteres(resultado, "\n", 160);
                    }
                }
            }
        }
        return mensaje;
    }

    @Override
    public void enviarAutorizarFacturasElectronicaLoteAutomatico(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception {
        List<AnxListaVentasPendientesTO> anxListaVentasPendientesTOs = ventaElectronicaService.getListaVentasPendientesAutorizacionAutomatica(sisEmpresaTO.getEmpCodigo());
        String resultadoEnvio = "";
        if (anxListaVentasPendientesTOs != null && !anxListaVentasPendientesTOs.isEmpty()) {
            List<String> listaErroresComprobantes = new ArrayList<>();
            int contadorComprobantesAutorizados = 0;
            int contadorComprobantesNoAutorizados = 0;
            System.out.println("\nResultado Envio Ventas Automáticas ->");
            SisFirmaElectronica cajCajaTO = validarRequisitosParaEnviarAutorizacion(sisInfoTO);
            for (int i = 0; i < anxListaVentasPendientesTOs.size(); i++) {
                try {
                    resultadoEnvio = autorizarFactura(sisEmpresaTO.getEmpCodigo(), anxListaVentasPendientesTOs.get(i), cajCajaTO, sisInfoTO);
                    if (resultadoEnvio != null && resultadoEnvio.charAt(0) == 'T') {
                        contadorComprobantesAutorizados++;
                    } else {
                        listaErroresComprobantes.add((resultadoEnvio != null ? resultadoEnvio.substring(1) : "Error desconocido.") + " Documento: " + anxListaVentasPendientesTOs.get(i).getVtaDocumentoNumero());
                        contadorComprobantesNoAutorizados++;
                    }
                } catch (Exception e) {
                    listaErroresComprobantes.add((e.getMessage() != null ? e.getMessage() : "Error desconocido.") + " Documento: " + anxListaVentasPendientesTOs.get(i).getVtaDocumentoNumero());
                    contadorComprobantesNoAutorizados++;
                }
            }
            if (contadorComprobantesAutorizados == 0) {
                System.out.println("\tSe enviaron, pero no se pudo autorizar ningún comprobante!");
            }
            if (contadorComprobantesNoAutorizados == 0) {
                if (contadorComprobantesAutorizados == 1) {
                    System.out.println("\tSe envió y autorizó su comprobante electrónico!");
                } else {
                    System.out.println("\tSe enviaron y autorizaron todos los comprobantes!");
                }
            } else {
                System.out.println("\tSe enviaron los comprobantes pero algunos no pudieron ser autorizados");
            }
            try {
                enviarEmailComprobantesConErroresAutomaticos(listaErroresComprobantes, "VENTAS", sisInfoTO);
            } catch (Exception e) {
            } finally {
                System.out.println("Fin del proceso!\n");
            }
        }
    }

    @Override
    public String enviarAutorizarFaturasElectronicaLote(String empresa, List<AnxListaVentasPendientesTO> listaEnviar,
            SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        SisFirmaElectronica cajCajaTO = validarRequisitosParaEnviarAutorizacion(sisInfoTO);
        for (AnxListaVentasPendientesTO factura : listaEnviar) {
            mensaje += autorizarFactura(empresa, factura, cajCajaTO, sisInfoTO);
            mensaje += ")|";
        }
        return mensaje;
    }

    public String autorizarFactura(String empresa, AnxListaVentasPendientesTO factura, SisFirmaElectronica cajCajaTO, SisInfoTO sisInfoTO) throws Exception {
        String mensajeAux = "";
        try {

            mensajeAux = enviarAutorizarFacturaElectronica(empresa, factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero(),
                    factura.getVtaAmbiente().compareToIgnoreCase("PRODUCCION") == 0 ? TipoAmbienteEnum.PRODUCCION.getCode() : TipoAmbienteEnum.PRUEBAS.getCode(),
                    'I', cajCajaTO, sisInfoTO);

            if (mensajeAux == null || mensajeAux.equals("")) {
                return "FError inesperado en el comprobante electrónico " + factura.getVtaDocumentoTipo() + ":" + factura.getVtaDocumentoNumero();
            }
            if (mensajeAux.charAt(0) == 'T') {
                if (mensajeAux.contains("AUTORIZADO") && !mensajeAux.contains("NO AUTORIZADO") && !mensajeAux.contains("erro")) {
                    String nombreReporte = "";
                    if (factura.getVtaDocumentoTipo().compareTo("18") == 0 || factura.getVtaDocumentoTipo().compareTo("41") == 0) {
                        nombreReporte = "reportComprobanteFacturaRide";
                    } else if (factura.getVtaDocumentoTipo().compareTo("05") == 0) {
                        nombreReporte = "reportComprobanteNotaDebitoRide";
                    } else if (factura.getVtaDocumentoTipo().compareTo("04") == 0) {
                        nombreReporte = "reportComprobanteNotaCreditoRide";
                    }
                    String xml = ventaElectronicaService.getXmlComprobanteElectronico(empresa, factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero());
                    String claveAcceso = xml.substring(xml.lastIndexOf("<claveAcceso>") + 13, xml.lastIndexOf("</claveAcceso>")).trim();
                    mensajeAux += ", " + compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero(), claveAcceso, nombreReporte, xml, sisInfoTO).substring(1);
                }
            }
        } catch (Exception e) {
            mensajeAux += e.getMessage() != null ? "F" + e.getMessage() + factura.getVtaDocumentoTipo() + ":" + factura.getVtaDocumentoNumero() : "FError inesperado en el comprobante electrónico " + factura.getVtaDocumentoTipo() + ":" + factura.getVtaDocumentoNumero();
        }
        return mensajeAux;
    }

    public boolean buscarCadenaEnLista(String cadena, List<String> lista) {
        boolean seEncuentraRegimen = false;
        for (String info : lista) {
            if (info.contains(cadena)) {
                seEncuentraRegimen = true;
            }
        }
        return seEncuentraRegimen;
    }

    @Override
    public String enviarAutorizarFacturaElectronica(
            String empresa,
            String vtaPerCodigo,
            String vtaMotCodigo,
            String vtaNumero,
            String tipoAmbiente,
            char accion,
            SisFirmaElectronica cajCajaTO,
            SisInfoTO sisInfoTO) throws Exception {

        String mensaje = "";
        String mensajeComplemento = "T";
        String claveAcceso = null;
        Desencriptar desencriptar1 = new Desencriptar();
        String passSignature = desencriptar1.desencriptarMod(cajCajaTO.getFirmaClave());

        InvVentas invVentas = ventasDao.obtenerPorIdEvict(InvVentas.class, new InvVentasPK(empresa, vtaPerCodigo, vtaMotCodigo, vtaNumero));
        accion = ventaElectronicaService.comprobarAnxVentaElectronica(empresa, vtaPerCodigo, vtaMotCodigo, vtaNumero) ? 'M' : 'I';
        if (invVentas == null) {
            mensaje = "FProblema al verificar la venta...";
        } else {
            //********VERIFICAR SI ES MICROEMPRESA/OBSERVACIONES DE VENTA /OBSERVACIONES DE EXPORTACION DE VENTA******
            String infAdicional = "";
            List<String> rucsInfAd = Arrays.asList("0791730651001", "0790068025001", "0903837367001");
            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, invVentas.getInvVentasPK().getVtaEmpresa());
            AnxVentaExportacion exportacion = exportacionesService.obtenerAnxVentaExportacion(empresa, invVentas.getInvVentasPK().getVtaPeriodo(), invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero());

            boolean seInsertarInfAdi = rucsInfAd.contains(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
            List<String> listaInformacionAdicional = (invVentas.getVtaInformacionAdicional() != null && !invVentas.getVtaInformacionAdicional().isEmpty())
                    ? UtilsValidacion.separar(invVentas.getVtaInformacionAdicional(), "|") : new ArrayList<>();
            if (seInsertarInfAdi) {
                boolean seEncuentraObservacion = (invVentas.getVtaObservaciones() != null && !invVentas.getVtaObservaciones().isEmpty())
                        ? buscarCadenaEnLista("OBSERVACIONES=" + invVentas.getVtaObservaciones(), listaInformacionAdicional) : true;// true para que no inserte
                infAdicional += !seEncuentraObservacion ? ("OBSERVACIONES=" + invVentas.getVtaObservaciones() + "|") : "";
            }
            boolean seEncuentraObservacionExportacion = (exportacion != null && exportacion.getExpObservaciones() != null && !exportacion.getExpObservaciones().isEmpty())
                    ? buscarCadenaEnLista("OBSERVACIONES=" + exportacion.getExpObservaciones(), listaInformacionAdicional) : true;// true para que no inserte
            infAdicional += !seEncuentraObservacionExportacion ? ("OBSERVACIONES=" + exportacion.getExpObservaciones() + "|") : "";
            infAdicional = listaInformacionAdicional.size() > 0 ? invVentas.getVtaInformacionAdicional() + infAdicional : infAdicional;
            invVentas.setVtaInformacionAdicional(infAdicional);
            //******************************************
            InvCliente invCliente = clienteDao.buscarInvCliente(empresa, invVentas.getInvCliente().getInvClientePK().getCliCodigo());
            if (invCliente != null && invCliente.getCliIdTipo() != null) {
                String mensajeCliente = "FLa identificación del cliente: " + invCliente.getCliIdNumero() + ", no coincide con la longitud permitida.";
                if (invCliente.getCliIdTipo().equals('C')) {
                    if (invCliente.getCliIdNumero() == null || invCliente.getCliIdNumero().length() != 10) {
                        return mensajeCliente;
                    }
                }
                if (invCliente.getCliIdTipo().equals('R')) {
                    if (invCliente.getCliIdNumero() == null || invCliente.getCliIdNumero().length() != 13) {
                        return mensajeCliente;
                    }
                }
            }
            List<InvListaDetalleVentasTO> listaInvVentasDetalleTO = ventasDetalleDao.getListaInvVentasDetalleTO(
                    invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                    invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero());
            Emisor emisor = llenarDatosEmisor(empresa, invVentas.getVtaDocumentoNumero(), tipoAmbiente);
            //Reembolso
            List<AnxVentaReembolsoTO> listaAnxVentaReembolsoTO = anxVentaReembolsoService.getListaAnxVentaReembolsoTO(
                    invVentas.getInvVentasPK().getVtaEmpresa(),
                    invVentas.getInvVentasPK().getVtaPeriodo(),
                    invVentas.getInvVentasPK().getVtaMotivo(),
                    invVentas.getInvVentasPK().getVtaNumero());

            Date fechaEmision = UtilsValidacion.fecha(UtilsValidacion.fecha(invVentas.getVtaFecha().toString(), "yyyy-MM-dd", "dd/MM/yyyy"), "dd/MM/yyyy");
            String tipoComprobante = "";
            if (invVentas.getVtaDocumentoTipo().compareTo("18") == 0 || invVentas.getVtaDocumentoTipo().compareTo("41") == 0) {
                tipoComprobante = TipoComprobanteEnum.FACTURA.getCode();
            } else if (invVentas.getVtaDocumentoTipo().compareTo("04") == 0) {
                tipoComprobante = TipoComprobanteEnum.NOTA_DE_CREDITO.getCode();
            } else if (invVentas.getVtaDocumentoTipo().compareTo("05") == 0) {
                tipoComprobante = TipoComprobanteEnum.NOTA_DE_DEBITO.getCode();
            }

            if (invVentas.getVtaClaveAccesoExterna() != null && !invVentas.getVtaClaveAccesoExterna().equals("")) {
                claveAcceso = invVentas.getVtaClaveAccesoExterna();
            } else {
                ClaveContingencia claveContingencia = new FormGenerales().obtieneClaveDeAcceso(invVentas.getVtaDocumentoNumero().substring(8), emisor, claveAcceso, fechaEmision, tipoComprobante);
                if ((claveContingencia.getCodigoComprobante() != null) && (!claveContingencia.getCodigoComprobante().isEmpty())) {
                    claveAcceso = claveContingencia.getCodigoComprobante();
                }
            }
            String tipoIdentificacion = tipoTransaccionService.getCodigoAnxTipoTransaccionTO(invCliente.getCliIdTipo().toString(), "VENTA");
            //guia remision
            InvVentaGuiaRemision guia = ventaGuiaRemisionService.obtenerInvVentaGuiaRemision(empresa, vtaPerCodigo, vtaMotCodigo, vtaNumero);
            if (guia != null) {
                guia.setInvVentas(new InvVentas(invVentas.getInvVentasPK()));
            }

            Date fechaComplemento = null;
            String complementoDocumentoNumero = null;
            //******************
            if (tipoComprobante.compareTo(TipoComprobanteEnum.NOTA_DE_CREDITO.getCode()) == 0) {
                if (invVentas.getVtaObservaciones() == null || invVentas.getVtaObservaciones().isEmpty()) {
                    mensajeComplemento = "FLa Nota de Credito no tiene Motivo ingresado. Ingrese el Motivo";
                } else {
                    InvVentasComplemento complementoNotaCredito = ventasComplementoDao.buscarVentasComplemento(empresa, vtaPerCodigo, vtaMotCodigo, vtaNumero);
                    if (complementoNotaCredito == null) {
                        mensajeComplemento = "FLa Nota de Credito no tiene Complemento. Ingrese el Complemento";
                    } else {
                        InvVentas invVentasComplemento = ventasDao.buscarVentaPorDocumentoNumero(empresa,
                                invCliente.getInvClientePK().getCliCodigo(),
                                complementoNotaCredito.getComDocumentoTipo(),
                                complementoNotaCredito.getComDocumentoNumero());
                        fechaComplemento = invVentasComplemento != null ? invVentasComplemento.getVtaFecha() : invVentas.getVtaFecha();
                        complementoDocumentoNumero = invVentasComplemento != null ? invVentasComplemento.getVtaDocumentoNumero() : complementoNotaCredito.getComDocumentoNumero();
                    }
                }
            } else if (tipoComprobante.compareTo(TipoComprobanteEnum.NOTA_DE_DEBITO.getCode()) == 0) {
                InvVentasComplemento complementoNotaDebito = ventasComplementoDao.buscarVentasComplemento(empresa, vtaPerCodigo, vtaMotCodigo, vtaNumero);
                if (complementoNotaDebito != null) {
                    InvVentas invVentasComplemento = ventasDao.buscarVentaPorDocumentoNumero(empresa,
                            invCliente.getInvClientePK().getCliCodigo(),
                            complementoNotaDebito.getComDocumentoTipo(),
                            complementoNotaDebito.getComDocumentoNumero());
                    fechaComplemento = invVentasComplemento != null ? invVentasComplemento.getVtaFecha() : invVentas.getVtaFecha();
                    complementoDocumentoNumero = invVentasComplemento != null ? invVentasComplemento.getVtaDocumentoNumero() : complementoNotaDebito.getComDocumentoNumero();
                } else {
                    mensajeComplemento = "FLa Nota de Debito no tiene Complemento, Ingrese el Complemento ";
                }
            }
            if (mensajeComplemento.charAt(0) == 'F') {
                mensaje = mensajeComplemento;
            } else {
                //Envio de comprobantes el SRI
                InvClientesDirecciones invDireccion = clientesDireccionesService.obtenerDireccion(invCliente.getInvClientePK().getCliEmpresa(), invVentas.getCliCodigoEstablecimiento(), invCliente.getInvClientePK().getCliCodigo());
                String direccion = invDireccion != null ? invDireccion.getDirDetalle() : "";
                System.out.println("==========> INICIO DE ENVIO DE FACTURA: " + invVentas.getVtaDocumentoNumero());
                RespuestaWebTO mensajeWS = urlWebServicesService.enviarAutorizarFacturaElectronica(
                        direccion, complementoDocumentoNumero,
                        tipoIdentificacion, claveAcceso,
                        tipoComprobante, passSignature,
                        invCliente, emisor,
                        invVentas, guia,
                        sisEmpresaParametros, fechaComplemento,
                        listaInvVentasDetalleTO, listaAnxVentaReembolsoTO, exportacion, cajCajaTO.getFirmaElectronicaArchivo());
                System.out.println("==========> FIN DE ENVIO DE FACTURA: " + invVentas.getVtaDocumentoNumero());
                String mensajeEnvioComprobanteAutorizado = "";
                if (mensajeWS != null && mensajeWS.getOperacionMensaje() != null) {
                    mensajeEnvioComprobanteAutorizado = mensajeWS.getOperacionMensaje();
                    if (mensajeEnvioComprobanteAutorizado.equals("AUTORIZADO")) {
                        AnxVentaElectronicaTO anxVentaElectronicaTO = UtilsJSON.jsonToObjeto(AnxVentaElectronicaTO.class, mensajeWS.getExtraInfo());
                        System.out.println("==========> INICIO DE GUARDADO EN BASE DE DATOS CUANDO AUTORIZO: " + invVentas.getVtaDocumentoNumero());
                        ventaElectronicaService.accionAnxVentaElectronica(anxVentaElectronicaTO, accion, sisInfoTO);
                        System.out.println("==========> FIN DE GUARDADO EN BASE DE DATOS CUANDO AUTORIZO: " + invVentas.getVtaDocumentoNumero());
                        mensaje = "T(";
                    } else {
                        mensaje = "F(";
                    }
                } else {
                    mensaje = "F(El microservicio no devolvió ninguna respuesta.";
                }
                mensaje += (emisor.getTipoAmbiente().equals("1") ? "PRUEBA" : "PRODUCCIÓN") + ", "
                        + invVentas.getVtaDocumentoNumero() + ", "
                        + UtilsValidacion.fecha(invVentas.getVtaFecha().toString(), "yyyy-MM-dd", "dd/MM/yyyy") + ", "
                        + (mensajeEnvioComprobanteAutorizado.equals("AUTORIZADO") ? mensajeEnvioComprobanteAutorizado : "{" + mensajeEnvioComprobanteAutorizado + "}") + ", "
                        + invCliente.getCliRazonSocial();
            }
        }
        return mensaje;
    }

    @Override
    public String descargarAutorizarFacturaElectronica(String empresa, String vtaPerCodigo, String vtaMotCodigo,
            String vtaNumero, String tipoAmbiente, SisInfoTO sisInfoTO) throws Exception {

        String mensaje = "";
        Emisor emisor = null;
        String claveAcceso = null;
        Date fechaEmision;

        anxUrlWebServicesTO urlWebServicesTO = urlWebServicesDao.getAnxUrlWebServicesTO();
        if (urlWebServicesTO == null || urlWebServicesTO.getUrlAmbienteProduccion() == null
                || urlWebServicesTO.getUrlAmbientePruebas() == null) {
            mensaje = "FFalta configurar las URL del Web Service...";
        } else {
            InvVentas invVentas = ventasDao.buscarInvVentas(empresa, vtaPerCodigo, vtaMotCodigo, vtaNumero);
            if (invVentas == null) {
                mensaje = "FProblema al verificar la venta...";
            } else {
                String respuestaAutoriz = null;
                InvCliente invCliente = clienteDao.buscarInvCliente(empresa, invVentas.getInvCliente().getInvClientePK().getCliCodigo());
                emisor = llenarDatosEmisor(empresa, invVentas.getVtaDocumentoNumero(), tipoAmbiente);
                fechaEmision = UtilsValidacion.fecha(UtilsValidacion.fecha(invVentas.getVtaFecha().toString(), "yyyy-MM-dd", "dd/MM/yyyy"), "dd/MM/yyyy");
                String tipoComprobante = "";

                if (invVentas.getVtaDocumentoTipo().compareTo("18") == 0
                        || invVentas.getVtaDocumentoTipo().compareTo("18") == 0) {
                    tipoComprobante = TipoComprobanteEnum.FACTURA.getCode();
                } else if (invVentas.getVtaDocumentoTipo().compareTo("04") == 0) {
                    tipoComprobante = TipoComprobanteEnum.NOTA_DE_CREDITO.getCode();
                } else if (invVentas.getVtaDocumentoTipo().compareTo("05") == 0) {
                    tipoComprobante = TipoComprobanteEnum.NOTA_DE_DEBITO.getCode();
                }

                if (invVentas.getVtaClaveAccesoExterna() != null && !invVentas.getVtaClaveAccesoExterna().equals("")) {
                    claveAcceso = invVentas.getVtaClaveAccesoExterna();
                } else {
                    ClaveContingencia claveContingencia = new FormGenerales().obtieneClaveDeAcceso(
                            invVentas.getVtaDocumentoNumero().substring(8), emisor, claveAcceso, fechaEmision,
                            tipoComprobante);

                    if ((claveContingencia.getCodigoComprobante() != null)
                            && (!claveContingencia.getCodigoComprobante().isEmpty())) {
                        claveAcceso = claveContingencia.getCodigoComprobante();
                    }
                }

                respuestaAutoriz = urlWebServicesService.generarXmlComprobantes(claveAcceso, tipoAmbiente);

                if (respuestaAutoriz == null) {
                    mensaje = "F<ERROR>" + "El comprobante esta en proceso de espera";
                } else if (respuestaAutoriz.equals("AUTORIZADO")) {
//                    AnxVentaElectronicaTO anxVentaElectronicaTO = LlenarObjetoAnx.llenarObjetoAnxVentaElectronicaTO(empresa,
//                            vtaPerCodigo, vtaMotCodigo, vtaNumero, claveAcceso, invVentas.getVtaFecha().toString(),
//                            respuestaAutoriz, emisor, sisInfoTO);
//                    mensaje = ventaElectronicaService.accionAnxVentaElectronica(anxVentaElectronicaTO, 'M', sisInfoTO);
                    if (mensaje.charAt(0) == 'T') {
                        mensaje = "T<html>  Respuesta WS SRI: <font size = 5> " + respuestaAutoriz + "</font>.<br>"
                                + "        Cliente : <font size = 5> " + invCliente.getCliRazonSocial() + "</font>.<br>"
                                + "        Ambiente  : <font size = 5> "
                                + (emisor.getTipoAmbiente().equals("1") ? "PRUEBA" : "PRODUCCIÓN")
                                + "</font>.<br></html>";
                    } else {
                        mensaje = "F<html><font size = 5>" + mensaje + "</font>.<br></html>";
                    }
                } else if (respuestaAutoriz.isEmpty()) {
                    mensaje = "FNo hay Resultado por parte del Servidor del SRI\n" + "        Clave Acceso : "
                            + claveAcceso + "\n" + "           Ambiente  : "
                            + (emisor.getTipoAmbiente().equals("1") ? "PRUEBA" : "PRODUCCIÓN")
                            + "\nPosibles Causas:\n -La Fecha de Autorizaciòn del Comprobante supera la fecha minima y maxima que el SRI dispone\n - No Existe el Comprobante Electrónico \n - No hay Respuesta del los WS del SRI";
                } else {
                    String estado = respuestaAutoriz.substring(0, respuestaAutoriz.lastIndexOf("|"));
                    String resultado = respuestaAutoriz.substring(respuestaAutoriz.lastIndexOf("|") + 1,
                            respuestaAutoriz.length());
                    mensaje = "FEl comprobante fue guardado, firmado y enviado exitósamente, pero no fue Autorizado\n"
                            + "<" + estado + ">" + "\n" + FormGenerales.insertarCaracteres(resultado, "\n", 160);
                }
            }
        }
        return mensaje;
    }

    @Override
    public String enviarAutorizarLiquidacionCompras(String empresa, List<AnxListaLiquidacionComprasPendientesTO> listaEnviar, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        SisFirmaElectronica cajCajaTO = validarRequisitosParaEnviarAutorizacion(sisInfoTO);
        for (AnxListaLiquidacionComprasPendientesTO anxListaLiquidacionComprasPendientesTO : listaEnviar) {
            String periodo = anxListaLiquidacionComprasPendientesTO.getCompPeriodo();
            String motivo = anxListaLiquidacionComprasPendientesTO.getCompMotivo();
            String numero = anxListaLiquidacionComprasPendientesTO.getCompNumero();

            String mensajeAux = enviarAutorizarLiquidacionCompras(empresa, periodo, motivo, numero,
                    anxListaLiquidacionComprasPendientesTO.getCompAmbiente().compareToIgnoreCase("PRODUCCION") == 0
                    ? TipoAmbienteEnum.PRODUCCION.getCode()
                    : TipoAmbienteEnum.PRUEBAS.getCode(),
                    'I',
                    cajCajaTO,
                    sisInfoTO);
            if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
                if (mensajeAux.contains("AUTORIZADO") && !mensajeAux.contains("NO AUTORIZADO") && !mensajeAux.contains("erro") && !mensajeAux.contains("ERRO")) {
                    mensaje += mensajeAux;
                    String xml = compraElectronicaService.getXmlLiquidacionCompras(empresa, periodo, motivo, numero);
                    String claveAcceso = xml.substring(xml.lastIndexOf("<claveAcceso>") + 13, xml.lastIndexOf("</claveAcceso>")).trim();
                    mensaje += ", " + compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, periodo, motivo, numero, claveAcceso, "reportComprobanteLiquidacionCompraRide", xml, sisInfoTO).substring(1) + ")|";
                } else {
                    mensaje = "F" + mensajeAux.substring(1);
                }
            } else {
                mensaje += mensajeAux;
            }
        }
        System.out.println("mensaje final " + mensaje);
        return mensaje;
    }

    @Override
    public String enviarAutorizarLiquidacionCompras(
            String empresa,
            String perCodigo,
            String motCodigo,
            String compNumero,
            String tipoAmbiente,
            char accion,
            SisFirmaElectronica cajCajaTO,
            SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        String claveAcceso = null;
        ClaveContingencia claveContingencia;
        Desencriptar desencriptar1 = new Desencriptar();
        String passSignature = desencriptar1.desencriptarMod(cajCajaTO.getFirmaClave());

        InvComprasTO invComprasTO = comprasService.getComprasTO(empresa, perCodigo, motCodigo, compNumero);
        accion = liquidacionComprasService.comprobarAnxLiquidacionComprasElectronica(empresa, perCodigo, motCodigo, compNumero) ? 'M' : 'I';
        if (invComprasTO == null) {
            mensaje = "FProblema al verificar la compra...";
        } else {
            InvProveedor invProveedor = proveedorService.buscarInvProveedor(empresa, invComprasTO.getProvCodigo());
            List<InvComprasDetalle> listaInvComprasDetalle = comprasDetalleDao.obtenerCompraDetallePorNumero(empresa, perCodigo, motCodigo, compNumero);
            List<InvComprasDetalle> listaDetalle = new ArrayList<>();
            if (listaInvComprasDetalle != null) {
                for (InvComprasDetalle invComprasDetalle : listaInvComprasDetalle) {
                    InvComprasDetalle detalle = new InvComprasDetalle();
                    detalle.setDetCantidad(invComprasDetalle.getDetCantidad());
                    detalle.setDetPrecio(invComprasDetalle.getDetPrecio());
                    detalle.setDetPorcentajeDescuento(invComprasDetalle.getDetPorcentajeDescuento());
                    detalle.setInvProducto(invComprasDetalle.getInvProducto());
                    listaDetalle.add(detalle);
                }

            }
            Emisor emisor = llenarDatosEmisor(empresa, invComprasTO.getCompDocumentoNumero(), tipoAmbiente);
            Date fechaEmision = UtilsValidacion.fecha(UtilsValidacion.fecha(invComprasTO.getCompFecha(), "dd-MM-yyyy", "dd/MM/yyyy"), "dd/MM/yyyy");
            claveContingencia = new FormGenerales().obtieneClaveDeAcceso(invComprasTO.getCompDocumentoNumero().substring(8), emisor, claveAcceso, fechaEmision, TipoComprobanteEnum.LIQUIDACION_DE_COMPRAS.getCode());
            if ((claveContingencia.getCodigoComprobante() != null) && (!claveContingencia.getCodigoComprobante().isEmpty())) {
                claveAcceso = claveContingencia.getCodigoComprobante();
            }
            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            boolean agregarInfAdicionalRetencion = sisEmpresaParametros.getParAgenteRetencion() != null && !sisEmpresaParametros.getParAgenteRetencion().equals("");
            String agenteRetencion = agregarInfAdicionalRetencion ? sisEmpresaParametros.getParAgenteRetencion() : null;
            if ((claveContingencia.getCodigoComprobante() != null) && (!claveContingencia.getCodigoComprobante().isEmpty())) {
                claveAcceso = claveContingencia.getCodigoComprobante();
            }
            RespuestaWebTO mensajeWS = urlWebServicesService.enviarAutorizarLiquidacionCompras(claveAcceso, agenteRetencion, passSignature, invProveedor, emisor, invComprasTO, listaDetalle, cajCajaTO.getFirmaElectronicaArchivo(), sisEmpresaParametros);
            if (mensajeWS != null && mensajeWS.getOperacionMensaje() != null) {
                mensaje = mensajeWS.getOperacionMensaje();
                if (mensaje.equals("AUTORIZADO")) {
                    AnxLiquidacionComprasElectronicaTO anxLiquidacionComprasElectronicaTO = UtilsJSON.jsonToObjeto(AnxLiquidacionComprasElectronicaTO.class, mensajeWS.getExtraInfo());
                    liquidacionComprasService.accionAnxLiquidacionComprasElectronicaTO(anxLiquidacionComprasElectronicaTO, accion, sisInfoTO);
                }
            } else {
                mensaje = "FEl microservicio no devolvió ninguna respuesta.";
            }

            mensaje = "T(" + (emisor.getTipoAmbiente().equals("1") ? "PRUEBA" : "PRODUCCIÓN") + ", "
                    + invComprasTO.getCompDocumentoNumero() + ", "
                    + mensaje + ", "
                    + invProveedor.getProvRazonSocial();
        }
        return mensaje;
    }

    @Override
    public String enviarAutorizarGuiaRemision(String empresa, AnxListaGuiaRemisionPendientesTO anxListaGuiaRemisionPendientesTO, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        String periodo = anxListaGuiaRemisionPendientesTO.getGuiaPeriodo();
        String numero = anxListaGuiaRemisionPendientesTO.getGuiaNumero();
        SisFirmaElectronica cajCajaTO = validarRequisitosParaEnviarAutorizacion(sisInfoTO);
        String mensajeAux = enviarAutorizarGuiaRemision(empresa, periodo, numero,
                anxListaGuiaRemisionPendientesTO.getGuiaAmbiente().compareToIgnoreCase("PRODUCCION") == 0
                ? TipoAmbienteEnum.PRODUCCION.getCode()
                : TipoAmbienteEnum.PRUEBAS.getCode(),
                'I', cajCajaTO, sisInfoTO);
        if (mensajeAux != null && mensajeAux.charAt(0) == 'T') {
            if (mensajeAux.contains("AUTORIZADO") && !mensajeAux.contains("NO AUTORIZADO") && !mensajeAux.contains("erro") && !mensajeAux.contains("ERRO")) {
                mensaje += mensajeAux;
                String xml = guiaElectronicaService.getXmlGuiaRemision(empresa, periodo, numero);
                String claveAcceso = xml.substring(xml.lastIndexOf("<claveAcceso>") + 13, xml.lastIndexOf("</claveAcceso>")).trim();
                mensaje += ", " + guiaElectronicaService.enviarEmailComprobantesElectronicos(empresa, periodo, numero, claveAcceso, "reportComprobanteGuiaRemisionRide", xml).substring(1) + ")|";
            } else {
                mensaje = "F" + mensajeAux.substring(1);
            }
        } else {
            mensaje += mensajeAux;
        }
        System.out.println("mensaje final " + mensaje);
        return mensaje;
    }

    @Override
    public String enviarAutorizarGuiaRemision(
            String empresa,
            String periodo,
            String numero,
            String tipoAmbiente,
            char accion,
            SisFirmaElectronica cajCajaTO,
            SisInfoTO sisInfoTO) throws Exception {
        String mensaje;
        String claveAcceso = null;
        ClaveContingencia claveContingencia;
        Desencriptar desencriptar1 = new Desencriptar();
        String passSignature = desencriptar1.desencriptarMod(cajCajaTO.getFirmaClave());

        InvGuiaRemision invGuiaRemision = guiaRemisionService.buscarInvGuiaRemision(empresa, periodo, numero);
        accion = guiaElectronicaService.comprobarAnxGuiaElectronica(empresa, periodo, numero) ? 'M' : 'I';

        if (invGuiaRemision == null) {
            mensaje = "FProblema al verificar la guía de remisión...";
        } else {
            InvCliente invCliente = invGuiaRemision.getInvCliente();
            InvTransportista invTransportista = invGuiaRemision.getInvTransportista();
            List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetalleTO = guiaRemisionService.obtenerGuiaRemisionDetalleTO(empresa, periodo, numero);
            Emisor emisor = llenarDatosEmisor(empresa, invGuiaRemision.getGuiaDocumentoNumero(), tipoAmbiente);
            String fechaA = UtilsValidacion.fecha(invGuiaRemision.getGuiaFechaEmision().toString(), "yyyy-MM-dd", "dd/MM/yyyy");
            Date fechaEmision = UtilsValidacion.fecha(fechaA, "dd/MM/yyyy");
            claveContingencia = new FormGenerales().obtieneClaveDeAcceso(invGuiaRemision.getGuiaDocumentoNumero().substring(8), emisor, claveAcceso, fechaEmision, TipoComprobanteEnum.GUIA_DE_REMISION.getCode());
            if ((claveContingencia.getCodigoComprobante() != null) && (!claveContingencia.getCodigoComprobante().isEmpty())) {
                claveAcceso = claveContingencia.getCodigoComprobante();
            }
            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            boolean agregarRetencion = sisEmpresaParametros.getParAgenteRetencion() != null && !sisEmpresaParametros.getParAgenteRetencion().equals("");
            String agenteRetencion = agregarRetencion ? sisEmpresaParametros.getParAgenteRetencion() : null;
            RespuestaWebTO mensajeWS = urlWebServicesService.enviarAutorizarGuiaRemision(claveAcceso, agenteRetencion, passSignature, invCliente, emisor, invTransportista, listaInvGuiaRemisionDetalleTO, cajCajaTO.getFirmaElectronicaArchivo(), invGuiaRemision, sisEmpresaParametros);
            if (mensajeWS != null && mensajeWS.getOperacionMensaje() != null) {
                mensaje = mensajeWS.getOperacionMensaje();
                if (mensaje.equals("AUTORIZADO")) {
                    AnxGuiaRemisionElectronicaTO anxGuiaRemisionElectronicaTO = UtilsJSON.jsonToObjeto(AnxGuiaRemisionElectronicaTO.class, mensajeWS.getExtraInfo());
                    guiaElectronicaService.accionAnxGuiaRemisionTOElectronica(anxGuiaRemisionElectronicaTO, accion, sisInfoTO);
                }
            } else {
                mensaje = "FEl microservicio no devolvió ninguna respuesta.";
            }
            mensaje = "T(" + (emisor.getTipoAmbiente().equals("1") ? "PRUEBA" : "PRODUCCIÓN") + ", "
                    + invGuiaRemision.getGuiaDocumentoNumero() + ", "
                    + mensaje + ", "
                    + invCliente.getCliRazonSocial();
        }
        return mensaje;
    }

    @Override
    public SisFirmaElectronica validarRequisitosParaEnviarAutorizacion(SisInfoTO sisInfoTO) throws Exception {
        SisFirmaElectronica firma = null;
        CajCajaTO cajCajaTO = cajaDao.getCajCajaTO(sisInfoTO.getEmpresa(), sisInfoTO.getUsuario());
        if (cajCajaTO == null) {
            throw new GeneralException("El usuario no tiene perfil de facturación configurado");
        }
        if (cajCajaTO.getFirmaSecuencial() != null) {
            firma = firmaElectronicaDao.obtenerFirmaElectronica(cajCajaTO.getFirmaSecuencial());
            if (firma != null) {
                Desencriptar desencriptar1 = new Desencriptar();
                String passSignature = desencriptar1.desencriptarMod(firma.getFirmaClave());
                if (passSignature == null) {
                    throw new GeneralException("La clave de la firma digital no está encriptada correctamente");
                }
                SisEmpresa empresa = empresaService.obtenerEmpresa(sisInfoTO.getEmpresa());
                Date fechaCaducidad = firmaElectronicaService.verificarDatosCertificado(passSignature, empresa.getEmpRuc(), null, firma.getFirmaElectronicaArchivo());
                if (fechaCaducidad != null && !UtilsValidacion.isFechaSuperior(fechaCaducidad)) {
                    throw new GeneralException("El certificado se encuentra caducado, la fecha de caducidad es la siguiente: " + fechaCaducidad);
                }
            } else {
                throw new GeneralException("No existe firma electrónica asociada al perfil de facturación.");
            }
        } else {
            throw new GeneralException("No existe firma electrónica asociada al perfil de facturación.");
        }
        anxUrlWebServicesTO urlWebServicesTO = urlWebServicesDao.getAnxUrlWebServicesTO();
        if (urlWebServicesTO == null || urlWebServicesTO.getUrlAmbienteProduccion() == null || urlWebServicesTO.getUrlAmbientePruebas() == null) {
            throw new GeneralException("Falta configurar la URL del web service...");
        }
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
        String mensaje = verificarDeudasVencidas(sisEmpresaParametros.getEmpCodigo().getEmpRuc(), sisInfoTO);
        if (mensaje.charAt(0) != 'T') {
            throw new GeneralException(mensaje.substring(1));
        }
        return firma;
    }

    private String verificarDeudasVencidas(String rucEmisor, SisInfoTO sisInfoTO) throws Exception {
        BigDecimal deudaVencida;
        String mensaje = "T";
        UtilsWebService.actualizarCertificadoSeguridadSRI();

        if (rucEmisor.contains("0791807611001") || rucEmisor.contains("0704016492001") || rucEmisor.contains("0704543727001")) { // es "ows" o "nelson benavides"
            deudaVencida = java.math.BigDecimal.ZERO;
        } else {
            deudaVencida = CarteraVerificarDeudasNelsonBenavidesDelegate.getInstance().getCarDeudaVencida("0791807611001", rucEmisor, sisInfoTO);
        }

        if (deudaVencida == null) {
            mensaje = "FServer Billing Comunication Error";
        } else if (deudaVencida.compareTo(java.math.BigDecimal.ZERO) > 0) {
            mensaje = "FOur records indicate that your Google Cloud Platform and APIs billing account: 000DB9-BBEFCE-CDCCA5 is past due or does not have valid payment information associated with it.";
        }

        return mensaje;
    }

    public Emisor llenarDatosEmisor(String empCodigo, String NumeroComprobante, String tipoAmbiente) throws Exception {
        Emisor emisor = new Emisor();
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empCodigo);
        emisor.setRuc(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
        if (sisEmpresaParametros.getEmpCodigo().getEmpRuc().equalsIgnoreCase("0903837367001")) {
            emisor.setRazonSocial("HERRERA COELLO CELSA PETRA");
            emisor.setNombreComercial("AUTOPINTURAS ASSAN");
        } else {
            emisor.setRazonSocial(sisEmpresaParametros.getEmpCodigo().getEmpNombre());
            emisor.setNombreComercial(sisEmpresaParametros.getEmpCodigo().getEmpRazonSocial());
        }
        emisor.setDirEstablecimiento(sisEmpresaParametros.getEmpCodigo().getEmpDireccion());
        emisor.setCodigoEstablecimiento(NumeroComprobante.substring(0, 3));
        emisor.setCodPuntoEmision(NumeroComprobante.substring(4, 7));
        emisor.setNumeroResolusion("");
        emisor.setContribuyenteEspecial(sisEmpresaParametros.getParResolucionContribuyenteEspecial());
        emisor.setLlevaContabilidad(sisEmpresaParametros.getParObligadoLlevarContabilidad() ? "SI" : "NO");
        emisor.setPathLogo("");
        emisor.setTipoEmision("1");
        emisor.setTipoAmbiente(tipoAmbiente);
        System.out.println("numeroComprobante: " + NumeroComprobante);
        emisor.setClaveInterna("26031982");
        emisor.setDireccionMatriz(sisEmpresaParametros.getEmpCodigo().getEmpDireccion());
        emisor.setParWebDocumentosElectronicos(sisEmpresaParametros.getParWebDocumentosElectronicos());
        emisor.setToken("");
        emisor.setTiempoEspera(6);
        return emisor;
    }
}
