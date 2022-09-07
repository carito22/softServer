/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametrosMikrotik;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
@Transactional
public interface SisEmpresaParametrosMikrotikService {

    public SisEmpresaParametrosMikrotik obtenerConfiguracionMikrotik(String empresa) throws Exception;

    public SisEmpresaParametrosMikrotik insertarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik, SisInfoTO sisInfoTO) throws Exception;

    public SisEmpresaParametrosMikrotik modificarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarSisEmpresaParametrosMikrotik(Integer secuencial, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> listarContratosWispro(String empresa) throws Exception;

    public Map<String, Object> listarContratos(String empresa) throws Exception;

    public Map<String, Object> consultarContrato(Map<String, Object> datos) throws Exception;

    public Map<String, Object> actualizarEstadoContrato(Map<String, Object> datos) throws Exception;
}
