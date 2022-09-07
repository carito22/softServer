/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoRelacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoRelacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface PiscinaGrupoRelacionService {

    public List<PrdPiscinaGrupoRelacionTO> listarPiscinaGrupoRelacionTO(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception;

    public List<PrdPiscinaGrupoRelacion> listarPiscinaGrupoRelacion(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception;

    @Transactional
    public PrdPiscinaGrupoRelacion insertarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacionTO prdPiscinaGrupo, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public PrdPiscinaGrupoRelacion modificarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacionTO prdPiscinaGrupo, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public PrdPiscinaGrupoRelacion eliminarPrdPiscinaGrupoRelacion(Integer secuencial, SisInfoTO sisInfoTO) throws Exception;
}
