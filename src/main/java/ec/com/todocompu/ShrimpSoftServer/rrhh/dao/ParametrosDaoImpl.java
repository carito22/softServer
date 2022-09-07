package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

@Repository
public class ParametrosDaoImpl extends GenericDaoImpl<RhParametros, String> implements ParametrosDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public RhParametros getRhParametros(String fechaHasta) throws Exception {
        String sql = "SELECT rh_parametros.* FROM recursoshumanos.rh_parametros WHERE (par_desde <= '" + fechaHasta
                + "' and par_hasta >= '" + fechaHasta + "') OR (par_desde <= '" + fechaHasta
                + "' and par_hasta IS NULL)";
        return obtenerObjetoPorSql(sql, RhParametros.class);
    }

    @Override
    public RhParametros obtenerRhParametrosPorCodigoRelacionTrabajo(String fechaHasta, String codigo) throws Exception {
        String sql = "SELECT rh_parametros.* FROM recursoshumanos.rh_parametros WHERE "
                + "((par_desde <= '" + fechaHasta + "' " + "and par_hasta >= '" + fechaHasta + "') OR "
                + "(par_desde <= '" + fechaHasta + "' and par_hasta IS NULL)) AND "
                + "par_relacion_trabajo = '" + codigo + "'";
        return obtenerObjetoPorSql(sql, RhParametros.class);
    }

    @Override
    public BigDecimal getSalarioMinimoVital(Date fecha) {
        String sql = "SELECT par_salario_minimo_vital FROM recursoshumanos.rh_parametros " + "WHERE par_desde<='"
                + UtilsDate.fechaFormatoString(fecha, "yyyy-MM-dd") + "' AND par_hasta>='"
                + UtilsDate.fechaFormatoString(fecha, "yyyy-MM-dd") + "'";
        Object obj = (Object) genericSQLDao.obtenerObjetoPorSql(sql);
        return obj == null ? null : (BigDecimal) UtilsConversiones.convertir(obj);
    }

    @Override
    public boolean esUnRangoOcupado(String fechaDesde, String fechaHasta, int secuencial) throws Exception {
        String sqlCompararElID = ""; //Cuando se va a modificar un parametro, le enviamos el secuencial para que no tome en cuenta el registro que se modificará
        String sqlCompararFechaHasta = "";//cuando me viene fecha hasta
        if (secuencial > 0) {
            sqlCompararElID = " AND par_secuencial != " + secuencial;
        }
        if (fechaHasta != null && !fechaHasta.equals("")) {
            sqlCompararFechaHasta = "OR (par_desde <= '" + fechaHasta + "' and par_hasta >= '" + fechaHasta + "')";
        }
        String sql = "SELECT rh_parametros.* FROM recursoshumanos.rh_parametros WHERE ("
                + " (par_desde <= '" + fechaDesde + "' and par_hasta >= '" + fechaDesde + "') "
                + sqlCompararFechaHasta + ")"
                + sqlCompararElID;
        List<RhParametros> parametros = obtenerPorSql(sql, RhParametros.class);
        return parametros != null && !parametros.isEmpty();
    }

    @Override
    public RhParametros obtenerUltimoParametro() throws Exception {
        String sql = "SELECT * FROM recursoshumanos.rh_parametros order by par_hasta desc LIMIT 1;";
        return obtenerObjetoPorSql(sql, RhParametros.class);
    }

    @Override
    public List<RhParametros> listaParametrosConfiguracion() throws Exception {
        String sql = "SELECT rh_parametros.* FROM recursoshumanos.rh_parametros order by par_hasta DESC;";
        return genericSQLDao.obtenerPorSql(sql, RhParametros.class);
    }

    @Override
    public RhParametros insertarRhParametro(RhParametros sisParametro, SisSuceso sisSuceso) throws Exception {
        insertar(sisParametro);
        sisSuceso.setSusClave(sisParametro.getParSecuencial() + "");
        sucesoDao.insertar(sisSuceso);
        return sisParametro;
    }

    @Override
    public RhParametros modificarRhParametro(RhParametros rhParametro, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(rhParametro);
        return rhParametro;
    }

    @Override
    public boolean eliminarRhParametro(RhParametros rhParametro, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(rhParametro);
        return true;
    }

}
