
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;

public interface EmpresaParametrosDao extends GenericDao<SisEmpresaParametros, String> {

	public Integer getColumnasEstadosFinancieros(String empCodigo) throws Exception;

}
