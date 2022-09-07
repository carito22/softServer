package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;


import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConVerificacionErrores;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface VerificacionService {

    public boolean insertar(ConVerificacionErrores conTipoTO, SisInfoTO sisInfoTO) throws Exception;

    public ConVerificacionErrores obtener(int secuencial) throws Exception;

}
