package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CuentasContablesDao;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosFormaDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProveedorDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCar;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosForma;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.RhPreavisoAnticiposPrestamosSueldoMachalaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhOrdenBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoGuayaquilTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoPichinchaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class PagosAnticiposServiceImpl implements PagosAnticiposService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private PagosFormaDao pagosFormaDao;
    @Autowired
    private PagosAnticiposDao pagosAnticiposDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private CuentasContablesDao cuentasContablesDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private ContableService contableService;
    @Autowired
    private BancoDao bancoDao;
    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public CarListaPagosProveedorTO getPagosConsultaProveedorAnticipoTO(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        return pagosAnticiposDao.getPagosConsultaProveedorAnticipo(empresa, periodo, tipo, numero);
    }

    @Override
    public List<CarFunPagosSaldoAnticipoTO> getCarFunPagosSaldoAnticipoTO(String empresa, String proveedor)
            throws Exception {
        return pagosAnticiposDao.getCarFunPagosSaldoAnticipoTO(empresa, proveedor);
    }

    @Override
    public CarPagosCobrosAnticipoTO getCarPagosCobrosAnticipoTO(String empresa, String periodo, String tipo,
            String numero, char accion) throws Exception {
        return pagosAnticiposDao.getCarPagosCobrosAnticipoTO(empresa, periodo, tipo, numero, accion);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTO(String empresa,
            String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception {
        return pagosAnticiposDao.getCarListaCuentasPorPagarSaldoAnticiposTO(empresa, sector, proveedorCodigo, hasta, incluirTodos);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistorico(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        return pagosAnticiposDao.getCarListadoAnticipoProveedorHistorico(empresa, sector, proveedorCodigo, desde, hasta, incluirTodos);
    }

    @Override
    public MensajeTO insertarAnticiposPago(CarPagosAnticipoTO carPagosAnticipoTO, String observaciones,
            String nombreProveedor, String fecha, String sectorProveedor, String documento, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO)
            throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        comprobar = false;
        Boolean periodoCerrado = false;
        List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(carPagosAnticipoTO.getAntEmpresa());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                carPagosAnticipoTO.setAntPeriodo(sisListaPeriodoTO.getPerCodigo());
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (comprobar) {
            if (!periodoCerrado) {
                if (sectorDao.obtenerPorId(PrdSector.class,
                        new PrdSectorPK(carPagosAnticipoTO.getAntEmpresa(), sectorProveedor)) != null) {
                    // CREA EL SUSCESO EL DETALLE SE COMPLETA
                    // insertarTransaccionContable
                    susSuceso = "INSERT";
                    susTabla = "cartera.car_pagos_anticipos";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);
                    carPagosAnticipoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

                    if (tipoDao.buscarTipoContable(carPagosAnticipoTO.getAntEmpresa(),
                            carPagosAnticipoTO.getAntTipo())) {
                        InvProveedor invProveedor = proveedorDao.buscarInvProveedor(
                                carPagosAnticipoTO.getProvEmpresa(), carPagosAnticipoTO.getProvCodigo());
                        if (invProveedor != null) {
                            CarPagosForma carPagosForma = pagosFormaDao.obtenerPorId(CarPagosForma.class,
                                    carPagosAnticipoTO.getFpSecuencial());
                            if (carPagosForma != null) {
                                ConCuentas conCuentaFormaPago = cuentasDao.buscarCuentas(
                                        carPagosForma.getCtaEmpresa(), carPagosForma.getCtaCodigo());
                                ConCuentas conCuentaAnticiposProveedores = cuentasDao
                                        .obtenerPorId(ConCuentas.class,
                                                new ConCuentasPK(invProveedor.getInvProveedorPK().getProvEmpresa(),
                                                        cuentasContablesDao
                                                                .getCuentasContablesTO(invProveedor
                                                                        .getInvProveedorPK().getProvEmpresa())
                                                                .getCtaAnticiposAProveedores()));// invProveedor.getInvProveedorCategoria().getCtaAntipos()
                                if (conCuentaFormaPago != null) {
                                    if (conCuentaAnticiposProveedores != null) {
                                        ConContable conContable = new ConContable();
                                        conContable.setConContablePK(new ConContablePK(
                                                carPagosAnticipoTO.getAntEmpresa(),
                                                carPagosAnticipoTO.getAntPeriodo(), carPagosAnticipoTO.getAntTipo(),
                                                carPagosAnticipoTO.getAntNumero()));
                                        conContable.setConAnulado(false);
                                        conContable.setConBloqueado(false);
                                        conContable.setConConcepto(nombreProveedor);
                                        conContable.setConReferencia("cartera.car_pagos_anticipos");
                                        conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                                        conContable.setConObservaciones(observaciones);
                                        conContable.setConFecha(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                        conContable.setConGenerado(true);
                                        conContable.setConPendiente(false);
                                        conContable.setConReversado(false);
                                        conContable.setUsrCodigo(carPagosAnticipoTO.getUsrCodigo());
                                        conContable.setUsrEmpresa(carPagosAnticipoTO.getUsrEmpresa());
                                        conContable.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(
                                                carPagosAnticipoTO.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss")));

                                        ConDetalle conDetalle = new ConDetalle();
                                        List<ConDetalle> listaConDetalle = new ArrayList<>();

                                        conDetalle.setConContable(conContable);
                                        conDetalle.setConCuentas(conCuentaAnticiposProveedores);
                                        conDetalle.setDetSecuencia(0L);
                                        conDetalle.setPrdSector(
                                                new PrdSector(carPagosAnticipoTO.getAntEmpresa(), sectorProveedor));
                                        conDetalle.setPrdPiscina(null);
                                        conDetalle.setDetDocumento("");
                                        conDetalle.setDetDebitoCredito('D');
                                        conDetalle.setDetValor(carPagosAnticipoTO.getAntValor());
                                        conDetalle.setDetGenerado(true);
                                        conDetalle.setDetReferencia("ANT-PRO");
                                        conDetalle.setDetObservaciones("");
                                        conDetalle.setDetOrden(1);
                                        listaConDetalle.add(conDetalle);

                                        conDetalle = new ConDetalle();
                                        conDetalle.setConContable(conContable);
                                        conDetalle.setConCuentas(conCuentaFormaPago);
                                        conDetalle.setDetSecuencia(0L);
                                        conDetalle.setPrdSector(new PrdSector(carPagosAnticipoTO.getAntEmpresa(),
                                                carPagosForma.getSecCodigo()));
                                        conDetalle.setPrdPiscina(null);
                                        conDetalle.setDetDocumento(documento);
                                        conDetalle.setDetDebitoCredito('C');
                                        conDetalle.setDetValor(carPagosAnticipoTO.getAntValor());
                                        conDetalle.setDetGenerado(true);
                                        conDetalle.setDetReferencia("FP");
                                        conDetalle.setDetObservaciones("");
                                        conDetalle.setDetOrden(2);
                                        listaConDetalle.add(conDetalle);
                                        ConNumeracion conNumeracion = new ConNumeracion(
                                                new ConNumeracionPK(carPagosAnticipoTO.getAntEmpresa(),
                                                        carPagosAnticipoTO.getAntPeriodo(),
                                                        carPagosAnticipoTO.getAntTipo()));
                                        carPagosAnticipoTO.setAntPagado(false);
                                        carPagosAnticipoTO.setSecEmpresa(carPagosAnticipoTO.getAntEmpresa());
                                        carPagosAnticipoTO.setSecCodigo(sectorProveedor);
                                        CarPagosAnticipos carPagosAnticipos = ConversionesCar
                                                .convertirCarPagosAnticiposTO_CarPagosAnticipos(carPagosAnticipoTO);
                                        carPagosAnticipos.setFpSecuencial(carPagosForma);

                                        comprobar = contableDao.insertarTransaccionContable(conContable,
                                                listaConDetalle, sisSuceso, conNumeracion, null, null, null, null,
                                                null, null, null, null, null, null, null, null, carPagosAnticipos,
                                                null, null, sisInfoTO);
                                        contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                                        if (comprobar) {

                                            SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                                    carPagosAnticipoTO.getAntEmpresa(),
                                                    conContable.getConContablePK().getConPeriodo());

                                            ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class,
                                                    new ConTipoPK(carPagosAnticipoTO.getAntEmpresa(),
                                                            conContable.getConContablePK().getConTipo()));

                                            mensaje = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                                                    + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                                    + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                                    + "</font>.<br> Número: <font size = 5>"
                                                    + conContable.getConContablePK().getConNumero()
                                                    + "</font>.</html>"
                                                    + conContable.getConContablePK().getConPeriodo() + ", "
                                                    + conContable.getConContablePK().getConNumero();
                                            mensajeTO.getMap().put("conContable", conContable);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "elsexc ");
                                            mensaje = "F<html>Hubo un error al guardar el Anticipo de Pago.\nCódigo repetido: "
                                                    + carPagosAnticipoTO.getAntNumero()
                                                    + "\nIntentelo de nuevo...</html>";
                                        }
                                    } else {
                                        mensaje = "F<html>La CUENTA CONTABLE anticipo del PROVEEDOR ya\nno se encuentra disponible "
                                                + "o no ha sido ingresada.\n</html>";
                                    }
                                } else {
                                    mensaje = "F<html>La CUENTA CONTABLE de la FORMA DE PAGO ya\nno se encuentra disponible o no ha sido ingresada.\nCódigo contable: "
                                            + carPagosForma.getCtaCodigo() + "</html>";
                                }
                            } else {
                                mensaje = "F<html>La FORMA DE PAGO que ingresó ya no se encuentra disponible.</html>";
                            }
                        } else {
                            mensaje = "F<html>El PROVEEDOR que ingresó ya no se encuentra disponible.</html>";
                        }
                    } else {
                        mensaje = "F<html>El TIPO DE CONTABLE C-ACLI no ha sido creado aún.\nContacte con el administrador.</html>";
                    }
                } else {
                    mensaje = "F<html>El CP " + sectorProveedor
                            + "ya no esta disponible.\nContacte con el administrador.</html>";
                }

            } else {
                mensaje = "F<html>El periodo de la fecha que ingresó está cerrado.</html>";
            }
        } else {
            mensaje = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
        }
        mensajeTO.setMensaje(mensaje);
        return mensajeTO;
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudAnticipos(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//P:Pagos; C:Cobros

        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        List<SisPeriodo> periodos = periodoService.getListaPeriodo(empresa);
        List<PrdListaSectorTO> sectores = sectorDao.getListaSector(empresa, false);
        List<CarComboPagosCobrosFormaTO> listaForma = pagosFormaDao.getCarComboPagosCobrosForma(empresa, accion);
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        List<ListaBanBancoTO> listadoBancos = bancoDao.getListaBanBancoTO(empresa);

        campos.put("isPeriodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("fechaActual", fechaActual);
        campos.put("periodos", periodos);
        campos.put("sectores", sectores);
        campos.put("listaForma", listaForma);
        campos.put("listadoBancos", listadoBancos);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerAnticipoPago(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));

        CarPagosAnticipos carPagosAnticipos = pagosAnticiposDao.obtenerCarPagosAnticipo(empresa, periodo, tipo, numero);
        if (carPagosAnticipos != null) {
            CarPagosForma fpSeleccionada = carPagosAnticipos.getFpSecuencial();
            CarComboPagosCobrosFormaTO fp = fpSeleccionada != null ? ConversionesCar.convertirCarPagosForma_CarComboPagosCobrosFormaTO(fpSeleccionada) : null;
            CarPagosAnticipoTO carPagosAnticiposTO = ConversionesCar.convertirCarPagosAnticipos_CarPagosAnticiposTO(carPagosAnticipos);
            InvProveedor proveedor = proveedorDao.buscarInvProveedor(carPagosAnticipos.getProvEmpresa(), carPagosAnticipos.getProvCodigo());
            if (proveedor != null) {
                String nombre = proveedor.getProvRazonSocial();
                String direccion = proveedor.getProvDireccion();
                String codigo = proveedor.getInvProveedorPK().getProvCodigo();
                campos.put("nombre", nombre);
                campos.put("codigo", codigo);
                campos.put("direccion", direccion);
            }
            ConContablePK contablePK = new ConContablePK(empresa, periodo, tipo, numero);
            ConContable contable = contableDao.getConContable(contablePK);

            String documento = "";
            if (contable != null && contable.getConDetalleList() != null && !contable.getConDetalleList().isEmpty()) {
                for (ConDetalle detalle : contable.getConDetalleList()) {
                    if (detalle.getDetDebitoCredito() == 'C') {
                        documento = detalle.getDetDocumento();
                        break;
                    }
                }
            }

            campos.put("fpSeleccionada", fp);
            campos.put("documento", documento);
            campos.put("carPagosAnticiposTO", carPagosAnticiposTO);
            campos.put("contable", contable);
        } else {
            throw new GeneralException("No se encuentra anticipo a proveedor");
        }

        return campos;
    }

    @Override
    public RespuestaWebTO generarOrdenBancariaCartera(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        //Banco Bolivariano
        if (ordenBancaria.getNombreCuenta().toUpperCase().contains("BOLIVARIANO")) {
            ordenBancaria.setBanco("BOLIVARIANO");
            List<RhPreavisoAnticiposPrestamosSueldoTO> listaBolivariano = pagosAnticiposDao.listaOrdenesAnticipoBancoBolivariano(ordenBancaria, sector, usuario);
            this.tratamientoDeLaLista(listaBolivariano);
            if (listaBolivariano != null && listaBolivariano.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaBolivariano);
            }
        }
        //Banco Internacional
        if (ordenBancaria.getNombreCuenta().toUpperCase().contains("INTERNACIONAL")) {
            ordenBancaria.setBanco("INTERNACIONAL");
            List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichinchaInternacional = pagosAnticiposDao.listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
            this.tratamientoDeLaLista(listaPichinchaInternacional);
            if (listaPichinchaInternacional != null && listaPichinchaInternacional.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaPichinchaInternacional);
            }
        }
        //Banco Pichincha 
        if (ordenBancaria.getNombreCuenta().toUpperCase().contains("PICHINCHA")) {
            ordenBancaria.setBanco("PICHINCHA");
            List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichincha = pagosAnticiposDao.listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
            this.tratamientoDeLaLista(listaPichincha);
            if (listaPichincha != null && listaPichincha.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaPichincha);
            }
        }
        if (ordenBancaria.getNombreCuenta().toUpperCase().contains("PRODUCCION")) {
            ordenBancaria.setBanco("PRODUCCION"); //Temporal - NUEVO METODO
            List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaProduccion = pagosAnticiposDao.listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
            this.tratamientoDeLaLista(listaProduccion);
            if (listaProduccion != null && listaProduccion.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaProduccion);
            }
        }
        //Banco Austro
        if (ordenBancaria.getNombreCuenta().toUpperCase().contains("AUSTRO")) {
            ordenBancaria.setBanco("AUSTRO");
            List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichinchaInternacional = pagosAnticiposDao.listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
            this.tratamientoDeLaLista(listaPichinchaInternacional);
            if (listaPichinchaInternacional != null && listaPichinchaInternacional.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaPichinchaInternacional);
            }
        }
        //Banco Machala
        if (ordenBancaria.getNombreCuenta().toUpperCase().contains("MACHALA")) {
            ordenBancaria.setBanco("MACHALA");
            List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> listaBancoMachala = pagosAnticiposDao.listaOrdenesBancoMachala(ordenBancaria, sector, usuario);
            this.tratamientoDeLaLista(listaBancoMachala);
            if (listaBancoMachala != null && listaBancoMachala.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaBancoMachala);
            }
        }
        //guayaquil
        if (ordenBancaria.getNombreCuenta().toUpperCase().contains("GUAYAQUIL")) {
            ordenBancaria.setBanco("GUAYAQUIL");
            List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> listaBancoGuayaquil = pagosAnticiposDao.listaOrdenesBancoGuayaquil(ordenBancaria, sector, usuario);
            this.tratamientoDeLaLista(listaBancoGuayaquil);
            if (listaBancoGuayaquil != null && listaBancoGuayaquil.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaBancoGuayaquil);
            }
        }
        return resp;
    }

    public void tratamientoDeLaLista(List lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) == null) {
                lista.remove(i);
            }
        }
    }

    @Override
    public MensajeTO mayorizarAnticiposPago(CarPagosAnticipoTO carPagosAnticipoTO, String observaciones, String nombreProveedor, String fecha, String sectorProveedor, String documento, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        comprobar = false;
        Boolean periodoCerrado = false;
        List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(carPagosAnticipoTO.getAntEmpresa());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                carPagosAnticipoTO.setAntPeriodo(sisListaPeriodoTO.getPerCodigo());
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (comprobar) {
            if (!periodoCerrado) {
                if (sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(carPagosAnticipoTO.getAntEmpresa(), sectorProveedor)) != null) {
                    // CREA EL SUSCESO EL DETALLE SE COMPLETA
                    // insertarTransaccionContable
                    susSuceso = "UPDATE";
                    susTabla = "cartera.car_pagos_anticipos";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    carPagosAnticipoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

                    if (tipoDao.buscarTipoContable(carPagosAnticipoTO.getAntEmpresa(), carPagosAnticipoTO.getAntTipo())) {
                        InvProveedor invProveedor = proveedorDao.buscarInvProveedor(carPagosAnticipoTO.getProvEmpresa(), carPagosAnticipoTO.getProvCodigo());
                        if (invProveedor != null) {
                            CarPagosForma carPagosForma = pagosFormaDao.obtenerPorId(CarPagosForma.class, carPagosAnticipoTO.getFpSecuencial());
                            if (carPagosForma != null) {
                                ConCuentas conCuentaFormaPago = cuentasDao.buscarCuentas(carPagosForma.getCtaEmpresa(), carPagosForma.getCtaCodigo());
                                ConCuentas conCuentaAnticiposProveedores = cuentasDao
                                        .obtenerPorId(ConCuentas.class,
                                                new ConCuentasPK(invProveedor.getInvProveedorPK().getProvEmpresa(), cuentasContablesDao.getCuentasContablesTO(invProveedor.getInvProveedorPK().getProvEmpresa()).getCtaAnticiposAProveedores()));
                                if (conCuentaFormaPago != null) {
                                    if (conCuentaAnticiposProveedores != null) {
                                        ConContable conContable = new ConContable();
                                        conContable.setConContablePK(new ConContablePK(
                                                carPagosAnticipoTO.getAntEmpresa(),
                                                carPagosAnticipoTO.getAntPeriodo(), carPagosAnticipoTO.getAntTipo(),
                                                carPagosAnticipoTO.getAntNumero()));
                                        conContable.setConAnulado(false);
                                        conContable.setConBloqueado(false);
                                        conContable.setConConcepto(nombreProveedor);
                                        conContable.setConReferencia("cartera.car_pagos_anticipos");
                                        conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                                        conContable.setConObservaciones(observaciones);
                                        conContable.setConFecha(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                        conContable.setConGenerado(true);
                                        conContable.setConPendiente(false);
                                        conContable.setConReversado(false);
                                        conContable.setUsrCodigo(carPagosAnticipoTO.getUsrCodigo());
                                        conContable.setUsrEmpresa(carPagosAnticipoTO.getUsrEmpresa());
                                        conContable.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(carPagosAnticipoTO.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss")));

                                        ConDetalle conDetalle = new ConDetalle();
                                        List<ConDetalle> listaConDetalle = new ArrayList<>();

                                        conDetalle.setConContable(conContable);
                                        conDetalle.setConCuentas(conCuentaAnticiposProveedores);
                                        conDetalle.setDetSecuencia(0L);
                                        conDetalle.setPrdSector(new PrdSector(carPagosAnticipoTO.getAntEmpresa(), sectorProveedor));
                                        conDetalle.setPrdPiscina(null);
                                        conDetalle.setDetDocumento("");
                                        conDetalle.setDetDebitoCredito('D');
                                        conDetalle.setDetValor(carPagosAnticipoTO.getAntValor());
                                        conDetalle.setDetGenerado(true);
                                        conDetalle.setDetReferencia("ANT-PRO");
                                        conDetalle.setDetObservaciones("");
                                        conDetalle.setDetOrden(1);
                                        listaConDetalle.add(conDetalle);

                                        conDetalle = new ConDetalle();
                                        conDetalle.setConContable(conContable);
                                        conDetalle.setConCuentas(conCuentaFormaPago);
                                        conDetalle.setDetSecuencia(0L);
                                        conDetalle.setPrdSector(new PrdSector(carPagosAnticipoTO.getAntEmpresa(), carPagosForma.getSecCodigo()));
                                        conDetalle.setPrdPiscina(null);
                                        conDetalle.setDetDocumento(documento);
                                        conDetalle.setDetDebitoCredito('C');
                                        conDetalle.setDetValor(carPagosAnticipoTO.getAntValor());
                                        conDetalle.setDetGenerado(true);
                                        conDetalle.setDetReferencia("FP");
                                        conDetalle.setDetObservaciones("");
                                        conDetalle.setDetOrden(2);
                                        listaConDetalle.add(conDetalle);
                                        carPagosAnticipoTO.setAntPagado(false);
                                        carPagosAnticipoTO.setSecEmpresa(carPagosAnticipoTO.getAntEmpresa());
                                        carPagosAnticipoTO.setSecCodigo(sectorProveedor);
                                        CarPagosAnticipos carPagosAnticipos = ConversionesCar.convertirCarPagosAnticiposTO_CarPagosAnticipos(carPagosAnticipoTO);
                                        carPagosAnticipos.setFpSecuencial(carPagosForma);

                                        comprobar = contableDao.mayorizarTransaccionContableCartera(conContable,
                                                listaConDetalle, sisSuceso, null, null, null, null, null, null, null, null, carPagosAnticipos, null, sisInfoTO);
                                        if (comprobar) {
                                            SisPeriodo sisPeriodo = periodoService.buscarPeriodo(carPagosAnticipoTO.getAntEmpresa(), conContable.getConContablePK().getConPeriodo());

                                            ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class,
                                                    new ConTipoPK(carPagosAnticipoTO.getAntEmpresa(), conContable.getConContablePK().getConTipo()));

                                            mensaje = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                                                    + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                                    + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                                    + "</font>.<br> Número: <font size = 5>"
                                                    + conContable.getConContablePK().getConNumero()
                                                    + "</font>.</html>"
                                                    + conContable.getConContablePK().getConPeriodo() + ", "
                                                    + conContable.getConContablePK().getConNumero();
                                            mensajeTO.getMap().put("conContable", conContable);
                                            contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "elsexc ");
                                            mensaje = "F<html>Hubo un error al guardar el Anticipo de Pago.\nCódigo repetido: "
                                                    + carPagosAnticipoTO.getAntNumero()
                                                    + "\nIntentelo de nuevo...</html>";
                                        }
                                    } else {
                                        mensaje = "F<html>La CUENTA CONTABLE anticipo del PROVEEDOR ya\nno se encuentra disponible "
                                                + "o no ha sido ingresada.\n</html>";
                                    }
                                } else {
                                    mensaje = "F<html>La CUENTA CONTABLE de la FORMA DE PAGO ya\nno se encuentra disponible o no ha sido ingresada.\nCódigo contable: "
                                            + carPagosForma.getCtaCodigo() + "</html>";
                                }
                            } else {
                                mensaje = "F<html>La FORMA DE PAGO que ingresó ya no se encuentra disponible.</html>";
                            }
                        } else {
                            mensaje = "F<html>El PROVEEDOR que ingresó ya no se encuentra disponible.</html>";
                        }
                    } else {
                        mensaje = "F<html>El TIPO DE CONTABLE C-ACLI no ha sido creado aún.\nContacte con el administrador.</html>";
                    }
                } else {
                    mensaje = "F<html>El CP " + sectorProveedor
                            + "ya no esta disponible.\nContacte con el administrador.</html>";
                }
            } else {
                mensaje = "F<html>El periodo de la fecha que ingresó está cerrado.</html>";
            }
        } else {
            mensaje = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
        }
        mensajeTO.setMensaje(mensaje);
        return mensajeTO;

    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoProveedor(String empresa, String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception {
        return pagosAnticiposDao.getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoProveedor(empresa, sector, proveedorCodigo, hasta, incluirTodos);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoCC(String empresa, String sector, String proveedorCodigo, String hasta, boolean incluirTodos) throws Exception {
        return pagosAnticiposDao.getCarListaCuentasPorPagarSaldoAnticiposTOAgrupadoCC(empresa, sector, proveedorCodigo, hasta, incluirTodos);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistoricoAgrupadoProveedor(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        return pagosAnticiposDao.getCarListadoAnticipoProveedorHistoricoAgrupadoProveedor(empresa, sector, proveedorCodigo, desde, hasta, incluirTodos);
    }

    @Override
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListadoAnticipoProveedorHistoricoAgrupadoCC(String empresa, String sector, String proveedorCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        return pagosAnticiposDao.getCarListadoAnticipoProveedorHistoricoAgrupadoCC(empresa, sector, proveedorCodigo, desde, hasta, incluirTodos);
    }

}
