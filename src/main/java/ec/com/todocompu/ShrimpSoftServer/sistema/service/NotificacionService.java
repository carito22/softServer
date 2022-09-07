package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.web.NotificacionesTO;
import java.util.List;

public interface NotificacionService {

	@Transactional
	public SisNotificacion obtener();
        
        @Transactional
	public List<NotificacionesTO> notificar(String codigoUsuario) throws Exception;
}