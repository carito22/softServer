package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSobrevivencia;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;

@Repository
public class SobrevivenciaDaoImpl extends GenericDaoImpl<PrdSobrevivencia, Integer> implements SobrevivenciaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public boolean insertarPrdSobrevivencia(PrdSobrevivencia prdSobrevivencia, SisSuceso sisSuceso) throws Exception {
        insertar(prdSobrevivencia);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public PrdSobrevivencia insertarSobrevivencia(PrdSobrevivencia prdSobrevivencia, SisSuceso sisSuceso) throws Exception {
        insertar(prdSobrevivencia);
        sisSucesoDao.insertar(sisSuceso);
        return prdSobrevivencia;
    }

    @Override
    public boolean modificarPrdSobrevivencia(PrdSobrevivencia prdSobrevivencia, SisSuceso sisSuceso) throws Exception {
        actualizar(prdSobrevivencia);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdSobrevivencia(PrdSobrevivencia prdSobrevivencia, SisSuceso sisSuceso) throws Exception {
        eliminar(prdSobrevivencia);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<PrdListaSobrevivenciaTO> getListaSobrevivencia(String empresa, String sector) throws Exception {
        String sql = "SELECT sob_codigo, sob_dias_desde, sob_dias_hasta, sob_sobrevivencia, sob_alimentacion "
                + "FROM produccion.prd_sobrevivencia WHERE sec_empresa = ('" + empresa + "') AND " + "sec_codigo = ('"
                + sector + "') ORDER BY sob_dias_desde";
        return genericSQLDao.obtenerPorSql(sql, PrdListaSobrevivenciaTO.class);
    }

    @Override
    public List<PrdListaSobrevivenciaTO> sobrevivenciaPorDefecto(String empresa, String sector) throws Exception {
        List<PrdListaSobrevivenciaTO> listaSobrevivencia = new ArrayList<>();
        String sql = "SELECT * FROM produccion.fun_sobrevivencia('" + empresa + "','" + sector + "')";

        boolean respuestaInsercion = (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
        if (respuestaInsercion) {
            listaSobrevivencia = getListaSobrevivencia(empresa, sector);
        }

        return listaSobrevivencia;
    }

}
