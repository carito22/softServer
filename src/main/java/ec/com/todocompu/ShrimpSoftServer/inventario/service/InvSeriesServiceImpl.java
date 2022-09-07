package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunMovimientosSerieTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvSerieTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalleSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvSeriesServiceImpl implements InvSeriesService {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvSerieTO> listarSeriesVigentes(String empresa, String bodega, String producto) throws Exception {
        String sql = "SELECT * FROM inventario.fun_series_vigentes('" + empresa + "','" + bodega + "','" + producto + "', null)";
        List<InvSerieTO> i = genericSQLDao.obtenerPorSql(sql, InvSerieTO.class);
        return i;
    }

    @Override
    public boolean esSerieValidaAlGuardarVenta(String tipoDocumento, String serie, String producto, String bodega, String empresa) throws Exception {
        boolean esSerieValida;
        serie = serie != null ? serie.trim() : "";
        String sql = "SELECT id, serie from  inventario.fun_series_vigentes('" + empresa + "','" + bodega + "','" + producto + "', null)"
                + " where serie = '" + serie + "'";
        List<InvSerieTO> series = genericSQLDao.obtenerPorSql(sql, InvSerieTO.class);
        esSerieValida = series != null && series.size() > 0;
        if (tipoDocumento != null && (tipoDocumento.equals("04") || tipoDocumento.equals("05"))) {
            return !esSerieValida;
        }
        return esSerieValida;
    }

    @Override
    public boolean validarSerieRepetidaAlGuardarConsumo(Integer secuencial, String serie, String producto) throws Exception {
        serie = serie != null ? serie.trim() : "";
        secuencial = secuencial == null ? 0 : secuencial;
        //validar que no esté en ventas
        String sql = "SELECT vds.* "
                + "FROM inventario.inv_ventas_detalle_series vds "
                + "INNER JOIN inventario.inv_ventas_detalle vd "
                + "ON vd.det_secuencial = vds.det_secuencial_venta "
                + "INNER JOIN inventario.inv_ventas v "
                + "ON vd.vta_empresa = v.vta_empresa and vd.vta_motivo = v.vta_motivo and vd.vta_periodo = v.vta_periodo and vd.vta_numero = v.vta_numero "
                + "WHERE vds.det_numero_serie = '" + serie + "' and vd.pro_codigo_principal = '" + producto + "' "
                + "AND not v.vta_anulado and not vta_pendiente ";
        List<InvVentasDetalleSeries> ventas = genericSQLDao.obtenerPorSql(sql, InvVentasDetalleSeries.class);
        if (ventas != null && ventas.size() > 0) {
            return true;//si existen ventas con esa serie
        }
        //validar que no esté en consumos
        String sqlConsumos = "SELECT cds.* "
                + "FROM inventario.inv_consumos_detalle_series cds "
                + "INNER JOIN inventario.inv_consumos_detalle cd "
                + "ON cd.det_secuencial = cds.det_secuencial_consumo "
                + "INNER JOIN inventario.inv_consumos c "
                + "ON cd.cons_empresa = c.cons_empresa and cd.cons_motivo = c.cons_motivo and cd.cons_periodo = c.cons_periodo and cd.cons_numero = c.cons_numero "
                + "WHERE cds.det_numero_serie = '" + serie + "' and cds.det_secuencial != " + secuencial + " and cd.pro_codigo_principal = '" + producto + "' "
                + "AND not c.cons_anulado and not cons_pendiente ";
        List<InvConsumosDetalleSeries> consumos = genericSQLDao.obtenerPorSql(sqlConsumos, InvConsumosDetalleSeries.class);
        return consumos != null && consumos.size() > 0;
    }

    @Override
    public boolean serieCompraOcupada(String empresa, String numero_serie, String codigo_producto) throws Exception {
        //ventas
        int cantidadOcupadaVentas = 0;
        boolean estaOcupado = false;
        String sqlVentas = "SELECT COUNT(*) "
                + "FROM inventario.inv_ventas "
                + "INNER JOIN inventario.inv_ventas_detalle "
                + "ON inv_ventas_detalle.vta_empresa = inv_ventas.vta_empresa "
                + "AND inv_ventas_detalle.vta_periodo = inv_ventas.vta_periodo "
                + "AND inv_ventas_detalle.vta_motivo = inv_ventas.vta_motivo "
                + "AND inv_ventas_detalle.vta_numero = inv_ventas.vta_numero "
                + "INNER JOIN inventario.inv_ventas_detalle_series "
                + "ON inv_ventas_detalle.det_secuencial = inv_ventas_detalle_series.det_secuencial_venta "
                + "WHERE NOT (vta_pendiente AND vta_anulado) AND det_numero_serie ='" + numero_serie + "' AND inv_ventas.vta_empresa='" + empresa + "' AND inv_ventas_detalle.pro_codigo_principal = '" + codigo_producto + "'";

        Object objventas = (Object) genericSQLDao.obtenerObjetoPorSql(sqlVentas);
        if (objventas != null) {
            cantidadOcupadaVentas = new Integer(String.valueOf(UtilsConversiones.convertir(objventas)));
        }

        //consumos
        int cantidadOcupadaConsumos = 0;
        String sqlConsumos = "SELECT COUNT(*) "
                + "FROM inventario.inv_consumos "
                + "INNER JOIN inventario.inv_consumos_detalle "
                + "ON inv_consumos_detalle.cons_empresa = inv_consumos.cons_empresa "
                + "AND inv_consumos_detalle.cons_periodo = inv_consumos.cons_periodo "
                + "AND inv_consumos_detalle.cons_motivo = inv_consumos.cons_motivo "
                + "AND inv_consumos_detalle.cons_numero = inv_consumos.cons_numero "
                + "INNER JOIN inventario.inv_consumos_detalle_series "
                + "ON inv_consumos_detalle.det_secuencial = inv_consumos_detalle_series.det_secuencial_consumo "
                + "WHERE NOT (cons_pendiente AND cons_anulado) AND det_numero_serie ='" + numero_serie + "' AND inv_consumos.cons_empresa='" + empresa + "' AND inv_consumos_detalle.pro_codigo_principal = '" + codigo_producto + "'";

        Object objconsumos = (Object) genericSQLDao.obtenerObjetoPorSql(sqlConsumos);
        if (objconsumos != null) {
            cantidadOcupadaConsumos = new Integer(String.valueOf(UtilsConversiones.convertir(objconsumos)));
        }

        //transferencias
        int cantidadOcupadaTransferencias = 0;
        String sqlTransferencias = "SELECT COUNT(*) "
                + "FROM inventario.inv_transferencias "
                + "INNER JOIN inventario.inv_transferencias_detalle "
                + "ON inv_transferencias_detalle.trans_empresa = inv_transferencias.trans_empresa "
                + "AND inv_transferencias_detalle.trans_periodo = inv_transferencias.trans_periodo "
                + "AND inv_transferencias_detalle.trans_motivo = inv_transferencias.trans_motivo "
                + "AND inv_transferencias_detalle.trans_numero = inv_transferencias.trans_numero "
                + "INNER JOIN inventario.inv_transferencias_detalle_series "
                + "ON inv_transferencias_detalle.det_secuencial = inv_transferencias_detalle_series.det_secuencial_transferencia "
                + "WHERE NOT (det_pendiente) AND det_numero_serie ='" + numero_serie + "' AND inv_transferencias.trans_empresa='" + empresa + "' AND inv_transferencias_detalle.pro_codigo_principal = '" + codigo_producto + "'";

        Object objTransferencia = (Object) genericSQLDao.obtenerObjetoPorSql(sqlTransferencias);
        if (objTransferencia != null) {
            cantidadOcupadaTransferencias = new Integer(String.valueOf(UtilsConversiones.convertir(objTransferencia)));
        }

        if (cantidadOcupadaVentas > 0 || cantidadOcupadaConsumos > 0 || cantidadOcupadaTransferencias > 0) {
            estaOcupado = true;
        }

        return estaOcupado;
    }

    @Override
    public boolean validarSiExisteSerieCompra(String empresa, String numero_serie, String codigo_producto, Integer secuencial) throws Exception {
        int cantidadOcupadaCompras = 0;
        secuencial = secuencial == null ? 0 : secuencial;
        boolean serieExiste = false;
        String sql = "SELECT count(*) "
                + "FROM inventario.inv_compras_detalle_series "
                + "INNER JOIN inventario.inv_compras_detalle "
                + "ON inv_compras_detalle.det_secuencial = inv_compras_detalle_series.det_secuencial_compra "
                + "INNER JOIN inventario.inv_compras "
                + "ON inv_compras_detalle.comp_empresa = inv_compras.comp_empresa "
                + "AND inv_compras_detalle.comp_periodo = inv_compras.comp_periodo "
                + "AND inv_compras_detalle.comp_motivo = inv_compras.comp_motivo "
                + "AND inv_compras_detalle.comp_numero = inv_compras.comp_numero "
                + "WHERE NOT (comp_pendiente AND comp_anulado) AND inv_compras.comp_empresa='" + empresa + "' "
                + "AND inv_compras_detalle.pro_codigo_principal = '" + codigo_producto + "' AND det_numero_serie ='" + numero_serie + "' AND inv_compras_detalle_series.det_secuencial != '" + secuencial + "'";

        Object objcompras = (Object) genericSQLDao.obtenerObjetoPorSql(sql);
        if (objcompras != null) {
            cantidadOcupadaCompras = new Integer(String.valueOf(UtilsConversiones.convertir(objcompras)));
        }
        if (cantidadOcupadaCompras > 0) {
            serieExiste = true;
        }

        return serieExiste;
    }

    @Override
    public List<InvFunMovimientosSerieTO> listarMovimientosSeries(String empresa, String serie, String producto) throws Exception {
        serie = serie != null ? serie.trim() : "";
        producto = producto != null ? producto.trim() : "";
        String sql = "select * from inventario.fun_movimientos_series('" + empresa + "', '" + serie + "', '" + producto + "')";
        return genericSQLDao.obtenerPorSql(sql, InvFunMovimientosSerieTO.class);
    }

}
