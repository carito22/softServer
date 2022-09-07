package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProveedorDao extends GenericDao<InvProveedor, InvProveedorPK> {

    public InvProveedor buscarInvProveedor(String empresa, String codigoProveedor) throws Exception;

    public boolean eliminarInvProveedor(InvProveedor InvProveedor, SisSuceso sisSuceso) throws Exception;

    public boolean insertarInvProveedor(InvProveedor invProveedor, SisSuceso sisSuceso, InvNumeracionVarios invNumeracionVarios) throws Exception;

    public boolean modificarInvProveedor(InvProveedor invProveedor, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvProveedorLlavePrincipal(InvProveedor invProveedorEliminar, InvProveedor invProveedor, SisSuceso sisSuceso) throws Exception;

    public String getInvProximaNumeracionProveedor(String empresa, InvProveedorTO invProveedorTO) throws Exception;

    public Boolean buscarConteoProveedor(String empCodigo, String codigoProveedor) throws Exception;

    public boolean comprobarInvAplicaRetencionIva(String empresa, String codigo) throws Exception;

    public InvProveedorTO getBuscaCedulaProveedorTO(String empresa, String cedRuc) throws Exception;

    public List<InvListaProveedoresTO> getListaProveedoresTO(String empresa, String busqueda, boolean incluirProveedorInactivo) throws Exception;

    public List<InvProveedorTO> getProveedorTO(String empresa, String codigo) throws Exception;

    public List<InvFunListadoProveedoresTO> getInvFunListadoProveedoresTO(String empresa, String categoria) throws Exception;

    public InvProveedor obtenerInvProveedorPorCedulaRuc(String empresa, String ruc);

    public List<InvProveedorSinMovimientoTO> obtenerProveedoresSinMovimientos(String empresa) throws Exception;

    /*getListProveedorTO*/
    public boolean getProveedorRepetido(String empresa, String codigo, String id, String nombre, String razonSocial) throws Exception;

    public List<InvProveedorTO> getListProveedorTO(String empresa, String codigoCategoria, boolean inactivos, String busqueda) throws Exception;
}
