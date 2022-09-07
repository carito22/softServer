package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListaProductosSaldosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvKardexTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioCantidadTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;

public interface ProductoSaldosDao extends GenericDao<InvProductoSaldos, Integer> {

    public List<InvListaProductosCambiarPrecioCantidadTO> getListaProductosCambiarPrecioCantidadTO(String empresa, String busqueda, String bodega, String fecha) throws Exception;

    public List<InvListaProductosCambiarPrecioTO> getListaProductosCambiarPrecioTO(String empresa, String busqueda, String bodega, String fecha) throws Exception;

    public List<InvListaProductosTO> getListaProductosTO(String empresa, String busqueda, String bodega, String categoria, String fecha, boolean incluirInactivos, boolean limite, boolean codigo) throws Exception;

    public List<InvFunListaProductosSaldosGeneralTO> getInvFunListaProductosSaldosGeneralTO(String empresa, String producto, String fecha, boolean estado) throws Exception;

    public InvProductoSaldos getInvProductoSaldo(String pro_empresa, String bodega, String producto) throws Exception;

    public List<InvKardexTO> getListaInvKardexTO(String empresa, String bodega, String producto, String desde, String hasta, String promedio) throws Exception;

    public List<InvKardexTO> getListaInvKardexTO(String empresa, String bodega, String producto, String desde, String hasta, String promedio, boolean incluirTodos) throws Exception;

    public List<InvListaProductosGeneralTO> getListaProductosTOWeb(String empresa, String busqueda, String bodega, String categoria, String fecha,
            Integer precio, boolean incluirInactivos, boolean limite, boolean codigo, String buscarPorcodigo, String tipo) throws Exception;

    public List<InvListaProductosGeneralTO> importarProductosSinStock(String empresa, String categoria) throws Exception;
}
