package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleAnticipos;
import java.util.List;

public interface PagosDetalleAnticiposDao extends GenericDao<CarPagosDetalleAnticipos, Integer> {

    public List<CarPagosDetalleAnticipos> listarDetallesAnticipoPorPago(String empresa, String periodo, String tipo, String numero) throws Exception;

}
