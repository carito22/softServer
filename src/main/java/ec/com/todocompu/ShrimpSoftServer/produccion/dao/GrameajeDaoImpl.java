package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoGramajeDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListadoGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameajePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoGramaje;

@Repository
public class GrameajeDaoImpl extends GenericDaoImpl<PrdGrameaje, PrdGrameajePK> implements GrameajeDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;
    @Autowired
    private SucesoGramajeDao sucesoGramajeDao;

    @Override
    public boolean insertarPrdGrameaje(PrdGrameaje prdGrameaje, SisSuceso sisSuceso) throws Exception {
        insertar(prdGrameaje);
        sisSucesoDao.insertar(sisSuceso);
        /////////////insertar suceso//////////////
        SisSucesoGramaje sucesoGram = new SisSucesoGramaje();
        String json = UtilsJSON.objetoToJson(prdGrameaje);
        sucesoGram.setSusJson(json);
        sucesoGram.setSisSuceso(sisSuceso);
        sucesoGram.setPrdGrameaje(prdGrameaje);
        sucesoGramajeDao.insertar(sucesoGram);
        return true;
    }

    @Override
    public boolean eliminarPrdGrameaje(PrdGrameaje prdGrameaje, SisSuceso sisSuceso) throws Exception {
        eliminar(prdGrameaje);
        // eliminarPorId(PrdGrameaje.class, prdGrameaje.getPrdGrameajePK());
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String obtenerFechaGrameajeSuperior(String empresa, String sector, String numPiscina) throws Exception {
        String empresaSectorPiscina = empresa.trim() + sector.trim() + numPiscina.trim();
        String sql = "select CASE WHEN cast(max(gra_fecha) as TEXT) IS NULL THEN '1111-11-11' ELSE cast(max(gra_fecha) as TEXT) END from produccion.prd_grameaje where TRIM(BOTH ' ' FROM gra_empresa) || TRIM(BOTH ' ' FROM gra_sector) || TRIM(BOTH ' ' FROM gra_piscina) = ('"
                + empresaSectorPiscina + "')";
        return (String) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public boolean getIsFechaGrameajeSuperior(String empresa, String sector, String numPiscina, String fechaDesde,
            String fechaHasta, String fecha) throws Exception {
        Date fechaCliente = UtilsValidacion.fecha(fecha, "yyyy-MM-dd");
        String sql = "select CASE WHEN cast(max(gra_fecha) as TEXT) IS NULL THEN '1111-11-11' ELSE cast(max(gra_fecha) as TEXT) END from produccion.prd_grameaje "
                + "where (gra_empresa, gra_sector, gra_piscina) = ('" + empresa + "' ,'" + sector + "', '" + numPiscina
                + "') and gra_fecha>= " + fechaDesde + " and gra_fecha<="
                + (fechaHasta == null || fechaHasta.compareToIgnoreCase("") == 0 ? "'2999-12-31'" : fechaHasta);
        String fechaConsulta = (String) genericSQLDao.obtenerObjetoPorSql(sql);
        if (fechaConsulta.trim().isEmpty()) {
            return true;
        } else {
            Date fechaBD = UtilsValidacion.fecha(fechaConsulta, "yyyy-MM-dd");
            return (fechaCliente.getTime() > fechaBD.getTime()) ? true : false;
        }
    }

    @Override
    public PrdGrameajeTO getPrdGrameajeTO(String empresa, String sector, String piscina, String fecha)
            throws Exception {
        fecha = fecha == null ? fecha : "'" + fecha + "'";
        String sql = "SELECT * FROM produccion.prd_grameaje " + "WHERE (gra_empresa = '" + empresa + "' "
                + "AND gra_sector = '" + sector + "' " + "AND gra_piscina = '" + piscina + "' " + "AND gra_fecha = "
                + fecha + ");";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdGrameajeTO.class);
    }

    @Override
    public PrdGrameajeTO getPrdGrameajeTO(String empresa, String sector, String piscina, String desde, String hasta)
            throws Exception {
        String sql = "SELECT * FROM produccion.prd_grameaje WHERE gra_empresa = '" + empresa + "' "
                + "AND gra_sector = '" + sector + "' AND " + "gra_piscina = '" + piscina + "' AND ((gra_fecha >= "
                + desde + " AND gra_fecha <= " + hasta + ") " + "OR (gra_fecha >= " + desde + " AND " + hasta
                + " IS NULL)) AND " + "(NOT gra_anulado OR gra_anulado IS NULL) ORDER BY gra_fecha DESC LIMIT 1";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdGrameajeTO.class);
    }

    @Override
    public PrdGrameaje getPrdGrameaje(String empresa, String sector, String piscina) throws Exception {
        String sql = "SELECT * FROM produccion.prd_grameaje WHERE pis_empresa='" + empresa + "' AND pis_sector='"
                + sector + "' AND pis_numero='" + piscina + "' AND "
                + "gra_fecha <= current_date AND (NOT gra_anulado OR gra_anulado IS NULL) "
                + "ORDER BY gra_fecha DESC LIMIT 1";
        return obtenerObjetoPorSql(sql, PrdGrameaje.class);
    }

    @Override
    public List<PrdListadoGrameajeTO> getPrdListadoGrameajeTO(String empresa, String sector, String piscina,
            String desde, String hasta) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        String sql = "SELECT * FROM produccion.fun_listado_grameaje('" + empresa + "', '" + sector + "', " + "'"
                + piscina + "', " + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdListadoGrameajeTO.class);
    }

    @Override
    public List<PrdGrameaje> getPrdListadoGrameaje(String empresa, String sector, String fecha) throws Exception {
        String sql = "SELECT * FROM produccion.prd_grameaje WHERE gra_empresa='" + empresa + "' and gra_sector='"
                + sector + "' and gra_fecha='" + fecha + "'";
        return genericSQLDao.obtenerPorSql(sql, PrdGrameaje.class);
    }

    @Override
    public List<ListadoGrameaje> obtenerGrameajePorFechasCorrida(String empresa, String sector, String piscina,
            String desde, String hasta) {
        empresa = empresa.compareTo("") == 0 ? null : "'" + empresa + "'";
        sector = sector.compareTo("") == 0 ? null : "'" + sector + "'";
        piscina = piscina.compareTo("") == 0 ? null : "'" + piscina + "'";
        desde = desde.compareTo("") == 0 ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";

        String sql = "SELECT * FROM produccion.fun_listado_grameaje(" + empresa + "," + sector + ", " + piscina + ", "
                + desde + ", " + hasta + ")";

        return genericSQLDao.obtenerPorSql(sql, ListadoGrameaje.class);
    }

}
