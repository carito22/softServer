package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.DetalleService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.EstructuraService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.TipoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.AnticipoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.AnticipoMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.CategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.FormaPagoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.RhPreavisoAnticiposPrestamosSueldoMachalaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhOrdenBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoGuayaquilTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoPichinchaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.ArrayList;
import java.util.Map;
import org.olap4j.impl.ArrayMap;

@Service
public class AnticipoServiceImpl implements AnticipoService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private AnticipoDao anticipoDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private CategoriaDao categoriaDao;
    @Autowired
    private AnticipoMotivoDao anticipoMotivoDao;
    @Autowired
    private FormaPagoDao formaPagoDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private DetalleService detalleService;
    @Autowired
    private EstructuraService estructuraService;
    @Autowired
    private CuentasService cuentasService;
    @Autowired
    private TipoService tipoService;

    private Boolean comprobar = false;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoAnticiposBolivariano(String empresa, String fecha,
            String cuenta, String tipoPreAviso, String servicio, String sector, boolean sinCuenta) throws Exception {
        return anticipoDao.getRhFunPreavisoAnticiposBolivariano(empresa, fecha, cuenta, tipoPreAviso, servicio, sector, sinCuenta);

    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> getRhFunPreavisoAnticiposPichincha(String empresa,
            String fecha, String cuenta, String tipo, String banco, String sector) throws Exception {
        return anticipoDao.getRhFunPreavisoAnticiposPichincha(empresa, fecha, cuenta, tipo, banco, sector);
    }

    //anticiposMachala
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> getRhFunPreavisoAnticiposMachala(String empresa,
            String fecha, String cuenta, String tipo, String banco, String sector) throws Exception {
        return anticipoDao.getRhFunPreavisoAnticiposMachala(empresa, fecha, cuenta, tipo, banco, sector);
    }
//guayaquil

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> getRhFunPreavisoAnticiposGuayaquil(String empresa, String fecha, String cuenta, String tipo, String banco, String sector) throws Exception {
        return anticipoDao.getRhFunPreavisoAnticiposGuayaquil(empresa, fecha, cuenta, tipo, banco, sector);
    }

    @Override
    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String formaPago) throws Exception {
        return anticipoDao.getRhDetalleAnticiposTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId, formaPago);
    }

    @Override
    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String formaPago, String parametro) throws Exception {
        return anticipoDao.getRhDetalleAnticiposFiltradoTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId,
                formaPago, parametro);
    }

    @Override
    public List<RhListaDetalleAnticiposLoteTO> getRhDetalleAnticiposLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        return anticipoDao.getRhDetalleAnticiposLoteTO(empresa, periodo, tipo, numero);
    }

    private String validarDetalle(List<RhAnticipo> listAnticipo) {
        BigDecimal cero = new BigDecimal("0.00");
        for (RhAnticipo ant : listAnticipo) {
            if (ant.getAntValor().compareTo(cero) != 0) {
                RhEmpleado empleado = empleadoDao.obtenerPorId(RhEmpleado.class, ant.getRhEmpleado().getRhEmpleadoPK());
                if (empleado.getRhCategoria().getCtaAnticipos() == null || empleado.getRhCategoria().getCtaAnticipos().isEmpty()) {
                    return "FNo se puede ingresar el Anticipo para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres() + "; falta la cuenta 'ANTICIPOS' ";
                }

                if (ant.getConCuentas().getConCuentasPK().getCtaCodigo() == null || ant.getConCuentas().getConCuentasPK().getCtaCodigo().compareTo("") == 0) {
                    return "FNo ingreso la forma de pago para el empleado " + empleado.getEmpApellidos() + " " + empleado.getEmpNombres();
                }
                if (empleado.getEmpFechaUltimoSueldo() == null) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public MensajeTO insertarModificarRhAnticipo(ConContable conContable, List<RhAnticipo> listaRhAnticipo, SisInfoTO sisInfoTO) throws Exception {
        boolean pendiente = conContable.getConPendiente();
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();

        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(),
                conContable.getConContablePK().getConEmpresa())) != null) {
        } else if ((retorno = validarDetalle(listaRhAnticipo)) != null) {
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
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("recursoshumanos.rh_anticipo");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }
            if (listaRhAnticipo.size() == 1) {
                conContable.setConConcepto(empleadoDao.getRhEmpleadoApellidosNombres(
                        listaRhAnticipo.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                        listaRhAnticipo.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpId()));
            } else {
                conContable.setConConcepto("ANTICIPOS POR LOTES");
            }

            susSuceso = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT"
                    : "UPDATE";
            susTabla = "recursoshumanos.rh_anticipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingresó"
                    : "modificó";
            comprobar = anticipoDao.insertarModificarRhAnticipo(conContable, listaRhAnticipo, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar el Anticipo.\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>Se " + mensaje + " el anticipo con la siguiente información:<br><br>"
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
    public List<RhAnticipo> getListRhAnticipo(ConContablePK conContablePK) throws Exception {
        return anticipoDao.getListRhAnticipo(conContablePK);
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        return anticipoDao.getPermisoAcciones(conContablePK, fechaContable);
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudAnticipos(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<PrdListaSectorTO> sectores = sectorDao.getListaSector(empresa, true);
        List<RhComboCategoriaTO> categorias = categoriaDao.getComboCategoria(empresa);
        List<RhAnticipoMotivo> motivos = anticipoMotivoDao.getListaRhAnticipoMotivo(empresa);
        List<RhComboFormaPagoTO> formasDePago = formaPagoDao.getComboFormaPago(empresa);
        Date fechaActual = sistemaWebServicio.getFechaActual();

        respuesta.put("sectores", sectores);
        respuesta.put("categorias", categorias);
        respuesta.put("motivos", motivos);
        respuesta.put("formasDePago", formasDePago);
        respuesta.put("fechaActual", fechaActual);

        return respuesta;
    }

    @Override
    public MensajeTO insertarRhAnticipoEscritorio(ConContable conContable, List<RhAnticipo> listaRhAnticipo, SisInfoTO sisInfoTO) throws Exception {
        boolean pendiente = conContable.getConPendiente();
        MensajeTO mensajeTO = insertarModificarRhAnticipo(conContable, listaRhAnticipo, sisInfoTO);
        if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
            if (!pendiente) {
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
        }
        return mensajeTO;
    }

    @Override
    public MensajeTO insertarRhAnticipo(String observacionesContable, List<RhAnticipo> listaRhAnticipo, String fechaHasta,
            List<ConAdjuntosContableWebTO> listadoImagenes, String codigoUnico, SisInfoTO sisInfoTO) throws Exception {
        ConContable contable = new ConContable(sisInfoTO.getEmpresa(), "", "C-ANT", "");
        contable.setConObservaciones(observacionesContable);
        contable.setConFecha(UtilsDate.fechaFormatoDate(fechaHasta, "dd-MM-yyyy"));
        contable.setConPendiente(false);
        contable.setConCodigo(codigoUnico);
        MensajeTO mensaje = insertarModificarRhAnticipo(contable, listaRhAnticipo, sisInfoTO);
        if (mensaje != null && mensaje.getMensaje() != null && mensaje.getMensaje().charAt(0) == 'T') {
            contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
            contableService.actualizarImagenesContables(listadoImagenes, contable.getConContablePK());
        }
        return mensaje;
    }

    @Override
    public List<ConContableReporteTO> generarConContableReporte(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String periodo, String tipoContable,
            String numeroContable, SisInfoTO usuario) throws Exception {

        List<ConContableReporteTO> list = new ArrayList<ConContableReporteTO>();
        try {
            ConContableTO cabeceraContable = contableService.getListaConContableTO(usuarioEmpresaReporteTO.getEmpCodigo(), periodo, tipoContable, numeroContable).get(0);

            List<ConDetalleTablaTO> detalleContable = detalleService.getListaConDetalleTO(usuarioEmpresaReporteTO.getEmpCodigo(), periodo, tipoContable, numeroContable);

            ConContableReporteTO ccrto = null;

            List<String> cuentaPadre = new ArrayList<String>();

            Short estGrupo1;
            Short estGrupo2;
            Short estGrupo3;
            Short estGrupo4;
            Short estGrupo5;
            Short estGrupo6;
            ConEstructuraTO estructura2 = estructuraService.getListaConEstructuraTO(usuarioEmpresaReporteTO.getEmpCodigo()).get(0);
            estGrupo1 = estructura2.getEstGrupo1();
            estGrupo2 = estructura2.getEstGrupo2();
            estGrupo3 = estructura2.getEstGrupo3();
            estGrupo4 = estructura2.getEstGrupo4();
            estGrupo5 = estructura2.getEstGrupo5();
            estGrupo6 = estructura2.getEstGrupo6();

            for (ConDetalleTablaTO dc : detalleContable) {
                String cuenta, nombre;
                cuenta = dc.getCtaCodigo();
                cuenta = cuenta.trim().substring(0,
                        ((estGrupo2 == 0 ? 0 : estGrupo1) + (estGrupo3 == 0 ? 0 : estGrupo2)
                        + (estGrupo4 == 0 ? 0 : estGrupo3) + (estGrupo5 == 0 ? 0 : estGrupo4)
                        + (estGrupo6 == 0 ? 0 : estGrupo5)));
                nombre = this.buscarDetalleContablePadre(cuenta, usuarioEmpresaReporteTO.getEmpCodigo(), usuario);
                cuentaPadre.add(cuenta + " | " + nombre);
            }

            ccrto = new ConContableReporteTO(tipoService.getConTipoTO(usuarioEmpresaReporteTO.getEmpCodigo(), tipoContable).getTipDetalle(), cuentaPadre,
                    cabeceraContable, detalleContable);

            list.add(ccrto);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", usuario));
        }
        return list;
    }

    public String buscarDetalleContablePadre(String codigo, String empresa, SisInfoTO sisInfoTO) {
        List<ConCuentasTO> listaConCuentasTO = new ArrayList<ConCuentasTO>();
        String encontro = "";
        try {
            listaConCuentasTO = cuentasService.getListaBuscarConCuentasTO(empresa, codigo, null);
        } catch (Exception e) {
            UtilsExcepciones.enviarError(e, "UtilsContable", sisInfoTO);
        }

        for (ConCuentasTO conCuentasTO : listaConCuentasTO) {
            if (conCuentasTO.getCuentaCodigo().equals(codigo)) {
                encontro = conCuentasTO.getCuentaDetalle().trim();
                break;
            } else {
                encontro = "";
            }
        }
        return encontro;
    }

    // ordenes bancarias
    @Override
    public Map<String, Object> obtenerDatosParaCrudOrdenesBancarias(boolean esCarter) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        List<ComboGenericoTO> tiposOrden = new ArrayList<>();
        if (esCarter) {
            tiposOrden.add(new ComboGenericoTO("Anticipos", "Orden de anticipo"));
            tiposOrden.add(new ComboGenericoTO("Pagos", "Orden de pago"));
        } else {
            tiposOrden.add(new ComboGenericoTO("Anticipos", "Orden de anticipo"));
            tiposOrden.add(new ComboGenericoTO("Prestamos", "Orden de préstamos"));
            tiposOrden.add(new ComboGenericoTO("Sueldos", "Orden de sueldos"));
            tiposOrden.add(new ComboGenericoTO("XIII", "Orden de decimo tercer sueldo"));
            tiposOrden.add(new ComboGenericoTO("XIV", "Orden de decimo cuarto sueldo"));
            tiposOrden.add(new ComboGenericoTO("Vacaciones", "Orden de vacaciones"));
            tiposOrden.add(new ComboGenericoTO("Utilidades", "Orden de utilidades"));
        }

        List<ComboGenericoTO> tiposServicios = new ArrayList<ComboGenericoTO>();
        tiposServicios.add(new ComboGenericoTO("RPA", "Roles de pago"));
        tiposServicios.add(new ComboGenericoTO("TER", "Pago a terceros"));

        respuesta.put("tiposOrden", tiposOrden);
        respuesta.put("tiposServicios", tiposServicios);

        return respuesta;
    }

    @Override
    public RespuestaWebTO generarListaOrden(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario, boolean sinCuenta) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        try {
            //Banco Bolivariano
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("BOLIVARIANO")) {
                ordenBancaria.setBanco("BOLIVARIANO");
                List<RhPreavisoAnticiposPrestamosSueldoTO> listaBolivariano = listaOrdenesAnticipoBancoBolivariano(ordenBancaria, sector, usuario, sinCuenta);
                this.tratamientoDeLaLista(listaBolivariano);
                if (listaBolivariano != null && listaBolivariano.size() > 0) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(listaBolivariano);
                }
            }
            //Banco Internacional
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("INTERNACIONAL")) {
                ordenBancaria.setBanco("INTERNACIONAL");
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichinchaInternacional = listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
                this.tratamientoDeLaLista(listaPichinchaInternacional);
                if (listaPichinchaInternacional != null && listaPichinchaInternacional.size() > 0) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(listaPichinchaInternacional);
                }
            }
            //Banco Pichincha 
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("PICHINCHA")) {
                ordenBancaria.setBanco("PICHINCHA");
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichincha = listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
                this.tratamientoDeLaLista(listaPichincha);
                if (listaPichincha != null && listaPichincha.size() > 0) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(listaPichincha);
                }
            }
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("PRODUCCION")) {
                ordenBancaria.setBanco("PRODUCCION"); //Temporal - NUEVO METODO
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaProduccion = listaOrdenesProduccion(ordenBancaria, sector, usuario);
                this.tratamientoDeLaLista(listaProduccion);
                if (listaProduccion != null && listaProduccion.size() > 0) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(listaProduccion);
                }
            }
            //Banco Austro
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("AUSTRO")) {
                ordenBancaria.setBanco("AUSTRO");
                List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaPichinchaInternacional = listaOrdenesPichinchaInternacional(ordenBancaria, sector, usuario);
                this.tratamientoDeLaLista(listaPichinchaInternacional);
                if (listaPichinchaInternacional != null && listaPichinchaInternacional.size() > 0) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(listaPichinchaInternacional);
                }
            }
            //Banco Machala
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("MACHALA")) {
                ordenBancaria.setBanco("MACHALA");
                List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> listaBancoMachala = listaOrdenesBancoMachala(ordenBancaria, sector, usuario);
                this.tratamientoDeLaLista(listaBancoMachala);
                if (listaBancoMachala != null && listaBancoMachala.size() > 0) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(listaBancoMachala);
                }
            }
            //guayaquil
            if (ordenBancaria.getNombreCuenta().toUpperCase().contains("GUAYAQUIL")) {
                ordenBancaria.setBanco("GUAYAQUIL");
                List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> listaBancoGuayaquil = listaOrdenesBancoGuayaquil(ordenBancaria, sector, usuario);
                this.tratamientoDeLaLista(listaBancoGuayaquil);
                if (listaBancoGuayaquil != null && listaBancoGuayaquil.size() > 0) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(listaBancoGuayaquil);
                }
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", usuario));
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

    //BANCO BOLIVARIANO
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> listaOrdenesAnticipoBancoBolivariano(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario, boolean sinCuenta) throws Exception {
        List<RhPreavisoAnticiposPrestamosSueldoTO> lista = new ArrayList<>();
        try {
            usuario.setEmpresa(ordenBancaria.getEmpresa());

            lista = getRhFunPreavisoAnticiposBolivariano(ordenBancaria.getEmpresa(),
                    UtilsDate.fechaFormatoString(ordenBancaria.getFecha(), "yyyy-MM-dd"), ordenBancaria.getCuentabancaria(),
                    ordenBancaria.getOrden(), ordenBancaria.getTipoServicio(), sector, sinCuenta);
        } catch (Exception e) {
            UtilsExcepciones.enviarError(e, "OrdenesBancariasService  - listaOrdenesAnticipoBancoBolivariano", usuario);
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", usuario));
        }
        return lista;
    }

    //BANCO PICHINCHA - INTERNACIONAL
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaOrdenesPichinchaInternacional(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception {
        List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> lista = new ArrayList<>();
        try {
            usuario.setEmpresa(ordenBancaria.getEmpresa());
            lista = getRhFunPreavisoAnticiposPichincha(ordenBancaria.getEmpresa(),
                    UtilsDate.fechaFormatoString(ordenBancaria.getFecha(), "yyyy-MM-dd"), ordenBancaria.getCuentabancaria(),
                    ordenBancaria.getOrden(), ordenBancaria.getBanco(), sector);
        } catch (Exception e) {
            UtilsExcepciones.enviarError(e, "OrdenesBancariasService  - listaOrdenesPichinchaInternacional", usuario);
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", usuario));
        }
        return lista;
    }

    //BANCO PRODUCCION
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaOrdenesProduccion(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception {
        List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> lista = new ArrayList<>();
        try {
            usuario.setEmpresa(ordenBancaria.getEmpresa());
            lista = getRhFunPreavisoAnticiposPichincha(ordenBancaria.getEmpresa(),
                    UtilsDate.fechaFormatoString(ordenBancaria.getFecha(), "yyyy-MM-dd"), ordenBancaria.getCuentabancaria(),
                    ordenBancaria.getOrden(), ordenBancaria.getBanco(), sector);
        } catch (Exception e) {
            UtilsExcepciones.enviarError(e, "OrdenesBancariasService  - listaOrdenesProduccion", usuario);
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", usuario));
        }
        return lista;
    }

    //BANCO MACHALA
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> listaOrdenesBancoMachala(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception {
        List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> lista = new ArrayList<>();
        try {
            usuario.setEmpresa(ordenBancaria.getEmpresa());
            lista = getRhFunPreavisoAnticiposMachala(ordenBancaria.getEmpresa(),
                    UtilsDate.fechaFormatoString(ordenBancaria.getFecha(), "yyyy-MM-dd"), ordenBancaria.getCuentabancaria(),
                    ordenBancaria.getOrden(), ordenBancaria.getBanco(), sector);
        } catch (Exception e) {
            UtilsExcepciones.enviarError(e, "OrdenesBancariasService  - listaOrdenesBancoMachala", usuario);
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", usuario));
        }
        return lista;
    }

    //BANCO GUAYAQUIL
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> listaOrdenesBancoGuayaquil(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception {
        List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> lista = new ArrayList<>();
        try {
            usuario.setEmpresa(ordenBancaria.getEmpresa());
            lista = getRhFunPreavisoAnticiposGuayaquil(ordenBancaria.getEmpresa(),
                    UtilsDate.fechaFormatoString(ordenBancaria.getFecha(), "yyyy-MM-dd"), ordenBancaria.getCuentabancaria(),
                    ordenBancaria.getOrden(), ordenBancaria.getBanco(), sector);
        } catch (Exception e) {
            UtilsExcepciones.enviarError(e, "OrdenesBancariasService  - listaOrdenesBancoMachala", usuario);
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", usuario));
        }
        return lista;
    }

}
