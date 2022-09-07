package ec.com.todocompu.ShrimpSoftServer.excepciones;

import ec.com.todocompu.ShrimpSoftServer.util.RespuestaWeb;
import ec.com.todocompu.ShrimpSoftServer.util.RespuestaWeb.EstadoOperacionEnum;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/*
 @ControllerAdvice tells your spring application that this class will do the exception handling for your application.
 @RestController will make it a controller and let this class render the response.
 Use @ExceptionHandler annotation to define the class of Exception it will catch. (A Base class will catch all the Inherited and extended classes)
 */
@ControllerAdvice
@RestController
public class ManejodeExcepciones {

    @ExceptionHandler(value = {GeneralException.class})
    public RespuestaWeb respuestaGeneral(GeneralException e) {
        RespuestaWeb resp = new RespuestaWeb();
        resp.setEstadoOperacion(EstadoOperacionEnum.ERROR.getValor());
        resp.setOperacionMensaje(e.getMessage());
//        loggerGeneral.error("ERROR EN LA LINEA N° " + e.getStackTrace()[0].getLineNumber()
//                + " DEL ARCHIVO: " + e.getStackTrace()[0].getFileName()
//                + " EN EL METODO: " + e.getStackTrace()[0].getMethodName()
//                + " DE LA CLASE: " + e.getStackTrace()[0].getClassName()
//                + " MENSAJE: " + e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = {Exception.class})
    public RespuestaWeb respuestaException(Exception e) {
        RespuestaWeb resp = new RespuestaWeb();
        resp.setEstadoOperacion(EstadoOperacionEnum.ERROR.getValor());
        e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
        resp.setOperacionMensaje(e.getMessage());
//        loggerGeneral.error("ERROR EN LA LINEA N° " + e.getStackTrace()[0].getLineNumber()
//                + " DEL ARCHIVO: " + e.getStackTrace()[0].getFileName()
//                + " EN EL METODO: " + e.getStackTrace()[0].getMethodName()
//                + " DE LA CLASE: " + e.getStackTrace()[0].getClassName()
//                + " MENSAJE: " + e.getMessage());
        return resp;
    }
}
