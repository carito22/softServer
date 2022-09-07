package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleCompras;
import java.util.List;

public interface PagosDetalleComprasDao extends GenericDao<CarPagosDetalleCompras, Integer> {

    public List<CarPagosDetalleCompras> listarDetallesComprasPorPago(String empresa, String periodo, String tipo, String numero) throws Exception;

}
