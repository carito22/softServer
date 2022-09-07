package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosDetalleAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosDetalleComprasDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosDetalleFormaDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosFormaDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCar;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarContableTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarDetalladoGranjasMarinasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPagadasListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorPagarListadoComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosPruebaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorPagarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleCompras;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleForma;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class PagosServiceImpl implements PagosService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ComprasDao comprasDao;
    @Autowired
    private PagosFormaDao pagosFormaDao;
    @Autowired
    private PagosDao pagosDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private PagosAnticiposService pagosAnticiposService;
    @Autowired
    private PagosDetalleComprasDao pagosDetalleComprasDao;
    @Autowired
    private PagosDetalleFormaDao pagosDetalleFormaDao;
    @Autowired
    private PagosDetalleAnticiposDao pagosDetalleAnticiposDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public CarContableTO insertarCarPagos(CarPagosTO carPagosTO,
            List<CarPagosDetalleComprasTO> carPagosDetalleComprasTOs,
            List<CarPagosDetalleAnticiposTO> carPagosDetalleAnticiposTOs,
            List<CarPagosDetalleFormaTO> carPagosDetalleFormaTOs, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        mensaje = "";
        String tipDetalle = "C-PAG";
        CarContableTO carContableTO = new CarContableTO();
        List<String> listaDetalle = new ArrayList<>(1);
        List<String> listaFacturaTO = new ArrayList<>(1);
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<>(1);
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(carPagosTO.getUsrEmpresa());

        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(carPagosTO.getPagFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(carPagosTO.getPagFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()
                    && sisListaPeriodoTO.getPerCerrado() == false) {
                comprobar = true;
                carPagosTO.setPagPeriodo(sisListaPeriodoTO.getPerCodigo());
                break;
            }
        }
        if (comprobar) {
            List<CarListaPagosTO> carListaPagosCobrosTOs = new ArrayList<>(1);
            carListaPagosCobrosTOs = pagosDao.getCarListaPagosTO(carPagosTO.getUsrEmpresa(), carPagosTO.getProvCodigo());
            boolean documento;
            for (int k = 0; k < carPagosDetalleComprasTOs.size(); k++) {
                documento = false;
                for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                    if (carPagosDetalleComprasTOs.get(k).getCompPeriodo()
                            .equals(carListaPagosCobrosTOs.get(i).getCxppPeriodo())
                            && carPagosDetalleComprasTOs.get(k).getCompMotivo()
                                    .equals(carListaPagosCobrosTOs.get(i).getCxppMotivo())
                            && carPagosDetalleComprasTOs.get(k).getCompNumero()
                                    .equals(carListaPagosCobrosTOs.get(i).getCxppNumero())) {
                        documento = true;
                        if ((carPagosDetalleComprasTOs.get(k).getDetValor().abs()
                                .compareTo(carListaPagosCobrosTOs.get(i).getCxppSaldo().abs()) > 0)) {
                            listaDetalle.add(carPagosDetalleComprasTOs.get(k).getCompDocumento() + "     "
                                    + carPagosDetalleComprasTOs.get(k).getCompPeriodo() + " | "
                                    + carPagosDetalleComprasTOs.get(k).getCompMotivo() + " | "
                                    + carPagosDetalleComprasTOs.get(k).getCompNumero());
                        }
                    } else if (i == carListaPagosCobrosTOs.size() - 1 && !documento) {
                        listaDetalle.add(carPagosDetalleComprasTOs.get(k).getCompDocumento() + "     "
                                + carPagosDetalleComprasTOs.get(k).getCompPeriodo() + " | "
                                + carPagosDetalleComprasTOs.get(k).getCompMotivo() + " | "
                                + carPagosDetalleComprasTOs.get(k).getCompNumero());
                    }
                }
            }
            if (listaDetalle.isEmpty()) {
                List<InvCompras> invComprases = new ArrayList<>(0);
                InvCompras invComprasBusqueda = null;
                InvCompras invCompras = null;
                for (CarPagosDetalleComprasTO carPagosDetalleComprasTO : carPagosDetalleComprasTOs) {
                    invComprasBusqueda = comprasDao.buscarInvCompras(carPagosDetalleComprasTO.getCompEmpresa(),
                            carPagosDetalleComprasTO.getCompPeriodo(), carPagosDetalleComprasTO.getCompMotivo(),
                            carPagosDetalleComprasTO.getCompNumero());
                    invCompras = ConversionesInventario.convertirInvCompras_InvCompras(invComprasBusqueda);
                    if (invCompras == null) {
                        listaFacturaTO.add(carPagosDetalleComprasTO.getCompDocumento() + " (no encontrada)");
                    } else {
                        if (invCompras.getCompAnulado()) {
                            listaFacturaTO.add(carPagosDetalleComprasTO.getCompDocumento() + " (anulada)");
                        }
                        if (invCompras.getCompPendiente()) {
                            listaFacturaTO.add(carPagosDetalleComprasTO.getCompDocumento() + " (pendiente)");
                        }
                    }
                    if (invCompras != null && !invCompras.getCompAnulado() && !invCompras.getCompPendiente()) {
                        invCompras.setCompSaldo(
                                invCompras.getCompSaldo().subtract(carPagosDetalleComprasTO.getDetValor()));
                        invComprases.add(invCompras);
                    }
                    invComprasBusqueda = null;
                    invCompras = null;
                }
                //// NO HAY ERRORES EN FACTURA
                if (listaFacturaTO.isEmpty()) {
                    // llenar contable
                    ConContableTO conContableTO = new ConContableTO();
                    conContableTO.setEmpCodigo(carPagosTO.getUsrEmpresa());
                    conContableTO.setPerCodigo(carPagosTO.getPagPeriodo());
                    conContableTO.setTipCodigo(tipDetalle);
                    carPagosTO.setPagTipo(tipDetalle);
                    conContableTO.setConFecha(carPagosTO.getPagFecha());
                    conContableTO.setConPendiente(false);
                    conContableTO.setConBloqueado(false);
                    conContableTO.setConAnulado(false);
                    conContableTO.setConGenerado(true);
                    conContableTO.setConConcepto(carPagosTO.getConApellidosNombres());// nombre
                    // empleado
                    conContableTO.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");//
                    conContableTO.setConObservaciones(carPagosTO.getPagObservaciones());
                    conContableTO.setUsrInsertaContable(carPagosTO.getUsrCodigo());
                    conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                    ///// CREANDO Suceso
                    susSuceso = "INSERT";
                    susTabla = "cartera.car_pagos";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);
                    ////// CREANDO NUMERACION
                    ConNumeracion conNumeracion = new ConNumeracion(new ConNumeracionPK(carPagosTO.getUsrEmpresa(),
                            carPagosTO.getPagPeriodo(), carPagosTO.getPagTipo()));
                    ////// CREANDO CONTABLE
                    ConContable conContable = ConversionesContabilidad
                            .convertirConContableTO_ConContable(conContableTO);
                    conContable.setConReferencia("cartera.car_pagos");
                    ////// CREANDO CarPagos
                    carPagosTO.setPagTipo(tipDetalle);
                    carPagosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                    CarPagos carPagos = ConversionesCar.convertirCarPagosTO_CarPagos(carPagosTO);
                    ////// CREANDO Lista de CarPagosDetalleCompras
                    List<CarPagosDetalleCompras> carPagosDetalleCompras = new ArrayList<CarPagosDetalleCompras>(0);
                    for (CarPagosDetalleComprasTO carPagosDetalleComprasTO : carPagosDetalleComprasTOs) {
                        carPagosDetalleComprasTO.setDetSecuencial(0);
                        carPagosDetalleCompras
                                .add(ConversionesCar.convertirCarPagosDetalleComprasTO_CarPagosDetalleCompras(
                                        carPagosDetalleComprasTO));
                    }
                    ////// CREANDO Lista de CarPagosDetalleForma
                    List<CarPagosDetalleForma> carPagosDetalleForma = new ArrayList<CarPagosDetalleForma>(0);
                    for (CarPagosDetalleFormaTO carPagosDetalleFormaTO : carPagosDetalleFormaTOs) {
                        carPagosDetalleForma.add(ConversionesCar
                                .convertirCarPagosDetalleFormaTO_CarPagosDetalleForma(carPagosDetalleFormaTO));
                    }
                    ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                    List<ConDetalle> listaConDetalle = new ArrayList<>();
                    ////// CREANDO Lista de CarPagosDetalleAnticipos
                    List<CarPagosDetalleAnticipos> carPagosDetalleAnticipos = new ArrayList<>(0);
                    for (CarPagosDetalleAnticiposTO carPagosDetalleAnticiposTO : carPagosDetalleAnticiposTOs) {
                        carPagosDetalleAnticipos
                                .add(ConversionesCar.convertirCarPagosDetalleAnticiposTO_CarPagosDetalleAnticipos(
                                        carPagosDetalleAnticiposTO));
                    }

                    comprobar = false;
                    if (mensaje.isEmpty()) {// revisar si estan llenos
                        comprobar = contableDao.insertarTransaccionContable(conContable, listaConDetalle, sisSuceso,
                                conNumeracion, null, null, carPagos, carPagosDetalleAnticipos,
                                carPagosDetalleCompras, carPagosDetalleForma, null, null, null, null, null, null,
                                null, null, null, sisInfoTO);
                        contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                    }
                    if (comprobar) {
                        SisPeriodo sisPeriodo = periodoService.buscarPeriodo(conContableTO.getEmpCodigo(),
                                conContable.getConContablePK().getConPeriodo());
                        ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(
                                conContableTO.getEmpCodigo(), conContable.getConContablePK().getConTipo()));
                        mensaje = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                                + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                + "</font>.<br> Número: <font size = 5>"
                                + conContable.getConContablePK().getConNumero() + "</font>.</html>"
                                + carPagos.getCarPagosPK().getPagNumero();
                        carContableTO.setContPeriodo(sisPeriodo.getSisPeriodoPK().getPerCodigo());
                        carContableTO.setContTipo(conTipo.getConTipoPK().getTipCodigo());
                        carContableTO.setContNumero(conContable.getConContablePK().getConNumero());
                        contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                    }
                } else {
                    mensaje = "FLos siguientes Documentos han tenido problemas:";
                    carContableTO.setListaFacturaTO(listaFacturaTO);
                }
            } else {
                mensaje = "FLos siguientes Documentos se esta pagando mas de lo que se debe:";
                carContableTO.setListaFacturaTO(listaDetalle);
            }
        } else {
            mensaje = "FNo se encuentra ningún periodo para la fecha que ingresó\nNo esta creado o esta cerrado...";
        }
        carContableTO.setMensaje(mensaje);
        return carContableTO;
    }

    @Override
    public CarListaPagosProveedorTO getPagosConsultaProveedorTO(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        return pagosDao.getPagosConsultaProveedor(empresa, periodo, tipo, numero);
    }

    @Override
    public List<CarListaPagosTO> getPagosConsultaDetalleComprasTO(String empresa, String periodo, String numero)
            throws Exception {
        return pagosDao.getPagosConsultaDetalleCompras(empresa, periodo, numero);
    }

    @Override
    public List<CarListaPagosCobrosDetalleFormaTO> getPagosConsultaDetalleFormaTO(String empresa, String periodo,
            String numero) throws Exception {
        return pagosDao.getPagosConsultaDetalleForma(empresa, periodo, numero);
    }

    @Override
    public List<CarListaPagosTO> getCarListaPagosTO(String empresa, String proveedor) throws Exception {
        return pagosDao.getCarListaPagosTO(empresa, proveedor);
    }

    @Override
    public List<CarFunPagosTO> getCarFunPagosTO(String empresa, String sector, String desde, String hasta,
            String proveedor, boolean incluirTodos) throws Exception {
        return pagosDao.getCarFunPagosTO(empresa, sector, desde, hasta, proveedor, incluirTodos);
    }

    @Override
    public List<CarFunPagosPruebaTO> getCarFunPagosPruebaTO(String empresa, String desde, String hasta,
            String proveedor) throws Exception {
        return pagosDao.getCarFunPagosPruebaTO(empresa, desde, hasta, proveedor);
    }

    @Override
    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTO(String empresa, String sector, String desde, String hasta,
            String proveedor, String formaPago) throws Exception {
        return pagosDao.getCarFunPagosDetalleTO(empresa, sector, desde, hasta, proveedor, formaPago);
    }

    @Override
    public List<CuentasPorPagarDetalladoTO> getCarListaCuentasPorPagarDetalladoTO(String empresa, String sector,
            String proveedor, String hasta, boolean excluirAprobadas, boolean incluirCheques, String formatoMensual) throws Exception {
        return pagosDao.getCarListaCuentasPorPagarDetalladoTO(empresa, sector, proveedor, hasta, excluirAprobadas, incluirCheques, formatoMensual);
    }

    @Override
    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorPagarGeneralTO(String empresa, String sector,
            String hasta) throws Exception {
        return pagosDao.getCarListaCuentasPorPagarGeneralTO(empresa, sector, hasta);
    }

    @Override
    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorPagarAnticiposTO(String empresa,
            String sector, String hasta) throws Exception {
        return pagosDao.getCarListaCuentasPorPagarAnticiposTO(empresa, sector, hasta);
    }

    @Override
    public List<CarFunCuentasPorPagarListadoComprasTO> getCarFunCuentasPorPagarListadoComprasTO(String empresa,
            String sector, String proveedor, String desde, String hasta) throws Exception {
        return pagosDao.getCarFunCuentasPorPagarListadoComprasTO(empresa, sector, proveedor, desde, hasta);
    }

    @Override
    public List<CarFunCuentasPagadasListadoTO> listarCuentasPagadas(String empresa, String sector, String proveedor, String desde, String hasta, boolean aprobadas) throws Exception {
        return pagosDao.listarCuentasPagadas(empresa, sector, proveedor, desde, hasta, aprobadas);
    }

    @Override
    public List<CarListaPagosCobrosDetalleAnticipoTO> getPagosConsultaDetalleAnticipo(String empresa, String periodo,
            String numero) throws Exception {
        return pagosDao.getPagosConsultaDetalleAnticipo(empresa, periodo, numero);
    }

    @Override
    public Map<String, Object> obtenerDatosParaPagos(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//P:Pagos; C:Cobros

        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        List<CarFunPagosSaldoAnticipoTO> listaAnticipo = pagosAnticiposService.getCarFunPagosSaldoAnticipoTO(empresa, proveedor);
        List<CarListaPagosTO> listaPagos = getCarListaPagosTO(empresa, proveedor);
        List<CarComboPagosCobrosFormaTO> listaForma = pagosFormaDao.getCarComboPagosCobrosForma(empresa, accion);

        campos.put("fechaActual", fechaActual);
        campos.put("listaAnticipo", listaAnticipo);
        campos.put("listaPagos", listaPagos);
        campos.put("listaForma", listaForma);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerPago(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));

        CarListaPagosProveedorTO pago = getPagosConsultaProveedorTO(empresa, periodo, tipo, numero);
        if (pago != null) {
            List<CarListaPagosTO> listaPagos = getPagosConsultaDetalleComprasTO(empresa, periodo, numero);
            List<CarListaPagosCobrosDetalleFormaTO> listadoDeFormasDePago = getPagosConsultaDetalleFormaTO(empresa, periodo, numero);
            List<CarListaPagosCobrosDetalleAnticipoTO> pagosAnticipos = getPagosConsultaDetalleAnticipo(empresa, periodo, numero);
            List<CarFunPagosSaldoAnticipoTO> listadoPagosAnticipos = new ArrayList<>();
            ConContablePK contablePK = new ConContablePK(empresa, periodo, tipo, numero);
            ConContable contable = contableDao.getConContable(contablePK);
            if (pagosAnticipos != null) {
                for (CarListaPagosCobrosDetalleAnticipoTO pagosAnticipo : pagosAnticipos) {
                    CarFunPagosSaldoAnticipoTO pagoAnticipo = ConversionesCar.convertirCarListaPagosCobrosDetalleAnticipoTO_CarFunPagosSaldoAnticipoTO(pagosAnticipo);
                    listadoPagosAnticipos.add(pagoAnticipo);
                }
            }
            campos.put("pago", pago);
            campos.put("listaPagos", listaPagos);
            campos.put("listadoDeFormasDePago", listadoDeFormasDePago);
            campos.put("listadoPagosAnticipos", listadoPagosAnticipos);
            campos.put("contable", contable);
        } else {
            throw new GeneralException("No se encuentra cobro.");
        }
        return campos;
    }

    @Override
    public List<RespuestaWebTO> aprobarPagos(List<CuentasPorPagarDetalladoTO> listado, SisInfoTO sisInfoTO) throws Exception {
        List<RespuestaWebTO> mensajes = new ArrayList<>();
        for (CuentasPorPagarDetalladoTO cuentaPorPagar : listado) {
            RespuestaWebTO respuesta = new RespuestaWebTO();
            if (cuentaPorPagar.getCxpdMotivo() != null && cuentaPorPagar.getCxpdNumero() != null && cuentaPorPagar.getCxpdNumero() != null) {
                susClave = sisInfoTO.getEmpresa() + " | " + cuentaPorPagar.getCxpdPeriodo() + " | " + cuentaPorPagar.getCxpdMotivo() + " | " + cuentaPorPagar.getCxpdNumero();
                String mensajeRespuesta;
                cuentaPorPagar.setCxpdUsuarioApruebaPago(null);
                try {
                    mensajeRespuesta = pagosDao.aprobarPago(cuentaPorPagar, sisInfoTO);
                    if (mensajeRespuesta != null && mensajeRespuesta.charAt(0) == 'T') {
                        cuentaPorPagar.setCxpdUsuarioApruebaPago(sisInfoTO.getUsuario());
                    }
                } catch (Exception e) {
                    e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                    mensajeRespuesta = "FOcurrió un error al aprobar pago para la compra " + susClave + " a causa de : " + e.getMessage();
                }
                respuesta.setOperacionMensaje(mensajeRespuesta);
                respuesta.setExtraInfo(cuentaPorPagar);
                mensajes.add(respuesta);
            }
        }
        return mensajes;
    }

    @Override
    public List<String> validarPagosAprobados(List<CarPagosDetalleComprasTO> carPagosDetalleComprasTOs) throws Exception {
        List<String> documentosNoAprobados = new ArrayList<>();
        InvCompras invComprasBusqueda = null;
        InvCompras invCompras = null;
        for (CarPagosDetalleComprasTO carPagosDetalleComprasTO : carPagosDetalleComprasTOs) {
            invComprasBusqueda = comprasDao.buscarInvCompras(carPagosDetalleComprasTO.getCompEmpresa(), carPagosDetalleComprasTO.getCompPeriodo(), carPagosDetalleComprasTO.getCompMotivo(), carPagosDetalleComprasTO.getCompNumero());
            invCompras = invComprasBusqueda != null ? ConversionesInventario.convertirInvCompras_InvCompras(invComprasBusqueda) : null;
            if (invCompras != null) {
                if (invCompras.getCompUsuarioApruebaPago() == null || invCompras.getCompUsuarioApruebaPago().equals("")) {
                    documentosNoAprobados.add("Documento: " + carPagosDetalleComprasTO.getCompDocumento());
                }
            }
        }
        return documentosNoAprobados;
    }

    @Override
    public List<CarCuentasPorPagarDetalladoGranjasMarinasTO> getCarCuentasPorPagarDetalladoGranjasMarinasTO(String fecha) throws Exception {
        return pagosDao.getCarCuentasPorPagarDetalladoGranjasMarinasTO(fecha);
    }

    @Override
    public CarContableTO mayorizarCarPagos(CarPagosTO carPagosTO,
            List<CarPagosDetalleComprasTO> carPagosDetalleComprasTOs,
            List<CarPagosDetalleAnticiposTO> carPagosDetalleAnticiposTOs,
            List<CarPagosDetalleFormaTO> carPagosDetalleFormaTOs, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        mensaje = "";
        String tipDetalle = "C-PAG";
        CarContableTO carContableTO = new CarContableTO();
        List<String> listaDetalle = new ArrayList<>(1);
        List<String> listaFacturaTO = new ArrayList<>(1);
        List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(carPagosTO.getUsrEmpresa());

        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(carPagosTO.getPagFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(carPagosTO.getPagFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()
                    && sisListaPeriodoTO.getPerCerrado() == false) {
                comprobar = true;
                carPagosTO.setPagPeriodo(sisListaPeriodoTO.getPerCodigo());
                break;
            }
        }
        if (comprobar) {
            carPagosTO.setPagCodigoTransaccional(null);
            List<CarListaPagosTO> carListaPagosCobrosTOs = pagosDao.getCarListaPagosTO(carPagosTO.getUsrEmpresa(), carPagosTO.getProvCodigo());
            boolean documento;
            for (int k = 0; k < carPagosDetalleComprasTOs.size(); k++) {
                documento = false;
                for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                    if (carPagosDetalleComprasTOs.get(k).getCompPeriodo().equals(carListaPagosCobrosTOs.get(i).getCxppPeriodo())
                            && carPagosDetalleComprasTOs.get(k).getCompMotivo().equals(carListaPagosCobrosTOs.get(i).getCxppMotivo())
                            && carPagosDetalleComprasTOs.get(k).getCompNumero().equals(carListaPagosCobrosTOs.get(i).getCxppNumero())) {
                        documento = true;
                        if ((carPagosDetalleComprasTOs.get(k).getDetValor().abs().compareTo(carListaPagosCobrosTOs.get(i).getCxppSaldo().abs()) > 0)) {
                            listaDetalle.add(carPagosDetalleComprasTOs.get(k).getCompDocumento() + "     "
                                    + carPagosDetalleComprasTOs.get(k).getCompPeriodo() + " | "
                                    + carPagosDetalleComprasTOs.get(k).getCompMotivo() + " | "
                                    + carPagosDetalleComprasTOs.get(k).getCompNumero());
                        }
                    } else if (i == carListaPagosCobrosTOs.size() - 1 && !documento) {
                        listaDetalle.add(carPagosDetalleComprasTOs.get(k).getCompDocumento() + "     "
                                + carPagosDetalleComprasTOs.get(k).getCompPeriodo() + " | "
                                + carPagosDetalleComprasTOs.get(k).getCompMotivo() + " | "
                                + carPagosDetalleComprasTOs.get(k).getCompNumero());
                    }
                }
            }
            if (listaDetalle.isEmpty()) {
                for (CarPagosDetalleComprasTO carPagosDetalleComprasTO : carPagosDetalleComprasTOs) {
                    InvCompras invComprasBusqueda = comprasDao.buscarInvCompras(carPagosDetalleComprasTO.getCompEmpresa(),
                            carPagosDetalleComprasTO.getCompPeriodo(), carPagosDetalleComprasTO.getCompMotivo(),
                            carPagosDetalleComprasTO.getCompNumero());
                    InvCompras invCompras = ConversionesInventario.convertirInvCompras_InvCompras(invComprasBusqueda);
                    if (invCompras == null) {
                        listaFacturaTO.add(carPagosDetalleComprasTO.getCompDocumento() + " (no encontrada)");
                    } else {
                        if (invCompras.getCompAnulado()) {
                            listaFacturaTO.add(carPagosDetalleComprasTO.getCompDocumento() + " (anulada)");
                        }
                        if (invCompras.getCompPendiente()) {
                            listaFacturaTO.add(carPagosDetalleComprasTO.getCompDocumento() + " (pendiente)");
                        }
                    }
                    if (invCompras != null && !invCompras.getCompAnulado() && !invCompras.getCompPendiente()) {
                        invCompras.setCompSaldo(invCompras.getCompSaldo().subtract(carPagosDetalleComprasTO.getDetValor()));
                    }
                }
                //// NO HAY ERRORES EN FACTURA
                if (listaFacturaTO.isEmpty()) {
                    // llenar contable
                    ConContableTO conContableTO = new ConContableTO();
                    conContableTO.setEmpCodigo(carPagosTO.getUsrEmpresa());
                    conContableTO.setPerCodigo(carPagosTO.getPagPeriodo());
                    conContableTO.setConNumero(carPagosTO.getPagNumero());
                    conContableTO.setTipCodigo(tipDetalle);
                    carPagosTO.setPagTipo(tipDetalle);
                    conContableTO.setConFecha(carPagosTO.getPagFecha());
                    conContableTO.setConPendiente(false);
                    conContableTO.setConBloqueado(false);
                    conContableTO.setConAnulado(false);
                    conContableTO.setConGenerado(true);
                    conContableTO.setConConcepto(carPagosTO.getConApellidosNombres());// nombre
                    // empleado
                    conContableTO.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");//
                    conContableTO.setConObservaciones(carPagosTO.getPagObservaciones());
                    conContableTO.setUsrInsertaContable(carPagosTO.getUsrCodigo());
                    conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                    ///// CREANDO Suceso
                    susSuceso = "UPDATE";
                    susTabla = "cartera.car_pagos";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    ////// CREANDO CONTABLE
                    ConContable conContable = ConversionesContabilidad.convertirConContableTO_ConContable(conContableTO);
                    conContable.setConReferencia("cartera.car_pagos");
                    ////// CREANDO CarPagos
                    carPagosTO.setPagTipo(tipDetalle);
                    carPagosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                    CarPagos carPagos = ConversionesCar.convertirCarPagosTO_CarPagos(carPagosTO);
                    ////// CREANDO Lista de CarPagosDetalleCompras
                    List<CarPagosDetalleCompras> carPagosDetalleComprasEnBaseDeDatos = pagosDetalleComprasDao.listarDetallesComprasPorPago(carPagosTO.getUsrEmpresa(), carPagosTO.getPagPeriodo(), tipDetalle, carPagosTO.getPagNumero());
                    if (carPagosDetalleComprasEnBaseDeDatos != null && !carPagosDetalleComprasEnBaseDeDatos.isEmpty()) {
                        for (int i = 0; i < carPagosDetalleComprasEnBaseDeDatos.size(); i++) {
                            pagosDetalleComprasDao.eliminar(carPagosDetalleComprasEnBaseDeDatos.get(i));
                        }
                    }
                    List<CarPagosDetalleCompras> carPagosDetalleCompras = new ArrayList<>(0);
                    for (CarPagosDetalleComprasTO carPagosDetalleComprasTO : carPagosDetalleComprasTOs) {
                        carPagosDetalleComprasTO.setDetSecuencial(0);
                        carPagosDetalleCompras.add(ConversionesCar.convertirCarPagosDetalleComprasTO_CarPagosDetalleCompras(carPagosDetalleComprasTO));
                    }
                    ////// CREANDO Lista de CarPagosDetalleForma
                    List<CarPagosDetalleForma> carPagosDetalleFormasEnBaseDeDatos = pagosDetalleFormaDao.listarDetallesFormaPorPago(carPagosTO.getUsrEmpresa(), carPagosTO.getPagPeriodo(), tipDetalle, carPagosTO.getPagNumero());
                    if (carPagosDetalleFormasEnBaseDeDatos != null && !carPagosDetalleFormasEnBaseDeDatos.isEmpty()) {
                        for (int i = 0; i < carPagosDetalleFormasEnBaseDeDatos.size(); i++) {
                            pagosDetalleFormaDao.eliminar(carPagosDetalleFormasEnBaseDeDatos.get(i));
                        }
                    }
                    List<CarPagosDetalleForma> carPagosDetalleForma = new ArrayList<>(0);
                    for (CarPagosDetalleFormaTO carPagosDetalleFormaTO : carPagosDetalleFormaTOs) {
                        carPagosDetalleForma.add(ConversionesCar.convertirCarPagosDetalleFormaTO_CarPagosDetalleForma(carPagosDetalleFormaTO));
                    }
                    ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                    List<ConDetalle> listaConDetalle = new ArrayList<>();
                    ////// CREANDO Lista de CarPagosDetalleAnticipos
                    List<CarPagosDetalleAnticipos> carPagosDetalleAnticiposEnBaseDeDatos = pagosDetalleAnticiposDao.listarDetallesAnticipoPorPago(carPagosTO.getUsrEmpresa(), carPagosTO.getPagPeriodo(), tipDetalle, carPagosTO.getPagNumero());
                    if (carPagosDetalleAnticiposEnBaseDeDatos != null && !carPagosDetalleAnticiposEnBaseDeDatos.isEmpty()) {
                        for (int i = 0; i < carPagosDetalleAnticiposEnBaseDeDatos.size(); i++) {
                            pagosDetalleAnticiposDao.eliminar(carPagosDetalleAnticiposEnBaseDeDatos.get(i));
                        }
                    }
                    List<CarPagosDetalleAnticipos> carPagosDetalleAnticipos = new ArrayList<>(0);
                    for (CarPagosDetalleAnticiposTO carPagosDetalleAnticiposTO : carPagosDetalleAnticiposTOs) {
                        carPagosDetalleAnticipos.add(ConversionesCar.convertirCarPagosDetalleAnticiposTO_CarPagosDetalleAnticipos(carPagosDetalleAnticiposTO));
                    }
                    comprobar = false;
                    if (mensaje.isEmpty()) {// revisar si estan llenos
                        comprobar = contableDao.mayorizarTransaccionContableCartera(conContable, listaConDetalle, sisSuceso,
                                carPagos, carPagosDetalleAnticipos,
                                carPagosDetalleCompras, carPagosDetalleForma, null, null, null, null, null, null, sisInfoTO);
                    }
                    if (comprobar) {
                        SisPeriodo sisPeriodo = periodoService.buscarPeriodo(conContableTO.getEmpCodigo(),
                                conContable.getConContablePK().getConPeriodo());
                        ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(
                                conContableTO.getEmpCodigo(), conContable.getConContablePK().getConTipo()));
                        mensaje = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                                + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                + "</font>.<br> Número: <font size = 5>"
                                + conContable.getConContablePK().getConNumero() + "</font>.</html>"
                                + carPagos.getCarPagosPK().getPagNumero();
                        carContableTO.setContPeriodo(sisPeriodo.getSisPeriodoPK().getPerCodigo());
                        carContableTO.setContTipo(conTipo.getConTipoPK().getTipCodigo());
                        carContableTO.setContNumero(conContable.getConContablePK().getConNumero());
                        contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                    }
                } else {
                    mensaje = "FLos siguientes Documentos han tenido problemas:";
                    carContableTO.setListaFacturaTO(listaFacturaTO);
                }
            } else {
                mensaje = "FLos siguientes Documentos se esta pagando mas de lo que se debe:";
                carContableTO.setListaFacturaTO(listaDetalle);
            }
        } else {
            mensaje = "FNo se encuentra ningún periodo para la fecha que ingresó\nNo esta creado o esta cerrado...";
        }
        carContableTO.setMensaje(mensaje);
        return carContableTO;
    }

    @Override
    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTOAgrupadoProveedor(String empresa, String sector, String desde, String hasta, String proveedor, String formaPago) throws Exception {
        return pagosDao.getCarFunPagosDetalleTOAgrupadoProveedor(empresa, sector, desde, hasta, proveedor, formaPago);
    }

    @Override
    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTOAgrupadoCP(String empresa, String sector, String desde, String hasta, String proveedor, String formaPago) throws Exception {
        return pagosDao.getCarFunPagosDetalleTOAgrupadoCP(empresa, sector, desde, hasta, proveedor, formaPago);
    }

}
