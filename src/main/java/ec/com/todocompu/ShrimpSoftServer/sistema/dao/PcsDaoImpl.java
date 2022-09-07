package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPcs;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

@Repository
public class PcsDaoImpl extends GenericDaoImpl<SisPcs, String> implements PcsDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean comprobarSisPcs(String mac) throws Exception {
        int conteo = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql("SELECT count(inf_mac) "
                + "FROM sistemaweb.sis_pcs WHERE inf_mac = ('" + mac + "') and inf_estado = TRUE"));
        if (conteo > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<SisPcs> listarSisPcs(String busqueda, boolean mostrarActivos) throws Exception {
        String sqlBusqueda = "";
        if (busqueda != null && !busqueda.equals("")) {
            sqlBusqueda = " where (inf_mac like '%" + busqueda + "%' "
                    + "OR inf_nombre like '%" + busqueda + "%' OR inf_descripcion like '%" + busqueda + "%') ";
        }
        if (sqlBusqueda.equals("") && mostrarActivos) {
            sqlBusqueda = " where inf_estado";
        } else {
            if (!sqlBusqueda.equals("") && mostrarActivos) {
                sqlBusqueda = " and inf_estado";
            }
        }
        String sql = "SELECT * FROM sistemaweb.sis_pcs" + sqlBusqueda;
        return genericSQLDao.obtenerPorSql(sql, SisPcs.class);
    }

    @Override
    public SisPcs insertarSisPc(SisPcs sisPc, SisSuceso sisSuceso) throws Exception {
        insertar(sisPc);
        sucesoDao.insertar(sisSuceso);
        return sisPc;
    }

    @Override
    public SisPcs modificarSisPc(SisPcs sisPc, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(sisPc);
        return sisPc;
    }

    @Override
    public boolean eliminarSisPc(SisPcs sisPc, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(sisPc);
        return true;
    }

}
