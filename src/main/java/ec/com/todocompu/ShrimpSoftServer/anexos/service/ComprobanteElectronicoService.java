package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteElectronico;
import java.util.Map;

@Transactional
public interface ComprobanteElectronicoService {

    public List<ComprobanteElectronico> obtenerDocumentosPorCedulaRucMesAnio(String cedulaRuc, String mes, String anio);

    public Map<String, Object> obtenerDatosParaComprobantesElectronicosRecibidos(Map<String, Object> map) throws Exception;
}
