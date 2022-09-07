package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.InvProveedorTransportistaDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorTransportista;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;
import java.util.List;

@Service
public class InvProveedorTransportistaServiceImpl implements InvProveedorTransportistaService {

    @Autowired
    private InvProveedorTransportistaDao transportistaDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean insertarProveedorTransportista(InvProveedorTransportista invProveedorTransportista, SisInfoTO sisInfoTO) throws Exception {
        susClave = invProveedorTransportista.getPtSecuencial() + "";
        susDetalle = "Se ingresó el transportista " + invProveedorTransportista.getPtTransportistaRuc() + " al proveedor "
                + invProveedorTransportista.getInvProveedor().getProvIdNumero();
        susSuceso = "INSERT";
        susTabla = "inventario.inv_proveedor_transportista";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        invProveedorTransportista.setUsrFechaInserta(new Date());
        invProveedorTransportista.setUsrCodigo(sisInfoTO.getUsuario());
        return transportistaDao.insertarTransportista(invProveedorTransportista, sisSuceso);
    }

    @Override
    public boolean modificarProveedorTransportista(InvProveedorTransportista transportista, SisInfoTO sisInfoTO) throws Exception {
        susClave = transportista.getPtSecuencial() + "";
        susDetalle = "Se ingresó el transportista " + transportista.getPtTransportistaRuc() + " al proveedor "
                + transportista.getInvProveedor().getProvIdNumero();
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_proveedor_transportista";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        transportista.setUsrFechaInserta(new Date());
        transportista.setUsrCodigo(sisInfoTO.getUsuario());
        return transportistaDao.modificarProveedorTransportista(transportista, sisSuceso);
    }

    @Override
    public List<InvProveedorTransportista> listarTransportistas(String provEmpresa, String provCodigo) throws Exception {
        return transportistaDao.listarTransportistas(provEmpresa, provCodigo);
    }
    
    @Override
    public List<InvProveedorTransportistaTO> listarTransportistasTO(String provEmpresa, String provCodigo) throws Exception {
        return transportistaDao.listarTransportistasTO(provEmpresa, provCodigo);
    }

    @Override
    public boolean eliminar(Integer secuencial, String transportistaRuc, String proveedorRuc, SisInfoTO sisInfoTO) throws Exception {
        susClave = secuencial + "";
        susDetalle = "Se eliminó el transportista " + transportistaRuc + " al proveedor "
                + proveedorRuc;
        susSuceso = "DELETE";
        susTabla = "inventario.inv_proveedor_transportista";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return transportistaDao.eliminar(secuencial, sisSuceso);
    }

}
