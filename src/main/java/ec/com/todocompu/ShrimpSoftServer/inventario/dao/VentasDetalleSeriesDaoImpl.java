package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalleSeries;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VentasDetalleSeriesDaoImpl extends GenericDaoImpl<InvVentasDetalleSeries, Integer> implements VentasDetalleSeriesDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Transactional
    @Override
    public List<InvVentasDetalleSeries> listarSeriesPorSecuencialDetalle(Integer secuencial) throws Exception {
        String sql = "SELECT * FROM inventario.inv_ventas_detalle_series where det_secuencial_venta = '" + secuencial + "'";
        List<InvVentasDetalleSeries> series = genericSQLDao.obtenerPorSql(sql, InvVentasDetalleSeries.class);
        if (series != null && !series.isEmpty()) {
            for (InvVentasDetalleSeries serie : series) {
                evict(serie);
            }
        }
        return series;
    }

}
