package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProveedorCategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaProveedorComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorCategoria;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ProveedorCategoriaServiceImpl implements ProveedorCategoriaService {

    @Autowired
    private ProveedorCategoriaDao proveedorCategoriaDao;

    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvProveedorCategoriaTO> getInvProveedorCategoriaTO(String empresa) throws Exception {
        return proveedorCategoriaDao.getInvProveedorCategoriaTO(empresa);
    }

    @Override
    public List<InvCategoriaProveedorComboTO> getListaCategoriaProveedorComboTO(String empresa) throws Exception {
        return proveedorCategoriaDao.getListaCategoriaProveedorComboTO(empresa);
    }

    @Override
    public InvProveedorCategoria buscarInvProveedorCategoria(String empresa, String codigo, String detalle) throws Exception {
        return proveedorCategoriaDao.buscarInvProveedorCategoria(empresa, codigo, detalle);
    }

    @Override
    public InvProveedorCategoria buscarInvProveedorCategoria(String empresa, String codigo) throws Exception {
        return proveedorCategoriaDao.buscarInvProveedorCategoria(empresa, codigo);
    }

    @Override
    public String accionInvProveedorCategoria(InvProveedorCategoriaTO invProveedorCategoriaTO, char accion,
            SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = invProveedorCategoriaTO.getPcCodigo() + " : " + invProveedorCategoriaTO.getPcDetalle();
        if (accion == 'I') {
            susDetalle = "Se insertó categoría Proveedor " + invProveedorCategoriaTO.getPcDetalle();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó categoría Proveedor " + invProveedorCategoriaTO.getPcDetalle();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó categoría Proveedor " + invProveedorCategoriaTO.getPcDetalle();
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_proveedor_categ";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invProveedorCategoria
        invProveedorCategoriaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        InvProveedorCategoria invProveedorCategoria = ConversionesInventario
                .convertirInvProveedorCategoriaTO_InvProveedorCategoria(invProveedorCategoriaTO);
        InvProveedorCategoria invProveedorCategoriaAux = null;
        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (proveedorCategoriaDao.comprobarInvProveedorCategoria(invProveedorCategoriaTO.getPcEmpresa(),
                    invProveedorCategoriaTO.getPcCodigo())) {
                if (proveedorCategoriaDao.comprobarEliminarInvProveedorCategoria(
                        invProveedorCategoriaTO.getPcEmpresa(), invProveedorCategoriaTO.getPcCodigo())) {
                    invProveedorCategoriaAux = proveedorCategoriaDao.buscarInvProveedorCategoria(
                            invProveedorCategoriaTO.getPcEmpresa(), invProveedorCategoriaTO.getPcCodigo());
                    invProveedorCategoria.setUsrFechaInserta(invProveedorCategoriaAux.getUsrFechaInserta());
                    comprobar = proveedorCategoriaDao.accionInvProveedorCategoria(invProveedorCategoria, sisSuceso,
                            accion);
                } else {
                    mensaje = "FNo se puede eliminar, la Categoría ya tiene Proveedores...";
                }
            } else {
                mensaje = "FNo se encuentra la Categoría Proveedor...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (proveedorCategoriaDao.comprobarInvProveedorCategoria(invProveedorCategoriaTO.getPcEmpresa(),
                    invProveedorCategoriaTO.getPcCodigo())) {
                invProveedorCategoriaAux = proveedorCategoriaDao.buscarInvProveedorCategoria(
                        invProveedorCategoriaTO.getPcEmpresa(), invProveedorCategoriaTO.getPcCodigo());
                invProveedorCategoria.setUsrFechaInserta(invProveedorCategoriaAux.getUsrFechaInserta());
                comprobar = proveedorCategoriaDao.accionInvProveedorCategoria(invProveedorCategoria, sisSuceso,
                        accion);
            } else {
                mensaje = "FNo se encuentra la Categoría Proveedor...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!proveedorCategoriaDao.comprobarInvProveedorCategoria(invProveedorCategoriaTO.getPcEmpresa(),
                    invProveedorCategoriaTO.getPcCodigo())) {
                invProveedorCategoria.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = proveedorCategoriaDao.accionInvProveedorCategoria(invProveedorCategoria, sisSuceso,
                        accion);
            } else {
                mensaje = "FYa existe la Categoría Proveedor...";
            }
        }

        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa categoría proveedor:Código " + invProveedorCategoriaTO.getPcCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa categoría proveedor:Código " + invProveedorCategoriaTO.getPcCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa categoría proveedor:Código " + invProveedorCategoriaTO.getPcCodigo() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

}
