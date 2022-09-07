/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.service;

import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosPK;
import ec.com.todocompu.ShrimpSoftUtils.isp.TO.FirewallContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.isp.entity.IspReporteArcotel;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface MikrotikService {

    public boolean conexion(String ip, String name, String password) throws Exception;

    public List<Map<String, String>> listarFirewallRules(String empresa) throws Exception;

    public List<Map<String, String>> listarFirewallAddressList(String empresa) throws Exception;

    public List<FirewallContratoTO> listarClientesAddressFirewall(String empresa) throws Exception;

    public String habilitarFirewall(String empresa, String id, boolean habilitar) throws Exception;

    public String actualizarEstadoFirewallAddress(String empresa, String ip, boolean habilitar, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosFirewallAddress(String empresa, String usuarioCodigo) throws Exception;

    public List<IspReporteArcotel> listarIspReporteArcotel(String empresa, String busqueda) throws Exception;

    public Map<String, Object> actualizarIpContratosListado(SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> cortarServicioPorLote(SisInfoTO sisInfoTO, List<CuentasPorCobrarDetalladoTO> listaEnviar) throws Exception;

    @Transactional
    public Map<String, Object> activarServicioEnCobros(SisInfoTO sisInfoTO, CarCobrosPK pk) throws Exception;
}
