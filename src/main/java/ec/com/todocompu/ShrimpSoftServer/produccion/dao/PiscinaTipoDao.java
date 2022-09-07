/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface PiscinaTipoDao extends GenericDao<PrdPiscinaTipo, PrdPiscinaTipoPK> {

    public List<PrdPiscinaTipo> listarPiscinaTipo(String empresa, boolean filtrarInactivos);

@Transactional
    public boolean insertarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTipo, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public boolean modificarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTipo, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public boolean eliminarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTipo, SisSuceso sisSuceso) throws Exception;
}
