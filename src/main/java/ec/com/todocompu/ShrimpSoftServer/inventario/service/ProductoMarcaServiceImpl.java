package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoMarcaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarcaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ProductoMarcaServiceImpl implements ProductoMarcaService {

    @Autowired
    private ProductoMarcaDao productoMarcaDao;
    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvProductoMarcaComboListadoTO> getInvMarcaComboListadoTO(String empresa) throws Exception {
        return productoMarcaDao.getInvMarcaComboListadoTO(empresa);
    }

    @Override
    public String accionInvProductoMarcaTO(InvProductoMarcaTO invProductoMarcaTO, char accion, SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        if (invProductoMarcaTO.isMarIncluirEnFacturacion()) {
            invProductoMarcaTO.setMarIncluirEnFacturacion(true);
        } else {
            invProductoMarcaTO.setMarIncluirEnFacturacion(false);
        }
        ///// CREANDO Suceso
        susClave = invProductoMarcaTO.getMarCodigo() + " : " + invProductoMarcaTO.getMarDetalle();
        if (accion == 'I') {
            susDetalle = "Se insertó Marca producto " + invProductoMarcaTO.getMarDetalle();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó Marca producto " + invProductoMarcaTO.getMarDetalle();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó Marca producto " + invProductoMarcaTO.getMarDetalle();
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_producto_marca";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invProductoMedida
        invProductoMarcaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        InvProductoMarca invProductoMarca = ConversionesInventario
                .convertirInvProductoMarcaTO_InvProductoMarca(invProductoMarcaTO);

        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (productoMarcaDao.comprobarEliminarInvProductoMarca(invProductoMarcaTO.getMarEmpresa(),
                    invProductoMarcaTO.getMarCodigo())) {
                invProductoMarca.setUsrFechaInserta(productoMarcaDao
                        .buscarMarcaProducto(invProductoMarcaTO.getMarEmpresa(), invProductoMarcaTO.getMarCodigo())
                        .getUsrFechaInserta());
                comprobar = productoMarcaDao.accionInvProductoMarca(invProductoMarca, sisSuceso, accion);
            } else {
                mensaje = "FNo se puede eliminar, el Marca ya esta asignado a algunos Productos...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (!productoMarcaDao.comprobarInvProductoMarca(invProductoMarcaTO.getMarEmpresa(),
                    invProductoMarcaTO.getMarCodigo(), invProductoMarcaTO.getMarDetalle(), "MODIFICAR")) {
                invProductoMarca.setUsrFechaInserta(productoMarcaDao.buscarMarcaProducto(invProductoMarcaTO.getMarEmpresa(), invProductoMarcaTO.getMarCodigo()).getUsrFechaInserta());
                comprobar = productoMarcaDao.accionInvProductoMarca(invProductoMarca, sisSuceso, accion);
            } else {
                mensaje = "FEl Detalle ya existe en otra Marca...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!productoMarcaDao.comprobarInvProductoMarca(invProductoMarcaTO.getMarEmpresa(), invProductoMarcaTO.getMarCodigo(), invProductoMarcaTO.getMarDetalle(), "INSERTAR")) {
                if (!productoMarcaDao.comprobarInvProductoMarca(invProductoMarcaTO.getMarEmpresa(), invProductoMarcaTO.getMarCodigo(), invProductoMarcaTO.getMarDetalle(), "NULL")) {
                    invProductoMarca.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = productoMarcaDao.accionInvProductoMarca(invProductoMarca, sisSuceso, accion);
                } else {
                    mensaje = "FEl Detalle ya existe en otra Marca...";
                }

            } else {
                mensaje = "FYa existe el Marca Producto...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa marca de producto: Código " + invProductoMarcaTO.getMarCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa marca de producto: Código " + invProductoMarcaTO.getMarCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa marca de producto: Código " + invProductoMarcaTO.getMarCodigo() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public boolean eliminarInvProductoMarca(InvProductoMarcaPK invProductoMarcaPK, SisInfoTO sisInfoTO) throws Exception {
        InvProductoMarca invProductoMarca = productoMarcaDao.buscarMarcaProducto(invProductoMarcaPK.getMarEmpresa(), invProductoMarcaPK.getMarCodigo());
        if (invProductoMarca != null) {
            comprobar = false;
            ///// CREANDO Suceso
            susClave = invProductoMarcaPK.getMarCodigo() + " : " + invProductoMarca.getMarDetalle();
            susDetalle = "Se eliminó Marca producto " + invProductoMarca.getMarDetalle();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_producto_marca";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ////// BUSCANDO existencia Categoría
            if (productoMarcaDao.comprobarEliminarInvProductoMarca(invProductoMarcaPK.getMarEmpresa(), invProductoMarcaPK.getMarCodigo())) {
                comprobar = productoMarcaDao.accionInvProductoMarca(invProductoMarca, sisSuceso, 'E');
            } else {
                mensaje = "No se puede eliminar, la Marca ya esta asignada a algunos Productos.";
                throw new GeneralException(mensaje, mensaje);
            }
            return comprobar;
        } else {
            throw new GeneralException("La marca que desea eliminar ya no existe.", "La marca que desea eliminar ya no existe.");
        }
    }

    @Override
    public InvProductoMarca buscarMarcaProducto(String empresa, String codigoMarca) throws Exception {
        return productoMarcaDao.buscarMarcaProducto(empresa, codigoMarca);
    }

}
