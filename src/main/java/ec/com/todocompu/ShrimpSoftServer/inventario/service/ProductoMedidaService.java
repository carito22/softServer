package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvMedidaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMedidaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedida;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedidaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ProductoMedidaService {

    public InvProductoMedida buscarProductoMedida(String empresa, String codigo) throws Exception;

    public List<InvProductoMedidaTO> getInvProductoMedidaTO(String empresa) throws Exception;

    public List<InvMedidaComboTO> getListaInvMedidaTablaTO(String empresa) throws Exception;

    public String accionInvProductoMedida(InvProductoMedidaTO invProductoMedidaTO, char accion, SisInfoTO sisInfoTO)
            throws Exception;

    public boolean eliminarInvProductoMedida(InvProductoMedidaPK invProductoMedidaPK, SisInfoTO sisInfoTO) throws Exception;

}
