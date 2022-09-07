package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunRegistroDatosCrediticiosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxDpaEcuador;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxDpaEcuadorPK;

@Repository
public class DpaEcuadorDaoImpl extends GenericDaoImpl<AnxDpaEcuador, AnxDpaEcuadorPK> implements DpaEcuadorDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxDpaProvinciaTO() throws Exception {
        String sql = "SELECT codigo_provincia, nombre_provincia " + "FROM anexo.anx_dpa_ecuador "
                + "GROUP BY codigo_provincia, nombre_provincia " + "ORDER BY nombre_provincia";
        return genericSQLDao.obtenerPorSql(sql, AnxProvinciaCantonTO.class);
    }

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxDpaCantonTO(String codigoProvincia) throws Exception {
        codigoProvincia = codigoProvincia == null ? codigoProvincia : "'" + codigoProvincia + "'";
        String sql = "SELECT codigo_canton as codigo_provincia, nombre_canton as nombre_provincia"
                + " FROM anexo.anx_dpa_ecuador " + "WHERE codigo_provincia = " + codigoProvincia + " "
                + "GROUP BY codigo_canton, nombre_canton " + "ORDER BY nombre_canton;";

        return genericSQLDao.obtenerPorSql(sql, AnxProvinciaCantonTO.class);
    }

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxParroquiaTO(String codigoProvincia, String codigoCanton)
            throws Exception {
        codigoProvincia = codigoProvincia == null ? codigoProvincia : "'" + codigoProvincia + "'";
        codigoCanton = codigoCanton == null ? codigoCanton : "'" + codigoCanton + "'";
        String sql = "SELECT codigo_parroquia as codigo_provincia, nombre_parroquia as nombre_provincia"
                + " FROM anexo.anx_dpa_ecuador " + "WHERE codigo_canton = " + codigoCanton + " "
                + "AND codigo_provincia = " + codigoProvincia + " " + "ORDER BY nombre_parroquia;";
        return genericSQLDao.obtenerPorSql(sql, AnxProvinciaCantonTO.class);
    }

    @Override
    public String getObtenerProvincia(String canton) throws Exception {
        String provincia = "";
        String sql = "SELECT * FROM anexo.fun_obtener_provincia('" + canton + "');";
        Object[] array = UtilsConversiones.convertirListToArray(genericSQLDao.obtenerPorSql(sql), 0);

        if (array != null) {
            provincia = array[0].toString().trim();
        } else {
            provincia = "";
        }
        return provincia;
    }

    @Override
    public List<AnxFunRegistroDatosCrediticiosTO> getFunRegistroDatosCrediticiosTOs(String codigoEmpresa,
            String fechaDesde, String fechaHasta) throws Exception {
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM anexo.fun_registro_datos_crediticios('" + codigoEmpresa + "'," + fechaDesde + ", "
                + fechaHasta + ");";
        return genericSQLDao.obtenerPorSql(sql, AnxFunRegistroDatosCrediticiosTO.class);

    }

    public List<String> getListadoCantonesAllTO() throws Exception {
        String sql = "SELECT nombre_canton FROM anexo.anx_dpa_ecuador GROUP BY codigo_provincia, codigo_canton, nombre_canton ORDER BY nombre_canton";
        return genericSQLDao.obtenerPorSql(sql);
    }
}
