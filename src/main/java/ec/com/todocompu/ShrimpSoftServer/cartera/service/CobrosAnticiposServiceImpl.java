package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CuentasContablesDao;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosFormaDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCar;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorCobrarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticiposPK;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosForma;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.HashMap;
import java.util.Map;

@Service
public class CobrosAnticiposServiceImpl implements CobrosAnticiposService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private CobrosFormaDao cobrosFormaDao;
    @Autowired
    private CobrosAnticiposDao cobrosAnticiposDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private CuentasContablesDao cuentasContablesDao;
    @Autowired
    private SectorDao sectorDao;
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
    public List<CarFunCobrosSaldoAnticipoTO> getCarFunCobrosSaldoAnticipoTO(String empresa, String cliente) throws Exception {
        return cobrosAnticiposDao.getCarFunCobrosSaldoAnticipoTO(empresa, cliente);
    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTO(String empresa, String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception {
        return cobrosAnticiposDao.getCarListaCuentasPorCobrarSaldoAnticiposTO(empresa, sector, clienteCodigo, hasta, incluirTodos);
    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCP(String empresa, String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception {
        return cobrosAnticiposDao.getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCP(empresa, sector, clienteCodigo, hasta, incluirTodos);
    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCliente(String empresa, String sector, String clienteCodigo, String hasta, boolean incluirTodos) throws Exception {
        return cobrosAnticiposDao.getCarListaCuentasPorCobrarSaldoAnticiposTOAgrupadoCliente(empresa, sector, clienteCodigo, hasta, incluirTodos);
    }

    @Override
    public MensajeTO insertarAnticiposCobro(CarCobrosAnticipoTO carCobrosAnticipoTO, String observaciones, String nombreCliente, String fecha, String sectorCliente, String documento, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        comprobar = false;
        Boolean periodoCerrado = false;
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(carCobrosAnticipoTO.getAntEmpresa());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                carCobrosAnticipoTO.setAntPeriodo(sisListaPeriodoTO.getPerCodigo());
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }

        if (comprobar) {
            if (!periodoCerrado) {
                //validar banco
                boolean validoBanco = true;
                if (carCobrosAnticipoTO.getBanCodigo() != null && carCobrosAnticipoTO.getBanEmpresa() != null) {
                    validoBanco = bancoDao.getBancoTo(carCobrosAnticipoTO.getBanCodigo(), carCobrosAnticipoTO.getBanEmpresa()) != null;
                }
                if (validoBanco) {
                    if (sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(carCobrosAnticipoTO.getAntEmpresa(), sectorCliente)) != null) {
                        /// SE CREA EL SUCESO
                        susSuceso = "INSERT";
                        susTabla = "cartera.car_anticipos_cobros";
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                        carCobrosAnticipoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

                        if (tipoDao.buscarTipoContable(carCobrosAnticipoTO.getAntEmpresa(), carCobrosAnticipoTO.getAntTipo())) {
                            InvCliente invCliente = clienteDao.buscarInvCliente(carCobrosAnticipoTO.getCliEmpresa(), carCobrosAnticipoTO.getCliCodigo());
                            if (invCliente != null) {
                                CarCobrosForma carCobrosForma = cobrosFormaDao.obtenerPorId(CarCobrosForma.class, carCobrosAnticipoTO.getFpSecuencial());
                                if (carCobrosForma != null) {
                                    ConCuentas conCuentaAnticiposClientes = cuentasDao.obtenerPorId(ConCuentas.class,
                                            new ConCuentasPK(invCliente.getInvClientePK().getCliEmpresa(),
                                                    cuentasContablesDao
                                                            .getCuentasContablesTO(invCliente.getInvClientePK()
                                                                    .getCliEmpresa())
                                                            .getCtaAnticiposDeClientes()));// invCliente.getInvClienteCategoria().getCtaAntipos()
                                    ConCuentas conCuentaFormaPago = cuentasDao.buscarCuentas(
                                            carCobrosForma.getCtaEmpresa(), carCobrosForma.getCtaCodigo());
                                    if (conCuentaFormaPago != null) {
                                        if (conCuentaAnticiposClientes != null) {
                                            ConContable conContable = new ConContable();
                                            conContable.setConContablePK(
                                                    new ConContablePK(carCobrosAnticipoTO.getAntEmpresa(),
                                                            carCobrosAnticipoTO.getAntPeriodo(),
                                                            carCobrosAnticipoTO.getAntTipo(),
                                                            carCobrosAnticipoTO.getAntNumero()));
                                            conContable.setConAnulado(false);
                                            conContable.setConBloqueado(false);
                                            conContable.setConConcepto(nombreCliente);
                                            conContable.setConReferencia("cartera.car_cobros_anticipos");
                                            conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                                            conContable.setConObservaciones(observaciones);
                                            conContable.setConFecha(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                            conContable.setConGenerado(true);
                                            conContable.setConPendiente(false);
                                            conContable.setUsrCodigo(carCobrosAnticipoTO.getUsrCodigo());
                                            conContable.setUsrEmpresa(carCobrosAnticipoTO.getUsrEmpresa());
                                            conContable.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(carCobrosAnticipoTO.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss")));

                                            ConDetalle conDetalle = null;
                                            List<ConDetalle> listaConDetalle = new ArrayList<ConDetalle>();

                                            conDetalle = new ConDetalle();
                                            conDetalle.setConContable(conContable);
                                            conDetalle.setConCuentas(conCuentaAnticiposClientes);
                                            conDetalle.setDetSecuencia(0L);
                                            conDetalle.setPrdSector(new PrdSector(carCobrosAnticipoTO.getAntEmpresa(), sectorCliente));
                                            conDetalle.setPrdPiscina(null);
                                            conDetalle.setDetDocumento("");
                                            conDetalle.setDetDebitoCredito('C');
                                            conDetalle.setDetValor(carCobrosAnticipoTO.getAntValor());
                                            conDetalle.setDetGenerado(true);
                                            conDetalle.setDetReferencia("ANT-CLI");
                                            conDetalle.setDetObservaciones("");
                                            conDetalle.setDetOrden(2);
                                            listaConDetalle.add(conDetalle);

                                            conDetalle = new ConDetalle();
                                            conDetalle.setConContable(conContable);
                                            conDetalle.setConCuentas(conCuentaFormaPago);
                                            conDetalle.setDetSecuencia(0L);
                                            conDetalle.setPrdSector(new PrdSector(carCobrosAnticipoTO.getAntEmpresa(), carCobrosForma.getSecCodigo()));
                                            conDetalle.setPrdPiscina(null);
                                            conDetalle.setDetDocumento(documento);
                                            conDetalle.setDetDebitoCredito('D');
                                            conDetalle.setDetValor(carCobrosAnticipoTO.getAntValor());
                                            conDetalle.setDetGenerado(true);
                                            conDetalle.setDetReferencia("FP");
                                            conDetalle.setDetObservaciones("");
                                            conDetalle.setDetOrden(1);
                                            listaConDetalle.add(conDetalle);

                                            ConNumeracion conNumeracion = new ConNumeracion(
                                                    new ConNumeracionPK(carCobrosAnticipoTO.getAntEmpresa(),
                                                            carCobrosAnticipoTO.getAntPeriodo(),
                                                            carCobrosAnticipoTO.getAntTipo()));

                                            carCobrosAnticipoTO.setAntCobrado(false);
                                            carCobrosAnticipoTO.setSecEmpresa(carCobrosAnticipoTO.getAntEmpresa());
                                            carCobrosAnticipoTO.setSecCodigo(sectorCliente);

                                            CarCobrosAnticipos carCobrosAnticipos = ConversionesCar.convertirCarCobrosAnticiposTO_CarCobrosAnticipos(carCobrosAnticipoTO);
                                            carCobrosAnticipos.setFpSecuencial(carCobrosForma);

                                            comprobar = contableDao.insertarTransaccionContable(conContable,
                                                    listaConDetalle, sisSuceso, conNumeracion, null, null, null, null,
                                                    null, null, null, null, null, null, null, null, null,
                                                    carCobrosAnticipos, null, sisInfoTO);
                                            contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                                            if (comprobar) {
                                                SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                                        carCobrosAnticipoTO.getAntEmpresa(),
                                                        conContable.getConContablePK().getConPeriodo());

                                                ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class,
                                                        new ConTipoPK(carCobrosAnticipoTO.getAntEmpresa(),
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
                                                mensaje = "F<html>Hubo un error al guardar el Anticipo de Cobro.\nCódigo repetido: "
                                                        + carCobrosAnticipoTO.getAntNumero()
                                                        + "\nIntentelo de nuevo...</html>";
                                            }
                                        } else {
                                            mensaje = "F<html>La CUENTA CONTABLE anticipo del CLIENTE ya\nno se encuentra disponible "
                                                    + "o no ha sido ingresada.\n</html>";
                                        }
                                    } else {
                                        mensaje = "F<html>La CUENTA CONTABLE de la FORMA DE COBRO ya\nno se encuentra disponible o no ha sido ingresada.\nCódigo contable: "
                                                + carCobrosForma.getCtaCodigo() + "</html>";
                                    }
                                } else {
                                    mensaje = "F<html>La FORMA DE COBRO que ingresó ya no se encuentra disponible.</html>";
                                }
                            } else {
                                mensaje = "F<html>El CLIENTE que ingresó ya no se encuentra disponible.</html>";
                            }
                        } else {
                            mensaje = "F<html>El TIPO DE CONTABLE C-ACLI no ha sido creado aún.\nContacte con el administrador.</html>";
                        }
                    } else {
                        mensaje = "F<html>El CP " + sectorCliente + "ya no está disponible.\nContacte con el administrador.</html>";
                    }
                } else {
                    mensaje = "F<html>El Banco seleccionado ya no está disponible.\nContacte con el administrador.</html>";
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

    // <editor-fold defaultstate="collapsed" desc="obtenerAnticipoCobro">
    @Override
    public Map<String, Object> obtenerAnticipoCobro(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));

        CarCobrosAnticipos carCobrosAnticipos = cobrosAnticiposDao.obtenerCarCobrosAnticipo(empresa, periodo, tipo, numero);
        if (carCobrosAnticipos != null) {
            CarCobrosForma fpSeleccionada = carCobrosAnticipos.getFpSecuencial();
            CarComboPagosCobrosFormaTO fp = fpSeleccionada != null ? ConversionesCar.convertirCarCobrosForma_CarComboPagosCobrosFormaTO(fpSeleccionada) : null;
            CarCobrosAnticipoTO carCobrosAnticiposTO = ConversionesCar.convertirCarCobrosAnticipos_CarCobrosAnticiposTO(carCobrosAnticipos);
            InvCliente cliente = clienteDao.buscarInvCliente(carCobrosAnticipos.getCliEmpresa(), carCobrosAnticipos.getCliCodigo());
            if (cliente != null) {
                String nombre = cliente.getCliRazonSocial();
                String direccion = cliente.getCliDireccion();
                String codigo = cliente.getInvClientePK().getCliCodigo();
                campos.put("nombre", nombre);
                campos.put("codigo", codigo);
                campos.put("direccion", direccion);
            }
            ConContablePK contablePK = new ConContablePK(empresa, periodo, tipo, numero);
            ConContable contable = contableDao.getConContable(contablePK);
            String documento = "";
            if (contable != null && contable.getConDetalleList() != null && !contable.getConDetalleList().isEmpty()) {
                for (ConDetalle detalle : contable.getConDetalleList()) {
                    if (detalle.getDetDebitoCredito() == 'D') {
                        documento = detalle.getDetDocumento();
                        break;
                    }
                }
            }
            campos.put("fpSeleccionada", fp);
            campos.put("documento", documento);
            campos.put("carCobrosAnticiposTO", carCobrosAnticiposTO);
            campos.put("contable", contable);
        } else {
            throw new GeneralException("No se encuentra anticipo a cliente");
        }

        return campos;
    }

    @Override
    public MensajeTO mayorizarAnticiposCobro(CarCobrosAnticipoTO carCobrosAnticipoTO, String observaciones, String nombreCliente, String fecha, String sectorCliente, String documento, SisInfoTO sisInfoTO) throws Exception {

        MensajeTO mensajeTO = new MensajeTO();
        comprobar = false;
        Boolean periodoCerrado = false;
        List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(carCobrosAnticipoTO.getAntEmpresa());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                carCobrosAnticipoTO.setAntPeriodo(sisListaPeriodoTO.getPerCodigo());
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }

        if (comprobar) {
            if (!periodoCerrado) {
                if (sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(carCobrosAnticipoTO.getAntEmpresa(), sectorCliente)) != null) {
                    /// SE CREA EL SUCESO
                    susSuceso = "UPDATE";
                    susTabla = "cartera.car_anticipos_cobros";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    carCobrosAnticipoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

                    if (tipoDao.buscarTipoContable(carCobrosAnticipoTO.getAntEmpresa(), carCobrosAnticipoTO.getAntTipo())) {
                        InvCliente invCliente = clienteDao.buscarInvCliente(carCobrosAnticipoTO.getCliEmpresa(), carCobrosAnticipoTO.getCliCodigo());
                        if (invCliente != null) {
                            CarCobrosForma carCobrosForma = cobrosFormaDao.obtenerPorId(CarCobrosForma.class, carCobrosAnticipoTO.getFpSecuencial());
                            if (carCobrosForma != null) {
                                ConCuentas conCuentaAnticiposClientes = cuentasDao
                                        .obtenerPorId(ConCuentas.class,
                                                new ConCuentasPK(invCliente.getInvClientePK().getCliEmpresa(),
                                                        cuentasContablesDao.getCuentasContablesTO(invCliente.getInvClientePK().getCliEmpresa()).getCtaAnticiposDeClientes()));
                                ConCuentas conCuentaFormaPago = cuentasDao.buscarCuentas(carCobrosForma.getCtaEmpresa(), carCobrosForma.getCtaCodigo());
                                if (conCuentaFormaPago != null) {
                                    if (conCuentaAnticiposClientes != null) {
                                        ConContable conContable = new ConContable();
                                        conContable.setConContablePK(
                                                new ConContablePK(carCobrosAnticipoTO.getAntEmpresa(),
                                                        carCobrosAnticipoTO.getAntPeriodo(),
                                                        carCobrosAnticipoTO.getAntTipo(),
                                                        carCobrosAnticipoTO.getAntNumero()));
                                        conContable.setConAnulado(false);
                                        conContable.setConBloqueado(false);
                                        conContable.setConConcepto(nombreCliente);
                                        conContable.setConReferencia("cartera.car_cobros_anticipos");
                                        conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                                        conContable.setConObservaciones(observaciones);
                                        conContable.setConFecha(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                        conContable.setConGenerado(true);
                                        conContable.setConPendiente(false);
                                        conContable.setUsrCodigo(carCobrosAnticipoTO.getUsrCodigo());
                                        conContable.setUsrEmpresa(carCobrosAnticipoTO.getUsrEmpresa());
                                        conContable.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(
                                                carCobrosAnticipoTO.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss")));

                                        List<ConDetalle> listaConDetalle = new ArrayList<>();
                                        ConDetalle conDetalle = new ConDetalle();
                                        conDetalle.setConContable(conContable);
                                        conDetalle.setConCuentas(conCuentaAnticiposClientes);
                                        conDetalle.setDetSecuencia(0L);
                                        conDetalle.setPrdSector(
                                                new PrdSector(carCobrosAnticipoTO.getAntEmpresa(), sectorCliente));
                                        conDetalle.setPrdPiscina(null);
                                        conDetalle.setDetDocumento("");
                                        conDetalle.setDetDebitoCredito('C');
                                        conDetalle.setDetValor(carCobrosAnticipoTO.getAntValor());
                                        conDetalle.setDetGenerado(true);
                                        conDetalle.setDetReferencia("ANT-CLI");
                                        conDetalle.setDetObservaciones("");
                                        conDetalle.setDetOrden(2);
                                        listaConDetalle.add(conDetalle);

                                        conDetalle = new ConDetalle();
                                        conDetalle.setConContable(conContable);
                                        conDetalle.setConCuentas(conCuentaFormaPago);
                                        conDetalle.setDetSecuencia(0L);
                                        conDetalle.setPrdSector(new PrdSector(carCobrosAnticipoTO.getAntEmpresa(), carCobrosForma.getSecCodigo()));
                                        conDetalle.setPrdPiscina(null);
                                        conDetalle.setDetDocumento(documento);
                                        conDetalle.setDetDebitoCredito('D');
                                        conDetalle.setDetValor(carCobrosAnticipoTO.getAntValor());
                                        conDetalle.setDetGenerado(true);
                                        conDetalle.setDetReferencia("FP");
                                        conDetalle.setDetObservaciones("");
                                        conDetalle.setDetOrden(1);
                                        listaConDetalle.add(conDetalle);
                                        carCobrosAnticipoTO.setAntCobrado(false);
                                        carCobrosAnticipoTO.setSecEmpresa(carCobrosAnticipoTO.getAntEmpresa());
                                        carCobrosAnticipoTO.setSecCodigo(sectorCliente);

                                        CarCobrosAnticipos carCobrosAnticipos = ConversionesCar.convertirCarCobrosAnticiposTO_CarCobrosAnticipos(carCobrosAnticipoTO);
                                        carCobrosAnticipos.setFpSecuencial(carCobrosForma);

                                        comprobar = contableDao.mayorizarTransaccionContableCartera(conContable,
                                                listaConDetalle, sisSuceso, null, null, null, null, null, null, null, null, null, carCobrosAnticipos, sisInfoTO);
                                        if (comprobar) {
                                            SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                                    carCobrosAnticipoTO.getAntEmpresa(),
                                                    conContable.getConContablePK().getConPeriodo());

                                            ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class,
                                                    new ConTipoPK(carCobrosAnticipoTO.getAntEmpresa(),
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
                                            contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
                                        } else {
                                            mensaje = "F<html>Hubo un error al guardar el Anticipo de Cobro.\nCódigo repetido: "
                                                    + carCobrosAnticipoTO.getAntNumero()
                                                    + "\nIntentelo de nuevo...</html>";
                                        }
                                    } else {
                                        mensaje = "F<html>La CUENTA CONTABLE anticipo del CLIENTE ya\nno se encuentra disponible "
                                                + "o no ha sido ingresada.\n</html>";
                                    }
                                } else {
                                    mensaje = "F<html>La CUENTA CONTABLE de la FORMA DE COBRO ya\nno se encuentra disponible o no ha sido ingresada.\nCódigo contable: "
                                            + carCobrosForma.getCtaCodigo() + "</html>";
                                }
                            } else {
                                mensaje = "F<html>La FORMA DE COBRO que ingresó ya no se encuentra disponible.</html>";
                            }
                        } else {
                            mensaje = "F<html>El CLIENTE que ingresó ya no se encuentra disponible.</html>";
                        }
                    } else {
                        mensaje = "F<html>El TIPO DE CONTABLE C-ACLI no ha sido creado aún.\nContacte con el administrador.</html>";
                    }
                } else {
                    mensaje = "F<html>El CP " + sectorCliente
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
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistorico(String empresa, String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        return cobrosAnticiposDao.getCarListarAnticiposClientesHistorico(empresa, sector, clienteCodigo, desde, hasta, incluirTodos);
    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistoricoAgrupadoCliente(String empresa, String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        return cobrosAnticiposDao.getCarListarAnticiposClientesHistoricoAgrupadoCliente(empresa, sector, clienteCodigo, desde, hasta, incluirTodos);
    }

    @Override
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListarAnticiposClientesHistoricoAgrupadoCC(String empresa, String sector, String clienteCodigo, String desde, String hasta, boolean incluirTodos) throws Exception {
        return cobrosAnticiposDao.getCarListarAnticiposClientesHistoricoAgrupadoCC(empresa, sector, clienteCodigo, desde, hasta, incluirTodos);
    }

}
