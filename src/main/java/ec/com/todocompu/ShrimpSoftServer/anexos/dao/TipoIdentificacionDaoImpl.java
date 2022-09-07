package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoIdentificacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTipoidentificacion;

@Repository
public class TipoIdentificacionDaoImpl extends GenericDaoImpl<AnxTipoidentificacion, Character>
        implements TipoIdentificacionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxTipoIdentificacionTO> getListaAnxTipoIdentificacionTO() throws Exception {
        String sql = "SELECT * FROM anexo.anx_tipoidentificacion";
        return genericSQLDao.obtenerPorSql(sql, AnxTipoIdentificacionTO.class);
    }

    @Override
    public AnxTipoIdentificacionTO getAnxTipoIdentificacion(String codigo) throws Exception {
        String sql = "SELECT * FROM anexo.anx_tipoidentificacion where ti_codigo = '" + codigo + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, AnxTipoIdentificacionTO.class);
    }

}
