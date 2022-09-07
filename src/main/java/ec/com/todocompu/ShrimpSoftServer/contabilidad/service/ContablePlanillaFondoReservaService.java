/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaFondoReserva;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Trabajo
 */
public interface ContablePlanillaFondoReservaService {

    public Map<String, Object> obtenerDatosParaPlanillaFondoReserva(String empresa, boolean mostrarTodos) throws Exception;

    public Map<String, Object> obtenerDatosParaPlanillaFondoReservaFormulario(String empresa, boolean mostrarTodos) throws Exception;

    public List<ConContablePlanillaFondoReserva> getListaConContablePlanillaFondoReserva(String empresa, String sector, String periodo) throws Exception;

    @Transactional
    public MensajeTO insertarConContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarConContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO eliminarConContablePlanillaFondoReserva(Integer secuencial, SisInfoTO sisInfoTO) throws Exception;
}
