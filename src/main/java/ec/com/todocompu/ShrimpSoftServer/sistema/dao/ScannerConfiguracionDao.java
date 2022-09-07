package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisScannerConfiguracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

public interface ScannerConfiguracionDao extends GenericDao<SisScannerConfiguracion, String> {

    public boolean comprobarScannerConfiguracion(String empresa) throws Exception;

    public List<SisScannerConfiguracion> listarScannerConfiguracion(String empresa) throws Exception;

    public SisScannerConfiguracion insertarScannerConfiguracion(SisScannerConfiguracion scannerconfiguracion, SisSuceso sisSuceso) throws Exception;

    public SisScannerConfiguracion modificarScannerConfiguracion(SisScannerConfiguracion scannerconfiguracion, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarScannerConfiguracion(SisScannerConfiguracion scannerconfiguracionF, SisSuceso sisSuceso) throws Exception;

}
