package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;

public interface NumeracionDao extends GenericDao<ConNumeracion, ConNumeracionPK> {

	public List<ConNumeracionTO> getListaConNumeracionTO(String empresa, String periodo, String tipo) throws Exception;

}
