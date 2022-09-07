package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.ScannerConfiguracionDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisScannerConfiguracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

@Service
public class ScannerConfiguracionServiceImpl implements ScannerConfiguracionService {

    @Autowired
    private ScannerConfiguracionDao scannerConfiguracionDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean comprobarSisScannerConfiguracion(String empresa) throws Exception {
        return scannerConfiguracionDao.comprobarScannerConfiguracion(empresa);
    }

    @Override
    public List<SisScannerConfiguracion> listarSisScannerConfiguracion(String empresa) throws Exception {
        return scannerConfiguracionDao.listarScannerConfiguracion(empresa);
    }

    @Override
    public SisScannerConfiguracion insertarSisScannerConfiguracion(SisScannerConfiguracion sisScannerConfiguracion, SisInfoTO sisInfoTO) throws Exception {
        sisScannerConfiguracion.setScEstado(true);
        sisScannerConfiguracion.setScEmpresa(sisInfoTO.getEmpresa());
        susClave = sisScannerConfiguracion.getScEmpresa();
        susDetalle = "Se ingresó la configuración de scanner: " + susClave;
        susSuceso = "INSERT";
        susTabla = "sistemaweb.sis_scanner_configuracion";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisScannerConfiguracion = scannerConfiguracionDao.insertarScannerConfiguracion(sisScannerConfiguracion, sisSuceso);
        return sisScannerConfiguracion;
    }

    @Override
    public SisScannerConfiguracion modificarSisScannerConfiguracion(SisScannerConfiguracion sisScannerConfiguracion, SisInfoTO sisInfoTO) throws Exception {
        susClave = sisScannerConfiguracion.getScEmpresa();
        susDetalle = "Se modificó la configuración de scanner: " + susClave;
        susSuceso = "UPDATE";
        susTabla = "sistemaweb.sis_scanner_configuracion";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisScannerConfiguracion = scannerConfiguracionDao.modificarScannerConfiguracion(sisScannerConfiguracion, sisSuceso);
        return sisScannerConfiguracion;
    }

    @Override
    public SisScannerConfiguracion modificarEstadoSisScannerConfiguracion(SisScannerConfiguracion sisScannerConfiguracion, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        sisScannerConfiguracion.setScEstado(estado);
        susClave = sisScannerConfiguracion.getScEmpresa();
        susDetalle = "Se " + (estado ? "activó" : "desactivó") + " la configuración de scanner: " + susClave;
        susSuceso = "UPDATE";
        susTabla = "sistemaweb.sis_scanner_configuracion";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisScannerConfiguracion = scannerConfiguracionDao.modificarScannerConfiguracion(sisScannerConfiguracion, sisSuceso);
        return sisScannerConfiguracion;
    }

    @Override
    public boolean eliminarSisScannerConfiguracion(String sisScannerConfiguracion, SisInfoTO sisInfoTO) throws Exception {
        susClave = sisScannerConfiguracion;
        susDetalle = "Se eliminó la configuración de scanner: " + susClave;
        susSuceso = "DELETE";
        susTabla = "sistemaweb.sis_scanner_configuracion";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return scannerConfiguracionDao.eliminarScannerConfiguracion(new SisScannerConfiguracion(sisScannerConfiguracion), sisSuceso);
    }

}
