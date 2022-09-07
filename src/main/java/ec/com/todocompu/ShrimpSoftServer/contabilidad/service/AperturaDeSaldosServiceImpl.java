package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContableEstadoSituacionInicial;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import org.hibernate.criterion.Restrictions;

@Service
public class AperturaDeSaldosServiceImpl implements AperturaDeSaldosService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private GenericoDao<ConContable, ConContablePK> contableCriteriaDao;
    @Autowired
    private GenericoDao<ConContableEstadoSituacionInicial, Integer> aperturaDao;
    @Autowired
    private SucesoDao sucesoDao;

    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public MensajeTO insertarConContableAperturaSaldoInicial(ConContableEstadoSituacionInicial conApertura, SisInfoTO sisInfoTO) throws Exception {
        String secuencia = UtilsValidacion.generarSecuenciaAleatoria();
        ConContable conContable = conApertura.getConContable();
        conContable.setConFecha(UtilsDate.fechaFormatoDate(conApertura.getApFechaHasta(), "yyyy-MM-dd"));
        conContable.setConPendiente(true);
        conContable.setConCodigo(secuencia);
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa())) != null) {
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConReversado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(true);
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("contabilidad.con_contable_estado_situacion_inicial");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
                conContable.setConConcepto("ESTADO DE SITUACION INICIAL");
            }
            susSuceso = "INSERT";
            susTabla = "contabilidad.con_contable";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
            sisSuceso.setSusDetalle("Contable de estado de situación inicial por " + conContable.getConObservaciones());
            mensaje = "ingresó";
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
            conContable = contableCriteriaDao.insertar(conContable);
            sucesoDao.insertar(sisSuceso);
            if (conContable == null) {
                retorno = "FHubo un error al guardar el contable de estado de situación inicial.\nIntente de nuevo o contacte con el administrador";
            } else {
                insertarConApertura(conApertura, conContable, false, sisInfoTO);
                retorno = "T<html>Se " + mensaje + " el contable de estado de situación inicial con la siguiente información:<br><br>"
                        + "Periodo: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Tipo: <font size = 5>"
                        + conContable.getConContablePK().getConTipo() + "</font>.<br> Número: <font size = 5>"
                        + conContable.getConContablePK().getConNumero() + "</font>.<br>"
                        + conContable.getConContablePK().getConNumero() + ", "
                        + periodo.getSisPeriodoPK().getPerCodigo() + "</html>";
                mensajeTO.setFechaCreacion(conContable.getUsrFechaInserta().toString());
                mensajeTO.getMap().put("conContable", conContable);
                contableDao.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, Suceso.crearSisSuceso(sisSuceso.getSusTabla(), sisSuceso.getSusClave(), "UPDATE", sisSuceso.getSusDetalle(), sisInfoTO));
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public ConContableEstadoSituacionInicial obtenerConAperturaSaldoInicial(ConContableEstadoSituacionInicial aperturaSaldoInicial) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(ConContableEstadoSituacionInicial.class);
        filtro.add(Restrictions.eq("ctaEmpresa", aperturaSaldoInicial.getCtaEmpresa()));
        filtro.add(Restrictions.eq("apFechaHasta", aperturaSaldoInicial.getApFechaHasta()));
        return aperturaDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
    }

    private void insertarConApertura(ConContableEstadoSituacionInicial conApertura, ConContable conContable, boolean modificar, SisInfoTO sisInfoTO) {
        conApertura.setConContable(conContable);
        conApertura.setUsrEmpresa(sisInfoTO.getEmpresa());
        conApertura.setUsrCodigo(sisInfoTO.getUsuario());
        conApertura.setUsrFechaInserta(UtilsDate.timestamp());
        aperturaDao.actualizar(conApertura);
        susSuceso = !modificar ? "INSERT" : "UPDATE";
        susTabla = "contabilidad.con_contable_estado_situacion_inicial";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisSuceso.setSusClave(conApertura.getApSecuencial() + "");
        sisSuceso.setSusDetalle("Contabilidad " + conApertura.getConContable().getConConcepto());
        sucesoDao.insertar(sisSuceso);
    }

    @Override
    public MensajeTO modificarConContableAperturaSaldoInicial(ConContableEstadoSituacionInicial conApertura, SisInfoTO sisInfoTO) throws Exception {
        String observacion = conApertura.getConContable().getConObservaciones();
        String cuenta = conApertura.getCtaCodigo();
        String sector = conApertura.getSecCodigo();
        conApertura = obtenerConAperturaSaldoInicial(conApertura);
        ConContable conContable = conApertura.getConContable();
        conContable.setConFecha(UtilsDate.fechaFormatoDate(conApertura.getApFechaHasta(), "yyyy-MM-dd"));
        conContable.setConPendiente(true);
        conContable.setConBloqueado(false);
        conContable.setConObservaciones(observacion);
        conApertura.setCtaCodigo(cuenta);
        conApertura.setSecCodigo(sector);
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa())) != null) {
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            susSuceso = "UPDATE";
            susTabla = "contabilidad.con_contable";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
            sisSuceso.setSusDetalle("Contable de estado de situación inicial por " + conContable.getConObservaciones());
            mensaje = "actualizó";
            conContable = contableCriteriaDao.actualizar(conContable);
            sucesoDao.insertar(sisSuceso);
            if (conContable == null) {
                retorno = "FHubo un error al modificar el contable de estado de situación inicial.\nIntente de nuevo o contacte con el administrador";
            } else {
                insertarConApertura(conApertura, conContable, true, sisInfoTO);
                retorno = "T<html>Se " + mensaje + " el contable de estado de situación inicial con la siguiente información:<br><br>"
                        + "Periodo: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Tipo: <font size = 5>"
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

}
