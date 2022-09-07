/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.TipoComprobanteDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnexoNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.GuiaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.GuiaRemisionDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.TransportistaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronica;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInp;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportista;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisLoginTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoComprobanteEnum;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class GuiaRemisionServiceImpl implements GuiaRemisionService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private GuiaRemisionDao guiaRemisionDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private TransportistaDao transportistaDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private TipoComprobanteDao tipoComprobanteDao;
    @Autowired
    private ClienteService clienteService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private AnexoNumeracionService numeracionService;
    @Autowired
    private InvTransportistaService invTransportistaService;
    @Autowired
    private GuiaInpService guiaInpService;
    @Autowired
    private GuiaElectronicaService guiaElectronicaService;

    @Override
    public List<InvConsultaGuiaRemisionTO> getListaInvGuiaRemisionTO(String empresa, String tipoDocumento, String fechaInicio, String fechaFinal, String nRegistros) throws Exception {
        return guiaRemisionDao.getListaInvGuiaRemisionTO(empresa, tipoDocumento, fechaInicio, fechaFinal, nRegistros);
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosGuiaRemision(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo periodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        String tipoDocumento = TipoComprobanteEnum.GUIA_DE_REMISION.getCode();
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        List<InvTransportistaTO> listaInvTransportista = invTransportistaService.getListaInvTransportistaTO(empresa, null, false);
//        List<InvGuiaRemisionInp> listadoINP = guiaInpService.getInvGuiaRemisionInp(empresa, true);
        List<AnxTipoComprobanteComboTO> listadoTipoComprobante = tipoComprobanteDao.getListaAnxTipoComprobanteComboTO(null);
        String numeroDocumento = asignarNumeroDocumento(empresa, tipoDocumento, sisInfoTO);
        boolean numeroValido = numeroDocumento != null && !numeroDocumento.equals("") ? validarNumero(empresa, tipoDocumento, numeroDocumento, sisInfoTO) : false;
        campos.put("numeroDocumento", numeroDocumento);
        campos.put("numeroValido", numeroValido);
        campos.put("fechaActual", fechaActual);
        campos.put("listadoTipoComprobante", listadoTipoComprobante);
        campos.put("listaInvTransportista", listaInvTransportista);
//        campos.put("listadoINP", listadoINP);
        campos.put("periodoAbierto", (periodo != null ? (periodo.getPerCerrado() ? false : true) : false));
        return campos;
    }

    public String asignarNumeroDocumento(String empresa, String tipoDocumento, SisInfoTO sisInfoTO) throws Exception {
        String numeroDocumento = "";
        SisLoginTO sisLoginTO = usuarioService.getPermisoInventarioTO(empresa, sisInfoTO.getUsuario());
        if (sisLoginTO != null && sisLoginTO.getPerSecuencialPermitidoGuias() != null) {
            String ultimoSecuencial = ventaService.getUltimaNumeracionComprobante(empresa, tipoDocumento, sisLoginTO.getPerSecuencialPermitidoGuias());
            if (ultimoSecuencial == null || "".equals(ultimoSecuencial)) {
                numeroDocumento = sisLoginTO.getPerSecuencialPermitidoGuias() + "-000000001";
            }
            if (ultimoSecuencial != null && !"".equals(ultimoSecuencial)) {
                String separado = ultimoSecuencial.substring(0, ultimoSecuencial.lastIndexOf("-") + 1);
                int numero = Integer.parseInt(ultimoSecuencial.substring(ultimoSecuencial.lastIndexOf("-") + 2));
                int numeroParaDocumento = numero + 1;
                int digitosNumero = ("" + numeroParaDocumento).length();
                for (int i = 0; i < (9 - digitosNumero); i++) {
                    numeroDocumento = numeroDocumento + "0";
                }
                numeroDocumento = separado + numeroDocumento + numeroParaDocumento;
            }
            if (!isNumeroDocumentoValido(empresa, tipoDocumento, numeroDocumento, sisInfoTO)) {
                return "";
            }
        }
        return numeroDocumento;
    }

    public boolean validarNumero(String empresa, String tipoDocumento, String numeroDocumento, SisInfoTO sisInfoTO) throws Exception {
        Boolean isValido = isNumeroDocumentoValido(empresa, tipoDocumento, numeroDocumento, sisInfoTO);
        return isValido;
    }

    public boolean isNumeroDocumentoValido(String empresa, String tipoDocumento, String numeroDocumento, SisInfoTO sisInfoTO) throws Exception {
        List<AnxNumeracionTablaTO> listaAnxNumeracionTablaTO = numeracionService.getListaAnexoNumeracionTO(empresa);
        String secuencialPermitido = numeroDocumento.substring(0, 7);
        String ultimosDigitos = numeroDocumento.substring(8);
        for (AnxNumeracionTablaTO item : listaAnxNumeracionTablaTO) {
            if (item.getNumeroComprobante().equalsIgnoreCase(tipoDocumento)
                    && item.getEstablecimientoPtoEmisionDesde().equalsIgnoreCase(secuencialPermitido)
                    && item.getEstablecimientoPtoEmisionHasta().equalsIgnoreCase(secuencialPermitido)) {
                if (Integer.parseInt(ultimosDigitos) >= Integer.parseInt(item.getNumeroDesde().substring(8))
                        && Integer.parseInt(ultimosDigitos) <= Integer.parseInt(item.getNumeroHasta().substring(8))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosGuiaRemisionConsulta(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));

        List<AnxTipoComprobanteComboTO> listadoTipoComprobante = tipoComprobanteDao.getListaAnxTipoComprobanteComboTO(null);
        InvGuiaRemision guiaRemision = guiaRemisionDao.buscarInvGuiaRemision(empresa, periodo, numero);
        List<InvGuiaRemisionDetalle> listaDetalle = obtenerGuiaRemisionDetalle(empresa, periodo, numero);
        List<InvGuiaRemisionDetalleTO> listaDetalleTO = new ArrayList<>();
        List<InvTransportistaTO> listaInvTransportista = invTransportistaService.getListaInvTransportistaTO(empresa, null, false);
        List<InvGuiaRemisionInp> listadoINP = guiaInpService.getInvGuiaRemisionInp(empresa, guiaRemision.getInvCliente().getInvClientePK().getCliCodigo(), false);
        List<InvClientesDirecciones> listaDireccionesCliente = clienteService.getListInvClientesDirecciones(empresa, guiaRemision.getInvCliente().getInvClientePK().getCliCodigo());

        AnxGuiaRemisionElectronica guiaElectronica = guiaElectronicaService.buscarAnxGuiaRemisionElectronica(empresa, periodo, numero);
        boolean estaAutorizada = guiaElectronica != null ? guiaElectronica.geteEstado().equals("AUTORIZADO") : false;

        for (InvGuiaRemisionDetalle detalle : listaDetalle) {
            InvGuiaRemisionDetalleTO detalleTO = ConversionesInventario.convertirInvGuiaRemisionDetalle_InvGuiaRemisionDetalleTO(detalle);
            listaDetalleTO.add(detalleTO);
        }

        campos.put("listadoTipoComprobante", listadoTipoComprobante);
        campos.put("listaDireccionesCliente", listaDireccionesCliente);
        campos.put("invGuiaRemision", guiaRemision);
        campos.put("listaDetalleTO", listaDetalleTO);
        campos.put("listadoINP", listadoINP);
        campos.put("listaInvTransportista", listaInvTransportista);
        campos.put("estaAutorizada", estaAutorizada);
        
        return campos;
    }

    @Override
    public MensajeTO insertarTransaccionInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetallesTO, SisInfoTO sisInfoTO) throws Exception {
        boolean periodoCerrado = false;
        String retorno = "";
        boolean comprobar = false;
        MensajeTO mensajeTO = new MensajeTO();
        String empresa = invGuiaRemision.getInvGuiaRemisionPK().getGuiaEmpresa();
        // ojo -

        if (!UtilsValidacion.isFechaSuperior(UtilsValidacion.fecha(invGuiaRemision.getGuiaFechaEmision(), "yyyy-MM-dd"), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
            listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (invGuiaRemision.getGuiaFechaEmision().getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && invGuiaRemision.getGuiaFechaEmision().getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    comprobar = true;
                    invGuiaRemision.getInvGuiaRemisionPK().setGuiaPeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (comprobar) {
                if (!periodoCerrado) {
                    susSuceso = "INSERT";
                    susTabla = "inventario.inv_guia_remision";
                    invGuiaRemision.setUsrFechaInserta(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd"));
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    ///// BUSCANDO TRANSPORTISTA
                    InvTransportista transportista = invGuiaRemision.getInvTransportista();
                    InvTransportista invTransportista = transportistaDao.buscarInvTransportista(empresa, transportista.getInvTransportistaPK().getTransCodigo());

                    if (invTransportista != null) {
                        ////// CREANDO GUIA
                        //InvGuiaRemision invGuiaRemision = ConversionesInventario.convertirInvGuiaRemisionTO_InvGuiaRemision(invGuiaRemisionTO);
                        invGuiaRemision.setInvTransportista(invTransportista);
                        ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                        List<InvGuiaRemisionDetalle> listInvGuiaRemisionDetalle = new ArrayList<InvGuiaRemisionDetalle>();
                        InvGuiaRemisionDetalle invGuiaRemisionDetalle = null;
                        int estadoDetalle = 0;
                        for (InvGuiaRemisionDetalleTO invGuiaRemisionDetalleTO : listaInvGuiaRemisionDetallesTO) {
                            invGuiaRemisionDetalle = new InvGuiaRemisionDetalle();
                            invGuiaRemisionDetalle = ConversionesInventario.convertirInvGuiaRemisionDetalleTO_InvGuiaRemisionDetalle(invGuiaRemisionDetalleTO);
                            ///// BUSCANDO EL PRODUCTO EN DETALLE
                            InvProducto invProducto = productoDao.buscarInvProducto(invGuiaRemisionDetalleTO.getProEmpresa(), invGuiaRemisionDetalleTO.getProCodigoPrincipal());
                            if (invProducto != null) {
                                invGuiaRemisionDetalle.setInvProducto(invProducto);
                                listInvGuiaRemisionDetalle.add(invGuiaRemisionDetalle);
                            } else {
                                estadoDetalle = 1;
                            }
                            if (estadoDetalle == 1) {
                                break;
                            } else {
                                invProducto = null;
                            }
                        }

                        if (estadoDetalle == 0) {

                            String codigoFactura = guiaRemisionDao.getConteoNumeroGuiaRemision(empresa, transportista.getInvTransportistaPK().getTransCodigo(), invGuiaRemision.getGuiaDocumentoNumero(), null);
                            if (codigoFactura == null || codigoFactura.trim().isEmpty()) {
                                comprobar = guiaRemisionDao.insertarTransaccionInvGuiaRemision(invGuiaRemision, listInvGuiaRemisionDetalle, sisSuceso);
                                if (comprobar) {
                                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(empresa, invGuiaRemision.getInvGuiaRemisionPK().getGuiaPeriodo());
                                    retorno = "T<html>La guia de remisión se guardo correctamente con la siguiente información:<br><br>"
                                            + "Periodo: <font size = 5>"
                                            + sisPeriodo.getPerDetalle()
                                            + "</font>.<br> Número: <font size = 5>"
                                            + invGuiaRemision.getInvGuiaRemisionPK().getGuiaNumero()
                                            + "</font>.</html>"
                                            + invGuiaRemision.getInvGuiaRemisionPK().getGuiaNumero() + ","
                                            + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                    Map<String, Object> campos = new HashMap<>();
                                    campos.put("invGuiaRemision", invGuiaRemision);
                                    mensajeTO.setMap(campos);
                                } else {
                                    retorno = "F<html>Hubo un error al guardar la guia de remisión...\nIntente de nuevo o contacte con el administrador</html>";
                                }
                            } else {
                                retorno = "F<html>El número de documento que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                            }

                        } else {
                            retorno = "F<html>Uno de los productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro producto o contacte con el administrador</html>";
                        }
                    } else {
                        retorno = "F<html>El transportista que escogió ya no está disponible...\nIntente de nuevo, escoja otro transportista o contacte con el administrador</html>";
                    }
                } else {
                    retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                }
            } else {
                retorno = "F <html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public MensajeTO modificarTransaccionInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetallesTO, List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetallesTOEliminar, SisInfoTO sisInfoTO) throws Exception {
        boolean periodoCerrado = false;
        String retorno = "";
        boolean comprobar = false;
        MensajeTO mensajeTO = new MensajeTO();
        String empresa = invGuiaRemision.getInvGuiaRemisionPK().getGuiaEmpresa();
        // ojo -

        if (!UtilsValidacion.isFechaSuperior(UtilsValidacion.fecha(invGuiaRemision.getGuiaFechaEmision(), "yyyy-MM-dd"), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
            listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (invGuiaRemision.getGuiaFechaEmision().getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && invGuiaRemision.getGuiaFechaEmision().getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    comprobar = true;
                    invGuiaRemision.getInvGuiaRemisionPK().setGuiaPeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (comprobar) {
                if (!periodoCerrado) {
                    susSuceso = "UPDATE";
                    susTabla = "inventario.inv_guia_remision";
                    //invGuiaRemision.setUsrFechaInserta(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd"));
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    ///// BUSCANDO TRANSPORTISTA
                    InvTransportista transportista = invGuiaRemision.getInvTransportista();
                    InvTransportista invTransportista = transportistaDao.buscarInvTransportista(empresa, transportista.getInvTransportistaPK().getTransCodigo());

                    if (invTransportista != null) {
                        ////// CREANDO GUIA
                        //InvGuiaRemision invGuiaRemision = ConversionesInventario.convertirInvGuiaRemisionTO_InvGuiaRemision(invGuiaRemisionTO);
                        invGuiaRemision.setInvTransportista(invTransportista);
                        ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                        List<InvGuiaRemisionDetalle> listInvGuiaRemisionDetalle = new ArrayList<InvGuiaRemisionDetalle>();
                        List<InvGuiaRemisionDetalle> listInvGuiaRemisionDetalleEliminar = new ArrayList<InvGuiaRemisionDetalle>();
                        InvGuiaRemisionDetalle invGuiaRemisionDetalle = null;
                        int estadoDetalle = 0;
                        for (InvGuiaRemisionDetalleTO invGuiaRemisionDetalleTO : listaInvGuiaRemisionDetallesTO) {
                            invGuiaRemisionDetalle = new InvGuiaRemisionDetalle();
                            invGuiaRemisionDetalle = ConversionesInventario.convertirInvGuiaRemisionDetalleTO_InvGuiaRemisionDetalle(invGuiaRemisionDetalleTO);
                            ///// BUSCANDO EL PRODUCTO EN DETALLE
                            InvProducto invProducto = productoDao.buscarInvProducto(invGuiaRemisionDetalleTO.getProEmpresa(), invGuiaRemisionDetalleTO.getProCodigoPrincipal());
                            if (invProducto != null) {
                                invGuiaRemisionDetalle.setInvProducto(invProducto);
                                invGuiaRemisionDetalle.setInvGuiaRemision(invGuiaRemision);
                                listInvGuiaRemisionDetalle.add(invGuiaRemisionDetalle);
                            } else {
                                estadoDetalle = 1;
                            }
                            if (estadoDetalle == 1) {
                                break;
                            } else {
                                invProducto = null;
                            }
                        }

                        if (estadoDetalle == 0) {
                            int estadoDetalleEliminar = 0;
                            if (listaInvGuiaRemisionDetallesTOEliminar != null && listaInvGuiaRemisionDetallesTOEliminar.size() > 0) {
                                InvGuiaRemisionDetalle invGuiaRemisionDetalleEliminar = null;
                                for (InvGuiaRemisionDetalleTO invGuiaRemisionDetalleTO : listaInvGuiaRemisionDetallesTOEliminar) {
                                    invGuiaRemisionDetalleEliminar = new InvGuiaRemisionDetalle();
                                    invGuiaRemisionDetalleEliminar = ConversionesInventario.convertirInvGuiaRemisionDetalleTO_InvGuiaRemisionDetalle(invGuiaRemisionDetalleTO);
                                    ///// BUSCANDO EL PRODUCTO EN DETALLE
                                    InvProducto invProducto = productoDao.buscarInvProducto(invGuiaRemisionDetalleTO.getProEmpresa(), invGuiaRemisionDetalleTO.getProCodigoPrincipal());
                                    if (invProducto != null) {
                                        invGuiaRemisionDetalleEliminar.setInvProducto(invProducto);
                                        invGuiaRemisionDetalleEliminar.setInvGuiaRemision(invGuiaRemision);
                                        listInvGuiaRemisionDetalleEliminar.add(invGuiaRemisionDetalleEliminar);
                                    } else {
                                        estadoDetalleEliminar = 1;
                                    }
                                    if (estadoDetalleEliminar == 1) {
                                        break;
                                    } else {
                                        invProducto = null;
                                    }
                                }
                            }
                            if (estadoDetalleEliminar == 0) {
                                String codigoFactura = guiaRemisionDao.getConteoNumeroGuiaRemision(empresa, transportista.getInvTransportistaPK().getTransCodigo(), invGuiaRemision.getGuiaDocumentoNumero(), invGuiaRemision.getInvGuiaRemisionPK());
                                if (codigoFactura == null || codigoFactura.trim().isEmpty()) {
                                    comprobar = guiaRemisionDao.modificarTransaccionInvGuiaRemision(invGuiaRemision, listInvGuiaRemisionDetalle, listInvGuiaRemisionDetalleEliminar, sisSuceso);
                                    if (comprobar) {
                                        SisPeriodo sisPeriodo = periodoService.buscarPeriodo(empresa, invGuiaRemision.getInvGuiaRemisionPK().getGuiaPeriodo());
                                        retorno = "T<html>La guia de remisión se modificado correctamente con la siguiente información:<br><br>"
                                                + "Periodo: <font size = 5>"
                                                + sisPeriodo.getPerDetalle()
                                                + "</font>.<br> Número: <font size = 5>"
                                                + invGuiaRemision.getInvGuiaRemisionPK().getGuiaNumero()
                                                + "</font>.</html>"
                                                + invGuiaRemision.getInvGuiaRemisionPK().getGuiaNumero() + ","
                                                + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                        Map<String, Object> campos = new HashMap<>();
                                        campos.put("invGuiaRemision", invGuiaRemision);
                                        mensajeTO.setMap(campos);
                                    } else {
                                        retorno = "F<html>Hubo un error al guardar la guia de remisión...\nIntente de nuevo o contacte con el administrador</html>";
                                    }
                                } else {
                                    retorno = "F<html>El número de documento que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>";
                                }
                            } else {
                                retorno = "F<html>Uno de los Productos que escogió ya no esta¡ disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                            }
                        } else {
                            retorno = "F<html>Uno de los productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro producto o contacte con el administrador</html>";
                        }
                    } else {
                        retorno = "F<html>El transportista que escogió ya no está disponible...\nIntente de nuevo, escoja otro transportista o contacte con el administrador</html>";
                    }
                } else {
                    retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                }
            } else {
                retorno = "F <html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public String validarSecuenciaPermitida(String empresa, String numero, SisInfoTO sisInfoTO) throws Exception {
        SisLoginTO sisLoginTO = usuarioService.getPermisoInventarioTO(sisInfoTO.getEmpresa(), sisInfoTO.getUsuario());
        String mensaje = "T";
        if (sisLoginTO.getPerSecuencialPermitidoGuias() != null) {
            if (!numero.substring(0, 7).equalsIgnoreCase(sisLoginTO.getPerSecuencialPermitidoGuias())) {
                mensaje = "F" + "-El número de la guia de remisión no cumple con el secuencial permitido.\n";
            }
        }
        return mensaje;
    }

    @Override
    public List<InvGuiaRemisionDetalle> obtenerGuiaRemisionDetalle(String empresa, String periodo, String numero) throws Exception {
        return guiaRemisionDao.obtenerGuiaRemisionDetallePorNumero(empresa, periodo, numero);
    }

    @Override
    public List<InvGuiaRemisionDetalleTO> obtenerGuiaRemisionDetalleTO(String empresa, String periodo, String numero) throws Exception {
        return guiaRemisionDao.obtenerGuiaRemisionDetalleTO(empresa, periodo, numero);
    }

    @Override
    public String anularGuiaRemision(String empresa, String periodo, String numero) throws Exception {
        String mensaje = "";
        guiaRemisionDao.anularRestaurarSql(new InvGuiaRemisionPK(empresa, periodo, numero), true);
        mensaje = "TLa guía de remisión: " + numero + ", se ha anulado correctamente.";
        return mensaje;
    }

    @Override
    public String desmayorizarGuiaRemision(String empresa, String periodo, String numero) throws Exception {
        String mensaje = "";
        guiaRemisionDao.desmayorizarRestaurarSql(new InvGuiaRemisionPK(empresa, periodo, numero), true);
        mensaje = "TLa guía de remisión: " + numero + ", se ha desmayorizado correctamente.";
        return mensaje;
    }

    @Override
    public InvGuiaRemision buscarInvGuiaRemision(String empresa, String periodo, String compNumero) throws Exception {
        return guiaRemisionDao.buscarInvGuiaRemision(empresa, periodo, compNumero);
    }

    @Override
    public List<AnxListaGuiaRemisionPendientesTO> getListaGuiaRemisionPendientes(String empresa) throws Exception {
        return guiaRemisionDao.getListaGuiaRemisionPendientes(empresa);
    }

    @Override
    public String eliminarGuiaRemision(String empresa, String periodo, String numero, SisInfoTO sisInfoTO) throws Exception {
        /// PREPARANDO OBJETO SISSUCESO
        susClave = empresa + "|" + periodo + "|" + numero;
        susDetalle = "Se eliminó la guía de remisión: " + susClave;
        susSuceso = "DELETE";
        susTabla = "inventario.inv_compras";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return guiaRemisionDao.eliminarGuiaRemision(empresa, periodo, numero, sisSuceso);

    }

    @Override
    public InvGuiaRemisionTO buscarInvGuiaRemisionTO(String empresa, String numeroDocumento) throws Exception {
        return guiaRemisionDao.buscarInvGuiaRemision(empresa, numeroDocumento);
    }

    @Override
    public Boolean actualizarClaveExterna(InvGuiaRemisionPK pk, String clave, SisInfoTO sisInfoTO) throws Exception {
        // / PREPARANDO OBJETO SISSUCESO
        susClave = pk.getGuiaPeriodo() + " "
                + pk.getGuiaNumero();
        susDetalle = "Se actualizó la clave externa de la guia de remisión con código: " + pk.getGuiaPeriodo() + " "
                + pk.getGuiaNumero();
        susTabla = "inventario.inv_guia_remision";
        susSuceso = "UPDATE";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return guiaRemisionDao.actualizarClaveExterna(pk, clave, sisSuceso);
    }

}
