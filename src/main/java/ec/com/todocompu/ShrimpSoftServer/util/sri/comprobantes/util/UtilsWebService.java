package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos;

public class UtilsWebService {

    private static String rutaKeystore = "";
    private static String passwordKeystore = "";

    private static void certificadoSeguridadSRI() {
        System.clearProperty("javax.net.ssl.keyStore");
        System.clearProperty("javax.net.ssl.keyStorePassword");
        System.clearProperty("javax.net.ssl.trustStore");
        System.clearProperty("javax.net.ssl.trustStorePassword");

        System.setProperty("javax.net.ssl.keyStore", rutaKeystore);
        System.setProperty("javax.net.ssl.keyStorePassword", passwordKeystore);
        System.setProperty("javax.net.ssl.trustStore", rutaKeystore);
        System.setProperty("javax.net.ssl.trustStorePassword", passwordKeystore);
    }

    public static void actualizarCertificadoSeguridadSRI() {
        rutaKeystore = UtilsArchivos.getRutaCertificadosWebServiceSRI() + "owsKeyStore.jks";
        passwordKeystore = "t0d0c0mPU";
        System.out.println("rutaKeystore:" + rutaKeystore);
        System.out.println("passwordKeystore: " + passwordKeystore);
        certificadoSeguridadSRI();
    }

}
