package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.BonoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBono;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;

@Service
public class BonoServiceImpl implements BonoService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private BonoDao bonoDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private ContableService contableService;

    private Boolean comprobar = false;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhListaDetalleBonosLoteTO> getRhDetalleBonosLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        return bonoDao.getRhDetalleBonosLoteTO(empresa, periodo, tipo, numero);
    }

    @Override
    public List<RhListaDetalleBonosTO> getRhDetalleBonosTO(String empCodigo, String fechaDesde, String fechaHasta,
            String empCategoria, String empId, String estadoDeducible) throws Exception {
        return bonoDao.getRhDetalleBonosTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId, estadoDeducible);
    }

    @Override
    public List<RhListaConsolidadoBonosTO> getRhConsolidadoBonosTO(String empCodigo, String fechaDesde,
            String fechaHasta) throws Exception {
        return bonoDao.getRhConsolidadoBonosTO(empCodigo, fechaDesde, fechaHasta);
    }

    @Override
    public List<RhListaSaldoConsolidadoBonosTO> getRhSaldoConsolidadoBonosTO(String empCodigo, String fechaHasta)
            throws Exception {
        return bonoDao.getRhSaldoConsolidadoBonosTO(empCodigo, fechaHasta);
    }

    @Override
    public List<RhListaConsolidadoBonosHorasExtrasTO> listarConsolidadoBonosHorasExtras(String empCodigo, String fechaDesde, String fechaHasta) throws Exception {
        return bonoDao.listarConsolidadoBonosHorasExtras(empCodigo, fechaDesde, fechaHasta);
    }

    @Override
    public List<RhListaDetalleBonosTO> getRhListaDetalleBonosFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String estadoDeducible, String parametro)
            throws Exception {
        return bonoDao.getRhListaDetalleBonosFiltradoTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId,
                estadoDeducible, parametro);
    }

    private String validarDetalle(List<RhBono> listaBono) {
        BigDecimal cero = new BigDecimal("0.00");
        for (RhBono bono : listaBono) {
            if (bono.getBonoValor().compareTo(cero) != 0) {
                RhEmpleado empleado = empleadoDao.obtenerPorId(RhEmpleado.class,
                        bono.getRhEmpleado().getRhEmpleadoPK());
                if (bono.isBonoDeducible() && (empleado.getRhCategoria().getCtaGastoBonos() == null || empleado.getRhCategoria().getCtaGastoBonos().isEmpty())) {
                    return "FNo se puede ingresar el Bono para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres() + "; falta la cuenta 'GASTO BONO' ";
                }
                if (!bono.isBonoDeducible() && (empleado.getRhCategoria().getCtaGastoBonosNd() == null || empleado.getRhCategoria().getCtaGastoBonosNd().isEmpty())) {
                    return "FNo se puede ingresar el Bono para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres() + "; falta la cuenta 'GASTO BONO ND' ";
                }
                if (empleado.getRhCategoria().getCtaPorPagarBonos() == null || empleado.getRhCategoria().getCtaPorPagarBonos().isEmpty()) {
                    return "FNo se puede ingresar el Bono para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres() + "; falta la cuenta 'POR PAGAR BONO' ";
                }
            }
        }
        return null;
    }

    @Override
    public MensajeTO insertarModificarRhBono(ConContable conContable, List<RhBono> listaRhBono, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();

        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa())) != null) {
        } else if ((retorno = validarDetalle(listaRhBono)) != null) {
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConReversado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(true);
                if (listaRhBono.size() == 1) {
                    conContable.setConConcepto(empleadoDao.getRhEmpleadoApellidosNombres(
                            listaRhBono.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                            listaRhBono.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpId()));
                } else {
                    conContable.setConConcepto("BONOS POR LOTES");
                }
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("recursoshumanos.rh_bono");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }

            susSuceso = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
            susTabla = "recursoshumanos.rh_bono";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingresó" : "modificó";
            comprobar = bonoDao.insertarModificarRhBono(conContable, listaRhBono, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar el Bono.\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>Se " + mensaje + " el bono con la siguiente información:<br><br>"
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
    public List<RhBono> getListRhBono(ConContablePK conContablePK) throws Exception {
        return bonoDao.getListRhBono(conContablePK);
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        return bonoDao.getPermisoAcciones(conContablePK, fechaContable);
    }

    @Override
    public MensajeTO insertarRhBonoEscritorio(ConContable conContable, List<RhBono> listaRhBono, SisInfoTO sisInfoTO) throws Exception {
        boolean pendiente = conContable.getConPendiente();
        MensajeTO mensajeTO = insertarModificarRhBono(conContable, listaRhBono, sisInfoTO);
        if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
            if (!pendiente) {
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
        }
        return mensajeTO;
    }

    //Nuevo
    @Override
    public MensajeTO insertarRhBono(Date fechaHasta, String empresa, List<RhListaBonosLoteTO> listaRhListaBonosLoteTO, 
            String observacionesContable, List<ConAdjuntosContableWebTO> listadoImagenes, String codigoUnico, SisInfoTO sisInfoTO) throws Exception {
        List<RhBono> listaBono;
        ConContable contable;
        MensajeTO mensaje;
        listaBono = generarListaBonos(listaRhListaBonosLoteTO, empresa);
        contable = new ConContable(empresa, "", "C-BON", "");
        contable.setConObservaciones(observacionesContable);
        contable.setConFecha(fechaHasta);
        contable.setConPendiente(false);
        contable.setConCodigo(codigoUnico);
        mensaje = insertarModificarRhBono(contable, listaBono, sisInfoTO);
        if (mensaje.getMensaje() != null && mensaje.getMensaje().substring(0, 1).equals("T")) {
            contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
            contableService.actualizarImagenesContables(listadoImagenes, contable.getConContablePK());
        }
        return mensaje;
    }

    @Override
    public MensajeTO modificarRhBono(ConContable conContable, List<RhListaBonosLoteTO> listaRhBono, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        List<RhBono> listaBono = generarListaBonos(listaRhBono, sisInfoTO.getEmpresa());
        MensajeTO mensaje;
        mensaje = insertarModificarRhBono(conContable, listaBono, sisInfoTO);
        contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
        return mensaje;
    }

    private List<RhBono> generarListaBonos(List<RhListaBonosLoteTO> listaRhListaBonosLoteTO, String empresa) {
        List<RhBono> listaBono = new ArrayList<>();
        for (RhListaBonosLoteTO rhBonoEmpleadoTO : listaRhListaBonosLoteTO) {
            RhBono bono = new RhBono(rhBonoEmpleadoTO.getBonoSecuencial());
            bono.setBonoConcepto(rhBonoEmpleadoTO.getConcepto().getBcDetalle());
            bono.setBonoObservacion(rhBonoEmpleadoTO.getObservacion());
            bono.setBonoValor(rhBonoEmpleadoTO.getRhListaEmpleadoLoteTO().getPrValor());
            bono.setBonoDeducible(rhBonoEmpleadoTO.isDeducible());
            bono.setBonoAuxiliar(rhBonoEmpleadoTO.isBonoAuxiliar());
            bono.setPrdSector(new PrdSector(empresa, rhBonoEmpleadoTO.getRhListaEmpleadoLoteTO().getPrSector()));
            if (rhBonoEmpleadoTO.getPiscina() != null && rhBonoEmpleadoTO.getPiscina().getPisNumero().trim().compareToIgnoreCase("") != 0) {
                bono.setPrdPiscina(new PrdPiscina(empresa, rhBonoEmpleadoTO.getRhListaEmpleadoLoteTO().getPrSector(), rhBonoEmpleadoTO.getPiscina().getPisNumero()));
            }
            bono.setRhEmpleado(new RhEmpleado(empresa, rhBonoEmpleadoTO.getRhListaEmpleadoLoteTO().getPrId()));
            listaBono.add(bono);
        }
        return listaBono;
    }

}
