/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Trabajo
 */
public interface PrdReprocesoService {

    public List<PrdReprocesoTO> getListarPrdReprocesoTO(String empresa, String motivo, String nrRegistros) throws Exception;

    public Map<String, Object> obtenerDatosParaPrdProceso(Map<String, Object> map) throws Exception;

    @Transactional
    public Map<String, Object> guardarReproceso(Map<String, Object> map) throws Exception;

    @Transactional
    public Map<String, Object> modificarReproceso(Map<String, Object> map) throws Exception;

    @Transactional
    public String desmayorizarPrdReproceso(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String anularPrdReproceso(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String restaurarPrdReproceso(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

}
