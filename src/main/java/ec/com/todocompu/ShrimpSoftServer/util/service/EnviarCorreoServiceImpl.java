package ec.com.todocompu.ShrimpSoftServer.util.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.GuiaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.RolService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEmpresaNotificacionesDao;
import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.EmpresaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.NotificacionService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SisEmpresaNotificacionesService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesSistema;
import ec.com.todocompu.ShrimpSoftServer.util.UtilsMail;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorCobrarTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.enums.TipoNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCuentasCobrarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaRolSaldoEmpleadoDetalladoTO;
import static ec.com.todocompu.ShrimpSoftUtils.rrhh.UtilsEmpleado.convertirValoresItemDetalleVaciosACero;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReportesRol;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioEmailTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificacionesEventos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import ec.com.todocompu.ShrimpSoftUtils.web.ArchivoSoporteTO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@Service
public class EnviarCorreoServiceImpl implements EnviarCorreoService {

    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private SisEmpresaNotificacionesDao sisEmpresaNotificacionesDao;
    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private RolService rolService;
    @Autowired
    private VentaElectronicaService ventaElectronicaService;
    @Autowired
    private CompraElectronicaService compraElectronicaService;
    @Autowired
    private GuiaElectronicaService guiaElectronicaService;
    @Autowired
    private SisEmpresaNotificacionesService sisEmpresaNotificacionesService;
    @Autowired
    private EmpleadoDao empleadoDao;

