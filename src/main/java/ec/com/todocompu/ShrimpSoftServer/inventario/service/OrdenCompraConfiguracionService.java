package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvOrdenCompraMotivoDetalleAprobadoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivoPK;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;

@Transactional
public interface OrdenCompraConfiguracionService {

    public List<InvOrdenCompraMotivoDetalleAprobadoresTO> getListaInvOrdenCompraMotivoDetalleAprobadoresTO(InvPedidosOrdenCompraMotivoPK pk, SisInfoTO sisInfoTO) throws Exception;

    public boolean insertarInvOrdenCompraConfiguracionTO(List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaConfiguracionDeOrdenCompra(String empresa, SisInfoTO sisInfoTO) throws Exception;
    
}
