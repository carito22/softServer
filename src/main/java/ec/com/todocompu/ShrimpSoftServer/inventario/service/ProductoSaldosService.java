package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvKardexTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioCantidadTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalle;
import java.util.Map;

public interface ProductoSaldosService {

    @Transactional
    public List<InvListaProductosTO> getListaProductosTO(String empresa, String busqueda, String bodega, String categoria, String fecha, boolean incluirInactivos, boolean limite, boolean codigo) throws Exception;

    @Transactional
    public InvProductoSaldos getInvProductoSaldo(String empresa, String codigoBodega, String codigoProducto) throws Exception;

    @Transactional
    public List<InvKardexTO> getListaInvKardexTO(String empresa, String bodega, String producto, String desde, String hasta, String promedio) throws Exception;

    @Transactional
    public List<InvKardexTO> getListaInvKardexTO(String empresa, String bodega, String producto, String desde, String hasta, String promedio, boolean incluirTodos) throws Exception;

    @Transactional
    public List<InvListaProductosCambiarPrecioTO> getListaProductosCambiarPrecioTO(String empresa, String busqueda, String bodega, String fecha) throws Exception;

    @Transactional
    public List<InvListaProductosCambiarPrecioCantidadTO> getListaProductosCambiarPrecioCantidadTO(String empresa, String busqueda, String bodega, String fecha) throws Exception;

    public RetornoTO getInvFunListaProductosSaldosGeneralTO(String empresa, String producto, String fecha, boolean estado, String usuario) throws Exception;

    @Transactional
    public List<String> verificarStockVentas(List<InvVentasDetalle> listInvVentasDetalle) throws Exception;

    public List<InvListaProductosGeneralTO> getListaProductosTOWeb(String empresa, String busqueda, String bodega, String categoria, String fecha, Integer precio, boolean incluirInactivos, boolean limite, boolean codigo, String buscarPorcodigo, String tipo) throws Exception;

    public Map<String, Object> obtenerDatosParaKardex(String empresa) throws Exception;

    public List<InvListaProductosGeneralTO> importarProductosSinStock(String empresa, String categoria) throws Exception;

    public List<InvListaProductosCambiarPrecioTO> formatearListadoInvListaProductosCambiarPrecioTO(List<InvListaProductosCambiarPrecioTO> listado) throws Exception;

}
