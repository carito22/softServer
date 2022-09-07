package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EstadosComparativosConOtrasEmpresasService {

    public Map<String, Object>  obtenerCuadroComparativoEntreEmpresas(String empresa, String fechaHasta) throws Exception;

    public Map<String, Object> obtenerResultadoComparativoEntreEmpresas(String temporal, String desde, String hasta) throws Exception;

}
