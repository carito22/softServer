package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxProvincias;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxProvinciasPK;

@Repository
public class ProvinciasDaoImpl extends GenericDaoImpl<AnxProvincias, AnxProvinciasPK> implements ProvinciasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxProvinciaTO() throws Exception {
        String sql = "SELECT provincia_codigo as codigo_provincia, provincia_nombre as nombre_provincia"
                + " FROM anexo.anx_provincias" + " GROUP BY codigo_provincia, nombre_provincia"
                + " ORDER BY nombre_provincia;";
        return genericSQLDao.obtenerPorSql(sql, AnxProvinciaCantonTO.class);
    }

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxCantonTO(String provincia) throws Exception {
        provincia = provincia == null ? provincia : "'" + provincia + "'";
        String sql = "SELECT canton_codigo as codigo_provincia, canton_nombre as nombre_provincia "
                + "FROM anexo.anx_provincias " + "WHERE provincia_codigo=" + provincia + " "
                + "ORDER BY canton_nombre;";
        return genericSQLDao.obtenerPorSql(sql, AnxProvinciaCantonTO.class);
    }

    @Override
    public AnxProvinciaCantonTO obtenerProvincia(String provincia) throws Exception {
        provincia = provincia == null ? provincia : "'" + provincia + "'";
        String sql = "SELECT provincia_codigo as codigo_provincia, provincia_nombre as nombre_provincia "
                + "FROM anexo.anx_provincias " + "WHERE provincia_codigo=" + provincia + " "
                + "ORDER BY canton_nombre;";
        List<AnxProvinciaCantonTO> resultado = genericSQLDao.obtenerPorSql(sql, AnxProvinciaCantonTO.class);
        if (resultado != null && !resultado.isEmpty()) {
            return resultado.get(0);
        }
        return null;
    }

    @Override
    public AnxProvinciaCantonTO obtenerCanton(String provincia, String canton) throws Exception {
        provincia = provincia == null ? provincia : "'" + provincia + "'";
        canton = canton == null ? canton : "'" + canton + "'";
        String sql = "SELECT canton_codigo as codigo_provincia, canton_nombre as nombre_provincia "
                + "FROM anexo.anx_provincias " + "WHERE provincia_codigo=" + provincia + " "
                + "AND canton_codigo = " + canton + " "
                + "ORDER BY canton_nombre;";
        List<AnxProvinciaCantonTO> resultado = genericSQLDao.obtenerPorSql(sql, AnxProvinciaCantonTO.class);
        if (resultado != null && !resultado.isEmpty()) {
            return resultado.get(0);
        }
        return null;
    }

}
