package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.PeriodoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesSistema;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisComboPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoInnecesarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class PeriodoServiceImpl implements PeriodoService {

    @Autowired
    private PeriodoDao periodoDao;

    private Boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public SisPeriodo getPeriodoPorFecha(Date fecha, String empresa) {
        List<SisPeriodo> list = getListaPeriodo(empresa);
        for (SisPeriodo periodo : list) {
            if (fecha.getTime() >= periodo.getPerDesde().getTime() && fecha.getTime() <= UtilsDate.dateCompleto(periodo.getPerHasta()).getTime()) {
                return periodo;
            }
        }
        return null;
    }

    @Override
    public String validarPeriodo(Date fecha, String empresa) {
        SisPeriodo periodo = getPeriodoPorFecha(fecha, empresa);
        if (periodo == null) {
            return "FNo se encuentra ningun periodo para la fecha que ingreso.";
        } else if (periodo.getPerCerrado()) {
            return "FEl periodo que corresponde a la fecha que ingreso se encuentra cerrado.";
        }
        return null;
    }

    @Override
    public String validarPeriodo(String fechaInicio, String fechaFin, String empresa) {
        String mensaje = null;
        int mes1 = UtilsDate.getNumeroMes(fechaInicio, "yyyy-MM-dd") + 1;
        int mes2 = UtilsDate.getNumeroMes(fechaFin, "yyyy-MM-dd") + 1;
        int anio = UtilsDate.getNumeroAnio(fechaFin, "yyyy-MM-dd");
        for (int i = mes1; i <= mes2; i++) {
            String fechaPeriodo = anio + "-" + i + "-01";
            SisPeriodo periodo = getPeriodoPorFecha(UtilsDate.fechaFormatoDate(fechaPeriodo, "yyyy-MM-dd"), empresa);
            if (periodo == null) {
                if (mensaje == null) {
                    mensaje = "";
                }
                mensaje += "No se encuentra ningun periodo para la fecha: " + fechaPeriodo + ".\n";
            } else if (periodo.getPerCerrado()) {
                if (mensaje == null) {
                    mensaje = "";
                }
                mensaje += "El periodo " + periodo.getSisPeriodoPK().getPerCodigo() + " se encuentra cerrado.\n";
            }
        }
        return mensaje == "" || mensaje == null ? null : "F" + mensaje;
    }

    @Override
    public String estadoPeriodo(String empresa, String fecha) throws Exception {
        String periodoCerrado = "No se puede realizar la acción, Periodo no existe.!";
        List<SisListaPeriodoTO> listaSisPeriodoTO = periodoDao.getListaSisPeriodoTO(empresa);
        if (fecha != null && fecha.compareToIgnoreCase("") != 0) {
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() <= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado()
                            ? "No se puede realizar la acción, Periodo cerrado.!" : "";
                }
            }
        }
        return periodoCerrado;
    }

    @Override
    public List<SisListaPeriodoTO> getListaPeriodoTO(String empresa) throws Exception {
        return periodoDao.getListaSisPeriodoTO(empresa);
    }

    @Override
    public List<SisPeriodo> getListaPeriodo(String empresa) {
        return periodoDao.getListaSisPeriodo(empresa);
    }

    @Override
    public String getSisPeriodo(String empresa, String periodo, String fecha) {
        SisPeriodo sisPeriodo = periodoDao.getSisPeriodo(empresa, periodo, fecha);
        if (sisPeriodo == null) {
            return "FFecha incorrecta / Periodo incorrecto!";
        } else if (sisPeriodo.getPerCerrado()) {
            return "FPeriodo cerrado!";
        } else {
            return "T";
        }
    }

    @Override
    public List<SisComboPeriodoTO> getSisComboPeriodoTO(String empresa) throws Exception {
        return periodoDao.getSisComboPeriodo(empresa);
    }

    @Override
    public SisPeriodo buscarPeriodo(String empresa, String codigo) throws Exception {
        return periodoDao.obtenerPorId(SisPeriodo.class, new SisPeriodoPK(empresa, codigo));
    }

    @Override
    public boolean insertarSisPeriodoTO(SisPeriodoTO sisPeriodoTO, SisInfoTO sisInfoTO) throws Exception {
        sisPeriodoTO.setUsrFechaInsertaPeriodo(UtilsValidacion.fechaSistema());
        comprobar = false;
        if (buscarPeriodo(sisPeriodoTO.getPerEmpresa().trim(), sisPeriodoTO.getPerCodigo().trim()) == null) {
            SisPeriodo sisPeriodo = ConversionesSistema.ConvertirSisPeriodoTO_SisPeriodo(sisPeriodoTO);
            sisPeriodo.setSisPeriodoPK(new SisPeriodoPK(sisPeriodoTO.getPerEmpresa(), sisPeriodoTO.getPerCodigo()));
            susClave = sisPeriodoTO.getPerCodigo();
            susDetalle = "Se insertó un periodo desde " + sisPeriodoTO.getPerDesde() + " hasta "
                    + sisPeriodoTO.getPerHasta();
            susSuceso = "INSERT";
            susTabla = "sistemaweb.sis_periodo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = periodoDao.insertarSisPeriodo(sisPeriodo, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarSisPeriodoTO(SisPeriodoTO sisPeriodoTO, SisInfoTO sisInfoTO) throws Exception {
        sisPeriodoTO.setUsrFechaInsertaPeriodo(UtilsValidacion.fechaSistema());
        comprobar = false;
        if (buscarPeriodo(sisPeriodoTO.getPerEmpresa().trim(), sisPeriodoTO.getPerCodigo().trim()) != null) {

            susClave = sisPeriodoTO.getPerCodigo();
            susDetalle = "Se modificó datos del periodo " + sisPeriodoTO.getPerCodigo();
            susSuceso = "UPDATE";
            susTabla = "sistemaweb.sis_periodo";
            SisPeriodo sisPeriodoAux = buscarPeriodo(sisPeriodoTO.getPerEmpresa().trim(),
                    sisPeriodoTO.getPerCodigo().trim());
            sisPeriodoTO.setUsrFechaInsertaPeriodo(
                    UtilsValidacion.fecha(sisPeriodoAux.getUsrFechaInsertaPeriodo(), "yyyy-MM-dd HH:mm:ss"));
            SisPeriodo sisPeriodo = ConversionesSistema.ConvertirSisPeriodoTO_SisPeriodo(sisPeriodoTO);
            sisPeriodo.setSisPeriodoPK(new SisPeriodoPK(sisPeriodoTO.getEmpCodigo(), sisPeriodoTO.getPerCodigo()));
            sisPeriodo.setEmpCodigo(new SisEmpresa(sisInfoTO.getEmpresa()));
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = periodoDao.modificarSisPeriodo(sisPeriodo, sisSuceso);
        }
        comprobar = true;
        return comprobar;
    }

    @Override
    public boolean modificarEstadoSisPeriodoTO(String empresa, String codigo, boolean perCerrado, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        if (buscarPeriodo(empresa, codigo) != null) {
            susClave = codigo;
            susDetalle = perCerrado ? "Se ha cerrado el periodo " : "Se ha abierto el periodo " + codigo;
            susSuceso = "UPDATE";
            susTabla = "sistemaweb.sis_periodo";
            SisPeriodo sisPeriodoAux = buscarPeriodo(empresa, codigo.trim());
            sisPeriodoAux.setPerCerrado(perCerrado);
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = periodoDao.modificarSisPeriodo(sisPeriodoAux, sisSuceso);
        }
        comprobar = true;
        return comprobar;
    }

    @Override
    public boolean eliminarSisPeriodoTO(SisPeriodoTO sisPeriodoTO, SisInfoTO sisInfoTO) throws Exception {
        boolean segir = periodoDao.retornoContadoEliminarPeriodo(sisPeriodoTO.getEmpCodigo(),
                sisPeriodoTO.getPerCodigo());
        SisPeriodo sisPeriodo = null;
        comprobar = false;
        if (segir) {
            sisPeriodoTO.setUsrFechaInsertaPeriodo(UtilsValidacion.fechaSistema());
            sisPeriodo = periodoDao.obtenerPorId(SisPeriodo.class,
                    new SisPeriodoPK(sisPeriodoTO.getPerEmpresa(), sisPeriodoTO.getPerCodigo()));

            // SUCESO
            susClave = sisPeriodoTO.getEmpCodigo();
            susTabla = "sistemaweb.sis_periodo";
            susDetalle = "Se eliminó el periodo " + sisPeriodoTO.getPerDetalle();
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = periodoDao.eliminarSisPeriodo(sisPeriodo, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public List<SisPeriodoInnecesarioTO> getSisPeriodoInnecesarioTO(String empresa) throws Exception {
        return periodoDao.getSisPeriodoInnecesarioTO(empresa);
    }

    @Override
    public boolean cerrarPeriodosAutomaticamente(String empresa) throws Exception {
        return periodoDao.cerrarPeriodosAutomaticamente(empresa);
    }

}
