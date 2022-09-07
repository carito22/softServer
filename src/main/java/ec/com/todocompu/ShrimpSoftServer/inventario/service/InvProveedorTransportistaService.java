package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorTransportista;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface InvProveedorTransportistaService {

    public boolean insertarProveedorTransportista(InvProveedorTransportista invProveedorTransportista, SisInfoTO sisInfoTO) throws Exception;

    public List<InvProveedorTransportista> listarTransportistas(String provEmpresa, String provCodigo) throws Exception;
    
    public List<InvProveedorTransportistaTO> listarTransportistasTO(String provEmpresa, String provCodigo) throws Exception;
    
    public boolean eliminar(Integer secuencial, String transportistaRuc, String proveedorRuc, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarProveedorTransportista(InvProveedorTransportista transportista, SisInfoTO sisInfoTO) throws Exception;

}
