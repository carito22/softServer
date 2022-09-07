package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstadoResultadosComparativoOtrasEmpresasTO;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstadoSituacionFinancieraComparativoOtrasEmpresasTO;

public interface EstadosComparativosConOtrasEmpresasDao {

    public List<ConEstadoSituacionFinancieraComparativoOtrasEmpresasTO> obtenerCuadroComparativoEntreEmpresas(String empresa, String fechaHasta) throws Exception;

    public List<ConEstadoResultadosComparativoOtrasEmpresasTO> obtenerResultadoComparativoEntreEmpresas(String temporal, String desde, String hasta) throws Exception;
}
