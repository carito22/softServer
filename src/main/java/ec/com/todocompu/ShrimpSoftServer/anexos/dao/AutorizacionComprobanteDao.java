package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxAnulados;

public interface AutorizacionComprobanteDao extends GenericDao<AnxAnulados, Integer> {

	public AnxAnuladosTO getAnxAnuladosTO(Integer secuencia) throws Exception;

}
