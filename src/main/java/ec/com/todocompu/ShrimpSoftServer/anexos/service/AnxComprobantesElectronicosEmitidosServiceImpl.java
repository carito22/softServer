/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import com.thoughtworks.xstream.XStream;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxComprobantesElectronicosEmitidosDao;
import ec.com.todocompu.ShrimpSoftServer.caja.service.CajaService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.BodegaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteCategoriaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VendedorService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasFormaCobroService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.Factura;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.ImpuestoFactura;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadecredito.ImpuestoNotaCredito;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadecredito.NotaCredito;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.ComprobanteRetencionReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.FacturaReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.NotaCreditoReporte;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes.TotalComprobante;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.ArchivoUtils;
import static ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.GenerarComprobantePdf.getTotales;
import static ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.GenerarComprobantePdf.getTotalesNC;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteImportarTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosEmitidos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxComprobantesElectronicosEmitidosPk;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoDAOTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasFormaCobro;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoComprobanteEnum;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.XStreamUtil;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.Autorizacion;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author VALDIVIEZO
 */
@Service
public class AnxComprobantesElectronicosEmitidosServiceImpl implements AnxComprobantesElectronicosEmitidosService {

    @Autowired
    private UrlWebServicesService urlWebServicesService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private AnxComprobantesElectronicosEmitidosDao anxComprobantesElectronicosEmitidosDao;
    @Autowired
    private VentasFormaCobroService ventaFormaCobroService;
    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private VentasMotivoService ventasMotivoService;
    @Autowired
    private ClienteCategoriaService clienteCategoriaService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private CajaService cajaService;
    @Autowired
    private EmpresaDao empresaDao;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    private Boolean comprobar = false;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String obtenerXMLComprobanteElectronicoEmitido(String empresa, String periodo, String clave) throws Exception {
        return anxComprobantesElectronicosEmitidosDao.obtenerXMLComprobanteElectronicoEmitido(empresa, periodo, clave);
    }

    @Override
    public AnxComprobantesElectronicosEmitidos obtenerAnxComprobantesElectronicosEmitidos(String empresa, String periodo, String clave) throws Exception {
        return anxComprobantesElectronicosEmitidosDao.obtenerAnxComprobantesElectronicosEmitidos(empresa, periodo, clave);
    }