    @Override
    public void enviarError(String clienteServidor, SisErrorTO sisErrorTO) {
        SisNotificacion sisNotificacion = notificacionService.obtener();
        SisEmpresa sisEmpresa = empresaService.obtenerPorId(sisErrorTO.getEmpresa());
        SisUsuario sisUsuario = usuarioService.obtenerPorId(sisErrorTO.getUsuario());
        String usuario = sisUsuario == null ? " " : sisUsuario.getUsrNombre() + " " + sisUsuario.getUsrApellido();
        String empresa = sisEmpresa == null ? " " : sisEmpresa.getEmpNombre();

        String asunto = "USUARIO: " + usuario;
        String detalle = "<p style='font-size: 18px; text-align: center;'><strong>Error ocasionado desde el " + clienteServidor
                + "</strong></p><p><strong>TIPO DE ERROR:</strong> " + sisErrorTO.getTipoError()
                + "</p><p><strong>CLASE:</strong> " + sisErrorTO.getClase()
                + "</p><p><strong>LINEA:</strong> " + sisErrorTO.getLinea()
                + "</p><p><strong>METODO:</strong> " + sisErrorTO.getMetodo()
                + "</p><p><strong>FECHA:</strong> " + sisErrorTO.getFecha()
                + "</p><p><strong>USUARIO:</strong> " + usuario
                + "</p><p><strong>EMAIL:</strong> " + (sisErrorTO.getEmail() != null ? sisErrorTO.getEmail() : "")
                + "</p><p><strong>TELEFONO:</strong> " + (sisErrorTO.getTelefono() != null ? sisErrorTO.getTelefono() : "")
                + "</p><p><strong>MAC:</strong> " + sisErrorTO.getMac()
                + "</p><p><strong>MENSAJE:</strong> " + sisErrorTO.getMensaje()
                + "</p><br/><p><strong>STACK TRACE:</strong></p><p>" + sisErrorTO.getException().replaceAll("\n", "<br/>") + "</p>";

        String detalleTextoPlano = "Error ocasionado desde el " + clienteServidor
                + "\nTIPO DE ERROR: " + sisErrorTO.getTipoError()
                + "\nCLASE:  " + sisErrorTO.getClase()
                + "\nLINEA:  " + sisErrorTO.getLinea()
                + "\nMETODO: " + sisErrorTO.getMetodo()
                + "\nFECHA:  " + sisErrorTO.getFecha()
                + "\nUSUARIO: " + usuario
                + "\nEMAIL: " + sisErrorTO.getEmail()
                + "\nTELEFONO: " + sisErrorTO.getTelefono()
                + "\nMAC: " + sisErrorTO.getMac()
                + "\nMENSAJE: " + sisErrorTO.getMensaje()
                + "\n\nSTACK TRACE: " + sisErrorTO.getException();

        try {
            UtilsMail.envioErrorAmazonSES(empresa, sisNotificacion.getMailReceptor(), asunto, detalle, detalleTextoPlano, sisNotificacion);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enviarErrorPersonalizado(String asunto, String detalle, String codigoEmpresa) {
        SisNotificacion sisNotificacion = notificacionService.obtener();
        SisEmpresa sisEmpresa = empresaService.obtenerPorId(codigoEmpresa);
        String empresa = sisEmpresa == null ? " " : sisEmpresa.getEmpNombre();
        try {
            UtilsMail.envioErrorAmazonSES(empresa, sisNotificacion.getMailReceptor(), asunto, detalle, "", sisNotificacion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String enviarComprobantesElectronicos(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO, List<File> listAdjunto) {
        SisNotificacion sisNotificacion = new SisNotificacion();

        sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
        sisNotificacion.setMailPasswordEmisor(sisEmailComprobanteElectronicoTO.getClaveEmisor());
        sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());

        String asunto = sisEmailComprobanteElectronicoTO.getTipoComprobante() + " Nº " + sisEmailComprobanteElectronicoTO.getNumeroComprobante();
        String logo = "";
        String header;
        String footer = footerPorDefecto();

        switch (sisEmailComprobanteElectronicoTO.getRucEmisor()) {
            case "0791807611001":
            case "0791787661001":
                logo = "https://ows-multimedia.s3.amazonaws.com/Archivos+firma+electronica/head_obinte.png";
                footer = "<div> <img src=\"https://ows-multimedia.s3.amazonaws.com/Archivos+firma+electronica/footer.png\" style=\"height: 200px; width: 100%;\"></div>\n";
                break;
            case "0791755093001":
            case "0791702070001":
                logo = "https://ows-multimedia.s3.amazonaws.com/logo-red-access.jpg";
                break;
            case "0791807298001":
                logo = "https://ows-multimedia.s3.amazonaws.com/logo-fialsanet.jpg";
                break;
        }

        switch (sisEmailComprobanteElectronicoTO.getRucEmisor()) {
            case "0791807611001":
            case "0791787661001":
            case "0791755093001":
            case "0791702070001":
            case "0791807298001":
                header = headerObinte(sisEmailComprobanteElectronicoTO, logo);
                break;
            default:
                header = headerOtrasEmpresas(sisEmailComprobanteElectronicoTO);
                break;
        }

        String templateMeganet
                = "<div> Para su mayor comodidad, puede efectuar sus pagos en las siguientes cuentas: </div> "
                + "<div>&nbsp;</div> "
                + "<div> <b> MEGANET </b> </div> "
                + "<div> Meganet S.A. </div> "
                + "<div> Bco Machala Cta Ahorros 1010869303 </div> "
                + "<div> Bco Pichincha Cta Cte 3513024304 </div> "
                + "<div> Bco Pacifico Cta Cte. # 7708173 </div> "
                + "<div> Bco Guayaquil (B. del Barrio) Cta. Aho. # 12704407 a nombre de DAVID SILVA JARAMILLO </div> "
                + "<div> Coop. Santa Rosa con su número de Cédula o Ruc </div> "
                + "<div>&nbsp;</div> ";

        String templateCommNet
                = "<div> <b> COMM&NET </b> </div> "
                + "<div>&nbsp;</div> "
                + "<div> Cheque o depósito a nombre de COMM&NET SA </div> "
                + "<div> Bco Guayaquil Cta. Cte. 4631099 </div> "
                + "<div> Bco Pichincha Cta. Ahorros 4179805900 </div> "
                + "<div> Bco Machala Cta. Corriente 1010708954 </div> "
                + "<div> Produbanco Cta. Cte. # 02653003990 </div> "
                + "<div> Coop. Santa Rosa con su número de Cédula o Ruc </div> "
                + "<div>&nbsp;</div> ";

        String template = "<html lang=\"es\" style=\"font-family: sans-serif; font-size: 12px; font-weight: bold;\">\n"
                + "<style>\n"
                + "    *, *::before, *::after {\n"
                + "        box-sizing: border-box;\n"
                + "    }\n"
                + "</style>\n"
                + "<body style=\"margin: 0; line-height: 1.5; text-align: left; margin-left: 10%; margin-right: 10%;\">\n"
                + header + informacionCentralDelCorreo(sisEmailComprobanteElectronicoTO)
                + "    <div style=\"text-align: center;\">\n"
                + "        <br>\n"
                + "        <p>Gracias por preferirnos: <br></p>\n"
                + "        <p>Atentamente,</p><br>\n"
                + "        <div><strong>" + sisEmailComprobanteElectronicoTO.getNombreEmisor() + "</strong></div>\n"
                + "        <div><strong>RUC: </strong>" + sisEmailComprobanteElectronicoTO.getRucEmisor() + "</div>\n"
                + "        <div><strong>Dirección: </strong>" + sisEmailComprobanteElectronicoTO.getDireccionEmisor() + "</div>\n"
                + "        <div><strong>Teléfono: </strong>" + sisEmailComprobanteElectronicoTO.getTelefonoEmisor() + "</div>\n"
                + "        <div><strong>Email: </strong>" + sisEmailComprobanteElectronicoTO.getMailEmisor() + "</div>\n"
                + "        <br>\n"
                + (sisEmailComprobanteElectronicoTO.getRucEmisor().equalsIgnoreCase("0791755093001") ? templateMeganet : "") //MEGANET
                + (sisEmailComprobanteElectronicoTO.getRucEmisor().equalsIgnoreCase("0791702070001") ? templateCommNet : "") //COMM & NET
                + "        <br>\n"
                + "    </div>\n"
                + footer
                + "</body>\n"
                + "</html>";

        String envioCorreoExitoso = "";
        try {
            if (sisNotificacion.getMailReceptor() != null && !sisNotificacion.getMailReceptor().equals("")) {
                String[] correos = sisNotificacion.getMailReceptor().split(";");
                for (String correo : correos) {
                    envioCorreoExitoso = UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, correo, asunto, template, "", listAdjunto, sisNotificacion);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return envioCorreoExitoso;
    }

    public String footerPorDefecto() {
        return "<div style=\"background-image: url('https://ows-multimedia.s3.amazonaws.com/Archivos+firma+electronica/foot_colmena.png'); text-align: center; color: #365b61;\">\n"
                + "        <br>\n"
                + "        <img src=\"https://ows-multimedia.s3.amazonaws.com/Archivos+firma+electronica/logo_acosux.png\" alt=\"obinte\" style=\"width: 20%;\">\n"
                + "        <br><br>\n"
                + "        <div>ACOSUX. Software contable integrado con facturación electrónica, contáctenos al </div>\n"
                + "        <div> (593)72966371 / (07)2924090 / 0939309203,</div>\n"
                + "        <div>estaremos gustosos en atender sus requerimientos.</div>\n"
                + "        <br><br>\n"
                + "    </div>";
    }

    public String headerOtrasEmpresas(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO) {
        return " <div style='height: 10px; background-color: #c81d89;'></div>"
                + "    <div style='height: 4px; background-color: #365963;'></div>"
                + "    <div style=\"height: 200px;\">\n"
                + "    </div>\n"
                + "    <div style=\"text-align: center; font-weight: bold; font-size: 18px; color: #365963;\">\n"
                + "        NUEVO DOCUMENTO ELECTRÓNICO GENERADO"
                + "    </div>\n"
                + "    <div style='height: 4px; background-color: #365963;'></div>"
                + "    <div style=\"padding: 3% 3% 0 3%;\">\n"
                + "         <b style=\"font-size: 16px;\"> Estimado (a) " + sisEmailComprobanteElectronicoTO.getNombreReceptor() + "</b>\n"
                + "    </div>\n";
    }

    public String headerObinte(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO, String logo) {
        return "<div>\n"
                + "        <img src=" + logo + " width=\"100%\" height=\"200px;\">\n"
                + "    </div>\n"
                + "    <div style=\"padding-top: 30px; border-bottom: #365b61; border-bottom-style: solid; display: flex;\">\n"
                + "        <div style=\"font-weight: bold; font-size: 16px; padding: 1%; padding-left: 5%; width: 33.333333%; max-width: 33.333333%;\">\n"
                + "            <div>Estimado (a) </div>\n"
                + "        </div>\n"
                + "        <div style=\"padding: 1%; width: 66.666667%; max-width: 66.666667%; background-color: #365b61; border-radius: 25px 0 0 0;\">\n"
                + "            <div style=\"color: white; font-size: 16px;  text-align: right;\"> " + sisEmailComprobanteElectronicoTO.getNombreReceptor() + "</div>\n"
                + "        </div>\n"
                + "    </div>\n";
    }

    public String informacionCentralDelCorreo(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO) {
        return "    <div style=\"padding: 3%;\">\n"
                + "        Es un placer saludarte, esperamos que todo esté saliendo bien, te informamos que se ha generado el siguiente documento electrónico:\n"
                + "    </div>\n"
                + "    <div style=\"background-color: #dedede; padding-top: 5px; padding-bottom: 5px;\">\n"
                + "        <div style=\"border-bottom-style: dashed; border-color: #fff; border-top-style: dashed; border-top-width: 2px; border-bottom-width: 2px;\">\n"
                + "            <div style=\"padding: 30px 3% 0 3%; display: flex;\">\n"
                + "                <div style=\"color: white; text-align: right; padding: 2% 2% 0 2%; width: 33.333333%; max-width: 33.333333%; background-color: #365b61; border-radius: 20px 0 0 0;\">\n"
                + "                    <div>Tipo de documento</div>\n"
                + "                </div>\n"
                + "                <div style=\"padding: 2% 2% 0 2%; width: 66.666667%; max-width: 66.666667%; font-weight: bold; background-color: #ffff; border-radius: 0 5px 0 0;\">\n"
                + "                    <div>" + sisEmailComprobanteElectronicoTO.getTipoComprobante() + "</div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "            <div style=\"padding: 0 3% 0 3%; display: flex;\">\n"
                + "                <div style=\"color: white; text-align: right; padding: 0 2% 0 2%; width: 33.333333%; max-width: 33.333333%; background-color: #365b61;\">\n"
                + "                    <div>Número de comprobante</div>\n"
                + "                </div>\n"
                + "                <div style=\"padding: 0 2% 0 2%; width: 66.666667%; max-width: 66.666667%; font-weight: bold; background-color: #ffff;\">\n"
                + "                    <div>" + sisEmailComprobanteElectronicoTO.getNumeroComprobante() + "</div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "            <div style=\"padding: 0 3% 0 3%; display: flex;\">\n"
                + "                <div style=\"color: white; text-align: right; padding: 0 2% 0 2%; width: 33.333333%; max-width: 33.333333%; background-color: #365b61;\">\n"
                + "                    <div>Clave de acceso</div>\n"
                + "                </div>\n"
                + "                <div style=\"padding: 0 2% 0 2%; width: 66.666667%; max-width: 66.666667%; font-weight: bold; background-color: #ffff;\">\n"
                + "                    <div>" + sisEmailComprobanteElectronicoTO.getClaveAcceso() + "</div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "            <div style=\"padding: 0 3% 0 3%; display: flex;\">\n"
                + "                <div style=\"color: white; text-align: right; padding: 0 2% 2% 2%; width: 33.333333%; max-width: 33.333333%; background-color: #365b61; border-radius: 0 0 0 20px;\">\n"
                + "                    <div>Valor</div>\n"
                + "                </div>\n"
                + "                <div style=\"padding: 0 2% 2% 2%; width: 66.666667%; max-width: 66.666667%; font-weight: bold; background-color: #ffff; border-radius: 0 0 5px 0;\">\n"
                + "                    <div>" + (sisEmailComprobanteElectronicoTO.getValor() != null ? "$ " + sisEmailComprobanteElectronicoTO.getValor() : "NO APLICA") + "</div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "            <div style=\"padding: 10px 3% 10px 3%; display: flex;\">\n"
                + "                <div style=\"color: white; text-align: right; padding: 2%; width: 33.333333%; max-width: 33.333333%;\">\n"
                + "                    <img src=\"https://ows-multimedia.s3.amazonaws.com/Archivos+firma+electronica/arrow_.png\"\n"
                + "                        width=\"30%\">\n"
                + "                </div>\n"
                + "                <div style=\"padding: 2%; width: 66.666667%; max-width: 66.666667%; color: #616060;\">\n"
                + "                    <i>La representación impresa del comprobante electrónico es el archivo PDF adjunto, no posee validez\n"
                + "                        tributaria y no es necesario que la imprima.</i>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </div>\n";
    }

    @Override
    public void enviarPDFOrdenCompraARegistrador(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO, List<File> listAdjunto) throws Exception {
        SisNotificacion sisNotificacion = new SisNotificacion();

        sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
        sisNotificacion.setMailPasswordEmisor(sisEmailComprobanteElectronicoTO.getClaveEmisor());
        sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());

        String asunto = sisEmailComprobanteElectronicoTO.getNombreEmisor() + ".  "
                + "Orden de compra Nº "
                + sisEmailComprobanteElectronicoTO.getNumeroComprobante();
        String detalle = "<html><head><title></title></head><body>"
                + "Estimado (a) " + sisEmailComprobanteElectronicoTO.getNombreReceptor()
                + "<br><br>Reciba un atento saludo de parte de " + sisEmailComprobanteElectronicoTO.getNombreEmisor()
                + "<br><br>Este correo electrónico es para notificarle la generación del siguiente documento sin validez tributaria: "
                + "<strong>ORDEN DE COMPRA N° " + sisEmailComprobanteElectronicoTO.getNumeroComprobante() + " </strong> "
                + "<br><br><br>Atentamente"
                + "<br> " + sisEmailComprobanteElectronicoTO.getNombreEmisor()
                + "<br>RUC: " + sisEmailComprobanteElectronicoTO.getRucEmisor()
                + "<br>Direccion: " + sisEmailComprobanteElectronicoTO.getDireccionEmisor()
                + "<br>Telefono: " + sisEmailComprobanteElectronicoTO.getTelefonoEmisor()
                + "<br>------------------------------------------------------------------------------------------------------"
                + "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>"
                + "<br><p style='font-size: 10px'>AVISO DE CONFIDENCIALIDAD: Este correo electrónico, incluyendo en su caso, los archivos adjuntos al"
                + "mismo, pueden contener información de carácter confidencial y/o privilegiada, y se envían a la atención"
                + "única y exclusivamente de la persona y/o entidad a quien va dirigido. La copia, revisión, uso, revelación y/o"
                + "distribución de dicha información confidencial sin la autorización por escrito de " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " está prohibida. "
                + "Si usted no es el destinatario a quien se dirige el presente correo, favor de contactar al remitente respondiendo al presente "
                + "correo y eliminar el correo original incluyendo sus archivos, así como cualesquiera copias del mismo. " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " se "
                + "reserva el derecho de ejercitar las acciones legales que correspondan.</p>"
                + "<br><p style='font-size: 10px'>CONFIDENTIALITY NOTICE: This e-mail message including attachments, if any, is intended only for the "
                + "person or entity to which it is addressed and may contain confidential and /or privileged material. Any review, "
                + "use, disclosure or distribution of such confidential information without the written authorization of " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " is prohibited. "
                + "If you are not the intended recipient, please contact the sender by reply e-mail and destroy all copies of the original message. "
                + "Any breach of the above provisions may entitle " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " to take legal actions.</p>";
        try {
            UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, sisNotificacion.getMailReceptor(), asunto, detalle, "", listAdjunto, sisNotificacion);
            //UtilsMail.envioCorreo(sisNotificacion.getMailReceptor(), asunto, detalle, listAdjunto, sisNotificacion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String enviarPDFOrdenCompra(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO, List<File> listAdjunto) throws Exception {
        SisNotificacion sisNotificacion = new SisNotificacion();

        sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
        sisNotificacion.setMailPasswordEmisor(sisEmailComprobanteElectronicoTO.getClaveEmisor());
        sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());

        String asunto = sisEmailComprobanteElectronicoTO.getNombreEmisor() + ".  "
                + "Orden de compra Nº "
                + sisEmailComprobanteElectronicoTO.getNumeroComprobante();
        String detalle = "<html><head><title></title></head><body>"
                + "Estimado (a) " + sisEmailComprobanteElectronicoTO.getNombreReceptor()
                + "<br><br>Reciba un atento saludo de parte de " + sisEmailComprobanteElectronicoTO.getNombreEmisor()
                + "<br><br>Este correo electrónico es para notificarle la generación del siguiente documento sin validez tributaria: "
                + "<strong>ORDEN DE COMPRA N° " + sisEmailComprobanteElectronicoTO.getNumeroComprobante() + " </strong> "
                + "<br><br>Por favor sírvase realizar lo que se detalle en la misma, de preferencia 5 días a partir de la fecha de emisión de este correo. La forma de pago será la habitual."
                + "<br><br>Favor no responder a este correo, cualquier consulta realice a nuestro Teléfono .- " + sisEmailComprobanteElectronicoTO.getTelefonoEmisor()
                + "<br><br>Agradecemos su atención."
                + "<br><br><br>Atentamente"
                + "<br> " + sisEmailComprobanteElectronicoTO.getNombreEmisor()
                + "<br>RUC: " + sisEmailComprobanteElectronicoTO.getRucEmisor()
                + "<br>Direccion: " + sisEmailComprobanteElectronicoTO.getDireccionEmisor()
                + "<br>Telefono: " + sisEmailComprobanteElectronicoTO.getTelefonoEmisor()
                + "<br>------------------------------------------------------------------------------------------------------"
                + "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>"
                + "<br><p style='font-size: 10px'>AVISO DE CONFIDENCIALIDAD: Este correo electrónico, incluyendo en su caso, los archivos adjuntos al"
                + "mismo, pueden contener información de carácter confidencial y/o privilegiada, y se envían a la atención"
                + "única y exclusivamente de la persona y/o entidad a quien va dirigido. La copia, revisión, uso, revelación y/o"
                + "distribución de dicha información confidencial sin la autorización por escrito de " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " está prohibida. "
                + "Si usted no es el destinatario a quien se dirige el presente correo, favor de contactar al remitente respondiendo al presente "
                + "correo y eliminar el correo original incluyendo sus archivos, así como cualesquiera copias del mismo. " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " se "
                + "reserva el derecho de ejercitar las acciones legales que correspondan.</p>"
                + "<br><p style='font-size: 10px'>CONFIDENTIALITY NOTICE: This e-mail message including attachments, if any, is intended only for the "
                + "person or entity to which it is addressed and may contain confidential and /or privileged material. Any review, "
                + "use, disclosure or distribution of such confidential information without the written authorization of " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " is prohibited. "
                + "If you are not the intended recipient, please contact the sender by reply e-mail and destroy all copies of the original message. "
                + "Any breach of the above provisions may entitle " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " to take legal actions.</p>";
        try {
            String envioCorreoExitoso = "";
            if (sisNotificacion.getMailReceptor() != null && !sisNotificacion.getMailReceptor().equals("")) {
                String[] correos = sisNotificacion.getMailReceptor().split(";");
                for (String correo : correos) {
                    envioCorreoExitoso = UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, correo, asunto, detalle, "", listAdjunto, sisNotificacion);
                }
            }
            return envioCorreoExitoso;
        } catch (Exception e) {
            e.printStackTrace();
            return "Algunos correos no pudieron ser enviados: " + e.getMessage();
        }
    }

    @Override
    public String enviarInformacionPedido(String correoUsuario, String notificacion, SisEmpresaNotificaciones idNotificacion, String empresa) throws GeneralException, Exception {
        SisNotificacion sisNotificacion = new SisNotificacion();
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = new SisEmailComprobanteElectronicoTO();
        SisEmpresa sisEmpresaParametros = empresaService.obtenerPorId(empresa);
        String correoUsuarioActivo = "";
        if (idNotificacion != null && idNotificacion.getIdVerificado()) {
            sisEmailComprobanteElectronicoTO.setMailEmisor(idNotificacion.getIdPrincipal());
            sisEmailComprobanteElectronicoTO.setClaveEmisor(idNotificacion.getIdNotificacionesEventos());
        } else {
            throw new GeneralException("Configuración de notificaciones electrónicas no establecida.");
        }
        if (sisEmpresaParametros == null || sisEmpresaParametros.getEmpCodigo() == null) {
            throw new GeneralException("Configuración de notificaciones electrónicas no establecida");
        }
        if (correoUsuario == null || correoUsuario.equals("")) {
            throw new GeneralException("No existen destinatarios.");
        } else {
            //evaluar correos de usuarios activos
            String[] correos = correoUsuario.split(";");
            for (String correo : correos) {
                SisUsuario sisUuario = usuarioService.obtenerPorEmail(correo);
                if (sisUuario != null) {
                    if (sisUuario.getUsrActivo() && !sisUuario.getUsrCodigo().equals("ADM") && !sisUuario.getUsrCodigo().equals("SOPORTE")) {
                        if (correoUsuarioActivo.equals("")) {
                            correoUsuarioActivo = correo;
                        } else {
                            correoUsuarioActivo = correoUsuarioActivo + ";" + correo;
                        }

                    }
                }
            }
        }

        if (correoUsuarioActivo == null || correoUsuarioActivo.equals("")) {
            throw new GeneralException("No existen destinatarios.");
        }

        String respuesta = "";
        sisEmailComprobanteElectronicoTO.setNombreEmisor(sisEmpresaParametros.getEmpNombre());
        sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
        sisNotificacion.setMailPasswordEmisor(sisEmailComprobanteElectronicoTO.getClaveEmisor());
        String asunto = "PEDIDOS DEL SISTEMA ACOSUX";
        String detalle = "<html><head><title></title></head><body>" + "Estimado (a) <br>Reciba un atento saludo de parte de <b>"
                + sisEmpresaParametros.getEmpNombre() + "</b>"
                + "<br><br>Este correo electrónico es para informarle que " + notificacion
                + "<br><br><br>Atentamente" + "<br> " + "<b>" + sisEmpresaParametros.getEmpNombre() + "</b>" + "<br><b>RUC: </b>"
                + sisEmpresaParametros.getEmpRuc() + "<br><b>Direccion: </b> "
                + sisEmpresaParametros.getEmpDireccion()
                + "<br>"
                + "<br>------------------------------------------------------------------------------------------------------"
                + "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>";

        String[] correos = correoUsuarioActivo.split(";");
        for (String correo : correos) {
            sisEmailComprobanteElectronicoTO.setMailReceptor(correo);
            sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());
            respuesta = UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, correo, asunto, detalle, "", new ArrayList<>(), sisNotificacion);
        }
        return respuesta;
    }

    @Override
    public String enviarCorreoParaTicket(String nombreUsuario, String correoUsuario, String asunto, String descripcion, String empresa, List<ArchivoSoporteTO> listaArchivoSoporteTO) throws GeneralException, Exception {
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = new SisEmailComprobanteElectronicoTO();
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        sisEmailComprobanteElectronicoTO.setNombreEmisor(nombreUsuario);
        sisEmailComprobanteElectronicoTO.setMailEmisor(correoUsuario);
        sisEmailComprobanteElectronicoTO.setClaveEmisor(sisEmpresaParametros.getEmpCodigo().getEmpClave());
        sisEmailComprobanteElectronicoTO.setMailReceptor("support@obinte.zendesk.com");

        SisNotificacion sisNotificacion = new SisNotificacion();

        sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
        sisNotificacion.setMailPasswordEmisor(sisEmailComprobanteElectronicoTO.getClaveEmisor());
        sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());

        List<File> listaFiles = new ArrayList<>();
        File carpeta = new File("tmp/");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        for (int i = 0; i < listaArchivoSoporteTO.size(); i++) {
            byte[] decodedString = Base64.getDecoder().decode(listaArchivoSoporteTO.get(i).getBase64().getBytes("UTF-8"));
            listaArchivoSoporteTO.get(i).setNombre("tmp/" + listaArchivoSoporteTO.get(i).getNombre());
            File file = new File(listaArchivoSoporteTO.get(i).getNombre());
            try (OutputStream out = new FileOutputStream(file)) {
                out.write(decodedString);
            }
            listaFiles.add(file);
        }
        return UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, "support@obinte.zendesk.com", asunto, descripcion, "", listaFiles, sisNotificacion);
    }

    @Override
    public boolean comprobarEmails(String destinatarios) throws Exception {
        return UtilsMail.comprobarEmails(destinatarios);
    }
    //CORREO CUENTAS POR COBRAR

    @Override
    public List<String> enviarCorreoParaCuentasPorCobrar(List<CuentasPorCobrarDetalladoTO> lista, String empresa, String asunto, SisEmpresaNotificaciones notificacion) throws GeneralException, Exception {
        List<String> listaRespuestas = new ArrayList<>();
        List<InvClienteCuentasCobrarTO> listaClienteCuentas = new ArrayList<>();

        while (!lista.isEmpty()) {
            List<CuentasPorCobrarDetalladoTO> listaFiltrada = new ArrayList<>();
            lista.stream().filter((cuentasPorCobrarDetalladoTO) -> (cuentasPorCobrarDetalladoTO.getCxcdClienteId().compareTo(lista.get(0).getCxcdClienteId()) == 0)).map((cuenta) -> {
                return cuenta;
            }).forEachOrdered((cuenta) -> {
                listaFiltrada.add(cuenta);
            });

            if (listaFiltrada != null && listaFiltrada.size() > 0) {
                List<CarCuentasPorCobrarTO> listaCuentas = new ArrayList<>();
                InvClienteCuentasCobrarTO clienteCuentaCobrar = new InvClienteCuentasCobrarTO();
                clienteCuentaCobrar.setCliCodigo(listaFiltrada.get(0).getCxcdClienteCodigo());
                clienteCuentaCobrar.setCliEmail(listaFiltrada.get(0).getCxcdClienteEmail());
                clienteCuentaCobrar.setCliNroIdentificador(listaFiltrada.get(0).getCxcdCliente());
                clienteCuentaCobrar.setCliRazonSocial(listaFiltrada.get(0).getCxcdClienteRazonSocial());
                for (CuentasPorCobrarDetalladoTO item : listaFiltrada) {
                    CarCuentasPorCobrarTO cuenta = new CarCuentasPorCobrarTO();
                    cuenta.setEmpresa(empresa);
                    cuenta.setMotivo(item.getCxcdMotivo());
                    cuenta.setNumero(item.getCxcdNumero());
                    cuenta.setNumeroDocumento(item.getCxcdCliente());
                    cuenta.setPeriodo(item.getCxcdPeriodo());
                    cuenta.setSaldo(item.getCxcdSaldo());
                    listaCuentas.add(cuenta);
                }
                clienteCuentaCobrar.setListaCuentas(listaCuentas);
                listaClienteCuentas.add(clienteCuentaCobrar);
            }
            lista.removeAll(listaFiltrada);
        }

        for (InvClienteCuentasCobrarTO clienteCuenta : listaClienteCuentas) {
            String mensajeAux = enviarCorreoParaCuentasPorCobrar(clienteCuenta, empresa, asunto, notificacion);
            listaRespuestas.add(mensajeAux);
        }

        return listaRespuestas;
    }

    public String enviarCorreoParaCuentasPorCobrar(InvClienteCuentasCobrarTO item, String empresa, String asunto, SisEmpresaNotificaciones notificacion) throws GeneralException, Exception {
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = new SisEmailComprobanteElectronicoTO();
        String nombreUsuario = item.getCliRazonSocial();
        String correoUsuario = item.getCliEmail();
        String respuesta = "FHubo un error al envíar el correo del siguiente cliente:  " + nombreUsuario + ", con identificación: " + item.getCliNroIdentificador();

        if (notificacion == null || !notificacion.getIdVerificado()) {
            throw new GeneralException("La configuración de notificaciones no ha sido verificada.");
        }
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        sisEmailComprobanteElectronicoTO.setRucEmisor(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
        if (sisEmpresaParametros.getEmpCodigo().getEmpRuc().equalsIgnoreCase("0903837367001")) {
            sisEmailComprobanteElectronicoTO.setNombreEmisor("AUTOPINTURAS ASSAN");
        } else {
            sisEmailComprobanteElectronicoTO.setNombreEmisor(sisEmpresaParametros.getEmpCodigo().getEmpNombre());
        }
        sisEmailComprobanteElectronicoTO.setMailEmisor(notificacion.getIdPrincipal());
        sisEmailComprobanteElectronicoTO.setClaveEmisor(notificacion.getIdNotificacionesEventos());
        sisEmailComprobanteElectronicoTO.setTelefonoEmisor(sisEmpresaParametros.getEmpCodigo().getEmpTelefono());
        sisEmailComprobanteElectronicoTO.setDireccionEmisor(sisEmpresaParametros.getEmpCodigo().getEmpDireccion());
        sisEmailComprobanteElectronicoTO.setUrlWebDocumentoElectronico(sisEmpresaParametros.getParWebDocumentosElectronicos());
        sisEmailComprobanteElectronicoTO.setNombreReceptor(nombreUsuario);
        sisEmailComprobanteElectronicoTO.setMailReceptor(correoUsuario);
        //Datos para AWS
        sisEmailComprobanteElectronicoTO.setTipoComprobante(TipoNotificacion.NOTIFICAR_CUENTAS_POR_COBRAR.getNombre());//SE CAMBIARA MAS ADELANTE 
        sisEmailComprobanteElectronicoTO.setEmpresa(empresa);
        sisEmailComprobanteElectronicoTO.setMotivo(item.getCliCodigo());
        sisEmailComprobanteElectronicoTO.setNumero("0");

        if (sisEmailComprobanteElectronicoTO.getMailEmisor() == null) {
            throw new GeneralException("Correo del emisor no registrado.");
        } else if (sisEmailComprobanteElectronicoTO.getMailReceptor() == null || sisEmailComprobanteElectronicoTO.getMailReceptor().compareTo("") == 0) {
            throw new GeneralException("Correo del receptor no registrado.");
        } else {
            SisNotificacion sisNotificacion = new SisNotificacion();
            sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
            sisNotificacion.setMailPasswordEmisor(sisEmailComprobanteElectronicoTO.getClaveEmisor());
            sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());
            String cuentasDetalle = "";

            if (item.getListaCuentas() != null && item.getListaCuentas().size() > 0) {
                if (item.getListaCuentas().size() == 1) {
                    String documento = "";
                    if (item.getListaCuentas().get(0).getPeriodo() != null && item.getListaCuentas().get(0).getMotivo() != null && item.getListaCuentas().get(0).getNumero() != null) {
                        documento = item.getListaCuentas().get(0).getPeriodo() + "|" + item.getListaCuentas().get(0).getMotivo() + "|" + item.getListaCuentas().get(0).getNumero();
                    }
                    if (item.getListaCuentas().get(0).getNumeroDocumento() == null || item.getListaCuentas().get(0).getNumeroDocumento().equals("")) {
                        item.getListaCuentas().get(0).setNumeroDocumento(documento + "->" + "999-999-999999999");
                    }
                    cuentasDetalle = "<strong>CUENTA POR COBRAR N° </strong>" + item.getListaCuentas().get(0).getNumeroDocumento() + " <br> <strong>VALOR</strong> $ " + item.getListaCuentas().get(0).getSaldo() + "<br><br>";
                    sisEmailComprobanteElectronicoTO.setPeriodo(item.getListaCuentas().get(0).getNumeroDocumento());
                } else {
                    for (CarCuentasPorCobrarTO cuenta : item.getListaCuentas()) {
                        String documento = "";
                        if (cuenta.getPeriodo() != null && cuenta.getMotivo() != null && cuenta.getNumero() != null) {
                            documento = cuenta.getPeriodo() + "|" + cuenta.getMotivo() + "|" + cuenta.getNumero();
                        }

                        if (cuenta.getNumeroDocumento() == null || cuenta.getNumeroDocumento().equals("")) {
                            cuenta.setNumeroDocumento(documento + "->" + "999-999-999999999");
                        }
                        cuentasDetalle += "<strong>CUENTA POR COBRAR N° </strong>" + cuenta.getNumeroDocumento() + " <br> <strong>VALOR</strong> $ " + cuenta.getSaldo() + "<br><br>";
                    }
                    sisEmailComprobanteElectronicoTO.setPeriodo("0");
                }
            }

            String detalle = "<html><head><title></title></head><body>"
                    + "Estimado (a) " + nombreUsuario
                    + "<br><br>Reciba un atento saludo de parte de " + sisEmailComprobanteElectronicoTO.getNombreEmisor()
                    + "<br><br>Este correo electrónico es para notificarle lo siquiente: <br>"
                    + cuentasDetalle
                    + "<br>Atentamente"
                    + "<br> " + sisEmailComprobanteElectronicoTO.getNombreEmisor()
                    + "<br>RUC: " + sisEmailComprobanteElectronicoTO.getRucEmisor()
                    + "<br>Direccion: " + sisEmailComprobanteElectronicoTO.getDireccionEmisor()
                    + "<br>Telefono: " + sisEmailComprobanteElectronicoTO.getTelefonoEmisor()
                    + "<br>------------------------------------------------------------------------------------------------------"
                    + "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                    + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>"
                    + "<br><p style='font-size: 10px'>AVISO DE CONFIDENCIALIDAD: Este correo electrónico, incluyendo en su caso, los archivos adjuntos al"
                    + "mismo, pueden contener información de carácter confidencial y/o privilegiada, y se envían a la atención"
                    + "única y exclusivamente de la persona y/o entidad a quien va dirigido. La copia, revisión, uso, revelación y/o"
                    + "distribución de dicha información confidencial sin la autorización por escrito de " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " está prohibida. "
                    + "Si usted no es el destinatario a quien se dirige el presente correo, favor de contactar al remitente respondiendo al presente "
                    + "correo y eliminar el correo original incluyendo sus archivos, así como cualesquiera copias del mismo. " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " se "
                    + "reserva el derecho de ejercitar las acciones legales que correspondan.</p>"
                    + "<br><p style='font-size: 10px'>CONFIDENTIALITY NOTICE: This e-mail message including attachments, if any, is intended only for the "
                    + "person or entity to which it is addressed and may contain confidential and /or privileged material. Any review, "
                    + "use, disclosure or distribution of such confidential information without the written authorization of " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " is prohibited. "
                    + "If you are not the intended recipient, please contact the sender by reply e-mail and destroy all copies of the original message. "
                    + "Any breach of the above provisions may entitle " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " to take legal actions.</p>";

            try {
                String res = UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, correoUsuario, asunto, detalle, "", new ArrayList<>(), sisNotificacion);
                if (res.equalsIgnoreCase("Email sent!")) {
                    respuesta = "TSe envió correctamente el correo al siguiente cliente: " + nombreUsuario + ", con identificación: " + item.getCliNroIdentificador();
                } else {
                    respuesta = "F" + res;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return respuesta;
    }

    @Override
    public void notificarPorCorreoDocumentosNoAutorizadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<String> listado, SisInfoTO sisInfoTO, String titulo)
            throws GeneralException, MessagingException, AddressException, IOException, Exception {
        if (listado != null && !listado.isEmpty()) {
            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, usuarioEmpresaReporteTO.getEmpCodigo());
            SisUsuario sisUuario = usuarioService.obtenerPorId(sisInfoTO.getUsuario());
            SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = ConversionesSistema.completarDatosComprobanteElectronicoTO(sisEmpresaParametros);
            sisEmailComprobanteElectronicoTO.setNombreReceptor(sisUuario.getUsrNombre() + " " + sisUuario.getUsrApellido());
            sisEmailComprobanteElectronicoTO.setMailReceptor(sisUuario.getUsrEmail());
            //Datos para AWS
            sisEmailComprobanteElectronicoTO.setTipoComprobante(TipoNotificacion.NOTIFICAR_VENTA_NO_AUTORIZADA.getNombre());
            List<SisEmpresaNotificaciones> notificaciones = sisEmpresaNotificacionesDao.listarSisEmpresaNotificaciones(usuarioEmpresaReporteTO.getEmpCodigo());
            if (notificaciones == null || notificaciones.isEmpty()) {
                throw new GeneralException("Datos del emisor no registrados.");
            }
            sisEmailComprobanteElectronicoTO.setMailEmisor(notificaciones.get(0).getIdPrincipal());
            sisEmailComprobanteElectronicoTO.setClaveEmisor(notificaciones.get(0).getIdNotificacionesEventos());
            SisNotificacion sisNotificacion = ConversionesSistema.completarDatosSisNotificacion(sisEmailComprobanteElectronicoTO);
            if (sisEmailComprobanteElectronicoTO.getMailReceptor() == null || sisEmailComprobanteElectronicoTO.getMailReceptor().compareTo("") == 0) {
                throw new GeneralException("Correo del receptor no registrado.");
            } else {
                String ventasNoAutorizadas = "<div>";
                for (String mensaje : listado) {
                    ventasNoAutorizadas += "<br><li>" + mensaje + "</li>";
                }
                String detalle = "<html><head><title></title></head><body>"
                        + "<br>Estimado usuario: " + sisEmailComprobanteElectronicoTO.getNombreReceptor() + ", usted intentó autorizar documentos de la empresa " + usuarioEmpresaReporteTO.getEmpRazonSocial() + "."
                        + "<br>"
                        + "<br>Este correo electrónico es para notificarle que: "
                        + "<br><strong>Los siguientes documentos no fueron autorizadas como se esperaba.</strong> "
                        + "<br>" + ventasNoAutorizadas + "</div>"
                        + "<br>Considere resolver los inconvenientes e intente nuevamente. "
                        + "<br>Si el problema persiste; contacte a soporte técnico. "
                        + "<br><br>------------------------------------------------------------------------------------------------------"
                        + "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                        + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>"
                        + "<br>";
                List<File> listAdjunto = new ArrayList<>();
                UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, sisEmailComprobanteElectronicoTO.getMailReceptor(), titulo, detalle, "", listAdjunto, sisNotificacion);
            }
            String mensajesDeError = "\n";
            for (String mensaje : listado) {
                mensajesDeError += mensaje + "\n";
            }
            enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(new GeneralException(mensajesDeError), getClass().getName(), "", sisInfoTO));
        }
    }

    @Override
    public String notificarPorCorreoErroresContabilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<File> listado, List<SisUsuarioEmailTO> receptores, int secuencial) throws Exception {
        List<String> respuestasConError = new ArrayList<>();
        if (listado != null && !listado.isEmpty()) {
            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, usuarioEmpresaReporteTO.getEmpCodigo());
            SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = ConversionesSistema.completarDatosComprobanteElectronicoTO(sisEmpresaParametros);
            //Datos para AWS
            sisEmailComprobanteElectronicoTO.setTipoComprobante(TipoNotificacion.NOTIFICAR_CONTABLE_ERRORES.getNombre());
            List<SisEmpresaNotificacionesEventos> eventos = sisEmpresaNotificacionesService.listarSisEventoNotificacion();
            if (eventos == null || eventos.isEmpty()) {
                throw new GeneralException("No hay evento para notificación en la tabla sistemaweb.sis_empresa_notificaciones_eventos.");
            }
            sisEmailComprobanteElectronicoTO.setMailEmisor("alex@obinte.com");
            sisEmailComprobanteElectronicoTO.setClaveEmisor(eventos.get(0).getAwsConfigurationSet());
            SisNotificacion sisNotificacion = ConversionesSistema.completarDatosSisNotificacion(sisEmailComprobanteElectronicoTO);
            for (SisUsuarioEmailTO receptor : receptores) {
                if (receptor.getUsrEmail() == null || receptor.getUsrEmail().compareTo("") == 0) {
                    throw new GeneralException("Correo del receptor no registrado.");
                } else {
                    String detalle = "<html><head><title></title></head><body>"
                            + "<br>Que tal " + receptor.getUsrNombre() + " " + receptor.getUsrApellido() + "."
                            + "<br>"
                            + "<br>Quería notificarle que existen inconsistencias en reporte de verificar errores en contabilidad, en la siguiente empresa: "
                            + "<br>"
                            + "<br>Empresa: <strong>" + usuarioEmpresaReporteTO.getEmpNombre() + ".</strong> "
                            + "<br>" + "</div>"
                            + "<br>Para tu comodidad he generado un reporte PDF por cada inconsistencia el cual adjunto. "
                            + "<br>"
                            + "<br>También puedes revisar este artículo por si deseas más información respecto a este reporte:  "
                            + "<br>"
                            + "<br> https://obinte.zendesk.com/hc/es/articles/360063056373--Qu%C3%A9-significan-cada-una-de-las-pesta%C3%B1as-en-reporte-de-verificaci%C3%B3n-de-contables-con-errores-"
                            + "<br>"
                            + "<br> Puede revisar este reporte en nuestro software contable Acosux siguiendo la ruta: Contabilidad, consultas, Verificación errores en contabilidad.  "
                            + "<br>"
                            + "<br> Esperamos pueda solucionar estas inconsistencias, si requiere de ayuda adicional puede contactarse con soporte técnico."
                            + "<br>"
                            + "<br> Saludos cordiales,"
                            + "<br>"
                            + "<br> Alex de Obinte"
                            + "<br>"
                            + "<br> Asistente Virtual"
                            + "<br><br>------------------------------------------------------------------------------------------------------"
                            + "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                            + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>"
                            + "<br>";
                    sisEmailComprobanteElectronicoTO.setNumero(secuencial + "");
                    sisEmailComprobanteElectronicoTO.setEmpresa(usuarioEmpresaReporteTO.getEmpCodigo());
                    String res = UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, receptor.getUsrEmail(), "Verificación de contables con errores.", detalle, "", listado, sisNotificacion);
                    if (res.equalsIgnoreCase("Email sent!")) {
                    } else {
                        respuestasConError.add(res);
                    }
                }
            }
        }
        if (respuestasConError.size() > 0) {
            return "Al menos un correo no pudo ser enviado: " + respuestasConError.get(0);
        }
        return "T";
    }

    //CORREO PARA ROLES
    public String enviarPFDRol(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO, List<File> listAdjunto, String empId) throws GeneralException, Exception {
        SisNotificacion sisNotificacion = new SisNotificacion();

        sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
        sisNotificacion.setMailPasswordEmisor(sisEmailComprobanteElectronicoTO.getClaveEmisor());
        sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());

        String asunto = sisEmailComprobanteElectronicoTO.getNombreEmisor() + ".  "
                + "Rol de pago: "
                + sisEmailComprobanteElectronicoTO.getNumero();
        String detalle = "<html><head><title></title></head><body>"
                + "Estimado (a) " + sisEmailComprobanteElectronicoTO.getNombreReceptor()
                + "<br><br>Reciba un atento saludo de parte de " + sisEmailComprobanteElectronicoTO.getNombreEmisor()
                + "<br><br>Este correo electrónico es para notificarle la generación del siguiente documento sin validez tributaria: "
                + "<strong>ROL DE PAGO: ° " + sisEmailComprobanteElectronicoTO.getNumero() + " </strong> "
                + "<br><br>Agradecemos su atención."
                + "<br><br><br>Atentamente"
                + "<br> " + sisEmailComprobanteElectronicoTO.getNombreEmisor()
                + "<br>RUC: " + sisEmailComprobanteElectronicoTO.getRucEmisor()
                + "<br>Direccion: " + sisEmailComprobanteElectronicoTO.getDireccionEmisor()
                + "<br>Telefono: " + sisEmailComprobanteElectronicoTO.getTelefonoEmisor()
                + "<br>------------------------------------------------------------------------------------------------------"
                + "<br><p style='font-size: 10px'>* Este correo electrónico ha sido generado por medio del software contable ACOSUX. "
                + "Potenciado por Obinte - Grupo Empresarial. Tel: (07) 2924 090 - (07) 2966 371 - 0981712024 mail: hola@obinte.com</p>"
                + "<br><p style='font-size: 10px'>AVISO DE CONFIDENCIALIDAD: Este correo electrónico, incluyendo en su caso, los archivos adjuntos al"
                + "mismo, pueden contener información de carácter confidencial y/o privilegiada, y se envían a la atención"
                + "única y exclusivamente de la persona y/o entidad a quien va dirigido. La copia, revisión, uso, revelación y/o"
                + "distribución de dicha información confidencial sin la autorización por escrito de " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " está prohibida. "
                + "Si usted no es el destinatario a quien se dirige el presente correo, favor de contactar al remitente respondiendo al presente "
                + "correo y eliminar el correo original incluyendo sus archivos, así como cualesquiera copias del mismo. " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " se "
                + "reserva el derecho de ejercitar las acciones legales que correspondan.</p>"
                + "<br><p style='font-size: 10px'>CONFIDENTIALITY NOTICE: This e-mail message including attachments, if any, is intended only for the "
                + "person or entity to which it is addressed and may contain confidential and /or privileged material. Any review, "
                + "use, disclosure or distribution of such confidential information without the written authorization of " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " is prohibited. "
                + "If you are not the intended recipient, please contact the sender by reply e-mail and destroy all copies of the original message. "
                + "Any breach of the above provisions may entitle " + sisEmailComprobanteElectronicoTO.getNombreEmisor() + " to take legal actions.</p>";
        try {
            String envioCorreoExitoso = "";
            if (sisNotificacion.getMailReceptor() != null && !sisNotificacion.getMailReceptor().equals("")) {
                String[] correos = sisNotificacion.getMailReceptor().split(";");
                if (correos.length > 0) {
                    for (String correo : correos) {
                        boolean enListaNegra = sisEmpresaNotificacionesService.existeCorreoEnListaNegra(correo);
                        if (enListaNegra) {
                            RhEmpleado e = empleadoDao.obtenerPorIdEvict(RhEmpleado.class, new RhEmpleadoPK(sisEmailComprobanteElectronicoTO.getEmpresa(), empId));
                            if (e != null && e.getEmpCorreoElectronico() != null) {
                                String correosDelEmpleado = e.getEmpCorreoElectronico().replaceAll(correo, "");
                                e.setEmpCorreoElectronico(correosDelEmpleado);
                                empleadoDao.actualizar(e);
                            }
                            envioCorreoExitoso = "El correo " + correo + ", tiene problemas con el envío de correos y fue removido del empleado " + empId + ".";
                        } else {
                            envioCorreoExitoso = UtilsMail.envioCorreoPersonalizadoAmazonSES(sisEmailComprobanteElectronicoTO, correo, asunto, detalle, "", listAdjunto, sisNotificacion);
                        }
                    }
                } else {
                    envioCorreoExitoso = "No hay correos validos para el empleado: " + sisEmailComprobanteElectronicoTO.getMotivo();
                }
            }
            return envioCorreoExitoso;
        } catch (Exception e) {
            e.printStackTrace();
            return "Algunos correos no pudieron ser enviados: " + e.getMessage();
        }
    }

    @Override
    public List<String> enviarCorreoRolPorLote(ConContablePK contablepk, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisEmpresaNotificaciones notificacion) throws Exception {
        List<String> listaRespuestas = new ArrayList<>();

        ConContable contable = contableService.getConContable(contablepk);
        List<RhRol> listRol = rolService.getListRhRol(contablepk);

        if (listRol != null && !listRol.isEmpty()) {
            List<RhListaDetalleRolesTO> listaDetalleRolesTO = new ArrayList<>();
            for (RhRol itemRol : listRol) {
                listaDetalleRolesTO.addAll(eliminarDetallesRolesNulos(rolService.getRhDetalleRolesTO(
                        contablepk.getConEmpresa(),
                        UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                        UtilsDate.fechaFormatoString(contable.getConFecha(), "dd-MM-yyyy"),
                        itemRol.getPrdSector().getPrdSectorPK().getSecCodigo(), itemRol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                        itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(), ""), false, null));
            }
            for (RhRol itemRol : listRol) {
                List<ReportesRol> listaRol = new ArrayList<>();
                List<RhListaRolSaldoEmpleadoDetalladoTO> detalleList = rolService.getRhRolSaldoEmpleadoDetalladoTO(
                        contablepk.getConEmpresa(),
                        itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                        UtilsDate.fechaFormatoString(itemRol.getRolDesde(), "dd-MM-yyyy"),
                        UtilsDate.fechaFormatoString(itemRol.getRolHasta(), "dd-MM-yyyy"));
                for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                    if (itemDetalle.getLrpObservaciones() == null || itemDetalle.getLrpObservaciones().equals("")) {
                        itemDetalle.setLrpObservaciones("OBSERVACION GENERAL");
                    }
                    if (itemRol.getRhEmpleado().getRhEmpleadoPK().getEmpId().equalsIgnoreCase(itemDetalle.getLrpId())) {
                        convertirValoresItemDetalleVaciosACero(itemDetalle);
                        if (detalleList == null || detalleList.isEmpty()) {
                            ReportesRol reporte = new ReportesRol(itemDetalle, "", "", "", "", "", null, "", itemRol.getPrdSector().getPrdSectorPK().getSecCodigo());
                            reporte.setEmpresa(contablepk.getConEmpresa());
                            reporte.setEmpCorreoElectronico(itemRol.getRhEmpleado().getEmpCorreoElectronico());
                            listaRol.add(reporte);
                        } else {
                            for (RhListaRolSaldoEmpleadoDetalladoTO detalle : detalleList) {
                                ReportesRol reporte = new ReportesRol(
                                        itemDetalle,
                                        detalle.getSedConcepto(),
                                        detalle.getSedDetalle(),
                                        detalle.getSedCp(),
                                        detalle.getSedCc(),
                                        detalle.getSedFecha(),
                                        detalle.getSedValor(),
                                        detalle.getSedObservaciones(),
                                        itemRol.getRhEmpleado().getPrdSector().getPrdSectorPK().getSecCodigo());
                                reporte.setEmpresa(contablepk.getConEmpresa());
                                reporte.setEmpCorreoElectronico(itemRol.getRhEmpleado().getEmpCorreoElectronico());
                                listaRol.add(reporte);
                            }
                        }
                    }
                }
                if (listaRol.size() > 0) {
                    String mensajeAux = enviarCorreoParaRhRol(listaRol, "ROL DE PAGO", usuarioEmpresaReporteTO, notificacion);
                    listaRespuestas.add(mensajeAux);
                }
            }
        }

        return listaRespuestas;

    }

    @Override
    public List<String> enviarCorreoRol(Integer rolSecuencial, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisEmpresaNotificaciones notificacion) throws Exception {
        List<String> listaRespuestas = new ArrayList<>();
        RhRol rol = rolService.buscarRolSQL(rolSecuencial);

        if (rol != null) {
            List<RhListaDetalleRolesTO> listaDetalleRolesTO = new ArrayList<>();

            listaDetalleRolesTO.addAll(eliminarDetallesRolesNulos(rolService.getRhDetalleRolesTO(
                    rol.getConContable().getConContablePK().getConEmpresa(),
                    UtilsDate.fechaFormatoString(rol.getConContable().getConFecha(), "dd-MM-yyyy"),
                    UtilsDate.fechaFormatoString(rol.getConContable().getConFecha(), "dd-MM-yyyy"),
                    rol.getPrdSector().getPrdSectorPK().getSecCodigo(),
                    rol.getRhEmpleado().getRhCategoria().getRhCategoriaPK().getCatNombre(),
                    rol.getRhEmpleado().getRhEmpleadoPK().getEmpId(), ""), false, null));

            List<ReportesRol> listaRol = new ArrayList<>();
            List<RhListaRolSaldoEmpleadoDetalladoTO> detalleList = rolService.getRhRolSaldoEmpleadoDetalladoTO(
                    rol.getConContable().getConContablePK().getConEmpresa(),
                    rol.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    UtilsDate.fechaFormatoString(rol.getRolDesde(), "dd-MM-yyyy"),
                    UtilsDate.fechaFormatoString(rol.getRolHasta(), "dd-MM-yyyy"));
            for (RhListaDetalleRolesTO itemDetalle : listaDetalleRolesTO) {
                if (itemDetalle.getLrpObservaciones() == null || itemDetalle.getLrpObservaciones().equals("")) {
                    itemDetalle.setLrpObservaciones("OBSERVACION GENERAL");
                }
                if (rol.getRhEmpleado().getRhEmpleadoPK().getEmpId().equalsIgnoreCase(itemDetalle.getLrpId())) {
                    convertirValoresItemDetalleVaciosACero(itemDetalle);
                    if (detalleList == null || detalleList.isEmpty()) {
                        ReportesRol reporte = new ReportesRol(itemDetalle, "", "", "", "", "", null, "",
                                rol.getPrdSector().getPrdSectorPK().getSecCodigo());
                        reporte.setEmpresa(rol.getConContable().getConContablePK().getConEmpresa());
                        reporte.setEmpCorreoElectronico(rol.getRhEmpleado().getEmpCorreoElectronico());
                        listaRol.add(reporte);
                    } else {
                        for (RhListaRolSaldoEmpleadoDetalladoTO detalle : detalleList) {
                            ReportesRol reporte = new ReportesRol(
                                    itemDetalle,
                                    detalle.getSedConcepto(),
                                    detalle.getSedDetalle(),
                                    detalle.getSedCp(),
                                    detalle.getSedCc(),
                                    detalle.getSedFecha(),
                                    detalle.getSedValor(),
                                    detalle.getSedObservaciones(),
                                    rol.getRhEmpleado().getPrdSector().getPrdSectorPK().getSecCodigo());
                            reporte.setEmpresa(rol.getConContable().getConContablePK().getConEmpresa());
                            reporte.setEmpCorreoElectronico(rol.getRhEmpleado().getEmpCorreoElectronico());
                            listaRol.add(reporte);
                        }
                    }
                }
            }
            if (listaRol.size() > 0) {
                String mensajeAux = enviarCorreoParaRhRol(listaRol, "ROL DE PAGO", usuarioEmpresaReporteTO, notificacion);
                listaRespuestas.add(mensajeAux);
            }

        }

        return listaRespuestas;

    }

    public String enviarCorreoParaRhRol(List<ReportesRol> listaRol, String asunto, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisEmpresaNotificaciones notificacion) throws Exception {
        List<File> listAdjunto = new ArrayList<>();
        ReportesRol reporte = listaRol.get(0);
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = new SisEmailComprobanteElectronicoTO();
        String nombreUsuario = reporte.getNombres();
        String correoUsuario = reporte.getEmpCorreoElectronico();
        String respuesta = "T";

        if (notificacion == null || !notificacion.getIdVerificado()) {
            respuesta = "FLa configuración de notificaciones no ha sido verificada.";
        }
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, reporte.getEmpresa());
        sisEmailComprobanteElectronicoTO.setRucEmisor(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
        if (sisEmpresaParametros.getEmpCodigo().getEmpRuc().equalsIgnoreCase("0903837367001")) {
            sisEmailComprobanteElectronicoTO.setNombreEmisor("AUTOPINTURAS ASSAN");
        } else {
            sisEmailComprobanteElectronicoTO.setNombreEmisor(sisEmpresaParametros.getEmpCodigo().getEmpNombre());
        }
        sisEmailComprobanteElectronicoTO.setMailEmisor(notificacion.getIdPrincipal());
        sisEmailComprobanteElectronicoTO.setClaveEmisor(notificacion.getIdNotificacionesEventos());
        sisEmailComprobanteElectronicoTO.setTelefonoEmisor(sisEmpresaParametros.getEmpCodigo().getEmpTelefono());
        sisEmailComprobanteElectronicoTO.setDireccionEmisor(sisEmpresaParametros.getEmpCodigo().getEmpDireccion());
        sisEmailComprobanteElectronicoTO.setUrlWebDocumentoElectronico(sisEmpresaParametros.getParWebDocumentosElectronicos());
        sisEmailComprobanteElectronicoTO.setNombreReceptor(nombreUsuario);
        sisEmailComprobanteElectronicoTO.setMailReceptor(correoUsuario);
        //Datos para AWS
        sisEmailComprobanteElectronicoTO.setTipoComprobante(TipoNotificacion.NOTIFICAR_ROL_PAGOS.getNombre());//SE CAMBIARA MAS ADELANTE 
        sisEmailComprobanteElectronicoTO.setEmpresa(reporte.getEmpresa());
        sisEmailComprobanteElectronicoTO.setMotivo(reporte.getCedula() + "-" + reporte.getNombres());
        sisEmailComprobanteElectronicoTO.setMotivo(sisEmailComprobanteElectronicoTO.getMotivo().replace(" ", "_").replace("ñ", "n").replace("Ñ", "N"));
        sisEmailComprobanteElectronicoTO.setNumero(reporte.getComprobante().replace("|", "_").replace(" ", "").replace("ñ", "n").replace("Ñ", "N"));

        if (sisEmailComprobanteElectronicoTO.getMailEmisor() == null) {
            respuesta = "FCorreo del emisor no registrado.";
        } else if (sisEmailComprobanteElectronicoTO.getMailReceptor() == null || sisEmailComprobanteElectronicoTO.getMailReceptor().compareTo("") == 0) {
            respuesta = "FCorreo no registrado del siguiente trabajador: " + nombreUsuario + ", con identificación: " + reporte.getCedula();
        } else {
            SisNotificacion sisNotificacion = new SisNotificacion();
            sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
            sisNotificacion.setMailPasswordEmisor(sisEmailComprobanteElectronicoTO.getClaveEmisor());
            sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());

            File filePDF = genericReporteService.generarFile("RRHH", "reportComprobanteRol.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaRol);
            if (filePDF == null) {
                respuesta = "FOcurrió un error al generar el documento a enviar, para el rol de pago: " + reporte.getComprobante();
            }

            File file = genericReporteService.respondeServidorCorreo(filePDF, "Rol_" + reporte.getCedula());
            if (file == null) {
                respuesta = "FOcurrió un error al generar el archivo de rol de pago: " + reporte.getComprobante();
            } else {
                listAdjunto.add(file);
            }

            try {
                if (respuesta.substring(0).equals("T")) {
                    String res = enviarPFDRol(sisEmailComprobanteElectronicoTO, listAdjunto, reporte.getCedula());
                    if (res.equalsIgnoreCase("Email sent!")) {
                        respuesta = "TSe envió correctamente el correo al siguiente trabajador: " + nombreUsuario + ", con identificación: " + reporte.getCedula();
                    } else {
                        respuesta = "F" + res;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return respuesta;
    }

    public List<RhListaDetalleRolesTO> eliminarDetallesRolesNulos(List<RhListaDetalleRolesTO> listaRhListaDetalleRolesTO, boolean evaluarContable, ConContablePK pk) {
        List<RhListaDetalleRolesTO> listaFinal = new ArrayList<>();
        for (RhListaDetalleRolesTO item : listaRhListaDetalleRolesTO) {
            if (item.getLrpContable() != null) {
                if (evaluarContable) {
                    String[] pkcontable = item.getLrpContable().split("\\|");
                    String periodo = pkcontable[0] != null ? pkcontable[0].trim() : null;
                    String tipo = pkcontable[1] != null ? pkcontable[1].trim() : null;
                    String numero = pkcontable[2] != null ? pkcontable[2].trim() : null;

                    if (pk != null && pk.getConPeriodo().equals(periodo) && pk.getConTipo().equals(tipo) && pk.getConNumero().equals(numero)) {
                        listaFinal.add(item);
                    }
                } else {
                    listaFinal.add(item);
                }
            }
        }
        return listaFinal;
    }

    @Override
    public List<String> enviarCorreoFacturasPorLote(List<AnxListaVentaElectronicaTO> listaEnviar, String empresa, SisInfoTO sisInfoTO) throws Exception {
        List<String> erroresAlEnviar = new ArrayList<>();

        for (AnxListaVentaElectronicaTO factura : listaEnviar) {
            String nombreReporte = "";
            if (factura.getVtaDocumento_tipo().equalsIgnoreCase("18")) {
                nombreReporte = "reportComprobanteFacturaRide";
            } else if (factura.getVtaDocumento_tipo().equalsIgnoreCase("05")) {
                nombreReporte = "reportComprobanteNotaDebitoRide";
            } else if (factura.getVtaDocumento_tipo().equalsIgnoreCase("04")) {
                nombreReporte = "reportComprobanteNotaCreditoRide";
            }

            String mensajeAux;
            try {
                String xmlAutorizacion = ventaElectronicaService.getXmlComprobanteElectronico(empresa, factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero());
                String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
                mensajeAux = compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, factura.getVtaPeriodo(), factura.getVtaMotivo(), factura.getVtaNumero(), claveAcceso, nombreReporte, xmlAutorizacion, sisInfoTO);
            } catch (GeneralException e) {
                mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
            } catch (Exception e) {
                mensajeAux = e.getMessage() != null ? "F" + e.getMessage() : null;
            }
            mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico " + factura.getVtaDocumento_tipo() + ":" + factura.getVtaDocumentoNumero()) : mensajeAux;
            erroresAlEnviar.add(factura.getVtaDocumentoNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(0) : "Error desconocido."));
        }

        return erroresAlEnviar;
    }

    @Override
    public List<String> enviarCorreoGuiasPorLote(List<AnxListaGuiaRemisionElectronicaTO> listaEnviar, String empresa) throws Exception {
        List<String> erroresAlEnviar = new ArrayList<>();
        for (AnxListaGuiaRemisionElectronicaTO item : listaEnviar) {
            String nombreReporte = "reportComprobanteGuiaRemisionRide";

            String xmlAutorizacion = guiaElectronicaService.getXmlGuiaRemision(empresa, item.getGuiaPeriodo(), item.getGuiaNumero());
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensajeAux = guiaElectronicaService.enviarEmailComprobantesElectronicos(empresa, item.getGuiaPeriodo(), item.getGuiaNumero(), claveAcceso, nombreReporte, xmlAutorizacion);

            mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico "
                    + item.getGuiaDocumentoNumero()) : mensajeAux;
            erroresAlEnviar.add(item.getGuiaDocumentoNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(0) : "Error desconocido."));

        }
        return erroresAlEnviar;
    }

    @Override
    public List<String> enviarCorreoRetencionesPorLote(List<AnxListadoCompraElectronicaTO> listaEnviar, String empresa, SisInfoTO sisInfoTO) throws Exception {
        List<String> erroresAlEnviar = new ArrayList<>();
        for (AnxListadoCompraElectronicaTO item : listaEnviar) {
            String periodo = item.getCompPeriodo();
            String motivo = item.getCompMotivo();
            String numero = item.getCompNumero();
            String xmlAutorizacion = compraElectronicaService.getXmlComprobanteRetencion(empresa, periodo, motivo, numero);
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensajeAux = compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, periodo, motivo, numero, claveAcceso, "reportComprobanteRetencionRide", xmlAutorizacion, sisInfoTO);
            mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico "
                    + item.getCompNumero()) : mensajeAux;
            erroresAlEnviar.add(item.getCompNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(0) : "Error desconocido."));

        }
        return erroresAlEnviar;
    }

    @Override
    public List<String> enviarCorreoLiquidacionesPorLote(List<AnxListaLiquidacionComprasElectronicaTO> listaEnviar, String empresa, SisInfoTO sisInfoTO) throws Exception {
        List<String> erroresAlEnviar = new ArrayList<>();
        for (AnxListaLiquidacionComprasElectronicaTO item : listaEnviar) {
            String nombreReporte = "";
            nombreReporte = "reportComprobanteLiquidacionCompraRide";
            String xmlAutorizacion = compraElectronicaService.getXmlLiquidacionCompras(empresa, item.getCompPeriodo(), item.getCompMotivo(), item.getCompNumero());
            String claveAcceso = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<claveAcceso>") + 13, xmlAutorizacion.lastIndexOf("</claveAcceso>")).trim();
            String mensajeAux = compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, item.getCompPeriodo(), item.getCompMotivo(), item.getCompNumero(), claveAcceso, nombreReporte, xmlAutorizacion, sisInfoTO);

            mensajeAux = (mensajeAux == null) ? ("FError inesperado en el comprobante electrónico "
                    + item.getCompNumero()) : mensajeAux;
            erroresAlEnviar.add(item.getCompNumero() + " --> " + (mensajeAux != null ? mensajeAux.substring(0) : "Error desconocido."));

        }

        return erroresAlEnviar;
    }

}
