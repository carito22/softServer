package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisComboPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoInnecesarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;

public interface PeriodoService {

    @Transactional
    public SisPeriodo getPeriodoPorFecha(Date fecha, String empresa);

    @Transactional
    public String validarPeriodo(Date fecha, String empresa);

    @Transactional
    public String validarPeriodo(String fechaInicio, String fechaFin, String empresa);

    @Transactional
    public SisPeriodo buscarPeriodo(String empresa, String codigo) throws Exception;

    @Transactional
    public String estadoPeriodo(String empresa, String fecha) throws Exception;

    @Transactional
    public List<SisListaPeriodoTO> getListaPeriodoTO(String empresa) throws Exception;

    @Transactional
    public List<SisPeriodo> getListaPeriodo(String empresa);

    @Transactional
    public String getSisPeriodo(String empresa, String periodo, String fecha);

    @Transactional
    public List<SisComboPeriodoTO> getSisComboPeriodoTO(String empresa) throws Exception;

    @Transactional
    public boolean insertarSisPeriodoTO(SisPeriodoTO sisPeriodoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean modificarSisPeriodoTO(SisPeriodoTO sisPeriodoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean modificarEstadoSisPeriodoTO(String empresa, String codigo, boolean perCerrado, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarSisPeriodoTO(SisPeriodoTO sisPeriodoTO, SisInfoTO sisInfoTO) throws Exception;

    public List<SisPeriodoInnecesarioTO> getSisPeriodoInnecesarioTO(String empresa) throws Exception;

    public boolean cerrarPeriodosAutomaticamente(String empresa) throws Exception;
}
