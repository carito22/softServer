package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.Emisor;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxComprobanteElectronicoUtilTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.anxUrlWebServicesTO;
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
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.recepcion.RespuestaSolicitud;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.Date;
import java.util.List;

@Transactional
public interface UrlWebServicesService {

    public String accionAnxUrlWebServicesTO(anxUrlWebServicesTO anxUrlWebServicesTO, char accion, SisInfoTO sisInfoTO)
            throws Exception;

    public anxUrlWebServicesTO getAnxUrlWebServicesTO() throws Exception;

    public RespuestaSolicitud getRecepcionComprobantes(byte[] eXmlFirmado, String tipoAmbiente, SisInfoTO sisInfoTO)
            throws Exception;

    public RespuestaComprobante getAutorizadocionComprobantes(String claveAcceso, String tipoAmbiente,
            SisInfoTO sisInfoTO) throws Exception;

    public AnxComprobanteElectronicoUtilTO getEnvioComprobanteElectronicosWS(byte[] eXmlFirmado, String claveAcceso,
            String tipoAmbiente, SisInfoTO sisInfoTO) throws Exception;

    public void verificarAnxUrlWebServicesTO(String urlVerificacion) throws GeneralException;

    public boolean verifcarDisponibilidadSRI() throws GeneralException;

    public RespuestaWebTO enviarAutorizarRetencion(String claveAcceso, String agenteRetencion, String passSignature, InvProveedor invProveedor,
            Emisor emisor, InvComprasTO invComprasTO, AnxCompraTO anxCompraTO, List<AnxCompraDetalleTO> listAnxCompraDetalleTO, byte[] archivoFirma, SisEmpresaParametros sisEmpresaParametros) throws Exception;

    public RespuestaWebTO enviarAutorizarFacturaElectronica(String direccion, String complementoDocumentoNumero, String tipoIdentificacion, String claveAcceso, String tipoComprobante, String passSignature,
            InvCliente invCliente, Emisor emisor, InvVentas invVentas, InvVentaGuiaRemision guia, SisEmpresaParametros sisEmpresaParametros, Date fechaComplemento,
            List<InvListaDetalleVentasTO> listaInvVentasDetalleTO, List<AnxVentaReembolsoTO> listaAnxVentaReembolsoTO, AnxVentaExportacion exportacion, byte[] archivoFirma) throws Exception;

    public RespuestaWebTO enviarAutorizarLiquidacionCompras(String claveAcceso, String agenteRetencion, String passSignature, InvProveedor invProveedor, Emisor emisor, InvComprasTO invComprasTO,
            List<InvComprasDetalle> listaInvComprasDetalle, byte[] archivoFirma, SisEmpresaParametros sisEmpresaParametros) throws Exception;

    public RespuestaWebTO enviarAutorizarGuiaRemision(String claveAcceso, String agenteRetencion, String passSignature, InvCliente invCliente, Emisor emisor, InvTransportista invTransportista,
            List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetalleTO, byte[] archivoFirma, InvGuiaRemision invGuiaRemision, SisEmpresaParametros sisEmpresaParametros) throws Exception;

    public String generarXmlComprobantes(String claveAcceso, String tipoAmbiente) throws Exception;
}
