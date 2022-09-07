package ec.com.todocompu.ShrimpSoftServer.anexos.controller;

import com.thoughtworks.xstream.XStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.anexos.report.ReporteAnexosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.report.ReporteComprobanteElectronicoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnexoCatastroMicroempresaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnexoNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnuladosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxCodigoRegimenService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxComprobantesElectronicosEmitidosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxDistritosAduanerosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxParaisosFiscalesService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxTiposExportacionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxTiposIngresoExteriorService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxTiposRegimenFiscalExteriorService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraDetalleService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraFormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraReembolsoService;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosRecibidos;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ComprobanteElectronicoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ComprobantesPublicosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ConceptoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CuentasContablesService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.DpaEcuadorService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.EnviarComprobantesWSService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ExportacionesService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.FormaPagoAnexoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.GuiaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.LiquidacionCompraElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.NotificacionesAWSService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.PaisService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.PorcentajeIvaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ProvinciasService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.SustentoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoComprobanteService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoIdentificacionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoTransaccionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.UrlWebServicesService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxComprobantesElectronicosRecibidosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxFormulario104Service;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxHomologacionProductoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnxRetencionesVentaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ComprobantesRecibidosService;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesObjetosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxDiferenciasTributacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxEstablecimientoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario103TO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario104TO;
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
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesRentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPuntoEmisionComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRespuestasErroresPorNotasDeDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionVentaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoIdentificacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoListaTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxUltimaAutorizacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteElectronico;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteImportarTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.anxUrlWebServicesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCatastroMicroempresa;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCodigoRegimen;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosEmitidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxDistritosAduaneros;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormulario104;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxParaisosFiscales;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxRetencionesVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposExportacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposIngresoExterior;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposRegimenFiscalExterior;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntosComprasWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprobantesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaSecuencialesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.XStreamUtil;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.Autorizacion;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import ec.com.todocompu.ShrimpSoftUtils.web.ArchivoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/todocompuWS/anexosWebController")
public class AnexosWebController {

    @Autowired
    private AnuladosService anuladosService;
    @Autowired
    private CompraService compraService;
    @Autowired
    private ComprasDao comprasDao;
    @Autowired
    private CompraFormaPagoService compraFormaPagoService;
    @Autowired
    private CompraDetalleService compraDetalleService;
    @Autowired
    private CompraReembolsoService compraReembolsoService;
    @Autowired
    private ConceptoService conceptoService;
    @Autowired
    private EnviarComprobantesWSService enviarComprobantesWSService;
    @Autowired
    private CompraElectronicaService compraElectronicaService;
    @Autowired
    private CuentasContablesService cuentasContablesService;
    @Autowired
    private PaisService paisService;
    @Autowired
    private DpaEcuadorService dpaEcuadorService;
    @Autowired
    private UrlWebServicesService urlWebServicesService;
    @Autowired
    private TipoTransaccionService tipoTransaccionService;
    @Autowired
    private PorcentajeIvaService porcentajeIvaService;
    @Autowired
    private AnxComprobantesElectronicosRecibidosService anxComprobantesElectronicosRecibidosService;
    @Autowired
    private ProvinciasService provinciasService;
    @Autowired
    private FormaPagoAnexoService formaPagoAnexoService;
    @Autowired
    private TipoIdentificacionService tipoIdentificacionService;
    @Autowired
    private TipoComprobanteService tipoComprobanteService;
    @Autowired
    private AnexoNumeracionService numeracionService;
    @Autowired
    private SustentoService sustentoService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private VentaElectronicaService ventaElectronicaService;
    @Autowired
    private ComprobanteElectronicoService comprobanteElectronicoService;
    @Autowired
    private ReporteAnexosService reporteAnexosService;
    @Autowired
    private ReporteComprobanteElectronicoService reporteComprobanteElectronicoService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private NotificacionesAWSService notificacionesAWSService;
    @Autowired
    private ComprobantesPublicosService comprobantesPublicosService;
    @Autowired
    private GuiaElectronicaService guiaElectronicaService;
    @Autowired
    private LiquidacionCompraElectronicaService liquidacionCompraElectronicaService;
    @Autowired
    private AnxCodigoRegimenService anxCodigoRegimenService;
    @Autowired
    private AnxDistritosAduanerosService anxDistritosAduanerosService;
    @Autowired
    private AnxParaisosFiscalesService anxParaisosFiscalesService;
    @Autowired
    private AnxTiposExportacionService anxTiposExportacionService;
    @Autowired
    private AnxTiposIngresoExteriorService anxTiposIngresoExteriorService;
    @Autowired
    private AnxTiposRegimenFiscalExteriorService anxTiposRegimenFiscalExteriorService;
    @Autowired
    private ExportacionesService exportacionesService;
    @Autowired
    private EmpresaDao empresaDao;
    @Autowired
    private AnxFormulario104Service anxFormulario104Service;
    @Autowired
    private AnxHomologacionProductoService anxHomologacionProductoService;
    @Autowired
    private AnxRetencionesVentaService anxRetencionesVentaService;
    @Autowired
    private AnxComprobantesElectronicosEmitidosService anxComprobantesElectronicosEmitidosService;
    @Autowired
    private VentasService ventasService;
    @Autowired
    private AnexoCatastroMicroempresaService anexoCatastroMicroempresaService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private ComprobantesRecibidosService comprobantesRecibidosService;

    //https://tws9.todocompu.com.ec/todocompu/todocompuWS/anexosWebController/amazonSNS
//    @RequestMapping("/amazonSNS")
    public @ResponseBody
    String amazonWSNS(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String tipoNotificacion = UtilsJSON.jsonToObjeto(String.class, map.get("eventType"));
        if (tipoNotificacion.equalsIgnoreCase("SubscriptionConfirmation")) {
            String confirmacion = UtilsJSON.jsonToObjeto(String.class, map.get("SubscribeURL"));
            System.out.println("SubscribeURL: \n" + confirmacion);
        } else if (tipoNotificacion.equalsIgnoreCase("Send")) { // Enviado
            /*
                {
                    "eventType": "Send",
                    "mail": {mail:
                            "timestamp": "2018-11-12T20:28:45.584Z",
                            "source": "LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>",
                            "sourceArn": "arn:aws:ses:us-east-1:163527965247:identity/notificaciones@documentos-electronicos.info",
                            "sendingAccountId": "163527965247",
                            "messageId": "01000167099cba90-8cd8783e-34c1-48b1-8d15-4d0568157412-000000",
                            "destination": ["nbenavides@todocompu.com.ec", "todocompu@xcvb.com.error"],
                            "headersTruncated": false,
                            "headers": [{
                                    "name": "From",
                                    "value": "LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>"
                            }, {
                                    "name": "Reply-To",
                                    "value": "gerencia@obinte.com, todocompu@hotmail.es"
                            }, {
                                    "name": "To",
                                    "value": "nbenavides@todocompu.com.ec, todocompu@xcvb.com.error"
                            }, {
                                    "name": "Message-ID",
                                    "value": "<874773376.2.1542054729978.JavaMail.todocompu@iMac-Pro-de-Nelson.local>"
                            }, {
                                    "name": "Subject",
                                    "value": "COMPROBANTE DE RETENCION Nº 001-002-000013590"
                            }, {
                                    "name": "MIME-Version",
                                    "value": "1.0"
                            }, {
                                    "name": "Content-Type",
                                    "value": "multipart/mixed;  boundary=\"----=_Part_1_1926830959.1542054729132\""
                            }],
                            "commonHeaders": {
                                    "from": ["LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>"],
                                    "replyTo": ["gerencia@obinte.com", "todocompu@hotmail.es"],
                                    "to": ["nbenavides@todocompu.com.ec", "todocompu@xcvb.com.error"],
                                    "messageId": "01000167099cba90-8cd8783e-34c1-48b1-8d15-4d0568157412-000000",
                                    "subject": "COMPROBANTE DE RETENCION Nº 001-002-000013590"
                            },
                            "tags": {
                                    "ows-empresa": ["LAN"],
                                    "ses:operation": ["SendRawEmail"],
                                    "ses:configuration-set": ["todocompu"],
                                    "ows-clave-acceso": ["1010201807079010078600120010020000135902603198219"],
                                    "ses:source-ip": ["201.183.232.112"],
                                    "ses:from-domain": ["documentos-electronicos.info"],
                                    "ows-periodo": ["2018-10"],
                                    "ses:caller-identity": ["nbenavides"],
                                    "ows-motivo": ["101"],
                                    "ows-numero": ["0000066"]
                            }
                    },
                    "send": {}
            }
             */
        } else if (tipoNotificacion.equalsIgnoreCase("Delivery")) { // Entregado, el correo llego al destino
            /*
                {
                    "eventType": "Delivery",
                    "mail": {
                            "timestamp": "2018-11-12T20:28:45.584Z",
                            "source": "LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>",
                            "sourceArn": "arn:aws:ses:us-east-1:163527965247:identity/notificaciones@documentos-electronicos.info",
                            "sendingAccountId": "163527965247",
                            "messageId": "01000167099cba90-8cd8783e-34c1-48b1-8d15-4d0568157412-000000",
                            "destination": ["todocompu@xcvb.com.error", "nbenavides@todocompu.com.ec"],
                            "headersTruncated": false,
                            "headers": [{
                                    "name": "From",
                                    "value": "LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>"
                            }, {
                                    "name": "Reply-To",
                                    "value": "gerencia@obinte.com, todocompu@hotmail.es"
                            }, {
                                    "name": "To",
                                    "value": "nbenavides@todocompu.com.ec, todocompu@xcvb.com.error"
                            }, {
                                    "name": "Message-ID",
                                    "value": "<874773376.2.1542054729978.JavaMail.todocompu@iMac-Pro-de-Nelson.local>"
                            }, {
                                    "name": "Subject",
                                    "value": "COMPROBANTE DE RETENCION Nº 001-002-000013590"
                            }, {
                                    "name": "MIME-Version",
                                    "value": "1.0"
                            }, {
                                    "name": "Content-Type",
                                    "value": "multipart/mixed;  boundary=\"----=_Part_1_1926830959.1542054729132\""
                            }],
                            "commonHeaders": {
                                    "from": ["LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>"],
                                    "replyTo": ["gerencia@obinte.com", "todocompu@hotmail.es"],
                                    "to": ["nbenavides@todocompu.com.ec", "todocompu@xcvb.com.error"],
                                    "messageId": "01000167099cba90-8cd8783e-34c1-48b1-8d15-4d0568157412-000000",
                                    "subject": "COMPROBANTE DE RETENCION Nº 001-002-000013590"
                            },
                            "tags": {
                                    "ows-empresa": ["LAN"],
                                    "ses:operation": ["SendRawEmail"],
                                    "ses:configuration-set": ["todocompu"],
                                    "ows-clave-acceso": ["1010201807079010078600120010020000135902603198219"],
                                    "ses:source-ip": ["201.183.232.112"],
                                    "ses:from-domain": ["documentos-electronicos.info"],
                                    "ows-periodo": ["2018-10"],
                                    "ses:caller-identity": ["nbenavides"],
                                    "ows-motivo": ["101"],
                                    "ows-numero": ["0000066"],
                                    "ses:outgoing-ip": ["54.240.8.208"]
                            }
                    },
                    "delivery": {
                            "timestamp": "2018-11-12T20:28:47.559Z",
                            "processingTimeMillis": 1975,
                            "recipients": ["nbenavides@todocompu.com.ec"],
                            "smtpResponse": "250 2.0.0 OK 1542054527 r13si10214887qtr.290 - gsmtp",
                            "reportingMTA": "a8-208.smtp-out.amazonses.com"
                    }
            }
             */
        } else if (tipoNotificacion.equalsIgnoreCase("Open")) { // Abierto, se supone que para leerlo
            /*
                {
                        "eventType": "Open",
                        "mail": {
                                "timestamp": "2018-11-12T20:28:45.584Z",
                                "source": "LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>",
                                "sendingAccountId": "163527965247",
                                "messageId": "01000167099cba90-8cd8783e-34c1-48b1-8d15-4d0568157412-000000",
                                "destination": ["nbenavides@todocompu.com.ec", "todocompu@xcvb.com.error"],
                                "headersTruncated": false,
                                "headers": [{
                                        "name": "From",
                                        "value": "LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>"
                                }, {
                                        "name": "Reply-To",
                                        "value": "gerencia@obinte.com, todocompu@hotmail.es"
                                }, {
                                        "name": "To",
                                        "value": "nbenavides@todocompu.com.ec, todocompu@xcvb.com.error"
                                }, {
                                        "name": "Message-ID",
                                        "value": "<874773376.2.1542054729978.JavaMail.todocompu@iMac-Pro-de-Nelson.local>"
                                }, {
                                        "name": "Subject",
                                        "value": "COMPROBANTE DE RETENCION Nº 001-002-000013590"
                                }, {
                                        "name": "MIME-Version",
                                        "value": "1.0"
                                }, {
                                        "name": "Content-Type",
                                        "value": "multipart/mixed;  boundary=\"----=_Part_1_1926830959.1542054729132\""
                                }],
                                "commonHeaders": {
                                        "from": ["LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>"],
                                        "replyTo": ["gerencia@obinte.com", "todocompu@hotmail.es"],
                                        "to": ["nbenavides@todocompu.com.ec", "todocompu@xcvb.com.error"],
                                        "messageId": "01000167099cba90-8cd8783e-34c1-48b1-8d15-4d0568157412-000000",
                                        "subject": "COMPROBANTE DE RETENCION Nº 001-002-000013590"
                                },
                                "tags": {
                                        "ows-empresa": ["LAN"],
                                        "ses:operation": ["SendRawEmail"],
                                        "ses:configuration-set": ["todocompu"],
                                        "ows-clave-acceso": ["1010201807079010078600120010020000135902603198219"],
                                        "ses:source-ip": ["201.183.232.112"],
                                        "ses:from-domain": ["documentos-electronicos.info"],
                                        "ows-periodo": ["2018-10"],
                                        "ses:caller-identity": ["nbenavides"],
                                        "ows-motivo": ["101"],
                                        "ows-numero": ["0000066"]
                                }
                        },
                        "open": {
                                "timestamp": "2018-11-12T20:33:01.933Z",
                                "userAgent": "Mozilla/5.0 (Windows NT 5.1; rv:11.0) Gecko Firefox/11.0 (via ggpht.com GoogleImageProxy)",
                                "ipAddress": "66.102.8.49"
                        }
                }
             */
        } else if (tipoNotificacion.equalsIgnoreCase("Bounce")) { // Rebote, por ej. una direccion que no existe
            /*
                {
                    "eventType": "Bounce",
                    "bounce": {
                            "bounceType": "Permanent",
                            "bounceSubType": "General",
                            "bouncedRecipients": [{
                                    "emailAddress": "todocompu@xcvb.com.error",
                                    "action": "failed",
                                    "status": "5.4.4",
                                    "diagnosticCode": "smtp; 550 5.4.4 Invalid domain"
                            }],
                            "timestamp": "2018-11-12T20:28:46.904Z",
                            "feedbackId": "01000167099cbdf1-4a7e7e62-0e8a-4e1e-814a-8222b15662e5-000000",
                            "reportingMTA": "dsn; a8-92.smtp-out.amazonses.com"
                    },
                    "mail": {
                            "timestamp": "2018-11-12T20:28:45.584Z",
                            "source": "LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>",
                            "sourceArn": "arn:aws:ses:us-east-1:163527965247:identity/notificaciones@documentos-electronicos.info",
                            "sendingAccountId": "163527965247",
                            "messageId": "01000167099cba90-8cd8783e-34c1-48b1-8d15-4d0568157412-000000",
                            "destination": ["nbenavides@todocompu.com.ec", "todocompu@xcvb.com.error"],
                            "headersTruncated": false,
                            "headers": [{
                                    "name": "From",
                                    "value": "LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>"
                            }, {
                                    "name": "Reply-To",
                                    "value": "gerencia@obinte.com, todocompu@hotmail.es"
                            }, {
                                    "name": "To",
                                    "value": "nbenavides@todocompu.com.ec, todocompu@xcvb.com.error"
                            }, {
                                    "name": "Message-ID",
                                    "value": "<874773376.2.1542054729978.JavaMail.todocompu@iMac-Pro-de-Nelson.local>"
                            }, {
                                    "name": "Subject",
                                    "value": "COMPROBANTE DE RETENCION Nº 001-002-000013590"
                            }, {
                                    "name": "MIME-Version",
                                    "value": "1.0"
                            }, {
                                    "name": "Content-Type",
                                    "value": "multipart/mixed;  boundary=\"----=_Part_1_1926830959.1542054729132\""
                            }],
                            "commonHeaders": {
                                    "from": ["LANGOSTINO S A LANCONOR <notificaciones@documentos-electronicos.info>"],
                                    "replyTo": ["gerencia@obinte.com", "todocompu@hotmail.es"],
                                    "to": ["nbenavides@todocompu.com.ec", "todocompu@xcvb.com.error"],
                                    "messageId": "01000167099cba90-8cd8783e-34c1-48b1-8d15-4d0568157412-000000",
                                    "subject": "COMPROBANTE DE RETENCION Nº 001-002-000013590"
                            },
                            "tags": {
                                    "ows-empresa": ["LAN"],
                                    "ses:operation": ["SendRawEmail"],
                                    "ses:configuration-set": ["todocompu"],
                                    "ows-clave-acceso": ["1010201807079010078600120010020000135902603198219"],
                                    "ses:source-ip": ["201.183.232.112"],
                                    "ses:from-domain": ["documentos-electronicos.info"],
                                    "ows-periodo": ["2018-10"],
                                    "ses:caller-identity": ["nbenavides"],
                                    "ows-motivo": ["101"],
                                    "ows-numero": ["0000066"]
                            }
                    }
            }          
             */
        }
        return null;
    }

