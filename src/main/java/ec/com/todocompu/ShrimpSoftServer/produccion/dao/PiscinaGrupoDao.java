/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface PiscinaGrupoDao extends GenericDao<PrdPiscinaGrupo, PrdPiscinaGrupoPK> {

    public List<PrdPiscinaGrupo> listarPiscinaGrupo(String empresa, boolean filtrarInactivos);

    public List<PrdPiscinaGrupoTO> listarPiscinaGrupoTO(String empresa, boolean filtrarInactivos);

    @Transactional
    public boolean insertarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public boolean modificarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public boolean eliminarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisSuceso sisSuceso) throws Exception;
}
