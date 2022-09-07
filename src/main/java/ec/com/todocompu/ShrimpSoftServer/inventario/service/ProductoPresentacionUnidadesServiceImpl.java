package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoPresentacionUnidadesDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidades;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidadesPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ProductoPresentacionUnidadesServiceImpl implements ProductoPresentacionUnidadesService {

    @Autowired
    private ProductoPresentacionUnidadesDao productoPresentacionUnidadesDao;

    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvProductoPresentacionUnidadesComboListadoTO> getListaPresentacionUnidadComboTO(String empresa)
            throws Exception {
        return productoPresentacionUnidadesDao.getListaPresentacionUnidadComboTO(empresa);
    }

    @Override
    public String accionInvProductoPresentacionUnidadesTO(
            InvProductoPresentacionUnidadesTO invProductoPresentacionUnidadesTO, char accion, SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = invProductoPresentacionUnidadesTO.getPresuCodigo() + " : "
                + invProductoPresentacionUnidadesTO.getPresuDetalle();
        if (accion == 'I') {
            susDetalle = "El producto presentación unidad: Código " + invProductoPresentacionUnidadesTO.getPresuDetalle() + ", se ha guardado correctamente.";
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "El producto presentación unidad: Código " + invProductoPresentacionUnidadesTO.getPresuDetalle() + ", se ha modificado correctamente.";
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "El producto presentación unidad: Código " + invProductoPresentacionUnidadesTO.getPresuDetalle() + ", se ha eliminado correctamente.";
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_producto_presentacion_Unidad";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invProductoMedida
        invProductoPresentacionUnidadesTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        InvProductoPresentacionUnidades invProductoPresentacionUnidades = ConversionesInventario
                .convertirInvProductoPresentacionUnidadesTO_InvProductoPresentacionUnidades(
                        invProductoPresentacionUnidadesTO);

        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (productoPresentacionUnidadesDao.comprobarInvProductoPresentacionUnidades(
                    invProductoPresentacionUnidadesTO.getPresuEmpresa(),
                    invProductoPresentacionUnidadesTO.getPresuCodigo())) {
                if (productoPresentacionUnidadesDao.comprobarEliminarInvProductoPresentacionUnidades(
                        invProductoPresentacionUnidadesTO.getPresuEmpresa(),
                        invProductoPresentacionUnidadesTO.getPresuCodigo())) {
                    invProductoPresentacionUnidades.setUsrFechaInserta(productoPresentacionUnidadesDao
                            .buscarProductoPresentacionUnidades(invProductoPresentacionUnidadesTO.getPresuEmpresa(),
                                    invProductoPresentacionUnidadesTO.getPresuCodigo())
                            .getUsrFechaInserta());
                    comprobar = productoPresentacionUnidadesDao.accionInvProductoPresentacionUnidades(
                            invProductoPresentacionUnidades, sisSuceso, accion);
                } else {
                    mensaje = "FNo se puede eliminar, el Presentación Unidad ya está asignado a algunos Productos...";
                }
            } else {
                mensaje = "FNo se encuentra el Producto Presentación Unidad...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (productoPresentacionUnidadesDao.comprobarInvProductoPresentacionUnidades(
                    invProductoPresentacionUnidadesTO.getPresuEmpresa(),
                    invProductoPresentacionUnidadesTO.getPresuCodigo())) {
                invProductoPresentacionUnidades.setUsrFechaInserta(productoPresentacionUnidadesDao
                        .buscarProductoPresentacionUnidades(invProductoPresentacionUnidadesTO.getPresuEmpresa(),
                                invProductoPresentacionUnidadesTO.getPresuCodigo())
                        .getUsrFechaInserta());
                comprobar = productoPresentacionUnidadesDao
                        .accionInvProductoPresentacionUnidades(invProductoPresentacionUnidades, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el Producto Presentación Unidad...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!productoPresentacionUnidadesDao.comprobarInvProductoPresentacionUnidades(
                    invProductoPresentacionUnidadesTO.getPresuEmpresa(),
                    invProductoPresentacionUnidadesTO.getPresuCodigo())) {
                invProductoPresentacionUnidades
                        .setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = productoPresentacionUnidadesDao
                        .accionInvProductoPresentacionUnidades(invProductoPresentacionUnidades, sisSuceso, accion);
            } else {
                mensaje = "FYa existe el Unidad Producto...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TEl producto presentación unidad: Código " + invProductoPresentacionUnidadesTO.getPresuCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TEl producto presentación unidad: Código " + invProductoPresentacionUnidadesTO.getPresuCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TEl producto presentación unidad: Código " + invProductoPresentacionUnidadesTO.getPresuCodigo() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public boolean eliminarInvProductoPresentacionUnidades(InvProductoPresentacionUnidadesPK invProductoPresentacionUnidadesPK, SisInfoTO sisInfoTO) throws Exception {
        InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao.buscarProductoPresentacionUnidades(invProductoPresentacionUnidadesPK.getPresuEmpresa(), invProductoPresentacionUnidadesPK.getPresuCodigo());
        if (invProductoPresentacionUnidades != null) {
            comprobar = false;
            ///// CREANDO Suceso
            susClave = invProductoPresentacionUnidadesPK.getPresuCodigo() + " : " + invProductoPresentacionUnidades.getPresuDetalle();
            susDetalle = "Se eliminó Producto Presentación Unidad " + invProductoPresentacionUnidades.getPresuDetalle();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_producto_presentacion_unidades";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            if (productoPresentacionUnidadesDao.comprobarEliminarInvProductoPresentacionUnidades(invProductoPresentacionUnidadesPK.getPresuEmpresa(), invProductoPresentacionUnidadesPK.getPresuCodigo())) {
                comprobar = productoPresentacionUnidadesDao.accionInvProductoPresentacionUnidades(invProductoPresentacionUnidades, sisSuceso, 'E');
            } else {
                mensaje = "No se puede eliminar, presentación unidad ya está asignado a algunos productos.";
                throw new GeneralException(mensaje, mensaje);
            }
            return comprobar;
        } else {
            throw new GeneralException("La presentación unidad que desea eliminar ya no existe.", "La presentación unidad que desea eliminar ya no existe.");
        }
    }
}
