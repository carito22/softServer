package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface XiiiSueldoPeriodoDao extends GenericDao<RhXiiiSueldoPeriodo, Integer> {

	public RhXiiiSueldoPeriodo buscarRhXiiiSueldoPeriodo(Integer secucenciaPeriodo) throws Exception;

	public Boolean accionRhXiiiSueldoPeriodo(RhXiiiSueldoPeriodo xiiiSueldoPeriodo, SisSuceso sisSuceso, char accion)
			throws Exception;

	public List<RhXiiiSueldoPeriodoTO> getRhComboXiiiSueldoPeriodoTO() throws Exception;

	public RhXiiiSueldoPeriodoTO buscarRhXiiiSueldoPeriodoTO(String descripcion) throws Exception;

	public boolean comprobarRhXiiiSueldoPeriodoTO(String descripcion) throws Exception;

}
