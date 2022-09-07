package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Arrays;
import java.util.List;

@Repository
public class FirmaElectronicaDaoImpl extends GenericDaoImpl<SisFirmaElectronica, Integer> implements FirmaElectronicaDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisFirmaElectronica> listarFirmaElectronica(String empresa) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_firma_electronica where firma_empresa='" + empresa + "'";
        return genericSQLDao.obtenerPorSql(sql, SisFirmaElectronica.class);
    }

    @Override
    public List<SisFirmaElectronica> listarFirmaElectronicaNoVerificadas(String empresa) throws Exception {
        String sqlEmpresa = empresa != null && !empresa.equals("") ? "  AND firma_empresa='" + empresa + "'" : "";
        String sql = "SELECT * FROM sistemaweb.sis_firma_electronica where not firma_verificada" + sqlEmpresa;
        return genericSQLDao.obtenerPorSql(sql, SisFirmaElectronica.class);
    }

    @Override
    public boolean existeCertificado(String empresa, Integer secuencia, byte[] base64) throws Exception {
        String sqlSecuencial = "";
        if (secuencia != null) {
            sqlSecuencial = " AND firma_secuencial !=" + secuencia;
        }
        String sql = "SELECT * FROM sistemaweb.sis_firma_electronica where firma_empresa='" + empresa
                + "'" + sqlSecuencial;
        List<SisFirmaElectronica> listaFirmas = genericSQLDao.obtenerPorSql(sql, SisFirmaElectronica.class);
        boolean resp = false;

        for (SisFirmaElectronica firma : listaFirmas) {
            if (Arrays.equals(firma.getFirmaElectronicaArchivo(), base64)) {
                resp = true;
                break;
            }
        }

        return resp;
    }

    @Override
    public SisFirmaElectronica insertarFirmaElectronica(SisFirmaElectronica firmaElectronica, SisSuceso sisSuceso) throws Exception {
        insertar(firmaElectronica);
        sucesoDao.insertar(sisSuceso);
        return firmaElectronica;
    }

    @Override
    public SisFirmaElectronica modificarFirmaElectronica(SisFirmaElectronica firmaElectronica, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(firmaElectronica);
        return firmaElectronica;
    }

    @Override
    public boolean eliminarFirmaElectronica(SisFirmaElectronica firmaElectronica, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(firmaElectronica);
        return true;
    }

    @Override
    public SisFirmaElectronica obtenerFirmaElectronica(Integer secuencial) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_firma_electronica where firma_secuencial=" + secuencial;
        return genericSQLDao.obtenerObjetoPorSql(sql, SisFirmaElectronica.class);
    }

}
