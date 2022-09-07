package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaComboProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface ProductoCategoriaService {

    public boolean comprobarInvProductoCategoria(String empresa, String codigoCategoria) throws Exception;

    public InvProductoCategoria buscarInvProductoCategoria(String empresa, String codigo) throws Exception;

    public List<InvProductoCategoriaTO> getInvProductoCategoriaTO(String empresa) throws Exception;

    public List<InvCategoriaComboProductoTO> getListaCategoriaComboTO(String empresa) throws Exception;

    public Boolean getPrecioFijoCategoriaProducto(String empresa, String codigoCategoria) throws Exception;

    public String accionInvProductoCategoria(InvProductoCategoriaTO invProductoCategoriaTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarInvProductoCategoria(InvProductoCategoriaPK invProductoCategoriaPK, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> validacionGrupoSegunCategoria(String empresa, String codigoCategoria, String cuentaInventario, String cuentaVenta, String cuentaCostoAutomatico, String cuentaGasto, String cuentaVentaExt, boolean isExportadora, boolean isActivoFijo) throws Exception;
}
