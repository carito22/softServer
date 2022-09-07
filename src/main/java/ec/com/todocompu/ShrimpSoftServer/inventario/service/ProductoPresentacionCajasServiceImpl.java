package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoPresentacionCajasDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ProductoPresentacionCajasServiceImpl implements ProductoPresentacionCajasService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private ProductoPresentacionCajasDao productoPresentacionCajasDao;

    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvProductoPresentacionCajasComboListadoTO> getListaPresentacionCajaComboTO(String empresa)
            throws Exception {
        return productoPresentacionCajasDao.getListaPresentacionCajaComboTO(empresa);
    }

    @Override
    public String accionInvProductoPresentacionCajasTO(InvProductoPresentacionCajasTO invProductoPresentacionCajasTO,
            char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = invProductoPresentacionCajasTO.getPrescCodigo() + " : "
                + invProductoPresentacionCajasTO.getPrescDetalle();
        if (accion == 'I') {
            susDetalle = "Se insertó Producto Presentación Caja "
                    + invProductoPresentacionCajasTO.getPrescDetalle();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó Producto Presentación Caja "
                    + invProductoPresentacionCajasTO.getPrescDetalle();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó Producto Presentación Caja "
                    + invProductoPresentacionCajasTO.getPrescDetalle();
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_producto_presentacion_cajas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invProductoMedida
        invProductoPresentacionCajasTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        InvProductoPresentacionCajas invProductoPresentacionCajas = ConversionesInventario
                .convertirInvProductoPresentacionCajasTO_InvProductoPresentacionCajas(
                        invProductoPresentacionCajasTO);

        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (productoPresentacionCajasDao.comprobarInvProductoPresentacionCajas(
                    invProductoPresentacionCajasTO.getPrescEmpresa(),
                    invProductoPresentacionCajasTO.getPrescCodigo())) {
                if (productoPresentacionCajasDao.comprobarEliminarInvProductoPresentacionCajas(
                        invProductoPresentacionCajasTO.getPrescEmpresa(),
                        invProductoPresentacionCajasTO.getPrescCodigo())) {
                    invProductoPresentacionCajas.setUsrFechaInserta(productoPresentacionCajasDao
                            .buscarProductoPresentacionCajas(invProductoPresentacionCajasTO.getPrescEmpresa(),
                                    invProductoPresentacionCajasTO.getPrescCodigo())
                            .getUsrFechaInserta());
                    comprobar = productoPresentacionCajasDao
                            .accionInvProductoPresentacionCajas(invProductoPresentacionCajas, sisSuceso, accion);
                } else {
                    mensaje = "FNo se puede eliminar, el Presentación Caja ya esta asignado a algunos Productos...";
                }
            } else {
                mensaje = "FNo se encuentra el Producto Presentación Caja...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (productoPresentacionCajasDao.comprobarInvProductoPresentacionCajas(
                    invProductoPresentacionCajasTO.getPrescEmpresa(),
                    invProductoPresentacionCajasTO.getPrescCodigo())) {
                invProductoPresentacionCajas.setUsrFechaInserta(productoPresentacionCajasDao
                        .buscarProductoPresentacionCajas(invProductoPresentacionCajasTO.getPrescEmpresa(),
                                invProductoPresentacionCajasTO.getPrescCodigo())
                        .getUsrFechaInserta());
                comprobar = productoPresentacionCajasDao
                        .accionInvProductoPresentacionCajas(invProductoPresentacionCajas, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el Producto Presentación Caja...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!productoPresentacionCajasDao.comprobarInvProductoPresentacionCajas(
                    invProductoPresentacionCajasTO.getPrescEmpresa(),
                    invProductoPresentacionCajasTO.getPrescCodigo())) {
                invProductoPresentacionCajas
                        .setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = productoPresentacionCajasDao
                        .accionInvProductoPresentacionCajas(invProductoPresentacionCajas, sisSuceso, accion);
            } else {
                mensaje = "FYa existe el código de Producto Presentación Caja";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TEl producto presentación de caja: Código " + invProductoPresentacionCajasTO.getPrescCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TEl producto presentación de caja: Código " + invProductoPresentacionCajasTO.getPrescCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TEl producto presentación de caja: Código " + invProductoPresentacionCajasTO.getPrescCodigo() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public boolean eliminarInvPresentacionCajas(InvProductoPresentacionCajasPK invProductoPresentacionCajasPK, SisInfoTO sisInfoTO) throws Exception {
        InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao.buscarProductoPresentacionCajas(invProductoPresentacionCajasPK.getPrescEmpresa(), invProductoPresentacionCajasPK.getPrescCodigo());
        if (invProductoPresentacionCajas != null) {
            comprobar = false;
            ///// CREANDO Suceso
            susClave = invProductoPresentacionCajasPK.getPrescCodigo() + " : " + invProductoPresentacionCajas.getPrescDetalle();
            susDetalle = "Se eliminó Producto Presentación Caja " + invProductoPresentacionCajas.getPrescDetalle();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_producto_presentacion_cajas";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ////// VERIFICANDO que no este siendo usada en los productos
            if (productoPresentacionCajasDao.comprobarEliminarInvProductoPresentacionCajas(invProductoPresentacionCajasPK.getPrescEmpresa(), invProductoPresentacionCajasPK.getPrescCodigo())) {
                comprobar = productoPresentacionCajasDao.accionInvProductoPresentacionCajas(invProductoPresentacionCajas, sisSuceso, 'E');
            } else {
                mensaje = "No se puede eliminar, la Presentación Caja ya esta asignado a algunos Productos.";
                throw new GeneralException(mensaje, mensaje);
            }
            return comprobar;
        } else {
            throw new GeneralException("El producto presentacion cajas que desea eliminar ya no existe", "El producto presentacion cajas que desea eliminar ya no existe");
        }
    }
}
