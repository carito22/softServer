package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisComboPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoInnecesarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PeriodoDao extends GenericDao<SisPeriodo, SisPeriodoPK> {

    public List<SisListaPeriodoTO> getListaSisPeriodoTO(String empresa) throws Exception;

    public List<SisPeriodo> getListaSisPeriodo(String empresa);

    public SisPeriodo getSisPeriodo(String empresa, String periodo, String fecha);

    public List<SisComboPeriodoTO> getSisComboPeriodo(String empresa) throws Exception;

    public boolean retornoContadoEliminarPeriodo(String empresa, String periodo) throws Exception;

    public boolean eliminarSisPeriodo(SisPeriodo sisPeriodo, SisSuceso sisSuceso) throws Exception;

    public boolean insertarSisPeriodo(SisPeriodo sisPeriodo, SisSuceso sisSuceso) throws Exception;

    public boolean modificarSisPeriodo(SisPeriodo sisPeriodo, SisSuceso sisSuceso) throws Exception;

    public List<SisPeriodoInnecesarioTO> getSisPeriodoInnecesarioTO(String empresa) throws Exception;

    public boolean cerrarPeriodosAutomaticamente(String empresa) throws Exception;

}
