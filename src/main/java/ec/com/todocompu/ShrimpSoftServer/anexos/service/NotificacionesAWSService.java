package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface NotificacionesAWSService {

    public MensajeTO insertarNotificacion(String json) throws Exception;

}
