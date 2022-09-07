package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import org.springframework.transaction.annotation.Transactional;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhProvisiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ProvisionesService {

    public RhProvisiones listarRhProvisiones(String empresa) throws Exception;

    @Transactional
    public RhProvisiones insertarRhProvisiones(RhProvisiones rhProvisiones, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public RhProvisiones modificarRhProvisiones(RhProvisiones rhProvisiones, SisInfoTO sisInfoTO) throws Exception;

}
