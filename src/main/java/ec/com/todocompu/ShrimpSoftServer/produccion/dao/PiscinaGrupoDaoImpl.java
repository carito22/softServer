/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class PiscinaGrupoDaoImpl extends GenericDaoImpl<PrdPiscinaGrupo, PrdPiscinaGrupoPK> implements PiscinaGrupoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public List<PrdPiscinaGrupo> listarPiscinaGrupo(String empresa, boolean filtrarInactivos) {
        String sql;
        if (!filtrarInactivos) {
            sql = "SELECT * FROM produccion.prd_piscina_grupos "
                    + "WHERE grupo_empresa = ('" + empresa + "') ORDER BY grupo_descripcion";
        } else {
            sql = "SELECT * FROM produccion.prd_piscina_grupos "
                    + "WHERE grupo_empresa = ('" + empresa + "') AND grupo_activo ORDER BY grupo_descripcion";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdPiscinaGrupo.class);
    }

    @Override
    public List<PrdPiscinaGrupoTO> listarPiscinaGrupoTO(String empresa, boolean filtrarInactivos) {
        String sql;
        if (!filtrarInactivos) {
            sql = "SELECT ROW_NUMBER() OVER() AS id,grupo_empresa,grupo_codigo,grupo_descripcion,grupo_activo,usr_empresa,usr_codigo,usr_fecha_inserta FROM produccion.prd_piscina_grupos "
                    + "WHERE grupo_empresa = ('" + empresa + "') ORDER BY grupo_descripcion";
        } else {
            sql = "SELECT ROW_NUMBER() OVER() AS id,grupo_empresa,grupo_codigo,grupo_descripcion,grupo_activo,usr_empresa,usr_codigo,usr_fecha_inserta FROM produccion.prd_piscina_grupos "
                    + "WHERE grupo_empresa = ('" + empresa + "') AND grupo_activo ORDER BY grupo_descripcion";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdPiscinaGrupoTO.class);
    }

    @Override
    public boolean insertarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception {
        insertar(prdPiscinaGrupo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception {
        actualizar(prdPiscinaGrupo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception {
        eliminar(prdPiscinaGrupo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

}
