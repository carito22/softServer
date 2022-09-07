/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumosPK;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CarolValdiviezo
 */
public interface ConfiguracionConsumosService {

    public List<SisConfiguracionConsumos> listarSisConfiguracionConsumos(String empresa) throws Exception;

    public SisConfiguracionConsumos getSisConfiguracionConsumos(SisConfiguracionConsumosPK pk) throws Exception;

    public Map<String, Object> obtenerDatosParaSisConfiguracionConsumos(String empresa, String sector, SisInfoTO sisInfoTO) throws Exception;

    public SisConfiguracionConsumos insertarSisConfiguracionConsumos(SisConfiguracionConsumos configuracionConsumos, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public SisConfiguracionConsumos modificarSisConfiguracionConsumos(SisConfiguracionConsumos configuracionConsumos, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public boolean eliminarSisConfiguracionConsumos(SisConfiguracionConsumosPK pk, SisInfoTO sisInfoTO) throws GeneralException;
}
