package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VendedorDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class VendedorServiceImpl implements VendedorService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private VendedorDao vendedorDao;

    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvVendedorComboListadoTO> getComboinvListaVendedorTOs(String empresa, String busqueda) throws Exception {
        return vendedorDao.getComboinvListaVendedorTOs(empresa,busqueda);
    }

    @Override
    public String accionInvVendedorTO(InvVendedorTO invVendedorTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = invVendedorTO.getVendCodigo() + " : " + invVendedorTO.getVendNombre();
        if (accion == 'I') {
            susDetalle = "Se insertó Vendedor " + invVendedorTO.getVendNombre();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó Vendedor " + invVendedorTO.getVendNombre();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó Vendedor " + invVendedorTO.getVendNombre();
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_vendedor";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invProductoMedida
        invVendedorTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        InvVendedor invVendedor = ConversionesInventario.convertirInvVendedorTO_InvVendedor(invVendedorTO);
        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (vendedorDao.comprobarInvVendedor(invVendedorTO.getVendEmpresa(), invVendedorTO.getVendCodigo())) {
                if (vendedorDao.comprobarEliminarInvVendedor(invVendedorTO.getVendEmpresa(),
                        invVendedorTO.getVendCodigo())) {
                    invVendedor.setUsrFechaInserta(vendedorDao
                            .buscarInvVendedor(invVendedorTO.getVendEmpresa(), invVendedorTO.getVendCodigo())
                            .getUsrFechaInserta());
                    comprobar = vendedorDao.accionInvVendedor(invVendedor, sisSuceso, accion);
                } else {
                    mensaje = "FNo se puede eliminar, el Vendedor ya esta asignado a algunos Clientes...";
                }
            } else {
                mensaje = "FNo se encuentra el Vendedor...";
            }
        }
        if (accion == 'M') {

            ////// BUSCANDO existencia Categoría
            if (vendedorDao.comprobarInvVendedor(invVendedorTO.getVendEmpresa(), invVendedorTO.getVendCodigo())) {
                invVendedor.setUsrFechaInserta(
                        vendedorDao.buscarInvVendedor(invVendedorTO.getVendEmpresa(), invVendedorTO.getVendCodigo())
                                .getUsrFechaInserta());
                comprobar = vendedorDao.accionInvVendedor(invVendedor, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el Vendedor...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!vendedorDao.comprobarInvVendedor(invVendedorTO.getVendEmpresa(), invVendedorTO.getVendCodigo())) {
                invVendedor.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = vendedorDao.accionInvVendedor(invVendedor, sisSuceso, accion);
            } else {
                mensaje = "FYa existe el Vendedor...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TSe eliminó correctamente el Vendedor";
            }
            if (accion == 'M') {
                mensaje = "TSe modificó correctamente el Vendedor";
            }
            if (accion == 'I') {
                mensaje = "TSe ingresó correctamente  el Vendedor";
            }
        }
        return mensaje;
    }

    @Override
    public boolean eliminarInvVendedor(InvVendedorPK invVendedorPK, SisInfoTO sisInfoTO) throws Exception {
        InvVendedor invVendedor = vendedorDao.buscarInvVendedor(invVendedorPK.getVendEmpresa(), invVendedorPK.getVendCodigo());
        if (invVendedor != null) {
            comprobar = false;
            ///// CREANDO Suceso
            susClave = invVendedor.getInvVendedorPK().getVendCodigo() + " : " + invVendedor.getVendNombre();
            susDetalle = "Se eliminó Vendedor " + invVendedor.getVendNombre();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_vendedor";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ////// BUSCANDO existencia Categoría
            if (vendedorDao.comprobarEliminarInvVendedor(invVendedor.getInvVendedorPK().getVendEmpresa(), invVendedor.getInvVendedorPK().getVendCodigo())) {
                comprobar = vendedorDao.accionInvVendedor(invVendedor, sisSuceso, 'E');
            } else {
                mensaje = "No se puede eliminar, el Vendedor ya esta asignado a algunos Clientes...";
                 throw new GeneralException(mensaje, mensaje);
            }
            return comprobar;
        } else {
            throw new GeneralException("El vendedor que desea eliminar ya no existe", "El vendedor que desea eliminar ya no existe");
        }
    }

}
