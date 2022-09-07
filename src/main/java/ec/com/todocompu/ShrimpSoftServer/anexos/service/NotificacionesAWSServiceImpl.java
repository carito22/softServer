package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.VerificacionNotificacionesDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosOrdenCompraService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.UtilsMail;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNotificacionAWSTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronicaNotificaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConVerificacionErroresNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.enums.TipoNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolPagoNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisNotificacion;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NotificacionesAWSServiceImpl implements NotificacionesAWSService {

    @Autowired
    private GenericoDao<AnxLiquidacionComprasElectronicaNotificaciones, Integer> anxLiquidacionComprasElectronicaNotificacionesDao;
    @Autowired
    private GenericoDao<AnxGuiaRemisionElectronicaNotificaciones, Integer> anxGuiaRemisionElectronicaNotificacionesDao;
    @Autowired
    private GenericoDao<AnxVentaElectronicaNotificaciones, Integer> anxventaElectronicaNotificacionesDao;
    @Autowired
    private GenericoDao<InvPedidosOrdenCompraNotificaciones, Integer> invPedidosOrdenCompraNotificacionesDao;
    @Autowired
    private GenericoDao<AnxCompraElectronicaNotificaciones, Integer> anxcompraElectronicaNotificacionesDao;
    @Autowired
    private GenericoDao<InvClienteNotificaciones, Integer> invClienteNotificacionesDao;
    @Autowired
    private GenericoDao<RhRolPagoNotificaciones, Integer> rhRolPagoNotificacionesDao;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private VentasService ventasService;
    @Autowired
    private ComprasService comprasService;
    @Autowired
    private PedidosOrdenCompraService pedidosOrdenCompraService;
    @Autowired
    private GuiaElectronicaService guiaElectronicaService;
    @Autowired
    private LiquidacionCompraElectronicaService liquidacionCompraElectronicaService;
    @Autowired
    private VerificacionNotificacionesDao verificacionNotificacionesDao;
    @Autowired
    private ClienteService clienteService;

    @Override
    public MensajeTO insertarNotificacion(String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        //confrmacion
        String confirmacion = UtilsJSON.jsonToObjeto(String.class, map.get("SubscribeURL"));
        if (confirmacion != null) {
            System.out.println("SubscribeURL:\n\n" + confirmacion + "\n\n");
        }
        //extraemos mensaje para procesar la insercion
        Map<String, Object> mensaje = UtilsJSON.jsonToMap(UtilsJSON.jsonToObjeto(String.class, map.get("Message")));
        if (mensaje != null) {
            String tipoEvento = (String) mensaje.get("eventType");
            Map<String, Object> mail = (Map<String, Object>) mensaje.get("mail");
            if (mail != null) {
                Map<String, Object> tags = (Map<String, Object>) mail.get("tags");
                String claveAcceso = tags == null ? null : (tags.get("ows-clave-acceso") == null ? null : tags.get("ows-clave-acceso").toString());
                String tipoNotificacion = "";
                if (tags != null) {
                    ArrayList<String> tipoNotificaciones = tags.get("ows-tipo-notificacion") == null ? new ArrayList<>() : (ArrayList<String>) tags.get("ows-tipo-notificacion");
                    if (tipoNotificaciones != null && !tipoNotificaciones.isEmpty()) {
                        tipoNotificacion = tipoNotificaciones.get(0);
                    }
                }
                if (TipoNotificacion.getTipoNotificacion(tipoNotificacion) != null) {
                    switch (TipoNotificacion.getTipoNotificacion(tipoNotificacion)) {
                        case NOTIFICAR_PROVEEDOR_ORDEN_COMPRA:
                            insertarNotificacionesOrdenCompra(tipoEvento, json, tags, mail, mensaje);
                            break;
                        case NOTIFICAR_CUENTAS_POR_COBRAR:
                            insertarNotificacionesCuentasCobrar(tipoEvento, json, tags, mail, mensaje);
                            break;
                        case NOTIFICAR_CONTABLE_ERRORES:
                            insertarNotificacionesVerificacionErrores(tipoEvento, json, tags, mail, mensaje);
                            break;
                        case NOTIFICAR_ROL_PAGOS:
                            insertarNotificacionesRolDePago(tipoEvento, json, tags, mail, mensaje);
                            break;
                        default:
                            break;
                    }
                } else {
                    if (claveAcceso == null) {

                    } else {
                        if (claveAcceso.substring(9, 11).equalsIgnoreCase("07")) {
                            insertarNotificacionesCompra(tipoEvento, json, tags, mail, mensaje);
                        } else if (claveAcceso.substring(9, 11).equalsIgnoreCase("03")) {
                            insertarNotificacionesLiquidacionCompra(tipoEvento, json, tags, mail, mensaje);
                        } else if (claveAcceso.substring(9, 11).equalsIgnoreCase("06")) {
                            insertarNotificacionesGuiaRemision(tipoEvento, json, tags, mail, mensaje);
                        } else {
                            insertarNotificacionesVenta(tipoEvento, json, tags, mail, mensaje);
                        }
                    }
                }
            }
        }
        return null;
    }

    private void insertarNotificacionesVenta(String tipoEvento, String json, Map<String, Object> tags, Map<String, Object> mail, Map<String, Object> mensaje) throws Exception {
        AnxVentaElectronicaNotificaciones notificacionVenta = new AnxVentaElectronicaNotificaciones();
        String empresa = obtenerAtributo((ArrayList<Object>) tags.get("ows-empresa"));
        String periodo = obtenerAtributo((ArrayList<Object>) tags.get("ows-periodo"));
        String motivo = obtenerAtributo((ArrayList<Object>) tags.get("ows-motivo"));
        String numero = obtenerAtributo((ArrayList<Object>) tags.get("ows-numero"));

        notificacionVenta.setETipo(tipoEvento);
        notificacionVenta.setVtaEmpresa(empresa);
        notificacionVenta.setVtaPeriodo(periodo);
        notificacionVenta.setVtaMotivo(motivo);
        notificacionVenta.setVtaNumero(numero);
        notificacionVenta.setEInforme(json);

        Map<String, Object> commonheaders = (Map<String, Object>) mail.get("commonHeaders");

        switch (tipoEvento) {
            case "Send":
                notificacionVenta.setEDestinatario(commonheaders.get("to").toString());
                String f = (String) mail.get("timestamp");
                notificacionVenta.setEFecha(UtilsDate.timestamp(f));
                anxventaElectronicaNotificacionesDao.insertar(notificacionVenta);
                break;
            case "Delivery":
                Map<String, Object> delivery = (Map<String, Object>) mensaje.get("delivery");
                notificacionVenta.setEDestinatario(delivery.get("recipients").toString());
                String f2 = (String) delivery.get("timestamp");
                notificacionVenta.setEFecha(UtilsDate.timestamp(f2));
                anxventaElectronicaNotificacionesDao.insertar(notificacionVenta);
                break;
            case "Open":
                Map<String, Object> open = (Map<String, Object>) mensaje.get("open");
                notificacionVenta.setEDestinatario(commonheaders.get("to").toString());
                String f3 = (String) open.get("timestamp");
                notificacionVenta.setEFecha(UtilsDate.timestamp(f3));
                anxventaElectronicaNotificacionesDao.insertar(notificacionVenta);
                break;
            case "Bounce":
                Map<String, Object> bounce = (Map<String, Object>) mensaje.get("bounce");
                if (bounce != null) {
                    List<Map<String, Object>> bouncedRecipients = (List<Map<String, Object>>) bounce.get("bouncedRecipients");
                    ArrayList<String> emisores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (bouncedRecipients != null && !bouncedRecipients.isEmpty()) {
                        for (Map<String, Object> bouncedRecipient : bouncedRecipients) {
                            AnxVentaElectronicaNotificaciones notificacion = new AnxVentaElectronicaNotificaciones();
                            notificacion.setVtaEmpresa(empresa);
                            notificacion.setVtaPeriodo(periodo);
                            notificacion.setVtaMotivo(motivo);
                            notificacion.setVtaNumero(numero);
                            notificacion.setEInforme(json);
                            notificacion.setETipo(tipoEvento + bounce.get("bounceType").toString());
                            String f4 = (String) bounce.get("timestamp");
                            notificacion.setEFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = bouncedRecipient;
                            notificacion.setEDestinatario(recipiente.get("emailAddress").toString());
                            notificacion.setEObservacion(recipiente.get("diagnosticCode").toString());
                            anxventaElectronicaNotificacionesDao.insertar(notificacion);
                            notificarAEmpresaPorVenta(emisores, notificacion);
                        }
                    }
                }
                break;
            case "Complaint":
                Map<String, Object> complaint = (Map<String, Object>) mensaje.get("complaint");
                if (complaint != null) {
                    List<Map<String, Object>> complaintRecipients = (List<Map<String, Object>>) complaint.get("complainedRecipients");
                    ArrayList<String> emisores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (complaintRecipients != null && !complaintRecipients.isEmpty()) {
                        for (Map<String, Object> complaintRecipient : complaintRecipients) {
                            AnxVentaElectronicaNotificaciones notificacion = new AnxVentaElectronicaNotificaciones();
                            notificacion.setVtaEmpresa(empresa);
                            notificacion.setVtaPeriodo(periodo);
                            notificacion.setVtaMotivo(motivo);
                            notificacion.setVtaNumero(numero);
                            notificacion.setEInforme(json);
                            notificacion.setETipo(tipoEvento);
                            String f4 = (String) complaint.get("timestamp");
                            notificacion.setEFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = complaintRecipient;
                            notificacion.setEDestinatario(recipiente.get("emailAddress").toString());
                            anxventaElectronicaNotificacionesDao.insertar(notificacion);
                            notificarAEmpresaPorVenta(emisores, notificacion);
                        }
                    }
                }
                break;
        }
    }

    private void insertarNotificacionesCompra(String tipoEvento, String json, Map<String, Object> tags, Map<String, Object> mail, Map<String, Object> mensaje) throws Exception {
        AnxCompraElectronicaNotificaciones notificacion = new AnxCompraElectronicaNotificaciones();

        String empresa = obtenerAtributo((ArrayList<Object>) tags.get("ows-empresa"));
        String periodo = obtenerAtributo((ArrayList<Object>) tags.get("ows-periodo"));
        String motivo = obtenerAtributo((ArrayList<Object>) tags.get("ows-motivo"));
        String numero = obtenerAtributo((ArrayList<Object>) tags.get("ows-numero"));

        notificacion.setETipo(tipoEvento);
        notificacion.setCompEmpresa(empresa);
        notificacion.setCompPeriodo(periodo);
        notificacion.setCompMotivo(motivo);
        notificacion.setCompNumero(numero);
        notificacion.setEInforme(json);

        Map<String, Object> commonheaders = (Map<String, Object>) mail.get("commonHeaders");

        switch (tipoEvento) {
            case "Send":
                notificacion.setEDestinatario(commonheaders.get("to").toString());
                String f = (String) mail.get("timestamp");
                notificacion.setEFecha(UtilsDate.timestamp(f));
                anxcompraElectronicaNotificacionesDao.insertar(notificacion);
                break;
            case "Delivery":
                Map<String, Object> delivery = (Map<String, Object>) mensaje.get("delivery");
                notificacion.setEDestinatario(delivery.get("recipients").toString());
                String f2 = (String) delivery.get("timestamp");
                notificacion.setEFecha(UtilsDate.timestamp(f2));
                anxcompraElectronicaNotificacionesDao.insertar(notificacion);
                break;
            case "Open":
                Map<String, Object> open = (Map<String, Object>) mensaje.get("open");
                notificacion.setEDestinatario(commonheaders.get("to").toString());
                String f3 = (String) open.get("timestamp");
                notificacion.setEFecha(UtilsDate.timestamp(f3));
                anxcompraElectronicaNotificacionesDao.insertar(notificacion);
                break;
            case "Bounce":
                Map<String, Object> bounce = (Map<String, Object>) mensaje.get("bounce");
                if (bounce != null) {
                    List<Map<String, Object>> bouncedRecipients = (List<Map<String, Object>>) bounce.get("bouncedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (bouncedRecipients != null && !bouncedRecipients.isEmpty()) {
                        for (Map<String, Object> bouncedRecipient : bouncedRecipients) {
                            AnxCompraElectronicaNotificaciones notificaciones = new AnxCompraElectronicaNotificaciones();
                            notificaciones.setCompEmpresa(empresa);
                            notificaciones.setCompPeriodo(periodo);
                            notificaciones.setCompMotivo(motivo);
                            notificaciones.setCompNumero(numero);
                            notificaciones.setEInforme(json);
                            notificaciones.setETipo(tipoEvento + bounce.get("bounceType").toString());
                            String f4 = (String) bounce.get("timestamp");
                            notificaciones.setEFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = bouncedRecipient;
                            notificaciones.setEDestinatario(recipiente.get("emailAddress").toString());
                            notificaciones.setEObservacion(recipiente.get("diagnosticCode").toString());
                            anxcompraElectronicaNotificacionesDao.insertar(notificaciones);
                            notificarAEmpresaPorCompra(receptores, notificaciones);
                        }
                    }
                }
                break;
            case "Complaint":
                Map<String, Object> complaint = (Map<String, Object>) mensaje.get("complaint");
                if (complaint != null) {
                    List<Map<String, Object>> complaintRecipients = (List<Map<String, Object>>) complaint.get("complainedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (complaintRecipients != null && !complaintRecipients.isEmpty()) {
                        for (Map<String, Object> complaintRecipient : complaintRecipients) {
                            AnxCompraElectronicaNotificaciones notificaciones = new AnxCompraElectronicaNotificaciones();
                            notificaciones.setCompEmpresa(empresa);
                            notificaciones.setCompPeriodo(periodo);
                            notificaciones.setCompMotivo(motivo);
                            notificaciones.setCompNumero(numero);
                            notificaciones.setEInforme(json);
                            notificaciones.setETipo(tipoEvento);
                            String f4 = (String) complaint.get("timestamp");
                            notificaciones.setEFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = complaintRecipient;
                            notificaciones.setEDestinatario(recipiente.get("emailAddress").toString());
                            anxcompraElectronicaNotificacionesDao.insertar(notificaciones);
                            notificarAEmpresaPorCompra(receptores, notificaciones);
                        }
                    }
                }
                break;
        }
    }

    private void insertarNotificacionesCuentasCobrar(String tipoEvento, String json, Map<String, Object> tags, Map<String, Object> mail, Map<String, Object> mensaje) throws Exception {
        InvClienteNotificaciones notificacion = new InvClienteNotificaciones();

        String empresa = obtenerAtributo((ArrayList<Object>) tags.get("ows-empresa"));
        String codigo = obtenerAtributo((ArrayList<Object>) tags.get("ows-codigo-cliente"));
        String documento = obtenerAtributo((ArrayList<Object>) tags.get("ows-documento"));

        documento = documento.equals("0") ? null : documento;

        notificacion.seteTipo(tipoEvento);
        notificacion.setCliEmpresa(empresa);
        notificacion.setCliCodigo(codigo);
        notificacion.seteInforme(json);
        notificacion.setMotivo("CUENTAS POR COBRAR");

        Map<String, Object> commonheaders = (Map<String, Object>) mail.get("commonHeaders");
        String asunto = commonheaders.get("subject").toString();
        notificacion.setAsunto(asunto);

        switch (tipoEvento) {
            case "Send":
                notificacion.seteDestinatario(commonheaders.get("to").toString());
                String f = (String) mail.get("timestamp");
                notificacion.seteFecha(UtilsDate.timestamp(f));
                invClienteNotificacionesDao.insertar(notificacion);
                break;
            case "Delivery":
                Map<String, Object> delivery = (Map<String, Object>) mensaje.get("delivery");
                notificacion.seteDestinatario(delivery.get("recipients").toString());
                String f2 = (String) delivery.get("timestamp");
                notificacion.seteFecha(UtilsDate.timestamp(f2));
                invClienteNotificacionesDao.insertar(notificacion);
                break;
            case "Open":
                Map<String, Object> open = (Map<String, Object>) mensaje.get("open");
                notificacion.seteDestinatario(commonheaders.get("to").toString());
                String f3 = (String) open.get("timestamp");
                notificacion.seteFecha(UtilsDate.timestamp(f3));
                invClienteNotificacionesDao.insertar(notificacion);
                break;
            case "Bounce":
                Map<String, Object> bounce = (Map<String, Object>) mensaje.get("bounce");
                if (bounce != null) {
                    List<Map<String, Object>> bouncedRecipients = (List<Map<String, Object>>) bounce.get("bouncedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (bouncedRecipients != null && !bouncedRecipients.isEmpty()) {
                        for (Map<String, Object> bouncedRecipient : bouncedRecipients) {
                            InvClienteNotificaciones notificaciones = new InvClienteNotificaciones();
                            notificaciones.setCliEmpresa(empresa);
                            notificaciones.setCliCodigo(codigo);
                            notificaciones.setAsunto(asunto);
                            notificaciones.seteInforme(json);
                            notificaciones.seteTipo(tipoEvento + bounce.get("bounceType").toString());
                            String f4 = (String) bounce.get("timestamp");
                            notificaciones.seteFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = bouncedRecipient;
                            notificaciones.seteDestinatario(recipiente.get("emailAddress").toString());
                            notificaciones.seteObservacion(recipiente.get("diagnosticCode").toString());
                            invClienteNotificacionesDao.insertar(notificaciones);
                            notificarAEmpresaPorCuentasPorCobrar(receptores, notificaciones, documento);
                        }
                    }
                }
                break;
            case "Complaint":
                Map<String, Object> complaint = (Map<String, Object>) mensaje.get("complaint");
                if (complaint != null) {
                    List<Map<String, Object>> complaintRecipients = (List<Map<String, Object>>) complaint.get("complainedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (complaintRecipients != null && !complaintRecipients.isEmpty()) {
                        for (Map<String, Object> complaintRecipient : complaintRecipients) {
                            InvClienteNotificaciones notificaciones = new InvClienteNotificaciones();
                            notificaciones.setCliEmpresa(empresa);
                            notificaciones.setCliCodigo(codigo);
                            notificaciones.setAsunto(asunto);
                            notificaciones.seteInforme(json);
                            notificaciones.seteTipo(tipoEvento);
                            String f4 = (String) complaint.get("timestamp");
                            notificaciones.seteFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = complaintRecipient;
                            notificaciones.seteDestinatario(recipiente.get("emailAddress").toString());
                            invClienteNotificacionesDao.insertar(notificaciones);
                            notificarAEmpresaPorCuentasPorCobrar(receptores, notificaciones, documento);
                        }
                    }
                }
                break;
        }
    }

    private void insertarNotificacionesVerificacionErrores(String tipoEvento, String json, Map<String, Object> tags, Map<String, Object> mail, Map<String, Object> mensaje) throws Exception {
        ConVerificacionErroresNotificaciones notificacion = new ConVerificacionErroresNotificaciones();

        String empresa = obtenerAtributo((ArrayList<Object>) tags.get("ows-empresa"));
        String numero = obtenerAtributo((ArrayList<Object>) tags.get("ows-numero"));

        notificacion.setVenTipo(tipoEvento);
        notificacion.setVenEmpresa(empresa);
        notificacion.setVenSecuencial(Integer.parseInt(numero));
        notificacion.setVenInforme(json);

        Map<String, Object> commonheaders = (Map<String, Object>) mail.get("commonHeaders");

        switch (tipoEvento) {
            case "Send":
                notificacion.setVenDestinatario(commonheaders.get("to").toString());
                String f = (String) mail.get("timestamp");
                notificacion.setVenFecha(UtilsDate.timestamp(f));
                verificacionNotificacionesDao.insertar(notificacion);
                break;
            case "Delivery":
                Map<String, Object> delivery = (Map<String, Object>) mensaje.get("delivery");
                notificacion.setVenDestinatario(delivery.get("recipients").toString());
                String f2 = (String) delivery.get("timestamp");
                notificacion.setVenFecha(UtilsDate.timestamp(f2));
                verificacionNotificacionesDao.insertar(notificacion);
                break;
            case "Open":
                Map<String, Object> open = (Map<String, Object>) mensaje.get("open");
                notificacion.setVenDestinatario(commonheaders.get("to").toString());
                String f3 = (String) open.get("timestamp");
                notificacion.setVenFecha(UtilsDate.timestamp(f3));
                verificacionNotificacionesDao.insertar(notificacion);
                break;
            case "Bounce":
                Map<String, Object> bounce = (Map<String, Object>) mensaje.get("bounce");
                if (bounce != null) {
                    List<Map<String, Object>> bouncedRecipients = (List<Map<String, Object>>) bounce.get("bouncedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (bouncedRecipients != null && !bouncedRecipients.isEmpty()) {
                        for (Map<String, Object> bouncedRecipient : bouncedRecipients) {
                            ConVerificacionErroresNotificaciones notificaciones = new ConVerificacionErroresNotificaciones();
                            notificaciones.setVenEmpresa(empresa);
                            notificaciones.setVenInforme(json);
                            notificaciones.setVenTipo(tipoEvento + bounce.get("bounceType").toString());
                            String f4 = (String) bounce.get("timestamp");
                            notificaciones.setVenFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = bouncedRecipient;
                            notificaciones.setVenDestinatario(recipiente.get("emailAddress").toString());
                            notificaciones.setVenObservacion(recipiente.get("diagnosticCode").toString());
                            verificacionNotificacionesDao.insertar(notificaciones);
                        }
                    }
                }
                break;
            case "Complaint":
                Map<String, Object> complaint = (Map<String, Object>) mensaje.get("complaint");
                if (complaint != null) {
                    List<Map<String, Object>> complaintRecipients = (List<Map<String, Object>>) complaint.get("complainedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (complaintRecipients != null && !complaintRecipients.isEmpty()) {
                        for (Map<String, Object> complaintRecipient : complaintRecipients) {
                            ConVerificacionErroresNotificaciones notificaciones = new ConVerificacionErroresNotificaciones();
                            notificaciones.setVenEmpresa(empresa);
                            notificaciones.setVenInforme(json);
                            notificaciones.setVenTipo(tipoEvento);
                            String f4 = (String) complaint.get("timestamp");
                            notificaciones.setVenFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = complaintRecipient;
                            notificaciones.setVenDestinatario(recipiente.get("emailAddress").toString());
                            verificacionNotificacionesDao.insertar(notificaciones);
                        }
                    }
                }
                break;
        }
    }

    private void insertarNotificacionesGuiaRemision(String tipoEvento, String json, Map<String, Object> tags, Map<String, Object> mail, Map<String, Object> mensaje) throws Exception {
        AnxGuiaRemisionElectronicaNotificaciones notificacion = new AnxGuiaRemisionElectronicaNotificaciones();

        String empresa = obtenerAtributo((ArrayList<Object>) tags.get("ows-empresa"));
        String periodo = obtenerAtributo((ArrayList<Object>) tags.get("ows-periodo"));
        String numero = obtenerAtributo((ArrayList<Object>) tags.get("ows-numero"));

        notificacion.seteTipo(tipoEvento);
        notificacion.setGuiaEmpresa(empresa);
        notificacion.setGuiaPeriodo(periodo);
        notificacion.setGuiaNumero(numero);
        notificacion.seteInforme(json);

        Map<String, Object> commonheaders = (Map<String, Object>) mail.get("commonHeaders");

        switch (tipoEvento) {
            case "Send":
                notificacion.seteDestinatario(commonheaders.get("to").toString());
                String f = (String) mail.get("timestamp");
                notificacion.seteFecha(UtilsDate.timestamp(f));
                anxGuiaRemisionElectronicaNotificacionesDao.insertar(notificacion);
                break;
            case "Delivery":
                Map<String, Object> delivery = (Map<String, Object>) mensaje.get("delivery");
                notificacion.seteDestinatario(delivery.get("recipients").toString());
                String f2 = (String) delivery.get("timestamp");
                notificacion.seteFecha(UtilsDate.timestamp(f2));
                anxGuiaRemisionElectronicaNotificacionesDao.insertar(notificacion);
                break;
            case "Open":
                Map<String, Object> open = (Map<String, Object>) mensaje.get("open");
                notificacion.seteDestinatario(commonheaders.get("to").toString());
                String f3 = (String) open.get("timestamp");
                notificacion.seteFecha(UtilsDate.timestamp(f3));
                anxGuiaRemisionElectronicaNotificacionesDao.insertar(notificacion);
                break;
            case "Bounce":
                Map<String, Object> bounce = (Map<String, Object>) mensaje.get("bounce");
                if (bounce != null) {
                    List<Map<String, Object>> bouncedRecipients = (List<Map<String, Object>>) bounce.get("bouncedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (bouncedRecipients != null && !bouncedRecipients.isEmpty()) {
                        for (Map<String, Object> bouncedRecipient : bouncedRecipients) {
                            AnxGuiaRemisionElectronicaNotificaciones notificaciones = new AnxGuiaRemisionElectronicaNotificaciones();
                            notificaciones.setGuiaEmpresa(empresa);
                            notificaciones.setGuiaPeriodo(periodo);
                            notificaciones.setGuiaNumero(numero);
                            notificaciones.seteInforme(json);
                            notificaciones.seteTipo(tipoEvento + bounce.get("bounceType").toString());
                            String f4 = (String) bounce.get("timestamp");
                            notificaciones.seteFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = bouncedRecipient;
                            notificaciones.seteDestinatario(recipiente.get("emailAddress").toString());
                            notificaciones.seteObservacion(recipiente.get("diagnosticCode").toString());
                            anxGuiaRemisionElectronicaNotificacionesDao.insertar(notificaciones);
                            notificarAEmpresaPorGuiaRemision(receptores, notificaciones);
                        }
                    }
                }
                break;
            case "Complaint":
                Map<String, Object> complaint = (Map<String, Object>) mensaje.get("complaint");
                if (complaint != null) {
                    List<Map<String, Object>> complaintRecipients = (List<Map<String, Object>>) complaint.get("complainedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (complaintRecipients != null && !complaintRecipients.isEmpty()) {
                        for (Map<String, Object> complaintRecipient : complaintRecipients) {
                            AnxGuiaRemisionElectronicaNotificaciones notificaciones = new AnxGuiaRemisionElectronicaNotificaciones();
                            notificaciones.setGuiaEmpresa(empresa);
                            notificaciones.setGuiaPeriodo(periodo);
                            notificaciones.setGuiaNumero(numero);
                            notificaciones.seteInforme(json);
                            notificaciones.seteTipo(tipoEvento);
                            String f4 = (String) complaint.get("timestamp");
                            notificaciones.seteFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = complaintRecipient;
                            notificaciones.seteDestinatario(recipiente.get("emailAddress").toString());
                            anxGuiaRemisionElectronicaNotificacionesDao.insertar(notificaciones);
                            notificarAEmpresaPorGuiaRemision(receptores, notificaciones);
                        }
                    }
                }
                break;
        }
    }

    private void insertarNotificacionesLiquidacionCompra(String tipoEvento, String json, Map<String, Object> tags, Map<String, Object> mail, Map<String, Object> mensaje) throws Exception {
        AnxLiquidacionComprasElectronicaNotificaciones notificacion = new AnxLiquidacionComprasElectronicaNotificaciones();

        String empresa = obtenerAtributo((ArrayList<Object>) tags.get("ows-empresa"));
        String periodo = obtenerAtributo((ArrayList<Object>) tags.get("ows-periodo"));
        String motivo = obtenerAtributo((ArrayList<Object>) tags.get("ows-motivo"));
        String numero = obtenerAtributo((ArrayList<Object>) tags.get("ows-numero"));

        notificacion.seteTipo(tipoEvento);
        notificacion.setCompEmpresa(empresa);
        notificacion.setCompPeriodo(periodo);
        notificacion.setCompMotivo(motivo);
        notificacion.setCompNumero(numero);
        notificacion.seteInforme(json);

        Map<String, Object> commonheaders = (Map<String, Object>) mail.get("commonHeaders");

        switch (tipoEvento) {
            case "Send":
                notificacion.seteDestinatario(commonheaders.get("to").toString());
                String f = (String) mail.get("timestamp");
                notificacion.seteFecha(UtilsDate.timestamp(f));
                anxLiquidacionComprasElectronicaNotificacionesDao.insertar(notificacion);
                break;
            case "Delivery":
                Map<String, Object> delivery = (Map<String, Object>) mensaje.get("delivery");
                notificacion.seteDestinatario(delivery.get("recipients").toString());
                String f2 = (String) delivery.get("timestamp");
                notificacion.seteFecha(UtilsDate.timestamp(f2));
                anxLiquidacionComprasElectronicaNotificacionesDao.insertar(notificacion);
                break;
            case "Open":
                Map<String, Object> open = (Map<String, Object>) mensaje.get("open");
                notificacion.seteDestinatario(commonheaders.get("to").toString());
                String f3 = (String) open.get("timestamp");
                notificacion.seteFecha(UtilsDate.timestamp(f3));
                anxLiquidacionComprasElectronicaNotificacionesDao.insertar(notificacion);
                break;
            case "Bounce":
                Map<String, Object> bounce = (Map<String, Object>) mensaje.get("bounce");
                if (bounce != null) {
                    List<Map<String, Object>> bouncedRecipients = (List<Map<String, Object>>) bounce.get("bouncedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (bouncedRecipients != null && !bouncedRecipients.isEmpty()) {
                        for (Map<String, Object> bouncedRecipient : bouncedRecipients) {
                            AnxLiquidacionComprasElectronicaNotificaciones notificaciones = new AnxLiquidacionComprasElectronicaNotificaciones();
                            notificaciones.setCompEmpresa(empresa);
                            notificaciones.setCompPeriodo(periodo);
                            notificaciones.setCompMotivo(motivo);
                            notificaciones.setCompNumero(numero);
                            notificaciones.seteInforme(json);
                            notificaciones.seteTipo(tipoEvento + bounce.get("bounceType").toString());
                            String f4 = (String) bounce.get("timestamp");
                            notificaciones.seteFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = bouncedRecipient;
                            notificaciones.seteDestinatario(recipiente.get("emailAddress").toString());
                            notificaciones.seteObservacion(recipiente.get("diagnosticCode").toString());
                            anxLiquidacionComprasElectronicaNotificacionesDao.insertar(notificaciones);
                            notificarAEmpresaPorLiquidacionCompra(receptores, notificaciones);
                        }
                    }
                }
                break;
            case "Complaint":
                Map<String, Object> complaint = (Map<String, Object>) mensaje.get("complaint");
                if (complaint != null) {
                    List<Map<String, Object>> complaintRecipients = (List<Map<String, Object>>) complaint.get("complainedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (complaintRecipients != null && !complaintRecipients.isEmpty()) {
                        for (Map<String, Object> complaintRecipient : complaintRecipients) {
                            AnxLiquidacionComprasElectronicaNotificaciones notificaciones = new AnxLiquidacionComprasElectronicaNotificaciones();
                            notificaciones.setCompEmpresa(empresa);
                            notificaciones.setCompPeriodo(periodo);
                            notificaciones.setCompMotivo(motivo);
                            notificaciones.setCompNumero(numero);
                            notificaciones.seteInforme(json);
                            notificaciones.seteTipo(tipoEvento);
                            String f4 = (String) complaint.get("timestamp");
                            notificaciones.seteFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = complaintRecipient;
                            notificaciones.seteDestinatario(recipiente.get("emailAddress").toString());
                            anxLiquidacionComprasElectronicaNotificacionesDao.insertar(notificaciones);
                            notificarAEmpresaPorLiquidacionCompra(receptores, notificaciones);
                        }
                    }
                }
                break;
        }
    }

    private void insertarNotificacionesOrdenCompra(String tipoEvento, String json, Map<String, Object> tags, Map<String, Object> mail, Map<String, Object> mensaje) throws Exception {
        AnxNotificacionAWSTO notificacion = new AnxNotificacionAWSTO();
        grabarEntidad(notificacion, tags);
        notificacion.setTipo(tipoEvento);
        notificacion.setInforme(json);
        Map<String, Object> commonheaders = (Map<String, Object>) mail.get("commonHeaders");
        switch (tipoEvento) {
            case "Send":
                enviado(notificacion, commonheaders, mail);
                invPedidosOrdenCompraNotificacionesDao.insertar(ConversionesInventario.convertirAnxNotificacionAWSTO_InvPedidosOrdenCompraNotificaciones(notificacion));
                break;
            case "Delivery":
                delivery(notificacion, mensaje);
                invPedidosOrdenCompraNotificacionesDao.insertar(ConversionesInventario.convertirAnxNotificacionAWSTO_InvPedidosOrdenCompraNotificaciones(notificacion));
                break;
            case "Open":
                open(notificacion, commonheaders, mensaje);
                invPedidosOrdenCompraNotificacionesDao.insertar(ConversionesInventario.convertirAnxNotificacionAWSTO_InvPedidosOrdenCompraNotificaciones(notificacion));
                break;
            case "Bounce":
                Map<String, Object> bounce = (Map<String, Object>) mensaje.get("bounce");
                if (bounce != null) {
                    List<Map<String, Object>> bouncedRecipients = (List<Map<String, Object>>) bounce.get("bouncedRecipients");
                    ArrayList<String> emisores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (bouncedRecipients != null && !bouncedRecipients.isEmpty()) {
                        for (Map<String, Object> bouncedRecipient : bouncedRecipients) {
                            AnxNotificacionAWSTO notificaciones = new AnxNotificacionAWSTO();
                            grabarEntidad(notificaciones, tags);
                            notificaciones.setInforme(json);
                            notificaciones.setTipo(tipoEvento + bounce.get("bounceType").toString());
                            bounce(notificaciones, bounce, bouncedRecipient);
                            invPedidosOrdenCompraNotificacionesDao.insertar(ConversionesInventario.convertirAnxNotificacionAWSTO_InvPedidosOrdenCompraNotificaciones(notificaciones));
                            notificarAEmpresaPorOrdenDeCompra(emisores, notificaciones);
                        }
                    }
                }
                break;
        }
    }

    private String obtenerAtributo(ArrayList<Object> get) {
        return (String) get.get(0);
    }

    public void grabarEntidad(AnxNotificacionAWSTO notificacion, Map<String, Object> tags) {
        notificacion.setEmpresa(obtenerAtributo((ArrayList<Object>) tags.get("ows-empresa")));
        notificacion.setSector(obtenerAtributo((ArrayList<Object>) tags.get("ows-sector")));//en realidad es sector pero para mantener el metodo con su tag numero 2
        notificacion.setMotivo(obtenerAtributo((ArrayList<Object>) tags.get("ows-motivo")));
        notificacion.setNumero(obtenerAtributo((ArrayList<Object>) tags.get("ows-numero")));
    }

    public void enviado(AnxNotificacionAWSTO notificacion, Map<String, Object> commonheaders, Map<String, Object> mail) {
        notificacion.setDestinatario(commonheaders.get("to").toString());
        String f = (String) mail.get("timestamp");
        notificacion.setFecha(UtilsDate.timestamp(f));
    }

    public void delivery(AnxNotificacionAWSTO notificacion, Map<String, Object> mensaje) {
        Map<String, Object> delivery = (Map<String, Object>) mensaje.get("delivery");
        notificacion.setDestinatario(delivery.get("recipients").toString());
        String f2 = (String) delivery.get("timestamp");
        notificacion.setFecha(UtilsDate.timestamp(f2));
    }

    public void open(AnxNotificacionAWSTO notificacion, Map<String, Object> commonheaders, Map<String, Object> mensaje) {
        Map<String, Object> open = (Map<String, Object>) mensaje.get("open");
        notificacion.setDestinatario(commonheaders.get("to").toString());
        String f3 = (String) open.get("timestamp");
        notificacion.setFecha(UtilsDate.timestamp(f3));
    }

    public void bounce(AnxNotificacionAWSTO notificacion, Map<String, Object> bounce, Map<String, Object> bouncedRecipient) {
        String f4 = (String) bounce.get("timestamp");
        notificacion.setFecha(UtilsDate.timestamp(f4));
        Map<String, Object> recipiente = bouncedRecipient;
        notificacion.setDestinatario(recipiente.get("emailAddress").toString());
        notificacion.setObservacion(recipiente.get("diagnosticCode").toString());
    }

    private void notificarAEmpresaPorVenta(ArrayList<String> receptores, AnxVentaElectronicaNotificaciones notificacion) throws Exception {
        InvEntidadTransaccionTO cliente = ventasService.obtenerClienteDeVenta(notificacion.getVtaEmpresa(), notificacion.getVtaPeriodo(), notificacion.getVtaMotivo(), notificacion.getVtaNumero());
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, notificacion.getVtaEmpresa());
        envioCorreo(sisEmpresaParametros, receptores, cliente, notificacion.getEDestinatario(), notificacion.getEObservacion());
    }

    private void notificarAEmpresaPorCompra(ArrayList<String> receptores, AnxCompraElectronicaNotificaciones notificacion) throws Exception {
        InvEntidadTransaccionTO proveedor = comprasService.obtenerProveedorDeCompra(notificacion.getCompEmpresa(), notificacion.getCompPeriodo(), notificacion.getCompMotivo(), notificacion.getCompNumero());
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, notificacion.getCompEmpresa());
        envioCorreo(sisEmpresaParametros, receptores, proveedor, notificacion.getEDestinatario(), notificacion.getEObservacion());
    }

    private void notificarAEmpresaPorGuiaRemision(ArrayList<String> receptores, AnxGuiaRemisionElectronicaNotificaciones notificacion) throws Exception {
        InvEntidadTransaccionTO cliente = guiaElectronicaService.obtenerClienteDeGuia(notificacion.getGuiaEmpresa(), notificacion.getGuiaPeriodo(), notificacion.getGuiaNumero());
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, notificacion.getGuiaEmpresa());
        envioCorreo(sisEmpresaParametros, receptores, cliente, notificacion.geteDestinatario(), notificacion.geteObservacion());
    }

    private void notificarAEmpresaPorLiquidacionCompra(ArrayList<String> receptores, AnxLiquidacionComprasElectronicaNotificaciones notificacion) throws Exception {
        InvEntidadTransaccionTO proveedor = liquidacionCompraElectronicaService.obtenerProveedorDeLiquidacionCompra(notificacion.getCompEmpresa(), notificacion.getCompPeriodo(), notificacion.getCompMotivo(), notificacion.getCompNumero());
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, notificacion.getCompEmpresa());
        envioCorreo(sisEmpresaParametros, receptores, proveedor, notificacion.geteDestinatario(), notificacion.geteObservacion());
    }

    private void notificarAEmpresaPorCuentasPorCobrar(ArrayList<String> receptores, InvClienteNotificaciones notificacion, String documento) throws Exception {
        InvEntidadTransaccionTO cliente = clienteService.obtenerInvEntidadTransaccionTOCuentasPorCobrar(notificacion.getCliEmpresa(), notificacion.getCliCodigo(), documento);
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, notificacion.getCliEmpresa());
        envioCorreo(sisEmpresaParametros, receptores, cliente, notificacion.geteDestinatario(), notificacion.geteObservacion());
    }

    private void notificarAEmpresaPorOrdenDeCompra(ArrayList<String> emisores, AnxNotificacionAWSTO notificacion) throws Exception {
        InvEntidadTransaccionTO proveedor = pedidosOrdenCompraService.obtenerProveedorDeOrdenDeCompra(notificacion.getEmpresa(), notificacion.getSector(), notificacion.getMotivo(), notificacion.getNumero());
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, notificacion.getEmpresa());
        envioCorreo(sisEmpresaParametros, emisores, proveedor, notificacion.getDestinatario(), notificacion.getObservacion());
    }

    private void envioCorreo(SisEmpresaParametros sisEmpresaParametros, ArrayList<String> receptores, InvEntidadTransaccionTO datosPersonales, String destinario, String observacion) throws Exception {
        SisNotificacion sisNotificacion = new SisNotificacion();
        if (sisEmpresaParametros == null || sisEmpresaParametros.getEmpCodigo() == null) {
        } else if (receptores == null || receptores.isEmpty()) {
        } else {
            String asunto = "Correo inválido de destino";
            String detalle = "<html><head><title></title></head><body>" + "Empresa: " + sisEmpresaParametros.getEmpCodigo().getEmpRazonSocial()
                    + "<br><br>Este correo electrónico es para informarle lo siguiente: "
                    + "<br><br>Durante el proceso de envío del documento electrónico: "
                    + datosPersonales.getTipo() + " N°: " + datosPersonales.getDocumento()
                    + ", se ha detectado que la dirección de correo " + destinario
                    + " de : " + datosPersonales.getIdentificacion() + " " + datosPersonales.getRazonSocial() + " es un email inválido por lo que será removido. "
                    + "<br><br>Usted deberá verificar en el sistema y agregar una dirección de correo válida."
                    + "<br><br><b> Razón Técnica:</b> <br>" + observacion
                    + "<br><br><br>Atentamente" + "<br> " + "<b>" + " Obinte Web Services " + "</b>"
                    + "<br>"
                    + "<br>------------------------------------------------------------------------------------------------------"
                    + "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                    + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>";

            SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = new SisEmailComprobanteElectronicoTO();
            sisEmailComprobanteElectronicoTO.setMailEmisor(sisEmpresaParametros.getEmpCodigo().getEmpEmail());
            sisEmailComprobanteElectronicoTO.setClaveEmisor(sisEmpresaParametros.getEmpCodigo().getEmpClave());
            sisEmailComprobanteElectronicoTO.setNombreEmisor("Alex de Obinte.");
            sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
            for (String receptor : receptores) {
                sisEmailComprobanteElectronicoTO.setMailReceptor(receptor);
                sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());
                UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, sisNotificacion.getMailReceptor(), asunto, detalle, "", new ArrayList<>(), sisNotificacion);
            }
        }
    }

    private void insertarNotificacionesRolDePago(String tipoEvento, String json, Map<String, Object> tags, Map<String, Object> mail, Map<String, Object> mensaje) throws Exception {
        RhRolPagoNotificaciones notificacion = new RhRolPagoNotificaciones();

        String empresa = obtenerAtributo((ArrayList<Object>) tags.get("ows-empresa"));
        String contable = obtenerAtributo((ArrayList<Object>) tags.get("ows-documento"));
        String motivo = obtenerAtributo((ArrayList<Object>) tags.get("ows-motivo"));
        motivo = motivo != null ? motivo.replace("_", " ") : "";
        notificacion.setRpnTipo(tipoEvento);
        notificacion.setRpnEmpleado(motivo);
        notificacion.setRpnContable(contable.replace("_", " | "));
        notificacion.setRpnEmpresa(empresa);
        notificacion.setRpnInforme(json);

        Map<String, Object> commonheaders = (Map<String, Object>) mail.get("commonHeaders");
        String asunto = commonheaders.get("subject").toString();
        notificacion.setRpnObservacion(asunto);

        switch (tipoEvento) {
            case "Send":
                notificacion.setRpnDestinatario(commonheaders.get("to").toString());
                String f = (String) mail.get("timestamp");
                notificacion.setRpnFecha(UtilsDate.timestamp(f));
                rhRolPagoNotificacionesDao.insertar(notificacion);
                break;
            case "Delivery":
                Map<String, Object> delivery = (Map<String, Object>) mensaje.get("delivery");
                notificacion.setRpnDestinatario(delivery.get("recipients").toString());
                String f2 = (String) delivery.get("timestamp");
                notificacion.setRpnFecha(UtilsDate.timestamp(f2));
                rhRolPagoNotificacionesDao.insertar(notificacion);
                break;
            case "Open":
                Map<String, Object> open = (Map<String, Object>) mensaje.get("open");
                notificacion.setRpnDestinatario(commonheaders.get("to").toString());
                String f3 = (String) open.get("timestamp");
                notificacion.setRpnFecha(UtilsDate.timestamp(f3));
                rhRolPagoNotificacionesDao.insertar(notificacion);
                break;
            case "Bounce":
                Map<String, Object> bounce = (Map<String, Object>) mensaje.get("bounce");
                if (bounce != null) {
                    List<Map<String, Object>> bouncedRecipients = (List<Map<String, Object>>) bounce.get("bouncedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (bouncedRecipients != null && !bouncedRecipients.isEmpty()) {
                        for (Map<String, Object> bouncedRecipient : bouncedRecipients) {
                            RhRolPagoNotificaciones notificaciones = new RhRolPagoNotificaciones();
                            notificaciones.setRpnEmpresa(empresa);
                            notificaciones.setRpnContable(contable);
                            notificaciones.setRpnObservacion(asunto);
                            notificaciones.setRpnInforme(json);
                            notificaciones.setRpnTipo(tipoEvento + bounce.get("bounceType").toString());
                            String f4 = (String) bounce.get("timestamp");
                            notificaciones.setRpnFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = bouncedRecipient;
                            notificaciones.setRpnDestinatario(recipiente.get("emailAddress").toString());
                            notificaciones.setRpnObservacion(recipiente.get("diagnosticCode").toString());
                            rhRolPagoNotificacionesDao.insertar(notificaciones);
                        }
                    }
                }
                break;
            case "Complaint":
                Map<String, Object> complaint = (Map<String, Object>) mensaje.get("complaint");
                if (complaint != null) {
                    List<Map<String, Object>> complaintRecipients = (List<Map<String, Object>>) complaint.get("complainedRecipients");
                    ArrayList<String> receptores = (ArrayList<String>) commonheaders.get("replyTo");
                    if (complaintRecipients != null && !complaintRecipients.isEmpty()) {
                        for (Map<String, Object> complaintRecipient : complaintRecipients) {
                            RhRolPagoNotificaciones notificaciones = new RhRolPagoNotificaciones();
                            notificaciones.setRpnEmpresa(empresa);
                            notificaciones.setRpnContable(contable);
                            notificaciones.setRpnObservacion(asunto);
                            notificaciones.setRpnInforme(json);
                            notificaciones.setRpnTipo(tipoEvento);
                            String f4 = (String) complaint.get("timestamp");
                            notificaciones.setRpnFecha(UtilsDate.timestamp(f4));
                            Map<String, Object> recipiente = complaintRecipient;
                            notificaciones.setRpnDestinatario(recipiente.get("emailAddress").toString());
                            rhRolPagoNotificacionesDao.insertar(notificaciones);
                        }
                    }
                }
                break;
        }
    }

}
