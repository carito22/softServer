package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPorcentajeIce;

@Repository
public class AnexoPorcentajeIceDaoImpl extends GenericDaoImpl<AnxPorcentajeIce, Integer> implements AnexoPorcentajeIceDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxPorcentajeIce> listarAnexoPorcentajeIce() throws Exception {
        String sql = "SELECT * FROM anexo.anx_porcentaje_ice ";
        return genericSQLDao.obtenerPorSql(sql, AnxPorcentajeIce.class);
    }

}
