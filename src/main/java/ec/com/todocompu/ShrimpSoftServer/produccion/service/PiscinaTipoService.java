/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface PiscinaTipoService {

    public List<PrdPiscinaTipo> listarPiscinaTipo(String empresa, boolean filtrarInactivos) throws Exception;

    @Transactional
    public boolean insertarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean modificarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean modificarEstadoPiscinaTipo(PrdPiscinaTipoPK pk, boolean activar, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarPrdPiscinaTipo(PrdPiscinaTipoPK pk, SisInfoTO sisInfoTO) throws Exception;
}