    @Override
    public Map<String, Object> insertarClientesRezagadosLote(List<AnxComprobantesElectronicosEmitidos> listado, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        Map<String, Object> campos = null;
        List<InvClienteTO> listaClientesInsertado = new ArrayList<>();
        try {
            campos = new HashMap<>();
            for (AnxComprobantesElectronicosEmitidos comprobante : listado) {
                String identificacion = comprobante.getComproIdentificacionReceptor();
                InvClienteTO clienteTO = setearValoresCliente(
                        sisInfoTO.getEmpresa(),
                        sisInfoTO.getUsuario(),
                        comprobante
                );
                String razonSocial = clienteTO.getCliRazonSocial();
                boolean clienteRepetido = true;
                String mensajeAux = "T";
                if (identificacion.equals("9999999999999")) {
                    clienteRepetido = clienteService.getClienteRepetido("'" + sisInfoTO.getEmpresa() + "'", "", "", null, "'CONSUMIDOR FINAL'");
                } else {
                    //buscar cedula y luego ruc
                    char tipo = clienteTO.getCliTipoId();
                    if (tipo == 'C' || tipo == 'R') {
                        clienteRepetido = clienteService.getClienteRepetidoCedulaRUC(sisInfoTO.getEmpresa(), identificacion, tipo);
                    } else {
                        clienteRepetido = clienteService.getClienteRepetido("'" + sisInfoTO.getEmpresa() + "'", null, "'" + identificacion + "'", null, null);
                    }
                }

                if (!clienteRepetido) {
                    List<InvClienteTO> fueInsertado = listaClientesInsertado.stream()
                            .filter(item -> item.getCliId().equals(identificacion)
                            || item.getCliRazonSocial().equals(razonSocial))
                            .collect(Collectors.toList());
                    if (fueInsertado == null || fueInsertado.isEmpty()) {

                        //consumidor final
                        if (identificacion.equals("9999999999999")) {
                            clienteTO.setCliCodigo("00000");
                            clienteTO.setCliRazonSocial("CONSUMIDOR FINAL");
                            clienteTO.setCliNombreComercial("");
                            clienteTO.setCliId("");
                            clienteTO.setCliTipoId('F');
                        }
                        //vendedor
                        List<InvVendedorComboListadoTO> listaVendedores = vendedorService.getComboinvListaVendedorTOs(sisInfoTO.getEmpresa(), null);
                        if (listaVendedores != null && listaVendedores.size() > 0) {
                            clienteTO.setVendEmpresa(sisInfoTO.getEmpresa());
                            clienteTO.setVendCodigo(listaVendedores.get(0).getVendCodigo());
                            //categoria
                            List<InvClienteCategoriaTO> listaCategorias = clienteCategoriaService.getInvClienteCategoriaTO(sisInfoTO.getEmpresa());
                            if (listaCategorias != null && listaCategorias.size() > 0) {
                                clienteTO.setCliCategoria(listaCategorias.get(0).getCcCodigo());
                            } else {
                                mensajeAux = "FNo se puede guardar cliente con identificación:" + identificacion
                                        + ", debido a que no existe categorías de cliente";
                            }
                        } else {
                            mensajeAux = "FNo se puede guardar cliente con identificación:" + identificacion
                                    + ", debido a que no existe vendedores de cliente";
                        }
                        if (mensajeAux != null && mensajeAux.substring(0, 1).equals("T")) {
                            String mensajeCliente = clienteService.insertarInvClienteTO(clienteTO, null, sisInfoTO);
                            if (mensajeCliente != null) {
                                if (mensajeCliente.substring(0, 1).equals("T")) {
                                    InvClienteTO item = clienteTO;
                                    item.setClaveAcceso(comprobante.getAnxComprobantesElectronicosEmitidosPk().getComproClaveAcceso());//CLAVE ACCESO EN DIRECCION
                                    listaClientesInsertado.add(item);
                                    mensajeAux = mensajeCliente;
                                } else {
                                    mensajeAux = mensajeCliente + ", cliente:" + razonSocial + " con identificación: " + identificacion;
                                }

                            }
                        }
                    }
                }
                mensaje += mensajeAux + "|";
            }

            campos.put("mensaje", mensaje);
            campos.put("listaClientesInsertado", listaClientesInsertado);
        } catch (GeneralException e) {
            throw new GeneralException(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            throw new GeneralException(e.getMessage());
        }
        return campos;
    }

    @Override
    public String insertarComprobantesElectronicosLote(List<AnxComprobantesElectronicosEmitidos> listaComprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        List<ComboGenericoTO> listaClientesInsertado = new ArrayList<>();
        try {
            for (AnxComprobantesElectronicosEmitidos emitido : listaComprobantesElectronicos) {
                SisSuceso sisSuceso;
                susClave = emitido.getAnxComprobantesElectronicosEmitidosPk().getComproClaveAcceso();
                susDetalle = "Se insertó registro " + emitido.getComproSerieComprobante() + "del período " + emitido.getAnxComprobantesElectronicosEmitidosPk().getComproPeriodo();
                susSuceso = "INSERT";
                susTabla = "anexo.anx_comprobantes_electronicos_emitidos";
                sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                String claveAcceso = emitido.getAnxComprobantesElectronicosEmitidosPk().getComproClaveAcceso();
                String tipoComprobante = claveAcceso.substring(8, 10);
                try {
                    //xml
                    String xmlAutorizacion = null;
                    if (emitido.getComproXml() != null && emitido.getComproXml().length == 0) {
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
                                    emitido.setComproXml(xmlAutorizacion.getBytes("UTF-8"));
                                }
                            }
                            //**************setear valores de xml**************
                            String complemento = null;
                            String total = null;
                            String comprobanteNombre = null;

                            if (xmlAutorizacion != null) {
                                String comprobanteSerie = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<estab>") + 7, xmlAutorizacion.lastIndexOf("</estab>")).trim()
                                        + "-" + xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<ptoEmi>") + 8, xmlAutorizacion.lastIndexOf("</ptoEmi>")).trim()
                                        + "-" + xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<secuencial>") + 12, xmlAutorizacion.lastIndexOf("</secuencial>")).trim();

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
                                emitido.setComproSerieComprobante(comprobanteSerie);
                                emitido.setComproComplemento(complemento);
                                emitido.setComproComprobante(comprobanteNombre);
                                emitido.setComproImporteTotal(new BigDecimal(total != null ? total : "0.00"));
                                //*********************************
                                emitido.setUsrCodigo(sisInfoTO.getUsuario());
                                emitido.setUsrEmpresa(sisInfoTO.getEmpresa());
                                emitido.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                            }
                        }
                    }

                    AnxComprobantesElectronicosEmitidos comprobante = anxComprobantesElectronicosEmitidosDao.obtenerPorId(
                            AnxComprobantesElectronicosEmitidos.class,
                            emitido.getAnxComprobantesElectronicosEmitidosPk());
                    //existe comprobante, entonces actualiza datos
                    String mensajeAux = "T";
                    if (comprobante != null) {
                        mensajeAux = "F" + emitido.getComproComprobante() + " --> "
                                + "(" + emitido.getComproSerieComprobante() + ") "
                                + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", ya se ingresó anteriormente";
                    } else {
                        if (xmlAutorizacion != null) {
                            if (anxComprobantesElectronicosEmitidosDao.insertarComprobantesElectronico(emitido, sisSuceso)) {
                                mensajeAux = "T" + emitido.getComproComprobante() + " --> "
                                        + "(" + emitido.getComproSerieComprobante() + ") "
                                        + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", se ha guardado correctamente.";
                                //=================================CLIENTE==============================================
                                boolean clienteRepetido = true;
                                if (emitido.getComproIdentificacionReceptor().equals("9999999999999")) {
                                    clienteRepetido = clienteService.getClienteRepetido("'" + sisInfoTO.getEmpresa() + "'", "", "", null, "'CONSUMIDOR FINAL'");
                                } else {
                                    clienteRepetido = clienteService.getClienteRepetido("'" + sisInfoTO.getEmpresa() + "'", null, "'" + emitido.getComproIdentificacionReceptor() + "'", null, null);
                                }
                                if (!clienteRepetido) {
                                    xmlAutorizacion = new String((byte[]) emitido.getComproXml(), "UTF-8");
                                    Node contenidoComprobanteXm = obtenerNodeComprobante(xmlAutorizacion);
                                    if (tipoComprobante.compareTo("01") == 0 && contenidoComprobanteXm != null) {
                                        FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXm));
                                        Factura factura = facturaReporte != null ? facturaReporte.getFactura() : null;
                                        if (factura != null) {
                                            List<ComboGenericoTO> fueInsertado = listaClientesInsertado.stream()
                                                    .filter(item -> item.getClave().equals(emitido.getComproIdentificacionReceptor())
                                                    || item.getValor().equals(factura.getInfoFactura().getRazonSocialComprador()))
                                                    .collect(Collectors.toList());
                                            if (fueInsertado == null || fueInsertado.isEmpty()) {
                                                InvClienteTO clienteTO = setearValoresCliente(
                                                        sisInfoTO.getEmpresa(),
                                                        sisInfoTO.getUsuario(),
                                                        emitido
                                                );
                                                //consumidor final
                                                if (emitido.getComproIdentificacionReceptor().equals("9999999999999")) {
                                                    clienteTO.setCliCodigo("00000");
                                                    clienteTO.setCliRazonSocial("CONSUMIDOR FINAL");
                                                    clienteTO.setCliNombreComercial("");
                                                    clienteTO.setCliId("");
                                                    clienteTO.setCliTipoId('F');
                                                }
                                                //vendedor
                                                List<InvVendedorComboListadoTO> listaVendedores = vendedorService.getComboinvListaVendedorTOs(sisInfoTO.getEmpresa(), null);
                                                if (listaVendedores != null && listaVendedores.size() > 0) {
                                                    clienteTO.setVendEmpresa(sisInfoTO.getEmpresa());
                                                    clienteTO.setVendCodigo(listaVendedores.get(0).getVendCodigo());
                                                    //categoria
                                                    List<InvClienteCategoriaTO> listaCategorias = clienteCategoriaService.getInvClienteCategoriaTO(sisInfoTO.getEmpresa());
                                                    if (listaCategorias != null && listaCategorias.size() > 0) {
                                                        clienteTO.setCliCategoria(listaCategorias.get(0).getCcCodigo());
                                                    } else {
                                                        mensajeAux = "FNo se puede guardar cliente con identificación:" + emitido.getComproIdentificacionReceptor()
                                                                + ", debido a que no existe categorías de cliente";
                                                    }
                                                } else {
                                                    mensajeAux = "FNo se puede guardar cliente con identificación:" + emitido.getComproIdentificacionReceptor()
                                                            + ", debido a que no existe vendedores de cliente";
                                                }
                                                if (mensajeAux != null && mensajeAux.substring(0, 1).equals("T")) {
                                                    String mensajeCliente = clienteService.insertarInvClienteTO(clienteTO, null, sisInfoTO);
                                                    if (mensajeCliente != null && mensajeCliente.substring(0, 1).equals("T")) {
                                                        ComboGenericoTO item = new ComboGenericoTO();
                                                        item.setClave(clienteTO.getCliId());//identificacion
                                                        item.setValor(clienteTO.getCliRazonSocial());//razon social
                                                        listaClientesInsertado.add(item);
                                                    } else {
                                                        mensajeAux = "FNo se puede guardar cliente con identificación:" + emitido.getComproIdentificacionReceptor()
                                                                + ", debido a: " + mensajeCliente.substring(0);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    //==========================================================================================
                                }
                            } else {
                                mensajeAux = "F" + emitido.getComproComprobante() + " --> "
                                        + "(" + emitido.getComproSerieComprobante() + ") "
                                        + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", no se pudo guardar";
                            }
                        } else {
                            mensajeAux = "F" + emitido.getComproComprobante() + " --> "
                                    + "(" + emitido.getComproSerieComprobante() + ") "
                                    + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", no se pudo guardar debido a que no tiene XML";

                        }

                    }

                    mensaje += mensajeAux + "|";
                } catch (GeneralException e) {
                    UtilsExcepciones.enviarError(e, "AnxComprobantesElectronicosEmitidosService-insertarComprobantesElectronicosLote", sisInfoTO);
                    throw new GeneralException(e.getMessage() != null ? "F" + e.getMessage() : null);

                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    UtilsExcepciones.enviarError(e, "AnxComprobantesElectronicosEmitidosService-insertarComprobantesElectronicosLote", sisInfoTO);
                    throw new GeneralException(e.getMessage() != null ? "F" + e.getMessage() : null);
                }
            }
        } catch (GeneralException e) {
            UtilsExcepciones.enviarError(e, "AnxComprobantesElectronicosEmitidosService-insertarComprobantesElectronicosLote", sisInfoTO);
            throw new GeneralException(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            UtilsExcepciones.enviarError(e, "AnxComprobantesElectronicosEmitidosService-insertarComprobantesElectronicosLote", sisInfoTO);
            throw new GeneralException(e.getMessage());
        }
        return mensaje;
    }

    public InvClienteTO setearValoresCliente(String empresa, String usuario,
            AnxComprobantesElectronicosEmitidos emitido) throws UnsupportedEncodingException, Exception {
        String claveAcceso = emitido.getAnxComprobantesElectronicosEmitidosPk().getComproClaveAcceso();
        String tipoComprobante = claveAcceso != null ? claveAcceso.substring(8, 10) : "";
        InvClienteTO clienteTO = new InvClienteTO();
        try {
            if (tipoComprobante != null) {
                clienteTO.setCliCodigo("");
                clienteTO.setCliId(emitido.getComproIdentificacionReceptor());
                clienteTO.setEmpCodigo(empresa);
                String tipoIdentificacion = "";
                String razonSocial = null;
                String dirEstablecimiento = null;
                String xmlAutorizacion = new String((byte[]) emitido.getComproXml(), "UTF-8");
                Node contenidoComprobanteXm = obtenerNodeComprobante(xmlAutorizacion);
                //FACTURA
                if (tipoComprobante.compareTo("01") == 0 && contenidoComprobanteXm != null) {
                    FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXm));
                    Factura factura = facturaReporte != null ? facturaReporte.getFactura() : null;
                    tipoIdentificacion = factura.getInfoFactura().getTipoIdentificacionComprador();
                    razonSocial = factura.getInfoFactura().getRazonSocialComprador();
                    dirEstablecimiento = factura.getInfoFactura().getDirEstablecimiento();
                } else {
                    //NOTA CREDITO
                    if (tipoComprobante.compareTo("04") == 0) {
                        NotaCreditoReporte nc = new NotaCreditoReporte((NotaCredito) ArchivoUtils.unmarshal(NotaCredito.class, contenidoComprobanteXm));
                        NotaCredito notaCredito = nc.getNotaCredito() != null ? nc.getNotaCredito() : null;
                        tipoIdentificacion = notaCredito.getInfoNotaCredito().getTipoIdentificacionComprador();
                        razonSocial = notaCredito.getInfoNotaCredito().getRazonSocialComprador();
                        dirEstablecimiento = notaCredito.getInfoNotaCredito().getDirEstablecimiento();
                    } else {

                    }
                }
                //tipo identificacion
                if (tipoIdentificacion.equals("04")) {//ruc
                    clienteTO.setCliTipoId('R');
                } else {
                    if (tipoIdentificacion.equals("05")) {//cedula
                        clienteTO.setCliTipoId('C');
                    } else {
                        if (tipoIdentificacion.equals("06")) {//pasaporte
                            clienteTO.setCliTipoId('P');
                        } else {
                            clienteTO.setCliTipoId('F');
                        }
                    }
                }
                //estado civil
                clienteTO.setCliEstadoCivil("S");
                //razon social
                //evaluar no contenga tildes ni ñ
                if (razonSocial != null) {
                    razonSocial = razonSocial.toUpperCase();
                    razonSocial = razonSocial.replace("Ñ", "N");
                }
                //**************
                clienteTO.setCliRazonSocial(razonSocial);
                clienteTO.setCliNombreComercial(razonSocial);
                //establecimiento
                clienteTO.setCliCodigoEstablecimiento("001");
                clienteTO.setCliDireccion(dirEstablecimiento);
                //
                clienteTO.setCliPrecio((short) 0);
                clienteTO.setCliDiasCredito((short) 0);
                clienteTO.setCliCupoCredito(new BigDecimal("0.00"));
                clienteTO.setCliRelacionado(false);
                clienteTO.setCliInactivo(false);
                //usr
                clienteTO.setUsrFechaInsertaCliente(usuario);
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            throw new GeneralException(e.getMessage());
        }

        return clienteTO;
    }

    @Override
    public Boolean insertarComprobantesElectronico(AnxComprobantesElectronicosEmitidos comprobantesElectronicos, SisInfoTO sisInfoTO) throws Exception {
        Boolean ingresoComprobante = false;
        SisSuceso sisSuceso;

        susClave = comprobantesElectronicos.getAnxComprobantesElectronicosEmitidosPk().getComproClaveAcceso();
        susDetalle = "Se insertó registro " + comprobantesElectronicos.getComproSerieComprobante() + " del periodo " + comprobantesElectronicos.getAnxComprobantesElectronicosEmitidosPk().getComproPeriodo();
        susSuceso = "INSERT";
        susTabla = "anexo.anx_comprobantes_electronicos_emitidos";
        sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        String claveAcceso = comprobantesElectronicos.getAnxComprobantesElectronicosEmitidosPk().getComproClaveAcceso();
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

        ingresoComprobante = anxComprobantesElectronicosEmitidosDao.insertarComprobantesElectronico(comprobantesElectronicos, sisSuceso);

        return ingresoComprobante;
    }

    @Override
    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicos(String empresa, String periodo) throws Exception {
        if (periodo != null) {
            return anxComprobantesElectronicosEmitidosDao.listarComprobantesElectronicosConsulta(empresa, periodo);

        } else {
            return anxComprobantesElectronicosEmitidosDao.listarComprobantesElectronicos(empresa);
        }
    }

    @Override
    public List<AnxComprobantesElectronicosEmitidos> listarComprobantesElectronicosEmitidos(String empresa, String periodo) throws Exception {
        List<AnxComprobantesElectronicosEmitidos> listadoOriginal = anxComprobantesElectronicosEmitidosDao.listarComprobantesElectronicosEmitidos(empresa, periodo);
        for (AnxComprobantesElectronicosEmitidos comprobante : listadoOriginal) {
            if (comprobante.getComproIdentificacionReceptor() != null) {
                if (comprobante.getComproIdentificacionReceptor().equals("9999999999999")) {
                    InvClienteTO clienteTO = clienteService.getInvClienteTOByRazonSocial(empresa, "CONSUMIDOR FINAL");
                    if (clienteTO != null) {
                        comprobante.setCodigoClienteEnSistema(clienteTO.getCliCodigo());
                    }
                } else {
                    char tipo = comprobante.getComproIdentificacionReceptor().length() == 13 ? 'R'
                            : comprobante.getComproIdentificacionReceptor().length() == 10 ? 'C' : '-';
                    boolean clienteExiste = clienteService.getClienteRepetidoCedulaRUC(empresa, comprobante.getComproIdentificacionReceptor(), tipo);
                    if (clienteExiste) {
                        InvCliente cliente = clienteService.obtenerInvClientePorCedulaRuc(empresa, comprobante.getComproIdentificacionReceptor(), tipo);
                        if (cliente != null) {
                            comprobante.setCodigoClienteEnSistema(cliente.getInvClientePK().getCliCodigo());
                        }
                    }

                }
            }
        }
        return listadoOriginal;
    }

    @Override
    public String cambiarEstadoCancelado(String empresa, String periodo, String clave, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        comprobar = false;
        susClave = clave;
        susSuceso = "UPDATE";
        susTabla = "anexo.anx_comprobantes_electronicos_emitidos";
        susDetalle = "Cambio de estado cancelado a " + estado + " del registro clave acceso = " + clave + "  periodo = " + periodo + " empresa = " + empresa;
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        comprobar = anxComprobantesElectronicosEmitidosDao.cambiarEstadoCancelado(empresa, periodo, clave, estado, sisSuceso);
        if (comprobar) {
            mensaje = "TSe cambio correctamente el estado del registo";
        } else {
            mensaje = "FOcurrio un error al modificar el estado del registro";
        }
        return mensaje;
    }

    @Override
    public Map<String, Object> obtenerDatosProductosDeXML(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = null;
        String clave = UtilsJSON.jsonToObjeto(String.class, map.get("clave"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String xmlDesdeCliente = UtilsJSON.jsonToObjeto(String.class, map.get("xml"));//codificado en base64
        Boolean comprobanteRecibido = UtilsJSON.jsonToObjeto(Boolean.class, map.get("comprobanteRecibido"));
        String tipoComprobante = clave.substring(8, 10);
        String periodo = "";
        if (clave != null) {
            periodo = clave.substring(0, 8);
            String anio = periodo.substring(4, 8);
            String mes = periodo.substring(2, 4);
            periodo = anio.concat("-").concat(mes);
        }
        String xmlUTF8 = "";
        if (xmlDesdeCliente != null && !xmlDesdeCliente.equals("")) {
            byte[] result = Base64.getDecoder().decode(xmlDesdeCliente);
            xmlUTF8 = new String((byte[]) result, "UTF-8");
        }

        String xml = xmlUTF8 != null && !xmlUTF8.equals("")
                ? xmlUTF8
                : obtenerXMLComprobanteElectronicoEmitido(empresa, periodo, clave);

        if (xml != null && !xml.equals("")) {
            campos = new HashMap<>();
            Node contenidoComprobanteXm = null;
            Document doc = null;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            contenidoComprobanteXm = doc.getElementsByTagName("comprobante").item(0);

            List<InvProductoDAOTO> listaProductos = new ArrayList<InvProductoDAOTO>();
            List<AnxHomologacionProducto> ListadoProductoHomologado = new ArrayList<AnxHomologacionProducto>();
            if (tipoComprobante.compareTo("01") == 0) {
                FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXm));
                Factura factura = facturaReporte != null ? facturaReporte.getFactura() : null;
                if (factura != null) {
                    for (Factura.Detalles.Detalle det : factura.getDetalles().getDetalle()) {
                        if (comprobanteRecibido) {
                            AnxHomologacionProducto productoHomologado = new AnxHomologacionProducto();
                            AnxHomologacionProducto productoHomologadoSistema = productoService.buscarInvProductoHomologado(empresa, det.getCodigoPrincipal(), factura.getInfoTributaria().getRuc());
                            if (productoHomologadoSistema != null) {
                                productoHomologado = productoHomologadoSistema;
                            }
                            ListadoProductoHomologado.add(productoHomologado);
                        } else {
                            InvProductoDAOTO producto = new InvProductoDAOTO();
                            InvProductoDAOTO productoSistema = productoService.buscarInvProductoDAOTO(empresa, det.getCodigoPrincipal());
                            if (productoSistema != null) {
                                producto = productoSistema;
                                producto.setExisteProductoEnSistema(true);
                            } else {
                                //datos de xml
                                producto.setProCodigoPrincipal(det.getCodigoPrincipal());
                                producto.setProCodigoAlterno(det.getCodigoAuxiliar());
                                producto.setProNombre(det.getDescripcion());
                                producto.setExisteProductoEnSistema(false);
                            }
                            listaProductos.add(producto);
                        }
                    }
                    campos.put("listaProductos", comprobanteRecibido ? ListadoProductoHomologado : listaProductos);
                }
            } else if (tipoComprobante.compareTo("04") == 0) {
                NotaCreditoReporte nc = new NotaCreditoReporte((NotaCredito) ArchivoUtils.unmarshal(NotaCredito.class, contenidoComprobanteXm));
                NotaCredito notaCredito = nc.getNotaCredito() != null ? nc.getNotaCredito() : null;
                if (notaCredito != null) {
                    for (NotaCredito.Detalles.Detalle det : notaCredito.getDetalles().getDetalle()) {
                        if (comprobanteRecibido) {
                            AnxHomologacionProducto productoHomologado = new AnxHomologacionProducto();
                            AnxHomologacionProducto productoHomologadoSistema = productoService.buscarInvProductoHomologado(empresa, det.getCodigoInterno(), notaCredito.getInfoTributaria().getRuc());
                            if (productoHomologadoSistema != null) {
                                productoHomologado = productoHomologadoSistema;
                            }
                            ListadoProductoHomologado.add(productoHomologado);
                        } else {
                            InvProductoDAOTO producto = new InvProductoDAOTO();
                            InvProductoDAOTO productoSistema = productoService.buscarInvProductoDAOTO(empresa, det.getCodigoInterno());
                            if (productoSistema != null) {
                                producto = productoSistema;
                                producto.setExisteProductoEnSistema(true);
                            } else {
                                //datos de xml
                                producto.setProCodigoPrincipal(det.getCodigoInterno());
                                producto.setProCodigoAlterno(det.getCodigoAdicional());
                                producto.setProNombre(det.getDescripcion());
                                producto.setExisteProductoEnSistema(false);
                            }
                            listaProductos.add(producto);
                        }
                    }
                    campos.put("listaProductos", comprobanteRecibido ? ListadoProductoHomologado : listaProductos);
                }
            }
        }

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaComprobantesElectronicosEmitidos(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));

        List<SisPeriodo> listadoPeriodos = periodoService.getListaPeriodo(empresa);
        List<InvListaBodegasTO> listadoBodegas = bodegaService.buscarBodegasTO(empresa, false, null);
        List<InvVentasFormaCobroTO> listadoFormaPago = ventaFormaCobroService.getListaInvVentasFormaCobroTO(empresa, false);
        InvProductoEtiquetas invProductoEtiquetas = productoService.traerEtiquetas(empresa);
        List<InvVentaMotivoComboTO> listaInvVentaMotivoComboTO = ventasMotivoService.getListaVentaMotivoComboTO(empresa, false);
        CajCajaTO caja = cajaService.getCajCajaTO(empresa, usuario);

        campos.put("caja", caja);
        campos.put("listadoPeriodos", listadoPeriodos);
        campos.put("listadoBodegas", listadoBodegas);
        campos.put("listadoFormaPago", listadoFormaPago);
        campos.put("invProductoEtiquetas", invProductoEtiquetas);
        campos.put("listadoInvVentaMotivoComboTO", listaInvVentaMotivoComboTO);
        return campos;
    }

