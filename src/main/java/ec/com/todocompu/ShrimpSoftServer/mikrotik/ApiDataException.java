package ec.com.todocompu.ShrimpSoftServer.mikrotik;

import ec.com.todocompu.ShrimpSoftServer.mikrotik.MikrotikApiException;

/**
 * Thrown if there is a problem unpacking data from the Api. 
 * @author GideonLeGrange
 */
public class ApiDataException extends MikrotikApiException {

    ApiDataException(String msg) {
        super(msg);
    }

    ApiDataException(String msg, Throwable err) {
        super(msg, err);
    }

    
    
}
