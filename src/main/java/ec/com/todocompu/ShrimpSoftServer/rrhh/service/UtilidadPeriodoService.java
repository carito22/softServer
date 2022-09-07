package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface UtilidadPeriodoService {

    public List<RhUtilidadesPeriodoTO> getRhComboUtilidadesPeriodoTO(String empresa) throws Exception;

    public String accionRhUtilidadesPeriodo(RhUtilidadesPeriodoTO rhUtilidadesPeriodoTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoUtilidadesPeriodo(RhUtilidadesPeriodoTO rhUtilidadesPeriodoTO, boolean estado, SisInfoTO sisInfoTO) throws Exception;

}
