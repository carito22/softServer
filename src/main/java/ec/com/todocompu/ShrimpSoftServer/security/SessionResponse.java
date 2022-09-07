package ec.com.todocompu.ShrimpSoftServer.security;

import ec.com.todocompu.ShrimpSoftUtils.web.SessionItemTO;
import ec.com.todocompu.ShrimpSoftServer.util.RespuestaWeb;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.DatosInicialesTO;
import ec.com.todocompu.ShrimpSoftUtils.web.NotificacionesTO;
import java.util.List;

public class SessionResponse extends RespuestaWeb {

    private SessionItemTO item;
    private DatosInicialesTO iniciales;
    private SisInfoTO sisInfoTO;
    private List<NotificacionesTO> notificaciones;

    public SessionItemTO getItem() {
        return item;
    }

    public void setItem(SessionItemTO item) {
        this.item = item;
    }

    public DatosInicialesTO getIniciales() {
        return iniciales;
    }

    public void setIniciales(DatosInicialesTO iniciales) {
        this.iniciales = iniciales;
    }

    public SisInfoTO getSisInfoTO() {
        return sisInfoTO;
    }

    public void setSisInfoTO(SisInfoTO sisInfoTO) {
        this.sisInfoTO = sisInfoTO;
    }

    public List<NotificacionesTO> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionesTO> notificaciones) {
        this.notificaciones = notificaciones;
    }
    
}
