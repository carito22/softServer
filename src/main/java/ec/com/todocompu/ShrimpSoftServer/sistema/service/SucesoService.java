package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaSusTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisSucesoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoDetalle;
import java.util.Map;

public interface SucesoService {

    @Transactional
    public List<SisSucesoTO> getListaSisSucesoTO(String desde, String hasta, String usuario, String suceso,
            String cadenaGeneral, String empresa) throws Exception;

    @Transactional
    public List<SisListaSusTablaTO> getListaSisSusTablaTOs(String empresa) throws Exception;

    @Transactional
    public void sisRegistrarEventosUsuario(String suceso, String mensaje, String tabla, SisInfoTO sisInfoTO)
            throws Exception;

    public List<SisSucesoDetalle> getListaSisSucesoDetalle(Integer secuencial) throws Exception;

    public Map<String, Object> obtenerDatosSucesoDetalle(Integer secuencial, String empresa) throws Exception;

}
