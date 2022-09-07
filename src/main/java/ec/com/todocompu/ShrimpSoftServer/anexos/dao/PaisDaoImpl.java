package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPais;

@Repository
public class PaisDaoImpl extends GenericDaoImpl<AnxPais, String> implements PaisDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxPaisTO> getComboAnxPaisTO() throws Exception {
        String sql = "SELECT pais_codigo, pais_nombre FROM anexo.anx_pais ORDER BY pais_nombre";
        return genericSQLDao.obtenerPorSql(sql, AnxPaisTO.class);
    }

    @Override
    public AnxPaisTO getLocalAnxPaisTO() throws Exception {
        String sql = "SELECT pais_codigo, pais_nombre FROM anexo.anx_pais WHERE pais_codigo = '593' ORDER BY pais_nombre";
        return genericSQLDao.obtenerObjetoPorSql(sql, AnxPaisTO.class);
    }

    @Override
    public AnxPaisTO getAnxPaisTO(String codigo) throws Exception {
        String sql = "SELECT pais_codigo, pais_nombre FROM anexo.anx_pais WHERE pais_codigo = '" + codigo + "' ORDER BY pais_nombre";
        return genericSQLDao.obtenerObjetoPorSql(sql, AnxPaisTO.class);
    }

}
