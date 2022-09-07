package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorTransportista;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class InvProveedorTransportistaDaoImpl extends GenericDaoImpl<InvProveedorTransportista, Integer> implements InvProveedorTransportistaDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean insertarTransportista(InvProveedorTransportista invProveedorTransportista, SisSuceso sisSuceso) throws Exception {
        insertar(invProveedorTransportista);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarProveedorTransportista(InvProveedorTransportista transportista, SisSuceso sisSuceso) throws Exception {
        actualizar(transportista);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<InvProveedorTransportista> listarTransportistas(String provEmpresa, String provCodigo) throws Exception {
        String sql = "SELECT * FROM inventario.inv_proveedor_transportista WHERE prov_empresa = ('"
                + provEmpresa + "') AND " + "prov_codigo = ('" + provCodigo + "')";
        return genericSQLDao.obtenerPorSql(sql, InvProveedorTransportista.class);
    }
    
    @Override
    public List<InvProveedorTransportistaTO> listarTransportistasTO(String provEmpresa, String provCodigo) throws Exception {
        String sql = "SELECT pt_secuencial, pt_transportista_ruc, pt_transportista_razon_social FROM inventario.inv_proveedor_transportista WHERE prov_empresa = ('"
                + provEmpresa + "') AND " + "prov_codigo = ('" + provCodigo + "')";
        return genericSQLDao.obtenerPorSql(sql, InvProveedorTransportistaTO.class);
    }

    @Override
    public boolean eliminar(Integer secuencial, SisSuceso sisSuceso) throws Exception {
        eliminar(new InvProveedorTransportista(secuencial));
        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
