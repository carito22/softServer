/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoRelacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoRelacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class PiscinaGrupoRelacionDaoImpl extends GenericDaoImpl<PrdPiscinaGrupoRelacion, Integer> implements PiscinaGrupoRelacionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public PrdPiscinaGrupoRelacion obtenerPiscinaGrupoRelacion(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception {
        String sql;
        String sqlComplemento = " grupo_empresa='" + grupoPK.getGrupoEmpresa() + "' and grupo_codigo='" + grupoPK.getGrupoCodigo() + "'"
                + " pis_empresa='" + prdPiscinaPK.getPisEmpresa()
                + "' and pis_sector='" + prdPiscinaPK.getPisSector()
                + "' and pis_numero='" + prdPiscinaPK.getPisNumero() + "'";

        sql = "SELECT * FROM produccion.prd_piscina_grupos_relacion "
                + (sqlComplemento.equals("") ? "" : " where " + sqlComplemento);
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdPiscinaGrupoRelacion.class);
    }

    @Override
    public List<PrdPiscinaGrupoRelacionTO> listarPiscinaGrupoRelacionTO(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception {
        String sql;
        String sqlComplemento = "";
        if (grupoPK != null) {
            sqlComplemento = " grupo_empresa='" + grupoPK.getGrupoEmpresa() + "' and grupo_codigo='" + grupoPK.getGrupoCodigo() + "'";
        }
        if (prdPiscinaPK != null) {
            sqlComplemento += (sqlComplemento.equals("") ? "" : " AND ");
            sqlComplemento += " pis_empresa='" + prdPiscinaPK.getPisEmpresa()
                    + "' and pis_sector='" + prdPiscinaPK.getPisSector()
                    + "' and pis_numero='" + prdPiscinaPK.getPisNumero() + "'";
        }

        sql = "SELECT grupo_secuencial,pis_empresa,pis_sector,pis_numero,grupo_empresa,grupo_codigo,usr_empresa,usr_codigo,usr_fecha_inserta FROM produccion.prd_piscina_grupos_relacion "
                + (sqlComplemento.equals("") ? "" : " where " + sqlComplemento);
        return genericSQLDao.obtenerPorSql(sql, PrdPiscinaGrupoRelacionTO.class);
    }

    @Override
    public List<PrdPiscinaGrupoRelacion> listarPiscinaGrupoRelacion(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception {
        String sql;
        String sqlComplemento = "";
        if (grupoPK != null) {
            sqlComplemento = " grupo_empresa='" + grupoPK.getGrupoEmpresa() + "' and grupo_codigo='" + grupoPK.getGrupoCodigo() + "'";
        }
        if (prdPiscinaPK != null) {
            sqlComplemento += (sqlComplemento.equals("") ? "" : " AND ");
            sqlComplemento += " pis_empresa='" + prdPiscinaPK.getPisEmpresa()
                    + "' and pis_sector='" + prdPiscinaPK.getPisSector()
                    + "' and pis_numero='" + prdPiscinaPK.getPisNumero() + "'";
        }

        sql = "SELECT * FROM produccion.prd_piscina_grupos_relacion "
                + (sqlComplemento.equals("") ? "" : " where " + sqlComplemento);
        return genericSQLDao.obtenerPorSql(sql, PrdPiscinaGrupoRelacion.class);
    }

    @Override
    public boolean insertarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacion prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception {
        insertar(prdPiscinaGrupo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacion prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception {
        actualizar(prdPiscinaGrupo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacion prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception {
        eliminar(prdPiscinaGrupo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

}
