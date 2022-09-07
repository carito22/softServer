package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhContableTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhSalarioDignoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface SalarioDignoService {

    public RhContableTO insertarRhSalarioDignoContable(RhSalarioDignoTO rhSalarioDignoTO, SisInfoTO sisInfoTO)
            throws Exception;

    public Map<String, Object> obtenerDatosSalarioDigno(Map<String, Object> map) throws Exception;

}
