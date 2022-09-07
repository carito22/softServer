package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107PeriodoFiscalTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormulario107Periodo;

public interface Formulario107PeriodoDao extends GenericDao<RhFormulario107Periodo, String> {

	public List<RhFormulario107PeriodoFiscalTO> getRhFormulario107PeriodoFiscalComboTO() throws Exception;

}
