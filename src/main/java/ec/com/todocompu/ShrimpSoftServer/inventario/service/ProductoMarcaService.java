package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarcaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ProductoMarcaService {

    public List<InvProductoMarcaComboListadoTO> getInvMarcaComboListadoTO(String empresa) throws Exception;

    public String accionInvProductoMarcaTO(InvProductoMarcaTO invProductoMarcaTO, char accion, SisInfoTO sisInfoTO)
            throws Exception;

    public boolean eliminarInvProductoMarca(InvProductoMarcaPK invProductoMarcaPK, SisInfoTO sisInfoTO) throws Exception;

    public InvProductoMarca buscarMarcaProducto(String empresa, String codigoMarca) throws Exception;
}
