package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface XiiiSueldoPeriodoService {

    public List<RhXiiiSueldoPeriodoTO> getRhComboXiiiSueldoPeriodoTO() throws Exception;

    public String accionRhXiiiSueldoPeriodo(RhXiiiSueldoPeriodoTO rhXiiiSueldoPeriodoTO, String codigoEmpresa, String codigoUsuario, char accion, SisInfoTO sisInfoTO) throws Exception;

    public RhXiiiSueldoPeriodo getRhXiiiSueldoPeriodoPorAtributo(String atributo, String valorAtributo) throws Exception;

}
