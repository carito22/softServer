package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleForma;
import java.util.List;

public interface PagosDetalleFormaDao extends GenericDao<CarPagosDetalleForma, Integer> {

    public List<CarPagosDetalleForma> listarDetallesFormaPorPago(String empresa, String periodo, String tipo, String numero) throws Exception;

}
