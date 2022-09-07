package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.ParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.XivSueldoDao;
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
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoXivSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoria;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.RoundingMode;
import java.util.ArrayList;

@Service
public class XivSueldoServiceImpl implements XivSueldoService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private XivSueldoDao xivSueldoDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private ParametrosDao parametrosDao;
    @Autowired
    private ContableService contableService;

    private Boolean comprobar = false;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhFunXivSueldoCalcularTO> getRhFunCalcularXivSueldo(String empresa, String sector, String desde, String hasta) throws Exception {
        return xivSueldoDao.getRhFunCalcularXivSueldo(empresa, sector, desde, hasta);
    }

    @Override
    public List<RhFunXivSueldoConsultarTO> getRhFunConsultarXivSueldo(String empresa, String sector, String desde, String hasta) throws Exception {
        return xivSueldoDao.getRhFunConsultarXivSueldo(empresa, sector, desde, hasta);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXivs(String empresa, String fecha, String cuenta) throws Exception {
        return xivSueldoDao.getRhFunPreavisoXivs(empresa, fecha, cuenta);
    }

    private String validarDetalle(List<RhXivSueldo> rhXivSueldos, Date fechaXivSueldo) {
        BigDecimal cero = new BigDecimal("0.00");
        BigDecimal smv = parametrosDao.getSalarioMinimoVital(fechaXivSueldo);
        for (RhXivSueldo xiv : rhXivSueldos) {
            if (xiv.getXivValor().compareTo(cero) > 0) {
                RhEmpleado empleado = empleadoDao.obtenerPorId(RhEmpleado.class, xiv.getRhEmpleado().getRhEmpleadoPK());
                xiv.setEmpSueldo(empleado.getEmpSueldoIess());
                if (xiv.getConCuentas().getConCuentasPK().getCtaCodigo() == null
                        || xiv.getConCuentas().getConCuentasPK().getCtaCodigo().compareTo("") == 0) {
                    return "FNo ingreso la forma de pago para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres();
                } else if (xiv.getXivValor().compareTo(smv) > 0) {
                    return "FEl valor de xiv sueldo para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres() + " es mayor al salario minimo vital " + smv;
                }
            }
        }
        return null;
    }

    @Override
    public MensajeTO insertarModificarRhXivSueldo(ConContable conContable, List<RhXivSueldo> rhXivSueldos, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();

        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa())) != null) {
        } else if ((retorno = validarDetalle(rhXivSueldos, conContable.getConFecha())) != null) {
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConReversado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(true);
                if (rhXivSueldos.size() == 1) {
                    conContable.setConConcepto(empleadoDao.getRhEmpleadoApellidosNombres(
                            rhXivSueldos.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                            rhXivSueldos.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpId()));
                } else {
                    conContable.setConConcepto("XIV SUELDO POR LOTE");
                }
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("recursoshumanos.rh_xiv_sueldo");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }

            susSuceso = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
            susTabla = "recursoshumanos.rh_xiv_sueldo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingresó" : "modificó";
            comprobar = xivSueldoDao.insertarModificarRhXivSueldo(conContable, rhXivSueldos, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar el xiv sueldo.\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>Se " + mensaje + " el xiv sueldo con la siguiente información:<br><br>"
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
    public List<RhXivSueldo> getListRhXivSueldo(ConContablePK conContablePK) throws Exception {
        return xivSueldoDao.getListRhXivSueldo(conContablePK);
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        return xivSueldoDao.getPermisoAcciones(conContablePK, fechaContable);
    }

    //nuevo
    @Override
    public List<RhXivSueldoXivSueldoCalcular> getListaRhXivSueldoXivSueldoCalcular(String empresa, String sector, String desde, String hasta, boolean formaPagoSeleccionado) throws Exception {
        BigDecimal cero = new BigDecimal("0.00");
        List<RhFunXivSueldoCalcularTO> listado = xivSueldoDao.getRhFunCalcularXivSueldo(empresa, sector, desde, hasta);
        List<RhXivSueldoXivSueldoCalcular> listadoRhXivSueldoXivSueldoCalcular = new ArrayList<>();
        for (int i = 0; i < listado.size(); i++) {
            RhFunXivSueldoCalcularTO RhFunXivSueldoCalcularTO = listado.get(i);
            RhXivSueldo rhXivSueldo = new RhXivSueldo();
            RhXivSueldoXivSueldoCalcular xivSueldoXivSueldoCalcular = new RhXivSueldoXivSueldoCalcular();
            //Empleado
            rhXivSueldo.setRhEmpleado(new RhEmpleado(empresa, RhFunXivSueldoCalcularTO.getXivId()));
            rhXivSueldo.getRhEmpleado().setEmpGenero(RhFunXivSueldoCalcularTO.getXivGenero());
            rhXivSueldo.getRhEmpleado().setEmpNombres(RhFunXivSueldoCalcularTO.getXivNombres());
            rhXivSueldo.getRhEmpleado().setEmpApellidos(RhFunXivSueldoCalcularTO.getXivApellidos());
            rhXivSueldo.getRhEmpleado().setEmpCargo(RhFunXivSueldoCalcularTO.getXivCargo());
            rhXivSueldo.getRhEmpleado().setRhCategoria(new RhCategoria(empresa, RhFunXivSueldoCalcularTO.getXivCategoria()));
            rhXivSueldo.getRhEmpleado().setEmpFechaPrimerIngreso(UtilsDate.fechaFormatoDate(RhFunXivSueldoCalcularTO.getXivFechaIngreso(), "yyyy-MM-dd"));//AUN NO SE...
            //Sector
            rhXivSueldo.setPrdSector(new PrdSector(empresa, RhFunXivSueldoCalcularTO.getXivSector()));
            //xiv sueldo
            rhXivSueldo.setEmpFechaIngreso(UtilsDate.fechaFormatoDate(RhFunXivSueldoCalcularTO.getXivFechaIngreso(), "yyyy-MM-dd"));
            rhXivSueldo.setXivDiasLaboradosEmpleado(RhFunXivSueldoCalcularTO.getXivDiasLaborados());//AUN NOSE..
            rhXivSueldo.setXivValor(RhFunXivSueldoCalcularTO.getXivValorXivSueldo());
            rhXivSueldo.setXivAuxiliar(false);
            rhXivSueldo.setXivBaseImponible(RhFunXivSueldoCalcularTO.getXivTotalIngresos());
            rhXivSueldo.setXivSalarioMinimoVital(RhFunXivSueldoCalcularTO.getXivSalarioMinimoVital());

            xivSueldoXivSueldoCalcular.setDiasLaborados(RhFunXivSueldoCalcularTO.getXivDiasLaborados());//Esto es editable , por defecto es igual  a xiv dias laborados empleados de xiv sueldo
            xivSueldoXivSueldoCalcular.setFormaPago(null);
            xivSueldoXivSueldoCalcular.setIsValorValido(true);
            xivSueldoXivSueldoCalcular.setIsFormaPagoValido(false);
            xivSueldoXivSueldoCalcular.setXivSalarioMinimoVital(RhFunXivSueldoCalcularTO.getXivSalarioMinimoVital());
            if (formaPagoSeleccionado) {
                xivSueldoXivSueldoCalcular.setIsFormaPagoValido(true);
            } else {
                if (rhXivSueldo.getXivDiasLaboradosEmpleado().equals(cero)) {
                    xivSueldoXivSueldoCalcular.setIsFormaPagoValido(true);
                }
            }
            xivSueldoXivSueldoCalcular.setRhXivSueldo(rhXivSueldo);
            listadoRhXivSueldoXivSueldoCalcular.add(xivSueldoXivSueldoCalcular);
        }
        return listadoRhXivSueldoXivSueldoCalcular;
    }

    @Override
    public MensajeTO insertarRhXivSueldoXivSueldoCalcular(ConContable conContable, List<RhXivSueldoXivSueldoCalcular> lista, String observaciones, boolean aceptado, String empresa, Date fechaMaximo, RhXivSueldoPeriodoTO periodoSelccionado, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        BigDecimal cero = new BigDecimal("0.00");
        MensajeTO mensaje;
        ConContable contable = new ConContable(empresa, "", "C-BEN", "");
        contable.setConObservaciones(observaciones);
        contable.setConFecha(fechaMaximo);
        contable.setConPendiente(false);
        contable.setConCodigo(UtilsValidacion.generarSecuenciaAleatoria());

        List<RhXivSueldo> listaRhXivSueldo = new ArrayList<RhXivSueldo>();
        if (aceptado) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getRhXivSueldo().getXivValor().compareTo(cero) > 0) {
                    if (lista.get(i).getRhXivSueldo().getXivValor().compareTo(lista.get(i).getRhXivSueldo().getXivBaseImponible().divide(new BigDecimal("12.00"), 2, RoundingMode.HALF_UP)) > 0) {
                        lista.get(i).getRhXivSueldo().setXivBaseImponible(lista.get(i).getRhXivSueldo().getXivValor().multiply(new BigDecimal("12.00")));
                    }
                }
            }
        }

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getRhXivSueldo().getXivValor().compareTo(cero) > 0) {
                lista.get(i).getRhXivSueldo().setConCuentas(new ConCuentas(empresa, lista.get(i).getFormaPago().getCtaCodigo()));
                lista.get(i).getRhXivSueldo().setXivDesde(UtilsDate.fechaFormatoDate(periodoSelccionado.getXivDesde(), "yyyy-MM-dd"));
                lista.get(i).getRhXivSueldo().setXivHasta(UtilsDate.fechaFormatoDate(periodoSelccionado.getXivHasta(), "yyyy-MM-dd"));
                lista.get(i).getRhXivSueldo().setXivCodigoMinisterial(lista.get(i).getFormaPago().getFpCodigoMinisterial());
                lista.get(i).getRhXivSueldo().setXivDiasCalculados(lista.get(i).getRhXivSueldo().getXivDiasLaboradosEmpleado());
                lista.get(i).getRhXivSueldo().setXivDiasLaboradosEmpleado(lista.get(i).getDiasLaborados());
            }
            listaRhXivSueldo.add(lista.get(i).getRhXivSueldo());
        }

        mensaje = insertarModificarRhXivSueldo(contable, listaRhXivSueldo, sisInfoTO);
        if (mensaje.getMensaje() != null && mensaje.getMensaje().substring(0, 1).equals("T")) {
            contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
            contableService.actualizarImagenesContables(listadoImagenes, contable.getConContablePK());
        }
        return mensaje;

    }

    @Override
    public MensajeTO modificarRhXivSueldoXivSueldoCalcular(ConContable contable, List<RhXivSueldoXivSueldoCalcular> lista, boolean aceptado, String empresa, SisInfoTO sisInfoTO) throws Exception {
        BigDecimal cero = new BigDecimal("0.00");
        MensajeTO mensaje;
        List<RhXivSueldo> listaRhXivSueldo = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getRhXivSueldo().getXivValor().compareTo(cero) > 0) {
                lista.get(i).getRhXivSueldo().setConCuentas(new ConCuentas(empresa, lista.get(i).getFormaPago().getCtaCodigo()));
                lista.get(i).getRhXivSueldo().setXivCodigoMinisterial(lista.get(i).getFormaPago().getFpCodigoMinisterial());
                lista.get(i).getRhXivSueldo().setXivDiasLaboradosEmpleado(lista.get(i).getDiasLaborados());
            }
            listaRhXivSueldo.add(lista.get(i).getRhXivSueldo());
        }
        mensaje = insertarModificarRhXivSueldo(contable, listaRhXivSueldo, sisInfoTO);
        return mensaje;

    }

}
