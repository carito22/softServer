package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContableEstadoSituacionInicial;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AperturaDeSaldosService {

    public ConContableEstadoSituacionInicial obtenerConAperturaSaldoInicial(ConContableEstadoSituacionInicial aperturaSaldoInicial) throws Exception;

    public MensajeTO insertarConContableAperturaSaldoInicial(ConContableEstadoSituacionInicial conApertura, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO modificarConContableAperturaSaldoInicial(ConContableEstadoSituacionInicial conApertura, SisInfoTO sisInfoTO) throws Exception;

}
