package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface XivSueldoPeriodoService {

    public List<RhXivSueldoPeriodoTO> getRhComboXivSueldoPeriodoTO() throws Exception;

    public String accionRhXivSueldoPeriodo(RhXivSueldoPeriodoTO rhXivSueldoPeriodoTO, String codigoEmpresa, String codigoUsuario, char accion, SisInfoTO sisInfoTO) throws Exception;

    public RhXivSueldoPeriodo getRhXivSueldoPeriodoPorAtributo(String atributo, String valorAtributo) throws Exception;

}
