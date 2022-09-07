package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.UrlWebServicesDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.Emisor;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxComprobanteElectronicoUtilTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.anxUrlWebServicesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxUrlWebServices;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportista;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.recepcion.Comprobante;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.recepcion.RespuestaSolicitud;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class UrlWebServicesServiceImpl implements UrlWebServicesService {

    @Autowired
    private UrlWebServicesDao urlWebServicesDao;

    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    private boolean comprobar = false;
    private String mensaje;
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${sri.url}")
    private String SRIURL;

    @Override
    public String accionAnxUrlWebServicesTO(anxUrlWebServicesTO anxUrlWebServicesTO, char accion, SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        // /// CREANDO Suceso
        susClave = anxUrlWebServicesTO.getUsrEmpresa() + " " + sisInfoTO.getEmpresa();
        if (accion == 'I') {
            susDetalle = "Se insertó la ruta de los directorios para los documentos electronico ";
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó la ruta de los directorios para los documentos electronico ";
            susSuceso = "UPDATE";
        }
        susTabla = "anexo.anx_url_web_services";
        // /// CREANDO invProductoMedida
        AnxUrlWebServices anxUrlWebServices = ConversionesAnexos
                .convertirAnxUrlWebServicesTO_AnxUrlWebServices(anxUrlWebServicesTO);
        if (accion == 'M') {
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = urlWebServicesDao.accionAnxUrlWebServixce(anxUrlWebServices, sisSuceso, accion);
        }
        if (accion == 'I') {
            // //// BUSCANDO existencia Categoría
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = urlWebServicesDao.accionAnxUrlWebServixce(anxUrlWebServices, sisSuceso, accion);
        }
        if (comprobar) {
            if (accion == 'M') {
                mensaje = "TSe modificó correctamente  los datos...";
            }
            if (accion == 'I') {
                mensaje = "TSe ingresó correctamente  los datos...";
            }
        }
        return mensaje;
    }

    @Override
    public anxUrlWebServicesTO getAnxUrlWebServicesTO() throws Exception {
        return urlWebServicesDao.getAnxUrlWebServicesTO();
    }

    @Override
    public RespuestaSolicitud getRecepcionComprobantes(byte[] eXmlFirmado, String tipoAmbiente, SisInfoTO sisInfoTO) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("eXmlFirmado", eXmlFirmado);
            map.put("tipoAmbiente", tipoAmbiente);
            RespuestaSolicitud resp = restTemplate.postForObject(SRIURL + "/consultasSRI/getRecepcionComprobantes", map, RespuestaSolicitud.class);
            return resp;
        } catch (RestClientException e) {
            throw new GeneralException(e.getMessage());
        }
    }

    @Override
    public RespuestaComprobante getAutorizadocionComprobantes(String claveAcceso, String tipoAmbiente, SisInfoTO sisInfoTO) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("claveAcceso", claveAcceso);
            map.put("tipoAmbiente", tipoAmbiente);
            RespuestaComprobante resp = restTemplate.postForObject(SRIURL + "/consultasSRI/getRespuestaComprobante", map, RespuestaComprobante.class);
            return resp;
        } catch (RestClientException e) {
            throw new GeneralException(e.getMessage());
        }
    }

    @Override
    public String generarXmlComprobantes(String claveAcceso, String tipoAmbiente) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("claveAcceso", claveAcceso);
            map.put("tipoAmbiente", tipoAmbiente);
            String resp = restTemplate.postForObject(SRIURL + "/consultasSRI/generarXmlComprobantes", map, String.class);
            return resp;
        } catch (RestClientException e) {
            throw new GeneralException(e.getMessage());
        }
    }

    @Override
    public RespuestaWebTO enviarAutorizarRetencion(String claveAcceso, String agenteRetencion, String passSignature, InvProveedor invProveedor, Emisor emisor, InvComprasTO invComprasTO,
            AnxCompraTO anxCompraTO, List<AnxCompraDetalleTO> listAnxCompraDetalleTO, byte[] archivoFirma, SisEmpresaParametros sisEmpresaParametros) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("claveAcceso", claveAcceso);
            map.put("agenteRetencion", agenteRetencion);
            map.put("passSignature", passSignature);
            map.put("emisor", emisor);
            map.put("invComprasTO", invComprasTO);
            map.put("anxCompraTO", anxCompraTO);
            map.put("listAnxCompraDetalleTO", listAnxCompraDetalleTO);
            map.put("invProveedor", invProveedor);
            map.put("archivoFirma", archivoFirma);
            map.put("sisEmpresaParametros", sisEmpresaParametros);
            RespuestaWebTO resp = restTemplate.postForObject(SRIURL + "/consultasSRI/enviarAutorizarRetencion", map, RespuestaWebTO.class);
            return resp;
        } catch (RestClientException e) {
            throw new GeneralException(e.getMessage());
        }
    }

    @Override
    public RespuestaWebTO enviarAutorizarLiquidacionCompras(String claveAcceso, String agenteRetencion, String passSignature, InvProveedor invProveedor, Emisor emisor, InvComprasTO invComprasTO,
            List<InvComprasDetalle> listaInvComprasDetalle, byte[] archivoFirma, SisEmpresaParametros sisEmpresaParametros) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("claveAcceso", claveAcceso);
            map.put("agenteRetencion", agenteRetencion);
            map.put("passSignature", passSignature);
            map.put("emisor", emisor);
            map.put("invComprasTO", invComprasTO);
            map.put("listaInvComprasDetalle", listaInvComprasDetalle);
            map.put("invProveedor", invProveedor);
            map.put("archivoFirma", archivoFirma);
            map.put("sisEmpresaParametros", sisEmpresaParametros);
            RespuestaWebTO resp = restTemplate.postForObject(SRIURL + "/consultasSRI/enviarAutorizarLiquidacionCompras", map, RespuestaWebTO.class);
            return resp;
        } catch (RestClientException e) {
            throw new GeneralException(e.getMessage());
        }
    }

    @Override
    public RespuestaWebTO enviarAutorizarGuiaRemision(String claveAcceso, String agenteRetencion, String passSignature, InvCliente invCliente, Emisor emisor, InvTransportista invTransportista,
            List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetalleTO, byte[] archivoFirma, InvGuiaRemision invGuiaRemision, SisEmpresaParametros sisEmpresaParametros) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("claveAcceso", claveAcceso);
            map.put("agenteRetencion", agenteRetencion);
            map.put("passSignature", passSignature);
            map.put("emisor", emisor);
            map.put("invTransportista", invTransportista);
            map.put("invGuiaRemision", invGuiaRemision);
            map.put("emision", invGuiaRemision.getGuiaFechaEmision());
            map.put("inicio", invGuiaRemision.getGuiaFechaInicioTransporte());
            map.put("fin", invGuiaRemision.getGuiaFechaFinTransporte());
            map.put("listaInvGuiaRemisionDetalleTO", listaInvGuiaRemisionDetalleTO);
            map.put("invCliente", invCliente);
            map.put("archivoFirma", archivoFirma);
            map.put("sisEmpresaParametros", sisEmpresaParametros);

            RespuestaWebTO resp = restTemplate.postForObject(SRIURL + "/consultasSRI/enviarAutorizarGuiaRemision", map, RespuestaWebTO.class);
            return resp;
        } catch (RestClientException e) {
            throw new GeneralException(e.getMessage());
        }
    }

    @Override
    public RespuestaWebTO enviarAutorizarFacturaElectronica(String direccion, String complementoDocumentoNumero, String tipoIdentificacion, String claveAcceso, String tipoComprobante, String passSignature,
            InvCliente invCliente, Emisor emisor, InvVentas invVentas, InvVentaGuiaRemision guia, SisEmpresaParametros sisEmpresaParametros, Date fechaComplemento,
            List<InvListaDetalleVentasTO> listaInvVentasDetalleTO, List<AnxVentaReembolsoTO> listaAnxVentaReembolsoTO, AnxVentaExportacion exportacion, byte[] archivoFirma) throws Exception {
        try {
            invVentas.setInvVentasDetalleList(new ArrayList<>());
            invVentas.setInvProformas(null);
            invVentas.setInvVentasRecepcionList(new ArrayList<>());
            invVentas.setInvVentasMotivoAnulacionList(new ArrayList<>());
            invVentas.setInvVentasComplementoList(new ArrayList<>());
            Map<String, Object> map = new HashMap<>();
            map.put("direccion", direccion);
            map.put("complementoDocumentoNumero", complementoDocumentoNumero);
            map.put("tipoIdentificacion", tipoIdentificacion);
            map.put("claveAcceso", claveAcceso);
            map.put("tipoComprobante", tipoComprobante);
            map.put("passSignature", passSignature);
            map.put("emisor", emisor);
            map.put("invVentas", invVentas);
            map.put("guia", guia);
            map.put("listaInvVentasDetalleTO", listaInvVentasDetalleTO);
            invCliente.setInvVentasList(new ArrayList<>());
            map.put("invCliente", invCliente);
            map.put("sisEmpresaParametros", sisEmpresaParametros);
            map.put("fechaComplemento", fechaComplemento);
            map.put("archivoFirma", archivoFirma);
            map.put("exportacion", exportacion);
            map.put("listaAnxVentaReembolsoTO", listaAnxVentaReembolsoTO);
            RespuestaWebTO resp = restTemplate.postForObject(SRIURL + "/consultasSRI/enviarAutorizarFacturaElectronica", map, RespuestaWebTO.class);
            return resp;
        } catch (RestClientException e) {
            throw new GeneralException(e.getMessage());
        }
    }

    @Override
    public AnxComprobanteElectronicoUtilTO getEnvioComprobanteElectronicosWS(byte[] eXmlFirmado, String claveAcceso,
            String tipoAmbiente, SisInfoTO sisInfoTO) throws Exception {
        AnxComprobanteElectronicoUtilTO anxComprobanteElectronicoUtilTO = new AnxComprobanteElectronicoUtilTO();
        RespuestaSolicitud respuestaSolicitud = getRecepcionComprobantes(eXmlFirmado, tipoAmbiente, sisInfoTO);
        if (respuestaSolicitud == null) {
            anxComprobanteElectronicoUtilTO
                    .setMensaje("F<ERROR>: Sin conexión con Web Services del SRI, "
                            + "Certificados incorrectos(Prueba o Producción), "
                            + "Firma electrónica Incorrecta");
        } else {
            if (respuestaSolicitud.getEstado() == null) {
                anxComprobanteElectronicoUtilTO
                        .setMensaje("F<ERROR>: Sin conexión con Web Services del SRI, "
                                + "Certificados incorrectos(Prueba o Producción), "
                                + "Firma electrónica Incorrecta");
            } else {
                anxComprobanteElectronicoUtilTO.setMensaje("TRespuesta de comprobante SRI");
                String mensajeRecepcion = "";
                if (respuestaSolicitud.getEstado().compareTo("DEVUELTA") == 0) {
                    for (Comprobante comp : respuestaSolicitud.getComprobantes().getComprobante()) {
                        for (ec.com.todocompu.ShrimpSoftUtils.sri.ws.recepcion.Mensaje m : comp.getMensajes().getMensaje()) {
                            if (m.getMensaje().compareTo("CLAVE ACCESO REGISTRADA") == 0) {
                                mensajeRecepcion = m.getMensaje();
                                break;
                            }
                        }
                    }
                }

                if (respuestaSolicitud.getEstado().equals("RECIBIDA")
                        || (respuestaSolicitud.getEstado().compareTo("DEVUELTA") == 0
                        && mensajeRecepcion.compareTo("CLAVE ACCESO REGISTRADA") == 0)) {
                    respuestaSolicitud.setEstado("RECIBIDA");
                    RespuestaComprobante respuestaComprobante = getAutorizadocionComprobantes(claveAcceso, tipoAmbiente, sisInfoTO);
                    anxComprobanteElectronicoUtilTO.setRespuestaComprobante(respuestaComprobante);
                }

                anxComprobanteElectronicoUtilTO.setRespuestaSolicitud(respuestaSolicitud);
            }
        }
        return anxComprobanteElectronicoUtilTO;
    }

    @Override
    public void verificarAnxUrlWebServicesTO(String urlVerificacion) throws GeneralException {
        try {
            if (urlVerificacion.equals("https://cel.sri.gob.ec")) {
                urlVerificacion = urlVerificacion.concat("/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl");
            }
            URI uri = new URI(urlVerificacion);
            URL urlC = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) urlC.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                mensaje = ("Failed : HTTP Error code : " + conn.getResponseCode());
            } else {
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(in);
                String output;
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
                mensaje = "TSe verifico correctamente URL";
            }
            conn.disconnect();
        } catch (Exception e) {
            throw new GeneralException("La URL no respondio correctamente");
        }
    }

    @Override
    public boolean verifcarDisponibilidadSRI() throws GeneralException {
        try {
            URI uri = new URI("https://cel.sri.gob.ec".concat("/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl"));
            URL urlC = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) urlC.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                conn.disconnect();
                return false;
            } else {
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(in);
                String output;
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
                conn.disconnect();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
