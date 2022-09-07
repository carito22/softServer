package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorConHectareajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class SectorDaoImpl extends GenericDaoImpl<PrdSector, PrdSectorPK> implements SectorDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public boolean insertarPrdSector(PrdSector prdSector, SisSuceso sisSuceso) throws Exception {
        insertar(prdSector);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdSector(PrdSector prdSector, SisSuceso sisSuceso) throws Exception {
        actualizar(prdSector);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdSector(PrdSector prdSector, SisSuceso sisSuceso) throws Exception {
        eliminar(prdSector);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<PrdSector> getListaSectorPorEmpresa(String empresa, boolean activo) throws Exception {
        String sql = "";
        if (activo) {
            sql = "SELECT * FROM produccion.prd_sector WHERE sec_empresa = ('" + empresa + "') ORDER BY sec_codigo";
        } else {
            sql = "SELECT * FROM produccion.prd_sector WHERE sec_empresa = ('" + empresa
                    + "') AND sec_activo ORDER BY sec_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdSector.class);
    }

    @Override
    public boolean eliminarPrdSector(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM produccion.fun_eliminar_produccion('S', '" + empresa + "', '" + codigo
                + "', null, null)";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<PrdListaSectorTO> getListaSector(String empresa, boolean activo) throws Exception { //false
        String sql = "";
        if (activo == true) { //muestra todos
            sql = "SELECT sec_codigo, sec_nombre, sec_direccion, sec_telefono, sec_latitud, "
                    + "sec_longitud, sec_activo, sec_actividad FROM produccion.prd_sector  WHERE sec_empresa = ('" + empresa + "') ORDER BY sec_nombre";
        } else { //solo activos
            sql = "SELECT sec_codigo, sec_nombre, sec_direccion, sec_telefono, sec_latitud,"
                    + "sec_longitud, sec_activo, sec_actividad FROM produccion.prd_sector WHERE sec_empresa = ('" + empresa + "') AND sec_activo ORDER BY sec_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdListaSectorTO.class);
    }

    @Override
    public List<PrdListaSectorTO> obtenerTodosLosSectores(String empresa) throws Exception {
        String sql = "";
        sql = "SELECT sec_codigo, sec_direccion, sec_telefono,sec_nombre, sec_latitud, "
                + "sec_longitud, sec_activo,sec_actividad FROM produccion.prd_sector WHERE sec_empresa = ('" + empresa
                + "') ORDER BY sec_codigo";
        return genericSQLDao.obtenerPorSql(sql, PrdListaSectorTO.class);
    }

    @Override
    public List<PrdListaSectorConHectareajeTO> getListaSectorConHectareaje(String empresa, String fecha) throws Exception {
        String sql = "SELECT prd_piscina.sec_codigo, prd_sector.sec_nombre, inv_bodega.bod_codigo, inv_bodega.bod_nombre, SUM(prd_piscina.pis_hectareaje) sec_hectareaje "
                + "FROM inventario.inv_bodega INNER JOIN produccion.prd_sector INNER JOIN produccion.prd_piscina INNER JOIN produccion.prd_corrida "
                + "ON prd_piscina.pis_empresa = prd_corrida.cor_empresa AND prd_piscina.pis_sector  = prd_corrida.cor_sector AND "
                + "prd_piscina.pis_numero  = prd_corrida.cor_piscina AND (('" + fecha
                + "' >= prd_corrida.cor_fecha_desde " + "AND '" + fecha + "' <= prd_corrida.cor_fecha_hasta) OR ('"
                + fecha + "' >= prd_corrida.cor_fecha_desde AND prd_corrida.cor_fecha_hasta IS NULL)) "
                + "ON prd_sector.sec_empresa = prd_piscina.sec_empresa AND prd_sector.sec_codigo = prd_piscina.sec_codigo "
                + "ON inv_bodega.sec_empresa = prd_sector.sec_empresa AND inv_bodega.sec_codigo = prd_sector.sec_codigo "
                + "WHERE prd_piscina.pis_empresa = ('" + empresa
                + "') GROUP BY prd_piscina.sec_empresa, prd_piscina.sec_codigo, prd_sector.sec_nombre, "
                + "inv_bodega.bod_codigo, inv_bodega.bod_nombre ORDER BY prd_piscina.sec_empresa, prd_piscina.sec_codigo";
        return genericSQLDao.obtenerPorSql(sql, PrdListaSectorConHectareajeTO.class);
    }

}
