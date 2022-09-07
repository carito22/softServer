package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface ParametrosService {

    public RhParametros getRhParametros(String fechaHasta) throws Exception;

    public RhParametros obtenerUltimoParametro() throws Exception;

    @Transactional
    public RhParametros insertarRhParametro(RhParametros rhParametro, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public RhParametros modificarRhParametro(RhParametros rhParametro, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarRhParametro(int rhParametro, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudConfiguracion() throws Exception;

    public RhParametros obtenerRhParametrosPorCodigoRelacionTrabajo(String fechaHasta, String codigo) throws Exception;

}
