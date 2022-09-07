package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorTransportista;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

public interface InvProveedorTransportistaDao extends GenericDao<InvProveedorTransportista, Integer> {

    public boolean insertarTransportista(InvProveedorTransportista invProveedorTransportista, SisSuceso sisSuceso) throws Exception;

    public List<InvProveedorTransportista> listarTransportistas(String porvEmpresa, String provCodigo) throws Exception;

    public boolean eliminar(Integer secuencial, SisSuceso sisSuceso) throws Exception;

    public boolean modificarProveedorTransportista(InvProveedorTransportista transportista, SisSuceso sisSuceso) throws Exception;
    
    public List<InvProveedorTransportistaTO> listarTransportistasTO(String provEmpresa, String provCodigo) throws Exception;

}
