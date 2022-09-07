package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.FormaPagoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.VacacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhContableTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhDetalleVacionesPagadasGozadasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;
import java.util.Map;
import org.olap4j.impl.ArrayMap;

@Service
public class VacacionesServiceImpl implements VacacionesService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private FormaPagoDao formaPagoDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private VacacionesDao vacacionesDao;
    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private FormaPagoService formaPagoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private GenericSQLDao genericSQLDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    private boolean insertarRhVacaciones(RhVacaciones rhVacaciones, ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso, ConNumeracion conNumeracion, SisInfoTO sisInfoTO) throws Exception {
        return contableDao.insertarTransaccionContable(conContable, listaConDetalle, sisSuceso, conNumeracion,
                rhVacaciones, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                sisInfoTO);
    }

    @Override
    public List<RhDetalleVacionesPagadasGozadasTO> getRhDetalleVacacionesPagadasGozadasTO(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta, String tipo) throws Exception {
        return vacacionesDao.getRhDetalleVacacionesPagadasGozadasTO(empCodigo, empId, sector, fechaDesde, fechaHasta,
                tipo);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoVacaciones(String empresa, String fecha, String cuenta) throws Exception {
        return vacacionesDao.getRhFunPreavisoVacaciones(empresa, fecha, cuenta);
    }

    @Override
    public List<RhVacaciones> listarVacacionesEntreUnRol(RhRol rol) throws Exception {
        return vacacionesDao.listarVacacionesEntreUnRol(rol);
    }

    @Override
    public RhContableTO insertarRhVacacionesConContable(RhVacacionesTO rhVacacionesTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        RhContableTO rhContableTO = new RhContableTO();
        RhCtaFormaPagoTO rhCtaFormaPagoTO = new RhCtaFormaPagoTO();
        String tipDetalle = "C-VAC";
        mensaje = "F";
        String ctaRhFormasPago = null;
        String ctaRhVacaciones = "";
        int numerosDias = 0;
        BigDecimal valor = cero;
        // fecha ultimo sueldo
        RhVacaciones ultimaVacaciones = vacacionesDao.buscarSiExisteVacaciones(rhVacacionesTO.getEmpEmpresa(),
                rhVacacionesTO.getEmpId(), rhVacacionesTO.getVacHasta(), rhVacacionesTO.getVacSecuencial());
        String fechaHastaUltimaVacaciones = "";
        String fechaDesdeUltimaVacaciones = "";
        String datosContablesVacaciones = "";

        if (ultimaVacaciones != null) {
            fechaHastaUltimaVacaciones = ultimaVacaciones.getVacHasta() != null ? UtilsDate.fechaFormatoString(ultimaVacaciones.getVacHasta(), "yyyy-MM-dd") : "";
            fechaDesdeUltimaVacaciones = ultimaVacaciones.getVacDesde() != null ? UtilsDate.fechaFormatoString(ultimaVacaciones.getVacDesde(), "yyyy-MM-dd") : "";
            datosContablesVacaciones = "Contable: (" + ultimaVacaciones.getConPeriodo() + "|" + ultimaVacaciones.getConTipo() + "|" + ultimaVacaciones.getConNumero() + ").";
            if (fechaHastaUltimaVacaciones.isEmpty() || fechaDesdeUltimaVacaciones.isEmpty()) {
                comprobar = true;
            } else {
                comprobar = UtilsValidacion.fecha(rhVacacionesTO.getVacHasta(), "yyyy-MM-dd").getTime() < UtilsValidacion.fecha(fechaDesdeUltimaVacaciones, "yyyy-MM-dd").getTime()
                        || UtilsValidacion.fecha(rhVacacionesTO.getVacDesde(), "yyyy-MM-dd").getTime() > UtilsValidacion.fecha(fechaHastaUltimaVacaciones, "yyyy-MM-dd").getTime();
            }
        } else {
            comprobar = true;
        }

        if (UtilsValidacion.fecha(rhVacacionesTO.getConFecha(), "yyyy-MM-dd").getTime() > UtilsValidacion
                .fecha(rhVacacionesTO.getVacHasta(), "yyyy-MM-dd").getTime()) {
            numerosDias = UtilsValidacion.numeroDias("yyyy-MM-dd", rhVacacionesTO.getVacDesde(),
                    rhVacacionesTO.getVacHasta());
            if (numerosDias >= 364 && numerosDias <= 366) {
                if (comprobar) {
                    comprobar = false;
                    // periodo
                    List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList(1);
                    listaSisPeriodoTO = periodoService.getListaPeriodoTO(rhVacacionesTO.getEmpEmpresa());

                    for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                        if (UtilsValidacion.fecha(rhVacacionesTO.getConFecha(), "yyyy-MM-dd")
                                .getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd")
                                        .getTime()
                                && UtilsValidacion.fecha(rhVacacionesTO.getConFecha(), "yyyy-MM-dd")
                                        .getTime() <= UtilsValidacion
                                        .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()
                                && sisListaPeriodoTO.getPerCerrado() == false) {
                            comprobar = true;
                            rhVacacionesTO.setConPeriodo(sisListaPeriodoTO.getPerCodigo());
                            break;
                        }
                    }
                    if (comprobar) {
                        comprobar = false;
                        if (tipoDao.buscarTipoContable(rhVacacionesTO.getEmpEmpresa(), tipDetalle)) {
                            // llenar contable
                            ConContableTO conContableTO = new ConContableTO();
                            conContableTO.setEmpCodigo(rhVacacionesTO.getEmpEmpresa());
                            conContableTO.setPerCodigo(rhVacacionesTO.getConPeriodo());
                            conContableTO.setTipCodigo(tipDetalle);
                            rhVacacionesTO.setConTipo(tipDetalle);
                            conContableTO.setConFecha(rhVacacionesTO.getConFecha());
                            conContableTO.setConPendiente(false);
                            conContableTO.setConBloqueado(false);
                            conContableTO.setConAnulado(false);
                            conContableTO.setConGenerado(true); // deberia
                            conContableTO.setConReferencia("recursoshumanos.rh_vacaciones");
                            conContableTO.setConConcepto(rhVacacionesTO.getEmpApellidosNombres());// nombre empleado
                            conContableTO.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");//
                            conContableTO.setConObservaciones(rhVacacionesTO.getConObservaciones());
                            conContableTO.setUsrInsertaContable(rhVacacionesTO.getUsrCodigo());
                            conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                            ///// CREANDO UN SUCESO
                            susClave = rhVacacionesTO.getConPeriodo() + " " + rhVacacionesTO.getConTipo() + " " + rhVacacionesTO.getConNumero();
                            susSuceso = "INSERT";
                            susTabla = "recursoshumanos.rh_vacaciones";
                            ////// CREANDO NUMERACION
                            ConNumeracion conNumeracion = new ConNumeracion(
                                    new ConNumeracionPK(conContableTO.getEmpCodigo(), conContableTO.getPerCodigo(),
                                            conContableTO.getTipCodigo()));
                            ////// CREANDO CONTABLE
                            ConContable conContable = ConversionesContabilidad.convertirConContableTO_ConContable(conContableTO);
                            ////// CREANDO Prestamo
                            RhVacaciones rhVacaciones = ConversionesRRHH.convertirRhVacacionesTO_RhVacaciones(rhVacacionesTO);
                            rhVacaciones.setVacAuxiliar(false);
                            ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                            List<ConDetalle> listaConDetalle = new ArrayList(0);
                            ConDetalle conDetalle = null;
                            /////////////////////////////////////////////////////////////////
                            ctaRhVacaciones = empleadoDao.buscarCtaRhVacaciones(rhVacacionesTO.getEmpEmpresa(), rhVacacionesTO.getEmpId());
                            ConCuentas obj1 = cuentasDao.obtenerPorId(ConCuentas.class,
                                    new ConCuentasPK(rhVacacionesTO.getEmpEmpresa(),
                                            (ctaRhVacaciones == null) ? "" : ctaRhVacaciones));
                            /////////////////////////////////////////////////////////////////

                            rhCtaFormaPagoTO = formaPagoDao.buscarCtaRhFormaPago(rhVacacionesTO.getEmpEmpresa(),
                                    rhVacacionesTO.getVacFormaPago());
                            ctaRhFormasPago = rhCtaFormaPagoTO.getCtaCodigo();
                            ConCuentas obj2 = cuentasDao.obtenerPorId(ConCuentas.class,
                                    new ConCuentasPK(rhVacacionesTO.getEmpEmpresa(),
                                            (ctaRhFormasPago == null) ? "" : ctaRhFormasPago));

                            for (int i = 0; i < 2; i++) {
                                conDetalle = new ConDetalle();
                                conDetalle.setConContable(conContable);
                                conDetalle.setDetSecuencia(0L);
                                conDetalle.setDetDocumento("");
                                conDetalle.setPrdPiscina(null);
                                conDetalle.setDetValor(rhVacacionesTO.getVacValor());
                                conDetalle.setDetGenerado(true);
                                if (i == 0) {
                                    if (rhVacacionesTO.getVacGozadas()) {
                                        conDetalle.setConCuentas(obj2);// categoria
                                        conDetalle.setPrdSector(new PrdSector(rhVacacionesTO.getEmpEmpresa(),
                                                rhCtaFormaPagoTO.getSecCodigo()));
                                    } else {
                                        conDetalle.setConCuentas(obj1);// categoria
                                        conDetalle.setPrdSector(new PrdSector(rhVacacionesTO.getEmpEmpresa(),
                                                rhVacacionesTO.getSecCodigo()));
                                    }
                                    conDetalle.setDetDebitoCredito('D');
                                    conDetalle.setDetReferencia("VAC");//
                                    conDetalle.setDetOrden(i + 1);//
                                    listaConDetalle.add(i, conDetalle);
                                }
                                if (i == 1) {
                                    if (rhCtaFormaPagoTO.getCtaCodigo() != null) {
                                        conDetalle.setPrdSector(new PrdSector(rhVacacionesTO.getEmpEmpresa(),
                                                rhVacacionesTO.getSecCodigo()));
                                        conDetalle.setConCuentas(obj2);// forma
                                        // pago
                                        conDetalle.setDetDebitoCredito('C');
                                        conDetalle.setDetDocumento(rhVacacionesTO.getConDetDocumento());
                                        conDetalle.setDetReferencia("FP");//
                                        conDetalle.setDetOrden(i + 1);//
                                        valor = valor.add(conDetalle.getDetValor());
                                        listaConDetalle.add(i, conDetalle);
                                    }
                                }
                                conDetalle = null;
                            }

                            ctaRhVacaciones = ctaRhVacaciones == null ? "" : ctaRhVacaciones;
                            ctaRhFormasPago = ctaRhFormasPago == null ? "" : ctaRhFormasPago;
                            comprobar = false;
                            if (!ctaRhVacaciones.isEmpty() && !ctaRhFormasPago.isEmpty()) {// revisar
                                // si
                                // estan
                                // llenos
                                susDetalle = "Vacaciones a "
                                        + empleadoDao.getRhEmpleadoApellidosNombres(
                                                rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                                                rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpId())
                                        + " por $" + valor.toPlainString();
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                        susDetalle, sisInfoTO);
                                comprobar = insertarRhVacaciones(rhVacaciones, conContable, listaConDetalle,
                                        sisSuceso, conNumeracion, sisInfoTO);
                            } else if (ctaRhVacaciones.isEmpty()) {
                                mensaje = "FNo se encuentra la cuenta contable de Vacaciones...";
                            }
                            if (ctaRhFormasPago.isEmpty()) {
                                mensaje = "FNo se encuentra la cuenta contable de forma de pago...";
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
                                        + conContable.getConContablePK().getConNumero() + "</font>.</html>";

                                rhContableTO.setPerCodigo(sisPeriodo.getSisPeriodoPK().getPerCodigo());
                                rhContableTO.setTipCodigo(conTipo.getConTipoPK().getTipCodigo());
                                rhContableTO.setConNumero(conContable.getConContablePK().getConNumero());
                                String resultadoMayorizar = contableService.mayorizarDesmayorizarSql(new ConContablePK(rhVacacionesTO.getEmpEmpresa(),
                                        rhContableTO.getPerCodigo(), rhContableTO.getTipCodigo(), rhContableTO.getConNumero()), false, sisInfoTO);
                                mensaje = resultadoMayorizar.charAt(0) == 'F' ? resultadoMayorizar : mensaje;
                            }
                        } else {
                            mensaje = "FNo se encuentra tipo de contable...";
                        }
                    } else {
                        mensaje = "FNo se encuentra ningún periodo para la fecha que ingresó (" + rhVacacionesTO.getConFecha() + ")\nNo esta creado o esta cerrado...";
                    }
                } else {

                    mensaje = "FLas fechas de las vacaciones a registrar, generan un conflicto debido a que ya existe el siguiente registro de vacaciones: "
                            + "<br>Identificador: " + ultimaVacaciones.getVacSecuencial()
                            + "<br>Fecha desde: " + ultimaVacaciones.getVacDesde()
                            + "<br>Fecha hasta: " + ultimaVacaciones.getVacHasta()
                            + "<br>" + datosContablesVacaciones;
                }
            } else {
                mensaje = "FDebe de haber mínimo 364 días y máximo 366 días...";
            }
        } else {
            mensaje = "FFecha Hasta (" + rhVacacionesTO.getVacHasta() + ") debe ser menor a la del contable (" + rhVacacionesTO.getConFecha() + ").";
        }
        rhContableTO.setMensaje(mensaje);
        return rhContableTO;
    }

    @Override
    public RhContableTO modificarRhVacacionesConContable(RhVacacionesTO rhVacacionesTO, ConContable conContable, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        RhContableTO rhContableTO = new RhContableTO();
        RhCtaFormaPagoTO rhCtaFormaPagoTO = new RhCtaFormaPagoTO();
        mensaje = "F";
        String ctaRhFormasPago = null;
        String ctaRhVacaciones = "";
        int numerosDias = 0;
        BigDecimal valor = cero;
        // fecha ultimo sueldo
        RhVacaciones ultimaVacaciones = vacacionesDao.buscarSiExisteVacaciones(rhVacacionesTO.getEmpEmpresa(), rhVacacionesTO.getEmpId(), rhVacacionesTO.getVacHasta(), rhVacacionesTO.getVacSecuencial());
        String fechaHastaUltimaVacaciones = "";
        String fechaDesdeUltimaVacaciones = "";
        String datosContablesVacaciones = "";

        if (ultimaVacaciones != null) {
            fechaHastaUltimaVacaciones = ultimaVacaciones.getVacHasta() != null ? UtilsDate.fechaFormatoString(ultimaVacaciones.getVacHasta(), "yyyy-MM-dd") : "";
            fechaDesdeUltimaVacaciones = ultimaVacaciones.getVacHasta() != null ? UtilsDate.fechaFormatoString(ultimaVacaciones.getVacHasta(), "yyyy-MM-dd") : "";
            datosContablesVacaciones = "Contable: (" + ultimaVacaciones.getConPeriodo() + "|" + ultimaVacaciones.getConTipo() + "|" + ultimaVacaciones.getConNumero() + ").";
            if (fechaHastaUltimaVacaciones.isEmpty() || fechaDesdeUltimaVacaciones.isEmpty()) {
                comprobar = true;
            } else {
                comprobar = UtilsValidacion.fecha(rhVacacionesTO.getVacHasta(), "yyyy-MM-dd").getTime() < UtilsValidacion.fecha(fechaDesdeUltimaVacaciones, "yyyy-MM-dd").getTime()
                        || UtilsValidacion.fecha(rhVacacionesTO.getVacDesde(), "yyyy-MM-dd").getTime() > UtilsValidacion.fecha(fechaHastaUltimaVacaciones, "yyyy-MM-dd").getTime();
            }
        } else {
            comprobar = true;
        }
        if (UtilsValidacion.fecha(rhVacacionesTO.getConFecha(), "yyyy-MM-dd").getTime() > UtilsValidacion.fecha(rhVacacionesTO.getVacHasta(), "yyyy-MM-dd").getTime()) {
            numerosDias = UtilsValidacion.numeroDias("yyyy-MM-dd", rhVacacionesTO.getVacDesde(), rhVacacionesTO.getVacHasta());
            if (numerosDias >= 364 && numerosDias <= 366) {
                if (comprobar) {
                    comprobar = false;
                    // periodo
                    List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList(1);
                    listaSisPeriodoTO = periodoService.getListaPeriodoTO(rhVacacionesTO.getEmpEmpresa());
                    for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                        if (UtilsValidacion.fecha(rhVacacionesTO.getConFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                                && UtilsValidacion.fecha(rhVacacionesTO.getConFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()
                                && sisListaPeriodoTO.getPerCerrado() == false) {
                            comprobar = true;
                            rhVacacionesTO.setConPeriodo(sisListaPeriodoTO.getPerCodigo());
                            break;
                        }
                    }
                    if (comprobar) {
                        comprobar = false;
                        if (tipoDao.buscarTipoContable(rhVacacionesTO.getEmpEmpresa(), rhVacacionesTO.getConTipo())) {
                            ///// CREANDO UN SUCESO
                            susClave = rhVacacionesTO.getConPeriodo() + " " + rhVacacionesTO.getConTipo() + " " + rhVacacionesTO.getConNumero();
                            susSuceso = "UPDATE";
                            susTabla = "recursoshumanos.rh_vacaciones";
                            ////// CREANDO CONTABLE
                            conContable.setConFecha(UtilsValidacion.fecha(rhVacacionesTO.getConFecha(), "yyyy-MM-dd"));
                            conContable.setUsrCodigo(rhVacacionesTO.getUsrCodigo());
                            conContable.setConPendiente(true);

                            RhVacaciones rhVacaciones = ConversionesRRHH.convertirRhVacacionesTO_RhVacaciones(rhVacacionesTO);
                            rhVacaciones.setVacAuxiliar(false);
                            ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                            List<ConDetalle> listaConDetalle = new ArrayList(0);
                            ConDetalle conDetalle = null;
                            /////////////////////////////////////////////////////////////////
                            ctaRhVacaciones = empleadoDao.buscarCtaRhVacaciones(rhVacacionesTO.getEmpEmpresa(), rhVacacionesTO.getEmpId());
                            RhEmpleado empleado = empleadoDao.obtenerPorIdEvict(RhEmpleado.class, new RhEmpleadoPK(rhVacacionesTO.getEmpEmpresa(), rhVacacionesTO.getEmpId()));
                            ConCuentas obj1 = cuentasDao.obtenerPorId(ConCuentas.class,
                                    new ConCuentasPK(rhVacacionesTO.getEmpEmpresa(), (ctaRhVacaciones == null) ? "" : ctaRhVacaciones));
                            /////////////////////////////////////////////////////////////////
                            rhCtaFormaPagoTO = formaPagoDao.buscarCtaRhFormaPago(rhVacacionesTO.getEmpEmpresa(), rhVacacionesTO.getVacFormaPago());
                            ctaRhFormasPago = rhCtaFormaPagoTO.getCtaCodigo();
                            ConCuentas obj2 = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(rhVacacionesTO.getEmpEmpresa(), (ctaRhFormasPago == null) ? "" : ctaRhFormasPago));
                            for (int i = 0; i < 2; i++) {
                                conDetalle = new ConDetalle();
                                conDetalle.setConContable(conContable);
                                conDetalle.setDetSecuencia(0L);
                                conDetalle.setDetDocumento("");
                                conDetalle.setPrdPiscina(null);
                                conDetalle.setDetValor(rhVacacionesTO.getVacValor());
                                conDetalle.setDetGenerado(true);
                                if (i == 0) {
                                    if (rhVacacionesTO.getVacGozadas()) {
                                        conDetalle.setConCuentas(obj2);// categoria
                                        conDetalle.setPrdSector(new PrdSector(rhVacacionesTO.getEmpEmpresa(), rhCtaFormaPagoTO.getSecCodigo()));
                                    } else {
                                        conDetalle.setConCuentas(obj1);// categoria
                                        conDetalle.setPrdSector(empleado.getPrdSector());
                                    }
                                    conDetalle.setDetDebitoCredito('D');
                                    conDetalle.setDetReferencia("VAC");//
                                    conDetalle.setDetOrden(i + 1);//
                                    listaConDetalle.add(i, conDetalle);
                                }
                                if (i == 1) {
                                    if (rhCtaFormaPagoTO.getCtaCodigo() != null) {
                                        conDetalle.setPrdSector(empleado.getPrdSector());
                                        conDetalle.setConCuentas(obj2);// forma // pago
                                        conDetalle.setDetDebitoCredito('C');
                                        conDetalle.setDetDocumento(rhVacacionesTO.getConDetDocumento());
                                        conDetalle.setDetReferencia("FP");//
                                        conDetalle.setDetOrden(i + 1);//
                                        valor = valor.add(conDetalle.getDetValor());
                                        listaConDetalle.add(i, conDetalle);
                                    }
                                }
                                conDetalle = null;
                            }
                            ctaRhVacaciones = ctaRhVacaciones == null ? "" : ctaRhVacaciones;
                            ctaRhFormasPago = ctaRhFormasPago == null ? "" : ctaRhFormasPago;
                            comprobar = false;
                            if (!ctaRhVacaciones.isEmpty() && !ctaRhFormasPago.isEmpty()) {
                                susDetalle = "Vacaciones a "
                                        + empleadoDao.getRhEmpleadoApellidosNombres(
                                                rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                                                rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpId())
                                        + " por $" + valor.toPlainString();
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                comprobar = contableDao.modificarRhVacaciones(rhVacaciones, conContable, listaConDetalle, sisSuceso, sisInfoTO);
                            } else if (ctaRhVacaciones.isEmpty()) {
                                mensaje = "FNo se encuentra la cuenta contable de Vacaciones...";
                            }
                            if (ctaRhFormasPago.isEmpty()) {
                                mensaje = "FNo se encuentra la cuenta contable de forma de pago...";
                            }
                            if (comprobar) {
                                SisPeriodo sisPeriodo = periodoService.buscarPeriodo(conContable.getConContablePK().getConEmpresa(), conContable.getConContablePK().getConPeriodo());
                                ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(
                                        conContable.getConContablePK().getConEmpresa(), conContable.getConContablePK().getConTipo()));
                                mensaje = "T<html>Se actualizó el contable con la siguiente información:<br><br>"
                                        + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                        + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                        + "</font>.<br> Número: <font size = 5>"
                                        + conContable.getConContablePK().getConNumero() + "</font>.</html>";
                                rhContableTO.setPerCodigo(sisPeriodo.getSisPeriodoPK().getPerCodigo());
                                rhContableTO.setTipCodigo(conTipo.getConTipoPK().getTipCodigo());
                                rhContableTO.setConNumero(conContable.getConContablePK().getConNumero());
                            }
                        } else {
                            mensaje = "FNo se encuentra tipo de contable...";
                        }
                    } else {
                        mensaje = "FNo se encuentra ningún periodo para la fecha que ingresó (" + rhVacacionesTO.getConFecha() + ")\nNo esta creado o esta cerrado...";
                    }
                } else {
                    mensaje = "FLas fechas de las vacaciones a registrar, generan un conflicto debido a que ya existe el siguiente registro de vacaciones: "
                            + "<br>Identificador: " + ultimaVacaciones.getVacSecuencial()
                            + "<br>Fecha desde: " + ultimaVacaciones.getVacDesde()
                            + "<br>Fecha hasta: " + ultimaVacaciones.getVacHasta()
                            + "<br>" + datosContablesVacaciones;
                }
            } else {
                mensaje = "FDebe de haber mínimo 364 días y máximo 366 días...";
            }
        } else {
            mensaje = "FFecha Hasta (" + rhVacacionesTO.getVacHasta() + ") debe ser menor a la del contable (" + rhVacacionesTO.getConFecha() + ").";
        }
        rhContableTO.setMensaje(mensaje);
        return rhContableTO;
    }

    @Override
    public List<RhVacaciones> getListRhVacaciones(ConContablePK conContablePK) throws Exception {
        return vacacionesDao.getListRhVacaciones(conContablePK);
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudVacaciones(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<RhComboFormaPagoTO> listaFormaPago = formaPagoService.getComboFormaPagoTO(empresa);
        Date fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo periodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);

        if (listaFormaPago != null && !listaFormaPago.isEmpty()) {
            respuesta.put("listaFormaPago", listaFormaPago);
        }
        respuesta.put("fechaActual", fechaActual);
        respuesta.put("periodoAbierto", (periodo != null ? (!periodo.getPerCerrado()) : false));
        return respuesta;
    }

    @Override
    public String buscarFechaHastaRhVacaciones(String empCodigo, String empId) throws Exception {
        RhVacaciones vacaciones = vacacionesDao.buscarFechaRhVacaciones(empCodigo, empId);
        if (vacaciones != null) {
            return vacaciones.getVacHasta() != null ? UtilsDate.fechaFormatoString(vacaciones.getVacHasta(), "yyyy-MM-dd") : "";
        } else {
            return "";
        }
    }

    @Override
    public BigDecimal obtenerValorProvisionadoRoles(String fechaDesde, String fechaHasta, String empresa, String empId) throws Exception {
        return vacacionesDao.obtenerValorProvisionadoRoles(fechaDesde, fechaHasta, empresa, empId);
    }

    @Override
    public List<RhVacaciones> listarRhVacacionesGozadas(String empresa, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception {
        empId = empId == null || empId.compareToIgnoreCase("") == 0 ? null : "'" + empId + "'";
        sector = sector == null || sector.compareToIgnoreCase("") == 0 ? null : "'" + sector + "'";
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        empresa = empresa == null ? null : "'" + empresa + "'";
        String pFechaDesde = fechaDesde;
        String pFechaHasta = fechaHasta;
        String sql = "SELECT * from recursoshumanos.fun_listado_vacaciones(" + empresa + ", " + "" + sector + ", "
                + empId + ", " + pFechaDesde + ", " + pFechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, RhVacaciones.class);
    }

    @Override
    public RhVacaciones buscarRhVacacionesPorPeriodoTrabajo(String empCodigo, String empId, String desde, String hasta) throws Exception {
        return vacacionesDao.buscarRhVacacionesPorPeriodoTrabajo(empCodigo, empId, desde, hasta);
    }

}
