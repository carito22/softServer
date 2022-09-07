package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdAmbienteProduccion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AmbienteProduccionService {

    public String insertarAmbienteProduccion(PrdAmbienteProduccion prdAmbienteProduccion, SisInfoTO sisInfoTO) throws Exception;

    public List<PrdAmbienteProduccion> getListarPrdAmbienteProduccion(String empresa) throws Exception;
    
    public String modificarAmbienteProduccion (PrdAmbienteProduccion prdAmbienteProduccion, SisInfoTO sisInfoTO) throws Exception;
    
    public String eliminarAmbienteProduccion(PrdAmbienteProduccion prdAmbienteProduccion, SisInfoTO sisInfoTO) throws Exception;
}
