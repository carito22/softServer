package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequeService;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.DetalleService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.TipoService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.ExcepcionServicio;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.PrestamoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.UtilsContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetallePrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoIndividualAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteAnticipoPrestamoXIIISueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.olap4j.impl.ArrayMap;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private PrestamoDao prestamoDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private ChequeService chequeService;
    @Autowired
    private DetalleService detalleService;
    @Autowired
    private TipoService tipoService;
    @Autowired
    private PrestamoMotivoService prestamoMotivoService;
    @Autowired
    private FormaPagoService formaPagoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;

    private Boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public BigDecimal getRhRolSaldoPrestamo(String empCodigo, String empId, String fechaDesde, String fechaHasta) throws Exception {
        return prestamoDao.getRhRolSaldoPrestamo(empCodigo, empId, fechaDesde, fechaHasta);
    }

    @Override
    public List<RhListaDetalleAnticiposPrestamosTO> getRhDetalleAnticiposPrestamosTO(String empCodigo, String fechaDesde, String fechaHasta, String empCategoria, String empId, String parametro) throws Exception {
        return prestamoDao.getRhDetalleAnticiposPrestamosTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId,
                parametro);
    }

    @Override
    public List<RhListaDetallePrestamosTO> getRhDetallePrestamosTO(String empCodigo, String fechaDesde, String fechaHasta, String empCategoria, String empId) throws Exception {
        return prestamoDao.getRhDetallePrestamosTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId);
    }

    @Override
    public List<RhListaConsolidadoAnticiposPrestamosTO> getRhConsolidadoAnticiposPrestamosTO(String empCodigo, String fechaDesde, String fechaHasta, String empCategoria, String empId) throws Exception {
        return prestamoDao.getRhConsolidadoAnticiposPrestamosTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId);
    }

    @Override
    public List<RhListaSaldoConsolidadoAnticiposPrestamosTO> getRhSaldoConsolidadoAnticiposPrestamosTO(String empCodigo, String fechaHasta) throws Exception {
        return prestamoDao.getRhSaldoConsolidadoAnticiposPrestamosTO(empCodigo, fechaHasta);
    }

    @Override
    public List<RhListaSaldoIndividualAnticiposPrestamosTO> getRhSaldoIndividualAnticiposPrestamosTO(String empCodigo, String fechaDesde, String fechaHasta, String empId, String tipo) throws Exception {
        return prestamoDao.getRhSaldoIndividualAnticiposPrestamosTO(empCodigo, fechaDesde, fechaHasta, empId, tipo);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoPrestamos(String empresa, String fecha, String cuenta) throws Exception {
        return prestamoDao.getRhFunPreavisoPrestamos(empresa, fecha, cuenta);
    }

    @Override
    public MensajeTO insertarModificarRhPrestamo(ConContable conContable, RhPrestamo rhPrestamo, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();

        if (UtilsValidacion.isFechaSuperior(conContable.getConFecha())) {
            retorno = "FLa fecha que ingreso es superior a la fecha actual del servidor. Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy");
        } else if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(),
                conContable.getConContablePK().getConEmpresa())) != null) {
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(),
                    conContable.getConContablePK().getConEmpresa());

            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConReversado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(true);
                conContable.setConConcepto(empleadoDao.getRhEmpleadoApellidosNombres(
                        rhPrestamo.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                        rhPrestamo.getRhEmpleado().getRhEmpleadoPK().getEmpId()));
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("recursoshumanos.rh_prestamo");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }

            susSuceso = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT"
                    : "UPDATE";
            susTabla = "recursoshumanos.rh_prestamo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingreso"
                    : "modifico";
            comprobar = prestamoDao.insertarModificarRhPrestamo(conContable, rhPrestamo, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar el Prestamo.\nIntente de nuevo o contacte con el administrador";
            } else {
                contableService.actualizarImagenesContables(listadoImagenes, conContable.getConContablePK());
                retorno = "T<html>Se " + mensaje + " el prestamo con la siguiente informacion:<br><br>"
                        + "Periodo: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Motivo: <font size = 5>"
                        + conContable.getConContablePK().getConTipo() + "</font>.<br> Numero: <font size = 5>"
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
    public MensajeTO insertarModificarRhPrestamoWeb(ConContable conContable, RhPrestamo rhPrestamo, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        boolean pendiente = conContable.getConPendiente();
        MensajeTO mensaje = insertarModificarRhPrestamo(conContable, rhPrestamo, listadoImagenes, sisInfoTO);
        if (!pendiente && mensaje.getMensaje() != null && mensaje.getMensaje().substring(0, 1).equals("T")) {
            String resultadoMayorizar = contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            mensaje.setMensaje(resultadoMayorizar.charAt(0) == 'F' ? resultadoMayorizar : mensaje.getMensaje());
        }
        return mensaje;
    }

    @Override
    public MensajeTO postIngresarPrestamo(RhPrestamo rhPrestamo, RhComboFormaPagoTO rhComboFormaPagoTO, Date fechaHasta, List<ConAdjuntosContableWebTO> listadoImagenes, String codigoUnico, SisInfoTO usuario) throws Exception {
        MensajeTO mensaje = new MensajeTO();
        mensaje.setMensaje("FNo se ingreso prestamo");
        if (rhComboFormaPagoTO.getValidar() && chequeService.isExisteChequeAimprimir(usuario.getEmpresa(), rhComboFormaPagoTO.getCtaCodigo(), rhPrestamo.getPreDocumento(), null)) {
            String texto = ".Se está intentando ingresar el siguiente préstamo con un cheque existente\n";
            texto += "\n";
            texto += ("- Id: " + rhPrestamo.getRhEmpleado().getRhEmpleadoPK().getEmpId() + "\n");
            texto += "\n";
            texto += "Transacción cancelada\n";
            mensaje = new MensajeTO(false, texto, "");
        } else {
            ConContable contable = generarConContable(usuario.getEmpresa(), fechaHasta, rhPrestamo.getPreObservaciones().toUpperCase());
            // generar prestamo
            RhPrestamo prestamo = new RhPrestamo();
            prestamo.setPreDocumento(rhPrestamo.getPreDocumento());
            prestamo.setMotEmpresa(rhPrestamo.getMotEmpresa());
            prestamo.setMotDetalle(rhPrestamo.getMotDetalle());
            prestamo.setPreObservaciones(rhPrestamo.getPreObservaciones().toUpperCase());
            prestamo.setPreAuxiliar(rhPrestamo.isPreAuxiliar());
            prestamo.setPreValor(rhPrestamo.isPreAuxiliar() ? rhPrestamo.getPreValor().multiply(new BigDecimal("-1")) : rhPrestamo.getPreValor());
            prestamo.setPreNumeroPagos(rhPrestamo.getPreNumeroPagos());
            prestamo.setConCuentas(new ConCuentas(usuario.getEmpresa(), rhComboFormaPagoTO.getCtaCodigo()));
            prestamo.setPrdSector(new PrdSector(usuario.getEmpresa(), rhPrestamo.getPrdSector().getPrdSectorPK().getSecCodigo()));
            prestamo.setRhEmpleado(new RhEmpleado(usuario.getEmpresa(), rhPrestamo.getRhEmpleado().getRhEmpleadoPK().getEmpId()));
            contable.setConCodigo(codigoUnico);
            mensaje = insertarModificarRhPrestamoWeb(contable, prestamo, listadoImagenes, usuario);
        }
        return mensaje;
    }

    @Override
    public ConContable generarConContable(String empresa, Date fecha, String observacion) throws Exception {
        String secuencia = UtilsValidacion.generarSecuenciaAleatoria();
        ConContable contable = new ConContable(empresa, "", "C-PRE", "");
        contable.setConFecha(UtilsDate.fechaFormatoDate(fecha, "dd-MM-yyyy"));
        contable.setConPendiente(false);
        contable.setConCodigo(secuencia);
        contable.setConObservaciones(observacion.toUpperCase());
        return contable;
    }

    public List<ConContableReporteTO> generarListaConContableReporteTO(List<ConContablePK> listContable, SisInfoTO usuario) throws Exception {
        List<ConContableReporteTO> list = new ArrayList<ConContableReporteTO>();
        try {
            Short estGrupo1;
            Short estGrupo2;
            Short estGrupo3;
            Short estGrupo4;
            Short estGrupo5;
            Short estGrupo6;
            Map<String, Short> estructura = UtilsContable.obtenerEstructura(listContable.get(0).getConEmpresa(), usuario);
            estGrupo1 = estructura.get("estGrupo1");
            estGrupo2 = estructura.get("estGrupo2");
            estGrupo3 = estructura.get("estGrupo3");
            estGrupo4 = estructura.get("estGrupo4");
            estGrupo5 = estructura.get("estGrupo5");
            estGrupo6 = estructura.get("estGrupo6");
            for (ConContablePK conPK : listContable) {

                ConContableTO contable = contableService.getListaConContableTO(conPK.getConEmpresa(), conPK.getConPeriodo(), conPK.getConTipo(), conPK.getConNumero()).get(0);
                List<ConDetalleTablaTO> detalleContable = detalleService.getListaConDetalleTO(conPK.getConEmpresa(), conPK.getConPeriodo(), conPK.getConTipo(), conPK.getConNumero());
                String tipoDetalle = tipoService.getConTipoTO(conPK.getConEmpresa(), contable.getTipCodigo()).getTipDetalle();
                List<String> cuentaPadre = new ArrayList<String>();
                for (ConDetalleTablaTO dc : detalleContable) {
                    String cuenta = dc.getCtaCodigo().trim().substring(0,
                            ((estGrupo2 == 0 ? 0 : estGrupo1) + (estGrupo3 == 0 ? 0 : estGrupo2)
                            + (estGrupo4 == 0 ? 0 : estGrupo3) + (estGrupo5 == 0 ? 0 : estGrupo4)
                            + (estGrupo6 == 0 ? 0 : estGrupo5)));
                    String nombre = UtilsContable.buscarDetalleContablePadre(cuenta, conPK.getConEmpresa(), usuario);
                    cuentaPadre.add(cuenta + " | " + nombre);
                }
                list.add(new ConContableReporteTO(tipoDetalle, cuentaPadre, contable, detalleContable));
            }
        } catch (Exception e) {
            throw new ExcepcionServicio(e.getMessage());
        }
        return list;
    }

    @Override
    public List<RhPrestamo> getListRhPrestamo(ConContablePK conContablePK) throws Exception {
        return prestamoDao.getListRhPrestamo(conContablePK);
    }

    public List<ReporteAnticipoPrestamoXIIISueldo> postGenerarReporteComprobantePrestamo(ConContable con, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, RhPrestamo rhPrestamo,
            RhComboFormaPagoTO rhComboFormaPagoTO, Date fechaHasta) throws Exception {
        List<ReporteAnticipoPrestamoXIIISueldo> lista = new ArrayList<>();
        if (con != null && usuarioEmpresaReporteTO.getEmpCodigo() != null) {
            RhPrestamo rhPrestamo_ = getListRhPrestamo(con.getConContablePK()).get(0);
            lista.add(new ReporteAnticipoPrestamoXIIISueldo(
                    con.getConContablePK().getConEmpresa() + " | " + con.getConContablePK().getConPeriodo() + " | " + con.getConContablePK().getConTipo() + " | " + con.getConContablePK().getConNumero(),
                    UtilsDate.fechaFormatoString(con.getConFecha(), "dd-MM-yyyy"),
                    rhPrestamo_.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    rhPrestamo_.getRhEmpleado().getEmpApellidos() + " " + rhPrestamo_.getRhEmpleado().getEmpNombres(),
                    rhPrestamo_.getRhEmpleado().getEmpCargo(),
                    rhPrestamo_.getRhEmpleado().getPrdSector().getSecNombre(),
                    rhPrestamo_.getPreValor(),
                    rhPrestamo_.getConCuentas().getCtaDetalle(),
                    rhPrestamo_.getPreDocumento(),
                    rhPrestamo_.getPreObservaciones() == null ? "" : rhPrestamo_.getPreObservaciones().toUpperCase()));
        } else {
            lista.add(new ReporteAnticipoPrestamoXIIISueldo(con.getConContablePK().getConEmpresa() + " | " + con.getConContablePK().getConPeriodo() + " | " + con.getConContablePK().getConTipo() + " | " + con.getConContablePK().getConNumero(),
                    UtilsDate.fechaFormatoString(fechaHasta, "dd-MM-yyyy"),
                    rhPrestamo.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    rhPrestamo.getRhEmpleado().getEmpApellidos() + " " + rhPrestamo.getRhEmpleado().getEmpNombres(),
                    "",
                    "",
                    rhPrestamo.getPreValor().add(new BigDecimal("0.00")),
                    rhComboFormaPagoTO.getFpDetalle(),
                    rhPrestamo.getPreDocumento(),
                    rhPrestamo.getPreObservaciones().toUpperCase()));
        }
        return lista;
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        return prestamoDao.getPermisoAcciones(conContablePK, fechaContable);
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudPrestamos(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivoMotivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivoMotivo"));

        List<RhPrestamoMotivo> listaPrestamo = prestamoMotivoService.getListaRhPrestamoMotivos(empresa, inactivoMotivo);
        List<RhComboFormaPagoTO> listaFormaPago = formaPagoService.getComboFormaPagoTO(empresa);
        Date fechaActual = sistemaWebServicio.getFechaActual();

        if (listaFormaPago != null && !listaFormaPago.isEmpty() && listaPrestamo != null && !listaPrestamo.isEmpty()) {
            respuesta.put("listaPrestamo", listaPrestamo);
            respuesta.put("listaFormaPago", listaFormaPago);
        }
        respuesta.put("fechaActual", fechaActual);
        return respuesta;
    }
}
