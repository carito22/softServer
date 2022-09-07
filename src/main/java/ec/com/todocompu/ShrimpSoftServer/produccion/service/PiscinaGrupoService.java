/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface PiscinaGrupoService {

    public List<PrdPiscinaGrupo> listarPiscinaGrupo(String empresa, boolean filtrarInactivos) throws Exception;

    public List<PrdPiscinaGrupoTO> listarPiscinaGrupoTO(String empresa, boolean filtrarInactivos) throws Exception;

    @Transactional
    public boolean insertarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean modificarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean modificarEstadoPiscinaGrupo(PrdPiscinaGrupoPK pk, boolean activar, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarPrdPiscinaGrupo(PrdPiscinaGrupoPK pk, SisInfoTO sisInfoTO) throws Exception;
}
