package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosConfiguracionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface PedidosConfiguracionService {

    public InvPedidosConfiguracionTO getListaInvPedidosConfiguracionTO(InvPedidosMotivoPK invPedidosMotivoPK, SisInfoTO sisInfoTO) throws Exception;

    public String insertarInvPedidosConfiguracionTO(InvPedidosConfiguracionTO invPedidosConfiguracionTO, SisInfoTO sisInfoTO) throws Exception;

    public InvPedidosMotivo insertarMotivoPedidoConfiguracionTO(InvPedidosConfiguracionTO invPedidosConfiguracionTO, InvPedidosMotivo invPedidosMotivo, boolean parametro, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaConfiguracionDePedidos(String empresa, SisInfoTO sisInfoTO) throws Exception;

}
