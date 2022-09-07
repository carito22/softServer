package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisScannerConfiguracion;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ScannerConfiguracionService {

    @Transactional
    public boolean comprobarSisScannerConfiguracion(String empresa) throws Exception;

    public List<SisScannerConfiguracion> listarSisScannerConfiguracion(String empresa) throws Exception;

    @Transactional
    public SisScannerConfiguracion insertarSisScannerConfiguracion(SisScannerConfiguracion sisScannerConfiguracion, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public SisScannerConfiguracion modificarSisScannerConfiguracion(SisScannerConfiguracion sisScannerConfiguracion, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public SisScannerConfiguracion modificarEstadoSisScannerConfiguracion(SisScannerConfiguracion sisScannerConfiguracion, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarSisScannerConfiguracion(String sisScannerConfiguracion, SisInfoTO sisInfoTO) throws Exception;

}
