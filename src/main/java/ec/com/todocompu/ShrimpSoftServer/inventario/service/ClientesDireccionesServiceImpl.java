package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClientesDireccionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesDireccionesServiceImpl implements ClientesDireccionesService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private ClientesDireccionesDao clientesDireccionesDao;

    @Override
    public void insertarClientesDirecciones(List<InvClientesDirecciones> clientesDirecciones, String cliEmpresa, String cliCodigo, SisInfoTO sisInfoTO) throws Exception {
        List<InvClientesDirecciones> listEnBaseDeDatos = clientesDireccionesDao.listarInvClientesDirecciones(cliEmpresa, cliCodigo);
        obtenerListaAPersistir(clientesDirecciones, cliEmpresa, cliCodigo);
        if (listEnBaseDeDatos != null && !listEnBaseDeDatos.isEmpty()) {
            clientesDirecciones.forEach((direccion) -> {
                listEnBaseDeDatos.removeIf(item -> item.getDirCodigo().equals(direccion.getDirCodigo()));
            });
            listEnBaseDeDatos.forEach((detalle) -> {
                eliminar(detalle, sisInfoTO);
            });
            clientesDireccionesDao.saveOrUpdate(clientesDirecciones);
        } else {
            insertar(clientesDirecciones, cliEmpresa, cliCodigo, sisInfoTO);
        }
    }

    @Override
    public List<InvClientesDirecciones> listarInvClientesDirecciones(String dirEmpresa, String dirCodigo) throws Exception {
        return clientesDireccionesDao.listarInvClientesDirecciones(dirEmpresa, dirCodigo);
    }

    @Override
    public InvClientesDirecciones obtenerDireccion(String cliEmpresa, String cliCodigoEstablecimiento, String cliCodigo) throws Exception {
        return clientesDireccionesDao.obtenerDireccion(cliEmpresa, cliCodigoEstablecimiento, cliCodigo);
    }

    private void obtenerListaAPersistir(List<InvClientesDirecciones> clientesDirecciones, String cliEmpresa, String cliCodigo) {
        for (InvClientesDirecciones direccion : clientesDirecciones) {
            direccion.setInvCliente(new InvCliente(cliEmpresa, cliCodigo));
        }
    }

    private void eliminar(InvClientesDirecciones detalle, SisInfoTO sisInfoTO) {
        clientesDireccionesDao.eliminar(detalle);
        String susClave = detalle.getDirCodigo() + "";
        String susDetalle = "Se eliminó la plantilla de direcciones: " + susClave;
        String susSuceso = "DELETE";
        String susTabla = "inventario.inv_clientes_direcciones";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sucesoDao.insertar(sisSuceso);
    }

    private void insertar(List<InvClientesDirecciones> listAPersistir, String cliEmpresa, String cliCodigo, SisInfoTO sisInfoTO) {
        for (InvClientesDirecciones detalle : listAPersistir) {
            detalle.setInvCliente(new InvCliente(cliEmpresa, cliCodigo));
            clientesDireccionesDao.insertar(detalle);
            String susClave = detalle.getDirCodigo() + "";
            String susDetalle = "Se insertó la plantilla de direcciones: " + susClave;
            String susSuceso = "INSERT";
            String susTabla = "inventario.inv_clientes_direcciones";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sucesoDao.insertar(sisSuceso);
        }
    }

}
