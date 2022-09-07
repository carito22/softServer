package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisScannerConfiguracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

@Repository
public class ScannerConfiguracionDaoImpl extends GenericDaoImpl<SisScannerConfiguracion, String> implements ScannerConfiguracionDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean comprobarScannerConfiguracion(String empresa) throws Exception {
        int conteo = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql("SELECT count(sc_empresa) "
                + "FROM sistemaweb.sis_scanner_configuracion WHERE sc_empresa = ('" + empresa + "') and sc_estado = TRUE"));
        return conteo > 0;
    }

    @Override
    public List<SisScannerConfiguracion> listarScannerConfiguracion(String empresa) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_scanner_configuracion where sc_empresa='" + empresa + "'";
        return genericSQLDao.obtenerPorSql(sql, SisScannerConfiguracion.class);
    }

    @Override
    public SisScannerConfiguracion insertarScannerConfiguracion(SisScannerConfiguracion scannerconfiguracion, SisSuceso sisSuceso) throws Exception {
        insertar(scannerconfiguracion);
        sucesoDao.insertar(sisSuceso);
        return scannerconfiguracion;
    }

    @Override
    public SisScannerConfiguracion modificarScannerConfiguracion(SisScannerConfiguracion scannerconfiguracion, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(scannerconfiguracion);
        return scannerconfiguracion;
    }

    @Override
    public boolean eliminarScannerConfiguracion(SisScannerConfiguracion scannerconfiguracion, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(scannerconfiguracion);
        return true;
    }

}
