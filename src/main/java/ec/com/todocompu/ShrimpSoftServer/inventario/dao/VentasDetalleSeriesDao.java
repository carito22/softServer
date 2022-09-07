package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalleSeries;
import java.util.List;

public interface VentasDetalleSeriesDao extends GenericDao<InvVentasDetalleSeries, Integer> {

    public List<InvVentasDetalleSeries> listarSeriesPorSecuencialDetalle(Integer secuencial) throws Exception;
    
}
