package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.SustentoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario103TO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario104TO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesFuenteIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesRentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxUltimaAutorizacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxSustento;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntosComprasWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaSecuencialesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisLoginTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoComprobanteEnum;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.tomcat.util.codec.binary.Base64;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraDao compraDao;
    @Autowired
    private SustentoDao sustentoDao;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private VentaService ventaService;
    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    @Autowired
    private AnexoNumeracionService numeracionService;
    @Autowired
    private ComprasService comprasService;

    @Override
    public AnxCompraTO getAnexoCompraTO(String empresa, String periodo, String motivo, String numeroCompra) throws Exception {
        return compraDao.getAnexoCompraTO(empresa, periodo, motivo, numeroCompra);
    }

    @Override
    public AnxCompra getAnexoCompra(String empresa, String periodo, String motivo, String numeroCompra)
            throws Exception {
        return compraDao.obtenerPorId(AnxCompra.class, new AnxCompraPK(empresa, periodo, motivo, numeroCompra));
    }

    @Override
    public AnxCompraTO getAnexoCompraTO(String empresa, String numeroDocumento) throws Exception {
        return compraDao.getAnexoCompraTO(empresa, numeroDocumento);
    }

    @Override
    public List<AnxListaRetencionesTO> getAnexoListaRetencionesTO(String empresa, String fechaDesde, String fechaHasta)
            throws Exception {
        return compraDao.getAnexoListaRetencionesTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxListaRetencionesRentaTO> getAnexoListaRetencionesRentaTO(String empresa, String fechaDesde,
            String fechaHasta, String pOrden) throws Exception {
        return compraDao.getAnexoListaRetencionesRentaTO(empresa, fechaDesde, fechaHasta, pOrden);
    }

    @Override
    public List<AnxListaRetencionesFuenteIvaTO> getAnexoListaRetencionesFuenteIvaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        return compraDao.getAnexoListaRetencionesFuenteIvaTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxListaRetencionesTO> getAnexoFunListadoRetencionesPorNumero(String empresa, String fechaDesde,
            String fechaHasta, String parametroEstado) throws Exception {
        return compraDao.getAnexoFunListadoRetencionesPorNumero(empresa, fechaDesde, fechaHasta, parametroEstado);
    }

    @Override
    public List<AnxTalonResumenTO> getAnexoTalonResumenTO(String empresa, String fechaDesde, String fechaHasta)
            throws Exception {
        return compraDao.getAnexoTalonResumenTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxFormulario103TO> listarFormulario103(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        return compraDao.listarFormulario103(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxFormulario104TO> listarFormulario104(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        return compraDao.listarFormulario104(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxRetencionCompraTO> listarAnxRetencionCompraTO(String empresa, String fechaDesde, String fechaHasta, String codigoSustento, String registros) throws Exception {
        return compraDao.listarAnxRetencionCompraTO(empresa, fechaDesde, fechaHasta, codigoSustento, registros);
    }

    @Override
    public List<AnxRetencionVentaTO> getListaAnxRetencionVentaTO(String empresa, String fechaDesde, String fechaHasta, String registro) throws Exception {
        return compraDao.getListaAnxRetencionVentaTO(empresa, fechaDesde, fechaHasta, registro);
    }

    @Override
    public List<AnxFunListadoDevolucionIvaTO> getAnxFunListadoDevolucionIvaTOs(String empCodigo, String desde,
            String hasta) throws Exception {
        return compraDao.getAnxFunListadoDevolucionIvaTOs(empCodigo, desde, hasta);
    }

    @Override
    public String reestructurarRetencion(AnxCompraTO anxCompraTO, String usuario, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        AnxCompra anxCompra = compraDao.obtenerPorId(AnxCompra.class, new AnxCompraPK(anxCompraTO.getEmpCodigo(),
                anxCompraTO.getPerCodigo(), anxCompraTO.getMotCodigo(), anxCompraTO.getCompNumero()));
        AnxCompra anxCompraAux = ConversionesAnexos.convertirAnxCompra_AnxCompra(anxCompra);
        anxCompraAux.setCompSustentotributario(
                sustentoDao.obtenerPorId(AnxSustento.class, anxCompraTO.getCompSustentotributario()));
        anxCompraAux.setCompAutorizacion(anxCompraTO.getCompAutorizacion());
        // / PREPARANDO OBJETO SISSUCESO
        susClave = anxCompraTO.getPerCodigo() + " " + anxCompraTO.getMotCodigo() + " "
                + anxCompraTO.getCompNumero();
        susDetalle = "Se reestructuró la retención con código: " + anxCompraTO.getPerCodigo() + " "
                + anxCompraTO.getMotCodigo() + " " + anxCompraTO.getCompNumero();
        susTabla = "anexos.anx_compras";
        susSuceso = "UPDATE";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        // / PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
        if (compraDao.reestructurarRetencion(anxCompraAux, sisSuceso)) {
            retorno = "TLa retención se reestructuró correctamente...";
        } else {
            retorno = "FHubo un error al reestructurar la retención...\nIntente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public Boolean actualizarClaveExternaRetencion(AnxCompraPK pk, String clave, SisInfoTO sisInfoTO) throws Exception {
        // / PREPARANDO OBJETO SISSUCESO
        susClave = pk.getCompPeriodo() + " " + pk.getCompMotivo() + " "
                + pk.getCompNumero();
        susDetalle = "Se actualizó la clave externa de la retención con código: " + pk.getCompPeriodo() + " "
                + pk.getCompMotivo() + " " + pk.getCompNumero();
        susTabla = "anexos.anx_compras";
        susSuceso = "UPDATE";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return compraDao.actualizarClaveExternaRetencion(pk, clave, sisSuceso);
    }

    @Override
    public List<String> getAnexoFunListadoRetencionesHuerfanas(String empresa, String fechaDesde, String fechaHasta)
            throws Exception {
        return compraDao.getAnexoFunListadoRetencionesHuerfanas(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<String> getAnexoFunListadoComprobantesPendientes(String empresa, String fechaDesde, String fechaHasta)
            throws Exception {
        return compraDao.getAnexoFunListadoComprobantesPendientes(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public String getCompAutorizacion(String empCodigo, String provCodigo, String codTipoComprobante,
            String numComplemento) throws Exception {
        return compraDao.getCompAutorizacion(empCodigo, provCodigo, codTipoComprobante, numComplemento);
    }

    @Override
    public List<AnxListadoCompraElectronicaTO> getListaAnxComprasElectronicaTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception {
        return compraDao.getListaAnxComprasElectronicaTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteTO(String empresa) throws Exception {
        return compraDao.getAnexoListaRetencionesPendienteTO(empresa);
    }

    @Override
    public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteAutomaticasTO(String empresa) throws Exception {
        return compraDao.getAnexoListaRetencionesPendienteAutomaticasTO(empresa);
    }

    @Override
    public AnxUltimaAutorizacionTO getAnxUltimaAutorizacionTO(String empresa, String proveedor, String tipoDocumento,
            String secuencial, String fechaFactura) throws Exception {
        return compraDao.getAnxUltimaAutorizacionTO(empresa, proveedor, tipoDocumento, secuencial, fechaFactura);
    }

    @Override
    public String validarSecuenciaPermitida(String empresa, String numeroRetencion, SisInfoTO sisInfoTO) throws Exception {
        SisLoginTO sisLoginTO = usuarioService.getPermisoInventarioTO(sisInfoTO.getEmpresa(), sisInfoTO.getUsuario());
        String mensaje = "T";
        if (sisLoginTO.getPerSecuencialPermitidoRetenciones() != null) {
            String ultimoSecuencial = ventaService.getUltimaNumeracionComprobante(
                    empresa,
                    TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode(),
                    sisLoginTO.getPerSecuencialPermitidoRetenciones());
            if (!numeroRetencion.substring(0, 7).equalsIgnoreCase(sisLoginTO.getPerSecuencialPermitidoRetenciones())) {
                mensaje = "F" + "-El número de retención no cumple con el secuencial permitido.\n";
            }
        }
        return mensaje;
    }

    @Override
    public List<InvListaSecuencialesTO> listarInvListaSecuencialesRetenciones(String empresa, String fecha) throws Exception {
        List<AnxNumeracionTablaTO> numeraciones = numeracionService.getListaAnexoNumeracionTO(empresa, TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode(), fecha);
        List<InvListaSecuencialesTO> lista = new ArrayList<>();

        if (numeraciones != null && !numeraciones.isEmpty()) {
            for (AnxNumeracionTablaTO item : numeraciones) {
                InvListaSecuencialesTO secuencialVenta = new InvListaSecuencialesTO();
                String numeroDocumento = "";

                String[] partsSecuencial = item.getNumeroDesde().split("-");
                String secuencialPermitido = partsSecuencial[0] + "-" + partsSecuencial[1];//001-001 ejemplo
                String ultimoSecuencial = ventaService.getUltimaNumeracionComprobante(empresa, TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode(), secuencialPermitido);

                if (ultimoSecuencial != null && !"".equals(ultimoSecuencial)) {
                    String separado = ultimoSecuencial.substring(0, ultimoSecuencial.lastIndexOf("-") + 1);
                    int numero = Integer.parseInt(ultimoSecuencial.substring(ultimoSecuencial.lastIndexOf("-") + 2));
                    int numeroParaDocumento = numero + 1;
                    int digitosNumero = ("" + numeroParaDocumento).length();
                    for (int i = 0; i < (9 - digitosNumero); i++) {
                        numeroDocumento = numeroDocumento + "0";
                    }
                    numeroDocumento = separado + numeroDocumento + numeroParaDocumento;
                    if (numero >= item.getSecuenciaDesde() && numero <= item.getSecuenciaHasta()) {
                        secuencialVenta.setNumAutorizacion(item.getNumeroAutorizacion());
                        secuencialVenta.setNumObservacion(item.getNumObservacion() != null ? (item.getNumObservacion()).toUpperCase() : "");
                        secuencialVenta.setSecuencial(numeroDocumento);
                        lista.add(secuencialVenta);
                    }

                } else {
                    numeroDocumento = secuencialPermitido + "-000000001";
                    secuencialVenta.setNumAutorizacion(item.getNumeroAutorizacion());
                    secuencialVenta.setNumObservacion(item.getNumObservacion() != null ? (item.getNumObservacion()).toUpperCase() : "");
                    secuencialVenta.setSecuencial(numeroDocumento);
                    lista.add(secuencialVenta);
                }

            }
        }

        return lista;
    }

    @Override
    public List<InvAdjuntosComprasWebTO> obtenerListadoImagenesCompraNecesitaSoporte(String empresa, List<AnxFunListadoDevolucionIvaTO> listado) throws Exception {
        List<InvAdjuntosComprasWebTO> listadoImagenes = new ArrayList<>();
        for (AnxFunListadoDevolucionIvaTO item : listado) {
            String pk = item.getActSecuencia();
            if (pk != null) {
                String[] array = pk.split("\\|");
                InvComprasPK invComprasPK = new InvComprasPK();
                invComprasPK.setCompEmpresa(empresa);
                invComprasPK.setCompPeriodo(array[0].trim());
                invComprasPK.setCompMotivo(array[1].trim());
                invComprasPK.setCompNumero(array[2].trim());
                List<InvAdjuntosComprasWebTO> listaImagenes = comprasService.convertirStringUTF8(invComprasPK, "FACTURA");
                if (listaImagenes != null && listaImagenes.size() > 0) {
                    for (InvAdjuntosComprasWebTO imagen : listaImagenes) {
                        imagen.setAdjClaveArchivo(getBase64EncodedImage(imagen.getAdjUrlArchivo()));
                    }
                    listadoImagenes.addAll(listaImagenes);
                }
            }

        }
        return listadoImagenes; //To change body of generated methods, choose Tools | Templates.
    }

    public String getBase64EncodedImage(String imageURL) throws IOException {
        java.net.URL url = new java.net.URL(imageURL);
        InputStream is = url.openStream();
        byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
        return Base64.encodeBase64String(bytes);
    }

    @Override
    public Map<String, Object> listarSecuencialesRetenciones(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<InvListaSecuencialesTO> listadoRetencionesHuerfanas = new ArrayList<>();
        List<InvListaSecuencialesTO> listadoInvListaSecuencialesTO = listarInvListaSecuencialesRetenciones(empresa, fechaHasta);
        List<String> listadoRetencionesHuerfanasString = getAnexoFunListadoRetencionesHuerfanas(empresa, fechaDesde, fechaHasta);

        for (String entry : listadoRetencionesHuerfanasString) {
            InvListaSecuencialesTO item = new InvListaSecuencialesTO();
            item.setSecuencial(entry);
            listadoRetencionesHuerfanas.add(item);
        }

        campos.put("listadoInvListaSecuencialesTO", listadoInvListaSecuencialesTO);
        campos.put("listadoRetencionesHuerfanas", listadoRetencionesHuerfanas);
        return campos;
    }

}