    //https://tws9.todocompu.com.ec/todocompu/todocompuWS/anexosWebController/amazonSNS
    @RequestMapping("/amazonSNS")
    public @ResponseBody
    String amazonSNS(HttpServletResponse response, @RequestBody String json) {
        try {
            notificacionesAWSService.insertarNotificacion(json);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/generarReporteRetencionesEmitidas")
    public @ResponseBody
    String generarReporteRetencionesEmitidas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoRetencionesEmitidas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListadoCompraElectronicaTO> listado = UtilsJSON.jsonToList(AnxListadoCompraElectronicaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteAnexosService.generarReporteRetencionesEmitidas(usuarioEmpresaReporteTO, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarRetencionesEmitidas")
    public @ResponseBody
    String exportarRetencionesEmitidas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxListadoCompraElectronicaTO> listado = UtilsJSON.jsonToList(AnxListadoCompraElectronicaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarRetencionesEmitidas(usuarioEmpresaReporteTO, listado, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarXmlAutorizacionComprobantes")
    public @ResponseBody
    String generarXmlAutorizacionComprobantes(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        String xml = UtilsJSON.jsonToObjeto(String.class, map.get("xml"));
        String tipoAmbiente = UtilsJSON.jsonToObjeto(String.class, map.get("tipoAmbiente"));
        String nombreReporteJasper = null;
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        byte[] respuesta;
        try {
            if (xml != null && !xml.equals("")) {
                respuesta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xml, "GENERICO", "", usuarioEmpresaReporteTO.getEmpRutaLogo());
                nombreReporteJasper = reporteComprobanteElectronicoService.obtenerNombreReporte(claveAcceso);
                return archivoService.generarReportePDF(respuesta, nombreReporteJasper, response);
            } else {
                RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(claveAcceso, tipoAmbiente, sisInfoTO);
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
                        respuesta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, "GENERICO", "", usuarioEmpresaReporteTO.getEmpRutaLogo());
                        nombreReporteJasper = reporteComprobanteElectronicoService.obtenerNombreReporte(claveAcceso);
                        return archivoService.generarReportePDF(respuesta, nombreReporteJasper, response);
                    }
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerDocumentosPorCedulaRucMesAnio")
    public List<ComprobanteElectronico> obtenerDocumentosPorCedulaRucMesAnio(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String cedulaRuc = UtilsJSON.jsonToObjeto(String.class, map.get("cedulaRuc"));
        String mes = UtilsJSON.jsonToObjeto(String.class, map.get("mes"));
        String anio = UtilsJSON.jsonToObjeto(String.class, map.get("anio"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprobanteElectronicoService.obtenerDocumentosPorCedulaRucMesAnio(cedulaRuc, mes, anio);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getEstablecimientos")
    public RespuestaWebTO getEstablecimientos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxEstablecimientoComboTO> respues = ventaService.getEstablecimientos(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de establecimientos.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getPuntosEmision")
    public RespuestaWebTO getPuntosEmision(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String establecimiento = UtilsJSON.jsonToObjeto(String.class, map.get("establecimiento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxPuntoEmisionComboTO> respues = ventaService.getPuntosEmision(empresa, establecimiento);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron puntos de emisión.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getNumeroAutorizacion")
    public RespuestaWebTO getNumeroAutorizacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String numeroRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("numeroRetencion"));
        String numeroComprobante = UtilsJSON.jsonToObjeto(String.class, map.get("numeroComprobante"));
        String fechaVencimiento = UtilsJSON.jsonToObjeto(String.class, map.get("fechaVencimiento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AnxNumeracionLineaTO respues = ventaService.getNumeroAutorizacion(empresa, numeroRetencion, numeroComprobante, fechaVencimiento);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Número de documento no valido.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoSustentoTO")
    public RespuestaWebTO getAnexoSustentoTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxSustentoTO> respues = sustentoService.getAnexoSustentoTO();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de sustentos tributarios");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxSustentoComboTO")
    public RespuestaWebTO getListaAnxSustentoComboTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String tipoComprobante = UtilsJSON.jsonToObjeto(String.class, map.get("tipoComprobante"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxSustentoComboTO> respues = sustentoService.getListaAnxSustentoComboTO(tipoComprobante);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron tipo de identificación");
            }
        } catch (Exception e) {
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxConceptoComboTO")
    public RespuestaWebTO getListaAnxConceptoComboTO(@RequestBody SisInfoTO sisInfoTO) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        try {
            List<AnxConceptoComboTO> respues = conceptoService.getListaAnxConceptoComboTO();
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron conceptos.");
            }
        } catch (Exception e) {
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxConceptoTO")
    public RespuestaWebTO getListaAnxConceptoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("fechaRetencion"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxConceptoComboTO> respues = conceptoService.getListaAnxConceptoTO(fechaRetencion, busqueda);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getCodigoAnxTipoTransaccionTO")
    public RespuestaWebTO getCodigoAnxTipoTransaccionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String tipoIdentificacion = UtilsJSON.jsonToObjeto(String.class, map.get("tipoIdentificacion"));
        String tipoTransaccion = UtilsJSON.jsonToObjeto(String.class, map.get("tipoTransaccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = tipoTransaccionService.getCodigoAnxTipoTransaccionTO(tipoIdentificacion, tipoTransaccion);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron tipo de comprobante");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxTipoComprobanteComboTO")
    public RespuestaWebTO getListaAnxTipoComprobanteComboTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoTipoTransaccion = UtilsJSON.jsonToObjeto(String.class, map.get("codigoTipoTransaccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxTipoComprobanteComboTO> respues = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron tipo de comprobante");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxTipoComprobanteComboTOCompleto")
    public RespuestaWebTO getListaAnxTipoComprobanteComboTOCompleto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxTipoComprobanteComboTO> listaResultadoBusqueda = tipoComprobanteService.getListaAnxTipoComprobanteComboTOCompleto();
            List<AnxTipoComprobanteComboTO> listaFinal = new ArrayList<>();
            for (AnxTipoComprobanteComboTO item : listaResultadoBusqueda) {
                if (!item.getTcCodigo().trim().equalsIgnoreCase("00")) {
                    listaFinal.add(item);
                }
            }
            if (listaFinal != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaFinal);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getValorAnxPorcentajeIvaTO")
    public RespuestaWebTO getValorAnxPorcentajeIvaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaFactura = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFactura"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            BigDecimal respues = porcentajeIvaService.getValorAnxPorcentajeIvaTO(fechaFactura);
            if (tipoDocumento != null && tipoDocumento.equals("00")) {
                List<String> rucsSinIva = Arrays.asList("0909476103001");
                SisEmpresa e = empresaDao.obtenerPorId(SisEmpresa.class, sisInfoTO.getEmpresa());
                if (e != null && rucsSinIva.contains(e.getEmpRuc())) {
                    respues = BigDecimal.ZERO;
                }
            }
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró porcentaje IVA.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getValorAnxMontoMaximoConsumidorFinalTO")
    public BigDecimal getValorAnxMontoMaximoConsumidorFinalTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaFactura = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFactura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return porcentajeIvaService.getValorAnxMontoMaximoConsumidorFinalTO(fechaFactura);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraTO")
    public AnxCompraTO getAnexoCompraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoCompraTO(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraDetalleTO")
    public List<AnxCompraDetalleTO> getAnexoCompraDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraDetalleService.getAnexoCompraDetalleTO(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraReembolsoTOs")
    public List<AnxCompraReembolsoTO> getAnexoCompraReembolsoTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraReembolsoService.getAnexoCompraReembolsoTOs(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoVentaTO")
    public RespuestaWebTO getAnexoVentaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroVenta = UtilsJSON.jsonToObjeto(String.class, map.get("numeroVenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AnxVentaTO respuesta = ventaService.getAnexoVentaTO(empresa, periodo, motivo, numeroVenta);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontró resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerAnexoVentaTO")
    public RespuestaWebTO obtenerAnexoVentaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = ventaService.obtenerAnexoVentaTO(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoCompra")
    public AnxCompra getAnexoCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoCompra(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoListaRetencionesTO")
    public RespuestaWebTO getAnexoListaRetencionesTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaRetencionesTO> respues = compraService.getAnexoListaRetencionesTO(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron lista de retenciones.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoListaRetencionesRentaTO")
    public RespuestaWebTO getAnexoListaRetencionesRentaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String pOrden = UtilsJSON.jsonToObjeto(String.class, map.get("pOrden"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaRetencionesRentaTO> respues = compraService.getAnexoListaRetencionesRentaTO(empresa, fechaDesde, fechaHasta, pOrden);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron retenciones renta en compras.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoListaRetencionesFuenteIvaTO")
    public RespuestaWebTO getAnexoListaRetencionesFuenteIvaTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaRetencionesFuenteIvaTO> respues = compraService.getAnexoListaRetencionesFuenteIvaTO(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró lista de retenciones.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoFunListadoRetencionesPorNumero")
    public RespuestaWebTO getAnexoFunListadoRetencionesPorNumero(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String parametroEstado = UtilsJSON.jsonToObjeto(String.class, map.get("parametroEstado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaRetencionesTO> respues = compraService.getAnexoFunListadoRetencionesPorNumero(empresa, fechaDesde, fechaHasta,
                    parametroEstado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró lista de retenciones.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnxListaComprobanteAnuladoTO")
    public List<AnxListaComprobanteAnuladoTO> getAnxListaComprobanteAnuladoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anuladosService.getAnxListaComprobanteAnuladoTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxListaConsolidadoRetencionesVentasTO")
    public RespuestaWebTO getAnxListaConsolidadoRetencionesVentasTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaConsolidadoRetencionesVentasTO> respues = ventaService.getAnxListaConsolidadoRetencionesVentasTO(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de lista consolidada por clientes");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnxListaEstablecimientoRetencionesVentasTO")
    public List<AnxListaEstablecimientoRetencionesVentasTO> getAnxListaEstablecimientoRetencionesVentasTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getAnxListaEstablecimientoRetencionesVentasTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxListadoRetencionesVentasTO")
    public RespuestaWebTO getAnxListadoRetencionesVentasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String establecimiento = UtilsJSON.jsonToObjeto(String.class, map.get("establecimiento"));
        String puntoEmision = UtilsJSON.jsonToObjeto(String.class, map.get("puntoEmision"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListadoRetencionesVentasTO> respues = ventaService.getAnxListadoRetencionesVentasTO(empresa, tipoDocumento, establecimiento, puntoEmision,
                    fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró listado retenciones en ventas.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnxFunListadoDevolucionIvaVentasTO")
    public RespuestaWebTO getAnxFunListadoDevolucionIvaVentasTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxFunListadoDevolucionIvaVentasTO> respues = ventaService.getAnxFunListadoDevolucionIvaVentasTO(empresa, fechaDesde, fechaHasta);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró listado devolución IVA ventas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoTalonResumenTO")
    public RespuestaWebTO getAnexoTalonResumenTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxTalonResumenTO> respues = compraService.getAnexoTalonResumenTO(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró talón resumen compras");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarFormulario103")
    public RespuestaWebTO listarFormulario103(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxFormulario103TO> respues = compraService.listarFormulario103(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró datos del formulario 103");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarFormulario104")
    public RespuestaWebTO listarFormulario104(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> extraInfo = new HashMap<>();
            List<AnxFormulario104TO> respues = compraService.listarFormulario104(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                List<AnxDiferenciasTributacionTO> listado = comprasDao.listarDiferenciasTributacion(empresa, fechaDesde, fechaHasta);
                extraInfo.put("listaFormulario104", respues);
                extraInfo.put("listaDiferenciasTributarias", listado);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(extraInfo);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró datos del formulario 104");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoTalonResumenVentaTO")
    public RespuestaWebTO getAnexoTalonResumenVentaTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxTalonResumenVentaTO> respues = ventaService.getAnexoTalonResumenVentaTO(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontró talón resumen ventas");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnexoNumeracionTO")
    public RespuestaWebTO getListaAnexoNumeracionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxNumeracionTablaTO> respuesta = numeracionService.getListaAnexoNumeracionTO(empresa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoNumeracionTO")
    public RespuestaWebTO getAnexoNumeracionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencia = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AnxNumeracionTO respuesta = numeracionService.getAnexoNumeracionTO(secuencia);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaAnexoNumeracionTO")
    public RespuestaWebTO obtenerDatosParaAnexoNumeracionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = numeracionService.obtenerDatosParaAnexoNumeracionTO(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarAnexoNumeracionTO")
    public RespuestaWebTO insertarAnexoNumeracionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxNumeracionTO anxNumeracionTO = UtilsJSON.jsonToObjeto(AnxNumeracionTO.class, map.get("anxNumeracionTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            anxNumeracionTO.setNumCaduca(UtilsValidacion.fecha(anxNumeracionTO.getNumCaduca().trim(), "yyyy-MM-dd", "yyyy/MM/dd"));
            String respuesta = numeracionService.insertarAnexoNumeracionTO(anxNumeracionTO, sisInfoTO);
            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                AnxNumeracion anxNumeracion = numeracionService.obtenerAnexoNumeracion(anxNumeracionTO, empresa);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(anxNumeracion);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarAnexoNumeracionTO")
    public RespuestaWebTO modificarAnexoNumeracionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxNumeracionTO anxNumeracionTO = UtilsJSON.jsonToObjeto(AnxNumeracionTO.class, map.get("anxNumeracionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            anxNumeracionTO.setNumCaduca(UtilsValidacion.fecha(anxNumeracionTO.getNumCaduca().trim(), "yyyy-MM-dd", "yyyy/MM/dd"));
            String respuesta = numeracionService.modificarAnexoNumeracionTO(anxNumeracionTO, sisInfoTO);
            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Hubo un error al modificar la numeración.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarAnexoNumeracionTO")
    public RespuestaWebTO eliminarAnexoNumeracionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxNumeracionTO anxNumeracionTO = UtilsJSON.jsonToObjeto(AnxNumeracionTO.class, map.get("anxNumeracionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = numeracionService.eliminarAnexoNumeracionTO(anxNumeracionTO, sisInfoTO);
            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
                resp.setExtraInfo(true);

            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Hubo un error al eliminar la numeración.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoConceptoTO")
    public RespuestaWebTO getConteoConCuentasTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxConceptoTO> respues = conceptoService.getAnexoConceptoTO();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de concepto de relación");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoTipoComprobanteTO")
    public RespuestaWebTO getAnexoTipoComprobanteTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxTipoComprobanteTO> respues = tipoComprobanteService.getAnexoTipoComprobanteTO();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de tipo de comprobante");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxTipoIdentificacionTO")
    public RespuestaWebTO getListaAnxTipoIdentificacionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxTipoIdentificacionTO> respues = tipoIdentificacionService.getListaAnxTipoIdentificacionTO();
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron tipo de identificación");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoTipoListaTransaccionTO")
    public RespuestaWebTO getAnexoTipoListaTransaccionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxTipoListaTransaccionTO> respues = tipoTransaccionService.getAnexoTipoListaTransaccionTO();
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron tipo de transacción");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxAnuladoTablaTO")
    public RespuestaWebTO getListaAnxAnuladoTablaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxAnuladoTablaTO> respuesta = anuladosService.getListaAnxAnuladoTablaTO(empresa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnxAnuladosTO")
    public RespuestaWebTO getAnxAnuladosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AnxAnuladosTO respuesta = anuladosService.getAnxAnuladosTO(secuencial);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaAnxAnuladosTO")
    public RespuestaWebTO obtenerDatosParaAnxAnuladosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = anuladosService.obtenerDatosParaAnexoAnuladosTO(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarAnexoAnuladoTO")
    public RespuestaWebTO insertarAnexoAnuladoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        AnxAnuladosTO anxAnuladosTO = UtilsJSON.jsonToObjeto(AnxAnuladosTO.class, map.get("anxAnuladosTO"));
        boolean validarRetencionElectronica = UtilsJSON.jsonToObjeto(boolean.class, map.get("validarRetencionElectronica"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = anuladosService.insertarAnexoAnuladoTO(anxAnuladosTO, validarRetencionElectronica, sisInfoTO);
            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                List<AnxAnuladosTO> anxAnulados = anuladosService.obtenerAnexoAnulados(anxAnuladosTO, empresa);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(anxAnulados);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarAnexoAnuladoTO")
    public RespuestaWebTO modificarAnexoAnuladoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxAnuladosTO anxAnuladosTO = UtilsJSON.jsonToObjeto(AnxAnuladosTO.class, map.get("anxAnuladosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = anuladosService.modificarAnexoAnuladoTO(anxAnuladosTO, sisInfoTO);
            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarAnexoAnuladoTO")
    public RespuestaWebTO eliminarAnexoAnuladoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxAnuladosTO anxAnuladosTO = UtilsJSON.jsonToObjeto(AnxAnuladosTO.class, map.get("anxAnuladosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = anuladosService.eliminarAnexoAnuladoTO(anxAnuladosTO, sisInfoTO);
            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnxCuentasContablesTO")
    public RespuestaWebTO getAnxCuentasContablesTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombreCuenta = UtilsJSON.jsonToObjeto(String.class, map.get("nombreCuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AnxCuentasContablesTO respues = cuentasContablesService.getAnxCuentasContablesTO(empresa, nombreCuenta);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados en cuentas contables.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnxCuentasContablesObjetosTO")
    public RespuestaWebTO getAnxCuentasContablesObjetosTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombreCuenta = UtilsJSON.jsonToObjeto(String.class, map.get("nombreCuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AnxCuentasContablesObjetosTO respues = cuentasContablesService.getAnxCuentasContablesObjetosTO(empresa, nombreCuenta);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados en cuentas contables.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/actualizarAnxCuentasContables")
    public RespuestaWebTO actualizarAnxCuentasContables(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxCuentasContablesTO anxCuentasContablesTO = UtilsJSON.jsonToObjeto(AnxCuentasContablesTO.class, map.get("anxCuentasContablesTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = cuentasContablesService.actualizarAnxCuentasContables(anxCuentasContablesTO, empresa, usuario, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se pudo modificar cuentas contables.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarAnxVentas")
    public String eliminarAnxVentas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxVentaTO anxVentaTO = UtilsJSON.jsonToObjeto(AnxVentaTO.class, map.get("anxVentaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.eliminarAnxVentas(anxVentaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionAnxVenta")
    public RespuestaWebTO accionAnxVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxVentaTO anxVentaTO = UtilsJSON.jsonToObjeto(AnxVentaTO.class, map.get("anxVentaTO"));
        String numeroFactura = UtilsJSON.jsonToObjeto(String.class, map.get("numeroFactura"));
        String periodoFactura = UtilsJSON.jsonToObjeto(String.class, map.get("periodoFactura"));
        String cliCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cliCodigo"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = ventaService.accionAnxVenta(anxVentaTO, numeroFactura, periodoFactura, cliCodigo, accion, sisInfoTO);
            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnxFunListadoDevolucionIvaTOs")
    public RespuestaWebTO getAnxFunListadoDevolucionIvaTOs(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxFunListadoDevolucionIvaTO> respues = compraService.getAnxFunListadoDevolucionIvaTOs(empCodigo, desde, hasta);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró listado devolución IVA compras");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/reestructurarRetencion")
    public String reestructurarRetencion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.reestructurarRetencion(anxCompraTO, usuario, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnexoTipoComprobanteTO")
    public List<AnxTipoComprobanteTablaTO> getListaAnexoTipoComprobanteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoComprobanteService.getListaAnexoTipoComprobanteTO(codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoFormaPago")
    public AnxFormaPago getAnexoFormaPago(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formaPagoAnexoService.getAnexoFormaPago(codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraFormaPagoTO")
    public List<AnxCompraFormaPagoTO> getAnexoCompraFormaPagoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraFormaPagoService.getAnexoCompraFormaPagoTO(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraFormaPago")
    public List<AnxCompraFormaPago> getAnexoCompraFormaPago(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraFormaPagoService.getAnexoCompraFormaPago(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboAnxPaisTO")
    public List<AnxPaisTO> getComboAnxPaisTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return paisService.getComboAnxPaisTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboAnxProvinciaTO")
    public List<AnxProvinciaCantonTO> getComboAnxProvinciaTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return provinciasService.getComboAnxProvinciaTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboAnxCantonTO")
    public RespuestaWebTO getComboAnxCantonTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String provincia = UtilsJSON.jsonToObjeto(String.class, map.get("provincia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxProvinciaCantonTO> respues = provinciasService.getComboAnxCantonTO(provincia);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron cantones");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getComboAnxDpaProvinciaTO")
    public RespuestaWebTO getComboAnxDpaProvinciaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxProvinciaCantonTO> respues = dpaEcuadorService.getComboAnxDpaProvinciaTO();
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron provincias");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getComboAnxDpaCantonTO")
    public RespuestaWebTO getComboAnxDpaCantonTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoProvincia = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProvincia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxProvinciaCantonTO> respues = dpaEcuadorService.getComboAnxDpaCantonTO(codigoProvincia);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron ciudades");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getComboAnxParroquiaTO")
    public List<AnxProvinciaCantonTO> getComboAnxParroquiaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoProvincia = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProvincia"));
        String codigoCanton = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCanton"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return dpaEcuadorService.getComboAnxParroquiaTO(codigoProvincia, codigoCanton);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunRegistroDatosCrediticiosTOs")
    public RespuestaWebTO getFunRegistroDatosCrediticiosTOs(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codigoEmpresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxFunRegistroDatosCrediticiosTO> respues = dpaEcuadorService.getFunRegistroDatosCrediticiosTOs(codigoEmpresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarArchivoAnexoXML")
    public RespuestaWebTO validarArchivoAnexoXML(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> listaMensajes = compraService.getAnexoFunListadoComprobantesPendientes(empresa, fechaDesde, fechaHasta);
            if (listaMensajes != null && !listaMensajes.isEmpty()) {
                resp.setExtraInfo(listaMensajes);
                throw new GeneralException("Existen Comprobantes pendientes, no se puede generar el XML.");
            }
            listaMensajes = compraService.getAnexoFunListadoRetencionesHuerfanas(empresa, fechaDesde, fechaHasta);
            if (listaMensajes != null && !listaMensajes.isEmpty()) {
                resp.setExtraInfo(listaMensajes);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
                throw new GeneralException("En el archivo XML existen Retenciones huerfanas");
            }
            List<AnxRespuestasErroresPorNotasDeDebitoTO> erroresPorNotasDebito = reporteAnexosService.validacionPorNotasDebito(empresa, fechaDesde, fechaHasta);
            if (erroresPorNotasDebito != null && !erroresPorNotasDebito.isEmpty()) {
                resp.setExtraInfo(erroresPorNotasDebito);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
                throw new GeneralException("El anexo transaccional se generará sin la información correspondiente a las retenciones en notas de débito. Por favor adicionar de acuerdo al siguiente listado:");
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(true);
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionAnxUrlWebServicesTO")
    public RespuestaWebTO accionAnxUrlWebServicesTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        anxUrlWebServicesTO anxUrlWebServicesTO = UtilsJSON.jsonToObjeto(anxUrlWebServicesTO.class, map.get("anxUrlWebServicesTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = urlWebServicesService.accionAnxUrlWebServicesTO(anxUrlWebServicesTO, accion, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de URL web services");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificacionURL")
    public RespuestaWebTO verificacionURL(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String urlVerificacion = UtilsJSON.jsonToObjeto(String.class, map.get("urlVerificacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            urlWebServicesService.verificarAnxUrlWebServicesTO(urlVerificacion);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo("La URL respondio correctamente");
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnxUrlWebServicesTO")
    public RespuestaWebTO getAnxUrlWebServicesTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            anxUrlWebServicesTO respues = urlWebServicesService.getAnxUrlWebServicesTO();
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de URL web services");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxVentaElectronicaTO")
    public RespuestaWebTO getListaAnxVentaElectronicaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaVentaElectronicaTO> respues = ventaService.getListaAnxVentaElectronicaTO(empresa, fechaDesde, fechaHasta, tipoDocumento);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarComprobantesPublicos")
    public RespuestaWebTO listarComprobantesPublicos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String comprobante = UtilsJSON.jsonToObjeto(String.class, map.get("comprobante"));
        String identificador = UtilsJSON.jsonToObjeto(String.class, map.get("identificador"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoComprobante"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComprobantesTO> respues = comprobantesPublicosService.listarComprobantesPublicos(comprobante, identificador, fechaDesde, fechaHasta, tipoDocumento);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxComprasElectronicaTO")
    public RespuestaWebTO getListaAnxComprasElectronicaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String estado = UtilsJSON.jsonToObjeto(String.class, map.get("estado"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListadoCompraElectronicaTO> respues = compraService.getListaAnxComprasElectronicaTO(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de compras electrónicas");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnexoListaRetencionesPendienteTO")
    public RespuestaWebTO getAnexoListaRetencionesPendienteTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaRetencionesPendientesTO> respuesta = compraService.getAnexoListaRetencionesPendienteTO(empresa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaVentasPendientes")
    public RespuestaWebTO getListaVentasPendientes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {

            SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
            if (parametros != null) {
                if (parametros.isParFacturasEnProceso()) {
                    resp.setExtraInfo(false);
                    resp.setOperacionMensaje("Existe un proceso en curso, espere un mensaje de confirmación al correo: " + sisInfoTO.getEmail() + ", para poder realizar el siguiente envío de comprobantes.");
                    return resp;
                }
            }

            List<AnxListaVentasPendientesTO> respuesta = ventaElectronicaService.getListaVentasPendientes(empresa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Ventas electrónicas emitidas archivo XML*/
    @RequestMapping("/getXmlComprobanteElectronico")
    public RespuestaWebTO getXmlComprobanteElectronico(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String ePeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("ePeriodo"));
        String eMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("eMotivo"));
        String eNumero = UtilsJSON.jsonToObjeto(String.class, map.get("eNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = ventaElectronicaService.getXmlComprobanteElectronico(empresa, ePeriodo, eMotivo, eNumero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/comprobarAnxVentaElectronicaAutorizacion")
    public String comprobarAnxVentaElectronicaAutorizacion(@RequestBody String json) {

        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaElectronicaService.comprobarAnxVentaElectronicaAutorizacion(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxUltimaAutorizacionTO")
    public RespuestaWebTO getAnxUltimaAutorizacionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String secuencial = UtilsJSON.jsonToObjeto(String.class, map.get("secuencial"));
        String fechaFactura = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFactura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AnxUltimaAutorizacionTO respues = compraService.getAnxUltimaAutorizacionTO(empresa, proveedor, tipoDocumento, secuencial, fechaFactura);
            if (respues != null && !respues.equals("")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRetencionesRentaCompras")
    public String generarReporteRetencionesRentaCompras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRetencionesRentaComprasTipoDocumento.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaRetencionesRentaTO> listaAnxConsolidadoRetencionesRentaTO = UtilsJSON.jsonToList(AnxListaRetencionesRentaTO.class, parametros.get("listaAnxConsolidadoRetencionesRentaTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRetencionesRentaComprasTD(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaAnxConsolidadoRetencionesRentaTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarRetencionesRentaCompras")
    public @ResponseBody
    String exportarRetencionesRentaCompras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AnxListaRetencionesRentaTO> listaAnxConsolidadoRetencionesRentaTO = UtilsJSON.jsonToList(AnxListaRetencionesRentaTO.class, map.get("listaAnxConsolidadoRetencionesRentaTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String orden = UtilsJSON.jsonToObjeto(String.class, map.get("orden"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRetencionesRentaComprasTD(usuarioEmpresaReporteTO, listaAnxConsolidadoRetencionesRentaTO, fechaDesde, fechaHasta, orden);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRetencionesIvaCompras")
    public String generarReporteRetencionesIvaCompras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRetencionesIvaCompras.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaRetencionesFuenteIvaTO> listaAnxRetencionesFuentesIva = UtilsJSON.jsonToList(AnxListaRetencionesFuenteIvaTO.class, parametros.get("listaAnxRetencionesFuentesIva"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRetencionesIvaCompras(usuarioEmpresaReporteTO, nombreReporte, fechaDesde, fechaHasta, listaAnxRetencionesFuentesIva);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarRetencionesIvaCompras")
    public @ResponseBody
    String exportarRetencionesIvaCompras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AnxListaRetencionesFuenteIvaTO> listaAnxRetencionesFuentesIva = UtilsJSON.jsonToList(AnxListaRetencionesFuenteIvaTO.class, map.get("listaAnxRetencionesFuentesIva"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRetencionesIvaCompras(usuarioEmpresaReporteTO, listaAnxRetencionesFuentesIva, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoRetencionesVentas")
    public String generarReporteConsolidadoRetencionesVentas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsolidadoRetencionesVentas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaConsolidadoRetencionesVentasTO> listaAnxConsolidadoRetencionesVentasTO = UtilsJSON.jsonToList(AnxListaConsolidadoRetencionesVentasTO.class, parametros.get("listaAnxConsolidadoRetencionesVentasTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteConsolidadoRetencionesVentas(usuarioEmpresaReporteTO, desde, hasta, listaAnxConsolidadoRetencionesVentasTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConsolidadoRetencionesVentas")
    public @ResponseBody
    String exportarReporteConsolidadoRetencionesVentas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AnxListaConsolidadoRetencionesVentasTO> listaAnxConsolidadoRetencionesVentasTO = UtilsJSON.jsonToList(AnxListaConsolidadoRetencionesVentasTO.class, map.get("listaAnxConsolidadoRetencionesVentasTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteConsolidadoRetencionesVentas(usuarioEmpresaReporteTO, listaAnxConsolidadoRetencionesVentasTO, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteTalonResumen")
    public String generarReporteTalonResumen(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportTalonResumenRetenciones.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxTalonResumenTO> listaAnxTalonResumenTO = UtilsJSON.jsonToList(AnxTalonResumenTO.class, parametros.get("listaAnxTalonResumenTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteTalonResumen(usuarioEmpresaReporteTO, desde, hasta, listaAnxTalonResumenTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteFormulario103")
    public String generarReporteFormulario103(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportFormulario103.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxFormulario103TO> lista = UtilsJSON.jsonToList(AnxFormulario103TO.class, parametros.get("lista"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteFormulario103(usuarioEmpresaReporteTO, desde, hasta, lista);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteFormulario104")
    public String generarReporteFormulario104(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportFormulario104.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxFormulario104TO> lista = UtilsJSON.jsonToList(AnxFormulario104TO.class, parametros.get("lista"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteFormulario104(usuarioEmpresaReporteTO, desde, hasta, lista);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteAnexoTalonResumenCompras")
    public @ResponseBody
    String exportarReporteAnexoTalonResumenCompras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AnxTalonResumenTO> listaAnxTalonResumenTO = UtilsJSON.jsonToList(AnxTalonResumenTO.class, map.get("listaAnxTalonResumenTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteTalonResumen(usuarioEmpresaReporteTO, listaAnxTalonResumenTO, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteFormulario103")
    public @ResponseBody
    String exportarReporteFormulario103(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AnxFormulario103TO> lista = UtilsJSON.jsonToList(AnxFormulario103TO.class, map.get("lista"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarReporteFormulario103(usuarioEmpresaReporteTO, lista, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteFormulario104")
    public @ResponseBody
    String exportarReporteFormulario104(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AnxFormulario104TO> lista = UtilsJSON.jsonToList(AnxFormulario104TO.class, map.get("lista"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarReporteFormulario104(usuarioEmpresaReporteTO, lista, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteTalonResumenVentas")
    public String generarReporteTalonResumenVentas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportTalonResumenVentas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        List<AnxTalonResumenVentaTO> listaAnxTalonResumenVentaTO = UtilsJSON.jsonToList(AnxTalonResumenVentaTO.class, parametros.get("listaAnxTalonResumenVentaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteTalonResumenVentas(usuarioEmpresaReporteTO, desde, hasta, listaAnxTalonResumenVentaTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteAnexoTalonResumenVentas")
    public @ResponseBody
    String exportarReporteAnexoTalonResumenVentas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<AnxTalonResumenVentaTO> listaAnxTalonResumenVentaTO = UtilsJSON.jsonToList(AnxTalonResumenVentaTO.class, map.get("listaAnxTalonResumenVentaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteTalonResumenVentas(usuarioEmpresaReporteTO, listaAnxTalonResumenVentaTO, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteAnexoListaRetencionesTO")
    public String generarReporteAnexoListaRetencionesTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoRetencionesCompras.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxListaRetencionesTO> listaRetencionesTO = UtilsJSON.jsonToList(AnxListaRetencionesTO.class, parametros.get("listaRetencionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteAnexoListadoRetencionesTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaRetencionesTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteAnexoListaRetencionesTO")
    public @ResponseBody
    String exportarReporteAnexoListaRetencionesTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxListaRetencionesTO> listaRetencionesTO = UtilsJSON.jsonToList(AnxListaRetencionesTO.class, map.get("listaRetencionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteAnexoListadoRetencionesTO(usuarioEmpresaReporteTO, listaRetencionesTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoRetencionesPorNumero")
    public @ResponseBody
    String generarReporteListadoRetencionesPorNumero(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoRetencionesComprasPorNumero.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaRetencionesTO> listaAnxListaRetencionesTO = UtilsJSON.jsonToList(AnxListaRetencionesTO.class, parametros.get("listaAnxListaRetencionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoRetencionesPorNumero(usuarioEmpresaReporteTO, listaAnxListaRetencionesTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteAnexoListaRetencionesPorNumeroTO")
    public @ResponseBody
    String exportarReporteAnexoListaRetencionesPorNumeroTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxListaRetencionesTO> listaAnxListaRetencionesTO = UtilsJSON.jsonToList(AnxListaRetencionesTO.class, map.get("listaAnxListaRetencionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoRetencionesPorNumero(listaAnxListaRetencionesTO, usuarioEmpresaReporteTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoRetencionesVentas")
    public @ResponseBody
    String generarReporteListadoRetencionesVentas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoRetencionesVentas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        List<AnxListadoRetencionesVentasTO> anxListadoRetencionesVentasTOs = UtilsJSON.jsonToList(AnxListadoRetencionesVentasTO.class, parametros.get("anxListadoRetencionesVentasTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoRetencionesVentas(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, anxListadoRetencionesVentasTOs);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoRetencionesVentas")
    public @ResponseBody
    String exportarReporteListadoRetencionesVentas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<AnxListadoRetencionesVentasTO> anxListadoRetencionesVentasTOs = UtilsJSON.jsonToList(AnxListadoRetencionesVentasTO.class, map.get("anxListadoRetencionesVentasTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoRetencionesVentas(usuarioEmpresaReporteTO, anxListadoRetencionesVentasTOs, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarAnxRespuestasErroresPorNotasDeDebitoTO")
    public @ResponseBody
    String exportarAnxRespuestasErroresPorNotasDeDebitoTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AnxRespuestasErroresPorNotasDeDebitoTO> lista = UtilsJSON.jsonToList(AnxRespuestasErroresPorNotasDeDebitoTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarAnxRespuestasErroresPorNotasDeDebitoTO(usuarioEmpresaReporteTO, lista);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoDevolucionIva")
    public @ResponseBody
    String generarReporteListadoDevolucionIva(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoDevolucionIva.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        List<AnxFunListadoDevolucionIvaTO> anxFunListadoDevolucionIvaTOs = UtilsJSON.jsonToList(AnxFunListadoDevolucionIvaTO.class, parametros.get("anxFunListadoDevolucionIvaTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoDevolucionIva(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, anxFunListadoDevolucionIvaTOs);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoDevolucionIva")
    public @ResponseBody
    String exportarReporteListadoDevolucionIva(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        List<AnxFunListadoDevolucionIvaTO> anxFunListadoDevolucionIvaTOs = UtilsJSON.jsonToList(AnxFunListadoDevolucionIvaTO.class, map.get("anxFunListadoDevolucionIvaTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoDevolucionIva(usuarioEmpresaReporteTO, anxFunListadoDevolucionIvaTOs, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoDevolucionIvaVentas")
    public String generarReporteListadoDevolucionIvaVentas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoDevolucionIvaVentas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxFunListadoDevolucionIvaVentasTO> anxFunListadoDevolucionIvaVentasTO = UtilsJSON.jsonToList(AnxFunListadoDevolucionIvaVentasTO.class, parametros.get("anxFunListadoDevolucionIvaVentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoDevolucionIvaVentas(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, anxFunListadoDevolucionIvaVentasTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoDevolucionIvaVentas")
    public @ResponseBody
    String exportarReporteListadoDevolucionIvaVentas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxFunListadoDevolucionIvaVentasTO> anxFunListadoDevolucionIvaVentasTO = UtilsJSON.jsonToList(AnxFunListadoDevolucionIvaVentasTO.class, map.get("anxFunListadoDevolucionIvaVentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoDevolucionIvaVentas(usuarioEmpresaReporteTO, anxFunListadoDevolucionIvaVentasTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteVentasElectronicasEmitidas")
    public @ResponseBody
    String generarReporteVentasElectronicasEmitidas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportVentasElectronicasEmitidas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaVentaElectronicaTO> listado = UtilsJSON.jsonToList(AnxListaVentaElectronicaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteVentasElectronicasEmitidas(usuarioEmpresaReporteTO, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarVentasElectronicasEmitidas")
    public @ResponseBody
    String exportarVentasElectronicasEmitidas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxListaVentaElectronicaTO> listado = UtilsJSON.jsonToList(AnxListaVentaElectronicaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarVentasElectronicasEmitidas(usuarioEmpresaReporteTO, listado, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarRideVentasElectronicasEmitidas")
    public @ResponseBody
    String generarRideVentasElectronicasEmitidas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        List<InputStream> respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaVentaElectronicaTO> listaAnxListaVentaElectronicaTO = UtilsJSON.jsonToList(AnxListaVentaElectronicaTO.class, parametros.get("listaAnxListaVentaElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarReporteRide(empresa, listaAnxListaVentaElectronicaTO);
            return archivoService.generaReportePDFMultiple(respuesta, response, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarRideVentasElectronicasEmitidasMatricial")
    public RespuestaWebTO generarRideVentasElectronicasEmitidasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaVentaElectronicaTO> listaAnxListaVentaElectronicaTO = UtilsJSON.jsonToList(AnxListaVentaElectronicaTO.class, parametros.get("listaAnxListaVentaElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarReporteRideMatricial(empresa, listaAnxListaVentaElectronicaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarXmlVentasElectronicasEmitidas")
    public @ResponseBody
    String exportarXmlVentasElectronicasEmitidas(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ArchivoTO> list = new ArrayList<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaVentaElectronicaTO> listaAnxListaVentaElectronicaTO = UtilsJSON.jsonToList(AnxListaVentaElectronicaTO.class, map.get("listaAnxListaVentaElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("usuario"));
        try {
            for (AnxListaVentaElectronicaTO item : listaAnxListaVentaElectronicaTO) {
                String xmlAutorizacion = ventaElectronicaService.getXmlComprobanteElectronico(empresa, item.getVtaPeriodo(), item.getVtaMotivo(), item.getVtaNumero());
                String nombre = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                File contenido = genericReporteService.respondeServidorXML(xmlAutorizacion);
                list.add(new ArchivoTO(contenido, nombre));
            }
            genericReporteService.generarZip(empresa, list, response);
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarComprobantesPorCorreo")
    public RespuestaWebTO enviarComprobantesPorCorreo(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resultado = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvComprobantesTO> comprobantes = UtilsJSON.jsonToList(InvComprobantesTO.class, map.get("listadoComprobantes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> resp = new ArrayList<>();
            for (InvComprobantesTO item : comprobantes) {
                String nombreReporte = "";
                if (item.getTipo().equalsIgnoreCase("18")) {
                    nombreReporte = "reportComprobanteFacturaRide";
                } else if (item.getTipo().equalsIgnoreCase("05")) {
                    nombreReporte = "reportComprobanteNotaDebitoRide";
                } else if (item.getTipo().equalsIgnoreCase("04")) {
                    nombreReporte = "reportComprobanteNotaCreditoRide";
                }
                String xmlAutorizacion = ventaElectronicaService.getXmlComprobanteElectronico(item.getEmpresa(), item.getPeriodo(), item.getMotivo(), item.getNumero());
                String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                String mensajeCorreo = compraElectronicaService.enviarEmailComprobantesElectronicos(item.getEmpresa(), item.getPeriodo(), item.getMotivo(), item.getNumero(), claveAcceso, nombreReporte, xmlAutorizacion, sisInfoTO);
                RespuestaWebTO respuesta = new RespuestaWebTO();
                if (mensajeCorreo != null && mensajeCorreo.length() > 0 && mensajeCorreo.charAt(0) == 'T') {
                    respuesta.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    respuesta.setOperacionMensaje("Se envió el comprobante N: " + item.getDocumentoNumero());
                } else if (mensajeCorreo != null && mensajeCorreo.length() > 0 && mensajeCorreo.charAt(0) == 'F') {
                    respuesta.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    respuesta.setOperacionMensaje(mensajeCorreo.length() > 1 ? mensajeCorreo.substring(1) + " " + item.getDocumentoNumero() : "No se envió comprobante N. " + item.getDocumentoNumero());
                } else {
                    respuesta.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    respuesta.setOperacionMensaje("El comprobante N. " + item.getDocumentoNumero() + "no se envió debido a que existen errores en su estructura.");
                }
                resp.add(respuesta);
            }
            resultado.setExtraInfo(resp);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resultado;
    }

    @RequestMapping("/enviarPorCorreoXmlVentasElectronicasEmitidas")
    public RespuestaWebTO enviarPorCorreoXmlVentasElectronicasEmitidas(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaVentaElectronicaTO> listaAnxListaVentaElectronicaTO = UtilsJSON.jsonToList(AnxListaVentaElectronicaTO.class, map.get("listaAnxListaVentaElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
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
                String mensajeCorreo = compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, item.getVtaPeriodo(), item.getVtaMotivo(), item.getVtaNumero(), claveAcceso, nombreReporte, xmlAutorizacion, sisInfoTO);
                if (mensajeCorreo.charAt(0) == 'F') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensajeCorreo.substring(1));
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(mensajeCorreo);
                }
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarRideRetencionesElectronicasEmitidas")
    public @ResponseBody
    String generarRideRetencionesElectronicasEmitidas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        List<InputStream> respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO = UtilsJSON.jsonToList(AnxListadoCompraElectronicaTO.class, parametros.get("listaAnxListaCompraElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarReporteRideRetencionesEmitidas(empresa, listaAnxListaCompraElectronicaTO);
            return archivoService.generaReportePDFMultiple(respuesta, response, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarRideRetencionesElectronicasEmitidasMatricial")
    public RespuestaWebTO generarRideRetencionesElectronicasEmitidasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO = UtilsJSON.jsonToList(AnxListadoCompraElectronicaTO.class, parametros.get("listaAnxListaCompraElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarReporteRideRetencionesEmitidasMatricial(empresa, listaAnxListaCompraElectronicaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarXmlRetencionesElectronicasEmitidas")
    public @ResponseBody
    String exportarXmlRetencionesElectronicasEmitidas(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ArchivoTO> respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO = UtilsJSON.jsonToList(AnxListadoCompraElectronicaTO.class, map.get("listaAnxListaCompraElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarXmlRetencionesElectronicasEmitidas(empresa, listaAnxListaCompraElectronicaTO);
            genericReporteService.generarZip(empresa, respuesta, response);
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarPorCorreoXmlRetencionesElectronicasEmitidas")
    public RespuestaWebTO enviarPorCorreoXmlRetencionesElectronicasEmitidas(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListadoCompraElectronicaTO> listaAnxListaCompraElectronicaTO = UtilsJSON.jsonToList(AnxListadoCompraElectronicaTO.class, map.get("listaAnxListaCompraElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            for (AnxListadoCompraElectronicaTO itemRetencion : listaAnxListaCompraElectronicaTO) {
                String periodo = itemRetencion.getCompPeriodo();
                String motivo = itemRetencion.getCompMotivo();
                String numero = itemRetencion.getCompNumero();
                String xmlAutorizacion = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
                String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                String mensaje = compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, periodo, motivo, numero, claveAcceso, "reportComprobanteRetencionRide", xmlAutorizacion, sisInfoTO);
                if (mensaje.charAt(0) == 'F') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setExtraInfo(mensaje.substring(1));
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1));
                }
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRegistroDatosCrediticio")
    public @ResponseBody
    String generarReporteRegistroDatosCrediticio(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportAnxRegistroDatosCrediticios.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                parametros.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        List<AnxFunRegistroDatosCrediticiosTO> listaAnxFunRegistroDatosCrediticiosTO = UtilsJSON.jsonToList(AnxFunRegistroDatosCrediticiosTO.class,
                parametros.get("listaAnxFunRegistroDatosCrediticiosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRegistroDatosCrediticios(usuarioEmpresaReporteTO, desde, hasta, listaAnxFunRegistroDatosCrediticiosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarRegitroDatosCrediticio")
    public @ResponseBody
    String exportarRegitroDatosCrediticio(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<AnxFunRegistroDatosCrediticiosTO> listaAnxFunRegistroDatosCrediticiosTO = UtilsJSON.jsonToList(AnxFunRegistroDatosCrediticiosTO.class, map.get("listaAnxFunRegistroDatosCrediticiosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRegistroDatosCrediticios(usuarioEmpresaReporteTO, listaAnxFunRegistroDatosCrediticiosTO, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteTxtDatosCrediticios")
    public @ResponseBody
    void exportarReporteTxtDatosCrediticios(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<AnxFunRegistroDatosCrediticiosTO> listado = UtilsJSON.jsonToList(AnxFunRegistroDatosCrediticiosTO.class, map.get("datosCrediticios"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            reporteAnexosService.generarTXT(listado, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
    }

    @RequestMapping("/enviarAutorizarRetencionElectronicaLote")
    public RespuestaWebTO enviarAutorizarRetencionElectronicaLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean esQuartz = UtilsJSON.jsonToObjeto(boolean.class, map.get("esQuartz"));
        List<AnxListaRetencionesPendientesTO> listaEnviar = UtilsJSON.jsonToList(AnxListaRetencionesPendientesTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();

        try {
            if (esQuartz) {
                sisInfoTO = new SisInfoTO(empresa, "QUARTZ", "QUARTZ", "", "WEB");
            }
            String respuesta = enviarComprobantesWSService.enviarAutorizarRetencionElectronicaLote(empresa, listaEnviar, sisInfoTO);
            listaMensajes = respuesta.split("\\|");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals("")) {
                    listaMensajesEnviar.add(listaMensajes[i].substring(1));
                }
            }

            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaMensajesEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setExtraInfo(listaMensajesEnviar);
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarRideRetencionesPendientes")
    public @ResponseBody
    String generarRideRetencionesPendientes(HttpServletResponse response, @RequestBody Map<String, Object> parametros) throws Exception {
        List<InputStream> respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaRetencionesPendientesTO> listaRetenciones = UtilsJSON.jsonToList(AnxListaRetencionesPendientesTO.class, parametros.get("listaRetenciones"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarRideRetencionesPendientes(empresa, listaRetenciones);
            return archivoService.generaReportePDFMultiple(respuesta, response, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarAutorizarFacturasElectronicaLote")
    public RespuestaWebTO enviarAutorizarFacturasElectronicaLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean esQuartz = UtilsJSON.jsonToObjeto(boolean.class, map.get("esQuartz"));
        List<AnxListaVentasPendientesTO> listaEnviar = UtilsJSON.jsonToList(AnxListaVentasPendientesTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();

        try {
            if (esQuartz) {
                sisInfoTO = new SisInfoTO(empresa, "QUARTZ", "QUARTZ", "", "WEB");
            }
            String respuesta = enviarComprobantesWSService.enviarAutorizarFaturasElectronicaLote(empresa, listaEnviar, sisInfoTO);
            listaMensajes = respuesta.split("\\|");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals("")) {
                    listaMensajesEnviar.add(listaMensajes[i].substring(1));
                }
            }

            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaMensajesEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setExtraInfo(listaMensajesEnviar);
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirRideVentasPendientesElectronicas")
    public @ResponseBody
    String imprimirRideVentasPendientesElectronicas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        List<InputStream> respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaVentasPendientesTO> listaEnviar = UtilsJSON.jsonToList(AnxListaVentasPendientesTO.class, parametros.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.imprimirRideVentasPendientesElectronicas(empresa, listaEnviar);
            return archivoService.generaReportePDFMultiple(respuesta, response, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarEmailComprobantesElectronicos")
    public String enviarEmailComprobantesElectronicos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String ePeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("ePeriodo"));
        String eMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("eMotivo"));
        String eNumero = UtilsJSON.jsonToObjeto(String.class, map.get("eNumero"));
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        String nombreReporteJasper = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporteJasper"));
        String XmlString = UtilsJSON.jsonToObjeto(String.class, map.get("XmlString"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, ePeriodo, eMotivo, eNumero,
                    claveAcceso, nombreReporteJasper, XmlString, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoFormaPagoTO")
    public RespuestaWebTO getAnexoFormaPagoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Date fechaFactura = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaFactura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxFormaPagoTO> respues = formaPagoAnexoService.getAnexoFormaPagoTO(fechaFactura);
            if (respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxSustentoComboTOByTipoCredito")
    public RespuestaWebTO getListaAnxSustentoComboTOByTipoCredito(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String tipoDoc = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDoc"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxSustentoComboTO> respues = sustentoService.getListaAnxSustentoComboTOByTipoCredito(tipoDoc, null);
            if (respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxConceptoComboTO2")
    public RespuestaWebTO getListaAnxConceptoComboTO2(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("fechaRetencion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxConceptoComboTO> respues = conceptoService.getListaAnxConceptoComboTO(fechaRetencion);
            if (respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Rtencion
    @RequestMapping("/comprobarRetencionAutorizadaProcesamiento")
    public RespuestaWebTO comprobarRetencionAutorizadaProcesamiento(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = compraElectronicaService.comprobarRetencionAutorizadaProcesamiento(empresa, periodo, motivo, numero);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getUltimaNumeracionComprobante")
    public RespuestaWebTO getUltimaNumeracionComprobante(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String comprobante = UtilsJSON.jsonToObjeto(String.class, map.get("comprobante"));
        String secuencial = UtilsJSON.jsonToObjeto(String.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = ventaService.getUltimaNumeracionComprobante(empresa, comprobante, secuencial);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarSecuenciaPermitida")
    public RespuestaWebTO validarSecuenciaPermitida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String numeroRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("numeroRetencion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = compraService.validarSecuenciaPermitida(empresa, numeroRetencion, sisInfoTO);
            if (respues != null && respues.substring(0, 0).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerAutorizacionNCND")
    public RespuestaWebTO obtenerAutorizacionNCND(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        String codTipoComprobante = UtilsJSON.jsonToObjeto(String.class, map.get("codTipoComprobante"));
        String numComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("numComplemento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = compraService.getCompAutorizacion(empCodigo, provCodigo, codTipoComprobante, numComplemento);
            if (respues != null && !respues.equals("")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("El número de complemento no existe");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarXmlAnexoTransaccional")
    public @ResponseBody
    String generarXmlAnexoTransaccional(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ArchivoTO> lista = new ArrayList<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String ruc = UtilsJSON.jsonToObjeto(String.class, map.get("ruc"));
        String nombreEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("nombreEmpresa"));
        Boolean agente = UtilsJSON.jsonToObjeto(Boolean.class, map.get("agente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("usuario"));
        try {
            ArchivoTO respuesta = reporteAnexosService.generarXmlAnexoTransaccional(empresa, fechaDesde, fechaHasta, ruc, nombreEmpresa, agente);
            lista.add(respuesta);
            genericReporteService.generarZip(empresa, lista, response);
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarArchivoEnXmlAnexoTransaccional")
    public @ResponseBody
    String generarArchivoEnXmlAnexoTransaccional(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String ruc = UtilsJSON.jsonToObjeto(String.class, map.get("ruc"));
        boolean comprimir = UtilsJSON.jsonToObjeto(boolean.class, map.get("comprimir"));
        String nombreEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("nombreEmpresa"));
        Boolean agente = UtilsJSON.jsonToObjeto(Boolean.class, map.get("agente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("usuario"));
        try {
            ArchivoTO respuesta = reporteAnexosService.generarXmlAnexoTransaccional(empresa, fechaDesde, fechaHasta, ruc, nombreEmpresa, agente);
            if (comprimir) {
                genericReporteService.generarXMLComprimido(respuesta.getNombre(), respuesta.getContenido(), response);
            } else {
                genericReporteService.generarXML(respuesta.getNombre(), respuesta.getContenido(), response);
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteRetencion")
    public @ResponseBody
    String generarReporteComprobanteRetencion(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportComprobanteRetencionPreliminar.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String reportComprobanteRetencion = UtilsJSON.jsonToObjeto(String.class, parametros.get("reportComprobanteRetencion"));
        List<AnxListaRetencionesTO> anxListaRetencionesTO = UtilsJSON.jsonToList(AnxListaRetencionesTO.class, parametros.get("anxListaRetencionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        boolean sinDescripcionDocumento = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("sinDescripcionDocumento"));

        try {
            respuesta = reporteAnexosService.generarReporteComprobanteRetencion(usuarioEmpresaReporteTO, empresa, anxListaRetencionesTO, sinDescripcionDocumento, reportComprobanteRetencion);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerDatosConsultaRetencionesVentasListadoSimple")
    public RespuestaWebTO obtenerDatosConsultaRetencionesVentasListadoSimple(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = ventaService.obtenerDatosConsultaRetencionesVentasListadoSimple(map);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //matricial
    @RequestMapping("/generarReporteComprobanteRetencionMatricial")
    public RespuestaWebTO generarReporteComprobanteRetencionMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String reportComprobanteRetencion = UtilsJSON.jsonToObjeto(String.class, parametros.get("reportComprobanteRetencion"));
        List<AnxListaRetencionesTO> anxListaRetencionesTO = UtilsJSON.jsonToList(AnxListaRetencionesTO.class, parametros.get("anxListaRetencionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        boolean sinDescripcionDocumento = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("sinDescripcionDocumento"));
        try {
            respuesta = reporteAnexosService.generarReporteComprobanteRetencion(usuarioEmpresaReporteTO, empresa, anxListaRetencionesTO, sinDescripcionDocumento, reportComprobanteRetencion);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //matricial
    @RequestMapping("/generarReporteListadoDevolucionIvaMatricial")
    public RespuestaWebTO generarReporteListadoDevolucionIvaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        List<AnxFunListadoDevolucionIvaTO> anxFunListadoDevolucionIvaTOs = UtilsJSON.jsonToList(AnxFunListadoDevolucionIvaTO.class, parametros.get("anxFunListadoDevolucionIvaTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoDevolucionIva(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, anxFunListadoDevolucionIvaTOs);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoDevolucionIvaVentasMatricial")
    public RespuestaWebTO generarReporteListadoDevolucionIvaVentasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxFunListadoDevolucionIvaVentasTO> anxFunListadoDevolucionIvaVentasTO = UtilsJSON.jsonToList(AnxFunListadoDevolucionIvaVentasTO.class, parametros.get("anxFunListadoDevolucionIvaVentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoDevolucionIvaVentas(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, anxFunListadoDevolucionIvaVentasTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoRetencionesPorNumeroMatricial")
    public RespuestaWebTO generarReporteListadoRetencionesPorNumeroMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaRetencionesTO> listaAnxListaRetencionesTO = UtilsJSON.jsonToList(AnxListaRetencionesTO.class, parametros.get("listaAnxListaRetencionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoRetencionesPorNumero(usuarioEmpresaReporteTO, listaAnxListaRetencionesTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRetencionesIvaComprasMatricial")
    public RespuestaWebTO generarReporteRetencionesIvaComprasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportRetencionesIvaCompras.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaRetencionesFuenteIvaTO> listaAnxRetencionesFuentesIva = UtilsJSON.jsonToList(AnxListaRetencionesFuenteIvaTO.class, parametros.get("listaAnxRetencionesFuentesIva"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRetencionesIvaCompras(usuarioEmpresaReporteTO, nombreReporte, fechaDesde, fechaHasta, listaAnxRetencionesFuentesIva);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRetencionesEmitidasMatricial")
    public RespuestaWebTO generarReporteRetencionesEmitidasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportRetencionesIvaCompras.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListadoCompraElectronicaTO> listado = UtilsJSON.jsonToList(AnxListadoCompraElectronicaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteAnexosService.generarReporteRetencionesEmitidas(usuarioEmpresaReporteTO, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRetencionesRentaComprasTDMatricial")
    public RespuestaWebTO generarReporteRetencionesRentaComprasTDMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaRetencionesRentaTO> listaAnxConsolidadoRetencionesRentaTO = UtilsJSON.jsonToList(AnxListaRetencionesRentaTO.class, parametros.get("listaAnxConsolidadoRetencionesRentaTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRetencionesRentaComprasTD(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaAnxConsolidadoRetencionesRentaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoRetencionesVentasMatricial")
    public RespuestaWebTO generarReporteConsolidadoRetencionesVentasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaConsolidadoRetencionesVentasTO> listaAnxConsolidadoRetencionesVentasTO = UtilsJSON.jsonToList(AnxListaConsolidadoRetencionesVentasTO.class, parametros.get("listaAnxConsolidadoRetencionesVentasTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteConsolidadoRetencionesVentas(usuarioEmpresaReporteTO, desde, hasta, listaAnxConsolidadoRetencionesVentasTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoRetencionesVentasMatricial")
    public RespuestaWebTO generarReporteListadoRetencionesVentasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        List<AnxListadoRetencionesVentasTO> anxListadoRetencionesVentasTOs = UtilsJSON.jsonToList(AnxListadoRetencionesVentasTO.class, parametros.get("anxListadoRetencionesVentasTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoRetencionesVentas(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, anxListadoRetencionesVentasTOs);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteTalonResumenMatricial")
    public RespuestaWebTO generarReporteTalonResumenMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxTalonResumenTO> listaAnxTalonResumenTO = UtilsJSON.jsonToList(AnxTalonResumenTO.class, parametros.get("listaAnxTalonResumenTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteTalonResumen(usuarioEmpresaReporteTO, desde, hasta, listaAnxTalonResumenTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteFormulario103Matricial")
    public RespuestaWebTO generarReporteFormulario103Matricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxFormulario103TO> lista = UtilsJSON.jsonToList(AnxFormulario103TO.class, parametros.get("lista"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteFormulario103(usuarioEmpresaReporteTO, desde, hasta, lista);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteFormulario104Matricial")
    public RespuestaWebTO generarReporteFormulario104Matricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxFormulario104TO> lista = UtilsJSON.jsonToList(AnxFormulario104TO.class, parametros.get("lista"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteFormulario104(usuarioEmpresaReporteTO, desde, hasta, lista);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteTalonResumenVentasMatricial")
    public RespuestaWebTO generarReporteTalonResumenVentasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        List<AnxTalonResumenVentaTO> listaAnxTalonResumenVentaTO = UtilsJSON.jsonToList(AnxTalonResumenVentaTO.class, parametros.get("listaAnxTalonResumenVentaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteTalonResumenVentas(usuarioEmpresaReporteTO, desde, hasta, listaAnxTalonResumenVentaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprobanteElectronicoMatricial")
    public RespuestaWebTO generarReporteComprobanteElectronicoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        String tipoAmbiente = UtilsJSON.jsonToObjeto(String.class, map.get("tipoAmbiente"));
        String xml = UtilsJSON.jsonToObjeto(String.class, map.get("xml"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        try {
            if (xml != null && !xml.equals("")) {
                respuesta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xml, "GENERICO", "", usuarioEmpresaReporteTO.getEmpRutaLogo());
                resp.setExtraInfo(respuesta);
            } else {
                RespuestaComprobante respuestaComprobante = urlWebServicesService.getAutorizadocionComprobantes(claveAcceso, tipoAmbiente, sisInfoTO);
                if (respuestaComprobante.getAutorizaciones().getAutorizacion() == null || respuestaComprobante.getAutorizaciones().getAutorizacion().isEmpty()) {
                    resp.setEstadoOperacion("El comprobante electrónico con clave de acceso :" + claveAcceso + ", puede que se encuentre anulado, por favor consultelo en el SRI.");
                } else {
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
                            respuesta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, xmlAutorizacion, "GENERICO", "", usuarioEmpresaReporteTO.getEmpRutaLogo());
                            resp.setExtraInfo(respuesta);
                        }
                    }
                }
            }

        } catch (Exception e) {
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteVentasElectronicasEmitidasMatricial")
    public RespuestaWebTO generarReporteVentasElectronicasEmitidasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportVentasElectronicasEmitidas.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaVentaElectronicaTO> listado = UtilsJSON.jsonToList(AnxListaVentaElectronicaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteVentasElectronicasEmitidas(usuarioEmpresaReporteTO, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRegistroDatosCrediticiosMatricial")
    public RespuestaWebTO generarReporteRegistroDatosCrediticiosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        List<AnxFunRegistroDatosCrediticiosTO> listaAnxFunRegistroDatosCrediticiosTO = UtilsJSON.jsonToList(AnxFunRegistroDatosCrediticiosTO.class, parametros.get("listaAnxFunRegistroDatosCrediticiosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRegistroDatosCrediticios(usuarioEmpresaReporteTO, desde, hasta, listaAnxFunRegistroDatosCrediticiosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteAnexoListadoRetencionesTOMatricial")
    public RespuestaWebTO generarReporteAnexoListadoRetencionesTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxListaRetencionesTO> listaRetencionesTO = UtilsJSON.jsonToList(AnxListaRetencionesTO.class, parametros.get("listaRetencionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteAnexoListadoRetencionesTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaRetencionesTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Liquidacion compra pendientes autorizacion
    @RequestMapping("/getListaLiquidacionCompraCompraPendientes")
    public RespuestaWebTO getListaLiquidacionCompraCompraPendientes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaLiquidacionComprasPendientesTO> respuesta = compraElectronicaService.getListaLiquidacionCompraPendientes(empresa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarAutorizarLiquidacionCompra")
    public RespuestaWebTO enviarAutorizarLiquidacionCompraLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaLiquidacionComprasPendientesTO> listaEnviar = UtilsJSON.jsonToList(AnxListaLiquidacionComprasPendientesTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<String>();

        try {
            String respuesta = enviarComprobantesWSService.enviarAutorizarLiquidacionCompras(empresa, listaEnviar, sisInfoTO);
            listaMensajes = respuesta.split("\\|");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals("")) {
                    listaMensajesEnviar.add(listaMensajes[i].substring(1));
                }
            }

            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaMensajesEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setExtraInfo(listaMensajesEnviar);
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarRideLiquidacionCompraPendientes")
    public @ResponseBody
    String generarRideLiquidacionCompraPendientes(HttpServletResponse response, @RequestBody Map<String, Object> parametros) throws Exception {
        List<InputStream> respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaLiquidacionComprasPendientesTO> listado = UtilsJSON.jsonToList(AnxListaLiquidacionComprasPendientesTO.class, parametros.get("listaAnxListaLiquidacionComprasPendientesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarRideLiquidacionCompraPendientes(empresa, listado);
            return archivoService.generaReportePDFMultiple(respuesta, response, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //
    @RequestMapping("/listarNotificacionesVentasElectronicas")
    public RespuestaWebTO listarNotificacionesVentasElectronicas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        try {
            List<AnxVentaElectronicaNotificaciones> respues = ventaElectronicaService.listarNotificacionesVentasElectronicas(empresa, motivo, periodo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se han realizado notificaciones");//
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarNotificacionesRetencionesElectronicas")
    public RespuestaWebTO listarNotificacionesRetencionesElectronicas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        try {
            List<AnxCompraElectronicaNotificaciones> respues = compraElectronicaService.listarNotificacionesRetencionesElectronicas(empresa, motivo, periodo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se han realizado notificaciones");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //GUIA REMISION PENDIENTES
    @RequestMapping("/getListaGuiaRemisionPendientesTO")
    public RespuestaWebTO getListaGuiaRemisionPendientesTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaGuiaRemisionPendientesTO> respuesta = guiaElectronicaService.getListaGuiaRemisionPendientesTO(empresa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxGuiasRemisionElectronicaTO")
    public RespuestaWebTO getListaAnxGuiasRemisionElectronicaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaGuiaRemisionElectronicaTO> respues = guiaElectronicaService.getListaAnxListaGuiaRemisionElectronicaTO(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarNotificacionesGuiasRemisionElectronicas")
    public RespuestaWebTO listarNotificacionesGuiasRemisionElectronicas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        try {
            List<AnxGuiaRemisionElectronicaNotificaciones> respues = guiaElectronicaService.listarNotificacionesGuiasElectronicas(empresa, periodo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encuentra historial de notificaciones");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarAutorizarGuiaRemision")
    public RespuestaWebTO enviarAutorizarGuiaRemision(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        AnxListaGuiaRemisionPendientesTO listaEnviar = UtilsJSON.jsonToObjeto(AnxListaGuiaRemisionPendientesTO.class, map.get("anxListaGuiaRemisionPendientesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<String>();

        try {
            String respuesta = enviarComprobantesWSService.enviarAutorizarGuiaRemision(empresa, listaEnviar, sisInfoTO);
            listaMensajes = respuesta.split("\\|");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals("")) {
                    listaMensajesEnviar.add(listaMensajes[i].substring(1));
                }
            }

            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaMensajesEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setExtraInfo(listaMensajesEnviar);
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarRideGuiaRemisionElectronicasEmitidasMatricial")
    public RespuestaWebTO generarRideGuiaRemisionElectronicasEmitidasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaGuiaRemisionElectronicaTO> listaAnxListaVentaElectronicaTO = UtilsJSON.jsonToList(AnxListaGuiaRemisionElectronicaTO.class, parametros.get("listaAnxListaGuiaRemisionElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarReporteRideGuiaRemisionMatricial(empresa, listaAnxListaVentaElectronicaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarRideGuiaRemisionElectronicasEmitidas")
    public @ResponseBody
    String generarRideGuiaRemisionElectronicasEmitidas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        List<InputStream> respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaGuiaRemisionElectronicaTO> listaAnxListaVentaElectronicaTO = UtilsJSON.jsonToList(AnxListaGuiaRemisionElectronicaTO.class, parametros.get("listaAnxListaGuiaRemisionElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarReporteRideGuiaRemision(empresa, listaAnxListaVentaElectronicaTO);
            return archivoService.generaReportePDFMultiple(respuesta, response, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirRideGuiaRemisionPendientesElectronicas")
    public @ResponseBody
    String imprimirRideGuiaRemisionPendientesElectronicas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        List<InputStream> respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaGuiaRemisionPendientesTO> listaEnviar = UtilsJSON.jsonToList(AnxListaGuiaRemisionPendientesTO.class, parametros.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.imprimirRideGuiaRemisionPendientesElectronicas(empresa, listaEnviar);
            return archivoService.generaReportePDFMultiple(respuesta, response, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarXmlGuiaRemisionElectronicasEmitidas")
    public @ResponseBody
    String exportarXmlGuiaRemisionElectronicasEmitidas(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ArchivoTO> list = new ArrayList<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaGuiaRemisionElectronicaTO> listaAnxListaVentaElectronicaTO = UtilsJSON.jsonToList(AnxListaGuiaRemisionElectronicaTO.class, map.get("listaAnxListaGuiaRemisionElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("usuario"));
        try {
            for (AnxListaGuiaRemisionElectronicaTO item : listaAnxListaVentaElectronicaTO) {
                String xmlAutorizacion = guiaElectronicaService.getXmlGuiaRemision(empresa, item.getGuiaPeriodo(), item.getGuiaNumero());
                String nombre = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                File contenido = genericReporteService.respondeServidorXML(xmlAutorizacion);
                list.add(new ArchivoTO(contenido, nombre));
            }
            genericReporteService.generarZip(empresa, list, response);
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarPorCorreoXmlGuiaRemisionElectronicasEmitidas")
    public RespuestaWebTO enviarPorCorreoXmlGuiaRemisionElectronicasEmitidas(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaGuiaRemisionElectronicaTO> lista = UtilsJSON.jsonToList(AnxListaGuiaRemisionElectronicaTO.class, map.get("listaAnxListaGuiaRemisionElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            for (AnxListaGuiaRemisionElectronicaTO item : lista) {
                String nombreReporte = "reportComprobanteGuiaRemisionRide";

                String xmlAutorizacion = guiaElectronicaService.getXmlGuiaRemision(empresa, item.getGuiaPeriodo(), item.getGuiaNumero());
                String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                String mensajeCorreo = guiaElectronicaService.enviarEmailComprobantesElectronicos(empresa, item.getGuiaPeriodo(), item.getGuiaNumero(), claveAcceso, nombreReporte, xmlAutorizacion);
                if (mensajeCorreo.charAt(0) == 'F') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensajeCorreo);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(mensajeCorreo);
                }
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //LIQUIDACION COMPRAS EMITIDAS
    @RequestMapping("/getListaAnxLiquidacionComprasElectronicaTO")
    public RespuestaWebTO getListaAnxLiquidacionComprasElectronicaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaLiquidacionComprasElectronicaTO> respues = liquidacionCompraElectronicaService.getListaAnxListaLiquidacionCompraElectronicaTO(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarNotificacionesLiquidacionComprasElectronicas")
    public RespuestaWebTO listarNotificacionesLiquidacionComprasElectronicas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        try {
            List<AnxLiquidacionComprasElectronicaNotificaciones> respues = liquidacionCompraElectronicaService.listarNotificacionesElectronicas(empresa, periodo, motivo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encuentra historial de notificaciones");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarRideLiquidacionCompraElectronicasEmitidas")
    public @ResponseBody
    String generarRideLiquidacionCompraElectronicasEmitidas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        List<InputStream> respuesta;
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaLiquidacionComprasElectronicaTO> listaAnxLiquidacionComprasElectronicaTO = UtilsJSON.jsonToList(AnxListaLiquidacionComprasElectronicaTO.class, parametros.get("listaAnxLiquidacionComprasElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarReporteRideLiquidacionCompra(empresa, listaAnxLiquidacionComprasElectronicaTO);
            return archivoService.generaReportePDFMultiple(respuesta, response, empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarRideLiquidacionCompraElectronicasEmitidasMatricial")
    public RespuestaWebTO generarRideLiquidacionCompraElectronicasEmitidasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<AnxListaLiquidacionComprasElectronicaTO> listaAnxLiquidacionComprasElectronicaTO = UtilsJSON.jsonToList(AnxListaLiquidacionComprasElectronicaTO.class, parametros.get("listaAnxLiquidacionComprasElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("usuario"));
        try {
            respuesta = reporteAnexosService.generarRideLiquidacionCompraElectronicasEmitidasMatricial(empresa, listaAnxLiquidacionComprasElectronicaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarXmlLiquidacionCompraElectronicasEmitidas")
    public @ResponseBody
    String exportarXmlLiquidacionCompraElectronicasEmitidas(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ArchivoTO> list = new ArrayList<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaLiquidacionComprasElectronicaTO> listaAnxLiquidacionComprasElectronicaTO = UtilsJSON.jsonToList(AnxListaLiquidacionComprasElectronicaTO.class, map.get("listaAnxLiquidacionComprasElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            for (AnxListaLiquidacionComprasElectronicaTO item : listaAnxLiquidacionComprasElectronicaTO) {
                String xmlAutorizacion = compraElectronicaService.getXmlLiquidacionCompras(empresa, item.getCompPeriodo(), item.getCompMotivo(), item.getCompNumero());
                String nombre = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                File contenido = genericReporteService.respondeServidorXML(xmlAutorizacion);
                list.add(new ArchivoTO(contenido, nombre));
            }
            genericReporteService.generarZip(empresa, list, response);
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarPorCorreoXmlLiquidacionCompraElectronicasEmitidas")
    public RespuestaWebTO enviarPorCorreoXmlLiquidacionCompraElectronicasEmitidas(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaLiquidacionComprasElectronicaTO> listaAnxLiquidacionComprasElectronicaTO = UtilsJSON.jsonToList(AnxListaLiquidacionComprasElectronicaTO.class, map.get("listaAnxLiquidacionComprasElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            for (AnxListaLiquidacionComprasElectronicaTO item : listaAnxLiquidacionComprasElectronicaTO) {
                String nombreReporte = "";
                nombreReporte = "reportComprobanteLiquidacionCompraRide";
                String xmlAutorizacion = compraElectronicaService.getXmlLiquidacionCompras(empresa, item.getCompPeriodo(), item.getCompMotivo(), item.getCompNumero());
                String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                String mensajeCorreo = compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, item.getCompPeriodo(), item.getCompMotivo(), item.getCompNumero(), claveAcceso, nombreReporte, xmlAutorizacion, sisInfoTO);
                if (mensajeCorreo.charAt(0) == 'F') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensajeCorreo.substring(1));
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(mensajeCorreo);
                }
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteLiquidacionComprasEmitidas")
    public @ResponseBody
    String generarReporteLiquidacionComprasEmitidas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionComprasElectronicasEmitidas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaLiquidacionComprasElectronicaTO> listado = UtilsJSON.jsonToList(AnxListaLiquidacionComprasElectronicaTO.class, parametros.get("listaAnxLiquidacionComprasElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteLiquidacionComprasEmitidas(usuarioEmpresaReporteTO, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteLiquidacionComprasEmitidasMatricial")
    public RespuestaWebTO generarReporteLiquidacionComprasEmitidasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportLiquidacionComprasElectronicasEmitidas.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxListaLiquidacionComprasElectronicaTO> listado = UtilsJSON.jsonToList(AnxListaLiquidacionComprasElectronicaTO.class, parametros.get("listaAnxLiquidacionComprasElectronicaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteLiquidacionComprasEmitidas(usuarioEmpresaReporteTO, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarInvListaSecuencialesRetenciones")
    public RespuestaWebTO listarInvListaSecuencialesRetenciones(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String fecha = SistemaWebUtil.obtenerFiltroComoString(parametros, "fecha");
        try {
            List<InvListaSecuencialesTO> respues = compraService.listarInvListaSecuencialesRetenciones(empresa, fecha);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerListadoImagenesCompraNecesitaSoporte")
    public RespuestaWebTO obtenerListadoImagenesCompraNecesitaSoporte(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        List<AnxFunListadoDevolucionIvaTO> listado = UtilsJSON.jsonToList(AnxFunListadoDevolucionIvaTO.class, parametros.get("listado"));
        try {
            List<InvAdjuntosComprasWebTO> respues = compraService.obtenerListadoImagenesCompraNecesitaSoporte(empresa, listado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getBuscarAnexoConceptoTO")
    public RespuestaWebTO getBuscarAnexoConceptoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String conceptoCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("concepto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AnxConceptoTO respues = conceptoService.getBuscarAnexoConceptoTO(fecha, conceptoCodigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró concepto de retención.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //EXPORTACIONES
    @RequestMapping("/getListaAnxCodigoRegimen")
    public RespuestaWebTO getListaAnxCodigoRegimen(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<AnxCodigoRegimen> respues = anxCodigoRegimenService.getListaAnxCodigoRegimen();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxDistritosAduaneros")
    public RespuestaWebTO getListaAnxDistritosAduaneros(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<AnxDistritosAduaneros> respues = anxDistritosAduanerosService.getListaAnxDistritosAduaneros();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxParaisosFiscales")
    public RespuestaWebTO getListaAnxParaisosFiscales(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<AnxParaisosFiscales> respues = anxParaisosFiscalesService.getListaAnxParaisosFiscales();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxTiposExportacion")
    public RespuestaWebTO getListaAnxTiposExportacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<AnxTiposExportacion> respues = anxTiposExportacionService.getListaAnxTiposExportacion();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxTiposIngresoExterior")
    public RespuestaWebTO getListaAnxTiposIngresoExterior(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<AnxTiposIngresoExterior> respues = anxTiposIngresoExteriorService.getListaAnxTiposIngresoExterior();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxTiposRegimenFiscalExterior")
    public RespuestaWebTO getListaAnxTiposRegimenFiscalExterior(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<AnxTiposRegimenFiscalExterior> respues = anxTiposRegimenFiscalExteriorService.getListaAnxTiposRegimenFiscalExterior();
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaExportaciones")
    public RespuestaWebTO obtenerDatosParaExportaciones(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            Map<String, Object> respues = exportacionesService.obtenerDatosParaExportaciones(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encuentra historial de notificaciones");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getAnxListaVentaExportacionTO")
    public RespuestaWebTO getAnxListaVentaExportacionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxListaVentaExportacionTO> respuesta = exportacionesService.getAnxListaVentaExportacionTO(empresa, fechaDesde, fechaHasta);
            if (respuesta != null && !respuesta.isEmpty()) {
                for (int i = 0; i < respuesta.size(); i++) {
                    if (respuesta.get(i).getVtaDocumentoTipo().equals("04")) {
                        respuesta.get(i).setExpValorFobExterior(respuesta.get(i).getExpValorFobExterior().negate());
                        respuesta.get(i).setExpValorFobLocal(respuesta.get(i).getExpValorFobLocal().negate());
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteAnxListaVentaExportacionTO")
    public @ResponseBody
    String exportarReporteAnxListaVentaExportacionTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxListaVentaExportacionTO> listaAnxListaVentaExportacionTO = UtilsJSON.jsonToList(AnxListaVentaExportacionTO.class, map.get("listaAnxListaVentaExportacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarReporteAnxListaVentaExportacionTO(usuarioEmpresaReporteTO, listaAnxListaVentaExportacionTO, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoAnxListaVentaExportacionTOMatricial")
    public RespuestaWebTO generarReporteListadoAnxListaVentaExportacionTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxListaVentaExportacionTO> listaAnxListaVentaExportacionTO = UtilsJSON.jsonToList(AnxListaVentaExportacionTO.class, parametros.get("listaAnxListaVentaExportacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoAnxListaVentaExportacionTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaAnxListaVentaExportacionTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoAnxListaVentaExportacionTO")
    public String generarReporteListadoAnxListaVentaExportacionTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoAnxListaVentaExportacionTO.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxListaVentaExportacionTO> listaAnxListaVentaExportacionTO = UtilsJSON.jsonToList(AnxListaVentaExportacionTO.class, parametros.get("listaAnxListaVentaExportacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoAnxListaVentaExportacionTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listaAnxListaVentaExportacionTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxRetencionCompraTO")
    public RespuestaWebTO getListaAnxRetencionCompraTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String suntento = UtilsJSON.jsonToObjeto(String.class, map.get("sustento"));
        String registros = UtilsJSON.jsonToObjeto(String.class, map.get("registros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxRetencionCompraTO> respues = compraService.listarAnxRetencionCompraTO(empresa, fechaDesde, fechaHasta, suntento, registros);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron lista de retenciones.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaAnxRetencionVentaTO")
    public RespuestaWebTO getListaAnxRetencionVentaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String registro = UtilsJSON.jsonToObjeto(String.class, map.get("registro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxRetencionVentaTO> respues = compraService.getListaAnxRetencionVentaTO(empresa, fechaDesde, fechaHasta, registro);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron lista de retenciones.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteListadoAnxRetencionVentaTO")
    public @ResponseBody
    String exportarReporteListadoAnxRetencionVentaaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxRetencionVentaTO> listado = UtilsJSON.jsonToList(AnxRetencionVentaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarReporteListadoAnxRetencionVentaTO(usuarioEmpresaReporteTO, listado, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoAnxRetencionCompraTO")
    public @ResponseBody
    String exportarReporteListadoAnxRetencionCompraTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxRetencionCompraTO> listado = UtilsJSON.jsonToList(AnxRetencionCompraTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarReporteListadoAnxRetencionCompraTO(usuarioEmpresaReporteTO, listado, fechaDesde, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoAnxRetencionCompraTOMatricial")
    public RespuestaWebTO generarReporteListadoAnxRetencionCompraTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxRetencionCompraTO> listado = UtilsJSON.jsonToList(AnxRetencionCompraTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoAnxRetencionCompraTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoAnxRetencionCompraTO")
    public String generarReporteListadoAnxRetencionCompraTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoAnxRetencionCompraTO.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxRetencionCompraTO> listado = UtilsJSON.jsonToList(AnxRetencionCompraTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoAnxRetencionCompraTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoAnxRetencionVentaTOMatricial")
    public RespuestaWebTO generarReporteListadoAnxRetencionVentaTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxRetencionVentaTO> listado = UtilsJSON.jsonToList(AnxRetencionVentaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoAnxRetencionVentaTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoAnxRetencionVentaTO")
    public String generarReporteListadoAnxRetencionVentaTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoAnxRetencionVentaTO.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<AnxRetencionVentaTO> listado = UtilsJSON.jsonToList(AnxRetencionVentaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteListadoAnxRetencionVentaTO(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/listarSecuencialesRetenciones")
    public RespuestaWebTO listarSecuencialesRetenciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = compraService.listarSecuencialesRetenciones(empresa, fechaDesde, fechaHasta);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarComprobantesElectronicos")
    public RespuestaWebTO insertarComprobantesElectronicos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<AnxComprobantesElectronicosRecibidos> listaComprobantesElectronicos = UtilsJSON.jsonToList(AnxComprobantesElectronicosRecibidos.class, map.get("listadoComprobantesElectronicos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        Boolean respuesta;
        try {
            respuesta = anxComprobantesElectronicosRecibidosService.insertarComprobantesElectronicos(listaComprobantesElectronicos, sisInfoTO);
            if (respuesta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
                resp.setOperacionMensaje("El archivo se guardo de manera exitosa");
            } else {
                resp.setOperacionMensaje("Hubo un error en guardar el archivo");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoFacturasElectronicaLote")
    public RespuestaWebTO enviarCorreoFacturasElectronicaLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaVentaElectronicaTO> listaEnviar = UtilsJSON.jsonToList(AnxListaVentaElectronicaTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> respuesta = envioCorreoService.enviarCorreoFacturasPorLote(listaEnviar, empresa, sisInfoTO);
            if (respuesta != null && respuesta.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarComprobantesElectronicos")
    public RespuestaWebTO listarComprobantesElectronicos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipoVista = UtilsJSON.jsonToObjeto(String.class, map.get("tipoVista"));//COMPRAS...default null
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxComprobantesElectronicosRecibidos> respuesta = anxComprobantesElectronicosRecibidosService.listarComprobantesElectronicos(empresa, periodo, tipoVista);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoGuiaElectronicaLote")
    public RespuestaWebTO enviarCorreoGuiaElectronicaLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaGuiaRemisionElectronicaTO> listaEnviar = UtilsJSON.jsonToList(AnxListaGuiaRemisionElectronicaTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> respuesta = envioCorreoService.enviarCorreoGuiasPorLote(listaEnviar, empresa);
            if (respuesta != null && respuesta.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("cambiarEstadoCancelado")
    public RespuestaWebTO cambiarEstadoCancelado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String clave = UtilsJSON.jsonToObjeto(String.class, map.get("clave"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        List<AnxComprobantesElectronicosRecibidos> listadoComprobantes = UtilsJSON.jsonToList(AnxComprobantesElectronicosRecibidos.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = anxComprobantesElectronicosRecibidosService.cambiarEstadoCancelado(empresa, periodo, clave, estado, tipo, listadoComprobantes, sisInfoTO);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al cambiar estado");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoRetencionesElectronicaLote")
    public RespuestaWebTO enviarCorreoRetencionesElectronicaLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListadoCompraElectronicaTO> listaEnviar = UtilsJSON.jsonToList(AnxListadoCompraElectronicaTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> respuesta = envioCorreoService.enviarCorreoRetencionesPorLote(listaEnviar, empresa, sisInfoTO);
            if (respuesta != null && respuesta.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarImportados")
    public RespuestaWebTO verificarImportados(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String resultado = anxComprobantesElectronicosRecibidosService.verificarImportados(empresa, periodo, sisInfoTO);
            if (resultado.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo("Es han actualizado " + resultado.substring(5, resultado.length()));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(resultado.substring(1, resultado.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/enviarCorreoLiquidacionesElectronicaLote")
    public RespuestaWebTO enviarCorreoLiquidacionesElectronicaLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaLiquidacionComprasElectronicaTO> listaEnviar = UtilsJSON.jsonToList(AnxListaLiquidacionComprasElectronicaTO.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> respuesta = envioCorreoService.enviarCorreoLiquidacionesPorLote(listaEnviar, empresa, sisInfoTO);
            if (respuesta != null && respuesta.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarExistenciaDocumentoAnxCompra")
    public RespuestaWebTO verificarExistenciaDocumentoAnxCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ComboGenericoTO> documentos = UtilsJSON.jsonToList(ComboGenericoTO.class, map.get("documentos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (documentos != null && !documentos.isEmpty()) {
                List<AnxCompraTO> documentosEncontrados = new ArrayList<>();
                String mensaje = "";
                for (int i = 0; i < documentos.size(); i++) {
                    AnxCompraTO retencion = compraService.getAnexoCompraTO(sisInfoTO.getEmpresa(), documentos.get(i).getClave());
                    if (retencion == null) {
                        mensaje = mensaje + "La retención con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> no existe.<br>";
                    } else {
                        documentosEncontrados.add(retencion);
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (mensaje != null && !mensaje.isEmpty()) {
                    resp.setOperacionMensaje("<strong>Las siguientes retenciones no existen en la base de datos actual: <br></strong>" + mensaje);
                } else {
                    //actualizamos
                    String mensajeActualizar = "";
                    String mensajeActualizarError = "";

                    for (int i = 0; i < documentosEncontrados.size(); i++) {
                        AnxCompraTO documentosEncontrado = documentosEncontrados.get(i);
                        AnxCompraPK pk = new AnxCompraPK();
                        pk.setCompEmpresa(documentosEncontrado.getEmpCodigo());
                        pk.setCompMotivo(documentosEncontrado.getMotCodigo());
                        pk.setCompNumero(documentosEncontrado.getCompNumero());
                        pk.setCompPeriodo(documentosEncontrado.getPerCodigo());

                        if (compraService.actualizarClaveExternaRetencion(
                                pk,
                                documentos.get(i).getValor(),
                                sisInfoTO)) {
                            mensajeActualizar = mensajeActualizar + "La retención con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> se pudó actualizar correctamente.<br>";
                        } else {
                            mensajeActualizarError = mensajeActualizarError + "La retención con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> no se pudó actualizar la clave externa.<br>";
                        }
                    }

                    if (mensajeActualizarError != null && !mensajeActualizarError.isEmpty()) {
                        resp.setOperacionMensaje("<strong>Las siguientes documentos no se lograron actualizar: <br></strong>" + mensajeActualizarError);
                    } else {
                        resp.setExtraInfo(documentosEncontrados);
                        resp.setOperacionMensaje("<strong>Las siguientes documentos se lograron actualizar: <br></strong>" + mensajeActualizar);
                    }
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarAnxFormulario104")
    public RespuestaWebTO insertarAnxFormulario104(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxFormulario104 anxFormulario104 = UtilsJSON.jsonToObjeto(AnxFormulario104.class, map.get("anxFormulario104"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String inicio = UtilsJSON.jsonToObjeto(String.class, map.get("inicio"));
        String fin = UtilsJSON.jsonToObjeto(String.class, map.get("fin"));
        try {
            AnxFormulario104 formulario104 = anxFormulario104Service.obtenerAnxFormulario104(anxFormulario104.getFrm104Empresa(), inicio, fin);
            if (formulario104 == null) {
                String respuesta = anxFormulario104Service.insertarAnxFormulario104(anxFormulario104, sisInfoTO);
                if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(anxFormulario104);
                    resp.setOperacionMensaje(respuesta.substring(1));
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(respuesta.substring(1));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.SIN_ACCESO.getValor());
                resp.setExtraInfo(formulario104.getFrm104Secuencial());
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarAnxFormulario104")
    public RespuestaWebTO modificarAnxFormulario104(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxFormulario104 anxFormulario104 = UtilsJSON.jsonToObjeto(AnxFormulario104.class, map.get("anxFormulario104"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = anxFormulario104Service.modificarAnxFormulario104(anxFormulario104, sisInfoTO);
            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(anxFormulario104);
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarAnxFormulario104")
    public RespuestaWebTO eliminarAnxFormulario104(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = anxFormulario104Service.eliminarAnxFormulario104(secuencial, sisInfoTO);
            if (respuesta.length() > 0 && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarComprobantesElectronicosRecibidosLote")
    public RespuestaWebTO insertarComprobantesElectronicosRecibidosLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<AnxComprobantesElectronicosRecibidos> listaEnviar = UtilsJSON.jsonToList(AnxComprobantesElectronicosRecibidos.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean estadoSRI = urlWebServicesService.verifcarDisponibilidadSRI();
            if (estadoSRI) {
                SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
                if (parametros != null) {
                    parametros.setParDocumentosRecibidosEnProceso(true);
                    empresaParametrosDao.actualizar(parametros);
                }
                comprobantesRecibidosService.insertarComprobantesElectronicosRecibidosLote(listaEnviar, sisInfoTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se enviará un mensaje de confirmación al correo: " + sisInfoTO.getEmail() + ", para poder realizar el siguiente envío de comprobantes.");
            } else {
                resp.setOperacionMensaje("No se ha realizado ninguna acción, debido a que no hemos obtenido respuesta del SRI. Intente nuevamente en unos minutos.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteXmlAutorizacionComprobantesLote")
    public @ResponseBody
    String generarReporteXmlAutorizacionComprobantesLote(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ComboGenericoTO> listaComprobantesElectronicos = UtilsJSON.jsonToList(ComboGenericoTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InputStream> respuesta;
        try {
            sisInfoTO.setEmpresa(usuarioEmpresaReporteTO.getEmpCodigo());
            respuesta = reporteComprobanteElectronicoService.generarReporteComprobanteElectronicoLote(listaComprobantesElectronicos, usuarioEmpresaReporteTO, sisInfoTO);
            return archivoService.generaReportePDFMultiple(respuesta, response, sisInfoTO.getEmpresa());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerDatosParaComprobantesElectronicosRecibidos")
    public RespuestaWebTO obtenerDatosParaComprobantesElectronicosRecibidos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprobanteElectronicoService.obtenerDatosParaComprobantesElectronicosRecibidos(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteDiferenciasTributacion")
    public @ResponseBody
    String generarReporteDiferenciasTributacion(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "ReporteDiferenciasTributacion.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxDiferenciasTributacionTO> listado = UtilsJSON.jsonToList(AnxDiferenciasTributacionTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteAnexosService.generarReporteDiferenciasTributacion(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDiferenciasTributacionMatricial")
    public RespuestaWebTO generarReporteDiferenciasTributacionMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxDiferenciasTributacionTO> listado = UtilsJSON.jsonToList(AnxDiferenciasTributacionTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteAnexosService.generarReporteDiferenciasTributacion(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteDiferenciasTributacion")
    public @ResponseBody
    String exportarReporteDiferenciasTributacion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarReporteDiferenciasTributacion(map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/listarAnxHomologacionProducto")
    public RespuestaWebTO listarAnxHomologacionProducto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String provIdentificacion = UtilsJSON.jsonToObjeto(String.class, map.get("provIdentificacion"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxHomologacionProducto> respuesta = anxHomologacionProductoService.listarAnxHomologacionProducto(empresa, provIdentificacion, busqueda, incluirTodos);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarAnxHomologacionProducto")
    public RespuestaWebTO modificarAnxHomologacionProducto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<AnxHomologacionProducto> anxHomologacionProducto = UtilsJSON.jsonToList(AnxHomologacionProducto.class, map.get("anxHomologacionProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            for (AnxHomologacionProducto homolgacionProducto : anxHomologacionProducto) {
                String respuesta = anxHomologacionProductoService.modificarAnxHomologacionProducto(homolgacionProducto, sisInfoTO);
                if (respuesta != null && respuesta.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respuesta);
                    resp.setOperacionMensaje(respuesta.substring(1));
                } else {
                    resp.setOperacionMensaje(respuesta.substring(1));
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarAnxRetencionesVentaPendientesImportar")
    public RespuestaWebTO listarAnxRetencionesVentaPendientesImportar(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String cliIdentificacion = SistemaWebUtil.obtenerFiltroComoString(parametros, "cliIdentificacion");
        String busqueda = SistemaWebUtil.obtenerFiltroComoString(parametros, "busqueda");
        try {
            List<AnxRetencionesVenta> respues = anxRetencionesVentaService.listarAnxRetencionesVentaPendientesImportar(empresa, cliIdentificacion, busqueda);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarAnxRetencionesVentaPendientesImportarSinAnexoVenta")
    public RespuestaWebTO listarAnxRetencionesVentaPendientesImportarSinAnexoVenta(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            List<AnxRetencionesVenta> respues = anxRetencionesVentaService.listarAnxRetencionesVentaPendientesImportarSinAnexoVenta(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarComprobantesElectronicosEmitidosLote")
    public RespuestaWebTO insertarComprobantesElectronicosEmitidosLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<AnxComprobantesElectronicosEmitidos> listaEnviar = UtilsJSON.jsonToList(AnxComprobantesElectronicosEmitidos.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();

        try {
            String respuesta = anxComprobantesElectronicosEmitidosService.insertarComprobantesElectronicosLote(listaEnviar, sisInfoTO);
            listaMensajes = respuesta.split("\\|");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals("")) {
                    listaMensajesEnviar.add(listaMensajes[i].substring(0));
                }
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(listaMensajesEnviar);

        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarComprobantesElectronicosEmitidos")
    public RespuestaWebTO listarComprobantesElectronicosEmitidos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<AnxComprobantesElectronicosEmitidos> respuesta = anxComprobantesElectronicosEmitidosService.listarComprobantesElectronicosEmitidos(empresa, periodo);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosProductosDeXML")
    public RespuestaWebTO obtenerDatosProductosDeXML(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = anxComprobantesElectronicosEmitidosService.obtenerDatosProductosDeXML(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaComprobantesElectronicosEmitidos")
    public RespuestaWebTO obtenerDatosParaComprobantesElectronicosEmitidos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = anxComprobantesElectronicosEmitidosService.obtenerDatosParaComprobantesElectronicosEmitidos(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarVentasLoteDeComprobantesEmitidos")
    public RespuestaWebTO generarVentasLoteDeComprobantesEmitidos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<AnxComprobantesElectronicosEmitidos> listaEnviar = UtilsJSON.jsonToList(AnxComprobantesElectronicosEmitidos.class, map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String bodega = SistemaWebUtil.obtenerFiltroComoString(map, "bodega");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(map, "motivo");
        String precioVenta = SistemaWebUtil.obtenerFiltroComoString(map, "precioVenta");
        Integer formaCobro = SistemaWebUtil.obtenerFiltroComoInteger(map, "fcSecuencial");
        String codigoProducto = SistemaWebUtil.obtenerFiltroComoString(map, "codigoProducto");
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();
        ArrayList<String> listaMensajesEnviarConError = new ArrayList<>();
        ArrayList<String> listaClavesIngresadas = new ArrayList<>();
        try {
            Map<String, Object> respuesta = anxComprobantesElectronicosEmitidosService.generarComprobantesEmitidos(listaEnviar, bodega, motivo, precioVenta, formaCobro, codigoProducto, sisInfoTO);
            if (respuesta != null) {
                Map<String, Object> respuestaEnviar = new HashMap<>();
                String mensaje = (String) respuesta.get("mensaje");//String  concatenado  resp1|resp2|resp3....  T|FNo se...|FError...|T
                listaMensajes = mensaje.split("\\|");
                for (int i = 0; i < listaMensajes.length; i++) {
                    //obtener solo mensajes con error
                    if (listaMensajes[i] != null && listaMensajes[i].substring(0, 1).equals("F")) {
                        listaMensajesEnviarConError.add(listaMensajes[i].substring(0));
                    }
                }
                //INSERTAR VENTAS POR LOTE
                List<InvVentaConDetalle> listadoRespuesta = (List<InvVentaConDetalle>) respuesta.get("listadoRespuesta");
                String mensajeRespuesta = "";//mensaje respuesta concatenado TSe insert...|FError....|....
                if (listadoRespuesta != null && listadoRespuesta.size() > 0) {
                    for (InvVentaConDetalle item : listadoRespuesta) {
                        String mensajeAux = "T";
                        try {
                            MensajeTO mensajeTO = ventasService.insertarInventarioVentasTO(
                                    item.getInvVentasTO(),
                                    item.getListaInvVentasDetalleTO(),
                                    null,
                                    item.getTipoDocumentoComplemento() != null ? item.getTipoDocumentoComplemento() : "",
                                    item.getNumeroDocumentoComplemento() != null ? item.getNumeroDocumentoComplemento() : "",
                                    item.getMotivoDocumentoComplemento() != null ? item.getMotivoDocumentoComplemento() : "",
                                    true,
                                    null,
                                    null,
                                    null,
                                    new ArrayList<>(),
                                    new ArrayList<>(),
                                    sisInfoTO);
                            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                                ventasService.quitarPendiente(item.getInvVentasTO());
                                //*****************INSERTAR ANX VENTAS ELECTRONICA*********************************************
                                AnxVentaElectronicaTO anxVentaElectronicaTO = new AnxVentaElectronicaTO();
                                anxVentaElectronicaTO.seteSecuencial(0);
                                anxVentaElectronicaTO.seteTipoAmbiente("PRODUCCION");
                                anxVentaElectronicaTO.seteEstado("AUTORIZADO");
                                anxVentaElectronicaTO.seteClaveAcceso(item.getClaveAcceso());
                                anxVentaElectronicaTO.seteAutorizacionNumero(item.getClaveAcceso());
                                anxVentaElectronicaTO.seteAutorizacionFecha(UtilsValidacion.fecha(item.getInvVentasTO().getVtaFecha(), "yyyyMM-dd", "yyyy-MM-dd HH:mm:ss"));
                                anxVentaElectronicaTO.seteXml(item.getComproXml());
                                anxVentaElectronicaTO.seteEnviadoPorCorreo(false);
                                anxVentaElectronicaTO.setUsrEmpresa(sisInfoTO.getEmpresa());
                                anxVentaElectronicaTO.setUsrCodigo(item.getInvVentasTO().getUsrCodigo());
                                anxVentaElectronicaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                                anxVentaElectronicaTO.setVtaEmpresa(item.getInvVentasTO().getVtaEmpresa());
                                anxVentaElectronicaTO.setVtaPeriodo(item.getInvVentasTO().getVtaPeriodo());
                                anxVentaElectronicaTO.setVtaMotivo(item.getInvVentasTO().getVtaMotivo());
                                anxVentaElectronicaTO.setVtaNumero(item.getInvVentasTO().getVtaNumero());
                                anxVentaElectronicaTO.setVtaFecha(item.getInvVentasTO().getVtaFecha());
                                String mensajeVentaElectronica = ventaElectronicaService.accionAnxVentaElectronica(anxVentaElectronicaTO, 'I', sisInfoTO);
                                //CONTABILIZAR
                                mensajeAux = mensajeTO.getMensaje();//correcto SE CREO VENTA
                                if (mensajeVentaElectronica != null && mensajeVentaElectronica.substring(0, 1).equals("T")) {
                                    MensajeTO contabilizar = ventasService.insertarInvContableVentasTO(item.getInvVentasTO().getVtaEmpresa(),
                                            item.getInvVentasTO().getVtaPeriodo(), item.getInvVentasTO().getVtaMotivo(), item.getInvVentasTO().getVtaNumero(),
                                            sisInfoTO.getUsuario(), false, null, null, sisInfoTO);
                                    if (contabilizar != null && contabilizar.getMensaje().charAt(0) == 'T') {
                                        mensajeAux += " y se contabilizó correctamente.";
                                    } else {
                                        mensajeAux += " pero no se logró contabilizar. Por los siguientes motivos:";
                                        if (contabilizar.getListaErrores1() != null) {
                                            //setear errores 
                                            for (String error : contabilizar.getListaErrores1()) {
                                                mensajeAux = mensajeAux + "<br>" + error.replace('|', ' ');
                                            }
                                        }
                                    }
                                }
                                //**************************************************************************
                                listaClavesIngresadas.add(item.getClaveAcceso());
                            } else {
                                if (mensajeTO != null && mensajeTO.getMensaje() != null) {
                                    mensajeAux = mensajeTO.getMensaje();
                                    if (mensajeTO.getListaErrores1() != null) {
                                        //setear errores 
                                        for (String error : mensajeTO.getListaErrores1()) {
                                            mensajeAux = mensajeAux + "<br>" + error.replace('|', ' ');
                                        }
                                    }
                                } else {
                                    if (mensajeTO.getListaErrores1() != null) {
                                        //setear errores 
                                        for (String error : mensajeTO.getListaErrores1()) {
                                            mensajeAux = mensajeAux + "<br>" + error.replace('|', ' ');
                                        }
                                    } else {
                                        String tipo = item.getInvVentasTO().getVtaDocumentoTipo().equals("18") ? "Factura"
                                                : item.getInvVentasTO().getVtaDocumentoTipo().equals("04") ? "Nota Crédito" : "Nota Débito";
                                        mensajeAux
                                                = "F" + tipo + " --> "
                                                + "(" + item.getInvVentasTO().getVtaDocumentoNumero() + ") "
                                                + "El comprobante electrónico con clave de acceso :" + item.getClaveAcceso() + ", no se logró guardar.";
                                    }
                                }
                            }
                            mensajeRespuesta += mensajeAux + "|";
                        } catch (GeneralException e) {
                            throw new GeneralException(e.getMessage() != null ? "F" + e.getMessage() : null);
                        } catch (Exception e) {
                            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                            throw new GeneralException(e.getMessage() != null ? "F" + e.getMessage() : null);
                        }
                    }
                }
                //insertamos errores si hubiese
                if (listaMensajesEnviarConError != null && listaMensajesEnviarConError.size() > 0) {
                    listaMensajesEnviar = listaMensajesEnviarConError;
                }
                //insertamos mensajes concatenados con | de error o correcto de ventas insertadas
                listaMensajes = mensajeRespuesta.split("\\|");
                for (int i = 0; i < listaMensajes.length; i++) {
                    if (!listaMensajes[i].equals("")) {
                        listaMensajesEnviar.add(listaMensajes[i].substring(0));
                    }
                }

                respuestaEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
                respuestaEnviar.put("listaClavesIngresadas", listaClavesIngresadas);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuestaEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("generarReporteComprobantesRecibidosMatricial")
    public RespuestaWebTO generarReporteComprobantesRecibidosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        List<AnxComprobantesElectronicosRecibidos> listadoComprobantesRecibidos = UtilsJSON.jsonToList(AnxComprobantesElectronicosRecibidos.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        try {
            respuesta = anxComprobantesElectronicosRecibidosService.generarReporteComprobantesRecibidos(usuarioEmpresaReporteTO, listadoComprobantesRecibidos, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("generarReporteComprobantesRecibidos")
    public RespuestaWebTO generarReporteComprobantesRecibidos(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        List<AnxComprobantesElectronicosRecibidos> listadoComprobantesRecibidos = UtilsJSON.jsonToList(AnxComprobantesElectronicosRecibidos.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        try {
            respuesta = anxComprobantesElectronicosRecibidosService.generarReporteComprobantesRecibidos(usuarioEmpresaReporteTO, listadoComprobantesRecibidos, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRetencionVentaMatricial")
    public RespuestaWebTO generarReporteRetencionVentaMatricial(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRetencionesVentas.jrxml";
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxRetencionVentaReporteTO> listaRetencionVenta = UtilsJSON.jsonToList(AnxRetencionVentaReporteTO.class, parametros.get("retencionVentaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRetencionVenta(usuarioEmpresaReporteTO, listaRetencionVenta, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteRetencionVenta")
    public String generarReporteRetencionVenta(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportRetencionesVentas.jrxml";
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<AnxRetencionVentaReporteTO> listaRetencionVenta = UtilsJSON.jsonToList(AnxRetencionVentaReporteTO.class, parametros.get("retencionVentaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.generarReporteRetencionVenta(usuarioEmpresaReporteTO, listaRetencionVenta, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarClientesRezagadosLote")
    public RespuestaWebTO insertarClientesRezagadosLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<AnxComprobantesElectronicosEmitidos> listaEnviar = UtilsJSON.jsonToList(AnxComprobantesElectronicosEmitidos.class, map.get("listaEnviar"));//identificacion y razon social
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();

        try {
            Map<String, Object> respuesta = anxComprobantesElectronicosEmitidosService.insertarClientesRezagadosLote(listaEnviar, sisInfoTO);
            if (respuesta != null) {
                Map<String, Object> respuestaEnviar = new HashMap<>();
                String mensaje = (String) respuesta.get("mensaje");//String  concatenado  resp1|resp2|resp3....  T|FNo se...|FError...|T
                List<InvClienteTO> listaClientesInsertado = (List<InvClienteTO>) respuesta.get("listaClientesInsertado");
                listaMensajes = mensaje.split("\\|");
                for (int i = 0; i < listaMensajes.length; i++) {
                    if (!listaMensajes[i].equals("")) {
                        listaMensajesEnviar.add(listaMensajes[i].substring(0));
                    }
                }
                respuestaEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
                respuestaEnviar.put("listaClientesInsertado", listaClientesInsertado);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuestaEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
                resp.setExtraInfo(null);
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarComprobantesEmitidosLote")
    public RespuestaWebTO insertarComprobantesEmitidosLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ComprobanteImportarTO> listaEnviar = UtilsJSON.jsonToList(ComprobanteImportarTO.class, map.get("listaComprobantes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();

        try {
            String respuesta = anxComprobantesElectronicosEmitidosService.insertarComprobantesEmitidosLote(listaEnviar, sisInfoTO);
            listaMensajes = respuesta.split("\\|");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals("")) {
                    listaMensajesEnviar.add(listaMensajes[i].substring(0));
                }
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(listaMensajesEnviar);

        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarComprobantesRecibidosLote")
    public RespuestaWebTO insertarComprobantesRecibidosLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ComprobanteImportarTO> listaEnviar = UtilsJSON.jsonToList(ComprobanteImportarTO.class, map.get("listaComprobantes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();

        try {
            String respuesta = anxComprobantesElectronicosRecibidosService.insertarComprobantesRecibidosLote(listaEnviar, sisInfoTO);
            String[] listaMensajes = respuesta.split("\\|");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals("")) {
                    listaMensajesEnviar.add(listaMensajes[i].substring(0));
                }
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(listaMensajesEnviar);

        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarListadoCatastro")
    public RespuestaWebTO insertarListadoCatastro(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<AnxCatastroMicroempresa> listaEnviar = UtilsJSON.jsonToList(AnxCatastroMicroempresa.class, map.get("listadoCatastro"));
        boolean permitirBorrar = UtilsJSON.jsonToObjeto(boolean.class, map.get("permitirBorrar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = anexoCatastroMicroempresaService.insertarListadoCatastroMicroempresa(listaEnviar, permitirBorrar);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion("Ocurrio un problema al guardar los regsitros. Contacte con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularRetencionesComprobantes")
    public RespuestaWebTO anularRetencionesComprobantes(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        List<AnxRetencionesVenta> listado = UtilsJSON.jsonToList(AnxRetencionesVenta.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            String respuesta = anxRetencionesVentaService.anularRetencionesComprobantes(empresa, listado, sisInfoTO);
            if (respuesta.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1, respuesta.length()));
            } else {
                resp.setOperacionMensaje(respuesta.substring(1, respuesta.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarRetencionesComprobantesVenta")
    public @ResponseBody
    String exportarRetencionesComprobantesVenta(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AnxRetencionesVenta> listado = UtilsJSON.jsonToList(AnxRetencionesVenta.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarRetencionesComprobantesVenta(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarComprobantesRecibidos")
    public @ResponseBody
    String exportarComprobantesRecibidos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<AnxComprobantesElectronicosRecibidos> listado = UtilsJSON.jsonToList(AnxComprobantesElectronicosRecibidos.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteAnexosService.exportarComprobantesRecibidos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

}
