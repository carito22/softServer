package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.BodegaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoSaldosDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.TransferenciasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.TransferenciasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.TransferenciasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaDetalleSeriesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferencias;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransferenciasServiceImpl implements TransferenciasService {

    @Autowired
    private BodegaDao bodegaDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private ProductoSaldosDao productoSaldosDao;
    @Autowired
    private TransferenciasDao transferenciasDao;
    @Autowired
    private TransferenciasMotivoDao transferenciasMotivoDao;
    @Autowired
    private TransferenciasDetalleDao transferenciasDetalleDao;
    @Autowired
    private TransferenciasMotivoService transferenciasMotivoService;
    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private PeriodoService periodoService;

    private boolean comprobar = false;
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public InvTransferenciasTO getInvTransferenciasCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroTransferencia) throws Exception {
        return transferenciasDao.getInvTransferenciasCabeceraTO(empresa, codigoPeriodo, motivo, numeroTransferencia);
    }

    @Override
    public List<InvListaDetalleTransferenciaTO> getInvTransferenciasDetalleTO(String empresa, String codigoPeriodo, String motivo,
            String numeroTransferencia) throws Exception {
        return transferenciasDetalleDao.getListaInvTransferenciasDetalleTO(empresa, codigoPeriodo, motivo, numeroTransferencia);
    }

    @Override
    public List<InvListaConsultaTransferenciaTO> getFunListadoTransferencias(String empresa, String fechaDesde,
            String fechaHasta, String status) throws Exception {
        return transferenciasDao.getFunListadoTransferencias(empresa, fechaDesde, fechaHasta, status);
    }

    @Override
    public List<InvListaConsultaTransferenciaTO> getListaInvConsultaTransferencias(String empresa, String periodo,
            String motivo, String busqueda, String nRegistros) throws Exception {
        return transferenciasDao.getListaInvConsultaTransferencia(empresa, periodo, motivo, busqueda, nRegistros);
    }

    @Override
    public MensajeTO insertarInvTransferenciaTO(InvTransferenciasTO invTransferenciasTO,
            List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvProductoSaldos invProductoSaldosOrigen = null;
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>();
        boolean pendiente = invTransferenciasTO.getTransPendiente();
        invTransferenciasTO.setTransPendiente(true);
        boolean periodoCerrado = false;
        if (!UtilsValidacion.isFechaSuperior(invTransferenciasTO.getTransFecha(), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
            listaSisPeriodoTO = periodoService.getListaPeriodoTO(invTransferenciasTO.getTransEmpresa());
            comprobar = false;
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(invTransferenciasTO.getTransFecha(), "yyyy-MM-dd")
                        .getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(invTransferenciasTO.getTransFecha(), "yyyy-MM-dd")
                                .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                                .getTime()) {
                    comprobar = true;
                    invTransferenciasTO.setTransPeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (comprobar) {
                if (!periodoCerrado) {
                    if (transferenciasMotivoDao.comprobarInvTransferenciaMotivo(
                            invTransferenciasTO.getTransEmpresa(), invTransferenciasTO.getTransMotivo())) {

                        susSuceso = "INSERT";
                        susTabla = "inventario.inv_transferencias";
                        invTransferenciasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                        ////// CREANDO TRANSFERENCIAS
                        InvTransferencias invTransferencias = ConversionesInventario
                                .convertirInvTransferenciasTO_InvTransferencias(invTransferenciasTO);
                        ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                        List<InvTransferenciasDetalle> listInvTransferenciasDetalle = new ArrayList<InvTransferenciasDetalle>();
                        InvTransferenciasDetalle invTransferenciasDetalle = null;

                        int estadoDetalle = 0;
                        for (InvTransferenciasDetalleTO invTransferenciasDetalleTO : listaInvTransferenciasDetalleTO) {
                            invTransferenciasDetalle = new InvTransferenciasDetalle();
                            invTransferenciasDetalle = ConversionesInventario
                                    .convertirInvTransferenciasDetalleTO_InvTransferenciasDetalle(
                                            invTransferenciasDetalleTO);
                            ///// BUSCANDO EL PRODUCTO EN DETALLE
                            InvProducto invProducto = productoDao.buscarInvProducto(
                                    invTransferenciasDetalleTO.getTransEmpresa(),
                                    invTransferenciasDetalleTO.getProCodigoPrincipal());
                            if (invProducto != null) {
                                invTransferenciasDetalle.setInvProducto(invProducto);
                                ////// BUSCANDO LA BODEGA EN EL DETALLE
                                ////// ORIGEN
                                InvBodega invBodegaOrigen = bodegaDao.buscarInvBodega(
                                        invTransferenciasDetalleTO.getTransEmpresa(),
                                        invTransferenciasDetalleTO.getBodOrigenCodigo());
                                ////// BUSCANDO LA BODEGA EN EL DETALLE
                                ////// DESTINO
                                InvBodega invBodegaDestino = bodegaDao.buscarInvBodega(
                                        invTransferenciasDetalleTO.getTransEmpresa(),
                                        invTransferenciasDetalleTO.getBodDestinoCodigo());

                                if (invBodegaOrigen != null && invBodegaDestino != null) {
                                    invTransferenciasDetalle.setInvBodega(invBodegaOrigen);
                                    invTransferenciasDetalle.setInvBodega1(invBodegaDestino);
                                    listInvTransferenciasDetalle.add(invTransferenciasDetalle);
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
                            if (!invTransferencias.getTransPendiente()) {
                                List<InvTransferenciasDetalle> listadoDetalleTransferencias = agrupraProductosBodegaTransferencia(
                                        listInvTransferenciasDetalle);
                                for (int i = 0; i < listadoDetalleTransferencias.size(); i++) {
                                    if (!listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("COSTO O GASTO")
                                            && !listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("SERVICIOS")
                                            && !listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("ACTIVO FIJO")) {
                                        invProductoSaldosOrigen = new InvProductoSaldos();
                                        //// CREO LA LLAVE DE ORIGEN
                                        //// BUSQUEDA DE LA ENTIDAD ORIGEN
                                        InvProductoSaldos invProductoSaldosConsultaOrigen = productoSaldosDao
                                                .getInvProductoSaldo(invTransferencias.getInvTransferenciasPK().getTransEmpresa(),
                                                        listadoDetalleTransferencias.get(i).getInvBodega().getInvBodegaPK().getBodCodigo(),
                                                        listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal());

                                        if (invProductoSaldosConsultaOrigen != null) {
                                            /// COMO SI EXISTE EN LA TABLA
                                            /// SE LE RESTA AL ORIGEN
                                            invProductoSaldosOrigen.setStkSaldoFinal(
                                                    invProductoSaldosConsultaOrigen.getStkSaldoFinal().subtract(
                                                            listadoDetalleTransferencias.get(i).getDetCantidad()));

                                            if (invProductoSaldosOrigen.getStkSaldoFinal().compareTo(cero) < 0) {
                                                retorno = "F<html>No se puede transferir porque no hay stock suficiente en los siguientes productos:</html>";
                                                mensajeClase.add(listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal() + " \t\t"
                                                        + listadoDetalleTransferencias.get(i).getInvProducto().getProNombre() + " : "
                                                        + listadoDetalleTransferencias.get(i).getDetCantidad() + " - Saldo Disponible : "
                                                        + invProductoSaldosConsultaOrigen.getStkSaldoFinal().setScale(2, BigDecimal.ROUND_HALF_UP));
                                            }
                                        } else {
                                            retorno = "F<html>No se puede transferir porque no hay stock suficiente en los siguientes productos:</html>";
                                            mensajeClase.add(listadoDetalleTransferencias.get(i).getInvProducto()
                                                    .getInvProductoPK().getProCodigoPrincipal() + " \t\t"
                                                    + listadoDetalleTransferencias.get(i).getInvProducto().getProNombre() + " : "
                                                    + listadoDetalleTransferencias.get(i).getDetCantidad() + " - Saldo : " + new BigDecimal("0.00"));
                                        }
                                    } else {
                                        retorno = "F<html>No es posible guardar porque los siguientes productos son de tipo SERVICIO o ACTIVO FIJO :</html>";
                                        mensajeClase.add(listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal() + " \t\t"
                                                + listadoDetalleTransferencias.get(i).getInvProducto().getProNombre() + " - Tipo : "
                                                + listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoTipo().getTipTipo());
                                    }
                                }
                            }

                            if (mensajeClase.isEmpty()) {
                                /// PREPARANDO OBJETO SISSUCESO (susClave y
                                /// susDetalle se llenan en DAOTransaccion)
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                        susDetalle, sisInfoTO);
                                comprobar = transferenciasDao.insertarTransaccionInvTransferencias(
                                        invTransferencias, listInvTransferenciasDetalle, sisSuceso);
                                invTransferenciasTO.setTransNumero(invTransferencias.getInvTransferenciasPK().getTransNumero());

                                if (comprobar) {
                                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                            invTransferenciasTO.getTransEmpresa(),
                                            invTransferencias.getInvTransferenciasPK().getTransPeriodo());
                                    retorno = "T<html>Se ingresó la transferencia con la siguiente información:<br><br>"
                                            + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                            + "</font>.<br> Motivo: <font size = 5>"
                                            + invTransferencias.getInvTransferenciasPK().getTransMotivo()
                                            + "</font>.<br> Número: <font size = 5>"
                                            + invTransferencias.getInvTransferenciasPK().getTransNumero()
                                            + "</font>.</html>"
                                            + invTransferencias.getInvTransferenciasPK().getTransNumero() + ","
                                            + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                    mensajeTO.setFechaCreacion(invTransferencias.getUsrFechaInserta().toString());
                                    mensajeTO.getMap().put("invTransferencias", invTransferencias);
                                } else {
                                    retorno = "FHubo un error al guardar la Transferencia...\nIntente de nuevo o contacte con el administrador";
                                }
                            } else {
                                mensajeTO.setMensaje(retorno);
                                mensajeTO.setListaErrores1(mensajeClase);
                            }
                        } else if (estadoDetalle == 1) {
                            retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                        } else {
                            retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                        }
                    }
                } else {
                    retorno = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                }
            } else {
                retorno = "FNo se encuentra ningún periodo para la fecha que ingresó...";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        mensajeTO.setMensaje(retorno);
        if (mensajeTO.getMensaje().charAt(0) == 'T' && !pendiente) {
            quitarPendiente(invTransferenciasTO);
        }
        return mensajeTO;

    }

    @Override
    public MensajeTO modificarInvTransferenciasTO(InvTransferenciasTO invTransferenciasTO,
            List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO,
            List<InvTransferenciasDetalleTO> listaInvTransferenciasEliminarDetalleTO, boolean desmayorizar,
            InvTransferenciasMotivoAnulacion invTransferenciasMotivoAnulacion, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>();
        String retorno = "";
        InvProductoSaldos invProductoSaldosOrigen = null;
        InvProductoSaldos invProductoSaldosDestino = null;
        List<InvProductoSaldos> listaInvProductoSaldosOrigen = new ArrayList<InvProductoSaldos>();
        List<InvProductoSaldos> listaInvProductoSaldosDestino = new ArrayList<InvProductoSaldos>();
        boolean periodoCerrado = false;
        if (!UtilsValidacion.isFechaSuperior(invTransferenciasTO.getTransFecha(), "yyyy-MM-dd")) {

            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
            listaSisPeriodoTO = periodoService.getListaPeriodoTO(invTransferenciasTO.getTransEmpresa());

            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(invTransferenciasTO.getTransFecha(), "yyyy-MM-dd")
                        .getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(invTransferenciasTO.getTransFecha(), "yyyy-MM-dd")
                                .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                                .getTime()) {
                    comprobar = true;
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (comprobar) {
                if (!periodoCerrado) {
                    if (transferenciasMotivoDao.comprobarInvTransferenciaMotivo(
                            invTransferenciasTO.getTransEmpresa(), invTransferenciasTO.getTransMotivo())) {
                        InvTransferencias invTransferenciasCreados = transferenciasDao.buscarInvTransferencias(
                                invTransferenciasTO.getTransEmpresa(), invTransferenciasTO.getTransPeriodo(),
                                invTransferenciasTO.getTransMotivo(), invTransferenciasTO.getTransNumero());
                        if (invTransferenciasCreados != null) {

                            String detalleError = "";
                            if (desmayorizar && listaInvTransferenciasDetalleTO == null) {
                                List<InvListaDetalleTransferenciaTO> invListaDetalleTO = transferenciasDetalleDao
                                        .getListaInvTransferenciasDetalleTO(invTransferenciasTO.getTransEmpresa(),
                                                invTransferenciasTO.getTransPeriodo(),
                                                invTransferenciasTO.getTransMotivo(),
                                                invTransferenciasTO.getTransNumero());
                                if (invListaDetalleTO != null) {
                                    listaInvTransferenciasDetalleTO = new ArrayList<InvTransferenciasDetalleTO>();
                                    for (int i = 0; i < invListaDetalleTO.size(); i++) {
                                        InvTransferenciasDetalleTO invTransferenciasDetalleTO = new InvTransferenciasDetalleTO();
                                        invTransferenciasDetalleTO
                                                .setTransEmpresa(invTransferenciasTO.getTransEmpresa());
                                        invTransferenciasDetalleTO
                                                .setTransPeriodo(invTransferenciasTO.getTransPeriodo());
                                        invTransferenciasDetalleTO
                                                .setTransMotivo(invTransferenciasTO.getTransMotivo());
                                        invTransferenciasDetalleTO
                                                .setTransNumero(invTransferenciasTO.getTransNumero());

                                        invTransferenciasDetalleTO
                                                .setDetSecuencial(invListaDetalleTO.get(i).getDetSecuencial());
                                        invTransferenciasDetalleTO.setDetOrden(i + 1);
                                        invTransferenciasDetalleTO
                                                .setDetCantidad(
                                                        invListaDetalleTO.get(i).getDetCantidad() != null
                                                        ? invListaDetalleTO.get(i).getDetCantidad()
                                                        : BigDecimal.ZERO);
                                        invTransferenciasDetalleTO
                                                .setBodOrigenEmpresa(invTransferenciasTO.getTransEmpresa());
                                        invTransferenciasDetalleTO
                                                .setBodOrigenCodigo(invListaDetalleTO.get(i).getBodOrigenCodigo());
                                        invTransferenciasDetalleTO
                                                .setBodDestinoEmpresa(invTransferenciasTO.getTransEmpresa());
                                        invTransferenciasDetalleTO.setBodDestinoCodigo(
                                                invListaDetalleTO.get(i).getBodDestinoCodigo());
                                        invTransferenciasDetalleTO
                                                .setSecOrigenEmpresa(invTransferenciasTO.getTransEmpresa());
                                        invTransferenciasDetalleTO
                                                .setSecOrigenCodigo(invListaDetalleTO.get(i).getCpOrigen());
                                        invTransferenciasDetalleTO
                                                .setSecDestinoEmpresa(invTransferenciasTO.getTransEmpresa());
                                        invTransferenciasDetalleTO
                                                .setSecDestinoCodigo(invListaDetalleTO.get(i).getCpDestino());
                                        invTransferenciasDetalleTO
                                                .setProEmpresa(invTransferenciasTO.getTransEmpresa());
                                        invTransferenciasDetalleTO.setProCodigoPrincipal(
                                                invListaDetalleTO.get(i).getProCodigoPrincipal());
                                        listaInvTransferenciasDetalleTO.add(invTransferenciasDetalleTO);
                                    }
                                } else {
                                    detalleError = "Hubo en error al recuperar el detalle de la TRANSFERENCIA.\nContacte con el administrador...";
                                }
                            }

                            if (detalleError.trim().isEmpty()) {
                                susClave = invTransferenciasTO.getTransPeriodo() + " "
                                        + invTransferenciasTO.getTransMotivo() + " "
                                        + invTransferenciasTO.getTransNumero();

                                susDetalle = invTransferenciasTO.getTransAnulado()
                                        ? "Transferencia número " + invTransferenciasTO.getTransNumero()
                                        + " se anuló por "
                                        + invTransferenciasMotivoAnulacion.getAnuComentario()
                                        : "Se modificó la transferencia en el periodo "
                                        + invTransferenciasTO.getTransPeriodo() + ", del motivo "
                                        + invTransferenciasTO.getTransMotivo() + ", de la numeración "
                                        + invTransferenciasTO.getTransNumero();
                                susSuceso = "UPDATE";
                                susTabla = "inventario.inv_transferencias";
                                invTransferenciasTO.setUsrCodigo(invTransferenciasCreados.getUsrCodigo());
                                invTransferenciasTO.setUsrFechaInserta(UtilsValidacion.fecha(
                                        invTransferenciasCreados.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                ////// CREANDO NUMERACION DE CONSUMO
                                ////// CREANDO CONSUMOS
                                InvTransferencias invTransferencias = ConversionesInventario
                                        .convertirInvTransferenciasTO_InvTransferencias(invTransferenciasTO);
                                ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                                List<InvTransferenciasDetalle> listInvTransferenciasDetalle = new ArrayList<InvTransferenciasDetalle>(
                                        0);
                                InvTransferenciasDetalle invTransferenciasDetalle = null;

                                int estadoDetalle = 0;
                                for (InvTransferenciasDetalleTO invTransferenciasDetalleTO : listaInvTransferenciasDetalleTO) {
                                    invTransferenciasDetalle = new InvTransferenciasDetalle();
                                    invTransferenciasDetalle = ConversionesInventario
                                            .convertirInvTransferenciasDetalleTO_InvTransferenciasDetalle(
                                                    invTransferenciasDetalleTO);

                                    ///// BUSCANDO EL PRODUCTO EN DETALLE
                                    InvProducto invProducto = productoDao.buscarInvProducto(
                                            invTransferenciasDetalleTO.getTransEmpresa(),
                                            invTransferenciasDetalleTO.getProCodigoPrincipal());
                                    if (invProducto != null) {
                                        invTransferenciasDetalle.setInvProducto(invProducto);
                                        ////// BUSCANDO LA BODEGA EN EL
                                        ////// DETALLE ORIGEN
                                        InvBodega invBodegaOrigen = bodegaDao.buscarInvBodega(
                                                invTransferenciasDetalleTO.getTransEmpresa(),
                                                invTransferenciasDetalleTO.getBodOrigenCodigo());
                                        ////// BUSCANDO LA BODEGA EN EL
                                        ////// DETALLE DESTINO
                                        InvBodega invBodegaDestino = bodegaDao.buscarInvBodega(
                                                invTransferenciasDetalleTO.getTransEmpresa(),
                                                invTransferenciasDetalleTO.getBodDestinoCodigo());
                                        if (invBodegaOrigen != null && invBodegaDestino != null) {
                                            invTransferenciasDetalle.setInvBodega(invBodegaOrigen);
                                            invTransferenciasDetalle.setInvBodega1(invBodegaDestino);
                                            listInvTransferenciasDetalle.add(invTransferenciasDetalle);
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
                                    ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                                    ////// A ELIMINAR
                                    List<InvTransferenciasDetalle> listInvTransferenciasDetalleEliminar = new ArrayList<InvTransferenciasDetalle>();
                                    InvTransferenciasDetalle invTransferenciasDetalleEliminar = null;

                                    int estadoDetalleEliminar = 0;
                                    if (listaInvTransferenciasEliminarDetalleTO != null) {
                                        for (InvTransferenciasDetalleTO invTransferenciasEliminarDetalleTO : listaInvTransferenciasEliminarDetalleTO) {
                                            invTransferenciasDetalleEliminar = new InvTransferenciasDetalle();
                                            invTransferenciasEliminarDetalleTO
                                                    .setTransPeriodo(invTransferenciasTO.getTransPeriodo());
                                            invTransferenciasDetalleEliminar = ConversionesInventario
                                                    .convertirInvTransferenciasDetalleTO_InvTransferenciasDetalle(
                                                            invTransferenciasEliminarDetalleTO);
                                            invTransferenciasDetalleEliminar
                                                    .setInvTransferencias(invTransferencias);
                                            ///// BUSCANDO EL PRODUCTO EN DETALLE
                                            InvProducto invProducto = null;
                                            if (invTransferenciasEliminarDetalleTO.getProCodigoPrincipal() == null || invTransferenciasEliminarDetalleTO.getProCodigoPrincipal().equals("")) {
                                                listInvTransferenciasDetalleEliminar.add(invTransferenciasDetalleEliminar);
                                            } else {
                                                invProducto = productoDao.buscarInvProducto(
                                                        invTransferenciasEliminarDetalleTO.getTransEmpresa(),
                                                        invTransferenciasEliminarDetalleTO.getProCodigoPrincipal());
                                                if (invProducto != null) {
                                                    invTransferenciasDetalleEliminar.setInvProducto(invProducto);
                                                    ////// BUSCANDO LA BODEGA EN
                                                    ////// EL DETALLE ORIGEN
                                                    InvBodega invBodegaOrigen = bodegaDao.buscarInvBodega(
                                                            invTransferenciasEliminarDetalleTO.getTransEmpresa(),
                                                            invTransferenciasEliminarDetalleTO.getBodOrigenCodigo());
                                                    ////// BUSCANDO LA BODEGA EN
                                                    ////// EL DETALLE DESTINO
                                                    InvBodega invBodegaDestino = bodegaDao.buscarInvBodega(
                                                            invTransferenciasEliminarDetalleTO.getTransEmpresa(),
                                                            invTransferenciasEliminarDetalleTO.getBodDestinoCodigo());

                                                    if (invBodegaOrigen != null && invBodegaDestino != null) {
                                                        invTransferenciasDetalleEliminar.setInvBodega1(invBodegaOrigen);
                                                        invTransferenciasDetalleEliminar.setInvBodega(invBodegaDestino);
                                                        listInvTransferenciasDetalleEliminar
                                                                .add(invTransferenciasDetalleEliminar);
                                                    } else {
                                                        estadoDetalle = 2;
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

                                        if (!invTransferencias.getTransPendiente() || desmayorizar) {
                                            List<InvTransferenciasDetalle> listadoDetalleTransferencias = agrupraProductosBodegaTransferencia(listInvTransferenciasDetalle);
                                            for (int i = 0; i < listadoDetalleTransferencias.size(); i++) {
                                                if (!listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("COSTO O GASTO")
                                                        && !listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("SERVICIOS")
                                                        && !listadoDetalleTransferencias.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("ACTIVO FIJO")) {
                                                    invProductoSaldosOrigen = new InvProductoSaldos();
                                                    invProductoSaldosDestino = new InvProductoSaldos();
                                                    //// CREO LA LLAVE DE
                                                    //// ORIGEN
                                                    invProductoSaldosOrigen.setStkSecuencial(0);

                                                    //// CREO LA LLAVE DE
                                                    //// DESTINO
                                                    invProductoSaldosDestino.setStkSecuencial(0);
                                                    //// BUSCO LA BODEGA DE
                                                    //// ORIGEN
                                                    invProductoSaldosOrigen.setInvBodega(bodegaDao.buscarInvBodega(
                                                            invTransferencias.getInvTransferenciasPK()
                                                                    .getTransEmpresa(),
                                                            listadoDetalleTransferencias.get(i).getInvBodega()
                                                                    .getInvBodegaPK().getBodCodigo()));
                                                    //// BUSCO LA BODEGA DE
                                                    //// DESTINO
                                                    invProductoSaldosDestino.setInvBodega(bodegaDao.buscarInvBodega(
                                                            invTransferencias.getInvTransferenciasPK()
                                                                    .getTransEmpresa(),
                                                            listadoDetalleTransferencias.get(i).getInvBodega1()
                                                                    .getInvBodegaPK().getBodCodigo()));
                                                    //// PONGO EL PRODUCTO
                                                    //// EN ORIGEN
                                                    invProductoSaldosOrigen.setInvProducto(
                                                            listadoDetalleTransferencias.get(i).getInvProducto());
                                                    //// PONGO EL PRODUCTO
                                                    //// EN DESTINO
                                                    invProductoSaldosDestino.setInvProducto(
                                                            listadoDetalleTransferencias.get(i).getInvProducto());
                                                    //// BUSQUEDA DE LA
                                                    //// ENTIDAD ORIGEN
                                                    InvProductoSaldos invProductoSaldosConsultaOrigen = productoSaldosDao
                                                            .getInvProductoSaldo(
                                                                    invTransferencias.getInvTransferenciasPK()
                                                                            .getTransEmpresa(),
                                                                    listadoDetalleTransferencias.get(i)
                                                                            .getInvBodega().getInvBodegaPK()
                                                                            .getBodCodigo(),
                                                                    listadoDetalleTransferencias.get(i)
                                                                            .getInvProducto().getInvProductoPK()
                                                                            .getProCodigoPrincipal());
                                                    //// BUSQUEDA DE LA
                                                    //// ENTIDAD DESTINO
                                                    InvProductoSaldos invProductoSaldosConsultaDestino = productoSaldosDao
                                                            .getInvProductoSaldo(
                                                                    invTransferencias.getInvTransferenciasPK()
                                                                            .getTransEmpresa(),
                                                                    listadoDetalleTransferencias.get(i)
                                                                            .getInvBodega1().getInvBodegaPK()
                                                                            .getBodCodigo(),
                                                                    listadoDetalleTransferencias.get(i)
                                                                            .getInvProducto().getInvProductoPK()
                                                                            .getProCodigoPrincipal());
                                                    if (desmayorizar || invTransferencias.getTransAnulado()) {
                                                        if (invProductoSaldosConsultaDestino != null) {
                                                            // COMO SI
                                                            // EXISTE EN LA
                                                            // TABLA SE LE
                                                            // RESTA AL
                                                            // DESTINO
                                                            invProductoSaldosDestino.setStkSaldoFinal(
                                                                    invProductoSaldosConsultaDestino
                                                                            .getStkSaldoFinal()
                                                                            .subtract(listadoDetalleTransferencias
                                                                                    .get(i).getDetCantidad()));
                                                            /// COMO SI
                                                            /// EXISTE EL
                                                            /// DESTINO EN
                                                            /// LA TABLA SE
                                                            /// LE SUMA AL
                                                            /// ORIGEN PERO
                                                            /// NO SIN ANTES
                                                            /// PREGUNTAR SI
                                                            /// YA EXISTE EL
                                                            /// ORIGEN, SINO
                                                            /// PARA CREARLO
                                                            if (invProductoSaldosConsultaOrigen == null) {
                                                                invProductoSaldosOrigen.setStkSaldoFinal(
                                                                        listadoDetalleTransferencias.get(i)
                                                                                .getDetCantidad());
                                                            } else {
                                                                invProductoSaldosOrigen
                                                                        .setStkSaldoFinal(
                                                                                invProductoSaldosConsultaOrigen
                                                                                        .getStkSaldoFinal()
                                                                                        .add(listadoDetalleTransferencias
                                                                                                .get(i)
                                                                                                .getDetCantidad()));
                                                            }
                                                            if (invProductoSaldosDestino.getStkSaldoFinal()
                                                                    .compareTo(cero) >= 0) {
                                                                invProductoSaldosDestino
                                                                        .setStkFechaUltimaCompraFinal(
                                                                                invTransferencias.getTransFecha());

                                                                invProductoSaldosOrigen
                                                                        .setStkFechaUltimaCompraFinal(
                                                                                invProductoSaldosConsultaOrigen
                                                                                        .getStkFechaUltimaCompraFinal());

                                                                invProductoSaldosDestino
                                                                        .setStkValorUltimaCompraFinal(
                                                                                invProductoSaldosConsultaDestino
                                                                                        .getStkValorUltimaCompraFinal());

                                                                invProductoSaldosDestino.setStkSaldoInicial(
                                                                        invProductoSaldosConsultaDestino
                                                                                .getStkSaldoInicial());
                                                                invProductoSaldosDestino.setStkValorPromedioInicial(
                                                                        invProductoSaldosConsultaDestino
                                                                                .getStkValorPromedioInicial());
                                                                invProductoSaldosDestino.setStkValorPromedioFinal(
                                                                        invProductoSaldosConsultaDestino
                                                                                .getStkValorPromedioFinal());

                                                                invProductoSaldosOrigen
                                                                        .setStkValorUltimaCompraFinal(
                                                                                invProductoSaldosConsultaOrigen
                                                                                        .getStkValorUltimaCompraFinal());

                                                                invProductoSaldosOrigen.setStkSaldoInicial(
                                                                        invProductoSaldosConsultaOrigen
                                                                                .getStkSaldoInicial());
                                                                invProductoSaldosOrigen.setStkValorPromedioInicial(
                                                                        invProductoSaldosConsultaOrigen
                                                                                .getStkValorPromedioInicial());
                                                                invProductoSaldosOrigen.setStkValorPromedioFinal(
                                                                        invProductoSaldosConsultaOrigen
                                                                                .getStkValorPromedioFinal());

                                                                listaInvProductoSaldosOrigen
                                                                        .add(invProductoSaldosOrigen);
                                                                listaInvProductoSaldosDestino
                                                                        .add(invProductoSaldosDestino);
                                                            } else {
                                                                retorno = "F<html>No se puede transferir porque no hay stock suficiente en los siguientes productos:</html>";
                                                                mensajeClase.add(listadoDetalleTransferencias.get(i)
                                                                        .getInvProducto().getInvProductoPK()
                                                                        .getProCodigoPrincipal()
                                                                        + " \t\t"
                                                                        + listadoDetalleTransferencias.get(i)
                                                                                .getInvProducto().getProNombre()
                                                                        + " : "
                                                                        + listadoDetalleTransferencias.get(i)
                                                                                .getDetCantidad()
                                                                        + " - Saldo Disponible : "
                                                                        + invProductoSaldosConsultaOrigen
                                                                                .getStkSaldoFinal().setScale(2,
                                                                                        BigDecimal.ROUND_HALF_UP));
                                                            }
                                                        } else {
                                                            retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                                                            mensajeClase.add(listadoDetalleTransferencias.get(i)
                                                                    .getInvProducto().getInvProductoPK()
                                                                    .getProCodigoPrincipal()
                                                                    + " \t\t"
                                                                    + listadoDetalleTransferencias.get(i)
                                                                            .getInvProducto().getProNombre()
                                                                    + " : "
                                                                    + listadoDetalleTransferencias.get(i)
                                                                            .getDetCantidad()
                                                                    + " - Saldo Disponible : "
                                                                    + invProductoSaldosConsultaOrigen
                                                                            .getStkSaldoFinal()
                                                                            .setScale(2, BigDecimal.ROUND_HALF_UP));
                                                        }
                                                    } else if (invProductoSaldosConsultaOrigen != null) {
                                                        /// COMO SI
                                                        /// EXISTE EN LA
                                                        /// TABLA SE LE
                                                        /// RESTA AL
                                                        /// ORIGEN
                                                        BigDecimal finalSaldo = invProductoSaldosConsultaOrigen.getStkSaldoFinal();
                                                        BigDecimal cantidad = listadoDetalleTransferencias.get(i).getDetCantidad() != null ? listadoDetalleTransferencias.get(i).getDetCantidad() : BigDecimal.ZERO;
                                                        BigDecimal resta = finalSaldo.subtract(cantidad);
                                                        invProductoSaldosOrigen.setStkSaldoFinal(resta);
                                                        /// COMO SI
                                                        /// EXISTE EL
                                                        /// ORIGEN EN LA
                                                        /// TABLA SE LE
                                                        /// SUMA AL
                                                        /// DESTINO PERO
                                                        /// NO SIN ANTES
                                                        /// PREGUNTAR SI
                                                        /// YA EXISTE EL
                                                        /// DESTINO,
                                                        /// SINO PARA
                                                        /// CREARLO
                                                        if (invProductoSaldosConsultaDestino == null) {
                                                            invProductoSaldosDestino.setStkSaldoFinal(
                                                                    listadoDetalleTransferencias.get(i)
                                                                            .getDetCantidad());
                                                        } else {
                                                            invProductoSaldosDestino.setStkSaldoFinal(
                                                                    invProductoSaldosConsultaDestino
                                                                            .getStkSaldoFinal()
                                                                            .add(cantidad));
                                                        }
                                                        if (invProductoSaldosOrigen.getStkSaldoFinal()
                                                                .compareTo(cero) >= 0) {
                                                            invProductoSaldosOrigen
                                                                    .setStkFechaUltimaCompraFinal(
                                                                            invProductoSaldosConsultaOrigen
                                                                                    .getStkFechaUltimaCompraFinal());

                                                            invProductoSaldosOrigen
                                                                    .setStkValorUltimaCompraFinal(
                                                                            invProductoSaldosConsultaOrigen
                                                                                    .getStkValorUltimaCompraFinal());

                                                            invProductoSaldosOrigen.setStkSaldoInicial(
                                                                    invProductoSaldosConsultaOrigen
                                                                            .getStkSaldoInicial());
                                                            invProductoSaldosOrigen.setStkValorPromedioInicial(
                                                                    invProductoSaldosConsultaOrigen
                                                                            .getStkValorPromedioInicial());
                                                            invProductoSaldosOrigen.setStkValorPromedioFinal(
                                                                    invProductoSaldosConsultaOrigen
                                                                            .getStkValorPromedioFinal());

                                                            invProductoSaldosDestino
                                                                    .setStkValorUltimaCompraFinal(
                                                                            invProductoSaldosConsultaDestino == null
                                                                                    ? cero
                                                                                    : invProductoSaldosConsultaDestino
                                                                                            .getStkValorUltimaCompraFinal());

                                                            invProductoSaldosDestino
                                                                    .setStkFechaUltimaCompraFinal(
                                                                            invProductoSaldosConsultaDestino == null
                                                                                    ? invTransferencias
                                                                                            .getTransFecha()
                                                                                    : invTransferencias
                                                                                            .getTransFecha());

                                                            invProductoSaldosDestino.setStkSaldoInicial(
                                                                    invProductoSaldosConsultaDestino == null
                                                                            ? cero
                                                                            : invProductoSaldosConsultaDestino
                                                                                    .getStkSaldoInicial());
                                                            invProductoSaldosDestino.setStkValorPromedioInicial(
                                                                    invProductoSaldosConsultaDestino == null
                                                                            ? cero
                                                                            : invProductoSaldosConsultaDestino
                                                                                    .getStkValorPromedioInicial());
                                                            invProductoSaldosDestino.setStkValorPromedioFinal(
                                                                    invProductoSaldosConsultaDestino == null
                                                                            ? cero
                                                                            : invProductoSaldosConsultaDestino
                                                                                    .getStkValorPromedioFinal());

                                                            invProductoSaldosOrigen
                                                                    .setStkValorUltimaCompraFinal(
                                                                            invProductoSaldosConsultaOrigen
                                                                                    .getStkValorUltimaCompraFinal());

                                                            listaInvProductoSaldosOrigen
                                                                    .add(invProductoSaldosOrigen);
                                                            listaInvProductoSaldosDestino
                                                                    .add(invProductoSaldosDestino);
                                                        } else {
                                                            retorno = "F<html>No se puede transferir porque no hay stock suficiente en los siguientes productos:</html>";
                                                            mensajeClase.add(listadoDetalleTransferencias.get(i)
                                                                    .getInvProducto().getInvProductoPK()
                                                                    .getProCodigoPrincipal()
                                                                    + " \t\t"
                                                                    + listadoDetalleTransferencias.get(i)
                                                                            .getInvProducto().getProNombre()
                                                                    + " : "
                                                                    + listadoDetalleTransferencias.get(i)
                                                                            .getDetCantidad()
                                                                    + " - Saldo Disponible : "
                                                                    + invProductoSaldosConsultaOrigen
                                                                            .getStkSaldoFinal().setScale(2,
                                                                                    BigDecimal.ROUND_HALF_UP));
                                                        }
                                                    } else {
                                                        retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                                                        mensajeClase.add(listadoDetalleTransferencias.get(i)
                                                                .getInvProducto().getInvProductoPK()
                                                                .getProCodigoPrincipal()
                                                                + " \t\t"
                                                                + listadoDetalleTransferencias.get(i)
                                                                        .getInvProducto().getProNombre()
                                                                + " : "
                                                                + listadoDetalleTransferencias.get(i)
                                                                        .getDetCantidad()
                                                                + " - Saldo Disponible : "
                                                                + new BigDecimal("0.00"));
                                                    }
                                                } else {
                                                    retorno = "F<html>No es posible guardar porque los siguientes productos son de tipo SERVICIO o ACTIVO FIJO :</html>";
                                                    mensajeClase.add(
                                                            listadoDetalleTransferencias.get(i).getInvProducto()
                                                                    .getInvProductoPK().getProCodigoPrincipal()
                                                            + " \t\t"
                                                            + listadoDetalleTransferencias.get(i)
                                                                    .getInvProducto().getProNombre()
                                                            + " - Tipo : "
                                                            + listadoDetalleTransferencias.get(i)
                                                                    .getInvProducto().getInvProductoTipo()
                                                                    .getTipTipo());
                                                }
                                            }
                                        }

                                        if (mensajeClase.isEmpty()) {
                                            if (invTransferencias.getTransAnulado()) {
                                                invTransferenciasMotivoAnulacion
                                                        .setInvTransferencias(invTransferencias);
                                            }
                                            /// PREPARANDO OBJETO SISSUCESO
                                            /// (susClave y susDetalle se
                                            /// llenan en DAOTransaccion)
                                            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave,
                                                    susSuceso, susDetalle, sisInfoTO);
                                            comprobar = transferenciasDao.modificarInvTransferencias(
                                                    invTransferencias, listInvTransferenciasDetalle,
                                                    listInvTransferenciasDetalleEliminar, sisSuceso,
                                                    listaInvProductoSaldosOrigen, listaInvProductoSaldosDestino,
                                                    invTransferenciasMotivoAnulacion, desmayorizar);
                                            if (comprobar) {
                                                SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                                        invTransferenciasTO.getTransEmpresa(), invTransferencias
                                                        .getInvTransferenciasPK().getTransPeriodo());
                                                retorno = "T<html>Se  "
                                                        + (invTransferenciasTO.getTransAnulado() ? "anuló"
                                                        : "modificó")
                                                        + "  la transferencia con la siguiente información:<br><br>"
                                                        + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                                        + "</font>.<br> Motivo: <font size = 5>"
                                                        + invTransferencias.getInvTransferenciasPK()
                                                                .getTransMotivo()
                                                        + "</font>.<br> Número: <font size = 5>" + invTransferencias
                                                                .getInvTransferenciasPK().getTransNumero()
                                                        + "</font>.</html>";
                                                mensajeTO.setFechaCreacion(
                                                        invTransferencias.getUsrFechaInserta().toString());
                                                mensajeTO.getMap().put("invTransferencias", invTransferencias);
                                            } else {
                                                retorno = "FHubo un error al guardar la Transferencia...\nIntente de nuevo o contacte con el administrador";
                                            }
                                        } else {
                                            mensajeTO.setMensaje(retorno);
                                            mensajeTO.setListaErrores1(mensajeClase);
                                        }
                                    } else if (estadoDetalleEliminar == 1) {
                                        retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                    } else {
                                        retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                    }
                                } else if (estadoDetalle == 1) {
                                    retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                } else {
                                    retorno = "F<html>Una de las Bodega que escogió ya no está disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                }
                            } else {
                                retorno = "F<html>" + detalleError + "</html>";
                            }
                        }
                    } else {
                        retorno = "FNo se encuentra el motivo...";
                    }
                } else {
                    retorno = "F<html>No se puede MAYORIZAR, DESMAYORIZAR o ANULAR debido a que el período se encuentra cerrado...</html>";
                }
            } else {
                retorno = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    private List<InvTransferenciasDetalle> agrupraProductosBodegaTransferencia(
            List<InvTransferenciasDetalle> transferenciasDetalle) throws Exception {
        List<InvTransferenciasDetalle> listaDetalleFinal = new ArrayList<InvTransferenciasDetalle>();
        listaDetalleFinal.add(ConversionesInventario
                .convertirInvTransferenciasDetalle_InvTransferenciasDetalle(transferenciasDetalle.get(0)));

        int contador = 0;
        boolean encontro = false;

        for (int i = 1; i < transferenciasDetalle.size(); i++) {
            contador = 0;
            for (InvTransferenciasDetalle invTransferenciasDetalleAux : listaDetalleFinal) {
                if (transferenciasDetalle.get(i).getInvBodega().getInvBodegaPK().getBodCodigo()
                        .equals(invTransferenciasDetalleAux.getInvBodega().getInvBodegaPK().getBodCodigo())
                        && transferenciasDetalle.get(i).getInvBodega1().getInvBodegaPK().getBodCodigo()
                                .equals(invTransferenciasDetalleAux.getInvBodega1().getInvBodegaPK().getBodCodigo())
                        && transferenciasDetalle.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                                .equals(invTransferenciasDetalleAux.getInvProducto().getInvProductoPK()
                                        .getProCodigoPrincipal())) {
                    encontro = true;
                    break;
                } else {
                    encontro = false;
                    contador++;
                }
            }

            if (encontro) {
                if (listaDetalleFinal.get(contador).getDetCantidad() == null) {
                    listaDetalleFinal.get(contador).setDetCantidad(BigDecimal.ZERO);
                }
                listaDetalleFinal.get(contador).setDetCantidad(listaDetalleFinal.get(contador).getDetCantidad()
                        .add(transferenciasDetalle.get(i).getDetCantidad()));
            } else {
                listaDetalleFinal.add(ConversionesInventario
                        .convertirInvTransferenciasDetalle_InvTransferenciasDetalle(transferenciasDetalle.get(i)));
            }
        }

        return listaDetalleFinal;
    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return transferenciasDao.getEstadoCCCVT(empresa, periodo, motivo, numero);
    }

    @Override
    public void quitarPendiente(InvTransferenciasTO invTransferenciasTO) throws Exception {
        InvTransferencias invTransferencias = ConversionesInventario
                .convertirInvTransferenciasTO_InvTransferencias(invTransferenciasTO);
        transferenciasDao.actualizarPendienteSql(invTransferencias.getInvTransferenciasPK(), false);
    }

    @Override
    public String desmayorizarTransferencia(String empresa, String periodo, String motivo, String numero) throws Exception {
        String mensaje = "";
        InvTransferencias invTransferencias = transferenciasDao.buscarInvTransferencias(empresa, periodo, motivo, numero);

        if ((mensaje = periodoService.validarPeriodo(invTransferencias.getTransFecha(),
                invTransferencias.getInvTransferenciasPK().getTransEmpresa())) == null) {
            transferenciasDao.actualizarPendienteSql(new InvTransferenciasPK(empresa, periodo, motivo, numero), true);
            mensaje = "TLa transferencia: código " + numero + ", se ha desmayorizado correctamente.";
        }
        return mensaje;
    }

    @Override
    public String anularTransferencia(String empresa, String periodo, String motivo, String numero) throws Exception {
        String mensaje = "";
        transferenciasDao.anularRestaurarSql(new InvTransferenciasPK(empresa, periodo, motivo, numero), true);
        mensaje = "TLa transferencia: código " + numero + ", se ha anulado correctamente.";
        return mensaje;
    }

    @Override
    public String restaurarTransferencia(String empresa, String periodo, String motivo, String numero) throws Exception {
        String mensaje = "";
        InvTransferencias invTransferencias = transferenciasDao.buscarInvTransferencias(empresa, periodo, motivo, numero);

        if ((mensaje = periodoService.validarPeriodo(invTransferencias.getTransFecha(),
                invTransferencias.getInvTransferenciasPK().getTransEmpresa())) == null) {
            transferenciasDao.anularRestaurarSql(new InvTransferenciasPK(empresa, periodo, motivo, numero), false);
            mensaje = "TLa transferencia: código " + numero + ", se ha restaurado correctamente.";
        }
        return mensaje;
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosNuevaTransferencia(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<InvComboBodegaTO> listadoBodega = bodegaService.getInvComboBodegaTO(empresa);
        List<InvTransferenciaMotivoTO> listadoMotivosTransferencia = transferenciasMotivoService.getListaTransferenciaMotivoTO(empresa, false);
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo periodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        campos.put("fechaActual", fechaActual);
        campos.put("listadoBodega", listadoBodega);
        campos.put("listadoMotivosTransferencia", listadoMotivosTransferencia);
        campos.put("periodoAbierto", (periodo != null ? (periodo.getPerCerrado() ? false : true) : false));
        return campos;
    }

    @Override
    public Map<String, Object> consultarTransferencia(Map<String, Object> map) throws Exception {

        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroTransferencia = UtilsJSON.jsonToObjeto(String.class, map.get("numeroTransferencia"));

        InvTransferenciasTO cabecera = getInvTransferenciasCabeceraTO(empresa, codigoPeriodo, motivo, numeroTransferencia);
        List<InvListaDetalleTransferenciaTO> listaDetalleTO = transferenciasDetalleDao.getListaInvTransferenciasDetalleTO(empresa, codigoPeriodo, motivo, numeroTransferencia);
        List<InvTransferenciasDetalle> listaDetalle = transferenciasDetalleDao.obtenerTransferenciaDetallePorNumero(empresa, codigoPeriodo, motivo, numeroTransferencia);
        List<InvTransferenciaMotivoTO> motivosTransferencia = transferenciasMotivoService.getListaTransferenciaMotivoTO(empresa, false);
        List<InvComboBodegaTO> bodega = bodegaService.getInvComboBodegaTO(empresa);

        for (InvTransferenciasDetalle detalle : listaDetalle) {
            for (InvListaDetalleTransferenciaTO detalleTO : listaDetalleTO) {
                if (detalleTO.getDetSecuencial().equals(detalle.getDetSecuencial())) {
                    //Series
                    List<InvTransferenciaDetalleSeriesTO> listaSerieTO = new ArrayList<>();
                    if (detalle.getInvTransferenciasDetalleSeriesList() != null && detalle.getInvTransferenciasDetalleSeriesList().size() > 0) {
                        for (InvTransferenciasDetalleSeries serie : detalle.getInvTransferenciasDetalleSeriesList()) {
                            InvTransferenciaDetalleSeriesTO serieTO = ConversionesInventario.convertirInvTransferenciasDetalleSeries_InvTransferenciaDetalleSeriesTO(serie);
                            serieTO.setProCodigoPrincipal(detalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
                            listaSerieTO.add(serieTO);
                        }
                    }
                    detalleTO.setInvTransferenciaDetalleSeriesListTO(listaSerieTO);
                    break;
                }
            }

        }

        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(UtilsDate.DeStringADate(cabecera.getTransFecha()), empresa);
        campos.put("cabecera", cabecera);
        campos.put("detalle", listaDetalleTO);
        campos.put("periodoSeleccionado", sisPeriodo);
        campos.put("listadoMotivosTransferencia", motivosTransferencia);
        campos.put("listadoBodega", bodega);
        return campos;
    }

    @Override
    public Map<String, Object> consultarTransferenciaActivo(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroTransferencia = UtilsJSON.jsonToObjeto(String.class, map.get("numeroTransferencia"));
        InvTransferenciasTO cabecera = getInvTransferenciasCabeceraTO(empresa, codigoPeriodo, motivo, numeroTransferencia);
        List<InvListaDetalleTransferenciaTO> listaDetalle = new ArrayList<>();

        if (cabecera != null) {
            if (!cabecera.getTransAnulado() && !cabecera.getTransPendiente()) {
                listaDetalle = transferenciasDetalleDao.getListaInvTransferenciasDetalleTO(empresa, codigoPeriodo, motivo, numeroTransferencia);

                campos.put("mensaje", "T");
            } else {
                if (cabecera.getTransAnulado()) {
                    campos.put("mensaje", "FLa transferencia se encuentra ANULADA");
                } else {
                    campos.put("mensaje", "FLa transferencia se encuentra PENDIENTE");
                }
            }
        } else {
            campos.put("mensaje", "FLa transferencia no existe");
        }

        campos.put("listaInvListaDetalleTransferenciaTO", listaDetalle);
        return campos;
    }

    @Override
    public List<String> desmayorizarTransferenciaLote(String empresa, List<InvListaConsultaTransferenciaTO> lista, SisInfoTO sisInfoTO) throws Exception {
        List<String> respuestas = new ArrayList<>();
        String numero;
        String motivo;
        String periodo;
        String mensaje = "";
        List<String> comprobante;
        for (InvListaConsultaTransferenciaTO transferencia : lista) {
            comprobante = UtilsValidacion.separarComprobante(transferencia.getTransNumero());
            periodo = comprobante.get(0);
            motivo = comprobante.get(1);
            numero = comprobante.get(2);
            if (transferencia.getTransStatus().equals("PENDIENTE")) {
                mensaje = "FNo se puede desmayorizar la transferencia " + numero + " ya está desmayorizado.";
            } else if (transferencia.getTransStatus().equals("ANULADO")) {
                mensaje = "FNo se puede desmayorizar la transferencia " + numero + " está anulada.";
            } else {
                mensaje = desmayorizarTransferencia(empresa, periodo, motivo, numero);
            }
            respuestas.add(mensaje);
        }
        return respuestas;
    }
}
