package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProductoTipoDao extends GenericDao<InvProductoTipo, InvProductoTipoPK> {

    public Boolean accionInvProductoTipo(InvProductoTipo invProductoTipo, SisSuceso sisSuceso, char accion) throws Exception;

    public InvProductoTipo buscarInvProductoTipo(String empresa, String codigo) throws Exception;

    public boolean comprobarInvProductoTipo(String empresa, String codigo) throws Exception;

    public boolean comprobarInvProductoTipoActivoFijo(String empresa, String codigo) throws Exception;

    public List<InvProductoTipoComboTO> getInvProductoTipoComboListadoTO(String empresa, String accion) throws Exception;

    public List<InvProductoTipoComboTO> getInvProductoTipoComboListadoTOActivoFijo(String empresa, String accion) throws Exception;

    public Boolean comprobarEliminarInvProductoTipo(String empresa, String codigo) throws Exception;

}
