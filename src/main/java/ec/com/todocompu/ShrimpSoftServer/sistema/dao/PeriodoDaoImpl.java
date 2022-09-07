package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisComboPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoInnecesarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;

@Repository
public class PeriodoDaoImpl extends GenericDaoImpl<SisPeriodo, SisPeriodoPK> implements PeriodoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<SisListaPeriodoTO> getListaSisPeriodoTO(String empresa) throws Exception {
        String sql = "SELECT per_codigo, per_detalle, cast(per_desde as text), cast(per_hasta as text), per_cerrado from sistemaweb.sis_periodo where per_empresa = ('"
                + empresa + "') ORDER BY per_codigo DESC;";
        return genericSQLDao.obtenerPorSql(sql, SisListaPeriodoTO.class);
    }

    @Override
    public List<SisPeriodo> getListaSisPeriodo(String empresa) {
        String sql = "select per.* from sistemaweb.sis_periodo per where per.per_empresa = ('" + empresa
                + "') ORDER BY per.per_codigo DESC;";
        return genericSQLDao.obtenerPorSql(sql, SisPeriodo.class);
    }

    @Override
    public SisPeriodo getSisPeriodo(String empresa, String periodo, String fecha) {
        String sql = "SELECT * FROM sistemaweb.sis_periodo "
                + "WHERE per_empresa= '" + empresa + "' AND "
                + "per_codigo='" + periodo + "'"
                + (fecha == null ? "" : (" AND per_desde <= '" + fecha + "' AND "
                        + "per_hasta >= '" + fecha + "'"));

        return genericSQLDao.obtenerObjetoPorSql(sql, SisPeriodo.class);
    }

    @Override
    public List<SisComboPeriodoTO> getSisComboPeriodo(String empresa) throws Exception {
        String sql = "SELECT per_codigo, per_detalle " + "FROM sistemaweb.sis_periodo " + "WHERE per_empresa = ('"
                + empresa + "') ORDER BY per_codigo DESC;";

        return genericSQLDao.obtenerPorSql(sql, SisComboPeriodoTO.class);
    }

    @Override
    public boolean retornoContadoEliminarPeriodo(String empresa, String periodo) throws Exception {
        String sql = "SELECT sistemaweb.fun_sw_periodo_eliminar('" + empresa + "', '" + periodo + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public boolean eliminarSisPeriodo(SisPeriodo sisPeriodo, SisSuceso sisSuceso) throws Exception {
        eliminar(sisPeriodo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarSisPeriodo(SisPeriodo sisPeriodo, SisSuceso sisSuceso) throws Exception {
        insertar(sisPeriodo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarSisPeriodo(SisPeriodo sisPeriodo, SisSuceso sisSuceso) throws Exception {
        actualizar(sisPeriodo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<SisPeriodoInnecesarioTO> getSisPeriodoInnecesarioTO(String empresa) throws Exception {
        String sql = "select * from sistemaweb.fun_periodos_abiertos_innecesariamente('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, SisPeriodoInnecesarioTO.class);
    }

    @Override
    public boolean cerrarPeriodosAutomaticamente(String empresa) throws Exception {
        String sql = "SELECT * FROM sistemaweb.fun_periodos_cerrar_automaticamente('" + empresa + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

}
