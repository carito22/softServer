/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleSeries;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Repository
public class ComprassDetalleSeriesDaoImpl extends GenericDaoImpl<InvComprasDetalleSeries, Integer> implements ComprasDetalleSeriesDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ComprasDetalleDao conmprasDetalleDao;

    @Transactional
    @Override
    public List<InvComprasDetalleSeries> listarSeriesPorSecuencialDetalle(Integer secuencial) throws Exception {
        String sql = "SELECT * FROM inventario.inv_compras_detalle_series where det_secuencial_compra = '" + secuencial + "'";
        List<InvComprasDetalleSeries> series = genericSQLDao.obtenerPorSql(sql, InvComprasDetalleSeries.class);
        if (series != null && !series.isEmpty()) {
            for (InvComprasDetalleSeries serie : series) {
                evict(serie);
                conmprasDetalleDao.evict(serie.getDetSecuencialCompra());
            }
        }
        return series;
    }

}
