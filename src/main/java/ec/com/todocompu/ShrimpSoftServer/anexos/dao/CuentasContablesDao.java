package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCuentascontables;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface CuentasContablesDao extends GenericDao<AnxCuentascontables, Integer> {

	public Boolean actualizarAnxCuentascontables(AnxCuentascontables anxCuentascontables, SisSuceso sisSuceso)
			throws Exception;

	public AnxCuentasContablesTO getCuentasContablesTO(String empresa) throws Exception;

	public boolean comprobarAnxCuentascontables(String empresa) throws Exception;

	public AnxCuentasContablesTO getAnxCuentasContablesTO(String empresa, String nombreCuenta) throws Exception;

}
