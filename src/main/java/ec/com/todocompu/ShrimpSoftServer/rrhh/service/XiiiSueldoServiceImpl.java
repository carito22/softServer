package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequeService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.XiiiSueldoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoXiiiSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoria;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;

@Service
public class XiiiSueldoServiceImpl implements XiiiSueldoService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private XiiiSueldoDao xiiiSueldoDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private ChequeService chequeService;
    private Boolean comprobar = false;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhFunXiiiSueldoCalcularTO> getRhFunCalcularXiiiSueldo(String empresa, String sector, String desde, String hasta) throws Exception {
        return xiiiSueldoDao.getRhFunCalcularXiiiSueldo(empresa, sector, desde, hasta);
    }

    @Override
    public List<RhFunXiiiSueldoConsultarTO> getRhFunConsultarXiiiSueldo(String empresa, String sector, String desde, String hasta) throws Exception {
        return xiiiSueldoDao.getRhFunConsultarXiiiSueldo(empresa, sector, desde, hasta);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXiiis(String empresa, String fecha, String cuenta) throws Exception {
        return xiiiSueldoDao.getRhFunPreavisoXiiis(empresa, fecha, cuenta);
    }

    private String validarDetalle(List<RhXiiiSueldo> rhXiiiSueldos, Date fechaAnticipo) {
        BigDecimal cero = new BigDecimal("0.00");
        for (RhXiiiSueldo xiii : rhXiiiSueldos) {
            if (xiii.getXiiiValor().compareTo(cero) > 0) {
                RhEmpleado empleado = empleadoDao.obtenerPorId(RhEmpleado.class,
                        xiii.getRhEmpleado().getRhEmpleadoPK());
                if (xiii.getConCuentas().getConCuentasPK().getCtaCodigo() == null
                        || xiii.getConCuentas().getConCuentasPK().getCtaCodigo().compareTo("") == 0) {
                    return "FNo ingreso la forma de pago para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres();
                }
//				else if (xiii.getXiiiValor().compareTo(
//						xiii.getXiiiBaseImponible().divide(new BigDecimal("12.00"), 2, RoundingMode.HALF_UP)) > 0)
//					return "FEl valor de xiii sueldo para el empleado " + empleado.getEmpApellidos() + " "
//							+ empleado.getEmpNombres() + " es mayor al total de ingresos dividido para 12";
            }
        }
        return null;
    }

    @Override
    public MensajeTO insertarModificarRhXiiiSueldo(ConContable conContable, List<RhXiiiSueldo> rhXiiiSueldos, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();

        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa())) != null) {
        } else if ((retorno = validarDetalle(rhXiiiSueldos, conContable.getConFecha())) != null) {
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConReversado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(true);
                if (rhXiiiSueldos.size() == 1) {
                    conContable.setConConcepto(empleadoDao.getRhEmpleadoApellidosNombres(
                            rhXiiiSueldos.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                            rhXiiiSueldos.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpId()));
                } else {
                    conContable.setConConcepto("XIII SUELDO POR LOTE");
                }
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("recursoshumanos.rh_xiii_sueldo");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }

            susSuceso = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
            susTabla = "recursoshumanos.rh_xiii_sueldo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingresó" : "modificó";
            comprobar = xiiiSueldoDao.insertarModificarRhXiiiSueldo(conContable, rhXiiiSueldos, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar el Xiii Sueldo.\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>Se " + mensaje + " el xiii sueldo con la siguiente información:<br><br>"
                        + "Período: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Motivo: <font size = 5>"
                        + conContable.getConContablePK().getConTipo() + "</font>.<br> Número: <font size = 5>"
                        + conContable.getConContablePK().getConNumero() + "</font>.<br>"
                        + conContable.getConContablePK().getConNumero() + ", "
                        + periodo.getSisPeriodoPK().getPerCodigo() + "</html>";
                mensajeTO.setFechaCreacion(conContable.getUsrFechaInserta().toString());
                mensajeTO.getMap().put("conContable", conContable);
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public List<RhXiiiSueldo> getListRhXiiiSueldo(ConContablePK conContablePK) throws Exception {
        return xiiiSueldoDao.getListRhXiiiSueldo(conContablePK);
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        return xiiiSueldoDao.getPermisoAcciones(conContablePK, fechaContable);
    }

    @Override
    public List<RhXiiiSueldoXiiiSueldoCalcular> getListaRhXiiiSueldoXiiiSueldoCalcular(String empresa, String sector, String desde, String hasta, boolean formaPagoSeleccionado) throws Exception {
        BigDecimal cero = new BigDecimal("0.00");
        List<RhFunXiiiSueldoCalcularTO> listado = xiiiSueldoDao.getRhFunCalcularXiiiSueldo(empresa, sector, desde, hasta);
        List<RhXiiiSueldoXiiiSueldoCalcular> listadoRhXiiiSueldoXiiiSueldoCalcular = new ArrayList<RhXiiiSueldoXiiiSueldoCalcular>();
        for (int i = 0; i < listado.size(); i++) {
            RhXiiiSueldoXiiiSueldoCalcular xiiiSueldoCalcular = new RhXiiiSueldoXiiiSueldoCalcular();
            RhFunXiiiSueldoCalcularTO xiiiSueldoCalcularTO = listado.get(i);
            RhXiiiSueldo rhXiiiSueldo = new RhXiiiSueldo();
            rhXiiiSueldo.setXiiiDiasLaborados(xiiiSueldoCalcularTO.getXiiiDiasLaborados());
            rhXiiiSueldo.setXiiiBaseImponible(xiiiSueldoCalcularTO.getXiiiTotalIngresos());//Inicializando ingresos calculados = ingresos reales por defecto
            rhXiiiSueldo.setXiiiValor(xiiiSueldoCalcularTO.getXiiiValorXiiiSueldo());
            rhXiiiSueldo.setXiiiAuxiliar(false);
            rhXiiiSueldo.setEmpCargo(xiiiSueldoCalcularTO.getXiiiCargo());
            rhXiiiSueldo.setEmpFechaIngreso(UtilsDate.fechaFormatoDate(xiiiSueldoCalcularTO.getXiiiFechaIngreso(), "yyyy-MM-dd"));
            rhXiiiSueldo.setPrdSector(new PrdSector(empresa, xiiiSueldoCalcularTO.getXiiiSector()));
            rhXiiiSueldo.setRhEmpleado(new RhEmpleado(empresa, xiiiSueldoCalcularTO.getXiiiId()));
            //rhXiiiSueldo.setConCuentas(new ConCuentas(empresa, xiiiSueldoCalcularTO.getFormaPago().getCtaCodigo())); AL GUARDAR
            rhXiiiSueldo.getRhEmpleado().setEmpApellidos(xiiiSueldoCalcularTO.getXiiiApellidos());
            rhXiiiSueldo.getRhEmpleado().setEmpNombres(xiiiSueldoCalcularTO.getXiiiNombres());
            RhCategoria categoria = new RhCategoria(new RhCategoriaPK(empresa, xiiiSueldoCalcularTO.getXiiiCategoria()));
            rhXiiiSueldo.getRhEmpleado().setRhCategoria(categoria);
            xiiiSueldoCalcular.setIngresosCalculados(xiiiSueldoCalcularTO.getXiiiTotalIngresos());
            xiiiSueldoCalcular.setRhXiiiSueldo(rhXiiiSueldo);
            xiiiSueldoCalcular.setIsValorValido(true);
            xiiiSueldoCalcular.setErrorEnDocumento(false);
            xiiiSueldoCalcular.setDocumentoRepetido(false);
            xiiiSueldoCalcular.setFormaPago(null);
            if (formaPagoSeleccionado) {
                xiiiSueldoCalcular.setIsFormaPagoValido(true);
            } else {
                if (rhXiiiSueldo.getXiiiBaseImponible().equals(cero)) {
                    xiiiSueldoCalcular.setIsFormaPagoValido(true);
                }
            }
            listadoRhXiiiSueldoXiiiSueldoCalcular.add(xiiiSueldoCalcular);
        }
        return listadoRhXiiiSueldoXiiiSueldoCalcular;

    }

    @Override
    public MensajeTO insertarRhXiiiSueldoXiiiSueldoCalcular(List<RhXiiiSueldoXiiiSueldoCalcular> lista, String observaciones, boolean aceptado, String empresa, Date fechaMaximo, RhXiiiSueldoPeriodoTO periodoSelccionado, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        BigDecimal cero = new BigDecimal("0.00");
        MensajeTO mensaje;
        ConContable contable = new ConContable(empresa, "", "C-BEN", "");
        contable.setConObservaciones(observaciones);
        contable.setConFecha(fechaMaximo);
        contable.setConPendiente(false);
        contable.setConCodigo(UtilsValidacion.generarSecuenciaAleatoria());

        List<RhXiiiSueldo> listaRhXiiiSueldo = new ArrayList<RhXiiiSueldo>();
        if (aceptado) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getRhXiiiSueldo().getXiiiValor().compareTo(cero) > 0) {
                    if (lista.get(i).getRhXiiiSueldo().getXiiiValor().compareTo(lista.get(i).getRhXiiiSueldo().getXiiiBaseImponible().divide(new BigDecimal("12.00"), 2, RoundingMode.HALF_UP)) > 0) {
                        lista.get(i).getRhXiiiSueldo().setXiiiBaseImponible(lista.get(i).getRhXiiiSueldo().getXiiiValor().multiply(new BigDecimal("12.00")));
                    }
                }
            }
        }
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getRhXiiiSueldo().getXiiiValor().compareTo(cero) > 0) {
                lista.get(i).getRhXiiiSueldo().setConCuentas(new ConCuentas(empresa, lista.get(i).getFormaPago().getCtaCodigo()));
                lista.get(i).getRhXiiiSueldo().setXiiiDesde(UtilsDate.fechaFormatoDate(periodoSelccionado.getXiiiDesde(), "yyyy-MM-dd"));
                lista.get(i).getRhXiiiSueldo().setXiiiHasta(UtilsDate.fechaFormatoDate(periodoSelccionado.getXiiiHasta(), "yyyy-MM-dd"));
            }
            listaRhXiiiSueldo.add(lista.get(i).getRhXiiiSueldo());
        }
        mensaje = insertarModificarRhXiiiSueldo(contable, listaRhXiiiSueldo, sisInfoTO);
        if (mensaje.getMensaje() != null && mensaje.getMensaje().substring(0, 1).equals("T")) {
            contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
            contableService.actualizarImagenesContables(listadoImagenes, contable.getConContablePK());
        }
        return mensaje;
    }

    @Override
    public String xiiiSueldoVerificarMayor(List<RhXiiiSueldoXiiiSueldoCalcular> lista) {
        BigDecimal cero = new BigDecimal("0.00");
        List<String> listaMensaje = new ArrayList<>();
        List<RhXiiiSueldo> listXiiiSueldo = new ArrayList<>();

        for (RhXiiiSueldoXiiiSueldoCalcular rhXiiiSueldoXiiiSueldoCalcular : lista) {
            RhXiiiSueldo xiiiSueldo = rhXiiiSueldoXiiiSueldoCalcular.getRhXiiiSueldo();
            String mensajeAux = null;

            if (xiiiSueldo.getXiiiValor().compareTo(cero) > 0) {
                if (xiiiSueldo.getXiiiValor().compareTo(xiiiSueldo.getXiiiBaseImponible().divide(new BigDecimal("12.00"), 2, RoundingMode.HALF_UP)) > 0) {
                    RhEmpleado empleado = xiiiSueldo.getRhEmpleado();
                    mensajeAux = "- " + empleado.getEmpApellidos() + " " + empleado.getEmpNombres() + "\n";
                    listaMensaje.add(mensajeAux);
                }
            }
            listXiiiSueldo.add(xiiiSueldo);
        }
        if (!listaMensaje.isEmpty()) {
            return construirMensaje(listaMensaje);
        } else {
            return "";
        }
    }

    private String construirMensaje(List<String> listaMensaje) {
        String mensaje = "El valor de XIII sueldo es mayor al total de ingresos dividido para 12, de los siguiente empleados\nDesea Continuar?\n\n";
        for (String string : listaMensaje) {
            mensaje += ("- " + string + "\n");
        }
        return mensaje;
    }

    @Override
    public MensajeTO modificarRhXiiiSueldoXiiiSueldoCalcular(ConContable contable, List<RhXiiiSueldoXiiiSueldoCalcular> lista, boolean aceptado, String empresa, SisInfoTO sisInfoTO) throws Exception {
        BigDecimal cero = new BigDecimal("0.00");
        MensajeTO mensaje;
        List<RhXiiiSueldo> listaRhXiiiSueldo = new ArrayList<RhXiiiSueldo>();
        if (aceptado) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getRhXiiiSueldo().getXiiiValor().compareTo(cero) > 0) {
                    if (lista.get(i).getRhXiiiSueldo().getXiiiValor().compareTo(lista.get(i).getRhXiiiSueldo().getXiiiBaseImponible().divide(new BigDecimal("12.00"), 2, RoundingMode.HALF_UP)) > 0) {
                        lista.get(i).getRhXiiiSueldo().setXiiiBaseImponible(lista.get(i).getRhXiiiSueldo().getXiiiValor().multiply(new BigDecimal("12.00")));
                    }
                }
                listaRhXiiiSueldo.add(lista.get(i).getRhXiiiSueldo());
            }
        }
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getRhXiiiSueldo().getXiiiValor().compareTo(cero) > 0) {
                lista.get(i).getRhXiiiSueldo().setConCuentas(new ConCuentas(empresa, lista.get(i).getFormaPago().getCtaCodigo()));
                // lista.get(i).getRhXiiiSueldo().setXiiiDesde(UtilsDate.fechaFormatoDate(periodoSelccionado.getXiiiDesde(), "yyyy-MM-dd"));
                // lista.get(i).getRhXiiiSueldo().setXiiiHasta(UtilsDate.fechaFormatoDate(periodoSelccionado.getXiiiHasta(), "yyyy-MM-dd"));
            }
            listaRhXiiiSueldo.add(lista.get(i).getRhXiiiSueldo());
        }
        mensaje = insertarModificarRhXiiiSueldo(contable, listaRhXiiiSueldo, sisInfoTO);
        return mensaje;
    }

}
