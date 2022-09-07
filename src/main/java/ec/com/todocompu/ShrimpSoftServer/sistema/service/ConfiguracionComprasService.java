/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionCompras;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionComprasPK;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CarolValdiviezo
 */
public interface ConfiguracionComprasService {

    public List<SisConfiguracionCompras> listarSisConfiguracionCompras(String empresa) throws Exception;

    public SisConfiguracionCompras getSisConfiguracionCompras(SisConfiguracionComprasPK pk) throws Exception;

    public Map<String, Object> obtenerDatosParaSisConfiguracionCompras(String empresa, String sector, boolean isNuevo, SisInfoTO sisInfoTO) throws Exception;

    public SisConfiguracionCompras insertarSisConfiguracionCompras(SisConfiguracionCompras configuracionCompras, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public SisConfiguracionCompras modificarSisConfiguracionCompras(SisConfiguracionCompras configuracionCompras, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public boolean eliminarSisConfiguracionCompras(SisConfiguracionComprasPK pk, SisInfoTO sisInfoTO) throws GeneralException;
}
