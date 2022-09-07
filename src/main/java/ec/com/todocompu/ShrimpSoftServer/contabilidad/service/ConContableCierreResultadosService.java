package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContableCierreResultado;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConContableCierreResultadosService {

    public ConContableCierreResultado obtenerConContableCierreResultados(ConContableCierreResultado cierreResultados) throws Exception;

    public MensajeTO insertarConContableCierreResultados(ConContableCierreResultado cierreResultados, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO modificarConContableCierreResultados(ConContableCierreResultado cierreResultados, SisInfoTO sisInfoTO) throws Exception;

}
