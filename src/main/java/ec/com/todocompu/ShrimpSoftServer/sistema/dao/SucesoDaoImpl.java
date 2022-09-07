package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaSusTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisSucesoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoDetalle;

@Repository
public class SucesoDaoImpl extends GenericDaoImpl<SisSuceso, String> implements SucesoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisSucesoTO> getListaSisSuceso(String desde, String hasta, String usuario, String suceso, String cadenaGeneral, String empresa) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        suceso = suceso == null || suceso.compareTo("") == 0 ? null : "'" + suceso + "'";
        usuario = usuario == null || usuario.compareTo("") == 0 ? null : "'" + usuario + "'";
        cadenaGeneral = cadenaGeneral == null || cadenaGeneral.compareTo("") == 0 ? null : "'" + cadenaGeneral + "'";

        String sql = "SELECT * FROM sistemaweb.fun_sucesos(" + "(" + empresa + ")," + "('" + desde + "'), " + "('"
                + hasta + "'), " + "(" + usuario + "), " + "(" + suceso + "), " + "(" + cadenaGeneral
                + ")) ORDER BY sus_secuencia";

        return genericSQLDao.obtenerPorSql(sql, SisSucesoTO.class);
    }

    @Override
    public int retornoContadoEliminarUsuario(String codUsuario) throws Exception {
        String sql = "SELECT COUNT (*) from sistemaweb.sis_suceso " + "WHERE det_usuario = ('" + codUsuario.trim()
                + "')";

        return (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<SisListaSusTablaTO> getListaSisSusTablaTOs(String empresa) throws Exception {
        String var = "('" + empresa.trim() + "')";
        String sql = "SELECT sus_tabla FROM sistemaweb.sis_suceso " + "WHERE det_empresa= " + var
                + " GROUP BY sus_tabla ORDER BY sus_tabla;";

        return genericSQLDao.obtenerPorSql(sql, SisListaSusTablaTO.class);
    }

    @Override
    public List<SisSucesoDetalle> getListaSisSucesoDetalle(Integer secuencial) throws Exception {
        String sql = "SELECT * FROM sistemaweb.fun_suceso_detalle(" + secuencial + ")";
        return genericSQLDao.obtenerPorSql(sql, SisSucesoDetalle.class);
    }

    @Override
    public void sisRegistrarSucesosUsuario(SisSuceso sisSuceso) throws Exception {
        insertar(sisSuceso);
    }

}
