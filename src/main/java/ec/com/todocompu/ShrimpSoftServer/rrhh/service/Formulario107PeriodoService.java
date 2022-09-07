package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107PeriodoFiscalTO;

@Transactional
public interface Formulario107PeriodoService {

	public List<RhFormulario107PeriodoFiscalTO> getRhFormulario107PeriodoFiscalComboTO() throws Exception;

}
