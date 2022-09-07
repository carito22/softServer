package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ProductoTipoService {

    public List<InvProductoTipoComboTO> getInvProductoTipoComboListadoTO(String empresa, String accion) throws Exception;

    public String accionInvProductoTipo(InvProductoTipoTO invProductoTipoTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public InvProductoTipo buscarInvProductoTipo(String empresa, String codigo) throws Exception;

    public Boolean comprobarEliminarInvProductoTipo(String empresa, String codigo) throws Exception;

    public boolean comprobarInvProductoTipoActivoFijo(String empresa, String codigo) throws Exception;

    public boolean eliminarInvProductoTipo(InvProductoTipoPK invProductoTipoPK, SisInfoTO sisInfoTO) throws Exception;
}
