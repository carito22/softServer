package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.banco.service.BancoService;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDetalleAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDetalleFormaDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDetalleVentasDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosFormaDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.InvClienteGrupoEmpresarialService;
import ec.com.todocompu.ShrimpSoftServer.isp.service.SisEmpresaParametrosMikrotikService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SisEmpresaNotificacionesService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCar;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarContableTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorCobrarListadoVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaMayorAuxiliarClienteProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobros;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleForma;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleVentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteGrupoEmpresarialTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametrosMikrotik;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class CobrosServiceImpl implements CobrosService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private CobrosDao cobrosDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private PagosFormaDao pagosFormaDao;
    @Autowired
    private CobrosAnticiposService cobrosAnticiposService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private BancoService bancoService;
    @Autowired
    private InvClienteGrupoEmpresarialService invClienteGrupoEmpresarialService;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private CobrosDetalleVentasDao cobrosDetalleVentasDao;
    @Autowired
    private CobrosDetalleFormaDao cobrosDetalleFormaDao;
    @Autowired
    private CobrosDetalleAnticiposDao cobrosDetalleAnticiposDao;
    @Autowired
    private SisEmpresaNotificacionesService sisEmpresaNotificacionesService;
    @Autowired
    private SisEmpresaParametrosMikrotikService sisEmpresaParametrosMikrotikService;
    @Autowired
    private SectorService sectorService;
    private Boolean comprobar = false;
    private String mensaje = "";
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public CarContableTO insertarCarCobros(CarCobrosTO carCobrosTO,
            List<CarCobrosDetalleVentasTO> carCobrosDetalleVentasTOs,
            List<CarCobrosDetalleAnticiposTO> carCobrosDetalleAnticiposTOs,
            List<CarCobrosDetalleFormaTO> carCobrosDetalleFormaTOs, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        mensaje = "";
        String tipDetalle = "C-COB";
        CarContableTO carContableTO = new CarContableTO();
        List<String> lista = new ArrayList<>(1);
        List<String> listaFacturaTO = new ArrayList<>(1);
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<>(1);
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(carCobrosTO.getUsrEmpresa());

        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(carCobrosTO.getCobFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(carCobrosTO.getCobFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()
                    && sisListaPeriodoTO.getPerCerrado() == false) {
                comprobar = true;
                carCobrosTO.setCobPeriodo(sisListaPeriodoTO.getPerCodigo());
                break;
            }
        }
        if (comprobar) {
            List<CarListaCobrosTO> carListaPagosCobrosTOs = new ArrayList<>(1);
            carListaPagosCobrosTOs = cobrosDao.getCarListaCobrosTO(carCobrosTO.getUsrEmpresa(), carCobrosTO.getCliCodigo());
            boolean documento;
            for (int k = 0; k < carCobrosDetalleVentasTOs.size(); k++) {
                documento = false;
                for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                    if (carCobrosDetalleVentasTOs.get(k).getVtaPeriodo()
                            .equals(carListaPagosCobrosTOs.get(i).getCxccPeriodo())
                            && carCobrosDetalleVentasTOs.get(k).getVtaMotivo()
                                    .equals(carListaPagosCobrosTOs.get(i).getCxccMotivo())
                            && carCobrosDetalleVentasTOs.get(k).getVtaNumero()
                                    .equals(carListaPagosCobrosTOs.get(i).getCxccNumero())) {
                        documento = true;

                        if ((carCobrosDetalleVentasTOs.get(k).getDetValor().abs().compareTo(carListaPagosCobrosTOs.get(i).getCxccSaldo().abs()) > 0)) {
                            lista.add(carCobrosDetalleVentasTOs.get(k).getVtaDocumento() + "     "
                                    + carCobrosDetalleVentasTOs.get(k).getVtaPeriodo() + " | "
                                    + carCobrosDetalleVentasTOs.get(k).getVtaMotivo() + " | "
                                    + carCobrosDetalleVentasTOs.get(k).getVtaNumero());
                        }
                    } else if (i == carListaPagosCobrosTOs.size() - 1 && !documento) {
                        lista.add(carCobrosDetalleVentasTOs.get(k).getVtaDocumento() + "     "
                                + carCobrosDetalleVentasTOs.get(k).getVtaPeriodo() + " | "
                                + carCobrosDetalleVentasTOs.get(k).getVtaMotivo() + " | "
                                + carCobrosDetalleVentasTOs.get(k).getVtaNumero());
                    }
                }
            }
            if (lista.isEmpty()) {
                comprobar = false;
                //// NO HAY ERRORES EN FACTURA
                if (listaFacturaTO.isEmpty()) {
                    // llenar contable
                    ConContableTO conContableTO = new ConContableTO();
                    conContableTO.setEmpCodigo(carCobrosTO.getUsrEmpresa());
                    conContableTO.setPerCodigo(carCobrosTO.getCobPeriodo());
                    conContableTO.setTipCodigo(tipDetalle);
                    carCobrosTO.setCobTipo(tipDetalle);
                    conContableTO.setConFecha(carCobrosTO.getCobFecha());
                    conContableTO.setConPendiente(false);
                    conContableTO.setConBloqueado(false);
                    conContableTO.setConAnulado(false);
                    conContableTO.setConGenerado(true);
                    conContableTO.setConConcepto(carCobrosTO.getConApellidosNombres());// nombre
                    // empleado
                    conContableTO.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");//
                    conContableTO.setConObservaciones(carCobrosTO.getCobObservaciones());
                    conContableTO.setUsrInsertaContable(carCobrosTO.getUsrCodigo());
                    conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                    ///// CREANDO Suceso (SE TERMINA DE LLENAR EN EL
                    ///// TRANSACCION)
                    susSuceso = "INSERT";
                    susTabla = "cartera.car_cobros";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);
                    ////// CREANDO NUMERACION
                    ConNumeracion conNumeracion = new ConNumeracion(new ConNumeracionPK(carCobrosTO.getUsrEmpresa(),
                            carCobrosTO.getCobPeriodo(), carCobrosTO.getCobTipo()));
                    ////// CREANDO CONTABLE
                    ConContable conContable = ConversionesContabilidad.convertirConContableTO_ConContable(conContableTO);
                    conContable.setConReferencia("cartera.car_cobros");
                    ////// CREANDO CarPagos
                    carCobrosTO.setCobTipo(tipDetalle);
                    carCobrosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                    CarCobros carCobros = ConversionesCar.convertirCarCobrosTO_CarCobros(carCobrosTO);
                    ////// CREANDO Lista de CarPagosDetalleCompras
                    List<CarCobrosDetalleVentas> carCobrosDetalleVentas = new ArrayList<>(0);
                    for (CarCobrosDetalleVentasTO carCobrosDetalleVentasTO : carCobrosDetalleVentasTOs) {
                        carCobrosDetalleVentasTO.setDetSecuencial(0);
                        carCobrosDetalleVentas.add(ConversionesCar.convertirCarCobrosDetalleVentasTO_CarCobrosDetalleVentas(carCobrosDetalleVentasTO));
                    }
                    ////// CREANDO Lista de CarPagosDetalleForma
                    List<CarCobrosDetalleForma> carCobrosDetalleForma = new ArrayList<>(0);
                    for (CarCobrosDetalleFormaTO carCobrosDetalleFormaTO : carCobrosDetalleFormaTOs) {
                        carCobrosDetalleForma.add(ConversionesCar.convertirCarCobrosDetalleFormaTO_CarCobrosDetalleForma(carCobrosDetalleFormaTO));
                    }
                    ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                    List<ConDetalle> listaConDetalle = new ArrayList<>(0);
                    ////// CREANDO Lista de CarCobrosDetalleAnticipos
                    List<CarCobrosDetalleAnticipos> carCobrosDetalleAnticipos = new ArrayList<>(0);
                    for (CarCobrosDetalleAnticiposTO carCobrosDetalleAnticiposTO : carCobrosDetalleAnticiposTOs) {
                        carCobrosDetalleAnticipos.add(ConversionesCar.convertirCarCobrosDetalleAnticiposTO_CarCobrosDetalleAnticipos(carCobrosDetalleAnticiposTO));
                    }

                    comprobar = false;
                    if (mensaje.isEmpty()) {// revisar si estan llenos
                        comprobar = contableDao.insertarTransaccionContable(conContable, listaConDetalle, sisSuceso,
                                conNumeracion, null, null, null, null, null, null, null, carCobros,
                                carCobrosDetalleAnticipos, carCobrosDetalleVentas, carCobrosDetalleForma, null,
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
                                + carCobros.getCarCobrosPK().getCobNumero();

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
                carContableTO.setListaFacturaTO(lista);
            }
        } else {
            mensaje = "FNo se encuentra ningún periodo para la fecha que ingresó\nNo esta creado o esta cerrado...";
        }
        carContableTO.setMensaje(mensaje);
        return carContableTO;
    }

    @Override
    public CarListaCobrosClienteTO getCobrosConsultaClienteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        return cobrosDao.getCobrosConsultaCliente(empresa, periodo, tipo, numero);
    }

    @Override
    public List<CarListaMayorAuxiliarClienteProveedorTO> getCarListaMayorAuxiliarClienteProveedorTO(String empresa,
            String sector, String proveedor, String desde, String hasta, char accion, boolean anticipos) throws Exception {
        return cobrosDao.getCarListaMayorAuxiliarClienteProveedorTO(empresa, sector, proveedor, desde, hasta, accion, anticipos);
    }

    @Override
    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorCobrarAnticiposTO(String empresa,
            String sector, String hasta) throws Exception {
        return cobrosDao.getCarListaCuentasPorCobrarAnticiposTO(empresa, sector, hasta);
    }

    @Override
    public List<CuentasPorCobrarDetalladoTO> getCarListaCuentasPorCobrarDetalladoTO(String empresa, String sector,
            String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception {
        return cobrosDao.getCarListaCuentasPorCobrarDetalladoTO(empresa, sector, cliente, desde, hasta, grupo, ichfa);
    }

    @Override
    public List<CuentasPorCobrarDetalladoTO> getCarListaCuentasPorCobrarDetalladoTOCortesConexion(String empresa, String sector, String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception {
        return cobrosDao.getCarListaCuentasPorCobrarDetalladoTOCorteconexion(empresa, sector, cliente, desde, hasta, grupo, ichfa);
    }

    @Override
    public List<CarFunCuentasPorCobrarListadoVentasTO> getCarFunCuentasPorCobrarListadoVentasTO(String empresa,
            String sector, String cliente, String desde, String hasta) throws Exception {
        return cobrosDao.getCarFunCuentasPorCobrarListadoVentasTO(empresa, sector, cliente, desde, hasta);
    }

    @Override
    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorCobrarGeneralTO(String empresa, String sector,
            String hasta) throws Exception {
        return cobrosDao.getCarListaCuentasPorCobrarGeneralTO(empresa, sector, hasta);
    }

    @Override
    public List<CarListaCobrosTO> getCobrosConsultaDetalleVentasTO(String empresa, String periodo, String numero)
            throws Exception {
        return cobrosDao.getCobrosConsultaDetalleVentas(empresa, periodo, numero);
    }

    @Override
    public List<CarListaPagosCobrosDetalleFormaTO> getCobrosConsultaDetalleFormaTO(String empresa, String periodo,
            String numero, boolean hayPostfechados) throws Exception {
        return cobrosDao.getCobrosConsultaDetalleForma(empresa, periodo, numero, hayPostfechados);
    }

    @Override
    public List<CarListaPagosCobrosDetalleAnticipoTO> getCobrosConsultaDetalleAnticipo(String empresa, String periodo,
            String numero) throws Exception {
        return cobrosDao.getCobrosConsultaDetalleAnticipo(empresa, periodo, numero);
    }

    @Override
    public List<CarListaCobrosTO> getCarListaCobrosTO(String empresa, String cliente) throws Exception {
        return cobrosDao.getCarListaCobrosTO(empresa, cliente);
    }

    @Override
    public java.math.BigDecimal getCarDeudaVencida(String empresa, String cliente) throws Exception {
        return cobrosDao.getCarDeudaVencida(empresa, cliente);
    }

    @Override
    public List<CarFunCobrosTO> getCarFunCobrosTO(String empresa, String sector, String desde, String hasta,
            String cliente, boolean incluirTodos) throws Exception {
        return cobrosDao.getCarFunCobrosTO(empresa, sector, desde, hasta, cliente, incluirTodos);
    }

    @Override
    public List<CarFunCobrosDetalleTO> getCarFunCobrosDetalleTO(String empresa, String sector, String desde,
            String hasta, String cliente, String formaPago) throws Exception {
        return cobrosDao.getCarFunCobrosDetalleTO(empresa, sector, desde, hasta, cliente, formaPago);
    }

    @Override
    public List<CarFunCobrosDetalleTO> getCarFunCobrosDetalleTOAgrupadoCliente(String empresa, String sector, String desde,
            String hasta, String cliente, String formaPago) throws Exception {
        return cobrosDao.getCarFunCobrosDetalleTOAgrupadoCliente(empresa, sector, desde, hasta, cliente, formaPago);
    }

    @Override
    public Map<String, Object> obtenerDatosParaCuentasCobrarDetallado(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<InvClienteGrupoEmpresarialTO> listaGrupoEmpresarial = invClienteGrupoEmpresarialService.getInvClienteGrupoEmpresarialTO(empresa, null);
        List<SisEmpresaNotificaciones> notificaciones = sisEmpresaNotificacionesService.listarSisEmpresaNotificaciones(empresa);
        List<PrdListaSectorTO> sectores = sectorService.getListaSectorTO(empresa, false);
        campos.put("listaGrupoEmpresarial", listaGrupoEmpresarial);
        campos.put("notificaciones", notificaciones);
        campos.put("sectores", sectores);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaCobros(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//P:Pagos; C:Cobros

        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        List<ListaBanBancoTO> bancos = bancoService.getListaBanBancoTO(empresa);
        List<CarFunCobrosSaldoAnticipoTO> listaAnticipo = cobrosAnticiposService.getCarFunCobrosSaldoAnticipoTO(empresa, cliente);
        List<CarListaCobrosTO> listaCobros = getCarListaCobrosTO(empresa, cliente);
        List<CarComboPagosCobrosFormaTO> listaForma = pagosFormaDao.getCarComboPagosCobrosForma(empresa, accion);
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        List<InvClienteGrupoEmpresarialTO> listaGrupoEmpresarial = invClienteGrupoEmpresarialService.getInvClienteGrupoEmpresarialTO(empresa, null);
        campos.put("isPeriodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("fechaActual", fechaActual);
        campos.put("bancos", bancos);
        campos.put("listaAnticipo", listaAnticipo);
        campos.put("listaCobros", listaCobros);
        campos.put("listaForma", listaForma);
        campos.put("listaGrupoEmpresarial", listaGrupoEmpresarial);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerCobro(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));

        CarListaCobrosClienteTO cobro = getCobrosConsultaClienteTO(empresa, periodo, tipo, numero);
        if (cobro != null) {
            InvCliente invCliente = clienteDao.buscarInvCliente(empresa, cobro.getCliCodigo());
            List<CarListaCobrosTO> listaCobros = getCobrosConsultaDetalleVentasTO(empresa, periodo, numero);
            List<CarListaPagosCobrosDetalleFormaTO> listadoDeFormasDeCobro = getCobrosConsultaDetalleFormaTO(empresa, periodo, numero, false);
            List<CarListaPagosCobrosDetalleAnticipoTO> cobrosAnticipos = getCobrosConsultaDetalleAnticipo(empresa, periodo, numero);
            List<CarFunCobrosSaldoAnticipoTO> listadoCobrosAnticipos = new ArrayList<>();
            ConContablePK contablePK = new ConContablePK(empresa, periodo, tipo, numero);
            ConContable contable = contableDao.getConContable(contablePK);
            List<InvClienteGrupoEmpresarialTO> listaGrupoEmpresarial = invClienteGrupoEmpresarialService.getInvClienteGrupoEmpresarialTO(empresa, null);

            if (cobrosAnticipos != null) {
                for (CarListaPagosCobrosDetalleAnticipoTO cobrosAnticipo : cobrosAnticipos) {
                    CarFunCobrosSaldoAnticipoTO cobroAnticipo = ConversionesCar.convertirCarListaPagosCobrosDetalleAnticipoTO_CarFunCobrosSaldoAnticipoTO(cobrosAnticipo);
                    listadoCobrosAnticipos.add(cobroAnticipo);
                }
            }
            //empresa
            SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(empresa);
            campos.put("sisEmpresaParametrosMikrotik", sisEmpresaParametrosMikrotik);
            campos.put("cobro", cobro);
            campos.put("listaCobros", listaCobros);
            campos.put("listadoDeFormasDeCobro", listadoDeFormasDeCobro);
            campos.put("listadoCobrosAnticipos", listadoCobrosAnticipos);
            campos.put("contable", contable);
            campos.put("listaGrupoEmpresarial", listaGrupoEmpresarial);
            campos.put("invCliente", invCliente);
        } else {
            throw new GeneralException("No se encuentra cobro.");
        }

        return campos;
    }

    @Override
    public CarContableTO mayorizarCarCobros(CarCobrosTO carCobrosTO, List<CarCobrosDetalleVentasTO> carCobrosDetalleVentasTOs, List<CarCobrosDetalleAnticiposTO> carCobrosDetalleAnticiposTOs, List<CarCobrosDetalleFormaTO> carCobrosDetalleFormaTOs, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        mensaje = "";
        String tipDetalle = "C-COB";
        CarContableTO carContableTO = new CarContableTO();
        List<String> lista = new ArrayList<>(1);
        List<String> listaFacturaTO = new ArrayList<>(1);
        List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(carCobrosTO.getUsrEmpresa());

        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(carCobrosTO.getCobFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(carCobrosTO.getCobFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()
                    && sisListaPeriodoTO.getPerCerrado() == false) {
                comprobar = true;
                carCobrosTO.setCobPeriodo(sisListaPeriodoTO.getPerCodigo());
                break;
            }
        }
        if (comprobar) {
            carCobrosTO.setCobCodigoTransaccional(null);
            List<CarListaCobrosTO> carListaPagosCobrosTOs = cobrosDao.getCarListaCobrosTO(carCobrosTO.getUsrEmpresa(), carCobrosTO.getCliCodigo());
            boolean documento;
            for (int k = 0; k < carCobrosDetalleVentasTOs.size(); k++) {
                documento = false;
                for (int i = 0; i < carListaPagosCobrosTOs.size(); i++) {
                    if (carCobrosDetalleVentasTOs.get(k).getVtaPeriodo()
                            .equals(carListaPagosCobrosTOs.get(i).getCxccPeriodo()) && carCobrosDetalleVentasTOs.get(k).getVtaMotivo()
                            .equals(carListaPagosCobrosTOs.get(i).getCxccMotivo()) && carCobrosDetalleVentasTOs.get(k).getVtaNumero()
                            .equals(carListaPagosCobrosTOs.get(i).getCxccNumero())) {
                        documento = true;
                        if ((carCobrosDetalleVentasTOs.get(k).getDetValor().compareTo(carListaPagosCobrosTOs.get(i).getCxccSaldo()) > 0)) {
                            lista.add(carCobrosDetalleVentasTOs.get(k).getVtaDocumento() + "     "
                                    + carCobrosDetalleVentasTOs.get(k).getVtaPeriodo() + " | "
                                    + carCobrosDetalleVentasTOs.get(k).getVtaMotivo() + " | "
                                    + carCobrosDetalleVentasTOs.get(k).getVtaNumero());
                        }
                    } else if (i == carListaPagosCobrosTOs.size() - 1 && !documento) {
                        lista.add(carCobrosDetalleVentasTOs.get(k).getVtaDocumento() + "     "
                                + carCobrosDetalleVentasTOs.get(k).getVtaPeriodo() + " | "
                                + carCobrosDetalleVentasTOs.get(k).getVtaMotivo() + " | "
                                + carCobrosDetalleVentasTOs.get(k).getVtaNumero());
                    }
                }
            }
            if (lista.isEmpty()) {
                comprobar = false;
                //// NO HAY ERRORES EN FACTURA
                if (listaFacturaTO.isEmpty()) {
                    // llenar contable
                    ConContableTO conContableTO = new ConContableTO();
                    conContableTO.setEmpCodigo(carCobrosTO.getUsrEmpresa());
                    conContableTO.setPerCodigo(carCobrosTO.getCobPeriodo());
                    conContableTO.setConNumero(carCobrosTO.getCobNumero());
                    conContableTO.setTipCodigo(tipDetalle);
                    carCobrosTO.setCobTipo(tipDetalle);
                    conContableTO.setConFecha(carCobrosTO.getCobFecha());
                    conContableTO.setConPendiente(false);
                    conContableTO.setConBloqueado(false);
                    conContableTO.setConAnulado(false);
                    conContableTO.setConGenerado(true);
                    conContableTO.setConConcepto(carCobrosTO.getConApellidosNombres());// nombre
                    // empleado
                    conContableTO.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");//
                    conContableTO.setConObservaciones(carCobrosTO.getCobObservaciones());
                    conContableTO.setUsrInsertaContable(carCobrosTO.getUsrCodigo());
                    conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                    ///// CREANDO Suceso (SE TERMINA DE LLENAR EN EL
                    ///// TRANSACCION)
                    susSuceso = "UPDATE";
                    susTabla = "cartera.car_cobros";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    ////// CREANDO CONTABLE
                    ConContable conContable = ConversionesContabilidad.convertirConContableTO_ConContable(conContableTO);
                    conContable.setConReferencia("cartera.car_cobros");
                    ////// CREANDO CarPagos
                    carCobrosTO.setCobTipo(tipDetalle);
                    carCobrosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                    CarCobros carCobros = ConversionesCar.convertirCarCobrosTO_CarCobros(carCobrosTO);
                    ////// CREANDO Lista de CarPagosDetalleCompras
                    List<CarCobrosDetalleVentas> carPagosDetalleVentasEnBaseDeDatos = cobrosDetalleVentasDao.listarDetallesVentasPorCobro(carCobrosTO.getUsrEmpresa(), carCobrosTO.getCobPeriodo(), tipDetalle, carCobrosTO.getCobNumero());
                    if (carPagosDetalleVentasEnBaseDeDatos != null && !carPagosDetalleVentasEnBaseDeDatos.isEmpty()) {
                        for (int i = 0; i < carPagosDetalleVentasEnBaseDeDatos.size(); i++) {
                            cobrosDetalleVentasDao.eliminar(carPagosDetalleVentasEnBaseDeDatos.get(i));
                        }
                    }
                    List<CarCobrosDetalleVentas> carCobrosDetalleVentas = new ArrayList<>(0);
                    for (CarCobrosDetalleVentasTO carCobrosDetalleVentasTO : carCobrosDetalleVentasTOs) {
                        carCobrosDetalleVentasTO.setDetSecuencial(0);
                        carCobrosDetalleVentas.add(ConversionesCar.convertirCarCobrosDetalleVentasTO_CarCobrosDetalleVentas(carCobrosDetalleVentasTO));
                    }
                    ////// CREANDO Lista de CarPagosDetalleForma
                    List<CarCobrosDetalleForma> carCobrosDetalleFormasEnBaseDeDatos = cobrosDetalleFormaDao.listarDetallesFormaPorCobro(carCobrosTO.getUsrEmpresa(), carCobrosTO.getCobPeriodo(), tipDetalle, carCobrosTO.getCobNumero());
                    if (carCobrosDetalleFormasEnBaseDeDatos != null && !carCobrosDetalleFormasEnBaseDeDatos.isEmpty()) {
                        for (int i = 0; i < carCobrosDetalleFormasEnBaseDeDatos.size(); i++) {
                            cobrosDetalleFormaDao.eliminar(carCobrosDetalleFormasEnBaseDeDatos.get(i));
                        }
                    }
                    List<CarCobrosDetalleForma> carCobrosDetalleForma = new ArrayList<>(0);
                    for (CarCobrosDetalleFormaTO carCobrosDetalleFormaTO : carCobrosDetalleFormaTOs) {
                        carCobrosDetalleForma.add(ConversionesCar.convertirCarCobrosDetalleFormaTO_CarCobrosDetalleForma(carCobrosDetalleFormaTO));
                    }
                    ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                    List<ConDetalle> listaConDetalle = new ArrayList<>(0);
                    ////// CREANDO Lista de CarCobrosDetalleAnticipos
                    List<CarCobrosDetalleAnticipos> carCobrosDetalleAnticiposEnBaseDeDatos = cobrosDetalleAnticiposDao.listarDetallesAnticipoPorCobro(carCobrosTO.getUsrEmpresa(), carCobrosTO.getCobPeriodo(), tipDetalle, carCobrosTO.getCobNumero());
                    if (carCobrosDetalleAnticiposEnBaseDeDatos != null && !carCobrosDetalleAnticiposEnBaseDeDatos.isEmpty()) {
                        for (int i = 0; i < carCobrosDetalleAnticiposEnBaseDeDatos.size(); i++) {
                            cobrosDetalleAnticiposDao.eliminar(carCobrosDetalleAnticiposEnBaseDeDatos.get(i));
                        }
                    }
                    List<CarCobrosDetalleAnticipos> carCobrosDetalleAnticipos = new ArrayList<>(0);
                    for (CarCobrosDetalleAnticiposTO carCobrosDetalleAnticiposTO : carCobrosDetalleAnticiposTOs) {
                        carCobrosDetalleAnticipos.add(ConversionesCar.convertirCarCobrosDetalleAnticiposTO_CarCobrosDetalleAnticipos(carCobrosDetalleAnticiposTO));
                    }

                    comprobar = false;
                    if (mensaje.isEmpty()) {// revisar si estan llenos
                        comprobar = contableDao.mayorizarTransaccionContableCartera(conContable, listaConDetalle, sisSuceso,
                                null, null, null, null, carCobros, carCobrosDetalleAnticipos, carCobrosDetalleVentas, carCobrosDetalleForma, null, null, sisInfoTO);
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
                                + carCobros.getCarCobrosPK().getCobNumero();

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
                carContableTO.setListaFacturaTO(lista);
            }
        } else {
            mensaje = "FNo se encuentra ningún periodo para la fecha que ingresó\nNo esta creado o esta cerrado...";
        }
        carContableTO.setMensaje(mensaje);
        return carContableTO;
    }

}
