package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleForma;
import java.util.List;

public interface CobrosDetalleFormaDao extends GenericDao<CarCobrosDetalleForma, Integer> {

    public List<CarCobrosDetalleForma> listarDetallesFormaPorCobro(String empresa, String periodo, String tipo, String numero) throws Exception;

}
