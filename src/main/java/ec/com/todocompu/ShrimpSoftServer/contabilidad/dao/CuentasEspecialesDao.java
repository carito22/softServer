package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasEspeciales;

public interface CuentasEspecialesDao extends GenericDao<ConCuentasEspeciales, String> {

	public Boolean buscarConDetallesActivosBiologicos(String empresa) throws Exception;

}
