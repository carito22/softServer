package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.service.PorcentajeIvaService;
import ec.com.todocompu.ShrimpSoftServer.caja.dao.CajaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.BodegaDao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProformasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProformasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProformasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaProformaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleProformasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProformasServiceImpl implements ProformasService {

    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private ProformasDao proformasDao;
    @Autowired
    private ProformasDetalleDao proformasDetalleDao;
    @Autowired
    private ProformasMotivoDao proformasMotivoDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ProformasDetalleService proformasDetalleService;
    @Autowired
    private ProformasMotivoService proformasMotivoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private VentasMotivoService ventasMotivoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private CajaDao cajaDao;
    @Autowired
    private PorcentajeIvaService porcentajeIvaService;
    @Autowired
    private BodegaDao bodegaDao;
    @Autowired
    private ProductoService productoService;
    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public InvProformaCabeceraTO getInvProformaCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroProforma) throws Exception {
        return proformasDao.getInvProformaCabeceraTO(empresa, codigoPeriodo, motivo, numeroProforma);
    }

    @Override
    public List<InvListaConsultaProformaTO> getListaInvConsultaProforma(String empresa, String periodo, String motivo,
            String busqueda) throws Exception {
        return proformasDao.getListaInvConsultaProforma(empresa, periodo, motivo, busqueda);
    }

    @Override
    public List<InvListaConsultaProformaTO> getListaInvConsultaProformas(String empresa, String periodo, String motivo, String busqueda, String nRegistros) throws Exception {
        return proformasDao.getListaInvConsultaProforma(empresa, periodo, motivo, busqueda, nRegistros);
    }

    @Override
    public MensajeTO insertarInvProformasTO(InvProformasTO invProformasTO, List<InvProformasDetalleTO> listaInvProformasDetalleTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        InvProformas invProformas = new InvProformas();
        boolean periodoCerrado = false;
        if (!UtilsValidacion.isFechaSuperior(invProformasTO.getProfFecha(), "yyyy-MM-dd")) {
            ///// BUSCANDO CLIENTE
            InvCliente invCliente = clienteDao.buscarInvCliente(invProformasTO.getProfEmpresa(), invProformasTO.getCliCodigo());

            if (invCliente != null) {
                comprobar = false;
                List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
                listaSisPeriodoTO = periodoService.getListaPeriodoTO(invProformasTO.getProfEmpresa());

                for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                    if (UtilsValidacion.fecha(invProformasTO.getProfFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                            && UtilsValidacion.fecha(invProformasTO.getProfFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                        comprobar = true;
                        invProformasTO.setProfPeriodo(sisListaPeriodoTO.getPerCodigo());
                        periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                    }
                }

                if (comprobar) {
                    if (!periodoCerrado) {
                        if (proformasMotivoDao.comprobarInvProformasMotivo(invProformasTO.getProfEmpresa(),
                                invProformasTO.getProfMotivo())) {
                            /// PREPARANDO OBJETO SISSUCESO (susClave y
                            /// susDetalle se llenan en DAOTransaccion)
                            susSuceso = "INSERT";
                            susTabla = "inventario.inv_proformas";
                            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

                            invProformasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                            ////// CREANDO NUMERACION DE PROFORMA
                            InvProformasNumeracion invProformasNumeracion = new InvProformasNumeracion(new InvProformasNumeracionPK(invProformasTO.getProfEmpresa(), invProformasTO.getProfPeriodo(), invProformasTO.getProfMotivo()));
                            ////// CREANDO PROFOR+MA
                            invProformas = ConversionesInventario.convertirInvProformasTO_InvProformas(invProformasTO);
                            invProformas.setInvCliente(invCliente);
                            ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                            List<InvProformasDetalle> listInvProformasDetalle = new ArrayList<InvProformasDetalle>();
                            InvProformasDetalle invProformasDetalle = null;

                            int estadoDetalle = 0;
                            for (InvProformasDetalleTO invProformasDetalleTO : listaInvProformasDetalleTO) {
                                invProformasDetalle = new InvProformasDetalle();
                                invProformasDetalleTO.setProfPeriodo(invProformasTO.getProfPeriodo());
                                invProformasDetalle = ConversionesInventario.convertirInvProformasDetalleTO_InvProformasDetalle(invProformasDetalleTO);
                                ///// BUSCANDO EL PRODUCTO EN DETALLE
                                InvProducto invProducto = productoDao.buscarInvProducto(invProformasDetalleTO.getProfEmpresa(), invProformasDetalleTO.getProCodigoPrincipal());
                                if (invProducto != null) {
                                    invProformasDetalle.setInvProducto(invProducto);
                                    if (!invProducto.getProInactivo()) {
                                        listInvProformasDetalle.add(invProformasDetalle);
                                    } else {
                                        estadoDetalle = 2;
                                    }
                                } else {
                                    estadoDetalle = 1;
                                }

                                if (estadoDetalle == 1 || estadoDetalle == 2) {
                                    break;
                                } else {
                                    invProducto = null;
                                }
                            }
                            if (estadoDetalle == 0) {
                                //////////// COMPROBAR SI NO EXISTE NUMERO
                                //////////// DE FACTURA Y SI ES EMPRESA
                                //////////// COMERCIAL YA NO SE PREGUNTA ESO
                                comprobar = proformasDao.insertarTransaccionInvProformas(invProformas, listInvProformasDetalle, sisSuceso, invProformasNumeracion);
                                if (comprobar) {
                                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                            invProformasTO.getProfEmpresa(),
                                            invProformas.getInvProformasPK().getProfPeriodo());
                                    InvProformasMotivo invProformaMotivo = proformasMotivoDao.getInvProformasMotivo(
                                            invProformasTO.getProfEmpresa(),
                                            invProformas.getInvProformasPK().getProfMotivo());
                                    retorno = "T<html>La proforma, se ha guardado correctamente con la siguiente información:<br><br>"
                                            + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                            + "</font>.<br> Motivo: <font size = 5>"
                                            + invProformaMotivo.getPmDetalle()
                                            + "</font>.<br> Número: <font size = 5>"
                                            + invProformas.getInvProformasPK().getProfNumero() + "</font>.</html>"
                                            + invProformas.getInvProformasPK().getProfNumero() + ","
                                            + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                } else {
                                    retorno = "F<html>Hubo un error al guardar la Proforma...\nIntente de nuevo o contacte con el administrador</html>";
                                }
                            } else if (estadoDetalle == 1) {
                                retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                            } else {
                                retorno = "F<html>Uno de los Productos que escogió esta como inactivo...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                            }
                        } else {
                            retorno = "F<html>No se encuentra el motivo...</html>";
                        }
                    } else {
                        retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                    }
                } else {
                    retorno = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
                }

            } else {
                retorno = "F<html>El Cliente que escogió ya no está disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }

        Map<String, Object> campos = new HashMap<>();
        campos.put("pk", invProformas.getInvProformasPK());
        mensajeTO.setMap(campos);
        mensajeTO.setMensaje(retorno);
        return mensajeTO;

    }

    @Override
    public MensajeTO modificarInvProformasTO(InvProformasTO invProformasTO,
            List<InvProformasDetalleTO> listaInvProformasDetalleTO,
            List<InvProformasDetalleTO> listaInvProformasEliminarDetalleTO, boolean quitarAnulado, SisInfoTO sisInfoTO)
            throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        boolean bandAux = false;
        bandAux = listaInvProformasDetalleTO == null ? true : false;
        String retorno = "";
        boolean periodoCerrado = false;
        if (!UtilsValidacion.isFechaSuperior(invProformasTO.getProfFecha(), "yyyy-MM-dd")) {
            ///// BUSCANDO CLIENTE
            InvCliente invCliente = clienteDao.buscarInvCliente(invProformasTO.getProfEmpresa(), invProformasTO.getCliCodigo());
            if (invCliente != null) {
                String detalleError = "";
                if (quitarAnulado && listaInvProformasDetalleTO == null) {
                    List<InvListaDetalleProformasTO> invListaDetalleTO = proformasDetalleDao.getListaInvProformasDetalleTO(invProformasTO.getProfEmpresa(),
                            invProformasTO.getProfPeriodo(), invProformasTO.getProfMotivo(),
                            invProformasTO.getProfNumero());
                    if (invListaDetalleTO != null) {
                        listaInvProformasDetalleTO = new ArrayList<InvProformasDetalleTO>();
                        for (int i = 0; i < invListaDetalleTO.size(); i++) {
                            InvProformasDetalleTO invProformasDetalleTO = new InvProformasDetalleTO();
                            invProformasDetalleTO.setDetSecuencia(invListaDetalleTO.get(i).getSecuencial());
                            invProformasDetalleTO.setDetOrden(i + 1);
                            invProformasDetalleTO.setDetCantidad(invListaDetalleTO.get(i).getCantidadProducto());
                            invProformasDetalleTO.setDetPrecio(invListaDetalleTO.get(i).getPrecioProducto());
                            invProformasDetalleTO.setDetPorcentajeDescuento(invListaDetalleTO.get(i).getPorcentajeDescuento());
                            invProformasDetalleTO.setProEmpresa(invProformasTO.getProfEmpresa());
                            invProformasDetalleTO.setProCodigoPrincipal(invListaDetalleTO.get(i).getCodigoProducto());
                            invProformasDetalleTO.setProNombre(invListaDetalleTO.get(i).getNombreProducto());
                            invProformasDetalleTO.setProfEmpresa(invProformasTO.getProfEmpresa());
                            invProformasDetalleTO.setProfPeriodo(invProformasTO.getProfPeriodo());
                            invProformasDetalleTO.setProfMotivo(invProformasTO.getProfMotivo());
                            invProformasDetalleTO.setProfNumero(invProformasTO.getProfNumero());
                            listaInvProformasDetalleTO.add(invProformasDetalleTO);
                        }
                    } else {
                        detalleError = "Hubo en error al recuperar el detalle de la PROFORMA.\nContacte con el administrador...";
                    }
                }
                if (detalleError.trim().isEmpty()) {
                    comprobar = false;
                    List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
                    listaSisPeriodoTO = periodoService.getListaPeriodoTO(invProformasTO.getProfEmpresa());

                    for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                        if (UtilsValidacion.fecha(invProformasTO.getProfFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                                && UtilsValidacion.fecha(invProformasTO.getProfFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                            comprobar = true;
                            invProformasTO.setProfPeriodo(sisListaPeriodoTO.getPerCodigo());
                            periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                        }
                    }

                    if (comprobar) {
                        if (!periodoCerrado) {
                            if (proformasMotivoDao.comprobarInvProformasMotivo(invProformasTO.getProfEmpresa(), invProformasTO.getProfMotivo())) {
                                InvProformas invProformasCreadas = proformasDao.buscarInvProformas(
                                        invProformasTO.getProfEmpresa().trim(),
                                        invProformasTO.getProfPeriodo().trim(),
                                        invProformasTO.getProfMotivo().trim(),
                                        invProformasTO.getProfNumero().trim());
                                String estado = "";
                                if (invProformasTO.getProfAnulado()) {
                                    estado = "anuló";
                                } else {
                                    estado = "modificó";
                                }
                                if (invProformasCreadas != null) {
                                    if (quitarAnulado) {
                                        estado = "restauró";
                                        //// CREANDO SUCESO
                                        susClave = invProformasTO.getProfPeriodo() + " "
                                                + invProformasTO.getProfMotivo() + " "
                                                + invProformasTO.getProfNumero();
                                        susDetalle = "Se " + estado + " la proformas en el periodo "
                                                + invProformasTO.getProfPeriodo() + ", del motivo "
                                                + invProformasTO.getProfMotivo() + ", de la numeración "
                                                + invProformasTO.getProfNumero();
                                        susSuceso = "UPDATE";
                                        susTabla = "inventario.inv_proformas";
                                        invProformasTO.setUsrCodigo(invProformasCreadas.getUsrCodigo());
                                        invProformasTO.setUsrFechaInserta(UtilsValidacion.fecha(
                                                invProformasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                    } else {
                                        susDetalle = "";
                                        if (estado.trim().equals("anuló")) {
                                            susDetalle = "Proforma número " + invProformasTO.getProfNumero()
                                                    + "se anuló";
                                        } else {
                                            susDetalle = "Se " + estado + " la proforma en el periodo "
                                                    + invProformasTO.getProfPeriodo() + ", del motivo "
                                                    + invProformasTO.getProfMotivo() + ", de la numeración "
                                                    + invProformasTO.getProfNumero();
                                        }
                                        //// CREANDO SUCESO
                                        susClave = invProformasTO.getProfPeriodo() + " "
                                                + invProformasTO.getProfMotivo() + " "
                                                + invProformasTO.getProfNumero();
                                        susSuceso = "UPDATE";
                                        susTabla = "inventario.inv_proformas";
                                        invProformasTO.setUsrCodigo(invProformasCreadas.getUsrCodigo());
                                        invProformasTO.setUsrFechaInserta(UtilsValidacion.fecha(
                                                invProformasCreadas.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                    }
                                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                            susDetalle, sisInfoTO);
                                    ////// CREANDO NUMERACION DE PROFORMA

                                    InvProformas invProformas = ConversionesInventario.convertirInvProformasTO_InvProformas(invProformasTO);
                                    invProformas.setInvCliente(clienteDao.buscarInvCliente(invProformasTO.getCliEmpresa(), invProformasTO.getCliCodigo()));
                                    ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                                    List<InvProformasDetalle> listInvProformasDetalle = new ArrayList<InvProformasDetalle>();
                                    InvProformasDetalle invProformasDetalle = null;
                                    int estadoDetalle = 0;
                                    if (listaInvProformasDetalleTO != null) {
                                        for (InvProformasDetalleTO invProformasDetalleTO : listaInvProformasDetalleTO) {
                                            invProformasDetalle = new InvProformasDetalle();
                                            invProformasDetalleTO.setProfPeriodo(invProformasTO.getProfPeriodo());
                                            invProformasDetalle = ConversionesInventario.convertirInvProformasDetalleTO_InvProformasDetalle(invProformasDetalleTO);
                                            ///// BUSCANDO EL PRODUCTO EN
                                            ///// DETALLE
                                            InvProducto invProducto = productoDao.buscarInvProducto(
                                                    invProformasDetalleTO.getProfEmpresa(),
                                                    invProformasDetalleTO.getProCodigoPrincipal());

                                            if (invProducto != null) {
                                                invProformasDetalle.setInvProducto(invProducto);
                                                if (!invProducto.getProInactivo()) {
                                                    listInvProformasDetalle.add(invProformasDetalle);
                                                } else {
                                                    estadoDetalle = 2;
                                                }
                                            } else {
                                                estadoDetalle = 1;
                                            }
                                            if (estadoDetalle == 1 || estadoDetalle == 2) {
                                                break;
                                            } else {
                                                invProducto = null;
                                            }
                                        }
                                    }
                                    if (estadoDetalle == 0) {
                                        ////// CONVIRTIENDO A ENTIDAD EL
                                        ////// DETALLE A ELIMINAR
                                        List<InvProformasDetalle> listInvProformasDetalleEliminar = new ArrayList<InvProformasDetalle>();
                                        InvProformasDetalle invProformasDetalleEliminar = null;
                                        int estadoDetalleEliminar = 0;
                                        if (listaInvProformasEliminarDetalleTO != null) {
                                            for (InvProformasDetalleTO invProformasDetalleTO : listaInvProformasEliminarDetalleTO) {
                                                invProformasDetalleEliminar = new InvProformasDetalle();
                                                invProformasDetalleTO.setProfPeriodo(invProformasTO.getProfPeriodo());
                                                invProformasDetalleEliminar = ConversionesInventario.convertirInvProformasDetalleTO_InvProformasDetalle(invProformasDetalleTO);
                                                ///// BUSCANDO EL PRODUCTO
                                                ///// EN DETALLE
                                                InvProducto invProducto = null;
                                                if (invProformasDetalleTO.getProCodigoPrincipal() == null || invProformasDetalleTO.getProCodigoPrincipal().equals("")) {
                                                    listInvProformasDetalleEliminar.add(invProformasDetalleEliminar);
                                                } else {
                                                    invProducto = productoDao.buscarInvProducto(
                                                            invProformasDetalleTO.getProfEmpresa(),
                                                            invProformasDetalleTO.getProCodigoPrincipal());
                                                    if (invProducto != null) {
                                                        invProformasDetalleEliminar.setInvProducto(invProducto);
                                                        if (!invProducto.getProInactivo()) {
                                                            listInvProformasDetalleEliminar.add(invProformasDetalleEliminar);
                                                        } else {
                                                            estadoDetalleEliminar = 2;
                                                        }
                                                    } else {
                                                        estadoDetalleEliminar = 1;
                                                    }
                                                }
                                                if (estadoDetalleEliminar == 1 || estadoDetalleEliminar == 2) {
                                                    break;
                                                } else {
                                                    invProducto = null;
                                                }
                                            }
                                        }
                                        if (estadoDetalleEliminar == 0) {
                                            if (invProformas.getProfAnulado()) {
                                                comprobar = proformasDao.modificarInvProformas(invProformas, listInvProformasDetalle, listInvProformasDetalleEliminar, sisSuceso);
                                            }
                                            if (comprobar) {
                                                SisPeriodo sisPeriodo = periodoService.buscarPeriodo(invProformasTO.getProfEmpresa(), invProformas.getInvProformasPK().getProfPeriodo());
                                                InvProformasMotivo invProformaMotivo = proformasMotivoDao.getInvProformasMotivo(invProformasTO.getProfEmpresa(), invProformas.getInvProformasPK().getProfMotivo());
                                                retorno = "T<html>La proforma,   "
                                                        + (invProformasTO.getProfAnulado() ? "se ha anulado correctamente" : "se ha modificado correctamente")
                                                        + " con la siguiente información:<br><br>"
                                                        + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                                        + "</font>.<br> Motivo: <font size = 5>"
                                                        + invProformaMotivo.getPmDetalle()
                                                        + "</font>.<br> Número: <font size = 5>"
                                                        + invProformas.getInvProformasPK().getProfNumero()
                                                        + "</font>.</html>"
                                                        + invProformas.getInvProformasPK().getProfNumero() + ","
                                                        + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                                mensajeTO.setFechaCreacion(
                                                        invProformas.getUsrFechaInserta().toString());
                                            } else {
                                                retorno = "FHubo un error al modificar la Proforma...\nIntente de nuevo o contacte con el administrador";
                                            }
                                            if (invProformas.getProfPendiente()) {
                                                if (!bandAux) {
                                                    proformasDao.modificarInvProformas(invProformas, listInvProformasDetalle, listInvProformasDetalleEliminar, sisSuceso);
                                                } else {
                                                    proformasDao.modificarInvProformas(invProformas, new ArrayList<InvProformasDetalle>(), new ArrayList<InvProformasDetalle>(), sisSuceso);
                                                }
                                            } else if (!invProformas.getProfAnulado()) {
                                                proformasDao.modificarInvProformas(invProformas, listInvProformasDetalle, listInvProformasDetalleEliminar, sisSuceso);
                                            }
                                        } else if (estadoDetalleEliminar == 1) {
                                            retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                        } else {
                                            retorno = "F<html>Uno de los Productos no esta disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                        }
                                    } else if (estadoDetalle == 1) {
                                        retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                    } else {
                                        retorno = "F<html>Uno de los Productos no esta disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                    }
                                } else {
                                    retorno = "F<html>La Proforma que quiere modificar ya no se encuentra disponible</html>";
                                }
                            } else {
                                retorno = "F<html>No se encuentra el motivo...</html>";
                            }
                        } else {
                            retorno = "F<html>No se puede modificar debido a que el período se encuentra cerrado...</html>";
                        }
                    } else {
                        retorno = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
                    }
                } else {
                    retorno = "F<html>" + detalleError + "</html>";
                }
            } else {
                retorno = "F<html>El Cliente que escogió ya no está disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;

    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return proformasDao.getEstadoCCCVT(empresa, periodo, motivo, numero);
    }

    @Override
    public String desmayorizarProforma(String empresa, String periodo, String motivo, String numero) throws Exception {
        String mensaje = "";
        InvProformaCabeceraTO invProforma = getInvProformaCabeceraTO(empresa, periodo, motivo, numero);

        if ((mensaje = periodoService.validarPeriodo(UtilsDate.DeStringADate(invProforma.getProfFecha()), empresa)) == null) {
            proformasDao.actualizarPendienteSql(new InvProformasPK(empresa, periodo, motivo, numero), true);
            mensaje = "TLa proforma: " + numero + ", se ha desmayorizado correctamente.";
        }
        return mensaje;
    }

    @Override
    public String desmayorizarLoteProforma(List<InvListaConsultaProformaTO> listado, SisInfoTO sisInfoTO) throws Exception {

        int contador = 0;
        String respuesta = "";
        StringBuilder mensaje = new StringBuilder();
        for (InvListaConsultaProformaTO proforma : listado) {
            if ((proforma.getProfAnulado() != null && proforma.getProfAnulado().equalsIgnoreCase("ANULADO")) || (proforma.getProfPendiente() != null && proforma.getProfPendiente().equalsIgnoreCase("PENDIENTE"))) {
                mensaje.append("\n").append("No se puede desmayorizar la proforma ").append(proforma.getProfNumero()).append(" ya ha sido ANULADA o está PENDIENTE");
                contador++;
            } else {
                InvProformasPK pk = new InvProformasPK(sisInfoTO.getEmpresa(), proforma.getProfPeriodo(), proforma.getProfMotivo(), proforma.getProfNumero());
                String cadena = desmayorizarProforma(sisInfoTO.getEmpresa(), proforma.getProfPeriodo(), proforma.getProfMotivo(), proforma.getProfNumero()) + '|';
                mensaje.append("\n").append(cadena.substring(1, cadena.length())).append(".");
                if (cadena.charAt(0) != 'T') {
                    contador++;
                }
            }
        }
        if (contador == 0) {
            respuesta = 'T' + mensaje.toString();
        } else {
            respuesta = mensaje.toString();
        }

        return respuesta;

    }

    @Override
    public String anularProforma(String empresa, String periodo, String motivo, String numero) throws Exception {
        String mensaje = "";
        proformasDao.anularRestaurarSql(new InvProformasPK(empresa, periodo, motivo, numero), true);
        mensaje = "TLa proforma: " + numero + ", se ha anulado correctamente.";
        return mensaje;
    }

    @Override
    public String restaurarProforma(String empresa, String periodo, String motivo, String numero) throws Exception {
        String mensaje = "";
        InvProformaCabeceraTO invProforma = getInvProformaCabeceraTO(empresa, periodo, motivo, numero);

        if ((mensaje = periodoService.validarPeriodo(UtilsDate.DeStringADate(invProforma.getProfFecha()), empresa)) == null) {
            proformasDao.anularRestaurarSql(new InvProformasPK(empresa, periodo, motivo, numero), false);
            mensaje = "TLa proforma: " + numero + ", se ha restaurado correctamente.";

        }
        return mensaje;
    }

    @Override
    public Map<String, Object> nuevaProforma(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usuarioCodigo"));
        /*Lista motivos*/
        List<InvProformaMotivoTO> listaInvProformaMotivoTO = proformasMotivoService.getListaInvProformaMotivoTO(empresa, false, null);
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        BigDecimal porcentaje = porcentajeIvaService.getValorAnxPorcentajeIvaTO(UtilsDate.DeDateAString(fechaActual));
        InvProductoEtiquetas etiquetas = productoService.traerEtiquetas(empresa);
        CajCajaTO caja = null;
        if (usuarioCodigo != null) {
            caja = cajaDao.getCajCajaTO(empresa, usuarioCodigo);
        }
        List<InvListaBodegasTO> listadoBodegas = bodegaDao.buscarBodegasTO(empresa, false, null);
        campos.put("etiquetas", etiquetas);
        campos.put("listadoBodegas", listadoBodegas);
        campos.put("porcentaje", porcentaje);
        campos.put("fechaActual", fechaActual);
        campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("caja", caja);
        campos.put("listaInvProformaMotivoTO", listaInvProformaMotivoTO);
        return campos;
    }

    @Override
    public Map<String, Object> consultarProforma(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usuarioCodigo"));

        InvProformaCabeceraTO invCompraCabeceraTO = getInvProformaCabeceraTO(empresa, periodo, motivo, numero);
        InvProformasTO proforma = convertirInvProformaCabeceraTO_InvProformasTO(invCompraCabeceraTO, motivo, numero, periodo);

        CajCajaTO caja = null;
        if (usuarioCodigo != null) {
            caja = cajaDao.getCajCajaTO(empresa, usuarioCodigo);
        }
        /*DETALLE*/
        List<InvListaDetalleProformasTO> listaDetalle = proformasDetalleService.getListaInvProformasDetalleTO(empresa, periodo, motivo, numero);
        /*CLIENTE*/
        InvClienteTO invClienteTO = clienteService.obtenerClienteTO(empresa, proforma.getCliCodigo());
        /*Lista motivos*/
        List<InvProformaMotivoTO> listaInvProformaMotivoTO = proformasMotivoService.getListaInvProformaMotivoTO(empresa, false, null);
        /*PERIODOS*/
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa);
        InvProductoEtiquetas etiquetas = productoService.traerEtiquetas(empresa);

        BigDecimal parcial = BigDecimal.ZERO;
        BigDecimal recargoBaseImponible = BigDecimal.ZERO;
        BigDecimal recargoBaseCero = BigDecimal.ZERO;
        for (int i = 0; i < listaDetalle.size(); i++) {
            InvListaDetalleProformasTO detalle = listaDetalle.get(i);
            parcial = detalle.getParcialProducto();
            BigDecimal porcentajeRecargo = detalle.getPorcentajeRecargo();
            BigDecimal parcialAuxRecargo = parcial.multiply(porcentajeRecargo);
            BigDecimal recargoValor = parcialAuxRecargo.divide(new java.math.BigDecimal("100.00"), 15, java.math.RoundingMode.HALF_UP);
            if (detalle.getGravaIva()) {
                recargoBaseImponible = recargoBaseImponible.add(recargoValor);
            } else {
                recargoBaseCero = recargoBaseCero.add(recargoValor);
            }
        }

        proforma.setProfRecargoBase0(recargoBaseCero);
        proforma.setProfRecargoBaseImponible(recargoBaseImponible);
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(UtilsDate.DeStringADate(proforma.getProfFecha()), empresa);
        BigDecimal porcentaje = porcentajeIvaService.getValorAnxPorcentajeIvaTO(proforma.getProfFecha());
        List<InvListaBodegasTO> listadoBodegas = bodegaDao.buscarBodegasTO(empresa, false, null);
        campos.put("etiquetas", etiquetas);
        campos.put("listadoBodegas", listadoBodegas);
        campos.put("porcentaje", porcentaje);
        campos.put("fechaActual", fechaActual);
        campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("caja", caja);
        campos.put("invProformasTO", proforma);
        campos.put("invClienteTO", invClienteTO);
        campos.put("listaDetalle", listaDetalle);
        campos.put("listaPeriodos", listaPeriodos);
        campos.put("listaInvProformaMotivoTO", listaInvProformaMotivoTO);
        return campos;
    }

    @Override
    public Map<String, Object> consultarProformaSoloActivos(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));

        InvProformaCabeceraTO invCompraCabeceraTO = getInvProformaCabeceraTO(empresa, periodo, motivo, numero);
        InvProformasTO proforma = null;
        InvClienteTO invClienteTO = null;
        List<InvListaDetalleProformasTO> listaDetalle = new ArrayList<InvListaDetalleProformasTO>();
        if (invCompraCabeceraTO != null && !invCompraCabeceraTO.getProfAnulado() && !invCompraCabeceraTO.getProfPendiente()) {
            proforma = convertirInvProformaCabeceraTO_InvProformasTO(invCompraCabeceraTO, motivo, numero, periodo);
            /*CLIENTE*/
            invClienteTO = clienteService.obtenerClienteTO(empresa, proforma.getCliCodigo());
            /*DETALLE*/
            listaDetalle = proformasDetalleService.getListaInvProformasDetalleTO(empresa, periodo, motivo, numero);
            BigDecimal parcial = BigDecimal.ZERO;
            BigDecimal recargoBaseImponible = BigDecimal.ZERO;
            BigDecimal recargoBaseCero = BigDecimal.ZERO;
            for (int i = 0; i < listaDetalle.size(); i++) {
                InvListaDetalleProformasTO detalle = listaDetalle.get(i);
                parcial = detalle.getParcialProducto();
                BigDecimal porcentajeRecargo = detalle.getPorcentajeRecargo();
                BigDecimal parcialAuxRecargo = parcial.multiply(porcentajeRecargo);
                BigDecimal recargoValor = parcialAuxRecargo.divide(new java.math.BigDecimal("100.00"), 15, java.math.RoundingMode.HALF_UP);
                if (detalle.getGravaIva()) {
                    recargoBaseImponible = recargoBaseImponible.add(recargoValor);
                } else {
                    recargoBaseCero = recargoBaseCero.add(recargoValor);
                }
            }

            proforma.setProfRecargoBase0(recargoBaseCero);
            proforma.setProfRecargoBaseImponible(recargoBaseImponible);
        }
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);

        campos.put("fechaActual", fechaActual);
        campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("invProformasTO", proforma);
        campos.put("invClienteTO", invClienteTO);
        campos.put("listaDetalle", listaDetalle);
        return campos;
    }

    public InvProformasTO convertirInvProformaCabeceraTO_InvProformasTO(InvProformaCabeceraTO invCompraCabeceraTO, String motivo, String numero, String periodo) {
        InvProformasTO proforma = new InvProformasTO();
        proforma.setCliCodigo(invCompraCabeceraTO.getCliCodigo());
        proforma.setCliEmpresa(invCompraCabeceraTO.getCliEmpresa());
        proforma.setProfAnulado(invCompraCabeceraTO.getProfAnulado());
        proforma.setProfBase0(invCompraCabeceraTO.getProfBase0());
        proforma.setProfBaseImponible(invCompraCabeceraTO.getProfBaseimponible());
        proforma.setProfDescuentoBase0(invCompraCabeceraTO.getProfDescuentoBase0() == null ? BigDecimal.ZERO : invCompraCabeceraTO.getProfDescuentoBase0());
        proforma.setProfDescuentoBaseImponible(invCompraCabeceraTO.getProfDescuentoBaseImponible());
        proforma.setProfEmpresa(invCompraCabeceraTO.getCliEmpresa());
        proforma.setProfFecha(invCompraCabeceraTO.getProfFecha());
        proforma.setProfIvaVigente(invCompraCabeceraTO.getProfIvaVigente());
        proforma.setProfMontoIva(invCompraCabeceraTO.getProfMontoiva());
        proforma.setProfMotivo(motivo);
        proforma.setProfNumero(numero);
        proforma.setProfObservaciones(invCompraCabeceraTO.getProfObservaciones());
        proforma.setProfPendiente(invCompraCabeceraTO.getProfPendiente());
        proforma.setProfPeriodo(periodo);
        proforma.setProfRecargoBase0(BigDecimal.ZERO);
        proforma.setProfRecargoBaseImponible(BigDecimal.ZERO);
        proforma.setProfSubtotalBase0(invCompraCabeceraTO.getProfSubtotalBase0());
        proforma.setProfSubtotalBaseImponible(invCompraCabeceraTO.getProfSubtotalBaseImponible());
        proforma.setProfTotal(invCompraCabeceraTO.getProfTotal());
        proforma.setUsrCodigo(invCompraCabeceraTO.getUsuarioInserto());
        proforma.setUsrEmpresa(invCompraCabeceraTO.getUserEmpresa());
        proforma.setUsrFechaInserta(invCompraCabeceraTO.getFechaUsuarioInserto());
        return proforma;
    }

    @Override
    public List<InvProformaClienteTO> listarInvProformaClienteTO(String empresa, String cliCodigo) throws Exception {
        return proformasDao.listarInvProformaClienteTO(empresa, cliCodigo);
    }

}
