package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaProveedorComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProveedorCategoriaDao extends GenericDao<InvProveedorCategoria, InvProveedorCategoriaPK> {

    public Boolean accionInvProveedorCategoria(InvProveedorCategoria invProveedorCategoria, SisSuceso sisSuceso,
            char accion) throws Exception;

    public InvProveedorCategoria buscarInvProveedorCategoria(String empresa, String codigo) throws Exception;

    public InvProveedorCategoria buscarInvProveedorCategoria(String empresa, String codigo, String detalle) throws Exception;

    public boolean comprobarInvProveedorCategoria(String empresa, String codigo) throws Exception;

    public List<InvProveedorCategoriaTO> getInvProveedorCategoriaTO(String empresa) throws Exception;

    public List<InvCategoriaProveedorComboTO> getListaCategoriaProveedorComboTO(String empresa) throws Exception;

    public boolean comprobarEliminarInvProveedorCategoria(String empresa, String codigo) throws Exception;

}
