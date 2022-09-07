/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Trabajo
 */
public interface PrdReprocesoMotivoService {

    public boolean comprobarPrdReprocesoMotivo(String empresa, String motCodigo) throws Exception;

    public List<PrdReprocesoMotivo> listarPrdReprocesoMotivo(String empresa, boolean incluirTodos) throws Exception;

    @Transactional
    public String modificarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public String modificarEstadoPrdReprocesoMotivo(PrdReprocesoMotivo motivo, boolean inactivo, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String eliminarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public String insertarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisInfoTO sisInfoTO)
            throws Exception;
}
