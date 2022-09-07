package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.FormaPagoBeneficiosSocialesDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.UtilidadMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.UtilidadesDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.UtilidadesPeriodoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleUtilidadesLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Map;
import org.olap4j.impl.ArrayMap;

@Service
public class UtilidadServiceImpl implements UtilidadService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private UtilidadesDao utilidadesDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private UtilidadMotivoDao utilidadMotivoDao;
    @Autowired
    private FormaPagoBeneficiosSocialesDao formaPagoBeneficiosSocialesDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private UtilidadesPeriodoDao utilidadesPeriodoDao;

    private Boolean comprobar = false;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhFunUtilidadesCalcularTO> getRhFunCalcularUtilidades(String empresa, String sector, String desde, String hasta, Integer totalDias, Integer totalCargas, BigDecimal totalPagar) throws Exception {
        return utilidadesDao.getRhFunCalcularUtilidades(empresa, sector, desde, hasta, totalDias, totalCargas,
                totalPagar);
    }

    @Override
    public List<RhFunUtilidadesConsultarTO> getRhFunConsultarUtilidades(String empresa, String sector, String desde, String hasta) throws Exception {
        return utilidadesDao.getRhFunConsultarUtilidades(empresa, sector, desde, hasta);
    }

    @Override
    public List<RhListaDetalleUtilidadesLoteTO> getRhDetalleUtilidadesLoteTO(String empresa, String periodo, String tipo, String numero) throws Exception {
        return utilidadesDao.getRhDetalleUtilidadesLoteTO(empresa, periodo, tipo, numero);
    }

    private String validarDetalle(List<RhUtilidades> rhUtilidades, Date fechaAnticipo) {
        BigDecimal cero = new BigDecimal("0.00");
        for (RhUtilidades uti : rhUtilidades) {
            if (uti.getUtiValorTotal().compareTo(cero) > 0) {
                RhEmpleado empleado = empleadoDao.obtenerPorId(RhEmpleado.class, uti.getRhEmpleado().getRhEmpleadoPK());
                if (uti.getConCuentas().getConCuentasPK().getCtaCodigo() == null
                        || uti.getConCuentas().getConCuentasPK().getCtaCodigo().compareTo("") == 0) {
                    return "FNo ingreso la forma de pago para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres();
                }
            }
        }
        return null;
    }

    @Override
    public MensajeTO insertarModificarRhUtilidades(ConContable conContable, List<RhUtilidades> rhUtilidades, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();

        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(),
                conContable.getConContablePK().getConEmpresa())) != null) {
        } else if ((retorno = validarDetalle(rhUtilidades, conContable.getConFecha())) != null) {
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
                conContable.setConConcepto("UTILIDADES POR LOTE");
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("recursoshumanos.rh_utilidades");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }

            susSuceso = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT"
                    : "UPDATE";
            susTabla = "recursoshumanos.rh_utilidades";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingresó"
                    : "modificó";
            comprobar = utilidadesDao.insertarModificarRhUtilidades(conContable, rhUtilidades, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar las utilidades.\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>Se " + mensaje + " las utilidades con la siguiente información:<br><br>"
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
    public List<RhUtilidades> getListRhUtilidades(ConContablePK conContablePK) throws Exception {
        return utilidadesDao.getListRhUtilidades(conContablePK);
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        return utilidadesDao.getPermisoAcciones(conContablePK, fechaContable);
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudUtilidades(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<PrdListaSectorTO> sectores = sectorDao.getListaSector(empresa, true);
        List<RhUtilidadMotivo> motivos = utilidadMotivoDao.getListaRhUtilidadMotivo(empresa);
        List<RhComboFormaPagoBeneficioSocialTO> formasDePago = formaPagoBeneficiosSocialesDao.getComboFormaPagoBeneficioSocial(empresa);
        List<RhUtilidadesPeriodoTO> periodos = utilidadesPeriodoDao.getRhComboUtilidadesPeriodoTO(empresa);
        Date fechaActual = sistemaWebServicio.getFechaActual();

        respuesta.put("sectores", sectores);
        respuesta.put("motivos", motivos);
        respuesta.put("formasDePago", formasDePago);
        respuesta.put("periodos", periodos);
        respuesta.put("fechaActual", fechaActual);

        return respuesta;
    }

    @Override
    public MensajeTO insertarRhUtilidades(String observacionesContable, List<RhUtilidades> listaRhUtilidades, String fechaMaximaPago, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        String secuencia = UtilsValidacion.generarSecuenciaAleatoria();
        ConContable contable = new ConContable(sisInfoTO.getEmpresa(), "", "C-BEN", "");
        contable.setConObservaciones(observacionesContable);
        contable.setConFecha(UtilsDate.fechaFormatoDate(fechaMaximaPago, "dd-MM-yyyy"));//fechaMaximoPAgo
        contable.setConPendiente(false);
        contable.setConCodigo(secuencia);
        MensajeTO mensaje = insertarModificarRhUtilidades(contable, listaRhUtilidades, sisInfoTO);
        if (mensaje.getMensaje() != null && mensaje.getMensaje().substring(0, 1).equals("T")) {
            contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
            contableService.actualizarImagenesContables(listadoImagenes, contable.getConContablePK());
        }
        return mensaje;
    }

}
