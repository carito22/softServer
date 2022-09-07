package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListaProductosSaldosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvKardexTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioCantidadTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;

@Repository
public class ProductoSaldosDaoImpl extends GenericDaoImpl<InvProductoSaldos, Integer> implements ProductoSaldosDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvListaProductosCambiarPrecioCantidadTO> getListaProductosCambiarPrecioCantidadTO(String empresa,
            String busqueda, String bodega, String fecha) throws Exception {
        fecha = fecha == null ? null : fecha.isEmpty() ? null : "" + fecha + "";
        String sql = "SELECT lpsp_codigo_principal, lpsp_nombre, lpsp_categoria, lpsp_saldo, lpsp_ultimo_costo, lpsp_costo_promedio, lpsp_medida, lpsp_precio1, lpsp_cantidad1, "
                + "lpsp_precio2, lpsp_cantidad2, lpsp_precio3, lpsp_cantidad3, lpsp_precio4, lpsp_cantidad4, lpsp_precio5, lpsp_cantidad5, lpsp_iva, id FROM "
                + "inventario.fun_lista_productos_saldos_precios('" + empresa + "', '" + bodega + "', '', '" + busqueda
                + "', " + fecha + ", FALSE)";
        return genericSQLDao.obtenerPorSql(sql, InvListaProductosCambiarPrecioCantidadTO.class);
    }

    @Override
    public List<InvListaProductosCambiarPrecioTO> getListaProductosCambiarPrecioTO(String empresa, String busqueda,
            String bodega, String fecha) throws Exception {
        String sql = "SELECT lpsp_codigo_principal, lpsp_nombre, lpsp_categoria, lpsp_saldo, lpsp_ultimo_costo, lpsp_costo_promedio, lpsp_medida, "
                + "lpsp_precio1, lpsp_descuento1, lpsp_margen_utilidad1, lpsp_precio2, lpsp_descuento2, lpsp_margen_utilidad2, "
                + "lpsp_precio3, lpsp_descuento3, lpsp_margen_utilidad3, lpsp_precio4, lpsp_descuento4, lpsp_margen_utilidad4, "
                + "lpsp_precio5, lpsp_descuento5, lpsp_margen_utilidad5, lpsp_iva, id FROM "
                + "inventario.fun_lista_productos_saldos_precios('" + empresa + "', '" + bodega + "', '', '" + busqueda
                + "', " + fecha + ", FALSE)";
        return genericSQLDao.obtenerPorSql(sql, InvListaProductosCambiarPrecioTO.class);
    }

    @Override
    public List<InvListaProductosTO> getListaProductosTO(String empresa, String busqueda, String bodega,
            String categoria, String fecha, boolean incluirInactivos, boolean limite, boolean codigo) throws Exception {
        String sql;
        String porcionSql = codigo ? " where lpsp_codigo_principal='" + busqueda + "' or lpsp_codigo_barra='" + busqueda
                + "' or lpsp_codigo_barra2='" + busqueda + "' or lpsp_codigo_barra3='" + busqueda
                + "' or lpsp_codigo_barra4='" + busqueda + "' or lpsp_codigo_barra5='" + busqueda + "';" : "";
        if (categoria == null) {
            categoria = "";
        }
        fecha = fecha == null ? null
                : fecha.isEmpty() ? null : fecha.substring(0, 1).compareTo("'") == 0 ? fecha : "'" + fecha + "'";

        if (bodega == null) {
            sql = "SELECT lpsp_codigo_principal,  lpsp_codigo_barra, lpsp_codigo_barra2, "
                    + "lpsp_codigo_barra3, lpsp_codigo_barra4, lpsp_codigo_barra5, lpsp_nombre, "
                    + "lpsp_categoria, lpsp_empaque, lpsp_saldo, lpsp_ultimo_costo, lpsp_costo_promedio, lpsp_medida, "
                    + "lpsp_precio1, lpsp_precio2, lpsp_precio3, lpsp_precio4, lpsp_precio5, "
                    + "lpsp_precio3 * lpsp_cantidad3 lpsp_precio_caja, lpsp_iva, lpsp_porcentaje_ice, lpsp_tarifa_fija_ice, lpsp_ice_codigo, id "
                    + "FROM inventario.fun_lista_productos_saldos_precios('" + empresa + "', " + bodega + ", '"
                    + categoria + "', '" + busqueda + "', " + fecha + ", " + incluirInactivos + ")" + porcionSql + ";";
        } else {
            sql = "SELECT lpsp_codigo_principal,  lpsp_codigo_barra, lpsp_codigo_barra2, "
                    + "lpsp_codigo_barra3, lpsp_codigo_barra4, lpsp_codigo_barra5, lpsp_nombre, "
                    + "lpsp_categoria, lpsp_empaque, lpsp_saldo, lpsp_ultimo_costo, lpsp_costo_promedio, lpsp_medida, "
                    + "lpsp_precio1, lpsp_precio2, lpsp_precio3, lpsp_precio4, lpsp_precio5, "
                    + "lpsp_precio3 * lpsp_cantidad3 lpsp_precio_caja, lpsp_iva, lpsp_porcentaje_ice, lpsp_tarifa_fija_ice, lpsp_ice_codigo, id "
                    + "FROM inventario.fun_lista_productos_saldos_precios('" + empresa + "', '" + bodega + "', '"
                    + categoria + "', '" + busqueda + "', " + fecha + ", " + incluirInactivos + ")" + porcionSql + ";";
        }
        return genericSQLDao.obtenerPorSql(sql, InvListaProductosTO.class);
    }

    @Override
    public List<InvListaProductosGeneralTO> getListaProductosTOWeb(String empresa, String busqueda, String bodega, String categoria, String fecha,
            Integer precio, boolean incluirInactivos, boolean limite, boolean codigo, String buscarPorcodigo, String tipo) throws Exception {
        String porcionSql = " where true ";
        porcionSql = codigo ? porcionSql + " AND lpsp_codigo_principal='" + busqueda + "' " : porcionSql;
        if (categoria == null) {
            categoria = "";
        }
        if (buscarPorcodigo == null || buscarPorcodigo.equals("")) {
            buscarPorcodigo = null;
        } else {
            buscarPorcodigo = "'" + buscarPorcodigo + "'";
        }
        if (busqueda == null) {
            busqueda = "";
        }
        if (bodega == null) {
            bodega = "";
        }
        if (precio == null) {
            precio = 1;
        }

        porcionSql = tipo != null ? porcionSql + " AND lpsp_tipo LIKE '%" + tipo + "%' " : porcionSql;

        if (limite) {
            porcionSql = porcionSql + "LIMIT 1000 ";
        }
        fecha = fecha == null ? null
                : fecha.isEmpty() ? null : fecha.substring(0, 1).compareTo("'") == 0 ? fecha : "'" + fecha + "'";

        String sql = "SELECT lpsp_codigo_principal, lpsp_nombre, "
                + "lpsp_categoria, lpsp_empaque, lpsp_saldo, lpsp_ultimo_costo, lpsp_costo_promedio, lpsp_factor_conversion, lpsp_medida, "
                + "lpsp_cantidad, lpsp_base_imponible, lpsp_precio, lpsp_descuento, lpsp_margen_utilidad, lpsp_precio2, lpsp_precio3,"
                + "lpsp_iva , lpsp_inactivo, lpsp_tipo, lpsp_precio_fijo, lpsp_exigir_serie,lpsp_costo_referencial,lpsp_imagenes, lpsp_credito_tributario, lpsp_ecommerce,lpsp_tipo_tipo_producto, "
                + "lpsp_porcentaje_ice, lpsp_tarifa_fija_ice, lpsp_ice_codigo, id "
                + "FROM inventario.fun_lista_productos_saldos_precios_web('" + empresa + "', '" + bodega + "'," + buscarPorcodigo + ", '"
                + categoria + "', '" + busqueda + "', " + fecha + ", " + precio + ", " + incluirInactivos + ")" + porcionSql + ";";
        return genericSQLDao.obtenerPorSql(sql, InvListaProductosGeneralTO.class);
    }

    @Override
    public List<InvListaProductosGeneralTO> importarProductosSinStock(String empresa, String categoria) throws Exception {
        String sql = "SELECT p.pro_codigo_principal lpsp_codigo_principal, p.pro_nombre lpsp_nombre, pc.cat_detalle lpsp_categoria, "
                + "p.pro_empaque lpsp_empaque, ps.stk_saldo_final lpsp_saldo, ps.stk_valor_ultima_compra_final lpsp_ultimo_costo, "
                + "ps.stk_valor_promedio_final lpsp_costo_promedio, pm.med_detalle lpsp_medida, (p.pro_minimo - ps.stk_saldo_final) lpsp_cantidad, 0 lpsp_precio, "
                + "0 lpsp_descuento, 0 lpsp_margen_utilidad, p.pro_iva lpsp_iva , p.pro_inactivo lpsp_inactivo, "
                + "p.tip_codigo lpsp_tipo, p.pro_factor_caja_saco_bulto lpsp_factor_conversion, false lpsp_precio_fijo, "
                + "p.pro_exigir_serie lpsp_exigir_serie,p.pro_costo_referencial1 lpsp_costo_referencial, 0 lpsp_base_imponible,false lpsp_imagenes,p.pro_credito_tributario lpsp_credito_tributario,p.pro_ecommerce lpsp_ecommerce,tipo.tip_tipo lpsp_tipo_tipo_producto, "
                + "0 as lpsp_porcentaje_ice, 0 as lpsp_tarifa_fija_ice, null as lpsp_ice_codigo, "
                + "ROW_NUMBER () OVER (ORDER BY p.pro_codigo_principal) id "
                + "from inventario.inv_producto p INNER JOIN inventario.inv_producto_saldos ps "
                + "ON ps.pro_empresa = p.pro_empresa and ps.pro_codigo_principal = p.pro_codigo_principal "
                + "INNER JOIN inventario.inv_producto_medida pm ON pm.med_empresa = p.med_empresa and pm.med_codigo = p.med_codigo "
                + "INNER JOIN inventario.inv_producto_tipo tipo ON p.tip_empresa = tipo.tip_empresa AND p.tip_codigo = tipo.tip_codigo "
                + "left JOIN inventario.inv_producto_categoria pc ON pc.cat_empresa = p.med_empresa and pc.cat_codigo = p.cat_codigo "
                + "WHERE p.pro_empresa LIKE '" + empresa + "' AND ps.stk_saldo_final < p.pro_minimo ";
        if (categoria != null && !categoria.equals("")) {
            sql = sql + " AND p.cat_codigo LIKE '" + categoria + "';";
        }
        return genericSQLDao.obtenerPorSql(sql, InvListaProductosGeneralTO.class);
    }

    @Transactional
    @Override
    public List<InvFunListaProductosSaldosGeneralTO> getInvFunListaProductosSaldosGeneralTO(String empresa,
            String producto, String fecha, boolean estado) throws Exception {
        producto = producto == null ? producto : "'" + producto + "'";
        fecha = fecha == null ? fecha : "'" + fecha + "'";
        String sql = "SELECT * FROM inventario.fun_lista_productos_saldos_general('" + empresa + "', " + producto + ", "
                + fecha + ", " + estado + " )";

        return genericSQLDao.obtenerPorSql(sql, InvFunListaProductosSaldosGeneralTO.class);
    }

    @Override
    public InvProductoSaldos getInvProductoSaldo(String pro_empresa, String bodega, String producto) throws Exception {
        pro_empresa = pro_empresa == null ? null : "'" + pro_empresa + "'";
        bodega = bodega == null ? null : "'" + bodega + "'";
        producto = producto == null ? null : "'" + producto + "'";
        String sql = "SELECT * FROM inventario.inv_producto_saldos WHERE  pro_empresa = " + pro_empresa
                + " AND pro_codigo_principal = " + producto + " AND bod_empresa = " + pro_empresa + " AND bod_codigo ="
                + bodega;
        return genericSQLDao.obtenerObjetoPorSql(sql, InvProductoSaldos.class);
    }

    @Override
    public List<InvKardexTO> getListaInvKardexTO(String empresa, String bodega, String producto, String desde,
            String hasta, String promedio) throws Exception {
        bodega = bodega != null && bodega.compareTo("") != 0 ? "'" + bodega + "'" : null;
        producto = producto != null && producto.compareTo("") != 0 ? "'" + producto + "'" : null;
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT * FROM inventario.fun_kardex('" + empresa + "', " + bodega + ", " + producto + ", "
                + desde + ", " + hasta + ", CASE WHEN '" + promedio + "' = 'SERVICIOS' THEN TRUE ELSE FALSE END);";

        return genericSQLDao.obtenerPorSql(sql, InvKardexTO.class);
    }

    @Override
    public List<InvKardexTO> getListaInvKardexTO(String empresa, String bodega, String producto, String desde, String hasta, String promedio, boolean incluirTodos) throws Exception {
        bodega = bodega != null && bodega.compareTo("") != 0 ? "'" + bodega + "'" : null;
        producto = producto != null && producto.compareTo("") != 0 ? "'" + producto + "'" : null;
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String porcionSql = incluirTodos ? "" : " where k_status is null ";
        String sql = "SELECT * FROM inventario.fun_kardex('" + empresa + "', " + bodega + ", " + producto + ", "
                + desde + ", " + hasta + ", CASE WHEN '" + promedio + "' = 'SERVICIOS' THEN TRUE ELSE FALSE END)" + porcionSql;

        return genericSQLDao.obtenerPorSql(sql, InvKardexTO.class);
    }

}
