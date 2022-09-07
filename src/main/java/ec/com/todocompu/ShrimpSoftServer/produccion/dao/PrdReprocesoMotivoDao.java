/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface PrdReprocesoMotivoDao extends GenericDao<PrdReprocesoMotivo, PrdReprocesoMotivoPK> {

    public boolean comprobarPrdReprocesoMotivo(String empresa, String motCodigo) throws Exception;

    public List<PrdReprocesoMotivo> listarPrdReprocesoMotivo(String empresa, boolean incluirTodos);

    public boolean insertarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisSuceso sisSuceso)
            throws Exception;

    public boolean modificarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisSuceso sisSuceso)
            throws Exception;

    public boolean eliminarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisSuceso sisSuceso)
            throws Exception;

    public boolean banderaEliminarPrdReprocesoMotivo(String empresa, String codigo) throws Exception;
}
