/**
 * This is the common structure for all responses if the response contains a
 * list(array) then it will have 'items' field if the response contains a single
 * item then it will have 'item' field
 */
package ec.com.todocompu.ShrimpSoftServer.util;

import com.fasterxml.jackson.annotation.JsonInclude;

public class RespuestaWeb {

    public enum EstadoOperacionEnum {
        EXITO("EXITO"),
        ERROR("ERROR"),
        ADVERTENCIA("ADVERTENCIA"),
        SIN_ACCESO("SIN_ACCESO");

        private final String valor;

        public String getValor() {
            return valor;
        }
        
        private EstadoOperacionEnum(String valor) {
            this.valor = valor;
        }
    };
    
    private String estadoOperacion;
    private String operacionMensaje;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object extraInfo;

    public String getEstadoOperacion() {
        return estadoOperacion;
    }

    public void setEstadoOperacion(String estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }

    public String getOperacionMensaje() {
        return operacionMensaje;
    }

    public void setOperacionMensaje(String operacionMensaje) {
        this.operacionMensaje = operacionMensaje;
    }

    public Object getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Object extraInfo) {
        this.extraInfo = extraInfo;
    }
    
    
}
