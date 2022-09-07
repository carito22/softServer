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
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContableCierreResultado;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConContableCierreResultadosServiceImpl implements ConContableCierreResultadosService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private GenericoDao<ConContable, ConContablePK> contableCriteriaDao;
    @Autowired
    private GenericoDao<ConContableCierreResultado, Integer> cierreResultadosDao;
    @Autowired
    private SucesoDao sucesoDao;

    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public ConContableCierreResultado obtenerConContableCierreResultados(ConContableCierreResultado cierreResultados) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(ConContableCierreResultado.class);
        filtro.add(Restrictions.eq("secEmpresa", cierreResultados.getSecEmpresa()));
        filtro.add(Restrictions.eq("secCodigo", cierreResultados.getSecCodigo()));
        filtro.add(Restrictions.eq("cierreFechaHasta", cierreResultados.getCierreFechaHasta()));
        return cierreResultadosDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
    }

    private void insertarConContableCierreResultados(ConContableCierreResultado conCierreResultados, ConContable conContable, boolean modificar, SisInfoTO sisInfoTO) {
        conCierreResultados.setConContable(conContable);
        conCierreResultados.setUsrEmpresa(sisInfoTO.getEmpresa());
        conCierreResultados.setUsrCodigo(sisInfoTO.getUsuario());
        conCierreResultados.setUsrFechaInserta(UtilsDate.timestamp());
        cierreResultadosDao.actualizar(conCierreResultados);
        susSuceso = !modificar ? "INSERT" : "UPDATE";
        susTabla = "contabilidad.con_contable_cierre_resultado";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisSuceso.setSusClave(conCierreResultados.getCierreSecuencial() + "");
        sisSuceso.setSusDetalle("Contabilidad " + conCierreResultados.getConContable().getConConcepto());
        sucesoDao.insertar(sisSuceso);
    }

    @Override
    public MensajeTO insertarConContableCierreResultados(ConContableCierreResultado cierreResultados, SisInfoTO sisInfoTO) throws Exception {
        String secuencia = UtilsValidacion.generarSecuenciaAleatoria();
        ConContable conContable = cierreResultados.getConContable();
        conContable.setConFecha(UtilsDate.fechaFormatoDate(cierreResultados.getCierreFechaHasta(), "yyyy-MM-dd"));
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
                conContable.setConReferencia("contabilidad.con_contable_cierre_resultado");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
                conContable.setConConcepto("CIERRE DE CUENTAS DE RESULTADOS");
            }
            susSuceso = "INSERT";
            susTabla = "contabilidad.con_contable";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
            sisSuceso.setSusDetalle("Contable de cierre de resultados por " + conContable.getConObservaciones());
            mensaje = "ingresó";
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
            conContable = contableCriteriaDao.insertar(conContable);
            sucesoDao.insertar(sisSuceso);
            if (conContable == null) {
                retorno = "FHubo un error al guardar el contable de de cierre de resultados.\nIntente de nuevo o contacte con el administrador";
            } else {
                insertarConContableCierreResultados(cierreResultados, conContable, false, sisInfoTO);
                retorno = "T<html>Se " + mensaje + " el contable de cierre de resultados con la siguiente información:<br><br>"
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
    public MensajeTO modificarConContableCierreResultados(ConContableCierreResultado cierreResultados, SisInfoTO sisInfoTO) throws Exception {
        String observacion = cierreResultados.getConContable().getConObservaciones();
        String cuenta = cierreResultados.getCtaCodigo();
        cierreResultados = obtenerConContableCierreResultados(cierreResultados);
        ConContable conContable = cierreResultados.getConContable();
        conContable.setConFecha(UtilsDate.fechaFormatoDate(cierreResultados.getCierreFechaHasta(), "yyyy-MM-dd"));
        conContable.setConPendiente(true);
        conContable.setConBloqueado(false);
        conContable.setConObservaciones(observacion);
        cierreResultados.setCtaCodigo(cuenta);
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
            sisSuceso.setSusDetalle("Contable de cierre de resultados por " + conContable.getConObservaciones());
            mensaje = "actualizó";
            conContable = contableCriteriaDao.actualizar(conContable);
            sucesoDao.insertar(sisSuceso);
            if (conContable == null) {
                retorno = "FHubo un error al modificar el contable de cierre de resultados.\nIntente de nuevo o contacte con el administrador";
            } else {
                insertarConContableCierreResultados(cierreResultados, conContable, true, sisInfoTO);
                retorno = "T<html>Se " + mensaje + " el contable de cierre de resultados con la siguiente información:<br><br>"
                        + "Periodo: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Tipo: <font size = 5>"
                        + conContable.getConContablePK().getConTipo() + "</font>.<br> Número: <font size = 5>"
                        + conContable.getConContablePK().getConNumero() + "</font>.<br>"
                        + conContable.getConContablePK().getConNumero() + ", "
                        + periodo.getSisPeriodoPK().getPerCodigo() + "</html>";
                mensajeTO.setFechaCreacion(conContable.getUsrFechaInserta().toString());
//                conContable = desmayorizarContable(conContable, Suceso.crearSisSuceso(sisSuceso.getSusTabla(), sisSuceso.getSusClave(), "UPDATE", sisSuceso.getSusDetalle(), sisInfoTO));
                mensajeTO.getMap().put("conContable", conContable);
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Transactional
    public ConContable desmayorizarContable(ConContable conContable, SisSuceso sisSuceso) {
        conContable.setConPendiente(false);
        sisSuceso.setSusDetalle("Se Desmayorizó el contable:" + conContable.getConContablePK().getConPeriodo()
                + " | " + conContable.getConContablePK().getConTipo() + " | " + conContable.getConContablePK().getConNumero());
        sucesoDao.insertar(sisSuceso);
        return contableCriteriaDao.actualizar(conContable);
    }

}
