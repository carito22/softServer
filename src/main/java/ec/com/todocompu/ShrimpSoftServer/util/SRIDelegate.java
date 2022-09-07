package ec.com.todocompu.ShrimpSoftServer.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxComprobanteElectronicoUtilTO;
import java.util.Properties;

public class SRIDelegate {

    private static SRIDelegate grupoDelegate;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String conexionMSSRI;

    Properties prop = new Properties();

    private SRIDelegate() throws Exception {
        prop.load(this.getClass().getResourceAsStream("/database.properties"));
        conexionMSSRI = prop.getProperty("sri.url");
    }

    public static SRIDelegate getInstance() {
        if (grupoDelegate == null) {
            try {
                grupoDelegate = new SRIDelegate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return grupoDelegate;
    }

    public AnxComprobanteElectronicoUtilTO envioComprobanteWebServiceSRI(byte[] eXmlFirmado, String claveAcceso, String tipoAmbiente) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("eXmlFirmado", eXmlFirmado);
            map.put("claveAcceso", claveAcceso);
            map.put("tipoAmbiente", tipoAmbiente);
            return restTemplate.postForObject(conexionMSSRI + "/MSFacturacion/sriMS/envioComprobanteWebServiceSRI",
                    UtilsJSON.objetoToJson(map), AnxComprobanteElectronicoUtilTO.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            //UtilsExcepciones.enviarError(e, getClass().getName(),sisInfoTO == null ? UtilsAplicacion.getSisInfoTO() : sisInfoTO);
        }
        return null;
    }
}
