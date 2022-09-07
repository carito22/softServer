package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClientesVentasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClientesVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesVentasDetalleServiceImpl implements ClientesVentasDetalleService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private ClientesVentasDetalleDao invClientesVentasDetalleDao;
    @Autowired
    private ProductoDao productoDao;

    @Override
    public void insertarClientesVentasDetalles(List<InvClientesVentasDetalleTO> invClientesVentasDetalleTO, String cliEmpresa, String cliCodigo, SisInfoTO sisInfoTO) throws Exception {
        List<InvClientesVentasDetalle> listEnBaseDeDatos = invClientesVentasDetalleDao.listarInvClientesVentasDetalle(cliEmpresa, cliCodigo, 0);
        List<InvClientesVentasDetalle> listAPersistir = obtenerListaAPersistir(invClientesVentasDetalleTO);
        validarQueSeanServicios(listAPersistir);
        if (listEnBaseDeDatos != null && !listEnBaseDeDatos.isEmpty()) {
            listEnBaseDeDatos.removeAll(listAPersistir);
            listEnBaseDeDatos.forEach((detalle) -> {
                eliminar(detalle, sisInfoTO);
            });
            invClientesVentasDetalleDao.saveOrUpdate(listAPersistir);
        } else {
            insertar(listAPersistir, cliEmpresa, cliCodigo, sisInfoTO);
        }
    }

    private void eliminar(InvClientesVentasDetalle detalle, SisInfoTO sisInfoTO) {
        invClientesVentasDetalleDao.eliminar(detalle);
        String susClave = detalle.getDetSecuencial() + "";
        String susDetalle = "Se eliminó la plantilla de venta recurrente: " + susClave;
        String susSuceso = "DELETE";
        String susTabla = "inventario.inv_clientes_ventas_detalle";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sucesoDao.insertar(sisSuceso);
    }

    private List<InvClientesVentasDetalle> obtenerListaAPersistir(List<InvClientesVentasDetalleTO> invClientesVentasDetalleTO) {
        List<InvClientesVentasDetalle> resultado = new ArrayList<>();
        invClientesVentasDetalleTO.stream().map((detalle) -> ConversionesInventario.convertirInvClientesVentasDetalleTO_InvClientesVentasDetalle(detalle)).forEachOrdered((ventaDetalle) -> {
            resultado.add(ventaDetalle);
        });
        return resultado;
    }
    
    private void insertar(List<InvClientesVentasDetalle> listAPersistir, String cliEmpresa, String cliCodigo, SisInfoTO sisInfoTO) {
        for (InvClientesVentasDetalle detalle : listAPersistir) {
            detalle.setInvCliente(new InvCliente(cliEmpresa, cliCodigo));
            invClientesVentasDetalleDao.insertar(detalle);
            String susClave = detalle.getDetSecuencial() + "";
            String susDetalle = "Se insertó la plantilla de venta recurrente: " + susClave;
            String susSuceso = "INSERT";
            String susTabla = "inventario.inv_clientes_ventas_detalle";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sucesoDao.insertar(sisSuceso);
        }
    }

    public void validarQueSeanServicios(List<InvClientesVentasDetalle> listAPersistir) {
        String mensaje = "";
        for (InvClientesVentasDetalle detalle : listAPersistir) {
            InvProducto p = productoDao.obtenerPorIdEvict(InvProducto.class, detalle.getInvProducto().getInvProductoPK());
            if (!p.getInvProductoTipo().getTipTipo().equals("SERVICIOS")) {
                mensaje = mensaje + "\n<br>" + "Servicio: " + p.getInvProductoPK().getProCodigoPrincipal() + " \t\t" + p.getProNombre() + " - Tipo : "
                        + p.getInvProductoTipo().getTipTipo();
            }
        }
        if (!mensaje.equals("")) {
            throw new GeneralException("No es posible guardar porque los siguientes productos son de tipo 'SERVICIO':" + mensaje);
        }
    }

}
