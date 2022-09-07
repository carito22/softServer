/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class PiscinaTipoDaoImpl extends GenericDaoImpl<PrdPiscinaTipo, PrdPiscinaTipoPK> implements PiscinaTipoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public List<PrdPiscinaTipo> listarPiscinaTipo(String empresa, boolean filtrarInactivos) {
        String sql;
        if (!filtrarInactivos) {
            sql = "SELECT * FROM produccion.prd_piscina_tipo "
                    + "WHERE tipo_empresa = ('" + empresa + "') ORDER BY tipo_descripcion";
        } else {
            sql = "SELECT * FROM produccion.prd_piscina_tipo "
                    + "WHERE tipo_empresa = ('" + empresa + "') AND tipo_activo ORDER BY tipo_descripcion";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdPiscinaTipo.class);
    }

    @Override
    public boolean insertarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTipo, SisSuceso sisSuceso) throws Exception {
        insertar(prdPiscinaTipo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTipo, SisSuceso sisSuceso) throws Exception {
        actualizar(prdPiscinaTipo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTipo, SisSuceso sisSuceso) throws Exception {
        eliminar(prdPiscinaTipo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

}
