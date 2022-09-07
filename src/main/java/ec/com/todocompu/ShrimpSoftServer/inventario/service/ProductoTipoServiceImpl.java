package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoTipoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ProductoTipoServiceImpl implements ProductoTipoService {

    @Autowired
    private ProductoTipoDao productoTipoDao;
    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvProductoTipoComboTO> getInvProductoTipoComboListadoTO(String empresa, String accion) throws Exception {
        return productoTipoDao.getInvProductoTipoComboListadoTO(empresa, accion);
    }

    @Override
    public boolean comprobarInvProductoTipoActivoFijo(String empresa, String codigo) throws Exception {
        return productoTipoDao.comprobarInvProductoTipoActivoFijo(empresa, codigo);
    }

    @Override
    public InvProductoTipo buscarInvProductoTipo(String empresa, String codigo) throws Exception {
        return productoTipoDao.buscarInvProductoTipo(empresa, codigo);
    }

    @Override
    public String accionInvProductoTipo(InvProductoTipoTO invProductoTipoTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = invProductoTipoTO.getTipCodigo() + " : " + invProductoTipoTO.getTipDetalle();
        if (accion == 'I') {
            susDetalle = "Se insertó tipo producto " + invProductoTipoTO.getTipDetalle();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó tipo producto " + invProductoTipoTO.getTipDetalle();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó tipo producto " + invProductoTipoTO.getTipDetalle();
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_producto_tipo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invProductoMedida
        invProductoTipoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        InvProductoTipo invProductoTipo = ConversionesInventario
                .convertirInvProductoTipoTO_InvProductoTipo(invProductoTipoTO);

        InvProductoTipo invProductoTipoAux = null;
        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (productoTipoDao.comprobarInvProductoTipo(invProductoTipoTO.getTipEmpresa(),
                    invProductoTipoTO.getTipCodigo())) {
                if (productoTipoDao.comprobarEliminarInvProductoTipo(invProductoTipoTO.getTipEmpresa(),
                        invProductoTipoTO.getTipCodigo())) {
                    invProductoTipoAux = productoTipoDao.buscarInvProductoTipo(invProductoTipoTO.getTipEmpresa(),
                            invProductoTipoTO.getTipCodigo());
                    invProductoTipo.setUsrFechaInserta(invProductoTipoAux.getUsrFechaInserta());
                    comprobar = productoTipoDao.accionInvProductoTipo(invProductoTipo, sisSuceso, accion);
                } else {
                    mensaje = "FNo se puede eliminar, el Tipo ya esta asignado a algunos Productos...";
                }
            } else {
                mensaje = "FNo se encuentra el Tipo Producto...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (productoTipoDao.comprobarInvProductoTipo(invProductoTipoTO.getTipEmpresa(),
                    invProductoTipoTO.getTipCodigo())) {
                invProductoTipoAux = productoTipoDao.buscarInvProductoTipo(invProductoTipoTO.getTipEmpresa(),
                        invProductoTipoTO.getTipCodigo());
                invProductoTipo.setUsrFechaInserta(invProductoTipoAux.getUsrFechaInserta());
                comprobar = productoTipoDao.accionInvProductoTipo(invProductoTipo, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el Tipo del Producto...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!productoTipoDao.comprobarInvProductoTipo(invProductoTipoTO.getTipEmpresa(), invProductoTipoTO.getTipCodigo())) {
                invProductoTipo.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = productoTipoDao.accionInvProductoTipo(invProductoTipo, sisSuceso, accion);
            } else {
                mensaje = "FYa existe el Tipo Producto...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TEl tipo de producto, código: " + invProductoTipoTO.getTipCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TEl tipo de producto, código: " + invProductoTipoTO.getTipCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TEl tipo de producto, código: " + invProductoTipoTO.getTipCodigo() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public Boolean comprobarEliminarInvProductoTipo(String empresa, String codigo) throws Exception {
        return productoTipoDao.comprobarEliminarInvProductoTipo(empresa, codigo);
    }

    @Override
    public boolean eliminarInvProductoTipo(InvProductoTipoPK invProductoTipoPK, SisInfoTO sisInfoTO) throws Exception {
        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(invProductoTipoPK.getTipEmpresa(), invProductoTipoPK.getTipCodigo());
        if (invProductoTipo != null) {
            comprobar = false;
            ///// CREANDO Suceso
            susClave = invProductoTipoPK.getTipCodigo() + " : " + invProductoTipo.getTipDetalle();
            susDetalle = "Se eliminó tipo producto " + invProductoTipo.getTipDetalle();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_producto_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ////// BUSCANDO existencia Categoría
            if (productoTipoDao.comprobarEliminarInvProductoTipo(invProductoTipoPK.getTipEmpresa(), invProductoTipoPK.getTipCodigo())) {
                comprobar = productoTipoDao.accionInvProductoTipo(invProductoTipo, sisSuceso, 'E');
            } else {
                mensaje = "No se puede eliminar, el Tipo ya esta asignado a algunos Productos...";
                throw new GeneralException(mensaje, mensaje);
            }
            return comprobar;
        } else {
            throw new GeneralException("El tipo que desea eliminar ya no existe.", "El tipo que desea eliminar ya no existe.");
        }
    }
}
