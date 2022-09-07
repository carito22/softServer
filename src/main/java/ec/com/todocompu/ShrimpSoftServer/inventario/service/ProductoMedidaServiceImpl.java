package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoMedidaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvMedidaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMedidaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedida;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedidaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ProductoMedidaServiceImpl implements ProductoMedidaService {

    @Autowired
    private ProductoMedidaDao productoMedidaDao;
    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvProductoMedidaTO> getInvProductoMedidaTO(String empresa) throws Exception {
        return productoMedidaDao.getInvProductoMedidaTO(empresa);
    }

    @Override
    public List<InvMedidaComboTO> getListaInvMedidaTablaTO(String empresa) throws Exception {
        return productoMedidaDao.getListaInvMedidaTablaTO(empresa);
    }

    @Override
    public InvProductoMedida buscarProductoMedida(String empresa, String codigo) throws Exception {
        return productoMedidaDao.buscarProductoMedida(empresa, codigo);
    }

    @Override
    public String accionInvProductoMedida(InvProductoMedidaTO invProductoMedidaTO, char accion, SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        if (productoMedidaDao.productoMedidaRepetido(invProductoMedidaTO.getMedEmpresa(),
                invProductoMedidaTO.getMedCodigo(), invProductoMedidaTO.getMedDetalle())) {
            mensaje = "FDetalle repetido, verifique por favor...!";
            return mensaje;
        }

        ///// CREANDO Suceso
        susClave = invProductoMedidaTO.getMedCodigo() + " : " + invProductoMedidaTO.getMedDetalle();
        if (accion == 'I') {
            susDetalle = "Se insertó medida producto " + invProductoMedidaTO.getMedDetalle();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó medida producto " + invProductoMedidaTO.getMedDetalle();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó medida producto " + invProductoMedidaTO.getMedDetalle();
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_producto_medida";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invProductoMedida
        invProductoMedidaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        InvProductoMedida invProductoMedida = ConversionesInventario
                .convertirInvProductoMedidaTO_InvProductoMedida(invProductoMedidaTO);

        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (productoMedidaDao.comprobarInvProductoMedida(invProductoMedidaTO.getMedEmpresa(),
                    invProductoMedidaTO.getMedCodigo())) {
                if (productoMedidaDao.comprobarEliminarInvProductoMedida(invProductoMedidaTO.getMedEmpresa(),
                        invProductoMedidaTO.getMedCodigo())) {
                    invProductoMedida.setUsrFechaInserta(
                            productoMedidaDao.buscarProductoMedida(invProductoMedidaTO.getMedEmpresa(),
                                    invProductoMedidaTO.getMedCodigo()).getUsrFechaInserta());
                    comprobar = productoMedidaDao.accionInvProductoMedida(invProductoMedida, sisSuceso, accion);
                } else {
                    mensaje = "FNo se puede eliminar, la Medida ya tiene Productos...";
                }
            } else {
                mensaje = "FNo se encuentra la Medida Producto...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (productoMedidaDao.comprobarInvProductoMedida(invProductoMedidaTO.getMedEmpresa(),
                    invProductoMedidaTO.getMedCodigo())) {
                invProductoMedida.setUsrFechaInserta(
                        productoMedidaDao.buscarProductoMedida(invProductoMedidaTO.getMedEmpresa(),
                                invProductoMedidaTO.getMedCodigo()).getUsrFechaInserta());
                comprobar = productoMedidaDao.accionInvProductoMedida(invProductoMedida, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra la Medida Producto...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!productoMedidaDao.comprobarInvProductoMedida(invProductoMedidaTO.getMedEmpresa(), invProductoMedidaTO.getMedCodigo())) {
                invProductoMedida.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = productoMedidaDao.accionInvProductoMedida(invProductoMedida, sisSuceso, accion);
            } else {
                mensaje = "FYa existe la Medida Producto...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa medida producto:Código " + invProductoMedidaTO.getMedCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa medida producto:Código " + invProductoMedidaTO.getMedCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa medida producto:Código " + invProductoMedidaTO.getMedCodigo() + ", se ha guardado correctamente.";
            }

        }
        return mensaje;
    }

    @Override
    public boolean eliminarInvProductoMedida(InvProductoMedidaPK invProductoMedidaPK, SisInfoTO sisInfoTO) throws Exception {
        InvProductoMedida invProductoMedida = productoMedidaDao.buscarProductoMedida(invProductoMedidaPK.getMedEmpresa(), invProductoMedidaPK.getMedCodigo());
        if (invProductoMedida != null) {
            comprobar = false;
            ///// CREANDO Suceso
            susClave = invProductoMedida.getInvProductoMedidaPK().getMedCodigo() + " : " + invProductoMedida.getMedDetalle();
            susDetalle = "Se eliminó medida producto " + invProductoMedida.getMedDetalle();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_producto_medida";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ////// BUSCANDO existencia Categoría
            if (productoMedidaDao.comprobarEliminarInvProductoMedida(invProductoMedida.getInvProductoMedidaPK().getMedEmpresa(), invProductoMedida.getInvProductoMedidaPK().getMedCodigo())) {
                invProductoMedida.setUsrFechaInserta(productoMedidaDao.buscarProductoMedida(invProductoMedida.getInvProductoMedidaPK().getMedEmpresa(), invProductoMedida.getInvProductoMedidaPK().getMedCodigo()).getUsrFechaInserta());
                comprobar = productoMedidaDao.accionInvProductoMedida(invProductoMedida, sisSuceso, 'E');
            } else {
                mensaje = "No se puede eliminar, la Medida ya tiene Productos.";
                throw new GeneralException(mensaje, mensaje);
            }
            return comprobar;
        } else {
            throw new GeneralException("La medida de producto que desea eliminar ya no existe", "La medida de producto que desea eliminar ya no existe");
        }
    }
}
