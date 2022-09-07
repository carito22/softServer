/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalleSeries;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Repository
public class ConsumosDetalleSeriesDaoImpl extends GenericDaoImpl<InvConsumosDetalleSeries, Integer> implements ConsumosDetalleSeriesDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ConsumosDetalleDao consumoDetalleDao;

    @Transactional
    @Override
    public List<InvConsumosDetalleSeries> listarSeriesPorSecuencialDetalle(Integer secuencial) throws Exception {
        String sql = "SELECT * FROM inventario.inv_consumos_detalle_series where det_secuencial_consumo = '" + secuencial + "'";
        List<InvConsumosDetalleSeries> series = genericSQLDao.obtenerPorSql(sql, InvConsumosDetalleSeries.class);
        if (series != null && !series.isEmpty()) {
            for (InvConsumosDetalleSeries serie : series) {
                evict(serie);
                consumoDetalleDao.evict(serie.getDetSecuencialConsumo());
            }
        }
        return series;
    }

}
