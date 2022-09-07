/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalleSeries;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public class TransferenciasDetalleSeriesDaoImpl extends GenericDaoImpl<InvTransferenciasDetalleSeries, Integer> implements TransferenciasDetalleSeriesDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private TransferenciasDetalleDao transferenciasDetalleDao;

    @Override
    public List<InvTransferenciasDetalleSeries> listarSeriesPorSecuencialDetalle(Integer secuencial) throws Exception {
        String sql = "SELECT * FROM inventario.inv_consumos_detalle_series where det_secuencial_consumo = '" + secuencial + "'";
        List<InvTransferenciasDetalleSeries> series = genericSQLDao.obtenerPorSql(sql, InvTransferenciasDetalleSeries.class);
        if (series != null && !series.isEmpty()) {
            for (InvTransferenciasDetalleSeries serie : series) {
                evict(serie);
                transferenciasDetalleDao.evict(serie.getDetSecuencialTransferencia());
            }
        }
        return series;
    }

}
