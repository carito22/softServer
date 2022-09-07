package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.TipoComprobanteDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxEstablecimientoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaConsolidadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaEstablecimientoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPuntoEmisionComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxRetencionesVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private VentasDao ventasDao;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private VentasService ventasService;
    @Autowired
    private VentasMotivoDao ventasMotivoDao;
    @Autowired
    private TipoComprobanteDao tipoComprobanteDao;
    @Autowired
    private AnxRetencionesVentaService anxRetencionesVentaService;

    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    private boolean comprobar = false;
    private String mensaje;

    @Override
    public List<AnxEstablecimientoComboTO> getEstablecimientos(String empresa) throws Exception {
        return ventaDao.getEstablecimientos(empresa);
    }

    @Override
    public List<AnxPuntoEmisionComboTO> getPuntosEmision(String empresa, String establecimiento) throws Exception {
        return ventaDao.getPuntosEmision(empresa, establecimiento);
    }

    @Override
    public AnxNumeracionLineaTO getNumeroAutorizacion(String empresa, String numeroRetencion, String numeroComprobante,
            String fechaVencimiento) throws Exception {
        return ventaDao.getNumeroAutorizacion(empresa, numeroRetencion, numeroComprobante, fechaVencimiento);
    }

    @Override
    public AnxVentaTO getAnexoVentaTO(String empresa, String periodo, String motivo, String numeroVenta)
            throws Exception {
        return ventaDao.getAnexoVentaTO(empresa, periodo, motivo, numeroVenta);
    }

    @Override
    public List<AnxListaRetencionVentaTO> getAnxListaRetencionVentaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        return ventaDao.getAnxListaRetencionVentaTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxListaConsolidadoRetencionesVentasTO> getAnxListaConsolidadoRetencionesVentasTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception {
        return ventaDao.getAnxListaConsolidadoRetencionesVentasTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxListaEstablecimientoRetencionesVentasTO> getAnxListaEstablecimientoRetencionesVentasTO(
            String empresa, String fechaDesde, String fechaHasta) throws Exception {
        return ventaDao.getAnxListaEstablecimientoRetencionesVentasTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxListadoRetencionesVentasTO> getAnxListadoRetencionesVentasTO(String empresa, String tipoDocumento,
            String establecimiento, String puntoEmision, String fechaDesde, String fechaHasta) throws Exception {
        return ventaDao.getAnxListadoRetencionesVentasTO(empresa, tipoDocumento, establecimiento, puntoEmision,
                fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxFunListadoDevolucionIvaVentasTO> getAnxFunListadoDevolucionIvaVentasTO(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        return ventaDao.getAnxFunListadoDevolucionIvaVentasTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxTalonResumenVentaTO> getAnexoTalonResumenVentaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        return ventaDao.getAnexoTalonResumenVentaTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public String eliminarAnxVentas(AnxVentaTO anxVentaTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AnxVenta anxVentaConsulta = ventaDao.obtenerPorId(AnxVenta.class, new AnxVentaPK(anxVentaTO.getVenEmpresa(),
                anxVentaTO.getVenPeriodo(), anxVentaTO.getVenMotivo(), anxVentaTO.getVenNumero()));
        if (anxVentaConsulta != null) {
            // / PREPARANDO OBJETO SISSUCESO
            susClave = anxVentaTO.getVenEmpresa() + " | " + anxVentaTO.getVenPeriodo() + " | "
                    + anxVentaTO.getVenMotivo() + " | " + anxVentaTO.getVenNumero();
            susDetalle = "Se eliminó una RETENCIÓN EN VENTAS con código " + anxVentaTO.getVenEmpresa() + " | "
                    + anxVentaTO.getVenPeriodo() + " | " + anxVentaTO.getVenMotivo() + " | "
                    + anxVentaTO.getVenNumero();
            susSuceso = "DELETE";
            susTabla = "anexos.anx_venta";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            // / PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            anxVentaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

            AnxVenta anxVenta = ConversionesAnexos.convertirAnxVentaTO_AnxVenta(anxVentaTO);

            if (ventaDao.eliminarAnxVentas(anxVenta, sisSuceso)) {
                retorno = "TLa RETENCIÓN EN VENTA fué eliminada correctamente...";
            } else {
                retorno = "FHubo un error al eliminar la RETENCIÓN EN VENTA ...\nIntente de nuevo o contacte con el administrador.";
            }
        } else {
            retorno = "FLa RETENCIÓN EN VENTA no se encuentra aún en la Base de Datos...\nInténtelo de nuevo o contacte con el administrador.";
        }
        return retorno;
    }

    @Override
    public String accionAnxVenta(AnxVentaTO anxVentaTO, String numeroFactura, String periodoFactura, String cliCodigo,
            char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        mensaje = "T";
        boolean periodoCerrado = false;
        String numeroRetencion = "";
        List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(anxVentaTO.getVenEmpresa());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (periodoFactura != null && UtilsValidacion.fecha(periodoFactura, "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(periodoFactura, "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                anxVentaTO.setVenPeriodo(sisListaPeriodoTO.getPerCodigo());
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (comprobar) {

            if (!periodoCerrado) {
                InvCliente invCliente = clienteDao.buscarInvCliente(anxVentaTO.getVenEmpresa(), cliCodigo);
                // /// BUSCANDO CLIENTE
                if (invCliente != null) {
                    InvVentas invVentasCreadas = ventasDao.buscarInvVentas(anxVentaTO.getVenEmpresa(),
                            anxVentaTO.getVenPeriodo(), anxVentaTO.getVenMotivo(), anxVentaTO.getVenNumero());
                    boolean noExiste = false;

                    String codigoRetencion = "";
                    anxVentaTO.setVenRetencionNumero(
                            anxVentaTO.getVenRetencionNumero() == null ? "" : anxVentaTO.getVenRetencionNumero());
                    if (!anxVentaTO.getVenRetencionNumero().isEmpty()) {
                        codigoRetencion = ventaDao.getConteoNumeroRetencionVenta(anxVentaTO.getVenEmpresa(),
                                anxVentaTO.getVenRetencionNumero(), invCliente.getInvClientePK().getCliCodigo());
                    } else {
                        codigoRetencion = "";
                    }
                    if (codigoRetencion.trim().isEmpty() || codigoRetencion.trim()
                            .equals(invVentasCreadas.getInvVentasPK().getVtaEmpresa().trim()
                                    .concat(invVentasCreadas.getInvVentasPK().getVtaPeriodo().trim()
                                            .concat(invVentasCreadas.getInvVentasPK().getVtaMotivo().trim().concat(
                                                    invVentasCreadas.getInvVentasPK().getVtaNumero().trim()))))) {
                        noExiste = true;
                    }
                    if (noExiste) {
                        // /// CREANDO Suceso
                        susClave = anxVentaTO.getVenPeriodo() + " " + anxVentaTO.getVenMotivo() + " "
                                + anxVentaTO.getVenNumero();
                        numeroRetencion = anxVentaTO.getVenRetencionNumero();
                        if (accion == 'I') {
                            // Se insertó Retencio de la Venta 2014-01 |
                            // 0000003
                            susDetalle = "Se ingresó la retención # " + anxVentaTO.getVenRetencionNumero()
                                    + " al comprobante de venta # " + numeroFactura;
                            susSuceso = "INSERT";
                            // 2014-01 002-002 0000003
                        }
                        if (accion == 'M') {
                            susDetalle = "Se modificó la retención # " + anxVentaTO.getVenRetencionNumero()
                                    + " del compobante de venta # " + numeroFactura;
                            susSuceso = "UPDATE";
                        }
                        if (accion == 'E') {
                            susDetalle = "Se eliminó la retención # " + anxVentaTO.getVenRetencionNumero()
                                    + " del comprobante de venta# " + numeroFactura;
                            susSuceso = "DELETE";
                        }
                        susTabla = "anexo.anx_venta";
                        // /// CREANDO anxVenta;
                        anxVentaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                        AnxVenta anxVenta = ConversionesAnexos.convertirAnxVentaTO_AnxVenta(anxVentaTO);
                        if (accion == 'E') {
                            // //// BUSCANDO existencia la Retencion
                            if (ventaDao.comprobarAnxVentas(anxVentaTO.getUsrEmpresa(), anxVentaTO.getVenPeriodo(),
                                    anxVentaTO.getVenMotivo(), anxVentaTO.getVenNumero())) {
                                if (ventaDao.comprobarEliminarAnxVentas(anxVentaTO.getUsrEmpresa(),
                                        anxVentaTO.getVenPeriodo(), anxVentaTO.getVenMotivo(),
                                        anxVentaTO.getVenNumero())) {

                                    anxVenta.setUsrFechaInserta(ventaDao.obtenerPorId(AnxVenta.class,
                                            new AnxVentaPK(anxVentaTO.getUsrEmpresa(), anxVentaTO.getVenPeriodo(),
                                                    anxVentaTO.getVenMotivo(), anxVentaTO.getVenNumero()))
                                            .getUsrFechaInserta());
                                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                            susDetalle, sisInfoTO);

                                    comprobar = ventaDao.accionAnxVenta(anxVenta, sisSuceso, accion);
                                } else {
                                    mensaje = "FNo se puede eliminar la Retencion, ya esta contabilizada...";
                                }
                            } else {
                                mensaje = "FNo se encuentra La retencion...";
                            }
                        }
                        if (accion == 'M') {
                            // //// BUSCANDO existencia Retencion
                            if (ventaDao.comprobarAnxVentas(anxVentaTO.getUsrEmpresa(), anxVentaTO.getVenPeriodo(),
                                    anxVentaTO.getVenMotivo(), anxVentaTO.getVenNumero())) {

                                anxVenta.setUsrFechaInserta(ventaDao.obtenerPorId(AnxVenta.class,
                                        new AnxVentaPK(anxVentaTO.getUsrEmpresa(), anxVentaTO.getVenPeriodo(),
                                                anxVentaTO.getVenMotivo(), anxVentaTO.getVenNumero()))
                                        .getUsrFechaInserta());
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                        susDetalle, sisInfoTO);
                                comprobar = ventaDao.accionAnxVenta(anxVenta, sisSuceso, accion);
                            } else {
                                mensaje = "FNo se encuentra La retencion...";
                            }
                        }

                        if (mensaje.substring(0, 1).equals("T")) {
                            if (accion == 'I') {
                                // //// BUSCANDO existencia Retencion
                                if (!ventaDao.comprobarAnxVentas(anxVentaTO.getUsrEmpresa(), anxVentaTO.getVenPeriodo(),
                                        anxVentaTO.getVenMotivo(), anxVentaTO.getVenNumero())) {

                                    anxVenta.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                            susDetalle, sisInfoTO);
                                    comprobar = ventaDao.accionAnxVenta(anxVenta, sisSuceso, accion);
                                } else {
                                    mensaje = "FYa existe la Retencion...";
                                }
                            }
                            if (comprobar) {
                                if (accion == 'E') {
                                    mensaje = "TSe eliminó correctamente la Retención \nNº " + numeroRetencion;
                                }
                                if (accion == 'M') {
                                    mensaje = "TSe modificó correctamente la Retención \nNº " + numeroRetencion;
                                }
                                if (accion == 'I') {
                                    mensaje = "TSe ingresó correctamente  la Retención \nNº " + numeroRetencion;
                                }
                            }
                        }

                    } else {
                        mensaje = "FEl Número de Retención que ingresó ya existe...\nIntente de nuevo o contacte con el administrador";
                    }

                } else {
                    mensaje = "FEl Cliente que escogió ya no está disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador";
                }
            } else {
                mensaje = "FEl periodo que corresponde a la fecha de La Factura se encuentra cerrado...";
            }
        } else {
            mensaje = "FNo se encuentra ningún periodo para la fecha que La Factura...";
        }
        return mensaje;
    }

    @Override
    public List<AnxListaVentaElectronicaTO> getListaAnxVentaElectronicaTO(String empresa,
            String fechaDesde, String fechaHasta, String tipoDocumento) throws Exception {
        return ventaDao.getListaAnxVentaElectronicaTO(empresa, fechaDesde, fechaHasta, tipoDocumento);
    }

    @Override
    public String getUltimaNumeracionComprobante(String empresa, String comprobante, String secuencial)
            throws Exception {
        return ventaDao.getUltimaNumeracionComprobante(empresa, comprobante, secuencial);
    }

    @Override
    public Map<String, Object> obtenerAnexoVentaTO(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String numeroDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("numeroDocumento"));
        //buscar retenciones ventas importar
        String numeroDocumentoRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("numeroDocumentoRetencion"));
        String identificacionCliente = UtilsJSON.jsonToObjeto(String.class, map.get("identificacionCliente"));
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        AnxRetencionesVenta anxRetencionesVenta = null;
        if (identificacionCliente != null && numeroDocumentoRetencion != null) {
            anxRetencionesVenta = anxRetencionesVentaService.obtenerAnxRetencionesVenta(empresa, numeroDocumentoRetencion, identificacionCliente);
            campos.put("anxRetencionesVenta", anxRetencionesVenta);
            if (claveAcceso != null && ventaDao.getAnexoVentaTO(empresa, claveAcceso) != null) {
                campos.put("existeRetencion", true);
            }
        }
        InvVentaRetencionesTO invVentaRetencionesTO = (tipoDocumento != null && !tipoDocumento.equals(""))
                ? ventasService.getInvVentaRetencionesTO(empresa, tipoDocumento, numeroDocumento)
                : ventasService.getInvVentaRetencionesTO(empresa, numeroDocumento);

        InvVentaCabeceraTO invVentaCabeceraTO = new InvVentaCabeceraTO();
        AnxVentaTO anxVentaTO = new AnxVentaTO();

        if (invVentaRetencionesTO != null) {
            invVentaCabeceraTO = ventasDao.getInvVentaCabeceraTO(empresa, invVentaRetencionesTO.getVenPeriodo(), invVentaRetencionesTO.getVenMotivo(), invVentaRetencionesTO.getVenNumero());
            anxVentaTO = ventaDao.getAnexoVentaTO(empresa, invVentaRetencionesTO.getVenPeriodo(), invVentaRetencionesTO.getVenMotivo(), invVentaRetencionesTO.getVenNumero());
            if (anxVentaTO == null) {
                anxVentaTO = new AnxVentaTO();
                anxVentaTO.setVenBase0(invVentaRetencionesTO.getVenBase0() == null ? BigDecimal.ZERO : invVentaRetencionesTO.getVenBase0());
                anxVentaTO.setVenBaseImponible(invVentaRetencionesTO.getVenBaseImponible() == null ? BigDecimal.ZERO : invVentaRetencionesTO.getVenBaseImponible());
                anxVentaTO.setVenBaseNoObjetoIva(BigDecimal.ZERO);
                anxVentaTO.setVenMontoIva(invVentaRetencionesTO.getVenMontoIva() == null ? BigDecimal.ZERO : invVentaRetencionesTO.getVenMontoIva());
                anxVentaTO.setVenValorRetenidoRenta(invVentaRetencionesTO.getVenValorRetenidoRenta() == null ? BigDecimal.ZERO : invVentaRetencionesTO.getVenValorRetenidoRenta());
                anxVentaTO.setVenValorRetenidoIva(BigDecimal.ZERO);
                anxVentaTO.setVenRetencionFechaEmision(invVentaRetencionesTO.getVtaFecha());
            } else {
                campos.put("anxRetencionesVenta", null);
            }
            anxVentaTO.setVenPeriodo(invVentaRetencionesTO.getVenMotivo());
            anxVentaTO.setVenMotivo(invVentaRetencionesTO.getVenMotivo());
            anxVentaTO.setVenNumero(invVentaRetencionesTO.getVenNumero());
        }

        List<InvVentaMotivoTO> listaMotivos = ventasMotivoDao.getListaInvVentasMotivoTO(empresa, false, null);

        campos.put("invVentaRetencionesTO", invVentaRetencionesTO);
        campos.put("invVentaCabeceraTO", invVentaCabeceraTO);
        campos.put("anxVentaTO", anxVentaTO);
        campos.put("listaMotivos", listaMotivos);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosConsultaRetencionesVentasListadoSimple(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String establecimiento = UtilsJSON.jsonToObjeto(String.class, map.get("establecimiento"));

        List<AnxTipoComprobanteComboTO> listaComprobante = tipoComprobanteDao.getListaAnxTipoComprobanteComboTO(null);
        List<AnxEstablecimientoComboTO> listaEstablecimiento = ventaDao.getEstablecimientos(empresa);
        List<AnxPuntoEmisionComboTO> listaPuntoEmision = ventaDao.getPuntosEmision(empresa, establecimiento);

        campos.put("listaComprobante", listaComprobante);
        campos.put("listaEstablecimiento", listaEstablecimiento);
        campos.put("listaPuntoEmision", listaPuntoEmision);
        return campos;
    }
}
