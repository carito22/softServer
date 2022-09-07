/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoRelacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoRelacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface PiscinaGrupoRelacionDao extends GenericDao<PrdPiscinaGrupoRelacion, Integer> {

    public PrdPiscinaGrupoRelacion obtenerPiscinaGrupoRelacion(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception;

    public List<PrdPiscinaGrupoRelacionTO> listarPiscinaGrupoRelacionTO(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception;

    public List<PrdPiscinaGrupoRelacion> listarPiscinaGrupoRelacion(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception;

    @Transactional
    public boolean insertarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacion prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public boolean modificarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacion prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public boolean eliminarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacion prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception;
}
