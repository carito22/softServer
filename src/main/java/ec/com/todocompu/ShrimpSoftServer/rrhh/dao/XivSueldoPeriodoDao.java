package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface XivSueldoPeriodoDao extends GenericDao<RhXivSueldoPeriodo, Integer> {

	public RhXivSueldoPeriodo buscarRhXivSueldoPeriodo(Integer secucenciaPeriodo) throws Exception;

	public Boolean accionRhXivSueldoPeriodo(RhXivSueldoPeriodo xivSueldoPeriodo, SisSuceso sisSuceso, char accion)
			throws Exception;

	public List<RhXivSueldoPeriodoTO> getRhComboXivSueldoPeriodoTO() throws Exception;

	public RhXivSueldoPeriodoTO buscarRhXivSueldoPeriodoTO(String descripcion) throws Exception;

	public boolean comprobarRhXivSueldoPeriodoTO(String descripcion) throws Exception;

}
