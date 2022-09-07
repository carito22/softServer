/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaAportes;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Trabajo
 */
public interface ContablePlanillaAportesService {

    public Map<String, Object> obtenerDatosParaPlanillaAportes(String empresa, boolean mostrarTodos) throws Exception;

    public Map<String, Object> obtenerDatosParaPlanillaAportesFormulario(String empresa, boolean mostrarTodos) throws Exception;

    public List<ConContablePlanillaAportes> getListaConContablePlanillaAportes(String empresa, String sector, String periodo) throws Exception;

    @Transactional
    public MensajeTO insertarConContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarConContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO eliminarConContablePlanillaAportes(Integer secuencial, SisInfoTO sisInfoTO) throws Exception;
}
