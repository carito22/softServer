package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

public interface FirmaElectronicaService {

    public List<SisFirmaElectronica> listarSisFirmaElectronica(String empresa) throws Exception;

    public List<SisFirmaElectronica> listarSisFirmaElectronicaNoVerificadas(String empresa) throws Exception;

    @Transactional
    public SisFirmaElectronica insertarSisFirmaElectronica(SisFirmaElectronica sisFirmaElectronica, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public SisFirmaElectronica modificarSisFirmaElectronica(SisFirmaElectronica sisFirmaElectronica, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarSisFirmaElectronica(Integer sisFirmaElectronica, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> getDatosFirmaElectronica(Integer secuencial, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean migracionCajaAFirmaElectronica(SisInfoTO sisInfoTO) throws Exception;
    
    public Date verificarDatosCertificado(String passSignature, String ruc, Date fechaVencimiento, byte[] archivo) throws Exception;

    public Map<String, Object> validacionCertificado(byte[] base64, String clave, boolean contraseniaEncriptada) throws Exception;

}
