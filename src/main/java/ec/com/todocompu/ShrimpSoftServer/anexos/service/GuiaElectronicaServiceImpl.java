/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.GuiaRemisionElectronicaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.GuiaRemisionService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.ArchivoUtils;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.GenerarComprobantePdf;
import ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoComprobanteEnum;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class GuiaElectronicaServiceImpl implements GuiaElectronicaService {

    @Autowired
    private GuiaRemisionService guiaRemisionService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private GuiaRemisionElectronicaDao guiaRemisionElectronicaDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    private boolean comprobar = false;
    private String mensaje;
    @Autowired
    private VentaDao ventaDao;

    @Override
    public AnxGuiaRemisionElectronica buscarAnxGuiaRemisionElectronica(String empresa, String periodo, String numero) throws Exception {
        return guiaRemisionElectronicaDao.buscarAnxGuiaRemisionElectronica(empresa, periodo, numero);
    }

    @Override
    public List<AnxListaGuiaRemisionPendientesTO> getListaGuiaRemisionPendientesTO(String empresa) throws Exception {
        return guiaRemisionElectronicaDao.getListaGuiaRemisionPendientesTO(empresa);
    }

    @Override
    public List<AnxListaGuiaRemisionElectronicaTO> getListaAnxListaGuiaRemisionElectronicaTO(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        return guiaRemisionElectronicaDao.getListaAnxListaGuiaRemisionElectronicaTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxGuiaRemisionElectronicaNotificaciones> listarNotificacionesGuiasElectronicas(String empresa, String periodo, String numero) throws Exception {
        String query = "SELECT * FROM anexo.anx_guia_remision_electronica_notificaciones n"
                + " WHERE n.guia_empresa = '" + empresa + "' AND n.guia_periodo = '" + periodo + "' AND n.guia_numero = '" + numero + "'  order by n.e_fecha;";
        List<AnxGuiaRemisionElectronicaNotificaciones> notificaciones = genericSQLDao.obtenerPorSql(query, AnxGuiaRemisionElectronicaNotificaciones.class);
        return notificaciones;
    }

    @Override
    public String accionAnxGuiaRemisionTOElectronica(AnxGuiaRemisionElectronicaTO anxGuiaRemisionElectronicaTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        anxGuiaRemisionElectronicaTO.setUsrCodigo(sisInfoTO.getUsuario());
        comprobar = false;
        boolean periodoCerrado = false;
        List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(anxGuiaRemisionElectronicaTO.getGuiaEmpresa());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(anxGuiaRemisionElectronicaTO.getGuiaFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(anxGuiaRemisionElectronicaTO.getGuiaFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                anxGuiaRemisionElectronicaTO.setGuiaPeriodo(sisListaPeriodoTO.getPerCodigo());
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (!periodoCerrado) {
            susClave = "CE " + anxGuiaRemisionElectronicaTO.getGuiaPeriodo() + " " + anxGuiaRemisionElectronicaTO.getGuiaNumero();
            if (accion == 'I') {
                susDetalle = "Se insertó comprobante electrónica autorizados: " + " Tipo : Guía de remisión"
                        + " ,Clave de Autorizacion: " + anxGuiaRemisionElectronicaTO.geteAutorizacionNumero()
                        + " ,Clave de Acceso" + anxGuiaRemisionElectronicaTO.geteClaveAcceso();
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "Se optienen la autorizados: " + " Tipo : Guía de remisión" + " ,Clave de Autorizacion: "
                        + anxGuiaRemisionElectronicaTO.geteAutorizacionNumero() + " ,Clave de Acceso"
                        + anxGuiaRemisionElectronicaTO.geteClaveAcceso();
                susSuceso = "UPDATE";
            }

            susTabla = "anexo.anx_venta_electronica";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            AnxGuiaRemisionElectronica anxVentaElectronica = ConversionesAnexos.convertirAnxGuiaRemisionElectronicaTO_AnxGuiaRemisionElectronica(anxGuiaRemisionElectronicaTO);
            if (accion == 'I') {
                comprobar = guiaRemisionElectronicaDao.accionAnxGuiaRemisionElectronica(anxVentaElectronica, sisSuceso, accion);
            }
            if (accion == 'M') {
                if (guiaRemisionElectronicaDao.comprobarAnxGuiaRemisionElectronica(anxGuiaRemisionElectronicaTO.getUsrEmpresa().trim(),
                        anxGuiaRemisionElectronicaTO.getGuiaPeriodo().trim(),
                        anxGuiaRemisionElectronicaTO.getGuiaNumero().trim())) {

                    AnxGuiaRemisionElectronica anxGuiaElectronicaAux = guiaRemisionElectronicaDao.buscarAnxGuiaRemisionElectronica(
                            anxGuiaRemisionElectronicaTO.getUsrEmpresa().trim(),
                            anxGuiaRemisionElectronicaTO.getGuiaPeriodo().trim(),
                            anxGuiaRemisionElectronicaTO.getGuiaNumero().trim());

                    anxVentaElectronica.setUsrFechaInserta(anxGuiaElectronicaAux.getUsrFechaInserta());
                    anxVentaElectronica.seteSecuencial(anxGuiaElectronicaAux.geteSecuencial());
                    comprobar = guiaRemisionElectronicaDao.accionAnxGuiaRemisionElectronica(anxVentaElectronica, sisSuceso,
                            accion);

                } else {
                    comprobar = false;
                }
            }

            if (comprobar) {
                if (accion == 'I') {
                    mensaje = "TEl comprobante fue autorizado por el SRI y guardado Correctamente...";
                }
                if (accion == 'M') {
                    mensaje = "TEl comprobante fue autorizado por el SRI y guardado Correctamente...";
                }
            } else {
                mensaje = "FNo se encuentra la guía de remisión Electrónica...";
            }

        } else {
            mensaje = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
        }
        return mensaje;
    }

    @Override
    public boolean comprobarAnxGuiaElectronica(String empresa, String periodo, String numero) throws Exception {
        return guiaRemisionElectronicaDao.comprobarAnxGuiaRemisionElectronica(empresa, periodo, numero);
    }

    @Override
    public String enviarEmailComprobantesElectronicos(String empresa, String ePeriodo, String eNumero, String claveAcceso, String nombreReporteJasper, String XmlString) throws Exception {
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = new SisEmailComprobanteElectronicoTO();
        String nombreReceptor = "";
        String mailReceptor = "";
        String cIReceptor = "";

        if (XmlString == null) {
            mensaje = "FNo se encuentra el XML de la Retencion Electrónica...";
        } else {
            String numeroComprobante = XmlString.substring(XmlString.lastIndexOf("<estab>") + 7, XmlString.lastIndexOf("</estab>")).trim()
                    + "-" + XmlString.substring(XmlString.lastIndexOf("<ptoEmi>") + 8, XmlString.lastIndexOf("</ptoEmi>")).trim()
                    + "-" + XmlString.substring(XmlString.lastIndexOf("<secuencial>") + 12, XmlString.lastIndexOf("</secuencial>")).trim();
            String tipoComprobante = XmlString.substring(XmlString.lastIndexOf("<claveAcceso>") + 13, XmlString.lastIndexOf("</claveAcceso>")).trim().substring(8, 10);
            InvGuiaRemision invGuiaRemision = guiaRemisionService.buscarInvGuiaRemision(empresa, ePeriodo, eNumero);
            InvCliente invCliente = invGuiaRemision.getInvCliente();
            nombreReceptor = invCliente.getCliRazonSocial();
            mailReceptor = invCliente.getCliEmail();
            cIReceptor = invCliente.getCliIdNumero() == null ? "9999999999999" : invCliente.getCliIdNumero();

            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            sisEmailComprobanteElectronicoTO.setRucEmisor(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
            if (sisEmpresaParametros.getEmpCodigo().getEmpRuc().equalsIgnoreCase("0903837367001")) {
                sisEmailComprobanteElectronicoTO.setNombreEmisor("AUTOPINTURAS ASSAN");
            } else {
                sisEmailComprobanteElectronicoTO.setNombreEmisor(sisEmpresaParametros.getEmpCodigo().getEmpNombre());
            }
            //
            AnxNumeracion numeracion = ventaDao.obtenerNumeracionPorTipoYNumeroDocumento(empresa, tipoComprobante, numeroComprobante);
            if (numeracion != null && numeracion.getIdNotificaciones() != null && numeracion.getIdNotificaciones().getIdVerificado()) {
                SisEmpresaNotificaciones notificacion = numeracion.getIdNotificaciones();
                sisEmailComprobanteElectronicoTO.setMailEmisor(notificacion.getIdPrincipal());//
                sisEmailComprobanteElectronicoTO.setClaveEmisor(notificacion.getIdNotificacionesEventos());
            } else {
                sisEmailComprobanteElectronicoTO.setClaveEmisor(null);
            }

            sisEmailComprobanteElectronicoTO.setTelefonoEmisor(sisEmpresaParametros.getEmpCodigo().getEmpTelefono());
            sisEmailComprobanteElectronicoTO.setDireccionEmisor(sisEmpresaParametros.getEmpCodigo().getEmpDireccion());
            sisEmailComprobanteElectronicoTO.setUrlWebDocumentoElectronico(sisEmpresaParametros.getParWebDocumentosElectronicos());
            sisEmailComprobanteElectronicoTO.setNombreReceptor(nombreReceptor);
            sisEmailComprobanteElectronicoTO.setMailReceptor(mailReceptor);
            sisEmailComprobanteElectronicoTO.setNumeroComprobante(numeroComprobante);
            sisEmailComprobanteElectronicoTO.setTipoComprobante(TipoComprobanteEnum.GUIA_DE_REMISION.getDescripcion());
            sisEmailComprobanteElectronicoTO.setClaveAcceso(claveAcceso);
            sisEmailComprobanteElectronicoTO.setEmpresa(empresa);
            sisEmailComprobanteElectronicoTO.setPeriodo(ePeriodo);
            sisEmailComprobanteElectronicoTO.setMotivo("00");
            sisEmailComprobanteElectronicoTO.setNumero(eNumero);
            
            if (sisEmailComprobanteElectronicoTO.getClaveEmisor() == null) {
                mensaje = "FCONFIGURACIÓN DE NOTIFICACIONES NO ESTABLECIDA.";
            } else if (sisEmailComprobanteElectronicoTO.getMailEmisor() == null) {
                mensaje = "FCORREO NO REGISTRADO DEL EMISOR";
            } else if (sisEmailComprobanteElectronicoTO.getMailReceptor() == null || sisEmailComprobanteElectronicoTO.getMailReceptor().compareTo("") == 0) {
                mensaje = "FCORREO DEL RECEPTOR NO REGISTRADO";
            } else if (cIReceptor.compareTo("9999999999999") == 0) {
                mensaje = "FNO SE PUDE NOTIFICAR A UN CONSUMIDOR FINAL";
            } else {
                File fileXML = ArchivoUtils.stringToArchivoEmail(UtilsArchivos.getRutaComprobnate() + claveAcceso + ".xml", XmlString);
                List<File> listAdjunto = new ArrayList<>();
                File filePDF = GenerarComprobantePdf.generarReporteComprobanteElectronicoPDFEmail(empresa, claveAcceso, XmlString, nombreReporteJasper, sisEmpresaParametros.getParRutaReportes(), sisEmpresaParametros.getParRutaLogo());
                listAdjunto.add(fileXML);
                listAdjunto.add(filePDF);
                mensaje = envioCorreoService.enviarComprobantesElectronicos(sisEmailComprobanteElectronicoTO, listAdjunto);
                if (mensaje.lastIndexOf("Email sent!") == -1) {
                    mensaje = "F" + mensaje;
                } else {
                    mensaje = "T" + mailReceptor;
                }
            }
        }
        return mensaje;
    }

    @Override
    public String getXmlGuiaRemision(String empresa, String ePeriodo, String eNumero) throws Exception {
        return guiaRemisionElectronicaDao.getXmlComprobanteElectronico(empresa, ePeriodo, eNumero);
    }

    @Override
    public InvEntidadTransaccionTO obtenerClienteDeGuia(String empresa, String periodo, String numero) throws Exception {
        InvGuiaRemision invGuiaRemision = guiaRemisionService.buscarInvGuiaRemision(empresa, periodo, numero);
        if (invGuiaRemision != null) {
            InvEntidadTransaccionTO entidadTransaccion = new InvEntidadTransaccionTO();
            entidadTransaccion.setDocumento(invGuiaRemision.getGuiaDocumentoNumero());
            entidadTransaccion.setTipo("Factura");//COORDINAR
            entidadTransaccion.setRazonSocial(invGuiaRemision.getInvCliente().getCliRazonSocial());
            entidadTransaccion.setIdentificacion(invGuiaRemision.getInvCliente().getCliIdNumero());
            return entidadTransaccion;
        }
        return null;
    }

}
