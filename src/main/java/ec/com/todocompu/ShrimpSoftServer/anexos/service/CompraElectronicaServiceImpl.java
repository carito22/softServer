package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraElectronicaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProveedorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.ArchivoUtils;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.GenerarComprobantePdf;
import ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoComprobanteEnum;

@Service
public class CompraElectronicaServiceImpl implements CompraElectronicaService {

    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @Autowired
    private CompraDao compraDao;

    @Autowired
    private CompraElectronicaDao compraElectronicaDao;

    @Autowired
    private ComprasService comprasService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private EnviarCorreoService envioCorreoService;

    @Autowired
    private VentasDao ventasDao;

    @Autowired
    private VentaDao ventaDao;

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;
    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    private boolean comprobar = false;
    private String mensaje;

    @Override
    public String accionAnxCompraElectronica(AnxCompraElectronicaTO anxCompraElectronicaTO, char accion,
            SisInfoTO sisInfoTO) throws Exception {
        anxCompraElectronicaTO.setUsrCodigo(sisInfoTO.getUsuario());
        comprobar = false;
        susClave = "CE " + anxCompraElectronicaTO.getCompPeriodo() + " " + anxCompraElectronicaTO.getCompMotivo()
                + " " + anxCompraElectronicaTO.getCompNumero();
        if (accion == 'I') {
            susDetalle = "Se insertó Comprobante Electronica Autorizados: " + " Estado : "
                    + anxCompraElectronicaTO.geteEstado() + " Tipo : Retencion" + " ,Tipo Ambiente: "
                    + anxCompraElectronicaTO.geteTipoAmbiente() + " ,Clave de Autorizacion: "
                    + anxCompraElectronicaTO.geteAutorizacionNumero() + " ,Clave de Acceso"
                    + anxCompraElectronicaTO.geteClaveAcceso();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se Actualizo Comprobante Electronica : " + " Estado : "
                    + anxCompraElectronicaTO.geteEstado() + " Tipo : Retencion" + " ,Tipo Ambiente: "
                    + anxCompraElectronicaTO.geteTipoAmbiente() + " ,Clave de Autorizacion: "
                    + anxCompraElectronicaTO.geteAutorizacionNumero() + " ,Clave de Acceso"
                    + anxCompraElectronicaTO.geteClaveAcceso();
            susSuceso = "UPDATE";
        }
        susTabla = "anexo.anx_compra_electronica";
        AnxCompraElectronica anxCompraElectronica = ConversionesAnexos
                .convertirAnxVentaElectronicaTO_AnxVentaElectronica(anxCompraElectronicaTO);

        AnxCompra anxCompra = null;
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        // String empresa, String periodo, String motivo, String
        // numeroCompra
        if (anxCompraElectronicaTO.geteEstado().equals("AUTORIZADO")
                && anxCompraElectronicaTO.geteTipoAmbiente().equals("PRODUCCION")) {
            anxCompra = ConversionesAnexos.convertirAnxCompra_AnxCompra(compraDao.obtenerPorId(AnxCompra.class,
                    new AnxCompraPK(anxCompraElectronicaTO.getCompEmpresa(),
                            anxCompraElectronicaTO.getCompPeriodo(), anxCompraElectronicaTO.getCompMotivo(),
                            anxCompraElectronicaTO.getCompNumero())));
            anxCompra.setCompRetencionAutorizacion(anxCompraElectronicaTO.geteAutorizacionNumero());

        }
        if (accion == 'I') {
            if (!compraElectronicaDao.comprobarAnxCompraElectronica(anxCompraElectronicaTO.getCompEmpresa(),
                    anxCompraElectronicaTO.getCompPeriodo(), anxCompraElectronicaTO.getCompMotivo(),
                    anxCompraElectronicaTO.getCompNumero())) {
                comprobar = compraElectronicaDao.accionAnxCompraElectronica(anxCompraElectronica, anxCompra,
                        sisSuceso, accion);
            } else {
                mensaje = "FYa Existe un registro de Compra Electrónica \nContacte al administrador ...";
            }
        }
        if (accion == 'M') {
            if (compraElectronicaDao.comprobarAnxCompraElectronica(anxCompraElectronicaTO.getCompEmpresa(),
                    anxCompraElectronicaTO.getCompPeriodo(), anxCompraElectronicaTO.getCompMotivo(),
                    anxCompraElectronicaTO.getCompNumero())) {
                AnxCompraElectronica anxCompraElectronicaAux = compraElectronicaDao.buscarAnxCompraElectronica(
                        anxCompraElectronicaTO.getCompEmpresa(), anxCompraElectronicaTO.getCompPeriodo(),
                        anxCompraElectronicaTO.getCompMotivo(), anxCompraElectronicaTO.getCompNumero());

                anxCompraElectronica.setUsrFechaInserta(anxCompraElectronicaAux.getUsrFechaInserta());
                anxCompraElectronica.setESecuencial(anxCompraElectronicaAux.getESecuencial());
                comprobar = compraElectronicaDao.accionAnxCompraElectronica(anxCompraElectronica, anxCompra,
                        sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra la Compra Electronicica...";
            }
        }
        if (comprobar) {
            if (accion == 'I') {
                mensaje = "TEl comprobante fue " + anxCompraElectronicaTO.geteEstado() + " por el SRI...";
            }
            if (accion == 'M') {
                mensaje = "TEl comprobante se descargo y fue " + anxCompraElectronicaTO.geteEstado()
                        + " por el SRI...";
            }
        }
        return mensaje;
    }

    @Override
    public String getXmlComprobanteRetencion(String empresa, String ePeriodo, String eMotivo, String eNumero)
            throws Exception {
        return compraElectronicaDao.getXmlComprobanteRetencion(empresa, ePeriodo, eMotivo, eNumero);
    }

    @Override
    public boolean comprobarAnxCompraElectronica(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return compraElectronicaDao.comprobarAnxCompraElectronica(empresa, periodo, motivo, numero);
    }

    @Override
    public boolean comprobarRetencionAutorizadaProcesamiento(String empresa, String periodo, String motivo,
            String numero) throws Exception {
        return compraElectronicaDao.comprobarRetencionAutorizadaProcesamiento(empresa, periodo, motivo, numero);

    }

    @Override
    public String enviarEmailComprobantesElectronicos(String empresa, String ePeriodo, String eMotivo, String eNumero,
            String claveAcceso, String nombreReporteJasper, String XmlString, SisInfoTO sisInfoTO) throws Exception {
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = new SisEmailComprobanteElectronicoTO();
        String nombreReceptor = "";
        String mailReceptor = "";
        String cIReceptor = "";

        if (XmlString == null) {
            mensaje = "FNo se encuentra el XML de la Retencion Electrónica...";
        } else {
            String numeroComprobante = XmlString
                    .substring(XmlString.lastIndexOf("<estab>") + 7, XmlString.lastIndexOf("</estab>")).trim()
                    + "-" + XmlString.substring(XmlString.lastIndexOf("<ptoEmi>") + 8, XmlString.lastIndexOf("</ptoEmi>")).trim()
                    + "-" + XmlString.substring(XmlString.lastIndexOf("<secuencial>") + 12, XmlString.lastIndexOf("</secuencial>")).trim();

            String tipoComprobante = XmlString.substring(XmlString.lastIndexOf("<claveAcceso>") + 13, XmlString.lastIndexOf("</claveAcceso>")).trim().substring(8, 10);
            AnxNumeracion numeracion;
            if (tipoComprobante.compareTo("07") == 0 || tipoComprobante.compareTo("03") == 0) {
                numeracion = ventaDao.obtenerNumeracionPorTipoYNumeroDocumento(empresa, tipoComprobante, numeroComprobante);
                InvComprasTO invCompraCabeceraTO = comprasService.getComprasTO(empresa, ePeriodo, eMotivo, eNumero);
                InvProveedor invProveedor = proveedorService.buscarInvProveedor(empresa, invCompraCabeceraTO.getProvCodigo());
                nombreReceptor = invProveedor.getProvRazonSocial();
                mailReceptor = invProveedor.getProvEmail();
                cIReceptor = invProveedor.getProvIdNumero();
                if (tipoComprobante.compareTo("03") == 0) {
                    tipoComprobante = TipoComprobanteEnum.LIQUIDACION_DE_COMPRAS.getDescripcion();
                    sisEmailComprobanteElectronicoTO.setValor(invCompraCabeceraTO.getCompTotal());
                } else {
                    tipoComprobante = TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getDescripcion();
                    sisEmailComprobanteElectronicoTO.setValor(invCompraCabeceraTO.getCompValorRetenido());
                }
            } else {
                InvVentas invVentas = ventasDao.buscarInvVentas(empresa, ePeriodo, eMotivo, eNumero);
                numeracion = ventaDao.obtenerNumeracionPorTipoYNumeroDocumento(empresa, invVentas.getVtaDocumentoTipo(), numeroComprobante);
                sisEmailComprobanteElectronicoTO.setValor(invVentas.getVtaTotal());
                InvCliente invCliente = clienteDao.buscarInvCliente(empresa, invVentas.getInvCliente().getInvClientePK().getCliCodigo());
                nombreReceptor = invCliente.getCliRazonSocial();
                mailReceptor = invCliente.getCliEmail();
                cIReceptor = invCliente.getCliIdNumero() == null ? "9999999999999" : invCliente.getCliIdNumero();
                if (invVentas.getVtaDocumentoTipo().compareTo("18") == 0 || invVentas.getVtaDocumentoTipo().compareTo("41") == 0) {
                    tipoComprobante = TipoComprobanteEnum.FACTURA.getDescripcion();
                } else if (invVentas.getVtaDocumentoTipo().compareTo("05") == 0) {
                    tipoComprobante = TipoComprobanteEnum.NOTA_DE_DEBITO.getDescripcion();
                } else if (invVentas.getVtaDocumentoTipo().compareTo("04") == 0) {
                    tipoComprobante = TipoComprobanteEnum.NOTA_DE_CREDITO.getDescripcion();
                }
            }

            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            sisEmailComprobanteElectronicoTO.setRucEmisor(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
            if (sisEmpresaParametros.getEmpCodigo().getEmpRuc().equalsIgnoreCase("0903837367001")) {
                sisEmailComprobanteElectronicoTO.setNombreEmisor("AUTOPINTURAS ASSAN");
            } else {
                sisEmailComprobanteElectronicoTO.setNombreEmisor(sisEmpresaParametros.getEmpCodigo().getEmpNombre());
            }

            sisEmailComprobanteElectronicoTO.setTelefonoEmisor(sisEmpresaParametros.getEmpCodigo().getEmpTelefono());
            sisEmailComprobanteElectronicoTO.setDireccionEmisor(sisEmpresaParametros.getEmpCodigo().getEmpDireccion());
            sisEmailComprobanteElectronicoTO.setUrlWebDocumentoElectronico(sisEmpresaParametros.getParWebDocumentosElectronicos());
            sisEmailComprobanteElectronicoTO.setNombreReceptor(nombreReceptor);
            sisEmailComprobanteElectronicoTO.setMailReceptor(mailReceptor);
            sisEmailComprobanteElectronicoTO.setNumeroComprobante(numeroComprobante);
            sisEmailComprobanteElectronicoTO.setTipoComprobante(tipoComprobante);
            sisEmailComprobanteElectronicoTO.setClaveAcceso(claveAcceso);
            sisEmailComprobanteElectronicoTO.setEmpresa(empresa);
            sisEmailComprobanteElectronicoTO.setPeriodo(ePeriodo);
            sisEmailComprobanteElectronicoTO.setMotivo(eMotivo);
            sisEmailComprobanteElectronicoTO.setNumero(eNumero);

            if (numeracion != null && numeracion.getIdNotificaciones() != null && numeracion.getIdNotificaciones().getIdVerificado()) {
                SisEmpresaNotificaciones notificacion = numeracion.getIdNotificaciones();
                sisEmailComprobanteElectronicoTO.setMailEmisor(notificacion.getIdPrincipal());//
                sisEmailComprobanteElectronicoTO.setClaveEmisor(notificacion.getIdNotificacionesEventos());
            } else {
                sisEmailComprobanteElectronicoTO.setClaveEmisor(null);
            }

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
                    if (sisInfoTO != null) {
                        //suceso
                        String susClave = numeroComprobante;
                        String susDetalle = "Se envió correo a: " + nombreReceptor + " con email :" + mailReceptor;
                        String susSuceso = "INSERT";
                        String susTabla = "sistemaweb.correo";

                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                        sucesoDao.insertar(sisSuceso);
                    }
                }
            }
        }
        return mensaje;

    }

    @Override
    public List<AnxListaLiquidacionComprasPendientesTO> getListaLiquidacionCompraPendientes(String empresa) throws Exception {
        return compraElectronicaDao.getListaLiquidacionCompraPendientes(empresa);
    }

    @Override
    public String getXmlLiquidacionCompras(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception {
        return compraElectronicaDao.getXmlLiquidacionCompras(empresa, ePeriodo, eMotivo, eNumero);
    }

    @Override
    public List<AnxCompraElectronicaNotificaciones> listarNotificacionesRetencionesElectronicas(String empresa, String motivo, String periodo, String numero) throws Exception {
        String query = "SELECT * FROM anexo.anx_compra_electronica_notificaciones n"
                + " WHERE n.comp_empresa = '" + empresa + "' AND n.comp_periodo = '" + periodo + "' AND n.comp_motivo = '" + motivo + "' AND n.comp_numero = '" + numero + "'  order by n.e_fecha;";
        List<AnxCompraElectronicaNotificaciones> notificaciones = genericSQLDao.obtenerPorSql(query, AnxCompraElectronicaNotificaciones.class);

        return notificaciones;
    }

}
