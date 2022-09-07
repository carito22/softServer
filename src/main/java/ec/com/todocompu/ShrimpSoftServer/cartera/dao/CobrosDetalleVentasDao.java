package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleVentas;
import java.util.List;

public interface CobrosDetalleVentasDao extends GenericDao<CarCobrosDetalleVentas, Integer> {

    public List<CarCobrosDetalleVentas> listarDetallesVentasPorCobro(String empresa, String periodo, String tipo, String numero) throws Exception;

}
