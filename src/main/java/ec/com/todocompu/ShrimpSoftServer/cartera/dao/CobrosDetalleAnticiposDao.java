package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleAnticipos;
import java.util.List;

public interface CobrosDetalleAnticiposDao extends GenericDao<CarCobrosDetalleAnticipos, Integer> {

	public CarListaCobrosClienteTO getCobrosConsultaClienteAnticipo(String empresa, String periodo, String tipo,
			String numero) throws Exception;

	public Boolean getCarReversarCobroAnticipos(String empresa, String periodo, String numero, String tipo)
			throws Exception;
        
        public List<CarCobrosDetalleAnticipos> listarDetallesAnticipoPorCobro(String empresa, String periodo, String tipo, String numero) throws Exception;

}
