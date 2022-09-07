package ec.com.todocompu.ShrimpSoftServer.caja.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajCajaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import java.util.Map;

@Transactional
public interface CajaService {

    public Map<String, Object> getDatosPerfilFacturacion(String empresa, CajCajaPK cajCajaPK, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public List<SisListaUsuarioTO> getListaUsuariosDisponibles(String empresa, SisInfoTO sisInfoTO) throws Exception;

    MensajeTO accionCajCajaTO(CajCajaTO cajCajaTO, String accion, SisInfoTO sisInfoTO) throws Exception;

    List<CajCajaTO> getListadoCajCajaTO(String empresa) throws Exception;

    List<CajCajaComboTO> getCajCajaComboTO(String empresa) throws Exception;

    CajCajaTO getCajCajaTO(String empresa, String usuarioCodigo) throws Exception;

    String guardarArchivoFirmaElectronica(byte[] imagen, String nombre) throws Exception;

}
