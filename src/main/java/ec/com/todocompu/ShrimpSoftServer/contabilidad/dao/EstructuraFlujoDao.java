package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConEstructuraFlujo;

public interface EstructuraFlujoDao extends GenericDao<ConEstructuraFlujo, String> {

	public List<ConEstructuraFlujoTO> getListaConEstructuraFlujoTO(String empresa) throws Exception;

}
