package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConEstructura;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface EstructuraService {

    public List<ConEstructuraTO> getListaConEstructuraTO(String empresa) throws Exception;

    public MensajeTO insertarConEstructura(ConEstructura conEstructura, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO modificarConEstructura(ConEstructura conEstructura, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO eliminarConEstructura(ConEstructura conEstructura, SisInfoTO sisInfoTO) throws Exception;
}