    @Override
    public Map<String, Object> generarComprobantesEmitidos(List<AnxComprobantesElectronicosEmitidos> listaComprobantesElectronicos,
            String bodega, String motivo, String precioVenta,
            Integer formaCobro, String codigoProducto, SisInfoTO sisInfoTO) throws Exception {
        //String mensaje = "";
        Map<String, Object> campos = new HashMap<>();
        try {
            String mensaje = "";
            List<InvVentaConDetalle> listadoRespuesta = new ArrayList<>();
            for (AnxComprobantesElectronicosEmitidos comprobante : listaComprobantesElectronicos) {
                String empresa = sisInfoTO.getEmpresa();
                String claveAcceso = comprobante.getAnxComprobantesElectronicosEmitidosPk().getComproClaveAcceso();
                String tipoComprobante = claveAcceso.substring(8, 10);
                String periodo = claveAcceso.substring(0, 8);
                if (periodo != null) {
                    String anio = periodo.substring(4, 8);
                    String mes = periodo.substring(2, 4);
                    periodo = anio.concat("-").concat(mes);
                }
                try {
                    String mensajeAux = "T";
                    //***************GUARDAR COMPROBANTES:VENTA,NC Y ND
                    //CLIENTE CONSUMIDOR FINAL
                    String cliCodigo = null;
                    if (comprobante.getComproIdentificacionReceptor().equals("9999999999999")) {
                        InvClienteTO cliente = clienteService.getInvClienteTOByRazonSocial(empresa, "CONSUMIDOR FINAL");
                        if (cliente != null) {
                            cliCodigo = cliente.getCliCodigo();
                        }
                    } else {
                        char tipo = comprobante.getComproIdentificacionReceptor().length() == 13 ? 'R'
                                : comprobante.getComproIdentificacionReceptor().length() == 10 ? 'C' : '-';
                        boolean clienteExiste = clienteService.getClienteRepetidoCedulaRUC(empresa, comprobante.getComproIdentificacionReceptor(), tipo);
                        if (clienteExiste) {
                            InvCliente cliente = clienteService.obtenerInvClientePorCedulaRuc(empresa, comprobante.getComproIdentificacionReceptor(), tipo);
                            if (cliente != null) {
                                cliCodigo = cliente.getInvClientePK().getCliCodigo();
                            }
                        }
                    }
                    if (cliCodigo == null) {
                        mensajeAux = "F" + comprobante.getComproComprobante() + " --> "
                                + "(" + comprobante.getComproSerieComprobante() + ") "
                                + "El cliente con identificación :" + comprobante.getComproIdentificacionReceptor() + ", no existe";
                    } else {
                        //SI NO EXISTE XML, ACTUALIZAR VALORES
                        String xmlAutorizacion = null;
                        boolean xmlCorrecto = true;
                        if (comprobante.getComproXml() == null || (comprobante.getComproXml() != null && comprobante.getComproXml().length == 0)) {
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
                                        comprobante.setComproXml(xmlAutorizacion.getBytes("UTF-8"));
                                    }
                                }
                                //**************setear valores de xml**************
                                String complemento = null;
                                String total = null;
                                String comprobanteNombre = null;
                                if (xmlAutorizacion != null) {
                                    String comprobanteSerie = xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<estab>") + 7, xmlAutorizacion.lastIndexOf("</estab>")).trim()
                                            + "-" + xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<ptoEmi>") + 8, xmlAutorizacion.lastIndexOf("</ptoEmi>")).trim()
                                            + "-" + xmlAutorizacion.substring(xmlAutorizacion.lastIndexOf("<secuencial>") + 12, xmlAutorizacion.lastIndexOf("</secuencial>")).trim();

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
                                    comprobante.setComproSerieComprobante(comprobanteSerie);
                                    comprobante.setComproComplemento(complemento);
                                    comprobante.setComproComprobante(comprobanteNombre);
                                    comprobante.setComproImporteTotal(new BigDecimal(total != null ? total : "0.00"));
                                    //**********SUCESO ACTUALIZAR
                                    SisSuceso sisSuceso;
                                    susClave = comprobante.getAnxComprobantesElectronicosEmitidosPk().getComproClaveAcceso();
                                    susDetalle = "Se actualizó registro " + comprobante.getComproSerieComprobante() + "del período " + comprobante.getAnxComprobantesElectronicosEmitidosPk().getComproPeriodo() + ", desde importar comprobantes de venta";
                                    susSuceso = "UPDATE";
                                    susTabla = "anexo.anx_comprobantes_electronicos_emitidos";
                                    sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                    if (!anxComprobantesElectronicosEmitidosDao.actualizarComprobantesElectronico(comprobante, sisSuceso)) {
                                        xmlCorrecto = false;
                                    }
                                } else {
                                    xmlCorrecto = false;
                                    mensajeAux
                                            = "F" + comprobante.getComproComprobante() + " --> "
                                            + "(" + comprobante.getComproSerieComprobante() + ") "
                                            + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", no se logró guardar debido a que no se generó el archivo XML";
                                }
                            } else {
                                xmlCorrecto = false;
                                mensajeAux
                                        = "F" + comprobante.getComproComprobante() + " --> "
                                        + "(" + comprobante.getComproSerieComprobante() + ") "
                                        + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", no se logró guardar debido a que no se generó el archivo XML";

                            }
                            //********
                        }

                        if (xmlCorrecto && comprobante.getComproXml() != null && comprobante.getComproXml().length != 0) {
                            if (xmlAutorizacion == null) {
                                xmlAutorizacion = new String((byte[]) comprobante.getComproXml(), "UTF-8");
                            }
                            Node contenidoComprobanteXm = obtenerNodeComprobante(xmlAutorizacion);
                            InvVentasTO invVentasTO = new InvVentasTO();
                            List<InvVentasDetalleTO> listaInvVentasDetalleTO = new ArrayList<>();
                            //FACTURA
                            if (tipoComprobante.compareTo("01") == 0 && contenidoComprobanteXm != null) {
                                FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXm));
                                Factura factura = facturaReporte != null ? facturaReporte.getFactura() : null;
                                if (factura != null) {
                                    InvBodega invBodega = bodegaService.buscarInvBodega(empresa, bodega);
                                    InvVentasFormaCobro formaPagoAux = ventaFormaCobroService.buscarVentasFormaCobro(formaCobro);
                                    InvProductoDAOTO producto = productoService.buscarInvProductoDAOTO(empresa, codigoProducto);
                                    //*********************************venta*******************************************
                                    invVentasTO = generarVentaCabecera(
                                            empresa, periodo, motivo,
                                            bodega, invBodega.getSecCodigo(),
                                            cliCodigo, precioVenta,
                                            formaCobro, formaPagoAux.getFcDetalle(),
                                            sisInfoTO.getUsuario(), factura, null);
                                    //********************************detalle*******************************************
                                    listaInvVentasDetalleTO = generarVentaDetalle(
                                            empresa, bodega,
                                            invBodega.getSecCodigo(),
                                            invVentasTO.getVtaIvaVigente(),
                                            producto, factura);
                                    //insertar venta a BD
                                    if (listaInvVentasDetalleTO != null && listaInvVentasDetalleTO.size() > 0) {
                                        //
                                        for (InvVentasDetalleTO detalle : listaInvVentasDetalleTO) {
                                            BigDecimal rec = BigDecimal.ZERO;
                                            BigDecimal desc = BigDecimal.ZERO;

                                            rec = detalle.getDetParcial().multiply(detalle.getDetPorcentajeRecargo().divide(new BigDecimal("100")));
                                            desc = (detalle.getDetParcial().add(detalle.getDetMontoIce()).add(rec))
                                                    .multiply((detalle.getDetPorcentajeDescuento().divide(new BigDecimal("100"))));
                                            if (detalle.getVtIva()) {
                                                invVentasTO.setVtaBaseImponible(invVentasTO.getVtaBaseImponible().add(detalle.getDetParcial()));
                                                invVentasTO.setVtaRecargoBaseImponible(invVentasTO.getVtaRecargoBaseImponible().add(rec));
                                                invVentasTO.setVtaDescuentoBaseImponible(invVentasTO.getVtaDescuentoBaseImponible().add(desc));
                                            } else {
                                                invVentasTO.setVtaBase0(invVentasTO.getVtaBase0().add(detalle.getDetParcial()));
                                                invVentasTO.setVtaRecargoBase0(invVentasTO.getVtaRecargoBase0().add(rec));
                                                invVentasTO.setVtaDescuentoBase0(invVentasTO.getVtaDescuentoBase0().add(desc));
                                            }
                                            invVentasTO.setVtaMontoIva(invVentasTO.getVtaMontoIva().add(detalle.getMontoIVa()));
                                            invVentasTO.setVtaMontoIce(invVentasTO.getVtaMontoIce().add(detalle.getDetMontoIce()));

                                        }
                                        invVentasTO.setVtaSubtotalBaseImponible(invVentasTO.getVtaBaseImponible().add(invVentasTO.getVtaRecargoBaseImponible()).subtract(invVentasTO.getVtaDescuentoBaseImponible()));
                                        invVentasTO.setVtaSubtotalBase0(invVentasTO.getVtaBase0().add(invVentasTO.getVtaRecargoBase0()).subtract(invVentasTO.getVtaDescuentoBase0()));
                                        invVentasTO.setVtaTotal(invVentasTO.getVtaMontoIva().add(invVentasTO.getVtaSubtotalBaseImponible()).add(invVentasTO.getVtaSubtotalBase0()));

                                        //Respuesta
                                        InvVentaConDetalle ventaConDetalle = new InvVentaConDetalle();
                                        ventaConDetalle.setInvVentasTO(invVentasTO);
                                        ventaConDetalle.setListaInvVentasDetalleTO(listaInvVentasDetalleTO);
                                        ventaConDetalle.setClaveAcceso(claveAcceso);
                                        ventaConDetalle.setComproXml(comprobante.getComproXml());
                                        listadoRespuesta.add(ventaConDetalle);
                                    } else {
                                        mensajeAux
                                                = "F" + comprobante.getComproComprobante() + " --> "
                                                + "(" + comprobante.getComproSerieComprobante() + ") "
                                                + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", no se logró guardar.";
                                    }

                                }
                            } else if (tipoComprobante.compareTo("04") == 0) {//credito
                                NotaCreditoReporte nc = new NotaCreditoReporte((NotaCredito) ArchivoUtils.unmarshal(NotaCredito.class, contenidoComprobanteXm));
                                NotaCredito notaCredito = nc.getNotaCredito() != null ? nc.getNotaCredito() : null;
                                if (notaCredito != null) {
                                    InvBodega invBodega = bodegaService.buscarInvBodega(empresa, bodega);
                                    InvVentasFormaCobro formaPagoAux = ventaFormaCobroService.buscarVentasFormaCobro(formaCobro);
                                    InvProductoDAOTO producto = productoService.buscarInvProductoDAOTO(empresa, codigoProducto);
                                    //*********************************venta*******************************************
                                    invVentasTO = generarVentaCabecera(
                                            empresa, periodo, motivo,
                                            bodega, invBodega.getSecCodigo(),
                                            cliCodigo, precioVenta,
                                            formaCobro, formaPagoAux.getFcDetalle(),
                                            sisInfoTO.getUsuario(), null, notaCredito);
                                    //*********************************detalle*******************************************
                                    listaInvVentasDetalleTO = new ArrayList<>();
                                    Integer i = 0;
                                    for (NotaCredito.Detalles.Detalle det : notaCredito.getDetalles().getDetalle()) {
                                        InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();
                                        invVentasDetalleTO.setDetOrden(i + 1);
                                        invVentasDetalleTO.setDetSecuencia(0);
                                        //venta
                                        invVentasDetalleTO.setVtaEmpresa(empresa);
                                        //producto
                                        invVentasDetalleTO.setProEmpresa(empresa);
                                        invVentasDetalleTO.setProCodigoPrincipal(producto.getProCodigoPrincipal());
                                        invVentasDetalleTO.setProNombre(det.getDescripcion());
                                        invVentasDetalleTO.setDetPendiente(false);
                                        //valores
                                        invVentasDetalleTO.setDetCantidad(det.getCantidad());
                                        invVentasDetalleTO.setDetPrecio(det.getPrecioUnitario());
                                        invVentasDetalleTO.setDetParcial(det.getCantidad().multiply(det.getPrecioUnitario()));

                                        invVentasDetalleTO.setDetPorcentajeDescuento(BigDecimal.ZERO);
                                        invVentasDetalleTO.setDetPorcentajeRecargo(BigDecimal.ZERO);
                                        //descuento
                                        if (det.getDescuento().compareTo(BigDecimal.ZERO) > 0) {
                                            invVentasDetalleTO.setDetPorcentajeDescuento(
                                                    det.getDescuento()
                                                            .divide(invVentasDetalleTO.getDetParcial())
                                                            .multiply(new BigDecimal("100.00")));
                                        }
                                        invVentasDetalleTO.setIcePorcentaje(BigDecimal.ZERO);
                                        invVentasDetalleTO.setIceTarifaFija(BigDecimal.ZERO);
                                        invVentasDetalleTO.setDetMontoIce(BigDecimal.ZERO);
                                        //bodega
                                        invVentasDetalleTO.setBodEmpresa(empresa);
                                        invVentasDetalleTO.setBodCodigo(bodega);
                                        //sector y piscina
                                        invVentasDetalleTO.setSecEmpresa(empresa);
                                        invVentasDetalleTO.setSecCodigo(invBodega.getSecCodigo());

                                        invVentasDetalleTO.setPisEmpresa(null);//opcional
                                        invVentasDetalleTO.setPisSector(null);//opcional
                                        invVentasDetalleTO.setPisNumero(null);//opcional

                                        invVentasDetalleTO.setProComplementario(null);
                                        invVentasDetalleTO.setDetObservaciones(null);
                                        invVentasDetalleTO.setDetEmpaque(null);
                                        invVentasDetalleTO.setDetEmpaqueCantidad(null);
                                        invVentasDetalleTO.setDetConversionPesoNeto(null);
                                        //*************IMPUESTOS*************
                                        if (det.getImpuestos().getImpuesto() != null) {
                                            for (ImpuestoNotaCredito impuesto : det.getImpuestos().getImpuesto()) {
                                                //descuento
                                                if (impuesto.getCodigo().equals("2")) {//IVA
                                                    if (impuesto.getTarifa().compareTo(BigDecimal.ZERO) != 0) {
                                                        invVentasDetalleTO.setVtIva(true);
                                                        invVentasDetalleTO.setMontoIVa(impuesto.getValor());
                                                    }
                                                }
                                                if (impuesto.getCodigo().equals("3")) {//ICE
                                                    invVentasDetalleTO.setDetMontoIce(impuesto.getValor());
                                                }
                                            }
                                        }
                                        i++;
                                        listaInvVentasDetalleTO.add(invVentasDetalleTO);
                                    }

                                    //*********************Respuesta**********************
                                    //insertar venta a BD
                                    if (listaInvVentasDetalleTO != null && listaInvVentasDetalleTO.size() > 0) {
                                        //
                                        for (InvVentasDetalleTO detalle : listaInvVentasDetalleTO) {
                                            BigDecimal rec = BigDecimal.ZERO;
                                            BigDecimal desc = BigDecimal.ZERO;

                                            rec = detalle.getDetParcial().multiply(detalle.getDetPorcentajeRecargo().divide(new BigDecimal("100")));
                                            desc = (detalle.getDetParcial().add(detalle.getDetMontoIce()).add(rec))
                                                    .multiply((detalle.getDetPorcentajeDescuento().divide(new BigDecimal("100"))));
                                            if (detalle.getVtIva()) {
                                                invVentasTO.setVtaBaseImponible(invVentasTO.getVtaBaseImponible().add(detalle.getDetParcial()));
                                                invVentasTO.setVtaRecargoBaseImponible(invVentasTO.getVtaRecargoBaseImponible().add(rec));
                                                invVentasTO.setVtaDescuentoBaseImponible(invVentasTO.getVtaDescuentoBaseImponible().add(desc));
                                            } else {
                                                invVentasTO.setVtaBase0(invVentasTO.getVtaBase0().add(detalle.getDetParcial()));
                                                invVentasTO.setVtaRecargoBase0(invVentasTO.getVtaRecargoBase0().add(rec));
                                                invVentasTO.setVtaDescuentoBase0(invVentasTO.getVtaDescuentoBase0().add(desc));
                                            }
                                            invVentasTO.setVtaMontoIva(invVentasTO.getVtaMontoIva().add(detalle.getMontoIVa()));
                                            invVentasTO.setVtaMontoIce(invVentasTO.getVtaMontoIce().add(detalle.getDetMontoIce()));

                                        }
                                        invVentasTO.setVtaSubtotalBaseImponible(invVentasTO.getVtaBaseImponible().add(invVentasTO.getVtaRecargoBaseImponible()).subtract(invVentasTO.getVtaDescuentoBaseImponible()));
                                        invVentasTO.setVtaSubtotalBase0(invVentasTO.getVtaBase0().add(invVentasTO.getVtaRecargoBase0()).subtract(invVentasTO.getVtaDescuentoBase0()));
                                        invVentasTO.setVtaTotal(invVentasTO.getVtaMontoIva().add(invVentasTO.getVtaSubtotalBaseImponible()).add(invVentasTO.getVtaSubtotalBase0()));

                                        //Respuesta
                                        InvVentaConDetalle ventaConDetalle = new InvVentaConDetalle();
                                        ventaConDetalle.setInvVentasTO(invVentasTO);
                                        ventaConDetalle.setListaInvVentasDetalleTO(listaInvVentasDetalleTO);
                                        ventaConDetalle.setClaveAcceso(claveAcceso);
                                        ventaConDetalle.setComproXml(comprobante.getComproXml());
                                        //complemento nc
                                        String motivoComplemento = null;
                                        if (notaCredito.getInfoNotaCredito().getMotivo() != null) {
                                            notaCredito.getInfoNotaCredito().setMotivo(notaCredito.getInfoNotaCredito().getMotivo().toUpperCase());
                                            if (notaCredito.getInfoNotaCredito().getMotivo().contains("DEVOLUCIÓN") || notaCredito.getInfoNotaCredito().getMotivo().contains("DEVOLUCION")) {
                                                motivoComplemento = "DEVOLUCION";
                                            } else {
                                                if (notaCredito.getInfoNotaCredito().getMotivo().contains("ANULACIÓN") || notaCredito.getInfoNotaCredito().getMotivo().contains("ANULACION")) {
                                                    motivoComplemento = "ANULACION";
                                                } else {
                                                    if (notaCredito.getInfoNotaCredito().getMotivo().contains("INTERNA")) {
                                                        motivoComplemento = "NOTA DE CREDITO INTERNA";
                                                    } else {
                                                        motivoComplemento = "OTROS";
                                                    }
                                                }
                                            }
                                        }
                                        ventaConDetalle.setMotivoDocumentoComplemento(motivoComplemento);
                                        ventaConDetalle.setNumeroDocumentoComplemento(notaCredito.getInfoNotaCredito().getNumDocModificado());
                                        ventaConDetalle.setTipoDocumentoComplemento("18");
                                        listadoRespuesta.add(ventaConDetalle);
                                    } else {
                                        mensajeAux
                                                = "F" + comprobante.getComproComprobante() + " --> "
                                                + "(" + comprobante.getComproSerieComprobante() + ") "
                                                + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", no se logró guardar.";
                                    }
                                }
                            } else if (tipoComprobante.compareTo("05") == 0) {//debito
                                //retenciones
                                ComprobanteRetencionReporte reten = new ComprobanteRetencionReporte((ComprobanteRetencion) ArchivoUtils.unmarshal(ComprobanteRetencion.class, contenidoComprobanteXm));
                                ComprobanteRetencion retencion = reten.getComprobanteRetencion() != null ? reten.getComprobanteRetencion() : null;
                                if (retencion != null) {
                                }
                            }
                        }
                    }

                    //*************
                    mensaje += mensajeAux + "|";
                } catch (GeneralException e) {
                    throw new GeneralException(e.getMessage() != null ? "F" + e.getMessage() : null);
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    throw new GeneralException(e.getMessage() != null ? "F" + e.getMessage() : null);
                }
            }
            campos.put("mensaje", mensaje);
            campos.put("listadoRespuesta", listadoRespuesta);
        } catch (GeneralException e) {
            throw new GeneralException(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            throw new GeneralException(e.getMessage());
        }
        return campos;
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

    public InvVentasTO generarVentaCabecera(String empresa, String periodo,
            String motivo, String bodega, String secCodigo,
            String cliCodigo, String precioVenta,
            Integer formaCobro, String fcDetalle, String usuarioCodigo,
            Factura factura, NotaCredito notaCredito) throws Exception {

        String documentoTipo = factura != null ? "18" : (notaCredito != null ? "04" : "05");
        InvVentasTO invVentasTO = new InvVentasTO();
        invVentasTO.setVtaEmpresa(empresa);//pk
        invVentasTO.setVtaPeriodo(periodo);//pk
        invVentasTO.setVtaMotivo(motivo);//pk
        invVentasTO.setVtaNumero(null);
        invVentasTO.setBodEmpresa(empresa);
        invVentasTO.setBodCodigo(bodega);
        invVentasTO.setVtaDocumentoTipo(documentoTipo);//18,04 o 05
        invVentasTO.setVtaPagadoEfectivo(BigDecimal.ZERO);
        invVentasTO.setVtaPagadoDineroElectronico(BigDecimal.ZERO);
        invVentasTO.setVtaPagadoTarjetaCredito(BigDecimal.ZERO);
        invVentasTO.setVtaPagadoOtro(BigDecimal.ZERO);
        //numero documento
        String numeroDoc = null;
        String fecha = null;
        TotalComprobante totales = null;
        if (factura != null) {
            numeroDoc = factura.getInfoTributaria().getEstab() + "-"
                    + factura.getInfoTributaria().getPtoEmi() + "-"
                    + factura.getInfoTributaria().getSecuencial();
            fecha = UtilsValidacion.fecha(factura.getInfoFactura().getFechaEmision(), "dd/MM/yyyy", "yyyy-MM-dd");
            totales = getTotales(factura.getInfoFactura());
            //pagos
            if (factura.getInfoFactura().getPagos() != null) {
                if ((factura.getInfoFactura().getPagos().getPago() != null) && !factura.getInfoFactura().getPagos().getPago().isEmpty()) {
                    for (Factura.InfoFactura.Pagos.Pago pago : factura.getInfoFactura().getPagos().getPago()) {
                        if (pago.getFormaPago().compareTo("01") == 0) {
                            invVentasTO.setVtaPagadoEfectivo(pago.getTotal());
                        } else if (pago.getFormaPago().compareTo("17") == 0) {
                            invVentasTO.setVtaPagadoDineroElectronico(pago.getTotal());
                        } else if (pago.getFormaPago().compareTo("19") == 0) {
                            invVentasTO.setVtaPagadoTarjetaCredito(pago.getTotal());
                        } else if (pago.getFormaPago().compareTo("20") == 0) {
                            invVentasTO.setVtaPagadoOtro(pago.getTotal());
                        }
                    }
                }
            }
        } else {
            if (notaCredito != null) {
                numeroDoc = notaCredito.getInfoTributaria().getEstab() + "-"
                        + notaCredito.getInfoTributaria().getPtoEmi() + "-"
                        + notaCredito.getInfoTributaria().getSecuencial();
                fecha = UtilsValidacion.fecha(notaCredito.getInfoNotaCredito().getFechaEmision(), "dd/MM/yyyy", "yyyy-MM-dd");
                totales = getTotalesNC(notaCredito.getInfoNotaCredito());
                //pagos
                invVentasTO.setVtaPagadoOtro(notaCredito.getInfoNotaCredito().getValorModificacion());
            } else {

            }
        }
        invVentasTO.setVtaDocumentoNumero(numeroDoc);
        invVentasTO.setVtaFecha(fecha);
        invVentasTO.setVtaFechaVencimiento(fecha);

        if (totales != null) {
            invVentasTO.setVtaIvaVigente(
                    totales.getIvaVigente().compareTo("14%") == 0
                    ? new BigDecimal("14.00")
                    : new BigDecimal("12.00"));
        }

        //totales
        invVentasTO.setVtaBase0(BigDecimal.ZERO);
        invVentasTO.setVtaBaseImponible(BigDecimal.ZERO);
        invVentasTO.setVtaBaseNoObjeto(BigDecimal.ZERO);
        invVentasTO.setVtaBaseExenta(BigDecimal.ZERO);
        invVentasTO.setVtaDescuentoBase0(BigDecimal.ZERO);
        invVentasTO.setVtaDescuentoBaseImponible(BigDecimal.ZERO);
        invVentasTO.setVtaDescuentoBaseExenta(BigDecimal.ZERO);
        invVentasTO.setVtaDescuentoBaseNoObjeto(BigDecimal.ZERO);
        invVentasTO.setVtaSubtotalBase0(BigDecimal.ZERO);
        invVentasTO.setVtaSubtotalBaseImponible(BigDecimal.ZERO);
        invVentasTO.setVtaSubtotalBaseExenta(BigDecimal.ZERO);
        invVentasTO.setVtaSubtotalBaseNoObjeto(BigDecimal.ZERO);
        invVentasTO.setVtaRecargoBase0(BigDecimal.ZERO);
        invVentasTO.setVtaRecargoBaseImponible(BigDecimal.ZERO);

        invVentasTO.setVtaMontoIva(BigDecimal.ZERO);
        invVentasTO.setVtaMontoIce(BigDecimal.ZERO);
        invVentasTO.setVtaTotal(BigDecimal.ZERO);

        //cliente
        invVentasTO.setCliEmpresa(empresa);
        invVentasTO.setCliCodigo(cliCodigo);
        //bodega
        invVentasTO.setBodEmpresa(empresa);
        invVentasTO.setBodCodigo(bodega);

        invVentasTO.setSecEmpresa(empresa);
        invVentasTO.setSecCodigo(secCodigo);
        //PRECIO VENTA
        invVentasTO.setVtaListaDePrecios(precioVenta);
        //formapago
        invVentasTO.setFcSecuencial(formaCobro);
        invVentasTO.setVtaFormaPago(fcDetalle);
        //usr
        invVentasTO.setUsrCodigo(usuarioCodigo);
        invVentasTO.setUsrEmpresa(empresa);
        invVentasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

        return invVentasTO;

    }

    public List<InvVentasDetalleTO> generarVentaDetalle(String empresa, String bodega, String secCodigo, BigDecimal porcentajeIva, InvProductoDAOTO producto, Factura factura) throws Exception {
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = new ArrayList<>();
        Integer i = 0;
        for (Factura.Detalles.Detalle det : factura.getDetalles().getDetalle()) {
            InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();
            invVentasDetalleTO.setDetOrden(i + 1);
            invVentasDetalleTO.setDetSecuencia(0);
            //venta
            invVentasDetalleTO.setVtaEmpresa(empresa);
            //producto
            invVentasDetalleTO.setProEmpresa(empresa);
            invVentasDetalleTO.setProCodigoPrincipal(producto.getProCodigoPrincipal());
            invVentasDetalleTO.setProNombre(det.getDescripcion());

            invVentasDetalleTO.setDetPendiente(false);
            //valores
            invVentasDetalleTO.setDetCantidad(det.getCantidad());
            invVentasDetalleTO.setDetPrecio(det.getPrecioUnitario());
            invVentasDetalleTO.setDetParcial(det.getCantidad().multiply(det.getPrecioUnitario()));

            invVentasDetalleTO.setDetPorcentajeDescuento(BigDecimal.ZERO);
            invVentasDetalleTO.setDetPorcentajeRecargo(BigDecimal.ZERO);
            //descuento
            if (det.getDescuento().compareTo(BigDecimal.ZERO) > 0) {
                invVentasDetalleTO.setDetPorcentajeDescuento(
                        det.getDescuento()
                                .divide(invVentasDetalleTO.getDetParcial())
                                .multiply(new BigDecimal("100.00")));
            }
            invVentasDetalleTO.setIcePorcentaje(BigDecimal.ZERO);
            invVentasDetalleTO.setIceTarifaFija(BigDecimal.ZERO);
            invVentasDetalleTO.setDetMontoIce(BigDecimal.ZERO);
            //bodega
            invVentasDetalleTO.setBodEmpresa(empresa);
            invVentasDetalleTO.setBodCodigo(bodega);
            //sector y piscina
            invVentasDetalleTO.setSecEmpresa(empresa);
            invVentasDetalleTO.setSecCodigo(secCodigo);

            invVentasDetalleTO.setPisEmpresa(null);//opcional
            invVentasDetalleTO.setPisSector(null);//opcional
            invVentasDetalleTO.setPisNumero(null);//opcional

            invVentasDetalleTO.setProComplementario(null);
            invVentasDetalleTO.setDetObservaciones(null);
            invVentasDetalleTO.setDetEmpaque(null);
            invVentasDetalleTO.setDetEmpaqueCantidad(null);
            invVentasDetalleTO.setDetConversionPesoNeto(null);
            //*************IMPUESTOS*************
            if (det.getImpuestos().getImpuesto() != null) {
                for (ImpuestoFactura impuesto : det.getImpuestos().getImpuesto()) {
                    //descuento
                    if (impuesto.getCodigo().equals("2")) {//IVA
                        if (impuesto.getTarifa().compareTo(BigDecimal.ZERO) != 0) {
                            invVentasDetalleTO.setVtIva(true);
                            invVentasDetalleTO.setMontoIVa(impuesto.getValor());
                        }
                    }
                    if (impuesto.getCodigo().equals("3")) {//ICE
                        invVentasDetalleTO.setDetMontoIce(impuesto.getValor());
                    }
                }
            }
            i++;
            listaInvVentasDetalleTO.add(invVentasDetalleTO);
        }

        return listaInvVentasDetalleTO;
    }

    @Override
    public String insertarComprobantesEmitidosLote(List<ComprobanteImportarTO> listaComprobantesElectronicos, SisInfoTO sisInfoTOAux) throws Exception {
        String mensaje = "";
        SisEmpresaTO sisEmpresa = null;

        List<ComboGenericoTO> listaClientesInsertado = new ArrayList<>();
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
                    String claveAcceso = comprobanteInsertar.getClaveAcceso();
                    String mensajeAux = "T";
                    String razonSocialReceptor = null;
                    if (comprobanteInsertar.getXml() != null && claveAcceso != null) {
                        String xmlAutorizacion = null;
                        try {
                            byte[] result = Base64.getDecoder().decode(comprobanteInsertar.getXml());
                            xmlAutorizacion = new String((byte[]) result, "UTF-8");
                        } catch (Exception e) {
                            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                            mensajeAux = "FError al decodificar el archivo :" + e.getMessage();
                        }
                        if (xmlAutorizacion != null) {
                            String tipoComprobante = claveAcceso.substring(8, 10);
                            String identificacionReceptor = null;
                            String identificacionEmisor = null;
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
                                //cuando fecha tiene este formato-->2021-07-26T01:52:41-05:00 
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
                                    //************
                                    Date fechaAut = UtilsValidacion.fecha(fechaAutFormato, "yyyy-MM-dd HH:mm:ss");
                                    if (fechaAut != null) {
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

                                        AnxComprobantesElectronicosEmitidos emitido = new AnxComprobantesElectronicosEmitidos();
                                        emitido.setAnxComprobantesElectronicosEmitidosPk(new AnxComprobantesElectronicosEmitidosPk(null, periodo, claveAcceso));
                                        emitido.setComproComprobante(comprobanteNombre);
                                        emitido.setComproSerieComprobante(comprobanteSerie);
                                        emitido.setComproFechaAutorizacion(fechaAut);
                                        emitido.setComproTipoEmision("NORMAL");
                                        emitido.setComproNumeroAutorizacion(claveAcceso);
                                        emitido.setComproImporteTotal(new BigDecimal(total != null ? total : "0.00"));
                                        emitido.setComproXml(xmlAutorizacion.getBytes("UTF-8"));
                                        emitido.setComproComplemento(complemento);
                                        emitido.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                        //*************OBTENER DATOS DE XML******************
                                        Node contenidoComprobanteXm = obtenerNodeComprobante(xmlAutorizacion);
                                        if (contenidoComprobanteXm != null) {
                                            if (tipoComprobante.compareTo("01") == 0) {//Factura
                                                FacturaReporte facturaReporte = new FacturaReporte((Factura) ArchivoUtils.unmarshal(Factura.class, contenidoComprobanteXm));
                                                Factura factura = facturaReporte != null ? facturaReporte.getFactura() : null;
                                                if (factura != null) {
                                                    identificacionEmisor = factura.getInfoTributaria().getRuc();
                                                    identificacionReceptor = factura.getInfoFactura().getIdentificacionComprador();
                                                    razonSocialReceptor = factura.getInfoFactura().getRazonSocialComprador();
                                                    String fecha = (UtilsValidacion.fecha(factura.getInfoFactura().getFechaEmision(), "dd/MM/yyyy", "yyyy-MM-dd"));

                                                    emitido.setComproRucEmisor(identificacionEmisor);
                                                    emitido.setComproIdentificacionReceptor(identificacionReceptor);
                                                    emitido.setComproRazonSocialReceptor(razonSocialReceptor);
                                                    emitido.setComproFechaEmision(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                                    claveAccesoXML = factura.getInfoTributaria().getClaveAcceso();
                                                }

                                            } else if (tipoComprobante.compareTo("04") == 0) {//credito
                                                NotaCreditoReporte nc = new NotaCreditoReporte((NotaCredito) ArchivoUtils.unmarshal(NotaCredito.class, contenidoComprobanteXm));
                                                NotaCredito notaCredito = nc.getNotaCredito() != null ? nc.getNotaCredito() : null;
                                                if (notaCredito != null) {
                                                    identificacionEmisor = notaCredito.getInfoTributaria().getRuc();
                                                    identificacionReceptor = notaCredito.getInfoNotaCredito().getIdentificacionComprador();
                                                    razonSocialReceptor = notaCredito.getInfoNotaCredito().getRazonSocialComprador();
                                                    String fecha = (UtilsValidacion.fecha(notaCredito.getInfoNotaCredito().getFechaEmision(), "dd/MM/yyyy", "yyyy-MM-dd"));

                                                    emitido.setComproRucEmisor(identificacionEmisor);
                                                    emitido.setComproIdentificacionReceptor(identificacionReceptor);
                                                    emitido.setComproRazonSocialReceptor(razonSocialReceptor);
                                                    emitido.setComproFechaEmision(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                                    claveAccesoXML = notaCredito.getInfoTributaria().getClaveAcceso();
                                                }
                                            } else if (tipoComprobante.compareTo("05") == 0) {//debito
                                                //falta...
                                            }

                                            if (identificacionEmisor != null) {
                                                //se ingresa desde acosux
                                                String codigoEmpresa = null;
                                                if (sisInfoTOAux != null) {
                                                    SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTOAux.getEmpresa());
                                                    if (sisEmpresaParametros != null) {
                                                        if (!identificacionEmisor.equalsIgnoreCase(sisEmpresaParametros.getEmpCodigo().getEmpRuc())) {
                                                            mensajeAux = "FRuc de emisor: " + identificacionEmisor + " no pertenece a la empresa "
                                                                    + sisInfoTOAux.getEmpresa() + " , con RUC:" + sisEmpresaParametros.getEmpCodigo().getEmpRuc();
                                                        } else {
                                                            codigoEmpresa = sisInfoTOAux.getEmpresa();
                                                        }
                                                    } else {
                                                        mensajeAux = "FNo existe parámetros de la empresa: " + sisInfoTOAux.getEmpresa();
                                                    }
                                                } else {
                                                    sisEmpresa = empresaDao.obtenerSisEmpresaTOByRUC(identificacionEmisor);
                                                    if (sisEmpresa != null) {
                                                        codigoEmpresa = sisEmpresa.getEmpCodigo();
                                                    } else {
                                                        mensajeAux = "FNo se logró encontrar la empresa en el sistema con el siguiente RUC: " + identificacionEmisor;
                                                    }
                                                }

                                                if (codigoEmpresa != null) {
                                                    sisInfoTO.setEmpresa(codigoEmpresa);
                                                    emitido.getAnxComprobantesElectronicosEmitidosPk().setComproEmpresa(codigoEmpresa);
                                                    emitido.setUsrCodigo(sisInfoTO.getUsuario());
                                                    emitido.setUsrEmpresa(codigoEmpresa);
                                                    if (claveAccesoXML != null && claveAccesoXML.equals(claveAcceso)) {
                                                        //EVALUAR INSERCIÓN DE CLIENTE
                                                        if (razonSocialReceptor != null && identificacionReceptor != null) {
                                                            boolean clienteRepetido = true;
                                                            if (identificacionReceptor.equals("9999999999999")) {
                                                                clienteRepetido = clienteService.getClienteRepetido("'" + sisInfoTO.getEmpresa() + "'", "", "", null, "'CONSUMIDOR FINAL'");
                                                            } else {
                                                                clienteRepetido = clienteService.getClienteRepetido("'" + sisInfoTO.getEmpresa() + "'", null, "'" + emitido.getComproIdentificacionReceptor() + "'", null, null);
                                                            }
                                                            if (!clienteRepetido) {
                                                                List<ComboGenericoTO> fueInsertado = null;
                                                                fueInsertado = listaClientesInsertado.stream()
                                                                        .filter(item -> item.getClave().equals(emitido.getComproIdentificacionReceptor())
                                                                        || item.getValor().equals(emitido.getComproRazonSocialReceptor()))
                                                                        .collect(Collectors.toList());

                                                                if (fueInsertado == null || fueInsertado.isEmpty()) {
                                                                    InvClienteTO clienteTO = setearValoresCliente(
                                                                            sisInfoTO.getEmpresa(),
                                                                            sisInfoTO.getUsuario(),
                                                                            emitido
                                                                    );
                                                                    //consumidor final
                                                                    if (emitido.getComproIdentificacionReceptor().equals("9999999999999")) {
                                                                        clienteTO.setCliCodigo("00000");
                                                                        clienteTO.setCliRazonSocial("CONSUMIDOR FINAL");
                                                                        clienteTO.setCliNombreComercial("");
                                                                        clienteTO.setCliId("");
                                                                        clienteTO.setCliTipoId('F');
                                                                    }
                                                                    //vendedor
                                                                    List<InvVendedorComboListadoTO> listaVendedores = vendedorService.getComboinvListaVendedorTOs(sisInfoTO.getEmpresa(), null);
                                                                    if (listaVendedores != null && listaVendedores.size() > 0) {
                                                                        clienteTO.setVendEmpresa(sisInfoTO.getEmpresa());
                                                                        clienteTO.setVendCodigo(listaVendedores.get(0).getVendCodigo());
                                                                        //categoria
                                                                        List<InvClienteCategoriaTO> listaCategorias = clienteCategoriaService.getInvClienteCategoriaTO(sisInfoTO.getEmpresa());
                                                                        if (listaCategorias != null && listaCategorias.size() > 0) {
                                                                            clienteTO.setCliCategoria(listaCategorias.get(0).getCcCodigo());
                                                                        } else {
                                                                            mensajeAux = "FNo se puede guardar cliente con identificación:" + identificacionReceptor
                                                                                    + ", debido a que no existe categorías de cliente";
                                                                        }
                                                                    } else {
                                                                        mensajeAux = "FNo se puede guardar cliente con identificación:" + identificacionReceptor
                                                                                + ", debido a que no existe vendedores de cliente";
                                                                    }
                                                                    if (mensajeAux != null && mensajeAux.substring(0, 1).equals("T")) {
                                                                        String mensajeCliente = clienteService.insertarInvClienteTO(clienteTO, null, sisInfoTO);
                                                                        if (mensajeCliente != null && mensajeCliente.substring(0, 1).equals("T")) {
                                                                            ComboGenericoTO item = new ComboGenericoTO();
                                                                            item.setClave(clienteTO.getCliId());//identificacion
                                                                            item.setValor(clienteTO.getCliRazonSocial());//razon social
                                                                            listaClientesInsertado.add(item);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        //INSERCIÓN O ACTUALIZACIÓN DE COMPROBANTE EMITIDO
                                                        AnxComprobantesElectronicosEmitidos comprobante = anxComprobantesElectronicosEmitidosDao.obtenerPorId(
                                                                AnxComprobantesElectronicosEmitidos.class,
                                                                emitido.getAnxComprobantesElectronicosEmitidosPk());
                                                        //preparando suceso
                                                        susClave = claveAcceso;
                                                        susDetalle = "Se insertó comprobante: " + comprobanteSerie + "del período " + periodo;
                                                        susSuceso = "INSERT";
                                                        susTabla = "anexo.anx_comprobantes_electronicos_emitidos";
                                                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                                        //actualizar
                                                        if (comprobante != null) {
                                                            mensajeAux = "F" + emitido.getComproComprobante() + " --> "
                                                                    + "(" + comprobanteSerie + ") "
                                                                    + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", ya se ingresó anteriormente.";
                                                        } else {
                                                            //insertar
                                                            if (anxComprobantesElectronicosEmitidosDao.insertarComprobantesElectronico(emitido, sisSuceso)) {
                                                                mensajeAux = "T" + emitido.getComproComprobante() + " --> "
                                                                        + "(" + emitido.getComproSerieComprobante() + ") "
                                                                        + "El comprobante electrónico con clave de acceso :" + claveAcceso + ", se ha guardado correctamente.";
                                                            } else {
                                                                mensajeAux = "F" + emitido.getComproComprobante() + " --> "
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
                                        } else {
                                            mensajeAux = "FHubo un error al obtener el contenido del XML";
                                        }
                                    } else {
                                        mensajeAux = "FHubo un error al obtener la fecha de autorización";
                                    }
                                } else {
                                    mensajeAux = "FEl archivo XML no contiene datos de autorización";
                                }
                            } else {
                                mensajeAux = "FEl XML es incorrecto";
                            }
                        }
                    } else {
                        mensajeAux = "FDebe ingresar clave de acceso y xml";
                    }
                    mensaje += mensajeAux + "|";
                }
            } else {
                mensaje += "FNo existe listado por importar" + "|";
            }
        } catch (GeneralException e) {
            mensaje += "F" + e.getMessage() + "|";
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            mensaje += "F" + e.getMessage() + "|";
        }
        return mensaje;
    }

}
